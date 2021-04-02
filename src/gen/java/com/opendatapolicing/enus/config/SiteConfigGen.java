package com.opendatapolicing.enus.config;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.lang.Integer;
import java.text.NumberFormat;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.Boolean;
import io.vertx.core.json.JsonObject;
import java.lang.String;
import java.math.RoundingMode;
import com.opendatapolicing.enus.wrap.Wrap;
import org.slf4j.Logger;
import java.math.MathContext;
import com.opendatapolicing.enus.writer.AllWriter;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.opendatapolicing.enus.request.api.ApiRequest;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.lang.Object;
import com.opendatapolicing.enus.cluster.Cluster;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class SiteConfigGen<DEV> extends Object {
	protected static final Logger LOG = LoggerFactory.getLogger(SiteConfig.class);

	////////////////
	// configPath //
	////////////////

	/**	 The entity configPath
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String configPath;
	@JsonIgnore
	public Wrap<String> configPathWrap = new Wrap<String>().p(this).c(String.class).var("configPath").o(configPath);

	/**	<br/> The entity configPath
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:configPath">Find the entity configPath in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _configPath(Wrap<String> c);

	public String getConfigPath() {
		return configPath;
	}
	public void setConfigPath(String o) {
		this.configPath = SiteConfig.staticSetConfigPath(null, o);
		this.configPathWrap.alreadyInitialized = true;
	}
	public static String staticSetConfigPath(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig configPathInit() {
		if(!configPathWrap.alreadyInitialized) {
			_configPath(configPathWrap);
			if(configPath == null)
				setConfigPath(configPathWrap.o);
		}
		configPathWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrConfigPath(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrConfigPath(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqConfigPath(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrConfigPath(siteRequest_, SiteConfig.staticSolrConfigPath(siteRequest_, SiteConfig.staticSetConfigPath(siteRequest_, o)));
	}

	////////////
	// config //
	////////////

	/**	 The entity config
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected JsonObject config;
	@JsonIgnore
	public Wrap<JsonObject> configWrap = new Wrap<JsonObject>().p(this).c(JsonObject.class).var("config").o(config);

	/**	<br/> The entity config
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:config">Find the entity config in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _config(Wrap<JsonObject> c);

	public JsonObject getConfig() {
		return config;
	}

	public void setConfig(JsonObject config) {
		this.config = config;
		this.configWrap.alreadyInitialized = true;
	}
	public static JsonObject staticSetConfig(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteConfig configInit() {
		if(!configWrap.alreadyInitialized) {
			_config(configWrap);
			if(config == null)
				setConfig(configWrap.o);
		}
		configWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	////////////////////
	// siteIdentifier //
	////////////////////

	/**	 The entity siteIdentifier
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String siteIdentifier;
	@JsonIgnore
	public Wrap<String> siteIdentifierWrap = new Wrap<String>().p(this).c(String.class).var("siteIdentifier").o(siteIdentifier);

	/**	<br/> The entity siteIdentifier
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:siteIdentifier">Find the entity siteIdentifier in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _siteIdentifier(Wrap<String> c);

	public String getSiteIdentifier() {
		return siteIdentifier;
	}
	public void setSiteIdentifier(String o) {
		this.siteIdentifier = SiteConfig.staticSetSiteIdentifier(null, o);
		this.siteIdentifierWrap.alreadyInitialized = true;
	}
	public static String staticSetSiteIdentifier(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig siteIdentifierInit() {
		if(!siteIdentifierWrap.alreadyInitialized) {
			_siteIdentifier(siteIdentifierWrap);
			if(siteIdentifier == null)
				setSiteIdentifier(siteIdentifierWrap.o);
		}
		siteIdentifierWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrSiteIdentifier(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrSiteIdentifier(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqSiteIdentifier(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrSiteIdentifier(siteRequest_, SiteConfig.staticSolrSiteIdentifier(siteRequest_, SiteConfig.staticSetSiteIdentifier(siteRequest_, o)));
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:appPath">Find the entity appPath in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _appPath(Wrap<String> c);

	public String getAppPath() {
		return appPath;
	}
	public void setAppPath(String o) {
		this.appPath = SiteConfig.staticSetAppPath(null, o);
		this.appPathWrap.alreadyInitialized = true;
	}
	public static String staticSetAppPath(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig appPathInit() {
		if(!appPathWrap.alreadyInitialized) {
			_appPath(appPathWrap);
			if(appPath == null)
				setAppPath(appPathWrap.o);
		}
		appPathWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrAppPath(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrAppPath(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqAppPath(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrAppPath(siteRequest_, SiteConfig.staticSolrAppPath(siteRequest_, SiteConfig.staticSetAppPath(siteRequest_, o)));
	}

	/////////////
	// docRoot //
	/////////////

	/**	 The entity docRoot
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String docRoot;
	@JsonIgnore
	public Wrap<String> docRootWrap = new Wrap<String>().p(this).c(String.class).var("docRoot").o(docRoot);

	/**	<br/> The entity docRoot
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:docRoot">Find the entity docRoot in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _docRoot(Wrap<String> c);

	public String getDocRoot() {
		return docRoot;
	}
	public void setDocRoot(String o) {
		this.docRoot = SiteConfig.staticSetDocRoot(null, o);
		this.docRootWrap.alreadyInitialized = true;
	}
	public static String staticSetDocRoot(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig docRootInit() {
		if(!docRootWrap.alreadyInitialized) {
			_docRoot(docRootWrap);
			if(docRoot == null)
				setDocRoot(docRootWrap.o);
		}
		docRootWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrDocRoot(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrDocRoot(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqDocRoot(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrDocRoot(siteRequest_, SiteConfig.staticSolrDocRoot(siteRequest_, SiteConfig.staticSetDocRoot(siteRequest_, o)));
	}

	/////////////////
	// companyName //
	/////////////////

	/**	 The entity companyName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String companyName;
	@JsonIgnore
	public Wrap<String> companyNameWrap = new Wrap<String>().p(this).c(String.class).var("companyName").o(companyName);

	/**	<br/> The entity companyName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:companyName">Find the entity companyName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _companyName(Wrap<String> c);

	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String o) {
		this.companyName = SiteConfig.staticSetCompanyName(null, o);
		this.companyNameWrap.alreadyInitialized = true;
	}
	public static String staticSetCompanyName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig companyNameInit() {
		if(!companyNameWrap.alreadyInitialized) {
			_companyName(companyNameWrap);
			if(companyName == null)
				setCompanyName(companyNameWrap.o);
		}
		companyNameWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrCompanyName(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrCompanyName(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqCompanyName(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrCompanyName(siteRequest_, SiteConfig.staticSolrCompanyName(siteRequest_, SiteConfig.staticSetCompanyName(siteRequest_, o)));
	}

	////////////////
	// domainName //
	////////////////

	/**	 The entity domainName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String domainName;
	@JsonIgnore
	public Wrap<String> domainNameWrap = new Wrap<String>().p(this).c(String.class).var("domainName").o(domainName);

	/**	<br/> The entity domainName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:domainName">Find the entity domainName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _domainName(Wrap<String> c);

	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String o) {
		this.domainName = SiteConfig.staticSetDomainName(null, o);
		this.domainNameWrap.alreadyInitialized = true;
	}
	public static String staticSetDomainName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig domainNameInit() {
		if(!domainNameWrap.alreadyInitialized) {
			_domainName(domainNameWrap);
			if(domainName == null)
				setDomainName(domainNameWrap.o);
		}
		domainNameWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrDomainName(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrDomainName(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqDomainName(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrDomainName(siteRequest_, SiteConfig.staticSolrDomainName(siteRequest_, SiteConfig.staticSetDomainName(siteRequest_, o)));
	}

	//////////////////
	// siteHostName //
	//////////////////

	/**	 The entity siteHostName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String siteHostName;
	@JsonIgnore
	public Wrap<String> siteHostNameWrap = new Wrap<String>().p(this).c(String.class).var("siteHostName").o(siteHostName);

	/**	<br/> The entity siteHostName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:siteHostName">Find the entity siteHostName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _siteHostName(Wrap<String> c);

	public String getSiteHostName() {
		return siteHostName;
	}
	public void setSiteHostName(String o) {
		this.siteHostName = SiteConfig.staticSetSiteHostName(null, o);
		this.siteHostNameWrap.alreadyInitialized = true;
	}
	public static String staticSetSiteHostName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig siteHostNameInit() {
		if(!siteHostNameWrap.alreadyInitialized) {
			_siteHostName(siteHostNameWrap);
			if(siteHostName == null)
				setSiteHostName(siteHostNameWrap.o);
		}
		siteHostNameWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrSiteHostName(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrSiteHostName(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqSiteHostName(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrSiteHostName(siteRequest_, SiteConfig.staticSolrSiteHostName(siteRequest_, SiteConfig.staticSetSiteHostName(siteRequest_, o)));
	}

	//////////////
	// sitePort //
	//////////////

	/**	 The entity sitePort
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer sitePort;
	@JsonIgnore
	public Wrap<Integer> sitePortWrap = new Wrap<Integer>().p(this).c(Integer.class).var("sitePort").o(sitePort);

	/**	<br/> The entity sitePort
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sitePort">Find the entity sitePort in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _sitePort(Wrap<Integer> c);

	public Integer getSitePort() {
		return sitePort;
	}

	public void setSitePort(Integer sitePort) {
		this.sitePort = sitePort;
		this.sitePortWrap.alreadyInitialized = true;
	}
	public void setSitePort(String o) {
		this.sitePort = SiteConfig.staticSetSitePort(null, o);
		this.sitePortWrap.alreadyInitialized = true;
	}
	public static Integer staticSetSitePort(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected SiteConfig sitePortInit() {
		if(!sitePortWrap.alreadyInitialized) {
			_sitePort(sitePortWrap);
			if(sitePort == null)
				setSitePort(sitePortWrap.o);
		}
		sitePortWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrSitePort(SiteRequestEnUS siteRequest_, Integer o) {
		return null;
	}

	public static String staticSolrStrSitePort(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqSitePort(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrSitePort(siteRequest_, SiteConfig.staticSolrSitePort(siteRequest_, SiteConfig.staticSetSitePort(siteRequest_, o)));
	}

	///////////////////
	// siteInstances //
	///////////////////

	/**	 The entity siteInstances
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer siteInstances;
	@JsonIgnore
	public Wrap<Integer> siteInstancesWrap = new Wrap<Integer>().p(this).c(Integer.class).var("siteInstances").o(siteInstances);

	/**	<br/> The entity siteInstances
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:siteInstances">Find the entity siteInstances in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _siteInstances(Wrap<Integer> c);

	public Integer getSiteInstances() {
		return siteInstances;
	}

	public void setSiteInstances(Integer siteInstances) {
		this.siteInstances = siteInstances;
		this.siteInstancesWrap.alreadyInitialized = true;
	}
	public void setSiteInstances(String o) {
		this.siteInstances = SiteConfig.staticSetSiteInstances(null, o);
		this.siteInstancesWrap.alreadyInitialized = true;
	}
	public static Integer staticSetSiteInstances(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected SiteConfig siteInstancesInit() {
		if(!siteInstancesWrap.alreadyInitialized) {
			_siteInstances(siteInstancesWrap);
			if(siteInstances == null)
				setSiteInstances(siteInstancesWrap.o);
		}
		siteInstancesWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrSiteInstances(SiteRequestEnUS siteRequest_, Integer o) {
		return null;
	}

	public static String staticSolrStrSiteInstances(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqSiteInstances(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrSiteInstances(siteRequest_, SiteConfig.staticSolrSiteInstances(siteRequest_, SiteConfig.staticSetSiteInstances(siteRequest_, o)));
	}

	///////////////
	// authRealm //
	///////////////

	/**	 The entity authRealm
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String authRealm;
	@JsonIgnore
	public Wrap<String> authRealmWrap = new Wrap<String>().p(this).c(String.class).var("authRealm").o(authRealm);

	/**	<br/> The entity authRealm
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:authRealm">Find the entity authRealm in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _authRealm(Wrap<String> c);

	public String getAuthRealm() {
		return authRealm;
	}
	public void setAuthRealm(String o) {
		this.authRealm = SiteConfig.staticSetAuthRealm(null, o);
		this.authRealmWrap.alreadyInitialized = true;
	}
	public static String staticSetAuthRealm(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig authRealmInit() {
		if(!authRealmWrap.alreadyInitialized) {
			_authRealm(authRealmWrap);
			if(authRealm == null)
				setAuthRealm(authRealmWrap.o);
		}
		authRealmWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrAuthRealm(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrAuthRealm(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqAuthRealm(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrAuthRealm(siteRequest_, SiteConfig.staticSolrAuthRealm(siteRequest_, SiteConfig.staticSetAuthRealm(siteRequest_, o)));
	}

	//////////////////
	// authResource //
	//////////////////

	/**	 The entity authResource
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String authResource;
	@JsonIgnore
	public Wrap<String> authResourceWrap = new Wrap<String>().p(this).c(String.class).var("authResource").o(authResource);

	/**	<br/> The entity authResource
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:authResource">Find the entity authResource in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _authResource(Wrap<String> c);

	public String getAuthResource() {
		return authResource;
	}
	public void setAuthResource(String o) {
		this.authResource = SiteConfig.staticSetAuthResource(null, o);
		this.authResourceWrap.alreadyInitialized = true;
	}
	public static String staticSetAuthResource(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig authResourceInit() {
		if(!authResourceWrap.alreadyInitialized) {
			_authResource(authResourceWrap);
			if(authResource == null)
				setAuthResource(authResourceWrap.o);
		}
		authResourceWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrAuthResource(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrAuthResource(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqAuthResource(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrAuthResource(siteRequest_, SiteConfig.staticSolrAuthResource(siteRequest_, SiteConfig.staticSetAuthResource(siteRequest_, o)));
	}

	////////////////
	// authSecret //
	////////////////

	/**	 The entity authSecret
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String authSecret;
	@JsonIgnore
	public Wrap<String> authSecretWrap = new Wrap<String>().p(this).c(String.class).var("authSecret").o(authSecret);

	/**	<br/> The entity authSecret
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:authSecret">Find the entity authSecret in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _authSecret(Wrap<String> c);

	public String getAuthSecret() {
		return authSecret;
	}
	public void setAuthSecret(String o) {
		this.authSecret = SiteConfig.staticSetAuthSecret(null, o);
		this.authSecretWrap.alreadyInitialized = true;
	}
	public static String staticSetAuthSecret(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig authSecretInit() {
		if(!authSecretWrap.alreadyInitialized) {
			_authSecret(authSecretWrap);
			if(authSecret == null)
				setAuthSecret(authSecretWrap.o);
		}
		authSecretWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrAuthSecret(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrAuthSecret(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqAuthSecret(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrAuthSecret(siteRequest_, SiteConfig.staticSolrAuthSecret(siteRequest_, SiteConfig.staticSetAuthSecret(siteRequest_, o)));
	}

	/////////////////////
	// authSslRequired //
	/////////////////////

	/**	 The entity authSslRequired
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String authSslRequired;
	@JsonIgnore
	public Wrap<String> authSslRequiredWrap = new Wrap<String>().p(this).c(String.class).var("authSslRequired").o(authSslRequired);

	/**	<br/> The entity authSslRequired
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:authSslRequired">Find the entity authSslRequired in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _authSslRequired(Wrap<String> c);

	public String getAuthSslRequired() {
		return authSslRequired;
	}
	public void setAuthSslRequired(String o) {
		this.authSslRequired = SiteConfig.staticSetAuthSslRequired(null, o);
		this.authSslRequiredWrap.alreadyInitialized = true;
	}
	public static String staticSetAuthSslRequired(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig authSslRequiredInit() {
		if(!authSslRequiredWrap.alreadyInitialized) {
			_authSslRequired(authSslRequiredWrap);
			if(authSslRequired == null)
				setAuthSslRequired(authSslRequiredWrap.o);
		}
		authSslRequiredWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrAuthSslRequired(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrAuthSslRequired(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqAuthSslRequired(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrAuthSslRequired(siteRequest_, SiteConfig.staticSolrAuthSslRequired(siteRequest_, SiteConfig.staticSetAuthSslRequired(siteRequest_, o)));
	}

	////////////////////
	// sslPassthrough //
	////////////////////

	/**	 The entity sslPassthrough
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean sslPassthrough;
	@JsonIgnore
	public Wrap<Boolean> sslPassthroughWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("sslPassthrough").o(sslPassthrough);

	/**	<br/> The entity sslPassthrough
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sslPassthrough">Find the entity sslPassthrough in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _sslPassthrough(Wrap<Boolean> w);

	public Boolean getSslPassthrough() {
		return sslPassthrough;
	}

	public void setSslPassthrough(Boolean sslPassthrough) {
		this.sslPassthrough = sslPassthrough;
		this.sslPassthroughWrap.alreadyInitialized = true;
	}
	public void setSslPassthrough(String o) {
		this.sslPassthrough = SiteConfig.staticSetSslPassthrough(null, o);
		this.sslPassthroughWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSslPassthrough(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SiteConfig sslPassthroughInit() {
		if(!sslPassthroughWrap.alreadyInitialized) {
			_sslPassthrough(sslPassthroughWrap);
			if(sslPassthrough == null)
				setSslPassthrough(sslPassthroughWrap.o);
		}
		sslPassthroughWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrSslPassthrough(SiteRequestEnUS siteRequest_, Boolean o) {
		return null;
	}

	public static String staticSolrStrSslPassthrough(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqSslPassthrough(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrSslPassthrough(siteRequest_, SiteConfig.staticSolrSslPassthrough(siteRequest_, SiteConfig.staticSetSslPassthrough(siteRequest_, o)));
	}

	////////////////
	// sslJksPath //
	////////////////

	/**	 The entity sslJksPath
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String sslJksPath;
	@JsonIgnore
	public Wrap<String> sslJksPathWrap = new Wrap<String>().p(this).c(String.class).var("sslJksPath").o(sslJksPath);

	/**	<br/> The entity sslJksPath
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sslJksPath">Find the entity sslJksPath in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _sslJksPath(Wrap<String> c);

	public String getSslJksPath() {
		return sslJksPath;
	}
	public void setSslJksPath(String o) {
		this.sslJksPath = SiteConfig.staticSetSslJksPath(null, o);
		this.sslJksPathWrap.alreadyInitialized = true;
	}
	public static String staticSetSslJksPath(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig sslJksPathInit() {
		if(!sslJksPathWrap.alreadyInitialized) {
			_sslJksPath(sslJksPathWrap);
			if(sslJksPath == null)
				setSslJksPath(sslJksPathWrap.o);
		}
		sslJksPathWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrSslJksPath(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrSslJksPath(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqSslJksPath(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrSslJksPath(siteRequest_, SiteConfig.staticSolrSslJksPath(siteRequest_, SiteConfig.staticSetSslJksPath(siteRequest_, o)));
	}

	////////////////////
	// sslJksPassword //
	////////////////////

	/**	 The entity sslJksPassword
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String sslJksPassword;
	@JsonIgnore
	public Wrap<String> sslJksPasswordWrap = new Wrap<String>().p(this).c(String.class).var("sslJksPassword").o(sslJksPassword);

	/**	<br/> The entity sslJksPassword
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sslJksPassword">Find the entity sslJksPassword in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _sslJksPassword(Wrap<String> c);

	public String getSslJksPassword() {
		return sslJksPassword;
	}
	public void setSslJksPassword(String o) {
		this.sslJksPassword = SiteConfig.staticSetSslJksPassword(null, o);
		this.sslJksPasswordWrap.alreadyInitialized = true;
	}
	public static String staticSetSslJksPassword(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig sslJksPasswordInit() {
		if(!sslJksPasswordWrap.alreadyInitialized) {
			_sslJksPassword(sslJksPasswordWrap);
			if(sslJksPassword == null)
				setSslJksPassword(sslJksPasswordWrap.o);
		}
		sslJksPasswordWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrSslJksPassword(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrSslJksPassword(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqSslJksPassword(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrSslJksPassword(siteRequest_, SiteConfig.staticSolrSslJksPassword(siteRequest_, SiteConfig.staticSetSslJksPassword(siteRequest_, o)));
	}

	/////////////
	// authUrl //
	/////////////

	/**	 The entity authUrl
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String authUrl;
	@JsonIgnore
	public Wrap<String> authUrlWrap = new Wrap<String>().p(this).c(String.class).var("authUrl").o(authUrl);

	/**	<br/> The entity authUrl
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:authUrl">Find the entity authUrl in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _authUrl(Wrap<String> c);

	public String getAuthUrl() {
		return authUrl;
	}
	public void setAuthUrl(String o) {
		this.authUrl = SiteConfig.staticSetAuthUrl(null, o);
		this.authUrlWrap.alreadyInitialized = true;
	}
	public static String staticSetAuthUrl(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig authUrlInit() {
		if(!authUrlWrap.alreadyInitialized) {
			_authUrl(authUrlWrap);
			if(authUrl == null)
				setAuthUrl(authUrlWrap.o);
		}
		authUrlWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrAuthUrl(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrAuthUrl(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqAuthUrl(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrAuthUrl(siteRequest_, SiteConfig.staticSolrAuthUrl(siteRequest_, SiteConfig.staticSetAuthUrl(siteRequest_, o)));
	}

	////////////////////
	// encryptionSalt //
	////////////////////

	/**	 The entity encryptionSalt
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String encryptionSalt;
	@JsonIgnore
	public Wrap<String> encryptionSaltWrap = new Wrap<String>().p(this).c(String.class).var("encryptionSalt").o(encryptionSalt);

	/**	<br/> The entity encryptionSalt
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:encryptionSalt">Find the entity encryptionSalt in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _encryptionSalt(Wrap<String> c);

	public String getEncryptionSalt() {
		return encryptionSalt;
	}
	public void setEncryptionSalt(String o) {
		this.encryptionSalt = SiteConfig.staticSetEncryptionSalt(null, o);
		this.encryptionSaltWrap.alreadyInitialized = true;
	}
	public static String staticSetEncryptionSalt(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig encryptionSaltInit() {
		if(!encryptionSaltWrap.alreadyInitialized) {
			_encryptionSalt(encryptionSaltWrap);
			if(encryptionSalt == null)
				setEncryptionSalt(encryptionSaltWrap.o);
		}
		encryptionSaltWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrEncryptionSalt(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrEncryptionSalt(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqEncryptionSalt(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrEncryptionSalt(siteRequest_, SiteConfig.staticSolrEncryptionSalt(siteRequest_, SiteConfig.staticSetEncryptionSalt(siteRequest_, o)));
	}

	////////////////////////
	// encryptionPassword //
	////////////////////////

	/**	 The entity encryptionPassword
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String encryptionPassword;
	@JsonIgnore
	public Wrap<String> encryptionPasswordWrap = new Wrap<String>().p(this).c(String.class).var("encryptionPassword").o(encryptionPassword);

	/**	<br/> The entity encryptionPassword
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:encryptionPassword">Find the entity encryptionPassword in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _encryptionPassword(Wrap<String> c);

	public String getEncryptionPassword() {
		return encryptionPassword;
	}
	public void setEncryptionPassword(String o) {
		this.encryptionPassword = SiteConfig.staticSetEncryptionPassword(null, o);
		this.encryptionPasswordWrap.alreadyInitialized = true;
	}
	public static String staticSetEncryptionPassword(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig encryptionPasswordInit() {
		if(!encryptionPasswordWrap.alreadyInitialized) {
			_encryptionPassword(encryptionPasswordWrap);
			if(encryptionPassword == null)
				setEncryptionPassword(encryptionPasswordWrap.o);
		}
		encryptionPasswordWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrEncryptionPassword(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrEncryptionPassword(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqEncryptionPassword(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrEncryptionPassword(siteRequest_, SiteConfig.staticSolrEncryptionPassword(siteRequest_, SiteConfig.staticSetEncryptionPassword(siteRequest_, o)));
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:siteBaseUrl">Find the entity siteBaseUrl in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _siteBaseUrl(Wrap<String> c);

	public String getSiteBaseUrl() {
		return siteBaseUrl;
	}
	public void setSiteBaseUrl(String o) {
		this.siteBaseUrl = SiteConfig.staticSetSiteBaseUrl(null, o);
		this.siteBaseUrlWrap.alreadyInitialized = true;
	}
	public static String staticSetSiteBaseUrl(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig siteBaseUrlInit() {
		if(!siteBaseUrlWrap.alreadyInitialized) {
			_siteBaseUrl(siteBaseUrlWrap);
			if(siteBaseUrl == null)
				setSiteBaseUrl(siteBaseUrlWrap.o);
		}
		siteBaseUrlWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrSiteBaseUrl(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrSiteBaseUrl(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqSiteBaseUrl(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrSiteBaseUrl(siteRequest_, SiteConfig.staticSolrSiteBaseUrl(siteRequest_, SiteConfig.staticSetSiteBaseUrl(siteRequest_, o)));
	}

	/////////////////////
	// siteDisplayName //
	/////////////////////

	/**	 The entity siteDisplayName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String siteDisplayName;
	@JsonIgnore
	public Wrap<String> siteDisplayNameWrap = new Wrap<String>().p(this).c(String.class).var("siteDisplayName").o(siteDisplayName);

	/**	<br/> The entity siteDisplayName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:siteDisplayName">Find the entity siteDisplayName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _siteDisplayName(Wrap<String> c);

	public String getSiteDisplayName() {
		return siteDisplayName;
	}
	public void setSiteDisplayName(String o) {
		this.siteDisplayName = SiteConfig.staticSetSiteDisplayName(null, o);
		this.siteDisplayNameWrap.alreadyInitialized = true;
	}
	public static String staticSetSiteDisplayName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig siteDisplayNameInit() {
		if(!siteDisplayNameWrap.alreadyInitialized) {
			_siteDisplayName(siteDisplayNameWrap);
			if(siteDisplayName == null)
				setSiteDisplayName(siteDisplayNameWrap.o);
		}
		siteDisplayNameWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrSiteDisplayName(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrSiteDisplayName(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqSiteDisplayName(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrSiteDisplayName(siteRequest_, SiteConfig.staticSolrSiteDisplayName(siteRequest_, SiteConfig.staticSetSiteDisplayName(siteRequest_, o)));
	}

	/////////////////////
	// jdbcDriverClass //
	/////////////////////

	/**	 The entity jdbcDriverClass
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String jdbcDriverClass;
	@JsonIgnore
	public Wrap<String> jdbcDriverClassWrap = new Wrap<String>().p(this).c(String.class).var("jdbcDriverClass").o(jdbcDriverClass);

	/**	<br/> The entity jdbcDriverClass
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:jdbcDriverClass">Find the entity jdbcDriverClass in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _jdbcDriverClass(Wrap<String> c);

	public String getJdbcDriverClass() {
		return jdbcDriverClass;
	}
	public void setJdbcDriverClass(String o) {
		this.jdbcDriverClass = SiteConfig.staticSetJdbcDriverClass(null, o);
		this.jdbcDriverClassWrap.alreadyInitialized = true;
	}
	public static String staticSetJdbcDriverClass(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig jdbcDriverClassInit() {
		if(!jdbcDriverClassWrap.alreadyInitialized) {
			_jdbcDriverClass(jdbcDriverClassWrap);
			if(jdbcDriverClass == null)
				setJdbcDriverClass(jdbcDriverClassWrap.o);
		}
		jdbcDriverClassWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrJdbcDriverClass(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrJdbcDriverClass(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqJdbcDriverClass(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrJdbcDriverClass(siteRequest_, SiteConfig.staticSolrJdbcDriverClass(siteRequest_, SiteConfig.staticSetJdbcDriverClass(siteRequest_, o)));
	}

	//////////////////
	// jdbcUsername //
	//////////////////

	/**	 The entity jdbcUsername
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String jdbcUsername;
	@JsonIgnore
	public Wrap<String> jdbcUsernameWrap = new Wrap<String>().p(this).c(String.class).var("jdbcUsername").o(jdbcUsername);

	/**	<br/> The entity jdbcUsername
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:jdbcUsername">Find the entity jdbcUsername in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _jdbcUsername(Wrap<String> c);

	public String getJdbcUsername() {
		return jdbcUsername;
	}
	public void setJdbcUsername(String o) {
		this.jdbcUsername = SiteConfig.staticSetJdbcUsername(null, o);
		this.jdbcUsernameWrap.alreadyInitialized = true;
	}
	public static String staticSetJdbcUsername(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig jdbcUsernameInit() {
		if(!jdbcUsernameWrap.alreadyInitialized) {
			_jdbcUsername(jdbcUsernameWrap);
			if(jdbcUsername == null)
				setJdbcUsername(jdbcUsernameWrap.o);
		}
		jdbcUsernameWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrJdbcUsername(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrJdbcUsername(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqJdbcUsername(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrJdbcUsername(siteRequest_, SiteConfig.staticSolrJdbcUsername(siteRequest_, SiteConfig.staticSetJdbcUsername(siteRequest_, o)));
	}

	//////////////////
	// jdbcPassword //
	//////////////////

	/**	 The entity jdbcPassword
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String jdbcPassword;
	@JsonIgnore
	public Wrap<String> jdbcPasswordWrap = new Wrap<String>().p(this).c(String.class).var("jdbcPassword").o(jdbcPassword);

	/**	<br/> The entity jdbcPassword
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:jdbcPassword">Find the entity jdbcPassword in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _jdbcPassword(Wrap<String> c);

	public String getJdbcPassword() {
		return jdbcPassword;
	}
	public void setJdbcPassword(String o) {
		this.jdbcPassword = SiteConfig.staticSetJdbcPassword(null, o);
		this.jdbcPasswordWrap.alreadyInitialized = true;
	}
	public static String staticSetJdbcPassword(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig jdbcPasswordInit() {
		if(!jdbcPasswordWrap.alreadyInitialized) {
			_jdbcPassword(jdbcPasswordWrap);
			if(jdbcPassword == null)
				setJdbcPassword(jdbcPasswordWrap.o);
		}
		jdbcPasswordWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrJdbcPassword(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrJdbcPassword(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqJdbcPassword(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrJdbcPassword(siteRequest_, SiteConfig.staticSolrJdbcPassword(siteRequest_, SiteConfig.staticSetJdbcPassword(siteRequest_, o)));
	}

	/////////////////////
	// jdbcMaxPoolSize //
	/////////////////////

	/**	 The entity jdbcMaxPoolSize
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer jdbcMaxPoolSize;
	@JsonIgnore
	public Wrap<Integer> jdbcMaxPoolSizeWrap = new Wrap<Integer>().p(this).c(Integer.class).var("jdbcMaxPoolSize").o(jdbcMaxPoolSize);

	/**	<br/> The entity jdbcMaxPoolSize
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:jdbcMaxPoolSize">Find the entity jdbcMaxPoolSize in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _jdbcMaxPoolSize(Wrap<Integer> c);

	public Integer getJdbcMaxPoolSize() {
		return jdbcMaxPoolSize;
	}

	public void setJdbcMaxPoolSize(Integer jdbcMaxPoolSize) {
		this.jdbcMaxPoolSize = jdbcMaxPoolSize;
		this.jdbcMaxPoolSizeWrap.alreadyInitialized = true;
	}
	public void setJdbcMaxPoolSize(String o) {
		this.jdbcMaxPoolSize = SiteConfig.staticSetJdbcMaxPoolSize(null, o);
		this.jdbcMaxPoolSizeWrap.alreadyInitialized = true;
	}
	public static Integer staticSetJdbcMaxPoolSize(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected SiteConfig jdbcMaxPoolSizeInit() {
		if(!jdbcMaxPoolSizeWrap.alreadyInitialized) {
			_jdbcMaxPoolSize(jdbcMaxPoolSizeWrap);
			if(jdbcMaxPoolSize == null)
				setJdbcMaxPoolSize(jdbcMaxPoolSizeWrap.o);
		}
		jdbcMaxPoolSizeWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrJdbcMaxPoolSize(SiteRequestEnUS siteRequest_, Integer o) {
		return null;
	}

	public static String staticSolrStrJdbcMaxPoolSize(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqJdbcMaxPoolSize(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrJdbcMaxPoolSize(siteRequest_, SiteConfig.staticSolrJdbcMaxPoolSize(siteRequest_, SiteConfig.staticSetJdbcMaxPoolSize(siteRequest_, o)));
	}

	//////////////////////////
	// jdbcMaxWaitQueueSize //
	//////////////////////////

	/**	 The entity jdbcMaxWaitQueueSize
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer jdbcMaxWaitQueueSize;
	@JsonIgnore
	public Wrap<Integer> jdbcMaxWaitQueueSizeWrap = new Wrap<Integer>().p(this).c(Integer.class).var("jdbcMaxWaitQueueSize").o(jdbcMaxWaitQueueSize);

	/**	<br/> The entity jdbcMaxWaitQueueSize
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:jdbcMaxWaitQueueSize">Find the entity jdbcMaxWaitQueueSize in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _jdbcMaxWaitQueueSize(Wrap<Integer> c);

	public Integer getJdbcMaxWaitQueueSize() {
		return jdbcMaxWaitQueueSize;
	}

	public void setJdbcMaxWaitQueueSize(Integer jdbcMaxWaitQueueSize) {
		this.jdbcMaxWaitQueueSize = jdbcMaxWaitQueueSize;
		this.jdbcMaxWaitQueueSizeWrap.alreadyInitialized = true;
	}
	public void setJdbcMaxWaitQueueSize(String o) {
		this.jdbcMaxWaitQueueSize = SiteConfig.staticSetJdbcMaxWaitQueueSize(null, o);
		this.jdbcMaxWaitQueueSizeWrap.alreadyInitialized = true;
	}
	public static Integer staticSetJdbcMaxWaitQueueSize(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected SiteConfig jdbcMaxWaitQueueSizeInit() {
		if(!jdbcMaxWaitQueueSizeWrap.alreadyInitialized) {
			_jdbcMaxWaitQueueSize(jdbcMaxWaitQueueSizeWrap);
			if(jdbcMaxWaitQueueSize == null)
				setJdbcMaxWaitQueueSize(jdbcMaxWaitQueueSizeWrap.o);
		}
		jdbcMaxWaitQueueSizeWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrJdbcMaxWaitQueueSize(SiteRequestEnUS siteRequest_, Integer o) {
		return null;
	}

	public static String staticSolrStrJdbcMaxWaitQueueSize(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqJdbcMaxWaitQueueSize(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrJdbcMaxWaitQueueSize(siteRequest_, SiteConfig.staticSolrJdbcMaxWaitQueueSize(siteRequest_, SiteConfig.staticSetJdbcMaxWaitQueueSize(siteRequest_, o)));
	}

	/////////////////////
	// jdbcMinPoolSize //
	/////////////////////

	/**	 The entity jdbcMinPoolSize
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer jdbcMinPoolSize;
	@JsonIgnore
	public Wrap<Integer> jdbcMinPoolSizeWrap = new Wrap<Integer>().p(this).c(Integer.class).var("jdbcMinPoolSize").o(jdbcMinPoolSize);

	/**	<br/> The entity jdbcMinPoolSize
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:jdbcMinPoolSize">Find the entity jdbcMinPoolSize in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _jdbcMinPoolSize(Wrap<Integer> c);

	public Integer getJdbcMinPoolSize() {
		return jdbcMinPoolSize;
	}

	public void setJdbcMinPoolSize(Integer jdbcMinPoolSize) {
		this.jdbcMinPoolSize = jdbcMinPoolSize;
		this.jdbcMinPoolSizeWrap.alreadyInitialized = true;
	}
	public void setJdbcMinPoolSize(String o) {
		this.jdbcMinPoolSize = SiteConfig.staticSetJdbcMinPoolSize(null, o);
		this.jdbcMinPoolSizeWrap.alreadyInitialized = true;
	}
	public static Integer staticSetJdbcMinPoolSize(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected SiteConfig jdbcMinPoolSizeInit() {
		if(!jdbcMinPoolSizeWrap.alreadyInitialized) {
			_jdbcMinPoolSize(jdbcMinPoolSizeWrap);
			if(jdbcMinPoolSize == null)
				setJdbcMinPoolSize(jdbcMinPoolSizeWrap.o);
		}
		jdbcMinPoolSizeWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrJdbcMinPoolSize(SiteRequestEnUS siteRequest_, Integer o) {
		return null;
	}

	public static String staticSolrStrJdbcMinPoolSize(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqJdbcMinPoolSize(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrJdbcMinPoolSize(siteRequest_, SiteConfig.staticSolrJdbcMinPoolSize(siteRequest_, SiteConfig.staticSetJdbcMinPoolSize(siteRequest_, o)));
	}

	///////////////////////
	// jdbcMaxStatements //
	///////////////////////

	/**	 The entity jdbcMaxStatements
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer jdbcMaxStatements;
	@JsonIgnore
	public Wrap<Integer> jdbcMaxStatementsWrap = new Wrap<Integer>().p(this).c(Integer.class).var("jdbcMaxStatements").o(jdbcMaxStatements);

	/**	<br/> The entity jdbcMaxStatements
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:jdbcMaxStatements">Find the entity jdbcMaxStatements in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _jdbcMaxStatements(Wrap<Integer> c);

	public Integer getJdbcMaxStatements() {
		return jdbcMaxStatements;
	}

	public void setJdbcMaxStatements(Integer jdbcMaxStatements) {
		this.jdbcMaxStatements = jdbcMaxStatements;
		this.jdbcMaxStatementsWrap.alreadyInitialized = true;
	}
	public void setJdbcMaxStatements(String o) {
		this.jdbcMaxStatements = SiteConfig.staticSetJdbcMaxStatements(null, o);
		this.jdbcMaxStatementsWrap.alreadyInitialized = true;
	}
	public static Integer staticSetJdbcMaxStatements(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected SiteConfig jdbcMaxStatementsInit() {
		if(!jdbcMaxStatementsWrap.alreadyInitialized) {
			_jdbcMaxStatements(jdbcMaxStatementsWrap);
			if(jdbcMaxStatements == null)
				setJdbcMaxStatements(jdbcMaxStatementsWrap.o);
		}
		jdbcMaxStatementsWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrJdbcMaxStatements(SiteRequestEnUS siteRequest_, Integer o) {
		return null;
	}

	public static String staticSolrStrJdbcMaxStatements(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqJdbcMaxStatements(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrJdbcMaxStatements(siteRequest_, SiteConfig.staticSolrJdbcMaxStatements(siteRequest_, SiteConfig.staticSetJdbcMaxStatements(siteRequest_, o)));
	}

	////////////////////////////////////
	// jdbcMaxStatementsPerConnection //
	////////////////////////////////////

	/**	 The entity jdbcMaxStatementsPerConnection
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer jdbcMaxStatementsPerConnection;
	@JsonIgnore
	public Wrap<Integer> jdbcMaxStatementsPerConnectionWrap = new Wrap<Integer>().p(this).c(Integer.class).var("jdbcMaxStatementsPerConnection").o(jdbcMaxStatementsPerConnection);

	/**	<br/> The entity jdbcMaxStatementsPerConnection
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:jdbcMaxStatementsPerConnection">Find the entity jdbcMaxStatementsPerConnection in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _jdbcMaxStatementsPerConnection(Wrap<Integer> c);

	public Integer getJdbcMaxStatementsPerConnection() {
		return jdbcMaxStatementsPerConnection;
	}

	public void setJdbcMaxStatementsPerConnection(Integer jdbcMaxStatementsPerConnection) {
		this.jdbcMaxStatementsPerConnection = jdbcMaxStatementsPerConnection;
		this.jdbcMaxStatementsPerConnectionWrap.alreadyInitialized = true;
	}
	public void setJdbcMaxStatementsPerConnection(String o) {
		this.jdbcMaxStatementsPerConnection = SiteConfig.staticSetJdbcMaxStatementsPerConnection(null, o);
		this.jdbcMaxStatementsPerConnectionWrap.alreadyInitialized = true;
	}
	public static Integer staticSetJdbcMaxStatementsPerConnection(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected SiteConfig jdbcMaxStatementsPerConnectionInit() {
		if(!jdbcMaxStatementsPerConnectionWrap.alreadyInitialized) {
			_jdbcMaxStatementsPerConnection(jdbcMaxStatementsPerConnectionWrap);
			if(jdbcMaxStatementsPerConnection == null)
				setJdbcMaxStatementsPerConnection(jdbcMaxStatementsPerConnectionWrap.o);
		}
		jdbcMaxStatementsPerConnectionWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrJdbcMaxStatementsPerConnection(SiteRequestEnUS siteRequest_, Integer o) {
		return null;
	}

	public static String staticSolrStrJdbcMaxStatementsPerConnection(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqJdbcMaxStatementsPerConnection(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrJdbcMaxStatementsPerConnection(siteRequest_, SiteConfig.staticSolrJdbcMaxStatementsPerConnection(siteRequest_, SiteConfig.staticSetJdbcMaxStatementsPerConnection(siteRequest_, o)));
	}

	/////////////////////
	// jdbcMaxIdleTime //
	/////////////////////

	/**	 The entity jdbcMaxIdleTime
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer jdbcMaxIdleTime;
	@JsonIgnore
	public Wrap<Integer> jdbcMaxIdleTimeWrap = new Wrap<Integer>().p(this).c(Integer.class).var("jdbcMaxIdleTime").o(jdbcMaxIdleTime);

	/**	<br/> The entity jdbcMaxIdleTime
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:jdbcMaxIdleTime">Find the entity jdbcMaxIdleTime in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _jdbcMaxIdleTime(Wrap<Integer> c);

	public Integer getJdbcMaxIdleTime() {
		return jdbcMaxIdleTime;
	}

	public void setJdbcMaxIdleTime(Integer jdbcMaxIdleTime) {
		this.jdbcMaxIdleTime = jdbcMaxIdleTime;
		this.jdbcMaxIdleTimeWrap.alreadyInitialized = true;
	}
	public void setJdbcMaxIdleTime(String o) {
		this.jdbcMaxIdleTime = SiteConfig.staticSetJdbcMaxIdleTime(null, o);
		this.jdbcMaxIdleTimeWrap.alreadyInitialized = true;
	}
	public static Integer staticSetJdbcMaxIdleTime(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected SiteConfig jdbcMaxIdleTimeInit() {
		if(!jdbcMaxIdleTimeWrap.alreadyInitialized) {
			_jdbcMaxIdleTime(jdbcMaxIdleTimeWrap);
			if(jdbcMaxIdleTime == null)
				setJdbcMaxIdleTime(jdbcMaxIdleTimeWrap.o);
		}
		jdbcMaxIdleTimeWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrJdbcMaxIdleTime(SiteRequestEnUS siteRequest_, Integer o) {
		return null;
	}

	public static String staticSolrStrJdbcMaxIdleTime(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqJdbcMaxIdleTime(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrJdbcMaxIdleTime(siteRequest_, SiteConfig.staticSolrJdbcMaxIdleTime(siteRequest_, SiteConfig.staticSetJdbcMaxIdleTime(siteRequest_, o)));
	}

	////////////////////////
	// jdbcConnectTimeout //
	////////////////////////

	/**	 The entity jdbcConnectTimeout
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer jdbcConnectTimeout;
	@JsonIgnore
	public Wrap<Integer> jdbcConnectTimeoutWrap = new Wrap<Integer>().p(this).c(Integer.class).var("jdbcConnectTimeout").o(jdbcConnectTimeout);

	/**	<br/> The entity jdbcConnectTimeout
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:jdbcConnectTimeout">Find the entity jdbcConnectTimeout in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _jdbcConnectTimeout(Wrap<Integer> c);

	public Integer getJdbcConnectTimeout() {
		return jdbcConnectTimeout;
	}

	public void setJdbcConnectTimeout(Integer jdbcConnectTimeout) {
		this.jdbcConnectTimeout = jdbcConnectTimeout;
		this.jdbcConnectTimeoutWrap.alreadyInitialized = true;
	}
	public void setJdbcConnectTimeout(String o) {
		this.jdbcConnectTimeout = SiteConfig.staticSetJdbcConnectTimeout(null, o);
		this.jdbcConnectTimeoutWrap.alreadyInitialized = true;
	}
	public static Integer staticSetJdbcConnectTimeout(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected SiteConfig jdbcConnectTimeoutInit() {
		if(!jdbcConnectTimeoutWrap.alreadyInitialized) {
			_jdbcConnectTimeout(jdbcConnectTimeoutWrap);
			if(jdbcConnectTimeout == null)
				setJdbcConnectTimeout(jdbcConnectTimeoutWrap.o);
		}
		jdbcConnectTimeoutWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrJdbcConnectTimeout(SiteRequestEnUS siteRequest_, Integer o) {
		return null;
	}

	public static String staticSolrStrJdbcConnectTimeout(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqJdbcConnectTimeout(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrJdbcConnectTimeout(siteRequest_, SiteConfig.staticSolrJdbcConnectTimeout(siteRequest_, SiteConfig.staticSetJdbcConnectTimeout(siteRequest_, o)));
	}

	//////////////
	// jdbcHost //
	//////////////

	/**	 The entity jdbcHost
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String jdbcHost;
	@JsonIgnore
	public Wrap<String> jdbcHostWrap = new Wrap<String>().p(this).c(String.class).var("jdbcHost").o(jdbcHost);

	/**	<br/> The entity jdbcHost
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:jdbcHost">Find the entity jdbcHost in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _jdbcHost(Wrap<String> c);

	public String getJdbcHost() {
		return jdbcHost;
	}
	public void setJdbcHost(String o) {
		this.jdbcHost = SiteConfig.staticSetJdbcHost(null, o);
		this.jdbcHostWrap.alreadyInitialized = true;
	}
	public static String staticSetJdbcHost(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig jdbcHostInit() {
		if(!jdbcHostWrap.alreadyInitialized) {
			_jdbcHost(jdbcHostWrap);
			if(jdbcHost == null)
				setJdbcHost(jdbcHostWrap.o);
		}
		jdbcHostWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrJdbcHost(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrJdbcHost(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqJdbcHost(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrJdbcHost(siteRequest_, SiteConfig.staticSolrJdbcHost(siteRequest_, SiteConfig.staticSetJdbcHost(siteRequest_, o)));
	}

	//////////////
	// jdbcPort //
	//////////////

	/**	 The entity jdbcPort
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer jdbcPort;
	@JsonIgnore
	public Wrap<Integer> jdbcPortWrap = new Wrap<Integer>().p(this).c(Integer.class).var("jdbcPort").o(jdbcPort);

	/**	<br/> The entity jdbcPort
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:jdbcPort">Find the entity jdbcPort in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _jdbcPort(Wrap<Integer> c);

	public Integer getJdbcPort() {
		return jdbcPort;
	}

	public void setJdbcPort(Integer jdbcPort) {
		this.jdbcPort = jdbcPort;
		this.jdbcPortWrap.alreadyInitialized = true;
	}
	public void setJdbcPort(String o) {
		this.jdbcPort = SiteConfig.staticSetJdbcPort(null, o);
		this.jdbcPortWrap.alreadyInitialized = true;
	}
	public static Integer staticSetJdbcPort(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected SiteConfig jdbcPortInit() {
		if(!jdbcPortWrap.alreadyInitialized) {
			_jdbcPort(jdbcPortWrap);
			if(jdbcPort == null)
				setJdbcPort(jdbcPortWrap.o);
		}
		jdbcPortWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrJdbcPort(SiteRequestEnUS siteRequest_, Integer o) {
		return null;
	}

	public static String staticSolrStrJdbcPort(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqJdbcPort(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrJdbcPort(siteRequest_, SiteConfig.staticSolrJdbcPort(siteRequest_, SiteConfig.staticSetJdbcPort(siteRequest_, o)));
	}

	//////////////////
	// jdbcDatabase //
	//////////////////

	/**	 The entity jdbcDatabase
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String jdbcDatabase;
	@JsonIgnore
	public Wrap<String> jdbcDatabaseWrap = new Wrap<String>().p(this).c(String.class).var("jdbcDatabase").o(jdbcDatabase);

	/**	<br/> The entity jdbcDatabase
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:jdbcDatabase">Find the entity jdbcDatabase in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _jdbcDatabase(Wrap<String> c);

	public String getJdbcDatabase() {
		return jdbcDatabase;
	}
	public void setJdbcDatabase(String o) {
		this.jdbcDatabase = SiteConfig.staticSetJdbcDatabase(null, o);
		this.jdbcDatabaseWrap.alreadyInitialized = true;
	}
	public static String staticSetJdbcDatabase(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig jdbcDatabaseInit() {
		if(!jdbcDatabaseWrap.alreadyInitialized) {
			_jdbcDatabase(jdbcDatabaseWrap);
			if(jdbcDatabase == null)
				setJdbcDatabase(jdbcDatabaseWrap.o);
		}
		jdbcDatabaseWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrJdbcDatabase(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrJdbcDatabase(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqJdbcDatabase(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrJdbcDatabase(siteRequest_, SiteConfig.staticSolrJdbcDatabase(siteRequest_, SiteConfig.staticSetJdbcDatabase(siteRequest_, o)));
	}

	/////////////
	// solrUrl //
	/////////////

	/**	 The entity solrUrl
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String solrUrl;
	@JsonIgnore
	public Wrap<String> solrUrlWrap = new Wrap<String>().p(this).c(String.class).var("solrUrl").o(solrUrl);

	/**	<br/> The entity solrUrl
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:solrUrl">Find the entity solrUrl in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _solrUrl(Wrap<String> c);

	public String getSolrUrl() {
		return solrUrl;
	}
	public void setSolrUrl(String o) {
		this.solrUrl = SiteConfig.staticSetSolrUrl(null, o);
		this.solrUrlWrap.alreadyInitialized = true;
	}
	public static String staticSetSolrUrl(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig solrUrlInit() {
		if(!solrUrlWrap.alreadyInitialized) {
			_solrUrl(solrUrlWrap);
			if(solrUrl == null)
				setSolrUrl(solrUrlWrap.o);
		}
		solrUrlWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrSolrUrl(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrSolrUrl(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqSolrUrl(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrSolrUrl(siteRequest_, SiteConfig.staticSolrSolrUrl(siteRequest_, SiteConfig.staticSetSolrUrl(siteRequest_, o)));
	}

	//////////////////////
	// solrUrlComputate //
	//////////////////////

	/**	 The entity solrUrlComputate
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String solrUrlComputate;
	@JsonIgnore
	public Wrap<String> solrUrlComputateWrap = new Wrap<String>().p(this).c(String.class).var("solrUrlComputate").o(solrUrlComputate);

	/**	<br/> The entity solrUrlComputate
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:solrUrlComputate">Find the entity solrUrlComputate in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _solrUrlComputate(Wrap<String> c);

	public String getSolrUrlComputate() {
		return solrUrlComputate;
	}
	public void setSolrUrlComputate(String o) {
		this.solrUrlComputate = SiteConfig.staticSetSolrUrlComputate(null, o);
		this.solrUrlComputateWrap.alreadyInitialized = true;
	}
	public static String staticSetSolrUrlComputate(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig solrUrlComputateInit() {
		if(!solrUrlComputateWrap.alreadyInitialized) {
			_solrUrlComputate(solrUrlComputateWrap);
			if(solrUrlComputate == null)
				setSolrUrlComputate(solrUrlComputateWrap.o);
		}
		solrUrlComputateWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrSolrUrlComputate(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrSolrUrlComputate(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqSolrUrlComputate(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrSolrUrlComputate(siteRequest_, SiteConfig.staticSolrSolrUrlComputate(siteRequest_, SiteConfig.staticSetSolrUrlComputate(siteRequest_, o)));
	}

	/////////////////////
	// accountFacebook //
	/////////////////////

	/**	 The entity accountFacebook
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String accountFacebook;
	@JsonIgnore
	public Wrap<String> accountFacebookWrap = new Wrap<String>().p(this).c(String.class).var("accountFacebook").o(accountFacebook);

	/**	<br/> The entity accountFacebook
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:accountFacebook">Find the entity accountFacebook in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _accountFacebook(Wrap<String> c);

	public String getAccountFacebook() {
		return accountFacebook;
	}
	public void setAccountFacebook(String o) {
		this.accountFacebook = SiteConfig.staticSetAccountFacebook(null, o);
		this.accountFacebookWrap.alreadyInitialized = true;
	}
	public static String staticSetAccountFacebook(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig accountFacebookInit() {
		if(!accountFacebookWrap.alreadyInitialized) {
			_accountFacebook(accountFacebookWrap);
			if(accountFacebook == null)
				setAccountFacebook(accountFacebookWrap.o);
		}
		accountFacebookWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrAccountFacebook(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrAccountFacebook(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqAccountFacebook(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrAccountFacebook(siteRequest_, SiteConfig.staticSolrAccountFacebook(siteRequest_, SiteConfig.staticSetAccountFacebook(siteRequest_, o)));
	}

	////////////////////
	// accountTwitter //
	////////////////////

	/**	 The entity accountTwitter
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String accountTwitter;
	@JsonIgnore
	public Wrap<String> accountTwitterWrap = new Wrap<String>().p(this).c(String.class).var("accountTwitter").o(accountTwitter);

	/**	<br/> The entity accountTwitter
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:accountTwitter">Find the entity accountTwitter in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _accountTwitter(Wrap<String> c);

	public String getAccountTwitter() {
		return accountTwitter;
	}
	public void setAccountTwitter(String o) {
		this.accountTwitter = SiteConfig.staticSetAccountTwitter(null, o);
		this.accountTwitterWrap.alreadyInitialized = true;
	}
	public static String staticSetAccountTwitter(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig accountTwitterInit() {
		if(!accountTwitterWrap.alreadyInitialized) {
			_accountTwitter(accountTwitterWrap);
			if(accountTwitter == null)
				setAccountTwitter(accountTwitterWrap.o);
		}
		accountTwitterWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrAccountTwitter(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrAccountTwitter(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqAccountTwitter(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrAccountTwitter(siteRequest_, SiteConfig.staticSolrAccountTwitter(siteRequest_, SiteConfig.staticSetAccountTwitter(siteRequest_, o)));
	}

	//////////////////////
	// accountInstagram //
	//////////////////////

	/**	 The entity accountInstagram
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String accountInstagram;
	@JsonIgnore
	public Wrap<String> accountInstagramWrap = new Wrap<String>().p(this).c(String.class).var("accountInstagram").o(accountInstagram);

	/**	<br/> The entity accountInstagram
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:accountInstagram">Find the entity accountInstagram in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _accountInstagram(Wrap<String> c);

	public String getAccountInstagram() {
		return accountInstagram;
	}
	public void setAccountInstagram(String o) {
		this.accountInstagram = SiteConfig.staticSetAccountInstagram(null, o);
		this.accountInstagramWrap.alreadyInitialized = true;
	}
	public static String staticSetAccountInstagram(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig accountInstagramInit() {
		if(!accountInstagramWrap.alreadyInitialized) {
			_accountInstagram(accountInstagramWrap);
			if(accountInstagram == null)
				setAccountInstagram(accountInstagramWrap.o);
		}
		accountInstagramWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrAccountInstagram(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrAccountInstagram(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqAccountInstagram(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrAccountInstagram(siteRequest_, SiteConfig.staticSolrAccountInstagram(siteRequest_, SiteConfig.staticSetAccountInstagram(siteRequest_, o)));
	}

	////////////////////
	// accountYoutube //
	////////////////////

	/**	 The entity accountYoutube
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String accountYoutube;
	@JsonIgnore
	public Wrap<String> accountYoutubeWrap = new Wrap<String>().p(this).c(String.class).var("accountYoutube").o(accountYoutube);

	/**	<br/> The entity accountYoutube
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:accountYoutube">Find the entity accountYoutube in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _accountYoutube(Wrap<String> c);

	public String getAccountYoutube() {
		return accountYoutube;
	}
	public void setAccountYoutube(String o) {
		this.accountYoutube = SiteConfig.staticSetAccountYoutube(null, o);
		this.accountYoutubeWrap.alreadyInitialized = true;
	}
	public static String staticSetAccountYoutube(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig accountYoutubeInit() {
		if(!accountYoutubeWrap.alreadyInitialized) {
			_accountYoutube(accountYoutubeWrap);
			if(accountYoutube == null)
				setAccountYoutube(accountYoutubeWrap.o);
		}
		accountYoutubeWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrAccountYoutube(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrAccountYoutube(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqAccountYoutube(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrAccountYoutube(siteRequest_, SiteConfig.staticSolrAccountYoutube(siteRequest_, SiteConfig.staticSetAccountYoutube(siteRequest_, o)));
	}

	//////////////////////
	// accountPinterest //
	//////////////////////

	/**	 The entity accountPinterest
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String accountPinterest;
	@JsonIgnore
	public Wrap<String> accountPinterestWrap = new Wrap<String>().p(this).c(String.class).var("accountPinterest").o(accountPinterest);

	/**	<br/> The entity accountPinterest
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:accountPinterest">Find the entity accountPinterest in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _accountPinterest(Wrap<String> c);

	public String getAccountPinterest() {
		return accountPinterest;
	}
	public void setAccountPinterest(String o) {
		this.accountPinterest = SiteConfig.staticSetAccountPinterest(null, o);
		this.accountPinterestWrap.alreadyInitialized = true;
	}
	public static String staticSetAccountPinterest(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig accountPinterestInit() {
		if(!accountPinterestWrap.alreadyInitialized) {
			_accountPinterest(accountPinterestWrap);
			if(accountPinterest == null)
				setAccountPinterest(accountPinterestWrap.o);
		}
		accountPinterestWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrAccountPinterest(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrAccountPinterest(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqAccountPinterest(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrAccountPinterest(siteRequest_, SiteConfig.staticSolrAccountPinterest(siteRequest_, SiteConfig.staticSetAccountPinterest(siteRequest_, o)));
	}

	//////////////////
	// accountEmail //
	//////////////////

	/**	 The entity accountEmail
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String accountEmail;
	@JsonIgnore
	public Wrap<String> accountEmailWrap = new Wrap<String>().p(this).c(String.class).var("accountEmail").o(accountEmail);

	/**	<br/> The entity accountEmail
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:accountEmail">Find the entity accountEmail in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _accountEmail(Wrap<String> c);

	public String getAccountEmail() {
		return accountEmail;
	}
	public void setAccountEmail(String o) {
		this.accountEmail = SiteConfig.staticSetAccountEmail(null, o);
		this.accountEmailWrap.alreadyInitialized = true;
	}
	public static String staticSetAccountEmail(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig accountEmailInit() {
		if(!accountEmailWrap.alreadyInitialized) {
			_accountEmail(accountEmailWrap);
			if(accountEmail == null)
				setAccountEmail(accountEmailWrap.o);
		}
		accountEmailWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrAccountEmail(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrAccountEmail(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqAccountEmail(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrAccountEmail(siteRequest_, SiteConfig.staticSolrAccountEmail(siteRequest_, SiteConfig.staticSetAccountEmail(siteRequest_, o)));
	}

	///////////////
	// roleAdmin //
	///////////////

	/**	 The entity roleAdmin
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String roleAdmin;
	@JsonIgnore
	public Wrap<String> roleAdminWrap = new Wrap<String>().p(this).c(String.class).var("roleAdmin").o(roleAdmin);

	/**	<br/> The entity roleAdmin
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:roleAdmin">Find the entity roleAdmin in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _roleAdmin(Wrap<String> c);

	public String getRoleAdmin() {
		return roleAdmin;
	}
	public void setRoleAdmin(String o) {
		this.roleAdmin = SiteConfig.staticSetRoleAdmin(null, o);
		this.roleAdminWrap.alreadyInitialized = true;
	}
	public static String staticSetRoleAdmin(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig roleAdminInit() {
		if(!roleAdminWrap.alreadyInitialized) {
			_roleAdmin(roleAdminWrap);
			if(roleAdmin == null)
				setRoleAdmin(roleAdminWrap.o);
		}
		roleAdminWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrRoleAdmin(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrRoleAdmin(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqRoleAdmin(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrRoleAdmin(siteRequest_, SiteConfig.staticSolrRoleAdmin(siteRequest_, SiteConfig.staticSetRoleAdmin(siteRequest_, o)));
	}

	////////////////
	// emailAdmin //
	////////////////

	/**	 The entity emailAdmin
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String emailAdmin;
	@JsonIgnore
	public Wrap<String> emailAdminWrap = new Wrap<String>().p(this).c(String.class).var("emailAdmin").o(emailAdmin);

	/**	<br/> The entity emailAdmin
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailAdmin">Find the entity emailAdmin in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _emailAdmin(Wrap<String> c);

	public String getEmailAdmin() {
		return emailAdmin;
	}
	public void setEmailAdmin(String o) {
		this.emailAdmin = SiteConfig.staticSetEmailAdmin(null, o);
		this.emailAdminWrap.alreadyInitialized = true;
	}
	public static String staticSetEmailAdmin(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig emailAdminInit() {
		if(!emailAdminWrap.alreadyInitialized) {
			_emailAdmin(emailAdminWrap);
			if(emailAdmin == null)
				setEmailAdmin(emailAdminWrap.o);
		}
		emailAdminWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrEmailAdmin(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrEmailAdmin(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqEmailAdmin(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrEmailAdmin(siteRequest_, SiteConfig.staticSolrEmailAdmin(siteRequest_, SiteConfig.staticSetEmailAdmin(siteRequest_, o)));
	}

	/////////////////////
	// numberExecutors //
	/////////////////////

	/**	 The entity numberExecutors
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer numberExecutors;
	@JsonIgnore
	public Wrap<Integer> numberExecutorsWrap = new Wrap<Integer>().p(this).c(Integer.class).var("numberExecutors").o(numberExecutors);

	/**	<br/> The entity numberExecutors
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:numberExecutors">Find the entity numberExecutors in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _numberExecutors(Wrap<Integer> c);

	public Integer getNumberExecutors() {
		return numberExecutors;
	}

	public void setNumberExecutors(Integer numberExecutors) {
		this.numberExecutors = numberExecutors;
		this.numberExecutorsWrap.alreadyInitialized = true;
	}
	public void setNumberExecutors(String o) {
		this.numberExecutors = SiteConfig.staticSetNumberExecutors(null, o);
		this.numberExecutorsWrap.alreadyInitialized = true;
	}
	public static Integer staticSetNumberExecutors(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected SiteConfig numberExecutorsInit() {
		if(!numberExecutorsWrap.alreadyInitialized) {
			_numberExecutors(numberExecutorsWrap);
			if(numberExecutors == null)
				setNumberExecutors(numberExecutorsWrap.o);
		}
		numberExecutorsWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrNumberExecutors(SiteRequestEnUS siteRequest_, Integer o) {
		return null;
	}

	public static String staticSolrStrNumberExecutors(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqNumberExecutors(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrNumberExecutors(siteRequest_, SiteConfig.staticSolrNumberExecutors(siteRequest_, SiteConfig.staticSetNumberExecutors(siteRequest_, o)));
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:openApiVersion">Find the entity openApiVersion in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _openApiVersion(Wrap<String> c);

	public String getOpenApiVersion() {
		return openApiVersion;
	}
	public void setOpenApiVersion(String o) {
		this.openApiVersion = SiteConfig.staticSetOpenApiVersion(null, o);
		this.openApiVersionWrap.alreadyInitialized = true;
	}
	public static String staticSetOpenApiVersion(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig openApiVersionInit() {
		if(!openApiVersionWrap.alreadyInitialized) {
			_openApiVersion(openApiVersionWrap);
			if(openApiVersion == null)
				setOpenApiVersion(openApiVersionWrap.o);
		}
		openApiVersionWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrOpenApiVersion(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrOpenApiVersion(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqOpenApiVersion(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrOpenApiVersion(siteRequest_, SiteConfig.staticSolrOpenApiVersion(siteRequest_, SiteConfig.staticSetOpenApiVersion(siteRequest_, o)));
	}

	////////////////////
	// apiDescription //
	////////////////////

	/**	 The entity apiDescription
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String apiDescription;
	@JsonIgnore
	public Wrap<String> apiDescriptionWrap = new Wrap<String>().p(this).c(String.class).var("apiDescription").o(apiDescription);

	/**	<br/> The entity apiDescription
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:apiDescription">Find the entity apiDescription in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _apiDescription(Wrap<String> c);

	public String getApiDescription() {
		return apiDescription;
	}
	public void setApiDescription(String o) {
		this.apiDescription = SiteConfig.staticSetApiDescription(null, o);
		this.apiDescriptionWrap.alreadyInitialized = true;
	}
	public static String staticSetApiDescription(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig apiDescriptionInit() {
		if(!apiDescriptionWrap.alreadyInitialized) {
			_apiDescription(apiDescriptionWrap);
			if(apiDescription == null)
				setApiDescription(apiDescriptionWrap.o);
		}
		apiDescriptionWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrApiDescription(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrApiDescription(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqApiDescription(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrApiDescription(siteRequest_, SiteConfig.staticSolrApiDescription(siteRequest_, SiteConfig.staticSetApiDescription(siteRequest_, o)));
	}

	//////////////
	// apiTitle //
	//////////////

	/**	 The entity apiTitle
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String apiTitle;
	@JsonIgnore
	public Wrap<String> apiTitleWrap = new Wrap<String>().p(this).c(String.class).var("apiTitle").o(apiTitle);

	/**	<br/> The entity apiTitle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:apiTitle">Find the entity apiTitle in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _apiTitle(Wrap<String> c);

	public String getApiTitle() {
		return apiTitle;
	}
	public void setApiTitle(String o) {
		this.apiTitle = SiteConfig.staticSetApiTitle(null, o);
		this.apiTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetApiTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig apiTitleInit() {
		if(!apiTitleWrap.alreadyInitialized) {
			_apiTitle(apiTitleWrap);
			if(apiTitle == null)
				setApiTitle(apiTitleWrap.o);
		}
		apiTitleWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrApiTitle(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrApiTitle(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqApiTitle(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrApiTitle(siteRequest_, SiteConfig.staticSolrApiTitle(siteRequest_, SiteConfig.staticSetApiTitle(siteRequest_, o)));
	}

	/////////////////////
	// apiTermsService //
	/////////////////////

	/**	 The entity apiTermsService
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String apiTermsService;
	@JsonIgnore
	public Wrap<String> apiTermsServiceWrap = new Wrap<String>().p(this).c(String.class).var("apiTermsService").o(apiTermsService);

	/**	<br/> The entity apiTermsService
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:apiTermsService">Find the entity apiTermsService in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _apiTermsService(Wrap<String> c);

	public String getApiTermsService() {
		return apiTermsService;
	}
	public void setApiTermsService(String o) {
		this.apiTermsService = SiteConfig.staticSetApiTermsService(null, o);
		this.apiTermsServiceWrap.alreadyInitialized = true;
	}
	public static String staticSetApiTermsService(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig apiTermsServiceInit() {
		if(!apiTermsServiceWrap.alreadyInitialized) {
			_apiTermsService(apiTermsServiceWrap);
			if(apiTermsService == null)
				setApiTermsService(apiTermsServiceWrap.o);
		}
		apiTermsServiceWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrApiTermsService(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrApiTermsService(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqApiTermsService(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrApiTermsService(siteRequest_, SiteConfig.staticSolrApiTermsService(siteRequest_, SiteConfig.staticSetApiTermsService(siteRequest_, o)));
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:apiVersion">Find the entity apiVersion in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _apiVersion(Wrap<String> c);

	public String getApiVersion() {
		return apiVersion;
	}
	public void setApiVersion(String o) {
		this.apiVersion = SiteConfig.staticSetApiVersion(null, o);
		this.apiVersionWrap.alreadyInitialized = true;
	}
	public static String staticSetApiVersion(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig apiVersionInit() {
		if(!apiVersionWrap.alreadyInitialized) {
			_apiVersion(apiVersionWrap);
			if(apiVersion == null)
				setApiVersion(apiVersionWrap.o);
		}
		apiVersionWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrApiVersion(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrApiVersion(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqApiVersion(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrApiVersion(siteRequest_, SiteConfig.staticSolrApiVersion(siteRequest_, SiteConfig.staticSetApiVersion(siteRequest_, o)));
	}

	/////////////////////
	// apiContactEmail //
	/////////////////////

	/**	 The entity apiContactEmail
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String apiContactEmail;
	@JsonIgnore
	public Wrap<String> apiContactEmailWrap = new Wrap<String>().p(this).c(String.class).var("apiContactEmail").o(apiContactEmail);

	/**	<br/> The entity apiContactEmail
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:apiContactEmail">Find the entity apiContactEmail in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _apiContactEmail(Wrap<String> c);

	public String getApiContactEmail() {
		return apiContactEmail;
	}
	public void setApiContactEmail(String o) {
		this.apiContactEmail = SiteConfig.staticSetApiContactEmail(null, o);
		this.apiContactEmailWrap.alreadyInitialized = true;
	}
	public static String staticSetApiContactEmail(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig apiContactEmailInit() {
		if(!apiContactEmailWrap.alreadyInitialized) {
			_apiContactEmail(apiContactEmailWrap);
			if(apiContactEmail == null)
				setApiContactEmail(apiContactEmailWrap.o);
		}
		apiContactEmailWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrApiContactEmail(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrApiContactEmail(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqApiContactEmail(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrApiContactEmail(siteRequest_, SiteConfig.staticSolrApiContactEmail(siteRequest_, SiteConfig.staticSetApiContactEmail(siteRequest_, o)));
	}

	////////////////////
	// apiLicenseName //
	////////////////////

	/**	 The entity apiLicenseName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String apiLicenseName;
	@JsonIgnore
	public Wrap<String> apiLicenseNameWrap = new Wrap<String>().p(this).c(String.class).var("apiLicenseName").o(apiLicenseName);

	/**	<br/> The entity apiLicenseName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:apiLicenseName">Find the entity apiLicenseName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _apiLicenseName(Wrap<String> c);

	public String getApiLicenseName() {
		return apiLicenseName;
	}
	public void setApiLicenseName(String o) {
		this.apiLicenseName = SiteConfig.staticSetApiLicenseName(null, o);
		this.apiLicenseNameWrap.alreadyInitialized = true;
	}
	public static String staticSetApiLicenseName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig apiLicenseNameInit() {
		if(!apiLicenseNameWrap.alreadyInitialized) {
			_apiLicenseName(apiLicenseNameWrap);
			if(apiLicenseName == null)
				setApiLicenseName(apiLicenseNameWrap.o);
		}
		apiLicenseNameWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrApiLicenseName(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrApiLicenseName(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqApiLicenseName(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrApiLicenseName(siteRequest_, SiteConfig.staticSolrApiLicenseName(siteRequest_, SiteConfig.staticSetApiLicenseName(siteRequest_, o)));
	}

	///////////////////
	// apiLicenseUrl //
	///////////////////

	/**	 The entity apiLicenseUrl
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String apiLicenseUrl;
	@JsonIgnore
	public Wrap<String> apiLicenseUrlWrap = new Wrap<String>().p(this).c(String.class).var("apiLicenseUrl").o(apiLicenseUrl);

	/**	<br/> The entity apiLicenseUrl
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:apiLicenseUrl">Find the entity apiLicenseUrl in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _apiLicenseUrl(Wrap<String> c);

	public String getApiLicenseUrl() {
		return apiLicenseUrl;
	}
	public void setApiLicenseUrl(String o) {
		this.apiLicenseUrl = SiteConfig.staticSetApiLicenseUrl(null, o);
		this.apiLicenseUrlWrap.alreadyInitialized = true;
	}
	public static String staticSetApiLicenseUrl(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig apiLicenseUrlInit() {
		if(!apiLicenseUrlWrap.alreadyInitialized) {
			_apiLicenseUrl(apiLicenseUrlWrap);
			if(apiLicenseUrl == null)
				setApiLicenseUrl(apiLicenseUrlWrap.o);
		}
		apiLicenseUrlWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrApiLicenseUrl(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrApiLicenseUrl(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqApiLicenseUrl(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrApiLicenseUrl(siteRequest_, SiteConfig.staticSolrApiLicenseUrl(siteRequest_, SiteConfig.staticSetApiLicenseUrl(siteRequest_, o)));
	}

	/////////////////
	// apiHostName //
	/////////////////

	/**	 The entity apiHostName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String apiHostName;
	@JsonIgnore
	public Wrap<String> apiHostNameWrap = new Wrap<String>().p(this).c(String.class).var("apiHostName").o(apiHostName);

	/**	<br/> The entity apiHostName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:apiHostName">Find the entity apiHostName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _apiHostName(Wrap<String> c);

	public String getApiHostName() {
		return apiHostName;
	}
	public void setApiHostName(String o) {
		this.apiHostName = SiteConfig.staticSetApiHostName(null, o);
		this.apiHostNameWrap.alreadyInitialized = true;
	}
	public static String staticSetApiHostName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig apiHostNameInit() {
		if(!apiHostNameWrap.alreadyInitialized) {
			_apiHostName(apiHostNameWrap);
			if(apiHostName == null)
				setApiHostName(apiHostNameWrap.o);
		}
		apiHostNameWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrApiHostName(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrApiHostName(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqApiHostName(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrApiHostName(siteRequest_, SiteConfig.staticSolrApiHostName(siteRequest_, SiteConfig.staticSetApiHostName(siteRequest_, o)));
	}

	/////////////////
	// apiBasePath //
	/////////////////

	/**	 The entity apiBasePath
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String apiBasePath;
	@JsonIgnore
	public Wrap<String> apiBasePathWrap = new Wrap<String>().p(this).c(String.class).var("apiBasePath").o(apiBasePath);

	/**	<br/> The entity apiBasePath
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:apiBasePath">Find the entity apiBasePath in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _apiBasePath(Wrap<String> c);

	public String getApiBasePath() {
		return apiBasePath;
	}
	public void setApiBasePath(String o) {
		this.apiBasePath = SiteConfig.staticSetApiBasePath(null, o);
		this.apiBasePathWrap.alreadyInitialized = true;
	}
	public static String staticSetApiBasePath(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig apiBasePathInit() {
		if(!apiBasePathWrap.alreadyInitialized) {
			_apiBasePath(apiBasePathWrap);
			if(apiBasePath == null)
				setApiBasePath(apiBasePathWrap.o);
		}
		apiBasePathWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrApiBasePath(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrApiBasePath(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqApiBasePath(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrApiBasePath(siteRequest_, SiteConfig.staticSolrApiBasePath(siteRequest_, SiteConfig.staticSetApiBasePath(siteRequest_, o)));
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:staticBaseUrl">Find the entity staticBaseUrl in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _staticBaseUrl(Wrap<String> c);

	public String getStaticBaseUrl() {
		return staticBaseUrl;
	}
	public void setStaticBaseUrl(String o) {
		this.staticBaseUrl = SiteConfig.staticSetStaticBaseUrl(null, o);
		this.staticBaseUrlWrap.alreadyInitialized = true;
	}
	public static String staticSetStaticBaseUrl(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig staticBaseUrlInit() {
		if(!staticBaseUrlWrap.alreadyInitialized) {
			_staticBaseUrl(staticBaseUrlWrap);
			if(staticBaseUrl == null)
				setStaticBaseUrl(staticBaseUrlWrap.o);
		}
		staticBaseUrlWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrStaticBaseUrl(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrStaticBaseUrl(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqStaticBaseUrl(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrStaticBaseUrl(siteRequest_, SiteConfig.staticSolrStaticBaseUrl(siteRequest_, SiteConfig.staticSetStaticBaseUrl(siteRequest_, o)));
	}

	////////////////
	// staticPath //
	////////////////

	/**	 The entity staticPath
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String staticPath;
	@JsonIgnore
	public Wrap<String> staticPathWrap = new Wrap<String>().p(this).c(String.class).var("staticPath").o(staticPath);

	/**	<br/> The entity staticPath
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:staticPath">Find the entity staticPath in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _staticPath(Wrap<String> c);

	public String getStaticPath() {
		return staticPath;
	}
	public void setStaticPath(String o) {
		this.staticPath = SiteConfig.staticSetStaticPath(null, o);
		this.staticPathWrap.alreadyInitialized = true;
	}
	public static String staticSetStaticPath(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig staticPathInit() {
		if(!staticPathWrap.alreadyInitialized) {
			_staticPath(staticPathWrap);
			if(staticPath == null)
				setStaticPath(staticPathWrap.o);
		}
		staticPathWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrStaticPath(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrStaticPath(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqStaticPath(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrStaticPath(siteRequest_, SiteConfig.staticSolrStaticPath(siteRequest_, SiteConfig.staticSetStaticPath(siteRequest_, o)));
	}

	////////////////
	// importPath //
	////////////////

	/**	 The entity importPath
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String importPath;
	@JsonIgnore
	public Wrap<String> importPathWrap = new Wrap<String>().p(this).c(String.class).var("importPath").o(importPath);

	/**	<br/> The entity importPath
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:importPath">Find the entity importPath in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _importPath(Wrap<String> c);

	public String getImportPath() {
		return importPath;
	}
	public void setImportPath(String o) {
		this.importPath = SiteConfig.staticSetImportPath(null, o);
		this.importPathWrap.alreadyInitialized = true;
	}
	public static String staticSetImportPath(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig importPathInit() {
		if(!importPathWrap.alreadyInitialized) {
			_importPath(importPathWrap);
			if(importPath == null)
				setImportPath(importPathWrap.o);
		}
		importPathWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrImportPath(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrImportPath(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqImportPath(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrImportPath(siteRequest_, SiteConfig.staticSolrImportPath(siteRequest_, SiteConfig.staticSetImportPath(siteRequest_, o)));
	}

	///////////////
	// emailHost //
	///////////////

	/**	 The entity emailHost
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String emailHost;
	@JsonIgnore
	public Wrap<String> emailHostWrap = new Wrap<String>().p(this).c(String.class).var("emailHost").o(emailHost);

	/**	<br/> The entity emailHost
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailHost">Find the entity emailHost in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _emailHost(Wrap<String> c);

	public String getEmailHost() {
		return emailHost;
	}
	public void setEmailHost(String o) {
		this.emailHost = SiteConfig.staticSetEmailHost(null, o);
		this.emailHostWrap.alreadyInitialized = true;
	}
	public static String staticSetEmailHost(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig emailHostInit() {
		if(!emailHostWrap.alreadyInitialized) {
			_emailHost(emailHostWrap);
			if(emailHost == null)
				setEmailHost(emailHostWrap.o);
		}
		emailHostWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrEmailHost(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrEmailHost(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqEmailHost(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrEmailHost(siteRequest_, SiteConfig.staticSolrEmailHost(siteRequest_, SiteConfig.staticSetEmailHost(siteRequest_, o)));
	}

	///////////////
	// emailPort //
	///////////////

	/**	 The entity emailPort
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer emailPort;
	@JsonIgnore
	public Wrap<Integer> emailPortWrap = new Wrap<Integer>().p(this).c(Integer.class).var("emailPort").o(emailPort);

	/**	<br/> The entity emailPort
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailPort">Find the entity emailPort in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _emailPort(Wrap<Integer> c);

	public Integer getEmailPort() {
		return emailPort;
	}

	public void setEmailPort(Integer emailPort) {
		this.emailPort = emailPort;
		this.emailPortWrap.alreadyInitialized = true;
	}
	public void setEmailPort(String o) {
		this.emailPort = SiteConfig.staticSetEmailPort(null, o);
		this.emailPortWrap.alreadyInitialized = true;
	}
	public static Integer staticSetEmailPort(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected SiteConfig emailPortInit() {
		if(!emailPortWrap.alreadyInitialized) {
			_emailPort(emailPortWrap);
			if(emailPort == null)
				setEmailPort(emailPortWrap.o);
		}
		emailPortWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrEmailPort(SiteRequestEnUS siteRequest_, Integer o) {
		return null;
	}

	public static String staticSolrStrEmailPort(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqEmailPort(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrEmailPort(siteRequest_, SiteConfig.staticSolrEmailPort(siteRequest_, SiteConfig.staticSetEmailPort(siteRequest_, o)));
	}

	///////////////////
	// emailUsername //
	///////////////////

	/**	 The entity emailUsername
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String emailUsername;
	@JsonIgnore
	public Wrap<String> emailUsernameWrap = new Wrap<String>().p(this).c(String.class).var("emailUsername").o(emailUsername);

	/**	<br/> The entity emailUsername
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailUsername">Find the entity emailUsername in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _emailUsername(Wrap<String> c);

	public String getEmailUsername() {
		return emailUsername;
	}
	public void setEmailUsername(String o) {
		this.emailUsername = SiteConfig.staticSetEmailUsername(null, o);
		this.emailUsernameWrap.alreadyInitialized = true;
	}
	public static String staticSetEmailUsername(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig emailUsernameInit() {
		if(!emailUsernameWrap.alreadyInitialized) {
			_emailUsername(emailUsernameWrap);
			if(emailUsername == null)
				setEmailUsername(emailUsernameWrap.o);
		}
		emailUsernameWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrEmailUsername(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrEmailUsername(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqEmailUsername(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrEmailUsername(siteRequest_, SiteConfig.staticSolrEmailUsername(siteRequest_, SiteConfig.staticSetEmailUsername(siteRequest_, o)));
	}

	///////////////////
	// emailPassword //
	///////////////////

	/**	 The entity emailPassword
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String emailPassword;
	@JsonIgnore
	public Wrap<String> emailPasswordWrap = new Wrap<String>().p(this).c(String.class).var("emailPassword").o(emailPassword);

	/**	<br/> The entity emailPassword
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailPassword">Find the entity emailPassword in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _emailPassword(Wrap<String> c);

	public String getEmailPassword() {
		return emailPassword;
	}
	public void setEmailPassword(String o) {
		this.emailPassword = SiteConfig.staticSetEmailPassword(null, o);
		this.emailPasswordWrap.alreadyInitialized = true;
	}
	public static String staticSetEmailPassword(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig emailPasswordInit() {
		if(!emailPasswordWrap.alreadyInitialized) {
			_emailPassword(emailPasswordWrap);
			if(emailPassword == null)
				setEmailPassword(emailPasswordWrap.o);
		}
		emailPasswordWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrEmailPassword(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrEmailPassword(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqEmailPassword(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrEmailPassword(siteRequest_, SiteConfig.staticSolrEmailPassword(siteRequest_, SiteConfig.staticSetEmailPassword(siteRequest_, o)));
	}

	///////////////
	// emailFrom //
	///////////////

	/**	 The entity emailFrom
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String emailFrom;
	@JsonIgnore
	public Wrap<String> emailFromWrap = new Wrap<String>().p(this).c(String.class).var("emailFrom").o(emailFrom);

	/**	<br/> The entity emailFrom
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailFrom">Find the entity emailFrom in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _emailFrom(Wrap<String> c);

	public String getEmailFrom() {
		return emailFrom;
	}
	public void setEmailFrom(String o) {
		this.emailFrom = SiteConfig.staticSetEmailFrom(null, o);
		this.emailFromWrap.alreadyInitialized = true;
	}
	public static String staticSetEmailFrom(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig emailFromInit() {
		if(!emailFromWrap.alreadyInitialized) {
			_emailFrom(emailFromWrap);
			if(emailFrom == null)
				setEmailFrom(emailFromWrap.o);
		}
		emailFromWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrEmailFrom(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrEmailFrom(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqEmailFrom(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrEmailFrom(siteRequest_, SiteConfig.staticSolrEmailFrom(siteRequest_, SiteConfig.staticSetEmailFrom(siteRequest_, o)));
	}

	///////////////
	// emailAuth //
	///////////////

	/**	 The entity emailAuth
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean emailAuth;
	@JsonIgnore
	public Wrap<Boolean> emailAuthWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("emailAuth").o(emailAuth);

	/**	<br/> The entity emailAuth
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailAuth">Find the entity emailAuth in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _emailAuth(Wrap<Boolean> c);

	public Boolean getEmailAuth() {
		return emailAuth;
	}

	public void setEmailAuth(Boolean emailAuth) {
		this.emailAuth = emailAuth;
		this.emailAuthWrap.alreadyInitialized = true;
	}
	public void setEmailAuth(String o) {
		this.emailAuth = SiteConfig.staticSetEmailAuth(null, o);
		this.emailAuthWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetEmailAuth(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SiteConfig emailAuthInit() {
		if(!emailAuthWrap.alreadyInitialized) {
			_emailAuth(emailAuthWrap);
			if(emailAuth == null)
				setEmailAuth(emailAuthWrap.o);
		}
		emailAuthWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrEmailAuth(SiteRequestEnUS siteRequest_, Boolean o) {
		return null;
	}

	public static String staticSolrStrEmailAuth(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqEmailAuth(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrEmailAuth(siteRequest_, SiteConfig.staticSolrEmailAuth(siteRequest_, SiteConfig.staticSetEmailAuth(siteRequest_, o)));
	}

	//////////////
	// emailSsl //
	//////////////

	/**	 The entity emailSsl
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean emailSsl;
	@JsonIgnore
	public Wrap<Boolean> emailSslWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("emailSsl").o(emailSsl);

	/**	<br/> The entity emailSsl
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:emailSsl">Find the entity emailSsl in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _emailSsl(Wrap<Boolean> c);

	public Boolean getEmailSsl() {
		return emailSsl;
	}

	public void setEmailSsl(Boolean emailSsl) {
		this.emailSsl = emailSsl;
		this.emailSslWrap.alreadyInitialized = true;
	}
	public void setEmailSsl(String o) {
		this.emailSsl = SiteConfig.staticSetEmailSsl(null, o);
		this.emailSslWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetEmailSsl(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SiteConfig emailSslInit() {
		if(!emailSslWrap.alreadyInitialized) {
			_emailSsl(emailSslWrap);
			if(emailSsl == null)
				setEmailSsl(emailSslWrap.o);
		}
		emailSslWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrEmailSsl(SiteRequestEnUS siteRequest_, Boolean o) {
		return null;
	}

	public static String staticSolrStrEmailSsl(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqEmailSsl(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrEmailSsl(siteRequest_, SiteConfig.staticSolrEmailSsl(siteRequest_, SiteConfig.staticSetEmailSsl(siteRequest_, o)));
	}

	//////////////
	// siteZone //
	//////////////

	/**	 The entity siteZone
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String siteZone;
	@JsonIgnore
	public Wrap<String> siteZoneWrap = new Wrap<String>().p(this).c(String.class).var("siteZone").o(siteZone);

	/**	<br/> The entity siteZone
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.config.SiteConfig&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:siteZone">Find the entity siteZone in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _siteZone(Wrap<String> c);

	public String getSiteZone() {
		return siteZone;
	}
	public void setSiteZone(String o) {
		this.siteZone = SiteConfig.staticSetSiteZone(null, o);
		this.siteZoneWrap.alreadyInitialized = true;
	}
	public static String staticSetSiteZone(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteConfig siteZoneInit() {
		if(!siteZoneWrap.alreadyInitialized) {
			_siteZone(siteZoneWrap);
			if(siteZone == null)
				setSiteZone(siteZoneWrap.o);
		}
		siteZoneWrap.alreadyInitialized(true);
		return (SiteConfig)this;
	}

	public static Object staticSolrSiteZone(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}

	public static String staticSolrStrSiteZone(SiteRequestEnUS siteRequest_, Object o) {
		return null;
	}

	public static String staticSolrFqSiteZone(SiteRequestEnUS siteRequest_, String o) {
		return SiteConfig.staticSolrStrSiteZone(siteRequest_, SiteConfig.staticSolrSiteZone(siteRequest_, SiteConfig.staticSetSiteZone(siteRequest_, o)));
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedSiteConfig = false;

	public SiteConfig initDeepSiteConfig(SiteRequestEnUS siteRequest_) {
		if(!alreadyInitializedSiteConfig) {
			alreadyInitializedSiteConfig = true;
			initDeepSiteConfig();
		}
		return (SiteConfig)this;
	}

	public void initDeepSiteConfig() {
		initSiteConfig();
	}

	public void initSiteConfig() {
		configPathInit();
		configInit();
		siteIdentifierInit();
		appPathInit();
		docRootInit();
		companyNameInit();
		domainNameInit();
		siteHostNameInit();
		sitePortInit();
		siteInstancesInit();
		authRealmInit();
		authResourceInit();
		authSecretInit();
		authSslRequiredInit();
		sslPassthroughInit();
		sslJksPathInit();
		sslJksPasswordInit();
		authUrlInit();
		encryptionSaltInit();
		encryptionPasswordInit();
		siteBaseUrlInit();
		siteDisplayNameInit();
		jdbcDriverClassInit();
		jdbcUsernameInit();
		jdbcPasswordInit();
		jdbcMaxPoolSizeInit();
		jdbcMaxWaitQueueSizeInit();
		jdbcMinPoolSizeInit();
		jdbcMaxStatementsInit();
		jdbcMaxStatementsPerConnectionInit();
		jdbcMaxIdleTimeInit();
		jdbcConnectTimeoutInit();
		jdbcHostInit();
		jdbcPortInit();
		jdbcDatabaseInit();
		solrUrlInit();
		solrUrlComputateInit();
		accountFacebookInit();
		accountTwitterInit();
		accountInstagramInit();
		accountYoutubeInit();
		accountPinterestInit();
		accountEmailInit();
		roleAdminInit();
		emailAdminInit();
		numberExecutorsInit();
		openApiVersionInit();
		apiDescriptionInit();
		apiTitleInit();
		apiTermsServiceInit();
		apiVersionInit();
		apiContactEmailInit();
		apiLicenseNameInit();
		apiLicenseUrlInit();
		apiHostNameInit();
		apiBasePathInit();
		staticBaseUrlInit();
		staticPathInit();
		importPathInit();
		emailHostInit();
		emailPortInit();
		emailUsernameInit();
		emailPasswordInit();
		emailFromInit();
		emailAuthInit();
		emailSslInit();
		siteZoneInit();
	}

	public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepSiteConfig(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainSiteConfig(v);
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
	public Object obtainSiteConfig(String var) {
		SiteConfig oSiteConfig = (SiteConfig)this;
		switch(var) {
			case "configPath":
				return oSiteConfig.configPath;
			case "config":
				return oSiteConfig.config;
			case "siteIdentifier":
				return oSiteConfig.siteIdentifier;
			case "appPath":
				return oSiteConfig.appPath;
			case "docRoot":
				return oSiteConfig.docRoot;
			case "companyName":
				return oSiteConfig.companyName;
			case "domainName":
				return oSiteConfig.domainName;
			case "siteHostName":
				return oSiteConfig.siteHostName;
			case "sitePort":
				return oSiteConfig.sitePort;
			case "siteInstances":
				return oSiteConfig.siteInstances;
			case "authRealm":
				return oSiteConfig.authRealm;
			case "authResource":
				return oSiteConfig.authResource;
			case "authSecret":
				return oSiteConfig.authSecret;
			case "authSslRequired":
				return oSiteConfig.authSslRequired;
			case "sslPassthrough":
				return oSiteConfig.sslPassthrough;
			case "sslJksPath":
				return oSiteConfig.sslJksPath;
			case "sslJksPassword":
				return oSiteConfig.sslJksPassword;
			case "authUrl":
				return oSiteConfig.authUrl;
			case "encryptionSalt":
				return oSiteConfig.encryptionSalt;
			case "encryptionPassword":
				return oSiteConfig.encryptionPassword;
			case "siteBaseUrl":
				return oSiteConfig.siteBaseUrl;
			case "siteDisplayName":
				return oSiteConfig.siteDisplayName;
			case "jdbcDriverClass":
				return oSiteConfig.jdbcDriverClass;
			case "jdbcUsername":
				return oSiteConfig.jdbcUsername;
			case "jdbcPassword":
				return oSiteConfig.jdbcPassword;
			case "jdbcMaxPoolSize":
				return oSiteConfig.jdbcMaxPoolSize;
			case "jdbcMaxWaitQueueSize":
				return oSiteConfig.jdbcMaxWaitQueueSize;
			case "jdbcMinPoolSize":
				return oSiteConfig.jdbcMinPoolSize;
			case "jdbcMaxStatements":
				return oSiteConfig.jdbcMaxStatements;
			case "jdbcMaxStatementsPerConnection":
				return oSiteConfig.jdbcMaxStatementsPerConnection;
			case "jdbcMaxIdleTime":
				return oSiteConfig.jdbcMaxIdleTime;
			case "jdbcConnectTimeout":
				return oSiteConfig.jdbcConnectTimeout;
			case "jdbcHost":
				return oSiteConfig.jdbcHost;
			case "jdbcPort":
				return oSiteConfig.jdbcPort;
			case "jdbcDatabase":
				return oSiteConfig.jdbcDatabase;
			case "solrUrl":
				return oSiteConfig.solrUrl;
			case "solrUrlComputate":
				return oSiteConfig.solrUrlComputate;
			case "accountFacebook":
				return oSiteConfig.accountFacebook;
			case "accountTwitter":
				return oSiteConfig.accountTwitter;
			case "accountInstagram":
				return oSiteConfig.accountInstagram;
			case "accountYoutube":
				return oSiteConfig.accountYoutube;
			case "accountPinterest":
				return oSiteConfig.accountPinterest;
			case "accountEmail":
				return oSiteConfig.accountEmail;
			case "roleAdmin":
				return oSiteConfig.roleAdmin;
			case "emailAdmin":
				return oSiteConfig.emailAdmin;
			case "numberExecutors":
				return oSiteConfig.numberExecutors;
			case "openApiVersion":
				return oSiteConfig.openApiVersion;
			case "apiDescription":
				return oSiteConfig.apiDescription;
			case "apiTitle":
				return oSiteConfig.apiTitle;
			case "apiTermsService":
				return oSiteConfig.apiTermsService;
			case "apiVersion":
				return oSiteConfig.apiVersion;
			case "apiContactEmail":
				return oSiteConfig.apiContactEmail;
			case "apiLicenseName":
				return oSiteConfig.apiLicenseName;
			case "apiLicenseUrl":
				return oSiteConfig.apiLicenseUrl;
			case "apiHostName":
				return oSiteConfig.apiHostName;
			case "apiBasePath":
				return oSiteConfig.apiBasePath;
			case "staticBaseUrl":
				return oSiteConfig.staticBaseUrl;
			case "staticPath":
				return oSiteConfig.staticPath;
			case "importPath":
				return oSiteConfig.importPath;
			case "emailHost":
				return oSiteConfig.emailHost;
			case "emailPort":
				return oSiteConfig.emailPort;
			case "emailUsername":
				return oSiteConfig.emailUsername;
			case "emailPassword":
				return oSiteConfig.emailPassword;
			case "emailFrom":
				return oSiteConfig.emailFrom;
			case "emailAuth":
				return oSiteConfig.emailAuth;
			case "emailSsl":
				return oSiteConfig.emailSsl;
			case "siteZone":
				return oSiteConfig.siteZone;
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
				o = attributeSiteConfig(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeSiteConfig(String var, Object val) {
		SiteConfig oSiteConfig = (SiteConfig)this;
		switch(var) {
			default:
				return null;
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetSiteConfig(entityVar,  siteRequest_, o);
	}
	public static Object staticSetSiteConfig(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "configPath":
			return SiteConfig.staticSetConfigPath(siteRequest_, o);
		case "siteIdentifier":
			return SiteConfig.staticSetSiteIdentifier(siteRequest_, o);
		case "appPath":
			return SiteConfig.staticSetAppPath(siteRequest_, o);
		case "docRoot":
			return SiteConfig.staticSetDocRoot(siteRequest_, o);
		case "companyName":
			return SiteConfig.staticSetCompanyName(siteRequest_, o);
		case "domainName":
			return SiteConfig.staticSetDomainName(siteRequest_, o);
		case "siteHostName":
			return SiteConfig.staticSetSiteHostName(siteRequest_, o);
		case "sitePort":
			return SiteConfig.staticSetSitePort(siteRequest_, o);
		case "siteInstances":
			return SiteConfig.staticSetSiteInstances(siteRequest_, o);
		case "authRealm":
			return SiteConfig.staticSetAuthRealm(siteRequest_, o);
		case "authResource":
			return SiteConfig.staticSetAuthResource(siteRequest_, o);
		case "authSecret":
			return SiteConfig.staticSetAuthSecret(siteRequest_, o);
		case "authSslRequired":
			return SiteConfig.staticSetAuthSslRequired(siteRequest_, o);
		case "sslPassthrough":
			return SiteConfig.staticSetSslPassthrough(siteRequest_, o);
		case "sslJksPath":
			return SiteConfig.staticSetSslJksPath(siteRequest_, o);
		case "sslJksPassword":
			return SiteConfig.staticSetSslJksPassword(siteRequest_, o);
		case "authUrl":
			return SiteConfig.staticSetAuthUrl(siteRequest_, o);
		case "encryptionSalt":
			return SiteConfig.staticSetEncryptionSalt(siteRequest_, o);
		case "encryptionPassword":
			return SiteConfig.staticSetEncryptionPassword(siteRequest_, o);
		case "siteBaseUrl":
			return SiteConfig.staticSetSiteBaseUrl(siteRequest_, o);
		case "siteDisplayName":
			return SiteConfig.staticSetSiteDisplayName(siteRequest_, o);
		case "jdbcDriverClass":
			return SiteConfig.staticSetJdbcDriverClass(siteRequest_, o);
		case "jdbcUsername":
			return SiteConfig.staticSetJdbcUsername(siteRequest_, o);
		case "jdbcPassword":
			return SiteConfig.staticSetJdbcPassword(siteRequest_, o);
		case "jdbcMaxPoolSize":
			return SiteConfig.staticSetJdbcMaxPoolSize(siteRequest_, o);
		case "jdbcMaxWaitQueueSize":
			return SiteConfig.staticSetJdbcMaxWaitQueueSize(siteRequest_, o);
		case "jdbcMinPoolSize":
			return SiteConfig.staticSetJdbcMinPoolSize(siteRequest_, o);
		case "jdbcMaxStatements":
			return SiteConfig.staticSetJdbcMaxStatements(siteRequest_, o);
		case "jdbcMaxStatementsPerConnection":
			return SiteConfig.staticSetJdbcMaxStatementsPerConnection(siteRequest_, o);
		case "jdbcMaxIdleTime":
			return SiteConfig.staticSetJdbcMaxIdleTime(siteRequest_, o);
		case "jdbcConnectTimeout":
			return SiteConfig.staticSetJdbcConnectTimeout(siteRequest_, o);
		case "jdbcHost":
			return SiteConfig.staticSetJdbcHost(siteRequest_, o);
		case "jdbcPort":
			return SiteConfig.staticSetJdbcPort(siteRequest_, o);
		case "jdbcDatabase":
			return SiteConfig.staticSetJdbcDatabase(siteRequest_, o);
		case "solrUrl":
			return SiteConfig.staticSetSolrUrl(siteRequest_, o);
		case "solrUrlComputate":
			return SiteConfig.staticSetSolrUrlComputate(siteRequest_, o);
		case "accountFacebook":
			return SiteConfig.staticSetAccountFacebook(siteRequest_, o);
		case "accountTwitter":
			return SiteConfig.staticSetAccountTwitter(siteRequest_, o);
		case "accountInstagram":
			return SiteConfig.staticSetAccountInstagram(siteRequest_, o);
		case "accountYoutube":
			return SiteConfig.staticSetAccountYoutube(siteRequest_, o);
		case "accountPinterest":
			return SiteConfig.staticSetAccountPinterest(siteRequest_, o);
		case "accountEmail":
			return SiteConfig.staticSetAccountEmail(siteRequest_, o);
		case "roleAdmin":
			return SiteConfig.staticSetRoleAdmin(siteRequest_, o);
		case "emailAdmin":
			return SiteConfig.staticSetEmailAdmin(siteRequest_, o);
		case "numberExecutors":
			return SiteConfig.staticSetNumberExecutors(siteRequest_, o);
		case "openApiVersion":
			return SiteConfig.staticSetOpenApiVersion(siteRequest_, o);
		case "apiDescription":
			return SiteConfig.staticSetApiDescription(siteRequest_, o);
		case "apiTitle":
			return SiteConfig.staticSetApiTitle(siteRequest_, o);
		case "apiTermsService":
			return SiteConfig.staticSetApiTermsService(siteRequest_, o);
		case "apiVersion":
			return SiteConfig.staticSetApiVersion(siteRequest_, o);
		case "apiContactEmail":
			return SiteConfig.staticSetApiContactEmail(siteRequest_, o);
		case "apiLicenseName":
			return SiteConfig.staticSetApiLicenseName(siteRequest_, o);
		case "apiLicenseUrl":
			return SiteConfig.staticSetApiLicenseUrl(siteRequest_, o);
		case "apiHostName":
			return SiteConfig.staticSetApiHostName(siteRequest_, o);
		case "apiBasePath":
			return SiteConfig.staticSetApiBasePath(siteRequest_, o);
		case "staticBaseUrl":
			return SiteConfig.staticSetStaticBaseUrl(siteRequest_, o);
		case "staticPath":
			return SiteConfig.staticSetStaticPath(siteRequest_, o);
		case "importPath":
			return SiteConfig.staticSetImportPath(siteRequest_, o);
		case "emailHost":
			return SiteConfig.staticSetEmailHost(siteRequest_, o);
		case "emailPort":
			return SiteConfig.staticSetEmailPort(siteRequest_, o);
		case "emailUsername":
			return SiteConfig.staticSetEmailUsername(siteRequest_, o);
		case "emailPassword":
			return SiteConfig.staticSetEmailPassword(siteRequest_, o);
		case "emailFrom":
			return SiteConfig.staticSetEmailFrom(siteRequest_, o);
		case "emailAuth":
			return SiteConfig.staticSetEmailAuth(siteRequest_, o);
		case "emailSsl":
			return SiteConfig.staticSetEmailSsl(siteRequest_, o);
		case "siteZone":
			return SiteConfig.staticSetSiteZone(siteRequest_, o);
			default:
				return null;
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrSiteConfig(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrSiteConfig(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "configPath":
			return SiteConfig.staticSolrConfigPath(siteRequest_, (String)o);
		case "siteIdentifier":
			return SiteConfig.staticSolrSiteIdentifier(siteRequest_, (String)o);
		case "appPath":
			return SiteConfig.staticSolrAppPath(siteRequest_, (String)o);
		case "docRoot":
			return SiteConfig.staticSolrDocRoot(siteRequest_, (String)o);
		case "companyName":
			return SiteConfig.staticSolrCompanyName(siteRequest_, (String)o);
		case "domainName":
			return SiteConfig.staticSolrDomainName(siteRequest_, (String)o);
		case "siteHostName":
			return SiteConfig.staticSolrSiteHostName(siteRequest_, (String)o);
		case "sitePort":
			return SiteConfig.staticSolrSitePort(siteRequest_, (Integer)o);
		case "siteInstances":
			return SiteConfig.staticSolrSiteInstances(siteRequest_, (Integer)o);
		case "authRealm":
			return SiteConfig.staticSolrAuthRealm(siteRequest_, (String)o);
		case "authResource":
			return SiteConfig.staticSolrAuthResource(siteRequest_, (String)o);
		case "authSecret":
			return SiteConfig.staticSolrAuthSecret(siteRequest_, (String)o);
		case "authSslRequired":
			return SiteConfig.staticSolrAuthSslRequired(siteRequest_, (String)o);
		case "sslPassthrough":
			return SiteConfig.staticSolrSslPassthrough(siteRequest_, (Boolean)o);
		case "sslJksPath":
			return SiteConfig.staticSolrSslJksPath(siteRequest_, (String)o);
		case "sslJksPassword":
			return SiteConfig.staticSolrSslJksPassword(siteRequest_, (String)o);
		case "authUrl":
			return SiteConfig.staticSolrAuthUrl(siteRequest_, (String)o);
		case "encryptionSalt":
			return SiteConfig.staticSolrEncryptionSalt(siteRequest_, (String)o);
		case "encryptionPassword":
			return SiteConfig.staticSolrEncryptionPassword(siteRequest_, (String)o);
		case "siteBaseUrl":
			return SiteConfig.staticSolrSiteBaseUrl(siteRequest_, (String)o);
		case "siteDisplayName":
			return SiteConfig.staticSolrSiteDisplayName(siteRequest_, (String)o);
		case "jdbcDriverClass":
			return SiteConfig.staticSolrJdbcDriverClass(siteRequest_, (String)o);
		case "jdbcUsername":
			return SiteConfig.staticSolrJdbcUsername(siteRequest_, (String)o);
		case "jdbcPassword":
			return SiteConfig.staticSolrJdbcPassword(siteRequest_, (String)o);
		case "jdbcMaxPoolSize":
			return SiteConfig.staticSolrJdbcMaxPoolSize(siteRequest_, (Integer)o);
		case "jdbcMaxWaitQueueSize":
			return SiteConfig.staticSolrJdbcMaxWaitQueueSize(siteRequest_, (Integer)o);
		case "jdbcMinPoolSize":
			return SiteConfig.staticSolrJdbcMinPoolSize(siteRequest_, (Integer)o);
		case "jdbcMaxStatements":
			return SiteConfig.staticSolrJdbcMaxStatements(siteRequest_, (Integer)o);
		case "jdbcMaxStatementsPerConnection":
			return SiteConfig.staticSolrJdbcMaxStatementsPerConnection(siteRequest_, (Integer)o);
		case "jdbcMaxIdleTime":
			return SiteConfig.staticSolrJdbcMaxIdleTime(siteRequest_, (Integer)o);
		case "jdbcConnectTimeout":
			return SiteConfig.staticSolrJdbcConnectTimeout(siteRequest_, (Integer)o);
		case "jdbcHost":
			return SiteConfig.staticSolrJdbcHost(siteRequest_, (String)o);
		case "jdbcPort":
			return SiteConfig.staticSolrJdbcPort(siteRequest_, (Integer)o);
		case "jdbcDatabase":
			return SiteConfig.staticSolrJdbcDatabase(siteRequest_, (String)o);
		case "solrUrl":
			return SiteConfig.staticSolrSolrUrl(siteRequest_, (String)o);
		case "solrUrlComputate":
			return SiteConfig.staticSolrSolrUrlComputate(siteRequest_, (String)o);
		case "accountFacebook":
			return SiteConfig.staticSolrAccountFacebook(siteRequest_, (String)o);
		case "accountTwitter":
			return SiteConfig.staticSolrAccountTwitter(siteRequest_, (String)o);
		case "accountInstagram":
			return SiteConfig.staticSolrAccountInstagram(siteRequest_, (String)o);
		case "accountYoutube":
			return SiteConfig.staticSolrAccountYoutube(siteRequest_, (String)o);
		case "accountPinterest":
			return SiteConfig.staticSolrAccountPinterest(siteRequest_, (String)o);
		case "accountEmail":
			return SiteConfig.staticSolrAccountEmail(siteRequest_, (String)o);
		case "roleAdmin":
			return SiteConfig.staticSolrRoleAdmin(siteRequest_, (String)o);
		case "emailAdmin":
			return SiteConfig.staticSolrEmailAdmin(siteRequest_, (String)o);
		case "numberExecutors":
			return SiteConfig.staticSolrNumberExecutors(siteRequest_, (Integer)o);
		case "openApiVersion":
			return SiteConfig.staticSolrOpenApiVersion(siteRequest_, (String)o);
		case "apiDescription":
			return SiteConfig.staticSolrApiDescription(siteRequest_, (String)o);
		case "apiTitle":
			return SiteConfig.staticSolrApiTitle(siteRequest_, (String)o);
		case "apiTermsService":
			return SiteConfig.staticSolrApiTermsService(siteRequest_, (String)o);
		case "apiVersion":
			return SiteConfig.staticSolrApiVersion(siteRequest_, (String)o);
		case "apiContactEmail":
			return SiteConfig.staticSolrApiContactEmail(siteRequest_, (String)o);
		case "apiLicenseName":
			return SiteConfig.staticSolrApiLicenseName(siteRequest_, (String)o);
		case "apiLicenseUrl":
			return SiteConfig.staticSolrApiLicenseUrl(siteRequest_, (String)o);
		case "apiHostName":
			return SiteConfig.staticSolrApiHostName(siteRequest_, (String)o);
		case "apiBasePath":
			return SiteConfig.staticSolrApiBasePath(siteRequest_, (String)o);
		case "staticBaseUrl":
			return SiteConfig.staticSolrStaticBaseUrl(siteRequest_, (String)o);
		case "staticPath":
			return SiteConfig.staticSolrStaticPath(siteRequest_, (String)o);
		case "importPath":
			return SiteConfig.staticSolrImportPath(siteRequest_, (String)o);
		case "emailHost":
			return SiteConfig.staticSolrEmailHost(siteRequest_, (String)o);
		case "emailPort":
			return SiteConfig.staticSolrEmailPort(siteRequest_, (Integer)o);
		case "emailUsername":
			return SiteConfig.staticSolrEmailUsername(siteRequest_, (String)o);
		case "emailPassword":
			return SiteConfig.staticSolrEmailPassword(siteRequest_, (String)o);
		case "emailFrom":
			return SiteConfig.staticSolrEmailFrom(siteRequest_, (String)o);
		case "emailAuth":
			return SiteConfig.staticSolrEmailAuth(siteRequest_, (Boolean)o);
		case "emailSsl":
			return SiteConfig.staticSolrEmailSsl(siteRequest_, (Boolean)o);
		case "siteZone":
			return SiteConfig.staticSolrSiteZone(siteRequest_, (String)o);
			default:
				return null;
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrSiteConfig(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrSiteConfig(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "configPath":
			return SiteConfig.staticSolrStrConfigPath(siteRequest_, (String)o);
		case "siteIdentifier":
			return SiteConfig.staticSolrStrSiteIdentifier(siteRequest_, (String)o);
		case "appPath":
			return SiteConfig.staticSolrStrAppPath(siteRequest_, (String)o);
		case "docRoot":
			return SiteConfig.staticSolrStrDocRoot(siteRequest_, (String)o);
		case "companyName":
			return SiteConfig.staticSolrStrCompanyName(siteRequest_, (String)o);
		case "domainName":
			return SiteConfig.staticSolrStrDomainName(siteRequest_, (String)o);
		case "siteHostName":
			return SiteConfig.staticSolrStrSiteHostName(siteRequest_, (String)o);
		case "sitePort":
			return SiteConfig.staticSolrStrSitePort(siteRequest_, (Integer)o);
		case "siteInstances":
			return SiteConfig.staticSolrStrSiteInstances(siteRequest_, (Integer)o);
		case "authRealm":
			return SiteConfig.staticSolrStrAuthRealm(siteRequest_, (String)o);
		case "authResource":
			return SiteConfig.staticSolrStrAuthResource(siteRequest_, (String)o);
		case "authSecret":
			return SiteConfig.staticSolrStrAuthSecret(siteRequest_, (String)o);
		case "authSslRequired":
			return SiteConfig.staticSolrStrAuthSslRequired(siteRequest_, (String)o);
		case "sslPassthrough":
			return SiteConfig.staticSolrStrSslPassthrough(siteRequest_, (Boolean)o);
		case "sslJksPath":
			return SiteConfig.staticSolrStrSslJksPath(siteRequest_, (String)o);
		case "sslJksPassword":
			return SiteConfig.staticSolrStrSslJksPassword(siteRequest_, (String)o);
		case "authUrl":
			return SiteConfig.staticSolrStrAuthUrl(siteRequest_, (String)o);
		case "encryptionSalt":
			return SiteConfig.staticSolrStrEncryptionSalt(siteRequest_, (String)o);
		case "encryptionPassword":
			return SiteConfig.staticSolrStrEncryptionPassword(siteRequest_, (String)o);
		case "siteBaseUrl":
			return SiteConfig.staticSolrStrSiteBaseUrl(siteRequest_, (String)o);
		case "siteDisplayName":
			return SiteConfig.staticSolrStrSiteDisplayName(siteRequest_, (String)o);
		case "jdbcDriverClass":
			return SiteConfig.staticSolrStrJdbcDriverClass(siteRequest_, (String)o);
		case "jdbcUsername":
			return SiteConfig.staticSolrStrJdbcUsername(siteRequest_, (String)o);
		case "jdbcPassword":
			return SiteConfig.staticSolrStrJdbcPassword(siteRequest_, (String)o);
		case "jdbcMaxPoolSize":
			return SiteConfig.staticSolrStrJdbcMaxPoolSize(siteRequest_, (Integer)o);
		case "jdbcMaxWaitQueueSize":
			return SiteConfig.staticSolrStrJdbcMaxWaitQueueSize(siteRequest_, (Integer)o);
		case "jdbcMinPoolSize":
			return SiteConfig.staticSolrStrJdbcMinPoolSize(siteRequest_, (Integer)o);
		case "jdbcMaxStatements":
			return SiteConfig.staticSolrStrJdbcMaxStatements(siteRequest_, (Integer)o);
		case "jdbcMaxStatementsPerConnection":
			return SiteConfig.staticSolrStrJdbcMaxStatementsPerConnection(siteRequest_, (Integer)o);
		case "jdbcMaxIdleTime":
			return SiteConfig.staticSolrStrJdbcMaxIdleTime(siteRequest_, (Integer)o);
		case "jdbcConnectTimeout":
			return SiteConfig.staticSolrStrJdbcConnectTimeout(siteRequest_, (Integer)o);
		case "jdbcHost":
			return SiteConfig.staticSolrStrJdbcHost(siteRequest_, (String)o);
		case "jdbcPort":
			return SiteConfig.staticSolrStrJdbcPort(siteRequest_, (Integer)o);
		case "jdbcDatabase":
			return SiteConfig.staticSolrStrJdbcDatabase(siteRequest_, (String)o);
		case "solrUrl":
			return SiteConfig.staticSolrStrSolrUrl(siteRequest_, (String)o);
		case "solrUrlComputate":
			return SiteConfig.staticSolrStrSolrUrlComputate(siteRequest_, (String)o);
		case "accountFacebook":
			return SiteConfig.staticSolrStrAccountFacebook(siteRequest_, (String)o);
		case "accountTwitter":
			return SiteConfig.staticSolrStrAccountTwitter(siteRequest_, (String)o);
		case "accountInstagram":
			return SiteConfig.staticSolrStrAccountInstagram(siteRequest_, (String)o);
		case "accountYoutube":
			return SiteConfig.staticSolrStrAccountYoutube(siteRequest_, (String)o);
		case "accountPinterest":
			return SiteConfig.staticSolrStrAccountPinterest(siteRequest_, (String)o);
		case "accountEmail":
			return SiteConfig.staticSolrStrAccountEmail(siteRequest_, (String)o);
		case "roleAdmin":
			return SiteConfig.staticSolrStrRoleAdmin(siteRequest_, (String)o);
		case "emailAdmin":
			return SiteConfig.staticSolrStrEmailAdmin(siteRequest_, (String)o);
		case "numberExecutors":
			return SiteConfig.staticSolrStrNumberExecutors(siteRequest_, (Integer)o);
		case "openApiVersion":
			return SiteConfig.staticSolrStrOpenApiVersion(siteRequest_, (String)o);
		case "apiDescription":
			return SiteConfig.staticSolrStrApiDescription(siteRequest_, (String)o);
		case "apiTitle":
			return SiteConfig.staticSolrStrApiTitle(siteRequest_, (String)o);
		case "apiTermsService":
			return SiteConfig.staticSolrStrApiTermsService(siteRequest_, (String)o);
		case "apiVersion":
			return SiteConfig.staticSolrStrApiVersion(siteRequest_, (String)o);
		case "apiContactEmail":
			return SiteConfig.staticSolrStrApiContactEmail(siteRequest_, (String)o);
		case "apiLicenseName":
			return SiteConfig.staticSolrStrApiLicenseName(siteRequest_, (String)o);
		case "apiLicenseUrl":
			return SiteConfig.staticSolrStrApiLicenseUrl(siteRequest_, (String)o);
		case "apiHostName":
			return SiteConfig.staticSolrStrApiHostName(siteRequest_, (String)o);
		case "apiBasePath":
			return SiteConfig.staticSolrStrApiBasePath(siteRequest_, (String)o);
		case "staticBaseUrl":
			return SiteConfig.staticSolrStrStaticBaseUrl(siteRequest_, (String)o);
		case "staticPath":
			return SiteConfig.staticSolrStrStaticPath(siteRequest_, (String)o);
		case "importPath":
			return SiteConfig.staticSolrStrImportPath(siteRequest_, (String)o);
		case "emailHost":
			return SiteConfig.staticSolrStrEmailHost(siteRequest_, (String)o);
		case "emailPort":
			return SiteConfig.staticSolrStrEmailPort(siteRequest_, (Integer)o);
		case "emailUsername":
			return SiteConfig.staticSolrStrEmailUsername(siteRequest_, (String)o);
		case "emailPassword":
			return SiteConfig.staticSolrStrEmailPassword(siteRequest_, (String)o);
		case "emailFrom":
			return SiteConfig.staticSolrStrEmailFrom(siteRequest_, (String)o);
		case "emailAuth":
			return SiteConfig.staticSolrStrEmailAuth(siteRequest_, (Boolean)o);
		case "emailSsl":
			return SiteConfig.staticSolrStrEmailSsl(siteRequest_, (Boolean)o);
		case "siteZone":
			return SiteConfig.staticSolrStrSiteZone(siteRequest_, (String)o);
			default:
				return null;
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqSiteConfig(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqSiteConfig(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "configPath":
			return SiteConfig.staticSolrFqConfigPath(siteRequest_, o);
		case "siteIdentifier":
			return SiteConfig.staticSolrFqSiteIdentifier(siteRequest_, o);
		case "appPath":
			return SiteConfig.staticSolrFqAppPath(siteRequest_, o);
		case "docRoot":
			return SiteConfig.staticSolrFqDocRoot(siteRequest_, o);
		case "companyName":
			return SiteConfig.staticSolrFqCompanyName(siteRequest_, o);
		case "domainName":
			return SiteConfig.staticSolrFqDomainName(siteRequest_, o);
		case "siteHostName":
			return SiteConfig.staticSolrFqSiteHostName(siteRequest_, o);
		case "sitePort":
			return SiteConfig.staticSolrFqSitePort(siteRequest_, o);
		case "siteInstances":
			return SiteConfig.staticSolrFqSiteInstances(siteRequest_, o);
		case "authRealm":
			return SiteConfig.staticSolrFqAuthRealm(siteRequest_, o);
		case "authResource":
			return SiteConfig.staticSolrFqAuthResource(siteRequest_, o);
		case "authSecret":
			return SiteConfig.staticSolrFqAuthSecret(siteRequest_, o);
		case "authSslRequired":
			return SiteConfig.staticSolrFqAuthSslRequired(siteRequest_, o);
		case "sslPassthrough":
			return SiteConfig.staticSolrFqSslPassthrough(siteRequest_, o);
		case "sslJksPath":
			return SiteConfig.staticSolrFqSslJksPath(siteRequest_, o);
		case "sslJksPassword":
			return SiteConfig.staticSolrFqSslJksPassword(siteRequest_, o);
		case "authUrl":
			return SiteConfig.staticSolrFqAuthUrl(siteRequest_, o);
		case "encryptionSalt":
			return SiteConfig.staticSolrFqEncryptionSalt(siteRequest_, o);
		case "encryptionPassword":
			return SiteConfig.staticSolrFqEncryptionPassword(siteRequest_, o);
		case "siteBaseUrl":
			return SiteConfig.staticSolrFqSiteBaseUrl(siteRequest_, o);
		case "siteDisplayName":
			return SiteConfig.staticSolrFqSiteDisplayName(siteRequest_, o);
		case "jdbcDriverClass":
			return SiteConfig.staticSolrFqJdbcDriverClass(siteRequest_, o);
		case "jdbcUsername":
			return SiteConfig.staticSolrFqJdbcUsername(siteRequest_, o);
		case "jdbcPassword":
			return SiteConfig.staticSolrFqJdbcPassword(siteRequest_, o);
		case "jdbcMaxPoolSize":
			return SiteConfig.staticSolrFqJdbcMaxPoolSize(siteRequest_, o);
		case "jdbcMaxWaitQueueSize":
			return SiteConfig.staticSolrFqJdbcMaxWaitQueueSize(siteRequest_, o);
		case "jdbcMinPoolSize":
			return SiteConfig.staticSolrFqJdbcMinPoolSize(siteRequest_, o);
		case "jdbcMaxStatements":
			return SiteConfig.staticSolrFqJdbcMaxStatements(siteRequest_, o);
		case "jdbcMaxStatementsPerConnection":
			return SiteConfig.staticSolrFqJdbcMaxStatementsPerConnection(siteRequest_, o);
		case "jdbcMaxIdleTime":
			return SiteConfig.staticSolrFqJdbcMaxIdleTime(siteRequest_, o);
		case "jdbcConnectTimeout":
			return SiteConfig.staticSolrFqJdbcConnectTimeout(siteRequest_, o);
		case "jdbcHost":
			return SiteConfig.staticSolrFqJdbcHost(siteRequest_, o);
		case "jdbcPort":
			return SiteConfig.staticSolrFqJdbcPort(siteRequest_, o);
		case "jdbcDatabase":
			return SiteConfig.staticSolrFqJdbcDatabase(siteRequest_, o);
		case "solrUrl":
			return SiteConfig.staticSolrFqSolrUrl(siteRequest_, o);
		case "solrUrlComputate":
			return SiteConfig.staticSolrFqSolrUrlComputate(siteRequest_, o);
		case "accountFacebook":
			return SiteConfig.staticSolrFqAccountFacebook(siteRequest_, o);
		case "accountTwitter":
			return SiteConfig.staticSolrFqAccountTwitter(siteRequest_, o);
		case "accountInstagram":
			return SiteConfig.staticSolrFqAccountInstagram(siteRequest_, o);
		case "accountYoutube":
			return SiteConfig.staticSolrFqAccountYoutube(siteRequest_, o);
		case "accountPinterest":
			return SiteConfig.staticSolrFqAccountPinterest(siteRequest_, o);
		case "accountEmail":
			return SiteConfig.staticSolrFqAccountEmail(siteRequest_, o);
		case "roleAdmin":
			return SiteConfig.staticSolrFqRoleAdmin(siteRequest_, o);
		case "emailAdmin":
			return SiteConfig.staticSolrFqEmailAdmin(siteRequest_, o);
		case "numberExecutors":
			return SiteConfig.staticSolrFqNumberExecutors(siteRequest_, o);
		case "openApiVersion":
			return SiteConfig.staticSolrFqOpenApiVersion(siteRequest_, o);
		case "apiDescription":
			return SiteConfig.staticSolrFqApiDescription(siteRequest_, o);
		case "apiTitle":
			return SiteConfig.staticSolrFqApiTitle(siteRequest_, o);
		case "apiTermsService":
			return SiteConfig.staticSolrFqApiTermsService(siteRequest_, o);
		case "apiVersion":
			return SiteConfig.staticSolrFqApiVersion(siteRequest_, o);
		case "apiContactEmail":
			return SiteConfig.staticSolrFqApiContactEmail(siteRequest_, o);
		case "apiLicenseName":
			return SiteConfig.staticSolrFqApiLicenseName(siteRequest_, o);
		case "apiLicenseUrl":
			return SiteConfig.staticSolrFqApiLicenseUrl(siteRequest_, o);
		case "apiHostName":
			return SiteConfig.staticSolrFqApiHostName(siteRequest_, o);
		case "apiBasePath":
			return SiteConfig.staticSolrFqApiBasePath(siteRequest_, o);
		case "staticBaseUrl":
			return SiteConfig.staticSolrFqStaticBaseUrl(siteRequest_, o);
		case "staticPath":
			return SiteConfig.staticSolrFqStaticPath(siteRequest_, o);
		case "importPath":
			return SiteConfig.staticSolrFqImportPath(siteRequest_, o);
		case "emailHost":
			return SiteConfig.staticSolrFqEmailHost(siteRequest_, o);
		case "emailPort":
			return SiteConfig.staticSolrFqEmailPort(siteRequest_, o);
		case "emailUsername":
			return SiteConfig.staticSolrFqEmailUsername(siteRequest_, o);
		case "emailPassword":
			return SiteConfig.staticSolrFqEmailPassword(siteRequest_, o);
		case "emailFrom":
			return SiteConfig.staticSolrFqEmailFrom(siteRequest_, o);
		case "emailAuth":
			return SiteConfig.staticSolrFqEmailAuth(siteRequest_, o);
		case "emailSsl":
			return SiteConfig.staticSolrFqEmailSsl(siteRequest_, o);
		case "siteZone":
			return SiteConfig.staticSolrFqSiteZone(siteRequest_, o);
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
					o = defineSiteConfig(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSiteConfig(String var, String val) {
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
					o = defineSiteConfig(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSiteConfig(String var, Object val) {
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
		if(!(o instanceof SiteConfig))
			return false;
		SiteConfig that = (SiteConfig)o;
		return true;
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("SiteConfig { ");
		sb.append(" }");
		return sb.toString();
	}
}
