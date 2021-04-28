package com.opendatapolicing.enus.searchbasis;

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
import com.opendatapolicing.enus.searchbasis.SearchBasis;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.opendatapolicing.enus.cluster.Cluster;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasisGenPage&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class SearchBasisGenPageGen<DEV> extends PageLayout {
	protected static final Logger LOG = LoggerFactory.getLogger(SearchBasisGenPage.class);

	/////////////////////
	// listSearchBasis //
	/////////////////////

	/**	 The entity listSearchBasis
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SearchList<SearchBasis> listSearchBasis;
	@JsonIgnore
	public Wrap<SearchList<SearchBasis>> listSearchBasisWrap = new Wrap<SearchList<SearchBasis>>().p(this).c(SearchList.class).var("listSearchBasis").o(listSearchBasis);

	/**	<br/> The entity listSearchBasis
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasisGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:listSearchBasis">Find the entity listSearchBasis in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _listSearchBasis(Wrap<SearchList<SearchBasis>> c);

	public SearchList<SearchBasis> getListSearchBasis() {
		return listSearchBasis;
	}

	public void setListSearchBasis(SearchList<SearchBasis> listSearchBasis) {
		this.listSearchBasis = listSearchBasis;
		this.listSearchBasisWrap.alreadyInitialized = true;
	}
	public static SearchList<SearchBasis> staticSetListSearchBasis(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SearchBasisGenPage listSearchBasisInit() {
		if(!listSearchBasisWrap.alreadyInitialized) {
			_listSearchBasis(listSearchBasisWrap);
			if(listSearchBasis == null)
				setListSearchBasis(listSearchBasisWrap.o);
		}
		if(listSearchBasis != null)
			listSearchBasis.initDeepForClass(siteRequest_);
		listSearchBasisWrap.alreadyInitialized(true);
		return (SearchBasisGenPage)this;
	}

	//////////////////
	// searchBasis_ //
	//////////////////

	/**	 The entity searchBasis_
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SearchBasis searchBasis_;
	@JsonIgnore
	public Wrap<SearchBasis> searchBasis_Wrap = new Wrap<SearchBasis>().p(this).c(SearchBasis.class).var("searchBasis_").o(searchBasis_);

	/**	<br/> The entity searchBasis_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasisGenPage&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchBasis_">Find the entity searchBasis_ in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _searchBasis_(Wrap<SearchBasis> c);

	public SearchBasis getSearchBasis_() {
		return searchBasis_;
	}

	public void setSearchBasis_(SearchBasis searchBasis_) {
		this.searchBasis_ = searchBasis_;
		this.searchBasis_Wrap.alreadyInitialized = true;
	}
	public static SearchBasis staticSetSearchBasis_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SearchBasisGenPage searchBasis_Init() {
		if(!searchBasis_Wrap.alreadyInitialized) {
			_searchBasis_(searchBasis_Wrap);
			if(searchBasis_ == null)
				setSearchBasis_(searchBasis_Wrap.o);
		}
		searchBasis_Wrap.alreadyInitialized(true);
		return (SearchBasisGenPage)this;
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedSearchBasisGenPage = false;

	public SearchBasisGenPage initDeepSearchBasisGenPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedSearchBasisGenPage) {
			alreadyInitializedSearchBasisGenPage = true;
			initDeepSearchBasisGenPage();
		}
		return (SearchBasisGenPage)this;
	}

	public void initDeepSearchBasisGenPage() {
		initSearchBasisGenPage();
		super.initDeepPageLayout(siteRequest_);
	}

	public void initSearchBasisGenPage() {
		listSearchBasisInit();
		searchBasis_Init();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepSearchBasisGenPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestSearchBasisGenPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestPageLayout(siteRequest_);
		if(listSearchBasis != null)
			listSearchBasis.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestSearchBasisGenPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainSearchBasisGenPage(v);
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
	public Object obtainSearchBasisGenPage(String var) {
		SearchBasisGenPage oSearchBasisGenPage = (SearchBasisGenPage)this;
		switch(var) {
			case "listSearchBasis":
				return oSearchBasisGenPage.listSearchBasis;
			case "searchBasis_":
				return oSearchBasisGenPage.searchBasis_;
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
				o = attributeSearchBasisGenPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeSearchBasisGenPage(String var, Object val) {
		SearchBasisGenPage oSearchBasisGenPage = (SearchBasisGenPage)this;
		switch(var) {
			default:
				return super.attributePageLayout(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetSearchBasisGenPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSetSearchBasisGenPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSetPageLayout(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrSearchBasisGenPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrSearchBasisGenPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSolrPageLayout(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrSearchBasisGenPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrSearchBasisGenPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSolrStrPageLayout(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqSearchBasisGenPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqSearchBasisGenPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
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
					o = defineSearchBasisGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSearchBasisGenPage(String var, String val) {
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
					o = defineSearchBasisGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSearchBasisGenPage(String var, Object val) {
		switch(var.toLowerCase()) {
			default:
				return super.definePageLayout(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsSearchBasisGenPage();
		super.htmlScripts();
	}

	public void htmlScriptsSearchBasisGenPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptSearchBasisGenPage();
		super.htmlScript();
	}

	public void htmlScriptSearchBasisGenPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodySearchBasisGenPage();
		super.htmlBody();
	}

	public void htmlBodySearchBasisGenPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlSearchBasisGenPage();
		super.html();
	}

	public void htmlSearchBasisGenPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaSearchBasisGenPage();
		super.htmlMeta();
	}

	public void htmlMetaSearchBasisGenPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesSearchBasisGenPage();
		super.htmlStyles();
	}

	public void htmlStylesSearchBasisGenPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleSearchBasisGenPage();
		super.htmlStyle();
	}

	public void htmlStyleSearchBasisGenPage() {
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestSearchBasisGenPage() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof SearchBasisGenPage) {
			SearchBasisGenPage original = (SearchBasisGenPage)o;
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
		if(!(o instanceof SearchBasisGenPage))
			return false;
		SearchBasisGenPage that = (SearchBasisGenPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("SearchBasisGenPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
