package com.opendatapolicing.enus.context;  

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opendatapolicing.enus.config.SiteConfig;
import com.opendatapolicing.enus.wrap.Wrap;

import io.vertx.core.Vertx;
import io.vertx.core.WorkerExecutor;
import io.vertx.ext.auth.authorization.AuthorizationProvider;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import io.vertx.ext.mail.MailClient;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.OAuth2AuthHandler;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.pgclient.PgPool;


/** 
 * Keyword: classSimpleNameSiteContext
 */      
public class SiteContextEnUS extends SiteContextEnUSGen<Object> {

	protected Logger log = LoggerFactory.getLogger(getClass());

	protected void _vertx(Wrap<Vertx> c) {
	}

	protected void _routerBuilder(Wrap<RouterBuilder> c) {
	}

	protected void _router(Wrap<Router> c) {
	}

	protected void _authHandler(Wrap<OAuth2AuthHandler> c) {
	}

	protected void _oauth2AuthenticationProvider(Wrap<OAuth2Auth> c) {
	}

	protected void _authorizationProvider(Wrap<AuthorizationProvider> c) {
	}

	protected void _workerExecutor(Wrap<WorkerExecutor> c) {
	}

	protected void _siteConfig(SiteConfig o) { 
	}

	protected void _pgPool(Wrap<PgPool> c) {
	}

	protected void _solrClient(Wrap<HttpSolrClient> c) {
		HttpSolrClient o = new HttpSolrClient.Builder(siteConfig.getSolrUrl()).build();
		c.o(o);
	}

	protected void _mailClient(Wrap<MailClient> c) {
	}

	protected void _solrClientComputate(Wrap<HttpSolrClient> c) {
		String solrUrlComputate = siteConfig.getSolrUrlComputate();
		if(StringUtils.isNotEmpty(solrUrlComputate)) {
			HttpSolrClient o = new HttpSolrClient.Builder(solrUrlComputate).build();
			c.o(o);
		}
	}
}
