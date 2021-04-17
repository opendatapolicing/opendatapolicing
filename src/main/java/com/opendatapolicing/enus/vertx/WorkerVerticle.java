package com.opendatapolicing.enus.vertx;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opendatapolicing.enus.config.ConfigKeys;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.opendatapolicing.enus.request.api.ApiRequest;
import com.opendatapolicing.enus.state.SiteStateEnUSApiServiceImpl;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.mail.MailClient;
import io.vertx.ext.mail.MailConfig;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;

/**
 */
public class WorkerVerticle extends WorkerVerticleGen<AbstractVerticle> {
	private static final Logger LOG = LoggerFactory.getLogger(WorkerVerticle.class);

	/**
	 * A io.vertx.ext.jdbc.JDBCClient for connecting to the relational database PostgreSQL. 
	 **/
	private PgPool pgPool;

	private SolrClient solrClient;

	private JsonObject config;

	WorkerExecutor workerExecutor;

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
					configureEmail().compose(h -> 
						importData()
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

				solrClient = new HttpSolrClient.Builder(config.getString(ConfigKeys.SOLR_URL)).build();

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
			pgOptions.setPort(config.getInteger(ConfigKeys.JDBC_PORT));
			pgOptions.setHost(config.getString(ConfigKeys.JDBC_HOST));
			pgOptions.setDatabase(config.getString(ConfigKeys.JDBC_DATABASE));
			pgOptions.setUser(config.getString(ConfigKeys.JDBC_USERNAME));
			pgOptions.setPassword(config.getString(ConfigKeys.JDBC_PASSWORD));
			pgOptions.setIdleTimeout(config.getInteger(ConfigKeys.JDBC_MAX_IDLE_TIME, 10));
			pgOptions.setIdleTimeoutUnit(TimeUnit.SECONDS);
			pgOptions.setConnectTimeout(config.getInteger(ConfigKeys.JDBC_CONNECT_TIMEOUT, 5));

			PoolOptions poolOptions = new PoolOptions();
			poolOptions.setMaxSize(config.getInteger(ConfigKeys.JDBC_MAX_POOL_SIZE, 1));
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
				SiteStateEnUSApiServiceImpl stateApi = new SiteStateEnUSApiServiceImpl(vertx.eventBus(), config, workerExecutor, pgPool, solrClient, null, null);
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
}

