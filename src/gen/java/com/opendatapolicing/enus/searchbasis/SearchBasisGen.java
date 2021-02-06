package com.opendatapolicing.enus.searchbasis;

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
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class SearchBasisGen<DEV> extends Cluster {
	protected static final Logger LOGGER = LoggerFactory.getLogger(SearchBasis.class);

	public static final List<String> ROLES = Arrays.asList("SiteService");
	public static final List<String> ROLE_READS = Arrays.asList("");

	public static final String SearchBasis_AName = "a search basis";
	public static final String SearchBasis_This = "this ";
	public static final String SearchBasis_ThisName = "this search basis";
	public static final String SearchBasis_A = "a ";
	public static final String SearchBasis_TheName = "the search basis";
	public static final String SearchBasis_NameSingular = "search basis";
	public static final String SearchBasis_NamePlural = "search bases";
	public static final String SearchBasis_NameActual = "current search basis";
	public static final String SearchBasis_AllName = "all the search bases";
	public static final String SearchBasis_SearchAllNameBy = "search search bases by ";
	public static final String SearchBasis_Title = "search bases";
	public static final String SearchBasis_ThePluralName = "the search bases";
	public static final String SearchBasis_NoNameFound = "no search basis found";
	public static final String SearchBasis_NameVar = "searchBasis";
	public static final String SearchBasis_OfName = "of search basis";
	public static final String SearchBasis_ANameAdjective = "a search basis";
	public static final String SearchBasis_NameAdjectiveSingular = "search basis";
	public static final String SearchBasis_NameAdjectivePlural = "search bases";
	public static final String SearchBasis_Color = "pale-green";
	public static final String SearchBasis_IconGroup = "regular";
	public static final String SearchBasis_IconName = "newspaper";

	////////////////////
	// searchBasisKey //
	////////////////////

	/**	 The entity searchBasisKey
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long searchBasisKey;
	@JsonIgnore
	public Wrap<Long> searchBasisKeyWrap = new Wrap<Long>().p(this).c(Long.class).var("searchBasisKey").o(searchBasisKey);

	/**	<br/> The entity searchBasisKey
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchBasisKey">Find the entity searchBasisKey in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _searchBasisKey(Wrap<Long> c);

	public Long getSearchBasisKey() {
		return searchBasisKey;
	}

	public void setSearchBasisKey(Long searchBasisKey) {
		this.searchBasisKey = searchBasisKey;
		this.searchBasisKeyWrap.alreadyInitialized = true;
	}
	public void setSearchBasisKey(String o) {
		this.searchBasisKey = SearchBasis.staticSetSearchBasisKey(siteRequest_, o);
		this.searchBasisKeyWrap.alreadyInitialized = true;
	}
	public static Long staticSetSearchBasisKey(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected SearchBasis searchBasisKeyInit() {
		if(!searchBasisKeyWrap.alreadyInitialized) {
			_searchBasisKey(searchBasisKeyWrap);
			if(searchBasisKey == null)
				setSearchBasisKey(searchBasisKeyWrap.o);
		}
		searchBasisKeyWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Long staticSolrSearchBasisKey(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrSearchBasisKey(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchBasisKey(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrSearchBasisKey(siteRequest_, SearchBasis.staticSolrSearchBasisKey(siteRequest_, SearchBasis.staticSetSearchBasisKey(siteRequest_, o)));
	}

	public Long solrSearchBasisKey() {
		return SearchBasis.staticSolrSearchBasisKey(siteRequest_, searchBasisKey);
	}

	public String strSearchBasisKey() {
		return searchBasisKey == null ? "" : searchBasisKey.toString();
	}

	public String jsonSearchBasisKey() {
		return searchBasisKey == null ? "" : searchBasisKey.toString();
	}

	public String nomAffichageSearchBasisKey() {
		return null;
	}

	public String htmTooltipSearchBasisKey() {
		return null;
	}

	public String htmSearchBasisKey() {
		return searchBasisKey == null ? "" : StringEscapeUtils.escapeHtml4(strSearchBasisKey());
	}

	///////////////
	// searchKey //
	///////////////

	/**	 The entity searchKey
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long searchKey;
	@JsonIgnore
	public Wrap<Long> searchKeyWrap = new Wrap<Long>().p(this).c(Long.class).var("searchKey").o(searchKey);

	/**	<br/> The entity searchKey
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchKey">Find the entity searchKey in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _searchKey(Wrap<Long> w);

	public Long getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(Long searchKey) {
		this.searchKey = searchKey;
		this.searchKeyWrap.alreadyInitialized = true;
	}
	public void setSearchKey(String o) {
		this.searchKey = SearchBasis.staticSetSearchKey(siteRequest_, o);
		this.searchKeyWrap.alreadyInitialized = true;
	}
	public static Long staticSetSearchKey(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected SearchBasis searchKeyInit() {
		if(!searchKeyWrap.alreadyInitialized) {
			_searchKey(searchKeyWrap);
			if(searchKey == null)
				setSearchKey(searchKeyWrap.o);
		}
		searchKeyWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Long staticSolrSearchKey(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrSearchKey(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchKey(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrSearchKey(siteRequest_, SearchBasis.staticSolrSearchKey(siteRequest_, SearchBasis.staticSetSearchKey(siteRequest_, o)));
	}

	public Long solrSearchKey() {
		return SearchBasis.staticSolrSearchKey(siteRequest_, searchKey);
	}

	public String strSearchKey() {
		return searchKey == null ? "" : searchKey.toString();
	}

	public String jsonSearchKey() {
		return searchKey == null ? "" : searchKey.toString();
	}

	public String nomAffichageSearchKey() {
		return "search key";
	}

	public String htmTooltipSearchKey() {
		return null;
	}

	public String htmSearchKey() {
		return searchKey == null ? "" : StringEscapeUtils.escapeHtml4(strSearchKey());
	}

	public void inputSearchKey(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("i").a("class", "far fa-search w3-xxlarge w3-cell w3-cell-middle ").f().g("i");
			if("PUTCopy".equals(classApiMethodMethod)) {
				{ e("div").f();
					e("input")
						.a("type", "checkbox")
						.a("id", classApiMethodMethod, "_searchKey_clear")
						.a("class", "searchKey_clear ")
						.fg();
					e("label").a("for", "classApiMethodMethod, \"_searchKey_clear").f().sx("clear").g("label");
				} g("div");
			}
			e("input")
				.a("type", "text")
				.a("placeholder", "search key")
				.a("class", "value suggestSearchKey w3-input w3-border w3-cell w3-cell-middle ")
				.a("name", "setSearchKey")
				.a("id", classApiMethodMethod, "_searchKey")
				.a("autocomplete", "off");
				a("oninput", "suggestSearchBasisSearchKey($(this).val() ? searchTrafficSearchFilters($(this.parentElement)) : [", pk == null ? "" : "{'name':'fq','value':'searchBasisKeys:" + pk + "'}", "], $('#listSearchBasisSearchKey_", classApiMethodMethod, "'), ", pk, "); ");

				fg();

		} else {
		}
	}

	public void htmSearchKey(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisSearchKey").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("a").a("href", "/traffic-search?fq=searchBasisKeys:", pk).a("class", "w3-cell w3-btn w3-center h4 w3-block h4 w3-pale-green w3-hover-pale-green ").f();
								e("i").a("class", "far fa-newspaper ").f().g("i");
								sx("search key");
							} g("a");
						} g("div");
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("h5").a("class", "w3-cell ").f();
								sx("relate a traffic search to this search basis");
							} g("h5");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();
								{ e("div").a("class", "w3-cell-row ").f();

								inputSearchKey(classApiMethodMethod);
								} g("div");
							} g("div");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
								{ e("ul").a("class", "w3-ul w3-hoverable ").a("id", "listSearchBasisSearchKey_", classApiMethodMethod).f();
								} g("ul");
								if(
										CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), TrafficSearch.ROLES)
										|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), TrafficSearch.ROLES)
										) {
									if("Page".equals(classApiMethodMethod)) {
										{ e("div").a("class", "w3-cell-row ").f();
											e("button")
												.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-green ")
												.a("id", classApiMethodMethod, "_searchKey_add")
												.a("onclick", "$(this).addClass('w3-disabled'); this.disabled = true; this.innerHTML = 'Sending…'; postTrafficSearchVals({ searchBasisKeys: [ \"", pk, "\" ] }, function() {}, function() { addError($('#", classApiMethodMethod, "searchKey')); });")
												.f().sx("add a traffic search")
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

	/////////////////////////
	// trafficSearchSearch //
	/////////////////////////

	/**	 The entity trafficSearchSearch
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<TrafficSearch>(). 
	 */
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected SearchList<TrafficSearch> trafficSearchSearch = new SearchList<TrafficSearch>();
	@JsonIgnore
	public Wrap<SearchList<TrafficSearch>> trafficSearchSearchWrap = new Wrap<SearchList<TrafficSearch>>().p(this).c(SearchList.class).var("trafficSearchSearch").o(trafficSearchSearch);

	/**	<br/> The entity trafficSearchSearch
	 *  It is constructed before being initialized with the constructor by default SearchList<TrafficSearch>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:trafficSearchSearch">Find the entity trafficSearchSearch in Solr</a>
	 * <br/>
	 * @param trafficSearchSearch is the entity already constructed. 
	 **/
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
	protected SearchBasis trafficSearchSearchInit() {
		if(!trafficSearchSearchWrap.alreadyInitialized) {
			_trafficSearchSearch(trafficSearchSearch);
		}
		trafficSearchSearch.initDeepForClass(siteRequest_);
		trafficSearchSearchWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	////////////////////
	// trafficSearch_ //
	////////////////////

	/**	 The entity trafficSearch_
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected TrafficSearch trafficSearch_;
	@JsonIgnore
	public Wrap<TrafficSearch> trafficSearch_Wrap = new Wrap<TrafficSearch>().p(this).c(TrafficSearch.class).var("trafficSearch_").o(trafficSearch_);

	/**	<br/> The entity trafficSearch_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:trafficSearch_">Find the entity trafficSearch_ in Solr</a>
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
	protected SearchBasis trafficSearch_Init() {
		if(!trafficSearch_Wrap.alreadyInitialized) {
			_trafficSearch_(trafficSearch_Wrap);
			if(trafficSearch_ == null)
				setTrafficSearch_(trafficSearch_Wrap.o);
		}
		trafficSearch_Wrap.alreadyInitialized(true);
		return (SearchBasis)this;
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopAgencyTitle">Find the entity stopAgencyTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopAgencyTitle(Wrap<String> w);

	public String getStopAgencyTitle() {
		return stopAgencyTitle;
	}
	public void setStopAgencyTitle(String o) {
		this.stopAgencyTitle = SearchBasis.staticSetStopAgencyTitle(siteRequest_, o);
		this.stopAgencyTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetStopAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SearchBasis stopAgencyTitleInit() {
		if(!stopAgencyTitleWrap.alreadyInitialized) {
			_stopAgencyTitle(stopAgencyTitleWrap);
			if(stopAgencyTitle == null)
				setStopAgencyTitle(stopAgencyTitleWrap.o);
		}
		stopAgencyTitleWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static String staticSolrStopAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrStopAgencyTitle(siteRequest_, SearchBasis.staticSolrStopAgencyTitle(siteRequest_, SearchBasis.staticSetStopAgencyTitle(siteRequest_, o)));
	}

	public String solrStopAgencyTitle() {
		return SearchBasis.staticSolrStopAgencyTitle(siteRequest_, stopAgencyTitle);
	}

	public String strStopAgencyTitle() {
		return stopAgencyTitle == null ? "" : stopAgencyTitle;
	}

	public String jsonStopAgencyTitle() {
		return stopAgencyTitle == null ? "" : stopAgencyTitle;
	}

	public String nomAffichageStopAgencyTitle() {
		return "agency title";
	}

	public String htmTooltipStopAgencyTitle() {
		return null;
	}

	public String htmStopAgencyTitle() {
		return stopAgencyTitle == null ? "" : StringEscapeUtils.escapeHtml4(strStopAgencyTitle());
	}

	public void inputStopAgencyTitle(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "agency title")
				.a("id", classApiMethodMethod, "_stopAgencyTitle");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setStopAgencyTitle classSearchBasis inputSearchBasis", pk, "StopAgencyTitle w3-input w3-border ");
					a("name", "setStopAgencyTitle");
				} else {
					a("class", "valueStopAgencyTitle w3-input w3-border classSearchBasis inputSearchBasis", pk, "StopAgencyTitle w3-input w3-border ");
					a("name", "stopAgencyTitle");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStopAgencyTitle', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_stopAgencyTitle')); }, function() { addError($('#", classApiMethodMethod, "_stopAgencyTitle')); }); ");
				}
				a("value", strStopAgencyTitle())
			.fg();

		} else {
			e("span").a("class", "varSearchBasis", pk, "StopAgencyTitle ").f().sx(htmStopAgencyTitle()).g("span");
		}
	}

	public void htmStopAgencyTitle(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisStopAgencyTitle").f();
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_stopAgencyTitle')); $('#", classApiMethodMethod, "_stopAgencyTitle').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SearchBasisForm :input[name=pk]').val() }], 'setStopAgencyTitle', null, function() { addGlow($('#", classApiMethodMethod, "_stopAgencyTitle')); }, function() { addError($('#", classApiMethodMethod, "_stopAgencyTitle')); }); ")
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopDateTime">Find the entity stopDateTime in Solr</a>
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
		this.stopDateTime = SearchBasis.staticSetStopDateTime(siteRequest_, o);
		this.stopDateTimeWrap.alreadyInitialized = true;
	}
	public static ZonedDateTime staticSetStopDateTime(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : ZonedDateTime.parse(o, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone()))).truncatedTo(ChronoUnit.MILLIS);
	}
	public void setStopDateTime(Date o) {
		this.stopDateTime = o == null ? null : ZonedDateTime.ofInstant(o.toInstant(), ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).truncatedTo(ChronoUnit.MILLIS);
		this.stopDateTimeWrap.alreadyInitialized = true;
	}
	protected SearchBasis stopDateTimeInit() {
		if(!stopDateTimeWrap.alreadyInitialized) {
			_stopDateTime(stopDateTimeWrap);
			if(stopDateTime == null)
				setStopDateTime(stopDateTimeWrap.o);
		}
		stopDateTimeWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Date staticSolrStopDateTime(SiteRequestEnUS siteRequest_, ZonedDateTime o) {
		return o == null ? null : Date.from(o.toInstant());
	}

	public static String staticSolrStrStopDateTime(SiteRequestEnUS siteRequest_, Date o) {
		return "\"" + DateTimeFormatter.ISO_DATE_TIME.format(o.toInstant().atOffset(ZoneOffset.UTC)) + "\"";
	}

	public static String staticSolrFqStopDateTime(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrStopDateTime(siteRequest_, SearchBasis.staticSolrStopDateTime(siteRequest_, SearchBasis.staticSetStopDateTime(siteRequest_, o)));
	}

	public Date solrStopDateTime() {
		return SearchBasis.staticSolrStopDateTime(siteRequest_, stopDateTime);
	}

	public String strStopDateTime() {
		return stopDateTime == null ? "" : stopDateTime.format(DateTimeFormatter.ofPattern("EEE d MMM yyyy H:mm:ss a zz", Locale.forLanguageTag("en-US")));
	}

	public String jsonStopDateTime() {
		return stopDateTime == null ? "" : stopDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
	}

	public String nomAffichageStopDateTime() {
		return "stop date/time";
	}

	public String htmTooltipStopDateTime() {
		return null;
	}

	public String htmStopDateTime() {
		return stopDateTime == null ? "" : StringEscapeUtils.escapeHtml4(strStopDateTime());
	}

	public void inputStopDateTime(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
					.a("type", "text")
					.a("class", "w3-input w3-border datepicker setStopDateTime classSearchBasis inputSearchBasis", pk, "StopDateTime w3-input w3-border ")
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
			e("span").a("class", "varSearchBasis", pk, "StopDateTime ").f().sx(htmStopDateTime()).g("span");
		}
	}

	public void htmStopDateTime(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisStopDateTime").f();
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_stopDateTime')); $('#", classApiMethodMethod, "_stopDateTime').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SearchBasisForm :input[name=pk]').val() }], 'setStopDateTime', null, function() { addGlow($('#", classApiMethodMethod, "_stopDateTime')); }, function() { addError($('#", classApiMethodMethod, "_stopDateTime')); }); ")
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopPurposeNum">Find the entity stopPurposeNum in Solr</a>
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
		this.stopPurposeNum = SearchBasis.staticSetStopPurposeNum(siteRequest_, o);
		this.stopPurposeNumWrap.alreadyInitialized = true;
	}
	public static Integer staticSetStopPurposeNum(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected SearchBasis stopPurposeNumInit() {
		if(!stopPurposeNumWrap.alreadyInitialized) {
			_stopPurposeNum(stopPurposeNumWrap);
			if(stopPurposeNum == null)
				setStopPurposeNum(stopPurposeNumWrap.o);
		}
		stopPurposeNumWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Integer staticSolrStopPurposeNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrStopPurposeNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopPurposeNum(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrStopPurposeNum(siteRequest_, SearchBasis.staticSolrStopPurposeNum(siteRequest_, SearchBasis.staticSetStopPurposeNum(siteRequest_, o)));
	}

	public Integer solrStopPurposeNum() {
		return SearchBasis.staticSolrStopPurposeNum(siteRequest_, stopPurposeNum);
	}

	public String strStopPurposeNum() {
		return stopPurposeNum == null ? "" : stopPurposeNum.toString();
	}

	public String jsonStopPurposeNum() {
		return stopPurposeNum == null ? "" : stopPurposeNum.toString();
	}

	public String nomAffichageStopPurposeNum() {
		return "stop purpose number";
	}

	public String htmTooltipStopPurposeNum() {
		return null;
	}

	public String htmStopPurposeNum() {
		return stopPurposeNum == null ? "" : StringEscapeUtils.escapeHtml4(strStopPurposeNum());
	}

	public void inputStopPurposeNum(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "stop purpose number")
				.a("id", classApiMethodMethod, "_stopPurposeNum");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setStopPurposeNum classSearchBasis inputSearchBasis", pk, "StopPurposeNum w3-input w3-border ");
					a("name", "setStopPurposeNum");
				} else {
					a("class", "valueStopPurposeNum w3-input w3-border classSearchBasis inputSearchBasis", pk, "StopPurposeNum w3-input w3-border ");
					a("name", "stopPurposeNum");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStopPurposeNum', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_stopPurposeNum')); }, function() { addError($('#", classApiMethodMethod, "_stopPurposeNum')); }); ");
				}
				a("value", strStopPurposeNum())
			.fg();

		} else {
			e("span").a("class", "varSearchBasis", pk, "StopPurposeNum ").f().sx(htmStopPurposeNum()).g("span");
		}
	}

	public void htmStopPurposeNum(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisStopPurposeNum").f();
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_stopPurposeNum')); $('#", classApiMethodMethod, "_stopPurposeNum').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SearchBasisForm :input[name=pk]').val() }], 'setStopPurposeNum', null, function() { addGlow($('#", classApiMethodMethod, "_stopPurposeNum')); }, function() { addError($('#", classApiMethodMethod, "_stopPurposeNum')); }); ")
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

	/**	 The entity stopPurposeTitle
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String stopPurposeTitle;
	@JsonIgnore
	public Wrap<String> stopPurposeTitleWrap = new Wrap<String>().p(this).c(String.class).var("stopPurposeTitle").o(stopPurposeTitle);

	/**	<br/> The entity stopPurposeTitle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopPurposeTitle">Find the entity stopPurposeTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopPurposeTitle(Wrap<String> w);

	public String getStopPurposeTitle() {
		return stopPurposeTitle;
	}
	public void setStopPurposeTitle(String o) {
		this.stopPurposeTitle = SearchBasis.staticSetStopPurposeTitle(siteRequest_, o);
		this.stopPurposeTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetStopPurposeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SearchBasis stopPurposeTitleInit() {
		if(!stopPurposeTitleWrap.alreadyInitialized) {
			_stopPurposeTitle(stopPurposeTitleWrap);
			if(stopPurposeTitle == null)
				setStopPurposeTitle(stopPurposeTitleWrap.o);
		}
		stopPurposeTitleWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static String staticSolrStopPurposeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopPurposeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopPurposeTitle(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrStopPurposeTitle(siteRequest_, SearchBasis.staticSolrStopPurposeTitle(siteRequest_, SearchBasis.staticSetStopPurposeTitle(siteRequest_, o)));
	}

	public String solrStopPurposeTitle() {
		return SearchBasis.staticSolrStopPurposeTitle(siteRequest_, stopPurposeTitle);
	}

	public String strStopPurposeTitle() {
		return stopPurposeTitle == null ? "" : stopPurposeTitle;
	}

	public String jsonStopPurposeTitle() {
		return stopPurposeTitle == null ? "" : stopPurposeTitle;
	}

	public String nomAffichageStopPurposeTitle() {
		return "stop purpose title";
	}

	public String htmTooltipStopPurposeTitle() {
		return null;
	}

	public String htmStopPurposeTitle() {
		return stopPurposeTitle == null ? "" : StringEscapeUtils.escapeHtml4(strStopPurposeTitle());
	}

	public void inputStopPurposeTitle(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "stop purpose title")
				.a("id", classApiMethodMethod, "_stopPurposeTitle");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setStopPurposeTitle classSearchBasis inputSearchBasis", pk, "StopPurposeTitle w3-input w3-border ");
					a("name", "setStopPurposeTitle");
				} else {
					a("class", "valueStopPurposeTitle w3-input w3-border classSearchBasis inputSearchBasis", pk, "StopPurposeTitle w3-input w3-border ");
					a("name", "stopPurposeTitle");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStopPurposeTitle', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_stopPurposeTitle')); }, function() { addError($('#", classApiMethodMethod, "_stopPurposeTitle')); }); ");
				}
				a("value", strStopPurposeTitle())
			.fg();

		} else {
			e("span").a("class", "varSearchBasis", pk, "StopPurposeTitle ").f().sx(htmStopPurposeTitle()).g("span");
		}
	}

	public void htmStopPurposeTitle(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisStopPurposeTitle").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_stopPurposeTitle").a("class", "").f().sx("stop purpose title").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputStopPurposeTitle(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_stopPurposeTitle')); $('#", classApiMethodMethod, "_stopPurposeTitle').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SearchBasisForm :input[name=pk]').val() }], 'setStopPurposeTitle', null, function() { addGlow($('#", classApiMethodMethod, "_stopPurposeTitle')); }, function() { addError($('#", classApiMethodMethod, "_stopPurposeTitle')); }); ")
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopActionNum">Find the entity stopActionNum in Solr</a>
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
		this.stopActionNum = SearchBasis.staticSetStopActionNum(siteRequest_, o);
		this.stopActionNumWrap.alreadyInitialized = true;
	}
	public static Integer staticSetStopActionNum(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected SearchBasis stopActionNumInit() {
		if(!stopActionNumWrap.alreadyInitialized) {
			_stopActionNum(stopActionNumWrap);
			if(stopActionNum == null)
				setStopActionNum(stopActionNumWrap.o);
		}
		stopActionNumWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Integer staticSolrStopActionNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrStopActionNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopActionNum(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrStopActionNum(siteRequest_, SearchBasis.staticSolrStopActionNum(siteRequest_, SearchBasis.staticSetStopActionNum(siteRequest_, o)));
	}

	public Integer solrStopActionNum() {
		return SearchBasis.staticSolrStopActionNum(siteRequest_, stopActionNum);
	}

	public String strStopActionNum() {
		return stopActionNum == null ? "" : stopActionNum.toString();
	}

	public String jsonStopActionNum() {
		return stopActionNum == null ? "" : stopActionNum.toString();
	}

	public String nomAffichageStopActionNum() {
		return "stop action number";
	}

	public String htmTooltipStopActionNum() {
		return null;
	}

	public String htmStopActionNum() {
		return stopActionNum == null ? "" : StringEscapeUtils.escapeHtml4(strStopActionNum());
	}

	public void inputStopActionNum(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "stop action number")
				.a("id", classApiMethodMethod, "_stopActionNum");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setStopActionNum classSearchBasis inputSearchBasis", pk, "StopActionNum w3-input w3-border ");
					a("name", "setStopActionNum");
				} else {
					a("class", "valueStopActionNum w3-input w3-border classSearchBasis inputSearchBasis", pk, "StopActionNum w3-input w3-border ");
					a("name", "stopActionNum");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStopActionNum', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_stopActionNum')); }, function() { addError($('#", classApiMethodMethod, "_stopActionNum')); }); ");
				}
				a("value", strStopActionNum())
			.fg();

		} else {
			e("span").a("class", "varSearchBasis", pk, "StopActionNum ").f().sx(htmStopActionNum()).g("span");
		}
	}

	public void htmStopActionNum(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisStopActionNum").f();
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_stopActionNum')); $('#", classApiMethodMethod, "_stopActionNum').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SearchBasisForm :input[name=pk]').val() }], 'setStopActionNum', null, function() { addGlow($('#", classApiMethodMethod, "_stopActionNum')); }, function() { addError($('#", classApiMethodMethod, "_stopActionNum')); }); ")
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

	/**	 The entity stopActionTitle
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String stopActionTitle;
	@JsonIgnore
	public Wrap<String> stopActionTitleWrap = new Wrap<String>().p(this).c(String.class).var("stopActionTitle").o(stopActionTitle);

	/**	<br/> The entity stopActionTitle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopActionTitle">Find the entity stopActionTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopActionTitle(Wrap<String> w);

	public String getStopActionTitle() {
		return stopActionTitle;
	}
	public void setStopActionTitle(String o) {
		this.stopActionTitle = SearchBasis.staticSetStopActionTitle(siteRequest_, o);
		this.stopActionTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetStopActionTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SearchBasis stopActionTitleInit() {
		if(!stopActionTitleWrap.alreadyInitialized) {
			_stopActionTitle(stopActionTitleWrap);
			if(stopActionTitle == null)
				setStopActionTitle(stopActionTitleWrap.o);
		}
		stopActionTitleWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static String staticSolrStopActionTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopActionTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopActionTitle(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrStopActionTitle(siteRequest_, SearchBasis.staticSolrStopActionTitle(siteRequest_, SearchBasis.staticSetStopActionTitle(siteRequest_, o)));
	}

	public String solrStopActionTitle() {
		return SearchBasis.staticSolrStopActionTitle(siteRequest_, stopActionTitle);
	}

	public String strStopActionTitle() {
		return stopActionTitle == null ? "" : stopActionTitle;
	}

	public String jsonStopActionTitle() {
		return stopActionTitle == null ? "" : stopActionTitle;
	}

	public String nomAffichageStopActionTitle() {
		return "agency title";
	}

	public String htmTooltipStopActionTitle() {
		return null;
	}

	public String htmStopActionTitle() {
		return stopActionTitle == null ? "" : StringEscapeUtils.escapeHtml4(strStopActionTitle());
	}

	public void inputStopActionTitle(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "agency title")
				.a("id", classApiMethodMethod, "_stopActionTitle");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setStopActionTitle classSearchBasis inputSearchBasis", pk, "StopActionTitle w3-input w3-border ");
					a("name", "setStopActionTitle");
				} else {
					a("class", "valueStopActionTitle w3-input w3-border classSearchBasis inputSearchBasis", pk, "StopActionTitle w3-input w3-border ");
					a("name", "stopActionTitle");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStopActionTitle', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_stopActionTitle')); }, function() { addError($('#", classApiMethodMethod, "_stopActionTitle')); }); ");
				}
				a("value", strStopActionTitle())
			.fg();

		} else {
			e("span").a("class", "varSearchBasis", pk, "StopActionTitle ").f().sx(htmStopActionTitle()).g("span");
		}
	}

	public void htmStopActionTitle(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisStopActionTitle").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_stopActionTitle").a("class", "").f().sx("agency title").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputStopActionTitle(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_stopActionTitle')); $('#", classApiMethodMethod, "_stopActionTitle').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SearchBasisForm :input[name=pk]').val() }], 'setStopActionTitle', null, function() { addGlow($('#", classApiMethodMethod, "_stopActionTitle')); }, function() { addError($('#", classApiMethodMethod, "_stopActionTitle')); }); ")
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopDriverArrest">Find the entity stopDriverArrest in Solr</a>
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
		this.stopDriverArrest = SearchBasis.staticSetStopDriverArrest(siteRequest_, o);
		this.stopDriverArrestWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopDriverArrest(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SearchBasis stopDriverArrestInit() {
		if(!stopDriverArrestWrap.alreadyInitialized) {
			_stopDriverArrest(stopDriverArrestWrap);
			if(stopDriverArrest == null)
				setStopDriverArrest(stopDriverArrestWrap.o);
		}
		stopDriverArrestWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Boolean staticSolrStopDriverArrest(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopDriverArrest(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopDriverArrest(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrStopDriverArrest(siteRequest_, SearchBasis.staticSolrStopDriverArrest(siteRequest_, SearchBasis.staticSetStopDriverArrest(siteRequest_, o)));
	}

	public Boolean solrStopDriverArrest() {
		return SearchBasis.staticSolrStopDriverArrest(siteRequest_, stopDriverArrest);
	}

	public String strStopDriverArrest() {
		return stopDriverArrest == null ? "" : stopDriverArrest.toString();
	}

	public String jsonStopDriverArrest() {
		return stopDriverArrest == null ? "" : stopDriverArrest.toString();
	}

	public String nomAffichageStopDriverArrest() {
		return "driver arrest";
	}

	public String htmTooltipStopDriverArrest() {
		return null;
	}

	public String htmStopDriverArrest() {
		return stopDriverArrest == null ? "" : StringEscapeUtils.escapeHtml4(strStopDriverArrest());
	}

	public void inputStopDriverArrest(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
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
				a("class", "setStopDriverArrest classSearchBasis inputSearchBasis", pk, "StopDriverArrest w3-input w3-border ");
				a("name", "setStopDriverArrest");
			} else {
				a("class", "valueStopDriverArrest classSearchBasis inputSearchBasis", pk, "StopDriverArrest w3-input w3-border ");
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
			e("span").a("class", "varSearchBasis", pk, "StopDriverArrest ").f().sx(htmStopDriverArrest()).g("span");
		}
	}

	public void htmStopDriverArrest(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisStopDriverArrest").f();
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

	/**	 The entity stopPassengerArrest
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean stopPassengerArrest;
	@JsonIgnore
	public Wrap<Boolean> stopPassengerArrestWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("stopPassengerArrest").o(stopPassengerArrest);

	/**	<br/> The entity stopPassengerArrest
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopPassengerArrest">Find the entity stopPassengerArrest in Solr</a>
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
		this.stopPassengerArrest = SearchBasis.staticSetStopPassengerArrest(siteRequest_, o);
		this.stopPassengerArrestWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopPassengerArrest(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SearchBasis stopPassengerArrestInit() {
		if(!stopPassengerArrestWrap.alreadyInitialized) {
			_stopPassengerArrest(stopPassengerArrestWrap);
			if(stopPassengerArrest == null)
				setStopPassengerArrest(stopPassengerArrestWrap.o);
		}
		stopPassengerArrestWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Boolean staticSolrStopPassengerArrest(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopPassengerArrest(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopPassengerArrest(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrStopPassengerArrest(siteRequest_, SearchBasis.staticSolrStopPassengerArrest(siteRequest_, SearchBasis.staticSetStopPassengerArrest(siteRequest_, o)));
	}

	public Boolean solrStopPassengerArrest() {
		return SearchBasis.staticSolrStopPassengerArrest(siteRequest_, stopPassengerArrest);
	}

	public String strStopPassengerArrest() {
		return stopPassengerArrest == null ? "" : stopPassengerArrest.toString();
	}

	public String jsonStopPassengerArrest() {
		return stopPassengerArrest == null ? "" : stopPassengerArrest.toString();
	}

	public String nomAffichageStopPassengerArrest() {
		return "passenger arrest";
	}

	public String htmTooltipStopPassengerArrest() {
		return null;
	}

	public String htmStopPassengerArrest() {
		return stopPassengerArrest == null ? "" : StringEscapeUtils.escapeHtml4(strStopPassengerArrest());
	}

	public void inputStopPassengerArrest(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
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
				a("class", "setStopPassengerArrest classSearchBasis inputSearchBasis", pk, "StopPassengerArrest w3-input w3-border ");
				a("name", "setStopPassengerArrest");
			} else {
				a("class", "valueStopPassengerArrest classSearchBasis inputSearchBasis", pk, "StopPassengerArrest w3-input w3-border ");
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
			e("span").a("class", "varSearchBasis", pk, "StopPassengerArrest ").f().sx(htmStopPassengerArrest()).g("span");
		}
	}

	public void htmStopPassengerArrest(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisStopPassengerArrest").f();
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

	/**	 The entity stopEncounterForce
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean stopEncounterForce;
	@JsonIgnore
	public Wrap<Boolean> stopEncounterForceWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("stopEncounterForce").o(stopEncounterForce);

	/**	<br/> The entity stopEncounterForce
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopEncounterForce">Find the entity stopEncounterForce in Solr</a>
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
		this.stopEncounterForce = SearchBasis.staticSetStopEncounterForce(siteRequest_, o);
		this.stopEncounterForceWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopEncounterForce(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SearchBasis stopEncounterForceInit() {
		if(!stopEncounterForceWrap.alreadyInitialized) {
			_stopEncounterForce(stopEncounterForceWrap);
			if(stopEncounterForce == null)
				setStopEncounterForce(stopEncounterForceWrap.o);
		}
		stopEncounterForceWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Boolean staticSolrStopEncounterForce(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopEncounterForce(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopEncounterForce(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrStopEncounterForce(siteRequest_, SearchBasis.staticSolrStopEncounterForce(siteRequest_, SearchBasis.staticSetStopEncounterForce(siteRequest_, o)));
	}

	public Boolean solrStopEncounterForce() {
		return SearchBasis.staticSolrStopEncounterForce(siteRequest_, stopEncounterForce);
	}

	public String strStopEncounterForce() {
		return stopEncounterForce == null ? "" : stopEncounterForce.toString();
	}

	public String jsonStopEncounterForce() {
		return stopEncounterForce == null ? "" : stopEncounterForce.toString();
	}

	public String nomAffichageStopEncounterForce() {
		return "encounter force";
	}

	public String htmTooltipStopEncounterForce() {
		return null;
	}

	public String htmStopEncounterForce() {
		return stopEncounterForce == null ? "" : StringEscapeUtils.escapeHtml4(strStopEncounterForce());
	}

	public void inputStopEncounterForce(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
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
				a("class", "setStopEncounterForce classSearchBasis inputSearchBasis", pk, "StopEncounterForce w3-input w3-border ");
				a("name", "setStopEncounterForce");
			} else {
				a("class", "valueStopEncounterForce classSearchBasis inputSearchBasis", pk, "StopEncounterForce w3-input w3-border ");
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
			e("span").a("class", "varSearchBasis", pk, "StopEncounterForce ").f().sx(htmStopEncounterForce()).g("span");
		}
	}

	public void htmStopEncounterForce(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisStopEncounterForce").f();
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

	/**	 The entity stopEngageForce
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean stopEngageForce;
	@JsonIgnore
	public Wrap<Boolean> stopEngageForceWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("stopEngageForce").o(stopEngageForce);

	/**	<br/> The entity stopEngageForce
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopEngageForce">Find the entity stopEngageForce in Solr</a>
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
		this.stopEngageForce = SearchBasis.staticSetStopEngageForce(siteRequest_, o);
		this.stopEngageForceWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopEngageForce(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SearchBasis stopEngageForceInit() {
		if(!stopEngageForceWrap.alreadyInitialized) {
			_stopEngageForce(stopEngageForceWrap);
			if(stopEngageForce == null)
				setStopEngageForce(stopEngageForceWrap.o);
		}
		stopEngageForceWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Boolean staticSolrStopEngageForce(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopEngageForce(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopEngageForce(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrStopEngageForce(siteRequest_, SearchBasis.staticSolrStopEngageForce(siteRequest_, SearchBasis.staticSetStopEngageForce(siteRequest_, o)));
	}

	public Boolean solrStopEngageForce() {
		return SearchBasis.staticSolrStopEngageForce(siteRequest_, stopEngageForce);
	}

	public String strStopEngageForce() {
		return stopEngageForce == null ? "" : stopEngageForce.toString();
	}

	public String jsonStopEngageForce() {
		return stopEngageForce == null ? "" : stopEngageForce.toString();
	}

	public String nomAffichageStopEngageForce() {
		return "engage force";
	}

	public String htmTooltipStopEngageForce() {
		return null;
	}

	public String htmStopEngageForce() {
		return stopEngageForce == null ? "" : StringEscapeUtils.escapeHtml4(strStopEngageForce());
	}

	public void inputStopEngageForce(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
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
				a("class", "setStopEngageForce classSearchBasis inputSearchBasis", pk, "StopEngageForce w3-input w3-border ");
				a("name", "setStopEngageForce");
			} else {
				a("class", "valueStopEngageForce classSearchBasis inputSearchBasis", pk, "StopEngageForce w3-input w3-border ");
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
			e("span").a("class", "varSearchBasis", pk, "StopEngageForce ").f().sx(htmStopEngageForce()).g("span");
		}
	}

	public void htmStopEngageForce(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisStopEngageForce").f();
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

	/**	 The entity stopOfficerInjury
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean stopOfficerInjury;
	@JsonIgnore
	public Wrap<Boolean> stopOfficerInjuryWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("stopOfficerInjury").o(stopOfficerInjury);

	/**	<br/> The entity stopOfficerInjury
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopOfficerInjury">Find the entity stopOfficerInjury in Solr</a>
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
		this.stopOfficerInjury = SearchBasis.staticSetStopOfficerInjury(siteRequest_, o);
		this.stopOfficerInjuryWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopOfficerInjury(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SearchBasis stopOfficerInjuryInit() {
		if(!stopOfficerInjuryWrap.alreadyInitialized) {
			_stopOfficerInjury(stopOfficerInjuryWrap);
			if(stopOfficerInjury == null)
				setStopOfficerInjury(stopOfficerInjuryWrap.o);
		}
		stopOfficerInjuryWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Boolean staticSolrStopOfficerInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopOfficerInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopOfficerInjury(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrStopOfficerInjury(siteRequest_, SearchBasis.staticSolrStopOfficerInjury(siteRequest_, SearchBasis.staticSetStopOfficerInjury(siteRequest_, o)));
	}

	public Boolean solrStopOfficerInjury() {
		return SearchBasis.staticSolrStopOfficerInjury(siteRequest_, stopOfficerInjury);
	}

	public String strStopOfficerInjury() {
		return stopOfficerInjury == null ? "" : stopOfficerInjury.toString();
	}

	public String jsonStopOfficerInjury() {
		return stopOfficerInjury == null ? "" : stopOfficerInjury.toString();
	}

	public String nomAffichageStopOfficerInjury() {
		return "officer injury";
	}

	public String htmTooltipStopOfficerInjury() {
		return null;
	}

	public String htmStopOfficerInjury() {
		return stopOfficerInjury == null ? "" : StringEscapeUtils.escapeHtml4(strStopOfficerInjury());
	}

	public void inputStopOfficerInjury(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
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
				a("class", "setStopOfficerInjury classSearchBasis inputSearchBasis", pk, "StopOfficerInjury w3-input w3-border ");
				a("name", "setStopOfficerInjury");
			} else {
				a("class", "valueStopOfficerInjury classSearchBasis inputSearchBasis", pk, "StopOfficerInjury w3-input w3-border ");
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
			e("span").a("class", "varSearchBasis", pk, "StopOfficerInjury ").f().sx(htmStopOfficerInjury()).g("span");
		}
	}

	public void htmStopOfficerInjury(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisStopOfficerInjury").f();
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

	/**	 The entity stopDriverInjury
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean stopDriverInjury;
	@JsonIgnore
	public Wrap<Boolean> stopDriverInjuryWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("stopDriverInjury").o(stopDriverInjury);

	/**	<br/> The entity stopDriverInjury
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopDriverInjury">Find the entity stopDriverInjury in Solr</a>
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
		this.stopDriverInjury = SearchBasis.staticSetStopDriverInjury(siteRequest_, o);
		this.stopDriverInjuryWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopDriverInjury(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SearchBasis stopDriverInjuryInit() {
		if(!stopDriverInjuryWrap.alreadyInitialized) {
			_stopDriverInjury(stopDriverInjuryWrap);
			if(stopDriverInjury == null)
				setStopDriverInjury(stopDriverInjuryWrap.o);
		}
		stopDriverInjuryWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Boolean staticSolrStopDriverInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopDriverInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopDriverInjury(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrStopDriverInjury(siteRequest_, SearchBasis.staticSolrStopDriverInjury(siteRequest_, SearchBasis.staticSetStopDriverInjury(siteRequest_, o)));
	}

	public Boolean solrStopDriverInjury() {
		return SearchBasis.staticSolrStopDriverInjury(siteRequest_, stopDriverInjury);
	}

	public String strStopDriverInjury() {
		return stopDriverInjury == null ? "" : stopDriverInjury.toString();
	}

	public String jsonStopDriverInjury() {
		return stopDriverInjury == null ? "" : stopDriverInjury.toString();
	}

	public String nomAffichageStopDriverInjury() {
		return "driver injury";
	}

	public String htmTooltipStopDriverInjury() {
		return null;
	}

	public String htmStopDriverInjury() {
		return stopDriverInjury == null ? "" : StringEscapeUtils.escapeHtml4(strStopDriverInjury());
	}

	public void inputStopDriverInjury(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
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
				a("class", "setStopDriverInjury classSearchBasis inputSearchBasis", pk, "StopDriverInjury w3-input w3-border ");
				a("name", "setStopDriverInjury");
			} else {
				a("class", "valueStopDriverInjury classSearchBasis inputSearchBasis", pk, "StopDriverInjury w3-input w3-border ");
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
			e("span").a("class", "varSearchBasis", pk, "StopDriverInjury ").f().sx(htmStopDriverInjury()).g("span");
		}
	}

	public void htmStopDriverInjury(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisStopDriverInjury").f();
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

	/**	 The entity stopPassengerInjury
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean stopPassengerInjury;
	@JsonIgnore
	public Wrap<Boolean> stopPassengerInjuryWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("stopPassengerInjury").o(stopPassengerInjury);

	/**	<br/> The entity stopPassengerInjury
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopPassengerInjury">Find the entity stopPassengerInjury in Solr</a>
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
		this.stopPassengerInjury = SearchBasis.staticSetStopPassengerInjury(siteRequest_, o);
		this.stopPassengerInjuryWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetStopPassengerInjury(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SearchBasis stopPassengerInjuryInit() {
		if(!stopPassengerInjuryWrap.alreadyInitialized) {
			_stopPassengerInjury(stopPassengerInjuryWrap);
			if(stopPassengerInjury == null)
				setStopPassengerInjury(stopPassengerInjuryWrap.o);
		}
		stopPassengerInjuryWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Boolean staticSolrStopPassengerInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrStopPassengerInjury(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopPassengerInjury(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrStopPassengerInjury(siteRequest_, SearchBasis.staticSolrStopPassengerInjury(siteRequest_, SearchBasis.staticSetStopPassengerInjury(siteRequest_, o)));
	}

	public Boolean solrStopPassengerInjury() {
		return SearchBasis.staticSolrStopPassengerInjury(siteRequest_, stopPassengerInjury);
	}

	public String strStopPassengerInjury() {
		return stopPassengerInjury == null ? "" : stopPassengerInjury.toString();
	}

	public String jsonStopPassengerInjury() {
		return stopPassengerInjury == null ? "" : stopPassengerInjury.toString();
	}

	public String nomAffichageStopPassengerInjury() {
		return "passenger injury";
	}

	public String htmTooltipStopPassengerInjury() {
		return null;
	}

	public String htmStopPassengerInjury() {
		return stopPassengerInjury == null ? "" : StringEscapeUtils.escapeHtml4(strStopPassengerInjury());
	}

	public void inputStopPassengerInjury(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
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
				a("class", "setStopPassengerInjury classSearchBasis inputSearchBasis", pk, "StopPassengerInjury w3-input w3-border ");
				a("name", "setStopPassengerInjury");
			} else {
				a("class", "valueStopPassengerInjury classSearchBasis inputSearchBasis", pk, "StopPassengerInjury w3-input w3-border ");
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
			e("span").a("class", "varSearchBasis", pk, "StopPassengerInjury ").f().sx(htmStopPassengerInjury()).g("span");
		}
	}

	public void htmStopPassengerInjury(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisStopPassengerInjury").f();
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

	/**	 The entity stopOfficerId
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String stopOfficerId;
	@JsonIgnore
	public Wrap<String> stopOfficerIdWrap = new Wrap<String>().p(this).c(String.class).var("stopOfficerId").o(stopOfficerId);

	/**	<br/> The entity stopOfficerId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopOfficerId">Find the entity stopOfficerId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopOfficerId(Wrap<String> w);

	public String getStopOfficerId() {
		return stopOfficerId;
	}
	public void setStopOfficerId(String o) {
		this.stopOfficerId = SearchBasis.staticSetStopOfficerId(siteRequest_, o);
		this.stopOfficerIdWrap.alreadyInitialized = true;
	}
	public static String staticSetStopOfficerId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SearchBasis stopOfficerIdInit() {
		if(!stopOfficerIdWrap.alreadyInitialized) {
			_stopOfficerId(stopOfficerIdWrap);
			if(stopOfficerId == null)
				setStopOfficerId(stopOfficerIdWrap.o);
		}
		stopOfficerIdWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static String staticSolrStopOfficerId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopOfficerId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopOfficerId(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrStopOfficerId(siteRequest_, SearchBasis.staticSolrStopOfficerId(siteRequest_, SearchBasis.staticSetStopOfficerId(siteRequest_, o)));
	}

	public String solrStopOfficerId() {
		return SearchBasis.staticSolrStopOfficerId(siteRequest_, stopOfficerId);
	}

	public String strStopOfficerId() {
		return stopOfficerId == null ? "" : stopOfficerId;
	}

	public String jsonStopOfficerId() {
		return stopOfficerId == null ? "" : stopOfficerId;
	}

	public String nomAffichageStopOfficerId() {
		return "officer ID";
	}

	public String htmTooltipStopOfficerId() {
		return null;
	}

	public String htmStopOfficerId() {
		return stopOfficerId == null ? "" : StringEscapeUtils.escapeHtml4(strStopOfficerId());
	}

	public void inputStopOfficerId(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "officer ID")
				.a("id", classApiMethodMethod, "_stopOfficerId");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setStopOfficerId classSearchBasis inputSearchBasis", pk, "StopOfficerId w3-input w3-border ");
					a("name", "setStopOfficerId");
				} else {
					a("class", "valueStopOfficerId w3-input w3-border classSearchBasis inputSearchBasis", pk, "StopOfficerId w3-input w3-border ");
					a("name", "stopOfficerId");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStopOfficerId', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_stopOfficerId')); }, function() { addError($('#", classApiMethodMethod, "_stopOfficerId')); }); ");
				}
				a("value", strStopOfficerId())
			.fg();

		} else {
			e("span").a("class", "varSearchBasis", pk, "StopOfficerId ").f().sx(htmStopOfficerId()).g("span");
		}
	}

	public void htmStopOfficerId(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisStopOfficerId").f();
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_stopOfficerId')); $('#", classApiMethodMethod, "_stopOfficerId').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SearchBasisForm :input[name=pk]').val() }], 'setStopOfficerId', null, function() { addGlow($('#", classApiMethodMethod, "_stopOfficerId')); }, function() { addError($('#", classApiMethodMethod, "_stopOfficerId')); }); ")
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

	/**	 The entity stopLocationId
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String stopLocationId;
	@JsonIgnore
	public Wrap<String> stopLocationIdWrap = new Wrap<String>().p(this).c(String.class).var("stopLocationId").o(stopLocationId);

	/**	<br/> The entity stopLocationId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopLocationId">Find the entity stopLocationId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopLocationId(Wrap<String> w);

	public String getStopLocationId() {
		return stopLocationId;
	}
	public void setStopLocationId(String o) {
		this.stopLocationId = SearchBasis.staticSetStopLocationId(siteRequest_, o);
		this.stopLocationIdWrap.alreadyInitialized = true;
	}
	public static String staticSetStopLocationId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SearchBasis stopLocationIdInit() {
		if(!stopLocationIdWrap.alreadyInitialized) {
			_stopLocationId(stopLocationIdWrap);
			if(stopLocationId == null)
				setStopLocationId(stopLocationIdWrap.o);
		}
		stopLocationIdWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static String staticSolrStopLocationId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopLocationId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopLocationId(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrStopLocationId(siteRequest_, SearchBasis.staticSolrStopLocationId(siteRequest_, SearchBasis.staticSetStopLocationId(siteRequest_, o)));
	}

	public String solrStopLocationId() {
		return SearchBasis.staticSolrStopLocationId(siteRequest_, stopLocationId);
	}

	public String strStopLocationId() {
		return stopLocationId == null ? "" : stopLocationId;
	}

	public String jsonStopLocationId() {
		return stopLocationId == null ? "" : stopLocationId;
	}

	public String nomAffichageStopLocationId() {
		return "location ID";
	}

	public String htmTooltipStopLocationId() {
		return null;
	}

	public String htmStopLocationId() {
		return stopLocationId == null ? "" : StringEscapeUtils.escapeHtml4(strStopLocationId());
	}

	public void inputStopLocationId(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "location ID")
				.a("id", classApiMethodMethod, "_stopLocationId");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setStopLocationId classSearchBasis inputSearchBasis", pk, "StopLocationId w3-input w3-border ");
					a("name", "setStopLocationId");
				} else {
					a("class", "valueStopLocationId w3-input w3-border classSearchBasis inputSearchBasis", pk, "StopLocationId w3-input w3-border ");
					a("name", "stopLocationId");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStopLocationId', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_stopLocationId')); }, function() { addError($('#", classApiMethodMethod, "_stopLocationId')); }); ");
				}
				a("value", strStopLocationId())
			.fg();

		} else {
			e("span").a("class", "varSearchBasis", pk, "StopLocationId ").f().sx(htmStopLocationId()).g("span");
		}
	}

	public void htmStopLocationId(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisStopLocationId").f();
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_stopLocationId')); $('#", classApiMethodMethod, "_stopLocationId').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SearchBasisForm :input[name=pk]').val() }], 'setStopLocationId', null, function() { addGlow($('#", classApiMethodMethod, "_stopLocationId')); }, function() { addError($('#", classApiMethodMethod, "_stopLocationId')); }); ")
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

	/**	 The entity stopCityId
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String stopCityId;
	@JsonIgnore
	public Wrap<String> stopCityIdWrap = new Wrap<String>().p(this).c(String.class).var("stopCityId").o(stopCityId);

	/**	<br/> The entity stopCityId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopCityId">Find the entity stopCityId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopCityId(Wrap<String> w);

	public String getStopCityId() {
		return stopCityId;
	}
	public void setStopCityId(String o) {
		this.stopCityId = SearchBasis.staticSetStopCityId(siteRequest_, o);
		this.stopCityIdWrap.alreadyInitialized = true;
	}
	public static String staticSetStopCityId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SearchBasis stopCityIdInit() {
		if(!stopCityIdWrap.alreadyInitialized) {
			_stopCityId(stopCityIdWrap);
			if(stopCityId == null)
				setStopCityId(stopCityIdWrap.o);
		}
		stopCityIdWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static String staticSolrStopCityId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopCityId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopCityId(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrStopCityId(siteRequest_, SearchBasis.staticSolrStopCityId(siteRequest_, SearchBasis.staticSetStopCityId(siteRequest_, o)));
	}

	public String solrStopCityId() {
		return SearchBasis.staticSolrStopCityId(siteRequest_, stopCityId);
	}

	public String strStopCityId() {
		return stopCityId == null ? "" : stopCityId;
	}

	public String jsonStopCityId() {
		return stopCityId == null ? "" : stopCityId;
	}

	public String nomAffichageStopCityId() {
		return "city ID";
	}

	public String htmTooltipStopCityId() {
		return null;
	}

	public String htmStopCityId() {
		return stopCityId == null ? "" : StringEscapeUtils.escapeHtml4(strStopCityId());
	}

	public void inputStopCityId(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "city ID")
				.a("id", classApiMethodMethod, "_stopCityId");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setStopCityId classSearchBasis inputSearchBasis", pk, "StopCityId w3-input w3-border ");
					a("name", "setStopCityId");
				} else {
					a("class", "valueStopCityId w3-input w3-border classSearchBasis inputSearchBasis", pk, "StopCityId w3-input w3-border ");
					a("name", "stopCityId");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStopCityId', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_stopCityId')); }, function() { addError($('#", classApiMethodMethod, "_stopCityId')); }); ");
				}
				a("value", strStopCityId())
			.fg();

		} else {
			e("span").a("class", "varSearchBasis", pk, "StopCityId ").f().sx(htmStopCityId()).g("span");
		}
	}

	public void htmStopCityId(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisStopCityId").f();
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_stopCityId')); $('#", classApiMethodMethod, "_stopCityId').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SearchBasisForm :input[name=pk]').val() }], 'setStopCityId', null, function() { addGlow($('#", classApiMethodMethod, "_stopCityId')); }, function() { addError($('#", classApiMethodMethod, "_stopCityId')); }); ")
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personAge">Find the entity personAge in Solr</a>
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
		this.personAge = SearchBasis.staticSetPersonAge(siteRequest_, o);
		this.personAgeWrap.alreadyInitialized = true;
	}
	public static Integer staticSetPersonAge(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected SearchBasis personAgeInit() {
		if(!personAgeWrap.alreadyInitialized) {
			_personAge(personAgeWrap);
			if(personAge == null)
				setPersonAge(personAgeWrap.o);
		}
		personAgeWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Integer staticSolrPersonAge(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrPersonAge(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonAge(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrPersonAge(siteRequest_, SearchBasis.staticSolrPersonAge(siteRequest_, SearchBasis.staticSetPersonAge(siteRequest_, o)));
	}

	public Integer solrPersonAge() {
		return SearchBasis.staticSolrPersonAge(siteRequest_, personAge);
	}

	public String strPersonAge() {
		return personAge == null ? "" : personAge.toString();
	}

	public String jsonPersonAge() {
		return personAge == null ? "" : personAge.toString();
	}

	public String nomAffichagePersonAge() {
		return "person age";
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personTypeId">Find the entity personTypeId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personTypeId(Wrap<String> w);

	public String getPersonTypeId() {
		return personTypeId;
	}
	public void setPersonTypeId(String o) {
		this.personTypeId = SearchBasis.staticSetPersonTypeId(siteRequest_, o);
		this.personTypeIdWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonTypeId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SearchBasis personTypeIdInit() {
		if(!personTypeIdWrap.alreadyInitialized) {
			_personTypeId(personTypeIdWrap);
			if(personTypeId == null)
				setPersonTypeId(personTypeIdWrap.o);
		}
		personTypeIdWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static String staticSolrPersonTypeId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonTypeId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonTypeId(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrPersonTypeId(siteRequest_, SearchBasis.staticSolrPersonTypeId(siteRequest_, SearchBasis.staticSetPersonTypeId(siteRequest_, o)));
	}

	public String solrPersonTypeId() {
		return SearchBasis.staticSolrPersonTypeId(siteRequest_, personTypeId);
	}

	public String strPersonTypeId() {
		return personTypeId == null ? "" : personTypeId;
	}

	public String jsonPersonTypeId() {
		return personTypeId == null ? "" : personTypeId;
	}

	public String nomAffichagePersonTypeId() {
		return "person type ID";
	}

	public String htmTooltipPersonTypeId() {
		return null;
	}

	public String htmPersonTypeId() {
		return personTypeId == null ? "" : StringEscapeUtils.escapeHtml4(strPersonTypeId());
	}

	public void inputPersonTypeId(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "person type ID")
				.a("id", classApiMethodMethod, "_personTypeId");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setPersonTypeId classSearchBasis inputSearchBasis", pk, "PersonTypeId w3-input w3-border ");
					a("name", "setPersonTypeId");
				} else {
					a("class", "valuePersonTypeId w3-input w3-border classSearchBasis inputSearchBasis", pk, "PersonTypeId w3-input w3-border ");
					a("name", "personTypeId");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setPersonTypeId', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_personTypeId')); }, function() { addError($('#", classApiMethodMethod, "_personTypeId')); }); ");
				}
				a("value", strPersonTypeId())
			.fg();

		} else {
			e("span").a("class", "varSearchBasis", pk, "PersonTypeId ").f().sx(htmPersonTypeId()).g("span");
		}
	}

	public void htmPersonTypeId(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisPersonTypeId").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_personTypeId").a("class", "").f().sx("person type ID").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPersonTypeId(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_personTypeId')); $('#", classApiMethodMethod, "_personTypeId').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SearchBasisForm :input[name=pk]').val() }], 'setPersonTypeId', null, function() { addGlow($('#", classApiMethodMethod, "_personTypeId')); }, function() { addError($('#", classApiMethodMethod, "_personTypeId')); }); ")
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personTypeTitle">Find the entity personTypeTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personTypeTitle(Wrap<String> w);

	public String getPersonTypeTitle() {
		return personTypeTitle;
	}
	public void setPersonTypeTitle(String o) {
		this.personTypeTitle = SearchBasis.staticSetPersonTypeTitle(siteRequest_, o);
		this.personTypeTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SearchBasis personTypeTitleInit() {
		if(!personTypeTitleWrap.alreadyInitialized) {
			_personTypeTitle(personTypeTitleWrap);
			if(personTypeTitle == null)
				setPersonTypeTitle(personTypeTitleWrap.o);
		}
		personTypeTitleWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static String staticSolrPersonTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrPersonTypeTitle(siteRequest_, SearchBasis.staticSolrPersonTypeTitle(siteRequest_, SearchBasis.staticSetPersonTypeTitle(siteRequest_, o)));
	}

	public String solrPersonTypeTitle() {
		return SearchBasis.staticSolrPersonTypeTitle(siteRequest_, personTypeTitle);
	}

	public String strPersonTypeTitle() {
		return personTypeTitle == null ? "" : personTypeTitle;
	}

	public String jsonPersonTypeTitle() {
		return personTypeTitle == null ? "" : personTypeTitle;
	}

	public String nomAffichagePersonTypeTitle() {
		return "person type title";
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personTypeDriver">Find the entity personTypeDriver in Solr</a>
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
		this.personTypeDriver = SearchBasis.staticSetPersonTypeDriver(siteRequest_, o);
		this.personTypeDriverWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetPersonTypeDriver(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SearchBasis personTypeDriverInit() {
		if(!personTypeDriverWrap.alreadyInitialized) {
			_personTypeDriver(personTypeDriverWrap);
			if(personTypeDriver == null)
				setPersonTypeDriver(personTypeDriverWrap.o);
		}
		personTypeDriverWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Boolean staticSolrPersonTypeDriver(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrPersonTypeDriver(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonTypeDriver(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrPersonTypeDriver(siteRequest_, SearchBasis.staticSolrPersonTypeDriver(siteRequest_, SearchBasis.staticSetPersonTypeDriver(siteRequest_, o)));
	}

	public Boolean solrPersonTypeDriver() {
		return SearchBasis.staticSolrPersonTypeDriver(siteRequest_, personTypeDriver);
	}

	public String strPersonTypeDriver() {
		return personTypeDriver == null ? "" : personTypeDriver.toString();
	}

	public String jsonPersonTypeDriver() {
		return personTypeDriver == null ? "" : personTypeDriver.toString();
	}

	public String nomAffichagePersonTypeDriver() {
		return "person was driver";
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personTypePassenger">Find the entity personTypePassenger in Solr</a>
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
		this.personTypePassenger = SearchBasis.staticSetPersonTypePassenger(siteRequest_, o);
		this.personTypePassengerWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetPersonTypePassenger(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SearchBasis personTypePassengerInit() {
		if(!personTypePassengerWrap.alreadyInitialized) {
			_personTypePassenger(personTypePassengerWrap);
			if(personTypePassenger == null)
				setPersonTypePassenger(personTypePassengerWrap.o);
		}
		personTypePassengerWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Boolean staticSolrPersonTypePassenger(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrPersonTypePassenger(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonTypePassenger(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrPersonTypePassenger(siteRequest_, SearchBasis.staticSolrPersonTypePassenger(siteRequest_, SearchBasis.staticSetPersonTypePassenger(siteRequest_, o)));
	}

	public Boolean solrPersonTypePassenger() {
		return SearchBasis.staticSolrPersonTypePassenger(siteRequest_, personTypePassenger);
	}

	public String strPersonTypePassenger() {
		return personTypePassenger == null ? "" : personTypePassenger.toString();
	}

	public String jsonPersonTypePassenger() {
		return personTypePassenger == null ? "" : personTypePassenger.toString();
	}

	public String nomAffichagePersonTypePassenger() {
		return "person was passenger";
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personGenderId">Find the entity personGenderId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personGenderId(Wrap<String> w);

	public String getPersonGenderId() {
		return personGenderId;
	}
	public void setPersonGenderId(String o) {
		this.personGenderId = SearchBasis.staticSetPersonGenderId(siteRequest_, o);
		this.personGenderIdWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonGenderId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SearchBasis personGenderIdInit() {
		if(!personGenderIdWrap.alreadyInitialized) {
			_personGenderId(personGenderIdWrap);
			if(personGenderId == null)
				setPersonGenderId(personGenderIdWrap.o);
		}
		personGenderIdWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static String staticSolrPersonGenderId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonGenderId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonGenderId(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrPersonGenderId(siteRequest_, SearchBasis.staticSolrPersonGenderId(siteRequest_, SearchBasis.staticSetPersonGenderId(siteRequest_, o)));
	}

	public String solrPersonGenderId() {
		return SearchBasis.staticSolrPersonGenderId(siteRequest_, personGenderId);
	}

	public String strPersonGenderId() {
		return personGenderId == null ? "" : personGenderId;
	}

	public String jsonPersonGenderId() {
		return personGenderId == null ? "" : personGenderId;
	}

	public String nomAffichagePersonGenderId() {
		return "person gender ID";
	}

	public String htmTooltipPersonGenderId() {
		return null;
	}

	public String htmPersonGenderId() {
		return personGenderId == null ? "" : StringEscapeUtils.escapeHtml4(strPersonGenderId());
	}

	public void inputPersonGenderId(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "person gender ID")
				.a("id", classApiMethodMethod, "_personGenderId");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setPersonGenderId classSearchBasis inputSearchBasis", pk, "PersonGenderId w3-input w3-border ");
					a("name", "setPersonGenderId");
				} else {
					a("class", "valuePersonGenderId w3-input w3-border classSearchBasis inputSearchBasis", pk, "PersonGenderId w3-input w3-border ");
					a("name", "personGenderId");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setPersonGenderId', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_personGenderId')); }, function() { addError($('#", classApiMethodMethod, "_personGenderId')); }); ");
				}
				a("value", strPersonGenderId())
			.fg();

		} else {
			e("span").a("class", "varSearchBasis", pk, "PersonGenderId ").f().sx(htmPersonGenderId()).g("span");
		}
	}

	public void htmPersonGenderId(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisPersonGenderId").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_personGenderId").a("class", "").f().sx("person gender ID").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPersonGenderId(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_personGenderId')); $('#", classApiMethodMethod, "_personGenderId').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SearchBasisForm :input[name=pk]').val() }], 'setPersonGenderId', null, function() { addGlow($('#", classApiMethodMethod, "_personGenderId')); }, function() { addError($('#", classApiMethodMethod, "_personGenderId')); }); ")
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personGenderTitle">Find the entity personGenderTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personGenderTitle(Wrap<String> w);

	public String getPersonGenderTitle() {
		return personGenderTitle;
	}
	public void setPersonGenderTitle(String o) {
		this.personGenderTitle = SearchBasis.staticSetPersonGenderTitle(siteRequest_, o);
		this.personGenderTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonGenderTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SearchBasis personGenderTitleInit() {
		if(!personGenderTitleWrap.alreadyInitialized) {
			_personGenderTitle(personGenderTitleWrap);
			if(personGenderTitle == null)
				setPersonGenderTitle(personGenderTitleWrap.o);
		}
		personGenderTitleWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static String staticSolrPersonGenderTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonGenderTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonGenderTitle(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrPersonGenderTitle(siteRequest_, SearchBasis.staticSolrPersonGenderTitle(siteRequest_, SearchBasis.staticSetPersonGenderTitle(siteRequest_, o)));
	}

	public String solrPersonGenderTitle() {
		return SearchBasis.staticSolrPersonGenderTitle(siteRequest_, personGenderTitle);
	}

	public String strPersonGenderTitle() {
		return personGenderTitle == null ? "" : personGenderTitle;
	}

	public String jsonPersonGenderTitle() {
		return personGenderTitle == null ? "" : personGenderTitle;
	}

	public String nomAffichagePersonGenderTitle() {
		return "person gender title";
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personGenderFemale">Find the entity personGenderFemale in Solr</a>
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
		this.personGenderFemale = SearchBasis.staticSetPersonGenderFemale(siteRequest_, o);
		this.personGenderFemaleWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetPersonGenderFemale(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SearchBasis personGenderFemaleInit() {
		if(!personGenderFemaleWrap.alreadyInitialized) {
			_personGenderFemale(personGenderFemaleWrap);
			if(personGenderFemale == null)
				setPersonGenderFemale(personGenderFemaleWrap.o);
		}
		personGenderFemaleWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Boolean staticSolrPersonGenderFemale(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrPersonGenderFemale(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonGenderFemale(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrPersonGenderFemale(siteRequest_, SearchBasis.staticSolrPersonGenderFemale(siteRequest_, SearchBasis.staticSetPersonGenderFemale(siteRequest_, o)));
	}

	public Boolean solrPersonGenderFemale() {
		return SearchBasis.staticSolrPersonGenderFemale(siteRequest_, personGenderFemale);
	}

	public String strPersonGenderFemale() {
		return personGenderFemale == null ? "" : personGenderFemale.toString();
	}

	public String jsonPersonGenderFemale() {
		return personGenderFemale == null ? "" : personGenderFemale.toString();
	}

	public String nomAffichagePersonGenderFemale() {
		return "person was female";
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personGenderMale">Find the entity personGenderMale in Solr</a>
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
		this.personGenderMale = SearchBasis.staticSetPersonGenderMale(siteRequest_, o);
		this.personGenderMaleWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetPersonGenderMale(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SearchBasis personGenderMaleInit() {
		if(!personGenderMaleWrap.alreadyInitialized) {
			_personGenderMale(personGenderMaleWrap);
			if(personGenderMale == null)
				setPersonGenderMale(personGenderMaleWrap.o);
		}
		personGenderMaleWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Boolean staticSolrPersonGenderMale(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrPersonGenderMale(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonGenderMale(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrPersonGenderMale(siteRequest_, SearchBasis.staticSolrPersonGenderMale(siteRequest_, SearchBasis.staticSetPersonGenderMale(siteRequest_, o)));
	}

	public Boolean solrPersonGenderMale() {
		return SearchBasis.staticSolrPersonGenderMale(siteRequest_, personGenderMale);
	}

	public String strPersonGenderMale() {
		return personGenderMale == null ? "" : personGenderMale.toString();
	}

	public String jsonPersonGenderMale() {
		return personGenderMale == null ? "" : personGenderMale.toString();
	}

	public String nomAffichagePersonGenderMale() {
		return "person was male";
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personEthnicityId">Find the entity personEthnicityId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personEthnicityId(Wrap<String> w);

	public String getPersonEthnicityId() {
		return personEthnicityId;
	}
	public void setPersonEthnicityId(String o) {
		this.personEthnicityId = SearchBasis.staticSetPersonEthnicityId(siteRequest_, o);
		this.personEthnicityIdWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonEthnicityId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SearchBasis personEthnicityIdInit() {
		if(!personEthnicityIdWrap.alreadyInitialized) {
			_personEthnicityId(personEthnicityIdWrap);
			if(personEthnicityId == null)
				setPersonEthnicityId(personEthnicityIdWrap.o);
		}
		personEthnicityIdWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static String staticSolrPersonEthnicityId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonEthnicityId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonEthnicityId(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrPersonEthnicityId(siteRequest_, SearchBasis.staticSolrPersonEthnicityId(siteRequest_, SearchBasis.staticSetPersonEthnicityId(siteRequest_, o)));
	}

	public String solrPersonEthnicityId() {
		return SearchBasis.staticSolrPersonEthnicityId(siteRequest_, personEthnicityId);
	}

	public String strPersonEthnicityId() {
		return personEthnicityId == null ? "" : personEthnicityId;
	}

	public String jsonPersonEthnicityId() {
		return personEthnicityId == null ? "" : personEthnicityId;
	}

	public String nomAffichagePersonEthnicityId() {
		return "person ethnicity ID";
	}

	public String htmTooltipPersonEthnicityId() {
		return null;
	}

	public String htmPersonEthnicityId() {
		return personEthnicityId == null ? "" : StringEscapeUtils.escapeHtml4(strPersonEthnicityId());
	}

	public void inputPersonEthnicityId(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "person ethnicity ID")
				.a("id", classApiMethodMethod, "_personEthnicityId");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setPersonEthnicityId classSearchBasis inputSearchBasis", pk, "PersonEthnicityId w3-input w3-border ");
					a("name", "setPersonEthnicityId");
				} else {
					a("class", "valuePersonEthnicityId w3-input w3-border classSearchBasis inputSearchBasis", pk, "PersonEthnicityId w3-input w3-border ");
					a("name", "personEthnicityId");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setPersonEthnicityId', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_personEthnicityId')); }, function() { addError($('#", classApiMethodMethod, "_personEthnicityId')); }); ");
				}
				a("value", strPersonEthnicityId())
			.fg();

		} else {
			e("span").a("class", "varSearchBasis", pk, "PersonEthnicityId ").f().sx(htmPersonEthnicityId()).g("span");
		}
	}

	public void htmPersonEthnicityId(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisPersonEthnicityId").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_personEthnicityId").a("class", "").f().sx("person ethnicity ID").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPersonEthnicityId(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_personEthnicityId')); $('#", classApiMethodMethod, "_personEthnicityId').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SearchBasisForm :input[name=pk]').val() }], 'setPersonEthnicityId', null, function() { addGlow($('#", classApiMethodMethod, "_personEthnicityId')); }, function() { addError($('#", classApiMethodMethod, "_personEthnicityId')); }); ")
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personEthnicityTitle">Find the entity personEthnicityTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personEthnicityTitle(Wrap<String> w);

	public String getPersonEthnicityTitle() {
		return personEthnicityTitle;
	}
	public void setPersonEthnicityTitle(String o) {
		this.personEthnicityTitle = SearchBasis.staticSetPersonEthnicityTitle(siteRequest_, o);
		this.personEthnicityTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonEthnicityTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SearchBasis personEthnicityTitleInit() {
		if(!personEthnicityTitleWrap.alreadyInitialized) {
			_personEthnicityTitle(personEthnicityTitleWrap);
			if(personEthnicityTitle == null)
				setPersonEthnicityTitle(personEthnicityTitleWrap.o);
		}
		personEthnicityTitleWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static String staticSolrPersonEthnicityTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonEthnicityTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonEthnicityTitle(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrPersonEthnicityTitle(siteRequest_, SearchBasis.staticSolrPersonEthnicityTitle(siteRequest_, SearchBasis.staticSetPersonEthnicityTitle(siteRequest_, o)));
	}

	public String solrPersonEthnicityTitle() {
		return SearchBasis.staticSolrPersonEthnicityTitle(siteRequest_, personEthnicityTitle);
	}

	public String strPersonEthnicityTitle() {
		return personEthnicityTitle == null ? "" : personEthnicityTitle;
	}

	public String jsonPersonEthnicityTitle() {
		return personEthnicityTitle == null ? "" : personEthnicityTitle;
	}

	public String nomAffichagePersonEthnicityTitle() {
		return "person ethnicity title";
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personRaceId">Find the entity personRaceId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personRaceId(Wrap<String> w);

	public String getPersonRaceId() {
		return personRaceId;
	}
	public void setPersonRaceId(String o) {
		this.personRaceId = SearchBasis.staticSetPersonRaceId(siteRequest_, o);
		this.personRaceIdWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonRaceId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SearchBasis personRaceIdInit() {
		if(!personRaceIdWrap.alreadyInitialized) {
			_personRaceId(personRaceIdWrap);
			if(personRaceId == null)
				setPersonRaceId(personRaceIdWrap.o);
		}
		personRaceIdWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static String staticSolrPersonRaceId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonRaceId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonRaceId(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrPersonRaceId(siteRequest_, SearchBasis.staticSolrPersonRaceId(siteRequest_, SearchBasis.staticSetPersonRaceId(siteRequest_, o)));
	}

	public String solrPersonRaceId() {
		return SearchBasis.staticSolrPersonRaceId(siteRequest_, personRaceId);
	}

	public String strPersonRaceId() {
		return personRaceId == null ? "" : personRaceId;
	}

	public String jsonPersonRaceId() {
		return personRaceId == null ? "" : personRaceId;
	}

	public String nomAffichagePersonRaceId() {
		return "person race ID";
	}

	public String htmTooltipPersonRaceId() {
		return null;
	}

	public String htmPersonRaceId() {
		return personRaceId == null ? "" : StringEscapeUtils.escapeHtml4(strPersonRaceId());
	}

	public void inputPersonRaceId(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "person race ID")
				.a("id", classApiMethodMethod, "_personRaceId");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setPersonRaceId classSearchBasis inputSearchBasis", pk, "PersonRaceId w3-input w3-border ");
					a("name", "setPersonRaceId");
				} else {
					a("class", "valuePersonRaceId w3-input w3-border classSearchBasis inputSearchBasis", pk, "PersonRaceId w3-input w3-border ");
					a("name", "personRaceId");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setPersonRaceId', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_personRaceId')); }, function() { addError($('#", classApiMethodMethod, "_personRaceId')); }); ");
				}
				a("value", strPersonRaceId())
			.fg();

		} else {
			e("span").a("class", "varSearchBasis", pk, "PersonRaceId ").f().sx(htmPersonRaceId()).g("span");
		}
	}

	public void htmPersonRaceId(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisPersonRaceId").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_personRaceId").a("class", "").f().sx("person race ID").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPersonRaceId(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_personRaceId')); $('#", classApiMethodMethod, "_personRaceId').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SearchBasisForm :input[name=pk]').val() }], 'setPersonRaceId', null, function() { addGlow($('#", classApiMethodMethod, "_personRaceId')); }, function() { addError($('#", classApiMethodMethod, "_personRaceId')); }); ")
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personRaceTitle">Find the entity personRaceTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personRaceTitle(Wrap<String> w);

	public String getPersonRaceTitle() {
		return personRaceTitle;
	}
	public void setPersonRaceTitle(String o) {
		this.personRaceTitle = SearchBasis.staticSetPersonRaceTitle(siteRequest_, o);
		this.personRaceTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetPersonRaceTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SearchBasis personRaceTitleInit() {
		if(!personRaceTitleWrap.alreadyInitialized) {
			_personRaceTitle(personRaceTitleWrap);
			if(personRaceTitle == null)
				setPersonRaceTitle(personRaceTitleWrap.o);
		}
		personRaceTitleWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static String staticSolrPersonRaceTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPersonRaceTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonRaceTitle(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrPersonRaceTitle(siteRequest_, SearchBasis.staticSolrPersonRaceTitle(siteRequest_, SearchBasis.staticSetPersonRaceTitle(siteRequest_, o)));
	}

	public String solrPersonRaceTitle() {
		return SearchBasis.staticSolrPersonRaceTitle(siteRequest_, personRaceTitle);
	}

	public String strPersonRaceTitle() {
		return personRaceTitle == null ? "" : personRaceTitle;
	}

	public String jsonPersonRaceTitle() {
		return personRaceTitle == null ? "" : personRaceTitle;
	}

	public String nomAffichagePersonRaceTitle() {
		return "person race title";
	}

	public String htmTooltipPersonRaceTitle() {
		return null;
	}

	public String htmPersonRaceTitle() {
		return personRaceTitle == null ? "" : StringEscapeUtils.escapeHtml4(strPersonRaceTitle());
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:trafficStopKey">Find the entity trafficStopKey in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _trafficStopKey(Wrap<Long> w);

	public Long getTrafficStopKey() {
		return trafficStopKey;
	}

	public void setTrafficStopKey(Long trafficStopKey) {
		this.trafficStopKey = trafficStopKey;
		this.trafficStopKeyWrap.alreadyInitialized = true;
	}
	public void setTrafficStopKey(String o) {
		this.trafficStopKey = SearchBasis.staticSetTrafficStopKey(siteRequest_, o);
		this.trafficStopKeyWrap.alreadyInitialized = true;
	}
	public static Long staticSetTrafficStopKey(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected SearchBasis trafficStopKeyInit() {
		if(!trafficStopKeyWrap.alreadyInitialized) {
			_trafficStopKey(trafficStopKeyWrap);
			if(trafficStopKey == null)
				setTrafficStopKey(trafficStopKeyWrap.o);
		}
		trafficStopKeyWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Long staticSolrTrafficStopKey(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrTrafficStopKey(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqTrafficStopKey(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrTrafficStopKey(siteRequest_, SearchBasis.staticSolrTrafficStopKey(siteRequest_, SearchBasis.staticSetTrafficStopKey(siteRequest_, o)));
	}

	public Long solrTrafficStopKey() {
		return SearchBasis.staticSolrTrafficStopKey(siteRequest_, trafficStopKey);
	}

	public String strTrafficStopKey() {
		return trafficStopKey == null ? "" : trafficStopKey.toString();
	}

	public String jsonTrafficStopKey() {
		return trafficStopKey == null ? "" : trafficStopKey.toString();
	}

	public String nomAffichageTrafficStopKey() {
		return "traffic stop key";
	}

	public String htmTooltipTrafficStopKey() {
		return null;
	}

	public String htmTrafficStopKey() {
		return trafficStopKey == null ? "" : StringEscapeUtils.escapeHtml4(strTrafficStopKey());
	}

	///////////////////
	// searchTypeNum //
	///////////////////

	/**	 The entity searchTypeNum
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer searchTypeNum;
	@JsonIgnore
	public Wrap<Integer> searchTypeNumWrap = new Wrap<Integer>().p(this).c(Integer.class).var("searchTypeNum").o(searchTypeNum);

	/**	<br/> The entity searchTypeNum
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchTypeNum">Find the entity searchTypeNum in Solr</a>
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
	public void setSearchTypeNum(String o) {
		this.searchTypeNum = SearchBasis.staticSetSearchTypeNum(siteRequest_, o);
		this.searchTypeNumWrap.alreadyInitialized = true;
	}
	public static Integer staticSetSearchTypeNum(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected SearchBasis searchTypeNumInit() {
		if(!searchTypeNumWrap.alreadyInitialized) {
			_searchTypeNum(searchTypeNumWrap);
			if(searchTypeNum == null)
				setSearchTypeNum(searchTypeNumWrap.o);
		}
		searchTypeNumWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Integer staticSolrSearchTypeNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrSearchTypeNum(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchTypeNum(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrSearchTypeNum(siteRequest_, SearchBasis.staticSolrSearchTypeNum(siteRequest_, SearchBasis.staticSetSearchTypeNum(siteRequest_, o)));
	}

	public Integer solrSearchTypeNum() {
		return SearchBasis.staticSolrSearchTypeNum(siteRequest_, searchTypeNum);
	}

	public String strSearchTypeNum() {
		return searchTypeNum == null ? "" : searchTypeNum.toString();
	}

	public String jsonSearchTypeNum() {
		return searchTypeNum == null ? "" : searchTypeNum.toString();
	}

	public String nomAffichageSearchTypeNum() {
		return "search type number";
	}

	public String htmTooltipSearchTypeNum() {
		return null;
	}

	public String htmSearchTypeNum() {
		return searchTypeNum == null ? "" : StringEscapeUtils.escapeHtml4(strSearchTypeNum());
	}

	public void inputSearchTypeNum(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "search type number")
				.a("id", classApiMethodMethod, "_searchTypeNum");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSearchTypeNum classSearchBasis inputSearchBasis", pk, "SearchTypeNum w3-input w3-border ");
					a("name", "setSearchTypeNum");
				} else {
					a("class", "valueSearchTypeNum w3-input w3-border classSearchBasis inputSearchBasis", pk, "SearchTypeNum w3-input w3-border ");
					a("name", "searchTypeNum");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSearchTypeNum', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_searchTypeNum')); }, function() { addError($('#", classApiMethodMethod, "_searchTypeNum')); }); ");
				}
				a("value", strSearchTypeNum())
			.fg();

		} else {
			e("span").a("class", "varSearchBasis", pk, "SearchTypeNum ").f().sx(htmSearchTypeNum()).g("span");
		}
	}

	public void htmSearchTypeNum(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisSearchTypeNum").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_searchTypeNum").a("class", "").f().sx("search type number").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSearchTypeNum(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_searchTypeNum')); $('#", classApiMethodMethod, "_searchTypeNum').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SearchBasisForm :input[name=pk]').val() }], 'setSearchTypeNum', null, function() { addGlow($('#", classApiMethodMethod, "_searchTypeNum')); }, function() { addError($('#", classApiMethodMethod, "_searchTypeNum')); }); ")
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
	// searchTypeTitle //
	/////////////////////

	/**	 The entity searchTypeTitle
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String searchTypeTitle;
	@JsonIgnore
	public Wrap<String> searchTypeTitleWrap = new Wrap<String>().p(this).c(String.class).var("searchTypeTitle").o(searchTypeTitle);

	/**	<br/> The entity searchTypeTitle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchTypeTitle">Find the entity searchTypeTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _searchTypeTitle(Wrap<String> w);

	public String getSearchTypeTitle() {
		return searchTypeTitle;
	}
	public void setSearchTypeTitle(String o) {
		this.searchTypeTitle = SearchBasis.staticSetSearchTypeTitle(siteRequest_, o);
		this.searchTypeTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetSearchTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SearchBasis searchTypeTitleInit() {
		if(!searchTypeTitleWrap.alreadyInitialized) {
			_searchTypeTitle(searchTypeTitleWrap);
			if(searchTypeTitle == null)
				setSearchTypeTitle(searchTypeTitleWrap.o);
		}
		searchTypeTitleWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static String staticSolrSearchTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrSearchTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchTypeTitle(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrSearchTypeTitle(siteRequest_, SearchBasis.staticSolrSearchTypeTitle(siteRequest_, SearchBasis.staticSetSearchTypeTitle(siteRequest_, o)));
	}

	public String solrSearchTypeTitle() {
		return SearchBasis.staticSolrSearchTypeTitle(siteRequest_, searchTypeTitle);
	}

	public String strSearchTypeTitle() {
		return searchTypeTitle == null ? "" : searchTypeTitle;
	}

	public String jsonSearchTypeTitle() {
		return searchTypeTitle == null ? "" : searchTypeTitle;
	}

	public String nomAffichageSearchTypeTitle() {
		return "search type title";
	}

	public String htmTooltipSearchTypeTitle() {
		return null;
	}

	public String htmSearchTypeTitle() {
		return searchTypeTitle == null ? "" : StringEscapeUtils.escapeHtml4(strSearchTypeTitle());
	}

	///////////////////
	// searchVehicle //
	///////////////////

	/**	 The entity searchVehicle
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean searchVehicle;
	@JsonIgnore
	public Wrap<Boolean> searchVehicleWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("searchVehicle").o(searchVehicle);

	/**	<br/> The entity searchVehicle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchVehicle">Find the entity searchVehicle in Solr</a>
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
	public void setSearchVehicle(String o) {
		this.searchVehicle = SearchBasis.staticSetSearchVehicle(siteRequest_, o);
		this.searchVehicleWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSearchVehicle(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SearchBasis searchVehicleInit() {
		if(!searchVehicleWrap.alreadyInitialized) {
			_searchVehicle(searchVehicleWrap);
			if(searchVehicle == null)
				setSearchVehicle(searchVehicleWrap.o);
		}
		searchVehicleWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Boolean staticSolrSearchVehicle(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrSearchVehicle(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchVehicle(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrSearchVehicle(siteRequest_, SearchBasis.staticSolrSearchVehicle(siteRequest_, SearchBasis.staticSetSearchVehicle(siteRequest_, o)));
	}

	public Boolean solrSearchVehicle() {
		return SearchBasis.staticSolrSearchVehicle(siteRequest_, searchVehicle);
	}

	public String strSearchVehicle() {
		return searchVehicle == null ? "" : searchVehicle.toString();
	}

	public String jsonSearchVehicle() {
		return searchVehicle == null ? "" : searchVehicle.toString();
	}

	public String nomAffichageSearchVehicle() {
		return "vehicle search";
	}

	public String htmTooltipSearchVehicle() {
		return null;
	}

	public String htmSearchVehicle() {
		return searchVehicle == null ? "" : StringEscapeUtils.escapeHtml4(strSearchVehicle());
	}

	//////////////////
	// searchDriver //
	//////////////////

	/**	 The entity searchDriver
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean searchDriver;
	@JsonIgnore
	public Wrap<Boolean> searchDriverWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("searchDriver").o(searchDriver);

	/**	<br/> The entity searchDriver
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchDriver">Find the entity searchDriver in Solr</a>
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
	public void setSearchDriver(String o) {
		this.searchDriver = SearchBasis.staticSetSearchDriver(siteRequest_, o);
		this.searchDriverWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSearchDriver(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SearchBasis searchDriverInit() {
		if(!searchDriverWrap.alreadyInitialized) {
			_searchDriver(searchDriverWrap);
			if(searchDriver == null)
				setSearchDriver(searchDriverWrap.o);
		}
		searchDriverWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Boolean staticSolrSearchDriver(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrSearchDriver(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchDriver(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrSearchDriver(siteRequest_, SearchBasis.staticSolrSearchDriver(siteRequest_, SearchBasis.staticSetSearchDriver(siteRequest_, o)));
	}

	public Boolean solrSearchDriver() {
		return SearchBasis.staticSolrSearchDriver(siteRequest_, searchDriver);
	}

	public String strSearchDriver() {
		return searchDriver == null ? "" : searchDriver.toString();
	}

	public String jsonSearchDriver() {
		return searchDriver == null ? "" : searchDriver.toString();
	}

	public String nomAffichageSearchDriver() {
		return "driver search";
	}

	public String htmTooltipSearchDriver() {
		return null;
	}

	public String htmSearchDriver() {
		return searchDriver == null ? "" : StringEscapeUtils.escapeHtml4(strSearchDriver());
	}

	/////////////////////
	// searchPassenger //
	/////////////////////

	/**	 The entity searchPassenger
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean searchPassenger;
	@JsonIgnore
	public Wrap<Boolean> searchPassengerWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("searchPassenger").o(searchPassenger);

	/**	<br/> The entity searchPassenger
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchPassenger">Find the entity searchPassenger in Solr</a>
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
	public void setSearchPassenger(String o) {
		this.searchPassenger = SearchBasis.staticSetSearchPassenger(siteRequest_, o);
		this.searchPassengerWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSearchPassenger(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SearchBasis searchPassengerInit() {
		if(!searchPassengerWrap.alreadyInitialized) {
			_searchPassenger(searchPassengerWrap);
			if(searchPassenger == null)
				setSearchPassenger(searchPassengerWrap.o);
		}
		searchPassengerWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Boolean staticSolrSearchPassenger(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrSearchPassenger(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchPassenger(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrSearchPassenger(siteRequest_, SearchBasis.staticSolrSearchPassenger(siteRequest_, SearchBasis.staticSetSearchPassenger(siteRequest_, o)));
	}

	public Boolean solrSearchPassenger() {
		return SearchBasis.staticSolrSearchPassenger(siteRequest_, searchPassenger);
	}

	public String strSearchPassenger() {
		return searchPassenger == null ? "" : searchPassenger.toString();
	}

	public String jsonSearchPassenger() {
		return searchPassenger == null ? "" : searchPassenger.toString();
	}

	public String nomAffichageSearchPassenger() {
		return "passenger search";
	}

	public String htmTooltipSearchPassenger() {
		return null;
	}

	public String htmSearchPassenger() {
		return searchPassenger == null ? "" : StringEscapeUtils.escapeHtml4(strSearchPassenger());
	}

	////////////////////
	// searchProperty //
	////////////////////

	/**	 The entity searchProperty
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean searchProperty;
	@JsonIgnore
	public Wrap<Boolean> searchPropertyWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("searchProperty").o(searchProperty);

	/**	<br/> The entity searchProperty
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchProperty">Find the entity searchProperty in Solr</a>
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
	public void setSearchProperty(String o) {
		this.searchProperty = SearchBasis.staticSetSearchProperty(siteRequest_, o);
		this.searchPropertyWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSearchProperty(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SearchBasis searchPropertyInit() {
		if(!searchPropertyWrap.alreadyInitialized) {
			_searchProperty(searchPropertyWrap);
			if(searchProperty == null)
				setSearchProperty(searchPropertyWrap.o);
		}
		searchPropertyWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Boolean staticSolrSearchProperty(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrSearchProperty(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchProperty(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrSearchProperty(siteRequest_, SearchBasis.staticSolrSearchProperty(siteRequest_, SearchBasis.staticSetSearchProperty(siteRequest_, o)));
	}

	public Boolean solrSearchProperty() {
		return SearchBasis.staticSolrSearchProperty(siteRequest_, searchProperty);
	}

	public String strSearchProperty() {
		return searchProperty == null ? "" : searchProperty.toString();
	}

	public String jsonSearchProperty() {
		return searchProperty == null ? "" : searchProperty.toString();
	}

	public String nomAffichageSearchProperty() {
		return "property search";
	}

	public String htmTooltipSearchProperty() {
		return null;
	}

	public String htmSearchProperty() {
		return searchProperty == null ? "" : StringEscapeUtils.escapeHtml4(strSearchProperty());
	}

	/////////////////////////
	// searchVehicleSiezed //
	/////////////////////////

	/**	 The entity searchVehicleSiezed
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean searchVehicleSiezed;
	@JsonIgnore
	public Wrap<Boolean> searchVehicleSiezedWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("searchVehicleSiezed").o(searchVehicleSiezed);

	/**	<br/> The entity searchVehicleSiezed
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchVehicleSiezed">Find the entity searchVehicleSiezed in Solr</a>
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
	public void setSearchVehicleSiezed(String o) {
		this.searchVehicleSiezed = SearchBasis.staticSetSearchVehicleSiezed(siteRequest_, o);
		this.searchVehicleSiezedWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSearchVehicleSiezed(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SearchBasis searchVehicleSiezedInit() {
		if(!searchVehicleSiezedWrap.alreadyInitialized) {
			_searchVehicleSiezed(searchVehicleSiezedWrap);
			if(searchVehicleSiezed == null)
				setSearchVehicleSiezed(searchVehicleSiezedWrap.o);
		}
		searchVehicleSiezedWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Boolean staticSolrSearchVehicleSiezed(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrSearchVehicleSiezed(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchVehicleSiezed(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrSearchVehicleSiezed(siteRequest_, SearchBasis.staticSolrSearchVehicleSiezed(siteRequest_, SearchBasis.staticSetSearchVehicleSiezed(siteRequest_, o)));
	}

	public Boolean solrSearchVehicleSiezed() {
		return SearchBasis.staticSolrSearchVehicleSiezed(siteRequest_, searchVehicleSiezed);
	}

	public String strSearchVehicleSiezed() {
		return searchVehicleSiezed == null ? "" : searchVehicleSiezed.toString();
	}

	public String jsonSearchVehicleSiezed() {
		return searchVehicleSiezed == null ? "" : searchVehicleSiezed.toString();
	}

	public String nomAffichageSearchVehicleSiezed() {
		return "vehicle siezed";
	}

	public String htmTooltipSearchVehicleSiezed() {
		return null;
	}

	public String htmSearchVehicleSiezed() {
		return searchVehicleSiezed == null ? "" : StringEscapeUtils.escapeHtml4(strSearchVehicleSiezed());
	}

	//////////////////////////////////
	// searchPersonalPropertySiezed //
	//////////////////////////////////

	/**	 The entity searchPersonalPropertySiezed
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean searchPersonalPropertySiezed;
	@JsonIgnore
	public Wrap<Boolean> searchPersonalPropertySiezedWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("searchPersonalPropertySiezed").o(searchPersonalPropertySiezed);

	/**	<br/> The entity searchPersonalPropertySiezed
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchPersonalPropertySiezed">Find the entity searchPersonalPropertySiezed in Solr</a>
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
	public void setSearchPersonalPropertySiezed(String o) {
		this.searchPersonalPropertySiezed = SearchBasis.staticSetSearchPersonalPropertySiezed(siteRequest_, o);
		this.searchPersonalPropertySiezedWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSearchPersonalPropertySiezed(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SearchBasis searchPersonalPropertySiezedInit() {
		if(!searchPersonalPropertySiezedWrap.alreadyInitialized) {
			_searchPersonalPropertySiezed(searchPersonalPropertySiezedWrap);
			if(searchPersonalPropertySiezed == null)
				setSearchPersonalPropertySiezed(searchPersonalPropertySiezedWrap.o);
		}
		searchPersonalPropertySiezedWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Boolean staticSolrSearchPersonalPropertySiezed(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrSearchPersonalPropertySiezed(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchPersonalPropertySiezed(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrSearchPersonalPropertySiezed(siteRequest_, SearchBasis.staticSolrSearchPersonalPropertySiezed(siteRequest_, SearchBasis.staticSetSearchPersonalPropertySiezed(siteRequest_, o)));
	}

	public Boolean solrSearchPersonalPropertySiezed() {
		return SearchBasis.staticSolrSearchPersonalPropertySiezed(siteRequest_, searchPersonalPropertySiezed);
	}

	public String strSearchPersonalPropertySiezed() {
		return searchPersonalPropertySiezed == null ? "" : searchPersonalPropertySiezed.toString();
	}

	public String jsonSearchPersonalPropertySiezed() {
		return searchPersonalPropertySiezed == null ? "" : searchPersonalPropertySiezed.toString();
	}

	public String nomAffichageSearchPersonalPropertySiezed() {
		return "personal property siezed";
	}

	public String htmTooltipSearchPersonalPropertySiezed() {
		return null;
	}

	public String htmSearchPersonalPropertySiezed() {
		return searchPersonalPropertySiezed == null ? "" : StringEscapeUtils.escapeHtml4(strSearchPersonalPropertySiezed());
	}

	///////////////////////////////
	// searchOtherPropertySiezed //
	///////////////////////////////

	/**	 The entity searchOtherPropertySiezed
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean searchOtherPropertySiezed;
	@JsonIgnore
	public Wrap<Boolean> searchOtherPropertySiezedWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("searchOtherPropertySiezed").o(searchOtherPropertySiezed);

	/**	<br/> The entity searchOtherPropertySiezed
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchOtherPropertySiezed">Find the entity searchOtherPropertySiezed in Solr</a>
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
	public void setSearchOtherPropertySiezed(String o) {
		this.searchOtherPropertySiezed = SearchBasis.staticSetSearchOtherPropertySiezed(siteRequest_, o);
		this.searchOtherPropertySiezedWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSearchOtherPropertySiezed(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SearchBasis searchOtherPropertySiezedInit() {
		if(!searchOtherPropertySiezedWrap.alreadyInitialized) {
			_searchOtherPropertySiezed(searchOtherPropertySiezedWrap);
			if(searchOtherPropertySiezed == null)
				setSearchOtherPropertySiezed(searchOtherPropertySiezedWrap.o);
		}
		searchOtherPropertySiezedWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static Boolean staticSolrSearchOtherPropertySiezed(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrSearchOtherPropertySiezed(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchOtherPropertySiezed(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrSearchOtherPropertySiezed(siteRequest_, SearchBasis.staticSolrSearchOtherPropertySiezed(siteRequest_, SearchBasis.staticSetSearchOtherPropertySiezed(siteRequest_, o)));
	}

	public Boolean solrSearchOtherPropertySiezed() {
		return SearchBasis.staticSolrSearchOtherPropertySiezed(siteRequest_, searchOtherPropertySiezed);
	}

	public String strSearchOtherPropertySiezed() {
		return searchOtherPropertySiezed == null ? "" : searchOtherPropertySiezed.toString();
	}

	public String jsonSearchOtherPropertySiezed() {
		return searchOtherPropertySiezed == null ? "" : searchOtherPropertySiezed.toString();
	}

	public String nomAffichageSearchOtherPropertySiezed() {
		return "other property siezed";
	}

	public String htmTooltipSearchOtherPropertySiezed() {
		return null;
	}

	public String htmSearchOtherPropertySiezed() {
		return searchOtherPropertySiezed == null ? "" : StringEscapeUtils.escapeHtml4(strSearchOtherPropertySiezed());
	}

	///////////////////
	// searchBasisId //
	///////////////////

	/**	 The entity searchBasisId
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String searchBasisId;
	@JsonIgnore
	public Wrap<String> searchBasisIdWrap = new Wrap<String>().p(this).c(String.class).var("searchBasisId").o(searchBasisId);

	/**	<br/> The entity searchBasisId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchBasisId">Find the entity searchBasisId in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _searchBasisId(Wrap<String> w);

	public String getSearchBasisId() {
		return searchBasisId;
	}
	public void setSearchBasisId(String o) {
		this.searchBasisId = SearchBasis.staticSetSearchBasisId(siteRequest_, o);
		this.searchBasisIdWrap.alreadyInitialized = true;
	}
	public static String staticSetSearchBasisId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SearchBasis searchBasisIdInit() {
		if(!searchBasisIdWrap.alreadyInitialized) {
			_searchBasisId(searchBasisIdWrap);
			if(searchBasisId == null)
				setSearchBasisId(searchBasisIdWrap.o);
		}
		searchBasisIdWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static String staticSolrSearchBasisId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrSearchBasisId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchBasisId(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrSearchBasisId(siteRequest_, SearchBasis.staticSolrSearchBasisId(siteRequest_, SearchBasis.staticSetSearchBasisId(siteRequest_, o)));
	}

	public String solrSearchBasisId() {
		return SearchBasis.staticSolrSearchBasisId(siteRequest_, searchBasisId);
	}

	public String strSearchBasisId() {
		return searchBasisId == null ? "" : searchBasisId;
	}

	public String jsonSearchBasisId() {
		return searchBasisId == null ? "" : searchBasisId;
	}

	public String nomAffichageSearchBasisId() {
		return "search basis ID";
	}

	public String htmTooltipSearchBasisId() {
		return null;
	}

	public String htmSearchBasisId() {
		return searchBasisId == null ? "" : StringEscapeUtils.escapeHtml4(strSearchBasisId());
	}

	public void inputSearchBasisId(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "search basis ID")
				.a("id", classApiMethodMethod, "_searchBasisId");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSearchBasisId classSearchBasis inputSearchBasis", pk, "SearchBasisId w3-input w3-border ");
					a("name", "setSearchBasisId");
				} else {
					a("class", "valueSearchBasisId w3-input w3-border classSearchBasis inputSearchBasis", pk, "SearchBasisId w3-input w3-border ");
					a("name", "searchBasisId");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSearchBasisId', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_searchBasisId')); }, function() { addError($('#", classApiMethodMethod, "_searchBasisId')); }); ");
				}
				a("value", strSearchBasisId())
			.fg();

		} else {
			e("span").a("class", "varSearchBasis", pk, "SearchBasisId ").f().sx(htmSearchBasisId()).g("span");
		}
	}

	public void htmSearchBasisId(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisSearchBasisId").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_searchBasisId").a("class", "").f().sx("search basis ID").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSearchBasisId(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_searchBasisId')); $('#", classApiMethodMethod, "_searchBasisId').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SearchBasisForm :input[name=pk]').val() }], 'setSearchBasisId', null, function() { addGlow($('#", classApiMethodMethod, "_searchBasisId')); }, function() { addError($('#", classApiMethodMethod, "_searchBasisId')); }); ")
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
	// searchBasisTitle //
	//////////////////////

	/**	 The entity searchBasisTitle
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String searchBasisTitle;
	@JsonIgnore
	public Wrap<String> searchBasisTitleWrap = new Wrap<String>().p(this).c(String.class).var("searchBasisTitle").o(searchBasisTitle);

	/**	<br/> The entity searchBasisTitle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchBasisTitle">Find the entity searchBasisTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _searchBasisTitle(Wrap<String> w);

	public String getSearchBasisTitle() {
		return searchBasisTitle;
	}
	public void setSearchBasisTitle(String o) {
		this.searchBasisTitle = SearchBasis.staticSetSearchBasisTitle(siteRequest_, o);
		this.searchBasisTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetSearchBasisTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SearchBasis searchBasisTitleInit() {
		if(!searchBasisTitleWrap.alreadyInitialized) {
			_searchBasisTitle(searchBasisTitleWrap);
			if(searchBasisTitle == null)
				setSearchBasisTitle(searchBasisTitleWrap.o);
		}
		searchBasisTitleWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static String staticSolrSearchBasisTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrSearchBasisTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchBasisTitle(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrSearchBasisTitle(siteRequest_, SearchBasis.staticSolrSearchBasisTitle(siteRequest_, SearchBasis.staticSetSearchBasisTitle(siteRequest_, o)));
	}

	public String solrSearchBasisTitle() {
		return SearchBasis.staticSolrSearchBasisTitle(siteRequest_, searchBasisTitle);
	}

	public String strSearchBasisTitle() {
		return searchBasisTitle == null ? "" : searchBasisTitle;
	}

	public String jsonSearchBasisTitle() {
		return searchBasisTitle == null ? "" : searchBasisTitle;
	}

	public String nomAffichageSearchBasisTitle() {
		return "search basis title";
	}

	public String htmTooltipSearchBasisTitle() {
		return null;
	}

	public String htmSearchBasisTitle() {
		return searchBasisTitle == null ? "" : StringEscapeUtils.escapeHtml4(strSearchBasisTitle());
	}

	public void inputSearchBasisTitle(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "search basis title")
				.a("id", classApiMethodMethod, "_searchBasisTitle");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSearchBasisTitle classSearchBasis inputSearchBasis", pk, "SearchBasisTitle w3-input w3-border ");
					a("name", "setSearchBasisTitle");
				} else {
					a("class", "valueSearchBasisTitle w3-input w3-border classSearchBasis inputSearchBasis", pk, "SearchBasisTitle w3-input w3-border ");
					a("name", "searchBasisTitle");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSearchBasisTitle', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_searchBasisTitle')); }, function() { addError($('#", classApiMethodMethod, "_searchBasisTitle')); }); ");
				}
				a("value", strSearchBasisTitle())
			.fg();

		} else {
			e("span").a("class", "varSearchBasis", pk, "SearchBasisTitle ").f().sx(htmSearchBasisTitle()).g("span");
		}
	}

	public void htmSearchBasisTitle(String classApiMethodMethod) {
		SearchBasis s = (SearchBasis)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SearchBasisSearchBasisTitle").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-green ").f();
							e("label").a("for", classApiMethodMethod, "_searchBasisTitle").a("class", "").f().sx("search basis title").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSearchBasisTitle(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_searchBasisTitle')); $('#", classApiMethodMethod, "_searchBasisTitle').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SearchBasisForm :input[name=pk]').val() }], 'setSearchBasisTitle', null, function() { addGlow($('#", classApiMethodMethod, "_searchBasisTitle')); }, function() { addError($('#", classApiMethodMethod, "_searchBasisTitle')); }); ")
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

	//////////////////////////
	// searchBasisShortName //
	//////////////////////////

	/**	 The entity searchBasisShortName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String searchBasisShortName;
	@JsonIgnore
	public Wrap<String> searchBasisShortNameWrap = new Wrap<String>().p(this).c(String.class).var("searchBasisShortName").o(searchBasisShortName);

	/**	<br/> The entity searchBasisShortName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchBasisShortName">Find the entity searchBasisShortName in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _searchBasisShortName(Wrap<String> w);

	public String getSearchBasisShortName() {
		return searchBasisShortName;
	}
	public void setSearchBasisShortName(String o) {
		this.searchBasisShortName = SearchBasis.staticSetSearchBasisShortName(siteRequest_, o);
		this.searchBasisShortNameWrap.alreadyInitialized = true;
	}
	public static String staticSetSearchBasisShortName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SearchBasis searchBasisShortNameInit() {
		if(!searchBasisShortNameWrap.alreadyInitialized) {
			_searchBasisShortName(searchBasisShortNameWrap);
			if(searchBasisShortName == null)
				setSearchBasisShortName(searchBasisShortNameWrap.o);
		}
		searchBasisShortNameWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static String staticSolrSearchBasisShortName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrSearchBasisShortName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchBasisShortName(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrSearchBasisShortName(siteRequest_, SearchBasis.staticSolrSearchBasisShortName(siteRequest_, SearchBasis.staticSetSearchBasisShortName(siteRequest_, o)));
	}

	public String solrSearchBasisShortName() {
		return SearchBasis.staticSolrSearchBasisShortName(siteRequest_, searchBasisShortName);
	}

	public String strSearchBasisShortName() {
		return searchBasisShortName == null ? "" : searchBasisShortName;
	}

	public String jsonSearchBasisShortName() {
		return searchBasisShortName == null ? "" : searchBasisShortName;
	}

	public String nomAffichageSearchBasisShortName() {
		return null;
	}

	public String htmTooltipSearchBasisShortName() {
		return null;
	}

	public String htmSearchBasisShortName() {
		return searchBasisShortName == null ? "" : StringEscapeUtils.escapeHtml4(strSearchBasisShortName());
	}

	/////////////////////////////
	// searchBasisCompleteName //
	/////////////////////////////

	/**	 The entity searchBasisCompleteName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String searchBasisCompleteName;
	@JsonIgnore
	public Wrap<String> searchBasisCompleteNameWrap = new Wrap<String>().p(this).c(String.class).var("searchBasisCompleteName").o(searchBasisCompleteName);

	/**	<br/> The entity searchBasisCompleteName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.searchbasis.SearchBasis&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchBasisCompleteName">Find the entity searchBasisCompleteName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _searchBasisCompleteName(Wrap<String> c);

	public String getSearchBasisCompleteName() {
		return searchBasisCompleteName;
	}
	public void setSearchBasisCompleteName(String o) {
		this.searchBasisCompleteName = SearchBasis.staticSetSearchBasisCompleteName(siteRequest_, o);
		this.searchBasisCompleteNameWrap.alreadyInitialized = true;
	}
	public static String staticSetSearchBasisCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SearchBasis searchBasisCompleteNameInit() {
		if(!searchBasisCompleteNameWrap.alreadyInitialized) {
			_searchBasisCompleteName(searchBasisCompleteNameWrap);
			if(searchBasisCompleteName == null)
				setSearchBasisCompleteName(searchBasisCompleteNameWrap.o);
		}
		searchBasisCompleteNameWrap.alreadyInitialized(true);
		return (SearchBasis)this;
	}

	public static String staticSolrSearchBasisCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrSearchBasisCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchBasisCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return SearchBasis.staticSolrStrSearchBasisCompleteName(siteRequest_, SearchBasis.staticSolrSearchBasisCompleteName(siteRequest_, SearchBasis.staticSetSearchBasisCompleteName(siteRequest_, o)));
	}

	public String solrSearchBasisCompleteName() {
		return SearchBasis.staticSolrSearchBasisCompleteName(siteRequest_, searchBasisCompleteName);
	}

	public String strSearchBasisCompleteName() {
		return searchBasisCompleteName == null ? "" : searchBasisCompleteName;
	}

	public String jsonSearchBasisCompleteName() {
		return searchBasisCompleteName == null ? "" : searchBasisCompleteName;
	}

	public String nomAffichageSearchBasisCompleteName() {
		return null;
	}

	public String htmTooltipSearchBasisCompleteName() {
		return null;
	}

	public String htmSearchBasisCompleteName() {
		return searchBasisCompleteName == null ? "" : StringEscapeUtils.escapeHtml4(strSearchBasisCompleteName());
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedSearchBasis = false;

	public SearchBasis initDeepSearchBasis(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedSearchBasis) {
			alreadyInitializedSearchBasis = true;
			initDeepSearchBasis();
		}
		return (SearchBasis)this;
	}

	public void initDeepSearchBasis() {
		initSearchBasis();
		super.initDeepCluster(siteRequest_);
	}

	public void initSearchBasis() {
		searchBasisKeyInit();
		searchKeyInit();
		trafficSearchSearchInit();
		trafficSearch_Init();
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
		trafficStopKeyInit();
		searchTypeNumInit();
		searchTypeTitleInit();
		searchVehicleInit();
		searchDriverInit();
		searchPassengerInit();
		searchPropertyInit();
		searchVehicleSiezedInit();
		searchPersonalPropertySiezedInit();
		searchOtherPropertySiezedInit();
		searchBasisIdInit();
		searchBasisTitleInit();
		searchBasisShortNameInit();
		searchBasisCompleteNameInit();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepSearchBasis(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestSearchBasis(SiteRequestEnUS siteRequest_) {
			super.siteRequestCluster(siteRequest_);
		if(trafficSearchSearch != null)
			trafficSearchSearch.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestSearchBasis(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainSearchBasis(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
		}
		return o;
	}
	public Object obtainSearchBasis(String var) {
		SearchBasis oSearchBasis = (SearchBasis)this;
		switch(var) {
			case "searchBasisKey":
				return oSearchBasis.searchBasisKey;
			case "searchKey":
				return oSearchBasis.searchKey;
			case "trafficSearchSearch":
				return oSearchBasis.trafficSearchSearch;
			case "trafficSearch_":
				return oSearchBasis.trafficSearch_;
			case "stopAgencyTitle":
				return oSearchBasis.stopAgencyTitle;
			case "stopDateTime":
				return oSearchBasis.stopDateTime;
			case "stopPurposeNum":
				return oSearchBasis.stopPurposeNum;
			case "stopPurposeTitle":
				return oSearchBasis.stopPurposeTitle;
			case "stopActionNum":
				return oSearchBasis.stopActionNum;
			case "stopActionTitle":
				return oSearchBasis.stopActionTitle;
			case "stopDriverArrest":
				return oSearchBasis.stopDriverArrest;
			case "stopPassengerArrest":
				return oSearchBasis.stopPassengerArrest;
			case "stopEncounterForce":
				return oSearchBasis.stopEncounterForce;
			case "stopEngageForce":
				return oSearchBasis.stopEngageForce;
			case "stopOfficerInjury":
				return oSearchBasis.stopOfficerInjury;
			case "stopDriverInjury":
				return oSearchBasis.stopDriverInjury;
			case "stopPassengerInjury":
				return oSearchBasis.stopPassengerInjury;
			case "stopOfficerId":
				return oSearchBasis.stopOfficerId;
			case "stopLocationId":
				return oSearchBasis.stopLocationId;
			case "stopCityId":
				return oSearchBasis.stopCityId;
			case "personAge":
				return oSearchBasis.personAge;
			case "personTypeId":
				return oSearchBasis.personTypeId;
			case "personTypeTitle":
				return oSearchBasis.personTypeTitle;
			case "personTypeDriver":
				return oSearchBasis.personTypeDriver;
			case "personTypePassenger":
				return oSearchBasis.personTypePassenger;
			case "personGenderId":
				return oSearchBasis.personGenderId;
			case "personGenderTitle":
				return oSearchBasis.personGenderTitle;
			case "personGenderFemale":
				return oSearchBasis.personGenderFemale;
			case "personGenderMale":
				return oSearchBasis.personGenderMale;
			case "personEthnicityId":
				return oSearchBasis.personEthnicityId;
			case "personEthnicityTitle":
				return oSearchBasis.personEthnicityTitle;
			case "personRaceId":
				return oSearchBasis.personRaceId;
			case "personRaceTitle":
				return oSearchBasis.personRaceTitle;
			case "trafficStopKey":
				return oSearchBasis.trafficStopKey;
			case "searchTypeNum":
				return oSearchBasis.searchTypeNum;
			case "searchTypeTitle":
				return oSearchBasis.searchTypeTitle;
			case "searchVehicle":
				return oSearchBasis.searchVehicle;
			case "searchDriver":
				return oSearchBasis.searchDriver;
			case "searchPassenger":
				return oSearchBasis.searchPassenger;
			case "searchProperty":
				return oSearchBasis.searchProperty;
			case "searchVehicleSiezed":
				return oSearchBasis.searchVehicleSiezed;
			case "searchPersonalPropertySiezed":
				return oSearchBasis.searchPersonalPropertySiezed;
			case "searchOtherPropertySiezed":
				return oSearchBasis.searchOtherPropertySiezed;
			case "searchBasisId":
				return oSearchBasis.searchBasisId;
			case "searchBasisTitle":
				return oSearchBasis.searchBasisTitle;
			case "searchBasisShortName":
				return oSearchBasis.searchBasisShortName;
			case "searchBasisCompleteName":
				return oSearchBasis.searchBasisCompleteName;
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
				o = attributeSearchBasis(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeSearchBasis(String var, Object val) {
		SearchBasis oSearchBasis = (SearchBasis)this;
		switch(var) {
			case "searchKey":
				if(oSearchBasis.getSearchKey() == null)
					oSearchBasis.setSearchKey((Long)val);
				if(!saves.contains(var))
					saves.add(var);
				return val;
			default:
				return super.attributeCluster(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetSearchBasis(entityVar,  siteRequest_, o);
	}
	public static Object staticSetSearchBasis(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "searchBasisKey":
			return SearchBasis.staticSetSearchBasisKey(siteRequest_, o);
		case "searchKey":
			return SearchBasis.staticSetSearchKey(siteRequest_, o);
		case "stopAgencyTitle":
			return SearchBasis.staticSetStopAgencyTitle(siteRequest_, o);
		case "stopDateTime":
			return SearchBasis.staticSetStopDateTime(siteRequest_, o);
		case "stopPurposeNum":
			return SearchBasis.staticSetStopPurposeNum(siteRequest_, o);
		case "stopPurposeTitle":
			return SearchBasis.staticSetStopPurposeTitle(siteRequest_, o);
		case "stopActionNum":
			return SearchBasis.staticSetStopActionNum(siteRequest_, o);
		case "stopActionTitle":
			return SearchBasis.staticSetStopActionTitle(siteRequest_, o);
		case "stopDriverArrest":
			return SearchBasis.staticSetStopDriverArrest(siteRequest_, o);
		case "stopPassengerArrest":
			return SearchBasis.staticSetStopPassengerArrest(siteRequest_, o);
		case "stopEncounterForce":
			return SearchBasis.staticSetStopEncounterForce(siteRequest_, o);
		case "stopEngageForce":
			return SearchBasis.staticSetStopEngageForce(siteRequest_, o);
		case "stopOfficerInjury":
			return SearchBasis.staticSetStopOfficerInjury(siteRequest_, o);
		case "stopDriverInjury":
			return SearchBasis.staticSetStopDriverInjury(siteRequest_, o);
		case "stopPassengerInjury":
			return SearchBasis.staticSetStopPassengerInjury(siteRequest_, o);
		case "stopOfficerId":
			return SearchBasis.staticSetStopOfficerId(siteRequest_, o);
		case "stopLocationId":
			return SearchBasis.staticSetStopLocationId(siteRequest_, o);
		case "stopCityId":
			return SearchBasis.staticSetStopCityId(siteRequest_, o);
		case "personAge":
			return SearchBasis.staticSetPersonAge(siteRequest_, o);
		case "personTypeId":
			return SearchBasis.staticSetPersonTypeId(siteRequest_, o);
		case "personTypeTitle":
			return SearchBasis.staticSetPersonTypeTitle(siteRequest_, o);
		case "personTypeDriver":
			return SearchBasis.staticSetPersonTypeDriver(siteRequest_, o);
		case "personTypePassenger":
			return SearchBasis.staticSetPersonTypePassenger(siteRequest_, o);
		case "personGenderId":
			return SearchBasis.staticSetPersonGenderId(siteRequest_, o);
		case "personGenderTitle":
			return SearchBasis.staticSetPersonGenderTitle(siteRequest_, o);
		case "personGenderFemale":
			return SearchBasis.staticSetPersonGenderFemale(siteRequest_, o);
		case "personGenderMale":
			return SearchBasis.staticSetPersonGenderMale(siteRequest_, o);
		case "personEthnicityId":
			return SearchBasis.staticSetPersonEthnicityId(siteRequest_, o);
		case "personEthnicityTitle":
			return SearchBasis.staticSetPersonEthnicityTitle(siteRequest_, o);
		case "personRaceId":
			return SearchBasis.staticSetPersonRaceId(siteRequest_, o);
		case "personRaceTitle":
			return SearchBasis.staticSetPersonRaceTitle(siteRequest_, o);
		case "trafficStopKey":
			return SearchBasis.staticSetTrafficStopKey(siteRequest_, o);
		case "searchTypeNum":
			return SearchBasis.staticSetSearchTypeNum(siteRequest_, o);
		case "searchTypeTitle":
			return SearchBasis.staticSetSearchTypeTitle(siteRequest_, o);
		case "searchVehicle":
			return SearchBasis.staticSetSearchVehicle(siteRequest_, o);
		case "searchDriver":
			return SearchBasis.staticSetSearchDriver(siteRequest_, o);
		case "searchPassenger":
			return SearchBasis.staticSetSearchPassenger(siteRequest_, o);
		case "searchProperty":
			return SearchBasis.staticSetSearchProperty(siteRequest_, o);
		case "searchVehicleSiezed":
			return SearchBasis.staticSetSearchVehicleSiezed(siteRequest_, o);
		case "searchPersonalPropertySiezed":
			return SearchBasis.staticSetSearchPersonalPropertySiezed(siteRequest_, o);
		case "searchOtherPropertySiezed":
			return SearchBasis.staticSetSearchOtherPropertySiezed(siteRequest_, o);
		case "searchBasisId":
			return SearchBasis.staticSetSearchBasisId(siteRequest_, o);
		case "searchBasisTitle":
			return SearchBasis.staticSetSearchBasisTitle(siteRequest_, o);
		case "searchBasisShortName":
			return SearchBasis.staticSetSearchBasisShortName(siteRequest_, o);
		case "searchBasisCompleteName":
			return SearchBasis.staticSetSearchBasisCompleteName(siteRequest_, o);
			default:
				return Cluster.staticSetCluster(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrSearchBasis(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrSearchBasis(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "searchBasisKey":
			return SearchBasis.staticSolrSearchBasisKey(siteRequest_, (Long)o);
		case "searchKey":
			return SearchBasis.staticSolrSearchKey(siteRequest_, (Long)o);
		case "stopAgencyTitle":
			return SearchBasis.staticSolrStopAgencyTitle(siteRequest_, (String)o);
		case "stopDateTime":
			return SearchBasis.staticSolrStopDateTime(siteRequest_, (ZonedDateTime)o);
		case "stopPurposeNum":
			return SearchBasis.staticSolrStopPurposeNum(siteRequest_, (Integer)o);
		case "stopPurposeTitle":
			return SearchBasis.staticSolrStopPurposeTitle(siteRequest_, (String)o);
		case "stopActionNum":
			return SearchBasis.staticSolrStopActionNum(siteRequest_, (Integer)o);
		case "stopActionTitle":
			return SearchBasis.staticSolrStopActionTitle(siteRequest_, (String)o);
		case "stopDriverArrest":
			return SearchBasis.staticSolrStopDriverArrest(siteRequest_, (Boolean)o);
		case "stopPassengerArrest":
			return SearchBasis.staticSolrStopPassengerArrest(siteRequest_, (Boolean)o);
		case "stopEncounterForce":
			return SearchBasis.staticSolrStopEncounterForce(siteRequest_, (Boolean)o);
		case "stopEngageForce":
			return SearchBasis.staticSolrStopEngageForce(siteRequest_, (Boolean)o);
		case "stopOfficerInjury":
			return SearchBasis.staticSolrStopOfficerInjury(siteRequest_, (Boolean)o);
		case "stopDriverInjury":
			return SearchBasis.staticSolrStopDriverInjury(siteRequest_, (Boolean)o);
		case "stopPassengerInjury":
			return SearchBasis.staticSolrStopPassengerInjury(siteRequest_, (Boolean)o);
		case "stopOfficerId":
			return SearchBasis.staticSolrStopOfficerId(siteRequest_, (String)o);
		case "stopLocationId":
			return SearchBasis.staticSolrStopLocationId(siteRequest_, (String)o);
		case "stopCityId":
			return SearchBasis.staticSolrStopCityId(siteRequest_, (String)o);
		case "personAge":
			return SearchBasis.staticSolrPersonAge(siteRequest_, (Integer)o);
		case "personTypeId":
			return SearchBasis.staticSolrPersonTypeId(siteRequest_, (String)o);
		case "personTypeTitle":
			return SearchBasis.staticSolrPersonTypeTitle(siteRequest_, (String)o);
		case "personTypeDriver":
			return SearchBasis.staticSolrPersonTypeDriver(siteRequest_, (Boolean)o);
		case "personTypePassenger":
			return SearchBasis.staticSolrPersonTypePassenger(siteRequest_, (Boolean)o);
		case "personGenderId":
			return SearchBasis.staticSolrPersonGenderId(siteRequest_, (String)o);
		case "personGenderTitle":
			return SearchBasis.staticSolrPersonGenderTitle(siteRequest_, (String)o);
		case "personGenderFemale":
			return SearchBasis.staticSolrPersonGenderFemale(siteRequest_, (Boolean)o);
		case "personGenderMale":
			return SearchBasis.staticSolrPersonGenderMale(siteRequest_, (Boolean)o);
		case "personEthnicityId":
			return SearchBasis.staticSolrPersonEthnicityId(siteRequest_, (String)o);
		case "personEthnicityTitle":
			return SearchBasis.staticSolrPersonEthnicityTitle(siteRequest_, (String)o);
		case "personRaceId":
			return SearchBasis.staticSolrPersonRaceId(siteRequest_, (String)o);
		case "personRaceTitle":
			return SearchBasis.staticSolrPersonRaceTitle(siteRequest_, (String)o);
		case "trafficStopKey":
			return SearchBasis.staticSolrTrafficStopKey(siteRequest_, (Long)o);
		case "searchTypeNum":
			return SearchBasis.staticSolrSearchTypeNum(siteRequest_, (Integer)o);
		case "searchTypeTitle":
			return SearchBasis.staticSolrSearchTypeTitle(siteRequest_, (String)o);
		case "searchVehicle":
			return SearchBasis.staticSolrSearchVehicle(siteRequest_, (Boolean)o);
		case "searchDriver":
			return SearchBasis.staticSolrSearchDriver(siteRequest_, (Boolean)o);
		case "searchPassenger":
			return SearchBasis.staticSolrSearchPassenger(siteRequest_, (Boolean)o);
		case "searchProperty":
			return SearchBasis.staticSolrSearchProperty(siteRequest_, (Boolean)o);
		case "searchVehicleSiezed":
			return SearchBasis.staticSolrSearchVehicleSiezed(siteRequest_, (Boolean)o);
		case "searchPersonalPropertySiezed":
			return SearchBasis.staticSolrSearchPersonalPropertySiezed(siteRequest_, (Boolean)o);
		case "searchOtherPropertySiezed":
			return SearchBasis.staticSolrSearchOtherPropertySiezed(siteRequest_, (Boolean)o);
		case "searchBasisId":
			return SearchBasis.staticSolrSearchBasisId(siteRequest_, (String)o);
		case "searchBasisTitle":
			return SearchBasis.staticSolrSearchBasisTitle(siteRequest_, (String)o);
		case "searchBasisShortName":
			return SearchBasis.staticSolrSearchBasisShortName(siteRequest_, (String)o);
		case "searchBasisCompleteName":
			return SearchBasis.staticSolrSearchBasisCompleteName(siteRequest_, (String)o);
			default:
				return Cluster.staticSolrCluster(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrSearchBasis(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrSearchBasis(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "searchBasisKey":
			return SearchBasis.staticSolrStrSearchBasisKey(siteRequest_, (Long)o);
		case "searchKey":
			return SearchBasis.staticSolrStrSearchKey(siteRequest_, (Long)o);
		case "stopAgencyTitle":
			return SearchBasis.staticSolrStrStopAgencyTitle(siteRequest_, (String)o);
		case "stopDateTime":
			return SearchBasis.staticSolrStrStopDateTime(siteRequest_, (Date)o);
		case "stopPurposeNum":
			return SearchBasis.staticSolrStrStopPurposeNum(siteRequest_, (Integer)o);
		case "stopPurposeTitle":
			return SearchBasis.staticSolrStrStopPurposeTitle(siteRequest_, (String)o);
		case "stopActionNum":
			return SearchBasis.staticSolrStrStopActionNum(siteRequest_, (Integer)o);
		case "stopActionTitle":
			return SearchBasis.staticSolrStrStopActionTitle(siteRequest_, (String)o);
		case "stopDriverArrest":
			return SearchBasis.staticSolrStrStopDriverArrest(siteRequest_, (Boolean)o);
		case "stopPassengerArrest":
			return SearchBasis.staticSolrStrStopPassengerArrest(siteRequest_, (Boolean)o);
		case "stopEncounterForce":
			return SearchBasis.staticSolrStrStopEncounterForce(siteRequest_, (Boolean)o);
		case "stopEngageForce":
			return SearchBasis.staticSolrStrStopEngageForce(siteRequest_, (Boolean)o);
		case "stopOfficerInjury":
			return SearchBasis.staticSolrStrStopOfficerInjury(siteRequest_, (Boolean)o);
		case "stopDriverInjury":
			return SearchBasis.staticSolrStrStopDriverInjury(siteRequest_, (Boolean)o);
		case "stopPassengerInjury":
			return SearchBasis.staticSolrStrStopPassengerInjury(siteRequest_, (Boolean)o);
		case "stopOfficerId":
			return SearchBasis.staticSolrStrStopOfficerId(siteRequest_, (String)o);
		case "stopLocationId":
			return SearchBasis.staticSolrStrStopLocationId(siteRequest_, (String)o);
		case "stopCityId":
			return SearchBasis.staticSolrStrStopCityId(siteRequest_, (String)o);
		case "personAge":
			return SearchBasis.staticSolrStrPersonAge(siteRequest_, (Integer)o);
		case "personTypeId":
			return SearchBasis.staticSolrStrPersonTypeId(siteRequest_, (String)o);
		case "personTypeTitle":
			return SearchBasis.staticSolrStrPersonTypeTitle(siteRequest_, (String)o);
		case "personTypeDriver":
			return SearchBasis.staticSolrStrPersonTypeDriver(siteRequest_, (Boolean)o);
		case "personTypePassenger":
			return SearchBasis.staticSolrStrPersonTypePassenger(siteRequest_, (Boolean)o);
		case "personGenderId":
			return SearchBasis.staticSolrStrPersonGenderId(siteRequest_, (String)o);
		case "personGenderTitle":
			return SearchBasis.staticSolrStrPersonGenderTitle(siteRequest_, (String)o);
		case "personGenderFemale":
			return SearchBasis.staticSolrStrPersonGenderFemale(siteRequest_, (Boolean)o);
		case "personGenderMale":
			return SearchBasis.staticSolrStrPersonGenderMale(siteRequest_, (Boolean)o);
		case "personEthnicityId":
			return SearchBasis.staticSolrStrPersonEthnicityId(siteRequest_, (String)o);
		case "personEthnicityTitle":
			return SearchBasis.staticSolrStrPersonEthnicityTitle(siteRequest_, (String)o);
		case "personRaceId":
			return SearchBasis.staticSolrStrPersonRaceId(siteRequest_, (String)o);
		case "personRaceTitle":
			return SearchBasis.staticSolrStrPersonRaceTitle(siteRequest_, (String)o);
		case "trafficStopKey":
			return SearchBasis.staticSolrStrTrafficStopKey(siteRequest_, (Long)o);
		case "searchTypeNum":
			return SearchBasis.staticSolrStrSearchTypeNum(siteRequest_, (Integer)o);
		case "searchTypeTitle":
			return SearchBasis.staticSolrStrSearchTypeTitle(siteRequest_, (String)o);
		case "searchVehicle":
			return SearchBasis.staticSolrStrSearchVehicle(siteRequest_, (Boolean)o);
		case "searchDriver":
			return SearchBasis.staticSolrStrSearchDriver(siteRequest_, (Boolean)o);
		case "searchPassenger":
			return SearchBasis.staticSolrStrSearchPassenger(siteRequest_, (Boolean)o);
		case "searchProperty":
			return SearchBasis.staticSolrStrSearchProperty(siteRequest_, (Boolean)o);
		case "searchVehicleSiezed":
			return SearchBasis.staticSolrStrSearchVehicleSiezed(siteRequest_, (Boolean)o);
		case "searchPersonalPropertySiezed":
			return SearchBasis.staticSolrStrSearchPersonalPropertySiezed(siteRequest_, (Boolean)o);
		case "searchOtherPropertySiezed":
			return SearchBasis.staticSolrStrSearchOtherPropertySiezed(siteRequest_, (Boolean)o);
		case "searchBasisId":
			return SearchBasis.staticSolrStrSearchBasisId(siteRequest_, (String)o);
		case "searchBasisTitle":
			return SearchBasis.staticSolrStrSearchBasisTitle(siteRequest_, (String)o);
		case "searchBasisShortName":
			return SearchBasis.staticSolrStrSearchBasisShortName(siteRequest_, (String)o);
		case "searchBasisCompleteName":
			return SearchBasis.staticSolrStrSearchBasisCompleteName(siteRequest_, (String)o);
			default:
				return Cluster.staticSolrStrCluster(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqSearchBasis(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqSearchBasis(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "searchBasisKey":
			return SearchBasis.staticSolrFqSearchBasisKey(siteRequest_, o);
		case "searchKey":
			return SearchBasis.staticSolrFqSearchKey(siteRequest_, o);
		case "stopAgencyTitle":
			return SearchBasis.staticSolrFqStopAgencyTitle(siteRequest_, o);
		case "stopDateTime":
			return SearchBasis.staticSolrFqStopDateTime(siteRequest_, o);
		case "stopPurposeNum":
			return SearchBasis.staticSolrFqStopPurposeNum(siteRequest_, o);
		case "stopPurposeTitle":
			return SearchBasis.staticSolrFqStopPurposeTitle(siteRequest_, o);
		case "stopActionNum":
			return SearchBasis.staticSolrFqStopActionNum(siteRequest_, o);
		case "stopActionTitle":
			return SearchBasis.staticSolrFqStopActionTitle(siteRequest_, o);
		case "stopDriverArrest":
			return SearchBasis.staticSolrFqStopDriverArrest(siteRequest_, o);
		case "stopPassengerArrest":
			return SearchBasis.staticSolrFqStopPassengerArrest(siteRequest_, o);
		case "stopEncounterForce":
			return SearchBasis.staticSolrFqStopEncounterForce(siteRequest_, o);
		case "stopEngageForce":
			return SearchBasis.staticSolrFqStopEngageForce(siteRequest_, o);
		case "stopOfficerInjury":
			return SearchBasis.staticSolrFqStopOfficerInjury(siteRequest_, o);
		case "stopDriverInjury":
			return SearchBasis.staticSolrFqStopDriverInjury(siteRequest_, o);
		case "stopPassengerInjury":
			return SearchBasis.staticSolrFqStopPassengerInjury(siteRequest_, o);
		case "stopOfficerId":
			return SearchBasis.staticSolrFqStopOfficerId(siteRequest_, o);
		case "stopLocationId":
			return SearchBasis.staticSolrFqStopLocationId(siteRequest_, o);
		case "stopCityId":
			return SearchBasis.staticSolrFqStopCityId(siteRequest_, o);
		case "personAge":
			return SearchBasis.staticSolrFqPersonAge(siteRequest_, o);
		case "personTypeId":
			return SearchBasis.staticSolrFqPersonTypeId(siteRequest_, o);
		case "personTypeTitle":
			return SearchBasis.staticSolrFqPersonTypeTitle(siteRequest_, o);
		case "personTypeDriver":
			return SearchBasis.staticSolrFqPersonTypeDriver(siteRequest_, o);
		case "personTypePassenger":
			return SearchBasis.staticSolrFqPersonTypePassenger(siteRequest_, o);
		case "personGenderId":
			return SearchBasis.staticSolrFqPersonGenderId(siteRequest_, o);
		case "personGenderTitle":
			return SearchBasis.staticSolrFqPersonGenderTitle(siteRequest_, o);
		case "personGenderFemale":
			return SearchBasis.staticSolrFqPersonGenderFemale(siteRequest_, o);
		case "personGenderMale":
			return SearchBasis.staticSolrFqPersonGenderMale(siteRequest_, o);
		case "personEthnicityId":
			return SearchBasis.staticSolrFqPersonEthnicityId(siteRequest_, o);
		case "personEthnicityTitle":
			return SearchBasis.staticSolrFqPersonEthnicityTitle(siteRequest_, o);
		case "personRaceId":
			return SearchBasis.staticSolrFqPersonRaceId(siteRequest_, o);
		case "personRaceTitle":
			return SearchBasis.staticSolrFqPersonRaceTitle(siteRequest_, o);
		case "trafficStopKey":
			return SearchBasis.staticSolrFqTrafficStopKey(siteRequest_, o);
		case "searchTypeNum":
			return SearchBasis.staticSolrFqSearchTypeNum(siteRequest_, o);
		case "searchTypeTitle":
			return SearchBasis.staticSolrFqSearchTypeTitle(siteRequest_, o);
		case "searchVehicle":
			return SearchBasis.staticSolrFqSearchVehicle(siteRequest_, o);
		case "searchDriver":
			return SearchBasis.staticSolrFqSearchDriver(siteRequest_, o);
		case "searchPassenger":
			return SearchBasis.staticSolrFqSearchPassenger(siteRequest_, o);
		case "searchProperty":
			return SearchBasis.staticSolrFqSearchProperty(siteRequest_, o);
		case "searchVehicleSiezed":
			return SearchBasis.staticSolrFqSearchVehicleSiezed(siteRequest_, o);
		case "searchPersonalPropertySiezed":
			return SearchBasis.staticSolrFqSearchPersonalPropertySiezed(siteRequest_, o);
		case "searchOtherPropertySiezed":
			return SearchBasis.staticSolrFqSearchOtherPropertySiezed(siteRequest_, o);
		case "searchBasisId":
			return SearchBasis.staticSolrFqSearchBasisId(siteRequest_, o);
		case "searchBasisTitle":
			return SearchBasis.staticSolrFqSearchBasisTitle(siteRequest_, o);
		case "searchBasisShortName":
			return SearchBasis.staticSolrFqSearchBasisShortName(siteRequest_, o);
		case "searchBasisCompleteName":
			return SearchBasis.staticSolrFqSearchBasisCompleteName(siteRequest_, o);
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
					o = defineSearchBasis(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSearchBasis(String var, String val) {
		switch(var) {
			case "stopAgencyTitle":
				if(val != null)
					setStopAgencyTitle(val);
				saves.add(var);
				return val;
			case "stopDateTime":
				if(val != null)
					setStopDateTime(val);
				saves.add(var);
				return val;
			case "stopPurposeNum":
				if(val != null)
					setStopPurposeNum(val);
				saves.add(var);
				return val;
			case "stopPurposeTitle":
				if(val != null)
					setStopPurposeTitle(val);
				saves.add(var);
				return val;
			case "stopActionNum":
				if(val != null)
					setStopActionNum(val);
				saves.add(var);
				return val;
			case "stopActionTitle":
				if(val != null)
					setStopActionTitle(val);
				saves.add(var);
				return val;
			case "stopDriverArrest":
				if(val != null)
					setStopDriverArrest(val);
				saves.add(var);
				return val;
			case "stopPassengerArrest":
				if(val != null)
					setStopPassengerArrest(val);
				saves.add(var);
				return val;
			case "stopEncounterForce":
				if(val != null)
					setStopEncounterForce(val);
				saves.add(var);
				return val;
			case "stopEngageForce":
				if(val != null)
					setStopEngageForce(val);
				saves.add(var);
				return val;
			case "stopOfficerInjury":
				if(val != null)
					setStopOfficerInjury(val);
				saves.add(var);
				return val;
			case "stopDriverInjury":
				if(val != null)
					setStopDriverInjury(val);
				saves.add(var);
				return val;
			case "stopPassengerInjury":
				if(val != null)
					setStopPassengerInjury(val);
				saves.add(var);
				return val;
			case "stopOfficerId":
				if(val != null)
					setStopOfficerId(val);
				saves.add(var);
				return val;
			case "stopLocationId":
				if(val != null)
					setStopLocationId(val);
				saves.add(var);
				return val;
			case "stopCityId":
				if(val != null)
					setStopCityId(val);
				saves.add(var);
				return val;
			case "personTypeId":
				if(val != null)
					setPersonTypeId(val);
				saves.add(var);
				return val;
			case "personGenderId":
				if(val != null)
					setPersonGenderId(val);
				saves.add(var);
				return val;
			case "personEthnicityId":
				if(val != null)
					setPersonEthnicityId(val);
				saves.add(var);
				return val;
			case "personRaceId":
				if(val != null)
					setPersonRaceId(val);
				saves.add(var);
				return val;
			case "searchTypeNum":
				if(val != null)
					setSearchTypeNum(val);
				saves.add(var);
				return val;
			case "searchBasisId":
				if(val != null)
					setSearchBasisId(val);
				saves.add(var);
				return val;
			case "searchBasisTitle":
				if(val != null)
					setSearchBasisTitle(val);
				saves.add(var);
				return val;
			default:
				return super.defineCluster(var, val);
		}
	}

	/////////////
	// populate //
	/////////////

	@Override public void populateForClass(SolrDocument solrDocument) {
		populateSearchBasis(solrDocument);
	}
	public void populateSearchBasis(SolrDocument solrDocument) {
		SearchBasis oSearchBasis = (SearchBasis)this;
		saves = (List<String>)solrDocument.get("saves_stored_strings");
		if(saves != null) {

			if(saves.contains("searchBasisKey")) {
				Long searchBasisKey = (Long)solrDocument.get("searchBasisKey_stored_long");
				if(searchBasisKey != null)
					oSearchBasis.setSearchBasisKey(searchBasisKey);
			}

			Long searchKey = (Long)solrDocument.get("searchKey_stored_long");
			if(searchKey != null)
				oSearchBasis.setSearchKey(searchKey);

			if(saves.contains("stopAgencyTitle")) {
				String stopAgencyTitle = (String)solrDocument.get("stopAgencyTitle_stored_string");
				if(stopAgencyTitle != null)
					oSearchBasis.setStopAgencyTitle(stopAgencyTitle);
			}

			if(saves.contains("stopDateTime")) {
				Date stopDateTime = (Date)solrDocument.get("stopDateTime_stored_date");
				if(stopDateTime != null)
					oSearchBasis.setStopDateTime(stopDateTime);
			}

			if(saves.contains("stopPurposeNum")) {
				Integer stopPurposeNum = (Integer)solrDocument.get("stopPurposeNum_stored_int");
				if(stopPurposeNum != null)
					oSearchBasis.setStopPurposeNum(stopPurposeNum);
			}

			if(saves.contains("stopPurposeTitle")) {
				String stopPurposeTitle = (String)solrDocument.get("stopPurposeTitle_stored_string");
				if(stopPurposeTitle != null)
					oSearchBasis.setStopPurposeTitle(stopPurposeTitle);
			}

			if(saves.contains("stopActionNum")) {
				Integer stopActionNum = (Integer)solrDocument.get("stopActionNum_stored_int");
				if(stopActionNum != null)
					oSearchBasis.setStopActionNum(stopActionNum);
			}

			if(saves.contains("stopActionTitle")) {
				String stopActionTitle = (String)solrDocument.get("stopActionTitle_stored_string");
				if(stopActionTitle != null)
					oSearchBasis.setStopActionTitle(stopActionTitle);
			}

			if(saves.contains("stopDriverArrest")) {
				Boolean stopDriverArrest = (Boolean)solrDocument.get("stopDriverArrest_stored_boolean");
				if(stopDriverArrest != null)
					oSearchBasis.setStopDriverArrest(stopDriverArrest);
			}

			if(saves.contains("stopPassengerArrest")) {
				Boolean stopPassengerArrest = (Boolean)solrDocument.get("stopPassengerArrest_stored_boolean");
				if(stopPassengerArrest != null)
					oSearchBasis.setStopPassengerArrest(stopPassengerArrest);
			}

			if(saves.contains("stopEncounterForce")) {
				Boolean stopEncounterForce = (Boolean)solrDocument.get("stopEncounterForce_stored_boolean");
				if(stopEncounterForce != null)
					oSearchBasis.setStopEncounterForce(stopEncounterForce);
			}

			if(saves.contains("stopEngageForce")) {
				Boolean stopEngageForce = (Boolean)solrDocument.get("stopEngageForce_stored_boolean");
				if(stopEngageForce != null)
					oSearchBasis.setStopEngageForce(stopEngageForce);
			}

			if(saves.contains("stopOfficerInjury")) {
				Boolean stopOfficerInjury = (Boolean)solrDocument.get("stopOfficerInjury_stored_boolean");
				if(stopOfficerInjury != null)
					oSearchBasis.setStopOfficerInjury(stopOfficerInjury);
			}

			if(saves.contains("stopDriverInjury")) {
				Boolean stopDriverInjury = (Boolean)solrDocument.get("stopDriverInjury_stored_boolean");
				if(stopDriverInjury != null)
					oSearchBasis.setStopDriverInjury(stopDriverInjury);
			}

			if(saves.contains("stopPassengerInjury")) {
				Boolean stopPassengerInjury = (Boolean)solrDocument.get("stopPassengerInjury_stored_boolean");
				if(stopPassengerInjury != null)
					oSearchBasis.setStopPassengerInjury(stopPassengerInjury);
			}

			if(saves.contains("stopOfficerId")) {
				String stopOfficerId = (String)solrDocument.get("stopOfficerId_stored_string");
				if(stopOfficerId != null)
					oSearchBasis.setStopOfficerId(stopOfficerId);
			}

			if(saves.contains("stopLocationId")) {
				String stopLocationId = (String)solrDocument.get("stopLocationId_stored_string");
				if(stopLocationId != null)
					oSearchBasis.setStopLocationId(stopLocationId);
			}

			if(saves.contains("stopCityId")) {
				String stopCityId = (String)solrDocument.get("stopCityId_stored_string");
				if(stopCityId != null)
					oSearchBasis.setStopCityId(stopCityId);
			}

			if(saves.contains("personAge")) {
				Integer personAge = (Integer)solrDocument.get("personAge_stored_int");
				if(personAge != null)
					oSearchBasis.setPersonAge(personAge);
			}

			if(saves.contains("personTypeId")) {
				String personTypeId = (String)solrDocument.get("personTypeId_stored_string");
				if(personTypeId != null)
					oSearchBasis.setPersonTypeId(personTypeId);
			}

			if(saves.contains("personTypeTitle")) {
				String personTypeTitle = (String)solrDocument.get("personTypeTitle_stored_string");
				if(personTypeTitle != null)
					oSearchBasis.setPersonTypeTitle(personTypeTitle);
			}

			if(saves.contains("personTypeDriver")) {
				Boolean personTypeDriver = (Boolean)solrDocument.get("personTypeDriver_stored_boolean");
				if(personTypeDriver != null)
					oSearchBasis.setPersonTypeDriver(personTypeDriver);
			}

			if(saves.contains("personTypePassenger")) {
				Boolean personTypePassenger = (Boolean)solrDocument.get("personTypePassenger_stored_boolean");
				if(personTypePassenger != null)
					oSearchBasis.setPersonTypePassenger(personTypePassenger);
			}

			if(saves.contains("personGenderId")) {
				String personGenderId = (String)solrDocument.get("personGenderId_stored_string");
				if(personGenderId != null)
					oSearchBasis.setPersonGenderId(personGenderId);
			}

			if(saves.contains("personGenderTitle")) {
				String personGenderTitle = (String)solrDocument.get("personGenderTitle_stored_string");
				if(personGenderTitle != null)
					oSearchBasis.setPersonGenderTitle(personGenderTitle);
			}

			if(saves.contains("personGenderFemale")) {
				Boolean personGenderFemale = (Boolean)solrDocument.get("personGenderFemale_stored_boolean");
				if(personGenderFemale != null)
					oSearchBasis.setPersonGenderFemale(personGenderFemale);
			}

			if(saves.contains("personGenderMale")) {
				Boolean personGenderMale = (Boolean)solrDocument.get("personGenderMale_stored_boolean");
				if(personGenderMale != null)
					oSearchBasis.setPersonGenderMale(personGenderMale);
			}

			if(saves.contains("personEthnicityId")) {
				String personEthnicityId = (String)solrDocument.get("personEthnicityId_stored_string");
				if(personEthnicityId != null)
					oSearchBasis.setPersonEthnicityId(personEthnicityId);
			}

			if(saves.contains("personEthnicityTitle")) {
				String personEthnicityTitle = (String)solrDocument.get("personEthnicityTitle_stored_string");
				if(personEthnicityTitle != null)
					oSearchBasis.setPersonEthnicityTitle(personEthnicityTitle);
			}

			if(saves.contains("personRaceId")) {
				String personRaceId = (String)solrDocument.get("personRaceId_stored_string");
				if(personRaceId != null)
					oSearchBasis.setPersonRaceId(personRaceId);
			}

			if(saves.contains("personRaceTitle")) {
				String personRaceTitle = (String)solrDocument.get("personRaceTitle_stored_string");
				if(personRaceTitle != null)
					oSearchBasis.setPersonRaceTitle(personRaceTitle);
			}

			if(saves.contains("trafficStopKey")) {
				Long trafficStopKey = (Long)solrDocument.get("trafficStopKey_stored_long");
				if(trafficStopKey != null)
					oSearchBasis.setTrafficStopKey(trafficStopKey);
			}

			if(saves.contains("searchTypeNum")) {
				Integer searchTypeNum = (Integer)solrDocument.get("searchTypeNum_stored_int");
				if(searchTypeNum != null)
					oSearchBasis.setSearchTypeNum(searchTypeNum);
			}

			if(saves.contains("searchTypeTitle")) {
				String searchTypeTitle = (String)solrDocument.get("searchTypeTitle_stored_string");
				if(searchTypeTitle != null)
					oSearchBasis.setSearchTypeTitle(searchTypeTitle);
			}

			if(saves.contains("searchVehicle")) {
				Boolean searchVehicle = (Boolean)solrDocument.get("searchVehicle_stored_boolean");
				if(searchVehicle != null)
					oSearchBasis.setSearchVehicle(searchVehicle);
			}

			if(saves.contains("searchDriver")) {
				Boolean searchDriver = (Boolean)solrDocument.get("searchDriver_stored_boolean");
				if(searchDriver != null)
					oSearchBasis.setSearchDriver(searchDriver);
			}

			if(saves.contains("searchPassenger")) {
				Boolean searchPassenger = (Boolean)solrDocument.get("searchPassenger_stored_boolean");
				if(searchPassenger != null)
					oSearchBasis.setSearchPassenger(searchPassenger);
			}

			if(saves.contains("searchProperty")) {
				Boolean searchProperty = (Boolean)solrDocument.get("searchProperty_stored_boolean");
				if(searchProperty != null)
					oSearchBasis.setSearchProperty(searchProperty);
			}

			if(saves.contains("searchVehicleSiezed")) {
				Boolean searchVehicleSiezed = (Boolean)solrDocument.get("searchVehicleSiezed_stored_boolean");
				if(searchVehicleSiezed != null)
					oSearchBasis.setSearchVehicleSiezed(searchVehicleSiezed);
			}

			if(saves.contains("searchPersonalPropertySiezed")) {
				Boolean searchPersonalPropertySiezed = (Boolean)solrDocument.get("searchPersonalPropertySiezed_stored_boolean");
				if(searchPersonalPropertySiezed != null)
					oSearchBasis.setSearchPersonalPropertySiezed(searchPersonalPropertySiezed);
			}

			if(saves.contains("searchOtherPropertySiezed")) {
				Boolean searchOtherPropertySiezed = (Boolean)solrDocument.get("searchOtherPropertySiezed_stored_boolean");
				if(searchOtherPropertySiezed != null)
					oSearchBasis.setSearchOtherPropertySiezed(searchOtherPropertySiezed);
			}

			if(saves.contains("searchBasisId")) {
				String searchBasisId = (String)solrDocument.get("searchBasisId_stored_string");
				if(searchBasisId != null)
					oSearchBasis.setSearchBasisId(searchBasisId);
			}

			if(saves.contains("searchBasisTitle")) {
				String searchBasisTitle = (String)solrDocument.get("searchBasisTitle_stored_string");
				if(searchBasisTitle != null)
					oSearchBasis.setSearchBasisTitle(searchBasisTitle);
			}

			if(saves.contains("searchBasisShortName")) {
				String searchBasisShortName = (String)solrDocument.get("searchBasisShortName_stored_string");
				if(searchBasisShortName != null)
					oSearchBasis.setSearchBasisShortName(searchBasisShortName);
			}

			if(saves.contains("searchBasisCompleteName")) {
				String searchBasisCompleteName = (String)solrDocument.get("searchBasisCompleteName_stored_string");
				if(searchBasisCompleteName != null)
					oSearchBasis.setSearchBasisCompleteName(searchBasisCompleteName);
			}
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
			solrQuery.addFilterQuery("id:" + ClientUtils.escapeQueryChars("com.opendatapolicing.enus.searchbasis.SearchBasis"));
			QueryResponse queryResponse = siteRequest.getSiteContext_().getSolrClient().query(solrQuery);
			if(queryResponse.getResults().size() > 0)
				siteRequest.setSolrDocument(queryResponse.getResults().get(0));
			SearchBasis o = new SearchBasis();
			o.siteRequestSearchBasis(siteRequest);
			o.initDeepSearchBasis(siteRequest);
			o.indexSearchBasis();
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}


	@Override public void indexForClass() {
		indexSearchBasis();
	}

	@Override public void indexForClass(SolrInputDocument document) {
		indexSearchBasis(document);
	}

	public void indexSearchBasis(SolrClient clientSolr) {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexSearchBasis(document);
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexSearchBasis() {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexSearchBasis(document);
			SolrClient clientSolr = siteRequest_.getSiteContext_().getSolrClient();
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexSearchBasis(SolrInputDocument document) {
		if(searchBasisKey != null) {
			document.addField("searchBasisKey_indexed_long", searchBasisKey);
			document.addField("searchBasisKey_stored_long", searchBasisKey);
		}
		if(searchKey != null) {
			document.addField("searchKey_indexed_long", searchKey);
			document.addField("searchKey_stored_long", searchKey);
		}
		if(stopAgencyTitle != null) {
			document.addField("stopAgencyTitle_indexed_string", stopAgencyTitle);
			document.addField("stopAgencyTitle_stored_string", stopAgencyTitle);
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
		if(trafficStopKey != null) {
			document.addField("trafficStopKey_indexed_long", trafficStopKey);
			document.addField("trafficStopKey_stored_long", trafficStopKey);
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
		if(searchBasisId != null) {
			document.addField("searchBasisId_indexed_string", searchBasisId);
			document.addField("searchBasisId_stored_string", searchBasisId);
		}
		if(searchBasisTitle != null) {
			document.addField("searchBasisTitle_indexed_string", searchBasisTitle);
			document.addField("searchBasisTitle_stored_string", searchBasisTitle);
		}
		if(searchBasisShortName != null) {
			document.addField("searchBasisShortName_indexed_string", searchBasisShortName);
			document.addField("searchBasisShortName_stored_string", searchBasisShortName);
		}
		if(searchBasisCompleteName != null) {
			document.addField("searchBasisCompleteName_indexed_string", searchBasisCompleteName);
			document.addField("searchBasisCompleteName_stored_string", searchBasisCompleteName);
		}
		super.indexCluster(document);

	}

	public void unindexSearchBasis() {
		try {
		SiteRequestEnUS siteRequest = new SiteRequestEnUS();
			siteRequest.initDeepSiteRequestEnUS();
			SiteContextEnUS siteContext = new SiteContextEnUS();
			siteContext.initDeepSiteContextEnUS();
			siteRequest.setSiteContext_(siteContext);
			siteRequest.setSiteConfig_(siteContext.getSiteConfig());
			initDeepSearchBasis(siteRequest);
			SolrClient solrClient = siteContext.getSolrClient();
			solrClient.deleteById(id.toString());
			solrClient.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public static String varIndexedSearchBasis(String entityVar) {
		switch(entityVar) {
			case "searchBasisKey":
				return "searchBasisKey_indexed_long";
			case "searchKey":
				return "searchKey_indexed_long";
			case "stopAgencyTitle":
				return "stopAgencyTitle_indexed_string";
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
			case "trafficStopKey":
				return "trafficStopKey_indexed_long";
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
			case "searchBasisId":
				return "searchBasisId_indexed_string";
			case "searchBasisTitle":
				return "searchBasisTitle_indexed_string";
			case "searchBasisShortName":
				return "searchBasisShortName_indexed_string";
			case "searchBasisCompleteName":
				return "searchBasisCompleteName_indexed_string";
			default:
				return Cluster.varIndexedCluster(entityVar);
		}
	}

	public static String varSearchSearchBasis(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSearchCluster(entityVar);
		}
	}

	public static String varSuggestedSearchBasis(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSuggestedCluster(entityVar);
		}
	}

	/////////////
	// store //
	/////////////

	@Override public void storeForClass(SolrDocument solrDocument) {
		storeSearchBasis(solrDocument);
	}
	public void storeSearchBasis(SolrDocument solrDocument) {
		SearchBasis oSearchBasis = (SearchBasis)this;

		Long searchBasisKey = (Long)solrDocument.get("searchBasisKey_stored_long");
		if(searchBasisKey != null)
			oSearchBasis.setSearchBasisKey(searchBasisKey);

		Long searchKey = (Long)solrDocument.get("searchKey_stored_long");
		if(searchKey != null)
			oSearchBasis.setSearchKey(searchKey);

		String stopAgencyTitle = (String)solrDocument.get("stopAgencyTitle_stored_string");
		if(stopAgencyTitle != null)
			oSearchBasis.setStopAgencyTitle(stopAgencyTitle);

		Date stopDateTime = (Date)solrDocument.get("stopDateTime_stored_date");
		if(stopDateTime != null)
			oSearchBasis.setStopDateTime(stopDateTime);

		Integer stopPurposeNum = (Integer)solrDocument.get("stopPurposeNum_stored_int");
		if(stopPurposeNum != null)
			oSearchBasis.setStopPurposeNum(stopPurposeNum);

		String stopPurposeTitle = (String)solrDocument.get("stopPurposeTitle_stored_string");
		if(stopPurposeTitle != null)
			oSearchBasis.setStopPurposeTitle(stopPurposeTitle);

		Integer stopActionNum = (Integer)solrDocument.get("stopActionNum_stored_int");
		if(stopActionNum != null)
			oSearchBasis.setStopActionNum(stopActionNum);

		String stopActionTitle = (String)solrDocument.get("stopActionTitle_stored_string");
		if(stopActionTitle != null)
			oSearchBasis.setStopActionTitle(stopActionTitle);

		Boolean stopDriverArrest = (Boolean)solrDocument.get("stopDriverArrest_stored_boolean");
		if(stopDriverArrest != null)
			oSearchBasis.setStopDriverArrest(stopDriverArrest);

		Boolean stopPassengerArrest = (Boolean)solrDocument.get("stopPassengerArrest_stored_boolean");
		if(stopPassengerArrest != null)
			oSearchBasis.setStopPassengerArrest(stopPassengerArrest);

		Boolean stopEncounterForce = (Boolean)solrDocument.get("stopEncounterForce_stored_boolean");
		if(stopEncounterForce != null)
			oSearchBasis.setStopEncounterForce(stopEncounterForce);

		Boolean stopEngageForce = (Boolean)solrDocument.get("stopEngageForce_stored_boolean");
		if(stopEngageForce != null)
			oSearchBasis.setStopEngageForce(stopEngageForce);

		Boolean stopOfficerInjury = (Boolean)solrDocument.get("stopOfficerInjury_stored_boolean");
		if(stopOfficerInjury != null)
			oSearchBasis.setStopOfficerInjury(stopOfficerInjury);

		Boolean stopDriverInjury = (Boolean)solrDocument.get("stopDriverInjury_stored_boolean");
		if(stopDriverInjury != null)
			oSearchBasis.setStopDriverInjury(stopDriverInjury);

		Boolean stopPassengerInjury = (Boolean)solrDocument.get("stopPassengerInjury_stored_boolean");
		if(stopPassengerInjury != null)
			oSearchBasis.setStopPassengerInjury(stopPassengerInjury);

		String stopOfficerId = (String)solrDocument.get("stopOfficerId_stored_string");
		if(stopOfficerId != null)
			oSearchBasis.setStopOfficerId(stopOfficerId);

		String stopLocationId = (String)solrDocument.get("stopLocationId_stored_string");
		if(stopLocationId != null)
			oSearchBasis.setStopLocationId(stopLocationId);

		String stopCityId = (String)solrDocument.get("stopCityId_stored_string");
		if(stopCityId != null)
			oSearchBasis.setStopCityId(stopCityId);

		Integer personAge = (Integer)solrDocument.get("personAge_stored_int");
		if(personAge != null)
			oSearchBasis.setPersonAge(personAge);

		String personTypeId = (String)solrDocument.get("personTypeId_stored_string");
		if(personTypeId != null)
			oSearchBasis.setPersonTypeId(personTypeId);

		String personTypeTitle = (String)solrDocument.get("personTypeTitle_stored_string");
		if(personTypeTitle != null)
			oSearchBasis.setPersonTypeTitle(personTypeTitle);

		Boolean personTypeDriver = (Boolean)solrDocument.get("personTypeDriver_stored_boolean");
		if(personTypeDriver != null)
			oSearchBasis.setPersonTypeDriver(personTypeDriver);

		Boolean personTypePassenger = (Boolean)solrDocument.get("personTypePassenger_stored_boolean");
		if(personTypePassenger != null)
			oSearchBasis.setPersonTypePassenger(personTypePassenger);

		String personGenderId = (String)solrDocument.get("personGenderId_stored_string");
		if(personGenderId != null)
			oSearchBasis.setPersonGenderId(personGenderId);

		String personGenderTitle = (String)solrDocument.get("personGenderTitle_stored_string");
		if(personGenderTitle != null)
			oSearchBasis.setPersonGenderTitle(personGenderTitle);

		Boolean personGenderFemale = (Boolean)solrDocument.get("personGenderFemale_stored_boolean");
		if(personGenderFemale != null)
			oSearchBasis.setPersonGenderFemale(personGenderFemale);

		Boolean personGenderMale = (Boolean)solrDocument.get("personGenderMale_stored_boolean");
		if(personGenderMale != null)
			oSearchBasis.setPersonGenderMale(personGenderMale);

		String personEthnicityId = (String)solrDocument.get("personEthnicityId_stored_string");
		if(personEthnicityId != null)
			oSearchBasis.setPersonEthnicityId(personEthnicityId);

		String personEthnicityTitle = (String)solrDocument.get("personEthnicityTitle_stored_string");
		if(personEthnicityTitle != null)
			oSearchBasis.setPersonEthnicityTitle(personEthnicityTitle);

		String personRaceId = (String)solrDocument.get("personRaceId_stored_string");
		if(personRaceId != null)
			oSearchBasis.setPersonRaceId(personRaceId);

		String personRaceTitle = (String)solrDocument.get("personRaceTitle_stored_string");
		if(personRaceTitle != null)
			oSearchBasis.setPersonRaceTitle(personRaceTitle);

		Long trafficStopKey = (Long)solrDocument.get("trafficStopKey_stored_long");
		if(trafficStopKey != null)
			oSearchBasis.setTrafficStopKey(trafficStopKey);

		Integer searchTypeNum = (Integer)solrDocument.get("searchTypeNum_stored_int");
		if(searchTypeNum != null)
			oSearchBasis.setSearchTypeNum(searchTypeNum);

		String searchTypeTitle = (String)solrDocument.get("searchTypeTitle_stored_string");
		if(searchTypeTitle != null)
			oSearchBasis.setSearchTypeTitle(searchTypeTitle);

		Boolean searchVehicle = (Boolean)solrDocument.get("searchVehicle_stored_boolean");
		if(searchVehicle != null)
			oSearchBasis.setSearchVehicle(searchVehicle);

		Boolean searchDriver = (Boolean)solrDocument.get("searchDriver_stored_boolean");
		if(searchDriver != null)
			oSearchBasis.setSearchDriver(searchDriver);

		Boolean searchPassenger = (Boolean)solrDocument.get("searchPassenger_stored_boolean");
		if(searchPassenger != null)
			oSearchBasis.setSearchPassenger(searchPassenger);

		Boolean searchProperty = (Boolean)solrDocument.get("searchProperty_stored_boolean");
		if(searchProperty != null)
			oSearchBasis.setSearchProperty(searchProperty);

		Boolean searchVehicleSiezed = (Boolean)solrDocument.get("searchVehicleSiezed_stored_boolean");
		if(searchVehicleSiezed != null)
			oSearchBasis.setSearchVehicleSiezed(searchVehicleSiezed);

		Boolean searchPersonalPropertySiezed = (Boolean)solrDocument.get("searchPersonalPropertySiezed_stored_boolean");
		if(searchPersonalPropertySiezed != null)
			oSearchBasis.setSearchPersonalPropertySiezed(searchPersonalPropertySiezed);

		Boolean searchOtherPropertySiezed = (Boolean)solrDocument.get("searchOtherPropertySiezed_stored_boolean");
		if(searchOtherPropertySiezed != null)
			oSearchBasis.setSearchOtherPropertySiezed(searchOtherPropertySiezed);

		String searchBasisId = (String)solrDocument.get("searchBasisId_stored_string");
		if(searchBasisId != null)
			oSearchBasis.setSearchBasisId(searchBasisId);

		String searchBasisTitle = (String)solrDocument.get("searchBasisTitle_stored_string");
		if(searchBasisTitle != null)
			oSearchBasis.setSearchBasisTitle(searchBasisTitle);

		String searchBasisShortName = (String)solrDocument.get("searchBasisShortName_stored_string");
		if(searchBasisShortName != null)
			oSearchBasis.setSearchBasisShortName(searchBasisShortName);

		String searchBasisCompleteName = (String)solrDocument.get("searchBasisCompleteName_stored_string");
		if(searchBasisCompleteName != null)
			oSearchBasis.setSearchBasisCompleteName(searchBasisCompleteName);

		super.storeCluster(solrDocument);
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestSearchBasis() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof SearchBasis) {
			SearchBasis original = (SearchBasis)o;
			if(!Objects.equals(searchBasisKey, original.getSearchBasisKey()))
				apiRequest.addVars("searchBasisKey");
			if(!Objects.equals(searchKey, original.getSearchKey()))
				apiRequest.addVars("searchKey");
			if(!Objects.equals(stopAgencyTitle, original.getStopAgencyTitle()))
				apiRequest.addVars("stopAgencyTitle");
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
			if(!Objects.equals(trafficStopKey, original.getTrafficStopKey()))
				apiRequest.addVars("trafficStopKey");
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
			if(!Objects.equals(searchBasisId, original.getSearchBasisId()))
				apiRequest.addVars("searchBasisId");
			if(!Objects.equals(searchBasisTitle, original.getSearchBasisTitle()))
				apiRequest.addVars("searchBasisTitle");
			if(!Objects.equals(searchBasisShortName, original.getSearchBasisShortName()))
				apiRequest.addVars("searchBasisShortName");
			if(!Objects.equals(searchBasisCompleteName, original.getSearchBasisCompleteName()))
				apiRequest.addVars("searchBasisCompleteName");
			super.apiRequestCluster();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), searchBasisKey, searchKey, stopAgencyTitle, stopDateTime, stopPurposeNum, stopPurposeTitle, stopActionNum, stopActionTitle, stopDriverArrest, stopPassengerArrest, stopEncounterForce, stopEngageForce, stopOfficerInjury, stopDriverInjury, stopPassengerInjury, stopOfficerId, stopLocationId, stopCityId, personAge, personTypeId, personTypeTitle, personTypeDriver, personTypePassenger, personGenderId, personGenderTitle, personGenderFemale, personGenderMale, personEthnicityId, personEthnicityTitle, personRaceId, personRaceTitle, trafficStopKey, searchTypeNum, searchTypeTitle, searchVehicle, searchDriver, searchPassenger, searchProperty, searchVehicleSiezed, searchPersonalPropertySiezed, searchOtherPropertySiezed, searchBasisId, searchBasisTitle, searchBasisShortName, searchBasisCompleteName);
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof SearchBasis))
			return false;
		SearchBasis that = (SearchBasis)o;
		return super.equals(o)
				&& Objects.equals( searchBasisKey, that.searchBasisKey )
				&& Objects.equals( searchKey, that.searchKey )
				&& Objects.equals( stopAgencyTitle, that.stopAgencyTitle )
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
				&& Objects.equals( trafficStopKey, that.trafficStopKey )
				&& Objects.equals( searchTypeNum, that.searchTypeNum )
				&& Objects.equals( searchTypeTitle, that.searchTypeTitle )
				&& Objects.equals( searchVehicle, that.searchVehicle )
				&& Objects.equals( searchDriver, that.searchDriver )
				&& Objects.equals( searchPassenger, that.searchPassenger )
				&& Objects.equals( searchProperty, that.searchProperty )
				&& Objects.equals( searchVehicleSiezed, that.searchVehicleSiezed )
				&& Objects.equals( searchPersonalPropertySiezed, that.searchPersonalPropertySiezed )
				&& Objects.equals( searchOtherPropertySiezed, that.searchOtherPropertySiezed )
				&& Objects.equals( searchBasisId, that.searchBasisId )
				&& Objects.equals( searchBasisTitle, that.searchBasisTitle )
				&& Objects.equals( searchBasisShortName, that.searchBasisShortName )
				&& Objects.equals( searchBasisCompleteName, that.searchBasisCompleteName );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("SearchBasis { ");
		sb.append( "searchBasisKey: " ).append(searchBasisKey);
		sb.append( ", searchKey: " ).append(searchKey);
		sb.append( ", stopAgencyTitle: \"" ).append(stopAgencyTitle).append( "\"" );
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
		sb.append( ", trafficStopKey: " ).append(trafficStopKey);
		sb.append( ", searchTypeNum: " ).append(searchTypeNum);
		sb.append( ", searchTypeTitle: \"" ).append(searchTypeTitle).append( "\"" );
		sb.append( ", searchVehicle: " ).append(searchVehicle);
		sb.append( ", searchDriver: " ).append(searchDriver);
		sb.append( ", searchPassenger: " ).append(searchPassenger);
		sb.append( ", searchProperty: " ).append(searchProperty);
		sb.append( ", searchVehicleSiezed: " ).append(searchVehicleSiezed);
		sb.append( ", searchPersonalPropertySiezed: " ).append(searchPersonalPropertySiezed);
		sb.append( ", searchOtherPropertySiezed: " ).append(searchOtherPropertySiezed);
		sb.append( ", searchBasisId: \"" ).append(searchBasisId).append( "\"" );
		sb.append( ", searchBasisTitle: \"" ).append(searchBasisTitle).append( "\"" );
		sb.append( ", searchBasisShortName: \"" ).append(searchBasisShortName).append( "\"" );
		sb.append( ", searchBasisCompleteName: \"" ).append(searchBasisCompleteName).append( "\"" );
		sb.append(" }");
		return sb.toString();
	}
}
