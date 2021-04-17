package com.opendatapolicing.enus.trafficstop;

import org.apache.solr.client.solrj.SolrClient;

import com.opendatapolicing.enus.request.SiteRequestEnUS;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.authorization.AuthorizationProvider;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.pgclient.PgPool;

/**
 * Translate: false
 **/
public class TrafficStopEnUSApiServiceImpl extends TrafficStopEnUSGenApiServiceImpl {

	public TrafficStopEnUSApiServiceImpl(EventBus eventBus, JsonObject config, WorkerExecutor workerExecutor, PgPool pgPool, SolrClient solrClient, OAuth2Auth oauth2AuthenticationProvider, AuthorizationProvider authorizationProvider) {
		super(eventBus, config, workerExecutor, pgPool, solrClient, oauth2AuthenticationProvider, authorizationProvider);
	}

	@Override
	public void sqlPATCHTrafficStop(TrafficStop o, Boolean inheritPk,
			Handler<AsyncResult<TrafficStop>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		JsonObject jsonObject = siteRequest.getJsonObject();

		// Because the TrafficStop data does not contain the personKeys, 
		// don't set the personKeys to null when reimporting TrafficStop data. 
		jsonObject.remove("setPersonKeys");

		super.sqlPATCHTrafficStop(o, inheritPk, eventHandler);
	}
}
