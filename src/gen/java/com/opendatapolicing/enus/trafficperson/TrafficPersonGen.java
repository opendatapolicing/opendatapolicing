package com.opendatapolicing.enus.trafficperson;

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
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.opendatapolicing.enus.trafficstop.TrafficStop;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class TrafficPersonGen<DEV> extends Cluster {
	protected static final Logger LOGGER = LoggerFactory.getLogger(TrafficPerson.class);

	public static final String TrafficPerson_AName = "a person";
	public static final String TrafficPerson_This = "this ";
	public static final String TrafficPerson_ThisName = "this person";
	public static final String TrafficPerson_A = "a ";
	public static final String TrafficPerson_TheName = "the person";
	public static final String TrafficPerson_NameSingular = "person";
	public static final String TrafficPerson_NamePlural = "persons";
	public static final String TrafficPerson_NameActual = "current person";
	public static final String TrafficPerson_AllName = "all the persons";
	public static final String TrafficPerson_SearchAllNameBy = "search persons by ";
	public static final String TrafficPerson_Title = "persons";
	public static final String TrafficPerson_ThePluralName = "the persons";
	public static final String TrafficPerson_NoNameFound = "no person found";
	public static final String TrafficPerson_NameVar = "trafficStop";
	public static final String TrafficPerson_OfName = "of person";
	public static final String TrafficPerson_ANameAdjective = "a person";
	public static final String TrafficPerson_NameAdjectiveSingular = "person";
	public static final String TrafficPerson_NameAdjectivePlural = "persons";
	public static final String TrafficPerson_Color = "pale-green";
	public static final String TrafficPerson_IconGroup = "regular";
	public static final String TrafficPerson_IconName = "newspaper";

	///////////////
	// personKey //
	///////////////

	/**	 The entity personKey
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long personKey;
	@JsonIgnore
	public Wrap<Long> personKeyWrap = new Wrap<Long>().p(this).c(Long.class).var("personKey").o(personKey);

	/**	<br/> The entity personKey
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personKey">Find the entity personKey in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personKey(Wrap<Long> c);

	public Long getPersonKey() {
		return personKey;
	}

	public void setPersonKey(Long personKey) {
		this.personKey = personKey;
		this.personKeyWrap.alreadyInitialized = true;
	}
	public void setPersonKey(String o) {
		this.personKey = TrafficPerson.staticSetPersonKey(siteRequest_, o);
		this.personKeyWrap.alreadyInitialized = true;
	}
	public static Long staticSetPersonKey(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected TrafficPerson personKeyInit() {
		if(!personKeyWrap.alreadyInitialized) {
			_personKey(personKeyWrap);
			if(personKey == null)
				setPersonKey(personKeyWrap.o);
		}
		personKeyWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static Long staticSolrPersonKey(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrPersonKey(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonKey(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrPersonKey(siteRequest_, TrafficPerson.staticSolrPersonKey(siteRequest_, TrafficPerson.staticSetPersonKey(siteRequest_, o)));
	}

	public Long solrPersonKey() {
		return TrafficPerson.staticSolrPersonKey(siteRequest_, personKey);
	}

	public String strPersonKey() {
		return personKey == null ? "" : personKey.toString();
	}

	public String jsonPersonKey() {
		return personKey == null ? "" : personKey.toString();
	}

	public String nomAffichagePersonKey() {
		return null;
	}

	public String htmTooltipPersonKey() {
		return null;
	}

	public String htmPersonKey() {
		return personKey == null ? "" : StringEscapeUtils.escapeHtml4(strPersonKey());
	}

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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:trafficStopKey">Find the entity trafficStopKey in Solr</a>
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
		this.trafficStopKey = TrafficPerson.staticSetTrafficStopKey(siteRequest_, o);
		this.trafficStopKeyWrap.alreadyInitialized = true;
	}
	public static Long staticSetTrafficStopKey(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected TrafficPerson trafficStopKeyInit() {
		if(!trafficStopKeyWrap.alreadyInitialized) {
			_trafficStopKey(trafficStopKeyWrap);
			if(trafficStopKey == null)
				setTrafficStopKey(trafficStopKeyWrap.o);
		}
		trafficStopKeyWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static Long staticSolrTrafficStopKey(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrTrafficStopKey(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqTrafficStopKey(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrTrafficStopKey(siteRequest_, TrafficPerson.staticSolrTrafficStopKey(siteRequest_, TrafficPerson.staticSetTrafficStopKey(siteRequest_, o)));
	}

	public Long solrTrafficStopKey() {
		return TrafficPerson.staticSolrTrafficStopKey(siteRequest_, trafficStopKey);
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

	///////////////////////
	// trafficStopSearch //
	///////////////////////

	/**	 The entity trafficStopSearch
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<TrafficStop>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SearchList<TrafficStop> trafficStopSearch = new SearchList<TrafficStop>();
	@JsonIgnore
	public Wrap<SearchList<TrafficStop>> trafficStopSearchWrap = new Wrap<SearchList<TrafficStop>>().p(this).c(SearchList.class).var("trafficStopSearch").o(trafficStopSearch);

	/**	<br/> The entity trafficStopSearch
	 *  It is constructed before being initialized with the constructor by default SearchList<TrafficStop>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:trafficStopSearch">Find the entity trafficStopSearch in Solr</a>
	 * <br/>
	 * @param trafficStopSearch is the entity already constructed. 
	 **/
	protected abstract void _trafficStopSearch(SearchList<TrafficStop> l);

	public SearchList<TrafficStop> getTrafficStopSearch() {
		return trafficStopSearch;
	}

	public void setTrafficStopSearch(SearchList<TrafficStop> trafficStopSearch) {
		this.trafficStopSearch = trafficStopSearch;
		this.trafficStopSearchWrap.alreadyInitialized = true;
	}
	public static SearchList<TrafficStop> staticSetTrafficStopSearch(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected TrafficPerson trafficStopSearchInit() {
		if(!trafficStopSearchWrap.alreadyInitialized) {
			_trafficStopSearch(trafficStopSearch);
		}
		trafficStopSearch.initDeepForClass(siteRequest_);
		trafficStopSearchWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	//////////////////
	// trafficStop_ //
	//////////////////

	/**	 The entity trafficStop_
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected TrafficStop trafficStop_;
	@JsonIgnore
	public Wrap<TrafficStop> trafficStop_Wrap = new Wrap<TrafficStop>().p(this).c(TrafficStop.class).var("trafficStop_").o(trafficStop_);

	/**	<br/> The entity trafficStop_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:trafficStop_">Find the entity trafficStop_ in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _trafficStop_(Wrap<TrafficStop> c);

	public TrafficStop getTrafficStop_() {
		return trafficStop_;
	}

	public void setTrafficStop_(TrafficStop trafficStop_) {
		this.trafficStop_ = trafficStop_;
		this.trafficStop_Wrap.alreadyInitialized = true;
	}
	public static TrafficStop staticSetTrafficStop_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected TrafficPerson trafficStop_Init() {
		if(!trafficStop_Wrap.alreadyInitialized) {
			_trafficStop_(trafficStop_Wrap);
			if(trafficStop_ == null)
				setTrafficStop_(trafficStop_Wrap.o);
		}
		trafficStop_Wrap.alreadyInitialized(true);
		return (TrafficPerson)this;
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopAgencyTitle">Find the entity stopAgencyTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopAgencyTitle(Wrap<String> w);

	public String getStopAgencyTitle() {
		return stopAgencyTitle;
	}
	public void setStopAgencyTitle(String o) {
		this.stopAgencyTitle = TrafficPerson.staticSetStopAgencyTitle(siteRequest_, o);
		this.stopAgencyTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetStopAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficPerson stopAgencyTitleInit() {
		if(!stopAgencyTitleWrap.alreadyInitialized) {
			_stopAgencyTitle(stopAgencyTitleWrap);
			if(stopAgencyTitle == null)
				setStopAgencyTitle(stopAgencyTitleWrap.o);
		}
		stopAgencyTitleWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static String staticSolrStopAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrStopAgencyTitle(siteRequest_, TrafficPerson.staticSolrStopAgencyTitle(siteRequest_, TrafficPerson.staticSetStopAgencyTitle(siteRequest_, o)));
	}

	public String solrStopAgencyTitle() {
		return TrafficPerson.staticSolrStopAgencyTitle(siteRequest_, stopAgencyTitle);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopDateTime">Find the entity stopDateTime in Solr</a>
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
		this.stopDateTime = TrafficPerson.staticSetStopDateTime(siteRequest_, o);
		this.stopDateTimeWrap.alreadyInitialized = true;
	}
	public static ZonedDateTime staticSetStopDateTime(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : ZonedDateTime.parse(o, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone()))).truncatedTo(ChronoUnit.MILLIS);
	}
	public void setStopDateTime(Date o) {
		this.stopDateTime = o == null ? null : ZonedDateTime.ofInstant(o.toInstant(), ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).truncatedTo(ChronoUnit.MILLIS);
		this.stopDateTimeWrap.alreadyInitialized = true;
	}
	protected TrafficPerson stopDateTimeInit() {
		if(!stopDateTimeWrap.alreadyInitialized) {
			_stopDateTime(stopDateTimeWrap);
			if(stopDateTime == null)
				setStopDateTime(stopDateTimeWrap.o);
		}
		stopDateTimeWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static Date staticSolrStopDateTime(SiteRequestEnUS siteRequest_, ZonedDateTime o) {
		return o == null ? null : Date.from(o.toInstant());
	}

	public static String staticSolrStrStopDateTime(SiteRequestEnUS siteRequest_, Date o) {
		return "\"" + DateTimeFormatter.ISO_DATE_TIME.format(o.toInstant().atOffset(ZoneOffset.UTC)) + "\"";
	}

	public static String staticSolrFqStopDateTime(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrStopDateTime(siteRequest_, TrafficPerson.staticSolrStopDateTime(siteRequest_, TrafficPerson.staticSetStopDateTime(siteRequest_, o)));
	}

	public Date solrStopDateTime() {
		return TrafficPerson.staticSolrStopDateTime(siteRequest_, stopDateTime);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopPurposeNum">Find the entity stopPurposeNum in Solr</a>
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
		this.stopPurposeNum = TrafficPerson.staticSetStopPurposeNum(siteRequest_, o);
		this.stopPurposeNumWrap.alreadyInitialized = true;
	}
	public static Integer staticSetStopPurposeNum(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected TrafficPerson stopPurposeNumInit() {
		if(!stopPurposeNumWrap.alreadyInitialized) {
			_stopPurposeNum(stopPurposeNumWrap);
			if(stopPurposeNum == null)
				setStopPurposeNum(stopPurposeNumWrap.o);
		}
		stopPurposeNumWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static Integer staticSolrStopPurposeNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrStopPurposeNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopPurposeNum(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrStopPurposeNum(siteRequest_, TrafficPerson.staticSolrStopPurposeNum(siteRequest_, TrafficPerson.staticSetStopPurposeNum(siteRequest_, o)));
	}

	public Integer solrStopPurposeNum() {
		return TrafficPerson.staticSolrStopPurposeNum(siteRequest_, stopPurposeNum);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopPurposeTitle">Find the entity stopPurposeTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopPurposeTitle(Wrap<String> w);

	public String getStopPurposeTitle() {
		return stopPurposeTitle;
	}
	public void setStopPurposeTitle(String o) {
		this.stopPurposeTitle = TrafficPerson.staticSetStopPurposeTitle(siteRequest_, o);
		this.stopPurposeTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetStopPurposeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficPerson stopPurposeTitleInit() {
		if(!stopPurposeTitleWrap.alreadyInitialized) {
			_stopPurposeTitle(stopPurposeTitleWrap);
			if(stopPurposeTitle == null)
				setStopPurposeTitle(stopPurposeTitleWrap.o);
		}
		stopPurposeTitleWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static String staticSolrStopPurposeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopPurposeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopPurposeTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrStopPurposeTitle(siteRequest_, TrafficPerson.staticSolrStopPurposeTitle(siteRequest_, TrafficPerson.staticSetStopPurposeTitle(siteRequest_, o)));
	}

	public String solrStopPurposeTitle() {
		return TrafficPerson.staticSolrStopPurposeTitle(siteRequest_, stopPurposeTitle);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopActionNum">Find the entity stopActionNum in Solr</a>
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
		this.stopActionNum = TrafficPerson.staticSetStopActionNum(siteRequest_, o);
		this.stopActionNumWrap.alreadyInitialized = true;
	}
	public static Integer staticSetStopActionNum(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected TrafficPerson stopActionNumInit() {
		if(!stopActionNumWrap.alreadyInitialized) {
			_stopActionNum(stopActionNumWrap);
			if(stopActionNum == null)
				setStopActionNum(stopActionNumWrap.o);
		}
		stopActionNumWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static Integer staticSolrStopActionNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrStopActionNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopActionNum(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrStopActionNum(siteRequest_, TrafficPerson.staticSolrStopActionNum(siteRequest_, TrafficPerson.staticSetStopActionNum(siteRequest_, o)));
	}

	public Integer solrStopActionNum() {
		return TrafficPerson.staticSolrStopActionNum(siteRequest_, stopActionNum);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopActionTitle">Find the entity stopActionTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopActionTitle(Wrap<String> w);

	public String getStopActionTitle() {
		return stopActionTitle;
	}
	public void setStopActionTitle(String o) {
		this.stopActionTitle = TrafficPerson.staticSetStopActionTitle(siteRequest_, o);
		this.stopActionTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetStopActionTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficPerson stopActionTitleInit() {
		if(!stopActionTitleWrap.alreadyInitialized) {
			_stopActionTitle(stopActionTitleWrap);
			if(stopActionTitle == null)
				setStopActionTitle(stopActionTitleWrap.o);
		}
		stopActionTitleWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static String staticSolrStopActionTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopActionTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopActionTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrStopActionTitle(siteRequest_, TrafficPerson.staticSolrStopActionTitle(siteRequest_, TrafficPerson.staticSetStopActionTitle(siteRequest_, o)));
	}

	public String solrStopActionTitle() {
		return TrafficPerson.staticSolrStopActionTitle(siteRequest_, stopActionTitle);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopDriverArrest">Find the entity stopDriverArrest in Solr</a>
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
		this.stopDriverArrest = TrafficPerson.staticSetStopDriverArrest(siteRequest_, o);
		this.stopDriverArrestWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopDriverArrest(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficPerson stopDriverArrestInit() {
		if(!stopDriverArrestWrap.alreadyInitialized) {
			_stopDriverArrest(stopDriverArrestWrap);
			if(stopDriverArrest == null)
				setStopDriverArrest(stopDriverArrestWrap.o);
		}
		stopDriverArrestWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static Boolean staticSolrStopDriverArrest(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopDriverArrest(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopDriverArrest(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrStopDriverArrest(siteRequest_, TrafficPerson.staticSolrStopDriverArrest(siteRequest_, TrafficPerson.staticSetStopDriverArrest(siteRequest_, o)));
	}

	public Boolean solrStopDriverArrest() {
		return TrafficPerson.staticSolrStopDriverArrest(siteRequest_, stopDriverArrest);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopPassengerArrest">Find the entity stopPassengerArrest in Solr</a>
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
		this.stopPassengerArrest = TrafficPerson.staticSetStopPassengerArrest(siteRequest_, o);
		this.stopPassengerArrestWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopPassengerArrest(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficPerson stopPassengerArrestInit() {
		if(!stopPassengerArrestWrap.alreadyInitialized) {
			_stopPassengerArrest(stopPassengerArrestWrap);
			if(stopPassengerArrest == null)
				setStopPassengerArrest(stopPassengerArrestWrap.o);
		}
		stopPassengerArrestWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static Boolean staticSolrStopPassengerArrest(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopPassengerArrest(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopPassengerArrest(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrStopPassengerArrest(siteRequest_, TrafficPerson.staticSolrStopPassengerArrest(siteRequest_, TrafficPerson.staticSetStopPassengerArrest(siteRequest_, o)));
	}

	public Boolean solrStopPassengerArrest() {
		return TrafficPerson.staticSolrStopPassengerArrest(siteRequest_, stopPassengerArrest);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopEncounterForce">Find the entity stopEncounterForce in Solr</a>
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
		this.stopEncounterForce = TrafficPerson.staticSetStopEncounterForce(siteRequest_, o);
		this.stopEncounterForceWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopEncounterForce(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficPerson stopEncounterForceInit() {
		if(!stopEncounterForceWrap.alreadyInitialized) {
			_stopEncounterForce(stopEncounterForceWrap);
			if(stopEncounterForce == null)
				setStopEncounterForce(stopEncounterForceWrap.o);
		}
		stopEncounterForceWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static Boolean staticSolrStopEncounterForce(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopEncounterForce(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopEncounterForce(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrStopEncounterForce(siteRequest_, TrafficPerson.staticSolrStopEncounterForce(siteRequest_, TrafficPerson.staticSetStopEncounterForce(siteRequest_, o)));
	}

	public Boolean solrStopEncounterForce() {
		return TrafficPerson.staticSolrStopEncounterForce(siteRequest_, stopEncounterForce);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopEngageForce">Find the entity stopEngageForce in Solr</a>
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
		this.stopEngageForce = TrafficPerson.staticSetStopEngageForce(siteRequest_, o);
		this.stopEngageForceWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopEngageForce(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficPerson stopEngageForceInit() {
		if(!stopEngageForceWrap.alreadyInitialized) {
			_stopEngageForce(stopEngageForceWrap);
			if(stopEngageForce == null)
				setStopEngageForce(stopEngageForceWrap.o);
		}
		stopEngageForceWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static Boolean staticSolrStopEngageForce(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopEngageForce(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopEngageForce(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrStopEngageForce(siteRequest_, TrafficPerson.staticSolrStopEngageForce(siteRequest_, TrafficPerson.staticSetStopEngageForce(siteRequest_, o)));
	}

	public Boolean solrStopEngageForce() {
		return TrafficPerson.staticSolrStopEngageForce(siteRequest_, stopEngageForce);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopOfficerInjury">Find the entity stopOfficerInjury in Solr</a>
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
		this.stopOfficerInjury = TrafficPerson.staticSetStopOfficerInjury(siteRequest_, o);
		this.stopOfficerInjuryWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopOfficerInjury(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficPerson stopOfficerInjuryInit() {
		if(!stopOfficerInjuryWrap.alreadyInitialized) {
			_stopOfficerInjury(stopOfficerInjuryWrap);
			if(stopOfficerInjury == null)
				setStopOfficerInjury(stopOfficerInjuryWrap.o);
		}
		stopOfficerInjuryWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static Boolean staticSolrStopOfficerInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopOfficerInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopOfficerInjury(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrStopOfficerInjury(siteRequest_, TrafficPerson.staticSolrStopOfficerInjury(siteRequest_, TrafficPerson.staticSetStopOfficerInjury(siteRequest_, o)));
	}

	public Boolean solrStopOfficerInjury() {
		return TrafficPerson.staticSolrStopOfficerInjury(siteRequest_, stopOfficerInjury);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopDriverInjury">Find the entity stopDriverInjury in Solr</a>
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
		this.stopDriverInjury = TrafficPerson.staticSetStopDriverInjury(siteRequest_, o);
		this.stopDriverInjuryWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopDriverInjury(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficPerson stopDriverInjuryInit() {
		if(!stopDriverInjuryWrap.alreadyInitialized) {
			_stopDriverInjury(stopDriverInjuryWrap);
			if(stopDriverInjury == null)
				setStopDriverInjury(stopDriverInjuryWrap.o);
		}
		stopDriverInjuryWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static Boolean staticSolrStopDriverInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopDriverInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopDriverInjury(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrStopDriverInjury(siteRequest_, TrafficPerson.staticSolrStopDriverInjury(siteRequest_, TrafficPerson.staticSetStopDriverInjury(siteRequest_, o)));
	}

	public Boolean solrStopDriverInjury() {
		return TrafficPerson.staticSolrStopDriverInjury(siteRequest_, stopDriverInjury);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopPassengerInjury">Find the entity stopPassengerInjury in Solr</a>
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
		this.stopPassengerInjury = TrafficPerson.staticSetStopPassengerInjury(siteRequest_, o);
		this.stopPassengerInjuryWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopPassengerInjury(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficPerson stopPassengerInjuryInit() {
		if(!stopPassengerInjuryWrap.alreadyInitialized) {
			_stopPassengerInjury(stopPassengerInjuryWrap);
			if(stopPassengerInjury == null)
				setStopPassengerInjury(stopPassengerInjuryWrap.o);
		}
		stopPassengerInjuryWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static Boolean staticSolrStopPassengerInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopPassengerInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopPassengerInjury(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrStopPassengerInjury(siteRequest_, TrafficPerson.staticSolrStopPassengerInjury(siteRequest_, TrafficPerson.staticSetStopPassengerInjury(siteRequest_, o)));
	}

	public Boolean solrStopPassengerInjury() {
		return TrafficPerson.staticSolrStopPassengerInjury(siteRequest_, stopPassengerInjury);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopOfficerId">Find the entity stopOfficerId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopOfficerId(Wrap<String> w);

	public String getStopOfficerId() {
		return stopOfficerId;
	}
	public void setStopOfficerId(String o) {
		this.stopOfficerId = TrafficPerson.staticSetStopOfficerId(siteRequest_, o);
		this.stopOfficerIdWrap.alreadyInitialized = true;
	}
	public static String staticSetStopOfficerId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficPerson stopOfficerIdInit() {
		if(!stopOfficerIdWrap.alreadyInitialized) {
			_stopOfficerId(stopOfficerIdWrap);
			if(stopOfficerId == null)
				setStopOfficerId(stopOfficerIdWrap.o);
		}
		stopOfficerIdWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static String staticSolrStopOfficerId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopOfficerId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopOfficerId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrStopOfficerId(siteRequest_, TrafficPerson.staticSolrStopOfficerId(siteRequest_, TrafficPerson.staticSetStopOfficerId(siteRequest_, o)));
	}

	public String solrStopOfficerId() {
		return TrafficPerson.staticSolrStopOfficerId(siteRequest_, stopOfficerId);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopLocationId">Find the entity stopLocationId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopLocationId(Wrap<String> w);

	public String getStopLocationId() {
		return stopLocationId;
	}
	public void setStopLocationId(String o) {
		this.stopLocationId = TrafficPerson.staticSetStopLocationId(siteRequest_, o);
		this.stopLocationIdWrap.alreadyInitialized = true;
	}
	public static String staticSetStopLocationId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficPerson stopLocationIdInit() {
		if(!stopLocationIdWrap.alreadyInitialized) {
			_stopLocationId(stopLocationIdWrap);
			if(stopLocationId == null)
				setStopLocationId(stopLocationIdWrap.o);
		}
		stopLocationIdWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static String staticSolrStopLocationId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopLocationId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopLocationId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrStopLocationId(siteRequest_, TrafficPerson.staticSolrStopLocationId(siteRequest_, TrafficPerson.staticSetStopLocationId(siteRequest_, o)));
	}

	public String solrStopLocationId() {
		return TrafficPerson.staticSolrStopLocationId(siteRequest_, stopLocationId);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopCityId">Find the entity stopCityId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopCityId(Wrap<String> w);

	public String getStopCityId() {
		return stopCityId;
	}
	public void setStopCityId(String o) {
		this.stopCityId = TrafficPerson.staticSetStopCityId(siteRequest_, o);
		this.stopCityIdWrap.alreadyInitialized = true;
	}
	public static String staticSetStopCityId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficPerson stopCityIdInit() {
		if(!stopCityIdWrap.alreadyInitialized) {
			_stopCityId(stopCityIdWrap);
			if(stopCityId == null)
				setStopCityId(stopCityIdWrap.o);
		}
		stopCityIdWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static String staticSolrStopCityId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopCityId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopCityId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrStopCityId(siteRequest_, TrafficPerson.staticSolrStopCityId(siteRequest_, TrafficPerson.staticSetStopCityId(siteRequest_, o)));
	}

	public String solrStopCityId() {
		return TrafficPerson.staticSolrStopCityId(siteRequest_, stopCityId);
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

	///////////////////////
	// trafficSearchKeys //
	///////////////////////

	/**	 The entity trafficSearchKeys
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> trafficSearchKeys = new ArrayList<Long>();
	@JsonIgnore
	public Wrap<List<Long>> trafficSearchKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("trafficSearchKeys").o(trafficSearchKeys);

	/**	<br/> The entity trafficSearchKeys
	 *  It is constructed before being initialized with the constructor by default List<Long>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:trafficSearchKeys">Find the entity trafficSearchKeys in Solr</a>
	 * <br/>
	 * @param trafficSearchKeys is the entity already constructed. 
	 **/
	protected abstract void _trafficSearchKeys(List<Long> c);

	public List<Long> getTrafficSearchKeys() {
		return trafficSearchKeys;
	}

	public void setTrafficSearchKeys(List<Long> trafficSearchKeys) {
		this.trafficSearchKeys = trafficSearchKeys;
		this.trafficSearchKeysWrap.alreadyInitialized = true;
	}
	public void setTrafficSearchKeys(String o) {
		Long l = TrafficPerson.staticSetTrafficSearchKeys(siteRequest_, o);
		if(l != null)
			addTrafficSearchKeys(l);
		this.trafficSearchKeysWrap.alreadyInitialized = true;
	}
	public static Long staticSetTrafficSearchKeys(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	public TrafficPerson addTrafficSearchKeys(Long...objets) {
		for(Long o : objets) {
			addTrafficSearchKeys(o);
		}
		return (TrafficPerson)this;
	}
	public TrafficPerson addTrafficSearchKeys(Long o) {
		if(o != null && !trafficSearchKeys.contains(o))
			this.trafficSearchKeys.add(o);
		return (TrafficPerson)this;
	}
	public void setTrafficSearchKeys(JsonArray objets) {
		trafficSearchKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addTrafficSearchKeys(o);
		}
	}
	public TrafficPerson addTrafficSearchKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addTrafficSearchKeys(p);
		}
		return (TrafficPerson)this;
	}
	protected TrafficPerson trafficSearchKeysInit() {
		if(!trafficSearchKeysWrap.alreadyInitialized) {
			_trafficSearchKeys(trafficSearchKeys);
		}
		trafficSearchKeysWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static Long staticSolrTrafficSearchKeys(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrTrafficSearchKeys(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqTrafficSearchKeys(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrTrafficSearchKeys(siteRequest_, TrafficPerson.staticSolrTrafficSearchKeys(siteRequest_, TrafficPerson.staticSetTrafficSearchKeys(siteRequest_, o)));
	}

	public List<Long> solrTrafficSearchKeys() {
		List<Long> l = new ArrayList<Long>();
		for(Long o : trafficSearchKeys) {
			l.add(TrafficPerson.staticSolrTrafficSearchKeys(siteRequest_, o));
		}
		return l;
	}

	public String strTrafficSearchKeys() {
		return trafficSearchKeys == null ? "" : trafficSearchKeys.toString();
	}

	public String jsonTrafficSearchKeys() {
		return trafficSearchKeys == null ? "" : trafficSearchKeys.toString();
	}

	public String nomAffichageTrafficSearchKeys() {
		return null;
	}

	public String htmTooltipTrafficSearchKeys() {
		return null;
	}

	public String htmTrafficSearchKeys() {
		return trafficSearchKeys == null ? "" : StringEscapeUtils.escapeHtml4(strTrafficSearchKeys());
	}

	///////////////
	// personAge //
	///////////////

	/**	 The entity personAge
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer personAge;
	@JsonIgnore
	public Wrap<Integer> personAgeWrap = new Wrap<Integer>().p(this).c(Integer.class).var("personAge").o(personAge);

	/**	<br/> The entity personAge
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personAge">Find the entity personAge in Solr</a>
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
	public void setPersonAge(String o) {
		this.personAge = TrafficPerson.staticSetPersonAge(siteRequest_, o);
		this.personAgeWrap.alreadyInitialized = true;
	}
	public static Integer staticSetPersonAge(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected TrafficPerson personAgeInit() {
		if(!personAgeWrap.alreadyInitialized) {
			_personAge(personAgeWrap);
			if(personAge == null)
				setPersonAge(personAgeWrap.o);
		}
		personAgeWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static Integer staticSolrPersonAge(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrPersonAge(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonAge(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrPersonAge(siteRequest_, TrafficPerson.staticSolrPersonAge(siteRequest_, TrafficPerson.staticSetPersonAge(siteRequest_, o)));
	}

	public Integer solrPersonAge() {
		return TrafficPerson.staticSolrPersonAge(siteRequest_, personAge);
	}

	public String strPersonAge() {
		return personAge == null ? "" : personAge.toString();
	}

	public String jsonPersonAge() {
		return personAge == null ? "" : personAge.toString();
	}

	public String nomAffichagePersonAge() {
		return null;
	}

	public String htmTooltipPersonAge() {
		return null;
	}

	public String htmPersonAge() {
		return personAge == null ? "" : StringEscapeUtils.escapeHtml4(strPersonAge());
	}

	//////////////////
	// personTypeId //
	//////////////////

	/**	 The entity personTypeId
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String personTypeId;
	@JsonIgnore
	public Wrap<String> personTypeIdWrap = new Wrap<String>().p(this).c(String.class).var("personTypeId").o(personTypeId);

	/**	<br/> The entity personTypeId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personTypeId">Find the entity personTypeId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personTypeId(Wrap<String> w);

	public String getPersonTypeId() {
		return personTypeId;
	}
	public void setPersonTypeId(String o) {
		this.personTypeId = TrafficPerson.staticSetPersonTypeId(siteRequest_, o);
		this.personTypeIdWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonTypeId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficPerson personTypeIdInit() {
		if(!personTypeIdWrap.alreadyInitialized) {
			_personTypeId(personTypeIdWrap);
			if(personTypeId == null)
				setPersonTypeId(personTypeIdWrap.o);
		}
		personTypeIdWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static String staticSolrPersonTypeId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonTypeId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonTypeId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrPersonTypeId(siteRequest_, TrafficPerson.staticSolrPersonTypeId(siteRequest_, TrafficPerson.staticSetPersonTypeId(siteRequest_, o)));
	}

	public String solrPersonTypeId() {
		return TrafficPerson.staticSolrPersonTypeId(siteRequest_, personTypeId);
	}

	public String strPersonTypeId() {
		return personTypeId == null ? "" : personTypeId;
	}

	public String jsonPersonTypeId() {
		return personTypeId == null ? "" : personTypeId;
	}

	public String nomAffichagePersonTypeId() {
		return null;
	}

	public String htmTooltipPersonTypeId() {
		return null;
	}

	public String htmPersonTypeId() {
		return personTypeId == null ? "" : StringEscapeUtils.escapeHtml4(strPersonTypeId());
	}

	/////////////////////
	// personTypeTitle //
	/////////////////////

	/**	 The entity personTypeTitle
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String personTypeTitle;
	@JsonIgnore
	public Wrap<String> personTypeTitleWrap = new Wrap<String>().p(this).c(String.class).var("personTypeTitle").o(personTypeTitle);

	/**	<br/> The entity personTypeTitle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personTypeTitle">Find the entity personTypeTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personTypeTitle(Wrap<String> w);

	public String getPersonTypeTitle() {
		return personTypeTitle;
	}
	public void setPersonTypeTitle(String o) {
		this.personTypeTitle = TrafficPerson.staticSetPersonTypeTitle(siteRequest_, o);
		this.personTypeTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficPerson personTypeTitleInit() {
		if(!personTypeTitleWrap.alreadyInitialized) {
			_personTypeTitle(personTypeTitleWrap);
			if(personTypeTitle == null)
				setPersonTypeTitle(personTypeTitleWrap.o);
		}
		personTypeTitleWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static String staticSolrPersonTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrPersonTypeTitle(siteRequest_, TrafficPerson.staticSolrPersonTypeTitle(siteRequest_, TrafficPerson.staticSetPersonTypeTitle(siteRequest_, o)));
	}

	public String solrPersonTypeTitle() {
		return TrafficPerson.staticSolrPersonTypeTitle(siteRequest_, personTypeTitle);
	}

	public String strPersonTypeTitle() {
		return personTypeTitle == null ? "" : personTypeTitle;
	}

	public String jsonPersonTypeTitle() {
		return personTypeTitle == null ? "" : personTypeTitle;
	}

	public String nomAffichagePersonTypeTitle() {
		return null;
	}

	public String htmTooltipPersonTypeTitle() {
		return null;
	}

	public String htmPersonTypeTitle() {
		return personTypeTitle == null ? "" : StringEscapeUtils.escapeHtml4(strPersonTypeTitle());
	}

	//////////////////////
	// personTypeDriver //
	//////////////////////

	/**	 The entity personTypeDriver
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean personTypeDriver;
	@JsonIgnore
	public Wrap<Boolean> personTypeDriverWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("personTypeDriver").o(personTypeDriver);

	/**	<br/> The entity personTypeDriver
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personTypeDriver">Find the entity personTypeDriver in Solr</a>
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
	public void setPersonTypeDriver(String o) {
		this.personTypeDriver = TrafficPerson.staticSetPersonTypeDriver(siteRequest_, o);
		this.personTypeDriverWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetPersonTypeDriver(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficPerson personTypeDriverInit() {
		if(!personTypeDriverWrap.alreadyInitialized) {
			_personTypeDriver(personTypeDriverWrap);
			if(personTypeDriver == null)
				setPersonTypeDriver(personTypeDriverWrap.o);
		}
		personTypeDriverWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static Boolean staticSolrPersonTypeDriver(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrPersonTypeDriver(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonTypeDriver(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrPersonTypeDriver(siteRequest_, TrafficPerson.staticSolrPersonTypeDriver(siteRequest_, TrafficPerson.staticSetPersonTypeDriver(siteRequest_, o)));
	}

	public Boolean solrPersonTypeDriver() {
		return TrafficPerson.staticSolrPersonTypeDriver(siteRequest_, personTypeDriver);
	}

	public String strPersonTypeDriver() {
		return personTypeDriver == null ? "" : personTypeDriver.toString();
	}

	public String jsonPersonTypeDriver() {
		return personTypeDriver == null ? "" : personTypeDriver.toString();
	}

	public String nomAffichagePersonTypeDriver() {
		return null;
	}

	public String htmTooltipPersonTypeDriver() {
		return null;
	}

	public String htmPersonTypeDriver() {
		return personTypeDriver == null ? "" : StringEscapeUtils.escapeHtml4(strPersonTypeDriver());
	}

	/////////////////////////
	// personTypePassenger //
	/////////////////////////

	/**	 The entity personTypePassenger
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean personTypePassenger;
	@JsonIgnore
	public Wrap<Boolean> personTypePassengerWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("personTypePassenger").o(personTypePassenger);

	/**	<br/> The entity personTypePassenger
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personTypePassenger">Find the entity personTypePassenger in Solr</a>
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
	public void setPersonTypePassenger(String o) {
		this.personTypePassenger = TrafficPerson.staticSetPersonTypePassenger(siteRequest_, o);
		this.personTypePassengerWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetPersonTypePassenger(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficPerson personTypePassengerInit() {
		if(!personTypePassengerWrap.alreadyInitialized) {
			_personTypePassenger(personTypePassengerWrap);
			if(personTypePassenger == null)
				setPersonTypePassenger(personTypePassengerWrap.o);
		}
		personTypePassengerWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static Boolean staticSolrPersonTypePassenger(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrPersonTypePassenger(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonTypePassenger(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrPersonTypePassenger(siteRequest_, TrafficPerson.staticSolrPersonTypePassenger(siteRequest_, TrafficPerson.staticSetPersonTypePassenger(siteRequest_, o)));
	}

	public Boolean solrPersonTypePassenger() {
		return TrafficPerson.staticSolrPersonTypePassenger(siteRequest_, personTypePassenger);
	}

	public String strPersonTypePassenger() {
		return personTypePassenger == null ? "" : personTypePassenger.toString();
	}

	public String jsonPersonTypePassenger() {
		return personTypePassenger == null ? "" : personTypePassenger.toString();
	}

	public String nomAffichagePersonTypePassenger() {
		return null;
	}

	public String htmTooltipPersonTypePassenger() {
		return null;
	}

	public String htmPersonTypePassenger() {
		return personTypePassenger == null ? "" : StringEscapeUtils.escapeHtml4(strPersonTypePassenger());
	}

	////////////////////
	// personGenderId //
	////////////////////

	/**	 The entity personGenderId
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String personGenderId;
	@JsonIgnore
	public Wrap<String> personGenderIdWrap = new Wrap<String>().p(this).c(String.class).var("personGenderId").o(personGenderId);

	/**	<br/> The entity personGenderId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personGenderId">Find the entity personGenderId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personGenderId(Wrap<String> w);

	public String getPersonGenderId() {
		return personGenderId;
	}
	public void setPersonGenderId(String o) {
		this.personGenderId = TrafficPerson.staticSetPersonGenderId(siteRequest_, o);
		this.personGenderIdWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonGenderId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficPerson personGenderIdInit() {
		if(!personGenderIdWrap.alreadyInitialized) {
			_personGenderId(personGenderIdWrap);
			if(personGenderId == null)
				setPersonGenderId(personGenderIdWrap.o);
		}
		personGenderIdWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static String staticSolrPersonGenderId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonGenderId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonGenderId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrPersonGenderId(siteRequest_, TrafficPerson.staticSolrPersonGenderId(siteRequest_, TrafficPerson.staticSetPersonGenderId(siteRequest_, o)));
	}

	public String solrPersonGenderId() {
		return TrafficPerson.staticSolrPersonGenderId(siteRequest_, personGenderId);
	}

	public String strPersonGenderId() {
		return personGenderId == null ? "" : personGenderId;
	}

	public String jsonPersonGenderId() {
		return personGenderId == null ? "" : personGenderId;
	}

	public String nomAffichagePersonGenderId() {
		return null;
	}

	public String htmTooltipPersonGenderId() {
		return null;
	}

	public String htmPersonGenderId() {
		return personGenderId == null ? "" : StringEscapeUtils.escapeHtml4(strPersonGenderId());
	}

	///////////////////////
	// personGenderTitle //
	///////////////////////

	/**	 The entity personGenderTitle
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String personGenderTitle;
	@JsonIgnore
	public Wrap<String> personGenderTitleWrap = new Wrap<String>().p(this).c(String.class).var("personGenderTitle").o(personGenderTitle);

	/**	<br/> The entity personGenderTitle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personGenderTitle">Find the entity personGenderTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personGenderTitle(Wrap<String> w);

	public String getPersonGenderTitle() {
		return personGenderTitle;
	}
	public void setPersonGenderTitle(String o) {
		this.personGenderTitle = TrafficPerson.staticSetPersonGenderTitle(siteRequest_, o);
		this.personGenderTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonGenderTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficPerson personGenderTitleInit() {
		if(!personGenderTitleWrap.alreadyInitialized) {
			_personGenderTitle(personGenderTitleWrap);
			if(personGenderTitle == null)
				setPersonGenderTitle(personGenderTitleWrap.o);
		}
		personGenderTitleWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static String staticSolrPersonGenderTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonGenderTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonGenderTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrPersonGenderTitle(siteRequest_, TrafficPerson.staticSolrPersonGenderTitle(siteRequest_, TrafficPerson.staticSetPersonGenderTitle(siteRequest_, o)));
	}

	public String solrPersonGenderTitle() {
		return TrafficPerson.staticSolrPersonGenderTitle(siteRequest_, personGenderTitle);
	}

	public String strPersonGenderTitle() {
		return personGenderTitle == null ? "" : personGenderTitle;
	}

	public String jsonPersonGenderTitle() {
		return personGenderTitle == null ? "" : personGenderTitle;
	}

	public String nomAffichagePersonGenderTitle() {
		return null;
	}

	public String htmTooltipPersonGenderTitle() {
		return null;
	}

	public String htmPersonGenderTitle() {
		return personGenderTitle == null ? "" : StringEscapeUtils.escapeHtml4(strPersonGenderTitle());
	}

	////////////////////////
	// personGenderFemale //
	////////////////////////

	/**	 The entity personGenderFemale
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean personGenderFemale;
	@JsonIgnore
	public Wrap<Boolean> personGenderFemaleWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("personGenderFemale").o(personGenderFemale);

	/**	<br/> The entity personGenderFemale
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personGenderFemale">Find the entity personGenderFemale in Solr</a>
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
	public void setPersonGenderFemale(String o) {
		this.personGenderFemale = TrafficPerson.staticSetPersonGenderFemale(siteRequest_, o);
		this.personGenderFemaleWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetPersonGenderFemale(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficPerson personGenderFemaleInit() {
		if(!personGenderFemaleWrap.alreadyInitialized) {
			_personGenderFemale(personGenderFemaleWrap);
			if(personGenderFemale == null)
				setPersonGenderFemale(personGenderFemaleWrap.o);
		}
		personGenderFemaleWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static Boolean staticSolrPersonGenderFemale(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrPersonGenderFemale(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonGenderFemale(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrPersonGenderFemale(siteRequest_, TrafficPerson.staticSolrPersonGenderFemale(siteRequest_, TrafficPerson.staticSetPersonGenderFemale(siteRequest_, o)));
	}

	public Boolean solrPersonGenderFemale() {
		return TrafficPerson.staticSolrPersonGenderFemale(siteRequest_, personGenderFemale);
	}

	public String strPersonGenderFemale() {
		return personGenderFemale == null ? "" : personGenderFemale.toString();
	}

	public String jsonPersonGenderFemale() {
		return personGenderFemale == null ? "" : personGenderFemale.toString();
	}

	public String nomAffichagePersonGenderFemale() {
		return null;
	}

	public String htmTooltipPersonGenderFemale() {
		return null;
	}

	public String htmPersonGenderFemale() {
		return personGenderFemale == null ? "" : StringEscapeUtils.escapeHtml4(strPersonGenderFemale());
	}

	//////////////////////
	// personGenderMale //
	//////////////////////

	/**	 The entity personGenderMale
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean personGenderMale;
	@JsonIgnore
	public Wrap<Boolean> personGenderMaleWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("personGenderMale").o(personGenderMale);

	/**	<br/> The entity personGenderMale
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personGenderMale">Find the entity personGenderMale in Solr</a>
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
	public void setPersonGenderMale(String o) {
		this.personGenderMale = TrafficPerson.staticSetPersonGenderMale(siteRequest_, o);
		this.personGenderMaleWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetPersonGenderMale(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected TrafficPerson personGenderMaleInit() {
		if(!personGenderMaleWrap.alreadyInitialized) {
			_personGenderMale(personGenderMaleWrap);
			if(personGenderMale == null)
				setPersonGenderMale(personGenderMaleWrap.o);
		}
		personGenderMaleWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static Boolean staticSolrPersonGenderMale(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrPersonGenderMale(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonGenderMale(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrPersonGenderMale(siteRequest_, TrafficPerson.staticSolrPersonGenderMale(siteRequest_, TrafficPerson.staticSetPersonGenderMale(siteRequest_, o)));
	}

	public Boolean solrPersonGenderMale() {
		return TrafficPerson.staticSolrPersonGenderMale(siteRequest_, personGenderMale);
	}

	public String strPersonGenderMale() {
		return personGenderMale == null ? "" : personGenderMale.toString();
	}

	public String jsonPersonGenderMale() {
		return personGenderMale == null ? "" : personGenderMale.toString();
	}

	public String nomAffichagePersonGenderMale() {
		return null;
	}

	public String htmTooltipPersonGenderMale() {
		return null;
	}

	public String htmPersonGenderMale() {
		return personGenderMale == null ? "" : StringEscapeUtils.escapeHtml4(strPersonGenderMale());
	}

	///////////////////////
	// personEthnicityId //
	///////////////////////

	/**	 The entity personEthnicityId
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String personEthnicityId;
	@JsonIgnore
	public Wrap<String> personEthnicityIdWrap = new Wrap<String>().p(this).c(String.class).var("personEthnicityId").o(personEthnicityId);

	/**	<br/> The entity personEthnicityId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personEthnicityId">Find the entity personEthnicityId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personEthnicityId(Wrap<String> w);

	public String getPersonEthnicityId() {
		return personEthnicityId;
	}
	public void setPersonEthnicityId(String o) {
		this.personEthnicityId = TrafficPerson.staticSetPersonEthnicityId(siteRequest_, o);
		this.personEthnicityIdWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonEthnicityId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficPerson personEthnicityIdInit() {
		if(!personEthnicityIdWrap.alreadyInitialized) {
			_personEthnicityId(personEthnicityIdWrap);
			if(personEthnicityId == null)
				setPersonEthnicityId(personEthnicityIdWrap.o);
		}
		personEthnicityIdWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static String staticSolrPersonEthnicityId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonEthnicityId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonEthnicityId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrPersonEthnicityId(siteRequest_, TrafficPerson.staticSolrPersonEthnicityId(siteRequest_, TrafficPerson.staticSetPersonEthnicityId(siteRequest_, o)));
	}

	public String solrPersonEthnicityId() {
		return TrafficPerson.staticSolrPersonEthnicityId(siteRequest_, personEthnicityId);
	}

	public String strPersonEthnicityId() {
		return personEthnicityId == null ? "" : personEthnicityId;
	}

	public String jsonPersonEthnicityId() {
		return personEthnicityId == null ? "" : personEthnicityId;
	}

	public String nomAffichagePersonEthnicityId() {
		return null;
	}

	public String htmTooltipPersonEthnicityId() {
		return null;
	}

	public String htmPersonEthnicityId() {
		return personEthnicityId == null ? "" : StringEscapeUtils.escapeHtml4(strPersonEthnicityId());
	}

	//////////////////////////
	// personEthnicityTitle //
	//////////////////////////

	/**	 The entity personEthnicityTitle
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String personEthnicityTitle;
	@JsonIgnore
	public Wrap<String> personEthnicityTitleWrap = new Wrap<String>().p(this).c(String.class).var("personEthnicityTitle").o(personEthnicityTitle);

	/**	<br/> The entity personEthnicityTitle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personEthnicityTitle">Find the entity personEthnicityTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personEthnicityTitle(Wrap<String> w);

	public String getPersonEthnicityTitle() {
		return personEthnicityTitle;
	}
	public void setPersonEthnicityTitle(String o) {
		this.personEthnicityTitle = TrafficPerson.staticSetPersonEthnicityTitle(siteRequest_, o);
		this.personEthnicityTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonEthnicityTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficPerson personEthnicityTitleInit() {
		if(!personEthnicityTitleWrap.alreadyInitialized) {
			_personEthnicityTitle(personEthnicityTitleWrap);
			if(personEthnicityTitle == null)
				setPersonEthnicityTitle(personEthnicityTitleWrap.o);
		}
		personEthnicityTitleWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static String staticSolrPersonEthnicityTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonEthnicityTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonEthnicityTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrPersonEthnicityTitle(siteRequest_, TrafficPerson.staticSolrPersonEthnicityTitle(siteRequest_, TrafficPerson.staticSetPersonEthnicityTitle(siteRequest_, o)));
	}

	public String solrPersonEthnicityTitle() {
		return TrafficPerson.staticSolrPersonEthnicityTitle(siteRequest_, personEthnicityTitle);
	}

	public String strPersonEthnicityTitle() {
		return personEthnicityTitle == null ? "" : personEthnicityTitle;
	}

	public String jsonPersonEthnicityTitle() {
		return personEthnicityTitle == null ? "" : personEthnicityTitle;
	}

	public String nomAffichagePersonEthnicityTitle() {
		return null;
	}

	public String htmTooltipPersonEthnicityTitle() {
		return null;
	}

	public String htmPersonEthnicityTitle() {
		return personEthnicityTitle == null ? "" : StringEscapeUtils.escapeHtml4(strPersonEthnicityTitle());
	}

	//////////////////
	// personRaceId //
	//////////////////

	/**	 The entity personRaceId
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String personRaceId;
	@JsonIgnore
	public Wrap<String> personRaceIdWrap = new Wrap<String>().p(this).c(String.class).var("personRaceId").o(personRaceId);

	/**	<br/> The entity personRaceId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personRaceId">Find the entity personRaceId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personRaceId(Wrap<String> w);

	public String getPersonRaceId() {
		return personRaceId;
	}
	public void setPersonRaceId(String o) {
		this.personRaceId = TrafficPerson.staticSetPersonRaceId(siteRequest_, o);
		this.personRaceIdWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonRaceId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficPerson personRaceIdInit() {
		if(!personRaceIdWrap.alreadyInitialized) {
			_personRaceId(personRaceIdWrap);
			if(personRaceId == null)
				setPersonRaceId(personRaceIdWrap.o);
		}
		personRaceIdWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static String staticSolrPersonRaceId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonRaceId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonRaceId(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrPersonRaceId(siteRequest_, TrafficPerson.staticSolrPersonRaceId(siteRequest_, TrafficPerson.staticSetPersonRaceId(siteRequest_, o)));
	}

	public String solrPersonRaceId() {
		return TrafficPerson.staticSolrPersonRaceId(siteRequest_, personRaceId);
	}

	public String strPersonRaceId() {
		return personRaceId == null ? "" : personRaceId;
	}

	public String jsonPersonRaceId() {
		return personRaceId == null ? "" : personRaceId;
	}

	public String nomAffichagePersonRaceId() {
		return null;
	}

	public String htmTooltipPersonRaceId() {
		return null;
	}

	public String htmPersonRaceId() {
		return personRaceId == null ? "" : StringEscapeUtils.escapeHtml4(strPersonRaceId());
	}

	/////////////////////
	// personRaceTitle //
	/////////////////////

	/**	 The entity personRaceTitle
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String personRaceTitle;
	@JsonIgnore
	public Wrap<String> personRaceTitleWrap = new Wrap<String>().p(this).c(String.class).var("personRaceTitle").o(personRaceTitle);

	/**	<br/> The entity personRaceTitle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficperson.TrafficPerson&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personRaceTitle">Find the entity personRaceTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personRaceTitle(Wrap<String> w);

	public String getPersonRaceTitle() {
		return personRaceTitle;
	}
	public void setPersonRaceTitle(String o) {
		this.personRaceTitle = TrafficPerson.staticSetPersonRaceTitle(siteRequest_, o);
		this.personRaceTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonRaceTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficPerson personRaceTitleInit() {
		if(!personRaceTitleWrap.alreadyInitialized) {
			_personRaceTitle(personRaceTitleWrap);
			if(personRaceTitle == null)
				setPersonRaceTitle(personRaceTitleWrap.o);
		}
		personRaceTitleWrap.alreadyInitialized(true);
		return (TrafficPerson)this;
	}

	public static String staticSolrPersonRaceTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonRaceTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonRaceTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficPerson.staticSolrStrPersonRaceTitle(siteRequest_, TrafficPerson.staticSolrPersonRaceTitle(siteRequest_, TrafficPerson.staticSetPersonRaceTitle(siteRequest_, o)));
	}

	public String solrPersonRaceTitle() {
		return TrafficPerson.staticSolrPersonRaceTitle(siteRequest_, personRaceTitle);
	}

	public String strPersonRaceTitle() {
		return personRaceTitle == null ? "" : personRaceTitle;
	}

	public String jsonPersonRaceTitle() {
		return personRaceTitle == null ? "" : personRaceTitle;
	}

	public String nomAffichagePersonRaceTitle() {
		return null;
	}

	public String htmTooltipPersonRaceTitle() {
		return null;
	}

	public String htmPersonRaceTitle() {
		return personRaceTitle == null ? "" : StringEscapeUtils.escapeHtml4(strPersonRaceTitle());
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedTrafficPerson = false;

	public TrafficPerson initDeepTrafficPerson(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedTrafficPerson) {
			alreadyInitializedTrafficPerson = true;
			initDeepTrafficPerson();
		}
		return (TrafficPerson)this;
	}

	public void initDeepTrafficPerson() {
		initTrafficPerson();
		super.initDeepCluster(siteRequest_);
	}

	public void initTrafficPerson() {
		personKeyInit();
		trafficStopKeyInit();
		trafficStopSearchInit();
		trafficStop_Init();
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
		trafficSearchKeysInit();
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
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepTrafficPerson(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestTrafficPerson(SiteRequestEnUS siteRequest_) {
			super.siteRequestCluster(siteRequest_);
		if(trafficStopSearch != null)
			trafficStopSearch.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestTrafficPerson(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainTrafficPerson(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
		}
		return o;
	}
	public Object obtainTrafficPerson(String var) {
		TrafficPerson oTrafficPerson = (TrafficPerson)this;
		switch(var) {
			case "personKey":
				return oTrafficPerson.personKey;
			case "trafficStopKey":
				return oTrafficPerson.trafficStopKey;
			case "trafficStopSearch":
				return oTrafficPerson.trafficStopSearch;
			case "trafficStop_":
				return oTrafficPerson.trafficStop_;
			case "stopAgencyTitle":
				return oTrafficPerson.stopAgencyTitle;
			case "stopDateTime":
				return oTrafficPerson.stopDateTime;
			case "stopPurposeNum":
				return oTrafficPerson.stopPurposeNum;
			case "stopPurposeTitle":
				return oTrafficPerson.stopPurposeTitle;
			case "stopActionNum":
				return oTrafficPerson.stopActionNum;
			case "stopActionTitle":
				return oTrafficPerson.stopActionTitle;
			case "stopDriverArrest":
				return oTrafficPerson.stopDriverArrest;
			case "stopPassengerArrest":
				return oTrafficPerson.stopPassengerArrest;
			case "stopEncounterForce":
				return oTrafficPerson.stopEncounterForce;
			case "stopEngageForce":
				return oTrafficPerson.stopEngageForce;
			case "stopOfficerInjury":
				return oTrafficPerson.stopOfficerInjury;
			case "stopDriverInjury":
				return oTrafficPerson.stopDriverInjury;
			case "stopPassengerInjury":
				return oTrafficPerson.stopPassengerInjury;
			case "stopOfficerId":
				return oTrafficPerson.stopOfficerId;
			case "stopLocationId":
				return oTrafficPerson.stopLocationId;
			case "stopCityId":
				return oTrafficPerson.stopCityId;
			case "trafficSearchKeys":
				return oTrafficPerson.trafficSearchKeys;
			case "personAge":
				return oTrafficPerson.personAge;
			case "personTypeId":
				return oTrafficPerson.personTypeId;
			case "personTypeTitle":
				return oTrafficPerson.personTypeTitle;
			case "personTypeDriver":
				return oTrafficPerson.personTypeDriver;
			case "personTypePassenger":
				return oTrafficPerson.personTypePassenger;
			case "personGenderId":
				return oTrafficPerson.personGenderId;
			case "personGenderTitle":
				return oTrafficPerson.personGenderTitle;
			case "personGenderFemale":
				return oTrafficPerson.personGenderFemale;
			case "personGenderMale":
				return oTrafficPerson.personGenderMale;
			case "personEthnicityId":
				return oTrafficPerson.personEthnicityId;
			case "personEthnicityTitle":
				return oTrafficPerson.personEthnicityTitle;
			case "personRaceId":
				return oTrafficPerson.personRaceId;
			case "personRaceTitle":
				return oTrafficPerson.personRaceTitle;
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
				o = attributeTrafficPerson(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeTrafficPerson(String var, Object val) {
		TrafficPerson oTrafficPerson = (TrafficPerson)this;
		switch(var) {
			default:
				return super.attributeCluster(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetTrafficPerson(entityVar,  siteRequest_, o);
	}
	public static Object staticSetTrafficPerson(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "personKey":
			return TrafficPerson.staticSetPersonKey(siteRequest_, o);
		case "trafficStopKey":
			return TrafficPerson.staticSetTrafficStopKey(siteRequest_, o);
		case "stopAgencyTitle":
			return TrafficPerson.staticSetStopAgencyTitle(siteRequest_, o);
		case "stopDateTime":
			return TrafficPerson.staticSetStopDateTime(siteRequest_, o);
		case "stopPurposeNum":
			return TrafficPerson.staticSetStopPurposeNum(siteRequest_, o);
		case "stopPurposeTitle":
			return TrafficPerson.staticSetStopPurposeTitle(siteRequest_, o);
		case "stopActionNum":
			return TrafficPerson.staticSetStopActionNum(siteRequest_, o);
		case "stopActionTitle":
			return TrafficPerson.staticSetStopActionTitle(siteRequest_, o);
		case "stopDriverArrest":
			return TrafficPerson.staticSetStopDriverArrest(siteRequest_, o);
		case "stopPassengerArrest":
			return TrafficPerson.staticSetStopPassengerArrest(siteRequest_, o);
		case "stopEncounterForce":
			return TrafficPerson.staticSetStopEncounterForce(siteRequest_, o);
		case "stopEngageForce":
			return TrafficPerson.staticSetStopEngageForce(siteRequest_, o);
		case "stopOfficerInjury":
			return TrafficPerson.staticSetStopOfficerInjury(siteRequest_, o);
		case "stopDriverInjury":
			return TrafficPerson.staticSetStopDriverInjury(siteRequest_, o);
		case "stopPassengerInjury":
			return TrafficPerson.staticSetStopPassengerInjury(siteRequest_, o);
		case "stopOfficerId":
			return TrafficPerson.staticSetStopOfficerId(siteRequest_, o);
		case "stopLocationId":
			return TrafficPerson.staticSetStopLocationId(siteRequest_, o);
		case "stopCityId":
			return TrafficPerson.staticSetStopCityId(siteRequest_, o);
		case "trafficSearchKeys":
			return TrafficPerson.staticSetTrafficSearchKeys(siteRequest_, o);
		case "personAge":
			return TrafficPerson.staticSetPersonAge(siteRequest_, o);
		case "personTypeId":
			return TrafficPerson.staticSetPersonTypeId(siteRequest_, o);
		case "personTypeTitle":
			return TrafficPerson.staticSetPersonTypeTitle(siteRequest_, o);
		case "personTypeDriver":
			return TrafficPerson.staticSetPersonTypeDriver(siteRequest_, o);
		case "personTypePassenger":
			return TrafficPerson.staticSetPersonTypePassenger(siteRequest_, o);
		case "personGenderId":
			return TrafficPerson.staticSetPersonGenderId(siteRequest_, o);
		case "personGenderTitle":
			return TrafficPerson.staticSetPersonGenderTitle(siteRequest_, o);
		case "personGenderFemale":
			return TrafficPerson.staticSetPersonGenderFemale(siteRequest_, o);
		case "personGenderMale":
			return TrafficPerson.staticSetPersonGenderMale(siteRequest_, o);
		case "personEthnicityId":
			return TrafficPerson.staticSetPersonEthnicityId(siteRequest_, o);
		case "personEthnicityTitle":
			return TrafficPerson.staticSetPersonEthnicityTitle(siteRequest_, o);
		case "personRaceId":
			return TrafficPerson.staticSetPersonRaceId(siteRequest_, o);
		case "personRaceTitle":
			return TrafficPerson.staticSetPersonRaceTitle(siteRequest_, o);
			default:
				return Cluster.staticSetCluster(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrTrafficPerson(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrTrafficPerson(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "personKey":
			return TrafficPerson.staticSolrPersonKey(siteRequest_, (Long)o);
		case "trafficStopKey":
			return TrafficPerson.staticSolrTrafficStopKey(siteRequest_, (Long)o);
		case "stopAgencyTitle":
			return TrafficPerson.staticSolrStopAgencyTitle(siteRequest_, (String)o);
		case "stopDateTime":
			return TrafficPerson.staticSolrStopDateTime(siteRequest_, (ZonedDateTime)o);
		case "stopPurposeNum":
			return TrafficPerson.staticSolrStopPurposeNum(siteRequest_, (Integer)o);
		case "stopPurposeTitle":
			return TrafficPerson.staticSolrStopPurposeTitle(siteRequest_, (String)o);
		case "stopActionNum":
			return TrafficPerson.staticSolrStopActionNum(siteRequest_, (Integer)o);
		case "stopActionTitle":
			return TrafficPerson.staticSolrStopActionTitle(siteRequest_, (String)o);
		case "stopDriverArrest":
			return TrafficPerson.staticSolrStopDriverArrest(siteRequest_, (Boolean)o);
		case "stopPassengerArrest":
			return TrafficPerson.staticSolrStopPassengerArrest(siteRequest_, (Boolean)o);
		case "stopEncounterForce":
			return TrafficPerson.staticSolrStopEncounterForce(siteRequest_, (Boolean)o);
		case "stopEngageForce":
			return TrafficPerson.staticSolrStopEngageForce(siteRequest_, (Boolean)o);
		case "stopOfficerInjury":
			return TrafficPerson.staticSolrStopOfficerInjury(siteRequest_, (Boolean)o);
		case "stopDriverInjury":
			return TrafficPerson.staticSolrStopDriverInjury(siteRequest_, (Boolean)o);
		case "stopPassengerInjury":
			return TrafficPerson.staticSolrStopPassengerInjury(siteRequest_, (Boolean)o);
		case "stopOfficerId":
			return TrafficPerson.staticSolrStopOfficerId(siteRequest_, (String)o);
		case "stopLocationId":
			return TrafficPerson.staticSolrStopLocationId(siteRequest_, (String)o);
		case "stopCityId":
			return TrafficPerson.staticSolrStopCityId(siteRequest_, (String)o);
		case "trafficSearchKeys":
			return TrafficPerson.staticSolrTrafficSearchKeys(siteRequest_, (Long)o);
		case "personAge":
			return TrafficPerson.staticSolrPersonAge(siteRequest_, (Integer)o);
		case "personTypeId":
			return TrafficPerson.staticSolrPersonTypeId(siteRequest_, (String)o);
		case "personTypeTitle":
			return TrafficPerson.staticSolrPersonTypeTitle(siteRequest_, (String)o);
		case "personTypeDriver":
			return TrafficPerson.staticSolrPersonTypeDriver(siteRequest_, (Boolean)o);
		case "personTypePassenger":
			return TrafficPerson.staticSolrPersonTypePassenger(siteRequest_, (Boolean)o);
		case "personGenderId":
			return TrafficPerson.staticSolrPersonGenderId(siteRequest_, (String)o);
		case "personGenderTitle":
			return TrafficPerson.staticSolrPersonGenderTitle(siteRequest_, (String)o);
		case "personGenderFemale":
			return TrafficPerson.staticSolrPersonGenderFemale(siteRequest_, (Boolean)o);
		case "personGenderMale":
			return TrafficPerson.staticSolrPersonGenderMale(siteRequest_, (Boolean)o);
		case "personEthnicityId":
			return TrafficPerson.staticSolrPersonEthnicityId(siteRequest_, (String)o);
		case "personEthnicityTitle":
			return TrafficPerson.staticSolrPersonEthnicityTitle(siteRequest_, (String)o);
		case "personRaceId":
			return TrafficPerson.staticSolrPersonRaceId(siteRequest_, (String)o);
		case "personRaceTitle":
			return TrafficPerson.staticSolrPersonRaceTitle(siteRequest_, (String)o);
			default:
				return Cluster.staticSolrCluster(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrTrafficPerson(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrTrafficPerson(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "personKey":
			return TrafficPerson.staticSolrStrPersonKey(siteRequest_, (Long)o);
		case "trafficStopKey":
			return TrafficPerson.staticSolrStrTrafficStopKey(siteRequest_, (Long)o);
		case "stopAgencyTitle":
			return TrafficPerson.staticSolrStrStopAgencyTitle(siteRequest_, (String)o);
		case "stopDateTime":
			return TrafficPerson.staticSolrStrStopDateTime(siteRequest_, (Date)o);
		case "stopPurposeNum":
			return TrafficPerson.staticSolrStrStopPurposeNum(siteRequest_, (Integer)o);
		case "stopPurposeTitle":
			return TrafficPerson.staticSolrStrStopPurposeTitle(siteRequest_, (String)o);
		case "stopActionNum":
			return TrafficPerson.staticSolrStrStopActionNum(siteRequest_, (Integer)o);
		case "stopActionTitle":
			return TrafficPerson.staticSolrStrStopActionTitle(siteRequest_, (String)o);
		case "stopDriverArrest":
			return TrafficPerson.staticSolrStrStopDriverArrest(siteRequest_, (Boolean)o);
		case "stopPassengerArrest":
			return TrafficPerson.staticSolrStrStopPassengerArrest(siteRequest_, (Boolean)o);
		case "stopEncounterForce":
			return TrafficPerson.staticSolrStrStopEncounterForce(siteRequest_, (Boolean)o);
		case "stopEngageForce":
			return TrafficPerson.staticSolrStrStopEngageForce(siteRequest_, (Boolean)o);
		case "stopOfficerInjury":
			return TrafficPerson.staticSolrStrStopOfficerInjury(siteRequest_, (Boolean)o);
		case "stopDriverInjury":
			return TrafficPerson.staticSolrStrStopDriverInjury(siteRequest_, (Boolean)o);
		case "stopPassengerInjury":
			return TrafficPerson.staticSolrStrStopPassengerInjury(siteRequest_, (Boolean)o);
		case "stopOfficerId":
			return TrafficPerson.staticSolrStrStopOfficerId(siteRequest_, (String)o);
		case "stopLocationId":
			return TrafficPerson.staticSolrStrStopLocationId(siteRequest_, (String)o);
		case "stopCityId":
			return TrafficPerson.staticSolrStrStopCityId(siteRequest_, (String)o);
		case "trafficSearchKeys":
			return TrafficPerson.staticSolrStrTrafficSearchKeys(siteRequest_, (Long)o);
		case "personAge":
			return TrafficPerson.staticSolrStrPersonAge(siteRequest_, (Integer)o);
		case "personTypeId":
			return TrafficPerson.staticSolrStrPersonTypeId(siteRequest_, (String)o);
		case "personTypeTitle":
			return TrafficPerson.staticSolrStrPersonTypeTitle(siteRequest_, (String)o);
		case "personTypeDriver":
			return TrafficPerson.staticSolrStrPersonTypeDriver(siteRequest_, (Boolean)o);
		case "personTypePassenger":
			return TrafficPerson.staticSolrStrPersonTypePassenger(siteRequest_, (Boolean)o);
		case "personGenderId":
			return TrafficPerson.staticSolrStrPersonGenderId(siteRequest_, (String)o);
		case "personGenderTitle":
			return TrafficPerson.staticSolrStrPersonGenderTitle(siteRequest_, (String)o);
		case "personGenderFemale":
			return TrafficPerson.staticSolrStrPersonGenderFemale(siteRequest_, (Boolean)o);
		case "personGenderMale":
			return TrafficPerson.staticSolrStrPersonGenderMale(siteRequest_, (Boolean)o);
		case "personEthnicityId":
			return TrafficPerson.staticSolrStrPersonEthnicityId(siteRequest_, (String)o);
		case "personEthnicityTitle":
			return TrafficPerson.staticSolrStrPersonEthnicityTitle(siteRequest_, (String)o);
		case "personRaceId":
			return TrafficPerson.staticSolrStrPersonRaceId(siteRequest_, (String)o);
		case "personRaceTitle":
			return TrafficPerson.staticSolrStrPersonRaceTitle(siteRequest_, (String)o);
			default:
				return Cluster.staticSolrStrCluster(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqTrafficPerson(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqTrafficPerson(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "personKey":
			return TrafficPerson.staticSolrFqPersonKey(siteRequest_, o);
		case "trafficStopKey":
			return TrafficPerson.staticSolrFqTrafficStopKey(siteRequest_, o);
		case "stopAgencyTitle":
			return TrafficPerson.staticSolrFqStopAgencyTitle(siteRequest_, o);
		case "stopDateTime":
			return TrafficPerson.staticSolrFqStopDateTime(siteRequest_, o);
		case "stopPurposeNum":
			return TrafficPerson.staticSolrFqStopPurposeNum(siteRequest_, o);
		case "stopPurposeTitle":
			return TrafficPerson.staticSolrFqStopPurposeTitle(siteRequest_, o);
		case "stopActionNum":
			return TrafficPerson.staticSolrFqStopActionNum(siteRequest_, o);
		case "stopActionTitle":
			return TrafficPerson.staticSolrFqStopActionTitle(siteRequest_, o);
		case "stopDriverArrest":
			return TrafficPerson.staticSolrFqStopDriverArrest(siteRequest_, o);
		case "stopPassengerArrest":
			return TrafficPerson.staticSolrFqStopPassengerArrest(siteRequest_, o);
		case "stopEncounterForce":
			return TrafficPerson.staticSolrFqStopEncounterForce(siteRequest_, o);
		case "stopEngageForce":
			return TrafficPerson.staticSolrFqStopEngageForce(siteRequest_, o);
		case "stopOfficerInjury":
			return TrafficPerson.staticSolrFqStopOfficerInjury(siteRequest_, o);
		case "stopDriverInjury":
			return TrafficPerson.staticSolrFqStopDriverInjury(siteRequest_, o);
		case "stopPassengerInjury":
			return TrafficPerson.staticSolrFqStopPassengerInjury(siteRequest_, o);
		case "stopOfficerId":
			return TrafficPerson.staticSolrFqStopOfficerId(siteRequest_, o);
		case "stopLocationId":
			return TrafficPerson.staticSolrFqStopLocationId(siteRequest_, o);
		case "stopCityId":
			return TrafficPerson.staticSolrFqStopCityId(siteRequest_, o);
		case "trafficSearchKeys":
			return TrafficPerson.staticSolrFqTrafficSearchKeys(siteRequest_, o);
		case "personAge":
			return TrafficPerson.staticSolrFqPersonAge(siteRequest_, o);
		case "personTypeId":
			return TrafficPerson.staticSolrFqPersonTypeId(siteRequest_, o);
		case "personTypeTitle":
			return TrafficPerson.staticSolrFqPersonTypeTitle(siteRequest_, o);
		case "personTypeDriver":
			return TrafficPerson.staticSolrFqPersonTypeDriver(siteRequest_, o);
		case "personTypePassenger":
			return TrafficPerson.staticSolrFqPersonTypePassenger(siteRequest_, o);
		case "personGenderId":
			return TrafficPerson.staticSolrFqPersonGenderId(siteRequest_, o);
		case "personGenderTitle":
			return TrafficPerson.staticSolrFqPersonGenderTitle(siteRequest_, o);
		case "personGenderFemale":
			return TrafficPerson.staticSolrFqPersonGenderFemale(siteRequest_, o);
		case "personGenderMale":
			return TrafficPerson.staticSolrFqPersonGenderMale(siteRequest_, o);
		case "personEthnicityId":
			return TrafficPerson.staticSolrFqPersonEthnicityId(siteRequest_, o);
		case "personEthnicityTitle":
			return TrafficPerson.staticSolrFqPersonEthnicityTitle(siteRequest_, o);
		case "personRaceId":
			return TrafficPerson.staticSolrFqPersonRaceId(siteRequest_, o);
		case "personRaceTitle":
			return TrafficPerson.staticSolrFqPersonRaceTitle(siteRequest_, o);
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
					o = defineTrafficPerson(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineTrafficPerson(String var, String val) {
		switch(var) {
			default:
				return super.defineCluster(var, val);
		}
	}

	/////////////
	// populate //
	/////////////

	@Override public void populateForClass(SolrDocument solrDocument) {
		populateTrafficPerson(solrDocument);
	}
	public void populateTrafficPerson(SolrDocument solrDocument) {
		TrafficPerson oTrafficPerson = (TrafficPerson)this;
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
			solrQuery.addFilterQuery("id:" + ClientUtils.escapeQueryChars("com.opendatapolicing.enus.trafficperson.TrafficPerson"));
			QueryResponse queryResponse = siteRequest.getSiteContext_().getSolrClient().query(solrQuery);
			if(queryResponse.getResults().size() > 0)
				siteRequest.setSolrDocument(queryResponse.getResults().get(0));
			TrafficPerson o = new TrafficPerson();
			o.siteRequestTrafficPerson(siteRequest);
			o.initDeepTrafficPerson(siteRequest);
			o.indexTrafficPerson();
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}


	@Override public void indexForClass() {
		indexTrafficPerson();
	}

	@Override public void indexForClass(SolrInputDocument document) {
		indexTrafficPerson(document);
	}

	public void indexTrafficPerson(SolrClient clientSolr) {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexTrafficPerson(document);
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexTrafficPerson() {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexTrafficPerson(document);
			SolrClient clientSolr = siteRequest_.getSiteContext_().getSolrClient();
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexTrafficPerson(SolrInputDocument document) {
		super.indexCluster(document);

	}

	public static String varIndexedTrafficPerson(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varIndexedCluster(entityVar);
		}
	}

	public static String varSearchTrafficPerson(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSearchCluster(entityVar);
		}
	}

	public static String varSuggestedTrafficPerson(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSuggestedCluster(entityVar);
		}
	}

	/////////////
	// store //
	/////////////

	@Override public void storeForClass(SolrDocument solrDocument) {
		storeTrafficPerson(solrDocument);
	}
	public void storeTrafficPerson(SolrDocument solrDocument) {
		TrafficPerson oTrafficPerson = (TrafficPerson)this;

		super.storeCluster(solrDocument);
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestTrafficPerson() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof TrafficPerson) {
			TrafficPerson original = (TrafficPerson)o;
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
		if(!(o instanceof TrafficPerson))
			return false;
		TrafficPerson that = (TrafficPerson)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("TrafficPerson { ");
		sb.append(" }");
		return sb.toString();
	}
}
