package com.opendatapolicing.enus.user;

import java.util.concurrent.Semaphore;

import org.apache.commons.lang3.StringUtils;

import com.opendatapolicing.enus.request.SiteRequestEnUS;

import io.vertx.core.Future;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.authorization.AuthorizationProvider;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.web.client.WebClient;
import io.vertx.pgclient.PgPool;

/**
 * Translate: false
 **/
public class SiteUserEnUSApiServiceImpl extends SiteUserEnUSGenApiServiceImpl {

	public SiteUserEnUSApiServiceImpl(Semaphore semaphore, EventBus eventBus, JsonObject config, WorkerExecutor workerExecutor, PgPool pgPool, WebClient webClient, OAuth2Auth oauth2AuthenticationProvider, AuthorizationProvider authorizationProvider) {
		super(semaphore, eventBus, config, workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider);
	}


	@Override
	public Future<Void> sqlPOSTSiteUser(SiteUser o, Boolean inheritPk) {
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
		return super.sqlPOSTSiteUser(o, inheritPk);
	}

	@Override
	public Future<SiteUser> sqlPATCHSiteUser(SiteUser o, Boolean inheritPk) {
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
		return super.sqlPATCHSiteUser(o, inheritPk);
	}
}
