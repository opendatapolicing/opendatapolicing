package com.opendatapolicing.enus.trafficsearch;

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
import com.opendatapolicing.enus.trafficsearch.TrafficSearch;
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
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearchGenPage&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class TrafficSearchGenPageGen<DEV> extends PageLayout {
	protected static final Logger LOG = LoggerFactory.getLogger(TrafficSearchGenPage.class);

	///////////////////////
	// listTrafficSearch //
	///////////////////////

	/**	 The entity listTrafficSearch
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SearchList<TrafficSearch> listTrafficSearch;
	@JsonIgnore
	public Wrap<SearchList<TrafficSearch>> listTrafficSearchWrap = new Wrap<SearchList<TrafficSearch>>().p(this).c(SearchList.class).var("listTrafficSearch").o(listTrafficSearch);

	/**	<br/> The entity listTrafficSearch
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearchGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:listTrafficSearch">Find the entity listTrafficSearch in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _listTrafficSearch(Wrap<SearchList<TrafficSearch>> c);

	public SearchList<TrafficSearch> getListTrafficSearch() {
		return listTrafficSearch;
	}

	public void setListTrafficSearch(SearchList<TrafficSearch> listTrafficSearch) {
		this.listTrafficSearch = listTrafficSearch;
		this.listTrafficSearchWrap.alreadyInitialized = true;
	}
	public static SearchList<TrafficSearch> staticSetListTrafficSearch(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected TrafficSearchGenPage listTrafficSearchInit() {
		if(!listTrafficSearchWrap.alreadyInitialized) {
			_listTrafficSearch(listTrafficSearchWrap);
			if(listTrafficSearch == null)
				setListTrafficSearch(listTrafficSearchWrap.o);
		}
		if(listTrafficSearch != null)
			listTrafficSearch.initDeepForClass(siteRequest_);
		listTrafficSearchWrap.alreadyInitialized(true);
		return (TrafficSearchGenPage)this;
	}

	////////////////////
	// trafficSearch_ //
	////////////////////

	/**	 The entity trafficSearch_
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected TrafficSearch trafficSearch_;
	@JsonIgnore
	public Wrap<TrafficSearch> trafficSearch_Wrap = new Wrap<TrafficSearch>().p(this).c(TrafficSearch.class).var("trafficSearch_").o(trafficSearch_);

	/**	<br/> The entity trafficSearch_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearchGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:trafficSearch_">Find the entity trafficSearch_ in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _trafficSearch_(Wrap<TrafficSearch> c);

	public TrafficSearch getTrafficSearch_() {
		return trafficSearch_;
	}

	public void setTrafficSearch_(TrafficSearch trafficSearch_) {
		this.trafficSearch_ = trafficSearch_;
		this.trafficSearch_Wrap.alreadyInitialized = true;
	}
	public static TrafficSearch staticSetTrafficSearch_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected TrafficSearchGenPage trafficSearch_Init() {
		if(!trafficSearch_Wrap.alreadyInitialized) {
			_trafficSearch_(trafficSearch_Wrap);
			if(trafficSearch_ == null)
				setTrafficSearch_(trafficSearch_Wrap.o);
		}
		trafficSearch_Wrap.alreadyInitialized(true);
		return (TrafficSearchGenPage)this;
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedTrafficSearchGenPage = false;

	public TrafficSearchGenPage initDeepTrafficSearchGenPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedTrafficSearchGenPage) {
			alreadyInitializedTrafficSearchGenPage = true;
			initDeepTrafficSearchGenPage();
		}
		return (TrafficSearchGenPage)this;
	}

	public void initDeepTrafficSearchGenPage() {
		initTrafficSearchGenPage();
		super.initDeepPageLayout(siteRequest_);
	}

	public void initTrafficSearchGenPage() {
		listTrafficSearchInit();
		trafficSearch_Init();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepTrafficSearchGenPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestTrafficSearchGenPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestPageLayout(siteRequest_);
		if(listTrafficSearch != null)
			listTrafficSearch.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestTrafficSearchGenPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainTrafficSearchGenPage(v);
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
	public Object obtainTrafficSearchGenPage(String var) {
		TrafficSearchGenPage oTrafficSearchGenPage = (TrafficSearchGenPage)this;
		switch(var) {
			case "listTrafficSearch":
				return oTrafficSearchGenPage.listTrafficSearch;
			case "trafficSearch_":
				return oTrafficSearchGenPage.trafficSearch_;
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
				o = attributeTrafficSearchGenPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeTrafficSearchGenPage(String var, Object val) {
		TrafficSearchGenPage oTrafficSearchGenPage = (TrafficSearchGenPage)this;
		switch(var) {
			default:
				return super.attributePageLayout(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetTrafficSearchGenPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSetTrafficSearchGenPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSetPageLayout(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrTrafficSearchGenPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrTrafficSearchGenPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSolrPageLayout(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrTrafficSearchGenPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrTrafficSearchGenPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSolrStrPageLayout(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqTrafficSearchGenPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqTrafficSearchGenPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
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
					o = defineTrafficSearchGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineTrafficSearchGenPage(String var, String val) {
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
					o = defineTrafficSearchGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineTrafficSearchGenPage(String var, Object val) {
		switch(var.toLowerCase()) {
			default:
				return super.definePageLayout(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsTrafficSearchGenPage();
		super.htmlScripts();
	}

	public void htmlScriptsTrafficSearchGenPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptTrafficSearchGenPage();
		super.htmlScript();
	}

	public void htmlScriptTrafficSearchGenPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodyTrafficSearchGenPage();
		super.htmlBody();
	}

	public void htmlBodyTrafficSearchGenPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlTrafficSearchGenPage();
		super.html();
	}

	public void htmlTrafficSearchGenPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaTrafficSearchGenPage();
		super.htmlMeta();
	}

	public void htmlMetaTrafficSearchGenPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesTrafficSearchGenPage();
		super.htmlStyles();
	}

	public void htmlStylesTrafficSearchGenPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleTrafficSearchGenPage();
		super.htmlStyle();
	}

	public void htmlStyleTrafficSearchGenPage() {
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestTrafficSearchGenPage() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof TrafficSearchGenPage) {
			TrafficSearchGenPage original = (TrafficSearchGenPage)o;
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
		if(!(o instanceof TrafficSearchGenPage))
			return false;
		TrafficSearchGenPage that = (TrafficSearchGenPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("TrafficSearchGenPage { ");
		sb.append(" }");
		return sb.toString();
	}

	public static final String VAR_listTrafficSearch = "listTrafficSearch";
	public static final String VAR_trafficSearch_ = "trafficSearch_";
}
