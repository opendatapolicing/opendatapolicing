package com.opendatapolicing.enus.vertx;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.lang.Integer;
import java.text.NumberFormat;
import io.vertx.core.logging.LoggerFactory;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.String;
import io.vertx.core.logging.Logger;
import java.math.RoundingMode;
import com.opendatapolicing.enus.wrap.Wrap;
import java.math.MathContext;
import com.opendatapolicing.enus.writer.AllWriter;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.opendatapolicing.enus.request.api.ApiRequest;
import com.opendatapolicing.enus.context.SiteContextEnUS;
import java.io.File;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.lang.Object;
import com.opendatapolicing.enus.cluster.Cluster;
import com.opendatapolicing.enus.config.SiteConfig;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.vertx.AppSwagger2&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class AppSwagger2Gen<DEV> extends Object {
	protected static final Logger LOGGER = LoggerFactory.getLogger(AppSwagger2.class);

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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.vertx.AppSwagger2&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:siteRequest_">Find the entity siteRequest_ in Solr</a>
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
	protected AppSwagger2 siteRequest_Init() {
		if(!siteRequest_Wrap.alreadyInitialized) {
			_siteRequest_(siteRequest_Wrap);
			if(siteRequest_ == null)
				setSiteRequest_(siteRequest_Wrap.o);
		}
		siteRequest_Wrap.alreadyInitialized(true);
		return (AppSwagger2)this;
	}

	/////////////////
	// siteContext //
	/////////////////

	/**	 The entity siteContext
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut SiteContextEnUS(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SiteContextEnUS siteContext = new SiteContextEnUS();
	@JsonIgnore
	public Wrap<SiteContextEnUS> siteContextWrap = new Wrap<SiteContextEnUS>().p(this).c(SiteContextEnUS.class).var("siteContext").o(siteContext);

	/**	<br/> The entity siteContext
	 *  It is constructed before being initialized with the constructor by default SiteContextEnUS(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.vertx.AppSwagger2&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:siteContext">Find the entity siteContext in Solr</a>
	 * <br/>
	 * @param siteContext is the entity already constructed. 
	 **/
	protected abstract void _siteContext(SiteContextEnUS o);

	public SiteContextEnUS getSiteContext() {
		return siteContext;
	}

	public void setSiteContext(SiteContextEnUS siteContext) {
		this.siteContext = siteContext;
		this.siteContextWrap.alreadyInitialized = true;
	}
	public static SiteContextEnUS staticSetSiteContext(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected AppSwagger2 siteContextInit() {
		if(!siteContextWrap.alreadyInitialized) {
			_siteContext(siteContext);
		}
		siteContext.initDeepForClass(null);
		siteContextWrap.alreadyInitialized(true);
		return (AppSwagger2)this;
	}

	////////////////
	// siteConfig //
	////////////////

	/**	 The entity siteConfig
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SiteConfig siteConfig;
	@JsonIgnore
	public Wrap<SiteConfig> siteConfigWrap = new Wrap<SiteConfig>().p(this).c(SiteConfig.class).var("siteConfig").o(siteConfig);

	/**	<br/> The entity siteConfig
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.vertx.AppSwagger2&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:siteConfig">Find the entity siteConfig in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _siteConfig(Wrap<SiteConfig> c);

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
	protected AppSwagger2 siteConfigInit() {
		if(!siteConfigWrap.alreadyInitialized) {
			_siteConfig(siteConfigWrap);
			if(siteConfig == null)
				setSiteConfig(siteConfigWrap.o);
		}
		if(siteConfig != null)
			siteConfig.initDeepForClass(null);
		siteConfigWrap.alreadyInitialized(true);
		return (AppSwagger2)this;
	}

	/////////////
	// appPath //
	/////////////

	/**	 The entity appPath
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String appPath;
	@JsonIgnore
	public Wrap<String> appPathWrap = new Wrap<String>().p(this).c(String.class).var("appPath").o(appPath);

	/**	<br/> The entity appPath
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.vertx.AppSwagger2&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:appPath">Find the entity appPath in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _appPath(Wrap<String> c);

	public String getAppPath() {
		return appPath;
	}
	public void setAppPath(String o) {
		this.appPath = AppSwagger2.staticSetAppPath(null, o);
		this.appPathWrap.alreadyInitialized = true;
	}
	public static String staticSetAppPath(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected AppSwagger2 appPathInit() {
		if(!appPathWrap.alreadyInitialized) {
			_appPath(appPathWrap);
			if(appPath == null)
				setAppPath(appPathWrap.o);
		}
		appPathWrap.alreadyInitialized(true);
		return (AppSwagger2)this;
	}

	public static Object staticSolrAppPath(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrAppPath(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqAppPath(SiteRequestEnUS siteRequest_, String o) {
		return AppSwagger2.staticSolrStrAppPath(siteRequest_, AppSwagger2.staticSolrAppPath(siteRequest_, AppSwagger2.staticSetAppPath(siteRequest_, o)));
	}

	/////////////
	// appName //
	/////////////

	/**	 The entity appName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String appName;
	@JsonIgnore
	public Wrap<String> appNameWrap = new Wrap<String>().p(this).c(String.class).var("appName").o(appName);

	/**	<br/> The entity appName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.vertx.AppSwagger2&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:appName">Find the entity appName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _appName(Wrap<String> c);

	public String getAppName() {
		return appName;
	}
	public void setAppName(String o) {
		this.appName = AppSwagger2.staticSetAppName(null, o);
		this.appNameWrap.alreadyInitialized = true;
	}
	public static String staticSetAppName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected AppSwagger2 appNameInit() {
		if(!appNameWrap.alreadyInitialized) {
			_appName(appNameWrap);
			if(appName == null)
				setAppName(appNameWrap.o);
		}
		appNameWrap.alreadyInitialized(true);
		return (AppSwagger2)this;
	}

	public static Object staticSolrAppName(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrAppName(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqAppName(SiteRequestEnUS siteRequest_, String o) {
		return AppSwagger2.staticSolrStrAppName(siteRequest_, AppSwagger2.staticSolrAppName(siteRequest_, AppSwagger2.staticSetAppName(siteRequest_, o)));
	}

	//////////////////
	// languageName //
	//////////////////

	/**	 The entity languageName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String languageName;
	@JsonIgnore
	public Wrap<String> languageNameWrap = new Wrap<String>().p(this).c(String.class).var("languageName").o(languageName);

	/**	<br/> The entity languageName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.vertx.AppSwagger2&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:languageName">Find the entity languageName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _languageName(Wrap<String> c);

	public String getLanguageName() {
		return languageName;
	}
	public void setLanguageName(String o) {
		this.languageName = AppSwagger2.staticSetLanguageName(null, o);
		this.languageNameWrap.alreadyInitialized = true;
	}
	public static String staticSetLanguageName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected AppSwagger2 languageNameInit() {
		if(!languageNameWrap.alreadyInitialized) {
			_languageName(languageNameWrap);
			if(languageName == null)
				setLanguageName(languageNameWrap.o);
		}
		languageNameWrap.alreadyInitialized(true);
		return (AppSwagger2)this;
	}

	public static Object staticSolrLanguageName(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrLanguageName(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqLanguageName(SiteRequestEnUS siteRequest_, String o) {
		return AppSwagger2.staticSolrStrLanguageName(siteRequest_, AppSwagger2.staticSolrLanguageName(siteRequest_, AppSwagger2.staticSetLanguageName(siteRequest_, o)));
	}

	////////////////////
	// openApiVersion //
	////////////////////

	/**	 The entity openApiVersion
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String openApiVersion;
	@JsonIgnore
	public Wrap<String> openApiVersionWrap = new Wrap<String>().p(this).c(String.class).var("openApiVersion").o(openApiVersion);

	/**	<br/> The entity openApiVersion
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.vertx.AppSwagger2&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:openApiVersion">Find the entity openApiVersion in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _openApiVersion(Wrap<String> c);

	public String getOpenApiVersion() {
		return openApiVersion;
	}
	public void setOpenApiVersion(String o) {
		this.openApiVersion = AppSwagger2.staticSetOpenApiVersion(null, o);
		this.openApiVersionWrap.alreadyInitialized = true;
	}
	public static String staticSetOpenApiVersion(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected AppSwagger2 openApiVersionInit() {
		if(!openApiVersionWrap.alreadyInitialized) {
			_openApiVersion(openApiVersionWrap);
			if(openApiVersion == null)
				setOpenApiVersion(openApiVersionWrap.o);
		}
		openApiVersionWrap.alreadyInitialized(true);
		return (AppSwagger2)this;
	}

	public static Object staticSolrOpenApiVersion(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrOpenApiVersion(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqOpenApiVersion(SiteRequestEnUS siteRequest_, String o) {
		return AppSwagger2.staticSolrStrOpenApiVersion(siteRequest_, AppSwagger2.staticSolrOpenApiVersion(siteRequest_, AppSwagger2.staticSetOpenApiVersion(siteRequest_, o)));
	}

	//////////////////////////
	// openApiVersionNumber //
	//////////////////////////

	/**	 The entity openApiVersionNumber
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer openApiVersionNumber;
	@JsonIgnore
	public Wrap<Integer> openApiVersionNumberWrap = new Wrap<Integer>().p(this).c(Integer.class).var("openApiVersionNumber").o(openApiVersionNumber);

	/**	<br/> The entity openApiVersionNumber
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.vertx.AppSwagger2&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:openApiVersionNumber">Find the entity openApiVersionNumber in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _openApiVersionNumber(Wrap<Integer> c);

	public Integer getOpenApiVersionNumber() {
		return openApiVersionNumber;
	}

	public void setOpenApiVersionNumber(Integer openApiVersionNumber) {
		this.openApiVersionNumber = openApiVersionNumber;
		this.openApiVersionNumberWrap.alreadyInitialized = true;
	}
	public void setOpenApiVersionNumber(String o) {
		this.openApiVersionNumber = AppSwagger2.staticSetOpenApiVersionNumber(null, o);
		this.openApiVersionNumberWrap.alreadyInitialized = true;
	}
	public static Integer staticSetOpenApiVersionNumber(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected AppSwagger2 openApiVersionNumberInit() {
		if(!openApiVersionNumberWrap.alreadyInitialized) {
			_openApiVersionNumber(openApiVersionNumberWrap);
			if(openApiVersionNumber == null)
				setOpenApiVersionNumber(openApiVersionNumberWrap.o);
		}
		openApiVersionNumberWrap.alreadyInitialized(true);
		return (AppSwagger2)this;
	}

	public static Object staticSolrOpenApiVersionNumber(SiteRequestEnUS siteRequest_, Integer o) {
		return null;
	}

	public static String staticSolrStrOpenApiVersionNumber(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqOpenApiVersionNumber(SiteRequestEnUS siteRequest_, String o) {
		return AppSwagger2.staticSolrStrOpenApiVersionNumber(siteRequest_, AppSwagger2.staticSolrOpenApiVersionNumber(siteRequest_, AppSwagger2.staticSetOpenApiVersionNumber(siteRequest_, o)));
	}

	////////////////
	// tabsSchema //
	////////////////

	/**	 The entity tabsSchema
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer tabsSchema;
	@JsonIgnore
	public Wrap<Integer> tabsSchemaWrap = new Wrap<Integer>().p(this).c(Integer.class).var("tabsSchema").o(tabsSchema);

	/**	<br/> The entity tabsSchema
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.vertx.AppSwagger2&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:tabsSchema">Find the entity tabsSchema in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _tabsSchema(Wrap<Integer> c);

	public Integer getTabsSchema() {
		return tabsSchema;
	}

	public void setTabsSchema(Integer tabsSchema) {
		this.tabsSchema = tabsSchema;
		this.tabsSchemaWrap.alreadyInitialized = true;
	}
	public void setTabsSchema(String o) {
		this.tabsSchema = AppSwagger2.staticSetTabsSchema(null, o);
		this.tabsSchemaWrap.alreadyInitialized = true;
	}
	public static Integer staticSetTabsSchema(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected AppSwagger2 tabsSchemaInit() {
		if(!tabsSchemaWrap.alreadyInitialized) {
			_tabsSchema(tabsSchemaWrap);
			if(tabsSchema == null)
				setTabsSchema(tabsSchemaWrap.o);
		}
		tabsSchemaWrap.alreadyInitialized(true);
		return (AppSwagger2)this;
	}

	public static Object staticSolrTabsSchema(SiteRequestEnUS siteRequest_, Integer o) {
		return null;
	}

	public static String staticSolrStrTabsSchema(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqTabsSchema(SiteRequestEnUS siteRequest_, String o) {
		return AppSwagger2.staticSolrStrTabsSchema(siteRequest_, AppSwagger2.staticSolrTabsSchema(siteRequest_, AppSwagger2.staticSetTabsSchema(siteRequest_, o)));
	}

	////////////////
	// apiVersion //
	////////////////

	/**	 The entity apiVersion
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String apiVersion;
	@JsonIgnore
	public Wrap<String> apiVersionWrap = new Wrap<String>().p(this).c(String.class).var("apiVersion").o(apiVersion);

	/**	<br/> The entity apiVersion
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.vertx.AppSwagger2&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:apiVersion">Find the entity apiVersion in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _apiVersion(Wrap<String> c);

	public String getApiVersion() {
		return apiVersion;
	}
	public void setApiVersion(String o) {
		this.apiVersion = AppSwagger2.staticSetApiVersion(null, o);
		this.apiVersionWrap.alreadyInitialized = true;
	}
	public static String staticSetApiVersion(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected AppSwagger2 apiVersionInit() {
		if(!apiVersionWrap.alreadyInitialized) {
			_apiVersion(apiVersionWrap);
			if(apiVersion == null)
				setApiVersion(apiVersionWrap.o);
		}
		apiVersionWrap.alreadyInitialized(true);
		return (AppSwagger2)this;
	}

	public static Object staticSolrApiVersion(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrApiVersion(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqApiVersion(SiteRequestEnUS siteRequest_, String o) {
		return AppSwagger2.staticSolrStrApiVersion(siteRequest_, AppSwagger2.staticSolrApiVersion(siteRequest_, AppSwagger2.staticSetApiVersion(siteRequest_, o)));
	}

	/////////////////////
	// openApiYamlPath //
	/////////////////////

	/**	 The entity openApiYamlPath
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String openApiYamlPath;
	@JsonIgnore
	public Wrap<String> openApiYamlPathWrap = new Wrap<String>().p(this).c(String.class).var("openApiYamlPath").o(openApiYamlPath);

	/**	<br/> The entity openApiYamlPath
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.vertx.AppSwagger2&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:openApiYamlPath">Find the entity openApiYamlPath in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _openApiYamlPath(Wrap<String> c);

	public String getOpenApiYamlPath() {
		return openApiYamlPath;
	}
	public void setOpenApiYamlPath(String o) {
		this.openApiYamlPath = AppSwagger2.staticSetOpenApiYamlPath(null, o);
		this.openApiYamlPathWrap.alreadyInitialized = true;
	}
	public static String staticSetOpenApiYamlPath(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected AppSwagger2 openApiYamlPathInit() {
		if(!openApiYamlPathWrap.alreadyInitialized) {
			_openApiYamlPath(openApiYamlPathWrap);
			if(openApiYamlPath == null)
				setOpenApiYamlPath(openApiYamlPathWrap.o);
		}
		openApiYamlPathWrap.alreadyInitialized(true);
		return (AppSwagger2)this;
	}

	public static Object staticSolrOpenApiYamlPath(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrOpenApiYamlPath(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqOpenApiYamlPath(SiteRequestEnUS siteRequest_, String o) {
		return AppSwagger2.staticSolrStrOpenApiYamlPath(siteRequest_, AppSwagger2.staticSolrOpenApiYamlPath(siteRequest_, AppSwagger2.staticSetOpenApiYamlPath(siteRequest_, o)));
	}

	/////////////////////
	// openApiYamlFile //
	/////////////////////

	/**	 The entity openApiYamlFile
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected File openApiYamlFile;
	@JsonIgnore
	public Wrap<File> openApiYamlFileWrap = new Wrap<File>().p(this).c(File.class).var("openApiYamlFile").o(openApiYamlFile);

	/**	<br/> The entity openApiYamlFile
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.vertx.AppSwagger2&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:openApiYamlFile">Find the entity openApiYamlFile in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _openApiYamlFile(Wrap<File> c);

	public File getOpenApiYamlFile() {
		return openApiYamlFile;
	}

	public void setOpenApiYamlFile(File openApiYamlFile) {
		this.openApiYamlFile = openApiYamlFile;
		this.openApiYamlFileWrap.alreadyInitialized = true;
	}
	public static File staticSetOpenApiYamlFile(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected AppSwagger2 openApiYamlFileInit() {
		if(!openApiYamlFileWrap.alreadyInitialized) {
			_openApiYamlFile(openApiYamlFileWrap);
			if(openApiYamlFile == null)
				setOpenApiYamlFile(openApiYamlFileWrap.o);
		}
		openApiYamlFileWrap.alreadyInitialized(true);
		return (AppSwagger2)this;
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.vertx.AppSwagger2&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:w">Find the entity w in Solr</a>
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
	protected AppSwagger2 wInit() {
		if(!wWrap.alreadyInitialized) {
			_w(wWrap);
			if(w == null)
				setW(wWrap.o);
		}
		if(w != null)
			w.initDeepForClass(null);
		wWrap.alreadyInitialized(true);
		return (AppSwagger2)this;
	}

	////////////
	// wPaths //
	////////////

	/**	 The entity wPaths
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected AllWriter wPaths;
	@JsonIgnore
	public Wrap<AllWriter> wPathsWrap = new Wrap<AllWriter>().p(this).c(AllWriter.class).var("wPaths").o(wPaths);

	/**	<br/> The entity wPaths
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.vertx.AppSwagger2&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:wPaths">Find the entity wPaths in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _wPaths(Wrap<AllWriter> c);

	public AllWriter getWPaths() {
		return wPaths;
	}

	public void setWPaths(AllWriter wPaths) {
		this.wPaths = wPaths;
		this.wPathsWrap.alreadyInitialized = true;
	}
	public static AllWriter staticSetWPaths(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected AppSwagger2 wPathsInit() {
		if(!wPathsWrap.alreadyInitialized) {
			_wPaths(wPathsWrap);
			if(wPaths == null)
				setWPaths(wPathsWrap.o);
		}
		if(wPaths != null)
			wPaths.initDeepForClass(null);
		wPathsWrap.alreadyInitialized(true);
		return (AppSwagger2)this;
	}

	////////////////////
	// wRequestBodies //
	////////////////////

	/**	 The entity wRequestBodies
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected AllWriter wRequestBodies;
	@JsonIgnore
	public Wrap<AllWriter> wRequestBodiesWrap = new Wrap<AllWriter>().p(this).c(AllWriter.class).var("wRequestBodies").o(wRequestBodies);

	/**	<br/> The entity wRequestBodies
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.vertx.AppSwagger2&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:wRequestBodies">Find the entity wRequestBodies in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _wRequestBodies(Wrap<AllWriter> c);

	public AllWriter getWRequestBodies() {
		return wRequestBodies;
	}

	public void setWRequestBodies(AllWriter wRequestBodies) {
		this.wRequestBodies = wRequestBodies;
		this.wRequestBodiesWrap.alreadyInitialized = true;
	}
	public static AllWriter staticSetWRequestBodies(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected AppSwagger2 wRequestBodiesInit() {
		if(!wRequestBodiesWrap.alreadyInitialized) {
			_wRequestBodies(wRequestBodiesWrap);
			if(wRequestBodies == null)
				setWRequestBodies(wRequestBodiesWrap.o);
		}
		if(wRequestBodies != null)
			wRequestBodies.initDeepForClass(null);
		wRequestBodiesWrap.alreadyInitialized(true);
		return (AppSwagger2)this;
	}

	//////////////
	// wSchemas //
	//////////////

	/**	 The entity wSchemas
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected AllWriter wSchemas;
	@JsonIgnore
	public Wrap<AllWriter> wSchemasWrap = new Wrap<AllWriter>().p(this).c(AllWriter.class).var("wSchemas").o(wSchemas);

	/**	<br/> The entity wSchemas
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.vertx.AppSwagger2&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:wSchemas">Find the entity wSchemas in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _wSchemas(Wrap<AllWriter> c);

	public AllWriter getWSchemas() {
		return wSchemas;
	}

	public void setWSchemas(AllWriter wSchemas) {
		this.wSchemas = wSchemas;
		this.wSchemasWrap.alreadyInitialized = true;
	}
	public static AllWriter staticSetWSchemas(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected AppSwagger2 wSchemasInit() {
		if(!wSchemasWrap.alreadyInitialized) {
			_wSchemas(wSchemasWrap);
			if(wSchemas == null)
				setWSchemas(wSchemasWrap.o);
		}
		if(wSchemas != null)
			wSchemas.initDeepForClass(null);
		wSchemasWrap.alreadyInitialized(true);
		return (AppSwagger2)this;
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedAppSwagger2 = false;

	public AppSwagger2 initDeepAppSwagger2(SiteRequestEnUS siteRequest_) {
		if(!alreadyInitializedAppSwagger2) {
			alreadyInitializedAppSwagger2 = true;
			initDeepAppSwagger2();
		}
		return (AppSwagger2)this;
	}

	public void initDeepAppSwagger2() {
		initAppSwagger2();
	}

	public void initAppSwagger2() {
		siteRequest_Init();
		siteContextInit();
		siteConfigInit();
		appPathInit();
		appNameInit();
		languageNameInit();
		openApiVersionInit();
		openApiVersionNumberInit();
		tabsSchemaInit();
		apiVersionInit();
		openApiYamlPathInit();
		openApiYamlFileInit();
		wInit();
		wPathsInit();
		wRequestBodiesInit();
		wSchemasInit();
	}

	public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepAppSwagger2(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainAppSwagger2(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
		}
		return o;
	}
	public Object obtainAppSwagger2(String var) {
		AppSwagger2 oAppSwagger2 = (AppSwagger2)this;
		switch(var) {
			case "siteRequest_":
				return oAppSwagger2.siteRequest_;
			case "siteContext":
				return oAppSwagger2.siteContext;
			case "siteConfig":
				return oAppSwagger2.siteConfig;
			case "appPath":
				return oAppSwagger2.appPath;
			case "appName":
				return oAppSwagger2.appName;
			case "languageName":
				return oAppSwagger2.languageName;
			case "openApiVersion":
				return oAppSwagger2.openApiVersion;
			case "openApiVersionNumber":
				return oAppSwagger2.openApiVersionNumber;
			case "tabsSchema":
				return oAppSwagger2.tabsSchema;
			case "apiVersion":
				return oAppSwagger2.apiVersion;
			case "openApiYamlPath":
				return oAppSwagger2.openApiYamlPath;
			case "openApiYamlFile":
				return oAppSwagger2.openApiYamlFile;
			case "w":
				return oAppSwagger2.w;
			case "wPaths":
				return oAppSwagger2.wPaths;
			case "wRequestBodies":
				return oAppSwagger2.wRequestBodies;
			case "wSchemas":
				return oAppSwagger2.wSchemas;
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
				o = attributeAppSwagger2(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeAppSwagger2(String var, Object val) {
		AppSwagger2 oAppSwagger2 = (AppSwagger2)this;
		switch(var) {
			default:
				return null;
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetAppSwagger2(entityVar,  siteRequest_, o);
	}
	public static Object staticSetAppSwagger2(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "appPath":
			return AppSwagger2.staticSetAppPath(siteRequest_, o);
		case "appName":
			return AppSwagger2.staticSetAppName(siteRequest_, o);
		case "languageName":
			return AppSwagger2.staticSetLanguageName(siteRequest_, o);
		case "openApiVersion":
			return AppSwagger2.staticSetOpenApiVersion(siteRequest_, o);
		case "openApiVersionNumber":
			return AppSwagger2.staticSetOpenApiVersionNumber(siteRequest_, o);
		case "tabsSchema":
			return AppSwagger2.staticSetTabsSchema(siteRequest_, o);
		case "apiVersion":
			return AppSwagger2.staticSetApiVersion(siteRequest_, o);
		case "openApiYamlPath":
			return AppSwagger2.staticSetOpenApiYamlPath(siteRequest_, o);
			default:
				return null;
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrAppSwagger2(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrAppSwagger2(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "appPath":
			return AppSwagger2.staticSolrAppPath(siteRequest_, (String)o);
		case "appName":
			return AppSwagger2.staticSolrAppName(siteRequest_, (String)o);
		case "languageName":
			return AppSwagger2.staticSolrLanguageName(siteRequest_, (String)o);
		case "openApiVersion":
			return AppSwagger2.staticSolrOpenApiVersion(siteRequest_, (String)o);
		case "openApiVersionNumber":
			return AppSwagger2.staticSolrOpenApiVersionNumber(siteRequest_, (Integer)o);
		case "tabsSchema":
			return AppSwagger2.staticSolrTabsSchema(siteRequest_, (Integer)o);
		case "apiVersion":
			return AppSwagger2.staticSolrApiVersion(siteRequest_, (String)o);
		case "openApiYamlPath":
			return AppSwagger2.staticSolrOpenApiYamlPath(siteRequest_, (String)o);
			default:
				return null;
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrAppSwagger2(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrAppSwagger2(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "appPath":
			return AppSwagger2.staticSolrStrAppPath(siteRequest_, (String)o);
		case "appName":
			return AppSwagger2.staticSolrStrAppName(siteRequest_, (String)o);
		case "languageName":
			return AppSwagger2.staticSolrStrLanguageName(siteRequest_, (String)o);
		case "openApiVersion":
			return AppSwagger2.staticSolrStrOpenApiVersion(siteRequest_, (String)o);
		case "openApiVersionNumber":
			return AppSwagger2.staticSolrStrOpenApiVersionNumber(siteRequest_, (Integer)o);
		case "tabsSchema":
			return AppSwagger2.staticSolrStrTabsSchema(siteRequest_, (Integer)o);
		case "apiVersion":
			return AppSwagger2.staticSolrStrApiVersion(siteRequest_, (String)o);
		case "openApiYamlPath":
			return AppSwagger2.staticSolrStrOpenApiYamlPath(siteRequest_, (String)o);
			default:
				return null;
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqAppSwagger2(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqAppSwagger2(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "appPath":
			return AppSwagger2.staticSolrFqAppPath(siteRequest_, o);
		case "appName":
			return AppSwagger2.staticSolrFqAppName(siteRequest_, o);
		case "languageName":
			return AppSwagger2.staticSolrFqLanguageName(siteRequest_, o);
		case "openApiVersion":
			return AppSwagger2.staticSolrFqOpenApiVersion(siteRequest_, o);
		case "openApiVersionNumber":
			return AppSwagger2.staticSolrFqOpenApiVersionNumber(siteRequest_, o);
		case "tabsSchema":
			return AppSwagger2.staticSolrFqTabsSchema(siteRequest_, o);
		case "apiVersion":
			return AppSwagger2.staticSolrFqApiVersion(siteRequest_, o);
		case "openApiYamlPath":
			return AppSwagger2.staticSolrFqOpenApiYamlPath(siteRequest_, o);
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
					o = defineAppSwagger2(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineAppSwagger2(String var, String val) {
		switch(var) {
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
		if(!(o instanceof AppSwagger2))
			return false;
		AppSwagger2 that = (AppSwagger2)o;
		return true;
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("AppSwagger2 { ");
		sb.append(" }");
		return sb.toString();
	}
}
