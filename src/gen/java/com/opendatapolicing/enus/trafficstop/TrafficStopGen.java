package com.opendatapolicing.enus.trafficstop;

import java.util.Arrays;
import java.util.Date;
import java.time.ZonedDateTime;
import org.apache.commons.lang3.StringUtils;
import java.lang.Integer;
import java.lang.Long;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.util.Locale;
import io.vertx.core.json.JsonObject;
import java.time.ZoneOffset;
import io.vertx.core.logging.Logger;
import java.math.RoundingMode;
import com.opendatapolicing.enus.wrap.Wrap;
import java.math.MathContext;
import java.util.Set;
import com.opendatapolicing.enus.writer.AllWriter;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import com.opendatapolicing.enus.context.SiteContextEnUS;
import com.opendatapolicing.enus.request.api.ApiRequest;
import java.time.ZoneId;
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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.text.NumberFormat;
import io.vertx.core.logging.LoggerFactory;
import com.opendatapolicing.enus.search.SearchList;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.Boolean;
import java.lang.String;
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
import com.opendatapolicing.enus.agency.SiteAgency;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class TrafficStopGen<DEV> extends Cluster {
	protected static final Logger LOGGER = LoggerFactory.getLogger(TrafficStop.class);

	public static final String TrafficStop_AName = "a traffic stop";
	public static final String TrafficStop_This = "this ";
	public static final String TrafficStop_ThisName = "this traffic stop";
	public static final String TrafficStop_A = "a ";
	public static final String TrafficStop_TheName = "the traffic stop";
	public static final String TrafficStop_NameSingular = "traffic stop";
	public static final String TrafficStop_NamePlural = "traffic stops";
	public static final String TrafficStop_NameActual = "current traffic stop";
	public static final String TrafficStop_AllName = "all the traffic stops";
	public static final String TrafficStop_SearchAllNameBy = "search traffic stops by ";
	public static final String TrafficStop_Title = "traffic stops";
	public static final String TrafficStop_ThePluralName = "the traffic stops";
	public static final String TrafficStop_NoNameFound = "no traffic stop found";
	public static final String TrafficStop_NameVar = "trafficStop";
	public static final String TrafficStop_OfName = "of traffic stop";
	public static final String TrafficStop_ANameAdjective = "a traffic stop";
	public static final String TrafficStop_NameAdjectiveSingular = "traffic stop";
	public static final String TrafficStop_NameAdjectivePlural = "traffic stops";
	public static final String TrafficStop_Color = "pale-green";
	public static final String TrafficStop_IconGroup = "regular";
	public static final String TrafficStop_IconName = "newspaper";

	////////////////////
	// trafficStopKey //
	////////////////////

	/**	 The entity trafficStopKey
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long trafficStopKey;
	@JsonIgnore
	public Wrap<Long> trafficStopKeyWrap = new Wrap<Long>().p(this).c(Long.class).var("trafficStopKey").o(trafficStopKey);

	/**	<br/> The entity trafficStopKey
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:trafficStopKey">Find the entity trafficStopKey in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _trafficStopKey(Wrap<Long> c);

	public Long getTrafficStopKey() {
		return trafficStopKey;
	}

	public void setTrafficStopKey(Long trafficStopKey) {
		this.trafficStopKey = trafficStopKey;
		this.trafficStopKeyWrap.alreadyInitialized = true;
	}
	public void setTrafficStopKey(String o) {
		this.trafficStopKey = TrafficStop.staticSetTrafficStopKey(siteRequest_, o);
		this.trafficStopKeyWrap.alreadyInitialized = true;
	}
	public static Long staticSetTrafficStopKey(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected TrafficStop trafficStopKeyInit() {
		if(!trafficStopKeyWrap.alreadyInitialized) {
			_trafficStopKey(trafficStopKeyWrap);
			if(trafficStopKey == null)
				setTrafficStopKey(trafficStopKeyWrap.o);
		}
		trafficStopKeyWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static Long staticSolrTrafficStopKey(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrTrafficStopKey(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqTrafficStopKey(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrTrafficStopKey(siteRequest_, TrafficStop.staticSolrTrafficStopKey(siteRequest_, TrafficStop.staticSetTrafficStopKey(siteRequest_, o)));
	}

	public Long solrTrafficStopKey() {
		return TrafficStop.staticSolrTrafficStopKey(siteRequest_, trafficStopKey);
	}

	public String strTrafficStopKey() {
		return trafficStopKey == null ? "" : trafficStopKey.toString();
	}

	public String jsonTrafficStopKey() {
		return trafficStopKey == null ? "" : trafficStopKey.toString();
	}

	public String nomAffichageTrafficStopKey() {
		return null;
	}

	public String htmTooltipTrafficStopKey() {
		return null;
	}

	public String htmTrafficStopKey() {
		return trafficStopKey == null ? "" : StringEscapeUtils.escapeHtml4(strTrafficStopKey());
	}

	///////////////
	// agencyKey //
	///////////////

	/**	 The entity agencyKey
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long agencyKey;
	@JsonIgnore
	public Wrap<Long> agencyKeyWrap = new Wrap<Long>().p(this).c(Long.class).var("agencyKey").o(agencyKey);

	/**	<br/> The entity agencyKey
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:agencyKey">Find the entity agencyKey in Solr</a>
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
	public void setAgencyKey(String o) {
		this.agencyKey = TrafficStop.staticSetAgencyKey(siteRequest_, o);
		this.agencyKeyWrap.alreadyInitialized = true;
	}
	public static Long staticSetAgencyKey(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected TrafficStop agencyKeyInit() {
		if(!agencyKeyWrap.alreadyInitialized) {
			_agencyKey(agencyKeyWrap);
			if(agencyKey == null)
				setAgencyKey(agencyKeyWrap.o);
		}
		agencyKeyWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static Long staticSolrAgencyKey(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrAgencyKey(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqAgencyKey(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrAgencyKey(siteRequest_, TrafficStop.staticSolrAgencyKey(siteRequest_, TrafficStop.staticSetAgencyKey(siteRequest_, o)));
	}

	public Long solrAgencyKey() {
		return TrafficStop.staticSolrAgencyKey(siteRequest_, agencyKey);
	}

	public String strAgencyKey() {
		return agencyKey == null ? "" : agencyKey.toString();
	}

	public String jsonAgencyKey() {
		return agencyKey == null ? "" : agencyKey.toString();
	}

	public String nomAffichageAgencyKey() {
		return null;
	}

	public String htmTooltipAgencyKey() {
		return null;
	}

	public String htmAgencyKey() {
		return agencyKey == null ? "" : StringEscapeUtils.escapeHtml4(strAgencyKey());
	}

	//////////////////
	// agencySearch //
	//////////////////

	/**	 The entity agencySearch
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<SiteAgency>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SearchList<SiteAgency> agencySearch = new SearchList<SiteAgency>();
	@JsonIgnore
	public Wrap<SearchList<SiteAgency>> agencySearchWrap = new Wrap<SearchList<SiteAgency>>().p(this).c(SearchList.class).var("agencySearch").o(agencySearch);

	/**	<br/> The entity agencySearch
	 *  It is constructed before being initialized with the constructor by default SearchList<SiteAgency>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:agencySearch">Find the entity agencySearch in Solr</a>
	 * <br/>
	 * @param agencySearch is the entity already constructed. 
	 **/
	protected abstract void _agencySearch(SearchList<SiteAgency> l);

	public SearchList<SiteAgency> getAgencySearch() {
		return agencySearch;
	}

	public void setAgencySearch(SearchList<SiteAgency> agencySearch) {
		this.agencySearch = agencySearch;
		this.agencySearchWrap.alreadyInitialized = true;
	}
	public static SearchList<SiteAgency> staticSetAgencySearch(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected TrafficStop agencySearchInit() {
		if(!agencySearchWrap.alreadyInitialized) {
			_agencySearch(agencySearch);
		}
		agencySearch.initDeepForClass(siteRequest_);
		agencySearchWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	/////////////
	// agency_ //
	/////////////

	/**	 The entity agency_
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SiteAgency agency_;
	@JsonIgnore
	public Wrap<SiteAgency> agency_Wrap = new Wrap<SiteAgency>().p(this).c(SiteAgency.class).var("agency_").o(agency_);

	/**	<br/> The entity agency_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:agency_">Find the entity agency_ in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _agency_(Wrap<SiteAgency> c);

	public SiteAgency getAgency_() {
		return agency_;
	}

	public void setAgency_(SiteAgency agency_) {
		this.agency_ = agency_;
		this.agency_Wrap.alreadyInitialized = true;
	}
	public static SiteAgency staticSetAgency_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected TrafficStop agency_Init() {
		if(!agency_Wrap.alreadyInitialized) {
			_agency_(agency_Wrap);
			if(agency_ == null)
				setAgency_(agency_Wrap.o);
		}
		agency_Wrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	/////////////////////
	// stopAgencyTitle //
	/////////////////////

	/**	 The entity stopAgencyTitle
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String stopAgencyTitle;
	@JsonIgnore
	public Wrap<String> stopAgencyTitleWrap = new Wrap<String>().p(this).c(String.class).var("stopAgencyTitle").o(stopAgencyTitle);

	/**	<br/> The entity stopAgencyTitle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopAgencyTitle">Find the entity stopAgencyTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopAgencyTitle(Wrap<String> w);

	public String getStopAgencyTitle() {
		return stopAgencyTitle;
	}
	public void setStopAgencyTitle(String o) {
		this.stopAgencyTitle = TrafficStop.staticSetStopAgencyTitle(siteRequest_, o);
		this.stopAgencyTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetStopAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficStop stopAgencyTitleInit() {
		if(!stopAgencyTitleWrap.alreadyInitialized) {
			_stopAgencyTitle(stopAgencyTitleWrap);
			if(stopAgencyTitle == null)
				setStopAgencyTitle(stopAgencyTitleWrap.o);
		}
		stopAgencyTitleWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static String staticSolrStopAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrStopAgencyTitle(siteRequest_, TrafficStop.staticSolrStopAgencyTitle(siteRequest_, TrafficStop.staticSetStopAgencyTitle(siteRequest_, o)));
	}

	public String solrStopAgencyTitle() {
		return TrafficStop.staticSolrStopAgencyTitle(siteRequest_, stopAgencyTitle);
	}

	public String strStopAgencyTitle() {
		return stopAgencyTitle == null ? "" : stopAgencyTitle;
	}

	public String jsonStopAgencyTitle() {
		return stopAgencyTitle == null ? "" : stopAgencyTitle;
	}

	public String nomAffichageStopAgencyTitle() {
		return null;
	}

	public String htmTooltipStopAgencyTitle() {
		return null;
	}

	public String htmStopAgencyTitle() {
		return stopAgencyTitle == null ? "" : StringEscapeUtils.escapeHtml4(strStopAgencyTitle());
	}

	//////////////////
	// stopDateTime //
	//////////////////

	/**	 The entity stopDateTime
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected ZonedDateTime stopDateTime;
	@JsonIgnore
	public Wrap<ZonedDateTime> stopDateTimeWrap = new Wrap<ZonedDateTime>().p(this).c(ZonedDateTime.class).var("stopDateTime").o(stopDateTime);

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
	public void setStopDateTime(Instant o) {
		this.stopDateTime = o == null ? null : ZonedDateTime.from(o).truncatedTo(ChronoUnit.MILLIS);
		this.stopDateTimeWrap.alreadyInitialized = true;
	}
	/** Example: 2011-12-03T10:15:30+01:00 **/
	public void setStopDateTime(String o) {
		this.stopDateTime = TrafficStop.staticSetStopDateTime(siteRequest_, o);
		this.stopDateTimeWrap.alreadyInitialized = true;
	}
	public static ZonedDateTime staticSetStopDateTime(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : ZonedDateTime.parse(o, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone()))).truncatedTo(ChronoUnit.MILLIS);
	}
	public void setStopDateTime(Date o) {
		this.stopDateTime = o == null ? null : ZonedDateTime.ofInstant(o.toInstant(), ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).truncatedTo(ChronoUnit.MILLIS);
		this.stopDateTimeWrap.alreadyInitialized = true;
	}
	protected TrafficStop stopDateTimeInit() {
		if(!stopDateTimeWrap.alreadyInitialized) {
			_stopDateTime(stopDateTimeWrap);
			if(stopDateTime == null)
				setStopDateTime(stopDateTimeWrap.o);
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

	public String jsonStopDateTime() {
		return stopDateTime == null ? "" : stopDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
	}

	public String nomAffichageStopDateTime() {
		return null;
	}

	public String htmTooltipStopDateTime() {
		return null;
	}

	public String htmStopDateTime() {
		return stopDateTime == null ? "" : StringEscapeUtils.escapeHtml4(strStopDateTime());
	}

	////////////////////
	// stopPurposeNum //
	////////////////////

	/**	 The entity stopPurposeNum
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer stopPurposeNum;
	@JsonIgnore
	public Wrap<Integer> stopPurposeNumWrap = new Wrap<Integer>().p(this).c(Integer.class).var("stopPurposeNum").o(stopPurposeNum);

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

	public String jsonStopPurposeNum() {
		return stopPurposeNum == null ? "" : stopPurposeNum.toString();
	}

	public String nomAffichageStopPurposeNum() {
		return null;
	}

	public String htmTooltipStopPurposeNum() {
		return null;
	}

	public String htmStopPurposeNum() {
		return stopPurposeNum == null ? "" : StringEscapeUtils.escapeHtml4(strStopPurposeNum());
	}

	//////////////////////
	// stopPurposeTitle //
	//////////////////////

	/**	 The entity stopPurposeTitle
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String stopPurposeTitle;
	@JsonIgnore
	public Wrap<String> stopPurposeTitleWrap = new Wrap<String>().p(this).c(String.class).var("stopPurposeTitle").o(stopPurposeTitle);

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

	public String jsonStopPurposeTitle() {
		return stopPurposeTitle == null ? "" : stopPurposeTitle;
	}

	public String nomAffichageStopPurposeTitle() {
		return null;
	}

	public String htmTooltipStopPurposeTitle() {
		return null;
	}

	public String htmStopPurposeTitle() {
		return stopPurposeTitle == null ? "" : StringEscapeUtils.escapeHtml4(strStopPurposeTitle());
	}

	///////////////////
	// stopActionNum //
	///////////////////

	/**	 The entity stopActionNum
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer stopActionNum;
	@JsonIgnore
	public Wrap<Integer> stopActionNumWrap = new Wrap<Integer>().p(this).c(Integer.class).var("stopActionNum").o(stopActionNum);

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

	public String jsonStopActionNum() {
		return stopActionNum == null ? "" : stopActionNum.toString();
	}

	public String nomAffichageStopActionNum() {
		return null;
	}

	public String htmTooltipStopActionNum() {
		return null;
	}

	public String htmStopActionNum() {
		return stopActionNum == null ? "" : StringEscapeUtils.escapeHtml4(strStopActionNum());
	}

	/////////////////////
	// stopActionTitle //
	/////////////////////

	/**	 The entity stopActionTitle
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String stopActionTitle;
	@JsonIgnore
	public Wrap<String> stopActionTitleWrap = new Wrap<String>().p(this).c(String.class).var("stopActionTitle").o(stopActionTitle);

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

	public String jsonStopActionTitle() {
		return stopActionTitle == null ? "" : stopActionTitle;
	}

	public String nomAffichageStopActionTitle() {
		return null;
	}

	public String htmTooltipStopActionTitle() {
		return null;
	}

	public String htmStopActionTitle() {
		return stopActionTitle == null ? "" : StringEscapeUtils.escapeHtml4(strStopActionTitle());
	}

	//////////////////////
	// stopDriverArrest //
	//////////////////////

	/**	 The entity stopDriverArrest
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean stopDriverArrest;
	@JsonIgnore
	public Wrap<Boolean> stopDriverArrestWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("stopDriverArrest").o(stopDriverArrest);

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

	public String jsonStopDriverArrest() {
		return stopDriverArrest == null ? "" : stopDriverArrest.toString();
	}

	public String nomAffichageStopDriverArrest() {
		return null;
	}

	public String htmTooltipStopDriverArrest() {
		return null;
	}

	public String htmStopDriverArrest() {
		return stopDriverArrest == null ? "" : StringEscapeUtils.escapeHtml4(strStopDriverArrest());
	}

	/////////////////////////
	// stopPassengerArrest //
	/////////////////////////

	/**	 The entity stopPassengerArrest
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean stopPassengerArrest;
	@JsonIgnore
	public Wrap<Boolean> stopPassengerArrestWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("stopPassengerArrest").o(stopPassengerArrest);

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

	public String jsonStopPassengerArrest() {
		return stopPassengerArrest == null ? "" : stopPassengerArrest.toString();
	}

	public String nomAffichageStopPassengerArrest() {
		return null;
	}

	public String htmTooltipStopPassengerArrest() {
		return null;
	}

	public String htmStopPassengerArrest() {
		return stopPassengerArrest == null ? "" : StringEscapeUtils.escapeHtml4(strStopPassengerArrest());
	}

	////////////////////////
	// stopEncounterForce //
	////////////////////////

	/**	 The entity stopEncounterForce
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean stopEncounterForce;
	@JsonIgnore
	public Wrap<Boolean> stopEncounterForceWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("stopEncounterForce").o(stopEncounterForce);

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

	public String jsonStopEncounterForce() {
		return stopEncounterForce == null ? "" : stopEncounterForce.toString();
	}

	public String nomAffichageStopEncounterForce() {
		return null;
	}

	public String htmTooltipStopEncounterForce() {
		return null;
	}

	public String htmStopEncounterForce() {
		return stopEncounterForce == null ? "" : StringEscapeUtils.escapeHtml4(strStopEncounterForce());
	}

	/////////////////////
	// stopEngageForce //
	/////////////////////

	/**	 The entity stopEngageForce
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean stopEngageForce;
	@JsonIgnore
	public Wrap<Boolean> stopEngageForceWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("stopEngageForce").o(stopEngageForce);

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

	public String jsonStopEngageForce() {
		return stopEngageForce == null ? "" : stopEngageForce.toString();
	}

	public String nomAffichageStopEngageForce() {
		return null;
	}

	public String htmTooltipStopEngageForce() {
		return null;
	}

	public String htmStopEngageForce() {
		return stopEngageForce == null ? "" : StringEscapeUtils.escapeHtml4(strStopEngageForce());
	}

	///////////////////////
	// stopOfficerInjury //
	///////////////////////

	/**	 The entity stopOfficerInjury
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean stopOfficerInjury;
	@JsonIgnore
	public Wrap<Boolean> stopOfficerInjuryWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("stopOfficerInjury").o(stopOfficerInjury);

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

	public String jsonStopOfficerInjury() {
		return stopOfficerInjury == null ? "" : stopOfficerInjury.toString();
	}

	public String nomAffichageStopOfficerInjury() {
		return null;
	}

	public String htmTooltipStopOfficerInjury() {
		return null;
	}

	public String htmStopOfficerInjury() {
		return stopOfficerInjury == null ? "" : StringEscapeUtils.escapeHtml4(strStopOfficerInjury());
	}

	//////////////////////
	// stopDriverInjury //
	//////////////////////

	/**	 The entity stopDriverInjury
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean stopDriverInjury;
	@JsonIgnore
	public Wrap<Boolean> stopDriverInjuryWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("stopDriverInjury").o(stopDriverInjury);

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

	public String jsonStopDriverInjury() {
		return stopDriverInjury == null ? "" : stopDriverInjury.toString();
	}

	public String nomAffichageStopDriverInjury() {
		return null;
	}

	public String htmTooltipStopDriverInjury() {
		return null;
	}

	public String htmStopDriverInjury() {
		return stopDriverInjury == null ? "" : StringEscapeUtils.escapeHtml4(strStopDriverInjury());
	}

	/////////////////////////
	// stopPassengerInjury //
	/////////////////////////

	/**	 The entity stopPassengerInjury
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean stopPassengerInjury;
	@JsonIgnore
	public Wrap<Boolean> stopPassengerInjuryWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("stopPassengerInjury").o(stopPassengerInjury);

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

	public String jsonStopPassengerInjury() {
		return stopPassengerInjury == null ? "" : stopPassengerInjury.toString();
	}

	public String nomAffichageStopPassengerInjury() {
		return null;
	}

	public String htmTooltipStopPassengerInjury() {
		return null;
	}

	public String htmStopPassengerInjury() {
		return stopPassengerInjury == null ? "" : StringEscapeUtils.escapeHtml4(strStopPassengerInjury());
	}

	///////////////////
	// stopOfficerId //
	///////////////////

	/**	 The entity stopOfficerId
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String stopOfficerId;
	@JsonIgnore
	public Wrap<String> stopOfficerIdWrap = new Wrap<String>().p(this).c(String.class).var("stopOfficerId").o(stopOfficerId);

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
		return o;
	}
	protected TrafficStop stopOfficerIdInit() {
		if(!stopOfficerIdWrap.alreadyInitialized) {
			_stopOfficerId(stopOfficerIdWrap);
			if(stopOfficerId == null)
				setStopOfficerId(stopOfficerIdWrap.o);
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

	public String jsonStopOfficerId() {
		return stopOfficerId == null ? "" : stopOfficerId;
	}

	public String nomAffichageStopOfficerId() {
		return null;
	}

	public String htmTooltipStopOfficerId() {
		return null;
	}

	public String htmStopOfficerId() {
		return stopOfficerId == null ? "" : StringEscapeUtils.escapeHtml4(strStopOfficerId());
	}

	////////////////////
	// stopLocationId //
	////////////////////

	/**	 The entity stopLocationId
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String stopLocationId;
	@JsonIgnore
	public Wrap<String> stopLocationIdWrap = new Wrap<String>().p(this).c(String.class).var("stopLocationId").o(stopLocationId);

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
		return o;
	}
	protected TrafficStop stopLocationIdInit() {
		if(!stopLocationIdWrap.alreadyInitialized) {
			_stopLocationId(stopLocationIdWrap);
			if(stopLocationId == null)
				setStopLocationId(stopLocationIdWrap.o);
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

	public String jsonStopLocationId() {
		return stopLocationId == null ? "" : stopLocationId;
	}

	public String nomAffichageStopLocationId() {
		return null;
	}

	public String htmTooltipStopLocationId() {
		return null;
	}

	public String htmStopLocationId() {
		return stopLocationId == null ? "" : StringEscapeUtils.escapeHtml4(strStopLocationId());
	}

	////////////////
	// stopCityId //
	////////////////

	/**	 The entity stopCityId
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String stopCityId;
	@JsonIgnore
	public Wrap<String> stopCityIdWrap = new Wrap<String>().p(this).c(String.class).var("stopCityId").o(stopCityId);

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
		return o;
	}
	protected TrafficStop stopCityIdInit() {
		if(!stopCityIdWrap.alreadyInitialized) {
			_stopCityId(stopCityIdWrap);
			if(stopCityId == null)
				setStopCityId(stopCityIdWrap.o);
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

	public String jsonStopCityId() {
		return stopCityId == null ? "" : stopCityId;
	}

	public String nomAffichageStopCityId() {
		return null;
	}

	public String htmTooltipStopCityId() {
		return null;
	}

	public String htmStopCityId() {
		return stopCityId == null ? "" : StringEscapeUtils.escapeHtml4(strStopCityId());
	}

	////////////////
	// personKeys //
	////////////////

	/**	 The entity personKeys
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> personKeys = new ArrayList<Long>();
	@JsonIgnore
	public Wrap<List<Long>> personKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("personKeys").o(personKeys);

	/**	<br/> The entity personKeys
	 *  It is constructed before being initialized with the constructor by default List<Long>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personKeys">Find the entity personKeys in Solr</a>
	 * <br/>
	 * @param personKeys is the entity already constructed. 
	 **/
	protected abstract void _personKeys(List<Long> c);

	public List<Long> getPersonKeys() {
		return personKeys;
	}

	public void setPersonKeys(List<Long> personKeys) {
		this.personKeys = personKeys;
		this.personKeysWrap.alreadyInitialized = true;
	}
	public void setPersonKeys(String o) {
		Long l = TrafficStop.staticSetPersonKeys(siteRequest_, o);
		if(l != null)
			addPersonKeys(l);
		this.personKeysWrap.alreadyInitialized = true;
	}
	public static Long staticSetPersonKeys(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	public TrafficStop addPersonKeys(Long...objets) {
		for(Long o : objets) {
			addPersonKeys(o);
		}
		return (TrafficStop)this;
	}
	public TrafficStop addPersonKeys(Long o) {
		if(o != null && !personKeys.contains(o))
			this.personKeys.add(o);
		return (TrafficStop)this;
	}
	public void setPersonKeys(JsonArray objets) {
		personKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addPersonKeys(o);
		}
	}
	public TrafficStop addPersonKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addPersonKeys(p);
		}
		return (TrafficStop)this;
	}
	protected TrafficStop personKeysInit() {
		if(!personKeysWrap.alreadyInitialized) {
			_personKeys(personKeys);
		}
		personKeysWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static Long staticSolrPersonKeys(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrPersonKeys(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonKeys(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrPersonKeys(siteRequest_, TrafficStop.staticSolrPersonKeys(siteRequest_, TrafficStop.staticSetPersonKeys(siteRequest_, o)));
	}

	public List<Long> solrPersonKeys() {
		List<Long> l = new ArrayList<Long>();
		for(Long o : personKeys) {
			l.add(TrafficStop.staticSolrPersonKeys(siteRequest_, o));
		}
		return l;
	}

	public String strPersonKeys() {
		return personKeys == null ? "" : personKeys.toString();
	}

	public String jsonPersonKeys() {
		return personKeys == null ? "" : personKeys.toString();
	}

	public String nomAffichagePersonKeys() {
		return null;
	}

	public String htmTooltipPersonKeys() {
		return null;
	}

	public String htmPersonKeys() {
		return personKeys == null ? "" : StringEscapeUtils.escapeHtml4(strPersonKeys());
	}

	/////////////////////////////
	// trafficStopCompleteName //
	/////////////////////////////

	/**	 The entity trafficStopCompleteName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String trafficStopCompleteName;
	@JsonIgnore
	public Wrap<String> trafficStopCompleteNameWrap = new Wrap<String>().p(this).c(String.class).var("trafficStopCompleteName").o(trafficStopCompleteName);

	/**	<br/> The entity trafficStopCompleteName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficstop.TrafficStop&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:trafficStopCompleteName">Find the entity trafficStopCompleteName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _trafficStopCompleteName(Wrap<String> c);

	public String getTrafficStopCompleteName() {
		return trafficStopCompleteName;
	}
	public void setTrafficStopCompleteName(String o) {
		this.trafficStopCompleteName = TrafficStop.staticSetTrafficStopCompleteName(siteRequest_, o);
		this.trafficStopCompleteNameWrap.alreadyInitialized = true;
	}
	public static String staticSetTrafficStopCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficStop trafficStopCompleteNameInit() {
		if(!trafficStopCompleteNameWrap.alreadyInitialized) {
			_trafficStopCompleteName(trafficStopCompleteNameWrap);
			if(trafficStopCompleteName == null)
				setTrafficStopCompleteName(trafficStopCompleteNameWrap.o);
		}
		trafficStopCompleteNameWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	public static String staticSolrTrafficStopCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrTrafficStopCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqTrafficStopCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return TrafficStop.staticSolrStrTrafficStopCompleteName(siteRequest_, TrafficStop.staticSolrTrafficStopCompleteName(siteRequest_, TrafficStop.staticSetTrafficStopCompleteName(siteRequest_, o)));
	}

	public String solrTrafficStopCompleteName() {
		return TrafficStop.staticSolrTrafficStopCompleteName(siteRequest_, trafficStopCompleteName);
	}

	public String strTrafficStopCompleteName() {
		return trafficStopCompleteName == null ? "" : trafficStopCompleteName;
	}

	public String jsonTrafficStopCompleteName() {
		return trafficStopCompleteName == null ? "" : trafficStopCompleteName;
	}

	public String nomAffichageTrafficStopCompleteName() {
		return null;
	}

	public String htmTooltipTrafficStopCompleteName() {
		return null;
	}

	public String htmTrafficStopCompleteName() {
		return trafficStopCompleteName == null ? "" : StringEscapeUtils.escapeHtml4(strTrafficStopCompleteName());
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedTrafficStop = false;

	public TrafficStop initDeepTrafficStop(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedTrafficStop) {
			alreadyInitializedTrafficStop = true;
			initDeepTrafficStop();
		}
		return (TrafficStop)this;
	}

	public void initDeepTrafficStop() {
		initTrafficStop();
		super.initDeepCluster(siteRequest_);
	}

	public void initTrafficStop() {
		trafficStopKeyInit();
		agencyKeyInit();
		agencySearchInit();
		agency_Init();
		stopAgencyTitleInit();
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
		personKeysInit();
		trafficStopCompleteNameInit();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepTrafficStop(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestTrafficStop(SiteRequestEnUS siteRequest_) {
			super.siteRequestCluster(siteRequest_);
		if(agencySearch != null)
			agencySearch.setSiteRequest_(siteRequest_);
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
		}
		return o;
	}
	public Object obtainTrafficStop(String var) {
		TrafficStop oTrafficStop = (TrafficStop)this;
		switch(var) {
			case "trafficStopKey":
				return oTrafficStop.trafficStopKey;
			case "agencyKey":
				return oTrafficStop.agencyKey;
			case "agencySearch":
				return oTrafficStop.agencySearch;
			case "agency_":
				return oTrafficStop.agency_;
			case "stopAgencyTitle":
				return oTrafficStop.stopAgencyTitle;
			case "stopDateTime":
				return oTrafficStop.stopDateTime;
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
			case "personKeys":
				return oTrafficStop.personKeys;
			case "trafficStopCompleteName":
				return oTrafficStop.trafficStopCompleteName;
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
		case "trafficStopKey":
			return TrafficStop.staticSetTrafficStopKey(siteRequest_, o);
		case "agencyKey":
			return TrafficStop.staticSetAgencyKey(siteRequest_, o);
		case "stopAgencyTitle":
			return TrafficStop.staticSetStopAgencyTitle(siteRequest_, o);
		case "stopDateTime":
			return TrafficStop.staticSetStopDateTime(siteRequest_, o);
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
		case "personKeys":
			return TrafficStop.staticSetPersonKeys(siteRequest_, o);
		case "trafficStopCompleteName":
			return TrafficStop.staticSetTrafficStopCompleteName(siteRequest_, o);
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
		case "trafficStopKey":
			return TrafficStop.staticSolrTrafficStopKey(siteRequest_, (Long)o);
		case "agencyKey":
			return TrafficStop.staticSolrAgencyKey(siteRequest_, (Long)o);
		case "stopAgencyTitle":
			return TrafficStop.staticSolrStopAgencyTitle(siteRequest_, (String)o);
		case "stopDateTime":
			return TrafficStop.staticSolrStopDateTime(siteRequest_, (ZonedDateTime)o);
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
		case "personKeys":
			return TrafficStop.staticSolrPersonKeys(siteRequest_, (Long)o);
		case "trafficStopCompleteName":
			return TrafficStop.staticSolrTrafficStopCompleteName(siteRequest_, (String)o);
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
		case "trafficStopKey":
			return TrafficStop.staticSolrStrTrafficStopKey(siteRequest_, (Long)o);
		case "agencyKey":
			return TrafficStop.staticSolrStrAgencyKey(siteRequest_, (Long)o);
		case "stopAgencyTitle":
			return TrafficStop.staticSolrStrStopAgencyTitle(siteRequest_, (String)o);
		case "stopDateTime":
			return TrafficStop.staticSolrStrStopDateTime(siteRequest_, (Date)o);
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
		case "personKeys":
			return TrafficStop.staticSolrStrPersonKeys(siteRequest_, (Long)o);
		case "trafficStopCompleteName":
			return TrafficStop.staticSolrStrTrafficStopCompleteName(siteRequest_, (String)o);
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
		case "trafficStopKey":
			return TrafficStop.staticSolrFqTrafficStopKey(siteRequest_, o);
		case "agencyKey":
			return TrafficStop.staticSolrFqAgencyKey(siteRequest_, o);
		case "stopAgencyTitle":
			return TrafficStop.staticSolrFqStopAgencyTitle(siteRequest_, o);
		case "stopDateTime":
			return TrafficStop.staticSolrFqStopDateTime(siteRequest_, o);
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
		case "personKeys":
			return TrafficStop.staticSolrFqPersonKeys(siteRequest_, o);
		case "trafficStopCompleteName":
			return TrafficStop.staticSolrFqTrafficStopCompleteName(siteRequest_, o);
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
		switch(var) {
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
		}

		super.populateCluster(solrDocument);
	}

	/////////////
	// index //
	/////////////

	public static void index() {
		try {
			SiteRequestEnUS siteRequest = new SiteRequestEnUS();
			siteRequest.initDeepSiteRequestEnUS();
			SiteContextEnUS siteContext = new SiteContextEnUS();
			siteContext.getSiteConfig().setConfigPath("/usr/local/src/opendatapolicing/config/opendatapolicing.config");
			siteContext.initDeepSiteContextEnUS();
			siteRequest.setSiteContext_(siteContext);
			siteRequest.setSiteConfig_(siteContext.getSiteConfig());
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.setQuery("*:*");
			solrQuery.setRows(1);
			solrQuery.addFilterQuery("id:" + ClientUtils.escapeQueryChars("com.opendatapolicing.enus.trafficstop.TrafficStop"));
			QueryResponse queryResponse = siteRequest.getSiteContext_().getSolrClient().query(solrQuery);
			if(queryResponse.getResults().size() > 0)
				siteRequest.setSolrDocument(queryResponse.getResults().get(0));
			TrafficStop o = new TrafficStop();
			o.siteRequestTrafficStop(siteRequest);
			o.initDeepTrafficStop(siteRequest);
			o.indexTrafficStop();
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}


	@Override public void indexForClass() {
		indexTrafficStop();
	}

	@Override public void indexForClass(SolrInputDocument document) {
		indexTrafficStop(document);
	}

	public void indexTrafficStop(SolrClient clientSolr) {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexTrafficStop(document);
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexTrafficStop() {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexTrafficStop(document);
			SolrClient clientSolr = siteRequest_.getSiteContext_().getSolrClient();
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexTrafficStop(SolrInputDocument document) {
		super.indexCluster(document);

	}

	public static String varIndexedTrafficStop(String entityVar) {
		switch(entityVar) {
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
			super.apiRequestCluster();
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
		if(!(o instanceof TrafficStop))
			return false;
		TrafficStop that = (TrafficStop)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("TrafficStop { ");
		sb.append(" }");
		return sb.toString();
	}
}
