package com.opendatapolicing.enus.trafficsearch;

import com.opendatapolicing.enus.trafficperson.TrafficPersonEnUSApiServiceImpl;
import com.opendatapolicing.enus.trafficperson.TrafficPerson;
import com.opendatapolicing.enus.trafficcontraband.TrafficContrabandEnUSApiServiceImpl;
import com.opendatapolicing.enus.trafficcontraband.TrafficContraband;
import com.opendatapolicing.enus.searchbasis.SearchBasisEnUSApiServiceImpl;
import com.opendatapolicing.enus.searchbasis.SearchBasis;
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
public class TrafficSearchEnUSGenApiServiceImpl extends BaseApiServiceImpl implements TrafficSearchEnUSGenApiService {

	protected static final Logger LOG = LoggerFactory.getLogger(TrafficSearchEnUSGenApiServiceImpl.class);

	public TrafficSearchEnUSGenApiServiceImpl(EventBus eventBus, JsonObject config, WorkerExecutor workerExecutor, PgPool pgPool, WebClient webClient, OAuth2Auth oauth2AuthenticationProvider, AuthorizationProvider authorizationProvider) {
		super(eventBus, config, workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider);
	}

	// PUTImport //

	@Override
	public void putimportTrafficSearch(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("putimportTrafficSearch started. "));
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/traffic-search/import");
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
						response200PUTImportTrafficSearch(siteRequest, c -> {
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
											eventBus.publish("websocketTrafficSearch", JsonObject.mapFrom(apiRequest).toString());
											varsTrafficSearch(siteRequest, d -> {
												if(d.succeeded()) {
													listPUTImportTrafficSearch(apiRequest, siteRequest).onSuccess(e -> {
														response200PUTImportTrafficSearch(siteRequest, f -> {
															if(f.succeeded()) {
																LOG.debug(String.format("putimportTrafficSearch succeeded. "));
																blockingCodeHandler.handle(Future.succeededFuture(f.result()));
															} else {
																LOG.error(String.format("putimportTrafficSearch failed. ", f.cause()));
																error(siteRequest, null, f);
															}
														});
													}).onFailure(ex -> {
														LOG.error(String.format("putimportTrafficSearch failed. ", ex));
														error(siteRequest, null, Future.failedFuture(ex));
													});
												} else {
													LOG.error(String.format("putimportTrafficSearch failed. ", d.cause()));
													error(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putimportTrafficSearch failed. ", ex));
											error(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putimportTrafficSearch failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putimportTrafficSearch failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("putimportTrafficSearch failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("putimportTrafficSearch failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public Future<Void> listPUTImportTrafficSearch(ApiRequest apiRequest, SiteRequestEnUS siteRequest) {
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

				SearchList<TrafficSearch> searchList = new SearchList<TrafficSearch>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(TrafficSearch.class);
				searchList.addFilterQuery("inheritPk_indexed_string:" + ClientUtils.escapeQueryChars(json.getString("pk")));
				searchList.initDeepForClass(siteRequest2);

				if(searchList.size() == 1) {
					TrafficSearch o = searchList.getList().stream().findFirst().orElse(null);
					TrafficSearch o2 = new TrafficSearch();
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
						}
						else {
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
						futures.add(
							patchTrafficSearchFuture(o, true).onFailure(ex -> {
								LOG.error(String.format("listPUTImportTrafficSearch failed. ", ex));
							})
						);
					}
				} else {
					futures.add(
						postTrafficSearchFuture(siteRequest2, true).onFailure(ex -> {
							LOG.error(String.format("listPUTImportTrafficSearch failed. ", ex));
						})
					);
				}
			});
			CompositeFuture.all(futures).onSuccess(a -> {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
				promise.complete();
			}).onFailure(ex -> {
				LOG.error(String.format("listPUTImportTrafficSearch failed. ", ex));
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("listPUTImportTrafficSearch failed. ", ex));
			promise.fail(ex);
		}
		return promise.future();
	}

	public void response200PUTImportTrafficSearch(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTImportTrafficSearch failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTMerge //

	@Override
	public void putmergeTrafficSearch(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("putmergeTrafficSearch started. "));
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/traffic-search/merge");
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
						response200PUTMergeTrafficSearch(siteRequest, c -> {
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
											eventBus.publish("websocketTrafficSearch", JsonObject.mapFrom(apiRequest).toString());
											varsTrafficSearch(siteRequest, d -> {
												if(d.succeeded()) {
													listPUTMergeTrafficSearch(apiRequest, siteRequest).onSuccess(e -> {
														response200PUTMergeTrafficSearch(siteRequest, f -> {
															if(f.succeeded()) {
																LOG.debug(String.format("putmergeTrafficSearch succeeded. "));
																blockingCodeHandler.handle(Future.succeededFuture(f.result()));
															} else {
																LOG.error(String.format("putmergeTrafficSearch failed. ", f.cause()));
																error(siteRequest, null, f);
															}
														});
													}).onFailure(ex -> {
														LOG.error(String.format("putmergeTrafficSearch failed. ", ex));
														error(siteRequest, null, Future.failedFuture(ex));
													});
												} else {
													LOG.error(String.format("putmergeTrafficSearch failed. ", d.cause()));
													error(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putmergeTrafficSearch failed. ", ex));
											error(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putmergeTrafficSearch failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putmergeTrafficSearch failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("putmergeTrafficSearch failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("putmergeTrafficSearch failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public Future<Void> listPUTMergeTrafficSearch(ApiRequest apiRequest, SiteRequestEnUS siteRequest) {
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

				SearchList<TrafficSearch> searchList = new SearchList<TrafficSearch>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(TrafficSearch.class);
				searchList.addFilterQuery("pk_indexed_long:" + ClientUtils.escapeQueryChars(json.getString("pk")));
				searchList.initDeepForClass(siteRequest2);

				if(searchList.size() == 1) {
					TrafficSearch o = searchList.getList().stream().findFirst().orElse(null);
					TrafficSearch o2 = new TrafficSearch();
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
						}
						else {
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
						futures.add(
							patchTrafficSearchFuture(o, false).onFailure(ex -> {
								LOG.error(String.format("listPUTMergeTrafficSearch failed. ", ex));
							})
						);
					}
				} else {
					futures.add(
						postTrafficSearchFuture(siteRequest2, false).onFailure(ex -> {
							LOG.error(String.format("listPUTMergeTrafficSearch failed. ", ex));
						})
					);
				}
			});
			CompositeFuture.all(futures).onSuccess(a -> {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
				promise.complete();
			}).onFailure(ex -> {
				LOG.error(String.format("listPUTMergeTrafficSearch failed. ", ex));
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("listPUTMergeTrafficSearch failed. ", ex));
			promise.fail(ex);
		}
		return promise.future();
	}

	public void putmergeTrafficSearchResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PUTMergeTrafficSearch(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("putmergeTrafficSearchResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("putmergeTrafficSearchResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTMergeTrafficSearch(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTMergeTrafficSearch failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTCopy //

	@Override
	public void putcopyTrafficSearch(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("putcopyTrafficSearch started. "));
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/traffic-search/copy");
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
						putcopyTrafficSearchResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											aSearchTrafficSearch(siteRequest, false, true, true, "/api/traffic-search/copy", "PUTCopy", d -> {
												if(d.succeeded()) {
													SearchList<TrafficSearch> listTrafficSearch = d.result();
													ApiRequest apiRequest = new ApiRequest();
													apiRequest.setRows(listTrafficSearch.getRows());
													apiRequest.setNumFound(listTrafficSearch.getQueryResponse().getResults().getNumFound());
													apiRequest.setNumPATCH(0L);
													apiRequest.initDeepApiRequest(siteRequest);
													siteRequest.setApiRequest_(apiRequest);
													eventBus.publish("websocketTrafficSearch", JsonObject.mapFrom(apiRequest).toString());
													try {
														listPUTCopyTrafficSearch(apiRequest, listTrafficSearch, e -> {
															if(e.succeeded()) {
																putcopyTrafficSearchResponse(siteRequest, f -> {
																	if(f.succeeded()) {
																		LOG.debug(String.format("putcopyTrafficSearch succeeded. "));
																		blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																	} else {
																		LOG.error(String.format("putcopyTrafficSearch failed. ", f.cause()));
																		error(siteRequest, null, f);
																	}
																});
															} else {
																LOG.error(String.format("putcopyTrafficSearch failed. ", e.cause()));
																error(siteRequest, null, e);
															}
														});
													} catch(Exception ex) {
														LOG.error(String.format("putcopyTrafficSearch failed. ", ex));
														error(siteRequest, null, Future.failedFuture(ex));
													}
												} else {
													LOG.error(String.format("putcopyTrafficSearch failed. ", d.cause()));
													error(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putcopyTrafficSearch failed. ", ex));
											error(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putcopyTrafficSearch failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putcopyTrafficSearch failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("putcopyTrafficSearch failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("putcopyTrafficSearch failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public void listPUTCopyTrafficSearch(ApiRequest apiRequest, SearchList<TrafficSearch> listTrafficSearch, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listTrafficSearch.getSiteRequest_();
		listTrafficSearch.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = siteRequest.copy();
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				putcopyTrafficSearchFuture(siteRequest2, JsonObject.mapFrom(o)).onFailure(ex -> {
					LOG.error(String.format("listPUTCopyTrafficSearch failed. ", ex));
					error(siteRequest, eventHandler, Future.failedFuture(ex));
				})
			);
		});
		CompositeFuture.all(futures).onComplete( a -> {
			if(a.succeeded()) {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + listTrafficSearch.size());
				if(listTrafficSearch.next()) {
					listPUTCopyTrafficSearch(apiRequest, listTrafficSearch, eventHandler);
				} else {
					response200PUTCopyTrafficSearch(siteRequest, eventHandler);
				}
			} else {
				LOG.error(String.format("listPUTCopyTrafficSearch failed. ", a.cause()));
				error(listTrafficSearch.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<TrafficSearch> putcopyTrafficSearchFuture(SiteRequestEnUS siteRequest, JsonObject jsonObject) {
		Promise<TrafficSearch> promise = Promise.promise();

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
				Promise<TrafficSearch> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				createTrafficSearch(siteRequest, a -> {
					if(a.succeeded()) {
						TrafficSearch trafficSearch = a.result();
						sqlPUTCopyTrafficSearch(trafficSearch, jsonObject, b -> {
							if(b.succeeded()) {
								defineTrafficSearch(trafficSearch, c -> {
									if(c.succeeded()) {
										attributeTrafficSearch(trafficSearch, d -> {
											if(d.succeeded()) {
												indexTrafficSearch(trafficSearch).onSuccess(e -> {
													promise1.complete(trafficSearch);
												}).onFailure(ex -> {
													LOG.error(String.format("putcopyTrafficSearchFuture failed. ", ex));
													promise1.fail(ex);
												});
											} else {
												LOG.error(String.format("putcopyTrafficSearchFuture failed. ", d.cause()));
												promise1.fail(d.cause());
											}
										});
									} else {
										LOG.error(String.format("putcopyTrafficSearchFuture failed. ", c.cause()));
										promise1.fail(c.cause());
									}
								});
							} else {
								LOG.error(String.format("putcopyTrafficSearchFuture failed. ", b.cause()));
								promise1.fail(b.cause());
							}
						});
					} else {
						LOG.error(String.format("putcopyTrafficSearchFuture failed. ", a.cause()));
						promise1.fail(a.cause());
					}
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, Future.failedFuture(ex));
			}).compose(trafficSearch -> {
				Promise<TrafficSearch> promise2 = Promise.promise();
				refreshTrafficSearch(trafficSearch, a -> {
					if(a.succeeded()) {
						ApiRequest apiRequest = siteRequest.getApiRequest_();
						if(apiRequest != null) {
							apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
							trafficSearch.apiRequestTrafficSearch();
							eventBus.publish("websocketTrafficSearch", JsonObject.mapFrom(apiRequest).toString());
						}
						promise2.complete(trafficSearch);
					} else {
						LOG.error(String.format("putcopyTrafficSearchFuture failed. ", a.cause()));
						promise2.fail(a.cause());
					}
				});
				return promise2.future();
			}).onSuccess(trafficSearch -> {
				promise.complete(trafficSearch);
				LOG.debug(String.format("putcopyTrafficSearchFuture succeeded. "));
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, promise.future());
			});
		} catch(Exception ex) {
			LOG.error(String.format("putcopyTrafficSearchFuture failed. "), ex);
			promise.fail(ex);
			error(siteRequest, null, promise.future());
		}
		return promise.future();
	}

	public void sqlPUTCopyTrafficSearch(TrafficSearch o, JsonObject jsonObject, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE TrafficSearch SET ");
			List<Object> bParams = new ArrayList<Object>();
			TrafficSearch o2 = new TrafficSearch();
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
					case "personKey":
						{
							Long l = Long.parseLong(jsonObject.getString(entityVar));
							if(l != null) {
								if(bParams.size() > 0) {
									bSql.append(", ");
								}
								bSql.append("personKey=$" + num);
								num++;
								bParams.add(l);
							}
						}
						break;
					case "contrabandKeys":
						{
							for(Long l : Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray()).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
								futures.add(Future.future(a -> {
									sqlConnection.preparedQuery("UPDATE TrafficContraband SET searchKey=$1 WHERE pk=$2")
											.execute(Tuple.of(pk, l)
											, b
									-> {
										if(b.succeeded())
											a.handle(Future.succeededFuture());
										else
											a.handle(Future.failedFuture(new Exception("value TrafficSearch.contrabandKeys failed", b.cause())));
									});
								}));
								if(!pks.contains(l)) {
									pks.add(l);
									classes.add("TrafficContraband");
								}
							}
						}
						break;
					case "searchBasisKeys":
						{
							for(Long l : Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray()).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
								futures.add(Future.future(a -> {
									sqlConnection.preparedQuery("UPDATE SearchBasis SET searchKey=$1 WHERE pk=$2")
											.execute(Tuple.of(pk, l)
											, b
									-> {
										if(b.succeeded())
											a.handle(Future.succeededFuture());
										else
											a.handle(Future.failedFuture(new Exception("value TrafficSearch.searchBasisKeys failed", b.cause())));
									});
								}));
								if(!pks.contains(l)) {
									pks.add(l);
									classes.add("SearchBasis");
								}
							}
						}
						break;
					case "searchTypeNum":
						o2.setSearchTypeNum(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("searchTypeNum=$" + num);
						num++;
						bParams.add(o2.sqlSearchTypeNum());
						break;
					case "searchVehicle":
						o2.setSearchVehicle(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("searchVehicle=$" + num);
						num++;
						bParams.add(o2.sqlSearchVehicle());
						break;
					case "searchDriver":
						o2.setSearchDriver(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("searchDriver=$" + num);
						num++;
						bParams.add(o2.sqlSearchDriver());
						break;
					case "searchPassenger":
						o2.setSearchPassenger(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("searchPassenger=$" + num);
						num++;
						bParams.add(o2.sqlSearchPassenger());
						break;
					case "searchProperty":
						o2.setSearchProperty(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("searchProperty=$" + num);
						num++;
						bParams.add(o2.sqlSearchProperty());
						break;
					case "searchVehicleSiezed":
						o2.setSearchVehicleSiezed(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("searchVehicleSiezed=$" + num);
						num++;
						bParams.add(o2.sqlSearchVehicleSiezed());
						break;
					case "searchPersonalPropertySiezed":
						o2.setSearchPersonalPropertySiezed(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("searchPersonalPropertySiezed=$" + num);
						num++;
						bParams.add(o2.sqlSearchPersonalPropertySiezed());
						break;
					case "searchOtherPropertySiezed":
						o2.setSearchOtherPropertySiezed(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("searchOtherPropertySiezed=$" + num);
						num++;
						bParams.add(o2.sqlSearchOtherPropertySiezed());
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
					LOG.error(String.format("sqlPUTCopyTrafficSearch failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPUTCopyTrafficSearch failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void putcopyTrafficSearchResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PUTCopyTrafficSearch(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("putcopyTrafficSearchResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("putcopyTrafficSearchResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTCopyTrafficSearch(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTCopyTrafficSearch failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// POST //

	@Override
	public void postTrafficSearch(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("postTrafficSearch started. "));
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/traffic-search");
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
						eventBus.publish("websocketTrafficSearch", JsonObject.mapFrom(apiRequest).toString());
						postTrafficSearchFuture(siteRequest, false).onSuccess(trafficSearch -> {
							apiRequest.setPk(trafficSearch.getPk());
							postTrafficSearchResponse(trafficSearch, d -> {
								if(d.succeeded()) {
									eventHandler.handle(Future.succeededFuture(d.result()));
									LOG.debug(String.format("postTrafficSearch succeeded. "));
								} else {
									LOG.error(String.format("postTrafficSearch failed. ", d.cause()));
									error(siteRequest, eventHandler, d);
								}
							});
						}).onFailure(ex -> {
							LOG.error(String.format("postTrafficSearch failed. ", Future.failedFuture(ex)));
							error(siteRequest, eventHandler, Future.failedFuture(ex));
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("postTrafficSearch failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("postTrafficSearch failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("postTrafficSearch failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public Future<TrafficSearch> postTrafficSearchFuture(SiteRequestEnUS siteRequest, Boolean inheritPk) {
		Promise<TrafficSearch> promise = Promise.promise();

		try {
			pgPool.withTransaction(sqlConnection -> {
				Promise<TrafficSearch> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				createTrafficSearch(siteRequest, a -> {
					if(a.succeeded()) {
						TrafficSearch trafficSearch = a.result();
						sqlPOSTTrafficSearch(trafficSearch, inheritPk, b -> {
							if(b.succeeded()) {
								defineTrafficSearch(trafficSearch, c -> {
									if(c.succeeded()) {
										attributeTrafficSearch(trafficSearch, d -> {
											if(d.succeeded()) {
												indexTrafficSearch(trafficSearch).onSuccess(e -> {
													promise1.complete(trafficSearch);
												}).onFailure(ex -> {
													LOG.error(String.format("postTrafficSearchFuture failed. ", ex));
													promise1.fail(ex);
												});
											} else {
												LOG.error(String.format("postTrafficSearchFuture failed. ", d.cause()));
												promise1.fail(d.cause());
											}
										});
									} else {
										LOG.error(String.format("postTrafficSearchFuture failed. ", c.cause()));
										promise1.fail(c.cause());
									}
								});
							} else {
								LOG.error(String.format("postTrafficSearchFuture failed. ", b.cause()));
								promise1.fail(b.cause());
							}
						});
					} else {
						LOG.error(String.format("postTrafficSearchFuture failed. ", a.cause()));
						promise1.fail(a.cause());
					}
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, Future.failedFuture(ex));
			}).compose(trafficSearch -> {
				Promise<TrafficSearch> promise2 = Promise.promise();
				refreshTrafficSearch(trafficSearch, a -> {
					if(a.succeeded()) {
						ApiRequest apiRequest = siteRequest.getApiRequest_();
						if(apiRequest != null) {
							apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
							trafficSearch.apiRequestTrafficSearch();
							eventBus.publish("websocketTrafficSearch", JsonObject.mapFrom(apiRequest).toString());
						}
						promise2.complete(trafficSearch);
					} else {
						LOG.error(String.format("postTrafficSearchFuture failed. ", a.cause()));
						promise2.fail(a.cause());
					}
				});
				return promise2.future();
			}).onSuccess(trafficSearch -> {
				promise.complete(trafficSearch);
				LOG.debug(String.format("postTrafficSearchFuture succeeded. "));
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, promise.future());
			});
		} catch(Exception ex) {
			LOG.error(String.format("postTrafficSearchFuture failed. "), ex);
			promise.fail(ex);
			error(siteRequest, null, promise.future());
		}
		return promise.future();
	}

	public void sqlPOSTTrafficSearch(TrafficSearch o, Boolean inheritPk, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE TrafficSearch SET ");
			List<Object> bParams = new ArrayList<Object>();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			TrafficSearch o2 = new TrafficSearch();
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
					case "personKey":
						{
							Long l = Long.parseLong(jsonObject.getString(entityVar));
							if(l != null) {
								SearchList<TrafficPerson> searchList = new SearchList<TrafficPerson>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(TrafficPerson.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									if(bParams.size() > 0) {
										bSql.append(", ");
									}
									bSql.append("personKey=$" + num);
									num++;
									bParams.add(l2);
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("TrafficPerson");
									}
								}
							}
						}
						break;
					case "contrabandKeys":
						for(Long l : Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray()).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							if(l != null) {
								SearchList<TrafficContraband> searchList = new SearchList<TrafficContraband>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(TrafficContraband.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE TrafficContraband SET searchKey=$1 WHERE pk=$2")
												.execute(Tuple.of(pk, l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficSearch.contrabandKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("TrafficContraband");
									}
								}
							}
						}
						break;
					case "searchBasisKeys":
						for(Long l : Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray()).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							if(l != null) {
								SearchList<SearchBasis> searchList = new SearchList<SearchBasis>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(SearchBasis.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE SearchBasis SET searchKey=$1 WHERE pk=$2")
												.execute(Tuple.of(pk, l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficSearch.searchBasisKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("SearchBasis");
									}
								}
							}
						}
						break;
					case "searchTypeNum":
						o2.setSearchTypeNum(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("searchTypeNum=$" + num);
						num++;
						bParams.add(o2.sqlSearchTypeNum());
						break;
					case "searchVehicle":
						o2.setSearchVehicle(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("searchVehicle=$" + num);
						num++;
						bParams.add(o2.sqlSearchVehicle());
						break;
					case "searchDriver":
						o2.setSearchDriver(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("searchDriver=$" + num);
						num++;
						bParams.add(o2.sqlSearchDriver());
						break;
					case "searchPassenger":
						o2.setSearchPassenger(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("searchPassenger=$" + num);
						num++;
						bParams.add(o2.sqlSearchPassenger());
						break;
					case "searchProperty":
						o2.setSearchProperty(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("searchProperty=$" + num);
						num++;
						bParams.add(o2.sqlSearchProperty());
						break;
					case "searchVehicleSiezed":
						o2.setSearchVehicleSiezed(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("searchVehicleSiezed=$" + num);
						num++;
						bParams.add(o2.sqlSearchVehicleSiezed());
						break;
					case "searchPersonalPropertySiezed":
						o2.setSearchPersonalPropertySiezed(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("searchPersonalPropertySiezed=$" + num);
						num++;
						bParams.add(o2.sqlSearchPersonalPropertySiezed());
						break;
					case "searchOtherPropertySiezed":
						o2.setSearchOtherPropertySiezed(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("searchOtherPropertySiezed=$" + num);
						num++;
						bParams.add(o2.sqlSearchOtherPropertySiezed());
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
					LOG.error(String.format("sqlPOSTTrafficSearch failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPOSTTrafficSearch failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void postTrafficSearchResponse(TrafficSearch trafficSearch, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = trafficSearch.getSiteRequest_();
		try {
			response200POSTTrafficSearch(trafficSearch, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("postTrafficSearchResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("postTrafficSearchResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200POSTTrafficSearch(TrafficSearch o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200POSTTrafficSearch failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchTrafficSearch(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("patchTrafficSearch started. "));
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/traffic-search");
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
						patchTrafficSearchResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											aSearchTrafficSearch(siteRequest, false, true, true, "/api/traffic-search", "PATCH", d -> {
												if(d.succeeded()) {
													SearchList<TrafficSearch> listTrafficSearch = d.result();

													List<String> roles2 = Arrays.asList("SiteAdmin");
													if(listTrafficSearch.getQueryResponse().getResults().getNumFound() > 1
															&& !CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles2)
															&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles2)
															) {
														String message = String.format("roles required: " + String.join(", ", roles2));
														LOG.error(message);
														error(siteRequest, eventHandler, Future.failedFuture(message));
													} else {

														ApiRequest apiRequest = new ApiRequest();
														apiRequest.setRows(listTrafficSearch.getRows());
														apiRequest.setNumFound(listTrafficSearch.getQueryResponse().getResults().getNumFound());
														apiRequest.setNumPATCH(0L);
														apiRequest.initDeepApiRequest(siteRequest);
														siteRequest.setApiRequest_(apiRequest);
														eventBus.publish("websocketTrafficSearch", JsonObject.mapFrom(apiRequest).toString());
														SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listTrafficSearch.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(null);
														Date date = null;
														if(facets != null)
														date = (Date)facets.get("max_modified");
														String dt;
														if(date == null)
															dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));
														else
															dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")));
														listTrafficSearch.addFilterQuery(String.format("modified_indexed_date:[* TO %s]", dt));

														try {
															listPATCHTrafficSearch(apiRequest, listTrafficSearch, dt, e -> {
																if(e.succeeded()) {
																	patchTrafficSearchResponse(siteRequest, f -> {
																		if(f.succeeded()) {
																			LOG.debug(String.format("patchTrafficSearch succeeded. "));
																			blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																		} else {
																			LOG.error(String.format("patchTrafficSearch failed. ", f.cause()));
																			error(siteRequest, null, f);
																		}
																	});
																} else {
																	LOG.error(String.format("patchTrafficSearch failed. ", e.cause()));
																	error(siteRequest, null, e);
																}
															});
														} catch(Exception ex) {
															LOG.error(String.format("patchTrafficSearch failed. ", ex));
															error(siteRequest, null, Future.failedFuture(ex));
														}
													}
												} else {
													LOG.error(String.format("patchTrafficSearch failed. ", d.cause()));
													error(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("patchTrafficSearch failed. ", ex));
											error(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("patchTrafficSearch failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("patchTrafficSearch failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("patchTrafficSearch failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("patchTrafficSearch failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public void listPATCHTrafficSearch(ApiRequest apiRequest, SearchList<TrafficSearch> listTrafficSearch, String dt, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listTrafficSearch.getSiteRequest_();
		listTrafficSearch.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = siteRequest.copy();
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				patchTrafficSearchFuture(o, false).onFailure(ex -> {
					error(siteRequest2, eventHandler, Future.failedFuture(ex));
				})
			);
		});
		CompositeFuture.all(futures).onSuccess( a -> {
			listTrafficSearch.next(dt).onSuccess(next -> {
				if(next) {
					listPATCHTrafficSearch(apiRequest, listTrafficSearch, dt, eventHandler);
				} else {
					response200PATCHTrafficSearch(siteRequest, eventHandler);
				}
			}).onFailure(ex -> {
				LOG.error(String.format("listPATCHTrafficSearch failed. ", ex));
				error(listTrafficSearch.getSiteRequest_(), eventHandler, Future.failedFuture(ex));
			});
		}).onFailure(ex -> {
			LOG.error(String.format("listPATCHTrafficSearch failed. ", ex));
			error(listTrafficSearch.getSiteRequest_(), eventHandler, Future.failedFuture(ex));
		});
	}

	public Future<TrafficSearch> patchTrafficSearchFuture(TrafficSearch o, Boolean inheritPk) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		Promise<TrafficSearch> promise = Promise.promise();

		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			if(apiRequest != null && apiRequest.getNumFound() == 1L) {
				apiRequest.setOriginal(o);
				apiRequest.setPk(o.getPk());
			}
			pgPool.withTransaction(sqlConnection -> {
				Promise<TrafficSearch> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				sqlPATCHTrafficSearch(o, inheritPk, a -> {
					if(a.succeeded()) {
						TrafficSearch trafficSearch = a.result();
						defineTrafficSearch(trafficSearch, c -> {
							if(c.succeeded()) {
								attributeTrafficSearch(trafficSearch, d -> {
									if(d.succeeded()) {
										indexTrafficSearch(trafficSearch).onSuccess(e -> {
											promise1.complete(trafficSearch);
										}).onFailure(ex -> {
											LOG.error(String.format("patchTrafficSearchFuture failed. ", ex));
											promise1.fail(ex);
										});
									} else {
										LOG.error(String.format("patchTrafficSearchFuture failed. ", d.cause()));
										promise1.fail(d.cause());
									}
								});
							} else {
								LOG.error(String.format("patchTrafficSearchFuture failed. ", c.cause()));
								promise1.fail(c.cause());
							}
						});
					} else {
						LOG.error(String.format("patchTrafficSearchFuture failed. ", a.cause()));
								promise1.fail(a.cause());
					}
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, Future.failedFuture(ex));
			}).compose(trafficSearch -> {
				Promise<TrafficSearch> promise2 = Promise.promise();
				refreshTrafficSearch(trafficSearch, a -> {
					if(a.succeeded()) {
						if(apiRequest != null) {
							apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
							trafficSearch.apiRequestTrafficSearch();
							eventBus.publish("websocketTrafficSearch", JsonObject.mapFrom(apiRequest).toString());
						}
						promise2.complete(trafficSearch);
					} else {
						LOG.error(String.format("patchTrafficSearchFuture failed. ", a.cause()));
						promise2.fail(a.cause());
					}
				});
				return promise2.future();
			}).onSuccess(trafficSearch -> {
				promise.complete(trafficSearch);
				LOG.debug(String.format("patchTrafficSearchFuture succeeded. "));
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, promise.future());
			});
		} catch(Exception ex) {
			LOG.error(String.format("patchTrafficSearchFuture failed. "), ex);
			promise.fail(ex);
			error(siteRequest, null, promise.future());
		}
		return promise.future();
	}

	public void sqlPATCHTrafficSearch(TrafficSearch o, Boolean inheritPk, Handler<AsyncResult<TrafficSearch>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE TrafficSearch SET ");
			List<Object> bParams = new ArrayList<Object>();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			TrafficSearch o2 = new TrafficSearch();
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
					case "setPersonKey":
						{
							o2.setPersonKey(jsonObject.getString(methodName));
							Long l = o2.getPersonKey();
							if(l != null) {
								SearchList<TrafficPerson> searchList = new SearchList<TrafficPerson>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(TrafficPerson.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && !l2.equals(o.getPersonKey())) {
									o2.setPersonKey(l2);
									if(bParams.size() > 0)
										bSql.append(", ");
									bSql.append("personKey=$" + num);
									num++;
									bParams.add(l2);
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("TrafficPerson");
									}
								}
							}
						}
						break;
					case "removePersonKey":
						{
							o2.setPersonKey(jsonObject.getString(methodName));
							Long l = o2.getPersonKey();
							if(l != null) {
								SearchList<TrafficPerson> searchList = new SearchList<TrafficPerson>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(TrafficPerson.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									o2.setPersonKey((Long)null);
									if(bParams.size() > 0)
										bSql.append(", ");
									bSql.append("personKey=null");
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("TrafficPerson");
									}
								}
							}
						}
						break;
					case "addContrabandKeys":
						{
							Long l = Long.parseLong(jsonObject.getString(methodName));
							if(l != null) {
								SearchList<TrafficContraband> searchList = new SearchList<TrafficContraband>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(TrafficContraband.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && !o.getContrabandKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE TrafficContraband SET searchKey=$1 WHERE pk=$2")
												.execute(Tuple.of(pk, l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficSearch.contrabandKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("TrafficContraband");
									}
								}
							}
						}
						break;
					case "addAllContrabandKeys":
						JsonArray addAllContrabandKeysValues = jsonObject.getJsonArray(methodName);
						if(addAllContrabandKeysValues != null) {
							for(Integer i = 0; i <  addAllContrabandKeysValues.size(); i++) {
								Long l = Long.parseLong(addAllContrabandKeysValues.getString(i));
								if(l != null) {
									SearchList<TrafficContraband> searchList = new SearchList<TrafficContraband>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(TrafficContraband.class);
									searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null && !o.getContrabandKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE TrafficContraband SET searchKey=$1 WHERE pk=$2")
												.execute(Tuple.of(pk, l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficSearch.contrabandKeys failed", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("TrafficContraband");
										}
									}
								}
							}
						}
						break;
					case "setContrabandKeys":
						JsonArray setContrabandKeysValues = jsonObject.getJsonArray(methodName);
						JsonArray setContrabandKeysValues2 = new JsonArray();
						if(setContrabandKeysValues != null) {
							for(Integer i = 0; i <  setContrabandKeysValues.size(); i++) {
								Long l = Long.parseLong(setContrabandKeysValues.getString(i));
								if(l != null) {
									SearchList<TrafficContraband> searchList = new SearchList<TrafficContraband>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(TrafficContraband.class);
									searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null)
										setContrabandKeysValues2.add(l2);
									if(l2 != null && !o.getContrabandKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE TrafficContraband SET searchKey=$1 WHERE pk=$2")
												.execute(Tuple.of(pk, l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficSearch.contrabandKeys failed", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("TrafficContraband");
										}
									}
								}
							}
						}
						if(o.getContrabandKeys() != null) {
							for(Long l :  o.getContrabandKeys()) {
								if(l != null && (setContrabandKeysValues2 == null || !setContrabandKeysValues2.contains(l))) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE TrafficContraband SET searchKey=null WHERE pk=$1")
												.execute(Tuple.of(l)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficSearch.contrabandKeys failed", b.cause())));
										});
									}));
								}
							}
						}
						break;
					case "removeContrabandKeys":
						{
							Long l = Long.parseLong(jsonObject.getString(methodName));
							if(l != null) {
								SearchList<TrafficContraband> searchList = new SearchList<TrafficContraband>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(TrafficContraband.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && o.getContrabandKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE TrafficContraband SET searchKey=null WHERE pk=$1")
												.execute(Tuple.of(l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficSearch.contrabandKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("TrafficContraband");
									}
								}
							}
						}
						break;
					case "addSearchBasisKeys":
						{
							Long l = Long.parseLong(jsonObject.getString(methodName));
							if(l != null) {
								SearchList<SearchBasis> searchList = new SearchList<SearchBasis>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(SearchBasis.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && !o.getSearchBasisKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE SearchBasis SET searchKey=$1 WHERE pk=$2")
												.execute(Tuple.of(pk, l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficSearch.searchBasisKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("SearchBasis");
									}
								}
							}
						}
						break;
					case "addAllSearchBasisKeys":
						JsonArray addAllSearchBasisKeysValues = jsonObject.getJsonArray(methodName);
						if(addAllSearchBasisKeysValues != null) {
							for(Integer i = 0; i <  addAllSearchBasisKeysValues.size(); i++) {
								Long l = Long.parseLong(addAllSearchBasisKeysValues.getString(i));
								if(l != null) {
									SearchList<SearchBasis> searchList = new SearchList<SearchBasis>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(SearchBasis.class);
									searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null && !o.getSearchBasisKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE SearchBasis SET searchKey=$1 WHERE pk=$2")
												.execute(Tuple.of(pk, l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficSearch.searchBasisKeys failed", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("SearchBasis");
										}
									}
								}
							}
						}
						break;
					case "setSearchBasisKeys":
						JsonArray setSearchBasisKeysValues = jsonObject.getJsonArray(methodName);
						JsonArray setSearchBasisKeysValues2 = new JsonArray();
						if(setSearchBasisKeysValues != null) {
							for(Integer i = 0; i <  setSearchBasisKeysValues.size(); i++) {
								Long l = Long.parseLong(setSearchBasisKeysValues.getString(i));
								if(l != null) {
									SearchList<SearchBasis> searchList = new SearchList<SearchBasis>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(SearchBasis.class);
									searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null)
										setSearchBasisKeysValues2.add(l2);
									if(l2 != null && !o.getSearchBasisKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE SearchBasis SET searchKey=$1 WHERE pk=$2")
												.execute(Tuple.of(pk, l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficSearch.searchBasisKeys failed", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("SearchBasis");
										}
									}
								}
							}
						}
						if(o.getSearchBasisKeys() != null) {
							for(Long l :  o.getSearchBasisKeys()) {
								if(l != null && (setSearchBasisKeysValues2 == null || !setSearchBasisKeysValues2.contains(l))) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE SearchBasis SET searchKey=null WHERE pk=$1")
												.execute(Tuple.of(l)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficSearch.searchBasisKeys failed", b.cause())));
										});
									}));
								}
							}
						}
						break;
					case "removeSearchBasisKeys":
						{
							Long l = Long.parseLong(jsonObject.getString(methodName));
							if(l != null) {
								SearchList<SearchBasis> searchList = new SearchList<SearchBasis>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(SearchBasis.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk_indexed_string:" : "pk_indexed_long:") + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && o.getSearchBasisKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE SearchBasis SET searchKey=null WHERE pk=$1")
												.execute(Tuple.of(l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficSearch.searchBasisKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("SearchBasis");
									}
								}
							}
						}
						break;
					case "setSearchTypeNum":
							o2.setSearchTypeNum(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("searchTypeNum=$" + num);
							num++;
							bParams.add(o2.sqlSearchTypeNum());
						break;
					case "setSearchVehicle":
							o2.setSearchVehicle(jsonObject.getBoolean(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("searchVehicle=$" + num);
							num++;
							bParams.add(o2.sqlSearchVehicle());
						break;
					case "setSearchDriver":
							o2.setSearchDriver(jsonObject.getBoolean(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("searchDriver=$" + num);
							num++;
							bParams.add(o2.sqlSearchDriver());
						break;
					case "setSearchPassenger":
							o2.setSearchPassenger(jsonObject.getBoolean(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("searchPassenger=$" + num);
							num++;
							bParams.add(o2.sqlSearchPassenger());
						break;
					case "setSearchProperty":
							o2.setSearchProperty(jsonObject.getBoolean(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("searchProperty=$" + num);
							num++;
							bParams.add(o2.sqlSearchProperty());
						break;
					case "setSearchVehicleSiezed":
							o2.setSearchVehicleSiezed(jsonObject.getBoolean(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("searchVehicleSiezed=$" + num);
							num++;
							bParams.add(o2.sqlSearchVehicleSiezed());
						break;
					case "setSearchPersonalPropertySiezed":
							o2.setSearchPersonalPropertySiezed(jsonObject.getBoolean(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("searchPersonalPropertySiezed=$" + num);
							num++;
							bParams.add(o2.sqlSearchPersonalPropertySiezed());
						break;
					case "setSearchOtherPropertySiezed":
							o2.setSearchOtherPropertySiezed(jsonObject.getBoolean(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("searchOtherPropertySiezed=$" + num);
							num++;
							bParams.add(o2.sqlSearchOtherPropertySiezed());
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
					TrafficSearch o3 = new TrafficSearch();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
					eventHandler.handle(Future.succeededFuture(o3));
				} else {
					LOG.error(String.format("sqlPATCHTrafficSearch failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPATCHTrafficSearch failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void patchTrafficSearchResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PATCHTrafficSearch(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("patchTrafficSearchResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("patchTrafficSearchResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PATCHTrafficSearch(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PATCHTrafficSearch failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// GET //

	@Override
	public void getTrafficSearch(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/traffic-search/{id}");
					siteRequest.setRequestMethod("GET");
					{
						aSearchTrafficSearch(siteRequest, false, true, false, "/api/traffic-search/{id}", "GET", c -> {
							if(c.succeeded()) {
								SearchList<TrafficSearch> listTrafficSearch = c.result();
								getTrafficSearchResponse(listTrafficSearch, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.debug(String.format("getTrafficSearch succeeded. "));
									} else {
										LOG.error(String.format("getTrafficSearch failed. ", d.cause()));
										error(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("getTrafficSearch failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("getTrafficSearch failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("getTrafficSearch failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("getTrafficSearch failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public void getTrafficSearchResponse(SearchList<TrafficSearch> listTrafficSearch, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficSearch.getSiteRequest_();
		try {
			response200GETTrafficSearch(listTrafficSearch, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("getTrafficSearchResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("getTrafficSearchResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200GETTrafficSearch(SearchList<TrafficSearch> listTrafficSearch, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listTrafficSearch.getSiteRequest_();
			SolrDocumentList solrDocuments = listTrafficSearch.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listTrafficSearch.getList().stream().findFirst().orElse(null));
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200GETTrafficSearch failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// Search //

	@Override
	public void searchTrafficSearch(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/traffic-search");
					siteRequest.setRequestMethod("Search");
					{
						aSearchTrafficSearch(siteRequest, false, true, false, "/api/traffic-search", "Search", c -> {
							if(c.succeeded()) {
								SearchList<TrafficSearch> listTrafficSearch = c.result();
								searchTrafficSearchResponse(listTrafficSearch, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.debug(String.format("searchTrafficSearch succeeded. "));
									} else {
										LOG.error(String.format("searchTrafficSearch failed. ", d.cause()));
										error(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("searchTrafficSearch failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("searchTrafficSearch failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("searchTrafficSearch failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("searchTrafficSearch failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public void searchTrafficSearchResponse(SearchList<TrafficSearch> listTrafficSearch, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficSearch.getSiteRequest_();
		try {
			response200SearchTrafficSearch(listTrafficSearch, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("searchTrafficSearchResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("searchTrafficSearchResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchTrafficSearch(SearchList<TrafficSearch> listTrafficSearch, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listTrafficSearch.getSiteRequest_();
			QueryResponse responseSearch = listTrafficSearch.getQueryResponse();
			SolrDocumentList solrDocuments = listTrafficSearch.getSolrDocumentList();
			Long searchInMillis = Long.valueOf(responseSearch.getQTime());
			Long transmissionInMillis = responseSearch.getElapsedTime();
			Long startNum = responseSearch.getResults().getStart();
			Long foundNum = responseSearch.getResults().getNumFound();
			Integer returnedNum = responseSearch.getResults().size();
			String searchTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(searchInMillis), TimeUnit.MILLISECONDS.toMillis(searchInMillis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(searchInMillis)));
			String transmissionTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis), TimeUnit.MILLISECONDS.toMillis(transmissionInMillis) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis)));
			Exception exceptionSearch = responseSearch.getException();
			List<String> fls = listTrafficSearch.getFields();

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
				json.put("searchTime", searchTime);
				json.put("transmissionTime", transmissionTime);
			}
			JsonArray l = new JsonArray();
			listTrafficSearch.getList().stream().forEach(o -> {
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
					responsePivotSearchTrafficSearch(pivotFields, pivotArray);
				}
			}
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200SearchTrafficSearch failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}
	public void responsePivotSearchTrafficSearch(List<PivotField> pivotFields, JsonArray pivotArray) {
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
				responsePivotSearchTrafficSearch(pivotFields2, pivotArray2);
			}
		}
	}

	// AdminSearch //

	@Override
	public void adminsearchTrafficSearch(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/admin/traffic-search");
					siteRequest.setRequestMethod("AdminSearch");
					{
						aSearchTrafficSearch(siteRequest, false, true, false, "/api/admin/traffic-search", "AdminSearch", c -> {
							if(c.succeeded()) {
								SearchList<TrafficSearch> listTrafficSearch = c.result();
								adminsearchTrafficSearchResponse(listTrafficSearch, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.debug(String.format("adminsearchTrafficSearch succeeded. "));
									} else {
										LOG.error(String.format("adminsearchTrafficSearch failed. ", d.cause()));
										error(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("adminsearchTrafficSearch failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("adminsearchTrafficSearch failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("adminsearchTrafficSearch failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("adminsearchTrafficSearch failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public void adminsearchTrafficSearchResponse(SearchList<TrafficSearch> listTrafficSearch, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficSearch.getSiteRequest_();
		try {
			response200AdminSearchTrafficSearch(listTrafficSearch, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("adminsearchTrafficSearchResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("adminsearchTrafficSearchResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200AdminSearchTrafficSearch(SearchList<TrafficSearch> listTrafficSearch, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listTrafficSearch.getSiteRequest_();
			QueryResponse responseSearch = listTrafficSearch.getQueryResponse();
			SolrDocumentList solrDocuments = listTrafficSearch.getSolrDocumentList();
			Long searchInMillis = Long.valueOf(responseSearch.getQTime());
			Long transmissionInMillis = responseSearch.getElapsedTime();
			Long startNum = responseSearch.getResults().getStart();
			Long foundNum = responseSearch.getResults().getNumFound();
			Integer returnedNum = responseSearch.getResults().size();
			String searchTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(searchInMillis), TimeUnit.MILLISECONDS.toMillis(searchInMillis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(searchInMillis)));
			String transmissionTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis), TimeUnit.MILLISECONDS.toMillis(transmissionInMillis) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis)));
			Exception exceptionSearch = responseSearch.getException();
			List<String> fls = listTrafficSearch.getFields();

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
				json.put("searchTime", searchTime);
				json.put("transmissionTime", transmissionTime);
			}
			JsonArray l = new JsonArray();
			listTrafficSearch.getList().stream().forEach(o -> {
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
					responsePivotAdminSearchTrafficSearch(pivotFields, pivotArray);
				}
			}
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200AdminSearchTrafficSearch failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}
	public void responsePivotAdminSearchTrafficSearch(List<PivotField> pivotFields, JsonArray pivotArray) {
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
				responsePivotAdminSearchTrafficSearch(pivotFields2, pivotArray2);
			}
		}
	}

	// SearchPage //

	@Override
	public void searchpageTrafficSearchId(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		searchpageTrafficSearch(serviceRequest, eventHandler);
	}

	@Override
	public void searchpageTrafficSearch(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/traffic-search");
					siteRequest.setRequestMethod("SearchPage");
					{
						aSearchTrafficSearch(siteRequest, false, true, false, "/traffic-search", "SearchPage", c -> {
							if(c.succeeded()) {
								SearchList<TrafficSearch> listTrafficSearch = c.result();
								searchpageTrafficSearchResponse(listTrafficSearch, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.debug(String.format("searchpageTrafficSearch succeeded. "));
									} else {
										LOG.error(String.format("searchpageTrafficSearch failed. ", d.cause()));
										error(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("searchpageTrafficSearch failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("searchpageTrafficSearch failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("searchpageTrafficSearch failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("searchpageTrafficSearch failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public void searchpageTrafficSearchPageInit(TrafficSearchPage page, SearchList<TrafficSearch> listTrafficSearch) {
	}
	public void searchpageTrafficSearchResponse(SearchList<TrafficSearch> listTrafficSearch, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficSearch.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200SearchPageTrafficSearch(listTrafficSearch, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("searchpageTrafficSearchResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("searchpageTrafficSearchResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchPageTrafficSearch(SearchList<TrafficSearch> listTrafficSearch, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listTrafficSearch.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listTrafficSearch.getSiteRequest_(), buffer);
			TrafficSearchPage page = new TrafficSearchPage();
			SolrDocument pageSolrDocument = new SolrDocument();
			MultiMap requestHeaders = MultiMap.caseInsensitiveMultiMap();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/traffic-search");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listTrafficSearch.size() == 1)
				siteRequest.setRequestPk(listTrafficSearch.get(0).getPk());
			siteRequest.setW(w);
			page.setListTrafficSearch(listTrafficSearch);
			page.setSiteRequest_(siteRequest);
			searchpageTrafficSearchPageInit(page, listTrafficSearch);
			page.initDeepTrafficSearchPage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new ServiceResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOG.error(String.format("response200SearchPageTrafficSearch failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// General //

	public void createTrafficSearch(SiteRequestEnUS siteRequest, Handler<AsyncResult<TrafficSearch>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			String userId = siteRequest.getUserId();
			Long userKey = siteRequest.getUserKey();
			ZonedDateTime created = Optional.ofNullable(siteRequest.getJsonObject()).map(j -> j.getString("created")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(config.getString("siteZone"))))).orElse(ZonedDateTime.now(ZoneId.of(config.getString("siteZone"))));

			sqlConnection.preparedQuery("INSERT INTO TrafficSearch(created) VALUES($1) RETURNING pk")
					.collecting(Collectors.toList())
					.execute(Tuple.of(created.toOffsetDateTime())
					, createAsync
			-> {
				if(createAsync.succeeded()) {
					Row createLine = createAsync.result().value().stream().findFirst().orElseGet(() -> null);
					Long pk = createLine.getLong(0);
					TrafficSearch o = new TrafficSearch();
					o.setPk(pk);
					o.setSiteRequest_(siteRequest);
					eventHandler.handle(Future.succeededFuture(o));
				} else {
					LOG.error(String.format("createTrafficSearch failed. ", createAsync.cause()));
					eventHandler.handle(Future.failedFuture(createAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("createTrafficSearch failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void aSearchTrafficSearchQ(String uri, String apiMethod, SearchList<TrafficSearch> searchList, String entityVar, String valueIndexed, String varIndexed) {
		searchList.setQuery(varIndexed + ":" + ("*".equals(valueIndexed) ? valueIndexed : ClientUtils.escapeQueryChars(valueIndexed)));
		if(!"*".equals(entityVar)) {
		}
	}

	public String aSearchTrafficSearchFq(String uri, String apiMethod, SearchList<TrafficSearch> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		if(StringUtils.startsWith(valueIndexed, "[")) {
			String[] fqs = StringUtils.substringBefore(StringUtils.substringAfter(valueIndexed, "["), "]").split(" TO ");
			if(fqs.length != 2)
				throw new RuntimeException(String.format("\"%s\" invalid range query. ", valueIndexed));
			String fq1 = fqs[0].equals("*") ? fqs[0] : TrafficSearch.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[0]);
			String fq2 = fqs[1].equals("*") ? fqs[1] : TrafficSearch.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[1]);
			 return varIndexed + ":[" + fq1 + " TO " + fq2 + "]";
		} else {
			return varIndexed + ":" + TrafficSearch.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), valueIndexed);
		}
	}

	public void aSearchTrafficSearchSort(String uri, String apiMethod, SearchList<TrafficSearch> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addSort(varIndexed, ORDER.valueOf(valueIndexed));
	}

	public void aSearchTrafficSearchRows(String uri, String apiMethod, SearchList<TrafficSearch> searchList, Integer valueRows) {
			searchList.setRows(apiMethod != null && apiMethod.contains("Search") ? valueRows : 10);
	}

	public void aSearchTrafficSearchStart(String uri, String apiMethod, SearchList<TrafficSearch> searchList, Integer valueStart) {
		searchList.setStart(valueStart);
	}

	public void aSearchTrafficSearchVar(String uri, String apiMethod, SearchList<TrafficSearch> searchList, String var, String value) {
		searchList.getSiteRequest_().getRequestVars().put(var, value);
	}

	public void aSearchTrafficSearchUri(String uri, String apiMethod, SearchList<TrafficSearch> searchList) {
	}

	public void varsTrafficSearch(SiteRequestEnUS siteRequest, Handler<AsyncResult<SearchList<ServiceResponse>>> eventHandler) {
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
					LOG.error(String.format("aSearchTrafficSearch failed. "), e);
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOG.error(String.format("aSearchTrafficSearch failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void aSearchTrafficSearch(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, Handler<AsyncResult<SearchList<TrafficSearch>>> eventHandler) {
		try {
			SearchList<TrafficSearch> searchList = aSearchTrafficSearchList(siteRequest, populate, store, modify, uri, apiMethod);
			eventHandler.handle(Future.succeededFuture(searchList));
		} catch(Exception e) {
			LOG.error(String.format("aSearchTrafficSearch failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SearchList<TrafficSearch> aSearchTrafficSearchList(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod) {
		ServiceRequest serviceRequest = siteRequest.getServiceRequest();
		String entityListStr = siteRequest.getServiceRequest().getParams().getJsonObject("query").getString("fl");
		String[] entityList = entityListStr == null ? null : entityListStr.split(",\\s*");
		SearchList<TrafficSearch> searchList = new SearchList<TrafficSearch>();
		searchList.setPopulate(populate);
		searchList.setStore(store);
		searchList.setQuery("*:*");
		searchList.setC(TrafficSearch.class);
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
							varsIndexed[i] = TrafficSearch.varIndexedTrafficSearch(entityVar);
						}
						searchList.add("facet.pivot", (solrLocalParams == null ? "" : solrLocalParams) + StringUtils.join(varsIndexed, ","));
					}
				} else if(paramValuesObject != null) {
					for(Object paramObject : paramObjects) {
						switch(paramName) {
							case "q":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								varIndexed = "*".equals(entityVar) ? entityVar : TrafficSearch.varSearchTrafficSearch(entityVar);
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								valueIndexed = StringUtils.isEmpty(valueIndexed) ? "*" : valueIndexed;
								aSearchTrafficSearchQ(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "fq":
								Matcher mFq = Pattern.compile("(\\w+):(.+?(?=(\\)|\\s+OR\\s+|\\s+AND\\s+|$)))").matcher((String)paramObject);
								boolean foundFq = mFq.find();
								if(foundFq) {
									StringBuffer sb = new StringBuffer();
									while(foundFq) {
										entityVar = mFq.group(1).trim();
										valueIndexed = mFq.group(2).trim();
										varIndexed = TrafficSearch.varIndexedTrafficSearch(entityVar);
										String entityFq = aSearchTrafficSearchFq(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
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
								varIndexed = TrafficSearch.varIndexedTrafficSearch(entityVar);
								aSearchTrafficSearchSort(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "start":
								valueStart = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
								aSearchTrafficSearchStart(uri, apiMethod, searchList, valueStart);
								break;
							case "rows":
								valueRows = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
								aSearchTrafficSearchRows(uri, apiMethod, searchList, valueRows);
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
									varIndexed = TrafficSearch.varIndexedTrafficSearch(entityVar);
									searchList.add("facet.range", (solrLocalParams == null ? "" : solrLocalParams) + varIndexed);
								}
								break;
							case "facet.field":
								entityVar = (String)paramObject;
								varIndexed = TrafficSearch.varIndexedTrafficSearch(entityVar);
								if(varIndexed != null)
									searchList.addFacetField(varIndexed);
								break;
							case "var":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								aSearchTrafficSearchVar(uri, apiMethod, searchList, entityVar, valueIndexed);
								break;
						}
					}
					aSearchTrafficSearchUri(uri, apiMethod, searchList);
				}
			} catch(Exception e) {
				ExceptionUtils.rethrow(e);
			}
		});
		if("*:*".equals(searchList.getQuery()) && searchList.getSorts().size() == 0) {
			searchList.addSort("created_indexed_date", ORDER.desc);
		}
		aSearchTrafficSearch2(siteRequest, populate, store, modify, uri, apiMethod, searchList);
		searchList.initDeepForClass(siteRequest);
		return searchList;
	}
	public void aSearchTrafficSearch2(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, SearchList<TrafficSearch> searchList) {
	}

	public void defineTrafficSearch(TrafficSearch o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT * FROM TrafficSearch WHERE pk=$1")
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
										LOG.error(String.format("defineTrafficSearch failed. "), e);
									}
								}
							}
						}
						eventHandler.handle(Future.succeededFuture());
					} catch(Exception e) {
						LOG.error(String.format("defineTrafficSearch failed. "), e);
						eventHandler.handle(Future.failedFuture(e));
					}
				} else {
					LOG.error(String.format("defineTrafficSearch failed. ", defineAsync.cause()));
					eventHandler.handle(Future.failedFuture(defineAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("defineTrafficSearch failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void attributeTrafficSearch(TrafficSearch o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT personKey as pk2, 'personKey' from TrafficSearch where pk=$1 UNION SELECT pk as pk2, 'contrabandKeys' from TrafficContraband where searchKey=$2 UNION SELECT pk as pk2, 'searchBasisKeys' from SearchBasis where searchKey=$3")
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
						LOG.error(String.format("attributeTrafficSearch failed. ", attributeAsync.cause()));
						eventHandler.handle(Future.failedFuture(attributeAsync.cause()));
					}
				} catch(Exception e) {
					LOG.error(String.format("attributeTrafficSearch failed. "), e);
					eventHandler.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("attributeTrafficSearch failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public Future<Void> indexTrafficSearch(TrafficSearch o) {
		Promise<Void> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			o.initDeepForClass(siteRequest);
			SolrInputDocument document = new SolrInputDocument();
			o.indexTrafficSearch(document);
			webClient.post(ConfigKeys.SOLR_URL + "/update?commitWithin=10000&overwrite=true&wt=json").sendBuffer(Buffer.buffer(document.jsonStr())).onSuccess(a -> {
				promise.complete();
			}).onFailure(ex -> {
				LOG.error(String.format("indexTrafficSearch failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("indexTrafficSearch failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public void refreshTrafficSearch(TrafficSearch o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Boolean refresh = !"false".equals(siteRequest.getRequestVars().get("refresh"));
			if(refresh && !Optional.ofNullable(siteRequest.getJsonObject()).map(JsonObject::isEmpty).orElse(true)) {
				SearchList<TrafficSearch> searchList = new SearchList<TrafficSearch>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(TrafficSearch.class);
				searchList.addFilterQuery("modified_indexed_date:[" + DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(siteRequest.getApiRequest_().getCreated().toInstant(), ZoneId.of("UTC"))) + " TO *]");
				searchList.add("json.facet", "{personKey:{terms:{field:personKey_indexed_longs, limit:1000}}}");
				searchList.add("json.facet", "{contrabandKeys:{terms:{field:contrabandKeys_indexed_longs, limit:1000}}}");
				searchList.add("json.facet", "{searchBasisKeys:{terms:{field:searchBasisKeys_indexed_longs, limit:1000}}}");
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

					if("TrafficContraband".equals(classSimpleName2) && pk2 != null) {
						SearchList<TrafficContraband> searchList2 = new SearchList<TrafficContraband>();
						searchList2.setStore(true);
						searchList2.setQuery("*:*");
						searchList2.setC(TrafficContraband.class);
						searchList2.addFilterQuery("pk_indexed_long:" + pk2);
						searchList2.setRows(1);
						searchList2.initDeepSearchList(siteRequest);
						TrafficContraband o2 = searchList2.getList().stream().findFirst().orElse(null);

						if(o2 != null) {
							TrafficContrabandEnUSApiServiceImpl service = new TrafficContrabandEnUSApiServiceImpl(eventBus, config, workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider);
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUS(siteRequest.getUser(), siteRequest.getServiceRequest(), new JsonObject());
							ApiRequest apiRequest2 = new ApiRequest();
							apiRequest2.setRows(1);
							apiRequest2.setNumFound(1l);
							apiRequest2.setNumPATCH(0L);
							apiRequest2.initDeepApiRequest(siteRequest2);
							siteRequest2.setApiRequest_(apiRequest2);
							eventBus.publish("websocketTrafficContraband", JsonObject.mapFrom(apiRequest2).toString());

							o2.setPk(pk2);
							o2.setSiteRequest_(siteRequest2);
							futures.add(
								service.patchTrafficContrabandFuture(o2, false).onFailure(ex -> {
									LOG.error(String.format("TrafficContraband %s failed. ", pk2), ex);
									eventHandler.handle(Future.failedFuture(ex));
								})
							);
						}
					}

					if("SearchBasis".equals(classSimpleName2) && pk2 != null) {
						SearchList<SearchBasis> searchList2 = new SearchList<SearchBasis>();
						searchList2.setStore(true);
						searchList2.setQuery("*:*");
						searchList2.setC(SearchBasis.class);
						searchList2.addFilterQuery("pk_indexed_long:" + pk2);
						searchList2.setRows(1);
						searchList2.initDeepSearchList(siteRequest);
						SearchBasis o2 = searchList2.getList().stream().findFirst().orElse(null);

						if(o2 != null) {
							SearchBasisEnUSApiServiceImpl service = new SearchBasisEnUSApiServiceImpl(eventBus, config, workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider);
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUS(siteRequest.getUser(), siteRequest.getServiceRequest(), new JsonObject());
							ApiRequest apiRequest2 = new ApiRequest();
							apiRequest2.setRows(1);
							apiRequest2.setNumFound(1l);
							apiRequest2.setNumPATCH(0L);
							apiRequest2.initDeepApiRequest(siteRequest2);
							siteRequest2.setApiRequest_(apiRequest2);
							eventBus.publish("websocketSearchBasis", JsonObject.mapFrom(apiRequest2).toString());

							o2.setPk(pk2);
							o2.setSiteRequest_(siteRequest2);
							futures.add(
								service.patchSearchBasisFuture(o2, false).onFailure(ex -> {
									LOG.error(String.format("SearchBasis %s failed. ", pk2), ex);
									eventHandler.handle(Future.failedFuture(ex));
								})
							);
						}
					}
				}

				CompositeFuture.all(futures).onComplete(a -> {
					if(a.succeeded()) {
						TrafficSearchEnUSApiServiceImpl service = new TrafficSearchEnUSApiServiceImpl(eventBus, config, workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider);
						List<Future> futures2 = new ArrayList<>();
						for(TrafficSearch o2 : searchList.getList()) {
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUS(siteRequest.getUser(), siteRequest.getServiceRequest(), new JsonObject());
							o2.setSiteRequest_(siteRequest2);
							futures2.add(
								service.patchTrafficSearchFuture(o2, false).onFailure(ex -> {
									LOG.error(String.format("TrafficSearch %s failed. ", o2.getPk()), ex);
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
			LOG.error(String.format("refreshTrafficSearch failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}
}
