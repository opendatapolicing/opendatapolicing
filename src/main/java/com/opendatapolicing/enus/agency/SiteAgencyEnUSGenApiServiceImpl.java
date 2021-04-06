package com.opendatapolicing.enus.agency;

import com.opendatapolicing.enus.state.SiteStateEnUSApiServiceImpl;
import com.opendatapolicing.enus.state.SiteState;
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
public class SiteAgencyEnUSGenApiServiceImpl implements SiteAgencyEnUSGenApiService {

	protected static final Logger LOG = LoggerFactory.getLogger(SiteAgencyEnUSGenApiServiceImpl.class);

	protected static final String SERVICE_ADDRESS = "SiteAgencyEnUSApiServiceImpl";

	protected SiteContextEnUS siteContext;

	public SiteAgencyEnUSGenApiServiceImpl(SiteContextEnUS siteContext) {
		this.siteContext = siteContext;
	}

	// PUTImport //

	@Override
	public void putimportSiteAgency(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("putimportSiteAgency started. "));
		userSiteAgency(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/agency/import");
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
						putimportSiteAgencyResponse(siteRequest, c -> {
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
											siteRequest.getVertx().eventBus().publish("websocketSiteAgency", JsonObject.mapFrom(apiRequest).toString());
											varsSiteAgency(siteRequest, d -> {
												if(d.succeeded()) {
													listPUTImportSiteAgency(apiRequest, siteRequest, e -> {
														if(e.succeeded()) {
															putimportSiteAgencyResponse(siteRequest, f -> {
																if(e.succeeded()) {
																	LOG.info(String.format("putimportSiteAgency succeeded. "));
																	blockingCodeHandler.handle(Future.succeededFuture(e.result()));
																} else {
																	LOG.error(String.format("putimportSiteAgency failed. ", f.cause()));
																	errorSiteAgency(siteRequest, null, f);
																}
															});
														} else {
															LOG.error(String.format("putimportSiteAgency failed. ", e.cause()));
															errorSiteAgency(siteRequest, null, e);
														}
													});
												} else {
													LOG.error(String.format("putimportSiteAgency failed. ", d.cause()));
													errorSiteAgency(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putimportSiteAgency failed. ", ex));
											errorSiteAgency(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putimportSiteAgency failed. ", c.cause()));
								errorSiteAgency(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putimportSiteAgency failed. ", ex));
					errorSiteAgency(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("putimportSiteAgency failed. ", ex));
						errorSiteAgency(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("putimportSiteAgency failed. ", b.cause()));
					errorSiteAgency(null, eventHandler, b);
				}
			}
		});
	}


	public void listPUTImportSiteAgency(ApiRequest apiRequest, SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				json.put("created", json.getValue("created"));

				SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSiteAgency(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), json);
				siteRequest2.setApiRequest_(apiRequest);
				siteRequest2.setRequestVars(siteRequest.getRequestVars());

				SearchList<SiteAgency> searchList = new SearchList<SiteAgency>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(SiteAgency.class);
				searchList.addFilterQuery("deleted_indexed_boolean:false");
				searchList.addFilterQuery("archived_indexed_boolean:false");
				searchList.addFilterQuery("inheritPk_indexed_long:" + json.getString("pk"));
				searchList.initDeepForClass(siteRequest2);

				if(searchList.size() == 1) {
					SiteAgency o = searchList.getList().stream().findFirst().orElse(null);
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
							patchSiteAgencyFuture(o, true).onFailure(ex -> {
								LOG.error(String.format("listPUTImportSiteAgency failed. ", ex));
								errorSiteAgency(siteRequest2, eventHandler, Future.failedFuture(ex));
							})
						);
					}
				} else {
					futures.add(
						postSiteAgencyFuture(siteRequest2, true).onFailure(ex -> {
							LOG.error(String.format("listPUTImportSiteAgency failed. ", ex));
							errorSiteAgency(siteRequest2, eventHandler, Future.failedFuture(ex));
						})
					);
				}
			});
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
					response200PUTImportSiteAgency(siteRequest, eventHandler);
				} else {
					LOG.error(String.format("listPUTImportSiteAgency failed. ", a.cause()));
					errorSiteAgency(apiRequest.getSiteRequest_(), eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("listPUTImportSiteAgency failed. ", ex));
			errorSiteAgency(siteRequest, null, Future.failedFuture(ex));
		}
	}

	public void putimportSiteAgencyResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PUTImportSiteAgency(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("putimportSiteAgencyResponse failed. ", a.cause()));
					errorSiteAgency(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("putimportSiteAgencyResponse failed. ", ex));
			errorSiteAgency(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTImportSiteAgency(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTImportSiteAgency failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTMerge //

	@Override
	public void putmergeSiteAgency(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("putmergeSiteAgency started. "));
		userSiteAgency(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/agency/merge");
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
						putmergeSiteAgencyResponse(siteRequest, c -> {
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
											siteRequest.getVertx().eventBus().publish("websocketSiteAgency", JsonObject.mapFrom(apiRequest).toString());
											varsSiteAgency(siteRequest, d -> {
												if(d.succeeded()) {
													listPUTMergeSiteAgency(apiRequest, siteRequest, e -> {
														if(e.succeeded()) {
															putmergeSiteAgencyResponse(siteRequest, f -> {
																if(e.succeeded()) {
																	LOG.info(String.format("putmergeSiteAgency succeeded. "));
																	blockingCodeHandler.handle(Future.succeededFuture(e.result()));
																} else {
																	LOG.error(String.format("putmergeSiteAgency failed. ", f.cause()));
																	errorSiteAgency(siteRequest, null, f);
																}
															});
														} else {
															LOG.error(String.format("putmergeSiteAgency failed. ", e.cause()));
															errorSiteAgency(siteRequest, null, e);
														}
													});
												} else {
													LOG.error(String.format("putmergeSiteAgency failed. ", d.cause()));
													errorSiteAgency(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putmergeSiteAgency failed. ", ex));
											errorSiteAgency(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putmergeSiteAgency failed. ", c.cause()));
								errorSiteAgency(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putmergeSiteAgency failed. ", ex));
					errorSiteAgency(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("putmergeSiteAgency failed. ", ex));
						errorSiteAgency(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("putmergeSiteAgency failed. ", b.cause()));
					errorSiteAgency(null, eventHandler, b);
				}
			}
		});
	}


	public void listPUTMergeSiteAgency(ApiRequest apiRequest, SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				JsonObject json = (JsonObject)obj;

				json.put("inheritPk", json.getValue("pk"));

				SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSiteAgency(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), json);
				siteRequest2.setApiRequest_(apiRequest);
				siteRequest2.setRequestVars(siteRequest.getRequestVars());

				SearchList<SiteAgency> searchList = new SearchList<SiteAgency>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(SiteAgency.class);
				searchList.addFilterQuery("deleted_indexed_boolean:false");
				searchList.addFilterQuery("archived_indexed_boolean:false");
				searchList.addFilterQuery("pk_indexed_long:" + json.getString("pk"));
				searchList.initDeepForClass(siteRequest2);

				if(searchList.size() == 1) {
					SiteAgency o = searchList.getList().stream().findFirst().orElse(null);
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
							patchSiteAgencyFuture(o, false).onFailure(ex -> {
								LOG.error(String.format("listPUTMergeSiteAgency failed. ", ex));
								errorSiteAgency(siteRequest2, eventHandler, Future.failedFuture(ex));
							})
						);
					}
				} else {
					futures.add(
						postSiteAgencyFuture(siteRequest2, false).onFailure(ex -> {
							LOG.error(String.format("listPUTMergeSiteAgency failed. ", ex));
							errorSiteAgency(siteRequest2, eventHandler, Future.failedFuture(ex));
						})
					);
				}
			});
			CompositeFuture.all(futures).onComplete( a -> {
				if(a.succeeded()) {
					apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
					response200PUTMergeSiteAgency(siteRequest, eventHandler);
				} else {
					LOG.error(String.format("listPUTMergeSiteAgency failed. ", a.cause()));
					errorSiteAgency(apiRequest.getSiteRequest_(), eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("listPUTMergeSiteAgency failed. ", ex));
			errorSiteAgency(siteRequest, null, Future.failedFuture(ex));
		}
	}

	public void putmergeSiteAgencyResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PUTMergeSiteAgency(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("putmergeSiteAgencyResponse failed. ", a.cause()));
					errorSiteAgency(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("putmergeSiteAgencyResponse failed. ", ex));
			errorSiteAgency(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTMergeSiteAgency(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTMergeSiteAgency failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUTCopy //

	@Override
	public void putcopySiteAgency(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("putcopySiteAgency started. "));
		userSiteAgency(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/agency/copy");
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
						putcopySiteAgencyResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											aSearchSiteAgency(siteRequest, false, true, true, "/api/agency/copy", "PUTCopy", d -> {
												if(d.succeeded()) {
													SearchList<SiteAgency> listSiteAgency = d.result();
													ApiRequest apiRequest = new ApiRequest();
													apiRequest.setRows(listSiteAgency.getRows());
													apiRequest.setNumFound(listSiteAgency.getQueryResponse().getResults().getNumFound());
													apiRequest.setNumPATCH(0L);
													apiRequest.initDeepApiRequest(siteRequest);
													siteRequest.setApiRequest_(apiRequest);
													siteRequest.getVertx().eventBus().publish("websocketSiteAgency", JsonObject.mapFrom(apiRequest).toString());
													try {
														listPUTCopySiteAgency(apiRequest, listSiteAgency, e -> {
															if(e.succeeded()) {
																putcopySiteAgencyResponse(siteRequest, f -> {
																	if(f.succeeded()) {
																		LOG.info(String.format("putcopySiteAgency succeeded. "));
																		blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																	} else {
																		LOG.error(String.format("putcopySiteAgency failed. ", f.cause()));
																		errorSiteAgency(siteRequest, null, f);
																	}
																});
															} else {
																LOG.error(String.format("putcopySiteAgency failed. ", e.cause()));
																errorSiteAgency(siteRequest, null, e);
															}
														});
													} catch(Exception ex) {
														LOG.error(String.format("putcopySiteAgency failed. ", ex));
														errorSiteAgency(siteRequest, null, Future.failedFuture(ex));
													}
												} else {
													LOG.error(String.format("putcopySiteAgency failed. ", d.cause()));
													errorSiteAgency(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("putcopySiteAgency failed. ", ex));
											errorSiteAgency(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("putcopySiteAgency failed. ", c.cause()));
								errorSiteAgency(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putcopySiteAgency failed. ", ex));
					errorSiteAgency(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("putcopySiteAgency failed. ", ex));
						errorSiteAgency(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("putcopySiteAgency failed. ", b.cause()));
					errorSiteAgency(null, eventHandler, b);
				}
			}
		});
	}


	public void listPUTCopySiteAgency(ApiRequest apiRequest, SearchList<SiteAgency> listSiteAgency, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listSiteAgency.getSiteRequest_();
		listSiteAgency.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSiteAgency(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				putcopySiteAgencyFuture(siteRequest2, JsonObject.mapFrom(o)).onFailure(ex -> {
					LOG.error(String.format("listPUTCopySiteAgency failed. ", ex));
					errorSiteAgency(siteRequest, eventHandler, Future.failedFuture(ex));
				})
			);
		});
		CompositeFuture.all(futures).onComplete( a -> {
			if(a.succeeded()) {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + listSiteAgency.size());
				if(listSiteAgency.next()) {
					listPUTCopySiteAgency(apiRequest, listSiteAgency, eventHandler);
				} else {
					response200PUTCopySiteAgency(siteRequest, eventHandler);
				}
			} else {
				LOG.error(String.format("listPUTCopySiteAgency failed. ", a.cause()));
				errorSiteAgency(listSiteAgency.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<SiteAgency> putcopySiteAgencyFuture(SiteRequestEnUS siteRequest, JsonObject jsonObject) {
		Promise<SiteAgency> promise = Promise.promise();

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

			siteRequest.getSiteContext_().getPgPool().withTransaction(sqlConnection -> {
				Promise<SiteAgency> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				createSiteAgency(siteRequest, a -> {
					if(a.succeeded()) {
						SiteAgency siteAgency = a.result();
						sqlPUTCopySiteAgency(siteAgency, jsonObject, b -> {
							if(b.succeeded()) {
								defineSiteAgency(siteAgency, c -> {
									if(c.succeeded()) {
										attributeSiteAgency(siteAgency, d -> {
											if(d.succeeded()) {
												indexSiteAgency(siteAgency, e -> {
													if(e.succeeded()) {
														promise1.complete(siteAgency);
													} else {
														LOG.error(String.format("putcopySiteAgencyFuture failed. ", e.cause()));
														promise1.fail(e.cause());
													}
												});
											} else {
												LOG.error(String.format("putcopySiteAgencyFuture failed. ", d.cause()));
												promise1.fail(d.cause());
											}
										});
									} else {
										LOG.error(String.format("putcopySiteAgencyFuture failed. ", c.cause()));
										promise1.fail(c.cause());
									}
								});
							} else {
								LOG.error(String.format("putcopySiteAgencyFuture failed. ", b.cause()));
								promise1.fail(b.cause());
							}
						});
					} else {
						LOG.error(String.format("putcopySiteAgencyFuture failed. ", a.cause()));
						promise1.fail(a.cause());
					}
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				promise.fail(ex);
				errorSiteAgency(siteRequest, null, Future.failedFuture(ex));
			}).compose(siteAgency -> {
				Promise<SiteAgency> promise2 = Promise.promise();
				refreshSiteAgency(siteAgency, a -> {
					if(a.succeeded()) {
						ApiRequest apiRequest = siteRequest.getApiRequest_();
						if(apiRequest != null) {
							apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
							siteAgency.apiRequestSiteAgency();
							siteRequest.getVertx().eventBus().publish("websocketSiteAgency", JsonObject.mapFrom(apiRequest).toString());
						}
						promise.complete(siteAgency);
					} else {
						LOG.error(String.format("putcopySiteAgencyFuture failed. ", a.cause()));
						promise2.fail(a.cause());
					}
				});
				return promise2.future();
			}).onSuccess(a -> {
				LOG.info(String.format("putcopySiteAgencyFuture succeeded. "));
			}).onFailure(ex -> {
				promise.fail(ex);
				errorSiteAgency(siteRequest, null, promise.future());
			});
		} catch(Exception ex) {
			LOG.error(String.format("putcopySiteAgencyFuture failed. "), ex);
			promise.fail(ex);
			errorSiteAgency(siteRequest, null, promise.future());
		}
		return promise.future();
	}

	public void sqlPUTCopySiteAgency(SiteAgency o, JsonObject jsonObject, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE SiteAgency SET ");
			List<Object> bParams = new ArrayList<Object>();
			SiteAgency o2 = new SiteAgency();
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
					case "agencyName":
						o2.setAgencyName(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("agencyName=$" + num);
						num++;
						bParams.add(o2.sqlAgencyName());
						break;
					case "stateKey":
						{
							Long l = Long.parseLong(jsonObject.getString(entityVar));
							if(l != null) {
								if(bParams.size() > 0) {
									bSql.append(", ");
								}
								bSql.append("stateKey=$" + num);
								num++;
								bParams.add(l);
							}
						}
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
					case "imageCoords":
						o2.setImageCoords(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("imageCoords=$" + num);
						num++;
						bParams.add(o2.sqlImageCoords());
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
					LOG.error(String.format("sqlPUTCopySiteAgency failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPUTCopySiteAgency failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void putcopySiteAgencyResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PUTCopySiteAgency(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("putcopySiteAgencyResponse failed. ", a.cause()));
					errorSiteAgency(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("putcopySiteAgencyResponse failed. ", ex));
			errorSiteAgency(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTCopySiteAgency(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PUTCopySiteAgency failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// POST //

	@Override
	public void postSiteAgency(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("postSiteAgency started. "));
		userSiteAgency(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/agency");
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
						siteRequest.getVertx().eventBus().publish("websocketSiteAgency", JsonObject.mapFrom(apiRequest).toString());
						postSiteAgencyFuture(siteRequest, false).onSuccess(siteAgency -> {
							apiRequest.setPk(siteAgency.getPk());
							postSiteAgencyResponse(siteAgency, d -> {
								if(d.succeeded()) {
									eventHandler.handle(Future.succeededFuture(d.result()));
									LOG.info(String.format("postSiteAgency succeeded. "));
								} else {
									LOG.error(String.format("postSiteAgency failed. ", d.cause()));
									errorSiteAgency(siteRequest, eventHandler, d);
								}
							});
						}).onFailure(ex -> {
							LOG.error(String.format("postSiteAgency failed. ", Future.failedFuture(ex)));
							errorSiteAgency(siteRequest, eventHandler, Future.failedFuture(ex));
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("postSiteAgency failed. ", ex));
					errorSiteAgency(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("postSiteAgency failed. ", ex));
						errorSiteAgency(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("postSiteAgency failed. ", b.cause()));
					errorSiteAgency(null, eventHandler, b);
				}
			}
		});
	}


	public Future<SiteAgency> postSiteAgencyFuture(SiteRequestEnUS siteRequest, Boolean inheritPk) {
		Promise<SiteAgency> promise = Promise.promise();

		try {
			siteRequest.getSiteContext_().getPgPool().withTransaction(sqlConnection -> {
				Promise<SiteAgency> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				createSiteAgency(siteRequest, a -> {
					if(a.succeeded()) {
						SiteAgency siteAgency = a.result();
						sqlPOSTSiteAgency(siteAgency, inheritPk, b -> {
							if(b.succeeded()) {
								defineSiteAgency(siteAgency, c -> {
									if(c.succeeded()) {
										attributeSiteAgency(siteAgency, d -> {
											if(d.succeeded()) {
												indexSiteAgency(siteAgency, e -> {
													if(e.succeeded()) {
														promise1.complete(siteAgency);
													} else {
														LOG.error(String.format("postSiteAgencyFuture failed. ", e.cause()));
														promise1.fail(e.cause());
													}
												});
											} else {
												LOG.error(String.format("postSiteAgencyFuture failed. ", d.cause()));
												promise1.fail(d.cause());
											}
										});
									} else {
										LOG.error(String.format("postSiteAgencyFuture failed. ", c.cause()));
										promise1.fail(c.cause());
									}
								});
							} else {
								LOG.error(String.format("postSiteAgencyFuture failed. ", b.cause()));
								promise1.fail(b.cause());
							}
						});
					} else {
						LOG.error(String.format("postSiteAgencyFuture failed. ", a.cause()));
						promise1.fail(a.cause());
					}
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				promise.fail(ex);
				errorSiteAgency(siteRequest, null, Future.failedFuture(ex));
			}).compose(siteAgency -> {
				Promise<SiteAgency> promise2 = Promise.promise();
				refreshSiteAgency(siteAgency, a -> {
					if(a.succeeded()) {
						ApiRequest apiRequest = siteRequest.getApiRequest_();
						if(apiRequest != null) {
							apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
							siteAgency.apiRequestSiteAgency();
							siteRequest.getVertx().eventBus().publish("websocketSiteAgency", JsonObject.mapFrom(apiRequest).toString());
						}
						promise.complete(siteAgency);
					} else {
						LOG.error(String.format("postSiteAgencyFuture failed. ", a.cause()));
						promise2.fail(a.cause());
					}
				});
				return promise2.future();
			}).onSuccess(a -> {
				LOG.info(String.format("postSiteAgencyFuture succeeded. "));
			}).onFailure(ex -> {
				promise.fail(ex);
				errorSiteAgency(siteRequest, null, promise.future());
			});
		} catch(Exception ex) {
			LOG.error(String.format("postSiteAgencyFuture failed. "), ex);
			promise.fail(ex);
			errorSiteAgency(siteRequest, null, promise.future());
		}
		return promise.future();
	}

	public void sqlPOSTSiteAgency(SiteAgency o, Boolean inheritPk, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE SiteAgency SET ");
			List<Object> bParams = new ArrayList<Object>();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			SiteAgency o2 = new SiteAgency();
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
					case "agencyName":
						o2.setAgencyName(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("agencyName=$" + num);
						num++;
						bParams.add(o2.sqlAgencyName());
						break;
					case "stateKey":
						{
							Long l = Long.parseLong(jsonObject.getString(entityVar));
							if(l != null) {
								SearchList<com.opendatapolicing.enus.state.SiteState> searchList = new SearchList<com.opendatapolicing.enus.state.SiteState>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(com.opendatapolicing.enus.state.SiteState.class);
								searchList.addFilterQuery("deleted_indexed_boolean:false");
								searchList.addFilterQuery("archived_indexed_boolean:false");
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									if(bParams.size() > 0) {
										bSql.append(", ");
									}
									bSql.append("stateKey=$" + num);
									num++;
									bParams.add(l2);
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("SiteState");
									}
								}
							}
						}
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
					case "imageCoords":
						o2.setImageCoords(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("imageCoords=$" + num);
						num++;
						bParams.add(o2.sqlImageCoords());
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
					LOG.error(String.format("sqlPOSTSiteAgency failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPOSTSiteAgency failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void postSiteAgencyResponse(SiteAgency siteAgency, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = siteAgency.getSiteRequest_();
		try {
			response200POSTSiteAgency(siteAgency, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("postSiteAgencyResponse failed. ", a.cause()));
					errorSiteAgency(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("postSiteAgencyResponse failed. ", ex));
			errorSiteAgency(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200POSTSiteAgency(SiteAgency o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200POSTSiteAgency failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchSiteAgency(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("patchSiteAgency started. "));
		userSiteAgency(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/agency");
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
						patchSiteAgencyResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											aSearchSiteAgency(siteRequest, false, true, true, "/api/agency", "PATCH", d -> {
												if(d.succeeded()) {
													SearchList<SiteAgency> listSiteAgency = d.result();

													List<String> roles2 = Arrays.asList("SiteAdmin");
													if(listSiteAgency.getQueryResponse().getResults().getNumFound() > 1
															&& !CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles2)
															&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles2)
															) {
														String message = String.format("roles required: " + String.join(", ", roles2));
														LOG.error(message);
														errorSiteAgency(siteRequest, eventHandler, Future.failedFuture(message));
													} else {

														ApiRequest apiRequest = new ApiRequest();
														apiRequest.setRows(listSiteAgency.getRows());
														apiRequest.setNumFound(listSiteAgency.getQueryResponse().getResults().getNumFound());
														apiRequest.setNumPATCH(0L);
														apiRequest.initDeepApiRequest(siteRequest);
														siteRequest.setApiRequest_(apiRequest);
														siteRequest.getVertx().eventBus().publish("websocketSiteAgency", JsonObject.mapFrom(apiRequest).toString());
														SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listSiteAgency.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(null);
														Date date = null;
														if(facets != null)
														date = (Date)facets.get("max_modified");
														String dt;
														if(date == null)
															dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));
														else
															dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")));
														listSiteAgency.addFilterQuery(String.format("modified_indexed_date:[* TO %s]", dt));

														try {
															listPATCHSiteAgency(apiRequest, listSiteAgency, dt, e -> {
																if(e.succeeded()) {
																	patchSiteAgencyResponse(siteRequest, f -> {
																		if(f.succeeded()) {
																			LOG.info(String.format("patchSiteAgency succeeded. "));
																			blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																		} else {
																			LOG.error(String.format("patchSiteAgency failed. ", f.cause()));
																			errorSiteAgency(siteRequest, null, f);
																		}
																	});
																} else {
																	LOG.error(String.format("patchSiteAgency failed. ", e.cause()));
																	errorSiteAgency(siteRequest, null, e);
																}
															});
														} catch(Exception ex) {
															LOG.error(String.format("patchSiteAgency failed. ", ex));
															errorSiteAgency(siteRequest, null, Future.failedFuture(ex));
														}
													}
										} else {
													LOG.error(String.format("patchSiteAgency failed. ", d.cause()));
													errorSiteAgency(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("patchSiteAgency failed. ", ex));
											errorSiteAgency(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("patchSiteAgency failed. ", c.cause()));
								errorSiteAgency(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("patchSiteAgency failed. ", ex));
					errorSiteAgency(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("patchSiteAgency failed. ", ex));
						errorSiteAgency(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("patchSiteAgency failed. ", b.cause()));
					errorSiteAgency(null, eventHandler, b);
				}
			}
		});
	}


	public void listPATCHSiteAgency(ApiRequest apiRequest, SearchList<SiteAgency> listSiteAgency, String dt, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listSiteAgency.getSiteRequest_();
		listSiteAgency.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSiteAgency(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				patchSiteAgencyFuture(o, false).onFailure(ex -> {
					errorSiteAgency(siteRequest2, eventHandler, Future.failedFuture(ex));
				})
			);
		});
		CompositeFuture.all(futures).onComplete( a -> {
			if(a.succeeded()) {
				if(listSiteAgency.next(dt)) {
					listPATCHSiteAgency(apiRequest, listSiteAgency, dt, eventHandler);
				} else {
					response200PATCHSiteAgency(siteRequest, eventHandler);
				}
			} else {
				LOG.error(String.format("listPATCHSiteAgency failed. ", a.cause()));
				errorSiteAgency(listSiteAgency.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<SiteAgency> patchSiteAgencyFuture(SiteAgency o, Boolean inheritPk) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		Promise<SiteAgency> promise = Promise.promise();

		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			if(apiRequest != null && apiRequest.getNumFound() == 1L) {
				apiRequest.setOriginal(o);
				apiRequest.setPk(o.getPk());
			}
			siteRequest.getSiteContext_().getPgPool().withTransaction(sqlConnection -> {
				Promise<SiteAgency> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				sqlPATCHSiteAgency(o, inheritPk, a -> {
					if(a.succeeded()) {
						SiteAgency siteAgency = a.result();
						defineSiteAgency(siteAgency, c -> {
							if(c.succeeded()) {
								attributeSiteAgency(siteAgency, d -> {
									if(d.succeeded()) {
										indexSiteAgency(siteAgency, e -> {
											if(e.succeeded()) {
												promise1.complete(siteAgency);
											} else {
												LOG.error(String.format("patchSiteAgencyFuture failed. ", e.cause()));
												promise1.fail(e.cause());
											}
										});
									} else {
										LOG.error(String.format("patchSiteAgencyFuture failed. ", d.cause()));
										promise1.fail(d.cause());
									}
								});
							} else {
								LOG.error(String.format("patchSiteAgencyFuture failed. ", c.cause()));
								promise1.fail(c.cause());
							}
						});
					} else {
						LOG.error(String.format("patchSiteAgencyFuture failed. ", a.cause()));
								promise1.fail(a.cause());
					}
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				promise.fail(ex);
				errorSiteAgency(siteRequest, null, Future.failedFuture(ex));
			}).compose(siteAgency -> {
				Promise<SiteAgency> promise2 = Promise.promise();
				refreshSiteAgency(siteAgency, a -> {
					if(a.succeeded()) {
						if(apiRequest != null) {
							apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
							siteAgency.apiRequestSiteAgency();
							siteRequest.getVertx().eventBus().publish("websocketSiteAgency", JsonObject.mapFrom(apiRequest).toString());
						}
						promise.complete(siteAgency);
					} else {
						LOG.error(String.format("patchSiteAgencyFuture failed. ", a.cause()));
						promise2.fail(a.cause());
					}
				});
				return promise2.future();
			}).onSuccess(a -> {
				LOG.info(String.format("patchSiteAgencyFuture succeeded. "));
			}).onFailure(ex -> {
				promise.fail(ex);
				errorSiteAgency(siteRequest, null, promise.future());
			});
		} catch(Exception ex) {
			LOG.error(String.format("patchSiteAgencyFuture failed. "), ex);
			promise.fail(ex);
			errorSiteAgency(siteRequest, null, promise.future());
		}
		return promise.future();
	}

	public void sqlPATCHSiteAgency(SiteAgency o, Boolean inheritPk, Handler<AsyncResult<SiteAgency>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE SiteAgency SET ");
			List<Object> bParams = new ArrayList<Object>();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			SiteAgency o2 = new SiteAgency();
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
					case "setAgencyName":
							o2.setAgencyName(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("agencyName=$" + num);
							num++;
							bParams.add(o2.sqlAgencyName());
						break;
					case "setStateKey":
						{
							o2.setStateKey(jsonObject.getString(methodName));
							Long l = o2.getStateKey();
							if(l != null && !l.equals(o.getStateKey())) {
								SearchList<com.opendatapolicing.enus.state.SiteState> searchList = new SearchList<com.opendatapolicing.enus.state.SiteState>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(com.opendatapolicing.enus.state.SiteState.class);
								searchList.addFilterQuery("deleted_indexed_boolean:false");
								searchList.addFilterQuery("archived_indexed_boolean:false");
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									o2.setStateKey(l2);
									if(bParams.size() > 0)
										bSql.append(", ");
									bSql.append("stateKey=$" + num);
									num++;
									bParams.add(l2);
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("SiteState");
									}
								}
							}
						}
						break;
					case "removeStateKey":
						{
							o2.setStateKey(jsonObject.getString(methodName));
							Long l = o2.getStateKey();
							if(l != null) {
								SearchList<com.opendatapolicing.enus.state.SiteState> searchList = new SearchList<com.opendatapolicing.enus.state.SiteState>();
								searchList.setQuery("*:*");
								searchList.setStore(true);
								searchList.setC(com.opendatapolicing.enus.state.SiteState.class);
								searchList.addFilterQuery("deleted_indexed_boolean:false");
								searchList.addFilterQuery("archived_indexed_boolean:false");
								searchList.addFilterQuery((inheritPk ? "inheritPk" : "pk") + "_indexed_long:" + l);
								searchList.initDeepSearchList(siteRequest);
								Long l2 = Optional.ofNullable(searchList.getList().stream().findFirst().orElse(null)).map(a -> a.getPk()).orElse(null);
								if(l2 != null) {
									o2.setStateKey((Long)null);
									if(bParams.size() > 0)
										bSql.append(", ");
									bSql.append("stateKey=null");
									if(!pks.contains(l2)) {
										pks.add(l2);
										classes.add("SiteState");
									}
								}
							}
						}
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
					case "setImageCoords":
							o2.setImageCoords(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("imageCoords=$" + num);
							num++;
							bParams.add(o2.sqlImageCoords());
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
					SiteAgency o3 = new SiteAgency();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
					eventHandler.handle(Future.succeededFuture(o3));
				} else {
					LOG.error(String.format("sqlPATCHSiteAgency failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPATCHSiteAgency failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void patchSiteAgencyResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PATCHSiteAgency(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("patchSiteAgencyResponse failed. ", a.cause()));
					errorSiteAgency(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("patchSiteAgencyResponse failed. ", ex));
			errorSiteAgency(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PATCHSiteAgency(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PATCHSiteAgency failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// GET //

	@Override
	public void getSiteAgency(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		userSiteAgency(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/agency/{id}");
					siteRequest.setRequestMethod("GET");
					{
						aSearchSiteAgency(siteRequest, false, true, false, "/api/agency/{id}", "GET", c -> {
							if(c.succeeded()) {
								SearchList<SiteAgency> listSiteAgency = c.result();
								getSiteAgencyResponse(listSiteAgency, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("getSiteAgency succeeded. "));
									} else {
										LOG.error(String.format("getSiteAgency failed. ", d.cause()));
										errorSiteAgency(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("getSiteAgency failed. ", c.cause()));
								errorSiteAgency(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("getSiteAgency failed. ", ex));
					errorSiteAgency(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("getSiteAgency failed. ", ex));
						errorSiteAgency(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("getSiteAgency failed. ", b.cause()));
					errorSiteAgency(null, eventHandler, b);
				}
			}
		});
	}


	public void getSiteAgencyResponse(SearchList<SiteAgency> listSiteAgency, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSiteAgency.getSiteRequest_();
		try {
			response200GETSiteAgency(listSiteAgency, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("getSiteAgencyResponse failed. ", a.cause()));
					errorSiteAgency(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("getSiteAgencyResponse failed. ", ex));
			errorSiteAgency(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200GETSiteAgency(SearchList<SiteAgency> listSiteAgency, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSiteAgency.getSiteRequest_();
			SolrDocumentList solrDocuments = listSiteAgency.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listSiteAgency.getList().stream().findFirst().orElse(null));
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200GETSiteAgency failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// Search //

	@Override
	public void searchSiteAgency(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		userSiteAgency(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/agency");
					siteRequest.setRequestMethod("Search");
					{
						aSearchSiteAgency(siteRequest, false, true, false, "/api/agency", "Search", c -> {
							if(c.succeeded()) {
								SearchList<SiteAgency> listSiteAgency = c.result();
								searchSiteAgencyResponse(listSiteAgency, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("searchSiteAgency succeeded. "));
									} else {
										LOG.error(String.format("searchSiteAgency failed. ", d.cause()));
										errorSiteAgency(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("searchSiteAgency failed. ", c.cause()));
								errorSiteAgency(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("searchSiteAgency failed. ", ex));
					errorSiteAgency(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("searchSiteAgency failed. ", ex));
						errorSiteAgency(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("searchSiteAgency failed. ", b.cause()));
					errorSiteAgency(null, eventHandler, b);
				}
			}
		});
	}


	public void searchSiteAgencyResponse(SearchList<SiteAgency> listSiteAgency, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSiteAgency.getSiteRequest_();
		try {
			response200SearchSiteAgency(listSiteAgency, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("searchSiteAgencyResponse failed. ", a.cause()));
					errorSiteAgency(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("searchSiteAgencyResponse failed. ", ex));
			errorSiteAgency(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchSiteAgency(SearchList<SiteAgency> listSiteAgency, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSiteAgency.getSiteRequest_();
			QueryResponse responseSearch = listSiteAgency.getQueryResponse();
			SolrDocumentList solrDocuments = listSiteAgency.getSolrDocumentList();
			Long searchInMillis = Long.valueOf(responseSearch.getQTime());
			Long transmissionInMillis = responseSearch.getElapsedTime();
			Long startNum = responseSearch.getResults().getStart();
			Long foundNum = responseSearch.getResults().getNumFound();
			Integer returnedNum = responseSearch.getResults().size();
			String searchTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(searchInMillis), TimeUnit.MILLISECONDS.toMillis(searchInMillis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(searchInMillis)));
			String transmissionTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis), TimeUnit.MILLISECONDS.toMillis(transmissionInMillis) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis)));
			Exception exceptionSearch = responseSearch.getException();
			List<String> fls = listSiteAgency.getFields();

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
				json.put("searchTime", searchTime);
				json.put("transmissionTime", transmissionTime);
			}
			JsonArray l = new JsonArray();
			listSiteAgency.getList().stream().forEach(o -> {
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
					responsePivotSearchSiteAgency(pivotFields, pivotArray);
				}
			}
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200SearchSiteAgency failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}
	public void responsePivotSearchSiteAgency(List<PivotField> pivotFields, JsonArray pivotArray) {
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
				responsePivotSearchSiteAgency(pivotFields2, pivotArray2);
			}
		}
	}

	// AdminSearch //

	@Override
	public void adminsearchSiteAgency(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		userSiteAgency(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/admin/agency");
					siteRequest.setRequestMethod("AdminSearch");
					{
						aSearchSiteAgency(siteRequest, false, true, false, "/api/admin/agency", "AdminSearch", c -> {
							if(c.succeeded()) {
								SearchList<SiteAgency> listSiteAgency = c.result();
								adminsearchSiteAgencyResponse(listSiteAgency, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("adminsearchSiteAgency succeeded. "));
									} else {
										LOG.error(String.format("adminsearchSiteAgency failed. ", d.cause()));
										errorSiteAgency(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("adminsearchSiteAgency failed. ", c.cause()));
								errorSiteAgency(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("adminsearchSiteAgency failed. ", ex));
					errorSiteAgency(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("adminsearchSiteAgency failed. ", ex));
						errorSiteAgency(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("adminsearchSiteAgency failed. ", b.cause()));
					errorSiteAgency(null, eventHandler, b);
				}
			}
		});
	}


	public void adminsearchSiteAgencyResponse(SearchList<SiteAgency> listSiteAgency, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSiteAgency.getSiteRequest_();
		try {
			response200AdminSearchSiteAgency(listSiteAgency, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("adminsearchSiteAgencyResponse failed. ", a.cause()));
					errorSiteAgency(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("adminsearchSiteAgencyResponse failed. ", ex));
			errorSiteAgency(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200AdminSearchSiteAgency(SearchList<SiteAgency> listSiteAgency, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSiteAgency.getSiteRequest_();
			QueryResponse responseSearch = listSiteAgency.getQueryResponse();
			SolrDocumentList solrDocuments = listSiteAgency.getSolrDocumentList();
			Long searchInMillis = Long.valueOf(responseSearch.getQTime());
			Long transmissionInMillis = responseSearch.getElapsedTime();
			Long startNum = responseSearch.getResults().getStart();
			Long foundNum = responseSearch.getResults().getNumFound();
			Integer returnedNum = responseSearch.getResults().size();
			String searchTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(searchInMillis), TimeUnit.MILLISECONDS.toMillis(searchInMillis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(searchInMillis)));
			String transmissionTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis), TimeUnit.MILLISECONDS.toMillis(transmissionInMillis) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis)));
			Exception exceptionSearch = responseSearch.getException();
			List<String> fls = listSiteAgency.getFields();

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
				json.put("searchTime", searchTime);
				json.put("transmissionTime", transmissionTime);
			}
			JsonArray l = new JsonArray();
			listSiteAgency.getList().stream().forEach(o -> {
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
					responsePivotAdminSearchSiteAgency(pivotFields, pivotArray);
				}
			}
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200AdminSearchSiteAgency failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}
	public void responsePivotAdminSearchSiteAgency(List<PivotField> pivotFields, JsonArray pivotArray) {
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
				responsePivotAdminSearchSiteAgency(pivotFields2, pivotArray2);
			}
		}
	}

	// SearchPage //

	@Override
	public void searchpageSiteAgencyId(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		searchpageSiteAgency(serviceRequest, eventHandler);
	}

	@Override
	public void searchpageSiteAgency(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		userSiteAgency(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/agency");
					siteRequest.setRequestMethod("SearchPage");
					{
						aSearchSiteAgency(siteRequest, false, true, false, "/agency", "SearchPage", c -> {
							if(c.succeeded()) {
								SearchList<SiteAgency> listSiteAgency = c.result();
								searchpageSiteAgencyResponse(listSiteAgency, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("searchpageSiteAgency succeeded. "));
									} else {
										LOG.error(String.format("searchpageSiteAgency failed. ", d.cause()));
										errorSiteAgency(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("searchpageSiteAgency failed. ", c.cause()));
								errorSiteAgency(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("searchpageSiteAgency failed. ", ex));
					errorSiteAgency(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				if("Inactive Token".equals(b.cause().getMessage())) {
					try {
						eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
					} catch(Exception ex) {
						LOG.error(String.format("searchpageSiteAgency failed. ", ex));
						errorSiteAgency(null, eventHandler, b);
					}
				} else {
					LOG.error(String.format("searchpageSiteAgency failed. ", b.cause()));
					errorSiteAgency(null, eventHandler, b);
				}
			}
		});
	}


	public void searchpageSiteAgencyPageInit(SiteAgencyPage page, SearchList<SiteAgency> listSiteAgency) {
	}
	public void searchpageSiteAgencyResponse(SearchList<SiteAgency> listSiteAgency, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSiteAgency.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200SearchPageSiteAgency(listSiteAgency, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("searchpageSiteAgencyResponse failed. ", a.cause()));
					errorSiteAgency(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("searchpageSiteAgencyResponse failed. ", ex));
			errorSiteAgency(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchPageSiteAgency(SearchList<SiteAgency> listSiteAgency, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSiteAgency.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listSiteAgency.getSiteRequest_(), buffer);
			SiteAgencyPage page = new SiteAgencyPage();
			SolrDocument pageSolrDocument = new SolrDocument();
			MultiMap requestHeaders = MultiMap.caseInsensitiveMultiMap();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/agency");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listSiteAgency.size() == 1)
				siteRequest.setRequestPk(listSiteAgency.get(0).getPk());
			siteRequest.setW(w);
			page.setListSiteAgency(listSiteAgency);
			page.setSiteRequest_(siteRequest);
			searchpageSiteAgencyPageInit(page, listSiteAgency);
			page.initDeepSiteAgencyPage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new ServiceResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOG.error(String.format("response200SearchPageSiteAgency failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// General //

	public void createSiteAgency(SiteRequestEnUS siteRequest, Handler<AsyncResult<SiteAgency>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			String userId = siteRequest.getUserId();
			Long userKey = siteRequest.getUserKey();
			ZonedDateTime created = Optional.ofNullable(siteRequest.getJsonObject()).map(j -> j.getString("created")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())))).orElse(ZonedDateTime.now(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())));

			sqlConnection.preparedQuery("INSERT INTO SiteAgency(created) VALUES($1) RETURNING pk")
					.collecting(Collectors.toList())
					.execute(Tuple.of(created.toOffsetDateTime())
					, createAsync
			-> {
				if(createAsync.succeeded()) {
					Row createLine = createAsync.result().value().stream().findFirst().orElseGet(() -> null);
					Long pk = createLine.getLong(0);
					SiteAgency o = new SiteAgency();
					o.setPk(pk);
					o.setSiteRequest_(siteRequest);
					eventHandler.handle(Future.succeededFuture(o));
				} else {
					LOG.error(String.format("createSiteAgency failed. ", createAsync.cause()));
					eventHandler.handle(Future.failedFuture(createAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("createSiteAgency failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void errorSiteAgency(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler, AsyncResult<?> resultAsync) {
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
			if(mailClient != null) {
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
			}
			eventHandler.handle(Future.succeededFuture(responseOperation));
		} else {
			eventHandler.handle(Future.succeededFuture(responseOperation));
		}
	}

	public SiteRequestEnUS generateSiteRequestEnUSForSiteAgency(User user, SiteContextEnUS siteContext, ServiceRequest serviceRequest) {
		return generateSiteRequestEnUSForSiteAgency(user, siteContext, serviceRequest, null);
	}

	public SiteRequestEnUS generateSiteRequestEnUSForSiteAgency(User user, SiteContextEnUS siteContext, ServiceRequest serviceRequest, JsonObject body) {
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

	public void userSiteAgency(ServiceRequest serviceRequest, Handler<AsyncResult<SiteRequestEnUS>> eventHandler) {
		try {
			JsonObject userJson = serviceRequest.getUser();
			if(userJson == null) {
				SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSiteAgency(null, siteContext, serviceRequest);
				eventHandler.handle(Future.succeededFuture(siteRequest));
			} else {
				User token = User.create(userJson);
				siteContext.getOauth2AuthenticationProvider().authenticate(token.principal(), a -> {
					if(a.succeeded()) {
						User user = a.result();
						siteContext.getAuthorizationProvider().getAuthorizations(user, b -> {
							if(b.succeeded()) {
								try {
									JsonObject userAttributes = user.attributes();
									JsonObject accessToken = userAttributes.getJsonObject("accessToken");
									String userId = userAttributes.getString("sub");
									SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSiteAgency(user, siteContext, serviceRequest);
									SearchList<SiteUser> searchList = new SearchList<SiteUser>();
									searchList.setQuery("*:*");
									searchList.setStore(true);
									searchList.setC(SiteUser.class);
									searchList.addFilterQuery("userId_indexed_string:" + ClientUtils.escapeQueryChars(userId));
									searchList.initDeepSearchList(siteRequest);
									SiteUser siteUser1 = searchList.getList().stream().findFirst().orElse(null);
									SiteUserEnUSApiServiceImpl userService = new SiteUserEnUSApiServiceImpl(siteContext);

									if(siteUser1 == null) {
										JsonObject userVertx = siteRequest.getServiceRequest().getUser();

										JsonObject jsonObject = new JsonObject();
										jsonObject.put("userName", accessToken.getString("preferred_username"));
										jsonObject.put("userFirstName", accessToken.getString("given_name"));
										jsonObject.put("userLastName", accessToken.getString("family_name"));
										jsonObject.put("userCompleteName", accessToken.getString("name"));
										jsonObject.put("userId", accessToken.getString("sub"));
										jsonObject.put("userEmail", accessToken.getString("email"));
										userSiteAgencyDefine(siteRequest, jsonObject, false);

										SiteRequestEnUS siteRequest2 = new SiteRequestEnUS();
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

										userService.postSiteUserFuture(siteRequest2, false).onSuccess(siteUser -> {
											siteRequest.setSiteUser(siteUser);
											siteRequest.setUserName(accessToken.getString("preferred_username"));
											siteRequest.setUserFirstName(accessToken.getString("given_name"));
											siteRequest.setUserLastName(accessToken.getString("family_name"));
											siteRequest.setUserEmail(accessToken.getString("email"));
											siteRequest.setUserId(accessToken.getString("sub"));
											siteRequest.setUserKey(siteUser.getPk());
											eventHandler.handle(Future.succeededFuture(siteRequest));
										}).onFailure(ex -> {
											errorSiteAgency(siteRequest, null, Future.failedFuture(ex));
										});
									} else {
										Long pkUser = siteUser1.getPk();
										JsonObject userVertx = siteRequest.getServiceRequest().getUser();

										JsonObject jsonObject = new JsonObject();
										jsonObject.put("setUserName", accessToken.getString("preferred_username"));
										jsonObject.put("setUserFirstName", accessToken.getString("given_name"));
										jsonObject.put("setUserLastName", accessToken.getString("family_name"));
										jsonObject.put("setUserCompleteName", accessToken.getString("name"));
										jsonObject.put("setUserId", accessToken.getString("sub"));
										jsonObject.put("setUserEmail", accessToken.getString("email"));
										Boolean define = userSiteAgencyDefine(siteRequest, jsonObject, true);
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

											userService.patchSiteUserFuture(siteUser, false).onSuccess(siteUser2 -> {
												siteRequest.setSiteUser(siteUser2);
												siteRequest.setUserName(siteUser2.getUserName());
												siteRequest.setUserFirstName(siteUser2.getUserFirstName());
												siteRequest.setUserLastName(siteUser2.getUserLastName());
												siteRequest.setUserKey(siteUser2.getPk());
												eventHandler.handle(Future.succeededFuture(siteRequest));
											}).onFailure(ex -> {
												errorSiteAgency(siteRequest, null, Future.failedFuture(ex));
											});
										} else {
											siteRequest.setSiteUser(siteUser1);
											siteRequest.setUserName(siteUser1.getUserName());
											siteRequest.setUserFirstName(siteUser1.getUserFirstName());
											siteRequest.setUserLastName(siteUser1.getUserLastName());
											siteRequest.setUserKey(siteUser1.getPk());
											eventHandler.handle(Future.succeededFuture(siteRequest));
										}
									}
								} catch(Exception ex) {
									LOG.error(String.format("userSiteAgency failed. "), ex);
									eventHandler.handle(Future.failedFuture(ex));
								}
							} else {
								LOG.error(String.format("userSiteAgency failed. ", b.cause()));
								eventHandler.handle(Future.failedFuture(b.cause()));
							}
						});
					} else {
						siteContext.getOauth2AuthenticationProvider().refresh(token, b -> {
							if(b.succeeded()) {
								User user = b.result();
								serviceRequest.setUser(user.principal());
								userSiteAgency(serviceRequest, c -> {
									if(c.succeeded()) {
										SiteRequestEnUS siteRequest = c.result();
										eventHandler.handle(Future.succeededFuture(siteRequest));
									} else {
										LOG.error(String.format("userSiteAgency failed. ", c.cause()));
										eventHandler.handle(Future.failedFuture(c.cause()));
									}
								});
							} else {
								LOG.error(String.format("userSiteAgency failed. ", a.cause()));
								eventHandler.handle(Future.failedFuture(a.cause()));
							}
						});
					}
				});
			}
		} catch(Exception ex) {
			LOG.error(String.format("userSiteAgency failed. "), ex);
			eventHandler.handle(Future.failedFuture(ex));
		}
	}

	public Boolean userSiteAgencyDefine(SiteRequestEnUS siteRequest, JsonObject jsonObject, Boolean patch) {
		if(patch) {
			return false;
		} else {
			return false;
		}
	}

	public void aSearchSiteAgencyQ(String uri, String apiMethod, SearchList<SiteAgency> searchList, String entityVar, String valueIndexed, String varIndexed) {
		searchList.setQuery(varIndexed + ":" + ("*".equals(valueIndexed) ? valueIndexed : ClientUtils.escapeQueryChars(valueIndexed)));
		if(!"*".equals(entityVar)) {
		}
	}

	public String aSearchSiteAgencyFq(String uri, String apiMethod, SearchList<SiteAgency> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		if(StringUtils.startsWith(valueIndexed, "[")) {
			String[] fqs = StringUtils.substringBefore(StringUtils.substringAfter(valueIndexed, "["), "]").split(" TO ");
			if(fqs.length != 2)
				throw new RuntimeException(String.format("\"%s\" invalid range query. ", valueIndexed));
			String fq1 = fqs[0].equals("*") ? fqs[0] : SiteAgency.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[0]);
			String fq2 = fqs[1].equals("*") ? fqs[1] : SiteAgency.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[1]);
			 return varIndexed + ":[" + fq1 + " TO " + fq2 + "]";
		} else {
			return varIndexed + ":" + SiteAgency.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), valueIndexed);
		}
	}

	public void aSearchSiteAgencySort(String uri, String apiMethod, SearchList<SiteAgency> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addSort(varIndexed, ORDER.valueOf(valueIndexed));
	}

	public void aSearchSiteAgencyRows(String uri, String apiMethod, SearchList<SiteAgency> searchList, Integer valueRows) {
			searchList.setRows(apiMethod != null && apiMethod.contains("Search") ? valueRows : 10);
	}

	public void aSearchSiteAgencyStart(String uri, String apiMethod, SearchList<SiteAgency> searchList, Integer valueStart) {
		searchList.setStart(valueStart);
	}

	public void aSearchSiteAgencyVar(String uri, String apiMethod, SearchList<SiteAgency> searchList, String var, String value) {
		searchList.getSiteRequest_().getRequestVars().put(var, value);
	}

	public void aSearchSiteAgencyUri(String uri, String apiMethod, SearchList<SiteAgency> searchList) {
	}

	public void varsSiteAgency(SiteRequestEnUS siteRequest, Handler<AsyncResult<SearchList<ServiceResponse>>> eventHandler) {
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
					LOG.error(String.format("aSearchSiteAgency failed. "), e);
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOG.error(String.format("aSearchSiteAgency failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void aSearchSiteAgency(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, Handler<AsyncResult<SearchList<SiteAgency>>> eventHandler) {
		try {
			SearchList<SiteAgency> searchList = aSearchSiteAgencyList(siteRequest, populate, store, modify, uri, apiMethod);
			eventHandler.handle(Future.succeededFuture(searchList));
		} catch(Exception e) {
			LOG.error(String.format("aSearchSiteAgency failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SearchList<SiteAgency> aSearchSiteAgencyList(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod) {
		ServiceRequest serviceRequest = siteRequest.getServiceRequest();
		String entityListStr = siteRequest.getServiceRequest().getParams().getJsonObject("query").getString("fl");
		String[] entityList = entityListStr == null ? null : entityListStr.split(",\\s*");
		SearchList<SiteAgency> searchList = new SearchList<SiteAgency>();
		searchList.setPopulate(populate);
		searchList.setStore(store);
		searchList.setQuery("*:*");
		searchList.setC(SiteAgency.class);
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
							varsIndexed[i] = SiteAgency.varIndexedSiteAgency(entityVar);
						}
						searchList.add("facet.pivot", (solrLocalParams == null ? "" : solrLocalParams) + StringUtils.join(varsIndexed, ","));
					}
				} else if(paramValuesObject != null) {
					for(Object paramObject : paramObjects) {
						switch(paramName) {
							case "q":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								varIndexed = "*".equals(entityVar) ? entityVar : SiteAgency.varSearchSiteAgency(entityVar);
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								valueIndexed = StringUtils.isEmpty(valueIndexed) ? "*" : valueIndexed;
								aSearchSiteAgencyQ(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "fq":
								Matcher mFq = Pattern.compile("(\\w+):(.+?(?=(\\)|\\s+OR\\s+|\\s+AND\\s+|$)))").matcher((String)paramObject);
								boolean foundFq = mFq.find();
								if(foundFq) {
									StringBuffer sb = new StringBuffer();
									while(foundFq) {
										entityVar = mFq.group(1).trim();
										valueIndexed = mFq.group(2).trim();
										varIndexed = SiteAgency.varIndexedSiteAgency(entityVar);
										String entityFq = aSearchSiteAgencyFq(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
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
								varIndexed = SiteAgency.varIndexedSiteAgency(entityVar);
								aSearchSiteAgencySort(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "start":
								valueStart = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
								aSearchSiteAgencyStart(uri, apiMethod, searchList, valueStart);
								break;
							case "rows":
								valueRows = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
								aSearchSiteAgencyRows(uri, apiMethod, searchList, valueRows);
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
									varIndexed = SiteAgency.varIndexedSiteAgency(entityVar);
									searchList.add("facet.range", (solrLocalParams == null ? "" : solrLocalParams) + varIndexed);
								}
								break;
							case "facet.field":
								entityVar = (String)paramObject;
								varIndexed = SiteAgency.varIndexedSiteAgency(entityVar);
								if(varIndexed != null)
									searchList.addFacetField(varIndexed);
								break;
							case "var":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								aSearchSiteAgencyVar(uri, apiMethod, searchList, entityVar, valueIndexed);
								break;
						}
					}
					aSearchSiteAgencyUri(uri, apiMethod, searchList);
				}
			} catch(Exception e) {
				ExceptionUtils.rethrow(e);
			}
		});
		if("*:*".equals(searchList.getQuery()) && searchList.getSorts().size() == 0) {
			searchList.addSort("agencyName_indexed_string", ORDER.asc);
		}
		aSearchSiteAgency2(siteRequest, populate, store, modify, uri, apiMethod, searchList);
		searchList.initDeepForClass(siteRequest);
		return searchList;
	}
	public void aSearchSiteAgency2(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, SearchList<SiteAgency> searchList) {
	}

	public void defineSiteAgency(SiteAgency o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT * FROM SiteAgency WHERE pk=$1")
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
										LOG.error(String.format("defineSiteAgency failed. "), e);
									}
								}
							}
						}
						eventHandler.handle(Future.succeededFuture());
					} catch(Exception e) {
						LOG.error(String.format("defineSiteAgency failed. "), e);
						eventHandler.handle(Future.failedFuture(e));
					}
				} else {
					LOG.error(String.format("defineSiteAgency failed. ", defineAsync.cause()));
					eventHandler.handle(Future.failedFuture(defineAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("defineSiteAgency failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void attributeSiteAgency(SiteAgency o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT stateKey as pk1, 'stateKey' from SiteAgency where pk=$1")
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
						LOG.error(String.format("attributeSiteAgency failed. ", attributeAsync.cause()));
						eventHandler.handle(Future.failedFuture(attributeAsync.cause()));
					}
				} catch(Exception e) {
					LOG.error(String.format("attributeSiteAgency failed. "), e);
					eventHandler.handle(Future.failedFuture(e));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("attributeSiteAgency failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void indexSiteAgency(SiteAgency o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			o.initDeepForClass(siteRequest);
			o.indexForClass();
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOG.error(String.format("indexSiteAgency failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void refreshSiteAgency(SiteAgency o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Boolean refresh = !"false".equals(siteRequest.getRequestVars().get("refresh"));
			if(refresh && Optional.ofNullable(siteRequest.getJsonObject()).map(JsonObject::isEmpty).orElse(true)) {
				SearchList<SiteAgency> searchList = new SearchList<SiteAgency>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(SiteAgency.class);
				searchList.addFilterQuery("modified_indexed_date:[" + DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(siteRequest.getApiRequest_().getCreated().toInstant(), ZoneId.of("UTC"))) + " TO *]");
				searchList.add("json.facet", "{stateKey:{terms:{field:stateKey_indexed_longs, limit:1000}}}");
				searchList.setRows(1000);
				searchList.initDeepSearchList(siteRequest);
				List<Future> futures = new ArrayList<>();

				for(int i=0; i < pks.size(); i++) {
					Long pk2 = pks.get(i);
					String classSimpleName2 = classes.get(i);

					if("SiteState".equals(classSimpleName2) && pk2 != null) {
						SearchList<SiteState> searchList2 = new SearchList<SiteState>();
						searchList2.setStore(true);
						searchList2.setQuery("*:*");
						searchList2.setC(SiteState.class);
						searchList2.addFilterQuery("pk_indexed_long:" + pk2);
						searchList2.setRows(1);
						searchList2.initDeepSearchList(siteRequest);
						SiteState o2 = searchList2.getList().stream().findFirst().orElse(null);

						if(o2 != null) {
							SiteStateEnUSApiServiceImpl service = new SiteStateEnUSApiServiceImpl(siteRequest.getSiteContext_());
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSiteAgency(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), new JsonObject());
							ApiRequest apiRequest2 = new ApiRequest();
							apiRequest2.setRows(1);
							apiRequest2.setNumFound(1l);
							apiRequest2.setNumPATCH(0L);
							apiRequest2.initDeepApiRequest(siteRequest2);
							siteRequest2.setApiRequest_(apiRequest2);
							siteRequest2.getVertx().eventBus().publish("websocketSiteState", JsonObject.mapFrom(apiRequest2).toString());

							o2.setPk(pk2);
							o2.setSiteRequest_(siteRequest2);
							futures.add(
								service.patchSiteStateFuture(o2, false).onFailure(ex -> {
									LOG.error(String.format("SiteState %s failed. ", pk2), ex);
									eventHandler.handle(Future.failedFuture(ex));
								})
							);
						}
					}
				}

				CompositeFuture.all(futures).onComplete(a -> {
					if(a.succeeded()) {
						SiteAgencyEnUSApiServiceImpl service = new SiteAgencyEnUSApiServiceImpl(siteRequest.getSiteContext_());
						List<Future> futures2 = new ArrayList<>();
						for(SiteAgency o2 : searchList.getList()) {
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSiteAgency(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), new JsonObject());
							o2.setSiteRequest_(siteRequest2);
							futures2.add(
								service.patchSiteAgencyFuture(o2, false).onFailure(ex -> {
									LOG.error(String.format("SiteAgency %s failed. ", o2.getPk()), ex);
									eventHandler.handle(Future.failedFuture(ex));
								})
							);
						}

						CompositeFuture.all(futures2).onComplete(b -> {
							if(b.succeeded()) {
								eventHandler.handle(Future.succeededFuture());
							} else {
								LOG.error("Refresh relations failed. ", b.cause());
								errorSiteAgency(siteRequest, eventHandler, b);
							}
						});
					} else {
						LOG.error("Refresh relations failed. ", a.cause());
						errorSiteAgency(siteRequest, eventHandler, a);
					}
				});
			} else {
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOG.error(String.format("refreshSiteAgency failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}
}
