package com.opendatapolicing.enus.state;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.Date;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.lang.Integer;
import java.text.NumberFormat;
import io.vertx.core.logging.LoggerFactory;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import java.lang.Long;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.vertx.core.json.JsonObject;
import java.lang.String;
import io.vertx.core.logging.Logger;
import java.math.RoundingMode;
import com.opendatapolicing.enus.wrap.Wrap;
import java.math.MathContext;
import org.apache.solr.client.solrj.response.QueryResponse;
import java.util.Set;
import com.opendatapolicing.enus.writer.AllWriter;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.opendatapolicing.enus.context.SiteContextEnUS;
import com.opendatapolicing.enus.request.api.ApiRequest;
import org.apache.solr.client.solrj.SolrClient;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import org.apache.solr.common.SolrDocument;
import java.util.List;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.opendatapolicing.enus.cluster.Cluster;
import org.apache.solr.client.solrj.util.ClientUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.apache.solr.common.SolrInputDocument;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.state.SiteState&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class SiteStateGen<DEV> extends Cluster {
	protected static final Logger LOGGER = LoggerFactory.getLogger(SiteState.class);

	public static final String SiteState_AName = "a state";
	public static final String SiteState_This = "this ";
	public static final String SiteState_ThisName = "this state";
	public static final String SiteState_A = "a ";
	public static final String SiteState_TheName = "the state";
	public static final String SiteState_NameSingular = "state";
	public static final String SiteState_NamePlural = "states";
	public static final String SiteState_NameActual = "current state";
	public static final String SiteState_AllName = "all the states";
	public static final String SiteState_SearchAllNameBy = "search states by ";
	public static final String SiteState_Title = "states";
	public static final String SiteState_ThePluralName = "the states";
	public static final String SiteState_NoNameFound = "no state found";
	public static final String SiteState_NameVar = "state";
	public static final String SiteState_OfName = "of state";
	public static final String SiteState_ANameAdjective = "a state";
	public static final String SiteState_NameAdjectiveSingular = "state";
	public static final String SiteState_NameAdjectivePlural = "states";
	public static final String SiteState_Color = "pale-blue";
	public static final String SiteState_IconGroup = "regular";
	public static final String SiteState_IconName = "globe-americas";
	public static final Integer SiteState_Rows = 100;

	//////////////
	// stateKey //
	//////////////

	/**	 The entity stateKey
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long stateKey;
	@JsonIgnore
	public Wrap<Long> stateKeyWrap = new Wrap<Long>().p(this).c(Long.class).var("stateKey").o(stateKey);

	/**	<br/> The entity stateKey
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.state.SiteState&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stateKey">Find the entity stateKey in Solr</a>
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
	public void setStateKey(String o) {
		this.stateKey = SiteState.staticSetStateKey(siteRequest_, o);
		this.stateKeyWrap.alreadyInitialized = true;
	}
	public static Long staticSetStateKey(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected SiteState stateKeyInit() {
		if(!stateKeyWrap.alreadyInitialized) {
			_stateKey(stateKeyWrap);
			if(stateKey == null)
				setStateKey(stateKeyWrap.o);
		}
		stateKeyWrap.alreadyInitialized(true);
		return (SiteState)this;
	}

	public static Long staticSolrStateKey(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrStateKey(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStateKey(SiteRequestEnUS siteRequest_, String o) {
		return SiteState.staticSolrStrStateKey(siteRequest_, SiteState.staticSolrStateKey(siteRequest_, SiteState.staticSetStateKey(siteRequest_, o)));
	}

	public Long solrStateKey() {
		return SiteState.staticSolrStateKey(siteRequest_, stateKey);
	}

	public String strStateKey() {
		return stateKey == null ? "" : stateKey.toString();
	}

	public String jsonStateKey() {
		return stateKey == null ? "" : stateKey.toString();
	}

	public String nomAffichageStateKey() {
		return null;
	}

	public String htmTooltipStateKey() {
		return null;
	}

	public String htmStateKey() {
		return stateKey == null ? "" : StringEscapeUtils.escapeHtml4(strStateKey());
	}

	///////////////
	// stateName //
	///////////////

	/**	 The entity stateName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String stateName;
	@JsonIgnore
	public Wrap<String> stateNameWrap = new Wrap<String>().p(this).c(String.class).var("stateName").o(stateName);

	/**	<br/> The entity stateName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.state.SiteState&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stateName">Find the entity stateName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stateName(Wrap<String> c);

	public String getStateName() {
		return stateName;
	}
	public void setStateName(String o) {
		this.stateName = SiteState.staticSetStateName(siteRequest_, o);
		this.stateNameWrap.alreadyInitialized = true;
	}
	public static String staticSetStateName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteState stateNameInit() {
		if(!stateNameWrap.alreadyInitialized) {
			_stateName(stateNameWrap);
			if(stateName == null)
				setStateName(stateNameWrap.o);
		}
		stateNameWrap.alreadyInitialized(true);
		return (SiteState)this;
	}

	public static String staticSolrStateName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStateName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStateName(SiteRequestEnUS siteRequest_, String o) {
		return SiteState.staticSolrStrStateName(siteRequest_, SiteState.staticSolrStateName(siteRequest_, SiteState.staticSetStateName(siteRequest_, o)));
	}

	public String solrStateName() {
		return SiteState.staticSolrStateName(siteRequest_, stateName);
	}

	public String strStateName() {
		return stateName == null ? "" : stateName;
	}

	public String jsonStateName() {
		return stateName == null ? "" : stateName;
	}

	public String nomAffichageStateName() {
		return null;
	}

	public String htmTooltipStateName() {
		return null;
	}

	public String htmStateName() {
		return stateName == null ? "" : StringEscapeUtils.escapeHtml4(strStateName());
	}

	///////////////////////
	// stateAbbreviation //
	///////////////////////

	/**	 The entity stateAbbreviation
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String stateAbbreviation;
	@JsonIgnore
	public Wrap<String> stateAbbreviationWrap = new Wrap<String>().p(this).c(String.class).var("stateAbbreviation").o(stateAbbreviation);

	/**	<br/> The entity stateAbbreviation
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.state.SiteState&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stateAbbreviation">Find the entity stateAbbreviation in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stateAbbreviation(Wrap<String> c);

	public String getStateAbbreviation() {
		return stateAbbreviation;
	}
	public void setStateAbbreviation(String o) {
		this.stateAbbreviation = SiteState.staticSetStateAbbreviation(siteRequest_, o);
		this.stateAbbreviationWrap.alreadyInitialized = true;
	}
	public static String staticSetStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteState stateAbbreviationInit() {
		if(!stateAbbreviationWrap.alreadyInitialized) {
			_stateAbbreviation(stateAbbreviationWrap);
			if(stateAbbreviation == null)
				setStateAbbreviation(stateAbbreviationWrap.o);
		}
		stateAbbreviationWrap.alreadyInitialized(true);
		return (SiteState)this;
	}

	public static String staticSolrStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return SiteState.staticSolrStrStateAbbreviation(siteRequest_, SiteState.staticSolrStateAbbreviation(siteRequest_, SiteState.staticSetStateAbbreviation(siteRequest_, o)));
	}

	public String solrStateAbbreviation() {
		return SiteState.staticSolrStateAbbreviation(siteRequest_, stateAbbreviation);
	}

	public String strStateAbbreviation() {
		return stateAbbreviation == null ? "" : stateAbbreviation;
	}

	public String jsonStateAbbreviation() {
		return stateAbbreviation == null ? "" : stateAbbreviation;
	}

	public String nomAffichageStateAbbreviation() {
		return null;
	}

	public String htmTooltipStateAbbreviation() {
		return null;
	}

	public String htmStateAbbreviation() {
		return stateAbbreviation == null ? "" : StringEscapeUtils.escapeHtml4(strStateAbbreviation());
	}

	///////////////
	// imageLeft //
	///////////////

	/**	 The entity imageLeft
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer imageLeft;
	@JsonIgnore
	public Wrap<Integer> imageLeftWrap = new Wrap<Integer>().p(this).c(Integer.class).var("imageLeft").o(imageLeft);

	/**	<br/> The entity imageLeft
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.state.SiteState&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:imageLeft">Find the entity imageLeft in Solr</a>
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
	public void setImageLeft(String o) {
		this.imageLeft = SiteState.staticSetImageLeft(siteRequest_, o);
		this.imageLeftWrap.alreadyInitialized = true;
	}
	public static Integer staticSetImageLeft(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected SiteState imageLeftInit() {
		if(!imageLeftWrap.alreadyInitialized) {
			_imageLeft(imageLeftWrap);
			if(imageLeft == null)
				setImageLeft(imageLeftWrap.o);
		}
		imageLeftWrap.alreadyInitialized(true);
		return (SiteState)this;
	}

	public static Integer staticSolrImageLeft(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrImageLeft(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqImageLeft(SiteRequestEnUS siteRequest_, String o) {
		return SiteState.staticSolrStrImageLeft(siteRequest_, SiteState.staticSolrImageLeft(siteRequest_, SiteState.staticSetImageLeft(siteRequest_, o)));
	}

	public Integer solrImageLeft() {
		return SiteState.staticSolrImageLeft(siteRequest_, imageLeft);
	}

	public String strImageLeft() {
		return imageLeft == null ? "" : imageLeft.toString();
	}

	public String jsonImageLeft() {
		return imageLeft == null ? "" : imageLeft.toString();
	}

	public String nomAffichageImageLeft() {
		return null;
	}

	public String htmTooltipImageLeft() {
		return null;
	}

	public String htmImageLeft() {
		return imageLeft == null ? "" : StringEscapeUtils.escapeHtml4(strImageLeft());
	}

	//////////////
	// imageTop //
	//////////////

	/**	 The entity imageTop
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer imageTop;
	@JsonIgnore
	public Wrap<Integer> imageTopWrap = new Wrap<Integer>().p(this).c(Integer.class).var("imageTop").o(imageTop);

	/**	<br/> The entity imageTop
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.state.SiteState&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:imageTop">Find the entity imageTop in Solr</a>
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
	public void setImageTop(String o) {
		this.imageTop = SiteState.staticSetImageTop(siteRequest_, o);
		this.imageTopWrap.alreadyInitialized = true;
	}
	public static Integer staticSetImageTop(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected SiteState imageTopInit() {
		if(!imageTopWrap.alreadyInitialized) {
			_imageTop(imageTopWrap);
			if(imageTop == null)
				setImageTop(imageTopWrap.o);
		}
		imageTopWrap.alreadyInitialized(true);
		return (SiteState)this;
	}

	public static Integer staticSolrImageTop(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrImageTop(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqImageTop(SiteRequestEnUS siteRequest_, String o) {
		return SiteState.staticSolrStrImageTop(siteRequest_, SiteState.staticSolrImageTop(siteRequest_, SiteState.staticSetImageTop(siteRequest_, o)));
	}

	public Integer solrImageTop() {
		return SiteState.staticSolrImageTop(siteRequest_, imageTop);
	}

	public String strImageTop() {
		return imageTop == null ? "" : imageTop.toString();
	}

	public String jsonImageTop() {
		return imageTop == null ? "" : imageTop.toString();
	}

	public String nomAffichageImageTop() {
		return null;
	}

	public String htmTooltipImageTop() {
		return null;
	}

	public String htmImageTop() {
		return imageTop == null ? "" : StringEscapeUtils.escapeHtml4(strImageTop());
	}

	////////////////
	// agencyKeys //
	////////////////

	/**	 The entity agencyKeys
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> agencyKeys = new ArrayList<Long>();
	@JsonIgnore
	public Wrap<List<Long>> agencyKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("agencyKeys").o(agencyKeys);

	/**	<br/> The entity agencyKeys
	 *  It is constructed before being initialized with the constructor by default List<Long>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.state.SiteState&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:agencyKeys">Find the entity agencyKeys in Solr</a>
	 * <br/>
	 * @param agencyKeys is the entity already constructed. 
	 **/
	protected abstract void _agencyKeys(List<Long> o);

	public List<Long> getAgencyKeys() {
		return agencyKeys;
	}

	public void setAgencyKeys(List<Long> agencyKeys) {
		this.agencyKeys = agencyKeys;
		this.agencyKeysWrap.alreadyInitialized = true;
	}
	public void setAgencyKeys(String o) {
		Long l = SiteState.staticSetAgencyKeys(siteRequest_, o);
		if(l != null)
			addAgencyKeys(l);
		this.agencyKeysWrap.alreadyInitialized = true;
	}
	public static Long staticSetAgencyKeys(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	public SiteState addAgencyKeys(Long...objets) {
		for(Long o : objets) {
			addAgencyKeys(o);
		}
		return (SiteState)this;
	}
	public SiteState addAgencyKeys(Long o) {
		if(o != null && !agencyKeys.contains(o))
			this.agencyKeys.add(o);
		return (SiteState)this;
	}
	public void setAgencyKeys(JsonArray objets) {
		agencyKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addAgencyKeys(o);
		}
	}
	public SiteState addAgencyKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addAgencyKeys(p);
		}
		return (SiteState)this;
	}
	protected SiteState agencyKeysInit() {
		if(!agencyKeysWrap.alreadyInitialized) {
			_agencyKeys(agencyKeys);
		}
		agencyKeysWrap.alreadyInitialized(true);
		return (SiteState)this;
	}

	public static Long staticSolrAgencyKeys(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrAgencyKeys(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqAgencyKeys(SiteRequestEnUS siteRequest_, String o) {
		return SiteState.staticSolrStrAgencyKeys(siteRequest_, SiteState.staticSolrAgencyKeys(siteRequest_, SiteState.staticSetAgencyKeys(siteRequest_, o)));
	}

	public List<Long> solrAgencyKeys() {
		List<Long> l = new ArrayList<Long>();
		for(Long o : agencyKeys) {
			l.add(SiteState.staticSolrAgencyKeys(siteRequest_, o));
		}
		return l;
	}

	public String strAgencyKeys() {
		return agencyKeys == null ? "" : agencyKeys.toString();
	}

	public String jsonAgencyKeys() {
		return agencyKeys == null ? "" : agencyKeys.toString();
	}

	public String nomAffichageAgencyKeys() {
		return null;
	}

	public String htmTooltipAgencyKeys() {
		return null;
	}

	public String htmAgencyKeys() {
		return agencyKeys == null ? "" : StringEscapeUtils.escapeHtml4(strAgencyKeys());
	}

	///////////////////////
	// stateCompleteName //
	///////////////////////

	/**	 The entity stateCompleteName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String stateCompleteName;
	@JsonIgnore
	public Wrap<String> stateCompleteNameWrap = new Wrap<String>().p(this).c(String.class).var("stateCompleteName").o(stateCompleteName);

	/**	<br/> The entity stateCompleteName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.state.SiteState&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stateCompleteName">Find the entity stateCompleteName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stateCompleteName(Wrap<String> c);

	public String getStateCompleteName() {
		return stateCompleteName;
	}
	public void setStateCompleteName(String o) {
		this.stateCompleteName = SiteState.staticSetStateCompleteName(siteRequest_, o);
		this.stateCompleteNameWrap.alreadyInitialized = true;
	}
	public static String staticSetStateCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteState stateCompleteNameInit() {
		if(!stateCompleteNameWrap.alreadyInitialized) {
			_stateCompleteName(stateCompleteNameWrap);
			if(stateCompleteName == null)
				setStateCompleteName(stateCompleteNameWrap.o);
		}
		stateCompleteNameWrap.alreadyInitialized(true);
		return (SiteState)this;
	}

	public static String staticSolrStateCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStateCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStateCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return SiteState.staticSolrStrStateCompleteName(siteRequest_, SiteState.staticSolrStateCompleteName(siteRequest_, SiteState.staticSetStateCompleteName(siteRequest_, o)));
	}

	public String solrStateCompleteName() {
		return SiteState.staticSolrStateCompleteName(siteRequest_, stateCompleteName);
	}

	public String strStateCompleteName() {
		return stateCompleteName == null ? "" : stateCompleteName;
	}

	public String jsonStateCompleteName() {
		return stateCompleteName == null ? "" : stateCompleteName;
	}

	public String nomAffichageStateCompleteName() {
		return null;
	}

	public String htmTooltipStateCompleteName() {
		return null;
	}

	public String htmStateCompleteName() {
		return stateCompleteName == null ? "" : StringEscapeUtils.escapeHtml4(strStateCompleteName());
	}

	/////////////////
	// objectTitle //
	/////////////////

	/**	 The entity objectTitle
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String objectTitle;
	@JsonIgnore
	public Wrap<String> objectTitleWrap = new Wrap<String>().p(this).c(String.class).var("objectTitle").o(objectTitle);

	/**	<br/> The entity objectTitle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.state.SiteState&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:objectTitle">Find the entity objectTitle in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _objectTitle(Wrap<String> c);

	public String getObjectTitle() {
		return objectTitle;
	}
	public void setObjectTitle(String o) {
		this.objectTitle = SiteState.staticSetObjectTitle(siteRequest_, o);
		this.objectTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetObjectTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteState objectTitleInit() {
		if(!objectTitleWrap.alreadyInitialized) {
			_objectTitle(objectTitleWrap);
			if(objectTitle == null)
				setObjectTitle(objectTitleWrap.o);
		}
		objectTitleWrap.alreadyInitialized(true);
		return (SiteState)this;
	}

	public static String staticSolrObjectTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrObjectTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqObjectTitle(SiteRequestEnUS siteRequest_, String o) {
		return SiteState.staticSolrStrObjectTitle(siteRequest_, SiteState.staticSolrObjectTitle(siteRequest_, SiteState.staticSetObjectTitle(siteRequest_, o)));
	}

	public String solrObjectTitle() {
		return SiteState.staticSolrObjectTitle(siteRequest_, objectTitle);
	}

	public String strObjectTitle() {
		return objectTitle == null ? "" : objectTitle;
	}

	public String jsonObjectTitle() {
		return objectTitle == null ? "" : objectTitle;
	}

	public String nomAffichageObjectTitle() {
		return null;
	}

	public String htmTooltipObjectTitle() {
		return null;
	}

	public String htmObjectTitle() {
		return objectTitle == null ? "" : StringEscapeUtils.escapeHtml4(strObjectTitle());
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedSiteState = false;

	public SiteState initDeepSiteState(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedSiteState) {
			alreadyInitializedSiteState = true;
			initDeepSiteState();
		}
		return (SiteState)this;
	}

	public void initDeepSiteState() {
		initSiteState();
		super.initDeepCluster(siteRequest_);
	}

	public void initSiteState() {
		stateKeyInit();
		stateNameInit();
		stateAbbreviationInit();
		imageLeftInit();
		imageTopInit();
		agencyKeysInit();
		stateCompleteNameInit();
		objectTitleInit();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepSiteState(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestSiteState(SiteRequestEnUS siteRequest_) {
			super.siteRequestCluster(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestSiteState(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainSiteState(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
		}
		return o;
	}
	public Object obtainSiteState(String var) {
		SiteState oSiteState = (SiteState)this;
		switch(var) {
			case "stateKey":
				return oSiteState.stateKey;
			case "stateName":
				return oSiteState.stateName;
			case "stateAbbreviation":
				return oSiteState.stateAbbreviation;
			case "imageLeft":
				return oSiteState.imageLeft;
			case "imageTop":
				return oSiteState.imageTop;
			case "agencyKeys":
				return oSiteState.agencyKeys;
			case "stateCompleteName":
				return oSiteState.stateCompleteName;
			case "objectTitle":
				return oSiteState.objectTitle;
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
				o = attributeSiteState(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeSiteState(String var, Object val) {
		SiteState oSiteState = (SiteState)this;
		switch(var) {
			default:
				return super.attributeCluster(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetSiteState(entityVar,  siteRequest_, o);
	}
	public static Object staticSetSiteState(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "stateKey":
			return SiteState.staticSetStateKey(siteRequest_, o);
		case "stateName":
			return SiteState.staticSetStateName(siteRequest_, o);
		case "stateAbbreviation":
			return SiteState.staticSetStateAbbreviation(siteRequest_, o);
		case "imageLeft":
			return SiteState.staticSetImageLeft(siteRequest_, o);
		case "imageTop":
			return SiteState.staticSetImageTop(siteRequest_, o);
		case "agencyKeys":
			return SiteState.staticSetAgencyKeys(siteRequest_, o);
		case "stateCompleteName":
			return SiteState.staticSetStateCompleteName(siteRequest_, o);
		case "objectTitle":
			return SiteState.staticSetObjectTitle(siteRequest_, o);
			default:
				return Cluster.staticSetCluster(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrSiteState(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrSiteState(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "stateKey":
			return SiteState.staticSolrStateKey(siteRequest_, (Long)o);
		case "stateName":
			return SiteState.staticSolrStateName(siteRequest_, (String)o);
		case "stateAbbreviation":
			return SiteState.staticSolrStateAbbreviation(siteRequest_, (String)o);
		case "imageLeft":
			return SiteState.staticSolrImageLeft(siteRequest_, (Integer)o);
		case "imageTop":
			return SiteState.staticSolrImageTop(siteRequest_, (Integer)o);
		case "agencyKeys":
			return SiteState.staticSolrAgencyKeys(siteRequest_, (Long)o);
		case "stateCompleteName":
			return SiteState.staticSolrStateCompleteName(siteRequest_, (String)o);
		case "objectTitle":
			return SiteState.staticSolrObjectTitle(siteRequest_, (String)o);
			default:
				return Cluster.staticSolrCluster(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrSiteState(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrSiteState(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "stateKey":
			return SiteState.staticSolrStrStateKey(siteRequest_, (Long)o);
		case "stateName":
			return SiteState.staticSolrStrStateName(siteRequest_, (String)o);
		case "stateAbbreviation":
			return SiteState.staticSolrStrStateAbbreviation(siteRequest_, (String)o);
		case "imageLeft":
			return SiteState.staticSolrStrImageLeft(siteRequest_, (Integer)o);
		case "imageTop":
			return SiteState.staticSolrStrImageTop(siteRequest_, (Integer)o);
		case "agencyKeys":
			return SiteState.staticSolrStrAgencyKeys(siteRequest_, (Long)o);
		case "stateCompleteName":
			return SiteState.staticSolrStrStateCompleteName(siteRequest_, (String)o);
		case "objectTitle":
			return SiteState.staticSolrStrObjectTitle(siteRequest_, (String)o);
			default:
				return Cluster.staticSolrStrCluster(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqSiteState(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqSiteState(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "stateKey":
			return SiteState.staticSolrFqStateKey(siteRequest_, o);
		case "stateName":
			return SiteState.staticSolrFqStateName(siteRequest_, o);
		case "stateAbbreviation":
			return SiteState.staticSolrFqStateAbbreviation(siteRequest_, o);
		case "imageLeft":
			return SiteState.staticSolrFqImageLeft(siteRequest_, o);
		case "imageTop":
			return SiteState.staticSolrFqImageTop(siteRequest_, o);
		case "agencyKeys":
			return SiteState.staticSolrFqAgencyKeys(siteRequest_, o);
		case "stateCompleteName":
			return SiteState.staticSolrFqStateCompleteName(siteRequest_, o);
		case "objectTitle":
			return SiteState.staticSolrFqObjectTitle(siteRequest_, o);
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
					o = defineSiteState(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSiteState(String var, String val) {
		switch(var) {
			default:
				return super.defineCluster(var, val);
		}
	}

	/////////////
	// populate //
	/////////////

	@Override public void populateForClass(SolrDocument solrDocument) {
		populateSiteState(solrDocument);
	}
	public void populateSiteState(SolrDocument solrDocument) {
		SiteState oSiteState = (SiteState)this;
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
			solrQuery.addFilterQuery("id:" + ClientUtils.escapeQueryChars("com.opendatapolicing.enus.state.SiteState"));
			QueryResponse queryResponse = siteRequest.getSiteContext_().getSolrClient().query(solrQuery);
			if(queryResponse.getResults().size() > 0)
				siteRequest.setSolrDocument(queryResponse.getResults().get(0));
			SiteState o = new SiteState();
			o.siteRequestSiteState(siteRequest);
			o.initDeepSiteState(siteRequest);
			o.indexSiteState();
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}


	@Override public void indexForClass() {
		indexSiteState();
	}

	@Override public void indexForClass(SolrInputDocument document) {
		indexSiteState(document);
	}

	public void indexSiteState(SolrClient clientSolr) {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexSiteState(document);
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexSiteState() {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexSiteState(document);
			SolrClient clientSolr = siteRequest_.getSiteContext_().getSolrClient();
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexSiteState(SolrInputDocument document) {
		super.indexCluster(document);

	}

	public static String varIndexedSiteState(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varIndexedCluster(entityVar);
		}
	}

	public static String varSearchSiteState(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSearchCluster(entityVar);
		}
	}

	public static String varSuggestedSiteState(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSuggestedCluster(entityVar);
		}
	}

	/////////////
	// store //
	/////////////

	@Override public void storeForClass(SolrDocument solrDocument) {
		storeSiteState(solrDocument);
	}
	public void storeSiteState(SolrDocument solrDocument) {
		SiteState oSiteState = (SiteState)this;

		super.storeCluster(solrDocument);
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestSiteState() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof SiteState) {
			SiteState original = (SiteState)o;
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
		if(!(o instanceof SiteState))
			return false;
		SiteState that = (SiteState)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("SiteState { ");
		sb.append(" }");
		return sb.toString();
	}
}
