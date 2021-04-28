package com.opendatapolicing.enus.user;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;

import com.opendatapolicing.enus.request.SiteRequestEnUS;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.authorization.AuthorizationProvider;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.web.api.service.ServiceResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.pgclient.PgPool;

/**
 * Translate: false
 **/
public class SiteUserEnUSApiServiceImpl extends SiteUserEnUSGenApiServiceImpl {

	public SiteUserEnUSApiServiceImpl(EventBus eventBus, JsonObject config, WorkerExecutor workerExecutor, PgPool pgPool, WebClient webClient, OAuth2Auth oauth2AuthenticationProvider, AuthorizationProvider authorizationProvider) {
		super(eventBus, config, workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider);
	}

	@Override
	public void sqlPOSTSiteUser(SiteUser o, Boolean inheritPk, Handler<AsyncResult<ServiceResponse>> eventHandler) {
		SiteRequestEnUS siteRequest_ = o.getSiteRequest_();
		JsonObject jsonObject = siteRequest_.getJsonObject();
		if(StringUtils.equals(o.getUserId(), siteRequest_.getUserId())) {
			jsonObject.put("userFirstName", siteRequest_.getUserFirstName());
			jsonObject.put("userLastName", siteRequest_.getUserLastName());
			jsonObject.put("userFullName", siteRequest_.getUserFullName());
			jsonObject.put("userEmail", siteRequest_.getUserEmail());
			jsonObject.put("userId", siteRequest_.getUserId());
			jsonObject.put("userName", siteRequest_.getUserName());
		}
		super.sqlPOSTSiteUser(o, inheritPk, eventHandler);
	}

	@Override
	public void sqlPATCHSiteUser(SiteUser o, Boolean inheritPk, Handler<AsyncResult<SiteUser>> eventHandler) {
		SiteRequestEnUS siteRequest_ = o.getSiteRequest_();
		JsonObject jsonObject = siteRequest_.getJsonObject();
		if(StringUtils.equals(o.getUserId(), siteRequest_.getUserId())) {
			jsonObject.put("setUserFirstName", siteRequest_.getUserFirstName());
			jsonObject.put("setUserLastName", siteRequest_.getUserLastName());
			jsonObject.put("setUserFullName", siteRequest_.getUserFullName());
			jsonObject.put("setUserEmail", siteRequest_.getUserEmail());
			jsonObject.put("setUserId", siteRequest_.getUserId());
			jsonObject.put("setUserName", siteRequest_.getUserName());
		}
		super.sqlPATCHSiteUser(o, inheritPk, eventHandler);
	}
}
