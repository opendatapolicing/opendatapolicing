package com.opendatapolicing.enus.trafficstop;

import com.opendatapolicing.enus.trafficperson.TrafficPersonEnUSApiServiceImpl;
import com.opendatapolicing.enus.trafficperson.TrafficPerson;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.opendatapolicing.enus.user.SiteUser;
import com.opendatapolicing.enus.request.api.ApiRequest;
import com.opendatapolicing.enus.search.SearchResult;
import com.opendatapolicing.enus.vertx.MailVerticle;
import com.opendatapolicing.enus.config.ConfigKeys;
import com.opendatapolicing.enus.cluster.BaseApiServiceImpl;
import io.vertx.ext.web.client.WebClient;
import java.util.Objects;
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
import org.apache.solr.common.SolrInputDocument;
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
public class TrafficStopEnUSGenApiServiceImpl extends BaseApiServiceImpl implements TrafficStopEnUSGenApiService {

	protected static final Logger LOG = LoggerFactory.getLogger(TrafficStopEnUSGenApiServiceImpl.class);

	public TrafficStopEnUSGenApiServiceImpl(EventBus eventBus, JsonObject config, WorkerExecutor workerExecutor, PgPool pgPool, WebClient webClient, OAuth2Auth oauth2AuthenticationProvider, AuthorizationProvider authorizationProvider) {
		super(eventBus, config, workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider);
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
										blockingCodeHandler.handle(Future.succeededFuture());
									}).onFailure(ex -> {
										LOG.error(String.format("putimportTrafficStop failed. ", ex));
										blockingCodeHandler.handle(Future.failedFuture(ex));
									});
								}).onFailure(ex -> {
									LOG.error(String.format("putimportTrafficStop failed. ", ex));
									blockingCodeHandler.handle(Future.failedFuture(ex));
								});
							} catch(Exception ex) {
								LOG.error(String.format("putimportTrafficStop failed. ", ex));
								error(siteRequest, null, ex);
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
										blockingCodeHandler.handle(Future.succeededFuture());
									}).onFailure(ex -> {
										LOG.error(String.format("putmergeTrafficStop failed. ", ex));
										blockingCodeHandler.handle(Future.failedFuture(ex));
									});
								}).onFailure(ex -> {
									LOG.error(String.format("putmergeTrafficStop failed. ", ex));
									blockingCodeHandler.handle(Future.failedFuture(ex));
								});
							} catch(Exception ex) {
								LOG.error(String.format("putmergeTrafficStop failed. ", ex));
								error(siteRequest, null, ex);
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
								aSearchTrafficStop(siteRequest, false, true, true, "/api/traffic-stop/copy", "PUTCopy").onSuccess(listTrafficStop -> {
									SearchList<TrafficStop> listTrafficStop = d.result();
									ApiRequest apiRequest = new ApiRequest();
									apiRequest.setRows(listTrafficStop.getRows());
									apiRequest.setNumFound(listTrafficStop.getQueryResponse().getResults().getNumFound());
									apiRequest.setNumPATCH(0L);
									apiRequest.initDeepApiRequest(siteRequest);
									siteRequest.setApiRequest_(apiRequest);
									eventBus.publish("websocketTrafficStop", JsonObject.mapFrom(apiRequest).toString());
									listPUTCopyTrafficStop(apiRequest, listTrafficStop).onSuccess(e -> {
										putcopyTrafficStopResponse(siteRequest).onSuccess(f -> {
											LOG.debug(String.format("putcopyTrafficStop succeeded. "));
											blockingCodeHandler.handle(Future.succeededFuture(f.result()));
										}).onFailure(ex -> {
											LOG.error(String.format("putcopyTrafficStop failed. ", ex));
											error(siteRequest, null, ex);
										});
									}).onFailure(ex -> {
										LOG.error(String.format("putcopyTrafficStop failed. ", ex));
										error(siteRequest, null, ex);
									});
								}).onFailure(ex -> {
									LOG.error(String.format("putcopyTrafficStop failed. ", ex));
									error(siteRequest, null, ex);
								});
							} catch(Exception ex) {
								LOG.error(String.format("putcopyTrafficStop failed. ", ex));
								error(siteRequest, null, ex);
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
				createTrafficStop(siteRequest, a -> {
					if(a.succeeded()) {
						TrafficStop trafficStop = a.result();
						sqlPUTCopyTrafficStop(trafficStop, jsonObject, b -> {
							if(b.succeeded()) {
								defineTrafficStop(trafficStop, c -> {
									if(c.succeeded()) {
										attributeTrafficStop(trafficStop, d -> {
											if(d.succeeded()) {
												indexTrafficStop(trafficStop).onSuccess(e -> {
													promise1.complete(trafficStop);
												}).onFailure(ex -> {
													LOG.error(String.format("putcopyTrafficStopFuture failed. ", ex));
													promise1.fail(ex);
												});
											} else {
												LOG.error(String.format("putcopyTrafficStopFuture failed. ", d.cause()));
												promise1.fail(d.cause());
											}
										});
									} else {
										LOG.error(String.format("putcopyTrafficStopFuture failed. ", c.cause()));
										promise1.fail(c.cause());
									}
								});
							} else {
								LOG.error(String.format("putcopyTrafficStopFuture failed. ", b.cause()));
								promise1.fail(b.cause());
							}
						});
					} else {
						LOG.error(String.format("putcopyTrafficStopFuture failed. ", a.cause()));
						promise1.fail(a.cause());
					}
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, ex);
			}).compose(trafficStop -> {
				Promise<TrafficStop> promise2 = Promise.promise();
				refreshTrafficStop(trafficStop, a -> {
					if(a.succeeded()) {
						ApiRequest apiRequest = siteRequest.getApiRequest_();
						if(apiRequest != null) {
							apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
							trafficStop.apiRequestTrafficStop();
							eventBus.publish("websocketTrafficStop", JsonObject.mapFrom(apiRequest).toString());
						}
						promise2.complete(trafficStop);
					} else {
						LOG.error(String.format("putcopyTrafficStopFuture failed. ", a.cause()));
						promise2.fail(a.cause());
					}
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
					case "stateAbbreviation":
						o2.setStateAbbreviation(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stateAbbreviation=$" + num);
						num++;
						bParams.add(o2.sqlStateAbbreviation());
						break;
					case "agencyTitle":
						o2.setAgencyTitle(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("agencyTitle=$" + num);
						num++;
						bParams.add(o2.sqlAgencyTitle());
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
					LOG.error(String.format("sqlPUTCopyTrafficStop failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPUTCopyTrafficStop failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
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
					case "stateAbbreviation":
						o2.setStateAbbreviation(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stateAbbreviation=$" + num);
						num++;
						bParams.add(o2.sqlStateAbbreviation());
						break;
					case "agencyTitle":
						o2.setAgencyTitle(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("agencyTitle=$" + num);
						num++;
						bParams.add(o2.sqlAgencyTitle());
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
								SearchList<TrafficPerson> searchList = new SearchList<TrafficPerson>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(TrafficPerson.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
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
					response200PATCHTrafficStop(siteRequest).onSuccess(response -> {
						eventHandler.handle(Future.succeededFuture(response));
						workerExecutor.executeBlocking(blockingCodeHandler -> {
							aSearchTrafficStop(siteRequest, false, true, true, "/api/traffic-stop", "PATCH").onSuccess(listTrafficStop -> {
								try {
									List<String> roles2 = Arrays.asList("SiteAdmin");
									if(listTrafficStop.getQueryResponse().getResults().getNumFound() > 1
											&& !CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles2)
											&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles2)
											) {
										String message = String.format("roles required: " + String.join(", ", roles2));
										LOG.error(message);
										error(siteRequest, eventHandler, Future.failedFuture(message));
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
											patchTrafficStopResponse(siteRequest).onSuccess(f -> {
												LOG.debug(String.format("patchTrafficStop succeeded. "));
												blockingCodeHandler.handle(Future.succeededFuture(f.result()));
											}).onFailure(ex -> {
												LOG.error(String.format("patchTrafficStop failed. ", ex));
												error(siteRequest, null, ex);
											});
										}).onFailure(ex -> {
											LOG.error(String.format("patchTrafficStop failed. ", ex));
											error(siteRequest, null, ex);
										});
									}
								} catch(Exception ex) {
									LOG.error(String.format("patchTrafficStop failed. ", ex));
									error(siteRequest, null, ex);
								}
							}).onFailure(ex -> {
								LOG.error(String.format("patchTrafficStop failed. ", ex));
								error(siteRequest, null, ex);
							});
						}, resultHandler -> {
						});
					}).onFailure(ex -> {
						LOG.error(String.format("patchTrafficStop failed. ", ex));
						error(siteRequest, eventHandler, ex);
					});
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
				sqlPATCHTrafficStop(o, inheritPk, a -> {
					if(a.succeeded()) {
						TrafficStop trafficStop = a.result();
						defineTrafficStop(trafficStop, c -> {
							if(c.succeeded()) {
								attributeTrafficStop(trafficStop, d -> {
									if(d.succeeded()) {
										indexTrafficStop(trafficStop).onSuccess(e -> {
											promise1.complete(trafficStop);
										}).onFailure(ex -> {
											LOG.error(String.format("patchTrafficStopFuture failed. ", ex));
											promise1.fail(ex);
										});
									} else {
										LOG.error(String.format("patchTrafficStopFuture failed. ", d.cause()));
										promise1.fail(d.cause());
									}
								});
							} else {
								LOG.error(String.format("patchTrafficStopFuture failed. ", c.cause()));
								promise1.fail(c.cause());
							}
						});
					} else {
						LOG.error(String.format("patchTrafficStopFuture failed. ", a.cause()));
								promise1.fail(a.cause());
					}
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, ex);
			}).compose(trafficStop -> {
				Promise<TrafficStop> promise2 = Promise.promise();
				refreshTrafficStop(trafficStop, a -> {
					if(a.succeeded()) {
						if(apiRequest != null) {
							apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
							trafficStop.apiRequestTrafficStop();
							eventBus.publish("websocketTrafficStop", JsonObject.mapFrom(apiRequest).toString());
						}
						promise2.complete(trafficStop);
					} else {
						LOG.error(String.format("patchTrafficStopFuture failed. ", a.cause()));
						promise2.fail(a.cause());
					}
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
					case "setStateAbbreviation":
							o2.setStateAbbreviation(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("stateAbbreviation=$" + num);
							num++;
							bParams.add(o2.sqlStateAbbreviation());
						break;
					case "setAgencyTitle":
							o2.setAgencyTitle(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("agencyTitle=$" + num);
							num++;
							bParams.add(o2.sqlAgencyTitle());
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
								SearchList<TrafficPerson> searchList = new SearchList<TrafficPerson>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(TrafficPerson.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
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
									SearchList<TrafficPerson> searchList = new SearchList<TrafficPerson>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(TrafficPerson.class);
									searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
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
									SearchList<TrafficPerson> searchList = new SearchList<TrafficPerson>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(TrafficPerson.class);
									searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
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
								SearchList<TrafficPerson> searchList = new SearchList<TrafficPerson>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(TrafficPerson.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
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
					aSearchTrafficStop(siteRequest, false, true, false, "/api/traffic-stop/{id}", "GET").onSuccess(listTrafficStop -> {
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
					aSearchTrafficStop(siteRequest, false, true, false, "/api/traffic-stop", "Search").onSuccess(listTrafficStop -> {
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
					aSearchTrafficStop(siteRequest, false, true, false, "/api/admin/traffic-stop", "AdminSearch").onSuccess(listTrafficStop -> {
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

	// SearchPage //

	@Override
	public void searchpageTrafficStopId(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		searchpageTrafficStop(serviceRequest, eventHandler);
	}

	@Override
	public void searchpageTrafficStop(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setRequestUri("/traffic-stop");
				siteRequest.setRequestMethod("SearchPage");
				{
					aSearchTrafficStop(siteRequest, false, true, false, "/traffic-stop", "SearchPage").onSuccess(listTrafficStop -> {
						response200SearchPageTrafficStop(listTrafficStop).onSuccess(response -> {
							eventHandler.handle(Future.succeededFuture(response));
							LOG.debug(String.format("searchpageTrafficStop succeeded. "));
						}).onFailure(ex -> {
							LOG.error(String.format("searchpageTrafficStop failed. ", ex));
							error(siteRequest, eventHandler, ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("searchpageTrafficStop failed. ", ex));
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("searchpageTrafficStop failed. ", ex));
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("searchpageTrafficStop failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("searchpageTrafficStop failed. ", ex));
				error(null, eventHandler, ex);
			}
		});
	}


	public void searchpageTrafficStopPageInit(TrafficStopPage page, SearchList<TrafficStop> listTrafficStop) {
	}
	public Future<ServiceResponse> response200SearchPageTrafficStop(SearchList<TrafficStop> listTrafficStop) {
		Promise<ServiceResponse> promise = Promise.promise();
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
			promise.complete(new ServiceResponse(200, "OK", buffer, requestHeaders));
		} catch(Exception ex) {
			LOG.error(String.format("response200SearchPageTrafficStop failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

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
					LOG.error(String.format("aSearchTrafficStop failed. "), ex);
					promise.fail(ex);
				}
			});
			promise.complete();
		} catch(Exception ex) {
			LOG.error(String.format("aSearchTrafficStop failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<SearchList<TrafficStop>> aSearchTrafficStopList(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod) {
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
			searchList.promiseDeepForClass(siteRequest).onSuccess(a -> {
				promise.complete(searchList);
			}).onFailure(ex -> {
				LOG.error(String.format("aSearchTrafficStop failed. ", ex));
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("aSearchTrafficStop failed. ", ex));
			promise.fail(ex);
		}
		return promise.future();
	}
	public void aSearchTrafficStop2(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, SearchList<TrafficStop> searchList) {
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
				webClient.post(ConfigKeys.SOLR_URL + "/update?commitWithin=10000&overwrite=true&wt=json").sendBuffer(Buffer.buffer(document.jsonStr())).onSuccess(b -> {
					promise.complete();
				}).onFailure(ex -> {
					LOG.error(String.format("indexTrafficStop failed. "), ex);
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

	public void refreshTrafficStop(TrafficStop o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
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
						searchList2.initDeepSearchList(siteRequest);
						TrafficPerson o2 = searchList2.getList().stream().findFirst().orElse(null);

						if(o2 != null) {
							TrafficPersonEnUSApiServiceImpl service = new TrafficPersonEnUSApiServiceImpl(eventBus, config, workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider);
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUS(siteRequest.getUser(), siteRequest.getServiceRequest(), new JsonObject());
							ApiRequest apiRequest2 = new ApiRequest();
							apiRequest2.setRows(1);
							apiRequest2.setNumFound(1l);
							apiRequest2.setNumPATCH(0L);
							apiRequest2.initDeepApiRequest(siteRequest2);
							siteRequest2.setApiRequest_(apiRequest2);
							eventBus.publish("websocketTrafficPerson", JsonObject.mapFrom(apiRequest2).toString());

							o2.setPk(pk2);
							o2.setSiteRequest_(siteRequest2);
							futures.add(
								service.patchTrafficPersonFuture(o2, false).onFailure(ex -> {
									LOG.error(String.format("TrafficPerson %s failed. ", pk2), ex);
									eventHandler.handle(Future.failedFuture(ex));
								})
							);
						}
					}
					}

					CompositeFuture.all(futures).onComplete(a -> {
						if(a.succeeded()) {
							TrafficStopEnUSApiServiceImpl service = new TrafficStopEnUSApiServiceImpl(eventBus, config, workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider);
							List<Future> futures2 = new ArrayList<>();
							for(TrafficStop o2 : searchList.getList()) {
								SiteRequestEnUS siteRequest2 = generateSiteRequestEnUS(siteRequest.getUser(), siteRequest.getServiceRequest(), new JsonObject());
								o2.setSiteRequest_(siteRequest2);
								futures2.add(
									service.patchTrafficStopFuture(o2, false).onFailure(ex -> {
										LOG.error(String.format("TrafficStop %s failed. ", o2.getPk()), ex);
										eventHandler.handle(ex);
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
				}).onFailure(ex -> {
					LOG.error("Refresh relations failed. ", ex);
					error(siteRequest, eventHandler, ex);
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
