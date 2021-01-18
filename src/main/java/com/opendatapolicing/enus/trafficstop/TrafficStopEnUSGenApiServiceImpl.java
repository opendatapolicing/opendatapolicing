package com.opendatapolicing.enus.trafficstop;

import com.opendatapolicing.enus.config.SiteConfig;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.opendatapolicing.enus.context.SiteContextEnUS;
import com.opendatapolicing.enus.request.api.ApiRequest;
import com.opendatapolicing.enus.search.SearchResult;
import io.vertx.core.WorkerExecutor;
import io.vertx.ext.mail.MailClient;
import io.vertx.ext.mail.MailMessage;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import io.vertx.core.json.Json;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.commons.lang3.StringUtils;
import java.security.Principal;
import org.apache.commons.lang3.exception.ExceptionUtils;
import java.io.PrintWriter;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrDocument;
import java.util.Collection;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.Router;
import io.vertx.core.Vertx;
import io.vertx.ext.reactivestreams.ReactiveReadStream;
import io.vertx.ext.reactivestreams.ReactiveWriteStream;
import io.vertx.core.MultiMap;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.api.validation.HTTPRequestValidationHandler;
import io.vertx.ext.web.api.validation.ParameterTypeValidator;
import io.vertx.ext.web.api.validation.ValidationException;
import io.vertx.ext.web.api.contract.openapi3.OpenAPI3RouterFactory;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Transaction;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.Tuple;
import io.vertx.sqlclient.Row;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.sql.Timestamp;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.CaseInsensitiveHeaders;
import io.vertx.core.AsyncResult;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.api.OperationResponse;
import io.vertx.core.CompositeFuture;
import org.apache.http.client.utils.URLEncodedUtils;
import java.nio.charset.Charset;
import org.apache.http.NameValuePair;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.ext.auth.oauth2.KeycloakHelper;
import java.util.Optional;
import java.util.stream.Stream;
import java.net.URLDecoder;
import java.time.ZonedDateTime;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import com.opendatapolicing.enus.search.SearchList;


/**
 * Translate: false
 **/
public class TrafficStopEnUSGenApiServiceImpl implements TrafficStopEnUSGenApiService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(TrafficStopEnUSGenApiServiceImpl.class);

	protected static final String SERVICE_ADDRESS = "TrafficStopEnUSApiServiceImpl";

	protected SiteContextEnUS siteContext;

	public TrafficStopEnUSGenApiServiceImpl(SiteContextEnUS siteContext) {
		this.siteContext = siteContext;
	}

	// PUTImport.enUS //

	@Override
	public void putimport.enusTrafficStop(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficStop(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/traffic-stop");
		siteRequest.setRequestMethod("PUTImport.enUS");
		try {
			LOGGER.info(String.format("putimport.enusTrafficStop started. "));
			{
				userTrafficStop(siteRequest, b -> {
					if(b.succeeded()) {
						putimport.enusTrafficStopResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								LOGGER.info(String.format("putimport.enusTrafficStop succeeded. "));
							} else {
								LOGGER.error(String.format("putimport.enusTrafficStop failed. ", c.cause()));
								errorTrafficStop(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("putimport.enusTrafficStop failed. ", b.cause()));
						errorTrafficStop(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("putimport.enusTrafficStop failed. ", ex));
			errorTrafficStop(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void putimport.enusTrafficStopResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTImport.enUSTrafficStop(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putimport.enusTrafficStopResponse failed. ", a.cause()));
					errorTrafficStop(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putimport.enusTrafficStopResponse failed. ", ex));
			errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTImport.enUSTrafficStop(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTImport.enUSTrafficStop failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTMerge.enUS //

	@Override
	public void putmerge.enusTrafficStop(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficStop(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/traffic-stop");
		siteRequest.setRequestMethod("PUTMerge.enUS");
		try {
			LOGGER.info(String.format("putmerge.enusTrafficStop started. "));
			{
				userTrafficStop(siteRequest, b -> {
					if(b.succeeded()) {
						putmerge.enusTrafficStopResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								LOGGER.info(String.format("putmerge.enusTrafficStop succeeded. "));
							} else {
								LOGGER.error(String.format("putmerge.enusTrafficStop failed. ", c.cause()));
								errorTrafficStop(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("putmerge.enusTrafficStop failed. ", b.cause()));
						errorTrafficStop(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("putmerge.enusTrafficStop failed. ", ex));
			errorTrafficStop(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void putmerge.enusTrafficStopResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTMerge.enUSTrafficStop(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putmerge.enusTrafficStopResponse failed. ", a.cause()));
					errorTrafficStop(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putmerge.enusTrafficStopResponse failed. ", ex));
			errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTMerge.enUSTrafficStop(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTMerge.enUSTrafficStop failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTCopy.enUS //

	@Override
	public void putcopy.enusTrafficStop(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficStop(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/traffic-stop");
		siteRequest.setRequestMethod("PUTCopy.enUS");
		try {
			LOGGER.info(String.format("putcopy.enusTrafficStop started. "));
			{
				userTrafficStop(siteRequest, b -> {
					if(b.succeeded()) {
						putcopy.enusTrafficStopResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								LOGGER.info(String.format("putcopy.enusTrafficStop succeeded. "));
							} else {
								LOGGER.error(String.format("putcopy.enusTrafficStop failed. ", c.cause()));
								errorTrafficStop(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("putcopy.enusTrafficStop failed. ", b.cause()));
						errorTrafficStop(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopy.enusTrafficStop failed. ", ex));
			errorTrafficStop(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public Future<TrafficStop> putcopy.enusTrafficStopFuture(SiteRequestEnUS siteRequest, JsonObject jsonObject, Handler<AsyncResult<TrafficStop>> eventHandler) {
		Promise<TrafficStop> promise = Promise.promise();
		try {
		} catch(Exception e) {
			LOGGER.error(String.format("putcopy.enusTrafficStopFuture failed. ", e));
			errorTrafficStop(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void putcopy.enusTrafficStopResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTCopy.enUSTrafficStop(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putcopy.enusTrafficStopResponse failed. ", a.cause()));
					errorTrafficStop(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopy.enusTrafficStopResponse failed. ", ex));
			errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTCopy.enUSTrafficStop(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTCopy.enUSTrafficStop failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// POST //

	@Override
	public void postTrafficStop(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficStop(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/traffic-stop");
		siteRequest.setRequestMethod("POST");
		try {
			LOGGER.info(String.format("postTrafficStop started. "));
			{
				userTrafficStop(siteRequest, b -> {
					if(b.succeeded()) {
						ApiRequest apiRequest = new ApiRequest();
						apiRequest.setRows(1);
						apiRequest.setNumFound(1L);
						apiRequest.setNumPATCH(0L);
						apiRequest.initDeepApiRequest(siteRequest);
						siteRequest.setApiRequest_(apiRequest);
						siteRequest.getVertx().eventBus().publish("websocketTrafficStop", JsonObject.mapFrom(apiRequest).toString());
						postTrafficStopFuture(siteRequest, false, c -> {
							if(c.succeeded()) {
								TrafficStop trafficStop = c.result();
								apiRequest.setPk(trafficStop.getPk());
								postTrafficStopResponse(trafficStop, d -> {
										if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("postTrafficStop succeeded. "));
									} else {
										LOGGER.error(String.format("postTrafficStop failed. ", d.cause()));
										errorTrafficStop(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("postTrafficStop failed. ", c.cause()));
								errorTrafficStop(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("postTrafficStop failed. ", b.cause()));
						errorTrafficStop(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("postTrafficStop failed. ", ex));
			errorTrafficStop(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public Future<TrafficStop> postTrafficStopFuture(SiteRequestEnUS siteRequest, Boolean inheritPk, Handler<AsyncResult<TrafficStop>> eventHandler) {
		Promise<TrafficStop> promise = Promise.promise();
		try {
			sqlConnectionTrafficStop(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionTrafficStop(siteRequest, b -> {
						if(b.succeeded()) {
							createTrafficStop(siteRequest, c -> {
								if(c.succeeded()) {
									TrafficStop trafficStop = c.result();
									sqlPOSTTrafficStop(trafficStop, inheritPk, d -> {
										if(d.succeeded()) {
											defineIndexTrafficStop(trafficStop, e -> {
												if(e.succeeded()) {
													ApiRequest apiRequest = siteRequest.getApiRequest_();
													if(apiRequest != null) {
														apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
														trafficStop.apiRequestTrafficStop();
														siteRequest.getVertx().eventBus().publish("websocketTrafficStop", JsonObject.mapFrom(apiRequest).toString());
													}
													eventHandler.handle(Future.succeededFuture(trafficStop));
													promise.complete(trafficStop);
												} else {
													LOGGER.error(String.format("postTrafficStopFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOGGER.error(String.format("postTrafficStopFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("postTrafficStopFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("postTrafficStopFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("postTrafficStopFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("postTrafficStopFuture failed. ", e));
			errorTrafficStop(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPOSTTrafficStop(TrafficStop o, Boolean inheritPk, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Transaction tx = siteRequest.getTx();
			Long  = o.get();
			JsonObject jsonObject = siteRequest.getJsonObject();
			List<Future> futures = new ArrayList<>();

			if(siteRequest.getSessionId() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContextEnUS.SQL_setD
				, Tuple.of(, "sessionId", siteRequest.getSessionId())
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));
			}
			if(siteRequest.getUserId() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContextEnUS.SQL_setD
				, Tuple.of(, "userId", siteRequest.getUserId())
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));
			}
			if(siteRequest.getUserKey() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContextEnUS.SQL_setD
				, Tuple.of(, "userKey", siteRequest.getUserKey().toString())
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));
			}

			if(jsonObject != null) {
				Set<String> entityVars = jsonObject.fieldNames();
				for(String entityVar : entityVars) {
					switch(entityVar) {
					}
				}
			}
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture());
				} else {
					LOGGER.error(String.format("sqlPOSTTrafficStop failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPOSTTrafficStop failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void postTrafficStopResponse(TrafficStop trafficStop, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = trafficStop.getSiteRequest_();
		try {
			response200POSTTrafficStop(trafficStop, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("postTrafficStopResponse failed. ", a.cause()));
					errorTrafficStop(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("postTrafficStopResponse failed. ", ex));
			errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200POSTTrafficStop(TrafficStop o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200POSTTrafficStop failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchTrafficStop(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficStop(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/traffic-stop");
		siteRequest.setRequestMethod("PATCH");
		try {
			LOGGER.info(String.format("patchTrafficStop started. "));
			{
				userTrafficStop(siteRequest, b -> {
					if(b.succeeded()) {
						patchTrafficStopResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											aSearchTrafficStop(siteRequest, false, true, "/api/traffic-stop", "PATCH", d -> {
												if(d.succeeded()) {
													SearchList<TrafficStop> listTrafficStop = d.result();

													if(listTrafficStop.getQueryResponse().getResults().getNumFound() > 1) {
														List<String> roles2 = Arrays.asList("SiteAdmin");
														if(
																!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles2)
																&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles2)
																) {
															String message = String.format("roles required: " + String.join(", ", roles2));
															LOGGER.error(message);
															errorTrafficStop(siteRequest, eventHandler, Future.failedFuture(message));
														}
													} else {

														ApiRequest apiRequest = new ApiRequest();
														apiRequest.setRows(listTrafficStop.getRows());
														apiRequest.setNumFound(listTrafficStop.getQueryResponse().getResults().getNumFound());
														apiRequest.setNumPATCH(0L);
														apiRequest.initDeepApiRequest(siteRequest);
														siteRequest.setApiRequest_(apiRequest);
														siteRequest.getVertx().eventBus().publish("websocketTrafficStop", JsonObject.mapFrom(apiRequest).toString());
														String dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));

														try {
															listPATCHTrafficStop(apiRequest, listTrafficStop, dt, e -> {
																if(e.succeeded()) {
																	patchTrafficStopResponse(siteRequest, f -> {
																		if(f.succeeded()) {
																			LOGGER.info(String.format("patchTrafficStop succeeded. "));
																			blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																		} else {
																			LOGGER.error(String.format("patchTrafficStop failed. ", f.cause()));
																			errorTrafficStop(siteRequest, null, f);
																		}
																	});
																} else {
																	LOGGER.error(String.format("patchTrafficStop failed. ", e.cause()));
																	errorTrafficStop(siteRequest, null, e);
																}
															});
														} catch(Exception ex) {
															LOGGER.error(String.format("patchTrafficStop failed. ", ex));
															errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
														}
													}
										} else {
													LOGGER.error(String.format("patchTrafficStop failed. ", d.cause()));
													errorTrafficStop(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOGGER.error(String.format("patchTrafficStop failed. ", ex));
											errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOGGER.error(String.format("patchTrafficStop failed. ", c.cause()));
								errorTrafficStop(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("patchTrafficStop failed. ", b.cause()));
						errorTrafficStop(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("patchTrafficStop failed. ", ex));
			errorTrafficStop(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPATCHTrafficStop(ApiRequest apiRequest, SearchList<TrafficStop> listTrafficStop, String dt, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listTrafficStop.getSiteRequest_();
		listTrafficStop.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForTrafficStop(siteContext, siteRequest.getOperationRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				patchTrafficStopFuture(o, false, a -> {
					if(a.succeeded()) {
					} else {
						errorTrafficStop(siteRequest2, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				if(listTrafficStop.next(dt)) {
					listPATCHTrafficStop(apiRequest, listTrafficStop, dt, eventHandler);
				} else {
					response200PATCHTrafficStop(siteRequest, eventHandler);
				}
			} else {
				LOGGER.error(String.format("listPATCHTrafficStop failed. ", a.cause()));
				errorTrafficStop(listTrafficStop.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<TrafficStop> patchTrafficStopFuture(TrafficStop o, Boolean inheritPk, Handler<AsyncResult<TrafficStop>> eventHandler) {
		Promise<TrafficStop> promise = Promise.promise();
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			if(apiRequest != null && apiRequest.getNumFound() == 1L) {
				apiRequest.setOriginal(o);
				apiRequest.setPk(o.getPk());
			}
			sqlConnectionTrafficStop(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionTrafficStop(siteRequest, b -> {
						if(b.succeeded()) {
							sqlPATCHTrafficStop(o, inheritPk, c -> {
								if(c.succeeded()) {
									TrafficStop trafficStop = c.result();
									defineIndexTrafficStop(trafficStop, d -> {
										if(d.succeeded()) {
											if(apiRequest != null) {
												apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
												if(apiRequest.getNumFound() == 1L) {
													trafficStop.apiRequestTrafficStop();
												}
												siteRequest.getVertx().eventBus().publish("websocketTrafficStop", JsonObject.mapFrom(apiRequest).toString());
											}
											eventHandler.handle(Future.succeededFuture(trafficStop));
											promise.complete(trafficStop);
										} else {
											LOGGER.error(String.format("patchTrafficStopFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("patchTrafficStopFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("patchTrafficStopFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("patchTrafficStopFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("patchTrafficStopFuture failed. ", e));
			errorTrafficStop(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPATCHTrafficStop(TrafficStop o, Boolean inheritPk, Handler<AsyncResult<TrafficStop>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Transaction tx = siteRequest.getTx();
			Long  = o.get();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			TrafficStop o2 = new TrafficStop();
			List<Future> futures = new ArrayList<>();

			if(o.getUserId() == null && siteRequest.getUserId() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContextEnUS.SQL_setD
							, Tuple.of(, "userId", siteRequest.getUserId())
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));
			}
			if(o.getUserKey() == null && siteRequest.getUserKey() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContextEnUS.SQL_setD
				, Tuple.of(, "userKey", siteRequest.getUserKey().toString())
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));
			}

			for(String methodName : methodNames) {
				switch(methodName) {
				}
			}
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					TrafficStop o3 = new TrafficStop();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.set();
					eventHandler.handle(Future.succeededFuture(o3));
				} else {
					LOGGER.error(String.format("sqlPATCHTrafficStop failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPATCHTrafficStop failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void patchTrafficStopResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PATCHTrafficStop(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("patchTrafficStopResponse failed. ", a.cause()));
					errorTrafficStop(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("patchTrafficStopResponse failed. ", ex));
			errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PATCHTrafficStop(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PATCHTrafficStop failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// GET.enUS //

	@Override
	public void get.enusTrafficStop(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficStop(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/traffic-stop/{id}");
		siteRequest.setRequestMethod("GET.enUS");
		try {
			{
				userTrafficStop(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchTrafficStop(siteRequest, false, true, "/api/traffic-stop/{id}", "GET.enUS", c -> {
							if(c.succeeded()) {
								SearchList<TrafficStop> listTrafficStop = c.result();
								get.enusTrafficStopResponse(listTrafficStop, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("get.enusTrafficStop succeeded. "));
									} else {
										LOGGER.error(String.format("get.enusTrafficStop failed. ", d.cause()));
										errorTrafficStop(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("get.enusTrafficStop failed. ", c.cause()));
								errorTrafficStop(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("get.enusTrafficStop failed. ", b.cause()));
						errorTrafficStop(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("get.enusTrafficStop failed. ", ex));
			errorTrafficStop(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void get.enusTrafficStopResponse(SearchList<TrafficStop> listTrafficStop, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficStop.getSiteRequest_();
		try {
			response200GET.enUSTrafficStop(listTrafficStop, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("get.enusTrafficStopResponse failed. ", a.cause()));
					errorTrafficStop(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("get.enusTrafficStopResponse failed. ", ex));
			errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200GET.enUSTrafficStop(SearchList<TrafficStop> listTrafficStop, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listTrafficStop.getSiteRequest_();
			SolrDocumentList solrDocuments = listTrafficStop.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listTrafficStop.getList().stream().findFirst().orElse(null));
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200GET.enUSTrafficStop failed. ", e));
			eventHandler.handle(Futur