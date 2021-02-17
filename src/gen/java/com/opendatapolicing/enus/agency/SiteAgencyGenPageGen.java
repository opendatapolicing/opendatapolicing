package com.opendatapolicing.enus.agency;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import io.vertx.core.logging.LoggerFactory;
import com.opendatapolicing.enus.search.SearchList;
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
import com.opendatapolicing.enus.page.PageLayout;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.opendatapolicing.enus.cluster.Cluster;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.opendatapolicing.enus.agency.SiteAgency;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgencyGenPage&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class SiteAgencyGenPageGen<DEV> extends PageLayout {
	protected static final Logger LOGGER = LoggerFactory.getLogger(SiteAgencyGenPage.class);

	////////////////////
	// listSiteAgency //
	////////////////////

	/**	 The entity listSiteAgency
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SearchList<SiteAgency> listSiteAgency;
	@JsonIgnore
	public Wrap<SearchList<SiteAgency>> listSiteAgencyWrap = new Wrap<SearchList<SiteAgency>>().p(this).c(SearchList.class).var("listSiteAgency").o(listSiteAgency);

	/**	<br/> The entity listSiteAgency
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgencyGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:listSiteAgency">Find the entity listSiteAgency in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _listSiteAgency(Wrap<SearchList<SiteAgency>> c);

	public SearchList<SiteAgency> getListSiteAgency() {
		return listSiteAgency;
	}

	public void setListSiteAgency(SearchList<SiteAgency> listSiteAgency) {
		this.listSiteAgency = listSiteAgency;
		this.listSiteAgencyWrap.alreadyInitialized = true;
	}
	public static SearchList<SiteAgency> staticSetListSiteAgency(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteAgencyGenPage listSiteAgencyInit() {
		if(!listSiteAgencyWrap.alreadyInitialized) {
			_listSiteAgency(listSiteAgencyWrap);
			if(listSiteAgency == null)
				setListSiteAgency(listSiteAgencyWrap.o);
		}
		if(listSiteAgency != null)
			listSiteAgency.initDeepForClass(siteRequest_);
		listSiteAgencyWrap.alreadyInitialized(true);
		return (SiteAgencyGenPage)this;
	}

	/////////////////
	// siteAgency_ //
	/////////////////

	/**	 The entity siteAgency_
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SiteAgency siteAgency_;
	@JsonIgnore
	public Wrap<SiteAgency> siteAgency_Wrap = new Wrap<SiteAgency>().p(this).c(SiteAgency.class).var("siteAgency_").o(siteAgency_);

	/**	<br/> The entity siteAgency_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgencyGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:siteAgency_">Find the entity siteAgency_ in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _siteAgency_(Wrap<SiteAgency> c);

	public SiteAgency getSiteAgency_() {
		return siteAgency_;
	}

	public void setSiteAgency_(SiteAgency siteAgency_) {
		this.siteAgency_ = siteAgency_;
		this.siteAgency_Wrap.alreadyInitialized = true;
	}
	public static SiteAgency staticSetSiteAgency_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteAgencyGenPage siteAgency_Init() {
		if(!siteAgency_Wrap.alreadyInitialized) {
			_siteAgency_(siteAgency_Wrap);
			if(siteAgency_ == null)
				setSiteAgency_(siteAgency_Wrap.o);
		}
		siteAgency_Wrap.alreadyInitialized(true);
		return (SiteAgencyGenPage)this;
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedSiteAgencyGenPage = false;

	public SiteAgencyGenPage initDeepSiteAgencyGenPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedSiteAgencyGenPage) {
			alreadyInitializedSiteAgencyGenPage = true;
			initDeepSiteAgencyGenPage();
		}
		return (SiteAgencyGenPage)this;
	}

	public void initDeepSiteAgencyGenPage() {
		initSiteAgencyGenPage();
		super.initDeepPageLayout(siteRequest_);
	}

	public void initSiteAgencyGenPage() {
		listSiteAgencyInit();
		siteAgency_Init();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepSiteAgencyGenPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestSiteAgencyGenPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestPageLayout(siteRequest_);
		if(listSiteAgency != null)
			listSiteAgency.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestSiteAgencyGenPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainSiteAgencyGenPage(v);
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
	public Object obtainSiteAgencyGenPage(String var) {
		SiteAgencyGenPage oSiteAgencyGenPage = (SiteAgencyGenPage)this;
		switch(var) {
			case "listSiteAgency":
				return oSiteAgencyGenPage.listSiteAgency;
			case "siteAgency_":
				return oSiteAgencyGenPage.siteAgency_;
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
				o = attributeSiteAgencyGenPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeSiteAgencyGenPage(String var, Object val) {
		SiteAgencyGenPage oSiteAgencyGenPage = (SiteAgencyGenPage)this;
		switch(var) {
			default:
				return super.attributePageLayout(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetSiteAgencyGenPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSetSiteAgencyGenPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSetPageLayout(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrSiteAgencyGenPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrSiteAgencyGenPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSolrPageLayout(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrSiteAgencyGenPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrSiteAgencyGenPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSolrStrPageLayout(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqSiteAgencyGenPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqSiteAgencyGenPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
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
					o = defineSiteAgencyGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSiteAgencyGenPage(String var, String val) {
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
					o = defineSiteAgencyGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSiteAgencyGenPage(String var, Object val) {
		switch(var.toLowerCase()) {
			default:
				return super.definePageLayout(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsSiteAgencyGenPage();
		super.htmlScripts();
	}

	public void htmlScriptsSiteAgencyGenPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptSiteAgencyGenPage();
		super.htmlScript();
	}

	public void htmlScriptSiteAgencyGenPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodySiteAgencyGenPage();
		super.htmlBody();
	}

	public void htmlBodySiteAgencyGenPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlSiteAgencyGenPage();
		super.html();
	}

	public void htmlSiteAgencyGenPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaSiteAgencyGenPage();
		super.htmlMeta();
	}

	public void htmlMetaSiteAgencyGenPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesSiteAgencyGenPage();
		super.htmlStyles();
	}

	public void htmlStylesSiteAgencyGenPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleSiteAgencyGenPage();
		super.htmlStyle();
	}

	public void htmlStyleSiteAgencyGenPage() {
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestSiteAgencyGenPage() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof SiteAgencyGenPage) {
			SiteAgencyGenPage original = (SiteAgencyGenPage)o;
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
		if(!(o instanceof SiteAgencyGenPage))
			return false;
		SiteAgencyGenPage that = (SiteAgencyGenPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("SiteAgencyGenPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
