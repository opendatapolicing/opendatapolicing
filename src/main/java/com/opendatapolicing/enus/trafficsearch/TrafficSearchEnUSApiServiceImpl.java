package com.opendatapolicing.enus.trafficsearch;

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
public class TrafficSearchEnUSApiServiceImpl extends TrafficSearchEnUSGenApiServiceImpl {

	public TrafficSearchEnUSApiServiceImpl(EventBus eventBus, JsonObject config, WorkerExecutor workerExecutor, PgPool pgPool, SolrClient solrClient, OAuth2Auth oauth2AuthenticationProvider, AuthorizationProvider authorizationProvider) {
		super(eventBus, config, workerExecutor, pgPool, solrClient, oauth2AuthenticationProvider, authorizationProvider);
	}

	@Override
	public void sqlPATCHTrafficSearch(TrafficSearch o, Boolean inheritPk,
			Handler<AsyncResult<TrafficSearch>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		JsonObject jsonObject = siteRequest.getJsonObject();

		// Because the TrafficSearch data does not contain the contrabandKeys, 
		// don't set the contrabandKeys to null when reimporting TrafficSearch data. 
		jsonObject.remove("setContrabandKeys");

		// Because the TrafficSearch data does not contain the searchBasisKeys, 
		// don't set the searchBasisKeys to null when reimporting TrafficSearch data. 
		jsonObject.remove("setSearchBasisKeys");

		super.sqlPATCHTrafficSearch(o, inheritPk, eventHandler);
	}
}
