package com.opendatapolicing.enus.state;

import com.opendatapolicing.enus.agency.SiteAgencyEnUSGenApiServiceImpl;
import com.opendatapolicing.enus.agency.SiteAgency;
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
public class SiteStateEnUSGenApiServiceImpl implements SiteStateEnUSGenApiService {

	protected static final Logger LOG = LoggerFactory.getLogger(SiteStateEnUSGenApiServiceImpl.class);

	protected static final String SERVICE_ADDRESS = "SiteStateEnUSApiServiceImpl";

	protected SiteContextEnUS siteContext;

	public SiteStateEnUSGenApiServiceImpl(SiteContextEnUS siteContext) {
		this.siteContext = siteContext;
	}

	// PUTImport //

	@Override
	public void putimportSiteState(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("putimportSiteState started. "));
		userSiteState(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
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
						putimportSiteStateResponse(siteRequest, c -> {
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
											siteRequest.getVertx().eventBus().publish("websocketSiteState", JsonObject.mapFrom(apiRequest).toString());
											varsSiteState(siteRequest, d -> {
												if(d.succeeded()) {
													listPUTImportSiteState(apiRequest, siteRequest, e -> {
														if(e.succeeded()) {
															putimportSiteStateResponse(siteRequest, f -> {
																if(e.succeeded()) {
																	LOG.info(String.format("putimportSiteState succeeded. "));
																	blockingCodeHandler.handle(Future.succeededFuture(e.result()));
																} else {
																	LOG.error(String.format("putimportSiteState failed. ", f.cause()));
																	errorSiteState(siteRequest, null, f);
																}
															});
														} else {
															LOG.error(String.format("putimportSiteState failed. ", e.cause()));
															errorSiteState(siteRequest, null, e);
														}
													});
												} else {
													LOG.error(String.format("putimportSiteState failed. ", d.cause()));
													errorSiteState(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putimportSiteState failed. ", ex));
											errorSiteState(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putimportSiteState failed. ", c.cause()));
								errorSiteState(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putimportSiteState failed. ", ex));
					errorSiteState(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("putimportSiteState failed. ", b.cause()));
				errorSiteState(null, eventHandler, b);
			}
		});
	}


	public void listPUTImportSiteState(ApiRequest apiRequest, SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				json.put("created", json.getValue("created"));

				SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSiteState(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), json);
				siteRequest2.setApiRequest_(apiRequest);
				siteRequest2.setRequestVars(siteRequest.getRequestVars());

				SearchList<SiteState> searchList = new SearchList<SiteState>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(SiteState.class);
				searchList.addFilterQuery("deleted_indexed_boolean:false");
				searchList.addFilterQuery("archived_indexed_boolean:false");
				searchList.addFilterQuery("inheritPk_indexed_long:" + json.getString("pk"));
				searchList.initDeepForClass(siteRequest2);

				if(searchList.size() == 1) {
					SiteState o = searchList.getList().stream().findFirst().orElse(null);
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
							patchSiteStateFuture(o, true, a -> {
								if(a.succeeded()) {
								} else {
									LOG.error(String.format("listPUTImportSiteState failed. ", a.cause()));
									errorSiteState(siteRequest2, eventHandler, a);
								}
							})
						);
					}
				} else {
					futures.add(
						postSiteStateFuture(siteRequest2, true, a -> {
							if(a.succeeded()) {
							} else {
								LOG.error(String.format("listPUTImportSiteState failed. ", a.cause()));
								errorSiteState(siteRequest2, eventHandler, a);
							}
						})
					);
				}
			});
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
					response200PUTImportSiteState(siteRequest, eventHandler);
				} else {
					LOG.error(String.format("listPUTImportSiteState failed. ", a.cause()));
					errorSiteState(apiRequest.getSiteRequest_(), eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("listPUTImportSiteState failed. ", ex));
			errorSiteState(siteRequest, null, Future.failedFuture(ex));
		}
	}

	public void putimportSiteStateResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PUTImportSiteState(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("putimportSiteStateResponse failed. ", a.cause()));
					errorSiteState(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("putimportSiteStateResponse failed. ", ex));
			errorSiteState(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTImportSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTImportSiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTMerge //

	@Override
	public void putmergeSiteState(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("putmergeSiteState started. "));
		userSiteState(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
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
						putmergeSiteStateResponse(siteRequest, c -> {
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
											siteRequest.getVertx().eventBus().publish("websocketSiteState", JsonObject.mapFrom(apiRequest).toString());
											varsSiteState(siteRequest, d -> {
												if(d.succeeded()) {
													listPUTMergeSiteState(apiRequest, siteRequest, e -> {
														if(e.succeeded()) {
															putmergeSiteStateResponse(siteRequest, f -> {
																if(e.succeeded()) {
																	LOG.info(String.format("putmergeSiteState succeeded. "));
																	blockingCodeHandler.handle(Future.succeededFuture(e.result()));
																} else {
																	LOG.error(String.format("putmergeSiteState failed. ", f.cause()));
																	errorSiteState(siteRequest, null, f);
																}
															});
														} else {
															LOG.error(String.format("putmergeSiteState failed. ", e.cause()));
															errorSiteState(siteRequest, null, e);
														}
													});
												} else {
													LOG.error(String.format("putmergeSiteState failed. ", d.cause()));
													errorSiteState(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putmergeSiteState failed. ", ex));
											errorSiteState(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putmergeSiteState failed. ", c.cause()));
								errorSiteState(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putmergeSiteState failed. ", ex));
					errorSiteState(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("putmergeSiteState failed. ", b.cause()));
				errorSiteState(null, eventHandler, b);
			}
		});
	}


	public void listPUTMergeSiteState(ApiRequest apiRequest, SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSiteState(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), json);
				siteRequest2.setApiRequest_(apiRequest);
				siteRequest2.setRequestVars(siteRequest.getRequestVars());

				SearchList<SiteState> searchList = new SearchList<SiteState>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(SiteState.class);
				searchList.addFilterQuery("deleted_indexed_boolean:false");
				searchList.addFilterQuery("archived_indexed_boolean:false");
				searchList.addFilterQuery("pk_indexed_long:" + json.getString("pk"));
				searchList.initDeepForClass(siteRequest2);

				if(searchList.size() == 1) {
					SiteState o = searchList.getList().stream().findFirst().orElse(null);
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
							patchSiteStateFuture(o, false, a -> {
								if(a.succeeded()) {
								} else {
									LOG.error(String.format("listPUTMergeSiteState failed. ", a.cause()));
									errorSiteState(siteRequest2, eventHandler, a);
								}
							})
						);
					}
				} else {
					futures.add(
						postSiteStateFuture(siteRequest2, false, a -> {
							if(a.succeeded()) {
							} else {
								LOG.error(String.format("listPUTMergeSiteState failed. ", a.cause()));
								errorSiteState(siteRequest2, eventHandler, a);
							}
						})
					);
				}
			});
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
					response200PUTMergeSiteState(siteRequest, eventHandler);
				} else {
					LOG.error(String.format("listPUTMergeSiteState failed. ", a.cause()));
					errorSiteState(apiRequest.getSiteRequest_(), eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("listPUTMergeSiteState failed. ", ex));
			errorSiteState(siteRequest, null, Future.failedFuture(ex));
		}
	}

	public void putmergeSiteStateResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PUTMergeSiteState(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("putmergeSiteStateResponse failed. ", a.cause()));
					errorSiteState(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("putmergeSiteStateResponse failed. ", ex));
			errorSiteState(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTMergeSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTMergeSiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTCopy //

	@Override
	public void putcopySiteState(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("putcopySiteState started. "));
		userSiteState(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
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
						putcopySiteStateResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											aSearchSiteState(siteRequest, false, true, true, "/api/state/copy", "PUTCopy", d -> {
												if(d.succeeded()) {
													SearchList<SiteState> listSiteState = d.result();
													ApiRequest apiRequest = new ApiRequest();
													apiRequest.setRows(listSiteState.getRows());
													apiRequest.setNumFound(listSiteState.getQueryResponse().getResults().getNumFound());
													apiRequest.setNumPATCH(0L);
													apiRequest.initDeepApiRequest(siteRequest);
													siteRequest.setApiRequest_(apiRequest);
													siteRequest.getVertx().eventBus().publish("websocketSiteState", JsonObject.mapFrom(apiRequest).toString());
													try {
														listPUTCopySiteState(apiRequest, listSiteState, e -> {
															if(e.succeeded()) {
																putcopySiteStateResponse(siteRequest, f -> {
																	if(f.succeeded()) {
																		LOG.info(String.format("putcopySiteState succeeded. "));
																		blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																	} else {
																		LOG.error(String.format("putcopySiteState failed. ", f.cause()));
																		errorSiteState(siteRequest, null, f);
																	}
																});
															} else {
																LOG.error(String.format("putcopySiteState failed. ", e.cause()));
																errorSiteState(siteRequest, null, e);
															}
														});
													} catch(Exception ex) {
														LOG.error(String.format("putcopySiteState failed. ", ex));
														errorSiteState(siteRequest, null, Future.failedFuture(ex));
													}
												} else {
													LOG.error(String.format("putcopySiteState failed. ", d.cause()));
													errorSiteState(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putcopySiteState failed. ", ex));
											errorSiteState(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putcopySiteState failed. ", c.cause()));
								errorSiteState(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putcopySiteState failed. ", ex));
					errorSiteState(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("putcopySiteState failed. ", b.cause()));
				errorSiteState(null, eventHandler, b);
			}
		});
	}


	public void listPUTCopySiteState(ApiRequest apiRequest, SearchList<SiteState> listSiteState, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listSiteState.getSiteRequest_();
		listSiteState.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSiteState(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				putcopySiteStateFuture(siteRequest2, JsonObject.mapFrom(o), a -> {
					if(a.succeeded()) {
					} else {
						LOG.error(String.format("listPUTCopySiteState failed. ", a.cause()));
						errorSiteState(siteRequest, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).onComplete( a -> {
			if(a.succeeded()) {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + listSiteState.size());
				if(listSiteState.next()) {
					listPUTCopySiteState(apiRequest, listSiteState, eventHandler);
				} else {
					response200PUTCopySiteState(siteRequest, eventHandler);
				}
			} else {
				LOG.error(String.format("listPUTCopySiteState failed. ", a.cause()));
				errorSiteState(listSiteState.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<SiteState> putcopySiteStateFuture(SiteRequestEnUS siteRequest, JsonObject jsonObject, Handler<AsyncResult<SiteState>> eventHandler) {
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

			sqlConnectionSiteState(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionSiteState(siteRequest, b -> {
						if(b.succeeded()) {
							createSiteState(siteRequest, c -> {
								if(c.succeeded()) {
									SiteState siteState = c.result();
									sqlPUTCopySiteState(siteState, jsonObject, d -> {
										if(d.succeeded()) {
											defineIndexSiteState(siteState, e -> {
												if(e.succeeded()) {
													ApiRequest apiRequest = siteRequest.getApiRequest_();
													if(apiRequest != null) {
														apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
														if(apiRequest.getNumFound() == 1L) {
															siteState.apiRequestSiteState();
														}
														siteRequest.getVertx().eventBus().publish("websocketSiteState", JsonObject.mapFrom(apiRequest).toString());
													}
													eventHandler.handle(Future.succeededFuture(siteState));
													promise.complete(siteState);
												} else {
													LOG.error(String.format("putcopySiteStateFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOG.error(String.format("putcopySiteStateFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOG.error(String.format("putcopySiteStateFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOG.error(String.format("putcopySiteStateFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOG.error(String.format("putcopySiteStateFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("putcopySiteStateFuture failed. "), e);
			errorSiteState(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPUTCopySiteState(SiteState o, JsonObject jsonObject, Handler<AsyncResult<ServiceResponse>> eventHandler) {
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
					case "stateName":
						o2.setStateName(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stateName=$" + num);
						num++;
						bParams.add(o2.sqlStateName());
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
					case "imageLeft":
						o2.setImageLeft(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("imageLeft=$" + num);
						num++;
						bParams.add(o2.sqlImageLeft());
						break;
					case "imageTop":
						o2.setImageTop(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("imageTop=$" + num);
						num++;
						bParams.add(o2.sqlImageTop());
						break;
					case "agencyKeys":
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
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture());
				} else {
					LOG.error(String.format("sqlPUTCopySiteState failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPUTCopySiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void putcopySiteStateResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PUTCopySiteState(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("putcopySiteStateResponse failed. ", a.cause()));
					errorSiteState(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("putcopySiteStateResponse failed. ", ex));
			errorSiteState(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTCopySiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTCopySiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// POST //

	@Override
	public void postSiteState(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("postSiteState started. "));
		userSiteState(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
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
						siteRequest.getVertx().eventBus().publish("websocketSiteState", JsonObject.mapFrom(apiRequest).toString());
						postSiteStateFuture(siteRequest, false, c -> {
							if(c.succeeded()) {
								SiteState siteState = c.result();
								apiRequest.setPk(siteState.getPk());
								postSiteStateResponse(siteState, d -> {
										if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("postSiteState succeeded. "));
									} else {
										LOG.error(String.format("postSiteState failed. ", d.cause()));
										errorSiteState(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("postSiteState failed. ", c.cause()));
								errorSiteState(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("postSiteState failed. ", ex));
					errorSiteState(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("postSiteState failed. ", b.cause()));
				errorSiteState(null, eventHandler, b);
			}
		});
	}


	public Future<SiteState> postSiteStateFuture(SiteRequestEnUS siteRequest, Boolean inheritPk, Handler<AsyncResult<SiteState>> eventHandler) {
		Promise<SiteState> promise = Promise.promise();
		try {
			sqlConnectionSiteState(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionSiteState(siteRequest, b -> {
						if(b.succeeded()) {
							createSiteState(siteRequest, c -> {
								if(c.succeeded()) {
									SiteState siteState = c.result();
									sqlPOSTSiteState(siteState, inheritPk, d -> {
										if(d.succeeded()) {
											defineIndexSiteState(siteState, e -> {
												if(e.succeeded()) {
													ApiRequest apiRequest = siteRequest.getApiRequest_();
													if(apiRequest != null) {
														apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
														siteState.apiRequestSiteState();
														siteRequest.getVertx().eventBus().publish("websocketSiteState", JsonObject.mapFrom(apiRequest).toString());
													}
													eventHandler.handle(Future.succeededFuture(siteState));
													promise.complete(siteState);
												} else {
													LOG.error(String.format("postSiteStateFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOG.error(String.format("postSiteStateFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOG.error(String.format("postSiteStateFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOG.error(String.format("postSiteStateFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOG.error(String.format("postSiteStateFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("postSiteStateFuture failed. "), e);
			errorSiteState(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPOSTSiteState(SiteState o, Boolean inheritPk, Handler<AsyncResult<ServiceResponse>> eventHandler) {
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
					case "stateName":
						o2.setStateName(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("stateName=$" + num);
						num++;
						bParams.add(o2.sqlStateName());
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
					case "imageLeft":
						o2.setImageLeft(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("imageLeft=$" + num);
						num++;
						bParams.add(o2.sqlImageLeft());
						break;
					case "imageTop":
						o2.setImageTop(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("imageTop=$" + num);
						num++;
						bParams.add(o2.sqlImageTop());
						break;
					case "agencyKeys":
						for(Long l : Optional.ofNullable(jsonObject.getJsonArray(entityVar)).orElse(new JsonArray()).stream().map(a -> Long.parseLong((String)a)).collect(Collectors.toList())) {
							if(l != null) {
								SearchList<com.opendatapolicing.enus.agency.SiteAgency> searchList = new SearchList<com.opendatapolicing.enus.agency.SiteAgency>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(com.opendatapolicing.enus.agency.SiteAgency.class);
								searchList.addFilterQuery("deleted_indexed_boolean:false");
								searchList.addFilterQuery("archived_indexed_boolean:false");
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE SiteAgency SET stateKey=$1 WHERE pk=$2")
												.execute(Tuple.of(pk, l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value SiteState.agencyKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("SiteAgency");
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
					LOG.error(String.format("sqlPOSTSiteState failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPOSTSiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void postSiteStateResponse(SiteState siteState, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = siteState.getSiteRequest_();
		try {
			response200POSTSiteState(siteState, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("postSiteStateResponse failed. ", a.cause()));
					errorSiteState(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("postSiteStateResponse failed. ", ex));
			errorSiteState(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200POSTSiteState(SiteState o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200POSTSiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchSiteState(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("patchSiteState started. "));
		userSiteState(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
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
						patchSiteStateResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											aSearchSiteState(siteRequest, false, true, true, "/api/state", "PATCH", d -> {
												if(d.succeeded()) {
													SearchList<SiteState> listSiteState = d.result();

													List<String> roles2 = Arrays.asList("SiteAdmin");
													if(listSiteState.getQueryResponse().getResults().getNumFound() > 1
															&& !CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles2)
															&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles2)
															) {
														String message = String.format("roles required: " + String.join(", ", roles2));
														LOG.error(message);
														errorSiteState(siteRequest, eventHandler, Future.failedFuture(message));
													} else {

														ApiRequest apiRequest = new ApiRequest();
														apiRequest.setRows(listSiteState.getRows());
														apiRequest.setNumFound(listSiteState.getQueryResponse().getResults().getNumFound());
														apiRequest.setNumPATCH(0L);
														apiRequest.initDeepApiRequest(siteRequest);
														siteRequest.setApiRequest_(apiRequest);
														siteRequest.getVertx().eventBus().publish("websocketSiteState", JsonObject.mapFrom(apiRequest).toString());
														SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listSiteState.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(null);
														Date date = null;
														if(facets != null)
														date = (Date)facets.get("max_modified");
														String dt;
														if(date == null)
															dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));
														else
															dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")));
														listSiteState.addFilterQuery(String.format("modified_indexed_date:[* TO %s]", dt));

														try {
															listPATCHSiteState(apiRequest, listSiteState, dt, e -> {
																if(e.succeeded()) {
																	patchSiteStateResponse(siteRequest, f -> {
																		if(f.succeeded()) {
																			LOG.info(String.format("patchSiteState succeeded. "));
																			blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																		} else {
																			LOG.error(String.format("patchSiteState failed. ", f.cause()));
																			errorSiteState(siteRequest, null, f);
																		}
																	});
																} else {
																	LOG.error(String.format("patchSiteState failed. ", e.cause()));
																	errorSiteState(siteRequest, null, e);
																}
															});
														} catch(Exception ex) {
															LOG.error(String.format("patchSiteState failed. ", ex));
															errorSiteState(siteRequest, null, Future.failedFuture(ex));
														}
													}
										} else {
													LOG.error(String.format("patchSiteState failed. ", d.cause()));
													errorSiteState(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("patchSiteState failed. ", ex));
											errorSiteState(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("patchSiteState failed. ", c.cause()));
								errorSiteState(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("patchSiteState failed. ", ex));
					errorSiteState(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("patchSiteState failed. ", b.cause()));
				errorSiteState(null, eventHandler, b);
			}
		});
	}


	public void listPATCHSiteState(ApiRequest apiRequest, SearchList<SiteState> listSiteState, String dt, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listSiteState.getSiteRequest_();
		listSiteState.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSiteState(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				patchSiteStateFuture(o, false, a -> {
					if(a.succeeded()) {
					} else {
						errorSiteState(siteRequest2, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).onComplete( a -> {
			if(a.succeeded()) {
				if(listSiteState.next(dt)) {
					listPATCHSiteState(apiRequest, listSiteState, dt, eventHandler);
				} else {
					response200PATCHSiteState(siteRequest, eventHandler);
				}
			} else {
				LOG.error(String.format("listPATCHSiteState failed. ", a.cause()));
				errorSiteState(listSiteState.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<SiteState> patchSiteStateFuture(SiteState o, Boolean inheritPk, Handler<AsyncResult<SiteState>> eventHandler) {
		Promise<SiteState> promise = Promise.promise();
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			if(apiRequest != null && apiRequest.getNumFound() == 1L) {
				apiRequest.setOriginal(o);
				apiRequest.setPk(o.getPk());
			}
			sqlConnectionSiteState(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionSiteState(siteRequest, b -> {
						if(b.succeeded()) {
							sqlPATCHSiteState(o, inheritPk, c -> {
								if(c.succeeded()) {
									SiteState siteState = c.result();
									defineIndexSiteState(siteState, d -> {
										if(d.succeeded()) {
											if(apiRequest != null) {
												apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
												if(apiRequest.getNumFound() == 1L) {
													siteState.apiRequestSiteState();
												}
												siteRequest.getVertx().eventBus().publish("websocketSiteState", JsonObject.mapFrom(apiRequest).toString());
											}
											eventHandler.handle(Future.succeededFuture(siteState));
											promise.complete(siteState);
										} else {
											LOG.error(String.format("patchSiteStateFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOG.error(String.format("patchSiteStateFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOG.error(String.format("patchSiteStateFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOG.error(String.format("patchSiteStateFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("patchSiteStateFuture failed. "), e);
			errorSiteState(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPATCHSiteState(SiteState o, Boolean inheritPk, Handler<AsyncResult<SiteState>> eventHandler) {
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
					case "setStateName":
							o2.setStateName(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("stateName=$" + num);
							num++;
							bParams.add(o2.sqlStateName());
						break;
					case "setStateAbbreviation":
							o2.setStateAbbreviation(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("stateAbbreviation=$" + num);
							num++;
							bParams.add(o2.sqlStateAbbreviation());
						break;
					case "setImageLeft":
							o2.setImageLeft(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("imageLeft=$" + num);
							num++;
							bParams.add(o2.sqlImageLeft());
						break;
					case "setImageTop":
							o2.setImageTop(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("imageTop=$" + num);
							num++;
							bParams.add(o2.sqlImageTop());
						break;
					case "addAgencyKeys":
						{
							Long l = Long.parseLong(jsonObject.getString(methodName));
							if(l != null) {
								SearchList<com.opendatapolicing.enus.agency.SiteAgency> searchList = new SearchList<com.opendatapolicing.enus.agency.SiteAgency>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(com.opendatapolicing.enus.agency.SiteAgency.class);
								searchList.addFilterQuery("deleted_indexed_boolean:false");
								searchList.addFilterQuery("archived_indexed_boolean:false");
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && !o.getAgencyKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE SiteAgency SET stateKey=$1 WHERE pk=$2")
												.execute(Tuple.of(pk, l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value SiteState.agencyKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("SiteAgency");
									}
								}
							}
						}
						break;
					case "addAllAgencyKeys":
						JsonArray addAllAgencyKeysValues = jsonObject.getJsonArray(methodName);
						if(addAllAgencyKeysValues != null) {
							for(Integer i = 0; i <  addAllAgencyKeysValues.size(); i++) {
								Long l = Long.parseLong(addAllAgencyKeysValues.getString(i));
								if(l != null) {
									SearchList<com.opendatapolicing.enus.agency.SiteAgency> searchList = new SearchList<com.opendatapolicing.enus.agency.SiteAgency>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(com.opendatapolicing.enus.agency.SiteAgency.class);
									searchList.addFilterQuery("deleted_indexed_boolean:false");
									searchList.addFilterQuery("archived_indexed_boolean:false");
									searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null && !o.getAgencyKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE SiteAgency SET stateKey=$1 WHERE pk=$2")
												.execute(Tuple.of(pk, l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value SiteState.agencyKeys failed", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("SiteAgency");
										}
									}
								}
							}
						}
						break;
					case "setAgencyKeys":
						JsonArray setAgencyKeysValues = jsonObject.getJsonArray(methodName);
						JsonArray setAgencyKeysValues2 = new JsonArray();
						if(setAgencyKeysValues != null) {
							for(Integer i = 0; i <  setAgencyKeysValues.size(); i++) {
								Long l = Long.parseLong(setAgencyKeysValues.getString(i));
								if(l != null) {
									SearchList<com.opendatapolicing.enus.agency.SiteAgency> searchList = new SearchList<com.opendatapolicing.enus.agency.SiteAgency>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(com.opendatapolicing.enus.agency.SiteAgency.class);
									searchList.addFilterQuery("deleted_indexed_boolean:false");
									searchList.addFilterQuery("archived_indexed_boolean:false");
									searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
									searchList.initDeepSearchList(siteRequest);
									Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
									if(l2 != null)
										setAgencyKeysValues2.add(l2);
									if(l2 != null && !o.getAgencyKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE SiteAgency SET stateKey=$1 WHERE pk=$2")
												.execute(Tuple.of(pk, l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value SiteState.agencyKeys failed", b.cause())));
										});
									}));
										if(!pks.contains(l2)) {
											pks.add(l2);
											classes.add("SiteAgency");
										}
									}
								}
							}
						}
						if(o.getAgencyKeys() != null) {
							for(Long l :  o.getAgencyKeys()) {
								if(l != null && (setAgencyKeysValues2 == null || !setAgencyKeysValues2.contains(l))) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE SiteAgency SET stateKey=null WHERE pk=$1")
												.execute(Tuple.of(l)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value SiteState.agencyKeys failed", b.cause())));
										});
									}));
								}
							}
						}
						break;
					case "removeAgencyKeys":
						{
							Long l = Long.parseLong(jsonObject.getString(methodName));
							if(l != null) {
								SearchList<com.opendatapolicing.enus.agency.SiteAgency> searchList = new SearchList<com.opendatapolicing.enus.agency.SiteAgency>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(com.opendatapolicing.enus.agency.SiteAgency.class);
								searchList.addFilterQuery("deleted_indexed_boolean:false");
								searchList.addFilterQuery("archived_indexed_boolean:false");
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null && o.getAgencyKeys().contains(l2)) {
									futures.add(Future.future(a -> {
										sqlConnection.preparedQuery("UPDATE SiteAgency SET stateKey=null WHERE pk=$1")
												.execute(Tuple.of(l2)
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value SiteState.agencyKeys failed", b.cause())));
										});
									}));
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("SiteAgency");
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
					SiteState o3 = new SiteState();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
					eventHandler.handle(Future.succeededFuture(o3));
				} else {
					LOG.error(String.format("sqlPATCHSiteState failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPATCHSiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void patchSiteStateResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PATCHSiteState(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("patchSiteStateResponse failed. ", a.cause()));
					errorSiteState(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("patchSiteStateResponse failed. ", ex));
			errorSiteState(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PATCHSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PATCHSiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// GET //

	@Override
	public void getSiteState(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		userSiteState(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/state/{id}");
					siteRequest.setRequestMethod("GET");
					{
						aSearchSiteState(siteRequest, false, true, false, "/api/state/{id}", "GET", c -> {
							if(c.succeeded()) {
								SearchList<SiteState> listSiteState = c.result();
								getSiteStateResponse(listSiteState, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("getSiteState succeeded. "));
									} else {
										LOG.error(String.format("getSiteState failed. ", d.cause()));
										errorSiteState(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("getSiteState failed. ", c.cause()));
								errorSiteState(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("getSiteState failed. ", ex));
					errorSiteState(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("getSiteState failed. ", b.cause()));
				errorSiteState(null, eventHandler, b);
			}
		});
	}


	public void getSiteStateResponse(SearchList<SiteState> listSiteState, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSiteState.getSiteRequest_();
		try {
			response200GETSiteState(listSiteState, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("getSiteStateResponse failed. ", a.cause()));
					errorSiteState(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("getSiteStateResponse failed. ", ex));
			errorSiteState(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200GETSiteState(SearchList<SiteState> listSiteState, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSiteState.getSiteRequest_();
			SolrDocumentList solrDocuments = listSiteState.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listSiteState.getList().stream().findFirst().orElse(null));
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200GETSiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// Search //

	@Override
	public void searchSiteState(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		userSiteState(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/state");
					siteRequest.setRequestMethod("Search");
					{
						aSearchSiteState(siteRequest, false, true, false, "/api/state", "Search", c -> {
							if(c.succeeded()) {
								SearchList<SiteState> listSiteState = c.result();
								searchSiteStateResponse(listSiteState, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("searchSiteState succeeded. "));
									} else {
										LOG.error(String.format("searchSiteState failed. ", d.cause()));
										errorSiteState(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("searchSiteState failed. ", c.cause()));
								errorSiteState(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("searchSiteState failed. ", ex));
					errorSiteState(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("searchSiteState failed. ", b.cause()));
				errorSiteState(null, eventHandler, b);
			}
		});
	}


	public void searchSiteStateResponse(SearchList<SiteState> listSiteState, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSiteState.getSiteRequest_();
		try {
			response200SearchSiteState(listSiteState, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("searchSiteStateResponse failed. ", a.cause()));
					errorSiteState(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("searchSiteStateResponse failed. ", ex));
			errorSiteState(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchSiteState(SearchList<SiteState> listSiteState, Handler<AsyncResult<ServiceResponse>> eventHandler) {
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
					responsePivotSearchSiteState(pivotFields, pivotArray);
				}
			}
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200SearchSiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
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
				responsePivotSearchSiteState(pivotFields2, pivotArray2);
			}
		}
	}

	// AdminSearch //

	@Override
	public void adminsearchSiteState(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		userSiteState(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/admin/state");
					siteRequest.setRequestMethod("AdminSearch");
					{
						aSearchSiteState(siteRequest, false, true, false, "/api/admin/state", "AdminSearch", c -> {
							if(c.succeeded()) {
								SearchList<SiteState> listSiteState = c.result();
								adminsearchSiteStateResponse(listSiteState, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("adminsearchSiteState succeeded. "));
									} else {
										LOG.error(String.format("adminsearchSiteState failed. ", d.cause()));
										errorSiteState(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("adminsearchSiteState failed. ", c.cause()));
								errorSiteState(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("adminsearchSiteState failed. ", ex));
					errorSiteState(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("adminsearchSiteState failed. ", b.cause()));
				errorSiteState(null, eventHandler, b);
			}
		});
	}


	public void adminsearchSiteStateResponse(SearchList<SiteState> listSiteState, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSiteState.getSiteRequest_();
		try {
			response200AdminSearchSiteState(listSiteState, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("adminsearchSiteStateResponse failed. ", a.cause()));
					errorSiteState(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("adminsearchSiteStateResponse failed. ", ex));
			errorSiteState(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200AdminSearchSiteState(SearchList<SiteState> listSiteState, Handler<AsyncResult<ServiceResponse>> eventHandler) {
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
					responsePivotAdminSearchSiteState(pivotFields, pivotArray);
				}
			}
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200AdminSearchSiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
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
				responsePivotAdminSearchSiteState(pivotFields2, pivotArray2);
			}
		}
	}

	// SearchPage //

	@Override
	public void searchpageSiteStateId(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		searchpageSiteState(serviceRequest, eventHandler);
	}

	@Override
	public void searchpageSiteState(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		userSiteState(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/state");
					siteRequest.setRequestMethod("SearchPage");
					{
						aSearchSiteState(siteRequest, false, true, false, "/state", "SearchPage", c -> {
							if(c.succeeded()) {
								SearchList<SiteState> listSiteState = c.result();
								searchpageSiteStateResponse(listSiteState, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("searchpageSiteState succeeded. "));
									} else {
										LOG.error(String.format("searchpageSiteState failed. ", d.cause()));
										errorSiteState(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("searchpageSiteState failed. ", c.cause()));
								errorSiteState(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("searchpageSiteState failed. ", ex));
					errorSiteState(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("searchpageSiteState failed. ", b.cause()));
				errorSiteState(null, eventHandler, b);
			}
		});
	}


	public void searchpageSiteStatePageInit(SiteStatePage page, SearchList<SiteState> listSiteState) {
	}
	public void searchpageSiteStateResponse(SearchList<SiteState> listSiteState, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSiteState.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200SearchPageSiteState(listSiteState, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("searchpageSiteStateResponse failed. ", a.cause()));
					errorSiteState(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("searchpageSiteStateResponse failed. ", ex));
			errorSiteState(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchPageSiteState(SearchList<SiteState> listSiteState, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSiteState.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listSiteState.getSiteRequest_(), buffer);
			SiteStatePage page = new SiteStatePage();
			SolrDocument pageSolrDocument = new SolrDocument();
			MultiMap requestHeaders = MultiMap.caseInsensitiveMultiMap();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/state");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listSiteState.size() == 1)
				siteRequest.setRequestPk(listSiteState.get(0).getPk());
			siteRequest.setW(w);
			page.setListSiteState(listSiteState);
			page.setSiteRequest_(siteRequest);
			searchpageSiteStatePageInit(page, listSiteState);
			page.initDeepSiteStatePage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new ServiceResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOG.error(String.format("response200SearchPageSiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// General //

	public Future<SiteState> defineIndexSiteState(SiteState siteState, Handler<AsyncResult<SiteState>> eventHandler) {
		Promise<SiteState> promise = Promise.promise();
		SiteRequestEnUS siteRequest = siteState.getSiteRequest_();
		defineSiteState(siteState, c -> {
			if(c.succeeded()) {
				attributeSiteState(siteState, d -> {
					if(d.succeeded()) {
						indexSiteState(siteState, e -> {
							if(e.succeeded()) {
								sqlCommitSiteState(siteRequest, f -> {
									if(f.succeeded()) {
										sqlCloseSiteState(siteRequest, g -> {
											if(g.succeeded()) {
												refreshSiteState(siteState, h -> {
													if(h.succeeded()) {
														eventHandler.handle(Future.succeededFuture(siteState));
														promise.complete(siteState);
													} else {
														LOG.error(String.format("refreshSiteState failed. ", h.cause()));
														errorSiteState(siteRequest, null, h);
													}
												});
											} else {
												LOG.error(String.format("defineIndexSiteState failed. ", g.cause()));
												errorSiteState(siteRequest, null, g);
											}
										});
									} else {
										LOG.error(String.format("defineIndexSiteState failed. ", f.cause()));
										errorSiteState(siteRequest, null, f);
									}
								});
							} else {
								LOG.error(String.format("defineIndexSiteState failed. ", e.cause()));
								errorSiteState(siteRequest, null, e);
							}
						});
					} else {
						LOG.error(String.format("defineIndexSiteState failed. ", d.cause()));
						errorSiteState(siteRequest, null, d);
					}
				});
			} else {
				LOG.error(String.format("defineIndexSiteState failed. ", c.cause()));
				errorSiteState(siteRequest, null, c);
			}
		});
		return promise.future();
	}

	public void createSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<SiteState>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			String userId = siteRequest.getUserId();
			Long userKey = siteRequest.getUserKey();
			ZonedDateTime created = Optional.ofNullable(siteRequest.getJsonObject()).map(j -> j.getString("created")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())))).orElse(ZonedDateTime.now(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())));

			sqlConnection.preparedQuery("INSERT INTO SiteState(created, userKey) VALUES($1, $2) RETURNING pk")
					.collecting(Collectors.toList())
					.execute(Tuple.of(created.toOffsetDateTime(), userKey)
					, createAsync
			-> {
				if(createAsync.succeeded()) {
					Row createLine = createAsync.result().value().stream().findFirst().orElseGet(() -> null);
					Long pk = createLine.getLong(0);
					SiteState o = new SiteState();
					o.setPk(pk);
					o.setSiteRequest_(siteRequest);
					eventHandler.handle(Future.succeededFuture(o));
				} else {
					LOG.error(String.format("createSiteState failed. ", createAsync.cause()));
					eventHandler.handle(Future.failedFuture(createAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("createSiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void errorSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler, AsyncResult<?> resultAsync) {
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
			sqlRollbackSiteState(siteRequest, a -> {
				if(a.succeeded()) {
					LOG.info(String.format("sql rollback. "));
					sqlCloseSiteState(siteRequest, b -> {
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

	public void sqlConnectionSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
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
						LOG.error(String.format("sqlConnectionSiteState failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlSiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlTransactionSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();

			if(sqlConnection == null) {
				eventHandler.handle(Future.failedFuture("sqlTransactionCloseSiteState failed, connection should not be null. "));
			} else {
				sqlConnection.begin(a -> {
					Transaction tx = a.result();
					siteRequest.setTx(tx);
					eventHandler.handle(Future.succeededFuture());
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlTransactionSiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCommitSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();

			if(tx == null) {
				eventHandler.handle(Future.failedFuture("sqlCommitCloseSiteState failed, tx should not be null. "));
			} else {
				tx.commit(a -> {
					if(a.succeeded()) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else if("Transaction already completed".equals(a.cause().getMessage())) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOG.error(String.format("sqlCommitSiteState failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlSiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlRollbackSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();

			if(tx == null) {
				eventHandler.handle(Future.failedFuture("sqlRollbackCloseSiteState failed, tx should not be null. "));
			} else {
				tx.rollback(a -> {
					if(a.succeeded()) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else if("Transaction already completed".equals(a.cause().getMessage())) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOG.error(String.format("sqlRollbackSiteState failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlSiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCloseSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();

			if(sqlConnection == null) {
				eventHandler.handle(Future.failedFuture("sqlCloseSiteState failed, connection should not be null. "));
			} else {
				sqlConnection.close(a -> {
					if(a.succeeded()) {
						siteRequest.setSqlConnection(null);
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOG.error(String.format("sqlCloseSiteState failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlCloseSiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SiteRequestEnUS generateSiteRequestEnUSForSiteState(User user, SiteContextEnUS siteContext, ServiceRequest serviceRequest) {
		return generateSiteRequestEnUSForSiteState(user, siteContext, serviceRequest, null);
	}

	public SiteRequestEnUS generateSiteRequestEnUSForSiteState(User user, SiteContextEnUS siteContext, ServiceRequest serviceRequest, JsonObject body) {
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

	public void userSiteState(ServiceRequest serviceRequest, Handler<AsyncResult<SiteRequestEnUS>> eventHandler) {
		try {
			JsonObject userJson = serviceRequest.getUser();
			if(userJson == null) {
				SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSiteState(null, siteContext, serviceRequest);
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
								SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSiteState(user, siteContext, serviceRequest);
								sqlConnectionSiteState(siteRequest, c -> {
									if(c.succeeded()) {
										sqlTransactionSiteState(siteRequest, d -> {
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
																userSiteStateDefine(siteRequest, jsonObject, false);

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
																						errorSiteState(siteRequest, null, g);
																					}
																				});
																			} else {
																				errorSiteState(siteRequest, null, f);
																			}
																		});
																	} else {
																		errorSiteState(siteRequest, null, e);
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
																Boolean define = userSiteStateDefine(siteRequest, jsonObject, true);
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
																					errorSiteState(siteRequest, null, f);
																				}
																			});
																		} else {
																			errorSiteState(siteRequest, null, e);
																		}
																	});
																} else {
																	siteRequest.setSiteUser(siteUser1);
																	siteRequest.setUserName(siteUser1.getUserName());
																	siteRequest.setUserFirstName(siteUser1.getUserFirstName());
																	siteRequest.setUserLastName(siteUser1.getUserLastName());
																	siteRequest.setUserKey(siteUser1.getPk());
																	sqlRollbackSiteState(siteRequest, e -> {
																		if(e.succeeded()) {
																			eventHandler.handle(Future.succeededFuture(siteRequest));
																		} else {
																			eventHandler.handle(Future.failedFuture(e.cause()));
																			errorSiteState(siteRequest, null, e);
																		}
																	});
																}
															}
														} catch(Exception ex) {
															LOG.error(String.format("userSiteState failed. "), ex);
															eventHandler.handle(Future.failedFuture(ex));
														}
													} else {
														LOG.error(String.format("userSiteState failed. ", selectCAsync.cause()));
														eventHandler.handle(Future.failedFuture(selectCAsync.cause()));
													}
												});
											} else {
												LOG.error(String.format("userSiteState failed. ", d.cause()));
												eventHandler.handle(Future.failedFuture(d.cause()));
											}
										});
									} else {
										LOG.error(String.format("userSiteState failed. ", c.cause()));
										eventHandler.handle(Future.failedFuture(c.cause()));
									}
								});
							} else {
								LOG.error(String.format("userSiteState failed. ", b.cause()));
								eventHandler.handle(Future.failedFuture(b.cause()));
							}
						});
					} else {
						siteContext.getOauth2AuthenticationProvider().refresh(token, b -> {
							if(b.succeeded()) {
								User user = b.result();
								serviceRequest.setUser(user.principal());
								userSiteState(serviceRequest, c -> {
									if(c.succeeded()) {
										SiteRequestEnUS siteRequest = c.result();
										eventHandler.handle(Future.succeededFuture(siteRequest));
									} else {
										LOG.error(String.format("userSiteState failed. ", c.cause()));
										eventHandler.handle(Future.failedFuture(c.cause()));
									}
								});
							} else {
								LOG.error(String.format("userSiteState failed. ", a.cause()));
								eventHandler.handle(Future.failedFuture(a.cause()));
							}
						});
					}
				});
			}
		} catch(Exception ex) {
			LOG.error(String.format("userSiteState failed. "), ex);
			eventHandler.handle(Future.failedFuture(ex));
		}
	}

	public Boolean userSiteStateDefine(SiteRequestEnUS siteRequest, JsonObject jsonObject, Boolean patch) {
		if(patch) {
			return false;
		} else {
			return false;
		}
	}

	public void aSearchSiteStateQ(String uri, String apiMethod, SearchList<SiteState> searchList, String entityVar, String valueIndexed, String varIndexed) {
		searchList.setQuery(varIndexed + ":" + ("*".equals(valueIndexed) ? valueIndexed : ClientUtils.escapeQueryChars(valueIndexed)));
		if(!"*".equals(entityVar)) {
		}
	}

	public String aSearchSiteStateFq(String uri, String apiMethod, SearchList<SiteState> searchList, String entityVar, String valueIndexed, String varIndexed) {
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

	public void aSearchSiteStateSort(String uri, String apiMethod, SearchList<SiteState> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addSort(varIndexed, ORDER.valueOf(valueIndexed));
	}

	public void aSearchSiteStateRows(String uri, String apiMethod, SearchList<SiteState> searchList, Integer valueRows) {
			searchList.setRows(apiMethod != null && apiMethod.contains("Search") ? valueRows : 10);
	}

	public void aSearchSiteStateStart(String uri, String apiMethod, SearchList<SiteState> searchList, Integer valueStart) {
		searchList.setStart(valueStart);
	}

	public void aSearchSiteStateVar(String uri, String apiMethod, SearchList<SiteState> searchList, String var, String value) {
		searchList.getSiteRequest_().getRequestVars().put(var, value);
	}

	public void aSearchSiteStateUri(String uri, String apiMethod, SearchList<SiteState> searchList) {
	}

	public void varsSiteState(SiteRequestEnUS siteRequest, Handler<AsyncResult<SearchList<ServiceResponse>>> eventHandler) {
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
					LOG.error(String.format("aSearchSiteState failed. "), e);
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOG.error(String.format("aSearchSiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void aSearchSiteState(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, Handler<AsyncResult<SearchList<SiteState>>> eventHandler) {
		try {
			SearchList<SiteState> searchList = aSearchSiteStateList(siteRequest, populate, store, modify, uri, apiMethod);
			eventHandler.handle(Future.succeededFuture(searchList));
		} catch(Exception e) {
			LOG.error(String.format("aSearchSiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SearchList<SiteState> aSearchSiteStateList(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod) {
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
								aSearchSiteStateQ(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
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
										String entityFq = aSearchSiteStateFq(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
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
								aSearchSiteStateSort(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "start":
								valueStart = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
								aSearchSiteStateStart(uri, apiMethod, searchList, valueStart);
								break;
							case "rows":
								valueRows = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
								aSearchSiteStateRows(uri, apiMethod, searchList, valueRows);
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
								aSearchSiteStateVar(uri, apiMethod, searchList, entityVar, valueIndexed);
								break;
						}
					}
					aSearchSiteStateUri(uri, apiMethod, searchList);
				}
			} catch(Exception e) {
				ExceptionUtils.rethrow(e);
			}
		});
		if("*:*".equals(searchList.getQuery()) && searchList.getSorts().size() == 0) {
			searchList.addSort("stateName_indexed_string", ORDER.asc);
		}
		aSearchSiteState2(siteRequest, populate, store, modify, uri, apiMethod, searchList);
		searchList.initDeepForClass(siteRequest);
		return searchList;
	}
	public void aSearchSiteState2(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, SearchList<SiteState> searchList) {
	}

	public void defineSiteState(SiteState o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT * FROM SiteState WHERE pk=$1")
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
										LOG.error(String.format("defineSiteState failed. "), e);
									}
								}
							}
						}
						eventHandler.handle(Future.succeededFuture());
					} catch(Exception e) {
						LOG.error(String.format("defineSiteState failed. "), e);
						eventHandler.handle(Future.failedFuture(e));
					}
				} else {
					LOG.error(String.format("defineSiteState failed. ", defineAsync.cause()));
					eventHandler.handle(Future.failedFuture(defineAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("defineSiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void attributeSiteState(SiteState o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT pk as pk2, 'agencyKeys' from SiteAgency where stateKey=$1")
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
						LOG.error(String.format("attributeSiteState failed. ", attributeAsync.cause()));
						eventHandler.handle(Future.failedFuture(attributeAsync.cause()));
					}
				} catch(Exception e) {
					LOG.error(String.format("attributeSiteState failed. "), e);
					eventHandler.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("attributeSiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void indexSiteState(SiteState o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			o.initDeepForClass(siteRequest);
			o.indexForClass();
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOG.error(String.format("indexSiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void refreshSiteState(SiteState o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Boolean refresh = !"false".equals(siteRequest.getRequestVars().get("refresh"));
			if(refresh && BooleanUtils.isFalse(Optional.ofNullable(siteRequest.getApiRequest_()).map(ApiRequest::getEmpty).orElse(true))) {
				SearchList<SiteState> searchList = new SearchList<SiteState>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(SiteState.class);
				searchList.addFilterQuery("modified_indexed_date:[" + DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(siteRequest.getApiRequest_().getCreated().toInstant(), ZoneId.of("UTC"))) + " TO *]");
				searchList.add("json.facet", "{agencyKeys:{terms:{field:agencyKeys_indexed_longs, limit:1000}}}");
				searchList.setRows(1000);
				searchList.initDeepSearchList(siteRequest);
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
						searchList2.initDeepSearchList(siteRequest);
						SiteAgency o2 = searchList2.getList().stream().findFirst().orElse(null);

						if(o2 != null) {
							SiteAgencyEnUSGenApiServiceImpl service = new SiteAgencyEnUSGenApiServiceImpl(siteRequest.getSiteContext_());
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSiteState(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), new JsonObject());
							ApiRequest apiRequest2 = new ApiRequest();
							apiRequest2.setRows(1);
							apiRequest2.setNumFound(1l);
							apiRequest2.setNumPATCH(0L);
							apiRequest2.initDeepApiRequest(siteRequest2);
							siteRequest2.setApiRequest_(apiRequest2);
							siteRequest2.getVertx().eventBus().publish("websocketSiteAgency", JsonObject.mapFrom(apiRequest2).toString());

							o2.setPk(pk2);
							o2.setSiteRequest_(siteRequest2);
							futures.add(
								service.patchSiteAgencyFuture(o2, false, a -> {
									if(a.succeeded()) {
									} else {
										LOG.info(String.format("SiteAgency %s failed. ", pk2));
										eventHandler.handle(Future.failedFuture(a.cause()));
									}
								})
							);
						}
					}
				}

				CompositeFuture.all(futures).onComplete(a -> {
					if(a.succeeded()) {
						SiteStateEnUSApiServiceImpl service = new SiteStateEnUSApiServiceImpl(siteRequest.getSiteContext_());
						List<Future> futures2 = new ArrayList<>();
						for(SiteState o2 : searchList.getList()) {
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSiteState(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), new JsonObject());
							o2.setSiteRequest_(siteRequest2);
							futures2.add(
								service.patchSiteStateFuture(o2, false, b -> {
									if(b.succeeded()) {
									} else {
										LOG.info(String.format("SiteState %s failed. ", o2.getPk()));
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
								errorSiteState(siteRequest, eventHandler, b);
							}
						});
					} else {
						LOG.error("Refresh relations failed. ", a.cause());
						errorSiteState(siteRequest, eventHandler, a);
					}
				});
			} else {
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOG.error(String.format("refreshSiteState failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}
}
