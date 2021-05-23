package com.opendatapolicing.enus.trafficcontraband;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Date;
import java.time.ZonedDateTime;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import java.lang.Integer;
import java.math.BigDecimal;
import java.lang.Long;
import java.util.Locale;
import java.util.Map;
import io.vertx.core.json.JsonObject;
import com.opendatapolicing.enus.java.ZonedDateTimeSerializer;
import java.time.ZoneOffset;
import com.opendatapolicing.enus.trafficsearch.TrafficSearch;
import java.math.RoundingMode;
import com.opendatapolicing.enus.wrap.Wrap;
import com.opendatapolicing.enus.java.ZonedDateTimeDeserializer;
import java.math.MathContext;
import java.util.Set;
import com.opendatapolicing.enus.writer.AllWriter;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import io.vertx.core.Future;
import com.opendatapolicing.enus.request.api.ApiRequest;
import java.time.ZoneId;
import java.util.Objects;
import java.util.List;
import java.time.OffsetDateTime;
import org.apache.solr.client.solrj.SolrQuery;
import java.util.Optional;
import com.opendatapolicing.enus.cluster.Cluster;
import org.apache.solr.client.solrj.util.ClientUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.solr.common.SolrInputDocument;
import org.apache.commons.lang3.exception.ExceptionUtils;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.text.NumberFormat;
import com.opendatapolicing.enus.search.SearchList;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.Boolean;
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
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.math.NumberUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.opendatapolicing.enus.request.SiteRequestEnUS;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class TrafficContrabandGen<DEV> extends Cluster {
	protected static final Logger LOG = LoggerFactory.getLogger(TrafficContraband.class);

	public static final List<String> ROLES = Arrays.asList("SiteService");
	public static final List<String> ROLE_READS = Arrays.asList("");

	///////////////////
	// contrabandKey //
	///////////////////

	/**	 The entity contrabandKey
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long contrabandKey;
	@JsonIgnore
	public Wrap<Long> contrabandKeyWrap = new Wrap<Long>().var("contrabandKey").o(contrabandKey);

	/**	<br/> The entity contrabandKey
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:contrabandKey">Find the entity contrabandKey in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _contrabandKey(Wrap<Long> c);

	public Long getContrabandKey() {
		return contrabandKey;
	}

	public void setContrabandKey(Long contrabandKey) {
		this.contrabandKey = contrabandKey;
		this.contrabandKeyWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setContrabandKey(String o) {
		this.contrabandKey = TrafficContraband.staticSetContrabandKey(siteRequest_, o);
		this.contrabandKeyWrap.alreadyInitialized = true;
	}
	public static Long staticSetContrabandKey(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected TrafficContraband contrabandKeyInit() {
		if(!contrabandKeyWrap.alreadyInitialized) {
			_contrabandKey(contrabandKeyWrap);
			if(contrabandKey == null)
				setContrabandKey(contrabandKeyWrap.o);
			contrabandKeyWrap.o(null);
		}
		contrabandKeyWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Long staticSolrContrabandKey(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrContrabandKey(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqContrabandKey(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrContrabandKey(siteRequest_, TrafficContraband.staticSolrContrabandKey(siteRequest_, TrafficContraband.staticSetContrabandKey(siteRequest_, o)));
	}

	public Long solrContrabandKey() {
		return TrafficContraband.staticSolrContrabandKey(siteRequest_, contrabandKey);
	}

	public String strContrabandKey() {
		return contrabandKey == null ? "" : contrabandKey.toString();
	}

	public Long sqlContrabandKey() {
		return contrabandKey;
	}

	public String jsonContrabandKey() {
		return contrabandKey == null ? "" : contrabandKey.toString();
	}

	//////////////
	// searchId //
	//////////////

	/**	 The entity searchId
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String searchId;
	@JsonIgnore
	public Wrap<String> searchIdWrap = new Wrap<String>().var("searchId").o(searchId);

	/**	<br/> The entity searchId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchId">Find the entity searchId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _searchId(Wrap<String> w);

	public String getSearchId() {
		return searchId;
	}
	public void setSearchId(String o) {
		this.searchId = TrafficContraband.staticSetSearchId(siteRequest_, o);
		this.searchIdWrap.alreadyInitialized = true;
	}
	public static String staticSetSearchId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficContraband searchIdInit() {
		if(!searchIdWrap.alreadyInitialized) {
			_searchId(searchIdWrap);
			if(searchId == null)
				setSearchId(searchIdWrap.o);
			searchIdWrap.o(null);
		}
		searchIdWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static String staticSolrSearchId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrSearchId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrSearchId(siteRequest_, TrafficContraband.staticSolrSearchId(siteRequest_, TrafficContraband.staticSetSearchId(siteRequest_, o)));
	}

	public String solrSearchId() {
		return TrafficContraband.staticSolrSearchId(siteRequest_, searchId);
	}

	public String strSearchId() {
		return searchId == null ? "" : searchId;
	}

	public String sqlSearchId() {
		return searchId;
	}

	public String jsonSearchId() {
		return searchId == null ? "" : searchId;
	}

	/////////////////////////
	// trafficSearchSearch //
	/////////////////////////

	/**	 The entity trafficSearchSearch
	 *	 is defined as null before being initialized. 
	 */
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected SearchList<TrafficSearch> trafficSearchSearch;
	@JsonIgnore
	public Wrap<SearchList<TrafficSearch>> trafficSearchSearchWrap = new Wrap<SearchList<TrafficSearch>>().var("trafficSearchSearch").o(trafficSearchSearch);

	/**	<br/> The entity trafficSearchSearch
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:trafficSearchSearch">Find the entity trafficSearchSearch in Solr</a>
	 * <br/>
	 * @param promise is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _trafficSearchSearch(Promise<SearchList<TrafficSearch>> promise);

	public SearchList<TrafficSearch> getTrafficSearchSearch() {
		return trafficSearchSearch;
	}

	public void setTrafficSearchSearch(SearchList<TrafficSearch> trafficSearchSearch) {
		this.trafficSearchSearch = trafficSearchSearch;
		this.trafficSearchSearchWrap.alreadyInitialized = true;
	}
	public static SearchList<TrafficSearch> staticSetTrafficSearchSearch(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected Future<SearchList<TrafficSearch>> trafficSearchSearchPromise() {
		Promise<SearchList<TrafficSearch>> promise = Promise.promise();
		if(!trafficSearchSearchWrap.alreadyInitialized) {
			Promise<SearchList<TrafficSearch>> promise2 = Promise.promise();
			_trafficSearchSearch(promise2);
			promise2.future().onSuccess(o -> {
				if(o != null && trafficSearchSearch == null) {
					o.promiseDeepForClass(siteRequest_).onSuccess(a -> {
						setTrafficSearchSearch(o);
						trafficSearchSearchWrap.alreadyInitialized(true);
						promise.complete(o);
					}).onFailure(ex -> {
						promise.fail(ex);
					});
				} else {
					trafficSearchSearchWrap.alreadyInitialized(true);
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

	////////////////////
	// trafficSearch_ //
	////////////////////

	/**	 The entity trafficSearch_
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected TrafficSearch trafficSearch_;
	@JsonIgnore
	public Wrap<TrafficSearch> trafficSearch_Wrap = new Wrap<TrafficSearch>().var("trafficSearch_").o(trafficSearch_);

	/**	<br/> The entity trafficSearch_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:trafficSearch_">Find the entity trafficSearch_ in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _trafficSearch_(Wrap<TrafficSearch> w);

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
	protected TrafficContraband trafficSearch_Init() {
		if(!trafficSearch_Wrap.alreadyInitialized) {
			_trafficSearch_(trafficSearch_Wrap);
			if(trafficSearch_ == null)
				setTrafficSearch_(trafficSearch_Wrap.o);
			trafficSearch_Wrap.o(null);
		}
		trafficSearch_Wrap.alreadyInitialized(true);
		return (TrafficContraband)this;
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stateAbbreviation">Find the entity stateAbbreviation in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stateAbbreviation(Wrap<String> w);

	public String getStateAbbreviation() {
		return stateAbbreviation;
	}
	public void setStateAbbreviation(String o) {
		this.stateAbbreviation = TrafficContraband.staticSetStateAbbreviation(siteRequest_, o);
		this.stateAbbreviationWrap.alreadyInitialized = true;
	}
	public static String staticSetStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficContraband stateAbbreviationInit() {
		if(!stateAbbreviationWrap.alreadyInitialized) {
			_stateAbbreviation(stateAbbreviationWrap);
			if(stateAbbreviation == null)
				setStateAbbreviation(stateAbbreviationWrap.o);
			stateAbbreviationWrap.o(null);
		}
		stateAbbreviationWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static String staticSolrStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrStateAbbreviation(siteRequest_, TrafficContraband.staticSolrStateAbbreviation(siteRequest_, TrafficContraband.staticSetStateAbbreviation(siteRequest_, o)));
	}

	public String solrStateAbbreviation() {
		return TrafficContraband.staticSolrStateAbbreviation(siteRequest_, stateAbbreviation);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:agencyTitle">Find the entity agencyTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _agencyTitle(Wrap<String> w);

	public String getAgencyTitle() {
		return agencyTitle;
	}
	public void setAgencyTitle(String o) {
		this.agencyTitle = TrafficContraband.staticSetAgencyTitle(siteRequest_, o);
		this.agencyTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficContraband agencyTitleInit() {
		if(!agencyTitleWrap.alreadyInitialized) {
			_agencyTitle(agencyTitleWrap);
			if(agencyTitle == null)
				setAgencyTitle(agencyTitleWrap.o);
			agencyTitleWrap.o(null);
		}
		agencyTitleWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static String staticSolrAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrAgencyTitle(siteRequest_, TrafficContraband.staticSolrAgencyTitle(siteRequest_, TrafficContraband.staticSetAgencyTitle(siteRequest_, o)));
	}

	public String solrAgencyTitle() {
		return TrafficContraband.staticSolrAgencyTitle(siteRequest_, agencyTitle);
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

	//////////////////
	// stopDateTime //
	//////////////////

	/**	 The entity stopDateTime
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonDeserialize(using = ZonedDateTimeDeserializer.class)
	@JsonSerialize(using = ZonedDateTimeSerializer.class)
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'['VV']'")
	@JsonInclude(Include.NON_NULL)
	protected ZonedDateTime stopDateTime;
	@JsonIgnore
	public Wrap<ZonedDateTime> stopDateTimeWrap = new Wrap<ZonedDateTime>().var("stopDateTime").o(stopDateTime);

	/**	<br/> The entity stopDateTime
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopDateTime">Find the entity stopDateTime in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopDateTime(Wrap<ZonedDateTime> w);

	public ZonedDateTime getStopDateTime() {
		return stopDateTime;
	}

	public void setStopDateTime(ZonedDateTime stopDateTime) {
		this.stopDateTime = stopDateTime;
		this.stopDateTimeWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setStopDateTime(Instant o) {
		this.stopDateTime = o == null ? null : ZonedDateTime.from(o).truncatedTo(ChronoUnit.MILLIS);
		this.stopDateTimeWrap.alreadyInitialized = true;
	}
	/** Example: 2011-12-03T10:15:30+01:00 **/
	@JsonIgnore
	public void setStopDateTime(String o) {
		this.stopDateTime = TrafficContraband.staticSetStopDateTime(siteRequest_, o);
		this.stopDateTimeWrap.alreadyInitialized = true;
	}
	public static ZonedDateTime staticSetStopDateTime(SiteRequestEnUS siteRequest_, String o) {
		if(StringUtils.endsWith(o, "Z"))
			return o == null ? null : Instant.parse(o).atZone(ZoneId.of(siteRequest_.getConfig().getString(ConfigKeys.SITE_ZONE))).truncatedTo(ChronoUnit.MILLIS);
		else
			return o == null ? null : ZonedDateTime.parse(o, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'['VV']'")).truncatedTo(ChronoUnit.MILLIS);
	}
	@JsonIgnore
	public void setStopDateTime(Date o) {
		this.stopDateTime = o == null ? null : ZonedDateTime.ofInstant(o.toInstant(), ZoneId.of(siteRequest_.getConfig().getString(ConfigKeys.SITE_ZONE))).truncatedTo(ChronoUnit.MILLIS);
		this.stopDateTimeWrap.alreadyInitialized = true;
	}
	protected TrafficContraband stopDateTimeInit() {
		if(!stopDateTimeWrap.alreadyInitialized) {
			_stopDateTime(stopDateTimeWrap);
			if(stopDateTime == null)
				setStopDateTime(stopDateTimeWrap.o);
			stopDateTimeWrap.o(null);
		}
		stopDateTimeWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Date staticSolrStopDateTime(SiteRequestEnUS siteRequest_, ZonedDateTime o) {
		return o == null ? null : Date.from(o.toInstant());
	}

	public static String staticSolrStrStopDateTime(SiteRequestEnUS siteRequest_, Date o) {
		return "\"" + DateTimeFormatter.ISO_DATE_TIME.format(o.toInstant().atOffset(ZoneOffset.UTC)) + "\"";
	}

	public static String staticSolrFqStopDateTime(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrStopDateTime(siteRequest_, TrafficContraband.staticSolrStopDateTime(siteRequest_, TrafficContraband.staticSetStopDateTime(siteRequest_, o)));
	}

	public Date solrStopDateTime() {
		return TrafficContraband.staticSolrStopDateTime(siteRequest_, stopDateTime);
	}

	public String strStopDateTime() {
		return stopDateTime == null ? "" : stopDateTime.format(DateTimeFormatter.ofPattern("EEE d MMM yyyy H:mm:ss a zz", Locale.forLanguageTag("en-US")));
	}

	public OffsetDateTime sqlStopDateTime() {
		return stopDateTime == null ? null : stopDateTime.toOffsetDateTime();
	}

	public String jsonStopDateTime() {
		return stopDateTime == null ? "" : stopDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
	}

	////////////////////
	// stopPurposeNum //
	////////////////////

	/**	 The entity stopPurposeNum
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer stopPurposeNum;
	@JsonIgnore
	public Wrap<Integer> stopPurposeNumWrap = new Wrap<Integer>().var("stopPurposeNum").o(stopPurposeNum);

	/**	<br/> The entity stopPurposeNum
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopPurposeNum">Find the entity stopPurposeNum in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopPurposeNum(Wrap<Integer> w);

	public Integer getStopPurposeNum() {
		return stopPurposeNum;
	}

	public void setStopPurposeNum(Integer stopPurposeNum) {
		this.stopPurposeNum = stopPurposeNum;
		this.stopPurposeNumWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setStopPurposeNum(String o) {
		this.stopPurposeNum = TrafficContraband.staticSetStopPurposeNum(siteRequest_, o);
		this.stopPurposeNumWrap.alreadyInitialized = true;
	}
	public static Integer staticSetStopPurposeNum(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected TrafficContraband stopPurposeNumInit() {
		if(!stopPurposeNumWrap.alreadyInitialized) {
			_stopPurposeNum(stopPurposeNumWrap);
			if(stopPurposeNum == null)
				setStopPurposeNum(stopPurposeNumWrap.o);
			stopPurposeNumWrap.o(null);
		}
		stopPurposeNumWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Integer staticSolrStopPurposeNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrStopPurposeNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopPurposeNum(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrStopPurposeNum(siteRequest_, TrafficContraband.staticSolrStopPurposeNum(siteRequest_, TrafficContraband.staticSetStopPurposeNum(siteRequest_, o)));
	}

	public Integer solrStopPurposeNum() {
		return TrafficContraband.staticSolrStopPurposeNum(siteRequest_, stopPurposeNum);
	}

	public String strStopPurposeNum() {
		return stopPurposeNum == null ? "" : stopPurposeNum.toString();
	}

	public Integer sqlStopPurposeNum() {
		return stopPurposeNum;
	}

	public String jsonStopPurposeNum() {
		return stopPurposeNum == null ? "" : stopPurposeNum.toString();
	}

	//////////////////////
	// stopPurposeTitle //
	//////////////////////

	/**	 The entity stopPurposeTitle
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String stopPurposeTitle;
	@JsonIgnore
	public Wrap<String> stopPurposeTitleWrap = new Wrap<String>().var("stopPurposeTitle").o(stopPurposeTitle);

	/**	<br/> The entity stopPurposeTitle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopPurposeTitle">Find the entity stopPurposeTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopPurposeTitle(Wrap<String> w);

	public String getStopPurposeTitle() {
		return stopPurposeTitle;
	}
	public void setStopPurposeTitle(String o) {
		this.stopPurposeTitle = TrafficContraband.staticSetStopPurposeTitle(siteRequest_, o);
		this.stopPurposeTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetStopPurposeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficContraband stopPurposeTitleInit() {
		if(!stopPurposeTitleWrap.alreadyInitialized) {
			_stopPurposeTitle(stopPurposeTitleWrap);
			if(stopPurposeTitle == null)
				setStopPurposeTitle(stopPurposeTitleWrap.o);
			stopPurposeTitleWrap.o(null);
		}
		stopPurposeTitleWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static String staticSolrStopPurposeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopPurposeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopPurposeTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrStopPurposeTitle(siteRequest_, TrafficContraband.staticSolrStopPurposeTitle(siteRequest_, TrafficContraband.staticSetStopPurposeTitle(siteRequest_, o)));
	}

	public String solrStopPurposeTitle() {
		return TrafficContraband.staticSolrStopPurposeTitle(siteRequest_, stopPurposeTitle);
	}

	public String strStopPurposeTitle() {
		return stopPurposeTitle == null ? "" : stopPurposeTitle;
	}

	public String sqlStopPurposeTitle() {
		return stopPurposeTitle;
	}

	public String jsonStopPurposeTitle() {
		return stopPurposeTitle == null ? "" : stopPurposeTitle;
	}

	///////////////////
	// stopActionNum //
	///////////////////

	/**	 The entity stopActionNum
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer stopActionNum;
	@JsonIgnore
	public Wrap<Integer> stopActionNumWrap = new Wrap<Integer>().var("stopActionNum").o(stopActionNum);

	/**	<br/> The entity stopActionNum
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopActionNum">Find the entity stopActionNum in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopActionNum(Wrap<Integer> w);

	public Integer getStopActionNum() {
		return stopActionNum;
	}

	public void setStopActionNum(Integer stopActionNum) {
		this.stopActionNum = stopActionNum;
		this.stopActionNumWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setStopActionNum(String o) {
		this.stopActionNum = TrafficContraband.staticSetStopActionNum(siteRequest_, o);
		this.stopActionNumWrap.alreadyInitialized = true;
	}
	public static Integer staticSetStopActionNum(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected TrafficContraband stopActionNumInit() {
		if(!stopActionNumWrap.alreadyInitialized) {
			_stopActionNum(stopActionNumWrap);
			if(stopActionNum == null)
				setStopActionNum(stopActionNumWrap.o);
			stopActionNumWrap.o(null);
		}
		stopActionNumWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Integer staticSolrStopActionNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrStopActionNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopActionNum(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrStopActionNum(siteRequest_, TrafficContraband.staticSolrStopActionNum(siteRequest_, TrafficContraband.staticSetStopActionNum(siteRequest_, o)));
	}

	public Integer solrStopActionNum() {
		return TrafficContraband.staticSolrStopActionNum(siteRequest_, stopActionNum);
	}

	public String strStopActionNum() {
		return stopActionNum == null ? "" : stopActionNum.toString();
	}

	public Integer sqlStopActionNum() {
		return stopActionNum;
	}

	public String jsonStopActionNum() {
		return stopActionNum == null ? "" : stopActionNum.toString();
	}

	/////////////////////
	// stopActionTitle //
	/////////////////////

	/**	 The entity stopActionTitle
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String stopActionTitle;
	@JsonIgnore
	public Wrap<String> stopActionTitleWrap = new Wrap<String>().var("stopActionTitle").o(stopActionTitle);

	/**	<br/> The entity stopActionTitle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopActionTitle">Find the entity stopActionTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopActionTitle(Wrap<String> w);

	public String getStopActionTitle() {
		return stopActionTitle;
	}
	public void setStopActionTitle(String o) {
		this.stopActionTitle = TrafficContraband.staticSetStopActionTitle(siteRequest_, o);
		this.stopActionTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetStopActionTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficContraband stopActionTitleInit() {
		if(!stopActionTitleWrap.alreadyInitialized) {
			_stopActionTitle(stopActionTitleWrap);
			if(stopActionTitle == null)
				setStopActionTitle(stopActionTitleWrap.o);
			stopActionTitleWrap.o(null);
		}
		stopActionTitleWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static String staticSolrStopActionTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopActionTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopActionTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrStopActionTitle(siteRequest_, TrafficContraband.staticSolrStopActionTitle(siteRequest_, TrafficContraband.staticSetStopActionTitle(siteRequest_, o)));
	}

	public String solrStopActionTitle() {
		return TrafficContraband.staticSolrStopActionTitle(siteRequest_, stopActionTitle);
	}

	public String strStopActionTitle() {
		return stopActionTitle == null ? "" : stopActionTitle;
	}

	public String sqlStopActionTitle() {
		return stopActionTitle;
	}

	public String jsonStopActionTitle() {
		return stopActionTitle == null ? "" : stopActionTitle;
	}

	//////////////////////
	// stopDriverArrest //
	//////////////////////

	/**	 The entity stopDriverArrest
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected Boolean stopDriverArrest;
	@JsonIgnore
	public Wrap<Boolean> stopDriverArrestWrap = new Wrap<Boolean>().var("stopDriverArrest").o(stopDriverArrest);

	/**	<br/> The entity stopDriverArrest
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopDriverArrest">Find the entity stopDriverArrest in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopDriverArrest(Wrap<Boolean> w);

	public Boolean getStopDriverArrest() {
		return stopDriverArrest;
	}

	public void setStopDriverArrest(Boolean stopDriverArrest) {
		this.stopDriverArrest = stopDriverArrest;
		this.stopDriverArrestWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setStopDriverArrest(String o) {
		this.stopDriverArrest = TrafficContraband.staticSetStopDriverArrest(siteRequest_, o);
		this.stopDriverArrestWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopDriverArrest(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficContraband stopDriverArrestInit() {
		if(!stopDriverArrestWrap.alreadyInitialized) {
			_stopDriverArrest(stopDriverArrestWrap);
			if(stopDriverArrest == null)
				setStopDriverArrest(stopDriverArrestWrap.o);
			stopDriverArrestWrap.o(null);
		}
		stopDriverArrestWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Boolean staticSolrStopDriverArrest(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopDriverArrest(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopDriverArrest(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrStopDriverArrest(siteRequest_, TrafficContraband.staticSolrStopDriverArrest(siteRequest_, TrafficContraband.staticSetStopDriverArrest(siteRequest_, o)));
	}

	public Boolean solrStopDriverArrest() {
		return TrafficContraband.staticSolrStopDriverArrest(siteRequest_, stopDriverArrest);
	}

	public String strStopDriverArrest() {
		return stopDriverArrest == null ? "" : stopDriverArrest.toString();
	}

	public Boolean sqlStopDriverArrest() {
		return stopDriverArrest;
	}

	public String jsonStopDriverArrest() {
		return stopDriverArrest == null ? "" : stopDriverArrest.toString();
	}

	/////////////////////////
	// stopPassengerArrest //
	/////////////////////////

	/**	 The entity stopPassengerArrest
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected Boolean stopPassengerArrest;
	@JsonIgnore
	public Wrap<Boolean> stopPassengerArrestWrap = new Wrap<Boolean>().var("stopPassengerArrest").o(stopPassengerArrest);

	/**	<br/> The entity stopPassengerArrest
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopPassengerArrest">Find the entity stopPassengerArrest in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopPassengerArrest(Wrap<Boolean> w);

	public Boolean getStopPassengerArrest() {
		return stopPassengerArrest;
	}

	public void setStopPassengerArrest(Boolean stopPassengerArrest) {
		this.stopPassengerArrest = stopPassengerArrest;
		this.stopPassengerArrestWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setStopPassengerArrest(String o) {
		this.stopPassengerArrest = TrafficContraband.staticSetStopPassengerArrest(siteRequest_, o);
		this.stopPassengerArrestWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopPassengerArrest(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficContraband stopPassengerArrestInit() {
		if(!stopPassengerArrestWrap.alreadyInitialized) {
			_stopPassengerArrest(stopPassengerArrestWrap);
			if(stopPassengerArrest == null)
				setStopPassengerArrest(stopPassengerArrestWrap.o);
			stopPassengerArrestWrap.o(null);
		}
		stopPassengerArrestWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Boolean staticSolrStopPassengerArrest(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopPassengerArrest(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopPassengerArrest(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrStopPassengerArrest(siteRequest_, TrafficContraband.staticSolrStopPassengerArrest(siteRequest_, TrafficContraband.staticSetStopPassengerArrest(siteRequest_, o)));
	}

	public Boolean solrStopPassengerArrest() {
		return TrafficContraband.staticSolrStopPassengerArrest(siteRequest_, stopPassengerArrest);
	}

	public String strStopPassengerArrest() {
		return stopPassengerArrest == null ? "" : stopPassengerArrest.toString();
	}

	public Boolean sqlStopPassengerArrest() {
		return stopPassengerArrest;
	}

	public String jsonStopPassengerArrest() {
		return stopPassengerArrest == null ? "" : stopPassengerArrest.toString();
	}

	////////////////////////
	// stopEncounterForce //
	////////////////////////

	/**	 The entity stopEncounterForce
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected Boolean stopEncounterForce;
	@JsonIgnore
	public Wrap<Boolean> stopEncounterForceWrap = new Wrap<Boolean>().var("stopEncounterForce").o(stopEncounterForce);

	/**	<br/> The entity stopEncounterForce
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopEncounterForce">Find the entity stopEncounterForce in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopEncounterForce(Wrap<Boolean> w);

	public Boolean getStopEncounterForce() {
		return stopEncounterForce;
	}

	public void setStopEncounterForce(Boolean stopEncounterForce) {
		this.stopEncounterForce = stopEncounterForce;
		this.stopEncounterForceWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setStopEncounterForce(String o) {
		this.stopEncounterForce = TrafficContraband.staticSetStopEncounterForce(siteRequest_, o);
		this.stopEncounterForceWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopEncounterForce(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficContraband stopEncounterForceInit() {
		if(!stopEncounterForceWrap.alreadyInitialized) {
			_stopEncounterForce(stopEncounterForceWrap);
			if(stopEncounterForce == null)
				setStopEncounterForce(stopEncounterForceWrap.o);
			stopEncounterForceWrap.o(null);
		}
		stopEncounterForceWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Boolean staticSolrStopEncounterForce(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopEncounterForce(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopEncounterForce(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrStopEncounterForce(siteRequest_, TrafficContraband.staticSolrStopEncounterForce(siteRequest_, TrafficContraband.staticSetStopEncounterForce(siteRequest_, o)));
	}

	public Boolean solrStopEncounterForce() {
		return TrafficContraband.staticSolrStopEncounterForce(siteRequest_, stopEncounterForce);
	}

	public String strStopEncounterForce() {
		return stopEncounterForce == null ? "" : stopEncounterForce.toString();
	}

	public Boolean sqlStopEncounterForce() {
		return stopEncounterForce;
	}

	public String jsonStopEncounterForce() {
		return stopEncounterForce == null ? "" : stopEncounterForce.toString();
	}

	/////////////////////
	// stopEngageForce //
	/////////////////////

	/**	 The entity stopEngageForce
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected Boolean stopEngageForce;
	@JsonIgnore
	public Wrap<Boolean> stopEngageForceWrap = new Wrap<Boolean>().var("stopEngageForce").o(stopEngageForce);

	/**	<br/> The entity stopEngageForce
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopEngageForce">Find the entity stopEngageForce in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopEngageForce(Wrap<Boolean> w);

	public Boolean getStopEngageForce() {
		return stopEngageForce;
	}

	public void setStopEngageForce(Boolean stopEngageForce) {
		this.stopEngageForce = stopEngageForce;
		this.stopEngageForceWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setStopEngageForce(String o) {
		this.stopEngageForce = TrafficContraband.staticSetStopEngageForce(siteRequest_, o);
		this.stopEngageForceWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopEngageForce(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficContraband stopEngageForceInit() {
		if(!stopEngageForceWrap.alreadyInitialized) {
			_stopEngageForce(stopEngageForceWrap);
			if(stopEngageForce == null)
				setStopEngageForce(stopEngageForceWrap.o);
			stopEngageForceWrap.o(null);
		}
		stopEngageForceWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Boolean staticSolrStopEngageForce(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopEngageForce(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopEngageForce(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrStopEngageForce(siteRequest_, TrafficContraband.staticSolrStopEngageForce(siteRequest_, TrafficContraband.staticSetStopEngageForce(siteRequest_, o)));
	}

	public Boolean solrStopEngageForce() {
		return TrafficContraband.staticSolrStopEngageForce(siteRequest_, stopEngageForce);
	}

	public String strStopEngageForce() {
		return stopEngageForce == null ? "" : stopEngageForce.toString();
	}

	public Boolean sqlStopEngageForce() {
		return stopEngageForce;
	}

	public String jsonStopEngageForce() {
		return stopEngageForce == null ? "" : stopEngageForce.toString();
	}

	///////////////////////
	// stopOfficerInjury //
	///////////////////////

	/**	 The entity stopOfficerInjury
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected Boolean stopOfficerInjury;
	@JsonIgnore
	public Wrap<Boolean> stopOfficerInjuryWrap = new Wrap<Boolean>().var("stopOfficerInjury").o(stopOfficerInjury);

	/**	<br/> The entity stopOfficerInjury
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopOfficerInjury">Find the entity stopOfficerInjury in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopOfficerInjury(Wrap<Boolean> w);

	public Boolean getStopOfficerInjury() {
		return stopOfficerInjury;
	}

	public void setStopOfficerInjury(Boolean stopOfficerInjury) {
		this.stopOfficerInjury = stopOfficerInjury;
		this.stopOfficerInjuryWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setStopOfficerInjury(String o) {
		this.stopOfficerInjury = TrafficContraband.staticSetStopOfficerInjury(siteRequest_, o);
		this.stopOfficerInjuryWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopOfficerInjury(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficContraband stopOfficerInjuryInit() {
		if(!stopOfficerInjuryWrap.alreadyInitialized) {
			_stopOfficerInjury(stopOfficerInjuryWrap);
			if(stopOfficerInjury == null)
				setStopOfficerInjury(stopOfficerInjuryWrap.o);
			stopOfficerInjuryWrap.o(null);
		}
		stopOfficerInjuryWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Boolean staticSolrStopOfficerInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopOfficerInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopOfficerInjury(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrStopOfficerInjury(siteRequest_, TrafficContraband.staticSolrStopOfficerInjury(siteRequest_, TrafficContraband.staticSetStopOfficerInjury(siteRequest_, o)));
	}

	public Boolean solrStopOfficerInjury() {
		return TrafficContraband.staticSolrStopOfficerInjury(siteRequest_, stopOfficerInjury);
	}

	public String strStopOfficerInjury() {
		return stopOfficerInjury == null ? "" : stopOfficerInjury.toString();
	}

	public Boolean sqlStopOfficerInjury() {
		return stopOfficerInjury;
	}

	public String jsonStopOfficerInjury() {
		return stopOfficerInjury == null ? "" : stopOfficerInjury.toString();
	}

	//////////////////////
	// stopDriverInjury //
	//////////////////////

	/**	 The entity stopDriverInjury
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected Boolean stopDriverInjury;
	@JsonIgnore
	public Wrap<Boolean> stopDriverInjuryWrap = new Wrap<Boolean>().var("stopDriverInjury").o(stopDriverInjury);

	/**	<br/> The entity stopDriverInjury
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopDriverInjury">Find the entity stopDriverInjury in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopDriverInjury(Wrap<Boolean> w);

	public Boolean getStopDriverInjury() {
		return stopDriverInjury;
	}

	public void setStopDriverInjury(Boolean stopDriverInjury) {
		this.stopDriverInjury = stopDriverInjury;
		this.stopDriverInjuryWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setStopDriverInjury(String o) {
		this.stopDriverInjury = TrafficContraband.staticSetStopDriverInjury(siteRequest_, o);
		this.stopDriverInjuryWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopDriverInjury(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficContraband stopDriverInjuryInit() {
		if(!stopDriverInjuryWrap.alreadyInitialized) {
			_stopDriverInjury(stopDriverInjuryWrap);
			if(stopDriverInjury == null)
				setStopDriverInjury(stopDriverInjuryWrap.o);
			stopDriverInjuryWrap.o(null);
		}
		stopDriverInjuryWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Boolean staticSolrStopDriverInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopDriverInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopDriverInjury(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrStopDriverInjury(siteRequest_, TrafficContraband.staticSolrStopDriverInjury(siteRequest_, TrafficContraband.staticSetStopDriverInjury(siteRequest_, o)));
	}

	public Boolean solrStopDriverInjury() {
		return TrafficContraband.staticSolrStopDriverInjury(siteRequest_, stopDriverInjury);
	}

	public String strStopDriverInjury() {
		return stopDriverInjury == null ? "" : stopDriverInjury.toString();
	}

	public Boolean sqlStopDriverInjury() {
		return stopDriverInjury;
	}

	public String jsonStopDriverInjury() {
		return stopDriverInjury == null ? "" : stopDriverInjury.toString();
	}

	/////////////////////////
	// stopPassengerInjury //
	/////////////////////////

	/**	 The entity stopPassengerInjury
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected Boolean stopPassengerInjury;
	@JsonIgnore
	public Wrap<Boolean> stopPassengerInjuryWrap = new Wrap<Boolean>().var("stopPassengerInjury").o(stopPassengerInjury);

	/**	<br/> The entity stopPassengerInjury
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopPassengerInjury">Find the entity stopPassengerInjury in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopPassengerInjury(Wrap<Boolean> w);

	public Boolean getStopPassengerInjury() {
		return stopPassengerInjury;
	}

	public void setStopPassengerInjury(Boolean stopPassengerInjury) {
		this.stopPassengerInjury = stopPassengerInjury;
		this.stopPassengerInjuryWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setStopPassengerInjury(String o) {
		this.stopPassengerInjury = TrafficContraband.staticSetStopPassengerInjury(siteRequest_, o);
		this.stopPassengerInjuryWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopPassengerInjury(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficContraband stopPassengerInjuryInit() {
		if(!stopPassengerInjuryWrap.alreadyInitialized) {
			_stopPassengerInjury(stopPassengerInjuryWrap);
			if(stopPassengerInjury == null)
				setStopPassengerInjury(stopPassengerInjuryWrap.o);
			stopPassengerInjuryWrap.o(null);
		}
		stopPassengerInjuryWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Boolean staticSolrStopPassengerInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopPassengerInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopPassengerInjury(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrStopPassengerInjury(siteRequest_, TrafficContraband.staticSolrStopPassengerInjury(siteRequest_, TrafficContraband.staticSetStopPassengerInjury(siteRequest_, o)));
	}

	public Boolean solrStopPassengerInjury() {
		return TrafficContraband.staticSolrStopPassengerInjury(siteRequest_, stopPassengerInjury);
	}

	public String strStopPassengerInjury() {
		return stopPassengerInjury == null ? "" : stopPassengerInjury.toString();
	}

	public Boolean sqlStopPassengerInjury() {
		return stopPassengerInjury;
	}

	public String jsonStopPassengerInjury() {
		return stopPassengerInjury == null ? "" : stopPassengerInjury.toString();
	}

	///////////////////
	// stopOfficerId //
	///////////////////

	/**	 The entity stopOfficerId
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String stopOfficerId;
	@JsonIgnore
	public Wrap<String> stopOfficerIdWrap = new Wrap<String>().var("stopOfficerId").o(stopOfficerId);

	/**	<br/> The entity stopOfficerId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopOfficerId">Find the entity stopOfficerId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopOfficerId(Wrap<String> w);

	public String getStopOfficerId() {
		return stopOfficerId;
	}
	public void setStopOfficerId(String o) {
		this.stopOfficerId = TrafficContraband.staticSetStopOfficerId(siteRequest_, o);
		this.stopOfficerIdWrap.alreadyInitialized = true;
	}
	public static String staticSetStopOfficerId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficContraband stopOfficerIdInit() {
		if(!stopOfficerIdWrap.alreadyInitialized) {
			_stopOfficerId(stopOfficerIdWrap);
			if(stopOfficerId == null)
				setStopOfficerId(stopOfficerIdWrap.o);
			stopOfficerIdWrap.o(null);
		}
		stopOfficerIdWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static String staticSolrStopOfficerId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopOfficerId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopOfficerId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrStopOfficerId(siteRequest_, TrafficContraband.staticSolrStopOfficerId(siteRequest_, TrafficContraband.staticSetStopOfficerId(siteRequest_, o)));
	}

	public String solrStopOfficerId() {
		return TrafficContraband.staticSolrStopOfficerId(siteRequest_, stopOfficerId);
	}

	public String strStopOfficerId() {
		return stopOfficerId == null ? "" : stopOfficerId;
	}

	public String sqlStopOfficerId() {
		return stopOfficerId;
	}

	public String jsonStopOfficerId() {
		return stopOfficerId == null ? "" : stopOfficerId;
	}

	////////////////////
	// stopLocationId //
	////////////////////

	/**	 The entity stopLocationId
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String stopLocationId;
	@JsonIgnore
	public Wrap<String> stopLocationIdWrap = new Wrap<String>().var("stopLocationId").o(stopLocationId);

	/**	<br/> The entity stopLocationId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopLocationId">Find the entity stopLocationId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopLocationId(Wrap<String> w);

	public String getStopLocationId() {
		return stopLocationId;
	}
	public void setStopLocationId(String o) {
		this.stopLocationId = TrafficContraband.staticSetStopLocationId(siteRequest_, o);
		this.stopLocationIdWrap.alreadyInitialized = true;
	}
	public static String staticSetStopLocationId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficContraband stopLocationIdInit() {
		if(!stopLocationIdWrap.alreadyInitialized) {
			_stopLocationId(stopLocationIdWrap);
			if(stopLocationId == null)
				setStopLocationId(stopLocationIdWrap.o);
			stopLocationIdWrap.o(null);
		}
		stopLocationIdWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static String staticSolrStopLocationId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopLocationId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopLocationId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrStopLocationId(siteRequest_, TrafficContraband.staticSolrStopLocationId(siteRequest_, TrafficContraband.staticSetStopLocationId(siteRequest_, o)));
	}

	public String solrStopLocationId() {
		return TrafficContraband.staticSolrStopLocationId(siteRequest_, stopLocationId);
	}

	public String strStopLocationId() {
		return stopLocationId == null ? "" : stopLocationId;
	}

	public String sqlStopLocationId() {
		return stopLocationId;
	}

	public String jsonStopLocationId() {
		return stopLocationId == null ? "" : stopLocationId;
	}

	////////////////
	// stopCityId //
	////////////////

	/**	 The entity stopCityId
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String stopCityId;
	@JsonIgnore
	public Wrap<String> stopCityIdWrap = new Wrap<String>().var("stopCityId").o(stopCityId);

	/**	<br/> The entity stopCityId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopCityId">Find the entity stopCityId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopCityId(Wrap<String> w);

	public String getStopCityId() {
		return stopCityId;
	}
	public void setStopCityId(String o) {
		this.stopCityId = TrafficContraband.staticSetStopCityId(siteRequest_, o);
		this.stopCityIdWrap.alreadyInitialized = true;
	}
	public static String staticSetStopCityId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficContraband stopCityIdInit() {
		if(!stopCityIdWrap.alreadyInitialized) {
			_stopCityId(stopCityIdWrap);
			if(stopCityId == null)
				setStopCityId(stopCityIdWrap.o);
			stopCityIdWrap.o(null);
		}
		stopCityIdWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static String staticSolrStopCityId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopCityId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopCityId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrStopCityId(siteRequest_, TrafficContraband.staticSolrStopCityId(siteRequest_, TrafficContraband.staticSetStopCityId(siteRequest_, o)));
	}

	public String solrStopCityId() {
		return TrafficContraband.staticSolrStopCityId(siteRequest_, stopCityId);
	}

	public String strStopCityId() {
		return stopCityId == null ? "" : stopCityId;
	}

	public String sqlStopCityId() {
		return stopCityId;
	}

	public String jsonStopCityId() {
		return stopCityId == null ? "" : stopCityId;
	}

	///////////////
	// personAge //
	///////////////

	/**	 The entity personAge
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer personAge;
	@JsonIgnore
	public Wrap<Integer> personAgeWrap = new Wrap<Integer>().var("personAge").o(personAge);

	/**	<br/> The entity personAge
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personAge">Find the entity personAge in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personAge(Wrap<Integer> w);

	public Integer getPersonAge() {
		return personAge;
	}

	public void setPersonAge(Integer personAge) {
		this.personAge = personAge;
		this.personAgeWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setPersonAge(String o) {
		this.personAge = TrafficContraband.staticSetPersonAge(siteRequest_, o);
		this.personAgeWrap.alreadyInitialized = true;
	}
	public static Integer staticSetPersonAge(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected TrafficContraband personAgeInit() {
		if(!personAgeWrap.alreadyInitialized) {
			_personAge(personAgeWrap);
			if(personAge == null)
				setPersonAge(personAgeWrap.o);
			personAgeWrap.o(null);
		}
		personAgeWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Integer staticSolrPersonAge(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrPersonAge(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonAge(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrPersonAge(siteRequest_, TrafficContraband.staticSolrPersonAge(siteRequest_, TrafficContraband.staticSetPersonAge(siteRequest_, o)));
	}

	public Integer solrPersonAge() {
		return TrafficContraband.staticSolrPersonAge(siteRequest_, personAge);
	}

	public String strPersonAge() {
		return personAge == null ? "" : personAge.toString();
	}

	public Integer sqlPersonAge() {
		return personAge;
	}

	public String jsonPersonAge() {
		return personAge == null ? "" : personAge.toString();
	}

	//////////////////
	// personTypeId //
	//////////////////

	/**	 The entity personTypeId
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String personTypeId;
	@JsonIgnore
	public Wrap<String> personTypeIdWrap = new Wrap<String>().var("personTypeId").o(personTypeId);

	/**	<br/> The entity personTypeId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personTypeId">Find the entity personTypeId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personTypeId(Wrap<String> w);

	public String getPersonTypeId() {
		return personTypeId;
	}
	public void setPersonTypeId(String o) {
		this.personTypeId = TrafficContraband.staticSetPersonTypeId(siteRequest_, o);
		this.personTypeIdWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonTypeId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficContraband personTypeIdInit() {
		if(!personTypeIdWrap.alreadyInitialized) {
			_personTypeId(personTypeIdWrap);
			if(personTypeId == null)
				setPersonTypeId(personTypeIdWrap.o);
			personTypeIdWrap.o(null);
		}
		personTypeIdWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static String staticSolrPersonTypeId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonTypeId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonTypeId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrPersonTypeId(siteRequest_, TrafficContraband.staticSolrPersonTypeId(siteRequest_, TrafficContraband.staticSetPersonTypeId(siteRequest_, o)));
	}

	public String solrPersonTypeId() {
		return TrafficContraband.staticSolrPersonTypeId(siteRequest_, personTypeId);
	}

	public String strPersonTypeId() {
		return personTypeId == null ? "" : personTypeId;
	}

	public String sqlPersonTypeId() {
		return personTypeId;
	}

	public String jsonPersonTypeId() {
		return personTypeId == null ? "" : personTypeId;
	}

	/////////////////////
	// personTypeTitle //
	/////////////////////

	/**	 The entity personTypeTitle
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String personTypeTitle;
	@JsonIgnore
	public Wrap<String> personTypeTitleWrap = new Wrap<String>().var("personTypeTitle").o(personTypeTitle);

	/**	<br/> The entity personTypeTitle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personTypeTitle">Find the entity personTypeTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personTypeTitle(Wrap<String> w);

	public String getPersonTypeTitle() {
		return personTypeTitle;
	}
	public void setPersonTypeTitle(String o) {
		this.personTypeTitle = TrafficContraband.staticSetPersonTypeTitle(siteRequest_, o);
		this.personTypeTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficContraband personTypeTitleInit() {
		if(!personTypeTitleWrap.alreadyInitialized) {
			_personTypeTitle(personTypeTitleWrap);
			if(personTypeTitle == null)
				setPersonTypeTitle(personTypeTitleWrap.o);
			personTypeTitleWrap.o(null);
		}
		personTypeTitleWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static String staticSolrPersonTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrPersonTypeTitle(siteRequest_, TrafficContraband.staticSolrPersonTypeTitle(siteRequest_, TrafficContraband.staticSetPersonTypeTitle(siteRequest_, o)));
	}

	public String solrPersonTypeTitle() {
		return TrafficContraband.staticSolrPersonTypeTitle(siteRequest_, personTypeTitle);
	}

	public String strPersonTypeTitle() {
		return personTypeTitle == null ? "" : personTypeTitle;
	}

	public String sqlPersonTypeTitle() {
		return personTypeTitle;
	}

	public String jsonPersonTypeTitle() {
		return personTypeTitle == null ? "" : personTypeTitle;
	}

	//////////////////////
	// personTypeDriver //
	//////////////////////

	/**	 The entity personTypeDriver
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected Boolean personTypeDriver;
	@JsonIgnore
	public Wrap<Boolean> personTypeDriverWrap = new Wrap<Boolean>().var("personTypeDriver").o(personTypeDriver);

	/**	<br/> The entity personTypeDriver
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personTypeDriver">Find the entity personTypeDriver in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personTypeDriver(Wrap<Boolean> w);

	public Boolean getPersonTypeDriver() {
		return personTypeDriver;
	}

	public void setPersonTypeDriver(Boolean personTypeDriver) {
		this.personTypeDriver = personTypeDriver;
		this.personTypeDriverWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setPersonTypeDriver(String o) {
		this.personTypeDriver = TrafficContraband.staticSetPersonTypeDriver(siteRequest_, o);
		this.personTypeDriverWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetPersonTypeDriver(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficContraband personTypeDriverInit() {
		if(!personTypeDriverWrap.alreadyInitialized) {
			_personTypeDriver(personTypeDriverWrap);
			if(personTypeDriver == null)
				setPersonTypeDriver(personTypeDriverWrap.o);
			personTypeDriverWrap.o(null);
		}
		personTypeDriverWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Boolean staticSolrPersonTypeDriver(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrPersonTypeDriver(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonTypeDriver(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrPersonTypeDriver(siteRequest_, TrafficContraband.staticSolrPersonTypeDriver(siteRequest_, TrafficContraband.staticSetPersonTypeDriver(siteRequest_, o)));
	}

	public Boolean solrPersonTypeDriver() {
		return TrafficContraband.staticSolrPersonTypeDriver(siteRequest_, personTypeDriver);
	}

	public String strPersonTypeDriver() {
		return personTypeDriver == null ? "" : personTypeDriver.toString();
	}

	public Boolean sqlPersonTypeDriver() {
		return personTypeDriver;
	}

	public String jsonPersonTypeDriver() {
		return personTypeDriver == null ? "" : personTypeDriver.toString();
	}

	/////////////////////////
	// personTypePassenger //
	/////////////////////////

	/**	 The entity personTypePassenger
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected Boolean personTypePassenger;
	@JsonIgnore
	public Wrap<Boolean> personTypePassengerWrap = new Wrap<Boolean>().var("personTypePassenger").o(personTypePassenger);

	/**	<br/> The entity personTypePassenger
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personTypePassenger">Find the entity personTypePassenger in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personTypePassenger(Wrap<Boolean> w);

	public Boolean getPersonTypePassenger() {
		return personTypePassenger;
	}

	public void setPersonTypePassenger(Boolean personTypePassenger) {
		this.personTypePassenger = personTypePassenger;
		this.personTypePassengerWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setPersonTypePassenger(String o) {
		this.personTypePassenger = TrafficContraband.staticSetPersonTypePassenger(siteRequest_, o);
		this.personTypePassengerWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetPersonTypePassenger(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficContraband personTypePassengerInit() {
		if(!personTypePassengerWrap.alreadyInitialized) {
			_personTypePassenger(personTypePassengerWrap);
			if(personTypePassenger == null)
				setPersonTypePassenger(personTypePassengerWrap.o);
			personTypePassengerWrap.o(null);
		}
		personTypePassengerWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Boolean staticSolrPersonTypePassenger(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrPersonTypePassenger(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonTypePassenger(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrPersonTypePassenger(siteRequest_, TrafficContraband.staticSolrPersonTypePassenger(siteRequest_, TrafficContraband.staticSetPersonTypePassenger(siteRequest_, o)));
	}

	public Boolean solrPersonTypePassenger() {
		return TrafficContraband.staticSolrPersonTypePassenger(siteRequest_, personTypePassenger);
	}

	public String strPersonTypePassenger() {
		return personTypePassenger == null ? "" : personTypePassenger.toString();
	}

	public Boolean sqlPersonTypePassenger() {
		return personTypePassenger;
	}

	public String jsonPersonTypePassenger() {
		return personTypePassenger == null ? "" : personTypePassenger.toString();
	}

	////////////////////
	// personGenderId //
	////////////////////

	/**	 The entity personGenderId
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String personGenderId;
	@JsonIgnore
	public Wrap<String> personGenderIdWrap = new Wrap<String>().var("personGenderId").o(personGenderId);

	/**	<br/> The entity personGenderId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personGenderId">Find the entity personGenderId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personGenderId(Wrap<String> w);

	public String getPersonGenderId() {
		return personGenderId;
	}
	public void setPersonGenderId(String o) {
		this.personGenderId = TrafficContraband.staticSetPersonGenderId(siteRequest_, o);
		this.personGenderIdWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonGenderId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficContraband personGenderIdInit() {
		if(!personGenderIdWrap.alreadyInitialized) {
			_personGenderId(personGenderIdWrap);
			if(personGenderId == null)
				setPersonGenderId(personGenderIdWrap.o);
			personGenderIdWrap.o(null);
		}
		personGenderIdWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static String staticSolrPersonGenderId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonGenderId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonGenderId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrPersonGenderId(siteRequest_, TrafficContraband.staticSolrPersonGenderId(siteRequest_, TrafficContraband.staticSetPersonGenderId(siteRequest_, o)));
	}

	public String solrPersonGenderId() {
		return TrafficContraband.staticSolrPersonGenderId(siteRequest_, personGenderId);
	}

	public String strPersonGenderId() {
		return personGenderId == null ? "" : personGenderId;
	}

	public String sqlPersonGenderId() {
		return personGenderId;
	}

	public String jsonPersonGenderId() {
		return personGenderId == null ? "" : personGenderId;
	}

	///////////////////////
	// personGenderTitle //
	///////////////////////

	/**	 The entity personGenderTitle
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String personGenderTitle;
	@JsonIgnore
	public Wrap<String> personGenderTitleWrap = new Wrap<String>().var("personGenderTitle").o(personGenderTitle);

	/**	<br/> The entity personGenderTitle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personGenderTitle">Find the entity personGenderTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personGenderTitle(Wrap<String> w);

	public String getPersonGenderTitle() {
		return personGenderTitle;
	}
	public void setPersonGenderTitle(String o) {
		this.personGenderTitle = TrafficContraband.staticSetPersonGenderTitle(siteRequest_, o);
		this.personGenderTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonGenderTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficContraband personGenderTitleInit() {
		if(!personGenderTitleWrap.alreadyInitialized) {
			_personGenderTitle(personGenderTitleWrap);
			if(personGenderTitle == null)
				setPersonGenderTitle(personGenderTitleWrap.o);
			personGenderTitleWrap.o(null);
		}
		personGenderTitleWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static String staticSolrPersonGenderTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonGenderTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonGenderTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrPersonGenderTitle(siteRequest_, TrafficContraband.staticSolrPersonGenderTitle(siteRequest_, TrafficContraband.staticSetPersonGenderTitle(siteRequest_, o)));
	}

	public String solrPersonGenderTitle() {
		return TrafficContraband.staticSolrPersonGenderTitle(siteRequest_, personGenderTitle);
	}

	public String strPersonGenderTitle() {
		return personGenderTitle == null ? "" : personGenderTitle;
	}

	public String sqlPersonGenderTitle() {
		return personGenderTitle;
	}

	public String jsonPersonGenderTitle() {
		return personGenderTitle == null ? "" : personGenderTitle;
	}

	////////////////////////
	// personGenderFemale //
	////////////////////////

	/**	 The entity personGenderFemale
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected Boolean personGenderFemale;
	@JsonIgnore
	public Wrap<Boolean> personGenderFemaleWrap = new Wrap<Boolean>().var("personGenderFemale").o(personGenderFemale);

	/**	<br/> The entity personGenderFemale
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personGenderFemale">Find the entity personGenderFemale in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personGenderFemale(Wrap<Boolean> w);

	public Boolean getPersonGenderFemale() {
		return personGenderFemale;
	}

	public void setPersonGenderFemale(Boolean personGenderFemale) {
		this.personGenderFemale = personGenderFemale;
		this.personGenderFemaleWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setPersonGenderFemale(String o) {
		this.personGenderFemale = TrafficContraband.staticSetPersonGenderFemale(siteRequest_, o);
		this.personGenderFemaleWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetPersonGenderFemale(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficContraband personGenderFemaleInit() {
		if(!personGenderFemaleWrap.alreadyInitialized) {
			_personGenderFemale(personGenderFemaleWrap);
			if(personGenderFemale == null)
				setPersonGenderFemale(personGenderFemaleWrap.o);
			personGenderFemaleWrap.o(null);
		}
		personGenderFemaleWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Boolean staticSolrPersonGenderFemale(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrPersonGenderFemale(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonGenderFemale(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrPersonGenderFemale(siteRequest_, TrafficContraband.staticSolrPersonGenderFemale(siteRequest_, TrafficContraband.staticSetPersonGenderFemale(siteRequest_, o)));
	}

	public Boolean solrPersonGenderFemale() {
		return TrafficContraband.staticSolrPersonGenderFemale(siteRequest_, personGenderFemale);
	}

	public String strPersonGenderFemale() {
		return personGenderFemale == null ? "" : personGenderFemale.toString();
	}

	public Boolean sqlPersonGenderFemale() {
		return personGenderFemale;
	}

	public String jsonPersonGenderFemale() {
		return personGenderFemale == null ? "" : personGenderFemale.toString();
	}

	//////////////////////
	// personGenderMale //
	//////////////////////

	/**	 The entity personGenderMale
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected Boolean personGenderMale;
	@JsonIgnore
	public Wrap<Boolean> personGenderMaleWrap = new Wrap<Boolean>().var("personGenderMale").o(personGenderMale);

	/**	<br/> The entity personGenderMale
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personGenderMale">Find the entity personGenderMale in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personGenderMale(Wrap<Boolean> w);

	public Boolean getPersonGenderMale() {
		return personGenderMale;
	}

	public void setPersonGenderMale(Boolean personGenderMale) {
		this.personGenderMale = personGenderMale;
		this.personGenderMaleWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setPersonGenderMale(String o) {
		this.personGenderMale = TrafficContraband.staticSetPersonGenderMale(siteRequest_, o);
		this.personGenderMaleWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetPersonGenderMale(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficContraband personGenderMaleInit() {
		if(!personGenderMaleWrap.alreadyInitialized) {
			_personGenderMale(personGenderMaleWrap);
			if(personGenderMale == null)
				setPersonGenderMale(personGenderMaleWrap.o);
			personGenderMaleWrap.o(null);
		}
		personGenderMaleWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Boolean staticSolrPersonGenderMale(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrPersonGenderMale(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonGenderMale(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrPersonGenderMale(siteRequest_, TrafficContraband.staticSolrPersonGenderMale(siteRequest_, TrafficContraband.staticSetPersonGenderMale(siteRequest_, o)));
	}

	public Boolean solrPersonGenderMale() {
		return TrafficContraband.staticSolrPersonGenderMale(siteRequest_, personGenderMale);
	}

	public String strPersonGenderMale() {
		return personGenderMale == null ? "" : personGenderMale.toString();
	}

	public Boolean sqlPersonGenderMale() {
		return personGenderMale;
	}

	public String jsonPersonGenderMale() {
		return personGenderMale == null ? "" : personGenderMale.toString();
	}

	///////////////////////
	// personEthnicityId //
	///////////////////////

	/**	 The entity personEthnicityId
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String personEthnicityId;
	@JsonIgnore
	public Wrap<String> personEthnicityIdWrap = new Wrap<String>().var("personEthnicityId").o(personEthnicityId);

	/**	<br/> The entity personEthnicityId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personEthnicityId">Find the entity personEthnicityId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personEthnicityId(Wrap<String> w);

	public String getPersonEthnicityId() {
		return personEthnicityId;
	}
	public void setPersonEthnicityId(String o) {
		this.personEthnicityId = TrafficContraband.staticSetPersonEthnicityId(siteRequest_, o);
		this.personEthnicityIdWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonEthnicityId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficContraband personEthnicityIdInit() {
		if(!personEthnicityIdWrap.alreadyInitialized) {
			_personEthnicityId(personEthnicityIdWrap);
			if(personEthnicityId == null)
				setPersonEthnicityId(personEthnicityIdWrap.o);
			personEthnicityIdWrap.o(null);
		}
		personEthnicityIdWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static String staticSolrPersonEthnicityId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonEthnicityId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonEthnicityId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrPersonEthnicityId(siteRequest_, TrafficContraband.staticSolrPersonEthnicityId(siteRequest_, TrafficContraband.staticSetPersonEthnicityId(siteRequest_, o)));
	}

	public String solrPersonEthnicityId() {
		return TrafficContraband.staticSolrPersonEthnicityId(siteRequest_, personEthnicityId);
	}

	public String strPersonEthnicityId() {
		return personEthnicityId == null ? "" : personEthnicityId;
	}

	public String sqlPersonEthnicityId() {
		return personEthnicityId;
	}

	public String jsonPersonEthnicityId() {
		return personEthnicityId == null ? "" : personEthnicityId;
	}

	//////////////////////////
	// personEthnicityTitle //
	//////////////////////////

	/**	 The entity personEthnicityTitle
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String personEthnicityTitle;
	@JsonIgnore
	public Wrap<String> personEthnicityTitleWrap = new Wrap<String>().var("personEthnicityTitle").o(personEthnicityTitle);

	/**	<br/> The entity personEthnicityTitle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personEthnicityTitle">Find the entity personEthnicityTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personEthnicityTitle(Wrap<String> w);

	public String getPersonEthnicityTitle() {
		return personEthnicityTitle;
	}
	public void setPersonEthnicityTitle(String o) {
		this.personEthnicityTitle = TrafficContraband.staticSetPersonEthnicityTitle(siteRequest_, o);
		this.personEthnicityTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonEthnicityTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficContraband personEthnicityTitleInit() {
		if(!personEthnicityTitleWrap.alreadyInitialized) {
			_personEthnicityTitle(personEthnicityTitleWrap);
			if(personEthnicityTitle == null)
				setPersonEthnicityTitle(personEthnicityTitleWrap.o);
			personEthnicityTitleWrap.o(null);
		}
		personEthnicityTitleWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static String staticSolrPersonEthnicityTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonEthnicityTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonEthnicityTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrPersonEthnicityTitle(siteRequest_, TrafficContraband.staticSolrPersonEthnicityTitle(siteRequest_, TrafficContraband.staticSetPersonEthnicityTitle(siteRequest_, o)));
	}

	public String solrPersonEthnicityTitle() {
		return TrafficContraband.staticSolrPersonEthnicityTitle(siteRequest_, personEthnicityTitle);
	}

	public String strPersonEthnicityTitle() {
		return personEthnicityTitle == null ? "" : personEthnicityTitle;
	}

	public String sqlPersonEthnicityTitle() {
		return personEthnicityTitle;
	}

	public String jsonPersonEthnicityTitle() {
		return personEthnicityTitle == null ? "" : personEthnicityTitle;
	}

	//////////////////
	// personRaceId //
	//////////////////

	/**	 The entity personRaceId
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String personRaceId;
	@JsonIgnore
	public Wrap<String> personRaceIdWrap = new Wrap<String>().var("personRaceId").o(personRaceId);

	/**	<br/> The entity personRaceId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personRaceId">Find the entity personRaceId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personRaceId(Wrap<String> w);

	public String getPersonRaceId() {
		return personRaceId;
	}
	public void setPersonRaceId(String o) {
		this.personRaceId = TrafficContraband.staticSetPersonRaceId(siteRequest_, o);
		this.personRaceIdWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonRaceId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficContraband personRaceIdInit() {
		if(!personRaceIdWrap.alreadyInitialized) {
			_personRaceId(personRaceIdWrap);
			if(personRaceId == null)
				setPersonRaceId(personRaceIdWrap.o);
			personRaceIdWrap.o(null);
		}
		personRaceIdWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static String staticSolrPersonRaceId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonRaceId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonRaceId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrPersonRaceId(siteRequest_, TrafficContraband.staticSolrPersonRaceId(siteRequest_, TrafficContraband.staticSetPersonRaceId(siteRequest_, o)));
	}

	public String solrPersonRaceId() {
		return TrafficContraband.staticSolrPersonRaceId(siteRequest_, personRaceId);
	}

	public String strPersonRaceId() {
		return personRaceId == null ? "" : personRaceId;
	}

	public String sqlPersonRaceId() {
		return personRaceId;
	}

	public String jsonPersonRaceId() {
		return personRaceId == null ? "" : personRaceId;
	}

	/////////////////////
	// personRaceTitle //
	/////////////////////

	/**	 The entity personRaceTitle
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String personRaceTitle;
	@JsonIgnore
	public Wrap<String> personRaceTitleWrap = new Wrap<String>().var("personRaceTitle").o(personRaceTitle);

	/**	<br/> The entity personRaceTitle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personRaceTitle">Find the entity personRaceTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personRaceTitle(Wrap<String> w);

	public String getPersonRaceTitle() {
		return personRaceTitle;
	}
	public void setPersonRaceTitle(String o) {
		this.personRaceTitle = TrafficContraband.staticSetPersonRaceTitle(siteRequest_, o);
		this.personRaceTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonRaceTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficContraband personRaceTitleInit() {
		if(!personRaceTitleWrap.alreadyInitialized) {
			_personRaceTitle(personRaceTitleWrap);
			if(personRaceTitle == null)
				setPersonRaceTitle(personRaceTitleWrap.o);
			personRaceTitleWrap.o(null);
		}
		personRaceTitleWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static String staticSolrPersonRaceTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonRaceTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonRaceTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrPersonRaceTitle(siteRequest_, TrafficContraband.staticSolrPersonRaceTitle(siteRequest_, TrafficContraband.staticSetPersonRaceTitle(siteRequest_, o)));
	}

	public String solrPersonRaceTitle() {
		return TrafficContraband.staticSolrPersonRaceTitle(siteRequest_, personRaceTitle);
	}

	public String strPersonRaceTitle() {
		return personRaceTitle == null ? "" : personRaceTitle;
	}

	public String sqlPersonRaceTitle() {
		return personRaceTitle;
	}

	public String jsonPersonRaceTitle() {
		return personRaceTitle == null ? "" : personRaceTitle;
	}

	////////////
	// stopId //
	////////////

	/**	 The entity stopId
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String stopId;
	@JsonIgnore
	public Wrap<String> stopIdWrap = new Wrap<String>().var("stopId").o(stopId);

	/**	<br/> The entity stopId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopId">Find the entity stopId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopId(Wrap<String> w);

	public String getStopId() {
		return stopId;
	}
	public void setStopId(String o) {
		this.stopId = TrafficContraband.staticSetStopId(siteRequest_, o);
		this.stopIdWrap.alreadyInitialized = true;
	}
	public static String staticSetStopId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficContraband stopIdInit() {
		if(!stopIdWrap.alreadyInitialized) {
			_stopId(stopIdWrap);
			if(stopId == null)
				setStopId(stopIdWrap.o);
			stopIdWrap.o(null);
		}
		stopIdWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static String staticSolrStopId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrStopId(siteRequest_, TrafficContraband.staticSolrStopId(siteRequest_, TrafficContraband.staticSetStopId(siteRequest_, o)));
	}

	public String solrStopId() {
		return TrafficContraband.staticSolrStopId(siteRequest_, stopId);
	}

	public String strStopId() {
		return stopId == null ? "" : stopId;
	}

	public String sqlStopId() {
		return stopId;
	}

	public String jsonStopId() {
		return stopId == null ? "" : stopId;
	}

	///////////////////
	// searchTypeNum //
	///////////////////

	/**	 The entity searchTypeNum
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer searchTypeNum;
	@JsonIgnore
	public Wrap<Integer> searchTypeNumWrap = new Wrap<Integer>().var("searchTypeNum").o(searchTypeNum);

	/**	<br/> The entity searchTypeNum
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchTypeNum">Find the entity searchTypeNum in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _searchTypeNum(Wrap<Integer> w);

	public Integer getSearchTypeNum() {
		return searchTypeNum;
	}

	public void setSearchTypeNum(Integer searchTypeNum) {
		this.searchTypeNum = searchTypeNum;
		this.searchTypeNumWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setSearchTypeNum(String o) {
		this.searchTypeNum = TrafficContraband.staticSetSearchTypeNum(siteRequest_, o);
		this.searchTypeNumWrap.alreadyInitialized = true;
	}
	public static Integer staticSetSearchTypeNum(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected TrafficContraband searchTypeNumInit() {
		if(!searchTypeNumWrap.alreadyInitialized) {
			_searchTypeNum(searchTypeNumWrap);
			if(searchTypeNum == null)
				setSearchTypeNum(searchTypeNumWrap.o);
			searchTypeNumWrap.o(null);
		}
		searchTypeNumWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Integer staticSolrSearchTypeNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrSearchTypeNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchTypeNum(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrSearchTypeNum(siteRequest_, TrafficContraband.staticSolrSearchTypeNum(siteRequest_, TrafficContraband.staticSetSearchTypeNum(siteRequest_, o)));
	}

	public Integer solrSearchTypeNum() {
		return TrafficContraband.staticSolrSearchTypeNum(siteRequest_, searchTypeNum);
	}

	public String strSearchTypeNum() {
		return searchTypeNum == null ? "" : searchTypeNum.toString();
	}

	public Integer sqlSearchTypeNum() {
		return searchTypeNum;
	}

	public String jsonSearchTypeNum() {
		return searchTypeNum == null ? "" : searchTypeNum.toString();
	}

	/////////////////////
	// searchTypeTitle //
	/////////////////////

	/**	 The entity searchTypeTitle
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String searchTypeTitle;
	@JsonIgnore
	public Wrap<String> searchTypeTitleWrap = new Wrap<String>().var("searchTypeTitle").o(searchTypeTitle);

	/**	<br/> The entity searchTypeTitle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchTypeTitle">Find the entity searchTypeTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _searchTypeTitle(Wrap<String> w);

	public String getSearchTypeTitle() {
		return searchTypeTitle;
	}
	public void setSearchTypeTitle(String o) {
		this.searchTypeTitle = TrafficContraband.staticSetSearchTypeTitle(siteRequest_, o);
		this.searchTypeTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetSearchTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficContraband searchTypeTitleInit() {
		if(!searchTypeTitleWrap.alreadyInitialized) {
			_searchTypeTitle(searchTypeTitleWrap);
			if(searchTypeTitle == null)
				setSearchTypeTitle(searchTypeTitleWrap.o);
			searchTypeTitleWrap.o(null);
		}
		searchTypeTitleWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static String staticSolrSearchTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrSearchTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrSearchTypeTitle(siteRequest_, TrafficContraband.staticSolrSearchTypeTitle(siteRequest_, TrafficContraband.staticSetSearchTypeTitle(siteRequest_, o)));
	}

	public String solrSearchTypeTitle() {
		return TrafficContraband.staticSolrSearchTypeTitle(siteRequest_, searchTypeTitle);
	}

	public String strSearchTypeTitle() {
		return searchTypeTitle == null ? "" : searchTypeTitle;
	}

	public String sqlSearchTypeTitle() {
		return searchTypeTitle;
	}

	public String jsonSearchTypeTitle() {
		return searchTypeTitle == null ? "" : searchTypeTitle;
	}

	///////////////////
	// searchVehicle //
	///////////////////

	/**	 The entity searchVehicle
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected Boolean searchVehicle;
	@JsonIgnore
	public Wrap<Boolean> searchVehicleWrap = new Wrap<Boolean>().var("searchVehicle").o(searchVehicle);

	/**	<br/> The entity searchVehicle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchVehicle">Find the entity searchVehicle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _searchVehicle(Wrap<Boolean> w);

	public Boolean getSearchVehicle() {
		return searchVehicle;
	}

	public void setSearchVehicle(Boolean searchVehicle) {
		this.searchVehicle = searchVehicle;
		this.searchVehicleWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setSearchVehicle(String o) {
		this.searchVehicle = TrafficContraband.staticSetSearchVehicle(siteRequest_, o);
		this.searchVehicleWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSearchVehicle(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficContraband searchVehicleInit() {
		if(!searchVehicleWrap.alreadyInitialized) {
			_searchVehicle(searchVehicleWrap);
			if(searchVehicle == null)
				setSearchVehicle(searchVehicleWrap.o);
			searchVehicleWrap.o(null);
		}
		searchVehicleWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Boolean staticSolrSearchVehicle(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrSearchVehicle(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchVehicle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrSearchVehicle(siteRequest_, TrafficContraband.staticSolrSearchVehicle(siteRequest_, TrafficContraband.staticSetSearchVehicle(siteRequest_, o)));
	}

	public Boolean solrSearchVehicle() {
		return TrafficContraband.staticSolrSearchVehicle(siteRequest_, searchVehicle);
	}

	public String strSearchVehicle() {
		return searchVehicle == null ? "" : searchVehicle.toString();
	}

	public Boolean sqlSearchVehicle() {
		return searchVehicle;
	}

	public String jsonSearchVehicle() {
		return searchVehicle == null ? "" : searchVehicle.toString();
	}

	//////////////////
	// searchDriver //
	//////////////////

	/**	 The entity searchDriver
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected Boolean searchDriver;
	@JsonIgnore
	public Wrap<Boolean> searchDriverWrap = new Wrap<Boolean>().var("searchDriver").o(searchDriver);

	/**	<br/> The entity searchDriver
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchDriver">Find the entity searchDriver in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _searchDriver(Wrap<Boolean> w);

	public Boolean getSearchDriver() {
		return searchDriver;
	}

	public void setSearchDriver(Boolean searchDriver) {
		this.searchDriver = searchDriver;
		this.searchDriverWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setSearchDriver(String o) {
		this.searchDriver = TrafficContraband.staticSetSearchDriver(siteRequest_, o);
		this.searchDriverWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSearchDriver(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficContraband searchDriverInit() {
		if(!searchDriverWrap.alreadyInitialized) {
			_searchDriver(searchDriverWrap);
			if(searchDriver == null)
				setSearchDriver(searchDriverWrap.o);
			searchDriverWrap.o(null);
		}
		searchDriverWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Boolean staticSolrSearchDriver(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrSearchDriver(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchDriver(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrSearchDriver(siteRequest_, TrafficContraband.staticSolrSearchDriver(siteRequest_, TrafficContraband.staticSetSearchDriver(siteRequest_, o)));
	}

	public Boolean solrSearchDriver() {
		return TrafficContraband.staticSolrSearchDriver(siteRequest_, searchDriver);
	}

	public String strSearchDriver() {
		return searchDriver == null ? "" : searchDriver.toString();
	}

	public Boolean sqlSearchDriver() {
		return searchDriver;
	}

	public String jsonSearchDriver() {
		return searchDriver == null ? "" : searchDriver.toString();
	}

	/////////////////////
	// searchPassenger //
	/////////////////////

	/**	 The entity searchPassenger
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected Boolean searchPassenger;
	@JsonIgnore
	public Wrap<Boolean> searchPassengerWrap = new Wrap<Boolean>().var("searchPassenger").o(searchPassenger);

	/**	<br/> The entity searchPassenger
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchPassenger">Find the entity searchPassenger in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _searchPassenger(Wrap<Boolean> w);

	public Boolean getSearchPassenger() {
		return searchPassenger;
	}

	public void setSearchPassenger(Boolean searchPassenger) {
		this.searchPassenger = searchPassenger;
		this.searchPassengerWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setSearchPassenger(String o) {
		this.searchPassenger = TrafficContraband.staticSetSearchPassenger(siteRequest_, o);
		this.searchPassengerWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSearchPassenger(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficContraband searchPassengerInit() {
		if(!searchPassengerWrap.alreadyInitialized) {
			_searchPassenger(searchPassengerWrap);
			if(searchPassenger == null)
				setSearchPassenger(searchPassengerWrap.o);
			searchPassengerWrap.o(null);
		}
		searchPassengerWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Boolean staticSolrSearchPassenger(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrSearchPassenger(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchPassenger(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrSearchPassenger(siteRequest_, TrafficContraband.staticSolrSearchPassenger(siteRequest_, TrafficContraband.staticSetSearchPassenger(siteRequest_, o)));
	}

	public Boolean solrSearchPassenger() {
		return TrafficContraband.staticSolrSearchPassenger(siteRequest_, searchPassenger);
	}

	public String strSearchPassenger() {
		return searchPassenger == null ? "" : searchPassenger.toString();
	}

	public Boolean sqlSearchPassenger() {
		return searchPassenger;
	}

	public String jsonSearchPassenger() {
		return searchPassenger == null ? "" : searchPassenger.toString();
	}

	////////////////////
	// searchProperty //
	////////////////////

	/**	 The entity searchProperty
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected Boolean searchProperty;
	@JsonIgnore
	public Wrap<Boolean> searchPropertyWrap = new Wrap<Boolean>().var("searchProperty").o(searchProperty);

	/**	<br/> The entity searchProperty
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchProperty">Find the entity searchProperty in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _searchProperty(Wrap<Boolean> w);

	public Boolean getSearchProperty() {
		return searchProperty;
	}

	public void setSearchProperty(Boolean searchProperty) {
		this.searchProperty = searchProperty;
		this.searchPropertyWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setSearchProperty(String o) {
		this.searchProperty = TrafficContraband.staticSetSearchProperty(siteRequest_, o);
		this.searchPropertyWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSearchProperty(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficContraband searchPropertyInit() {
		if(!searchPropertyWrap.alreadyInitialized) {
			_searchProperty(searchPropertyWrap);
			if(searchProperty == null)
				setSearchProperty(searchPropertyWrap.o);
			searchPropertyWrap.o(null);
		}
		searchPropertyWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Boolean staticSolrSearchProperty(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrSearchProperty(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchProperty(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrSearchProperty(siteRequest_, TrafficContraband.staticSolrSearchProperty(siteRequest_, TrafficContraband.staticSetSearchProperty(siteRequest_, o)));
	}

	public Boolean solrSearchProperty() {
		return TrafficContraband.staticSolrSearchProperty(siteRequest_, searchProperty);
	}

	public String strSearchProperty() {
		return searchProperty == null ? "" : searchProperty.toString();
	}

	public Boolean sqlSearchProperty() {
		return searchProperty;
	}

	public String jsonSearchProperty() {
		return searchProperty == null ? "" : searchProperty.toString();
	}

	/////////////////////////
	// searchVehicleSiezed //
	/////////////////////////

	/**	 The entity searchVehicleSiezed
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected Boolean searchVehicleSiezed;
	@JsonIgnore
	public Wrap<Boolean> searchVehicleSiezedWrap = new Wrap<Boolean>().var("searchVehicleSiezed").o(searchVehicleSiezed);

	/**	<br/> The entity searchVehicleSiezed
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchVehicleSiezed">Find the entity searchVehicleSiezed in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _searchVehicleSiezed(Wrap<Boolean> w);

	public Boolean getSearchVehicleSiezed() {
		return searchVehicleSiezed;
	}

	public void setSearchVehicleSiezed(Boolean searchVehicleSiezed) {
		this.searchVehicleSiezed = searchVehicleSiezed;
		this.searchVehicleSiezedWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setSearchVehicleSiezed(String o) {
		this.searchVehicleSiezed = TrafficContraband.staticSetSearchVehicleSiezed(siteRequest_, o);
		this.searchVehicleSiezedWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSearchVehicleSiezed(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficContraband searchVehicleSiezedInit() {
		if(!searchVehicleSiezedWrap.alreadyInitialized) {
			_searchVehicleSiezed(searchVehicleSiezedWrap);
			if(searchVehicleSiezed == null)
				setSearchVehicleSiezed(searchVehicleSiezedWrap.o);
			searchVehicleSiezedWrap.o(null);
		}
		searchVehicleSiezedWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Boolean staticSolrSearchVehicleSiezed(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrSearchVehicleSiezed(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchVehicleSiezed(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrSearchVehicleSiezed(siteRequest_, TrafficContraband.staticSolrSearchVehicleSiezed(siteRequest_, TrafficContraband.staticSetSearchVehicleSiezed(siteRequest_, o)));
	}

	public Boolean solrSearchVehicleSiezed() {
		return TrafficContraband.staticSolrSearchVehicleSiezed(siteRequest_, searchVehicleSiezed);
	}

	public String strSearchVehicleSiezed() {
		return searchVehicleSiezed == null ? "" : searchVehicleSiezed.toString();
	}

	public Boolean sqlSearchVehicleSiezed() {
		return searchVehicleSiezed;
	}

	public String jsonSearchVehicleSiezed() {
		return searchVehicleSiezed == null ? "" : searchVehicleSiezed.toString();
	}

	//////////////////////////////////
	// searchPersonalPropertySiezed //
	//////////////////////////////////

	/**	 The entity searchPersonalPropertySiezed
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected Boolean searchPersonalPropertySiezed;
	@JsonIgnore
	public Wrap<Boolean> searchPersonalPropertySiezedWrap = new Wrap<Boolean>().var("searchPersonalPropertySiezed").o(searchPersonalPropertySiezed);

	/**	<br/> The entity searchPersonalPropertySiezed
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchPersonalPropertySiezed">Find the entity searchPersonalPropertySiezed in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _searchPersonalPropertySiezed(Wrap<Boolean> w);

	public Boolean getSearchPersonalPropertySiezed() {
		return searchPersonalPropertySiezed;
	}

	public void setSearchPersonalPropertySiezed(Boolean searchPersonalPropertySiezed) {
		this.searchPersonalPropertySiezed = searchPersonalPropertySiezed;
		this.searchPersonalPropertySiezedWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setSearchPersonalPropertySiezed(String o) {
		this.searchPersonalPropertySiezed = TrafficContraband.staticSetSearchPersonalPropertySiezed(siteRequest_, o);
		this.searchPersonalPropertySiezedWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSearchPersonalPropertySiezed(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficContraband searchPersonalPropertySiezedInit() {
		if(!searchPersonalPropertySiezedWrap.alreadyInitialized) {
			_searchPersonalPropertySiezed(searchPersonalPropertySiezedWrap);
			if(searchPersonalPropertySiezed == null)
				setSearchPersonalPropertySiezed(searchPersonalPropertySiezedWrap.o);
			searchPersonalPropertySiezedWrap.o(null);
		}
		searchPersonalPropertySiezedWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Boolean staticSolrSearchPersonalPropertySiezed(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrSearchPersonalPropertySiezed(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchPersonalPropertySiezed(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrSearchPersonalPropertySiezed(siteRequest_, TrafficContraband.staticSolrSearchPersonalPropertySiezed(siteRequest_, TrafficContraband.staticSetSearchPersonalPropertySiezed(siteRequest_, o)));
	}

	public Boolean solrSearchPersonalPropertySiezed() {
		return TrafficContraband.staticSolrSearchPersonalPropertySiezed(siteRequest_, searchPersonalPropertySiezed);
	}

	public String strSearchPersonalPropertySiezed() {
		return searchPersonalPropertySiezed == null ? "" : searchPersonalPropertySiezed.toString();
	}

	public Boolean sqlSearchPersonalPropertySiezed() {
		return searchPersonalPropertySiezed;
	}

	public String jsonSearchPersonalPropertySiezed() {
		return searchPersonalPropertySiezed == null ? "" : searchPersonalPropertySiezed.toString();
	}

	///////////////////////////////
	// searchOtherPropertySiezed //
	///////////////////////////////

	/**	 The entity searchOtherPropertySiezed
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected Boolean searchOtherPropertySiezed;
	@JsonIgnore
	public Wrap<Boolean> searchOtherPropertySiezedWrap = new Wrap<Boolean>().var("searchOtherPropertySiezed").o(searchOtherPropertySiezed);

	/**	<br/> The entity searchOtherPropertySiezed
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchOtherPropertySiezed">Find the entity searchOtherPropertySiezed in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _searchOtherPropertySiezed(Wrap<Boolean> w);

	public Boolean getSearchOtherPropertySiezed() {
		return searchOtherPropertySiezed;
	}

	public void setSearchOtherPropertySiezed(Boolean searchOtherPropertySiezed) {
		this.searchOtherPropertySiezed = searchOtherPropertySiezed;
		this.searchOtherPropertySiezedWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setSearchOtherPropertySiezed(String o) {
		this.searchOtherPropertySiezed = TrafficContraband.staticSetSearchOtherPropertySiezed(siteRequest_, o);
		this.searchOtherPropertySiezedWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSearchOtherPropertySiezed(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficContraband searchOtherPropertySiezedInit() {
		if(!searchOtherPropertySiezedWrap.alreadyInitialized) {
			_searchOtherPropertySiezed(searchOtherPropertySiezedWrap);
			if(searchOtherPropertySiezed == null)
				setSearchOtherPropertySiezed(searchOtherPropertySiezedWrap.o);
			searchOtherPropertySiezedWrap.o(null);
		}
		searchOtherPropertySiezedWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Boolean staticSolrSearchOtherPropertySiezed(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrSearchOtherPropertySiezed(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchOtherPropertySiezed(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrSearchOtherPropertySiezed(siteRequest_, TrafficContraband.staticSolrSearchOtherPropertySiezed(siteRequest_, TrafficContraband.staticSetSearchOtherPropertySiezed(siteRequest_, o)));
	}

	public Boolean solrSearchOtherPropertySiezed() {
		return TrafficContraband.staticSolrSearchOtherPropertySiezed(siteRequest_, searchOtherPropertySiezed);
	}

	public String strSearchOtherPropertySiezed() {
		return searchOtherPropertySiezed == null ? "" : searchOtherPropertySiezed.toString();
	}

	public Boolean sqlSearchOtherPropertySiezed() {
		return searchOtherPropertySiezed;
	}

	public String jsonSearchOtherPropertySiezed() {
		return searchOtherPropertySiezed == null ? "" : searchOtherPropertySiezed.toString();
	}

	//////////////////////
	// contrabandOunces //
	//////////////////////

	/**	 The entity contrabandOunces
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected BigDecimal contrabandOunces;
	@JsonIgnore
	public Wrap<BigDecimal> contrabandOuncesWrap = new Wrap<BigDecimal>().var("contrabandOunces").o(contrabandOunces);

	/**	<br/> The entity contrabandOunces
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:contrabandOunces">Find the entity contrabandOunces in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _contrabandOunces(Wrap<BigDecimal> w);

	public BigDecimal getContrabandOunces() {
		return contrabandOunces;
	}

	public void setContrabandOunces(BigDecimal contrabandOunces) {
		this.contrabandOunces = contrabandOunces;
		this.contrabandOuncesWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setContrabandOunces(String o) {
		this.contrabandOunces = TrafficContraband.staticSetContrabandOunces(siteRequest_, o);
		this.contrabandOuncesWrap.alreadyInitialized = true;
	}
	public static BigDecimal staticSetContrabandOunces(SiteRequestEnUS siteRequest_, String o) {
		o = StringUtils.removeAll(o, "[^\\d\\.]");
		if(NumberUtils.isParsable(o))
			return new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		return null;
	}
	@JsonIgnore
	public void setContrabandOunces(Double o) {
			this.contrabandOunces = new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		this.contrabandOuncesWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setContrabandOunces(Integer o) {
			this.contrabandOunces = new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		this.contrabandOuncesWrap.alreadyInitialized = true;
	}
	protected TrafficContraband contrabandOuncesInit() {
		if(!contrabandOuncesWrap.alreadyInitialized) {
			_contrabandOunces(contrabandOuncesWrap);
			if(contrabandOunces == null)
				setContrabandOunces(contrabandOuncesWrap.o);
			contrabandOuncesWrap.o(null);
		}
		contrabandOuncesWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Double staticSolrContrabandOunces(SiteRequestEnUS siteRequest_, BigDecimal o) {
		return o == null ? null : o.doubleValue();
	}

	public static String staticSolrStrContrabandOunces(SiteRequestEnUS siteRequest_, Double o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqContrabandOunces(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrContrabandOunces(siteRequest_, TrafficContraband.staticSolrContrabandOunces(siteRequest_, TrafficContraband.staticSetContrabandOunces(siteRequest_, o)));
	}

	public Double solrContrabandOunces() {
		return TrafficContraband.staticSolrContrabandOunces(siteRequest_, contrabandOunces);
	}

	public String strContrabandOunces() {
		return contrabandOunces == null ? "" : contrabandOunces.setScale(2, RoundingMode.HALF_UP).toString();
	}

	public BigDecimal sqlContrabandOunces() {
		return contrabandOunces;
	}

	public String jsonContrabandOunces() {
		return contrabandOunces == null ? "" : contrabandOunces.toString();
	}

	//////////////////////
	// contrabandPounds //
	//////////////////////

	/**	 The entity contrabandPounds
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected BigDecimal contrabandPounds;
	@JsonIgnore
	public Wrap<BigDecimal> contrabandPoundsWrap = new Wrap<BigDecimal>().var("contrabandPounds").o(contrabandPounds);

	/**	<br/> The entity contrabandPounds
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:contrabandPounds">Find the entity contrabandPounds in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _contrabandPounds(Wrap<BigDecimal> w);

	public BigDecimal getContrabandPounds() {
		return contrabandPounds;
	}

	public void setContrabandPounds(BigDecimal contrabandPounds) {
		this.contrabandPounds = contrabandPounds;
		this.contrabandPoundsWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setContrabandPounds(String o) {
		this.contrabandPounds = TrafficContraband.staticSetContrabandPounds(siteRequest_, o);
		this.contrabandPoundsWrap.alreadyInitialized = true;
	}
	public static BigDecimal staticSetContrabandPounds(SiteRequestEnUS siteRequest_, String o) {
		o = StringUtils.removeAll(o, "[^\\d\\.]");
		if(NumberUtils.isParsable(o))
			return new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		return null;
	}
	@JsonIgnore
	public void setContrabandPounds(Double o) {
			this.contrabandPounds = new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		this.contrabandPoundsWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setContrabandPounds(Integer o) {
			this.contrabandPounds = new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		this.contrabandPoundsWrap.alreadyInitialized = true;
	}
	protected TrafficContraband contrabandPoundsInit() {
		if(!contrabandPoundsWrap.alreadyInitialized) {
			_contrabandPounds(contrabandPoundsWrap);
			if(contrabandPounds == null)
				setContrabandPounds(contrabandPoundsWrap.o);
			contrabandPoundsWrap.o(null);
		}
		contrabandPoundsWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Double staticSolrContrabandPounds(SiteRequestEnUS siteRequest_, BigDecimal o) {
		return o == null ? null : o.doubleValue();
	}

	public static String staticSolrStrContrabandPounds(SiteRequestEnUS siteRequest_, Double o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqContrabandPounds(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrContrabandPounds(siteRequest_, TrafficContraband.staticSolrContrabandPounds(siteRequest_, TrafficContraband.staticSetContrabandPounds(siteRequest_, o)));
	}

	public Double solrContrabandPounds() {
		return TrafficContraband.staticSolrContrabandPounds(siteRequest_, contrabandPounds);
	}

	public String strContrabandPounds() {
		return contrabandPounds == null ? "" : contrabandPounds.setScale(2, RoundingMode.HALF_UP).toString();
	}

	public BigDecimal sqlContrabandPounds() {
		return contrabandPounds;
	}

	public String jsonContrabandPounds() {
		return contrabandPounds == null ? "" : contrabandPounds.toString();
	}

	/////////////////////
	// contrabandPints //
	/////////////////////

	/**	 The entity contrabandPints
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected BigDecimal contrabandPints;
	@JsonIgnore
	public Wrap<BigDecimal> contrabandPintsWrap = new Wrap<BigDecimal>().var("contrabandPints").o(contrabandPints);

	/**	<br/> The entity contrabandPints
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:contrabandPints">Find the entity contrabandPints in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _contrabandPints(Wrap<BigDecimal> w);

	public BigDecimal getContrabandPints() {
		return contrabandPints;
	}

	public void setContrabandPints(BigDecimal contrabandPints) {
		this.contrabandPints = contrabandPints;
		this.contrabandPintsWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setContrabandPints(String o) {
		this.contrabandPints = TrafficContraband.staticSetContrabandPints(siteRequest_, o);
		this.contrabandPintsWrap.alreadyInitialized = true;
	}
	public static BigDecimal staticSetContrabandPints(SiteRequestEnUS siteRequest_, String o) {
		o = StringUtils.removeAll(o, "[^\\d\\.]");
		if(NumberUtils.isParsable(o))
			return new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		return null;
	}
	@JsonIgnore
	public void setContrabandPints(Double o) {
			this.contrabandPints = new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		this.contrabandPintsWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setContrabandPints(Integer o) {
			this.contrabandPints = new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		this.contrabandPintsWrap.alreadyInitialized = true;
	}
	protected TrafficContraband contrabandPintsInit() {
		if(!contrabandPintsWrap.alreadyInitialized) {
			_contrabandPints(contrabandPintsWrap);
			if(contrabandPints == null)
				setContrabandPints(contrabandPintsWrap.o);
			contrabandPintsWrap.o(null);
		}
		contrabandPintsWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Double staticSolrContrabandPints(SiteRequestEnUS siteRequest_, BigDecimal o) {
		return o == null ? null : o.doubleValue();
	}

	public static String staticSolrStrContrabandPints(SiteRequestEnUS siteRequest_, Double o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqContrabandPints(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrContrabandPints(siteRequest_, TrafficContraband.staticSolrContrabandPints(siteRequest_, TrafficContraband.staticSetContrabandPints(siteRequest_, o)));
	}

	public Double solrContrabandPints() {
		return TrafficContraband.staticSolrContrabandPints(siteRequest_, contrabandPints);
	}

	public String strContrabandPints() {
		return contrabandPints == null ? "" : contrabandPints.setScale(2, RoundingMode.HALF_UP).toString();
	}

	public BigDecimal sqlContrabandPints() {
		return contrabandPints;
	}

	public String jsonContrabandPints() {
		return contrabandPints == null ? "" : contrabandPints.toString();
	}

	///////////////////////
	// contrabandGallons //
	///////////////////////

	/**	 The entity contrabandGallons
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected BigDecimal contrabandGallons;
	@JsonIgnore
	public Wrap<BigDecimal> contrabandGallonsWrap = new Wrap<BigDecimal>().var("contrabandGallons").o(contrabandGallons);

	/**	<br/> The entity contrabandGallons
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:contrabandGallons">Find the entity contrabandGallons in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _contrabandGallons(Wrap<BigDecimal> w);

	public BigDecimal getContrabandGallons() {
		return contrabandGallons;
	}

	public void setContrabandGallons(BigDecimal contrabandGallons) {
		this.contrabandGallons = contrabandGallons;
		this.contrabandGallonsWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setContrabandGallons(String o) {
		this.contrabandGallons = TrafficContraband.staticSetContrabandGallons(siteRequest_, o);
		this.contrabandGallonsWrap.alreadyInitialized = true;
	}
	public static BigDecimal staticSetContrabandGallons(SiteRequestEnUS siteRequest_, String o) {
		o = StringUtils.removeAll(o, "[^\\d\\.]");
		if(NumberUtils.isParsable(o))
			return new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		return null;
	}
	@JsonIgnore
	public void setContrabandGallons(Double o) {
			this.contrabandGallons = new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		this.contrabandGallonsWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setContrabandGallons(Integer o) {
			this.contrabandGallons = new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		this.contrabandGallonsWrap.alreadyInitialized = true;
	}
	protected TrafficContraband contrabandGallonsInit() {
		if(!contrabandGallonsWrap.alreadyInitialized) {
			_contrabandGallons(contrabandGallonsWrap);
			if(contrabandGallons == null)
				setContrabandGallons(contrabandGallonsWrap.o);
			contrabandGallonsWrap.o(null);
		}
		contrabandGallonsWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Double staticSolrContrabandGallons(SiteRequestEnUS siteRequest_, BigDecimal o) {
		return o == null ? null : o.doubleValue();
	}

	public static String staticSolrStrContrabandGallons(SiteRequestEnUS siteRequest_, Double o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqContrabandGallons(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrContrabandGallons(siteRequest_, TrafficContraband.staticSolrContrabandGallons(siteRequest_, TrafficContraband.staticSetContrabandGallons(siteRequest_, o)));
	}

	public Double solrContrabandGallons() {
		return TrafficContraband.staticSolrContrabandGallons(siteRequest_, contrabandGallons);
	}

	public String strContrabandGallons() {
		return contrabandGallons == null ? "" : contrabandGallons.setScale(2, RoundingMode.HALF_UP).toString();
	}

	public BigDecimal sqlContrabandGallons() {
		return contrabandGallons;
	}

	public String jsonContrabandGallons() {
		return contrabandGallons == null ? "" : contrabandGallons.toString();
	}

	///////////////////////
	// contrabandDosages //
	///////////////////////

	/**	 The entity contrabandDosages
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected BigDecimal contrabandDosages;
	@JsonIgnore
	public Wrap<BigDecimal> contrabandDosagesWrap = new Wrap<BigDecimal>().var("contrabandDosages").o(contrabandDosages);

	/**	<br/> The entity contrabandDosages
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:contrabandDosages">Find the entity contrabandDosages in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _contrabandDosages(Wrap<BigDecimal> w);

	public BigDecimal getContrabandDosages() {
		return contrabandDosages;
	}

	public void setContrabandDosages(BigDecimal contrabandDosages) {
		this.contrabandDosages = contrabandDosages;
		this.contrabandDosagesWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setContrabandDosages(String o) {
		this.contrabandDosages = TrafficContraband.staticSetContrabandDosages(siteRequest_, o);
		this.contrabandDosagesWrap.alreadyInitialized = true;
	}
	public static BigDecimal staticSetContrabandDosages(SiteRequestEnUS siteRequest_, String o) {
		o = StringUtils.removeAll(o, "[^\\d\\.]");
		if(NumberUtils.isParsable(o))
			return new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		return null;
	}
	@JsonIgnore
	public void setContrabandDosages(Double o) {
			this.contrabandDosages = new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		this.contrabandDosagesWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setContrabandDosages(Integer o) {
			this.contrabandDosages = new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		this.contrabandDosagesWrap.alreadyInitialized = true;
	}
	protected TrafficContraband contrabandDosagesInit() {
		if(!contrabandDosagesWrap.alreadyInitialized) {
			_contrabandDosages(contrabandDosagesWrap);
			if(contrabandDosages == null)
				setContrabandDosages(contrabandDosagesWrap.o);
			contrabandDosagesWrap.o(null);
		}
		contrabandDosagesWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Double staticSolrContrabandDosages(SiteRequestEnUS siteRequest_, BigDecimal o) {
		return o == null ? null : o.doubleValue();
	}

	public static String staticSolrStrContrabandDosages(SiteRequestEnUS siteRequest_, Double o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqContrabandDosages(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrContrabandDosages(siteRequest_, TrafficContraband.staticSolrContrabandDosages(siteRequest_, TrafficContraband.staticSetContrabandDosages(siteRequest_, o)));
	}

	public Double solrContrabandDosages() {
		return TrafficContraband.staticSolrContrabandDosages(siteRequest_, contrabandDosages);
	}

	public String strContrabandDosages() {
		return contrabandDosages == null ? "" : contrabandDosages.setScale(2, RoundingMode.HALF_UP).toString();
	}

	public BigDecimal sqlContrabandDosages() {
		return contrabandDosages;
	}

	public String jsonContrabandDosages() {
		return contrabandDosages == null ? "" : contrabandDosages.toString();
	}

	/////////////////////
	// contrabandGrams //
	/////////////////////

	/**	 The entity contrabandGrams
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected BigDecimal contrabandGrams;
	@JsonIgnore
	public Wrap<BigDecimal> contrabandGramsWrap = new Wrap<BigDecimal>().var("contrabandGrams").o(contrabandGrams);

	/**	<br/> The entity contrabandGrams
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:contrabandGrams">Find the entity contrabandGrams in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _contrabandGrams(Wrap<BigDecimal> w);

	public BigDecimal getContrabandGrams() {
		return contrabandGrams;
	}

	public void setContrabandGrams(BigDecimal contrabandGrams) {
		this.contrabandGrams = contrabandGrams;
		this.contrabandGramsWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setContrabandGrams(String o) {
		this.contrabandGrams = TrafficContraband.staticSetContrabandGrams(siteRequest_, o);
		this.contrabandGramsWrap.alreadyInitialized = true;
	}
	public static BigDecimal staticSetContrabandGrams(SiteRequestEnUS siteRequest_, String o) {
		o = StringUtils.removeAll(o, "[^\\d\\.]");
		if(NumberUtils.isParsable(o))
			return new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		return null;
	}
	@JsonIgnore
	public void setContrabandGrams(Double o) {
			this.contrabandGrams = new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		this.contrabandGramsWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setContrabandGrams(Integer o) {
			this.contrabandGrams = new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		this.contrabandGramsWrap.alreadyInitialized = true;
	}
	protected TrafficContraband contrabandGramsInit() {
		if(!contrabandGramsWrap.alreadyInitialized) {
			_contrabandGrams(contrabandGramsWrap);
			if(contrabandGrams == null)
				setContrabandGrams(contrabandGramsWrap.o);
			contrabandGramsWrap.o(null);
		}
		contrabandGramsWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Double staticSolrContrabandGrams(SiteRequestEnUS siteRequest_, BigDecimal o) {
		return o == null ? null : o.doubleValue();
	}

	public static String staticSolrStrContrabandGrams(SiteRequestEnUS siteRequest_, Double o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqContrabandGrams(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrContrabandGrams(siteRequest_, TrafficContraband.staticSolrContrabandGrams(siteRequest_, TrafficContraband.staticSetContrabandGrams(siteRequest_, o)));
	}

	public Double solrContrabandGrams() {
		return TrafficContraband.staticSolrContrabandGrams(siteRequest_, contrabandGrams);
	}

	public String strContrabandGrams() {
		return contrabandGrams == null ? "" : contrabandGrams.setScale(2, RoundingMode.HALF_UP).toString();
	}

	public BigDecimal sqlContrabandGrams() {
		return contrabandGrams;
	}

	public String jsonContrabandGrams() {
		return contrabandGrams == null ? "" : contrabandGrams.toString();
	}

	/////////////////////
	// contrabandKilos //
	/////////////////////

	/**	 The entity contrabandKilos
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected BigDecimal contrabandKilos;
	@JsonIgnore
	public Wrap<BigDecimal> contrabandKilosWrap = new Wrap<BigDecimal>().var("contrabandKilos").o(contrabandKilos);

	/**	<br/> The entity contrabandKilos
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:contrabandKilos">Find the entity contrabandKilos in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _contrabandKilos(Wrap<BigDecimal> w);

	public BigDecimal getContrabandKilos() {
		return contrabandKilos;
	}

	public void setContrabandKilos(BigDecimal contrabandKilos) {
		this.contrabandKilos = contrabandKilos;
		this.contrabandKilosWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setContrabandKilos(String o) {
		this.contrabandKilos = TrafficContraband.staticSetContrabandKilos(siteRequest_, o);
		this.contrabandKilosWrap.alreadyInitialized = true;
	}
	public static BigDecimal staticSetContrabandKilos(SiteRequestEnUS siteRequest_, String o) {
		o = StringUtils.removeAll(o, "[^\\d\\.]");
		if(NumberUtils.isParsable(o))
			return new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		return null;
	}
	@JsonIgnore
	public void setContrabandKilos(Double o) {
			this.contrabandKilos = new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		this.contrabandKilosWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setContrabandKilos(Integer o) {
			this.contrabandKilos = new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		this.contrabandKilosWrap.alreadyInitialized = true;
	}
	protected TrafficContraband contrabandKilosInit() {
		if(!contrabandKilosWrap.alreadyInitialized) {
			_contrabandKilos(contrabandKilosWrap);
			if(contrabandKilos == null)
				setContrabandKilos(contrabandKilosWrap.o);
			contrabandKilosWrap.o(null);
		}
		contrabandKilosWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Double staticSolrContrabandKilos(SiteRequestEnUS siteRequest_, BigDecimal o) {
		return o == null ? null : o.doubleValue();
	}

	public static String staticSolrStrContrabandKilos(SiteRequestEnUS siteRequest_, Double o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqContrabandKilos(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrContrabandKilos(siteRequest_, TrafficContraband.staticSolrContrabandKilos(siteRequest_, TrafficContraband.staticSetContrabandKilos(siteRequest_, o)));
	}

	public Double solrContrabandKilos() {
		return TrafficContraband.staticSolrContrabandKilos(siteRequest_, contrabandKilos);
	}

	public String strContrabandKilos() {
		return contrabandKilos == null ? "" : contrabandKilos.setScale(2, RoundingMode.HALF_UP).toString();
	}

	public BigDecimal sqlContrabandKilos() {
		return contrabandKilos;
	}

	public String jsonContrabandKilos() {
		return contrabandKilos == null ? "" : contrabandKilos.toString();
	}

	/////////////////////
	// contrabandMoney //
	/////////////////////

	/**	 The entity contrabandMoney
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected BigDecimal contrabandMoney;
	@JsonIgnore
	public Wrap<BigDecimal> contrabandMoneyWrap = new Wrap<BigDecimal>().var("contrabandMoney").o(contrabandMoney);

	/**	<br/> The entity contrabandMoney
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:contrabandMoney">Find the entity contrabandMoney in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _contrabandMoney(Wrap<BigDecimal> w);

	public BigDecimal getContrabandMoney() {
		return contrabandMoney;
	}

	public void setContrabandMoney(BigDecimal contrabandMoney) {
		this.contrabandMoney = contrabandMoney;
		this.contrabandMoneyWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setContrabandMoney(String o) {
		this.contrabandMoney = TrafficContraband.staticSetContrabandMoney(siteRequest_, o);
		this.contrabandMoneyWrap.alreadyInitialized = true;
	}
	public static BigDecimal staticSetContrabandMoney(SiteRequestEnUS siteRequest_, String o) {
		o = StringUtils.removeAll(o, "[^\\d\\.]");
		if(NumberUtils.isParsable(o))
			return new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		return null;
	}
	@JsonIgnore
	public void setContrabandMoney(Double o) {
			this.contrabandMoney = new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		this.contrabandMoneyWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setContrabandMoney(Integer o) {
			this.contrabandMoney = new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		this.contrabandMoneyWrap.alreadyInitialized = true;
	}
	protected TrafficContraband contrabandMoneyInit() {
		if(!contrabandMoneyWrap.alreadyInitialized) {
			_contrabandMoney(contrabandMoneyWrap);
			if(contrabandMoney == null)
				setContrabandMoney(contrabandMoneyWrap.o);
			contrabandMoneyWrap.o(null);
		}
		contrabandMoneyWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Double staticSolrContrabandMoney(SiteRequestEnUS siteRequest_, BigDecimal o) {
		return o == null ? null : o.doubleValue();
	}

	public static String staticSolrStrContrabandMoney(SiteRequestEnUS siteRequest_, Double o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqContrabandMoney(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrContrabandMoney(siteRequest_, TrafficContraband.staticSolrContrabandMoney(siteRequest_, TrafficContraband.staticSetContrabandMoney(siteRequest_, o)));
	}

	public Double solrContrabandMoney() {
		return TrafficContraband.staticSolrContrabandMoney(siteRequest_, contrabandMoney);
	}

	public String strContrabandMoney() {
		return contrabandMoney == null ? "" : contrabandMoney.setScale(2, RoundingMode.HALF_UP).toString();
	}

	public BigDecimal sqlContrabandMoney() {
		return contrabandMoney;
	}

	public String jsonContrabandMoney() {
		return contrabandMoney == null ? "" : contrabandMoney.toString();
	}

	///////////////////////
	// contrabandWeapons //
	///////////////////////

	/**	 The entity contrabandWeapons
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected BigDecimal contrabandWeapons;
	@JsonIgnore
	public Wrap<BigDecimal> contrabandWeaponsWrap = new Wrap<BigDecimal>().var("contrabandWeapons").o(contrabandWeapons);

	/**	<br/> The entity contrabandWeapons
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:contrabandWeapons">Find the entity contrabandWeapons in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _contrabandWeapons(Wrap<BigDecimal> w);

	public BigDecimal getContrabandWeapons() {
		return contrabandWeapons;
	}

	public void setContrabandWeapons(BigDecimal contrabandWeapons) {
		this.contrabandWeapons = contrabandWeapons;
		this.contrabandWeaponsWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setContrabandWeapons(String o) {
		this.contrabandWeapons = TrafficContraband.staticSetContrabandWeapons(siteRequest_, o);
		this.contrabandWeaponsWrap.alreadyInitialized = true;
	}
	public static BigDecimal staticSetContrabandWeapons(SiteRequestEnUS siteRequest_, String o) {
		o = StringUtils.removeAll(o, "[^\\d\\.]");
		if(NumberUtils.isParsable(o))
			return new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		return null;
	}
	@JsonIgnore
	public void setContrabandWeapons(Double o) {
			this.contrabandWeapons = new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		this.contrabandWeaponsWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setContrabandWeapons(Integer o) {
			this.contrabandWeapons = new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		this.contrabandWeaponsWrap.alreadyInitialized = true;
	}
	protected TrafficContraband contrabandWeaponsInit() {
		if(!contrabandWeaponsWrap.alreadyInitialized) {
			_contrabandWeapons(contrabandWeaponsWrap);
			if(contrabandWeapons == null)
				setContrabandWeapons(contrabandWeaponsWrap.o);
			contrabandWeaponsWrap.o(null);
		}
		contrabandWeaponsWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Double staticSolrContrabandWeapons(SiteRequestEnUS siteRequest_, BigDecimal o) {
		return o == null ? null : o.doubleValue();
	}

	public static String staticSolrStrContrabandWeapons(SiteRequestEnUS siteRequest_, Double o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqContrabandWeapons(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrContrabandWeapons(siteRequest_, TrafficContraband.staticSolrContrabandWeapons(siteRequest_, TrafficContraband.staticSetContrabandWeapons(siteRequest_, o)));
	}

	public Double solrContrabandWeapons() {
		return TrafficContraband.staticSolrContrabandWeapons(siteRequest_, contrabandWeapons);
	}

	public String strContrabandWeapons() {
		return contrabandWeapons == null ? "" : contrabandWeapons.setScale(2, RoundingMode.HALF_UP).toString();
	}

	public BigDecimal sqlContrabandWeapons() {
		return contrabandWeapons;
	}

	public String jsonContrabandWeapons() {
		return contrabandWeapons == null ? "" : contrabandWeapons.toString();
	}

	////////////////////////////
	// contrabandDollarAmount //
	////////////////////////////

	/**	 The entity contrabandDollarAmount
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected BigDecimal contrabandDollarAmount;
	@JsonIgnore
	public Wrap<BigDecimal> contrabandDollarAmountWrap = new Wrap<BigDecimal>().var("contrabandDollarAmount").o(contrabandDollarAmount);

	/**	<br/> The entity contrabandDollarAmount
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficcontraband.TrafficContraband&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:contrabandDollarAmount">Find the entity contrabandDollarAmount in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _contrabandDollarAmount(Wrap<BigDecimal> w);

	public BigDecimal getContrabandDollarAmount() {
		return contrabandDollarAmount;
	}

	public void setContrabandDollarAmount(BigDecimal contrabandDollarAmount) {
		this.contrabandDollarAmount = contrabandDollarAmount;
		this.contrabandDollarAmountWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setContrabandDollarAmount(String o) {
		this.contrabandDollarAmount = TrafficContraband.staticSetContrabandDollarAmount(siteRequest_, o);
		this.contrabandDollarAmountWrap.alreadyInitialized = true;
	}
	public static BigDecimal staticSetContrabandDollarAmount(SiteRequestEnUS siteRequest_, String o) {
		o = StringUtils.removeAll(o, "[^\\d\\.]");
		if(NumberUtils.isParsable(o))
			return new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		return null;
	}
	@JsonIgnore
	public void setContrabandDollarAmount(Double o) {
			this.contrabandDollarAmount = new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		this.contrabandDollarAmountWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setContrabandDollarAmount(Integer o) {
			this.contrabandDollarAmount = new BigDecimal(o, MathContext.DECIMAL64).setScale(2, RoundingMode.HALF_UP);
		this.contrabandDollarAmountWrap.alreadyInitialized = true;
	}
	protected TrafficContraband contrabandDollarAmountInit() {
		if(!contrabandDollarAmountWrap.alreadyInitialized) {
			_contrabandDollarAmount(contrabandDollarAmountWrap);
			if(contrabandDollarAmount == null)
				setContrabandDollarAmount(contrabandDollarAmountWrap.o);
			contrabandDollarAmountWrap.o(null);
		}
		contrabandDollarAmountWrap.alreadyInitialized(true);
		return (TrafficContraband)this;
	}

	public static Double staticSolrContrabandDollarAmount(SiteRequestEnUS siteRequest_, BigDecimal o) {
		return o == null ? null : o.doubleValue();
	}

	public static String staticSolrStrContrabandDollarAmount(SiteRequestEnUS siteRequest_, Double o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqContrabandDollarAmount(SiteRequestEnUS siteRequest_, String o) {
		return TrafficContraband.staticSolrStrContrabandDollarAmount(siteRequest_, TrafficContraband.staticSolrContrabandDollarAmount(siteRequest_, TrafficContraband.staticSetContrabandDollarAmount(siteRequest_, o)));
	}

	public Double solrContrabandDollarAmount() {
		return TrafficContraband.staticSolrContrabandDollarAmount(siteRequest_, contrabandDollarAmount);
	}

	public String strContrabandDollarAmount() {
		return contrabandDollarAmount == null ? "" : contrabandDollarAmount.setScale(2, RoundingMode.HALF_UP).toString();
	}

	public BigDecimal sqlContrabandDollarAmount() {
		return contrabandDollarAmount;
	}

	public String jsonContrabandDollarAmount() {
		return contrabandDollarAmount == null ? "" : contrabandDollarAmount.toString();
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedTrafficContraband = false;

	public Future<Void> promiseDeepTrafficContraband(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedTrafficContraband) {
			alreadyInitializedTrafficContraband = true;
			return promiseDeepTrafficContraband();
		} else {
			return Future.succeededFuture();
		}
	}

	public Future<Void> promiseDeepTrafficContraband() {
		Promise<Void> promise = Promise.promise();
		Promise<Void> promise2 = Promise.promise();
		promiseTrafficContraband(promise2);
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

	public Future<Void> promiseTrafficContraband(Promise<Void> promise) {
		Future.future(a -> a.complete()).compose(a -> {
			Promise<Void> promise2 = Promise.promise();
			try {
				contrabandKeyInit();
				searchIdInit();
				promise2.complete();
			} catch(Exception ex) {
				promise2.fail(ex);
			}
			return promise2.future();
		}).compose(a -> {
			Promise<Void> promise2 = Promise.promise();
			trafficSearchSearchPromise().onSuccess(trafficSearchSearch -> {
				promise2.complete();
			}).onFailure(ex -> {
				promise2.fail(ex);
			});
			return promise2.future();
		}).compose(a -> {
			Promise<Void> promise2 = Promise.promise();
			try {
				trafficSearch_Init();
				stateAbbreviationInit();
				agencyTitleInit();
				stopDateTimeInit();
				stopPurposeNumInit();
				stopPurposeTitleInit();
				stopActionNumInit();
				stopActionTitleInit();
				stopDriverArrestInit();
				stopPassengerArrestInit();
				stopEncounterForceInit();
				stopEngageForceInit();
				stopOfficerInjuryInit();
				stopDriverInjuryInit();
				stopPassengerInjuryInit();
				stopOfficerIdInit();
				stopLocationIdInit();
				stopCityIdInit();
				personAgeInit();
				personTypeIdInit();
				personTypeTitleInit();
				personTypeDriverInit();
				personTypePassengerInit();
				personGenderIdInit();
				personGenderTitleInit();
				personGenderFemaleInit();
				personGenderMaleInit();
				personEthnicityIdInit();
				personEthnicityTitleInit();
				personRaceIdInit();
				personRaceTitleInit();
				stopIdInit();
				searchTypeNumInit();
				searchTypeTitleInit();
				searchVehicleInit();
				searchDriverInit();
				searchPassengerInit();
				searchPropertyInit();
				searchVehicleSiezedInit();
				searchPersonalPropertySiezedInit();
				searchOtherPropertySiezedInit();
				contrabandOuncesInit();
				contrabandPoundsInit();
				contrabandPintsInit();
				contrabandGallonsInit();
				contrabandDosagesInit();
				contrabandGramsInit();
				contrabandKilosInit();
				contrabandMoneyInit();
				contrabandWeaponsInit();
				contrabandDollarAmountInit();
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
		return promiseDeepTrafficContraband(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestTrafficContraband(SiteRequestEnUS siteRequest_) {
			super.siteRequestCluster(siteRequest_);
		if(trafficSearchSearch != null)
			trafficSearchSearch.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestTrafficContraband(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainTrafficContraband(v);
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
	public Object obtainTrafficContraband(String var) {
		TrafficContraband oTrafficContraband = (TrafficContraband)this;
		switch(var) {
			case "contrabandKey":
				return oTrafficContraband.contrabandKey;
			case "searchId":
				return oTrafficContraband.searchId;
			case "trafficSearchSearch":
				return oTrafficContraband.trafficSearchSearch;
			case "trafficSearch_":
				return oTrafficContraband.trafficSearch_;
			case "stateAbbreviation":
				return oTrafficContraband.stateAbbreviation;
			case "agencyTitle":
				return oTrafficContraband.agencyTitle;
			case "stopDateTime":
				return oTrafficContraband.stopDateTime;
			case "stopPurposeNum":
				return oTrafficContraband.stopPurposeNum;
			case "stopPurposeTitle":
				return oTrafficContraband.stopPurposeTitle;
			case "stopActionNum":
				return oTrafficContraband.stopActionNum;
			case "stopActionTitle":
				return oTrafficContraband.stopActionTitle;
			case "stopDriverArrest":
				return oTrafficContraband.stopDriverArrest;
			case "stopPassengerArrest":
				return oTrafficContraband.stopPassengerArrest;
			case "stopEncounterForce":
				return oTrafficContraband.stopEncounterForce;
			case "stopEngageForce":
				return oTrafficContraband.stopEngageForce;
			case "stopOfficerInjury":
				return oTrafficContraband.stopOfficerInjury;
			case "stopDriverInjury":
				return oTrafficContraband.stopDriverInjury;
			case "stopPassengerInjury":
				return oTrafficContraband.stopPassengerInjury;
			case "stopOfficerId":
				return oTrafficContraband.stopOfficerId;
			case "stopLocationId":
				return oTrafficContraband.stopLocationId;
			case "stopCityId":
				return oTrafficContraband.stopCityId;
			case "personAge":
				return oTrafficContraband.personAge;
			case "personTypeId":
				return oTrafficContraband.personTypeId;
			case "personTypeTitle":
				return oTrafficContraband.personTypeTitle;
			case "personTypeDriver":
				return oTrafficContraband.personTypeDriver;
			case "personTypePassenger":
				return oTrafficContraband.personTypePassenger;
			case "personGenderId":
				return oTrafficContraband.personGenderId;
			case "personGenderTitle":
				return oTrafficContraband.personGenderTitle;
			case "personGenderFemale":
				return oTrafficContraband.personGenderFemale;
			case "personGenderMale":
				return oTrafficContraband.personGenderMale;
			case "personEthnicityId":
				return oTrafficContraband.personEthnicityId;
			case "personEthnicityTitle":
				return oTrafficContraband.personEthnicityTitle;
			case "personRaceId":
				return oTrafficContraband.personRaceId;
			case "personRaceTitle":
				return oTrafficContraband.personRaceTitle;
			case "stopId":
				return oTrafficContraband.stopId;
			case "searchTypeNum":
				return oTrafficContraband.searchTypeNum;
			case "searchTypeTitle":
				return oTrafficContraband.searchTypeTitle;
			case "searchVehicle":
				return oTrafficContraband.searchVehicle;
			case "searchDriver":
				return oTrafficContraband.searchDriver;
			case "searchPassenger":
				return oTrafficContraband.searchPassenger;
			case "searchProperty":
				return oTrafficContraband.searchProperty;
			case "searchVehicleSiezed":
				return oTrafficContraband.searchVehicleSiezed;
			case "searchPersonalPropertySiezed":
				return oTrafficContraband.searchPersonalPropertySiezed;
			case "searchOtherPropertySiezed":
				return oTrafficContraband.searchOtherPropertySiezed;
			case "contrabandOunces":
				return oTrafficContraband.contrabandOunces;
			case "contrabandPounds":
				return oTrafficContraband.contrabandPounds;
			case "contrabandPints":
				return oTrafficContraband.contrabandPints;
			case "contrabandGallons":
				return oTrafficContraband.contrabandGallons;
			case "contrabandDosages":
				return oTrafficContraband.contrabandDosages;
			case "contrabandGrams":
				return oTrafficContraband.contrabandGrams;
			case "contrabandKilos":
				return oTrafficContraband.contrabandKilos;
			case "contrabandMoney":
				return oTrafficContraband.contrabandMoney;
			case "contrabandWeapons":
				return oTrafficContraband.contrabandWeapons;
			case "contrabandDollarAmount":
				return oTrafficContraband.contrabandDollarAmount;
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
				o = attributeTrafficContraband(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeTrafficContraband(String var, Object val) {
		TrafficContraband oTrafficContraband = (TrafficContraband)this;
		switch(var) {
			default:
				return super.attributeCluster(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetTrafficContraband(entityVar,  siteRequest_, o);
	}
	public static Object staticSetTrafficContraband(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "contrabandKey":
			return TrafficContraband.staticSetContrabandKey(siteRequest_, o);
		case "searchId":
			return TrafficContraband.staticSetSearchId(siteRequest_, o);
		case "stateAbbreviation":
			return TrafficContraband.staticSetStateAbbreviation(siteRequest_, o);
		case "agencyTitle":
			return TrafficContraband.staticSetAgencyTitle(siteRequest_, o);
		case "stopDateTime":
			return TrafficContraband.staticSetStopDateTime(siteRequest_, o);
		case "stopPurposeNum":
			return TrafficContraband.staticSetStopPurposeNum(siteRequest_, o);
		case "stopPurposeTitle":
			return TrafficContraband.staticSetStopPurposeTitle(siteRequest_, o);
		case "stopActionNum":
			return TrafficContraband.staticSetStopActionNum(siteRequest_, o);
		case "stopActionTitle":
			return TrafficContraband.staticSetStopActionTitle(siteRequest_, o);
		case "stopDriverArrest":
			return TrafficContraband.staticSetStopDriverArrest(siteRequest_, o);
		case "stopPassengerArrest":
			return TrafficContraband.staticSetStopPassengerArrest(siteRequest_, o);
		case "stopEncounterForce":
			return TrafficContraband.staticSetStopEncounterForce(siteRequest_, o);
		case "stopEngageForce":
			return TrafficContraband.staticSetStopEngageForce(siteRequest_, o);
		case "stopOfficerInjury":
			return TrafficContraband.staticSetStopOfficerInjury(siteRequest_, o);
		case "stopDriverInjury":
			return TrafficContraband.staticSetStopDriverInjury(siteRequest_, o);
		case "stopPassengerInjury":
			return TrafficContraband.staticSetStopPassengerInjury(siteRequest_, o);
		case "stopOfficerId":
			return TrafficContraband.staticSetStopOfficerId(siteRequest_, o);
		case "stopLocationId":
			return TrafficContraband.staticSetStopLocationId(siteRequest_, o);
		case "stopCityId":
			return TrafficContraband.staticSetStopCityId(siteRequest_, o);
		case "personAge":
			return TrafficContraband.staticSetPersonAge(siteRequest_, o);
		case "personTypeId":
			return TrafficContraband.staticSetPersonTypeId(siteRequest_, o);
		case "personTypeTitle":
			return TrafficContraband.staticSetPersonTypeTitle(siteRequest_, o);
		case "personTypeDriver":
			return TrafficContraband.staticSetPersonTypeDriver(siteRequest_, o);
		case "personTypePassenger":
			return TrafficContraband.staticSetPersonTypePassenger(siteRequest_, o);
		case "personGenderId":
			return TrafficContraband.staticSetPersonGenderId(siteRequest_, o);
		case "personGenderTitle":
			return TrafficContraband.staticSetPersonGenderTitle(siteRequest_, o);
		case "personGenderFemale":
			return TrafficContraband.staticSetPersonGenderFemale(siteRequest_, o);
		case "personGenderMale":
			return TrafficContraband.staticSetPersonGenderMale(siteRequest_, o);
		case "personEthnicityId":
			return TrafficContraband.staticSetPersonEthnicityId(siteRequest_, o);
		case "personEthnicityTitle":
			return TrafficContraband.staticSetPersonEthnicityTitle(siteRequest_, o);
		case "personRaceId":
			return TrafficContraband.staticSetPersonRaceId(siteRequest_, o);
		case "personRaceTitle":
			return TrafficContraband.staticSetPersonRaceTitle(siteRequest_, o);
		case "stopId":
			return TrafficContraband.staticSetStopId(siteRequest_, o);
		case "searchTypeNum":
			return TrafficContraband.staticSetSearchTypeNum(siteRequest_, o);
		case "searchTypeTitle":
			return TrafficContraband.staticSetSearchTypeTitle(siteRequest_, o);
		case "searchVehicle":
			return TrafficContraband.staticSetSearchVehicle(siteRequest_, o);
		case "searchDriver":
			return TrafficContraband.staticSetSearchDriver(siteRequest_, o);
		case "searchPassenger":
			return TrafficContraband.staticSetSearchPassenger(siteRequest_, o);
		case "searchProperty":
			return TrafficContraband.staticSetSearchProperty(siteRequest_, o);
		case "searchVehicleSiezed":
			return TrafficContraband.staticSetSearchVehicleSiezed(siteRequest_, o);
		case "searchPersonalPropertySiezed":
			return TrafficContraband.staticSetSearchPersonalPropertySiezed(siteRequest_, o);
		case "searchOtherPropertySiezed":
			return TrafficContraband.staticSetSearchOtherPropertySiezed(siteRequest_, o);
		case "contrabandOunces":
			return TrafficContraband.staticSetContrabandOunces(siteRequest_, o);
		case "contrabandPounds":
			return TrafficContraband.staticSetContrabandPounds(siteRequest_, o);
		case "contrabandPints":
			return TrafficContraband.staticSetContrabandPints(siteRequest_, o);
		case "contrabandGallons":
			return TrafficContraband.staticSetContrabandGallons(siteRequest_, o);
		case "contrabandDosages":
			return TrafficContraband.staticSetContrabandDosages(siteRequest_, o);
		case "contrabandGrams":
			return TrafficContraband.staticSetContrabandGrams(siteRequest_, o);
		case "contrabandKilos":
			return TrafficContraband.staticSetContrabandKilos(siteRequest_, o);
		case "contrabandMoney":
			return TrafficContraband.staticSetContrabandMoney(siteRequest_, o);
		case "contrabandWeapons":
			return TrafficContraband.staticSetContrabandWeapons(siteRequest_, o);
		case "contrabandDollarAmount":
			return TrafficContraband.staticSetContrabandDollarAmount(siteRequest_, o);
			default:
				return Cluster.staticSetCluster(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrTrafficContraband(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrTrafficContraband(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "contrabandKey":
			return TrafficContraband.staticSolrContrabandKey(siteRequest_, (Long)o);
		case "searchId":
			return TrafficContraband.staticSolrSearchId(siteRequest_, (String)o);
		case "stateAbbreviation":
			return TrafficContraband.staticSolrStateAbbreviation(siteRequest_, (String)o);
		case "agencyTitle":
			return TrafficContraband.staticSolrAgencyTitle(siteRequest_, (String)o);
		case "stopDateTime":
			return TrafficContraband.staticSolrStopDateTime(siteRequest_, (ZonedDateTime)o);
		case "stopPurposeNum":
			return TrafficContraband.staticSolrStopPurposeNum(siteRequest_, (Integer)o);
		case "stopPurposeTitle":
			return TrafficContraband.staticSolrStopPurposeTitle(siteRequest_, (String)o);
		case "stopActionNum":
			return TrafficContraband.staticSolrStopActionNum(siteRequest_, (Integer)o);
		case "stopActionTitle":
			return TrafficContraband.staticSolrStopActionTitle(siteRequest_, (String)o);
		case "stopDriverArrest":
			return TrafficContraband.staticSolrStopDriverArrest(siteRequest_, (Boolean)o);
		case "stopPassengerArrest":
			return TrafficContraband.staticSolrStopPassengerArrest(siteRequest_, (Boolean)o);
		case "stopEncounterForce":
			return TrafficContraband.staticSolrStopEncounterForce(siteRequest_, (Boolean)o);
		case "stopEngageForce":
			return TrafficContraband.staticSolrStopEngageForce(siteRequest_, (Boolean)o);
		case "stopOfficerInjury":
			return TrafficContraband.staticSolrStopOfficerInjury(siteRequest_, (Boolean)o);
		case "stopDriverInjury":
			return TrafficContraband.staticSolrStopDriverInjury(siteRequest_, (Boolean)o);
		case "stopPassengerInjury":
			return TrafficContraband.staticSolrStopPassengerInjury(siteRequest_, (Boolean)o);
		case "stopOfficerId":
			return TrafficContraband.staticSolrStopOfficerId(siteRequest_, (String)o);
		case "stopLocationId":
			return TrafficContraband.staticSolrStopLocationId(siteRequest_, (String)o);
		case "stopCityId":
			return TrafficContraband.staticSolrStopCityId(siteRequest_, (String)o);
		case "personAge":
			return TrafficContraband.staticSolrPersonAge(siteRequest_, (Integer)o);
		case "personTypeId":
			return TrafficContraband.staticSolrPersonTypeId(siteRequest_, (String)o);
		case "personTypeTitle":
			return TrafficContraband.staticSolrPersonTypeTitle(siteRequest_, (String)o);
		case "personTypeDriver":
			return TrafficContraband.staticSolrPersonTypeDriver(siteRequest_, (Boolean)o);
		case "personTypePassenger":
			return TrafficContraband.staticSolrPersonTypePassenger(siteRequest_, (Boolean)o);
		case "personGenderId":
			return TrafficContraband.staticSolrPersonGenderId(siteRequest_, (String)o);
		case "personGenderTitle":
			return TrafficContraband.staticSolrPersonGenderTitle(siteRequest_, (String)o);
		case "personGenderFemale":
			return TrafficContraband.staticSolrPersonGenderFemale(siteRequest_, (Boolean)o);
		case "personGenderMale":
			return TrafficContraband.staticSolrPersonGenderMale(siteRequest_, (Boolean)o);
		case "personEthnicityId":
			return TrafficContraband.staticSolrPersonEthnicityId(siteRequest_, (String)o);
		case "personEthnicityTitle":
			return TrafficContraband.staticSolrPersonEthnicityTitle(siteRequest_, (String)o);
		case "personRaceId":
			return TrafficContraband.staticSolrPersonRaceId(siteRequest_, (String)o);
		case "personRaceTitle":
			return TrafficContraband.staticSolrPersonRaceTitle(siteRequest_, (String)o);
		case "stopId":
			return TrafficContraband.staticSolrStopId(siteRequest_, (String)o);
		case "searchTypeNum":
			return TrafficContraband.staticSolrSearchTypeNum(siteRequest_, (Integer)o);
		case "searchTypeTitle":
			return TrafficContraband.staticSolrSearchTypeTitle(siteRequest_, (String)o);
		case "searchVehicle":
			return TrafficContraband.staticSolrSearchVehicle(siteRequest_, (Boolean)o);
		case "searchDriver":
			return TrafficContraband.staticSolrSearchDriver(siteRequest_, (Boolean)o);
		case "searchPassenger":
			return TrafficContraband.staticSolrSearchPassenger(siteRequest_, (Boolean)o);
		case "searchProperty":
			return TrafficContraband.staticSolrSearchProperty(siteRequest_, (Boolean)o);
		case "searchVehicleSiezed":
			return TrafficContraband.staticSolrSearchVehicleSiezed(siteRequest_, (Boolean)o);
		case "searchPersonalPropertySiezed":
			return TrafficContraband.staticSolrSearchPersonalPropertySiezed(siteRequest_, (Boolean)o);
		case "searchOtherPropertySiezed":
			return TrafficContraband.staticSolrSearchOtherPropertySiezed(siteRequest_, (Boolean)o);
		case "contrabandOunces":
			return TrafficContraband.staticSolrContrabandOunces(siteRequest_, (BigDecimal)o);
		case "contrabandPounds":
			return TrafficContraband.staticSolrContrabandPounds(siteRequest_, (BigDecimal)o);
		case "contrabandPints":
			return TrafficContraband.staticSolrContrabandPints(siteRequest_, (BigDecimal)o);
		case "contrabandGallons":
			return TrafficContraband.staticSolrContrabandGallons(siteRequest_, (BigDecimal)o);
		case "contrabandDosages":
			return TrafficContraband.staticSolrContrabandDosages(siteRequest_, (BigDecimal)o);
		case "contrabandGrams":
			return TrafficContraband.staticSolrContrabandGrams(siteRequest_, (BigDecimal)o);
		case "contrabandKilos":
			return TrafficContraband.staticSolrContrabandKilos(siteRequest_, (BigDecimal)o);
		case "contrabandMoney":
			return TrafficContraband.staticSolrContrabandMoney(siteRequest_, (BigDecimal)o);
		case "contrabandWeapons":
			return TrafficContraband.staticSolrContrabandWeapons(siteRequest_, (BigDecimal)o);
		case "contrabandDollarAmount":
			return TrafficContraband.staticSolrContrabandDollarAmount(siteRequest_, (BigDecimal)o);
			default:
				return Cluster.staticSolrCluster(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrTrafficContraband(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrTrafficContraband(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "contrabandKey":
			return TrafficContraband.staticSolrStrContrabandKey(siteRequest_, (Long)o);
		case "searchId":
			return TrafficContraband.staticSolrStrSearchId(siteRequest_, (String)o);
		case "stateAbbreviation":
			return TrafficContraband.staticSolrStrStateAbbreviation(siteRequest_, (String)o);
		case "agencyTitle":
			return TrafficContraband.staticSolrStrAgencyTitle(siteRequest_, (String)o);
		case "stopDateTime":
			return TrafficContraband.staticSolrStrStopDateTime(siteRequest_, (Date)o);
		case "stopPurposeNum":
			return TrafficContraband.staticSolrStrStopPurposeNum(siteRequest_, (Integer)o);
		case "stopPurposeTitle":
			return TrafficContraband.staticSolrStrStopPurposeTitle(siteRequest_, (String)o);
		case "stopActionNum":
			return TrafficContraband.staticSolrStrStopActionNum(siteRequest_, (Integer)o);
		case "stopActionTitle":
			return TrafficContraband.staticSolrStrStopActionTitle(siteRequest_, (String)o);
		case "stopDriverArrest":
			return TrafficContraband.staticSolrStrStopDriverArrest(siteRequest_, (Boolean)o);
		case "stopPassengerArrest":
			return TrafficContraband.staticSolrStrStopPassengerArrest(siteRequest_, (Boolean)o);
		case "stopEncounterForce":
			return TrafficContraband.staticSolrStrStopEncounterForce(siteRequest_, (Boolean)o);
		case "stopEngageForce":
			return TrafficContraband.staticSolrStrStopEngageForce(siteRequest_, (Boolean)o);
		case "stopOfficerInjury":
			return TrafficContraband.staticSolrStrStopOfficerInjury(siteRequest_, (Boolean)o);
		case "stopDriverInjury":
			return TrafficContraband.staticSolrStrStopDriverInjury(siteRequest_, (Boolean)o);
		case "stopPassengerInjury":
			return TrafficContraband.staticSolrStrStopPassengerInjury(siteRequest_, (Boolean)o);
		case "stopOfficerId":
			return TrafficContraband.staticSolrStrStopOfficerId(siteRequest_, (String)o);
		case "stopLocationId":
			return TrafficContraband.staticSolrStrStopLocationId(siteRequest_, (String)o);
		case "stopCityId":
			return TrafficContraband.staticSolrStrStopCityId(siteRequest_, (String)o);
		case "personAge":
			return TrafficContraband.staticSolrStrPersonAge(siteRequest_, (Integer)o);
		case "personTypeId":
			return TrafficContraband.staticSolrStrPersonTypeId(siteRequest_, (String)o);
		case "personTypeTitle":
			return TrafficContraband.staticSolrStrPersonTypeTitle(siteRequest_, (String)o);
		case "personTypeDriver":
			return TrafficContraband.staticSolrStrPersonTypeDriver(siteRequest_, (Boolean)o);
		case "personTypePassenger":
			return TrafficContraband.staticSolrStrPersonTypePassenger(siteRequest_, (Boolean)o);
		case "personGenderId":
			return TrafficContraband.staticSolrStrPersonGenderId(siteRequest_, (String)o);
		case "personGenderTitle":
			return TrafficContraband.staticSolrStrPersonGenderTitle(siteRequest_, (String)o);
		case "personGenderFemale":
			return TrafficContraband.staticSolrStrPersonGenderFemale(siteRequest_, (Boolean)o);
		case "personGenderMale":
			return TrafficContraband.staticSolrStrPersonGenderMale(siteRequest_, (Boolean)o);
		case "personEthnicityId":
			return TrafficContraband.staticSolrStrPersonEthnicityId(siteRequest_, (String)o);
		case "personEthnicityTitle":
			return TrafficContraband.staticSolrStrPersonEthnicityTitle(siteRequest_, (String)o);
		case "personRaceId":
			return TrafficContraband.staticSolrStrPersonRaceId(siteRequest_, (String)o);
		case "personRaceTitle":
			return TrafficContraband.staticSolrStrPersonRaceTitle(siteRequest_, (String)o);
		case "stopId":
			return TrafficContraband.staticSolrStrStopId(siteRequest_, (String)o);
		case "searchTypeNum":
			return TrafficContraband.staticSolrStrSearchTypeNum(siteRequest_, (Integer)o);
		case "searchTypeTitle":
			return TrafficContraband.staticSolrStrSearchTypeTitle(siteRequest_, (String)o);
		case "searchVehicle":
			return TrafficContraband.staticSolrStrSearchVehicle(siteRequest_, (Boolean)o);
		case "searchDriver":
			return TrafficContraband.staticSolrStrSearchDriver(siteRequest_, (Boolean)o);
		case "searchPassenger":
			return TrafficContraband.staticSolrStrSearchPassenger(siteRequest_, (Boolean)o);
		case "searchProperty":
			return TrafficContraband.staticSolrStrSearchProperty(siteRequest_, (Boolean)o);
		case "searchVehicleSiezed":
			return TrafficContraband.staticSolrStrSearchVehicleSiezed(siteRequest_, (Boolean)o);
		case "searchPersonalPropertySiezed":
			return TrafficContraband.staticSolrStrSearchPersonalPropertySiezed(siteRequest_, (Boolean)o);
		case "searchOtherPropertySiezed":
			return TrafficContraband.staticSolrStrSearchOtherPropertySiezed(siteRequest_, (Boolean)o);
		case "contrabandOunces":
			return TrafficContraband.staticSolrStrContrabandOunces(siteRequest_, (Double)o);
		case "contrabandPounds":
			return TrafficContraband.staticSolrStrContrabandPounds(siteRequest_, (Double)o);
		case "contrabandPints":
			return TrafficContraband.staticSolrStrContrabandPints(siteRequest_, (Double)o);
		case "contrabandGallons":
			return TrafficContraband.staticSolrStrContrabandGallons(siteRequest_, (Double)o);
		case "contrabandDosages":
			return TrafficContraband.staticSolrStrContrabandDosages(siteRequest_, (Double)o);
		case "contrabandGrams":
			return TrafficContraband.staticSolrStrContrabandGrams(siteRequest_, (Double)o);
		case "contrabandKilos":
			return TrafficContraband.staticSolrStrContrabandKilos(siteRequest_, (Double)o);
		case "contrabandMoney":
			return TrafficContraband.staticSolrStrContrabandMoney(siteRequest_, (Double)o);
		case "contrabandWeapons":
			return TrafficContraband.staticSolrStrContrabandWeapons(siteRequest_, (Double)o);
		case "contrabandDollarAmount":
			return TrafficContraband.staticSolrStrContrabandDollarAmount(siteRequest_, (Double)o);
			default:
				return Cluster.staticSolrStrCluster(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqTrafficContraband(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqTrafficContraband(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "contrabandKey":
			return TrafficContraband.staticSolrFqContrabandKey(siteRequest_, o);
		case "searchId":
			return TrafficContraband.staticSolrFqSearchId(siteRequest_, o);
		case "stateAbbreviation":
			return TrafficContraband.staticSolrFqStateAbbreviation(siteRequest_, o);
		case "agencyTitle":
			return TrafficContraband.staticSolrFqAgencyTitle(siteRequest_, o);
		case "stopDateTime":
			return TrafficContraband.staticSolrFqStopDateTime(siteRequest_, o);
		case "stopPurposeNum":
			return TrafficContraband.staticSolrFqStopPurposeNum(siteRequest_, o);
		case "stopPurposeTitle":
			return TrafficContraband.staticSolrFqStopPurposeTitle(siteRequest_, o);
		case "stopActionNum":
			return TrafficContraband.staticSolrFqStopActionNum(siteRequest_, o);
		case "stopActionTitle":
			return TrafficContraband.staticSolrFqStopActionTitle(siteRequest_, o);
		case "stopDriverArrest":
			return TrafficContraband.staticSolrFqStopDriverArrest(siteRequest_, o);
		case "stopPassengerArrest":
			return TrafficContraband.staticSolrFqStopPassengerArrest(siteRequest_, o);
		case "stopEncounterForce":
			return TrafficContraband.staticSolrFqStopEncounterForce(siteRequest_, o);
		case "stopEngageForce":
			return TrafficContraband.staticSolrFqStopEngageForce(siteRequest_, o);
		case "stopOfficerInjury":
			return TrafficContraband.staticSolrFqStopOfficerInjury(siteRequest_, o);
		case "stopDriverInjury":
			return TrafficContraband.staticSolrFqStopDriverInjury(siteRequest_, o);
		case "stopPassengerInjury":
			return TrafficContraband.staticSolrFqStopPassengerInjury(siteRequest_, o);
		case "stopOfficerId":
			return TrafficContraband.staticSolrFqStopOfficerId(siteRequest_, o);
		case "stopLocationId":
			return TrafficContraband.staticSolrFqStopLocationId(siteRequest_, o);
		case "stopCityId":
			return TrafficContraband.staticSolrFqStopCityId(siteRequest_, o);
		case "personAge":
			return TrafficContraband.staticSolrFqPersonAge(siteRequest_, o);
		case "personTypeId":
			return TrafficContraband.staticSolrFqPersonTypeId(siteRequest_, o);
		case "personTypeTitle":
			return TrafficContraband.staticSolrFqPersonTypeTitle(siteRequest_, o);
		case "personTypeDriver":
			return TrafficContraband.staticSolrFqPersonTypeDriver(siteRequest_, o);
		case "personTypePassenger":
			return TrafficContraband.staticSolrFqPersonTypePassenger(siteRequest_, o);
		case "personGenderId":
			return TrafficContraband.staticSolrFqPersonGenderId(siteRequest_, o);
		case "personGenderTitle":
			return TrafficContraband.staticSolrFqPersonGenderTitle(siteRequest_, o);
		case "personGenderFemale":
			return TrafficContraband.staticSolrFqPersonGenderFemale(siteRequest_, o);
		case "personGenderMale":
			return TrafficContraband.staticSolrFqPersonGenderMale(siteRequest_, o);
		case "personEthnicityId":
			return TrafficContraband.staticSolrFqPersonEthnicityId(siteRequest_, o);
		case "personEthnicityTitle":
			return TrafficContraband.staticSolrFqPersonEthnicityTitle(siteRequest_, o);
		case "personRaceId":
			return TrafficContraband.staticSolrFqPersonRaceId(siteRequest_, o);
		case "personRaceTitle":
			return TrafficContraband.staticSolrFqPersonRaceTitle(siteRequest_, o);
		case "stopId":
			return TrafficContraband.staticSolrFqStopId(siteRequest_, o);
		case "searchTypeNum":
			return TrafficContraband.staticSolrFqSearchTypeNum(siteRequest_, o);
		case "searchTypeTitle":
			return TrafficContraband.staticSolrFqSearchTypeTitle(siteRequest_, o);
		case "searchVehicle":
			return TrafficContraband.staticSolrFqSearchVehicle(siteRequest_, o);
		case "searchDriver":
			return TrafficContraband.staticSolrFqSearchDriver(siteRequest_, o);
		case "searchPassenger":
			return TrafficContraband.staticSolrFqSearchPassenger(siteRequest_, o);
		case "searchProperty":
			return TrafficContraband.staticSolrFqSearchProperty(siteRequest_, o);
		case "searchVehicleSiezed":
			return TrafficContraband.staticSolrFqSearchVehicleSiezed(siteRequest_, o);
		case "searchPersonalPropertySiezed":
			return TrafficContraband.staticSolrFqSearchPersonalPropertySiezed(siteRequest_, o);
		case "searchOtherPropertySiezed":
			return TrafficContraband.staticSolrFqSearchOtherPropertySiezed(siteRequest_, o);
		case "contrabandOunces":
			return TrafficContraband.staticSolrFqContrabandOunces(siteRequest_, o);
		case "contrabandPounds":
			return TrafficContraband.staticSolrFqContrabandPounds(siteRequest_, o);
		case "contrabandPints":
			return TrafficContraband.staticSolrFqContrabandPints(siteRequest_, o);
		case "contrabandGallons":
			return TrafficContraband.staticSolrFqContrabandGallons(siteRequest_, o);
		case "contrabandDosages":
			return TrafficContraband.staticSolrFqContrabandDosages(siteRequest_, o);
		case "contrabandGrams":
			return TrafficContraband.staticSolrFqContrabandGrams(siteRequest_, o);
		case "contrabandKilos":
			return TrafficContraband.staticSolrFqContrabandKilos(siteRequest_, o);
		case "contrabandMoney":
			return TrafficContraband.staticSolrFqContrabandMoney(siteRequest_, o);
		case "contrabandWeapons":
			return TrafficContraband.staticSolrFqContrabandWeapons(siteRequest_, o);
		case "contrabandDollarAmount":
			return TrafficContraband.staticSolrFqContrabandDollarAmount(siteRequest_, o);
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
					o = defineTrafficContraband(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineTrafficContraband(String var, String val) {
		switch(var.toLowerCase()) {
			case "searchid":
				if(val != null)
					setSearchId(val);
				saves.add("searchId");
				return val;
			case "contrabandounces":
				if(val != null)
					setContrabandOunces(val);
				saves.add("contrabandOunces");
				return val;
			case "contrabandpounds":
				if(val != null)
					setContrabandPounds(val);
				saves.add("contrabandPounds");
				return val;
			case "contrabandpints":
				if(val != null)
					setContrabandPints(val);
				saves.add("contrabandPints");
				return val;
			case "contrabandgallons":
				if(val != null)
					setContrabandGallons(val);
				saves.add("contrabandGallons");
				return val;
			case "contrabanddosages":
				if(val != null)
					setContrabandDosages(val);
				saves.add("contrabandDosages");
				return val;
			case "contrabandgrams":
				if(val != null)
					setContrabandGrams(val);
				saves.add("contrabandGrams");
				return val;
			case "contrabandkilos":
				if(val != null)
					setContrabandKilos(val);
				saves.add("contrabandKilos");
				return val;
			case "contrabandmoney":
				if(val != null)
					setContrabandMoney(val);
				saves.add("contrabandMoney");
				return val;
			case "contrabandweapons":
				if(val != null)
					setContrabandWeapons(val);
				saves.add("contrabandWeapons");
				return val;
			case "contrabanddollaramount":
				if(val != null)
					setContrabandDollarAmount(val);
				saves.add("contrabandDollarAmount");
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
					o = defineTrafficContraband(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineTrafficContraband(String var, Object val) {
		switch(var.toLowerCase()) {
			case "searchid":
				if(val instanceof String)
					setSearchId((String)val);
				saves.add("searchId");
				return val;
			case "contrabandounces":
				if(val instanceof BigDecimal)
					setContrabandOunces((BigDecimal)val);
				saves.add("contrabandOunces");
				return val;
			case "contrabandpounds":
				if(val instanceof BigDecimal)
					setContrabandPounds((BigDecimal)val);
				saves.add("contrabandPounds");
				return val;
			case "contrabandpints":
				if(val instanceof BigDecimal)
					setContrabandPints((BigDecimal)val);
				saves.add("contrabandPints");
				return val;
			case "contrabandgallons":
				if(val instanceof BigDecimal)
					setContrabandGallons((BigDecimal)val);
				saves.add("contrabandGallons");
				return val;
			case "contrabanddosages":
				if(val instanceof BigDecimal)
					setContrabandDosages((BigDecimal)val);
				saves.add("contrabandDosages");
				return val;
			case "contrabandgrams":
				if(val instanceof BigDecimal)
					setContrabandGrams((BigDecimal)val);
				saves.add("contrabandGrams");
				return val;
			case "contrabandkilos":
				if(val instanceof BigDecimal)
					setContrabandKilos((BigDecimal)val);
				saves.add("contrabandKilos");
				return val;
			case "contrabandmoney":
				if(val instanceof BigDecimal)
					setContrabandMoney((BigDecimal)val);
				saves.add("contrabandMoney");
				return val;
			case "contrabandweapons":
				if(val instanceof BigDecimal)
					setContrabandWeapons((BigDecimal)val);
				saves.add("contrabandWeapons");
				return val;
			case "contrabanddollaramount":
				if(val instanceof BigDecimal)
					setContrabandDollarAmount((BigDecimal)val);
				saves.add("contrabandDollarAmount");
				return val;
			default:
				return super.defineCluster(var, val);
		}
	}

	/////////////
	// populate //
	/////////////

	@Override public void populateForClass(SolrDocument solrDocument) {
		populateTrafficContraband(solrDocument);
	}
	public void populateTrafficContraband(SolrDocument solrDocument) {
		TrafficContraband oTrafficContraband = (TrafficContraband)this;
		saves = (List<String>)solrDocument.get("saves_stored_strings");
		if(saves != null) {

			if(saves.contains("contrabandKey")) {
				Long contrabandKey = (Long)solrDocument.get("contrabandKey_stored_long");
				if(contrabandKey != null)
					oTrafficContraband.setContrabandKey(contrabandKey);
			}

			if(saves.contains("searchId")) {
				String searchId = (String)solrDocument.get("searchId_stored_string");
				if(searchId != null)
					oTrafficContraband.setSearchId(searchId);
			}

			if(saves.contains("stateAbbreviation")) {
				String stateAbbreviation = (String)solrDocument.get("stateAbbreviation_stored_string");
				if(stateAbbreviation != null)
					oTrafficContraband.setStateAbbreviation(stateAbbreviation);
			}

			if(saves.contains("agencyTitle")) {
				String agencyTitle = (String)solrDocument.get("agencyTitle_stored_string");
				if(agencyTitle != null)
					oTrafficContraband.setAgencyTitle(agencyTitle);
			}

			if(saves.contains("stopDateTime")) {
				Date stopDateTime = (Date)solrDocument.get("stopDateTime_stored_date");
				if(stopDateTime != null)
					oTrafficContraband.setStopDateTime(stopDateTime);
			}

			if(saves.contains("stopPurposeNum")) {
				Integer stopPurposeNum = (Integer)solrDocument.get("stopPurposeNum_stored_int");
				if(stopPurposeNum != null)
					oTrafficContraband.setStopPurposeNum(stopPurposeNum);
			}

			if(saves.contains("stopPurposeTitle")) {
				String stopPurposeTitle = (String)solrDocument.get("stopPurposeTitle_stored_string");
				if(stopPurposeTitle != null)
					oTrafficContraband.setStopPurposeTitle(stopPurposeTitle);
			}

			if(saves.contains("stopActionNum")) {
				Integer stopActionNum = (Integer)solrDocument.get("stopActionNum_stored_int");
				if(stopActionNum != null)
					oTrafficContraband.setStopActionNum(stopActionNum);
			}

			if(saves.contains("stopActionTitle")) {
				String stopActionTitle = (String)solrDocument.get("stopActionTitle_stored_string");
				if(stopActionTitle != null)
					oTrafficContraband.setStopActionTitle(stopActionTitle);
			}

			if(saves.contains("stopDriverArrest")) {
				Boolean stopDriverArrest = (Boolean)solrDocument.get("stopDriverArrest_stored_boolean");
				if(stopDriverArrest != null)
					oTrafficContraband.setStopDriverArrest(stopDriverArrest);
			}

			if(saves.contains("stopPassengerArrest")) {
				Boolean stopPassengerArrest = (Boolean)solrDocument.get("stopPassengerArrest_stored_boolean");
				if(stopPassengerArrest != null)
					oTrafficContraband.setStopPassengerArrest(stopPassengerArrest);
			}

			if(saves.contains("stopEncounterForce")) {
				Boolean stopEncounterForce = (Boolean)solrDocument.get("stopEncounterForce_stored_boolean");
				if(stopEncounterForce != null)
					oTrafficContraband.setStopEncounterForce(stopEncounterForce);
			}

			if(saves.contains("stopEngageForce")) {
				Boolean stopEngageForce = (Boolean)solrDocument.get("stopEngageForce_stored_boolean");
				if(stopEngageForce != null)
					oTrafficContraband.setStopEngageForce(stopEngageForce);
			}

			if(saves.contains("stopOfficerInjury")) {
				Boolean stopOfficerInjury = (Boolean)solrDocument.get("stopOfficerInjury_stored_boolean");
				if(stopOfficerInjury != null)
					oTrafficContraband.setStopOfficerInjury(stopOfficerInjury);
			}

			if(saves.contains("stopDriverInjury")) {
				Boolean stopDriverInjury = (Boolean)solrDocument.get("stopDriverInjury_stored_boolean");
				if(stopDriverInjury != null)
					oTrafficContraband.setStopDriverInjury(stopDriverInjury);
			}

			if(saves.contains("stopPassengerInjury")) {
				Boolean stopPassengerInjury = (Boolean)solrDocument.get("stopPassengerInjury_stored_boolean");
				if(stopPassengerInjury != null)
					oTrafficContraband.setStopPassengerInjury(stopPassengerInjury);
			}

			if(saves.contains("stopOfficerId")) {
				String stopOfficerId = (String)solrDocument.get("stopOfficerId_stored_string");
				if(stopOfficerId != null)
					oTrafficContraband.setStopOfficerId(stopOfficerId);
			}

			if(saves.contains("stopLocationId")) {
				String stopLocationId = (String)solrDocument.get("stopLocationId_stored_string");
				if(stopLocationId != null)
					oTrafficContraband.setStopLocationId(stopLocationId);
			}

			if(saves.contains("stopCityId")) {
				String stopCityId = (String)solrDocument.get("stopCityId_stored_string");
				if(stopCityId != null)
					oTrafficContraband.setStopCityId(stopCityId);
			}

			if(saves.contains("personAge")) {
				Integer personAge = (Integer)solrDocument.get("personAge_stored_int");
				if(personAge != null)
					oTrafficContraband.setPersonAge(personAge);
			}

			if(saves.contains("personTypeId")) {
				String personTypeId = (String)solrDocument.get("personTypeId_stored_string");
				if(personTypeId != null)
					oTrafficContraband.setPersonTypeId(personTypeId);
			}

			if(saves.contains("personTypeTitle")) {
				String personTypeTitle = (String)solrDocument.get("personTypeTitle_stored_string");
				if(personTypeTitle != null)
					oTrafficContraband.setPersonTypeTitle(personTypeTitle);
			}

			if(saves.contains("personTypeDriver")) {
				Boolean personTypeDriver = (Boolean)solrDocument.get("personTypeDriver_stored_boolean");
				if(personTypeDriver != null)
					oTrafficContraband.setPersonTypeDriver(personTypeDriver);
			}

			if(saves.contains("personTypePassenger")) {
				Boolean personTypePassenger = (Boolean)solrDocument.get("personTypePassenger_stored_boolean");
				if(personTypePassenger != null)
					oTrafficContraband.setPersonTypePassenger(personTypePassenger);
			}

			if(saves.contains("personGenderId")) {
				String personGenderId = (String)solrDocument.get("personGenderId_stored_string");
				if(personGenderId != null)
					oTrafficContraband.setPersonGenderId(personGenderId);
			}

			if(saves.contains("personGenderTitle")) {
				String personGenderTitle = (String)solrDocument.get("personGenderTitle_stored_string");
				if(personGenderTitle != null)
					oTrafficContraband.setPersonGenderTitle(personGenderTitle);
			}

			if(saves.contains("personGenderFemale")) {
				Boolean personGenderFemale = (Boolean)solrDocument.get("personGenderFemale_stored_boolean");
				if(personGenderFemale != null)
					oTrafficContraband.setPersonGenderFemale(personGenderFemale);
			}

			if(saves.contains("personGenderMale")) {
				Boolean personGenderMale = (Boolean)solrDocument.get("personGenderMale_stored_boolean");
				if(personGenderMale != null)
					oTrafficContraband.setPersonGenderMale(personGenderMale);
			}

			if(saves.contains("personEthnicityId")) {
				String personEthnicityId = (String)solrDocument.get("personEthnicityId_stored_string");
				if(personEthnicityId != null)
					oTrafficContraband.setPersonEthnicityId(personEthnicityId);
			}

			if(saves.contains("personEthnicityTitle")) {
				String personEthnicityTitle = (String)solrDocument.get("personEthnicityTitle_stored_string");
				if(personEthnicityTitle != null)
					oTrafficContraband.setPersonEthnicityTitle(personEthnicityTitle);
			}

			if(saves.contains("personRaceId")) {
				String personRaceId = (String)solrDocument.get("personRaceId_stored_string");
				if(personRaceId != null)
					oTrafficContraband.setPersonRaceId(personRaceId);
			}

			if(saves.contains("personRaceTitle")) {
				String personRaceTitle = (String)solrDocument.get("personRaceTitle_stored_string");
				if(personRaceTitle != null)
					oTrafficContraband.setPersonRaceTitle(personRaceTitle);
			}

			if(saves.contains("stopId")) {
				String stopId = (String)solrDocument.get("stopId_stored_string");
				if(stopId != null)
					oTrafficContraband.setStopId(stopId);
			}

			if(saves.contains("searchTypeNum")) {
				Integer searchTypeNum = (Integer)solrDocument.get("searchTypeNum_stored_int");
				if(searchTypeNum != null)
					oTrafficContraband.setSearchTypeNum(searchTypeNum);
			}

			if(saves.contains("searchTypeTitle")) {
				String searchTypeTitle = (String)solrDocument.get("searchTypeTitle_stored_string");
				if(searchTypeTitle != null)
					oTrafficContraband.setSearchTypeTitle(searchTypeTitle);
			}

			if(saves.contains("searchVehicle")) {
				Boolean searchVehicle = (Boolean)solrDocument.get("searchVehicle_stored_boolean");
				if(searchVehicle != null)
					oTrafficContraband.setSearchVehicle(searchVehicle);
			}

			if(saves.contains("searchDriver")) {
				Boolean searchDriver = (Boolean)solrDocument.get("searchDriver_stored_boolean");
				if(searchDriver != null)
					oTrafficContraband.setSearchDriver(searchDriver);
			}

			if(saves.contains("searchPassenger")) {
				Boolean searchPassenger = (Boolean)solrDocument.get("searchPassenger_stored_boolean");
				if(searchPassenger != null)
					oTrafficContraband.setSearchPassenger(searchPassenger);
			}

			if(saves.contains("searchProperty")) {
				Boolean searchProperty = (Boolean)solrDocument.get("searchProperty_stored_boolean");
				if(searchProperty != null)
					oTrafficContraband.setSearchProperty(searchProperty);
			}

			if(saves.contains("searchVehicleSiezed")) {
				Boolean searchVehicleSiezed = (Boolean)solrDocument.get("searchVehicleSiezed_stored_boolean");
				if(searchVehicleSiezed != null)
					oTrafficContraband.setSearchVehicleSiezed(searchVehicleSiezed);
			}

			if(saves.contains("searchPersonalPropertySiezed")) {
				Boolean searchPersonalPropertySiezed = (Boolean)solrDocument.get("searchPersonalPropertySiezed_stored_boolean");
				if(searchPersonalPropertySiezed != null)
					oTrafficContraband.setSearchPersonalPropertySiezed(searchPersonalPropertySiezed);
			}

			if(saves.contains("searchOtherPropertySiezed")) {
				Boolean searchOtherPropertySiezed = (Boolean)solrDocument.get("searchOtherPropertySiezed_stored_boolean");
				if(searchOtherPropertySiezed != null)
					oTrafficContraband.setSearchOtherPropertySiezed(searchOtherPropertySiezed);
			}

			if(saves.contains("contrabandOunces")) {
				Double contrabandOunces = (Double)solrDocument.get("contrabandOunces_stored_double");
				if(contrabandOunces != null)
					oTrafficContraband.setContrabandOunces(contrabandOunces);
			}

			if(saves.contains("contrabandPounds")) {
				Double contrabandPounds = (Double)solrDocument.get("contrabandPounds_stored_double");
				if(contrabandPounds != null)
					oTrafficContraband.setContrabandPounds(contrabandPounds);
			}

			if(saves.contains("contrabandPints")) {
				Double contrabandPints = (Double)solrDocument.get("contrabandPints_stored_double");
				if(contrabandPints != null)
					oTrafficContraband.setContrabandPints(contrabandPints);
			}

			if(saves.contains("contrabandGallons")) {
				Double contrabandGallons = (Double)solrDocument.get("contrabandGallons_stored_double");
				if(contrabandGallons != null)
					oTrafficContraband.setContrabandGallons(contrabandGallons);
			}

			if(saves.contains("contrabandDosages")) {
				Double contrabandDosages = (Double)solrDocument.get("contrabandDosages_stored_double");
				if(contrabandDosages != null)
					oTrafficContraband.setContrabandDosages(contrabandDosages);
			}

			if(saves.contains("contrabandGrams")) {
				Double contrabandGrams = (Double)solrDocument.get("contrabandGrams_stored_double");
				if(contrabandGrams != null)
					oTrafficContraband.setContrabandGrams(contrabandGrams);
			}

			if(saves.contains("contrabandKilos")) {
				Double contrabandKilos = (Double)solrDocument.get("contrabandKilos_stored_double");
				if(contrabandKilos != null)
					oTrafficContraband.setContrabandKilos(contrabandKilos);
			}

			if(saves.contains("contrabandMoney")) {
				Double contrabandMoney = (Double)solrDocument.get("contrabandMoney_stored_double");
				if(contrabandMoney != null)
					oTrafficContraband.setContrabandMoney(contrabandMoney);
			}

			if(saves.contains("contrabandWeapons")) {
				Double contrabandWeapons = (Double)solrDocument.get("contrabandWeapons_stored_double");
				if(contrabandWeapons != null)
					oTrafficContraband.setContrabandWeapons(contrabandWeapons);
			}

			if(saves.contains("contrabandDollarAmount")) {
				Double contrabandDollarAmount = (Double)solrDocument.get("contrabandDollarAmount_stored_double");
				if(contrabandDollarAmount != null)
					oTrafficContraband.setContrabandDollarAmount(contrabandDollarAmount);
			}
		}

		super.populateCluster(solrDocument);
	}

	public void indexTrafficContraband(SolrInputDocument document) {
		if(contrabandKey != null) {
			document.addField("contrabandKey_indexed_long", contrabandKey);
			document.addField("contrabandKey_stored_long", contrabandKey);
		}
		if(searchId != null) {
			document.addField("searchId_indexed_string", searchId);
			document.addField("searchId_stored_string", searchId);
		}
		if(stateAbbreviation != null) {
			document.addField("stateAbbreviation_indexed_string", stateAbbreviation);
			document.addField("stateAbbreviation_stored_string", stateAbbreviation);
		}
		if(agencyTitle != null) {
			document.addField("agencyTitle_indexed_string", agencyTitle);
			document.addField("agencyTitle_stored_string", agencyTitle);
		}
		if(stopDateTime != null) {
			document.addField("stopDateTime_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(stopDateTime.toInstant(), ZoneId.of("UTC"))));
			document.addField("stopDateTime_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(stopDateTime.toInstant(), ZoneId.of("UTC"))));
		}
		if(stopPurposeNum != null) {
			document.addField("stopPurposeNum_indexed_int", stopPurposeNum);
			document.addField("stopPurposeNum_stored_int", stopPurposeNum);
		}
		if(stopPurposeTitle != null) {
			document.addField("stopPurposeTitle_indexed_string", stopPurposeTitle);
			document.addField("stopPurposeTitle_stored_string", stopPurposeTitle);
		}
		if(stopActionNum != null) {
			document.addField("stopActionNum_indexed_int", stopActionNum);
			document.addField("stopActionNum_stored_int", stopActionNum);
		}
		if(stopActionTitle != null) {
			document.addField("stopActionTitle_indexed_string", stopActionTitle);
			document.addField("stopActionTitle_stored_string", stopActionTitle);
		}
		if(stopDriverArrest != null) {
			document.addField("stopDriverArrest_indexed_boolean", stopDriverArrest);
			document.addField("stopDriverArrest_stored_boolean", stopDriverArrest);
		}
		if(stopPassengerArrest != null) {
			document.addField("stopPassengerArrest_indexed_boolean", stopPassengerArrest);
			document.addField("stopPassengerArrest_stored_boolean", stopPassengerArrest);
		}
		if(stopEncounterForce != null) {
			document.addField("stopEncounterForce_indexed_boolean", stopEncounterForce);
			document.addField("stopEncounterForce_stored_boolean", stopEncounterForce);
		}
		if(stopEngageForce != null) {
			document.addField("stopEngageForce_indexed_boolean", stopEngageForce);
			document.addField("stopEngageForce_stored_boolean", stopEngageForce);
		}
		if(stopOfficerInjury != null) {
			document.addField("stopOfficerInjury_indexed_boolean", stopOfficerInjury);
			document.addField("stopOfficerInjury_stored_boolean", stopOfficerInjury);
		}
		if(stopDriverInjury != null) {
			document.addField("stopDriverInjury_indexed_boolean", stopDriverInjury);
			document.addField("stopDriverInjury_stored_boolean", stopDriverInjury);
		}
		if(stopPassengerInjury != null) {
			document.addField("stopPassengerInjury_indexed_boolean", stopPassengerInjury);
			document.addField("stopPassengerInjury_stored_boolean", stopPassengerInjury);
		}
		if(stopOfficerId != null) {
			document.addField("stopOfficerId_indexed_string", stopOfficerId);
			document.addField("stopOfficerId_stored_string", stopOfficerId);
		}
		if(stopLocationId != null) {
			document.addField("stopLocationId_indexed_string", stopLocationId);
			document.addField("stopLocationId_stored_string", stopLocationId);
		}
		if(stopCityId != null) {
			document.addField("stopCityId_indexed_string", stopCityId);
			document.addField("stopCityId_stored_string", stopCityId);
		}
		if(personAge != null) {
			document.addField("personAge_indexed_int", personAge);
			document.addField("personAge_stored_int", personAge);
		}
		if(personTypeId != null) {
			document.addField("personTypeId_indexed_string", personTypeId);
			document.addField("personTypeId_stored_string", personTypeId);
		}
		if(personTypeTitle != null) {
			document.addField("personTypeTitle_indexed_string", personTypeTitle);
			document.addField("personTypeTitle_stored_string", personTypeTitle);
		}
		if(personTypeDriver != null) {
			document.addField("personTypeDriver_indexed_boolean", personTypeDriver);
			document.addField("personTypeDriver_stored_boolean", personTypeDriver);
		}
		if(personTypePassenger != null) {
			document.addField("personTypePassenger_indexed_boolean", personTypePassenger);
			document.addField("personTypePassenger_stored_boolean", personTypePassenger);
		}
		if(personGenderId != null) {
			document.addField("personGenderId_indexed_string", personGenderId);
			document.addField("personGenderId_stored_string", personGenderId);
		}
		if(personGenderTitle != null) {
			document.addField("personGenderTitle_indexed_string", personGenderTitle);
			document.addField("personGenderTitle_stored_string", personGenderTitle);
		}
		if(personGenderFemale != null) {
			document.addField("personGenderFemale_indexed_boolean", personGenderFemale);
			document.addField("personGenderFemale_stored_boolean", personGenderFemale);
		}
		if(personGenderMale != null) {
			document.addField("personGenderMale_indexed_boolean", personGenderMale);
			document.addField("personGenderMale_stored_boolean", personGenderMale);
		}
		if(personEthnicityId != null) {
			document.addField("personEthnicityId_indexed_string", personEthnicityId);
			document.addField("personEthnicityId_stored_string", personEthnicityId);
		}
		if(personEthnicityTitle != null) {
			document.addField("personEthnicityTitle_indexed_string", personEthnicityTitle);
			document.addField("personEthnicityTitle_stored_string", personEthnicityTitle);
		}
		if(personRaceId != null) {
			document.addField("personRaceId_indexed_string", personRaceId);
			document.addField("personRaceId_stored_string", personRaceId);
		}
		if(personRaceTitle != null) {
			document.addField("personRaceTitle_indexed_string", personRaceTitle);
			document.addField("personRaceTitle_stored_string", personRaceTitle);
		}
		if(stopId != null) {
			document.addField("stopId_indexed_string", stopId);
			document.addField("stopId_stored_string", stopId);
		}
		if(searchTypeNum != null) {
			document.addField("searchTypeNum_indexed_int", searchTypeNum);
			document.addField("searchTypeNum_stored_int", searchTypeNum);
		}
		if(searchTypeTitle != null) {
			document.addField("searchTypeTitle_indexed_string", searchTypeTitle);
			document.addField("searchTypeTitle_stored_string", searchTypeTitle);
		}
		if(searchVehicle != null) {
			document.addField("searchVehicle_indexed_boolean", searchVehicle);
			document.addField("searchVehicle_stored_boolean", searchVehicle);
		}
		if(searchDriver != null) {
			document.addField("searchDriver_indexed_boolean", searchDriver);
			document.addField("searchDriver_stored_boolean", searchDriver);
		}
		if(searchPassenger != null) {
			document.addField("searchPassenger_indexed_boolean", searchPassenger);
			document.addField("searchPassenger_stored_boolean", searchPassenger);
		}
		if(searchProperty != null) {
			document.addField("searchProperty_indexed_boolean", searchProperty);
			document.addField("searchProperty_stored_boolean", searchProperty);
		}
		if(searchVehicleSiezed != null) {
			document.addField("searchVehicleSiezed_indexed_boolean", searchVehicleSiezed);
			document.addField("searchVehicleSiezed_stored_boolean", searchVehicleSiezed);
		}
		if(searchPersonalPropertySiezed != null) {
			document.addField("searchPersonalPropertySiezed_indexed_boolean", searchPersonalPropertySiezed);
			document.addField("searchPersonalPropertySiezed_stored_boolean", searchPersonalPropertySiezed);
		}
		if(searchOtherPropertySiezed != null) {
			document.addField("searchOtherPropertySiezed_indexed_boolean", searchOtherPropertySiezed);
			document.addField("searchOtherPropertySiezed_stored_boolean", searchOtherPropertySiezed);
		}
		if(contrabandOunces != null) {
			document.addField("contrabandOunces_indexed_double", contrabandOunces.doubleValue());
			document.addField("contrabandOunces_stored_double", contrabandOunces.doubleValue());
		}
		if(contrabandPounds != null) {
			document.addField("contrabandPounds_indexed_double", contrabandPounds.doubleValue());
			document.addField("contrabandPounds_stored_double", contrabandPounds.doubleValue());
		}
		if(contrabandPints != null) {
			document.addField("contrabandPints_indexed_double", contrabandPints.doubleValue());
			document.addField("contrabandPints_stored_double", contrabandPints.doubleValue());
		}
		if(contrabandGallons != null) {
			document.addField("contrabandGallons_indexed_double", contrabandGallons.doubleValue());
			document.addField("contrabandGallons_stored_double", contrabandGallons.doubleValue());
		}
		if(contrabandDosages != null) {
			document.addField("contrabandDosages_indexed_double", contrabandDosages.doubleValue());
			document.addField("contrabandDosages_stored_double", contrabandDosages.doubleValue());
		}
		if(contrabandGrams != null) {
			document.addField("contrabandGrams_indexed_double", contrabandGrams.doubleValue());
			document.addField("contrabandGrams_stored_double", contrabandGrams.doubleValue());
		}
		if(contrabandKilos != null) {
			document.addField("contrabandKilos_indexed_double", contrabandKilos.doubleValue());
			document.addField("contrabandKilos_stored_double", contrabandKilos.doubleValue());
		}
		if(contrabandMoney != null) {
			document.addField("contrabandMoney_indexed_double", contrabandMoney.doubleValue());
			document.addField("contrabandMoney_stored_double", contrabandMoney.doubleValue());
		}
		if(contrabandWeapons != null) {
			document.addField("contrabandWeapons_indexed_double", contrabandWeapons.doubleValue());
			document.addField("contrabandWeapons_stored_double", contrabandWeapons.doubleValue());
		}
		if(contrabandDollarAmount != null) {
			document.addField("contrabandDollarAmount_indexed_double", contrabandDollarAmount.doubleValue());
			document.addField("contrabandDollarAmount_stored_double", contrabandDollarAmount.doubleValue());
		}
		super.indexCluster(document);

	}

	public static String varIndexedTrafficContraband(String entityVar) {
		switch(entityVar) {
			case "contrabandKey":
				return "contrabandKey_indexed_long";
			case "searchId":
				return "searchId_indexed_string";
			case "stateAbbreviation":
				return "stateAbbreviation_indexed_string";
			case "agencyTitle":
				return "agencyTitle_indexed_string";
			case "stopDateTime":
				return "stopDateTime_indexed_date";
			case "stopPurposeNum":
				return "stopPurposeNum_indexed_int";
			case "stopPurposeTitle":
				return "stopPurposeTitle_indexed_string";
			case "stopActionNum":
				return "stopActionNum_indexed_int";
			case "stopActionTitle":
				return "stopActionTitle_indexed_string";
			case "stopDriverArrest":
				return "stopDriverArrest_indexed_boolean";
			case "stopPassengerArrest":
				return "stopPassengerArrest_indexed_boolean";
			case "stopEncounterForce":
				return "stopEncounterForce_indexed_boolean";
			case "stopEngageForce":
				return "stopEngageForce_indexed_boolean";
			case "stopOfficerInjury":
				return "stopOfficerInjury_indexed_boolean";
			case "stopDriverInjury":
				return "stopDriverInjury_indexed_boolean";
			case "stopPassengerInjury":
				return "stopPassengerInjury_indexed_boolean";
			case "stopOfficerId":
				return "stopOfficerId_indexed_string";
			case "stopLocationId":
				return "stopLocationId_indexed_string";
			case "stopCityId":
				return "stopCityId_indexed_string";
			case "personAge":
				return "personAge_indexed_int";
			case "personTypeId":
				return "personTypeId_indexed_string";
			case "personTypeTitle":
				return "personTypeTitle_indexed_string";
			case "personTypeDriver":
				return "personTypeDriver_indexed_boolean";
			case "personTypePassenger":
				return "personTypePassenger_indexed_boolean";
			case "personGenderId":
				return "personGenderId_indexed_string";
			case "personGenderTitle":
				return "personGenderTitle_indexed_string";
			case "personGenderFemale":
				return "personGenderFemale_indexed_boolean";
			case "personGenderMale":
				return "personGenderMale_indexed_boolean";
			case "personEthnicityId":
				return "personEthnicityId_indexed_string";
			case "personEthnicityTitle":
				return "personEthnicityTitle_indexed_string";
			case "personRaceId":
				return "personRaceId_indexed_string";
			case "personRaceTitle":
				return "personRaceTitle_indexed_string";
			case "stopId":
				return "stopId_indexed_string";
			case "searchTypeNum":
				return "searchTypeNum_indexed_int";
			case "searchTypeTitle":
				return "searchTypeTitle_indexed_string";
			case "searchVehicle":
				return "searchVehicle_indexed_boolean";
			case "searchDriver":
				return "searchDriver_indexed_boolean";
			case "searchPassenger":
				return "searchPassenger_indexed_boolean";
			case "searchProperty":
				return "searchProperty_indexed_boolean";
			case "searchVehicleSiezed":
				return "searchVehicleSiezed_indexed_boolean";
			case "searchPersonalPropertySiezed":
				return "searchPersonalPropertySiezed_indexed_boolean";
			case "searchOtherPropertySiezed":
				return "searchOtherPropertySiezed_indexed_boolean";
			case "contrabandOunces":
				return "contrabandOunces_indexed_double";
			case "contrabandPounds":
				return "contrabandPounds_indexed_double";
			case "contrabandPints":
				return "contrabandPints_indexed_double";
			case "contrabandGallons":
				return "contrabandGallons_indexed_double";
			case "contrabandDosages":
				return "contrabandDosages_indexed_double";
			case "contrabandGrams":
				return "contrabandGrams_indexed_double";
			case "contrabandKilos":
				return "contrabandKilos_indexed_double";
			case "contrabandMoney":
				return "contrabandMoney_indexed_double";
			case "contrabandWeapons":
				return "contrabandWeapons_indexed_double";
			case "contrabandDollarAmount":
				return "contrabandDollarAmount_indexed_double";
			default:
				return Cluster.varIndexedCluster(entityVar);
		}
	}

	public static String varSearchTrafficContraband(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSearchCluster(entityVar);
		}
	}

	public static String varSuggestedTrafficContraband(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSuggestedCluster(entityVar);
		}
	}

	/////////////
	// store //
	/////////////

	@Override public void storeForClass(SolrDocument solrDocument) {
		storeTrafficContraband(solrDocument);
	}
	public void storeTrafficContraband(SolrDocument solrDocument) {
		TrafficContraband oTrafficContraband = (TrafficContraband)this;

		oTrafficContraband.setContrabandKey(Optional.ofNullable(solrDocument.get("contrabandKey_stored_long")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setSearchId(Optional.ofNullable(solrDocument.get("searchId_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setStateAbbreviation(Optional.ofNullable(solrDocument.get("stateAbbreviation_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setAgencyTitle(Optional.ofNullable(solrDocument.get("agencyTitle_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setStopDateTime(Optional.ofNullable(solrDocument.get("stopDateTime_stored_date")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setStopPurposeNum(Optional.ofNullable(solrDocument.get("stopPurposeNum_stored_int")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setStopPurposeTitle(Optional.ofNullable(solrDocument.get("stopPurposeTitle_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setStopActionNum(Optional.ofNullable(solrDocument.get("stopActionNum_stored_int")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setStopActionTitle(Optional.ofNullable(solrDocument.get("stopActionTitle_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setStopDriverArrest(Optional.ofNullable(solrDocument.get("stopDriverArrest_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setStopPassengerArrest(Optional.ofNullable(solrDocument.get("stopPassengerArrest_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setStopEncounterForce(Optional.ofNullable(solrDocument.get("stopEncounterForce_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setStopEngageForce(Optional.ofNullable(solrDocument.get("stopEngageForce_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setStopOfficerInjury(Optional.ofNullable(solrDocument.get("stopOfficerInjury_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setStopDriverInjury(Optional.ofNullable(solrDocument.get("stopDriverInjury_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setStopPassengerInjury(Optional.ofNullable(solrDocument.get("stopPassengerInjury_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setStopOfficerId(Optional.ofNullable(solrDocument.get("stopOfficerId_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setStopLocationId(Optional.ofNullable(solrDocument.get("stopLocationId_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setStopCityId(Optional.ofNullable(solrDocument.get("stopCityId_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setPersonAge(Optional.ofNullable(solrDocument.get("personAge_stored_int")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setPersonTypeId(Optional.ofNullable(solrDocument.get("personTypeId_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setPersonTypeTitle(Optional.ofNullable(solrDocument.get("personTypeTitle_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setPersonTypeDriver(Optional.ofNullable(solrDocument.get("personTypeDriver_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setPersonTypePassenger(Optional.ofNullable(solrDocument.get("personTypePassenger_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setPersonGenderId(Optional.ofNullable(solrDocument.get("personGenderId_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setPersonGenderTitle(Optional.ofNullable(solrDocument.get("personGenderTitle_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setPersonGenderFemale(Optional.ofNullable(solrDocument.get("personGenderFemale_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setPersonGenderMale(Optional.ofNullable(solrDocument.get("personGenderMale_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setPersonEthnicityId(Optional.ofNullable(solrDocument.get("personEthnicityId_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setPersonEthnicityTitle(Optional.ofNullable(solrDocument.get("personEthnicityTitle_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setPersonRaceId(Optional.ofNullable(solrDocument.get("personRaceId_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setPersonRaceTitle(Optional.ofNullable(solrDocument.get("personRaceTitle_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setStopId(Optional.ofNullable(solrDocument.get("stopId_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setSearchTypeNum(Optional.ofNullable(solrDocument.get("searchTypeNum_stored_int")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setSearchTypeTitle(Optional.ofNullable(solrDocument.get("searchTypeTitle_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setSearchVehicle(Optional.ofNullable(solrDocument.get("searchVehicle_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setSearchDriver(Optional.ofNullable(solrDocument.get("searchDriver_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setSearchPassenger(Optional.ofNullable(solrDocument.get("searchPassenger_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setSearchProperty(Optional.ofNullable(solrDocument.get("searchProperty_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setSearchVehicleSiezed(Optional.ofNullable(solrDocument.get("searchVehicleSiezed_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setSearchPersonalPropertySiezed(Optional.ofNullable(solrDocument.get("searchPersonalPropertySiezed_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setSearchOtherPropertySiezed(Optional.ofNullable(solrDocument.get("searchOtherPropertySiezed_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setContrabandOunces(Optional.ofNullable(solrDocument.get("contrabandOunces_stored_double")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setContrabandPounds(Optional.ofNullable(solrDocument.get("contrabandPounds_stored_double")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setContrabandPints(Optional.ofNullable(solrDocument.get("contrabandPints_stored_double")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setContrabandGallons(Optional.ofNullable(solrDocument.get("contrabandGallons_stored_double")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setContrabandDosages(Optional.ofNullable(solrDocument.get("contrabandDosages_stored_double")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setContrabandGrams(Optional.ofNullable(solrDocument.get("contrabandGrams_stored_double")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setContrabandKilos(Optional.ofNullable(solrDocument.get("contrabandKilos_stored_double")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setContrabandMoney(Optional.ofNullable(solrDocument.get("contrabandMoney_stored_double")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setContrabandWeapons(Optional.ofNullable(solrDocument.get("contrabandWeapons_stored_double")).map(v -> v.toString()).orElse(null));
		oTrafficContraband.setContrabandDollarAmount(Optional.ofNullable(solrDocument.get("contrabandDollarAmount_stored_double")).map(v -> v.toString()).orElse(null));

		super.storeCluster(solrDocument);
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestTrafficContraband() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof TrafficContraband) {
			TrafficContraband original = (TrafficContraband)o;
			if(!Objects.equals(contrabandKey, original.getContrabandKey()))
				apiRequest.addVars("contrabandKey");
			if(!Objects.equals(searchId, original.getSearchId()))
				apiRequest.addVars("searchId");
			if(!Objects.equals(stateAbbreviation, original.getStateAbbreviation()))
				apiRequest.addVars("stateAbbreviation");
			if(!Objects.equals(agencyTitle, original.getAgencyTitle()))
				apiRequest.addVars("agencyTitle");
			if(!Objects.equals(stopDateTime, original.getStopDateTime()))
				apiRequest.addVars("stopDateTime");
			if(!Objects.equals(stopPurposeNum, original.getStopPurposeNum()))
				apiRequest.addVars("stopPurposeNum");
			if(!Objects.equals(stopPurposeTitle, original.getStopPurposeTitle()))
				apiRequest.addVars("stopPurposeTitle");
			if(!Objects.equals(stopActionNum, original.getStopActionNum()))
				apiRequest.addVars("stopActionNum");
			if(!Objects.equals(stopActionTitle, original.getStopActionTitle()))
				apiRequest.addVars("stopActionTitle");
			if(!Objects.equals(stopDriverArrest, original.getStopDriverArrest()))
				apiRequest.addVars("stopDriverArrest");
			if(!Objects.equals(stopPassengerArrest, original.getStopPassengerArrest()))
				apiRequest.addVars("stopPassengerArrest");
			if(!Objects.equals(stopEncounterForce, original.getStopEncounterForce()))
				apiRequest.addVars("stopEncounterForce");
			if(!Objects.equals(stopEngageForce, original.getStopEngageForce()))
				apiRequest.addVars("stopEngageForce");
			if(!Objects.equals(stopOfficerInjury, original.getStopOfficerInjury()))
				apiRequest.addVars("stopOfficerInjury");
			if(!Objects.equals(stopDriverInjury, original.getStopDriverInjury()))
				apiRequest.addVars("stopDriverInjury");
			if(!Objects.equals(stopPassengerInjury, original.getStopPassengerInjury()))
				apiRequest.addVars("stopPassengerInjury");
			if(!Objects.equals(stopOfficerId, original.getStopOfficerId()))
				apiRequest.addVars("stopOfficerId");
			if(!Objects.equals(stopLocationId, original.getStopLocationId()))
				apiRequest.addVars("stopLocationId");
			if(!Objects.equals(stopCityId, original.getStopCityId()))
				apiRequest.addVars("stopCityId");
			if(!Objects.equals(personAge, original.getPersonAge()))
				apiRequest.addVars("personAge");
			if(!Objects.equals(personTypeId, original.getPersonTypeId()))
				apiRequest.addVars("personTypeId");
			if(!Objects.equals(personTypeTitle, original.getPersonTypeTitle()))
				apiRequest.addVars("personTypeTitle");
			if(!Objects.equals(personTypeDriver, original.getPersonTypeDriver()))
				apiRequest.addVars("personTypeDriver");
			if(!Objects.equals(personTypePassenger, original.getPersonTypePassenger()))
				apiRequest.addVars("personTypePassenger");
			if(!Objects.equals(personGenderId, original.getPersonGenderId()))
				apiRequest.addVars("personGenderId");
			if(!Objects.equals(personGenderTitle, original.getPersonGenderTitle()))
				apiRequest.addVars("personGenderTitle");
			if(!Objects.equals(personGenderFemale, original.getPersonGenderFemale()))
				apiRequest.addVars("personGenderFemale");
			if(!Objects.equals(personGenderMale, original.getPersonGenderMale()))
				apiRequest.addVars("personGenderMale");
			if(!Objects.equals(personEthnicityId, original.getPersonEthnicityId()))
				apiRequest.addVars("personEthnicityId");
			if(!Objects.equals(personEthnicityTitle, original.getPersonEthnicityTitle()))
				apiRequest.addVars("personEthnicityTitle");
			if(!Objects.equals(personRaceId, original.getPersonRaceId()))
				apiRequest.addVars("personRaceId");
			if(!Objects.equals(personRaceTitle, original.getPersonRaceTitle()))
				apiRequest.addVars("personRaceTitle");
			if(!Objects.equals(stopId, original.getStopId()))
				apiRequest.addVars("stopId");
			if(!Objects.equals(searchTypeNum, original.getSearchTypeNum()))
				apiRequest.addVars("searchTypeNum");
			if(!Objects.equals(searchTypeTitle, original.getSearchTypeTitle()))
				apiRequest.addVars("searchTypeTitle");
			if(!Objects.equals(searchVehicle, original.getSearchVehicle()))
				apiRequest.addVars("searchVehicle");
			if(!Objects.equals(searchDriver, original.getSearchDriver()))
				apiRequest.addVars("searchDriver");
			if(!Objects.equals(searchPassenger, original.getSearchPassenger()))
				apiRequest.addVars("searchPassenger");
			if(!Objects.equals(searchProperty, original.getSearchProperty()))
				apiRequest.addVars("searchProperty");
			if(!Objects.equals(searchVehicleSiezed, original.getSearchVehicleSiezed()))
				apiRequest.addVars("searchVehicleSiezed");
			if(!Objects.equals(searchPersonalPropertySiezed, original.getSearchPersonalPropertySiezed()))
				apiRequest.addVars("searchPersonalPropertySiezed");
			if(!Objects.equals(searchOtherPropertySiezed, original.getSearchOtherPropertySiezed()))
				apiRequest.addVars("searchOtherPropertySiezed");
			if(!Objects.equals(contrabandOunces, original.getContrabandOunces()))
				apiRequest.addVars("contrabandOunces");
			if(!Objects.equals(contrabandPounds, original.getContrabandPounds()))
				apiRequest.addVars("contrabandPounds");
			if(!Objects.equals(contrabandPints, original.getContrabandPints()))
				apiRequest.addVars("contrabandPints");
			if(!Objects.equals(contrabandGallons, original.getContrabandGallons()))
				apiRequest.addVars("contrabandGallons");
			if(!Objects.equals(contrabandDosages, original.getContrabandDosages()))
				apiRequest.addVars("contrabandDosages");
			if(!Objects.equals(contrabandGrams, original.getContrabandGrams()))
				apiRequest.addVars("contrabandGrams");
			if(!Objects.equals(contrabandKilos, original.getContrabandKilos()))
				apiRequest.addVars("contrabandKilos");
			if(!Objects.equals(contrabandMoney, original.getContrabandMoney()))
				apiRequest.addVars("contrabandMoney");
			if(!Objects.equals(contrabandWeapons, original.getContrabandWeapons()))
				apiRequest.addVars("contrabandWeapons");
			if(!Objects.equals(contrabandDollarAmount, original.getContrabandDollarAmount()))
				apiRequest.addVars("contrabandDollarAmount");
			super.apiRequestCluster();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), contrabandKey, searchId, stateAbbreviation, agencyTitle, stopDateTime, stopPurposeNum, stopPurposeTitle, stopActionNum, stopActionTitle, stopDriverArrest, stopPassengerArrest, stopEncounterForce, stopEngageForce, stopOfficerInjury, stopDriverInjury, stopPassengerInjury, stopOfficerId, stopLocationId, stopCityId, personAge, personTypeId, personTypeTitle, personTypeDriver, personTypePassenger, personGenderId, personGenderTitle, personGenderFemale, personGenderMale, personEthnicityId, personEthnicityTitle, personRaceId, personRaceTitle, stopId, searchTypeNum, searchTypeTitle, searchVehicle, searchDriver, searchPassenger, searchProperty, searchVehicleSiezed, searchPersonalPropertySiezed, searchOtherPropertySiezed, contrabandOunces, contrabandPounds, contrabandPints, contrabandGallons, contrabandDosages, contrabandGrams, contrabandKilos, contrabandMoney, contrabandWeapons, contrabandDollarAmount);
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof TrafficContraband))
			return false;
		TrafficContraband that = (TrafficContraband)o;
		return super.equals(o)
				&& Objects.equals( contrabandKey, that.contrabandKey )
				&& Objects.equals( searchId, that.searchId )
				&& Objects.equals( stateAbbreviation, that.stateAbbreviation )
				&& Objects.equals( agencyTitle, that.agencyTitle )
				&& Objects.equals( stopDateTime, that.stopDateTime )
				&& Objects.equals( stopPurposeNum, that.stopPurposeNum )
				&& Objects.equals( stopPurposeTitle, that.stopPurposeTitle )
				&& Objects.equals( stopActionNum, that.stopActionNum )
				&& Objects.equals( stopActionTitle, that.stopActionTitle )
				&& Objects.equals( stopDriverArrest, that.stopDriverArrest )
				&& Objects.equals( stopPassengerArrest, that.stopPassengerArrest )
				&& Objects.equals( stopEncounterForce, that.stopEncounterForce )
				&& Objects.equals( stopEngageForce, that.stopEngageForce )
				&& Objects.equals( stopOfficerInjury, that.stopOfficerInjury )
				&& Objects.equals( stopDriverInjury, that.stopDriverInjury )
				&& Objects.equals( stopPassengerInjury, that.stopPassengerInjury )
				&& Objects.equals( stopOfficerId, that.stopOfficerId )
				&& Objects.equals( stopLocationId, that.stopLocationId )
				&& Objects.equals( stopCityId, that.stopCityId )
				&& Objects.equals( personAge, that.personAge )
				&& Objects.equals( personTypeId, that.personTypeId )
				&& Objects.equals( personTypeTitle, that.personTypeTitle )
				&& Objects.equals( personTypeDriver, that.personTypeDriver )
				&& Objects.equals( personTypePassenger, that.personTypePassenger )
				&& Objects.equals( personGenderId, that.personGenderId )
				&& Objects.equals( personGenderTitle, that.personGenderTitle )
				&& Objects.equals( personGenderFemale, that.personGenderFemale )
				&& Objects.equals( personGenderMale, that.personGenderMale )
				&& Objects.equals( personEthnicityId, that.personEthnicityId )
				&& Objects.equals( personEthnicityTitle, that.personEthnicityTitle )
				&& Objects.equals( personRaceId, that.personRaceId )
				&& Objects.equals( personRaceTitle, that.personRaceTitle )
				&& Objects.equals( stopId, that.stopId )
				&& Objects.equals( searchTypeNum, that.searchTypeNum )
				&& Objects.equals( searchTypeTitle, that.searchTypeTitle )
				&& Objects.equals( searchVehicle, that.searchVehicle )
				&& Objects.equals( searchDriver, that.searchDriver )
				&& Objects.equals( searchPassenger, that.searchPassenger )
				&& Objects.equals( searchProperty, that.searchProperty )
				&& Objects.equals( searchVehicleSiezed, that.searchVehicleSiezed )
				&& Objects.equals( searchPersonalPropertySiezed, that.searchPersonalPropertySiezed )
				&& Objects.equals( searchOtherPropertySiezed, that.searchOtherPropertySiezed )
				&& Objects.equals( contrabandOunces, that.contrabandOunces )
				&& Objects.equals( contrabandPounds, that.contrabandPounds )
				&& Objects.equals( contrabandPints, that.contrabandPints )
				&& Objects.equals( contrabandGallons, that.contrabandGallons )
				&& Objects.equals( contrabandDosages, that.contrabandDosages )
				&& Objects.equals( contrabandGrams, that.contrabandGrams )
				&& Objects.equals( contrabandKilos, that.contrabandKilos )
				&& Objects.equals( contrabandMoney, that.contrabandMoney )
				&& Objects.equals( contrabandWeapons, that.contrabandWeapons )
				&& Objects.equals( contrabandDollarAmount, that.contrabandDollarAmount );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("TrafficContraband { ");
		sb.append( "contrabandKey: " ).append(contrabandKey);
		sb.append( ", searchId: \"" ).append(searchId).append( "\"" );
		sb.append( ", stateAbbreviation: \"" ).append(stateAbbreviation).append( "\"" );
		sb.append( ", agencyTitle: \"" ).append(agencyTitle).append( "\"" );
		sb.append( ", stopDateTime: " ).append(stopDateTime);
		sb.append( ", stopPurposeNum: " ).append(stopPurposeNum);
		sb.append( ", stopPurposeTitle: \"" ).append(stopPurposeTitle).append( "\"" );
		sb.append( ", stopActionNum: " ).append(stopActionNum);
		sb.append( ", stopActionTitle: \"" ).append(stopActionTitle).append( "\"" );
		sb.append( ", stopDriverArrest: " ).append(stopDriverArrest);
		sb.append( ", stopPassengerArrest: " ).append(stopPassengerArrest);
		sb.append( ", stopEncounterForce: " ).append(stopEncounterForce);
		sb.append( ", stopEngageForce: " ).append(stopEngageForce);
		sb.append( ", stopOfficerInjury: " ).append(stopOfficerInjury);
		sb.append( ", stopDriverInjury: " ).append(stopDriverInjury);
		sb.append( ", stopPassengerInjury: " ).append(stopPassengerInjury);
		sb.append( ", stopOfficerId: \"" ).append(stopOfficerId).append( "\"" );
		sb.append( ", stopLocationId: \"" ).append(stopLocationId).append( "\"" );
		sb.append( ", stopCityId: \"" ).append(stopCityId).append( "\"" );
		sb.append( ", personAge: " ).append(personAge);
		sb.append( ", personTypeId: \"" ).append(personTypeId).append( "\"" );
		sb.append( ", personTypeTitle: \"" ).append(personTypeTitle).append( "\"" );
		sb.append( ", personTypeDriver: " ).append(personTypeDriver);
		sb.append( ", personTypePassenger: " ).append(personTypePassenger);
		sb.append( ", personGenderId: \"" ).append(personGenderId).append( "\"" );
		sb.append( ", personGenderTitle: \"" ).append(personGenderTitle).append( "\"" );
		sb.append( ", personGenderFemale: " ).append(personGenderFemale);
		sb.append( ", personGenderMale: " ).append(personGenderMale);
		sb.append( ", personEthnicityId: \"" ).append(personEthnicityId).append( "\"" );
		sb.append( ", personEthnicityTitle: \"" ).append(personEthnicityTitle).append( "\"" );
		sb.append( ", personRaceId: \"" ).append(personRaceId).append( "\"" );
		sb.append( ", personRaceTitle: \"" ).append(personRaceTitle).append( "\"" );
		sb.append( ", stopId: \"" ).append(stopId).append( "\"" );
		sb.append( ", searchTypeNum: " ).append(searchTypeNum);
		sb.append( ", searchTypeTitle: \"" ).append(searchTypeTitle).append( "\"" );
		sb.append( ", searchVehicle: " ).append(searchVehicle);
		sb.append( ", searchDriver: " ).append(searchDriver);
		sb.append( ", searchPassenger: " ).append(searchPassenger);
		sb.append( ", searchProperty: " ).append(searchProperty);
		sb.append( ", searchVehicleSiezed: " ).append(searchVehicleSiezed);
		sb.append( ", searchPersonalPropertySiezed: " ).append(searchPersonalPropertySiezed);
		sb.append( ", searchOtherPropertySiezed: " ).append(searchOtherPropertySiezed);
		sb.append( ", contrabandOunces: " ).append(contrabandOunces);
		sb.append( ", contrabandPounds: " ).append(contrabandPounds);
		sb.append( ", contrabandPints: " ).append(contrabandPints);
		sb.append( ", contrabandGallons: " ).append(contrabandGallons);
		sb.append( ", contrabandDosages: " ).append(contrabandDosages);
		sb.append( ", contrabandGrams: " ).append(contrabandGrams);
		sb.append( ", contrabandKilos: " ).append(contrabandKilos);
		sb.append( ", contrabandMoney: " ).append(contrabandMoney);
		sb.append( ", contrabandWeapons: " ).append(contrabandWeapons);
		sb.append( ", contrabandDollarAmount: " ).append(contrabandDollarAmount);
		sb.append(" }");
		return sb.toString();
	}

	public static final String VAR_contrabandKey = "contrabandKey";
	public static final String VAR_searchId = "searchId";
	public static final String VAR_trafficSearchSearch = "trafficSearchSearch";
	public static final String VAR_trafficSearch_ = "trafficSearch_";
	public static final String VAR_stateAbbreviation = "stateAbbreviation";
	public static final String VAR_agencyTitle = "agencyTitle";
	public static final String VAR_stopDateTime = "stopDateTime";
	public static final String VAR_stopPurposeNum = "stopPurposeNum";
	public static final String VAR_stopPurposeTitle = "stopPurposeTitle";
	public static final String VAR_stopActionNum = "stopActionNum";
	public static final String VAR_stopActionTitle = "stopActionTitle";
	public static final String VAR_stopDriverArrest = "stopDriverArrest";
	public static final String VAR_stopPassengerArrest = "stopPassengerArrest";
	public static final String VAR_stopEncounterForce = "stopEncounterForce";
	public static final String VAR_stopEngageForce = "stopEngageForce";
	public static final String VAR_stopOfficerInjury = "stopOfficerInjury";
	public static final String VAR_stopDriverInjury = "stopDriverInjury";
	public static final String VAR_stopPassengerInjury = "stopPassengerInjury";
	public static final String VAR_stopOfficerId = "stopOfficerId";
	public static final String VAR_stopLocationId = "stopLocationId";
	public static final String VAR_stopCityId = "stopCityId";
	public static final String VAR_personAge = "personAge";
	public static final String VAR_personTypeId = "personTypeId";
	public static final String VAR_personTypeTitle = "personTypeTitle";
	public static final String VAR_personTypeDriver = "personTypeDriver";
	public static final String VAR_personTypePassenger = "personTypePassenger";
	public static final String VAR_personGenderId = "personGenderId";
	public static final String VAR_personGenderTitle = "personGenderTitle";
	public static final String VAR_personGenderFemale = "personGenderFemale";
	public static final String VAR_personGenderMale = "personGenderMale";
	public static final String VAR_personEthnicityId = "personEthnicityId";
	public static final String VAR_personEthnicityTitle = "personEthnicityTitle";
	public static final String VAR_personRaceId = "personRaceId";
	public static final String VAR_personRaceTitle = "personRaceTitle";
	public static final String VAR_stopId = "stopId";
	public static final String VAR_searchTypeNum = "searchTypeNum";
	public static final String VAR_searchTypeTitle = "searchTypeTitle";
	public static final String VAR_searchVehicle = "searchVehicle";
	public static final String VAR_searchDriver = "searchDriver";
	public static final String VAR_searchPassenger = "searchPassenger";
	public static final String VAR_searchProperty = "searchProperty";
	public static final String VAR_searchVehicleSiezed = "searchVehicleSiezed";
	public static final String VAR_searchPersonalPropertySiezed = "searchPersonalPropertySiezed";
	public static final String VAR_searchOtherPropertySiezed = "searchOtherPropertySiezed";
	public static final String VAR_contrabandOunces = "contrabandOunces";
	public static final String VAR_contrabandPounds = "contrabandPounds";
	public static final String VAR_contrabandPints = "contrabandPints";
	public static final String VAR_contrabandGallons = "contrabandGallons";
	public static final String VAR_contrabandDosages = "contrabandDosages";
	public static final String VAR_contrabandGrams = "contrabandGrams";
	public static final String VAR_contrabandKilos = "contrabandKilos";
	public static final String VAR_contrabandMoney = "contrabandMoney";
	public static final String VAR_contrabandWeapons = "contrabandWeapons";
	public static final String VAR_contrabandDollarAmount = "contrabandDollarAmount";
}
