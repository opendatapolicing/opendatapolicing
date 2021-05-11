package com.opendatapolicing.enus.vertx;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opendatapolicing.enus.config.ConfigKeys;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.opendatapolicing.enus.request.api.ApiRequest;
import com.opendatapolicing.enus.state.SiteStateEnUSApiServiceImpl;
import com.opendatapolicing.enus.trafficstop.TrafficStopEnUSApiServiceImpl;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mail.MailClient;
import io.vertx.ext.mail.MailConfig;
import io.vertx.ext.web.client.WebClient;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowStream;

/**
 */
public class WorkerVerticle extends WorkerVerticleGen<AbstractVerticle> {
	private static final Logger LOG = LoggerFactory.getLogger(WorkerVerticle.class);

	/**
	 * A io.vertx.ext.jdbc.JDBCClient for connecting to the relational database PostgreSQL. 
	 **/
	private PgPool pgPool;

	private WebClient webClient;

	private JsonObject config;

	WorkerExecutor workerExecutor;

	Semaphore semaphore;

	public WorkerVerticle setSemaphore(Semaphore semaphore) {
		this.semaphore = semaphore;
		return this;
	}

	/**	
	 *	This is called by Vert.x when the verticle instance is deployed. 
	 *	Initialize a new site context object for storing information about the entire site in English. 
	 *	Setup the startPromise to handle the configuration steps and starting the server. 
	 **/
	@Override()
	public void  start(Promise<Void> startPromise) throws Exception, Exception {

		try {
			Future<Void> promiseSteps = configureSiteContext().compose(a ->
				configureData().compose(b -> 
					configureSharedWorkerExecutor().compose(c -> 
						configureEmail().compose(d -> 
							syncDbToSolr().compose(e -> 
								importData()
							)
						)
					)
				)
			);
			promiseSteps.onComplete(startPromise);
		} catch (Exception ex) {
			LOG.error("Couldn't start verticle. ", ex);
		}
	}

	/**	
	 **/
	private Future<Void> configureSiteContext() {
		Promise<Void> promise = Promise.promise();

		try {
			ConfigRetrieverOptions retrieverOptions = new ConfigRetrieverOptions();
			ConfigStoreOptions storeEnv = new ConfigStoreOptions().setType("env");
			retrieverOptions.addStore(storeEnv);

			String configPath = System.getenv("configPath");
			if(StringUtils.isNotBlank(configPath)) {
				ConfigStoreOptions configIni = new ConfigStoreOptions().setType("file").setFormat("properties")
						.setConfig(new JsonObject().put("path", configPath));
				retrieverOptions.addStore(configIni);
			}

			ConfigRetriever configRetriever = ConfigRetriever.create(vertx, retrieverOptions);
			configRetriever.getConfig(a -> {
				config = a.result();

				webClient = WebClient.create(vertx);

				LOG.info("The site context was configured successfully. ");
				promise.complete();
			});
		} catch(Exception ex) {
			LOG.error("Unable to configure site context. ", ex);
			promise.fail(ex);
		}

		return promise.future();
	}

	/**	
	 * 
	 * Val.ConnectionError.enUS:Could not open the database client connection. 
	 * Val.ConnectionSuccess.enUS:The database client connection was successful. 
	 * 
	 * Val.InitError.enUS:Could not initialize the database tables. 
	 * Val.InitSuccess.enUS:The database tables were created successfully. 
	 * 
	 *	Configure shared database connections across the cluster for massive scaling of the application. 
	 *	Return a promise that configures a shared database client connection. 
	 *	Load the database configuration into a shared io.vertx.ext.jdbc.JDBCClient for a scalable, clustered datasource connection pool. 
	 *	Initialize the database tables if not already created for the first time. 
	 **/
	private Future<Void> configureData() {
		Promise<Void> promise = Promise.promise();
		try {
			PgConnectOptions pgOptions = new PgConnectOptions();
			Integer jdbcMaxPoolSize = config.getInteger(ConfigKeys.JDBC_MAX_POOL_SIZE, 1);

			pgOptions.setPort(config.getInteger(ConfigKeys.JDBC_PORT));
			pgOptions.setHost(config.getString(ConfigKeys.JDBC_HOST));
			pgOptions.setDatabase(config.getString(ConfigKeys.JDBC_DATABASE));
			pgOptions.setUser(config.getString(ConfigKeys.JDBC_USERNAME));
			pgOptions.setPassword(config.getString(ConfigKeys.JDBC_PASSWORD));
			pgOptions.setIdleTimeout(config.getInteger(ConfigKeys.JDBC_MAX_IDLE_TIME, 10));
			pgOptions.setIdleTimeoutUnit(TimeUnit.SECONDS);
			pgOptions.setConnectTimeout(config.getInteger(ConfigKeys.JDBC_CONNECT_TIMEOUT, 5));

			PoolOptions poolOptions = new PoolOptions();
			poolOptions.setMaxSize(jdbcMaxPoolSize);
			poolOptions.setMaxWaitQueueSize(config.getInteger(ConfigKeys.JDBC_MAX_WAIT_QUEUE_SIZE, 10));

			pgPool = PgPool.pool(vertx, pgOptions, poolOptions);

			LOG.info(configureDataInitSuccess);
			promise.complete();
		} catch (Exception ex) {
			LOG.error(configureDataInitError, ex);
			promise.fail(ex);
		}

		return promise.future();
	}

	/**	
	 * 
	 * Val.Error.enUS:Could not configure the shared worker executor. 
	 * Val.Success.enUS:The shared worker executor was configured successfully. 
	 * 
	 *	Configure a shared worker executor for running blocking tasks in the background. 
	 *	Return a promise that configures the shared worker executor. 
	 **/
	private Future<Void> configureSharedWorkerExecutor() {
		Promise<Void> promise = Promise.promise();
		try {
			workerExecutor = vertx.createSharedWorkerExecutor("WorkerExecutor");
			promise.complete();
		} catch (Exception ex) {
			LOG.error(configureSharedWorkerExecutorError, ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	/**	
	 * Configure sending email. 
	 * Val.Complete.enUS:Configure sending email succeeded. 
	 * Val.Fail.enUS:Configure sending email failed. 
	 **/
	private Future<Void> configureEmail() {
		Promise<Void> promise = Promise.promise();
		try {
			String emailHost = config.getString(ConfigKeys.EMAIL_HOST);
			if(StringUtils.isNotBlank(emailHost)) {
				MailConfig mailConfig = new MailConfig();
				mailConfig.setHostname(emailHost);
				mailConfig.setPort(config.getInteger(ConfigKeys.EMAIL_PORT));
				mailConfig.setSsl(config.getBoolean(ConfigKeys.EMAIL_SSL));
				mailConfig.setUsername(config.getString(ConfigKeys.EMAIL_USERNAME));
				mailConfig.setPassword(config.getString(ConfigKeys.EMAIL_PASSWORD));
				MailClient.createShared(vertx, mailConfig);
				LOG.info(configureEmailComplete);
				promise.complete();
			} else {
				LOG.info(configureEmailComplete);
				promise.complete();
			}
		} catch (Exception ex) {
			LOG.error(configureEmailFail, ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	/**	
	 * Import initial data
	 * Val.Complete.enUS:Importing initial data completed. 
	 * Val.Fail.enUS:Importing initial data failed. 
	 **/
	private Future<Void> importData() {
		Promise<Void> promise = Promise.promise();
		try {
			{
				SiteStateEnUSApiServiceImpl stateApi = new SiteStateEnUSApiServiceImpl(semaphore, vertx.eventBus(), config, workerExecutor, pgPool, webClient, null, null);
				SiteRequestEnUS siteRequest = stateApi.generateSiteRequestEnUS(null, null);
				JsonObject o = new JsonObject().put("stateName", "North Carolina").put("stateAbbreviation", "NC").put("pk", "NC");
				JsonArray list = new JsonArray().add(o);
				JsonObject jsonObject = new JsonObject().put("list", list);
				siteRequest.setJsonObject(jsonObject);
				ApiRequest apiRequest = new ApiRequest();
				apiRequest.setNumFound(1L);
				apiRequest.setNumPATCH(1L);
				apiRequest.initDeepApiRequest(siteRequest);
				siteRequest.setApiRequest_(apiRequest);
				stateApi.listPUTImportSiteState(apiRequest, siteRequest).onSuccess(a -> {
					LOG.info("{} State imported. ", o.getString("stateName"));
					promise.complete();
				}).onFailure(ex -> {
					LOG.error("WorkerVerticle importData failed. ", ex);
					promise.fail(ex);
				});
			}
		} catch (Exception ex) {
			LOG.error(configureEmailFail, ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	/**	
	 * Import initial data
	 * Val.Complete.enUS:Syncing database to Solr completed. 
	 * Val.Fail.enUS:Syncing database to Solr failed. 
	 * Val.Skip.enUS:Skip syncing database to Solr. 
	 **/
	private Future<Void> syncDbToSolr() {
		Promise<Void> promise = Promise.promise();
		if(config.getBoolean(ConfigKeys.ENABLE_DB_SOLR_SYNC, false)) {
			vertx.setTimer(1000 * 10, a -> {
				syncTrafficStops().onSuccess(b -> {
					LOG.info(syncDbToSolrComplete);
					promise.complete();
				}).onFailure(ex -> {
					LOG.error(syncDbToSolrFail, ex);
					promise.fail(ex);
				});
			});
		} else {
			LOG.info(syncDbToSolrSkip);
			promise.complete();
		}
		return promise.future();
	}

	/**	
	 * Sync TrafficStop data from the database to Solr. 
	 * Val.Complete.enUS:TrafficStop data sync completed. 
	 * Val.Fail.enUS:TrafficStop data sync failed. 
	 **/
	private Future<Void> syncTrafficStops() {
		Promise<Void> promise = Promise.promise();
		try {
			TrafficStopEnUSApiServiceImpl api = new TrafficStopEnUSApiServiceImpl(semaphore, vertx.eventBus(), config, workerExecutor, pgPool, webClient, null, null);
			pgPool.withTransaction(sqlConnection -> {
				Promise<Void> promise1 = Promise.promise();
				sqlConnection.prepare("SELECT * FROM TrafficStop").onSuccess(preparedStatement -> {
					try {
						RowStream<Row> stream = preparedStatement.createStream(2);
						stream.exceptionHandler(ex -> {
							LOG.error(syncTrafficStopsFail, ex);
							promise.fail(ex);
						});
						stream.endHandler(v -> {
							LOG.info(syncTrafficStopsComplete);
							promise.complete();
						});
						stream.handler(row -> {
							try {
								LOG.info("aquire: {}", semaphore.availablePermits());
								semaphore.acquire();
								Long pk = row.getLong(0);

								JsonObject params = new JsonObject();
								params.put("body", new JsonObject().put("pk", pk.toString()));
								params.put("path", new JsonObject());
								params.put("cookie", new JsonObject());
								params.put("query", new JsonObject().put("q", "*:*").put("fq", new JsonArray().add("pk:" + pk)));
								JsonObject context = new JsonObject().put("params", params);
								JsonObject json = new JsonObject().put("context", context);
								vertx.eventBus().send("opendatapolicing-enUS-TrafficStop", json, new DeliveryOptions().addHeader("action", "patchTrafficStopFuture"));
							} catch (Exception ex) {
								LOG.error(syncTrafficStopsFail, ex);
								promise1.fail(ex);
								LOG.info("release: {}", semaphore.availablePermits());
								semaphore.release();
							}
						});
					} catch (Exception ex) {
						LOG.error(syncTrafficStopsFail, ex);
						promise1.fail(ex);
					}
				}).onFailure(ex -> {
					LOG.error(syncTrafficStopsFail, ex);
					promise1.fail(ex);
				});
				return promise1.future();
			}).onFailure(ex -> {
				LOG.error(syncTrafficStopsFail, ex);
				promise.fail(ex);
			});
		} catch (Exception ex) {
			LOG.error(syncTrafficStopsFail, ex);
			promise.fail(ex);
		}
		return promise.future();
	}
}

