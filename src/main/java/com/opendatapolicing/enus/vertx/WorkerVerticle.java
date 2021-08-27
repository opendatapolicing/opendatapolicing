package com.opendatapolicing.enus.vertx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
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

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
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
import io.vertx.core.buffer.Buffer;
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

	public static final Integer INT_COMMIT_WITHIN = 10000;

	public static final String STR_NEW_LINE = "\n";

	public static final String STR_TAB = "\t";

	public static final Integer INT_ZERO = 0;

	public static final Long LONG_ZERO = 0L;

	private Integer jdbcMaxPoolSize;

	private Integer jdbcMaxWaitQueueSize;

//	/**
//	 * A io.vertx.ext.jdbc.JDBCClient for connecting to the relational database PostgreSQL. 
//	 **/
//	private PgPool pgPool;

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
//				configureData().compose(b -> 
					configureSharedWorkerExecutor().compose(c -> 
						configureEmail().compose(d -> 
							importData().compose(e -> 
//								syncDbToSolr().compose(f -> 
									syncFtp().compose(g -> 
										refreshAllData()
									)
//								)
							)
						)
					)
//				)
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
//
//	/**	
//	 * 
//	 * Val.ConnectionError.enUS:Could not open the database client connection. 
//	 * Val.ConnectionSuccess.enUS:The database client connection was successful. 
//	 * 
//	 * Val.InitError.enUS:Could not initialize the database tables. 
//	 * Val.InitSuccess.enUS:The database tables were created successfully. 
//	 * 
//	 *	Configure shared database connections across the cluster for massive scaling of the application. 
//	 *	Return a promise that configures a shared database client connection. 
//	 *	Load the database configuration into a shared io.vertx.ext.jdbc.JDBCClient for a scalable, clustered datasource connection pool. 
//	 *	Initialize the database tables if not already created for the first time. 
//	 **/
//	private Future<Void> configureData() {
//		Promise<Void> promise = Promise.promise();
//		try {
//			PgConnectOptions pgOptions = new PgConnectOptions();
//			pgOptions.setPort(config().getInteger(ConfigKeys.JDBC_PORT));
//			pgOptions.setHost(config().getString(ConfigKeys.JDBC_HOST));
//			pgOptions.setDatabase(config().getString(ConfigKeys.JDBC_DATABASE));
//			pgOptions.setUser(config().getString(ConfigKeys.JDBC_USERNAME));
//			pgOptions.setPassword(config().getString(ConfigKeys.JDBC_PASSWORD));
//			pgOptions.setIdleTimeout(config().getInteger(ConfigKeys.JDBC_MAX_IDLE_TIME, 10));
//			pgOptions.setIdleTimeoutUnit(TimeUnit.SECONDS);
//			pgOptions.setConnectTimeout(config().getInteger(ConfigKeys.JDBC_CONNECT_TIMEOUT, 5));
//
//			PoolOptions poolOptions = new PoolOptions();
//			jdbcMaxPoolSize = config().getInteger(ConfigKeys.JDBC_MAX_POOL_SIZE, 1);
//			jdbcMaxWaitQueueSize = config().getInteger(ConfigKeys.JDBC_MAX_WAIT_QUEUE_SIZE, 10);
//			poolOptions.setMaxSize(jdbcMaxPoolSize);
//			poolOptions.setMaxWaitQueueSize(jdbcMaxWaitQueueSize);
//
//			pgPool = PgPool.pool(vertx, pgOptions, poolOptions);
//
//			LOG.info(configureDataInitSuccess);
//			promise.complete();
//		} catch (Exception ex) {
//			LOG.error(configureDataInitError, ex);
//			promise.fail(ex);
//		}
//
//		return promise.future();
//	}

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
		Integer commitWithin = config().getInteger(ConfigKeys.SOLR_WORKER_COMMIT_WITHIN_MILLIS);
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
										params.put("query", new JsonObject().put("commitWithin", commitWithin));
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
	 * Val.Started.enUS:Started syncing FTP files. 
	 **/
	private Future<Void> syncFtp() {
		Promise<Void> promise = Promise.promise();
		String stateAbbreviation = "NC";
		if(config().getBoolean(ConfigKeys.ENABLE_FTP_SYNC, false)) {
			LOG.info(syncFtpStarted);
			Long millis = 1000L * config().getInteger(ConfigKeys.TIMER_FTP_SYNC_IN_SECONDS, 10);
			vertx.setTimer(millis, a -> {
				workerExecutor.executeBlocking(blockingCodeHandler -> {
					syncFtpDownloadExtract().onSuccess(b -> {
						syncFtpRecord("TrafficStop", stateAbbreviation).onSuccess(c -> {
							syncFtpRecord("TrafficPerson", stateAbbreviation).onSuccess(d -> {
								syncFtpRecord("TrafficSearch", stateAbbreviation).onSuccess(e -> {
									syncFtpRecord("SearchBasis", stateAbbreviation).onSuccess(f -> {
										syncFtpRecord("TrafficContraband", stateAbbreviation).onSuccess(g -> {
											syncAgencies().onSuccess(h -> {
												syncFtpRecord("TrafficStop", stateAbbreviation).onSuccess(i -> {
													LOG.info(syncFtpComplete);
													promise.complete();
												}).onFailure(ex -> {
													LOG.error(syncFtpFail, ex);
													promise.fail(ex);
												});
											}).onFailure(ex -> {
												LOG.error(syncFtpFail, ex);
												promise.fail(ex);
											});
										}).onFailure(ex -> {
											LOG.error(syncFtpFail, ex);
											promise.fail(ex);
										});
									}).onFailure(ex -> {
										LOG.error(syncFtpFail, ex);
										promise.fail(ex);
									});
								}).onFailure(ex -> {
									LOG.error(syncFtpFail, ex);
									promise.fail(ex);
								});
							}).onFailure(ex -> {
								LOG.error(syncFtpFail, ex);
								promise.fail(ex);
							});
						}).onFailure(ex -> {
							LOG.error(syncFtpFail, ex);
							promise.fail(ex);
						});
					}).onFailure(ex -> {
						LOG.error(syncFtpFail, ex);
						promise.fail(ex);
					});
				}, false).onSuccess(b -> {
					promise.complete();
				}).onFailure(ex -> {
					LOG.error(String.format(syncFtpFail), ex);
					promise.fail(ex);
				});
			});
		} else {
			LOG.info(syncFtpSkip);
			promise.complete();
		}
		return promise.future();
	}

	private Future<Void> syncFtpDownloadExtract() {
		Promise<Void> promise = Promise.promise();
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
								promise.complete();
							} catch(Exception ex) {
								LOG.error(syncFtpFail, ex);
								promise.fail(ex);
							}
						} else {
							client.noop();
							client.logout();
							RuntimeException ex = new RuntimeException(String.format("Ftp retrieve error"));
							LOG.error(syncFtpFail, ex);
							promise.fail(ex);
						}
					} else {
						RuntimeException ex = new RuntimeException(String.format("Ftp login error error"));
						LOG.error(syncFtpFail, ex);
						promise.fail(ex);
					}
				} else {
					RuntimeException ex = new RuntimeException(String.format("Ftp connect error %s", connectReply));
					LOG.error(syncFtpFail, ex);
					promise.fail(ex);
				}
			} else {
				promise.complete();
			}
		} catch(Exception ex) {
			LOG.error(syncFtpFail, ex);
			promise.fail(ex);
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
				syncFtpRecordCount(tableName, stateAbbreviation).onSuccess(apiRequest -> {
					syncFtpRecordData(tableName, stateAbbreviation, apiRequest).onSuccess(b -> {
						promise.complete();
					}).onFailure(ex -> {
						LOG.error(String.format(syncFtpRecordFail, tableName), ex);
						promise.fail(ex);
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

	private Future<ApiRequest> syncFtpRecordCount(String tableName, String stateAbbreviation) {
		Promise<ApiRequest> promise = Promise.promise();
		try {
			if(config().getBoolean(String.format("%s_%s", ConfigKeys.ENABLE_FTP_SYNC, tableName), true)) {

				String path = config().getString(String.format("%s_%s", ConfigKeys.FTP_SYNC_PATH, tableName));
				vertx.fileSystem().open(path, new OpenOptions().setRead(true)).onSuccess(lineStream -> {
					Optional.ofNullable(config().getInteger(ConfigKeys.READ_BUFFER_SIZE)).ifPresent(readBufferSize -> {
						lineStream.setReadBufferSize(readBufferSize);
					});
					LOG.info(String.format(syncFtpRecordStarted, tableName));
					ApiCounter lineCounter = new ApiCounter();
					Integer apiCounterFetch = config().getInteger(ConfigKeys.API_COUNTER_FETCH);
					lineCounter.setTotalNum(0L);

					RecordParser lineParser = RecordParser.newDelimited("\n", lineStream);
					Optional.ofNullable(config().getInteger(ConfigKeys.FTP_MAX_RECORD_SIZE)).ifPresent(ftpMaxRecordSize -> {
						lineParser.maxRecordSize(ftpMaxRecordSize);
					});

					lineParser.handler(bufferedLine -> {
						lineCounter.incrementTotalNum();
					}).exceptionHandler(ex -> {
						LOG.error(String.format(syncFtpRecordFail, tableName), new RuntimeException(ex));
						promise.fail(ex);
					}).endHandler(v -> {
						lineStream.flush();
						lineStream.close();
						Long lines = lineCounter.getTotalNum();

						SiteRequestEnUS siteRequest = new SiteRequestEnUS();
						siteRequest.setConfig(config());
						siteRequest.initDeepSiteRequestEnUS(siteRequest);

						ApiRequest apiRequest = new ApiRequest();
						apiRequest.setRows(apiCounterFetch.intValue());
						apiRequest.setNumFound(lines);
						apiRequest.setNumPATCH(0L);
						apiRequest.setCreated(ZonedDateTime.now(ZoneId.of(config().getString(ConfigKeys.SITE_ZONE))));
						apiRequest.initDeepApiRequest(siteRequest);
						vertx.eventBus().publish(String.format("websocket%s", tableName), JsonObject.mapFrom(apiRequest));

						promise.complete(apiRequest);
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

	private Future<Void> syncFtpRecordData(String tableName, String stateAbbreviation, ApiRequest apiRequest) {
		Promise<Void> promise = Promise.promise();
		try {
			String path = config().getString(String.format("%s_%s", ConfigKeys.FTP_SYNC_PATH, tableName));
			Long apiCounterResume = config().getLong(ConfigKeys.API_COUNTER_RESUME);
			Long apiCounterFetch = config().getLong(ConfigKeys.API_COUNTER_FETCH);

			ApiCounter apiCounter = new ApiCounter();
			apiCounter.setTotalNum(LONG_ZERO);
			apiCounter.setQueueNum(LONG_ZERO);

			vertx.fileSystem().open(path, new OpenOptions().setRead(true)).onSuccess(stream -> {
				Optional.ofNullable(config().getInteger(ConfigKeys.READ_BUFFER_SIZE)).ifPresent(readBufferSize -> {
					stream.setReadBufferSize(readBufferSize);
				});
				RecordParser recordParser = RecordParser.newDelimited(STR_NEW_LINE, stream);
				Optional.ofNullable(config().getInteger(ConfigKeys.FTP_MAX_RECORD_SIZE)).ifPresent(ftpMaxRecordSize -> {
					recordParser.maxRecordSize(ftpMaxRecordSize);
				});
				recordParser.pause();

				Long periodicId = vertx.setPeriodic(config().getLong(ConfigKeys.API_CHECK_TIMER_MILLIS), periodicHandler -> {
					if(apiCounter.getTotalNum().equals(apiCounter.getTotalNumOld())) {
						LOG.info("FETCH FROM PERIODIC TIMER");
						apiCounter.setTotalNum(apiCounter.getQueueNum());
						recordParser.fetch(apiCounterFetch);
						apiCounter.incrementTotalNum(apiCounterFetch);
					}
					apiCounter.setQueueNumOld(apiCounter.getQueueNum());
					apiCounter.setTotalNumOld(apiCounter.getTotalNum());
				});

				recordParser.handler(bufferedLine -> {
					try {
						syncFtpHandleBody(tableName, stateAbbreviation, bufferedLine, apiRequest, recordParser, apiCounter, apiCounterResume, apiCounterFetch).onSuccess(a -> {
						}).onFailure(ex -> {
							LOG.error(String.format(syncFtpRecordFail, tableName), ex);
							promise.fail(ex);
						});
					} catch (Exception ex) {
						LOG.error(String.format(syncFtpRecordFail, tableName), ex);
						promise.fail(ex);
					}
				}).exceptionHandler(ex -> {
					LOG.error(String.format(syncFtpRecordFail, tableName), new RuntimeException(ex));
					promise.fail(ex);
				}).endHandler(w -> {
					stream.flush();
					stream.close();
					vertx.cancelTimer(periodicId);

					LOG.info(String.format(syncFtpRecordComplete, tableName));
					promise.complete();
				});
				recordParser.fetch(apiCounterFetch);
				apiCounter.incrementTotalNum(apiCounterFetch);
			}).onFailure(ex -> {
				LOG.error(String.format(syncFtpRecordFail, tableName), ex);
				promise.fail(ex);
			});
		} catch (Exception ex) {
			LOG.error(String.format(syncFtpRecordFail, tableName), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public static final String STR_TrafficStop = "TrafficStop";
	public static final String STR_TrafficPerson = "TrafficPerson";
	public static final String STR_TrafficSearch = "TrafficSearch";
	public static final String STR_TrafficContraband = "TrafficContraband";
	public static final String STR_SearchBasis = "SearchBasis";
	public static final String STR_saves = "saves";
	public static final String STR_0 = "0";
	public static final String STR_1 = "1";
	public static final Integer INT_0 = 0;
	public static final Integer INT_1 = 1;
	public static final Integer INT_2 = 2;
	public static final Integer INT_3 = 3;
	public static final Integer INT_4 = 4;
	public static final Integer INT_5 = 5;
	public static final Integer INT_6 = 6;
	public static final Integer INT_7 = 7;
	public static final Integer INT_8 = 8;
	public static final Integer INT_9 = 9;
	public static final Integer INT_10 = 10;
	public static final Integer INT_11 = 11;
	public static final Integer INT_12 = 12;
	public static final Integer INT_13 = 13;
	public static final Integer INT_14 = 14;
	public static final Integer INT_15 = 15;
	public static final Integer INT_16 = 16;
	public static final Integer INT_17 = 17;
	public static final Integer INT_18 = 18;
	public static final Integer INT_19 = 19;

	private JsonObject syncFtpBody(String tableName, String stateAbbreviation, Buffer bufferedLine) {
		String[] values = bufferedLine.toString().trim().split(STR_TAB);
		if(STR_TrafficStop.equals(tableName)) {
			return new JsonObject()
					.put(STR_saves, new JsonArray()
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
					.put(TrafficStop.VAR_pk, values.length > INT_0 ? values[INT_0] : null)
					.put(TrafficStop.VAR_agencyTitle, values.length > INT_1 ? values[INT_1] : null)
					.put(TrafficStop.VAR_stateAbbreviation, stateAbbreviation)
					.put(TrafficStop.VAR_stopDateTime, values.length > INT_2 ? values[INT_2] : null)
					.put(TrafficStop.VAR_stopPurposeNum, values.length > INT_3 ? values[INT_3] : null)
					.put(TrafficStop.VAR_stopActionNum, values.length > INT_4 ? values[INT_4] : null)
					.put(TrafficStop.VAR_stopDriverArrest, BooleanUtils.toBoolean(values.length > INT_5 ? values[INT_5] : null, STR_1, STR_0))
					.put(TrafficStop.VAR_stopPassengerArrest, BooleanUtils.toBoolean(values.length > INT_6 ? values[INT_6] : null, STR_1, STR_0))
					.put(TrafficStop.VAR_stopEncounterForce, BooleanUtils.toBoolean(values.length > INT_7 ? values[INT_7] : null, STR_1, STR_0))
					.put(TrafficStop.VAR_stopEngageForce, BooleanUtils.toBoolean(values.length > INT_8 ? values[INT_8] : null, STR_1, STR_0))
					.put(TrafficStop.VAR_stopOfficerInjury, BooleanUtils.toBoolean(values.length > INT_9 ? values[INT_9] : null, STR_1, STR_0))
					.put(TrafficStop.VAR_stopDriverInjury, BooleanUtils.toBoolean(values.length > INT_10 ? values[INT_10] : null, STR_1, STR_0))
					.put(TrafficStop.VAR_stopPassengerInjury, BooleanUtils.toBoolean(values.length > INT_11 ? values[INT_11] : null, STR_1, STR_0))
					.put(TrafficStop.VAR_stopOfficerId, values.length > INT_12 ? values[INT_12] : null)
					.put(TrafficStop.VAR_stopLocationId, values.length > INT_13 ? values[INT_13] : null)
					.put(TrafficStop.VAR_stopCityId, values.length > INT_14 ? values[INT_14] : null)
					;
		} else if(STR_TrafficPerson.equals(tableName)) {
			return new JsonObject()
					.put(STR_saves, new JsonArray()
							.add(TrafficPerson.VAR_inheritPk)
							.add(TrafficPerson.VAR_stopId)
							.add(TrafficPerson.VAR_personTypeId)
							.add(TrafficPerson.VAR_personAge)
							.add(TrafficPerson.VAR_personGenderId)
							.add(TrafficPerson.VAR_personEthnicityId)
							.add(TrafficPerson.VAR_personRaceId)
							)
					.put(TrafficPerson.VAR_pk, values.length > INT_0 ? values[INT_0] : null)
					.put(TrafficPerson.VAR_stopId, values.length > INT_1 ? values[INT_1] : null)
					.put(TrafficPerson.VAR_personTypeId, values.length > INT_2 ? values[INT_2] : null)
					.put(TrafficPerson.VAR_personAge, values.length > INT_3 ? values[INT_3] : null)
					.put(TrafficPerson.VAR_personGenderId, values.length > INT_4 ? values[INT_4] : null)
					.put(TrafficPerson.VAR_personEthnicityId, values.length > INT_5 ? values[INT_5] : null)
					.put(TrafficPerson.VAR_personRaceId, values.length > INT_6 ? values[INT_6] : null)
					;
		} else if(STR_TrafficSearch.equals(tableName)) {
			return new JsonObject()
					.put(STR_saves, new JsonArray()
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
					.put(TrafficSearch.VAR_pk, values.length > INT_0 ? values[INT_0] : null)
					.put(TrafficSearch.VAR_personId, values.length > INT_1 ? values[INT_1] : null)
					.put(TrafficSearch.VAR_searchTypeNum, values.length > INT_3 ? values[INT_3] : null)
					.put(TrafficSearch.VAR_searchVehicle, BooleanUtils.toBoolean(values.length > INT_4 ? values[INT_4] : null, STR_1, STR_0))
					.put(TrafficSearch.VAR_searchDriver, BooleanUtils.toBoolean(values.length > INT_5 ? values[INT_5] : null, STR_1, STR_0))
					.put(TrafficSearch.VAR_searchPassenger, BooleanUtils.toBoolean(values.length > INT_6 ? values[INT_6] : null, STR_1, STR_0))
					.put(TrafficSearch.VAR_searchProperty, BooleanUtils.toBoolean(values.length > INT_7 ? values[INT_7] : null, STR_1, STR_0))
					.put(TrafficSearch.VAR_searchVehicleSiezed, BooleanUtils.toBoolean(values.length > INT_8 ? values[INT_8] : null, STR_1, STR_0))
					.put(TrafficSearch.VAR_searchPersonalPropertySiezed, BooleanUtils.toBoolean(values.length > INT_9 ? values[INT_9] : null, STR_1, STR_0))
					.put(TrafficSearch.VAR_searchOtherPropertySiezed, BooleanUtils.toBoolean(values.length > INT_10 ? values[INT_10] : null, STR_1, STR_0))
					;
		} else if(STR_TrafficContraband.equals(tableName)) {
			return new JsonObject()
					.put(STR_saves, new JsonArray()
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
					.put(TrafficContraband.VAR_pk, values.length > INT_0 ? values[INT_0] : null)
					.put(TrafficContraband.VAR_searchId, values.length > INT_1 ? values[INT_1] : null)
					.put(TrafficContraband.VAR_contrabandOunces, values.length > INT_4 ? values[INT_4] : null)
					.put(TrafficContraband.VAR_contrabandPounds, values.length > INT_5 ? values[INT_5] : null)
					.put(TrafficContraband.VAR_contrabandPints, values.length > INT_6 ? values[INT_6] : null)
					.put(TrafficContraband.VAR_contrabandGallons, values.length > INT_7 ? values[INT_7] : null)
					.put(TrafficContraband.VAR_contrabandDosages, values.length > INT_8 ? values[INT_8] : null)
					.put(TrafficContraband.VAR_contrabandGrams, values.length > INT_9 ? values[INT_9] : null)
					.put(TrafficContraband.VAR_contrabandKilos, values.length > INT_10 ? values[INT_10] : null)
					.put(TrafficContraband.VAR_contrabandMoney, values.length > INT_11 ? values[INT_11] : null)
					.put(TrafficContraband.VAR_contrabandWeapons, values.length > INT_12 ? values[INT_12] : null)
					.put(TrafficContraband.VAR_contrabandDollarAmount, values.length > INT_13 ? values[INT_13] : null)
					;
		} else if(STR_SearchBasis.equals(tableName)) {
			return new JsonObject()
					.put(STR_saves, new JsonArray()
							.add(SearchBasis.VAR_inheritPk)
							.add(SearchBasis.VAR_searchId)
							.add(SearchBasis.VAR_searchBasisId)
							)
					.put(SearchBasis.VAR_pk, values.length > INT_0 ? values[INT_0] : null)
					.put(SearchBasis.VAR_searchId, values.length > INT_1 ? values[INT_1] : null)
					.put(SearchBasis.VAR_searchBasisId, values.length > INT_4 ? values[INT_4] : null)
					;
		} else {
			return null;
		}
	}

	/**
	 * Val.EventBusName.enUS:opendatapolicing-enUS-%s
	 * Val.Context.enUS:context
	 * Val.Params.enUS:params
	 * Val.Body.enUS:body
	 * Val.Path.enUS:path
	 * Val.Cookie.enUS:cookie
	 * Val.Query.enUS:query
	 * Val.Var.enUS:var
	 * Val.RefreshFalse.enUS:refresh:false
	 * Val.CommitWithin.enUS:commitWithin
	 * Val.Action.enUS:action
	 * Val.PutImportFuture.enUS:putimport%sFuture
	 * Val.WebSocket.enUS:websocket%s
	 */
	private Future<Void> syncFtpHandleBody(String tableName, String stateAbbreviation, Buffer bufferedLine, ApiRequest apiRequest, RecordParser recordParser, ApiCounter apiCounter, Long apiCounterResume, Long apiCounterFetch) {
		Promise<Void> promise = Promise.promise();
		JsonObject body = syncFtpBody(tableName, stateAbbreviation, bufferedLine);
		if(body != null) {
			DeliveryOptions deliveryOptions = new DeliveryOptions();
			deliveryOptions.addHeader(syncFtpHandleBodyAction, String.format(syncFtpHandleBodyPutImportFuture, tableName));
			deliveryOptions.setSendTimeout(config().getInteger(ConfigKeys.VERTX_WORKER_SEND_TIMEOUT_MILLIS));
			vertx.eventBus().request(
					String.format(syncFtpHandleBodyEventBusName, tableName)
					, new JsonObject().put(
							syncFtpHandleBodyContext
							, new JsonObject().put(
									syncFtpHandleBodyParams
									, new JsonObject()
											.put(syncFtpHandleBodyBody, body)
											.put(syncFtpHandleBodyPath, new JsonObject())
											.put(syncFtpHandleBodyCookie, new JsonObject())
											.put(syncFtpHandleBodyQuery, new JsonObject())
											.put(syncFtpHandleBodyQuery, new JsonObject().put(syncFtpHandleBodyVar, new JsonArray().add(syncFtpHandleBodyRefreshFalse)).put(syncFtpHandleBodyCommitWithin, INT_COMMIT_WITHIN))
							)
					)
					, deliveryOptions).onSuccess(a -> {
				apiCounter.incrementQueueNum();
//					LOG.info("{} {} {} {} {}", apiCounter.getTotalNum(), apiCounter.getQueueNum(), apiCounterResume, apiCounterResume.compareTo(apiCounter.getTotalNum() - apiCounter.getQueueNum()) >= INT_ZERO, apiCounter.getTotalNum() - apiCounter.getQueueNum());
				if(apiCounterResume.compareTo(apiCounter.getTotalNum() - apiCounter.getQueueNum()) >= INT_ZERO) {
					recordParser.fetch(apiCounterFetch);
					apiCounter.incrementTotalNum(apiCounterFetch);
					apiRequest.setNumPATCH(apiCounter.getTotalNum());
					apiRequest.setTimeRemaining(apiRequest.calculateTimeRemaining());
					vertx.eventBus().publish(String.format(syncFtpHandleBodyWebSocket, tableName), JsonObject.mapFrom(apiRequest));
				}
				promise.complete();
			}).onFailure(ex -> {
				apiCounter.incrementQueueNum();
				if(apiCounterResume.compareTo(apiCounter.getTotalNum() - apiCounter.getQueueNum()) >= INT_ZERO) {
					recordParser.fetch(apiCounterFetch);
					apiCounter.incrementTotalNum(apiCounterFetch);
					LOG.info("FETCH Failure");
					apiRequest.setNumPATCH(apiCounter.getTotalNum());
					apiRequest.setTimeRemaining(apiRequest.calculateTimeRemaining());
					vertx.eventBus().publish(String.format(syncFtpHandleBodyWebSocket, tableName), JsonObject.mapFrom(apiRequest));
				}
				promise.complete();
			});
		} else {
			apiCounter.incrementQueueNum();
			promise.complete();
		}
		return promise.future();
	}
//
//	/**	
//	 * Val.Complete.enUS:Syncing database to Solr completed. 
//	 * Val.Fail.enUS:Syncing database to Solr failed. 
//	 * Val.Skip.enUS:Skip syncing database to Solr. 
//	 **/
//	private Future<Void> syncDbToSolr() {
//		Promise<Void> promise = Promise.promise();
//		if(config().getBoolean(ConfigKeys.ENABLE_DB_SOLR_SYNC, false)) {
//			Long millis = 1000L * config().getInteger(ConfigKeys.TIMER_DB_SOLR_SYNC_IN_SECONDS, 10);
//			vertx.setTimer(millis, a -> {
//				syncDbToSolrRecord("TrafficStop").onSuccess(b -> {
//					syncDbToSolrRecord("TrafficPerson").onSuccess(c -> {
//						syncDbToSolrRecord("TrafficSearch").onSuccess(d -> {
//							syncDbToSolrRecord("SearchBasis").onSuccess(e -> {
//								syncDbToSolrRecord("TrafficContraband").onSuccess(f -> {
//									syncAgencies().onSuccess(g -> {
//										LOG.info(syncDbToSolrComplete);
//										promise.complete();
//									}).onFailure(ex -> {
//										LOG.error(syncDbToSolrFail, ex);
//										promise.fail(ex);
//									});
//								}).onFailure(ex -> {
//									LOG.error(syncDbToSolrFail, ex);
//									promise.fail(ex);
//								});
//							}).onFailure(ex -> {
//								LOG.error(syncDbToSolrFail, ex);
//								promise.fail(ex);
//							});
//						}).onFailure(ex -> {
//							LOG.error(syncDbToSolrFail, ex);
//							promise.fail(ex);
//						});
//					}).onFailure(ex -> {
//						LOG.error(syncDbToSolrFail, ex);
//						promise.fail(ex);
//					});
//				}).onFailure(ex -> {
//					LOG.error(syncDbToSolrFail, ex);
//					promise.fail(ex);
//				});
//			});
//		} else {
//			LOG.info(syncDbToSolrSkip);
//			promise.complete();
//		}
//		return promise.future();
//	}
//
//	/**	
//	 * Sync %s data from the database to Solr. 
//	 * Val.Complete.enUS:%s data sync completed. 
//	 * Val.Fail.enUS:%s data sync failed. 
//	 * Val.CounterResetFail.enUS:%s data sync failed to reset counter. 
//	 * Val.Skip.enUS:%s data sync skipped. 
//	 * Val.Started.enUS:%s data sync started. 
//	 **/
//	private Future<Void> syncDbToSolrRecord(String tableName) {
//		Promise<Void> promise = Promise.promise();
//		try {
//			if(config().getBoolean(String.format("%s_%s", ConfigKeys.ENABLE_DB_SOLR_SYNC, tableName), true)) {
//
//				LOG.info(String.format(syncDbToSolrRecordStarted, tableName));
//				pgPool.withTransaction(sqlConnection -> {
//					Promise<Void> promise1 = Promise.promise();
//					sqlConnection.query(String.format("SELECT count(pk) FROM %s", tableName)).execute().onSuccess(countRowSet -> {
//						try {
//							Optional<Long> rowCountOptional = Optional.ofNullable(countRowSet.iterator().next()).map(row -> row.getLong(0));
//							if(rowCountOptional.isPresent()) {
//								Long apiCounterResume = config().getLong(ConfigKeys.API_COUNTER_RESUME);
//								Integer apiCounterFetch = config().getInteger(ConfigKeys.API_COUNTER_FETCH);
//								ApiCounter apiCounter = new ApiCounter();
//	
//								SiteRequestEnUS siteRequest = new SiteRequestEnUS();
//								siteRequest.setConfig(config());
//								siteRequest.initDeepSiteRequestEnUS(siteRequest);
//		
//								ApiRequest apiRequest = new ApiRequest();
//								apiRequest.setRows(apiCounterFetch.intValue());
//								apiRequest.setNumFound(rowCountOptional.get());
//								apiRequest.setNumPATCH(apiCounter.getQueueNum());
//								apiRequest.setCreated(ZonedDateTime.now(ZoneId.of(config().getString(ConfigKeys.SITE_ZONE))));
//								apiRequest.initDeepApiRequest(siteRequest);
//								vertx.eventBus().publish(String.format("websocket%s", tableName), JsonObject.mapFrom(apiRequest));
//		
//								sqlConnection.prepare(String.format("SELECT pk FROM %s", tableName)).onSuccess(preparedStatement -> {
//									apiCounter.setQueueNum(0L);
//									apiCounter.setTotalNum(0L);
//									try {
//										RowStream<Row> stream = preparedStatement.createStream(apiCounterFetch);
//										stream.pause();
//										stream.fetch(apiCounterFetch);
//										stream.exceptionHandler(ex -> {
//											LOG.error(String.format(syncDbToSolrRecordFail, tableName), new RuntimeException(ex));
//											promise1.fail(ex);
//										});
//										stream.endHandler(v -> {
//											LOG.info(String.format(syncDbToSolrRecordComplete, tableName));
//											promise1.complete();
//										});
//										stream.handler(row -> {
//											apiCounter.incrementQueueNum();
//											try {
//												vertx.eventBus().request(
//														String.format("opendatapolicing-enUS-%s", tableName)
//														, new JsonObject().put(
//																"context"
//																, new JsonObject().put(
//																		"params"
//																		, new JsonObject()
//																				.put("body", new JsonObject().put("pk", row.getLong(0).toString()))
//																				.put("path", new JsonObject())
//																				.put("cookie", new JsonObject())
//																				.put("query", new JsonObject().put("q", "*:*").put("fq", new JsonArray().add("pk:" + row.getLong(0))).put("var", new JsonArray().add("refresh:false")))
//																)
//														)
//														, new DeliveryOptions().addHeader("action", String.format("patch%sFuture", tableName))).onSuccess(a -> {
//													apiCounter.incrementTotalNum();
//													apiCounter.decrementQueueNum();
//													if(apiCounter.getQueueNum().compareTo(apiCounterResume) == INT_ZERO) {
//														stream.fetch(apiCounterFetch);
//														apiRequest.setNumPATCH(apiCounter.getTotalNum());
//														apiRequest.setTimeRemaining(apiRequest.calculateTimeRemaining());
//														vertx.eventBus().publish(String.format("websocket%s", tableName), JsonObject.mapFrom(apiRequest));
//													}
//												}).onFailure(ex -> {
//													LOG.error(String.format(syncDbToSolrRecordFail, tableName), ex);
//													promise1.fail(ex);
//												});
//											} catch (Exception ex) {
//												LOG.error(String.format(syncDbToSolrRecordFail, tableName), ex);
//												promise1.fail(ex);
//											}
//										});
//									} catch (Exception ex) {
//										LOG.error(String.format(syncDbToSolrRecordFail, tableName), ex);
//										promise1.fail(ex);
//									}
//								}).onFailure(ex -> {
//									LOG.error(String.format(syncDbToSolrRecordFail, tableName), ex);
//									promise1.fail(ex);
//								});
//							} else {
//								promise1.complete();
//							}
//						} catch (Exception ex) {
//							LOG.error(String.format(syncDbToSolrRecordFail, tableName), ex);
//							promise1.fail(ex);
//						}
//					}).onFailure(ex -> {
//						LOG.error(String.format(syncDbToSolrRecordFail, tableName), ex);
//						promise1.fail(ex);
//					});
//					return promise1.future();
//				}).onSuccess(a -> {
//					promise.complete();
//				}).onFailure(ex -> {
//					LOG.error(String.format(syncDbToSolrRecordFail, tableName), ex);
//					promise.fail(ex);
//				});
//			} else {
//				LOG.info(String.format(syncDbToSolrRecordSkip, tableName));
//				promise.complete();
//			}
//		} catch (Exception ex) {
//			LOG.error(String.format(syncDbToSolrRecordFail, tableName), ex);
//			promise.fail(ex);
//		}
//		return promise.future();
//	}

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
				stateSearch.addFilterQuery("inheritPk_indexedstored_string:NC");
				stateSearch.promiseDeepForClass(siteRequest).onSuccess(c -> {
					SiteState state = stateSearch.first();
					if(state != null) {
						SearchList<TrafficStop> stopSearch1 = new SearchList<TrafficStop>();
						stopSearch1.setStore(true);
						stopSearch1.setQuery("*:*");
						stopSearch1.setC(TrafficStop.class);
						stopSearch1.setRows(0);
						stopSearch1.addFacetField("agencyTitle_indexedstored_string");
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
		Integer commitWithin = config().getInteger(ConfigKeys.SOLR_WORKER_COMMIT_WITHIN_MILLIS);
		Promise<Void> promise = Promise.promise();
		try {
			FacetField agencyTitleFacet = Optional.ofNullable(stopSearch1.getQueryResponse()).map(r -> r.getFacetField("agencyTitle_indexedstored_string")).orElse(new FacetField("agencyTitle_indexedstored_string"));
			if(agencyTitleFacet.getValueCount() > 0) {
				List<Future> futures = new ArrayList<>();

				FacetField groupNameFacet = Optional.ofNullable(stopSearch1.getQueryResponse()).map(r -> r.getFacetField("agencyTitle_indexedstored_string")).orElse(new FacetField("agencyTitle_indexedstored_string"));
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
								params.put("query", new JsonObject().put("q", "*:*").put("var", new JsonArray().add("refresh:false")).put("commitWithin", commitWithin));
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
