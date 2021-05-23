package com.opendatapolicing.enus.trafficsearch;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Date;
import java.time.ZonedDateTime;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import java.lang.Integer;
import java.lang.Long;
import java.util.Locale;
import java.util.Map;
import io.vertx.core.json.JsonObject;
import com.opendatapolicing.enus.java.ZonedDateTimeSerializer;
import java.time.ZoneOffset;
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
import com.opendatapolicing.enus.trafficperson.TrafficPerson;
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
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class TrafficSearchGen<DEV> extends Cluster {
	protected static final Logger LOG = LoggerFactory.getLogger(TrafficSearch.class);

	public static final List<String> ROLES = Arrays.asList("SiteService");
	public static final List<String> ROLE_READS = Arrays.asList("");

	//////////////////////
	// trafficSearchKey //
	//////////////////////

	/**	 The entity trafficSearchKey
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long trafficSearchKey;
	@JsonIgnore
	public Wrap<Long> trafficSearchKeyWrap = new Wrap<Long>().var("trafficSearchKey").o(trafficSearchKey);

	/**	<br/> The entity trafficSearchKey
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:trafficSearchKey">Find the entity trafficSearchKey in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _trafficSearchKey(Wrap<Long> c);

	public Long getTrafficSearchKey() {
		return trafficSearchKey;
	}

	public void setTrafficSearchKey(Long trafficSearchKey) {
		this.trafficSearchKey = trafficSearchKey;
		this.trafficSearchKeyWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setTrafficSearchKey(String o) {
		this.trafficSearchKey = TrafficSearch.staticSetTrafficSearchKey(siteRequest_, o);
		this.trafficSearchKeyWrap.alreadyInitialized = true;
	}
	public static Long staticSetTrafficSearchKey(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected TrafficSearch trafficSearchKeyInit() {
		if(!trafficSearchKeyWrap.alreadyInitialized) {
			_trafficSearchKey(trafficSearchKeyWrap);
			if(trafficSearchKey == null)
				setTrafficSearchKey(trafficSearchKeyWrap.o);
			trafficSearchKeyWrap.o(null);
		}
		trafficSearchKeyWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Long staticSolrTrafficSearchKey(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrTrafficSearchKey(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqTrafficSearchKey(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrTrafficSearchKey(siteRequest_, TrafficSearch.staticSolrTrafficSearchKey(siteRequest_, TrafficSearch.staticSetTrafficSearchKey(siteRequest_, o)));
	}

	public Long solrTrafficSearchKey() {
		return TrafficSearch.staticSolrTrafficSearchKey(siteRequest_, trafficSearchKey);
	}

	public String strTrafficSearchKey() {
		return trafficSearchKey == null ? "" : trafficSearchKey.toString();
	}

	public Long sqlTrafficSearchKey() {
		return trafficSearchKey;
	}

	public String jsonTrafficSearchKey() {
		return trafficSearchKey == null ? "" : trafficSearchKey.toString();
	}

	//////////////
	// personId //
	//////////////

	/**	 The entity personId
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String personId;
	@JsonIgnore
	public Wrap<String> personIdWrap = new Wrap<String>().var("personId").o(personId);

	/**	<br/> The entity personId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personId">Find the entity personId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personId(Wrap<String> w);

	public String getPersonId() {
		return personId;
	}
	public void setPersonId(String o) {
		this.personId = TrafficSearch.staticSetPersonId(siteRequest_, o);
		this.personIdWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficSearch personIdInit() {
		if(!personIdWrap.alreadyInitialized) {
			_personId(personIdWrap);
			if(personId == null)
				setPersonId(personIdWrap.o);
			personIdWrap.o(null);
		}
		personIdWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static String staticSolrPersonId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrPersonId(siteRequest_, TrafficSearch.staticSolrPersonId(siteRequest_, TrafficSearch.staticSetPersonId(siteRequest_, o)));
	}

	public String solrPersonId() {
		return TrafficSearch.staticSolrPersonId(siteRequest_, personId);
	}

	public String strPersonId() {
		return personId == null ? "" : personId;
	}

	public String sqlPersonId() {
		return personId;
	}

	public String jsonPersonId() {
		return personId == null ? "" : personId;
	}

	/////////////////////////
	// trafficPersonSearch //
	/////////////////////////

	/**	 The entity trafficPersonSearch
	 *	 is defined as null before being initialized. 
	 */
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected SearchList<TrafficPerson> trafficPersonSearch;
	@JsonIgnore
	public Wrap<SearchList<TrafficPerson>> trafficPersonSearchWrap = new Wrap<SearchList<TrafficPerson>>().var("trafficPersonSearch").o(trafficPersonSearch);

	/**	<br/> The entity trafficPersonSearch
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:trafficPersonSearch">Find the entity trafficPersonSearch in Solr</a>
	 * <br/>
	 * @param promise is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _trafficPersonSearch(Promise<SearchList<TrafficPerson>> promise);

	public SearchList<TrafficPerson> getTrafficPersonSearch() {
		return trafficPersonSearch;
	}

	public void setTrafficPersonSearch(SearchList<TrafficPerson> trafficPersonSearch) {
		this.trafficPersonSearch = trafficPersonSearch;
		this.trafficPersonSearchWrap.alreadyInitialized = true;
	}
	public static SearchList<TrafficPerson> staticSetTrafficPersonSearch(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected Future<SearchList<TrafficPerson>> trafficPersonSearchPromise() {
		Promise<SearchList<TrafficPerson>> promise = Promise.promise();
		if(!trafficPersonSearchWrap.alreadyInitialized) {
			Promise<SearchList<TrafficPerson>> promise2 = Promise.promise();
			_trafficPersonSearch(promise2);
			promise2.future().onSuccess(o -> {
				if(o != null && trafficPersonSearch == null) {
					o.promiseDeepForClass(siteRequest_).onSuccess(a -> {
						setTrafficPersonSearch(o);
						trafficPersonSearchWrap.alreadyInitialized(true);
						promise.complete(o);
					}).onFailure(ex -> {
						promise.fail(ex);
					});
				} else {
					trafficPersonSearchWrap.alreadyInitialized(true);
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
	// trafficPerson_ //
	////////////////////

	/**	 The entity trafficPerson_
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected TrafficPerson trafficPerson_;
	@JsonIgnore
	public Wrap<TrafficPerson> trafficPerson_Wrap = new Wrap<TrafficPerson>().var("trafficPerson_").o(trafficPerson_);

	/**	<br/> The entity trafficPerson_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:trafficPerson_">Find the entity trafficPerson_ in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _trafficPerson_(Wrap<TrafficPerson> w);

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
	protected TrafficSearch trafficPerson_Init() {
		if(!trafficPerson_Wrap.alreadyInitialized) {
			_trafficPerson_(trafficPerson_Wrap);
			if(trafficPerson_ == null)
				setTrafficPerson_(trafficPerson_Wrap.o);
			trafficPerson_Wrap.o(null);
		}
		trafficPerson_Wrap.alreadyInitialized(true);
		return (TrafficSearch)this;
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stateAbbreviation">Find the entity stateAbbreviation in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stateAbbreviation(Wrap<String> w);

	public String getStateAbbreviation() {
		return stateAbbreviation;
	}
	public void setStateAbbreviation(String o) {
		this.stateAbbreviation = TrafficSearch.staticSetStateAbbreviation(siteRequest_, o);
		this.stateAbbreviationWrap.alreadyInitialized = true;
	}
	public static String staticSetStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficSearch stateAbbreviationInit() {
		if(!stateAbbreviationWrap.alreadyInitialized) {
			_stateAbbreviation(stateAbbreviationWrap);
			if(stateAbbreviation == null)
				setStateAbbreviation(stateAbbreviationWrap.o);
			stateAbbreviationWrap.o(null);
		}
		stateAbbreviationWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static String staticSolrStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrStateAbbreviation(siteRequest_, TrafficSearch.staticSolrStateAbbreviation(siteRequest_, TrafficSearch.staticSetStateAbbreviation(siteRequest_, o)));
	}

	public String solrStateAbbreviation() {
		return TrafficSearch.staticSolrStateAbbreviation(siteRequest_, stateAbbreviation);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:agencyTitle">Find the entity agencyTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _agencyTitle(Wrap<String> w);

	public String getAgencyTitle() {
		return agencyTitle;
	}
	public void setAgencyTitle(String o) {
		this.agencyTitle = TrafficSearch.staticSetAgencyTitle(siteRequest_, o);
		this.agencyTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficSearch agencyTitleInit() {
		if(!agencyTitleWrap.alreadyInitialized) {
			_agencyTitle(agencyTitleWrap);
			if(agencyTitle == null)
				setAgencyTitle(agencyTitleWrap.o);
			agencyTitleWrap.o(null);
		}
		agencyTitleWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static String staticSolrAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrAgencyTitle(siteRequest_, TrafficSearch.staticSolrAgencyTitle(siteRequest_, TrafficSearch.staticSetAgencyTitle(siteRequest_, o)));
	}

	public String solrAgencyTitle() {
		return TrafficSearch.staticSolrAgencyTitle(siteRequest_, agencyTitle);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopDateTime">Find the entity stopDateTime in Solr</a>
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
		this.stopDateTime = TrafficSearch.staticSetStopDateTime(siteRequest_, o);
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
	protected TrafficSearch stopDateTimeInit() {
		if(!stopDateTimeWrap.alreadyInitialized) {
			_stopDateTime(stopDateTimeWrap);
			if(stopDateTime == null)
				setStopDateTime(stopDateTimeWrap.o);
			stopDateTimeWrap.o(null);
		}
		stopDateTimeWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Date staticSolrStopDateTime(SiteRequestEnUS siteRequest_, ZonedDateTime o) {
		return o == null ? null : Date.from(o.toInstant());
	}

	public static String staticSolrStrStopDateTime(SiteRequestEnUS siteRequest_, Date o) {
		return "\"" + DateTimeFormatter.ISO_DATE_TIME.format(o.toInstant().atOffset(ZoneOffset.UTC)) + "\"";
	}

	public static String staticSolrFqStopDateTime(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrStopDateTime(siteRequest_, TrafficSearch.staticSolrStopDateTime(siteRequest_, TrafficSearch.staticSetStopDateTime(siteRequest_, o)));
	}

	public Date solrStopDateTime() {
		return TrafficSearch.staticSolrStopDateTime(siteRequest_, stopDateTime);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopPurposeNum">Find the entity stopPurposeNum in Solr</a>
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
		this.stopPurposeNum = TrafficSearch.staticSetStopPurposeNum(siteRequest_, o);
		this.stopPurposeNumWrap.alreadyInitialized = true;
	}
	public static Integer staticSetStopPurposeNum(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected TrafficSearch stopPurposeNumInit() {
		if(!stopPurposeNumWrap.alreadyInitialized) {
			_stopPurposeNum(stopPurposeNumWrap);
			if(stopPurposeNum == null)
				setStopPurposeNum(stopPurposeNumWrap.o);
			stopPurposeNumWrap.o(null);
		}
		stopPurposeNumWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Integer staticSolrStopPurposeNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrStopPurposeNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopPurposeNum(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrStopPurposeNum(siteRequest_, TrafficSearch.staticSolrStopPurposeNum(siteRequest_, TrafficSearch.staticSetStopPurposeNum(siteRequest_, o)));
	}

	public Integer solrStopPurposeNum() {
		return TrafficSearch.staticSolrStopPurposeNum(siteRequest_, stopPurposeNum);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopPurposeTitle">Find the entity stopPurposeTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopPurposeTitle(Wrap<String> w);

	public String getStopPurposeTitle() {
		return stopPurposeTitle;
	}
	public void setStopPurposeTitle(String o) {
		this.stopPurposeTitle = TrafficSearch.staticSetStopPurposeTitle(siteRequest_, o);
		this.stopPurposeTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetStopPurposeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficSearch stopPurposeTitleInit() {
		if(!stopPurposeTitleWrap.alreadyInitialized) {
			_stopPurposeTitle(stopPurposeTitleWrap);
			if(stopPurposeTitle == null)
				setStopPurposeTitle(stopPurposeTitleWrap.o);
			stopPurposeTitleWrap.o(null);
		}
		stopPurposeTitleWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static String staticSolrStopPurposeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopPurposeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopPurposeTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrStopPurposeTitle(siteRequest_, TrafficSearch.staticSolrStopPurposeTitle(siteRequest_, TrafficSearch.staticSetStopPurposeTitle(siteRequest_, o)));
	}

	public String solrStopPurposeTitle() {
		return TrafficSearch.staticSolrStopPurposeTitle(siteRequest_, stopPurposeTitle);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopActionNum">Find the entity stopActionNum in Solr</a>
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
		this.stopActionNum = TrafficSearch.staticSetStopActionNum(siteRequest_, o);
		this.stopActionNumWrap.alreadyInitialized = true;
	}
	public static Integer staticSetStopActionNum(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected TrafficSearch stopActionNumInit() {
		if(!stopActionNumWrap.alreadyInitialized) {
			_stopActionNum(stopActionNumWrap);
			if(stopActionNum == null)
				setStopActionNum(stopActionNumWrap.o);
			stopActionNumWrap.o(null);
		}
		stopActionNumWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Integer staticSolrStopActionNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrStopActionNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopActionNum(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrStopActionNum(siteRequest_, TrafficSearch.staticSolrStopActionNum(siteRequest_, TrafficSearch.staticSetStopActionNum(siteRequest_, o)));
	}

	public Integer solrStopActionNum() {
		return TrafficSearch.staticSolrStopActionNum(siteRequest_, stopActionNum);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopActionTitle">Find the entity stopActionTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopActionTitle(Wrap<String> w);

	public String getStopActionTitle() {
		return stopActionTitle;
	}
	public void setStopActionTitle(String o) {
		this.stopActionTitle = TrafficSearch.staticSetStopActionTitle(siteRequest_, o);
		this.stopActionTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetStopActionTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficSearch stopActionTitleInit() {
		if(!stopActionTitleWrap.alreadyInitialized) {
			_stopActionTitle(stopActionTitleWrap);
			if(stopActionTitle == null)
				setStopActionTitle(stopActionTitleWrap.o);
			stopActionTitleWrap.o(null);
		}
		stopActionTitleWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static String staticSolrStopActionTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopActionTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopActionTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrStopActionTitle(siteRequest_, TrafficSearch.staticSolrStopActionTitle(siteRequest_, TrafficSearch.staticSetStopActionTitle(siteRequest_, o)));
	}

	public String solrStopActionTitle() {
		return TrafficSearch.staticSolrStopActionTitle(siteRequest_, stopActionTitle);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopDriverArrest">Find the entity stopDriverArrest in Solr</a>
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
		this.stopDriverArrest = TrafficSearch.staticSetStopDriverArrest(siteRequest_, o);
		this.stopDriverArrestWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopDriverArrest(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficSearch stopDriverArrestInit() {
		if(!stopDriverArrestWrap.alreadyInitialized) {
			_stopDriverArrest(stopDriverArrestWrap);
			if(stopDriverArrest == null)
				setStopDriverArrest(stopDriverArrestWrap.o);
			stopDriverArrestWrap.o(null);
		}
		stopDriverArrestWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Boolean staticSolrStopDriverArrest(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopDriverArrest(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopDriverArrest(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrStopDriverArrest(siteRequest_, TrafficSearch.staticSolrStopDriverArrest(siteRequest_, TrafficSearch.staticSetStopDriverArrest(siteRequest_, o)));
	}

	public Boolean solrStopDriverArrest() {
		return TrafficSearch.staticSolrStopDriverArrest(siteRequest_, stopDriverArrest);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopPassengerArrest">Find the entity stopPassengerArrest in Solr</a>
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
		this.stopPassengerArrest = TrafficSearch.staticSetStopPassengerArrest(siteRequest_, o);
		this.stopPassengerArrestWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopPassengerArrest(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficSearch stopPassengerArrestInit() {
		if(!stopPassengerArrestWrap.alreadyInitialized) {
			_stopPassengerArrest(stopPassengerArrestWrap);
			if(stopPassengerArrest == null)
				setStopPassengerArrest(stopPassengerArrestWrap.o);
			stopPassengerArrestWrap.o(null);
		}
		stopPassengerArrestWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Boolean staticSolrStopPassengerArrest(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopPassengerArrest(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopPassengerArrest(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrStopPassengerArrest(siteRequest_, TrafficSearch.staticSolrStopPassengerArrest(siteRequest_, TrafficSearch.staticSetStopPassengerArrest(siteRequest_, o)));
	}

	public Boolean solrStopPassengerArrest() {
		return TrafficSearch.staticSolrStopPassengerArrest(siteRequest_, stopPassengerArrest);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopEncounterForce">Find the entity stopEncounterForce in Solr</a>
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
		this.stopEncounterForce = TrafficSearch.staticSetStopEncounterForce(siteRequest_, o);
		this.stopEncounterForceWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopEncounterForce(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficSearch stopEncounterForceInit() {
		if(!stopEncounterForceWrap.alreadyInitialized) {
			_stopEncounterForce(stopEncounterForceWrap);
			if(stopEncounterForce == null)
				setStopEncounterForce(stopEncounterForceWrap.o);
			stopEncounterForceWrap.o(null);
		}
		stopEncounterForceWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Boolean staticSolrStopEncounterForce(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopEncounterForce(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopEncounterForce(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrStopEncounterForce(siteRequest_, TrafficSearch.staticSolrStopEncounterForce(siteRequest_, TrafficSearch.staticSetStopEncounterForce(siteRequest_, o)));
	}

	public Boolean solrStopEncounterForce() {
		return TrafficSearch.staticSolrStopEncounterForce(siteRequest_, stopEncounterForce);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopEngageForce">Find the entity stopEngageForce in Solr</a>
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
		this.stopEngageForce = TrafficSearch.staticSetStopEngageForce(siteRequest_, o);
		this.stopEngageForceWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopEngageForce(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficSearch stopEngageForceInit() {
		if(!stopEngageForceWrap.alreadyInitialized) {
			_stopEngageForce(stopEngageForceWrap);
			if(stopEngageForce == null)
				setStopEngageForce(stopEngageForceWrap.o);
			stopEngageForceWrap.o(null);
		}
		stopEngageForceWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Boolean staticSolrStopEngageForce(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopEngageForce(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopEngageForce(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrStopEngageForce(siteRequest_, TrafficSearch.staticSolrStopEngageForce(siteRequest_, TrafficSearch.staticSetStopEngageForce(siteRequest_, o)));
	}

	public Boolean solrStopEngageForce() {
		return TrafficSearch.staticSolrStopEngageForce(siteRequest_, stopEngageForce);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopOfficerInjury">Find the entity stopOfficerInjury in Solr</a>
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
		this.stopOfficerInjury = TrafficSearch.staticSetStopOfficerInjury(siteRequest_, o);
		this.stopOfficerInjuryWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopOfficerInjury(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficSearch stopOfficerInjuryInit() {
		if(!stopOfficerInjuryWrap.alreadyInitialized) {
			_stopOfficerInjury(stopOfficerInjuryWrap);
			if(stopOfficerInjury == null)
				setStopOfficerInjury(stopOfficerInjuryWrap.o);
			stopOfficerInjuryWrap.o(null);
		}
		stopOfficerInjuryWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Boolean staticSolrStopOfficerInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopOfficerInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopOfficerInjury(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrStopOfficerInjury(siteRequest_, TrafficSearch.staticSolrStopOfficerInjury(siteRequest_, TrafficSearch.staticSetStopOfficerInjury(siteRequest_, o)));
	}

	public Boolean solrStopOfficerInjury() {
		return TrafficSearch.staticSolrStopOfficerInjury(siteRequest_, stopOfficerInjury);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopDriverInjury">Find the entity stopDriverInjury in Solr</a>
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
		this.stopDriverInjury = TrafficSearch.staticSetStopDriverInjury(siteRequest_, o);
		this.stopDriverInjuryWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopDriverInjury(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficSearch stopDriverInjuryInit() {
		if(!stopDriverInjuryWrap.alreadyInitialized) {
			_stopDriverInjury(stopDriverInjuryWrap);
			if(stopDriverInjury == null)
				setStopDriverInjury(stopDriverInjuryWrap.o);
			stopDriverInjuryWrap.o(null);
		}
		stopDriverInjuryWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Boolean staticSolrStopDriverInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopDriverInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopDriverInjury(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrStopDriverInjury(siteRequest_, TrafficSearch.staticSolrStopDriverInjury(siteRequest_, TrafficSearch.staticSetStopDriverInjury(siteRequest_, o)));
	}

	public Boolean solrStopDriverInjury() {
		return TrafficSearch.staticSolrStopDriverInjury(siteRequest_, stopDriverInjury);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopPassengerInjury">Find the entity stopPassengerInjury in Solr</a>
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
		this.stopPassengerInjury = TrafficSearch.staticSetStopPassengerInjury(siteRequest_, o);
		this.stopPassengerInjuryWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopPassengerInjury(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficSearch stopPassengerInjuryInit() {
		if(!stopPassengerInjuryWrap.alreadyInitialized) {
			_stopPassengerInjury(stopPassengerInjuryWrap);
			if(stopPassengerInjury == null)
				setStopPassengerInjury(stopPassengerInjuryWrap.o);
			stopPassengerInjuryWrap.o(null);
		}
		stopPassengerInjuryWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Boolean staticSolrStopPassengerInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopPassengerInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopPassengerInjury(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrStopPassengerInjury(siteRequest_, TrafficSearch.staticSolrStopPassengerInjury(siteRequest_, TrafficSearch.staticSetStopPassengerInjury(siteRequest_, o)));
	}

	public Boolean solrStopPassengerInjury() {
		return TrafficSearch.staticSolrStopPassengerInjury(siteRequest_, stopPassengerInjury);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopOfficerId">Find the entity stopOfficerId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopOfficerId(Wrap<String> w);

	public String getStopOfficerId() {
		return stopOfficerId;
	}
	public void setStopOfficerId(String o) {
		this.stopOfficerId = TrafficSearch.staticSetStopOfficerId(siteRequest_, o);
		this.stopOfficerIdWrap.alreadyInitialized = true;
	}
	public static String staticSetStopOfficerId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficSearch stopOfficerIdInit() {
		if(!stopOfficerIdWrap.alreadyInitialized) {
			_stopOfficerId(stopOfficerIdWrap);
			if(stopOfficerId == null)
				setStopOfficerId(stopOfficerIdWrap.o);
			stopOfficerIdWrap.o(null);
		}
		stopOfficerIdWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static String staticSolrStopOfficerId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopOfficerId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopOfficerId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrStopOfficerId(siteRequest_, TrafficSearch.staticSolrStopOfficerId(siteRequest_, TrafficSearch.staticSetStopOfficerId(siteRequest_, o)));
	}

	public String solrStopOfficerId() {
		return TrafficSearch.staticSolrStopOfficerId(siteRequest_, stopOfficerId);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopLocationId">Find the entity stopLocationId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopLocationId(Wrap<String> w);

	public String getStopLocationId() {
		return stopLocationId;
	}
	public void setStopLocationId(String o) {
		this.stopLocationId = TrafficSearch.staticSetStopLocationId(siteRequest_, o);
		this.stopLocationIdWrap.alreadyInitialized = true;
	}
	public static String staticSetStopLocationId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficSearch stopLocationIdInit() {
		if(!stopLocationIdWrap.alreadyInitialized) {
			_stopLocationId(stopLocationIdWrap);
			if(stopLocationId == null)
				setStopLocationId(stopLocationIdWrap.o);
			stopLocationIdWrap.o(null);
		}
		stopLocationIdWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static String staticSolrStopLocationId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopLocationId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopLocationId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrStopLocationId(siteRequest_, TrafficSearch.staticSolrStopLocationId(siteRequest_, TrafficSearch.staticSetStopLocationId(siteRequest_, o)));
	}

	public String solrStopLocationId() {
		return TrafficSearch.staticSolrStopLocationId(siteRequest_, stopLocationId);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopCityId">Find the entity stopCityId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopCityId(Wrap<String> w);

	public String getStopCityId() {
		return stopCityId;
	}
	public void setStopCityId(String o) {
		this.stopCityId = TrafficSearch.staticSetStopCityId(siteRequest_, o);
		this.stopCityIdWrap.alreadyInitialized = true;
	}
	public static String staticSetStopCityId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficSearch stopCityIdInit() {
		if(!stopCityIdWrap.alreadyInitialized) {
			_stopCityId(stopCityIdWrap);
			if(stopCityId == null)
				setStopCityId(stopCityIdWrap.o);
			stopCityIdWrap.o(null);
		}
		stopCityIdWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static String staticSolrStopCityId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopCityId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopCityId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrStopCityId(siteRequest_, TrafficSearch.staticSolrStopCityId(siteRequest_, TrafficSearch.staticSetStopCityId(siteRequest_, o)));
	}

	public String solrStopCityId() {
		return TrafficSearch.staticSolrStopCityId(siteRequest_, stopCityId);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personAge">Find the entity personAge in Solr</a>
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
		this.personAge = TrafficSearch.staticSetPersonAge(siteRequest_, o);
		this.personAgeWrap.alreadyInitialized = true;
	}
	public static Integer staticSetPersonAge(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected TrafficSearch personAgeInit() {
		if(!personAgeWrap.alreadyInitialized) {
			_personAge(personAgeWrap);
			if(personAge == null)
				setPersonAge(personAgeWrap.o);
			personAgeWrap.o(null);
		}
		personAgeWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Integer staticSolrPersonAge(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrPersonAge(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonAge(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrPersonAge(siteRequest_, TrafficSearch.staticSolrPersonAge(siteRequest_, TrafficSearch.staticSetPersonAge(siteRequest_, o)));
	}

	public Integer solrPersonAge() {
		return TrafficSearch.staticSolrPersonAge(siteRequest_, personAge);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personTypeId">Find the entity personTypeId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personTypeId(Wrap<String> w);

	public String getPersonTypeId() {
		return personTypeId;
	}
	public void setPersonTypeId(String o) {
		this.personTypeId = TrafficSearch.staticSetPersonTypeId(siteRequest_, o);
		this.personTypeIdWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonTypeId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficSearch personTypeIdInit() {
		if(!personTypeIdWrap.alreadyInitialized) {
			_personTypeId(personTypeIdWrap);
			if(personTypeId == null)
				setPersonTypeId(personTypeIdWrap.o);
			personTypeIdWrap.o(null);
		}
		personTypeIdWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static String staticSolrPersonTypeId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonTypeId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonTypeId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrPersonTypeId(siteRequest_, TrafficSearch.staticSolrPersonTypeId(siteRequest_, TrafficSearch.staticSetPersonTypeId(siteRequest_, o)));
	}

	public String solrPersonTypeId() {
		return TrafficSearch.staticSolrPersonTypeId(siteRequest_, personTypeId);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personTypeTitle">Find the entity personTypeTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personTypeTitle(Wrap<String> w);

	public String getPersonTypeTitle() {
		return personTypeTitle;
	}
	public void setPersonTypeTitle(String o) {
		this.personTypeTitle = TrafficSearch.staticSetPersonTypeTitle(siteRequest_, o);
		this.personTypeTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficSearch personTypeTitleInit() {
		if(!personTypeTitleWrap.alreadyInitialized) {
			_personTypeTitle(personTypeTitleWrap);
			if(personTypeTitle == null)
				setPersonTypeTitle(personTypeTitleWrap.o);
			personTypeTitleWrap.o(null);
		}
		personTypeTitleWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static String staticSolrPersonTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrPersonTypeTitle(siteRequest_, TrafficSearch.staticSolrPersonTypeTitle(siteRequest_, TrafficSearch.staticSetPersonTypeTitle(siteRequest_, o)));
	}

	public String solrPersonTypeTitle() {
		return TrafficSearch.staticSolrPersonTypeTitle(siteRequest_, personTypeTitle);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personTypeDriver">Find the entity personTypeDriver in Solr</a>
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
		this.personTypeDriver = TrafficSearch.staticSetPersonTypeDriver(siteRequest_, o);
		this.personTypeDriverWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetPersonTypeDriver(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficSearch personTypeDriverInit() {
		if(!personTypeDriverWrap.alreadyInitialized) {
			_personTypeDriver(personTypeDriverWrap);
			if(personTypeDriver == null)
				setPersonTypeDriver(personTypeDriverWrap.o);
			personTypeDriverWrap.o(null);
		}
		personTypeDriverWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Boolean staticSolrPersonTypeDriver(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrPersonTypeDriver(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonTypeDriver(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrPersonTypeDriver(siteRequest_, TrafficSearch.staticSolrPersonTypeDriver(siteRequest_, TrafficSearch.staticSetPersonTypeDriver(siteRequest_, o)));
	}

	public Boolean solrPersonTypeDriver() {
		return TrafficSearch.staticSolrPersonTypeDriver(siteRequest_, personTypeDriver);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personTypePassenger">Find the entity personTypePassenger in Solr</a>
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
		this.personTypePassenger = TrafficSearch.staticSetPersonTypePassenger(siteRequest_, o);
		this.personTypePassengerWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetPersonTypePassenger(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficSearch personTypePassengerInit() {
		if(!personTypePassengerWrap.alreadyInitialized) {
			_personTypePassenger(personTypePassengerWrap);
			if(personTypePassenger == null)
				setPersonTypePassenger(personTypePassengerWrap.o);
			personTypePassengerWrap.o(null);
		}
		personTypePassengerWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Boolean staticSolrPersonTypePassenger(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrPersonTypePassenger(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonTypePassenger(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrPersonTypePassenger(siteRequest_, TrafficSearch.staticSolrPersonTypePassenger(siteRequest_, TrafficSearch.staticSetPersonTypePassenger(siteRequest_, o)));
	}

	public Boolean solrPersonTypePassenger() {
		return TrafficSearch.staticSolrPersonTypePassenger(siteRequest_, personTypePassenger);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personGenderId">Find the entity personGenderId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personGenderId(Wrap<String> w);

	public String getPersonGenderId() {
		return personGenderId;
	}
	public void setPersonGenderId(String o) {
		this.personGenderId = TrafficSearch.staticSetPersonGenderId(siteRequest_, o);
		this.personGenderIdWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonGenderId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficSearch personGenderIdInit() {
		if(!personGenderIdWrap.alreadyInitialized) {
			_personGenderId(personGenderIdWrap);
			if(personGenderId == null)
				setPersonGenderId(personGenderIdWrap.o);
			personGenderIdWrap.o(null);
		}
		personGenderIdWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static String staticSolrPersonGenderId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonGenderId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonGenderId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrPersonGenderId(siteRequest_, TrafficSearch.staticSolrPersonGenderId(siteRequest_, TrafficSearch.staticSetPersonGenderId(siteRequest_, o)));
	}

	public String solrPersonGenderId() {
		return TrafficSearch.staticSolrPersonGenderId(siteRequest_, personGenderId);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personGenderTitle">Find the entity personGenderTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personGenderTitle(Wrap<String> w);

	public String getPersonGenderTitle() {
		return personGenderTitle;
	}
	public void setPersonGenderTitle(String o) {
		this.personGenderTitle = TrafficSearch.staticSetPersonGenderTitle(siteRequest_, o);
		this.personGenderTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonGenderTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficSearch personGenderTitleInit() {
		if(!personGenderTitleWrap.alreadyInitialized) {
			_personGenderTitle(personGenderTitleWrap);
			if(personGenderTitle == null)
				setPersonGenderTitle(personGenderTitleWrap.o);
			personGenderTitleWrap.o(null);
		}
		personGenderTitleWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static String staticSolrPersonGenderTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonGenderTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonGenderTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrPersonGenderTitle(siteRequest_, TrafficSearch.staticSolrPersonGenderTitle(siteRequest_, TrafficSearch.staticSetPersonGenderTitle(siteRequest_, o)));
	}

	public String solrPersonGenderTitle() {
		return TrafficSearch.staticSolrPersonGenderTitle(siteRequest_, personGenderTitle);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personGenderFemale">Find the entity personGenderFemale in Solr</a>
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
		this.personGenderFemale = TrafficSearch.staticSetPersonGenderFemale(siteRequest_, o);
		this.personGenderFemaleWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetPersonGenderFemale(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficSearch personGenderFemaleInit() {
		if(!personGenderFemaleWrap.alreadyInitialized) {
			_personGenderFemale(personGenderFemaleWrap);
			if(personGenderFemale == null)
				setPersonGenderFemale(personGenderFemaleWrap.o);
			personGenderFemaleWrap.o(null);
		}
		personGenderFemaleWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Boolean staticSolrPersonGenderFemale(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrPersonGenderFemale(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonGenderFemale(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrPersonGenderFemale(siteRequest_, TrafficSearch.staticSolrPersonGenderFemale(siteRequest_, TrafficSearch.staticSetPersonGenderFemale(siteRequest_, o)));
	}

	public Boolean solrPersonGenderFemale() {
		return TrafficSearch.staticSolrPersonGenderFemale(siteRequest_, personGenderFemale);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personGenderMale">Find the entity personGenderMale in Solr</a>
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
		this.personGenderMale = TrafficSearch.staticSetPersonGenderMale(siteRequest_, o);
		this.personGenderMaleWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetPersonGenderMale(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficSearch personGenderMaleInit() {
		if(!personGenderMaleWrap.alreadyInitialized) {
			_personGenderMale(personGenderMaleWrap);
			if(personGenderMale == null)
				setPersonGenderMale(personGenderMaleWrap.o);
			personGenderMaleWrap.o(null);
		}
		personGenderMaleWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Boolean staticSolrPersonGenderMale(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrPersonGenderMale(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonGenderMale(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrPersonGenderMale(siteRequest_, TrafficSearch.staticSolrPersonGenderMale(siteRequest_, TrafficSearch.staticSetPersonGenderMale(siteRequest_, o)));
	}

	public Boolean solrPersonGenderMale() {
		return TrafficSearch.staticSolrPersonGenderMale(siteRequest_, personGenderMale);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personEthnicityId">Find the entity personEthnicityId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personEthnicityId(Wrap<String> w);

	public String getPersonEthnicityId() {
		return personEthnicityId;
	}
	public void setPersonEthnicityId(String o) {
		this.personEthnicityId = TrafficSearch.staticSetPersonEthnicityId(siteRequest_, o);
		this.personEthnicityIdWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonEthnicityId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficSearch personEthnicityIdInit() {
		if(!personEthnicityIdWrap.alreadyInitialized) {
			_personEthnicityId(personEthnicityIdWrap);
			if(personEthnicityId == null)
				setPersonEthnicityId(personEthnicityIdWrap.o);
			personEthnicityIdWrap.o(null);
		}
		personEthnicityIdWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static String staticSolrPersonEthnicityId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonEthnicityId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonEthnicityId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrPersonEthnicityId(siteRequest_, TrafficSearch.staticSolrPersonEthnicityId(siteRequest_, TrafficSearch.staticSetPersonEthnicityId(siteRequest_, o)));
	}

	public String solrPersonEthnicityId() {
		return TrafficSearch.staticSolrPersonEthnicityId(siteRequest_, personEthnicityId);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personEthnicityTitle">Find the entity personEthnicityTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personEthnicityTitle(Wrap<String> w);

	public String getPersonEthnicityTitle() {
		return personEthnicityTitle;
	}
	public void setPersonEthnicityTitle(String o) {
		this.personEthnicityTitle = TrafficSearch.staticSetPersonEthnicityTitle(siteRequest_, o);
		this.personEthnicityTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonEthnicityTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficSearch personEthnicityTitleInit() {
		if(!personEthnicityTitleWrap.alreadyInitialized) {
			_personEthnicityTitle(personEthnicityTitleWrap);
			if(personEthnicityTitle == null)
				setPersonEthnicityTitle(personEthnicityTitleWrap.o);
			personEthnicityTitleWrap.o(null);
		}
		personEthnicityTitleWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static String staticSolrPersonEthnicityTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonEthnicityTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonEthnicityTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrPersonEthnicityTitle(siteRequest_, TrafficSearch.staticSolrPersonEthnicityTitle(siteRequest_, TrafficSearch.staticSetPersonEthnicityTitle(siteRequest_, o)));
	}

	public String solrPersonEthnicityTitle() {
		return TrafficSearch.staticSolrPersonEthnicityTitle(siteRequest_, personEthnicityTitle);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personRaceId">Find the entity personRaceId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personRaceId(Wrap<String> w);

	public String getPersonRaceId() {
		return personRaceId;
	}
	public void setPersonRaceId(String o) {
		this.personRaceId = TrafficSearch.staticSetPersonRaceId(siteRequest_, o);
		this.personRaceIdWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonRaceId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficSearch personRaceIdInit() {
		if(!personRaceIdWrap.alreadyInitialized) {
			_personRaceId(personRaceIdWrap);
			if(personRaceId == null)
				setPersonRaceId(personRaceIdWrap.o);
			personRaceIdWrap.o(null);
		}
		personRaceIdWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static String staticSolrPersonRaceId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonRaceId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonRaceId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrPersonRaceId(siteRequest_, TrafficSearch.staticSolrPersonRaceId(siteRequest_, TrafficSearch.staticSetPersonRaceId(siteRequest_, o)));
	}

	public String solrPersonRaceId() {
		return TrafficSearch.staticSolrPersonRaceId(siteRequest_, personRaceId);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personRaceTitle">Find the entity personRaceTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personRaceTitle(Wrap<String> w);

	public String getPersonRaceTitle() {
		return personRaceTitle;
	}
	public void setPersonRaceTitle(String o) {
		this.personRaceTitle = TrafficSearch.staticSetPersonRaceTitle(siteRequest_, o);
		this.personRaceTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonRaceTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficSearch personRaceTitleInit() {
		if(!personRaceTitleWrap.alreadyInitialized) {
			_personRaceTitle(personRaceTitleWrap);
			if(personRaceTitle == null)
				setPersonRaceTitle(personRaceTitleWrap.o);
			personRaceTitleWrap.o(null);
		}
		personRaceTitleWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static String staticSolrPersonRaceTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonRaceTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonRaceTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrPersonRaceTitle(siteRequest_, TrafficSearch.staticSolrPersonRaceTitle(siteRequest_, TrafficSearch.staticSetPersonRaceTitle(siteRequest_, o)));
	}

	public String solrPersonRaceTitle() {
		return TrafficSearch.staticSolrPersonRaceTitle(siteRequest_, personRaceTitle);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopId">Find the entity stopId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopId(Wrap<String> w);

	public String getStopId() {
		return stopId;
	}
	public void setStopId(String o) {
		this.stopId = TrafficSearch.staticSetStopId(siteRequest_, o);
		this.stopIdWrap.alreadyInitialized = true;
	}
	public static String staticSetStopId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficSearch stopIdInit() {
		if(!stopIdWrap.alreadyInitialized) {
			_stopId(stopIdWrap);
			if(stopId == null)
				setStopId(stopIdWrap.o);
			stopIdWrap.o(null);
		}
		stopIdWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static String staticSolrStopId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrStopId(siteRequest_, TrafficSearch.staticSolrStopId(siteRequest_, TrafficSearch.staticSetStopId(siteRequest_, o)));
	}

	public String solrStopId() {
		return TrafficSearch.staticSolrStopId(siteRequest_, stopId);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchTypeNum">Find the entity searchTypeNum in Solr</a>
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
		this.searchTypeNum = TrafficSearch.staticSetSearchTypeNum(siteRequest_, o);
		this.searchTypeNumWrap.alreadyInitialized = true;
	}
	public static Integer staticSetSearchTypeNum(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected TrafficSearch searchTypeNumInit() {
		if(!searchTypeNumWrap.alreadyInitialized) {
			_searchTypeNum(searchTypeNumWrap);
			if(searchTypeNum == null)
				setSearchTypeNum(searchTypeNumWrap.o);
			searchTypeNumWrap.o(null);
		}
		searchTypeNumWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Integer staticSolrSearchTypeNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrSearchTypeNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchTypeNum(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrSearchTypeNum(siteRequest_, TrafficSearch.staticSolrSearchTypeNum(siteRequest_, TrafficSearch.staticSetSearchTypeNum(siteRequest_, o)));
	}

	public Integer solrSearchTypeNum() {
		return TrafficSearch.staticSolrSearchTypeNum(siteRequest_, searchTypeNum);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchTypeTitle">Find the entity searchTypeTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _searchTypeTitle(Wrap<String> w);

	public String getSearchTypeTitle() {
		return searchTypeTitle;
	}
	public void setSearchTypeTitle(String o) {
		this.searchTypeTitle = TrafficSearch.staticSetSearchTypeTitle(siteRequest_, o);
		this.searchTypeTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetSearchTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficSearch searchTypeTitleInit() {
		if(!searchTypeTitleWrap.alreadyInitialized) {
			_searchTypeTitle(searchTypeTitleWrap);
			if(searchTypeTitle == null)
				setSearchTypeTitle(searchTypeTitleWrap.o);
			searchTypeTitleWrap.o(null);
		}
		searchTypeTitleWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static String staticSolrSearchTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrSearchTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrSearchTypeTitle(siteRequest_, TrafficSearch.staticSolrSearchTypeTitle(siteRequest_, TrafficSearch.staticSetSearchTypeTitle(siteRequest_, o)));
	}

	public String solrSearchTypeTitle() {
		return TrafficSearch.staticSolrSearchTypeTitle(siteRequest_, searchTypeTitle);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchVehicle">Find the entity searchVehicle in Solr</a>
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
		this.searchVehicle = TrafficSearch.staticSetSearchVehicle(siteRequest_, o);
		this.searchVehicleWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSearchVehicle(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficSearch searchVehicleInit() {
		if(!searchVehicleWrap.alreadyInitialized) {
			_searchVehicle(searchVehicleWrap);
			if(searchVehicle == null)
				setSearchVehicle(searchVehicleWrap.o);
			searchVehicleWrap.o(null);
		}
		searchVehicleWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Boolean staticSolrSearchVehicle(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrSearchVehicle(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchVehicle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrSearchVehicle(siteRequest_, TrafficSearch.staticSolrSearchVehicle(siteRequest_, TrafficSearch.staticSetSearchVehicle(siteRequest_, o)));
	}

	public Boolean solrSearchVehicle() {
		return TrafficSearch.staticSolrSearchVehicle(siteRequest_, searchVehicle);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchDriver">Find the entity searchDriver in Solr</a>
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
		this.searchDriver = TrafficSearch.staticSetSearchDriver(siteRequest_, o);
		this.searchDriverWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSearchDriver(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficSearch searchDriverInit() {
		if(!searchDriverWrap.alreadyInitialized) {
			_searchDriver(searchDriverWrap);
			if(searchDriver == null)
				setSearchDriver(searchDriverWrap.o);
			searchDriverWrap.o(null);
		}
		searchDriverWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Boolean staticSolrSearchDriver(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrSearchDriver(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchDriver(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrSearchDriver(siteRequest_, TrafficSearch.staticSolrSearchDriver(siteRequest_, TrafficSearch.staticSetSearchDriver(siteRequest_, o)));
	}

	public Boolean solrSearchDriver() {
		return TrafficSearch.staticSolrSearchDriver(siteRequest_, searchDriver);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchPassenger">Find the entity searchPassenger in Solr</a>
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
		this.searchPassenger = TrafficSearch.staticSetSearchPassenger(siteRequest_, o);
		this.searchPassengerWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSearchPassenger(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficSearch searchPassengerInit() {
		if(!searchPassengerWrap.alreadyInitialized) {
			_searchPassenger(searchPassengerWrap);
			if(searchPassenger == null)
				setSearchPassenger(searchPassengerWrap.o);
			searchPassengerWrap.o(null);
		}
		searchPassengerWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Boolean staticSolrSearchPassenger(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrSearchPassenger(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchPassenger(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrSearchPassenger(siteRequest_, TrafficSearch.staticSolrSearchPassenger(siteRequest_, TrafficSearch.staticSetSearchPassenger(siteRequest_, o)));
	}

	public Boolean solrSearchPassenger() {
		return TrafficSearch.staticSolrSearchPassenger(siteRequest_, searchPassenger);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchProperty">Find the entity searchProperty in Solr</a>
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
		this.searchProperty = TrafficSearch.staticSetSearchProperty(siteRequest_, o);
		this.searchPropertyWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSearchProperty(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficSearch searchPropertyInit() {
		if(!searchPropertyWrap.alreadyInitialized) {
			_searchProperty(searchPropertyWrap);
			if(searchProperty == null)
				setSearchProperty(searchPropertyWrap.o);
			searchPropertyWrap.o(null);
		}
		searchPropertyWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Boolean staticSolrSearchProperty(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrSearchProperty(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchProperty(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrSearchProperty(siteRequest_, TrafficSearch.staticSolrSearchProperty(siteRequest_, TrafficSearch.staticSetSearchProperty(siteRequest_, o)));
	}

	public Boolean solrSearchProperty() {
		return TrafficSearch.staticSolrSearchProperty(siteRequest_, searchProperty);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchVehicleSiezed">Find the entity searchVehicleSiezed in Solr</a>
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
		this.searchVehicleSiezed = TrafficSearch.staticSetSearchVehicleSiezed(siteRequest_, o);
		this.searchVehicleSiezedWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSearchVehicleSiezed(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficSearch searchVehicleSiezedInit() {
		if(!searchVehicleSiezedWrap.alreadyInitialized) {
			_searchVehicleSiezed(searchVehicleSiezedWrap);
			if(searchVehicleSiezed == null)
				setSearchVehicleSiezed(searchVehicleSiezedWrap.o);
			searchVehicleSiezedWrap.o(null);
		}
		searchVehicleSiezedWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Boolean staticSolrSearchVehicleSiezed(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrSearchVehicleSiezed(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchVehicleSiezed(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrSearchVehicleSiezed(siteRequest_, TrafficSearch.staticSolrSearchVehicleSiezed(siteRequest_, TrafficSearch.staticSetSearchVehicleSiezed(siteRequest_, o)));
	}

	public Boolean solrSearchVehicleSiezed() {
		return TrafficSearch.staticSolrSearchVehicleSiezed(siteRequest_, searchVehicleSiezed);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchPersonalPropertySiezed">Find the entity searchPersonalPropertySiezed in Solr</a>
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
		this.searchPersonalPropertySiezed = TrafficSearch.staticSetSearchPersonalPropertySiezed(siteRequest_, o);
		this.searchPersonalPropertySiezedWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSearchPersonalPropertySiezed(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficSearch searchPersonalPropertySiezedInit() {
		if(!searchPersonalPropertySiezedWrap.alreadyInitialized) {
			_searchPersonalPropertySiezed(searchPersonalPropertySiezedWrap);
			if(searchPersonalPropertySiezed == null)
				setSearchPersonalPropertySiezed(searchPersonalPropertySiezedWrap.o);
			searchPersonalPropertySiezedWrap.o(null);
		}
		searchPersonalPropertySiezedWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Boolean staticSolrSearchPersonalPropertySiezed(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrSearchPersonalPropertySiezed(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchPersonalPropertySiezed(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrSearchPersonalPropertySiezed(siteRequest_, TrafficSearch.staticSolrSearchPersonalPropertySiezed(siteRequest_, TrafficSearch.staticSetSearchPersonalPropertySiezed(siteRequest_, o)));
	}

	public Boolean solrSearchPersonalPropertySiezed() {
		return TrafficSearch.staticSolrSearchPersonalPropertySiezed(siteRequest_, searchPersonalPropertySiezed);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchOtherPropertySiezed">Find the entity searchOtherPropertySiezed in Solr</a>
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
		this.searchOtherPropertySiezed = TrafficSearch.staticSetSearchOtherPropertySiezed(siteRequest_, o);
		this.searchOtherPropertySiezedWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSearchOtherPropertySiezed(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficSearch searchOtherPropertySiezedInit() {
		if(!searchOtherPropertySiezedWrap.alreadyInitialized) {
			_searchOtherPropertySiezed(searchOtherPropertySiezedWrap);
			if(searchOtherPropertySiezed == null)
				setSearchOtherPropertySiezed(searchOtherPropertySiezedWrap.o);
			searchOtherPropertySiezedWrap.o(null);
		}
		searchOtherPropertySiezedWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Boolean staticSolrSearchOtherPropertySiezed(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrSearchOtherPropertySiezed(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchOtherPropertySiezed(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrSearchOtherPropertySiezed(siteRequest_, TrafficSearch.staticSolrSearchOtherPropertySiezed(siteRequest_, TrafficSearch.staticSetSearchOtherPropertySiezed(siteRequest_, o)));
	}

	public Boolean solrSearchOtherPropertySiezed() {
		return TrafficSearch.staticSolrSearchOtherPropertySiezed(siteRequest_, searchOtherPropertySiezed);
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

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedTrafficSearch = false;

	public Future<Void> promiseDeepTrafficSearch(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedTrafficSearch) {
			alreadyInitializedTrafficSearch = true;
			return promiseDeepTrafficSearch();
		} else {
			return Future.succeededFuture();
		}
	}

	public Future<Void> promiseDeepTrafficSearch() {
		Promise<Void> promise = Promise.promise();
		Promise<Void> promise2 = Promise.promise();
		promiseTrafficSearch(promise2);
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

	public Future<Void> promiseTrafficSearch(Promise<Void> promise) {
		Future.future(a -> a.complete()).compose(a -> {
			Promise<Void> promise2 = Promise.promise();
			try {
				trafficSearchKeyInit();
				personIdInit();
				promise2.complete();
			} catch(Exception ex) {
				promise2.fail(ex);
			}
			return promise2.future();
		}).compose(a -> {
			Promise<Void> promise2 = Promise.promise();
			trafficPersonSearchPromise().onSuccess(trafficPersonSearch -> {
				promise2.complete();
			}).onFailure(ex -> {
				promise2.fail(ex);
			});
			return promise2.future();
		}).compose(a -> {
			Promise<Void> promise2 = Promise.promise();
			try {
				trafficPerson_Init();
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
		return promiseDeepTrafficSearch(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestTrafficSearch(SiteRequestEnUS siteRequest_) {
			super.siteRequestCluster(siteRequest_);
		if(trafficPersonSearch != null)
			trafficPersonSearch.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestTrafficSearch(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainTrafficSearch(v);
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
	public Object obtainTrafficSearch(String var) {
		TrafficSearch oTrafficSearch = (TrafficSearch)this;
		switch(var) {
			case "trafficSearchKey":
				return oTrafficSearch.trafficSearchKey;
			case "personId":
				return oTrafficSearch.personId;
			case "trafficPersonSearch":
				return oTrafficSearch.trafficPersonSearch;
			case "trafficPerson_":
				return oTrafficSearch.trafficPerson_;
			case "stateAbbreviation":
				return oTrafficSearch.stateAbbreviation;
			case "agencyTitle":
				return oTrafficSearch.agencyTitle;
			case "stopDateTime":
				return oTrafficSearch.stopDateTime;
			case "stopPurposeNum":
				return oTrafficSearch.stopPurposeNum;
			case "stopPurposeTitle":
				return oTrafficSearch.stopPurposeTitle;
			case "stopActionNum":
				return oTrafficSearch.stopActionNum;
			case "stopActionTitle":
				return oTrafficSearch.stopActionTitle;
			case "stopDriverArrest":
				return oTrafficSearch.stopDriverArrest;
			case "stopPassengerArrest":
				return oTrafficSearch.stopPassengerArrest;
			case "stopEncounterForce":
				return oTrafficSearch.stopEncounterForce;
			case "stopEngageForce":
				return oTrafficSearch.stopEngageForce;
			case "stopOfficerInjury":
				return oTrafficSearch.stopOfficerInjury;
			case "stopDriverInjury":
				return oTrafficSearch.stopDriverInjury;
			case "stopPassengerInjury":
				return oTrafficSearch.stopPassengerInjury;
			case "stopOfficerId":
				return oTrafficSearch.stopOfficerId;
			case "stopLocationId":
				return oTrafficSearch.stopLocationId;
			case "stopCityId":
				return oTrafficSearch.stopCityId;
			case "personAge":
				return oTrafficSearch.personAge;
			case "personTypeId":
				return oTrafficSearch.personTypeId;
			case "personTypeTitle":
				return oTrafficSearch.personTypeTitle;
			case "personTypeDriver":
				return oTrafficSearch.personTypeDriver;
			case "personTypePassenger":
				return oTrafficSearch.personTypePassenger;
			case "personGenderId":
				return oTrafficSearch.personGenderId;
			case "personGenderTitle":
				return oTrafficSearch.personGenderTitle;
			case "personGenderFemale":
				return oTrafficSearch.personGenderFemale;
			case "personGenderMale":
				return oTrafficSearch.personGenderMale;
			case "personEthnicityId":
				return oTrafficSearch.personEthnicityId;
			case "personEthnicityTitle":
				return oTrafficSearch.personEthnicityTitle;
			case "personRaceId":
				return oTrafficSearch.personRaceId;
			case "personRaceTitle":
				return oTrafficSearch.personRaceTitle;
			case "stopId":
				return oTrafficSearch.stopId;
			case "searchTypeNum":
				return oTrafficSearch.searchTypeNum;
			case "searchTypeTitle":
				return oTrafficSearch.searchTypeTitle;
			case "searchVehicle":
				return oTrafficSearch.searchVehicle;
			case "searchDriver":
				return oTrafficSearch.searchDriver;
			case "searchPassenger":
				return oTrafficSearch.searchPassenger;
			case "searchProperty":
				return oTrafficSearch.searchProperty;
			case "searchVehicleSiezed":
				return oTrafficSearch.searchVehicleSiezed;
			case "searchPersonalPropertySiezed":
				return oTrafficSearch.searchPersonalPropertySiezed;
			case "searchOtherPropertySiezed":
				return oTrafficSearch.searchOtherPropertySiezed;
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
				o = attributeTrafficSearch(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeTrafficSearch(String var, Object val) {
		TrafficSearch oTrafficSearch = (TrafficSearch)this;
		switch(var) {
			default:
				return super.attributeCluster(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetTrafficSearch(entityVar,  siteRequest_, o);
	}
	public static Object staticSetTrafficSearch(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "trafficSearchKey":
			return TrafficSearch.staticSetTrafficSearchKey(siteRequest_, o);
		case "personId":
			return TrafficSearch.staticSetPersonId(siteRequest_, o);
		case "stateAbbreviation":
			return TrafficSearch.staticSetStateAbbreviation(siteRequest_, o);
		case "agencyTitle":
			return TrafficSearch.staticSetAgencyTitle(siteRequest_, o);
		case "stopDateTime":
			return TrafficSearch.staticSetStopDateTime(siteRequest_, o);
		case "stopPurposeNum":
			return TrafficSearch.staticSetStopPurposeNum(siteRequest_, o);
		case "stopPurposeTitle":
			return TrafficSearch.staticSetStopPurposeTitle(siteRequest_, o);
		case "stopActionNum":
			return TrafficSearch.staticSetStopActionNum(siteRequest_, o);
		case "stopActionTitle":
			return TrafficSearch.staticSetStopActionTitle(siteRequest_, o);
		case "stopDriverArrest":
			return TrafficSearch.staticSetStopDriverArrest(siteRequest_, o);
		case "stopPassengerArrest":
			return TrafficSearch.staticSetStopPassengerArrest(siteRequest_, o);
		case "stopEncounterForce":
			return TrafficSearch.staticSetStopEncounterForce(siteRequest_, o);
		case "stopEngageForce":
			return TrafficSearch.staticSetStopEngageForce(siteRequest_, o);
		case "stopOfficerInjury":
			return TrafficSearch.staticSetStopOfficerInjury(siteRequest_, o);
		case "stopDriverInjury":
			return TrafficSearch.staticSetStopDriverInjury(siteRequest_, o);
		case "stopPassengerInjury":
			return TrafficSearch.staticSetStopPassengerInjury(siteRequest_, o);
		case "stopOfficerId":
			return TrafficSearch.staticSetStopOfficerId(siteRequest_, o);
		case "stopLocationId":
			return TrafficSearch.staticSetStopLocationId(siteRequest_, o);
		case "stopCityId":
			return TrafficSearch.staticSetStopCityId(siteRequest_, o);
		case "personAge":
			return TrafficSearch.staticSetPersonAge(siteRequest_, o);
		case "personTypeId":
			return TrafficSearch.staticSetPersonTypeId(siteRequest_, o);
		case "personTypeTitle":
			return TrafficSearch.staticSetPersonTypeTitle(siteRequest_, o);
		case "personTypeDriver":
			return TrafficSearch.staticSetPersonTypeDriver(siteRequest_, o);
		case "personTypePassenger":
			return TrafficSearch.staticSetPersonTypePassenger(siteRequest_, o);
		case "personGenderId":
			return TrafficSearch.staticSetPersonGenderId(siteRequest_, o);
		case "personGenderTitle":
			return TrafficSearch.staticSetPersonGenderTitle(siteRequest_, o);
		case "personGenderFemale":
			return TrafficSearch.staticSetPersonGenderFemale(siteRequest_, o);
		case "personGenderMale":
			return TrafficSearch.staticSetPersonGenderMale(siteRequest_, o);
		case "personEthnicityId":
			return TrafficSearch.staticSetPersonEthnicityId(siteRequest_, o);
		case "personEthnicityTitle":
			return TrafficSearch.staticSetPersonEthnicityTitle(siteRequest_, o);
		case "personRaceId":
			return TrafficSearch.staticSetPersonRaceId(siteRequest_, o);
		case "personRaceTitle":
			return TrafficSearch.staticSetPersonRaceTitle(siteRequest_, o);
		case "stopId":
			return TrafficSearch.staticSetStopId(siteRequest_, o);
		case "searchTypeNum":
			return TrafficSearch.staticSetSearchTypeNum(siteRequest_, o);
		case "searchTypeTitle":
			return TrafficSearch.staticSetSearchTypeTitle(siteRequest_, o);
		case "searchVehicle":
			return TrafficSearch.staticSetSearchVehicle(siteRequest_, o);
		case "searchDriver":
			return TrafficSearch.staticSetSearchDriver(siteRequest_, o);
		case "searchPassenger":
			return TrafficSearch.staticSetSearchPassenger(siteRequest_, o);
		case "searchProperty":
			return TrafficSearch.staticSetSearchProperty(siteRequest_, o);
		case "searchVehicleSiezed":
			return TrafficSearch.staticSetSearchVehicleSiezed(siteRequest_, o);
		case "searchPersonalPropertySiezed":
			return TrafficSearch.staticSetSearchPersonalPropertySiezed(siteRequest_, o);
		case "searchOtherPropertySiezed":
			return TrafficSearch.staticSetSearchOtherPropertySiezed(siteRequest_, o);
			default:
				return Cluster.staticSetCluster(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrTrafficSearch(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrTrafficSearch(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "trafficSearchKey":
			return TrafficSearch.staticSolrTrafficSearchKey(siteRequest_, (Long)o);
		case "personId":
			return TrafficSearch.staticSolrPersonId(siteRequest_, (String)o);
		case "stateAbbreviation":
			return TrafficSearch.staticSolrStateAbbreviation(siteRequest_, (String)o);
		case "agencyTitle":
			return TrafficSearch.staticSolrAgencyTitle(siteRequest_, (String)o);
		case "stopDateTime":
			return TrafficSearch.staticSolrStopDateTime(siteRequest_, (ZonedDateTime)o);
		case "stopPurposeNum":
			return TrafficSearch.staticSolrStopPurposeNum(siteRequest_, (Integer)o);
		case "stopPurposeTitle":
			return TrafficSearch.staticSolrStopPurposeTitle(siteRequest_, (String)o);
		case "stopActionNum":
			return TrafficSearch.staticSolrStopActionNum(siteRequest_, (Integer)o);
		case "stopActionTitle":
			return TrafficSearch.staticSolrStopActionTitle(siteRequest_, (String)o);
		case "stopDriverArrest":
			return TrafficSearch.staticSolrStopDriverArrest(siteRequest_, (Boolean)o);
		case "stopPassengerArrest":
			return TrafficSearch.staticSolrStopPassengerArrest(siteRequest_, (Boolean)o);
		case "stopEncounterForce":
			return TrafficSearch.staticSolrStopEncounterForce(siteRequest_, (Boolean)o);
		case "stopEngageForce":
			return TrafficSearch.staticSolrStopEngageForce(siteRequest_, (Boolean)o);
		case "stopOfficerInjury":
			return TrafficSearch.staticSolrStopOfficerInjury(siteRequest_, (Boolean)o);
		case "stopDriverInjury":
			return TrafficSearch.staticSolrStopDriverInjury(siteRequest_, (Boolean)o);
		case "stopPassengerInjury":
			return TrafficSearch.staticSolrStopPassengerInjury(siteRequest_, (Boolean)o);
		case "stopOfficerId":
			return TrafficSearch.staticSolrStopOfficerId(siteRequest_, (String)o);
		case "stopLocationId":
			return TrafficSearch.staticSolrStopLocationId(siteRequest_, (String)o);
		case "stopCityId":
			return TrafficSearch.staticSolrStopCityId(siteRequest_, (String)o);
		case "personAge":
			return TrafficSearch.staticSolrPersonAge(siteRequest_, (Integer)o);
		case "personTypeId":
			return TrafficSearch.staticSolrPersonTypeId(siteRequest_, (String)o);
		case "personTypeTitle":
			return TrafficSearch.staticSolrPersonTypeTitle(siteRequest_, (String)o);
		case "personTypeDriver":
			return TrafficSearch.staticSolrPersonTypeDriver(siteRequest_, (Boolean)o);
		case "personTypePassenger":
			return TrafficSearch.staticSolrPersonTypePassenger(siteRequest_, (Boolean)o);
		case "personGenderId":
			return TrafficSearch.staticSolrPersonGenderId(siteRequest_, (String)o);
		case "personGenderTitle":
			return TrafficSearch.staticSolrPersonGenderTitle(siteRequest_, (String)o);
		case "personGenderFemale":
			return TrafficSearch.staticSolrPersonGenderFemale(siteRequest_, (Boolean)o);
		case "personGenderMale":
			return TrafficSearch.staticSolrPersonGenderMale(siteRequest_, (Boolean)o);
		case "personEthnicityId":
			return TrafficSearch.staticSolrPersonEthnicityId(siteRequest_, (String)o);
		case "personEthnicityTitle":
			return TrafficSearch.staticSolrPersonEthnicityTitle(siteRequest_, (String)o);
		case "personRaceId":
			return TrafficSearch.staticSolrPersonRaceId(siteRequest_, (String)o);
		case "personRaceTitle":
			return TrafficSearch.staticSolrPersonRaceTitle(siteRequest_, (String)o);
		case "stopId":
			return TrafficSearch.staticSolrStopId(siteRequest_, (String)o);
		case "searchTypeNum":
			return TrafficSearch.staticSolrSearchTypeNum(siteRequest_, (Integer)o);
		case "searchTypeTitle":
			return TrafficSearch.staticSolrSearchTypeTitle(siteRequest_, (String)o);
		case "searchVehicle":
			return TrafficSearch.staticSolrSearchVehicle(siteRequest_, (Boolean)o);
		case "searchDriver":
			return TrafficSearch.staticSolrSearchDriver(siteRequest_, (Boolean)o);
		case "searchPassenger":
			return TrafficSearch.staticSolrSearchPassenger(siteRequest_, (Boolean)o);
		case "searchProperty":
			return TrafficSearch.staticSolrSearchProperty(siteRequest_, (Boolean)o);
		case "searchVehicleSiezed":
			return TrafficSearch.staticSolrSearchVehicleSiezed(siteRequest_, (Boolean)o);
		case "searchPersonalPropertySiezed":
			return TrafficSearch.staticSolrSearchPersonalPropertySiezed(siteRequest_, (Boolean)o);
		case "searchOtherPropertySiezed":
			return TrafficSearch.staticSolrSearchOtherPropertySiezed(siteRequest_, (Boolean)o);
			default:
				return Cluster.staticSolrCluster(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrTrafficSearch(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrTrafficSearch(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "trafficSearchKey":
			return TrafficSearch.staticSolrStrTrafficSearchKey(siteRequest_, (Long)o);
		case "personId":
			return TrafficSearch.staticSolrStrPersonId(siteRequest_, (String)o);
		case "stateAbbreviation":
			return TrafficSearch.staticSolrStrStateAbbreviation(siteRequest_, (String)o);
		case "agencyTitle":
			return TrafficSearch.staticSolrStrAgencyTitle(siteRequest_, (String)o);
		case "stopDateTime":
			return TrafficSearch.staticSolrStrStopDateTime(siteRequest_, (Date)o);
		case "stopPurposeNum":
			return TrafficSearch.staticSolrStrStopPurposeNum(siteRequest_, (Integer)o);
		case "stopPurposeTitle":
			return TrafficSearch.staticSolrStrStopPurposeTitle(siteRequest_, (String)o);
		case "stopActionNum":
			return TrafficSearch.staticSolrStrStopActionNum(siteRequest_, (Integer)o);
		case "stopActionTitle":
			return TrafficSearch.staticSolrStrStopActionTitle(siteRequest_, (String)o);
		case "stopDriverArrest":
			return TrafficSearch.staticSolrStrStopDriverArrest(siteRequest_, (Boolean)o);
		case "stopPassengerArrest":
			return TrafficSearch.staticSolrStrStopPassengerArrest(siteRequest_, (Boolean)o);
		case "stopEncounterForce":
			return TrafficSearch.staticSolrStrStopEncounterForce(siteRequest_, (Boolean)o);
		case "stopEngageForce":
			return TrafficSearch.staticSolrStrStopEngageForce(siteRequest_, (Boolean)o);
		case "stopOfficerInjury":
			return TrafficSearch.staticSolrStrStopOfficerInjury(siteRequest_, (Boolean)o);
		case "stopDriverInjury":
			return TrafficSearch.staticSolrStrStopDriverInjury(siteRequest_, (Boolean)o);
		case "stopPassengerInjury":
			return TrafficSearch.staticSolrStrStopPassengerInjury(siteRequest_, (Boolean)o);
		case "stopOfficerId":
			return TrafficSearch.staticSolrStrStopOfficerId(siteRequest_, (String)o);
		case "stopLocationId":
			return TrafficSearch.staticSolrStrStopLocationId(siteRequest_, (String)o);
		case "stopCityId":
			return TrafficSearch.staticSolrStrStopCityId(siteRequest_, (String)o);
		case "personAge":
			return TrafficSearch.staticSolrStrPersonAge(siteRequest_, (Integer)o);
		case "personTypeId":
			return TrafficSearch.staticSolrStrPersonTypeId(siteRequest_, (String)o);
		case "personTypeTitle":
			return TrafficSearch.staticSolrStrPersonTypeTitle(siteRequest_, (String)o);
		case "personTypeDriver":
			return TrafficSearch.staticSolrStrPersonTypeDriver(siteRequest_, (Boolean)o);
		case "personTypePassenger":
			return TrafficSearch.staticSolrStrPersonTypePassenger(siteRequest_, (Boolean)o);
		case "personGenderId":
			return TrafficSearch.staticSolrStrPersonGenderId(siteRequest_, (String)o);
		case "personGenderTitle":
			return TrafficSearch.staticSolrStrPersonGenderTitle(siteRequest_, (String)o);
		case "personGenderFemale":
			return TrafficSearch.staticSolrStrPersonGenderFemale(siteRequest_, (Boolean)o);
		case "personGenderMale":
			return TrafficSearch.staticSolrStrPersonGenderMale(siteRequest_, (Boolean)o);
		case "personEthnicityId":
			return TrafficSearch.staticSolrStrPersonEthnicityId(siteRequest_, (String)o);
		case "personEthnicityTitle":
			return TrafficSearch.staticSolrStrPersonEthnicityTitle(siteRequest_, (String)o);
		case "personRaceId":
			return TrafficSearch.staticSolrStrPersonRaceId(siteRequest_, (String)o);
		case "personRaceTitle":
			return TrafficSearch.staticSolrStrPersonRaceTitle(siteRequest_, (String)o);
		case "stopId":
			return TrafficSearch.staticSolrStrStopId(siteRequest_, (String)o);
		case "searchTypeNum":
			return TrafficSearch.staticSolrStrSearchTypeNum(siteRequest_, (Integer)o);
		case "searchTypeTitle":
			return TrafficSearch.staticSolrStrSearchTypeTitle(siteRequest_, (String)o);
		case "searchVehicle":
			return TrafficSearch.staticSolrStrSearchVehicle(siteRequest_, (Boolean)o);
		case "searchDriver":
			return TrafficSearch.staticSolrStrSearchDriver(siteRequest_, (Boolean)o);
		case "searchPassenger":
			return TrafficSearch.staticSolrStrSearchPassenger(siteRequest_, (Boolean)o);
		case "searchProperty":
			return TrafficSearch.staticSolrStrSearchProperty(siteRequest_, (Boolean)o);
		case "searchVehicleSiezed":
			return TrafficSearch.staticSolrStrSearchVehicleSiezed(siteRequest_, (Boolean)o);
		case "searchPersonalPropertySiezed":
			return TrafficSearch.staticSolrStrSearchPersonalPropertySiezed(siteRequest_, (Boolean)o);
		case "searchOtherPropertySiezed":
			return TrafficSearch.staticSolrStrSearchOtherPropertySiezed(siteRequest_, (Boolean)o);
			default:
				return Cluster.staticSolrStrCluster(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqTrafficSearch(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqTrafficSearch(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "trafficSearchKey":
			return TrafficSearch.staticSolrFqTrafficSearchKey(siteRequest_, o);
		case "personId":
			return TrafficSearch.staticSolrFqPersonId(siteRequest_, o);
		case "stateAbbreviation":
			return TrafficSearch.staticSolrFqStateAbbreviation(siteRequest_, o);
		case "agencyTitle":
			return TrafficSearch.staticSolrFqAgencyTitle(siteRequest_, o);
		case "stopDateTime":
			return TrafficSearch.staticSolrFqStopDateTime(siteRequest_, o);
		case "stopPurposeNum":
			return TrafficSearch.staticSolrFqStopPurposeNum(siteRequest_, o);
		case "stopPurposeTitle":
			return TrafficSearch.staticSolrFqStopPurposeTitle(siteRequest_, o);
		case "stopActionNum":
			return TrafficSearch.staticSolrFqStopActionNum(siteRequest_, o);
		case "stopActionTitle":
			return TrafficSearch.staticSolrFqStopActionTitle(siteRequest_, o);
		case "stopDriverArrest":
			return TrafficSearch.staticSolrFqStopDriverArrest(siteRequest_, o);
		case "stopPassengerArrest":
			return TrafficSearch.staticSolrFqStopPassengerArrest(siteRequest_, o);
		case "stopEncounterForce":
			return TrafficSearch.staticSolrFqStopEncounterForce(siteRequest_, o);
		case "stopEngageForce":
			return TrafficSearch.staticSolrFqStopEngageForce(siteRequest_, o);
		case "stopOfficerInjury":
			return TrafficSearch.staticSolrFqStopOfficerInjury(siteRequest_, o);
		case "stopDriverInjury":
			return TrafficSearch.staticSolrFqStopDriverInjury(siteRequest_, o);
		case "stopPassengerInjury":
			return TrafficSearch.staticSolrFqStopPassengerInjury(siteRequest_, o);
		case "stopOfficerId":
			return TrafficSearch.staticSolrFqStopOfficerId(siteRequest_, o);
		case "stopLocationId":
			return TrafficSearch.staticSolrFqStopLocationId(siteRequest_, o);
		case "stopCityId":
			return TrafficSearch.staticSolrFqStopCityId(siteRequest_, o);
		case "personAge":
			return TrafficSearch.staticSolrFqPersonAge(siteRequest_, o);
		case "personTypeId":
			return TrafficSearch.staticSolrFqPersonTypeId(siteRequest_, o);
		case "personTypeTitle":
			return TrafficSearch.staticSolrFqPersonTypeTitle(siteRequest_, o);
		case "personTypeDriver":
			return TrafficSearch.staticSolrFqPersonTypeDriver(siteRequest_, o);
		case "personTypePassenger":
			return TrafficSearch.staticSolrFqPersonTypePassenger(siteRequest_, o);
		case "personGenderId":
			return TrafficSearch.staticSolrFqPersonGenderId(siteRequest_, o);
		case "personGenderTitle":
			return TrafficSearch.staticSolrFqPersonGenderTitle(siteRequest_, o);
		case "personGenderFemale":
			return TrafficSearch.staticSolrFqPersonGenderFemale(siteRequest_, o);
		case "personGenderMale":
			return TrafficSearch.staticSolrFqPersonGenderMale(siteRequest_, o);
		case "personEthnicityId":
			return TrafficSearch.staticSolrFqPersonEthnicityId(siteRequest_, o);
		case "personEthnicityTitle":
			return TrafficSearch.staticSolrFqPersonEthnicityTitle(siteRequest_, o);
		case "personRaceId":
			return TrafficSearch.staticSolrFqPersonRaceId(siteRequest_, o);
		case "personRaceTitle":
			return TrafficSearch.staticSolrFqPersonRaceTitle(siteRequest_, o);
		case "stopId":
			return TrafficSearch.staticSolrFqStopId(siteRequest_, o);
		case "searchTypeNum":
			return TrafficSearch.staticSolrFqSearchTypeNum(siteRequest_, o);
		case "searchTypeTitle":
			return TrafficSearch.staticSolrFqSearchTypeTitle(siteRequest_, o);
		case "searchVehicle":
			return TrafficSearch.staticSolrFqSearchVehicle(siteRequest_, o);
		case "searchDriver":
			return TrafficSearch.staticSolrFqSearchDriver(siteRequest_, o);
		case "searchPassenger":
			return TrafficSearch.staticSolrFqSearchPassenger(siteRequest_, o);
		case "searchProperty":
			return TrafficSearch.staticSolrFqSearchProperty(siteRequest_, o);
		case "searchVehicleSiezed":
			return TrafficSearch.staticSolrFqSearchVehicleSiezed(siteRequest_, o);
		case "searchPersonalPropertySiezed":
			return TrafficSearch.staticSolrFqSearchPersonalPropertySiezed(siteRequest_, o);
		case "searchOtherPropertySiezed":
			return TrafficSearch.staticSolrFqSearchOtherPropertySiezed(siteRequest_, o);
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
					o = defineTrafficSearch(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineTrafficSearch(String var, String val) {
		switch(var.toLowerCase()) {
			case "personid":
				if(val != null)
					setPersonId(val);
				saves.add("personId");
				return val;
			case "searchtypenum":
				if(val != null)
					setSearchTypeNum(val);
				saves.add("searchTypeNum");
				return val;
			case "searchvehicle":
				if(val != null)
					setSearchVehicle(val);
				saves.add("searchVehicle");
				return val;
			case "searchdriver":
				if(val != null)
					setSearchDriver(val);
				saves.add("searchDriver");
				return val;
			case "searchpassenger":
				if(val != null)
					setSearchPassenger(val);
				saves.add("searchPassenger");
				return val;
			case "searchproperty":
				if(val != null)
					setSearchProperty(val);
				saves.add("searchProperty");
				return val;
			case "searchvehiclesiezed":
				if(val != null)
					setSearchVehicleSiezed(val);
				saves.add("searchVehicleSiezed");
				return val;
			case "searchpersonalpropertysiezed":
				if(val != null)
					setSearchPersonalPropertySiezed(val);
				saves.add("searchPersonalPropertySiezed");
				return val;
			case "searchotherpropertysiezed":
				if(val != null)
					setSearchOtherPropertySiezed(val);
				saves.add("searchOtherPropertySiezed");
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
					o = defineTrafficSearch(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineTrafficSearch(String var, Object val) {
		switch(var.toLowerCase()) {
			case "personid":
				if(val instanceof String)
					setPersonId((String)val);
				saves.add("personId");
				return val;
			case "searchtypenum":
				if(val instanceof Integer)
					setSearchTypeNum((Integer)val);
				saves.add("searchTypeNum");
				return val;
			case "searchvehicle":
				if(val instanceof Boolean)
					setSearchVehicle((Boolean)val);
				saves.add("searchVehicle");
				return val;
			case "searchdriver":
				if(val instanceof Boolean)
					setSearchDriver((Boolean)val);
				saves.add("searchDriver");
				return val;
			case "searchpassenger":
				if(val instanceof Boolean)
					setSearchPassenger((Boolean)val);
				saves.add("searchPassenger");
				return val;
			case "searchproperty":
				if(val instanceof Boolean)
					setSearchProperty((Boolean)val);
				saves.add("searchProperty");
				return val;
			case "searchvehiclesiezed":
				if(val instanceof Boolean)
					setSearchVehicleSiezed((Boolean)val);
				saves.add("searchVehicleSiezed");
				return val;
			case "searchpersonalpropertysiezed":
				if(val instanceof Boolean)
					setSearchPersonalPropertySiezed((Boolean)val);
				saves.add("searchPersonalPropertySiezed");
				return val;
			case "searchotherpropertysiezed":
				if(val instanceof Boolean)
					setSearchOtherPropertySiezed((Boolean)val);
				saves.add("searchOtherPropertySiezed");
				return val;
			default:
				return super.defineCluster(var, val);
		}
	}

	/////////////
	// populate //
	/////////////

	@Override public void populateForClass(SolrDocument solrDocument) {
		populateTrafficSearch(solrDocument);
	}
	public void populateTrafficSearch(SolrDocument solrDocument) {
		TrafficSearch oTrafficSearch = (TrafficSearch)this;
		saves = (List<String>)solrDocument.get("saves_stored_strings");
		if(saves != null) {

			if(saves.contains("trafficSearchKey")) {
				Long trafficSearchKey = (Long)solrDocument.get("trafficSearchKey_stored_long");
				if(trafficSearchKey != null)
					oTrafficSearch.setTrafficSearchKey(trafficSearchKey);
			}

			if(saves.contains("personId")) {
				String personId = (String)solrDocument.get("personId_stored_string");
				if(personId != null)
					oTrafficSearch.setPersonId(personId);
			}

			if(saves.contains("stateAbbreviation")) {
				String stateAbbreviation = (String)solrDocument.get("stateAbbreviation_stored_string");
				if(stateAbbreviation != null)
					oTrafficSearch.setStateAbbreviation(stateAbbreviation);
			}

			if(saves.contains("agencyTitle")) {
				String agencyTitle = (String)solrDocument.get("agencyTitle_stored_string");
				if(agencyTitle != null)
					oTrafficSearch.setAgencyTitle(agencyTitle);
			}

			if(saves.contains("stopDateTime")) {
				Date stopDateTime = (Date)solrDocument.get("stopDateTime_stored_date");
				if(stopDateTime != null)
					oTrafficSearch.setStopDateTime(stopDateTime);
			}

			if(saves.contains("stopPurposeNum")) {
				Integer stopPurposeNum = (Integer)solrDocument.get("stopPurposeNum_stored_int");
				if(stopPurposeNum != null)
					oTrafficSearch.setStopPurposeNum(stopPurposeNum);
			}

			if(saves.contains("stopPurposeTitle")) {
				String stopPurposeTitle = (String)solrDocument.get("stopPurposeTitle_stored_string");
				if(stopPurposeTitle != null)
					oTrafficSearch.setStopPurposeTitle(stopPurposeTitle);
			}

			if(saves.contains("stopActionNum")) {
				Integer stopActionNum = (Integer)solrDocument.get("stopActionNum_stored_int");
				if(stopActionNum != null)
					oTrafficSearch.setStopActionNum(stopActionNum);
			}

			if(saves.contains("stopActionTitle")) {
				String stopActionTitle = (String)solrDocument.get("stopActionTitle_stored_string");
				if(stopActionTitle != null)
					oTrafficSearch.setStopActionTitle(stopActionTitle);
			}

			if(saves.contains("stopDriverArrest")) {
				Boolean stopDriverArrest = (Boolean)solrDocument.get("stopDriverArrest_stored_boolean");
				if(stopDriverArrest != null)
					oTrafficSearch.setStopDriverArrest(stopDriverArrest);
			}

			if(saves.contains("stopPassengerArrest")) {
				Boolean stopPassengerArrest = (Boolean)solrDocument.get("stopPassengerArrest_stored_boolean");
				if(stopPassengerArrest != null)
					oTrafficSearch.setStopPassengerArrest(stopPassengerArrest);
			}

			if(saves.contains("stopEncounterForce")) {
				Boolean stopEncounterForce = (Boolean)solrDocument.get("stopEncounterForce_stored_boolean");
				if(stopEncounterForce != null)
					oTrafficSearch.setStopEncounterForce(stopEncounterForce);
			}

			if(saves.contains("stopEngageForce")) {
				Boolean stopEngageForce = (Boolean)solrDocument.get("stopEngageForce_stored_boolean");
				if(stopEngageForce != null)
					oTrafficSearch.setStopEngageForce(stopEngageForce);
			}

			if(saves.contains("stopOfficerInjury")) {
				Boolean stopOfficerInjury = (Boolean)solrDocument.get("stopOfficerInjury_stored_boolean");
				if(stopOfficerInjury != null)
					oTrafficSearch.setStopOfficerInjury(stopOfficerInjury);
			}

			if(saves.contains("stopDriverInjury")) {
				Boolean stopDriverInjury = (Boolean)solrDocument.get("stopDriverInjury_stored_boolean");
				if(stopDriverInjury != null)
					oTrafficSearch.setStopDriverInjury(stopDriverInjury);
			}

			if(saves.contains("stopPassengerInjury")) {
				Boolean stopPassengerInjury = (Boolean)solrDocument.get("stopPassengerInjury_stored_boolean");
				if(stopPassengerInjury != null)
					oTrafficSearch.setStopPassengerInjury(stopPassengerInjury);
			}

			if(saves.contains("stopOfficerId")) {
				String stopOfficerId = (String)solrDocument.get("stopOfficerId_stored_string");
				if(stopOfficerId != null)
					oTrafficSearch.setStopOfficerId(stopOfficerId);
			}

			if(saves.contains("stopLocationId")) {
				String stopLocationId = (String)solrDocument.get("stopLocationId_stored_string");
				if(stopLocationId != null)
					oTrafficSearch.setStopLocationId(stopLocationId);
			}

			if(saves.contains("stopCityId")) {
				String stopCityId = (String)solrDocument.get("stopCityId_stored_string");
				if(stopCityId != null)
					oTrafficSearch.setStopCityId(stopCityId);
			}

			if(saves.contains("personAge")) {
				Integer personAge = (Integer)solrDocument.get("personAge_stored_int");
				if(personAge != null)
					oTrafficSearch.setPersonAge(personAge);
			}

			if(saves.contains("personTypeId")) {
				String personTypeId = (String)solrDocument.get("personTypeId_stored_string");
				if(personTypeId != null)
					oTrafficSearch.setPersonTypeId(personTypeId);
			}

			if(saves.contains("personTypeTitle")) {
				String personTypeTitle = (String)solrDocument.get("personTypeTitle_stored_string");
				if(personTypeTitle != null)
					oTrafficSearch.setPersonTypeTitle(personTypeTitle);
			}

			if(saves.contains("personTypeDriver")) {
				Boolean personTypeDriver = (Boolean)solrDocument.get("personTypeDriver_stored_boolean");
				if(personTypeDriver != null)
					oTrafficSearch.setPersonTypeDriver(personTypeDriver);
			}

			if(saves.contains("personTypePassenger")) {
				Boolean personTypePassenger = (Boolean)solrDocument.get("personTypePassenger_stored_boolean");
				if(personTypePassenger != null)
					oTrafficSearch.setPersonTypePassenger(personTypePassenger);
			}

			if(saves.contains("personGenderId")) {
				String personGenderId = (String)solrDocument.get("personGenderId_stored_string");
				if(personGenderId != null)
					oTrafficSearch.setPersonGenderId(personGenderId);
			}

			if(saves.contains("personGenderTitle")) {
				String personGenderTitle = (String)solrDocument.get("personGenderTitle_stored_string");
				if(personGenderTitle != null)
					oTrafficSearch.setPersonGenderTitle(personGenderTitle);
			}

			if(saves.contains("personGenderFemale")) {
				Boolean personGenderFemale = (Boolean)solrDocument.get("personGenderFemale_stored_boolean");
				if(personGenderFemale != null)
					oTrafficSearch.setPersonGenderFemale(personGenderFemale);
			}

			if(saves.contains("personGenderMale")) {
				Boolean personGenderMale = (Boolean)solrDocument.get("personGenderMale_stored_boolean");
				if(personGenderMale != null)
					oTrafficSearch.setPersonGenderMale(personGenderMale);
			}

			if(saves.contains("personEthnicityId")) {
				String personEthnicityId = (String)solrDocument.get("personEthnicityId_stored_string");
				if(personEthnicityId != null)
					oTrafficSearch.setPersonEthnicityId(personEthnicityId);
			}

			if(saves.contains("personEthnicityTitle")) {
				String personEthnicityTitle = (String)solrDocument.get("personEthnicityTitle_stored_string");
				if(personEthnicityTitle != null)
					oTrafficSearch.setPersonEthnicityTitle(personEthnicityTitle);
			}

			if(saves.contains("personRaceId")) {
				String personRaceId = (String)solrDocument.get("personRaceId_stored_string");
				if(personRaceId != null)
					oTrafficSearch.setPersonRaceId(personRaceId);
			}

			if(saves.contains("personRaceTitle")) {
				String personRaceTitle = (String)solrDocument.get("personRaceTitle_stored_string");
				if(personRaceTitle != null)
					oTrafficSearch.setPersonRaceTitle(personRaceTitle);
			}

			if(saves.contains("stopId")) {
				String stopId = (String)solrDocument.get("stopId_stored_string");
				if(stopId != null)
					oTrafficSearch.setStopId(stopId);
			}

			if(saves.contains("searchTypeNum")) {
				Integer searchTypeNum = (Integer)solrDocument.get("searchTypeNum_stored_int");
				if(searchTypeNum != null)
					oTrafficSearch.setSearchTypeNum(searchTypeNum);
			}

			if(saves.contains("searchTypeTitle")) {
				String searchTypeTitle = (String)solrDocument.get("searchTypeTitle_stored_string");
				if(searchTypeTitle != null)
					oTrafficSearch.setSearchTypeTitle(searchTypeTitle);
			}

			if(saves.contains("searchVehicle")) {
				Boolean searchVehicle = (Boolean)solrDocument.get("searchVehicle_stored_boolean");
				if(searchVehicle != null)
					oTrafficSearch.setSearchVehicle(searchVehicle);
			}

			if(saves.contains("searchDriver")) {
				Boolean searchDriver = (Boolean)solrDocument.get("searchDriver_stored_boolean");
				if(searchDriver != null)
					oTrafficSearch.setSearchDriver(searchDriver);
			}

			if(saves.contains("searchPassenger")) {
				Boolean searchPassenger = (Boolean)solrDocument.get("searchPassenger_stored_boolean");
				if(searchPassenger != null)
					oTrafficSearch.setSearchPassenger(searchPassenger);
			}

			if(saves.contains("searchProperty")) {
				Boolean searchProperty = (Boolean)solrDocument.get("searchProperty_stored_boolean");
				if(searchProperty != null)
					oTrafficSearch.setSearchProperty(searchProperty);
			}

			if(saves.contains("searchVehicleSiezed")) {
				Boolean searchVehicleSiezed = (Boolean)solrDocument.get("searchVehicleSiezed_stored_boolean");
				if(searchVehicleSiezed != null)
					oTrafficSearch.setSearchVehicleSiezed(searchVehicleSiezed);
			}

			if(saves.contains("searchPersonalPropertySiezed")) {
				Boolean searchPersonalPropertySiezed = (Boolean)solrDocument.get("searchPersonalPropertySiezed_stored_boolean");
				if(searchPersonalPropertySiezed != null)
					oTrafficSearch.setSearchPersonalPropertySiezed(searchPersonalPropertySiezed);
			}

			if(saves.contains("searchOtherPropertySiezed")) {
				Boolean searchOtherPropertySiezed = (Boolean)solrDocument.get("searchOtherPropertySiezed_stored_boolean");
				if(searchOtherPropertySiezed != null)
					oTrafficSearch.setSearchOtherPropertySiezed(searchOtherPropertySiezed);
			}
		}

		super.populateCluster(solrDocument);
	}

	public void indexTrafficSearch(SolrInputDocument document) {
		if(trafficSearchKey != null) {
			document.addField("trafficSearchKey_indexed_long", trafficSearchKey);
			document.addField("trafficSearchKey_stored_long", trafficSearchKey);
		}
		if(personId != null) {
			document.addField("personId_indexed_string", personId);
			document.addField("personId_stored_string", personId);
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
		super.indexCluster(document);

	}

	public static String varIndexedTrafficSearch(String entityVar) {
		switch(entityVar) {
			case "trafficSearchKey":
				return "trafficSearchKey_indexed_long";
			case "personId":
				return "personId_indexed_string";
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
			default:
				return Cluster.varIndexedCluster(entityVar);
		}
	}

	public static String varSearchTrafficSearch(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSearchCluster(entityVar);
		}
	}

	public static String varSuggestedTrafficSearch(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSuggestedCluster(entityVar);
		}
	}

	/////////////
	// store //
	/////////////

	@Override public void storeForClass(SolrDocument solrDocument) {
		storeTrafficSearch(solrDocument);
	}
	public void storeTrafficSearch(SolrDocument solrDocument) {
		TrafficSearch oTrafficSearch = (TrafficSearch)this;

		oTrafficSearch.setTrafficSearchKey(Optional.ofNullable(solrDocument.get("trafficSearchKey_stored_long")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setPersonId(Optional.ofNullable(solrDocument.get("personId_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setStateAbbreviation(Optional.ofNullable(solrDocument.get("stateAbbreviation_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setAgencyTitle(Optional.ofNullable(solrDocument.get("agencyTitle_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setStopDateTime(Optional.ofNullable(solrDocument.get("stopDateTime_stored_date")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setStopPurposeNum(Optional.ofNullable(solrDocument.get("stopPurposeNum_stored_int")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setStopPurposeTitle(Optional.ofNullable(solrDocument.get("stopPurposeTitle_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setStopActionNum(Optional.ofNullable(solrDocument.get("stopActionNum_stored_int")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setStopActionTitle(Optional.ofNullable(solrDocument.get("stopActionTitle_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setStopDriverArrest(Optional.ofNullable(solrDocument.get("stopDriverArrest_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setStopPassengerArrest(Optional.ofNullable(solrDocument.get("stopPassengerArrest_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setStopEncounterForce(Optional.ofNullable(solrDocument.get("stopEncounterForce_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setStopEngageForce(Optional.ofNullable(solrDocument.get("stopEngageForce_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setStopOfficerInjury(Optional.ofNullable(solrDocument.get("stopOfficerInjury_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setStopDriverInjury(Optional.ofNullable(solrDocument.get("stopDriverInjury_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setStopPassengerInjury(Optional.ofNullable(solrDocument.get("stopPassengerInjury_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setStopOfficerId(Optional.ofNullable(solrDocument.get("stopOfficerId_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setStopLocationId(Optional.ofNullable(solrDocument.get("stopLocationId_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setStopCityId(Optional.ofNullable(solrDocument.get("stopCityId_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setPersonAge(Optional.ofNullable(solrDocument.get("personAge_stored_int")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setPersonTypeId(Optional.ofNullable(solrDocument.get("personTypeId_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setPersonTypeTitle(Optional.ofNullable(solrDocument.get("personTypeTitle_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setPersonTypeDriver(Optional.ofNullable(solrDocument.get("personTypeDriver_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setPersonTypePassenger(Optional.ofNullable(solrDocument.get("personTypePassenger_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setPersonGenderId(Optional.ofNullable(solrDocument.get("personGenderId_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setPersonGenderTitle(Optional.ofNullable(solrDocument.get("personGenderTitle_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setPersonGenderFemale(Optional.ofNullable(solrDocument.get("personGenderFemale_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setPersonGenderMale(Optional.ofNullable(solrDocument.get("personGenderMale_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setPersonEthnicityId(Optional.ofNullable(solrDocument.get("personEthnicityId_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setPersonEthnicityTitle(Optional.ofNullable(solrDocument.get("personEthnicityTitle_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setPersonRaceId(Optional.ofNullable(solrDocument.get("personRaceId_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setPersonRaceTitle(Optional.ofNullable(solrDocument.get("personRaceTitle_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setStopId(Optional.ofNullable(solrDocument.get("stopId_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setSearchTypeNum(Optional.ofNullable(solrDocument.get("searchTypeNum_stored_int")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setSearchTypeTitle(Optional.ofNullable(solrDocument.get("searchTypeTitle_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setSearchVehicle(Optional.ofNullable(solrDocument.get("searchVehicle_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setSearchDriver(Optional.ofNullable(solrDocument.get("searchDriver_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setSearchPassenger(Optional.ofNullable(solrDocument.get("searchPassenger_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setSearchProperty(Optional.ofNullable(solrDocument.get("searchProperty_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setSearchVehicleSiezed(Optional.ofNullable(solrDocument.get("searchVehicleSiezed_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setSearchPersonalPropertySiezed(Optional.ofNullable(solrDocument.get("searchPersonalPropertySiezed_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficSearch.setSearchOtherPropertySiezed(Optional.ofNullable(solrDocument.get("searchOtherPropertySiezed_stored_boolean")).map(v -> v.toString()).orElse(null));

		super.storeCluster(solrDocument);
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestTrafficSearch() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof TrafficSearch) {
			TrafficSearch original = (TrafficSearch)o;
			if(!Objects.equals(trafficSearchKey, original.getTrafficSearchKey()))
				apiRequest.addVars("trafficSearchKey");
			if(!Objects.equals(personId, original.getPersonId()))
				apiRequest.addVars("personId");
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
			super.apiRequestCluster();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), trafficSearchKey, personId, stateAbbreviation, agencyTitle, stopDateTime, stopPurposeNum, stopPurposeTitle, stopActionNum, stopActionTitle, stopDriverArrest, stopPassengerArrest, stopEncounterForce, stopEngageForce, stopOfficerInjury, stopDriverInjury, stopPassengerInjury, stopOfficerId, stopLocationId, stopCityId, personAge, personTypeId, personTypeTitle, personTypeDriver, personTypePassenger, personGenderId, personGenderTitle, personGenderFemale, personGenderMale, personEthnicityId, personEthnicityTitle, personRaceId, personRaceTitle, stopId, searchTypeNum, searchTypeTitle, searchVehicle, searchDriver, searchPassenger, searchProperty, searchVehicleSiezed, searchPersonalPropertySiezed, searchOtherPropertySiezed);
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof TrafficSearch))
			return false;
		TrafficSearch that = (TrafficSearch)o;
		return super.equals(o)
				&& Objects.equals( trafficSearchKey, that.trafficSearchKey )
				&& Objects.equals( personId, that.personId )
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
				&& Objects.equals( searchOtherPropertySiezed, that.searchOtherPropertySiezed );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("TrafficSearch { ");
		sb.append( "trafficSearchKey: " ).append(trafficSearchKey);
		sb.append( ", personId: \"" ).append(personId).append( "\"" );
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
		sb.append(" }");
		return sb.toString();
	}

	public static final String VAR_trafficSearchKey = "trafficSearchKey";
	public static final String VAR_personId = "personId";
	public static final String VAR_trafficPersonSearch = "trafficPersonSearch";
	public static final String VAR_trafficPerson_ = "trafficPerson_";
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
}
