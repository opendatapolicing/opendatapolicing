package com.opendatapolicing.enus.trafficsearch;

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
			SiteRequestEnUS siteReq