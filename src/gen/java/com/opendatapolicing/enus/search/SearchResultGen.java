package com.opendatapolicing.enus.search;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import io.vertx.core.logging.LoggerFactory;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import java.lang.Long;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.String;
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
import org.apache.solr.common.SolrDocument;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.lang.Object;
import com.opendatapolicing.enus.cluster.Cluster;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.search.SearchResult&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class SearchResultGen<DEV> extends Object {
	protected static final Logger LOGGER = LoggerFactory.getLogger(SearchResult.class);

	//////////////////
	// siteRequest_ //
	//////////////////

	/**	 The entity siteRequest_
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SiteRequestEnUS siteRequest_;
	@JsonIgnore
	public Wrap<SiteRequestEnUS> siteRequest_Wrap = new Wrap<SiteRequestEnUS>().p(this).c(SiteRequestEnUS.class).var("siteRequest_").o(siteRequest_);

	/**	<br/> The entity siteRequest_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.search.SearchResult&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:siteRequest_">Find the entity siteRequest_ in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _siteRequest_(Wrap<SiteRequestEnUS> c);

	public SiteRequestEnUS getSiteRequest_() {
		return siteRequest_;
	}

	public void setSiteRequest_(SiteRequestEnUS siteRequest_) {
		this.siteRequest_ = siteRequest_;
		this.siteRequest_Wrap.alreadyInitialized = true;
	}
	public static SiteRequestEnUS staticSetSiteRequest_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SearchResult siteRequest_Init() {
		if(!siteRequest_Wrap.alreadyInitialized) {
			_siteRequest_(siteRequest_Wrap);
			if(siteRequest_ == null)
				setSiteRequest_(siteRequest_Wrap.o);
		}
		siteRequest_Wrap.alreadyInitialized(true);
		return (SearchResult)this;
	}

	//////////////////
	// solrDocument //
	//////////////////

	/**	 The entity solrDocument
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SolrDocument solrDocument;
	@JsonIgnore
	public Wrap<SolrDocument> solrDocumentWrap = new Wrap<SolrDocument>().p(this).c(SolrDocument.class).var("solrDocument").o(solrDocument);

	/**	<br/> The entity solrDocument
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.search.SearchResult&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:solrDocument">Find the entity solrDocument in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _solrDocument(Wrap<SolrDocument> c);

	public SolrDocument getSolrDocument() {
		return solrDocument;
	}

	public void setSolrDocument(SolrDocument solrDocument) {
		this.solrDocument = solrDocument;
		this.solrDocumentWrap.alreadyInitialized = true;
	}
	public static SolrDocument staticSetSolrDocument(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SearchResult solrDocumentInit() {
		if(!solrDocumentWrap.alreadyInitialized) {
			_solrDocument(solrDocumentWrap);
			if(solrDocument == null)
				setSolrDocument(solrDocumentWrap.o);
		}
		solrDocumentWrap.alreadyInitialized(true);
		return (SearchResult)this;
	}

	/////////////////
	// resultIndex //
	/////////////////

	/**	 The entity resultIndex
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long resultIndex;
	@JsonIgnore
	public Wrap<Long> resultIndexWrap = new Wrap<Long>().p(this).c(Long.class).var("resultIndex").o(resultIndex);

	/**	<br/> The entity resultIndex
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.search.SearchResult&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:resultIndex">Find the entity resultIndex in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _resultIndex(Wrap<Long> c);

	public Long getResultIndex() {
		return resultIndex;
	}

	public void setResultIndex(Long resultIndex) {
		this.resultIndex = resultIndex;
		this.resultIndexWrap.alreadyInitialized = true;
	}
	public void setResultIndex(String o) {
		this.resultIndex = SearchResult.staticSetResultIndex(siteRequest_, o);
		this.resultIndexWrap.alreadyInitialized = true;
	}
	public static Long staticSetResultIndex(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected SearchResult resultIndexInit() {
		if(!resultIndexWrap.alreadyInitialized) {
			_resultIndex(resultIndexWrap);
			if(resultIndex == null)
				setResultIndex(resultIndexWrap.o);
		}
		resultIndexWrap.alreadyInitialized(true);
		return (SearchResult)this;
	}

	public static Long staticSolrResultIndex(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrResultIndex(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqResultIndex(SiteRequestEnUS siteRequest_, String o) {
		return SearchResult.staticSolrStrResultIndex(siteRequest_, SearchResult.staticSolrResultIndex(siteRequest_, SearchResult.staticSetResultIndex(siteRequest_, o)));
	}

	public Long solrResultIndex() {
		return SearchResult.staticSolrResultIndex(siteRequest_, resultIndex);
	}

	public String strResultIndex() {
		return resultIndex == null ? "" : resultIndex.toString();
	}

	public Long sqlResultIndex() {
		return resultIndex;
	}

	public String jsonResultIndex() {
		return resultIndex == null ? "" : resultIndex.toString();
	}

	public String htmTooltipResultIndex() {
		return null;
	}

	public String htmResultIndex() {
		return resultIndex == null ? "" : StringEscapeUtils.escapeHtml4(strResultIndex());
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedSearchResult = false;

	public SearchResult initDeepSearchResult(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedSearchResult) {
			alreadyInitializedSearchResult = true;
			initDeepSearchResult();
		}
		return (SearchResult)this;
	}

	public void initDeepSearchResult() {
		initSearchResult();
	}

	public void initSearchResult() {
		siteRequest_Init();
		solrDocumentInit();
		resultIndexInit();
	}

	public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepSearchResult(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestSearchResult(SiteRequestEnUS siteRequest_) {
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestSearchResult(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainSearchResult(v);
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
	public Object obtainSearchResult(String var) {
		SearchResult oSearchResult = (SearchResult)this;
		switch(var) {
			case "siteRequest_":
				return oSearchResult.siteRequest_;
			case "solrDocument":
				return oSearchResult.solrDocument;
			case "resultIndex":
				return oSearchResult.resultIndex;
			default:
				return null;
		}
	}

	///////////////
	// attribute //
	///////////////

	public boolean attributeForClass(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = attributeSearchResult(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeSearchResult(String var, Object val) {
		SearchResult oSearchResult = (SearchResult)this;
		switch(var) {
			default:
				return null;
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetSearchResult(entityVar,  siteRequest_, o);
	}
	public static Object staticSetSearchResult(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "resultIndex":
			return SearchResult.staticSetResultIndex(siteRequest_, o);
			default:
				return null;
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrSearchResult(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrSearchResult(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "resultIndex":
			return SearchResult.staticSolrResultIndex(siteRequest_, (Long)o);
			default:
				return null;
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrSearchResult(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrSearchResult(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "resultIndex":
			return SearchResult.staticSolrStrResultIndex(siteRequest_, (Long)o);
			default:
				return null;
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqSearchResult(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqSearchResult(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "resultIndex":
			return SearchResult.staticSolrFqResultIndex(siteRequest_, o);
			default:
				return null;
		}
	}

	/////////////
	// define //
	/////////////

	public boolean defineForClass(String var, String val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = defineSearchResult(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSearchResult(String var, String val) {
		switch(var.toLowerCase()) {
			default:
				return null;
		}
	}

	public boolean defineForClass(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = defineSearchResult(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSearchResult(String var, Object val) {
		switch(var.toLowerCase()) {
			default:
				return null;
		}
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestSearchResult() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof SearchResult) {
			SearchResult original = (SearchResult)o;
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash();
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof SearchResult))
			return false;
		SearchResult that = (SearchResult)o;
		return true;
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("SearchResult { ");
		sb.append(" }");
		return sb.toString();
	}
}
