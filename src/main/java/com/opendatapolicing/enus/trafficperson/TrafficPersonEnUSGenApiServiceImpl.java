package com.opendatapolicing.enus.trafficperson;

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
public class TrafficPersonEnUSGenApiServiceImpl implements TrafficPersonEnUSGenApiService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(TrafficPersonEnUSGenApiServiceImpl.class);

	protected static final String SERVICE_ADDRESS = "TrafficPersonEnUSApiServiceImpl";

	protected SiteContextEnUS siteContext;

	public TrafficPersonEnUSGenApiServiceImpl(SiteContextEnUS siteContext) {
		this.siteContext = siteContext;
	}

	// PUTImport.enUS //

	@Override
	public void putimport.enusTrafficPerson(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficPerson(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/person");
		siteRequest.setRequestMethod("PUTImport.enUS");
		try {
			LOGGER.info(String.format("putimport.enusTrafficPerson started. "));
			{
				userTrafficPerson(siteRequest, b -> {
					if(b.succeeded()) {
						putimport.enusTrafficPersonResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								LOGGER.info(String.format("putimport.enusTrafficPerson succeeded. "));
							} else {
								LOGGER.error(String.format("putimport.enusTrafficPerson failed. ", c.cause()));
								errorTrafficPerson(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("putimport.enusTrafficPerson failed. ", b.cause()));
						errorTrafficPerson(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("putimport.enusTrafficPerson failed. ", ex));
			errorTrafficPerson(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void putimport.enusTrafficPersonResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTImport.enUSTrafficPerson(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putimport.enusTrafficPersonResponse failed. ", a.cause()));
					errorTrafficPerson(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putimport.enusTrafficPersonResponse failed. ", ex));
			errorTrafficPerson(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTImport.enUSTrafficPerson(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTImport.enUSTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTMerge.enUS //

	@Override
	public void putmerge.enusTrafficPerson(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficPerson(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/person");
		siteRequest.setRequestMethod("PUTMerge.enUS");
		try {
			LOGGER.info(String.format("putmerge.enusTrafficPerson started. "));
			{
				userTrafficPerson(siteRequest, b -> {
					if(b.succeeded()) {
						putmerge.enusTrafficPersonResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								LOGGER.info(String.format("putmerge.enusTrafficPerson succeeded. "));
							} else {
								LOGGER.error(String.format("putmerge.enusTrafficPerson failed. ", c.cause()));
								errorTrafficPerson(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("putmerge.enusTrafficPerson failed. ", b.cause()));
						errorTrafficPerson(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("putmerge.enusTrafficPerson failed. ", ex));
			errorTrafficPerson(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void putmerge.enusTrafficPersonResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTMerge.enUSTrafficPerson(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putmerge.enusTrafficPersonResponse failed. ", a.cause()));
					errorTrafficPerson(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putmerge.enusTrafficPersonResponse failed. ", ex));
			errorTrafficPerson(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTMerge.enUSTrafficPerson(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTMerge.enUSTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTCopy.enUS //

	@Override
	public void putcopy.enusTrafficPerson(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficPerson(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/person");
		siteRequest.setRequestMethod("PUTCopy.enUS");
		try {
			LOGGER.info(String.format("putcopy.enusTrafficPerson started. "));
			{
				userTrafficPerson(siteRequest, b -> {
					if(b.succeeded()) {
						putcopy.enusTrafficPersonResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								LOGGER.info(String.format("putcopy.enusTrafficPerson succeeded. "));
							} else {
								LOGGER.error(String.format("putcopy.enusTrafficPerson failed. ", c.cause()));
								errorTrafficPerson(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("putcopy.enusTrafficPerson failed. ", b.cause()));
						errorTrafficPerson(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopy.enusTrafficPerson failed. ", ex));
			errorTrafficPerson(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public Future<TrafficPerson> putcopy.enusTrafficPersonFuture(SiteRequestEnUS siteRequest, JsonObject jsonObject, Handler<AsyncResult<TrafficPerson>> eventHandler) {
		Promise<TrafficPerson> promise = Promise.promise();
		try {
		} catch(Exception e) {
			LOGGER.error(String.format("putcopy.enusTrafficPersonFuture failed. ", e));
			errorTrafficPerson(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void putcopy.enusTrafficPersonResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTCopy.enUSTrafficPerson(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putcopy.enusTrafficPersonResponse failed. ", a.cause()));
					errorTrafficPerson(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopy.enusTrafficPersonResponse failed. ", ex));
			errorTrafficPerson(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTCopy.enUSTrafficPerson(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTCopy.enUSTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// POST //

	@Override
	public void postTrafficPerson(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficPerson(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/person");
		siteRequest.setRequestMethod("POST");
		try {
			LOGGER.info(String.format("postTrafficPerson started. "));
			{
				userTrafficPerson(siteRequest, b -> {
					if(b.succeeded()) {
						ApiRequest apiRequest = new ApiRequest();
						apiRequest.setRows(1);
						apiRequest.setNumFound(1L);
						apiRequest.setNumPATCH(0L);
						apiRequest.initDeepApiRequest(siteRequest);
						siteRequest.setApiRequest_(apiRequest);
						siteRequest.getVertx().eventBus().publish("websocketTrafficPerson", JsonObject.mapFrom(apiRequest).toString());
						postTrafficPersonFuture(siteRequest, false, c -> {
							if(c.succeeded()) {
								TrafficPerson trafficPerson = c.result();
								apiRequest.setPk(trafficPerson.getPk());
								postTrafficPersonResponse(trafficPerson, d -> {
										if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("postTrafficPerson succeeded. "));
									} else {
										LOGGER.error(String.format("postTrafficPerson failed. ", d.cause()));
										errorTrafficPerson(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("postTrafficPerson failed. ", c.cause()));
								errorTrafficPerson(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("postTrafficPerson failed. ", b.cause()));
						errorTrafficPerson(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("postTrafficPerson failed. ", ex));
			errorTrafficPerson(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public Future<TrafficPerson> postTrafficPersonFuture(SiteRequestEnUS siteRequest, Boolean inheritPk, Handler<AsyncResult<TrafficPerson>> eventHandler) {
		Promise<TrafficPerson> promise = Promise.promise();
		try {
			sqlConnectionTrafficPerson(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionTrafficPerson(siteRequest, b -> {
						if(b.succeeded()) {
							createTrafficPerson(siteRequest, c -> {
								if(c.succeeded()) {
									TrafficPerson trafficPerson = c.result();
									sqlPOSTTrafficPerson(trafficPerson, inheritPk, d -> {
										if(d.succeeded()) {
											defineIndexTrafficPerson(trafficPerson, e -> {
												if(e.succeeded()) {
													ApiRequest apiRequest = siteRequest.getApiRequest_();
													if(apiRequest != null) {
														apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
														trafficPerson.apiRequestTrafficPerson();
														siteRequest.getVertx().eventBus().publish("websocketTrafficPerson", JsonObject.mapFrom(apiRequest).toString());
													}
													eventHandler.handle(Future.succeededFuture(trafficPerson));
													promise.complete(trafficPerson);
												} else {
													LOGGER.error(String.format("postTrafficPersonFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOGGER.error(String.format("postTrafficPersonFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("postTrafficPersonFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("postTrafficPersonFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("postTrafficPersonFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("postTrafficPersonFuture failed. ", e));
			errorTrafficPerson(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPOSTTrafficPerson(TrafficPerson o, Boolean inheritPk, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
					LOGGER.error(String.format("sqlPOSTTrafficPerson failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPOSTTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void postTrafficPersonResponse(TrafficPerson trafficPerson, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = trafficPerson.getSiteRequest_();
		try {
			response200POSTTrafficPerson(trafficPerson, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("postTrafficPersonResponse failed. ", a.cause()));
					errorTrafficPerson(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("postTrafficPersonResponse failed. ", ex));
			errorTrafficPerson(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200POSTTrafficPerson(TrafficPerson o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200POSTTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchTrafficPerson(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficPerson(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/person");
		siteRequest.setRequestMethod("PATCH");
		try {
			LOGGER.info(String.format("patchTrafficPerson started. "));
			{
				userTrafficPerson(siteRequest, b -> {
					if(b.succeeded()) {
						patchTrafficPersonResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											aSearchTrafficPerson(siteRequest, false, true, "/api/person", "PATCH", d -> {
												if(d.succeeded()) {
													SearchList<TrafficPerson> listTrafficPerson = d.result();

													if(listTrafficPerson.getQueryResponse().getResults().getNumFound() > 1) {
														List<String> roles2 = Arrays.asList("SiteAdmin");
														if(
																!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles2)
																&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles2)
																) {
															String message = String.format("roles required: " + String.join(", ", roles2));
															LOGGER.error(message);
															errorTrafficPerson(siteRequest, eventHandler, Future.failedFuture(message));
														}
													} else {

														ApiRequest apiRequest = new ApiRequest();
														apiRequest.setRows(listTrafficPerson.getRows());
														apiRequest.setNumFound(listTrafficPerson.getQueryResponse().getResults().getNumFound());
														apiRequest.setNumPATCH(0L);
														apiRequest.initDeepApiRequest(siteRequest);
														siteRequest.setApiRequest_(apiRequest);
														siteRequest.getVertx().eventBus().publish("websocketTrafficPerson", JsonObject.mapFrom(apiRequest).toString());
														String dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));

														try {
															listPATCHTrafficPerson(apiRequest, listTrafficPerson, dt, e -> {
																if(e.succeeded()) {
																	patchTrafficPersonResponse(siteRequest, f -> {
																		if(f.succeeded()) {
																			LOGGER.info(String.format("patchTrafficPerson succeeded. "));
																			blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																		} else {
																			LOGGER.error(String.format("patchTrafficPerson failed. ", f.cause()));
																			errorTrafficPerson(siteRequest, null, f);
																		}
																	});
																} else {
																	LOGGER.error(String.format("patchTrafficPerson failed. ", e.cause()));
																	errorTrafficPerson(siteRequest, null, e);
																}
															});
														} catch(Exception ex) {
															LOGGER.error(String.format("patchTrafficPerson failed. ", ex));
															errorTrafficPerson(siteRequest, null, Future.failedFuture(ex));
														}
													}
										} else {
													LOGGER.error(String.format("patchTrafficPerson failed. ", d.cause()));
													errorTrafficPerson(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOGGER.error(String.format("patchTrafficPerson failed. ", ex));
											errorTrafficPerson(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOGGER.error(String.format("patchTrafficPerson failed. ", c.cause()));
								errorTrafficPerson(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("patchTrafficPerson failed. ", b.cause()));
						errorTrafficPerson(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("patchTrafficPerson failed. ", ex));
			errorTrafficPerson(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPATCHTrafficPerson(ApiRequest apiRequest, SearchList<TrafficPerson> listTrafficPerson, String dt, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listTrafficPerson.getSiteRequest_();
		listTrafficPerson.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForTrafficPerson(siteContext, siteRequest.getOperationRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				patchTrafficPersonFuture(o, false, a -> {
					if(a.succeeded()) {
					} else {
						errorTrafficPerson(siteRequest2, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				if(listTrafficPerson.next(dt)) {
					listPATCHTrafficPerson(apiRequest, listTrafficPerson, dt, eventHandler);
				} else {
					response200PATCHTrafficPerson(siteRequest, eventHandler);
				}
			} else {
				LOGGER.error(String.format("listPATCHTrafficPerson failed. ", a.cause()));
				errorTrafficPerson(listTrafficPerson.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<TrafficPerson> patchTrafficPersonFuture(TrafficPerson o, Boolean inheritPk, Handler<AsyncResult<TrafficPerson>> eventHandler) {
		Promise<TrafficPerson> promise = Promise.promise();
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			if(apiRequest != null && apiRequest.getNumFound() == 1L) {
				apiRequest.setOriginal(o);
				apiRequest.setPk(o.getPk());
			}
			sqlConnectionTrafficPerson(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionTrafficPerson(siteRequest, b -> {
						if(b.succeeded()) {
							sqlPATCHTrafficPerson(o, inheritPk, c -> {
								if(c.succeeded()) {
									TrafficPerson trafficPerson = c.result();
									defineIndexTrafficPerson(trafficPerson, d -> {
										if(d.succeeded()) {
											if(apiRequest != null) {
												apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
												if(apiRequest.getNumFound() == 1L) {
													trafficPerson.apiRequestTrafficPerson();
												}
												siteRequest.getVertx().eventBus().publish("websocketTrafficPerson", JsonObject.mapFrom(apiRequest).toString());
											}
											eventHandler.handle(Future.succeededFuture(trafficPerson));
											promise.complete(trafficPerson);
										} else {
											LOGGER.error(String.format("patchTrafficPersonFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("patchTrafficPersonFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("patchTrafficPersonFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("patchTrafficPersonFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("patchTrafficPersonFuture failed. ", e));
			errorTrafficPerson(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPATCHTrafficPerson(TrafficPerson o, Boolean inheritPk, Handler<AsyncResult<TrafficPerson>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Transaction tx = siteRequest.getTx();
			Long  = o.get();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			TrafficPerson o2 = new TrafficPerson();
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
					TrafficPerson o3 = new TrafficPerson();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.set();
					eventHandler.handle(Future.succeededFuture(o3));
				} else {
					LOGGER.error(String.format("sqlPATCHTrafficPerson failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPATCHTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void patchTrafficPersonResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PATCHTrafficPerson(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("patchTrafficPersonResponse failed. ", a.cause()));
					errorTrafficPerson(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("patchTrafficPersonResponse failed. ", ex));
			errorTrafficPerson(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PATCHTrafficPerson(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PATCHTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// GET.enUS //

	@Override
	public void get.enusTrafficPerson(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficPerson(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/person/{id}");
		siteRequest.setRequestMethod("GET.enUS");
		try {
			{
				userTrafficPerson(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchTrafficPerson(siteRequest, false, true, "/api/person/{id}", "GET.enUS", c -> {
							if(c.succeeded()) {
								SearchList<TrafficPerson> listTrafficPerson = c.result();
								get.enusTrafficPersonResponse(listTrafficPerson, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("get.enusTrafficPerson succeeded. "));
									} else {
										LOGGER.error(String.format("get.enusTrafficPerson failed. ", d.cause()));
										errorTrafficPerson(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("get.enusTrafficPerson failed. ", c.cause()));
								errorTrafficPerson(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("get.enusTrafficPerson failed. ", b.cause()));
						errorTrafficPerson(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("get.enusTrafficPerson failed. ", ex));
			errorTrafficPerson(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void get.enusTrafficPersonResponse(SearchList<TrafficPerson> listTrafficPerson, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficPerson.getSiteRequest_();
		try {
			response200GET.enUSTrafficPerson(listTrafficPerson, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("get.enusTrafficPersonResponse failed. ", a.cause()));
					errorTrafficPerson(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("get.enusTrafficPersonResponse failed. ", ex));
			errorTrafficPerson(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200GET.enUSTrafficPerson(SearchList<TrafficPerson> listTrafficPerson, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listTrafficPerson.getSiteRequest_();
			SolrDocumentList 