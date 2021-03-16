package com.opendatapolicing.enus.user;

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
public class SiteUserEnUSGenApiServiceImpl implements SiteUserEnUSGenApiService {

	protected static final Logger LOG = LoggerFactory.getLogger(SiteUserEnUSGenApiServiceImpl.class);

	protected static final String SERVICE_ADDRESS = "SiteUserEnUSApiServiceImpl";

	protected SiteContextEnUS siteContext;

	public SiteUserEnUSGenApiServiceImpl(SiteContextEnUS siteContext) {
		this.siteContext = siteContext;
	}

	// Search //

	@Override
	public void searchSiteUser(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		userSiteUser(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/api/user");
					siteRequest.setRequestMethod("Search");
					{
						aSearchSiteUser(siteRequest, false, true, false, "/api/user", "Search", c -> {
							if(c.succeeded()) {
								SearchList<SiteUser> listSiteUser = c.result();
								searchSiteUserResponse(listSiteUser, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("searchSiteUser succeeded. "));
									} else {
										LOG.error(String.format("searchSiteUser failed. ", d.cause()));
										errorSiteUser(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("searchSiteUser failed. ", c.cause()));
								errorSiteUser(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("searchSiteUser failed. ", ex));
					errorSiteUser(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("searchSiteUser failed. ", b.cause()));
				errorSiteUser(null, eventHandler, b);
			}
		});
	}


	public void searchSiteUserResponse(SearchList<SiteUser> listSiteUser, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSiteUser.getSiteRequest_();
		try {
			response200SearchSiteUser(listSiteUser, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("searchSiteUserResponse failed. ", a.cause()));
					errorSiteUser(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("searchSiteUserResponse failed. ", ex));
			errorSiteUser(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchSiteUser(SearchList<SiteUser> listSiteUser, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSiteUser.getSiteRequest_();
			QueryResponse responseSearch = listSiteUser.getQueryResponse();
			SolrDocumentList solrDocuments = listSiteUser.getSolrDocumentList();
			Long searchInMillis = Long.valueOf(responseSearch.getQTime());
			Long transmissionInMillis = responseSearch.getElapsedTime();
			Long startNum = responseSearch.getResults().getStart();
			Long foundNum = responseSearch.getResults().getNumFound();
			Integer returnedNum = responseSearch.getResults().size();
			String searchTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(searchInMillis), TimeUnit.MILLISECONDS.toMillis(searchInMillis) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(searchInMillis)));
			String transmissionTime = String.format("%d.%03d sec", TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis), TimeUnit.MILLISECONDS.toMillis(transmissionInMillis) - TimeUnit.SECONDS.toSeconds(TimeUnit.MILLISECONDS.toSeconds(transmissionInMillis)));
			Exception exceptionSearch = responseSearch.getException();
			List<String> fls = listSiteUser.getFields();

			JsonObject json = new JsonObject();
			json.put("startNum", startNum);
			json.put("foundNum", foundNum);
			json.put("returnedNum", returnedNum);
			if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
				json.put("searchTime", searchTime);
				json.put("transmissionTime", transmissionTime);
			}
			JsonArray l = new JsonArray();
			listSiteUser.getList().stream().forEach(o -> {
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
					responsePivotSearchSiteUser(pivotFields, pivotArray);
				}
			}
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200SearchSiteUser failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}
	public void responsePivotSearchSiteUser(List<PivotField> pivotFields, JsonArray pivotArray) {
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
				responsePivotSearchSiteUser(pivotFields2, pivotArray2);
			}
		}
	}

	// PATCH //

	@Override
	public void patchSiteUser(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("patchSiteUser started. "));
		userSiteUser(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/user");
					siteRequest.setRequestMethod("PATCH");
					{
						patchSiteUserResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											aSearchSiteUser(siteRequest, false, true, true, "/api/user", "PATCH", d -> {
												if(d.succeeded()) {
													SearchList<SiteUser> listSiteUser = d.result();

													List<String> roles2 = Arrays.asList("SiteAdmin");
													if(listSiteUser.getQueryResponse().getResults().getNumFound() > 1
															&& !CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles2)
															&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles2)
															) {
														String message = String.format("roles required: " + String.join(", ", roles2));
														LOG.error(message);
														errorSiteUser(siteRequest, eventHandler, Future.failedFuture(message));
													} else {

														ApiRequest apiRequest = new ApiRequest();
														apiRequest.setRows(listSiteUser.getRows());
														apiRequest.setNumFound(listSiteUser.getQueryResponse().getResults().getNumFound());
														apiRequest.setNumPATCH(0L);
														apiRequest.initDeepApiRequest(siteRequest);
														siteRequest.setApiRequest_(apiRequest);
														siteRequest.getVertx().eventBus().publish("websocketSiteUser", JsonObject.mapFrom(apiRequest).toString());
														SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listSiteUser.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(null);
														Date date = null;
														if(facets != null)
														date = (Date)facets.get("max_modified");
														String dt;
														if(date == null)
															dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));
														else
															dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(date.toInstant(), ZoneId.of("UTC")));
														listSiteUser.addFilterQuery(String.format("modified_indexed_date:[* TO %s]", dt));

														try {
															listPATCHSiteUser(apiRequest, listSiteUser, dt, e -> {
																if(e.succeeded()) {
																	patchSiteUserResponse(siteRequest, f -> {
																		if(f.succeeded()) {
																			LOG.info(String.format("patchSiteUser succeeded. "));
																			blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																		} else {
																			LOG.error(String.format("patchSiteUser failed. ", f.cause()));
																			errorSiteUser(siteRequest, null, f);
																		}
																	});
																} else {
																	LOG.error(String.format("patchSiteUser failed. ", e.cause()));
																	errorSiteUser(siteRequest, null, e);
																}
															});
														} catch(Exception ex) {
															LOG.error(String.format("patchSiteUser failed. ", ex));
															errorSiteUser(siteRequest, null, Future.failedFuture(ex));
														}
													}
										} else {
													LOG.error(String.format("patchSiteUser failed. ", d.cause()));
													errorSiteUser(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOG.error(String.format("patchSiteUser failed. ", ex));
											errorSiteUser(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOG.error(String.format("patchSiteUser failed. ", c.cause()));
								errorSiteUser(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("patchSiteUser failed. ", ex));
					errorSiteUser(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("patchSiteUser failed. ", b.cause()));
				errorSiteUser(null, eventHandler, b);
			}
		});
	}


	public void listPATCHSiteUser(ApiRequest apiRequest, SearchList<SiteUser> listSiteUser, String dt, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listSiteUser.getSiteRequest_();
		listSiteUser.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSiteUser(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				patchSiteUserFuture(o, false, a -> {
					if(a.succeeded()) {
					} else {
						errorSiteUser(siteRequest2, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).onComplete( a -> {
			if(a.succeeded()) {
				if(listSiteUser.next(dt)) {
					listPATCHSiteUser(apiRequest, listSiteUser, dt, eventHandler);
				} else {
					response200PATCHSiteUser(siteRequest, eventHandler);
				}
			} else {
				LOG.error(String.format("listPATCHSiteUser failed. ", a.cause()));
				errorSiteUser(listSiteUser.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<SiteUser> patchSiteUserFuture(SiteUser o, Boolean inheritPk, Handler<AsyncResult<SiteUser>> eventHandler) {
		Promise<SiteUser> promise = Promise.promise();
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			if(apiRequest != null && apiRequest.getNumFound() == 1L) {
				apiRequest.setOriginal(o);
				apiRequest.setPk(o.getPk());
			}
			sqlConnectionSiteUser(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionSiteUser(siteRequest, b -> {
						if(b.succeeded()) {
							sqlPATCHSiteUser(o, inheritPk, c -> {
								if(c.succeeded()) {
									SiteUser siteUser = c.result();
									defineIndexSiteUser(siteUser, d -> {
										if(d.succeeded()) {
											if(apiRequest != null) {
												apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
												if(apiRequest.getNumFound() == 1L) {
													siteUser.apiRequestSiteUser();
												}
												siteRequest.getVertx().eventBus().publish("websocketSiteUser", JsonObject.mapFrom(apiRequest).toString());
											}
											eventHandler.handle(Future.succeededFuture(siteUser));
											promise.complete(siteUser);
										} else {
											LOG.error(String.format("patchSiteUserFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOG.error(String.format("patchSiteUserFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOG.error(String.format("patchSiteUserFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOG.error(String.format("patchSiteUserFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("patchSiteUserFuture failed. "), e);
			errorSiteUser(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPATCHSiteUser(SiteUser o, Boolean inheritPk, Handler<AsyncResult<SiteUser>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE SiteUser SET ");
			List<Object> bParams = new ArrayList<Object>();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			SiteUser o2 = new SiteUser();
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
					case "setUserId":
							o2.setUserId(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("userId=$" + num);
							num++;
							bParams.add(o2.sqlUserId());
						break;
					case "setUserName":
							o2.setUserName(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("userName=$" + num);
							num++;
							bParams.add(o2.sqlUserName());
						break;
					case "setUserEmail":
							o2.setUserEmail(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("userEmail=$" + num);
							num++;
							bParams.add(o2.sqlUserEmail());
						break;
					case "setUserFirstName":
							o2.setUserFirstName(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("userFirstName=$" + num);
							num++;
							bParams.add(o2.sqlUserFirstName());
						break;
					case "setUserLastName":
							o2.setUserLastName(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("userLastName=$" + num);
							num++;
							bParams.add(o2.sqlUserLastName());
						break;
					case "setUserFullName":
							o2.setUserFullName(jsonObject.getString(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("userFullName=$" + num);
							num++;
							bParams.add(o2.sqlUserFullName());
						break;
					case "setSeeArchived":
							o2.setSeeArchived(jsonObject.getBoolean(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("seeArchived=$" + num);
							num++;
							bParams.add(o2.sqlSeeArchived());
						break;
					case "setSeeDeleted":
							o2.setSeeDeleted(jsonObject.getBoolean(methodName));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append("seeDeleted=$" + num);
							num++;
							bParams.add(o2.sqlSeeDeleted());
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
					SiteUser o3 = new SiteUser();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
					eventHandler.handle(Future.succeededFuture(o3));
				} else {
					LOG.error(String.format("sqlPATCHSiteUser failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPATCHSiteUser failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void patchSiteUserResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			response200PATCHSiteUser(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("patchSiteUserResponse failed. ", a.cause()));
					errorSiteUser(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("patchSiteUserResponse failed. ", ex));
			errorSiteUser(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PATCHSiteUser(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200PATCHSiteUser failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// POST //

	@Override
	public void postSiteUser(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.info(String.format("postSiteUser started. "));
		userSiteUser(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setJsonObject(body);
					siteRequest.setRequestUri("/api/user");
					siteRequest.setRequestMethod("POST");
					{
						ApiRequest apiRequest = new ApiRequest();
						apiRequest.setRows(1);
						apiRequest.setNumFound(1L);
						apiRequest.setNumPATCH(0L);
						apiRequest.initDeepApiRequest(siteRequest);
						siteRequest.setApiRequest_(apiRequest);
						siteRequest.getVertx().eventBus().publish("websocketSiteUser", JsonObject.mapFrom(apiRequest).toString());
						postSiteUserFuture(siteRequest, false, c -> {
							if(c.succeeded()) {
								SiteUser siteUser = c.result();
								apiRequest.setPk(siteUser.getPk());
								postSiteUserResponse(siteUser, d -> {
										if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("postSiteUser succeeded. "));
									} else {
										LOG.error(String.format("postSiteUser failed. ", d.cause()));
										errorSiteUser(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("postSiteUser failed. ", c.cause()));
								errorSiteUser(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("postSiteUser failed. ", ex));
					errorSiteUser(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("postSiteUser failed. ", b.cause()));
				errorSiteUser(null, eventHandler, b);
			}
		});
	}


	public Future<SiteUser> postSiteUserFuture(SiteRequestEnUS siteRequest, Boolean inheritPk, Handler<AsyncResult<SiteUser>> eventHandler) {
		Promise<SiteUser> promise = Promise.promise();
		try {
			sqlConnectionSiteUser(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionSiteUser(siteRequest, b -> {
						if(b.succeeded()) {
							createSiteUser(siteRequest, c -> {
								if(c.succeeded()) {
									SiteUser siteUser = c.result();
									sqlPOSTSiteUser(siteUser, inheritPk, d -> {
										if(d.succeeded()) {
											defineIndexSiteUser(siteUser, e -> {
												if(e.succeeded()) {
													ApiRequest apiRequest = siteRequest.getApiRequest_();
													if(apiRequest != null) {
														apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
														siteUser.apiRequestSiteUser();
														siteRequest.getVertx().eventBus().publish("websocketSiteUser", JsonObject.mapFrom(apiRequest).toString());
													}
													eventHandler.handle(Future.succeededFuture(siteUser));
													promise.complete(siteUser);
												} else {
													LOG.error(String.format("postSiteUserFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOG.error(String.format("postSiteUserFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOG.error(String.format("postSiteUserFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOG.error(String.format("postSiteUserFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOG.error(String.format("postSiteUserFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("postSiteUserFuture failed. "), e);
			errorSiteUser(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPOSTSiteUser(SiteUser o, Boolean inheritPk, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Integer num = 1;
			StringBuilder bSql = new StringBuilder("UPDATE SiteUser SET ");
			List<Object> bParams = new ArrayList<Object>();
			Long pk = o.getPk();
			JsonObject jsonObject = siteRequest.getJsonObject();
			SiteUser o2 = new SiteUser();
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

				JsonArray userKeys = Optional.ofNullable(jsonObject.getJsonArray("userKeys")).orElse(null);
				if(userKeys != null && !userKeys.contains(siteRequest.getUserKey()))
					userKeys.add(siteRequest.getUserKey().toString());
				else
					jsonObject.put("userKeys", new JsonArray().add(siteRequest.getUserKey().toString()));
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
					case "userId":
						o2.setUserId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("userId=$" + num);
						num++;
						bParams.add(o2.sqlUserId());
						break;
					case "userName":
						o2.setUserName(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("userName=$" + num);
						num++;
						bParams.add(o2.sqlUserName());
						break;
					case "userEmail":
						o2.setUserEmail(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("userEmail=$" + num);
						num++;
						bParams.add(o2.sqlUserEmail());
						break;
					case "userFirstName":
						o2.setUserFirstName(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("userFirstName=$" + num);
						num++;
						bParams.add(o2.sqlUserFirstName());
						break;
					case "userLastName":
						o2.setUserLastName(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("userLastName=$" + num);
						num++;
						bParams.add(o2.sqlUserLastName());
						break;
					case "userFullName":
						o2.setUserFullName(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("userFullName=$" + num);
						num++;
						bParams.add(o2.sqlUserFullName());
						break;
					case "seeArchived":
						o2.setSeeArchived(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("seeArchived=$" + num);
						num++;
						bParams.add(o2.sqlSeeArchived());
						break;
					case "seeDeleted":
						o2.setSeeDeleted(jsonObject.getBoolean(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append("seeDeleted=$" + num);
						num++;
						bParams.add(o2.sqlSeeDeleted());
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
					LOG.error(String.format("sqlPOSTSiteUser failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("sqlPOSTSiteUser failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void postSiteUserResponse(SiteUser siteUser, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = siteUser.getSiteRequest_();
		try {
			response200POSTSiteUser(siteUser, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("postSiteUserResponse failed. ", a.cause()));
					errorSiteUser(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("postSiteUserResponse failed. ", ex));
			errorSiteUser(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200POSTSiteUser(SiteUser o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOG.error(String.format("response200POSTSiteUser failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// SearchPage //

	@Override
	public void searchpageSiteUserId(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		searchpageSiteUser(serviceRequest, eventHandler);
	}

	@Override
	public void searchpageSiteUser(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		userSiteUser(serviceRequest, b -> {
			if(b.succeeded()) {
				try {
					SiteRequestEnUS siteRequest = b.result();
					siteRequest.setRequestUri("/user");
					siteRequest.setRequestMethod("SearchPage");
					{
						aSearchSiteUser(siteRequest, false, true, false, "/user", "SearchPage", c -> {
							if(c.succeeded()) {
								SearchList<SiteUser> listSiteUser = c.result();
								searchpageSiteUserResponse(listSiteUser, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOG.info(String.format("searchpageSiteUser succeeded. "));
									} else {
										LOG.error(String.format("searchpageSiteUser failed. ", d.cause()));
										errorSiteUser(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOG.error(String.format("searchpageSiteUser failed. ", c.cause()));
								errorSiteUser(siteRequest, eventHandler, c);
							}
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("searchpageSiteUser failed. ", ex));
					errorSiteUser(null, eventHandler, Future.failedFuture(ex));
				}
			} else {
				LOG.error(String.format("searchpageSiteUser failed. ", b.cause()));
				errorSiteUser(null, eventHandler, b);
			}
		});
	}


	public void searchpageSiteUserPageInit(SiteUserPage page, SearchList<SiteUser> listSiteUser) {
	}
	public void searchpageSiteUserResponse(SearchList<SiteUser> listSiteUser, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listSiteUser.getSiteRequest_();
		try {
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(siteRequest, buffer);
			siteRequest.setW(w);
			response200SearchPageSiteUser(listSiteUser, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOG.error(String.format("searchpageSiteUserResponse failed. ", a.cause()));
					errorSiteUser(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOG.error(String.format("searchpageSiteUserResponse failed. ", ex));
			errorSiteUser(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchPageSiteUser(SearchList<SiteUser> listSiteUser, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listSiteUser.getSiteRequest_();
			Buffer buffer = Buffer.buffer();
			AllWriter w = AllWriter.create(listSiteUser.getSiteRequest_(), buffer);
			SiteUserPage page = new SiteUserPage();
			SolrDocument pageSolrDocument = new SolrDocument();
			MultiMap requestHeaders = MultiMap.caseInsensitiveMultiMap();
			siteRequest.setRequestHeaders(requestHeaders);

			pageSolrDocument.setField("pageUri_frFR_stored_string", "/user");
			page.setPageSolrDocument(pageSolrDocument);
			page.setW(w);
			if(listSiteUser.size() == 1)
				siteRequest.setRequestPk(listSiteUser.get(0).getPk());
			siteRequest.setW(w);
			page.setListSiteUser(listSiteUser);
			page.setSiteRequest_(siteRequest);
			searchpageSiteUserPageInit(page, listSiteUser);
			page.initDeepSiteUserPage(siteRequest);
			page.html();
			eventHandler.handle(Future.succeededFuture(new ServiceResponse(200, "OK", buffer, requestHeaders)));
		} catch(Exception e) {
			LOG.error(String.format("response200SearchPageSiteUser failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// General //

	public Future<SiteUser> defineIndexSiteUser(SiteUser siteUser, Handler<AsyncResult<SiteUser>> eventHandler) {
		Promise<SiteUser> promise = Promise.promise();
		SiteRequestEnUS siteRequest = siteUser.getSiteRequest_();
		defineSiteUser(siteUser, c -> {
			if(c.succeeded()) {
				attributeSiteUser(siteUser, d -> {
					if(d.succeeded()) {
						indexSiteUser(siteUser, e -> {
							if(e.succeeded()) {
								sqlCommitSiteUser(siteRequest, f -> {
									if(f.succeeded()) {
										sqlCloseSiteUser(siteRequest, g -> {
											if(g.succeeded()) {
												refreshSiteUser(siteUser, h -> {
													if(h.succeeded()) {
														eventHandler.handle(Future.succeededFuture(siteUser));
														promise.complete(siteUser);
													} else {
														LOG.error(String.format("refreshSiteUser failed. ", h.cause()));
														errorSiteUser(siteRequest, null, h);
													}
												});
											} else {
												LOG.error(String.format("defineIndexSiteUser failed. ", g.cause()));
												errorSiteUser(siteRequest, null, g);
											}
										});
									} else {
										LOG.error(String.format("defineIndexSiteUser failed. ", f.cause()));
										errorSiteUser(siteRequest, null, f);
									}
								});
							} else {
								LOG.error(String.format("defineIndexSiteUser failed. ", e.cause()));
								errorSiteUser(siteRequest, null, e);
							}
						});
					} else {
						LOG.error(String.format("defineIndexSiteUser failed. ", d.cause()));
						errorSiteUser(siteRequest, null, d);
					}
				});
			} else {
				LOG.error(String.format("defineIndexSiteUser failed. ", c.cause()));
				errorSiteUser(siteRequest, null, c);
			}
		});
		return promise.future();
	}

	public void createSiteUser(SiteRequestEnUS siteRequest, Handler<AsyncResult<SiteUser>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			String userId = siteRequest.getUserId();
			Long userKey = siteRequest.getUserKey();
			ZonedDateTime created = Optional.ofNullable(siteRequest.getJsonObject()).map(j -> j.getString("created")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())))).orElse(ZonedDateTime.now(ZoneId.of(siteRequest.getSiteConfig_().getSiteZone())));

			sqlConnection.preparedQuery("INSERT INTO SiteUser(created, userKey) VALUES($1, $2) RETURNING pk")
					.collecting(Collectors.toList())
					.execute(Tuple.of(created.toOffsetDateTime(), userKey)
					, createAsync
			-> {
				if(createAsync.succeeded()) {
					Row createLine = createAsync.result().value().stream().findFirst().orElseGet(() -> null);
					Long pk = createLine.getLong(0);
					SiteUser o = new SiteUser();
					o.setPk(pk);
					o.setSiteRequest_(siteRequest);
					eventHandler.handle(Future.succeededFuture(o));
				} else {
					LOG.error(String.format("createSiteUser failed. ", createAsync.cause()));
					eventHandler.handle(Future.failedFuture(createAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("createSiteUser failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void errorSiteUser(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler, AsyncResult<?> resultAsync) {
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
			sqlRollbackSiteUser(siteRequest, a -> {
				if(a.succeeded()) {
					LOG.info(String.format("sql rollback. "));
					sqlCloseSiteUser(siteRequest, b -> {
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

	public void sqlConnectionSiteUser(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
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
						LOG.error(String.format("sqlConnectionSiteUser failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlSiteUser failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlTransactionSiteUser(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();

			if(sqlConnection == null) {
				eventHandler.handle(Future.failedFuture("sqlTransactionCloseSiteUser failed, connection should not be null. "));
			} else {
				sqlConnection.begin(a -> {
					Transaction tx = a.result();
					siteRequest.setTx(tx);
					eventHandler.handle(Future.succeededFuture());
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlTransactionSiteUser failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCommitSiteUser(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();

			if(tx == null) {
				eventHandler.handle(Future.failedFuture("sqlCommitCloseSiteUser failed, tx should not be null. "));
			} else {
				tx.commit(a -> {
					if(a.succeeded()) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else if("Transaction already completed".equals(a.cause().getMessage())) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOG.error(String.format("sqlCommitSiteUser failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlSiteUser failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlRollbackSiteUser(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			Transaction tx = siteRequest.getTx();

			if(tx == null) {
				eventHandler.handle(Future.failedFuture("sqlRollbackCloseSiteUser failed, tx should not be null. "));
			} else {
				tx.rollback(a -> {
					if(a.succeeded()) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else if("Transaction already completed".equals(a.cause().getMessage())) {
						siteRequest.setTx(null);
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOG.error(String.format("sqlRollbackSiteUser failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlSiteUser failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void sqlCloseSiteUser(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();

			if(sqlConnection == null) {
				eventHandler.handle(Future.failedFuture("sqlCloseSiteUser failed, connection should not be null. "));
			} else {
				sqlConnection.close(a -> {
					if(a.succeeded()) {
						siteRequest.setSqlConnection(null);
						eventHandler.handle(Future.succeededFuture());
					} else {
						LOG.error(String.format("sqlCloseSiteUser failed. ", a.cause()));
						eventHandler.handle(Future.failedFuture(a.cause()));
					}
				});
			}
		} catch(Exception e) {
			LOG.error(String.format("sqlCloseSiteUser failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SiteRequestEnUS generateSiteRequestEnUSForSiteUser(User user, SiteContextEnUS siteContext, ServiceRequest serviceRequest) {
		return generateSiteRequestEnUSForSiteUser(user, siteContext, serviceRequest, null);
	}

	public SiteRequestEnUS generateSiteRequestEnUSForSiteUser(User user, SiteContextEnUS siteContext, ServiceRequest serviceRequest, JsonObject body) {
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

	public void userSiteUser(ServiceRequest serviceRequest, Handler<AsyncResult<SiteRequestEnUS>> eventHandler) {
		try {
			JsonObject userJson = serviceRequest.getUser();
			if(userJson == null) {
				SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSiteUser(null, siteContext, serviceRequest);
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
								SiteRequestEnUS siteRequest = generateSiteRequestEnUSForSiteUser(user, siteContext, serviceRequest);
								sqlConnectionSiteUser(siteRequest, c -> {
									if(c.succeeded()) {
										sqlTransactionSiteUser(siteRequest, d -> {
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
																userSiteUserDefine(siteRequest, jsonObject, false);

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
																						errorSiteUser(siteRequest, null, g);
																					}
																				});
																			} else {
																				errorSiteUser(siteRequest, null, f);
																			}
																		});
																	} else {
																		errorSiteUser(siteRequest, null, e);
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
																Boolean define = userSiteUserDefine(siteRequest, jsonObject, true);
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
																					errorSiteUser(siteRequest, null, f);
																				}
																			});
																		} else {
																			errorSiteUser(siteRequest, null, e);
																		}
																	});
																} else {
																	siteRequest.setSiteUser(siteUser1);
																	siteRequest.setUserName(siteUser1.getUserName());
																	siteRequest.setUserFirstName(siteUser1.getUserFirstName());
																	siteRequest.setUserLastName(siteUser1.getUserLastName());
																	siteRequest.setUserKey(siteUser1.getPk());
																	sqlRollbackSiteUser(siteRequest, e -> {
																		if(e.succeeded()) {
																			eventHandler.handle(Future.succeededFuture(siteRequest));
																		} else {
																			eventHandler.handle(Future.failedFuture(e.cause()));
																			errorSiteUser(siteRequest, null, e);
																		}
																	});
																}
															}
														} catch(Exception ex) {
															LOG.error(String.format("userSiteUser failed. "), ex);
															eventHandler.handle(Future.failedFuture(ex));
														}
													} else {
														LOG.error(String.format("userSiteUser failed. ", selectCAsync.cause()));
														eventHandler.handle(Future.failedFuture(selectCAsync.cause()));
													}
												});
											} else {
												LOG.error(String.format("userSiteUser failed. ", d.cause()));
												eventHandler.handle(Future.failedFuture(d.cause()));
											}
										});
									} else {
										LOG.error(String.format("userSiteUser failed. ", c.cause()));
										eventHandler.handle(Future.failedFuture(c.cause()));
									}
								});
							} else {
								LOG.error(String.format("userSiteUser failed. ", b.cause()));
								eventHandler.handle(Future.failedFuture(b.cause()));
							}
						});
					} else {
						siteContext.getOauth2AuthenticationProvider().refresh(token, b -> {
							if(b.succeeded()) {
								User user = b.result();
								serviceRequest.setUser(user.principal());
								userSiteUser(serviceRequest, c -> {
									if(c.succeeded()) {
										SiteRequestEnUS siteRequest = c.result();
										eventHandler.handle(Future.succeededFuture(siteRequest));
									} else {
										LOG.error(String.format("userSiteUser failed. ", c.cause()));
										eventHandler.handle(Future.failedFuture(c.cause()));
									}
								});
							} else {
								LOG.error(String.format("userSiteUser failed. ", a.cause()));
								eventHandler.handle(Future.failedFuture(a.cause()));
							}
						});
					}
				});
			}
		} catch(Exception ex) {
			LOG.error(String.format("userSiteUser failed. "), ex);
			eventHandler.handle(Future.failedFuture(ex));
		}
	}

	public Boolean userSiteUserDefine(SiteRequestEnUS siteRequest, JsonObject jsonObject, Boolean patch) {
		if(patch) {
			return false;
		} else {
			return false;
		}
	}

	public void aSearchSiteUserQ(String uri, String apiMethod, SearchList<SiteUser> searchList, String entityVar, String valueIndexed, String varIndexed) {
		searchList.setQuery(varIndexed + ":" + ("*".equals(valueIndexed) ? valueIndexed : ClientUtils.escapeQueryChars(valueIndexed)));
		if(!"*".equals(entityVar)) {
		}
	}

	public String aSearchSiteUserFq(String uri, String apiMethod, SearchList<SiteUser> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		if(StringUtils.startsWith(valueIndexed, "[")) {
			String[] fqs = StringUtils.substringBefore(StringUtils.substringAfter(valueIndexed, "["), "]").split(" TO ");
			if(fqs.length != 2)
				throw new RuntimeException(String.format("\"%s\" invalid range query. ", valueIndexed));
			String fq1 = fqs[0].equals("*") ? fqs[0] : SiteUser.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[0]);
			String fq2 = fqs[1].equals("*") ? fqs[1] : SiteUser.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), fqs[1]);
			 return varIndexed + ":[" + fq1 + " TO " + fq2 + "]";
		} else {
			return varIndexed + ":" + SiteUser.staticSolrFqForClass(entityVar, searchList.getSiteRequest_(), valueIndexed);
		}
	}

	public void aSearchSiteUserSort(String uri, String apiMethod, SearchList<SiteUser> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addSort(varIndexed, ORDER.valueOf(valueIndexed));
	}

	public void aSearchSiteUserRows(String uri, String apiMethod, SearchList<SiteUser> searchList, Integer valueRows) {
			searchList.setRows(apiMethod != null && apiMethod.contains("Search") ? valueRows : 10);
	}

	public void aSearchSiteUserStart(String uri, String apiMethod, SearchList<SiteUser> searchList, Integer valueStart) {
		searchList.setStart(valueStart);
	}

	public void aSearchSiteUserVar(String uri, String apiMethod, SearchList<SiteUser> searchList, String var, String value) {
		searchList.getSiteRequest_().getRequestVars().put(var, value);
	}

	public void aSearchSiteUserUri(String uri, String apiMethod, SearchList<SiteUser> searchList) {
	}

	public void varsSiteUser(SiteRequestEnUS siteRequest, Handler<AsyncResult<SearchList<ServiceResponse>>> eventHandler) {
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
					LOG.error(String.format("aSearchSiteUser failed. "), e);
					eventHandler.handle(Future.failedFuture(e));
				}
			});
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOG.error(String.format("aSearchSiteUser failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void aSearchSiteUser(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, Handler<AsyncResult<SearchList<SiteUser>>> eventHandler) {
		try {
			SearchList<SiteUser> searchList = aSearchSiteUserList(siteRequest, populate, store, modify, uri, apiMethod);
			eventHandler.handle(Future.succeededFuture(searchList));
		} catch(Exception e) {
			LOG.error(String.format("aSearchSiteUser failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public SearchList<SiteUser> aSearchSiteUserList(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod) {
		ServiceRequest serviceRequest = siteRequest.getServiceRequest();
		String entityListStr = siteRequest.getServiceRequest().getParams().getJsonObject("query").getString("fl");
		String[] entityList = entityListStr == null ? null : entityListStr.split(",\\s*");
		SearchList<SiteUser> searchList = new SearchList<SiteUser>();
		searchList.setPopulate(populate);
		searchList.setStore(store);
		searchList.setQuery("*:*");
		searchList.setC(SiteUser.class);
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

		List<String> roles = Arrays.asList("SiteAdmin", "SiteAdmin");
		List<String> roleReads = Arrays.asList("");
		if(
				!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles)
				&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles)
				&& (modify || !CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roleReads))
				&& (modify || !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roleReads))
				) {
			searchList.addFilterQuery("sessionId_indexed_string:" + ClientUtils.escapeQueryChars(Optional.ofNullable(siteRequest.getSessionId()).orElse("-----")) + " OR " + "sessionId_indexed_string:" + ClientUtils.escapeQueryChars(Optional.ofNullable(siteRequest.getSessionIdBefore()).orElse("-----"))
					+ " OR userKeys_indexed_longs:" + Optional.ofNullable(siteRequest.getUserKey()).orElse(0L));
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
							varsIndexed[i] = SiteUser.varIndexedSiteUser(entityVar);
						}
						searchList.add("facet.pivot", (solrLocalParams == null ? "" : solrLocalParams) + StringUtils.join(varsIndexed, ","));
					}
				} else if(paramValuesObject != null) {
					for(Object paramObject : paramObjects) {
						switch(paramName) {
							case "q":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								varIndexed = "*".equals(entityVar) ? entityVar : SiteUser.varSearchSiteUser(entityVar);
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								valueIndexed = StringUtils.isEmpty(valueIndexed) ? "*" : valueIndexed;
								aSearchSiteUserQ(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "fq":
								Matcher mFq = Pattern.compile("(\\w+):(.+?(?=(\\)|\\s+OR\\s+|\\s+AND\\s+|$)))").matcher((String)paramObject);
								boolean foundFq = mFq.find();
								if(foundFq) {
									StringBuffer sb = new StringBuffer();
									while(foundFq) {
										entityVar = mFq.group(1).trim();
										valueIndexed = mFq.group(2).trim();
										varIndexed = SiteUser.varIndexedSiteUser(entityVar);
										String entityFq = aSearchSiteUserFq(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
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
								varIndexed = SiteUser.varIndexedSiteUser(entityVar);
								aSearchSiteUserSort(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
								break;
							case "start":
								valueStart = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
								aSearchSiteUserStart(uri, apiMethod, searchList, valueStart);
								break;
							case "rows":
								valueRows = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
								aSearchSiteUserRows(uri, apiMethod, searchList, valueRows);
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
									varIndexed = SiteUser.varIndexedSiteUser(entityVar);
									searchList.add("facet.range", (solrLocalParams == null ? "" : solrLocalParams) + varIndexed);
								}
								break;
							case "facet.field":
								entityVar = (String)paramObject;
								varIndexed = SiteUser.varIndexedSiteUser(entityVar);
								if(varIndexed != null)
									searchList.addFacetField(varIndexed);
								break;
							case "var":
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								aSearchSiteUserVar(uri, apiMethod, searchList, entityVar, valueIndexed);
								break;
						}
					}
					aSearchSiteUserUri(uri, apiMethod, searchList);
				}
			} catch(Exception e) {
				ExceptionUtils.rethrow(e);
			}
		});
		if("*:*".equals(searchList.getQuery()) && searchList.getSorts().size() == 0) {
			searchList.addSort("created_indexed_date", ORDER.desc);
		}
		aSearchSiteUser2(siteRequest, populate, store, modify, uri, apiMethod, searchList);
		searchList.initDeepForClass(siteRequest);
		return searchList;
	}
	public void aSearchSiteUser2(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, SearchList<SiteUser> searchList) {
	}

	public void defineSiteUser(SiteUser o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT * FROM SiteUser WHERE pk=$1")
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
										LOG.error(String.format("defineSiteUser failed. "), e);
									}
								}
							}
						}
						eventHandler.handle(Future.succeededFuture());
					} catch(Exception e) {
						LOG.error(String.format("defineSiteUser failed. "), e);
						eventHandler.handle(Future.failedFuture(e));
					}
				} else {
					LOG.error(String.format("defineSiteUser failed. ", defineAsync.cause()));
					eventHandler.handle(Future.failedFuture(defineAsync.cause()));
				}
			});
		} catch(Exception e) {
			LOG.error(String.format("defineSiteUser failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void attributeSiteUser(SiteUser o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
			eventHandler.handle(Future.succeededFuture());
	}

	public void indexSiteUser(SiteUser o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			o.initDeepForClass(siteRequest);
			o.indexForClass();
			eventHandler.handle(Future.succeededFuture());
		} catch(Exception e) {
			LOG.error(String.format("indexSiteUser failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void refreshSiteUser(SiteUser o, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Boolean refresh = !"false".equals(siteRequest.getRequestVars().get("refresh"));
			if(refresh && BooleanUtils.isFalse(Optional.ofNullable(siteRequest.getApiRequest_()).map(ApiRequest::getEmpty).orElse(true))) {
				SearchList<SiteUser> searchList = new SearchList<SiteUser>();
				searchList.setStore(true);
				searchList.setQuery("*:*");
				searchList.setC(SiteUser.class);
				searchList.addFilterQuery("modified_indexed_date:[" + DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(siteRequest.getApiRequest_().getCreated().toInstant(), ZoneId.of("UTC"))) + " TO *]");
				searchList.setRows(1000);
				searchList.initDeepSearchList(siteRequest);
				List<Future> futures = new ArrayList<>();

				for(int i=0; i < pks.size(); i++) {
					Long pk2 = pks.get(i);
					String classSimpleName2 = classes.get(i);
				}

				CompositeFuture.all(futures).onComplete(a -> {
					if(a.succeeded()) {
						SiteUserEnUSApiServiceImpl service = new SiteUserEnUSApiServiceImpl(siteRequest.getSiteContext_());
						List<Future> futures2 = new ArrayList<>();
						for(SiteUser o2 : searchList.getList()) {
							SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForSiteUser(siteRequest.getUser(), siteContext, siteRequest.getServiceRequest(), new JsonObject());
							o2.setSiteRequest_(siteRequest2);
							futures2.add(
								service.patchSiteUserFuture(o2, false, b -> {
									if(b.succeeded()) {
									} else {
										LOG.info(String.format("SiteUser %s failed. ", o2.getPk()));
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
								errorSiteUser(siteRequest, eventHandler, b);
							}
						});
					} else {
						LOG.error("Refresh relations failed. ", a.cause());
						errorSiteUser(siteRequest, eventHandler, a);
					}
				});
			} else {
				eventHandler.handle(Future.succeededFuture());
			}
		} catch(Exception e) {
			LOG.error(String.format("refreshSiteUser failed. "), e);
			eventHandler.handle(Future.failedFuture(e));
		}
	}
}
