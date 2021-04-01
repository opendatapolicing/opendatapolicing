package com.opendatapolicing.enus.trafficstop;

import java.util.Arrays;
import java.util.Date;
import java.time.ZonedDateTime;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import java.lang.Integer;
import java.lang.Long;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.util.Locale;
import java.util.Map;
import io.vertx.core.json.JsonObject;
import java.time.ZoneOffset;
import com.opendatapolicing.enus.trafficsearch.TrafficSearch;
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
import java.lang.String;
import org.slf4j.Logger;
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

public abstract class TrafficStopGen<DEV> extends Cluster {
	protected static final Logger LOG = LoggerFactory.getLogger(TrafficStop.class);

	public static final List<String> ROLES = Arrays.asList("SiteService");
	public static final List<String> ROLE_READS = Arrays.asList("");

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

	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long trafficStopKey;
	@JsonIgnore
	public Wrap<Long> trafficStopKeyWrap = new Wrap<Long>().p(this).c(Long.class).var("trafficStopKey").o(trafficStopKey);

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

	public Long sqlTrafficStopKey() {
		return trafficStopKey;
	}

	public String jsonTrafficStopKey() {
		return trafficStopKey == null ? "" : trafficStopKey.toString();
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

	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long agencyKey;
	@JsonIgnore
	public Wrap<Long> agencyKeyWrap = new Wrap<Long>().p(this).c(Long.class).var("agencyKey").o(agencyKey);

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

	public Long sqlAgencyKey() {
		return agencyKey;
	}

	public String jsonAgencyKey() {
		return agencyKey == null ? "" : agencyKey.toString();
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

	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected SearchList<SiteAgency> agencySearch = new SearchList<SiteAgency>();
	@JsonIgnore
	public Wrap<SearchList<SiteAgency>> agencySearchWrap = new Wrap<SearchList<SiteAgency>>().p(this).c(SearchList.class).var("agencySearch").o(agencySearch);

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

	@JsonInclude(Include.NON_NULL)
	protected SiteAgency agency_;
	@JsonIgnore
	public Wrap<SiteAgency> agency_Wrap = new Wrap<SiteAgency>().p(this).c(SiteAgency.class).var("agency_").o(agency_);

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

	@JsonInclude(Include.NON_NULL)
	protected String stopAgencyTitle;
	@JsonIgnore
	public Wrap<String> stopAgencyTitleWrap = new Wrap<String>().p(this).c(String.class).var("stopAgencyTitle").o(stopAgencyTitle);

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

	public String sqlStopAgencyTitle() {
		return stopAgencyTitle;
	}

	public String jsonStopAgencyTitle() {
		return stopAgencyTitle == null ? "" : stopAgencyTitle;
	}

	public String htmTooltipStopAgencyTitle() {
		return null;
	}

	public String htmStopAgencyTitle() {
		return stopAgencyTitle == null ? "" : StringEscapeUtils.escapeHtml4(strStopAgencyTitle());
	}

	public void inputStopAgencyTitle(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "agency title")
				.a("id", classApiMethodMethod, "_stopAgencyTitle");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setStopAgencyTitle classTrafficStop inputTrafficStop", pk, "StopAgencyTitle w3-input w3-border ");
					a("name", "setStopAgencyTitle");
				} else {
					a("class", "valueStopAgencyTitle w3-input w3-border classTrafficStop inputTrafficStop", pk, "StopAgencyTitle w3-input w3-border ");
					a("name", "stopAgencyTitle");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStopAgencyTitle', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_stopAgencyTitle')); }, function() { addError($('#", classApiMethodMethod, "_stopAgencyTitle')); }); ");
				}
				a("value", strStopAgencyTitle())
			.fg();

		} else {
			e("span").a("class", "varTrafficStop", pk, "StopAgencyTitle ").f().sx(htmStopAgencyTitle()).g("span");
		}
	}

	public void htmStopAgencyTitle(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "TrafficStopStopAgencyTitle").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_stopAgencyTitle").a("class", "").f().sx("agency title").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputStopAgencyTitle(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pale-green ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_stopAgencyTitle')); $('#", classApiMethodMethod, "_stopAgencyTitle').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#TrafficStopForm :input[name=pk]').val() }], 'setStopAgencyTitle', null, function() { addGlow($('#", classApiMethodMethod, "_stopAgencyTitle')); }, function() { addError($('#", classApiMethodMethod, "_stopAgencyTitle')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	//////////////////
	// stopDateTime //
	//////////////////

	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected ZonedDateTime stopDateTime;
	@JsonIgnore
	public Wrap<ZonedDateTime> stopDateTimeWrap = new Wrap<ZonedDateTime>().p(this).c(ZonedDateTime.class).var("stopDateTime").o(stopDateTime);

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

	public OffsetDateTime sqlStopDateTime() {
		return stopDateTime == null ? null : stopDateTime.toOffsetDateTime();
	}

	public String jsonStopDateTime() {
		return stopDateTime == null ? "" : stopDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
	}

	public String htmTooltipStopDateTime() {
		return null;
	}

	public String htmStopDateTime() {
		return stopDateTime == null ? "" : StringEscapeUtils.escapeHtml4(strStopDateTime());
	}

	public void inputStopDateTime(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
					.a("type", "text")
					.a("class", "w3-input w3-border datepicker setStopDateTime classTrafficStop inputTrafficStop", pk, "StopDateTime w3-input w3-border ")
					.a("placeholder", "MM/DD/YYYY HH:MM AM")
					.a("data-timeformat", "MM/dd/yyyy")
					.a("id", classApiMethodMethod, "_stopDateTime")
				.a("value", stopDateTime == null ? "" : DateTimeFormatter.ofPattern("EEE d MMM yyyy H:mm:ss a zz").format(stopDateTime));
			if("Page".equals(classApiMethodMethod)) {
				a("onclick", "removeGlow($(this)); ");
				a("onchange", "var t = moment(this.value, 'MM/DD/YYYY'); if(t) { var s = t.format('YYYY-MM-DD'); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStopDateTime', s, function() { addGlow($('#", classApiMethodMethod, "_stopDateTime')); }, function() { addError($('#", classApiMethodMethod, "_stopDateTime')); }); } ");
			}
			fg();
		} else {
			e("span").a("class", "varTrafficStop", pk, "StopDateTime ").f().sx(htmStopDateTime()).g("span");
		}
	}

	public void htmStopDateTime(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "TrafficStopStopDateTime").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_stopDateTime").a("class", "").f().sx("stop date/time").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();
								inputStopDateTime(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pale-green ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_stopDateTime')); $('#", classApiMethodMethod, "_stopDateTime').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#TrafficStopForm :input[name=pk]').val() }], 'setStopDateTime', null, function() { addGlow($('#", classApiMethodMethod, "_stopDateTime')); }, function() { addError($('#", classApiMethodMethod, "_stopDateTime')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	//////////////
	// stopYear //
	//////////////

	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer stopYear;
	@JsonIgnore
	public Wrap<Integer> stopYearWrap = new Wrap<Integer>().p(this).c(Integer.class).var("stopYear").o(stopYear);

	protected abstract void _stopYear(Wrap<Integer> w);

	public Integer getStopYear() {
		return stopYear;
	}

	public void setStopYear(Integer stopYear) {
		this.stopYear = stopYear;
		this.stopYearWrap.alreadyInitialized = true;
	}
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

	public String htmTooltipStopYear() {
		return null;
	}

	public String htmStopYear() {
		return stopYear == null ? "" : StringEscapeUtils.escapeHtml4(strStopYear());
	}

	public void inputStopYear(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "stop year")
				.a("id", classApiMethodMethod, "_stopYear");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setStopYear classTrafficStop inputTrafficStop", pk, "StopYear w3-input w3-border ");
					a("name", "setStopYear");
				} else {
					a("class", "valueStopYear w3-input w3-border classTrafficStop inputTrafficStop", pk, "StopYear w3-input w3-border ");
					a("name", "stopYear");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStopYear', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_stopYear')); }, function() { addError($('#", classApiMethodMethod, "_stopYear')); }); ");
				}
				a("value", strStopYear())
			.fg();

		} else {
			e("span").a("class", "varTrafficStop", pk, "StopYear ").f().sx(htmStopYear()).g("span");
		}
	}

	public void htmStopYear(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "TrafficStopStopYear").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_stopYear").a("class", "").f().sx("stop year").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputStopYear(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pale-green ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_stopYear')); $('#", classApiMethodMethod, "_stopYear').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#TrafficStopForm :input[name=pk]').val() }], 'setStopYear', null, function() { addGlow($('#", classApiMethodMethod, "_stopYear')); }, function() { addError($('#", classApiMethodMethod, "_stopYear')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	////////////////////
	// stopPurposeNum //
	////////////////////

	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer stopPurposeNum;
	@JsonIgnore
	public Wrap<Integer> stopPurposeNumWrap = new Wrap<Integer>().p(this).c(Integer.class).var("stopPurposeNum").o(stopPurposeNum);

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

	public Integer sqlStopPurposeNum() {
		return stopPurposeNum;
	}

	public String jsonStopPurposeNum() {
		return stopPurposeNum == null ? "" : stopPurposeNum.toString();
	}

	public String htmTooltipStopPurposeNum() {
		return null;
	}

	public String htmStopPurposeNum() {
		return stopPurposeNum == null ? "" : StringEscapeUtils.escapeHtml4(strStopPurposeNum());
	}

	public void inputStopPurposeNum(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "stop purpose number")
				.a("id", classApiMethodMethod, "_stopPurposeNum");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setStopPurposeNum classTrafficStop inputTrafficStop", pk, "StopPurposeNum w3-input w3-border ");
					a("name", "setStopPurposeNum");
				} else {
					a("class", "valueStopPurposeNum w3-input w3-border classTrafficStop inputTrafficStop", pk, "StopPurposeNum w3-input w3-border ");
					a("name", "stopPurposeNum");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStopPurposeNum', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_stopPurposeNum')); }, function() { addError($('#", classApiMethodMethod, "_stopPurposeNum')); }); ");
				}
				a("value", strStopPurposeNum())
			.fg();

		} else {
			e("span").a("class", "varTrafficStop", pk, "StopPurposeNum ").f().sx(htmStopPurposeNum()).g("span");
		}
	}

	public void htmStopPurposeNum(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "TrafficStopStopPurposeNum").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_stopPurposeNum").a("class", "").f().sx("stop purpose number").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputStopPurposeNum(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pale-green ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_stopPurposeNum')); $('#", classApiMethodMethod, "_stopPurposeNum').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#TrafficStopForm :input[name=pk]').val() }], 'setStopPurposeNum', null, function() { addGlow($('#", classApiMethodMethod, "_stopPurposeNum')); }, function() { addError($('#", classApiMethodMethod, "_stopPurposeNum')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	//////////////////////
	// stopPurposeTitle //
	//////////////////////

	@JsonInclude(Include.NON_NULL)
	protected String stopPurposeTitle;
	@JsonIgnore
	public Wrap<String> stopPurposeTitleWrap = new Wrap<String>().p(this).c(String.class).var("stopPurposeTitle").o(stopPurposeTitle);

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

	public String sqlStopPurposeTitle() {
		return stopPurposeTitle;
	}

	public String jsonStopPurposeTitle() {
		return stopPurposeTitle == null ? "" : stopPurposeTitle;
	}

	public String htmTooltipStopPurposeTitle() {
		return null;
	}

	public String htmStopPurposeTitle() {
		return stopPurposeTitle == null ? "" : StringEscapeUtils.escapeHtml4(strStopPurposeTitle());
	}

	public void inputStopPurposeTitle(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
	}

	public void htmStopPurposeTitle(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			if("Page".equals(classApiMethodMethod)) {
				{ e("div").a("class", "w3-padding ").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("class", "").f().sx("stop purpose title").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								{ e("div").a("class", "w3-rest ").f();
									e("span").a("class", "varTrafficStop", pk, "StopPurposeTitle ").f().sx(strStopPurposeTitle()).g("span");
								} g("div");
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			}
		} g("div");
	}

	///////////////////
	// stopActionNum //
	///////////////////

	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer stopActionNum;
	@JsonIgnore
	public Wrap<Integer> stopActionNumWrap = new Wrap<Integer>().p(this).c(Integer.class).var("stopActionNum").o(stopActionNum);

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

	public Integer sqlStopActionNum() {
		return stopActionNum;
	}

	public String jsonStopActionNum() {
		return stopActionNum == null ? "" : stopActionNum.toString();
	}

	public String htmTooltipStopActionNum() {
		return null;
	}

	public String htmStopActionNum() {
		return stopActionNum == null ? "" : StringEscapeUtils.escapeHtml4(strStopActionNum());
	}

	public void inputStopActionNum(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "stop action number")
				.a("id", classApiMethodMethod, "_stopActionNum");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setStopActionNum classTrafficStop inputTrafficStop", pk, "StopActionNum w3-input w3-border ");
					a("name", "setStopActionNum");
				} else {
					a("class", "valueStopActionNum w3-input w3-border classTrafficStop inputTrafficStop", pk, "StopActionNum w3-input w3-border ");
					a("name", "stopActionNum");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStopActionNum', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_stopActionNum')); }, function() { addError($('#", classApiMethodMethod, "_stopActionNum')); }); ");
				}
				a("value", strStopActionNum())
			.fg();

		} else {
			e("span").a("class", "varTrafficStop", pk, "StopActionNum ").f().sx(htmStopActionNum()).g("span");
		}
	}

	public void htmStopActionNum(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "TrafficStopStopActionNum").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_stopActionNum").a("class", "").f().sx("stop action number").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputStopActionNum(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pale-green ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_stopActionNum')); $('#", classApiMethodMethod, "_stopActionNum').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#TrafficStopForm :input[name=pk]').val() }], 'setStopActionNum', null, function() { addGlow($('#", classApiMethodMethod, "_stopActionNum')); }, function() { addError($('#", classApiMethodMethod, "_stopActionNum')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	/////////////////////
	// stopActionTitle //
	/////////////////////

	@JsonInclude(Include.NON_NULL)
	protected String stopActionTitle;
	@JsonIgnore
	public Wrap<String> stopActionTitleWrap = new Wrap<String>().p(this).c(String.class).var("stopActionTitle").o(stopActionTitle);

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

	public String sqlStopActionTitle() {
		return stopActionTitle;
	}

	public String jsonStopActionTitle() {
		return stopActionTitle == null ? "" : stopActionTitle;
	}

	public String htmTooltipStopActionTitle() {
		return null;
	}

	public String htmStopActionTitle() {
		return stopActionTitle == null ? "" : StringEscapeUtils.escapeHtml4(strStopActionTitle());
	}

	public void inputStopActionTitle(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
	}

	public void htmStopActionTitle(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			if("Page".equals(classApiMethodMethod)) {
				{ e("div").a("class", "w3-padding ").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("class", "").f().sx("agency title").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row  ").f();
							{ e("div").a("class", "w3-cell ").f();
								{ e("div").a("class", "w3-rest ").f();
									e("span").a("class", "varTrafficStop", pk, "StopActionTitle ").f().sx(strStopActionTitle()).g("span");
								} g("div");
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			}
		} g("div");
	}

	//////////////////////
	// stopDriverArrest //
	//////////////////////

	@JsonInclude(Include.NON_NULL)
	protected Boolean stopDriverArrest;
	@JsonIgnore
	public Wrap<Boolean> stopDriverArrestWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("stopDriverArrest").o(stopDriverArrest);

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

	public Boolean sqlStopDriverArrest() {
		return stopDriverArrest;
	}

	public String jsonStopDriverArrest() {
		return stopDriverArrest == null ? "" : stopDriverArrest.toString();
	}

	public String htmTooltipStopDriverArrest() {
		return null;
	}

	public String htmStopDriverArrest() {
		return stopDriverArrest == null ? "" : StringEscapeUtils.escapeHtml4(strStopDriverArrest());
	}

	public void inputStopDriverArrest(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			if("Page".equals(classApiMethodMethod)) {
				e("input")
					.a("type", "checkbox")
					.a("id", classApiMethodMethod, "_stopDriverArrest")
					.a("value", "true");
			} else {
				e("select")
					.a("id", classApiMethodMethod, "_stopDriverArrest");
			}
			if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
				a("class", "setStopDriverArrest classTrafficStop inputTrafficStop", pk, "StopDriverArrest w3-input w3-border ");
				a("name", "setStopDriverArrest");
			} else {
				a("class", "valueStopDriverArrest classTrafficStop inputTrafficStop", pk, "StopDriverArrest w3-input w3-border ");
				a("name", "stopDriverArrest");
			}
			if("Page".equals(classApiMethodMethod)) {
				a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStopDriverArrest', $(this).prop('checked'), function() { addGlow($('#", classApiMethodMethod, "_stopDriverArrest')); }, function() { addError($('#", classApiMethodMethod, "_stopDriverArrest')); }); ");
			}
			if("Page".equals(classApiMethodMethod)) {
				if(getStopDriverArrest() != null && getStopDriverArrest())
					a("checked", "checked");
				fg();
			} else {
				f();
				e("option").a("value", "").a("selected", "selected").f().g("option");
				e("option").a("value", "true").f().sx("true").g("option");
				e("option").a("value", "false").f().sx("false").g("option");
				g("select");
			}

		} else {
			e("span").a("class", "varTrafficStop", pk, "StopDriverArrest ").f().sx(htmStopDriverArrest()).g("span");
		}
	}

	public void htmStopDriverArrest(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "TrafficStopStopDriverArrest").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_stopDriverArrest").a("class", "").f().sx("driver arrest").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputStopDriverArrest(classApiMethodMethod);
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	/////////////////////////
	// stopPassengerArrest //
	/////////////////////////

	@JsonInclude(Include.NON_NULL)
	protected Boolean stopPassengerArrest;
	@JsonIgnore
	public Wrap<Boolean> stopPassengerArrestWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("stopPassengerArrest").o(stopPassengerArrest);

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

	public Boolean sqlStopPassengerArrest() {
		return stopPassengerArrest;
	}

	public String jsonStopPassengerArrest() {
		return stopPassengerArrest == null ? "" : stopPassengerArrest.toString();
	}

	public String htmTooltipStopPassengerArrest() {
		return null;
	}

	public String htmStopPassengerArrest() {
		return stopPassengerArrest == null ? "" : StringEscapeUtils.escapeHtml4(strStopPassengerArrest());
	}

	public void inputStopPassengerArrest(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			if("Page".equals(classApiMethodMethod)) {
				e("input")
					.a("type", "checkbox")
					.a("id", classApiMethodMethod, "_stopPassengerArrest")
					.a("value", "true");
			} else {
				e("select")
					.a("id", classApiMethodMethod, "_stopPassengerArrest");
			}
			if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
				a("class", "setStopPassengerArrest classTrafficStop inputTrafficStop", pk, "StopPassengerArrest w3-input w3-border ");
				a("name", "setStopPassengerArrest");
			} else {
				a("class", "valueStopPassengerArrest classTrafficStop inputTrafficStop", pk, "StopPassengerArrest w3-input w3-border ");
				a("name", "stopPassengerArrest");
			}
			if("Page".equals(classApiMethodMethod)) {
				a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStopPassengerArrest', $(this).prop('checked'), function() { addGlow($('#", classApiMethodMethod, "_stopPassengerArrest')); }, function() { addError($('#", classApiMethodMethod, "_stopPassengerArrest')); }); ");
			}
			if("Page".equals(classApiMethodMethod)) {
				if(getStopPassengerArrest() != null && getStopPassengerArrest())
					a("checked", "checked");
				fg();
			} else {
				f();
				e("option").a("value", "").a("selected", "selected").f().g("option");
				e("option").a("value", "true").f().sx("true").g("option");
				e("option").a("value", "false").f().sx("false").g("option");
				g("select");
			}

		} else {
			e("span").a("class", "varTrafficStop", pk, "StopPassengerArrest ").f().sx(htmStopPassengerArrest()).g("span");
		}
	}

	public void htmStopPassengerArrest(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "TrafficStopStopPassengerArrest").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_stopPassengerArrest").a("class", "").f().sx("passenger arrest").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputStopPassengerArrest(classApiMethodMethod);
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	////////////////////////
	// stopEncounterForce //
	////////////////////////

	@JsonInclude(Include.NON_NULL)
	protected Boolean stopEncounterForce;
	@JsonIgnore
	public Wrap<Boolean> stopEncounterForceWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("stopEncounterForce").o(stopEncounterForce);

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

	public Boolean sqlStopEncounterForce() {
		return stopEncounterForce;
	}

	public String jsonStopEncounterForce() {
		return stopEncounterForce == null ? "" : stopEncounterForce.toString();
	}

	public String htmTooltipStopEncounterForce() {
		return null;
	}

	public String htmStopEncounterForce() {
		return stopEncounterForce == null ? "" : StringEscapeUtils.escapeHtml4(strStopEncounterForce());
	}

	public void inputStopEncounterForce(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			if("Page".equals(classApiMethodMethod)) {
				e("input")
					.a("type", "checkbox")
					.a("id", classApiMethodMethod, "_stopEncounterForce")
					.a("value", "true");
			} else {
				e("select")
					.a("id", classApiMethodMethod, "_stopEncounterForce");
			}
			if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
				a("class", "setStopEncounterForce classTrafficStop inputTrafficStop", pk, "StopEncounterForce w3-input w3-border ");
				a("name", "setStopEncounterForce");
			} else {
				a("class", "valueStopEncounterForce classTrafficStop inputTrafficStop", pk, "StopEncounterForce w3-input w3-border ");
				a("name", "stopEncounterForce");
			}
			if("Page".equals(classApiMethodMethod)) {
				a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStopEncounterForce', $(this).prop('checked'), function() { addGlow($('#", classApiMethodMethod, "_stopEncounterForce')); }, function() { addError($('#", classApiMethodMethod, "_stopEncounterForce')); }); ");
			}
			if("Page".equals(classApiMethodMethod)) {
				if(getStopEncounterForce() != null && getStopEncounterForce())
					a("checked", "checked");
				fg();
			} else {
				f();
				e("option").a("value", "").a("selected", "selected").f().g("option");
				e("option").a("value", "true").f().sx("true").g("option");
				e("option").a("value", "false").f().sx("false").g("option");
				g("select");
			}

		} else {
			e("span").a("class", "varTrafficStop", pk, "StopEncounterForce ").f().sx(htmStopEncounterForce()).g("span");
		}
	}

	public void htmStopEncounterForce(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "TrafficStopStopEncounterForce").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_stopEncounterForce").a("class", "").f().sx("encounter force").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputStopEncounterForce(classApiMethodMethod);
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	/////////////////////
	// stopEngageForce //
	/////////////////////

	@JsonInclude(Include.NON_NULL)
	protected Boolean stopEngageForce;
	@JsonIgnore
	public Wrap<Boolean> stopEngageForceWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("stopEngageForce").o(stopEngageForce);

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

	public Boolean sqlStopEngageForce() {
		return stopEngageForce;
	}

	public String jsonStopEngageForce() {
		return stopEngageForce == null ? "" : stopEngageForce.toString();
	}

	public String htmTooltipStopEngageForce() {
		return null;
	}

	public String htmStopEngageForce() {
		return stopEngageForce == null ? "" : StringEscapeUtils.escapeHtml4(strStopEngageForce());
	}

	public void inputStopEngageForce(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			if("Page".equals(classApiMethodMethod)) {
				e("input")
					.a("type", "checkbox")
					.a("id", classApiMethodMethod, "_stopEngageForce")
					.a("value", "true");
			} else {
				e("select")
					.a("id", classApiMethodMethod, "_stopEngageForce");
			}
			if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
				a("class", "setStopEngageForce classTrafficStop inputTrafficStop", pk, "StopEngageForce w3-input w3-border ");
				a("name", "setStopEngageForce");
			} else {
				a("class", "valueStopEngageForce classTrafficStop inputTrafficStop", pk, "StopEngageForce w3-input w3-border ");
				a("name", "stopEngageForce");
			}
			if("Page".equals(classApiMethodMethod)) {
				a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStopEngageForce', $(this).prop('checked'), function() { addGlow($('#", classApiMethodMethod, "_stopEngageForce')); }, function() { addError($('#", classApiMethodMethod, "_stopEngageForce')); }); ");
			}
			if("Page".equals(classApiMethodMethod)) {
				if(getStopEngageForce() != null && getStopEngageForce())
					a("checked", "checked");
				fg();
			} else {
				f();
				e("option").a("value", "").a("selected", "selected").f().g("option");
				e("option").a("value", "true").f().sx("true").g("option");
				e("option").a("value", "false").f().sx("false").g("option");
				g("select");
			}

		} else {
			e("span").a("class", "varTrafficStop", pk, "StopEngageForce ").f().sx(htmStopEngageForce()).g("span");
		}
	}

	public void htmStopEngageForce(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "TrafficStopStopEngageForce").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_stopEngageForce").a("class", "").f().sx("engage force").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputStopEngageForce(classApiMethodMethod);
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	///////////////////////
	// stopOfficerInjury //
	///////////////////////

	@JsonInclude(Include.NON_NULL)
	protected Boolean stopOfficerInjury;
	@JsonIgnore
	public Wrap<Boolean> stopOfficerInjuryWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("stopOfficerInjury").o(stopOfficerInjury);

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

	public Boolean sqlStopOfficerInjury() {
		return stopOfficerInjury;
	}

	public String jsonStopOfficerInjury() {
		return stopOfficerInjury == null ? "" : stopOfficerInjury.toString();
	}

	public String htmTooltipStopOfficerInjury() {
		return null;
	}

	public String htmStopOfficerInjury() {
		return stopOfficerInjury == null ? "" : StringEscapeUtils.escapeHtml4(strStopOfficerInjury());
	}

	public void inputStopOfficerInjury(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			if("Page".equals(classApiMethodMethod)) {
				e("input")
					.a("type", "checkbox")
					.a("id", classApiMethodMethod, "_stopOfficerInjury")
					.a("value", "true");
			} else {
				e("select")
					.a("id", classApiMethodMethod, "_stopOfficerInjury");
			}
			if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
				a("class", "setStopOfficerInjury classTrafficStop inputTrafficStop", pk, "StopOfficerInjury w3-input w3-border ");
				a("name", "setStopOfficerInjury");
			} else {
				a("class", "valueStopOfficerInjury classTrafficStop inputTrafficStop", pk, "StopOfficerInjury w3-input w3-border ");
				a("name", "stopOfficerInjury");
			}
			if("Page".equals(classApiMethodMethod)) {
				a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStopOfficerInjury', $(this).prop('checked'), function() { addGlow($('#", classApiMethodMethod, "_stopOfficerInjury')); }, function() { addError($('#", classApiMethodMethod, "_stopOfficerInjury')); }); ");
			}
			if("Page".equals(classApiMethodMethod)) {
				if(getStopOfficerInjury() != null && getStopOfficerInjury())
					a("checked", "checked");
				fg();
			} else {
				f();
				e("option").a("value", "").a("selected", "selected").f().g("option");
				e("option").a("value", "true").f().sx("true").g("option");
				e("option").a("value", "false").f().sx("false").g("option");
				g("select");
			}

		} else {
			e("span").a("class", "varTrafficStop", pk, "StopOfficerInjury ").f().sx(htmStopOfficerInjury()).g("span");
		}
	}

	public void htmStopOfficerInjury(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "TrafficStopStopOfficerInjury").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_stopOfficerInjury").a("class", "").f().sx("officer injury").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputStopOfficerInjury(classApiMethodMethod);
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	//////////////////////
	// stopDriverInjury //
	//////////////////////

	@JsonInclude(Include.NON_NULL)
	protected Boolean stopDriverInjury;
	@JsonIgnore
	public Wrap<Boolean> stopDriverInjuryWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("stopDriverInjury").o(stopDriverInjury);

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

	public Boolean sqlStopDriverInjury() {
		return stopDriverInjury;
	}

	public String jsonStopDriverInjury() {
		return stopDriverInjury == null ? "" : stopDriverInjury.toString();
	}

	public String htmTooltipStopDriverInjury() {
		return null;
	}

	public String htmStopDriverInjury() {
		return stopDriverInjury == null ? "" : StringEscapeUtils.escapeHtml4(strStopDriverInjury());
	}

	public void inputStopDriverInjury(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			if("Page".equals(classApiMethodMethod)) {
				e("input")
					.a("type", "checkbox")
					.a("id", classApiMethodMethod, "_stopDriverInjury")
					.a("value", "true");
			} else {
				e("select")
					.a("id", classApiMethodMethod, "_stopDriverInjury");
			}
			if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
				a("class", "setStopDriverInjury classTrafficStop inputTrafficStop", pk, "StopDriverInjury w3-input w3-border ");
				a("name", "setStopDriverInjury");
			} else {
				a("class", "valueStopDriverInjury classTrafficStop inputTrafficStop", pk, "StopDriverInjury w3-input w3-border ");
				a("name", "stopDriverInjury");
			}
			if("Page".equals(classApiMethodMethod)) {
				a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStopDriverInjury', $(this).prop('checked'), function() { addGlow($('#", classApiMethodMethod, "_stopDriverInjury')); }, function() { addError($('#", classApiMethodMethod, "_stopDriverInjury')); }); ");
			}
			if("Page".equals(classApiMethodMethod)) {
				if(getStopDriverInjury() != null && getStopDriverInjury())
					a("checked", "checked");
				fg();
			} else {
				f();
				e("option").a("value", "").a("selected", "selected").f().g("option");
				e("option").a("value", "true").f().sx("true").g("option");
				e("option").a("value", "false").f().sx("false").g("option");
				g("select");
			}

		} else {
			e("span").a("class", "varTrafficStop", pk, "StopDriverInjury ").f().sx(htmStopDriverInjury()).g("span");
		}
	}

	public void htmStopDriverInjury(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "TrafficStopStopDriverInjury").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_stopDriverInjury").a("class", "").f().sx("driver injury").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputStopDriverInjury(classApiMethodMethod);
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	/////////////////////////
	// stopPassengerInjury //
	/////////////////////////

	@JsonInclude(Include.NON_NULL)
	protected Boolean stopPassengerInjury;
	@JsonIgnore
	public Wrap<Boolean> stopPassengerInjuryWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("stopPassengerInjury").o(stopPassengerInjury);

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

	public Boolean sqlStopPassengerInjury() {
		return stopPassengerInjury;
	}

	public String jsonStopPassengerInjury() {
		return stopPassengerInjury == null ? "" : stopPassengerInjury.toString();
	}

	public String htmTooltipStopPassengerInjury() {
		return null;
	}

	public String htmStopPassengerInjury() {
		return stopPassengerInjury == null ? "" : StringEscapeUtils.escapeHtml4(strStopPassengerInjury());
	}

	public void inputStopPassengerInjury(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			if("Page".equals(classApiMethodMethod)) {
				e("input")
					.a("type", "checkbox")
					.a("id", classApiMethodMethod, "_stopPassengerInjury")
					.a("value", "true");
			} else {
				e("select")
					.a("id", classApiMethodMethod, "_stopPassengerInjury");
			}
			if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
				a("class", "setStopPassengerInjury classTrafficStop inputTrafficStop", pk, "StopPassengerInjury w3-input w3-border ");
				a("name", "setStopPassengerInjury");
			} else {
				a("class", "valueStopPassengerInjury classTrafficStop inputTrafficStop", pk, "StopPassengerInjury w3-input w3-border ");
				a("name", "stopPassengerInjury");
			}
			if("Page".equals(classApiMethodMethod)) {
				a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStopPassengerInjury', $(this).prop('checked'), function() { addGlow($('#", classApiMethodMethod, "_stopPassengerInjury')); }, function() { addError($('#", classApiMethodMethod, "_stopPassengerInjury')); }); ");
			}
			if("Page".equals(classApiMethodMethod)) {
				if(getStopPassengerInjury() != null && getStopPassengerInjury())
					a("checked", "checked");
				fg();
			} else {
				f();
				e("option").a("value", "").a("selected", "selected").f().g("option");
				e("option").a("value", "true").f().sx("true").g("option");
				e("option").a("value", "false").f().sx("false").g("option");
				g("select");
			}

		} else {
			e("span").a("class", "varTrafficStop", pk, "StopPassengerInjury ").f().sx(htmStopPassengerInjury()).g("span");
		}
	}

	public void htmStopPassengerInjury(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "TrafficStopStopPassengerInjury").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_stopPassengerInjury").a("class", "").f().sx("passenger injury").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputStopPassengerInjury(classApiMethodMethod);
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	///////////////////
	// stopOfficerId //
	///////////////////

	@JsonInclude(Include.NON_NULL)
	protected String stopOfficerId;
	@JsonIgnore
	public Wrap<String> stopOfficerIdWrap = new Wrap<String>().p(this).c(String.class).var("stopOfficerId").o(stopOfficerId);

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

	public String sqlStopOfficerId() {
		return stopOfficerId;
	}

	public String jsonStopOfficerId() {
		return stopOfficerId == null ? "" : stopOfficerId;
	}

	public String htmTooltipStopOfficerId() {
		return null;
	}

	public String htmStopOfficerId() {
		return stopOfficerId == null ? "" : StringEscapeUtils.escapeHtml4(strStopOfficerId());
	}

	public void inputStopOfficerId(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "officer ID")
				.a("id", classApiMethodMethod, "_stopOfficerId");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setStopOfficerId classTrafficStop inputTrafficStop", pk, "StopOfficerId w3-input w3-border ");
					a("name", "setStopOfficerId");
				} else {
					a("class", "valueStopOfficerId w3-input w3-border classTrafficStop inputTrafficStop", pk, "StopOfficerId w3-input w3-border ");
					a("name", "stopOfficerId");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStopOfficerId', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_stopOfficerId')); }, function() { addError($('#", classApiMethodMethod, "_stopOfficerId')); }); ");
				}
				a("value", strStopOfficerId())
			.fg();

		} else {
			e("span").a("class", "varTrafficStop", pk, "StopOfficerId ").f().sx(htmStopOfficerId()).g("span");
		}
	}

	public void htmStopOfficerId(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "TrafficStopStopOfficerId").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_stopOfficerId").a("class", "").f().sx("officer ID").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputStopOfficerId(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pale-green ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_stopOfficerId')); $('#", classApiMethodMethod, "_stopOfficerId').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#TrafficStopForm :input[name=pk]').val() }], 'setStopOfficerId', null, function() { addGlow($('#", classApiMethodMethod, "_stopOfficerId')); }, function() { addError($('#", classApiMethodMethod, "_stopOfficerId')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	////////////////////
	// stopLocationId //
	////////////////////

	@JsonInclude(Include.NON_NULL)
	protected String stopLocationId;
	@JsonIgnore
	public Wrap<String> stopLocationIdWrap = new Wrap<String>().p(this).c(String.class).var("stopLocationId").o(stopLocationId);

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

	public String sqlStopLocationId() {
		return stopLocationId;
	}

	public String jsonStopLocationId() {
		return stopLocationId == null ? "" : stopLocationId;
	}

	public String htmTooltipStopLocationId() {
		return null;
	}

	public String htmStopLocationId() {
		return stopLocationId == null ? "" : StringEscapeUtils.escapeHtml4(strStopLocationId());
	}

	public void inputStopLocationId(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "location ID")
				.a("id", classApiMethodMethod, "_stopLocationId");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setStopLocationId classTrafficStop inputTrafficStop", pk, "StopLocationId w3-input w3-border ");
					a("name", "setStopLocationId");
				} else {
					a("class", "valueStopLocationId w3-input w3-border classTrafficStop inputTrafficStop", pk, "StopLocationId w3-input w3-border ");
					a("name", "stopLocationId");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStopLocationId', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_stopLocationId')); }, function() { addError($('#", classApiMethodMethod, "_stopLocationId')); }); ");
				}
				a("value", strStopLocationId())
			.fg();

		} else {
			e("span").a("class", "varTrafficStop", pk, "StopLocationId ").f().sx(htmStopLocationId()).g("span");
		}
	}

	public void htmStopLocationId(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "TrafficStopStopLocationId").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_stopLocationId").a("class", "").f().sx("location ID").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputStopLocationId(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pale-green ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_stopLocationId')); $('#", classApiMethodMethod, "_stopLocationId').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#TrafficStopForm :input[name=pk]').val() }], 'setStopLocationId', null, function() { addGlow($('#", classApiMethodMethod, "_stopLocationId')); }, function() { addError($('#", classApiMethodMethod, "_stopLocationId')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	////////////////
	// stopCityId //
	////////////////

	@JsonInclude(Include.NON_NULL)
	protected String stopCityId;
	@JsonIgnore
	public Wrap<String> stopCityIdWrap = new Wrap<String>().p(this).c(String.class).var("stopCityId").o(stopCityId);

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

	public String sqlStopCityId() {
		return stopCityId;
	}

	public String jsonStopCityId() {
		return stopCityId == null ? "" : stopCityId;
	}

	public String htmTooltipStopCityId() {
		return null;
	}

	public String htmStopCityId() {
		return stopCityId == null ? "" : StringEscapeUtils.escapeHtml4(strStopCityId());
	}

	public void inputStopCityId(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "city ID")
				.a("id", classApiMethodMethod, "_stopCityId");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setStopCityId classTrafficStop inputTrafficStop", pk, "StopCityId w3-input w3-border ");
					a("name", "setStopCityId");
				} else {
					a("class", "valueStopCityId w3-input w3-border classTrafficStop inputTrafficStop", pk, "StopCityId w3-input w3-border ");
					a("name", "stopCityId");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStopCityId', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_stopCityId')); }, function() { addError($('#", classApiMethodMethod, "_stopCityId')); }); ");
				}
				a("value", strStopCityId())
			.fg();

		} else {
			e("span").a("class", "varTrafficStop", pk, "StopCityId ").f().sx(htmStopCityId()).g("span");
		}
	}

	public void htmStopCityId(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "TrafficStopStopCityId").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_stopCityId").a("class", "").f().sx("city ID").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputStopCityId(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pale-green ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_stopCityId')); $('#", classApiMethodMethod, "_stopCityId').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#TrafficStopForm :input[name=pk]').val() }], 'setStopCityId', null, function() { addGlow($('#", classApiMethodMethod, "_stopCityId')); }, function() { addError($('#", classApiMethodMethod, "_stopCityId')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	////////////////
	// personKeys //
	////////////////

	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> personKeys = new ArrayList<Long>();
	@JsonIgnore
	public Wrap<List<Long>> personKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("personKeys").o(personKeys);

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

	public List<Long> sqlPersonKeys() {
		return personKeys;
	}

	public String jsonPersonKeys() {
		return personKeys == null ? "" : personKeys.toString();
	}

	public String htmTooltipPersonKeys() {
		return null;
	}

	public String htmPersonKeys() {
		return personKeys == null ? "" : StringEscapeUtils.escapeHtml4(strPersonKeys());
	}

	public void inputPersonKeys(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("i").a("class", "far fa-search w3-xxlarge w3-cell w3-cell-middle ").f().g("i");
			if("PUTCopy".equals(classApiMethodMethod)) {
				{ e("div").f();
					e("input")
						.a("type", "checkbox")
						.a("id", classApiMethodMethod, "_personKeys_clear")
						.a("class", "personKeys_clear ")
						.fg();
					e("label").a("for", "classApiMethodMethod, \"_personKeys_clear").f().sx("clear").g("label");
				} g("div");
			}
			e("input")
				.a("type", "text")
				.a("placeholder", "people")
				.a("class", "valueObjectSuggest suggestPersonKeys w3-input w3-border w3-cell w3-cell-middle ")
				.a("name", "setPersonKeys")
				.a("id", classApiMethodMethod, "_personKeys")
				.a("autocomplete", "off");
				a("oninput", "suggestTrafficStopPersonKeys($(this).val() ? [ { 'name': 'q', 'value': 'objectSuggest:' + $(this).val() }, { 'name': 'rows', 'value': '10' }, { 'name': 'fl', 'value': 'pk,pageUrlPk,trafficPersonCompleteName' } ] : [", pk == null ? "" : "{'name':'fq','value':'trafficStopKey:" + pk + "'}", "], $('#listTrafficStopPersonKeys_", classApiMethodMethod, "'), ", pk, "); ");

				fg();

		} else {
		}
	}

	public void htmPersonKeys(String classApiMethodMethod) {
		TrafficStop s = (TrafficStop)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "TrafficStopPersonKeys").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("a").a("href", "/person?fq=trafficStopKey:", pk).a("class", "w3-cell w3-btn w3-center h4 w3-block h4 w3-pale-green w3-hover-pale-green ").f();
								e("i").a("class", "far fa-newspaper ").f().g("i");
								sx("people");
							} g("a");
						} g("div");
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("h5").a("class", "w3-cell ").f();
								sx("relate  to this traffic stop");
							} g("h5");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();
								{ e("div").a("class", "w3-cell-row ").f();

								inputPersonKeys(classApiMethodMethod);
								} g("div");
							} g("div");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
								{ e("ul").a("class", "w3-ul w3-hoverable ").a("id", "listTrafficStopPersonKeys_", classApiMethodMethod).f();
								} g("ul");
								if(
										CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), TrafficPerson.ROLES)
										|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), TrafficPerson.ROLES)
										) {
									if("Page".equals(classApiMethodMethod)) {
										{ e("div").a("class", "w3-cell-row ").f();
											e("button")
												.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-green ")
												.a("id", classApiMethodMethod, "_personKeys_add")
												.a("onclick", "$(this).addClass('w3-disabled'); this.disabled = true; this.innerHTML = 'Sending…'; postTrafficPersonVals({ trafficStopKey: \"", pk, "\" }, function() {}, function() { addError($('#", classApiMethodMethod, "personKeys')); });")
												.f().sx("add a person")
											.g("button");
										} g("div");
									}
								}
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	//////////////////
	// personSearch //
	//////////////////

	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected SearchList<TrafficPerson> personSearch = new SearchList<TrafficPerson>();
	@JsonIgnore
	public Wrap<SearchList<TrafficPerson>> personSearchWrap = new Wrap<SearchList<TrafficPerson>>().p(this).c(SearchList.class).var("personSearch").o(personSearch);

	protected abstract void _personSearch(SearchList<TrafficPerson> l);

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
	protected TrafficStop personSearchInit() {
		if(!personSearchWrap.alreadyInitialized) {
			_personSearch(personSearch);
		}
		personSearch.initDeepForClass(siteRequest_);
		personSearchWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	//////////////////////
	// personRaceTitles //
	//////////////////////

	@JsonInclude(Include.NON_NULL)
	protected List<String> personRaceTitles = new ArrayList<String>();
	@JsonIgnore
	public Wrap<List<String>> personRaceTitlesWrap = new Wrap<List<String>>().p(this).c(List.class).var("personRaceTitles").o(personRaceTitles);

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

	public String htmTooltipPersonRaceTitles() {
		return null;
	}

	public String htmPersonRaceTitles() {
		return personRaceTitles == null ? "" : StringEscapeUtils.escapeHtml4(strPersonRaceTitles());
	}

	/////////////////////////
	// trafficSearchSearch //
	/////////////////////////

	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected SearchList<TrafficSearch> trafficSearchSearch = new SearchList<TrafficSearch>();
	@JsonIgnore
	public Wrap<SearchList<TrafficSearch>> trafficSearchSearchWrap = new Wrap<SearchList<TrafficSearch>>().p(this).c(SearchList.class).var("trafficSearchSearch").o(trafficSearchSearch);

	protected abstract void _trafficSearchSearch(SearchList<TrafficSearch> l);

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
	protected TrafficStop trafficSearchSearchInit() {
		if(!trafficSearchSearchWrap.alreadyInitialized) {
			_trafficSearchSearch(trafficSearchSearch);
		}
		trafficSearchSearch.initDeepForClass(siteRequest_);
		trafficSearchSearchWrap.alreadyInitialized(true);
		return (TrafficStop)this;
	}

	/////////////////////////////
	// trafficSearchRaceTitles //
	/////////////////////////////

	@JsonInclude(Include.NON_NULL)
	protected List<String> trafficSearchRaceTitles = new ArrayList<String>();
	@JsonIgnore
	public Wrap<List<String>> trafficSearchRaceTitlesWrap = new Wrap<List<String>>().p(this).c(List.class).var("trafficSearchRaceTitles").o(trafficSearchRaceTitles);

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

	public String htmTooltipTrafficSearchRaceTitles() {
		return null;
	}

	public String htmTrafficSearchRaceTitles() {
		return trafficSearchRaceTitles == null ? "" : StringEscapeUtils.escapeHtml4(strTrafficSearchRaceTitles());
	}

	/////////////////////////////
	// trafficStopCompleteName //
	/////////////////////////////

	@JsonInclude(Include.NON_NULL)
	protected String trafficStopCompleteName;
	@JsonIgnore
	public Wrap<String> trafficStopCompleteNameWrap = new Wrap<String>().p(this).c(String.class).var("trafficStopCompleteName").o(trafficStopCompleteName);

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

	public String sqlTrafficStopCompleteName() {
		return trafficStopCompleteName;
	}

	public String jsonTrafficStopCompleteName() {
		return trafficStopCompleteName == null ? "" : trafficStopCompleteName;
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
		personKeysInit();
		personSearchInit();
		personRaceTitlesInit();
		trafficSearchSearchInit();
		trafficSearchRaceTitlesInit();
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
			case "personKeys":
				return oTrafficStop.personKeys;
			case "personSearch":
				return oTrafficStop.personSearch;
			case "personRaceTitles":
				return oTrafficStop.personRaceTitles;
			case "trafficSearchSearch":
				return oTrafficStop.trafficSearchSearch;
			case "trafficSearchRaceTitles":
				return oTrafficStop.trafficSearchRaceTitles;
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
			case "personKeys":
				oTrafficStop.addPersonKeys((Long)val);
				if(!saves.contains("personKeys"))
					saves.add("personKeys");
				return val;
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
		case "personKeys":
			return TrafficStop.staticSetPersonKeys(siteRequest_, o);
		case "personRaceTitles":
			return TrafficStop.staticSetPersonRaceTitles(siteRequest_, o);
		case "trafficSearchRaceTitles":
			return TrafficStop.staticSetTrafficSearchRaceTitles(siteRequest_, o);
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
		case "personKeys":
			return TrafficStop.staticSolrPersonKeys(siteRequest_, (Long)o);
		case "personRaceTitles":
			return TrafficStop.staticSolrPersonRaceTitles(siteRequest_, (String)o);
		case "trafficSearchRaceTitles":
			return TrafficStop.staticSolrTrafficSearchRaceTitles(siteRequest_, (String)o);
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
		case "personKeys":
			return TrafficStop.staticSolrStrPersonKeys(siteRequest_, (Long)o);
		case "personRaceTitles":
			return TrafficStop.staticSolrStrPersonRaceTitles(siteRequest_, (String)o);
		case "trafficSearchRaceTitles":
			return TrafficStop.staticSolrStrTrafficSearchRaceTitles(siteRequest_, (String)o);
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
		case "personKeys":
			return TrafficStop.staticSolrFqPersonKeys(siteRequest_, o);
		case "personRaceTitles":
			return TrafficStop.staticSolrFqPersonRaceTitles(siteRequest_, o);
		case "trafficSearchRaceTitles":
			return TrafficStop.staticSolrFqTrafficSearchRaceTitles(siteRequest_, o);
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
		switch(var.toLowerCase()) {
			case "stopagencytitle":
				if(val != null)
					setStopAgencyTitle(val);
				saves.add("stopAgencyTitle");
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
			case "stopagencytitle":
				if(val instanceof String)
					setStopAgencyTitle((String)val);
				saves.add("stopAgencyTitle");
				return val;
			case "stopdatetime":
				if(val instanceof ZonedDateTime)
					setStopDateTime((ZonedDateTime)val);
				else if(val instanceof OffsetDateTime)
					setStopDateTime(((OffsetDateTime)val).atZoneSameInstant(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())));
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

			if(saves.contains("trafficStopKey")) {
				Long trafficStopKey = (Long)solrDocument.get("trafficStopKey_stored_long");
				if(trafficStopKey != null)
					oTrafficStop.setTrafficStopKey(trafficStopKey);
			}

			if(saves.contains("agencyKey")) {
				Long agencyKey = (Long)solrDocument.get("agencyKey_stored_long");
				if(agencyKey != null)
					oTrafficStop.setAgencyKey(agencyKey);
			}

			if(saves.contains("stopAgencyTitle")) {
				String stopAgencyTitle = (String)solrDocument.get("stopAgencyTitle_stored_string");
				if(stopAgencyTitle != null)
					oTrafficStop.setStopAgencyTitle(stopAgencyTitle);
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

			List<Long> personKeys = (List<Long>)solrDocument.get("personKeys_stored_longs");
			if(personKeys != null)
				oTrafficStop.personKeys.addAll(personKeys);

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

			if(saves.contains("trafficStopCompleteName")) {
				String trafficStopCompleteName = (String)solrDocument.get("trafficStopCompleteName_stored_string");
				if(trafficStopCompleteName != null)
					oTrafficStop.setTrafficStopCompleteName(trafficStopCompleteName);
			}
		}

		super.populateCluster(solrDocument);
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
		if(trafficStopKey != null) {
			document.addField("trafficStopKey_indexed_long", trafficStopKey);
			document.addField("trafficStopKey_stored_long", trafficStopKey);
		}
		if(agencyKey != null) {
			document.addField("agencyKey_indexed_long", agencyKey);
			document.addField("agencyKey_stored_long", agencyKey);
		}
		if(stopAgencyTitle != null) {
			document.addField("stopAgencyTitle_indexed_string", stopAgencyTitle);
			document.addField("stopAgencyTitle_stored_string", stopAgencyTitle);
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
		if(personKeys != null) {
			for(java.lang.Long o : personKeys) {
				document.addField("personKeys_indexed_longs", o);
			}
			for(java.lang.Long o : personKeys) {
				document.addField("personKeys_stored_longs", o);
			}
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
		if(trafficStopCompleteName != null) {
			document.addField("trafficStopCompleteName_indexed_string", trafficStopCompleteName);
			document.addField("trafficStopCompleteName_stored_string", trafficStopCompleteName);
		}
		super.indexCluster(document);

	}

	public static String varIndexedTrafficStop(String entityVar) {
		switch(entityVar) {
			case "trafficStopKey":
				return "trafficStopKey_indexed_long";
			case "agencyKey":
				return "agencyKey_indexed_long";
			case "stopAgencyTitle":
				return "stopAgencyTitle_indexed_string";
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
			case "personKeys":
				return "personKeys_indexed_longs";
			case "personRaceTitles":
				return "personRaceTitles_indexed_strings";
			case "trafficSearchRaceTitles":
				return "trafficSearchRaceTitles_indexed_strings";
			case "trafficStopCompleteName":
				return "trafficStopCompleteName_indexed_string";
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

		Long trafficStopKey = (Long)solrDocument.get("trafficStopKey_stored_long");
		if(trafficStopKey != null)
			oTrafficStop.setTrafficStopKey(trafficStopKey);

		Long agencyKey = (Long)solrDocument.get("agencyKey_stored_long");
		if(agencyKey != null)
			oTrafficStop.setAgencyKey(agencyKey);

		String stopAgencyTitle = (String)solrDocument.get("stopAgencyTitle_stored_string");
		if(stopAgencyTitle != null)
			oTrafficStop.setStopAgencyTitle(stopAgencyTitle);

		Date stopDateTime = (Date)solrDocument.get("stopDateTime_stored_date");
		if(stopDateTime != null)
			oTrafficStop.setStopDateTime(stopDateTime);

		Integer stopYear = (Integer)solrDocument.get("stopYear_stored_int");
		if(stopYear != null)
			oTrafficStop.setStopYear(stopYear);

		Integer stopPurposeNum = (Integer)solrDocument.get("stopPurposeNum_stored_int");
		if(stopPurposeNum != null)
			oTrafficStop.setStopPurposeNum(stopPurposeNum);

		String stopPurposeTitle = (String)solrDocument.get("stopPurposeTitle_stored_string");
		if(stopPurposeTitle != null)
			oTrafficStop.setStopPurposeTitle(stopPurposeTitle);

		Integer stopActionNum = (Integer)solrDocument.get("stopActionNum_stored_int");
		if(stopActionNum != null)
			oTrafficStop.setStopActionNum(stopActionNum);

		String stopActionTitle = (String)solrDocument.get("stopActionTitle_stored_string");
		if(stopActionTitle != null)
			oTrafficStop.setStopActionTitle(stopActionTitle);

		Boolean stopDriverArrest = (Boolean)solrDocument.get("stopDriverArrest_stored_boolean");
		if(stopDriverArrest != null)
			oTrafficStop.setStopDriverArrest(stopDriverArrest);

		Boolean stopPassengerArrest = (Boolean)solrDocument.get("stopPassengerArrest_stored_boolean");
		if(stopPassengerArrest != null)
			oTrafficStop.setStopPassengerArrest(stopPassengerArrest);

		Boolean stopEncounterForce = (Boolean)solrDocument.get("stopEncounterForce_stored_boolean");
		if(stopEncounterForce != null)
			oTrafficStop.setStopEncounterForce(stopEncounterForce);

		Boolean stopEngageForce = (Boolean)solrDocument.get("stopEngageForce_stored_boolean");
		if(stopEngageForce != null)
			oTrafficStop.setStopEngageForce(stopEngageForce);

		Boolean stopOfficerInjury = (Boolean)solrDocument.get("stopOfficerInjury_stored_boolean");
		if(stopOfficerInjury != null)
			oTrafficStop.setStopOfficerInjury(stopOfficerInjury);

		Boolean stopDriverInjury = (Boolean)solrDocument.get("stopDriverInjury_stored_boolean");
		if(stopDriverInjury != null)
			oTrafficStop.setStopDriverInjury(stopDriverInjury);

		Boolean stopPassengerInjury = (Boolean)solrDocument.get("stopPassengerInjury_stored_boolean");
		if(stopPassengerInjury != null)
			oTrafficStop.setStopPassengerInjury(stopPassengerInjury);

		String stopOfficerId = (String)solrDocument.get("stopOfficerId_stored_string");
		if(stopOfficerId != null)
			oTrafficStop.setStopOfficerId(stopOfficerId);

		String stopLocationId = (String)solrDocument.get("stopLocationId_stored_string");
		if(stopLocationId != null)
			oTrafficStop.setStopLocationId(stopLocationId);

		String stopCityId = (String)solrDocument.get("stopCityId_stored_string");
		if(stopCityId != null)
			oTrafficStop.setStopCityId(stopCityId);

		List<Long> personKeys = (List<Long>)solrDocument.get("personKeys_stored_longs");
		if(personKeys != null)
			oTrafficStop.personKeys.addAll(personKeys);

		List<String> personRaceTitles = (List<String>)solrDocument.get("personRaceTitles_stored_strings");
		if(personRaceTitles != null)
			oTrafficStop.personRaceTitles.addAll(personRaceTitles);

		List<String> trafficSearchRaceTitles = (List<String>)solrDocument.get("trafficSearchRaceTitles_stored_strings");
		if(trafficSearchRaceTitles != null)
			oTrafficStop.trafficSearchRaceTitles.addAll(trafficSearchRaceTitles);

		String trafficStopCompleteName = (String)solrDocument.get("trafficStopCompleteName_stored_string");
		if(trafficStopCompleteName != null)
			oTrafficStop.setTrafficStopCompleteName(trafficStopCompleteName);

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
			if(!Objects.equals(trafficStopKey, original.getTrafficStopKey()))
				apiRequest.addVars("trafficStopKey");
			if(!Objects.equals(agencyKey, original.getAgencyKey()))
				apiRequest.addVars("agencyKey");
			if(!Objects.equals(stopAgencyTitle, original.getStopAgencyTitle()))
				apiRequest.addVars("stopAgencyTitle");
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
			if(!Objects.equals(personKeys, original.getPersonKeys()))
				apiRequest.addVars("personKeys");
			if(!Objects.equals(personRaceTitles, original.getPersonRaceTitles()))
				apiRequest.addVars("personRaceTitles");
			if(!Objects.equals(trafficSearchRaceTitles, original.getTrafficSearchRaceTitles()))
				apiRequest.addVars("trafficSearchRaceTitles");
			if(!Objects.equals(trafficStopCompleteName, original.getTrafficStopCompleteName()))
				apiRequest.addVars("trafficStopCompleteName");
			super.apiRequestCluster();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), trafficStopKey, agencyKey, stopAgencyTitle, stopDateTime, stopYear, stopPurposeNum, stopPurposeTitle, stopActionNum, stopActionTitle, stopDriverArrest, stopPassengerArrest, stopEncounterForce, stopEngageForce, stopOfficerInjury, stopDriverInjury, stopPassengerInjury, stopOfficerId, stopLocationId, stopCityId, personKeys, personRaceTitles, trafficSearchRaceTitles, trafficStopCompleteName);
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
				&& Objects.equals( trafficStopKey, that.trafficStopKey )
				&& Objects.equals( agencyKey, that.agencyKey )
				&& Objects.equals( stopAgencyTitle, that.stopAgencyTitle )
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
				&& Objects.equals( personKeys, that.personKeys )
				&& Objects.equals( personRaceTitles, that.personRaceTitles )
				&& Objects.equals( trafficSearchRaceTitles, that.trafficSearchRaceTitles )
				&& Objects.equals( trafficStopCompleteName, that.trafficStopCompleteName );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("TrafficStop { ");
		sb.append( "trafficStopKey: " ).append(trafficStopKey);
		sb.append( ", agencyKey: " ).append(agencyKey);
		sb.append( ", stopAgencyTitle: \"" ).append(stopAgencyTitle).append( "\"" );
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
		sb.append( ", personKeys: " ).append(personKeys);
		sb.append( ", personRaceTitles: " ).append(personRaceTitles);
		sb.append( ", trafficSearchRaceTitles: " ).append(trafficSearchRaceTitles);
		sb.append( ", trafficStopCompleteName: \"" ).append(trafficStopCompleteName).append( "\"" );
		sb.append(" }");
		return sb.toString();
	}
}
