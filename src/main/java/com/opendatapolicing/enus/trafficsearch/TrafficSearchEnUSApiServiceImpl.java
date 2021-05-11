package com.opendatapolicing.enus.trafficsearch;

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
public class TrafficSearchEnUSApiServiceImpl extends TrafficSearchEnUSGenApiServiceImpl {

	public TrafficSearchEnUSApiServiceImpl(Semaphore semaphore, EventBus eventBus, JsonObject config, WorkerExecutor workerExecutor, PgPool pgPool, WebClient webClient, OAuth2Auth oauth2AuthenticationProvider, AuthorizationProvider authorizationProvider) {
		super(semaphore, eventBus, config, workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider);
	}

	@Override
	public Future<TrafficSearch> sqlPATCHTrafficSearch(TrafficSearch o, Boolean inheritPk) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		JsonObject jsonObject = siteRequest.getJsonObject();

		// Because the TrafficSearch data does not contain the contrabandKeys, 
		// don't set the contrabandKeys to null when reimporting TrafficSearch data. 
		jsonObject.remove("setContrabandKeys");

		// Because the TrafficSearch data does not contain the searchBasisKeys, 
		// don't set the searchBasisKeys to null when reimporting TrafficSearch data. 
		jsonObject.remove("setSearchBasisKeys");

		return super.sqlPATCHTrafficSearch(o, inheritPk);
	}
}
