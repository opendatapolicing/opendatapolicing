package com.opendatapolicing.enus.trafficperson;

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
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			List<Future> futures = new ArrayList<>();

			if(siteRequest.getSessionId() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContextEnUS.SQL_setD
				, Tuple.of(pk, "sessionId", siteRequest.getSessionId())
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
				, Tuple.of(pk, "userId", siteRequest.getUserId())
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
				, Tuple.of(pk, "userKey", siteRequest.getUserKey().toString())
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
					case "inheritPk":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "inheritPk", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value TrafficPerson.inheritPk failed", b.cause())));
							});
						}));
						break;
					case "archived":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "archived", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value TrafficPerson.archived failed", b.cause())));
							});
						}));
						break;
					case "deleted":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD
									, Tuple.of(pk, "deleted", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value TrafficPerson.deleted failed", b.cause())));
							});
						}));
						break;
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
														SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listTrafficPerson.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(null);
														Date date = null;
														if(facets != null)
														date = (Date)facets.get("max_modified");
														String dt;
														if(date == null)
															dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));
														else
															dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")));
														listTrafficPerson.addFilterQuery(String.format("modified_indexed_date:[* TO %s]", dt));

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
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			TrafficPerson o2 = new TrafficPerson();
			List<Future> futures = new ArrayList<>();

			if(o.getUserId() == null && siteRequest.getUserId() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContextEnUS.SQL_setD
							, Tuple.of(pk, "userId", siteRequest.getUserId())
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
				, Tuple.of(pk, "userKey", siteRequest.getUserKey().toString())
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
					case "setInheritPk":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "inheritPk")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value TrafficPerson.inheritPk failed", b.cause())));
								});
							}));
						} else {
							o2.setInheritPk(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "inheritPk", o2.jsonInheritPk())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value TrafficPerson.inheritPk failed", b.cause())));
								});
							}));
						}
						break;
					case "setArchived":
						if(jsonObject.getBoolean(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "archived")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value TrafficPerson.archived failed", b.cause())));
								});
							}));
						} else {
							o2.setArchived(jsonObject.getBoolean(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "archived", o2.jsonArchived())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value TrafficPerson.archived failed", b.cause())));
								});
							}));
						}
						break;
					case "setDeleted":
						if(jsonObject.getBoolean(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD
										, Tuple.of(pk, "deleted")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value TrafficPerson.deleted failed", b.cause())));
								});
							}));
						} else {
							o2.setDeleted(jsonObject.getBoolean(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD
										, Tuple.of(pk, "deleted", o2.jsonDeleted())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value TrafficPerson.deleted failed", b.cause())));
								});
							}));
						}
						break;
				}
			}
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					TrafficPerson o3 = new TrafficPerson();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
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
			SolrDocumentList solrDocuments = listTrafficPerson.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listTrafficPerson.getList().stream().findFirst().orElse(null));
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200GET.enUSTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// Search //

	@Override
	public void searchTrafficPerson(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficPerson(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/person");
		siteRequest.setRequestMethod("Search");
		try {
			{
				userTrafficPerson(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchTrafficPerson(siteRequest, false, true, "/api/person", "Search", c -> {
							if(c.succeeded()) {
								SearchList<TrafficPerson> listTrafficPerson = c.result();
								searchTrafficPersonResponse(listTrafficPerson, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("searchTrafficPerson succeeded. "));
									} else {
										LOGGER.error(String.format("searchTrafficPerson failed. ", d.cause()));
										errorTrafficPerson(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("searchTrafficPerson failed. ", c.cause()));
								errorTrafficPerson(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("searchTrafficPerson failed. ", b.cause()));
						errorTrafficPerson(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("searchTrafficPerson failed. ", ex));
			errorTrafficPerson(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void searchTrafficPersonResponse(SearchList<TrafficPerson> listTrafficPerson, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficPerson.getSiteRequest_();
		try {
			response200SearchTrafficPerson(listTrafficPerson, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("searchTrafficPersonResponse failed. ", a.cause()));
					errorTrafficPerson(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchTrafficPersonResponse failed. ", ex));
			errorTrafficPerson(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchTrafficPerson(SearchList<TrafficPerson> listTrafficPerson, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listTrafficPerson.getSiteRequest_();
			QueryResponse responseSearch = listTrafficPerson.getQueryResponse();
			SolrDocumentList solrDocuments = listTrafficPerson.getSolrDocumentList();
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
			listTrafficPerson.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
				List<String> fls = listTrafficPerson.getFields();
				if(fls.size() > 0) {
					Set<String> fieldNames = new HashSet<String>();
					fieldNames.addAll(json2.fieldNames());
					if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
						fieldNames.removeAll(Optional.ofNullable(json2.getJsonArray("saves")).orElse(new JsonArray()).stream().map(s -> s.toString()).collect(Collectors.toList()));
						fieldNames.remove("pk");
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
			LOGGER.error(String.format("response200SearchTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// AdminSearch.enUS //

	@Override
	public void adminsearch.enusTrafficPerson(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficPerson(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/person");
		siteRequest.setRequestMethod("AdminSearch.enUS");
		try {
			{
				userTrafficPerson(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchTrafficPerson(siteRequest, false, true, "/api/person", "AdminSearch.enUS", c -> {
							if(c.succeeded()) {
								SearchList<TrafficPerson> listTrafficPerson = c.result();
								adminsearch.enusTrafficPersonResponse(listTrafficPerson, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("adminsearch.enusTrafficPerson succeeded. "));
									} else {
										LOGGER.error(String.format("adminsearch.enusTrafficPerson failed. ", d.cause()));
										errorTrafficPerson(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("adminsearch.enusTrafficPerson failed. ", c.cause()));
								errorTrafficPerson(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("adminsearch.enusTrafficPerson failed. ", b.cause()));
						errorTrafficPerson(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("adminsearch.enusTrafficPerson failed. ", ex));
			errorTrafficPerson(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void adminsearch.enusTrafficPersonResponse(SearchList<TrafficPerson> listTrafficPerson, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficPerson.getSiteRequest_();
		try {
			response200AdminSearch.enUSTrafficPerson(listTrafficPerson, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("adminsearch.enusTrafficPersonResponse failed. ", a.cause()));
					errorTrafficPerson(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("adminsearch.enusTrafficPersonResponse failed. ", ex));
			errorTrafficPerson(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200AdminSearch.enUSTrafficPerson(SearchList<TrafficPerson> listTrafficPerson, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listTrafficPerson.getSiteRequest_();
			QueryResponse responseSearch = listTrafficPerson.getQueryResponse();
			SolrDocumentList solrDocuments = listTrafficPerson.getSolrDocumentList();
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
			listTrafficPerson.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
				List<String> fls = listTrafficPerson.getFields();
				if(fls.size() > 0) {
					Set<String> fieldNames = new HashSet<String>();
					fieldNames.addAll(json2.fieldNames());
					if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
						fieldNames.removeAll(Optional.ofNullable(json2.getJsonArray("saves")).orElse(new JsonArray()).stream().map(s -> s.toString()).collect(Collectors.toList()));
						fieldNames.remove("pk");
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
			LOGGER.error(String.format("response200AdminSearch.enUSTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// SearchPage.enUS //

	@Override
	public void searchpage.enusTrafficPersonId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		searchpage.enusTrafficPerson(operationRequest, eventHandler);
	}

	@Override
	public void searchpage.enusTrafficPerson(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficPerson(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/person");
		siteRequest.setRequestMethod("SearchPage.enUS");
		try {
			{
				userTrafficPerson(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchTrafficPerson(siteRequest, false, true, "/api/person", "SearchPage.enUS", c -> {
							if(c.succeeded()) {
								SearchList<TrafficPerson> listTrafficPerson = c.result();
								searchpage.enusTrafficPersonResponse(listTrafficPerson, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("searchpage.enusTrafficPerson succeeded. "));
									} else {
										LOGGER.error(String.format("searchpage.enusTrafficPerson failed. ", d.cause()));
										errorTrafficPerson(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("searchpage.enusTrafficPerson failed. ", c.cause()));
								errorTrafficPerson(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("searchpage.enusTrafficPerson failed. ", b.cause()));
						errorTrafficPerson(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("searchpage.enusTrafficPerson failed. ", ex));
			errorTrafficPerson(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void searchpage.enusTrafficPersonPageInit(TrafficPersonPage page, SearchList<TrafficPerson> listTrafficPerson) {
	}
	public void searchpage.enusTrafficPersonResponse(SearchList<TrafficPerson> listTrafficPerson, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficPerson.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200SearchPage.enUSTrafficPerson(listTrafficPerson, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("searchpage.enusTrafficPersonResponse failed. ", a.cause()));
					errorTrafficPerson(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchpage.enusTrafficPersonResponse failed. ", ex));
			errorTrafficPerson(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchPage.enUSTrafficPerson(SearchList<TrafficPerson> listTrafficPerson, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listTrafficPerson.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listTrafficPerson.getSiteRequest_(), buffer);
			TrafficPersonPage page = new TrafficPersonPage();
			SolrDocument pageSolrDocument = new SolrDocument();
			CaseInsensitiveHeaders requestHeaders = new CaseInsensitiveHeaders();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/api/person");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listTrafficPerson.size() == 1)
				siteRequest.setRequestPk(listTrafficPerson.get(0).getPk());
			siteRequest.setW(w);
			page.setListTrafficPerson(listTrafficPerson);
			page.setSiteRequest_(siteRequest);
			searchpage.enusTrafficPersonPageInit(page, listTrafficPerson);
			page.initDeepTrafficPersonPage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new OperationResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOGGER.error(String.format("response200SearchPage.enUSTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// General //

	public Future<TrafficPerson> defineIndexTrafficPerson(TrafficPerson trafficPerson, Handler<AsyncResult<TrafficPerson>> eventHandler) {
		Promise<TrafficPerson> promise = Promise.promise();
		SiteRequestEnUS siteRequest = trafficPerson.getSiteRequest_();
		defineTrafficPerson(trafficPerson, c -> {
			if(c.succeeded()) {
				attributeTrafficPerson(trafficPerson, d -> {
					if(d.succeeded()) {
						indexTrafficPerson(trafficPerson, e -> {
							if(e.succeeded()) {
								sqlCommitTrafficPerson(siteRequest, f -> {
									if(f.succeeded()) {
										sqlCloseTrafficPerson(siteRequest, g -> {
											if(g.succeeded()) {
												refreshTrafficPerson(trafficPerson, h -> {
													if(h.succeeded()) {
														eventHandler.handle(Future.succeededFuture(trafficPerson));
														promise.complete(trafficPerson);
													} else {
														LOGGER.error(String.format("refreshTrafficPerson failed. ", h.cause()));
														errorTrafficPerson(siteRequest, null, h);
													}
												});
											} else {
												LOGGER.error(String.format("defineIndexTrafficPerson failed. ", g.cause()));
												errorTrafficPerson(siteRequest, null, g);
											}
										});
									} else {
										LOGGER.error(String.format("defineIndexTrafficPerson failed. ", f.cause()));
										errorTrafficPerson(siteRequest, null, f);
									}
								});
							} else {
								LOGGER.error(String.format("defineIndexTrafficPerson failed. ", e.cause()));
								errorTrafficPerson(siteRequest, null, e);
							}
						});
					} else {
						LOGGER.error(String.format("defineIndexTrafficPerson failed. ", d.cause()));
						errorTrafficPerson(siteRequest, null, d);
					}
				});
			} else {
				LOGGER.error(String.format("defineIndexTrafficPerson failed. ", c.cause()));
				errorTrafficPerson(siteRequest, null, c);
			}
		});
		return promise.future();
	}

	public void createTrafficPerson(SiteRequestEnUS siteRequest, Handler<AsyncResult<TrafficPerson>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();
			String userId = siteRequest.getUserId();
			ZonedDateTime created = Optional.ofNullable(siteRequest.getJsonObject()).map(j -> j.getString("created")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())))).orElse(ZonedDateTime.now(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())));

			tx.preparedQuery(
					SiteContextEnUS.SQL_create
					, Tuple.of(TrafficPerson.class.getCanonicalName(), userId, created.toOffsetDateTime())
					, Collectors.toList()
					, createAsync
			-> {
				if(createAsync.succeeded()) {
					Row createLine = createAsync.result().value().stream().findFirst().orElseGet(() -> null);
					Long pk = createLine.getLong(0);
					TrafficPerson o = new TrafficPerson();
					o.setPk(pk);
					o.setSiteRequest_(siteRequest);
					eventHandler.handle(Future.succeededFuture(o));
				} else {
					LOGGER.error(String.format("createTrafficPerson failed. ", createAsync.cause()));
					eventHandler.handle(Future.failedFuture(createAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("createTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void errorTrafficPerson(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler, AsyncResult<?> resultAsync) {
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
		sqlRollbackTrafficPerson(siteRequest, a -> {
			if(a.succeeded()) {
				LOGGER.info(String.format("sql rollback. "));
				sqlCloseTrafficPerson(siteRequest, b -> {
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

	public void sqlConnectionTrafficPerson(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("sqlConnectionTrafficPerson failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlTransactionTrafficPerson(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
			LOGGER.error(String.format("sqlTransactionTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCommitTrafficPerson(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("sqlCommitTrafficPerson failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlRollbackTrafficPerson(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("sqlRollbackTrafficPerson failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCloseTrafficPerson(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
			LOGGER.error(String.format("sqlCloseTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SiteRequestEnUS generateSiteRequestEnUSForTrafficPerson(SiteContextEnUS siteContext, OperationRequest operationRequest) {
		return generateSiteRequestEnUSForTrafficPerson(siteContext, operationRequest, null);
	}

	public SiteRequestEnUS generateSiteRequestEnUSForTrafficPerson(SiteContextEnUS siteContext, OperationRequest operationRequest, JsonObject body) {
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

	public void userTrafficPerson(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			String userId = siteRequest.getUserId();
			if(userId == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				sqlConnectionTrafficPerson(siteRequest, a -> {
					if(a.succeeded()) {
						sqlTransactionTrafficPerson(siteRequest, b -> {
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
												userTrafficPersonDefine(siteRequest, jsonObject, false);

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
																		siteRequest.setUserKey(siteUser.getPk());
																		eventHandler.handle(Future.succeededFuture());
																	} else {
																		errorTrafficPerson(siteRequest, eventHandler, e);
																	}
																});
															} else {
																errorTrafficPerson(siteRequest, eventHandler, d);
															}
														});
													} else {
														errorTrafficPerson(siteRequest, eventHandler, c);
													}
												});
											} else {
												Long pkUser = userValues.getLong(0);
												SearchList<SiteUser> searchList = new SearchList<SiteUser>();
												searchList.setQuery("*:*");
												searchList.setStore(true);
												searchList.setC(SiteUser.class);
												searchList.addFilterQuery("userId_indexed_string:" + ClientUtils.escapeQueryChars(userId));
												searchList.addFilterQuery("pk_indexed_long:" + pkUser);
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
												Boolean define = userTrafficPersonDefine(siteRequest, jsonObject, true);
												if(define) {
													SiteUser siteUser;
													if(siteUser1 == null) {
														siteUser = new SiteUser();
														siteUser.setPk(pkUser);
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
																	siteRequest.setUserKey(siteUser2.getPk());
																	eventHandler.handle(Future.succeededFuture());
																} else {
																	errorTrafficPerson(siteRequest, eventHandler, e);
																}
															});
														} else {
															errorTrafficPerson(siteRequest, eventHandler, d);
														}
													});
												} else {
													siteRequest.setSiteUser(siteUser1);
													siteRequest.setUserName(siteUser1.getUserName());
													siteRequest.setUserFirstName(siteUser1.getUserFirstName());
													siteRequest.setUserLastName(siteUser1.getUserLastName());
													siteRequest.setUserId(siteUser1.getUserId());
													siteRequest.setUserKey(siteUser1.getPk());
													sqlRollbackTrafficPerson(siteRequest, c -> {
														if(c.succeeded()) {
															eventHandler.handle(Future.succeededFuture());
														} else {
															eventHandler.handle(Future.failedFuture(c.cause()));
															errorTrafficPerson(siteRequest, eventHandler, c);
														}
													});
												}
											}
										} catch(Exception e) {
											LOGGER.error(String.format("userTrafficPerson failed. ", e));
											eventHandler.handle(Future.failedFuture(e));
										}
									} else {
										LOGGER.error(String.format("userTrafficPerson failed. ", selectCAsync.cause()));
										eventHandler.handle(Future.failedFuture(selectCAsync.cause()));
									}
								});
							} else {
								LOGGER.error(String.format("userTrafficPerson failed. ", b.cause()));
								eventHandler.handle(Future.failedFuture(b.cause()));
							}
						});
					} else {
						LOGGER.error(String.format("userTrafficPerson failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("userTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public Boolean userTrafficPersonDefine(SiteRequestEnUS siteRequest, JsonObject jsonObject, Boolean patch) {
		if(patch) {
			return false;
		} else {
			return false;
		}
	}

	public void aSearchTrafficPersonQ(String uri, String apiMethod, SearchList<TrafficPerson> searchList, String entityVar, String valueIndexed, String varIndexed) {
		searchList.setQuery(varIndexed + ":" + ("*".equals(valueIndexed) ? valueIndexed : ClientUtils.escapeQueryChars(valueIndexed)));
		if(!"*".equals(entityVar)) {
		}
	}

	public void aSearchTrafficPersonFq(String uri, String apiMethod, SearchList<TrafficPerson> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		if(StringUtils.startsWith(valueIndexed, "[")) {
			String[] fqs = StringUtils.substringBefore(StringUtils.substringAfter(valueIndexed, "["), "]").split(" TO ");
			if(fqs.length != 2)
				throw new RuntimeException(String.format("\"%s\" invalid range query. ", valueIndexed));
			String fq1 = fqs[0].equals("*") ? fqs[0] : TrafficPerson.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[0]);
			String fq2 = fqs[1].equals("*") ? fqs[1] : TrafficPerson.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[1]);
			searchList.addFilterQuery(varIndexed + ":[" + fq1 + " TO " + fq2 + "]");
		} else {
			searchList.addFilterQuery(varIndexed + ":" + TrafficPerson.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), valueIndexed));
		}
	}

	public void aSearchTrafficPersonSort(String uri, String apiMethod, SearchList<TrafficPerson> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addSort(varIndexed, ORDER.valueOf(valueIndexed));
	}

	public void aSearchTrafficPersonRows(String uri, String apiMethod, SearchList<TrafficPerson> searchList, Integer valueRows) {
			searchList.setRows(apiMethod != null && apiMethod.contains("Search") ? valueRows : 10);
	}

	public void aSearchTrafficPersonStart(String uri, String apiMethod, SearchList<TrafficPerson> searchList, Integer valueStart) {
		searchList.setStart(valueStart);
	}

	public void aSearchTrafficPersonVar(String uri, String apiMethod, SearchList<TrafficPerson> searchList, String var, String value) {
		searchList.getSiteRequest_().getRequestVars().put(var, value);
	}

	public void aSearchTrafficPersonUri(String uri, String apiMethod, SearchList<TrafficPerson> searchList) {
	}

	public void varsTrafficPerson(SiteRequestEnUS siteRequest, Handler<AsyncResult<SearchList<OperationResponse>>> eventHandler) {
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
					LOGGER.error(String.format("aSearchTrafficPerson failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOGGER.error(String.format("aSearchTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void aSearchTrafficPerson(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, String uri, String apiMethod, Handler<AsyncResult<SearchList<TrafficPerson>>> eventHandler) {
		try {
			OperationRequest operationRequest = siteRequest.getOperationRequest();
			String entityListStr = siteRequest.getOperationRequest().getParams().getJsonObject("query").getString("fl");
			String[] entityList = entityListStr == null ? null : entityListStr.split(",\\s*");
			SearchList<TrafficPerson> searchList = new SearchList<TrafficPerson>();
			searchList.setPopulate(populate);
			searchList.setStore(store);
			searchList.setQuery("*:*");
			searchList.setC(TrafficPerson.class);
			searchList.setSiteRequest_(siteRequest);
			if(entityList != null)
				searchList.addFields(entityList);
			searchList.add("json.facet", "{max_modified:'max(modified_indexed_date)'}");

			String id = operationRequest.getParams().getJsonObject("path").getString("id");
			if(id != null) {
				searchList.addFilterQuery("(id:" + ClientUtils.escapeQueryChars(id) + " OR objectId_indexed_string:" + ClientUtils.escapeQueryChars(id) + ")");
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
								varIndexed = "*".equals(entityVar) ? entityVar : TrafficPerson.varSearchTrafficPerson(entityVar);
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								valueIndexed = StringUtils.isEmpty(valueIndexed) ? "*" : valueIndexed;
								aSearchTrafficPersonQ(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "fq":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								varIndexed = TrafficPerson.varIndexedTrafficPerson(entityVar);
								aSearchTrafficPersonFq(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "sort":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, " "));
								valueIndexed = StringUtils.trim(StringUtils.substringAfter((String)paramObject, " "));
								varIndexed = TrafficPerson.varIndexedTrafficPerson(entityVar);
								aSearchTrafficPersonSort(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "start":
								valueStart = (Integer)paramObject;
								aSearchTrafficPersonStart(uri, apiMethod, searchList, valueStart);
								break;
							case "rows":
								valueRows = (Integer)paramObject;
								aSearchTrafficPersonRows(uri, apiMethod, searchList, valueRows);
								break;
							case "var":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								aSearchTrafficPersonVar(uri, apiMethod, searchList, entityVar, valueIndexed);
								break;
						}
					}
					aSearchTrafficPersonUri(uri, apiMethod, searchList);
				} catch(Exception e) {
					LOGGER.error(String.format("aSearchTrafficPerson failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			if("*:*".equals(searchList.getQuery()) && searchList.getSorts().size() == 0) {
				searchList.addSort("created_indexed_date", ORDER.desc);
			}
			searchList.initDeepForClass(siteRequest);
			eventHandler.handle(Future.succeededFuture(searchList));
		} catch(Exception e) {
			LOGGER.error(String.format("aSearchTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void defineTrafficPerson(TrafficPerson o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			Transaction tx = siteRequest.getTx();
			Long pk = o.getPk();
			tx.preparedQuery(
					SiteContextEnUS.SQL_define
					, Tuple.of(pk)
					, Collectors.toList()
					, defineAsync
			-> {
				if(defineAsync.succeeded()) {
					try {
						for(Row definition : defineAsync.result().value()) {
							try {
								o.defineForClass(definition.getString(0), definition.getString(1));
							} catch(Exception e) {
								LOGGER.error(String.format("defineTrafficPerson failed. ", e));
								LOGGER.error(e);
							}
						}
						eventHandler.handle(Future.succeededFuture());
					} catch(Exception e) {
						LOGGER.error(String.format("defineTrafficPerson failed. ", e));
						eventHandler.handle(Future.failedFuture(e));
					}
				} else {
					LOGGER.error(String.format("defineTrafficPerson failed. ", defineAsync.cause()));
					eventHandler.handle(Future.failedFuture(defineAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("defineTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void attributeTrafficPerson(TrafficPerson o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			Transaction tx = siteRequest.getTx();
			Long pk = o.getPk();
			tx.preparedQuery(
					SiteContextEnUS.SQL_attribute
					, Tuple.of(pk, pk)
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
						LOGGER.error(String.format("attributeTrafficPerson failed. ", attributeAsync.cause()));
						eventHandler.handle(Future.failedFuture(attributeAsync.cause()));
					}
				} catch(Exception e) {
					LOGGER.error(String.format("attributeTrafficPerson failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("attributeTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void indexTrafficPerson(TrafficPerson o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			o.initDeepForClass(siteRequest);
			o.indexForClass();
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOGGER.error(String.format("indexTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void refreshTrafficPerson(TrafficPerson o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Boolean refresh = !"false".equals(siteRequest.getRequestVars().get("refresh"));
			if(refresh && BooleanUtils.isFalse(Optional.ofNullable(siteRequest.getApiRequest_()).map(ApiRequest::getEmpty).orElse(true))) {
				SearchList<TrafficPerson> searchList = new SearchList<TrafficPerson>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(TrafficPerson.class);
				searchList.addFilterQuery("modified_indexed_date:[" + DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(siteRequest.getApiRequest_().getCreated().toInstant(), ZoneId.of("UTC"))) + " TO *]");
				searchList.setRows(1000);
				searchList.initDeepSearchList(siteRequest);
				List<Future> futures = new ArrayList<>();

				for(int i=0; i < pks.size(); i++) {
					Long pk2 = pks.get(i);
					String classSimpleName2 = classes.get(i);
				}

				CompositeFuture.all(futures).setHandler(a -> {
					if(a.succeeded()) {
						TrafficPersonEnUSApiServiceImpl service = new TrafficPersonEnUSApiServiceImpl(siteRequest.getSiteContext_());
						List<Future> futures2 = new ArrayList<>();
						for(TrafficPerson o2 : searchList.getList()) {
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForTrafficPerson(siteContext, siteRequest.getOperationRequest(), new JsonObject());
							o2.setSiteRequest_(siteRequest2);
							futures2.add(
								service.patchTrafficPersonFuture(o2, false, b -> {
									if(b.succeeded()) {
									} else {
										LOGGER.info(String.format("TrafficPerson %s failed. ", o2.getPk()));
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
								errorTrafficPerson(siteRequest, eventHandler, b);
							}
						});
					} else {
						LOGGER.error("Refresh relations failed. ", a.cause());
						errorTrafficPerson(siteRequest, eventHandler, a);
					}
				});
			} else {
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOGGER.error(String.format("refreshTrafficPerson failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}
}
