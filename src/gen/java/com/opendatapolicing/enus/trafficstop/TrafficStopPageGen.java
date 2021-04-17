package com.opendatapolicing.enus.trafficstop;

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
import com.opendatapolicing.enus.trafficstop.TrafficStopGenPage;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStopPage&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class TrafficStopPageGen<DEV> extends TrafficStopGenPage {
	protected static final Logger LOG = LoggerFactory.getLogger(TrafficStopPage.class);

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedTrafficStopPage = false;

	public TrafficStopPage initDeepTrafficStopPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedTrafficStopPage) {
			alreadyInitializedTrafficStopPage = true;
			initDeepTrafficStopPage();
		}
		return (TrafficStopPage)this;
	}

	public void initDeepTrafficStopPage() {
		initTrafficStopPage();
		super.initDeepTrafficStopGenPage(siteRequest_);
	}

	public void initTrafficStopPage() {
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepTrafficStopPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestTrafficStopPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestTrafficStopGenPage(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestTrafficStopPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainTrafficStopPage(v);
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
	public Object obtainTrafficStopPage(String var) {
		TrafficStopPage oTrafficStopPage = (TrafficStopPage)this;
		switch(var) {
			default:
				return super.obtainTrafficStopGenPage(var);
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
				o = attributeTrafficStopPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeTrafficStopPage(String var, Object val) {
		TrafficStopPage oTrafficStopPage = (TrafficStopPage)this;
		switch(var) {
			default:
				return super.attributeTrafficStopGenPage(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetTrafficStopPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSetTrafficStopPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return TrafficStopGenPage.staticSetTrafficStopGenPage(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrTrafficStopPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrTrafficStopPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return TrafficStopGenPage.staticSolrTrafficStopGenPage(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrTrafficStopPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrTrafficStopPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return TrafficStopGenPage.staticSolrStrTrafficStopGenPage(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqTrafficStopPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqTrafficStopPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return TrafficStopGenPage.staticSolrFqTrafficStopGenPage(entityVar,  siteRequest_, o);
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
					o = defineTrafficStopPage(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineTrafficStopPage(String var, String val) {
		switch(var.toLowerCase()) {
			default:
				return super.defineTrafficStopGenPage(var, val);
		}
	}

	@Override public boolean defineForClass(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = defineTrafficStopPage(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineTrafficStopPage(String var, Object val) {
		switch(var.toLowerCase()) {
			default:
				return super.defineTrafficStopGenPage(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsTrafficStopPage();
		super.htmlScripts();
	}

	public void htmlScriptsTrafficStopPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptTrafficStopPage();
		super.htmlScript();
	}

	public void htmlScriptTrafficStopPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodyTrafficStopPage();
		super.htmlBody();
	}

	public void htmlBodyTrafficStopPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlTrafficStopPage();
		super.html();
	}

	public void htmlTrafficStopPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaTrafficStopPage();
		super.htmlMeta();
	}

	public void htmlMetaTrafficStopPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesTrafficStopPage();
		super.htmlStyles();
	}

	public void htmlStylesTrafficStopPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleTrafficStopPage();
		super.htmlStyle();
	}

	public void htmlStyleTrafficStopPage() {
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestTrafficStopPage() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof TrafficStopPage) {
			TrafficStopPage original = (TrafficStopPage)o;
			super.apiRequestTrafficStopGenPage();
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
		if(!(o instanceof TrafficStopPage))
			return false;
		TrafficStopPage that = (TrafficStopPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("TrafficStopPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
