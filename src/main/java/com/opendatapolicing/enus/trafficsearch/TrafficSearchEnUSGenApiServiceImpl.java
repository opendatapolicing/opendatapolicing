package com.opendatapolicing.enus.trafficsearch;

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
public class TrafficSearchEnUSGenApiServiceImpl implements TrafficSearchEnUSGenApiService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(TrafficSearchEnUSGenApiServiceImpl.class);

	protected static final String SERVICE_ADDRESS = "TrafficSearchEnUSApiServiceImpl";

	protected SiteContextEnUS siteContext;

	public TrafficSearchEnUSGenApiServiceImpl(SiteContextEnUS siteContext) {
		this.siteContext = siteContext;
	}

	// PUTImport.enUS //

	@Override
	public void putimport.enusTrafficSearch(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficSearch(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/traffic-search");
		siteRequest.setRequestMethod("PUTImport.enUS");
		try {
			LOGGER.info(String.format("putimport.enusTrafficSearch started. "));
			{
				userTrafficSearch(siteRequest, b -> {
					if(b.succeeded()) {
						putimport.enusTrafficSearchResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								LOGGER.info(String.format("putimport.enusTrafficSearch succeeded. "));
							} else {
								LOGGER.error(String.format("putimport.enusTrafficSearch failed. ", c.cause()));
								errorTrafficSearch(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("putimport.enusTrafficSearch failed. ", b.cause()));
						errorTrafficSearch(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("putimport.enusTrafficSearch failed. ", ex));
			errorTrafficSearch(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void putimport.enusTrafficSearchResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTImport.enUSTrafficSearch(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putimport.enusTrafficSearchResponse failed. ", a.cause()));
					errorTrafficSearch(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putimport.enusTrafficSearchResponse failed. ", ex));
			errorTrafficSearch(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTImport.enUSTrafficSearch(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTImport.enUSTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTMerge.enUS //

	@Override
	public void putmerge.enusTrafficSearch(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficSearch(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/traffic-search");
		siteRequest.setRequestMethod("PUTMerge.enUS");
		try {
			LOGGER.info(String.format("putmerge.enusTrafficSearch started. "));
			{
				userTrafficSearch(siteRequest, b -> {
					if(b.succeeded()) {
						putmerge.enusTrafficSearchResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								LOGGER.info(String.format("putmerge.enusTrafficSearch succeeded. "));
							} else {
								LOGGER.error(String.format("putmerge.enusTrafficSearch failed. ", c.cause()));
								errorTrafficSearch(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("putmerge.enusTrafficSearch failed. ", b.cause()));
						errorTrafficSearch(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("putmerge.enusTrafficSearch failed. ", ex));
			errorTrafficSearch(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void putmerge.enusTrafficSearchResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTMerge.enUSTrafficSearch(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putmerge.enusTrafficSearchResponse failed. ", a.cause()));
					errorTrafficSearch(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putmerge.enusTrafficSearchResponse failed. ", ex));
			errorTrafficSearch(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTMerge.enUSTrafficSearch(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTMerge.enUSTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTCopy.enUS //

	@Override
	public void putcopy.enusTrafficSearch(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficSearch(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/traffic-search");
		siteRequest.setRequestMethod("PUTCopy.enUS");
		try {
			LOGGER.info(String.format("putcopy.enusTrafficSearch started. "));
			{
				userTrafficSearch(siteRequest, b -> {
					if(b.succeeded()) {
						putcopy.enusTrafficSearchResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								LOGGER.info(String.format("putcopy.enusTrafficSearch succeeded. "));
							} else {
								LOGGER.error(String.format("putcopy.enusTrafficSearch failed. ", c.cause()));
								errorTrafficSearch(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("putcopy.enusTrafficSearch failed. ", b.cause()));
						errorTrafficSearch(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopy.enusTrafficSearch failed. ", ex));
			errorTrafficSearch(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public Future<TrafficSearch> putcopy.enusTrafficSearchFuture(SiteRequestEnUS siteRequest, JsonObject jsonObject, Handler<AsyncResult<TrafficSearch>> eventHandler) {
		Promise<TrafficSearch> promise = Promise.promise();
		try {
		} catch(Exception e) {
			LOGGER.error(String.format("putcopy.enusTrafficSearchFuture failed. ", e));
			errorTrafficSearch(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void putcopy.enusTrafficSearchResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTCopy.enUSTrafficSearch(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putcopy.enusTrafficSearchResponse failed. ", a.cause()));
					errorTrafficSearch(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopy.enusTrafficSearchResponse failed. ", ex));
			errorTrafficSearch(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTCopy.enUSTrafficSearch(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTCopy.enUSTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// POST //

	@Override
	public void postTrafficSearch(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficSearch(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/traffic-search");
		siteRequest.setRequestMethod("POST");
		try {
			LOGGER.info(String.format("postTrafficSearch started. "));
			{
				userTrafficSearch(siteRequest, b -> {
					if(b.succeeded()) {
						ApiRequest apiRequest = new ApiRequest();
						apiRequest.setRows(1);
						apiRequest.setNumFound(1L);
						apiRequest.setNumPATCH(0L);
						apiRequest.initDeepApiRequest(siteRequest);
						siteRequest.setApiRequest_(apiRequest);
						siteRequest.getVertx().eventBus().publish("websocketTrafficSearch", JsonObject.mapFrom(apiRequest).toString());
						postTrafficSearchFuture(siteRequest, false, c -> {
							if(c.succeeded()) {
								TrafficSearch trafficSearch = c.result();
								apiRequest.setPk(trafficSearch.getPk());
								postTrafficSearchResponse(trafficSearch, d -> {
										if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("postTrafficSearch succeeded. "));
									} else {
										LOGGER.error(String.format("postTrafficSearch failed. ", d.cause()));
										errorTrafficSearch(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("postTrafficSearch failed. ", c.cause()));
								errorTrafficSearch(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("postTrafficSearch failed. ", b.cause()));
						errorTrafficSearch(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("postTrafficSearch failed. ", ex));
			errorTrafficSearch(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public Future<TrafficSearch> postTrafficSearchFuture(SiteRequestEnUS siteRequest, Boolean inheritPk, Handler<AsyncResult<TrafficSearch>> eventHandler) {
		Promise<TrafficSearch> promise = Promise.promise();
		try {
			sqlConnectionTrafficSearch(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionTrafficSearch(siteRequest, b -> {
						if(b.succeeded()) {
							createTrafficSearch(siteRequest, c -> {
								if(c.succeeded()) {
									TrafficSearch trafficSearch = c.result();
									sqlPOSTTrafficSearch(trafficSearch, inheritPk, d -> {
										if(d.succeeded()) {
											defineIndexTrafficSearch(trafficSearch, e -> {
												if(e.succeeded()) {
													ApiRequest apiRequest = siteRequest.getApiRequest_();
													if(apiRequest != null) {
														apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
														trafficSearch.apiRequestTrafficSearch();
														siteRequest.getVertx().eventBus().publish("websocketTrafficSearch", JsonObject.mapFrom(apiRequest).toString());
													}
													eventHandler.handle(Future.succeededFuture(trafficSearch));
													promise.complete(trafficSearch);
												} else {
													LOGGER.error(String.format("postTrafficSearchFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOGGER.error(String.format("postTrafficSearchFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("postTrafficSearchFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("postTrafficSearchFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("postTrafficSearchFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("postTrafficSearchFuture failed. ", e));
			errorTrafficSearch(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPOSTTrafficSearch(TrafficSearch o, Boolean inheritPk, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
					LOGGER.error(String.format("sqlPOSTTrafficSearch failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPOSTTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void postTrafficSearchResponse(TrafficSearch trafficSearch, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = trafficSearch.getSiteRequest_();
		try {
			response200POSTTrafficSearch(trafficSearch, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("postTrafficSearchResponse failed. ", a.cause()));
					errorTrafficSearch(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("postTrafficSearchResponse failed. ", ex));
			errorTrafficSearch(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200POSTTrafficSearch(TrafficSearch o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200POSTTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchTrafficSearch(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficSearch(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/traffic-search");
		siteRequest.setRequestMethod("PATCH");
		try {
			LOGGER.info(String.format("patchTrafficSearch started. "));
			{
				userTrafficSearch(siteRequest, b -> {
					if(b.succeeded()) {
						patchTrafficSearchResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											aSearchTrafficSearch(siteRequest, false, true, "/api/traffic-search", "PATCH", d -> {
												if(d.succeeded()) {
													SearchList<TrafficSearch> listTrafficSearch = d.result();

													if(listTrafficSearch.getQueryResponse().getResults().getNumFound() > 1) {
														List<String> roles2 = Arrays.asList("SiteAdmin");
														if(
																!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles2)
																&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles2)
																) {
															String message = String.format("roles required: " + String.join(", ", roles2));
															LOGGER.error(message);
															errorTrafficSearch(siteRequest, eventHandler, Future.failedFuture(message));
														}
													} else {

														ApiRequest apiRequest = new ApiRequest();
														apiRequest.setRows(listTrafficSearch.getRows());
														apiRequest.setNumFound(listTrafficSearch.getQueryResponse().getResults().getNumFound());
														apiRequest.setNumPATCH(0L);
														apiRequest.initDeepApiRequest(siteRequest);
														siteRequest.setApiRequest_(apiRequest);
														siteRequest.getVertx().eventBus().publish("websocketTrafficSearch", JsonObject.mapFrom(apiRequest).toString());
														String dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));

														try {
															listPATCHTrafficSearch(apiRequest, listTrafficSearch, dt, e -> {
																if(e.succeeded()) {
																	patchTrafficSearchResponse(siteRequest, f -> {
																		if(f.succeeded()) {
																			LOGGER.info(String.format("patchTrafficSearch succeeded. "));
																			blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																		} else {
																			LOGGER.error(String.format("patchTrafficSearch failed. ", f.cause()));
																			errorTrafficSearch(siteRequest, null, f);
																		}
																	});
																} else {
																	LOGGER.error(String.format("patchTrafficSearch failed. ", e.cause()));
																	errorTrafficSearch(siteRequest, null, e);
																}
															});
														} catch(Exception ex) {
															LOGGER.error(String.format("patchTrafficSearch failed. ", ex));
															errorTrafficSearch(siteRequest, null, Future.failedFuture(ex));
														}
													}
										} else {
													LOGGER.error(String.format("patchTrafficSearch failed. ", d.cause()));
													errorTrafficSearch(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOGGER.error(String.format("patchTrafficSearch failed. ", ex));
											errorTrafficSearch(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOGGER.error(String.format("patchTrafficSearch failed. ", c.cause()));
								errorTrafficSearch(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("patchTrafficSearch failed. ", b.cause()));
						errorTrafficSearch(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("patchTrafficSearch failed. ", ex));
			errorTrafficSearch(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPATCHTrafficSearch(ApiRequest apiRequest, SearchList<TrafficSearch> listTrafficSearch, String dt, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listTrafficSearch.getSiteRequest_();
		listTrafficSearch.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForTrafficSearch(siteContext, siteRequest.getOperationRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				patchTrafficSearchFuture(o, false, a -> {
					if(a.succeeded()) {
					} else {
						errorTrafficSearch(siteRequest2, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				if(listTrafficSearch.next(dt)) {
					listPATCHTrafficSearch(apiRequest, listTrafficSearch, dt, eventHandler);
				} else {
					response200PATCHTrafficSearch(siteRequest, eventHandler);
				}
			} else {
				LOGGER.error(String.format("listPATCHTrafficSearch failed. ", a.cause()));
				errorTrafficSearch(listTrafficSearch.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<TrafficSearch> patchTrafficSearchFuture(TrafficSearch o, Boolean inheritPk, Handler<AsyncResult<TrafficSearch>> eventHandler) {
		Promise<TrafficSearch> promise = Promise.promise();
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			if(apiRequest != null && apiRequest.getNumFound() == 1L) {
				apiRequest.setOriginal(o);
				apiRequest.setPk(o.getPk());
			}
			sqlConnectionTrafficSearch(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionTrafficSearch(siteRequest, b -> {
						if(b.succeeded()) {
							sqlPATCHTrafficSearch(o, inheritPk, c -> {
								if(c.succeeded()) {
									TrafficSearch trafficSearch = c.result();
									defineIndexTrafficSearch(trafficSearch, d -> {
										if(d.succeeded()) {
											if(apiRequest != null) {
												apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
												if(apiRequest.getNumFound() == 1L) {
													trafficSearch.apiRequestTrafficSearch();
												}
												siteRequest.getVertx().eventBus().publish("websocketTrafficSearch", JsonObject.mapFrom(apiRequest).toString());
											}
											eventHandler.handle(Future.succeededFuture(trafficSearch));
											promise.complete(trafficSearch);
										} else {
											LOGGER.error(String.format("patchTrafficSearchFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("patchTrafficSearchFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("patchTrafficSearchFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("patchTrafficSearchFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("patchTrafficSearchFuture failed. ", e));
			errorTrafficSearch(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPATCHTrafficSearch(TrafficSearch o, Boolean inheritPk, Handler<AsyncResult<TrafficSearch>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Transaction tx = siteRequest.getTx();
			Long  = o.get();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			TrafficSearch o2 = new TrafficSearch();
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
					TrafficSearch o3 = new TrafficSearch();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.set();
					eventHandler.handle(Future.succeededFuture(o3));
				} else {
					LOGGER.error(String.format("sqlPATCHTrafficSearch failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPATCHTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void patchTrafficSearchResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PATCHTrafficSearch(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("patchTrafficSearchResponse failed. ", a.cause()));
					errorTrafficSearch(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("patchTrafficSearchResponse failed. ", ex));
			errorTrafficSearch(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PATCHTrafficSearch(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PATCHTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// GET.enUS //

	@Override
	public void get.enusTrafficSearch(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficSearch(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/traffic-search/{id}");
		siteRequest.setRequestMethod("GET.enUS");
		try {
			{
				userTrafficSearch(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchTrafficSearch(siteRequest, false, true, "/api/traffic-search/{id}", "GET.enUS", c -> {
							if(c.succeeded()) {
								SearchList<TrafficSearch> listTrafficSearch = c.result();
								get.enusTrafficSearchResponse(listTrafficSearch, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("get.enusTrafficSearch succeeded. "));
									} else {
										LOGGER.error(String.format("get.enusTrafficSearch failed. ", d.cause()));
										errorTrafficSearch(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("get.enusTrafficSearch failed. ", c.cause()));
								errorTrafficSearch(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("get.enusTrafficSearch failed. ", b.cause()));
						errorTrafficSearch(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("get.enusTrafficSearch failed. ", ex));
			errorTrafficSearch(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void get.enusTrafficSearchResponse(SearchList<TrafficSearch> listTrafficSearch, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficSearch.getSiteRequest_();
		try {
			response200GET.enUSTrafficSearch(listTrafficSearch, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("get.enusTrafficSearchResponse failed. ", a.cause()));
					errorTrafficSearch(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("get.enusTrafficSearchResponse failed. ", ex));
			errorTrafficSearch(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200GET.enUSTrafficSearch(SearchList<TrafficSearch> listTrafficSearch, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listTrafficSearch.getSiteRequest_();
			SolrDocumentList solrDocuments = listTrafficSearch.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listTrafficSearch.getList().stream().findFirst().orElse(null));
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200GET.enUSTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// Search //

	@Override
	public void searchTrafficSearch(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficSearch(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/traffic-search");
		siteRequest.setRequestMethod("Search");
		try {
			{
				userTrafficSearch(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchTrafficSearch(siteRequest, false, true, "/api/traffic-search", "Search", c -> {
							if(c.succeeded()) {
								SearchList<TrafficSearch> listTrafficSearch = c.result();
								searchTrafficSearchResponse(listTrafficSearch, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("searchTrafficSearch succeeded. "));
									} else {
										LOGGER.error(String.format("searchTrafficSearch failed. ", d.cause()));
										errorTrafficSearch(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("searchTrafficSearch failed. ", c.cause()));
								errorTrafficSearch(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("searchTrafficSearch failed. ", b.cause()));
						errorTrafficSearch(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("searchTrafficSearch failed. ", ex));
			errorTrafficSearch(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void searchTrafficSearchResponse(SearchList<TrafficSearch> listTrafficSearch, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficSearch.getSiteRequest_();
		try {
			response200SearchTrafficSearch(listTrafficSearch, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("searchTrafficSearchResponse failed. ", a.cause()));
					errorTrafficSearch(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchTrafficSearchResponse failed. ", ex));
			errorTrafficSearch(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchTrafficSearch(SearchList<TrafficSearch> listTrafficSearch, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listTrafficSearch.getSiteRequest_();
			QueryResponse responseSearch = listTrafficSearch.getQueryResponse();
			SolrDocumentList solrDocuments = listTrafficSearch.getSolrDocumentList();
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
			listTrafficSearch.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
				List<String> fls = listTrafficSearch.getFields();
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
			LOGGER.error(String.format("response200SearchTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// AdminSearch.enUS //

	@Override
	public void adminsearch.enusTrafficSearch(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficSearch(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/traffic-search");
		siteRequest.setRequestMethod("AdminSearch.enUS");
		try {
			{
				userTrafficSearch(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchTrafficSearch(siteRequest, false, true, "/api/traffic-search", "AdminSearch.enUS", c -> {
							if(c.succeeded()) {
								SearchList<TrafficSearch> listTrafficSearch = c.result();
								adminsearch.enusTrafficSearchResponse(listTrafficSearch, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("adminsearch.enusTrafficSearch succeeded. "));
									} else {
										LOGGER.error(String.format("adminsearch.enusTrafficSearch failed. ", d.cause()));
										errorTrafficSearch(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("adminsearch.enusTrafficSearch failed. ", c.cause()));
								errorTrafficSearch(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("adminsearch.enusTrafficSearch failed. ", b.cause()));
						errorTrafficSearch(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("adminsearch.enusTrafficSearch failed. ", ex));
			errorTrafficSearch(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void adminsearch.enusTrafficSearchResponse(SearchList<TrafficSearch> listTrafficSearch, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficSearch.getSiteRequest_();
		try {
			response200AdminSearch.enUSTrafficSearch(listTrafficSearch, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("adminsearch.enusTrafficSearchResponse failed. ", a.cause()));
					errorTrafficSearch(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("adminsearch.enusTrafficSearchResponse failed. ", ex));
			errorTrafficSearch(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200AdminSearch.enUSTrafficSearch(SearchList<TrafficSearch> listTrafficSearch, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listTrafficSearch.getSiteRequest_();
			QueryResponse responseSearch = listTrafficSearch.getQueryResponse();
			SolrDocumentList solrDocuments = listTrafficSearch.getSolrDocumentList();
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
			listTrafficSearch.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
				List<String> fls = listTrafficSearch.getFields();
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
			LOGGER.error(String.format("response200AdminSearch.enUSTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// SearchPage.enUS //

	@Override
	public void searchpage.enusTrafficSearchId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		searchpage.enusTrafficSearch(operationRequest, eventHandler);
	}

	@Override
	public void searchpage.enusTrafficSearch(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficSearch(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/traffic-search");
		siteRequest.setRequestMethod("SearchPage.enUS");
		try {
			{
				userTrafficSearch(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchTrafficSearch(siteRequest, false, true, "/api/traffic-search", "SearchPage.enUS", c -> {
							if(c.succeeded()) {
								SearchList<TrafficSearch> listTrafficSearch = c.result();
								searchpage.enusTrafficSearchResponse(listTrafficSearch, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("searchpage.enusTrafficSearch succeeded. "));
									} else {
										LOGGER.error(String.format("searchpage.enusTrafficSearch failed. ", d.cause()));
										errorTrafficSearch(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("searchpage.enusTrafficSearch failed. ", c.cause()));
								errorTrafficSearch(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("searchpage.enusTrafficSearch failed. ", b.cause()));
						errorTrafficSearch(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("searchpage.enusTrafficSearch failed. ", ex));
			errorTrafficSearch(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void searchpage.enusTrafficSearchPageInit(TrafficSearchPage page, SearchList<TrafficSearch> listTrafficSearch) {
	}
	public void searchpage.enusTrafficSearchResponse(SearchList<TrafficSearch> listTrafficSearch, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficSearch.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200SearchPage.enUSTrafficSearch(listTrafficSearch, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("searchpage.enusTrafficSearchResponse failed. ", a.cause()));
					errorTrafficSearch(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchpage.enusTrafficSearchResponse failed. ", ex));
			errorTrafficSearch(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchPage.enUSTrafficSearch(SearchList<TrafficSearch> listTrafficSearch, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listTrafficSearch.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listTrafficSearch.getSiteRequest_(), buffer);
			TrafficSearchPage page = new TrafficSearchPage();
			SolrDocument pageSolrDocument = new SolrDocument();
			CaseInsensitiveHeaders requestHeaders = new CaseInsensitiveHeaders();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/api/traffic-search");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listTrafficSearch.size() == 1)
				siteRequest.setRequest(listTrafficSearch.get(0).get());
			siteRequest.setW(w);
			page.setListTrafficSearch(listTrafficSearch);
			page.setSiteRequest_(siteRequest);
			searchpage.enusTrafficSearchPageInit(page, listTrafficSearch);
			page.initDeepTrafficSearchPage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new OperationResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOGGER.error(String.format("response200SearchPage.enUSTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// General //

	public Future<TrafficSearch> defineIndexTrafficSearch(TrafficSearch trafficSearch, Handler<AsyncResult<TrafficSearch>> eventHandler) {
		Promise<TrafficSearch> promise = Promise.promise();
		SiteRequestEnUS siteRequest = trafficSearch.getSiteRequest_();
		defineTrafficSearch(trafficSearch, c -> {
			if(c.succeeded()) {
				attributeTrafficSearch(trafficSearch, d -> {
					if(d.succeeded()) {
						indexTrafficSearch(trafficSearch, e -> {
							if(e.succeeded()) {
								sqlCommitTrafficSearch(siteRequest, f -> {
									if(f.succeeded()) {
										sqlCloseTrafficSearch(siteRequest, g -> {
											if(g.succeeded()) {
												refreshTrafficSearch(trafficSearch, h -> {
													if(h.succeeded()) {
														eventHandler.handle(Future.succeededFuture(trafficSearch));
														promise.complete(trafficSearch);
													} else {
														LOGGER.error(String.format("refreshTrafficSearch failed. ", h.cause()));
														errorTrafficSearch(siteRequest, null, h);
													}
												});
											} else {
												LOGGER.error(String.format("defineIndexTrafficSearch failed. ", g.cause()));
												errorTrafficSearch(siteRequest, null, g);
											}
										});
									} else {
										LOGGER.error(String.format("defineIndexTrafficSearch failed. ", f.cause()));
										errorTrafficSearch(siteRequest, null, f);
									}
								});
							} else {
								LOGGER.error(String.format("defineIndexTrafficSearch failed. ", e.cause()));
								errorTrafficSearch(siteRequest, null, e);
							}
						});
					} else {
						LOGGER.error(String.format("defineIndexTrafficSearch failed. ", d.cause()));
						errorTrafficSearch(siteRequest, null, d);
					}
				});
			} else {
				LOGGER.error(String.format("defineIndexTrafficSearch failed. ", c.cause()));
				errorTrafficSearch(siteRequest, null, c);
			}
		});
		return promise.future();
	}

	public void createTrafficSearch(SiteRequestEnUS siteRequest, Handler<AsyncResult<TrafficSearch>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();
			String userId = siteRequest.getUserId();
			ZonedDateTime created = Optional.ofNullable(siteRequest.getJsonObject()).map(j -> j.getString("created")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())))).orElse(ZonedDateTime.now(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())));

			tx.preparedQuery(
					SiteContextEnUS.SQL_create
					, Tuple.of(TrafficSearch.class.getCanonicalName(), userId, created.toOffsetDateTime())
					, Collectors.toList()
					, createAsync
			-> {
				if(createAsync.succeeded()) {
					Row createLine = createAsync.result().value().stream().findFirst().orElseGet(() -> null);
					Long  = createLine.getLong(0);
					TrafficSearch o = new TrafficSearch();
					o.set();
					o.setSiteRequest_(siteRequest);
					eventHandler.handle(Future.succeededFuture(o));
				} else {
					LOGGER.error(String.format("createTrafficSearch failed. ", createAsync.cause()));
					eventHandler.handle(Future.failedFuture(createAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("createTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void errorTrafficSearch(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler, AsyncResult<?> resultAsync) {
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
		sqlRollbackTrafficSearch(siteRequest, a -> {
			if(a.succeeded()) {
				LOGGER.info(String.format("sql rollback. "));
				sqlCloseTrafficSearch(siteRequest, b -> {
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

	public void sqlConnectionTrafficSearch(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("sqlConnectionTrafficSearch failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlTransactionTrafficSearch(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
			LOGGER.error(String.format("sqlTransactionTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCommitTrafficSearch(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("sqlCommitTrafficSearch failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlRollbackTrafficSearch(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("sqlRollbackTrafficSearch failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCloseTrafficSearch(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
			LOGGER.error(String.format("sqlCloseTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SiteRequestEnUS generateSiteRequestEnUSForTrafficSearch(SiteContextEnUS siteContext, OperationRequest operationRequest) {
		return generateSiteRequestEnUSForTrafficSearch(siteContext, operationRequest, null);
	}

	public SiteRequestEnUS generateSiteRequestEnUSForTrafficSearch(SiteContextEnUS siteContext, OperationRequest operationRequest, JsonObject body) {
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

	public void userTrafficSearch(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			String userId = siteRequest.getUserId();
			if(userId == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				sqlConnectionTrafficSearch(siteRequest, a -> {
					if(a.succeeded()) {
						sqlTransactionTrafficSearch(siteRequest, b -> {
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
												userTrafficSearchDefine(siteRequest, jsonObject, false);

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
																		errorTrafficSearch(siteRequest, eventHandler, e);
																	}
																});
															} else {
																errorTrafficSearch(siteRequest, eventHandler, d);
															}
														});
													} else {
														errorTrafficSearch(siteRequest, eventHandler, c);
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
												Boolean define = userTrafficSearchDefine(siteRequest, jsonObject, true);
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
																	errorTrafficSearch(siteRequest, eventHandler, e);
																}
															});
														} else {
															errorTrafficSearch(siteRequest, eventHandler, d);
														}
													});
												} else {
													siteRequest.setSiteUser(siteUser1);
													siteRequest.setUserName(siteUser1.getUserName());
													siteRequest.setUserFirstName(siteUser1.getUserFirstName());
													siteRequest.setUserLastName(siteUser1.getUserLastName());
													siteRequest.setUserId(siteUser1.getUserId());
													siteRequest.setUserKey(siteUser1.get());
													sqlRollbackTrafficSearch(siteRequest, c -> {
														if(c.succeeded()) {
															eventHandler.handle(Future.succeededFuture());
														} else {
															eventHandler.handle(Future.failedFuture(c.cause()));
															errorTrafficSearch(siteRequest, eventHandler, c);
														}
													});
												}
											}
										} catch(Exception e) {
											LOGGER.error(String.format("userTrafficSearch failed. ", e));
											eventHandler.handle(Future.failedFuture(e));
										}
									} else {
										LOGGER.error(String.format("userTrafficSearch failed. ", selectCAsync.cause()));
										eventHandler.handle(Future.failedFuture(selectCAsync.cause()));
									}
								});
							} else {
								LOGGER.error(String.format("userTrafficSearch failed. ", b.cause()));
								eventHandler.handle(Future.failedFuture(b.cause()));
							}
						});
					} else {
						LOGGER.error(String.format("userTrafficSearch failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("userTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public Boolean userTrafficSearchDefine(SiteRequestEnUS siteRequest, JsonObject jsonObject, Boolean patch) {
		if(patch) {
			return false;
		} else {
			return false;
		}
	}

	public void aSearchTrafficSearchQ(String uri, String apiMethod, SearchList<TrafficSearch> searchList, String entityVar, String valueIndexed, String varIndexed) {
		searchList.setQuery(varIndexed + ":" + ("*".equals(valueIndexed) ? valueIndexed : ClientUtils.escapeQueryChars(valueIndexed)));
		if(!"*".equals(entityVar)) {
		}
	}

	public void aSearchTrafficSearchFq(String uri, String apiMethod, SearchList<TrafficSearch> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		if(StringUtils.startsWith(valueIndexed, "[")) {
			String[] fqs = StringUtils.substringBefore(StringUtils.substringAfter(valueIndexed, "["), "]").split(" TO ");
			if(fqs.length != 2)
				throw new RuntimeException(String.format("\"%s\" invalid range query. ", valueIndexed));
			String fq1 = fqs[0].equals("*") ? fqs[0] : TrafficSearch.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[0]);
			String fq2 = fqs[1].equals("*") ? fqs[1] : TrafficSearch.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[1]);
			searchList.addFilterQuery(varIndexed + ":[" + fq1 + " TO " + fq2 + "]");
		} else {
			searchList.addFilterQuery(varIndexed + ":" + TrafficSearch.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), valueIndexed));
		}
	}

	public void aSearchTrafficSearchSort(String uri, String apiMethod, SearchList<TrafficSearch> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addSort(varIndexed, ORDER.valueOf(valueIndexed));
	}

	public void aSearchTrafficSearchRows(String uri, String apiMethod, SearchList<TrafficSearch> searchList, Integer valueRows) {
			searchList.setRows(apiMethod != null && apiMethod.contains("Search") ? valueRows : 10);
	}

	public void aSearchTrafficSearchStart(String uri, String apiMethod, SearchList<TrafficSearch> searchList, Integer valueStart) {
		searchList.setStart(valueStart);
	}

	public void aSearchTrafficSearchVar(String uri, String apiMethod, SearchList<TrafficSearch> searchList, String var, String value) {
		searchList.getSiteRequest_().getRequestVars().put(var, value);
	}

	public void aSearchTrafficSearchUri(String uri, String apiMethod, SearchList<TrafficSearch> searchList) {
	}

	public void varsTrafficSearch(SiteRequestEnUS siteRequest, Handler<AsyncResult<SearchList<OperationResponse>>> eventHandler) {
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
					LOGGER.error(String.format("aSearchTrafficSearch failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOGGER.error(String.format("aSearchTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void aSearchTrafficSearch(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, String uri, String apiMethod, Handler<AsyncResult<SearchList<TrafficSearch>>> eventHandler) {
		try {
			OperationRequest operationRequest = siteRequest.getOperationRequest();
			String entityListStr = siteRequest.getOperationRequest().getParams().getJsonObject("query").getString("fl");
			String[] entityList = entityListStr == null ? null : entityListStr.split(",\\s*");
			SearchList<TrafficSearch> searchList = new SearchList<TrafficSearch>();
			searchList.setPopulate(populate);
			searchList.setStore(store);
			searchList.setQuery("*:*");
			searchList.setC(TrafficSearch.class);
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
								varIndexed = "*".equals(entityVar) ? entityVar : TrafficSearch.varSearchTrafficSearch(entityVar);
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								valueIndexed = StringUtils.isEmpty(valueIndexed) ? "*" : valueIndexed;
								aSearchTrafficSearchQ(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "fq":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								varIndexed = TrafficSearch.varIndexedTrafficSearch(entityVar);
								aSearchTrafficSearchFq(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "sort":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, " "));
								valueIndexed = StringUtils.trim(StringUtils.substringAfter((String)paramObject, " "));
								varIndexed = TrafficSearch.varIndexedTrafficSearch(entityVar);
								aSearchTrafficSearchSort(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "start":
								valueStart = (Integer)paramObject;
								aSearchTrafficSearchStart(uri, apiMethod, searchList, valueStart);
								break;
							case "rows":
								valueRows = (Integer)paramObject;
								aSearchTrafficSearchRows(uri, apiMethod, searchList, valueRows);
								break;
							case "var":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								aSearchTrafficSearchVar(uri, apiMethod, searchList, entityVar, valueIndexed);
								break;
						}
					}
					aSearchTrafficSearchUri(uri, apiMethod, searchList);
				} catch(Exception e) {
					LOGGER.error(String.format("aSearchTrafficSearch failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			searchList.initDeepForClass(siteRequest);
			eventHandler.handle(Future.succeededFuture(searchList));
		} catch(Exception e) {
			LOGGER.error(String.format("aSearchTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void defineTrafficSearch(TrafficSearch o, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
								LOGGER.error(String.format("defineTrafficSearch failed. ", e));
								LOGGER.error(e);
							}
						}
						eventHandler.handle(Future.succeededFuture());
					} catch(Exception e) {
						LOGGER.error(String.format("defineTrafficSearch failed. ", e));
						eventHandler.handle(Future.failedFuture(e));
					}
				} else {
					LOGGER.error(String.format("defineTrafficSearch failed. ", defineAsync.cause()));
					eventHandler.handle(Future.failedFuture(defineAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("defineTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void attributeTrafficSearch(TrafficSearch o, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("attributeTrafficSearch failed. ", attributeAsync.cause()));
						eventHandler.handle(Future.failedFuture(attributeAsync.cause()));
					}
				} catch(Exception e) {
					LOGGER.error(String.format("attributeTrafficSearch failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("attributeTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void indexTrafficSearch(TrafficSearch o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			o.initDeepForClass(siteRequest);
			o.indexForClass();
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOGGER.error(String.format("indexTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void refreshTrafficSearch(TrafficSearch o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Boolean refresh = !"false".equals(siteRequest.getRequestVars().get("refresh"));
			if(refresh && BooleanUtils.isFalse(Optional.ofNullable(siteRequest.getApiRequest_()).map(ApiRequest::getEmpty).orElse(true))) {
				SearchList<TrafficSearch> searchList = new SearchList<TrafficSearch>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(TrafficSearch.class);
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
						TrafficSearchEnUSApiServiceImpl service = new TrafficSearchEnUSApiServiceImpl(siteRequest.getSiteContext_());
						List<Future> futures2 = new ArrayList<>();
						for(TrafficSearch o2 : searchList.getList()) {
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForTrafficSearch(siteContext, siteRequest.getOperationRequest(), new JsonObject());
							o2.setSiteRequest_(siteRequest2);
							futures2.add(
								service.patchTrafficSearchFuture(o2, false, b -> {
									if(b.succeeded()) {
									} else {
										LOGGER.info(String.format("TrafficSearch %s failed. ", o2.get()));
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
								errorTrafficSearch(siteRequest, eventHandler, b);
							}
						});
					} else {
						LOGGER.error("Refresh relations failed. ", a.cause());
						errorTrafficSearch(siteRequest, eventHandler, a);
					}
				});
			} else {
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOGGER.error(String.format("refreshTrafficSearch failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}
}
