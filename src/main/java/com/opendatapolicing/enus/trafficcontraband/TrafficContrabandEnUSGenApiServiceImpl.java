package com.opendatapolicing.enus.trafficcontraband;

import com.opendatapolicing.enus.trafficsearch.TrafficSearchEnUSGenApiServiceImpl;
import com.opendatapolicing.enus.trafficsearch.TrafficSearch;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import io.vertx.core.CompositeFuture;
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
public class TrafficContrabandEnUSGenApiServiceImpl implements TrafficContrabandEnUSGenApiService {

	protected static final Logger LOG = LoggerFactory.getLogger(TrafficContrabandEnUSGenApiServiceImpl.class);

	protected static final String SERVICE_ADDRESS = "TrafficContrabandEnUSApiServiceImpl";

	protected SiteContextEnUS siteContext;

	public TrafficContrabandEnUSGenApiServiceImpl(SiteContextEnUS siteContext) {
		this.siteContext = siteContext;
	}

	// PUTImport //

	@Override
	public void putimportTrafficContraband(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("putimportTrafficContraband started. "));
		userTrafficContraband(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/contraband/import");
					siteRequest.setRequestMethod("PUTImport");

					List<String> roles = Arrays.asList("SiteService");
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
						putimportTrafficContrabandResponse(siteRequest, c -> {
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
											siteRequest.getVertx().eventBus().publish("websocketTrafficContraband", JsonObject.mapFrom(apiRequest).toString());
											varsTrafficContraband(siteRequest, d -> {
												if(d.succeeded()) {
													listPUTImportTrafficContraband(apiRequest, siteRequest, e -> {
														if(e.succeeded()) {
															putimportTrafficContrabandResponse(siteRequest, f -> {
																if(e.succeeded()) {
																	LOG.info(String.format("putimportTrafficContraband succeeded. "));
																	blockingCodeHandler.handle(Future.succeededFuture(e.result()));
																} else {
																	LOG.error(String.format("putimportTrafficContraband failed. ", f.cause()));
																	errorTrafficContraband(siteRequest, null, f);
																}
															});
														} else {
															LOG.error(String.format("putimportTrafficContraband failed. ", e.cause()));
															errorTrafficContraband(siteRequest, null, e);
														}
													});
												} else {
													LOG.error(String.format("putimportTrafficContraband failed. ", d.cause()));
													errorTrafficContraband(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putimportTrafficContraband failed. ", ex));
											errorTrafficContraband(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putimportTrafficContraband failed. ", c.cause()));
								errorTrafficContraband(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putimportTrafficContraband failed. ", ex));
					errorTrafficContraband(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("putimportTrafficContraband failed. ", b.cause()));
				errorTrafficContraband(null, eventHandler, b);
			}
		});
	}


	public void listPUTImportTrafficContraband(ApiRequest apiRequest, SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				json.put("created", json.getValue("created"));

				SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForTrafficContraband(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), json);
				siteRequest2.setApiRequest_(apiRequest);
				siteRequest2.setRequestVars(siteRequest.getRequestVars());

				SearchList<TrafficContraband> searchList = new SearchList<TrafficContraband>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(TrafficContraband.class);
				searchList.addFilterQuery("deleted_indexed_boolean:false");
				searchList.addFilterQuery("archived_indexed_boolean:false");
				searchList.addFilterQuery("inheritPk_indexed_long:" + json.getString("pk"));
				searchList.initDeepForClass(siteRequest2);

				if(searchList.size() == 1) {
					TrafficContraband o = searchList.getList().stream().findFirst().orElse(null);
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
							patchTrafficContrabandFuture(o, true, a -> {
								if(a.succeeded()) {
								} else {
									LOG.error(String.format("listPUTImportTrafficContraband failed. ", a.cause()));
									errorTrafficContraband(siteRequest2, eventHandler, a);
								}
							})
						);
					}
				} else {
					futures.add(
						postTrafficContrabandFuture(siteRequest2, true, a -> {
							if(a.succeeded()) {
							} else {
								LOG.error(String.format("listPUTImportTrafficContraband failed. ", a.cause()));
								errorTrafficContraband(siteRequest2, eventHandler, a);
							}
						})
					);
				}
			});
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
					response200PUTImportTrafficContraband(siteRequest, eventHandler);
				} else {
					LOG.error(String.format("listPUTImportTrafficContraband failed. ", a.cause()));
					errorTrafficContraband(apiRequest.getSiteRequest_(), eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("listPUTImportTrafficContraband failed. ", ex));
			errorTrafficContraband(siteRequest, null, Future.failedFuture(ex));
		}
	}

	public void putimportTrafficContrabandResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PUTImportTrafficContraband(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("putimportTrafficContrabandResponse failed. ", a.cause()));
					errorTrafficContraband(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("putimportTrafficContrabandResponse failed. ", ex));
			errorTrafficContraband(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTImportTrafficContraband(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTImportTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTMerge //

	@Override
	public void putmergeTrafficContraband(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("putmergeTrafficContraband started. "));
		userTrafficContraband(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/contraband/merge");
					siteRequest.setRequestMethod("PUTMerge");

					List<String> roles = Arrays.asList("SiteService");
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
						putmergeTrafficContrabandResponse(siteRequest, c -> {
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
											siteRequest.getVertx().eventBus().publish("websocketTrafficContraband", JsonObject.mapFrom(apiRequest).toString());
											varsTrafficContraband(siteRequest, d -> {
												if(d.succeeded()) {
													listPUTMergeTrafficContraband(apiRequest, siteRequest, e -> {
														if(e.succeeded()) {
															putmergeTrafficContrabandResponse(siteRequest, f -> {
																if(e.succeeded()) {
																	LOG.info(String.format("putmergeTrafficContraband succeeded. "));
																	blockingCodeHandler.handle(Future.succeededFuture(e.result()));
																} else {
																	LOG.error(String.format("putmergeTrafficContraband failed. ", f.cause()));
																	errorTrafficContraband(siteRequest, null, f);
																}
															});
														} else {
															LOG.error(String.format("putmergeTrafficContraband failed. ", e.cause()));
															errorTrafficContraband(siteRequest, null, e);
														}
													});
												} else {
													LOG.error(String.format("putmergeTrafficContraband failed. ", d.cause()));
													errorTrafficContraband(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putmergeTrafficContraband failed. ", ex));
											errorTrafficContraband(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putmergeTrafficContraband failed. ", c.cause()));
								errorTrafficContraband(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putmergeTrafficContraband failed. ", ex));
					errorTrafficContraband(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("putmergeTrafficContraband failed. ", b.cause()));
				errorTrafficContraband(null, eventHandler, b);
			}
		});
	}


	public void listPUTMergeTrafficContraband(ApiRequest apiRequest, SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForTrafficContraband(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), json);
				siteRequest2.setApiRequest_(apiRequest);
				siteRequest2.setRequestVars(siteRequest.getRequestVars());

				SearchList<TrafficContraband> searchList = new SearchList<TrafficContraband>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(TrafficContraband.class);
				searchList.addFilterQuery("deleted_indexed_boolean:false");
				searchList.addFilterQuery("archived_indexed_boolean:false");
				searchList.addFilterQuery("pk_indexed_long:" + json.getString("pk"));
				searchList.initDeepForClass(siteRequest2);

				if(searchList.size() == 1) {
					TrafficContraband o = searchList.getList().stream().findFirst().orElse(null);
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
							patchTrafficContrabandFuture(o, false, a -> {
								if(a.succeeded()) {
								} else {
									LOG.error(String.format("listPUTMergeTrafficContraband failed. ", a.cause()));
									errorTrafficContraband(siteRequest2, eventHandler, a);
								}
							})
						);
					}
				} else {
					futures.add(
						postTrafficContrabandFuture(siteRequest2, false, a -> {
							if(a.succeeded()) {
							} else {
								LOG.error(String.format("listPUTMergeTrafficContraband failed. ", a.cause()));
								errorTrafficContraband(siteRequest2, eventHandler, a);
							}
						})
					);
				}
			});
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
					response200PUTMergeTrafficContraband(siteRequest, eventHandler);
				} else {
					LOG.error(String.format("listPUTMergeTrafficContraband failed. ", a.cause()));
					errorTrafficContraband(apiRequest.getSiteRequest_(), eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("listPUTMergeTrafficContraband failed. ", ex));
			errorTrafficContraband(siteRequest, null, Future.failedFuture(ex));
		}
	}

	public void putmergeTrafficContrabandResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PUTMergeTrafficContraband(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("putmergeTrafficContrabandResponse failed. ", a.cause()));
					errorTrafficContraband(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("putmergeTrafficContrabandResponse failed. ", ex));
			errorTrafficContraband(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTMergeTrafficContraband(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTMergeTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTCopy //

	@Override
	public void putcopyTrafficContraband(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("putcopyTrafficContraband started. "));
		userTrafficContraband(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/contraband/copy");
					siteRequest.setRequestMethod("PUTCopy");

					List<String> roles = Arrays.asList("SiteService");
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
						putcopyTrafficContrabandResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											aSearchTrafficContraband(siteRequest, false, true, true, "/api/contraband/copy", "PUTCopy", d -> {
												if(d.succeeded()) {
													SearchList<TrafficContraband> listTrafficContraband = d.result();
													ApiRequest apiRequest = new ApiRequest();
													apiRequest.setRows(listTrafficContraband.getRows());
													apiRequest.setNumFound(listTrafficContraband.getQueryResponse().getResults().getNumFound());
													apiRequest.setNumPATCH(0L);
													apiRequest.initDeepApiRequest(siteRequest);
													siteRequest.setApiRequest_(apiRequest);
													siteRequest.getVertx().eventBus().publish("websocketTrafficContraband", JsonObject.mapFrom(apiRequest).toString());
													try {
														listPUTCopyTrafficContraband(apiRequest, listTrafficContraband, e -> {
															if(e.succeeded()) {
																putcopyTrafficContrabandResponse(siteRequest, f -> {
																	if(f.succeeded()) {
																		LOG.info(String.format("putcopyTrafficContraband succeeded. "));
																		blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																	} else {
																		LOG.error(String.format("putcopyTrafficContraband failed. ", f.cause()));
																		errorTrafficContraband(siteRequest, null, f);
																	}
																});
															} else {
																LOG.error(String.format("putcopyTrafficContraband failed. ", e.cause()));
																errorTrafficContraband(siteRequest, null, e);
															}
														});
													} catch(Exception ex) {
														LOG.error(String.format("putcopyTrafficContraband failed. ", ex));
														errorTrafficContraband(siteRequest, null, Future.failedFuture(ex));
													}
												} else {
													LOG.error(String.format("putcopyTrafficContraband failed. ", d.cause()));
													errorTrafficContraband(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putcopyTrafficContraband failed. ", ex));
											errorTrafficContraband(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putcopyTrafficContraband failed. ", c.cause()));
								errorTrafficContraband(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putcopyTrafficContraband failed. ", ex));
					errorTrafficContraband(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("putcopyTrafficContraband failed. ", b.cause()));
				errorTrafficContraband(null, eventHandler, b);
			}
		});
	}


	public void listPUTCopyTrafficContraband(ApiRequest apiRequest, SearchList<TrafficContraband> listTrafficContraband, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listTrafficContraband.getSiteRequest_();
		listTrafficContraband.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForTrafficContraband(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				putcopyTrafficContrabandFuture(siteRequest2, JsonObject.mapFrom(o), a -> {
					if(a.succeeded()) {
					} else {
						LOG.error(String.format("listPUTCopyTrafficContraband failed. ", a.cause()));
						errorTrafficContraband(siteRequest, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).onComplete( a -> {
			if(a.succeeded()) {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + listTrafficContraband.size());
				if(listTrafficContraband.next()) {
					listPUTCopyTrafficContraband(apiRequest, listTrafficContraband, eventHandler);
				} else {
					response200PUTCopyTrafficContraband(siteRequest, eventHandler);
				}
			} else {
				LOG.error(String.format("listPUTCopyTrafficContraband failed. ", a.cause()));
				errorTrafficContraband(listTrafficContraband.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<TrafficContraband> putcopyTrafficContrabandFuture(SiteRequestEnUS siteRequest, JsonObject jsonObject, Handler<AsyncResult<TrafficContraband>> eventHandler) {
		Promise<TrafficContraband> promise = Promise.promise();
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

			sqlConnectionTrafficContraband(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionTrafficContraband(siteRequest, b -> {
						if(b.succeeded()) {
							createTrafficContraband(siteRequest, c -> {
								if(c.succeeded()) {
									TrafficContraband trafficContraband = c.result();
									sqlPUTCopyTrafficContraband(trafficContraband, jsonObject, d -> {
										if(d.succeeded()) {
											defineIndexTrafficContraband(trafficContraband, e -> {
												if(e.succeeded()) {
													ApiRequest apiRequest = siteRequest.getApiRequest_();
													if(apiRequest != null) {
														apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
														if(apiRequest.getNumFound() == 1L) {
															trafficContraband.apiRequestTrafficContraband();
														}
														siteRequest.getVertx().eventBus().publish("websocketTrafficContraband", JsonObject.mapFrom(apiRequest).toString());
													}
													eventHandler.handle(Future.succeededFuture(trafficContraband));
													promise.complete(trafficContraband);
												} else {
													LOG.error(String.format("putcopyTrafficContrabandFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOG.error(String.format("putcopyTrafficContrabandFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOG.error(String.format("putcopyTrafficContrabandFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOG.error(String.format("putcopyTrafficContrabandFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOG.error(String.format("putcopyTrafficContrabandFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("putcopyTrafficContrabandFuture failed. "), e);
			errorTrafficContraband(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPUTCopyTrafficContraband(TrafficContraband o, JsonObject jsonObject, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE TrafficContraband SET ");
			List<Object> bParams = new ArrayList<Object>();
			TrafficContraband o2 = new TrafficContraband();
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
					case "archived":
						o2.setArchived(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("archived=$" + num);
						num++;
						bParams.add(o2.sqlArchived());
						break;
					case "deleted":
						o2.setDeleted(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("deleted=$" + num);
						num++;
						bParams.add(o2.sqlDeleted());
						break;
					case "searchKey":
						{
							Long l = Long.parseLong(jsonObject.getString(entityVar));
							if(l != null) {
								if(bParams.size() > 0) {
									bSql.append(", ");
								}
								bSql.append("searchKey=$" + num);
								num++;
								bParams.add(l);
							}
						}
						break;
					case "contrabandOunces":
						o2.setContrabandOunces(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("contrabandOunces=$" + num);
						num++;
						bParams.add(o2.sqlContrabandOunces());
						break;
					case "contrabandPounds":
						o2.setContrabandPounds(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("contrabandPounds=$" + num);
						num++;
						bParams.add(o2.sqlContrabandPounds());
						break;
					case "contrabandPints":
						o2.setContrabandPints(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("contrabandPints=$" + num);
						num++;
						bParams.add(o2.sqlContrabandPints());
						break;
					case "contrabandGallons":
						o2.setContrabandGallons(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("contrabandGallons=$" + num);
						num++;
						bParams.add(o2.sqlContrabandGallons());
						break;
					case "contrabandDosages":
						o2.setContrabandDosages(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("contrabandDosages=$" + num);
						num++;
						bParams.add(o2.sqlContrabandDosages());
						break;
					case "contrabandGrams":
						o2.setContrabandGrams(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("contrabandGrams=$" + num);
						num++;
						bParams.add(o2.sqlContrabandGrams());
						break;
					case "contrabandKilos":
						o2.setContrabandKilos(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("contrabandKilos=$" + num);
						num++;
						bParams.add(o2.sqlContrabandKilos());
						break;
					case "contrabandMoney":
						o2.setContrabandMoney(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("contrabandMoney=$" + num);
						num++;
						bParams.add(o2.sqlContrabandMoney());
						break;
					case "contrabandWeapons":
						o2.setContrabandWeapons(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("contrabandWeapons=$" + num);
						num++;
						bParams.add(o2.sqlContrabandWeapons());
						break;
					case "contrabandDollarAmount":
						o2.setContrabandDollarAmount(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("contrabandDollarAmount=$" + num);
						num++;
						bParams.add(o2.sqlContrabandDollarAmount());
						break;
					}
				}
			}
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture());
				} else {
					LOG.error(String.format("sqlPUTCopyTrafficContraband failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPUTCopyTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void putcopyTrafficContrabandResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PUTCopyTrafficContraband(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("putcopyTrafficContrabandResponse failed. ", a.cause()));
					errorTrafficContraband(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("putcopyTrafficContrabandResponse failed. ", ex));
			errorTrafficContraband(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTCopyTrafficContraband(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTCopyTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// POST //

	@Override
	public void postTrafficContraband(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("postTrafficContraband started. "));
		userTrafficContraband(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/contraband");
					siteRequest.setRequestMethod("POST");

					List<String> roles = Arrays.asList("SiteService");
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
						siteRequest.getVertx().eventBus().publish("websocketTrafficContraband", JsonObject.mapFrom(apiRequest).toString());
						postTrafficContrabandFuture(siteRequest, false, c -> {
							if(c.succeeded()) {
								TrafficContraband trafficContraband = c.result();
								apiRequest.setPk(trafficContraband.getPk());
								postTrafficContrabandResponse(trafficContraband, d -> {
										if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("postTrafficContraband succeeded. "));
									} else {
										LOG.error(String.format("postTrafficContraband failed. ", d.cause()));
										errorTrafficContraband(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("postTrafficContraband failed. ", c.cause()));
								errorTrafficContraband(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("postTrafficContraband failed. ", ex));
					errorTrafficContraband(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("postTrafficContraband failed. ", b.cause()));
				errorTrafficContraband(null, eventHandler, b);
			}
		});
	}


	public Future<TrafficContraband> postTrafficContrabandFuture(SiteRequestEnUS siteRequest, Boolean inheritPk, Handler<AsyncResult<TrafficContraband>> eventHandler) {
		Promise<TrafficContraband> promise = Promise.promise();
		try {
			sqlConnectionTrafficContraband(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionTrafficContraband(siteRequest, b -> {
						if(b.succeeded()) {
							createTrafficContraband(siteRequest, c -> {
								if(c.succeeded()) {
									TrafficContraband trafficContraband = c.result();
									sqlPOSTTrafficContraband(trafficContraband, inheritPk, d -> {
										if(d.succeeded()) {
											defineIndexTrafficContraband(trafficContraband, e -> {
												if(e.succeeded()) {
													ApiRequest apiRequest = siteRequest.getApiRequest_();
													if(apiRequest != null) {
														apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
														trafficContraband.apiRequestTrafficContraband();
														siteRequest.getVertx().eventBus().publish("websocketTrafficContraband", JsonObject.mapFrom(apiRequest).toString());
													}
													eventHandler.handle(Future.succeededFuture(trafficContraband));
													promise.complete(trafficContraband);
												} else {
													LOG.error(String.format("postTrafficContrabandFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOG.error(String.format("postTrafficContrabandFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOG.error(String.format("postTrafficContrabandFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOG.error(String.format("postTrafficContrabandFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOG.error(String.format("postTrafficContrabandFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("postTrafficContrabandFuture failed. "), e);
			errorTrafficContraband(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPOSTTrafficContraband(TrafficContraband o, Boolean inheritPk, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE TrafficContraband SET ");
			List<Object> bParams = new ArrayList<Object>();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			TrafficContraband o2 = new TrafficContraband();
			o2.setSiteRequest_(siteRequest);
			List<Future> futures = new ArrayList<>();

			if(siteRequest.getSessionId() != null) {
				if(bParams.size() > 0) {
					bSql.append(", ");
				}
				bSql.append("sessionId=$" + num);
				num++;
				bParams.add(siteRequest.getSessionId());
			}
			if(siteRequest.getUserKey() != null) {
				if(bParams.size() > 0) {
					bSql.append(", ");
				}
				bSql.append("userKey=$" + num);
				num++;
				bParams.add(siteRequest.getUserKey());
			}

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
					case "archived":
						o2.setArchived(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("archived=$" + num);
						num++;
						bParams.add(o2.sqlArchived());
						break;
					case "deleted":
						o2.setDeleted(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("deleted=$" + num);
						num++;
						bParams.add(o2.sqlDeleted());
						break;
					case "searchKey":
						{
							Long l = Long.parseLong(jsonObject.getString(entityVar));
							if(l != null) {
								SearchList<com.opendatapolicing.enus.trafficsearch.TrafficSearch> searchList = new SearchList<com.opendatapolicing.enus.trafficsearch.TrafficSearch>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(com.opendatapolicing.enus.trafficsearch.TrafficSearch.class);
								searchList.addFilterQuery("deleted_indexed_boolean:false");
								searchList.addFilterQuery("archived_indexed_boolean:false");
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									if(bParams.size() > 0) {
										bSql.append(", ");
									}
									bSql.append("searchKey=$" + num);
									num++;
									bParams.add(l2);
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("TrafficSearch");
									}
								}
							}
						}
						break;
					case "contrabandOunces":
						o2.setContrabandOunces(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("contrabandOunces=$" + num);
						num++;
						bParams.add(o2.sqlContrabandOunces());
						break;
					case "contrabandPounds":
						o2.setContrabandPounds(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("contrabandPounds=$" + num);
						num++;
						bParams.add(o2.sqlContrabandPounds());
						break;
					case "contrabandPints":
						o2.setContrabandPints(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("contrabandPints=$" + num);
						num++;
						bParams.add(o2.sqlContrabandPints());
						break;
					case "contrabandGallons":
						o2.setContrabandGallons(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("contrabandGallons=$" + num);
						num++;
						bParams.add(o2.sqlContrabandGallons());
						break;
					case "contrabandDosages":
						o2.setContrabandDosages(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("contrabandDosages=$" + num);
						num++;
						bParams.add(o2.sqlContrabandDosages());
						break;
					case "contrabandGrams":
						o2.setContrabandGrams(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("contrabandGrams=$" + num);
						num++;
						bParams.add(o2.sqlContrabandGrams());
						break;
					case "contrabandKilos":
						o2.setContrabandKilos(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("contrabandKilos=$" + num);
						num++;
						bParams.add(o2.sqlContrabandKilos());
						break;
					case "contrabandMoney":
						o2.setContrabandMoney(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("contrabandMoney=$" + num);
						num++;
						bParams.add(o2.sqlContrabandMoney());
						break;
					case "contrabandWeapons":
						o2.setContrabandWeapons(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("contrabandWeapons=$" + num);
						num++;
						bParams.add(o2.sqlContrabandWeapons());
						break;
					case "contrabandDollarAmount":
						o2.setContrabandDollarAmount(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("contrabandDollarAmount=$" + num);
						num++;
						bParams.add(o2.sqlContrabandDollarAmount());
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
					LOG.error(String.format("sqlPOSTTrafficContraband failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPOSTTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void postTrafficContrabandResponse(TrafficContraband trafficContraband, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = trafficContraband.getSiteRequest_();
		try {
			response200POSTTrafficContraband(trafficContraband, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("postTrafficContrabandResponse failed. ", a.cause()));
					errorTrafficContraband(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("postTrafficContrabandResponse failed. ", ex));
			errorTrafficContraband(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200POSTTrafficContraband(TrafficContraband o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200POSTTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchTrafficContraband(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("patchTrafficContraband started. "));
		userTrafficContraband(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/contraband");
					siteRequest.setRequestMethod("PATCH");

					List<String> roles = Arrays.asList("SiteService");
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
						patchTrafficContrabandResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											aSearchTrafficContraband(siteRequest, false, true, true, "/api/contraband", "PATCH", d -> {
												if(d.succeeded()) {
													SearchList<TrafficContraband> listTrafficContraband = d.result();

													List<String> roles2 = Arrays.asList("SiteAdmin");
													if(listTrafficContraband.getQueryResponse().getResults().getNumFound() > 1
															&& !CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles2)
															&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles2)
															) {
														String message = String.format("roles required: " + String.join(", ", roles2));
														LOG.error(message);
														errorTrafficContraband(siteRequest, eventHandler, Future.failedFuture(message));
													} else {

														ApiRequest apiRequest = new ApiRequest();
														apiRequest.setRows(listTrafficContraband.getRows());
														apiRequest.setNumFound(listTrafficContraband.getQueryResponse().getResults().getNumFound());
														apiRequest.setNumPATCH(0L);
														apiRequest.initDeepApiRequest(siteRequest);
														siteRequest.setApiRequest_(apiRequest);
														siteRequest.getVertx().eventBus().publish("websocketTrafficContraband", JsonObject.mapFrom(apiRequest).toString());
														SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listTrafficContraband.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(null);
														Date date = null;
														if(facets != null)
														date = (Date)facets.get("max_modified");
														String dt;
														if(date == null)
															dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));
														else
															dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")));
														listTrafficContraband.addFilterQuery(String.format("modified_indexed_date:[* TO %s]", dt));

														try {
															listPATCHTrafficContraband(apiRequest, listTrafficContraband, dt, e -> {
																if(e.succeeded()) {
																	patchTrafficContrabandResponse(siteRequest, f -> {
																		if(f.succeeded()) {
																			LOG.info(String.format("patchTrafficContraband succeeded. "));
																			blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																		} else {
																			LOG.error(String.format("patchTrafficContraband failed. ", f.cause()));
																			errorTrafficContraband(siteRequest, null, f);
																		}
																	});
																} else {
																	LOG.error(String.format("patchTrafficContraband failed. ", e.cause()));
																	errorTrafficContraband(siteRequest, null, e);
																}
															});
														} catch(Exception ex) {
															LOG.error(String.format("patchTrafficContraband failed. ", ex));
															errorTrafficContraband(siteRequest, null, Future.failedFuture(ex));
														}
													}
										} else {
													LOG.error(String.format("patchTrafficContraband failed. ", d.cause()));
													errorTrafficContraband(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("patchTrafficContraband failed. ", ex));
											errorTrafficContraband(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("patchTrafficContraband failed. ", c.cause()));
								errorTrafficContraband(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("patchTrafficContraband failed. ", ex));
					errorTrafficContraband(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("patchTrafficContraband failed. ", b.cause()));
				errorTrafficContraband(null, eventHandler, b);
			}
		});
	}


	public void listPATCHTrafficContraband(ApiRequest apiRequest, SearchList<TrafficContraband> listTrafficContraband, String dt, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listTrafficContraband.getSiteRequest_();
		listTrafficContraband.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForTrafficContraband(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				patchTrafficContrabandFuture(o, false, a -> {
					if(a.succeeded()) {
					} else {
						errorTrafficContraband(siteRequest2, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).onComplete( a -> {
			if(a.succeeded()) {
				if(listTrafficContraband.next(dt)) {
					listPATCHTrafficContraband(apiRequest, listTrafficContraband, dt, eventHandler);
				} else {
					response200PATCHTrafficContraband(siteRequest, eventHandler);
				}
			} else {
				LOG.error(String.format("listPATCHTrafficContraband failed. ", a.cause()));
				errorTrafficContraband(listTrafficContraband.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<TrafficContraband> patchTrafficContrabandFuture(TrafficContraband o, Boolean inheritPk, Handler<AsyncResult<TrafficContraband>> eventHandler) {
		Promise<TrafficContraband> promise = Promise.promise();
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			if(apiRequest != null && apiRequest.getNumFound() == 1L) {
				apiRequest.setOriginal(o);
				apiRequest.setPk(o.getPk());
			}
			sqlConnectionTrafficContraband(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionTrafficContraband(siteRequest, b -> {
						if(b.succeeded()) {
							sqlPATCHTrafficContraband(o, inheritPk, c -> {
								if(c.succeeded()) {
									TrafficContraband trafficContraband = c.result();
									defineIndexTrafficContraband(trafficContraband, d -> {
										if(d.succeeded()) {
											if(apiRequest != null) {
												apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
												if(apiRequest.getNumFound() == 1L) {
													trafficContraband.apiRequestTrafficContraband();
												}
												siteRequest.getVertx().eventBus().publish("websocketTrafficContraband", JsonObject.mapFrom(apiRequest).toString());
											}
											eventHandler.handle(Future.succeededFuture(trafficContraband));
											promise.complete(trafficContraband);
										} else {
											LOG.error(String.format("patchTrafficContrabandFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOG.error(String.format("patchTrafficContrabandFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOG.error(String.format("patchTrafficContrabandFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOG.error(String.format("patchTrafficContrabandFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("patchTrafficContrabandFuture failed. "), e);
			errorTrafficContraband(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPATCHTrafficContraband(TrafficContraband o, Boolean inheritPk, Handler<AsyncResult<TrafficContraband>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE TrafficContraband SET ");
			List<Object> bParams = new ArrayList<Object>();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			TrafficContraband o2 = new TrafficContraband();
			o2.setSiteRequest_(siteRequest);
			List<Future> futures = new ArrayList<>();

			if(o.getUserKey() == null && siteRequest.getUserKey() != null) {
				if(bParams.size() > 0)
					bSql.append(", ");
				bSql.append("userKey=$" + num);
				num++;
				bParams.add(siteRequest.getUserKey());
			}

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
					case "setArchived":
							o2.setArchived(jsonObject.getBoolean(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("archived=$" + num);
							num++;
							bParams.add(o2.sqlArchived());
						break;
					case "setDeleted":
							o2.setDeleted(jsonObject.getBoolean(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("deleted=$" + num);
							num++;
							bParams.add(o2.sqlDeleted());
						break;
					case "setSearchKey":
						{
							o2.setSearchKey(jsonObject.getString(methodName));
							Long l = o2.getSearchKey();
							if(l != null && !l.equals(o.getSearchKey())) {
								SearchList<com.opendatapolicing.enus.trafficsearch.TrafficSearch> searchList = new SearchList<com.opendatapolicing.enus.trafficsearch.TrafficSearch>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(com.opendatapolicing.enus.trafficsearch.TrafficSearch.class);
								searchList.addFilterQuery("deleted_indexed_boolean:false");
								searchList.addFilterQuery("archived_indexed_boolean:false");
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									o2.setSearchKey(l2);
									if(bParams.size() > 0)
										bSql.append(", ");
									bSql.append("searchKey=$" + num);
									num++;
									bParams.add(l2);
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("TrafficSearch");
									}
								}
							}
						}
						break;
					case "removeSearchKey":
						{
							o2.setSearchKey(jsonObject.getString(methodName));
							Long l = o2.getSearchKey();
							if(l != null) {
								SearchList<com.opendatapolicing.enus.trafficsearch.TrafficSearch> searchList = new SearchList<com.opendatapolicing.enus.trafficsearch.TrafficSearch>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(com.opendatapolicing.enus.trafficsearch.TrafficSearch.class);
								searchList.addFilterQuery("deleted_indexed_boolean:false");
								searchList.addFilterQuery("archived_indexed_boolean:false");
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									o2.setSearchKey((Long)null);
									if(bParams.size() > 0)
										bSql.append(", ");
									bSql.append("searchKey=null");
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("TrafficSearch");
									}
								}
							}
						}
						break;
					case "setContrabandOunces":
							o2.setContrabandOunces(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("contrabandOunces=$" + num);
							num++;
							bParams.add(o2.sqlContrabandOunces());
						break;
					case "setContrabandPounds":
							o2.setContrabandPounds(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("contrabandPounds=$" + num);
							num++;
							bParams.add(o2.sqlContrabandPounds());
						break;
					case "setContrabandPints":
							o2.setContrabandPints(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("contrabandPints=$" + num);
							num++;
							bParams.add(o2.sqlContrabandPints());
						break;
					case "setContrabandGallons":
							o2.setContrabandGallons(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("contrabandGallons=$" + num);
							num++;
							bParams.add(o2.sqlContrabandGallons());
						break;
					case "setContrabandDosages":
							o2.setContrabandDosages(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("contrabandDosages=$" + num);
							num++;
							bParams.add(o2.sqlContrabandDosages());
						break;
					case "setContrabandGrams":
							o2.setContrabandGrams(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("contrabandGrams=$" + num);
							num++;
							bParams.add(o2.sqlContrabandGrams());
						break;
					case "setContrabandKilos":
							o2.setContrabandKilos(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("contrabandKilos=$" + num);
							num++;
							bParams.add(o2.sqlContrabandKilos());
						break;
					case "setContrabandMoney":
							o2.setContrabandMoney(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("contrabandMoney=$" + num);
							num++;
							bParams.add(o2.sqlContrabandMoney());
						break;
					case "setContrabandWeapons":
							o2.setContrabandWeapons(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("contrabandWeapons=$" + num);
							num++;
							bParams.add(o2.sqlContrabandWeapons());
						break;
					case "setContrabandDollarAmount":
							o2.setContrabandDollarAmount(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("contrabandDollarAmount=$" + num);
							num++;
							bParams.add(o2.sqlContrabandDollarAmount());
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
					TrafficContraband o3 = new TrafficContraband();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
					eventHandler.handle(Future.succeededFuture(o3));
				} else {
					LOG.error(String.format("sqlPATCHTrafficContraband failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPATCHTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void patchTrafficContrabandResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PATCHTrafficContraband(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("patchTrafficContrabandResponse failed. ", a.cause()));
					errorTrafficContraband(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("patchTrafficContrabandResponse failed. ", ex));
			errorTrafficContraband(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PATCHTrafficContraband(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PATCHTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// GET //

	@Override
	public void getTrafficContraband(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		userTrafficContraband(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/contraband/{id}");
					siteRequest.setRequestMethod("GET");
					{
						aSearchTrafficContraband(siteRequest, false, true, false, "/api/contraband/{id}", "GET", c -> {
							if(c.succeeded()) {
								SearchList<TrafficContraband> listTrafficContraband = c.result();
								getTrafficContrabandResponse(listTrafficContraband, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("getTrafficContraband succeeded. "));
									} else {
										LOG.error(String.format("getTrafficContraband failed. ", d.cause()));
										errorTrafficContraband(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("getTrafficContraband failed. ", c.cause()));
								errorTrafficContraband(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("getTrafficContraband failed. ", ex));
					errorTrafficContraband(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("getTrafficContraband failed. ", b.cause()));
				errorTrafficContraband(null, eventHandler, b);
			}
		});
	}


	public void getTrafficContrabandResponse(SearchList<TrafficContraband> listTrafficContraband, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficContraband.getSiteRequest_();
		try {
			response200GETTrafficContraband(listTrafficContraband, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("getTrafficContrabandResponse failed. ", a.cause()));
					errorTrafficContraband(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("getTrafficContrabandResponse failed. ", ex));
			errorTrafficContraband(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200GETTrafficContraband(SearchList<TrafficContraband> listTrafficContraband, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listTrafficContraband.getSiteRequest_();
			SolrDocumentList solrDocuments = listTrafficContraband.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listTrafficContraband.getList().stream().findFirst().orElse(null));
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200GETTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// Search //

	@Override
	public void searchTrafficContraband(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		userTrafficContraband(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/contraband");
					siteRequest.setRequestMethod("Search");
					{
						aSearchTrafficContraband(siteRequest, false, true, false, "/api/contraband", "Search", c -> {
							if(c.succeeded()) {
								SearchList<TrafficContraband> listTrafficContraband = c.result();
								searchTrafficContrabandResponse(listTrafficContraband, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("searchTrafficContraband succeeded. "));
									} else {
										LOG.error(String.format("searchTrafficContraband failed. ", d.cause()));
										errorTrafficContraband(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("searchTrafficContraband failed. ", c.cause()));
								errorTrafficContraband(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("searchTrafficContraband failed. ", ex));
					errorTrafficContraband(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("searchTrafficContraband failed. ", b.cause()));
				errorTrafficContraband(null, eventHandler, b);
			}
		});
	}


	public void searchTrafficContrabandResponse(SearchList<TrafficContraband> listTrafficContraband, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficContraband.getSiteRequest_();
		try {
			response200SearchTrafficContraband(listTrafficContraband, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("searchTrafficContrabandResponse failed. ", a.cause()));
					errorTrafficContraband(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("searchTrafficContrabandResponse failed. ", ex));
			errorTrafficContraband(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchTrafficContraband(SearchList<TrafficContraband> listTrafficContraband, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listTrafficContraband.getSiteRequest_();
			QueryResponse responseSearch = listTrafficContraband.getQueryResponse();
			SolrDocumentList solrDocuments = listTrafficContraband.getSolrDocumentList();
			Long searchInMillis = Long.valueOf(responseSearch.getQTime());
			Long transmissionInMillis = responseSearch.getElapsedTime();
			Long startNum = responseSearch.getResults().getStart();
			Long foundNum = responseSearch.getResults().getNumFound();
			Integer returnedNum = responseSearch.getResults().size();
			String searchTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(searchInMillis), TimeUnit.MILLISECONDS.toMillis(searchInMillis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(searchInMillis)));
			String transmissionTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis), TimeUnit.MILLISECONDS.toMillis(transmissionInMillis) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis)));
			Exception exceptionSearch = responseSearch.getException();
			List<String> fls = listTrafficContraband.getFields();

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
				json.put("searchTime", searchTime);
				json.put("transmissionTime", transmissionTime);
			}
			JsonArray l = new JsonArray();
			listTrafficContraband.getList().stream().forEach(o -> {
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
					responsePivotSearchTrafficContraband(pivotFields, pivotArray);
				}
			}
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200SearchTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}
	public void responsePivotSearchTrafficContraband(List<PivotField> pivotFields, JsonArray pivotArray) {
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
				responsePivotSearchTrafficContraband(pivotFields2, pivotArray2);
			}
		}
	}

	// AdminSearch //

	@Override
	public void adminsearchTrafficContraband(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		userTrafficContraband(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/admin/contraband");
					siteRequest.setRequestMethod("AdminSearch");
					{
						aSearchTrafficContraband(siteRequest, false, true, false, "/api/admin/contraband", "AdminSearch", c -> {
							if(c.succeeded()) {
								SearchList<TrafficContraband> listTrafficContraband = c.result();
								adminsearchTrafficContrabandResponse(listTrafficContraband, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("adminsearchTrafficContraband succeeded. "));
									} else {
										LOG.error(String.format("adminsearchTrafficContraband failed. ", d.cause()));
										errorTrafficContraband(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("adminsearchTrafficContraband failed. ", c.cause()));
								errorTrafficContraband(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("adminsearchTrafficContraband failed. ", ex));
					errorTrafficContraband(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("adminsearchTrafficContraband failed. ", b.cause()));
				errorTrafficContraband(null, eventHandler, b);
			}
		});
	}


	public void adminsearchTrafficContrabandResponse(SearchList<TrafficContraband> listTrafficContraband, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficContraband.getSiteRequest_();
		try {
			response200AdminSearchTrafficContraband(listTrafficContraband, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("adminsearchTrafficContrabandResponse failed. ", a.cause()));
					errorTrafficContraband(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("adminsearchTrafficContrabandResponse failed. ", ex));
			errorTrafficContraband(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200AdminSearchTrafficContraband(SearchList<TrafficContraband> listTrafficContraband, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listTrafficContraband.getSiteRequest_();
			QueryResponse responseSearch = listTrafficContraband.getQueryResponse();
			SolrDocumentList solrDocuments = listTrafficContraband.getSolrDocumentList();
			Long searchInMillis = Long.valueOf(responseSearch.getQTime());
			Long transmissionInMillis = responseSearch.getElapsedTime();
			Long startNum = responseSearch.getResults().getStart();
			Long foundNum = responseSearch.getResults().getNumFound();
			Integer returnedNum = responseSearch.getResults().size();
			String searchTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(searchInMillis), TimeUnit.MILLISECONDS.toMillis(searchInMillis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(searchInMillis)));
			String transmissionTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis), TimeUnit.MILLISECONDS.toMillis(transmissionInMillis) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis)));
			Exception exceptionSearch = responseSearch.getException();
			List<String> fls = listTrafficContraband.getFields();

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
				json.put("searchTime", searchTime);
				json.put("transmissionTime", transmissionTime);
			}
			JsonArray l = new JsonArray();
			listTrafficContraband.getList().stream().forEach(o -> {
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
					responsePivotAdminSearchTrafficContraband(pivotFields, pivotArray);
				}
			}
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200AdminSearchTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}
	public void responsePivotAdminSearchTrafficContraband(List<PivotField> pivotFields, JsonArray pivotArray) {
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
				responsePivotAdminSearchTrafficContraband(pivotFields2, pivotArray2);
			}
		}
	}

	// SearchPage //

	@Override
	public void searchpageTrafficContrabandId(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		searchpageTrafficContraband(serviceRequest, eventHandler);
	}

	@Override
	public void searchpageTrafficContraband(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		userTrafficContraband(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/contraband");
					siteRequest.setRequestMethod("SearchPage");
					{
						aSearchTrafficContraband(siteRequest, false, true, false, "/contraband", "SearchPage", c -> {
							if(c.succeeded()) {
								SearchList<TrafficContraband> listTrafficContraband = c.result();
								searchpageTrafficContrabandResponse(listTrafficContraband, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("searchpageTrafficContraband succeeded. "));
									} else {
										LOG.error(String.format("searchpageTrafficContraband failed. ", d.cause()));
										errorTrafficContraband(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("searchpageTrafficContraband failed. ", c.cause()));
								errorTrafficContraband(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("searchpageTrafficContraband failed. ", ex));
					errorTrafficContraband(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("searchpageTrafficContraband failed. ", b.cause()));
				errorTrafficContraband(null, eventHandler, b);
			}
		});
	}


	public void searchpageTrafficContrabandPageInit(ContrabandPage page, SearchList<TrafficContraband> listTrafficContraband) {
	}
	public void searchpageTrafficContrabandResponse(SearchList<TrafficContraband> listTrafficContraband, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficContraband.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200SearchPageTrafficContraband(listTrafficContraband, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("searchpageTrafficContrabandResponse failed. ", a.cause()));
					errorTrafficContraband(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("searchpageTrafficContrabandResponse failed. ", ex));
			errorTrafficContraband(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchPageTrafficContraband(SearchList<TrafficContraband> listTrafficContraband, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listTrafficContraband.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listTrafficContraband.getSiteRequest_(), buffer);
			ContrabandPage page = new ContrabandPage();
			SolrDocument pageSolrDocument = new SolrDocument();
			MultiMap requestHeaders = MultiMap.caseInsensitiveMultiMap();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/contraband");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listTrafficContraband.size() == 1)
				siteRequest.setRequestPk(listTrafficContraband.get(0).getPk());
			siteRequest.setW(w);
			page.setListTrafficContraband(listTrafficContraband);
			page.setSiteRequest_(siteRequest);
			searchpageTrafficContrabandPageInit(page, listTrafficContraband);
			page.initDeepContrabandPage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new ServiceResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOG.error(String.format("response200SearchPageTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// General //

	public Future<TrafficContraband> defineIndexTrafficContraband(TrafficContraband trafficContraband, Handler<AsyncResult<TrafficContraband>> eventHandler) {
		Promise<TrafficContraband> promise = Promise.promise();
		SiteRequestEnUS siteRequest = trafficContraband.getSiteRequest_();
		defineTrafficContraband(trafficContraband, c -> {
			if(c.succeeded()) {
				attributeTrafficContraband(trafficContraband, d -> {
					if(d.succeeded()) {
						indexTrafficContraband(trafficContraband, e -> {
							if(e.succeeded()) {
								sqlCommitTrafficContraband(siteRequest, f -> {
									if(f.succeeded()) {
										sqlCloseTrafficContraband(siteRequest, g -> {
											if(g.succeeded()) {
												refreshTrafficContraband(trafficContraband, h -> {
													if(h.succeeded()) {
														eventHandler.handle(Future.succeededFuture(trafficContraband));
														promise.complete(trafficContraband);
													} else {
														LOG.error(String.format("refreshTrafficContraband failed. ", h.cause()));
														errorTrafficContraband(siteRequest, null, h);
													}
												});
											} else {
												LOG.error(String.format("defineIndexTrafficContraband failed. ", g.cause()));
												errorTrafficContraband(siteRequest, null, g);
											}
										});
									} else {
										LOG.error(String.format("defineIndexTrafficContraband failed. ", f.cause()));
										errorTrafficContraband(siteRequest, null, f);
									}
								});
							} else {
								LOG.error(String.format("defineIndexTrafficContraband failed. ", e.cause()));
								errorTrafficContraband(siteRequest, null, e);
							}
						});
					} else {
						LOG.error(String.format("defineIndexTrafficContraband failed. ", d.cause()));
						errorTrafficContraband(siteRequest, null, d);
					}
				});
			} else {
				LOG.error(String.format("defineIndexTrafficContraband failed. ", c.cause()));
				errorTrafficContraband(siteRequest, null, c);
			}
		});
		return promise.future();
	}

	public void createTrafficContraband(SiteRequestEnUS siteRequest, Handler<AsyncResult<TrafficContraband>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			String userId = siteRequest.getUserId();
			Long userKey = siteRequest.getUserKey();
			ZonedDateTime created = Optional.ofNullable(siteRequest.getJsonObject()).map(j -> j.getString("created")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())))).orElse(ZonedDateTime.now(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())));

			sqlConnection.preparedQuery("INSERT INTO TrafficContraband(created, userKey) VALUES($1, $2) RETURNING pk")
					.collecting(Collectors.toList())
					.execute(Tuple.of(created.toOffsetDateTime(), userKey)
					, createAsync
			-> {
				if(createAsync.succeeded()) {
					Row createLine = createAsync.result().value().stream().findFirst().orElseGet(() -> null);
					Long pk = createLine.getLong(0);
					TrafficContraband o = new TrafficContraband();
					o.setPk(pk);
					o.setSiteRequest_(siteRequest);
					eventHandler.handle(Future.succeededFuture(o));
				} else {
					LOG.error(String.format("createTrafficContraband failed. ", createAsync.cause()));
					eventHandler.handle(Future.failedFuture(createAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("createTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void errorTrafficContraband(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler, AsyncResult<?> resultAsync) {
		Throwable e = resultAsync.cause();
		JsonObject json = new JsonObject();
		JsonObject jsonError = new JsonObject();
		json.put("error", jsonError);
		jsonError.put("message", Optional.ofNullable(e).map(Throwable::getMessage).orElse(null));
		if(siteRequest != null) {
			jsonError.put("userName", siteRequest.getUserName());
			jsonError.put("userFullName", siteRequest.getUserFullName());
			jsonError.put("requestUri", siteRequest.getRequestUri());
			jsonError.put("requestMethod", siteRequest.getRequestMethod());
			jsonError.put("params", Optional.ofNullable(siteRequest.getServiceRequest()).map(o -> o.getParams()).orElse(null));
		}
		LOG.error("error: ", e);
		ServiceResponse responseOperation = new ServiceResponse(400, "BAD REQUEST", 
				Buffer.buffer().appendString(json.encodePrettily())
				, MultiMap.caseInsensitiveMultiMap().add("Content-Type", "application/json")
		);
		if(siteRequest != null) {
			SiteConfig siteConfig = siteRequest.getSiteConfig_();
			SiteContextEnUS siteContext = siteRequest.getSiteContext_();
			MailClient mailClient = siteContext.getMailClient();
			MailMessage message = new MailMessage();
			message.setFrom(siteConfig.getEmailFrom());
			message.setTo(siteConfig.getEmailAdmin());
			if(e != null && siteConfig.getEmailFrom() != null)
				message.setText(String.format("%s\n\n%s", json.encodePrettily(), ExceptionUtils.getStackTrace(e)));
			message.setSubject(String.format(siteConfig.getSiteBaseUrl() + " " + Optional.ofNullable(e).map(Throwable::getMessage).orElse(null)));
			WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
			workerExecutor.executeBlocking(
				blockingCodeHandler -> {
					mailClient.sendMail(message, result -> {
						if (result.succeeded()) {
							LOG.info(result.result().toString());
						} else {
							LOG.error("sendMail failed. ", result.cause());
						}
					});
				}, resultHandler -> {
				}
			);
			sqlRollbackTrafficContraband(siteRequest, a -> {
				if(a.succeeded()) {
					LOG.info(String.format("sql rollback. "));
					sqlCloseTrafficContraband(siteRequest, b -> {
						if(b.succeeded()) {
							LOG.info(String.format("sql close. "));
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
		} else {
			eventHandler.handle(Future.succeededFuture(responseOperation));
		}
	}

	public void sqlConnectionTrafficContraband(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
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
						LOG.error(String.format("sqlConnectionTrafficContraband failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlTransactionTrafficContraband(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();

			if(sqlConnection == null) {
				eventHandler.handle(Future.failedFuture("sqlTransactionCloseTrafficContraband failed, connection should not be null. "));
			} else {
				sqlConnection.begin(a -> {
					Transaction tx = a.result();
					siteRequest.setTx(tx);
					eventHandler.handle(Future.succeededFuture());
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlTransactionTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCommitTrafficContraband(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();

			if(tx == null) {
				eventHandler.handle(Future.failedFuture("sqlCommitCloseTrafficContraband failed, tx should not be null. "));
			} else {
				tx.commit(a -> {
					if(a.succeeded()) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else if("Transaction already completed".equals(a.cause().getMessage())) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOG.error(String.format("sqlCommitTrafficContraband failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlRollbackTrafficContraband(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();

			if(tx == null) {
				eventHandler.handle(Future.failedFuture("sqlRollbackCloseTrafficContraband failed, tx should not be null. "));
			} else {
				tx.rollback(a -> {
					if(a.succeeded()) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else if("Transaction already completed".equals(a.cause().getMessage())) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOG.error(String.format("sqlRollbackTrafficContraband failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCloseTrafficContraband(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();

			if(sqlConnection == null) {
				eventHandler.handle(Future.failedFuture("sqlCloseTrafficContraband failed, connection should not be null. "));
			} else {
				sqlConnection.close(a -> {
					if(a.succeeded()) {
						siteRequest.setSqlConnection(null);
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOG.error(String.format("sqlCloseTrafficContraband failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlCloseTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SiteRequestEnUS generateSiteRequestEnUSForTrafficContraband(User user, SiteContextEnUS siteContext, ServiceRequest serviceRequest) {
		return generateSiteRequestEnUSForTrafficContraband(user, siteContext, serviceRequest, null);
	}

	public SiteRequestEnUS generateSiteRequestEnUSForTrafficContraband(User user, SiteContextEnUS siteContext, ServiceRequest serviceRequest, JsonObject body) {
		Vertx vertx = siteContext.getVertx();
		SiteRequestEnUS siteRequest = new SiteRequestEnUS();
		siteRequest.setJsonObject(body);
		siteRequest.setVertx(vertx);
		siteRequest.setUser(user);
		siteRequest.setSiteContext_(siteContext);
		siteRequest.setSiteConfig_(siteContext.getSiteConfig());
		siteRequest.setServiceRequest(serviceRequest);
		siteRequest.initDeepSiteRequestEnUS(siteRequest);

		return siteRequest;
	}

	public void userTrafficContraband(ServiceRequest serviceRequest, Handler<AsyncResult<SiteRequestEnUS>> eventHandler) {
		try {
			JsonObject userJson = serviceRequest.getUser();
			if(userJson == null) {
				SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficContraband(null, siteContext, serviceRequest);
				eventHandler.handle(Future.succeededFuture(siteRequest));
			} else {
				User token = User.create(userJson);
				siteContext.getOauth2AuthenticationProvider().authenticate(token.principal(), a -> {
					if(a.succeeded()) {
						User user = a.result();
						siteContext.getAuthorizationProvider().getAuthorizations(user, b -> {
							if(b.succeeded()) {
								JsonObject userAttributes = user.attributes();
								JsonObject accessToken = userAttributes.getJsonObject("accessToken");
								String userId = userAttributes.getString("sub");
								SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficContraband(user, siteContext, serviceRequest);
								sqlConnectionTrafficContraband(siteRequest, c -> {
									if(c.succeeded()) {
										sqlTransactionTrafficContraband(siteRequest, d -> {
											if(d.succeeded()) {
												SqlConnection sqlConnection = siteRequest.getSqlConnection();
												sqlConnection.preparedQuery("SELECT pk FROM SiteUser WHERE userId=$1")
														.collecting(Collectors.toList())
														.execute(Tuple.of(userId)
														, selectCAsync
												-> {
													if(selectCAsync.succeeded()) {
														try {
															Row userValues = selectCAsync.result().value().stream().findFirst().orElse(null);
															SiteUserEnUSApiServiceImpl userService = new SiteUserEnUSApiServiceImpl(siteContext);
															if(userValues == null) {
																JsonObject userVertx = siteRequest.getServiceRequest().getUser();

																JsonObject jsonObject = new JsonObject();
																jsonObject.put("userName", accessToken.getString("preferred_username"));
																jsonObject.put("userFirstName", accessToken.getString("given_name"));
																jsonObject.put("userLastName", accessToken.getString("family_name"));
																jsonObject.put("userCompleteName", accessToken.getString("name"));
																jsonObject.put("userId", accessToken.getString("sub"));
																jsonObject.put("userEmail", accessToken.getString("email"));
																userTrafficContrabandDefine(siteRequest, jsonObject, false);

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

																userService.createSiteUser(siteRequest2, e -> {
																	if(e.succeeded()) {
																		SiteUser siteUser = e.result();
																		userService.sqlPOSTSiteUser(siteUser, false, f -> {
																			if(f.succeeded()) {
																				userService.defineIndexSiteUser(siteUser, g -> {
																					if(g.succeeded()) {
																						siteRequest.setSiteUser(siteUser);
																						siteRequest.setUserName(accessToken.getString("preferred_username"));
																						siteRequest.setUserFirstName(accessToken.getString("given_name"));
																						siteRequest.setUserLastName(accessToken.getString("family_name"));
																						siteRequest.setUserEmail(accessToken.getString("email"));
																						siteRequest.setUserId(accessToken.getString("sub"));
																						siteRequest.setUserKey(siteUser.getPk());
																						eventHandler.handle(Future.succeededFuture(siteRequest));
																					} else {
																						errorTrafficContraband(siteRequest, null, g);
																					}
																				});
																			} else {
																				errorTrafficContraband(siteRequest, null, f);
																			}
																		});
																	} else {
																		errorTrafficContraband(siteRequest, null, e);
																	}
																});
															} else {
																Long pkUser = userValues.getLong(0);
																SearchList<SiteUser> searchList = new SearchList<SiteUser>();
																searchList.setQuery("*:*");
																searchList.setStore(true);
																searchList.setC(SiteUser.class);
																searchList.addFilterQuery("pk_indexed_long:" + pkUser);
																searchList.initDeepSearchList(siteRequest);
																SiteUser siteUser1 = searchList.getList().stream().findFirst().orElse(null);

																JsonObject userVertx = siteRequest.getServiceRequest().getUser();

																JsonObject jsonObject = new JsonObject();
																jsonObject.put("setUserName", accessToken.getString("preferred_username"));
																jsonObject.put("setUserFirstName", accessToken.getString("given_name"));
																jsonObject.put("setUserLastName", accessToken.getString("family_name"));
																jsonObject.put("setUserCompleteName", accessToken.getString("name"));
																jsonObject.put("setUserId", accessToken.getString("sub"));
																jsonObject.put("setUserEmail", accessToken.getString("email"));
																Boolean define = userTrafficContrabandDefine(siteRequest, jsonObject, true);
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
																	siteRequest2.setUserKey(pkUser);
																	siteRequest.setUserKey(pkUser);
																	siteRequest2.initDeepSiteRequestEnUS(siteRequest);
																	siteUser.setSiteRequest_(siteRequest2);

																	ApiRequest apiRequest = new ApiRequest();
																	apiRequest.setRows(1);
																	apiRequest.setNumFound(1L);
																	apiRequest.setNumPATCH(0L);
																	apiRequest.initDeepApiRequest(siteRequest2);
																	siteRequest2.setApiRequest_(apiRequest);

																	userService.sqlPATCHSiteUser(siteUser, false, e -> {
																		if(e.succeeded()) {
																			SiteUser siteUser2 = e.result();
																			userService.defineIndexSiteUser(siteUser2, f -> {
																				if(f.succeeded()) {
																					siteRequest.setSiteUser(siteUser2);
																					siteRequest.setUserName(siteUser2.getUserName());
																					siteRequest.setUserFirstName(siteUser2.getUserFirstName());
																					siteRequest.setUserLastName(siteUser2.getUserLastName());
																					siteRequest.setUserKey(siteUser2.getPk());
																					eventHandler.handle(Future.succeededFuture(siteRequest));
																				} else {
																					errorTrafficContraband(siteRequest, null, f);
																				}
																			});
																		} else {
																			errorTrafficContraband(siteRequest, null, e);
																		}
																	});
																} else {
																	siteRequest.setSiteUser(siteUser1);
																	siteRequest.setUserName(siteUser1.getUserName());
																	siteRequest.setUserFirstName(siteUser1.getUserFirstName());
																	siteRequest.setUserLastName(siteUser1.getUserLastName());
																	siteRequest.setUserKey(siteUser1.getPk());
																	sqlRollbackTrafficContraband(siteRequest, e -> {
																		if(e.succeeded()) {
																			eventHandler.handle(Future.succeededFuture(siteRequest));
																		} else {
																			eventHandler.handle(Future.failedFuture(e.cause()));
																			errorTrafficContraband(siteRequest, null, e);
																		}
																	});
																}
															}
														} catch(Exception ex) {
															LOG.error(String.format("userTrafficContraband failed. "), ex);
															eventHandler.handle(Future.failedFuture(ex));
														}
													} else {
														LOG.error(String.format("userTrafficContraband failed. ", selectCAsync.cause()));
														eventHandler.handle(Future.failedFuture(selectCAsync.cause()));
													}
												});
											} else {
												LOG.error(String.format("userTrafficContraband failed. ", d.cause()));
												eventHandler.handle(Future.failedFuture(d.cause()));
											}
										});
									} else {
										LOG.error(String.format("userTrafficContraband failed. ", c.cause()));
										eventHandler.handle(Future.failedFuture(c.cause()));
									}
								});
							} else {
								LOG.error(String.format("userTrafficContraband failed. ", b.cause()));
								eventHandler.handle(Future.failedFuture(b.cause()));
							}
						});
					} else {
						siteContext.getOauth2AuthenticationProvider().refresh(token, b -> {
							if(b.succeeded()) {
								User user = b.result();
								serviceRequest.setUser(user.principal());
								userTrafficContraband(serviceRequest, c -> {
									if(c.succeeded()) {
										SiteRequestEnUS siteRequest = c.result();
										eventHandler.handle(Future.succeededFuture(siteRequest));
									} else {
										LOG.error(String.format("userTrafficContraband failed. ", c.cause()));
										eventHandler.handle(Future.failedFuture(c.cause()));
									}
								});
							} else {
								LOG.error(String.format("userTrafficContraband failed. ", a.cause()));
								eventHandler.handle(Future.failedFuture(a.cause()));
							}
						});
					}
				});
			}
		} catch(Exception ex) {
			LOG.error(String.format("userTrafficContraband failed. "), ex);
			eventHandler.handle(Future.failedFuture(ex));
		}
	}

	public Boolean userTrafficContrabandDefine(SiteRequestEnUS siteRequest, JsonObject jsonObject, Boolean patch) {
		if(patch) {
			return false;
		} else {
			return false;
		}
	}

	public void aSearchTrafficContrabandQ(String uri, String apiMethod, SearchList<TrafficContraband> searchList, String entityVar, String valueIndexed, String varIndexed) {
		searchList.setQuery(varIndexed + ":" + ("*".equals(valueIndexed) ? valueIndexed : ClientUtils.escapeQueryChars(valueIndexed)));
		if(!"*".equals(entityVar)) {
		}
	}

	public String aSearchTrafficContrabandFq(String uri, String apiMethod, SearchList<TrafficContraband> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		if(StringUtils.startsWith(valueIndexed, "[")) {
			String[] fqs = StringUtils.substringBefore(StringUtils.substringAfter(valueIndexed, "["), "]").split(" TO ");
			if(fqs.length != 2)
				throw new RuntimeException(String.format("\"%s\" invalid range query. ", valueIndexed));
			String fq1 = fqs[0].equals("*") ? fqs[0] : TrafficContraband.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[0]);
			String fq2 = fqs[1].equals("*") ? fqs[1] : TrafficContraband.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[1]);
			 return varIndexed + ":[" + fq1 + " TO " + fq2 + "]";
		} else {
			return varIndexed + ":" + TrafficContraband.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), valueIndexed);
		}
	}

	public void aSearchTrafficContrabandSort(String uri, String apiMethod, SearchList<TrafficContraband> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addSort(varIndexed, ORDER.valueOf(valueIndexed));
	}

	public void aSearchTrafficContrabandRows(String uri, String apiMethod, SearchList<TrafficContraband> searchList, Integer valueRows) {
			searchList.setRows(apiMethod != null && apiMethod.contains("Search") ? valueRows : 10);
	}

	public void aSearchTrafficContrabandStart(String uri, String apiMethod, SearchList<TrafficContraband> searchList, Integer valueStart) {
		searchList.setStart(valueStart);
	}

	public void aSearchTrafficContrabandVar(String uri, String apiMethod, SearchList<TrafficContraband> searchList, String var, String value) {
		searchList.getSiteRequest_().getRequestVars().put(var, value);
	}

	public void aSearchTrafficContrabandUri(String uri, String apiMethod, SearchList<TrafficContraband> searchList) {
	}

	public void varsTrafficContraband(SiteRequestEnUS siteRequest, Handler<AsyncResult<SearchList<ServiceResponse>>> eventHandler) {
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
					LOG.error(String.format("aSearchTrafficContraband failed. "), e);
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOG.error(String.format("aSearchTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void aSearchTrafficContraband(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, Handler<AsyncResult<SearchList<TrafficContraband>>> eventHandler) {
		try {
			SearchList<TrafficContraband> searchList = aSearchTrafficContrabandList(siteRequest, populate, store, modify, uri, apiMethod);
			eventHandler.handle(Future.succeededFuture(searchList));
		} catch(Exception e) {
			LOG.error(String.format("aSearchTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SearchList<TrafficContraband> aSearchTrafficContrabandList(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod) {
		ServiceRequest serviceRequest = siteRequest.getServiceRequest();
		String entityListStr = siteRequest.getServiceRequest().getParams().getJsonObject("query").getString("fl");
		String[] entityList = entityListStr == null ? null : entityListStr.split(",\\s*");
		SearchList<TrafficContraband> searchList = new SearchList<TrafficContraband>();
		searchList.setPopulate(populate);
		searchList.setStore(store);
		searchList.setQuery("*:*");
		searchList.setC(TrafficContraband.class);
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
							varsIndexed[i] = TrafficContraband.varIndexedTrafficContraband(entityVar);
						}
						searchList.add("facet.pivot", (solrLocalParams == null ? "" : solrLocalParams) + StringUtils.join(varsIndexed, ","));
					}
				} else if(paramValuesObject != null) {
					for(Object paramObject : paramObjects) {
						switch(paramName) {
							case "q":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								varIndexed = "*".equals(entityVar) ? entityVar : TrafficContraband.varSearchTrafficContraband(entityVar);
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								valueIndexed = StringUtils.isEmpty(valueIndexed) ? "*" : valueIndexed;
								aSearchTrafficContrabandQ(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "fq":
								Matcher mFq = Pattern.compile("(\\w+):(.+?(?=(\\)|\\s+OR\\s+|\\s+AND\\s+|$)))").matcher((String)paramObject);
								boolean foundFq = mFq.find();
								if(foundFq) {
									StringBuffer sb = new StringBuffer();
									while(foundFq) {
										entityVar = mFq.group(1).trim();
										valueIndexed = mFq.group(2).trim();
										varIndexed = TrafficContraband.varIndexedTrafficContraband(entityVar);
										String entityFq = aSearchTrafficContrabandFq(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
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
								varIndexed = TrafficContraband.varIndexedTrafficContraband(entityVar);
								aSearchTrafficContrabandSort(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "start":
								valueStart = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
								aSearchTrafficContrabandStart(uri, apiMethod, searchList, valueStart);
								break;
							case "rows":
								valueRows = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
								aSearchTrafficContrabandRows(uri, apiMethod, searchList, valueRows);
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
									varIndexed = TrafficContraband.varIndexedTrafficContraband(entityVar);
									searchList.add("facet.range", (solrLocalParams == null ? "" : solrLocalParams) + varIndexed);
								}
								break;
							case "facet.field":
								entityVar = (String)paramObject;
								varIndexed = TrafficContraband.varIndexedTrafficContraband(entityVar);
								if(varIndexed != null)
									searchList.addFacetField(varIndexed);
								break;
							case "var":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								aSearchTrafficContrabandVar(uri, apiMethod, searchList, entityVar, valueIndexed);
								break;
						}
					}
					aSearchTrafficContrabandUri(uri, apiMethod, searchList);
				}
			} catch(Exception e) {
				ExceptionUtils.rethrow(e);
			}
		});
		if("*:*".equals(searchList.getQuery()) && searchList.getSorts().size() == 0) {
			searchList.addSort("created_indexed_date", ORDER.desc);
		}
		aSearchTrafficContraband2(siteRequest, populate, store, modify, uri, apiMethod, searchList);
		searchList.initDeepForClass(siteRequest);
		return searchList;
	}
	public void aSearchTrafficContraband2(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, SearchList<TrafficContraband> searchList) {
	}

	public void defineTrafficContraband(TrafficContraband o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT * FROM TrafficContraband WHERE pk=$1")
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
										LOG.error(String.format("defineTrafficContraband failed. "), e);
									}
								}
							}
						}
						eventHandler.handle(Future.succeededFuture());
					} catch(Exception e) {
						LOG.error(String.format("defineTrafficContraband failed. "), e);
						eventHandler.handle(Future.failedFuture(e));
					}
				} else {
					LOG.error(String.format("defineTrafficContraband failed. ", defineAsync.cause()));
					eventHandler.handle(Future.failedFuture(defineAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("defineTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void attributeTrafficContraband(TrafficContraband o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT searchKey as pk1, 'searchKey' from TrafficContraband where pk=$1")
					.collecting(Collectors.toList())
					.execute(Tuple.of(pk)
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
						LOG.error(String.format("attributeTrafficContraband failed. ", attributeAsync.cause()));
						eventHandler.handle(Future.failedFuture(attributeAsync.cause()));
					}
				} catch(Exception e) {
					LOG.error(String.format("attributeTrafficContraband failed. "), e);
					eventHandler.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("attributeTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void indexTrafficContraband(TrafficContraband o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			o.initDeepForClass(siteRequest);
			o.indexForClass();
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOG.error(String.format("indexTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void refreshTrafficContraband(TrafficContraband o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Boolean refresh = !"false".equals(siteRequest.getRequestVars().get("refresh"));
			if(refresh && BooleanUtils.isFalse(Optional.ofNullable(siteRequest.getApiRequest_()).map(ApiRequest::getEmpty).orElse(true))) {
				SearchList<TrafficContraband> searchList = new SearchList<TrafficContraband>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(TrafficContraband.class);
				searchList.addFilterQuery("modified_indexed_date:[" + DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(siteRequest.getApiRequest_().getCreated().toInstant(), ZoneId.of("UTC"))) + " TO *]");
				searchList.add("json.facet", "{searchKey:{terms:{field:searchKey_indexed_longs, limit:1000}}}");
				searchList.setRows(1000);
				searchList.initDeepSearchList(siteRequest);
				List<Future> futures = new ArrayList<>();

				for(int i=0; i < pks.size(); i++) {
					Long pk2 = pks.get(i);
					String classSimpleName2 = classes.get(i);

					if("TrafficSearch".equals(classSimpleName2) && pk2 != null) {
						SearchList<TrafficSearch> searchList2 = new SearchList<TrafficSearch>();
						searchList2.setStore(true);
						searchList2.setQuery("*:*");
						searchList2.setC(TrafficSearch.class);
						searchList2.addFilterQuery("pk_indexed_long:" + pk2);
						searchList2.setRows(1);
						searchList2.initDeepSearchList(siteRequest);
						TrafficSearch o2 = searchList2.getList().stream().findFirst().orElse(null);

						if(o2 != null) {
							TrafficSearchEnUSGenApiServiceImpl service = new TrafficSearchEnUSGenApiServiceImpl(siteRequest.getSiteContext_());
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForTrafficContraband(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), new JsonObject());
							ApiRequest apiRequest2 = new ApiRequest();
							apiRequest2.setRows(1);
							apiRequest2.setNumFound(1l);
							apiRequest2.setNumPATCH(0L);
							apiRequest2.initDeepApiRequest(siteRequest2);
							siteRequest2.setApiRequest_(apiRequest2);
							siteRequest2.getVertx().eventBus().publish("websocketTrafficSearch", JsonObject.mapFrom(apiRequest2).toString());

							o2.setPk(pk2);
							o2.setSiteRequest_(siteRequest2);
							futures.add(
								service.patchTrafficSearchFuture(o2, false, a -> {
									if(a.succeeded()) {
									} else {
										LOG.info(String.format("TrafficSearch %s failed. ", pk2));
										eventHandler.handle(Future.failedFuture(a.cause()));
									}
								})
							);
						}
					}
				}

				CompositeFuture.all(futures).onComplete(a -> {
					if(a.succeeded()) {
						TrafficContrabandEnUSApiServiceImpl service = new TrafficContrabandEnUSApiServiceImpl(siteRequest.getSiteContext_());
						List<Future> futures2 = new ArrayList<>();
						for(TrafficContraband o2 : searchList.getList()) {
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForTrafficContraband(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), new JsonObject());
							o2.setSiteRequest_(siteRequest2);
							futures2.add(
								service.patchTrafficContrabandFuture(o2, false, b -> {
									if(b.succeeded()) {
									} else {
										LOG.info(String.format("TrafficContraband %s failed. ", o2.getPk()));
										eventHandler.handle(Future.failedFuture(b.cause()));
									}
								})
							);
						}

						CompositeFuture.all(futures2).onComplete(b -> {
							if(b.succeeded()) {
								eventHandler.handle(Future.succeededFuture());
							} else {
								LOG.error("Refresh relations failed. ", b.cause());
								errorTrafficContraband(siteRequest, eventHandler, b);
							}
						});
					} else {
						LOG.error("Refresh relations failed. ", a.cause());
						errorTrafficContraband(siteRequest, eventHandler, a);
					}
				});
			} else {
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOG.error(String.format("refreshTrafficContraband failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}
}
