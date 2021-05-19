package com.opendatapolicing.enus.state;

import com.opendatapolicing.enus.agency.SiteAgencyEnUSApiServiceImpl;
import com.opendatapolicing.enus.agency.SiteAgency;
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
import java.util.concurrent.Semaphore;
import io.vertx.core.eventbus.EventBus;
import io.vertx.pgclient.PgPool;
import io.vertx.ext.auth.authorization.AuthorizationProvider;
import io.vertx.core.eventbus.DeliveryOptions;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.time.Instant;
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
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import java.util.HashMap;
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
public class SiteStateEnUSGenApiServiceImpl extends BaseApiServiceImpl implements SiteStateEnUSGenApiService {

	protected static final Logger LOG = LoggerFactory.getLogger(SiteStateEnUSGenApiServiceImpl.class);

	public SiteStateEnUSGenApiServiceImpl(Semaphore semaphore, EventBus eventBus, JsonObject config, WorkerExecutor workerExecutor, PgPool pgPool, WebClient webClient, OAuth2Auth oauth2AuthenticationProvider, AuthorizationProvider authorizationProvider) {
		super(semaphore, eventBus, config, workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider);
	}

	// PUTImport //

	@Override
	public void putimportSiteState(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("putimportSiteState started. "));
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setJsonObject(body);
				siteRequest.setRequestUri("/api/state/import");
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
					try {
						ApiRequest apiRequest = new ApiRequest();
						JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
						apiRequest.setRows(jsonArray.size());
						apiRequest.setNumFound(new Integer(jsonArray.size()).longValue());
						apiRequest.setNumPATCH(0L);
						apiRequest.initDeepApiRequest(siteRequest);
						siteRequest.setApiRequest_(apiRequest);
						eventBus.publish("websocketSiteState", JsonObject.mapFrom(apiRequest).toString());
						varsSiteState(siteRequest).onSuccess(d -> {
							listPUTImportSiteState(apiRequest, siteRequest).onSuccess(e -> {
								response200PUTImportSiteState(siteRequest).onSuccess(response -> {
									LOG.debug(String.format("putimportSiteState succeeded. "));
									eventHandler.handle(Future.succeededFuture(response));
								}).onFailure(ex -> {
									LOG.error(String.format("putimportSiteState failed. "), ex);
									error(siteRequest, eventHandler, ex);
								});
							}).onFailure(ex -> {
								LOG.error(String.format("putimportSiteState failed. "), ex);
								error(siteRequest, eventHandler, ex);
							});
						}).onFailure(ex -> {
							LOG.error(String.format("putimportSiteState failed. "), ex);
							error(siteRequest, eventHandler, ex);
						});
					} catch(Exception ex) {
						LOG.error(String.format("putimportSiteState failed. "), ex);
						error(siteRequest, eventHandler, ex);
					}
				}
			} catch(Exception ex) {
				LOG.error(String.format("putimportSiteState failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("putimportSiteState failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("putimportSiteState failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<Void> listPUTImportSiteState(ApiRequest apiRequest, SiteRequestEnUS siteRequest) {
		Promise<Void> promise = Promise.promise();
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				futures.add(Future.future(promise1 -> {
					workerExecutor.executeBlocking(blockingCodeHandler -> {
						try {
							semaphore.acquire();

							JsonObject params = new JsonObject();
							params.put("body", obj);
							params.put("path", new JsonObject());
							params.put("cookie", new JsonObject());
							params.put("query", new JsonObject());
							JsonObject context = new JsonObject().put("params", params);
							JsonObject json = new JsonObject().put("context", context);
							eventBus.send("opendatapolicing-enUS-SiteState", json, new DeliveryOptions().addHeader("action", "putimportSiteStateFuture"));
							blockingCodeHandler.complete();
						} catch(Exception ex) {
							LOG.error(String.format("listPUTImportSiteState failed. "), ex);
							blockingCodeHandler.fail(ex);
						}
					}).onSuccess(a -> {
						promise1.complete();
					}).onFailure(ex -> {
						LOG.error(String.format("listPUTImportSiteState failed. "), ex);
						promise1.fail(ex);
					});
				}));
			});
			CompositeFuture.all(futures).onSuccess(a -> {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
				promise.complete();
			}).onFailure(ex -> {
				LOG.error(String.format("listPUTImportSiteState failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("listPUTImportSiteState failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	@Override
	public void putimportSiteStateFuture(JsonObject json, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = generateSiteRequestEnUS(null, serviceRequest, json);
			ApiRequest apiRequest = new ApiRequest();
			apiRequest.setRows(1);
			apiRequest.setNumFound(1L);
			apiRequest.setNumPATCH(0L);
			apiRequest.initDeepApiRequest(siteRequest);
			siteRequest.setApiRequest_(apiRequest);
			json.put("inheritPk", json.getValue("pk"));

			SearchList<SiteState> searchList = new SearchList<SiteState>();
			searchList.setStore(true);
			searchList.setQuery("*:*");
			searchList.setC(SiteState.class);
			searchList.addFilterQuery("inheritPk_indexed_string:" + ClientUtils.escapeQueryChars(json.getString("pk")));
			searchList.promiseDeepForClass(siteRequest).onSuccess(a -> {
				try {
					if(searchList.size() == 1) {
						SiteState o = searchList.getList().stream().findFirst().orElse(null);
						SiteState o2 = new SiteState();
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
							siteRequest.setJsonObject(json2);
							patchSiteStateFuture(o, true).onSuccess(b -> {
								semaphore.release();
								eventHandler.handle(Future.succeededFuture());
							}).onFailure(ex -> {
								LOG.error(String.format("putimportSiteStateFuture failed. "), ex);
							});
						}
					} else {
						postSiteStateFuture(siteRequest, true).onSuccess(b -> {
							semaphore.release();
							eventHandler.handle(Future.succeededFuture());
						}).onFailure(ex -> {
							LOG.error(String.format("putimportSiteStateFuture failed. "), ex);
							semaphore.release();
							eventHandler.handle(Future.failedFuture(ex));
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putimportSiteStateFuture failed. "), ex);
					semaphore.release();
					eventHandler.handle(Future.failedFuture(ex));
				}
			}).onFailure(ex -> {
				LOG.error(String.format("putimportSiteStateFuture failed. "), ex);
				semaphore.release();
				eventHandler.handle(Future.failedFuture(ex));
			});
		} catch(Exception ex) {
			LOG.error(String.format("putimportSiteStateFuture failed. "), ex);
			semaphore.release();
			eventHandler.handle(Future.failedFuture(ex));
		}
	}

	public Future<ServiceResponse> response200PUTImportSiteState(SiteRequestEnUS siteRequest) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			JsonObject json = new JsonObject();
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200PUTImportSiteState failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// PUTMerge //

	@Override
	public void putmergeSiteState(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("putmergeSiteState started. "));
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setJsonObject(body);
				siteRequest.setRequestUri("/api/state/merge");
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
					try {
						ApiRequest apiRequest = new ApiRequest();
						JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
						apiRequest.setRows(jsonArray.size());
						apiRequest.setNumFound(new Integer(jsonArray.size()).longValue());
						apiRequest.setNumPATCH(0L);
						apiRequest.initDeepApiRequest(siteRequest);
						siteRequest.setApiRequest_(apiRequest);
						eventBus.publish("websocketSiteState", JsonObject.mapFrom(apiRequest).toString());
						varsSiteState(siteRequest).onSuccess(d -> {
							listPUTMergeSiteState(apiRequest, siteRequest).onSuccess(e -> {
								response200PUTMergeSiteState(siteRequest).onSuccess(response -> {
									LOG.debug(String.format("putmergeSiteState succeeded. "));
									eventHandler.handle(Future.succeededFuture(response));
								}).onFailure(ex -> {
									LOG.error(String.format("putmergeSiteState failed. "), ex);
									error(siteRequest, eventHandler, ex);
								});
							}).onFailure(ex -> {
								LOG.error(String.format("putmergeSiteState failed. "), ex);
								error(siteRequest, eventHandler, ex);
							});
						}).onFailure(ex -> {
							LOG.error(String.format("putmergeSiteState failed. "), ex);
							error(siteRequest, eventHandler, ex);
						});
					} catch(Exception ex) {
						LOG.error(String.format("putmergeSiteState failed. "), ex);
						error(siteRequest, eventHandler, ex);
					}
				}
			} catch(Exception ex) {
				LOG.error(String.format("putmergeSiteState failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("putmergeSiteState failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("putmergeSiteState failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<Void> listPUTMergeSiteState(ApiRequest apiRequest, SiteRequestEnUS siteRequest) {
		Promise<Void> promise = Promise.promise();
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				futures.add(Future.future(promise1 -> {
					workerExecutor.executeBlocking(blockingCodeHandler -> {
						try {
							semaphore.acquire();

							JsonObject params = new JsonObject();
							params.put("body", obj);
							params.put("path", new JsonObject());
							params.put("cookie", new JsonObject());
							params.put("query", new JsonObject());
							JsonObject context = new JsonObject().put("params", params);
							JsonObject json = new JsonObject().put("context", context);
							eventBus.send("opendatapolicing-enUS-SiteState", json, new DeliveryOptions().addHeader("action", "putmergeSiteStateFuture"));
							blockingCodeHandler.complete();
						} catch(Exception ex) {
							LOG.error(String.format("listPUTMergeSiteState failed. "), ex);
							blockingCodeHandler.fail(ex);
						}
					}).onSuccess(a -> {
						promise1.complete();
					}).onFailure(ex -> {
						LOG.error(String.format("listPUTMergeSiteState failed. "), ex);
						promise1.fail(ex);
					});
				}));
			});
			CompositeFuture.all(futures).onSuccess(a -> {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
				promise.complete();
			}).onFailure(ex -> {
				LOG.error(String.format("listPUTMergeSiteState failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("listPUTMergeSiteState failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	@Override
	public void putmergeSiteStateFuture(JsonObject json, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = generateSiteRequestEnUS(null, serviceRequest, json);
			ApiRequest apiRequest = new ApiRequest();
			apiRequest.setRows(1);
			apiRequest.setNumFound(1L);
			apiRequest.setNumPATCH(0L);
			apiRequest.initDeepApiRequest(siteRequest);
			siteRequest.setApiRequest_(apiRequest);
			json.put("inheritPk", json.getValue("pk"));

			SearchList<SiteState> searchList = new SearchList<SiteState>();
			searchList.setStore(true);
			searchList.setQuery("*:*");
			searchList.setC(SiteState.class);
			searchList.addFilterQuery("pk_indexed_long:" + ClientUtils.escapeQueryChars(json.getString("pk")));
			searchList.promiseDeepForClass(siteRequest).onSuccess(a -> {
				try {
					if(searchList.size() == 1) {
						SiteState o = searchList.getList().stream().findFirst().orElse(null);
						SiteState o2 = new SiteState();
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
							siteRequest.setJsonObject(json2);
							patchSiteStateFuture(o, false).onSuccess(b -> {
								semaphore.release();
								eventHandler.handle(Future.succeededFuture());
							}).onFailure(ex -> {
								LOG.error(String.format("putmergeSiteStateFuture failed. "), ex);
							});
						}
					} else {
						postSiteStateFuture(siteRequest, false).onSuccess(b -> {
							semaphore.release();
							eventHandler.handle(Future.succeededFuture());
						}).onFailure(ex -> {
							LOG.error(String.format("putmergeSiteStateFuture failed. "), ex);
							semaphore.release();
							eventHandler.handle(Future.failedFuture(ex));
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putmergeSiteStateFuture failed. "), ex);
					semaphore.release();
					eventHandler.handle(Future.failedFuture(ex));
				}
			}).onFailure(ex -> {
				LOG.error(String.format("putmergeSiteStateFuture failed. "), ex);
				semaphore.release();
				eventHandler.handle(Future.failedFuture(ex));
			});
		} catch(Exception ex) {
			LOG.error(String.format("putmergeSiteStateFuture failed. "), ex);
			semaphore.release();
			eventHandler.handle(Future.failedFuture(ex));
		}
	}

	public Future<ServiceResponse> response200PUTMergeSiteState(SiteRequestEnUS siteRequest) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			JsonObject json = new JsonObject();
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200PUTMergeSiteState failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// PUTCopy //

	@Override
	public void putcopySiteState(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("putcopySiteState started. "));
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setJsonObject(body);
				siteRequest.setRequestUri("/api/state/copy");
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
					response200PUTCopySiteState(siteRequest).onSuccess(response -> {
						eventHandler.handle(Future.succeededFuture(response));
						workerExecutor.executeBlocking(blockingCodeHandler -> {
							try {
								semaphore.acquire();
								searchSiteStateList(siteRequest, false, true, true, "/api/state/copy", "PUTCopy").onSuccess(listSiteState -> {
									ApiRequest apiRequest = new ApiRequest();
									apiRequest.setRows(listSiteState.getRows());
									apiRequest.setNumFound(listSiteState.getQueryResponse().getResults().getNumFound());
									apiRequest.setNumPATCH(0L);
									apiRequest.initDeepApiRequest(siteRequest);
									siteRequest.setApiRequest_(apiRequest);
									eventBus.publish("websocketSiteState", JsonObject.mapFrom(apiRequest).toString());
									listPUTCopySiteState(apiRequest, listSiteState).onSuccess(e -> {
										response200PUTCopySiteState(siteRequest).onSuccess(f -> {
											LOG.debug(String.format("putcopySiteState succeeded. "));
											blockingCodeHandler.complete();
										}).onFailure(ex -> {
											LOG.error(String.format("putcopySiteState failed. "), ex);
											blockingCodeHandler.fail(ex);
										});
									}).onFailure(ex -> {
										LOG.error(String.format("putcopySiteState failed. "), ex);
										blockingCodeHandler.fail(ex);
									});
								}).onFailure(ex -> {
									LOG.error(String.format("putcopySiteState failed. "), ex);
									blockingCodeHandler.fail(ex);
								});
							} catch(Exception ex) {
								LOG.error(String.format("putcopySiteState failed. "), ex);
								blockingCodeHandler.fail(ex);
							}
						}, resultHandler -> {
							semaphore.release();
						});
					}).onFailure(ex -> {
						LOG.error(String.format("putcopySiteState failed. "), ex);
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("putcopySiteState failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("putcopySiteState failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("putcopySiteState failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<Void> listPUTCopySiteState(ApiRequest apiRequest, SearchList<SiteState> listSiteState) {
		Promise<Void> promise = Promise.promise();
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listSiteState.getSiteRequest_();
		listSiteState.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = siteRequest.copy();
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				putcopySiteStateFuture(siteRequest2, JsonObject.mapFrom(o)).onFailure(ex -> {
					LOG.error(String.format("listPUTCopySiteState failed. "), ex);
					error(siteRequest, null, ex);
				})
			);
		});
		CompositeFuture.all(futures).onSuccess(a -> {
			apiRequest.setNumPATCH(apiRequest.getNumPATCH() + listSiteState.size());
			listSiteState.next().onSuccess(next -> {
				if(next) {
					listPUTCopySiteState(apiRequest, listSiteState);
				} else {
					promise.complete();
				}
			}).onFailure(ex -> {
				LOG.error(String.format("listPUTCopySiteState failed. "), ex);
				error(listSiteState.getSiteRequest_(), null, ex);
			});
		}).onFailure(ex -> {
			LOG.error(String.format("listPUTCopySiteState failed. "), ex);
			error(listSiteState.getSiteRequest_(), null, ex);
		});
		return promise.future();
	}

	public Future<SiteState> putcopySiteStateFuture(SiteRequestEnUS siteRequest, JsonObject jsonObject) {
		Promise<SiteState> promise = Promise.promise();

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
				Promise<SiteState> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				createSiteState(siteRequest).onSuccess(siteState -> {
					sqlPUTCopySiteState(siteState, jsonObject).onSuccess(b -> {
						defineSiteState(siteState).onSuccess(c -> {
							attributeSiteState(siteState).onSuccess(d -> {
								indexSiteState(siteState).onSuccess(e -> {
									promise1.complete(siteState);
								}).onFailure(ex -> {
									LOG.error(String.format("putcopySiteStateFuture failed. "), ex);
									promise1.fail(ex);
								});
							}).onFailure(ex -> {
								LOG.error(String.format("putcopySiteStateFuture failed. "), ex);
								promise1.fail(ex);
							});
						}).onFailure(ex -> {
							LOG.error(String.format("putcopySiteStateFuture failed. "), ex);
							promise1.fail(ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("putcopySiteStateFuture failed. "), ex);
						promise1.fail(ex);
					});
				}).onFailure(ex -> {
					LOG.error(String.format("putcopySiteStateFuture failed. "), ex);
					promise1.fail(ex);
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, ex);
			}).compose(siteState -> {
				Promise<SiteState> promise2 = Promise.promise();
				refreshSiteState(siteState).onSuccess(a -> {
					ApiRequest apiRequest = siteRequest.getApiRequest_();
					if(apiRequest != null) {
						apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
						siteState.apiRequestSiteState();
						eventBus.publish("websocketSiteState", JsonObject.mapFrom(apiRequest).toString());
					}
					promise2.complete(siteState);
				}).onFailure(ex -> {
					LOG.error(String.format("putcopySiteStateFuture failed. "), ex);
					promise2.fail(ex);
				});
				return promise2.future();
			}).onSuccess(siteState -> {
				promise.complete(siteState);
				LOG.debug(String.format("putcopySiteStateFuture succeeded. "));
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("putcopySiteStateFuture failed. "), ex);
			promise.fail(ex);
			error(siteRequest, null, ex);
		}
		return promise.future();
	}

	public Future<Void> sqlPUTCopySiteState(SiteState o, JsonObject jsonObject) {
		Promise<Void> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE SiteState SET ");
			List<Object> bParams = new ArrayList<Object>();
			SiteState o2 = new SiteState();
			o2.setSiteRequest_(siteRequest);
			Long pk = o.getPk();
			List<Future> futures = new ArrayList<>();

			if(jsonObject != null) {
				JsonArray entityVars = jsonObject.getJsonArray("saves");
				for(Integer i = 0; i < entityVars.size(); i++) {
					String entityVar = entityVars.getString(i);
					switch(entityVar) {
					case SiteState.VAR_inheritPk:
						o2.setInheritPk(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SiteState.VAR_inheritPk + "=$" + num);
						num++;
						bParams.add(o2.sqlInheritPk());
						break;
					case SiteState.VAR_stateName:
						o2.setStateName(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SiteState.VAR_stateName + "=$" + num);
						num++;
						bParams.add(o2.sqlStateName());
						break;
					case SiteState.VAR_stateAbbreviation:
						o2.setStateAbbreviation(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SiteState.VAR_stateAbbreviation + "=$" + num);
						num++;
						bParams.add(o2.sqlStateAbbreviation());
						break;
					case SiteState.VAR_imageLeft:
						o2.setImageLeft(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SiteState.VAR_imageLeft + "=$" + num);
						num++;
						bParams.add(o2.sqlImageLeft());
						break;
					case SiteState.VAR_imageTop:
						o2.setImageTop(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SiteState.VAR_imageTop + "=$" + num);
						num++;
						bParams.add(o2.sqlImageTop());
						break;
					case SiteState.VAR_agencyKeys:
						{
							for(Long l : Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray()).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
								futures.add(Future.future(a -> {
									sqlConnection.preparedQuery("UPDATE SiteAgency SET stateKey=$1 WHERE pk=$2")
											.execute(Tuple.of(pk, l)
											, b
									-> {
										if(b.succeeded())
											a.handle(Future.succeededFuture());
										else
											a.handle(Future.failedFuture(new Exception("value SiteState.agencyKeys failed", b.cause())));
									});
								}));
								if(!pks.contains(l)) {
									pks.add(l);
									classes.add("SiteAgency");
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
				LOG.error(String.format("sqlPUTCopySiteState failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("sqlPUTCopySiteState failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<ServiceResponse> response200PUTCopySiteState(SiteRequestEnUS siteRequest) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			JsonObject json = new JsonObject();
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200PUTCopySiteState failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// POST //

	@Override
	public void postSiteState(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("postSiteState started. "));
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setJsonObject(body);
				siteRequest.setRequestUri("/api/state");
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
					eventBus.publish("websocketSiteState", JsonObject.mapFrom(apiRequest).toString());
					postSiteStateFuture(siteRequest, false).onSuccess(siteState -> {
						apiRequest.setPk(siteState.getPk());
						response200POSTSiteState(siteState).onSuccess(response -> {
							eventHandler.handle(Future.succeededFuture(response));
							LOG.debug(String.format("postSiteState succeeded. "));
						}).onFailure(ex -> {
							LOG.error(String.format("postSiteState failed. "), ex);
							error(siteRequest, eventHandler, ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("postSiteState failed. "), ex);
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("postSiteState failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("postSiteState failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("postSiteState failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	@Override
	public void postSiteStateFuture(JsonObject json, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUS(null, serviceRequest, json);
		ApiRequest apiRequest = new ApiRequest();
		apiRequest.setRows(1);
		apiRequest.setNumFound(1L);
		apiRequest.setNumPATCH(0L);
		apiRequest.initDeepApiRequest(siteRequest);
		siteRequest.setApiRequest_(apiRequest);
		postSiteStateFuture(siteRequest, false).onSuccess(a -> {
			semaphore.release();
			eventHandler.handle(Future.succeededFuture());
		}).onFailure(ex -> {
			semaphore.release();
			eventHandler.handle(Future.failedFuture(ex));
		});
	}

	public Future<SiteState> postSiteStateFuture(SiteRequestEnUS siteRequest, Boolean inheritPk) {
		Promise<SiteState> promise = Promise.promise();

		try {
			pgPool.withTransaction(sqlConnection -> {
				Promise<SiteState> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				createSiteState(siteRequest).onSuccess(siteState -> {
					sqlPOSTSiteState(siteState, inheritPk).onSuccess(b -> {
						defineSiteState(siteState).onSuccess(c -> {
							attributeSiteState(siteState).onSuccess(d -> {
								indexSiteState(siteState).onSuccess(e -> {
									promise1.complete(siteState);
								}).onFailure(ex -> {
									LOG.error(String.format("postSiteStateFuture failed. "), ex);
									promise1.fail(ex);
								});
							}).onFailure(ex -> {
								LOG.error(String.format("postSiteStateFuture failed. "), ex);
								promise1.fail(ex);
							});
						}).onFailure(ex -> {
							LOG.error(String.format("postSiteStateFuture failed. "), ex);
							promise1.fail(ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("postSiteStateFuture failed. "), ex);
						promise1.fail(ex);
					});
				}).onFailure(ex -> {
					LOG.error(String.format("postSiteStateFuture failed. "), ex);
					promise1.fail(ex);
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, ex);
			}).compose(siteState -> {
				Promise<SiteState> promise2 = Promise.promise();
				refreshSiteState(siteState).onSuccess(a -> {
					ApiRequest apiRequest = siteRequest.getApiRequest_();
					if(apiRequest != null) {
						apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
						siteState.apiRequestSiteState();
						eventBus.publish("websocketSiteState", JsonObject.mapFrom(apiRequest).toString());
					}
					promise2.complete(siteState);
				}).onFailure(ex -> {
					LOG.error(String.format("postSiteStateFuture failed. "), ex);
					promise2.fail(ex);
				});
				return promise2.future();
			}).onSuccess(siteState -> {
				promise.complete(siteState);
				LOG.debug(String.format("postSiteStateFuture succeeded. "));
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("postSiteStateFuture failed. "), ex);
			promise.fail(ex);
			error(siteRequest, null, ex);
		}
		return promise.future();
	}

	public Future<Void> sqlPOSTSiteState(SiteState o, Boolean inheritPk) {
		Promise<Void> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE SiteState SET ");
			List<Object> bParams = new ArrayList<Object>();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			SiteState o2 = new SiteState();
			o2.setSiteRequest_(siteRequest);
			List<Future> futures1 = new ArrayList<>();
			List<Future> futures2 = new ArrayList<>();

			if(jsonObject != null) {
				Set<String> entityVars = jsonObject.fieldNames();
				for(String entityVar : entityVars) {
					switch(entityVar) {
					case SiteState.VAR_inheritPk:
						o2.setInheritPk(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SiteState.VAR_inheritPk + "=$" + num);
						num++;
						bParams.add(o2.sqlInheritPk());
						break;
					case SiteState.VAR_stateName:
						o2.setStateName(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SiteState.VAR_stateName + "=$" + num);
						num++;
						bParams.add(o2.sqlStateName());
						break;
					case SiteState.VAR_stateAbbreviation:
						o2.setStateAbbreviation(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SiteState.VAR_stateAbbreviation + "=$" + num);
						num++;
						bParams.add(o2.sqlStateAbbreviation());
						break;
					case SiteState.VAR_imageLeft:
						o2.setImageLeft(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SiteState.VAR_imageLeft + "=$" + num);
						num++;
						bParams.add(o2.sqlImageLeft());
						break;
					case SiteState.VAR_imageTop:
						o2.setImageTop(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SiteState.VAR_imageTop + "=$" + num);
						num++;
						bParams.add(o2.sqlImageTop());
						break;
					case SiteState.VAR_agencyKeys:
						Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray()).stream().map(oVal -> oVal.toString()).forEach(val -> {
							futures2.add(Future.future(promise2 -> {
								search(siteRequest).query(SiteAgency.class, val, inheritPk).onSuccess(pk2 -> {
									if(!pks.contains(pk2)) {
										pks.add(pk2);
										classes.add("SiteAgency");
									}
									sql(siteRequest).update(SiteAgency.class, pk2).set(SiteAgency.VAR_stateKey, SiteState.class, pk).onSuccess(a -> {
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
					LOG.error(String.format("sqlPOSTSiteState failed. "), ex);
					promise.fail(ex);
				});
			}).onFailure(ex -> {
				LOG.error(String.format("sqlPOSTSiteState failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("sqlPOSTSiteState failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<ServiceResponse> response200POSTSiteState(SiteState o) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200POSTSiteState failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// PATCH //

	@Override
	public void patchSiteState(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("patchSiteState started. "));
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setJsonObject(body);
				siteRequest.setRequestUri("/api/state");
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
					serviceRequest.getParams().getJsonObject("query").put("rows", 100);
					searchSiteStateList(siteRequest, false, true, true, "/api/state", "PATCH").onSuccess(listSiteState -> {
						try {
							List<String> roles2 = Arrays.asList("SiteAdmin");
							if(listSiteState.getQueryResponse().getResults().getNumFound() > 1
									&& !CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles2)
									&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles2)
									) {
								String message = String.format("roles required: " + String.join(", ", roles2));
								LOG.error(message);
								error(siteRequest, eventHandler, new RuntimeException(message));
							} else {

								ApiRequest apiRequest = new ApiRequest();
								apiRequest.setRows(listSiteState.getRows());
								apiRequest.setNumFound(listSiteState.getQueryResponse().getResults().getNumFound());
								apiRequest.setNumPATCH(0L);
								apiRequest.initDeepApiRequest(siteRequest);
								siteRequest.setApiRequest_(apiRequest);
								if(apiRequest.getNumFound() == 1L)
									apiRequest.setOriginal(listSiteState.first());
								apiRequest.setPk(listSiteState.first().getPk());
								eventBus.publish("websocketSiteState", JsonObject.mapFrom(apiRequest).toString());

								listPATCHSiteState(apiRequest, listSiteState).onSuccess(e -> {
									response200PATCHSiteState(siteRequest).onSuccess(response -> {
										LOG.debug(String.format("patchSiteState succeeded. "));
										eventHandler.handle(Future.succeededFuture(response));
									}).onFailure(ex -> {
										LOG.error(String.format("patchSiteState failed. "), ex);
										error(siteRequest, eventHandler, ex);
									});
								}).onFailure(ex -> {
									LOG.error(String.format("patchSiteState failed. "), ex);
									error(siteRequest, eventHandler, ex);
								});
							}
						} catch(Exception ex) {
							LOG.error(String.format("patchSiteState failed. "), ex);
							error(siteRequest, eventHandler, ex);
						}
					}).onFailure(ex -> {
						LOG.error(String.format("patchSiteState failed. "), ex);
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("patchSiteState failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("patchSiteState failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("patchSiteState failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<Void> listPATCHSiteState(ApiRequest apiRequest, SearchList<SiteState> listSiteState) {
		Promise<Void> promise = Promise.promise();
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listSiteState.getSiteRequest_();
		listSiteState.getList().forEach(o -> {
			futures.add(Future.future(promise1 -> {
				workerExecutor.executeBlocking(blockingCodeHandler -> {
					try {
						semaphore.acquire();
						Long pk = o.getPk();

						JsonObject params = new JsonObject();
						params.put("body", siteRequest.getJsonObject().put(SiteState.VAR_pk, pk.toString()));
						params.put("path", new JsonObject());
						params.put("cookie", new JsonObject());
						params.put("query", new JsonObject().put("q", "*:*").put("fq", new JsonArray().add("pk:" + pk)));
						JsonObject context = new JsonObject().put("params", params);
						JsonObject json = new JsonObject().put("context", context);
						eventBus.send("opendatapolicing-enUS-SiteState", json, new DeliveryOptions().addHeader("action", "patchSiteStateFuture"));
						blockingCodeHandler.complete();
					} catch(Exception ex) {
						LOG.error(String.format("listPATCHSiteState failed. "), ex);
						blockingCodeHandler.fail(ex);
					}
				}).onSuccess(a -> {
					promise1.complete();
				}).onFailure(ex -> {
					LOG.error(String.format("listPATCHSiteState failed. "), ex);
					promise1.fail(ex);
				});
			}));
		});
		CompositeFuture.all(futures).onSuccess( a -> {
			if(apiRequest != null) {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + listSiteState.getQueryResponse().getResults().size());
				if(apiRequest.getNumFound() == 1L)
					listSiteState.first().apiRequestSiteState();
				eventBus.publish("websocketSiteState", JsonObject.mapFrom(apiRequest).toString());
			}
			listSiteState.next().onSuccess(next -> {
				if(next) {
					listPATCHSiteState(apiRequest, listSiteState);
				} else {
					promise.complete();
				}
			}).onFailure(ex -> {
				LOG.error(String.format("listPATCHSiteState failed. "), ex);
				promise.fail(ex);
			});
		}).onFailure(ex -> {
			LOG.error(String.format("listPATCHSiteState failed. "), ex);
			promise.fail(ex);
		});
		return promise.future();
	}

	@Override
	public void patchSiteStateFuture(JsonObject json, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUS(null, serviceRequest, json);
		SiteState o = new SiteState();
		o.setSiteRequest_(siteRequest);
		ApiRequest apiRequest = new ApiRequest();
		apiRequest.setRows(1);
		apiRequest.setNumFound(1L);
		apiRequest.setNumPATCH(0L);
		apiRequest.initDeepApiRequest(siteRequest);
		siteRequest.setApiRequest_(apiRequest);
		o.setPk(json.getString(SiteState.VAR_pk));
		patchSiteStateFuture(o, false).onSuccess(a -> {
			semaphore.release();
			eventHandler.handle(Future.succeededFuture());
		}).onFailure(ex -> {
			semaphore.release();
			eventHandler.handle(Future.failedFuture(ex));
		});
	}

	public Future<SiteState> patchSiteStateFuture(SiteState o, Boolean inheritPk) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		Promise<SiteState> promise = Promise.promise();

		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			pgPool.withTransaction(sqlConnection -> {
				Promise<SiteState> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				sqlPATCHSiteState(o, inheritPk).onSuccess(siteState -> {
					defineSiteState(siteState).onSuccess(c -> {
						attributeSiteState(siteState).onSuccess(d -> {
							indexSiteState(siteState).onSuccess(e -> {
								promise1.complete(siteState);
							}).onFailure(ex -> {
								LOG.error(String.format("patchSiteStateFuture failed. "), ex);
								promise1.fail(ex);
							});
						}).onFailure(ex -> {
							LOG.error(String.format("patchSiteStateFuture failed. "), ex);
							promise1.fail(ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("patchSiteStateFuture failed. "), ex);
						promise1.fail(ex);
					});
				}).onFailure(ex -> {
					LOG.error(String.format("patchSiteStateFuture failed. "), ex);
					promise1.fail(ex);
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, ex);
			}).compose(siteState -> {
				Promise<SiteState> promise2 = Promise.promise();
				refreshSiteState(siteState).onSuccess(a -> {
					promise2.complete(siteState);
				}).onFailure(ex -> {
					LOG.error(String.format("patchSiteStateFuture failed. "), ex);
					promise2.fail(ex);
				});
				return promise2.future();
			}).onSuccess(siteState -> {
				promise.complete(siteState);
				LOG.debug(String.format("patchSiteStateFuture succeeded. "));
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("patchSiteStateFuture failed. "), ex);
			promise.fail(ex);
			error(siteRequest, null, ex);
		}
		return promise.future();
	}

	public Future<SiteState> sqlPATCHSiteState(SiteState o, Boolean inheritPk) {
		Promise<SiteState> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE SiteState SET ");
			List<Object> bParams = new ArrayList<Object>();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			SiteState o2 = new SiteState();
			o2.setSiteRequest_(siteRequest);
			List<Future> futures1 = new ArrayList<>();
			List<Future> futures2 = new ArrayList<>();

			for(String entityVar : methodNames) {
				switch(entityVar) {
					case "setInheritPk":
							o2.setInheritPk(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(SiteState.VAR_inheritPk + "=$" + num);
							num++;
							bParams.add(o2.sqlInheritPk());
						break;
					case "setStateName":
							o2.setStateName(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(SiteState.VAR_stateName + "=$" + num);
							num++;
							bParams.add(o2.sqlStateName());
						break;
					case "setStateAbbreviation":
							o2.setStateAbbreviation(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(SiteState.VAR_stateAbbreviation + "=$" + num);
							num++;
							bParams.add(o2.sqlStateAbbreviation());
						break;
					case "setImageLeft":
							o2.setImageLeft(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(SiteState.VAR_imageLeft + "=$" + num);
							num++;
							bParams.add(o2.sqlImageLeft());
						break;
					case "setImageTop":
							o2.setImageTop(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(SiteState.VAR_imageTop + "=$" + num);
							num++;
							bParams.add(o2.sqlImageTop());
						break;
					case "setAgencyKeys":
						JsonArray setAgencyKeysValues = Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray());
						setAgencyKeysValues.stream().map(oVal -> oVal.toString()).forEach(val -> {
							futures2.add(Future.future(promise2 -> {
								search(siteRequest).query(SiteAgency.class, val, inheritPk).onSuccess(pk2 -> {
									if(!pks.contains(pk2)) {
										pks.add(pk2);
										classes.add("SiteAgency");
									}
									sql(siteRequest).update(SiteAgency.class, pk2).set(SiteAgency.VAR_stateKey, SiteState.class, pk).onSuccess(a -> {
										promise2.complete();
									}).onFailure(ex -> {
										promise2.fail(ex);
									});
								}).onFailure(ex -> {
									promise2.fail(ex);
								});
							}));
						});
						Optional.ofNullable(o.getAgencyKeys()).orElse(Arrays.asList()).stream().filter(oVal -> oVal != null && !setAgencyKeysValues.contains(oVal.toString())).forEach(pk2 -> {
							if(!pks.contains(pk2)) {
								pks.add(pk2);
								classes.add("SiteAgency");
							}
							futures2.add(Future.future(promise2 -> {
								sql(siteRequest).update(SiteAgency.class, pk2).setToNull(SiteAgency.VAR_stateKey, SiteState.class, pk2).onSuccess(a -> {
									promise2.complete();
								}).onFailure(ex -> {
									promise2.fail(ex);
								});
							}));
						});
					case "addAllAgencyKeys":
						JsonArray addAllAgencyKeysValues = Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray());
						addAllAgencyKeysValues.stream().map(oVal -> oVal.toString()).forEach(val -> {
							futures2.add(Future.future(promise2 -> {
								search(siteRequest).query(SiteAgency.class, val, inheritPk).onSuccess(pk2 -> {
									if(!pks.contains(pk2)) {
										pks.add(pk2);
										classes.add("SiteAgency");
									}
									sql(siteRequest).update(SiteAgency.class, pk2).set(SiteAgency.VAR_stateKey, SiteState.class, pk).onSuccess(a -> {
										promise2.complete();
									}).onFailure(ex -> {
										promise2.fail(ex);
									});
								}).onFailure(ex -> {
									promise2.fail(ex);
								});
							}));
						});
					case "addAgencyKeys":
						Optional.ofNullable(jsonObject.getString(entityVar)).ifPresent(val -> {
							futures2.add(Future.future(promise2 -> {
								search(siteRequest).query(SiteAgency.class, val, inheritPk).onSuccess(pk2 -> {
									if(!pks.contains(pk2)) {
										pks.add(pk2);
										classes.add("SiteAgency");
									}
									sql(siteRequest).update(SiteAgency.class, pk2).set(SiteAgency.VAR_stateKey, SiteState.class, pk).onSuccess(a -> {
										promise2.complete();
									}).onFailure(ex -> {
										promise2.fail(ex);
									});
								}).onFailure(ex -> {
									promise2.fail(ex);
								});
							}));
						});
					case "removeAgencyKeys":
						Optional.ofNullable(jsonObject.getLong(entityVar)).ifPresent(pk2 -> {
							if(!pks.contains(pk2)) {
								pks.add(pk2);
								classes.add("SiteAgency");
							}
							futures2.add(Future.future(promise2 -> {
								sql(siteRequest).update(SiteAgency.class, pk2).setToNull(SiteAgency.VAR_stateKey, SiteState.class, pk2).onSuccess(a -> {
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
					SiteState o3 = new SiteState();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
					promise.complete(o3);
				}).onFailure(ex -> {
					LOG.error(String.format("sqlPATCHSiteState failed. "), ex);
					promise.fail(ex);
				});
			}).onFailure(ex -> {
				LOG.error(String.format("sqlPATCHSiteState failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("sqlPATCHSiteState failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<ServiceResponse> response200PATCHSiteState(SiteRequestEnUS siteRequest) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			JsonObject json = new JsonObject();
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200PATCHSiteState failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// GET //

	@Override
	public void getSiteState(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setRequestUri("/api/state/{id}");
				siteRequest.setRequestMethod("GET");
				{
					searchSiteStateList(siteRequest, false, true, false, "/api/state/{id}", "GET").onSuccess(listSiteState -> {
						response200GETSiteState(listSiteState).onSuccess(response -> {
							eventHandler.handle(Future.succeededFuture(response));
							LOG.debug(String.format("getSiteState succeeded. "));
						}).onFailure(ex -> {
							LOG.error(String.format("getSiteState failed. "), ex);
							error(siteRequest, eventHandler, ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("getSiteState failed. "), ex);
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("getSiteState failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("getSiteState failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("getSiteState failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<ServiceResponse> response200GETSiteState(SearchList<SiteState> listSiteState) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = listSiteState.getSiteRequest_();
			SolrDocumentList solrDocuments = listSiteState.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listSiteState.getList().stream().findFirst().orElse(null));
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200GETSiteState failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// Search //

	@Override
	public void searchSiteState(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setRequestUri("/api/state");
				siteRequest.setRequestMethod("Search");
				{
					searchSiteStateList(siteRequest, false, true, false, "/api/state", "Search").onSuccess(listSiteState -> {
						response200SearchSiteState(listSiteState).onSuccess(response -> {
							eventHandler.handle(Future.succeededFuture(response));
							LOG.debug(String.format("searchSiteState succeeded. "));
						}).onFailure(ex -> {
							LOG.error(String.format("searchSiteState failed. "), ex);
							error(siteRequest, eventHandler, ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("searchSiteState failed. "), ex);
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("searchSiteState failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("searchSiteState failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("searchSiteState failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<ServiceResponse> response200SearchSiteState(SearchList<SiteState> listSiteState) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = listSiteState.getSiteRequest_();
			QueryResponse responseSearch = listSiteState.getQueryResponse();
			SolrDocumentList solrDocuments = listSiteState.getSolrDocumentList();
			Long searchInMillis = Long.valueOf(responseSearch.getQTime());
			Long transmissionInMillis = responseSearch.getElapsedTime();
			Long startNum = responseSearch.getResults().getStart();
			Long foundNum = responseSearch.getResults().getNumFound();
			Integer returnedNum = responseSearch.getResults().size();
			String searchTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(searchInMillis), TimeUnit.MILLISECONDS.toMillis(searchInMillis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(searchInMillis)));
			String transmissionTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis), TimeUnit.MILLISECONDS.toMillis(transmissionInMillis) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis)));
			Exception exceptionSearch = responseSearch.getException();
			List<String> fls = listSiteState.getFields();

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
				json.put("searchTime", searchTime);
				json.put("transmissionTime", transmissionTime);
			}
			JsonArray l = new JsonArray();
			listSiteState.getList().stream().forEach(o -> {
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
					JsonObject rangeFacetCountsMap = new JsonObject();
					rangeFacetJson.put("counts", rangeFacetCountsMap);
					List<?> rangeFacetCounts = rangeFacet.getCounts();
					for(Integer i = 0; i < rangeFacetCounts.size(); i+= 1) {
						RangeFacet.Count count = (RangeFacet.Count)rangeFacetCounts.get(i);
						rangeFacetCountsMap.put(count.getValue(), count.getCount());
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
					responsePivotSearchSiteState(pivotFields, pivotArray);
				}
			}
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200SearchSiteState failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}
	public void responsePivotSearchSiteState(List<PivotField> pivotFields, JsonArray pivotArray) {
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
					JsonObject rangeFacetCountsObject = new JsonObject();
					rangeFacetJson.put("counts", rangeFacetCountsObject);
					List<?> rangeFacetCounts = rangeFacet.getCounts();
					for(Integer i = 0; i < rangeFacetCounts.size(); i+= 1) {
						RangeFacet.Count count = (RangeFacet.Count)rangeFacetCounts.get(i);
						rangeFacetCountsObject.put(count.getValue(), count.getCount());
					}
				}
			}
			if(pivotFields2 != null) {
				JsonArray pivotArray2 = new JsonArray();
				pivotJson.put("pivot", pivotArray2);
				responsePivotSearchSiteState(pivotFields2, pivotArray2);
			}
		}
	}

	// AdminSearch //

	@Override
	public void adminsearchSiteState(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setRequestUri("/api/admin/state");
				siteRequest.setRequestMethod("AdminSearch");
				{
					searchSiteStateList(siteRequest, false, true, false, "/api/admin/state", "AdminSearch").onSuccess(listSiteState -> {
						response200AdminSearchSiteState(listSiteState).onSuccess(response -> {
							eventHandler.handle(Future.succeededFuture(response));
							LOG.debug(String.format("adminsearchSiteState succeeded. "));
						}).onFailure(ex -> {
							LOG.error(String.format("adminsearchSiteState failed. "), ex);
							error(siteRequest, eventHandler, ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("adminsearchSiteState failed. "), ex);
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("adminsearchSiteState failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("adminsearchSiteState failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("adminsearchSiteState failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<ServiceResponse> response200AdminSearchSiteState(SearchList<SiteState> listSiteState) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = listSiteState.getSiteRequest_();
			QueryResponse responseSearch = listSiteState.getQueryResponse();
			SolrDocumentList solrDocuments = listSiteState.getSolrDocumentList();
			Long searchInMillis = Long.valueOf(responseSearch.getQTime());
			Long transmissionInMillis = responseSearch.getElapsedTime();
			Long startNum = responseSearch.getResults().getStart();
			Long foundNum = responseSearch.getResults().getNumFound();
			Integer returnedNum = responseSearch.getResults().size();
			String searchTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(searchInMillis), TimeUnit.MILLISECONDS.toMillis(searchInMillis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(searchInMillis)));
			String transmissionTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis), TimeUnit.MILLISECONDS.toMillis(transmissionInMillis) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis)));
			Exception exceptionSearch = responseSearch.getException();
			List<String> fls = listSiteState.getFields();

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
				json.put("searchTime", searchTime);
				json.put("transmissionTime", transmissionTime);
			}
			JsonArray l = new JsonArray();
			listSiteState.getList().stream().forEach(o -> {
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
					JsonObject rangeFacetCountsMap = new JsonObject();
					rangeFacetJson.put("counts", rangeFacetCountsMap);
					List<?> rangeFacetCounts = rangeFacet.getCounts();
					for(Integer i = 0; i < rangeFacetCounts.size(); i+= 1) {
						RangeFacet.Count count = (RangeFacet.Count)rangeFacetCounts.get(i);
						rangeFacetCountsMap.put(count.getValue(), count.getCount());
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
					responsePivotAdminSearchSiteState(pivotFields, pivotArray);
				}
			}
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200AdminSearchSiteState failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}
	public void responsePivotAdminSearchSiteState(List<PivotField> pivotFields, JsonArray pivotArray) {
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
					JsonObject rangeFacetCountsObject = new JsonObject();
					rangeFacetJson.put("counts", rangeFacetCountsObject);
					List<?> rangeFacetCounts = rangeFacet.getCounts();
					for(Integer i = 0; i < rangeFacetCounts.size(); i+= 1) {
						RangeFacet.Count count = (RangeFacet.Count)rangeFacetCounts.get(i);
						rangeFacetCountsObject.put(count.getValue(), count.getCount());
					}
				}
			}
			if(pivotFields2 != null) {
				JsonArray pivotArray2 = new JsonArray();
				pivotJson.put("pivot", pivotArray2);
				responsePivotAdminSearchSiteState(pivotFields2, pivotArray2);
			}
		}
	}
	public static final String VAR_stateKey = "stateKey";
	public static final String VAR_stateName = "stateName";
	public static final String VAR_stateAbbreviation = "stateAbbreviation";
	public static final String VAR_imageLeft = "imageLeft";
	public static final String VAR_imageTop = "imageTop";
	public static final String VAR_agencyKeys = "agencyKeys";
	public static final String VAR_stateCompleteName = "stateCompleteName";

	// General //

	public Future<SiteState> createSiteState(SiteRequestEnUS siteRequest) {
		Promise<SiteState> promise = Promise.promise();
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			String userId = siteRequest.getUserId();
			Long userKey = siteRequest.getUserKey();
			ZonedDateTime created = Optional.ofNullable(siteRequest.getJsonObject()).map(j -> j.getString("created")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(config.getString("siteZone"))))).orElse(ZonedDateTime.now(ZoneId.of(config.getString("siteZone"))));

			sqlConnection.preparedQuery("INSERT INTO SiteState(created) VALUES($1) RETURNING pk")
					.collecting(Collectors.toList())
					.execute(Tuple.of(created.toOffsetDateTime())).onSuccess(result -> {
				Row createLine = result.value().stream().findFirst().orElseGet(() -> null);
				Long pk = createLine.getLong(0);
				SiteState o = new SiteState();
				o.setPk(pk);
				o.setSiteRequest_(siteRequest);
				promise.complete(o);
			}).onFailure(ex -> {
				LOG.error("createSiteState failed. ", ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("createSiteState failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public void searchSiteStateQ(String uri, String apiMethod, SearchList<SiteState> searchList, String entityVar, String valueIndexed, String varIndexed) {
		searchList.setQuery(varIndexed + ":" + ("*".equals(valueIndexed) ? valueIndexed : ClientUtils.escapeQueryChars(valueIndexed)));
		if(!"*".equals(entityVar)) {
		}
	}

	public String searchSiteStateFq(String uri, String apiMethod, SearchList<SiteState> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		if(StringUtils.startsWith(valueIndexed, "[")) {
			String[] fqs = StringUtils.substringBefore(StringUtils.substringAfter(valueIndexed, "["), "]").split(" TO ");
			if(fqs.length != 2)
				throw new RuntimeException(String.format("\"%s\" invalid range query. ", valueIndexed));
			String fq1 = fqs[0].equals("*") ? fqs[0] : SiteState.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[0]);
			String fq2 = fqs[1].equals("*") ? fqs[1] : SiteState.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[1]);
			 return varIndexed + ":[" + fq1 + " TO " + fq2 + "]";
		} else {
			return varIndexed + ":" + SiteState.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), valueIndexed);
		}
	}

	public void searchSiteStateSort(String uri, String apiMethod, SearchList<SiteState> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addSort(varIndexed, ORDER.valueOf(valueIndexed));
	}

	public void searchSiteStateRows(String uri, String apiMethod, SearchList<SiteState> searchList, Integer valueRows) {
			searchList.setRows(apiMethod != null ? valueRows : 10);
	}

	public void searchSiteStateStart(String uri, String apiMethod, SearchList<SiteState> searchList, Integer valueStart) {
		searchList.setStart(valueStart);
	}

	public void searchSiteStateVar(String uri, String apiMethod, SearchList<SiteState> searchList, String var, String value) {
		searchList.getSiteRequest_().getRequestVars().put(var, value);
	}

	public void searchSiteStateUri(String uri, String apiMethod, SearchList<SiteState> searchList) {
	}

	public Future<ServiceResponse> varsSiteState(SiteRequestEnUS siteRequest) {
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
					LOG.error(String.format("searchSiteState failed. "), ex);
					promise.fail(ex);
				}
			});
			promise.complete();
		} catch(Exception ex) {
			LOG.error(String.format("searchSiteState failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<SearchList<SiteState>> searchSiteStateList(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod) {
		Promise<SearchList<SiteState>> promise = Promise.promise();
		try {
			ServiceRequest serviceRequest = siteRequest.getServiceRequest();
			String entityListStr = siteRequest.getServiceRequest().getParams().getJsonObject("query").getString("fl");
			String[] entityList = entityListStr == null ? null : entityListStr.split(",\\s*");
			SearchList<SiteState> searchList = new SearchList<SiteState>();
			searchList.setPopulate(populate);
			searchList.setStore(store);
			searchList.setQuery("*:*");
			searchList.setC(SiteState.class);
			searchList.setSiteRequest_(siteRequest);
			if(entityList != null)
				searchList.addFields(entityList);

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
								varsIndexed[i] = SiteState.varIndexedSiteState(entityVar);
							}
							searchList.add("facet.pivot", (solrLocalParams == null ? "" : solrLocalParams) + StringUtils.join(varsIndexed, ","));
						}
					} else if(paramValuesObject != null) {
						for(Object paramObject : paramObjects) {
							switch(paramName) {
								case "q":
									entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
									varIndexed = "*".equals(entityVar) ? entityVar : SiteState.varSearchSiteState(entityVar);
									valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
									valueIndexed = StringUtils.isEmpty(valueIndexed) ? "*" : valueIndexed;
									searchSiteStateQ(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
									break;
								case "fq":
									Matcher mFq = Pattern.compile("(\\w+):(.+?(?=(\\)|\\s+OR\\s+|\\s+AND\\s+|$)))").matcher((String)paramObject);
									boolean foundFq = mFq.find();
									if(foundFq) {
										StringBuffer sb = new StringBuffer();
										while(foundFq) {
											entityVar = mFq.group(1).trim();
											valueIndexed = mFq.group(2).trim();
											varIndexed = SiteState.varIndexedSiteState(entityVar);
											String entityFq = searchSiteStateFq(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
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
									varIndexed = SiteState.varIndexedSiteState(entityVar);
									searchSiteStateSort(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
									break;
								case "start":
									valueStart = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
									searchSiteStateStart(uri, apiMethod, searchList, valueStart);
									break;
								case "rows":
									valueRows = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
									searchSiteStateRows(uri, apiMethod, searchList, valueRows);
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
										varIndexed = SiteState.varIndexedSiteState(entityVar);
										searchList.add("facet.range", (solrLocalParams == null ? "" : solrLocalParams) + varIndexed);
									}
									break;
								case "facet.field":
									entityVar = (String)paramObject;
									varIndexed = SiteState.varIndexedSiteState(entityVar);
									if(varIndexed != null)
										searchList.addFacetField(varIndexed);
									break;
								case "var":
									entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
									valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
									searchSiteStateVar(uri, apiMethod, searchList, entityVar, valueIndexed);
									break;
							}
						}
						searchSiteStateUri(uri, apiMethod, searchList);
					}
				} catch(Exception e) {
					ExceptionUtils.rethrow(e);
				}
			});
			if("*:*".equals(searchList.getQuery()) && searchList.getSorts().size() == 0) {
				searchList.addSort("stateName_indexed_string", ORDER.asc);
			}
			searchSiteState2(siteRequest, populate, store, modify, uri, apiMethod, searchList);
			searchList.promiseDeepForClass(siteRequest).onSuccess(a -> {
				promise.complete(searchList);
			}).onFailure(ex -> {
				LOG.error(String.format("searchSiteState failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("searchSiteState failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}
	public void searchSiteState2(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, SearchList<SiteState> searchList) {
	}

	public Future<Void> defineSiteState(SiteState o) {
		Promise<Void> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT * FROM SiteState WHERE pk=$1")
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
									LOG.error(String.format("defineSiteState failed. "), e);
								}
							}
						}
					}
					promise.complete();
				} catch(Exception ex) {
					LOG.error(String.format("defineSiteState failed. "), ex);
					promise.fail(ex);
				}
			}).onFailure(ex -> {
				LOG.error(String.format("defineSiteState failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("defineSiteState failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<Void> attributeSiteState(SiteState o) {
		Promise<Void> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT pk as pk2, 'agencyKeys' from SiteAgency where stateKey=$1")
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
					LOG.error(String.format("attributeSiteState failed. "), ex);
					promise.fail(ex);
				}
			}).onFailure(ex -> {
				LOG.error(String.format("attributeSiteState failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("attributeSiteState failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<Void> indexSiteState(SiteState o) {
		Promise<Void> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			o.promiseDeepForClass(siteRequest).onSuccess(a -> {
				SolrInputDocument document = new SolrInputDocument();
				o.indexSiteState(document);
				String solrHostName = siteRequest.getConfig().getString(ConfigKeys.SOLR_HOST_NAME);
				Integer solrPort = siteRequest.getConfig().getInteger(ConfigKeys.SOLR_PORT);
				String solrCollection = siteRequest.getConfig().getString(ConfigKeys.SOLR_COLLECTION);
				String solrRequestUri = String.format("/solr/%s/update%s", solrCollection, "?commitWithin=10000&overwrite=true&wt=json");
				JsonArray json = new JsonArray().add(new JsonObject(document.toMap(new HashMap<String, Object>())));
				webClient.post(solrPort, solrHostName, solrRequestUri).putHeader("Content-Type", "application/json").expect(ResponsePredicate.SC_OK).sendBuffer(json.toBuffer()).onSuccess(b -> {
					promise.complete();
				}).onFailure(ex -> {
					LOG.error(String.format("indexSiteState failed. "), new RuntimeException(ex));
					promise.fail(ex);
				});
			}).onFailure(ex -> {
				LOG.error(String.format("indexSiteState failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("indexSiteState failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<Void> refreshSiteState(SiteState o) {
		Promise<Void> promise = Promise.promise();
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Boolean refresh = !"false".equals(siteRequest.getRequestVars().get("refresh"));
			if(refresh && !Optional.ofNullable(siteRequest.getJsonObject()).map(JsonObject::isEmpty).orElse(true)) {
				List<Future> futures = new ArrayList<>();

				for(int i=0; i < pks.size(); i++) {
					Long pk2 = pks.get(i);
					String classSimpleName2 = classes.get(i);

					if("SiteAgency".equals(classSimpleName2) && pk2 != null) {
						SearchList<SiteAgency> searchList2 = new SearchList<SiteAgency>();
						searchList2.setStore(true);
						searchList2.setQuery("*:*");
						searchList2.setC(SiteAgency.class);
						searchList2.addFilterQuery("pk_indexed_long:" + pk2);
						searchList2.setRows(1);
						futures.add(Future.future(promise2 -> {
							searchList2.promiseDeepSearchList(siteRequest).onSuccess(b -> {
								SiteAgency o2 = searchList2.getList().stream().findFirst().orElse(null);
								if(o2 != null) {
									JsonObject params = new JsonObject();
									params.put("body", new JsonObject());
									params.put("cookie", new JsonObject());
									params.put("path", new JsonObject());
									params.put("query", new JsonObject().put("q", "*:*").put("fq", new JsonArray().add("pk:" + pk2)));
									JsonObject context = new JsonObject().put("params", params).put("user", siteRequest.getJsonPrincipal());
									JsonObject json = new JsonObject().put("context", context);
									eventBus.request("opendatapolicing-enUS-SiteAgency", json, new DeliveryOptions().addHeader("action", "patchSiteAgency")).onSuccess(c -> {
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
					JsonObject params = new JsonObject();
					params.put("body", new JsonObject());
					params.put("cookie", new JsonObject());
					params.put("path", new JsonObject());
					params.put("query", new JsonObject().put("q", "*:*").put("fq", new JsonArray().add("pk:" + o.getPk())));
					JsonObject context = new JsonObject().put("params", params).put("user", siteRequest.getJsonPrincipal());
					JsonObject json = new JsonObject().put("context", context);
					eventBus.request("opendatapolicing-enUS-SiteState", json, new DeliveryOptions().addHeader("action", "patchSiteState")).onSuccess(c -> {
						promise.complete();
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
			LOG.error(String.format("refreshSiteState failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}
}
