package com.opendatapolicing.enus.searchbasis;

import org.apache.solr.client.solrj.SolrClient;

import io.vertx.core.WorkerExecutor;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.authorization.AuthorizationProvider;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.pgclient.PgPool;

/**
 * Translate: false
 **/
public class SearchBasisEnUSApiServiceImpl extends SearchBasisEnUSGenApiServiceImpl {

	public SearchBasisEnUSApiServiceImpl(EventBus eventBus, JsonObject config, WorkerExecutor workerExecutor, PgPool pgPool, SolrClient solrClient, OAuth2Auth oauth2AuthenticationProvider, AuthorizationProvider authorizationProvider) {
		super(eventBus, config, workerExecutor, pgPool, solrClient, oauth2AuthenticationProvider, authorizationProvider);
	}
}
