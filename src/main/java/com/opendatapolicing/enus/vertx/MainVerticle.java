package com.opendatapolicing.enus.vertx;           

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.helper.ConditionalHelpers;
import com.github.jknack.handlebars.helper.StringHelpers;
import com.opendatapolicing.enus.agency.SiteAgency;
import com.opendatapolicing.enus.agency.SiteAgencyEnUSGenApiService;
import com.opendatapolicing.enus.config.ConfigKeys;
import com.opendatapolicing.enus.java.LocalDateSerializer;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.opendatapolicing.enus.search.SearchList;
import com.opendatapolicing.enus.searchbasis.SearchBasisEnUSGenApiService;
import com.opendatapolicing.enus.state.SiteState;
import com.opendatapolicing.enus.state.SiteStateEnUSGenApiService;
import com.opendatapolicing.enus.trafficcontraband.TrafficContrabandEnUSGenApiService;
import com.opendatapolicing.enus.trafficperson.TrafficPersonEnUSGenApiService;
import com.opendatapolicing.enus.trafficsearch.TrafficSearch;
import com.opendatapolicing.enus.trafficsearch.TrafficSearchEnUSGenApiService;
import com.opendatapolicing.enus.trafficstop.TrafficStop;
import com.opendatapolicing.enus.trafficstop.TrafficStopEnUSGenApiService;
import com.opendatapolicing.enus.user.SiteUserEnUSGenApiService;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.JksOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.ext.auth.authorization.AuthorizationProvider;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.auth.oauth2.OAuth2FlowType;
import io.vertx.ext.auth.oauth2.OAuth2Options;
import io.vertx.ext.auth.oauth2.authorization.KeycloakAuthorization;
import io.vertx.ext.auth.oauth2.providers.OpenIDConnectAuth;
import io.vertx.ext.bridge.PermittedOptions;
import io.vertx.ext.healthchecks.HealthCheckHandler;
import io.vertx.ext.healthchecks.Status;
import io.vertx.ext.mail.MailClient;
import io.vertx.ext.mail.MailConfig;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.handler.OAuth2AuthHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.TemplateHandler;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.sstore.ClusteredSessionStore;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.templ.handlebars.HandlebarsTemplateEngine;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;
import io.vertx.sqlclient.PoolOptions;

/**	
 *	A Java class to start the Vert.x application as a main method. 
 * Keyword: classSimpleNameVerticle
 **/
public class MainVerticle extends MainVerticleGen<AbstractVerticle> {
	private static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);

	public final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	private Integer siteInstances;

	private Integer workerPoolSize;

	private Integer jdbcMaxPoolSize;

	private Integer jdbcMaxWaitQueueSize;

	/**
	 * A io.vertx.ext.jdbc.JDBCClient for connecting to the relational database PostgreSQL. 
	 **/
	private PgPool pgPool;

	private WebClient webClient;

	private Router router;

	WorkerExecutor workerExecutor;

	OAuth2Auth oauth2AuthenticationProvider;

	AuthorizationProvider authorizationProvider;

	/**	
	 *	The main method for the Vert.x application that runs the Vert.x Runner class
	 **/
	public static void  main(String[] args) {
		run();
	}

	/**	
	 * Val.Complete.enUS:The config was configured successfully. 
	 * Val.Fail.enUS:Could not configure the config(). 
	 **/
	public static Future<JsonObject> configureConfig(Vertx vertx) {
		Promise<JsonObject> promise = Promise.promise();

		try {
			ConfigRetrieverOptions retrieverOptions = new ConfigRetrieverOptions();

			retrieverOptions.addStore(new ConfigStoreOptions().setType("file").setFormat("properties").setConfig(new JsonObject().put("path", "application.properties")));

			String configPath = System.getenv(ConfigKeys.CONFIG_PATH);
			if(StringUtils.isNotBlank(configPath)) {
				ConfigStoreOptions configIni = new ConfigStoreOptions().setType("file").setFormat("properties").setConfig(new JsonObject().put("path", configPath));
				retrieverOptions.addStore(configIni);
			}

			ConfigStoreOptions storeEnv = new ConfigStoreOptions().setType("env");
			retrieverOptions.addStore(storeEnv);

			ConfigRetriever configRetriever = ConfigRetriever.create(vertx, retrieverOptions);
			configRetriever.getConfig().onSuccess(config -> {
				LOG.info("The config was configured successfully. ");
				promise.complete(config);
			}).onFailure(ex -> {
				LOG.error("Unable to configure site context. ", ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error("Unable to configure site context. ", ex);
			promise.fail(ex);
		}

		return promise.future();
	}

	public static void  run() {
		JsonObject zkConfig = new JsonObject();
		String zookeeperHostName = System.getenv(ConfigKeys.ZOOKEEPER_HOST_NAME);
		Integer zookeeperPort = Integer.parseInt(Optional.ofNullable(System.getenv(ConfigKeys.ZOOKEEPER_PORT)).orElse("2181"));
		String zookeeperHosts = Optional.ofNullable(System.getenv(ConfigKeys.ZOOKEEPER_HOSTS)).orElse(zookeeperHostName + ":" + zookeeperPort);
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(zookeeperHosts, retryPolicy);
		curatorFramework.start();
		Integer clusterPort = Optional.ofNullable(System.getenv(ConfigKeys.CLUSTER_PORT)).map(s -> Integer.parseInt(s)).orElse(null);
		String clusterHostName = System.getenv(ConfigKeys.CLUSTER_HOST_NAME);
		Integer clusterPublicPort = Optional.ofNullable(System.getenv(ConfigKeys.CLUSTER_PUBLIC_PORT)).map(s -> Integer.parseInt(s)).orElse(null);
		Integer siteInstances = Optional.ofNullable(System.getenv(ConfigKeys.SITE_INSTANCES)).map(s -> Integer.parseInt(s)).orElse(1);
		Long vertxWarningExceptionSeconds = Optional.ofNullable(System.getenv(ConfigKeys.VERTX_WARNING_EXCEPTION_SECONDS)).map(s -> Long.parseLong(s)).orElse(10L);
		String clusterPublicHostName = System.getenv(ConfigKeys.CLUSTER_PUBLIC_HOST_NAME);
		zkConfig.put("zookeeperHosts", zookeeperHosts);
		zkConfig.put("sessionTimeout", 500000);
		zkConfig.put("connectTimeout", 3000);
		zkConfig.put("rootPath", "opendatapolicing");
		zkConfig.put("retry", new JsonObject() {
			{
				put("initialSleepTime", 3000);
				put("intervalTimes", 10000);
				put("maxTimes", 3);
			}
		});
		ClusterManager clusterManager = new ZookeeperClusterManager(zkConfig);
		VertxOptions vertxOptions = new VertxOptions();
		// For OpenShift
		EventBusOptions eventBusOptions = new EventBusOptions();
		String hostname = System.getenv(ConfigKeys.HOSTNAME);
		String openshiftService = System.getenv(ConfigKeys.OPENSHIFT_SERVICE);
		if(clusterHostName == null) {
			clusterHostName = hostname;
		}
		if(clusterPublicHostName == null) {
			if(hostname != null && openshiftService != null) {
				clusterPublicHostName = hostname + "." + openshiftService;
			}
		}
		if(clusterHostName != null) {
			LOG.info(String.format("%s: %s", ConfigKeys.CLUSTER_HOST_NAME, clusterHostName));
			eventBusOptions.setHost(clusterHostName);
		}
		if(clusterPort != null) {
			LOG.info(String.format("%s: %s", ConfigKeys.CLUSTER_PORT, clusterPort));
			eventBusOptions.setPort(clusterPort);
		}
		if(clusterPublicHostName != null) {
			LOG.info(String.format("%s: %s", ConfigKeys.CLUSTER_PUBLIC_HOST_NAME, clusterPublicHostName));
			eventBusOptions.setClusterPublicHost(clusterPublicHostName);
		}
		if(clusterPublicPort != null) {
			LOG.info(String.format("%s: %s", ConfigKeys.CLUSTER_PUBLIC_PORT, clusterPublicPort));
			eventBusOptions.setClusterPublicPort(clusterPublicPort);
		}
		vertxOptions.setEventBusOptions(eventBusOptions);
		vertxOptions.setClusterManager(clusterManager);
		vertxOptions.setWarningExceptionTime(vertxWarningExceptionSeconds);
		vertxOptions.setWarningExceptionTimeUnit(TimeUnit.SECONDS);
		vertxOptions.setWorkerPoolSize(System.getenv(ConfigKeys.WORKER_POOL_SIZE) == null ? 5 : Integer.parseInt(System.getenv(ConfigKeys.WORKER_POOL_SIZE)));
		Consumer<Vertx> runner = vertx -> {
			configureConfig(vertx).onSuccess(config -> {
				try {
					List<Future> futures = new ArrayList<>();

					DeploymentOptions deploymentOptions = new DeploymentOptions();
					deploymentOptions.setInstances(siteInstances);
					deploymentOptions.setConfig(config);
		
					DeploymentOptions mailVerticleDeploymentOptions = new DeploymentOptions();
					mailVerticleDeploymentOptions.setConfig(config);
					mailVerticleDeploymentOptions.setWorker(true);
		
					DeploymentOptions workerVerticleDeploymentOptions = new DeploymentOptions();
					workerVerticleDeploymentOptions.setConfig(config);
					workerVerticleDeploymentOptions.setInstances(1);
		
					vertx.deployVerticle(MainVerticle.class, deploymentOptions).onSuccess(a -> {
						vertx.deployVerticle(WorkerVerticle.class, workerVerticleDeploymentOptions);
						LOG.info("Started main verticle. ");
					}).onFailure(ex -> {
						LOG.error("Failed to start main verticle. ", ex);
					});
				} catch (Throwable ex) {
					LOG.error("Creating clustered Vertx failed. ", ex);
					ExceptionUtils.rethrow(ex);
				}
			}).onFailure(ex -> {
				LOG.error("Creating clustered Vertx failed. ", ex);
				ExceptionUtils.rethrow(ex);
			});
		};

		Vertx.clusteredVertx(vertxOptions).onSuccess(vertx -> {
			runner.accept(vertx);
		}).onFailure(ex -> {
			LOG.error("Creating clustered Vertx failed. ", ex);
			ExceptionUtils.rethrow(ex);
		});
	}

	/**	
	 *	This is called by Vert.x when the verticle instance is deployed. 
	 *	Initialize a new site context object for storing information about the entire site in English. 
	 *	Setup the startPromise to handle the configuration steps and starting the server. 
	 **/
	@Override()
	public void  start(Promise<Void> startPromise) throws Exception, Exception {

		try {
			Future<Void> promiseSteps = configureWebClient().compose(a ->
				configureData().compose(b -> 
					configureOpenApi().compose(d -> 
						configureHealthChecks().compose(e -> 
							configureSharedWorkerExecutor().compose(f -> 
								configureWebsockets().compose(g -> 
									configureEmail().compose(h -> 
										configureApi().compose(i -> 
											configureUi().compose(j -> 
												startServer()
											)
										)
									)
								)
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
	private Future<Void> configureWebClient() {
		Promise<Void> promise = Promise.promise();

		try {
			webClient = WebClient.create(vertx);
			promise.complete();
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
			pgOptions.setPort(config().getInteger(ConfigKeys.JDBC_PORT));
			pgOptions.setHost(config().getString(ConfigKeys.JDBC_HOST));
			pgOptions.setDatabase(config().getString(ConfigKeys.JDBC_DATABASE));
			pgOptions.setUser(config().getString(ConfigKeys.JDBC_USERNAME));
			pgOptions.setPassword(config().getString(ConfigKeys.JDBC_PASSWORD));
			pgOptions.setIdleTimeout(config().getInteger(ConfigKeys.JDBC_MAX_IDLE_TIME, 10));
			pgOptions.setIdleTimeoutUnit(TimeUnit.SECONDS);
			pgOptions.setConnectTimeout(config().getInteger(ConfigKeys.JDBC_CONNECT_TIMEOUT, 5));

			PoolOptions poolOptions = new PoolOptions();
			jdbcMaxPoolSize = config().getInteger(ConfigKeys.JDBC_MAX_POOL_SIZE, 1);
			jdbcMaxWaitQueueSize = config().getInteger(ConfigKeys.JDBC_MAX_WAIT_QUEUE_SIZE, 10);
			poolOptions.setMaxSize(jdbcMaxPoolSize);
			poolOptions.setMaxWaitQueueSize(jdbcMaxWaitQueueSize);

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
	 * Val.Error.enUS:Could not configure the auth server and API. 
	 * Val.Success.enUS:The auth server and API was configured successfully. 
	 * 
	 *	Configure the connection to the auth server and setup the routes based on the OpenAPI definition. 
	 *	Setup a callback route when returning from the auth server after successful authentication. 
	 *	Setup a logout route for logging out completely of the application. 
	 *	Return a promise that configures the authentication server and OpenAPI. 
	 **/
	private Future<Void> configureOpenApi() {
		Promise<Void> promise = Promise.promise();
		try {
			String siteBaseUrl = config().getString(ConfigKeys.SITE_BASE_URL);

			OAuth2Options oauth2ClientOptions = new OAuth2Options();
			Boolean authSsl = config().getBoolean(ConfigKeys.AUTH_SSL);
			String authHostName = config().getString(ConfigKeys.AUTH_HOST_NAME);
			Integer authPort = config().getInteger(ConfigKeys.AUTH_PORT);
			String authUrl = String.format("%s://%s%s/auth", (authSsl ? "https" : "http"), authHostName, (authPort == 443 || authPort == 80 ? "" : ":" + authPort));
			oauth2ClientOptions.setSite(authUrl + "/realms/" + config().getString(ConfigKeys.AUTH_REALM));
			oauth2ClientOptions.setTenant(config().getString(ConfigKeys.AUTH_REALM));
			oauth2ClientOptions.setClientID(config().getString(ConfigKeys.AUTH_RESOURCE));
			oauth2ClientOptions.setClientSecret(config().getString(ConfigKeys.AUTH_SECRET));
			oauth2ClientOptions.setFlow(OAuth2FlowType.AUTH_CODE);
			JsonObject extraParams = new JsonObject();
			extraParams.put("scope", "DefaultAuthScope");
			oauth2ClientOptions.setExtraParameters(extraParams);

			OpenIDConnectAuth.discover(vertx, oauth2ClientOptions, a -> {
				if(a.succeeded()) {
					oauth2AuthenticationProvider = a.result();

					authorizationProvider = KeycloakAuthorization.create();

					OAuth2AuthHandler oauth2AuthHandler = OAuth2AuthHandler.create(vertx, oauth2AuthenticationProvider, siteBaseUrl + "/callback");
					{
						Router tempRouter = Router.router(vertx);
						oauth2AuthHandler.setupCallback(tempRouter.get("/callback"));
					}
			
//					ClusteredSessionStore sessionStore = ClusteredSessionStore.create(vertx);
					LocalSessionStore sessionStore = LocalSessionStore.create(vertx, "opendatapolicing-sessions");
					SessionHandler sessionHandler = SessionHandler.create(sessionStore);
					if(StringUtils.startsWith(siteBaseUrl, "https://"))
						sessionHandler.setCookieSecureFlag(true);
			
					RouterBuilder.create(vertx, "webroot/openapi3-enUS.yaml", b -> {
						if (b.succeeded()) {
							RouterBuilder routerBuilder = b.result();
							routerBuilder.mountServicesFromExtensions();
			
							routerBuilder.serviceExtraPayloadMapper(routingContext -> new JsonObject()
									.put("uri", routingContext.request().uri())
									.put("method", routingContext.request().method().name())
									);
							routerBuilder.rootHandler(sessionHandler);
							routerBuilder.securityHandler("openIdConnect", oauth2AuthHandler);
							routerBuilder.operation("callback").handler(ctx -> {
			
								// Handle the callback of the flow
								final String code = ctx.request().getParam("code");
			
								// code is a require value
								if (code == null) {
									ctx.fail(400);
									return;
								}
			
								final String state = ctx.request().getParam("state");
			
								final JsonObject config = new JsonObject().put("code", code);
			
								config.put("redirect_uri", siteBaseUrl + "/callback");
			
								oauth2AuthenticationProvider.authenticate(config, res -> {
									if (res.failed()) {
										LOG.error("Failed to authenticate user. ", res.cause());
										ctx.fail(res.cause());
									} else {
										ctx.setUser(res.result());
										Session session = ctx.session();
										if (session != null) {
											// the user has upgraded from unauthenticated to authenticated
											// session should be upgraded as recommended by owasp
											Cookie cookie = Cookie.cookie("sessionIdBefore", session.id());
											if(StringUtils.startsWith(siteBaseUrl, "https://"))
												cookie.setSecure(true);
											ctx.addCookie(cookie);
											session.regenerateId();
											String redirectUri = session.get("redirect_uri");
											// we should redirect the UA so this link becomes invalid
											ctx.response()
													// disable all caching
													.putHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
													.putHeader("Pragma", "no-cache")
													.putHeader(HttpHeaders.EXPIRES, "0")
													// redirect (when there is no state, redirect to home
													.putHeader(HttpHeaders.LOCATION, redirectUri != null ? redirectUri : "/")
													.setStatusCode(302)
													.end("Redirecting to " + (redirectUri != null ? redirectUri : "/") + ".");
										} else {
											// there is no session object so we cannot keep state
											ctx.reroute(state != null ? state : "/");
										}
									}
								});
							});
							routerBuilder.operation("callback").failureHandler(c -> {});
			
							routerBuilder.operation("logout").handler(rc -> {
								String redirectUri = rc.request().params().get("redirect_uri");
								if(redirectUri == null)
									redirectUri = "/";
								rc.clearUser();
								rc.response()
										.putHeader(HttpHeaders.LOCATION, redirectUri)
										.setStatusCode(302)
										.end("Redirecting to " + redirectUri + ".");
							});
							routerBuilder.operation("logout").handler(c -> {});
			
							router = routerBuilder.createRouter();
			
							LOG.info(configureOpenApiSuccess);
							promise.complete();
						} else {
							LOG.error(configureOpenApiError, b.cause());
							promise.fail(b.cause());
						}
					});
				} else {
					LOG.error(configureOpenApiError, a.cause());
					promise.fail(a.cause());
				}
			});
		} catch (Exception ex) {
			LOG.error(configureOpenApiError, ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	/**
	 * 
	 * Val.Fail.enUS:Could not configure the shared worker executor. 
	 * Val.Complete.enUS:The shared worker executor "{}" was configured successfully. 
	 * 
	 *	Configure a shared worker executor for running blocking tasks in the background. 
	 *	Return a promise that configures the shared worker executor. 
	 **/
	private Future<Void> configureSharedWorkerExecutor() {
		Promise<Void> promise = Promise.promise();
		try {
			String name = "MainVerticle-WorkerExecutor";
			Integer workerPoolSize = System.getenv(ConfigKeys.WORKER_POOL_SIZE) == null ? 5 : Integer.parseInt(System.getenv(ConfigKeys.WORKER_POOL_SIZE));
			workerExecutor = vertx.createSharedWorkerExecutor(name, workerPoolSize);
			LOG.info(configureSharedWorkerExecutorComplete, name);
			promise.complete();
		} catch (Exception ex) {
			LOG.error(configureSharedWorkerExecutorFail, ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	/**	
	 * Val.Complete.enUS:The health checks were configured successfully. 
	 * Val.Fail.enUS:Could not configure the health checks. 
	 * Val.ErrorDatabase.enUS:The database is not configured properly. 
	 * Val.EmptySolr.enUS:The Solr search engine is empty. 
	 * Val.ErrorSolr.enUS:The Solr search engine is not configured properly. 
	 * Val.ErrorVertx.enUS:The Vert.x application is not configured properly. 
	 *	Configure health checks for the status of the website and it's dependent services. 
	 *	Return a promise that configures the health checks. 
	 **/
	private Future<Void> configureHealthChecks() {
		Promise<Void> promise = Promise.promise();
		try {
			HealthCheckHandler healthCheckHandler = HealthCheckHandler.create(vertx);
			siteInstances = Optional.ofNullable(System.getenv(ConfigKeys.SITE_INSTANCES)).map(s -> Integer.parseInt(s)).orElse(1);
			workerPoolSize = System.getenv(ConfigKeys.WORKER_POOL_SIZE) == null ? null : Integer.parseInt(System.getenv(ConfigKeys.WORKER_POOL_SIZE));

			healthCheckHandler.register("database", 2000, a -> {
				pgPool.preparedQuery("select current_timestamp").execute(selectCAsync -> {
					if(selectCAsync.succeeded()) {
						a.complete(Status.OK(new JsonObject().put("jdbcMaxPoolSize", jdbcMaxPoolSize).put("jdbcMaxWaitQueueSize", jdbcMaxWaitQueueSize)));
					} else {
						LOG.error(configureHealthChecksErrorDatabase, a.future().cause());
						promise.fail(a.future().cause());
					}
				});
			});
			healthCheckHandler.register("solr", 2000, a -> {
				SolrQuery query = new SolrQuery();
				query.setQuery("*:*");
				try {
					String solrHostName = config().getString(ConfigKeys.SOLR_HOST_NAME);
					Integer solrPort = config().getInteger(ConfigKeys.SOLR_PORT);
					String solrCollection = config().getString(ConfigKeys.SOLR_COLLECTION);
					String solrRequestUri = String.format("/solr/%s/select%s", solrCollection, query.toQueryString());
					webClient.get(solrPort, solrHostName, solrRequestUri).send().onSuccess(b -> {
						try {
							a.complete(Status.OK());
						} catch(Exception ex) {
							LOG.error("Could not read response from Solr. ", ex);
							a.fail(ex);
						}
					}).onFailure(ex -> {
						LOG.error(String.format("Solr request failed. "), new RuntimeException(ex));
						a.fail(ex);
					});
				} catch (Exception e) {
					LOG.error(configureHealthChecksErrorSolr, a.future().cause());
					a.fail(a.future().cause());
				}
			});
			healthCheckHandler.register("vertx", 2000, a -> {
				a.complete(Status.OK(new JsonObject().put(ConfigKeys.SITE_INSTANCES, siteInstances).put("workerPoolSize", workerPoolSize)));
			});
			router.get("/health").handler(healthCheckHandler);
			LOG.info(configureHealthChecksComplete);
			promise.complete();
		} catch (Exception ex) {
			LOG.error(configureHealthChecksFail, ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	/**	
	 * Configure websockets for realtime messages. 
	 * Val.Complete.enUS:Configure websockets succeeded. 
	 * Val.Fail.enUS:Configure websockets failed. 
	 **/
	private Future<Void> configureWebsockets() {
		Promise<Void> promise = Promise.promise();
		try {
			SockJSBridgeOptions options = new SockJSBridgeOptions()
					.addOutboundPermitted(new PermittedOptions().setAddressRegex("websocket.*"));
			SockJSHandler sockJsHandler = SockJSHandler.create(vertx);
			sockJsHandler.bridge(options);
			router.route("/eventbus/*").handler(sockJsHandler);
			LOG.info(configureWebsocketsComplete);
			promise.complete();
		} catch (Exception ex) {
			LOG.error(configureWebsocketsFail, ex);
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
			String emailHost = config().getString(ConfigKeys.EMAIL_HOST);
			if(StringUtils.isNotBlank(emailHost)) {
				MailConfig mailConfig = new MailConfig();
				mailConfig.setHostname(emailHost);
				mailConfig.setPort(config().getInteger(ConfigKeys.EMAIL_PORT));
				mailConfig.setSsl(config().getBoolean(ConfigKeys.EMAIL_SSL));
				mailConfig.setUsername(config().getString(ConfigKeys.EMAIL_USERNAME));
				mailConfig.setPassword(config().getString(ConfigKeys.EMAIL_PASSWORD));
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
	 * Val.Fail.enUS:The API was not configured properly. 
	 * Val.Complete.enUS:The API was configured properly. 
	 */
	private Future<Void> configureApi() {
		Promise<Void> promise = Promise.promise();
		try {
			SiteUserEnUSGenApiService.registerService(vertx.eventBus(), config(), workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider, vertx);
			SiteStateEnUSGenApiService.registerService(vertx.eventBus(), config(), workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider, vertx);
			SiteAgencyEnUSGenApiService.registerService(vertx.eventBus(), config(), workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider, vertx);
			SearchBasisEnUSGenApiService.registerService(vertx.eventBus(), config(), workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider, vertx);
			TrafficContrabandEnUSGenApiService.registerService(vertx.eventBus(), config(), workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider, vertx);
			TrafficPersonEnUSGenApiService.registerService(vertx.eventBus(), config(), workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider, vertx);
			TrafficSearchEnUSGenApiService.registerService(vertx.eventBus(), config(), workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider, vertx);
			TrafficStopEnUSGenApiService.registerService(vertx.eventBus(), config(), workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider, vertx);

			LOG.info(configureApiComplete);
			promise.complete();
		} catch(Exception ex) {
			LOG.error(configureApiFail, ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	/**
	 * Val.Fail.enUS:The UI was not configured properly. 
	 * Val.Complete.enUS:The UI was configured properly. 
	 */
	private Future<Void> configureUi() {
		Promise<Void> promise = Promise.promise();
		try {
			String staticPath = config().getString(ConfigKeys.STATIC_PATH);
			String staticBaseUrl = config().getString(ConfigKeys.STATIC_BASE_URL);
			String siteBaseUrl = config().getString(ConfigKeys.SITE_BASE_URL);
			HandlebarsTemplateEngine engine = HandlebarsTemplateEngine.create(vertx);
			Handlebars handlebars = (Handlebars)engine.unwrap();
			TemplateHandler templateHandler = TemplateHandler.create(engine, staticPath + "/template", "text/html");

			handlebars.registerHelper("urlencode", (Helper<String>) (value, options) -> {
				try {
					return URLEncoder.encode(value, "UTF-8");
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			});
			handlebars.registerHelpers(ConditionalHelpers.class);
			handlebars.registerHelpers(StringHelpers.class);

			router.get("/").handler(a -> {
				a.reroute("/template/home-page");
			});

			router.get("/agency").handler(ctx -> {
				putVarsInRoutingContext(ctx);
				ctx.reroute("/template/agency-search");
			});

			router.get("/template/agency-search").handler(ctx -> {
				try {
					SiteRequestEnUS siteRequest = new SiteRequestEnUS();
					siteRequest.setWebClient(webClient);
					siteRequest.setConfig(config());
					siteRequest.initDeepSiteRequestEnUS(siteRequest);
	
					List<String> urlParams = new ArrayList<String>();
					Long rows = 1000L;
					SearchList<SiteAgency> agencySearch = new SearchList<SiteAgency>();
					agencySearch.setStore(true);
					agencySearch.setQuery("*:*");
					agencySearch.setC(SiteAgency.class);
					agencySearch.setRows(rows.intValue());
					agencySearch.setFields(Arrays.asList(
							"agencyName_stored_string"
							, "stateAbbreviation_stored_string"
							, "pk_stored_long"
							));
					agencySearch.addSort(SortClause.asc("agencyTitle_indexed_string"));
					agencySearch.promiseDeepForClass(siteRequest).onSuccess(b -> {
						JsonObject agencyLettersMap = new JsonObject();
						JsonArray agencyLettersArray = new JsonArray();
						JsonObject agencyLetter = null;
						JsonArray agencies = null;
						String ch = null;
						for(SiteAgency agency : agencySearch.getList()) {
							String agencyTitle = agency.getAgencyTitle();
							if(StringUtils.isNotBlank(agencyTitle)) {
								ch = agencyTitle.substring(0, 1);
								if(agencyLettersMap.containsKey(ch)) {
									agencyLetter = agencyLettersMap.getJsonObject(ch);
								}
								else {
									agencies = new JsonArray();
									agencyLetter = new JsonObject();
									agencyLetter.put("letter", ch);
									agencyLetter.put("agencies", agencies);
									agencyLettersMap.put(ch, agencyLetter);
									agencyLettersArray.add(agencyLetter);
								}
								JsonObject json = new JsonObject();
								json.put("pk", agency.getPk());
								json.put("stateAbbreviation", agency.getStateAbbreviation());
								json.put("agencyTitle", agencyTitle);
								agencies.add(json);
							}
						}
						ctx.put("agencyLetters", agencyLettersArray);
						ctx.next();
					}).onFailure(ex -> {
						LOG.error("Stop search page failed to load stop data. ", ex);
						ctx.fail(ex);
					});
				} catch(Exception ex) {
					LOG.error("Stop search page failed to load stop data. ", ex);
					ctx.fail(ex);
				}
			});

			router.get("/stop").handler(ctx -> {
				putVarsInRoutingContext(ctx);
				ctx.reroute("/template/stop-search");
			});

			router.get("/template/stop-search").handler(ctx -> {
				try {
					SiteRequestEnUS siteRequest = new SiteRequestEnUS();
					siteRequest.setWebClient(webClient);
					siteRequest.setConfig(config());
					siteRequest.initDeepSiteRequestEnUS(siteRequest);
	
					SearchList<TrafficStop> stopSearch1 = new SearchList<TrafficStop>();
					stopSearch1.setStore(true);
					stopSearch1.setQuery("*:*");
					stopSearch1.setC(TrafficStop.class);
					stopSearch1.setRows(1);
					stopSearch1.addSort(SortClause.asc("stopDateTime_indexed_date"));
					stopSearch1.addFacetField("stopYear_indexed_int");
					stopSearch1.addFacetField("personRaceTitles_indexed_strings");
					stopSearch1.addFacetField("stopPurposeTitle_indexed_string");
					stopSearch1.addFacetField("stopActionTitle_indexed_string");
	//				stopSearch1.addFacetField("personGenderTitles_indexed_strings");
	//				stopSearch1.addFacetField("personAges_indexed_ints");
					stopSearch1.promiseDeepForClass(siteRequest).onSuccess(b -> {
						List<String> urlParams = new ArrayList<String>();
						Integer startYear = LocalDateTime.now().getYear();
						TrafficStop first = stopSearch1.first();
						if(first != null)
							startYear = first.getStopDateTime().getYear();
						ctx.put("startYear", startYear);
						SearchList<TrafficStop> stopSearch2 = new SearchList<TrafficStop>();
						stopSearch2.setStore(true);
						stopSearch2.setQuery("*:*");
						stopSearch2.setSort("stopDateTime_indexed_date", ORDER.desc);
						stopSearch2.setFields(Arrays.asList(
								"stopDateTime_stored_string"
								, "personGenderTitles_stored_strings"
								, "personRaceTitles_stored_strings"
								, "personAges_stored_ints"
								, "agencyTitle_stored_string"
								, "stopOfficerId_stored_string"
								, "pk_stored_long"
								));
						stopSearch2.setFacet(true);
		
						ctx.put("siteZone", config().getValue(ConfigKeys.SITE_ZONE));
	
						Long rows = 100L;
						stopSearch2.setRows(rows.intValue());
	
						Long start = Optional.ofNullable((String)ctx.get("start")).map(s -> Long.valueOf(s)).orElse(0L);
						stopSearch2.setStart(start.intValue());
	
						DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
						String startDateParam = ctx.get("startDate");
						String endDateParam = ctx.get("endDate");
						if(startDateParam != null && endDateParam != null) {
							ZonedDateTime startDate = TrafficStop.staticSetStopDateTime(siteRequest, startDateParam);
							ZonedDateTime endDate = TrafficStop.staticSetStopDateTime(siteRequest, endDateParam);
							ctx.put("startDateStr", startDate.format(dateFormatter));
							ctx.put("endDateStr", endDate.format(dateFormatter));
							stopSearch2.addFilterQuery("stopDateTime_indexed_date:[" + TrafficStop.staticSolrStrStopDateTime(siteRequest, TrafficStop.staticSolrStopDateTime(siteRequest, startDate)) + " TO " + TrafficStop.staticSolrStrStopDateTime(siteRequest, TrafficStop.staticSolrStopDateTime(siteRequest, endDate)) + "]");
							urlParams.add("var=startDate:" + startDateParam);
							urlParams.add("var=endDate:" + endDateParam);
						} else if(startDateParam == null && endDateParam != null) {
							ZonedDateTime endDate = TrafficStop.staticSetStopDateTime(siteRequest, endDateParam);
							ctx.put("endDateStr", endDate.format(dateFormatter));
							stopSearch2.addFilterQuery("stopDateTime_indexed_date:[* TO " + TrafficStop.staticSolrStrStopDateTime(siteRequest, TrafficStop.staticSolrStopDateTime(siteRequest, endDate)) + "]");
							urlParams.add("var=endDate:" + endDateParam);
						} else if(startDateParam != null && endDateParam == null) {
							ZonedDateTime startDate = TrafficStop.staticSetStopDateTime(siteRequest, startDateParam);
							ctx.put("startDateStr", startDate.format(dateFormatter));
							stopSearch2.addFilterQuery("stopDateTime_indexed_date:[" + TrafficStop.staticSolrStrStopDateTime(siteRequest, TrafficStop.staticSolrStopDateTime(siteRequest, startDate)) + " TO *]");
							urlParams.add("var=startDate:" + startDateParam);
						}
		
						Optional.ofNullable((String)ctx.get("stateAbbreviation")).ifPresent(stateAbbreviation -> {
							try {
								stopSearch2.addFilterQuery("stateAbbreviation_indexed_string:" + ClientUtils.escapeQueryChars(stateAbbreviation));
								urlParams.add("var=stateAbbreviation:" + URLEncoder.encode(stateAbbreviation, "UTF-8"));
							} catch (UnsupportedEncodingException ex) {
								ExceptionUtils.rethrow(ex);
							}
						});
						Optional.ofNullable((String)ctx.get("agencyTitle")).ifPresent(agencyTitle -> {
							try {
								stopSearch2.addFilterQuery("agencyTitle_indexed_string:" + ClientUtils.escapeQueryChars(agencyTitle));
								urlParams.add("var=agencyTitle:" + URLEncoder.encode(agencyTitle, "UTF-8"));
							} catch (UnsupportedEncodingException ex) {
								ExceptionUtils.rethrow(ex);
							}
						});
						Optional.ofNullable((String)ctx.get("stopOfficerId")).ifPresent(stopOfficerId -> {
							try {
								stopSearch2.addFilterQuery("stopOfficerId_indexed_string:" + ClientUtils.escapeQueryChars(stopOfficerId));
								urlParams.add("var=stopOfficerId:" + URLEncoder.encode(stopOfficerId, "UTF-8"));
							} catch (UnsupportedEncodingException ex) {
								ExceptionUtils.rethrow(ex);
							}
						});
						Optional.ofNullable((String)ctx.get("race")).ifPresent(race -> {
							try {
								stopSearch2.addFilterQuery("personRaceTitles_indexed_strings:" + ClientUtils.escapeQueryChars(race));
								urlParams.add("var=race:" + URLEncoder.encode(race, "UTF-8"));
							} catch (UnsupportedEncodingException ex) {
								ExceptionUtils.rethrow(ex);
							}
						});
						Optional.ofNullable((String)ctx.get("gender")).ifPresent(gender -> {
							try {
								stopSearch2.addFilterQuery("personGenderTitles_indexed_strings:" + ClientUtils.escapeQueryChars(gender));
								urlParams.add("var=gender:" + URLEncoder.encode(gender, "UTF-8"));
							} catch (UnsupportedEncodingException ex) {
								ExceptionUtils.rethrow(ex);
							}
						});
						Optional.ofNullable((String)ctx.get("age")).ifPresent(age -> {
							try {
								stopSearch2.addFilterQuery("personAges_indexed_ints:" + ClientUtils.escapeQueryChars(age));
								urlParams.add("var=age:" + URLEncoder.encode(age, "UTF-8"));
							} catch (UnsupportedEncodingException ex) {
								ExceptionUtils.rethrow(ex);
							}
						});
	
						JsonArray personPurposeTitles = new JsonArray();
						stopSearch1.getQueryResponse().getFacetFields().stream().filter(facetField -> "stopPurposeTitle_indexed_string".equals(facetField.getName())).findFirst().ifPresent(facetField -> {
							List<String> fqValues = new ArrayList<>();
							facetField.getValues().forEach(value -> {
								JsonObject json = new JsonObject();
								String id = toId(value.getName());
								Boolean checked = ctx.get("purpose-" + id) != null;
								json.put("value", value.getName());
								json.put("id", id);
								json.put("checked", checked);
								personPurposeTitles.add(json);
								if(checked) {
									fqValues.add(value.getName());
									urlParams.add(String.format("var=purpose-%s:true", id));
								}
							});
							if(fqValues.size() > 0) {
								stopSearch2.addFilterQuery("stopPurposeTitle_indexed_string:(\"" + StringUtils.join(fqValues, "\" OR \"") + "\")");
							}
						});
						ctx.put("personPurposeTitles", personPurposeTitles);
	
						JsonArray personActionTitles = new JsonArray();
						stopSearch1.getQueryResponse().getFacetFields().stream().filter(facetField -> "stopActionTitle_indexed_string".equals(facetField.getName())).findFirst().ifPresent(facetField -> {
							List<String> fqValues = new ArrayList<>();
							facetField.getValues().forEach(value -> {
								JsonObject json = new JsonObject();
								String id = toId(value.getName());
								Boolean checked = ctx.get("action-" + id) != null;
								json.put("value", value.getName());
								json.put("id", id);
								json.put("checked", checked);
								personActionTitles.add(json);
								if(checked) {
									fqValues.add(value.getName());
									urlParams.add(String.format("var=action-%s:true", id));
								}
							});
							if(fqValues.size() > 0) {
								stopSearch2.addFilterQuery("stopActionTitle_indexed_string:(\"" + StringUtils.join(fqValues, "\" OR \"") + "\")");
							}
						});
						ctx.put("personActionTitles", personActionTitles);
		
						stopSearch2.setC(TrafficStop.class);
						stopSearch2.promiseDeepForClass(siteRequest).onSuccess(c -> {
							Long numFound = stopSearch2.getQueryResponse().getResults().getNumFound();
							Long startNum = start + 1;
							Long endNum = start + rows;
							endNum = endNum < numFound ? endNum : numFound;
							startNum = numFound == 0L ? 0L : startNum;
							JsonObject pagination = new JsonObject();

							{
								JsonObject page = new JsonObject();
								page.put("url", String.format("/stop?%s&var=start:%s", StringUtils.join(urlParams, "&"), 0));
								page.put("currentPage", start.equals(0L));
								pagination.put("pageStart", page);
							}

							if(start > 0L) {
								JsonObject page = new JsonObject();
								page.put("url", String.format("/stop?%s&var=start:%s", StringUtils.join(urlParams, "&"), start - rows));
								pagination.put("pagePrev", page);
							}

							if((start + rows) < numFound) {
								JsonObject page = new JsonObject();
								page.put("url", String.format("/stop?%s&var=start:%s", StringUtils.join(urlParams, "&"), start + rows));
								pagination.put("pageNext", page);
							}

							{
								JsonObject page = new JsonObject();
								Long floorMod = Math.floorMod(numFound, rows);
								Long last = (Math.floorDiv(numFound, rows) - (floorMod.equals(0L) ? 1L : 0L)) * rows;
								page.put("currentPage", start.equals(last));
								page.put("url", String.format("/stop?%s&var=start:%s", StringUtils.join(urlParams, "&"), last));
								pagination.put("pageEnd", page);
							}

							Long paginationStart = start - 10 * rows;
							if(paginationStart < 0)
								paginationStart = 0L;
							Long paginationEnd = start + 10 * rows;
							if(paginationEnd > numFound)
								paginationEnd = numFound;
							pagination.put("startNum", startNum);
							pagination.put("endNum", endNum);
							pagination.put("numFound", numFound);
							JsonArray pages = new JsonArray();
							for(Long i = paginationStart; i < paginationEnd; i += rows) {
								Long pageNumber = Math.floorDiv(i, rows) + 1L;
								JsonObject page = new JsonObject();
								page.put("pageNumber", pageNumber);
								page.put("currentPage", start.equals(i));
								page.put("start", i);
								page.put("url", String.format("/stop?%s&var=start:%s", StringUtils.join(urlParams, "&"), i));
								pages.add(page);
							}
							pagination.put("pages", pages);
							ctx.put("pagination", pagination);
	
							ctx.put("searchTotal", NumberFormat.getNumberInstance(Locale.US).format(numFound));
		
							JsonArray personRaceTitles = new JsonArray();
							stopSearch1.getQueryResponse().getFacetFields().stream().filter(facetField -> "personRaceTitles_indexed_strings".equals(facetField.getName())).findFirst().ifPresent(facetField -> {
								facetField.getValues().forEach(value -> {
									personRaceTitles.add(value.getName());
								});
							});
							ctx.put("personRaceTitles", personRaceTitles);
		
							JsonArray personGenderTitles = new JsonArray();
							stopSearch1.getQueryResponse().getFacetFields().stream().filter(facetField -> "personGenderTitles_indexed_strings".equals(facetField.getName())).findFirst().ifPresent(facetField -> {
								facetField.getValues().forEach(value -> {
									personGenderTitles.add(value.getName());
								});
							});
							ctx.put("personGenderTitles", personGenderTitles);
		
							JsonArray personAges = new JsonArray();
							stopSearch1.getQueryResponse().getFacetFields().stream().filter(facetField -> "personAges_indexed_ints".equals(facetField.getName())).findFirst().ifPresent(facetField -> {
								facetField.getValues().forEach(value -> {
									personAges.add(value.getName());
								});
							});
							ctx.put("personAges", personAges);
		
							JsonArray stopsJson = new JsonArray();
							stopSearch2.getList().stream().forEach(stop -> {
								JsonObject stopJson = new JsonObject();
								stopJson.put("pk", stop.getPk());
								stopJson.put("stopDateTime", stop.strStopDateTime());
								stopJson.put("personGenderTitles", stop.getPersonGenderTitles());
								stopJson.put("personRaceTitles", stop.getPersonRaceTitles());
								stopJson.put("personAges", stop.getPersonAges());
								stopJson.put("stateAbbreviation", stop.getStateAbbreviation());
								stopJson.put("agencyTitle", stop.getAgencyTitle());
								stopJson.put("stopPurposeTitle", stop.getStopPurposeTitle());
								stopJson.put("stopActionTitle", stop.getStopActionTitle());
								stopJson.put("stopOfficerId", stop.getStopOfficerId());
								stopJson.put("searchStart", start);
								stopJson.put("searchRows", rows);
								stopsJson.add(stopJson);
							});
							ctx.put("stops", stopsJson);
							ctx.next();
						}).onFailure(ex -> {
							LOG.error("Stop search page failed to load stop data. ", ex);
							ctx.fail(ex);
						});
					}).onFailure(ex -> {
						LOG.error("Stop search page failed to load stop data. ", ex);
						ctx.fail(ex);
					});
				} catch(Exception ex) {
					LOG.error("Stop search page failed to load stop data. ", ex);
					ctx.fail(ex);
				}
			});

			router.get("/template/home-page").handler(ctx -> {
				SiteRequestEnUS siteRequest = new SiteRequestEnUS();
				siteRequest.setWebClient(webClient);
				siteRequest.setConfig(config());
				siteRequest.initDeepSiteRequestEnUS(siteRequest);

				SearchList<SiteState> stateSearch = new SearchList<SiteState>();
				stateSearch.setStore(true);
				stateSearch.setQuery("*:*");
				stateSearch.setC(SiteState.class);
				stateSearch.addFilterQuery("stateAbbreviation_indexed_string:[* TO *]");
				stateSearch.promiseDeepForClass(siteRequest).onSuccess(b -> {
					JsonArray states = new JsonArray();
					ctx.put("states", states);

					SearchList<TrafficStop> stopSearch = new SearchList<TrafficStop>();
					stopSearch.setStore(true);
					stopSearch.setQuery("*:*");
					stopSearch.setC(TrafficStop.class);
					stopSearch.addFacetField("stateAbbreviation_indexed_string");
					stopSearch.promiseDeepForClass(siteRequest).onSuccess(c -> {
						stateSearch.getList().stream().forEach(state -> {
							String stateAbbreviation = state.getStateAbbreviation();
							Count count = stopSearch.getQueryResponse().getFacetField("stateAbbreviation_indexed_string").getValues().stream().filter(count1 -> count1.getName().equals(stateAbbreviation)).findFirst().orElse(null);
							if(count != null) {
								Long stopCount = count.getCount();
								String stopCountStr;
								if(stopCount > 1000000000)
									stopCountStr = new Double(Math.floor(stopCount / 1000000000)).intValue() + "+ billion";
								else if(stopCount > 1000000)
									stopCountStr = new Double(Math.floor(stopCount / 1000000)).intValue() + "+ million";
								else if(stopCount > 1000)
									stopCountStr = new Double(Math.floor(stopCount / 1000)).intValue() + "+ thousand";
								else
									stopCountStr = stopCount.toString();
		
								JsonObject stateJson = new JsonObject();
								stateJson.put("stateAbbreviation", stateAbbreviation);
								stateJson.put("stateName", state.getStateName());
								stateJson.put("objectId", state.getObjectId());
								stateJson.put("stopCountStr", stopCountStr);
								states.add(stateJson);
							}
						});
						ctx.next();
					}).onFailure(ex -> {
						LOG.error("Home page failed to load state data. ", ex);
						ctx.fail(ex);
					});
				}).onFailure(ex -> {
					LOG.error("Home page failed to load state data. ", ex);
					ctx.fail(ex);
				});
			});

			router.get("/state/:stateId").handler(ctx -> {
				ctx.put("stateId", ctx.pathParam("stateId"));
				ctx.reroute("/template/state-page");
			});

			router.get("/template/state-page").handler(ctx -> {
				JsonArray agencyFacets = new JsonArray();
				ctx.put("agencyFacets", agencyFacets);
				String stateId = ctx.get("stateId");
				SiteRequestEnUS siteRequest = new SiteRequestEnUS();
				siteRequest.setWebClient(webClient);
				siteRequest.setConfig(config());
				siteRequest.initDeepSiteRequestEnUS(siteRequest);

				SearchList<SiteState> stateSearch = new SearchList<SiteState>();
				stateSearch.setStore(true);
				stateSearch.setQuery("*:*");
				stateSearch.setC(SiteState.class);
				stateSearch.addFilterQuery("objectId_indexed_string:" + ClientUtils.escapeQueryChars(stateId));
				stateSearch.promiseDeepForClass(siteRequest).onSuccess(b -> {
					SiteState state = stateSearch.first();
					if(state != null) {
						ctx.put("state", state);
					}

					SearchList<TrafficStop> stopSearch1 = new SearchList<TrafficStop>();
					stopSearch1.setStore(true);
					stopSearch1.setQuery("*:*");
					stopSearch1.setC(TrafficStop.class);
					stopSearch1.setRows(1);
					stopSearch1.addSort(SortClause.asc("stopDateTime_indexed_date"));
					stopSearch1.addFacetField("agencyTitle_indexed_string");
					stopSearch1.addFacetField("personRaceTitles_indexed_strings");
					stopSearch1.add("facet.limit", "20");
					stopSearch1.add("json.facet", "{agencyTitle:\"unique(agencyTitle_indexed_string)\"}");
					stopSearch1.promiseDeepForClass(siteRequest).onSuccess(c -> {
						TrafficStop beginStop = stopSearch1.first();
						SearchList<TrafficStop> stopSearch2 = new SearchList<TrafficStop>();
						stopSearch2.setStore(true);
						stopSearch2.setQuery("*:*");
						stopSearch2.setC(TrafficStop.class);
						stopSearch2.setRows(1);
						stopSearch2.addSort(SortClause.desc("stopDateTime_indexed_date"));
						stopSearch2.promiseDeepForClass(siteRequest).onSuccess(d -> {
							TrafficStop endStop = stopSearch2.first();
							SearchList<TrafficSearch> searchSearch = new SearchList<TrafficSearch>();
							searchSearch.setStore(true);
							searchSearch.setQuery("*:*");
							searchSearch.setC(TrafficSearch.class);
							searchSearch.setRows(0);
							searchSearch.promiseDeepForClass(siteRequest).onSuccess(e -> {
								ctx.put("beginStopDateTime", beginStop.getStopDateTime().format(LocalDateSerializer.FORMATDateSite));
								ctx.put("endStopDateTime", endStop.getStopDateTime().format(LocalDateSerializer.FORMATDateSite));
								ctx.put("stopCount", NumberFormat.getNumberInstance(Locale.US).format(stopSearch1.getQueryResponse().getResults().getNumFound()));
								ctx.put("searchCount", NumberFormat.getNumberInstance(Locale.US).format(searchSearch.getQueryResponse().getResults().getNumFound()));
	
								SimpleOrderedMap jsonFacets = (SimpleOrderedMap)Optional.ofNullable(stopSearch1.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(new SimpleOrderedMap());
								Integer agencyCount = (Integer)jsonFacets.get("agencyTitle");
								ctx.put("agencyCount", NumberFormat.getNumberInstance(Locale.US).format(agencyCount));
	
								stopSearch1.getQueryResponse().getFacetFields().stream().filter(facetField -> "agencyTitle_indexed_string".equals(facetField.getName())).findFirst().ifPresent(facetField -> {
									List<Count> counts = facetField.getValues();
									for(Integer i = 0; i < counts.size(); i++) {
										if(i == 5)
											break;
										Count value = counts.get(i);
										agencyFacets.add(
												new JsonObject()
												.put("stateAbbreviation", state.getStateAbbreviation())
												.put("agencyTitle", value.getName())
												.put("stopCount", NumberFormat.getNumberInstance(Locale.US).format(value.getCount()))
												);
									}
								});

								JsonArray personRaceTitles = new JsonArray();
								stopSearch1.getQueryResponse().getFacetFields().stream().filter(facetField -> "personRaceTitles_indexed_strings".equals(facetField.getName())).findFirst().ifPresent(facetField -> {
									facetField.getValues().forEach(value -> {
										personRaceTitles.add(value.getName());
									});
								});
								ctx.put("personRaceTitles", personRaceTitles);
								ctx.next();
							}).onFailure(ex -> {
								LOG.error("Home page failed to load state data. ", ex);
								ctx.fail(ex);
							});
						}).onFailure(ex -> {
							LOG.error("Home page failed to load state data. ", ex);
							ctx.fail(ex);
						});
					}).onFailure(ex -> {
						LOG.error("Home page failed to load state data. ", ex);
						ctx.fail(ex);
					});
				}).onFailure(ex -> {
					LOG.error("Home page failed to load state data. ", ex);
					ctx.fail(ex);
				});
			});

			router.get("/report").handler(ctx -> {
				putVarsInRoutingContext(ctx);
				ctx.put("queryParams", ctx.queryParams());
				ctx.reroute("/template/traffic-stop-report");
			});

			router.get("/template/traffic-stop-report").handler(ctx -> {
				try {
					SiteRequestEnUS siteRequest = new SiteRequestEnUS();
					siteRequest.setWebClient(webClient);
					siteRequest.setConfig(config());
					siteRequest.initDeepSiteRequestEnUS(siteRequest);

					String stopYearStr = (String)ctx.data().get("stopYear");
					ZonedDateTime startDate = null;
					ZonedDateTime endDate = null;
					if(stopYearStr != null) {
						Integer stopYear = Integer.parseInt(stopYearStr);
						startDate = ZonedDateTime.of(stopYear, 1, 1, 0, 0, 0, 0, ZoneId.of(config().getString(ConfigKeys.SITE_ZONE)));
						endDate = ZonedDateTime.of(stopYear, 12, 31, 23, 59, 59, 999999999, ZoneId.of(config().getString(ConfigKeys.SITE_ZONE)));
					} else {
						startDate = ZonedDateTime.of(2002, 1, 1, 0, 0, 0, 0, ZoneId.of(config().getString(ConfigKeys.SITE_ZONE)));
						endDate = ZonedDateTime.now(ZoneId.of(config().getString(ConfigKeys.SITE_ZONE))).with(TemporalAdjusters.firstDayOfYear()).minusNanos(1).minusYears(1);
					}
					String startDateStr = startDate.toInstant().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE_TIME);
					ctx.put("startDateStr", startDateStr);
					String endDateStr = endDate.toInstant().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE_TIME);
					ctx.put("endDateStr", endDateStr);

					SearchList<TrafficStop> stopSearch2 = new SearchList<TrafficStop>();
					stopSearch2.setStore(true);
					stopSearch2.setQuery("*:*");
					stopSearch2.setC(TrafficStop.class);
					stopSearch2.setRows(0);
					stopSearch2.addFacetField("stopYear_indexed_int");
					stopSearch2.addFacetField("personRaceTitles_indexed_strings");
					stopSearch2.addFacetField("stopPurposeTitle_indexed_string");
					stopSearch2.addFacetField("stopActionTitle_indexed_string");
					stopSearch2.addFilterQuery(String.format("stopDateTime_indexed_date:[%s TO %s]", startDateStr, endDateStr));
					stopSearch2.set("facet.mincount", "1");
					stopSearch2.set("facet.sort", "index");
					stopSearch2.set("facet.range.end", endDateStr);
					List<String> fqParamsWithoutYear = new ArrayList<String>();
					List<String> fqParamsWithoutStopPurposeTitle = new ArrayList<String>();
					ctx.put("siteZone", config().getValue(ConfigKeys.SITE_ZONE));
					ctx.put("ACS_API_YEAR", config().getValue(ConfigKeys.ACS_API_YEAR));
					String agencyTitle = ctx.get("agencyTitle");

					if(agencyTitle != null) {
						try {
							stopSearch2.addFilterQuery("agencyTitle_indexed_string:" + ClientUtils.escapeQueryChars(agencyTitle));
							fqParamsWithoutYear.add("var=agencyTitle:" + URLEncoder.encode(agencyTitle, "UTF-8"));
							fqParamsWithoutStopPurposeTitle.add("var=agencyTitle:" + URLEncoder.encode(agencyTitle, "UTF-8"));
						} catch (UnsupportedEncodingException ex) {
							ExceptionUtils.rethrow(ex);
						}
					}
					Optional.ofNullable((String)ctx.get("stateAbbreviation")).ifPresent(stateAbbreviation -> {
						try {
							stopSearch2.addFilterQuery("stateAbbreviation_indexed_string:" + ClientUtils.escapeQueryChars(stateAbbreviation));
							fqParamsWithoutYear.add("var=stateAbbreviation:" + URLEncoder.encode(stateAbbreviation, "UTF-8"));
							fqParamsWithoutStopPurposeTitle.add("var=stateAbbreviation:" + URLEncoder.encode(stateAbbreviation, "UTF-8"));
						} catch (UnsupportedEncodingException ex) {
							ExceptionUtils.rethrow(ex);
						}
					});
					Optional.ofNullable((String)ctx.get("stopPurposeTitle")).ifPresent(stopPurposeTitle -> {
						try {
							stopSearch2.addFilterQuery("stopPurposeTitle_indexed_string:" + ClientUtils.escapeQueryChars(stopPurposeTitle));
							fqParamsWithoutYear.add("var=stopPurposeTitle:" + URLEncoder.encode(stopPurposeTitle, "UTF-8"));
						} catch (UnsupportedEncodingException ex) {
							ExceptionUtils.rethrow(ex);
						}
					});
					Optional.ofNullable((String)ctx.get("stopOfficerId")).ifPresent(stopOfficerId -> {
						try {
							stopSearch2.addFilterQuery("stopOfficerId_indexed_string:" + ClientUtils.escapeQueryChars(stopOfficerId));
							fqParamsWithoutYear.add("var=stopOfficerId:" + URLEncoder.encode(stopOfficerId, "UTF-8"));
							fqParamsWithoutStopPurposeTitle.add("var=stopOfficerId:" + URLEncoder.encode(stopOfficerId, "UTF-8"));
						} catch (UnsupportedEncodingException ex) {
							ExceptionUtils.rethrow(ex);
						}
					});
					Optional.ofNullable((String)ctx.get("race")).ifPresent(race -> {
						try {
							stopSearch2.addFilterQuery("personRaceTitles_indexed_strings:" + ClientUtils.escapeQueryChars(race));
							fqParamsWithoutYear.add("var=race:" + URLEncoder.encode(race, "UTF-8"));
							fqParamsWithoutStopPurposeTitle.add("var=race:" + URLEncoder.encode(race, "UTF-8"));
						} catch (UnsupportedEncodingException ex) {
							ExceptionUtils.rethrow(ex);
						}
					});
					Optional.ofNullable((String)ctx.get("gender")).ifPresent(gender -> {
						try {
							stopSearch2.addFilterQuery("personGenderTitles_indexed_strings:" + ClientUtils.escapeQueryChars(gender));
							fqParamsWithoutYear.add("var=gender:" + URLEncoder.encode(gender, "UTF-8"));
							fqParamsWithoutStopPurposeTitle.add("var=gender:" + URLEncoder.encode(gender, "UTF-8"));
						} catch (UnsupportedEncodingException ex) {
							ExceptionUtils.rethrow(ex);
						}
					});
					Optional.ofNullable((String)ctx.get("age")).ifPresent(age -> {
						try {
							stopSearch2.addFilterQuery("personAges_indexed_ints:" + ClientUtils.escapeQueryChars(age));
							fqParamsWithoutYear.add("var=age:" + URLEncoder.encode(age, "UTF-8"));
							fqParamsWithoutStopPurposeTitle.add("var=age:" + URLEncoder.encode(age, "UTF-8"));
						} catch (UnsupportedEncodingException ex) {
							ExceptionUtils.rethrow(ex);
						}
					});
					Optional.ofNullable((String)ctx.get("stopYear")).ifPresent(stopYear -> {
						try {
							stopSearch2.addFilterQuery("stopYear_indexed_int:" + ClientUtils.escapeQueryChars(stopYear));
							fqParamsWithoutStopPurposeTitle.add("var=stopYear:" + URLEncoder.encode(stopYear, "UTF-8"));
						} catch (Exception ex) {
							ExceptionUtils.rethrow(ex);
						}
					});
					ctx.put("fqParamsWithoutYear", StringUtils.join(fqParamsWithoutYear, "&"));
					ctx.put("fqParamsWithoutStopPurposeTitle", StringUtils.join(fqParamsWithoutStopPurposeTitle, "&"));
	
					SearchList<SiteAgency> agencySearch = new SearchList<SiteAgency>();
					agencySearch.setStore(true);
					agencySearch.setQuery("*:*");
					agencySearch.setC(SiteAgency.class);
					agencySearch.setRows(1);
					if(agencyTitle == null)
						agencySearch.addFilterQuery("agencyTitle_indexed_string:------");
					else
						agencySearch.addFilterQuery("agencyTitle_indexed_string:" + ClientUtils.escapeQueryChars(agencyTitle));
					agencySearch.promiseDeepForClass(siteRequest).onSuccess(c -> {
						SiteAgency agency = agencySearch.first();
						if(agency != null && agency.getAgencyTotal() != null) {
							JsonObject agencyJson = JsonObject.mapFrom(agency);
							Long agencyTotal = agency.getAgencyTotal();

							agencyJson.put("agencyTotalWhite", agency.getAgencyTotalWhite());
							agencyJson.put("agencyTotalBlack", agency.getAgencyTotalBlack());
							agencyJson.put("agencyTotalIndigenous", agency.getAgencyTotalIndigenous());
							agencyJson.put("agencyTotalAsian", agency.getAgencyTotalAsian());
							agencyJson.put("agencyTotalLatinx", agency.getAgencyTotalLatinx());
							agencyJson.put("agencyTotalOther", agency.getAgencyTotalOther());
							agencyJson.put("agencyTotal", agency.getAgencyTotal());

							agencyJson.put("agencyPercentWhite", agency.getAgencyTotalWhite().doubleValue() / agencyTotal.doubleValue() * 100.0);
							agencyJson.put("agencyPercentBlack", agency.getAgencyTotalBlack().doubleValue() / agencyTotal.doubleValue() * 100.0);
							agencyJson.put("agencyPercentIndigenous", agency.getAgencyTotalIndigenous().doubleValue() / agencyTotal.doubleValue() * 100.0);
							agencyJson.put("agencyPercentAsian", agency.getAgencyTotalAsian().doubleValue() / agencyTotal.doubleValue() * 100.0);
							agencyJson.put("agencyPercentLatinx", agency.getAgencyTotalLatinx().doubleValue() / agencyTotal.doubleValue() * 100.0);
							agencyJson.put("agencyPercentOther", agency.getAgencyTotalOther().doubleValue() / agencyTotal.doubleValue() * 100.0);
							ctx.put("agency", agencyJson);
						}

						stopSearch2.setC(TrafficStop.class);
						stopSearch2.promiseDeepForClass(siteRequest).onSuccess(d -> {
							Long numFound = stopSearch2.getQueryResponse().getResults().getNumFound();
	
							ctx.put("searchTotal", NumberFormat.getNumberInstance(Locale.US).format(numFound));
	
							JsonObject stopJson = new JsonObject();
							stopJson.put("foundNum", numFound);
							JsonObject facetFields = new JsonObject();
							stopJson.put("facet_fields", facetFields);
							JsonObject personRaceTitlesFacets = new JsonObject();
							facetFields.put("personRaceTitles", personRaceTitlesFacets);
	//						JsonObject facetRanges = new JsonObject();
	//						stopJson.put("facet_ranges", facetRanges);
	//						JsonObject stopDateTimeFacetRanges = new JsonObject();
	//						facetRanges.put("stopDateTime", stopDateTimeFacetRanges);
	
							JsonArray personPurposeTitles = new JsonArray();
							stopSearch2.getQueryResponse().getFacetFields().stream().filter(facetField -> "stopPurposeTitle_indexed_string".equals(facetField.getName())).findFirst().ifPresent(facetField -> {
								facetField.getValues().forEach(value -> {
									personPurposeTitles.add(value.getName());
								});
							});
							ctx.put("personPurposeTitles", personPurposeTitles);
	
							JsonArray personActionTitles = new JsonArray();
							stopSearch2.getQueryResponse().getFacetFields().stream().filter(facetField -> "stopActionTitle_indexed_string".equals(facetField.getName())).findFirst().ifPresent(facetField -> {
								facetField.getValues().forEach(value -> {
									personActionTitles.add(value.getName());
								});
							});
							ctx.put("personActionTitles", personActionTitles);
		
							JsonArray personRaceTitles = new JsonArray();
							stopSearch2.getQueryResponse().getFacetFields().stream().filter(facetField -> "personRaceTitles_indexed_strings".equals(facetField.getName())).findFirst().ifPresent(facetField -> {
								facetField.getValues().forEach(value -> {
									personRaceTitles.add(value.getName());
									personRaceTitlesFacets.put(value.getName(), value.getCount());
								});
							});
							ctx.put("personRaceTitles", personRaceTitles);
		
							JsonArray personGenderTitles = new JsonArray();
							stopSearch2.getQueryResponse().getFacetFields().stream().filter(facetField -> "personGenderTitles_indexed_strings".equals(facetField.getName())).findFirst().ifPresent(facetField -> {
								facetField.getValues().forEach(value -> {
									personGenderTitles.add(value.getName());
								});
							});
							ctx.put("personGenderTitles", personGenderTitles);
		
							JsonArray personAges = new JsonArray();
							stopSearch2.getQueryResponse().getFacetFields().stream().filter(facetField -> "personAges_indexed_ints".equals(facetField.getName())).findFirst().ifPresent(facetField -> {
								facetField.getValues().forEach(value -> {
									personAges.add(value.getName());
								});
							});
							ctx.put("personAges", personAges);
	
							ctx.put("stopJson", stopJson.toString());
	
							ctx.next();
						}).onFailure(ex -> {
							LOG.error("Stop search page failed to load stop data. ", ex);
							ctx.fail(ex);
						});
					}).onFailure(ex -> {
						LOG.error("Stop search page failed to load stop data. ", ex);
						ctx.fail(ex);
					});
				} catch(Exception ex) {
					LOG.error("Stop search page failed to load stop data. ", ex);
					ctx.fail(ex);
				}
			});

			router.get("/api").handler(ctx -> {
				ctx.reroute("/template/openapi");
			});

			router.get("/template/*").handler(ctx -> {
				ctx.put(ConfigKeys.STATIC_BASE_URL, staticBaseUrl);
				ctx.put(ConfigKeys.SITE_BASE_URL, siteBaseUrl);
				ctx.next();
			});

			router.get("/template/*").handler(templateHandler);
			router.errorHandler(500,  ctx -> {
				Throwable ex = ctx.failure();
				LOG.error("Error occured. ", ex);
				ctx.json(new JsonObject().put("error", new JsonObject().put("message", ex.getMessage())));
			});

			StaticHandler staticHandler = StaticHandler.create().setCachingEnabled(false).setFilesReadOnly(false);
			if(staticPath != null) {
				staticHandler.setAllowRootFileSystemAccess(true);
				staticHandler.setWebRoot(staticPath);
			}
			router.route("/static/*").handler(staticHandler);

			LOG.info(configureUiComplete);
			promise.complete();
		} catch(Exception ex) {
			LOG.error(configureUiFail);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<SearchList<TrafficStop>> putVarsInRoutingContext(RoutingContext ctx) {
		Promise<SearchList<TrafficStop>> promise = Promise.promise();
		try {
			for(Entry<String, String> entry : ctx.queryParams()) {
				String paramName = entry.getKey();
				String paramObject = entry.getValue();
				String entityVar = null;
				String valueIndexed = null;

				switch(paramName) {
					case "var":
						entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
						valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
						ctx.put(entityVar, valueIndexed);
						break;
				}
			}
		} catch(Exception ex) {
			LOG.error(String.format("putVarsInRoutingContext failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	/**	
	 * 
	 * Val.ErrorServer.enUS:The server is not configured properly. 
	 * Val.SuccessServer.enUS:The HTTP server is running: %s
	 * Val.BeforeServer.enUS:HTTP server starting: %s
	 * Val.Ssl.enUS:Configuring SSL: %s
	 * 
	 *	Start the Vert.x server. 
	 **/
	private Future<Void> startServer() {
		Promise<Void> promise = Promise.promise();

		try {
			Boolean sslPassthrough = config().getBoolean(ConfigKeys.SSL_PASSTHROUGH, false);
			String siteBaseUrl = config().getString(ConfigKeys.SITE_BASE_URL);
			Integer sitePort = config().getInteger(ConfigKeys.SITE_PORT);
			String sslJksPath = config().getString(ConfigKeys.SSL_JKS_PATH);
			HttpServerOptions options = new HttpServerOptions();
			if(sslPassthrough) {
				options.setKeyStoreOptions(new JksOptions().setPath(sslJksPath).setPassword(config().getString(ConfigKeys.SSL_JKS_PASSWORD)));
				options.setSsl(true);
				LOG.info(String.format(startServerSsl, sslJksPath));
			}
			options.setPort(sitePort);
	
			LOG.info(String.format(startServerBeforeServer, siteBaseUrl));
			vertx.createHttpServer(options).requestHandler(router).listen(ar -> {
				if (ar.succeeded()) {
					LOG.info(String.format(startServerSuccessServer, siteBaseUrl));
					promise.complete();
				} else {
					LOG.error(startServerErrorServer, ar.cause());
					promise.fail(ar.cause());
				}
			});
		} catch (Exception ex) {
			LOG.error(startServerErrorServer, ex);
			promise.fail(ex);
		}

		return promise.future();
	}

	/**	
	 *	This is called by Vert.x when the verticle instance is undeployed. 
	 *	Setup the stopPromise to handle tearing down the server. 
	 * Val.Fail.enUS:Could not close the database client connection. 
	 * Val.Complete.enUS:The database client connection was closed. 
	 **/
	@Override()
	public void  stop(Promise<Void> promise) throws Exception, Exception {
		if(pgPool != null) {
			pgPool.close().onSuccess(a -> {
				LOG.info(stopComplete);
				promise.complete();
			}).onFailure(ex -> {
				LOG.error(stopFail, ex);
				promise.fail(ex);
			});
		}
	}

	public String toId(String s) {
		if(s != null) {
			s = Normalizer.normalize(s, Normalizer.Form.NFD);
			s = StringUtils.lowerCase(s);
			s = StringUtils.trim(s);
			s = StringUtils.replacePattern(s, "\\s{1,}", "-");
			s = StringUtils.replacePattern(s, "[^\\w-]", "");
			s = StringUtils.replacePattern(s, "-{2,}", "-");
		}

		return s;
	}
}
