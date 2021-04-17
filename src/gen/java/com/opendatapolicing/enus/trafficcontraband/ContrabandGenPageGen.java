package com.opendatapolicing.enus.trafficcontraband;

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
import com.opendatapolicing.enus.writer.AllWriter;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.opendatapolicing.enus.request.api.ApiRequest;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import com.opendatapolicing.enus.page.PageLayout;
import com.opendatapolicing.enus.trafficcontraband.TrafficContraband;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.opendatapolicing.enus.cluster.Cluster;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.ContrabandGenPage&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class ContrabandGenPageGen<DEV> extends PageLayout {
	protected static final Logger LOG = LoggerFactory.getLogger(ContrabandGenPage.class);

	///////////////////////////
	// listTrafficContraband //
	///////////////////////////

	/**	 The entity listTrafficContraband
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SearchList<TrafficContraband> listTrafficContraband;
	@JsonIgnore
	public Wrap<SearchList<TrafficContraband>> listTrafficContrabandWrap = new Wrap<SearchList<TrafficContraband>>().p(this).c(SearchList.class).var("listTrafficContraband").o(listTrafficContraband);

	/**	<br/> The entity listTrafficContraband
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.ContrabandGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:listTrafficContraband">Find the entity listTrafficContraband in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _listTrafficContraband(Wrap<SearchList<TrafficContraband>> c);

	public SearchList<TrafficContraband> getListTrafficContraband() {
		return listTrafficContraband;
	}

	public void setListTrafficContraband(SearchList<TrafficContraband> listTrafficContraband) {
		this.listTrafficContraband = listTrafficContraband;
		this.listTrafficContrabandWrap.alreadyInitialized = true;
	}
	public static SearchList<TrafficContraband> staticSetListTrafficContraband(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected ContrabandGenPage listTrafficContrabandInit() {
		if(!listTrafficContrabandWrap.alreadyInitialized) {
			_listTrafficContraband(listTrafficContrabandWrap);
			if(listTrafficContraband == null)
				setListTrafficContraband(listTrafficContrabandWrap.o);
		}
		if(listTrafficContraband != null)
			listTrafficContraband.initDeepForClass(siteRequest_);
		listTrafficContrabandWrap.alreadyInitialized(true);
		return (ContrabandGenPage)this;
	}

	////////////////////////
	// trafficContraband_ //
	////////////////////////

	/**	 The entity trafficContraband_
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected TrafficContraband trafficContraband_;
	@JsonIgnore
	public Wrap<TrafficContraband> trafficContraband_Wrap = new Wrap<TrafficContraband>().p(this).c(TrafficContraband.class).var("trafficContraband_").o(trafficContraband_);

	/**	<br/> The entity trafficContraband_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.ContrabandGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:trafficContraband_">Find the entity trafficContraband_ in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _trafficContraband_(Wrap<TrafficContraband> c);

	public TrafficContraband getTrafficContraband_() {
		return trafficContraband_;
	}

	public void setTrafficContraband_(TrafficContraband trafficContraband_) {
		this.trafficContraband_ = trafficContraband_;
		this.trafficContraband_Wrap.alreadyInitialized = true;
	}
	public static TrafficContraband staticSetTrafficContraband_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected ContrabandGenPage trafficContraband_Init() {
		if(!trafficContraband_Wrap.alreadyInitialized) {
			_trafficContraband_(trafficContraband_Wrap);
			if(trafficContraband_ == null)
				setTrafficContraband_(trafficContraband_Wrap.o);
		}
		trafficContraband_Wrap.alreadyInitialized(true);
		return (ContrabandGenPage)this;
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedContrabandGenPage = false;

	public ContrabandGenPage initDeepContrabandGenPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedContrabandGenPage) {
			alreadyInitializedContrabandGenPage = true;
			initDeepContrabandGenPage();
		}
		return (ContrabandGenPage)this;
	}

	public void initDeepContrabandGenPage() {
		initContrabandGenPage();
		super.initDeepPageLayout(siteRequest_);
	}

	public void initContrabandGenPage() {
		listTrafficContrabandInit();
		trafficContraband_Init();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepContrabandGenPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestContrabandGenPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestPageLayout(siteRequest_);
		if(listTrafficContraband != null)
			listTrafficContraband.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestContrabandGenPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainContrabandGenPage(v);
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
	public Object obtainContrabandGenPage(String var) {
		ContrabandGenPage oContrabandGenPage = (ContrabandGenPage)this;
		switch(var) {
			case "listTrafficContraband":
				return oContrabandGenPage.listTrafficContraband;
			case "trafficContraband_":
				return oContrabandGenPage.trafficContraband_;
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
				o = attributeContrabandGenPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeContrabandGenPage(String var, Object val) {
		ContrabandGenPage oContrabandGenPage = (ContrabandGenPage)this;
		switch(var) {
			default:
				return super.attributePageLayout(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetContrabandGenPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSetContrabandGenPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSetPageLayout(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrContrabandGenPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrContrabandGenPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSolrPageLayout(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrContrabandGenPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrContrabandGenPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSolrStrPageLayout(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqContrabandGenPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqContrabandGenPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
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
					o = defineContrabandGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineContrabandGenPage(String var, String val) {
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
					o = defineContrabandGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineContrabandGenPage(String var, Object val) {
		switch(var.toLowerCase()) {
			default:
				return super.definePageLayout(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsContrabandGenPage();
		super.htmlScripts();
	}

	public void htmlScriptsContrabandGenPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptContrabandGenPage();
		super.htmlScript();
	}

	public void htmlScriptContrabandGenPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodyContrabandGenPage();
		super.htmlBody();
	}

	public void htmlBodyContrabandGenPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlContrabandGenPage();
		super.html();
	}

	public void htmlContrabandGenPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaContrabandGenPage();
		super.htmlMeta();
	}

	public void htmlMetaContrabandGenPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesContrabandGenPage();
		super.htmlStyles();
	}

	public void htmlStylesContrabandGenPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleContrabandGenPage();
		super.htmlStyle();
	}

	public void htmlStyleContrabandGenPage() {
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestContrabandGenPage() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof ContrabandGenPage) {
			ContrabandGenPage original = (ContrabandGenPage)o;
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
		if(!(o instanceof ContrabandGenPage))
			return false;
		ContrabandGenPage that = (ContrabandGenPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("ContrabandGenPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
