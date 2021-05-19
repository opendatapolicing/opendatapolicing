package com.opendatapolicing.enus.trafficstop;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Date;
import java.time.ZonedDateTime;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import java.lang.Integer;
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
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class TrafficStopGen<DEV> extends Cluster {
	protected static final Logger LOG = LoggerFactory.getLogger(TrafficStop.class);

	public static final List<String> ROLES = Arrays.asList("SiteService");
	public static final List<String> ROLE_READS = Arrays.asList("");

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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stateAbbreviation">Find the entity stateAbbreviation in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stateAbbreviation(Wrap<String> w);

	public String getStateAbbreviation() {
		return stateAbbreviation;
	}
	public void setStateAbbreviation(String o) {
		this.stateAbbreviation = TrafficStop.staticSetStateAbbreviation(siteRequest_, o);
		this.stateAbbreviationWrap.alreadyInitialized = true;
	}
	public static String staticSetStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficStop stateAbbreviationInit() {
		if(!stateAbbreviationWrap.alreadyInitialized) {
			_stateAbbreviation(stateAbbreviationWrap);
			if(stateAbbreviation == null)
				setStateAbbreviation(stateAbbreviationWrap.o);
			stateAbbreviationWrap.o(null);
		}
		stateAbbreviationWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static String staticSolrStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrStateAbbreviation(siteRequest_, TrafficStop.staticSolrStateAbbreviation(siteRequest_, TrafficStop.staticSetStateAbbreviation(siteRequest_, o)));
	}

	public String solrStateAbbreviation() {
		return TrafficStop.staticSolrStateAbbreviation(siteRequest_, stateAbbreviation);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stateName">Find the entity stateName in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stateName(Wrap<String> w);

	public String getStateName() {
		return stateName;
	}
	public void setStateName(String o) {
		this.stateName = TrafficStop.staticSetStateName(siteRequest_, o);
		this.stateNameWrap.alreadyInitialized = true;
	}
	public static String staticSetStateName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficStop stateNameInit() {
		if(!stateNameWrap.alreadyInitialized) {
			_stateName(stateNameWrap);
			if(stateName == null)
				setStateName(stateNameWrap.o);
			stateNameWrap.o(null);
		}
		stateNameWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static String staticSolrStateName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStateName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStateName(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrStateName(siteRequest_, TrafficStop.staticSolrStateName(siteRequest_, TrafficStop.staticSetStateName(siteRequest_, o)));
	}

	public String solrStateName() {
		return TrafficStop.staticSolrStateName(siteRequest_, stateName);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:agencyTitle">Find the entity agencyTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _agencyTitle(Wrap<String> w);

	public String getAgencyTitle() {
		return agencyTitle;
	}
	public void setAgencyTitle(String o) {
		this.agencyTitle = TrafficStop.staticSetAgencyTitle(siteRequest_, o);
		this.agencyTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficStop agencyTitleInit() {
		if(!agencyTitleWrap.alreadyInitialized) {
			_agencyTitle(agencyTitleWrap);
			if(agencyTitle == null)
				setAgencyTitle(agencyTitleWrap.o);
			agencyTitleWrap.o(null);
		}
		agencyTitleWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static String staticSolrAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrAgencyTitle(siteRequest_, TrafficStop.staticSolrAgencyTitle(siteRequest_, TrafficStop.staticSetAgencyTitle(siteRequest_, o)));
	}

	public String solrAgencyTitle() {
		return TrafficStop.staticSolrAgencyTitle(siteRequest_, agencyTitle);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopDateTime">Find the entity stopDateTime in Solr</a>
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
		this.stopDateTime = TrafficStop.staticSetStopDateTime(siteRequest_, o);
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
	protected TrafficStop stopDateTimeInit() {
		if(!stopDateTimeWrap.alreadyInitialized) {
			_stopDateTime(stopDateTimeWrap);
			if(stopDateTime == null)
				setStopDateTime(stopDateTimeWrap.o);
			stopDateTimeWrap.o(null);
		}
		stopDateTimeWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static Date staticSolrStopDateTime(SiteRequestEnUS siteRequest_, ZonedDateTime o) {
		return o == null ? null : Date.from(o.toInstant());
	}

	public static String staticSolrStrStopDateTime(SiteRequestEnUS siteRequest_, Date o) {
		return "\"" + DateTimeFormatter.ISO_DATE_TIME.format(o.toInstant().atOffset(ZoneOffset.UTC)) + "\"";
	}

	public static String staticSolrFqStopDateTime(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrStopDateTime(siteRequest_, TrafficStop.staticSolrStopDateTime(siteRequest_, TrafficStop.staticSetStopDateTime(siteRequest_, o)));
	}

	public Date solrStopDateTime() {
		return TrafficStop.staticSolrStopDateTime(siteRequest_, stopDateTime);
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

	//////////////
	// stopYear //
	//////////////

	/**	 The entity stopYear
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer stopYear;
	@JsonIgnore
	public Wrap<Integer> stopYearWrap = new Wrap<Integer>().var("stopYear").o(stopYear);

	/**	<br/> The entity stopYear
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopYear">Find the entity stopYear in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopYear(Wrap<Integer> w);

	public Integer getStopYear() {
		return stopYear;
	}

	public void setStopYear(Integer stopYear) {
		this.stopYear = stopYear;
		this.stopYearWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setStopYear(String o) {
		this.stopYear = TrafficStop.staticSetStopYear(siteRequest_, o);
		this.stopYearWrap.alreadyInitialized = true;
	}
	public static Integer staticSetStopYear(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected TrafficStop stopYearInit() {
		if(!stopYearWrap.alreadyInitialized) {
			_stopYear(stopYearWrap);
			if(stopYear == null)
				setStopYear(stopYearWrap.o);
			stopYearWrap.o(null);
		}
		stopYearWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static Integer staticSolrStopYear(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrStopYear(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopYear(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrStopYear(siteRequest_, TrafficStop.staticSolrStopYear(siteRequest_, TrafficStop.staticSetStopYear(siteRequest_, o)));
	}

	public Integer solrStopYear() {
		return TrafficStop.staticSolrStopYear(siteRequest_, stopYear);
	}

	public String strStopYear() {
		return stopYear == null ? "" : stopYear.toString();
	}

	public Integer sqlStopYear() {
		return stopYear;
	}

	public String jsonStopYear() {
		return stopYear == null ? "" : stopYear.toString();
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopPurposeNum">Find the entity stopPurposeNum in Solr</a>
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
		this.stopPurposeNum = TrafficStop.staticSetStopPurposeNum(siteRequest_, o);
		this.stopPurposeNumWrap.alreadyInitialized = true;
	}
	public static Integer staticSetStopPurposeNum(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected TrafficStop stopPurposeNumInit() {
		if(!stopPurposeNumWrap.alreadyInitialized) {
			_stopPurposeNum(stopPurposeNumWrap);
			if(stopPurposeNum == null)
				setStopPurposeNum(stopPurposeNumWrap.o);
			stopPurposeNumWrap.o(null);
		}
		stopPurposeNumWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static Integer staticSolrStopPurposeNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrStopPurposeNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopPurposeNum(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrStopPurposeNum(siteRequest_, TrafficStop.staticSolrStopPurposeNum(siteRequest_, TrafficStop.staticSetStopPurposeNum(siteRequest_, o)));
	}

	public Integer solrStopPurposeNum() {
		return TrafficStop.staticSolrStopPurposeNum(siteRequest_, stopPurposeNum);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopPurposeTitle">Find the entity stopPurposeTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopPurposeTitle(Wrap<String> w);

	public String getStopPurposeTitle() {
		return stopPurposeTitle;
	}
	public void setStopPurposeTitle(String o) {
		this.stopPurposeTitle = TrafficStop.staticSetStopPurposeTitle(siteRequest_, o);
		this.stopPurposeTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetStopPurposeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficStop stopPurposeTitleInit() {
		if(!stopPurposeTitleWrap.alreadyInitialized) {
			_stopPurposeTitle(stopPurposeTitleWrap);
			if(stopPurposeTitle == null)
				setStopPurposeTitle(stopPurposeTitleWrap.o);
			stopPurposeTitleWrap.o(null);
		}
		stopPurposeTitleWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static String staticSolrStopPurposeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopPurposeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopPurposeTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrStopPurposeTitle(siteRequest_, TrafficStop.staticSolrStopPurposeTitle(siteRequest_, TrafficStop.staticSetStopPurposeTitle(siteRequest_, o)));
	}

	public String solrStopPurposeTitle() {
		return TrafficStop.staticSolrStopPurposeTitle(siteRequest_, stopPurposeTitle);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopActionNum">Find the entity stopActionNum in Solr</a>
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
		this.stopActionNum = TrafficStop.staticSetStopActionNum(siteRequest_, o);
		this.stopActionNumWrap.alreadyInitialized = true;
	}
	public static Integer staticSetStopActionNum(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected TrafficStop stopActionNumInit() {
		if(!stopActionNumWrap.alreadyInitialized) {
			_stopActionNum(stopActionNumWrap);
			if(stopActionNum == null)
				setStopActionNum(stopActionNumWrap.o);
			stopActionNumWrap.o(null);
		}
		stopActionNumWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static Integer staticSolrStopActionNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrStopActionNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopActionNum(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrStopActionNum(siteRequest_, TrafficStop.staticSolrStopActionNum(siteRequest_, TrafficStop.staticSetStopActionNum(siteRequest_, o)));
	}

	public Integer solrStopActionNum() {
		return TrafficStop.staticSolrStopActionNum(siteRequest_, stopActionNum);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopActionTitle">Find the entity stopActionTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopActionTitle(Wrap<String> w);

	public String getStopActionTitle() {
		return stopActionTitle;
	}
	public void setStopActionTitle(String o) {
		this.stopActionTitle = TrafficStop.staticSetStopActionTitle(siteRequest_, o);
		this.stopActionTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetStopActionTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficStop stopActionTitleInit() {
		if(!stopActionTitleWrap.alreadyInitialized) {
			_stopActionTitle(stopActionTitleWrap);
			if(stopActionTitle == null)
				setStopActionTitle(stopActionTitleWrap.o);
			stopActionTitleWrap.o(null);
		}
		stopActionTitleWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static String staticSolrStopActionTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopActionTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopActionTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrStopActionTitle(siteRequest_, TrafficStop.staticSolrStopActionTitle(siteRequest_, TrafficStop.staticSetStopActionTitle(siteRequest_, o)));
	}

	public String solrStopActionTitle() {
		return TrafficStop.staticSolrStopActionTitle(siteRequest_, stopActionTitle);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopDriverArrest">Find the entity stopDriverArrest in Solr</a>
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
		this.stopDriverArrest = TrafficStop.staticSetStopDriverArrest(siteRequest_, o);
		this.stopDriverArrestWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopDriverArrest(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficStop stopDriverArrestInit() {
		if(!stopDriverArrestWrap.alreadyInitialized) {
			_stopDriverArrest(stopDriverArrestWrap);
			if(stopDriverArrest == null)
				setStopDriverArrest(stopDriverArrestWrap.o);
			stopDriverArrestWrap.o(null);
		}
		stopDriverArrestWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static Boolean staticSolrStopDriverArrest(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopDriverArrest(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopDriverArrest(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrStopDriverArrest(siteRequest_, TrafficStop.staticSolrStopDriverArrest(siteRequest_, TrafficStop.staticSetStopDriverArrest(siteRequest_, o)));
	}

	public Boolean solrStopDriverArrest() {
		return TrafficStop.staticSolrStopDriverArrest(siteRequest_, stopDriverArrest);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopPassengerArrest">Find the entity stopPassengerArrest in Solr</a>
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
		this.stopPassengerArrest = TrafficStop.staticSetStopPassengerArrest(siteRequest_, o);
		this.stopPassengerArrestWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopPassengerArrest(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficStop stopPassengerArrestInit() {
		if(!stopPassengerArrestWrap.alreadyInitialized) {
			_stopPassengerArrest(stopPassengerArrestWrap);
			if(stopPassengerArrest == null)
				setStopPassengerArrest(stopPassengerArrestWrap.o);
			stopPassengerArrestWrap.o(null);
		}
		stopPassengerArrestWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static Boolean staticSolrStopPassengerArrest(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopPassengerArrest(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopPassengerArrest(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrStopPassengerArrest(siteRequest_, TrafficStop.staticSolrStopPassengerArrest(siteRequest_, TrafficStop.staticSetStopPassengerArrest(siteRequest_, o)));
	}

	public Boolean solrStopPassengerArrest() {
		return TrafficStop.staticSolrStopPassengerArrest(siteRequest_, stopPassengerArrest);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopEncounterForce">Find the entity stopEncounterForce in Solr</a>
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
		this.stopEncounterForce = TrafficStop.staticSetStopEncounterForce(siteRequest_, o);
		this.stopEncounterForceWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopEncounterForce(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficStop stopEncounterForceInit() {
		if(!stopEncounterForceWrap.alreadyInitialized) {
			_stopEncounterForce(stopEncounterForceWrap);
			if(stopEncounterForce == null)
				setStopEncounterForce(stopEncounterForceWrap.o);
			stopEncounterForceWrap.o(null);
		}
		stopEncounterForceWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static Boolean staticSolrStopEncounterForce(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopEncounterForce(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopEncounterForce(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrStopEncounterForce(siteRequest_, TrafficStop.staticSolrStopEncounterForce(siteRequest_, TrafficStop.staticSetStopEncounterForce(siteRequest_, o)));
	}

	public Boolean solrStopEncounterForce() {
		return TrafficStop.staticSolrStopEncounterForce(siteRequest_, stopEncounterForce);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopEngageForce">Find the entity stopEngageForce in Solr</a>
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
		this.stopEngageForce = TrafficStop.staticSetStopEngageForce(siteRequest_, o);
		this.stopEngageForceWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopEngageForce(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficStop stopEngageForceInit() {
		if(!stopEngageForceWrap.alreadyInitialized) {
			_stopEngageForce(stopEngageForceWrap);
			if(stopEngageForce == null)
				setStopEngageForce(stopEngageForceWrap.o);
			stopEngageForceWrap.o(null);
		}
		stopEngageForceWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static Boolean staticSolrStopEngageForce(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopEngageForce(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopEngageForce(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrStopEngageForce(siteRequest_, TrafficStop.staticSolrStopEngageForce(siteRequest_, TrafficStop.staticSetStopEngageForce(siteRequest_, o)));
	}

	public Boolean solrStopEngageForce() {
		return TrafficStop.staticSolrStopEngageForce(siteRequest_, stopEngageForce);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopOfficerInjury">Find the entity stopOfficerInjury in Solr</a>
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
		this.stopOfficerInjury = TrafficStop.staticSetStopOfficerInjury(siteRequest_, o);
		this.stopOfficerInjuryWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopOfficerInjury(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficStop stopOfficerInjuryInit() {
		if(!stopOfficerInjuryWrap.alreadyInitialized) {
			_stopOfficerInjury(stopOfficerInjuryWrap);
			if(stopOfficerInjury == null)
				setStopOfficerInjury(stopOfficerInjuryWrap.o);
			stopOfficerInjuryWrap.o(null);
		}
		stopOfficerInjuryWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static Boolean staticSolrStopOfficerInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopOfficerInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopOfficerInjury(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrStopOfficerInjury(siteRequest_, TrafficStop.staticSolrStopOfficerInjury(siteRequest_, TrafficStop.staticSetStopOfficerInjury(siteRequest_, o)));
	}

	public Boolean solrStopOfficerInjury() {
		return TrafficStop.staticSolrStopOfficerInjury(siteRequest_, stopOfficerInjury);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopDriverInjury">Find the entity stopDriverInjury in Solr</a>
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
		this.stopDriverInjury = TrafficStop.staticSetStopDriverInjury(siteRequest_, o);
		this.stopDriverInjuryWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopDriverInjury(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficStop stopDriverInjuryInit() {
		if(!stopDriverInjuryWrap.alreadyInitialized) {
			_stopDriverInjury(stopDriverInjuryWrap);
			if(stopDriverInjury == null)
				setStopDriverInjury(stopDriverInjuryWrap.o);
			stopDriverInjuryWrap.o(null);
		}
		stopDriverInjuryWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static Boolean staticSolrStopDriverInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopDriverInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopDriverInjury(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrStopDriverInjury(siteRequest_, TrafficStop.staticSolrStopDriverInjury(siteRequest_, TrafficStop.staticSetStopDriverInjury(siteRequest_, o)));
	}

	public Boolean solrStopDriverInjury() {
		return TrafficStop.staticSolrStopDriverInjury(siteRequest_, stopDriverInjury);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopPassengerInjury">Find the entity stopPassengerInjury in Solr</a>
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
		this.stopPassengerInjury = TrafficStop.staticSetStopPassengerInjury(siteRequest_, o);
		this.stopPassengerInjuryWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopPassengerInjury(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficStop stopPassengerInjuryInit() {
		if(!stopPassengerInjuryWrap.alreadyInitialized) {
			_stopPassengerInjury(stopPassengerInjuryWrap);
			if(stopPassengerInjury == null)
				setStopPassengerInjury(stopPassengerInjuryWrap.o);
			stopPassengerInjuryWrap.o(null);
		}
		stopPassengerInjuryWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static Boolean staticSolrStopPassengerInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopPassengerInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopPassengerInjury(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrStopPassengerInjury(siteRequest_, TrafficStop.staticSolrStopPassengerInjury(siteRequest_, TrafficStop.staticSetStopPassengerInjury(siteRequest_, o)));
	}

	public Boolean solrStopPassengerInjury() {
		return TrafficStop.staticSolrStopPassengerInjury(siteRequest_, stopPassengerInjury);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopOfficerId">Find the entity stopOfficerId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopOfficerId(Wrap<String> w);

	public String getStopOfficerId() {
		return stopOfficerId;
	}
	public void setStopOfficerId(String o) {
		this.stopOfficerId = TrafficStop.staticSetStopOfficerId(siteRequest_, o);
		this.stopOfficerIdWrap.alreadyInitialized = true;
	}
	public static String staticSetStopOfficerId(SiteRequestEnUS siteRequest_, String o) {
		return StringUtils.trim(o);
	}
	protected TrafficStop stopOfficerIdInit() {
		if(!stopOfficerIdWrap.alreadyInitialized) {
			_stopOfficerId(stopOfficerIdWrap);
			if(stopOfficerId == null)
				setStopOfficerId(stopOfficerIdWrap.o);
			stopOfficerIdWrap.o(null);
		}
		stopOfficerIdWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static String staticSolrStopOfficerId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopOfficerId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopOfficerId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrStopOfficerId(siteRequest_, TrafficStop.staticSolrStopOfficerId(siteRequest_, TrafficStop.staticSetStopOfficerId(siteRequest_, o)));
	}

	public String solrStopOfficerId() {
		return TrafficStop.staticSolrStopOfficerId(siteRequest_, stopOfficerId);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopLocationId">Find the entity stopLocationId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopLocationId(Wrap<String> w);

	public String getStopLocationId() {
		return stopLocationId;
	}
	public void setStopLocationId(String o) {
		this.stopLocationId = TrafficStop.staticSetStopLocationId(siteRequest_, o);
		this.stopLocationIdWrap.alreadyInitialized = true;
	}
	public static String staticSetStopLocationId(SiteRequestEnUS siteRequest_, String o) {
		return StringUtils.trim(o);
	}
	protected TrafficStop stopLocationIdInit() {
		if(!stopLocationIdWrap.alreadyInitialized) {
			_stopLocationId(stopLocationIdWrap);
			if(stopLocationId == null)
				setStopLocationId(stopLocationIdWrap.o);
			stopLocationIdWrap.o(null);
		}
		stopLocationIdWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static String staticSolrStopLocationId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopLocationId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopLocationId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrStopLocationId(siteRequest_, TrafficStop.staticSolrStopLocationId(siteRequest_, TrafficStop.staticSetStopLocationId(siteRequest_, o)));
	}

	public String solrStopLocationId() {
		return TrafficStop.staticSolrStopLocationId(siteRequest_, stopLocationId);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopCityId">Find the entity stopCityId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopCityId(Wrap<String> w);

	public String getStopCityId() {
		return stopCityId;
	}
	public void setStopCityId(String o) {
		this.stopCityId = TrafficStop.staticSetStopCityId(siteRequest_, o);
		this.stopCityIdWrap.alreadyInitialized = true;
	}
	public static String staticSetStopCityId(SiteRequestEnUS siteRequest_, String o) {
		return StringUtils.trim(o);
	}
	protected TrafficStop stopCityIdInit() {
		if(!stopCityIdWrap.alreadyInitialized) {
			_stopCityId(stopCityIdWrap);
			if(stopCityId == null)
				setStopCityId(stopCityIdWrap.o);
			stopCityIdWrap.o(null);
		}
		stopCityIdWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static String staticSolrStopCityId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopCityId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopCityId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrStopCityId(siteRequest_, TrafficStop.staticSolrStopCityId(siteRequest_, TrafficStop.staticSetStopCityId(siteRequest_, o)));
	}

	public String solrStopCityId() {
		return TrafficStop.staticSolrStopCityId(siteRequest_, stopCityId);
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

	//////////////////
	// personSearch //
	//////////////////

	/**	 The entity personSearch
	 *	 is defined as null before being initialized. 
	 */
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected SearchList<TrafficPerson> personSearch;
	@JsonIgnore
	public Wrap<SearchList<TrafficPerson>> personSearchWrap = new Wrap<SearchList<TrafficPerson>>().var("personSearch").o(personSearch);

	/**	<br/> The entity personSearch
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personSearch">Find the entity personSearch in Solr</a>
	 * <br/>
	 * @param promise is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personSearch(Promise<SearchList<TrafficPerson>> promise);

	public SearchList<TrafficPerson> getPersonSearch() {
		return personSearch;
	}

	public void setPersonSearch(SearchList<TrafficPerson> personSearch) {
		this.personSearch = personSearch;
		this.personSearchWrap.alreadyInitialized = true;
	}
	public static SearchList<TrafficPerson> staticSetPersonSearch(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected Future<SearchList<TrafficPerson>> personSearchPromise() {
		Promise<SearchList<TrafficPerson>> promise = Promise.promise();
		if(!personSearchWrap.alreadyInitialized) {
			Promise<SearchList<TrafficPerson>> promise2 = Promise.promise();
			_personSearch(promise2);
			promise2.future().onSuccess(o -> {
				if(o != null && personSearch == null) {
					o.promiseDeepForClass(siteRequest_).onSuccess(a -> {
						setPersonSearch(o);
						personSearchWrap.alreadyInitialized(true);
						promise.complete(o);
					}).onFailure(ex -> {
						promise.fail(ex);
					});
				} else {
					personSearchWrap.alreadyInitialized(true);
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

	//////////////////////
	// personRaceTitles //
	//////////////////////

	/**	 The entity personRaceTitles
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<String>(). 
	 */
	@JsonProperty
	@JsonFormat(shape = JsonFormat.Shape.ARRAY)
	@JsonInclude(Include.NON_NULL)
	protected List<String> personRaceTitles = new ArrayList<String>();
	@JsonIgnore
	public Wrap<List<String>> personRaceTitlesWrap = new Wrap<List<String>>().var("personRaceTitles").o(personRaceTitles);

	/**	<br/> The entity personRaceTitles
	 *  It is constructed before being initialized with the constructor by default List<String>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personRaceTitles">Find the entity personRaceTitles in Solr</a>
	 * <br/>
	 * @param personRaceTitles is the entity already constructed. 
	 **/
	protected abstract void _personRaceTitles(List<String> l);

	public List<String> getPersonRaceTitles() {
		return personRaceTitles;
	}

	public void setPersonRaceTitles(List<String> personRaceTitles) {
		this.personRaceTitles = personRaceTitles;
		this.personRaceTitlesWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonRaceTitles(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	public TrafficStop addPersonRaceTitles(String...objets) {
		for(String o : objets) {
			addPersonRaceTitles(o);
		}
		return (TrafficStop)this;
	}
	public TrafficStop addPersonRaceTitles(String o) {
		if(o != null && !personRaceTitles.contains(o))
			this.personRaceTitles.add(o);
		return (TrafficStop)this;
	}
	@JsonIgnore
	public void setPersonRaceTitles(JsonArray objets) {
		personRaceTitles.clear();
		for(int i = 0; i < objets.size(); i++) {
			String o = objets.getString(i);
			addPersonRaceTitles(o);
		}
	}
	protected TrafficStop personRaceTitlesInit() {
		if(!personRaceTitlesWrap.alreadyInitialized) {
			_personRaceTitles(personRaceTitles);
		}
		personRaceTitlesWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static String staticSolrPersonRaceTitles(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonRaceTitles(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonRaceTitles(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrPersonRaceTitles(siteRequest_, TrafficStop.staticSolrPersonRaceTitles(siteRequest_, TrafficStop.staticSetPersonRaceTitles(siteRequest_, o)));
	}

	public List<String> solrPersonRaceTitles() {
		List<String> l = new ArrayList<String>();
		for(String o : personRaceTitles) {
			l.add(TrafficStop.staticSolrPersonRaceTitles(siteRequest_, o));
		}
		return l;
	}

	public String strPersonRaceTitles() {
		return personRaceTitles == null ? "" : personRaceTitles.toString();
	}

	public List<String> sqlPersonRaceTitles() {
		return personRaceTitles;
	}

	public String jsonPersonRaceTitles() {
		return personRaceTitles == null ? "" : personRaceTitles.toString();
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:trafficSearchSearch">Find the entity trafficSearchSearch in Solr</a>
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

	/////////////////////////////
	// trafficSearchRaceTitles //
	/////////////////////////////

	/**	 The entity trafficSearchRaceTitles
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<String>(). 
	 */
	@JsonProperty
	@JsonFormat(shape = JsonFormat.Shape.ARRAY)
	@JsonInclude(Include.NON_NULL)
	protected List<String> trafficSearchRaceTitles = new ArrayList<String>();
	@JsonIgnore
	public Wrap<List<String>> trafficSearchRaceTitlesWrap = new Wrap<List<String>>().var("trafficSearchRaceTitles").o(trafficSearchRaceTitles);

	/**	<br/> The entity trafficSearchRaceTitles
	 *  It is constructed before being initialized with the constructor by default List<String>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:trafficSearchRaceTitles">Find the entity trafficSearchRaceTitles in Solr</a>
	 * <br/>
	 * @param trafficSearchRaceTitles is the entity already constructed. 
	 **/
	protected abstract void _trafficSearchRaceTitles(List<String> l);

	public List<String> getTrafficSearchRaceTitles() {
		return trafficSearchRaceTitles;
	}

	public void setTrafficSearchRaceTitles(List<String> trafficSearchRaceTitles) {
		this.trafficSearchRaceTitles = trafficSearchRaceTitles;
		this.trafficSearchRaceTitlesWrap.alreadyInitialized = true;
	}
	public static String staticSetTrafficSearchRaceTitles(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	public TrafficStop addTrafficSearchRaceTitles(String...objets) {
		for(String o : objets) {
			addTrafficSearchRaceTitles(o);
		}
		return (TrafficStop)this;
	}
	public TrafficStop addTrafficSearchRaceTitles(String o) {
		if(o != null && !trafficSearchRaceTitles.contains(o))
			this.trafficSearchRaceTitles.add(o);
		return (TrafficStop)this;
	}
	@JsonIgnore
	public void setTrafficSearchRaceTitles(JsonArray objets) {
		trafficSearchRaceTitles.clear();
		for(int i = 0; i < objets.size(); i++) {
			String o = objets.getString(i);
			addTrafficSearchRaceTitles(o);
		}
	}
	protected TrafficStop trafficSearchRaceTitlesInit() {
		if(!trafficSearchRaceTitlesWrap.alreadyInitialized) {
			_trafficSearchRaceTitles(trafficSearchRaceTitles);
		}
		trafficSearchRaceTitlesWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static String staticSolrTrafficSearchRaceTitles(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrTrafficSearchRaceTitles(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqTrafficSearchRaceTitles(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrTrafficSearchRaceTitles(siteRequest_, TrafficStop.staticSolrTrafficSearchRaceTitles(siteRequest_, TrafficStop.staticSetTrafficSearchRaceTitles(siteRequest_, o)));
	}

	public List<String> solrTrafficSearchRaceTitles() {
		List<String> l = new ArrayList<String>();
		for(String o : trafficSearchRaceTitles) {
			l.add(TrafficStop.staticSolrTrafficSearchRaceTitles(siteRequest_, o));
		}
		return l;
	}

	public String strTrafficSearchRaceTitles() {
		return trafficSearchRaceTitles == null ? "" : trafficSearchRaceTitles.toString();
	}

	public List<String> sqlTrafficSearchRaceTitles() {
		return trafficSearchRaceTitles;
	}

	public String jsonTrafficSearchRaceTitles() {
		return trafficSearchRaceTitles == null ? "" : trafficSearchRaceTitles.toString();
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedTrafficStop = false;

	public Future<Void> promiseDeepTrafficStop(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedTrafficStop) {
			alreadyInitializedTrafficStop = true;
			return promiseDeepTrafficStop();
		} else {
			return Future.succeededFuture();
		}
	}

	public Future<Void> promiseDeepTrafficStop() {
		Promise<Void> promise = Promise.promise();
		Promise<Void> promise2 = Promise.promise();
		promiseTrafficStop(promise2);
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

	public Future<Void> promiseTrafficStop(Promise<Void> promise) {
		Future.future(a -> a.complete()).compose(a -> {
			Promise<Void> promise2 = Promise.promise();
			try {
				stateAbbreviationInit();
				stateNameInit();
				agencyTitleInit();
				stopDateTimeInit();
				stopYearInit();
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
				promise2.complete();
			} catch(Exception ex) {
				promise2.fail(ex);
			}
			return promise2.future();
		}).compose(a -> {
			Promise<Void> promise2 = Promise.promise();
			personSearchPromise().onSuccess(personSearch -> {
				promise2.complete();
			}).onFailure(ex -> {
				promise2.fail(ex);
			});
			return promise2.future();
		}).compose(a -> {
			Promise<Void> promise2 = Promise.promise();
			try {
				personRaceTitlesInit();
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
				trafficSearchRaceTitlesInit();
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
		return promiseDeepTrafficStop(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestTrafficStop(SiteRequestEnUS siteRequest_) {
			super.siteRequestCluster(siteRequest_);
		if(personSearch != null)
			personSearch.setSiteRequest_(siteRequest_);
		if(trafficSearchSearch != null)
			trafficSearchSearch.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestTrafficStop(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainTrafficStop(v);
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
	public Object obtainTrafficStop(String var) {
		TrafficStop oTrafficStop = (TrafficStop)this;
		switch(var) {
			case "stateAbbreviation":
				return oTrafficStop.stateAbbreviation;
			case "stateName":
				return oTrafficStop.stateName;
			case "agencyTitle":
				return oTrafficStop.agencyTitle;
			case "stopDateTime":
				return oTrafficStop.stopDateTime;
			case "stopYear":
				return oTrafficStop.stopYear;
			case "stopPurposeNum":
				return oTrafficStop.stopPurposeNum;
			case "stopPurposeTitle":
				return oTrafficStop.stopPurposeTitle;
			case "stopActionNum":
				return oTrafficStop.stopActionNum;
			case "stopActionTitle":
				return oTrafficStop.stopActionTitle;
			case "stopDriverArrest":
				return oTrafficStop.stopDriverArrest;
			case "stopPassengerArrest":
				return oTrafficStop.stopPassengerArrest;
			case "stopEncounterForce":
				return oTrafficStop.stopEncounterForce;
			case "stopEngageForce":
				return oTrafficStop.stopEngageForce;
			case "stopOfficerInjury":
				return oTrafficStop.stopOfficerInjury;
			case "stopDriverInjury":
				return oTrafficStop.stopDriverInjury;
			case "stopPassengerInjury":
				return oTrafficStop.stopPassengerInjury;
			case "stopOfficerId":
				return oTrafficStop.stopOfficerId;
			case "stopLocationId":
				return oTrafficStop.stopLocationId;
			case "stopCityId":
				return oTrafficStop.stopCityId;
			case "personSearch":
				return oTrafficStop.personSearch;
			case "personRaceTitles":
				return oTrafficStop.personRaceTitles;
			case "trafficSearchSearch":
				return oTrafficStop.trafficSearchSearch;
			case "trafficSearchRaceTitles":
				return oTrafficStop.trafficSearchRaceTitles;
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
				o = attributeTrafficStop(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeTrafficStop(String var, Object val) {
		TrafficStop oTrafficStop = (TrafficStop)this;
		switch(var) {
			default:
				return super.attributeCluster(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetTrafficStop(entityVar,  siteRequest_, o);
	}
	public static Object staticSetTrafficStop(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "stateAbbreviation":
			return TrafficStop.staticSetStateAbbreviation(siteRequest_, o);
		case "stateName":
			return TrafficStop.staticSetStateName(siteRequest_, o);
		case "agencyTitle":
			return TrafficStop.staticSetAgencyTitle(siteRequest_, o);
		case "stopDateTime":
			return TrafficStop.staticSetStopDateTime(siteRequest_, o);
		case "stopYear":
			return TrafficStop.staticSetStopYear(siteRequest_, o);
		case "stopPurposeNum":
			return TrafficStop.staticSetStopPurposeNum(siteRequest_, o);
		case "stopPurposeTitle":
			return TrafficStop.staticSetStopPurposeTitle(siteRequest_, o);
		case "stopActionNum":
			return TrafficStop.staticSetStopActionNum(siteRequest_, o);
		case "stopActionTitle":
			return TrafficStop.staticSetStopActionTitle(siteRequest_, o);
		case "stopDriverArrest":
			return TrafficStop.staticSetStopDriverArrest(siteRequest_, o);
		case "stopPassengerArrest":
			return TrafficStop.staticSetStopPassengerArrest(siteRequest_, o);
		case "stopEncounterForce":
			return TrafficStop.staticSetStopEncounterForce(siteRequest_, o);
		case "stopEngageForce":
			return TrafficStop.staticSetStopEngageForce(siteRequest_, o);
		case "stopOfficerInjury":
			return TrafficStop.staticSetStopOfficerInjury(siteRequest_, o);
		case "stopDriverInjury":
			return TrafficStop.staticSetStopDriverInjury(siteRequest_, o);
		case "stopPassengerInjury":
			return TrafficStop.staticSetStopPassengerInjury(siteRequest_, o);
		case "stopOfficerId":
			return TrafficStop.staticSetStopOfficerId(siteRequest_, o);
		case "stopLocationId":
			return TrafficStop.staticSetStopLocationId(siteRequest_, o);
		case "stopCityId":
			return TrafficStop.staticSetStopCityId(siteRequest_, o);
		case "personRaceTitles":
			return TrafficStop.staticSetPersonRaceTitles(siteRequest_, o);
		case "trafficSearchRaceTitles":
			return TrafficStop.staticSetTrafficSearchRaceTitles(siteRequest_, o);
			default:
				return Cluster.staticSetCluster(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrTrafficStop(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrTrafficStop(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "stateAbbreviation":
			return TrafficStop.staticSolrStateAbbreviation(siteRequest_, (String)o);
		case "stateName":
			return TrafficStop.staticSolrStateName(siteRequest_, (String)o);
		case "agencyTitle":
			return TrafficStop.staticSolrAgencyTitle(siteRequest_, (String)o);
		case "stopDateTime":
			return TrafficStop.staticSolrStopDateTime(siteRequest_, (ZonedDateTime)o);
		case "stopYear":
			return TrafficStop.staticSolrStopYear(siteRequest_, (Integer)o);
		case "stopPurposeNum":
			return TrafficStop.staticSolrStopPurposeNum(siteRequest_, (Integer)o);
		case "stopPurposeTitle":
			return TrafficStop.staticSolrStopPurposeTitle(siteRequest_, (String)o);
		case "stopActionNum":
			return TrafficStop.staticSolrStopActionNum(siteRequest_, (Integer)o);
		case "stopActionTitle":
			return TrafficStop.staticSolrStopActionTitle(siteRequest_, (String)o);
		case "stopDriverArrest":
			return TrafficStop.staticSolrStopDriverArrest(siteRequest_, (Boolean)o);
		case "stopPassengerArrest":
			return TrafficStop.staticSolrStopPassengerArrest(siteRequest_, (Boolean)o);
		case "stopEncounterForce":
			return TrafficStop.staticSolrStopEncounterForce(siteRequest_, (Boolean)o);
		case "stopEngageForce":
			return TrafficStop.staticSolrStopEngageForce(siteRequest_, (Boolean)o);
		case "stopOfficerInjury":
			return TrafficStop.staticSolrStopOfficerInjury(siteRequest_, (Boolean)o);
		case "stopDriverInjury":
			return TrafficStop.staticSolrStopDriverInjury(siteRequest_, (Boolean)o);
		case "stopPassengerInjury":
			return TrafficStop.staticSolrStopPassengerInjury(siteRequest_, (Boolean)o);
		case "stopOfficerId":
			return TrafficStop.staticSolrStopOfficerId(siteRequest_, (String)o);
		case "stopLocationId":
			return TrafficStop.staticSolrStopLocationId(siteRequest_, (String)o);
		case "stopCityId":
			return TrafficStop.staticSolrStopCityId(siteRequest_, (String)o);
		case "personRaceTitles":
			return TrafficStop.staticSolrPersonRaceTitles(siteRequest_, (String)o);
		case "trafficSearchRaceTitles":
			return TrafficStop.staticSolrTrafficSearchRaceTitles(siteRequest_, (String)o);
			default:
				return Cluster.staticSolrCluster(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrTrafficStop(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrTrafficStop(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "stateAbbreviation":
			return TrafficStop.staticSolrStrStateAbbreviation(siteRequest_, (String)o);
		case "stateName":
			return TrafficStop.staticSolrStrStateName(siteRequest_, (String)o);
		case "agencyTitle":
			return TrafficStop.staticSolrStrAgencyTitle(siteRequest_, (String)o);
		case "stopDateTime":
			return TrafficStop.staticSolrStrStopDateTime(siteRequest_, (Date)o);
		case "stopYear":
			return TrafficStop.staticSolrStrStopYear(siteRequest_, (Integer)o);
		case "stopPurposeNum":
			return TrafficStop.staticSolrStrStopPurposeNum(siteRequest_, (Integer)o);
		case "stopPurposeTitle":
			return TrafficStop.staticSolrStrStopPurposeTitle(siteRequest_, (String)o);
		case "stopActionNum":
			return TrafficStop.staticSolrStrStopActionNum(siteRequest_, (Integer)o);
		case "stopActionTitle":
			return TrafficStop.staticSolrStrStopActionTitle(siteRequest_, (String)o);
		case "stopDriverArrest":
			return TrafficStop.staticSolrStrStopDriverArrest(siteRequest_, (Boolean)o);
		case "stopPassengerArrest":
			return TrafficStop.staticSolrStrStopPassengerArrest(siteRequest_, (Boolean)o);
		case "stopEncounterForce":
			return TrafficStop.staticSolrStrStopEncounterForce(siteRequest_, (Boolean)o);
		case "stopEngageForce":
			return TrafficStop.staticSolrStrStopEngageForce(siteRequest_, (Boolean)o);
		case "stopOfficerInjury":
			return TrafficStop.staticSolrStrStopOfficerInjury(siteRequest_, (Boolean)o);
		case "stopDriverInjury":
			return TrafficStop.staticSolrStrStopDriverInjury(siteRequest_, (Boolean)o);
		case "stopPassengerInjury":
			return TrafficStop.staticSolrStrStopPassengerInjury(siteRequest_, (Boolean)o);
		case "stopOfficerId":
			return TrafficStop.staticSolrStrStopOfficerId(siteRequest_, (String)o);
		case "stopLocationId":
			return TrafficStop.staticSolrStrStopLocationId(siteRequest_, (String)o);
		case "stopCityId":
			return TrafficStop.staticSolrStrStopCityId(siteRequest_, (String)o);
		case "personRaceTitles":
			return TrafficStop.staticSolrStrPersonRaceTitles(siteRequest_, (String)o);
		case "trafficSearchRaceTitles":
			return TrafficStop.staticSolrStrTrafficSearchRaceTitles(siteRequest_, (String)o);
			default:
				return Cluster.staticSolrStrCluster(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqTrafficStop(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqTrafficStop(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "stateAbbreviation":
			return TrafficStop.staticSolrFqStateAbbreviation(siteRequest_, o);
		case "stateName":
			return TrafficStop.staticSolrFqStateName(siteRequest_, o);
		case "agencyTitle":
			return TrafficStop.staticSolrFqAgencyTitle(siteRequest_, o);
		case "stopDateTime":
			return TrafficStop.staticSolrFqStopDateTime(siteRequest_, o);
		case "stopYear":
			return TrafficStop.staticSolrFqStopYear(siteRequest_, o);
		case "stopPurposeNum":
			return TrafficStop.staticSolrFqStopPurposeNum(siteRequest_, o);
		case "stopPurposeTitle":
			return TrafficStop.staticSolrFqStopPurposeTitle(siteRequest_, o);
		case "stopActionNum":
			return TrafficStop.staticSolrFqStopActionNum(siteRequest_, o);
		case "stopActionTitle":
			return TrafficStop.staticSolrFqStopActionTitle(siteRequest_, o);
		case "stopDriverArrest":
			return TrafficStop.staticSolrFqStopDriverArrest(siteRequest_, o);
		case "stopPassengerArrest":
			return TrafficStop.staticSolrFqStopPassengerArrest(siteRequest_, o);
		case "stopEncounterForce":
			return TrafficStop.staticSolrFqStopEncounterForce(siteRequest_, o);
		case "stopEngageForce":
			return TrafficStop.staticSolrFqStopEngageForce(siteRequest_, o);
		case "stopOfficerInjury":
			return TrafficStop.staticSolrFqStopOfficerInjury(siteRequest_, o);
		case "stopDriverInjury":
			return TrafficStop.staticSolrFqStopDriverInjury(siteRequest_, o);
		case "stopPassengerInjury":
			return TrafficStop.staticSolrFqStopPassengerInjury(siteRequest_, o);
		case "stopOfficerId":
			return TrafficStop.staticSolrFqStopOfficerId(siteRequest_, o);
		case "stopLocationId":
			return TrafficStop.staticSolrFqStopLocationId(siteRequest_, o);
		case "stopCityId":
			return TrafficStop.staticSolrFqStopCityId(siteRequest_, o);
		case "personRaceTitles":
			return TrafficStop.staticSolrFqPersonRaceTitles(siteRequest_, o);
		case "trafficSearchRaceTitles":
			return TrafficStop.staticSolrFqTrafficSearchRaceTitles(siteRequest_, o);
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
					o = defineTrafficStop(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineTrafficStop(String var, String val) {
		switch(var.toLowerCase()) {
			case "stateabbreviation":
				if(val != null)
					setStateAbbreviation(val);
				saves.add("stateAbbreviation");
				return val;
			case "statename":
				if(val != null)
					setStateName(val);
				saves.add("stateName");
				return val;
			case "agencytitle":
				if(val != null)
					setAgencyTitle(val);
				saves.add("agencyTitle");
				return val;
			case "stopdatetime":
				if(val != null)
					setStopDateTime(val);
				saves.add("stopDateTime");
				return val;
			case "stopyear":
				if(val != null)
					setStopYear(val);
				saves.add("stopYear");
				return val;
			case "stoppurposenum":
				if(val != null)
					setStopPurposeNum(val);
				saves.add("stopPurposeNum");
				return val;
			case "stopactionnum":
				if(val != null)
					setStopActionNum(val);
				saves.add("stopActionNum");
				return val;
			case "stopdriverarrest":
				if(val != null)
					setStopDriverArrest(val);
				saves.add("stopDriverArrest");
				return val;
			case "stoppassengerarrest":
				if(val != null)
					setStopPassengerArrest(val);
				saves.add("stopPassengerArrest");
				return val;
			case "stopencounterforce":
				if(val != null)
					setStopEncounterForce(val);
				saves.add("stopEncounterForce");
				return val;
			case "stopengageforce":
				if(val != null)
					setStopEngageForce(val);
				saves.add("stopEngageForce");
				return val;
			case "stopofficerinjury":
				if(val != null)
					setStopOfficerInjury(val);
				saves.add("stopOfficerInjury");
				return val;
			case "stopdriverinjury":
				if(val != null)
					setStopDriverInjury(val);
				saves.add("stopDriverInjury");
				return val;
			case "stoppassengerinjury":
				if(val != null)
					setStopPassengerInjury(val);
				saves.add("stopPassengerInjury");
				return val;
			case "stopofficerid":
				if(val != null)
					setStopOfficerId(val);
				saves.add("stopOfficerId");
				return val;
			case "stoplocationid":
				if(val != null)
					setStopLocationId(val);
				saves.add("stopLocationId");
				return val;
			case "stopcityid":
				if(val != null)
					setStopCityId(val);
				saves.add("stopCityId");
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
					o = defineTrafficStop(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineTrafficStop(String var, Object val) {
		switch(var.toLowerCase()) {
			case "stateabbreviation":
				if(val instanceof String)
					setStateAbbreviation((String)val);
				saves.add("stateAbbreviation");
				return val;
			case "statename":
				if(val instanceof String)
					setStateName((String)val);
				saves.add("stateName");
				return val;
			case "agencytitle":
				if(val instanceof String)
					setAgencyTitle((String)val);
				saves.add("agencyTitle");
				return val;
			case "stopdatetime":
				if(val instanceof ZonedDateTime)
					setStopDateTime((ZonedDateTime)val);
				else if(val instanceof OffsetDateTime)
					setStopDateTime(((OffsetDateTime)val).atZoneSameInstant(ZoneId.of(siteRequest_.getConfig().getString(ConfigKeys.SITE_ZONE))));
				saves.add("stopDateTime");
				return val;
			case "stopyear":
				if(val instanceof Integer)
					setStopYear((Integer)val);
				saves.add("stopYear");
				return val;
			case "stoppurposenum":
				if(val instanceof Integer)
					setStopPurposeNum((Integer)val);
				saves.add("stopPurposeNum");
				return val;
			case "stopactionnum":
				if(val instanceof Integer)
					setStopActionNum((Integer)val);
				saves.add("stopActionNum");
				return val;
			case "stopdriverarrest":
				if(val instanceof Boolean)
					setStopDriverArrest((Boolean)val);
				saves.add("stopDriverArrest");
				return val;
			case "stoppassengerarrest":
				if(val instanceof Boolean)
					setStopPassengerArrest((Boolean)val);
				saves.add("stopPassengerArrest");
				return val;
			case "stopencounterforce":
				if(val instanceof Boolean)
					setStopEncounterForce((Boolean)val);
				saves.add("stopEncounterForce");
				return val;
			case "stopengageforce":
				if(val instanceof Boolean)
					setStopEngageForce((Boolean)val);
				saves.add("stopEngageForce");
				return val;
			case "stopofficerinjury":
				if(val instanceof Boolean)
					setStopOfficerInjury((Boolean)val);
				saves.add("stopOfficerInjury");
				return val;
			case "stopdriverinjury":
				if(val instanceof Boolean)
					setStopDriverInjury((Boolean)val);
				saves.add("stopDriverInjury");
				return val;
			case "stoppassengerinjury":
				if(val instanceof Boolean)
					setStopPassengerInjury((Boolean)val);
				saves.add("stopPassengerInjury");
				return val;
			case "stopofficerid":
				if(val instanceof String)
					setStopOfficerId((String)val);
				saves.add("stopOfficerId");
				return val;
			case "stoplocationid":
				if(val instanceof String)
					setStopLocationId((String)val);
				saves.add("stopLocationId");
				return val;
			case "stopcityid":
				if(val instanceof String)
					setStopCityId((String)val);
				saves.add("stopCityId");
				return val;
			default:
				return super.defineCluster(var, val);
		}
	}

	/////////////
	// populate //
	/////////////

	@Override public void populateForClass(SolrDocument solrDocument) {
		populateTrafficStop(solrDocument);
	}
	public void populateTrafficStop(SolrDocument solrDocument) {
		TrafficStop oTrafficStop = (TrafficStop)this;
		saves = (List<String>)solrDocument.get("saves_stored_strings");
		if(saves != null) {

			if(saves.contains("stateAbbreviation")) {
				String stateAbbreviation = (String)solrDocument.get("stateAbbreviation_stored_string");
				if(stateAbbreviation != null)
					oTrafficStop.setStateAbbreviation(stateAbbreviation);
			}

			if(saves.contains("stateName")) {
				String stateName = (String)solrDocument.get("stateName_stored_string");
				if(stateName != null)
					oTrafficStop.setStateName(stateName);
			}

			if(saves.contains("agencyTitle")) {
				String agencyTitle = (String)solrDocument.get("agencyTitle_stored_string");
				if(agencyTitle != null)
					oTrafficStop.setAgencyTitle(agencyTitle);
			}

			if(saves.contains("stopDateTime")) {
				Date stopDateTime = (Date)solrDocument.get("stopDateTime_stored_date");
				if(stopDateTime != null)
					oTrafficStop.setStopDateTime(stopDateTime);
			}

			if(saves.contains("stopYear")) {
				Integer stopYear = (Integer)solrDocument.get("stopYear_stored_int");
				if(stopYear != null)
					oTrafficStop.setStopYear(stopYear);
			}

			if(saves.contains("stopPurposeNum")) {
				Integer stopPurposeNum = (Integer)solrDocument.get("stopPurposeNum_stored_int");
				if(stopPurposeNum != null)
					oTrafficStop.setStopPurposeNum(stopPurposeNum);
			}

			if(saves.contains("stopPurposeTitle")) {
				String stopPurposeTitle = (String)solrDocument.get("stopPurposeTitle_stored_string");
				if(stopPurposeTitle != null)
					oTrafficStop.setStopPurposeTitle(stopPurposeTitle);
			}

			if(saves.contains("stopActionNum")) {
				Integer stopActionNum = (Integer)solrDocument.get("stopActionNum_stored_int");
				if(stopActionNum != null)
					oTrafficStop.setStopActionNum(stopActionNum);
			}

			if(saves.contains("stopActionTitle")) {
				String stopActionTitle = (String)solrDocument.get("stopActionTitle_stored_string");
				if(stopActionTitle != null)
					oTrafficStop.setStopActionTitle(stopActionTitle);
			}

			if(saves.contains("stopDriverArrest")) {
				Boolean stopDriverArrest = (Boolean)solrDocument.get("stopDriverArrest_stored_boolean");
				if(stopDriverArrest != null)
					oTrafficStop.setStopDriverArrest(stopDriverArrest);
			}

			if(saves.contains("stopPassengerArrest")) {
				Boolean stopPassengerArrest = (Boolean)solrDocument.get("stopPassengerArrest_stored_boolean");
				if(stopPassengerArrest != null)
					oTrafficStop.setStopPassengerArrest(stopPassengerArrest);
			}

			if(saves.contains("stopEncounterForce")) {
				Boolean stopEncounterForce = (Boolean)solrDocument.get("stopEncounterForce_stored_boolean");
				if(stopEncounterForce != null)
					oTrafficStop.setStopEncounterForce(stopEncounterForce);
			}

			if(saves.contains("stopEngageForce")) {
				Boolean stopEngageForce = (Boolean)solrDocument.get("stopEngageForce_stored_boolean");
				if(stopEngageForce != null)
					oTrafficStop.setStopEngageForce(stopEngageForce);
			}

			if(saves.contains("stopOfficerInjury")) {
				Boolean stopOfficerInjury = (Boolean)solrDocument.get("stopOfficerInjury_stored_boolean");
				if(stopOfficerInjury != null)
					oTrafficStop.setStopOfficerInjury(stopOfficerInjury);
			}

			if(saves.contains("stopDriverInjury")) {
				Boolean stopDriverInjury = (Boolean)solrDocument.get("stopDriverInjury_stored_boolean");
				if(stopDriverInjury != null)
					oTrafficStop.setStopDriverInjury(stopDriverInjury);
			}

			if(saves.contains("stopPassengerInjury")) {
				Boolean stopPassengerInjury = (Boolean)solrDocument.get("stopPassengerInjury_stored_boolean");
				if(stopPassengerInjury != null)
					oTrafficStop.setStopPassengerInjury(stopPassengerInjury);
			}

			if(saves.contains("stopOfficerId")) {
				String stopOfficerId = (String)solrDocument.get("stopOfficerId_stored_string");
				if(stopOfficerId != null)
					oTrafficStop.setStopOfficerId(stopOfficerId);
			}

			if(saves.contains("stopLocationId")) {
				String stopLocationId = (String)solrDocument.get("stopLocationId_stored_string");
				if(stopLocationId != null)
					oTrafficStop.setStopLocationId(stopLocationId);
			}

			if(saves.contains("stopCityId")) {
				String stopCityId = (String)solrDocument.get("stopCityId_stored_string");
				if(stopCityId != null)
					oTrafficStop.setStopCityId(stopCityId);
			}

			if(saves.contains("personRaceTitles")) {
				List<String> personRaceTitles = (List<String>)solrDocument.get("personRaceTitles_stored_strings");
				if(personRaceTitles != null)
					oTrafficStop.personRaceTitles.addAll(personRaceTitles);
			}

			if(saves.contains("trafficSearchRaceTitles")) {
				List<String> trafficSearchRaceTitles = (List<String>)solrDocument.get("trafficSearchRaceTitles_stored_strings");
				if(trafficSearchRaceTitles != null)
					oTrafficStop.trafficSearchRaceTitles.addAll(trafficSearchRaceTitles);
			}
		}

		super.populateCluster(solrDocument);
	}

	public void indexTrafficStop(SolrInputDocument document) {
		if(stateAbbreviation != null) {
			document.addField("stateAbbreviation_indexed_string", stateAbbreviation);
			document.addField("stateAbbreviation_stored_string", stateAbbreviation);
		}
		if(stateName != null) {
			document.addField("stateName_indexed_string", stateName);
			document.addField("stateName_stored_string", stateName);
		}
		if(agencyTitle != null) {
			document.addField("agencyTitle_indexed_string", agencyTitle);
			document.addField("agencyTitle_stored_string", agencyTitle);
		}
		if(stopDateTime != null) {
			document.addField("stopDateTime_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(stopDateTime.toInstant(), ZoneId.of("UTC"))));
			document.addField("stopDateTime_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(stopDateTime.toInstant(), ZoneId.of("UTC"))));
		}
		if(stopYear != null) {
			document.addField("stopYear_indexed_int", stopYear);
			document.addField("stopYear_stored_int", stopYear);
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
		if(personRaceTitles != null) {
			for(java.lang.String o : personRaceTitles) {
				document.addField("personRaceTitles_indexed_strings", o);
			}
			for(java.lang.String o : personRaceTitles) {
				document.addField("personRaceTitles_stored_strings", o);
			}
		}
		if(trafficSearchRaceTitles != null) {
			for(java.lang.String o : trafficSearchRaceTitles) {
				document.addField("trafficSearchRaceTitles_indexed_strings", o);
			}
			for(java.lang.String o : trafficSearchRaceTitles) {
				document.addField("trafficSearchRaceTitles_stored_strings", o);
			}
		}
		super.indexCluster(document);

	}

	public static String varIndexedTrafficStop(String entityVar) {
		switch(entityVar) {
			case "stateAbbreviation":
				return "stateAbbreviation_indexed_string";
			case "stateName":
				return "stateName_indexed_string";
			case "agencyTitle":
				return "agencyTitle_indexed_string";
			case "stopDateTime":
				return "stopDateTime_indexed_date";
			case "stopYear":
				return "stopYear_indexed_int";
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
			case "personRaceTitles":
				return "personRaceTitles_indexed_strings";
			case "trafficSearchRaceTitles":
				return "trafficSearchRaceTitles_indexed_strings";
			default:
				return Cluster.varIndexedCluster(entityVar);
		}
	}

	public static String varSearchTrafficStop(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSearchCluster(entityVar);
		}
	}

	public static String varSuggestedTrafficStop(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSuggestedCluster(entityVar);
		}
	}

	/////////////
	// store //
	/////////////

	@Override public void storeForClass(SolrDocument solrDocument) {
		storeTrafficStop(solrDocument);
	}
	public void storeTrafficStop(SolrDocument solrDocument) {
		TrafficStop oTrafficStop = (TrafficStop)this;

		oTrafficStop.setStateAbbreviation(Optional.ofNullable(solrDocument.get("stateAbbreviation_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficStop.setStateName(Optional.ofNullable(solrDocument.get("stateName_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficStop.setAgencyTitle(Optional.ofNullable(solrDocument.get("agencyTitle_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficStop.setStopDateTime(Optional.ofNullable(solrDocument.get("stopDateTime_stored_date")).map(v -> v.toString()).orElse(null));
		oTrafficStop.setStopYear(Optional.ofNullable(solrDocument.get("stopYear_stored_int")).map(v -> v.toString()).orElse(null));
		oTrafficStop.setStopPurposeNum(Optional.ofNullable(solrDocument.get("stopPurposeNum_stored_int")).map(v -> v.toString()).orElse(null));
		oTrafficStop.setStopPurposeTitle(Optional.ofNullable(solrDocument.get("stopPurposeTitle_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficStop.setStopActionNum(Optional.ofNullable(solrDocument.get("stopActionNum_stored_int")).map(v -> v.toString()).orElse(null));
		oTrafficStop.setStopActionTitle(Optional.ofNullable(solrDocument.get("stopActionTitle_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficStop.setStopDriverArrest(Optional.ofNullable(solrDocument.get("stopDriverArrest_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficStop.setStopPassengerArrest(Optional.ofNullable(solrDocument.get("stopPassengerArrest_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficStop.setStopEncounterForce(Optional.ofNullable(solrDocument.get("stopEncounterForce_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficStop.setStopEngageForce(Optional.ofNullable(solrDocument.get("stopEngageForce_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficStop.setStopOfficerInjury(Optional.ofNullable(solrDocument.get("stopOfficerInjury_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficStop.setStopDriverInjury(Optional.ofNullable(solrDocument.get("stopDriverInjury_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficStop.setStopPassengerInjury(Optional.ofNullable(solrDocument.get("stopPassengerInjury_stored_boolean")).map(v -> v.toString()).orElse(null));
		oTrafficStop.setStopOfficerId(Optional.ofNullable(solrDocument.get("stopOfficerId_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficStop.setStopLocationId(Optional.ofNullable(solrDocument.get("stopLocationId_stored_string")).map(v -> v.toString()).orElse(null));
		oTrafficStop.setStopCityId(Optional.ofNullable(solrDocument.get("stopCityId_stored_string")).map(v -> v.toString()).orElse(null));
		Optional.ofNullable((List<?>)solrDocument.get("personRaceTitles_stored_strings")).orElse(Arrays.asList()).stream().filter(v -> v != null).forEach(v -> {
			oTrafficStop.addPersonRaceTitles(v.toString());
		});
		Optional.ofNullable((List<?>)solrDocument.get("trafficSearchRaceTitles_stored_strings")).orElse(Arrays.asList()).stream().filter(v -> v != null).forEach(v -> {
			oTrafficStop.addTrafficSearchRaceTitles(v.toString());
		});

		super.storeCluster(solrDocument);
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestTrafficStop() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof TrafficStop) {
			TrafficStop original = (TrafficStop)o;
			if(!Objects.equals(stateAbbreviation, original.getStateAbbreviation()))
				apiRequest.addVars("stateAbbreviation");
			if(!Objects.equals(stateName, original.getStateName()))
				apiRequest.addVars("stateName");
			if(!Objects.equals(agencyTitle, original.getAgencyTitle()))
				apiRequest.addVars("agencyTitle");
			if(!Objects.equals(stopDateTime, original.getStopDateTime()))
				apiRequest.addVars("stopDateTime");
			if(!Objects.equals(stopYear, original.getStopYear()))
				apiRequest.addVars("stopYear");
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
			if(!Objects.equals(personRaceTitles, original.getPersonRaceTitles()))
				apiRequest.addVars("personRaceTitles");
			if(!Objects.equals(trafficSearchRaceTitles, original.getTrafficSearchRaceTitles()))
				apiRequest.addVars("trafficSearchRaceTitles");
			super.apiRequestCluster();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), stateAbbreviation, stateName, agencyTitle, stopDateTime, stopYear, stopPurposeNum, stopPurposeTitle, stopActionNum, stopActionTitle, stopDriverArrest, stopPassengerArrest, stopEncounterForce, stopEngageForce, stopOfficerInjury, stopDriverInjury, stopPassengerInjury, stopOfficerId, stopLocationId, stopCityId, personRaceTitles, trafficSearchRaceTitles);
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof TrafficStop))
			return false;
		TrafficStop that = (TrafficStop)o;
		return super.equals(o)
				&& Objects.equals( stateAbbreviation, that.stateAbbreviation )
				&& Objects.equals( stateName, that.stateName )
				&& Objects.equals( agencyTitle, that.agencyTitle )
				&& Objects.equals( stopDateTime, that.stopDateTime )
				&& Objects.equals( stopYear, that.stopYear )
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
				&& Objects.equals( personRaceTitles, that.personRaceTitles )
				&& Objects.equals( trafficSearchRaceTitles, that.trafficSearchRaceTitles );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("TrafficStop { ");
		sb.append( "stateAbbreviation: \"" ).append(stateAbbreviation).append( "\"" );
		sb.append( ", stateName: \"" ).append(stateName).append( "\"" );
		sb.append( ", agencyTitle: \"" ).append(agencyTitle).append( "\"" );
		sb.append( ", stopDateTime: " ).append(stopDateTime);
		sb.append( ", stopYear: " ).append(stopYear);
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
		sb.append( ", personRaceTitles: " ).append(personRaceTitles);
		sb.append( ", trafficSearchRaceTitles: " ).append(trafficSearchRaceTitles);
		sb.append(" }");
		return sb.toString();
	}

	public static final String VAR_stateAbbreviation = "stateAbbreviation";
	public static final String VAR_stateName = "stateName";
	public static final String VAR_agencyTitle = "agencyTitle";
	public static final String VAR_stopDateTime = "stopDateTime";
	public static final String VAR_stopYear = "stopYear";
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
	public static final String VAR_personSearch = "personSearch";
	public static final String VAR_personRaceTitles = "personRaceTitles";
	public static final String VAR_trafficSearchSearch = "trafficSearchSearch";
	public static final String VAR_trafficSearchRaceTitles = "trafficSearchRaceTitles";
}
