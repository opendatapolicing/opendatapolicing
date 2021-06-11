package com.opendatapolicing.enus.vertx;          

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
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

	Integer semaphorePermits;

	Semaphore semaphore;
	public MainVerticle setSemaphore(Semaphore semaphore) {
		this.semaphore = semaphore;
		return this;
	}

	public static final String CONFIG_staticPath = "staticPath";

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

			String configPath = System.getenv("configPath");
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
		Integer semaphorePermits = System.getenv(ConfigKeys.SEMAPHORE_PERMITS) == null ? 10 : Integer.parseInt(System.getenv(ConfigKeys.SEMAPHORE_PERMITS));
//		InterProcessSemaphoreV2 semaphore = new InterProcessSemaphoreV2(curatorFramework, "/opendatapolicing/semaphore", semaphorePermits);
		Semaphore semaphore = new Semaphore(semaphorePermits);
		Integer clusterPort = System.getenv("clusterPort") == null ? null : Integer.parseInt(System.getenv("clusterPort"));
		String clusterHost = System.getenv("clusterHost");
		Integer clusterPublicPort = System.getenv("clusterPublicPort") == null ? null : Integer.parseInt(System.getenv("clusterPublicPort"));
		Integer siteInstances = System.getenv(ConfigKeys.SITE_INSTANCES) == null ? 1 : Integer.parseInt(System.getenv(ConfigKeys.SITE_INSTANCES));
		Integer semaphoreVerticleInstances = System.getenv(ConfigKeys.SEMAPHORE_VERTICLE_INSTANCES) == null ? 1 : Integer.parseInt(System.getenv(ConfigKeys.SEMAPHORE_VERTICLE_INSTANCES));
		Long vertxWarningExceptionSeconds = System.getenv("vertxWarningExceptionSeconds") == null ? 10 : Long.parseLong(System.getenv("vertxWarningExceptionSeconds"));
		String clusterPublicHost = System.getenv("clusterPublicHost");
		zkConfig.put("zookeeperHosts", zookeeperHosts);
		zkConfig.put("sessionTimeout", 20000000);
		zkConfig.put("connectTimeout", 30000);
		zkConfig.put("rootPath", "io.vertx");
		zkConfig.put("retry", new JsonObject() {
			{
				put("initialSleepTime", 100);
				put("intervalTimes", 10000);
				put("maxTimes", 30);
			}
		});
		ClusterManager clusterManager = new ZookeeperClusterManager(zkConfig);
		VertxOptions vertxOptions = new VertxOptions();
		// For OpenShift
		EventBusOptions eventBusOptions = new EventBusOptions();
		String hostname = System.getenv("HOSTNAME");
		String openshiftService = System.getenv("openshiftService");
		if(clusterHost == null) {
			clusterHost = hostname;
		}
		if(clusterPublicHost == null) {
			if(hostname != null && openshiftService != null) {
				clusterPublicHost = hostname + "." + openshiftService;
			}
		}
		if(clusterHost != null) {
			LOG.info(String.format("clusterHost: %s", clusterHost));
			eventBusOptions.setHost(clusterHost);
		}
		if(clusterPort != null) {
			LOG.info(String.format("clusterPort: %s", clusterPort));
			eventBusOptions.setPort(clusterPort);
		}
		if(clusterPublicHost != null) {
			LOG.info(String.format("clusterPublicHost: %s", clusterPublicHost));
			eventBusOptions.setClusterPublicHost(clusterPublicHost);
		}
		if(clusterPublicPort != null) {
			LOG.info(String.format("clusterPublicPort: %s", clusterPublicPort));
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
		
					DeploymentOptions semaphoreVerticleDeploymentOptions = new DeploymentOptions();
					semaphoreVerticleDeploymentOptions.setConfig(config);
					semaphoreVerticleDeploymentOptions.setWorker(true);
		
					DeploymentOptions mailVerticleDeploymentOptions = new DeploymentOptions();
					mailVerticleDeploymentOptions.setConfig(config);
					mailVerticleDeploymentOptions.setWorker(true);
		
					DeploymentOptions workerVerticleDeploymentOptions = new DeploymentOptions();
					workerVerticleDeploymentOptions.setConfig(config);
//					workerVerticleDeploymentOptions.setWorker(true);
					workerVerticleDeploymentOptions.setInstances(1);
		
					vertx.deployVerticle(MainVerticle.class, deploymentOptions).onSuccess(a -> {
						vertx.deployVerticle(WorkerVerticle.class, workerVerticleDeploymentOptions);
						LOG.info("Started main verticle. ");
					}).onFailure(ex -> {
						LOG.error("Failed to start main verticle. ", ex);
					});
	//				for(Integer i = 0; i < semaphoreVerticleInstances; i++) {
	//					futures.add(vertx.deployVerticle(new SemaphoreVerticle().setClusterManager(clusterManager), semaphoreVerticleDeploymentOptions).onSuccess(a -> {
	//						LOG.info("Started SemaphoreVerticle. ");
	//					}).onFailure(ex -> {
	//						LOG.error("Failed to start SemaphoreVerticle. ", ex);
	//					}));
	//				}
		//			futures.add(vertx.deployVerticle(new MailVerticle(), mailVerticleDeploymentOptions).onSuccess(a -> {
		//				LOG.info("Started mail verticle. ");
		//			}).onFailure(ex -> {
		//				LOG.error("Failed to start mail verticle. ", ex);
		//			}));
		//			futures.add(Future.future(promise -> {
		//				vertx.deployVerticle(new WorkerVerticle().setSemaphore(semaphore), workerVerticleDeploymentOptions).onSuccess(a -> {
		//					promise.complete();
		//					LOG.info("Started worker verticle. ");
		//				}).onFailure(ex -> {
		//					promise.fail(ex);
		//					LOG.error("Failed to start worker verticle. ", ex);
		//				});
		//			}));
//		
//					CompositeFuture.all(futures).onSuccess(b -> {
//						LOG.info("We now have a clustered event bus. ");
//						vertx.deployVerticle(new WorkerVerticle().setClusterManager(clusterManager), workerVerticleDeploymentOptions);
//						Runtime.getRuntime().addShutdownHook(new Thread() {
//							public void run() {
//								LOG.info("Shutting down vertx. ");
//								List<Future> futures = new ArrayList<>();
//								CountDownLatch latch = new CountDownLatch(vertx.deploymentIDs().size());
//								vertx.deploymentIDs().forEach(deploymentId -> {
//									futures.add(vertx.undeploy(deploymentId).onSuccess(a -> {
//										LOG.info("Succeeded to undeploy verticle {}. ", deploymentId);
//										latch.countDown();
//									}).onFailure(ex -> {
//										LOG.error("Failed to undeploy verticle {}. ", deploymentId);
//										latch.countDown();
//									}));
//									
//								});
//								CompositeFuture.all(futures).onSuccess(a -> {
//									vertx.close().onSuccess(b -> {
//										LOG.info("Goodbye");
//									}).onFailure(ex -> {
//										LOG.error("Failed to shut down vertx. ", ex);
//									});
//								}).onFailure(ex -> {
//									LOG.error(String.format("listPUTImportTrafficStop failed. ", ex));
//								});
//								try {
//									latch.await(10, TimeUnit.SECONDS);
//								} catch (Exception ignored) {
//								}
//							}
//						});
//					}).onFailure(ex -> {
//						LOG.error("Creating clustered Vertx failed. ", ex);
//						ExceptionUtils.rethrow(ex);
//					});
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
			semaphorePermits = System.getenv(ConfigKeys.SEMAPHORE_PERMITS) == null ? 10 : Integer.parseInt(System.getenv(ConfigKeys.SEMAPHORE_PERMITS));
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
			oauth2ClientOptions.setSite(config().getString(ConfigKeys.AUTH_URL) + "/realms/" + config().getString(ConfigKeys.AUTH_REALM));
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
			
			//		ClusteredSessionStore sessionStore = ClusteredSessionStore.create(vertx);
					LocalSessionStore sessionStore = LocalSessionStore.create(vertx, "opendatapolicing-sessions");
					SessionHandler sessionHandler = SessionHandler.create(sessionStore);
					if(StringUtils.startsWith(siteBaseUrl, "https://"))
						sessionHandler.setCookieSecureFlag(true);
			
					RouterBuilder.create(vertx, "src/main/resources/openapi3-enUS.yaml", b -> {
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
			
								config().put("redirect_uri", siteBaseUrl + "/callback");
			
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
			String name = "AppVertx-WorkerExecutor";
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
			siteInstances = System.getenv("siteInstances") == null ? 1 : Integer.parseInt(System.getenv("siteInstances"));
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
					String solrRequestUri = String.format("/solr/%s/select%s", solrCollection, query.toQueryString() + "&suggest=true&terms=true&terms.fl=stopPurposeTitle_indexed_string");
					webClient.get(solrPort, solrHostName, solrRequestUri).send().onSuccess(b -> {
						try {
							a.complete(Status.OK());
						} catch(Exception ex) {
							LOG.error("Could not read response from Solr. ", ex);
							a.fail(ex);
						}
					}).onFailure(ex -> {
						LOG.error(String.format("indexTrafficStop failed. "), new RuntimeException(ex));
						a.fail(ex);
					});
				} catch (Exception e) {
					LOG.error(configureHealthChecksErrorSolr, a.future().cause());
					a.fail(a.future().cause());
				}
			});
			healthCheckHandler.register("semaphore", 2000, a -> {
				a.complete(Status.OK(new JsonObject().put("permits", semaphorePermits)));
			});
			healthCheckHandler.register("vertx", 2000, a -> {
				a.complete(Status.OK(new JsonObject().put("siteInstances", siteInstances).put("workerPoolSize", workerPoolSize)));
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

			router.get("/").handler(a -> {
				a.reroute("/template/home-page");
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

					SearchList<TrafficStop> stopSearch = new SearchList<TrafficStop>();
					stopSearch.setStore(true);
					stopSearch.setQuery("*:*");
					stopSearch.setC(TrafficStop.class);
					stopSearch.setRows(1);
					stopSearch.addSort(SortClause.asc("stopDateTime_indexed_date"));
					stopSearch.addFacetField("agencyTitle_indexed_string");
					stopSearch.add("facet.limit", "5");
					stopSearch.promiseDeepForClass(siteRequest).onSuccess(c -> {
						TrafficStop stop = stopSearch.first();
						ctx.put("beginStopDateTime", stop.getStopDateTime().format(LocalDateSerializer.FORMATDateSite));
						stopSearch.getQueryResponse().getFacetFields().forEach(facetField -> {
							facetField.getValues().forEach(value -> {
								agencyFacets.add(
										new JsonObject()
										.put("stateAbbreviation", state.getStateAbbreviation())
										.put("agencyTitle", value.getName())
										.put("stopCount", NumberFormat.getNumberInstance(Locale.US).format(value.getCount()))
										);
							});
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

			router.get("/template/traffic-stop-report").handler(ctx -> {
				putVarsInRoutingContext(ctx);
				ctx.next();
			});


			router.get("/api").handler(ctx -> {
				ctx.reroute("/template/openapi");
			});

			router.get("/report").handler(ctx -> {
				ctx.put("queryParams", ctx.queryParams());
				ctx.reroute("/template/traffic-stop-report");
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
			LOG.error(String.format("searchTrafficStop failed. "), ex);
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
	 *	Démarrer le serveur Vert.x. 
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
}
