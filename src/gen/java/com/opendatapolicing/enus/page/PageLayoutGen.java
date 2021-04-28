package com.opendatapolicing.enus.page;

import java.util.Arrays;
import java.util.Date;
import java.time.ZonedDateTime;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import java.lang.Integer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.util.Locale;
import java.util.Map;
import java.time.ZoneOffset;
import java.math.RoundingMode;
import com.opendatapolicing.enus.wrap.Wrap;
import java.math.MathContext;
import com.opendatapolicing.enus.writer.AllWriter;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import io.vertx.core.Future;
import com.opendatapolicing.enus.request.api.ApiRequest;
import java.time.ZoneId;
import java.util.Objects;
import java.util.List;
import java.time.OffsetDateTime;
import java.util.Optional;
import com.opendatapolicing.enus.cluster.Cluster;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.text.NumberFormat;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.Boolean;
import com.opendatapolicing.enus.config.ConfigKeys;
import java.lang.String;
import org.slf4j.Logger;
import io.vertx.core.Promise;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.vertx.core.json.JsonArray;
import org.apache.solr.common.SolrDocument;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.math.NumberUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.lang.Object;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class PageLayoutGen<DEV> extends Object {
	protected static final Logger LOG = LoggerFactory.getLogger(PageLayout.class);

	//////////////////
	// siteRequest_ //
	//////////////////

	/**	 The entity siteRequest_
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SiteRequestEnUS siteRequest_;
	@JsonIgnore
	public Wrap<SiteRequestEnUS> siteRequest_Wrap = new Wrap<SiteRequestEnUS>().p(this).c(SiteRequestEnUS.class).var("siteRequest_").o(siteRequest_);

	/**	<br/> The entity siteRequest_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:siteRequest_">Find the entity siteRequest_ in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _siteRequest_(Wrap<SiteRequestEnUS> c);

	public SiteRequestEnUS getSiteRequest_() {
		return siteRequest_;
	}

	public void setSiteRequest_(SiteRequestEnUS siteRequest_) {
		this.siteRequest_ = siteRequest_;
		this.siteRequest_Wrap.alreadyInitialized = true;
	}
	public static SiteRequestEnUS staticSetSiteRequest_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected PageLayout siteRequest_Init() {
		if(!siteRequest_Wrap.alreadyInitialized) {
			_siteRequest_(siteRequest_Wrap);
			if(siteRequest_ == null)
				setSiteRequest_(siteRequest_Wrap.o);
		}
		siteRequest_Wrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	/////////////////
	// siteBaseUrl //
	/////////////////

	/**	 The entity siteBaseUrl
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String siteBaseUrl;
	@JsonIgnore
	public Wrap<String> siteBaseUrlWrap = new Wrap<String>().p(this).c(String.class).var("siteBaseUrl").o(siteBaseUrl);

	/**	<br/> The entity siteBaseUrl
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:siteBaseUrl">Find the entity siteBaseUrl in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _siteBaseUrl(Wrap<String> c);

	public String getSiteBaseUrl() {
		return siteBaseUrl;
	}
	public void setSiteBaseUrl(String o) {
		this.siteBaseUrl = PageLayout.staticSetSiteBaseUrl(siteRequest_, o);
		this.siteBaseUrlWrap.alreadyInitialized = true;
	}
	public static String staticSetSiteBaseUrl(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout siteBaseUrlInit() {
		if(!siteBaseUrlWrap.alreadyInitialized) {
			_siteBaseUrl(siteBaseUrlWrap);
			if(siteBaseUrl == null)
				setSiteBaseUrl(siteBaseUrlWrap.o);
		}
		siteBaseUrlWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrSiteBaseUrl(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrSiteBaseUrl(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSiteBaseUrl(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrSiteBaseUrl(siteRequest_, PageLayout.staticSolrSiteBaseUrl(siteRequest_, PageLayout.staticSetSiteBaseUrl(siteRequest_, o)));
	}

	public String solrSiteBaseUrl() {
		return PageLayout.staticSolrSiteBaseUrl(siteRequest_, siteBaseUrl);
	}

	public String strSiteBaseUrl() {
		return siteBaseUrl == null ? "" : siteBaseUrl;
	}

	public String sqlSiteBaseUrl() {
		return siteBaseUrl;
	}

	public String jsonSiteBaseUrl() {
		return siteBaseUrl == null ? "" : siteBaseUrl;
	}

	///////////////////
	// staticBaseUrl //
	///////////////////

	/**	 The entity staticBaseUrl
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String staticBaseUrl;
	@JsonIgnore
	public Wrap<String> staticBaseUrlWrap = new Wrap<String>().p(this).c(String.class).var("staticBaseUrl").o(staticBaseUrl);

	/**	<br/> The entity staticBaseUrl
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:staticBaseUrl">Find the entity staticBaseUrl in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _staticBaseUrl(Wrap<String> c);

	public String getStaticBaseUrl() {
		return staticBaseUrl;
	}
	public void setStaticBaseUrl(String o) {
		this.staticBaseUrl = PageLayout.staticSetStaticBaseUrl(siteRequest_, o);
		this.staticBaseUrlWrap.alreadyInitialized = true;
	}
	public static String staticSetStaticBaseUrl(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout staticBaseUrlInit() {
		if(!staticBaseUrlWrap.alreadyInitialized) {
			_staticBaseUrl(staticBaseUrlWrap);
			if(staticBaseUrl == null)
				setStaticBaseUrl(staticBaseUrlWrap.o);
		}
		staticBaseUrlWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrStaticBaseUrl(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStaticBaseUrl(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStaticBaseUrl(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrStaticBaseUrl(siteRequest_, PageLayout.staticSolrStaticBaseUrl(siteRequest_, PageLayout.staticSetStaticBaseUrl(siteRequest_, o)));
	}

	public String solrStaticBaseUrl() {
		return PageLayout.staticSolrStaticBaseUrl(siteRequest_, staticBaseUrl);
	}

	public String strStaticBaseUrl() {
		return staticBaseUrl == null ? "" : staticBaseUrl;
	}

	public String sqlStaticBaseUrl() {
		return staticBaseUrl;
	}

	public String jsonStaticBaseUrl() {
		return staticBaseUrl == null ? "" : staticBaseUrl;
	}

	/////////
	// map //
	/////////

	/**	 The entity map
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut Map<String, Object>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Map<String, Object> map = new HashMap<String, Object>();
	@JsonIgnore
	public Wrap<Map<String, Object>> mapWrap = new Wrap<Map<String, Object>>().p(this).c(Map.class).var("map").o(map);

	/**	<br/> The entity map
	 *  It is constructed before being initialized with the constructor by default Map<String, Object>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:map">Find the entity map in Solr</a>
	 * <br/>
	 * @param map is the entity already constructed. 
	 **/
	protected abstract void _map(Map<String, Object> m);

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
		this.mapWrap.alreadyInitialized = true;
	}
	public static Map<String, Object> staticSetMap(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected PageLayout mapInit() {
		if(!mapWrap.alreadyInitialized) {
			_map(map);
		}
		mapWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	//////////////////////
	// pageSolrDocument //
	//////////////////////

	/**	 The entity pageSolrDocument
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SolrDocument pageSolrDocument;
	@JsonIgnore
	public Wrap<SolrDocument> pageSolrDocumentWrap = new Wrap<SolrDocument>().p(this).c(SolrDocument.class).var("pageSolrDocument").o(pageSolrDocument);

	/**	<br/> The entity pageSolrDocument
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageSolrDocument">Find the entity pageSolrDocument in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageSolrDocument(Wrap<SolrDocument> c);

	public SolrDocument getPageSolrDocument() {
		return pageSolrDocument;
	}

	public void setPageSolrDocument(SolrDocument pageSolrDocument) {
		this.pageSolrDocument = pageSolrDocument;
		this.pageSolrDocumentWrap.alreadyInitialized = true;
	}
	public static SolrDocument staticSetPageSolrDocument(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected PageLayout pageSolrDocumentInit() {
		if(!pageSolrDocumentWrap.alreadyInitialized) {
			_pageSolrDocument(pageSolrDocumentWrap);
			if(pageSolrDocument == null)
				setPageSolrDocument(pageSolrDocumentWrap.o);
		}
		pageSolrDocumentWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	///////
	// w //
	///////

	/**	 The entity w
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected AllWriter w;
	@JsonIgnore
	public Wrap<AllWriter> wWrap = new Wrap<AllWriter>().p(this).c(AllWriter.class).var("w").o(w);

	/**	<br/> The entity w
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:w">Find the entity w in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _w(Wrap<AllWriter> c);

	public AllWriter getW() {
		return w;
	}

	public void setW(AllWriter w) {
		this.w = w;
		this.wWrap.alreadyInitialized = true;
	}
	public static AllWriter staticSetW(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected PageLayout wInit() {
		if(!wWrap.alreadyInitialized) {
			_w(wWrap);
			if(w == null)
				setW(wWrap.o);
		}
		if(w != null)
			w.initDeepForClass(siteRequest_);
		wWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	//////////////////////
	// contextIconGroup //
	//////////////////////

	/**	 The entity contextIconGroup
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String contextIconGroup;
	@JsonIgnore
	public Wrap<String> contextIconGroupWrap = new Wrap<String>().p(this).c(String.class).var("contextIconGroup").o(contextIconGroup);

	/**	<br/> The entity contextIconGroup
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:contextIconGroup">Find the entity contextIconGroup in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _contextIconGroup(Wrap<String> c);

	public String getContextIconGroup() {
		return contextIconGroup;
	}
	public void setContextIconGroup(String o) {
		this.contextIconGroup = PageLayout.staticSetContextIconGroup(siteRequest_, o);
		this.contextIconGroupWrap.alreadyInitialized = true;
	}
	public static String staticSetContextIconGroup(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout contextIconGroupInit() {
		if(!contextIconGroupWrap.alreadyInitialized) {
			_contextIconGroup(contextIconGroupWrap);
			if(contextIconGroup == null)
				setContextIconGroup(contextIconGroupWrap.o);
		}
		contextIconGroupWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrContextIconGroup(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrContextIconGroup(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqContextIconGroup(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrContextIconGroup(siteRequest_, PageLayout.staticSolrContextIconGroup(siteRequest_, PageLayout.staticSetContextIconGroup(siteRequest_, o)));
	}

	public String solrContextIconGroup() {
		return PageLayout.staticSolrContextIconGroup(siteRequest_, contextIconGroup);
	}

	public String strContextIconGroup() {
		return contextIconGroup == null ? "" : contextIconGroup;
	}

	public String sqlContextIconGroup() {
		return contextIconGroup;
	}

	public String jsonContextIconGroup() {
		return contextIconGroup == null ? "" : contextIconGroup;
	}

	/////////////////////
	// contextIconName //
	/////////////////////

	/**	 The entity contextIconName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String contextIconName;
	@JsonIgnore
	public Wrap<String> contextIconNameWrap = new Wrap<String>().p(this).c(String.class).var("contextIconName").o(contextIconName);

	/**	<br/> The entity contextIconName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:contextIconName">Find the entity contextIconName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _contextIconName(Wrap<String> c);

	public String getContextIconName() {
		return contextIconName;
	}
	public void setContextIconName(String o) {
		this.contextIconName = PageLayout.staticSetContextIconName(siteRequest_, o);
		this.contextIconNameWrap.alreadyInitialized = true;
	}
	public static String staticSetContextIconName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout contextIconNameInit() {
		if(!contextIconNameWrap.alreadyInitialized) {
			_contextIconName(contextIconNameWrap);
			if(contextIconName == null)
				setContextIconName(contextIconNameWrap.o);
		}
		contextIconNameWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrContextIconName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrContextIconName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqContextIconName(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrContextIconName(siteRequest_, PageLayout.staticSolrContextIconName(siteRequest_, PageLayout.staticSetContextIconName(siteRequest_, o)));
	}

	public String solrContextIconName() {
		return PageLayout.staticSolrContextIconName(siteRequest_, contextIconName);
	}

	public String strContextIconName() {
		return contextIconName == null ? "" : contextIconName;
	}

	public String sqlContextIconName() {
		return contextIconName;
	}

	public String jsonContextIconName() {
		return contextIconName == null ? "" : contextIconName;
	}

	///////////////////////////
	// contextIconCssClasses //
	///////////////////////////

	/**	 The entity contextIconCssClasses
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String contextIconCssClasses;
	@JsonIgnore
	public Wrap<String> contextIconCssClassesWrap = new Wrap<String>().p(this).c(String.class).var("contextIconCssClasses").o(contextIconCssClasses);

	/**	<br/> The entity contextIconCssClasses
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:contextIconCssClasses">Find the entity contextIconCssClasses in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _contextIconCssClasses(Wrap<String> c);

	public String getContextIconCssClasses() {
		return contextIconCssClasses;
	}
	public void setContextIconCssClasses(String o) {
		this.contextIconCssClasses = PageLayout.staticSetContextIconCssClasses(siteRequest_, o);
		this.contextIconCssClassesWrap.alreadyInitialized = true;
	}
	public static String staticSetContextIconCssClasses(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout contextIconCssClassesInit() {
		if(!contextIconCssClassesWrap.alreadyInitialized) {
			_contextIconCssClasses(contextIconCssClassesWrap);
			if(contextIconCssClasses == null)
				setContextIconCssClasses(contextIconCssClassesWrap.o);
		}
		contextIconCssClassesWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrContextIconCssClasses(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrContextIconCssClasses(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqContextIconCssClasses(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrContextIconCssClasses(siteRequest_, PageLayout.staticSolrContextIconCssClasses(siteRequest_, PageLayout.staticSetContextIconCssClasses(siteRequest_, o)));
	}

	public String solrContextIconCssClasses() {
		return PageLayout.staticSolrContextIconCssClasses(siteRequest_, contextIconCssClasses);
	}

	public String strContextIconCssClasses() {
		return contextIconCssClasses == null ? "" : contextIconCssClasses;
	}

	public String sqlContextIconCssClasses() {
		return contextIconCssClasses;
	}

	public String jsonContextIconCssClasses() {
		return contextIconCssClasses == null ? "" : contextIconCssClasses;
	}

	///////////////////////
	// pageVisibleToBots //
	///////////////////////

	/**	 The entity pageVisibleToBots
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean pageVisibleToBots;
	@JsonIgnore
	public Wrap<Boolean> pageVisibleToBotsWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("pageVisibleToBots").o(pageVisibleToBots);

	/**	<br/> The entity pageVisibleToBots
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageVisibleToBots">Find the entity pageVisibleToBots in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageVisibleToBots(Wrap<Boolean> c);

	public Boolean getPageVisibleToBots() {
		return pageVisibleToBots;
	}

	public void setPageVisibleToBots(Boolean pageVisibleToBots) {
		this.pageVisibleToBots = pageVisibleToBots;
		this.pageVisibleToBotsWrap.alreadyInitialized = true;
	}
	public void setPageVisibleToBots(String o) {
		this.pageVisibleToBots = PageLayout.staticSetPageVisibleToBots(siteRequest_, o);
		this.pageVisibleToBotsWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetPageVisibleToBots(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected PageLayout pageVisibleToBotsInit() {
		if(!pageVisibleToBotsWrap.alreadyInitialized) {
			_pageVisibleToBots(pageVisibleToBotsWrap);
			if(pageVisibleToBots == null)
				setPageVisibleToBots(pageVisibleToBotsWrap.o);
		}
		pageVisibleToBotsWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static Boolean staticSolrPageVisibleToBots(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrPageVisibleToBots(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageVisibleToBots(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageVisibleToBots(siteRequest_, PageLayout.staticSolrPageVisibleToBots(siteRequest_, PageLayout.staticSetPageVisibleToBots(siteRequest_, o)));
	}

	public Boolean solrPageVisibleToBots() {
		return PageLayout.staticSolrPageVisibleToBots(siteRequest_, pageVisibleToBots);
	}

	public String strPageVisibleToBots() {
		return pageVisibleToBots == null ? "" : pageVisibleToBots.toString();
	}

	public Boolean sqlPageVisibleToBots() {
		return pageVisibleToBots;
	}

	public String jsonPageVisibleToBots() {
		return pageVisibleToBots == null ? "" : pageVisibleToBots.toString();
	}

	////////////
	// pageH1 //
	////////////

	/**	 The entity pageH1
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageH1;
	@JsonIgnore
	public Wrap<String> pageH1Wrap = new Wrap<String>().p(this).c(String.class).var("pageH1").o(pageH1);

	/**	<br/> The entity pageH1
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageH1">Find the entity pageH1 in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageH1(Wrap<String> c);

	public String getPageH1() {
		return pageH1;
	}
	public void setPageH1(String o) {
		this.pageH1 = PageLayout.staticSetPageH1(siteRequest_, o);
		this.pageH1Wrap.alreadyInitialized = true;
	}
	public static String staticSetPageH1(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout pageH1Init() {
		if(!pageH1Wrap.alreadyInitialized) {
			_pageH1(pageH1Wrap);
			if(pageH1 == null)
				setPageH1(pageH1Wrap.o);
		}
		pageH1Wrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrPageH1(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageH1(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageH1(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageH1(siteRequest_, PageLayout.staticSolrPageH1(siteRequest_, PageLayout.staticSetPageH1(siteRequest_, o)));
	}

	public String solrPageH1() {
		return PageLayout.staticSolrPageH1(siteRequest_, pageH1);
	}

	public String strPageH1() {
		return pageH1 == null ? "" : pageH1;
	}

	public String sqlPageH1() {
		return pageH1;
	}

	public String jsonPageH1() {
		return pageH1 == null ? "" : pageH1;
	}

	////////////
	// pageH2 //
	////////////

	/**	 The entity pageH2
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageH2;
	@JsonIgnore
	public Wrap<String> pageH2Wrap = new Wrap<String>().p(this).c(String.class).var("pageH2").o(pageH2);

	/**	<br/> The entity pageH2
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageH2">Find the entity pageH2 in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageH2(Wrap<String> c);

	public String getPageH2() {
		return pageH2;
	}
	public void setPageH2(String o) {
		this.pageH2 = PageLayout.staticSetPageH2(siteRequest_, o);
		this.pageH2Wrap.alreadyInitialized = true;
	}
	public static String staticSetPageH2(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout pageH2Init() {
		if(!pageH2Wrap.alreadyInitialized) {
			_pageH2(pageH2Wrap);
			if(pageH2 == null)
				setPageH2(pageH2Wrap.o);
		}
		pageH2Wrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrPageH2(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageH2(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageH2(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageH2(siteRequest_, PageLayout.staticSolrPageH2(siteRequest_, PageLayout.staticSetPageH2(siteRequest_, o)));
	}

	public String solrPageH2() {
		return PageLayout.staticSolrPageH2(siteRequest_, pageH2);
	}

	public String strPageH2() {
		return pageH2 == null ? "" : pageH2;
	}

	public String sqlPageH2() {
		return pageH2;
	}

	public String jsonPageH2() {
		return pageH2 == null ? "" : pageH2;
	}

	////////////
	// pageH3 //
	////////////

	/**	 The entity pageH3
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageH3;
	@JsonIgnore
	public Wrap<String> pageH3Wrap = new Wrap<String>().p(this).c(String.class).var("pageH3").o(pageH3);

	/**	<br/> The entity pageH3
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageH3">Find the entity pageH3 in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageH3(Wrap<String> c);

	public String getPageH3() {
		return pageH3;
	}
	public void setPageH3(String o) {
		this.pageH3 = PageLayout.staticSetPageH3(siteRequest_, o);
		this.pageH3Wrap.alreadyInitialized = true;
	}
	public static String staticSetPageH3(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout pageH3Init() {
		if(!pageH3Wrap.alreadyInitialized) {
			_pageH3(pageH3Wrap);
			if(pageH3 == null)
				setPageH3(pageH3Wrap.o);
		}
		pageH3Wrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrPageH3(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageH3(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageH3(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageH3(siteRequest_, PageLayout.staticSolrPageH3(siteRequest_, PageLayout.staticSetPageH3(siteRequest_, o)));
	}

	public String solrPageH3() {
		return PageLayout.staticSolrPageH3(siteRequest_, pageH3);
	}

	public String strPageH3() {
		return pageH3 == null ? "" : pageH3;
	}

	public String sqlPageH3() {
		return pageH3;
	}

	public String jsonPageH3() {
		return pageH3 == null ? "" : pageH3;
	}

	//////////////////
	// _pageH1Short //
	//////////////////

	/**	 The entity _pageH1Short
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String _pageH1Short;
	@JsonIgnore
	public Wrap<String> _pageH1ShortWrap = new Wrap<String>().p(this).c(String.class).var("_pageH1Short").o(_pageH1Short);

	/**	<br/> The entity _pageH1Short
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:_pageH1Short">Find the entity _pageH1Short in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void __pageH1Short(Wrap<String> c);

	public String get_pageH1Short() {
		return _pageH1Short;
	}
	public void set_pageH1Short(String o) {
		this._pageH1Short = PageLayout.staticSet_pageH1Short(siteRequest_, o);
		this._pageH1ShortWrap.alreadyInitialized = true;
	}
	public static String staticSet_pageH1Short(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout _pageH1ShortInit() {
		if(!_pageH1ShortWrap.alreadyInitialized) {
			__pageH1Short(_pageH1ShortWrap);
			if(_pageH1Short == null)
				set_pageH1Short(_pageH1ShortWrap.o);
		}
		_pageH1ShortWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolr_pageH1Short(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStr_pageH1Short(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFq_pageH1Short(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStr_pageH1Short(siteRequest_, PageLayout.staticSolr_pageH1Short(siteRequest_, PageLayout.staticSet_pageH1Short(siteRequest_, o)));
	}

	public String solr_pageH1Short() {
		return PageLayout.staticSolr_pageH1Short(siteRequest_, _pageH1Short);
	}

	public String str_pageH1Short() {
		return _pageH1Short == null ? "" : _pageH1Short;
	}

	public String sql_pageH1Short() {
		return _pageH1Short;
	}

	public String json_pageH1Short() {
		return _pageH1Short == null ? "" : _pageH1Short;
	}

	//////////////////
	// _pageH2Short //
	//////////////////

	/**	 The entity _pageH2Short
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String _pageH2Short;
	@JsonIgnore
	public Wrap<String> _pageH2ShortWrap = new Wrap<String>().p(this).c(String.class).var("_pageH2Short").o(_pageH2Short);

	/**	<br/> The entity _pageH2Short
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:_pageH2Short">Find the entity _pageH2Short in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void __pageH2Short(Wrap<String> c);

	public String get_pageH2Short() {
		return _pageH2Short;
	}
	public void set_pageH2Short(String o) {
		this._pageH2Short = PageLayout.staticSet_pageH2Short(siteRequest_, o);
		this._pageH2ShortWrap.alreadyInitialized = true;
	}
	public static String staticSet_pageH2Short(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout _pageH2ShortInit() {
		if(!_pageH2ShortWrap.alreadyInitialized) {
			__pageH2Short(_pageH2ShortWrap);
			if(_pageH2Short == null)
				set_pageH2Short(_pageH2ShortWrap.o);
		}
		_pageH2ShortWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolr_pageH2Short(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStr_pageH2Short(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFq_pageH2Short(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStr_pageH2Short(siteRequest_, PageLayout.staticSolr_pageH2Short(siteRequest_, PageLayout.staticSet_pageH2Short(siteRequest_, o)));
	}

	public String solr_pageH2Short() {
		return PageLayout.staticSolr_pageH2Short(siteRequest_, _pageH2Short);
	}

	public String str_pageH2Short() {
		return _pageH2Short == null ? "" : _pageH2Short;
	}

	public String sql_pageH2Short() {
		return _pageH2Short;
	}

	public String json_pageH2Short() {
		return _pageH2Short == null ? "" : _pageH2Short;
	}

	//////////////////
	// _pageH3Short //
	//////////////////

	/**	 The entity _pageH3Short
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String _pageH3Short;
	@JsonIgnore
	public Wrap<String> _pageH3ShortWrap = new Wrap<String>().p(this).c(String.class).var("_pageH3Short").o(_pageH3Short);

	/**	<br/> The entity _pageH3Short
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:_pageH3Short">Find the entity _pageH3Short in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void __pageH3Short(Wrap<String> c);

	public String get_pageH3Short() {
		return _pageH3Short;
	}
	public void set_pageH3Short(String o) {
		this._pageH3Short = PageLayout.staticSet_pageH3Short(siteRequest_, o);
		this._pageH3ShortWrap.alreadyInitialized = true;
	}
	public static String staticSet_pageH3Short(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout _pageH3ShortInit() {
		if(!_pageH3ShortWrap.alreadyInitialized) {
			__pageH3Short(_pageH3ShortWrap);
			if(_pageH3Short == null)
				set_pageH3Short(_pageH3ShortWrap.o);
		}
		_pageH3ShortWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolr_pageH3Short(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStr_pageH3Short(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFq_pageH3Short(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStr_pageH3Short(siteRequest_, PageLayout.staticSolr_pageH3Short(siteRequest_, PageLayout.staticSet_pageH3Short(siteRequest_, o)));
	}

	public String solr_pageH3Short() {
		return PageLayout.staticSolr_pageH3Short(siteRequest_, _pageH3Short);
	}

	public String str_pageH3Short() {
		return _pageH3Short == null ? "" : _pageH3Short;
	}

	public String sql_pageH3Short() {
		return _pageH3Short;
	}

	public String json_pageH3Short() {
		return _pageH3Short == null ? "" : _pageH3Short;
	}

	///////////////
	// pageTitle //
	///////////////

	/**	 The entity pageTitle
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageTitle;
	@JsonIgnore
	public Wrap<String> pageTitleWrap = new Wrap<String>().p(this).c(String.class).var("pageTitle").o(pageTitle);

	/**	<br/> The entity pageTitle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageTitle">Find the entity pageTitle in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageTitle(Wrap<String> c);

	public String getPageTitle() {
		return pageTitle;
	}
	public void setPageTitle(String o) {
		this.pageTitle = PageLayout.staticSetPageTitle(siteRequest_, o);
		this.pageTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetPageTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout pageTitleInit() {
		if(!pageTitleWrap.alreadyInitialized) {
			_pageTitle(pageTitleWrap);
			if(pageTitle == null)
				setPageTitle(pageTitleWrap.o);
		}
		pageTitleWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrPageTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageTitle(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageTitle(siteRequest_, PageLayout.staticSolrPageTitle(siteRequest_, PageLayout.staticSetPageTitle(siteRequest_, o)));
	}

	public String solrPageTitle() {
		return PageLayout.staticSolrPageTitle(siteRequest_, pageTitle);
	}

	public String strPageTitle() {
		return pageTitle == null ? "" : pageTitle;
	}

	public String sqlPageTitle() {
		return pageTitle;
	}

	public String jsonPageTitle() {
		return pageTitle == null ? "" : pageTitle;
	}

	/////////////
	// pageUri //
	/////////////

	/**	 The entity pageUri
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageUri;
	@JsonIgnore
	public Wrap<String> pageUriWrap = new Wrap<String>().p(this).c(String.class).var("pageUri").o(pageUri);

	/**	<br/> The entity pageUri
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageUri">Find the entity pageUri in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageUri(Wrap<String> c);

	public String getPageUri() {
		return pageUri;
	}
	public void setPageUri(String o) {
		this.pageUri = PageLayout.staticSetPageUri(siteRequest_, o);
		this.pageUriWrap.alreadyInitialized = true;
	}
	public static String staticSetPageUri(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout pageUriInit() {
		if(!pageUriWrap.alreadyInitialized) {
			_pageUri(pageUriWrap);
			if(pageUri == null)
				setPageUri(pageUriWrap.o);
		}
		pageUriWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrPageUri(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageUri(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageUri(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageUri(siteRequest_, PageLayout.staticSolrPageUri(siteRequest_, PageLayout.staticSetPageUri(siteRequest_, o)));
	}

	public String solrPageUri() {
		return PageLayout.staticSolrPageUri(siteRequest_, pageUri);
	}

	public String strPageUri() {
		return pageUri == null ? "" : pageUri;
	}

	public String sqlPageUri() {
		return pageUri;
	}

	public String jsonPageUri() {
		return pageUri == null ? "" : pageUri;
	}

	//////////////
	// pageUris //
	//////////////

	/**	 The entity pageUris
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<String>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected List<String> pageUris = new ArrayList<String>();
	@JsonIgnore
	public Wrap<List<String>> pageUrisWrap = new Wrap<List<String>>().p(this).c(List.class).var("pageUris").o(pageUris);

	/**	<br/> The entity pageUris
	 *  It is constructed before being initialized with the constructor by default List<String>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageUris">Find the entity pageUris in Solr</a>
	 * <br/>
	 * @param pageUris is the entity already constructed. 
	 **/
	protected abstract void _pageUris(List<String> l);

	public List<String> getPageUris() {
		return pageUris;
	}

	public void setPageUris(List<String> pageUris) {
		this.pageUris = pageUris;
		this.pageUrisWrap.alreadyInitialized = true;
	}
	public static String staticSetPageUris(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	public PageLayout addPageUris(String...objets) {
		for(String o : objets) {
			addPageUris(o);
		}
		return (PageLayout)this;
	}
	public PageLayout addPageUris(String o) {
		if(o != null && !pageUris.contains(o))
			this.pageUris.add(o);
		return (PageLayout)this;
	}
	public void setPageUris(JsonArray objets) {
		pageUris.clear();
		for(int i = 0; i < objets.size(); i++) {
			String o = objets.getString(i);
			addPageUris(o);
		}
	}
	protected PageLayout pageUrisInit() {
		if(!pageUrisWrap.alreadyInitialized) {
			_pageUris(pageUris);
		}
		pageUrisWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrPageUris(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageUris(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageUris(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageUris(siteRequest_, PageLayout.staticSolrPageUris(siteRequest_, PageLayout.staticSetPageUris(siteRequest_, o)));
	}

	public List<String> solrPageUris() {
		List<String> l = new ArrayList<String>();
		for(String o : pageUris) {
			l.add(PageLayout.staticSolrPageUris(siteRequest_, o));
		}
		return l;
	}

	public String strPageUris() {
		return pageUris == null ? "" : pageUris.toString();
	}

	public List<String> sqlPageUris() {
		return pageUris;
	}

	public String jsonPageUris() {
		return pageUris == null ? "" : pageUris.toString();
	}

	/////////////
	// pageUrl //
	/////////////

	/**	 The entity pageUrl
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageUrl;
	@JsonIgnore
	public Wrap<String> pageUrlWrap = new Wrap<String>().p(this).c(String.class).var("pageUrl").o(pageUrl);

	/**	<br/> The entity pageUrl
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageUrl">Find the entity pageUrl in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageUrl(Wrap<String> c);

	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String o) {
		this.pageUrl = PageLayout.staticSetPageUrl(siteRequest_, o);
		this.pageUrlWrap.alreadyInitialized = true;
	}
	public static String staticSetPageUrl(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout pageUrlInit() {
		if(!pageUrlWrap.alreadyInitialized) {
			_pageUrl(pageUrlWrap);
			if(pageUrl == null)
				setPageUrl(pageUrlWrap.o);
		}
		pageUrlWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrPageUrl(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageUrl(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageUrl(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageUrl(siteRequest_, PageLayout.staticSolrPageUrl(siteRequest_, PageLayout.staticSetPageUrl(siteRequest_, o)));
	}

	public String solrPageUrl() {
		return PageLayout.staticSolrPageUrl(siteRequest_, pageUrl);
	}

	public String strPageUrl() {
		return pageUrl == null ? "" : pageUrl;
	}

	public String sqlPageUrl() {
		return pageUrl;
	}

	public String jsonPageUrl() {
		return pageUrl == null ? "" : pageUrl;
	}

	//////////////////
	// pageImageUri //
	//////////////////

	/**	 The entity pageImageUri
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageImageUri;
	@JsonIgnore
	public Wrap<String> pageImageUriWrap = new Wrap<String>().p(this).c(String.class).var("pageImageUri").o(pageImageUri);

	/**	<br/> The entity pageImageUri
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageImageUri">Find the entity pageImageUri in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageImageUri(Wrap<String> c);

	public String getPageImageUri() {
		return pageImageUri;
	}
	public void setPageImageUri(String o) {
		this.pageImageUri = PageLayout.staticSetPageImageUri(siteRequest_, o);
		this.pageImageUriWrap.alreadyInitialized = true;
	}
	public static String staticSetPageImageUri(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout pageImageUriInit() {
		if(!pageImageUriWrap.alreadyInitialized) {
			_pageImageUri(pageImageUriWrap);
			if(pageImageUri == null)
				setPageImageUri(pageImageUriWrap.o);
		}
		pageImageUriWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrPageImageUri(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageImageUri(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageImageUri(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageImageUri(siteRequest_, PageLayout.staticSolrPageImageUri(siteRequest_, PageLayout.staticSetPageImageUri(siteRequest_, o)));
	}

	public String solrPageImageUri() {
		return PageLayout.staticSolrPageImageUri(siteRequest_, pageImageUri);
	}

	public String strPageImageUri() {
		return pageImageUri == null ? "" : pageImageUri;
	}

	public String sqlPageImageUri() {
		return pageImageUri;
	}

	public String jsonPageImageUri() {
		return pageImageUri == null ? "" : pageImageUri;
	}

	//////////////////
	// pageImageUrl //
	//////////////////

	/**	 The entity pageImageUrl
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageImageUrl;
	@JsonIgnore
	public Wrap<String> pageImageUrlWrap = new Wrap<String>().p(this).c(String.class).var("pageImageUrl").o(pageImageUrl);

	/**	<br/> The entity pageImageUrl
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageImageUrl">Find the entity pageImageUrl in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageImageUrl(Wrap<String> c);

	public String getPageImageUrl() {
		return pageImageUrl;
	}
	public void setPageImageUrl(String o) {
		this.pageImageUrl = PageLayout.staticSetPageImageUrl(siteRequest_, o);
		this.pageImageUrlWrap.alreadyInitialized = true;
	}
	public static String staticSetPageImageUrl(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout pageImageUrlInit() {
		if(!pageImageUrlWrap.alreadyInitialized) {
			_pageImageUrl(pageImageUrlWrap);
			if(pageImageUrl == null)
				setPageImageUrl(pageImageUrlWrap.o);
		}
		pageImageUrlWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrPageImageUrl(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageImageUrl(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageImageUrl(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageImageUrl(siteRequest_, PageLayout.staticSolrPageImageUrl(siteRequest_, PageLayout.staticSetPageImageUrl(siteRequest_, o)));
	}

	public String solrPageImageUrl() {
		return PageLayout.staticSolrPageImageUrl(siteRequest_, pageImageUrl);
	}

	public String strPageImageUrl() {
		return pageImageUrl == null ? "" : pageImageUrl;
	}

	public String sqlPageImageUrl() {
		return pageImageUrl;
	}

	public String jsonPageImageUrl() {
		return pageImageUrl == null ? "" : pageImageUrl;
	}

	/////////////////
	// pageVideoId //
	/////////////////

	/**	 The entity pageVideoId
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageVideoId;
	@JsonIgnore
	public Wrap<String> pageVideoIdWrap = new Wrap<String>().p(this).c(String.class).var("pageVideoId").o(pageVideoId);

	/**	<br/> The entity pageVideoId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageVideoId">Find the entity pageVideoId in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageVideoId(Wrap<String> c);

	public String getPageVideoId() {
		return pageVideoId;
	}
	public void setPageVideoId(String o) {
		this.pageVideoId = PageLayout.staticSetPageVideoId(siteRequest_, o);
		this.pageVideoIdWrap.alreadyInitialized = true;
	}
	public static String staticSetPageVideoId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout pageVideoIdInit() {
		if(!pageVideoIdWrap.alreadyInitialized) {
			_pageVideoId(pageVideoIdWrap);
			if(pageVideoId == null)
				setPageVideoId(pageVideoIdWrap.o);
		}
		pageVideoIdWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrPageVideoId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageVideoId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageVideoId(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageVideoId(siteRequest_, PageLayout.staticSolrPageVideoId(siteRequest_, PageLayout.staticSetPageVideoId(siteRequest_, o)));
	}

	public String solrPageVideoId() {
		return PageLayout.staticSolrPageVideoId(siteRequest_, pageVideoId);
	}

	public String strPageVideoId() {
		return pageVideoId == null ? "" : pageVideoId;
	}

	public String sqlPageVideoId() {
		return pageVideoId;
	}

	public String jsonPageVideoId() {
		return pageVideoId == null ? "" : pageVideoId;
	}

	//////////////////
	// pageVideoUrl //
	//////////////////

	/**	 The entity pageVideoUrl
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageVideoUrl;
	@JsonIgnore
	public Wrap<String> pageVideoUrlWrap = new Wrap<String>().p(this).c(String.class).var("pageVideoUrl").o(pageVideoUrl);

	/**	<br/> The entity pageVideoUrl
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageVideoUrl">Find the entity pageVideoUrl in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageVideoUrl(Wrap<String> c);

	public String getPageVideoUrl() {
		return pageVideoUrl;
	}
	public void setPageVideoUrl(String o) {
		this.pageVideoUrl = PageLayout.staticSetPageVideoUrl(siteRequest_, o);
		this.pageVideoUrlWrap.alreadyInitialized = true;
	}
	public static String staticSetPageVideoUrl(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout pageVideoUrlInit() {
		if(!pageVideoUrlWrap.alreadyInitialized) {
			_pageVideoUrl(pageVideoUrlWrap);
			if(pageVideoUrl == null)
				setPageVideoUrl(pageVideoUrlWrap.o);
		}
		pageVideoUrlWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrPageVideoUrl(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageVideoUrl(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageVideoUrl(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageVideoUrl(siteRequest_, PageLayout.staticSolrPageVideoUrl(siteRequest_, PageLayout.staticSetPageVideoUrl(siteRequest_, o)));
	}

	public String solrPageVideoUrl() {
		return PageLayout.staticSolrPageVideoUrl(siteRequest_, pageVideoUrl);
	}

	public String strPageVideoUrl() {
		return pageVideoUrl == null ? "" : pageVideoUrl;
	}

	public String sqlPageVideoUrl() {
		return pageVideoUrl;
	}

	public String jsonPageVideoUrl() {
		return pageVideoUrl == null ? "" : pageVideoUrl;
	}

	///////////////////////
	// pageVideoUrlEmbed //
	///////////////////////

	/**	 The entity pageVideoUrlEmbed
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageVideoUrlEmbed;
	@JsonIgnore
	public Wrap<String> pageVideoUrlEmbedWrap = new Wrap<String>().p(this).c(String.class).var("pageVideoUrlEmbed").o(pageVideoUrlEmbed);

	/**	<br/> The entity pageVideoUrlEmbed
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageVideoUrlEmbed">Find the entity pageVideoUrlEmbed in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageVideoUrlEmbed(Wrap<String> c);

	public String getPageVideoUrlEmbed() {
		return pageVideoUrlEmbed;
	}
	public void setPageVideoUrlEmbed(String o) {
		this.pageVideoUrlEmbed = PageLayout.staticSetPageVideoUrlEmbed(siteRequest_, o);
		this.pageVideoUrlEmbedWrap.alreadyInitialized = true;
	}
	public static String staticSetPageVideoUrlEmbed(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout pageVideoUrlEmbedInit() {
		if(!pageVideoUrlEmbedWrap.alreadyInitialized) {
			_pageVideoUrlEmbed(pageVideoUrlEmbedWrap);
			if(pageVideoUrlEmbed == null)
				setPageVideoUrlEmbed(pageVideoUrlEmbedWrap.o);
		}
		pageVideoUrlEmbedWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrPageVideoUrlEmbed(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageVideoUrlEmbed(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageVideoUrlEmbed(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageVideoUrlEmbed(siteRequest_, PageLayout.staticSolrPageVideoUrlEmbed(siteRequest_, PageLayout.staticSetPageVideoUrlEmbed(siteRequest_, o)));
	}

	public String solrPageVideoUrlEmbed() {
		return PageLayout.staticSolrPageVideoUrlEmbed(siteRequest_, pageVideoUrlEmbed);
	}

	public String strPageVideoUrlEmbed() {
		return pageVideoUrlEmbed == null ? "" : pageVideoUrlEmbed;
	}

	public String sqlPageVideoUrlEmbed() {
		return pageVideoUrlEmbed;
	}

	public String jsonPageVideoUrlEmbed() {
		return pageVideoUrlEmbed == null ? "" : pageVideoUrlEmbed;
	}

	////////////////////
	// pageImageWidth //
	////////////////////

	/**	 The entity pageImageWidth
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer pageImageWidth;
	@JsonIgnore
	public Wrap<Integer> pageImageWidthWrap = new Wrap<Integer>().p(this).c(Integer.class).var("pageImageWidth").o(pageImageWidth);

	/**	<br/> The entity pageImageWidth
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageImageWidth">Find the entity pageImageWidth in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageImageWidth(Wrap<Integer> c);

	public Integer getPageImageWidth() {
		return pageImageWidth;
	}

	public void setPageImageWidth(Integer pageImageWidth) {
		this.pageImageWidth = pageImageWidth;
		this.pageImageWidthWrap.alreadyInitialized = true;
	}
	public void setPageImageWidth(String o) {
		this.pageImageWidth = PageLayout.staticSetPageImageWidth(siteRequest_, o);
		this.pageImageWidthWrap.alreadyInitialized = true;
	}
	public static Integer staticSetPageImageWidth(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected PageLayout pageImageWidthInit() {
		if(!pageImageWidthWrap.alreadyInitialized) {
			_pageImageWidth(pageImageWidthWrap);
			if(pageImageWidth == null)
				setPageImageWidth(pageImageWidthWrap.o);
		}
		pageImageWidthWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static Integer staticSolrPageImageWidth(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrPageImageWidth(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageImageWidth(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageImageWidth(siteRequest_, PageLayout.staticSolrPageImageWidth(siteRequest_, PageLayout.staticSetPageImageWidth(siteRequest_, o)));
	}

	public Integer solrPageImageWidth() {
		return PageLayout.staticSolrPageImageWidth(siteRequest_, pageImageWidth);
	}

	public String strPageImageWidth() {
		return pageImageWidth == null ? "" : pageImageWidth.toString();
	}

	public Integer sqlPageImageWidth() {
		return pageImageWidth;
	}

	public String jsonPageImageWidth() {
		return pageImageWidth == null ? "" : pageImageWidth.toString();
	}

	/////////////////////
	// pageImageHeight //
	/////////////////////

	/**	 The entity pageImageHeight
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer pageImageHeight;
	@JsonIgnore
	public Wrap<Integer> pageImageHeightWrap = new Wrap<Integer>().p(this).c(Integer.class).var("pageImageHeight").o(pageImageHeight);

	/**	<br/> The entity pageImageHeight
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageImageHeight">Find the entity pageImageHeight in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageImageHeight(Wrap<Integer> c);

	public Integer getPageImageHeight() {
		return pageImageHeight;
	}

	public void setPageImageHeight(Integer pageImageHeight) {
		this.pageImageHeight = pageImageHeight;
		this.pageImageHeightWrap.alreadyInitialized = true;
	}
	public void setPageImageHeight(String o) {
		this.pageImageHeight = PageLayout.staticSetPageImageHeight(siteRequest_, o);
		this.pageImageHeightWrap.alreadyInitialized = true;
	}
	public static Integer staticSetPageImageHeight(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected PageLayout pageImageHeightInit() {
		if(!pageImageHeightWrap.alreadyInitialized) {
			_pageImageHeight(pageImageHeightWrap);
			if(pageImageHeight == null)
				setPageImageHeight(pageImageHeightWrap.o);
		}
		pageImageHeightWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static Integer staticSolrPageImageHeight(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrPageImageHeight(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageImageHeight(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageImageHeight(siteRequest_, PageLayout.staticSolrPageImageHeight(siteRequest_, PageLayout.staticSetPageImageHeight(siteRequest_, o)));
	}

	public Integer solrPageImageHeight() {
		return PageLayout.staticSolrPageImageHeight(siteRequest_, pageImageHeight);
	}

	public String strPageImageHeight() {
		return pageImageHeight == null ? "" : pageImageHeight.toString();
	}

	public Integer sqlPageImageHeight() {
		return pageImageHeight;
	}

	public String jsonPageImageHeight() {
		return pageImageHeight == null ? "" : pageImageHeight.toString();
	}

	//////////////////////////
	// pageImageContentType //
	//////////////////////////

	/**	 The entity pageImageContentType
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageImageContentType;
	@JsonIgnore
	public Wrap<String> pageImageContentTypeWrap = new Wrap<String>().p(this).c(String.class).var("pageImageContentType").o(pageImageContentType);

	/**	<br/> The entity pageImageContentType
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageImageContentType">Find the entity pageImageContentType in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageImageContentType(Wrap<String> c);

	public String getPageImageContentType() {
		return pageImageContentType;
	}
	public void setPageImageContentType(String o) {
		this.pageImageContentType = PageLayout.staticSetPageImageContentType(siteRequest_, o);
		this.pageImageContentTypeWrap.alreadyInitialized = true;
	}
	public static String staticSetPageImageContentType(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout pageImageContentTypeInit() {
		if(!pageImageContentTypeWrap.alreadyInitialized) {
			_pageImageContentType(pageImageContentTypeWrap);
			if(pageImageContentType == null)
				setPageImageContentType(pageImageContentTypeWrap.o);
		}
		pageImageContentTypeWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrPageImageContentType(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageImageContentType(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageImageContentType(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageImageContentType(siteRequest_, PageLayout.staticSolrPageImageContentType(siteRequest_, PageLayout.staticSetPageImageContentType(siteRequest_, o)));
	}

	public String solrPageImageContentType() {
		return PageLayout.staticSolrPageImageContentType(siteRequest_, pageImageContentType);
	}

	public String strPageImageContentType() {
		return pageImageContentType == null ? "" : pageImageContentType;
	}

	public String sqlPageImageContentType() {
		return pageImageContentType;
	}

	public String jsonPageImageContentType() {
		return pageImageContentType == null ? "" : pageImageContentType;
	}

	/////////////////////
	// pageContentType //
	/////////////////////

	/**	 The entity pageContentType
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageContentType;
	@JsonIgnore
	public Wrap<String> pageContentTypeWrap = new Wrap<String>().p(this).c(String.class).var("pageContentType").o(pageContentType);

	/**	<br/> The entity pageContentType
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageContentType">Find the entity pageContentType in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageContentType(Wrap<String> c);

	public String getPageContentType() {
		return pageContentType;
	}
	public void setPageContentType(String o) {
		this.pageContentType = PageLayout.staticSetPageContentType(siteRequest_, o);
		this.pageContentTypeWrap.alreadyInitialized = true;
	}
	public static String staticSetPageContentType(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout pageContentTypeInit() {
		if(!pageContentTypeWrap.alreadyInitialized) {
			_pageContentType(pageContentTypeWrap);
			if(pageContentType == null)
				setPageContentType(pageContentTypeWrap.o);
		}
		pageContentTypeWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrPageContentType(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageContentType(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageContentType(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageContentType(siteRequest_, PageLayout.staticSolrPageContentType(siteRequest_, PageLayout.staticSetPageContentType(siteRequest_, o)));
	}

	public String solrPageContentType() {
		return PageLayout.staticSolrPageContentType(siteRequest_, pageContentType);
	}

	public String strPageContentType() {
		return pageContentType == null ? "" : pageContentType;
	}

	public String sqlPageContentType() {
		return pageContentType;
	}

	public String jsonPageContentType() {
		return pageContentType == null ? "" : pageContentType;
	}

	/////////////////
	// pageCreated //
	/////////////////

	/**	 The entity pageCreated
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected LocalDateTime pageCreated;
	@JsonIgnore
	public Wrap<LocalDateTime> pageCreatedWrap = new Wrap<LocalDateTime>().p(this).c(LocalDateTime.class).var("pageCreated").o(pageCreated);

	/**	<br/> The entity pageCreated
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageCreated">Find the entity pageCreated in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageCreated(Wrap<LocalDateTime> c);

	public LocalDateTime getPageCreated() {
		return pageCreated;
	}

	public void setPageCreated(LocalDateTime pageCreated) {
		this.pageCreated = pageCreated;
		this.pageCreatedWrap.alreadyInitialized = true;
	}
	public void setPageCreated(Instant o) {
		this.pageCreated = o == null ? null : LocalDateTime.from(o).truncatedTo(ChronoUnit.MILLIS);
		this.pageCreatedWrap.alreadyInitialized = true;
	}
	/** Example: 2011-12-03T10:15:30+01:00 **/
	public void setPageCreated(String o) {
		this.pageCreated = PageLayout.staticSetPageCreated(siteRequest_, o);
		this.pageCreatedWrap.alreadyInitialized = true;
	}
	public static LocalDateTime staticSetPageCreated(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : LocalDateTime.parse(o, DateTimeFormatter.ISO_DATE_TIME).truncatedTo(ChronoUnit.MILLIS);
	}
	public void setPageCreated(Date o) {
		this.pageCreated = o == null ? null : LocalDateTime.ofInstant(o.toInstant(), ZoneId.of(siteRequest_.getConfig().getString(ConfigKeys.SITE_ZONE))).truncatedTo(ChronoUnit.MILLIS);
		this.pageCreatedWrap.alreadyInitialized = true;
	}
	protected PageLayout pageCreatedInit() {
		if(!pageCreatedWrap.alreadyInitialized) {
			_pageCreated(pageCreatedWrap);
			if(pageCreated == null)
				setPageCreated(pageCreatedWrap.o);
		}
		pageCreatedWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static Date staticSolrPageCreated(SiteRequestEnUS siteRequest_, LocalDateTime o) {
		return o == null ? null : Date.from(o.atZone(ZoneId.of(siteRequest_.getConfig().getString(ConfigKeys.SITE_ZONE))).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public static String staticSolrStrPageCreated(SiteRequestEnUS siteRequest_, Date o) {
		return "\"" + DateTimeFormatter.ISO_DATE_TIME.format(o.toInstant().atOffset(ZoneOffset.UTC)) + "\"";
	}

	public static String staticSolrFqPageCreated(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageCreated(siteRequest_, PageLayout.staticSolrPageCreated(siteRequest_, PageLayout.staticSetPageCreated(siteRequest_, o)));
	}

	public Date solrPageCreated() {
		return PageLayout.staticSolrPageCreated(siteRequest_, pageCreated);
	}

	public String strPageCreated() {
		return pageCreated == null ? "" : pageCreated.format(DateTimeFormatter.ofPattern("EEE MMM d, yyyy H:mm:ss a zz", Locale.forLanguageTag("en-US")));
	}

	public LocalDateTime sqlPageCreated() {
		return pageCreated;
	}

	public String jsonPageCreated() {
		return pageCreated == null ? "" : pageCreated.format(DateTimeFormatter.ISO_DATE_TIME);
	}

	//////////////////
	// pageModified //
	//////////////////

	/**	 The entity pageModified
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected LocalDateTime pageModified;
	@JsonIgnore
	public Wrap<LocalDateTime> pageModifiedWrap = new Wrap<LocalDateTime>().p(this).c(LocalDateTime.class).var("pageModified").o(pageModified);

	/**	<br/> The entity pageModified
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageModified">Find the entity pageModified in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageModified(Wrap<LocalDateTime> c);

	public LocalDateTime getPageModified() {
		return pageModified;
	}

	public void setPageModified(LocalDateTime pageModified) {
		this.pageModified = pageModified;
		this.pageModifiedWrap.alreadyInitialized = true;
	}
	public void setPageModified(Instant o) {
		this.pageModified = o == null ? null : LocalDateTime.from(o).truncatedTo(ChronoUnit.MILLIS);
		this.pageModifiedWrap.alreadyInitialized = true;
	}
	/** Example: 2011-12-03T10:15:30+01:00 **/
	public void setPageModified(String o) {
		this.pageModified = PageLayout.staticSetPageModified(siteRequest_, o);
		this.pageModifiedWrap.alreadyInitialized = true;
	}
	public static LocalDateTime staticSetPageModified(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : LocalDateTime.parse(o, DateTimeFormatter.ISO_DATE_TIME).truncatedTo(ChronoUnit.MILLIS);
	}
	public void setPageModified(Date o) {
		this.pageModified = o == null ? null : LocalDateTime.ofInstant(o.toInstant(), ZoneId.of(siteRequest_.getConfig().getString(ConfigKeys.SITE_ZONE))).truncatedTo(ChronoUnit.MILLIS);
		this.pageModifiedWrap.alreadyInitialized = true;
	}
	protected PageLayout pageModifiedInit() {
		if(!pageModifiedWrap.alreadyInitialized) {
			_pageModified(pageModifiedWrap);
			if(pageModified == null)
				setPageModified(pageModifiedWrap.o);
		}
		pageModifiedWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static Date staticSolrPageModified(SiteRequestEnUS siteRequest_, LocalDateTime o) {
		return o == null ? null : Date.from(o.atZone(ZoneId.of(siteRequest_.getConfig().getString(ConfigKeys.SITE_ZONE))).toInstant().atZone(ZoneId.of("Z")).toInstant());
	}

	public static String staticSolrStrPageModified(SiteRequestEnUS siteRequest_, Date o) {
		return "\"" + DateTimeFormatter.ISO_DATE_TIME.format(o.toInstant().atOffset(ZoneOffset.UTC)) + "\"";
	}

	public static String staticSolrFqPageModified(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageModified(siteRequest_, PageLayout.staticSolrPageModified(siteRequest_, PageLayout.staticSetPageModified(siteRequest_, o)));
	}

	public Date solrPageModified() {
		return PageLayout.staticSolrPageModified(siteRequest_, pageModified);
	}

	public String strPageModified() {
		return pageModified == null ? "" : pageModified.format(DateTimeFormatter.ofPattern("EEE MMM d, yyyy H:mm:ss a zz", Locale.forLanguageTag("en-US")));
	}

	public LocalDateTime sqlPageModified() {
		return pageModified;
	}

	public String jsonPageModified() {
		return pageModified == null ? "" : pageModified.format(DateTimeFormatter.ISO_DATE_TIME);
	}

	//////////////////
	// pageKeywords //
	//////////////////

	/**	 The entity pageKeywords
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageKeywords;
	@JsonIgnore
	public Wrap<String> pageKeywordsWrap = new Wrap<String>().p(this).c(String.class).var("pageKeywords").o(pageKeywords);

	/**	<br/> The entity pageKeywords
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageKeywords">Find the entity pageKeywords in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageKeywords(Wrap<String> c);

	public String getPageKeywords() {
		return pageKeywords;
	}
	public void setPageKeywords(String o) {
		this.pageKeywords = PageLayout.staticSetPageKeywords(siteRequest_, o);
		this.pageKeywordsWrap.alreadyInitialized = true;
	}
	public static String staticSetPageKeywords(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout pageKeywordsInit() {
		if(!pageKeywordsWrap.alreadyInitialized) {
			_pageKeywords(pageKeywordsWrap);
			if(pageKeywords == null)
				setPageKeywords(pageKeywordsWrap.o);
		}
		pageKeywordsWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrPageKeywords(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageKeywords(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageKeywords(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageKeywords(siteRequest_, PageLayout.staticSolrPageKeywords(siteRequest_, PageLayout.staticSetPageKeywords(siteRequest_, o)));
	}

	public String solrPageKeywords() {
		return PageLayout.staticSolrPageKeywords(siteRequest_, pageKeywords);
	}

	public String strPageKeywords() {
		return pageKeywords == null ? "" : pageKeywords;
	}

	public String sqlPageKeywords() {
		return pageKeywords;
	}

	public String jsonPageKeywords() {
		return pageKeywords == null ? "" : pageKeywords;
	}

	/////////////////////
	// pageDescription //
	/////////////////////

	/**	 The entity pageDescription
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageDescription;
	@JsonIgnore
	public Wrap<String> pageDescriptionWrap = new Wrap<String>().p(this).c(String.class).var("pageDescription").o(pageDescription);

	/**	<br/> The entity pageDescription
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageDescription">Find the entity pageDescription in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageDescription(Wrap<String> c);

	public String getPageDescription() {
		return pageDescription;
	}
	public void setPageDescription(String o) {
		this.pageDescription = PageLayout.staticSetPageDescription(siteRequest_, o);
		this.pageDescriptionWrap.alreadyInitialized = true;
	}
	public static String staticSetPageDescription(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout pageDescriptionInit() {
		if(!pageDescriptionWrap.alreadyInitialized) {
			_pageDescription(pageDescriptionWrap);
			if(pageDescription == null)
				setPageDescription(pageDescriptionWrap.o);
		}
		pageDescriptionWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrPageDescription(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageDescription(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageDescription(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageDescription(siteRequest_, PageLayout.staticSolrPageDescription(siteRequest_, PageLayout.staticSetPageDescription(siteRequest_, o)));
	}

	public String solrPageDescription() {
		return PageLayout.staticSolrPageDescription(siteRequest_, pageDescription);
	}

	public String strPageDescription() {
		return pageDescription == null ? "" : pageDescription;
	}

	public String sqlPageDescription() {
		return pageDescription;
	}

	public String jsonPageDescription() {
		return pageDescription == null ? "" : pageDescription;
	}

	/////////////////
	// pageHomeUri //
	/////////////////

	/**	 The entity pageHomeUri
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageHomeUri;
	@JsonIgnore
	public Wrap<String> pageHomeUriWrap = new Wrap<String>().p(this).c(String.class).var("pageHomeUri").o(pageHomeUri);

	/**	<br/> The entity pageHomeUri
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageHomeUri">Find the entity pageHomeUri in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageHomeUri(Wrap<String> c);

	public String getPageHomeUri() {
		return pageHomeUri;
	}
	public void setPageHomeUri(String o) {
		this.pageHomeUri = PageLayout.staticSetPageHomeUri(siteRequest_, o);
		this.pageHomeUriWrap.alreadyInitialized = true;
	}
	public static String staticSetPageHomeUri(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout pageHomeUriInit() {
		if(!pageHomeUriWrap.alreadyInitialized) {
			_pageHomeUri(pageHomeUriWrap);
			if(pageHomeUri == null)
				setPageHomeUri(pageHomeUriWrap.o);
		}
		pageHomeUriWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrPageHomeUri(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageHomeUri(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageHomeUri(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageHomeUri(siteRequest_, PageLayout.staticSolrPageHomeUri(siteRequest_, PageLayout.staticSetPageHomeUri(siteRequest_, o)));
	}

	public String solrPageHomeUri() {
		return PageLayout.staticSolrPageHomeUri(siteRequest_, pageHomeUri);
	}

	public String strPageHomeUri() {
		return pageHomeUri == null ? "" : pageHomeUri;
	}

	public String sqlPageHomeUri() {
		return pageHomeUri;
	}

	public String jsonPageHomeUri() {
		return pageHomeUri == null ? "" : pageHomeUri;
	}

	///////////////////
	// pageSchoolUri //
	///////////////////

	/**	 The entity pageSchoolUri
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageSchoolUri;
	@JsonIgnore
	public Wrap<String> pageSchoolUriWrap = new Wrap<String>().p(this).c(String.class).var("pageSchoolUri").o(pageSchoolUri);

	/**	<br/> The entity pageSchoolUri
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageSchoolUri">Find the entity pageSchoolUri in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageSchoolUri(Wrap<String> c);

	public String getPageSchoolUri() {
		return pageSchoolUri;
	}
	public void setPageSchoolUri(String o) {
		this.pageSchoolUri = PageLayout.staticSetPageSchoolUri(siteRequest_, o);
		this.pageSchoolUriWrap.alreadyInitialized = true;
	}
	public static String staticSetPageSchoolUri(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout pageSchoolUriInit() {
		if(!pageSchoolUriWrap.alreadyInitialized) {
			_pageSchoolUri(pageSchoolUriWrap);
			if(pageSchoolUri == null)
				setPageSchoolUri(pageSchoolUriWrap.o);
		}
		pageSchoolUriWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrPageSchoolUri(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageSchoolUri(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageSchoolUri(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageSchoolUri(siteRequest_, PageLayout.staticSolrPageSchoolUri(siteRequest_, PageLayout.staticSetPageSchoolUri(siteRequest_, o)));
	}

	public String solrPageSchoolUri() {
		return PageLayout.staticSolrPageSchoolUri(siteRequest_, pageSchoolUri);
	}

	public String strPageSchoolUri() {
		return pageSchoolUri == null ? "" : pageSchoolUri;
	}

	public String sqlPageSchoolUri() {
		return pageSchoolUri;
	}

	public String jsonPageSchoolUri() {
		return pageSchoolUri == null ? "" : pageSchoolUri;
	}

	/////////////////
	// pageUserUri //
	/////////////////

	/**	 The entity pageUserUri
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageUserUri;
	@JsonIgnore
	public Wrap<String> pageUserUriWrap = new Wrap<String>().p(this).c(String.class).var("pageUserUri").o(pageUserUri);

	/**	<br/> The entity pageUserUri
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageUserUri">Find the entity pageUserUri in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageUserUri(Wrap<String> c);

	public String getPageUserUri() {
		return pageUserUri;
	}
	public void setPageUserUri(String o) {
		this.pageUserUri = PageLayout.staticSetPageUserUri(siteRequest_, o);
		this.pageUserUriWrap.alreadyInitialized = true;
	}
	public static String staticSetPageUserUri(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout pageUserUriInit() {
		if(!pageUserUriWrap.alreadyInitialized) {
			_pageUserUri(pageUserUriWrap);
			if(pageUserUri == null)
				setPageUserUri(pageUserUriWrap.o);
		}
		pageUserUriWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrPageUserUri(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageUserUri(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageUserUri(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageUserUri(siteRequest_, PageLayout.staticSolrPageUserUri(siteRequest_, PageLayout.staticSetPageUserUri(siteRequest_, o)));
	}

	public String solrPageUserUri() {
		return PageLayout.staticSolrPageUserUri(siteRequest_, pageUserUri);
	}

	public String strPageUserUri() {
		return pageUserUri == null ? "" : pageUserUri;
	}

	public String sqlPageUserUri() {
		return pageUserUri;
	}

	public String jsonPageUserUri() {
		return pageUserUri == null ? "" : pageUserUri;
	}

	///////////////////
	// pageLogoutUri //
	///////////////////

	/**	 The entity pageLogoutUri
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageLogoutUri;
	@JsonIgnore
	public Wrap<String> pageLogoutUriWrap = new Wrap<String>().p(this).c(String.class).var("pageLogoutUri").o(pageLogoutUri);

	/**	<br/> The entity pageLogoutUri
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.page.PageLayout&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageLogoutUri">Find the entity pageLogoutUri in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageLogoutUri(Wrap<String> c);

	public String getPageLogoutUri() {
		return pageLogoutUri;
	}
	public void setPageLogoutUri(String o) {
		this.pageLogoutUri = PageLayout.staticSetPageLogoutUri(siteRequest_, o);
		this.pageLogoutUriWrap.alreadyInitialized = true;
	}
	public static String staticSetPageLogoutUri(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageLayout pageLogoutUriInit() {
		if(!pageLogoutUriWrap.alreadyInitialized) {
			_pageLogoutUri(pageLogoutUriWrap);
			if(pageLogoutUri == null)
				setPageLogoutUri(pageLogoutUriWrap.o);
		}
		pageLogoutUriWrap.alreadyInitialized(true);
		return (PageLayout)this;
	}

	public static String staticSolrPageLogoutUri(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageLogoutUri(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageLogoutUri(SiteRequestEnUS siteRequest_, String o) {
		return PageLayout.staticSolrStrPageLogoutUri(siteRequest_, PageLayout.staticSolrPageLogoutUri(siteRequest_, PageLayout.staticSetPageLogoutUri(siteRequest_, o)));
	}

	public String solrPageLogoutUri() {
		return PageLayout.staticSolrPageLogoutUri(siteRequest_, pageLogoutUri);
	}

	public String strPageLogoutUri() {
		return pageLogoutUri == null ? "" : pageLogoutUri;
	}

	public String sqlPageLogoutUri() {
		return pageLogoutUri;
	}

	public String jsonPageLogoutUri() {
		return pageLogoutUri == null ? "" : pageLogoutUri;
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedPageLayout = false;

	public PageLayout initDeepPageLayout(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedPageLayout) {
			alreadyInitializedPageLayout = true;
			initDeepPageLayout();
		}
		return (PageLayout)this;
	}

	public void initDeepPageLayout() {
		initPageLayout();
	}

	public void initPageLayout() {
		siteRequest_Init();
		siteBaseUrlInit();
		staticBaseUrlInit();
		mapInit();
		pageSolrDocumentInit();
		wInit();
		contextIconGroupInit();
		contextIconNameInit();
		contextIconCssClassesInit();
		pageVisibleToBotsInit();
		pageH1Init();
		pageH2Init();
		pageH3Init();
		_pageH1ShortInit();
		_pageH2ShortInit();
		_pageH3ShortInit();
		pageTitleInit();
		pageUriInit();
		pageUrisInit();
		pageUrlInit();
		pageImageUriInit();
		pageImageUrlInit();
		pageVideoIdInit();
		pageVideoUrlInit();
		pageVideoUrlEmbedInit();
		pageImageWidthInit();
		pageImageHeightInit();
		pageImageContentTypeInit();
		pageContentTypeInit();
		pageCreatedInit();
		pageModifiedInit();
		pageKeywordsInit();
		pageDescriptionInit();
		pageHomeUriInit();
		pageSchoolUriInit();
		pageUserUriInit();
		pageLogoutUriInit();
	}

	public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepPageLayout(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestPageLayout(SiteRequestEnUS siteRequest_) {
		if(w != null)
			w.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestPageLayout(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainPageLayout(v);
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
	public Object obtainPageLayout(String var) {
		PageLayout oPageLayout = (PageLayout)this;
		switch(var) {
			case "siteRequest_":
				return oPageLayout.siteRequest_;
			case "siteBaseUrl":
				return oPageLayout.siteBaseUrl;
			case "staticBaseUrl":
				return oPageLayout.staticBaseUrl;
			case "map":
				return oPageLayout.map;
			case "pageSolrDocument":
				return oPageLayout.pageSolrDocument;
			case "w":
				return oPageLayout.w;
			case "contextIconGroup":
				return oPageLayout.contextIconGroup;
			case "contextIconName":
				return oPageLayout.contextIconName;
			case "contextIconCssClasses":
				return oPageLayout.contextIconCssClasses;
			case "pageVisibleToBots":
				return oPageLayout.pageVisibleToBots;
			case "pageH1":
				return oPageLayout.pageH1;
			case "pageH2":
				return oPageLayout.pageH2;
			case "pageH3":
				return oPageLayout.pageH3;
			case "_pageH1Short":
				return oPageLayout._pageH1Short;
			case "_pageH2Short":
				return oPageLayout._pageH2Short;
			case "_pageH3Short":
				return oPageLayout._pageH3Short;
			case "pageTitle":
				return oPageLayout.pageTitle;
			case "pageUri":
				return oPageLayout.pageUri;
			case "pageUris":
				return oPageLayout.pageUris;
			case "pageUrl":
				return oPageLayout.pageUrl;
			case "pageImageUri":
				return oPageLayout.pageImageUri;
			case "pageImageUrl":
				return oPageLayout.pageImageUrl;
			case "pageVideoId":
				return oPageLayout.pageVideoId;
			case "pageVideoUrl":
				return oPageLayout.pageVideoUrl;
			case "pageVideoUrlEmbed":
				return oPageLayout.pageVideoUrlEmbed;
			case "pageImageWidth":
				return oPageLayout.pageImageWidth;
			case "pageImageHeight":
				return oPageLayout.pageImageHeight;
			case "pageImageContentType":
				return oPageLayout.pageImageContentType;
			case "pageContentType":
				return oPageLayout.pageContentType;
			case "pageCreated":
				return oPageLayout.pageCreated;
			case "pageModified":
				return oPageLayout.pageModified;
			case "pageKeywords":
				return oPageLayout.pageKeywords;
			case "pageDescription":
				return oPageLayout.pageDescription;
			case "pageHomeUri":
				return oPageLayout.pageHomeUri;
			case "pageSchoolUri":
				return oPageLayout.pageSchoolUri;
			case "pageUserUri":
				return oPageLayout.pageUserUri;
			case "pageLogoutUri":
				return oPageLayout.pageLogoutUri;
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
				o = attributePageLayout(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributePageLayout(String var, Object val) {
		PageLayout oPageLayout = (PageLayout)this;
		switch(var) {
			default:
				return null;
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetPageLayout(entityVar,  siteRequest_, o);
	}
	public static Object staticSetPageLayout(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "siteBaseUrl":
			return PageLayout.staticSetSiteBaseUrl(siteRequest_, o);
		case "staticBaseUrl":
			return PageLayout.staticSetStaticBaseUrl(siteRequest_, o);
		case "contextIconGroup":
			return PageLayout.staticSetContextIconGroup(siteRequest_, o);
		case "contextIconName":
			return PageLayout.staticSetContextIconName(siteRequest_, o);
		case "contextIconCssClasses":
			return PageLayout.staticSetContextIconCssClasses(siteRequest_, o);
		case "pageVisibleToBots":
			return PageLayout.staticSetPageVisibleToBots(siteRequest_, o);
		case "pageH1":
			return PageLayout.staticSetPageH1(siteRequest_, o);
		case "pageH2":
			return PageLayout.staticSetPageH2(siteRequest_, o);
		case "pageH3":
			return PageLayout.staticSetPageH3(siteRequest_, o);
		case "_pageH1Short":
			return PageLayout.staticSet_pageH1Short(siteRequest_, o);
		case "_pageH2Short":
			return PageLayout.staticSet_pageH2Short(siteRequest_, o);
		case "_pageH3Short":
			return PageLayout.staticSet_pageH3Short(siteRequest_, o);
		case "pageTitle":
			return PageLayout.staticSetPageTitle(siteRequest_, o);
		case "pageUri":
			return PageLayout.staticSetPageUri(siteRequest_, o);
		case "pageUris":
			return PageLayout.staticSetPageUris(siteRequest_, o);
		case "pageUrl":
			return PageLayout.staticSetPageUrl(siteRequest_, o);
		case "pageImageUri":
			return PageLayout.staticSetPageImageUri(siteRequest_, o);
		case "pageImageUrl":
			return PageLayout.staticSetPageImageUrl(siteRequest_, o);
		case "pageVideoId":
			return PageLayout.staticSetPageVideoId(siteRequest_, o);
		case "pageVideoUrl":
			return PageLayout.staticSetPageVideoUrl(siteRequest_, o);
		case "pageVideoUrlEmbed":
			return PageLayout.staticSetPageVideoUrlEmbed(siteRequest_, o);
		case "pageImageWidth":
			return PageLayout.staticSetPageImageWidth(siteRequest_, o);
		case "pageImageHeight":
			return PageLayout.staticSetPageImageHeight(siteRequest_, o);
		case "pageImageContentType":
			return PageLayout.staticSetPageImageContentType(siteRequest_, o);
		case "pageContentType":
			return PageLayout.staticSetPageContentType(siteRequest_, o);
		case "pageCreated":
			return PageLayout.staticSetPageCreated(siteRequest_, o);
		case "pageModified":
			return PageLayout.staticSetPageModified(siteRequest_, o);
		case "pageKeywords":
			return PageLayout.staticSetPageKeywords(siteRequest_, o);
		case "pageDescription":
			return PageLayout.staticSetPageDescription(siteRequest_, o);
		case "pageHomeUri":
			return PageLayout.staticSetPageHomeUri(siteRequest_, o);
		case "pageSchoolUri":
			return PageLayout.staticSetPageSchoolUri(siteRequest_, o);
		case "pageUserUri":
			return PageLayout.staticSetPageUserUri(siteRequest_, o);
		case "pageLogoutUri":
			return PageLayout.staticSetPageLogoutUri(siteRequest_, o);
			default:
				return null;
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrPageLayout(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrPageLayout(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "siteBaseUrl":
			return PageLayout.staticSolrSiteBaseUrl(siteRequest_, (String)o);
		case "staticBaseUrl":
			return PageLayout.staticSolrStaticBaseUrl(siteRequest_, (String)o);
		case "contextIconGroup":
			return PageLayout.staticSolrContextIconGroup(siteRequest_, (String)o);
		case "contextIconName":
			return PageLayout.staticSolrContextIconName(siteRequest_, (String)o);
		case "contextIconCssClasses":
			return PageLayout.staticSolrContextIconCssClasses(siteRequest_, (String)o);
		case "pageVisibleToBots":
			return PageLayout.staticSolrPageVisibleToBots(siteRequest_, (Boolean)o);
		case "pageH1":
			return PageLayout.staticSolrPageH1(siteRequest_, (String)o);
		case "pageH2":
			return PageLayout.staticSolrPageH2(siteRequest_, (String)o);
		case "pageH3":
			return PageLayout.staticSolrPageH3(siteRequest_, (String)o);
		case "_pageH1Short":
			return PageLayout.staticSolr_pageH1Short(siteRequest_, (String)o);
		case "_pageH2Short":
			return PageLayout.staticSolr_pageH2Short(siteRequest_, (String)o);
		case "_pageH3Short":
			return PageLayout.staticSolr_pageH3Short(siteRequest_, (String)o);
		case "pageTitle":
			return PageLayout.staticSolrPageTitle(siteRequest_, (String)o);
		case "pageUri":
			return PageLayout.staticSolrPageUri(siteRequest_, (String)o);
		case "pageUris":
			return PageLayout.staticSolrPageUris(siteRequest_, (String)o);
		case "pageUrl":
			return PageLayout.staticSolrPageUrl(siteRequest_, (String)o);
		case "pageImageUri":
			return PageLayout.staticSolrPageImageUri(siteRequest_, (String)o);
		case "pageImageUrl":
			return PageLayout.staticSolrPageImageUrl(siteRequest_, (String)o);
		case "pageVideoId":
			return PageLayout.staticSolrPageVideoId(siteRequest_, (String)o);
		case "pageVideoUrl":
			return PageLayout.staticSolrPageVideoUrl(siteRequest_, (String)o);
		case "pageVideoUrlEmbed":
			return PageLayout.staticSolrPageVideoUrlEmbed(siteRequest_, (String)o);
		case "pageImageWidth":
			return PageLayout.staticSolrPageImageWidth(siteRequest_, (Integer)o);
		case "pageImageHeight":
			return PageLayout.staticSolrPageImageHeight(siteRequest_, (Integer)o);
		case "pageImageContentType":
			return PageLayout.staticSolrPageImageContentType(siteRequest_, (String)o);
		case "pageContentType":
			return PageLayout.staticSolrPageContentType(siteRequest_, (String)o);
		case "pageCreated":
			return PageLayout.staticSolrPageCreated(siteRequest_, (LocalDateTime)o);
		case "pageModified":
			return PageLayout.staticSolrPageModified(siteRequest_, (LocalDateTime)o);
		case "pageKeywords":
			return PageLayout.staticSolrPageKeywords(siteRequest_, (String)o);
		case "pageDescription":
			return PageLayout.staticSolrPageDescription(siteRequest_, (String)o);
		case "pageHomeUri":
			return PageLayout.staticSolrPageHomeUri(siteRequest_, (String)o);
		case "pageSchoolUri":
			return PageLayout.staticSolrPageSchoolUri(siteRequest_, (String)o);
		case "pageUserUri":
			return PageLayout.staticSolrPageUserUri(siteRequest_, (String)o);
		case "pageLogoutUri":
			return PageLayout.staticSolrPageLogoutUri(siteRequest_, (String)o);
			default:
				return null;
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrPageLayout(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrPageLayout(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "siteBaseUrl":
			return PageLayout.staticSolrStrSiteBaseUrl(siteRequest_, (String)o);
		case "staticBaseUrl":
			return PageLayout.staticSolrStrStaticBaseUrl(siteRequest_, (String)o);
		case "contextIconGroup":
			return PageLayout.staticSolrStrContextIconGroup(siteRequest_, (String)o);
		case "contextIconName":
			return PageLayout.staticSolrStrContextIconName(siteRequest_, (String)o);
		case "contextIconCssClasses":
			return PageLayout.staticSolrStrContextIconCssClasses(siteRequest_, (String)o);
		case "pageVisibleToBots":
			return PageLayout.staticSolrStrPageVisibleToBots(siteRequest_, (Boolean)o);
		case "pageH1":
			return PageLayout.staticSolrStrPageH1(siteRequest_, (String)o);
		case "pageH2":
			return PageLayout.staticSolrStrPageH2(siteRequest_, (String)o);
		case "pageH3":
			return PageLayout.staticSolrStrPageH3(siteRequest_, (String)o);
		case "_pageH1Short":
			return PageLayout.staticSolrStr_pageH1Short(siteRequest_, (String)o);
		case "_pageH2Short":
			return PageLayout.staticSolrStr_pageH2Short(siteRequest_, (String)o);
		case "_pageH3Short":
			return PageLayout.staticSolrStr_pageH3Short(siteRequest_, (String)o);
		case "pageTitle":
			return PageLayout.staticSolrStrPageTitle(siteRequest_, (String)o);
		case "pageUri":
			return PageLayout.staticSolrStrPageUri(siteRequest_, (String)o);
		case "pageUris":
			return PageLayout.staticSolrStrPageUris(siteRequest_, (String)o);
		case "pageUrl":
			return PageLayout.staticSolrStrPageUrl(siteRequest_, (String)o);
		case "pageImageUri":
			return PageLayout.staticSolrStrPageImageUri(siteRequest_, (String)o);
		case "pageImageUrl":
			return PageLayout.staticSolrStrPageImageUrl(siteRequest_, (String)o);
		case "pageVideoId":
			return PageLayout.staticSolrStrPageVideoId(siteRequest_, (String)o);
		case "pageVideoUrl":
			return PageLayout.staticSolrStrPageVideoUrl(siteRequest_, (String)o);
		case "pageVideoUrlEmbed":
			return PageLayout.staticSolrStrPageVideoUrlEmbed(siteRequest_, (String)o);
		case "pageImageWidth":
			return PageLayout.staticSolrStrPageImageWidth(siteRequest_, (Integer)o);
		case "pageImageHeight":
			return PageLayout.staticSolrStrPageImageHeight(siteRequest_, (Integer)o);
		case "pageImageContentType":
			return PageLayout.staticSolrStrPageImageContentType(siteRequest_, (String)o);
		case "pageContentType":
			return PageLayout.staticSolrStrPageContentType(siteRequest_, (String)o);
		case "pageCreated":
			return PageLayout.staticSolrStrPageCreated(siteRequest_, (Date)o);
		case "pageModified":
			return PageLayout.staticSolrStrPageModified(siteRequest_, (Date)o);
		case "pageKeywords":
			return PageLayout.staticSolrStrPageKeywords(siteRequest_, (String)o);
		case "pageDescription":
			return PageLayout.staticSolrStrPageDescription(siteRequest_, (String)o);
		case "pageHomeUri":
			return PageLayout.staticSolrStrPageHomeUri(siteRequest_, (String)o);
		case "pageSchoolUri":
			return PageLayout.staticSolrStrPageSchoolUri(siteRequest_, (String)o);
		case "pageUserUri":
			return PageLayout.staticSolrStrPageUserUri(siteRequest_, (String)o);
		case "pageLogoutUri":
			return PageLayout.staticSolrStrPageLogoutUri(siteRequest_, (String)o);
			default:
				return null;
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqPageLayout(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqPageLayout(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "siteBaseUrl":
			return PageLayout.staticSolrFqSiteBaseUrl(siteRequest_, o);
		case "staticBaseUrl":
			return PageLayout.staticSolrFqStaticBaseUrl(siteRequest_, o);
		case "contextIconGroup":
			return PageLayout.staticSolrFqContextIconGroup(siteRequest_, o);
		case "contextIconName":
			return PageLayout.staticSolrFqContextIconName(siteRequest_, o);
		case "contextIconCssClasses":
			return PageLayout.staticSolrFqContextIconCssClasses(siteRequest_, o);
		case "pageVisibleToBots":
			return PageLayout.staticSolrFqPageVisibleToBots(siteRequest_, o);
		case "pageH1":
			return PageLayout.staticSolrFqPageH1(siteRequest_, o);
		case "pageH2":
			return PageLayout.staticSolrFqPageH2(siteRequest_, o);
		case "pageH3":
			return PageLayout.staticSolrFqPageH3(siteRequest_, o);
		case "_pageH1Short":
			return PageLayout.staticSolrFq_pageH1Short(siteRequest_, o);
		case "_pageH2Short":
			return PageLayout.staticSolrFq_pageH2Short(siteRequest_, o);
		case "_pageH3Short":
			return PageLayout.staticSolrFq_pageH3Short(siteRequest_, o);
		case "pageTitle":
			return PageLayout.staticSolrFqPageTitle(siteRequest_, o);
		case "pageUri":
			return PageLayout.staticSolrFqPageUri(siteRequest_, o);
		case "pageUris":
			return PageLayout.staticSolrFqPageUris(siteRequest_, o);
		case "pageUrl":
			return PageLayout.staticSolrFqPageUrl(siteRequest_, o);
		case "pageImageUri":
			return PageLayout.staticSolrFqPageImageUri(siteRequest_, o);
		case "pageImageUrl":
			return PageLayout.staticSolrFqPageImageUrl(siteRequest_, o);
		case "pageVideoId":
			return PageLayout.staticSolrFqPageVideoId(siteRequest_, o);
		case "pageVideoUrl":
			return PageLayout.staticSolrFqPageVideoUrl(siteRequest_, o);
		case "pageVideoUrlEmbed":
			return PageLayout.staticSolrFqPageVideoUrlEmbed(siteRequest_, o);
		case "pageImageWidth":
			return PageLayout.staticSolrFqPageImageWidth(siteRequest_, o);
		case "pageImageHeight":
			return PageLayout.staticSolrFqPageImageHeight(siteRequest_, o);
		case "pageImageContentType":
			return PageLayout.staticSolrFqPageImageContentType(siteRequest_, o);
		case "pageContentType":
			return PageLayout.staticSolrFqPageContentType(siteRequest_, o);
		case "pageCreated":
			return PageLayout.staticSolrFqPageCreated(siteRequest_, o);
		case "pageModified":
			return PageLayout.staticSolrFqPageModified(siteRequest_, o);
		case "pageKeywords":
			return PageLayout.staticSolrFqPageKeywords(siteRequest_, o);
		case "pageDescription":
			return PageLayout.staticSolrFqPageDescription(siteRequest_, o);
		case "pageHomeUri":
			return PageLayout.staticSolrFqPageHomeUri(siteRequest_, o);
		case "pageSchoolUri":
			return PageLayout.staticSolrFqPageSchoolUri(siteRequest_, o);
		case "pageUserUri":
			return PageLayout.staticSolrFqPageUserUri(siteRequest_, o);
		case "pageLogoutUri":
			return PageLayout.staticSolrFqPageLogoutUri(siteRequest_, o);
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
					o = definePageLayout(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object definePageLayout(String var, String val) {
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
					o = definePageLayout(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object definePageLayout(String var, Object val) {
		switch(var.toLowerCase()) {
			default:
				return null;
		}
	}

	//////////
	// html //
	//////////

	public void html() {
		htmlPageLayout();
	}

	public void htmlPageLayout() {
	}

	//////////////
	// htmlMeta //
	//////////////

	public void htmlMeta() {
		htmlMetaPageLayout();
	}

	public void htmlMetaPageLayout() {
	}

	/////////////////
	// htmlScripts //
	/////////////////

	public void htmlScripts() {
		htmlScriptsPageLayout();
	}

	public void htmlScriptsPageLayout() {
	}

	////////////////
	// htmlScript //
	////////////////

	public void htmlScript() {
		htmlScriptPageLayout();
	}

	public void htmlScriptPageLayout() {
	}

	////////////////
	// htmlStyles //
	////////////////

	public void htmlStyles() {
		htmlStylesPageLayout();
	}

	public void htmlStylesPageLayout() {
	}

	///////////////
	// htmlStyle //
	///////////////

	public void htmlStyle() {
		htmlStylePageLayout();
	}

	public void htmlStylePageLayout() {
	}

	//////////////
	// htmlBody //
	//////////////

	public void htmlBody() {
		htmlBodyPageLayout();
	}

	public void htmlBodyPageLayout() {
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestPageLayout() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof PageLayout) {
			PageLayout original = (PageLayout)o;
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
		if(!(o instanceof PageLayout))
			return false;
		PageLayout that = (PageLayout)o;
		return true;
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("PageLayout { ");
		sb.append(" }");
		return sb.toString();
	}
}
