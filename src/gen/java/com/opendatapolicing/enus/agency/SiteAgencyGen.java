package com.opendatapolicing.enus.agency;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Date;
import org.slf4j.LoggerFactory;
import com.opendatapolicing.enus.state.SiteState;
import org.apache.commons.lang3.StringUtils;
import java.lang.Integer;
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
import com.opendatapolicing.enus.cluster.Cluster;
import org.apache.solr.client.solrj.util.ClientUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
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
public abstract class SiteAgencyGen<DEV> extends Cluster {
	protected static final Logger LOG = LoggerFactory.getLogger(SiteAgency.class);

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("");

	///////////////
	// agencyKey //
	///////////////

	/**	 The entity agencyKey
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long agencyKey;
	@JsonIgnore
	public Wrap<Long> agencyKeyWrap = new Wrap<Long>().var("agencyKey").o(agencyKey);

	/**	<br/> The entity agencyKey
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:agencyKey">Find the entity agencyKey in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _agencyKey(Wrap<Long> c);

	public Long getAgencyKey() {
		return agencyKey;
	}

	public void setAgencyKey(Long agencyKey) {
		this.agencyKey = agencyKey;
		this.agencyKeyWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setAgencyKey(String o) {
		this.agencyKey = SiteAgency.staticSetAgencyKey(siteRequest_, o);
		this.agencyKeyWrap.alreadyInitialized = true;
	}
	public static Long staticSetAgencyKey(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected SiteAgency agencyKeyInit() {
		if(!agencyKeyWrap.alreadyInitialized) {
			_agencyKey(agencyKeyWrap);
			if(agencyKey == null)
				setAgencyKey(agencyKeyWrap.o);
			agencyKeyWrap.o(null);
		}
		agencyKeyWrap.alreadyInitialized(true);
		return (SiteAgency)this;
	}

	public static Long staticSolrAgencyKey(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrAgencyKey(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqAgencyKey(SiteRequestEnUS siteRequest_, String o) {
		return SiteAgency.staticSolrStrAgencyKey(siteRequest_, SiteAgency.staticSolrAgencyKey(siteRequest_, SiteAgency.staticSetAgencyKey(siteRequest_, o)));
	}

	public Long solrAgencyKey() {
		return SiteAgency.staticSolrAgencyKey(siteRequest_, agencyKey);
	}

	public String strAgencyKey() {
		return agencyKey == null ? "" : agencyKey.toString();
	}

	public Long sqlAgencyKey() {
		return agencyKey;
	}

	public String jsonAgencyKey() {
		return agencyKey == null ? "" : agencyKey.toString();
	}

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

	///////////////
	// imageLeft //
	///////////////

	/**	 The entity imageLeft
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer imageLeft;
	@JsonIgnore
	public Wrap<Integer> imageLeftWrap = new Wrap<Integer>().var("imageLeft").o(imageLeft);

	/**	<br/> The entity imageLeft
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:imageLeft">Find the entity imageLeft in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _imageLeft(Wrap<Integer> c);

	public Integer getImageLeft() {
		return imageLeft;
	}

	public void setImageLeft(Integer imageLeft) {
		this.imageLeft = imageLeft;
		this.imageLeftWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setImageLeft(String o) {
		this.imageLeft = SiteAgency.staticSetImageLeft(siteRequest_, o);
		this.imageLeftWrap.alreadyInitialized = true;
	}
	public static Integer staticSetImageLeft(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected SiteAgency imageLeftInit() {
		if(!imageLeftWrap.alreadyInitialized) {
			_imageLeft(imageLeftWrap);
			if(imageLeft == null)
				setImageLeft(imageLeftWrap.o);
			imageLeftWrap.o(null);
		}
		imageLeftWrap.alreadyInitialized(true);
		return (SiteAgency)this;
	}

	public static Integer staticSolrImageLeft(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrImageLeft(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqImageLeft(SiteRequestEnUS siteRequest_, String o) {
		return SiteAgency.staticSolrStrImageLeft(siteRequest_, SiteAgency.staticSolrImageLeft(siteRequest_, SiteAgency.staticSetImageLeft(siteRequest_, o)));
	}

	public Integer solrImageLeft() {
		return SiteAgency.staticSolrImageLeft(siteRequest_, imageLeft);
	}

	public String strImageLeft() {
		return imageLeft == null ? "" : imageLeft.toString();
	}

	public Integer sqlImageLeft() {
		return imageLeft;
	}

	public String jsonImageLeft() {
		return imageLeft == null ? "" : imageLeft.toString();
	}

	//////////////
	// imageTop //
	//////////////

	/**	 The entity imageTop
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer imageTop;
	@JsonIgnore
	public Wrap<Integer> imageTopWrap = new Wrap<Integer>().var("imageTop").o(imageTop);

	/**	<br/> The entity imageTop
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:imageTop">Find the entity imageTop in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _imageTop(Wrap<Integer> c);

	public Integer getImageTop() {
		return imageTop;
	}

	public void setImageTop(Integer imageTop) {
		this.imageTop = imageTop;
		this.imageTopWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setImageTop(String o) {
		this.imageTop = SiteAgency.staticSetImageTop(siteRequest_, o);
		this.imageTopWrap.alreadyInitialized = true;
	}
	public static Integer staticSetImageTop(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected SiteAgency imageTopInit() {
		if(!imageTopWrap.alreadyInitialized) {
			_imageTop(imageTopWrap);
			if(imageTop == null)
				setImageTop(imageTopWrap.o);
			imageTopWrap.o(null);
		}
		imageTopWrap.alreadyInitialized(true);
		return (SiteAgency)this;
	}

	public static Integer staticSolrImageTop(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrImageTop(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqImageTop(SiteRequestEnUS siteRequest_, String o) {
		return SiteAgency.staticSolrStrImageTop(siteRequest_, SiteAgency.staticSolrImageTop(siteRequest_, SiteAgency.staticSetImageTop(siteRequest_, o)));
	}

	public Integer solrImageTop() {
		return SiteAgency.staticSolrImageTop(siteRequest_, imageTop);
	}

	public String strImageTop() {
		return imageTop == null ? "" : imageTop.toString();
	}

	public Integer sqlImageTop() {
		return imageTop;
	}

	public String jsonImageTop() {
		return imageTop == null ? "" : imageTop.toString();
	}

	/////////////////
	// imageCoords //
	/////////////////

	/**	 The entity imageCoords
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String imageCoords;
	@JsonIgnore
	public Wrap<String> imageCoordsWrap = new Wrap<String>().var("imageCoords").o(imageCoords);

	/**	<br/> The entity imageCoords
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:imageCoords">Find the entity imageCoords in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _imageCoords(Wrap<String> c);

	public String getImageCoords() {
		return imageCoords;
	}
	public void setImageCoords(String o) {
		this.imageCoords = SiteAgency.staticSetImageCoords(siteRequest_, o);
		this.imageCoordsWrap.alreadyInitialized = true;
	}
	public static String staticSetImageCoords(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteAgency imageCoordsInit() {
		if(!imageCoordsWrap.alreadyInitialized) {
			_imageCoords(imageCoordsWrap);
			if(imageCoords == null)
				setImageCoords(imageCoordsWrap.o);
			imageCoordsWrap.o(null);
		}
		imageCoordsWrap.alreadyInitialized(true);
		return (SiteAgency)this;
	}

	public static String staticSolrImageCoords(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrImageCoords(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqImageCoords(SiteRequestEnUS siteRequest_, String o) {
		return SiteAgency.staticSolrStrImageCoords(siteRequest_, SiteAgency.staticSolrImageCoords(siteRequest_, SiteAgency.staticSetImageCoords(siteRequest_, o)));
	}

	public String solrImageCoords() {
		return SiteAgency.staticSolrImageCoords(siteRequest_, imageCoords);
	}

	public String strImageCoords() {
		return imageCoords == null ? "" : imageCoords;
	}

	public String sqlImageCoords() {
		return imageCoords;
	}

	public String jsonImageCoords() {
		return imageCoords == null ? "" : imageCoords;
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

	////////////////////
	// agencyOnlyName //
	////////////////////

	/**	 The entity agencyOnlyName
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String agencyOnlyName;
	@JsonIgnore
	public Wrap<String> agencyOnlyNameWrap = new Wrap<String>().var("agencyOnlyName").o(agencyOnlyName);

	/**	<br/> The entity agencyOnlyName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:agencyOnlyName">Find the entity agencyOnlyName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _agencyOnlyName(Wrap<String> c);

	public String getAgencyOnlyName() {
		return agencyOnlyName;
	}
	public void setAgencyOnlyName(String o) {
		this.agencyOnlyName = SiteAgency.staticSetAgencyOnlyName(siteRequest_, o);
		this.agencyOnlyNameWrap.alreadyInitialized = true;
	}
	public static String staticSetAgencyOnlyName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteAgency agencyOnlyNameInit() {
		if(!agencyOnlyNameWrap.alreadyInitialized) {
			_agencyOnlyName(agencyOnlyNameWrap);
			if(agencyOnlyName == null)
				setAgencyOnlyName(agencyOnlyNameWrap.o);
			agencyOnlyNameWrap.o(null);
		}
		agencyOnlyNameWrap.alreadyInitialized(true);
		return (SiteAgency)this;
	}

	public static String staticSolrAgencyOnlyName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrAgencyOnlyName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqAgencyOnlyName(SiteRequestEnUS siteRequest_, String o) {
		return SiteAgency.staticSolrStrAgencyOnlyName(siteRequest_, SiteAgency.staticSolrAgencyOnlyName(siteRequest_, SiteAgency.staticSetAgencyOnlyName(siteRequest_, o)));
	}

	public String solrAgencyOnlyName() {
		return SiteAgency.staticSolrAgencyOnlyName(siteRequest_, agencyOnlyName);
	}

	public String strAgencyOnlyName() {
		return agencyOnlyName == null ? "" : agencyOnlyName;
	}

	public String sqlAgencyOnlyName() {
		return agencyOnlyName;
	}

	public String jsonAgencyOnlyName() {
		return agencyOnlyName == null ? "" : agencyOnlyName;
	}

	////////////////////////
	// agencyCompleteName //
	////////////////////////

	/**	 The entity agencyCompleteName
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String agencyCompleteName;
	@JsonIgnore
	public Wrap<String> agencyCompleteNameWrap = new Wrap<String>().var("agencyCompleteName").o(agencyCompleteName);

	/**	<br/> The entity agencyCompleteName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:agencyCompleteName">Find the entity agencyCompleteName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _agencyCompleteName(Wrap<String> c);

	public String getAgencyCompleteName() {
		return agencyCompleteName;
	}
	public void setAgencyCompleteName(String o) {
		this.agencyCompleteName = SiteAgency.staticSetAgencyCompleteName(siteRequest_, o);
		this.agencyCompleteNameWrap.alreadyInitialized = true;
	}
	public static String staticSetAgencyCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteAgency agencyCompleteNameInit() {
		if(!agencyCompleteNameWrap.alreadyInitialized) {
			_agencyCompleteName(agencyCompleteNameWrap);
			if(agencyCompleteName == null)
				setAgencyCompleteName(agencyCompleteNameWrap.o);
			agencyCompleteNameWrap.o(null);
		}
		agencyCompleteNameWrap.alreadyInitialized(true);
		return (SiteAgency)this;
	}

	public static String staticSolrAgencyCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrAgencyCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqAgencyCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return SiteAgency.staticSolrStrAgencyCompleteName(siteRequest_, SiteAgency.staticSolrAgencyCompleteName(siteRequest_, SiteAgency.staticSetAgencyCompleteName(siteRequest_, o)));
	}

	public String solrAgencyCompleteName() {
		return SiteAgency.staticSolrAgencyCompleteName(siteRequest_, agencyCompleteName);
	}

	public String strAgencyCompleteName() {
		return agencyCompleteName == null ? "" : agencyCompleteName;
	}

	public String sqlAgencyCompleteName() {
		return agencyCompleteName;
	}

	public String jsonAgencyCompleteName() {
		return agencyCompleteName == null ? "" : agencyCompleteName;
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
			super.promiseDeepCluster(siteRequest_).onSuccess(b -> {
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
				agencyKeyInit();
				agencyTitleInit();
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
				stateKeyInit();
				imageLeftInit();
				imageTopInit();
				imageCoordsInit();
				stateIdInit();
				stateNameInit();
				stateAbbreviationInit();
				agencyOnlyNameInit();
				agencyCompleteNameInit();
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
			super.siteRequestCluster(siteRequest_);
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
	public Object obtainSiteAgency(String var) {
		SiteAgency oSiteAgency = (SiteAgency)this;
		switch(var) {
			case "agencyKey":
				return oSiteAgency.agencyKey;
			case "agencyTitle":
				return oSiteAgency.agencyTitle;
			case "stateSearch":
				return oSiteAgency.stateSearch;
			case "state_":
				return oSiteAgency.state_;
			case "stateKey":
				return oSiteAgency.stateKey;
			case "imageLeft":
				return oSiteAgency.imageLeft;
			case "imageTop":
				return oSiteAgency.imageTop;
			case "imageCoords":
				return oSiteAgency.imageCoords;
			case "stateId":
				return oSiteAgency.stateId;
			case "stateName":
				return oSiteAgency.stateName;
			case "stateAbbreviation":
				return oSiteAgency.stateAbbreviation;
			case "agencyOnlyName":
				return oSiteAgency.agencyOnlyName;
			case "agencyCompleteName":
				return oSiteAgency.agencyCompleteName;
			default:
				return super.obtainCluster(var);
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
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeSiteAgency(String var, Object val) {
		SiteAgency oSiteAgency = (SiteAgency)this;
		switch(var) {
			case "stateKey":
				if(oSiteAgency.getStateKey() == null)
					oSiteAgency.setStateKey((Long)val);
				if(!saves.contains("stateKey"))
					saves.add("stateKey");
				return val;
			default:
				return super.attributeCluster(var, val);
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
		case "agencyKey":
			return SiteAgency.staticSetAgencyKey(siteRequest_, o);
		case "agencyTitle":
			return SiteAgency.staticSetAgencyTitle(siteRequest_, o);
		case "stateKey":
			return SiteAgency.staticSetStateKey(siteRequest_, o);
		case "imageLeft":
			return SiteAgency.staticSetImageLeft(siteRequest_, o);
		case "imageTop":
			return SiteAgency.staticSetImageTop(siteRequest_, o);
		case "imageCoords":
			return SiteAgency.staticSetImageCoords(siteRequest_, o);
		case "stateId":
			return SiteAgency.staticSetStateId(siteRequest_, o);
		case "stateName":
			return SiteAgency.staticSetStateName(siteRequest_, o);
		case "stateAbbreviation":
			return SiteAgency.staticSetStateAbbreviation(siteRequest_, o);
		case "agencyOnlyName":
			return SiteAgency.staticSetAgencyOnlyName(siteRequest_, o);
		case "agencyCompleteName":
			return SiteAgency.staticSetAgencyCompleteName(siteRequest_, o);
			default:
				return Cluster.staticSetCluster(entityVar,  siteRequest_, o);
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
		case "agencyKey":
			return SiteAgency.staticSolrAgencyKey(siteRequest_, (Long)o);
		case "agencyTitle":
			return SiteAgency.staticSolrAgencyTitle(siteRequest_, (String)o);
		case "stateKey":
			return SiteAgency.staticSolrStateKey(siteRequest_, (Long)o);
		case "imageLeft":
			return SiteAgency.staticSolrImageLeft(siteRequest_, (Integer)o);
		case "imageTop":
			return SiteAgency.staticSolrImageTop(siteRequest_, (Integer)o);
		case "imageCoords":
			return SiteAgency.staticSolrImageCoords(siteRequest_, (String)o);
		case "stateId":
			return SiteAgency.staticSolrStateId(siteRequest_, (String)o);
		case "stateName":
			return SiteAgency.staticSolrStateName(siteRequest_, (String)o);
		case "stateAbbreviation":
			return SiteAgency.staticSolrStateAbbreviation(siteRequest_, (String)o);
		case "agencyOnlyName":
			return SiteAgency.staticSolrAgencyOnlyName(siteRequest_, (String)o);
		case "agencyCompleteName":
			return SiteAgency.staticSolrAgencyCompleteName(siteRequest_, (String)o);
			default:
				return Cluster.staticSolrCluster(entityVar,  siteRequest_, o);
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
		case "agencyKey":
			return SiteAgency.staticSolrStrAgencyKey(siteRequest_, (Long)o);
		case "agencyTitle":
			return SiteAgency.staticSolrStrAgencyTitle(siteRequest_, (String)o);
		case "stateKey":
			return SiteAgency.staticSolrStrStateKey(siteRequest_, (Long)o);
		case "imageLeft":
			return SiteAgency.staticSolrStrImageLeft(siteRequest_, (Integer)o);
		case "imageTop":
			return SiteAgency.staticSolrStrImageTop(siteRequest_, (Integer)o);
		case "imageCoords":
			return SiteAgency.staticSolrStrImageCoords(siteRequest_, (String)o);
		case "stateId":
			return SiteAgency.staticSolrStrStateId(siteRequest_, (String)o);
		case "stateName":
			return SiteAgency.staticSolrStrStateName(siteRequest_, (String)o);
		case "stateAbbreviation":
			return SiteAgency.staticSolrStrStateAbbreviation(siteRequest_, (String)o);
		case "agencyOnlyName":
			return SiteAgency.staticSolrStrAgencyOnlyName(siteRequest_, (String)o);
		case "agencyCompleteName":
			return SiteAgency.staticSolrStrAgencyCompleteName(siteRequest_, (String)o);
			default:
				return Cluster.staticSolrStrCluster(entityVar,  siteRequest_, o);
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
		case "agencyKey":
			return SiteAgency.staticSolrFqAgencyKey(siteRequest_, o);
		case "agencyTitle":
			return SiteAgency.staticSolrFqAgencyTitle(siteRequest_, o);
		case "stateKey":
			return SiteAgency.staticSolrFqStateKey(siteRequest_, o);
		case "imageLeft":
			return SiteAgency.staticSolrFqImageLeft(siteRequest_, o);
		case "imageTop":
			return SiteAgency.staticSolrFqImageTop(siteRequest_, o);
		case "imageCoords":
			return SiteAgency.staticSolrFqImageCoords(siteRequest_, o);
		case "stateId":
			return SiteAgency.staticSolrFqStateId(siteRequest_, o);
		case "stateName":
			return SiteAgency.staticSolrFqStateName(siteRequest_, o);
		case "stateAbbreviation":
			return SiteAgency.staticSolrFqStateAbbreviation(siteRequest_, o);
		case "agencyOnlyName":
			return SiteAgency.staticSolrFqAgencyOnlyName(siteRequest_, o);
		case "agencyCompleteName":
			return SiteAgency.staticSolrFqAgencyCompleteName(siteRequest_, o);
			default:
				return Cluster.staticSolrFqCluster(entityVar,  siteRequest_, o);
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
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
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
			case "statekey":
				if(val != null)
					setStateKey(val);
				saves.add("stateKey");
				return val;
			case "imageleft":
				if(val != null)
					setImageLeft(val);
				saves.add("imageLeft");
				return val;
			case "imagetop":
				if(val != null)
					setImageTop(val);
				saves.add("imageTop");
				return val;
			case "imagecoords":
				if(val != null)
					setImageCoords(val);
				saves.add("imageCoords");
				return val;
			default:
				return super.defineCluster(var, val);
		}
	}

	@Override public boolean defineForClass(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = defineSiteAgency(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
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
			case "statekey":
				if(val instanceof Long)
					setStateKey((Long)val);
				saves.add("stateKey");
				return val;
			case "imageleft":
				if(val instanceof Integer)
					setImageLeft((Integer)val);
				saves.add("imageLeft");
				return val;
			case "imagetop":
				if(val instanceof Integer)
					setImageTop((Integer)val);
				saves.add("imageTop");
				return val;
			case "imagecoords":
				if(val instanceof String)
					setImageCoords((String)val);
				saves.add("imageCoords");
				return val;
			default:
				return super.defineCluster(var, val);
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

			if(saves.contains("agencyKey")) {
				Long agencyKey = (Long)solrDocument.get("agencyKey_stored_long");
				if(agencyKey != null)
					oSiteAgency.setAgencyKey(agencyKey);
			}

			if(saves.contains("agencyTitle")) {
				String agencyTitle = (String)solrDocument.get("agencyTitle_stored_string");
				if(agencyTitle != null)
					oSiteAgency.setAgencyTitle(agencyTitle);
			}

			Long stateKey = (Long)solrDocument.get("stateKey_stored_long");
			if(stateKey != null)
				oSiteAgency.setStateKey(stateKey);

			if(saves.contains("imageLeft")) {
				Integer imageLeft = (Integer)solrDocument.get("imageLeft_stored_int");
				if(imageLeft != null)
					oSiteAgency.setImageLeft(imageLeft);
			}

			if(saves.contains("imageTop")) {
				Integer imageTop = (Integer)solrDocument.get("imageTop_stored_int");
				if(imageTop != null)
					oSiteAgency.setImageTop(imageTop);
			}

			if(saves.contains("imageCoords")) {
				String imageCoords = (String)solrDocument.get("imageCoords_stored_string");
				if(imageCoords != null)
					oSiteAgency.setImageCoords(imageCoords);
			}

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

			if(saves.contains("agencyOnlyName")) {
				String agencyOnlyName = (String)solrDocument.get("agencyOnlyName_stored_string");
				if(agencyOnlyName != null)
					oSiteAgency.setAgencyOnlyName(agencyOnlyName);
			}

			if(saves.contains("agencyCompleteName")) {
				String agencyCompleteName = (String)solrDocument.get("agencyCompleteName_stored_string");
				if(agencyCompleteName != null)
					oSiteAgency.setAgencyCompleteName(agencyCompleteName);
			}
		}

		super.populateCluster(solrDocument);
	}

	public void indexSiteAgency(SolrInputDocument document) {
		if(agencyKey != null) {
			document.addField("agencyKey_indexed_long", agencyKey);
			document.addField("agencyKey_stored_long", agencyKey);
		}
		if(agencyTitle != null) {
			document.addField("agencyTitle_indexed_string", agencyTitle);
			document.addField("agencyTitle_stored_string", agencyTitle);
		}
		if(stateKey != null) {
			document.addField("stateKey_indexed_long", stateKey);
			document.addField("stateKey_stored_long", stateKey);
		}
		if(imageLeft != null) {
			document.addField("imageLeft_indexed_int", imageLeft);
			document.addField("imageLeft_stored_int", imageLeft);
		}
		if(imageTop != null) {
			document.addField("imageTop_indexed_int", imageTop);
			document.addField("imageTop_stored_int", imageTop);
		}
		if(imageCoords != null) {
			document.addField("imageCoords_indexed_string", imageCoords);
			document.addField("imageCoords_stored_string", imageCoords);
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
		if(agencyOnlyName != null) {
			document.addField("agencyOnlyName_indexed_string", agencyOnlyName);
			document.addField("agencyOnlyName_stored_string", agencyOnlyName);
		}
		if(agencyCompleteName != null) {
			document.addField("agencyCompleteName_indexed_string", agencyCompleteName);
			document.addField("agencyCompleteName_stored_string", agencyCompleteName);
		}
		super.indexCluster(document);

	}

	public static String varIndexedSiteAgency(String entityVar) {
		switch(entityVar) {
			case "agencyKey":
				return "agencyKey_indexed_long";
			case "agencyTitle":
				return "agencyTitle_indexed_string";
			case "stateKey":
				return "stateKey_indexed_long";
			case "imageLeft":
				return "imageLeft_indexed_int";
			case "imageTop":
				return "imageTop_indexed_int";
			case "imageCoords":
				return "imageCoords_indexed_string";
			case "stateId":
				return "stateId_indexed_string";
			case "stateName":
				return "stateName_indexed_string";
			case "stateAbbreviation":
				return "stateAbbreviation_indexed_string";
			case "agencyOnlyName":
				return "agencyOnlyName_indexed_string";
			case "agencyCompleteName":
				return "agencyCompleteName_indexed_string";
			default:
				return Cluster.varIndexedCluster(entityVar);
		}
	}

	public static String varSearchSiteAgency(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSearchCluster(entityVar);
		}
	}

	public static String varSuggestedSiteAgency(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSuggestedCluster(entityVar);
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

		oSiteAgency.setAgencyKey(Optional.ofNullable(solrDocument.get("agencyKey_stored_long")).map(v -> v.toString()).orElse(null));
		oSiteAgency.setAgencyTitle(Optional.ofNullable(solrDocument.get("agencyTitle_stored_string")).map(v -> v.toString()).orElse(null));
		oSiteAgency.setStateKey(Optional.ofNullable(solrDocument.get("stateKey_stored_long")).map(v -> v.toString()).orElse(null));
		oSiteAgency.setImageLeft(Optional.ofNullable(solrDocument.get("imageLeft_stored_int")).map(v -> v.toString()).orElse(null));
		oSiteAgency.setImageTop(Optional.ofNullable(solrDocument.get("imageTop_stored_int")).map(v -> v.toString()).orElse(null));
		oSiteAgency.setImageCoords(Optional.ofNullable(solrDocument.get("imageCoords_stored_string")).map(v -> v.toString()).orElse(null));
		oSiteAgency.setStateId(Optional.ofNullable(solrDocument.get("stateId_stored_string")).map(v -> v.toString()).orElse(null));
		oSiteAgency.setStateName(Optional.ofNullable(solrDocument.get("stateName_stored_string")).map(v -> v.toString()).orElse(null));
		oSiteAgency.setStateAbbreviation(Optional.ofNullable(solrDocument.get("stateAbbreviation_stored_string")).map(v -> v.toString()).orElse(null));
		oSiteAgency.setAgencyOnlyName(Optional.ofNullable(solrDocument.get("agencyOnlyName_stored_string")).map(v -> v.toString()).orElse(null));
		oSiteAgency.setAgencyCompleteName(Optional.ofNullable(solrDocument.get("agencyCompleteName_stored_string")).map(v -> v.toString()).orElse(null));

		super.storeCluster(solrDocument);
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestSiteAgency() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof SiteAgency) {
			SiteAgency original = (SiteAgency)o;
			if(!Objects.equals(agencyKey, original.getAgencyKey()))
				apiRequest.addVars("agencyKey");
			if(!Objects.equals(agencyTitle, original.getAgencyTitle()))
				apiRequest.addVars("agencyTitle");
			if(!Objects.equals(stateKey, original.getStateKey()))
				apiRequest.addVars("stateKey");
			if(!Objects.equals(imageLeft, original.getImageLeft()))
				apiRequest.addVars("imageLeft");
			if(!Objects.equals(imageTop, original.getImageTop()))
				apiRequest.addVars("imageTop");
			if(!Objects.equals(imageCoords, original.getImageCoords()))
				apiRequest.addVars("imageCoords");
			if(!Objects.equals(stateId, original.getStateId()))
				apiRequest.addVars("stateId");
			if(!Objects.equals(stateName, original.getStateName()))
				apiRequest.addVars("stateName");
			if(!Objects.equals(stateAbbreviation, original.getStateAbbreviation()))
				apiRequest.addVars("stateAbbreviation");
			if(!Objects.equals(agencyOnlyName, original.getAgencyOnlyName()))
				apiRequest.addVars("agencyOnlyName");
			if(!Objects.equals(agencyCompleteName, original.getAgencyCompleteName()))
				apiRequest.addVars("agencyCompleteName");
			super.apiRequestCluster();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), agencyKey, agencyTitle, stateKey, imageLeft, imageTop, imageCoords, stateId, stateName, stateAbbreviation, agencyOnlyName, agencyCompleteName);
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
				&& Objects.equals( agencyKey, that.agencyKey )
				&& Objects.equals( agencyTitle, that.agencyTitle )
				&& Objects.equals( stateKey, that.stateKey )
				&& Objects.equals( imageLeft, that.imageLeft )
				&& Objects.equals( imageTop, that.imageTop )
				&& Objects.equals( imageCoords, that.imageCoords )
				&& Objects.equals( stateId, that.stateId )
				&& Objects.equals( stateName, that.stateName )
				&& Objects.equals( stateAbbreviation, that.stateAbbreviation )
				&& Objects.equals( agencyOnlyName, that.agencyOnlyName )
				&& Objects.equals( agencyCompleteName, that.agencyCompleteName );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("SiteAgency { ");
		sb.append( "agencyKey: " ).append(agencyKey);
		sb.append( ", agencyTitle: \"" ).append(agencyTitle).append( "\"" );
		sb.append( ", stateKey: " ).append(stateKey);
		sb.append( ", imageLeft: " ).append(imageLeft);
		sb.append( ", imageTop: " ).append(imageTop);
		sb.append( ", imageCoords: \"" ).append(imageCoords).append( "\"" );
		sb.append( ", stateId: \"" ).append(stateId).append( "\"" );
		sb.append( ", stateName: \"" ).append(stateName).append( "\"" );
		sb.append( ", stateAbbreviation: \"" ).append(stateAbbreviation).append( "\"" );
		sb.append( ", agencyOnlyName: \"" ).append(agencyOnlyName).append( "\"" );
		sb.append( ", agencyCompleteName: \"" ).append(agencyCompleteName).append( "\"" );
		sb.append(" }");
		return sb.toString();
	}

	public static final String VAR_agencyKey = "agencyKey";
	public static final String VAR_agencyTitle = "agencyTitle";
	public static final String VAR_stateSearch = "stateSearch";
	public static final String VAR_state_ = "state_";
	public static final String VAR_stateKey = "stateKey";
	public static final String VAR_imageLeft = "imageLeft";
	public static final String VAR_imageTop = "imageTop";
	public static final String VAR_imageCoords = "imageCoords";
	public static final String VAR_stateId = "stateId";
	public static final String VAR_stateName = "stateName";
	public static final String VAR_stateAbbreviation = "stateAbbreviation";
	public static final String VAR_agencyOnlyName = "agencyOnlyName";
	public static final String VAR_agencyCompleteName = "agencyCompleteName";
}
