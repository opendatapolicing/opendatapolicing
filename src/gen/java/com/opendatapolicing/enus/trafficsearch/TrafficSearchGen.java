package com.opendatapolicing.enus.trafficsearch;

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
import com.opendatapolicing.enus.trafficperson.TrafficPerson;
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
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class TrafficSearchGen<DEV> extends Cluster {
	protected static final Logger LOGGER = LoggerFactory.getLogger(TrafficSearch.class);

	public static final String TrafficSearch_AName = "a traffic search";
	public static final String TrafficSearch_This = "this ";
	public static final String TrafficSearch_ThisName = "this traffic search";
	public static final String TrafficSearch_A = "a ";
	public static final String TrafficSearch_TheName = "the traffic search";
	public static final String TrafficSearch_NameSingular = "traffic search";
	public static final String TrafficSearch_NamePlural = "traffic searchs";
	public static final String TrafficSearch_NameActual = "current traffic search";
	public static final String TrafficSearch_AllName = "all the traffic searchs";
	public static final String TrafficSearch_SearchAllNameBy = "search traffic searchs by ";
	public static final String TrafficSearch_Title = "traffic searchs";
	public static final String TrafficSearch_ThePluralName = "the traffic searchs";
	public static final String TrafficSearch_NoNameFound = "no traffic search found";
	public static final String TrafficSearch_NameVar = "trafficSearch";
	public static final String TrafficSearch_OfName = "of traffic search";
	public static final String TrafficSearch_ANameAdjective = "a traffic search";
	public static final String TrafficSearch_NameAdjectiveSingular = "traffic search";
	public static final String TrafficSearch_NameAdjectivePlural = "traffic searchs";
	public static final String TrafficSearch_Color = "pale-green";
	public static final String TrafficSearch_IconGroup = "regular";
	public static final String TrafficSearch_IconName = "newspaper";
	public static final Integer TrafficSearch_Rows = 100;

	//////////////////////
	// trafficSearchKey //
	//////////////////////

	/**	 The entity trafficSearchKey
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long trafficSearchKey;
	@JsonIgnore
	public Wrap<Long> trafficSearchKeyWrap = new Wrap<Long>().p(this).c(Long.class).var("trafficSearchKey").o(trafficSearchKey);

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

	public String jsonTrafficSearchKey() {
		return trafficSearchKey == null ? "" : trafficSearchKey.toString();
	}

	public String nomAffichageTrafficSearchKey() {
		return null;
	}

	public String htmTooltipTrafficSearchKey() {
		return null;
	}

	public String htmTrafficSearchKey() {
		return trafficSearchKey == null ? "" : StringEscapeUtils.escapeHtml4(strTrafficSearchKey());
	}

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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:personKey">Find the entity personKey in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _personKey(Wrap<Long> w);

	public Long getPersonKey() {
		return personKey;
	}

	public void setPersonKey(Long personKey) {
		this.personKey = personKey;
		this.personKeyWrap.alreadyInitialized = true;
	}
	public void setPersonKey(String o) {
		this.personKey = TrafficSearch.staticSetPersonKey(siteRequest_, o);
		this.personKeyWrap.alreadyInitialized = true;
	}
	public static Long staticSetPersonKey(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected TrafficSearch personKeyInit() {
		if(!personKeyWrap.alreadyInitialized) {
			_personKey(personKeyWrap);
			if(personKey == null)
				setPersonKey(personKeyWrap.o);
		}
		personKeyWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Long staticSolrPersonKey(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrPersonKey(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPersonKey(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrPersonKey(siteRequest_, TrafficSearch.staticSolrPersonKey(siteRequest_, TrafficSearch.staticSetPersonKey(siteRequest_, o)));
	}

	public Long solrPersonKey() {
		return TrafficSearch.staticSolrPersonKey(siteRequest_, personKey);
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

	/////////////////////////
	// trafficPersonSearch //
	/////////////////////////

	/**	 The entity trafficPersonSearch
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut SearchList<TrafficPerson>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SearchList<TrafficPerson> trafficPersonSearch = new SearchList<TrafficPerson>();
	@JsonIgnore
	public Wrap<SearchList<TrafficPerson>> trafficPersonSearchWrap = new Wrap<SearchList<TrafficPerson>>().p(this).c(SearchList.class).var("trafficPersonSearch").o(trafficPersonSearch);

	/**	<br/> The entity trafficPersonSearch
	 *  It is constructed before being initialized with the constructor by default SearchList<TrafficPerson>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:trafficPersonSearch">Find the entity trafficPersonSearch in Solr</a>
	 * <br/>
	 * @param trafficPersonSearch is the entity already constructed. 
	 **/
	protected abstract void _trafficPersonSearch(SearchList<TrafficPerson> l);

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
	protected TrafficSearch trafficPersonSearchInit() {
		if(!trafficPersonSearchWrap.alreadyInitialized) {
			_trafficPersonSearch(trafficPersonSearch);
		}
		trafficPersonSearch.initDeepForClass(siteRequest_);
		trafficPersonSearchWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	////////////////////
	// trafficPerson_ //
	////////////////////

	/**	 The entity trafficPerson_
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected TrafficPerson trafficPerson_;
	@JsonIgnore
	public Wrap<TrafficPerson> trafficPerson_Wrap = new Wrap<TrafficPerson>().p(this).c(TrafficPerson.class).var("trafficPerson_").o(trafficPerson_);

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
		}
		trafficPerson_Wrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	////////////////////
	// contrabandKeys //
	////////////////////

	/**	 The entity contrabandKeys
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> contrabandKeys = new ArrayList<Long>();
	@JsonIgnore
	public Wrap<List<Long>> contrabandKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("contrabandKeys").o(contrabandKeys);

	/**	<br/> The entity contrabandKeys
	 *  It is constructed before being initialized with the constructor by default List<Long>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:contrabandKeys">Find the entity contrabandKeys in Solr</a>
	 * <br/>
	 * @param contrabandKeys is the entity already constructed. 
	 **/
	protected abstract void _contrabandKeys(List<Long> w);

	public List<Long> getContrabandKeys() {
		return contrabandKeys;
	}

	public void setContrabandKeys(List<Long> contrabandKeys) {
		this.contrabandKeys = contrabandKeys;
		this.contrabandKeysWrap.alreadyInitialized = true;
	}
	public void setContrabandKeys(String o) {
		Long l = TrafficSearch.staticSetContrabandKeys(siteRequest_, o);
		if(l != null)
			addContrabandKeys(l);
		this.contrabandKeysWrap.alreadyInitialized = true;
	}
	public static Long staticSetContrabandKeys(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	public TrafficSearch addContrabandKeys(Long...objets) {
		for(Long o : objets) {
			addContrabandKeys(o);
		}
		return (TrafficSearch)this;
	}
	public TrafficSearch addContrabandKeys(Long o) {
		if(o != null && !contrabandKeys.contains(o))
			this.contrabandKeys.add(o);
		return (TrafficSearch)this;
	}
	public void setContrabandKeys(JsonArray objets) {
		contrabandKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addContrabandKeys(o);
		}
	}
	public TrafficSearch addContrabandKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addContrabandKeys(p);
		}
		return (TrafficSearch)this;
	}
	protected TrafficSearch contrabandKeysInit() {
		if(!contrabandKeysWrap.alreadyInitialized) {
			_contrabandKeys(contrabandKeys);
		}
		contrabandKeysWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Long staticSolrContrabandKeys(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrContrabandKeys(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqContrabandKeys(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrContrabandKeys(siteRequest_, TrafficSearch.staticSolrContrabandKeys(siteRequest_, TrafficSearch.staticSetContrabandKeys(siteRequest_, o)));
	}

	public List<Long> solrContrabandKeys() {
		List<Long> l = new ArrayList<Long>();
		for(Long o : contrabandKeys) {
			l.add(TrafficSearch.staticSolrContrabandKeys(siteRequest_, o));
		}
		return l;
	}

	public String strContrabandKeys() {
		return contrabandKeys == null ? "" : contrabandKeys.toString();
	}

	public String jsonContrabandKeys() {
		return contrabandKeys == null ? "" : contrabandKeys.toString();
	}

	public String nomAffichageContrabandKeys() {
		return null;
	}

	public String htmTooltipContrabandKeys() {
		return null;
	}

	public String htmContrabandKeys() {
		return contrabandKeys == null ? "" : StringEscapeUtils.escapeHtml4(strContrabandKeys());
	}

	/////////////////////
	// searchBasisKeys //
	/////////////////////

	/**	 The entity searchBasisKeys
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> searchBasisKeys = new ArrayList<Long>();
	@JsonIgnore
	public Wrap<List<Long>> searchBasisKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("searchBasisKeys").o(searchBasisKeys);

	/**	<br/> The entity searchBasisKeys
	 *  It is constructed before being initialized with the constructor by default List<Long>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchBasisKeys">Find the entity searchBasisKeys in Solr</a>
	 * <br/>
	 * @param searchBasisKeys is the entity already constructed. 
	 **/
	protected abstract void _searchBasisKeys(List<Long> w);

	public List<Long> getSearchBasisKeys() {
		return searchBasisKeys;
	}

	public void setSearchBasisKeys(List<Long> searchBasisKeys) {
		this.searchBasisKeys = searchBasisKeys;
		this.searchBasisKeysWrap.alreadyInitialized = true;
	}
	public void setSearchBasisKeys(String o) {
		Long l = TrafficSearch.staticSetSearchBasisKeys(siteRequest_, o);
		if(l != null)
			addSearchBasisKeys(l);
		this.searchBasisKeysWrap.alreadyInitialized = true;
	}
	public static Long staticSetSearchBasisKeys(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	public TrafficSearch addSearchBasisKeys(Long...objets) {
		for(Long o : objets) {
			addSearchBasisKeys(o);
		}
		return (TrafficSearch)this;
	}
	public TrafficSearch addSearchBasisKeys(Long o) {
		if(o != null && !searchBasisKeys.contains(o))
			this.searchBasisKeys.add(o);
		return (TrafficSearch)this;
	}
	public void setSearchBasisKeys(JsonArray objets) {
		searchBasisKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addSearchBasisKeys(o);
		}
	}
	public TrafficSearch addSearchBasisKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addSearchBasisKeys(p);
		}
		return (TrafficSearch)this;
	}
	protected TrafficSearch searchBasisKeysInit() {
		if(!searchBasisKeysWrap.alreadyInitialized) {
			_searchBasisKeys(searchBasisKeys);
		}
		searchBasisKeysWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Long staticSolrSearchBasisKeys(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrSearchBasisKeys(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchBasisKeys(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrSearchBasisKeys(siteRequest_, TrafficSearch.staticSolrSearchBasisKeys(siteRequest_, TrafficSearch.staticSetSearchBasisKeys(siteRequest_, o)));
	}

	public List<Long> solrSearchBasisKeys() {
		List<Long> l = new ArrayList<Long>();
		for(Long o : searchBasisKeys) {
			l.add(TrafficSearch.staticSolrSearchBasisKeys(siteRequest_, o));
		}
		return l;
	}

	public String strSearchBasisKeys() {
		return searchBasisKeys == null ? "" : searchBasisKeys.toString();
	}

	public String jsonSearchBasisKeys() {
		return searchBasisKeys == null ? "" : searchBasisKeys.toString();
	}

	public String nomAffichageSearchBasisKeys() {
		return null;
	}

	public String htmTooltipSearchBasisKeys() {
		return null;
	}

	public String htmSearchBasisKeys() {
		return searchBasisKeys == null ? "" : StringEscapeUtils.escapeHtml4(strSearchBasisKeys());
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stopAgencyTitle">Find the entity stopAgencyTitle in Solr</a>
	 * <br/>
	 * @param w is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stopAgencyTitle(Wrap<String> w);

	public String getStopAgencyTitle() {
		return stopAgencyTitle;
	}
	public void setStopAgencyTitle(String o) {
		this.stopAgencyTitle = TrafficSearch.staticSetStopAgencyTitle(siteRequest_, o);
		this.stopAgencyTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetStopAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficSearch stopAgencyTitleInit() {
		if(!stopAgencyTitleWrap.alreadyInitialized) {
			_stopAgencyTitle(stopAgencyTitleWrap);
			if(stopAgencyTitle == null)
				setStopAgencyTitle(stopAgencyTitleWrap.o);
		}
		stopAgencyTitleWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static String staticSolrStopAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStopAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStopAgencyTitle(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrStopAgencyTitle(siteRequest_, TrafficSearch.staticSolrStopAgencyTitle(siteRequest_, TrafficSearch.staticSetStopAgencyTitle(siteRequest_, o)));
	}

	public String solrStopAgencyTitle() {
		return TrafficSearch.staticSolrStopAgencyTitle(siteRequest_, stopAgencyTitle);
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
	public void setStopDateTime(Instant o) {
		this.stopDateTime = o == null ? null : ZonedDateTime.from(o).truncatedTo(ChronoUnit.MILLIS);
		this.stopDateTimeWrap.alreadyInitialized = true;
	}
	/** Example: 2011-12-03T10:15:30+01:00 **/
	public void setStopDateTime(String o) {
		this.stopDateTime = TrafficSearch.staticSetStopDateTime(siteRequest_, o);
		this.stopDateTimeWrap.alreadyInitialized = true;
	}
	public static ZonedDateTime staticSetStopDateTime(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : ZonedDateTime.parse(o, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone()))).truncatedTo(ChronoUnit.MILLIS);
	}
	public void setStopDateTime(Date o) {
		this.stopDateTime = o == null ? null : ZonedDateTime.ofInstant(o.toInstant(), ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).truncatedTo(ChronoUnit.MILLIS);
		this.stopDateTimeWrap.alreadyInitialized = true;
	}
	protected TrafficSearch stopDateTimeInit() {
		if(!stopDateTimeWrap.alreadyInitialized) {
			_stopDateTime(stopDateTimeWrap);
			if(stopDateTime == null)
				setStopDateTime(stopDateTimeWrap.o);
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:trafficStopKey">Find the entity trafficStopKey in Solr</a>
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
		this.trafficStopKey = TrafficSearch.staticSetTrafficStopKey(siteRequest_, o);
		this.trafficStopKeyWrap.alreadyInitialized = true;
	}
	public static Long staticSetTrafficStopKey(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected TrafficSearch trafficStopKeyInit() {
		if(!trafficStopKeyWrap.alreadyInitialized) {
			_trafficStopKey(trafficStopKeyWrap);
			if(trafficStopKey == null)
				setTrafficStopKey(trafficStopKeyWrap.o);
		}
		trafficStopKeyWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static Long staticSolrTrafficStopKey(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrTrafficStopKey(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqTrafficStopKey(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrTrafficStopKey(siteRequest_, TrafficSearch.staticSolrTrafficStopKey(siteRequest_, TrafficSearch.staticSetTrafficStopKey(siteRequest_, o)));
	}

	public Long solrTrafficStopKey() {
		return TrafficSearch.staticSolrTrafficStopKey(siteRequest_, trafficStopKey);
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

	public String jsonSearchTypeNum() {
		return searchTypeNum == null ? "" : searchTypeNum.toString();
	}

	public String nomAffichageSearchTypeNum() {
		return null;
	}

	public String htmTooltipSearchTypeNum() {
		return null;
	}

	public String htmSearchTypeNum() {
		return searchTypeNum == null ? "" : StringEscapeUtils.escapeHtml4(strSearchTypeNum());
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

	public String jsonSearchTypeTitle() {
		return searchTypeTitle == null ? "" : searchTypeTitle;
	}

	public String nomAffichageSearchTypeTitle() {
		return null;
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

	public String jsonSearchVehicle() {
		return searchVehicle == null ? "" : searchVehicle.toString();
	}

	public String nomAffichageSearchVehicle() {
		return null;
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

	public String jsonSearchDriver() {
		return searchDriver == null ? "" : searchDriver.toString();
	}

	public String nomAffichageSearchDriver() {
		return null;
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

	public String jsonSearchPassenger() {
		return searchPassenger == null ? "" : searchPassenger.toString();
	}

	public String nomAffichageSearchPassenger() {
		return null;
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

	public String jsonSearchProperty() {
		return searchProperty == null ? "" : searchProperty.toString();
	}

	public String nomAffichageSearchProperty() {
		return null;
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

	public String jsonSearchVehicleSiezed() {
		return searchVehicleSiezed == null ? "" : searchVehicleSiezed.toString();
	}

	public String nomAffichageSearchVehicleSiezed() {
		return null;
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

	public String jsonSearchPersonalPropertySiezed() {
		return searchPersonalPropertySiezed == null ? "" : searchPersonalPropertySiezed.toString();
	}

	public String nomAffichageSearchPersonalPropertySiezed() {
		return null;
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

	public String jsonSearchOtherPropertySiezed() {
		return searchOtherPropertySiezed == null ? "" : searchOtherPropertySiezed.toString();
	}

	public String nomAffichageSearchOtherPropertySiezed() {
		return null;
	}

	public String htmTooltipSearchOtherPropertySiezed() {
		return null;
	}

	public String htmSearchOtherPropertySiezed() {
		return searchOtherPropertySiezed == null ? "" : StringEscapeUtils.escapeHtml4(strSearchOtherPropertySiezed());
	}

	///////////////////////////////
	// trafficSearchCompleteName //
	///////////////////////////////

	/**	 The entity trafficSearchCompleteName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String trafficSearchCompleteName;
	@JsonIgnore
	public Wrap<String> trafficSearchCompleteNameWrap = new Wrap<String>().p(this).c(String.class).var("trafficSearchCompleteName").o(trafficSearchCompleteName);

	/**	<br/> The entity trafficSearchCompleteName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.trafficsearch.TrafficSearch&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:trafficSearchCompleteName">Find the entity trafficSearchCompleteName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _trafficSearchCompleteName(Wrap<String> c);

	public String getTrafficSearchCompleteName() {
		return trafficSearchCompleteName;
	}
	public void setTrafficSearchCompleteName(String o) {
		this.trafficSearchCompleteName = TrafficSearch.staticSetTrafficSearchCompleteName(siteRequest_, o);
		this.trafficSearchCompleteNameWrap.alreadyInitialized = true;
	}
	public static String staticSetTrafficSearchCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected TrafficSearch trafficSearchCompleteNameInit() {
		if(!trafficSearchCompleteNameWrap.alreadyInitialized) {
			_trafficSearchCompleteName(trafficSearchCompleteNameWrap);
			if(trafficSearchCompleteName == null)
				setTrafficSearchCompleteName(trafficSearchCompleteNameWrap.o);
		}
		trafficSearchCompleteNameWrap.alreadyInitialized(true);
		return (TrafficSearch)this;
	}

	public static String staticSolrTrafficSearchCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrTrafficSearchCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqTrafficSearchCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return TrafficSearch.staticSolrStrTrafficSearchCompleteName(siteRequest_, TrafficSearch.staticSolrTrafficSearchCompleteName(siteRequest_, TrafficSearch.staticSetTrafficSearchCompleteName(siteRequest_, o)));
	}

	public String solrTrafficSearchCompleteName() {
		return TrafficSearch.staticSolrTrafficSearchCompleteName(siteRequest_, trafficSearchCompleteName);
	}

	public String strTrafficSearchCompleteName() {
		return trafficSearchCompleteName == null ? "" : trafficSearchCompleteName;
	}

	public String jsonTrafficSearchCompleteName() {
		return trafficSearchCompleteName == null ? "" : trafficSearchCompleteName;
	}

	public String nomAffichageTrafficSearchCompleteName() {
		return null;
	}

	public String htmTooltipTrafficSearchCompleteName() {
		return null;
	}

	public String htmTrafficSearchCompleteName() {
		return trafficSearchCompleteName == null ? "" : StringEscapeUtils.escapeHtml4(strTrafficSearchCompleteName());
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedTrafficSearch = false;

	public TrafficSearch initDeepTrafficSearch(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedTrafficSearch) {
			alreadyInitializedTrafficSearch = true;
			initDeepTrafficSearch();
		}
		return (TrafficSearch)this;
	}

	public void initDeepTrafficSearch() {
		initTrafficSearch();
		super.initDeepCluster(siteRequest_);
	}

	public void initTrafficSearch() {
		trafficSearchKeyInit();
		personKeyInit();
		trafficPersonSearchInit();
		trafficPerson_Init();
		contrabandKeysInit();
		searchBasisKeysInit();
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
		trafficSearchCompleteNameInit();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepTrafficSearch(siteRequest_);
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
		}
		return o;
	}
	public Object obtainTrafficSearch(String var) {
		TrafficSearch oTrafficSearch = (TrafficSearch)this;
		switch(var) {
			case "trafficSearchKey":
				return oTrafficSearch.trafficSearchKey;
			case "personKey":
				return oTrafficSearch.personKey;
			case "trafficPersonSearch":
				return oTrafficSearch.trafficPersonSearch;
			case "trafficPerson_":
				return oTrafficSearch.trafficPerson_;
			case "contrabandKeys":
				return oTrafficSearch.contrabandKeys;
			case "searchBasisKeys":
				return oTrafficSearch.searchBasisKeys;
			case "stopAgencyTitle":
				return oTrafficSearch.stopAgencyTitle;
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
			case "trafficStopKey":
				return oTrafficSearch.trafficStopKey;
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
			case "trafficSearchCompleteName":
				return oTrafficSearch.trafficSearchCompleteName;
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
		case "personKey":
			return TrafficSearch.staticSetPersonKey(siteRequest_, o);
		case "contrabandKeys":
			return TrafficSearch.staticSetContrabandKeys(siteRequest_, o);
		case "searchBasisKeys":
			return TrafficSearch.staticSetSearchBasisKeys(siteRequest_, o);
		case "stopAgencyTitle":
			return TrafficSearch.staticSetStopAgencyTitle(siteRequest_, o);
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
		case "trafficStopKey":
			return TrafficSearch.staticSetTrafficStopKey(siteRequest_, o);
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
		case "trafficSearchCompleteName":
			return TrafficSearch.staticSetTrafficSearchCompleteName(siteRequest_, o);
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
		case "personKey":
			return TrafficSearch.staticSolrPersonKey(siteRequest_, (Long)o);
		case "contrabandKeys":
			return TrafficSearch.staticSolrContrabandKeys(siteRequest_, (Long)o);
		case "searchBasisKeys":
			return TrafficSearch.staticSolrSearchBasisKeys(siteRequest_, (Long)o);
		case "stopAgencyTitle":
			return TrafficSearch.staticSolrStopAgencyTitle(siteRequest_, (String)o);
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
		case "trafficStopKey":
			return TrafficSearch.staticSolrTrafficStopKey(siteRequest_, (Long)o);
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
		case "trafficSearchCompleteName":
			return TrafficSearch.staticSolrTrafficSearchCompleteName(siteRequest_, (String)o);
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
		case "personKey":
			return TrafficSearch.staticSolrStrPersonKey(siteRequest_, (Long)o);
		case "contrabandKeys":
			return TrafficSearch.staticSolrStrContrabandKeys(siteRequest_, (Long)o);
		case "searchBasisKeys":
			return TrafficSearch.staticSolrStrSearchBasisKeys(siteRequest_, (Long)o);
		case "stopAgencyTitle":
			return TrafficSearch.staticSolrStrStopAgencyTitle(siteRequest_, (String)o);
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
		case "trafficStopKey":
			return TrafficSearch.staticSolrStrTrafficStopKey(siteRequest_, (Long)o);
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
		case "trafficSearchCompleteName":
			return TrafficSearch.staticSolrStrTrafficSearchCompleteName(siteRequest_, (String)o);
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
		case "personKey":
			return TrafficSearch.staticSolrFqPersonKey(siteRequest_, o);
		case "contrabandKeys":
			return TrafficSearch.staticSolrFqContrabandKeys(siteRequest_, o);
		case "searchBasisKeys":
			return TrafficSearch.staticSolrFqSearchBasisKeys(siteRequest_, o);
		case "stopAgencyTitle":
			return TrafficSearch.staticSolrFqStopAgencyTitle(siteRequest_, o);
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
		case "trafficStopKey":
			return TrafficSearch.staticSolrFqTrafficStopKey(siteRequest_, o);
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
		case "trafficSearchCompleteName":
			return TrafficSearch.staticSolrFqTrafficSearchCompleteName(siteRequest_, o);
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
		switch(var) {
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
			solrQuery.addFilterQuery("id:" + ClientUtils.escapeQueryChars("com.opendatapolicing.enus.trafficsearch.TrafficSearch"));
			QueryResponse queryResponse = siteRequest.getSiteContext_().getSolrClient().query(solrQuery);
			if(queryResponse.getResults().size() > 0)
				siteRequest.setSolrDocument(queryResponse.getResults().get(0));
			TrafficSearch o = new TrafficSearch();
			o.siteRequestTrafficSearch(siteRequest);
			o.initDeepTrafficSearch(siteRequest);
			o.indexTrafficSearch();
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}


	@Override public void indexForClass() {
		indexTrafficSearch();
	}

	@Override public void indexForClass(SolrInputDocument document) {
		indexTrafficSearch(document);
	}

	public void indexTrafficSearch(SolrClient clientSolr) {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexTrafficSearch(document);
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexTrafficSearch() {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexTrafficSearch(document);
			SolrClient clientSolr = siteRequest_.getSiteContext_().getSolrClient();
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexTrafficSearch(SolrInputDocument document) {
		super.indexCluster(document);

	}

	public static String varIndexedTrafficSearch(String entityVar) {
		switch(entityVar) {
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
		if(!(o instanceof TrafficSearch))
			return false;
		TrafficSearch that = (TrafficSearch)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("TrafficSearch { ");
		sb.append(" }");
		return sb.toString();
	}
}
