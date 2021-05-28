package com.opendatapolicing.enus.trafficstop;

import java.util.concurrent.Semaphore;

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
public class TrafficStopEnUSApiServiceImpl extends TrafficStopEnUSGenApiServiceImpl {

	public TrafficStopEnUSApiServiceImpl(EventBus eventBus, JsonObject config, WorkerExecutor workerExecutor, PgPool pgPool, WebClient webClient, OAuth2Auth oauth2AuthenticationProvider, AuthorizationProvider authorizationProvider) {
		super(eventBus, config, workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider);
	}

	@Override
	public Future<TrafficStop> sqlPATCHTrafficStop(TrafficStop o, Boolean inheritPk) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		JsonObject jsonObject = siteRequest.getJsonObject();

		// Because the TrafficStop data does not contain the personKeys, 
		// don't set the personKeys to null when reimporting TrafficStop data. 
		jsonObject.remove("setPersonKeys");

		return super.sqlPATCHTrafficStop(o, inheritPk);
	}
}
