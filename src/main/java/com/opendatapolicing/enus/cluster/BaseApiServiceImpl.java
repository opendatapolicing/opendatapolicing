package com.opendatapolicing.enus.cluster;

import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opendatapolicing.enus.config.ConfigKeys;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.opendatapolicing.enus.request.api.ApiRequest;
import com.opendatapolicing.enus.search.SearchList;
import com.opendatapolicing.enus.user.SiteUser;
import com.opendatapolicing.enus.user.SiteUserEnUSApiServiceImpl;
import com.opendatapolicing.enus.vertx.MailVerticle;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.MultiMap;
import io.vertx.core.Promise;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.authorization.AuthorizationProvider;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.web.api.service.ServiceRequest;
import io.vertx.ext.web.api.service.ServiceResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.pgclient.PgPool;


/**
 * Keyword: classSimpleNameBaseApiServiceImpl
 **/
public class BaseApiServiceImpl {

	protected static final Logger LOG = LoggerFactory.getLogger(BaseApiServiceImpl.class);

	protected EventBus eventBus;

	protected JsonObject config;

	protected WorkerExecutor workerExecutor;

	protected PgPool pgPool;

	protected WebClient webClient;

	protected OAuth2Auth oauth2AuthenticationProvider;

	protected AuthorizationProvider authorizationProvider;

	public BaseApiServiceImpl(EventBus eventBus, JsonObject config, WorkerExecutor workerExecutor, PgPool pgPool, WebClient webClient, OAuth2Auth oauth2AuthenticationProvider, AuthorizationProvider authorizationProvider) {
		this.eventBus = eventBus;
		this.config = config;
		this.workerExecutor = workerExecutor;
		this.pgPool = pgPool;
		this.webClient = webClient;
		this.oauth2AuthenticationProvider = oauth2AuthenticationProvider;
		this.authorizationProvider = authorizationProvider;
	}

	// General //

	public void error(SiteRequestEnUS siteRequest, Handler<AsyncResult<ServiceResponse>> eventHandler, Throwable ex) {
		JsonObject json = new JsonObject();
		JsonObject jsonError = new JsonObject();
		json.put("error", jsonError);
		jsonError.put("message", Optional.ofNullable(ex).map(Throwable::getMessage).orElse(null));
		if(siteRequest != null) {
			jsonError.put("userName", siteRequest.getUserName());
			jsonError.put("userFullName", siteRequest.getUserFullName());
			jsonError.put("requestUri", siteRequest.getRequestUri());
			jsonError.put("requestMethod", siteRequest.getRequestMethod());
			jsonError.put("params", Optional.ofNullable(siteRequest.getServiceRequest()).map(o -> o.getParams()).orElse(null));
		}
		LOG.error("error: ", ex);
		ServiceResponse responseOperation = new ServiceResponse(400, "BAD REQUEST", 
				Buffer.buffer().appendString(json.encodePrettily())
				, MultiMap.caseInsensitiveMultiMap().add("Content-Type", "application/json")
		);
		if(siteRequest != null) {
			DeliveryOptions options = new DeliveryOptions();
			options.addHeader(MailVerticle.MAIL_HEADER_SUBJECT, String.format("%s %s", config.getString(ConfigKeys.SITE_BASE_URL), Optional.ofNullable(ex).map(Throwable::getMessage).orElse(null)));
			eventBus.publish(MailVerticle.MAIL_EVENTBUS_ADDRESS, String.format("%s\n\n%s", json.encodePrettily(), ExceptionUtils.getStackTrace(ex)));
			if(eventHandler != null)
				eventHandler.handle(Future.succeededFuture(responseOperation));
		} else {
			if(eventHandler != null)
				eventHandler.handle(Future.succeededFuture(responseOperation));
		}
	}

	public SiteRequestEnUS generateSiteRequestEnUS(User user, ServiceRequest serviceRequest) {
		return generateSiteRequestEnUS(user, serviceRequest, null);
	}

	public SiteRequestEnUS generateSiteRequestEnUS(User user, ServiceRequest serviceRequest, JsonObject body) {
		SiteRequestEnUS siteRequest = new SiteRequestEnUS();
		siteRequest.setWebClient(webClient);
		siteRequest.setJsonObject(body);
		siteRequest.setUser(user);
		siteRequest.setConfig(config);
		siteRequest.setServiceRequest(serviceRequest);
		siteRequest.initDeepSiteRequestEnUS(siteRequest);

		return siteRequest;
	}

	public Future<SiteRequestEnUS> user(ServiceRequest serviceRequest) {
		Promise<SiteRequestEnUS> promise = Promise.promise();
		try {
			JsonObject userJson = serviceRequest.getUser();
			if(userJson == null) {
				SiteRequestEnUS siteRequest = generateSiteRequestEnUS(null, serviceRequest);
				promise.complete(siteRequest);
			} else {
				User token = User.create(userJson);
				oauth2AuthenticationProvider.authenticate(token.principal()).onSuccess(user -> {
					authorizationProvider.getAuthorizations(user).onSuccess(b -> {
						try {
							JsonObject userAttributes = user.attributes();
							JsonObject accessToken = userAttributes.getJsonObject("accessToken");
							String userId = userAttributes.getString("sub");
							SiteRequestEnUS siteRequest = generateSiteRequestEnUS(user, serviceRequest);
							SearchList<SiteUser> searchList = new SearchList<SiteUser>();
							searchList.setQuery("*:*");
							searchList.setStore(true);
							searchList.setC(SiteUser.class);
							searchList.addFilterQuery("userId_indexed_string:" + ClientUtils.escapeQueryChars(userId));
							searchList.promiseDeepSearchList(siteRequest).onSuccess(c -> {
								SiteUser siteUser1 = searchList.getList().stream().findFirst().orElse(null);
								SiteUserEnUSApiServiceImpl userService = new SiteUserEnUSApiServiceImpl(eventBus, config, workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider);

								if(siteUser1 == null) {
									JsonObject jsonObject = new JsonObject();
									jsonObject.put("userName", accessToken.getString("preferred_username"));
									jsonObject.put("userFirstName", accessToken.getString("given_name"));
									jsonObject.put("userLastName", accessToken.getString("family_name"));
									jsonObject.put("userCompleteName", accessToken.getString("name"));
									jsonObject.put("userId", accessToken.getString("sub"));
									jsonObject.put("userEmail", accessToken.getString("email"));
									userDefine(siteRequest, jsonObject, false);

									SiteRequestEnUS siteRequest2 = siteRequest.copy();
									siteRequest2.setJsonObject(jsonObject);
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
										promise.complete(siteRequest);
									}).onFailure(ex -> {
										error(siteRequest, null, ex);
									});
								} else {
									JsonObject jsonObject = new JsonObject();
									jsonObject.put("setUserName", accessToken.getString("preferred_username"));
									jsonObject.put("setUserFirstName", accessToken.getString("given_name"));
									jsonObject.put("setUserLastName", accessToken.getString("family_name"));
									jsonObject.put("setUserCompleteName", accessToken.getString("name"));
									jsonObject.put("setUserId", accessToken.getString("sub"));
									jsonObject.put("setUserEmail", accessToken.getString("email"));
									Boolean define = userDefine(siteRequest, jsonObject, true);
									if(define) {

										SiteRequestEnUS siteRequest2 = siteRequest.copy();
										siteRequest2.setJsonObject(jsonObject);
										siteRequest2.initDeepSiteRequestEnUS(siteRequest);
										siteUser1.setSiteRequest_(siteRequest2);

										ApiRequest apiRequest = new ApiRequest();
										apiRequest.setRows(1);
										apiRequest.setNumFound(1L);
										apiRequest.setNumPATCH(0L);
										apiRequest.initDeepApiRequest(siteRequest2);
										siteRequest2.setApiRequest_(apiRequest);

										userService.patchSiteUserFuture(siteUser1, false).onSuccess(siteUser2 -> {
											siteRequest.setSiteUser(siteUser2);
											siteRequest.setUserName(siteUser2.getUserName());
											siteRequest.setUserFirstName(siteUser2.getUserFirstName());
											siteRequest.setUserLastName(siteUser2.getUserLastName());
											siteRequest.setUserKey(siteUser2.getPk());
											promise.complete(siteRequest);
										}).onFailure(ex -> {
											promise.fail(ex);
										});
									} else {
										siteRequest.setSiteUser(siteUser1);
										siteRequest.setUserName(siteUser1.getUserName());
										siteRequest.setUserFirstName(siteUser1.getUserFirstName());
										siteRequest.setUserLastName(siteUser1.getUserLastName());
										siteRequest.setUserKey(siteUser1.getPk());
										promise.complete(siteRequest);
									}
								}
							}).onFailure(ex -> {
								LOG.error(String.format("user failed. "), ex);
								promise.fail(ex);
							});
						} catch(Exception ex) {
							LOG.error(String.format("user failed. "), ex);
							promise.fail(ex);
						}
					}).onFailure(ex -> {
						LOG.error(String.format("user failed. ", ex));
						promise.fail(ex);
					});
				}).onFailure(ex -> {
					oauth2AuthenticationProvider.refresh(token).onSuccess(user -> {
						serviceRequest.setUser(user.principal());
						user(serviceRequest).onSuccess(siteRequest -> {
							promise.complete(siteRequest);
						}).onFailure(ex2 -> {
							LOG.error(String.format("user failed. ", ex2));
							promise.fail(ex2);
						});
					}).onFailure(ex2 -> {
						LOG.error(String.format("user failed. ", ex2));
						promise.fail(ex2);
					});
				});
			}
		} catch(Exception ex) {
			LOG.error(String.format("user failed. "), ex);
			promise.fail(ex);
		}
		return promise.future();
	}

	public Boolean userDefine(SiteRequestEnUS siteRequest, JsonObject jsonObject, Boolean patch) {
		if(patch) {
			return false;
		} else {
			return false;
		}
	}
}
