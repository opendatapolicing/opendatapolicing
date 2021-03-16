package com.opendatapolicing.enus.searchbasis;

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
public class SearchBasisEnUSGenApiServiceImpl implements SearchBasisEnUSGenApiService {

	protected static final Logger LOG = LoggerFactory.getLogger(SearchBasisEnUSGenApiServiceImpl.class);

	protected static final String SERVICE_ADDRESS = "SearchBasisEnUSApiServiceImpl";

	protected SiteContextEnUS siteContext;

	public SearchBasisEnUSGenApiServiceImpl(SiteContextEnUS siteContext) {
		this.siteContext = siteContext;
	}

	// PUTImport //

	@Override
	public void putimportSearchBasis(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("putimportSearchBasis started. "));
		userSearchBasis(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/search-basis/import");
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
						putimportSearchBasisResponse(siteRequest, c -> {
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
											siteRequest.getVertx().eventBus().publish("websocketSearchBasis", JsonObject.mapFrom(apiRequest).toString());
											varsSearchBasis(siteRequest, d -> {
												if(d.succeeded()) {
													listPUTImportSearchBasis(apiRequest, siteRequest, e -> {
														if(e.succeeded()) {
															putimportSearchBasisResponse(siteRequest, f -> {
																if(e.succeeded()) {
																	LOG.info(String.format("putimportSearchBasis succeeded. "));
																	blockingCodeHandler.handle(Future.succeededFuture(e.result()));
																} else {
																	LOG.error(String.format("putimportSearchBasis failed. ", f.cause()));
																	errorSearchBasis(siteRequest, null, f);
																}
															});
														} else {
															LOG.error(String.format("putimportSearchBasis failed. ", e.cause()));
															errorSearchBasis(siteRequest, null, e);
														}
													});
												} else {
													LOG.error(String.format("putimportSearchBasis failed. ", d.cause()));
													errorSearchBasis(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putimportSearchBasis failed. ", ex));
											errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putimportSearchBasis failed. ", c.cause()));
								errorSearchBasis(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putimportSearchBasis failed. ", ex));
					errorSearchBasis(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("putimportSearchBasis failed. ", b.cause()));
				errorSearchBasis(null, eventHandler, b);
			}
		});
	}


	public void listPUTImportSearchBasis(ApiRequest apiRequest, SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				json.put("created", json.getValue("created"));

				SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSearchBasis(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), json);
				siteRequest2.setApiRequest_(apiRequest);
				siteRequest2.setRequestVars(siteRequest.getRequestVars());

				SearchList<SearchBasis> searchList = new SearchList<SearchBasis>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(SearchBasis.class);
				searchList.addFilterQuery("deleted_indexed_boolean:false");
				searchList.addFilterQuery("archived_indexed_boolean:false");
				searchList.addFilterQuery("inheritPk_indexed_long:" + json.getString("pk"));
				searchList.initDeepForClass(siteRequest2);

				if(searchList.size() == 1) {
					SearchBasis o = searchList.getList().stream().findFirst().orElse(null);
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
							patchSearchBasisFuture(o, true, a -> {
								if(a.succeeded()) {
								} else {
									LOG.error(String.format("listPUTImportSearchBasis failed. ", a.cause()));
									errorSearchBasis(siteRequest2, eventHandler, a);
								}
							})
						);
					}
				} else {
					futures.add(
						postSearchBasisFuture(siteRequest2, true, a -> {
							if(a.succeeded()) {
							} else {
								LOG.error(String.format("listPUTImportSearchBasis failed. ", a.cause()));
								errorSearchBasis(siteRequest2, eventHandler, a);
							}
						})
					);
				}
			});
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
					response200PUTImportSearchBasis(siteRequest, eventHandler);
				} else {
					LOG.error(String.format("listPUTImportSearchBasis failed. ", a.cause()));
					errorSearchBasis(apiRequest.getSiteRequest_(), eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("listPUTImportSearchBasis failed. ", ex));
			errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
		}
	}

	public void putimportSearchBasisResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PUTImportSearchBasis(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("putimportSearchBasisResponse failed. ", a.cause()));
					errorSearchBasis(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("putimportSearchBasisResponse failed. ", ex));
			errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTImportSearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTImportSearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTMerge //

	@Override
	public void putmergeSearchBasis(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("putmergeSearchBasis started. "));
		userSearchBasis(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/search-basis/merge");
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
						putmergeSearchBasisResponse(siteRequest, c -> {
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
											siteRequest.getVertx().eventBus().publish("websocketSearchBasis", JsonObject.mapFrom(apiRequest).toString());
											varsSearchBasis(siteRequest, d -> {
												if(d.succeeded()) {
													listPUTMergeSearchBasis(apiRequest, siteRequest, e -> {
														if(e.succeeded()) {
															putmergeSearchBasisResponse(siteRequest, f -> {
																if(e.succeeded()) {
																	LOG.info(String.format("putmergeSearchBasis succeeded. "));
																	blockingCodeHandler.handle(Future.succeededFuture(e.result()));
																} else {
																	LOG.error(String.format("putmergeSearchBasis failed. ", f.cause()));
																	errorSearchBasis(siteRequest, null, f);
																}
															});
														} else {
															LOG.error(String.format("putmergeSearchBasis failed. ", e.cause()));
															errorSearchBasis(siteRequest, null, e);
														}
													});
												} else {
													LOG.error(String.format("putmergeSearchBasis failed. ", d.cause()));
													errorSearchBasis(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putmergeSearchBasis failed. ", ex));
											errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putmergeSearchBasis failed. ", c.cause()));
								errorSearchBasis(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putmergeSearchBasis failed. ", ex));
					errorSearchBasis(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("putmergeSearchBasis failed. ", b.cause()));
				errorSearchBasis(null, eventHandler, b);
			}
		});
	}


	public void listPUTMergeSearchBasis(ApiRequest apiRequest, SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSearchBasis(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), json);
				siteRequest2.setApiRequest_(apiRequest);
				siteRequest2.setRequestVars(siteRequest.getRequestVars());

				SearchList<SearchBasis> searchList = new SearchList<SearchBasis>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(SearchBasis.class);
				searchList.addFilterQuery("deleted_indexed_boolean:false");
				searchList.addFilterQuery("archived_indexed_boolean:false");
				searchList.addFilterQuery("pk_indexed_long:" + json.getString("pk"));
				searchList.initDeepForClass(siteRequest2);

				if(searchList.size() == 1) {
					SearchBasis o = searchList.getList().stream().findFirst().orElse(null);
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
							patchSearchBasisFuture(o, false, a -> {
								if(a.succeeded()) {
								} else {
									LOG.error(String.format("listPUTMergeSearchBasis failed. ", a.cause()));
									errorSearchBasis(siteRequest2, eventHandler, a);
								}
							})
						);
					}
				} else {
					futures.add(
						postSearchBasisFuture(siteRequest2, false, a -> {
							if(a.succeeded()) {
							} else {
								LOG.error(String.format("listPUTMergeSearchBasis failed. ", a.cause()));
								errorSearchBasis(siteRequest2, eventHandler, a);
							}
						})
					);
				}
			});
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
					response200PUTMergeSearchBasis(siteRequest, eventHandler);
				} else {
					LOG.error(String.format("listPUTMergeSearchBasis failed. ", a.cause()));
					errorSearchBasis(apiRequest.getSiteRequest_(), eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("listPUTMergeSearchBasis failed. ", ex));
			errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
		}
	}

	public void putmergeSearchBasisResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PUTMergeSearchBasis(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("putmergeSearchBasisResponse failed. ", a.cause()));
					errorSearchBasis(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("putmergeSearchBasisResponse failed. ", ex));
			errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTMergeSearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTMergeSearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTCopy //

	@Override
	public void putcopySearchBasis(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("putcopySearchBasis started. "));
		userSearchBasis(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/search-basis/copy");
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
						putcopySearchBasisResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											aSearchSearchBasis(siteRequest, false, true, true, "/api/search-basis/copy", "PUTCopy", d -> {
												if(d.succeeded()) {
													SearchList<SearchBasis> listSearchBasis = d.result();
													ApiRequest apiRequest = new ApiRequest();
													apiRequest.setRows(listSearchBasis.getRows());
													apiRequest.setNumFound(listSearchBasis.getQueryResponse().getResults().getNumFound());
													apiRequest.setNumPATCH(0L);
													apiRequest.initDeepApiRequest(siteRequest);
													siteRequest.setApiRequest_(apiRequest);
													siteRequest.getVertx().eventBus().publish("websocketSearchBasis", JsonObject.mapFrom(apiRequest).toString());
													try {
														listPUTCopySearchBasis(apiRequest, listSearchBasis, e -> {
															if(e.succeeded()) {
																putcopySearchBasisResponse(siteRequest, f -> {
																	if(f.succeeded()) {
																		LOG.info(String.format("putcopySearchBasis succeeded. "));
																		blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																	} else {
																		LOG.error(String.format("putcopySearchBasis failed. ", f.cause()));
																		errorSearchBasis(siteRequest, null, f);
																	}
																});
															} else {
																LOG.error(String.format("putcopySearchBasis failed. ", e.cause()));
																errorSearchBasis(siteRequest, null, e);
															}
														});
													} catch(Exception ex) {
														LOG.error(String.format("putcopySearchBasis failed. ", ex));
														errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
													}
												} else {
													LOG.error(String.format("putcopySearchBasis failed. ", d.cause()));
													errorSearchBasis(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putcopySearchBasis failed. ", ex));
											errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putcopySearchBasis failed. ", c.cause()));
								errorSearchBasis(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putcopySearchBasis failed. ", ex));
					errorSearchBasis(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("putcopySearchBasis failed. ", b.cause()));
				errorSearchBasis(null, eventHandler, b);
			}
		});
	}


	public void listPUTCopySearchBasis(ApiRequest apiRequest, SearchList<SearchBasis> listSearchBasis, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listSearchBasis.getSiteRequest_();
		listSearchBasis.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSearchBasis(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				putcopySearchBasisFuture(siteRequest2, JsonObject.mapFrom(o), a -> {
					if(a.succeeded()) {
					} else {
						LOG.error(String.format("listPUTCopySearchBasis failed. ", a.cause()));
						errorSearchBasis(siteRequest, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).onComplete( a -> {
			if(a.succeeded()) {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + listSearchBasis.size());
				if(listSearchBasis.next()) {
					listPUTCopySearchBasis(apiRequest, listSearchBasis, eventHandler);
				} else {
					response200PUTCopySearchBasis(siteRequest, eventHandler);
				}
			} else {
				LOG.error(String.format("listPUTCopySearchBasis failed. ", a.cause()));
				errorSearchBasis(listSearchBasis.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<SearchBasis> putcopySearchBasisFuture(SiteRequestEnUS siteRequest, JsonObject jsonObject, Handler<AsyncResult<SearchBasis>> eventHandler) {
		Promise<SearchBasis> promise = Promise.promise();
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

			sqlConnectionSearchBasis(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionSearchBasis(siteRequest, b -> {
						if(b.succeeded()) {
							createSearchBasis(siteRequest, c -> {
								if(c.succeeded()) {
									SearchBasis searchBasis = c.result();
									sqlPUTCopySearchBasis(searchBasis, jsonObject, d -> {
										if(d.succeeded()) {
											defineIndexSearchBasis(searchBasis, e -> {
												if(e.succeeded()) {
													ApiRequest apiRequest = siteRequest.getApiRequest_();
													if(apiRequest != null) {
														apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
														if(apiRequest.getNumFound() == 1L) {
															searchBasis.apiRequestSearchBasis();
														}
														siteRequest.getVertx().eventBus().publish("websocketSearchBasis", JsonObject.mapFrom(apiRequest).toString());
													}
													eventHandler.handle(Future.succeededFuture(searchBasis));
													promise.complete(searchBasis);
												} else {
													LOG.error(String.format("putcopySearchBasisFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOG.error(String.format("putcopySearchBasisFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOG.error(String.format("putcopySearchBasisFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOG.error(String.format("putcopySearchBasisFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOG.error(String.format("putcopySearchBasisFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("putcopySearchBasisFuture failed. "), e);
			errorSearchBasis(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPUTCopySearchBasis(SearchBasis o, JsonObject jsonObject, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE SearchBasis SET ");
			List<Object> bParams = new ArrayList<Object>();
			SearchBasis o2 = new SearchBasis();
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
					case "searchBasisId":
						o2.setSearchBasisId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("searchBasisId=$" + num);
						num++;
						bParams.add(o2.sqlSearchBasisId());
						break;
					case "searchBasisTitle":
						o2.setSearchBasisTitle(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("searchBasisTitle=$" + num);
						num++;
						bParams.add(o2.sqlSearchBasisTitle());
						break;
					}
				}
			}
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture());
				} else {
					LOG.error(String.format("sqlPUTCopySearchBasis failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPUTCopySearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void putcopySearchBasisResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PUTCopySearchBasis(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("putcopySearchBasisResponse failed. ", a.cause()));
					errorSearchBasis(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("putcopySearchBasisResponse failed. ", ex));
			errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTCopySearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTCopySearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// POST //

	@Override
	public void postSearchBasis(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("postSearchBasis started. "));
		userSearchBasis(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/search-basis");
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
						siteRequest.getVertx().eventBus().publish("websocketSearchBasis", JsonObject.mapFrom(apiRequest).toString());
						postSearchBasisFuture(siteRequest, false, c -> {
							if(c.succeeded()) {
								SearchBasis searchBasis = c.result();
								apiRequest.setPk(searchBasis.getPk());
								postSearchBasisResponse(searchBasis, d -> {
										if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("postSearchBasis succeeded. "));
									} else {
										LOG.error(String.format("postSearchBasis failed. ", d.cause()));
										errorSearchBasis(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("postSearchBasis failed. ", c.cause()));
								errorSearchBasis(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("postSearchBasis failed. ", ex));
					errorSearchBasis(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("postSearchBasis failed. ", b.cause()));
				errorSearchBasis(null, eventHandler, b);
			}
		});
	}


	public Future<SearchBasis> postSearchBasisFuture(SiteRequestEnUS siteRequest, Boolean inheritPk, Handler<AsyncResult<SearchBasis>> eventHandler) {
		Promise<SearchBasis> promise = Promise.promise();
		try {
			sqlConnectionSearchBasis(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionSearchBasis(siteRequest, b -> {
						if(b.succeeded()) {
							createSearchBasis(siteRequest, c -> {
								if(c.succeeded()) {
									SearchBasis searchBasis = c.result();
									sqlPOSTSearchBasis(searchBasis, inheritPk, d -> {
										if(d.succeeded()) {
											defineIndexSearchBasis(searchBasis, e -> {
												if(e.succeeded()) {
													ApiRequest apiRequest = siteRequest.getApiRequest_();
													if(apiRequest != null) {
														apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
														searchBasis.apiRequestSearchBasis();
														siteRequest.getVertx().eventBus().publish("websocketSearchBasis", JsonObject.mapFrom(apiRequest).toString());
													}
													eventHandler.handle(Future.succeededFuture(searchBasis));
													promise.complete(searchBasis);
												} else {
													LOG.error(String.format("postSearchBasisFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOG.error(String.format("postSearchBasisFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOG.error(String.format("postSearchBasisFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOG.error(String.format("postSearchBasisFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOG.error(String.format("postSearchBasisFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("postSearchBasisFuture failed. "), e);
			errorSearchBasis(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPOSTSearchBasis(SearchBasis o, Boolean inheritPk, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE SearchBasis SET ");
			List<Object> bParams = new ArrayList<Object>();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			SearchBasis o2 = new SearchBasis();
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
					case "searchBasisId":
						o2.setSearchBasisId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("searchBasisId=$" + num);
						num++;
						bParams.add(o2.sqlSearchBasisId());
						break;
					case "searchBasisTitle":
						o2.setSearchBasisTitle(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("searchBasisTitle=$" + num);
						num++;
						bParams.add(o2.sqlSearchBasisTitle());
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
					LOG.error(String.format("sqlPOSTSearchBasis failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPOSTSearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void postSearchBasisResponse(SearchBasis searchBasis, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = searchBasis.getSiteRequest_();
		try {
			response200POSTSearchBasis(searchBasis, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("postSearchBasisResponse failed. ", a.cause()));
					errorSearchBasis(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("postSearchBasisResponse failed. ", ex));
			errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200POSTSearchBasis(SearchBasis o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200POSTSearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchSearchBasis(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("patchSearchBasis started. "));
		userSearchBasis(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/search-basis");
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
						patchSearchBasisResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											aSearchSearchBasis(siteRequest, false, true, true, "/api/search-basis", "PATCH", d -> {
												if(d.succeeded()) {
													SearchList<SearchBasis> listSearchBasis = d.result();

													List<String> roles2 = Arrays.asList("SiteAdmin");
													if(listSearchBasis.getQueryResponse().getResults().getNumFound() > 1
															&& !CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles2)
															&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles2)
															) {
														String message = String.format("roles required: " + String.join(", ", roles2));
														LOG.error(message);
														errorSearchBasis(siteRequest, eventHandler, Future.failedFuture(message));
													} else {

														ApiRequest apiRequest = new ApiRequest();
														apiRequest.setRows(listSearchBasis.getRows());
														apiRequest.setNumFound(listSearchBasis.getQueryResponse().getResults().getNumFound());
														apiRequest.setNumPATCH(0L);
														apiRequest.initDeepApiRequest(siteRequest);
														siteRequest.setApiRequest_(apiRequest);
														siteRequest.getVertx().eventBus().publish("websocketSearchBasis", JsonObject.mapFrom(apiRequest).toString());
														SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listSearchBasis.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(null);
														Date date = null;
														if(facets != null)
														date = (Date)facets.get("max_modified");
														String dt;
														if(date == null)
															dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));
														else
															dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")));
														listSearchBasis.addFilterQuery(String.format("modified_indexed_date:[* TO %s]", dt));

														try {
															listPATCHSearchBasis(apiRequest, listSearchBasis, dt, e -> {
																if(e.succeeded()) {
																	patchSearchBasisResponse(siteRequest, f -> {
																		if(f.succeeded()) {
																			LOG.info(String.format("patchSearchBasis succeeded. "));
																			blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																		} else {
																			LOG.error(String.format("patchSearchBasis failed. ", f.cause()));
																			errorSearchBasis(siteRequest, null, f);
																		}
																	});
																} else {
																	LOG.error(String.format("patchSearchBasis failed. ", e.cause()));
																	errorSearchBasis(siteRequest, null, e);
																}
															});
														} catch(Exception ex) {
															LOG.error(String.format("patchSearchBasis failed. ", ex));
															errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
														}
													}
										} else {
													LOG.error(String.format("patchSearchBasis failed. ", d.cause()));
													errorSearchBasis(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("patchSearchBasis failed. ", ex));
											errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("patchSearchBasis failed. ", c.cause()));
								errorSearchBasis(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("patchSearchBasis failed. ", ex));
					errorSearchBasis(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("patchSearchBasis failed. ", b.cause()));
				errorSearchBasis(null, eventHandler, b);
			}
		});
	}


	public void listPATCHSearchBasis(ApiRequest apiRequest, SearchList<SearchBasis> listSearchBasis, String dt, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listSearchBasis.getSiteRequest_();
		listSearchBasis.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSearchBasis(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				patchSearchBasisFuture(o, false, a -> {
					if(a.succeeded()) {
					} else {
						errorSearchBasis(siteRequest2, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).onComplete( a -> {
			if(a.succeeded()) {
				if(listSearchBasis.next(dt)) {
					listPATCHSearchBasis(apiRequest, listSearchBasis, dt, eventHandler);
				} else {
					response200PATCHSearchBasis(siteRequest, eventHandler);
				}
			} else {
				LOG.error(String.format("listPATCHSearchBasis failed. ", a.cause()));
				errorSearchBasis(listSearchBasis.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<SearchBasis> patchSearchBasisFuture(SearchBasis o, Boolean inheritPk, Handler<AsyncResult<SearchBasis>> eventHandler) {
		Promise<SearchBasis> promise = Promise.promise();
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			if(apiRequest != null && apiRequest.getNumFound() == 1L) {
				apiRequest.setOriginal(o);
				apiRequest.setPk(o.getPk());
			}
			sqlConnectionSearchBasis(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionSearchBasis(siteRequest, b -> {
						if(b.succeeded()) {
							sqlPATCHSearchBasis(o, inheritPk, c -> {
								if(c.succeeded()) {
									SearchBasis searchBasis = c.result();
									defineIndexSearchBasis(searchBasis, d -> {
										if(d.succeeded()) {
											if(apiRequest != null) {
												apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
												if(apiRequest.getNumFound() == 1L) {
													searchBasis.apiRequestSearchBasis();
												}
												siteRequest.getVertx().eventBus().publish("websocketSearchBasis", JsonObject.mapFrom(apiRequest).toString());
											}
											eventHandler.handle(Future.succeededFuture(searchBasis));
											promise.complete(searchBasis);
										} else {
											LOG.error(String.format("patchSearchBasisFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOG.error(String.format("patchSearchBasisFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOG.error(String.format("patchSearchBasisFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOG.error(String.format("patchSearchBasisFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("patchSearchBasisFuture failed. "), e);
			errorSearchBasis(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPATCHSearchBasis(SearchBasis o, Boolean inheritPk, Handler<AsyncResult<SearchBasis>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE SearchBasis SET ");
			List<Object> bParams = new ArrayList<Object>();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			SearchBasis o2 = new SearchBasis();
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
					case "setSearchBasisId":
							o2.setSearchBasisId(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("searchBasisId=$" + num);
							num++;
							bParams.add(o2.sqlSearchBasisId());
						break;
					case "setSearchBasisTitle":
							o2.setSearchBasisTitle(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("searchBasisTitle=$" + num);
							num++;
							bParams.add(o2.sqlSearchBasisTitle());
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
					SearchBasis o3 = new SearchBasis();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
					eventHandler.handle(Future.succeededFuture(o3));
				} else {
					LOG.error(String.format("sqlPATCHSearchBasis failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPATCHSearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void patchSearchBasisResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PATCHSearchBasis(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("patchSearchBasisResponse failed. ", a.cause()));
					errorSearchBasis(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("patchSearchBasisResponse failed. ", ex));
			errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PATCHSearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PATCHSearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// GET //

	@Override
	public void getSearchBasis(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		userSearchBasis(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/search-basis/{id}");
					siteRequest.setRequestMethod("GET");
					{
						aSearchSearchBasis(siteRequest, false, true, false, "/api/search-basis/{id}", "GET", c -> {
							if(c.succeeded()) {
								SearchList<SearchBasis> listSearchBasis = c.result();
								getSearchBasisResponse(listSearchBasis, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("getSearchBasis succeeded. "));
									} else {
										LOG.error(String.format("getSearchBasis failed. ", d.cause()));
										errorSearchBasis(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("getSearchBasis failed. ", c.cause()));
								errorSearchBasis(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("getSearchBasis failed. ", ex));
					errorSearchBasis(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("getSearchBasis failed. ", b.cause()));
				errorSearchBasis(null, eventHandler, b);
			}
		});
	}


	public void getSearchBasisResponse(SearchList<SearchBasis> listSearchBasis, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSearchBasis.getSiteRequest_();
		try {
			response200GETSearchBasis(listSearchBasis, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("getSearchBasisResponse failed. ", a.cause()));
					errorSearchBasis(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("getSearchBasisResponse failed. ", ex));
			errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200GETSearchBasis(SearchList<SearchBasis> listSearchBasis, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSearchBasis.getSiteRequest_();
			SolrDocumentList solrDocuments = listSearchBasis.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listSearchBasis.getList().stream().findFirst().orElse(null));
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200GETSearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// Search //

	@Override
	public void searchSearchBasis(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		userSearchBasis(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/search-basis");
					siteRequest.setRequestMethod("Search");
					{
						aSearchSearchBasis(siteRequest, false, true, false, "/api/search-basis", "Search", c -> {
							if(c.succeeded()) {
								SearchList<SearchBasis> listSearchBasis = c.result();
								searchSearchBasisResponse(listSearchBasis, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("searchSearchBasis succeeded. "));
									} else {
										LOG.error(String.format("searchSearchBasis failed. ", d.cause()));
										errorSearchBasis(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("searchSearchBasis failed. ", c.cause()));
								errorSearchBasis(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("searchSearchBasis failed. ", ex));
					errorSearchBasis(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("searchSearchBasis failed. ", b.cause()));
				errorSearchBasis(null, eventHandler, b);
			}
		});
	}


	public void searchSearchBasisResponse(SearchList<SearchBasis> listSearchBasis, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSearchBasis.getSiteRequest_();
		try {
			response200SearchSearchBasis(listSearchBasis, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("searchSearchBasisResponse failed. ", a.cause()));
					errorSearchBasis(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("searchSearchBasisResponse failed. ", ex));
			errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchSearchBasis(SearchList<SearchBasis> listSearchBasis, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSearchBasis.getSiteRequest_();
			QueryResponse responseSearch = listSearchBasis.getQueryResponse();
			SolrDocumentList solrDocuments = listSearchBasis.getSolrDocumentList();
			Long searchInMillis = Long.valueOf(responseSearch.getQTime());
			Long transmissionInMillis = responseSearch.getElapsedTime();
			Long startNum = responseSearch.getResults().getStart();
			Long foundNum = responseSearch.getResults().getNumFound();
			Integer returnedNum = responseSearch.getResults().size();
			String searchTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(searchInMillis), TimeUnit.MILLISECONDS.toMillis(searchInMillis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(searchInMillis)));
			String transmissionTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis), TimeUnit.MILLISECONDS.toMillis(transmissionInMillis) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis)));
			Exception exceptionSearch = responseSearch.getException();
			List<String> fls = listSearchBasis.getFields();

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
				json.put("searchTime", searchTime);
				json.put("transmissionTime", transmissionTime);
			}
			JsonArray l = new JsonArray();
			listSearchBasis.getList().stream().forEach(o -> {
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
					responsePivotSearchSearchBasis(pivotFields, pivotArray);
				}
			}
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200SearchSearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}
	public void responsePivotSearchSearchBasis(List<PivotField> pivotFields, JsonArray pivotArray) {
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
				responsePivotSearchSearchBasis(pivotFields2, pivotArray2);
			}
		}
	}

	// AdminSearch //

	@Override
	public void adminsearchSearchBasis(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		userSearchBasis(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/admin/search-basis");
					siteRequest.setRequestMethod("AdminSearch");
					{
						aSearchSearchBasis(siteRequest, false, true, false, "/api/admin/search-basis", "AdminSearch", c -> {
							if(c.succeeded()) {
								SearchList<SearchBasis> listSearchBasis = c.result();
								adminsearchSearchBasisResponse(listSearchBasis, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("adminsearchSearchBasis succeeded. "));
									} else {
										LOG.error(String.format("adminsearchSearchBasis failed. ", d.cause()));
										errorSearchBasis(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("adminsearchSearchBasis failed. ", c.cause()));
								errorSearchBasis(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("adminsearchSearchBasis failed. ", ex));
					errorSearchBasis(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("adminsearchSearchBasis failed. ", b.cause()));
				errorSearchBasis(null, eventHandler, b);
			}
		});
	}


	public void adminsearchSearchBasisResponse(SearchList<SearchBasis> listSearchBasis, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSearchBasis.getSiteRequest_();
		try {
			response200AdminSearchSearchBasis(listSearchBasis, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("adminsearchSearchBasisResponse failed. ", a.cause()));
					errorSearchBasis(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("adminsearchSearchBasisResponse failed. ", ex));
			errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200AdminSearchSearchBasis(SearchList<SearchBasis> listSearchBasis, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSearchBasis.getSiteRequest_();
			QueryResponse responseSearch = listSearchBasis.getQueryResponse();
			SolrDocumentList solrDocuments = listSearchBasis.getSolrDocumentList();
			Long searchInMillis = Long.valueOf(responseSearch.getQTime());
			Long transmissionInMillis = responseSearch.getElapsedTime();
			Long startNum = responseSearch.getResults().getStart();
			Long foundNum = responseSearch.getResults().getNumFound();
			Integer returnedNum = responseSearch.getResults().size();
			String searchTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(searchInMillis), TimeUnit.MILLISECONDS.toMillis(searchInMillis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(searchInMillis)));
			String transmissionTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis), TimeUnit.MILLISECONDS.toMillis(transmissionInMillis) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis)));
			Exception exceptionSearch = responseSearch.getException();
			List<String> fls = listSearchBasis.getFields();

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
				json.put("searchTime", searchTime);
				json.put("transmissionTime", transmissionTime);
			}
			JsonArray l = new JsonArray();
			listSearchBasis.getList().stream().forEach(o -> {
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
					responsePivotAdminSearchSearchBasis(pivotFields, pivotArray);
				}
			}
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200AdminSearchSearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}
	public void responsePivotAdminSearchSearchBasis(List<PivotField> pivotFields, JsonArray pivotArray) {
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
				responsePivotAdminSearchSearchBasis(pivotFields2, pivotArray2);
			}
		}
	}

	// SearchPage //

	@Override
	public void searchpageSearchBasisId(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		searchpageSearchBasis(serviceRequest, eventHandler);
	}

	@Override
	public void searchpageSearchBasis(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		userSearchBasis(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/search-basis");
					siteRequest.setRequestMethod("SearchPage");
					{
						aSearchSearchBasis(siteRequest, false, true, false, "/search-basis", "SearchPage", c -> {
							if(c.succeeded()) {
								SearchList<SearchBasis> listSearchBasis = c.result();
								searchpageSearchBasisResponse(listSearchBasis, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("searchpageSearchBasis succeeded. "));
									} else {
										LOG.error(String.format("searchpageSearchBasis failed. ", d.cause()));
										errorSearchBasis(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("searchpageSearchBasis failed. ", c.cause()));
								errorSearchBasis(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("searchpageSearchBasis failed. ", ex));
					errorSearchBasis(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("searchpageSearchBasis failed. ", b.cause()));
				errorSearchBasis(null, eventHandler, b);
			}
		});
	}


	public void searchpageSearchBasisPageInit(SearchBasisPage page, SearchList<SearchBasis> listSearchBasis) {
	}
	public void searchpageSearchBasisResponse(SearchList<SearchBasis> listSearchBasis, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSearchBasis.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200SearchPageSearchBasis(listSearchBasis, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("searchpageSearchBasisResponse failed. ", a.cause()));
					errorSearchBasis(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("searchpageSearchBasisResponse failed. ", ex));
			errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchPageSearchBasis(SearchList<SearchBasis> listSearchBasis, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSearchBasis.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listSearchBasis.getSiteRequest_(), buffer);
			SearchBasisPage page = new SearchBasisPage();
			SolrDocument pageSolrDocument = new SolrDocument();
			MultiMap requestHeaders = MultiMap.caseInsensitiveMultiMap();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/search-basis");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listSearchBasis.size() == 1)
				siteRequest.setRequestPk(listSearchBasis.get(0).getPk());
			siteRequest.setW(w);
			page.setListSearchBasis(listSearchBasis);
			page.setSiteRequest_(siteRequest);
			searchpageSearchBasisPageInit(page, listSearchBasis);
			page.initDeepSearchBasisPage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new ServiceResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOG.error(String.format("response200SearchPageSearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// General //

	public Future<SearchBasis> defineIndexSearchBasis(SearchBasis searchBasis, Handler<AsyncResult<SearchBasis>> eventHandler) {
		Promise<SearchBasis> promise = Promise.promise();
		SiteRequestEnUS siteRequest = searchBasis.getSiteRequest_();
		defineSearchBasis(searchBasis, c -> {
			if(c.succeeded()) {
				attributeSearchBasis(searchBasis, d -> {
					if(d.succeeded()) {
						indexSearchBasis(searchBasis, e -> {
							if(e.succeeded()) {
								sqlCommitSearchBasis(siteRequest, f -> {
									if(f.succeeded()) {
										sqlCloseSearchBasis(siteRequest, g -> {
											if(g.succeeded()) {
												refreshSearchBasis(searchBasis, h -> {
													if(h.succeeded()) {
														eventHandler.handle(Future.succeededFuture(searchBasis));
														promise.complete(searchBasis);
													} else {
														LOG.error(String.format("refreshSearchBasis failed. ", h.cause()));
														errorSearchBasis(siteRequest, null, h);
													}
												});
											} else {
												LOG.error(String.format("defineIndexSearchBasis failed. ", g.cause()));
												errorSearchBasis(siteRequest, null, g);
											}
										});
									} else {
										LOG.error(String.format("defineIndexSearchBasis failed. ", f.cause()));
										errorSearchBasis(siteRequest, null, f);
									}
								});
							} else {
								LOG.error(String.format("defineIndexSearchBasis failed. ", e.cause()));
								errorSearchBasis(siteRequest, null, e);
							}
						});
					} else {
						LOG.error(String.format("defineIndexSearchBasis failed. ", d.cause()));
						errorSearchBasis(siteRequest, null, d);
					}
				});
			} else {
				LOG.error(String.format("defineIndexSearchBasis failed. ", c.cause()));
				errorSearchBasis(siteRequest, null, c);
			}
		});
		return promise.future();
	}

	public void createSearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<SearchBasis>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			String userId = siteRequest.getUserId();
			Long userKey = siteRequest.getUserKey();
			ZonedDateTime created = Optional.ofNullable(siteRequest.getJsonObject()).map(j -> j.getString("created")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())))).orElse(ZonedDateTime.now(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())));

			sqlConnection.preparedQuery("INSERT INTO SearchBasis(created, userKey) VALUES($1, $2) RETURNING pk")
					.collecting(Collectors.toList())
					.execute(Tuple.of(created.toOffsetDateTime(), userKey)
					, createAsync
			-> {
				if(createAsync.succeeded()) {
					Row createLine = createAsync.result().value().stream().findFirst().orElseGet(() -> null);
					Long pk = createLine.getLong(0);
					SearchBasis o = new SearchBasis();
					o.setPk(pk);
					o.setSiteRequest_(siteRequest);
					eventHandler.handle(Future.succeededFuture(o));
				} else {
					LOG.error(String.format("createSearchBasis failed. ", createAsync.cause()));
					eventHandler.handle(Future.failedFuture(createAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("createSearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void errorSearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler, AsyncResult<?> resultAsync) {
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
			sqlRollbackSearchBasis(siteRequest, a -> {
				if(a.succeeded()) {
					LOG.info(String.format("sql rollback. "));
					sqlCloseSearchBasis(siteRequest, b -> {
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

	public void sqlConnectionSearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
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
						LOG.error(String.format("sqlConnectionSearchBasis failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlSearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlTransactionSearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();

			if(sqlConnection == null) {
				eventHandler.handle(Future.failedFuture("sqlTransactionCloseSearchBasis failed, connection should not be null. "));
			} else {
				sqlConnection.begin(a -> {
					Transaction tx = a.result();
					siteRequest.setTx(tx);
					eventHandler.handle(Future.succeededFuture());
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlTransactionSearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCommitSearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();

			if(tx == null) {
				eventHandler.handle(Future.failedFuture("sqlCommitCloseSearchBasis failed, tx should not be null. "));
			} else {
				tx.commit(a -> {
					if(a.succeeded()) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else if("Transaction already completed".equals(a.cause().getMessage())) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOG.error(String.format("sqlCommitSearchBasis failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlSearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlRollbackSearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();

			if(tx == null) {
				eventHandler.handle(Future.failedFuture("sqlRollbackCloseSearchBasis failed, tx should not be null. "));
			} else {
				tx.rollback(a -> {
					if(a.succeeded()) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else if("Transaction already completed".equals(a.cause().getMessage())) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOG.error(String.format("sqlRollbackSearchBasis failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlSearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCloseSearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();

			if(sqlConnection == null) {
				eventHandler.handle(Future.failedFuture("sqlCloseSearchBasis failed, connection should not be null. "));
			} else {
				sqlConnection.close(a -> {
					if(a.succeeded()) {
						siteRequest.setSqlConnection(null);
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOG.error(String.format("sqlCloseSearchBasis failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlCloseSearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SiteRequestEnUS generateSiteRequestEnUSForSearchBasis(User user, SiteContextEnUS siteContext, ServiceRequest serviceRequest) {
		return generateSiteRequestEnUSForSearchBasis(user, siteContext, serviceRequest, null);
	}

	public SiteRequestEnUS generateSiteRequestEnUSForSearchBasis(User user, SiteContextEnUS siteContext, ServiceRequest serviceRequest, JsonObject body) {
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

	public void userSearchBasis(ServiceRequest serviceRequest, Handler<AsyncResult<SiteRequestEnUS>> eventHandler) {
		try {
			JsonObject userJson = serviceRequest.getUser();
			if(userJson == null) {
				SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSearchBasis(null, siteContext, serviceRequest);
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
								SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSearchBasis(user, siteContext, serviceRequest);
								sqlConnectionSearchBasis(siteRequest, c -> {
									if(c.succeeded()) {
										sqlTransactionSearchBasis(siteRequest, d -> {
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
																userSearchBasisDefine(siteRequest, jsonObject, false);

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
																						errorSearchBasis(siteRequest, null, g);
																					}
																				});
																			} else {
																				errorSearchBasis(siteRequest, null, f);
																			}
																		});
																	} else {
																		errorSearchBasis(siteRequest, null, e);
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
																Boolean define = userSearchBasisDefine(siteRequest, jsonObject, true);
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
																					errorSearchBasis(siteRequest, null, f);
																				}
																			});
																		} else {
																			errorSearchBasis(siteRequest, null, e);
																		}
																	});
																} else {
																	siteRequest.setSiteUser(siteUser1);
																	siteRequest.setUserName(siteUser1.getUserName());
																	siteRequest.setUserFirstName(siteUser1.getUserFirstName());
																	siteRequest.setUserLastName(siteUser1.getUserLastName());
																	siteRequest.setUserKey(siteUser1.getPk());
																	sqlRollbackSearchBasis(siteRequest, e -> {
																		if(e.succeeded()) {
																			eventHandler.handle(Future.succeededFuture(siteRequest));
																		} else {
																			eventHandler.handle(Future.failedFuture(e.cause()));
																			errorSearchBasis(siteRequest, null, e);
																		}
																	});
																}
															}
														} catch(Exception ex) {
															LOG.error(String.format("userSearchBasis failed. "), ex);
															eventHandler.handle(Future.failedFuture(ex));
														}
													} else {
														LOG.error(String.format("userSearchBasis failed. ", selectCAsync.cause()));
														eventHandler.handle(Future.failedFuture(selectCAsync.cause()));
													}
												});
											} else {
												LOG.error(String.format("userSearchBasis failed. ", d.cause()));
												eventHandler.handle(Future.failedFuture(d.cause()));
											}
										});
									} else {
										LOG.error(String.format("userSearchBasis failed. ", c.cause()));
										eventHandler.handle(Future.failedFuture(c.cause()));
									}
								});
							} else {
								LOG.error(String.format("userSearchBasis failed. ", b.cause()));
								eventHandler.handle(Future.failedFuture(b.cause()));
							}
						});
					} else {
						siteContext.getOauth2AuthenticationProvider().refresh(token, b -> {
							if(b.succeeded()) {
								User user = b.result();
								serviceRequest.setUser(user.principal());
								userSearchBasis(serviceRequest, c -> {
									if(c.succeeded()) {
										SiteRequestEnUS siteRequest = c.result();
										eventHandler.handle(Future.succeededFuture(siteRequest));
									} else {
										LOG.error(String.format("userSearchBasis failed. ", c.cause()));
										eventHandler.handle(Future.failedFuture(c.cause()));
									}
								});
							} else {
								LOG.error(String.format("userSearchBasis failed. ", a.cause()));
								eventHandler.handle(Future.failedFuture(a.cause()));
							}
						});
					}
				});
			}
		} catch(Exception ex) {
			LOG.error(String.format("userSearchBasis failed. "), ex);
			eventHandler.handle(Future.failedFuture(ex));
		}
	}

	public Boolean userSearchBasisDefine(SiteRequestEnUS siteRequest, JsonObject jsonObject, Boolean patch) {
		if(patch) {
			return false;
		} else {
			return false;
		}
	}

	public void aSearchSearchBasisQ(String uri, String apiMethod, SearchList<SearchBasis> searchList, String entityVar, String valueIndexed, String varIndexed) {
		searchList.setQuery(varIndexed + ":" + ("*".equals(valueIndexed) ? valueIndexed : ClientUtils.escapeQueryChars(valueIndexed)));
		if(!"*".equals(entityVar)) {
		}
	}

	public String aSearchSearchBasisFq(String uri, String apiMethod, SearchList<SearchBasis> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		if(StringUtils.startsWith(valueIndexed, "[")) {
			String[] fqs = StringUtils.substringBefore(StringUtils.substringAfter(valueIndexed, "["), "]").split(" TO ");
			if(fqs.length != 2)
				throw new RuntimeException(String.format("\"%s\" invalid range query. ", valueIndexed));
			String fq1 = fqs[0].equals("*") ? fqs[0] : SearchBasis.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[0]);
			String fq2 = fqs[1].equals("*") ? fqs[1] : SearchBasis.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[1]);
			 return varIndexed + ":[" + fq1 + " TO " + fq2 + "]";
		} else {
			return varIndexed + ":" + SearchBasis.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), valueIndexed);
		}
	}

	public void aSearchSearchBasisSort(String uri, String apiMethod, SearchList<SearchBasis> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addSort(varIndexed, ORDER.valueOf(valueIndexed));
	}

	public void aSearchSearchBasisRows(String uri, String apiMethod, SearchList<SearchBasis> searchList, Integer valueRows) {
			searchList.setRows(apiMethod != null && apiMethod.contains("Search") ? valueRows : 10);
	}

	public void aSearchSearchBasisStart(String uri, String apiMethod, SearchList<SearchBasis> searchList, Integer valueStart) {
		searchList.setStart(valueStart);
	}

	public void aSearchSearchBasisVar(String uri, String apiMethod, SearchList<SearchBasis> searchList, String var, String value) {
		searchList.getSiteRequest_().getRequestVars().put(var, value);
	}

	public void aSearchSearchBasisUri(String uri, String apiMethod, SearchList<SearchBasis> searchList) {
	}

	public void varsSearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<SearchList<ServiceResponse>>> eventHandler) {
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
					LOG.error(String.format("aSearchSearchBasis failed. "), e);
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOG.error(String.format("aSearchSearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void aSearchSearchBasis(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, Handler<AsyncResult<SearchList<SearchBasis>>> eventHandler) {
		try {
			SearchList<SearchBasis> searchList = aSearchSearchBasisList(siteRequest, populate, store, modify, uri, apiMethod);
			eventHandler.handle(Future.succeededFuture(searchList));
		} catch(Exception e) {
			LOG.error(String.format("aSearchSearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SearchList<SearchBasis> aSearchSearchBasisList(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod) {
		ServiceRequest serviceRequest = siteRequest.getServiceRequest();
		String entityListStr = siteRequest.getServiceRequest().getParams().getJsonObject("query").getString("fl");
		String[] entityList = entityListStr == null ? null : entityListStr.split(",\\s*");
		SearchList<SearchBasis> searchList = new SearchList<SearchBasis>();
		searchList.setPopulate(populate);
		searchList.setStore(store);
		searchList.setQuery("*:*");
		searchList.setC(SearchBasis.class);
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
							varsIndexed[i] = SearchBasis.varIndexedSearchBasis(entityVar);
						}
						searchList.add("facet.pivot", (solrLocalParams == null ? "" : solrLocalParams) + StringUtils.join(varsIndexed, ","));
					}
				} else if(paramValuesObject != null) {
					for(Object paramObject : paramObjects) {
						switch(paramName) {
							case "q":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								varIndexed = "*".equals(entityVar) ? entityVar : SearchBasis.varSearchSearchBasis(entityVar);
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								valueIndexed = StringUtils.isEmpty(valueIndexed) ? "*" : valueIndexed;
								aSearchSearchBasisQ(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "fq":
								Matcher mFq = Pattern.compile("(\\w+):(.+?(?=(\\)|\\s+OR\\s+|\\s+AND\\s+|$)))").matcher((String)paramObject);
								boolean foundFq = mFq.find();
								if(foundFq) {
									StringBuffer sb = new StringBuffer();
									while(foundFq) {
										entityVar = mFq.group(1).trim();
										valueIndexed = mFq.group(2).trim();
										varIndexed = SearchBasis.varIndexedSearchBasis(entityVar);
										String entityFq = aSearchSearchBasisFq(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
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
								varIndexed = SearchBasis.varIndexedSearchBasis(entityVar);
								aSearchSearchBasisSort(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "start":
								valueStart = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
								aSearchSearchBasisStart(uri, apiMethod, searchList, valueStart);
								break;
							case "rows":
								valueRows = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
								aSearchSearchBasisRows(uri, apiMethod, searchList, valueRows);
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
									varIndexed = SearchBasis.varIndexedSearchBasis(entityVar);
									searchList.add("facet.range", (solrLocalParams == null ? "" : solrLocalParams) + varIndexed);
								}
								break;
							case "facet.field":
								entityVar = (String)paramObject;
								varIndexed = SearchBasis.varIndexedSearchBasis(entityVar);
								if(varIndexed != null)
									searchList.addFacetField(varIndexed);
								break;
							case "var":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								aSearchSearchBasisVar(uri, apiMethod, searchList, entityVar, valueIndexed);
								break;
						}
					}
					aSearchSearchBasisUri(uri, apiMethod, searchList);
				}
			} catch(Exception e) {
				ExceptionUtils.rethrow(e);
			}
		});
		if("*:*".equals(searchList.getQuery()) && searchList.getSorts().size() == 0) {
			searchList.addSort("created_indexed_date", ORDER.desc);
		}
		aSearchSearchBasis2(siteRequest, populate, store, modify, uri, apiMethod, searchList);
		searchList.initDeepForClass(siteRequest);
		return searchList;
	}
	public void aSearchSearchBasis2(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, SearchList<SearchBasis> searchList) {
	}

	public void defineSearchBasis(SearchBasis o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT * FROM SearchBasis WHERE pk=$1")
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
										LOG.error(String.format("defineSearchBasis failed. "), e);
									}
								}
							}
						}
						eventHandler.handle(Future.succeededFuture());
					} catch(Exception e) {
						LOG.error(String.format("defineSearchBasis failed. "), e);
						eventHandler.handle(Future.failedFuture(e));
					}
				} else {
					LOG.error(String.format("defineSearchBasis failed. ", defineAsync.cause()));
					eventHandler.handle(Future.failedFuture(defineAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("defineSearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void attributeSearchBasis(SearchBasis o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT searchKey as pk1, 'searchKey' from SearchBasis where pk=$1")
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
						LOG.error(String.format("attributeSearchBasis failed. ", attributeAsync.cause()));
						eventHandler.handle(Future.failedFuture(attributeAsync.cause()));
					}
				} catch(Exception e) {
					LOG.error(String.format("attributeSearchBasis failed. "), e);
					eventHandler.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("attributeSearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void indexSearchBasis(SearchBasis o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			o.initDeepForClass(siteRequest);
			o.indexForClass();
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOG.error(String.format("indexSearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void refreshSearchBasis(SearchBasis o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Boolean refresh = !"false".equals(siteRequest.getRequestVars().get("refresh"));
			if(refresh && BooleanUtils.isFalse(Optional.ofNullable(siteRequest.getApiRequest_()).map(ApiRequest::getEmpty).orElse(true))) {
				SearchList<SearchBasis> searchList = new SearchList<SearchBasis>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(SearchBasis.class);
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
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSearchBasis(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), new JsonObject());
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
						SearchBasisEnUSApiServiceImpl service = new SearchBasisEnUSApiServiceImpl(siteRequest.getSiteContext_());
						List<Future> futures2 = new ArrayList<>();
						for(SearchBasis o2 : searchList.getList()) {
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSearchBasis(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), new JsonObject());
							o2.setSiteRequest_(siteRequest2);
							futures2.add(
								service.patchSearchBasisFuture(o2, false, b -> {
									if(b.succeeded()) {
									} else {
										LOG.info(String.format("SearchBasis %s failed. ", o2.getPk()));
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
								errorSearchBasis(siteRequest, eventHandler, b);
							}
						});
					} else {
						LOG.error("Refresh relations failed. ", a.cause());
						errorSearchBasis(siteRequest, eventHandler, a);
					}
				});
			} else {
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOG.error(String.format("refreshSearchBasis failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}
}
