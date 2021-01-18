package com.opendatapolicing.enus.state;

import com.opendatapolicing.enus.config.SiteConfig;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.opendatapolicing.enus.context.SiteContextEnUS;
import com.opendatapolicing.enus.user.SiteUser;
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
import com.opendatapolicing.enus.user.SiteUserEnUSApiServiceImpl;
import com.opendatapolicing.enus.search.SearchList;
import com.opendatapolicing.enus.writer.AllWriter;


/**
 * Translate: false
 **/
public class SiteStateEnUSGenApiServiceImpl implements SiteStateEnUSGenApiService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(SiteStateEnUSGenApiServiceImpl.class);

	protected static final String SERVICE_ADDRESS = "SiteStateEnUSApiServiceImpl";

	protected SiteContextEnUS siteContext;

	public SiteStateEnUSGenApiServiceImpl(SiteContextEnUS siteContext) {
		this.siteContext = siteContext;
	}

	// PUTImport.enUS //

	@Override
	public void putimport.enusSiteState(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSiteState(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/state");
		siteRequest.setRequestMethod("PUTImport.enUS");
		try {
			LOGGER.info(String.format("putimport.enusSiteState started. "));
			{
				userSiteState(siteRequest, b -> {
					if(b.succeeded()) {
						putimport.enusSiteStateResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								LOGGER.info(String.format("putimport.enusSiteState succeeded. "));
							} else {
								LOGGER.error(String.format("putimport.enusSiteState failed. ", c.cause()));
								errorSiteState(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("putimport.enusSiteState failed. ", b.cause()));
						errorSiteState(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("putimport.enusSiteState failed. ", ex));
			errorSiteState(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void putimport.enusSiteStateResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTImport.enUSSiteState(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putimport.enusSiteStateResponse failed. ", a.cause()));
					errorSiteState(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putimport.enusSiteStateResponse failed. ", ex));
			errorSiteState(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTImport.enUSSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTImport.enUSSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTMerge.enUS //

	@Override
	public void putmerge.enusSiteState(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSiteState(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/state");
		siteRequest.setRequestMethod("PUTMerge.enUS");
		try {
			LOGGER.info(String.format("putmerge.enusSiteState started. "));
			{
				userSiteState(siteRequest, b -> {
					if(b.succeeded()) {
						putmerge.enusSiteStateResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								LOGGER.info(String.format("putmerge.enusSiteState succeeded. "));
							} else {
								LOGGER.error(String.format("putmerge.enusSiteState failed. ", c.cause()));
								errorSiteState(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("putmerge.enusSiteState failed. ", b.cause()));
						errorSiteState(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("putmerge.enusSiteState failed. ", ex));
			errorSiteState(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void putmerge.enusSiteStateResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTMerge.enUSSiteState(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putmerge.enusSiteStateResponse failed. ", a.cause()));
					errorSiteState(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putmerge.enusSiteStateResponse failed. ", ex));
			errorSiteState(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTMerge.enUSSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTMerge.enUSSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTCopy.enUS //

	@Override
	public void putcopy.enusSiteState(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSiteState(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/state");
		siteRequest.setRequestMethod("PUTCopy.enUS");
		try {
			LOGGER.info(String.format("putcopy.enusSiteState started. "));
			{
				userSiteState(siteRequest, b -> {
					if(b.succeeded()) {
						putcopy.enusSiteStateResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								LOGGER.info(String.format("putcopy.enusSiteState succeeded. "));
							} else {
								LOGGER.error(String.format("putcopy.enusSiteState failed. ", c.cause()));
								errorSiteState(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("putcopy.enusSiteState failed. ", b.cause()));
						errorSiteState(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopy.enusSiteState failed. ", ex));
			errorSiteState(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public Future<SiteState> putcopy.enusSiteStateFuture(SiteRequestEnUS siteRequest, JsonObject jsonObject, Handler<AsyncResult<SiteState>> eventHandler) {
		Promise<SiteState> promise = Promise.promise();
		try {
		} catch(Exception e) {
			LOGGER.error(String.format("putcopy.enusSiteStateFuture failed. ", e));
			errorSiteState(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void putcopy.enusSiteStateResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTCopy.enUSSiteState(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putcopy.enusSiteStateResponse failed. ", a.cause()));
					errorSiteState(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopy.enusSiteStateResponse failed. ", ex));
			errorSiteState(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTCopy.enUSSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTCopy.enUSSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// POST //

	@Override
	public void postSiteState(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSiteState(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/state");
		siteRequest.setRequestMethod("POST");
		try {
			LOGGER.info(String.format("postSiteState started. "));
			{
				userSiteState(siteRequest, b -> {
					if(b.succeeded()) {
						ApiRequest apiRequest = new ApiRequest();
						apiRequest.setRows(1);
						apiRequest.setNumFound(1L);
						apiRequest.setNumPATCH(0L);
						apiRequest.initDeepApiRequest(siteRequest);
						siteRequest.setApiRequest_(apiRequest);
						siteRequest.getVertx().eventBus().publish("websocketSiteState", JsonObject.mapFrom(apiRequest).toString());
						postSiteStateFuture(siteRequest, false, c -> {
							if(c.succeeded()) {
								SiteState siteState = c.result();
								apiRequest.setPk(siteState.getPk());
								postSiteStateResponse(siteState, d -> {
										if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("postSiteState succeeded. "));
									} else {
										LOGGER.error(String.format("postSiteState failed. ", d.cause()));
										errorSiteState(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("postSiteState failed. ", c.cause()));
								errorSiteState(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("postSiteState failed. ", b.cause()));
						errorSiteState(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("postSiteState failed. ", ex));
			errorSiteState(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public Future<SiteState> postSiteStateFuture(SiteRequestEnUS siteRequest, Boolean inheritPk, Handler<AsyncResult<SiteState>> eventHandler) {
		Promise<SiteState> promise = Promise.promise();
		try {
			sqlConnectionSiteState(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionSiteState(siteRequest, b -> {
						if(b.succeeded()) {
							createSiteState(siteRequest, c -> {
								if(c.succeeded()) {
									SiteState siteState = c.result();
									sqlPOSTSiteState(siteState, inheritPk, d -> {
										if(d.succeeded()) {
											defineIndexSiteState(siteState, e -> {
												if(e.succeeded()) {
													ApiRequest apiRequest = siteRequest.getApiRequest_();
													if(apiRequest != null) {
														apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
														siteState.apiRequestSiteState();
														siteRequest.getVertx().eventBus().publish("websocketSiteState", JsonObject.mapFrom(apiRequest).toString());
													}
													eventHandler.handle(Future.succeededFuture(siteState));
													promise.complete(siteState);
												} else {
													LOGGER.error(String.format("postSiteStateFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOGGER.error(String.format("postSiteStateFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("postSiteStateFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("postSiteStateFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("postSiteStateFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("postSiteStateFuture failed. ", e));
			errorSiteState(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPOSTSiteState(SiteState o, Boolean inheritPk, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
					LOGGER.error(String.format("sqlPOSTSiteState failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPOSTSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void postSiteStateResponse(SiteState siteState, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = siteState.getSiteRequest_();
		try {
			response200POSTSiteState(siteState, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("postSiteStateResponse failed. ", a.cause()));
					errorSiteState(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("postSiteStateResponse failed. ", ex));
			errorSiteState(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200POSTSiteState(SiteState o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200POSTSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchSiteState(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSiteState(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/state");
		siteRequest.setRequestMethod("PATCH");
		try {
			LOGGER.info(String.format("patchSiteState started. "));
			{
				userSiteState(siteRequest, b -> {
					if(b.succeeded()) {
						patchSiteStateResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											aSearchSiteState(siteRequest, false, true, "/api/state", "PATCH", d -> {
												if(d.succeeded()) {
													SearchList<SiteState> listSiteState = d.result();

													if(listSiteState.getQueryResponse().getResults().getNumFound() > 1) {
														List<String> roles2 = Arrays.asList("SiteAdmin");
														if(
																!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles2)
																&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles2)
																) {
															String message = String.format("roles required: " + String.join(", ", roles2));
															LOGGER.error(message);
															errorSiteState(siteRequest, eventHandler, Future.failedFuture(message));
														}
													} else {

														ApiRequest apiRequest = new ApiRequest();
														apiRequest.setRows(listSiteState.getRows());
														apiRequest.setNumFound(listSiteState.getQueryResponse().getResults().getNumFound());
														apiRequest.setNumPATCH(0L);
														apiRequest.initDeepApiRequest(siteRequest);
														siteRequest.setApiRequest_(apiRequest);
														siteRequest.getVertx().eventBus().publish("websocketSiteState", JsonObject.mapFrom(apiRequest).toString());
														String dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));

														try {
															listPATCHSiteState(apiRequest, listSiteState, dt, e -> {
																if(e.succeeded()) {
																	patchSiteStateResponse(siteRequest, f -> {
																		if(f.succeeded()) {
																			LOGGER.info(String.format("patchSiteState succeeded. "));
																			blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																		} else {
																			LOGGER.error(String.format("patchSiteState failed. ", f.cause()));
																			errorSiteState(siteRequest, null, f);
																		}
																	});
																} else {
																	LOGGER.error(String.format("patchSiteState failed. ", e.cause()));
																	errorSiteState(siteRequest, null, e);
																}
															});
														} catch(Exception ex) {
															LOGGER.error(String.format("patchSiteState failed. ", ex));
															errorSiteState(siteRequest, null, Future.failedFuture(ex));
														}
													}
										} else {
													LOGGER.error(String.format("patchSiteState failed. ", d.cause()));
													errorSiteState(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOGGER.error(String.format("patchSiteState failed. ", ex));
											errorSiteState(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOGGER.error(String.format("patchSiteState failed. ", c.cause()));
								errorSiteState(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("patchSiteState failed. ", b.cause()));
						errorSiteState(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("patchSiteState failed. ", ex));
			errorSiteState(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPATCHSiteState(ApiRequest apiRequest, SearchList<SiteState> listSiteState, String dt, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listSiteState.getSiteRequest_();
		listSiteState.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSiteState(siteContext, siteRequest.getOperationRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				patchSiteStateFuture(o, false, a -> {
					if(a.succeeded()) {
					} else {
						errorSiteState(siteRequest2, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				if(listSiteState.next(dt)) {
					listPATCHSiteState(apiRequest, listSiteState, dt, eventHandler);
				} else {
					response200PATCHSiteState(siteRequest, eventHandler);
				}
			} else {
				LOGGER.error(String.format("listPATCHSiteState failed. ", a.cause()));
				errorSiteState(listSiteState.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<SiteState> patchSiteStateFuture(SiteState o, Boolean inheritPk, Handler<AsyncResult<SiteState>> eventHandler) {
		Promise<SiteState> promise = Promise.promise();
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			if(apiRequest != null && apiRequest.getNumFound() == 1L) {
				apiRequest.setOriginal(o);
				apiRequest.setPk(o.getPk());
			}
			sqlConnectionSiteState(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionSiteState(siteRequest, b -> {
						if(b.succeeded()) {
							sqlPATCHSiteState(o, inheritPk, c -> {
								if(c.succeeded()) {
									SiteState siteState = c.result();
									defineIndexSiteState(siteState, d -> {
										if(d.succeeded()) {
											if(apiRequest != null) {
												apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
												if(apiRequest.getNumFound() == 1L) {
													siteState.apiRequestSiteState();
												}
												siteRequest.getVertx().eventBus().publish("websocketSiteState", JsonObject.mapFrom(apiRequest).toString());
											}
											eventHandler.handle(Future.succeededFuture(siteState));
											promise.complete(siteState);
										} else {
											LOGGER.error(String.format("patchSiteStateFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("patchSiteStateFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("patchSiteStateFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("patchSiteStateFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("patchSiteStateFuture failed. ", e));
			errorSiteState(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPATCHSiteState(SiteState o, Boolean inheritPk, Handler<AsyncResult<SiteState>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Transaction tx = siteRequest.getTx();
			Long  = o.get();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			SiteState o2 = new SiteState();
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
					SiteState o3 = new SiteState();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.set();
					eventHandler.handle(Future.succeededFuture(o3));
				} else {
					LOGGER.error(String.format("sqlPATCHSiteState failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPATCHSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void patchSiteStateResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PATCHSiteState(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("patchSiteStateResponse failed. ", a.cause()));
					errorSiteState(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("patchSiteStateResponse failed. ", ex));
			errorSiteState(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PATCHSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PATCHSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// GET.enUS //

	@Override
	public void get.enusSiteState(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSiteState(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/state/{id}");
		siteRequest.setRequestMethod("GET.enUS");
		try {
			{
				userSiteState(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchSiteState(siteRequest, false, true, "/api/state/{id}", "GET.enUS", c -> {
							if(c.succeeded()) {
								SearchList<SiteState> listSiteState = c.result();
								get.enusSiteStateResponse(listSiteState, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("get.enusSiteState succeeded. "));
									} else {
										LOGGER.error(String.format("get.enusSiteState failed. ", d.cause()));
										errorSiteState(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("get.enusSiteState failed. ", c.cause()));
								errorSiteState(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("get.enusSiteState failed. ", b.cause()));
						errorSiteState(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("get.enusSiteState failed. ", ex));
			errorSiteState(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void get.enusSiteStateResponse(SearchList<SiteState> listSiteState, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSiteState.getSiteRequest_();
		try {
			response200GET.enUSSiteState(listSiteState, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("get.enusSiteStateResponse failed. ", a.cause()));
					errorSiteState(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("get.enusSiteStateResponse failed. ", ex));
			errorSiteState(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200GET.enUSSiteState(SearchList<SiteState> listSiteState, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSiteState.getSiteRequest_();
			SolrDocumentList solrDocuments = listSiteState.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listSiteState.getList().stream().findFirst().orElse(null));
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200GET.enUSSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// Search //

	@Override
	public void searchSiteState(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSiteState(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/state");
		siteRequest.setRequestMethod("Search");
		try {
			{
				userSiteState(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchSiteState(siteRequest, false, true, "/api/state", "Search", c -> {
							if(c.succeeded()) {
								SearchList<SiteState> listSiteState = c.result();
								searchSiteStateResponse(listSiteState, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("searchSiteState succeeded. "));
									} else {
										LOGGER.error(String.format("searchSiteState failed. ", d.cause()));
										errorSiteState(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("searchSiteState failed. ", c.cause()));
								errorSiteState(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("searchSiteState failed. ", b.cause()));
						errorSiteState(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("searchSiteState failed. ", ex));
			errorSiteState(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void searchSiteStateResponse(SearchList<SiteState> listSiteState, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSiteState.getSiteRequest_();
		try {
			response200SearchSiteState(listSiteState, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("searchSiteStateResponse failed. ", a.cause()));
					errorSiteState(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchSiteStateResponse failed. ", ex));
			errorSiteState(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchSiteState(SearchList<SiteState> listSiteState, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSiteState.getSiteRequest_();
			QueryResponse responseSearch = listSiteState.getQueryResponse();
			SolrDocumentList solrDocuments = listSiteState.getSolrDocumentList();
			Long searchInMillis = Long.valueOf(responseSearch.getQTime());
			Long transmissionInMillis = responseSearch.getElapsedTime();
			Long startNum = responseSearch.getResults().getStart();
			Long foundNum = responseSearch.getResults().getNumFound();
			Integer returnedNum = responseSearch.getResults().size();
			String searchTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(searchInMillis), TimeUnit.MILLISECONDS.toMillis(searchInMillis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(searchInMillis)));
			String transmissionTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis), TimeUnit.MILLISECONDS.toMillis(transmissionInMillis) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis)));
			Exception exceptionSearch = responseSearch.getException();

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			json.put("searchTime", searchTime);
			json.put("transmissionTime", transmissionTime);
			JsonArray l = new JsonArray();
			listSiteState.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
				List<String> fls = listSiteState.getFields();
				if(fls.size() > 0) {
					Set<String> fieldNames = new HashSet<String>();
					fieldNames.addAll(json2.fieldNames());
					if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
						fieldNames.removeAll(Optional.ofNullable(json2.getJsonArray("saves")).orElse(new JsonArray()).stream().map(s -> s.toString()).collect(Collectors.toList()));
						fieldNames.remove("");
						fieldNames.remove("created");
					}
					else if(fls.size() >= 1) {
						fieldNames.removeAll(fls);
					}
					for(String fieldName : fieldNames) {
						if(!fls.contains(fieldName))
							json2.remove(fieldName);
					}
				}
				l.add(json2);
			});
			json.put("list", l);
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200SearchSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// AdminSearch.enUS //

	@Override
	public void adminsearch.enusSiteState(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSiteState(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/state");
		siteRequest.setRequestMethod("AdminSearch.enUS");
		try {
			{
				userSiteState(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchSiteState(siteRequest, false, true, "/api/state", "AdminSearch.enUS", c -> {
							if(c.succeeded()) {
								SearchList<SiteState> listSiteState = c.result();
								adminsearch.enusSiteStateResponse(listSiteState, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("adminsearch.enusSiteState succeeded. "));
									} else {
										LOGGER.error(String.format("adminsearch.enusSiteState failed. ", d.cause()));
										errorSiteState(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("adminsearch.enusSiteState failed. ", c.cause()));
								errorSiteState(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("adminsearch.enusSiteState failed. ", b.cause()));
						errorSiteState(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("adminsearch.enusSiteState failed. ", ex));
			errorSiteState(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void adminsearch.enusSiteStateResponse(SearchList<SiteState> listSiteState, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSiteState.getSiteRequest_();
		try {
			response200AdminSearch.enUSSiteState(listSiteState, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("adminsearch.enusSiteStateResponse failed. ", a.cause()));
					errorSiteState(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("adminsearch.enusSiteStateResponse failed. ", ex));
			errorSiteState(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200AdminSearch.enUSSiteState(SearchList<SiteState> listSiteState, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSiteState.getSiteRequest_();
			QueryResponse responseSearch = listSiteState.getQueryResponse();
			SolrDocumentList solrDocuments = listSiteState.getSolrDocumentList();
			Long searchInMillis = Long.valueOf(responseSearch.getQTime());
			Long transmissionInMillis = responseSearch.getElapsedTime();
			Long startNum = responseSearch.getResults().getStart();
			Long foundNum = responseSearch.getResults().getNumFound();
			Integer returnedNum = responseSearch.getResults().size();
			String searchTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(searchInMillis), TimeUnit.MILLISECONDS.toMillis(searchInMillis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(searchInMillis)));
			String transmissionTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis), TimeUnit.MILLISECONDS.toMillis(transmissionInMillis) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis)));
			Exception exceptionSearch = responseSearch.getException();

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			json.put("searchTime", searchTime);
			json.put("transmissionTime", transmissionTime);
			JsonArray l = new JsonArray();
			listSiteState.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
				List<String> fls = listSiteState.getFields();
				if(fls.size() > 0) {
					Set<String> fieldNames = new HashSet<String>();
					fieldNames.addAll(json2.fieldNames());
					if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
						fieldNames.removeAll(Optional.ofNullable(json2.getJsonArray("saves")).orElse(new JsonArray()).stream().map(s -> s.toString()).collect(Collectors.toList()));
						fieldNames.remove("");
						fieldNames.remove("created");
					}
					else if(fls.size() >= 1) {
						fieldNames.removeAll(fls);
					}
					for(String fieldName : fieldNames) {
						if(!fls.contains(fieldName))
							json2.remove(fieldName);
					}
				}
				l.add(json2);
			});
			json.put("list", l);
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200AdminSearch.enUSSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// SearchPage.enUS //

	@Override
	public void searchpage.enusSiteStateId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		searchpage.enusSiteState(operationRequest, eventHandler);
	}

	@Override
	public void searchpage.enusSiteState(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSiteState(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/state");
		siteRequest.setRequestMethod("SearchPage.enUS");
		try {
			{
				userSiteState(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchSiteState(siteRequest, false, true, "/api/state", "SearchPage.enUS", c -> {
							if(c.succeeded()) {
								SearchList<SiteState> listSiteState = c.result();
								searchpage.enusSiteStateResponse(listSiteState, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("searchpage.enusSiteState succeeded. "));
									} else {
										LOGGER.error(String.format("searchpage.enusSiteState failed. ", d.cause()));
										errorSiteState(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("searchpage.enusSiteState failed. ", c.cause()));
								errorSiteState(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("searchpage.enusSiteState failed. ", b.cause()));
						errorSiteState(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("searchpage.enusSiteState failed. ", ex));
			errorSiteState(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void searchpage.enusSiteStatePageInit(SiteStatePage page, SearchList<SiteState> listSiteState) {
	}
	public void searchpage.enusSiteStateResponse(SearchList<SiteState> listSiteState, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSiteState.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200SearchPage.enUSSiteState(listSiteState, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("searchpage.enusSiteStateResponse failed. ", a.cause()));
					errorSiteState(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchpage.enusSiteStateResponse failed. ", ex));
			errorSiteState(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchPage.enUSSiteState(SearchList<SiteState> listSiteState, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSiteState.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listSiteState.getSiteRequest_(), buffer);
			SiteStatePage page = new SiteStatePage();
			SolrDocument pageSolrDocument = new SolrDocument();
			CaseInsensitiveHeaders requestHeaders = new CaseInsensitiveHeaders();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/api/state");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listSiteState.size() == 1)
				siteRequest.setRequest(listSiteState.get(0).get());
			siteRequest.setW(w);
			page.setListSiteState(listSiteState);
			page.setSiteRequest_(siteRequest);
			searchpage.enusSiteStatePageInit(page, listSiteState);
			page.initDeepSiteStatePage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new OperationResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOGGER.error(String.format("response200SearchPage.enUSSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// General //

	public Future<SiteState> defineIndexSiteState(SiteState siteState, Handler<AsyncResult<SiteState>> eventHandler) {
		Promise<SiteState> promise = Promise.promise();
		SiteRequestEnUS siteRequest = siteState.getSiteRequest_();
		defineSiteState(siteState, c -> {
			if(c.succeeded()) {
				attributeSiteState(siteState, d -> {
					if(d.succeeded()) {
						indexSiteState(siteState, e -> {
							if(e.succeeded()) {
								sqlCommitSiteState(siteRequest, f -> {
									if(f.succeeded()) {
										sqlCloseSiteState(siteRequest, g -> {
											if(g.succeeded()) {
												refreshSiteState(siteState, h -> {
													if(h.succeeded()) {
														eventHandler.handle(Future.succeededFuture(siteState));
														promise.complete(siteState);
													} else {
														LOGGER.error(String.format("refreshSiteState failed. ", h.cause()));
														errorSiteState(siteRequest, null, h);
													}
												});
											} else {
												LOGGER.error(String.format("defineIndexSiteState failed. ", g.cause()));
												errorSiteState(siteRequest, null, g);
											}
										});
									} else {
										LOGGER.error(String.format("defineIndexSiteState failed. ", f.cause()));
										errorSiteState(siteRequest, null, f);
									}
								});
							} else {
								LOGGER.error(String.format("defineIndexSiteState failed. ", e.cause()));
								errorSiteState(siteRequest, null, e);
							}
						});
					} else {
						LOGGER.error(String.format("defineIndexSiteState failed. ", d.cause()));
						errorSiteState(siteRequest, null, d);
					}
				});
			} else {
				LOGGER.error(String.format("defineIndexSiteState failed. ", c.cause()));
				errorSiteState(siteRequest, null, c);
			}
		});
		return promise.future();
	}

	public void createSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<SiteState>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();
			String userId = siteRequest.getUserId();
			ZonedDateTime created = Optional.ofNullable(siteRequest.getJsonObject()).map(j -> j.getString("created")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())))).orElse(ZonedDateTime.now(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())));

			tx.preparedQuery(
					SiteContextEnUS.SQL_create
					, Tuple.of(SiteState.class.getCanonicalName(), userId, created.toOffsetDateTime())
					, Collectors.toList()
					, createAsync
			-> {
				if(createAsync.succeeded()) {
					Row createLine = createAsync.result().value().stream().findFirst().orElseGet(() -> null);
					Long  = createLine.getLong(0);
					SiteState o = new SiteState();
					o.set();
					o.setSiteRequest_(siteRequest);
					eventHandler.handle(Future.succeededFuture(o));
				} else {
					LOGGER.error(String.format("createSiteState failed. ", createAsync.cause()));
					eventHandler.handle(Future.failedFuture(createAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("createSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void errorSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler, AsyncResult<?> resultAsync) {
		Throwable e = resultAsync.cause();
		JsonObject json = new JsonObject()
				.put("error", new JsonObject()
				.put("message", Optional.ofNullable(e).map(Throwable::getMessage).orElse(null))
				.put("userName", siteRequest.getUserName())
				.put("userFullName", siteRequest.getUserFullName())
				.put("requestUri", siteRequest.getRequestUri())
				.put("requestMethod", siteRequest.getRequestMethod())
				.put("params", Optional.ofNullable(siteRequest.getOperationRequest()).map(o -> o.getParams()).orElse(null))
				);
		ExceptionUtils.printRootCauseStackTrace(e);
		OperationResponse responseOperation = new OperationResponse(400, "BAD REQUEST", 
				Buffer.buffer().appendString(json.encodePrettily())
				, new CaseInsensitiveHeaders().add("Content-Type", "application/json")
		);
		SiteConfig siteConfig = siteRequest.getSiteConfig_();
		SiteContextEnUS siteContext = siteRequest.getSiteContext_();
		MailClient mailClient = siteContext.getMailClient();
		MailMessage message = new MailMessage();
		message.setFrom(siteConfig.getEmailFrom());
		message.setTo(siteConfig.getEmailAdmin());
		if(e != null)
			message.setText(String.format("%s\n\n%s", json.encodePrettily(), ExceptionUtils.getStackTrace(e)));
		message.setSubject(String.format(siteConfig.getSiteBaseUrl() + " " + Optional.ofNullable(e).map(Throwable::getMessage).orElse(null)));
		WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
		workerExecutor.executeBlocking(
			blockingCodeHandler -> {
				mailClient.sendMail(message, result -> {
					if (result.succeeded()) {
						LOGGER.info(result.result());
					} else {
						LOGGER.error(result.cause());
					}
				});
			}, resultHandler -> {
			}
		);
		sqlRollbackSiteState(siteRequest, a -> {
			if(a.succeeded()) {
				LOGGER.info(String.format("sql rollback. "));
				sqlCloseSiteState(siteRequest, b -> {
					if(b.succeeded()) {
						LOGGER.info(String.format("sql close. "));
						if(eventHandler != null)
							eventHandler.handle(Future.succeededFuture(responseOperation));
					} else {
						if(eventHandler != null)
							eventHandler.handle(Future.succeededFuture(responseOperation));
					}
				});
			} else {
				if(eventHandler != null)
					eventHandler.handle(Future.succeededFuture(responseOperation));
			}
		});
	}

	public void sqlConnectionSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			PgPool pgPool = siteRequest.getSiteContext_().getPgPool();

			if(pgPool == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				pgPool.getConnection(a -> {
					if(a.succeeded()) {
						SqlConnection sqlConnection = a.result();
						siteRequest.setSqlConnection(sqlConnection);
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOGGER.error(String.format("sqlConnectionSiteState failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlTransactionSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();

			if(sqlConnection == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				Transaction tx = sqlConnection.begin();
				siteRequest.setTx(tx);
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlTransactionSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCommitSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();

			if(tx == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				tx.commit(a -> {
					if(a.succeeded()) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else if("Transaction already completed".equals(a.cause().getMessage())) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOGGER.error(String.format("sqlCommitSiteState failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlRollbackSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();

			if(tx == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				tx.rollback(a -> {
					if(a.succeeded()) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else if("Transaction already completed".equals(a.cause().getMessage())) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOGGER.error(String.format("sqlRollbackSiteState failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCloseSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();

			if(sqlConnection == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				sqlConnection.close();
				siteRequest.setSqlConnection(null);
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlCloseSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SiteRequestEnUS generateSiteRequestEnUSForSiteState(SiteContextEnUS siteContext, OperationRequest operationRequest) {
		return generateSiteRequestEnUSForSiteState(siteContext, operationRequest, null);
	}

	public SiteRequestEnUS generateSiteRequestEnUSForSiteState(SiteContextEnUS siteContext, OperationRequest operationRequest, JsonObject body) {
		Vertx vertx = siteContext.getVertx();
		SiteRequestEnUS siteRequest = new SiteRequestEnUS();
		siteRequest.setJsonObject(body);
		siteRequest.setVertx(vertx);
		siteRequest.setSiteContext_(siteContext);
		siteRequest.setSiteConfig_(siteContext.getSiteConfig());
		siteRequest.setOperationRequest(operationRequest);
		siteRequest.initDeepSiteRequestEnUS(siteRequest);

		return siteRequest;
	}

	public void userSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			String userId = siteRequest.getUserId();
			if(userId == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				sqlConnectionSiteState(siteRequest, a -> {
					if(a.succeeded()) {
						sqlTransactionSiteState(siteRequest, b -> {
							if(b.succeeded()) {
								Transaction tx = siteRequest.getTx();
								tx.preparedQuery(
										SiteContextEnUS.SQL_selectC
										, Tuple.of("com.opendatapolicing.enus.user.SiteUser", userId)
										, Collectors.toList()
										, selectCAsync
								-> {
									if(selectCAsync.succeeded()) {
										try {
											Row userValues = selectCAsync.result().value().stream().findFirst().orElse(null);
											SiteUserEnUSApiServiceImpl userService = new SiteUserEnUSApiServiceImpl(siteContext);
											if(userValues == null) {
												JsonObject userVertx = siteRequest.getOperationRequest().getUser();
												JsonObject jsonPrincipal = KeycloakHelper.parseToken(userVertx.getString("access_token"));

												JsonObject jsonObject = new JsonObject();
												jsonObject.put("userName", jsonPrincipal.getString("preferred_username"));
												jsonObject.put("userFirstName", jsonPrincipal.getString("given_name"));
												jsonObject.put("userLastName", jsonPrincipal.getString("family_name"));
												jsonObject.put("userCompleteName", jsonPrincipal.getString("name"));
												jsonObject.put("userId", jsonPrincipal.getString("sub"));
												jsonObject.put("userEmail", jsonPrincipal.getString("email"));
												userSiteStateDefine(siteRequest, jsonObject, false);

												SiteRequestEnUS siteRequest2 = new SiteRequestEnUS();
												siteRequest2.setTx(siteRequest.getTx());
												siteRequest2.setSqlConnection(siteRequest.getSqlConnection());
												siteRequest2.setJsonObject(jsonObject);
												siteRequest2.setVertx(siteRequest.getVertx());
												siteRequest2.setSiteContext_(siteContext);
												siteRequest2.setSiteConfig_(siteContext.getSiteConfig());
												siteRequest2.setUserId(siteRequest.getUserId());
												siteRequest2.initDeepSiteRequestEnUS(siteRequest);

												ApiRequest apiRequest = new ApiRequest();
												apiRequest.setRows(1);
												apiRequest.setNumFound(1L);
												apiRequest.setNumPATCH(0L);
												apiRequest.initDeepApiRequest(siteRequest2);
												siteRequest2.setApiRequest_(apiRequest);

												userService.createSiteUser(siteRequest2, c -> {
													if(c.succeeded()) {
														SiteUser siteUser = c.result();
														userService.sqlPOSTSiteUser(siteUser, false, d -> {
															if(d.succeeded()) {
																userService.defineIndexSiteUser(siteUser, e -> {
																	if(e.succeeded()) {
																		siteRequest.setSiteUser(siteUser);
																		siteRequest.setUserName(jsonPrincipal.getString("preferred_username"));
																		siteRequest.setUserFirstName(jsonPrincipal.getString("given_name"));
																		siteRequest.setUserLastName(jsonPrincipal.getString("family_name"));
																		siteRequest.setUserId(jsonPrincipal.getString("sub"));
																		siteRequest.setUserKey(siteUser.get());
																		eventHandler.handle(Future.succeededFuture());
																	} else {
																		errorSiteState(siteRequest, eventHandler, e);
																	}
																});
															} else {
																errorSiteState(siteRequest, eventHandler, d);
															}
														});
													} else {
														errorSiteState(siteRequest, eventHandler, c);
													}
												});
											} else {
												Long User = userValues.getLong(0);
												SearchList<SiteUser> searchList = new SearchList<SiteUser>();
												searchList.setQuery("*:*");
												searchList.setStore(true);
												searchList.setC(SiteUser.class);
												searchList.addFilterQuery("userId_indexed_string:" + ClientUtils.escapeQueryChars(userId));
												searchList.addFilterQuery("pk_indexed_long:" + User);
												searchList.initDeepSearchList(siteRequest);
												SiteUser siteUser1 = searchList.getList().stream().findFirst().orElse(null);

												JsonObject userVertx = siteRequest.getOperationRequest().getUser();
												JsonObject jsonPrincipal = KeycloakHelper.parseToken(userVertx.getString("access_token"));

												JsonObject jsonObject = new JsonObject();
												jsonObject.put("setUserName", jsonPrincipal.getString("preferred_username"));
												jsonObject.put("setUserFirstName", jsonPrincipal.getString("given_name"));
												jsonObject.put("setUserLastName", jsonPrincipal.getString("family_name"));
												jsonObject.put("setUserCompleteName", jsonPrincipal.getString("name"));
												jsonObject.put("setUserId", jsonPrincipal.getString("sub"));
												jsonObject.put("setUserEmail", jsonPrincipal.getString("email"));
												Boolean define = userSiteStateDefine(siteRequest, jsonObject, true);
												if(define) {
													SiteUser siteUser;
													if(siteUser1 == null) {
														siteUser = new SiteUser();
														siteUser.set(User);
														siteUser.setSiteRequest_(siteRequest);
													} else {
														siteUser = siteUser1;
													}

													SiteRequestEnUS siteRequest2 = new SiteRequestEnUS();
													siteRequest2.setTx(siteRequest.getTx());
													siteRequest2.setSqlConnection(siteRequest.getSqlConnection());
													siteRequest2.setJsonObject(jsonObject);
													siteRequest2.setVertx(siteRequest.getVertx());
													siteRequest2.setSiteContext_(siteContext);
													siteRequest2.setSiteConfig_(siteContext.getSiteConfig());
													siteRequest2.setUserId(siteRequest.getUserId());
													siteRequest2.setUserKey(siteRequest.getUserKey());
													siteRequest2.initDeepSiteRequestEnUS(siteRequest);
													siteUser.setSiteRequest_(siteRequest2);

													ApiRequest apiRequest = new ApiRequest();
													apiRequest.setRows(1);
													apiRequest.setNumFound(1L);
													apiRequest.setNumPATCH(0L);
													apiRequest.initDeepApiRequest(siteRequest2);
													siteRequest2.setApiRequest_(apiRequest);

													userService.sqlPATCHSiteUser(siteUser, false, d -> {
														if(d.succeeded()) {
															SiteUser siteUser2 = d.result();
															userService.defineIndexSiteUser(siteUser2, e -> {
																if(e.succeeded()) {
																	siteRequest.setSiteUser(siteUser2);
																	siteRequest.setUserName(siteUser2.getUserName());
																	siteRequest.setUserFirstName(siteUser2.getUserFirstName());
																	siteRequest.setUserLastName(siteUser2.getUserLastName());
																	siteRequest.setUserId(siteUser2.getUserId());
																	siteRequest.setUserKey(siteUser2.get());
																	eventHandler.handle(Future.succeededFuture());
																} else {
																	errorSiteState(siteRequest, eventHandler, e);
																}
															});
														} else {
															errorSiteState(siteRequest, eventHandler, d);
														}
													});
												} else {
													siteRequest.setSiteUser(siteUser1);
													siteRequest.setUserName(siteUser1.getUserName());
													siteRequest.setUserFirstName(siteUser1.getUserFirstName());
													siteRequest.setUserLastName(siteUser1.getUserLastName());
													siteRequest.setUserId(siteUser1.getUserId());
													siteRequest.setUserKey(siteUser1.get());
													sqlRollbackSiteState(siteRequest, c -> {
														if(c.succeeded()) {
															eventHandler.handle(Future.succeededFuture());
														} else {
															eventHandler.handle(Future.failedFuture(c.cause()));
															errorSiteState(siteRequest, eventHandler, c);
														}
													});
												}
											}
										} catch(Exception e) {
											LOGGER.error(String.format("userSiteState failed. ", e));
											eventHandler.handle(Future.failedFuture(e));
										}
									} else {
										LOGGER.error(String.format("userSiteState failed. ", selectCAsync.cause()));
										eventHandler.handle(Future.failedFuture(selectCAsync.cause()));
									}
								});
							} else {
								LOGGER.error(String.format("userSiteState failed. ", b.cause()));
								eventHandler.handle(Future.failedFuture(b.cause()));
							}
						});
					} else {
						LOGGER.error(String.format("userSiteState failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("userSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public Boolean userSiteStateDefine(SiteRequestEnUS siteRequest, JsonObject jsonObject, Boolean patch) {
		if(patch) {
			return false;
		} else {
			return false;
		}
	}

	public void aSearchSiteStateQ(String uri, String apiMethod, SearchList<SiteState> searchList, String entityVar, String valueIndexed, String varIndexed) {
		searchList.setQuery(varIndexed + ":" + ("*".equals(valueIndexed) ? valueIndexed : ClientUtils.escapeQueryChars(valueIndexed)));
		if(!"*".equals(entityVar)) {
		}
	}

	public void aSearchSiteStateFq(String uri, String apiMethod, SearchList<SiteState> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		if(StringUtils.startsWith(valueIndexed, "[")) {
			String[] fqs = StringUtils.substringBefore(StringUtils.substringAfter(valueIndexed, "["), "]").split(" TO ");
			if(fqs.length != 2)
				throw new RuntimeException(String.format("\"%s\" invalid range query. ", valueIndexed));
			String fq1 = fqs[0].equals("*") ? fqs[0] : SiteState.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[0]);
			String fq2 = fqs[1].equals("*") ? fqs[1] : SiteState.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[1]);
			searchList.addFilterQuery(varIndexed + ":[" + fq1 + " TO " + fq2 + "]");
		} else {
			searchList.addFilterQuery(varIndexed + ":" + SiteState.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), valueIndexed));
		}
	}

	public void aSearchSiteStateSort(String uri, String apiMethod, SearchList<SiteState> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addSort(varIndexed, ORDER.valueOf(valueIndexed));
	}

	public void aSearchSiteStateRows(String uri, String apiMethod, SearchList<SiteState> searchList, Integer valueRows) {
			searchList.setRows(apiMethod != null && apiMethod.contains("Search") ? valueRows : 10);
	}

	public void aSearchSiteStateStart(String uri, String apiMethod, SearchList<SiteState> searchList, Integer valueStart) {
		searchList.setStart(valueStart);
	}

	public void aSearchSiteStateVar(String uri, String apiMethod, SearchList<SiteState> searchList, String var, String value) {
		searchList.getSiteRequest_().getRequestVars().put(var, value);
	}

	public void aSearchSiteStateUri(String uri, String apiMethod, SearchList<SiteState> searchList) {
	}

	public void varsSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<SearchList<OperationResponse>>> eventHandler) {
		try {
			OperationRequest operationRequest = siteRequest.getOperationRequest();

			operationRequest.getParams().getJsonObject("query").forEach(paramRequest -> {
				String entityVar = null;
				String valueIndexed = null;
				String paramName = paramRequest.getKey();
				Object paramValuesObject = paramRequest.getValue();
				JsonArray paramObjects = paramValuesObject instanceof JsonArray ? (JsonArray)paramValuesObject : new JsonArray().add(paramValuesObject);

				try {
					for(Object paramObject : paramObjects) {
						switch(paramName) {
							case "var":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								siteRequest.getRequestVars().put(entityVar, valueIndexed);
								break;
						}
					}
				} catch(Exception e) {
					LOGGER.error(String.format("aSearchSiteState failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOGGER.error(String.format("aSearchSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void aSearchSiteState(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, String uri, String apiMethod, Handler<AsyncResult<SearchList<SiteState>>> eventHandler) {
		try {
			OperationRequest operationRequest = siteRequest.getOperationRequest();
			String entityListStr = siteRequest.getOperationRequest().getParams().getJsonObject("query").getString("fl");
			String[] entityList = entityListStr == null ? null : entityListStr.split(",\\s*");
			SearchList<SiteState> searchList = new SearchList<SiteState>();
			searchList.setPopulate(populate);
			searchList.setStore(store);
			searchList.setQuery("*:*");
			searchList.setC(SiteState.class);
			searchList.setSiteRequest_(siteRequest);
			if(entityList != null)
				searchList.addFields(entityList);

			String id = operationRequest.getParams().getJsonObject("path").getString("id");
			if( != null) {
				searchList.addFilterQuery("(:" + ClientUtils.escapeQueryChars(id) + " OR _indexed_string:" + ClientUtils.escapeQueryChars(id) + ")");
			}

			operationRequest.getParams().getJsonObject("query").forEach(paramRequest -> {
				String entityVar = null;
				String valueIndexed = null;
				String varIndexed = null;
				String valueSort = null;
				Integer valueStart = null;
				Integer valueRows = null;
				String paramName = paramRequest.getKey();
				Object paramValuesObject = paramRequest.getValue();
				JsonArray paramObjects = paramValuesObject instanceof JsonArray ? (JsonArray)paramValuesObject : new JsonArray().add(paramValuesObject);

				try {
					for(Object paramObject : paramObjects) {
						switch(paramName) {
							case "q":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								varIndexed = "*".equals(entityVar) ? entityVar : SiteState.varSearchSiteState(entityVar);
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								valueIndexed = StringUtils.isEmpty(valueIndexed) ? "*" : valueIndexed;
								aSearchSiteStateQ(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "fq":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								varIndexed = SiteState.varIndexedSiteState(entityVar);
								aSearchSiteStateFq(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "sort":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, " "));
								valueIndexed = StringUtils.trim(StringUtils.substringAfter((String)paramObject, " "));
								varIndexed = SiteState.varIndexedSiteState(entityVar);
								aSearchSiteStateSort(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "start":
								valueStart = (Integer)paramObject;
								aSearchSiteStateStart(uri, apiMethod, searchList, valueStart);
								break;
							case "rows":
								valueRows = (Integer)paramObject;
								aSearchSiteStateRows(uri, apiMethod, searchList, valueRows);
								break;
							case "var":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								aSearchSiteStateVar(uri, apiMethod, searchList, entityVar, valueIndexed);
								break;
						}
					}
					aSearchSiteStateUri(uri, apiMethod, searchList);
				} catch(Exception e) {
					LOGGER.error(String.format("aSearchSiteState failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			searchList.initDeepForClass(siteRequest);
			eventHandler.handle(Future.succeededFuture(searchList));
		} catch(Exception e) {
			LOGGER.error(String.format("aSearchSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void defineSiteState(SiteState o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			Transaction tx = siteRequest.getTx();
			Long  = o.get();
			tx.preparedQuery(
					SiteContextEnUS.SQL_define
					, Tuple.of()
					, Collectors.toList()
					, defineAsync
			-> {
				if(defineAsync.succeeded()) {
					try {
						for(Row definition : defineAsync.result().value()) {
							try {
								o.defineForClass(definition.getString(0), definition.getString(1));
							} catch(Exception e) {
								LOGGER.error(String.format("defineSiteState failed. ", e));
								LOGGER.error(e);
							}
						}
						eventHandler.handle(Future.succeededFuture());
					} catch(Exception e) {
						LOGGER.error(String.format("defineSiteState failed. ", e));
						eventHandler.handle(Future.failedFuture(e));
					}
				} else {
					LOGGER.error(String.format("defineSiteState failed. ", defineAsync.cause()));
					eventHandler.handle(Future.failedFuture(defineAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("defineSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void attributeSiteState(SiteState o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			Transaction tx = siteRequest.getTx();
			Long  = o.get();
			tx.preparedQuery(
					SiteContextEnUS.SQL_attribute
					, Tuple.of(, )
					, Collectors.toList()
					, attributeAsync
			-> {
				try {
					if(attributeAsync.succeeded()) {
						if(attributeAsync.result() != null) {
							for(Row definition : attributeAsync.result().value()) {
								if(pk.equals(definition.getLong(0)))
									o.attributeForClass(definition.getString(2), definition.getLong(1));
								else
									o.attributeForClass(definition.getString(3), definition.getLong(0));
							}
						}
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOGGER.error(String.format("attributeSiteState failed. ", attributeAsync.cause()));
						eventHandler.handle(Future.failedFuture(attributeAsync.cause()));
					}
				} catch(Exception e) {
					LOGGER.error(String.format("attributeSiteState failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("attributeSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void indexSiteState(SiteState o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			o.initDeepForClass(siteRequest);
			o.indexForClass();
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOGGER.error(String.format("indexSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void refreshSiteState(SiteState o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Boolean refresh = !"false".equals(siteRequest.getRequestVars().get("refresh"));
			if(refresh && BooleanUtils.isFalse(Optional.ofNullable(siteRequest.getApiRequest_()).map(ApiRequest::getEmpty).orElse(true))) {
				SearchList<SiteState> searchList = new SearchList<SiteState>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(SiteState.class);
				searchList.addFilterQuery("modified_indexed_date:[" + DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(siteRequest.getApiRequest_().getCreated().toInstant(), ZoneId.of("UTC"))) + " TO *]");
				searchList.setRows(1000);
				searchList.initDeepSearchList(siteRequest);
				List<Future> futures = new ArrayList<>();

				for(int i=0; i < pks.size(); i++) {
					Long 2 = pks.get(i);
					String classSimpleName2 = classes.get(i);
				}

				CompositeFuture.all(futures).setHandler(a -> {
					if(a.succeeded()) {
						SiteStateEnUSApiServiceImpl service = new SiteStateEnUSApiServiceImpl(siteRequest.getSiteContext_());
						List<Future> futures2 = new ArrayList<>();
						for(SiteState o2 : searchList.getList()) {
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSiteState(siteContext, siteRequest.getOperationRequest(), new JsonObject());
							o2.setSiteRequest_(siteRequest2);
							futures2.add(
								service.patchSiteStateFuture(o2, false, b -> {
									if(b.succeeded()) {
									} else {
										LOGGER.info(String.format("SiteState %s failed. ", o2.get()));
										eventHandler.handle(Future.failedFuture(b.cause()));
									}
								})
							);
						}

						CompositeFuture.all(futures2).setHandler(b -> {
							if(b.succeeded()) {
								eventHandler.handle(Future.succeededFuture());
							} else {
								LOGGER.error("Refresh relations failed. ", b.cause());
								errorSiteState(siteRequest, eventHandler, b);
							}
						});
					} else {
						LOGGER.error("Refresh relations failed. ", a.cause());
						errorSiteState(siteRequest, eventHandler, a);
					}
				});
			} else {
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOGGER.error(String.format("refreshSiteState failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}
}
