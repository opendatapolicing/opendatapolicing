package com.opendatapolicing.enus.context;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.slf4j.LoggerFactory;
import io.vertx.ext.web.Router;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import java.util.ArrayList;
import io.vertx.core.WorkerExecutor;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.vertx.ext.auth.authorization.AuthorizationProvider;
import io.vertx.ext.web.openapi.RouterBuilder;
import java.math.RoundingMode;
import com.opendatapolicing.enus.wrap.Wrap;
import org.slf4j.Logger;
import java.math.MathContext;
import io.vertx.core.Vertx;
import io.vertx.ext.web.handler.OAuth2AuthHandler;
import io.vertx.pgclient.PgPool;
import com.opendatapolicing.enus.writer.AllWriter;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.opendatapolicing.enus.request.api.ApiRequest;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.lang.Object;
import com.opendatapolicing.enus.cluster.Cluster;
import io.vertx.ext.auth.oauth2.OAuth2Auth;
import com.opendatapolicing.enus.config.SiteConfig;
import io.vertx.ext.mail.MailClient;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.context.SiteContextEnUS&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class SiteContextEnUSGen<DEV> extends Object {

/*
CREATE TABLE PageDesign(
	pk bigserial primary key
	, inheritPk bigint
	, created timestamp with time zone
	, pageDesignCompleteName text
	, designHidden boolean
	, pageContentType text
	);
CREATE TABLE HtmlPart(
	htmlLink text
	, htmlElement text
	, htmlId text
	, htmlClasses text
	, htmlStyle text
	, htmlBefore text
	, htmlAfter text
	, htmlText text
	, htmlVar text
	, htmlVarSpan text
	, htmlVarForm text
	, htmlVarInput text
	, htmlVarForEach text
	, htmlVarHtml text
	, htmlVarBase64Decode text
	, htmlExclude boolean
	, pdfExclude boolean
	, loginLogout boolean
	, searchUri text
	, mapTo text
	, sort1 double precision
	, sort2 double precision
	, sort3 double precision
	, sort4 double precision
	, sort5 double precision
	, sort6 double precision
	, sort7 double precision
	, sort8 double precision
	, sort9 double precision
	, sort10 double precision
	, pk bigserial primary key
	, inheritPk bigint
	, created timestamp with time zone
	);
CREATE TABLE SiteUser(
	userKey bigint
	, userId text
	, userName text
	, userEmail text
	, userFirstName text
	, userLastName text
	, userFullName text
	, pk bigserial primary key
	, inheritPk bigint
	, created timestamp with time zone
	);
CREATE TABLE SiteState(
	pk bigserial primary key
	, inheritPk bigint
	, created timestamp with time zone
	, stateName text
	, stateAbbreviation text
	, imageLeft integer
	, imageTop integer
	);
CREATE TABLE SiteAgency(
	pk bigserial primary key
	, inheritPk bigint
	, created timestamp with time zone
	, agencyName text
	, stateKey bigint references SiteState(pk)
	, imageLeft integer
	, imageTop integer
	, imageCoords text
	);
CREATE TABLE TrafficStop(
	pk bigserial primary key
	, inheritPk bigint
	, created timestamp with time zone
	, stopAgencyTitle text
	, stopDateTime timestamp with time zone
	, stopYear integer
	, stopPurposeNum integer
	, stopActionNum integer
	, stopDriverArrest boolean
	, stopPassengerArrest boolean
	, stopEncounterForce boolean
	, stopEngageForce boolean
	, stopOfficerInjury boolean
	, stopDriverInjury boolean
	, stopPassengerInjury boolean
	, stopOfficerId text
	, stopLocationId text
	, stopCityId text
	);
CREATE TABLE TrafficPerson(
	pk bigserial primary key
	, inheritPk bigint
	, created timestamp with time zone
	, trafficStopKey bigint references TrafficStop(pk)
	, personAge integer
	, personTypeId text
	, personGenderId text
	, personEthnicityId text
	, personRaceId text
	);
CREATE TABLE TrafficSearch(
	pk bigserial primary key
	, inheritPk bigint
	, created timestamp with time zone
	, personKey bigint references TrafficPerson(pk)
	, searchTypeNum integer
	, searchVehicle boolean
	, searchDriver boolean
	, searchPassenger boolean
	, searchProperty boolean
	, searchVehicleSiezed boolean
	, searchPersonalPropertySiezed boolean
	, searchOtherPropertySiezed boolean
	);
CREATE TABLE TrafficContraband(
	pk bigserial primary key
	, inheritPk bigint
	, created timestamp with time zone
	, searchKey bigint references TrafficSearch(pk)
	, contrabandOunces decimal
	, contrabandPounds decimal
	, contrabandPints decimal
	, contrabandGallons decimal
	, contrabandDosages decimal
	, contrabandGrams decimal
	, contrabandKilos decimal
	, contrabandMoney decimal
	, contrabandWeapons decimal
	, contrabandDollarAmount decimal
	);
CREATE TABLE SearchBasis(
	pk bigserial primary key
	, inheritPk bigint
	, created timestamp with time zone
	, searchKey bigint references TrafficSearch(pk)
	, searchBasisId text
	, searchBasisTitle text
	);
CREATE TABLE PageDesignChildDesignKeys_PageDesignParentDesignKeys(
	pk bigserial primary key
	, pk1 bigint references PageDesign(pk)
	, pk2 bigint references PageDesign(pk)
	);
CREATE TABLE PageDesignHtmlPartKeys_HtmlPartPageDesignKeys(
	pk bigserial primary key
	, pk1 bigint references PageDesign(pk)
	, pk2 bigint references HtmlPart(pk)
	);
*/

	protected static final Logger LOG = LoggerFactory.getLogger(SiteContextEnUS.class);

	///////////
	// vertx //
	///////////

	/**	 The entity vertx
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Vertx vertx;
	@JsonIgnore
	public Wrap<Vertx> vertxWrap = new Wrap<Vertx>().p(this).c(Vertx.class).var("vertx").o(vertx);

	/**	<br/> The entity vertx
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.context.SiteContextEnUS&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:vertx">Find the entity vertx in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _vertx(Wrap<Vertx> c);

	public Vertx getVertx() {
		return vertx;
	}

	public void setVertx(Vertx vertx) {
		this.vertx = vertx;
		this.vertxWrap.alreadyInitialized = true;
	}
	public static Vertx staticSetVertx(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteContextEnUS vertxInit() {
		if(!vertxWrap.alreadyInitialized) {
			_vertx(vertxWrap);
			if(vertx == null)
				setVertx(vertxWrap.o);
		}
		vertxWrap.alreadyInitialized(true);
		return (SiteContextEnUS)this;
	}

	///////////////////
	// routerBuilder //
	///////////////////

	/**	 The entity routerBuilder
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected RouterBuilder routerBuilder;
	@JsonIgnore
	public Wrap<RouterBuilder> routerBuilderWrap = new Wrap<RouterBuilder>().p(this).c(RouterBuilder.class).var("routerBuilder").o(routerBuilder);

	/**	<br/> The entity routerBuilder
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.context.SiteContextEnUS&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:routerBuilder">Find the entity routerBuilder in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _routerBuilder(Wrap<RouterBuilder> c);

	public RouterBuilder getRouterBuilder() {
		return routerBuilder;
	}

	public void setRouterBuilder(RouterBuilder routerBuilder) {
		this.routerBuilder = routerBuilder;
		this.routerBuilderWrap.alreadyInitialized = true;
	}
	public static RouterBuilder staticSetRouterBuilder(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteContextEnUS routerBuilderInit() {
		if(!routerBuilderWrap.alreadyInitialized) {
			_routerBuilder(routerBuilderWrap);
			if(routerBuilder == null)
				setRouterBuilder(routerBuilderWrap.o);
		}
		routerBuilderWrap.alreadyInitialized(true);
		return (SiteContextEnUS)this;
	}

	////////////
	// router //
	////////////

	/**	 The entity router
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Router router;
	@JsonIgnore
	public Wrap<Router> routerWrap = new Wrap<Router>().p(this).c(Router.class).var("router").o(router);

	/**	<br/> The entity router
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.context.SiteContextEnUS&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:router">Find the entity router in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _router(Wrap<Router> c);

	public Router getRouter() {
		return router;
	}

	public void setRouter(Router router) {
		this.router = router;
		this.routerWrap.alreadyInitialized = true;
	}
	public static Router staticSetRouter(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteContextEnUS routerInit() {
		if(!routerWrap.alreadyInitialized) {
			_router(routerWrap);
			if(router == null)
				setRouter(routerWrap.o);
		}
		routerWrap.alreadyInitialized(true);
		return (SiteContextEnUS)this;
	}

	/////////////////
	// authHandler //
	/////////////////

	/**	 The entity authHandler
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected OAuth2AuthHandler authHandler;
	@JsonIgnore
	public Wrap<OAuth2AuthHandler> authHandlerWrap = new Wrap<OAuth2AuthHandler>().p(this).c(OAuth2AuthHandler.class).var("authHandler").o(authHandler);

	/**	<br/> The entity authHandler
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.context.SiteContextEnUS&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:authHandler">Find the entity authHandler in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _authHandler(Wrap<OAuth2AuthHandler> c);

	public OAuth2AuthHandler getAuthHandler() {
		return authHandler;
	}

	public void setAuthHandler(OAuth2AuthHandler authHandler) {
		this.authHandler = authHandler;
		this.authHandlerWrap.alreadyInitialized = true;
	}
	public static OAuth2AuthHandler staticSetAuthHandler(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteContextEnUS authHandlerInit() {
		if(!authHandlerWrap.alreadyInitialized) {
			_authHandler(authHandlerWrap);
			if(authHandler == null)
				setAuthHandler(authHandlerWrap.o);
		}
		authHandlerWrap.alreadyInitialized(true);
		return (SiteContextEnUS)this;
	}

	//////////////////////////////////
	// oauth2AuthenticationProvider //
	//////////////////////////////////

	/**	 The entity oauth2AuthenticationProvider
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected OAuth2Auth oauth2AuthenticationProvider;
	@JsonIgnore
	public Wrap<OAuth2Auth> oauth2AuthenticationProviderWrap = new Wrap<OAuth2Auth>().p(this).c(OAuth2Auth.class).var("oauth2AuthenticationProvider").o(oauth2AuthenticationProvider);

	/**	<br/> The entity oauth2AuthenticationProvider
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.context.SiteContextEnUS&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:oauth2AuthenticationProvider">Find the entity oauth2AuthenticationProvider in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _oauth2AuthenticationProvider(Wrap<OAuth2Auth> c);

	public OAuth2Auth getOauth2AuthenticationProvider() {
		return oauth2AuthenticationProvider;
	}

	public void setOauth2AuthenticationProvider(OAuth2Auth oauth2AuthenticationProvider) {
		this.oauth2AuthenticationProvider = oauth2AuthenticationProvider;
		this.oauth2AuthenticationProviderWrap.alreadyInitialized = true;
	}
	public static OAuth2Auth staticSetOauth2AuthenticationProvider(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteContextEnUS oauth2AuthenticationProviderInit() {
		if(!oauth2AuthenticationProviderWrap.alreadyInitialized) {
			_oauth2AuthenticationProvider(oauth2AuthenticationProviderWrap);
			if(oauth2AuthenticationProvider == null)
				setOauth2AuthenticationProvider(oauth2AuthenticationProviderWrap.o);
		}
		oauth2AuthenticationProviderWrap.alreadyInitialized(true);
		return (SiteContextEnUS)this;
	}

	///////////////////////////
	// authorizationProvider //
	///////////////////////////

	/**	 The entity authorizationProvider
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected AuthorizationProvider authorizationProvider;
	@JsonIgnore
	public Wrap<AuthorizationProvider> authorizationProviderWrap = new Wrap<AuthorizationProvider>().p(this).c(AuthorizationProvider.class).var("authorizationProvider").o(authorizationProvider);

	/**	<br/> The entity authorizationProvider
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.context.SiteContextEnUS&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:authorizationProvider">Find the entity authorizationProvider in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _authorizationProvider(Wrap<AuthorizationProvider> c);

	public AuthorizationProvider getAuthorizationProvider() {
		return authorizationProvider;
	}

	public void setAuthorizationProvider(AuthorizationProvider authorizationProvider) {
		this.authorizationProvider = authorizationProvider;
		this.authorizationProviderWrap.alreadyInitialized = true;
	}
	public static AuthorizationProvider staticSetAuthorizationProvider(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteContextEnUS authorizationProviderInit() {
		if(!authorizationProviderWrap.alreadyInitialized) {
			_authorizationProvider(authorizationProviderWrap);
			if(authorizationProvider == null)
				setAuthorizationProvider(authorizationProviderWrap.o);
		}
		authorizationProviderWrap.alreadyInitialized(true);
		return (SiteContextEnUS)this;
	}

	////////////////////
	// workerExecutor //
	////////////////////

	/**	 The entity workerExecutor
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected WorkerExecutor workerExecutor;
	@JsonIgnore
	public Wrap<WorkerExecutor> workerExecutorWrap = new Wrap<WorkerExecutor>().p(this).c(WorkerExecutor.class).var("workerExecutor").o(workerExecutor);

	/**	<br/> The entity workerExecutor
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.context.SiteContextEnUS&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:workerExecutor">Find the entity workerExecutor in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _workerExecutor(Wrap<WorkerExecutor> c);

	public WorkerExecutor getWorkerExecutor() {
		return workerExecutor;
	}

	public void setWorkerExecutor(WorkerExecutor workerExecutor) {
		this.workerExecutor = workerExecutor;
		this.workerExecutorWrap.alreadyInitialized = true;
	}
	public static WorkerExecutor staticSetWorkerExecutor(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteContextEnUS workerExecutorInit() {
		if(!workerExecutorWrap.alreadyInitialized) {
			_workerExecutor(workerExecutorWrap);
			if(workerExecutor == null)
				setWorkerExecutor(workerExecutorWrap.o);
		}
		workerExecutorWrap.alreadyInitialized(true);
		return (SiteContextEnUS)this;
	}

	////////////////
	// siteConfig //
	////////////////

	/**	 The entity siteConfig
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut SiteConfig(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SiteConfig siteConfig = new SiteConfig();
	@JsonIgnore
	public Wrap<SiteConfig> siteConfigWrap = new Wrap<SiteConfig>().p(this).c(SiteConfig.class).var("siteConfig").o(siteConfig);

	/**	<br/> The entity siteConfig
	 *  It is constructed before being initialized with the constructor by default SiteConfig(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.context.SiteContextEnUS&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:siteConfig">Find the entity siteConfig in Solr</a>
	 * <br/>
	 * @param siteConfig is the entity already constructed. 
	 **/
	protected abstract void _siteConfig(SiteConfig o);

	public SiteConfig getSiteConfig() {
		return siteConfig;
	}

	public void setSiteConfig(SiteConfig siteConfig) {
		this.siteConfig = siteConfig;
		this.siteConfigWrap.alreadyInitialized = true;
	}
	public static SiteConfig staticSetSiteConfig(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteContextEnUS siteConfigInit() {
		if(!siteConfigWrap.alreadyInitialized) {
			_siteConfig(siteConfig);
		}
		siteConfig.initDeepForClass(null);
		siteConfigWrap.alreadyInitialized(true);
		return (SiteContextEnUS)this;
	}

	////////////
	// pgPool //
	////////////

	/**	 The entity pgPool
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected PgPool pgPool;
	@JsonIgnore
	public Wrap<PgPool> pgPoolWrap = new Wrap<PgPool>().p(this).c(PgPool.class).var("pgPool").o(pgPool);

	/**	<br/> The entity pgPool
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.context.SiteContextEnUS&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pgPool">Find the entity pgPool in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pgPool(Wrap<PgPool> c);

	public PgPool getPgPool() {
		return pgPool;
	}

	public void setPgPool(PgPool pgPool) {
		this.pgPool = pgPool;
		this.pgPoolWrap.alreadyInitialized = true;
	}
	public static PgPool staticSetPgPool(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteContextEnUS pgPoolInit() {
		if(!pgPoolWrap.alreadyInitialized) {
			_pgPool(pgPoolWrap);
			if(pgPool == null)
				setPgPool(pgPoolWrap.o);
		}
		pgPoolWrap.alreadyInitialized(true);
		return (SiteContextEnUS)this;
	}

	////////////////
	// solrClient //
	////////////////

	/**	 The entity solrClient
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected HttpSolrClient solrClient;
	@JsonIgnore
	public Wrap<HttpSolrClient> solrClientWrap = new Wrap<HttpSolrClient>().p(this).c(HttpSolrClient.class).var("solrClient").o(solrClient);

	/**	<br/> The entity solrClient
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.context.SiteContextEnUS&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:solrClient">Find the entity solrClient in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _solrClient(Wrap<HttpSolrClient> c);

	public HttpSolrClient getSolrClient() {
		return solrClient;
	}

	public void setSolrClient(HttpSolrClient solrClient) {
		this.solrClient = solrClient;
		this.solrClientWrap.alreadyInitialized = true;
	}
	public static HttpSolrClient staticSetSolrClient(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteContextEnUS solrClientInit() {
		if(!solrClientWrap.alreadyInitialized) {
			_solrClient(solrClientWrap);
			if(solrClient == null)
				setSolrClient(solrClientWrap.o);
		}
		solrClientWrap.alreadyInitialized(true);
		return (SiteContextEnUS)this;
	}

	////////////////
	// mailClient //
	////////////////

	/**	 The entity mailClient
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected MailClient mailClient;
	@JsonIgnore
	public Wrap<MailClient> mailClientWrap = new Wrap<MailClient>().p(this).c(MailClient.class).var("mailClient").o(mailClient);

	/**	<br/> The entity mailClient
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.context.SiteContextEnUS&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:mailClient">Find the entity mailClient in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _mailClient(Wrap<MailClient> c);

	public MailClient getMailClient() {
		return mailClient;
	}

	public void setMailClient(MailClient mailClient) {
		this.mailClient = mailClient;
		this.mailClientWrap.alreadyInitialized = true;
	}
	public static MailClient staticSetMailClient(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteContextEnUS mailClientInit() {
		if(!mailClientWrap.alreadyInitialized) {
			_mailClient(mailClientWrap);
			if(mailClient == null)
				setMailClient(mailClientWrap.o);
		}
		mailClientWrap.alreadyInitialized(true);
		return (SiteContextEnUS)this;
	}

	/////////////////////////
	// solrClientComputate //
	/////////////////////////

	/**	 The entity solrClientComputate
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected HttpSolrClient solrClientComputate;
	@JsonIgnore
	public Wrap<HttpSolrClient> solrClientComputateWrap = new Wrap<HttpSolrClient>().p(this).c(HttpSolrClient.class).var("solrClientComputate").o(solrClientComputate);

	/**	<br/> The entity solrClientComputate
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.context.SiteContextEnUS&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:solrClientComputate">Find the entity solrClientComputate in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _solrClientComputate(Wrap<HttpSolrClient> c);

	public HttpSolrClient getSolrClientComputate() {
		return solrClientComputate;
	}

	public void setSolrClientComputate(HttpSolrClient solrClientComputate) {
		this.solrClientComputate = solrClientComputate;
		this.solrClientComputateWrap.alreadyInitialized = true;
	}
	public static HttpSolrClient staticSetSolrClientComputate(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteContextEnUS solrClientComputateInit() {
		if(!solrClientComputateWrap.alreadyInitialized) {
			_solrClientComputate(solrClientComputateWrap);
			if(solrClientComputate == null)
				setSolrClientComputate(solrClientComputateWrap.o);
		}
		solrClientComputateWrap.alreadyInitialized(true);
		return (SiteContextEnUS)this;
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedSiteContextEnUS = false;

	public SiteContextEnUS initDeepSiteContextEnUS(SiteRequestEnUS siteRequest_) {
		if(!alreadyInitializedSiteContextEnUS) {
			alreadyInitializedSiteContextEnUS = true;
			initDeepSiteContextEnUS();
		}
		return (SiteContextEnUS)this;
	}

	public void initDeepSiteContextEnUS() {
		initSiteContextEnUS();
	}

	public void initSiteContextEnUS() {
		vertxInit();
		routerBuilderInit();
		routerInit();
		authHandlerInit();
		oauth2AuthenticationProviderInit();
		authorizationProviderInit();
		workerExecutorInit();
		siteConfigInit();
		pgPoolInit();
		solrClientInit();
		mailClientInit();
		solrClientComputateInit();
	}

	public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepSiteContextEnUS(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainSiteContextEnUS(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
			else if(o instanceof Map) {
				Map<?, ?> map = (Map<?, ?>)o;
				o = map.get(v);
			}
		}
		return o;
	}
	public Object obtainSiteContextEnUS(String var) {
		SiteContextEnUS oSiteContextEnUS = (SiteContextEnUS)this;
		switch(var) {
			case "vertx":
				return oSiteContextEnUS.vertx;
			case "routerBuilder":
				return oSiteContextEnUS.routerBuilder;
			case "router":
				return oSiteContextEnUS.router;
			case "authHandler":
				return oSiteContextEnUS.authHandler;
			case "oauth2AuthenticationProvider":
				return oSiteContextEnUS.oauth2AuthenticationProvider;
			case "authorizationProvider":
				return oSiteContextEnUS.authorizationProvider;
			case "workerExecutor":
				return oSiteContextEnUS.workerExecutor;
			case "siteConfig":
				return oSiteContextEnUS.siteConfig;
			case "pgPool":
				return oSiteContextEnUS.pgPool;
			case "solrClient":
				return oSiteContextEnUS.solrClient;
			case "mailClient":
				return oSiteContextEnUS.mailClient;
			case "solrClientComputate":
				return oSiteContextEnUS.solrClientComputate;
			default:
				return null;
		}
	}

	///////////////
	// attribute //
	///////////////

	public boolean attributeForClass(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = attributeSiteContextEnUS(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeSiteContextEnUS(String var, Object val) {
		SiteContextEnUS oSiteContextEnUS = (SiteContextEnUS)this;
		switch(var) {
			default:
				return null;
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetSiteContextEnUS(entityVar,  siteRequest_, o);
	}
	public static Object staticSetSiteContextEnUS(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return null;
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrSiteContextEnUS(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrSiteContextEnUS(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return null;
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrSiteContextEnUS(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrSiteContextEnUS(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return null;
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqSiteContextEnUS(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqSiteContextEnUS(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return null;
		}
	}

	/////////////
	// define //
	/////////////

	public boolean defineForClass(String var, String val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = defineSiteContextEnUS(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSiteContextEnUS(String var, String val) {
		switch(var.toLowerCase()) {
			default:
				return null;
		}
	}

	public boolean defineForClass(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = defineSiteContextEnUS(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSiteContextEnUS(String var, Object val) {
		switch(var.toLowerCase()) {
			default:
				return null;
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash();
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof SiteContextEnUS))
			return false;
		SiteContextEnUS that = (SiteContextEnUS)o;
		return true;
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("SiteContextEnUS { ");
		sb.append(" }");
		return sb.toString();
	}
}
