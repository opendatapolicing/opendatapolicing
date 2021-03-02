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
import java.util.Map;
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
import com.opendatapolicing.enus.agency.SiteAgency;
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

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("");

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

	public Long sqlStateKey() {
		return stateKey;
	}

	public String jsonStateKey() {
		return stateKey == null ? "" : stateKey.toString();
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

	public String sqlStateName() {
		return stateName;
	}

	public String jsonStateName() {
		return stateName == null ? "" : stateName;
	}

	public String htmTooltipStateName() {
		return null;
	}

	public String htmStateName() {
		return stateName == null ? "" : StringEscapeUtils.escapeHtml4(strStateName());
	}

	public void inputStateName(String classApiMethodMethod) {
		SiteState s = (SiteState)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "name")
				.a("id", classApiMethodMethod, "_stateName");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setStateName classSiteState inputSiteState", pk, "StateName w3-input w3-border ");
					a("name", "setStateName");
				} else {
					a("class", "valueStateName w3-input w3-border classSiteState inputSiteState", pk, "StateName w3-input w3-border ");
					a("name", "stateName");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStateName', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_stateName')); }, function() { addError($('#", classApiMethodMethod, "_stateName')); }); ");
				}
				a("value", strStateName())
			.fg();

		} else {
			e("span").a("class", "varSiteState", pk, "StateName ").f().sx(htmStateName()).g("span");
		}
	}

	public void htmStateName(String classApiMethodMethod) {
		SiteState s = (SiteState)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SiteStateStateName").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-blue ").f();
							e("label").a("for", classApiMethodMethod, "_stateName").a("class", "").f().sx("name").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputStateName(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pale-blue ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_stateName')); $('#", classApiMethodMethod, "_stateName').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SiteStateForm :input[name=pk]').val() }], 'setStateName', null, function() { addGlow($('#", classApiMethodMethod, "_stateName')); }, function() { addError($('#", classApiMethodMethod, "_stateName')); }); ")
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

	public String sqlStateAbbreviation() {
		return stateAbbreviation;
	}

	public String jsonStateAbbreviation() {
		return stateAbbreviation == null ? "" : stateAbbreviation;
	}

	public String htmTooltipStateAbbreviation() {
		return null;
	}

	public String htmStateAbbreviation() {
		return stateAbbreviation == null ? "" : StringEscapeUtils.escapeHtml4(strStateAbbreviation());
	}

	public void inputStateAbbreviation(String classApiMethodMethod) {
		SiteState s = (SiteState)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "abbreviation")
				.a("id", classApiMethodMethod, "_stateAbbreviation");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setStateAbbreviation classSiteState inputSiteState", pk, "StateAbbreviation w3-input w3-border ");
					a("name", "setStateAbbreviation");
				} else {
					a("class", "valueStateAbbreviation w3-input w3-border classSiteState inputSiteState", pk, "StateAbbreviation w3-input w3-border ");
					a("name", "stateAbbreviation");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setStateAbbreviation', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_stateAbbreviation')); }, function() { addError($('#", classApiMethodMethod, "_stateAbbreviation')); }); ");
				}
				a("value", strStateAbbreviation())
			.fg();

		} else {
			e("span").a("class", "varSiteState", pk, "StateAbbreviation ").f().sx(htmStateAbbreviation()).g("span");
		}
	}

	public void htmStateAbbreviation(String classApiMethodMethod) {
		SiteState s = (SiteState)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SiteStateStateAbbreviation").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-blue ").f();
							e("label").a("for", classApiMethodMethod, "_stateAbbreviation").a("class", "").f().sx("abbreviation").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputStateAbbreviation(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pale-blue ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_stateAbbreviation')); $('#", classApiMethodMethod, "_stateAbbreviation').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SiteStateForm :input[name=pk]').val() }], 'setStateAbbreviation', null, function() { addGlow($('#", classApiMethodMethod, "_stateAbbreviation')); }, function() { addError($('#", classApiMethodMethod, "_stateAbbreviation')); }); ")
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

	public Integer sqlImageLeft() {
		return imageLeft;
	}

	public String jsonImageLeft() {
		return imageLeft == null ? "" : imageLeft.toString();
	}

	public String htmTooltipImageLeft() {
		return null;
	}

	public String htmImageLeft() {
		return imageLeft == null ? "" : StringEscapeUtils.escapeHtml4(strImageLeft());
	}

	public void inputImageLeft(String classApiMethodMethod) {
		SiteState s = (SiteState)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "image left pixels")
				.a("id", classApiMethodMethod, "_imageLeft");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setImageLeft classSiteState inputSiteState", pk, "ImageLeft w3-input w3-border ");
					a("name", "setImageLeft");
				} else {
					a("class", "valueImageLeft w3-input w3-border classSiteState inputSiteState", pk, "ImageLeft w3-input w3-border ");
					a("name", "imageLeft");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setImageLeft', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_imageLeft')); }, function() { addError($('#", classApiMethodMethod, "_imageLeft')); }); ");
				}
				a("value", strImageLeft())
			.fg();

		} else {
			e("span").a("class", "varSiteState", pk, "ImageLeft ").f().sx(htmImageLeft()).g("span");
		}
	}

	public void htmImageLeft(String classApiMethodMethod) {
		SiteState s = (SiteState)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SiteStateImageLeft").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-blue ").f();
							e("label").a("for", classApiMethodMethod, "_imageLeft").a("class", "").f().sx("image left pixels").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputImageLeft(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pale-blue ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_imageLeft')); $('#", classApiMethodMethod, "_imageLeft').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SiteStateForm :input[name=pk]').val() }], 'setImageLeft', null, function() { addGlow($('#", classApiMethodMethod, "_imageLeft')); }, function() { addError($('#", classApiMethodMethod, "_imageLeft')); }); ")
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

	public Integer sqlImageTop() {
		return imageTop;
	}

	public String jsonImageTop() {
		return imageTop == null ? "" : imageTop.toString();
	}

	public String htmTooltipImageTop() {
		return null;
	}

	public String htmImageTop() {
		return imageTop == null ? "" : StringEscapeUtils.escapeHtml4(strImageTop());
	}

	public void inputImageTop(String classApiMethodMethod) {
		SiteState s = (SiteState)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "image top pixels")
				.a("id", classApiMethodMethod, "_imageTop");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setImageTop classSiteState inputSiteState", pk, "ImageTop w3-input w3-border ");
					a("name", "setImageTop");
				} else {
					a("class", "valueImageTop w3-input w3-border classSiteState inputSiteState", pk, "ImageTop w3-input w3-border ");
					a("name", "imageTop");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setImageTop', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_imageTop')); }, function() { addError($('#", classApiMethodMethod, "_imageTop')); }); ");
				}
				a("value", strImageTop())
			.fg();

		} else {
			e("span").a("class", "varSiteState", pk, "ImageTop ").f().sx(htmImageTop()).g("span");
		}
	}

	public void htmImageTop(String classApiMethodMethod) {
		SiteState s = (SiteState)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SiteStateImageTop").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-pale-blue ").f();
							e("label").a("for", classApiMethodMethod, "_imageTop").a("class", "").f().sx("image top pixels").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputImageTop(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pale-blue ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_imageTop')); $('#", classApiMethodMethod, "_imageTop').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SiteStateForm :input[name=pk]').val() }], 'setImageTop', null, function() { addGlow($('#", classApiMethodMethod, "_imageTop')); }, function() { addError($('#", classApiMethodMethod, "_imageTop')); }); ")
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

	public List<Long> sqlAgencyKeys() {
		return agencyKeys;
	}

	public String jsonAgencyKeys() {
		return agencyKeys == null ? "" : agencyKeys.toString();
	}

	public String htmTooltipAgencyKeys() {
		return null;
	}

	public String htmAgencyKeys() {
		return agencyKeys == null ? "" : StringEscapeUtils.escapeHtml4(strAgencyKeys());
	}

	public void inputAgencyKeys(String classApiMethodMethod) {
		SiteState s = (SiteState)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("i").a("class", "far fa-search w3-xxlarge w3-cell w3-cell-middle ").f().g("i");
			if("PUTCopy".equals(classApiMethodMethod)) {
				{ e("div").f();
					e("input")
						.a("type", "checkbox")
						.a("id", classApiMethodMethod, "_agencyKeys_clear")
						.a("class", "agencyKeys_clear ")
						.fg();
					e("label").a("for", "classApiMethodMethod, \"_agencyKeys_clear").f().sx("clear").g("label");
				} g("div");
			}
			e("input")
				.a("type", "text")
				.a("placeholder", "agencies")
				.a("class", "valueObjectSuggest suggestAgencyKeys w3-input w3-border w3-cell w3-cell-middle ")
				.a("name", "setAgencyKeys")
				.a("id", classApiMethodMethod, "_agencyKeys")
				.a("autocomplete", "off");
				a("oninput", "suggestSiteStateAgencyKeys($(this).val() ? [ { 'name': 'q', 'value': 'objectSuggest:' + $(this).val() }, { 'name': 'rows', 'value': '10' }, { 'name': 'fl', 'value': 'pk,pageUrlPk,agencyCompleteName' } ] : [", pk == null ? "" : "{'name':'fq','value':'stateKey:" + pk + "'}", "], $('#listSiteStateAgencyKeys_", classApiMethodMethod, "'), ", pk, "); ");

				fg();

		} else {
		}
	}

	public void htmAgencyKeys(String classApiMethodMethod) {
		SiteState s = (SiteState)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SiteStateAgencyKeys").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("a").a("href", "/agency?fq=stateKey:", pk).a("class", "w3-cell w3-btn w3-center h4 w3-block h4 w3-pale-yellow w3-hover-pale-yellow ").f();
								e("i").a("class", "far fa-road ").f().g("i");
								sx("agencies");
							} g("a");
						} g("div");
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("h5").a("class", "w3-cell ").f();
								sx("relate  to this state");
							} g("h5");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();
								{ e("div").a("class", "w3-cell-row ").f();

								inputAgencyKeys(classApiMethodMethod);
								} g("div");
							} g("div");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
								{ e("ul").a("class", "w3-ul w3-hoverable ").a("id", "listSiteStateAgencyKeys_", classApiMethodMethod).f();
								} g("ul");
								if(
										CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), SiteAgency.ROLES)
										|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), SiteAgency.ROLES)
										) {
									if("Page".equals(classApiMethodMethod)) {
										{ e("div").a("class", "w3-cell-row ").f();
											e("button")
												.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-yellow ")
												.a("id", classApiMethodMethod, "_agencyKeys_add")
												.a("onclick", "$(this).addClass('w3-disabled'); this.disabled = true; this.innerHTML = 'Sending…'; postSiteAgencyVals({ stateKey: \"", pk, "\" }, function() {}, function() { addError($('#", classApiMethodMethod, "agencyKeys')); });")
												.f().sx("add a agency")
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

	public String sqlStateCompleteName() {
		return stateCompleteName;
	}

	public String jsonStateCompleteName() {
		return stateCompleteName == null ? "" : stateCompleteName;
	}

	public String htmTooltipStateCompleteName() {
		return null;
	}

	public String htmStateCompleteName() {
		return stateCompleteName == null ? "" : StringEscapeUtils.escapeHtml4(strStateCompleteName());
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
			else if(o instanceof Map) {
				Map<?, ?> map = (Map<?, ?>)o;
				o = map.get(v);
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
			case "agencyKeys":
				oSiteState.addAgencyKeys((Long)val);
				if(!saves.contains("agencyKeys"))
					saves.add("agencyKeys");
				return val;
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
		switch(var.toLowerCase()) {
			case "statename":
				if(val != null)
					setStateName(val);
				saves.add("stateName");
				return val;
			case "stateabbreviation":
				if(val != null)
					setStateAbbreviation(val);
				saves.add("stateAbbreviation");
				return val;
			case "imageleft":
				if(val != null)
					setImageLeft(val);
				saves.add("imageLeft");
				return val;
			case "imagetop":
				if(val != null)
					setImageTop(val);
				saves.add("imageTop");
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
					o = defineSiteState(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSiteState(String var, Object val) {
		switch(var.toLowerCase()) {
			case "statename":
				if(val instanceof String)
					setStateName((String)val);
				saves.add("stateName");
				return val;
			case "stateabbreviation":
				if(val instanceof String)
					setStateAbbreviation((String)val);
				saves.add("stateAbbreviation");
				return val;
			case "imageleft":
				if(val instanceof Integer)
					setImageLeft((Integer)val);
				saves.add("imageLeft");
				return val;
			case "imagetop":
				if(val instanceof Integer)
					setImageTop((Integer)val);
				saves.add("imageTop");
				return val;
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

			if(saves.contains("stateKey")) {
				Long stateKey = (Long)solrDocument.get("stateKey_stored_long");
				if(stateKey != null)
					oSiteState.setStateKey(stateKey);
			}

			if(saves.contains("stateName")) {
				String stateName = (String)solrDocument.get("stateName_stored_string");
				if(stateName != null)
					oSiteState.setStateName(stateName);
			}

			if(saves.contains("stateAbbreviation")) {
				String stateAbbreviation = (String)solrDocument.get("stateAbbreviation_stored_string");
				if(stateAbbreviation != null)
					oSiteState.setStateAbbreviation(stateAbbreviation);
			}

			if(saves.contains("imageLeft")) {
				Integer imageLeft = (Integer)solrDocument.get("imageLeft_stored_int");
				if(imageLeft != null)
					oSiteState.setImageLeft(imageLeft);
			}

			if(saves.contains("imageTop")) {
				Integer imageTop = (Integer)solrDocument.get("imageTop_stored_int");
				if(imageTop != null)
					oSiteState.setImageTop(imageTop);
			}

			List<Long> agencyKeys = (List<Long>)solrDocument.get("agencyKeys_stored_longs");
			if(agencyKeys != null)
				oSiteState.agencyKeys.addAll(agencyKeys);

			if(saves.contains("stateCompleteName")) {
				String stateCompleteName = (String)solrDocument.get("stateCompleteName_stored_string");
				if(stateCompleteName != null)
					oSiteState.setStateCompleteName(stateCompleteName);
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
		if(stateKey != null) {
			document.addField("stateKey_indexed_long", stateKey);
			document.addField("stateKey_stored_long", stateKey);
		}
		if(stateName != null) {
			document.addField("stateName_indexed_string", stateName);
			document.addField("stateName_stored_string", stateName);
		}
		if(stateAbbreviation != null) {
			document.addField("stateAbbreviation_indexed_string", stateAbbreviation);
			document.addField("stateAbbreviation_stored_string", stateAbbreviation);
		}
		if(imageLeft != null) {
			document.addField("imageLeft_indexed_int", imageLeft);
			document.addField("imageLeft_stored_int", imageLeft);
		}
		if(imageTop != null) {
			document.addField("imageTop_indexed_int", imageTop);
			document.addField("imageTop_stored_int", imageTop);
		}
		if(agencyKeys != null) {
			for(java.lang.Long o : agencyKeys) {
				document.addField("agencyKeys_indexed_longs", o);
			}
			for(java.lang.Long o : agencyKeys) {
				document.addField("agencyKeys_stored_longs", o);
			}
		}
		if(stateCompleteName != null) {
			document.addField("stateCompleteName_indexed_string", stateCompleteName);
			document.addField("stateCompleteName_stored_string", stateCompleteName);
		}
		super.indexCluster(document);

	}

	public void unindexSiteState() {
		try {
		SiteRequestEnUS siteRequest = new SiteRequestEnUS();
			siteRequest.initDeepSiteRequestEnUS();
			SiteContextEnUS siteContext = new SiteContextEnUS();
			siteContext.initDeepSiteContextEnUS();
			siteRequest.setSiteContext_(siteContext);
			siteRequest.setSiteConfig_(siteContext.getSiteConfig());
			initDeepSiteState(siteRequest);
			SolrClient solrClient = siteContext.getSolrClient();
			solrClient.deleteById(id.toString());
			solrClient.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public static String varIndexedSiteState(String entityVar) {
		switch(entityVar) {
			case "stateKey":
				return "stateKey_indexed_long";
			case "stateName":
				return "stateName_indexed_string";
			case "stateAbbreviation":
				return "stateAbbreviation_indexed_string";
			case "imageLeft":
				return "imageLeft_indexed_int";
			case "imageTop":
				return "imageTop_indexed_int";
			case "agencyKeys":
				return "agencyKeys_indexed_longs";
			case "stateCompleteName":
				return "stateCompleteName_indexed_string";
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

		Long stateKey = (Long)solrDocument.get("stateKey_stored_long");
		if(stateKey != null)
			oSiteState.setStateKey(stateKey);

		String stateName = (String)solrDocument.get("stateName_stored_string");
		if(stateName != null)
			oSiteState.setStateName(stateName);

		String stateAbbreviation = (String)solrDocument.get("stateAbbreviation_stored_string");
		if(stateAbbreviation != null)
			oSiteState.setStateAbbreviation(stateAbbreviation);

		Integer imageLeft = (Integer)solrDocument.get("imageLeft_stored_int");
		if(imageLeft != null)
			oSiteState.setImageLeft(imageLeft);

		Integer imageTop = (Integer)solrDocument.get("imageTop_stored_int");
		if(imageTop != null)
			oSiteState.setImageTop(imageTop);

		List<Long> agencyKeys = (List<Long>)solrDocument.get("agencyKeys_stored_longs");
		if(agencyKeys != null)
			oSiteState.agencyKeys.addAll(agencyKeys);

		String stateCompleteName = (String)solrDocument.get("stateCompleteName_stored_string");
		if(stateCompleteName != null)
			oSiteState.setStateCompleteName(stateCompleteName);

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
			if(!Objects.equals(stateKey, original.getStateKey()))
				apiRequest.addVars("stateKey");
			if(!Objects.equals(stateName, original.getStateName()))
				apiRequest.addVars("stateName");
			if(!Objects.equals(stateAbbreviation, original.getStateAbbreviation()))
				apiRequest.addVars("stateAbbreviation");
			if(!Objects.equals(imageLeft, original.getImageLeft()))
				apiRequest.addVars("imageLeft");
			if(!Objects.equals(imageTop, original.getImageTop()))
				apiRequest.addVars("imageTop");
			if(!Objects.equals(agencyKeys, original.getAgencyKeys()))
				apiRequest.addVars("agencyKeys");
			if(!Objects.equals(stateCompleteName, original.getStateCompleteName()))
				apiRequest.addVars("stateCompleteName");
			super.apiRequestCluster();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), stateKey, stateName, stateAbbreviation, imageLeft, imageTop, agencyKeys, stateCompleteName);
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
		return super.equals(o)
				&& Objects.equals( stateKey, that.stateKey )
				&& Objects.equals( stateName, that.stateName )
				&& Objects.equals( stateAbbreviation, that.stateAbbreviation )
				&& Objects.equals( imageLeft, that.imageLeft )
				&& Objects.equals( imageTop, that.imageTop )
				&& Objects.equals( agencyKeys, that.agencyKeys )
				&& Objects.equals( stateCompleteName, that.stateCompleteName );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("SiteState { ");
		sb.append( "stateKey: " ).append(stateKey);
		sb.append( ", stateName: \"" ).append(stateName).append( "\"" );
		sb.append( ", stateAbbreviation: \"" ).append(stateAbbreviation).append( "\"" );
		sb.append( ", imageLeft: " ).append(imageLeft);
		sb.append( ", imageTop: " ).append(imageTop);
		sb.append( ", agencyKeys: " ).append(agencyKeys);
		sb.append( ", stateCompleteName: \"" ).append(stateCompleteName).append( "\"" );
		sb.append(" }");
		return sb.toString();
	}
}
