package com.opendatapolicing.enus.vertx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.commons.net.util.TrustManagerUtils;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opendatapolicing.enus.agency.SiteAgency;
import com.opendatapolicing.enus.config.ConfigKeys;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.opendatapolicing.enus.request.api.ApiRequest;
import com.opendatapolicing.enus.search.SearchList;
import com.opendatapolicing.enus.searchbasis.SearchBasis;
import com.opendatapolicing.enus.state.SiteState;
import com.opendatapolicing.enus.trafficcontraband.TrafficContraband;
import com.opendatapolicing.enus.trafficperson.TrafficPerson;
import com.opendatapolicing.enus.trafficsearch.TrafficSearch;
import com.opendatapolicing.enus.trafficstop.TrafficStop;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.file.OpenOptions;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.parsetools.RecordParser;
import io.vertx.ext.auth.authentication.UsernamePasswordCredentials;
import io.vertx.ext.mail.MailClient;
import io.vertx.ext.mail.MailConfig;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowStream;

/**
 */
public class WorkerVerticle extends WorkerVerticleGen<AbstractVerticle> {
	private static final Logger LOG = LoggerFactory.getLogger(WorkerVerticle.class);

	public static final Integer FACET_LIMIT = 100;

	/**
	 * A io.vertx.ext.jdbc.JDBCClient for connecting to the relational database PostgreSQL. 
	 **/
	private PgPool pgPool;

	private WebClient webClient;

	WorkerExecutor workerExecutor;

	/**	
	 *	This is called by Vert.x when the verticle instance is deployed. 
	 *	Initialize a new site context object for storing information about the entire site in English. 
	 *	Setup the startPromise to handle the configuration steps and starting the server. 
	 **/
	@Override()
	public void  start(Promise<Void> startPromise) throws Exception, Exception {

		try {
			configureWebClient().compose(a -> 
				configureData().compose(b -> 
					configureSharedWorkerExecutor().compose(c -> 
						configureEmail().compose(d -> 
							importData().compose(e -> 
								syncDbToSolr().compose(f -> 
									syncFtp().compose(g -> 
										refreshAllData()
									)
								)
							)
						)
					)
				)
			).onComplete(startPromise);
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
			Integer jdbcMaxPoolSize = config().getInteger(ConfigKeys.JDBC_MAX_POOL_SIZE, 1);

			pgOptions.setPort(config().getInteger(ConfigKeys.JDBC_PORT));
			pgOptions.setHost(config().getString(ConfigKeys.JDBC_HOST));
			pgOptions.setDatabase(config().getString(ConfigKeys.JDBC_DATABASE));
			pgOptions.setUser(config().getString(ConfigKeys.JDBC_USERNAME));
			pgOptions.setPassword(config().getString(ConfigKeys.JDBC_PASSWORD));
			pgOptions.setIdleTimeout(config().getInteger(ConfigKeys.JDBC_MAX_IDLE_TIME, 24));
			pgOptions.setIdleTimeoutUnit(TimeUnit.HOURS);
			pgOptions.setConnectTimeout(config().getInteger(ConfigKeys.JDBC_CONNECT_TIMEOUT, 86400000));

			PoolOptions poolOptions = new PoolOptions();
			poolOptions.setMaxSize(jdbcMaxPoolSize);
			poolOptions.setMaxWaitQueueSize(config().getInteger(ConfigKeys.JDBC_MAX_WAIT_QUEUE_SIZE, 10));

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
	 * Val.Fail.enUS:Could not configure the shared worker executor. 
	 * Val.Complete.enUS:The shared worker executor "{}" was configured successfully. 
	 * 
	 *	Configure a shared worker executor for running blocking tasks in the background. 
	 *	Return a promise that configures the shared worker executor. 
	 **/
	private Future<Void> configureSharedWorkerExecutor() {
		Promise<Void> promise = Promise.promise();
		try {
			String name = "WorkerVerticle-WorkerExecutor";
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
	 * Import initial data
	 * Val.Complete.enUS:Importing initial data completed. 
	 * Val.Fail.enUS:Importing initial data failed. 
	 * Val.Skip.enUS:data import skipped. 
	 **/
	private Future<Void> importData() {
		Promise<Void> promise = Promise.promise();
		try {
			if(config().getBoolean(ConfigKeys.ENABLE_IMPORT_DATA, true)) {
				List<Future> futures = new ArrayList<>();
				futures.add(Future.future(promise1 -> {
					workerExecutor.executeBlocking(blockingCodeHandler -> {
						Integer acsApiYear = config().getInteger(ConfigKeys.ACS_API_YEAR);
						webClient.post(config().getInteger(ConfigKeys.AUTH_PORT), config().getString(ConfigKeys.AUTH_HOST_NAME), config().getString(ConfigKeys.AUTH_TOKEN_URI))
								.expect(ResponsePredicate.SC_OK)
								.ssl(config().getBoolean(ConfigKeys.AUTH_SSL))
								.authentication(new UsernamePasswordCredentials(config().getString(ConfigKeys.AUTH_RESOURCE), config().getString(ConfigKeys.AUTH_SECRET)))
								.putHeader("Content-Type", "application/x-www-form-urlencoded")
								.sendForm(MultiMap.caseInsensitiveMultiMap().set("grant_type", "client_credentials"))
								.onSuccess(tokenResponse -> {
							JsonObject token = tokenResponse.bodyAsJsonObject();
							webClient.get(443, "api.census.gov", String.format("/data/%s/acs/acs5?get=NAME&for=state:*&key=%s"
											, acsApiYear
											, config().getString(ConfigKeys.ACS_API_KEY)
											))
									.expect(ResponsePredicate.SC_OK)
									.ssl(true)
									.send()
									.onSuccess(acsGetStateResponse -> {
								JsonArray acsGetStateJson = acsGetStateResponse.bodyAsJsonArray();
								String stateAcsId = acsGetStateJson.stream().map(o -> (JsonArray)o).filter(items -> "North Carolina".equals(items.getString(0))).findFirst().map(item -> item.getString(1)).orElse(null);
	
								try {
									try {
										JsonObject params = new JsonObject();
										JsonObject body = new JsonObject()
												.put(SiteState.VAR_stateName, "North Carolina")
												.put(SiteState.VAR_stateAbbreviation, "NC")
												.put(SiteState.VAR_stateAcsId, stateAcsId)
												.put(SiteState.VAR_pk, "NC");
										params.put("body", body);
										params.put("path", new JsonObject());
										params.put("cookie", new JsonObject());
										params.put("header", new JsonObject());
										params.put("form", new JsonObject());
										params.put("query", new JsonObject().put("commitWithin", 10000));
										JsonObject context = new JsonObject().put("params", params).put("user", token);
										JsonObject json = new JsonObject().put("context", context);
										vertx.eventBus().request("opendatapolicing-enUS-SiteState", json, new DeliveryOptions().addHeader("action", "putimportSiteStateFuture")).onSuccess(a -> {
											LOG.info("{} State imported. ", body.getString("stateName"));
											blockingCodeHandler.complete();
										}).onFailure(ex -> {
											LOG.error(String.format("listPUTImportSiteState failed. "), ex);
											blockingCodeHandler.fail(ex);
										});
									} catch(Exception ex) {
										LOG.error(String.format("listPUTImportSiteState failed. "), ex);
										blockingCodeHandler.fail(ex);
									}
								} catch(Exception ex) {
									LOG.error(String.format("listPUTImportSiteState failed. "), ex);
									blockingCodeHandler.fail(ex);
								}
							}).onFailure(ex -> {
								LOG.error(String.format("listPUTImportSiteState failed. "), ex);
								promise1.fail(ex);
							});
						}).onFailure(ex -> {
							LOG.error(String.format("listPUTImportSiteState failed. "), ex);
							promise1.fail(ex);
						});
					}, false).onSuccess(a -> {
						promise1.complete();
					}).onFailure(ex -> {
						LOG.error(String.format("listPUTImportSiteState failed. "), ex);
						promise1.fail(ex);
					});
				}));
				CompositeFuture.all(futures).onSuccess(a -> {
					LOG.info("States imported. ");
					promise.complete();
				}).onFailure(ex -> {
					LOG.error(String.format("importData failed. "), ex);
					promise.fail(ex);
				});
			} else {
				LOG.info(String.format(importDataSkip));
				promise.complete();
			}
		} catch (Exception ex) {
			LOG.error(configureEmailFail, ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	/**	
	 * Val.Complete.enUS:Syncing FTP files completed. 
	 * Val.Fail.enUS:Syncing FTP files failed. 
	 * Val.Skip.enUS:Skip syncing FTP files. 
	 **/
	private Future<Void> syncFtp() {
		Promise<Void> promise = Promise.promise();
		if(config().getBoolean(ConfigKeys.ENABLE_FTP_SYNC, false)) {
			Long millis = 1000L * config().getInteger(ConfigKeys.TIMER_FTP_SYNC_IN_SECONDS, 10);
			vertx.setTimer(millis, a -> {
				workerExecutor.executeBlocking(blockingCodeHandler -> {
					try {
						if(config().getBoolean(ConfigKeys.ENABLE_FTP_DOWNLOAD)) {
							String zipPath = config().getString(ConfigKeys.FTP_SYNC_PATH_ZIP);
							String remotePath = config().getString(ConfigKeys.FTP_SYNC_REMOTE_PATH);
							String remoteHostName = config().getString(ConfigKeys.FTP_SYNC_HOST_NAME);
							Integer remotePort = config().getInteger(ConfigKeys.FTP_SYNC_PORT);
							String remoteUsername = config().getString(ConfigKeys.FTP_SYNC_USERNAME);
							String remotePassword = config().getString(ConfigKeys.FTP_SYNC_PASSWORD);
	
							FTPSClient client = new FTPSClient(false);
//							client.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
							client.setTrustManager(TrustManagerUtils.getAcceptAllTrustManager());
							client.connect(remoteHostName, remotePort);
							int connectReply = client.getReplyCode();
							if (FTPReply.isPositiveCompletion(connectReply)){
								if(client.login(remoteUsername, remotePassword)) {
									client.execPBSZ(0);  // Set protection buffer size
									client.execPROT("P"); // Set data channel protection to private
									client.cwd(StringUtils.substringBeforeLast(remotePath, "/"));
									client.type(FTP.BINARY_FILE_TYPE);
									client.setFileType(FTP.BINARY_FILE_TYPE);
									client.enterLocalPassiveMode();
									FileOutputStream os = new FileOutputStream(zipPath, false);
									if(client.retrieveFile(StringUtils.substringAfterLast(remotePath, "/"), os)) {
										os.flush();
										os.close();
										client.logout();
										try (ZipFile zipFile = new ZipFile(new File(zipPath), ZipFile.OPEN_READ, Charset.forName("UTF-8"))) {
											Enumeration<? extends ZipEntry> entries = zipFile.entries();
											Path destFolderPath = Paths.get(zipPath).getParent();
											while (entries.hasMoreElements()) {
												ZipEntry entry = entries.nextElement();
												Path entryPath = destFolderPath.resolve(entry.getName());
												if (entry.isDirectory()) {
													Files.createDirectories(entryPath);
												} else {
													Files.createDirectories(entryPath.getParent());
													try (InputStream in = zipFile.getInputStream(entry)) {
														try (OutputStream out = new FileOutputStream(entryPath.toFile())) {
															IOUtils.copy(in, out);
														}
													}
												}
											}
											syncFtpRecord("TrafficStop", "NC").onSuccess(c -> {
												syncFtpRecord("TrafficPerson", "NC").onSuccess(d -> {
													syncFtpRecord("TrafficSearch", "NC").onSuccess(e -> {
														syncFtpRecord("SearchBasis", "NC").onSuccess(f -> {
															syncFtpRecord("TrafficContraband", "NC").onSuccess(g -> {
																syncAgencies().onSuccess(h -> {
																	LOG.info(syncDbToSolrComplete);
																	promise.complete();
																}).onFailure(ex -> {
																	LOG.error(syncDbToSolrFail, ex);
																	promise.fail(ex);
																});
															}).onFailure(ex -> {
																LOG.error(syncDbToSolrFail, ex);
																promise.fail(ex);
															});
														}).onFailure(ex -> {
															LOG.error(syncDbToSolrFail, ex);
															promise.fail(ex);
														});
													}).onFailure(ex -> {
														LOG.error(syncDbToSolrFail, ex);
														promise.fail(ex);
													});
												}).onFailure(ex -> {
													LOG.error(syncDbToSolrFail, ex);
													promise.fail(ex);
												});
											}).onFailure(ex -> {
												LOG.error(syncDbToSolrFail, ex);
												promise.fail(ex);
											});
										} catch(Exception ex) {
											LOG.error(syncDbToSolrFail, ex);
											promise.fail(ex);
										}
									} else {
										client.noop();
										client.logout();
										RuntimeException ex = new RuntimeException(String.format("Ftp retrieve error"));
										LOG.error(syncDbToSolrFail, ex);
										promise.fail(ex);
									}
								} else {
									RuntimeException ex = new RuntimeException(String.format("Ftp login error error"));
									LOG.error(syncDbToSolrFail, ex);
									promise.fail(ex);
								}
							} else {
								RuntimeException ex = new RuntimeException(String.format("Ftp connect error %s", connectReply));
								LOG.error(syncDbToSolrFail, ex);
								promise.fail(ex);
							}
						} else {
							syncFtpRecord("TrafficStop", "NC").onSuccess(c -> {
								syncFtpRecord("TrafficPerson", "NC").onSuccess(d -> {
									syncFtpRecord("TrafficSearch", "NC").onSuccess(e -> {
										syncFtpRecord("SearchBasis", "NC").onSuccess(f -> {
											syncFtpRecord("TrafficContraband", "NC").onSuccess(g -> {
												syncAgencies().onSuccess(h -> {
													LOG.info(syncDbToSolrComplete);
													promise.complete();
												}).onFailure(ex -> {
													LOG.error(syncDbToSolrFail, ex);
													promise.fail(ex);
												});
											}).onFailure(ex -> {
												LOG.error(syncDbToSolrFail, ex);
												promise.fail(ex);
											});
										}).onFailure(ex -> {
											LOG.error(syncDbToSolrFail, ex);
											promise.fail(ex);
										});
									}).onFailure(ex -> {
										LOG.error(syncDbToSolrFail, ex);
										promise.fail(ex);
									});
								}).onFailure(ex -> {
									LOG.error(syncDbToSolrFail, ex);
									promise.fail(ex);
								});
							}).onFailure(ex -> {
								LOG.error(syncDbToSolrFail, ex);
								promise.fail(ex);
							});
						}
					} catch(Exception ex) {
						LOG.error(syncDbToSolrFail, ex);
						promise.fail(ex);
					}
				}, false).onSuccess(b -> {
					promise.complete();
				}).onFailure(ex -> {
					LOG.error(String.format("listPUTImportSiteState failed. "), ex);
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
	 * Sync %s data from FTP. 
	 * Val.Complete.enUS:%s FTP sync completed. 
	 * Val.Fail.enUS:%s FTP sync failed. 
	 * Val.CounterResetFail.enUS:%s FTP sync failed to reset counter. 
	 * Val.Skip.enUS:%s FTP sync skipped. 
	 * Val.Started.enUS:%s FTP sync started. 
	 **/
	private Future<Void> syncFtpRecord(String tableName, String stateAbbreviation) {
		Promise<Void> promise = Promise.promise();
		try {
			if(config().getBoolean(String.format("%s_%s", ConfigKeys.ENABLE_FTP_SYNC, tableName), true)) {

				String path = config().getString(String.format("%s_%s", ConfigKeys.FTP_SYNC_PATH, tableName));
				vertx.fileSystem().open(path, new OpenOptions().setRead(true)).onSuccess(lineStream -> {
					LOG.info(String.format(syncFtpRecordStarted, tableName));
					ApiCounter lineCounter = new ApiCounter();
					Long apiCounterResume = config().getLong(ConfigKeys.API_COUNTER_RESUME);
					Integer apiCounterFetch = config().getInteger(ConfigKeys.API_COUNTER_FETCH);
					lineCounter.setTotalNum(0L);

					RecordParser lineParser = RecordParser.newDelimited("\n", bufferedLine -> {
						lineCounter.incrementTotalNum();
					});

					lineStream.handler(lineParser).exceptionHandler(ex -> {
						LOG.error(String.format(syncFtpRecordFail, tableName), new RuntimeException(ex));
						promise.fail(ex);
					}).endHandler(v -> {
						lineStream.close();
						Long lines = lineCounter.getTotalNum();

						ApiCounter apiCounter = new ApiCounter();
						apiCounter.setTotalNum(0L);
						apiCounter.setQueueNum(0L);

						SiteRequestEnUS siteRequest = new SiteRequestEnUS();
						siteRequest.setConfig(config());
						siteRequest.initDeepSiteRequestEnUS(siteRequest);

						ApiRequest apiRequest = new ApiRequest();
						apiRequest.setRows(apiCounterFetch.intValue());
						apiRequest.setNumFound(lines);
						apiRequest.setNumPATCH(apiCounter.getQueueNum());
						apiRequest.setCreated(ZonedDateTime.now(ZoneId.of(config().getString(ConfigKeys.SITE_ZONE))));
						apiRequest.initDeepApiRequest(siteRequest);
						vertx.eventBus().publish(String.format("websocket%s", tableName), JsonObject.mapFrom(apiRequest));
	
	
						vertx.fileSystem().open(path, new OpenOptions().setRead(true)).onSuccess(stream -> {
							RecordParser recordParser = RecordParser.newDelimited("\n");
							recordParser.handler(bufferedLine -> {
								try {
									apiCounter.incrementQueueNum();
									String[] values = bufferedLine.toString().trim().split("\t");
									String pkStr = null;
	
									JsonObject body = null;
									if(values.length >= 15 && "TrafficStop".equals(tableName)) {
										pkStr = values[0];
										body = new JsonObject()
												.put("saves", new JsonArray()
														.add(TrafficStop.VAR_inheritPk)
														.add(TrafficStop.VAR_agencyTitle)
														.add(TrafficStop.VAR_stateAbbreviation)
														.add(TrafficStop.VAR_stopDateTime)
														.add(TrafficStop.VAR_stopPurposeNum)
														.add(TrafficStop.VAR_stopActionNum)
														.add(TrafficStop.VAR_stopDriverArrest)
														.add(TrafficStop.VAR_stopPassengerArrest)
														.add(TrafficStop.VAR_stopEncounterForce)
														.add(TrafficStop.VAR_stopEngageForce)
														.add(TrafficStop.VAR_stopOfficerInjury)
														.add(TrafficStop.VAR_stopDriverInjury)
														.add(TrafficStop.VAR_stopPassengerInjury)
														.add(TrafficStop.VAR_stopOfficerId)
														.add(TrafficStop.VAR_stopLocationId)
														.add(TrafficStop.VAR_stopCityId)
														)
												.put(TrafficStop.VAR_pk, values[0])
												.put(TrafficStop.VAR_agencyTitle, values[1])
												.put(TrafficStop.VAR_stateAbbreviation, stateAbbreviation)
												.put(TrafficStop.VAR_stopDateTime, values[2])
												.put(TrafficStop.VAR_stopPurposeNum, values[3])
												.put(TrafficStop.VAR_stopActionNum, values[4])
												.put(TrafficStop.VAR_stopDriverArrest, BooleanUtils.toBoolean(values[5], "1", "0"))
												.put(TrafficStop.VAR_stopPassengerArrest, BooleanUtils.toBoolean(values[6], "1", "0"))
												.put(TrafficStop.VAR_stopEncounterForce, BooleanUtils.toBoolean(values[7], "1", "0"))
												.put(TrafficStop.VAR_stopEngageForce, BooleanUtils.toBoolean(values[8], "1", "0"))
												.put(TrafficStop.VAR_stopOfficerInjury, BooleanUtils.toBoolean(values[9], "1", "0"))
												.put(TrafficStop.VAR_stopDriverInjury, BooleanUtils.toBoolean(values[10], "1", "0"))
												.put(TrafficStop.VAR_stopPassengerInjury, BooleanUtils.toBoolean(values[11], "1", "0"))
												.put(TrafficStop.VAR_stopOfficerId, values[12])
												.put(TrafficStop.VAR_stopLocationId, values[13])
												.put(TrafficStop.VAR_stopCityId, values[14])
												;
									} else if(values.length >= 7 && "TrafficPerson".equals(tableName)) {
										pkStr = values[0];
										body = new JsonObject()
												.put("saves", new JsonArray()
														.add(TrafficPerson.VAR_inheritPk)
														.add(TrafficPerson.VAR_stopId)
														.add(TrafficPerson.VAR_personTypeId)
														.add(TrafficPerson.VAR_personAge)
														.add(TrafficPerson.VAR_personGenderId)
														.add(TrafficPerson.VAR_personEthnicityId)
														.add(TrafficPerson.VAR_personRaceId)
														)
												.put(TrafficPerson.VAR_pk, values[0])
												.put(TrafficPerson.VAR_stopId, values[1])
												.put(TrafficPerson.VAR_personTypeId, values[2])
												.put(TrafficPerson.VAR_personAge, values[3])
												.put(TrafficPerson.VAR_personGenderId, values[4])
												.put(TrafficPerson.VAR_personEthnicityId, values[5])
												.put(TrafficPerson.VAR_personRaceId, values[6])
												;
									} else if(values.length >= 11 && "TrafficSearch".equals(tableName)) {
										pkStr = values[0];
										body = new JsonObject()
												.put("saves", new JsonArray()
														.add(TrafficSearch.VAR_inheritPk)
														.add(TrafficSearch.VAR_personId)
														.add(TrafficSearch.VAR_searchTypeNum)
														.add(TrafficSearch.VAR_searchVehicle)
														.add(TrafficSearch.VAR_searchDriver)
														.add(TrafficSearch.VAR_searchPassenger)
														.add(TrafficSearch.VAR_searchProperty)
														.add(TrafficSearch.VAR_searchVehicleSiezed)
														.add(TrafficSearch.VAR_searchPersonalPropertySiezed)
														.add(TrafficSearch.VAR_searchOtherPropertySiezed)
														)
												.put(TrafficSearch.VAR_pk, values[0])
												.put(TrafficSearch.VAR_personId, values[1])
												.put(TrafficSearch.VAR_searchTypeNum, values[3])
												.put(TrafficSearch.VAR_searchVehicle, values[4])
												.put(TrafficSearch.VAR_searchDriver, values[5])
												.put(TrafficSearch.VAR_searchPassenger, values[6])
												.put(TrafficSearch.VAR_searchProperty, values[7])
												.put(TrafficSearch.VAR_searchVehicleSiezed, values[8])
												.put(TrafficSearch.VAR_searchPersonalPropertySiezed, values[9])
												.put(TrafficSearch.VAR_searchOtherPropertySiezed, values[10])
												;
									} else if(values.length >= 14 && "TrafficContraband".equals(tableName)) {
										pkStr = values[0];
										body = new JsonObject()
												.put("saves", new JsonArray()
														.add(TrafficContraband.VAR_inheritPk)
														.add(TrafficContraband.VAR_searchId)
														.add(TrafficContraband.VAR_contrabandOunces)
														.add(TrafficContraband.VAR_contrabandPounds)
														.add(TrafficContraband.VAR_contrabandPints)
														.add(TrafficContraband.VAR_contrabandGallons)
														.add(TrafficContraband.VAR_contrabandDosages)
														.add(TrafficContraband.VAR_contrabandGrams)
														.add(TrafficContraband.VAR_contrabandKilos)
														.add(TrafficContraband.VAR_contrabandMoney)
														.add(TrafficContraband.VAR_contrabandWeapons)
														.add(TrafficContraband.VAR_contrabandDollarAmount)
														)
												.put(TrafficContraband.VAR_pk, values[0])
												.put(TrafficContraband.VAR_searchId, values[1])
												.put(TrafficContraband.VAR_contrabandOunces, values[4])
												.put(TrafficContraband.VAR_contrabandPounds, values[5])
												.put(TrafficContraband.VAR_contrabandPints, values[6])
												.put(TrafficContraband.VAR_contrabandGallons, values[7])
												.put(TrafficContraband.VAR_contrabandDosages, values[8])
												.put(TrafficContraband.VAR_contrabandGrams, values[9])
												.put(TrafficContraband.VAR_contrabandKilos, values[10])
												.put(TrafficContraband.VAR_contrabandMoney, values[11])
												.put(TrafficContraband.VAR_contrabandWeapons, values[12])
												.put(TrafficContraband.VAR_contrabandDollarAmount, values[13])
												;
									} else if(values.length >= 5 && "SearchBasis".equals(tableName)) {
										pkStr = values[0];
										body = new JsonObject()
												.put("saves", new JsonArray()
														.add(SearchBasis.VAR_inheritPk)
														.add(SearchBasis.VAR_searchId)
														.add(SearchBasis.VAR_searchBasisId)
														)
												.put(SearchBasis.VAR_pk, values[0])
												.put(SearchBasis.VAR_searchId, values[1])
												.put(SearchBasis.VAR_searchBasisId, values[4])
												;
									}
	
									if(body != null) {
										vertx.eventBus().request(
												String.format("opendatapolicing-enUS-%s", tableName)
												, new JsonObject().put(
														"context"
														, new JsonObject().put(
																"params"
																, new JsonObject()
																		.put("body", body)
																		.put("path", new JsonObject())
																		.put("cookie", new JsonObject())
																		.put("query", new JsonObject())
																		.put("query", new JsonObject().put("var", new JsonArray().add("refresh:false")).put("commitWithin", 10000))
														)
												)
												, new DeliveryOptions().addHeader("action", String.format("putimport%sFuture", tableName))).onSuccess(a -> {
											apiCounter.incrementTotalNum();
											apiCounter.decrementQueueNum();
											if(apiCounter.getQueueNum().compareTo(apiCounterResume) <= 0) {
//												stream.fetch(apiCounterFetch);
												recordParser.fetch(apiCounterFetch);
												apiRequest.setNumPATCH(apiCounter.getTotalNum());
												apiRequest.setTimeRemaining(apiRequest.calculateTimeRemaining());
												vertx.eventBus().publish(String.format("websocket%s", tableName), JsonObject.mapFrom(apiRequest));
											}
										}).onFailure(ex -> {
											LOG.error(String.format(syncFtpRecordFail, tableName), ex);
											promise.fail(ex);
										});
									}
								} catch (Exception ex) {
									LOG.error(String.format(syncFtpRecordFail, tableName), ex);
									promise.fail(ex);
								}
							});

//							stream.pause();
//							stream.fetch(apiCounterFetch);
							recordParser.maxRecordSize(config().getInteger(ConfigKeys.FTP_MAX_RECORD_SIZE));
							recordParser.pause();
							recordParser.fetch(apiCounterFetch);
							stream.handler(recordParser).exceptionHandler(ex -> {
								LOG.error(String.format(syncFtpRecordFail, tableName), new RuntimeException(ex));
								promise.fail(ex);
							}).endHandler(w -> {
								stream.close();
		
								LOG.info(String.format(syncFtpRecordComplete, tableName));
								promise.complete();
							});
						}).onFailure(ex -> {
							LOG.error(String.format(syncFtpRecordFail, tableName), ex);
							promise.fail(ex);
						});
					});
				}).onFailure(ex -> {
					LOG.error(String.format(syncFtpRecordFail, tableName), ex);
					promise.fail(ex);
				});
			} else {
				LOG.info(String.format(syncFtpRecordSkip, tableName));
				promise.complete();
			}
		} catch (Exception ex) {
			LOG.error(String.format(syncFtpRecordFail, tableName), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	/**	
	 * Val.Complete.enUS:Syncing database to Solr completed. 
	 * Val.Fail.enUS:Syncing database to Solr failed. 
	 * Val.Skip.enUS:Skip syncing database to Solr. 
	 **/
	private Future<Void> syncDbToSolr() {
		Promise<Void> promise = Promise.promise();
		if(config().getBoolean(ConfigKeys.ENABLE_DB_SOLR_SYNC, false)) {
			Long millis = 1000L * config().getInteger(ConfigKeys.TIMER_DB_SOLR_SYNC_IN_SECONDS, 10);
			vertx.setTimer(millis, a -> {
				syncDbToSolrRecord("TrafficStop").onSuccess(b -> {
					syncDbToSolrRecord("TrafficPerson").onSuccess(c -> {
						syncDbToSolrRecord("TrafficSearch").onSuccess(d -> {
							syncDbToSolrRecord("SearchBasis").onSuccess(e -> {
								syncDbToSolrRecord("TrafficContraband").onSuccess(f -> {
									syncAgencies().onSuccess(g -> {
										LOG.info(syncDbToSolrComplete);
										promise.complete();
									}).onFailure(ex -> {
										LOG.error(syncDbToSolrFail, ex);
										promise.fail(ex);
									});
								}).onFailure(ex -> {
									LOG.error(syncDbToSolrFail, ex);
									promise.fail(ex);
								});
							}).onFailure(ex -> {
								LOG.error(syncDbToSolrFail, ex);
								promise.fail(ex);
							});
						}).onFailure(ex -> {
							LOG.error(syncDbToSolrFail, ex);
							promise.fail(ex);
						});
					}).onFailure(ex -> {
						LOG.error(syncDbToSolrFail, ex);
						promise.fail(ex);
					});
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
	 * Sync %s data from the database to Solr. 
	 * Val.Complete.enUS:%s data sync completed. 
	 * Val.Fail.enUS:%s data sync failed. 
	 * Val.CounterResetFail.enUS:%s data sync failed to reset counter. 
	 * Val.Skip.enUS:%s data sync skipped. 
	 * Val.Started.enUS:%s data sync started. 
	 **/
	private Future<Void> syncDbToSolrRecord(String tableName) {
		Promise<Void> promise = Promise.promise();
		try {
			if(config().getBoolean(String.format("%s_%s", ConfigKeys.ENABLE_DB_SOLR_SYNC, tableName), true)) {

				LOG.info(String.format(syncDbToSolrRecordStarted, tableName));
				pgPool.withTransaction(sqlConnection -> {
					Promise<Void> promise1 = Promise.promise();
					sqlConnection.query(String.format("SELECT count(pk) FROM %s", tableName)).execute().onSuccess(countRowSet -> {
						try {
							Optional<Long> rowCountOptional = Optional.ofNullable(countRowSet.iterator().next()).map(row -> row.getLong(0));
							if(rowCountOptional.isPresent()) {
								Long apiCounterResume = config().getLong(ConfigKeys.API_COUNTER_RESUME);
								Integer apiCounterFetch = config().getInteger(ConfigKeys.API_COUNTER_FETCH);
								ApiCounter apiCounter = new ApiCounter();
	
								SiteRequestEnUS siteRequest = new SiteRequestEnUS();
								siteRequest.setConfig(config());
								siteRequest.initDeepSiteRequestEnUS(siteRequest);
		
								ApiRequest apiRequest = new ApiRequest();
								apiRequest.setRows(apiCounterFetch.intValue());
								apiRequest.setNumFound(rowCountOptional.get());
								apiRequest.setNumPATCH(apiCounter.getQueueNum());
								apiRequest.setCreated(ZonedDateTime.now(ZoneId.of(config().getString(ConfigKeys.SITE_ZONE))));
								apiRequest.initDeepApiRequest(siteRequest);
								vertx.eventBus().publish(String.format("websocket%s", tableName), JsonObject.mapFrom(apiRequest));
		
								sqlConnection.prepare(String.format("SELECT pk FROM %s", tableName)).onSuccess(preparedStatement -> {
									apiCounter.setQueueNum(0L);
									apiCounter.setTotalNum(0L);
									try {
										RowStream<Row> stream = preparedStatement.createStream(apiCounterFetch);
										stream.pause();
										stream.fetch(apiCounterFetch);
										stream.exceptionHandler(ex -> {
											LOG.error(String.format(syncDbToSolrRecordFail, tableName), new RuntimeException(ex));
											promise1.fail(ex);
										});
										stream.endHandler(v -> {
											LOG.info(String.format(syncDbToSolrRecordComplete, tableName));
											promise1.complete();
										});
										stream.handler(row -> {
											apiCounter.incrementQueueNum();
											try {
												vertx.eventBus().request(
														String.format("opendatapolicing-enUS-%s", tableName)
														, new JsonObject().put(
																"context"
																, new JsonObject().put(
																		"params"
																		, new JsonObject()
																				.put("body", new JsonObject().put("pk", row.getLong(0).toString()))
																				.put("path", new JsonObject())
																				.put("cookie", new JsonObject())
																				.put("query", new JsonObject().put("q", "*:*").put("fq", new JsonArray().add("pk:" + row.getLong(0))).put("var", new JsonArray().add("refresh:false")))
																)
														)
														, new DeliveryOptions().addHeader("action", String.format("patch%sFuture", tableName))).onSuccess(a -> {
													apiCounter.incrementTotalNum();
													apiCounter.decrementQueueNum();
													if(apiCounter.getQueueNum().compareTo(apiCounterResume) == 0) {
														stream.fetch(apiCounterFetch);
														apiRequest.setNumPATCH(apiCounter.getTotalNum());
														apiRequest.setTimeRemaining(apiRequest.calculateTimeRemaining());
														vertx.eventBus().publish(String.format("websocket%s", tableName), JsonObject.mapFrom(apiRequest));
													}
												}).onFailure(ex -> {
													LOG.error(String.format(syncDbToSolrRecordFail, tableName), ex);
													promise1.fail(ex);
												});
											} catch (Exception ex) {
												LOG.error(String.format(syncDbToSolrRecordFail, tableName), ex);
												promise1.fail(ex);
											}
										});
									} catch (Exception ex) {
										LOG.error(String.format(syncDbToSolrRecordFail, tableName), ex);
										promise1.fail(ex);
									}
								}).onFailure(ex -> {
									LOG.error(String.format(syncDbToSolrRecordFail, tableName), ex);
									promise1.fail(ex);
								});
							} else {
								promise1.complete();
							}
						} catch (Exception ex) {
							LOG.error(String.format(syncDbToSolrRecordFail, tableName), ex);
							promise1.fail(ex);
						}
					}).onFailure(ex -> {
						LOG.error(String.format(syncDbToSolrRecordFail, tableName), ex);
						promise1.fail(ex);
					});
					return promise1.future();
				}).onSuccess(a -> {
					promise.complete();
				}).onFailure(ex -> {
					LOG.error(String.format(syncDbToSolrRecordFail, tableName), ex);
					promise.fail(ex);
				});
			} else {
				LOG.info(String.format(syncDbToSolrRecordSkip, tableName));
				promise.complete();
			}
		} catch (Exception ex) {
			LOG.error(String.format(syncDbToSolrRecordFail, tableName), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	/**	
	 * Val.Complete.enUS:Refresh all data completed. 
	 * Val.Fail.enUS:Refresh all data failed. 
	 * Val.Skip.enUS:Refresh all data skipped. 
	 **/
	private Future<Void> refreshAllData() {
		Promise<Void> promise = Promise.promise();
		vertx.setTimer(1000 * 10, a -> {
			if(config().getBoolean(ConfigKeys.ENABLE_REFRESH_DATA, false)) {
				refreshData("TrafficContraband").onSuccess(b -> {
					refreshData("SearchBasis").onSuccess(c -> {
						refreshData("TrafficSearch").onSuccess(d -> {
							refreshData("TrafficPerson").onSuccess(e -> {
								refreshData("TrafficStop").onSuccess(f -> {
									LOG.info(refreshAllDataComplete);
									promise.complete();
								}).onFailure(ex -> {
									LOG.error(refreshAllDataFail, ex);
									promise.fail(ex);
								});
							}).onFailure(ex -> {
								LOG.error(refreshAllDataFail, ex);
								promise.fail(ex);
							});
						}).onFailure(ex -> {
							LOG.error(refreshAllDataFail, ex);
							promise.fail(ex);
						});
					}).onFailure(ex -> {
						LOG.error(refreshAllDataFail, ex);
						promise.fail(ex);
					});
				}).onFailure(ex -> {
					LOG.error(refreshAllDataFail, ex);
					promise.fail(ex);
				});
			} else {
				LOG.info(refreshAllDataSkip);
				promise.complete();
			}
		});
		return promise.future();
	}

	/**	
	 * Refresh %s data from the database to Solr. 
	 * Val.Complete.enUS:%s refresh completed. 
	 * Val.Fail.enUS:%s refresh failed. 
	 * Val.Skip.enUS:%s refresh skipped. 
	 **/
	private Future<Void> refreshData(String tableName) {
		Promise<Void> promise = Promise.promise();
		try {
			if(config().getBoolean(String.format("%s_%s", ConfigKeys.ENABLE_REFRESH_DATA, tableName), true)) {
				webClient.post(config().getInteger(ConfigKeys.AUTH_PORT), config().getString(ConfigKeys.AUTH_HOST_NAME), config().getString(ConfigKeys.AUTH_TOKEN_URI))
						.expect(ResponsePredicate.SC_OK)
						.ssl(config().getBoolean(ConfigKeys.AUTH_SSL))
						.authentication(new UsernamePasswordCredentials(config().getString(ConfigKeys.AUTH_RESOURCE), config().getString(ConfigKeys.AUTH_SECRET)))
						.putHeader("Content-Type", "application/x-www-form-urlencoded")
						.sendForm(MultiMap.caseInsensitiveMultiMap().set("grant_type", "client_credentials"))
						.onSuccess(tokenResponse -> {
					JsonObject token = tokenResponse.bodyAsJsonObject();
					JsonObject params = new JsonObject();
					params.put("body", new JsonObject());
					params.put("path", new JsonObject());
					params.put("cookie", new JsonObject());
					params.put("query", new JsonObject().put("q", "*:*").put("var", new JsonArray().add("refresh:false")));
					JsonObject context = new JsonObject().put("params", params).put("user", token);
					JsonObject json = new JsonObject().put("context", context);
					vertx.eventBus().request(String.format("opendatapolicing-enUS-%s", tableName), json, new DeliveryOptions().addHeader("action", String.format("patch%s", tableName))).onSuccess(a -> {
						LOG.info(String.format(refreshDataComplete, tableName));
						promise.complete();
					}).onFailure(ex -> {
						LOG.error(String.format(refreshDataFail, tableName), ex);
						promise.fail(ex);
					});
				}).onFailure(ex -> {
					LOG.error(String.format(refreshDataFail, tableName), ex);
					promise.fail(ex);
				});
			} else {
				LOG.info(String.format(refreshDataSkip, tableName));
				promise.complete();
			}
		} catch (Exception ex) {
			LOG.error(String.format(refreshDataFail, tableName), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	/**	
	 * Import agency data to Solr. 
	 * Val.Complete.enUS:%s refresh completed. 
	 * Val.Fail.enUS:%s refresh failed. 
	 * Val.Skip.enUS:%s refresh skipped. 
	 **/
	private Future<Void> syncAgencies() {
		Promise<Void> promise = Promise.promise();
		try {
			if(config().getBoolean(String.format("%s_%s", ConfigKeys.ENABLE_DB_SOLR_SYNC, "SiteAgency"), true)) {
				SiteRequestEnUS siteRequest = new SiteRequestEnUS();
				siteRequest.setConfig(config());
				siteRequest.setWebClient(webClient);
				siteRequest.initDeepSiteRequestEnUS(siteRequest);

				SearchList<SiteState> stateSearch = new SearchList<SiteState>();
				stateSearch.setStore(true);
				stateSearch.setQuery("*:*");
				stateSearch.setC(SiteState.class);
				stateSearch.setRows(1);
				stateSearch.addFilterQuery("inheritPk_indexed_string:NC");
				stateSearch.promiseDeepForClass(siteRequest).onSuccess(c -> {
					SiteState state = stateSearch.first();
					if(state != null) {
						SearchList<TrafficStop> stopSearch1 = new SearchList<TrafficStop>();
						stopSearch1.setStore(true);
						stopSearch1.setQuery("*:*");
						stopSearch1.setC(TrafficStop.class);
						stopSearch1.setRows(0);
						stopSearch1.addFacetField("agencyTitle_indexed_string");
						stopSearch1.set("facet.offset", 0);
						stopSearch1.set("facet.limit", FACET_LIMIT);
						stopSearch1.promiseDeepForClass(siteRequest).onSuccess(d -> {
							syncAgenciesFacets(state, stopSearch1, 0).onSuccess(a -> {
								LOG.info(String.format(syncAgenciesComplete, "SiteAgency"));
								promise.complete();
							}).onFailure(ex -> {
								LOG.error(String.format(syncAgenciesFail, "SiteAgency"), ex);
								promise.fail(ex);
							});
						}).onFailure(ex -> {
							LOG.error(String.format(syncAgenciesFail, "SiteAgency"), ex);
							promise.fail(ex);
						});
					} else {
						Exception ex = new RuntimeException("No State NC found. ");
						LOG.error(String.format(syncAgenciesFail, "SiteAgency"), ex);
						promise.fail(ex);
					}
				}).onFailure(ex -> {
					LOG.error(String.format(syncAgenciesFail, "SiteAgency"), ex);
					promise.fail(ex);
				});
			} else {
				LOG.info(String.format(syncAgenciesSkip, "SiteAgency"));
				promise.complete();
			}
		} catch (Exception ex) {
			LOG.error(String.format(syncAgenciesFail, "SiteAgency"), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	/**	
	 * Import agency facet data to Solr. 
	 * Val.Complete.enUS:%s refresh facet data completed. 
	 * Val.Fail.enUS:%s refresh facet data failed. 
	 * Val.Skip.enUS:%s refresh facet data skipped. 
	 * @param state 
	 **/  
	private Future<Void> syncAgenciesFacets(SiteState state, SearchList<TrafficStop> stopSearch1, Integer facetOffset) {
		Promise<Void> promise = Promise.promise();
		try {
			FacetField agencyTitleFacet = Optional.ofNullable(stopSearch1.getQueryResponse()).map(r -> r.getFacetField("agencyTitle_indexed_string")).orElse(new FacetField("agencyTitle_indexed_string"));
			if(agencyTitleFacet.getValueCount() > 0) {
				List<Future> futures = new ArrayList<>();

				FacetField groupNameFacet = Optional.ofNullable(stopSearch1.getQueryResponse()).map(r -> r.getFacetField("agencyTitle_indexed_string")).orElse(new FacetField("agencyTitle_indexed_string"));
				List<Count> groupNameCounts = Optional.ofNullable(groupNameFacet.getValues()).orElse(Arrays.asList());
				Integer acsApiYear = config().getInteger(ConfigKeys.ACS_API_YEAR);

				JsonArray acsArray = new JsonArray();
				webClient.get(443, "api.census.gov", String.format("/data/%s/acs/acs5?get=NAME,GEO_ID,%s,%s,%s,%s,%s,%s,%s,%s,%s&for=county:*&in=state:%s&key=%s"
								, acsApiYear
								, "B03002_001E" // total
								, "B03002_003E" // white
								, "B03002_004E" // black
								, "B03002_005E" // indigenous
								, "B03002_006E" // asian
								, "B03002_007E" // pacific islander
								, "B03002_008E" // other
								, "B03002_009E" // multi-racial
								, "B03002_012E" // latinx
								, state.getStateAcsId()
								, config().getString(ConfigKeys.ACS_API_KEY)
								))
						.expect(ResponsePredicate.SC_OK)
						.ssl(true)
						.send()
						.onSuccess(acsGetCountyResponse -> {
					JsonArray acsGetCountyJson = acsGetCountyResponse.bodyAsJsonArray();
					String replaceExpression = " \\w+, " + state.getStateName() + "$";
					acsGetCountyJson.stream().map(o -> (JsonArray)o).filter(items -> !"NAME".equals(items.getString(0))).forEach(item -> {
						String name = item.getString(0);
						name = name.replaceFirst(replaceExpression, "");
						item.remove(0);
						item.add(0, name);
						acsArray.add(item);
					});
					webClient.get(443, "api.census.gov", String.format("/data/%s/acs/acs5?get=NAME,GEO_ID,%s,%s,%s,%s,%s,%s,%s,%s,%s&for=place:*&in=state:%s&key=%s"
									, acsApiYear
									, "B03002_001E" // total
									, "B03002_003E" // white
									, "B03002_004E" // black
									, "B03002_005E" // indigenous
									, "B03002_006E" // asian
									, "B03002_007E" // pacific islander
									, "B03002_008E" // other
									, "B03002_009E" // multi-racial
									, "B03002_012E" // latinx
									, state.getStateAcsId()
									, config().getString(ConfigKeys.ACS_API_KEY)
									))
							.expect(ResponsePredicate.SC_OK)
							.ssl(true)
							.send()
							.onSuccess(acsGetPlaceResponse -> {
						JsonArray acsGetPlaceJson = acsGetPlaceResponse.bodyAsJsonArray();
						acsGetPlaceJson.stream().map(o -> (JsonArray)o).filter(items -> !"NAME".equals(items.getString(0))).forEach(item -> {
							String name = item.getString(0);
							name = name.replaceFirst(replaceExpression, "");
							item.remove(0);
							item.add(0, name);
							acsArray.add(item);
						});
						for(Count count : groupNameCounts) {
							futures.add(Future.future(promise1 -> {
			
								String agencyTitle = count.getName();
								String agencyTitleSimplified = StringUtils.replaceEach(agencyTitle, new String[] { " Police Department", " Sheriff's Office" }, new String[] { "", "" });
								JsonArray agencyAcsData = acsArray.stream().map(o -> (JsonArray)o).filter(items -> agencyTitleSimplified.equals(items.getString(0))).findFirst().orElse(null);
								JsonObject body = new JsonObject()
										.put("saves", new JsonArray()
												.add(SiteAgency.VAR_inheritPk)
												.add(SiteAgency.VAR_agencyTitle)
												.add(SiteAgency.VAR_stateAbbreviation)
												.add(SiteAgency.VAR_agencyAcsId)
												.add(SiteAgency.VAR_agencyTotal)
												.add(SiteAgency.VAR_agencyTotalWhite)
												.add(SiteAgency.VAR_agencyTotalBlack)
												.add(SiteAgency.VAR_agencyTotalIndigenous)
												.add(SiteAgency.VAR_agencyTotalAsian)
												.add(SiteAgency.VAR_agencyTotalPacificIslander)
												.add(SiteAgency.VAR_agencyTotalOther)
												.add(SiteAgency.VAR_agencyTotalLatinx)
												)
										.put(SiteAgency.VAR_agencyTitle, agencyTitle)
										.put(SiteAgency.VAR_pk, state.getStateAbbreviation() + "-" + agencyTitle)
										.put(SiteAgency.VAR_stateAbbreviation, state.getStateAbbreviation())
										;
								if(agencyAcsData != null) {
									body.put(SiteAgency.VAR_agencyTotal, agencyAcsData.getString(2));
									body.put(SiteAgency.VAR_agencyTotalWhite, agencyAcsData.getString(3));
									body.put(SiteAgency.VAR_agencyTotalBlack, agencyAcsData.getString(4));
									body.put(SiteAgency.VAR_agencyTotalIndigenous, agencyAcsData.getString(5));
									body.put(SiteAgency.VAR_agencyTotalAsian, agencyAcsData.getString(6));
									body.put(SiteAgency.VAR_agencyTotalPacificIslander, agencyAcsData.getString(7));
									body.put(SiteAgency.VAR_agencyTotalOther, agencyAcsData.getString(8));
									body.put(SiteAgency.VAR_agencyTotalLatinx, agencyAcsData.getString(10));
								}
								JsonObject params = new JsonObject();
								params.put("body", body);
								params.put("path", new JsonObject());
								params.put("cookie", new JsonObject());
								params.put("query", new JsonObject().put("q", "*:*").put("var", new JsonArray().add("refresh:false")).put("commitWithin", 10000));
								JsonObject context = new JsonObject().put("params", params);
								JsonObject json = new JsonObject().put("context", context);
								vertx.eventBus().request(String.format("opendatapolicing-enUS-%s", "SiteAgency"), json, new DeliveryOptions().addHeader("action", String.format("putimport%sFuture", "SiteAgency"))).onSuccess(a -> {
									promise1.complete();
								}).onFailure(ex -> {
									LOG.error(String.format(syncAgenciesFacetsFail, "SiteAgency"), ex);
									promise1.fail(ex);
								});
							}));
						}
						CompositeFuture.all(futures).onSuccess(a -> {
							Integer facetOffsetNext = facetOffset + FACET_LIMIT;
							stopSearch1.set("facet.offset", facetOffsetNext);
							stopSearch1.query().onSuccess(b -> {
								syncAgenciesFacets(state, stopSearch1, facetOffsetNext).onSuccess(c -> {
									LOG.info(String.format(syncAgenciesFacetsComplete, "SiteAgency"));
									promise.complete();
								}).onFailure(ex -> {
									LOG.error(String.format(syncAgenciesFacetsFail, "SiteAgency"), ex);
									promise.fail(ex);
								});
							}).onFailure(ex -> {
								LOG.error(String.format(syncAgenciesFacetsFail, "SiteAgency"), ex);
								promise.fail(ex);
							});
						}).onFailure(ex -> {
							LOG.error(String.format(syncAgenciesFacetsFail, "SiteAgency"), ex);
							promise.fail(ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format(syncAgenciesFacetsFail, "SiteAgency"), ex);
						promise.fail(ex);
					});
				}).onFailure(ex -> {
					LOG.error(String.format(syncAgenciesFacetsFail, "SiteAgency"), ex);
					promise.fail(ex);
				});
			} else {
				LOG.info(String.format(syncAgenciesFacetsComplete, "SiteAgency"));
				promise.complete();
			}
		} catch (Exception ex) {
			LOG.error(String.format(syncAgenciesFacetsFail, "SiteAgency"), ex);
			promise.fail(ex);
		}
		return promise.future();
	}
}
