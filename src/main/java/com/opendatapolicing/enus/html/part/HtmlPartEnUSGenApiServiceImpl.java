package com.opendatapolicing.enus.html.part;

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
public class HtmlPartEnUSGenApiServiceImpl implements HtmlPartEnUSGenApiService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(HtmlPartEnUSGenApiServiceImpl.class);

	protected static final String SERVICE_ADDRESS = "HtmlPartEnUSApiServiceImpl";

	protected SiteContextEnUS siteContext;

	public HtmlPartEnUSGenApiServiceImpl(SiteContextEnUS siteContext) {
		this.siteContext = siteContext;
	}

	// POST //

	@Override
	public void postHtmlPart(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForHtmlPart(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/html-part");
		siteRequest.setRequestMethod("POST");
		try {
			LOGGER.info(String.format("postHtmlPart started. "));
			{
				userHtmlPart(siteRequest, b -> {
					if(b.succeeded()) {
						ApiRequest apiRequest = new ApiRequest();
						apiRequest.setRows(1);
						apiRequest.setNumFound(1L);
						apiRequest.setNumPATCH(0L);
						apiRequest.initDeepApiRequest(siteRequest);
						siteRequest.setApiRequest_(apiRequest);
						siteRequest.getVertx().eventBus().publish("websocketHtmlPart", JsonObject.mapFrom(apiRequest).toString());
						postHtmlPartFuture(siteRequest, false, c -> {
							if(c.succeeded()) {
								HtmlPart htmlPart = c.result();
								apiRequest.setPk(htmlPart.getPk());
								postHtmlPartResponse(htmlPart, d -> {
										if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("postHtmlPart succeeded. "));
									} else {
										LOGGER.error(String.format("postHtmlPart failed. ", d.cause()));
										errorHtmlPart(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("postHtmlPart failed. ", c.cause()));
								errorHtmlPart(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("postHtmlPart failed. ", b.cause()));
						errorHtmlPart(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("postHtmlPart failed. ", ex));
			errorHtmlPart(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public Future<HtmlPart> postHtmlPartFuture(SiteRequestEnUS siteRequest, Boolean inheritPk, Handler<AsyncResult<HtmlPart>> eventHandler) {
		Promise<HtmlPart> promise = Promise.promise();
		try {
			sqlConnectionHtmlPart(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionHtmlPart(siteRequest, b -> {
						if(b.succeeded()) {
							createHtmlPart(siteRequest, c -> {
								if(c.succeeded()) {
									HtmlPart htmlPart = c.result();
									sqlPOSTHtmlPart(htmlPart, inheritPk, d -> {
										if(d.succeeded()) {
											defineIndexHtmlPart(htmlPart, e -> {
												if(e.succeeded()) {
													ApiRequest apiRequest = siteRequest.getApiRequest_();
													if(apiRequest != null) {
														apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
														htmlPart.apiRequestHtmlPart();
														siteRequest.getVertx().eventBus().publish("websocketHtmlPart", JsonObject.mapFrom(apiRequest).toString());
													}
													eventHandler.handle(Future.succeededFuture(htmlPart));
													promise.complete(htmlPart);
												} else {
													LOGGER.error(String.format("postHtmlPartFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOGGER.error(String.format("postHtmlPartFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("postHtmlPartFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("postHtmlPartFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("postHtmlPartFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("postHtmlPartFuture failed. ", e));
			errorHtmlPart(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPOSTHtmlPart(HtmlPart o, Boolean inheritPk, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
									a.handle(Future.failedFuture(new Exception("value HtmlPart.inheritPk failed", b.cause())));
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
									a.handle(Future.failedFuture(new Exception("value HtmlPart.archived failed", b.cause())));
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
									a.handle(Future.failedFuture(new Exception("value HtmlPart.deleted failed", b.cause())));
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
					LOGGER.error(String.format("sqlPOSTHtmlPart failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPOSTHtmlPart failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void postHtmlPartResponse(HtmlPart htmlPart, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = htmlPart.getSiteRequest_();
		try {
			response200POSTHtmlPart(htmlPart, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("postHtmlPartResponse failed. ", a.cause()));
					errorHtmlPart(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("postHtmlPartResponse failed. ", ex));
			errorHtmlPart(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200POSTHtmlPart(HtmlPart o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200POSTHtmlPart failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTImport.enUS //

	@Override
	public void putimport.enusHtmlPart(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForHtmlPart(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/html-part");
		siteRequest.setRequestMethod("PUTImport.enUS");
		try {
			LOGGER.info(String.format("putimport.enusHtmlPart started. "));
			{
				userHtmlPart(siteRequest, b -> {
					if(b.succeeded()) {
						putimport.enusHtmlPartResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								LOGGER.info(String.format("putimport.enusHtmlPart succeeded. "));
							} else {
								LOGGER.error(String.format("putimport.enusHtmlPart failed. ", c.cause()));
								errorHtmlPart(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("putimport.enusHtmlPart failed. ", b.cause()));
						errorHtmlPart(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("putimport.enusHtmlPart failed. ", ex));
			errorHtmlPart(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void putimport.enusHtmlPartResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTImport.enUSHtmlPart(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putimport.enusHtmlPartResponse failed. ", a.cause()));
					errorHtmlPart(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putimport.enusHtmlPartResponse failed. ", ex));
			errorHtmlPart(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTImport.enUSHtmlPart(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTImport.enUSHtmlPart failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTMerge.enUS //

	@Override
	public void putmerge.enusHtmlPart(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForHtmlPart(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/html-part");
		siteRequest.setRequestMethod("PUTMerge.enUS");
		try {
			LOGGER.info(String.format("putmerge.enusHtmlPart started. "));
			{
				userHtmlPart(siteRequest, b -> {
					if(b.succeeded()) {
						putmerge.enusHtmlPartResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								LOGGER.info(String.format("putmerge.enusHtmlPart succeeded. "));
							} else {
								LOGGER.error(String.format("putmerge.enusHtmlPart failed. ", c.cause()));
								errorHtmlPart(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("putmerge.enusHtmlPart failed. ", b.cause()));
						errorHtmlPart(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("putmerge.enusHtmlPart failed. ", ex));
			errorHtmlPart(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void putmerge.enusHtmlPartResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTMerge.enUSHtmlPart(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putmerge.enusHtmlPartResponse failed. ", a.cause()));
					errorHtmlPart(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putmerge.enusHtmlPartResponse failed. ", ex));
			errorHtmlPart(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTMerge.enUSHtmlPart(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTMerge.enUSHtmlPart failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTCopy.enUS //

	@Override
	public void putcopy.enusHtmlPart(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForHtmlPart(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/html-part");
		siteRequest.setRequestMethod("PUTCopy.enUS");
		try {
			LOGGER.info(String.format("putcopy.enusHtmlPart started. "));
			{
				userHtmlPart(siteRequest, b -> {
					if(b.succeeded()) {
						putcopy.enusHtmlPartResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								LOGGER.info(String.format("putcopy.enusHtmlPart succeeded. "));
							} else {
								LOGGER.error(String.format("putcopy.enusHtmlPart failed. ", c.cause()));
								errorHtmlPart(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("putcopy.enusHtmlPart failed. ", b.cause()));
						errorHtmlPart(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopy.enusHtmlPart failed. ", ex));
			errorHtmlPart(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public Future<HtmlPart> putcopy.enusHtmlPartFuture(SiteRequestEnUS siteRequest, JsonObject jsonObject, Handler<AsyncResult<HtmlPart>> eventHandler) {
		Promise<HtmlPart> promise = Promise.promise();
		try {
		} catch(Exception e) {
			LOGGER.error(String.format("putcopy.enusHtmlPartFuture failed. ", e));
			errorHtmlPart(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void putcopy.enusHtmlPartResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTCopy.enUSHtmlPart(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putcopy.enusHtmlPartResponse failed. ", a.cause()));
					errorHtmlPart(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopy.enusHtmlPartResponse failed. ", ex));
			errorHtmlPart(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTCopy.enUSHtmlPart(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTCopy.enUSHtmlPart failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchHtmlPart(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForHtmlPart(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/html-part");
		siteRequest.setRequestMethod("PATCH");
		try {
			LOGGER.info(String.format("patchHtmlPart started. "));
			{
				userHtmlPart(siteRequest, b -> {
					if(b.succeeded()) {
						patchHtmlPartResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											aSearchHtmlPart(siteRequest, false, true, "/api/html-part", "PATCH", d -> {
												if(d.succeeded()) {
													SearchList<HtmlPart> listHtmlPart = d.result();

													if(listHtmlPart.getQueryResponse().getResults().getNumFound() > 1) {
														List<String> roles2 = Arrays.asList("SiteAdmin");
														if(
																!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles2)
																&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles2)
																) {
															String message = String.format("roles required: " + String.join(", ", roles2));
															LOGGER.error(message);
															errorHtmlPart(siteRequest, eventHandler, Future.failedFuture(message));
														}
													} else {

														ApiRequest apiRequest = new ApiRequest();
														apiRequest.setRows(listHtmlPart.getRows());
														apiRequest.setNumFound(listHtmlPart.getQueryResponse().getResults().getNumFound());
														apiRequest.setNumPATCH(0L);
														apiRequest.initDeepApiRequest(siteRequest);
														siteRequest.setApiRequest_(apiRequest);
														siteRequest.getVertx().eventBus().publish("websocketHtmlPart", JsonObject.mapFrom(apiRequest).toString());
														SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listHtmlPart.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(null);
														Date date = null;
														if(facets != null)
														date = (Date)facets.get("max_modified");
														String dt;
														if(date == null)
															dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));
														else
															dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")));
														listHtmlPart.addFilterQuery(String.format("modified_indexed_date:[* TO %s]", dt));

														try {
															listPATCHHtmlPart(apiRequest, listHtmlPart, dt, e -> {
																if(e.succeeded()) {
																	patchHtmlPartResponse(siteRequest, f -> {
																		if(f.succeeded()) {
																			LOGGER.info(String.format("patchHtmlPart succeeded. "));
																			blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																		} else {
																			LOGGER.error(String.format("patchHtmlPart failed. ", f.cause()));
																			errorHtmlPart(siteRequest, null, f);
																		}
																	});
																} else {
																	LOGGER.error(String.format("patchHtmlPart failed. ", e.cause()));
																	errorHtmlPart(siteRequest, null, e);
																}
															});
														} catch(Exception ex) {
															LOGGER.error(String.format("patchHtmlPart failed. ", ex));
															errorHtmlPart(siteRequest, null, Future.failedFuture(ex));
														}
													}
										} else {
													LOGGER.error(String.format("patchHtmlPart failed. ", d.cause()));
													errorHtmlPart(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOGGER.error(String.format("patchHtmlPart failed. ", ex));
											errorHtmlPart(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOGGER.error(String.format("patchHtmlPart failed. ", c.cause()));
								errorHtmlPart(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("patchHtmlPart failed. ", b.cause()));
						errorHtmlPart(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("patchHtmlPart failed. ", ex));
			errorHtmlPart(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPATCHHtmlPart(ApiRequest apiRequest, SearchList<HtmlPart> listHtmlPart, String dt, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listHtmlPart.getSiteRequest_();
		listHtmlPart.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForHtmlPart(siteContext, siteRequest.getOperationRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				patchHtmlPartFuture(o, false, a -> {
					if(a.succeeded()) {
					} else {
						errorHtmlPart(siteRequest2, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				if(listHtmlPart.next(dt)) {
					listPATCHHtmlPart(apiRequest, listHtmlPart, dt, eventHandler);
				} else {
					response200PATCHHtmlPart(siteRequest, eventHandler);
				}
			} else {
				LOGGER.error(String.format("listPATCHHtmlPart failed. ", a.cause()));
				errorHtmlPart(listHtmlPart.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<HtmlPart> patchHtmlPartFuture(HtmlPart o, Boolean inheritPk, Handler<AsyncResult<HtmlPart>> eventHandler) {
		Promise<HtmlPart> promise = Promise.promise();
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			if(apiRequest != null && apiRequest.getNumFound() == 1L) {
				apiRequest.setOriginal(o);
				apiRequest.setPk(o.getPk());
			}
			sqlConnectionHtmlPart(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionHtmlPart(siteRequest, b -> {
						if(b.succeeded()) {
							sqlPATCHHtmlPart(o, inheritPk, c -> {
								if(c.succeeded()) {
									HtmlPart htmlPart = c.result();
									defineIndexHtmlPart(htmlPart, d -> {
										if(d.succeeded()) {
											if(apiRequest != null) {
												apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
												if(apiRequest.getNumFound() == 1L) {
													htmlPart.apiRequestHtmlPart();
												}
												siteRequest.getVertx().eventBus().publish("websocketHtmlPart", JsonObject.mapFrom(apiRequest).toString());
											}
											eventHandler.handle(Future.succeededFuture(htmlPart));
											promise.complete(htmlPart);
										} else {
											LOGGER.error(String.format("patchHtmlPartFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("patchHtmlPartFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("patchHtmlPartFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("patchHtmlPartFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("patchHtmlPartFuture failed. ", e));
			errorHtmlPart(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPATCHHtmlPart(HtmlPart o, Boolean inheritPk, Handler<AsyncResult<HtmlPart>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Transaction tx = siteRequest.getTx();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			HtmlPart o2 = new HtmlPart();
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
										a.handle(Future.failedFuture(new Exception("value HtmlPart.inheritPk failed", b.cause())));
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
										a.handle(Future.failedFuture(new Exception("value HtmlPart.inheritPk failed", b.cause())));
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
										a.handle(Future.failedFuture(new Exception("value HtmlPart.archived failed", b.cause())));
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
										a.handle(Future.failedFuture(new Exception("value HtmlPart.archived failed", b.cause())));
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
										a.handle(Future.failedFuture(new Exception("value HtmlPart.deleted failed", b.cause())));
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
										a.handle(Future.failedFuture(new Exception("value HtmlPart.deleted failed", b.cause())));
								});
							}));
						}
						break;
				}
			}
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					HtmlPart o3 = new HtmlPart();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
					eventHandler.handle(Future.succeededFuture(o3));
				} else {
					LOGGER.error(String.format("sqlPATCHHtmlPart failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPATCHHtmlPart failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void patchHtmlPartResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PATCHHtmlPart(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("patchHtmlPartResponse failed. ", a.cause()));
					errorHtmlPart(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("patchHtmlPartResponse failed. ", ex));
			errorHtmlPart(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PATCHHtmlPart(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PATCHHtmlPart failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// GET.enUS //

	@Override
	public void get.enusHtmlPart(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForHtmlPart(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/html-part/{id}");
		siteRequest.setRequestMethod("GET.enUS");
		try {
			{
				userHtmlPart(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchHtmlPart(siteRequest, false, true, "/api/html-part/{id}", "GET.enUS", c -> {
							if(c.succeeded()) {
								SearchList<HtmlPart> listHtmlPart = c.result();
								get.enusHtmlPartResponse(listHtmlPart, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("get.enusHtmlPart succeeded. "));
									} else {
										LOGGER.error(String.format("get.enusHtmlPart failed. ", d.cause()));
										errorHtmlPart(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("get.enusHtmlPart failed. ", c.cause()));
								errorHtmlPart(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("get.enusHtmlPart failed. ", b.cause()));
						errorHtmlPart(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("get.enusHtmlPart failed. ", ex));
			errorHtmlPart(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void get.enusHtmlPartResponse(SearchList<HtmlPart> listHtmlPart, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listHtmlPart.getSiteRequest_();
		try {
			response200GET.enUSHtmlPart(listHtmlPart, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("get.enusHtmlPartResponse failed. ", a.cause()));
					errorHtmlPart(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("get.enusHtmlPartResponse failed. ", ex));
			errorHtmlPart(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200GET.enUSHtmlPart(SearchList<HtmlPart> listHtmlPart, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listHtmlPart.getSiteRequest_();
			SolrDocumentList solrDocuments = listHtmlPart.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listHtmlPart.getList().stream().findFirst().orElse(null));
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200GET.enUSHtmlPart failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// Search //

	@Override
	public void searchHtmlPart(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForHtmlPart(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/html-part");
		siteRequest.setRequestMethod("Search");
		try {
			{
				userHtmlPart(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchHtmlPart(siteRequest, false, true, "/api/html-part", "Search", c -> {
							if(c.succeeded()) {
								SearchList<HtmlPart> listHtmlPart = c.result();
								searchHtmlPartResponse(listHtmlPart, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("searchHtmlPart succeeded. "));
									} else {
										LOGGER.error(String.format("searchHtmlPart failed. ", d.cause()));
										errorHtmlPart(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("searchHtmlPart failed. ", c.cause()));
								errorHtmlPart(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("searchHtmlPart failed. ", b.cause()));
						errorHtmlPart(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("searchHtmlPart failed. ", ex));
			errorHtmlPart(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void searchHtmlPartResponse(SearchList<HtmlPart> listHtmlPart, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listHtmlPart.getSiteRequest_();
		try {
			response200SearchHtmlPart(listHtmlPart, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("searchHtmlPartResponse failed. ", a.cause()));
					errorHtmlPart(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchHtmlPartResponse failed. ", ex));
			errorHtmlPart(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchHtmlPart(SearchList<HtmlPart> listHtmlPart, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listHtmlPart.getSiteRequest_();
			QueryResponse responseSearch = listHtmlPart.getQueryResponse();
			SolrDocumentList solrDocuments = listHtmlPart.getSolrDocumentList();
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
			listHtmlPart.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
				List<String> fls = listHtmlPart.getFields();
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
			LOGGER.error(String.format("response200SearchHtmlPart failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// SearchPage.enUS //

	@Override
	public void searchpage.enusHtmlPartId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		searchpage.enusHtmlPart(operationRequest, eventHandler);
	}

	@Override
	public void searchpage.enusHtmlPart(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForHtmlPart(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/html-part");
		siteRequest.setRequestMethod("SearchPage.enUS");
		try {
			{
				userHtmlPart(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchHtmlPart(siteRequest, false, true, "/api/html-part", "SearchPage.enUS", c -> {
							if(c.succeeded()) {
								SearchList<HtmlPart> listHtmlPart = c.result();
								searchpage.enusHtmlPartResponse(listHtmlPart, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("searchpage.enusHtmlPart succeeded. "));
									} else {
										LOGGER.error(String.format("searchpage.enusHtmlPart failed. ", d.cause()));
										errorHtmlPart(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("searchpage.enusHtmlPart failed. ", c.cause()));
								errorHtmlPart(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("searchpage.enusHtmlPart failed. ", b.cause()));
						errorHtmlPart(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("searchpage.enusHtmlPart failed. ", ex));
			errorHtmlPart(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void searchpage.enusHtmlPartPageInit(HtmlPartPage page, SearchList<HtmlPart> listHtmlPart) {
	}
	public void searchpage.enusHtmlPartResponse(SearchList<HtmlPart> listHtmlPart, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listHtmlPart.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200SearchPage.enUSHtmlPart(listHtmlPart, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("searchpage.enusHtmlPartResponse failed. ", a.cause()));
					errorHtmlPart(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchpage.enusHtmlPartResponse failed. ", ex));
			errorHtmlPart(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchPage.enUSHtmlPart(SearchList<HtmlPart> listHtmlPart, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listHtmlPart.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listHtmlPart.getSiteRequest_(), buffer);
			HtmlPartPage page = new HtmlPartPage();
			SolrDocument pageSolrDocument = new SolrDocument();
			CaseInsensitiveHeaders requestHeaders = new CaseInsensitiveHeaders();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/api/html-part");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listHtmlPart.size() == 1)
				siteRequest.setRequestPk(listHtmlPart.get(0).getPk());
			siteRequest.setW(w);
			page.setListHtmlPart(listHtmlPart);
			page.setSiteRequest_(siteRequest);
			searchpage.enusHtmlPartPageInit(page, listHtmlPart);
			page.initDeepHtmlPartPage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new OperationResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOGGER.error(String.format("response200SearchPage.enUSHtmlPart failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// General //

	public Future<HtmlPart> defineIndexHtmlPart(HtmlPart htmlPart, Handler<AsyncResult<HtmlPart>> eventHandler) {
		Promise<HtmlPart> promise = Promise.promise();
		SiteRequestEnUS siteRequest = htmlPart.getSiteRequest_();
		defineHtmlPart(htmlPart, c -> {
			if(c.succeeded()) {
				attributeHtmlPart(htmlPart, d -> {
					if(d.succeeded()) {
						indexHtmlPart(htmlPart, e -> {
							if(e.succeeded()) {
								sqlCommitHtmlPart(siteRequest, f -> {
									if(f.succeeded()) {
										sqlCloseHtmlPart(siteRequest, g -> {
											if(g.succeeded()) {
												refreshHtmlPart(htmlPart, h -> {
													if(h.succeeded()) {
														eventHandler.handle(Future.succeededFuture(htmlPart));
														promise.complete(htmlPart);
													} else {
														LOGGER.error(String.format("refreshHtmlPart failed. ", h.cause()));
														errorHtmlPart(siteRequest, null, h);
													}
												});
											} else {
												LOGGER.error(String.format("defineIndexHtmlPart failed. ", g.cause()));
												errorHtmlPart(siteRequest, null, g);
											}
										});
									} else {
										LOGGER.error(String.format("defineIndexHtmlPart failed. ", f.cause()));
										errorHtmlPart(siteRequest, null, f);
									}
								});
							} else {
								LOGGER.error(String.format("defineIndexHtmlPart failed. ", e.cause()));
								errorHtmlPart(siteRequest, null, e);
							}
						});
					} else {
						LOGGER.error(String.format("defineIndexHtmlPart failed. ", d.cause()));
						errorHtmlPart(siteRequest, null, d);
					}
				});
			} else {
				LOGGER.error(String.format("defineIndexHtmlPart failed. ", c.cause()));
				errorHtmlPart(siteRequest, null, c);
			}
		});
		return promise.future();
	}

	public void createHtmlPart(SiteRequestEnUS siteRequest, Handler<AsyncResult<HtmlPart>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();
			String userId = siteRequest.getUserId();
			ZonedDateTime created = Optional.ofNullable(siteRequest.getJsonObject()).map(j -> j.getString("created")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())))).orElse(ZonedDateTime.now(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())));

			tx.preparedQuery(
					SiteContextEnUS.SQL_create
					, Tuple.of(HtmlPart.class.getCanonicalName(), userId, created.toOffsetDateTime())
					, Collectors.toList()
					, createAsync
			-> {
				if(createAsync.succeeded()) {
					Row createLine = createAsync.result().value().stream().findFirst().orElseGet(() -> null);
					Long pk = createLine.getLong(0);
					HtmlPart o = new HtmlPart();
					o.setPk(pk);
					o.setSiteRequest_(siteRequest);
					eventHandler.handle(Future.succeededFuture(o));
				} else {
					LOGGER.error(String.format("createHtmlPart failed. ", createAsync.cause()));
					eventHandler.handle(Future.failedFuture(createAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("createHtmlPart failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void errorHtmlPart(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler, AsyncResult<?> resultAsync) {
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
		sqlRollbackHtmlPart(siteRequest, a -> {
			if(a.succeeded()) {
				LOGGER.info(String.format("sql rollback. "));
				sqlCloseHtmlPart(siteRequest, b -> {
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

	public void sqlConnectionHtmlPart(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("sqlConnectionHtmlPart failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlHtmlPart failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlTransactionHtmlPart(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
			LOGGER.error(String.format("sqlTransactionHtmlPart failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCommitHtmlPart(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("sqlCommitHtmlPart failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlHtmlPart failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlRollbackHtmlPart(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("sqlRollbackHtmlPart failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlHtmlPart failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCloseHtmlPart(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
			LOGGER.error(String.format("sqlCloseHtmlPart failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SiteRequestEnUS generateSiteRequestEnUSForHtmlPart(SiteContextEnUS siteContext, OperationRequest operationRequest) {
		return generateSiteRequestEnUSForHtmlPart(siteContext, operationRequest, null);
	}

	public SiteRequestEnUS generateSiteRequestEnUSForHtmlPart(SiteContextEnUS siteContext, OperationRequest operationRequest, JsonObject body) {
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

	public void userHtmlPart(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			String userId = siteRequest.getUserId();
			if(userId == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				sqlConnectionHtmlPart(siteRequest, a -> {
					if(a.succeeded()) {
						sqlTransactionHtmlPart(siteRequest, b -> {
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
												userHtmlPartDefine(siteRequest, jsonObject, false);

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
																		errorHtmlPart(siteRequest, eventHandler, e);
																	}
																});
															} else {
																errorHtmlPart(siteRequest, eventHandler, d);
															}
														});
													} else {
														errorHtmlPart(siteRequest, eventHandler, c);
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
												Boolean define = userHtmlPartDefine(siteRequest, jsonObject, true);
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
																	errorHtmlPart(siteRequest, eventHandler, e);
																}
															});
														} else {
															errorHtmlPart(siteRequest, eventHandler, d);
														}
													});
												} else {
													siteRequest.setSiteUser(siteUser1);
													siteRequest.setUserName(siteUser1.getUserName());
													siteRequest.setUserFirstName(siteUser1.getUserFirstName());
													siteRequest.setUserLastName(siteUser1.getUserLastName());
													siteRequest.setUserId(siteUser1.getUserId());
													siteRequest.setUserKey(siteUser1.getPk());
													sqlRollbackHtmlPart(siteRequest, c -> {
														if(c.succeeded()) {
															eventHandler.handle(Future.succeededFuture());
														} else {
															eventHandler.handle(Future.failedFuture(c.cause()));
															errorHtmlPart(siteRequest, eventHandler, c);
														}
													});
												}
											}
										} catch(Exception e) {
											LOGGER.error(String.format("userHtmlPart failed. ", e));
											eventHandler.handle(Future.failedFuture(e));
										}
									} else {
										LOGGER.error(String.format("userHtmlPart failed. ", selectCAsync.cause()));
										eventHandler.handle(Future.failedFuture(selectCAsync.cause()));
									}
								});
							} else {
								LOGGER.error(String.format("userHtmlPart failed. ", b.cause()));
								eventHandler.handle(Future.failedFuture(b.cause()));
							}
						});
					} else {
						LOGGER.error(String.format("userHtmlPart failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("userHtmlPart failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public Boolean userHtmlPartDefine(SiteRequestEnUS siteRequest, JsonObject jsonObject, Boolean patch) {
		if(patch) {
			return false;
		} else {
			return false;
		}
	}

	public void aSearchHtmlPartQ(String uri, String apiMethod, SearchList<HtmlPart> searchList, String entityVar, String valueIndexed, String varIndexed) {
		searchList.setQuery(varIndexed + ":" + ("*".equals(valueIndexed) ? valueIndexed : ClientUtils.escapeQueryChars(valueIndexed)));
		if(!"*".equals(entityVar)) {
		}
	}

	public void aSearchHtmlPartFq(String uri, String apiMethod, SearchList<HtmlPart> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		if(StringUtils.startsWith(valueIndexed, "[")) {
			String[] fqs = StringUtils.substringBefore(StringUtils.substringAfter(valueIndexed, "["), "]").split(" TO ");
			if(fqs.length != 2)
				throw new RuntimeException(String.format("\"%s\" invalid range query. ", valueIndexed));
			String fq1 = fqs[0].equals("*") ? fqs[0] : HtmlPart.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[0]);
			String fq2 = fqs[1].equals("*") ? fqs[1] : HtmlPart.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[1]);
			searchList.addFilterQuery(varIndexed + ":[" + fq1 + " TO " + fq2 + "]");
		} else {
			searchList.addFilterQuery(varIndexed + ":" + HtmlPart.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), valueIndexed));
		}
	}

	public void aSearchHtmlPartSort(String uri, String apiMethod, SearchList<HtmlPart> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addSort(varIndexed, ORDER.valueOf(valueIndexed));
	}

	public void aSearchHtmlPartRows(String uri, String apiMethod, SearchList<HtmlPart> searchList, Integer valueRows) {
			searchList.setRows(apiMethod != null && apiMethod.contains("Search") ? valueRows : 10);
	}

	public void aSearchHtmlPartStart(String uri, String apiMethod, SearchList<HtmlPart> searchList, Integer valueStart) {
		searchList.setStart(valueStart);
	}

	public void aSearchHtmlPartVar(String uri, String apiMethod, SearchList<HtmlPart> searchList, String var, String value) {
		searchList.getSiteRequest_().getRequestVars().put(var, value);
	}

	public void aSearchHtmlPartUri(String uri, String apiMethod, SearchList<HtmlPart> searchList) {
	}

	public void varsHtmlPart(SiteRequestEnUS siteRequest, Handler<AsyncResult<SearchList<OperationResponse>>> eventHandler) {
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
					LOGGER.error(String.format("aSearchHtmlPart failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOGGER.error(String.format("aSearchHtmlPart failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void aSearchHtmlPart(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, String uri, String apiMethod, Handler<AsyncResult<SearchList<HtmlPart>>> eventHandler) {
		try {
			OperationRequest operationRequest = siteRequest.getOperationRequest();
			String entityListStr = siteRequest.getOperationRequest().getParams().getJsonObject("query").getString("fl");
			String[] entityList = entityListStr == null ? null : entityListStr.split(",\\s*");
			SearchList<HtmlPart> searchList = new SearchList<HtmlPart>();
			searchList.setPopulate(populate);
			searchList.setStore(store);
			searchList.setQuery("*:*");
			searchList.setC(HtmlPart.class);
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
								varIndexed = "*".equals(entityVar) ? entityVar : HtmlPart.varSearchHtmlPart(entityVar);
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								valueIndexed = StringUtils.isEmpty(valueIndexed) ? "*" : valueIndexed;
								aSearchHtmlPartQ(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "fq":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								varIndexed = HtmlPart.varIndexedHtmlPart(entityVar);
								aSearchHtmlPartFq(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "sort":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, " "));
								valueIndexed = StringUtils.trim(StringUtils.substringAfter((String)paramObject, " "));
								varIndexed = HtmlPart.varIndexedHtmlPart(entityVar);
								aSearchHtmlPartSort(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "start":
								valueStart = (Integer)paramObject;
								aSearchHtmlPartStart(uri, apiMethod, searchList, valueStart);
								break;
							case "rows":
								valueRows = (Integer)paramObject;
								aSearchHtmlPartRows(uri, apiMethod, searchList, valueRows);
								break;
							case "var":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								aSearchHtmlPartVar(uri, apiMethod, searchList, entityVar, valueIndexed);
								break;
						}
					}
					aSearchHtmlPartUri(uri, apiMethod, searchList);
				} catch(Exception e) {
					LOGGER.error(String.format("aSearchHtmlPart failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			if("*:*".equals(searchList.getQuery()) && searchList.getSorts().size() == 0) {
				searchList.addSort("sort1_indexed_double", ORDER.asc);
				searchList.addSort("sort2_indexed_double", ORDER.asc);
				searchList.addSort("sort3_indexed_double", ORDER.asc);
				searchList.addSort("sort4_indexed_double", ORDER.asc);
				searchList.addSort("sort5_indexed_double", ORDER.asc);
				searchList.addSort("sort6_indexed_double", ORDER.asc);
				searchList.addSort("sort7_indexed_double", ORDER.asc);
				searchList.addSort("sort8_indexed_double", ORDER.asc);
				searchList.addSort("sort9_indexed_double", ORDER.asc);
				searchList.addSort("sort10_indexed_double", ORDER.asc);
			}
			searchList.initDeepForClass(siteRequest);
			eventHandler.handle(Future.succeededFuture(searchList));
		} catch(Exception e) {
			LOGGER.error(String.format("aSearchHtmlPart failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void defineHtmlPart(HtmlPart o, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
								LOGGER.error(String.format("defineHtmlPart failed. ", e));
								LOGGER.error(e);
							}
						}
						eventHandler.handle(Future.succeededFuture());
					} catch(Exception e) {
						LOGGER.error(String.format("defineHtmlPart failed. ", e));
						eventHandler.handle(Future.failedFuture(e));
					}
				} else {
					LOGGER.error(String.format("defineHtmlPart failed. ", defineAsync.cause()));
					eventHandler.handle(Future.failedFuture(defineAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("defineHtmlPart failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void attributeHtmlPart(HtmlPart o, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("attributeHtmlPart failed. ", attributeAsync.cause()));
						eventHandler.handle(Future.failedFuture(attributeAsync.cause()));
					}
				} catch(Exception e) {
					LOGGER.error(String.format("attributeHtmlPart failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("attributeHtmlPart failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void indexHtmlPart(HtmlPart o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			o.initDeepForClass(siteRequest);
			o.indexForClass();
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOGGER.error(String.format("indexHtmlPart failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void refreshHtmlPart(HtmlPart o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Boolean refresh = !"false".equals(siteRequest.getRequestVars().get("refresh"));
			if(refresh && BooleanUtils.isFalse(Optional.ofNullable(siteRequest.getApiRequest_()).map(ApiRequest::getEmpty).orElse(true))) {
				SearchList<HtmlPart> searchList = new SearchList<HtmlPart>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(HtmlPart.class);
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
						HtmlPartEnUSApiServiceImpl service = new HtmlPartEnUSApiServiceImpl(siteRequest.getSiteContext_());
						List<Future> futures2 = new ArrayList<>();
						for(HtmlPart o2 : searchList.getList()) {
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForHtmlPart(siteContext, siteRequest.getOperationRequest(), new JsonObject());
							o2.setSiteRequest_(siteRequest2);
							futures2.add(
								service.patchHtmlPartFuture(o2, false, b -> {
									if(b.succeeded()) {
									} else {
										LOGGER.info(String.format("HtmlPart %s failed. ", o2.getPk()));
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
								errorHtmlPart(siteRequest, eventHandler, b);
							}
						});
					} else {
						LOGGER.error("Refresh relations failed. ", a.cause());
						errorHtmlPart(siteRequest, eventHandler, a);
					}
				});
			} else {
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOGGER.error(String.format("refreshHtmlPart failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}
}
