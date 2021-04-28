package com.opendatapolicing.enus.trafficstop;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import com.opendatapolicing.enus.search.SearchList;
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
import io.vertx.core.Promise;
import com.opendatapolicing.enus.writer.AllWriter;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.vertx.core.Future;
import com.opendatapolicing.enus.request.api.ApiRequest;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import com.opendatapolicing.enus.page.PageLayout;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.opendatapolicing.enus.cluster.Cluster;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.opendatapolicing.enus.trafficstop.TrafficStop;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStopGenPage&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class TrafficStopGenPageGen<DEV> extends PageLayout {
	protected static final Logger LOG = LoggerFactory.getLogger(TrafficStopGenPage.class);

	/////////////////////
	// listTrafficStop //
	/////////////////////

	/**	 The entity listTrafficStop
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SearchList<TrafficStop> listTrafficStop;
	@JsonIgnore
	public Wrap<SearchList<TrafficStop>> listTrafficStopWrap = new Wrap<SearchList<TrafficStop>>().p(this).c(SearchList.class).var("listTrafficStop").o(listTrafficStop);

	/**	<br/> The entity listTrafficStop
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStopGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:listTrafficStop">Find the entity listTrafficStop in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _listTrafficStop(Wrap<SearchList<TrafficStop>> c);

	public SearchList<TrafficStop> getListTrafficStop() {
		return listTrafficStop;
	}

	public void setListTrafficStop(SearchList<TrafficStop> listTrafficStop) {
		this.listTrafficStop = listTrafficStop;
		this.listTrafficStopWrap.alreadyInitialized = true;
	}
	public static SearchList<TrafficStop> staticSetListTrafficStop(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected TrafficStopGenPage listTrafficStopInit() {
		if(!listTrafficStopWrap.alreadyInitialized) {
			_listTrafficStop(listTrafficStopWrap);
			if(listTrafficStop == null)
				setListTrafficStop(listTrafficStopWrap.o);
		}
		if(listTrafficStop != null)
			listTrafficStop.initDeepForClass(siteRequest_);
		listTrafficStopWrap.alreadyInitialized(true);
		return (TrafficStopGenPage)this;
	}

	//////////////////
	// trafficStop_ //
	//////////////////

	/**	 The entity trafficStop_
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected TrafficStop trafficStop_;
	@JsonIgnore
	public Wrap<TrafficStop> trafficStop_Wrap = new Wrap<TrafficStop>().p(this).c(TrafficStop.class).var("trafficStop_").o(trafficStop_);

	/**	<br/> The entity trafficStop_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStopGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:trafficStop_">Find the entity trafficStop_ in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _trafficStop_(Wrap<TrafficStop> c);

	public TrafficStop getTrafficStop_() {
		return trafficStop_;
	}

	public void setTrafficStop_(TrafficStop trafficStop_) {
		this.trafficStop_ = trafficStop_;
		this.trafficStop_Wrap.alreadyInitialized = true;
	}
	public static TrafficStop staticSetTrafficStop_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected TrafficStopGenPage trafficStop_Init() {
		if(!trafficStop_Wrap.alreadyInitialized) {
			_trafficStop_(trafficStop_Wrap);
			if(trafficStop_ == null)
				setTrafficStop_(trafficStop_Wrap.o);
		}
		trafficStop_Wrap.alreadyInitialized(true);
		return (TrafficStopGenPage)this;
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedTrafficStopGenPage = false;

	public TrafficStopGenPage initDeepTrafficStopGenPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedTrafficStopGenPage) {
			alreadyInitializedTrafficStopGenPage = true;
			initDeepTrafficStopGenPage();
		}
		return (TrafficStopGenPage)this;
	}

	public void initDeepTrafficStopGenPage() {
		initTrafficStopGenPage();
		super.initDeepPageLayout(siteRequest_);
	}

	public void initTrafficStopGenPage() {
		listTrafficStopInit();
		trafficStop_Init();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepTrafficStopGenPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestTrafficStopGenPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestPageLayout(siteRequest_);
		if(listTrafficStop != null)
			listTrafficStop.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestTrafficStopGenPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainTrafficStopGenPage(v);
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
	public Object obtainTrafficStopGenPage(String var) {
		TrafficStopGenPage oTrafficStopGenPage = (TrafficStopGenPage)this;
		switch(var) {
			case "listTrafficStop":
				return oTrafficStopGenPage.listTrafficStop;
			case "trafficStop_":
				return oTrafficStopGenPage.trafficStop_;
			default:
				return super.obtainPageLayout(var);
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
				o = attributeTrafficStopGenPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeTrafficStopGenPage(String var, Object val) {
		TrafficStopGenPage oTrafficStopGenPage = (TrafficStopGenPage)this;
		switch(var) {
			default:
				return super.attributePageLayout(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetTrafficStopGenPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSetTrafficStopGenPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSetPageLayout(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrTrafficStopGenPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrTrafficStopGenPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSolrPageLayout(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrTrafficStopGenPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrTrafficStopGenPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSolrStrPageLayout(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqTrafficStopGenPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqTrafficStopGenPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSolrFqPageLayout(entityVar,  siteRequest_, o);
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
					o = defineTrafficStopGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineTrafficStopGenPage(String var, String val) {
		switch(var.toLowerCase()) {
			default:
				return super.definePageLayout(var, val);
		}
	}

	@Override public boolean defineForClass(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = defineTrafficStopGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineTrafficStopGenPage(String var, Object val) {
		switch(var.toLowerCase()) {
			default:
				return super.definePageLayout(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsTrafficStopGenPage();
		super.htmlScripts();
	}

	public void htmlScriptsTrafficStopGenPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptTrafficStopGenPage();
		super.htmlScript();
	}

	public void htmlScriptTrafficStopGenPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodyTrafficStopGenPage();
		super.htmlBody();
	}

	public void htmlBodyTrafficStopGenPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlTrafficStopGenPage();
		super.html();
	}

	public void htmlTrafficStopGenPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaTrafficStopGenPage();
		super.htmlMeta();
	}

	public void htmlMetaTrafficStopGenPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesTrafficStopGenPage();
		super.htmlStyles();
	}

	public void htmlStylesTrafficStopGenPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleTrafficStopGenPage();
		super.htmlStyle();
	}

	public void htmlStyleTrafficStopGenPage() {
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestTrafficStopGenPage() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof TrafficStopGenPage) {
			TrafficStopGenPage original = (TrafficStopGenPage)o;
			super.apiRequestPageLayout();
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
		if(!(o instanceof TrafficStopGenPage))
			return false;
		TrafficStopGenPage that = (TrafficStopGenPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("TrafficStopGenPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
