package com.opendatapolicing.enus.trafficperson;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import com.opendatapolicing.enus.search.SearchList;
import java.util.ArrayList;
import com.opendatapolicing.enus.trafficperson.TrafficPerson;
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
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPersonGenPage&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class TrafficPersonGenPageGen<DEV> extends PageLayout {
	protected static final Logger LOG = LoggerFactory.getLogger(TrafficPersonGenPage.class);

	///////////////////////
	// listTrafficPerson //
	///////////////////////

	/**	 The entity listTrafficPerson
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SearchList<TrafficPerson> listTrafficPerson;
	@JsonIgnore
	public Wrap<SearchList<TrafficPerson>> listTrafficPersonWrap = new Wrap<SearchList<TrafficPerson>>().p(this).c(SearchList.class).var("listTrafficPerson").o(listTrafficPerson);

	/**	<br/> The entity listTrafficPerson
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPersonGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:listTrafficPerson">Find the entity listTrafficPerson in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _listTrafficPerson(Wrap<SearchList<TrafficPerson>> c);

	public SearchList<TrafficPerson> getListTrafficPerson() {
		return listTrafficPerson;
	}

	public void setListTrafficPerson(SearchList<TrafficPerson> listTrafficPerson) {
		this.listTrafficPerson = listTrafficPerson;
		this.listTrafficPersonWrap.alreadyInitialized = true;
	}
	public static SearchList<TrafficPerson> staticSetListTrafficPerson(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected TrafficPersonGenPage listTrafficPersonInit() {
		if(!listTrafficPersonWrap.alreadyInitialized) {
			_listTrafficPerson(listTrafficPersonWrap);
			if(listTrafficPerson == null)
				setListTrafficPerson(listTrafficPersonWrap.o);
		}
		if(listTrafficPerson != null)
			listTrafficPerson.initDeepForClass(siteRequest_);
		listTrafficPersonWrap.alreadyInitialized(true);
		return (TrafficPersonGenPage)this;
	}

	////////////////////
	// trafficPerson_ //
	////////////////////

	/**	 The entity trafficPerson_
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected TrafficPerson trafficPerson_;
	@JsonIgnore
	public Wrap<TrafficPerson> trafficPerson_Wrap = new Wrap<TrafficPerson>().p(this).c(TrafficPerson.class).var("trafficPerson_").o(trafficPerson_);

	/**	<br/> The entity trafficPerson_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPersonGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:trafficPerson_">Find the entity trafficPerson_ in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _trafficPerson_(Wrap<TrafficPerson> c);

	public TrafficPerson getTrafficPerson_() {
		return trafficPerson_;
	}

	public void setTrafficPerson_(TrafficPerson trafficPerson_) {
		this.trafficPerson_ = trafficPerson_;
		this.trafficPerson_Wrap.alreadyInitialized = true;
	}
	public static TrafficPerson staticSetTrafficPerson_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected TrafficPersonGenPage trafficPerson_Init() {
		if(!trafficPerson_Wrap.alreadyInitialized) {
			_trafficPerson_(trafficPerson_Wrap);
			if(trafficPerson_ == null)
				setTrafficPerson_(trafficPerson_Wrap.o);
		}
		trafficPerson_Wrap.alreadyInitialized(true);
		return (TrafficPersonGenPage)this;
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedTrafficPersonGenPage = false;

	public TrafficPersonGenPage initDeepTrafficPersonGenPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedTrafficPersonGenPage) {
			alreadyInitializedTrafficPersonGenPage = true;
			initDeepTrafficPersonGenPage();
		}
		return (TrafficPersonGenPage)this;
	}

	public void initDeepTrafficPersonGenPage() {
		initTrafficPersonGenPage();
		super.initDeepPageLayout(siteRequest_);
	}

	public void initTrafficPersonGenPage() {
		listTrafficPersonInit();
		trafficPerson_Init();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepTrafficPersonGenPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestTrafficPersonGenPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestPageLayout(siteRequest_);
		if(listTrafficPerson != null)
			listTrafficPerson.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestTrafficPersonGenPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainTrafficPersonGenPage(v);
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
	public Object obtainTrafficPersonGenPage(String var) {
		TrafficPersonGenPage oTrafficPersonGenPage = (TrafficPersonGenPage)this;
		switch(var) {
			case "listTrafficPerson":
				return oTrafficPersonGenPage.listTrafficPerson;
			case "trafficPerson_":
				return oTrafficPersonGenPage.trafficPerson_;
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
				o = attributeTrafficPersonGenPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeTrafficPersonGenPage(String var, Object val) {
		TrafficPersonGenPage oTrafficPersonGenPage = (TrafficPersonGenPage)this;
		switch(var) {
			default:
				return super.attributePageLayout(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetTrafficPersonGenPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSetTrafficPersonGenPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSetPageLayout(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrTrafficPersonGenPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrTrafficPersonGenPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSolrPageLayout(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrTrafficPersonGenPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrTrafficPersonGenPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSolrStrPageLayout(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqTrafficPersonGenPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqTrafficPersonGenPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
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
					o = defineTrafficPersonGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineTrafficPersonGenPage(String var, String val) {
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
					o = defineTrafficPersonGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineTrafficPersonGenPage(String var, Object val) {
		switch(var.toLowerCase()) {
			default:
				return super.definePageLayout(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsTrafficPersonGenPage();
		super.htmlScripts();
	}

	public void htmlScriptsTrafficPersonGenPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptTrafficPersonGenPage();
		super.htmlScript();
	}

	public void htmlScriptTrafficPersonGenPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodyTrafficPersonGenPage();
		super.htmlBody();
	}

	public void htmlBodyTrafficPersonGenPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlTrafficPersonGenPage();
		super.html();
	}

	public void htmlTrafficPersonGenPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaTrafficPersonGenPage();
		super.htmlMeta();
	}

	public void htmlMetaTrafficPersonGenPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesTrafficPersonGenPage();
		super.htmlStyles();
	}

	public void htmlStylesTrafficPersonGenPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleTrafficPersonGenPage();
		super.htmlStyle();
	}

	public void htmlStyleTrafficPersonGenPage() {
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestTrafficPersonGenPage() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof TrafficPersonGenPage) {
			TrafficPersonGenPage original = (TrafficPersonGenPage)o;
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
		if(!(o instanceof TrafficPersonGenPage))
			return false;
		TrafficPersonGenPage that = (TrafficPersonGenPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("TrafficPersonGenPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
