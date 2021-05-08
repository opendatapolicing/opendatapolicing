package com.opendatapolicing.enus.trafficstop;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.PivotField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.RangeFacet;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.apache.solr.util.DateMathParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opendatapolicing.enus.cluster.BaseApiServiceImpl;
import com.opendatapolicing.enus.config.ConfigKeys;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.opendatapolicing.enus.request.api.ApiRequest;
import com.opendatapolicing.enus.search.SearchList;
import com.opendatapolicing.enus.trafficperson.TrafficPerson;

import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.authorization.AuthorizationProvider;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.web.api.service.ServiceRequest;
import io.vertx.ext.web.api.service.ServiceResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.Tuple;


/**
 * Translate: false
 **/
public class TrafficStopEnUSGenApiServiceImpl extends BaseApiServiceImpl implements TrafficStopEnUSGenApiService {

	protected static final Logger LOG = LoggerFactory.getLogger(TrafficStopEnUSGenApiServiceImpl.class);

	private Semaphore semaphore;

	public TrafficStopEnUSGenApiServiceImpl(Semaphore semaphore, EventBus eventBus, JsonObject config, WorkerExecutor workerExecutor, PgPool pgPool, WebClient webClient, OAuth2Auth oauth2AuthenticationProvider, AuthorizationProvider authorizationProvider) {
		super(eventBus, config, workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider);
		this.semaphore = semaphore;
	}

	// PUTImport //

	@Override
	public void putimportTrafficStop(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("putimportTrafficStop started. "));
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
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
					response200PUTImportTrafficStop(siteRequest).onSuccess(response -> {
						eventHandler.handle(Future.succeededFuture(response));
						workerExecutor.executeBlocking(blockingCodeHandler -> {
							try {
								ApiRequest apiRequest = new ApiRequest();
								JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
								apiRequest.setRows(jsonArray.size());
								apiRequest.setNumFound(new Integer(jsonArray.size()).longValue());
								apiRequest.setNumPATCH(0L);
								apiRequest.initDeepApiRequest(siteRequest);
								siteRequest.setApiRequest_(apiRequest);
								eventBus.publish("websocketTrafficStop", JsonObject.mapFrom(apiRequest).toString());
								varsTrafficStop(siteRequest).onSuccess(d -> {
									listPUTImportTrafficStop(apiRequest, siteRequest).onSuccess(e -> {
										LOG.debug(String.format("putimportTrafficStop succeeded. "));
										blockingCodeHandler.complete();
									}).onFailure(ex -> {
										LOG.error(String.format("putimportTrafficStop failed. ", ex));
										blockingCodeHandler.fail(ex);
									});
								}).onFailure(ex -> {
									LOG.error(String.format("putimportTrafficStop failed. ", ex));
									blockingCodeHandler.fail(ex);
								});
							} catch(Exception ex) {
								LOG.error(String.format("putimportTrafficStop failed. ", ex));
								blockingCodeHandler.fail(ex);
							}
						}, resultHandler -> {
						});
					}).onFailure(ex -> {
						LOG.error(String.format("putimportTrafficStop failed. ", ex));
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("putimportTrafficStop failed. ", ex));
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("putimportTrafficStop failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("putimportTrafficStop failed. ", ex));
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<Void> listPUTImportTrafficStop(ApiRequest apiRequest, SiteRequestEnUS siteRequest) {
		Promise<Void> promise = Promise.promise();
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;
				futures.add(Future.future(promise2 -> {
					try {
						json.put("inheritPk", json.getValue("pk"));
						json.put("created", json.getValue("created"));

						SiteRequestEnUS siteRequest2 = siteRequest.copy();
						siteRequest2.setJsonObject(json);
						siteRequest2.setApiRequest_(apiRequest);
						siteRequest2.setRequestVars(siteRequest.getRequestVars());

						SearchList<TrafficStop> searchList = new SearchList<TrafficStop>();
						searchList.setStore(true);
						searchList.setQuery("*:*");
						searchList.setC(TrafficStop.class);
						searchList.addFilterQuery("inheritPk_indexed_string:" + ClientUtils.escapeQueryChars(json.getString("pk")));
						searchList.promiseDeepForClass(siteRequest2).onSuccess(a -> {
							try {
								if(searchList.size() == 1) {
									TrafficStop o = searchList.getList().stream().findFirst().orElse(null);
									TrafficStop o2 = new TrafficStop();
									JsonObject json2 = new JsonObject();
									for(String f : json.fieldNames()) {
										Object jsonVal = json.getValue(f);
										if(jsonVal instanceof JsonArray) {
											JsonArray jsonVals = (JsonArray)jsonVal;
											Collection<?> vals = (Collection<?>)o.obtainForClass(f);
											if(jsonVals.size() == vals.size()) {
												Boolean match = true;
												for(Object val : vals) {
													if(val != null) {
														if(!jsonVals.contains(val.toString())) {
															match = false;
															break;
														}
													} else {
														match = false;
														break;
													}
												}
												if(!match) {
													json2.put("set" + StringUtils.capitalize(f), jsonVal);
												}
											} else {
												json2.put("set" + StringUtils.capitalize(f), jsonVal);
											}
										} else {
											o2.defineForClass(f, jsonVal);
											if(!StringUtils.containsAny(f, "pk", "created") && !Objects.equals(o.obtainForClass(f), o2.obtainForClass(f)))
												json2.put("set" + StringUtils.capitalize(f), jsonVal);
										}
									}
									for(String f : Optional.ofNullable(o.getSaves()).orElse(new ArrayList<>())) {
										if(!json.fieldNames().contains(f))
											json2.putNull("set" + StringUtils.capitalize(f));
									}
									if(json2.size() > 0) {
										siteRequest2.setJsonObject(json2);
										patchTrafficStopFuture(o, true).onSuccess(b -> {
											promise2.complete();
										}).onFailure(ex -> {
											LOG.error(String.format("listPUTImportTrafficStop failed. ", ex));
										});
									}
								} else {
									postTrafficStopFuture(siteRequest2, true).onSuccess(b -> {
										promise2.complete();
									}).onFailure(ex -> {
										LOG.error(String.format("listPUTImportTrafficStop failed. ", ex));
										promise2.fail(ex);
									});
								}
							} catch(Exception ex) {
								LOG.error(String.format("listPUTImportTrafficStop failed. ", ex));
								promise2.fail(ex);
							}
						}).onFailure(ex -> {
							LOG.error(String.format("listPUTImportTrafficStop failed. ", ex));
							promise2.fail(ex);
						});
					} catch(Exception ex) {
						LOG.error(String.format("listPUTImportTrafficStop failed. ", ex));
						promise2.fail(ex);
					}
				}));
			});
			CompositeFuture.all(futures).onSuccess(a -> {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
				promise.complete();
			}).onFailure(ex -> {
				LOG.error(String.format("listPUTImportTrafficStop failed. ", ex));
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("listPUTImportTrafficStop failed. ", ex));
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<ServiceResponse> response200PUTImportTrafficStop(SiteRequestEnUS siteRequest) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			JsonObject json = new JsonObject();
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200PUTImportTrafficStop failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// PUTMerge //

	@Override
	public void putmergeTrafficStop(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("putmergeTrafficStop started. "));
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
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
					response200PUTMergeTrafficStop(siteRequest).onSuccess(response -> {
						eventHandler.handle(Future.succeededFuture(response));
						workerExecutor.executeBlocking(blockingCodeHandler -> {
							try {
								ApiRequest apiRequest = new ApiRequest();
								JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
								apiRequest.setRows(jsonArray.size());
								apiRequest.setNumFound(new Integer(jsonArray.size()).longValue());
								apiRequest.setNumPATCH(0L);
								apiRequest.initDeepApiRequest(siteRequest);
								siteRequest.setApiRequest_(apiRequest);
								eventBus.publish("websocketTrafficStop", JsonObject.mapFrom(apiRequest).toString());
								varsTrafficStop(siteRequest).onSuccess(d -> {
									listPUTMergeTrafficStop(apiRequest, siteRequest).onSuccess(e -> {
										LOG.debug(String.format("putmergeTrafficStop succeeded. "));
										blockingCodeHandler.complete();
									}).onFailure(ex -> {
										LOG.error(String.format("putmergeTrafficStop failed. ", ex));
										blockingCodeHandler.fail(ex);
									});
								}).onFailure(ex -> {
									LOG.error(String.format("putmergeTrafficStop failed. ", ex));
									blockingCodeHandler.fail(ex);
								});
							} catch(Exception ex) {
								LOG.error(String.format("putmergeTrafficStop failed. ", ex));
								blockingCodeHandler.fail(ex);
							}
						}, resultHandler -> {
						});
					}).onFailure(ex -> {
						LOG.error(String.format("putmergeTrafficStop failed. ", ex));
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("putmergeTrafficStop failed. ", ex));
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("putmergeTrafficStop failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("putmergeTrafficStop failed. ", ex));
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<Void> listPUTMergeTrafficStop(ApiRequest apiRequest, SiteRequestEnUS siteRequest) {
		Promise<Void> promise = Promise.promise();
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;
				futures.add(Future.future(promise2 -> {
					try {
						json.put("inheritPk", json.getValue("pk"));

						SiteRequestEnUS siteRequest2 = siteRequest.copy();
						siteRequest2.setJsonObject(json);
						siteRequest2.setApiRequest_(apiRequest);
						siteRequest2.setRequestVars(siteRequest.getRequestVars());

						SearchList<TrafficStop> searchList = new SearchList<TrafficStop>();
						searchList.setStore(true);
						searchList.setQuery("*:*");
						searchList.setC(TrafficStop.class);
						searchList.addFilterQuery("pk_indexed_long:" + ClientUtils.escapeQueryChars(json.getString("pk")));
						searchList.promiseDeepForClass(siteRequest2).onSuccess(a -> {
							try {
								if(searchList.size() == 1) {
									TrafficStop o = searchList.getList().stream().findFirst().orElse(null);
									TrafficStop o2 = new TrafficStop();
									JsonObject json2 = new JsonObject();
									for(String f : json.fieldNames()) {
										Object jsonVal = json.getValue(f);
										if(jsonVal instanceof JsonArray) {
											JsonArray jsonVals = (JsonArray)jsonVal;
											Collection<?> vals = (Collection<?>)o.obtainForClass(f);
											if(jsonVals.size() == vals.size()) {
												Boolean match = true;
												for(Object val : vals) {
													if(val != null) {
														if(!jsonVals.contains(val.toString())) {
															match = false;
															break;
														}
													} else {
														match = false;
														break;
													}
												}
												if(!match) {
													json2.put("set" + StringUtils.capitalize(f), jsonVal);
												}
											} else {
												json2.put("set" + StringUtils.capitalize(f), jsonVal);
											}
										} else {
											o2.defineForClass(f, jsonVal);
											if(!StringUtils.containsAny(f, "pk", "created") && !Objects.equals(o.obtainForClass(f), o2.obtainForClass(f)))
												json2.put("set" + StringUtils.capitalize(f), jsonVal);
										}
									}
									for(String f : Optional.ofNullable(o.getSaves()).orElse(new ArrayList<>())) {
										if(!json.fieldNames().contains(f))
											json2.putNull("set" + StringUtils.capitalize(f));
									}
									if(json2.size() > 0) {
										siteRequest2.setJsonObject(json2);
										patchTrafficStopFuture(o, false).onSuccess(b -> {
											promise2.complete();
										}).onFailure(ex -> {
											LOG.error(String.format("listPUTMergeTrafficStop failed. ", ex));
										});
									}
								} else {
									postTrafficStopFuture(siteRequest2, false).onSuccess(b -> {
										promise2.complete();
									}).onFailure(ex -> {
										LOG.error(String.format("listPUTMergeTrafficStop failed. ", ex));
										promise2.fail(ex);
									});
								}
							} catch(Exception ex) {
								LOG.error(String.format("listPUTMergeTrafficStop failed. ", ex));
								promise2.fail(ex);
							}
						}).onFailure(ex -> {
							LOG.error(String.format("listPUTMergeTrafficStop failed. ", ex));
							promise2.fail(ex);
						});
					} catch(Exception ex) {
						LOG.error(String.format("listPUTMergeTrafficStop failed. ", ex));
						promise2.fail(ex);
					}
				}));
			});
			CompositeFuture.all(futures).onSuccess(a -> {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
				promise.complete();
			}).onFailure(ex -> {
				LOG.error(String.format("listPUTMergeTrafficStop failed. ", ex));
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("listPUTMergeTrafficStop failed. ", ex));
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<ServiceResponse> response200PUTMergeTrafficStop(SiteRequestEnUS siteRequest) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			JsonObject json = new JsonObject();
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200PUTMergeTrafficStop failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// PUTCopy //

	@Override
	public void putcopyTrafficStop(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("putcopyTrafficStop started. "));
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
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
					response200PUTCopyTrafficStop(siteRequest).onSuccess(response -> {
						eventHandler.handle(Future.succeededFuture(response));
						workerExecutor.executeBlocking(blockingCodeHandler -> {
							try {
								searchTrafficStopList(siteRequest, false, true, true, "/api/traffic-stop/copy", "PUTCopy").onSuccess(listTrafficStop -> {
									ApiRequest apiRequest = new ApiRequest();
									apiRequest.setRows(listTrafficStop.getRows());
									apiRequest.setNumFound(listTrafficStop.getQueryResponse().getResults().getNumFound());
									apiRequest.setNumPATCH(0L);
									apiRequest.initDeepApiRequest(siteRequest);
									siteRequest.setApiRequest_(apiRequest);
									eventBus.publish("websocketTrafficStop", JsonObject.mapFrom(apiRequest).toString());
									listPUTCopyTrafficStop(apiRequest, listTrafficStop).onSuccess(e -> {
										response200PUTCopyTrafficStop(siteRequest).onSuccess(f -> {
											LOG.debug(String.format("putcopyTrafficStop succeeded. "));
											blockingCodeHandler.complete();
										}).onFailure(ex -> {
											LOG.error(String.format("putcopyTrafficStop failed. ", ex));
											blockingCodeHandler.fail(ex);
										});
									}).onFailure(ex -> {
										LOG.error(String.format("putcopyTrafficStop failed. ", ex));
										blockingCodeHandler.fail(ex);
									});
								}).onFailure(ex -> {
									LOG.error(String.format("putcopyTrafficStop failed. ", ex));
									blockingCodeHandler.fail(ex);
								});
							} catch(Exception ex) {
								LOG.error(String.format("putcopyTrafficStop failed. ", ex));
								blockingCodeHandler.fail(ex);
							}
						}, resultHandler -> {
						});
					}).onFailure(ex -> {
						LOG.error(String.format("putcopyTrafficStop failed. ", ex));
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("putcopyTrafficStop failed. ", ex));
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("putcopyTrafficStop failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("putcopyTrafficStop failed. ", ex));
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<Void> listPUTCopyTrafficStop(ApiRequest apiRequest, SearchList<TrafficStop> listTrafficStop) {
		Promise<Void> promise = Promise.promise();
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listTrafficStop.getSiteRequest_();
		listTrafficStop.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = siteRequest.copy();
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				putcopyTrafficStopFuture(siteRequest2, JsonObject.mapFrom(o)).onFailure(ex -> {
					LOG.error(String.format("listPUTCopyTrafficStop failed. ", ex));
					error(siteRequest, null, ex);
				})
			);
		});
		CompositeFuture.all(futures).onSuccess(a -> {
			apiRequest.setNumPATCH(apiRequest.getNumPATCH() + listTrafficStop.size());
			listTrafficStop.next().onSuccess(next -> {
				if(next) {
					listPUTCopyTrafficStop(apiRequest, listTrafficStop);
				} else {
					promise.complete();
				}
			}).onFailure(ex -> {
				LOG.error(String.format("listPUTCopyTrafficStop failed. ", ex));
				error(listTrafficStop.getSiteRequest_(), null, ex);
			});
		}).onFailure(ex -> {
			LOG.error(String.format("listPUTCopyTrafficStop failed. ", ex));
			error(listTrafficStop.getSiteRequest_(), null, ex);
		});
		return promise.future();
	}

	public Future<TrafficStop> putcopyTrafficStopFuture(SiteRequestEnUS siteRequest, JsonObject jsonObject) {
		Promise<TrafficStop> promise = Promise.promise();

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
				Promise<TrafficStop> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				createTrafficStop(siteRequest).onSuccess(trafficStop -> {
					sqlPUTCopyTrafficStop(trafficStop, jsonObject).onSuccess(b -> {
						defineTrafficStop(trafficStop).onSuccess(c -> {
							attributeTrafficStop(trafficStop).onSuccess(d -> {
								indexTrafficStop(trafficStop).onSuccess(e -> {
									promise1.complete(trafficStop);
								}).onFailure(ex -> {
									LOG.error(String.format("putcopyTrafficStopFuture failed. ", ex));
									promise1.fail(ex);
								});
							}).onFailure(ex -> {
								LOG.error(String.format("putcopyTrafficStopFuture failed. ", ex));
								promise1.fail(ex);
							});
						}).onFailure(ex -> {
							LOG.error(String.format("putcopyTrafficStopFuture failed. ", ex));
							promise1.fail(ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("putcopyTrafficStopFuture failed. ", ex));
						promise1.fail(ex);
					});
				}).onFailure(ex -> {
					LOG.error(String.format("putcopyTrafficStopFuture failed. ", ex));
					promise1.fail(ex);
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, ex);
			}).compose(trafficStop -> {
				Promise<TrafficStop> promise2 = Promise.promise();
				refreshTrafficStop(trafficStop).onSuccess(a -> {
					ApiRequest apiRequest = siteRequest.getApiRequest_();
					if(apiRequest != null) {
						apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
						trafficStop.apiRequestTrafficStop();
						eventBus.publish("websocketTrafficStop", JsonObject.mapFrom(apiRequest).toString());
					}
					promise2.complete(trafficStop);
				}).onFailure(ex -> {
					LOG.error(String.format("putcopyTrafficStopFuture failed. ", ex));
					promise2.fail(ex);
				});
				return promise2.future();
			}).onSuccess(trafficStop -> {
				promise.complete(trafficStop);
				LOG.debug(String.format("putcopyTrafficStopFuture succeeded. "));
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("putcopyTrafficStopFuture failed. "), ex);
			promise.fail(ex);
			error(siteRequest, null, ex);
		}
		return promise.future();
	}

	public Future<Void> sqlPUTCopyTrafficStop(TrafficStop o, JsonObject jsonObject) {
		Promise<Void> promise = Promise.promise();
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
					case TrafficStop.VAR_inheritPk:
						o2.setInheritPk(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_inheritPk + "=$" + num);
						num++;
						bParams.add(o2.sqlInheritPk());
						break;
					case TrafficStop.VAR_stateAbbreviation:
						o2.setStateAbbreviation(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stateAbbreviation + "=$" + num);
						num++;
						bParams.add(o2.sqlStateAbbreviation());
						break;
					case TrafficStop.VAR_agencyTitle:
						o2.setAgencyTitle(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_agencyTitle + "=$" + num);
						num++;
						bParams.add(o2.sqlAgencyTitle());
						break;
					case TrafficStop.VAR_stopDateTime:
						o2.setStopDateTime(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopDateTime + "=$" + num);
						num++;
						bParams.add(o2.sqlStopDateTime());
						break;
					case TrafficStop.VAR_stopYear:
						o2.setStopYear(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopYear + "=$" + num);
						num++;
						bParams.add(o2.sqlStopYear());
						break;
					case TrafficStop.VAR_stopPurposeNum:
						o2.setStopPurposeNum(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopPurposeNum + "=$" + num);
						num++;
						bParams.add(o2.sqlStopPurposeNum());
						break;
					case TrafficStop.VAR_stopActionNum:
						o2.setStopActionNum(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopActionNum + "=$" + num);
						num++;
						bParams.add(o2.sqlStopActionNum());
						break;
					case TrafficStop.VAR_stopDriverArrest:
						o2.setStopDriverArrest(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopDriverArrest + "=$" + num);
						num++;
						bParams.add(o2.sqlStopDriverArrest());
						break;
					case TrafficStop.VAR_stopPassengerArrest:
						o2.setStopPassengerArrest(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopPassengerArrest + "=$" + num);
						num++;
						bParams.add(o2.sqlStopPassengerArrest());
						break;
					case TrafficStop.VAR_stopEncounterForce:
						o2.setStopEncounterForce(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopEncounterForce + "=$" + num);
						num++;
						bParams.add(o2.sqlStopEncounterForce());
						break;
					case TrafficStop.VAR_stopEngageForce:
						o2.setStopEngageForce(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopEngageForce + "=$" + num);
						num++;
						bParams.add(o2.sqlStopEngageForce());
						break;
					case TrafficStop.VAR_stopOfficerInjury:
						o2.setStopOfficerInjury(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopOfficerInjury + "=$" + num);
						num++;
						bParams.add(o2.sqlStopOfficerInjury());
						break;
					case TrafficStop.VAR_stopDriverInjury:
						o2.setStopDriverInjury(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopDriverInjury + "=$" + num);
						num++;
						bParams.add(o2.sqlStopDriverInjury());
						break;
					case TrafficStop.VAR_stopPassengerInjury:
						o2.setStopPassengerInjury(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopPassengerInjury + "=$" + num);
						num++;
						bParams.add(o2.sqlStopPassengerInjury());
						break;
					case TrafficStop.VAR_stopOfficerId:
						o2.setStopOfficerId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopOfficerId + "=$" + num);
						num++;
						bParams.add(o2.sqlStopOfficerId());
						break;
					case TrafficStop.VAR_stopLocationId:
						o2.setStopLocationId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopLocationId + "=$" + num);
						num++;
						bParams.add(o2.sqlStopLocationId());
						break;
					case TrafficStop.VAR_stopCityId:
						o2.setStopCityId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopCityId + "=$" + num);
						num++;
						bParams.add(o2.sqlStopCityId());
						break;
					case TrafficStop.VAR_personKeys:
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
			CompositeFuture.all(futures).onSuccess(a -> {
				promise.complete();
			}).onFailure(ex -> {
				LOG.error(String.format("sqlPUTCopyTrafficStop failed. ", ex));
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("sqlPUTCopyTrafficStop failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<ServiceResponse> response200PUTCopyTrafficStop(SiteRequestEnUS siteRequest) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			JsonObject json = new JsonObject();
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200PUTCopyTrafficStop failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// POST //

	@Override
	public void postTrafficStop(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("postTrafficStop started. "));
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
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
					eventBus.publish("websocketTrafficStop", JsonObject.mapFrom(apiRequest).toString());
					postTrafficStopFuture(siteRequest, false).onSuccess(trafficStop -> {
						apiRequest.setPk(trafficStop.getPk());
						response200POSTTrafficStop(trafficStop).onSuccess(response -> {
							eventHandler.handle(Future.succeededFuture(response));
							LOG.debug(String.format("postTrafficStop succeeded. "));
						}).onFailure(ex -> {
							LOG.error(String.format("postTrafficStop failed. ", ex));
							error(siteRequest, eventHandler, ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("postTrafficStop failed. ", ex));
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("postTrafficStop failed. ", ex));
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("postTrafficStop failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("postTrafficStop failed. ", ex));
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<TrafficStop> postTrafficStopFuture(SiteRequestEnUS siteRequest, Boolean inheritPk) {
		Promise<TrafficStop> promise = Promise.promise();

		try {
			pgPool.withTransaction(sqlConnection -> {
				Promise<TrafficStop> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				createTrafficStop(siteRequest).onSuccess(trafficStop -> {
					sqlPOSTTrafficStop(trafficStop, inheritPk).onSuccess(b -> {
						defineTrafficStop(trafficStop).onSuccess(c -> {
							attributeTrafficStop(trafficStop).onSuccess(d -> {
								indexTrafficStop(trafficStop).onSuccess(e -> {
									promise1.complete(trafficStop);
								}).onFailure(ex -> {
									LOG.error(String.format("postTrafficStopFuture failed. ", ex));
									promise1.fail(ex);
								});
							}).onFailure(ex -> {
								LOG.error(String.format("postTrafficStopFuture failed. ", ex));
								promise1.fail(ex);
							});
						}).onFailure(ex -> {
							LOG.error(String.format("postTrafficStopFuture failed. ", ex));
							promise1.fail(ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("postTrafficStopFuture failed. ", ex));
						promise1.fail(ex);
					});
				}).onFailure(ex -> {
					LOG.error(String.format("postTrafficStopFuture failed. ", ex));
					promise1.fail(ex);
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, ex);
			}).compose(trafficStop -> {
				Promise<TrafficStop> promise2 = Promise.promise();
				refreshTrafficStop(trafficStop).onSuccess(a -> {
					ApiRequest apiRequest = siteRequest.getApiRequest_();
					if(apiRequest != null) {
						apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
						trafficStop.apiRequestTrafficStop();
						eventBus.publish("websocketTrafficStop", JsonObject.mapFrom(apiRequest).toString());
					}
					promise2.complete(trafficStop);
				}).onFailure(ex -> {
					LOG.error(String.format("postTrafficStopFuture failed. ", ex));
					promise2.fail(ex);
				});
				return promise2.future();
			}).onSuccess(trafficStop -> {
				promise.complete(trafficStop);
				LOG.debug(String.format("postTrafficStopFuture succeeded. "));
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("postTrafficStopFuture failed. "), ex);
			promise.fail(ex);
			error(siteRequest, null, ex);
		}
		return promise.future();
	}

	public Future<Void> sqlPOSTTrafficStop(TrafficStop o, Boolean inheritPk) {
		Promise<Void> promise = Promise.promise();
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
			List<Future> futures1 = new ArrayList<>();
			List<Future> futures2 = new ArrayList<>();

			if(jsonObject != null) {
				Set<String> entityVars = jsonObject.fieldNames();
				for(String entityVar : entityVars) {
					switch(entityVar) {
					case TrafficStop.VAR_inheritPk:
						o2.setInheritPk(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_inheritPk + "=$" + num);
						num++;
						bParams.add(o2.sqlInheritPk());
						break;
					case TrafficStop.VAR_stateAbbreviation:
						o2.setStateAbbreviation(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stateAbbreviation + "=$" + num);
						num++;
						bParams.add(o2.sqlStateAbbreviation());
						break;
					case TrafficStop.VAR_agencyTitle:
						o2.setAgencyTitle(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_agencyTitle + "=$" + num);
						num++;
						bParams.add(o2.sqlAgencyTitle());
						break;
					case TrafficStop.VAR_stopDateTime:
						o2.setStopDateTime(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopDateTime + "=$" + num);
						num++;
						bParams.add(o2.sqlStopDateTime());
						break;
					case TrafficStop.VAR_stopYear:
						o2.setStopYear(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopYear + "=$" + num);
						num++;
						bParams.add(o2.sqlStopYear());
						break;
					case TrafficStop.VAR_stopPurposeNum:
						o2.setStopPurposeNum(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopPurposeNum + "=$" + num);
						num++;
						bParams.add(o2.sqlStopPurposeNum());
						break;
					case TrafficStop.VAR_stopActionNum:
						o2.setStopActionNum(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopActionNum + "=$" + num);
						num++;
						bParams.add(o2.sqlStopActionNum());
						break;
					case TrafficStop.VAR_stopDriverArrest:
						o2.setStopDriverArrest(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopDriverArrest + "=$" + num);
						num++;
						bParams.add(o2.sqlStopDriverArrest());
						break;
					case TrafficStop.VAR_stopPassengerArrest:
						o2.setStopPassengerArrest(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopPassengerArrest + "=$" + num);
						num++;
						bParams.add(o2.sqlStopPassengerArrest());
						break;
					case TrafficStop.VAR_stopEncounterForce:
						o2.setStopEncounterForce(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopEncounterForce + "=$" + num);
						num++;
						bParams.add(o2.sqlStopEncounterForce());
						break;
					case TrafficStop.VAR_stopEngageForce:
						o2.setStopEngageForce(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopEngageForce + "=$" + num);
						num++;
						bParams.add(o2.sqlStopEngageForce());
						break;
					case TrafficStop.VAR_stopOfficerInjury:
						o2.setStopOfficerInjury(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopOfficerInjury + "=$" + num);
						num++;
						bParams.add(o2.sqlStopOfficerInjury());
						break;
					case TrafficStop.VAR_stopDriverInjury:
						o2.setStopDriverInjury(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopDriverInjury + "=$" + num);
						num++;
						bParams.add(o2.sqlStopDriverInjury());
						break;
					case TrafficStop.VAR_stopPassengerInjury:
						o2.setStopPassengerInjury(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopPassengerInjury + "=$" + num);
						num++;
						bParams.add(o2.sqlStopPassengerInjury());
						break;
					case TrafficStop.VAR_stopOfficerId:
						o2.setStopOfficerId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopOfficerId + "=$" + num);
						num++;
						bParams.add(o2.sqlStopOfficerId());
						break;
					case TrafficStop.VAR_stopLocationId:
						o2.setStopLocationId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopLocationId + "=$" + num);
						num++;
						bParams.add(o2.sqlStopLocationId());
						break;
					case TrafficStop.VAR_stopCityId:
						o2.setStopCityId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(TrafficStop.VAR_stopCityId + "=$" + num);
						num++;
						bParams.add(o2.sqlStopCityId());
						break;
					case TrafficStop.VAR_personKeys:
						Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray()).stream().map(oVal -> oVal.toString()).forEach(val -> {
							futures2.add(Future.future(promise2 -> {
								search(siteRequest).query(TrafficPerson.class, val, inheritPk).onSuccess(pk2 -> {
									sql(siteRequest).update(TrafficPerson.class, pk2).set(TrafficPerson.VAR_trafficStopKey, TrafficStop.class, pk).onSuccess(a -> {
										promise2.complete();
									}).onFailure(ex -> {
										promise2.fail(ex);
									});
								}).onFailure(ex -> {
									promise2.fail(ex);
								});
							}));
						});
						break;
					}
				}
			}
			bSql.append(" WHERE pk=$" + num);
			if(bParams.size() > 0) {
			bParams.add(pk);
			num++;
				futures2.add(0, Future.future(a -> {
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
			CompositeFuture.all(futures1).onSuccess(a -> {
				CompositeFuture.all(futures2).onSuccess(b -> {
					promise.complete();
				}).onFailure(ex -> {
					LOG.error(String.format("sqlPOSTTrafficStop failed. ", ex));
					promise.fail(ex);
				});
			}).onFailure(ex -> {
				LOG.error(String.format("sqlPOSTTrafficStop failed. ", ex));
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("sqlPOSTTrafficStop failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<ServiceResponse> response200POSTTrafficStop(TrafficStop o) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200POSTTrafficStop failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// PATCH //

	@Override
	public void patchTrafficStop(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("patchTrafficStop started. "));
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setJsonObject(body);
				siteRequest.setRequestUri("/api/traffic-stop");
				siteRequest.setRequestMethod("PATCH");

				{
//					response200PATCHTrafficStop(siteRequest).onSuccess(response -> {
//						eventHandler.handle(Future.succeededFuture(response));
//						workerExecutor.executeBlocking(blockingCodeHandler -> {
							searchTrafficStopList(siteRequest, false, true, true, "/api/traffic-stop", "PATCH").onSuccess(listTrafficStop -> {
								try {
									List<String> roles2 = Arrays.asList("SiteAdmin");
									if(listTrafficStop.getQueryResponse().getResults().getNumFound() > 1
											&& !CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles2)
											&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles2)
											) {
										String message = String.format("roles required: " + String.join(", ", roles2));
										LOG.error(message);
										error(siteRequest, eventHandler, null);
									} else {

										ApiRequest apiRequest = new ApiRequest();
										apiRequest.setRows(listTrafficStop.getRows());
										apiRequest.setNumFound(listTrafficStop.getQueryResponse().getResults().getNumFound());
										apiRequest.setNumPATCH(0L);
										apiRequest.initDeepApiRequest(siteRequest);
										siteRequest.setApiRequest_(apiRequest);
										eventBus.publish("websocketTrafficStop", JsonObject.mapFrom(apiRequest).toString());
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

										listPATCHTrafficStop(apiRequest, listTrafficStop, dt).onSuccess(e -> {
											response200PATCHTrafficStop(siteRequest).onSuccess(response -> {
												eventHandler.handle(Future.succeededFuture(response));
												LOG.debug(String.format("patchTrafficStop succeeded. "));
											}).onFailure(ex -> {
												LOG.error(String.format("patchTrafficStop failed. ", ex));
												error(siteRequest, eventHandler, ex);
											});
										}).onFailure(ex -> {
											LOG.error(String.format("patchTrafficStop failed. ", ex));
											error(siteRequest, eventHandler, ex);
										});
									}
								} catch(Exception ex) {
									LOG.error(String.format("patchTrafficStop failed. ", ex));
									error(siteRequest, eventHandler, ex);
								}
							}).onFailure(ex -> {
								LOG.error(String.format("patchTrafficStop failed. ", ex));
								error(siteRequest, eventHandler, ex);
							});
//						}, resultHandler -> {
//						});
//					}).onFailure(ex -> {
//						LOG.error(String.format("patchTrafficStop failed. ", ex));
//						error(siteRequest, eventHandler, ex);
//					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("patchTrafficStop failed. ", ex));
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("patchTrafficStop failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("patchTrafficStop failed. ", ex));
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<Void> listPATCHTrafficStop(ApiRequest apiRequest, SearchList<TrafficStop> listTrafficStop, String dt) {
		Promise<Void> promise = Promise.promise();
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listTrafficStop.getSiteRequest_();
		listTrafficStop.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = siteRequest.copy();
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				patchTrafficStopFuture(o, false).onFailure(ex -> {
					error(siteRequest2, null, ex);
				})
			);
		});
		CompositeFuture.all(futures).onSuccess( a -> {
			listTrafficStop.next(dt).onSuccess(next -> {
				if(next) {
					listPATCHTrafficStop(apiRequest, listTrafficStop, dt);
				} else {
					promise.complete();
				}
			}).onFailure(ex -> {
				LOG.error(String.format("listPATCHTrafficStop failed. ", ex));
				error(listTrafficStop.getSiteRequest_(), null, ex);
			});
		}).onFailure(ex -> {
			LOG.error(String.format("listPATCHTrafficStop failed. ", ex));
			error(listTrafficStop.getSiteRequest_(), null, ex);
		});
		return promise.future();
	}

	@Override
	public void patchTrafficStopFuture(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUS(null, serviceRequest, body);
		TrafficStop o = new TrafficStop();
		o.setSiteRequest_(siteRequest);
		ApiRequest apiRequest = new ApiRequest();
		apiRequest.setRows(1);
		apiRequest.setNumFound(1L);
		apiRequest.setNumPATCH(0L);
		apiRequest.initDeepApiRequest(siteRequest);
		siteRequest.setApiRequest_(apiRequest);
		o.setPk(body.getString(TrafficStop.VAR_pk));
		patchTrafficStopFuture(o, false).onSuccess(a -> {
			semaphore.release();
			eventHandler.handle(Future.succeededFuture());
		}).onFailure(ex -> {
			eventHandler.handle(Future.failedFuture(ex));
		});
	}

	public Future<TrafficStop> patchTrafficStopFuture(TrafficStop o, Boolean inheritPk) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		Promise<TrafficStop> promise = Promise.promise();

		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			if(apiRequest != null && apiRequest.getNumFound() == 1L) {
				apiRequest.setOriginal(o);
				apiRequest.setPk(o.getPk());
			}
			pgPool.withTransaction(sqlConnection -> {
				Promise<TrafficStop> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				sqlPATCHTrafficStop(o, inheritPk).onSuccess(trafficStop -> {
					defineTrafficStop(trafficStop).onSuccess(c -> {
						attributeTrafficStop(trafficStop).onSuccess(d -> {
							indexTrafficStop(trafficStop).onSuccess(e -> {
								promise1.complete(trafficStop);
							}).onFailure(ex -> {
								LOG.error(String.format("patchTrafficStopFuture failed. "), ex);
								promise1.fail(ex);
							});
						}).onFailure(ex -> {
							LOG.error(String.format("patchTrafficStopFuture failed. "), ex);
							promise1.fail(ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("patchTrafficStopFuture failed. "), ex);
						promise1.fail(ex);
					});
				}).onFailure(ex -> {
					LOG.error(String.format("patchTrafficStopFuture failed. "), ex);
					promise1.fail(ex);
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, ex);
			}).compose(trafficStop -> {
				Promise<TrafficStop> promise2 = Promise.promise();
				refreshTrafficStop(trafficStop).onSuccess(a -> {
					if(apiRequest != null) {
						apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
						trafficStop.apiRequestTrafficStop();
						eventBus.publish("websocketTrafficStop", JsonObject.mapFrom(apiRequest).toString());
					}
					promise2.complete(trafficStop);
				}).onFailure(ex -> {
					LOG.error(String.format("patchTrafficStopFuture failed. ", ex));
					promise2.fail(ex);
				});
				return promise2.future();
			}).onSuccess(trafficStop -> {
				promise.complete(trafficStop);
				LOG.debug(String.format("patchTrafficStopFuture succeeded. "));
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("patchTrafficStopFuture failed. "), ex);
			promise.fail(ex);
			error(siteRequest, null, ex);
		}
		return promise.future();
	}

	public Future<TrafficStop> sqlPATCHTrafficStop(TrafficStop o, Boolean inheritPk) {
		Promise<TrafficStop> promise = Promise.promise();
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
			List<Future> futures1 = new ArrayList<>();
			List<Future> futures2 = new ArrayList<>();

			for(String entityVar : methodNames) {
				switch(entityVar) {
					case "setInheritPk":
							o2.setInheritPk(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(TrafficStop.VAR_inheritPk + "=$" + num);
							num++;
							bParams.add(o2.sqlInheritPk());
						break;
					case "setStateAbbreviation":
							o2.setStateAbbreviation(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(TrafficStop.VAR_stateAbbreviation + "=$" + num);
							num++;
							bParams.add(o2.sqlStateAbbreviation());
						break;
					case "setAgencyTitle":
							o2.setAgencyTitle(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(TrafficStop.VAR_agencyTitle + "=$" + num);
							num++;
							bParams.add(o2.sqlAgencyTitle());
						break;
					case "setStopDateTime":
							o2.setStopDateTime(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(TrafficStop.VAR_stopDateTime + "=$" + num);
							num++;
							bParams.add(o2.sqlStopDateTime());
						break;
					case "setStopYear":
							o2.setStopYear(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(TrafficStop.VAR_stopYear + "=$" + num);
							num++;
							bParams.add(o2.sqlStopYear());
						break;
					case "setStopPurposeNum":
							o2.setStopPurposeNum(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(TrafficStop.VAR_stopPurposeNum + "=$" + num);
							num++;
							bParams.add(o2.sqlStopPurposeNum());
						break;
					case "setStopActionNum":
							o2.setStopActionNum(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(TrafficStop.VAR_stopActionNum + "=$" + num);
							num++;
							bParams.add(o2.sqlStopActionNum());
						break;
					case "setStopDriverArrest":
							o2.setStopDriverArrest(jsonObject.getBoolean(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(TrafficStop.VAR_stopDriverArrest + "=$" + num);
							num++;
							bParams.add(o2.sqlStopDriverArrest());
						break;
					case "setStopPassengerArrest":
							o2.setStopPassengerArrest(jsonObject.getBoolean(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(TrafficStop.VAR_stopPassengerArrest + "=$" + num);
							num++;
							bParams.add(o2.sqlStopPassengerArrest());
						break;
					case "setStopEncounterForce":
							o2.setStopEncounterForce(jsonObject.getBoolean(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(TrafficStop.VAR_stopEncounterForce + "=$" + num);
							num++;
							bParams.add(o2.sqlStopEncounterForce());
						break;
					case "setStopEngageForce":
							o2.setStopEngageForce(jsonObject.getBoolean(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(TrafficStop.VAR_stopEngageForce + "=$" + num);
							num++;
							bParams.add(o2.sqlStopEngageForce());
						break;
					case "setStopOfficerInjury":
							o2.setStopOfficerInjury(jsonObject.getBoolean(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(TrafficStop.VAR_stopOfficerInjury + "=$" + num);
							num++;
							bParams.add(o2.sqlStopOfficerInjury());
						break;
					case "setStopDriverInjury":
							o2.setStopDriverInjury(jsonObject.getBoolean(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(TrafficStop.VAR_stopDriverInjury + "=$" + num);
							num++;
							bParams.add(o2.sqlStopDriverInjury());
						break;
					case "setStopPassengerInjury":
							o2.setStopPassengerInjury(jsonObject.getBoolean(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(TrafficStop.VAR_stopPassengerInjury + "=$" + num);
							num++;
							bParams.add(o2.sqlStopPassengerInjury());
						break;
					case "setStopOfficerId":
							o2.setStopOfficerId(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(TrafficStop.VAR_stopOfficerId + "=$" + num);
							num++;
							bParams.add(o2.sqlStopOfficerId());
						break;
					case "setStopLocationId":
							o2.setStopLocationId(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(TrafficStop.VAR_stopLocationId + "=$" + num);
							num++;
							bParams.add(o2.sqlStopLocationId());
						break;
					case "setStopCityId":
							o2.setStopCityId(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(TrafficStop.VAR_stopCityId + "=$" + num);
							num++;
							bParams.add(o2.sqlStopCityId());
						break;
					case "setPersonKeys":
						JsonArray setPersonKeysValues = Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray());
						setPersonKeysValues.stream().map(oVal -> oVal.toString()).forEach(val -> {
							futures2.add(Future.future(promise2 -> {
								search(siteRequest).query(TrafficPerson.class, val, inheritPk).onSuccess(pk2 -> {
									sql(siteRequest).update(TrafficPerson.class, pk2).set(TrafficPerson.VAR_trafficStopKey, TrafficStop.class, pk).onSuccess(a -> {
										promise2.complete();
									}).onFailure(ex -> {
										promise2.fail(ex);
									});
								}).onFailure(ex -> {
									promise2.fail(ex);
								});
							}));
						});
						Optional.ofNullable(o.getPersonKeys()).orElse(Arrays.asList()).stream().filter(oVal -> oVal != null && !setPersonKeysValues.contains(oVal.toString())).forEach(pk2 -> {
							futures2.add(Future.future(promise2 -> {
								sql(siteRequest).update(TrafficPerson.class, pk2).setToNull(TrafficPerson.VAR_trafficStopKey, TrafficStop.class, pk2).onSuccess(a -> {
									promise2.complete();
								}).onFailure(ex -> {
									promise2.fail(ex);
								});
							}));
						});
					case "addAllPersonKeys":
						JsonArray addAllPersonKeysValues = Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray());
						addAllPersonKeysValues.stream().map(oVal -> oVal.toString()).forEach(val -> {
							futures2.add(Future.future(promise2 -> {
								search(siteRequest).query(TrafficPerson.class, val, inheritPk).onSuccess(pk2 -> {
									sql(siteRequest).update(TrafficPerson.class, pk2).set(TrafficPerson.VAR_trafficStopKey, TrafficStop.class, pk).onSuccess(a -> {
										promise2.complete();
									}).onFailure(ex -> {
										promise2.fail(ex);
									});
								}).onFailure(ex -> {
									promise2.fail(ex);
								});
							}));
						});
					case "addPersonKeys":
						Optional.ofNullable(jsonObject.getString(entityVar)).ifPresent(val -> {
							futures2.add(Future.future(promise2 -> {
								search(siteRequest).query(TrafficPerson.class, val, inheritPk).onSuccess(pk2 -> {
									sql(siteRequest).update(TrafficPerson.class, pk2).set(TrafficPerson.VAR_trafficStopKey, TrafficStop.class, pk).onSuccess(a -> {
										promise2.complete();
									}).onFailure(ex -> {
										promise2.fail(ex);
									});
								}).onFailure(ex -> {
									promise2.fail(ex);
								});
							}));
						});
					case "removePersonKeys":
						Optional.ofNullable(jsonObject.getLong(entityVar)).ifPresent(pk2 -> {
							futures2.add(Future.future(promise2 -> {
								sql(siteRequest).update(TrafficPerson.class, pk2).setToNull(TrafficPerson.VAR_trafficStopKey, TrafficStop.class, pk2).onSuccess(a -> {
									promise2.complete();
								}).onFailure(ex -> {
									promise2.fail(ex);
								});
							}));
						});
				}
			}
			bSql.append(" WHERE pk=$" + num);
			if(bParams.size() > 0) {
				bParams.add(pk);
				num++;
				futures2.add(0, Future.future(a -> {
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
			CompositeFuture.all(futures1).onSuccess(a -> {
				CompositeFuture.all(futures2).onSuccess(b -> {
					TrafficStop o3 = new TrafficStop();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
					promise.complete(o3);
				}).onFailure(ex -> {
					LOG.error(String.format("sqlPATCHTrafficStop failed. ", ex));
					promise.fail(ex);
				});
			}).onFailure(ex -> {
				LOG.error(String.format("sqlPATCHTrafficStop failed. ", ex));
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("sqlPATCHTrafficStop failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<ServiceResponse> response200PATCHTrafficStop(SiteRequestEnUS siteRequest) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			JsonObject json = new JsonObject();
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200PATCHTrafficStop failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// GET //

	@Override
	public void getTrafficStop(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setRequestUri("/api/traffic-stop/{id}");
				siteRequest.setRequestMethod("GET");
				{
					searchTrafficStopList(siteRequest, false, true, false, "/api/traffic-stop/{id}", "GET").onSuccess(listTrafficStop -> {
						response200GETTrafficStop(listTrafficStop).onSuccess(response -> {
							eventHandler.handle(Future.succeededFuture(response));
							LOG.debug(String.format("getTrafficStop succeeded. "));
						}).onFailure(ex -> {
							LOG.error(String.format("getTrafficStop failed. ", ex));
							error(siteRequest, eventHandler, ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("getTrafficStop failed. ", ex));
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("getTrafficStop failed. ", ex));
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("getTrafficStop failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("getTrafficStop failed. ", ex));
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<ServiceResponse> response200GETTrafficStop(SearchList<TrafficStop> listTrafficStop) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = listTrafficStop.getSiteRequest_();
			SolrDocumentList solrDocuments = listTrafficStop.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listTrafficStop.getList().stream().findFirst().orElse(null));
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200GETTrafficStop failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// Search //

	@Override
	public void searchTrafficStop(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setRequestUri("/api/traffic-stop");
				siteRequest.setRequestMethod("Search");
				{
					searchTrafficStopList(siteRequest, false, true, false, "/api/traffic-stop", "Search").onSuccess(listTrafficStop -> {
						response200SearchTrafficStop(listTrafficStop).onSuccess(response -> {
							eventHandler.handle(Future.succeededFuture(response));
							LOG.debug(String.format("searchTrafficStop succeeded. "));
						}).onFailure(ex -> {
							LOG.error(String.format("searchTrafficStop failed. ", ex));
							error(siteRequest, eventHandler, ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("searchTrafficStop failed. ", ex));
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("searchTrafficStop failed. ", ex));
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("searchTrafficStop failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("searchTrafficStop failed. ", ex));
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<ServiceResponse> response200SearchTrafficStop(SearchList<TrafficStop> listTrafficStop) {
		Promise<ServiceResponse> promise = Promise.promise();
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
					JsonObject facetFieldCounts = new JsonObject();
					facetFieldsJson.put(facetFieldVar, facetFieldCounts);
					List<FacetField.Count> facetFieldValues = facetField.getValues();
					for(Integer i = 0; i < facetFieldValues.size(); i+= 1) {
						FacetField.Count count = (FacetField.Count)facetFieldValues.get(i);
						facetFieldCounts.put(count.getName(), count.getCount());
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
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200SearchTrafficStop failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
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
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setRequestUri("/api/admin/traffic-stop");
				siteRequest.setRequestMethod("AdminSearch");
				{
					searchTrafficStopList(siteRequest, false, true, false, "/api/admin/traffic-stop", "AdminSearch").onSuccess(listTrafficStop -> {
						response200AdminSearchTrafficStop(listTrafficStop).onSuccess(response -> {
							eventHandler.handle(Future.succeededFuture(response));
							LOG.debug(String.format("adminsearchTrafficStop succeeded. "));
						}).onFailure(ex -> {
							LOG.error(String.format("adminsearchTrafficStop failed. ", ex));
							error(siteRequest, eventHandler, ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("adminsearchTrafficStop failed. ", ex));
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("adminsearchTrafficStop failed. ", ex));
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("adminsearchTrafficStop failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("adminsearchTrafficStop failed. ", ex));
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<ServiceResponse> response200AdminSearchTrafficStop(SearchList<TrafficStop> listTrafficStop) {
		Promise<ServiceResponse> promise = Promise.promise();
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
					JsonObject facetFieldCounts = new JsonObject();
					facetFieldsJson.put(facetFieldVar, facetFieldCounts);
					List<FacetField.Count> facetFieldValues = facetField.getValues();
					for(Integer i = 0; i < facetFieldValues.size(); i+= 1) {
						FacetField.Count count = (FacetField.Count)facetFieldValues.get(i);
						facetFieldCounts.put(count.getName(), count.getCount());
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
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200AdminSearchTrafficStop failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
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
	public static final String VAR_trafficStopKey = "trafficStopKey";
	public static final String VAR_stateAbbreviation = "stateAbbreviation";
	public static final String VAR_stateSearch = "stateSearch";
	public static final String VAR_state_ = "state_";
	public static final String VAR_stateKey = "stateKey";
	public static final String VAR_stateName = "stateName";
	public static final String VAR_agencyKey = "agencyKey";
	public static final String VAR_agencySearch = "agencySearch";
	public static final String VAR_agency_ = "agency_";
	public static final String VAR_agencyTitle = "agencyTitle";
	public static final String VAR_stopDateTime = "stopDateTime";
	public static final String VAR_stopYear = "stopYear";
	public static final String VAR_stopPurposeNum = "stopPurposeNum";
	public static final String VAR_stopPurposeTitle = "stopPurposeTitle";
	public static final String VAR_stopActionNum = "stopActionNum";
	public static final String VAR_stopActionTitle = "stopActionTitle";
	public static final String VAR_stopDriverArrest = "stopDriverArrest";
	public static final String VAR_stopPassengerArrest = "stopPassengerArrest";
	public static final String VAR_stopEncounterForce = "stopEncounterForce";
	public static final String VAR_stopEngageForce = "stopEngageForce";
	public static final String VAR_stopOfficerInjury = "stopOfficerInjury";
	public static final String VAR_stopDriverInjury = "stopDriverInjury";
	public static final String VAR_stopPassengerInjury = "stopPassengerInjury";
	public static final String VAR_stopOfficerId = "stopOfficerId";
	public static final String VAR_stopLocationId = "stopLocationId";
	public static final String VAR_stopCityId = "stopCityId";
	public static final String VAR_personKeys = "personKeys";
	public static final String VAR_personSearch = "personSearch";
	public static final String VAR_personRaceTitles = "personRaceTitles";
	public static final String VAR_trafficSearchSearch = "trafficSearchSearch";
	public static final String VAR_trafficSearchRaceTitles = "trafficSearchRaceTitles";

	// General //

	public Future<TrafficStop> createTrafficStop(SiteRequestEnUS siteRequest) {
		Promise<TrafficStop> promise = Promise.promise();
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			String userId = siteRequest.getUserId();
			Long userKey = siteRequest.getUserKey();
			ZonedDateTime created = Optional.ofNullable(siteRequest.getJsonObject()).map(j -> j.getString("created")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(config.getString("siteZone"))))).orElse(ZonedDateTime.now(ZoneId.of(config.getString("siteZone"))));

			sqlConnection.preparedQuery("INSERT INTO TrafficStop(created) VALUES($1) RETURNING pk")
					.collecting(Collectors.toList())
					.execute(Tuple.of(created.toOffsetDateTime())).onSuccess(result -> {
				Row createLine = result.value().stream().findFirst().orElseGet(() -> null);
				Long pk = createLine.getLong(0);
				TrafficStop o = new TrafficStop();
				o.setPk(pk);
				o.setSiteRequest_(siteRequest);
				promise.complete(o);
			}).onFailure(ex -> {
				LOG.error("createTrafficStop failed. ", ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("createTrafficStop failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public void searchTrafficStopQ(String uri, String apiMethod, SearchList<TrafficStop> searchList, String entityVar, String valueIndexed, String varIndexed) {
		searchList.setQuery(varIndexed + ":" + ("*".equals(valueIndexed) ? valueIndexed : ClientUtils.escapeQueryChars(valueIndexed)));
		if(!"*".equals(entityVar)) {
		}
	}

	public String searchTrafficStopFq(String uri, String apiMethod, SearchList<TrafficStop> searchList, String entityVar, String valueIndexed, String varIndexed) {
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

	public void searchTrafficStopSort(String uri, String apiMethod, SearchList<TrafficStop> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addSort(varIndexed, ORDER.valueOf(valueIndexed));
	}

	public void searchTrafficStopRows(String uri, String apiMethod, SearchList<TrafficStop> searchList, Integer valueRows) {
			searchList.setRows(apiMethod != null && apiMethod.contains("Search") ? valueRows : 10);
	}

	public void searchTrafficStopStart(String uri, String apiMethod, SearchList<TrafficStop> searchList, Integer valueStart) {
		searchList.setStart(valueStart);
	}

	public void searchTrafficStopVar(String uri, String apiMethod, SearchList<TrafficStop> searchList, String var, String value) {
		searchList.getSiteRequest_().getRequestVars().put(var, value);
	}

	public void searchTrafficStopUri(String uri, String apiMethod, SearchList<TrafficStop> searchList) {
	}

	public Future<ServiceResponse> varsTrafficStop(SiteRequestEnUS siteRequest) {
		Promise<ServiceResponse> promise = Promise.promise();
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
				} catch(Exception ex) {
					LOG.error(String.format("searchTrafficStop failed. "), ex);
					promise.fail(ex);
				}
			});
			promise.complete();
		} catch(Exception ex) {
			LOG.error(String.format("searchTrafficStop failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<SearchList<TrafficStop>> searchTrafficStopList(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod) {
		Promise<SearchList<TrafficStop>> promise = Promise.promise();
		try {
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
									searchTrafficStopQ(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
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
											String entityFq = searchTrafficStopFq(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
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
									searchTrafficStopSort(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
									break;
								case "start":
									valueStart = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
									searchTrafficStopStart(uri, apiMethod, searchList, valueStart);
									break;
								case "rows":
									valueRows = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
									searchTrafficStopRows(uri, apiMethod, searchList, valueRows);
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
									searchTrafficStopVar(uri, apiMethod, searchList, entityVar, valueIndexed);
									break;
							}
						}
						searchTrafficStopUri(uri, apiMethod, searchList);
					}
				} catch(Exception e) {
					ExceptionUtils.rethrow(e);
				}
			});
			if("*:*".equals(searchList.getQuery()) && searchList.getSorts().size() == 0) {
				searchList.addSort("created_indexed_date", ORDER.desc);
			}
			searchTrafficStop2(siteRequest, populate, store, modify, uri, apiMethod, searchList);
			searchList.promiseDeepForClass(siteRequest).onSuccess(a -> {
				promise.complete(searchList);
			}).onFailure(ex -> {
				LOG.error(String.format("searchTrafficStop failed. ", ex));
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("searchTrafficStop failed. ", ex));
			promise.fail(ex);
		}
		return promise.future();
	}
	public void searchTrafficStop2(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, SearchList<TrafficStop> searchList) {
	}

	public Future<Void> defineTrafficStop(TrafficStop o) {
		Promise<Void> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT * FROM TrafficStop WHERE pk=$1")
					.collecting(Collectors.toList())
					.execute(Tuple.of(pk)
					).onSuccess(result -> {
				try {
					for(Row definition : result.value()) {
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
					promise.complete();
				} catch(Exception ex) {
					LOG.error(String.format("defineTrafficStop failed. "), ex);
					promise.fail(ex);
				}
			}).onFailure(ex -> {
				LOG.error(String.format("defineTrafficStop failed. ", ex));
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("defineTrafficStop failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<Void> attributeTrafficStop(TrafficStop o) {
		Promise<Void> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT pk as pk2, 'personKeys' from TrafficPerson where trafficStopKey=$1")
					.collecting(Collectors.toList())
					.execute(Tuple.of(pk)
					).onSuccess(result -> {
				try {
					if(result != null) {
						for(Row definition : result.value()) {
							o.attributeForClass(definition.getString(1), definition.getLong(0));
						}
					}
					promise.complete();
				} catch(Exception ex) {
					LOG.error(String.format("attributeTrafficStop failed. "), ex);
					promise.fail(ex);
				}
			}).onFailure(ex -> {
				LOG.error(String.format("attributeTrafficStop failed. ", ex));
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("attributeTrafficStop failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<Void> indexTrafficStop(TrafficStop o) {
		Promise<Void> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			o.promiseDeepForClass(siteRequest).onSuccess(a -> {
				SolrInputDocument document = new SolrInputDocument();
				o.indexTrafficStop(document);
				String solrHostName = siteRequest.getConfig().getString(ConfigKeys.SOLR_HOST_NAME);
				Integer solrPort = siteRequest.getConfig().getInteger(ConfigKeys.SOLR_PORT);
				String solrCollection = siteRequest.getConfig().getString(ConfigKeys.SOLR_COLLECTION);
				String solrRequestUri = String.format("/solr/%s/update%s", solrCollection, "?commitWithin=10000&overwrite=true&wt=json");
				JsonArray json = new JsonArray().add(new JsonObject(document.toMap(new HashMap<String, Object>())));
				webClient.post(solrPort, solrHostName, solrRequestUri).putHeader("Content-Type", "application/json").expect(ResponsePredicate.SC_OK).sendBuffer(json.toBuffer()).onSuccess(b -> {
					promise.complete();
				}).onFailure(ex -> {
					LOG.error(String.format("indexTrafficStop failed. "), new RuntimeException(ex));
					promise.fail(ex);
				});
			}).onFailure(ex -> {
				LOG.error(String.format("indexTrafficStop failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("indexTrafficStop failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<Void> refreshTrafficStop(TrafficStop o) {
		Promise<Void> promise = Promise.promise();
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Boolean refresh = !"false".equals(siteRequest.getRequestVars().get("refresh"));
			if(refresh && !Optional.ofNullable(siteRequest.getJsonObject()).map(JsonObject::isEmpty).orElse(true)) {
				SearchList<TrafficStop> searchList = new SearchList<TrafficStop>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(TrafficStop.class);
				searchList.addFilterQuery("modified_indexed_date:[" + DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(siteRequest.getApiRequest_().getCreated().toInstant(), ZoneId.of("UTC"))) + " TO *]");
				searchList.add("json.facet", "{personKeys:{terms:{field:personKeys_indexed_longs, limit:1000}}}");
				searchList.setRows(1000);
				searchList.promiseDeepSearchList(siteRequest).onSuccess(a -> {
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
							futures.add(Future.future(promise2 -> {
								searchList2.promiseDeepSearchList(siteRequest).onSuccess(b -> {
									TrafficPerson o2 = searchList2.getList().stream().findFirst().orElse(null);
									if(o2 != null) {
										JsonObject params = new JsonObject();
										params.put("body", new JsonObject());
										params.put("query", new JsonObject().put("q", "*:*").put("fq", new JsonArray().add("pk:" + pk2)));
										JsonObject context = new JsonObject().put("params", params).put("user", siteRequest.getJsonPrincipal());
										JsonObject json = new JsonObject().put("context", context);
										eventBus.request("opendatapolicing-enUS-TrafficPerson", json, new DeliveryOptions().addHeader("action", "patchTrafficPerson")).onSuccess(c -> {
											promise2.complete();
										}).onFailure(ex -> {
											promise2.fail(ex);
										});
									}
								}).onFailure(ex -> {
									promise2.fail(ex);
								});
							}));
						}
					}

					CompositeFuture.all(futures).onSuccess(b -> {
						List<Future> futures2 = new ArrayList<>();
						for(TrafficStop o2 : searchList.getList()) {
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUS(siteRequest.getUser(), siteRequest.getServiceRequest(), new JsonObject());
							o2.setSiteRequest_(siteRequest2);
							futures2.add(
								patchTrafficStopFuture(o2, false).onFailure(ex -> {
									LOG.error(String.format("TrafficStop %s failed. ", o2.getPk()), ex);
								})
							);
						}

						CompositeFuture.all(futures2).onSuccess(c -> {
							promise.complete();
						}).onFailure(ex -> {
							LOG.error("Refresh relations failed. ", ex);
							promise.fail(ex);
						});
					}).onFailure(ex -> {
						LOG.error("Refresh relations failed. ", ex);
						promise.fail(ex);
					});
				}).onFailure(ex -> {
					LOG.error("Refresh relations failed. ", ex);
					promise.fail(ex);
				});
			} else {
				promise.complete();
			}
		} catch(Exception ex) {
			LOG.error(String.format("refreshTrafficStop failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}
}
