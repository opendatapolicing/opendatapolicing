package com.opendatapolicing.enus.design;

import com.opendatapolicing.enus.design.PageDesignEnUSApiServiceImpl;
import com.opendatapolicing.enus.design.PageDesign;
import com.opendatapolicing.enus.design.PageDesignEnUSApiServiceImpl;
import com.opendatapolicing.enus.design.PageDesign;
import com.opendatapolicing.enus.html.part.HtmlPartEnUSApiServiceImpl;
import com.opendatapolicing.enus.html.part.HtmlPart;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.opendatapolicing.enus.user.SiteUser;
import com.opendatapolicing.enus.request.api.ApiRequest;
import com.opendatapolicing.enus.search.SearchResult;
import com.opendatapolicing.enus.vertx.MailVerticle;
import com.opendatapolicing.enus.config.ConfigKeys;
import com.opendatapolicing.enus.cluster.BaseApiServiceImpl;
import org.apache.solr.client.solrj.SolrClient;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.eventbus.EventBus;
import io.vertx.pgclient.PgPool;
import io.vertx.ext.auth.authorization.AuthorizationProvider;
import io.vertx.core.eventbus.DeliveryOptions;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.net.URLEncoder;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.CompositeFuture;
import io.vertx.core.http.HttpHeaders;
import org.apache.http.client.utils.URLEncodedUtils;
import java.nio.charset.Charset;
import org.apache.http.NameValuePair;
import io.vertx.ext.web.api.service.ServiceRequest;
import io.vertx.ext.web.api.service.ServiceResponse;
import io.vertx.ext.auth.User;
import java.util.Optional;
import java.util.stream.Stream;
import java.net.URLDecoder;
import org.apache.solr.util.DateMathParser;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.client.solrj.response.PivotField;
import org.apache.solr.client.solrj.response.RangeFacet;
import org.apache.solr.client.solrj.response.FacetField;
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
public class PageDesignEnUSGenApiServiceImpl extends BaseApiServiceImpl implements PageDesignEnUSGenApiService {

	protected static final Logger LOG = LoggerFactory.getLogger(PageDesignEnUSGenApiServiceImpl.class);

	public PageDesignEnUSGenApiServiceImpl(EventBus eventBus, JsonObject config, WorkerExecutor workerExecutor, PgPool pgPool, SolrClient solrClient, OAuth2Auth oauth2AuthenticationProvider, AuthorizationProvider authorizationProvider) {
		super(eventBus, config, workerExecutor, pgPool, solrClient, oauth2AuthenticationProvider, authorizationProvider);
	}

	// PUTImport //

	@Override
	public void putimportPageDesign(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("putimportPageDesign started. "));
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/page-design/import");
					siteRequest.setRequestMethod("PUTImport");

					List<String> roles = Arrays.asList("SiteAdmin");
					if(
							!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
							&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
							) {
						eventHandler.handle(Future.succeededFuture(
							new ServiceResponse(401, "UNAUTHORIZED", 
								Buffer.buffer().appendString(
									new JsonObject()
										.put("errorCode", "401")
										.put("errorMessage", "roles required: " + String.join(", ", roles))
										.encodePrettily()
									), MultiMap.caseInsensitiveMultiMap()
							)
						));
					} else {
						response200PUTImportPageDesign(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
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
											eventBus.publish("websocketPageDesign", JsonObject.mapFrom(apiRequest).toString());
											varsPageDesign(siteRequest, d -> {
												if(d.succeeded()) {
													listPUTImportPageDesign(apiRequest, siteRequest).onSuccess(e -> {
														response200PUTImportPageDesign(siteRequest, f -> {
															if(f.succeeded()) {
																LOG.info(String.format("putimportPageDesign succeeded. "));
																blockingCodeHandler.handle(Future.succeededFuture(f.result()));
															} else {
																LOG.error(String.format("putimportPageDesign failed. ", f.cause()));
																error(siteRequest, null, f);
															}
														});
													}).onFailure(ex -> {
														LOG.error(String.format("putimportPageDesign failed. ", ex));
														error(siteRequest, null, Future.failedFuture(ex));
													});
												} else {
													LOG.error(String.format("putimportPageDesign failed. ", d.cause()));
													error(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putimportPageDesign failed. ", ex));
											error(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putimportPageDesign failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putimportPageDesign failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("putimportPageDesign failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("putimportPageDesign failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public Future<Void> listPUTImportPageDesign(ApiRequest apiRequest, SiteRequestEnUS siteRequest) {
		Promise<Void> promise = Promise.promise();
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				json.put("created", json.getValue("created"));

				SiteRequestEnUS siteRequest2 = siteRequest.copy();
				siteRequest2.setJsonObject(json);
				siteRequest2.setApiRequest_(apiRequest);
				siteRequest2.setRequestVars(siteRequest.getRequestVars());

				SearchList<PageDesign> searchList = new SearchList<PageDesign>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(PageDesign.class);
				searchList.addFilterQuery("inheritPk_indexed_string:" + ClientUtils.escapeQueryChars(json.getString("pk")));
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
							patchPageDesignFuture(o, true).onFailure(ex -> {
								LOG.error(String.format("listPUTImportPageDesign failed. ", ex));
							})
						);
					}
				} else {
					futures.add(
						postPageDesignFuture(siteRequest2, true).onFailure(ex -> {
							LOG.error(String.format("listPUTImportPageDesign failed. ", ex));
						})
					);
				}
			});
			CompositeFuture.all(futures).onSuccess(a -> {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
				promise.complete();
			}).onFailure(ex -> {
				LOG.error(String.format("listPUTImportPageDesign failed. ", ex));
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("listPUTImportPageDesign failed. ", ex));
			promise.fail(ex);
		}
		return promise.future();
	}

	public void response200PUTImportPageDesign(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTImportPageDesign failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTMerge //

	@Override
	public void putmergePageDesign(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("putmergePageDesign started. "));
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/page-design/merge");
					siteRequest.setRequestMethod("PUTMerge");

					List<String> roles = Arrays.asList("SiteAdmin");
					if(
							!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
							&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
							) {
						eventHandler.handle(Future.succeededFuture(
							new ServiceResponse(401, "UNAUTHORIZED", 
								Buffer.buffer().appendString(
									new JsonObject()
										.put("errorCode", "401")
										.put("errorMessage", "roles required: " + String.join(", ", roles))
										.encodePrettily()
									), MultiMap.caseInsensitiveMultiMap()
							)
						));
					} else {
						response200PUTMergePageDesign(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
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
											eventBus.publish("websocketPageDesign", JsonObject.mapFrom(apiRequest).toString());
											varsPageDesign(siteRequest, d -> {
												if(d.succeeded()) {
													listPUTMergePageDesign(apiRequest, siteRequest).onSuccess(e -> {
														response200PUTMergePageDesign(siteRequest, f -> {
															if(f.succeeded()) {
																LOG.info(String.format("putmergePageDesign succeeded. "));
																blockingCodeHandler.handle(Future.succeededFuture(f.result()));
															} else {
																LOG.error(String.format("putmergePageDesign failed. ", f.cause()));
																error(siteRequest, null, f);
															}
														});
													}).onFailure(ex -> {
														LOG.error(String.format("putmergePageDesign failed. ", ex));
														error(siteRequest, null, Future.failedFuture(ex));
													});
												} else {
													LOG.error(String.format("putmergePageDesign failed. ", d.cause()));
													error(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putmergePageDesign failed. ", ex));
											error(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putmergePageDesign failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putmergePageDesign failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("putmergePageDesign failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("putmergePageDesign failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public Future<Void> listPUTMergePageDesign(ApiRequest apiRequest, SiteRequestEnUS siteRequest) {
		Promise<Void> promise = Promise.promise();
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				SiteRequestEnUS siteRequest2 = siteRequest.copy();
				siteRequest2.setJsonObject(json);
				siteRequest2.setApiRequest_(apiRequest);
				siteRequest2.setRequestVars(siteRequest.getRequestVars());

				SearchList<PageDesign> searchList = new SearchList<PageDesign>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(PageDesign.class);
				searchList.addFilterQuery("pk_indexed_long:" + ClientUtils.escapeQueryChars(json.getString("pk")));
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
							patchPageDesignFuture(o, false).onFailure(ex -> {
								LOG.error(String.format("listPUTMergePageDesign failed. ", ex));
							})
						);
					}
				} else {
					futures.add(
						postPageDesignFuture(siteRequest2, false).onFailure(ex -> {
							LOG.error(String.format("listPUTMergePageDesign failed. ", ex));
						})
					);
				}
			});
			CompositeFuture.all(futures).onSuccess(a -> {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
				promise.complete();
			}).onFailure(ex -> {
				LOG.error(String.format("listPUTMergePageDesign failed. ", ex));
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("listPUTMergePageDesign failed. ", ex));
			promise.fail(ex);
		}
		return promise.future();
	}

	public void putmergePageDesignResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PUTMergePageDesign(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("putmergePageDesignResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("putmergePageDesignResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTMergePageDesign(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTMergePageDesign failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTCopy //

	@Override
	public void putcopyPageDesign(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("putcopyPageDesign started. "));
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/page-design/copy");
					siteRequest.setRequestMethod("PUTCopy");

					List<String> roles = Arrays.asList("SiteAdmin");
					if(
							!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
							&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
							) {
						eventHandler.handle(Future.succeededFuture(
							new ServiceResponse(401, "UNAUTHORIZED", 
								Buffer.buffer().appendString(
									new JsonObject()
										.put("errorCode", "401")
										.put("errorMessage", "roles required: " + String.join(", ", roles))
										.encodePrettily()
									), MultiMap.caseInsensitiveMultiMap()
							)
						));
					} else {
						putcopyPageDesignResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
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
													eventBus.publish("websocketPageDesign", JsonObject.mapFrom(apiRequest).toString());
													try {
														listPUTCopyPageDesign(apiRequest, listPageDesign, e -> {
															if(e.succeeded()) {
																putcopyPageDesignResponse(siteRequest, f -> {
																	if(f.succeeded()) {
																		LOG.info(String.format("putcopyPageDesign succeeded. "));
																		blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																	} else {
																		LOG.error(String.format("putcopyPageDesign failed. ", f.cause()));
																		error(siteRequest, null, f);
																	}
																});
															} else {
																LOG.error(String.format("putcopyPageDesign failed. ", e.cause()));
																error(siteRequest, null, e);
															}
														});
													} catch(Exception ex) {
														LOG.error(String.format("putcopyPageDesign failed. ", ex));
														error(siteRequest, null, Future.failedFuture(ex));
													}
												} else {
													LOG.error(String.format("putcopyPageDesign failed. ", d.cause()));
													error(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putcopyPageDesign failed. ", ex));
											error(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putcopyPageDesign failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putcopyPageDesign failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("putcopyPageDesign failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("putcopyPageDesign failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public void listPUTCopyPageDesign(ApiRequest apiRequest, SearchList<PageDesign> listPageDesign, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
		listPageDesign.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = siteRequest.copy();
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				putcopyPageDesignFuture(siteRequest2, JsonObject.mapFrom(o)).onFailure(ex -> {
					LOG.error(String.format("listPUTCopyPageDesign failed. ", ex));
					error(siteRequest, eventHandler, Future.failedFuture(ex));
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
				LOG.error(String.format("listPUTCopyPageDesign failed. ", a.cause()));
				error(listPageDesign.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<PageDesign> putcopyPageDesignFuture(SiteRequestEnUS siteRequest, JsonObject jsonObject) {
		Promise<PageDesign> promise = Promise.promise();

		try {

			jsonObject.put("saves", Optional.ofNullable(jsonObject.getJsonArray("saves")).orElse(new JsonArray()));
			JsonObject jsonPatch = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonObject("patch")).orElse(new JsonObject());
			jsonPatch.stream().forEach(o -> {
				if(o.getValue() == null)
					jsonObject.remove(o.getKey());
				else
					jsonObject.put(o.getKey(), o.getValue());
				if(!jsonObject.getJsonArray("saves").contains(o.getKey()))
					jsonObject.getJsonArray("saves").add(o.getKey());
			});

			pgPool.withTransaction(sqlConnection -> {
				Promise<PageDesign> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				createPageDesign(siteRequest, a -> {
					if(a.succeeded()) {
						PageDesign pageDesign = a.result();
						sqlPUTCopyPageDesign(pageDesign, jsonObject, b -> {
							if(b.succeeded()) {
								definePageDesign(pageDesign, c -> {
									if(c.succeeded()) {
										attributePageDesign(pageDesign, d -> {
											if(d.succeeded()) {
												indexPageDesign(pageDesign, e -> {
													if(e.succeeded()) {
														promise1.complete(pageDesign);
													} else {
														LOG.error(String.format("putcopyPageDesignFuture failed. ", e.cause()));
														promise1.fail(e.cause());
													}
												});
											} else {
												LOG.error(String.format("putcopyPageDesignFuture failed. ", d.cause()));
												promise1.fail(d.cause());
											}
										});
									} else {
										LOG.error(String.format("putcopyPageDesignFuture failed. ", c.cause()));
										promise1.fail(c.cause());
									}
								});
							} else {
								LOG.error(String.format("putcopyPageDesignFuture failed. ", b.cause()));
								promise1.fail(b.cause());
							}
						});
					} else {
						LOG.error(String.format("putcopyPageDesignFuture failed. ", a.cause()));
						promise1.fail(a.cause());
					}
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, Future.failedFuture(ex));
			}).compose(pageDesign -> {
				Promise<PageDesign> promise2 = Promise.promise();
				refreshPageDesign(pageDesign, a -> {
					if(a.succeeded()) {
						ApiRequest apiRequest = siteRequest.getApiRequest_();
						if(apiRequest != null) {
							apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
							pageDesign.apiRequestPageDesign();
							eventBus.publish("websocketPageDesign", JsonObject.mapFrom(apiRequest).toString());
						}
						promise.complete(pageDesign);
					} else {
						LOG.error(String.format("putcopyPageDesignFuture failed. ", a.cause()));
						promise2.fail(a.cause());
					}
				});
				return promise2.future();
			}).onSuccess(pageDesign -> {
				promise.complete(pageDesign);
				LOG.info(String.format("putcopyPageDesignFuture succeeded. "));
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, promise.future());
			});
		} catch(Exception ex) {
			LOG.error(String.format("putcopyPageDesignFuture failed. "), ex);
			promise.fail(ex);
			error(siteRequest, null, promise.future());
		}
		return promise.future();
	}

	public void sqlPUTCopyPageDesign(PageDesign o, JsonObject jsonObject, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE PageDesign SET ");
			List<Object> bParams = new ArrayList<Object>();
			PageDesign o2 = new PageDesign();
			o2.setSiteRequest_(siteRequest);
			Long pk = o.getPk();
			List<Future> futures = new ArrayList<>();

			if(jsonObject != null) {
				JsonArray entityVars = jsonObject.getJsonArray("saves");
				for(Integer i = 0; i < entityVars.size(); i++) {
					String entityVar = entityVars.getString(i);
					switch(entityVar) {
					case "inheritPk":
						o2.setInheritPk(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("inheritPk=$" + num);
						num++;
						bParams.add(o2.sqlInheritPk());
						break;
					case "childDesignKeys":
						{
							for(Long l : Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray()).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
								futures.add(Future.future(a -> {
									sqlConnection.preparedQuery("INSERT INTO PageDesignChildDesignKeys_PageDesignParentDesignKeys(pk1, pk2) values($1, $2)")
											.execute(Tuple.of(pk, l)
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
						}
						break;
					case "parentDesignKeys":
						{
							for(Long l : Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray()).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
								futures.add(Future.future(a -> {
									sqlConnection.preparedQuery("INSERT INTO PageDesignChildDesignKeys_PageDesignParentDesignKeys(pk1, pk2) values($1, $2)")
											.execute(Tuple.of(l, pk)
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
						}
						break;
					case "htmlPartKeys":
						{
							for(Long l : Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray()).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
								futures.add(Future.future(a -> {
									sqlConnection.preparedQuery("INSERT INTO PageDesignHtmlPartKeys_HtmlPartPageDesignKeys(pk1, pk2) values($1, $2)")
											.execute(Tuple.of(pk, l)
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
						}
						break;
					case "pageDesignCompleteName":
						o2.setPageDesignCompleteName(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("pageDesignCompleteName=$" + num);
						num++;
						bParams.add(o2.sqlPageDesignCompleteName());
						break;
					case "designHidden":
						o2.setDesignHidden(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("designHidden=$" + num);
						num++;
						bParams.add(o2.sqlDesignHidden());
						break;
					case "pageContentType":
						o2.setPageContentType(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("pageContentType=$" + num);
						num++;
						bParams.add(o2.sqlPageContentType());
						break;
					}
				}
			}
			bSql.append(" WHERE pk=$" + num);
			if(bParams.size() > 0) {
			bParams.add(pk);
			num++;
				futures.add(Future.future(a -> {
					sqlConnection.preparedQuery(bSql.toString())
							.execute(Tuple.tuple(bParams)
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));
			}
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture());
				} else {
					LOG.error(String.format("sqlPUTCopyPageDesign failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPUTCopyPageDesign failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void putcopyPageDesignResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PUTCopyPageDesign(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("putcopyPageDesignResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("putcopyPageDesignResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTCopyPageDesign(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTCopyPageDesign failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// POST //

	@Override
	public void postPageDesign(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("postPageDesign started. "));
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/page-design");
					siteRequest.setRequestMethod("POST");

					List<String> roles = Arrays.asList("SiteAdmin");
					if(
							!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
							&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
							) {
						eventHandler.handle(Future.succeededFuture(
							new ServiceResponse(401, "UNAUTHORIZED", 
								Buffer.buffer().appendString(
									new JsonObject()
										.put("errorCode", "401")
										.put("errorMessage", "roles required: " + String.join(", ", roles))
										.encodePrettily()
									), MultiMap.caseInsensitiveMultiMap()
							)
						));
					} else {
						ApiRequest apiRequest = new ApiRequest();
						apiRequest.setRows(1);
						apiRequest.setNumFound(1L);
						apiRequest.setNumPATCH(0L);
						apiRequest.initDeepApiRequest(siteRequest);
						siteRequest.setApiRequest_(apiRequest);
						eventBus.publish("websocketPageDesign", JsonObject.mapFrom(apiRequest).toString());
						postPageDesignFuture(siteRequest, false).onSuccess(pageDesign -> {
							apiRequest.setPk(pageDesign.getPk());
							postPageDesignResponse(pageDesign, d -> {
								if(d.succeeded()) {
									eventHandler.handle(Future.succeededFuture(d.result()));
									LOG.info(String.format("postPageDesign succeeded. "));
								} else {
									LOG.error(String.format("postPageDesign failed. ", d.cause()));
									error(siteRequest, eventHandler, d);
								}
							});
						}).onFailure(ex -> {
							LOG.error(String.format("postPageDesign failed. ", Future.failedFuture(ex)));
							error(siteRequest, eventHandler, Future.failedFuture(ex));
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("postPageDesign failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("postPageDesign failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("postPageDesign failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public Future<PageDesign> postPageDesignFuture(SiteRequestEnUS siteRequest, Boolean inheritPk) {
		Promise<PageDesign> promise = Promise.promise();

		try {
			pgPool.withTransaction(sqlConnection -> {
				Promise<PageDesign> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				createPageDesign(siteRequest, a -> {
					if(a.succeeded()) {
						PageDesign pageDesign = a.result();
						sqlPOSTPageDesign(pageDesign, inheritPk, b -> {
							if(b.succeeded()) {
								definePageDesign(pageDesign, c -> {
									if(c.succeeded()) {
										attributePageDesign(pageDesign, d -> {
											if(d.succeeded()) {
												indexPageDesign(pageDesign, e -> {
													if(e.succeeded()) {
														promise1.complete(pageDesign);
													} else {
														LOG.error(String.format("postPageDesignFuture failed. ", e.cause()));
														promise1.fail(e.cause());
													}
												});
											} else {
												LOG.error(String.format("postPageDesignFuture failed. ", d.cause()));
												promise1.fail(d.cause());
											}
										});
									} else {
										LOG.error(String.format("postPageDesignFuture failed. ", c.cause()));
										promise1.fail(c.cause());
									}
								});
							} else {
								LOG.error(String.format("postPageDesignFuture failed. ", b.cause()));
								promise1.fail(b.cause());
							}
						});
					} else {
						LOG.error(String.format("postPageDesignFuture failed. ", a.cause()));
						promise1.fail(a.cause());
					}
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, Future.failedFuture(ex));
			}).compose(pageDesign -> {
				Promise<PageDesign> promise2 = Promise.promise();
				refreshPageDesign(pageDesign, a -> {
					if(a.succeeded()) {
						ApiRequest apiRequest = siteRequest.getApiRequest_();
						if(apiRequest != null) {
							apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
							pageDesign.apiRequestPageDesign();
							eventBus.publish("websocketPageDesign", JsonObject.mapFrom(apiRequest).toString());
						}
						promise.complete(pageDesign);
					} else {
						LOG.error(String.format("postPageDesignFuture failed. ", a.cause()));
						promise2.fail(a.cause());
					}
				});
				return promise2.future();
			}).onSuccess(pageDesign -> {
				promise.complete(pageDesign);
				LOG.info(String.format("postPageDesignFuture succeeded. "));
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, promise.future());
			});
		} catch(Exception ex) {
			LOG.error(String.format("postPageDesignFuture failed. "), ex);
			promise.fail(ex);
			error(siteRequest, null, promise.future());
		}
		return promise.future();
	}

	public void sqlPOSTPageDesign(PageDesign o, Boolean inheritPk, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE PageDesign SET ");
			List<Object> bParams = new ArrayList<Object>();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			PageDesign o2 = new PageDesign();
			o2.setSiteRequest_(siteRequest);
			List<Future> futures = new ArrayList<>();

			if(jsonObject != null) {
				Set<String> entityVars = jsonObject.fieldNames();
				for(String entityVar : entityVars) {
					switch(entityVar) {
					case "inheritPk":
						o2.setInheritPk(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("inheritPk=$" + num);
						num++;
						bParams.add(o2.sqlInheritPk());
						break;
					case "childDesignKeys":
						for(Long l : Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray()).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							if(l != null) {
								SearchList<PageDesign> searchList = new SearchList<PageDesign>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(PageDesign.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("INSERT INTO PageDesignChildDesignKeys_PageDesignParentDesignKeys(pk1, pk2) values($1, $2)")
												.execute(Tuple.of(pk, l2)
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
								SearchList<PageDesign> searchList = new SearchList<PageDesign>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(PageDesign.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("INSERT INTO PageDesignChildDesignKeys_PageDesignParentDesignKeys(pk1, pk2) values($1, $2)")
												.execute(Tuple.of(l2, pk)
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
								SearchList<HtmlPart> searchList = new SearchList<HtmlPart>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(HtmlPart.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("INSERT INTO PageDesignHtmlPartKeys_HtmlPartPageDesignKeys(pk1, pk2) values($1, $2)")
												.execute(Tuple.of(pk, l2)
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
						o2.setPageDesignCompleteName(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("pageDesignCompleteName=$" + num);
						num++;
						bParams.add(o2.sqlPageDesignCompleteName());
						break;
					case "designHidden":
						o2.setDesignHidden(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("designHidden=$" + num);
						num++;
						bParams.add(o2.sqlDesignHidden());
						break;
					case "pageContentType":
						o2.setPageContentType(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("pageContentType=$" + num);
						num++;
						bParams.add(o2.sqlPageContentType());
						break;
					}
				}
			}
			bSql.append(" WHERE pk=$" + num);
			if(bParams.size() > 0) {
			bParams.add(pk);
			num++;
				futures.add(Future.future(a -> {
					sqlConnection.preparedQuery(bSql.toString())
							.execute(Tuple.tuple(bParams)
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));
			}
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture());
				} else {
					LOG.error(String.format("sqlPOSTPageDesign failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPOSTPageDesign failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void postPageDesignResponse(PageDesign pageDesign, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = pageDesign.getSiteRequest_();
		try {
			response200POSTPageDesign(pageDesign, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("postPageDesignResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("postPageDesignResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200POSTPageDesign(PageDesign o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200POSTPageDesign failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchPageDesign(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("patchPageDesign started. "));
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/page-design");
					siteRequest.setRequestMethod("PATCH");

					List<String> roles = Arrays.asList("SiteAdmin");
					if(
							!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
							&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
							) {
						eventHandler.handle(Future.succeededFuture(
							new ServiceResponse(401, "UNAUTHORIZED", 
								Buffer.buffer().appendString(
									new JsonObject()
										.put("errorCode", "401")
										.put("errorMessage", "roles required: " + String.join(", ", roles))
										.encodePrettily()
									), MultiMap.caseInsensitiveMultiMap()
							)
						));
					} else {
						patchPageDesignResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
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
														LOG.error(message);
														error(siteRequest, eventHandler, Future.failedFuture(message));
													} else {

														ApiRequest apiRequest = new ApiRequest();
														apiRequest.setRows(listPageDesign.getRows());
														apiRequest.setNumFound(listPageDesign.getQueryResponse().getResults().getNumFound());
														apiRequest.setNumPATCH(0L);
														apiRequest.initDeepApiRequest(siteRequest);
														siteRequest.setApiRequest_(apiRequest);
														eventBus.publish("websocketPageDesign", JsonObject.mapFrom(apiRequest).toString());
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
																			LOG.info(String.format("patchPageDesign succeeded. "));
																			blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																		} else {
																			LOG.error(String.format("patchPageDesign failed. ", f.cause()));
																			error(siteRequest, null, f);
																		}
																	});
																} else {
																	LOG.error(String.format("patchPageDesign failed. ", e.cause()));
																	error(siteRequest, null, e);
																}
															});
														} catch(Exception ex) {
															LOG.error(String.format("patchPageDesign failed. ", ex));
															error(siteRequest, null, Future.failedFuture(ex));
														}
													}
												} else {
													LOG.error(String.format("patchPageDesign failed. ", d.cause()));
													error(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("patchPageDesign failed. ", ex));
											error(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("patchPageDesign failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("patchPageDesign failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("patchPageDesign failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("patchPageDesign failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public void listPATCHPageDesign(ApiRequest apiRequest, SearchList<PageDesign> listPageDesign, String dt, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
		listPageDesign.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = siteRequest.copy();
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				patchPageDesignFuture(o, false).onFailure(ex -> {
					error(siteRequest2, eventHandler, Future.failedFuture(ex));
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
				LOG.error(String.format("listPATCHPageDesign failed. ", a.cause()));
				error(listPageDesign.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<PageDesign> patchPageDesignFuture(PageDesign o, Boolean inheritPk) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		Promise<PageDesign> promise = Promise.promise();

		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			if(apiRequest != null && apiRequest.getNumFound() == 1L) {
				apiRequest.setOriginal(o);
				apiRequest.setPk(o.getPk());
			}
			pgPool.withTransaction(sqlConnection -> {
				Promise<PageDesign> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				sqlPATCHPageDesign(o, inheritPk, a -> {
					if(a.succeeded()) {
						PageDesign pageDesign = a.result();
						definePageDesign(pageDesign, c -> {
							if(c.succeeded()) {
								attributePageDesign(pageDesign, d -> {
									if(d.succeeded()) {
										indexPageDesign(pageDesign, e -> {
											if(e.succeeded()) {
												promise1.complete(pageDesign);
											} else {
												LOG.error(String.format("patchPageDesignFuture failed. ", e.cause()));
												promise1.fail(e.cause());
											}
										});
									} else {
										LOG.error(String.format("patchPageDesignFuture failed. ", d.cause()));
										promise1.fail(d.cause());
									}
								});
							} else {
								LOG.error(String.format("patchPageDesignFuture failed. ", c.cause()));
								promise1.fail(c.cause());
							}
						});
					} else {
						LOG.error(String.format("patchPageDesignFuture failed. ", a.cause()));
								promise1.fail(a.cause());
					}
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, Future.failedFuture(ex));
			}).compose(pageDesign -> {
				Promise<PageDesign> promise2 = Promise.promise();
				refreshPageDesign(pageDesign, a -> {
					if(a.succeeded()) {
						if(apiRequest != null) {
							apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
							pageDesign.apiRequestPageDesign();
							eventBus.publish("websocketPageDesign", JsonObject.mapFrom(apiRequest).toString());
						}
						promise.complete(pageDesign);
					} else {
						LOG.error(String.format("patchPageDesignFuture failed. ", a.cause()));
						promise2.fail(a.cause());
					}
				});
				return promise2.future();
			}).onSuccess(pageDesign -> {
				promise.complete(pageDesign);
				LOG.info(String.format("patchPageDesignFuture succeeded. "));
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, promise.future());
			});
		} catch(Exception ex) {
			LOG.error(String.format("patchPageDesignFuture failed. "), ex);
			promise.fail(ex);
			error(siteRequest, null, promise.future());
		}
		return promise.future();
	}

	public void sqlPATCHPageDesign(PageDesign o, Boolean inheritPk, Handler<AsyncResult<PageDesign>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE PageDesign SET ");
			List<Object> bParams = new ArrayList<Object>();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			PageDesign o2 = new PageDesign();
			o2.setSiteRequest_(siteRequest);
			List<Future> futures = new ArrayList<>();

			for(String methodName : methodNames) {
				switch(methodName) {
					case "setInheritPk":
							o2.setInheritPk(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("inheritPk=$" + num);
							num++;
							bParams.add(o2.sqlInheritPk());
						break;
					case "addChildDesignKeys":
						{
							Long l = Long.parseLong(jsonObject.getString(methodName));
							if(l != null) {
								SearchList<PageDesign> searchList = new SearchList<PageDesign>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(PageDesign.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && !o.getChildDesignKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("INSERT INTO PageDesignChildDesignKeys_PageDesignParentDesignKeys(pk1, pk2) values($1, $2)")
												.execute(Tuple.of(pk, l2)
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
									SearchList<PageDesign> searchList = new SearchList<PageDesign>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(PageDesign.class);
									searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null && !o.getChildDesignKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("INSERT INTO PageDesignChildDesignKeys_PageDesignParentDesignKeys(pk1, pk2) values($1, $2)")
												.execute(Tuple.of(pk, l2)
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
									SearchList<PageDesign> searchList = new SearchList<PageDesign>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(PageDesign.class);
									searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null)
										setChildDesignKeysValues2.add(l2);
									if(l2 != null && !o.getChildDesignKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("INSERT INTO PageDesignChildDesignKeys_PageDesignParentDesignKeys(pk1, pk2) values($1, $2)")
												.execute(Tuple.of(pk, l2)
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
										sqlConnection.preparedQuery("DELETE FROM PageDesignChildDesignKeys_PageDesignParentDesignKeys WHERE pk1=$1 AND pk2=$2")
												.execute(Tuple.of(pk, l)
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
								SearchList<PageDesign> searchList = new SearchList<PageDesign>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(PageDesign.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && o.getChildDesignKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("DELETE FROM PageDesignChildDesignKeys_PageDesignParentDesignKeys WHERE pk1=$1 AND pk2=$2")
												.execute(Tuple.of(pk, l2)
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
								SearchList<PageDesign> searchList = new SearchList<PageDesign>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(PageDesign.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && !o.getParentDesignKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("INSERT INTO PageDesignChildDesignKeys_PageDesignParentDesignKeys(pk1, pk2) values($1, $2)")
												.execute(Tuple.of(l2, pk)
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
									SearchList<PageDesign> searchList = new SearchList<PageDesign>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(PageDesign.class);
									searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null && !o.getParentDesignKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("INSERT INTO PageDesignChildDesignKeys_PageDesignParentDesignKeys(pk1, pk2) values($1, $2)")
												.execute(Tuple.of(l2, pk)
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
									SearchList<PageDesign> searchList = new SearchList<PageDesign>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(PageDesign.class);
									searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null)
										setParentDesignKeysValues2.add(l2);
									if(l2 != null && !o.getParentDesignKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("INSERT INTO PageDesignChildDesignKeys_PageDesignParentDesignKeys(pk1, pk2) values($1, $2)")
												.execute(Tuple.of(l2, pk)
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
										sqlConnection.preparedQuery("DELETE FROM PageDesignChildDesignKeys_PageDesignParentDesignKeys WHERE pk1=$1 AND pk2=$2")
												.execute(Tuple.of(l, pk)
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
								SearchList<PageDesign> searchList = new SearchList<PageDesign>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(PageDesign.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && o.getParentDesignKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("DELETE FROM PageDesignChildDesignKeys_PageDesignParentDesignKeys WHERE pk1=$1 AND pk2=$2")
												.execute(Tuple.of(l2, pk)
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
								SearchList<HtmlPart> searchList = new SearchList<HtmlPart>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(HtmlPart.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && !o.getHtmlPartKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("INSERT INTO PageDesignHtmlPartKeys_HtmlPartPageDesignKeys(pk1, pk2) values($1, $2)")
												.execute(Tuple.of(pk, l2)
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
									SearchList<HtmlPart> searchList = new SearchList<HtmlPart>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(HtmlPart.class);
									searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null && !o.getHtmlPartKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("INSERT INTO PageDesignHtmlPartKeys_HtmlPartPageDesignKeys(pk1, pk2) values($1, $2)")
												.execute(Tuple.of(pk, l2)
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
									SearchList<HtmlPart> searchList = new SearchList<HtmlPart>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(HtmlPart.class);
									searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null)
										setHtmlPartKeysValues2.add(l2);
									if(l2 != null && !o.getHtmlPartKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("INSERT INTO PageDesignHtmlPartKeys_HtmlPartPageDesignKeys(pk1, pk2) values($1, $2)")
												.execute(Tuple.of(pk, l2)
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
										sqlConnection.preparedQuery("DELETE FROM PageDesignHtmlPartKeys_HtmlPartPageDesignKeys WHERE pk1=$1 AND pk2=$2")
												.execute(Tuple.of(pk, l)
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
								SearchList<HtmlPart> searchList = new SearchList<HtmlPart>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(HtmlPart.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && o.getHtmlPartKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("DELETE FROM PageDesignHtmlPartKeys_HtmlPartPageDesignKeys WHERE pk1=$1 AND pk2=$2")
												.execute(Tuple.of(pk, l2)
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
							o2.setPageDesignCompleteName(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("pageDesignCompleteName=$" + num);
							num++;
							bParams.add(o2.sqlPageDesignCompleteName());
						break;
					case "setDesignHidden":
							o2.setDesignHidden(jsonObject.getBoolean(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("designHidden=$" + num);
							num++;
							bParams.add(o2.sqlDesignHidden());
						break;
					case "setPageContentType":
							o2.setPageContentType(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("pageContentType=$" + num);
							num++;
							bParams.add(o2.sqlPageContentType());
						break;
				}
			}
			bSql.append(" WHERE pk=$" + num);
			if(bParams.size() > 0) {
				bParams.add(pk);
				num++;
				futures.add(Future.future(a -> {
					sqlConnection.preparedQuery(bSql.toString())
							.execute(Tuple.tuple(bParams)
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));
			}
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					PageDesign o3 = new PageDesign();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
					eventHandler.handle(Future.succeededFuture(o3));
				} else {
					LOG.error(String.format("sqlPATCHPageDesign failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPATCHPageDesign failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void patchPageDesignResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PATCHPageDesign(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("patchPageDesignResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("patchPageDesignResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PATCHPageDesign(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PATCHPageDesign failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// GET //

	@Override
	public void getPageDesign(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/page-design/{id}");
					siteRequest.setRequestMethod("GET");
					{
						aSearchPageDesign(siteRequest, false, true, false, "/api/page-design/{id}", "GET", c -> {
							if(c.succeeded()) {
								SearchList<PageDesign> listPageDesign = c.result();
								getPageDesignResponse(listPageDesign, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("getPageDesign succeeded. "));
									} else {
										LOG.error(String.format("getPageDesign failed. ", d.cause()));
										error(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("getPageDesign failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("getPageDesign failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("getPageDesign failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("getPageDesign failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public void getPageDesignResponse(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
		try {
			response200GETPageDesign(listPageDesign, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("getPageDesignResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("getPageDesignResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200GETPageDesign(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
			SolrDocumentList solrDocuments = listPageDesign.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listPageDesign.getList().stream().findFirst().orElse(null));
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200GETPageDesign failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// Search //

	@Override
	public void searchPageDesign(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/page-design");
					siteRequest.setRequestMethod("Search");
					{
						aSearchPageDesign(siteRequest, false, true, false, "/api/page-design", "Search", c -> {
							if(c.succeeded()) {
								SearchList<PageDesign> listPageDesign = c.result();
								searchPageDesignResponse(listPageDesign, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("searchPageDesign succeeded. "));
									} else {
										LOG.error(String.format("searchPageDesign failed. ", d.cause()));
										error(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("searchPageDesign failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("searchPageDesign failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("searchPageDesign failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("searchPageDesign failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public void searchPageDesignResponse(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
		try {
			response200SearchPageDesign(listPageDesign, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("searchPageDesignResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("searchPageDesignResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchPageDesign(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<ServiceResponse>> eventHandler) {
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

			List<FacetField> facetFields = responseSearch.getFacetFields();
			if(facetFields != null) {
				JsonObject facetFieldsJson = new JsonObject();
				json.put("facet_fields", facetFieldsJson);
				for(FacetField facetField : facetFields) {
					String facetFieldVar = StringUtils.substringBefore(facetField.getName(), "_indexed_");
					JsonArray facetFieldCountsArray = new JsonArray();
					facetFieldsJson.put(facetFieldVar, facetFieldCountsArray);
					List<FacetField.Count> facetFieldValues = facetField.getValues();
					for(Integer i = 0; i < facetFieldValues.size(); i+= 1) {
						JsonObject countJson = new JsonObject();
						FacetField.Count count = (FacetField.Count)facetFieldValues.get(i);
						countJson.put(count.getName(), count.getCount());
						facetFieldCountsArray.add(countJson);
					}
				}
			}

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
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200SearchPageDesign failed. "), e);
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
	public void adminsearchPageDesign(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/admin/page-design");
					siteRequest.setRequestMethod("AdminSearch");
					{
						aSearchPageDesign(siteRequest, false, true, false, "/api/admin/page-design", "AdminSearch", c -> {
							if(c.succeeded()) {
								SearchList<PageDesign> listPageDesign = c.result();
								adminsearchPageDesignResponse(listPageDesign, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("adminsearchPageDesign succeeded. "));
									} else {
										LOG.error(String.format("adminsearchPageDesign failed. ", d.cause()));
										error(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("adminsearchPageDesign failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("adminsearchPageDesign failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("adminsearchPageDesign failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("adminsearchPageDesign failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public void adminsearchPageDesignResponse(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
		try {
			response200AdminSearchPageDesign(listPageDesign, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("adminsearchPageDesignResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("adminsearchPageDesignResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200AdminSearchPageDesign(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<ServiceResponse>> eventHandler) {
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

			List<FacetField> facetFields = responseSearch.getFacetFields();
			if(facetFields != null) {
				JsonObject facetFieldsJson = new JsonObject();
				json.put("facet_fields", facetFieldsJson);
				for(FacetField facetField : facetFields) {
					String facetFieldVar = StringUtils.substringBefore(facetField.getName(), "_indexed_");
					JsonArray facetFieldCountsArray = new JsonArray();
					facetFieldsJson.put(facetFieldVar, facetFieldCountsArray);
					List<FacetField.Count> facetFieldValues = facetField.getValues();
					for(Integer i = 0; i < facetFieldValues.size(); i+= 1) {
						JsonObject countJson = new JsonObject();
						FacetField.Count count = (FacetField.Count)facetFieldValues.get(i);
						countJson.put(count.getName(), count.getCount());
						facetFieldCountsArray.add(countJson);
					}
				}
			}

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
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200AdminSearchPageDesign failed. "), e);
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
	public void searchpagePageDesignId(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		searchpagePageDesign(serviceRequest, eventHandler);
	}

	@Override
	public void searchpagePageDesign(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/page-design");
					siteRequest.setRequestMethod("SearchPage");
					{
						aSearchPageDesign(siteRequest, false, true, false, "/page-design", "SearchPage", c -> {
							if(c.succeeded()) {
								SearchList<PageDesign> listPageDesign = c.result();
								searchpagePageDesignResponse(listPageDesign, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("searchpagePageDesign succeeded. "));
									} else {
										LOG.error(String.format("searchpagePageDesign failed. ", d.cause()));
										error(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("searchpagePageDesign failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("searchpagePageDesign failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("searchpagePageDesign failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("searchpagePageDesign failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public void searchpagePageDesignPageInit(PageDesignPage page, SearchList<PageDesign> listPageDesign) {
	}
	public void searchpagePageDesignResponse(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200SearchPagePageDesign(listPageDesign, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("searchpagePageDesignResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("searchpagePageDesignResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchPagePageDesign(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<ServiceResponse>> eventHandler) {
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
			eventHandler.handle(Future.succeededFuture(new ServiceResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOG.error(String.format("response200SearchPagePageDesign failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// DesignDisplaySearchPage //

	@Override
	public void designdisplaysearchpagePageDesignId(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		designdisplaysearchpagePageDesign(serviceRequest, eventHandler);
	}

	@Override
	public void designdisplaysearchpagePageDesign(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/page");
					siteRequest.setRequestMethod("DesignDisplaySearchPage");
					{
						aSearchPageDesign(siteRequest, false, true, false, "/page", "DesignDisplaySearchPage", c -> {
							if(c.succeeded()) {
								SearchList<PageDesign> listPageDesign = c.result();
								designdisplaysearchpagePageDesignResponse(listPageDesign, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("designdisplaysearchpagePageDesign succeeded. "));
									} else {
										LOG.error(String.format("designdisplaysearchpagePageDesign failed. ", d.cause()));
										error(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("designdisplaysearchpagePageDesign failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("designdisplaysearchpagePageDesign failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("designdisplaysearchpagePageDesign failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("designdisplaysearchpagePageDesign failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public void designdisplaysearchpagePageDesignPageInit(DesignDisplayPage page, SearchList<PageDesign> listPageDesign) {
	}
	public void designdisplaysearchpagePageDesignResponse(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200DesignDisplaySearchPagePageDesign(listPageDesign, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("designdisplaysearchpagePageDesignResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("designdisplaysearchpagePageDesignResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200DesignDisplaySearchPagePageDesign(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<ServiceResponse>> eventHandler) {
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
			eventHandler.handle(Future.succeededFuture(new ServiceResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOG.error(String.format("response200DesignDisplaySearchPagePageDesign failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// DesignPdfSearchPage //

	@Override
	public void designpdfsearchpagePageDesignId(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		designpdfsearchpagePageDesign(serviceRequest, eventHandler);
	}

	@Override
	public void designpdfsearchpagePageDesign(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/pdf");
					siteRequest.setRequestMethod("DesignPdfSearchPage");
					{
						aSearchPageDesign(siteRequest, false, true, false, "/pdf", "DesignPdfSearchPage", c -> {
							if(c.succeeded()) {
								SearchList<PageDesign> listPageDesign = c.result();
								designpdfsearchpagePageDesignResponse(listPageDesign, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("designpdfsearchpagePageDesign succeeded. "));
									} else {
										LOG.error(String.format("designpdfsearchpagePageDesign failed. ", d.cause()));
										error(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("designpdfsearchpagePageDesign failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("designpdfsearchpagePageDesign failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("designpdfsearchpagePageDesign failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("designpdfsearchpagePageDesign failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public void designpdfsearchpagePageDesignPageInit(DesignPdfPage page, SearchList<PageDesign> listPageDesign) {
	}
	public void designpdfsearchpagePageDesignResponse(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
		try {
			response200DesignPdfSearchPagePageDesign(listPageDesign, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("designpdfsearchpagePageDesignResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("designpdfsearchpagePageDesignResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200DesignPdfSearchPagePageDesign(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<ServiceResponse>> eventHandler) {
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
			eventHandler.handle(Future.succeededFuture(new ServiceResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOG.error(String.format("response200DesignPdfSearchPagePageDesign failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// DesignEmailSearchPage //

	@Override
	public void designemailsearchpagePageDesignId(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		designemailsearchpagePageDesign(serviceRequest, eventHandler);
	}

	@Override
	public void designemailsearchpagePageDesign(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/email");
					siteRequest.setRequestMethod("DesignEmailSearchPage");
					{
						aSearchPageDesign(siteRequest, false, true, false, "/email", "DesignEmailSearchPage", c -> {
							if(c.succeeded()) {
								SearchList<PageDesign> listPageDesign = c.result();
								designemailsearchpagePageDesignResponse(listPageDesign, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("designemailsearchpagePageDesign succeeded. "));
									} else {
										LOG.error(String.format("designemailsearchpagePageDesign failed. ", d.cause()));
										error(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("designemailsearchpagePageDesign failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("designemailsearchpagePageDesign failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("designemailsearchpagePageDesign failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("designemailsearchpagePageDesign failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public void designemailsearchpagePageDesignPageInit(DesignEmailPage page, SearchList<PageDesign> listPageDesign) {
	}
	public void designemailsearchpagePageDesignResponse(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listPageDesign.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200DesignEmailSearchPagePageDesign(listPageDesign, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("designemailsearchpagePageDesignResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("designemailsearchpagePageDesignResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200DesignEmailSearchPagePageDesign(SearchList<PageDesign> listPageDesign, Handler<AsyncResult<ServiceResponse>> eventHandler) {
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
			eventHandler.handle(Future.succeededFuture(new ServiceResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOG.error(String.format("response200DesignEmailSearchPagePageDesign failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// General //

	public void createPageDesign(SiteRequestEnUS siteRequest, Handler<AsyncResult<PageDesign>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			String userId = siteRequest.getUserId();
			Long userKey = siteRequest.getUserKey();
			ZonedDateTime created = Optional.ofNullable(siteRequest.getJsonObject()).map(j -> j.getString("created")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(config.getString("siteZone"))))).orElse(ZonedDateTime.now(ZoneId.of(config.getString("siteZone"))));

			sqlConnection.preparedQuery("INSERT INTO PageDesign(created) VALUES($1) RETURNING pk")
					.collecting(Collectors.toList())
					.execute(Tuple.of(created.toOffsetDateTime())
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
					LOG.error(String.format("createPageDesign failed. ", createAsync.cause()));
					eventHandler.handle(Future.failedFuture(createAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("createPageDesign failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void aSearchPageDesignQ(String uri, String apiMethod, SearchList<PageDesign> searchList, String entityVar, String valueIndexed, String varIndexed) {
		searchList.setQuery(varIndexed + ":" + ("*".equals(valueIndexed) ? valueIndexed : ClientUtils.escapeQueryChars(valueIndexed)));
		if(!"*".equals(entityVar)) {
		}
	}

	public String aSearchPageDesignFq(String uri, String apiMethod, SearchList<PageDesign> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		if(StringUtils.startsWith(valueIndexed, "[")) {
			String[] fqs = StringUtils.substringBefore(StringUtils.substringAfter(valueIndexed, "["), "]").split(" TO ");
			if(fqs.length != 2)
				throw new RuntimeException(String.format("\"%s\" invalid range query. ", valueIndexed));
			String fq1 = fqs[0].equals("*") ? fqs[0] : PageDesign.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[0]);
			String fq2 = fqs[1].equals("*") ? fqs[1] : PageDesign.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[1]);
			 return varIndexed + ":[" + fq1 + " TO " + fq2 + "]";
		} else {
			return varIndexed + ":" + PageDesign.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), valueIndexed);
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

	public void varsPageDesign(SiteRequestEnUS siteRequest, Handler<AsyncResult<SearchList<ServiceResponse>>> eventHandler) {
		try {
			ServiceRequest serviceRequest = siteRequest.getServiceRequest();

			serviceRequest.getParams().getJsonObject("query").forEach(paramRequest -> {
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
					LOG.error(String.format("aSearchPageDesign failed. "), e);
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOG.error(String.format("aSearchPageDesign failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void aSearchPageDesign(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, Handler<AsyncResult<SearchList<PageDesign>>> eventHandler) {
		try {
			SearchList<PageDesign> searchList = aSearchPageDesignList(siteRequest, populate, store, modify, uri, apiMethod);
			eventHandler.handle(Future.succeededFuture(searchList));
		} catch(Exception e) {
			LOG.error(String.format("aSearchPageDesign failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SearchList<PageDesign> aSearchPageDesignList(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod) {
		ServiceRequest serviceRequest = siteRequest.getServiceRequest();
		String entityListStr = siteRequest.getServiceRequest().getParams().getJsonObject("query").getString("fl");
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

		String id = serviceRequest.getParams().getJsonObject("path").getString("id");
		if(id != null && NumberUtils.isCreatable(id)) {
			searchList.addFilterQuery("(pk_indexed_long:" + ClientUtils.escapeQueryChars(id) + " OR objectId_indexed_string:" + ClientUtils.escapeQueryChars(id) + ")");
		} else if(id != null) {
			searchList.addFilterQuery("objectId_indexed_string:" + ClientUtils.escapeQueryChars(id));
		}

		serviceRequest.getParams().getJsonObject("query").forEach(paramRequest -> {
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
				if(paramValuesObject != null && "facet.pivot".equals(paramName)) {
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
				} else if(paramValuesObject != null) {
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
								Matcher mFq = Pattern.compile("(\\w+):(.+?(?=(\\)|\\s+OR\\s+|\\s+AND\\s+|$)))").matcher((String)paramObject);
								boolean foundFq = mFq.find();
								if(foundFq) {
									StringBuffer sb = new StringBuffer();
									while(foundFq) {
										entityVar = mFq.group(1).trim();
										valueIndexed = mFq.group(2).trim();
										varIndexed = PageDesign.varIndexedPageDesign(entityVar);
										String entityFq = aSearchPageDesignFq(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
										mFq.appendReplacement(sb, entityFq);
										foundFq = mFq.find();
									}
									mFq.appendTail(sb);
									searchList.addFilterQuery(sb.toString());
								}
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
							case "facet.field":
								entityVar = (String)paramObject;
								varIndexed = PageDesign.varIndexedPageDesign(entityVar);
								if(varIndexed != null)
									searchList.addFacetField(varIndexed);
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

	public void definePageDesign(PageDesign o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT * FROM PageDesign WHERE pk=$1")
					.collecting(Collectors.toList())
					.execute(Tuple.of(pk)
					, defineAsync
			-> {
				if(defineAsync.succeeded()) {
					try {
						for(Row definition : defineAsync.result().value()) {
							for(Integer i = 0; i < definition.size(); i++) {
								String columnName = definition.getColumnName(i);
								Object columnValue = definition.getValue(i);
								if(!"pk".equals(columnName)) {
									try {
										o.defineForClass(columnName, columnValue);
									} catch(Exception e) {
										LOG.error(String.format("definePageDesign failed. "), e);
									}
								}
							}
						}
						eventHandler.handle(Future.succeededFuture());
					} catch(Exception e) {
						LOG.error(String.format("definePageDesign failed. "), e);
						eventHandler.handle(Future.failedFuture(e));
					}
				} else {
					LOG.error(String.format("definePageDesign failed. ", defineAsync.cause()));
					eventHandler.handle(Future.failedFuture(defineAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("definePageDesign failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void attributePageDesign(PageDesign o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT pk2, 'childDesignKeys' from PageDesignchildDesignKeys_PageDesignparentDesignKeys where pk1=$1 UNION SELECT pk1, 'parentDesignKeys' from PageDesignchildDesignKeys_PageDesignparentDesignKeys where pk2=$2 UNION SELECT pk2, 'htmlPartKeys' from PageDesignhtmlPartKeys_HtmlPartpageDesignKeys where pk1=$3")
					.collecting(Collectors.toList())
					.execute(Tuple.of(pk, pk, pk)
					, attributeAsync
			-> {
				try {
					if(attributeAsync.succeeded()) {
						if(attributeAsync.result() != null) {
							for(Row definition : attributeAsync.result().value()) {
								o.attributeForClass(definition.getString(1), definition.getLong(0));
							}
						}
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOG.error(String.format("attributePageDesign failed. ", attributeAsync.cause()));
						eventHandler.handle(Future.failedFuture(attributeAsync.cause()));
					}
				} catch(Exception e) {
					LOG.error(String.format("attributePageDesign failed. "), e);
					eventHandler.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("attributePageDesign failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void indexPageDesign(PageDesign o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			o.initDeepForClass(siteRequest);
			o.indexForClass();
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOG.error(String.format("indexPageDesign failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void refreshPageDesign(PageDesign o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Boolean refresh = !"false".equals(siteRequest.getRequestVars().get("refresh"));
			if(refresh && !Optional.ofNullable(siteRequest.getJsonObject()).map(JsonObject::isEmpty).orElse(true)) {
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
							PageDesignEnUSApiServiceImpl service = new PageDesignEnUSApiServiceImpl(eventBus, config, workerExecutor, pgPool, solrClient, oauth2AuthenticationProvider, authorizationProvider);
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUS(siteRequest.getUser(), siteRequest.getServiceRequest(), new JsonObject());
							ApiRequest apiRequest2 = new ApiRequest();
							apiRequest2.setRows(1);
							apiRequest2.setNumFound(1l);
							apiRequest2.setNumPATCH(0L);
							apiRequest2.initDeepApiRequest(siteRequest2);
							siteRequest2.setApiRequest_(apiRequest2);
							eventBus.publish("websocketPageDesign", JsonObject.mapFrom(apiRequest2).toString());

							o2.setPk(pk2);
							o2.setSiteRequest_(siteRequest2);
							futures.add(
								service.patchPageDesignFuture(o2, false).onFailure(ex -> {
									LOG.error(String.format("PageDesign %s failed. ", pk2), ex);
									eventHandler.handle(Future.failedFuture(ex));
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
							HtmlPartEnUSApiServiceImpl service = new HtmlPartEnUSApiServiceImpl(eventBus, config, workerExecutor, pgPool, solrClient, oauth2AuthenticationProvider, authorizationProvider);
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUS(siteRequest.getUser(), siteRequest.getServiceRequest(), new JsonObject());
							ApiRequest apiRequest2 = new ApiRequest();
							apiRequest2.setRows(1);
							apiRequest2.setNumFound(1l);
							apiRequest2.setNumPATCH(0L);
							apiRequest2.initDeepApiRequest(siteRequest2);
							siteRequest2.setApiRequest_(apiRequest2);
							eventBus.publish("websocketHtmlPart", JsonObject.mapFrom(apiRequest2).toString());

							o2.setPk(pk2);
							o2.setSiteRequest_(siteRequest2);
							futures.add(
								service.patchHtmlPartFuture(o2, false).onFailure(ex -> {
									LOG.error(String.format("HtmlPart %s failed. ", pk2), ex);
									eventHandler.handle(Future.failedFuture(ex));
								})
							);
						}
					}
				}

				CompositeFuture.all(futures).onComplete(a -> {
					if(a.succeeded()) {
						PageDesignEnUSApiServiceImpl service = new PageDesignEnUSApiServiceImpl(eventBus, config, workerExecutor, pgPool, solrClient, oauth2AuthenticationProvider, authorizationProvider);
						List<Future> futures2 = new ArrayList<>();
						for(PageDesign o2 : searchList.getList()) {
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUS(siteRequest.getUser(), siteRequest.getServiceRequest(), new JsonObject());
							o2.setSiteRequest_(siteRequest2);
							futures2.add(
								service.patchPageDesignFuture(o2, false).onFailure(ex -> {
									LOG.error(String.format("PageDesign %s failed. ", o2.getPk()), ex);
									eventHandler.handle(Future.failedFuture(ex));
								})
							);
						}

						CompositeFuture.all(futures2).onComplete(b -> {
							if(b.succeeded()) {
								eventHandler.handle(Future.succeededFuture());
							} else {
								LOG.error("Refresh relations failed. ", b.cause());
								error(siteRequest, eventHandler, b);
							}
						});
					} else {
						LOG.error("Refresh relations failed. ", a.cause());
						error(siteRequest, eventHandler, a);
					}
				});
			} else {
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOG.error(String.format("refreshPageDesign failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}
}
