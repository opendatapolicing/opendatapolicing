package com.opendatapolicing.enus.trafficperson;

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
public class TrafficPersonEnUSApiServiceImpl extends TrafficPersonEnUSGenApiServiceImpl {

	public TrafficPersonEnUSApiServiceImpl(Semaphore semaphore, EventBus eventBus, JsonObject config, WorkerExecutor workerExecutor, PgPool pgPool, WebClient webClient, OAuth2Auth oauth2AuthenticationProvider, AuthorizationProvider authorizationProvider) {
		super(semaphore, eventBus, config, workerExecutor, pgPool, webClient, oauth2AuthenticationProvider, authorizationProvider);
	}

	@Override
	public Future<TrafficPerson> sqlPATCHTrafficPerson(TrafficPerson o, Boolean inheritPk) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		JsonObject jsonObject = siteRequest.getJsonObject();

		// Because the TrafficPerson data does not contain the searchKeys, 
		// don't set the searchKeys to null when reimporting TrafficPerson data. 
		jsonObject.remove("setTrafficSearchKeys");

		return super.sqlPATCHTrafficPerson(o, inheritPk);
	}
}
