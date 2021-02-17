package com.opendatapolicing.enus.agency;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.opendatapolicing.enus.agency.SiteAgencyGenPage;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import io.vertx.core.logging.LoggerFactory;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.vertx.core.logging.Logger;
import java.math.RoundingMode;
import com.opendatapolicing.enus.wrap.Wrap;
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
import com.opendatapolicing.enus.cluster.Cluster;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgencyPage&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class SiteAgencyPageGen<DEV> extends SiteAgencyGenPage {
	protected static final Logger LOGGER = LoggerFactory.getLogger(SiteAgencyPage.class);

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedSiteAgencyPage = false;

	public SiteAgencyPage initDeepSiteAgencyPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedSiteAgencyPage) {
			alreadyInitializedSiteAgencyPage = true;
			initDeepSiteAgencyPage();
		}
		return (SiteAgencyPage)this;
	}

	public void initDeepSiteAgencyPage() {
		initSiteAgencyPage();
		super.initDeepSiteAgencyGenPage(siteRequest_);
	}

	public void initSiteAgencyPage() {
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepSiteAgencyPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestSiteAgencyPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestSiteAgencyGenPage(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestSiteAgencyPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainSiteAgencyPage(v);
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
	public Object obtainSiteAgencyPage(String var) {
		SiteAgencyPage oSiteAgencyPage = (SiteAgencyPage)this;
		switch(var) {
			default:
				return super.obtainSiteAgencyGenPage(var);
		}
	}

	///////////////
	// attribute //
	///////////////

	@Override public boolean attributeForClass(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = attributeSiteAgencyPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeSiteAgencyPage(String var, Object val) {
		SiteAgencyPage oSiteAgencyPage = (SiteAgencyPage)this;
		switch(var) {
			default:
				return super.attributeSiteAgencyGenPage(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetSiteAgencyPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSetSiteAgencyPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return SiteAgencyGenPage.staticSetSiteAgencyGenPage(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrSiteAgencyPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrSiteAgencyPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return SiteAgencyGenPage.staticSolrSiteAgencyGenPage(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrSiteAgencyPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrSiteAgencyPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return SiteAgencyGenPage.staticSolrStrSiteAgencyGenPage(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqSiteAgencyPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqSiteAgencyPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return SiteAgencyGenPage.staticSolrFqSiteAgencyGenPage(entityVar,  siteRequest_, o);
		}
	}

	/////////////
	// define //
	/////////////

	@Override public boolean defineForClass(String var, String val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = defineSiteAgencyPage(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSiteAgencyPage(String var, String val) {
		switch(var.toLowerCase()) {
			default:
				return super.defineSiteAgencyGenPage(var, val);
		}
	}

	@Override public boolean defineForClass(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = defineSiteAgencyPage(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSiteAgencyPage(String var, Object val) {
		switch(var.toLowerCase()) {
			default:
				return super.defineSiteAgencyGenPage(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsSiteAgencyPage();
		super.htmlScripts();
	}

	public void htmlScriptsSiteAgencyPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptSiteAgencyPage();
		super.htmlScript();
	}

	public void htmlScriptSiteAgencyPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodySiteAgencyPage();
		super.htmlBody();
	}

	public void htmlBodySiteAgencyPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlSiteAgencyPage();
		super.html();
	}

	public void htmlSiteAgencyPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaSiteAgencyPage();
		super.htmlMeta();
	}

	public void htmlMetaSiteAgencyPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesSiteAgencyPage();
		super.htmlStyles();
	}

	public void htmlStylesSiteAgencyPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleSiteAgencyPage();
		super.htmlStyle();
	}

	public void htmlStyleSiteAgencyPage() {
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestSiteAgencyPage() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof SiteAgencyPage) {
			SiteAgencyPage original = (SiteAgencyPage)o;
			super.apiRequestSiteAgencyGenPage();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode());
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof SiteAgencyPage))
			return false;
		SiteAgencyPage that = (SiteAgencyPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("SiteAgencyPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
