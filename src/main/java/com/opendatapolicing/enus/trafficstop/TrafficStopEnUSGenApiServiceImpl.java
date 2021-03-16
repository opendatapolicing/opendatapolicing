package com.opendatapolicing.enus.trafficstop;

import com.opendatapolicing.enus.trafficperson.TrafficPersonEnUSGenApiServiceImpl;
import com.opendatapolicing.enus.trafficperson.TrafficPerson;
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
public class TrafficStopEnUSGenApiServiceImpl implements TrafficStopEnUSGenApiService {

	protected static final Logger LOG = LoggerFactory.getLogger(TrafficStopEnUSGenApiServiceImpl.class);

	protected static final String SERVICE_ADDRESS = "TrafficStopEnUSApiServiceImpl";

	protected SiteContextEnUS siteContext;

	public TrafficStopEnUSGenApiServiceImpl(SiteContextEnUS siteContext) {
		this.siteContext = siteContext;
	}

	// PUTImport //

	@Override
	public void putimportTrafficStop(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("putimportTrafficStop started. "));
		userTrafficStop(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/traffic-stop/import");
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
						putimportTrafficStopResponse(siteRequest, c -> {
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
											siteRequest.getVertx().eventBus().publish("websocketTrafficStop", JsonObject.mapFrom(apiRequest).toString());
											varsTrafficStop(siteRequest, d -> {
												if(d.succeeded()) {
													listPUTImportTrafficStop(apiRequest, siteRequest, e -> {
														if(e.succeeded()) {
															putimportTrafficStopResponse(siteRequest, f -> {
																if(e.succeeded()) {
																	LOG.info(String.format("putimportTrafficStop succeeded. "));
																	blockingCodeHandler.handle(Future.succeededFuture(e.result()));
																} else {
																	LOG.error(String.format("putimportTrafficStop failed. ", f.cause()));
																	errorTrafficStop(siteRequest, null, f);
																}
															});
														} else {
															LOG.error(String.format("putimportTrafficStop failed. ", e.cause()));
															errorTrafficStop(siteRequest, null, e);
														}
													});
												} else {
													LOG.error(String.format("putimportTrafficStop failed. ", d.cause()));
													errorTrafficStop(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putimportTrafficStop failed. ", ex));
											errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putimportTrafficStop failed. ", c.cause()));
								errorTrafficStop(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putimportTrafficStop failed. ", ex));
					errorTrafficStop(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("putimportTrafficStop failed. ", b.cause()));
				errorTrafficStop(null, eventHandler, b);
			}
		});
	}


	public void listPUTImportTrafficStop(ApiRequest apiRequest, SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				json.put("created", json.getValue("created"));

				SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForTrafficStop(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), json);
				siteRequest2.setApiRequest_(apiRequest);
				siteRequest2.setRequestVars(siteRequest.getRequestVars());

				SearchList<TrafficStop> searchList = new SearchList<TrafficStop>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(TrafficStop.class);
				searchList.addFilterQuery("deleted_indexed_boolean:false");
				searchList.addFilterQuery("archived_indexed_boolean:false");
				searchList.addFilterQuery("inheritPk_indexed_long:" + json.getString("pk"));
				searchList.initDeepForClass(siteRequest2);

				if(searchList.size() == 1) {
					TrafficStop o = searchList.getList().stream().findFirst().orElse(null);
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
							patchTrafficStopFuture(o, true, a -> {
								if(a.succeeded()) {
								} else {
									LOG.error(String.format("listPUTImportTrafficStop failed. ", a.cause()));
									errorTrafficStop(siteRequest2, eventHandler, a);
								}
							})
						);
					}
				} else {
					futures.add(
						postTrafficStopFuture(siteRequest2, true, a -> {
							if(a.succeeded()) {
							} else {
								LOG.error(String.format("listPUTImportTrafficStop failed. ", a.cause()));
								errorTrafficStop(siteRequest2, eventHandler, a);
							}
						})
					);
				}
			});
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
					response200PUTImportTrafficStop(siteRequest, eventHandler);
				} else {
					LOG.error(String.format("listPUTImportTrafficStop failed. ", a.cause()));
					errorTrafficStop(apiRequest.getSiteRequest_(), eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("listPUTImportTrafficStop failed. ", ex));
			errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
		}
	}

	public void putimportTrafficStopResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PUTImportTrafficStop(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("putimportTrafficStopResponse failed. ", a.cause()));
					errorTrafficStop(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("putimportTrafficStopResponse failed. ", ex));
			errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTImportTrafficStop(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTImportTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTMerge //

	@Override
	public void putmergeTrafficStop(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("putmergeTrafficStop started. "));
		userTrafficStop(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/traffic-stop/merge");
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
						putmergeTrafficStopResponse(siteRequest, c -> {
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
											siteRequest.getVertx().eventBus().publish("websocketTrafficStop", JsonObject.mapFrom(apiRequest).toString());
											varsTrafficStop(siteRequest, d -> {
												if(d.succeeded()) {
													listPUTMergeTrafficStop(apiRequest, siteRequest, e -> {
														if(e.succeeded()) {
															putmergeTrafficStopResponse(siteRequest, f -> {
																if(e.succeeded()) {
																	LOG.info(String.format("putmergeTrafficStop succeeded. "));
																	blockingCodeHandler.handle(Future.succeededFuture(e.result()));
																} else {
																	LOG.error(String.format("putmergeTrafficStop failed. ", f.cause()));
																	errorTrafficStop(siteRequest, null, f);
																}
															});
														} else {
															LOG.error(String.format("putmergeTrafficStop failed. ", e.cause()));
															errorTrafficStop(siteRequest, null, e);
														}
													});
												} else {
													LOG.error(String.format("putmergeTrafficStop failed. ", d.cause()));
													errorTrafficStop(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putmergeTrafficStop failed. ", ex));
											errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putmergeTrafficStop failed. ", c.cause()));
								errorTrafficStop(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putmergeTrafficStop failed. ", ex));
					errorTrafficStop(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("putmergeTrafficStop failed. ", b.cause()));
				errorTrafficStop(null, eventHandler, b);
			}
		});
	}


	public void listPUTMergeTrafficStop(ApiRequest apiRequest, SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForTrafficStop(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), json);
				siteRequest2.setApiRequest_(apiRequest);
				siteRequest2.setRequestVars(siteRequest.getRequestVars());

				SearchList<TrafficStop> searchList = new SearchList<TrafficStop>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(TrafficStop.class);
				searchList.addFilterQuery("deleted_indexed_boolean:false");
				searchList.addFilterQuery("archived_indexed_boolean:false");
				searchList.addFilterQuery("pk_indexed_long:" + json.getString("pk"));
				searchList.initDeepForClass(siteRequest2);

				if(searchList.size() == 1) {
					TrafficStop o = searchList.getList().stream().findFirst().orElse(null);
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
							patchTrafficStopFuture(o, false, a -> {
								if(a.succeeded()) {
								} else {
									LOG.error(String.format("listPUTMergeTrafficStop failed. ", a.cause()));
									errorTrafficStop(siteRequest2, eventHandler, a);
								}
							})
						);
					}
				} else {
					futures.add(
						postTrafficStopFuture(siteRequest2, false, a -> {
							if(a.succeeded()) {
							} else {
								LOG.error(String.format("listPUTMergeTrafficStop failed. ", a.cause()));
								errorTrafficStop(siteRequest2, eventHandler, a);
							}
						})
					);
				}
			});
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
					response200PUTMergeTrafficStop(siteRequest, eventHandler);
				} else {
					LOG.error(String.format("listPUTMergeTrafficStop failed. ", a.cause()));
					errorTrafficStop(apiRequest.getSiteRequest_(), eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("listPUTMergeTrafficStop failed. ", ex));
			errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
		}
	}

	public void putmergeTrafficStopResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PUTMergeTrafficStop(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("putmergeTrafficStopResponse failed. ", a.cause()));
					errorTrafficStop(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("putmergeTrafficStopResponse failed. ", ex));
			errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTMergeTrafficStop(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTMergeTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTCopy //

	@Override
	public void putcopyTrafficStop(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("putcopyTrafficStop started. "));
		userTrafficStop(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/traffic-stop/copy");
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
						putcopyTrafficStopResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											aSearchTrafficStop(siteRequest, false, true, true, "/api/traffic-stop/copy", "PUTCopy", d -> {
												if(d.succeeded()) {
													SearchList<TrafficStop> listTrafficStop = d.result();
													ApiRequest apiRequest = new ApiRequest();
													apiRequest.setRows(listTrafficStop.getRows());
													apiRequest.setNumFound(listTrafficStop.getQueryResponse().getResults().getNumFound());
													apiRequest.setNumPATCH(0L);
													apiRequest.initDeepApiRequest(siteRequest);
													siteRequest.setApiRequest_(apiRequest);
													siteRequest.getVertx().eventBus().publish("websocketTrafficStop", JsonObject.mapFrom(apiRequest).toString());
													try {
														listPUTCopyTrafficStop(apiRequest, listTrafficStop, e -> {
															if(e.succeeded()) {
																putcopyTrafficStopResponse(siteRequest, f -> {
																	if(f.succeeded()) {
																		LOG.info(String.format("putcopyTrafficStop succeeded. "));
																		blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																	} else {
																		LOG.error(String.format("putcopyTrafficStop failed. ", f.cause()));
																		errorTrafficStop(siteRequest, null, f);
																	}
																});
															} else {
																LOG.error(String.format("putcopyTrafficStop failed. ", e.cause()));
																errorTrafficStop(siteRequest, null, e);
															}
														});
													} catch(Exception ex) {
														LOG.error(String.format("putcopyTrafficStop failed. ", ex));
														errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
													}
												} else {
													LOG.error(String.format("putcopyTrafficStop failed. ", d.cause()));
													errorTrafficStop(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putcopyTrafficStop failed. ", ex));
											errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putcopyTrafficStop failed. ", c.cause()));
								errorTrafficStop(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putcopyTrafficStop failed. ", ex));
					errorTrafficStop(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("putcopyTrafficStop failed. ", b.cause()));
				errorTrafficStop(null, eventHandler, b);
			}
		});
	}


	public void listPUTCopyTrafficStop(ApiRequest apiRequest, SearchList<TrafficStop> listTrafficStop, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listTrafficStop.getSiteRequest_();
		listTrafficStop.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForTrafficStop(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				putcopyTrafficStopFuture(siteRequest2, JsonObject.mapFrom(o), a -> {
					if(a.succeeded()) {
					} else {
						LOG.error(String.format("listPUTCopyTrafficStop failed. ", a.cause()));
						errorTrafficStop(siteRequest, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).onComplete( a -> {
			if(a.succeeded()) {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + listTrafficStop.size());
				if(listTrafficStop.next()) {
					listPUTCopyTrafficStop(apiRequest, listTrafficStop, eventHandler);
				} else {
					response200PUTCopyTrafficStop(siteRequest, eventHandler);
				}
			} else {
				LOG.error(String.format("listPUTCopyTrafficStop failed. ", a.cause()));
				errorTrafficStop(listTrafficStop.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<TrafficStop> putcopyTrafficStopFuture(SiteRequestEnUS siteRequest, JsonObject jsonObject, Handler<AsyncResult<TrafficStop>> eventHandler) {
		Promise<TrafficStop> promise = Promise.promise();
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

			sqlConnectionTrafficStop(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionTrafficStop(siteRequest, b -> {
						if(b.succeeded()) {
							createTrafficStop(siteRequest, c -> {
								if(c.succeeded()) {
									TrafficStop trafficStop = c.result();
									sqlPUTCopyTrafficStop(trafficStop, jsonObject, d -> {
										if(d.succeeded()) {
											defineIndexTrafficStop(trafficStop, e -> {
												if(e.succeeded()) {
													ApiRequest apiRequest = siteRequest.getApiRequest_();
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
													LOG.error(String.format("putcopyTrafficStopFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOG.error(String.format("putcopyTrafficStopFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOG.error(String.format("putcopyTrafficStopFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOG.error(String.format("putcopyTrafficStopFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOG.error(String.format("putcopyTrafficStopFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("putcopyTrafficStopFuture failed. "), e);
			errorTrafficStop(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPUTCopyTrafficStop(TrafficStop o, JsonObject jsonObject, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE TrafficStop SET ");
			List<Object> bParams = new ArrayList<Object>();
			TrafficStop o2 = new TrafficStop();
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
					case "stopAgencyTitle":
						o2.setStopAgencyTitle(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopAgencyTitle=$" + num);
						num++;
						bParams.add(o2.sqlStopAgencyTitle());
						break;
					case "stopDateTime":
						o2.setStopDateTime(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopDateTime=$" + num);
						num++;
						bParams.add(o2.sqlStopDateTime());
						break;
					case "stopYear":
						o2.setStopYear(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopYear=$" + num);
						num++;
						bParams.add(o2.sqlStopYear());
						break;
					case "stopPurposeNum":
						o2.setStopPurposeNum(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopPurposeNum=$" + num);
						num++;
						bParams.add(o2.sqlStopPurposeNum());
						break;
					case "stopActionNum":
						o2.setStopActionNum(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopActionNum=$" + num);
						num++;
						bParams.add(o2.sqlStopActionNum());
						break;
					case "stopDriverArrest":
						o2.setStopDriverArrest(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopDriverArrest=$" + num);
						num++;
						bParams.add(o2.sqlStopDriverArrest());
						break;
					case "stopPassengerArrest":
						o2.setStopPassengerArrest(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopPassengerArrest=$" + num);
						num++;
						bParams.add(o2.sqlStopPassengerArrest());
						break;
					case "stopEncounterForce":
						o2.setStopEncounterForce(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopEncounterForce=$" + num);
						num++;
						bParams.add(o2.sqlStopEncounterForce());
						break;
					case "stopEngageForce":
						o2.setStopEngageForce(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopEngageForce=$" + num);
						num++;
						bParams.add(o2.sqlStopEngageForce());
						break;
					case "stopOfficerInjury":
						o2.setStopOfficerInjury(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopOfficerInjury=$" + num);
						num++;
						bParams.add(o2.sqlStopOfficerInjury());
						break;
					case "stopDriverInjury":
						o2.setStopDriverInjury(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopDriverInjury=$" + num);
						num++;
						bParams.add(o2.sqlStopDriverInjury());
						break;
					case "stopPassengerInjury":
						o2.setStopPassengerInjury(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopPassengerInjury=$" + num);
						num++;
						bParams.add(o2.sqlStopPassengerInjury());
						break;
					case "stopOfficerId":
						o2.setStopOfficerId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopOfficerId=$" + num);
						num++;
						bParams.add(o2.sqlStopOfficerId());
						break;
					case "stopLocationId":
						o2.setStopLocationId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopLocationId=$" + num);
						num++;
						bParams.add(o2.sqlStopLocationId());
						break;
					case "stopCityId":
						o2.setStopCityId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopCityId=$" + num);
						num++;
						bParams.add(o2.sqlStopCityId());
						break;
					case "personKeys":
						{
							for(Long l : Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray()).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
								futures.add(Future.future(a -> {
									sqlConnection.preparedQuery("UPDATE TrafficPerson SET trafficStopKey=$1 WHERE pk=$2")
											.execute(Tuple.of(pk, l)
											, b
									-> {
										if(b.succeeded())
											a.handle(Future.succeededFuture());
										else
											a.handle(Future.failedFuture(new Exception("value TrafficStop.personKeys failed", b.cause())));
									});
								}));
								if(!pks.contains(l)) {
									pks.add(l);
									classes.add("TrafficPerson");
								}
							}
						}
						break;
					}
				}
			}
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture());
				} else {
					LOG.error(String.format("sqlPUTCopyTrafficStop failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPUTCopyTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void putcopyTrafficStopResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PUTCopyTrafficStop(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("putcopyTrafficStopResponse failed. ", a.cause()));
					errorTrafficStop(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("putcopyTrafficStopResponse failed. ", ex));
			errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTCopyTrafficStop(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTCopyTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// POST //

	@Override
	public void postTrafficStop(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("postTrafficStop started. "));
		userTrafficStop(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/traffic-stop");
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
						siteRequest.getVertx().eventBus().publish("websocketTrafficStop", JsonObject.mapFrom(apiRequest).toString());
						postTrafficStopFuture(siteRequest, false, c -> {
							if(c.succeeded()) {
								TrafficStop trafficStop = c.result();
								apiRequest.setPk(trafficStop.getPk());
								postTrafficStopResponse(trafficStop, d -> {
										if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("postTrafficStop succeeded. "));
									} else {
										LOG.error(String.format("postTrafficStop failed. ", d.cause()));
										errorTrafficStop(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("postTrafficStop failed. ", c.cause()));
								errorTrafficStop(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("postTrafficStop failed. ", ex));
					errorTrafficStop(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("postTrafficStop failed. ", b.cause()));
				errorTrafficStop(null, eventHandler, b);
			}
		});
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
													LOG.error(String.format("postTrafficStopFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOG.error(String.format("postTrafficStopFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOG.error(String.format("postTrafficStopFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOG.error(String.format("postTrafficStopFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOG.error(String.format("postTrafficStopFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("postTrafficStopFuture failed. "), e);
			errorTrafficStop(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPOSTTrafficStop(TrafficStop o, Boolean inheritPk, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE TrafficStop SET ");
			List<Object> bParams = new ArrayList<Object>();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			TrafficStop o2 = new TrafficStop();
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
					case "stopAgencyTitle":
						o2.setStopAgencyTitle(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopAgencyTitle=$" + num);
						num++;
						bParams.add(o2.sqlStopAgencyTitle());
						break;
					case "stopDateTime":
						o2.setStopDateTime(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopDateTime=$" + num);
						num++;
						bParams.add(o2.sqlStopDateTime());
						break;
					case "stopYear":
						o2.setStopYear(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopYear=$" + num);
						num++;
						bParams.add(o2.sqlStopYear());
						break;
					case "stopPurposeNum":
						o2.setStopPurposeNum(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopPurposeNum=$" + num);
						num++;
						bParams.add(o2.sqlStopPurposeNum());
						break;
					case "stopActionNum":
						o2.setStopActionNum(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopActionNum=$" + num);
						num++;
						bParams.add(o2.sqlStopActionNum());
						break;
					case "stopDriverArrest":
						o2.setStopDriverArrest(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopDriverArrest=$" + num);
						num++;
						bParams.add(o2.sqlStopDriverArrest());
						break;
					case "stopPassengerArrest":
						o2.setStopPassengerArrest(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopPassengerArrest=$" + num);
						num++;
						bParams.add(o2.sqlStopPassengerArrest());
						break;
					case "stopEncounterForce":
						o2.setStopEncounterForce(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopEncounterForce=$" + num);
						num++;
						bParams.add(o2.sqlStopEncounterForce());
						break;
					case "stopEngageForce":
						o2.setStopEngageForce(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopEngageForce=$" + num);
						num++;
						bParams.add(o2.sqlStopEngageForce());
						break;
					case "stopOfficerInjury":
						o2.setStopOfficerInjury(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopOfficerInjury=$" + num);
						num++;
						bParams.add(o2.sqlStopOfficerInjury());
						break;
					case "stopDriverInjury":
						o2.setStopDriverInjury(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopDriverInjury=$" + num);
						num++;
						bParams.add(o2.sqlStopDriverInjury());
						break;
					case "stopPassengerInjury":
						o2.setStopPassengerInjury(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopPassengerInjury=$" + num);
						num++;
						bParams.add(o2.sqlStopPassengerInjury());
						break;
					case "stopOfficerId":
						o2.setStopOfficerId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopOfficerId=$" + num);
						num++;
						bParams.add(o2.sqlStopOfficerId());
						break;
					case "stopLocationId":
						o2.setStopLocationId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopLocationId=$" + num);
						num++;
						bParams.add(o2.sqlStopLocationId());
						break;
					case "stopCityId":
						o2.setStopCityId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stopCityId=$" + num);
						num++;
						bParams.add(o2.sqlStopCityId());
						break;
					case "personKeys":
						for(Long l : Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray()).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							if(l != null) {
								SearchList<com.opendatapolicing.enus.trafficperson.TrafficPerson> searchList = new SearchList<com.opendatapolicing.enus.trafficperson.TrafficPerson>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(com.opendatapolicing.enus.trafficperson.TrafficPerson.class);
								searchList.addFilterQuery("deleted_indexed_boolean:false");
								searchList.addFilterQuery("archived_indexed_boolean:false");
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE TrafficPerson SET trafficStopKey=$1 WHERE pk=$2")
												.execute(Tuple.of(pk, l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficStop.personKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("TrafficPerson");
									}
								}
							}
						}
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
					LOG.error(String.format("sqlPOSTTrafficStop failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPOSTTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void postTrafficStopResponse(TrafficStop trafficStop, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = trafficStop.getSiteRequest_();
		try {
			response200POSTTrafficStop(trafficStop, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("postTrafficStopResponse failed. ", a.cause()));
					errorTrafficStop(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("postTrafficStopResponse failed. ", ex));
			errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200POSTTrafficStop(TrafficStop o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200POSTTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchTrafficStop(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("patchTrafficStop started. "));
		userTrafficStop(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/traffic-stop");
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
						patchTrafficStopResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											aSearchTrafficStop(siteRequest, false, true, true, "/api/traffic-stop", "PATCH", d -> {
												if(d.succeeded()) {
													SearchList<TrafficStop> listTrafficStop = d.result();

													List<String> roles2 = Arrays.asList("SiteAdmin");
													if(listTrafficStop.getQueryResponse().getResults().getNumFound() > 1
															&& !CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles2)
															&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles2)
															) {
														String message = String.format("roles required: " + String.join(", ", roles2));
														LOG.error(message);
														errorTrafficStop(siteRequest, eventHandler, Future.failedFuture(message));
													} else {

														ApiRequest apiRequest = new ApiRequest();
														apiRequest.setRows(listTrafficStop.getRows());
														apiRequest.setNumFound(listTrafficStop.getQueryResponse().getResults().getNumFound());
														apiRequest.setNumPATCH(0L);
														apiRequest.initDeepApiRequest(siteRequest);
														siteRequest.setApiRequest_(apiRequest);
														siteRequest.getVertx().eventBus().publish("websocketTrafficStop", JsonObject.mapFrom(apiRequest).toString());
														SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listTrafficStop.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(null);
														Date date = null;
														if(facets != null)
														date = (Date)facets.get("max_modified");
														String dt;
														if(date == null)
															dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));
														else
															dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")));
														listTrafficStop.addFilterQuery(String.format("modified_indexed_date:[* TO %s]", dt));

														try {
															listPATCHTrafficStop(apiRequest, listTrafficStop, dt, e -> {
																if(e.succeeded()) {
																	patchTrafficStopResponse(siteRequest, f -> {
																		if(f.succeeded()) {
																			LOG.info(String.format("patchTrafficStop succeeded. "));
																			blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																		} else {
																			LOG.error(String.format("patchTrafficStop failed. ", f.cause()));
																			errorTrafficStop(siteRequest, null, f);
																		}
																	});
																} else {
																	LOG.error(String.format("patchTrafficStop failed. ", e.cause()));
																	errorTrafficStop(siteRequest, null, e);
																}
															});
														} catch(Exception ex) {
															LOG.error(String.format("patchTrafficStop failed. ", ex));
															errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
														}
													}
										} else {
													LOG.error(String.format("patchTrafficStop failed. ", d.cause()));
													errorTrafficStop(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("patchTrafficStop failed. ", ex));
											errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("patchTrafficStop failed. ", c.cause()));
								errorTrafficStop(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("patchTrafficStop failed. ", ex));
					errorTrafficStop(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("patchTrafficStop failed. ", b.cause()));
				errorTrafficStop(null, eventHandler, b);
			}
		});
	}


	public void listPATCHTrafficStop(ApiRequest apiRequest, SearchList<TrafficStop> listTrafficStop, String dt, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listTrafficStop.getSiteRequest_();
		listTrafficStop.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForTrafficStop(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), siteRequest.getJsonObject());
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
		CompositeFuture.all(futures).onComplete( a -> {
			if(a.succeeded()) {
				if(listTrafficStop.next(dt)) {
					listPATCHTrafficStop(apiRequest, listTrafficStop, dt, eventHandler);
				} else {
					response200PATCHTrafficStop(siteRequest, eventHandler);
				}
			} else {
				LOG.error(String.format("listPATCHTrafficStop failed. ", a.cause()));
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
											LOG.error(String.format("patchTrafficStopFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOG.error(String.format("patchTrafficStopFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOG.error(String.format("patchTrafficStopFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOG.error(String.format("patchTrafficStopFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("patchTrafficStopFuture failed. "), e);
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
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE TrafficStop SET ");
			List<Object> bParams = new ArrayList<Object>();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			TrafficStop o2 = new TrafficStop();
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
					case "setStopAgencyTitle":
							o2.setStopAgencyTitle(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("stopAgencyTitle=$" + num);
							num++;
							bParams.add(o2.sqlStopAgencyTitle());
						break;
					case "setStopDateTime":
							o2.setStopDateTime(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("stopDateTime=$" + num);
							num++;
							bParams.add(o2.sqlStopDateTime());
						break;
					case "setStopYear":
							o2.setStopYear(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("stopYear=$" + num);
							num++;
							bParams.add(o2.sqlStopYear());
						break;
					case "setStopPurposeNum":
							o2.setStopPurposeNum(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("stopPurposeNum=$" + num);
							num++;
							bParams.add(o2.sqlStopPurposeNum());
						break;
					case "setStopActionNum":
							o2.setStopActionNum(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("stopActionNum=$" + num);
							num++;
							bParams.add(o2.sqlStopActionNum());
						break;
					case "setStopDriverArrest":
							o2.setStopDriverArrest(jsonObject.getBoolean(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("stopDriverArrest=$" + num);
							num++;
							bParams.add(o2.sqlStopDriverArrest());
						break;
					case "setStopPassengerArrest":
							o2.setStopPassengerArrest(jsonObject.getBoolean(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("stopPassengerArrest=$" + num);
							num++;
							bParams.add(o2.sqlStopPassengerArrest());
						break;
					case "setStopEncounterForce":
							o2.setStopEncounterForce(jsonObject.getBoolean(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("stopEncounterForce=$" + num);
							num++;
							bParams.add(o2.sqlStopEncounterForce());
						break;
					case "setStopEngageForce":
							o2.setStopEngageForce(jsonObject.getBoolean(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("stopEngageForce=$" + num);
							num++;
							bParams.add(o2.sqlStopEngageForce());
						break;
					case "setStopOfficerInjury":
							o2.setStopOfficerInjury(jsonObject.getBoolean(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("stopOfficerInjury=$" + num);
							num++;
							bParams.add(o2.sqlStopOfficerInjury());
						break;
					case "setStopDriverInjury":
							o2.setStopDriverInjury(jsonObject.getBoolean(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("stopDriverInjury=$" + num);
							num++;
							bParams.add(o2.sqlStopDriverInjury());
						break;
					case "setStopPassengerInjury":
							o2.setStopPassengerInjury(jsonObject.getBoolean(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("stopPassengerInjury=$" + num);
							num++;
							bParams.add(o2.sqlStopPassengerInjury());
						break;
					case "setStopOfficerId":
							o2.setStopOfficerId(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("stopOfficerId=$" + num);
							num++;
							bParams.add(o2.sqlStopOfficerId());
						break;
					case "setStopLocationId":
							o2.setStopLocationId(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("stopLocationId=$" + num);
							num++;
							bParams.add(o2.sqlStopLocationId());
						break;
					case "setStopCityId":
							o2.setStopCityId(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("stopCityId=$" + num);
							num++;
							bParams.add(o2.sqlStopCityId());
						break;
					case "addPersonKeys":
						{
							Long l = Long.parseLong(jsonObject.getString(methodName));
							if(l != null) {
								SearchList<com.opendatapolicing.enus.trafficperson.TrafficPerson> searchList = new SearchList<com.opendatapolicing.enus.trafficperson.TrafficPerson>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(com.opendatapolicing.enus.trafficperson.TrafficPerson.class);
								searchList.addFilterQuery("deleted_indexed_boolean:false");
								searchList.addFilterQuery("archived_indexed_boolean:false");
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && !o.getPersonKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE TrafficPerson SET trafficStopKey=$1 WHERE pk=$2")
												.execute(Tuple.of(pk, l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficStop.personKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("TrafficPerson");
									}
								}
							}
						}
						break;
					case "addAllPersonKeys":
						JsonArray addAllPersonKeysValues = jsonObject.getJsonArray(methodName);
						if(addAllPersonKeysValues != null) {
							for(Integer i = 0; i <  addAllPersonKeysValues.size(); i++) {
								Long l = Long.parseLong(addAllPersonKeysValues.getString(i));
								if(l != null) {
									SearchList<com.opendatapolicing.enus.trafficperson.TrafficPerson> searchList = new SearchList<com.opendatapolicing.enus.trafficperson.TrafficPerson>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(com.opendatapolicing.enus.trafficperson.TrafficPerson.class);
									searchList.addFilterQuery("deleted_indexed_boolean:false");
									searchList.addFilterQuery("archived_indexed_boolean:false");
									searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null && !o.getPersonKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE TrafficPerson SET trafficStopKey=$1 WHERE pk=$2")
												.execute(Tuple.of(pk, l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficStop.personKeys failed", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("TrafficPerson");
										}
									}
								}
							}
						}
						break;
					case "setPersonKeys":
						JsonArray setPersonKeysValues = jsonObject.getJsonArray(methodName);
						JsonArray setPersonKeysValues2 = new JsonArray();
						if(setPersonKeysValues != null) {
							for(Integer i = 0; i <  setPersonKeysValues.size(); i++) {
								Long l = Long.parseLong(setPersonKeysValues.getString(i));
								if(l != null) {
									SearchList<com.opendatapolicing.enus.trafficperson.TrafficPerson> searchList = new SearchList<com.opendatapolicing.enus.trafficperson.TrafficPerson>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(com.opendatapolicing.enus.trafficperson.TrafficPerson.class);
									searchList.addFilterQuery("deleted_indexed_boolean:false");
									searchList.addFilterQuery("archived_indexed_boolean:false");
									searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null)
										setPersonKeysValues2.add(l2);
									if(l2 != null && !o.getPersonKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE TrafficPerson SET trafficStopKey=$1 WHERE pk=$2")
												.execute(Tuple.of(pk, l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficStop.personKeys failed", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("TrafficPerson");
										}
									}
								}
							}
						}
						if(o.getPersonKeys() != null) {
							for(Long l :  o.getPersonKeys()) {
								if(l != null && (setPersonKeysValues2 == null || !setPersonKeysValues2.contains(l))) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE TrafficPerson SET trafficStopKey=null WHERE pk=$1")
												.execute(Tuple.of(l)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficStop.personKeys failed", b.cause())));
										});
									}));
								}
							}
						}
						break;
					case "removePersonKeys":
						{
							Long l = Long.parseLong(jsonObject.getString(methodName));
							if(l != null) {
								SearchList<com.opendatapolicing.enus.trafficperson.TrafficPerson> searchList = new SearchList<com.opendatapolicing.enus.trafficperson.TrafficPerson>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(com.opendatapolicing.enus.trafficperson.TrafficPerson.class);
								searchList.addFilterQuery("deleted_indexed_boolean:false");
								searchList.addFilterQuery("archived_indexed_boolean:false");
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && o.getPersonKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE TrafficPerson SET trafficStopKey=null WHERE pk=$1")
												.execute(Tuple.of(l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficStop.personKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("TrafficPerson");
									}
								}
							}
						}
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
					TrafficStop o3 = new TrafficStop();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
					eventHandler.handle(Future.succeededFuture(o3));
				} else {
					LOG.error(String.format("sqlPATCHTrafficStop failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPATCHTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void patchTrafficStopResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PATCHTrafficStop(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("patchTrafficStopResponse failed. ", a.cause()));
					errorTrafficStop(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("patchTrafficStopResponse failed. ", ex));
			errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PATCHTrafficStop(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PATCHTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// GET //

	@Override
	public void getTrafficStop(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		userTrafficStop(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/traffic-stop/{id}");
					siteRequest.setRequestMethod("GET");
					{
						aSearchTrafficStop(siteRequest, false, true, false, "/api/traffic-stop/{id}", "GET", c -> {
							if(c.succeeded()) {
								SearchList<TrafficStop> listTrafficStop = c.result();
								getTrafficStopResponse(listTrafficStop, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("getTrafficStop succeeded. "));
									} else {
										LOG.error(String.format("getTrafficStop failed. ", d.cause()));
										errorTrafficStop(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("getTrafficStop failed. ", c.cause()));
								errorTrafficStop(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("getTrafficStop failed. ", ex));
					errorTrafficStop(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("getTrafficStop failed. ", b.cause()));
				errorTrafficStop(null, eventHandler, b);
			}
		});
	}


	public void getTrafficStopResponse(SearchList<TrafficStop> listTrafficStop, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficStop.getSiteRequest_();
		try {
			response200GETTrafficStop(listTrafficStop, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("getTrafficStopResponse failed. ", a.cause()));
					errorTrafficStop(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("getTrafficStopResponse failed. ", ex));
			errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200GETTrafficStop(SearchList<TrafficStop> listTrafficStop, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listTrafficStop.getSiteRequest_();
			SolrDocumentList solrDocuments = listTrafficStop.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listTrafficStop.getList().stream().findFirst().orElse(null));
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200GETTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// Search //

	@Override
	public void searchTrafficStop(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		userTrafficStop(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/traffic-stop");
					siteRequest.setRequestMethod("Search");
					{
						aSearchTrafficStop(siteRequest, false, true, false, "/api/traffic-stop", "Search", c -> {
							if(c.succeeded()) {
								SearchList<TrafficStop> listTrafficStop = c.result();
								searchTrafficStopResponse(listTrafficStop, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("searchTrafficStop succeeded. "));
									} else {
										LOG.error(String.format("searchTrafficStop failed. ", d.cause()));
										errorTrafficStop(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("searchTrafficStop failed. ", c.cause()));
								errorTrafficStop(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("searchTrafficStop failed. ", ex));
					errorTrafficStop(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("searchTrafficStop failed. ", b.cause()));
				errorTrafficStop(null, eventHandler, b);
			}
		});
	}


	public void searchTrafficStopResponse(SearchList<TrafficStop> listTrafficStop, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficStop.getSiteRequest_();
		try {
			response200SearchTrafficStop(listTrafficStop, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("searchTrafficStopResponse failed. ", a.cause()));
					errorTrafficStop(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("searchTrafficStopResponse failed. ", ex));
			errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchTrafficStop(SearchList<TrafficStop> listTrafficStop, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listTrafficStop.getSiteRequest_();
			QueryResponse responseSearch = listTrafficStop.getQueryResponse();
			SolrDocumentList solrDocuments = listTrafficStop.getSolrDocumentList();
			Long searchInMillis = Long.valueOf(responseSearch.getQTime());
			Long transmissionInMillis = responseSearch.getElapsedTime();
			Long startNum = responseSearch.getResults().getStart();
			Long foundNum = responseSearch.getResults().getNumFound();
			Integer returnedNum = responseSearch.getResults().size();
			String searchTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(searchInMillis), TimeUnit.MILLISECONDS.toMillis(searchInMillis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(searchInMillis)));
			String transmissionTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis), TimeUnit.MILLISECONDS.toMillis(transmissionInMillis) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis)));
			Exception exceptionSearch = responseSearch.getException();
			List<String> fls = listTrafficStop.getFields();

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
				json.put("searchTime", searchTime);
				json.put("transmissionTime", transmissionTime);
			}
			JsonArray l = new JsonArray();
			listTrafficStop.getList().stream().forEach(o -> {
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
					responsePivotSearchTrafficStop(pivotFields, pivotArray);
				}
			}
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200SearchTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}
	public void responsePivotSearchTrafficStop(List<PivotField> pivotFields, JsonArray pivotArray) {
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
				responsePivotSearchTrafficStop(pivotFields2, pivotArray2);
			}
		}
	}

	// AdminSearch //

	@Override
	public void adminsearchTrafficStop(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		userTrafficStop(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/admin/traffic-stop");
					siteRequest.setRequestMethod("AdminSearch");
					{
						aSearchTrafficStop(siteRequest, false, true, false, "/api/admin/traffic-stop", "AdminSearch", c -> {
							if(c.succeeded()) {
								SearchList<TrafficStop> listTrafficStop = c.result();
								adminsearchTrafficStopResponse(listTrafficStop, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("adminsearchTrafficStop succeeded. "));
									} else {
										LOG.error(String.format("adminsearchTrafficStop failed. ", d.cause()));
										errorTrafficStop(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("adminsearchTrafficStop failed. ", c.cause()));
								errorTrafficStop(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("adminsearchTrafficStop failed. ", ex));
					errorTrafficStop(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("adminsearchTrafficStop failed. ", b.cause()));
				errorTrafficStop(null, eventHandler, b);
			}
		});
	}


	public void adminsearchTrafficStopResponse(SearchList<TrafficStop> listTrafficStop, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficStop.getSiteRequest_();
		try {
			response200AdminSearchTrafficStop(listTrafficStop, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("adminsearchTrafficStopResponse failed. ", a.cause()));
					errorTrafficStop(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("adminsearchTrafficStopResponse failed. ", ex));
			errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200AdminSearchTrafficStop(SearchList<TrafficStop> listTrafficStop, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listTrafficStop.getSiteRequest_();
			QueryResponse responseSearch = listTrafficStop.getQueryResponse();
			SolrDocumentList solrDocuments = listTrafficStop.getSolrDocumentList();
			Long searchInMillis = Long.valueOf(responseSearch.getQTime());
			Long transmissionInMillis = responseSearch.getElapsedTime();
			Long startNum = responseSearch.getResults().getStart();
			Long foundNum = responseSearch.getResults().getNumFound();
			Integer returnedNum = responseSearch.getResults().size();
			String searchTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(searchInMillis), TimeUnit.MILLISECONDS.toMillis(searchInMillis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(searchInMillis)));
			String transmissionTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis), TimeUnit.MILLISECONDS.toMillis(transmissionInMillis) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis)));
			Exception exceptionSearch = responseSearch.getException();
			List<String> fls = listTrafficStop.getFields();

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
				json.put("searchTime", searchTime);
				json.put("transmissionTime", transmissionTime);
			}
			JsonArray l = new JsonArray();
			listTrafficStop.getList().stream().forEach(o -> {
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
					responsePivotAdminSearchTrafficStop(pivotFields, pivotArray);
				}
			}
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200AdminSearchTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}
	public void responsePivotAdminSearchTrafficStop(List<PivotField> pivotFields, JsonArray pivotArray) {
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
				responsePivotAdminSearchTrafficStop(pivotFields2, pivotArray2);
			}
		}
	}

	// SearchPage //

	@Override
	public void searchpageTrafficStopId(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		searchpageTrafficStop(serviceRequest, eventHandler);
	}

	@Override
	public void searchpageTrafficStop(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		userTrafficStop(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/traffic-stop");
					siteRequest.setRequestMethod("SearchPage");
					{
						aSearchTrafficStop(siteRequest, false, true, false, "/traffic-stop", "SearchPage", c -> {
							if(c.succeeded()) {
								SearchList<TrafficStop> listTrafficStop = c.result();
								searchpageTrafficStopResponse(listTrafficStop, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("searchpageTrafficStop succeeded. "));
									} else {
										LOG.error(String.format("searchpageTrafficStop failed. ", d.cause()));
										errorTrafficStop(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("searchpageTrafficStop failed. ", c.cause()));
								errorTrafficStop(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("searchpageTrafficStop failed. ", ex));
					errorTrafficStop(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("searchpageTrafficStop failed. ", b.cause()));
				errorTrafficStop(null, eventHandler, b);
			}
		});
	}


	public void searchpageTrafficStopPageInit(TrafficStopPage page, SearchList<TrafficStop> listTrafficStop) {
	}
	public void searchpageTrafficStopResponse(SearchList<TrafficStop> listTrafficStop, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficStop.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200SearchPageTrafficStop(listTrafficStop, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("searchpageTrafficStopResponse failed. ", a.cause()));
					errorTrafficStop(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("searchpageTrafficStopResponse failed. ", ex));
			errorTrafficStop(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchPageTrafficStop(SearchList<TrafficStop> listTrafficStop, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listTrafficStop.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listTrafficStop.getSiteRequest_(), buffer);
			TrafficStopPage page = new TrafficStopPage();
			SolrDocument pageSolrDocument = new SolrDocument();
			MultiMap requestHeaders = MultiMap.caseInsensitiveMultiMap();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/traffic-stop");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listTrafficStop.size() == 1)
				siteRequest.setRequestPk(listTrafficStop.get(0).getPk());
			siteRequest.setW(w);
			page.setListTrafficStop(listTrafficStop);
			page.setSiteRequest_(siteRequest);
			searchpageTrafficStopPageInit(page, listTrafficStop);
			page.initDeepTrafficStopPage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new ServiceResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOG.error(String.format("response200SearchPageTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// General //

	public Future<TrafficStop> defineIndexTrafficStop(TrafficStop trafficStop, Handler<AsyncResult<TrafficStop>> eventHandler) {
		Promise<TrafficStop> promise = Promise.promise();
		SiteRequestEnUS siteRequest = trafficStop.getSiteRequest_();
		defineTrafficStop(trafficStop, c -> {
			if(c.succeeded()) {
				attributeTrafficStop(trafficStop, d -> {
					if(d.succeeded()) {
						indexTrafficStop(trafficStop, e -> {
							if(e.succeeded()) {
								sqlCommitTrafficStop(siteRequest, f -> {
									if(f.succeeded()) {
										sqlCloseTrafficStop(siteRequest, g -> {
											if(g.succeeded()) {
												refreshTrafficStop(trafficStop, h -> {
													if(h.succeeded()) {
														eventHandler.handle(Future.succeededFuture(trafficStop));
														promise.complete(trafficStop);
													} else {
														LOG.error(String.format("refreshTrafficStop failed. ", h.cause()));
														errorTrafficStop(siteRequest, null, h);
													}
												});
											} else {
												LOG.error(String.format("defineIndexTrafficStop failed. ", g.cause()));
												errorTrafficStop(siteRequest, null, g);
											}
										});
									} else {
										LOG.error(String.format("defineIndexTrafficStop failed. ", f.cause()));
										errorTrafficStop(siteRequest, null, f);
									}
								});
							} else {
								LOG.error(String.format("defineIndexTrafficStop failed. ", e.cause()));
								errorTrafficStop(siteRequest, null, e);
							}
						});
					} else {
						LOG.error(String.format("defineIndexTrafficStop failed. ", d.cause()));
						errorTrafficStop(siteRequest, null, d);
					}
				});
			} else {
				LOG.error(String.format("defineIndexTrafficStop failed. ", c.cause()));
				errorTrafficStop(siteRequest, null, c);
			}
		});
		return promise.future();
	}

	public void createTrafficStop(SiteRequestEnUS siteRequest, Handler<AsyncResult<TrafficStop>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			String userId = siteRequest.getUserId();
			Long userKey = siteRequest.getUserKey();
			ZonedDateTime created = Optional.ofNullable(siteRequest.getJsonObject()).map(j -> j.getString("created")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())))).orElse(ZonedDateTime.now(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())));

			sqlConnection.preparedQuery("INSERT INTO TrafficStop(created, userKey) VALUES($1, $2) RETURNING pk")
					.collecting(Collectors.toList())
					.execute(Tuple.of(created.toOffsetDateTime(), userKey)
					, createAsync
			-> {
				if(createAsync.succeeded()) {
					Row createLine = createAsync.result().value().stream().findFirst().orElseGet(() -> null);
					Long pk = createLine.getLong(0);
					TrafficStop o = new TrafficStop();
					o.setPk(pk);
					o.setSiteRequest_(siteRequest);
					eventHandler.handle(Future.succeededFuture(o));
				} else {
					LOG.error(String.format("createTrafficStop failed. ", createAsync.cause()));
					eventHandler.handle(Future.failedFuture(createAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("createTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void errorTrafficStop(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler, AsyncResult<?> resultAsync) {
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
			sqlRollbackTrafficStop(siteRequest, a -> {
				if(a.succeeded()) {
					LOG.info(String.format("sql rollback. "));
					sqlCloseTrafficStop(siteRequest, b -> {
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

	public void sqlConnectionTrafficStop(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
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
						LOG.error(String.format("sqlConnectionTrafficStop failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlTransactionTrafficStop(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();

			if(sqlConnection == null) {
				eventHandler.handle(Future.failedFuture("sqlTransactionCloseTrafficStop failed, connection should not be null. "));
			} else {
				sqlConnection.begin(a -> {
					Transaction tx = a.result();
					siteRequest.setTx(tx);
					eventHandler.handle(Future.succeededFuture());
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlTransactionTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCommitTrafficStop(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();

			if(tx == null) {
				eventHandler.handle(Future.failedFuture("sqlCommitCloseTrafficStop failed, tx should not be null. "));
			} else {
				tx.commit(a -> {
					if(a.succeeded()) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else if("Transaction already completed".equals(a.cause().getMessage())) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOG.error(String.format("sqlCommitTrafficStop failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlRollbackTrafficStop(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();

			if(tx == null) {
				eventHandler.handle(Future.failedFuture("sqlRollbackCloseTrafficStop failed, tx should not be null. "));
			} else {
				tx.rollback(a -> {
					if(a.succeeded()) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else if("Transaction already completed".equals(a.cause().getMessage())) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOG.error(String.format("sqlRollbackTrafficStop failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCloseTrafficStop(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();

			if(sqlConnection == null) {
				eventHandler.handle(Future.failedFuture("sqlCloseTrafficStop failed, connection should not be null. "));
			} else {
				sqlConnection.close(a -> {
					if(a.succeeded()) {
						siteRequest.setSqlConnection(null);
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOG.error(String.format("sqlCloseTrafficStop failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlCloseTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SiteRequestEnUS generateSiteRequestEnUSForTrafficStop(User user, SiteContextEnUS siteContext, ServiceRequest serviceRequest) {
		return generateSiteRequestEnUSForTrafficStop(user, siteContext, serviceRequest, null);
	}

	public SiteRequestEnUS generateSiteRequestEnUSForTrafficStop(User user, SiteContextEnUS siteContext, ServiceRequest serviceRequest, JsonObject body) {
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

	public void userTrafficStop(ServiceRequest serviceRequest, Handler<AsyncResult<SiteRequestEnUS>> eventHandler) {
		try {
			JsonObject userJson = serviceRequest.getUser();
			if(userJson == null) {
				SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficStop(null, siteContext, serviceRequest);
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
								SiteRequestEnUS siteRequest = generateSiteRequestEnUSForTrafficStop(user, siteContext, serviceRequest);
								sqlConnectionTrafficStop(siteRequest, c -> {
									if(c.succeeded()) {
										sqlTransactionTrafficStop(siteRequest, d -> {
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
																userTrafficStopDefine(siteRequest, jsonObject, false);

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
																						errorTrafficStop(siteRequest, null, g);
																					}
																				});
																			} else {
																				errorTrafficStop(siteRequest, null, f);
																			}
																		});
																	} else {
																		errorTrafficStop(siteRequest, null, e);
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
																Boolean define = userTrafficStopDefine(siteRequest, jsonObject, true);
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
																					errorTrafficStop(siteRequest, null, f);
																				}
																			});
																		} else {
																			errorTrafficStop(siteRequest, null, e);
																		}
																	});
																} else {
																	siteRequest.setSiteUser(siteUser1);
																	siteRequest.setUserName(siteUser1.getUserName());
																	siteRequest.setUserFirstName(siteUser1.getUserFirstName());
																	siteRequest.setUserLastName(siteUser1.getUserLastName());
																	siteRequest.setUserKey(siteUser1.getPk());
																	sqlRollbackTrafficStop(siteRequest, e -> {
																		if(e.succeeded()) {
																			eventHandler.handle(Future.succeededFuture(siteRequest));
																		} else {
																			eventHandler.handle(Future.failedFuture(e.cause()));
																			errorTrafficStop(siteRequest, null, e);
																		}
																	});
																}
															}
														} catch(Exception ex) {
															LOG.error(String.format("userTrafficStop failed. "), ex);
															eventHandler.handle(Future.failedFuture(ex));
														}
													} else {
														LOG.error(String.format("userTrafficStop failed. ", selectCAsync.cause()));
														eventHandler.handle(Future.failedFuture(selectCAsync.cause()));
													}
												});
											} else {
												LOG.error(String.format("userTrafficStop failed. ", d.cause()));
												eventHandler.handle(Future.failedFuture(d.cause()));
											}
										});
									} else {
										LOG.error(String.format("userTrafficStop failed. ", c.cause()));
										eventHandler.handle(Future.failedFuture(c.cause()));
									}
								});
							} else {
								LOG.error(String.format("userTrafficStop failed. ", b.cause()));
								eventHandler.handle(Future.failedFuture(b.cause()));
							}
						});
					} else {
						siteContext.getOauth2AuthenticationProvider().refresh(token, b -> {
							if(b.succeeded()) {
								User user = b.result();
								serviceRequest.setUser(user.principal());
								userTrafficStop(serviceRequest, c -> {
									if(c.succeeded()) {
										SiteRequestEnUS siteRequest = c.result();
										eventHandler.handle(Future.succeededFuture(siteRequest));
									} else {
										LOG.error(String.format("userTrafficStop failed. ", c.cause()));
										eventHandler.handle(Future.failedFuture(c.cause()));
									}
								});
							} else {
								LOG.error(String.format("userTrafficStop failed. ", a.cause()));
								eventHandler.handle(Future.failedFuture(a.cause()));
							}
						});
					}
				});
			}
		} catch(Exception ex) {
			LOG.error(String.format("userTrafficStop failed. "), ex);
			eventHandler.handle(Future.failedFuture(ex));
		}
	}

	public Boolean userTrafficStopDefine(SiteRequestEnUS siteRequest, JsonObject jsonObject, Boolean patch) {
		if(patch) {
			return false;
		} else {
			return false;
		}
	}

	public void aSearchTrafficStopQ(String uri, String apiMethod, SearchList<TrafficStop> searchList, String entityVar, String valueIndexed, String varIndexed) {
		searchList.setQuery(varIndexed + ":" + ("*".equals(valueIndexed) ? valueIndexed : ClientUtils.escapeQueryChars(valueIndexed)));
		if(!"*".equals(entityVar)) {
		}
	}

	public String aSearchTrafficStopFq(String uri, String apiMethod, SearchList<TrafficStop> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		if(StringUtils.startsWith(valueIndexed, "[")) {
			String[] fqs = StringUtils.substringBefore(StringUtils.substringAfter(valueIndexed, "["), "]").split(" TO ");
			if(fqs.length != 2)
				throw new RuntimeException(String.format("\"%s\" invalid range query. ", valueIndexed));
			String fq1 = fqs[0].equals("*") ? fqs[0] : TrafficStop.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[0]);
			String fq2 = fqs[1].equals("*") ? fqs[1] : TrafficStop.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[1]);
			 return varIndexed + ":[" + fq1 + " TO " + fq2 + "]";
		} else {
			return varIndexed + ":" + TrafficStop.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), valueIndexed);
		}
	}

	public void aSearchTrafficStopSort(String uri, String apiMethod, SearchList<TrafficStop> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addSort(varIndexed, ORDER.valueOf(valueIndexed));
	}

	public void aSearchTrafficStopRows(String uri, String apiMethod, SearchList<TrafficStop> searchList, Integer valueRows) {
			searchList.setRows(apiMethod != null && apiMethod.contains("Search") ? valueRows : 10);
	}

	public void aSearchTrafficStopStart(String uri, String apiMethod, SearchList<TrafficStop> searchList, Integer valueStart) {
		searchList.setStart(valueStart);
	}

	public void aSearchTrafficStopVar(String uri, String apiMethod, SearchList<TrafficStop> searchList, String var, String value) {
		searchList.getSiteRequest_().getRequestVars().put(var, value);
	}

	public void aSearchTrafficStopUri(String uri, String apiMethod, SearchList<TrafficStop> searchList) {
	}

	public void varsTrafficStop(SiteRequestEnUS siteRequest, Handler<AsyncResult<SearchList<ServiceResponse>>> eventHandler) {
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
					LOG.error(String.format("aSearchTrafficStop failed. "), e);
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOG.error(String.format("aSearchTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void aSearchTrafficStop(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, Handler<AsyncResult<SearchList<TrafficStop>>> eventHandler) {
		try {
			SearchList<TrafficStop> searchList = aSearchTrafficStopList(siteRequest, populate, store, modify, uri, apiMethod);
			eventHandler.handle(Future.succeededFuture(searchList));
		} catch(Exception e) {
			LOG.error(String.format("aSearchTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SearchList<TrafficStop> aSearchTrafficStopList(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod) {
		ServiceRequest serviceRequest = siteRequest.getServiceRequest();
		String entityListStr = siteRequest.getServiceRequest().getParams().getJsonObject("query").getString("fl");
		String[] entityList = entityListStr == null ? null : entityListStr.split(",\\s*");
		SearchList<TrafficStop> searchList = new SearchList<TrafficStop>();
		searchList.setPopulate(populate);
		searchList.setStore(store);
		searchList.setQuery("*:*");
		searchList.setC(TrafficStop.class);
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
							varsIndexed[i] = TrafficStop.varIndexedTrafficStop(entityVar);
						}
						searchList.add("facet.pivot", (solrLocalParams == null ? "" : solrLocalParams) + StringUtils.join(varsIndexed, ","));
					}
				} else if(paramValuesObject != null) {
					for(Object paramObject : paramObjects) {
						switch(paramName) {
							case "q":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								varIndexed = "*".equals(entityVar) ? entityVar : TrafficStop.varSearchTrafficStop(entityVar);
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								valueIndexed = StringUtils.isEmpty(valueIndexed) ? "*" : valueIndexed;
								aSearchTrafficStopQ(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "fq":
								Matcher mFq = Pattern.compile("(\\w+):(.+?(?=(\\)|\\s+OR\\s+|\\s+AND\\s+|$)))").matcher((String)paramObject);
								boolean foundFq = mFq.find();
								if(foundFq) {
									StringBuffer sb = new StringBuffer();
									while(foundFq) {
										entityVar = mFq.group(1).trim();
										valueIndexed = mFq.group(2).trim();
										varIndexed = TrafficStop.varIndexedTrafficStop(entityVar);
										String entityFq = aSearchTrafficStopFq(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
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
								varIndexed = TrafficStop.varIndexedTrafficStop(entityVar);
								aSearchTrafficStopSort(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "start":
								valueStart = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
								aSearchTrafficStopStart(uri, apiMethod, searchList, valueStart);
								break;
							case "rows":
								valueRows = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
								aSearchTrafficStopRows(uri, apiMethod, searchList, valueRows);
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
									varIndexed = TrafficStop.varIndexedTrafficStop(entityVar);
									searchList.add("facet.range", (solrLocalParams == null ? "" : solrLocalParams) + varIndexed);
								}
								break;
							case "facet.field":
								entityVar = (String)paramObject;
								varIndexed = TrafficStop.varIndexedTrafficStop(entityVar);
								if(varIndexed != null)
									searchList.addFacetField(varIndexed);
								break;
							case "var":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								aSearchTrafficStopVar(uri, apiMethod, searchList, entityVar, valueIndexed);
								break;
						}
					}
					aSearchTrafficStopUri(uri, apiMethod, searchList);
				}
			} catch(Exception e) {
				ExceptionUtils.rethrow(e);
			}
		});
		if("*:*".equals(searchList.getQuery()) && searchList.getSorts().size() == 0) {
			searchList.addSort("created_indexed_date", ORDER.desc);
		}
		aSearchTrafficStop2(siteRequest, populate, store, modify, uri, apiMethod, searchList);
		searchList.initDeepForClass(siteRequest);
		return searchList;
	}
	public void aSearchTrafficStop2(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, SearchList<TrafficStop> searchList) {
	}

	public void defineTrafficStop(TrafficStop o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT * FROM TrafficStop WHERE pk=$1")
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
										LOG.error(String.format("defineTrafficStop failed. "), e);
									}
								}
							}
						}
						eventHandler.handle(Future.succeededFuture());
					} catch(Exception e) {
						LOG.error(String.format("defineTrafficStop failed. "), e);
						eventHandler.handle(Future.failedFuture(e));
					}
				} else {
					LOG.error(String.format("defineTrafficStop failed. ", defineAsync.cause()));
					eventHandler.handle(Future.failedFuture(defineAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("defineTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void attributeTrafficStop(TrafficStop o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT pk as pk2, 'personKeys' from TrafficPerson where trafficStopKey=$1")
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
						LOG.error(String.format("attributeTrafficStop failed. ", attributeAsync.cause()));
						eventHandler.handle(Future.failedFuture(attributeAsync.cause()));
					}
				} catch(Exception e) {
					LOG.error(String.format("attributeTrafficStop failed. "), e);
					eventHandler.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("attributeTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void indexTrafficStop(TrafficStop o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			o.initDeepForClass(siteRequest);
			o.indexForClass();
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOG.error(String.format("indexTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void refreshTrafficStop(TrafficStop o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Boolean refresh = !"false".equals(siteRequest.getRequestVars().get("refresh"));
			if(refresh && BooleanUtils.isFalse(Optional.ofNullable(siteRequest.getApiRequest_()).map(ApiRequest::getEmpty).orElse(true))) {
				SearchList<TrafficStop> searchList = new SearchList<TrafficStop>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(TrafficStop.class);
				searchList.addFilterQuery("modified_indexed_date:[" + DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(siteRequest.getApiRequest_().getCreated().toInstant(), ZoneId.of("UTC"))) + " TO *]");
				searchList.add("json.facet", "{personKeys:{terms:{field:personKeys_indexed_longs, limit:1000}}}");
				searchList.setRows(1000);
				searchList.initDeepSearchList(siteRequest);
				List<Future> futures = new ArrayList<>();

				for(int i=0; i < pks.size(); i++) {
					Long pk2 = pks.get(i);
					String classSimpleName2 = classes.get(i);

					if("TrafficPerson".equals(classSimpleName2) && pk2 != null) {
						SearchList<TrafficPerson> searchList2 = new SearchList<TrafficPerson>();
						searchList2.setStore(true);
						searchList2.setQuery("*:*");
						searchList2.setC(TrafficPerson.class);
						searchList2.addFilterQuery("pk_indexed_long:" + pk2);
						searchList2.setRows(1);
						searchList2.initDeepSearchList(siteRequest);
						TrafficPerson o2 = searchList2.getList().stream().findFirst().orElse(null);

						if(o2 != null) {
							TrafficPersonEnUSGenApiServiceImpl service = new TrafficPersonEnUSGenApiServiceImpl(siteRequest.getSiteContext_());
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForTrafficStop(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), new JsonObject());
							ApiRequest apiRequest2 = new ApiRequest();
							apiRequest2.setRows(1);
							apiRequest2.setNumFound(1l);
							apiRequest2.setNumPATCH(0L);
							apiRequest2.initDeepApiRequest(siteRequest2);
							siteRequest2.setApiRequest_(apiRequest2);
							siteRequest2.getVertx().eventBus().publish("websocketTrafficPerson", JsonObject.mapFrom(apiRequest2).toString());

							o2.setPk(pk2);
							o2.setSiteRequest_(siteRequest2);
							futures.add(
								service.patchTrafficPersonFuture(o2, false, a -> {
									if(a.succeeded()) {
									} else {
										LOG.info(String.format("TrafficPerson %s failed. ", pk2));
										eventHandler.handle(Future.failedFuture(a.cause()));
									}
								})
							);
						}
					}
				}

				CompositeFuture.all(futures).onComplete(a -> {
					if(a.succeeded()) {
						TrafficStopEnUSApiServiceImpl service = new TrafficStopEnUSApiServiceImpl(siteRequest.getSiteContext_());
						List<Future> futures2 = new ArrayList<>();
						for(TrafficStop o2 : searchList.getList()) {
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForTrafficStop(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), new JsonObject());
							o2.setSiteRequest_(siteRequest2);
							futures2.add(
								service.patchTrafficStopFuture(o2, false, b -> {
									if(b.succeeded()) {
									} else {
										LOG.info(String.format("TrafficStop %s failed. ", o2.getPk()));
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
								errorTrafficStop(siteRequest, eventHandler, b);
							}
						});
					} else {
						LOG.error("Refresh relations failed. ", a.cause());
						errorTrafficStop(siteRequest, eventHandler, a);
					}
				});
			} else {
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOG.error(String.format("refreshTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}
}
