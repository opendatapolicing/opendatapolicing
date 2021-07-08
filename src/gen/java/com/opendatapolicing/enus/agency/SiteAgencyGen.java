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

	/////////////////
	// agencyTotal //
	/////////////////

	/**	 The entity agencyTotal
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long agencyTotal;
	@JsonIgnore
	public Wrap<Long> agencyTotalWrap = new Wrap<Long>().var("agencyTotal").o(agencyTotal);

	/**	<br/> The entity agencyTotal
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:agencyTotal">Find the entity agencyTotal in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _agencyTotal(Wrap<Long> c);

	public Long getAgencyTotal() {
		return agencyTotal;
	}

	public void setAgencyTotal(Long agencyTotal) {
		this.agencyTotal = agencyTotal;
		this.agencyTotalWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setAgencyTotal(String o) {
		this.agencyTotal = SiteAgency.staticSetAgencyTotal(siteRequest_, o);
		this.agencyTotalWrap.alreadyInitialized = true;
	}
	public static Long staticSetAgencyTotal(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected SiteAgency agencyTotalInit() {
		if(!agencyTotalWrap.alreadyInitialized) {
			_agencyTotal(agencyTotalWrap);
			if(agencyTotal == null)
				setAgencyTotal(agencyTotalWrap.o);
			agencyTotalWrap.o(null);
		}
		agencyTotalWrap.alreadyInitialized(true);
		return (SiteAgency)this;
	}

	public static Long staticSolrAgencyTotal(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrAgencyTotal(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqAgencyTotal(SiteRequestEnUS siteRequest_, String o) {
		return SiteAgency.staticSolrStrAgencyTotal(siteRequest_, SiteAgency.staticSolrAgencyTotal(siteRequest_, SiteAgency.staticSetAgencyTotal(siteRequest_, o)));
	}

	public Long solrAgencyTotal() {
		return SiteAgency.staticSolrAgencyTotal(siteRequest_, agencyTotal);
	}

	public String strAgencyTotal() {
		return agencyTotal == null ? "" : agencyTotal.toString();
	}

	public Long sqlAgencyTotal() {
		return agencyTotal;
	}

	public String jsonAgencyTotal() {
		return agencyTotal == null ? "" : agencyTotal.toString();
	}

	//////////////////////
	// agencyTotalWhite //
	//////////////////////

	/**	 The entity agencyTotalWhite
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long agencyTotalWhite;
	@JsonIgnore
	public Wrap<Long> agencyTotalWhiteWrap = new Wrap<Long>().var("agencyTotalWhite").o(agencyTotalWhite);

	/**	<br/> The entity agencyTotalWhite
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:agencyTotalWhite">Find the entity agencyTotalWhite in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _agencyTotalWhite(Wrap<Long> c);

	public Long getAgencyTotalWhite() {
		return agencyTotalWhite;
	}

	public void setAgencyTotalWhite(Long agencyTotalWhite) {
		this.agencyTotalWhite = agencyTotalWhite;
		this.agencyTotalWhiteWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setAgencyTotalWhite(String o) {
		this.agencyTotalWhite = SiteAgency.staticSetAgencyTotalWhite(siteRequest_, o);
		this.agencyTotalWhiteWrap.alreadyInitialized = true;
	}
	public static Long staticSetAgencyTotalWhite(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected SiteAgency agencyTotalWhiteInit() {
		if(!agencyTotalWhiteWrap.alreadyInitialized) {
			_agencyTotalWhite(agencyTotalWhiteWrap);
			if(agencyTotalWhite == null)
				setAgencyTotalWhite(agencyTotalWhiteWrap.o);
			agencyTotalWhiteWrap.o(null);
		}
		agencyTotalWhiteWrap.alreadyInitialized(true);
		return (SiteAgency)this;
	}

	public static Long staticSolrAgencyTotalWhite(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrAgencyTotalWhite(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqAgencyTotalWhite(SiteRequestEnUS siteRequest_, String o) {
		return SiteAgency.staticSolrStrAgencyTotalWhite(siteRequest_, SiteAgency.staticSolrAgencyTotalWhite(siteRequest_, SiteAgency.staticSetAgencyTotalWhite(siteRequest_, o)));
	}

	public Long solrAgencyTotalWhite() {
		return SiteAgency.staticSolrAgencyTotalWhite(siteRequest_, agencyTotalWhite);
	}

	public String strAgencyTotalWhite() {
		return agencyTotalWhite == null ? "" : agencyTotalWhite.toString();
	}

	public Long sqlAgencyTotalWhite() {
		return agencyTotalWhite;
	}

	public String jsonAgencyTotalWhite() {
		return agencyTotalWhite == null ? "" : agencyTotalWhite.toString();
	}

	//////////////////////
	// agencyTotalBlack //
	//////////////////////

	/**	 The entity agencyTotalBlack
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long agencyTotalBlack;
	@JsonIgnore
	public Wrap<Long> agencyTotalBlackWrap = new Wrap<Long>().var("agencyTotalBlack").o(agencyTotalBlack);

	/**	<br/> The entity agencyTotalBlack
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:agencyTotalBlack">Find the entity agencyTotalBlack in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _agencyTotalBlack(Wrap<Long> c);

	public Long getAgencyTotalBlack() {
		return agencyTotalBlack;
	}

	public void setAgencyTotalBlack(Long agencyTotalBlack) {
		this.agencyTotalBlack = agencyTotalBlack;
		this.agencyTotalBlackWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setAgencyTotalBlack(String o) {
		this.agencyTotalBlack = SiteAgency.staticSetAgencyTotalBlack(siteRequest_, o);
		this.agencyTotalBlackWrap.alreadyInitialized = true;
	}
	public static Long staticSetAgencyTotalBlack(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected SiteAgency agencyTotalBlackInit() {
		if(!agencyTotalBlackWrap.alreadyInitialized) {
			_agencyTotalBlack(agencyTotalBlackWrap);
			if(agencyTotalBlack == null)
				setAgencyTotalBlack(agencyTotalBlackWrap.o);
			agencyTotalBlackWrap.o(null);
		}
		agencyTotalBlackWrap.alreadyInitialized(true);
		return (SiteAgency)this;
	}

	public static Long staticSolrAgencyTotalBlack(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrAgencyTotalBlack(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqAgencyTotalBlack(SiteRequestEnUS siteRequest_, String o) {
		return SiteAgency.staticSolrStrAgencyTotalBlack(siteRequest_, SiteAgency.staticSolrAgencyTotalBlack(siteRequest_, SiteAgency.staticSetAgencyTotalBlack(siteRequest_, o)));
	}

	public Long solrAgencyTotalBlack() {
		return SiteAgency.staticSolrAgencyTotalBlack(siteRequest_, agencyTotalBlack);
	}

	public String strAgencyTotalBlack() {
		return agencyTotalBlack == null ? "" : agencyTotalBlack.toString();
	}

	public Long sqlAgencyTotalBlack() {
		return agencyTotalBlack;
	}

	public String jsonAgencyTotalBlack() {
		return agencyTotalBlack == null ? "" : agencyTotalBlack.toString();
	}

	///////////////////////////
	// agencyTotalIndigenous //
	///////////////////////////

	/**	 The entity agencyTotalIndigenous
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long agencyTotalIndigenous;
	@JsonIgnore
	public Wrap<Long> agencyTotalIndigenousWrap = new Wrap<Long>().var("agencyTotalIndigenous").o(agencyTotalIndigenous);

	/**	<br/> The entity agencyTotalIndigenous
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:agencyTotalIndigenous">Find the entity agencyTotalIndigenous in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _agencyTotalIndigenous(Wrap<Long> c);

	public Long getAgencyTotalIndigenous() {
		return agencyTotalIndigenous;
	}

	public void setAgencyTotalIndigenous(Long agencyTotalIndigenous) {
		this.agencyTotalIndigenous = agencyTotalIndigenous;
		this.agencyTotalIndigenousWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setAgencyTotalIndigenous(String o) {
		this.agencyTotalIndigenous = SiteAgency.staticSetAgencyTotalIndigenous(siteRequest_, o);
		this.agencyTotalIndigenousWrap.alreadyInitialized = true;
	}
	public static Long staticSetAgencyTotalIndigenous(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected SiteAgency agencyTotalIndigenousInit() {
		if(!agencyTotalIndigenousWrap.alreadyInitialized) {
			_agencyTotalIndigenous(agencyTotalIndigenousWrap);
			if(agencyTotalIndigenous == null)
				setAgencyTotalIndigenous(agencyTotalIndigenousWrap.o);
			agencyTotalIndigenousWrap.o(null);
		}
		agencyTotalIndigenousWrap.alreadyInitialized(true);
		return (SiteAgency)this;
	}

	public static Long staticSolrAgencyTotalIndigenous(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrAgencyTotalIndigenous(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqAgencyTotalIndigenous(SiteRequestEnUS siteRequest_, String o) {
		return SiteAgency.staticSolrStrAgencyTotalIndigenous(siteRequest_, SiteAgency.staticSolrAgencyTotalIndigenous(siteRequest_, SiteAgency.staticSetAgencyTotalIndigenous(siteRequest_, o)));
	}

	public Long solrAgencyTotalIndigenous() {
		return SiteAgency.staticSolrAgencyTotalIndigenous(siteRequest_, agencyTotalIndigenous);
	}

	public String strAgencyTotalIndigenous() {
		return agencyTotalIndigenous == null ? "" : agencyTotalIndigenous.toString();
	}

	public Long sqlAgencyTotalIndigenous() {
		return agencyTotalIndigenous;
	}

	public String jsonAgencyTotalIndigenous() {
		return agencyTotalIndigenous == null ? "" : agencyTotalIndigenous.toString();
	}

	//////////////////////
	// agencyTotalAsian //
	//////////////////////

	/**	 The entity agencyTotalAsian
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long agencyTotalAsian;
	@JsonIgnore
	public Wrap<Long> agencyTotalAsianWrap = new Wrap<Long>().var("agencyTotalAsian").o(agencyTotalAsian);

	/**	<br/> The entity agencyTotalAsian
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:agencyTotalAsian">Find the entity agencyTotalAsian in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _agencyTotalAsian(Wrap<Long> c);

	public Long getAgencyTotalAsian() {
		return agencyTotalAsian;
	}

	public void setAgencyTotalAsian(Long agencyTotalAsian) {
		this.agencyTotalAsian = agencyTotalAsian;
		this.agencyTotalAsianWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setAgencyTotalAsian(String o) {
		this.agencyTotalAsian = SiteAgency.staticSetAgencyTotalAsian(siteRequest_, o);
		this.agencyTotalAsianWrap.alreadyInitialized = true;
	}
	public static Long staticSetAgencyTotalAsian(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected SiteAgency agencyTotalAsianInit() {
		if(!agencyTotalAsianWrap.alreadyInitialized) {
			_agencyTotalAsian(agencyTotalAsianWrap);
			if(agencyTotalAsian == null)
				setAgencyTotalAsian(agencyTotalAsianWrap.o);
			agencyTotalAsianWrap.o(null);
		}
		agencyTotalAsianWrap.alreadyInitialized(true);
		return (SiteAgency)this;
	}

	public static Long staticSolrAgencyTotalAsian(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrAgencyTotalAsian(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqAgencyTotalAsian(SiteRequestEnUS siteRequest_, String o) {
		return SiteAgency.staticSolrStrAgencyTotalAsian(siteRequest_, SiteAgency.staticSolrAgencyTotalAsian(siteRequest_, SiteAgency.staticSetAgencyTotalAsian(siteRequest_, o)));
	}

	public Long solrAgencyTotalAsian() {
		return SiteAgency.staticSolrAgencyTotalAsian(siteRequest_, agencyTotalAsian);
	}

	public String strAgencyTotalAsian() {
		return agencyTotalAsian == null ? "" : agencyTotalAsian.toString();
	}

	public Long sqlAgencyTotalAsian() {
		return agencyTotalAsian;
	}

	public String jsonAgencyTotalAsian() {
		return agencyTotalAsian == null ? "" : agencyTotalAsian.toString();
	}

	////////////////////////////////
	// agencyTotalPacificIslander //
	////////////////////////////////

	/**	 The entity agencyTotalPacificIslander
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long agencyTotalPacificIslander;
	@JsonIgnore
	public Wrap<Long> agencyTotalPacificIslanderWrap = new Wrap<Long>().var("agencyTotalPacificIslander").o(agencyTotalPacificIslander);

	/**	<br/> The entity agencyTotalPacificIslander
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:agencyTotalPacificIslander">Find the entity agencyTotalPacificIslander in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _agencyTotalPacificIslander(Wrap<Long> c);

	public Long getAgencyTotalPacificIslander() {
		return agencyTotalPacificIslander;
	}

	public void setAgencyTotalPacificIslander(Long agencyTotalPacificIslander) {
		this.agencyTotalPacificIslander = agencyTotalPacificIslander;
		this.agencyTotalPacificIslanderWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setAgencyTotalPacificIslander(String o) {
		this.agencyTotalPacificIslander = SiteAgency.staticSetAgencyTotalPacificIslander(siteRequest_, o);
		this.agencyTotalPacificIslanderWrap.alreadyInitialized = true;
	}
	public static Long staticSetAgencyTotalPacificIslander(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected SiteAgency agencyTotalPacificIslanderInit() {
		if(!agencyTotalPacificIslanderWrap.alreadyInitialized) {
			_agencyTotalPacificIslander(agencyTotalPacificIslanderWrap);
			if(agencyTotalPacificIslander == null)
				setAgencyTotalPacificIslander(agencyTotalPacificIslanderWrap.o);
			agencyTotalPacificIslanderWrap.o(null);
		}
		agencyTotalPacificIslanderWrap.alreadyInitialized(true);
		return (SiteAgency)this;
	}

	public static Long staticSolrAgencyTotalPacificIslander(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrAgencyTotalPacificIslander(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqAgencyTotalPacificIslander(SiteRequestEnUS siteRequest_, String o) {
		return SiteAgency.staticSolrStrAgencyTotalPacificIslander(siteRequest_, SiteAgency.staticSolrAgencyTotalPacificIslander(siteRequest_, SiteAgency.staticSetAgencyTotalPacificIslander(siteRequest_, o)));
	}

	public Long solrAgencyTotalPacificIslander() {
		return SiteAgency.staticSolrAgencyTotalPacificIslander(siteRequest_, agencyTotalPacificIslander);
	}

	public String strAgencyTotalPacificIslander() {
		return agencyTotalPacificIslander == null ? "" : agencyTotalPacificIslander.toString();
	}

	public Long sqlAgencyTotalPacificIslander() {
		return agencyTotalPacificIslander;
	}

	public String jsonAgencyTotalPacificIslander() {
		return agencyTotalPacificIslander == null ? "" : agencyTotalPacificIslander.toString();
	}

	///////////////////////
	// agencyTotalLatinx //
	///////////////////////

	/**	 The entity agencyTotalLatinx
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long agencyTotalLatinx;
	@JsonIgnore
	public Wrap<Long> agencyTotalLatinxWrap = new Wrap<Long>().var("agencyTotalLatinx").o(agencyTotalLatinx);

	/**	<br/> The entity agencyTotalLatinx
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:agencyTotalLatinx">Find the entity agencyTotalLatinx in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _agencyTotalLatinx(Wrap<Long> c);

	public Long getAgencyTotalLatinx() {
		return agencyTotalLatinx;
	}

	public void setAgencyTotalLatinx(Long agencyTotalLatinx) {
		this.agencyTotalLatinx = agencyTotalLatinx;
		this.agencyTotalLatinxWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setAgencyTotalLatinx(String o) {
		this.agencyTotalLatinx = SiteAgency.staticSetAgencyTotalLatinx(siteRequest_, o);
		this.agencyTotalLatinxWrap.alreadyInitialized = true;
	}
	public static Long staticSetAgencyTotalLatinx(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected SiteAgency agencyTotalLatinxInit() {
		if(!agencyTotalLatinxWrap.alreadyInitialized) {
			_agencyTotalLatinx(agencyTotalLatinxWrap);
			if(agencyTotalLatinx == null)
				setAgencyTotalLatinx(agencyTotalLatinxWrap.o);
			agencyTotalLatinxWrap.o(null);
		}
		agencyTotalLatinxWrap.alreadyInitialized(true);
		return (SiteAgency)this;
	}

	public static Long staticSolrAgencyTotalLatinx(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrAgencyTotalLatinx(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqAgencyTotalLatinx(SiteRequestEnUS siteRequest_, String o) {
		return SiteAgency.staticSolrStrAgencyTotalLatinx(siteRequest_, SiteAgency.staticSolrAgencyTotalLatinx(siteRequest_, SiteAgency.staticSetAgencyTotalLatinx(siteRequest_, o)));
	}

	public Long solrAgencyTotalLatinx() {
		return SiteAgency.staticSolrAgencyTotalLatinx(siteRequest_, agencyTotalLatinx);
	}

	public String strAgencyTotalLatinx() {
		return agencyTotalLatinx == null ? "" : agencyTotalLatinx.toString();
	}

	public Long sqlAgencyTotalLatinx() {
		return agencyTotalLatinx;
	}

	public String jsonAgencyTotalLatinx() {
		return agencyTotalLatinx == null ? "" : agencyTotalLatinx.toString();
	}

	////////////////////////////
	// agencyTotalMultiracial //
	////////////////////////////

	/**	 The entity agencyTotalMultiracial
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long agencyTotalMultiracial;
	@JsonIgnore
	public Wrap<Long> agencyTotalMultiracialWrap = new Wrap<Long>().var("agencyTotalMultiracial").o(agencyTotalMultiracial);

	/**	<br/> The entity agencyTotalMultiracial
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:agencyTotalMultiracial">Find the entity agencyTotalMultiracial in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _agencyTotalMultiracial(Wrap<Long> c);

	public Long getAgencyTotalMultiracial() {
		return agencyTotalMultiracial;
	}

	public void setAgencyTotalMultiracial(Long agencyTotalMultiracial) {
		this.agencyTotalMultiracial = agencyTotalMultiracial;
		this.agencyTotalMultiracialWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setAgencyTotalMultiracial(String o) {
		this.agencyTotalMultiracial = SiteAgency.staticSetAgencyTotalMultiracial(siteRequest_, o);
		this.agencyTotalMultiracialWrap.alreadyInitialized = true;
	}
	public static Long staticSetAgencyTotalMultiracial(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected SiteAgency agencyTotalMultiracialInit() {
		if(!agencyTotalMultiracialWrap.alreadyInitialized) {
			_agencyTotalMultiracial(agencyTotalMultiracialWrap);
			if(agencyTotalMultiracial == null)
				setAgencyTotalMultiracial(agencyTotalMultiracialWrap.o);
			agencyTotalMultiracialWrap.o(null);
		}
		agencyTotalMultiracialWrap.alreadyInitialized(true);
		return (SiteAgency)this;
	}

	public static Long staticSolrAgencyTotalMultiracial(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrAgencyTotalMultiracial(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqAgencyTotalMultiracial(SiteRequestEnUS siteRequest_, String o) {
		return SiteAgency.staticSolrStrAgencyTotalMultiracial(siteRequest_, SiteAgency.staticSolrAgencyTotalMultiracial(siteRequest_, SiteAgency.staticSetAgencyTotalMultiracial(siteRequest_, o)));
	}

	public Long solrAgencyTotalMultiracial() {
		return SiteAgency.staticSolrAgencyTotalMultiracial(siteRequest_, agencyTotalMultiracial);
	}

	public String strAgencyTotalMultiracial() {
		return agencyTotalMultiracial == null ? "" : agencyTotalMultiracial.toString();
	}

	public Long sqlAgencyTotalMultiracial() {
		return agencyTotalMultiracial;
	}

	public String jsonAgencyTotalMultiracial() {
		return agencyTotalMultiracial == null ? "" : agencyTotalMultiracial.toString();
	}

	//////////////////////
	// agencyTotalOther //
	//////////////////////

	/**	 The entity agencyTotalOther
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long agencyTotalOther;
	@JsonIgnore
	public Wrap<Long> agencyTotalOtherWrap = new Wrap<Long>().var("agencyTotalOther").o(agencyTotalOther);

	/**	<br/> The entity agencyTotalOther
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.agency.SiteAgency&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:agencyTotalOther">Find the entity agencyTotalOther in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _agencyTotalOther(Wrap<Long> c);

	public Long getAgencyTotalOther() {
		return agencyTotalOther;
	}

	public void setAgencyTotalOther(Long agencyTotalOther) {
		this.agencyTotalOther = agencyTotalOther;
		this.agencyTotalOtherWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setAgencyTotalOther(String o) {
		this.agencyTotalOther = SiteAgency.staticSetAgencyTotalOther(siteRequest_, o);
		this.agencyTotalOtherWrap.alreadyInitialized = true;
	}
	public static Long staticSetAgencyTotalOther(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected SiteAgency agencyTotalOtherInit() {
		if(!agencyTotalOtherWrap.alreadyInitialized) {
			_agencyTotalOther(agencyTotalOtherWrap);
			if(agencyTotalOther == null)
				setAgencyTotalOther(agencyTotalOtherWrap.o);
			agencyTotalOtherWrap.o(null);
		}
		agencyTotalOtherWrap.alreadyInitialized(true);
		return (SiteAgency)this;
	}

	public static Long staticSolrAgencyTotalOther(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrAgencyTotalOther(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqAgencyTotalOther(SiteRequestEnUS siteRequest_, String o) {
		return SiteAgency.staticSolrStrAgencyTotalOther(siteRequest_, SiteAgency.staticSolrAgencyTotalOther(siteRequest_, SiteAgency.staticSetAgencyTotalOther(siteRequest_, o)));
	}

	public Long solrAgencyTotalOther() {
		return SiteAgency.staticSolrAgencyTotalOther(siteRequest_, agencyTotalOther);
	}

	public String strAgencyTotalOther() {
		return agencyTotalOther == null ? "" : agencyTotalOther.toString();
	}

	public Long sqlAgencyTotalOther() {
		return agencyTotalOther;
	}

	public String jsonAgencyTotalOther() {
		return agencyTotalOther == null ? "" : agencyTotalOther.toString();
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
				agencyTotalInit();
				agencyTotalWhiteInit();
				agencyTotalBlackInit();
				agencyTotalIndigenousInit();
				agencyTotalAsianInit();
				agencyTotalPacificIslanderInit();
				agencyTotalLatinxInit();
				agencyTotalMultiracialInit();
				agencyTotalOtherInit();
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
			case "agencyTotal":
				return oSiteAgency.agencyTotal;
			case "agencyTotalWhite":
				return oSiteAgency.agencyTotalWhite;
			case "agencyTotalBlack":
				return oSiteAgency.agencyTotalBlack;
			case "agencyTotalIndigenous":
				return oSiteAgency.agencyTotalIndigenous;
			case "agencyTotalAsian":
				return oSiteAgency.agencyTotalAsian;
			case "agencyTotalPacificIslander":
				return oSiteAgency.agencyTotalPacificIslander;
			case "agencyTotalLatinx":
				return oSiteAgency.agencyTotalLatinx;
			case "agencyTotalMultiracial":
				return oSiteAgency.agencyTotalMultiracial;
			case "agencyTotalOther":
				return oSiteAgency.agencyTotalOther;
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
		case "agencyTotal":
			return SiteAgency.staticSetAgencyTotal(siteRequest_, o);
		case "agencyTotalWhite":
			return SiteAgency.staticSetAgencyTotalWhite(siteRequest_, o);
		case "agencyTotalBlack":
			return SiteAgency.staticSetAgencyTotalBlack(siteRequest_, o);
		case "agencyTotalIndigenous":
			return SiteAgency.staticSetAgencyTotalIndigenous(siteRequest_, o);
		case "agencyTotalAsian":
			return SiteAgency.staticSetAgencyTotalAsian(siteRequest_, o);
		case "agencyTotalPacificIslander":
			return SiteAgency.staticSetAgencyTotalPacificIslander(siteRequest_, o);
		case "agencyTotalLatinx":
			return SiteAgency.staticSetAgencyTotalLatinx(siteRequest_, o);
		case "agencyTotalMultiracial":
			return SiteAgency.staticSetAgencyTotalMultiracial(siteRequest_, o);
		case "agencyTotalOther":
			return SiteAgency.staticSetAgencyTotalOther(siteRequest_, o);
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
		case "agencyTotal":
			return SiteAgency.staticSolrAgencyTotal(siteRequest_, (Long)o);
		case "agencyTotalWhite":
			return SiteAgency.staticSolrAgencyTotalWhite(siteRequest_, (Long)o);
		case "agencyTotalBlack":
			return SiteAgency.staticSolrAgencyTotalBlack(siteRequest_, (Long)o);
		case "agencyTotalIndigenous":
			return SiteAgency.staticSolrAgencyTotalIndigenous(siteRequest_, (Long)o);
		case "agencyTotalAsian":
			return SiteAgency.staticSolrAgencyTotalAsian(siteRequest_, (Long)o);
		case "agencyTotalPacificIslander":
			return SiteAgency.staticSolrAgencyTotalPacificIslander(siteRequest_, (Long)o);
		case "agencyTotalLatinx":
			return SiteAgency.staticSolrAgencyTotalLatinx(siteRequest_, (Long)o);
		case "agencyTotalMultiracial":
			return SiteAgency.staticSolrAgencyTotalMultiracial(siteRequest_, (Long)o);
		case "agencyTotalOther":
			return SiteAgency.staticSolrAgencyTotalOther(siteRequest_, (Long)o);
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
		case "agencyTotal":
			return SiteAgency.staticSolrStrAgencyTotal(siteRequest_, (Long)o);
		case "agencyTotalWhite":
			return SiteAgency.staticSolrStrAgencyTotalWhite(siteRequest_, (Long)o);
		case "agencyTotalBlack":
			return SiteAgency.staticSolrStrAgencyTotalBlack(siteRequest_, (Long)o);
		case "agencyTotalIndigenous":
			return SiteAgency.staticSolrStrAgencyTotalIndigenous(siteRequest_, (Long)o);
		case "agencyTotalAsian":
			return SiteAgency.staticSolrStrAgencyTotalAsian(siteRequest_, (Long)o);
		case "agencyTotalPacificIslander":
			return SiteAgency.staticSolrStrAgencyTotalPacificIslander(siteRequest_, (Long)o);
		case "agencyTotalLatinx":
			return SiteAgency.staticSolrStrAgencyTotalLatinx(siteRequest_, (Long)o);
		case "agencyTotalMultiracial":
			return SiteAgency.staticSolrStrAgencyTotalMultiracial(siteRequest_, (Long)o);
		case "agencyTotalOther":
			return SiteAgency.staticSolrStrAgencyTotalOther(siteRequest_, (Long)o);
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
		case "agencyTotal":
			return SiteAgency.staticSolrFqAgencyTotal(siteRequest_, o);
		case "agencyTotalWhite":
			return SiteAgency.staticSolrFqAgencyTotalWhite(siteRequest_, o);
		case "agencyTotalBlack":
			return SiteAgency.staticSolrFqAgencyTotalBlack(siteRequest_, o);
		case "agencyTotalIndigenous":
			return SiteAgency.staticSolrFqAgencyTotalIndigenous(siteRequest_, o);
		case "agencyTotalAsian":
			return SiteAgency.staticSolrFqAgencyTotalAsian(siteRequest_, o);
		case "agencyTotalPacificIslander":
			return SiteAgency.staticSolrFqAgencyTotalPacificIslander(siteRequest_, o);
		case "agencyTotalLatinx":
			return SiteAgency.staticSolrFqAgencyTotalLatinx(siteRequest_, o);
		case "agencyTotalMultiracial":
			return SiteAgency.staticSolrFqAgencyTotalMultiracial(siteRequest_, o);
		case "agencyTotalOther":
			return SiteAgency.staticSolrFqAgencyTotalOther(siteRequest_, o);
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
			case "agencytotal":
				if(val != null)
					setAgencyTotal(val);
				saves.add("agencyTotal");
				return val;
			case "agencytotalwhite":
				if(val != null)
					setAgencyTotalWhite(val);
				saves.add("agencyTotalWhite");
				return val;
			case "agencytotalblack":
				if(val != null)
					setAgencyTotalBlack(val);
				saves.add("agencyTotalBlack");
				return val;
			case "agencytotalindigenous":
				if(val != null)
					setAgencyTotalIndigenous(val);
				saves.add("agencyTotalIndigenous");
				return val;
			case "agencytotalasian":
				if(val != null)
					setAgencyTotalAsian(val);
				saves.add("agencyTotalAsian");
				return val;
			case "agencytotalpacificislander":
				if(val != null)
					setAgencyTotalPacificIslander(val);
				saves.add("agencyTotalPacificIslander");
				return val;
			case "agencytotallatinx":
				if(val != null)
					setAgencyTotalLatinx(val);
				saves.add("agencyTotalLatinx");
				return val;
			case "agencytotalmultiracial":
				if(val != null)
					setAgencyTotalMultiracial(val);
				saves.add("agencyTotalMultiracial");
				return val;
			case "agencytotalother":
				if(val != null)
					setAgencyTotalOther(val);
				saves.add("agencyTotalOther");
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
			case "agencytotal":
				if(val instanceof Long)
					setAgencyTotal((Long)val);
				saves.add("agencyTotal");
				return val;
			case "agencytotalwhite":
				if(val instanceof Long)
					setAgencyTotalWhite((Long)val);
				saves.add("agencyTotalWhite");
				return val;
			case "agencytotalblack":
				if(val instanceof Long)
					setAgencyTotalBlack((Long)val);
				saves.add("agencyTotalBlack");
				return val;
			case "agencytotalindigenous":
				if(val instanceof Long)
					setAgencyTotalIndigenous((Long)val);
				saves.add("agencyTotalIndigenous");
				return val;
			case "agencytotalasian":
				if(val instanceof Long)
					setAgencyTotalAsian((Long)val);
				saves.add("agencyTotalAsian");
				return val;
			case "agencytotalpacificislander":
				if(val instanceof Long)
					setAgencyTotalPacificIslander((Long)val);
				saves.add("agencyTotalPacificIslander");
				return val;
			case "agencytotallatinx":
				if(val instanceof Long)
					setAgencyTotalLatinx((Long)val);
				saves.add("agencyTotalLatinx");
				return val;
			case "agencytotalmultiracial":
				if(val instanceof Long)
					setAgencyTotalMultiracial((Long)val);
				saves.add("agencyTotalMultiracial");
				return val;
			case "agencytotalother":
				if(val instanceof Long)
					setAgencyTotalOther((Long)val);
				saves.add("agencyTotalOther");
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

			if(saves.contains("agencyTotal")) {
				Long agencyTotal = (Long)solrDocument.get("agencyTotal_stored_long");
				if(agencyTotal != null)
					oSiteAgency.setAgencyTotal(agencyTotal);
			}

			if(saves.contains("agencyTotalWhite")) {
				Long agencyTotalWhite = (Long)solrDocument.get("agencyTotalWhite_stored_long");
				if(agencyTotalWhite != null)
					oSiteAgency.setAgencyTotalWhite(agencyTotalWhite);
			}

			if(saves.contains("agencyTotalBlack")) {
				Long agencyTotalBlack = (Long)solrDocument.get("agencyTotalBlack_stored_long");
				if(agencyTotalBlack != null)
					oSiteAgency.setAgencyTotalBlack(agencyTotalBlack);
			}

			if(saves.contains("agencyTotalIndigenous")) {
				Long agencyTotalIndigenous = (Long)solrDocument.get("agencyTotalIndigenous_stored_long");
				if(agencyTotalIndigenous != null)
					oSiteAgency.setAgencyTotalIndigenous(agencyTotalIndigenous);
			}

			if(saves.contains("agencyTotalAsian")) {
				Long agencyTotalAsian = (Long)solrDocument.get("agencyTotalAsian_stored_long");
				if(agencyTotalAsian != null)
					oSiteAgency.setAgencyTotalAsian(agencyTotalAsian);
			}

			if(saves.contains("agencyTotalPacificIslander")) {
				Long agencyTotalPacificIslander = (Long)solrDocument.get("agencyTotalPacificIslander_stored_long");
				if(agencyTotalPacificIslander != null)
					oSiteAgency.setAgencyTotalPacificIslander(agencyTotalPacificIslander);
			}

			if(saves.contains("agencyTotalLatinx")) {
				Long agencyTotalLatinx = (Long)solrDocument.get("agencyTotalLatinx_stored_long");
				if(agencyTotalLatinx != null)
					oSiteAgency.setAgencyTotalLatinx(agencyTotalLatinx);
			}

			if(saves.contains("agencyTotalMultiracial")) {
				Long agencyTotalMultiracial = (Long)solrDocument.get("agencyTotalMultiracial_stored_long");
				if(agencyTotalMultiracial != null)
					oSiteAgency.setAgencyTotalMultiracial(agencyTotalMultiracial);
			}

			if(saves.contains("agencyTotalOther")) {
				Long agencyTotalOther = (Long)solrDocument.get("agencyTotalOther_stored_long");
				if(agencyTotalOther != null)
					oSiteAgency.setAgencyTotalOther(agencyTotalOther);
			}

			if(saves.contains("stateKey")) {
				Long stateKey = (Long)solrDocument.get("stateKey_stored_long");
				if(stateKey != null)
					oSiteAgency.setStateKey(stateKey);
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
		if(agencyTotal != null) {
			document.addField("agencyTotal_indexed_long", agencyTotal);
			document.addField("agencyTotal_stored_long", agencyTotal);
		}
		if(agencyTotalWhite != null) {
			document.addField("agencyTotalWhite_indexed_long", agencyTotalWhite);
			document.addField("agencyTotalWhite_stored_long", agencyTotalWhite);
		}
		if(agencyTotalBlack != null) {
			document.addField("agencyTotalBlack_indexed_long", agencyTotalBlack);
			document.addField("agencyTotalBlack_stored_long", agencyTotalBlack);
		}
		if(agencyTotalIndigenous != null) {
			document.addField("agencyTotalIndigenous_indexed_long", agencyTotalIndigenous);
			document.addField("agencyTotalIndigenous_stored_long", agencyTotalIndigenous);
		}
		if(agencyTotalAsian != null) {
			document.addField("agencyTotalAsian_indexed_long", agencyTotalAsian);
			document.addField("agencyTotalAsian_stored_long", agencyTotalAsian);
		}
		if(agencyTotalPacificIslander != null) {
			document.addField("agencyTotalPacificIslander_indexed_long", agencyTotalPacificIslander);
			document.addField("agencyTotalPacificIslander_stored_long", agencyTotalPacificIslander);
		}
		if(agencyTotalLatinx != null) {
			document.addField("agencyTotalLatinx_indexed_long", agencyTotalLatinx);
			document.addField("agencyTotalLatinx_stored_long", agencyTotalLatinx);
		}
		if(agencyTotalMultiracial != null) {
			document.addField("agencyTotalMultiracial_indexed_long", agencyTotalMultiracial);
			document.addField("agencyTotalMultiracial_stored_long", agencyTotalMultiracial);
		}
		if(agencyTotalOther != null) {
			document.addField("agencyTotalOther_indexed_long", agencyTotalOther);
			document.addField("agencyTotalOther_stored_long", agencyTotalOther);
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
			case "agencyTotal":
				return "agencyTotal_indexed_long";
			case "agencyTotalWhite":
				return "agencyTotalWhite_indexed_long";
			case "agencyTotalBlack":
				return "agencyTotalBlack_indexed_long";
			case "agencyTotalIndigenous":
				return "agencyTotalIndigenous_indexed_long";
			case "agencyTotalAsian":
				return "agencyTotalAsian_indexed_long";
			case "agencyTotalPacificIslander":
				return "agencyTotalPacificIslander_indexed_long";
			case "agencyTotalLatinx":
				return "agencyTotalLatinx_indexed_long";
			case "agencyTotalMultiracial":
				return "agencyTotalMultiracial_indexed_long";
			case "agencyTotalOther":
				return "agencyTotalOther_indexed_long";
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
		oSiteAgency.setAgencyTotal(Optional.ofNullable(solrDocument.get("agencyTotal_stored_long")).map(v -> v.toString()).orElse(null));
		oSiteAgency.setAgencyTotalWhite(Optional.ofNullable(solrDocument.get("agencyTotalWhite_stored_long")).map(v -> v.toString()).orElse(null));
		oSiteAgency.setAgencyTotalBlack(Optional.ofNullable(solrDocument.get("agencyTotalBlack_stored_long")).map(v -> v.toString()).orElse(null));
		oSiteAgency.setAgencyTotalIndigenous(Optional.ofNullable(solrDocument.get("agencyTotalIndigenous_stored_long")).map(v -> v.toString()).orElse(null));
		oSiteAgency.setAgencyTotalAsian(Optional.ofNullable(solrDocument.get("agencyTotalAsian_stored_long")).map(v -> v.toString()).orElse(null));
		oSiteAgency.setAgencyTotalPacificIslander(Optional.ofNullable(solrDocument.get("agencyTotalPacificIslander_stored_long")).map(v -> v.toString()).orElse(null));
		oSiteAgency.setAgencyTotalLatinx(Optional.ofNullable(solrDocument.get("agencyTotalLatinx_stored_long")).map(v -> v.toString()).orElse(null));
		oSiteAgency.setAgencyTotalMultiracial(Optional.ofNullable(solrDocument.get("agencyTotalMultiracial_stored_long")).map(v -> v.toString()).orElse(null));
		oSiteAgency.setAgencyTotalOther(Optional.ofNullable(solrDocument.get("agencyTotalOther_stored_long")).map(v -> v.toString()).orElse(null));
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
			if(!Objects.equals(agencyTotal, original.getAgencyTotal()))
				apiRequest.addVars("agencyTotal");
			if(!Objects.equals(agencyTotalWhite, original.getAgencyTotalWhite()))
				apiRequest.addVars("agencyTotalWhite");
			if(!Objects.equals(agencyTotalBlack, original.getAgencyTotalBlack()))
				apiRequest.addVars("agencyTotalBlack");
			if(!Objects.equals(agencyTotalIndigenous, original.getAgencyTotalIndigenous()))
				apiRequest.addVars("agencyTotalIndigenous");
			if(!Objects.equals(agencyTotalAsian, original.getAgencyTotalAsian()))
				apiRequest.addVars("agencyTotalAsian");
			if(!Objects.equals(agencyTotalPacificIslander, original.getAgencyTotalPacificIslander()))
				apiRequest.addVars("agencyTotalPacificIslander");
			if(!Objects.equals(agencyTotalLatinx, original.getAgencyTotalLatinx()))
				apiRequest.addVars("agencyTotalLatinx");
			if(!Objects.equals(agencyTotalMultiracial, original.getAgencyTotalMultiracial()))
				apiRequest.addVars("agencyTotalMultiracial");
			if(!Objects.equals(agencyTotalOther, original.getAgencyTotalOther()))
				apiRequest.addVars("agencyTotalOther");
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
		return Objects.hash(super.hashCode(), agencyTitle, agencyAcsId, agencyTotal, agencyTotalWhite, agencyTotalBlack, agencyTotalIndigenous, agencyTotalAsian, agencyTotalPacificIslander, agencyTotalLatinx, agencyTotalMultiracial, agencyTotalOther, stateKey, stateId, stateName, stateAbbreviation, objectSuggest);
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
				&& Objects.equals( agencyTotal, that.agencyTotal )
				&& Objects.equals( agencyTotalWhite, that.agencyTotalWhite )
				&& Objects.equals( agencyTotalBlack, that.agencyTotalBlack )
				&& Objects.equals( agencyTotalIndigenous, that.agencyTotalIndigenous )
				&& Objects.equals( agencyTotalAsian, that.agencyTotalAsian )
				&& Objects.equals( agencyTotalPacificIslander, that.agencyTotalPacificIslander )
				&& Objects.equals( agencyTotalLatinx, that.agencyTotalLatinx )
				&& Objects.equals( agencyTotalMultiracial, that.agencyTotalMultiracial )
				&& Objects.equals( agencyTotalOther, that.agencyTotalOther )
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
		sb.append( ", agencyTotal: " ).append(agencyTotal);
		sb.append( ", agencyTotalWhite: " ).append(agencyTotalWhite);
		sb.append( ", agencyTotalBlack: " ).append(agencyTotalBlack);
		sb.append( ", agencyTotalIndigenous: " ).append(agencyTotalIndigenous);
		sb.append( ", agencyTotalAsian: " ).append(agencyTotalAsian);
		sb.append( ", agencyTotalPacificIslander: " ).append(agencyTotalPacificIslander);
		sb.append( ", agencyTotalLatinx: " ).append(agencyTotalLatinx);
		sb.append( ", agencyTotalMultiracial: " ).append(agencyTotalMultiracial);
		sb.append( ", agencyTotalOther: " ).append(agencyTotalOther);
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
	public static final String VAR_agencyTotal = "agencyTotal";
	public static final String VAR_agencyTotalWhite = "agencyTotalWhite";
	public static final String VAR_agencyTotalBlack = "agencyTotalBlack";
	public static final String VAR_agencyTotalIndigenous = "agencyTotalIndigenous";
	public static final String VAR_agencyTotalAsian = "agencyTotalAsian";
	public static final String VAR_agencyTotalPacificIslander = "agencyTotalPacificIslander";
	public static final String VAR_agencyTotalLatinx = "agencyTotalLatinx";
	public static final String VAR_agencyTotalMultiracial = "agencyTotalMultiracial";
	public static final String VAR_agencyTotalOther = "agencyTotalOther";
	public static final String VAR_stateKey = "stateKey";
	public static final String VAR_stateSearch = "stateSearch";
	public static final String VAR_state_ = "state_";
	public static final String VAR_stateId = "stateId";
	public static final String VAR_stateName = "stateName";
	public static final String VAR_stateAbbreviation = "stateAbbreviation";
	public static final String VAR_objectSuggest = "objectSuggest";
}
