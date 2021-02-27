package com.opendatapolicing.enus.design;

import com.opendatapolicing.enus.design.PageDesignEnUSGenApiServiceImpl;
import com.opendatapolicing.enus.design.PageDesign;
import com.opendatapolicing.enus.design.PageDesignEnUSGenApiServiceImpl;
import com.opendatapolicing.enus.design.PageDesign;
import com.opendatapolicing.enus.html.part.HtmlPartEnUSGenApiServiceImpl;
import com.opendatapolicing.enus.html.part.HtmlPart;
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
import org.apache.commons.lang3.math.NumberUtils;
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
import io.vertx.core.AsyncResult;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.api.OperationResponse;
import io.vertx.core.CompositeFuture;
import org.apache.http.client.utils.URLEncodedUtils;
import java.nio.charset.Charset;
import org.apache.http.NameValuePair;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.ext.auth.oauth2.impl.OAuth2TokenImpl;
import java.util.Optional;
import java.util.stream.Stream;
import java.net.URLDecoder;
import org.apache.solr.util.DateMathParser;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.client.solrj.response.PivotField;
import org.apache.solr.client.solrj.response.RangeFacet;
import java.util.Map.Entry;
import java.util.Iterator;
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
public class PageDesignEnUSGenApiServiceImpl implements PageDesignEnUSGenApiService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(PageDesignEnUSGenApiServiceImpl.class);

	protected static final String SERVICE_ADDRESS = "PageDesignEnUSApiServiceImpl";

	protected SiteContextEnUS siteContext;

	public PageDesignEnUSGenApiServiceImpl(SiteContextEnUS siteContext) {
		this.siteContext = siteContext;
	}

	// PUTImport //

	@Override
	public void putimportPageDesign(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForPageDesign(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/page-design/import");
		siteRequest.setRequestMethod("PUTImport");
		try {
			LOGGER.info(String.format("putimportPageDesign started. "));

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), MultiMap.caseInsensitiveMultiMap()
					)
				));
			} else {

				userPageDesign(siteRequest, b -> {
					if(b.succeeded()) {
						putimportPageDesignResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											ApiRequest apiRequest = new ApiRequest();
											JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
											apiRequest.setRows(jsonArray.size());
											apiRequest.setNumFound(new Integer(jsonArray.size()).longValue());
											apiRequest.setNumPATCH(0L);
											apiRequest.initDeepApiRequest(siteRequest);
											siteRequest.setApiRequest_(apiRequest);
											siteRequest.getVertx().eventBus().publish("websocketPageDesign", JsonObject.mapFrom(apiRequest).toString());
											varsPageDesign(siteRequest, d -> {
												if(d.succeeded()) {
													listPUTImportPageDesign(apiRequest, siteRequest, e -> {
														if(e.succeeded()) {
															putimportPageDesignResponse(siteRequest, f -> {
																if(e.succeeded()) {
																	LOGGER.info(String.format("putimportPageDesign succeeded. "));
																	blockingCodeHandler.handle(Future.succeededFuture(e.result()));
																} else {
																	LOGGER.error(String.format("putimportPageDesign failed. ", f.cause()));
																	errorPageDesign(siteRequest, null, f);
																}
															});
														} else {
															LOGGER.error(String.format("putimportPageDesign failed. ", e.cause()));
															errorPageDesign(siteRequest, null, e);
														}
													});
												} else {
													LOGGER.error(String.format("putimportPageDesign failed. ", d.cause()));
													errorPageDesign(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOGGER.error(String.format("putimportPageDesign failed. ", ex));
											errorPageDesign(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOGGER.error(String.format("putimportPageDesign failed. ", c.cause()));
								errorPageDesign(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("putimportPageDesign failed. ", b.cause()));
						errorPageDesign(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("putimportPageDesign failed. ", ex));
			errorPageDesign(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPUTImportPageDesign(ApiRequest apiRequest, SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				json.put("created", json.getValue("created"));

				SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForPageDesign(siteContext, siteRequest.getOperationRequest(), json);
				siteRequest2.setApiRequest_(apiRequest);
				siteRequest2.setRequestVars(siteRequest.getRequestVars());

				SearchList<PageDesign> searchList = new SearchList<PageDesign>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(PageDesign.class);
				searchList.addFilterQuery("deleted_indexed_boolean:false");
				searchList.addFilterQuery("archived_indexed_boolean:false");
				searchList.addFilterQuery("inheritPk_indexed_long:" + json.getString("pk"));
				searchList.initDeepForClass(siteRequest2);

				if(searchList.size() == 1) {
					PageDesign o = searchList.getList().stream().findFirst().orElse(null);
					JsonObject json2 = new JsonObject();
					for(String f : json.fieldNames()) {
						json2.put("set" + StringUtils.capitalize(f), json.getValue(f));
					}
					if(o != null) {
						for(String f : Optional.ofNullable(o.getSaves()).orElse(new ArrayList<>())) {
							if(!json.fieldNames().contains(f))
								json2.putNull("set" + StringUtils.capitalize(f));
						}
						siteRequest2.setJsonObject(json2);
						futures.add(
							patchPageDesignFuture(o, true, a -> {
								if(a.succeeded()) {
								} else {
									LOGGER.error(String.format("listPUTImportPageDesign failed. ", a.cause()));
									errorPageDesign(siteRequest2, eventHandler, a);
								}
							})
						);
					}
				} else {
					futures.add(
						postPageDesignFuture(siteRequest2, true, a -> {
							if(a.succeeded()) {
							} else {
								LOGGER.error(String.format("listPUTImportPageDesign failed. ", a.cause()));
								errorPageDesign(siteRequest2, eventHandler, a);
							}
						})
					);
				}
			});
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
					response200PUTImportPageDesign(siteRequest, eventHandler);
				} else {
					LOGGER.error(String.format("listPUTImportPageDesign failed. ", a.cause()));
					errorPageDesign(apiRequest.getSiteRequest_(), eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("listPUTImportPageDesign failed. ", ex));
			errorPageDesign(siteRequest, null, Future.failedFuture(ex));
		}
	}

	public void putimportPageDesignResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTImportPageDesign(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putimportPageDesignResponse failed. ", a.cause()));
					errorPageDesign(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putimportPageDesignResponse failed. ", ex));
			errorPageDesign(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTImportPageDesign(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTImportPageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTMerge //

	@Override
	public void putmergePageDesign(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForPageDesign(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/page-design/merge");
		siteRequest.setRequestMethod("PUTMerge");
		try {
			LOGGER.info(String.format("putmergePageDesign started. "));

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), MultiMap.caseInsensitiveMultiMap()
					)
				));
			} else {

				userPageDesign(siteRequest, b -> {
					if(b.succeeded()) {
						putmergePageDesignResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											ApiRequest apiRequest = new ApiRequest();
											JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
											apiRequest.setRows(jsonArray.size());
											apiRequest.setNumFound(new Integer(jsonArray.size()).longValue());
											apiRequest.setNumPATCH(0L);
											apiRequest.initDeepApiRequest(siteRequest);
											siteRequest.setApiRequest_(apiRequest);
											siteRequest.getVertx().eventBus().publish("websocketPageDesign", JsonObject.mapFrom(apiRequest).toString());
											varsPageDesign(siteRequest, d -> {
												if(d.succeeded()) {
													listPUTMergePageDesign(apiRequest, siteRequest, e -> {
														if(e.succeeded()) {
															putmergePageDesignResponse(siteRequest, f -> {
																if(e.succeeded()) {
																	LOGGER.info(String.format("putmergePageDesign succeeded. "));
																	blockingCodeHandler.handle(Future.succeededFuture(e.result()));
																} else {
																	LOGGER.error(String.format("putmergePageDesign failed. ", f.cause()));
																	errorPageDesign(siteRequest, null, f);
																}
															});
														} else {
															LOGGER.error(String.format("putmergePageDesign failed. ", e.cause()));
															errorPageDesign(siteRequest, null, e);
														}
													});
												} else {
													LOGGER.error(String.format("putmergePageDesign failed. ", d.cause()));
													errorPageDesign(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOGGER.error(String.format("putmergePageDesign failed. ", ex));
											errorPageDesign(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOGGER.error(String.format("putmergePageDesign failed. ", c.cause()));
								errorPageDesign(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("putmergePageDesign failed. ", b.cause()));
						errorPageDesign(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("putmergePageDesign failed. ", ex));
			errorPageDesign(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPUTMergePageDesign(ApiRequest apiRequest, SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForPageDesign(siteContext, siteRequest.getOperationRequest(), json);
				siteRequest2.setApiRequest_(apiRequest);
				siteRequest2.setRequestVars(siteRequest.getRequestVars());

				SearchList<PageDesign> searchList = new SearchList<PageDesign>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(PageDesign.class);
				searchList.addFilterQuery("deleted_indexed_boolean:false");
				searchList.addFilterQuery("archived_indexed_boolean:false");
				searchList.addFilterQuery("pk_indexed_long:" + json.getString("pk"));
				searchList.initDeepForClass(siteRequest2);

				if(searchList.size() == 1) {
					PageDesign o = searchList.getList().stream().findFirst().orElse(null);
					JsonObject json2 = new JsonObject();
					for(String f : json.fieldNames()) {
						json2.put("set" + StringUtils.capitalize(f), json.getValue(f));
					}
					if(o != null) {
						for(String f : Optional.ofNullable(o.getSaves()).orElse(new ArrayList<>())) {
							if(!json.fieldNames().contains(f))
								json2.putNull("set" + StringUtils.capitalize(f));
						}
						siteRequest2.setJsonObject(json2);
						futures.add(
							patchPageDesignFuture(o, false, a -> {
								if(a.succeeded()) {
								} else {
									LOGGER.error(String.format("listPUTMergePageDesign failed. ", a.cause()));
									errorPageDesign(siteRequest2, eventHandler, a);
								}
							})
						);
					}
				} else {
					futures.add(
						postPageDesignFuture(siteRequest2, false, a -> {
							if(a.succeeded()) {
							} else {
								LOGGER.error(String.format("listPUTMergePageDesign failed. ", a.cause()));
								errorPageDesign(siteRequest2, eventHandler, a);
							}
						})
					);
				}
			});
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
					response200PUTMergePageDesign(siteRequest, eventHandler);
				} else {
					LOGGER.error(String.format("listPUTMergePageDesign failed. ", a.cause()));
					errorPageDesign(apiRequest.getSiteRequest_(), eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("listPUTMergePageDesign failed. ", ex));
			errorPageDesign(siteRequest, null, Future.failedFuture(ex));
		}
	}

	public void putmergePageDesignResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTMergePageDesign(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putmergePageDesignResponse failed. ", a.cause()));
					errorPageDesign(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putmergePageDesignResponse failed. ", ex));
			errorPageDesign(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTMergePageDesign(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTMergePageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTCopy //

	@Override
	public void putcopyPageDesign(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForPageDesign(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/page-design/copy");
		siteRequest.setRequestMethod("PUTCopy");
		try {
			LOGGER.info(String.format("putcopyPageDesign started. "));

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), MultiMap.caseInsensitiveMultiMap()
					)
				));
			} else {

				userPageDesign(siteRequest, b -> {
					if(b.succeeded()) {
						putcopyPageDesignResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											aSearchPageDesign(siteRequest, false, true, true, "/api/page-design/copy", "PUTCopy", d -> {
												if(d.succeeded()) {
													SearchList<PageDesign> listPageDesign = d.result();
													ApiRequest apiRequest = new ApiRequest();
													apiRequest.setRows(listPageDesign.getRows());
													apiRequest.setNumFound(listPageDesign.getQueryResponse().getResults().getNumFound());
													apiRequest.setNumPATCH(0L);
													apiRequest.initDeepApiRequest(siteRequest);
													siteRequest.setApiRequest_(apiRequest);
													siteRequest.getVertx().eventBus().publish("websocketPageDesign", JsonObject.mapFrom(apiRequest).toString());
													try {
														listPUTCopyPageDesign(apiRequest, listPageDesign, e -> {
															if(e.succeeded()) {
																putcopyPageDesignResponse(siteRequest, f -> {
																	if(f.succeeded()) {
																		LOGGER.info(String.format("putcopyPageDesign succeeded. "));
																		blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																	} else {
																		LOGGER.error(String.format("putcopyPageDesign failed. ", f.cause()));
																		errorPageDesign(siteRequest, null, f);
																	}
																});
															} else {
																LOGGER.error(String.format("putcopyPageDesign failed. ", e.cause()));
																errorPageDesign(siteRequest, null, e);
															}
														});
													} catch(Exception ex) {
														LOGGER.error(String.format("putcopyPageDesign failed. ", ex));
														errorPageDesign(siteRequest, null, Future.failedFuture(ex));
													}
												} else {
													LOGGER.error(String.format("putcopyPageDesign failed. ", d.cause()));
													errorPageDesign(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOGGER.error(String.format("putcopyPageDesign failed. ", ex));
											errorPageDesign(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOGGER.error(String.format("putcopyPageDesign failed. ", c.cause()));
								errorPageDesign(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("putcopyPageDesign failed. ", b.cause()));
						errorPageDesign(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopyPageDesign failed. ", ex));
			errorPageDesign(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPUTCopyPageDesign(ApiRequest apiRequest, SearchList<PageDesign> listPageDesign, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
		listPageDesign.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForPageDesign(siteContext, siteRequest.getOperationRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				putcopyPageDesignFuture(siteRequest2, JsonObject.mapFrom(o), a -> {
					if(a.succeeded()) {
					} else {
						LOGGER.error(String.format("listPUTCopyPageDesign failed. ", a.cause()));
						errorPageDesign(siteRequest, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).onComplete( a -> {
			if(a.succeeded()) {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + listPageDesign.size());
				if(listPageDesign.next()) {
					listPUTCopyPageDesign(apiRequest, listPageDesign, eventHandler);
				} else {
					response200PUTCopyPageDesign(siteRequest, eventHandler);
				}
			} else {
				LOGGER.error(String.format("listPUTCopyPageDesign failed. ", a.cause()));
				errorPageDesign(listPageDesign.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<PageDesign> putcopyPageDesignFuture(SiteRequestEnUS siteRequest, JsonObject jsonObject, Handler<AsyncResult<PageDesign>> eventHandler) {
		Promise<PageDesign> promise = Promise.promise();
		try {

			jsonObject.put("saves", Optional.ofNullable(jsonObject.getJsonArray("saves")).orElse(new JsonArray()));
			JsonObject jsonPatch = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonObject("patch")).orElse(new JsonObject());
			jsonPatch.stream().forEach(o -> {
				if(o.getValue() == null)
					jsonObject.remove(o.getKey());
				else
					jsonObject.put(o.getKey(), o.getValue());
				jsonObject.getJsonArray("saves").add(o.getKey());
			});

			sqlConnectionPageDesign(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionPageDesign(siteRequest, b -> {
						if(b.succeeded()) {
							createPageDesign(siteRequest, c -> {
								if(c.succeeded()) {
									PageDesign pageDesign = c.result();
									sqlPUTCopyPageDesign(pageDesign, jsonObject, d -> {
										if(d.succeeded()) {
											defineIndexPageDesign(pageDesign, e -> {
												if(e.succeeded()) {
													ApiRequest apiRequest = siteRequest.getApiRequest_();
													if(apiRequest != null) {
														apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
														if(apiRequest.getNumFound() == 1L) {
															pageDesign.apiRequestPageDesign();
														}
														siteRequest.getVertx().eventBus().publish("websocketPageDesign", JsonObject.mapFrom(apiRequest).toString());
													}
													eventHandler.handle(Future.succeededFuture(pageDesign));
													promise.complete(pageDesign);
												} else {
													LOGGER.error(String.format("putcopyPageDesignFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOGGER.error(String.format("putcopyPageDesignFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("putcopyPageDesignFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("putcopyPageDesignFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("putcopyPageDesignFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("putcopyPageDesignFuture failed. ", e));
			errorPageDesign(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPUTCopyPageDesign(PageDesign o, JsonObject jsonObject, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Transaction tx = siteRequest.getTx();
			Integer num = 1;
			Long pk = o.getPk();
			List<Future> futures = new ArrayList<>();

			if(jsonObject != null) {
				JsonArray entityVars = jsonObject.getJsonArray("saves");
				for(Integer i = 0; i < entityVars.size(); i++) {
					String entityVar = entityVars.getString(i);
					switch(entityVar) {
					case "inheritPk":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "inheritPk", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value PageDesign.inheritPk failed", b.cause())));
							});
						}));
						break;
					case "archived":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "archived", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value PageDesign.archived failed", b.cause())));
							});
						}));
						break;
					case "deleted":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "deleted", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value PageDesign.deleted failed", b.cause())));
							});
						}));
						break;
					case "childDesignKeys":
						for(Long l : Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray()).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_addA)
										.execute(Tuple.of(pk, "childDesignKeys", l, "parentDesignKeys")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value PageDesign.childDesignKeys failed", b.cause())));
								});
							}));
							if(!pks.contains(l)) {
								pks.add(l);
								classes.add("PageDesign");
							}
						}
						break;
					case "parentDesignKeys":
						for(Long l : Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray()).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_addA)
										.execute(Tuple.of(l, "childDesignKeys", pk, "parentDesignKeys")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value PageDesign.parentDesignKeys failed", b.cause())));
								});
							}));
							if(!pks.contains(l)) {
								pks.add(l);
								classes.add("PageDesign");
							}
						}
						break;
					case "htmlPartKeys":
						for(Long l : Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray()).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_addA)
										.execute(Tuple.of(pk, "htmlPartKeys", l, "pageDesignKeys")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value PageDesign.htmlPartKeys failed", b.cause())));
								});
							}));
							if(!pks.contains(l)) {
								pks.add(l);
								classes.add("HtmlPart");
							}
						}
						break;
					case "pageDesignCompleteName":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "pageDesignCompleteName", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value PageDesign.pageDesignCompleteName failed", b.cause())));
							});
						}));
						break;
					case "designHidden":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "designHidden", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value PageDesign.designHidden failed", b.cause())));
							});
						}));
						break;
					case "pageContentType":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "pageContentType", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value PageDesign.pageContentType failed", b.cause())));
							});
						}));
						break;
					}
				}
			}
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture());
				} else {
					LOGGER.error(String.format("sqlPUTCopyPageDesign failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPUTCopyPageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void putcopyPageDesignResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTCopyPageDesign(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putcopyPageDesignResponse failed. ", a.cause()));
					errorPageDesign(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopyPageDesignResponse failed. ", ex));
			errorPageDesign(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTCopyPageDesign(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTCopyPageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// POST //

	@Override
	public void postPageDesign(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForPageDesign(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/page-design");
		siteRequest.setRequestMethod("POST");
		try {
			LOGGER.info(String.format("postPageDesign started. "));

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), MultiMap.caseInsensitiveMultiMap()
					)
				));
			} else {

				userPageDesign(siteRequest, b -> {
					if(b.succeeded()) {
						ApiRequest apiRequest = new ApiRequest();
						apiRequest.setRows(1);
						apiRequest.setNumFound(1L);
						apiRequest.setNumPATCH(0L);
						apiRequest.initDeepApiRequest(siteRequest);
						siteRequest.setApiRequest_(apiRequest);
						siteRequest.getVertx().eventBus().publish("websocketPageDesign", JsonObject.mapFrom(apiRequest).toString());
						postPageDesignFuture(siteRequest, false, c -> {
							if(c.succeeded()) {
								PageDesign pageDesign = c.result();
								apiRequest.setPk(pageDesign.getPk());
								postPageDesignResponse(pageDesign, d -> {
										if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("postPageDesign succeeded. "));
									} else {
										LOGGER.error(String.format("postPageDesign failed. ", d.cause()));
										errorPageDesign(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("postPageDesign failed. ", c.cause()));
								errorPageDesign(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("postPageDesign failed. ", b.cause()));
						errorPageDesign(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("postPageDesign failed. ", ex));
			errorPageDesign(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public Future<PageDesign> postPageDesignFuture(SiteRequestEnUS siteRequest, Boolean inheritPk, Handler<AsyncResult<PageDesign>> eventHandler) {
		Promise<PageDesign> promise = Promise.promise();
		try {
			sqlConnectionPageDesign(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionPageDesign(siteRequest, b -> {
						if(b.succeeded()) {
							createPageDesign(siteRequest, c -> {
								if(c.succeeded()) {
									PageDesign pageDesign = c.result();
									sqlPOSTPageDesign(pageDesign, inheritPk, d -> {
										if(d.succeeded()) {
											defineIndexPageDesign(pageDesign, e -> {
												if(e.succeeded()) {
													ApiRequest apiRequest = siteRequest.getApiRequest_();
													if(apiRequest != null) {
														apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
														pageDesign.apiRequestPageDesign();
														siteRequest.getVertx().eventBus().publish("websocketPageDesign", JsonObject.mapFrom(apiRequest).toString());
													}
													eventHandler.handle(Future.succeededFuture(pageDesign));
													promise.complete(pageDesign);
												} else {
													LOGGER.error(String.format("postPageDesignFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOGGER.error(String.format("postPageDesignFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("postPageDesignFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("postPageDesignFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("postPageDesignFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("postPageDesignFuture failed. ", e));
			errorPageDesign(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPOSTPageDesign(PageDesign o, Boolean inheritPk, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Transaction tx = siteRequest.getTx();
			Integer num = 1;
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			PageDesign o2 = new PageDesign();
			o2.setSiteRequest_(siteRequest);
			List<Future> futures = new ArrayList<>();

			if(siteRequest.getSessionId() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContextEnUS.SQL_setD)
				.execute(Tuple.of(pk, "sessionId", siteRequest.getSessionId())
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
					tx.preparedQuery(SiteContextEnUS.SQL_setD)
				.execute(Tuple.of(pk, "userId", siteRequest.getUserId())
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
					tx.preparedQuery(SiteContextEnUS.SQL_setD)
				.execute(Tuple.of(pk, "userKey", siteRequest.getUserKey().toString())
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
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "inheritPk", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value PageDesign.inheritPk failed", b.cause())));
							});
						}));
						break;
					case "archived":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "archived", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value PageDesign.archived failed", b.cause())));
							});
						}));
						break;
					case "deleted":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "deleted", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value PageDesign.deleted failed", b.cause())));
							});
						}));
						break;
					case "childDesignKeys":
						for(Long l : Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray()).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							if(l != null) {
								SearchList<com.opendatapolicing.enus.design.PageDesign> searchList = new SearchList<com.opendatapolicing.enus.design.PageDesign>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(com.opendatapolicing.enus.design.PageDesign.class);
								searchList.addFilterQuery("deleted_indexed_boolean:false");
								searchList.addFilterQuery("archived_indexed_boolean:false");
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA)
												.execute(Tuple.of(pk, "childDesignKeys", l2, "parentDesignKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value PageDesign.childDesignKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("PageDesign");
									}
								}
							}
						}
						break;
					case "parentDesignKeys":
						for(Long l : Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray()).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							if(l != null) {
								SearchList<com.opendatapolicing.enus.design.PageDesign> searchList = new SearchList<com.opendatapolicing.enus.design.PageDesign>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(com.opendatapolicing.enus.design.PageDesign.class);
								searchList.addFilterQuery("deleted_indexed_boolean:false");
								searchList.addFilterQuery("archived_indexed_boolean:false");
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA)
												.execute(Tuple.of(l2, "childDesignKeys", pk, "parentDesignKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value PageDesign.parentDesignKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("PageDesign");
									}
								}
							}
						}
						break;
					case "htmlPartKeys":
						for(Long l : Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray()).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							if(l != null) {
								SearchList<com.opendatapolicing.enus.html.part.HtmlPart> searchList = new SearchList<com.opendatapolicing.enus.html.part.HtmlPart>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(com.opendatapolicing.enus.html.part.HtmlPart.class);
								searchList.addFilterQuery("deleted_indexed_boolean:false");
								searchList.addFilterQuery("archived_indexed_boolean:false");
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA)
												.execute(Tuple.of(pk, "htmlPartKeys", l2, "pageDesignKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value PageDesign.htmlPartKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("HtmlPart");
									}
								}
							}
						}
						break;
					case "pageDesignCompleteName":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "pageDesignCompleteName", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value PageDesign.pageDesignCompleteName failed", b.cause())));
							});
						}));
						break;
					case "designHidden":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "designHidden", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value PageDesign.designHidden failed", b.cause())));
							});
						}));
						break;
					case "pageContentType":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "pageContentType", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value PageDesign.pageContentType failed", b.cause())));
							});
						}));
						break;
					}
				}
			}
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture());
				} else {
					LOGGER.error(String.format("sqlPOSTPageDesign failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPOSTPageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void postPageDesignResponse(PageDesign pageDesign, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = pageDesign.getSiteRequest_();
		try {
			response200POSTPageDesign(pageDesign, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("postPageDesignResponse failed. ", a.cause()));
					errorPageDesign(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("postPageDesignResponse failed. ", ex));
			errorPageDesign(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200POSTPageDesign(PageDesign o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200POSTPageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchPageDesign(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForPageDesign(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/page-design");
		siteRequest.setRequestMethod("PATCH");
		try {
			LOGGER.info(String.format("patchPageDesign started. "));

			List<String> roles = Arrays.asList("SiteAdmin");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), MultiMap.caseInsensitiveMultiMap()
					)
				));
			} else {

				userPageDesign(siteRequest, b -> {
					if(b.succeeded()) {
						patchPageDesignResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											aSearchPageDesign(siteRequest, false, true, true, "/api/page-design", "PATCH", d -> {
												if(d.succeeded()) {
													SearchList<PageDesign> listPageDesign = d.result();

													List<String> roles2 = Arrays.asList("SiteAdmin");
													if(listPageDesign.getQueryResponse().getResults().getNumFound() > 1
															&& !CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles2)
															&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles2)
															) {
														String message = String.format("roles required: " + String.join(", ", roles2));
														LOGGER.error(message);
														errorPageDesign(siteRequest, eventHandler, Future.failedFuture(message));
													} else {

														ApiRequest apiRequest = new ApiRequest();
														apiRequest.setRows(listPageDesign.getRows());
														apiRequest.setNumFound(listPageDesign.getQueryResponse().getResults().getNumFound());
														apiRequest.setNumPATCH(0L);
														apiRequest.initDeepApiRequest(siteRequest);
														siteRequest.setApiRequest_(apiRequest);
														siteRequest.getVertx().eventBus().publish("websocketPageDesign", JsonObject.mapFrom(apiRequest).toString());
														SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listPageDesign.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(null);
														Date date = null;
														if(facets != null)
														date = (Date)facets.get("max_modified");
														String dt;
														if(date == null)
															dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));
														else
															dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")));
														listPageDesign.addFilterQuery(String.format("modified_indexed_date:[* TO %s]", dt));

														try {
															listPATCHPageDesign(apiRequest, listPageDesign, dt, e -> {
																if(e.succeeded()) {
																	patchPageDesignResponse(siteRequest, f -> {
																		if(f.succeeded()) {
																			LOGGER.info(String.format("patchPageDesign succeeded. "));
																			blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																		} else {
																			LOGGER.error(String.format("patchPageDesign failed. ", f.cause()));
																			errorPageDesign(siteRequest, null, f);
																		}
																	});
																} else {
																	LOGGER.error(String.format("patchPageDesign failed. ", e.cause()));
																	errorPageDesign(siteRequest, null, e);
																}
															});
														} catch(Exception ex) {
															LOGGER.error(String.format("patchPageDesign failed. ", ex));
															errorPageDesign(siteRequest, null, Future.failedFuture(ex));
														}
													}
										} else {
													LOGGER.error(String.format("patchPageDesign failed. ", d.cause()));
													errorPageDesign(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOGGER.error(String.format("patchPageDesign failed. ", ex));
											errorPageDesign(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOGGER.error(String.format("patchPageDesign failed. ", c.cause()));
								errorPageDesign(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("patchPageDesign failed. ", b.cause()));
						errorPageDesign(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("patchPageDesign failed. ", ex));
			errorPageDesign(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPATCHPageDesign(ApiRequest apiRequest, SearchList<PageDesign> listPageDesign, String dt, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
		listPageDesign.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForPageDesign(siteContext, siteRequest.getOperationRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				patchPageDesignFuture(o, false, a -> {
					if(a.succeeded()) {
					} else {
						errorPageDesign(siteRequest2, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).onComplete( a -> {
			if(a.succeeded()) {
				if(listPageDesign.next(dt)) {
					listPATCHPageDesign(apiRequest, listPageDesign, dt, eventHandler);
				} else {
					response200PATCHPageDesign(siteRequest, eventHandler);
				}
			} else {
				LOGGER.error(String.format("listPATCHPageDesign failed. ", a.cause()));
				errorPageDesign(listPageDesign.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<PageDesign> patchPageDesignFuture(PageDesign o, Boolean inheritPk, Handler<AsyncResult<PageDesign>> eventHandler) {
		Promise<PageDesign> promise = Promise.promise();
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			if(apiRequest != null && apiRequest.getNumFound() == 1L) {
				apiRequest.setOriginal(o);
				apiRequest.setPk(o.getPk());
			}
			sqlConnectionPageDesign(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionPageDesign(siteRequest, b -> {
						if(b.succeeded()) {
							sqlPATCHPageDesign(o, inheritPk, c -> {
								if(c.succeeded()) {
									PageDesign pageDesign = c.result();
									defineIndexPageDesign(pageDesign, d -> {
										if(d.succeeded()) {
											if(apiRequest != null) {
												apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
												if(apiRequest.getNumFound() == 1L) {
													pageDesign.apiRequestPageDesign();
												}
												siteRequest.getVertx().eventBus().publish("websocketPageDesign", JsonObject.mapFrom(apiRequest).toString());
											}
											eventHandler.handle(Future.succeededFuture(pageDesign));
											promise.complete(pageDesign);
										} else {
											LOGGER.error(String.format("patchPageDesignFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("patchPageDesignFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("patchPageDesignFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("patchPageDesignFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("patchPageDesignFuture failed. ", e));
			errorPageDesign(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPATCHPageDesign(PageDesign o, Boolean inheritPk, Handler<AsyncResult<PageDesign>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Transaction tx = siteRequest.getTx();
			Integer num = 1;
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			PageDesign o2 = new PageDesign();
			o2.setSiteRequest_(siteRequest);
			List<Future> futures = new ArrayList<>();

			if(o.getUserId() == null && siteRequest.getUserId() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContextEnUS.SQL_setD)
							.execute(Tuple.of(pk, "userId", siteRequest.getUserId())
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
					tx.preparedQuery(SiteContextEnUS.SQL_setD)
				.execute(Tuple.of(pk, "userKey", siteRequest.getUserKey().toString())
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
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "inheritPk")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value PageDesign.inheritPk failed", b.cause())));
								});
							}));
						} else {
							o2.setInheritPk(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "inheritPk", o2.jsonInheritPk())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value PageDesign.inheritPk failed", b.cause())));
								});
							}));
						}
						break;
					case "setArchived":
						if(jsonObject.getBoolean(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "archived")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value PageDesign.archived failed", b.cause())));
								});
							}));
						} else {
							o2.setArchived(jsonObject.getBoolean(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "archived", o2.jsonArchived())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value PageDesign.archived failed", b.cause())));
								});
							}));
						}
						break;
					case "setDeleted":
						if(jsonObject.getBoolean(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "deleted")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value PageDesign.deleted failed", b.cause())));
								});
							}));
						} else {
							o2.setDeleted(jsonObject.getBoolean(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "deleted", o2.jsonDeleted())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value PageDesign.deleted failed", b.cause())));
								});
							}));
						}
						break;
					case "addChildDesignKeys":
						{
							Long l = Long.parseLong(jsonObject.getString(methodName));
							if(l != null) {
								SearchList<com.opendatapolicing.enus.design.PageDesign> searchList = new SearchList<com.opendatapolicing.enus.design.PageDesign>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(com.opendatapolicing.enus.design.PageDesign.class);
								searchList.addFilterQuery("deleted_indexed_boolean:false");
								searchList.addFilterQuery("archived_indexed_boolean:false");
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && !o.getChildDesignKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA)
												.execute(Tuple.of(pk, "childDesignKeys", l2, "parentDesignKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value PageDesign.childDesignKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("PageDesign");
									}
								}
							}
						}
						break;
					case "addAllChildDesignKeys":
						JsonArray addAllChildDesignKeysValues = jsonObject.getJsonArray(methodName);
						if(addAllChildDesignKeysValues != null) {
							for(Integer i = 0; i <  addAllChildDesignKeysValues.size(); i++) {
								Long l = Long.parseLong(addAllChildDesignKeysValues.getString(i));
								if(l != null) {
									SearchList<com.opendatapolicing.enus.design.PageDesign> searchList = new SearchList<com.opendatapolicing.enus.design.PageDesign>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(com.opendatapolicing.enus.design.PageDesign.class);
									searchList.addFilterQuery("deleted_indexed_boolean:false");
									searchList.addFilterQuery("archived_indexed_boolean:false");
									searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null && !o.getChildDesignKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA)
												.execute(Tuple.of(pk, "childDesignKeys", l2, "parentDesignKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value PageDesign.childDesignKeys failed", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("PageDesign");
										}
									}
								}
							}
						}
						break;
					case "setChildDesignKeys":
						JsonArray setChildDesignKeysValues = jsonObject.getJsonArray(methodName);
						JsonArray setChildDesignKeysValues2 = new JsonArray();
						if(setChildDesignKeysValues != null) {
							for(Integer i = 0; i <  setChildDesignKeysValues.size(); i++) {
								Long l = Long.parseLong(setChildDesignKeysValues.getString(i));
								if(l != null) {
									SearchList<com.opendatapolicing.enus.design.PageDesign> searchList = new SearchList<com.opendatapolicing.enus.design.PageDesign>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(com.opendatapolicing.enus.design.PageDesign.class);
									searchList.addFilterQuery("deleted_indexed_boolean:false");
									searchList.addFilterQuery("archived_indexed_boolean:false");
									searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null)
										setChildDesignKeysValues2.add(l2);
									if(l2 != null && !o.getChildDesignKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA)
												.execute(Tuple.of(pk, "childDesignKeys", l2, "parentDesignKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value PageDesign.childDesignKeys failed", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("PageDesign");
										}
									}
								}
							}
						}
						if(o.getChildDesignKeys() != null) {
							for(Long l :  o.getChildDesignKeys()) {
								if(l != null && (setChildDesignKeysValues2 == null || !setChildDesignKeysValues2.contains(l))) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_removeA)
												.execute(Tuple.of(pk, "childDesignKeys", l, "parentDesignKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value PageDesign.childDesignKeys failed", b.cause())));
										});
									}));
								}
							}
						}
						break;
					case "removeChildDesignKeys":
						{
							Long l = Long.parseLong(jsonObject.getString(methodName));
							if(l != null) {
								SearchList<com.opendatapolicing.enus.design.PageDesign> searchList = new SearchList<com.opendatapolicing.enus.design.PageDesign>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(com.opendatapolicing.enus.design.PageDesign.class);
								searchList.addFilterQuery("deleted_indexed_boolean:false");
								searchList.addFilterQuery("archived_indexed_boolean:false");
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && o.getChildDesignKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_removeA)
												.execute(Tuple.of(pk, "childDesignKeys", l2, "parentDesignKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value PageDesign.childDesignKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("PageDesign");
									}
								}
							}
						}
						break;
					case "addParentDesignKeys":
						{
							Long l = Long.parseLong(jsonObject.getString(methodName));
							if(l != null) {
								SearchList<com.opendatapolicing.enus.design.PageDesign> searchList = new SearchList<com.opendatapolicing.enus.design.PageDesign>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(com.opendatapolicing.enus.design.PageDesign.class);
								searchList.addFilterQuery("deleted_indexed_boolean:false");
								searchList.addFilterQuery("archived_indexed_boolean:false");
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && !o.getParentDesignKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA)
												.execute(Tuple.of(l2, "childDesignKeys", pk, "parentDesignKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value PageDesign.parentDesignKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("PageDesign");
									}
								}
							}
						}
						break;
					case "addAllParentDesignKeys":
						JsonArray addAllParentDesignKeysValues = jsonObject.getJsonArray(methodName);
						if(addAllParentDesignKeysValues != null) {
							for(Integer i = 0; i <  addAllParentDesignKeysValues.size(); i++) {
								Long l = Long.parseLong(addAllParentDesignKeysValues.getString(i));
								if(l != null) {
									SearchList<com.opendatapolicing.enus.design.PageDesign> searchList = new SearchList<com.opendatapolicing.enus.design.PageDesign>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(com.opendatapolicing.enus.design.PageDesign.class);
									searchList.addFilterQuery("deleted_indexed_boolean:false");
									searchList.addFilterQuery("archived_indexed_boolean:false");
									searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null && !o.getParentDesignKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA)
												.execute(Tuple.of(l2, "childDesignKeys", pk, "parentDesignKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value PageDesign.parentDesignKeys failed", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("PageDesign");
										}
									}
								}
							}
						}
						break;
					case "setParentDesignKeys":
						JsonArray setParentDesignKeysValues = jsonObject.getJsonArray(methodName);
						JsonArray setParentDesignKeysValues2 = new JsonArray();
						if(setParentDesignKeysValues != null) {
							for(Integer i = 0; i <  setParentDesignKeysValues.size(); i++) {
								Long l = Long.parseLong(setParentDesignKeysValues.getString(i));
								if(l != null) {
									SearchList<com.opendatapolicing.enus.design.PageDesign> searchList = new SearchList<com.opendatapolicing.enus.design.PageDesign>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(com.opendatapolicing.enus.design.PageDesign.class);
									searchList.addFilterQuery("deleted_indexed_boolean:false");
									searchList.addFilterQuery("archived_indexed_boolean:false");
									searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null)
										setParentDesignKeysValues2.add(l2);
									if(l2 != null && !o.getParentDesignKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA)
												.execute(Tuple.of(l2, "childDesignKeys", pk, "parentDesignKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value PageDesign.parentDesignKeys failed", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("PageDesign");
										}
									}
								}
							}
						}
						if(o.getParentDesignKeys() != null) {
							for(Long l :  o.getParentDesignKeys()) {
								if(l != null && (setParentDesignKeysValues == null || !setParentDesignKeysValues2.contains(l))) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_removeA)
												.execute(Tuple.of(l, "childDesignKeys", pk, "parentDesignKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value PageDesign.parentDesignKeys failed", b.cause())));
										});
									}));
								}
							}
						}
						break;
					case "removeParentDesignKeys":
						{
							Long l = Long.parseLong(jsonObject.getString(methodName));
							if(l != null) {
								SearchList<com.opendatapolicing.enus.design.PageDesign> searchList = new SearchList<com.opendatapolicing.enus.design.PageDesign>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(com.opendatapolicing.enus.design.PageDesign.class);
								searchList.addFilterQuery("deleted_indexed_boolean:false");
								searchList.addFilterQuery("archived_indexed_boolean:false");
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && o.getParentDesignKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_removeA)
												.execute(Tuple.of(l2, "childDesignKeys", pk, "parentDesignKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value PageDesign.parentDesignKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("PageDesign");
									}
								}
							}
						}
						break;
					case "addHtmlPartKeys":
						{
							Long l = Long.parseLong(jsonObject.getString(methodName));
							if(l != null) {
								SearchList<com.opendatapolicing.enus.html.part.HtmlPart> searchList = new SearchList<com.opendatapolicing.enus.html.part.HtmlPart>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(com.opendatapolicing.enus.html.part.HtmlPart.class);
								searchList.addFilterQuery("deleted_indexed_boolean:false");
								searchList.addFilterQuery("archived_indexed_boolean:false");
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && !o.getHtmlPartKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA)
												.execute(Tuple.of(pk, "htmlPartKeys", l2, "pageDesignKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value PageDesign.htmlPartKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("HtmlPart");
									}
								}
							}
						}
						break;
					case "addAllHtmlPartKeys":
						JsonArray addAllHtmlPartKeysValues = jsonObject.getJsonArray(methodName);
						if(addAllHtmlPartKeysValues != null) {
							for(Integer i = 0; i <  addAllHtmlPartKeysValues.size(); i++) {
								Long l = Long.parseLong(addAllHtmlPartKeysValues.getString(i));
								if(l != null) {
									SearchList<com.opendatapolicing.enus.html.part.HtmlPart> searchList = new SearchList<com.opendatapolicing.enus.html.part.HtmlPart>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(com.opendatapolicing.enus.html.part.HtmlPart.class);
									searchList.addFilterQuery("deleted_indexed_boolean:false");
									searchList.addFilterQuery("archived_indexed_boolean:false");
									searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null && !o.getHtmlPartKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA)
												.execute(Tuple.of(pk, "htmlPartKeys", l2, "pageDesignKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value PageDesign.htmlPartKeys failed", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("HtmlPart");
										}
									}
								}
							}
						}
						break;
					case "setHtmlPartKeys":
						JsonArray setHtmlPartKeysValues = jsonObject.getJsonArray(methodName);
						JsonArray setHtmlPartKeysValues2 = new JsonArray();
						if(setHtmlPartKeysValues != null) {
							for(Integer i = 0; i <  setHtmlPartKeysValues.size(); i++) {
								Long l = Long.parseLong(setHtmlPartKeysValues.getString(i));
								if(l != null) {
									SearchList<com.opendatapolicing.enus.html.part.HtmlPart> searchList = new SearchList<com.opendatapolicing.enus.html.part.HtmlPart>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(com.opendatapolicing.enus.html.part.HtmlPart.class);
									searchList.addFilterQuery("deleted_indexed_boolean:false");
									searchList.addFilterQuery("archived_indexed_boolean:false");
									searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null)
										setHtmlPartKeysValues2.add(l2);
									if(l2 != null && !o.getHtmlPartKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA)
												.execute(Tuple.of(pk, "htmlPartKeys", l2, "pageDesignKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value PageDesign.htmlPartKeys failed", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("HtmlPart");
										}
									}
								}
							}
						}
						if(o.getHtmlPartKeys() != null) {
							for(Long l :  o.getHtmlPartKeys()) {
								if(l != null && (setHtmlPartKeysValues2 == null || !setHtmlPartKeysValues2.contains(l))) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_removeA)
												.execute(Tuple.of(pk, "htmlPartKeys", l, "pageDesignKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value PageDesign.htmlPartKeys failed", b.cause())));
										});
									}));
								}
							}
						}
						break;
					case "removeHtmlPartKeys":
						{
							Long l = Long.parseLong(jsonObject.getString(methodName));
							if(l != null) {
								SearchList<com.opendatapolicing.enus.html.part.HtmlPart> searchList = new SearchList<com.opendatapolicing.enus.html.part.HtmlPart>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(com.opendatapolicing.enus.html.part.HtmlPart.class);
								searchList.addFilterQuery("deleted_indexed_boolean:false");
								searchList.addFilterQuery("archived_indexed_boolean:false");
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && o.getHtmlPartKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_removeA)
												.execute(Tuple.of(pk, "htmlPartKeys", l2, "pageDesignKeys")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value PageDesign.htmlPartKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("HtmlPart");
									}
								}
							}
						}
						break;
					case "setPageDesignCompleteName":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "pageDesignCompleteName")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value PageDesign.pageDesignCompleteName failed", b.cause())));
								});
							}));
						} else {
							o2.setPageDesignCompleteName(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "pageDesignCompleteName", o2.jsonPageDesignCompleteName())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value PageDesign.pageDesignCompleteName failed", b.cause())));
								});
							}));
						}
						break;
					case "setDesignHidden":
						if(jsonObject.getBoolean(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "designHidden")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value PageDesign.designHidden failed", b.cause())));
								});
							}));
						} else {
							o2.setDesignHidden(jsonObject.getBoolean(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "designHidden", o2.jsonDesignHidden())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value PageDesign.designHidden failed", b.cause())));
								});
							}));
						}
						break;
					case "setPageContentType":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "pageContentType")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value PageDesign.pageContentType failed", b.cause())));
								});
							}));
						} else {
							o2.setPageContentType(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "pageContentType", o2.jsonPageContentType())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value PageDesign.pageContentType failed", b.cause())));
								});
							}));
						}
						break;
				}
			}
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					PageDesign o3 = new PageDesign();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
					eventHandler.handle(Future.succeededFuture(o3));
				} else {
					LOGGER.error(String.format("sqlPATCHPageDesign failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPATCHPageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void patchPageDesignResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PATCHPageDesign(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("patchPageDesignResponse failed. ", a.cause()));
					errorPageDesign(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("patchPageDesignResponse failed. ", ex));
			errorPageDesign(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PATCHPageDesign(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PATCHPageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// GET //

	@Override
	public void getPageDesign(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForPageDesign(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/page-design/{id}");
		siteRequest.setRequestMethod("GET");
		try {
			{
				userPageDesign(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchPageDesign(siteRequest, false, true, false, "/api/page-design/{id}", "GET", c -> {
							if(c.succeeded()) {
								SearchList<PageDesign> listPageDesign = c.result();
								getPageDesignResponse(listPageDesign, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("getPageDesign succeeded. "));
									} else {
										LOGGER.error(String.format("getPageDesign failed. ", d.cause()));
										errorPageDesign(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("getPageDesign failed. ", c.cause()));
								errorPageDesign(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("getPageDesign failed. ", b.cause()));
						errorPageDesign(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("getPageDesign failed. ", ex));
			errorPageDesign(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void getPageDesignResponse(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
		try {
			response200GETPageDesign(listPageDesign, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("getPageDesignResponse failed. ", a.cause()));
					errorPageDesign(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("getPageDesignResponse failed. ", ex));
			errorPageDesign(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200GETPageDesign(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
			SolrDocumentList solrDocuments = listPageDesign.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listPageDesign.getList().stream().findFirst().orElse(null));
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200GETPageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// Search //

	@Override
	public void searchPageDesign(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForPageDesign(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/page-design");
		siteRequest.setRequestMethod("Search");
		try {
			{
				userPageDesign(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchPageDesign(siteRequest, false, true, false, "/api/page-design", "Search", c -> {
							if(c.succeeded()) {
								SearchList<PageDesign> listPageDesign = c.result();
								searchPageDesignResponse(listPageDesign, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("searchPageDesign succeeded. "));
									} else {
										LOGGER.error(String.format("searchPageDesign failed. ", d.cause()));
										errorPageDesign(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("searchPageDesign failed. ", c.cause()));
								errorPageDesign(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("searchPageDesign failed. ", b.cause()));
						errorPageDesign(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("searchPageDesign failed. ", ex));
			errorPageDesign(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void searchPageDesignResponse(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
		try {
			response200SearchPageDesign(listPageDesign, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("searchPageDesignResponse failed. ", a.cause()));
					errorPageDesign(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchPageDesignResponse failed. ", ex));
			errorPageDesign(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchPageDesign(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
			QueryResponse responseSearch = listPageDesign.getQueryResponse();
			SolrDocumentList solrDocuments = listPageDesign.getSolrDocumentList();
			Long searchInMillis = Long.valueOf(responseSearch.getQTime());
			Long transmissionInMillis = responseSearch.getElapsedTime();
			Long startNum = responseSearch.getResults().getStart();
			Long foundNum = responseSearch.getResults().getNumFound();
			Integer returnedNum = responseSearch.getResults().size();
			String searchTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(searchInMillis), TimeUnit.MILLISECONDS.toMillis(searchInMillis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(searchInMillis)));
			String transmissionTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis), TimeUnit.MILLISECONDS.toMillis(transmissionInMillis) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis)));
			Exception exceptionSearch = responseSearch.getException();
			List<String> fls = listPageDesign.getFields();

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
				json.put("searchTime", searchTime);
				json.put("transmissionTime", transmissionTime);
			}
			JsonArray l = new JsonArray();
			listPageDesign.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
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

			List<RangeFacet> facetRanges = responseSearch.getFacetRanges();
			if(facetRanges != null) {
				JsonObject rangeJson = new JsonObject();
				json.put("facet_ranges", rangeJson);
				for(RangeFacet rangeFacet : facetRanges) {
					JsonObject rangeFacetJson = new JsonObject();
					String rangeFacetVar = StringUtils.substringBefore(rangeFacet.getName(), "_indexed_");
					rangeJson.put(rangeFacetVar, rangeFacetJson);
					JsonArray rangeFacetCountsList = new JsonArray();
					rangeFacetJson.put("counts", rangeFacetCountsList);
					List<?> rangeFacetCounts = rangeFacet.getCounts();
					for(Integer i = 0; i < rangeFacetCounts.size(); i+= 1) {
						JsonObject countJson = new JsonObject();
						RangeFacet.Count count = (RangeFacet.Count)rangeFacetCounts.get(i);
						countJson.put("value", count.getValue());
						countJson.put("count", count.getCount());
						rangeFacetCountsList.add(countJson);
					}
				}
			}

			NamedList<List<PivotField>> facetPivot = responseSearch.getFacetPivot();
			if(facetPivot != null) {
				JsonObject facetPivotJson = new JsonObject();
				json.put("facet_pivot", facetPivotJson);
				Iterator<Entry<String, List<PivotField>>> facetPivotIterator = responseSearch.getFacetPivot().iterator();
				while(facetPivotIterator.hasNext()) {
					Entry<String, List<PivotField>> pivotEntry = facetPivotIterator.next();
					List<PivotField> pivotFields = pivotEntry.getValue();
					String[] varsIndexed = pivotEntry.getKey().trim().split(",");
					String[] entityVars = new String[varsIndexed.length];
					for(Integer i = 0; i < entityVars.length; i++) {
						String entityIndexed = varsIndexed[i];
						entityVars[i] = StringUtils.substringBefore(entityIndexed, "_indexed_");
					}
					JsonArray pivotArray = new JsonArray();
					facetPivotJson.put(StringUtils.join(entityVars, ","), pivotArray);
					responsePivotSearchPageDesign(pivotFields, pivotArray);
				}
			}
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200SearchPageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}
	public void responsePivotSearchPageDesign(List<PivotField> pivotFields, JsonArray pivotArray) {
		for(PivotField pivotField : pivotFields) {
			String entityIndexed = pivotField.getField();
			String entityVar = StringUtils.substringBefore(entityIndexed, "_indexed_");
			JsonObject pivotJson = new JsonObject();
			pivotArray.add(pivotJson);
			pivotJson.put("field", entityVar);
			pivotJson.put("value", pivotField.getValue());
			pivotJson.put("count", pivotField.getCount());
			List<RangeFacet> pivotRanges = pivotField.getFacetRanges();
			List<PivotField> pivotFields2 = pivotField.getPivot();
			if(pivotRanges != null) {
				JsonObject rangeJson = new JsonObject();
				pivotJson.put("ranges", rangeJson);
				for(RangeFacet rangeFacet : pivotRanges) {
					JsonObject rangeFacetJson = new JsonObject();
					String rangeFacetVar = StringUtils.substringBefore(rangeFacet.getName(), "_indexed_");
					rangeJson.put(rangeFacetVar, rangeFacetJson);
					JsonArray rangeFacetCountsList = new JsonArray();
					rangeFacetJson.put("counts", rangeFacetCountsList);
					List<?> rangeFacetCounts = rangeFacet.getCounts();
					for(Integer i = 0; i < rangeFacetCounts.size(); i+= 1) {
						JsonObject countJson = new JsonObject();
						RangeFacet.Count count = (RangeFacet.Count)rangeFacetCounts.get(i);
						countJson.put("value", count.getValue());
						countJson.put("count", count.getCount());
						rangeFacetCountsList.add(countJson);
					}
				}
			}
			if(pivotFields2 != null) {
				JsonArray pivotArray2 = new JsonArray();
				pivotJson.put("pivot", pivotArray2);
				responsePivotSearchPageDesign(pivotFields2, pivotArray2);
			}
		}
	}

	// AdminSearch //

	@Override
	public void adminsearchPageDesign(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForPageDesign(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/admin/page-design");
		siteRequest.setRequestMethod("AdminSearch");
		try {
			{
				userPageDesign(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchPageDesign(siteRequest, false, true, false, "/api/admin/page-design", "AdminSearch", c -> {
							if(c.succeeded()) {
								SearchList<PageDesign> listPageDesign = c.result();
								adminsearchPageDesignResponse(listPageDesign, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("adminsearchPageDesign succeeded. "));
									} else {
										LOGGER.error(String.format("adminsearchPageDesign failed. ", d.cause()));
										errorPageDesign(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("adminsearchPageDesign failed. ", c.cause()));
								errorPageDesign(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("adminsearchPageDesign failed. ", b.cause()));
						errorPageDesign(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("adminsearchPageDesign failed. ", ex));
			errorPageDesign(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void adminsearchPageDesignResponse(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
		try {
			response200AdminSearchPageDesign(listPageDesign, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("adminsearchPageDesignResponse failed. ", a.cause()));
					errorPageDesign(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("adminsearchPageDesignResponse failed. ", ex));
			errorPageDesign(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200AdminSearchPageDesign(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
			QueryResponse responseSearch = listPageDesign.getQueryResponse();
			SolrDocumentList solrDocuments = listPageDesign.getSolrDocumentList();
			Long searchInMillis = Long.valueOf(responseSearch.getQTime());
			Long transmissionInMillis = responseSearch.getElapsedTime();
			Long startNum = responseSearch.getResults().getStart();
			Long foundNum = responseSearch.getResults().getNumFound();
			Integer returnedNum = responseSearch.getResults().size();
			String searchTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(searchInMillis), TimeUnit.MILLISECONDS.toMillis(searchInMillis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(searchInMillis)));
			String transmissionTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis), TimeUnit.MILLISECONDS.toMillis(transmissionInMillis) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis)));
			Exception exceptionSearch = responseSearch.getException();
			List<String> fls = listPageDesign.getFields();

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
				json.put("searchTime", searchTime);
				json.put("transmissionTime", transmissionTime);
			}
			JsonArray l = new JsonArray();
			listPageDesign.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
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

			List<RangeFacet> facetRanges = responseSearch.getFacetRanges();
			if(facetRanges != null) {
				JsonObject rangeJson = new JsonObject();
				json.put("facet_ranges", rangeJson);
				for(RangeFacet rangeFacet : facetRanges) {
					JsonObject rangeFacetJson = new JsonObject();
					String rangeFacetVar = StringUtils.substringBefore(rangeFacet.getName(), "_indexed_");
					rangeJson.put(rangeFacetVar, rangeFacetJson);
					JsonArray rangeFacetCountsList = new JsonArray();
					rangeFacetJson.put("counts", rangeFacetCountsList);
					List<?> rangeFacetCounts = rangeFacet.getCounts();
					for(Integer i = 0; i < rangeFacetCounts.size(); i+= 1) {
						JsonObject countJson = new JsonObject();
						RangeFacet.Count count = (RangeFacet.Count)rangeFacetCounts.get(i);
						countJson.put("value", count.getValue());
						countJson.put("count", count.getCount());
						rangeFacetCountsList.add(countJson);
					}
				}
			}

			NamedList<List<PivotField>> facetPivot = responseSearch.getFacetPivot();
			if(facetPivot != null) {
				JsonObject facetPivotJson = new JsonObject();
				json.put("facet_pivot", facetPivotJson);
				Iterator<Entry<String, List<PivotField>>> facetPivotIterator = responseSearch.getFacetPivot().iterator();
				while(facetPivotIterator.hasNext()) {
					Entry<String, List<PivotField>> pivotEntry = facetPivotIterator.next();
					List<PivotField> pivotFields = pivotEntry.getValue();
					String[] varsIndexed = pivotEntry.getKey().trim().split(",");
					String[] entityVars = new String[varsIndexed.length];
					for(Integer i = 0; i < entityVars.length; i++) {
						String entityIndexed = varsIndexed[i];
						entityVars[i] = StringUtils.substringBefore(entityIndexed, "_indexed_");
					}
					JsonArray pivotArray = new JsonArray();
					facetPivotJson.put(StringUtils.join(entityVars, ","), pivotArray);
					responsePivotAdminSearchPageDesign(pivotFields, pivotArray);
				}
			}
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200AdminSearchPageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}
	public void responsePivotAdminSearchPageDesign(List<PivotField> pivotFields, JsonArray pivotArray) {
		for(PivotField pivotField : pivotFields) {
			String entityIndexed = pivotField.getField();
			String entityVar = StringUtils.substringBefore(entityIndexed, "_indexed_");
			JsonObject pivotJson = new JsonObject();
			pivotArray.add(pivotJson);
			pivotJson.put("field", entityVar);
			pivotJson.put("value", pivotField.getValue());
			pivotJson.put("count", pivotField.getCount());
			List<RangeFacet> pivotRanges = pivotField.getFacetRanges();
			List<PivotField> pivotFields2 = pivotField.getPivot();
			if(pivotRanges != null) {
				JsonObject rangeJson = new JsonObject();
				pivotJson.put("ranges", rangeJson);
				for(RangeFacet rangeFacet : pivotRanges) {
					JsonObject rangeFacetJson = new JsonObject();
					String rangeFacetVar = StringUtils.substringBefore(rangeFacet.getName(), "_indexed_");
					rangeJson.put(rangeFacetVar, rangeFacetJson);
					JsonArray rangeFacetCountsList = new JsonArray();
					rangeFacetJson.put("counts", rangeFacetCountsList);
					List<?> rangeFacetCounts = rangeFacet.getCounts();
					for(Integer i = 0; i < rangeFacetCounts.size(); i+= 1) {
						JsonObject countJson = new JsonObject();
						RangeFacet.Count count = (RangeFacet.Count)rangeFacetCounts.get(i);
						countJson.put("value", count.getValue());
						countJson.put("count", count.getCount());
						rangeFacetCountsList.add(countJson);
					}
				}
			}
			if(pivotFields2 != null) {
				JsonArray pivotArray2 = new JsonArray();
				pivotJson.put("pivot", pivotArray2);
				responsePivotAdminSearchPageDesign(pivotFields2, pivotArray2);
			}
		}
	}

	// SearchPage //

	@Override
	public void searchpagePageDesignId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		searchpagePageDesign(operationRequest, eventHandler);
	}

	@Override
	public void searchpagePageDesign(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForPageDesign(siteContext, operationRequest);
		siteRequest.setRequestUri("/page-design");
		siteRequest.setRequestMethod("SearchPage");
		try {
			{
				userPageDesign(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchPageDesign(siteRequest, false, true, false, "/page-design", "SearchPage", c -> {
							if(c.succeeded()) {
								SearchList<PageDesign> listPageDesign = c.result();
								searchpagePageDesignResponse(listPageDesign, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("searchpagePageDesign succeeded. "));
									} else {
										LOGGER.error(String.format("searchpagePageDesign failed. ", d.cause()));
										errorPageDesign(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("searchpagePageDesign failed. ", c.cause()));
								errorPageDesign(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("searchpagePageDesign failed. ", b.cause()));
						errorPageDesign(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("searchpagePageDesign failed. ", ex));
			errorPageDesign(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void searchpagePageDesignPageInit(PageDesignPage page, SearchList<PageDesign> listPageDesign) {
	}
	public void searchpagePageDesignResponse(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200SearchPagePageDesign(listPageDesign, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("searchpagePageDesignResponse failed. ", a.cause()));
					errorPageDesign(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchpagePageDesignResponse failed. ", ex));
			errorPageDesign(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchPagePageDesign(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listPageDesign.getSiteRequest_(), buffer);
			PageDesignPage page = new PageDesignPage();
			SolrDocument pageSolrDocument = new SolrDocument();
			MultiMap requestHeaders = MultiMap.caseInsensitiveMultiMap();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/page-design");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listPageDesign.size() == 1)
				siteRequest.setRequestPk(listPageDesign.get(0).getPk());
			siteRequest.setW(w);
			page.setListPageDesign(listPageDesign);
			page.setSiteRequest_(siteRequest);
			searchpagePageDesignPageInit(page, listPageDesign);
			page.initDeepPageDesignPage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new OperationResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOGGER.error(String.format("response200SearchPagePageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// DesignDisplaySearchPage //

	@Override
	public void designdisplaysearchpagePageDesignId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		designdisplaysearchpagePageDesign(operationRequest, eventHandler);
	}

	@Override
	public void designdisplaysearchpagePageDesign(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForPageDesign(siteContext, operationRequest);
		siteRequest.setRequestUri("/page");
		siteRequest.setRequestMethod("DesignDisplaySearchPage");
		try {
			{
				userPageDesign(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchPageDesign(siteRequest, false, true, false, "/page", "DesignDisplaySearchPage", c -> {
							if(c.succeeded()) {
								SearchList<PageDesign> listPageDesign = c.result();
								designdisplaysearchpagePageDesignResponse(listPageDesign, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("designdisplaysearchpagePageDesign succeeded. "));
									} else {
										LOGGER.error(String.format("designdisplaysearchpagePageDesign failed. ", d.cause()));
										errorPageDesign(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("designdisplaysearchpagePageDesign failed. ", c.cause()));
								errorPageDesign(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("designdisplaysearchpagePageDesign failed. ", b.cause()));
						errorPageDesign(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("designdisplaysearchpagePageDesign failed. ", ex));
			errorPageDesign(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void designdisplaysearchpagePageDesignPageInit(DesignDisplayPage page, SearchList<PageDesign> listPageDesign) {
	}
	public void designdisplaysearchpagePageDesignResponse(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200DesignDisplaySearchPagePageDesign(listPageDesign, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("designdisplaysearchpagePageDesignResponse failed. ", a.cause()));
					errorPageDesign(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("designdisplaysearchpagePageDesignResponse failed. ", ex));
			errorPageDesign(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200DesignDisplaySearchPagePageDesign(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listPageDesign.getSiteRequest_(), buffer);
			DesignDisplayPage page = new DesignDisplayPage();
			SolrDocument pageSolrDocument = new SolrDocument();
			MultiMap requestHeaders = MultiMap.caseInsensitiveMultiMap();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/page");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listPageDesign.size() == 1)
				siteRequest.setRequestPk(listPageDesign.get(0).getPk());
			siteRequest.setW(w);
			page.setListPageDesign(listPageDesign);
			page.setSiteRequest_(siteRequest);
			designdisplaysearchpagePageDesignPageInit(page, listPageDesign);
			page.initDeepDesignDisplayPage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new OperationResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOGGER.error(String.format("response200DesignDisplaySearchPagePageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// DesignPdfSearchPage //

	@Override
	public void designpdfsearchpagePageDesignId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		designpdfsearchpagePageDesign(operationRequest, eventHandler);
	}

	@Override
	public void designpdfsearchpagePageDesign(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForPageDesign(siteContext, operationRequest);
		siteRequest.setRequestUri("/pdf");
		siteRequest.setRequestMethod("DesignPdfSearchPage");
		try {
			{
				userPageDesign(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchPageDesign(siteRequest, false, true, false, "/pdf", "DesignPdfSearchPage", c -> {
							if(c.succeeded()) {
								SearchList<PageDesign> listPageDesign = c.result();
								designpdfsearchpagePageDesignResponse(listPageDesign, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("designpdfsearchpagePageDesign succeeded. "));
									} else {
										LOGGER.error(String.format("designpdfsearchpagePageDesign failed. ", d.cause()));
										errorPageDesign(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("designpdfsearchpagePageDesign failed. ", c.cause()));
								errorPageDesign(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("designpdfsearchpagePageDesign failed. ", b.cause()));
						errorPageDesign(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("designpdfsearchpagePageDesign failed. ", ex));
			errorPageDesign(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void designpdfsearchpagePageDesignPageInit(DesignPdfPage page, SearchList<PageDesign> listPageDesign) {
	}
	public void designpdfsearchpagePageDesignResponse(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
		try {
			response200DesignPdfSearchPagePageDesign(listPageDesign, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("designpdfsearchpagePageDesignResponse failed. ", a.cause()));
					errorPageDesign(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("designpdfsearchpagePageDesignResponse failed. ", ex));
			errorPageDesign(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200DesignPdfSearchPagePageDesign(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listPageDesign.getSiteRequest_(), buffer);
			DesignPdfPage page = new DesignPdfPage();
			SolrDocument pageSolrDocument = new SolrDocument();
			MultiMap requestHeaders = MultiMap.caseInsensitiveMultiMap();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/pdf");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listPageDesign.size() == 1)
				siteRequest.setRequestPk(listPageDesign.get(0).getPk());
			siteRequest.setW(w);
			page.setListPageDesign(listPageDesign);
			page.setSiteRequest_(siteRequest);
			designpdfsearchpagePageDesignPageInit(page, listPageDesign);
			page.initDeepDesignPdfPage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new OperationResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOGGER.error(String.format("response200DesignPdfSearchPagePageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// DesignEmailSearchPage //

	@Override
	public void designemailsearchpagePageDesignId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		designemailsearchpagePageDesign(operationRequest, eventHandler);
	}

	@Override
	public void designemailsearchpagePageDesign(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForPageDesign(siteContext, operationRequest);
		siteRequest.setRequestUri("/email");
		siteRequest.setRequestMethod("DesignEmailSearchPage");
		try {
			{
				userPageDesign(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchPageDesign(siteRequest, false, true, false, "/email", "DesignEmailSearchPage", c -> {
							if(c.succeeded()) {
								SearchList<PageDesign> listPageDesign = c.result();
								designemailsearchpagePageDesignResponse(listPageDesign, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("designemailsearchpagePageDesign succeeded. "));
									} else {
										LOGGER.error(String.format("designemailsearchpagePageDesign failed. ", d.cause()));
										errorPageDesign(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("designemailsearchpagePageDesign failed. ", c.cause()));
								errorPageDesign(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("designemailsearchpagePageDesign failed. ", b.cause()));
						errorPageDesign(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("designemailsearchpagePageDesign failed. ", ex));
			errorPageDesign(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void designemailsearchpagePageDesignPageInit(DesignEmailPage page, SearchList<PageDesign> listPageDesign) {
	}
	public void designemailsearchpagePageDesignResponse(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200DesignEmailSearchPagePageDesign(listPageDesign, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("designemailsearchpagePageDesignResponse failed. ", a.cause()));
					errorPageDesign(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("designemailsearchpagePageDesignResponse failed. ", ex));
			errorPageDesign(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200DesignEmailSearchPagePageDesign(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listPageDesign.getSiteRequest_(), buffer);
			DesignEmailPage page = new DesignEmailPage();
			SolrDocument pageSolrDocument = new SolrDocument();
			MultiMap requestHeaders = MultiMap.caseInsensitiveMultiMap();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/email");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listPageDesign.size() == 1)
				siteRequest.setRequestPk(listPageDesign.get(0).getPk());
			siteRequest.setW(w);
			page.setListPageDesign(listPageDesign);
			page.setSiteRequest_(siteRequest);
			designemailsearchpagePageDesignPageInit(page, listPageDesign);
			page.initDeepDesignEmailPage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new OperationResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOGGER.error(String.format("response200DesignEmailSearchPagePageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// HomePageSearchPage //

	@Override
	public void homepagesearchpagePageDesignId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		homepagesearchpagePageDesign(operationRequest, eventHandler);
	}

	@Override
	public void homepagesearchpagePageDesign(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForPageDesign(siteContext, operationRequest);
		siteRequest.setRequestUri("/");
		siteRequest.setRequestMethod("HomePageSearchPage");
		try {
			{
				userPageDesign(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchPageDesign(siteRequest, false, true, false, "/", "HomePageSearchPage", c -> {
							if(c.succeeded()) {
								SearchList<PageDesign> listPageDesign = c.result();
								homepagesearchpagePageDesignResponse(listPageDesign, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("homepagesearchpagePageDesign succeeded. "));
									} else {
										LOGGER.error(String.format("homepagesearchpagePageDesign failed. ", d.cause()));
										errorPageDesign(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("homepagesearchpagePageDesign failed. ", c.cause()));
								errorPageDesign(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("homepagesearchpagePageDesign failed. ", b.cause()));
						errorPageDesign(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("homepagesearchpagePageDesign failed. ", ex));
			errorPageDesign(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void homepagesearchpagePageDesignPageInit(DesignDisplayPage page, SearchList<PageDesign> listPageDesign) {
	}
	public void homepagesearchpagePageDesignResponse(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200HomePageSearchPagePageDesign(listPageDesign, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("homepagesearchpagePageDesignResponse failed. ", a.cause()));
					errorPageDesign(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("homepagesearchpagePageDesignResponse failed. ", ex));
			errorPageDesign(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200HomePageSearchPagePageDesign(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listPageDesign.getSiteRequest_(), buffer);
			DesignDisplayPage page = new DesignDisplayPage();
			SolrDocument pageSolrDocument = new SolrDocument();
			MultiMap requestHeaders = MultiMap.caseInsensitiveMultiMap();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listPageDesign.size() == 1)
				siteRequest.setRequestPk(listPageDesign.get(0).getPk());
			siteRequest.setW(w);
			page.setListPageDesign(listPageDesign);
			page.setSiteRequest_(siteRequest);
			homepagesearchpagePageDesignPageInit(page, listPageDesign);
			page.initDeepDesignDisplayPage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new OperationResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOGGER.error(String.format("response200HomePageSearchPagePageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// General //

	public Future<PageDesign> defineIndexPageDesign(PageDesign pageDesign, Handler<AsyncResult<PageDesign>> eventHandler) {
		Promise<PageDesign> promise = Promise.promise();
		SiteRequestEnUS siteRequest = pageDesign.getSiteRequest_();
		definePageDesign(pageDesign, c -> {
			if(c.succeeded()) {
				attributePageDesign(pageDesign, d -> {
					if(d.succeeded()) {
						indexPageDesign(pageDesign, e -> {
							if(e.succeeded()) {
								sqlCommitPageDesign(siteRequest, f -> {
									if(f.succeeded()) {
										sqlClosePageDesign(siteRequest, g -> {
											if(g.succeeded()) {
												refreshPageDesign(pageDesign, h -> {
													if(h.succeeded()) {
														eventHandler.handle(Future.succeededFuture(pageDesign));
														promise.complete(pageDesign);
													} else {
														LOGGER.error(String.format("refreshPageDesign failed. ", h.cause()));
														errorPageDesign(siteRequest, null, h);
													}
												});
											} else {
												LOGGER.error(String.format("defineIndexPageDesign failed. ", g.cause()));
												errorPageDesign(siteRequest, null, g);
											}
										});
									} else {
										LOGGER.error(String.format("defineIndexPageDesign failed. ", f.cause()));
										errorPageDesign(siteRequest, null, f);
									}
								});
							} else {
								LOGGER.error(String.format("defineIndexPageDesign failed. ", e.cause()));
								errorPageDesign(siteRequest, null, e);
							}
						});
					} else {
						LOGGER.error(String.format("defineIndexPageDesign failed. ", d.cause()));
						errorPageDesign(siteRequest, null, d);
					}
				});
			} else {
				LOGGER.error(String.format("defineIndexPageDesign failed. ", c.cause()));
				errorPageDesign(siteRequest, null, c);
			}
		});
		return promise.future();
	}

	public void createPageDesign(SiteRequestEnUS siteRequest, Handler<AsyncResult<PageDesign>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();
			String userId = siteRequest.getUserId();
			ZonedDateTime created = Optional.ofNullable(siteRequest.getJsonObject()).map(j -> j.getString("created")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())))).orElse(ZonedDateTime.now(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())));

			tx.preparedQuery(SiteContextEnUS.SQL_create)
					.collecting(Collectors.toList())
					.execute(Tuple.of(PageDesign.class.getCanonicalName(), userId, created.toOffsetDateTime())
					, createAsync
			-> {
				if(createAsync.succeeded()) {
					Row createLine = createAsync.result().value().stream().findFirst().orElseGet(() -> null);
					Long pk = createLine.getLong(0);
					PageDesign o = new PageDesign();
					o.setPk(pk);
					o.setSiteRequest_(siteRequest);
					eventHandler.handle(Future.succeededFuture(o));
				} else {
					LOGGER.error(String.format("createPageDesign failed. ", createAsync.cause()));
					eventHandler.handle(Future.failedFuture(createAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("createPageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void errorPageDesign(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler, AsyncResult<?> resultAsync) {
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
				, MultiMap.caseInsensitiveMultiMap().add("Content-Type", "application/json")
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
		sqlRollbackPageDesign(siteRequest, a -> {
			if(a.succeeded()) {
				LOGGER.info(String.format("sql rollback. "));
				sqlClosePageDesign(siteRequest, b -> {
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

	public void sqlConnectionPageDesign(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("sqlConnectionPageDesign failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlTransactionPageDesign(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
			LOGGER.error(String.format("sqlTransactionPageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCommitPageDesign(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("sqlCommitPageDesign failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlRollbackPageDesign(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("sqlRollbackPageDesign failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlClosePageDesign(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
			LOGGER.error(String.format("sqlClosePageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SiteRequestEnUS generateSiteRequestEnUSForPageDesign(SiteContextEnUS siteContext, OperationRequest operationRequest) {
		return generateSiteRequestEnUSForPageDesign(siteContext, operationRequest, null);
	}

	public SiteRequestEnUS generateSiteRequestEnUSForPageDesign(SiteContextEnUS siteContext, OperationRequest operationRequest, JsonObject body) {
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

	public void userPageDesign(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			String userId = siteRequest.getUserId();
			if(userId == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				sqlConnectionPageDesign(siteRequest, a -> {
					if(a.succeeded()) {
						sqlTransactionPageDesign(siteRequest, b -> {
							if(b.succeeded()) {
								Transaction tx = siteRequest.getTx();
								tx.preparedQuery(SiteContextEnUS.SQL_selectC)
										.collecting(Collectors.toList())
										.execute(Tuple.of("com.opendatapolicing.enus.user.SiteUser", userId)
										, selectCAsync
								-> {
									if(selectCAsync.succeeded()) {
										try {
											Row userValues = selectCAsync.result().value().stream().findFirst().orElse(null);
											SiteUserEnUSApiServiceImpl userService = new SiteUserEnUSApiServiceImpl(siteContext);
											if(userValues == null) {
												JsonObject userVertx = siteRequest.getOperationRequest().getUser();
												OAuth2TokenImpl token = new OAuth2TokenImpl(siteContext.getAuthProvider(), userVertx);
												JsonObject jsonPrincipal = token.accessToken();

												JsonObject jsonObject = new JsonObject();
												jsonObject.put("userName", jsonPrincipal.getString("preferred_username"));
												jsonObject.put("userFirstName", jsonPrincipal.getString("given_name"));
												jsonObject.put("userLastName", jsonPrincipal.getString("family_name"));
												jsonObject.put("userCompleteName", jsonPrincipal.getString("name"));
												jsonObject.put("userId", jsonPrincipal.getString("sub"));
												jsonObject.put("userEmail", jsonPrincipal.getString("email"));
												userPageDesignDefine(siteRequest, jsonObject, false);

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
																		siteRequest.setUserEmail(jsonPrincipal.getString("email"));
																		siteRequest.setUserId(jsonPrincipal.getString("sub"));
																		siteRequest.setUserKey(siteUser.getPk());
																		eventHandler.handle(Future.succeededFuture());
																	} else {
																		errorPageDesign(siteRequest, eventHandler, e);
																	}
																});
															} else {
																errorPageDesign(siteRequest, eventHandler, d);
															}
														});
													} else {
														errorPageDesign(siteRequest, eventHandler, c);
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
												OAuth2TokenImpl token = new OAuth2TokenImpl(siteContext.getAuthProvider(), userVertx);
												JsonObject jsonPrincipal = token.accessToken();

												JsonObject jsonObject = new JsonObject();
												jsonObject.put("setUserName", jsonPrincipal.getString("preferred_username"));
												jsonObject.put("setUserFirstName", jsonPrincipal.getString("given_name"));
												jsonObject.put("setUserLastName", jsonPrincipal.getString("family_name"));
												jsonObject.put("setUserCompleteName", jsonPrincipal.getString("name"));
												jsonObject.put("setUserId", jsonPrincipal.getString("sub"));
												jsonObject.put("setUserEmail", jsonPrincipal.getString("email"));
												Boolean define = userPageDesignDefine(siteRequest, jsonObject, true);
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
																	errorPageDesign(siteRequest, eventHandler, e);
																}
															});
														} else {
															errorPageDesign(siteRequest, eventHandler, d);
														}
													});
												} else {
													siteRequest.setSiteUser(siteUser1);
													siteRequest.setUserName(siteUser1.getUserName());
													siteRequest.setUserFirstName(siteUser1.getUserFirstName());
													siteRequest.setUserLastName(siteUser1.getUserLastName());
													siteRequest.setUserId(siteUser1.getUserId());
													siteRequest.setUserKey(siteUser1.getPk());
													sqlRollbackPageDesign(siteRequest, c -> {
														if(c.succeeded()) {
															eventHandler.handle(Future.succeededFuture());
														} else {
															eventHandler.handle(Future.failedFuture(c.cause()));
															errorPageDesign(siteRequest, eventHandler, c);
														}
													});
												}
											}
										} catch(Exception e) {
											LOGGER.error(String.format("userPageDesign failed. ", e));
											eventHandler.handle(Future.failedFuture(e));
										}
									} else {
										LOGGER.error(String.format("userPageDesign failed. ", selectCAsync.cause()));
										eventHandler.handle(Future.failedFuture(selectCAsync.cause()));
									}
								});
							} else {
								LOGGER.error(String.format("userPageDesign failed. ", b.cause()));
								eventHandler.handle(Future.failedFuture(b.cause()));
							}
						});
					} else {
						LOGGER.error(String.format("userPageDesign failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("userPageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public Boolean userPageDesignDefine(SiteRequestEnUS siteRequest, JsonObject jsonObject, Boolean patch) {
		if(patch) {
			return false;
		} else {
			return false;
		}
	}

	public void aSearchPageDesignQ(String uri, String apiMethod, SearchList<PageDesign> searchList, String entityVar, String valueIndexed, String varIndexed) {
		searchList.setQuery(varIndexed + ":" + ("*".equals(valueIndexed) ? valueIndexed : ClientUtils.escapeQueryChars(valueIndexed)));
		if(!"*".equals(entityVar)) {
		}
	}

	public void aSearchPageDesignFq(String uri, String apiMethod, SearchList<PageDesign> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		if(StringUtils.startsWith(valueIndexed, "[")) {
			String[] fqs = StringUtils.substringBefore(StringUtils.substringAfter(valueIndexed, "["), "]").split(" TO ");
			if(fqs.length != 2)
				throw new RuntimeException(String.format("\"%s\" invalid range query. ", valueIndexed));
			String fq1 = fqs[0].equals("*") ? fqs[0] : PageDesign.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[0]);
			String fq2 = fqs[1].equals("*") ? fqs[1] : PageDesign.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[1]);
			searchList.addFilterQuery(varIndexed + ":[" + fq1 + " TO " + fq2 + "]");
		} else {
			searchList.addFilterQuery(varIndexed + ":" + PageDesign.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), valueIndexed));
		}
	}

	public void aSearchPageDesignSort(String uri, String apiMethod, SearchList<PageDesign> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addSort(varIndexed, ORDER.valueOf(valueIndexed));
	}

	public void aSearchPageDesignRows(String uri, String apiMethod, SearchList<PageDesign> searchList, Integer valueRows) {
			searchList.setRows(apiMethod != null && apiMethod.contains("Search") ? valueRows : 10);
	}

	public void aSearchPageDesignStart(String uri, String apiMethod, SearchList<PageDesign> searchList, Integer valueStart) {
		searchList.setStart(valueStart);
	}

	public void aSearchPageDesignVar(String uri, String apiMethod, SearchList<PageDesign> searchList, String var, String value) {
		searchList.getSiteRequest_().getRequestVars().put(var, value);
	}

	public void aSearchPageDesignUri(String uri, String apiMethod, SearchList<PageDesign> searchList) {
	}

	public void varsPageDesign(SiteRequestEnUS siteRequest, Handler<AsyncResult<SearchList<OperationResponse>>> eventHandler) {
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
					LOGGER.error(String.format("aSearchPageDesign failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOGGER.error(String.format("aSearchPageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void aSearchPageDesign(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, Handler<AsyncResult<SearchList<PageDesign>>> eventHandler) {
		try {
			SearchList<PageDesign> searchList = aSearchPageDesignList(siteRequest, populate, store, modify, uri, apiMethod);
			eventHandler.handle(Future.succeededFuture(searchList));
		} catch(Exception e) {
			LOGGER.error(String.format("aSearchPageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SearchList<PageDesign> aSearchPageDesignList(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod) {
		OperationRequest operationRequest = siteRequest.getOperationRequest();
		String entityListStr = siteRequest.getOperationRequest().getParams().getJsonObject("query").getString("fl");
		String[] entityList = entityListStr == null ? null : entityListStr.split(",\\s*");
		SearchList<PageDesign> searchList = new SearchList<PageDesign>();
		searchList.setPopulate(populate);
		searchList.setStore(store);
		searchList.setQuery("*:*");
		searchList.setC(PageDesign.class);
		searchList.setSiteRequest_(siteRequest);
		if(entityList != null)
			searchList.addFields(entityList);
		searchList.add("json.facet", "{max_modified:'max(modified_indexed_date)'}");

		String id = operationRequest.getParams().getJsonObject("path").getString("id");
		if(id != null && NumberUtils.isCreatable(id)) {
			searchList.addFilterQuery("(pk_indexed_long:" + ClientUtils.escapeQueryChars(id) + " OR objectId_indexed_string:" + ClientUtils.escapeQueryChars(id) + ")");
		} else if(id != null) {
			searchList.addFilterQuery("objectId_indexed_string:" + ClientUtils.escapeQueryChars(id));
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
				if("facet.pivot".equals(paramName)) {
					Matcher mFacetPivot = Pattern.compile("(?:(\\{![^\\}]+\\}))?(.*)").matcher(StringUtils.join(paramObjects.getList().toArray(), ","));
					boolean foundFacetPivot = mFacetPivot.find();
					if(foundFacetPivot) {
						String solrLocalParams = mFacetPivot.group(1);
						String[] entityVars = mFacetPivot.group(2).trim().split(",");
						String[] varsIndexed = new String[entityVars.length];
						for(Integer i = 0; i < entityVars.length; i++) {
							entityVar = entityVars[i];
							varsIndexed[i] = PageDesign.varIndexedPageDesign(entityVar);
						}
						searchList.add("facet.pivot", (solrLocalParams == null ? "" : solrLocalParams) + StringUtils.join(varsIndexed, ","));
					}
				} else {
					for(Object paramObject : paramObjects) {
						switch(paramName) {
							case "q":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								varIndexed = "*".equals(entityVar) ? entityVar : PageDesign.varSearchPageDesign(entityVar);
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								valueIndexed = StringUtils.isEmpty(valueIndexed) ? "*" : valueIndexed;
								aSearchPageDesignQ(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "fq":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								varIndexed = PageDesign.varIndexedPageDesign(entityVar);
								aSearchPageDesignFq(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "sort":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, " "));
								valueIndexed = StringUtils.trim(StringUtils.substringAfter((String)paramObject, " "));
								varIndexed = PageDesign.varIndexedPageDesign(entityVar);
								aSearchPageDesignSort(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "start":
								valueStart = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
								aSearchPageDesignStart(uri, apiMethod, searchList, valueStart);
								break;
							case "rows":
								valueRows = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
								aSearchPageDesignRows(uri, apiMethod, searchList, valueRows);
								break;
							case "facet":
								searchList.add("facet", ((Boolean)paramObject).toString());
								break;
							case "facet.range.start":
								String startMathStr = (String)paramObject;
								Date start = DateMathParser.parseMath(null, startMathStr);
								searchList.add("facet.range.start", start.toInstant().toString());
								break;
							case "facet.range.end":
								String endMathStr = (String)paramObject;
								Date end = DateMathParser.parseMath(null, endMathStr);
								searchList.add("facet.range.end", end.toInstant().toString());
								break;
							case "facet.range.gap":
								String gap = (String)paramObject;
								searchList.add("facet.range.gap", gap);
								break;
							case "facet.range":
								Matcher mFacetRange = Pattern.compile("(?:(\\{![^\\}]+\\}))?(.*)").matcher((String)paramObject);
								boolean foundFacetRange = mFacetRange.find();
								if(foundFacetRange) {
									String solrLocalParams = mFacetRange.group(1);
									entityVar = mFacetRange.group(2).trim();
									varIndexed = PageDesign.varIndexedPageDesign(entityVar);
									searchList.add("facet.range", (solrLocalParams == null ? "" : solrLocalParams) + varIndexed);
								}
								break;
							case "var":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								aSearchPageDesignVar(uri, apiMethod, searchList, entityVar, valueIndexed);
								break;
						}
					}
					aSearchPageDesignUri(uri, apiMethod, searchList);
				}
			} catch(Exception e) {
				ExceptionUtils.rethrow(e);
			}
		});
		if("*:*".equals(searchList.getQuery()) && searchList.getSorts().size() == 0) {
			searchList.addSort("pageDesignCompleteName_indexed_string", ORDER.asc);
		}
		aSearchPageDesign2(siteRequest, populate, store, modify, uri, apiMethod, searchList);
		searchList.initDeepForClass(siteRequest);
		return searchList;
	}
	public void aSearchPageDesign2(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, SearchList<PageDesign> searchList) {
	}

	public void definePageDesign(PageDesign o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			Transaction tx = siteRequest.getTx();
			Long pk = o.getPk();
			tx.preparedQuery(SiteContextEnUS.SQL_define)
					.collecting(Collectors.toList())
					.execute(Tuple.of(pk)
					, defineAsync
			-> {
				if(defineAsync.succeeded()) {
					try {
						for(Row definition : defineAsync.result().value()) {
							try {
								o.defineForClass(definition.getString(0), definition.getString(1));
							} catch(Exception e) {
								LOGGER.error(String.format("definePageDesign failed. ", e));
								LOGGER.error(e);
							}
						}
						eventHandler.handle(Future.succeededFuture());
					} catch(Exception e) {
						LOGGER.error(String.format("definePageDesign failed. ", e));
						eventHandler.handle(Future.failedFuture(e));
					}
				} else {
					LOGGER.error(String.format("definePageDesign failed. ", defineAsync.cause()));
					eventHandler.handle(Future.failedFuture(defineAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("definePageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void attributePageDesign(PageDesign o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			Transaction tx = siteRequest.getTx();
			Long pk = o.getPk();
			tx.preparedQuery(SiteContextEnUS.SQL_attribute)
					.collecting(Collectors.toList())
					.execute(Tuple.of(pk, pk)
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
						LOGGER.error(String.format("attributePageDesign failed. ", attributeAsync.cause()));
						eventHandler.handle(Future.failedFuture(attributeAsync.cause()));
					}
				} catch(Exception e) {
					LOGGER.error(String.format("attributePageDesign failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("attributePageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void indexPageDesign(PageDesign o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			o.initDeepForClass(siteRequest);
			o.indexForClass();
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOGGER.error(String.format("indexPageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void refreshPageDesign(PageDesign o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Boolean refresh = !"false".equals(siteRequest.getRequestVars().get("refresh"));
			if(refresh && BooleanUtils.isFalse(Optional.ofNullable(siteRequest.getApiRequest_()).map(ApiRequest::getEmpty).orElse(true))) {
				SearchList<PageDesign> searchList = new SearchList<PageDesign>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(PageDesign.class);
				searchList.addFilterQuery("modified_indexed_date:[" + DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(siteRequest.getApiRequest_().getCreated().toInstant(), ZoneId.of("UTC"))) + " TO *]");
				searchList.add("json.facet", "{childDesignKeys:{terms:{field:childDesignKeys_indexed_longs, limit:1000}}}");
				searchList.add("json.facet", "{htmlPartKeys:{terms:{field:htmlPartKeys_indexed_longs, limit:1000}}}");
				searchList.setRows(1000);
				searchList.initDeepSearchList(siteRequest);
				List<Future> futures = new ArrayList<>();

				for(int i=0; i < pks.size(); i++) {
					Long pk2 = pks.get(i);
					String classSimpleName2 = classes.get(i);

					if("PageDesign".equals(classSimpleName2) && pk2 != null) {
						SearchList<PageDesign> searchList2 = new SearchList<PageDesign>();
						searchList2.setStore(true);
						searchList2.setQuery("*:*");
						searchList2.setC(PageDesign.class);
						searchList2.addFilterQuery("pk_indexed_long:" + pk2);
						searchList2.setRows(1);
						searchList2.initDeepSearchList(siteRequest);
						PageDesign o2 = searchList2.getList().stream().findFirst().orElse(null);

						if(o2 != null) {
							PageDesignEnUSGenApiServiceImpl service = new PageDesignEnUSGenApiServiceImpl(siteRequest.getSiteContext_());
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForPageDesign(siteContext, siteRequest.getOperationRequest(), new JsonObject());
							ApiRequest apiRequest2 = new ApiRequest();
							apiRequest2.setRows(1);
							apiRequest2.setNumFound(1l);
							apiRequest2.setNumPATCH(0L);
							apiRequest2.initDeepApiRequest(siteRequest2);
							siteRequest2.setApiRequest_(apiRequest2);
							siteRequest2.getVertx().eventBus().publish("websocketPageDesign", JsonObject.mapFrom(apiRequest2).toString());

							o2.setPk(pk2);
							o2.setSiteRequest_(siteRequest2);
							futures.add(
								service.patchPageDesignFuture(o2, false, a -> {
									if(a.succeeded()) {
									} else {
										LOGGER.info(String.format("PageDesign %s failed. ", pk2));
										eventHandler.handle(Future.failedFuture(a.cause()));
									}
								})
							);
						}
					}

					if("HtmlPart".equals(classSimpleName2) && pk2 != null) {
						SearchList<HtmlPart> searchList2 = new SearchList<HtmlPart>();
						searchList2.setStore(true);
						searchList2.setQuery("*:*");
						searchList2.setC(HtmlPart.class);
						searchList2.addFilterQuery("pk_indexed_long:" + pk2);
						searchList2.setRows(1);
						searchList2.initDeepSearchList(siteRequest);
						HtmlPart o2 = searchList2.getList().stream().findFirst().orElse(null);

						if(o2 != null) {
							HtmlPartEnUSGenApiServiceImpl service = new HtmlPartEnUSGenApiServiceImpl(siteRequest.getSiteContext_());
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForPageDesign(siteContext, siteRequest.getOperationRequest(), new JsonObject());
							ApiRequest apiRequest2 = new ApiRequest();
							apiRequest2.setRows(1);
							apiRequest2.setNumFound(1l);
							apiRequest2.setNumPATCH(0L);
							apiRequest2.initDeepApiRequest(siteRequest2);
							siteRequest2.setApiRequest_(apiRequest2);
							siteRequest2.getVertx().eventBus().publish("websocketHtmlPart", JsonObject.mapFrom(apiRequest2).toString());

							o2.setPk(pk2);
							o2.setSiteRequest_(siteRequest2);
							futures.add(
								service.patchHtmlPartFuture(o2, false, a -> {
									if(a.succeeded()) {
									} else {
										LOGGER.info(String.format("HtmlPart %s failed. ", pk2));
										eventHandler.handle(Future.failedFuture(a.cause()));
									}
								})
							);
						}
					}
				}

				CompositeFuture.all(futures).onComplete(a -> {
					if(a.succeeded()) {
						PageDesignEnUSApiServiceImpl service = new PageDesignEnUSApiServiceImpl(siteRequest.getSiteContext_());
						List<Future> futures2 = new ArrayList<>();
						for(PageDesign o2 : searchList.getList()) {
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForPageDesign(siteContext, siteRequest.getOperationRequest(), new JsonObject());
							o2.setSiteRequest_(siteRequest2);
							futures2.add(
								service.patchPageDesignFuture(o2, false, b -> {
									if(b.succeeded()) {
									} else {
										LOGGER.info(String.format("PageDesign %s failed. ", o2.getPk()));
										eventHandler.handle(Future.failedFuture(b.cause()));
									}
								})
							);
						}

						CompositeFuture.all(futures2).onComplete(b -> {
							if(b.succeeded()) {
								eventHandler.handle(Future.succeededFuture());
							} else {
								LOGGER.error("Refresh relations failed. ", b.cause());
								errorPageDesign(siteRequest, eventHandler, b);
							}
						});
					} else {
						LOGGER.error("Refresh relations failed. ", a.cause());
						errorPageDesign(siteRequest, eventHandler, a);
					}
				});
			} else {
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOGGER.error(String.format("refreshPageDesign failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}
}
