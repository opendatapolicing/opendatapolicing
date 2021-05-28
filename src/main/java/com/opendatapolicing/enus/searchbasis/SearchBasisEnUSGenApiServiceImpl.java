package com.opendatapolicing.enus.searchbasis;

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
public class SearchBasisEnUSGenApiServiceImpl extends BaseApiServiceImpl implements SearchBasisEnUSGenApiService {

	protected static final Logger LOG = LoggerFactory.getLogger(SearchBasisEnUSGenApiServiceImpl.class);

	public SearchBasisEnUSGenApiServiceImpl(Semaphore semaphore, EventBus eventBus, JsonObject config, WorkerExecutor workerExecutor, PgPool pgPool, WebClient webClient, OAuth2Auth oauth2AuthenticationProvider, AuthorizationProvider authorizationProvider) {
		super(semaphore, eventBus, config, workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider);
	}

	// PUTImport //

	@Override
	public void putimportSearchBasis(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("putimportSearchBasis started. "));
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
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
					try {
						ApiRequest apiRequest = new ApiRequest();
						JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
						apiRequest.setRows(jsonArray.size());
						apiRequest.setNumFound(new Integer(jsonArray.size()).longValue());
						apiRequest.setNumPATCH(0L);
						apiRequest.initDeepApiRequest(siteRequest);
						siteRequest.setApiRequest_(apiRequest);
						eventBus.publish("websocketSearchBasis", JsonObject.mapFrom(apiRequest).toString());
						varsSearchBasis(siteRequest).onSuccess(d -> {
							listPUTImportSearchBasis(apiRequest, siteRequest).onSuccess(e -> {
								response200PUTImportSearchBasis(siteRequest).onSuccess(response -> {
									LOG.debug(String.format("putimportSearchBasis succeeded. "));
									eventHandler.handle(Future.succeededFuture(response));
								}).onFailure(ex -> {
									LOG.error(String.format("putimportSearchBasis failed. "), ex);
									error(siteRequest, eventHandler, ex);
								});
							}).onFailure(ex -> {
								LOG.error(String.format("putimportSearchBasis failed. "), ex);
								error(siteRequest, eventHandler, ex);
							});
						}).onFailure(ex -> {
							LOG.error(String.format("putimportSearchBasis failed. "), ex);
							error(siteRequest, eventHandler, ex);
						});
					} catch(Exception ex) {
						LOG.error(String.format("putimportSearchBasis failed. "), ex);
						error(siteRequest, eventHandler, ex);
					}
				}
			} catch(Exception ex) {
				LOG.error(String.format("putimportSearchBasis failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("putimportSearchBasis failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("putimportSearchBasis failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<Void> listPUTImportSearchBasis(ApiRequest apiRequest, SiteRequestEnUS siteRequest) {
		Promise<Void> promise = Promise.promise();
		List<Future> futures = new ArrayList<>();
		JsonArray jsonArray = Optional.ofNullable(siteRequest.getJsonObject()).map(o -> o.getJsonArray("list")).orElse(new JsonArray());
		try {
			jsonArray.forEach(obj -> {
				futures.add(Future.future(promise1 -> {
					workerExecutor.executeBlocking(blockingCodeHandler -> {
						try {
							semaphore.acquire();
							try {
								JsonObject params = new JsonObject();
								params.put("body", obj);
								params.put("path", new JsonObject());
								params.put("cookie", new JsonObject());
								params.put("header", new JsonObject());
								params.put("form", new JsonObject());
								params.put("query", new JsonObject());
								JsonObject context = new JsonObject().put("params", params);
								JsonObject json = new JsonObject().put("context", context);
								eventBus.request("opendatapolicing-enUS-SearchBasis", json, new DeliveryOptions().addHeader("action", "putimportSearchBasisFuture")).onSuccess(a -> {
									blockingCodeHandler.complete();
									semaphore.release();
								}).onFailure(ex -> {
									LOG.error(String.format("listPUTImportSearchBasis failed. "), ex);
									blockingCodeHandler.fail(ex);
									semaphore.release();
								});
							} catch(Exception ex) {
								LOG.error(String.format("listPUTImportSearchBasis failed. "), ex);
								blockingCodeHandler.fail(ex);
								semaphore.release();
							}
						} catch(Exception ex) {
							LOG.error(String.format("listPUTImportSearchBasis failed. "), ex);
							blockingCodeHandler.fail(ex);
						}
					}, false).onSuccess(a -> {
						promise1.complete();
					}).onFailure(ex -> {
						LOG.error(String.format("listPUTImportSearchBasis failed. "), ex);
						promise1.fail(ex);
					});
				}));
			});
			CompositeFuture.all(futures).onSuccess(a -> {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
				promise.complete();
			}).onFailure(ex -> {
				LOG.error(String.format("listPUTImportSearchBasis failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("listPUTImportSearchBasis failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	@Override
	public void putimportSearchBasisFuture(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = generateSiteRequestEnUS(null, serviceRequest, body);
			ApiRequest apiRequest = new ApiRequest();
			apiRequest.setRows(1);
			apiRequest.setNumFound(1L);
			apiRequest.setNumPATCH(0L);
			apiRequest.initDeepApiRequest(siteRequest);
			siteRequest.setApiRequest_(apiRequest);
			body.put("inheritPk", body.getValue("pk"));
			if(Optional.ofNullable(serviceRequest.getParams()).map(p -> p.getJsonObject("query")).map( q -> q.getJsonArray("var")).orElse(new JsonArray()).stream().filter(s -> "refresh:false".equals(s)).count() > 0L) {
				siteRequest.getRequestVars().put( "refresh", "false" );
			}

			SearchList<SearchBasis> searchList = new SearchList<SearchBasis>();
			searchList.setStore(true);
			searchList.setQuery("*:*");
			searchList.setC(SearchBasis.class);
			searchList.addFilterQuery("inheritPk_indexed_string:" + ClientUtils.escapeQueryChars(body.getString("pk")));
			searchList.promiseDeepForClass(siteRequest).onSuccess(a -> {
				try {
					if(searchList.size() == 1) {
						SearchBasis o = searchList.getList().stream().findFirst().orElse(null);
						SearchBasis o2 = new SearchBasis();
						JsonObject body2 = new JsonObject();
						for(String f : body.fieldNames()) {
							Object bodyVal = body.getValue(f);
							if(bodyVal instanceof JsonArray) {
								JsonArray bodyVals = (JsonArray)bodyVal;
								Collection<?> vals = (Collection<?>)o.obtainForClass(f);
								if(bodyVals.size() == vals.size()) {
									Boolean match = true;
									for(Object val : vals) {
										if(val != null) {
											if(!bodyVals.contains(val.toString())) {
												match = false;
												break;
											}
										} else {
											match = false;
											break;
										}
									}
									if(!match) {
										body2.put("set" + StringUtils.capitalize(f), bodyVal);
									}
								} else {
									body2.put("set" + StringUtils.capitalize(f), bodyVal);
								}
							} else {
								o2.defineForClass(f, bodyVal);
								if(!StringUtils.containsAny(f, "pk", "created", "setCreated") && !Objects.equals(o.obtainForClass(f), o2.obtainForClass(f)))
									body2.put("set" + StringUtils.capitalize(f), bodyVal);
							}
						}
						for(String f : Optional.ofNullable(o.getSaves()).orElse(new ArrayList<>())) {
							if(!body.fieldNames().contains(f))
								body2.putNull("set" + StringUtils.capitalize(f));
						}
						if(body2.size() > 0) {
							siteRequest.setJsonObject(body2);
							patchSearchBasisFuture(o, true).onSuccess(b -> {
								LOG.info("Import SearchBasis {} succeeded, modified SearchBasis. ", body.getValue("pk"));
								eventHandler.handle(Future.succeededFuture());
							}).onFailure(ex -> {
								LOG.error(String.format("putimportSearchBasisFuture failed. "), ex);
							});
						} else {
							eventHandler.handle(Future.succeededFuture());
						}
					} else {
						postSearchBasisFuture(siteRequest, true).onSuccess(b -> {
							LOG.info("Import SearchBasis {} succeeded, created new SearchBasis. ", body.getValue("pk"));
							eventHandler.handle(Future.succeededFuture());
						}).onFailure(ex -> {
							LOG.error(String.format("putimportSearchBasisFuture failed. "), ex);
							eventHandler.handle(Future.failedFuture(ex));
						});
					}
				} catch(Exception ex) {
					LOG.error(String.format("putimportSearchBasisFuture failed. "), ex);
					eventHandler.handle(Future.failedFuture(ex));
				}
			}).onFailure(ex -> {
				LOG.error(String.format("putimportSearchBasisFuture failed. "), ex);
				eventHandler.handle(Future.failedFuture(ex));
			});
		} catch(Exception ex) {
			LOG.error(String.format("putimportSearchBasisFuture failed. "), ex);
			eventHandler.handle(Future.failedFuture(ex));
		}
	}

	public Future<ServiceResponse> response200PUTImportSearchBasis(SiteRequestEnUS siteRequest) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			JsonObject json = new JsonObject();
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200PUTImportSearchBasis failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// POST //

	@Override
	public void postSearchBasis(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("postSearchBasis started. "));
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
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
					eventBus.publish("websocketSearchBasis", JsonObject.mapFrom(apiRequest).toString());
					workerExecutor.executeBlocking(blockingCodeHandler -> {
						try {
							semaphore.acquire();
							try {
								JsonObject params = new JsonObject();
								params.put("body", siteRequest.getJsonObject());
								params.put("path", new JsonObject());
								params.put("cookie", new JsonObject());
								params.put("header", new JsonObject());
								params.put("form", new JsonObject());
								params.put("query", new JsonObject());
								JsonObject context = new JsonObject().put("params", params);
								JsonObject json = new JsonObject().put("context", context);
								eventBus.request("opendatapolicing-enUS-SearchBasis", json, new DeliveryOptions().addHeader("action", "postSearchBasisFuture")).onSuccess(a -> {
									blockingCodeHandler.complete();
									semaphore.release();
								}).onFailure(ex -> {
									LOG.error(String.format("postSearchBasis failed. "), ex);
									blockingCodeHandler.fail(ex);
									semaphore.release();
								});
							} catch(Exception ex) {
								LOG.error(String.format("postSearchBasis failed. "), ex);
								blockingCodeHandler.fail(ex);
								semaphore.release();
							}
						} catch(Exception ex) {
							LOG.error(String.format("postSearchBasis failed. "), ex);
							blockingCodeHandler.fail(ex);
						}
					}, false).onSuccess(a -> {
						postSearchBasisFuture(siteRequest, false).onSuccess(searchBasis -> {
							apiRequest.setPk(searchBasis.getPk());
							response200POSTSearchBasis(searchBasis).onSuccess(response -> {
								eventHandler.handle(Future.succeededFuture(response));
								LOG.debug(String.format("postSearchBasis succeeded. "));
							}).onFailure(ex -> {
								LOG.error(String.format("postSearchBasis failed. "), ex);
								error(siteRequest, eventHandler, ex);
							});
						}).onFailure(ex -> {
							LOG.error(String.format("postSearchBasis failed. "), ex);
							error(siteRequest, eventHandler, ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("postSearchBasis failed. "), ex);
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("postSearchBasis failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("postSearchBasis failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("postSearchBasis failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	@Override
	public void postSearchBasisFuture(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUS(null, serviceRequest, body);
		ApiRequest apiRequest = new ApiRequest();
		apiRequest.setRows(1);
		apiRequest.setNumFound(1L);
		apiRequest.setNumPATCH(0L);
		apiRequest.initDeepApiRequest(siteRequest);
		siteRequest.setApiRequest_(apiRequest);
		if(Optional.ofNullable(serviceRequest.getParams()).map(p -> p.getJsonObject("query")).map( q -> q.getJsonArray("var")).orElse(new JsonArray()).stream().filter(s -> "refresh:false".equals(s)).count() > 0L) {
			siteRequest.getRequestVars().put( "refresh", "false" );
		}
		postSearchBasisFuture(siteRequest, false).onSuccess(a -> {
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(new JsonObject().encodePrettily()))));
		}).onFailure(ex -> {
			eventHandler.handle(Future.failedFuture(ex));
		});
	}

	public Future<SearchBasis> postSearchBasisFuture(SiteRequestEnUS siteRequest, Boolean inheritPk) {
		Promise<SearchBasis> promise = Promise.promise();

		try {
			pgPool.withTransaction(sqlConnection -> {
				Promise<SearchBasis> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				createSearchBasis(siteRequest).onSuccess(searchBasis -> {
					sqlPOSTSearchBasis(searchBasis, inheritPk).onSuccess(b -> {
						defineSearchBasis(searchBasis).onSuccess(c -> {
							attributeSearchBasis(searchBasis).onSuccess(d -> {
								indexSearchBasis(searchBasis).onSuccess(e -> {
									promise1.complete(searchBasis);
								}).onFailure(ex -> {
									LOG.error(String.format("postSearchBasisFuture failed. "), ex);
									promise1.fail(ex);
								});
							}).onFailure(ex -> {
								LOG.error(String.format("postSearchBasisFuture failed. "), ex);
								promise1.fail(ex);
							});
						}).onFailure(ex -> {
							LOG.error(String.format("postSearchBasisFuture failed. "), ex);
							promise1.fail(ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("postSearchBasisFuture failed. "), ex);
						promise1.fail(ex);
					});
				}).onFailure(ex -> {
					LOG.error(String.format("postSearchBasisFuture failed. "), ex);
					promise1.fail(ex);
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, ex);
			}).compose(searchBasis -> {
				Promise<SearchBasis> promise2 = Promise.promise();
				refreshSearchBasis(searchBasis).onSuccess(a -> {
					ApiRequest apiRequest = siteRequest.getApiRequest_();
					if(apiRequest != null) {
						apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
						searchBasis.apiRequestSearchBasis();
						eventBus.publish("websocketSearchBasis", JsonObject.mapFrom(apiRequest).toString());
					}
					promise2.complete(searchBasis);
				}).onFailure(ex -> {
					LOG.error(String.format("postSearchBasisFuture failed. "), ex);
					promise2.fail(ex);
				});
				return promise2.future();
			}).onSuccess(searchBasis -> {
				promise.complete(searchBasis);
				LOG.debug(String.format("postSearchBasisFuture succeeded. "));
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("postSearchBasisFuture failed. "), ex);
			promise.fail(ex);
			error(siteRequest, null, ex);
		}
		return promise.future();
	}

	public Future<Void> sqlPOSTSearchBasis(SearchBasis o, Boolean inheritPk) {
		Promise<Void> promise = Promise.promise();
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
			List<Future> futures1 = new ArrayList<>();
			List<Future> futures2 = new ArrayList<>();

			if(jsonObject != null) {
				Set<String> entityVars = jsonObject.fieldNames();
				for(String entityVar : entityVars) {
					switch(entityVar) {
					case SearchBasis.VAR_inheritPk:
						o2.setInheritPk(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SearchBasis.VAR_inheritPk + "=$" + num);
						num++;
						bParams.add(o2.sqlInheritPk());
						break;
					case SearchBasis.VAR_searchId:
						o2.setSearchId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SearchBasis.VAR_searchId + "=$" + num);
						num++;
						bParams.add(o2.sqlSearchId());
						break;
					case SearchBasis.VAR_searchBasisId:
						o2.setSearchBasisId(jsonObject.getString(entityVar));
						if(bParams.size() > 0) {
							bSql.append(", ");
						}
						bSql.append(SearchBasis.VAR_searchBasisId + "=$" + num);
						num++;
						bParams.add(o2.sqlSearchBasisId());
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
					LOG.error(String.format("sqlPOSTSearchBasis failed. "), ex);
					promise.fail(ex);
				});
			}).onFailure(ex -> {
				LOG.error(String.format("sqlPOSTSearchBasis failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("sqlPOSTSearchBasis failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<ServiceResponse> response200POSTSearchBasis(SearchBasis o) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200POSTSearchBasis failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// PATCH //

	@Override
	public void patchSearchBasis(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		LOG.debug(String.format("patchSearchBasis started. "));
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
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
					serviceRequest.getParams().getJsonObject("query").put("rows", 100);
					searchSearchBasisList(siteRequest, false, true, true, "/api/search-basis", "PATCH").onSuccess(listSearchBasis -> {
						try {
							List<String> roles2 = Arrays.asList("SiteAdmin");
							if(listSearchBasis.getQueryResponse().getResults().getNumFound() > 1
									&& !CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles2)
									&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles2)
									) {
								String message = String.format("roles required: " + String.join(", ", roles2));
								LOG.error(message);
								error(siteRequest, eventHandler, new RuntimeException(message));
							} else {

								ApiRequest apiRequest = new ApiRequest();
								apiRequest.setRows(listSearchBasis.getRows());
								apiRequest.setNumFound(listSearchBasis.getQueryResponse().getResults().getNumFound());
								apiRequest.setNumPATCH(0L);
								apiRequest.initDeepApiRequest(siteRequest);
								siteRequest.setApiRequest_(apiRequest);
								if(apiRequest.getNumFound() == 1L)
									apiRequest.setOriginal(listSearchBasis.first());
								apiRequest.setPk(listSearchBasis.first().getPk());
								eventBus.publish("websocketSearchBasis", JsonObject.mapFrom(apiRequest).toString());

								listPATCHSearchBasis(apiRequest, listSearchBasis).onSuccess(e -> {
									response200PATCHSearchBasis(siteRequest).onSuccess(response -> {
										LOG.debug(String.format("patchSearchBasis succeeded. "));
										eventHandler.handle(Future.succeededFuture(response));
									}).onFailure(ex -> {
										LOG.error(String.format("patchSearchBasis failed. "), ex);
										error(siteRequest, eventHandler, ex);
									});
								}).onFailure(ex -> {
									LOG.error(String.format("patchSearchBasis failed. "), ex);
									error(siteRequest, eventHandler, ex);
								});
							}
						} catch(Exception ex) {
							LOG.error(String.format("patchSearchBasis failed. "), ex);
							error(siteRequest, eventHandler, ex);
						}
					}).onFailure(ex -> {
						LOG.error(String.format("patchSearchBasis failed. "), ex);
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("patchSearchBasis failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("patchSearchBasis failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("patchSearchBasis failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<Void> listPATCHSearchBasis(ApiRequest apiRequest, SearchList<SearchBasis> listSearchBasis) {
		Promise<Void> promise = Promise.promise();
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listSearchBasis.getSiteRequest_();
		listSearchBasis.getList().forEach(o -> {
			futures.add(Future.future(promise1 -> {
				workerExecutor.executeBlocking(blockingCodeHandler -> {
					try {
						semaphore.acquire();
						try {
							Long pk = o.getPk();

							JsonObject params = new JsonObject();
							params.put("body", siteRequest.getJsonObject().put(SearchBasis.VAR_pk, pk.toString()));
							params.put("path", new JsonObject());
							params.put("cookie", new JsonObject());
							params.put("header", new JsonObject());
							params.put("form", new JsonObject());
							params.put("query", new JsonObject().put("q", "*:*").put("fq", new JsonArray().add("pk:" + pk)));
							JsonObject context = new JsonObject().put("params", params);
							JsonObject json = new JsonObject().put("context", context);
							eventBus.request("opendatapolicing-enUS-SearchBasis", json, new DeliveryOptions().addHeader("action", "patchSearchBasisFuture")).onSuccess(a -> {
								blockingCodeHandler.complete();
								semaphore.release();
							}).onFailure(ex -> {
								LOG.error(String.format("listPATCHSearchBasis failed. "), ex);
								blockingCodeHandler.fail(ex);
								semaphore.release();
							});
						} catch(Exception ex) {
							LOG.error(String.format("listPATCHSearchBasis failed. "), ex);
							blockingCodeHandler.fail(ex);
							semaphore.release();
						}
					} catch(Exception ex) {
						LOG.error(String.format("listPATCHSearchBasis failed. "), ex);
						blockingCodeHandler.fail(ex);
					}
				}, false).onSuccess(a -> {
					promise1.complete();
				}).onFailure(ex -> {
					LOG.error(String.format("listPATCHSearchBasis failed. "), ex);
					promise1.fail(ex);
				});
			}));
		});
		CompositeFuture.all(futures).onSuccess( a -> {
			if(apiRequest != null) {
				apiRequest.setNumPATCH(apiRequest.getNumPATCH() + listSearchBasis.getQueryResponse().getResults().size());
				if(apiRequest.getNumFound() == 1L)
					listSearchBasis.first().apiRequestSearchBasis();
				eventBus.publish("websocketSearchBasis", JsonObject.mapFrom(apiRequest).toString());
			}
			listSearchBasis.next().onSuccess(next -> {
				if(next) {
					listPATCHSearchBasis(apiRequest, listSearchBasis);
				} else {
					promise.complete();
				}
			}).onFailure(ex -> {
				LOG.error(String.format("listPATCHSearchBasis failed. "), ex);
				promise.fail(ex);
			});
		}).onFailure(ex -> {
			LOG.error(String.format("listPATCHSearchBasis failed. "), ex);
			promise.fail(ex);
		});
		return promise.future();
	}

	@Override
	public void patchSearchBasisFuture(JsonObject body, ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUS(null, serviceRequest, body);
		SearchBasis o = new SearchBasis();
		o.setSiteRequest_(siteRequest);
		ApiRequest apiRequest = new ApiRequest();
		apiRequest.setRows(1);
		apiRequest.setNumFound(1L);
		apiRequest.setNumPATCH(0L);
		apiRequest.initDeepApiRequest(siteRequest);
		siteRequest.setApiRequest_(apiRequest);
		if(Optional.ofNullable(serviceRequest.getParams()).map(p -> p.getJsonObject("query")).map( q -> q.getJsonArray("var")).orElse(new JsonArray()).stream().filter(s -> "refresh:false".equals(s)).count() > 0L) {
			siteRequest.getRequestVars().put( "refresh", "false" );
		}
		o.setPk(body.getString(SearchBasis.VAR_pk));
		patchSearchBasisFuture(o, false).onSuccess(a -> {
			eventHandler.handle(Future.succeededFuture(ServiceResponse.completedWithJson(Buffer.buffer(new JsonObject().encodePrettily()))));
		}).onFailure(ex -> {
			eventHandler.handle(Future.failedFuture(ex));
		});
	}

	public Future<SearchBasis> patchSearchBasisFuture(SearchBasis o, Boolean inheritPk) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		Promise<SearchBasis> promise = Promise.promise();

		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			pgPool.withTransaction(sqlConnection -> {
				Promise<SearchBasis> promise1 = Promise.promise();
				siteRequest.setSqlConnection(sqlConnection);
				sqlPATCHSearchBasis(o, inheritPk).onSuccess(searchBasis -> {
					defineSearchBasis(searchBasis).onSuccess(c -> {
						attributeSearchBasis(searchBasis).onSuccess(d -> {
							indexSearchBasis(searchBasis).onSuccess(e -> {
								promise1.complete(searchBasis);
							}).onFailure(ex -> {
								LOG.error(String.format("patchSearchBasisFuture failed. "), ex);
								promise1.fail(ex);
							});
						}).onFailure(ex -> {
							LOG.error(String.format("patchSearchBasisFuture failed. "), ex);
							promise1.fail(ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("patchSearchBasisFuture failed. "), ex);
						promise1.fail(ex);
					});
				}).onFailure(ex -> {
					LOG.error(String.format("patchSearchBasisFuture failed. "), ex);
					promise1.fail(ex);
				});
				return promise1.future();
			}).onSuccess(a -> {
				siteRequest.setSqlConnection(null);
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, ex);
			}).compose(searchBasis -> {
				Promise<SearchBasis> promise2 = Promise.promise();
				refreshSearchBasis(searchBasis).onSuccess(a -> {
					promise2.complete(searchBasis);
				}).onFailure(ex -> {
					LOG.error(String.format("patchSearchBasisFuture failed. "), ex);
					promise2.fail(ex);
				});
				return promise2.future();
			}).onSuccess(searchBasis -> {
				promise.complete(searchBasis);
				LOG.debug(String.format("patchSearchBasisFuture succeeded. "));
			}).onFailure(ex -> {
				promise.fail(ex);
				error(siteRequest, null, ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("patchSearchBasisFuture failed. "), ex);
			promise.fail(ex);
			error(siteRequest, null, ex);
		}
		return promise.future();
	}

	public Future<SearchBasis> sqlPATCHSearchBasis(SearchBasis o, Boolean inheritPk) {
		Promise<SearchBasis> promise = Promise.promise();
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
			List<Future> futures1 = new ArrayList<>();
			List<Future> futures2 = new ArrayList<>();

			for(String entityVar : methodNames) {
				switch(entityVar) {
					case "setInheritPk":
							o2.setInheritPk(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(SearchBasis.VAR_inheritPk + "=$" + num);
							num++;
							bParams.add(o2.sqlInheritPk());
						break;
					case "setSearchId":
							o2.setSearchId(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(SearchBasis.VAR_searchId + "=$" + num);
							num++;
							bParams.add(o2.sqlSearchId());
						break;
					case "setSearchBasisId":
							o2.setSearchBasisId(jsonObject.getString(entityVar));
							if(bParams.size() > 0)
								bSql.append(", ");
							bSql.append(SearchBasis.VAR_searchBasisId + "=$" + num);
							num++;
							bParams.add(o2.sqlSearchBasisId());
						break;
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
					SearchBasis o3 = new SearchBasis();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.setPk(pk);
					promise.complete(o3);
				}).onFailure(ex -> {
					LOG.error(String.format("sqlPATCHSearchBasis failed. "), ex);
					promise.fail(ex);
				});
			}).onFailure(ex -> {
				LOG.error(String.format("sqlPATCHSearchBasis failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("sqlPATCHSearchBasis failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<ServiceResponse> response200PATCHSearchBasis(SiteRequestEnUS siteRequest) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			JsonObject json = new JsonObject();
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200PATCHSearchBasis failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// GET //

	@Override
	public void getSearchBasis(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setRequestUri("/api/search-basis/{id}");
				siteRequest.setRequestMethod("GET");
				{
					searchSearchBasisList(siteRequest, false, true, false, "/api/search-basis/{id}", "GET").onSuccess(listSearchBasis -> {
						response200GETSearchBasis(listSearchBasis).onSuccess(response -> {
							eventHandler.handle(Future.succeededFuture(response));
							LOG.debug(String.format("getSearchBasis succeeded. "));
						}).onFailure(ex -> {
							LOG.error(String.format("getSearchBasis failed. "), ex);
							error(siteRequest, eventHandler, ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("getSearchBasis failed. "), ex);
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("getSearchBasis failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("getSearchBasis failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("getSearchBasis failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<ServiceResponse> response200GETSearchBasis(SearchList<SearchBasis> listSearchBasis) {
		Promise<ServiceResponse> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = listSearchBasis.getSiteRequest_();
			SolrDocumentList solrDocuments = listSearchBasis.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listSearchBasis.getList().stream().findFirst().orElse(null));
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200GETSearchBasis failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	// Search //

	@Override
	public void searchSearchBasis(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setRequestUri("/api/search-basis");
				siteRequest.setRequestMethod("Search");
				{
					searchSearchBasisList(siteRequest, false, true, false, "/api/search-basis", "Search").onSuccess(listSearchBasis -> {
						response200SearchSearchBasis(listSearchBasis).onSuccess(response -> {
							eventHandler.handle(Future.succeededFuture(response));
							LOG.debug(String.format("searchSearchBasis succeeded. "));
						}).onFailure(ex -> {
							LOG.error(String.format("searchSearchBasis failed. "), ex);
							error(siteRequest, eventHandler, ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("searchSearchBasis failed. "), ex);
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("searchSearchBasis failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("searchSearchBasis failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("searchSearchBasis failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<ServiceResponse> response200SearchSearchBasis(SearchList<SearchBasis> listSearchBasis) {
		Promise<ServiceResponse> promise = Promise.promise();
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
					responsePivotSearchSearchBasis(pivotFields, pivotArray);
				}
			}
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200SearchSearchBasis failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
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
				responsePivotSearchSearchBasis(pivotFields2, pivotArray2);
			}
		}
	}

	// AdminSearch //

	@Override
	public void adminsearchSearchBasis(ServiceRequest serviceRequest, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		user(serviceRequest).onSuccess(siteRequest -> {
			try {
				siteRequest.setRequestUri("/api/admin/search-basis");
				siteRequest.setRequestMethod("AdminSearch");
				{
					searchSearchBasisList(siteRequest, false, true, false, "/api/admin/search-basis", "AdminSearch").onSuccess(listSearchBasis -> {
						response200AdminSearchSearchBasis(listSearchBasis).onSuccess(response -> {
							eventHandler.handle(Future.succeededFuture(response));
							LOG.debug(String.format("adminsearchSearchBasis succeeded. "));
						}).onFailure(ex -> {
							LOG.error(String.format("adminsearchSearchBasis failed. "), ex);
							error(siteRequest, eventHandler, ex);
						});
					}).onFailure(ex -> {
						LOG.error(String.format("adminsearchSearchBasis failed. "), ex);
						error(siteRequest, eventHandler, ex);
					});
				}
			} catch(Exception ex) {
				LOG.error(String.format("adminsearchSearchBasis failed. "), ex);
				error(null, eventHandler, ex);
			}
		}).onFailure(ex -> {
			if("Inactive Token".equals(ex.getMessage())) {
				try {
					eventHandler.handle(Future.succeededFuture(new ServiceResponse(302, "Found", null, MultiMap.caseInsensitiveMultiMap().add(HttpHeaders.LOCATION, "/logout?redirect_uri=" + URLEncoder.encode(serviceRequest.getExtra().getString("uri"), "UTF-8")))));
				} catch(Exception ex2) {
					LOG.error(String.format("adminsearchSearchBasis failed. ", ex2));
					error(null, eventHandler, ex2);
				}
			} else {
				LOG.error(String.format("adminsearchSearchBasis failed. "), ex);
				error(null, eventHandler, ex);
			}
		});
	}


	public Future<ServiceResponse> response200AdminSearchSearchBasis(SearchList<SearchBasis> listSearchBasis) {
		Promise<ServiceResponse> promise = Promise.promise();
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
					responsePivotAdminSearchSearchBasis(pivotFields, pivotArray);
				}
			}
			if(exceptionSearch != null) {
				json.put("exceptionSearch", exceptionSearch.getMessage());
			}
			promise.complete(ServiceResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily())));
		} catch(Exception ex) {
			LOG.error(String.format("response200AdminSearchSearchBasis failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
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
				responsePivotAdminSearchSearchBasis(pivotFields2, pivotArray2);
			}
		}
	}
	public static final String VAR_searchBasisKey = "searchBasisKey";
	public static final String VAR_searchId = "searchId";
	public static final String VAR_trafficSearchSearch = "trafficSearchSearch";
	public static final String VAR_trafficSearch_ = "trafficSearch_";
	public static final String VAR_stateAbbreviation = "stateAbbreviation";
	public static final String VAR_agencyTitle = "agencyTitle";
	public static final String VAR_stopDateTime = "stopDateTime";
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
	public static final String VAR_personAge = "personAge";
	public static final String VAR_personTypeId = "personTypeId";
	public static final String VAR_personTypeTitle = "personTypeTitle";
	public static final String VAR_personTypeDriver = "personTypeDriver";
	public static final String VAR_personTypePassenger = "personTypePassenger";
	public static final String VAR_personGenderId = "personGenderId";
	public static final String VAR_personGenderTitle = "personGenderTitle";
	public static final String VAR_personGenderFemale = "personGenderFemale";
	public static final String VAR_personGenderMale = "personGenderMale";
	public static final String VAR_personEthnicityId = "personEthnicityId";
	public static final String VAR_personEthnicityTitle = "personEthnicityTitle";
	public static final String VAR_personRaceId = "personRaceId";
	public static final String VAR_personRaceTitle = "personRaceTitle";
	public static final String VAR_stopId = "stopId";
	public static final String VAR_searchTypeNum = "searchTypeNum";
	public static final String VAR_searchTypeTitle = "searchTypeTitle";
	public static final String VAR_searchVehicle = "searchVehicle";
	public static final String VAR_searchDriver = "searchDriver";
	public static final String VAR_searchPassenger = "searchPassenger";
	public static final String VAR_searchProperty = "searchProperty";
	public static final String VAR_searchVehicleSiezed = "searchVehicleSiezed";
	public static final String VAR_searchPersonalPropertySiezed = "searchPersonalPropertySiezed";
	public static final String VAR_searchOtherPropertySiezed = "searchOtherPropertySiezed";
	public static final String VAR_searchBasisId = "searchBasisId";
	public static final String VAR_searchBasisTitle = "searchBasisTitle";

	// General //

	public Future<SearchBasis> createSearchBasis(SiteRequestEnUS siteRequest) {
		Promise<SearchBasis> promise = Promise.promise();
		try {
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			String userId = siteRequest.getUserId();
			Long userKey = siteRequest.getUserKey();
			ZonedDateTime created = Optional.ofNullable(siteRequest.getJsonObject()).map(j -> j.getString("created")).map(s -> ZonedDateTime.parse(s, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(config.getString("siteZone"))))).orElse(ZonedDateTime.now(ZoneId.of(config.getString("siteZone"))));

			sqlConnection.preparedQuery("INSERT INTO SearchBasis(created) VALUES($1) RETURNING pk")
					.collecting(Collectors.toList())
					.execute(Tuple.of(created.toOffsetDateTime())).onSuccess(result -> {
				Row createLine = result.value().stream().findFirst().orElseGet(() -> null);
				Long pk = createLine.getLong(0);
				SearchBasis o = new SearchBasis();
				o.setPk(pk);
				o.setSiteRequest_(siteRequest);
				promise.complete(o);
			}).onFailure(ex -> {
				LOG.error("createSearchBasis failed. ", ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("createSearchBasis failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public void searchSearchBasisQ(String uri, String apiMethod, SearchList<SearchBasis> searchList, String entityVar, String valueIndexed, String varIndexed) {
		searchList.setQuery(varIndexed + ":" + ("*".equals(valueIndexed) ? valueIndexed : ClientUtils.escapeQueryChars(valueIndexed)));
		if(!"*".equals(entityVar)) {
		}
	}

	public String searchSearchBasisFq(String uri, String apiMethod, SearchList<SearchBasis> searchList, String entityVar, String valueIndexed, String varIndexed) {
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

	public void searchSearchBasisSort(String uri, String apiMethod, SearchList<SearchBasis> searchList, String entityVar, String valueIndexed, String varIndexed) {
		if(varIndexed == null)
			throw new RuntimeException(String.format("\"%s\" is not an indexed entity. ", entityVar));
		searchList.addSort(varIndexed, ORDER.valueOf(valueIndexed));
	}

	public void searchSearchBasisRows(String uri, String apiMethod, SearchList<SearchBasis> searchList, Integer valueRows) {
			searchList.setRows(apiMethod != null ? valueRows : 10);
	}

	public void searchSearchBasisStart(String uri, String apiMethod, SearchList<SearchBasis> searchList, Integer valueStart) {
		searchList.setStart(valueStart);
	}

	public void searchSearchBasisVar(String uri, String apiMethod, SearchList<SearchBasis> searchList, String var, String value) {
		searchList.getSiteRequest_().getRequestVars().put(var, value);
	}

	public void searchSearchBasisUri(String uri, String apiMethod, SearchList<SearchBasis> searchList) {
	}

	public Future<ServiceResponse> varsSearchBasis(SiteRequestEnUS siteRequest) {
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
					LOG.error(String.format("searchSearchBasis failed. "), ex);
					promise.fail(ex);
				}
			});
			promise.complete();
		} catch(Exception ex) {
			LOG.error(String.format("searchSearchBasis failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<SearchList<SearchBasis>> searchSearchBasisList(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod) {
		Promise<SearchList<SearchBasis>> promise = Promise.promise();
		try {
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
									searchSearchBasisQ(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
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
											String entityFq = searchSearchBasisFq(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
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
									searchSearchBasisSort(uri, apiMethod, searchList, entityVar, valueIndexed, varIndexed);
									break;
								case "start":
									valueStart = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
									searchSearchBasisStart(uri, apiMethod, searchList, valueStart);
									break;
								case "rows":
									valueRows = paramObject instanceof Integer ? (Integer)paramObject : Integer.parseInt(paramObject.toString());
									searchSearchBasisRows(uri, apiMethod, searchList, valueRows);
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
									searchSearchBasisVar(uri, apiMethod, searchList, entityVar, valueIndexed);
									break;
							}
						}
						searchSearchBasisUri(uri, apiMethod, searchList);
					}
				} catch(Exception e) {
					ExceptionUtils.rethrow(e);
				}
			});
			if("*:*".equals(searchList.getQuery()) && searchList.getSorts().size() == 0) {
				searchList.addSort("created_indexed_date", ORDER.desc);
			}
			searchSearchBasis2(siteRequest, populate, store, modify, uri, apiMethod, searchList);
			searchList.promiseDeepForClass(siteRequest).onSuccess(a -> {
				promise.complete(searchList);
			}).onFailure(ex -> {
				LOG.error(String.format("searchSearchBasis failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("searchSearchBasis failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}
	public void searchSearchBasis2(SiteRequestEnUS siteRequest, Boolean populate, Boolean store, Boolean modify, String uri, String apiMethod, SearchList<SearchBasis> searchList) {
	}

	public Future<Void> defineSearchBasis(SearchBasis o) {
		Promise<Void> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			SqlConnection sqlConnection = siteRequest.getSqlConnection();
			Long pk = o.getPk();
			sqlConnection.preparedQuery("SELECT * FROM SearchBasis WHERE pk=$1")
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
									LOG.error(String.format("defineSearchBasis failed. "), e);
								}
							}
						}
					}
					promise.complete();
				} catch(Exception ex) {
					LOG.error(String.format("defineSearchBasis failed. "), ex);
					promise.fail(ex);
				}
			}).onFailure(ex -> {
				LOG.error(String.format("defineSearchBasis failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("defineSearchBasis failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<Void> attributeSearchBasis(SearchBasis o) {
		Promise<Void> promise = Promise.promise();
			promise.complete();
		return promise.future();
	}

	public Future<Void> indexSearchBasis(SearchBasis o) {
		Promise<Void> promise = Promise.promise();
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			o.promiseDeepForClass(siteRequest).onSuccess(a -> {
				SolrInputDocument document = new SolrInputDocument();
				o.indexSearchBasis(document);
				String solrHostName = siteRequest.getConfig().getString(ConfigKeys.SOLR_HOST_NAME);
				Integer solrPort = siteRequest.getConfig().getInteger(ConfigKeys.SOLR_PORT);
				String solrCollection = siteRequest.getConfig().getString(ConfigKeys.SOLR_COLLECTION);
				String solrRequestUri = String.format("/solr/%s/update%s", solrCollection, "?commitWithin=10000&overwrite=true&wt=json");
				JsonArray json = new JsonArray().add(new JsonObject(document.toMap(new HashMap<String, Object>())));
				webClient.post(solrPort, solrHostName, solrRequestUri).putHeader("Content-Type", "application/json").expect(ResponsePredicate.SC_OK).sendBuffer(json.toBuffer()).onSuccess(b -> {
					promise.complete();
				}).onFailure(ex -> {
					LOG.error(String.format("indexSearchBasis failed. "), new RuntimeException(ex));
					promise.fail(ex);
				});
			}).onFailure(ex -> {
				LOG.error(String.format("indexSearchBasis failed. "), ex);
				promise.fail(ex);
			});
		} catch(Exception ex) {
			LOG.error(String.format("indexSearchBasis failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Future<Void> refreshSearchBasis(SearchBasis o) {
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
				}

				CompositeFuture.all(futures).onSuccess(b -> {
					JsonObject params = new JsonObject();
					params.put("body", new JsonObject());
					params.put("cookie", new JsonObject());
					params.put("header", new JsonObject());
					params.put("form", new JsonObject());
					params.put("path", new JsonObject());
					params.put("query", new JsonObject().put("q", "*:*").put("fq", new JsonArray().add("pk:" + o.getPk())));
					JsonObject context = new JsonObject().put("params", params).put("user", siteRequest.getJsonPrincipal());
					JsonObject json = new JsonObject().put("context", context);
					eventBus.request("opendatapolicing-enUS-SearchBasis", json, new DeliveryOptions().addHeader("action", "patchSearchBasis")).onSuccess(c -> {
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
			LOG.error(String.format("refreshSearchBasis failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}
}
