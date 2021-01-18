package com.opendatapolicing.enus.search;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.solr.common.SolrDocumentList;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import io.vertx.core.logging.LoggerFactory;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.Boolean;
import java.lang.String;
import io.vertx.core.logging.Logger;
import java.math.RoundingMode;
import com.opendatapolicing.enus.wrap.Wrap;
import java.math.MathContext;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.opendatapolicing.enus.request.api.ApiRequest;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import java.util.List;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.lang.Class;
import com.opendatapolicing.enus.cluster.Cluster;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.search.SearchList&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class SearchListGen<DEV> {
	protected static final Logger LOGGER = LoggerFactory.getLogger(SearchList.class);

	///////
	// c //
	///////

	/**	 The entity c
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Class<DEV> c;
	@JsonIgnore
	public Wrap<Class<DEV>> cWrap = new Wrap<Class<DEV>>().p(this).c(Class.class).var("c").o(c);

	/**	<br/> The entity c
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.search.SearchList&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:c">Find the entity c in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _c(Wrap<Class<DEV>> c);

	public Class<DEV> getC() {
		return c;
	}

	public void setC(Class<DEV> c) {
		this.c = c;
		this.cWrap.alreadyInitialized = true;
	}
	protected SearchList cInit() {
		if(!cWrap.alreadyInitialized) {
			_c(cWrap);
			if(c == null)
				setC(cWrap.o);
		}
		cWrap.alreadyInitialized(true);
		return (SearchList)this;
	}

	//////////////////
	// siteRequest_ //
	//////////////////

	/**	 The entity siteRequest_
	 *	 is defined as null before being initialized. 
	 */
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected SiteRequestEnUS siteRequest_;
	@JsonIgnore
	public Wrap<SiteRequestEnUS> siteRequest_Wrap = new Wrap<SiteRequestEnUS>().p(this).c(SiteRequestEnUS.class).var("siteRequest_").o(siteRequest_);

	/**	<br/> The entity siteRequest_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.search.SearchList&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:siteRequest_">Find the entity siteRequest_ in Solr</a>
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
	protected SearchList siteRequest_Init() {
		if(!siteRequest_Wrap.alreadyInitialized) {
			_siteRequest_(siteRequest_Wrap);
			if(siteRequest_ == null)
				setSiteRequest_(siteRequest_Wrap.o);
		}
		siteRequest_Wrap.alreadyInitialized(true);
		return (SearchList)this;
	}

	///////////
	// store //
	///////////

	/**	 The entity store
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean store;
	@JsonIgnore
	public Wrap<Boolean> storeWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("store").o(store);

	/**	<br/> The entity store
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.search.SearchList&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:store">Find the entity store in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _store(Wrap<Boolean> c);

	public Boolean getStore() {
		return store;
	}

	public void setStore(Boolean store) {
		this.store = store;
		this.storeWrap.alreadyInitialized = true;
	}
	public void setStore(String o) {
		this.store = SearchList.staticSetStore(siteRequest_, o);
		this.storeWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStore(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SearchList storeInit() {
		if(!storeWrap.alreadyInitialized) {
			_store(storeWrap);
			if(store == null)
				setStore(storeWrap.o);
		}
		storeWrap.alreadyInitialized(true);
		return (SearchList)this;
	}

	public static Boolean staticSolrStore(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStore(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStore(SiteRequestEnUS siteRequest_, String o) {
		return SearchList.staticSolrStrStore(siteRequest_, SearchList.staticSolrStore(siteRequest_, SearchList.staticSetStore(siteRequest_, o)));
	}

	public Boolean solrStore() {
		return SearchList.staticSolrStore(siteRequest_, store);
	}

	public String strStore() {
		return store == null ? "" : store.toString();
	}

	public String jsonStore() {
		return store == null ? "" : store.toString();
	}

	public String nomAffichageStore() {
		return null;
	}

	public String htmTooltipStore() {
		return null;
	}

	public String htmStore() {
		return store == null ? "" : StringEscapeUtils.escapeHtml4(strStore());
	}

	//////////////
	// populate //
	//////////////

	/**	 The entity populate
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean populate;
	@JsonIgnore
	public Wrap<Boolean> populateWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("populate").o(populate);

	/**	<br/> The entity populate
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.search.SearchList&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:populate">Find the entity populate in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _populate(Wrap<Boolean> c);

	public Boolean getPopulate() {
		return populate;
	}

	public void setPopulate(Boolean populate) {
		this.populate = populate;
		this.populateWrap.alreadyInitialized = true;
	}
	public void setPopulate(String o) {
		this.populate = SearchList.staticSetPopulate(siteRequest_, o);
		this.populateWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetPopulate(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SearchList populateInit() {
		if(!populateWrap.alreadyInitialized) {
			_populate(populateWrap);
			if(populate == null)
				setPopulate(populateWrap.o);
		}
		populateWrap.alreadyInitialized(true);
		return (SearchList)this;
	}

	public static Boolean staticSolrPopulate(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrPopulate(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPopulate(SiteRequestEnUS siteRequest_, String o) {
		return SearchList.staticSolrStrPopulate(siteRequest_, SearchList.staticSolrPopulate(siteRequest_, SearchList.staticSetPopulate(siteRequest_, o)));
	}

	public Boolean solrPopulate() {
		return SearchList.staticSolrPopulate(siteRequest_, populate);
	}

	public String strPopulate() {
		return populate == null ? "" : populate.toString();
	}

	public String jsonPopulate() {
		return populate == null ? "" : populate.toString();
	}

	public String nomAffichagePopulate() {
		return null;
	}

	public String htmTooltipPopulate() {
		return null;
	}

	public String htmPopulate() {
		return populate == null ? "" : StringEscapeUtils.escapeHtml4(strPopulate());
	}

	////////////
	// fields //
	////////////

	/**	 The entity fields
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<String>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected List<String> fields = new ArrayList<String>();
	@JsonIgnore
	public Wrap<List<String>> fieldsWrap = new Wrap<List<String>>().p(this).c(List.class).var("fields").o(fields);

	/**	<br/> The entity fields
	 *  It is constructed before being initialized with the constructor by default List<String>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.search.SearchList&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:fields">Find the entity fields in Solr</a>
	 * <br/>
	 * @param fields is the entity already constructed. 
	 **/
	protected abstract void _fields(List<String> c);

	public List<String> getFields() {
		return fields;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
		this.fieldsWrap.alreadyInitialized = true;
	}
	public static String staticSetFields(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	public SearchList addFields(String...objets) {
		for(String o : objets) {
			addFields(o);
		}
		return (SearchList)this;
	}
	public SearchList addFields(String o) {
		if(o != null && !fields.contains(o))
			this.fields.add(o);
		return (SearchList)this;
	}
	public void setFields(JsonArray objets) {
		fields.clear();
		for(int i = 0; i < objets.size(); i++) {
			String o = objets.getString(i);
			addFields(o);
		}
	}
	protected SearchList fieldsInit() {
		if(!fieldsWrap.alreadyInitialized) {
			_fields(fields);
		}
		fieldsWrap.alreadyInitialized(true);
		return (SearchList)this;
	}

	public static String staticSolrFields(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrFields(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqFields(SiteRequestEnUS siteRequest_, String o) {
		return SearchList.staticSolrStrFields(siteRequest_, SearchList.staticSolrFields(siteRequest_, SearchList.staticSetFields(siteRequest_, o)));
	}

	public List<String> solrFields() {
		List<String> l = new ArrayList<String>();
		for(String o : fields) {
			l.add(SearchList.staticSolrFields(siteRequest_, o));
		}
		return l;
	}

	public String strFields() {
		return fields == null ? "" : fields.toString();
	}

	public String jsonFields() {
		return fields == null ? "" : fields.toString();
	}

	public String nomAffichageFields() {
		return null;
	}

	public String htmTooltipFields() {
		return null;
	}

	public String htmFields() {
		return fields == null ? "" : StringEscapeUtils.escapeHtml4(strFields());
	}

	///////////////
	// solrQuery //
	///////////////

	/**	 The entity solrQuery
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut SolrQuery(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SolrQuery solrQuery = new SolrQuery();
	@JsonIgnore
	public Wrap<SolrQuery> solrQueryWrap = new Wrap<SolrQuery>().p(this).c(SolrQuery.class).var("solrQuery").o(solrQuery);

	/**	<br/> The entity solrQuery
	 *  It is constructed before being initialized with the constructor by default SolrQuery(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.search.SearchList&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:solrQuery">Find the entity solrQuery in Solr</a>
	 * <br/>
	 * @param solrQuery is the entity already constructed. 
	 **/
	protected abstract void _solrQuery(SolrQuery o);

	public SolrQuery getSolrQuery() {
		return solrQuery;
	}

	public void setSolrQuery(SolrQuery solrQuery) {
		this.solrQuery = solrQuery;
		this.solrQueryWrap.alreadyInitialized = true;
	}
	public static SolrQuery staticSetSolrQuery(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SearchList solrQueryInit() {
		if(!solrQueryWrap.alreadyInitialized) {
			_solrQuery(solrQuery);
		}
		solrQueryWrap.alreadyInitialized(true);
		return (SearchList)this;
	}

	///////////////////
	// queryResponse //
	///////////////////

	/**	 The entity queryResponse
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected QueryResponse queryResponse;
	@JsonIgnore
	public Wrap<QueryResponse> queryResponseWrap = new Wrap<QueryResponse>().p(this).c(QueryResponse.class).var("queryResponse").o(queryResponse);

	/**	<br/> The entity queryResponse
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.search.SearchList&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:queryResponse">Find the entity queryResponse in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _queryResponse(Wrap<QueryResponse> c);

	public QueryResponse getQueryResponse() {
		return queryResponse;
	}

	public void setQueryResponse(QueryResponse queryResponse) {
		this.queryResponse = queryResponse;
		this.queryResponseWrap.alreadyInitialized = true;
	}
	public static QueryResponse staticSetQueryResponse(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SearchList queryResponseInit() {
		if(!queryResponseWrap.alreadyInitialized) {
			_queryResponse(queryResponseWrap);
			if(queryResponse == null)
				setQueryResponse(queryResponseWrap.o);
		}
		queryResponseWrap.alreadyInitialized(true);
		return (SearchList)this;
	}

	//////////////////////
	// solrDocumentList //
	//////////////////////

	/**	 The entity solrDocumentList
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SolrDocumentList solrDocumentList;
	@JsonIgnore
	public Wrap<SolrDocumentList> solrDocumentListWrap = new Wrap<SolrDocumentList>().p(this).c(SolrDocumentList.class).var("solrDocumentList").o(solrDocumentList);

	/**	<br/> The entity solrDocumentList
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.search.SearchList&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:solrDocumentList">Find the entity solrDocumentList in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _solrDocumentList(Wrap<SolrDocumentList> c);

	public SolrDocumentList getSolrDocumentList() {
		return solrDocumentList;
	}

	public void setSolrDocumentList(SolrDocumentList solrDocumentList) {
		this.solrDocumentList = solrDocumentList;
		this.solrDocumentListWrap.alreadyInitialized = true;
	}
	public static SolrDocumentList staticSetSolrDocumentList(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SearchList solrDocumentListInit() {
		if(!solrDocumentListWrap.alreadyInitialized) {
			_solrDocumentList(solrDocumentListWrap);
			if(solrDocumentList == null)
				setSolrDocumentList(solrDocumentListWrap.o);
		}
		solrDocumentListWrap.alreadyInitialized(true);
		return (SearchList)this;
	}

	//////////
	// list //
	//////////

	/**	 The entity list
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<DEV>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected List<DEV> list = new ArrayList<DEV>();
	@JsonIgnore
	public Wrap<List<DEV>> listWrap = new Wrap<List<DEV>>().p(this).c(List.class).var("list").o(list);

	/**	<br/> The entity list
	 *  It is constructed before being initialized with the constructor by default List<DEV>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.search.SearchList&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:list">Find the entity list in Solr</a>
	 * <br/>
	 * @param list is the entity already constructed. 
	 **/
	protected abstract void _list(List<DEV> l);

	public List<DEV> getList() {
		return list;
	}

	public void setList(List<DEV> list) {
		this.list = list;
		this.listWrap.alreadyInitialized = true;
	}
	public SearchList addList(DEV...objets) {
		for(DEV o : objets) {
			addList(o);
		}
		return (SearchList)this;
	}
	public SearchList addList(DEV o) {
		if(o != null && !list.contains(o))
			this.list.add(o);
		return (SearchList)this;
	}
	protected SearchList listInit() {
		if(!listWrap.alreadyInitialized) {
			_list(list);
		}
		listWrap.alreadyInitialized(true);
		return (SearchList)this;
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedSearchList = false;

	public SearchList initDeepSearchList(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedSearchList) {
			alreadyInitializedSearchList = true;
			initDeepSearchList();
		}
		return (SearchList)this;
	}

	public void initDeepSearchList() {
		initSearchList();
	}

	public void initSearchList() {
		cInit();
		siteRequest_Init();
		storeInit();
		populateInit();
		fieldsInit();
		solrQueryInit();
		queryResponseInit();
		solrDocumentListInit();
		listInit();
	}

	public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepSearchList(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestSearchList(SiteRequestEnUS siteRequest_) {
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestSearchList(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainSearchList(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
		}
		return o;
	}
	public Object obtainSearchList(String var) {
		SearchList oSearchList = (SearchList)this;
		switch(var) {
			case "c":
				return oSearchList.c;
			case "siteRequest_":
				return oSearchList.siteRequest_;
			case "store":
				return oSearchList.store;
			case "populate":
				return oSearchList.populate;
			case "fields":
				return oSearchList.fields;
			case "solrQuery":
				return oSearchList.solrQuery;
			case "queryResponse":
				return oSearchList.queryResponse;
			case "solrDocumentList":
				return oSearchList.solrDocumentList;
			case "list":
				return oSearchList.list;
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
				o = attributeSearchList(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeSearchList(String var, Object val) {
		SearchList oSearchList = (SearchList)this;
		switch(var) {
			default:
				return null;
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetSearchList(entityVar,  siteRequest_, o);
	}
	public static Object staticSetSearchList(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "store":
			return SearchList.staticSetStore(siteRequest_, o);
		case "populate":
			return SearchList.staticSetPopulate(siteRequest_, o);
		case "fields":
			return SearchList.staticSetFields(siteRequest_, o);
			default:
				return null;
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrSearchList(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrSearchList(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "store":
			return SearchList.staticSolrStore(siteRequest_, (Boolean)o);
		case "populate":
			return SearchList.staticSolrPopulate(siteRequest_, (Boolean)o);
		case "fields":
			return SearchList.staticSolrFields(siteRequest_, (String)o);
			default:
				return null;
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrSearchList(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrSearchList(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "store":
			return SearchList.staticSolrStrStore(siteRequest_, (Boolean)o);
		case "populate":
			return SearchList.staticSolrStrPopulate(siteRequest_, (Boolean)o);
		case "fields":
			return SearchList.staticSolrStrFields(siteRequest_, (String)o);
			default:
				return null;
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqSearchList(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqSearchList(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "store":
			return SearchList.staticSolrFqStore(siteRequest_, o);
		case "populate":
			return SearchList.staticSolrFqPopulate(siteRequest_, o);
		case "fields":
			return SearchList.staticSolrFqFields(siteRequest_, o);
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
					o = defineSearchList(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSearchList(String var, String val) {
		switch(var) {
			default:
				return null;
		}
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestSearchList() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof SearchList) {
			SearchList original = (SearchList)o;
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
		if(!(o instanceof SearchList))
			return false;
		SearchList that = (SearchList)o;
		return true;
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("SearchList { ");
		sb.append(" }");
		return sb.toString();
	}
}
