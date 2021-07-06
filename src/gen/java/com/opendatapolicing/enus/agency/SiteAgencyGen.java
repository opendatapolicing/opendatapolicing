package com.opendatapolicing.enus.agency;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Date;
import org.slf4j.LoggerFactory;
import com.opendatapolicing.enus.state.SiteState;
import org.apache.commons.lang3.StringUtils;
import java.lang.Long;
import java.util.Map;
import io.vertx.core.json.JsonObject;
import com.opendatapolicing.enus.java.ZonedDateTimeSerializer;
import java.math.RoundingMode;
import com.opendatapolicing.enus.wrap.Wrap;
import com.opendatapolicing.enus.java.ZonedDateTimeDeserializer;
import java.math.MathContext;
import java.util.Set;
import com.opendatapolicing.enus.writer.AllWriter;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.vertx.core.Future;
import com.opendatapolicing.enus.request.api.ApiRequest;
import java.util.Objects;
import java.util.List;
import org.apache.solr.client.solrj.SolrQuery;
import java.util.Optional;
import org.apache.solr.client.solrj.util.ClientUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.opendatapolicing.enus.base.BaseModel;
import org.apache.solr.common.SolrInputDocument;
import org.apache.commons.lang3.exception.ExceptionUtils;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.HashMap;
import java.text.NumberFormat;
import com.opendatapolicing.enus.search.SearchList;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opendatapolicing.enus.config.ConfigKeys;
import java.lang.String;
import org.slf4j.Logger;
import io.vertx.core.Promise;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.apache.solr.client.solrj.SolrClient;
import io.vertx.core.json.JsonArray;
import org.apache.solr.common.SolrDocument;
import org.apache.commons.lang3.math.NumberUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.opendatapolicing.enus.request.SiteRequestEnUS;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class SiteAgencyGen<DEV> extends BaseModel {
	protected static final Logger LOG = LoggerFactory.getLogger(SiteAgency.class);

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("");

	/////////////////
	// agencyTitle //
	/////////////////

	/**	 The entity agencyTitle
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String agencyTitle;
	@JsonIgnore
	public Wrap<String> agencyTitleWrap = new Wrap<String>().var("agencyTitle").o(agencyTitle);

	/**	<br/> The entity agencyTitle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:agencyTitle">Find the entity agencyTitle in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _agencyTitle(Wrap<String> c);

	public String getAgencyTitle() {
		return agencyTitle;
	}
	public void setAgencyTitle(String o) {
		this.agencyTitle = SiteAgency.staticSetAgencyTitle(siteRequest_, o);
		this.agencyTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteAgency agencyTitleInit() {
		if(!agencyTitleWrap.alreadyInitialized) {
			_agencyTitle(agencyTitleWrap);
			if(agencyTitle == null)
				setAgencyTitle(agencyTitleWrap.o);
			agencyTitleWrap.o(null);
		}
		agencyTitleWrap.alreadyInitialized(true);
		return (SiteAgency)this;
	}

	public static String staticSolrAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return SiteAgency.staticSolrStrAgencyTitle(siteRequest_, SiteAgency.staticSolrAgencyTitle(siteRequest_, SiteAgency.staticSetAgencyTitle(siteRequest_, o)));
	}

	public String solrAgencyTitle() {
		return SiteAgency.staticSolrAgencyTitle(siteRequest_, agencyTitle);
	}

	public String strAgencyTitle() {
		return agencyTitle == null ? "" : agencyTitle;
	}

	public String sqlAgencyTitle() {
		return agencyTitle;
	}

	public String jsonAgencyTitle() {
		return agencyTitle == null ? "" : agencyTitle;
	}

	/////////////////
	// agencyAcsId //
	/////////////////

	/**	 The entity agencyAcsId
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String agencyAcsId;
	@JsonIgnore
	public Wrap<String> agencyAcsIdWrap = new Wrap<String>().var("agencyAcsId").o(agencyAcsId);

	/**	<br/> The entity agencyAcsId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:agencyAcsId">Find the entity agencyAcsId in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _agencyAcsId(Wrap<String> c);

	public String getAgencyAcsId() {
		return agencyAcsId;
	}
	public void setAgencyAcsId(String o) {
		this.agencyAcsId = SiteAgency.staticSetAgencyAcsId(siteRequest_, o);
		this.agencyAcsIdWrap.alreadyInitialized = true;
	}
	public static String staticSetAgencyAcsId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteAgency agencyAcsIdInit() {
		if(!agencyAcsIdWrap.alreadyInitialized) {
			_agencyAcsId(agencyAcsIdWrap);
			if(agencyAcsId == null)
				setAgencyAcsId(agencyAcsIdWrap.o);
			agencyAcsIdWrap.o(null);
		}
		agencyAcsIdWrap.alreadyInitialized(true);
		return (SiteAgency)this;
	}

	public static String staticSolrAgencyAcsId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrAgencyAcsId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqAgencyAcsId(SiteRequestEnUS siteRequest_, String o) {
		return SiteAgency.staticSolrStrAgencyAcsId(siteRequest_, SiteAgency.staticSolrAgencyAcsId(siteRequest_, SiteAgency.staticSetAgencyAcsId(siteRequest_, o)));
	}

	public String solrAgencyAcsId() {
		return SiteAgency.staticSolrAgencyAcsId(siteRequest_, agencyAcsId);
	}

	public String strAgencyAcsId() {
		return agencyAcsId == null ? "" : agencyAcsId;
	}

	public String sqlAgencyAcsId() {
		return agencyAcsId;
	}

	public String jsonAgencyAcsId() {
		return agencyAcsId == null ? "" : agencyAcsId;
	}

	//////////////
	// stateKey //
	//////////////

	/**	 The entity stateKey
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long stateKey;
	@JsonIgnore
	public Wrap<Long> stateKeyWrap = new Wrap<Long>().var("stateKey").o(stateKey);

	/**	<br/> The entity stateKey
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stateKey">Find the entity stateKey in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stateKey(Wrap<Long> c);

	public Long getStateKey() {
		return stateKey;
	}

	public void setStateKey(Long stateKey) {
		this.stateKey = stateKey;
		this.stateKeyWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setStateKey(String o) {
		this.stateKey = SiteAgency.staticSetStateKey(siteRequest_, o);
		this.stateKeyWrap.alreadyInitialized = true;
	}
	public static Long staticSetStateKey(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected SiteAgency stateKeyInit() {
		if(!stateKeyWrap.alreadyInitialized) {
			_stateKey(stateKeyWrap);
			if(stateKey == null)
				setStateKey(stateKeyWrap.o);
			stateKeyWrap.o(null);
		}
		stateKeyWrap.alreadyInitialized(true);
		return (SiteAgency)this;
	}

	public static Long staticSolrStateKey(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrStateKey(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStateKey(SiteRequestEnUS siteRequest_, String o) {
		return SiteAgency.staticSolrStrStateKey(siteRequest_, SiteAgency.staticSolrStateKey(siteRequest_, SiteAgency.staticSetStateKey(siteRequest_, o)));
	}

	public Long solrStateKey() {
		return SiteAgency.staticSolrStateKey(siteRequest_, stateKey);
	}

	public String strStateKey() {
		return stateKey == null ? "" : stateKey.toString();
	}

	public Long sqlStateKey() {
		return stateKey;
	}

	public String jsonStateKey() {
		return stateKey == null ? "" : stateKey.toString();
	}

	/////////////////
	// stateSearch //
	/////////////////

	/**	 The entity stateSearch
	 *	 is defined as null before being initialized. 
	 */
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected SearchList<SiteState> stateSearch;
	@JsonIgnore
	public Wrap<SearchList<SiteState>> stateSearchWrap = new Wrap<SearchList<SiteState>>().var("stateSearch").o(stateSearch);

	/**	<br/> The entity stateSearch
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stateSearch">Find the entity stateSearch in Solr</a>
	 * <br/>
	 * @param promise is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stateSearch(Promise<SearchList<SiteState>> promise);

	public SearchList<SiteState> getStateSearch() {
		return stateSearch;
	}

	public void setStateSearch(SearchList<SiteState> stateSearch) {
		this.stateSearch = stateSearch;
		this.stateSearchWrap.alreadyInitialized = true;
	}
	public static SearchList<SiteState> staticSetStateSearch(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected Future<SearchList<SiteState>> stateSearchPromise() {
		Promise<SearchList<SiteState>> promise = Promise.promise();
		if(!stateSearchWrap.alreadyInitialized) {
			Promise<SearchList<SiteState>> promise2 = Promise.promise();
			_stateSearch(promise2);
			promise2.future().onSuccess(o -> {
				if(o != null && stateSearch == null) {
					o.promiseDeepForClass(siteRequest_).onSuccess(a -> {
						setStateSearch(o);
						stateSearchWrap.alreadyInitialized(true);
						promise.complete(o);
					}).onFailure(ex -> {
						promise.fail(ex);
					});
				} else {
					stateSearchWrap.alreadyInitialized(true);
					promise.complete(o);
				}
			}).onFailure(ex -> {
				promise.fail(ex);
			});
		} else {
			promise.complete();
		}
		return promise.future();
	}

	////////////
	// state_ //
	////////////

	/**	 The entity state_
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected SiteState state_;
	@JsonIgnore
	public Wrap<SiteState> state_Wrap = new Wrap<SiteState>().var("state_").o(state_);

	/**	<br/> The entity state_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:state_">Find the entity state_ in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _state_(Wrap<SiteState> c);

	public SiteState getState_() {
		return state_;
	}

	public void setState_(SiteState state_) {
		this.state_ = state_;
		this.state_Wrap.alreadyInitialized = true;
	}
	public static SiteState staticSetState_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteAgency state_Init() {
		if(!state_Wrap.alreadyInitialized) {
			_state_(state_Wrap);
			if(state_ == null)
				setState_(state_Wrap.o);
			state_Wrap.o(null);
		}
		state_Wrap.alreadyInitialized(true);
		return (SiteAgency)this;
	}

	/////////////
	// stateId //
	/////////////

	/**	 The entity stateId
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String stateId;
	@JsonIgnore
	public Wrap<String> stateIdWrap = new Wrap<String>().var("stateId").o(stateId);

	/**	<br/> The entity stateId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stateId">Find the entity stateId in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stateId(Wrap<String> c);

	public String getStateId() {
		return stateId;
	}
	public void setStateId(String o) {
		this.stateId = SiteAgency.staticSetStateId(siteRequest_, o);
		this.stateIdWrap.alreadyInitialized = true;
	}
	public static String staticSetStateId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteAgency stateIdInit() {
		if(!stateIdWrap.alreadyInitialized) {
			_stateId(stateIdWrap);
			if(stateId == null)
				setStateId(stateIdWrap.o);
			stateIdWrap.o(null);
		}
		stateIdWrap.alreadyInitialized(true);
		return (SiteAgency)this;
	}

	public static String staticSolrStateId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStateId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStateId(SiteRequestEnUS siteRequest_, String o) {
		return SiteAgency.staticSolrStrStateId(siteRequest_, SiteAgency.staticSolrStateId(siteRequest_, SiteAgency.staticSetStateId(siteRequest_, o)));
	}

	public String solrStateId() {
		return SiteAgency.staticSolrStateId(siteRequest_, stateId);
	}

	public String strStateId() {
		return stateId == null ? "" : stateId;
	}

	public String sqlStateId() {
		return stateId;
	}

	public String jsonStateId() {
		return stateId == null ? "" : stateId;
	}

	///////////////
	// stateName //
	///////////////

	/**	 The entity stateName
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String stateName;
	@JsonIgnore
	public Wrap<String> stateNameWrap = new Wrap<String>().var("stateName").o(stateName);

	/**	<br/> The entity stateName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stateName">Find the entity stateName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stateName(Wrap<String> c);

	public String getStateName() {
		return stateName;
	}
	public void setStateName(String o) {
		this.stateName = SiteAgency.staticSetStateName(siteRequest_, o);
		this.stateNameWrap.alreadyInitialized = true;
	}
	public static String staticSetStateName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteAgency stateNameInit() {
		if(!stateNameWrap.alreadyInitialized) {
			_stateName(stateNameWrap);
			if(stateName == null)
				setStateName(stateNameWrap.o);
			stateNameWrap.o(null);
		}
		stateNameWrap.alreadyInitialized(true);
		return (SiteAgency)this;
	}

	public static String staticSolrStateName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStateName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStateName(SiteRequestEnUS siteRequest_, String o) {
		return SiteAgency.staticSolrStrStateName(siteRequest_, SiteAgency.staticSolrStateName(siteRequest_, SiteAgency.staticSetStateName(siteRequest_, o)));
	}

	public String solrStateName() {
		return SiteAgency.staticSolrStateName(siteRequest_, stateName);
	}

	public String strStateName() {
		return stateName == null ? "" : stateName;
	}

	public String sqlStateName() {
		return stateName;
	}

	public String jsonStateName() {
		return stateName == null ? "" : stateName;
	}

	///////////////////////
	// stateAbbreviation //
	///////////////////////

	/**	 The entity stateAbbreviation
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String stateAbbreviation;
	@JsonIgnore
	public Wrap<String> stateAbbreviationWrap = new Wrap<String>().var("stateAbbreviation").o(stateAbbreviation);

	/**	<br/> The entity stateAbbreviation
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stateAbbreviation">Find the entity stateAbbreviation in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stateAbbreviation(Wrap<String> c);

	public String getStateAbbreviation() {
		return stateAbbreviation;
	}
	public void setStateAbbreviation(String o) {
		this.stateAbbreviation = SiteAgency.staticSetStateAbbreviation(siteRequest_, o);
		this.stateAbbreviationWrap.alreadyInitialized = true;
	}
	public static String staticSetStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteAgency stateAbbreviationInit() {
		if(!stateAbbreviationWrap.alreadyInitialized) {
			_stateAbbreviation(stateAbbreviationWrap);
			if(stateAbbreviation == null)
				setStateAbbreviation(stateAbbreviationWrap.o);
			stateAbbreviationWrap.o(null);
		}
		stateAbbreviationWrap.alreadyInitialized(true);
		return (SiteAgency)this;
	}

	public static String staticSolrStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return SiteAgency.staticSolrStrStateAbbreviation(siteRequest_, SiteAgency.staticSolrStateAbbreviation(siteRequest_, SiteAgency.staticSetStateAbbreviation(siteRequest_, o)));
	}

	public String solrStateAbbreviation() {
		return SiteAgency.staticSolrStateAbbreviation(siteRequest_, stateAbbreviation);
	}

	public String strStateAbbreviation() {
		return stateAbbreviation == null ? "" : stateAbbreviation;
	}

	public String sqlStateAbbreviation() {
		return stateAbbreviation;
	}

	public String jsonStateAbbreviation() {
		return stateAbbreviation == null ? "" : stateAbbreviation;
	}

	///////////////////
	// objectSuggest //
	///////////////////

	/**	 The entity objectSuggest
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String objectSuggest;
	@JsonIgnore
	public Wrap<String> objectSuggestWrap = new Wrap<String>().var("objectSuggest").o(objectSuggest);

	/**	<br/> The entity objectSuggest
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:objectSuggest">Find the entity objectSuggest in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _objectSuggest(Wrap<String> c);

	public String getObjectSuggest() {
		return objectSuggest;
	}
	public void setObjectSuggest(String o) {
		this.objectSuggest = SiteAgency.staticSetObjectSuggest(siteRequest_, o);
		this.objectSuggestWrap.alreadyInitialized = true;
	}
	public static String staticSetObjectSuggest(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteAgency objectSuggestInit() {
		if(!objectSuggestWrap.alreadyInitialized) {
			_objectSuggest(objectSuggestWrap);
			if(objectSuggest == null)
				setObjectSuggest(objectSuggestWrap.o);
			objectSuggestWrap.o(null);
		}
		objectSuggestWrap.alreadyInitialized(true);
		return (SiteAgency)this;
	}

	public static String staticSolrObjectSuggest(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrObjectSuggest(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqObjectSuggest(SiteRequestEnUS siteRequest_, String o) {
		return SiteAgency.staticSolrStrObjectSuggest(siteRequest_, SiteAgency.staticSolrObjectSuggest(siteRequest_, SiteAgency.staticSetObjectSuggest(siteRequest_, o)));
	}

	public String solrObjectSuggest() {
		return SiteAgency.staticSolrObjectSuggest(siteRequest_, objectSuggest);
	}

	public String strObjectSuggest() {
		return objectSuggest == null ? "" : objectSuggest;
	}

	public String sqlObjectSuggest() {
		return objectSuggest;
	}

	public String jsonObjectSuggest() {
		return objectSuggest == null ? "" : objectSuggest;
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedSiteAgency = false;

	public Future<Void> promiseDeepSiteAgency(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedSiteAgency) {
			alreadyInitializedSiteAgency = true;
			return promiseDeepSiteAgency();
		} else {
			return Future.succeededFuture();
		}
	}

	public Future<Void> promiseDeepSiteAgency() {
		Promise<Void> promise = Promise.promise();
		Promise<Void> promise2 = Promise.promise();
		promiseSiteAgency(promise2);
		promise2.future().onSuccess(a -> {
			super.promiseDeepBaseModel(siteRequest_).onSuccess(b -> {
				promise.complete();
			}).onFailure(ex -> {
				promise.fail(ex);
			});
		}).onFailure(ex -> {
			promise.fail(ex);
		});
		return promise.future();
	}

	public Future<Void> promiseSiteAgency(Promise<Void> promise) {
		Future.future(a -> a.complete()).compose(a -> {
			Promise<Void> promise2 = Promise.promise();
			try {
				agencyTitleInit();
				agencyAcsIdInit();
				stateKeyInit();
				promise2.complete();
			} catch(Exception ex) {
				promise2.fail(ex);
			}
			return promise2.future();
		}).compose(a -> {
			Promise<Void> promise2 = Promise.promise();
			stateSearchPromise().onSuccess(stateSearch -> {
				promise2.complete();
			}).onFailure(ex -> {
				promise2.fail(ex);
			});
			return promise2.future();
		}).compose(a -> {
			Promise<Void> promise2 = Promise.promise();
			try {
				state_Init();
				stateIdInit();
				stateNameInit();
				stateAbbreviationInit();
				objectSuggestInit();
				promise2.complete();
			} catch(Exception ex) {
				promise2.fail(ex);
			}
			return promise2.future();
		}).onSuccess(a -> {
			promise.complete();
		}).onFailure(ex -> {
			promise.fail(ex);
		});
		return promise.future();
	}

	@Override public Future<Void> promiseDeepForClass(SiteRequestEnUS siteRequest_) {
		return promiseDeepSiteAgency(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestSiteAgency(SiteRequestEnUS siteRequest_) {
			super.siteRequestBaseModel(siteRequest_);
		if(stateSearch != null)
			stateSearch.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestSiteAgency(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainSiteAgency(v);
			else if(o instanceof BaseModel) {
				BaseModel baseModel = (BaseModel)o;
				o = baseModel.obtainForClass(v);
			}
			else if(o instanceof Map) {
				Map<?, ?> map = (Map<?, ?>)o;
				o = map.get(v);
			}
		}
		return o;
	}
	public Object obtainSiteAgency(String var) {
		SiteAgency oSiteAgency = (SiteAgency)this;
		switch(var) {
			case "agencyTitle":
				return oSiteAgency.agencyTitle;
			case "agencyAcsId":
				return oSiteAgency.agencyAcsId;
			case "stateKey":
				return oSiteAgency.stateKey;
			case "stateSearch":
				return oSiteAgency.stateSearch;
			case "state_":
				return oSiteAgency.state_;
			case "stateId":
				return oSiteAgency.stateId;
			case "stateName":
				return oSiteAgency.stateName;
			case "stateAbbreviation":
				return oSiteAgency.stateAbbreviation;
			case "objectSuggest":
				return oSiteAgency.objectSuggest;
			default:
				return super.obtainBaseModel(var);
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
				o = attributeSiteAgency(v, val);
			else if(o instanceof BaseModel) {
				BaseModel baseModel = (BaseModel)o;
				o = baseModel.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeSiteAgency(String var, Object val) {
		SiteAgency oSiteAgency = (SiteAgency)this;
		switch(var) {
			case "stateKey":
				if(oSiteAgency.getStateKey() == null)
					oSiteAgency.setStateKey(val == null ? null : (NumberUtils.isCreatable(val.toString()) ? Long.parseLong(val.toString()) : -1L));
				if(!saves.contains("stateKey"))
					saves.add("stateKey");
				return val;
			default:
				return super.attributeBaseModel(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetSiteAgency(entityVar,  siteRequest_, o);
	}
	public static Object staticSetSiteAgency(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "agencyTitle":
			return SiteAgency.staticSetAgencyTitle(siteRequest_, o);
		case "agencyAcsId":
			return SiteAgency.staticSetAgencyAcsId(siteRequest_, o);
		case "stateKey":
			return SiteAgency.staticSetStateKey(siteRequest_, o);
		case "stateId":
			return SiteAgency.staticSetStateId(siteRequest_, o);
		case "stateName":
			return SiteAgency.staticSetStateName(siteRequest_, o);
		case "stateAbbreviation":
			return SiteAgency.staticSetStateAbbreviation(siteRequest_, o);
		case "objectSuggest":
			return SiteAgency.staticSetObjectSuggest(siteRequest_, o);
			default:
				return BaseModel.staticSetBaseModel(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrSiteAgency(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrSiteAgency(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "agencyTitle":
			return SiteAgency.staticSolrAgencyTitle(siteRequest_, (String)o);
		case "agencyAcsId":
			return SiteAgency.staticSolrAgencyAcsId(siteRequest_, (String)o);
		case "stateKey":
			return SiteAgency.staticSolrStateKey(siteRequest_, (Long)o);
		case "stateId":
			return SiteAgency.staticSolrStateId(siteRequest_, (String)o);
		case "stateName":
			return SiteAgency.staticSolrStateName(siteRequest_, (String)o);
		case "stateAbbreviation":
			return SiteAgency.staticSolrStateAbbreviation(siteRequest_, (String)o);
		case "objectSuggest":
			return SiteAgency.staticSolrObjectSuggest(siteRequest_, (String)o);
			default:
				return BaseModel.staticSolrBaseModel(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrSiteAgency(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrSiteAgency(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "agencyTitle":
			return SiteAgency.staticSolrStrAgencyTitle(siteRequest_, (String)o);
		case "agencyAcsId":
			return SiteAgency.staticSolrStrAgencyAcsId(siteRequest_, (String)o);
		case "stateKey":
			return SiteAgency.staticSolrStrStateKey(siteRequest_, (Long)o);
		case "stateId":
			return SiteAgency.staticSolrStrStateId(siteRequest_, (String)o);
		case "stateName":
			return SiteAgency.staticSolrStrStateName(siteRequest_, (String)o);
		case "stateAbbreviation":
			return SiteAgency.staticSolrStrStateAbbreviation(siteRequest_, (String)o);
		case "objectSuggest":
			return SiteAgency.staticSolrStrObjectSuggest(siteRequest_, (String)o);
			default:
				return BaseModel.staticSolrStrBaseModel(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqSiteAgency(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqSiteAgency(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "agencyTitle":
			return SiteAgency.staticSolrFqAgencyTitle(siteRequest_, o);
		case "agencyAcsId":
			return SiteAgency.staticSolrFqAgencyAcsId(siteRequest_, o);
		case "stateKey":
			return SiteAgency.staticSolrFqStateKey(siteRequest_, o);
		case "stateId":
			return SiteAgency.staticSolrFqStateId(siteRequest_, o);
		case "stateName":
			return SiteAgency.staticSolrFqStateName(siteRequest_, o);
		case "stateAbbreviation":
			return SiteAgency.staticSolrFqStateAbbreviation(siteRequest_, o);
		case "objectSuggest":
			return SiteAgency.staticSolrFqObjectSuggest(siteRequest_, o);
			default:
				return BaseModel.staticSolrFqBaseModel(entityVar,  siteRequest_, o);
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
					o = defineSiteAgency(v, val);
				else if(o instanceof BaseModel) {
					BaseModel oBaseModel = (BaseModel)o;
					o = oBaseModel.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSiteAgency(String var, String val) {
		switch(var.toLowerCase()) {
			case "agencytitle":
				if(val != null)
					setAgencyTitle(val);
				saves.add("agencyTitle");
				return val;
			case "agencyacsid":
				if(val != null)
					setAgencyAcsId(val);
				saves.add("agencyAcsId");
				return val;
			case "statekey":
				if(val != null)
					setStateKey(val);
				saves.add("stateKey");
				return val;
			default:
				return super.defineBaseModel(var, val);
		}
	}

	@Override public boolean defineForClass(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = defineSiteAgency(v, val);
				else if(o instanceof BaseModel) {
					BaseModel oBaseModel = (BaseModel)o;
					o = oBaseModel.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSiteAgency(String var, Object val) {
		switch(var.toLowerCase()) {
			case "agencytitle":
				if(val instanceof String)
					setAgencyTitle((String)val);
				saves.add("agencyTitle");
				return val;
			case "agencyacsid":
				if(val instanceof String)
					setAgencyAcsId((String)val);
				saves.add("agencyAcsId");
				return val;
			case "statekey":
				if(val instanceof Long)
					setStateKey((Long)val);
				saves.add("stateKey");
				return val;
			default:
				return super.defineBaseModel(var, val);
		}
	}

	/////////////
	// populate //
	/////////////

	@Override public void populateForClass(SolrDocument solrDocument) {
		populateSiteAgency(solrDocument);
	}
	public void populateSiteAgency(SolrDocument solrDocument) {
		SiteAgency oSiteAgency = (SiteAgency)this;
		saves = (List<String>)solrDocument.get("saves_stored_strings");
		if(saves != null) {

			if(saves.contains("agencyTitle")) {
				String agencyTitle = (String)solrDocument.get("agencyTitle_stored_string");
				if(agencyTitle != null)
					oSiteAgency.setAgencyTitle(agencyTitle);
			}

			if(saves.contains("agencyAcsId")) {
				String agencyAcsId = (String)solrDocument.get("agencyAcsId_stored_string");
				if(agencyAcsId != null)
					oSiteAgency.setAgencyAcsId(agencyAcsId);
			}

			Long stateKey = (Long)solrDocument.get("stateKey_stored_long");
			if(stateKey != null)
				oSiteAgency.setStateKey(stateKey);

			if(saves.contains("stateId")) {
				String stateId = (String)solrDocument.get("stateId_stored_string");
				if(stateId != null)
					oSiteAgency.setStateId(stateId);
			}

			if(saves.contains("stateName")) {
				String stateName = (String)solrDocument.get("stateName_stored_string");
				if(stateName != null)
					oSiteAgency.setStateName(stateName);
			}

			if(saves.contains("stateAbbreviation")) {
				String stateAbbreviation = (String)solrDocument.get("stateAbbreviation_stored_string");
				if(stateAbbreviation != null)
					oSiteAgency.setStateAbbreviation(stateAbbreviation);
			}

			if(saves.contains("objectSuggest")) {
				String objectSuggest = (String)solrDocument.get("objectSuggest_suggested");
				oSiteAgency.setObjectSuggest(objectSuggest);
			}
		}

		super.populateBaseModel(solrDocument);
	}

	public void indexSiteAgency(SolrInputDocument document) {
		if(agencyTitle != null) {
			document.addField("agencyTitle_indexed_string", agencyTitle);
			document.addField("agencyTitle_stored_string", agencyTitle);
		}
		if(agencyAcsId != null) {
			document.addField("agencyAcsId_indexed_string", agencyAcsId);
			document.addField("agencyAcsId_stored_string", agencyAcsId);
		}
		if(stateKey != null) {
			document.addField("stateKey_indexed_long", stateKey);
			document.addField("stateKey_stored_long", stateKey);
		}
		if(stateId != null) {
			document.addField("stateId_indexed_string", stateId);
			document.addField("stateId_stored_string", stateId);
		}
		if(stateName != null) {
			document.addField("stateName_indexed_string", stateName);
			document.addField("stateName_stored_string", stateName);
		}
		if(stateAbbreviation != null) {
			document.addField("stateAbbreviation_indexed_string", stateAbbreviation);
			document.addField("stateAbbreviation_stored_string", stateAbbreviation);
		}
		if(objectSuggest != null) {
			document.addField("objectSuggest_suggested", objectSuggest);
		}
		super.indexBaseModel(document);

	}

	public static String varIndexedSiteAgency(String entityVar) {
		switch(entityVar) {
			case "agencyTitle":
				return "agencyTitle_indexed_string";
			case "agencyAcsId":
				return "agencyAcsId_indexed_string";
			case "stateKey":
				return "stateKey_indexed_long";
			case "stateId":
				return "stateId_indexed_string";
			case "stateName":
				return "stateName_indexed_string";
			case "stateAbbreviation":
				return "stateAbbreviation_indexed_string";
			case "objectSuggest":
				return "objectSuggest_suggested";
			default:
				return BaseModel.varIndexedBaseModel(entityVar);
		}
	}

	public static String varSearchSiteAgency(String entityVar) {
		switch(entityVar) {
			case "objectSuggest":
				return "objectSuggest_suggested";
			default:
				return BaseModel.varSearchBaseModel(entityVar);
		}
	}

	public static String varSuggestedSiteAgency(String entityVar) {
		switch(entityVar) {
			case "objectSuggest":
				return "objectSuggest_suggested";
			default:
				return BaseModel.varSuggestedBaseModel(entityVar);
		}
	}

	/////////////
	// store //
	/////////////

	@Override public void storeForClass(SolrDocument solrDocument) {
		storeSiteAgency(solrDocument);
	}
	public void storeSiteAgency(SolrDocument solrDocument) {
		SiteAgency oSiteAgency = (SiteAgency)this;

		oSiteAgency.setAgencyTitle(Optional.ofNullable(solrDocument.get("agencyTitle_stored_string")).map(v -> v.toString()).orElse(null));
		oSiteAgency.setAgencyAcsId(Optional.ofNullable(solrDocument.get("agencyAcsId_stored_string")).map(v -> v.toString()).orElse(null));
		oSiteAgency.setStateKey(Optional.ofNullable(solrDocument.get("stateKey_stored_long")).map(v -> v.toString()).orElse(null));
		oSiteAgency.setStateId(Optional.ofNullable(solrDocument.get("stateId_stored_string")).map(v -> v.toString()).orElse(null));
		oSiteAgency.setStateName(Optional.ofNullable(solrDocument.get("stateName_stored_string")).map(v -> v.toString()).orElse(null));
		oSiteAgency.setStateAbbreviation(Optional.ofNullable(solrDocument.get("stateAbbreviation_stored_string")).map(v -> v.toString()).orElse(null));
		String objectSuggest = (String)solrDocument.get("objectSuggest_suggested");
		oSiteAgency.setObjectSuggest(objectSuggest);

		super.storeBaseModel(solrDocument);
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestSiteAgency() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof SiteAgency) {
			SiteAgency original = (SiteAgency)o;
			if(!Objects.equals(agencyTitle, original.getAgencyTitle()))
				apiRequest.addVars("agencyTitle");
			if(!Objects.equals(agencyAcsId, original.getAgencyAcsId()))
				apiRequest.addVars("agencyAcsId");
			if(!Objects.equals(stateKey, original.getStateKey()))
				apiRequest.addVars("stateKey");
			if(!Objects.equals(stateId, original.getStateId()))
				apiRequest.addVars("stateId");
			if(!Objects.equals(stateName, original.getStateName()))
				apiRequest.addVars("stateName");
			if(!Objects.equals(stateAbbreviation, original.getStateAbbreviation()))
				apiRequest.addVars("stateAbbreviation");
			if(!Objects.equals(objectSuggest, original.getObjectSuggest()))
				apiRequest.addVars("objectSuggest");
			super.apiRequestBaseModel();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), agencyTitle, agencyAcsId, stateKey, stateId, stateName, stateAbbreviation, objectSuggest);
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof SiteAgency))
			return false;
		SiteAgency that = (SiteAgency)o;
		return super.equals(o)
				&& Objects.equals( agencyTitle, that.agencyTitle )
				&& Objects.equals( agencyAcsId, that.agencyAcsId )
				&& Objects.equals( stateKey, that.stateKey )
				&& Objects.equals( stateId, that.stateId )
				&& Objects.equals( stateName, that.stateName )
				&& Objects.equals( stateAbbreviation, that.stateAbbreviation )
				&& Objects.equals( objectSuggest, that.objectSuggest );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("SiteAgency { ");
		sb.append( "agencyTitle: \"" ).append(agencyTitle).append( "\"" );
		sb.append( ", agencyAcsId: \"" ).append(agencyAcsId).append( "\"" );
		sb.append( ", stateKey: " ).append(stateKey);
		sb.append( ", stateId: \"" ).append(stateId).append( "\"" );
		sb.append( ", stateName: \"" ).append(stateName).append( "\"" );
		sb.append( ", stateAbbreviation: \"" ).append(stateAbbreviation).append( "\"" );
		sb.append( ", objectSuggest: \"" ).append(objectSuggest).append( "\"" );
		sb.append(" }");
		return sb.toString();
	}

	public static final String VAR_agencyTitle = "agencyTitle";
	public static final String VAR_agencyAcsId = "agencyAcsId";
	public static final String VAR_stateKey = "stateKey";
	public static final String VAR_stateSearch = "stateSearch";
	public static final String VAR_state_ = "state_";
	public static final String VAR_stateId = "stateId";
	public static final String VAR_stateName = "stateName";
	public static final String VAR_stateAbbreviation = "stateAbbreviation";
	public static final String VAR_objectSuggest = "objectSuggest";
}
