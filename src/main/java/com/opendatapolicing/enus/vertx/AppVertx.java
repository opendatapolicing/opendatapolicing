package com.opendatapolicing.enus.vertx; 

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.opendatapolicing.enus.agency.SiteAgencyEnUSGenApiService;
import com.opendatapolicing.enus.config.SiteConfig;
import com.opendatapolicing.enus.context.SiteContextEnUS;
import com.opendatapolicing.enus.design.PageDesignEnUSGenApiService;
import com.opendatapolicing.enus.html.part.HtmlPartEnUSGenApiService;
import com.opendatapolicing.enus.java.LocalDateSerializer;
import com.opendatapolicing.enus.java.LocalTimeSerializer;
import com.opendatapolicing.enus.java.ZonedDateTimeSerializer;
import com.opendatapolicing.enus.searchbasis.SearchBasisEnUSGenApiService;
import com.opendatapolicing.enus.state.SiteStateEnUSGenApiService;
import com.opendatapolicing.enus.trafficcontraband.TrafficContrabandEnUSGenApiService;
import com.opendatapolicing.enus.trafficperson.TrafficPersonEnUSGenApiService;
import com.opendatapolicing.enus.trafficsearch.TrafficSearchEnUSGenApiService;
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
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.core.http.Cookie;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.core.net.JksOptions;
import io.vertx.core.shareddata.AsyncMap;
import io.vertx.core.shareddata.SharedData;
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
import io.vertx.ext.web.handler.OAuth2AuthHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.handler.sockjs.SockJSBridgeOptions;
import io.vertx.ext.web.handler.sockjs.SockJSHandler;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.spi.cluster.zookeeper.ZookeeperClusterManager;
import io.vertx.sqlclient.PoolOptions;

/**	
 *	A Java class to start the Vert.x application as a main method. 
 **/
public class AppVertx extends AppVertxGen<AbstractVerticle> {

	public final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	/**
	 * A io.vertx.ext.jdbc.JDBCClient for connecting to the relational database PostgreSQL. 
	 **/
	private PgPool pgPool;

	/**
	 * A site context object for storing information about the entire site in English. 
	 **/
	SiteContextEnUS siteContextEnUS;

	/**
	 * For logging information and errors in the application. 
	 **/
	private static final Logger LOG = LoggerFactory.getLogger(AppVertx.class);

	/**	
	 *	The main method for the Vert.x application that runs the Vert.x Runner class
	 **/
	public static void  main(String[] args) {
		run();
	}

	public static void  run() {
		Class<?> c = AppVertx.class;
		JsonObject zkConfig = new JsonObject();
		String zookeeperHostName = System.getenv("zookeeperHostName");
		Integer zookeeperPort = Integer.parseInt(System.getenv("zookeeperPort"));
		Integer clusterPort = System.getenv("clusterPort") == null ? null : Integer.parseInt(System.getenv("clusterPort"));
		String clusterHost = System.getenv("clusterHost");
		Integer clusterPublicPort = System.getenv("clusterPublicPort") == null ? null : Integer.parseInt(System.getenv("clusterPublicPort"));
		Integer siteInstances = System.getenv("siteInstances") == null ? 1 : Integer.parseInt(System.getenv("siteInstances"));
		String clusterPublicHost = System.getenv("clusterPublicHost");
		String zookeeperHosts = zookeeperHostName + ":" + zookeeperPort;
		zkConfig.put("zookeeperHosts", zookeeperHosts);
		zkConfig.put("sessionTimeout", 20000);
		zkConfig.put("connectTimeout", 3000);
		zkConfig.put("rootPath", "io.vertx");
		zkConfig.put("retry", new JsonObject() {
			{
				put("initialSleepTime", 100);
				put("intervalTimes", 10000);
				put("maxTimes", 3);
			}
		});
		ClusterManager gestionnaireCluster = new ZookeeperClusterManager(zkConfig);
		VertxOptions optionsVertx = new VertxOptions();
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
		optionsVertx.setEventBusOptions(eventBusOptions);
		optionsVertx.setClusterManager(gestionnaireCluster);
		DeploymentOptions deploymentOptions = new DeploymentOptions();
		deploymentOptions.setInstances(siteInstances);

		String verticleID = c.getName();

		Consumer<Vertx> runner = vertx -> {
			vertx.deployVerticle(verticleID, deploymentOptions);
		};
		Vertx.clusteredVertx(optionsVertx, res -> {
			if (res.succeeded()) {
				Vertx vertx = res.result();
				EventBus eventBus = vertx.eventBus();
				LOG.info("We now have a clustered event bus: {}", eventBus);
				runner.accept(vertx);
			} else {
				res.cause().printStackTrace();
			}
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
			Future<Void> promiseSteps = configureSiteContext().future().compose(a ->
				configureData().future().compose(b -> 
					configureCluster().future().compose(c -> 
						configureOpenApi().future().compose(d -> 
							configureHealthChecks().future().compose(e -> 
								configureSharedWorkerExecutor().future().compose(f -> 
									configureWebsockets().future().compose(g -> 
										configureEmail().future().compose(h -> 
											startServer().future()
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
	private Promise<Void> configureSiteContext() {
		Promise<Void> promise = Promise.promise();

		try {
			ConfigRetrieverOptions retrieverOptions = new ConfigRetrieverOptions();
			ConfigStoreOptions storeEnv = new ConfigStoreOptions().setType("env");
			retrieverOptions.addStore(storeEnv);

			String configPath = System.getenv("configPath");
			if(StringUtils.isNotBlank(configPath)) {
				ConfigStoreOptions configIni = new ConfigStoreOptions().setType("file").setFormat("properties")
						.setConfig(new JsonObject().put("path", configPath).put("raw-data", true));
				retrieverOptions.addStore(configIni);
			}

			ConfigRetriever configRetriever = ConfigRetriever.create(vertx, retrieverOptions);
			configRetriever.getConfig(a -> {
				JsonObject config = a.result();

				siteContextEnUS = new SiteContextEnUS();
				siteContextEnUS.setVertx(vertx);
				siteContextEnUS.getSiteConfig().setConfig(config);
				siteContextEnUS.initDeepSiteContextEnUS();

				LOG.info("The site context was configured successfully. ");
				promise.complete();
			});
		} catch(Exception ex) {
			LOG.error("Unable to configure site context. ", ex);
			promise.fail(ex);
		}

		return promise;
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
	private Promise<Void> configureData() {
		Promise<Void> promise = Promise.promise();
		try {
			SiteConfig siteConfig = siteContextEnUS.getSiteConfig();

			PgConnectOptions pgOptions = new PgConnectOptions();
			pgOptions.setPort(siteConfig.getJdbcPort());
			pgOptions.setHost(siteConfig.getJdbcHost());
			pgOptions.setDatabase(siteConfig.getJdbcDatabase());
			pgOptions.setUser(siteConfig.getJdbcUsername());
			pgOptions.setPassword(siteConfig.getJdbcPassword());
			pgOptions.setIdleTimeout(siteConfig.getJdbcMaxIdleTime());
			pgOptions.setIdleTimeoutUnit(TimeUnit.SECONDS);
			pgOptions.setConnectTimeout(siteConfig.getJdbcConnectTimeout());

			PoolOptions poolOptions = new PoolOptions();
			poolOptions.setMaxSize(siteConfig.getJdbcMaxPoolSize());
			poolOptions.setMaxWaitQueueSize(siteConfig.getJdbcMaxWaitQueueSize());

			pgPool = PgPool.pool(vertx, pgOptions, poolOptions);

			siteContextEnUS.setPgPool(pgPool);

			LOG.info(configureDataInitSuccess);
			promise.complete();
		} catch (Exception ex) {
			LOG.error(configureDataInitError, ex);
			promise.fail(ex);
		}

		return promise;
	}

	/**	
	 * 
	 * Val.DataError.enUS:Could not configure the shared cluster data. 
	 * Val.DataSuccess.enUS:The shared cluster data was configured successfully. 
	 * 
	 *	Configure shared data across the cluster for massive scaling of the application. 
	 *	Return a promise that configures a shared cluster data. 
	 **/ 
	private Promise<Void> configureCluster() {
		Promise<Void> promise = Promise.promise();
		try {
			SiteConfig siteConfig = siteContextEnUS.getSiteConfig();
			SharedData sharedData = vertx.sharedData();
			sharedData.getClusterWideMap("clusterData", res -> {
				if (res.succeeded()) {
					AsyncMap<Object, Object> clusterData = res.result();
					clusterData.put("siteConfig", siteConfig, resPut -> {
						if (resPut.succeeded()) {
							LOG.info(configureClusterDataSuccess);
							promise.complete();
						} else {
							LOG.error(configureClusterDataError, res.cause());
							promise.fail(res.cause());
						}
					});
				} else {
					LOG.error(configureClusterDataError, res.cause());
					promise.fail(res.cause());
				}
			});
		} catch (Exception ex) {
			LOG.error(configureClusterDataError, ex);
			promise.fail(ex);
		}
		return promise;
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
	private Promise<Void> configureOpenApi() {
		Promise<Void> promise = Promise.promise();
		try {
			SiteConfig siteConfig = siteContextEnUS.getSiteConfig();
			String siteUrlBase = siteConfig.getSiteBaseUrl();

			OAuth2Options oauth2ClientOptions = new OAuth2Options();
			oauth2ClientOptions.setSite(siteConfig.getAuthUrl() + "/realms/" + siteConfig.getAuthRealm());
			oauth2ClientOptions.setClientID(siteConfig.getAuthResource());
			oauth2ClientOptions.setClientSecret(siteConfig.getAuthSecret());
			oauth2ClientOptions.setFlow(OAuth2FlowType.AUTH_CODE);
			JsonObject extraParams = new JsonObject();
			extraParams.put("scope", "DefaultAuthScope");
			oauth2ClientOptions.setExtraParameters(extraParams);

			OpenIDConnectAuth.discover(vertx, oauth2ClientOptions, a -> {
				if(a.succeeded()) {
					OAuth2Auth oauth2AuthenticationProvider = a.result();
					siteContextEnUS.setOauth2AuthenticationProvider(oauth2AuthenticationProvider);

					AuthorizationProvider authorizationProvider = KeycloakAuthorization.create();
					siteContextEnUS.setAuthorizationProvider(authorizationProvider);

					OAuth2AuthHandler oauth2AuthHandler = OAuth2AuthHandler.create(vertx, oauth2AuthenticationProvider, siteUrlBase + "/callback");
					{
						Router tempRouter = Router.router(vertx);
						oauth2AuthHandler.setupCallback(tempRouter.get("/callback"));
					}
			
			//		ClusteredSessionStore sessionStore = ClusteredSessionStore.create(vertx);
					LocalSessionStore sessionStore = LocalSessionStore.create(vertx, "opendatapolicing-sessions");
					SessionHandler sessionHandler = SessionHandler.create(sessionStore);
					String siteBaseUrl = siteConfig.getSiteBaseUrl();
					if(StringUtils.startsWith(siteBaseUrl, "https://"))
						sessionHandler.setCookieSecureFlag(true);
			
					RouterBuilder.create(vertx, "src/main/resources/openapi3-enUS.yaml", b -> {
						if (b.succeeded()) {
							RouterBuilder routerBuilder = b.result();
							routerBuilder.mountServicesFromExtensions();
							siteContextEnUS.setRouterBuilder(routerBuilder);
			
							Function<RoutingContext, JsonObject> extraPayloadMapper = routingContext -> new JsonObject().put("uri", routingContext.request().uri()).put("method", routingContext.request().method().name());
							routerBuilder.serviceExtraPayloadMapper(extraPayloadMapper);
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
			
								config.put("redirect_uri", siteUrlBase + "/callback");
			
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
			
							Router router = routerBuilder.createRouter();
							siteContextEnUS.setRouter(router);
			
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
		return promise;
	}

	/**
	 * Val.Error.enUS:Could not configure the shared worker executor. 
	 * Val.Success.enUS:The shared worker executor was configured successfully. 
	 * 
	 *	Configure a shared worker executor for running blocking tasks in the background. 
	 *	Return a promise that configures the shared worker executor. 
	 **/    
	private Promise<Void> configureSharedWorkerExecutor() {
		Promise<Void> promise = Promise.promise();
		try {
			WorkerExecutor workerExecutor = vertx.createSharedWorkerExecutor("WorkerExecutor");
			siteContextEnUS.setWorkerExecutor(workerExecutor);
			promise.complete();
		} catch (Exception ex) {
			LOG.error(configureSharedWorkerExecutorError, ex);
			promise.fail(ex);
		}
		return promise;
	}

	/**	
	 * 
	 * Val.ErrorDatabase.enUS:The database is not configured properly. 
	 * 
	 * Val.EmptySolr.enUS:The Solr search engine is empty. 
	 * 
	 * Val.ErrorSolr.enUS:The Solr search engine is not configured properly. 
	 * 
	 * Val.ErrorVertx.enUS:The Vert.x application is not configured properly. 
	 * 
	 *	Configure health checks for the status of the website and it's dependent services. 
	 *	Return a promise that configures the health checks. 
	 **/
	private Promise<Void> configureHealthChecks() {
		Promise<Void> promise = Promise.promise();
		try {
			Router siteRouteur = siteContextEnUS.getRouter();
			HealthCheckHandler healthCheckHandler = HealthCheckHandler.create(vertx);

			healthCheckHandler.register("database", 2000, a -> {
				siteContextEnUS.getPgPool().preparedQuery("select current_timestamp").execute(selectCAsync -> {
					if(selectCAsync.succeeded()) {
						a.complete(Status.OK());
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
					QueryResponse r = siteContextEnUS.getSolrClient().query(query);
					if(r.getResults().size() > 0)
						a.complete(Status.OK());
					else {
						LOG.error(configureHealthChecksEmptySolr, a.future().cause());
						promise.fail(a.future().cause());
					}
				} catch (SolrServerException | IOException e) {
					LOG.error(configureHealthChecksErrorSolr, a.future().cause());
					promise.fail(a.future().cause());
				}
			});
			siteRouteur.get("/health").handler(healthCheckHandler);
			promise.complete();
		} catch (Exception ex) {
			LOG.error(configureHealthChecksErrorSolr, ex);
			promise.fail(ex);
		}
		return promise;
	}

	/**	
	 * Val.Error.enUS:Could not configure websockets. 
	 * Val.Success.enUS:The websockets configured successfully. 
	 * 
	 *	Configure websockets for realtime messages. 
	 **/
	private Promise<Void> configureWebsockets() {
		Promise<Void> promise = Promise.promise();
		try {
			Router siteRouter = siteContextEnUS.getRouter();
			SockJSBridgeOptions options = new SockJSBridgeOptions()
					.addOutboundPermitted(new PermittedOptions().setAddressRegex("websocket.*"));
			SockJSHandler sockJsHandler = SockJSHandler.create(vertx);
			sockJsHandler.bridge(options);
			siteRouter.route("/eventbus/*").handler(sockJsHandler);
			promise.complete();
		} catch (Exception ex) {
			LOG.error(configureWebsocketsError, ex);
			promise.fail(ex);
		}
		return promise;
	}

	/**	
	 * Val.Error.enUS:Could not configure the email. 
	 * Val.Success.enUS:The email was configured successfully. 
	 * 
	 *	Configure sending email. 
	 **/
	private Promise<Void> configureEmail() {
		Promise<Void> promise = Promise.promise();
		try {
			SiteConfig siteConfig = siteContextEnUS.getSiteConfig();
			MailConfig config = new MailConfig();
			if(StringUtils.isNotBlank(siteConfig.getEmailHost())) {
				config.setHostname(siteConfig.getEmailHost());
				config.setPort(siteConfig.getEmailPort());
				config.setSsl(siteConfig.getEmailSsl());
				config.setUsername(siteConfig.getEmailUsername());
				config.setPassword(siteConfig.getEmailPassword());
				MailClient mailClient = MailClient.createShared(vertx, config);
				siteContextEnUS.setMailClient(mailClient);
			}
			promise.complete();
		} catch (Exception ex) {
			LOG.error(configureEmailError, ex);
			promise.fail(ex);
		}
		return promise;
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
	private Promise<Void> startServer() {
		SiteConfig siteConfig = siteContextEnUS.getSiteConfig();
		Promise<Void> promise = Promise.promise();

		SiteUserEnUSGenApiService.registerService(siteContextEnUS, vertx);
		PageDesignEnUSGenApiService.registerService(siteContextEnUS, vertx);
		HtmlPartEnUSGenApiService.registerService(siteContextEnUS, vertx);
		SiteStateEnUSGenApiService.registerService(siteContextEnUS, vertx);
		SiteAgencyEnUSGenApiService.registerService(siteContextEnUS, vertx);
		SearchBasisEnUSGenApiService.registerService(siteContextEnUS, vertx);
		TrafficContrabandEnUSGenApiService.registerService(siteContextEnUS, vertx);
		TrafficPersonEnUSGenApiService.registerService(siteContextEnUS, vertx);
		TrafficSearchEnUSGenApiService.registerService(siteContextEnUS, vertx);
		TrafficStopEnUSGenApiService.registerService(siteContextEnUS, vertx);

		Router siteRouter = siteContextEnUS.getRouter();

		StaticHandler staticHandler = StaticHandler.create().setCachingEnabled(false).setFilesReadOnly(true);
		String staticPath = siteConfig.getStaticPath();
		if(staticPath != null) {
			staticHandler.setAllowRootFileSystemAccess(true);
			staticHandler.setWebRoot(staticPath);
		}
		siteRouter.route("/static/*").handler(staticHandler);

		SimpleModule module = new SimpleModule();
		module.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer());
		module.addSerializer(LocalDate.class, new LocalDateSerializer());
		module.addSerializer(LocalTime.class, new LocalTimeSerializer());
		DatabindCodec.mapper().registerModule(module);

		String siteHostName = siteConfig.getSiteHostName();
		String siteBaseUrl = siteConfig.getSiteBaseUrl();
		Integer sitePort = siteConfig.getSitePort();
		HttpServerOptions options = new HttpServerOptions();
		if(siteConfig.getSslPassthrough() != null && siteConfig.getSslPassthrough()) {
			options.setKeyStoreOptions(new JksOptions().setPath(siteConfig.getSslJksPath()).setPassword(siteConfig.getSslJksPassword()));
			options.setSsl(true);
			LOG.info(String.format(startServerSsl, siteConfig.getSslJksPath()));
		}
		options.setPort(sitePort);

		LOG.info(String.format(startServerBeforeServer, siteBaseUrl));
		vertx.createHttpServer(options).requestHandler(siteRouter).listen(ar -> {
			if (ar.succeeded()) {
				LOG.info(String.format(startServerSuccessServer, siteBaseUrl));
				promise.complete();
			} else {
				LOG.error(startServerErrorServer, ar.cause());
				promise.fail(ar.cause());
			}
		});

		return promise;
	}

	/**	
	 *	This is called by Vert.x when the verticle instance is undeployed. 
	 *	Setup the stopPromise to handle tearing down the server. 
	 **/
	@Override()
	public void  stop(Promise<Void> stopPromise) throws Exception, Exception {
		Promise<Void> promiseSteps = closeData();
		promiseSteps.future().onComplete(stopPromise);
	}

	/**	
	 * Val.Error.enUS:Could not close the database client connection. 
	 * Val.Success.enUS:The database client connextion was closed. 
	 * 
	 *	Return a promise to close the database client connection. 
	 **/
	private Promise<Void> closeData() {
		Promise<Void> promise = Promise.promise();
		PgPool pgPool = siteContextEnUS.getPgPool();

		if(pgPool != null) {
			pgPool.close();
			LOG.info(closeDataSuccess);
			promise.complete();
		}
		return promise;
	}
}
