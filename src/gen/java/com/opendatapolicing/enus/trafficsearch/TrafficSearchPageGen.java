package com.opendatapolicing.enus.trafficsearch;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opendatapolicing.enus.config.ConfigKeys;
import java.math.RoundingMode;
import com.opendatapolicing.enus.wrap.Wrap;
import org.slf4j.Logger;
import java.math.MathContext;
import com.opendatapolicing.enus.trafficsearch.TrafficSearchGenPage;
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
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearchPage&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class TrafficSearchPageGen<DEV> extends TrafficSearchGenPage {
	protected static final Logger LOG = LoggerFactory.getLogger(TrafficSearchPage.class);

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedTrafficSearchPage = false;

	public TrafficSearchPage initDeepTrafficSearchPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedTrafficSearchPage) {
			alreadyInitializedTrafficSearchPage = true;
			initDeepTrafficSearchPage();
		}
		return (TrafficSearchPage)this;
	}

	public void initDeepTrafficSearchPage() {
		initTrafficSearchPage();
		super.initDeepTrafficSearchGenPage(siteRequest_);
	}

	public void initTrafficSearchPage() {
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepTrafficSearchPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestTrafficSearchPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestTrafficSearchGenPage(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestTrafficSearchPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainTrafficSearchPage(v);
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
	public Object obtainTrafficSearchPage(String var) {
		TrafficSearchPage oTrafficSearchPage = (TrafficSearchPage)this;
		switch(var) {
			default:
				return super.obtainTrafficSearchGenPage(var);
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
				o = attributeTrafficSearchPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeTrafficSearchPage(String var, Object val) {
		TrafficSearchPage oTrafficSearchPage = (TrafficSearchPage)this;
		switch(var) {
			default:
				return super.attributeTrafficSearchGenPage(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetTrafficSearchPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSetTrafficSearchPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return TrafficSearchGenPage.staticSetTrafficSearchGenPage(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrTrafficSearchPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrTrafficSearchPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return TrafficSearchGenPage.staticSolrTrafficSearchGenPage(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrTrafficSearchPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrTrafficSearchPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return TrafficSearchGenPage.staticSolrStrTrafficSearchGenPage(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqTrafficSearchPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqTrafficSearchPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return TrafficSearchGenPage.staticSolrFqTrafficSearchGenPage(entityVar,  siteRequest_, o);
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
					o = defineTrafficSearchPage(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineTrafficSearchPage(String var, String val) {
		switch(var.toLowerCase()) {
			default:
				return super.defineTrafficSearchGenPage(var, val);
		}
	}

	@Override public boolean defineForClass(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = defineTrafficSearchPage(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineTrafficSearchPage(String var, Object val) {
		switch(var.toLowerCase()) {
			default:
				return super.defineTrafficSearchGenPage(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsTrafficSearchPage();
		super.htmlScripts();
	}

	public void htmlScriptsTrafficSearchPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptTrafficSearchPage();
		super.htmlScript();
	}

	public void htmlScriptTrafficSearchPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodyTrafficSearchPage();
		super.htmlBody();
	}

	public void htmlBodyTrafficSearchPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlTrafficSearchPage();
		super.html();
	}

	public void htmlTrafficSearchPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaTrafficSearchPage();
		super.htmlMeta();
	}

	public void htmlMetaTrafficSearchPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesTrafficSearchPage();
		super.htmlStyles();
	}

	public void htmlStylesTrafficSearchPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleTrafficSearchPage();
		super.htmlStyle();
	}

	public void htmlStyleTrafficSearchPage() {
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestTrafficSearchPage() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof TrafficSearchPage) {
			TrafficSearchPage original = (TrafficSearchPage)o;
			super.apiRequestTrafficSearchGenPage();
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
		if(!(o instanceof TrafficSearchPage))
			return false;
		TrafficSearchPage that = (TrafficSearchPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("TrafficSearchPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
