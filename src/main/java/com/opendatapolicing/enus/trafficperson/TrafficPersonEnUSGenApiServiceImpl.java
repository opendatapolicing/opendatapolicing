package com.opendatapolicing.enus.trafficperson;

import com.opendatapolicing.enus.trafficstop.TrafficStopEnUSApiServiceImpl;
import com.opendatapolicing.enus.trafficstop.TrafficStop;
import com.opendatapolicing.enus.trafficsearch.TrafficSearchEnUSApiServiceImpl;
import com.opendatapolicing.enus.trafficsearch.TrafficSearch;
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
public class TrafficPersonEnUSGenApiServiceImpl extends BaseApiServiceImpl implements TrafficPersonEnUSGenApiService {

	protected static final Logger LOG = LoggerFactory.getLogger(TrafficPersonEnUSGenApiServiceImpl.class);

	public TrafficPersonEnUSGenApiServiceImpl(EventBus eventBus, JsonObject config, WorkerExecutor workerExecutor, PgPool pgPool, SolrClient solrClient, OAuth2Auth oauth2AuthenticationProvider, AuthorizationProvider authorizationProvider) {
		super(eventBus, config, workerExecutor, pgPool, solrClient, oauth2AuthenticationProvider, authorizationProvider);
	}

	// PUTImport //

	@Override
	public void putimportTrafficPerson(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("putimportTrafficPerson started. "));
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/person/import");
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
						response200PUTImportTrafficPerson(siteRequest, c -> {
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
											eventBus.publish("websocketTrafficPerson", JsonObject.mapFrom(apiRequest).toString());
											varsTrafficPerson(siteRequest, d -> {
												if(d.succeeded()) {
													listPUTImportTrafficPerson(apiRequest, siteRequest).onSuccess(e -> {
														response200PUTImportTrafficPerson(siteRequest, f -> {
															if(f.succeeded()) {
																LOG.info(String.format("putimportTrafficPerson succeeded. "));
																blockingCodeHandler.handle(Future.succeededFuture(f.result()));
															} else {
																LOG.error(String.format("putimportTrafficPerson failed. ", f.cause()));
																error(siteRequest, null, f);
															}
														});
													}).onFailure(ex -> {
														LOG.error(String.format("putimportTrafficPerson failed. ", ex));
														error(siteRequest, null, Future.failedFuture(ex));
													});
												} else {
													LOG.error(String.format("putimportTrafficPerson failed. ", d.cause()));
													error(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putimportTrafficPerson failed. ", ex));
											error(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putimportTrafficPerson failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putimportTrafficPerson failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("putimportTrafficPerson failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("putimportTrafficPerson failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public Future<Void> listPUTImportTrafficPerson(ApiRequest apiRequest, SiteRequestEnUS siteRequest) {
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

				SearchList<TrafficPerson> searchList = new SearchList<TrafficPerson>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(TrafficPerson.class);
				searchList.addFilterQuery("inheritPk_indexed_string:" + json.getString("pk"));
				searchList.initDeepForClass(siteRequest2);

				if(searchList.size() == 1) {
					TrafficPerson o = searchList.getList().stream().findFirst().orElse(null);
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
							patchTrafficPersonFuture(o, true).onFailure(ex -> {
								LOG.error(String.format("listPUTImportTrafficPerson failed. ", ex));
							})
						);
					}
				} else {
					futures.add(
						postTrafficPersonFuture(siteRequest2, true).onFailure(ex -> {
							LOG.error(String.format("listPUTImportTrafficPerson failed. ", ex));
						})
					);
				}
			});
			CompositeFuture.all(futures).onSuccess(a -> {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
				promise.complete();
			}).onFailure(ex -> {
				LOG.error(String.format("listPUTImportTrafficPerson failed. ", ex));
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("listPUTImportTrafficPerson failed. ", ex));
			promise.fail(ex);
		}
		return promise.future();
	}

	public void response200PUTImportTrafficPerson(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTImportTrafficPerson failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTMerge //

	@Override
	public void putmergeTrafficPerson(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("putmergeTrafficPerson started. "));
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/person/merge");
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
						response200PUTMergeTrafficPerson(siteRequest, c -> {
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
											eventBus.publish("websocketTrafficPerson", JsonObject.mapFrom(apiRequest).toString());
											varsTrafficPerson(siteRequest, d -> {
												if(d.succeeded()) {
													listPUTMergeTrafficPerson(apiRequest, siteRequest).onSuccess(e -> {
														response200PUTMergeTrafficPerson(siteRequest, f -> {
															if(f.succeeded()) {
																LOG.info(String.format("putmergeTrafficPerson succeeded. "));
																blockingCodeHandler.handle(Future.succeededFuture(f.result()));
															} else {
																LOG.error(String.format("putmergeTrafficPerson failed. ", f.cause()));
																error(siteRequest, null, f);
															}
														});
													}).onFailure(ex -> {
														LOG.error(String.format("putmergeTrafficPerson failed. ", ex));
														error(siteRequest, null, Future.failedFuture(ex));
													});
												} else {
													LOG.error(String.format("putmergeTrafficPerson failed. ", d.cause()));
													error(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putmergeTrafficPerson failed. ", ex));
											error(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putmergeTrafficPerson failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putmergeTrafficPerson failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("putmergeTrafficPerson failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("putmergeTrafficPerson failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public Future<Void> listPUTMergeTrafficPerson(ApiRequest apiRequest, SiteRequestEnUS siteRequest) {
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

				SearchList<TrafficPerson> searchList = new SearchList<TrafficPerson>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(TrafficPerson.class);
				searchList.addFilterQuery("pk_indexed_long:" + json.getString("pk"));
				searchList.initDeepForClass(siteRequest2);

				if(searchList.size() == 1) {
					TrafficPerson o = searchList.getList().stream().findFirst().orElse(null);
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
							patchTrafficPersonFuture(o, false).onFailure(ex -> {
								LOG.error(String.format("listPUTMergeTrafficPerson failed. ", ex));
							})
						);
					}
				} else {
					futures.add(
						postTrafficPersonFuture(siteRequest2, false).onFailure(ex -> {
							LOG.error(String.format("listPUTMergeTrafficPerson failed. ", ex));
						})
					);
				}
			});
			CompositeFuture.all(futures).onSuccess(a -> {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
				promise.complete();
			}).onFailure(ex -> {
				LOG.error(String.format("listPUTMergeTrafficPerson failed. ", ex));
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("listPUTMergeTrafficPerson failed. ", ex));
			promise.fail(ex);
		}
		return promise.future();
	}

	public void putmergeTrafficPersonResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PUTMergeTrafficPerson(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("putmergeTrafficPersonResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("putmergeTrafficPersonResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTMergeTrafficPerson(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTMergeTrafficPerson failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTCopy //

	@Override
	public void putcopyTrafficPerson(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("putcopyTrafficPerson started. "));
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/person/copy");
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
						putcopyTrafficPersonResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											aSearchTrafficPerson(siteRequest, false, true, true, "/api/person/copy", "PUTCopy", d -> {
												if(d.succeeded()) {
													SearchList<TrafficPerson> listTrafficPerson = d.result();
													ApiRequest apiRequest = new ApiRequest();
													apiRequest.setRows(listTrafficPerson.getRows());
													apiRequest.setNumFound(listTrafficPerson.getQueryResponse().getResults().getNumFound());
													apiRequest.setNumPATCH(0L);
													apiRequest.initDeepApiRequest(siteRequest);
													siteRequest.setApiRequest_(apiRequest);
													eventBus.publish("websocketTrafficPerson", JsonObject.mapFrom(apiRequest).toString());
													try {
														listPUTCopyTrafficPerson(apiRequest, listTrafficPerson, e -> {
															if(e.succeeded()) {
																putcopyTrafficPersonResponse(siteRequest, f -> {
																	if(f.succeeded()) {
																		LOG.info(String.format("putcopyTrafficPerson succeeded. "));
																		blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																	} else {
																		LOG.error(String.format("putcopyTrafficPerson failed. ", f.cause()));
																		error(siteRequest, null, f);
																	}
																});
															} else {
																LOG.error(String.format("putcopyTrafficPerson failed. ", e.cause()));
																error(siteRequest, null, e);
															}
														});
													} catch(Exception ex) {
														LOG.error(String.format("putcopyTrafficPerson failed. ", ex));
														error(siteRequest, null, Future.failedFuture(ex));
													}
												} else {
													LOG.error(String.format("putcopyTrafficPerson failed. ", d.cause()));
													error(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putcopyTrafficPerson failed. ", ex));
											error(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putcopyTrafficPerson failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putcopyTrafficPerson failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("putcopyTrafficPerson failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("putcopyTrafficPerson failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public void listPUTCopyTrafficPerson(ApiRequest apiRequest, SearchList<TrafficPerson> listTrafficPerson, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listTrafficPerson.getSiteRequest_();
		listTrafficPerson.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = siteRequest.copy();
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				putcopyTrafficPersonFuture(siteRequest2, JsonObject.mapFrom(o)).onFailure(ex -> {
					LOG.error(String.format("listPUTCopyTrafficPerson failed. ", ex));
					error(siteRequest, eventHandler, Future.failedFuture(ex));
				})
			);
		});
		CompositeFuture.all(futures).onComplete( a -> {
			if(a.succeeded()) {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + listTrafficPerson.size());
				if(listTrafficPerson.next()) {
					listPUTCopyTrafficPerson(apiRequest, listTrafficPerson, eventHandler);
				} else {
					response200PUTCopyTrafficPerson(siteRequest, eventHandler);
				}
			} else {
				LOG.error(String.format("listPUTCopyTrafficPerson failed. ", a.cause()));
				error(listTrafficPerson.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<TrafficPerson> putcopyTrafficPersonFuture(SiteRequestEnUS siteRequest, JsonObject jsonObject) {
		Promise<TrafficPerson> promise = Promise.promise();

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
				Promise<TrafficPerson> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				createTrafficPerson(siteRequest, a -> {
					if(a.succeeded()) {
						TrafficPerson trafficPerson = a.result();
						sqlPUTCopyTrafficPerson(trafficPerson, jsonObject, b -> {
							if(b.succeeded()) {
								defineTrafficPerson(trafficPerson, c -> {
									if(c.succeeded()) {
										attributeTrafficPerson(trafficPerson, d -> {
											if(d.succeeded()) {
												indexTrafficPerson(trafficPerson, e -> {
													if(e.succeeded()) {
														promise1.complete(trafficPerson);
													} else {
														LOG.error(String.format("putcopyTrafficPersonFuture failed. ", e.cause()));
														promise1.fail(e.cause());
													}
												});
											} else {
												LOG.error(String.format("putcopyTrafficPersonFuture failed. ", d.cause()));
												promise1.fail(d.cause());
											}
										});
									} else {
										LOG.error(String.format("putcopyTrafficPersonFuture failed. ", c.cause()));
										promise1.fail(c.cause());
									}
								});
							} else {
								LOG.error(String.format("putcopyTrafficPersonFuture failed. ", b.cause()));
								promise1.fail(b.cause());
							}
						});
					} else {
						LOG.error(String.format("putcopyTrafficPersonFuture failed. ", a.cause()));
						promise1.fail(a.cause());
					}
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, Future.failedFuture(ex));
			}).compose(trafficPerson -> {
				Promise<TrafficPerson> promise2 = Promise.promise();
				refreshTrafficPerson(trafficPerson, a -> {
					if(a.succeeded()) {
						ApiRequest apiRequest = siteRequest.getApiRequest_();
						if(apiRequest != null) {
							apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
							trafficPerson.apiRequestTrafficPerson();
							eventBus.publish("websocketTrafficPerson", JsonObject.mapFrom(apiRequest).toString());
						}
						promise.complete(trafficPerson);
					} else {
						LOG.error(String.format("putcopyTrafficPersonFuture failed. ", a.cause()));
						promise2.fail(a.cause());
					}
				});
				return promise2.future();
			}).onSuccess(trafficPerson -> {
				promise.complete(trafficPerson);
				LOG.info(String.format("putcopyTrafficPersonFuture succeeded. "));
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, promise.future());
			});
		} catch(Exception ex) {
			LOG.error(String.format("putcopyTrafficPersonFuture failed. "), ex);
			promise.fail(ex);
			error(siteRequest, null, promise.future());
		}
		return promise.future();
	}

	public void sqlPUTCopyTrafficPerson(TrafficPerson o, JsonObject jsonObject, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE TrafficPerson SET ");
			List<Object> bParams = new ArrayList<Object>();
			TrafficPerson o2 = new TrafficPerson();
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
					case "trafficStopKey":
						{
							Long l = Long.parseLong(jsonObject.getString(entityVar));
							if(l != null) {
								if(bParams.size() > 0) {
									bSql.append(", ");
								}
								bSql.append("trafficStopKey=$" + num);
								num++;
								bParams.add(l);
							}
						}
						break;
					case "trafficSearchKeys":
						{
							for(Long l : Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray()).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
								futures.add(Future.future(a -> {
									sqlConnection.preparedQuery("UPDATE TrafficSearch SET personKey=$1 WHERE pk=$2")
											.execute(Tuple.of(pk, l)
											, b
									-> {
										if(b.succeeded())
											a.handle(Future.succeededFuture());
										else
											a.handle(Future.failedFuture(new Exception("value TrafficPerson.trafficSearchKeys failed", b.cause())));
									});
								}));
								if(!pks.contains(l)) {
									pks.add(l);
									classes.add("TrafficSearch");
								}
							}
						}
						break;
					case "personAge":
						o2.setPersonAge(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("personAge=$" + num);
						num++;
						bParams.add(o2.sqlPersonAge());
						break;
					case "personTypeId":
						o2.setPersonTypeId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("personTypeId=$" + num);
						num++;
						bParams.add(o2.sqlPersonTypeId());
						break;
					case "personGenderId":
						o2.setPersonGenderId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("personGenderId=$" + num);
						num++;
						bParams.add(o2.sqlPersonGenderId());
						break;
					case "personEthnicityId":
						o2.setPersonEthnicityId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("personEthnicityId=$" + num);
						num++;
						bParams.add(o2.sqlPersonEthnicityId());
						break;
					case "personRaceId":
						o2.setPersonRaceId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("personRaceId=$" + num);
						num++;
						bParams.add(o2.sqlPersonRaceId());
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
					LOG.error(String.format("sqlPUTCopyTrafficPerson failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPUTCopyTrafficPerson failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void putcopyTrafficPersonResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PUTCopyTrafficPerson(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("putcopyTrafficPersonResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("putcopyTrafficPersonResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTCopyTrafficPerson(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTCopyTrafficPerson failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// POST //

	@Override
	public void postTrafficPerson(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("postTrafficPerson started. "));
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/person");
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
						eventBus.publish("websocketTrafficPerson", JsonObject.mapFrom(apiRequest).toString());
						postTrafficPersonFuture(siteRequest, false).onSuccess(trafficPerson -> {
							apiRequest.setPk(trafficPerson.getPk());
							postTrafficPersonResponse(trafficPerson, d -> {
								if(d.succeeded()) {
									eventHandler.handle(Future.succeededFuture(d.result()));
									LOG.info(String.format("postTrafficPerson succeeded. "));
								} else {
									LOG.error(String.format("postTrafficPerson failed. ", d.cause()));
									error(siteRequest, eventHandler, d);
								}
							});
						}).onFailure(ex -> {
							LOG.error(String.format("postTrafficPerson failed. ", Future.failedFuture(ex)));
							error(siteRequest, eventHandler, Future.failedFuture(ex));
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("postTrafficPerson failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("postTrafficPerson failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("postTrafficPerson failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public Future<TrafficPerson> postTrafficPersonFuture(SiteRequestEnUS siteRequest, Boolean inheritPk) {
		Promise<TrafficPerson> promise = Promise.promise();

		try {
			pgPool.withTransaction(sqlConnection -> {
				Promise<TrafficPerson> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				createTrafficPerson(siteRequest, a -> {
					if(a.succeeded()) {
						TrafficPerson trafficPerson = a.result();
						sqlPOSTTrafficPerson(trafficPerson, inheritPk, b -> {
							if(b.succeeded()) {
								defineTrafficPerson(trafficPerson, c -> {
									if(c.succeeded()) {
										attributeTrafficPerson(trafficPerson, d -> {
											if(d.succeeded()) {
												indexTrafficPerson(trafficPerson, e -> {
													if(e.succeeded()) {
														promise1.complete(trafficPerson);
													} else {
														LOG.error(String.format("postTrafficPersonFuture failed. ", e.cause()));
														promise1.fail(e.cause());
													}
												});
											} else {
												LOG.error(String.format("postTrafficPersonFuture failed. ", d.cause()));
												promise1.fail(d.cause());
											}
										});
									} else {
										LOG.error(String.format("postTrafficPersonFuture failed. ", c.cause()));
										promise1.fail(c.cause());
									}
								});
							} else {
								LOG.error(String.format("postTrafficPersonFuture failed. ", b.cause()));
								promise1.fail(b.cause());
							}
						});
					} else {
						LOG.error(String.format("postTrafficPersonFuture failed. ", a.cause()));
						promise1.fail(a.cause());
					}
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, Future.failedFuture(ex));
			}).compose(trafficPerson -> {
				Promise<TrafficPerson> promise2 = Promise.promise();
				refreshTrafficPerson(trafficPerson, a -> {
					if(a.succeeded()) {
						ApiRequest apiRequest = siteRequest.getApiRequest_();
						if(apiRequest != null) {
							apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
							trafficPerson.apiRequestTrafficPerson();
							eventBus.publish("websocketTrafficPerson", JsonObject.mapFrom(apiRequest).toString());
						}
						promise.complete(trafficPerson);
					} else {
						LOG.error(String.format("postTrafficPersonFuture failed. ", a.cause()));
						promise2.fail(a.cause());
					}
				});
				return promise2.future();
			}).onSuccess(trafficPerson -> {
				promise.complete(trafficPerson);
				LOG.info(String.format("postTrafficPersonFuture succeeded. "));
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, promise.future());
			});
		} catch(Exception ex) {
			LOG.error(String.format("postTrafficPersonFuture failed. "), ex);
			promise.fail(ex);
			error(siteRequest, null, promise.future());
		}
		return promise.future();
	}

	public void sqlPOSTTrafficPerson(TrafficPerson o, Boolean inheritPk, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE TrafficPerson SET ");
			List<Object> bParams = new ArrayList<Object>();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			TrafficPerson o2 = new TrafficPerson();
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
					case "trafficStopKey":
						{
							Long l = Long.parseLong(jsonObject.getString(entityVar));
							if(l != null) {
								SearchList<TrafficStop> searchList = new SearchList<TrafficStop>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(TrafficStop.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									if(bParams.size() > 0) {
										bSql.append(", ");
									}
									bSql.append("trafficStopKey=$" + num);
									num++;
									bParams.add(l2);
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("TrafficStop");
									}
								}
							}
						}
						break;
					case "trafficSearchKeys":
						for(Long l : Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray()).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							if(l != null) {
								SearchList<TrafficSearch> searchList = new SearchList<TrafficSearch>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(TrafficSearch.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE TrafficSearch SET personKey=$1 WHERE pk=$2")
												.execute(Tuple.of(pk, l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficPerson.trafficSearchKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("TrafficSearch");
									}
								}
							}
						}
						break;
					case "personAge":
						o2.setPersonAge(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("personAge=$" + num);
						num++;
						bParams.add(o2.sqlPersonAge());
						break;
					case "personTypeId":
						o2.setPersonTypeId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("personTypeId=$" + num);
						num++;
						bParams.add(o2.sqlPersonTypeId());
						break;
					case "personGenderId":
						o2.setPersonGenderId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("personGenderId=$" + num);
						num++;
						bParams.add(o2.sqlPersonGenderId());
						break;
					case "personEthnicityId":
						o2.setPersonEthnicityId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("personEthnicityId=$" + num);
						num++;
						bParams.add(o2.sqlPersonEthnicityId());
						break;
					case "personRaceId":
						o2.setPersonRaceId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("personRaceId=$" + num);
						num++;
						bParams.add(o2.sqlPersonRaceId());
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
					LOG.error(String.format("sqlPOSTTrafficPerson failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPOSTTrafficPerson failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void postTrafficPersonResponse(TrafficPerson trafficPerson, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = trafficPerson.getSiteRequest_();
		try {
			response200POSTTrafficPerson(trafficPerson, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("postTrafficPersonResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("postTrafficPersonResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200POSTTrafficPerson(TrafficPerson o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200POSTTrafficPerson failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchTrafficPerson(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("patchTrafficPerson started. "));
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/person");
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
						patchTrafficPersonResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											aSearchTrafficPerson(siteRequest, false, true, true, "/api/person", "PATCH", d -> {
												if(d.succeeded()) {
													SearchList<TrafficPerson> listTrafficPerson = d.result();

													List<String> roles2 = Arrays.asList("SiteAdmin");
													if(listTrafficPerson.getQueryResponse().getResults().getNumFound() > 1
															&& !CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles2)
															&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles2)
															) {
														String message = String.format("roles required: " + String.join(", ", roles2));
														LOG.error(message);
														error(siteRequest, eventHandler, Future.failedFuture(message));
													} else {

														ApiRequest apiRequest = new ApiRequest();
														apiRequest.setRows(listTrafficPerson.getRows());
														apiRequest.setNumFound(listTrafficPerson.getQueryResponse().getResults().getNumFound());
														apiRequest.setNumPATCH(0L);
														apiRequest.initDeepApiRequest(siteRequest);
														siteRequest.setApiRequest_(apiRequest);
														eventBus.publish("websocketTrafficPerson", JsonObject.mapFrom(apiRequest).toString());
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
																			LOG.info(String.format("patchTrafficPerson succeeded. "));
																			blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																		} else {
																			LOG.error(String.format("patchTrafficPerson failed. ", f.cause()));
																			error(siteRequest, null, f);
																		}
																	});
																} else {
																	LOG.error(String.format("patchTrafficPerson failed. ", e.cause()));
																	error(siteRequest, null, e);
																}
															});
														} catch(Exception ex) {
															LOG.error(String.format("patchTrafficPerson failed. ", ex));
															error(siteRequest, null, Future.failedFuture(ex));
														}
													}
												} else {
													LOG.error(String.format("patchTrafficPerson failed. ", d.cause()));
													error(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("patchTrafficPerson failed. ", ex));
											error(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("patchTrafficPerson failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("patchTrafficPerson failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("patchTrafficPerson failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("patchTrafficPerson failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public void listPATCHTrafficPerson(ApiRequest apiRequest, SearchList<TrafficPerson> listTrafficPerson, String dt, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listTrafficPerson.getSiteRequest_();
		listTrafficPerson.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = siteRequest.copy();
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				patchTrafficPersonFuture(o, false).onFailure(ex -> {
					error(siteRequest2, eventHandler, Future.failedFuture(ex));
				})
			);
		});
		CompositeFuture.all(futures).onComplete( a -> {
			if(a.succeeded()) {
				if(listTrafficPerson.next(dt)) {
					listPATCHTrafficPerson(apiRequest, listTrafficPerson, dt, eventHandler);
				} else {
					response200PATCHTrafficPerson(siteRequest, eventHandler);
				}
			} else {
				LOG.error(String.format("listPATCHTrafficPerson failed. ", a.cause()));
				error(listTrafficPerson.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<TrafficPerson> patchTrafficPersonFuture(TrafficPerson o, Boolean inheritPk) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		Promise<TrafficPerson> promise = Promise.promise();

		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			if(apiRequest != null && apiRequest.getNumFound() == 1L) {
				apiRequest.setOriginal(o);
				apiRequest.setPk(o.getPk());
			}
			pgPool.withTransaction(sqlConnection -> {
				Promise<TrafficPerson> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				sqlPATCHTrafficPerson(o, inheritPk, a -> {
					if(a.succeeded()) {
						TrafficPerson trafficPerson = a.result();
						defineTrafficPerson(trafficPerson, c -> {
							if(c.succeeded()) {
								attributeTrafficPerson(trafficPerson, d -> {
									if(d.succeeded()) {
										indexTrafficPerson(trafficPerson, e -> {
											if(e.succeeded()) {
												promise1.complete(trafficPerson);
											} else {
												LOG.error(String.format("patchTrafficPersonFuture failed. ", e.cause()));
												promise1.fail(e.cause());
											}
										});
									} else {
										LOG.error(String.format("patchTrafficPersonFuture failed. ", d.cause()));
										promise1.fail(d.cause());
									}
								});
							} else {
								LOG.error(String.format("patchTrafficPersonFuture failed. ", c.cause()));
								promise1.fail(c.cause());
							}
						});
					} else {
						LOG.error(String.format("patchTrafficPersonFuture failed. ", a.cause()));
								promise1.fail(a.cause());
					}
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, Future.failedFuture(ex));
			}).compose(trafficPerson -> {
				Promise<TrafficPerson> promise2 = Promise.promise();
				refreshTrafficPerson(trafficPerson, a -> {
					if(a.succeeded()) {
						if(apiRequest != null) {
							apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
							trafficPerson.apiRequestTrafficPerson();
							eventBus.publish("websocketTrafficPerson", JsonObject.mapFrom(apiRequest).toString());
						}
						promise.complete(trafficPerson);
					} else {
						LOG.error(String.format("patchTrafficPersonFuture failed. ", a.cause()));
						promise2.fail(a.cause());
					}
				});
				return promise2.future();
			}).onSuccess(trafficPerson -> {
				promise.complete(trafficPerson);
				LOG.info(String.format("patchTrafficPersonFuture succeeded. "));
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, promise.future());
			});
		} catch(Exception ex) {
			LOG.error(String.format("patchTrafficPersonFuture failed. "), ex);
			promise.fail(ex);
			error(siteRequest, null, promise.future());
		}
		return promise.future();
	}

	public void sqlPATCHTrafficPerson(TrafficPerson o, Boolean inheritPk, Handler<AsyncResult<TrafficPerson>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE TrafficPerson SET ");
			List<Object> bParams = new ArrayList<Object>();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			TrafficPerson o2 = new TrafficPerson();
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
					case "setTrafficStopKey":
						{
							o2.setTrafficStopKey(jsonObject.getString(methodName));
							Long l = o2.getTrafficStopKey();
							if(l != null && !l.equals(o.getTrafficStopKey())) {
								SearchList<TrafficStop> searchList = new SearchList<TrafficStop>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(TrafficStop.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									o2.setTrafficStopKey(l2);
									if(bParams.size() > 0)
										bSql.append(", ");
									bSql.append("trafficStopKey=$" + num);
									num++;
									bParams.add(l2);
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("TrafficStop");
									}
								}
							}
						}
						break;
					case "removeTrafficStopKey":
						{
							o2.setTrafficStopKey(jsonObject.getString(methodName));
							Long l = o2.getTrafficStopKey();
							if(l != null) {
								SearchList<TrafficStop> searchList = new SearchList<TrafficStop>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(TrafficStop.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									o2.setTrafficStopKey((Long)null);
									if(bParams.size() > 0)
										bSql.append(", ");
									bSql.append("trafficStopKey=null");
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("TrafficStop");
									}
								}
							}
						}
						break;
					case "addTrafficSearchKeys":
						{
							Long l = Long.parseLong(jsonObject.getString(methodName));
							if(l != null) {
								SearchList<TrafficSearch> searchList = new SearchList<TrafficSearch>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(TrafficSearch.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && !o.getTrafficSearchKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE TrafficSearch SET personKey=$1 WHERE pk=$2")
												.execute(Tuple.of(pk, l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficPerson.trafficSearchKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("TrafficSearch");
									}
								}
							}
						}
						break;
					case "addAllTrafficSearchKeys":
						JsonArray addAllTrafficSearchKeysValues = jsonObject.getJsonArray(methodName);
						if(addAllTrafficSearchKeysValues != null) {
							for(Integer i = 0; i <  addAllTrafficSearchKeysValues.size(); i++) {
								Long l = Long.parseLong(addAllTrafficSearchKeysValues.getString(i));
								if(l != null) {
									SearchList<TrafficSearch> searchList = new SearchList<TrafficSearch>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(TrafficSearch.class);
									searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null && !o.getTrafficSearchKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE TrafficSearch SET personKey=$1 WHERE pk=$2")
												.execute(Tuple.of(pk, l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficPerson.trafficSearchKeys failed", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("TrafficSearch");
										}
									}
								}
							}
						}
						break;
					case "setTrafficSearchKeys":
						JsonArray setTrafficSearchKeysValues = jsonObject.getJsonArray(methodName);
						JsonArray setTrafficSearchKeysValues2 = new JsonArray();
						if(setTrafficSearchKeysValues != null) {
							for(Integer i = 0; i <  setTrafficSearchKeysValues.size(); i++) {
								Long l = Long.parseLong(setTrafficSearchKeysValues.getString(i));
								if(l != null) {
									SearchList<TrafficSearch> searchList = new SearchList<TrafficSearch>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(TrafficSearch.class);
									searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null)
										setTrafficSearchKeysValues2.add(l2);
									if(l2 != null && !o.getTrafficSearchKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE TrafficSearch SET personKey=$1 WHERE pk=$2")
												.execute(Tuple.of(pk, l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficPerson.trafficSearchKeys failed", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("TrafficSearch");
										}
									}
								}
							}
						}
						if(o.getTrafficSearchKeys() != null) {
							for(Long l :  o.getTrafficSearchKeys()) {
								if(l != null && (setTrafficSearchKeysValues == null || !setTrafficSearchKeysValues2.contains(l))) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE TrafficSearch SET personKey=null WHERE pk=$1")
												.execute(Tuple.of(l)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficPerson.trafficSearchKeys failed", b.cause())));
										});
									}));
								}
							}
						}
						break;
					case "removeTrafficSearchKeys":
						{
							Long l = Long.parseLong(jsonObject.getString(methodName));
							if(l != null) {
								SearchList<TrafficSearch> searchList = new SearchList<TrafficSearch>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(TrafficSearch.class);
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && o.getTrafficSearchKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE TrafficSearch SET personKey=null WHERE pk=$1")
												.execute(Tuple.of(l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value TrafficPerson.trafficSearchKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("TrafficSearch");
									}
								}
							}
						}
						break;
					case "setPersonAge":
							o2.setPersonAge(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("personAge=$" + num);
							num++;
							bParams.add(o2.sqlPersonAge());
						break;
					case "setPersonTypeId":
							o2.setPersonTypeId(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("personTypeId=$" + num);
							num++;
							bParams.add(o2.sqlPersonTypeId());
						break;
					case "setPersonGenderId":
							o2.setPersonGenderId(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("personGenderId=$" + num);
							num++;
							bParams.add(o2.sqlPersonGenderId());
						break;
					case "setPersonEthnicityId":
							o2.setPersonEthnicityId(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("personEthnicityId=$" + num);
							num++;
							bParams.add(o2.sqlPersonEthnicityId());
						break;
					case "setPersonRaceId":
							o2.setPersonRaceId(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("personRaceId=$" + num);
							num++;
							bParams.add(o2.sqlPersonRaceId());
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
					TrafficPerson o3 = new TrafficPerson();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
					eventHandler.handle(Future.succeededFuture(o3));
				} else {
					LOG.error(String.format("sqlPATCHTrafficPerson failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPATCHTrafficPerson failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void patchTrafficPersonResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PATCHTrafficPerson(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("patchTrafficPersonResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("patchTrafficPersonResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PATCHTrafficPerson(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PATCHTrafficPerson failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// GET //

	@Override
	public void getTrafficPerson(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/person/{id}");
					siteRequest.setRequestMethod("GET");
					{
						aSearchTrafficPerson(siteRequest, false, true, false, "/api/person/{id}", "GET", c -> {
							if(c.succeeded()) {
								SearchList<TrafficPerson> listTrafficPerson = c.result();
								getTrafficPersonResponse(listTrafficPerson, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("getTrafficPerson succeeded. "));
									} else {
										LOG.error(String.format("getTrafficPerson failed. ", d.cause()));
										error(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("getTrafficPerson failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("getTrafficPerson failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("getTrafficPerson failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("getTrafficPerson failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public void getTrafficPersonResponse(SearchList<TrafficPerson> listTrafficPerson, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficPerson.getSiteRequest_();
		try {
			response200GETTrafficPerson(listTrafficPerson, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("getTrafficPersonResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("getTrafficPersonResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200GETTrafficPerson(SearchList<TrafficPerson> listTrafficPerson, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listTrafficPerson.getSiteRequest_();
			SolrDocumentList solrDocuments = listTrafficPerson.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listTrafficPerson.getList().stream().findFirst().orElse(null));
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200GETTrafficPerson failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// Search //

	@Override
	public void searchTrafficPerson(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/person");
					siteRequest.setRequestMethod("Search");
					{
						aSearchTrafficPerson(siteRequest, false, true, false, "/api/person", "Search", c -> {
							if(c.succeeded()) {
								SearchList<TrafficPerson> listTrafficPerson = c.result();
								searchTrafficPersonResponse(listTrafficPerson, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("searchTrafficPerson succeeded. "));
									} else {
										LOG.error(String.format("searchTrafficPerson failed. ", d.cause()));
										error(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("searchTrafficPerson failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("searchTrafficPerson failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("searchTrafficPerson failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("searchTrafficPerson failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public void searchTrafficPersonResponse(SearchList<TrafficPerson> listTrafficPerson, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficPerson.getSiteRequest_();
		try {
			response200SearchTrafficPerson(listTrafficPerson, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("searchTrafficPersonResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("searchTrafficPersonResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchTrafficPerson(SearchList<TrafficPerson> listTrafficPerson, Handler<AsyncResult<ServiceResponse>> eventHandler) {
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
			List<String> fls = listTrafficPerson.getFields();

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
				json.put("searchTime", searchTime);
				json.put("transmissionTime", transmissionTime);
			}
			JsonArray l = new JsonArray();
			listTrafficPerson.getList().stream().forEach(o -> {
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
					responsePivotSearchTrafficPerson(pivotFields, pivotArray);
				}
			}
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200SearchTrafficPerson failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}
	public void responsePivotSearchTrafficPerson(List<PivotField> pivotFields, JsonArray pivotArray) {
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
				responsePivotSearchTrafficPerson(pivotFields2, pivotArray2);
			}
		}
	}

	// AdminSearch //

	@Override
	public void adminsearchTrafficPerson(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/admin/person");
					siteRequest.setRequestMethod("AdminSearch");
					{
						aSearchTrafficPerson(siteRequest, false, true, false, "/api/admin/person", "AdminSearch", c -> {
							if(c.succeeded()) {
								SearchList<TrafficPerson> listTrafficPerson = c.result();
								adminsearchTrafficPersonResponse(listTrafficPerson, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("adminsearchTrafficPerson succeeded. "));
									} else {
										LOG.error(String.format("adminsearchTrafficPerson failed. ", d.cause()));
										error(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("adminsearchTrafficPerson failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("adminsearchTrafficPerson failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("adminsearchTrafficPerson failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("adminsearchTrafficPerson failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public void adminsearchTrafficPersonResponse(SearchList<TrafficPerson> listTrafficPerson, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficPerson.getSiteRequest_();
		try {
			response200AdminSearchTrafficPerson(listTrafficPerson, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("adminsearchTrafficPersonResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("adminsearchTrafficPersonResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200AdminSearchTrafficPerson(SearchList<TrafficPerson> listTrafficPerson, Handler<AsyncResult<ServiceResponse>> eventHandler) {
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
			List<String> fls = listTrafficPerson.getFields();

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
				json.put("searchTime", searchTime);
				json.put("transmissionTime", transmissionTime);
			}
			JsonArray l = new JsonArray();
			listTrafficPerson.getList().stream().forEach(o -> {
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
					responsePivotAdminSearchTrafficPerson(pivotFields, pivotArray);
				}
			}
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200AdminSearchTrafficPerson failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}
	public void responsePivotAdminSearchTrafficPerson(List<PivotField> pivotFields, JsonArray pivotArray) {
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
				responsePivotAdminSearchTrafficPerson(pivotFields2, pivotArray2);
			}
		}
	}

	// SearchPage //

	@Override
	public void searchpageTrafficPersonId(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		searchpageTrafficPerson(serviceRequest, eventHandler);
	}

	@Override
	public void searchpageTrafficPerson(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/person");
					siteRequest.setRequestMethod("SearchPage");
					{
						aSearchTrafficPerson(siteRequest, false, true, false, "/person", "SearchPage", c -> {
							if(c.succeeded()) {
								SearchList<TrafficPerson> listTrafficPerson = c.result();
								searchpageTrafficPersonResponse(listTrafficPerson, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("searchpageTrafficPerson succeeded. "));
									} else {
										LOG.error(String.format("searchpageTrafficPerson failed. ", d.cause()));
										error(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("searchpageTrafficPerson failed. ", c.cause()));
								error(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("searchpageTrafficPerson failed. ", ex));
					error(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("searchpageTrafficPerson failed. ", ex));
						error(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("searchpageTrafficPerson failed. ", b.cause()));
					error(null, eventHandler, b);
				}
			}
		});
	}


	public void searchpageTrafficPersonPageInit(TrafficPersonPage page, SearchList<TrafficPerson> listTrafficPerson) {
	}
	public void searchpageTrafficPersonResponse(SearchList<TrafficPerson> listTrafficPerson, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listTrafficPerson.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200SearchPageTrafficPerson(listTrafficPerson, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("searchpageTrafficPersonResponse failed. ", a.cause()));
					error(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("searchpageTrafficPersonResponse failed. ", ex));
			error(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchPageTrafficPerson(SearchList<TrafficPerson> listTrafficPerson, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listTrafficPerson.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listTrafficPerson.getSiteRequest_(), buffer);
			TrafficPersonPage page = new TrafficPersonPage();
			SolrDocument pageSolrDocument = new SolrDocument();
			MultiMap requestHeaders = MultiMap.caseInsensitiveMultiMap();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/person");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listTrafficPerson.size() == 1)
				siteRequest.setRequestPk(listTrafficPerson.get(0).getPk());
			siteRequest.setW(w);
			page.setListTrafficPerson(listTrafficPerson);
			page.setSiteRequest_(siteRequest);
			searchpageTrafficPersonPageInit(page, listTrafficPerson);
			page.initDeepTrafficPersonPage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new ServiceResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOG.error(String.format("response200SearchPageTrafficPerson failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// General //

	public void createTrafficPerson(SiteRequestEnUS siteRequest, Handler<AsyncResult<TrafficPerson>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			String userId = siteRequest.getUserId();
			Long userKey = siteRequest.getUserKey();
			ZonedDateTime created = Optional.ofNullable(siteRequest.getJsonObject()).map(j -> j.getString("created")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(config.getString("siteZone"))))).orElse(ZonedDateTime.now(ZoneId.of(config.getString("siteZone"))));

			sqlConnection.preparedQuery("INSERT INTO TrafficPerson(created) VALUES($1) RETURNING pk")
					.collecting(Collectors.toList())
					.execute(Tuple.of(created.toOffsetDateTime())
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
					LOG.error(String.format("createTrafficPerson failed. ", createAsync.cause()));
					eventHandler.handle(Future.failedFuture(createAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("createTrafficPerson failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void aSearchTrafficPersonQ(String uri, String apiMethod, SearchList<TrafficPerson> searchList, String entityVar, String valueIndexed, String varIndexed) {
		searchList.setQuery(varIndexed + ":" + ("*".equals(valueIndexed) ? valueIndexed : ClientUtils.escapeQueryChars(valueIndexed)));
		if(!"*".equals(entityVar)) {
		}
	}

	public String aSearchTrafficPersonFq(String uri, String apiMethod, SearchList<TrafficPerson> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		if(StringUtils.startsWith(valueIndexed, "[")) {
			String[] fqs = StringUtils.substringBefore(StringUtils.substringAfter(valueIndexed, "["), "]").split(" TO ");
			if(fqs.length != 2)
				throw new RuntimeException(String.format("\"%s\" invalid range query. ", valueIndexed));
			String fq1 = fqs[0].equals("*") ? fqs[0] : TrafficPerson.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[0]);
			String fq2 = fqs[1].equals("*") ? fqs[1] : TrafficPerson.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[1]);
			 return varIndexed + ":[" + fq1 + " TO " + fq2 + "]";
		} else {
			return varIndexed + ":" + TrafficPerson.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), valueIndexed);
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

	public void varsTrafficPerson(SiteRequestEnUS siteRequest, Handler<AsyncResult<SearchList<ServiceResponse>>> eventHandler) {
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
					LOG.error(String.format("aSearchTrafficPerson failed. "), e);
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOG.error(String.format("aSearchTrafficPerson failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void aSearchTrafficPerson(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, Handler<AsyncResult<SearchList<TrafficPerson>>> eventHandler) {
		try {
			SearchList<TrafficPerson> searchList = aSearchTrafficPersonList(siteRequest, populate, store, modify, uri, apiMethod);
			eventHandler.handle(Future.succeededFuture(searchList));
		} catch(Exception e) {
			LOG.error(String.format("aSearchTrafficPerson failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SearchList<TrafficPerson> aSearchTrafficPersonList(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod) {
		ServiceRequest serviceRequest = siteRequest.getServiceRequest();
		String entityListStr = siteRequest.getServiceRequest().getParams().getJsonObject("query").getString("fl");
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
							varsIndexed[i] = TrafficPerson.varIndexedTrafficPerson(entityVar);
						}
						searchList.add("facet.pivot", (solrLocalParams == null ? "" : solrLocalParams) + StringUtils.join(varsIndexed, ","));
					}
				} else if(paramValuesObject != null) {
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
								Matcher mFq = Pattern.compile("(\\w+):(.+?(?=(\\)|\\s+OR\\s+|\\s+AND\\s+|$)))").matcher((String)paramObject);
								boolean foundFq = mFq.find();
								if(foundFq) {
									StringBuffer sb = new StringBuffer();
									while(foundFq) {
										entityVar = mFq.group(1).trim();
										valueIndexed = mFq.group(2).trim();
										varIndexed = TrafficPerson.varIndexedTrafficPerson(entityVar);
										String entityFq = aSearchTrafficPersonFq(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
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
								varIndexed = TrafficPerson.varIndexedTrafficPerson(entityVar);
								aSearchTrafficPersonSort(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "start":
								valueStart = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
								aSearchTrafficPersonStart(uri, apiMethod, searchList, valueStart);
								break;
							case "rows":
								valueRows = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
								aSearchTrafficPersonRows(uri, apiMethod, searchList, valueRows);
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
									varIndexed = TrafficPerson.varIndexedTrafficPerson(entityVar);
									searchList.add("facet.range", (solrLocalParams == null ? "" : solrLocalParams) + varIndexed);
								}
								break;
							case "facet.field":
								entityVar = (String)paramObject;
								varIndexed = TrafficPerson.varIndexedTrafficPerson(entityVar);
								if(varIndexed != null)
									searchList.addFacetField(varIndexed);
								break;
							case "var":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								aSearchTrafficPersonVar(uri, apiMethod, searchList, entityVar, valueIndexed);
								break;
						}
					}
					aSearchTrafficPersonUri(uri, apiMethod, searchList);
				}
			} catch(Exception e) {
				ExceptionUtils.rethrow(e);
			}
		});
		if("*:*".equals(searchList.getQuery()) && searchList.getSorts().size() == 0) {
			searchList.addSort("created_indexed_date", ORDER.desc);
		}
		aSearchTrafficPerson2(siteRequest, populate, store, modify, uri, apiMethod, searchList);
		searchList.initDeepForClass(siteRequest);
		return searchList;
	}
	public void aSearchTrafficPerson2(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, SearchList<TrafficPerson> searchList) {
	}

	public void defineTrafficPerson(TrafficPerson o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT * FROM TrafficPerson WHERE pk=$1")
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
										LOG.error(String.format("defineTrafficPerson failed. "), e);
									}
								}
							}
						}
						eventHandler.handle(Future.succeededFuture());
					} catch(Exception e) {
						LOG.error(String.format("defineTrafficPerson failed. "), e);
						eventHandler.handle(Future.failedFuture(e));
					}
				} else {
					LOG.error(String.format("defineTrafficPerson failed. ", defineAsync.cause()));
					eventHandler.handle(Future.failedFuture(defineAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("defineTrafficPerson failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void attributeTrafficPerson(TrafficPerson o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT trafficStopKey as pk1, 'trafficStopKey' from TrafficPerson where pk=$1 UNION SELECT pk as pk1, 'trafficSearchKeys' from TrafficSearch where personKey=$2")
					.collecting(Collectors.toList())
					.execute(Tuple.of(pk, pk)
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
						LOG.error(String.format("attributeTrafficPerson failed. ", attributeAsync.cause()));
						eventHandler.handle(Future.failedFuture(attributeAsync.cause()));
					}
				} catch(Exception e) {
					LOG.error(String.format("attributeTrafficPerson failed. "), e);
					eventHandler.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("attributeTrafficPerson failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void indexTrafficPerson(TrafficPerson o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			o.initDeepForClass(siteRequest);
			o.indexForClass();
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOG.error(String.format("indexTrafficPerson failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void refreshTrafficPerson(TrafficPerson o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Boolean refresh = !"false".equals(siteRequest.getRequestVars().get("refresh"));
			if(refresh && !Optional.ofNullable(siteRequest.getJsonObject()).map(JsonObject::isEmpty).orElse(true)) {
				SearchList<TrafficPerson> searchList = new SearchList<TrafficPerson>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(TrafficPerson.class);
				searchList.addFilterQuery("modified_indexed_date:[" + DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(siteRequest.getApiRequest_().getCreated().toInstant(), ZoneId.of("UTC"))) + " TO *]");
				searchList.add("json.facet", "{trafficStopKey:{terms:{field:trafficStopKey_indexed_longs, limit:1000}}}");
				searchList.add("json.facet", "{trafficSearchKeys:{terms:{field:trafficSearchKeys_indexed_longs, limit:1000}}}");
				searchList.setRows(1000);
				searchList.initDeepSearchList(siteRequest);
				List<Future> futures = new ArrayList<>();

				for(int i=0; i < pks.size(); i++) {
					Long pk2 = pks.get(i);
					String classSimpleName2 = classes.get(i);

					if("TrafficStop".equals(classSimpleName2) && pk2 != null) {
						SearchList<TrafficStop> searchList2 = new SearchList<TrafficStop>();
						searchList2.setStore(true);
						searchList2.setQuery("*:*");
						searchList2.setC(TrafficStop.class);
						searchList2.addFilterQuery("pk_indexed_long:" + pk2);
						searchList2.setRows(1);
						searchList2.initDeepSearchList(siteRequest);
						TrafficStop o2 = searchList2.getList().stream().findFirst().orElse(null);

						if(o2 != null) {
							TrafficStopEnUSApiServiceImpl service = new TrafficStopEnUSApiServiceImpl(eventBus, config, workerExecutor, pgPool, solrClient, oauth2AuthenticationProvider, authorizationProvider);
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUS(siteRequest.getUser(), siteRequest.getServiceRequest(), new JsonObject());
							ApiRequest apiRequest2 = new ApiRequest();
							apiRequest2.setRows(1);
							apiRequest2.setNumFound(1l);
							apiRequest2.setNumPATCH(0L);
							apiRequest2.initDeepApiRequest(siteRequest2);
							siteRequest2.setApiRequest_(apiRequest2);
							eventBus.publish("websocketTrafficStop", JsonObject.mapFrom(apiRequest2).toString());

							o2.setPk(pk2);
							o2.setSiteRequest_(siteRequest2);
							futures.add(
								service.patchTrafficStopFuture(o2, false).onFailure(ex -> {
									LOG.error(String.format("TrafficStop %s failed. ", pk2), ex);
									eventHandler.handle(Future.failedFuture(ex));
								})
							);
						}
					}

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
							TrafficSearchEnUSApiServiceImpl service = new TrafficSearchEnUSApiServiceImpl(eventBus, config, workerExecutor, pgPool, solrClient, oauth2AuthenticationProvider, authorizationProvider);
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUS(siteRequest.getUser(), siteRequest.getServiceRequest(), new JsonObject());
							ApiRequest apiRequest2 = new ApiRequest();
							apiRequest2.setRows(1);
							apiRequest2.setNumFound(1l);
							apiRequest2.setNumPATCH(0L);
							apiRequest2.initDeepApiRequest(siteRequest2);
							siteRequest2.setApiRequest_(apiRequest2);
							eventBus.publish("websocketTrafficSearch", JsonObject.mapFrom(apiRequest2).toString());

							o2.setPk(pk2);
							o2.setSiteRequest_(siteRequest2);
							futures.add(
								service.patchTrafficSearchFuture(o2, false).onFailure(ex -> {
									LOG.error(String.format("TrafficSearch %s failed. ", pk2), ex);
									eventHandler.handle(Future.failedFuture(ex));
								})
							);
						}
					}
				}

				CompositeFuture.all(futures).onComplete(a -> {
					if(a.succeeded()) {
						TrafficPersonEnUSApiServiceImpl service = new TrafficPersonEnUSApiServiceImpl(eventBus, config, workerExecutor, pgPool, solrClient, oauth2AuthenticationProvider, authorizationProvider);
						List<Future> futures2 = new ArrayList<>();
						for(TrafficPerson o2 : searchList.getList()) {
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUS(siteRequest.getUser(), siteRequest.getServiceRequest(), new JsonObject());
							o2.setSiteRequest_(siteRequest2);
							futures2.add(
								service.patchTrafficPersonFuture(o2, false).onFailure(ex -> {
									LOG.error(String.format("TrafficPerson %s failed. ", o2.getPk()), ex);
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
			LOG.error(String.format("refreshTrafficPerson failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}
}
