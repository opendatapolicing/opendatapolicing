package com.opendatapolicing.enus.design;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import io.vertx.core.logging.LoggerFactory;
import java.util.ArrayList;
import com.opendatapolicing.enus.design.DesignDisplayGenPage;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
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
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.design.DesignDisplayPage&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class DesignDisplayPageGen<DEV> extends DesignDisplayGenPage {
	protected static final Logger LOGGER = LoggerFactory.getLogger(DesignDisplayPage.class);

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedDesignDisplayPage = false;

	public DesignDisplayPage initDeepDesignDisplayPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedDesignDisplayPage) {
			alreadyInitializedDesignDisplayPage = true;
			initDeepDesignDisplayPage();
		}
		return (DesignDisplayPage)this;
	}

	public void initDeepDesignDisplayPage() {
		initDesignDisplayPage();
		super.initDeepDesignDisplayGenPage(siteRequest_);
	}

	public void initDesignDisplayPage() {
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepDesignDisplayPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestDesignDisplayPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestDesignDisplayGenPage(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestDesignDisplayPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainDesignDisplayPage(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
		}
		return o;
	}
	public Object obtainDesignDisplayPage(String var) {
		DesignDisplayPage oDesignDisplayPage = (DesignDisplayPage)this;
		switch(var) {
			default:
				return super.obtainDesignDisplayGenPage(var);
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
				o = attributeDesignDisplayPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeDesignDisplayPage(String var, Object val) {
		DesignDisplayPage oDesignDisplayPage = (DesignDisplayPage)this;
		switch(var) {
			default:
				return super.attributeDesignDisplayGenPage(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetDesignDisplayPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSetDesignDisplayPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return DesignDisplayGenPage.staticSetDesignDisplayGenPage(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrDesignDisplayPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrDesignDisplayPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return DesignDisplayGenPage.staticSolrDesignDisplayGenPage(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrDesignDisplayPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrDesignDisplayPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return DesignDisplayGenPage.staticSolrStrDesignDisplayGenPage(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqDesignDisplayPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqDesignDisplayPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return DesignDisplayGenPage.staticSolrFqDesignDisplayGenPage(entityVar,  siteRequest_, o);
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
					o = defineDesignDisplayPage(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineDesignDisplayPage(String var, String val) {
		switch(var) {
			default:
				return super.defineDesignDisplayGenPage(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsDesignDisplayPage();
		super.htmlScripts();
	}

	public void htmlScriptsDesignDisplayPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptDesignDisplayPage();
		super.htmlScript();
	}

	public void htmlScriptDesignDisplayPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodyDesignDisplayPage();
		super.htmlBody();
	}

	public void htmlBodyDesignDisplayPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlDesignDisplayPage();
		super.html();
	}

	public void htmlDesignDisplayPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaDesignDisplayPage();
		super.htmlMeta();
	}

	public void htmlMetaDesignDisplayPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesDesignDisplayPage();
		super.htmlStyles();
	}

	public void htmlStylesDesignDisplayPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleDesignDisplayPage();
		super.htmlStyle();
	}

	public void htmlStyleDesignDisplayPage() {
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestDesignDisplayPage() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof DesignDisplayPage) {
			DesignDisplayPage original = (DesignDisplayPage)o;
			super.apiRequestDesignDisplayGenPage();
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
		if(!(o instanceof DesignDisplayPage))
			return false;
		DesignDisplayPage that = (DesignDisplayPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("DesignDisplayPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
