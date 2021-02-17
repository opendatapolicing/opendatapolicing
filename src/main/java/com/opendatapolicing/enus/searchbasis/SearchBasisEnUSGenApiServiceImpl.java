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
import io.vertx.ext.web.Router;
import io.vertx.core.Vertx;
import io.vertx.ext.reactivestreams.ReactiveReadStream;
import io.vertx.ext.reactivestreams.ReactiveWriteStream;
import io.vertx.core.MultiMap;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.api.validation.HTTPRequestValidationHandler;
import io.vertx.ext.web.api.validation.ParameterTypeValidator;
import io.vertx.ext.web.api.validation.ValidationException;
import io.vertx.ext.web.api.contract.openapi3.OpenAPI3RouterFactory;
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
import io.vertx.ext.web.api.OperationResponse;
import io.vertx.core.CompositeFuture;
import org.apache.http.client.utils.URLEncodedUtils;
import java.nio.charset.Charset;
import org.apache.http.NameValuePair;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.ext.auth.oauth2.impl.OAuth2TokenImpl;
import java.util.Optional;
import java.util.stream.Stream;
import java.net.URLDecoder;
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

	protected static final Logger LOGGER = LoggerFactory.getLogger(SearchBasisEnUSGenApiServiceImpl.class);

	protected static final String SERVICE_ADDRESS = "SearchBasisEnUSApiServiceImpl";

	protected SiteContextEnUS siteContext;

	public SearchBasisEnUSGenApiServiceImpl(SiteContextEnUS siteContext) {
		this.siteContext = siteContext;
	}

	// PUTImport //

	@Override
	public void putimportSearchBasis(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSearchBasis(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/search-basis/import");
		siteRequest.setRequestMethod("PUTImport");
		try {
			LOGGER.info(String.format("putimportSearchBasis started. "));

			List<String> roles = Arrays.asList("SiteService");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), MultiMap.caseInsensitiveMultiMap()
					)
				));
			} else {

				userSearchBasis(siteRequest, b -> {
					if(b.succeeded()) {
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
																	LOGGER.info(String.format("putimportSearchBasis succeeded. "));
																	blockingCodeHandler.handle(Future.succeededFuture(e.result()));
																} else {
																	LOGGER.error(String.format("putimportSearchBasis failed. ", f.cause()));
																	errorSearchBasis(siteRequest, null, f);
																}
															});
														} else {
															LOGGER.error(String.format("putimportSearchBasis failed. ", e.cause()));
															errorSearchBasis(siteRequest, null, e);
														}
													});
												} else {
													LOGGER.error(String.format("putimportSearchBasis failed. ", d.cause()));
													errorSearchBasis(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOGGER.error(String.format("putimportSearchBasis failed. ", ex));
											errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOGGER.error(String.format("putimportSearchBasis failed. ", c.cause()));
								errorSearchBasis(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("putimportSearchBasis failed. ", b.cause()));
						errorSearchBasis(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("putimportSearchBasis failed. ", ex));
			errorSearchBasis(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPUTImportSearchBasis(ApiRequest apiRequest, SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				json.put("created", json.getValue("created"));

				SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSearchBasis(siteContext, siteRequest.getOperationRequest(), json);
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
									LOGGER.error(String.format("listPUTImportSearchBasis failed. ", a.cause()));
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
								LOGGER.error(String.format("listPUTImportSearchBasis failed. ", a.cause()));
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
					LOGGER.error(String.format("listPUTImportSearchBasis failed. ", a.cause()));
					errorSearchBasis(apiRequest.getSiteRequest_(), eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("listPUTImportSearchBasis failed. ", ex));
			errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
		}
	}

	public void putimportSearchBasisResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTImportSearchBasis(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putimportSearchBasisResponse failed. ", a.cause()));
					errorSearchBasis(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putimportSearchBasisResponse failed. ", ex));
			errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTImportSearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTImportSearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTMerge //

	@Override
	public void putmergeSearchBasis(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSearchBasis(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/search-basis/merge");
		siteRequest.setRequestMethod("PUTMerge");
		try {
			LOGGER.info(String.format("putmergeSearchBasis started. "));

			List<String> roles = Arrays.asList("SiteService");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), MultiMap.caseInsensitiveMultiMap()
					)
				));
			} else {

				userSearchBasis(siteRequest, b -> {
					if(b.succeeded()) {
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
																	LOGGER.info(String.format("putmergeSearchBasis succeeded. "));
																	blockingCodeHandler.handle(Future.succeededFuture(e.result()));
																} else {
																	LOGGER.error(String.format("putmergeSearchBasis failed. ", f.cause()));
																	errorSearchBasis(siteRequest, null, f);
																}
															});
														} else {
															LOGGER.error(String.format("putmergeSearchBasis failed. ", e.cause()));
															errorSearchBasis(siteRequest, null, e);
														}
													});
												} else {
													LOGGER.error(String.format("putmergeSearchBasis failed. ", d.cause()));
													errorSearchBasis(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOGGER.error(String.format("putmergeSearchBasis failed. ", ex));
											errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOGGER.error(String.format("putmergeSearchBasis failed. ", c.cause()));
								errorSearchBasis(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("putmergeSearchBasis failed. ", b.cause()));
						errorSearchBasis(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("putmergeSearchBasis failed. ", ex));
			errorSearchBasis(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPUTMergeSearchBasis(ApiRequest apiRequest, SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSearchBasis(siteContext, siteRequest.getOperationRequest(), json);
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
									LOGGER.error(String.format("listPUTMergeSearchBasis failed. ", a.cause()));
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
								LOGGER.error(String.format("listPUTMergeSearchBasis failed. ", a.cause()));
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
					LOGGER.error(String.format("listPUTMergeSearchBasis failed. ", a.cause()));
					errorSearchBasis(apiRequest.getSiteRequest_(), eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("listPUTMergeSearchBasis failed. ", ex));
			errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
		}
	}

	public void putmergeSearchBasisResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTMergeSearchBasis(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putmergeSearchBasisResponse failed. ", a.cause()));
					errorSearchBasis(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putmergeSearchBasisResponse failed. ", ex));
			errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTMergeSearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTMergeSearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTCopy //

	@Override
	public void putcopySearchBasis(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSearchBasis(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/search-basis/copy");
		siteRequest.setRequestMethod("PUTCopy");
		try {
			LOGGER.info(String.format("putcopySearchBasis started. "));

			List<String> roles = Arrays.asList("SiteService");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), MultiMap.caseInsensitiveMultiMap()
					)
				));
			} else {

				userSearchBasis(siteRequest, b -> {
					if(b.succeeded()) {
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
																		LOGGER.info(String.format("putcopySearchBasis succeeded. "));
																		blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																	} else {
																		LOGGER.error(String.format("putcopySearchBasis failed. ", f.cause()));
																		errorSearchBasis(siteRequest, null, f);
																	}
																});
															} else {
																LOGGER.error(String.format("putcopySearchBasis failed. ", e.cause()));
																errorSearchBasis(siteRequest, null, e);
															}
														});
													} catch(Exception ex) {
														LOGGER.error(String.format("putcopySearchBasis failed. ", ex));
														errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
													}
												} else {
													LOGGER.error(String.format("putcopySearchBasis failed. ", d.cause()));
													errorSearchBasis(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOGGER.error(String.format("putcopySearchBasis failed. ", ex));
											errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOGGER.error(String.format("putcopySearchBasis failed. ", c.cause()));
								errorSearchBasis(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("putcopySearchBasis failed. ", b.cause()));
						errorSearchBasis(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopySearchBasis failed. ", ex));
			errorSearchBasis(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPUTCopySearchBasis(ApiRequest apiRequest, SearchList<SearchBasis> listSearchBasis, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listSearchBasis.getSiteRequest_();
		listSearchBasis.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSearchBasis(siteContext, siteRequest.getOperationRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				putcopySearchBasisFuture(siteRequest2, JsonObject.mapFrom(o), a -> {
					if(a.succeeded()) {
					} else {
						LOGGER.error(String.format("listPUTCopySearchBasis failed. ", a.cause()));
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
				LOGGER.error(String.format("listPUTCopySearchBasis failed. ", a.cause()));
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
													LOGGER.error(String.format("putcopySearchBasisFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOGGER.error(String.format("putcopySearchBasisFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("putcopySearchBasisFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("putcopySearchBasisFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("putcopySearchBasisFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("putcopySearchBasisFuture failed. ", e));
			errorSearchBasis(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPUTCopySearchBasis(SearchBasis o, JsonObject jsonObject, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Transaction tx = siteRequest.getTx();
			Integer num = 1;
			Long pk = o.getPk();
			List<Future> futures = new ArrayList<>();

			if(jsonObject != null) {
				JsonArray entityVars = jsonObject.getJsonArray("saves");
				for(Integer i = 0; i < entityVars.size(); i++) {
					String entityVar = entityVars.getString(i);
					switch(entityVar) {
					case "inheritPk":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "inheritPk", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.inheritPk failed", b.cause())));
							});
						}));
						break;
					case "archived":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "archived", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.archived failed", b.cause())));
							});
						}));
						break;
					case "deleted":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "deleted", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.deleted failed", b.cause())));
							});
						}));
						break;
					case "searchKey":
							{
						Long l = Long.parseLong(jsonObject.getString(entityVar));
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_addA)
									.execute(Tuple.of(l, "searchBasisKeys", pk, "searchKey")
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.searchKey failed", b.cause())));
							});
						}));
						}
						break;
					case "stopAgencyTitle":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopAgencyTitle", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopAgencyTitle failed", b.cause())));
							});
						}));
						break;
					case "stopDateTime":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopDateTime", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopDateTime failed", b.cause())));
							});
						}));
						break;
					case "stopPurposeNum":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopPurposeNum", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopPurposeNum failed", b.cause())));
							});
						}));
						break;
					case "stopPurposeTitle":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopPurposeTitle", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopPurposeTitle failed", b.cause())));
							});
						}));
						break;
					case "stopActionNum":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopActionNum", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopActionNum failed", b.cause())));
							});
						}));
						break;
					case "stopActionTitle":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopActionTitle", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopActionTitle failed", b.cause())));
							});
						}));
						break;
					case "stopDriverArrest":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopDriverArrest", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopDriverArrest failed", b.cause())));
							});
						}));
						break;
					case "stopPassengerArrest":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopPassengerArrest", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopPassengerArrest failed", b.cause())));
							});
						}));
						break;
					case "stopEncounterForce":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopEncounterForce", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopEncounterForce failed", b.cause())));
							});
						}));
						break;
					case "stopEngageForce":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopEngageForce", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopEngageForce failed", b.cause())));
							});
						}));
						break;
					case "stopOfficerInjury":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopOfficerInjury", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopOfficerInjury failed", b.cause())));
							});
						}));
						break;
					case "stopDriverInjury":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopDriverInjury", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopDriverInjury failed", b.cause())));
							});
						}));
						break;
					case "stopPassengerInjury":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopPassengerInjury", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopPassengerInjury failed", b.cause())));
							});
						}));
						break;
					case "stopOfficerId":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopOfficerId", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopOfficerId failed", b.cause())));
							});
						}));
						break;
					case "stopLocationId":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopLocationId", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopLocationId failed", b.cause())));
							});
						}));
						break;
					case "stopCityId":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopCityId", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopCityId failed", b.cause())));
							});
						}));
						break;
					case "personTypeId":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "personTypeId", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.personTypeId failed", b.cause())));
							});
						}));
						break;
					case "personGenderId":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "personGenderId", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.personGenderId failed", b.cause())));
							});
						}));
						break;
					case "personEthnicityId":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "personEthnicityId", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.personEthnicityId failed", b.cause())));
							});
						}));
						break;
					case "personRaceId":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "personRaceId", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.personRaceId failed", b.cause())));
							});
						}));
						break;
					case "searchTypeNum":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "searchTypeNum", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.searchTypeNum failed", b.cause())));
							});
						}));
						break;
					case "searchBasisId":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "searchBasisId", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.searchBasisId failed", b.cause())));
							});
						}));
						break;
					case "searchBasisTitle":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "searchBasisTitle", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.searchBasisTitle failed", b.cause())));
							});
						}));
						break;
					}
				}
			}
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture());
				} else {
					LOGGER.error(String.format("sqlPUTCopySearchBasis failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPUTCopySearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void putcopySearchBasisResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTCopySearchBasis(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putcopySearchBasisResponse failed. ", a.cause()));
					errorSearchBasis(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putcopySearchBasisResponse failed. ", ex));
			errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTCopySearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTCopySearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// POST //

	@Override
	public void postSearchBasis(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSearchBasis(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/search-basis");
		siteRequest.setRequestMethod("POST");
		try {
			LOGGER.info(String.format("postSearchBasis started. "));

			List<String> roles = Arrays.asList("SiteService");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), MultiMap.caseInsensitiveMultiMap()
					)
				));
			} else {

				userSearchBasis(siteRequest, b -> {
					if(b.succeeded()) {
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
										LOGGER.info(String.format("postSearchBasis succeeded. "));
									} else {
										LOGGER.error(String.format("postSearchBasis failed. ", d.cause()));
										errorSearchBasis(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("postSearchBasis failed. ", c.cause()));
								errorSearchBasis(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("postSearchBasis failed. ", b.cause()));
						errorSearchBasis(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("postSearchBasis failed. ", ex));
			errorSearchBasis(siteRequest, eventHandler, Future.failedFuture(ex));
		}
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
													LOGGER.error(String.format("postSearchBasisFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOGGER.error(String.format("postSearchBasisFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("postSearchBasisFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("postSearchBasisFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("postSearchBasisFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("postSearchBasisFuture failed. ", e));
			errorSearchBasis(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPOSTSearchBasis(SearchBasis o, Boolean inheritPk, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Transaction tx = siteRequest.getTx();
			Integer num = 1;
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			SearchBasis o2 = new SearchBasis();
			o2.setSiteRequest_(siteRequest);
			List<Future> futures = new ArrayList<>();

			if(siteRequest.getSessionId() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContextEnUS.SQL_setD)
				.execute(Tuple.of(pk, "sessionId", siteRequest.getSessionId())
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));
			}
			if(siteRequest.getUserId() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContextEnUS.SQL_setD)
				.execute(Tuple.of(pk, "userId", siteRequest.getUserId())
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));
			}
			if(siteRequest.getUserKey() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContextEnUS.SQL_setD)
				.execute(Tuple.of(pk, "userKey", siteRequest.getUserKey().toString())
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));
			}

			if(jsonObject != null) {
				Set<String> entityVars = jsonObject.fieldNames();
				for(String entityVar : entityVars) {
					switch(entityVar) {
					case "inheritPk":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "inheritPk", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.inheritPk failed", b.cause())));
							});
						}));
						break;
					case "archived":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "archived", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.archived failed", b.cause())));
							});
						}));
						break;
					case "deleted":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "deleted", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.deleted failed", b.cause())));
							});
						}));
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
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA)
												.execute(Tuple.of(l2, "searchBasisKeys", pk, "searchKey")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value SearchBasis.searchKey failed", b.cause())));
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
					case "stopAgencyTitle":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopAgencyTitle", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopAgencyTitle failed", b.cause())));
							});
						}));
						break;
					case "stopDateTime":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopDateTime", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopDateTime failed", b.cause())));
							});
						}));
						break;
					case "stopPurposeNum":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopPurposeNum", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopPurposeNum failed", b.cause())));
							});
						}));
						break;
					case "stopPurposeTitle":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopPurposeTitle", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopPurposeTitle failed", b.cause())));
							});
						}));
						break;
					case "stopActionNum":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopActionNum", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopActionNum failed", b.cause())));
							});
						}));
						break;
					case "stopActionTitle":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopActionTitle", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopActionTitle failed", b.cause())));
							});
						}));
						break;
					case "stopDriverArrest":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopDriverArrest", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopDriverArrest failed", b.cause())));
							});
						}));
						break;
					case "stopPassengerArrest":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopPassengerArrest", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopPassengerArrest failed", b.cause())));
							});
						}));
						break;
					case "stopEncounterForce":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopEncounterForce", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopEncounterForce failed", b.cause())));
							});
						}));
						break;
					case "stopEngageForce":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopEngageForce", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopEngageForce failed", b.cause())));
							});
						}));
						break;
					case "stopOfficerInjury":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopOfficerInjury", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopOfficerInjury failed", b.cause())));
							});
						}));
						break;
					case "stopDriverInjury":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopDriverInjury", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopDriverInjury failed", b.cause())));
							});
						}));
						break;
					case "stopPassengerInjury":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopPassengerInjury", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopPassengerInjury failed", b.cause())));
							});
						}));
						break;
					case "stopOfficerId":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopOfficerId", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopOfficerId failed", b.cause())));
							});
						}));
						break;
					case "stopLocationId":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopLocationId", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopLocationId failed", b.cause())));
							});
						}));
						break;
					case "stopCityId":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "stopCityId", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.stopCityId failed", b.cause())));
							});
						}));
						break;
					case "personTypeId":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "personTypeId", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.personTypeId failed", b.cause())));
							});
						}));
						break;
					case "personGenderId":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "personGenderId", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.personGenderId failed", b.cause())));
							});
						}));
						break;
					case "personEthnicityId":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "personEthnicityId", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.personEthnicityId failed", b.cause())));
							});
						}));
						break;
					case "personRaceId":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "personRaceId", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.personRaceId failed", b.cause())));
							});
						}));
						break;
					case "searchTypeNum":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "searchTypeNum", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.searchTypeNum failed", b.cause())));
							});
						}));
						break;
					case "searchBasisId":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "searchBasisId", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.searchBasisId failed", b.cause())));
							});
						}));
						break;
					case "searchBasisTitle":
						futures.add(Future.future(a -> {
							tx.preparedQuery(SiteContextEnUS.SQL_setD)
									.execute(Tuple.of(pk, "searchBasisTitle", Optional.ofNullable(jsonObject.getValue(entityVar)).map(s -> s.toString()).orElse(null))
									, b
							-> {
								if(b.succeeded())
									a.handle(Future.succeededFuture());
								else
									a.handle(Future.failedFuture(new Exception("value SearchBasis.searchBasisTitle failed", b.cause())));
							});
						}));
						break;
					}
				}
			}
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture());
				} else {
					LOGGER.error(String.format("sqlPOSTSearchBasis failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPOSTSearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void postSearchBasisResponse(SearchBasis searchBasis, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = searchBasis.getSiteRequest_();
		try {
			response200POSTSearchBasis(searchBasis, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("postSearchBasisResponse failed. ", a.cause()));
					errorSearchBasis(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("postSearchBasisResponse failed. ", ex));
			errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200POSTSearchBasis(SearchBasis o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200POSTSearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchSearchBasis(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSearchBasis(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/search-basis");
		siteRequest.setRequestMethod("PATCH");
		try {
			LOGGER.info(String.format("patchSearchBasis started. "));

			List<String> roles = Arrays.asList("SiteService");
			if(
					!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
					&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
					) {
				eventHandler.handle(Future.succeededFuture(
					new OperationResponse(401, "UNAUTHORIZED", 
						Buffer.buffer().appendString(
							new JsonObject()
								.put("errorCode", "401")
								.put("errorMessage", "roles required: " + String.join(", ", roles))
								.encodePrettily()
							), MultiMap.caseInsensitiveMultiMap()
					)
				));
			} else {

				userSearchBasis(siteRequest, b -> {
					if(b.succeeded()) {
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
														LOGGER.error(message);
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
																			LOGGER.info(String.format("patchSearchBasis succeeded. "));
																			blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																		} else {
																			LOGGER.error(String.format("patchSearchBasis failed. ", f.cause()));
																			errorSearchBasis(siteRequest, null, f);
																		}
																	});
																} else {
																	LOGGER.error(String.format("patchSearchBasis failed. ", e.cause()));
																	errorSearchBasis(siteRequest, null, e);
																}
															});
														} catch(Exception ex) {
															LOGGER.error(String.format("patchSearchBasis failed. ", ex));
															errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
														}
													}
										} else {
													LOGGER.error(String.format("patchSearchBasis failed. ", d.cause()));
													errorSearchBasis(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOGGER.error(String.format("patchSearchBasis failed. ", ex));
											errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOGGER.error(String.format("patchSearchBasis failed. ", c.cause()));
								errorSearchBasis(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("patchSearchBasis failed. ", b.cause()));
						errorSearchBasis(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("patchSearchBasis failed. ", ex));
			errorSearchBasis(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPATCHSearchBasis(ApiRequest apiRequest, SearchList<SearchBasis> listSearchBasis, String dt, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listSearchBasis.getSiteRequest_();
		listSearchBasis.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSearchBasis(siteContext, siteRequest.getOperationRequest(), siteRequest.getJsonObject());
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
				LOGGER.error(String.format("listPATCHSearchBasis failed. ", a.cause()));
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
											LOGGER.error(String.format("patchSearchBasisFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("patchSearchBasisFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("patchSearchBasisFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("patchSearchBasisFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("patchSearchBasisFuture failed. ", e));
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
			Transaction tx = siteRequest.getTx();
			Integer num = 1;
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			SearchBasis o2 = new SearchBasis();
			o2.setSiteRequest_(siteRequest);
			List<Future> futures = new ArrayList<>();

			if(o.getUserId() == null && siteRequest.getUserId() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContextEnUS.SQL_setD)
							.execute(Tuple.of(pk, "userId", siteRequest.getUserId())
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));
			}
			if(o.getUserKey() == null && siteRequest.getUserKey() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContextEnUS.SQL_setD)
				.execute(Tuple.of(pk, "userKey", siteRequest.getUserKey().toString())
							, b
					-> {
						if(b.succeeded())
							a.handle(Future.succeededFuture());
						else
							a.handle(Future.failedFuture(b.cause()));
					});
				}));
			}

			for(String methodName : methodNames) {
				switch(methodName) {
					case "setInheritPk":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "inheritPk")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.inheritPk failed", b.cause())));
								});
							}));
						} else {
							o2.setInheritPk(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "inheritPk", o2.jsonInheritPk())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.inheritPk failed", b.cause())));
								});
							}));
						}
						break;
					case "setArchived":
						if(jsonObject.getBoolean(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "archived")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.archived failed", b.cause())));
								});
							}));
						} else {
							o2.setArchived(jsonObject.getBoolean(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "archived", o2.jsonArchived())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.archived failed", b.cause())));
								});
							}));
						}
						break;
					case "setDeleted":
						if(jsonObject.getBoolean(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "deleted")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.deleted failed", b.cause())));
								});
							}));
						} else {
							o2.setDeleted(jsonObject.getBoolean(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "deleted", o2.jsonDeleted())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.deleted failed", b.cause())));
								});
							}));
						}
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
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_addA)
												.execute(Tuple.of(l2, "searchBasisKeys", pk, "searchKey")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value SearchBasis.searchKey failed", b.cause())));
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
									futures.add(Future.future(a -> {
										tx.preparedQuery(SiteContextEnUS.SQL_removeA)
												.execute(Tuple.of(l2, "searchBasisKeys", pk, "searchKey")
												, b
										-> {
											if(b.succeeded())
												a.handle(Future.succeededFuture());
											else
												a.handle(Future.failedFuture(new Exception("value SearchBasis.searchKey failed", b.cause())));
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
					case "setStopAgencyTitle":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "stopAgencyTitle")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopAgencyTitle failed", b.cause())));
								});
							}));
						} else {
							o2.setStopAgencyTitle(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "stopAgencyTitle", o2.jsonStopAgencyTitle())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopAgencyTitle failed", b.cause())));
								});
							}));
						}
						break;
					case "setStopDateTime":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "stopDateTime")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopDateTime failed", b.cause())));
								});
							}));
						} else {
							o2.setStopDateTime(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "stopDateTime", o2.jsonStopDateTime())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopDateTime failed", b.cause())));
								});
							}));
						}
						break;
					case "setStopPurposeNum":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "stopPurposeNum")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopPurposeNum failed", b.cause())));
								});
							}));
						} else {
							o2.setStopPurposeNum(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "stopPurposeNum", o2.jsonStopPurposeNum())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopPurposeNum failed", b.cause())));
								});
							}));
						}
						break;
					case "setStopPurposeTitle":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "stopPurposeTitle")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopPurposeTitle failed", b.cause())));
								});
							}));
						} else {
							o2.setStopPurposeTitle(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "stopPurposeTitle", o2.jsonStopPurposeTitle())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopPurposeTitle failed", b.cause())));
								});
							}));
						}
						break;
					case "setStopActionNum":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "stopActionNum")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopActionNum failed", b.cause())));
								});
							}));
						} else {
							o2.setStopActionNum(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "stopActionNum", o2.jsonStopActionNum())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopActionNum failed", b.cause())));
								});
							}));
						}
						break;
					case "setStopActionTitle":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "stopActionTitle")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopActionTitle failed", b.cause())));
								});
							}));
						} else {
							o2.setStopActionTitle(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "stopActionTitle", o2.jsonStopActionTitle())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopActionTitle failed", b.cause())));
								});
							}));
						}
						break;
					case "setStopDriverArrest":
						if(jsonObject.getBoolean(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "stopDriverArrest")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopDriverArrest failed", b.cause())));
								});
							}));
						} else {
							o2.setStopDriverArrest(jsonObject.getBoolean(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "stopDriverArrest", o2.jsonStopDriverArrest())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopDriverArrest failed", b.cause())));
								});
							}));
						}
						break;
					case "setStopPassengerArrest":
						if(jsonObject.getBoolean(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "stopPassengerArrest")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopPassengerArrest failed", b.cause())));
								});
							}));
						} else {
							o2.setStopPassengerArrest(jsonObject.getBoolean(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "stopPassengerArrest", o2.jsonStopPassengerArrest())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopPassengerArrest failed", b.cause())));
								});
							}));
						}
						break;
					case "setStopEncounterForce":
						if(jsonObject.getBoolean(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "stopEncounterForce")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopEncounterForce failed", b.cause())));
								});
							}));
						} else {
							o2.setStopEncounterForce(jsonObject.getBoolean(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "stopEncounterForce", o2.jsonStopEncounterForce())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopEncounterForce failed", b.cause())));
								});
							}));
						}
						break;
					case "setStopEngageForce":
						if(jsonObject.getBoolean(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "stopEngageForce")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopEngageForce failed", b.cause())));
								});
							}));
						} else {
							o2.setStopEngageForce(jsonObject.getBoolean(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "stopEngageForce", o2.jsonStopEngageForce())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopEngageForce failed", b.cause())));
								});
							}));
						}
						break;
					case "setStopOfficerInjury":
						if(jsonObject.getBoolean(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "stopOfficerInjury")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopOfficerInjury failed", b.cause())));
								});
							}));
						} else {
							o2.setStopOfficerInjury(jsonObject.getBoolean(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "stopOfficerInjury", o2.jsonStopOfficerInjury())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopOfficerInjury failed", b.cause())));
								});
							}));
						}
						break;
					case "setStopDriverInjury":
						if(jsonObject.getBoolean(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "stopDriverInjury")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopDriverInjury failed", b.cause())));
								});
							}));
						} else {
							o2.setStopDriverInjury(jsonObject.getBoolean(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "stopDriverInjury", o2.jsonStopDriverInjury())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopDriverInjury failed", b.cause())));
								});
							}));
						}
						break;
					case "setStopPassengerInjury":
						if(jsonObject.getBoolean(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "stopPassengerInjury")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopPassengerInjury failed", b.cause())));
								});
							}));
						} else {
							o2.setStopPassengerInjury(jsonObject.getBoolean(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "stopPassengerInjury", o2.jsonStopPassengerInjury())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopPassengerInjury failed", b.cause())));
								});
							}));
						}
						break;
					case "setStopOfficerId":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "stopOfficerId")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopOfficerId failed", b.cause())));
								});
							}));
						} else {
							o2.setStopOfficerId(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "stopOfficerId", o2.jsonStopOfficerId())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopOfficerId failed", b.cause())));
								});
							}));
						}
						break;
					case "setStopLocationId":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "stopLocationId")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopLocationId failed", b.cause())));
								});
							}));
						} else {
							o2.setStopLocationId(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "stopLocationId", o2.jsonStopLocationId())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopLocationId failed", b.cause())));
								});
							}));
						}
						break;
					case "setStopCityId":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "stopCityId")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopCityId failed", b.cause())));
								});
							}));
						} else {
							o2.setStopCityId(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "stopCityId", o2.jsonStopCityId())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.stopCityId failed", b.cause())));
								});
							}));
						}
						break;
					case "setPersonTypeId":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "personTypeId")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.personTypeId failed", b.cause())));
								});
							}));
						} else {
							o2.setPersonTypeId(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "personTypeId", o2.jsonPersonTypeId())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.personTypeId failed", b.cause())));
								});
							}));
						}
						break;
					case "setPersonGenderId":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "personGenderId")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.personGenderId failed", b.cause())));
								});
							}));
						} else {
							o2.setPersonGenderId(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "personGenderId", o2.jsonPersonGenderId())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.personGenderId failed", b.cause())));
								});
							}));
						}
						break;
					case "setPersonEthnicityId":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "personEthnicityId")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.personEthnicityId failed", b.cause())));
								});
							}));
						} else {
							o2.setPersonEthnicityId(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "personEthnicityId", o2.jsonPersonEthnicityId())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.personEthnicityId failed", b.cause())));
								});
							}));
						}
						break;
					case "setPersonRaceId":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "personRaceId")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.personRaceId failed", b.cause())));
								});
							}));
						} else {
							o2.setPersonRaceId(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "personRaceId", o2.jsonPersonRaceId())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.personRaceId failed", b.cause())));
								});
							}));
						}
						break;
					case "setSearchTypeNum":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "searchTypeNum")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.searchTypeNum failed", b.cause())));
								});
							}));
						} else {
							o2.setSearchTypeNum(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "searchTypeNum", o2.jsonSearchTypeNum())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.searchTypeNum failed", b.cause())));
								});
							}));
						}
						break;
					case "setSearchBasisId":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "searchBasisId")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.searchBasisId failed", b.cause())));
								});
							}));
						} else {
							o2.setSearchBasisId(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "searchBasisId", o2.jsonSearchBasisId())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.searchBasisId failed", b.cause())));
								});
							}));
						}
						break;
					case "setSearchBasisTitle":
						if(jsonObject.getString(methodName) == null) {
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_removeD)
										.execute(Tuple.of(pk, "searchBasisTitle")
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.searchBasisTitle failed", b.cause())));
								});
							}));
						} else {
							o2.setSearchBasisTitle(jsonObject.getString(methodName));
							futures.add(Future.future(a -> {
								tx.preparedQuery(SiteContextEnUS.SQL_setD)
										.execute(Tuple.of(pk, "searchBasisTitle", o2.jsonSearchBasisTitle())
										, b
								-> {
									if(b.succeeded())
										a.handle(Future.succeededFuture());
									else
										a.handle(Future.failedFuture(new Exception("value SearchBasis.searchBasisTitle failed", b.cause())));
								});
							}));
						}
						break;
				}
			}
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					SearchBasis o3 = new SearchBasis();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
					eventHandler.handle(Future.succeededFuture(o3));
				} else {
					LOGGER.error(String.format("sqlPATCHSearchBasis failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPATCHSearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void patchSearchBasisResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PATCHSearchBasis(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("patchSearchBasisResponse failed. ", a.cause()));
					errorSearchBasis(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("patchSearchBasisResponse failed. ", ex));
			errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PATCHSearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PATCHSearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// GET //

	@Override
	public void getSearchBasis(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSearchBasis(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/search-basis/{id}");
		siteRequest.setRequestMethod("GET");
		try {
			{
				userSearchBasis(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchSearchBasis(siteRequest, false, true, false, "/api/search-basis/{id}", "GET", c -> {
							if(c.succeeded()) {
								SearchList<SearchBasis> listSearchBasis = c.result();
								getSearchBasisResponse(listSearchBasis, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("getSearchBasis succeeded. "));
									} else {
										LOGGER.error(String.format("getSearchBasis failed. ", d.cause()));
										errorSearchBasis(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("getSearchBasis failed. ", c.cause()));
								errorSearchBasis(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("getSearchBasis failed. ", b.cause()));
						errorSearchBasis(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("getSearchBasis failed. ", ex));
			errorSearchBasis(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void getSearchBasisResponse(SearchList<SearchBasis> listSearchBasis, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSearchBasis.getSiteRequest_();
		try {
			response200GETSearchBasis(listSearchBasis, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("getSearchBasisResponse failed. ", a.cause()));
					errorSearchBasis(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("getSearchBasisResponse failed. ", ex));
			errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200GETSearchBasis(SearchList<SearchBasis> listSearchBasis, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSearchBasis.getSiteRequest_();
			SolrDocumentList solrDocuments = listSearchBasis.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listSearchBasis.getList().stream().findFirst().orElse(null));
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200GETSearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// Search //

	@Override
	public void searchSearchBasis(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSearchBasis(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/search-basis");
		siteRequest.setRequestMethod("Search");
		try {
			{
				userSearchBasis(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchSearchBasis(siteRequest, false, true, false, "/api/search-basis", "Search", c -> {
							if(c.succeeded()) {
								SearchList<SearchBasis> listSearchBasis = c.result();
								searchSearchBasisResponse(listSearchBasis, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("searchSearchBasis succeeded. "));
									} else {
										LOGGER.error(String.format("searchSearchBasis failed. ", d.cause()));
										errorSearchBasis(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("searchSearchBasis failed. ", c.cause()));
								errorSearchBasis(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("searchSearchBasis failed. ", b.cause()));
						errorSearchBasis(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("searchSearchBasis failed. ", ex));
			errorSearchBasis(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void searchSearchBasisResponse(SearchList<SearchBasis> listSearchBasis, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSearchBasis.getSiteRequest_();
		try {
			response200SearchSearchBasis(listSearchBasis, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("searchSearchBasisResponse failed. ", a.cause()));
					errorSearchBasis(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchSearchBasisResponse failed. ", ex));
			errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchSearchBasis(SearchList<SearchBasis> listSearchBasis, Handler<AsyncResult<OperationResponse>> eventHandler) {
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

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			json.put("searchTime", searchTime);
			json.put("transmissionTime", transmissionTime);
			JsonArray l = new JsonArray();
			listSearchBasis.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
				List<String> fls = listSearchBasis.getFields();
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
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200SearchSearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// AdminSearch //

	@Override
	public void adminsearchSearchBasis(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSearchBasis(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/admin/search-basis");
		siteRequest.setRequestMethod("AdminSearch");
		try {
			{
				userSearchBasis(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchSearchBasis(siteRequest, false, true, false, "/api/admin/search-basis", "AdminSearch", c -> {
							if(c.succeeded()) {
								SearchList<SearchBasis> listSearchBasis = c.result();
								adminsearchSearchBasisResponse(listSearchBasis, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("adminsearchSearchBasis succeeded. "));
									} else {
										LOGGER.error(String.format("adminsearchSearchBasis failed. ", d.cause()));
										errorSearchBasis(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("adminsearchSearchBasis failed. ", c.cause()));
								errorSearchBasis(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("adminsearchSearchBasis failed. ", b.cause()));
						errorSearchBasis(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("adminsearchSearchBasis failed. ", ex));
			errorSearchBasis(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void adminsearchSearchBasisResponse(SearchList<SearchBasis> listSearchBasis, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSearchBasis.getSiteRequest_();
		try {
			response200AdminSearchSearchBasis(listSearchBasis, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("adminsearchSearchBasisResponse failed. ", a.cause()));
					errorSearchBasis(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("adminsearchSearchBasisResponse failed. ", ex));
			errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200AdminSearchSearchBasis(SearchList<SearchBasis> listSearchBasis, Handler<AsyncResult<OperationResponse>> eventHandler) {
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

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			json.put("searchTime", searchTime);
			json.put("transmissionTime", transmissionTime);
			JsonArray l = new JsonArray();
			listSearchBasis.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
				List<String> fls = listSearchBasis.getFields();
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
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200AdminSearchSearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// SearchPage //

	@Override
	public void searchpageSearchBasisId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		searchpageSearchBasis(operationRequest, eventHandler);
	}

	@Override
	public void searchpageSearchBasis(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSearchBasis(siteContext, operationRequest);
		siteRequest.setRequestUri("/search-basis");
		siteRequest.setRequestMethod("SearchPage");
		try {
			{
				userSearchBasis(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchSearchBasis(siteRequest, false, true, false, "/search-basis", "SearchPage", c -> {
							if(c.succeeded()) {
								SearchList<SearchBasis> listSearchBasis = c.result();
								searchpageSearchBasisResponse(listSearchBasis, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("searchpageSearchBasis succeeded. "));
									} else {
										LOGGER.error(String.format("searchpageSearchBasis failed. ", d.cause()));
										errorSearchBasis(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("searchpageSearchBasis failed. ", c.cause()));
								errorSearchBasis(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("searchpageSearchBasis failed. ", b.cause()));
						errorSearchBasis(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("searchpageSearchBasis failed. ", ex));
			errorSearchBasis(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void searchpageSearchBasisPageInit(SearchBasisPage page, SearchList<SearchBasis> listSearchBasis) {
	}
	public void searchpageSearchBasisResponse(SearchList<SearchBasis> listSearchBasis, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSearchBasis.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200SearchPageSearchBasis(listSearchBasis, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("searchpageSearchBasisResponse failed. ", a.cause()));
					errorSearchBasis(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchpageSearchBasisResponse failed. ", ex));
			errorSearchBasis(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchPageSearchBasis(SearchList<SearchBasis> listSearchBasis, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
			eventHandler.handle(Future.succeededFuture(new OperationResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOGGER.error(String.format("response200SearchPageSearchBasis failed. ", e));
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
														LOGGER.error(String.format("refreshSearchBasis failed. ", h.cause()));
														errorSearchBasis(siteRequest, null, h);
													}
												});
											} else {
												LOGGER.error(String.format("defineIndexSearchBasis failed. ", g.cause()));
												errorSearchBasis(siteRequest, null, g);
											}
										});
									} else {
										LOGGER.error(String.format("defineIndexSearchBasis failed. ", f.cause()));
										errorSearchBasis(siteRequest, null, f);
									}
								});
							} else {
								LOGGER.error(String.format("defineIndexSearchBasis failed. ", e.cause()));
								errorSearchBasis(siteRequest, null, e);
							}
						});
					} else {
						LOGGER.error(String.format("defineIndexSearchBasis failed. ", d.cause()));
						errorSearchBasis(siteRequest, null, d);
					}
				});
			} else {
				LOGGER.error(String.format("defineIndexSearchBasis failed. ", c.cause()));
				errorSearchBasis(siteRequest, null, c);
			}
		});
		return promise.future();
	}

	public void createSearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<SearchBasis>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();
			String userId = siteRequest.getUserId();
			ZonedDateTime created = Optional.ofNullable(siteRequest.getJsonObject()).map(j -> j.getString("created")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())))).orElse(ZonedDateTime.now(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())));

			tx.preparedQuery(SiteContextEnUS.SQL_create)
					.collecting(Collectors.toList())
					.execute(Tuple.of(SearchBasis.class.getCanonicalName(), userId, created.toOffsetDateTime())
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
					LOGGER.error(String.format("createSearchBasis failed. ", createAsync.cause()));
					eventHandler.handle(Future.failedFuture(createAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("createSearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void errorSearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler, AsyncResult<?> resultAsync) {
		Throwable e = resultAsync.cause();
		JsonObject json = new JsonObject()
				.put("error", new JsonObject()
				.put("message", Optional.ofNullable(e).map(Throwable::getMessage).orElse(null))
				.put("userName", siteRequest.getUserName())
				.put("userFullName", siteRequest.getUserFullName())
				.put("requestUri", siteRequest.getRequestUri())
				.put("requestMethod", siteRequest.getRequestMethod())
				.put("params", Optional.ofNullable(siteRequest.getOperationRequest()).map(o -> o.getParams()).orElse(null))
				);
		ExceptionUtils.printRootCauseStackTrace(e);
		OperationResponse responseOperation = new OperationResponse(400, "BAD REQUEST", 
				Buffer.buffer().appendString(json.encodePrettily())
				, MultiMap.caseInsensitiveMultiMap().add("Content-Type", "application/json")
		);
		SiteConfig siteConfig = siteRequest.getSiteConfig_();
		SiteContextEnUS siteContext = siteRequest.getSiteContext_();
		MailClient mailClient = siteContext.getMailClient();
		MailMessage message = new MailMessage();
		message.setFrom(siteConfig.getEmailFrom());
		message.setTo(siteConfig.getEmailAdmin());
		if(e != null)
			message.setText(String.format("%s\n\n%s", json.encodePrettily(), ExceptionUtils.getStackTrace(e)));
		message.setSubject(String.format(siteConfig.getSiteBaseUrl() + " " + Optional.ofNullable(e).map(Throwable::getMessage).orElse(null)));
		WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
		workerExecutor.executeBlocking(
			blockingCodeHandler -> {
				mailClient.sendMail(message, result -> {
					if (result.succeeded()) {
						LOGGER.info(result.result());
					} else {
						LOGGER.error(result.cause());
					}
				});
			}, resultHandler -> {
			}
		);
		sqlRollbackSearchBasis(siteRequest, a -> {
			if(a.succeeded()) {
				LOGGER.info(String.format("sql rollback. "));
				sqlCloseSearchBasis(siteRequest, b -> {
					if(b.succeeded()) {
						LOGGER.info(String.format("sql close. "));
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
	}

	public void sqlConnectionSearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
						LOGGER.error(String.format("sqlConnectionSearchBasis failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlSearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlTransactionSearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();

			if(sqlConnection == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				Transaction tx = sqlConnection.begin();
				siteRequest.setTx(tx);
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlTransactionSearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCommitSearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();

			if(tx == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				tx.commit(a -> {
					if(a.succeeded()) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else if("Transaction already completed".equals(a.cause().getMessage())) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOGGER.error(String.format("sqlCommitSearchBasis failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlSearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlRollbackSearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();

			if(tx == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				tx.rollback(a -> {
					if(a.succeeded()) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else if("Transaction already completed".equals(a.cause().getMessage())) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOGGER.error(String.format("sqlRollbackSearchBasis failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlSearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCloseSearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();

			if(sqlConnection == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				sqlConnection.close();
				siteRequest.setSqlConnection(null);
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOGGER.error(String.format("sqlCloseSearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SiteRequestEnUS generateSiteRequestEnUSForSearchBasis(SiteContextEnUS siteContext, OperationRequest operationRequest) {
		return generateSiteRequestEnUSForSearchBasis(siteContext, operationRequest, null);
	}

	public SiteRequestEnUS generateSiteRequestEnUSForSearchBasis(SiteContextEnUS siteContext, OperationRequest operationRequest, JsonObject body) {
		Vertx vertx = siteContext.getVertx();
		SiteRequestEnUS siteRequest = new SiteRequestEnUS();
		siteRequest.setJsonObject(body);
		siteRequest.setVertx(vertx);
		siteRequest.setSiteContext_(siteContext);
		siteRequest.setSiteConfig_(siteContext.getSiteConfig());
		siteRequest.setOperationRequest(operationRequest);
		siteRequest.initDeepSiteRequestEnUS(siteRequest);

		return siteRequest;
	}

	public void userSearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			String userId = siteRequest.getUserId();
			if(userId == null) {
				eventHandler.handle(Future.succeededFuture());
			} else {
				sqlConnectionSearchBasis(siteRequest, a -> {
					if(a.succeeded()) {
						sqlTransactionSearchBasis(siteRequest, b -> {
							if(b.succeeded()) {
								Transaction tx = siteRequest.getTx();
								tx.preparedQuery(SiteContextEnUS.SQL_selectC)
										.collecting(Collectors.toList())
										.execute(Tuple.of("com.opendatapolicing.enus.user.SiteUser", userId)
										, selectCAsync
								-> {
									if(selectCAsync.succeeded()) {
										try {
											Row userValues = selectCAsync.result().value().stream().findFirst().orElse(null);
											SiteUserEnUSApiServiceImpl userService = new SiteUserEnUSApiServiceImpl(siteContext);
											if(userValues == null) {
												JsonObject userVertx = siteRequest.getOperationRequest().getUser();
												OAuth2TokenImpl token = new OAuth2TokenImpl(siteContext.getAuthProvider(), userVertx);
												JsonObject jsonPrincipal = token.accessToken();

												JsonObject jsonObject = new JsonObject();
												jsonObject.put("userName", jsonPrincipal.getString("preferred_username"));
												jsonObject.put("userFirstName", jsonPrincipal.getString("given_name"));
												jsonObject.put("userLastName", jsonPrincipal.getString("family_name"));
												jsonObject.put("userCompleteName", jsonPrincipal.getString("name"));
												jsonObject.put("userId", jsonPrincipal.getString("sub"));
												jsonObject.put("userEmail", jsonPrincipal.getString("email"));
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

												userService.createSiteUser(siteRequest2, c -> {
													if(c.succeeded()) {
														SiteUser siteUser = c.result();
														userService.sqlPOSTSiteUser(siteUser, false, d -> {
															if(d.succeeded()) {
																userService.defineIndexSiteUser(siteUser, e -> {
																	if(e.succeeded()) {
																		siteRequest.setSiteUser(siteUser);
																		siteRequest.setUserName(jsonPrincipal.getString("preferred_username"));
																		siteRequest.setUserFirstName(jsonPrincipal.getString("given_name"));
																		siteRequest.setUserLastName(jsonPrincipal.getString("family_name"));
																		siteRequest.setUserEmail(jsonPrincipal.getString("email"));
																		siteRequest.setUserId(jsonPrincipal.getString("sub"));
																		siteRequest.setUserKey(siteUser.getPk());
																		eventHandler.handle(Future.succeededFuture());
																	} else {
																		errorSearchBasis(siteRequest, eventHandler, e);
																	}
																});
															} else {
																errorSearchBasis(siteRequest, eventHandler, d);
															}
														});
													} else {
														errorSearchBasis(siteRequest, eventHandler, c);
													}
												});
											} else {
												Long pkUser = userValues.getLong(0);
												SearchList<SiteUser> searchList = new SearchList<SiteUser>();
												searchList.setQuery("*:*");
												searchList.setStore(true);
												searchList.setC(SiteUser.class);
												searchList.addFilterQuery("userId_indexed_string:" + ClientUtils.escapeQueryChars(userId));
												searchList.addFilterQuery("pk_indexed_long:" + pkUser);
												searchList.initDeepSearchList(siteRequest);
												SiteUser siteUser1 = searchList.getList().stream().findFirst().orElse(null);

												JsonObject userVertx = siteRequest.getOperationRequest().getUser();
												OAuth2TokenImpl token = new OAuth2TokenImpl(siteContext.getAuthProvider(), userVertx);
												JsonObject jsonPrincipal = token.accessToken();

												JsonObject jsonObject = new JsonObject();
												jsonObject.put("setUserName", jsonPrincipal.getString("preferred_username"));
												jsonObject.put("setUserFirstName", jsonPrincipal.getString("given_name"));
												jsonObject.put("setUserLastName", jsonPrincipal.getString("family_name"));
												jsonObject.put("setUserCompleteName", jsonPrincipal.getString("name"));
												jsonObject.put("setUserId", jsonPrincipal.getString("sub"));
												jsonObject.put("setUserEmail", jsonPrincipal.getString("email"));
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
													siteRequest2.setUserKey(siteRequest.getUserKey());
													siteRequest2.initDeepSiteRequestEnUS(siteRequest);
													siteUser.setSiteRequest_(siteRequest2);

													ApiRequest apiRequest = new ApiRequest();
													apiRequest.setRows(1);
													apiRequest.setNumFound(1L);
													apiRequest.setNumPATCH(0L);
													apiRequest.initDeepApiRequest(siteRequest2);
													siteRequest2.setApiRequest_(apiRequest);

													userService.sqlPATCHSiteUser(siteUser, false, d -> {
														if(d.succeeded()) {
															SiteUser siteUser2 = d.result();
															userService.defineIndexSiteUser(siteUser2, e -> {
																if(e.succeeded()) {
																	siteRequest.setSiteUser(siteUser2);
																	siteRequest.setUserName(siteUser2.getUserName());
																	siteRequest.setUserFirstName(siteUser2.getUserFirstName());
																	siteRequest.setUserLastName(siteUser2.getUserLastName());
																	siteRequest.setUserId(siteUser2.getUserId());
																	siteRequest.setUserKey(siteUser2.getPk());
																	eventHandler.handle(Future.succeededFuture());
																} else {
																	errorSearchBasis(siteRequest, eventHandler, e);
																}
															});
														} else {
															errorSearchBasis(siteRequest, eventHandler, d);
														}
													});
												} else {
													siteRequest.setSiteUser(siteUser1);
													siteRequest.setUserName(siteUser1.getUserName());
													siteRequest.setUserFirstName(siteUser1.getUserFirstName());
													siteRequest.setUserLastName(siteUser1.getUserLastName());
													siteRequest.setUserId(siteUser1.getUserId());
													siteRequest.setUserKey(siteUser1.getPk());
													sqlRollbackSearchBasis(siteRequest, c -> {
														if(c.succeeded()) {
															eventHandler.handle(Future.succeededFuture());
														} else {
															eventHandler.handle(Future.failedFuture(c.cause()));
															errorSearchBasis(siteRequest, eventHandler, c);
														}
													});
												}
											}
										} catch(Exception e) {
											LOGGER.error(String.format("userSearchBasis failed. ", e));
											eventHandler.handle(Future.failedFuture(e));
										}
									} else {
										LOGGER.error(String.format("userSearchBasis failed. ", selectCAsync.cause()));
										eventHandler.handle(Future.failedFuture(selectCAsync.cause()));
									}
								});
							} else {
								LOGGER.error(String.format("userSearchBasis failed. ", b.cause()));
								eventHandler.handle(Future.failedFuture(b.cause()));
							}
						});
					} else {
						LOGGER.error(String.format("userSearchBasis failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOGGER.error(String.format("userSearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
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

	public void aSearchSearchBasisFq(String uri, String apiMethod, SearchList<SearchBasis> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		if(StringUtils.startsWith(valueIndexed, "[")) {
			String[] fqs = StringUtils.substringBefore(StringUtils.substringAfter(valueIndexed, "["), "]").split(" TO ");
			if(fqs.length != 2)
				throw new RuntimeException(String.format("\"%s\" invalid range query. ", valueIndexed));
			String fq1 = fqs[0].equals("*") ? fqs[0] : SearchBasis.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[0]);
			String fq2 = fqs[1].equals("*") ? fqs[1] : SearchBasis.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[1]);
			searchList.addFilterQuery(varIndexed + ":[" + fq1 + " TO " + fq2 + "]");
		} else {
			searchList.addFilterQuery(varIndexed + ":" + SearchBasis.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), valueIndexed));
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

	public void varsSearchBasis(SiteRequestEnUS siteRequest, Handler<AsyncResult<SearchList<OperationResponse>>> eventHandler) {
		try {
			OperationRequest operationRequest = siteRequest.getOperationRequest();

			operationRequest.getParams().getJsonObject("query").forEach(paramRequest -> {
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
					LOGGER.error(String.format("aSearchSearchBasis failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOGGER.error(String.format("aSearchSearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void aSearchSearchBasis(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, Handler<AsyncResult<SearchList<SearchBasis>>> eventHandler) {
		try {
			SearchList<SearchBasis> searchList = aSearchSearchBasisList(siteRequest, populate, store, modify, uri, apiMethod);
			eventHandler.handle(Future.succeededFuture(searchList));
		} catch(Exception e) {
			LOGGER.error(String.format("aSearchSearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SearchList<SearchBasis> aSearchSearchBasisList(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod) {
		OperationRequest operationRequest = siteRequest.getOperationRequest();
		String entityListStr = siteRequest.getOperationRequest().getParams().getJsonObject("query").getString("fl");
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

		String id = operationRequest.getParams().getJsonObject("path").getString("id");
		if(id != null) {
			searchList.addFilterQuery("(pk_indexed_long:" + ClientUtils.escapeQueryChars(id) + " OR objectId_indexed_string:" + ClientUtils.escapeQueryChars(id) + ")");
		}

		operationRequest.getParams().getJsonObject("query").forEach(paramRequest -> {
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
							entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
							valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
							varIndexed = SearchBasis.varIndexedSearchBasis(entityVar);
							aSearchSearchBasisFq(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
							break;
						case "sort":
							entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, " "));
							valueIndexed = StringUtils.trim(StringUtils.substringAfter((String)paramObject, " "));
							varIndexed = SearchBasis.varIndexedSearchBasis(entityVar);
							aSearchSearchBasisSort(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
							break;
						case "start":
							valueStart = (Integer)paramObject;
							aSearchSearchBasisStart(uri, apiMethod, searchList, valueStart);
							break;
						case "rows":
							valueRows = (Integer)paramObject;
							aSearchSearchBasisRows(uri, apiMethod, searchList, valueRows);
							break;
						case "var":
							entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
							valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
							aSearchSearchBasisVar(uri, apiMethod, searchList, entityVar, valueIndexed);
							break;
					}
				}
				aSearchSearchBasisUri(uri, apiMethod, searchList);
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

	public void defineSearchBasis(SearchBasis o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			Transaction tx = siteRequest.getTx();
			Long pk = o.getPk();
			tx.preparedQuery(SiteContextEnUS.SQL_define)
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
										LOGGER.error(String.format("defineSearchBasis failed. ", e));
										LOGGER.error(e);
									}
								}
							}
						}
						eventHandler.handle(Future.succeededFuture());
					} catch(Exception e) {
						LOGGER.error(String.format("defineSearchBasis failed. ", e));
						eventHandler.handle(Future.failedFuture(e));
					}
				} else {
					LOGGER.error(String.format("defineSearchBasis failed. ", defineAsync.cause()));
					eventHandler.handle(Future.failedFuture(defineAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("defineSearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void attributeSearchBasis(SearchBasis o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			Transaction tx = siteRequest.getTx();
			Long pk = o.getPk();
			tx.preparedQuery(SiteContextEnUS.SQL_attribute)
					.collecting(Collectors.toList())
					.execute(Tuple.of(pk, pk)
					, attributeAsync
			-> {
				try {
					if(attributeAsync.succeeded()) {
						if(attributeAsync.result() != null) {
							for(Row definition : attributeAsync.result().value()) {
								if(pk.equals(definition.getLong(0)))
									o.attributeForClass(definition.getString(2), definition.getLong(1));
								else
									o.attributeForClass(definition.getString(3), definition.getLong(0));
							}
						}
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOGGER.error(String.format("attributeSearchBasis failed. ", attributeAsync.cause()));
						eventHandler.handle(Future.failedFuture(attributeAsync.cause()));
					}
				} catch(Exception e) {
					LOGGER.error(String.format("attributeSearchBasis failed. ", e));
					eventHandler.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("attributeSearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void indexSearchBasis(SearchBasis o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			o.initDeepForClass(siteRequest);
			o.indexForClass();
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOGGER.error(String.format("indexSearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void refreshSearchBasis(SearchBasis o, Handler<AsyncResult<OperationResponse>> eventHandler) {
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
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSearchBasis(siteContext, siteRequest.getOperationRequest(), new JsonObject());
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
										LOGGER.info(String.format("TrafficSearch %s failed. ", pk2));
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
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSearchBasis(siteContext, siteRequest.getOperationRequest(), new JsonObject());
							o2.setSiteRequest_(siteRequest2);
							futures2.add(
								service.patchSearchBasisFuture(o2, false, b -> {
									if(b.succeeded()) {
									} else {
										LOGGER.info(String.format("SearchBasis %s failed. ", o2.getPk()));
										eventHandler.handle(Future.failedFuture(b.cause()));
									}
								})
							);
						}

						CompositeFuture.all(futures2).onComplete(b -> {
							if(b.succeeded()) {
								eventHandler.handle(Future.succeededFuture());
							} else {
								LOGGER.error("Refresh relations failed. ", b.cause());
								errorSearchBasis(siteRequest, eventHandler, b);
							}
						});
					} else {
						LOGGER.error("Refresh relations failed. ", a.cause());
						errorSearchBasis(siteRequest, eventHandler, a);
					}
				});
			} else {
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOGGER.error(String.format("refreshSearchBasis failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}
}
