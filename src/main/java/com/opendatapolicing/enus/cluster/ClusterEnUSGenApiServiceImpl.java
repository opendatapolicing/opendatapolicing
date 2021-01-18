package com.opendatapolicing.enus.cluster;

import com.opendatapolicing.enus.config.SiteConfig;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.opendatapolicing.enus.context.SiteContextEnUS;
import com.opendatapolicing.enus.request.api.ApiRequest;
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
import io.vertx.core.http.CaseInsensitiveHeaders;
import io.vertx.core.AsyncResult;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.api.OperationResponse;
import io.vertx.core.CompositeFuture;
import org.apache.http.client.utils.URLEncodedUtils;
import java.nio.charset.Charset;
import org.apache.http.NameValuePair;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.ext.auth.oauth2.KeycloakHelper;
import java.util.Optional;
import java.util.stream.Stream;
import java.net.URLDecoder;
import java.time.ZonedDateTime;
import org.apache.solr.common.util.SimpleOrderedMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import com.opendatapolicing.enus.search.SearchList;


/**
 * Translate: false
 **/
public class ClusterEnUSGenApiServiceImpl implements ClusterEnUSGenApiService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(ClusterEnUSGenApiServiceImpl.class);

	protected static final String SERVICE_ADDRESS = "ClusterEnUSApiServiceImpl";

	protected SiteContextEnUS siteContext;

	public ClusterEnUSGenApiServiceImpl(SiteContextEnUS siteContext) {
		this.siteContext = siteContext;
	}

	// POST //

	@Override
	public void postCluster(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForCluster(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/cluster");
		siteRequest.setRequestMethod("POST");
		try {
			LOGGER.info(String.format("postCluster started. "));
			{
				userCluster(siteRequest, b -> {
					if(b.succeeded()) {
						ApiRequest apiRequest = new ApiRequest();
						apiRequest.setRows(1);
						apiRequest.setNumFound(1L);
						apiRequest.setNumPATCH(0L);
						apiRequest.initDeepApiRequest(siteRequest);
						siteRequest.setApiRequest_(apiRequest);
						siteRequest.getVertx().eventBus().publish("websocketCluster", JsonObject.mapFrom(apiRequest).toString());
						postClusterFuture(siteRequest, false, c -> {
							if(c.succeeded()) {
								Cluster cluster = c.result();
								apiRequest.setPk(cluster.getPk());
								postClusterResponse(cluster, d -> {
										if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("postCluster succeeded. "));
									} else {
										LOGGER.error(String.format("postCluster failed. ", d.cause()));
										errorCluster(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("postCluster failed. ", c.cause()));
								errorCluster(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("postCluster failed. ", b.cause()));
						errorCluster(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("postCluster failed. ", ex));
			errorCluster(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public Future<Cluster> postClusterFuture(SiteRequestEnUS siteRequest, Boolean inheritPk, Handler<AsyncResult<Cluster>> eventHandler) {
		Promise<Cluster> promise = Promise.promise();
		try {
			sqlConnectionCluster(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionCluster(siteRequest, b -> {
						if(b.succeeded()) {
							createCluster(siteRequest, c -> {
								if(c.succeeded()) {
									Cluster cluster = c.result();
									sqlPOSTCluster(cluster, inheritPk, d -> {
										if(d.succeeded()) {
											defineIndexCluster(cluster, e -> {
												if(e.succeeded()) {
													ApiRequest apiRequest = siteRequest.getApiRequest_();
													if(apiRequest != null) {
														apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
														cluster.apiRequestCluster();
														siteRequest.getVertx().eventBus().publish("websocketCluster", JsonObject.mapFrom(apiRequest).toString());
													}
													eventHandler.handle(Future.succeededFuture(cluster));
													promise.complete(cluster);
												} else {
													LOGGER.error(String.format("postClusterFuture failed. ", e.cause()));
													eventHandler.handle(Future.failedFuture(e.cause()));
												}
											});
										} else {
											LOGGER.error(String.format("postClusterFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("postClusterFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("postClusterFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("postClusterFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("postClusterFuture failed. ", e));
			errorCluster(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPOSTCluster(Cluster o, Boolean inheritPk, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Transaction tx = siteRequest.getTx();
			Long  = o.get();
			JsonObject jsonObject = siteRequest.getJsonObject();
			List<Future> futures = new ArrayList<>();

			if(siteRequest.getSessionId() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContextEnUS.SQL_setD
				, Tuple.of(, "sessionId", siteRequest.getSessionId())
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
					tx.preparedQuery(SiteContextEnUS.SQL_setD
				, Tuple.of(, "userId", siteRequest.getUserId())
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
					tx.preparedQuery(SiteContextEnUS.SQL_setD
				, Tuple.of(, "userKey", siteRequest.getUserKey().toString())
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
					}
				}
			}
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture());
				} else {
					LOGGER.error(String.format("sqlPOSTCluster failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPOSTCluster failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void postClusterResponse(Cluster cluster, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = cluster.getSiteRequest_();
		try {
			response200POSTCluster(cluster, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("postClusterResponse failed. ", a.cause()));
					errorCluster(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("postClusterResponse failed. ", ex));
			errorCluster(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200POSTCluster(Cluster o, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			JsonObject json = JsonObject.mapFrom(o);
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200POSTCluster failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PUT //

	@Override
	public void putCluster(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForCluster(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/cluster");
		siteRequest.setRequestMethod("PUT");
		try {
			LOGGER.info(String.format("putCluster started. "));
			{
				userCluster(siteRequest, b -> {
					if(b.succeeded()) {
						putClusterResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								LOGGER.info(String.format("putCluster succeeded. "));
							} else {
								LOGGER.error(String.format("putCluster failed. ", c.cause()));
								errorCluster(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("putCluster failed. ", b.cause()));
						errorCluster(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("putCluster failed. ", ex));
			errorCluster(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void putClusterResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PUTCluster(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("putClusterResponse failed. ", a.cause()));
					errorCluster(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("putClusterResponse failed. ", ex));
			errorCluster(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PUTCluster(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PUTCluster failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// PATCH //

	@Override
	public void patchCluster(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForCluster(siteContext, operationRequest, body);
		siteRequest.setRequestUri("/api/cluster");
		siteRequest.setRequestMethod("PATCH");
		try {
			LOGGER.info(String.format("patchCluster started. "));
			{
				userCluster(siteRequest, b -> {
					if(b.succeeded()) {
						patchClusterResponse(siteRequest, c -> {
							if(c.succeeded()) {
								eventHandler.handle(Future.succeededFuture(c.result()));
								WorkerExecutor workerExecutor = siteContext.getWorkerExecutor();
								workerExecutor.executeBlocking(
									blockingCodeHandler -> {
										try {
											aSearchCluster(siteRequest, false, true, "/api/cluster", "PATCH", d -> {
												if(d.succeeded()) {
													SearchList<Cluster> listCluster = d.result();

													if(listCluster.getQueryResponse().getResults().getNumFound() > 1) {
														List<String> roles2 = Arrays.asList("SiteAdmin");
														if(
																!CollectionUtils.containsAny(siteRequest.getUserResourceRoles(), roles2)
																&& !CollectionUtils.containsAny(siteRequest.getUserRealmRoles(), roles2)
																) {
															String message = String.format("roles required: " + String.join(", ", roles2));
															LOGGER.error(message);
															errorCluster(siteRequest, eventHandler, Future.failedFuture(message));
														}
													} else {

														ApiRequest apiRequest = new ApiRequest();
														apiRequest.setRows(listCluster.getRows());
														apiRequest.setNumFound(listCluster.getQueryResponse().getResults().getNumFound());
														apiRequest.setNumPATCH(0L);
														apiRequest.initDeepApiRequest(siteRequest);
														siteRequest.setApiRequest_(apiRequest);
														siteRequest.getVertx().eventBus().publish("websocketCluster", JsonObject.mapFrom(apiRequest).toString());
														String dt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(ZonedDateTime.now().toInstant(), ZoneId.of("UTC")).minusNanos(1000));

														try {
															listPATCHCluster(apiRequest, listCluster, dt, e -> {
																if(e.succeeded()) {
																	patchClusterResponse(siteRequest, f -> {
																		if(f.succeeded()) {
																			LOGGER.info(String.format("patchCluster succeeded. "));
																			blockingCodeHandler.handle(Future.succeededFuture(f.result()));
																		} else {
																			LOGGER.error(String.format("patchCluster failed. ", f.cause()));
																			errorCluster(siteRequest, null, f);
																		}
																	});
																} else {
																	LOGGER.error(String.format("patchCluster failed. ", e.cause()));
																	errorCluster(siteRequest, null, e);
																}
															});
														} catch(Exception ex) {
															LOGGER.error(String.format("patchCluster failed. ", ex));
															errorCluster(siteRequest, null, Future.failedFuture(ex));
														}
													}
										} else {
													LOGGER.error(String.format("patchCluster failed. ", d.cause()));
													errorCluster(siteRequest, null, d);
												}
											});
										} catch(Exception ex) {
											LOGGER.error(String.format("patchCluster failed. ", ex));
											errorCluster(siteRequest, null, Future.failedFuture(ex));
										}
									}, resultHandler -> {
									}
								);
							} else {
								LOGGER.error(String.format("patchCluster failed. ", c.cause()));
								errorCluster(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("patchCluster failed. ", b.cause()));
						errorCluster(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("patchCluster failed. ", ex));
			errorCluster(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void listPATCHCluster(ApiRequest apiRequest, SearchList<Cluster> listCluster, String dt, Handler<AsyncResult<OperationResponse>> eventHandler) {
		List<Future> futures = new ArrayList<>();
		SiteRequestEnUS siteRequest = listCluster.getSiteRequest_();
		listCluster.getList().forEach(o -> {
			SiteRequestEnUS siteRequest2 = generateSiteRequestEnUSForCluster(siteContext, siteRequest.getOperationRequest(), siteRequest.getJsonObject());
			siteRequest2.setApiRequest_(siteRequest.getApiRequest_());
			o.setSiteRequest_(siteRequest2);
			futures.add(
				patchClusterFuture(o, false, a -> {
					if(a.succeeded()) {
					} else {
						errorCluster(siteRequest2, eventHandler, a);
					}
				})
			);
		});
		CompositeFuture.all(futures).setHandler( a -> {
			if(a.succeeded()) {
				if(listCluster.next(dt)) {
					listPATCHCluster(apiRequest, listCluster, dt, eventHandler);
				} else {
					response200PATCHCluster(siteRequest, eventHandler);
				}
			} else {
				LOGGER.error(String.format("listPATCHCluster failed. ", a.cause()));
				errorCluster(listCluster.getSiteRequest_(), eventHandler, a);
			}
		});
	}

	public Future<Cluster> patchClusterFuture(Cluster o, Boolean inheritPk, Handler<AsyncResult<Cluster>> eventHandler) {
		Promise<Cluster> promise = Promise.promise();
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		try {
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			if(apiRequest != null && apiRequest.getNumFound() == 1L) {
				apiRequest.setOriginal(o);
				apiRequest.setPk(o.getPk());
			}
			sqlConnectionCluster(siteRequest, a -> {
				if(a.succeeded()) {
					sqlTransactionCluster(siteRequest, b -> {
						if(b.succeeded()) {
							sqlPATCHCluster(o, inheritPk, c -> {
								if(c.succeeded()) {
									Cluster cluster = c.result();
									defineIndexCluster(cluster, d -> {
										if(d.succeeded()) {
											if(apiRequest != null) {
												apiRequest.setNumPATCH(apiRequest.getNumPATCH() + 1);
												if(apiRequest.getNumFound() == 1L) {
													cluster.apiRequestCluster();
												}
												siteRequest.getVertx().eventBus().publish("websocketCluster", JsonObject.mapFrom(apiRequest).toString());
											}
											eventHandler.handle(Future.succeededFuture(cluster));
											promise.complete(cluster);
										} else {
											LOGGER.error(String.format("patchClusterFuture failed. ", d.cause()));
											eventHandler.handle(Future.failedFuture(d.cause()));
										}
									});
								} else {
									LOGGER.error(String.format("patchClusterFuture failed. ", c.cause()));
									eventHandler.handle(Future.failedFuture(c.cause()));
								}
							});
						} else {
							LOGGER.error(String.format("patchClusterFuture failed. ", b.cause()));
							eventHandler.handle(Future.failedFuture(b.cause()));
						}
					});
				} else {
					LOGGER.error(String.format("patchClusterFuture failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("patchClusterFuture failed. ", e));
			errorCluster(siteRequest, null, Future.failedFuture(e));
		}
		return promise.future();
	}

	public void sqlPATCHCluster(Cluster o, Boolean inheritPk, Handler<AsyncResult<Cluster>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = o.getSiteRequest_();
			ApiRequest apiRequest = siteRequest.getApiRequest_();
			List<Long> pks = Optional.ofNullable(apiRequest).map(r -> r.getPks()).orElse(new ArrayList<>());
			List<String> classes = Optional.ofNullable(apiRequest).map(r -> r.getClasses()).orElse(new ArrayList<>());
			Transaction tx = siteRequest.getTx();
			Long  = o.get();
			JsonObject jsonObject = siteRequest.getJsonObject();
			Set<String> methodNames = jsonObject.fieldNames();
			Cluster o2 = new Cluster();
			List<Future> futures = new ArrayList<>();

			if(o.getUserId() == null && siteRequest.getUserId() != null) {
				futures.add(Future.future(a -> {
					tx.preparedQuery(SiteContextEnUS.SQL_setD
							, Tuple.of(, "userId", siteRequest.getUserId())
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
					tx.preparedQuery(SiteContextEnUS.SQL_setD
				, Tuple.of(, "userKey", siteRequest.getUserKey().toString())
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
				}
			}
			CompositeFuture.all(futures).setHandler( a -> {
				if(a.succeeded()) {
					Cluster o3 = new Cluster();
					o3.setSiteRequest_(o.getSiteRequest_());
					o3.set();
					eventHandler.handle(Future.succeededFuture(o3));
				} else {
					LOGGER.error(String.format("sqlPATCHCluster failed. ", a.cause()));
					eventHandler.handle(Future.failedFuture(a.cause()));
				}
			});
		} catch(Exception e) {
			LOGGER.error(String.format("sqlPATCHCluster failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	public void patchClusterResponse(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			response200PATCHCluster(siteRequest, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("patchClusterResponse failed. ", a.cause()));
					errorCluster(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("patchClusterResponse failed. ", ex));
			errorCluster(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200PATCHCluster(SiteRequestEnUS siteRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			JsonObject json = new JsonObject();
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200PATCHCluster failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// GET //

	@Override
	public void getCluster(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForCluster(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/cluster/{id}");
		siteRequest.setRequestMethod("GET");
		try {
			{
				userCluster(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchCluster(siteRequest, false, true, "/api/cluster/{id}", "GET", c -> {
							if(c.succeeded()) {
								SearchList<Cluster> listCluster = c.result();
								getClusterResponse(listCluster, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("getCluster succeeded. "));
									} else {
										LOGGER.error(String.format("getCluster failed. ", d.cause()));
										errorCluster(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("getCluster failed. ", c.cause()));
								errorCluster(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("getCluster failed. ", b.cause()));
						errorCluster(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("getCluster failed. ", ex));
			errorCluster(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void getClusterResponse(SearchList<Cluster> listCluster, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listCluster.getSiteRequest_();
		try {
			response200GETCluster(listCluster, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("getClusterResponse failed. ", a.cause()));
					errorCluster(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("getClusterResponse failed. ", ex));
			errorCluster(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200GETCluster(SearchList<Cluster> listCluster, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listCluster.getSiteRequest_();
			SolrDocumentList solrDocuments = listCluster.getSolrDocumentList();

			JsonObject json = JsonObject.mapFrom(listCluster.getList().stream().findFirst().orElse(null));
			eventHandler.handle(Future.succeededFuture(OperationResponse.completedWithJson(Buffer.buffer(Optional.ofNullable(json).orElse(new JsonObject()).encodePrettily()))));
		} catch(Exception e) {
			LOGGER.error(String.format("response200GETCluster failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	// Search //

	@Override
	public void searchCluster(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = generateSiteRequestEnUSForCluster(siteContext, operationRequest);
		siteRequest.setRequestUri("/api/cluster");
		siteRequest.setRequestMethod("Search");
		try {
			{
				userCluster(siteRequest, b -> {
					if(b.succeeded()) {
						aSearchCluster(siteRequest, false, true, "/api/cluster", "Search", c -> {
							if(c.succeeded()) {
								SearchList<Cluster> listCluster = c.result();
								searchClusterResponse(listCluster, d -> {
									if(d.succeeded()) {
										eventHandler.handle(Future.succeededFuture(d.result()));
										LOGGER.info(String.format("searchCluster succeeded. "));
									} else {
										LOGGER.error(String.format("searchCluster failed. ", d.cause()));
										errorCluster(siteRequest, eventHandler, d);
									}
								});
							} else {
								LOGGER.error(String.format("searchCluster failed. ", c.cause()));
								errorCluster(siteRequest, eventHandler, c);
							}
						});
					} else {
						LOGGER.error(String.format("searchCluster failed. ", b.cause()));
						errorCluster(siteRequest, eventHandler, b);
					}
				});
			}
		} catch(Exception ex) {
			LOGGER.error(String.format("searchCluster failed. ", ex));
			errorCluster(siteRequest, eventHandler, Future.failedFuture(ex));
		}
	}


	public void searchClusterResponse(SearchList<Cluster> listCluster, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest = listCluster.getSiteRequest_();
		try {
			response200SearchCluster(listCluster, a -> {
				if(a.succeeded()) {
					eventHandler.handle(Future.succeededFuture(a.result()));
				} else {
					LOGGER.error(String.format("searchClusterResponse failed. ", a.cause()));
					errorCluster(siteRequest, eventHandler, a);
				}
			});
		} catch(Exception ex) {
			LOGGER.error(String.format("searchClusterResponse failed. ", ex));
			errorCluster(siteRequest, null, Future.failedFuture(ex));
		}
	}
	public void response200SearchCluster(SearchList<Cluster> listCluster, Handler<AsyncResult<OperationResponse>> eventHandler) {
		try {
			SiteRequestEnUS siteRequest = listCluster.getSiteRequest_();
			QueryResponse responseSearch = listCluster.getQueryResponse();
			SolrDocumentList solrDocuments = listCluster.getSolrDocumentList();
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
			listCluster.getList().stream().forEach(o -> {
				JsonObject json2 = JsonObject.mapFrom(o);
				List<String> fls = listCluster.getFields();
				if(fls.size() > 0) {
					Set<String> fieldNames = new HashSet<String>();
					fieldNames.addAll(json2.fieldNames());
					if(fls.size() == 1 && fls.stream().findFirst().orElse(null).equals("saves")) {
						fieldNames.removeAll(Optional.ofNullable(json2.getJsonArray("saves")).orElse(new JsonArray()).stream().map(s -> s.toString()).collect(Collectors.toList()));
						fieldNames.remove("");
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
			LOGGER.error(String.format("response200SearchCluster failed. ", e));
			eventHandler.handle(Future.failedFuture(e));
		}
	}

	ClusterGen(String s) {
		if(s != null) {
			s = Normalizer.normalize(s, Normalizer.Form.NFD);
			s = StringUtils.lowerCase(s);
			s = StringUtils.trim(s);
			s = StringUtils.replacePattern(s, "\\s{1,}", "-");
			s = StringUtils.replacePattern(s, "[^\\w-]", "");
			s = StringUtils.replacePattern(s, "-{2,}", "-");
		}

		return s;
	}

	ClusterGen(String attributeName, Object...objects) {
		AllWriter w = siteRequest_.getW();
		w.s(" ");
		w.s(attributeName);
		w.s("=\"");
		for(Object object : objects) {
			if(object != null) {
				String s = object.toString();
				w.s(UtilXml.escapeInQuotes(s));
			}
		}
		w.s("\"");
		
		return this;
	}

	ClusterGen(Object...objects) {
		AllWriter w = siteRequest_.getW();
		for(Object object : objects) {
			if(object != null) {
				String s = object.toString();
				w.s(UtilXml.escape(s));
			}
		}
		
		return this;
	}

	ClusterGen(int numberTabs, Object...objects) {
		for(int i = 0; i < numberTabs; i++)
			sx("  ");
		sx(objects);
		sx("\n");
		return this;
	}

	ClusterGen() {
		AllWriter w = siteRequest_.getW();
		w.s("/>");
		siteRequest_.getXmlStack().pop();
		
		return this;
	}

	ClusterGen(String localName) {
		AllWriter w = siteRequest_.getW();
		String localNameParent = siteRequest_.getXmlStack().peek();
		boolean eNoWrap = localNameParent == null || PageLayout.HTML_ELEMENTS_NO_WRAP.contains(localName);
	
		siteRequest_.getXmlStack().pop();
		String tabs = String.join("", Collections.nCopies(siteRequest_.getXmlStack().size(), "  "));
	
		if(!eNoWrap || localNameParent == null)
			w.l();
		if(!eNoWrap && !tabs.isEmpty())
			w.s(tabs);
		w.s("</");
		w.s(loc