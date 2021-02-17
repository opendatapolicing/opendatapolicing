package com.opendatapolicing.enus.design;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.Date;
import com.opendatapolicing.enus.html.part.HtmlPart;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import io.vertx.core.logging.LoggerFactory;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import java.lang.Long;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.Boolean;
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
import com.opendatapolicing.enus.design.PageDesign;
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
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.design.PageDesign&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class PageDesignGen<DEV> extends Cluster {
	protected static final Logger LOGGER = LoggerFactory.getLogger(PageDesign.class);

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("");

	public static final String PageDesign_AName = "a page design";
	public static final String PageDesign_This = "this ";
	public static final String PageDesign_ThisName = "this page design";
	public static final String PageDesign_A = "a ";
	public static final String PageDesign_TheName = "the page design";
	public static final String PageDesign_NameSingular = "page design";
	public static final String PageDesign_NamePlural = "page designs";
	public static final String PageDesign_NameActual = "current page design";
	public static final String PageDesign_AllName = "all the page designs";
	public static final String PageDesign_SearchAllNameBy = "search page designs by ";
	public static final String PageDesign_Title = "page designs";
	public static final String PageDesign_ThePluralName = "the page designs";
	public static final String PageDesign_NoNameFound = "no page design found";
	public static final String PageDesign_NameVar = "page-design";
	public static final String PageDesign_OfName = "of page design";
	public static final String PageDesign_ANameAdjective = "a page design";
	public static final String PageDesign_NameAdjectiveSingular = "page design";
	public static final String PageDesign_NameAdjectivePlural = "page designs";
	public static final String PageDesign_Color = "khaki";
	public static final String PageDesign_IconGroup = "regular";
	public static final String PageDesign_IconName = "drafting-compass";
	public static final Integer PageDesign_Rows = 100;

	///////////////////
	// pageDesignKey //
	///////////////////

	/**	 The entity pageDesignKey
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long pageDesignKey;
	@JsonIgnore
	public Wrap<Long> pageDesignKeyWrap = new Wrap<Long>().p(this).c(Long.class).var("pageDesignKey").o(pageDesignKey);

	/**	<br/> The entity pageDesignKey
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.design.PageDesign&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageDesignKey">Find the entity pageDesignKey in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageDesignKey(Wrap<Long> c);

	public Long getPageDesignKey() {
		return pageDesignKey;
	}

	public void setPageDesignKey(Long pageDesignKey) {
		this.pageDesignKey = pageDesignKey;
		this.pageDesignKeyWrap.alreadyInitialized = true;
	}
	public void setPageDesignKey(String o) {
		this.pageDesignKey = PageDesign.staticSetPageDesignKey(siteRequest_, o);
		this.pageDesignKeyWrap.alreadyInitialized = true;
	}
	public static Long staticSetPageDesignKey(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected PageDesign pageDesignKeyInit() {
		if(!pageDesignKeyWrap.alreadyInitialized) {
			_pageDesignKey(pageDesignKeyWrap);
			if(pageDesignKey == null)
				setPageDesignKey(pageDesignKeyWrap.o);
		}
		pageDesignKeyWrap.alreadyInitialized(true);
		return (PageDesign)this;
	}

	public static Long staticSolrPageDesignKey(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrPageDesignKey(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageDesignKey(SiteRequestEnUS siteRequest_, String o) {
		return PageDesign.staticSolrStrPageDesignKey(siteRequest_, PageDesign.staticSolrPageDesignKey(siteRequest_, PageDesign.staticSetPageDesignKey(siteRequest_, o)));
	}

	public Long solrPageDesignKey() {
		return PageDesign.staticSolrPageDesignKey(siteRequest_, pageDesignKey);
	}

	public String strPageDesignKey() {
		return pageDesignKey == null ? "" : pageDesignKey.toString();
	}

	public Long sqlPageDesignKey() {
		return pageDesignKey;
	}

	public String jsonPageDesignKey() {
		return pageDesignKey == null ? "" : pageDesignKey.toString();
	}

	public String nomAffichagePageDesignKey() {
		return "key";
	}

	public String htmTooltipPageDesignKey() {
		return null;
	}

	public String htmPageDesignKey() {
		return pageDesignKey == null ? "" : StringEscapeUtils.escapeHtml4(strPageDesignKey());
	}

	/////////////////////
	// childDesignKeys //
	/////////////////////

	/**	 The entity childDesignKeys
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> childDesignKeys = new ArrayList<Long>();
	@JsonIgnore
	public Wrap<List<Long>> childDesignKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("childDesignKeys").o(childDesignKeys);

	/**	<br/> The entity childDesignKeys
	 *  It is constructed before being initialized with the constructor by default List<Long>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.design.PageDesign&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:childDesignKeys">Find the entity childDesignKeys in Solr</a>
	 * <br/>
	 * @param childDesignKeys is the entity already constructed. 
	 **/
	protected abstract void _childDesignKeys(List<Long> c);

	public List<Long> getChildDesignKeys() {
		return childDesignKeys;
	}

	public void setChildDesignKeys(List<Long> childDesignKeys) {
		this.childDesignKeys = childDesignKeys;
		this.childDesignKeysWrap.alreadyInitialized = true;
	}
	public void setChildDesignKeys(String o) {
		Long l = PageDesign.staticSetChildDesignKeys(siteRequest_, o);
		if(l != null)
			addChildDesignKeys(l);
		this.childDesignKeysWrap.alreadyInitialized = true;
	}
	public static Long staticSetChildDesignKeys(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	public PageDesign addChildDesignKeys(Long...objets) {
		for(Long o : objets) {
			addChildDesignKeys(o);
		}
		return (PageDesign)this;
	}
	public PageDesign addChildDesignKeys(Long o) {
		if(o != null && !childDesignKeys.contains(o))
			this.childDesignKeys.add(o);
		return (PageDesign)this;
	}
	public void setChildDesignKeys(JsonArray objets) {
		childDesignKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addChildDesignKeys(o);
		}
	}
	public PageDesign addChildDesignKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addChildDesignKeys(p);
		}
		return (PageDesign)this;
	}
	protected PageDesign childDesignKeysInit() {
		if(!childDesignKeysWrap.alreadyInitialized) {
			_childDesignKeys(childDesignKeys);
		}
		childDesignKeysWrap.alreadyInitialized(true);
		return (PageDesign)this;
	}

	public static Long staticSolrChildDesignKeys(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrChildDesignKeys(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqChildDesignKeys(SiteRequestEnUS siteRequest_, String o) {
		return PageDesign.staticSolrStrChildDesignKeys(siteRequest_, PageDesign.staticSolrChildDesignKeys(siteRequest_, PageDesign.staticSetChildDesignKeys(siteRequest_, o)));
	}

	public List<Long> solrChildDesignKeys() {
		List<Long> l = new ArrayList<Long>();
		for(Long o : childDesignKeys) {
			l.add(PageDesign.staticSolrChildDesignKeys(siteRequest_, o));
		}
		return l;
	}

	public String strChildDesignKeys() {
		return childDesignKeys == null ? "" : childDesignKeys.toString();
	}

	public List<Long> sqlChildDesignKeys() {
		return childDesignKeys;
	}

	public String jsonChildDesignKeys() {
		return childDesignKeys == null ? "" : childDesignKeys.toString();
	}

	public String nomAffichageChildDesignKeys() {
		return "child designs";
	}

	public String htmTooltipChildDesignKeys() {
		return null;
	}

	public String htmChildDesignKeys() {
		return childDesignKeys == null ? "" : StringEscapeUtils.escapeHtml4(strChildDesignKeys());
	}

	//////////////////////
	// parentDesignKeys //
	//////////////////////

	/**	 The entity parentDesignKeys
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> parentDesignKeys = new ArrayList<Long>();
	@JsonIgnore
	public Wrap<List<Long>> parentDesignKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("parentDesignKeys").o(parentDesignKeys);

	/**	<br/> The entity parentDesignKeys
	 *  It is constructed before being initialized with the constructor by default List<Long>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.design.PageDesign&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:parentDesignKeys">Find the entity parentDesignKeys in Solr</a>
	 * <br/>
	 * @param parentDesignKeys is the entity already constructed. 
	 **/
	protected abstract void _parentDesignKeys(List<Long> c);

	public List<Long> getParentDesignKeys() {
		return parentDesignKeys;
	}

	public void setParentDesignKeys(List<Long> parentDesignKeys) {
		this.parentDesignKeys = parentDesignKeys;
		this.parentDesignKeysWrap.alreadyInitialized = true;
	}
	public void setParentDesignKeys(String o) {
		Long l = PageDesign.staticSetParentDesignKeys(siteRequest_, o);
		if(l != null)
			addParentDesignKeys(l);
		this.parentDesignKeysWrap.alreadyInitialized = true;
	}
	public static Long staticSetParentDesignKeys(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	public PageDesign addParentDesignKeys(Long...objets) {
		for(Long o : objets) {
			addParentDesignKeys(o);
		}
		return (PageDesign)this;
	}
	public PageDesign addParentDesignKeys(Long o) {
		if(o != null && !parentDesignKeys.contains(o))
			this.parentDesignKeys.add(o);
		return (PageDesign)this;
	}
	public void setParentDesignKeys(JsonArray objets) {
		parentDesignKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addParentDesignKeys(o);
		}
	}
	public PageDesign addParentDesignKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addParentDesignKeys(p);
		}
		return (PageDesign)this;
	}
	protected PageDesign parentDesignKeysInit() {
		if(!parentDesignKeysWrap.alreadyInitialized) {
			_parentDesignKeys(parentDesignKeys);
		}
		parentDesignKeysWrap.alreadyInitialized(true);
		return (PageDesign)this;
	}

	public static Long staticSolrParentDesignKeys(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrParentDesignKeys(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqParentDesignKeys(SiteRequestEnUS siteRequest_, String o) {
		return PageDesign.staticSolrStrParentDesignKeys(siteRequest_, PageDesign.staticSolrParentDesignKeys(siteRequest_, PageDesign.staticSetParentDesignKeys(siteRequest_, o)));
	}

	public List<Long> solrParentDesignKeys() {
		List<Long> l = new ArrayList<Long>();
		for(Long o : parentDesignKeys) {
			l.add(PageDesign.staticSolrParentDesignKeys(siteRequest_, o));
		}
		return l;
	}

	public String strParentDesignKeys() {
		return parentDesignKeys == null ? "" : parentDesignKeys.toString();
	}

	public List<Long> sqlParentDesignKeys() {
		return parentDesignKeys;
	}

	public String jsonParentDesignKeys() {
		return parentDesignKeys == null ? "" : parentDesignKeys.toString();
	}

	public String nomAffichageParentDesignKeys() {
		return "parent designs";
	}

	public String htmTooltipParentDesignKeys() {
		return null;
	}

	public String htmParentDesignKeys() {
		return parentDesignKeys == null ? "" : StringEscapeUtils.escapeHtml4(strParentDesignKeys());
	}

	public void inputParentDesignKeys(String classApiMethodMethod) {
		PageDesign s = (PageDesign)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("i").a("class", "far fa-search w3-xxlarge w3-cell w3-cell-middle ").f().g("i");
			if("PUTCopy".equals(classApiMethodMethod)) {
				{ e("div").f();
					e("input")
						.a("type", "checkbox")
						.a("id", classApiMethodMethod, "_parentDesignKeys_clear")
						.a("class", "parentDesignKeys_clear ")
						.fg();
					e("label").a("for", "classApiMethodMethod, \"_parentDesignKeys_clear").f().sx("clear").g("label");
				} g("div");
			}
			e("input")
				.a("type", "text")
				.a("placeholder", "parent designs")
				.a("class", "valueObjectSuggest suggestParentDesignKeys w3-input w3-border w3-cell w3-cell-middle ")
				.a("name", "setParentDesignKeys")
				.a("id", classApiMethodMethod, "_parentDesignKeys")
				.a("autocomplete", "off");
				a("oninput", "suggestPageDesignParentDesignKeys($(this).val() ? [ { 'name': 'q', 'value': 'objectSuggest:' + $(this).val() }, { 'name': 'rows', 'value': '10' }, { 'name': 'fl', 'value': 'pk,pageUrlPk,pageDesignCompleteName' } ] : [", pk == null ? "" : "{'name':'fq','value':'childDesignKeys:" + pk + "'}", "], $('#listPageDesignParentDesignKeys_", classApiMethodMethod, "'), ", pk, "); ");

				fg();

		} else {
		}
	}

	public void htmParentDesignKeys(String classApiMethodMethod) {
		PageDesign s = (PageDesign)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "PageDesignParentDesignKeys").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("a").a("href", "/page-design?fq=childDesignKeys:", pk).a("class", "w3-cell w3-btn w3-center h4 w3-block h4 w3-khaki w3-hover-khaki ").f();
								e("i").a("class", "far fa-drafting-compass ").f().g("i");
								sx("parent designs");
							} g("a");
						} g("div");
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("h5").a("class", "w3-cell ").f();
								sx("relate  to this page design");
							} g("h5");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();
								{ e("div").a("class", "w3-cell-row ").f();

								inputParentDesignKeys(classApiMethodMethod);
								} g("div");
							} g("div");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
								{ e("ul").a("class", "w3-ul w3-hoverable ").a("id", "listPageDesignParentDesignKeys_", classApiMethodMethod).f();
								} g("ul");
								if(
										CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), PageDesign.ROLES)
										|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), PageDesign.ROLES)
										) {
									if("Page".equals(classApiMethodMethod)) {
										{ e("div").a("class", "w3-cell-row ").f();
											e("button")
												.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-khaki ")
												.a("id", classApiMethodMethod, "_parentDesignKeys_add")
												.a("onclick", "$(this).addClass('w3-disabled'); this.disabled = true; this.innerHTML = 'Sending…'; postPageDesignVals({ childDesignKeys: [ \"", pk, "\" ] }, function() {}, function() { addError($('#", classApiMethodMethod, "parentDesignKeys')); });")
												.f().sx("add a page design")
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
	// htmlPartKeys //
	//////////////////

	/**	 The entity htmlPartKeys
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> htmlPartKeys = new ArrayList<Long>();
	@JsonIgnore
	public Wrap<List<Long>> htmlPartKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("htmlPartKeys").o(htmlPartKeys);

	/**	<br/> The entity htmlPartKeys
	 *  It is constructed before being initialized with the constructor by default List<Long>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.design.PageDesign&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlPartKeys">Find the entity htmlPartKeys in Solr</a>
	 * <br/>
	 * @param htmlPartKeys is the entity already constructed. 
	 **/
	protected abstract void _htmlPartKeys(List<Long> o);

	public List<Long> getHtmlPartKeys() {
		return htmlPartKeys;
	}

	public void setHtmlPartKeys(List<Long> htmlPartKeys) {
		this.htmlPartKeys = htmlPartKeys;
		this.htmlPartKeysWrap.alreadyInitialized = true;
	}
	public void setHtmlPartKeys(String o) {
		Long l = PageDesign.staticSetHtmlPartKeys(siteRequest_, o);
		if(l != null)
			addHtmlPartKeys(l);
		this.htmlPartKeysWrap.alreadyInitialized = true;
	}
	public static Long staticSetHtmlPartKeys(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	public PageDesign addHtmlPartKeys(Long...objets) {
		for(Long o : objets) {
			addHtmlPartKeys(o);
		}
		return (PageDesign)this;
	}
	public PageDesign addHtmlPartKeys(Long o) {
		if(o != null && !htmlPartKeys.contains(o))
			this.htmlPartKeys.add(o);
		return (PageDesign)this;
	}
	public void setHtmlPartKeys(JsonArray objets) {
		htmlPartKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addHtmlPartKeys(o);
		}
	}
	public PageDesign addHtmlPartKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addHtmlPartKeys(p);
		}
		return (PageDesign)this;
	}
	protected PageDesign htmlPartKeysInit() {
		if(!htmlPartKeysWrap.alreadyInitialized) {
			_htmlPartKeys(htmlPartKeys);
		}
		htmlPartKeysWrap.alreadyInitialized(true);
		return (PageDesign)this;
	}

	public static Long staticSolrHtmlPartKeys(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrHtmlPartKeys(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqHtmlPartKeys(SiteRequestEnUS siteRequest_, String o) {
		return PageDesign.staticSolrStrHtmlPartKeys(siteRequest_, PageDesign.staticSolrHtmlPartKeys(siteRequest_, PageDesign.staticSetHtmlPartKeys(siteRequest_, o)));
	}

	public List<Long> solrHtmlPartKeys() {
		List<Long> l = new ArrayList<Long>();
		for(Long o : htmlPartKeys) {
			l.add(PageDesign.staticSolrHtmlPartKeys(siteRequest_, o));
		}
		return l;
	}

	public String strHtmlPartKeys() {
		return htmlPartKeys == null ? "" : htmlPartKeys.toString();
	}

	public List<Long> sqlHtmlPartKeys() {
		return htmlPartKeys;
	}

	public String jsonHtmlPartKeys() {
		return htmlPartKeys == null ? "" : htmlPartKeys.toString();
	}

	public String nomAffichageHtmlPartKeys() {
		return "parts";
	}

	public String htmTooltipHtmlPartKeys() {
		return null;
	}

	public String htmHtmlPartKeys() {
		return htmlPartKeys == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlPartKeys());
	}

	public void inputHtmlPartKeys(String classApiMethodMethod) {
		PageDesign s = (PageDesign)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("i").a("class", "far fa-search w3-xxlarge w3-cell w3-cell-middle ").f().g("i");
			if("PUTCopy".equals(classApiMethodMethod)) {
				{ e("div").f();
					e("input")
						.a("type", "checkbox")
						.a("id", classApiMethodMethod, "_htmlPartKeys_clear")
						.a("class", "htmlPartKeys_clear ")
						.fg();
					e("label").a("for", "classApiMethodMethod, \"_htmlPartKeys_clear").f().sx("clear").g("label");
				} g("div");
			}
			e("input")
				.a("type", "text")
				.a("placeholder", "parts")
				.a("class", "valueObjectSuggest suggestHtmlPartKeys w3-input w3-border w3-cell w3-cell-middle ")
				.a("name", "setHtmlPartKeys")
				.a("id", classApiMethodMethod, "_htmlPartKeys")
				.a("autocomplete", "off");
				a("oninput", "suggestPageDesignHtmlPartKeys($(this).val() ? [ { 'name': 'q', 'value': 'objectSuggest:' + $(this).val() }, { 'name': 'rows', 'value': '10' }, { 'name': 'fl', 'value': 'pk,pageUrlPk,objectTitle' } ] : [", pk == null ? "" : "{'name':'fq','value':'pageDesignKeys:" + pk + "'}", "], $('#listPageDesignHtmlPartKeys_", classApiMethodMethod, "'), ", pk, "); ");

				fg();

		} else {
		}
	}

	public void htmHtmlPartKeys(String classApiMethodMethod) {
		PageDesign s = (PageDesign)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "PageDesignHtmlPartKeys").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("a").a("href", "/html-part?fq=pageDesignKeys:", pk).a("class", "w3-cell w3-btn w3-center h4 w3-block h4 w3-khaki w3-hover-khaki ").f();
								e("i").a("class", "far fa-puzzle-piece ").f().g("i");
								sx("parts");
							} g("a");
						} g("div");
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("h5").a("class", "w3-cell ").f();
								sx("relate  to this page design");
							} g("h5");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();
								{ e("div").a("class", "w3-cell-row ").f();

								inputHtmlPartKeys(classApiMethodMethod);
								} g("div");
							} g("div");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
								{ e("ul").a("class", "w3-ul w3-hoverable ").a("id", "listPageDesignHtmlPartKeys_", classApiMethodMethod).f();
								} g("ul");
								if(
										CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), HtmlPart.ROLES)
										|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), HtmlPart.ROLES)
										) {
									if("Page".equals(classApiMethodMethod)) {
										{ e("div").a("class", "w3-cell-row ").f();
											e("button")
												.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-khaki ")
												.a("id", classApiMethodMethod, "_htmlPartKeys_add")
												.a("onclick", "$(this).addClass('w3-disabled'); this.disabled = true; this.innerHTML = 'Sending…'; postHtmlPartVals({ pageDesignKeys: [ \"", pk, "\" ] }, function() {}, function() { addError($('#", classApiMethodMethod, "htmlPartKeys')); });")
												.f().sx("add an HTML part")
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

	////////////////////////////
	// pageDesignCompleteName //
	////////////////////////////

	/**	 The entity pageDesignCompleteName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageDesignCompleteName;
	@JsonIgnore
	public Wrap<String> pageDesignCompleteNameWrap = new Wrap<String>().p(this).c(String.class).var("pageDesignCompleteName").o(pageDesignCompleteName);

	/**	<br/> The entity pageDesignCompleteName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.design.PageDesign&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageDesignCompleteName">Find the entity pageDesignCompleteName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageDesignCompleteName(Wrap<String> c);

	public String getPageDesignCompleteName() {
		return pageDesignCompleteName;
	}
	public void setPageDesignCompleteName(String o) {
		this.pageDesignCompleteName = PageDesign.staticSetPageDesignCompleteName(siteRequest_, o);
		this.pageDesignCompleteNameWrap.alreadyInitialized = true;
	}
	public static String staticSetPageDesignCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageDesign pageDesignCompleteNameInit() {
		if(!pageDesignCompleteNameWrap.alreadyInitialized) {
			_pageDesignCompleteName(pageDesignCompleteNameWrap);
			if(pageDesignCompleteName == null)
				setPageDesignCompleteName(pageDesignCompleteNameWrap.o);
		}
		pageDesignCompleteNameWrap.alreadyInitialized(true);
		return (PageDesign)this;
	}

	public static String staticSolrPageDesignCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageDesignCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageDesignCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return PageDesign.staticSolrStrPageDesignCompleteName(siteRequest_, PageDesign.staticSolrPageDesignCompleteName(siteRequest_, PageDesign.staticSetPageDesignCompleteName(siteRequest_, o)));
	}

	public String solrPageDesignCompleteName() {
		return PageDesign.staticSolrPageDesignCompleteName(siteRequest_, pageDesignCompleteName);
	}

	public String strPageDesignCompleteName() {
		return pageDesignCompleteName == null ? "" : pageDesignCompleteName;
	}

	public String sqlPageDesignCompleteName() {
		return pageDesignCompleteName;
	}

	public String jsonPageDesignCompleteName() {
		return pageDesignCompleteName == null ? "" : pageDesignCompleteName;
	}

	public String nomAffichagePageDesignCompleteName() {
		return "name";
	}

	public String htmTooltipPageDesignCompleteName() {
		return null;
	}

	public String htmPageDesignCompleteName() {
		return pageDesignCompleteName == null ? "" : StringEscapeUtils.escapeHtml4(strPageDesignCompleteName());
	}

	public void inputPageDesignCompleteName(String classApiMethodMethod) {
		PageDesign s = (PageDesign)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "name")
				.a("id", classApiMethodMethod, "_pageDesignCompleteName");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setPageDesignCompleteName classPageDesign inputPageDesign", pk, "PageDesignCompleteName w3-input w3-border ");
					a("name", "setPageDesignCompleteName");
				} else {
					a("class", "valuePageDesignCompleteName w3-input w3-border classPageDesign inputPageDesign", pk, "PageDesignCompleteName w3-input w3-border ");
					a("name", "pageDesignCompleteName");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setPageDesignCompleteName', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_pageDesignCompleteName')); }, function() { addError($('#", classApiMethodMethod, "_pageDesignCompleteName')); }); ");
				}
				a("value", strPageDesignCompleteName())
			.fg();

		} else {
			e("span").a("class", "varPageDesign", pk, "PageDesignCompleteName ").f().sx(htmPageDesignCompleteName()).g("span");
		}
	}

	public void htmPageDesignCompleteName(String classApiMethodMethod) {
		PageDesign s = (PageDesign)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "PageDesignPageDesignCompleteName").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_pageDesignCompleteName").a("class", "").f().sx("name").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPageDesignCompleteName(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-khaki ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_pageDesignCompleteName')); $('#", classApiMethodMethod, "_pageDesignCompleteName').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#PageDesignForm :input[name=pk]').val() }], 'setPageDesignCompleteName', null, function() { addGlow($('#", classApiMethodMethod, "_pageDesignCompleteName')); }, function() { addError($('#", classApiMethodMethod, "_pageDesignCompleteName')); }); ")
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
	// designHidden //
	//////////////////

	/**	 The entity designHidden
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean designHidden;
	@JsonIgnore
	public Wrap<Boolean> designHiddenWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("designHidden").o(designHidden);

	/**	<br/> The entity designHidden
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.design.PageDesign&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:designHidden">Find the entity designHidden in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _designHidden(Wrap<Boolean> c);

	public Boolean getDesignHidden() {
		return designHidden;
	}

	public void setDesignHidden(Boolean designHidden) {
		this.designHidden = designHidden;
		this.designHiddenWrap.alreadyInitialized = true;
	}
	public void setDesignHidden(String o) {
		this.designHidden = PageDesign.staticSetDesignHidden(siteRequest_, o);
		this.designHiddenWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetDesignHidden(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected PageDesign designHiddenInit() {
		if(!designHiddenWrap.alreadyInitialized) {
			_designHidden(designHiddenWrap);
			if(designHidden == null)
				setDesignHidden(designHiddenWrap.o);
		}
		designHiddenWrap.alreadyInitialized(true);
		return (PageDesign)this;
	}

	public static Boolean staticSolrDesignHidden(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrDesignHidden(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqDesignHidden(SiteRequestEnUS siteRequest_, String o) {
		return PageDesign.staticSolrStrDesignHidden(siteRequest_, PageDesign.staticSolrDesignHidden(siteRequest_, PageDesign.staticSetDesignHidden(siteRequest_, o)));
	}

	public Boolean solrDesignHidden() {
		return PageDesign.staticSolrDesignHidden(siteRequest_, designHidden);
	}

	public String strDesignHidden() {
		return designHidden == null ? "" : designHidden.toString();
	}

	public Boolean sqlDesignHidden() {
		return designHidden;
	}

	public String jsonDesignHidden() {
		return designHidden == null ? "" : designHidden.toString();
	}

	public String nomAffichageDesignHidden() {
		return "hidden";
	}

	public String htmTooltipDesignHidden() {
		return null;
	}

	public String htmDesignHidden() {
		return designHidden == null ? "" : StringEscapeUtils.escapeHtml4(strDesignHidden());
	}

	public void inputDesignHidden(String classApiMethodMethod) {
		PageDesign s = (PageDesign)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			if("Page".equals(classApiMethodMethod)) {
				e("input")
					.a("type", "checkbox")
					.a("id", classApiMethodMethod, "_designHidden")
					.a("value", "true");
			} else {
				e("select")
					.a("id", classApiMethodMethod, "_designHidden");
			}
			if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
				a("class", "setDesignHidden classPageDesign inputPageDesign", pk, "DesignHidden w3-input w3-border ");
				a("name", "setDesignHidden");
			} else {
				a("class", "valueDesignHidden classPageDesign inputPageDesign", pk, "DesignHidden w3-input w3-border ");
				a("name", "designHidden");
			}
			if("Page".equals(classApiMethodMethod)) {
				a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setDesignHidden', $(this).prop('checked'), function() { addGlow($('#", classApiMethodMethod, "_designHidden')); }, function() { addError($('#", classApiMethodMethod, "_designHidden')); }); ");
			}
			if("Page".equals(classApiMethodMethod)) {
				if(getDesignHidden() != null && getDesignHidden())
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
			e("span").a("class", "varPageDesign", pk, "DesignHidden ").f().sx(htmDesignHidden()).g("span");
		}
	}

	public void htmDesignHidden(String classApiMethodMethod) {
		PageDesign s = (PageDesign)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "PageDesignDesignHidden").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_designHidden").a("class", "").f().sx("hidden").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputDesignHidden(classApiMethodMethod);
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	/////////////////////
	// pageContentType //
	/////////////////////

	/**	 The entity pageContentType
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageContentType;
	@JsonIgnore
	public Wrap<String> pageContentTypeWrap = new Wrap<String>().p(this).c(String.class).var("pageContentType").o(pageContentType);

	/**	<br/> The entity pageContentType
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.design.PageDesign&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageContentType">Find the entity pageContentType in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageContentType(Wrap<String> c);

	public String getPageContentType() {
		return pageContentType;
	}
	public void setPageContentType(String o) {
		this.pageContentType = PageDesign.staticSetPageContentType(siteRequest_, o);
		this.pageContentTypeWrap.alreadyInitialized = true;
	}
	public static String staticSetPageContentType(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected PageDesign pageContentTypeInit() {
		if(!pageContentTypeWrap.alreadyInitialized) {
			_pageContentType(pageContentTypeWrap);
			if(pageContentType == null)
				setPageContentType(pageContentTypeWrap.o);
		}
		pageContentTypeWrap.alreadyInitialized(true);
		return (PageDesign)this;
	}

	public static String staticSolrPageContentType(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageContentType(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageContentType(SiteRequestEnUS siteRequest_, String o) {
		return PageDesign.staticSolrStrPageContentType(siteRequest_, PageDesign.staticSolrPageContentType(siteRequest_, PageDesign.staticSetPageContentType(siteRequest_, o)));
	}

	public String solrPageContentType() {
		return PageDesign.staticSolrPageContentType(siteRequest_, pageContentType);
	}

	public String strPageContentType() {
		return pageContentType == null ? "" : pageContentType;
	}

	public String sqlPageContentType() {
		return pageContentType;
	}

	public String jsonPageContentType() {
		return pageContentType == null ? "" : pageContentType;
	}

	public String nomAffichagePageContentType() {
		return "content type";
	}

	public String htmTooltipPageContentType() {
		return null;
	}

	public String htmPageContentType() {
		return pageContentType == null ? "" : StringEscapeUtils.escapeHtml4(strPageContentType());
	}

	public void inputPageContentType(String classApiMethodMethod) {
		PageDesign s = (PageDesign)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "content type")
				.a("id", classApiMethodMethod, "_pageContentType");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setPageContentType classPageDesign inputPageDesign", pk, "PageContentType w3-input w3-border ");
					a("name", "setPageContentType");
				} else {
					a("class", "valuePageContentType w3-input w3-border classPageDesign inputPageDesign", pk, "PageContentType w3-input w3-border ");
					a("name", "pageContentType");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setPageContentType', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_pageContentType')); }, function() { addError($('#", classApiMethodMethod, "_pageContentType')); }); ");
				}
				a("value", strPageContentType())
			.fg();

		} else {
			e("span").a("class", "varPageDesign", pk, "PageContentType ").f().sx(htmPageContentType()).g("span");
		}
	}

	public void htmPageContentType(String classApiMethodMethod) {
		PageDesign s = (PageDesign)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "PageDesignPageContentType").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_pageContentType").a("class", "").f().sx("content type").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPageContentType(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-khaki ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_pageContentType')); $('#", classApiMethodMethod, "_pageContentType').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#PageDesignForm :input[name=pk]').val() }], 'setPageContentType', null, function() { addGlow($('#", classApiMethodMethod, "_pageContentType')); }, function() { addError($('#", classApiMethodMethod, "_pageContentType')); }); ")
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
	// initDeep //
	//////////////

	protected boolean alreadyInitializedPageDesign = false;

	public PageDesign initDeepPageDesign(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedPageDesign) {
			alreadyInitializedPageDesign = true;
			initDeepPageDesign();
		}
		return (PageDesign)this;
	}

	public void initDeepPageDesign() {
		initPageDesign();
		super.initDeepCluster(siteRequest_);
	}

	public void initPageDesign() {
		pageDesignKeyInit();
		childDesignKeysInit();
		parentDesignKeysInit();
		htmlPartKeysInit();
		pageDesignCompleteNameInit();
		designHiddenInit();
		pageContentTypeInit();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepPageDesign(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestPageDesign(SiteRequestEnUS siteRequest_) {
			super.siteRequestCluster(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestPageDesign(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainPageDesign(v);
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
	public Object obtainPageDesign(String var) {
		PageDesign oPageDesign = (PageDesign)this;
		switch(var) {
			case "pageDesignKey":
				return oPageDesign.pageDesignKey;
			case "childDesignKeys":
				return oPageDesign.childDesignKeys;
			case "parentDesignKeys":
				return oPageDesign.parentDesignKeys;
			case "htmlPartKeys":
				return oPageDesign.htmlPartKeys;
			case "pageDesignCompleteName":
				return oPageDesign.pageDesignCompleteName;
			case "designHidden":
				return oPageDesign.designHidden;
			case "pageContentType":
				return oPageDesign.pageContentType;
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
				o = attributePageDesign(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributePageDesign(String var, Object val) {
		PageDesign oPageDesign = (PageDesign)this;
		switch(var) {
			case "childDesignKeys":
				oPageDesign.addChildDesignKeys((Long)val);
				if(!saves.contains("childDesignKeys"))
					saves.add("childDesignKeys");
				return val;
			case "parentDesignKeys":
				oPageDesign.addParentDesignKeys((Long)val);
				if(!saves.contains("parentDesignKeys"))
					saves.add("parentDesignKeys");
				return val;
			case "htmlPartKeys":
				oPageDesign.addHtmlPartKeys((Long)val);
				if(!saves.contains("htmlPartKeys"))
					saves.add("htmlPartKeys");
				return val;
			default:
				return super.attributeCluster(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetPageDesign(entityVar,  siteRequest_, o);
	}
	public static Object staticSetPageDesign(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "pageDesignKey":
			return PageDesign.staticSetPageDesignKey(siteRequest_, o);
		case "childDesignKeys":
			return PageDesign.staticSetChildDesignKeys(siteRequest_, o);
		case "parentDesignKeys":
			return PageDesign.staticSetParentDesignKeys(siteRequest_, o);
		case "htmlPartKeys":
			return PageDesign.staticSetHtmlPartKeys(siteRequest_, o);
		case "pageDesignCompleteName":
			return PageDesign.staticSetPageDesignCompleteName(siteRequest_, o);
		case "designHidden":
			return PageDesign.staticSetDesignHidden(siteRequest_, o);
		case "pageContentType":
			return PageDesign.staticSetPageContentType(siteRequest_, o);
			default:
				return Cluster.staticSetCluster(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrPageDesign(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrPageDesign(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "pageDesignKey":
			return PageDesign.staticSolrPageDesignKey(siteRequest_, (Long)o);
		case "childDesignKeys":
			return PageDesign.staticSolrChildDesignKeys(siteRequest_, (Long)o);
		case "parentDesignKeys":
			return PageDesign.staticSolrParentDesignKeys(siteRequest_, (Long)o);
		case "htmlPartKeys":
			return PageDesign.staticSolrHtmlPartKeys(siteRequest_, (Long)o);
		case "pageDesignCompleteName":
			return PageDesign.staticSolrPageDesignCompleteName(siteRequest_, (String)o);
		case "designHidden":
			return PageDesign.staticSolrDesignHidden(siteRequest_, (Boolean)o);
		case "pageContentType":
			return PageDesign.staticSolrPageContentType(siteRequest_, (String)o);
			default:
				return Cluster.staticSolrCluster(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrPageDesign(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrPageDesign(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "pageDesignKey":
			return PageDesign.staticSolrStrPageDesignKey(siteRequest_, (Long)o);
		case "childDesignKeys":
			return PageDesign.staticSolrStrChildDesignKeys(siteRequest_, (Long)o);
		case "parentDesignKeys":
			return PageDesign.staticSolrStrParentDesignKeys(siteRequest_, (Long)o);
		case "htmlPartKeys":
			return PageDesign.staticSolrStrHtmlPartKeys(siteRequest_, (Long)o);
		case "pageDesignCompleteName":
			return PageDesign.staticSolrStrPageDesignCompleteName(siteRequest_, (String)o);
		case "designHidden":
			return PageDesign.staticSolrStrDesignHidden(siteRequest_, (Boolean)o);
		case "pageContentType":
			return PageDesign.staticSolrStrPageContentType(siteRequest_, (String)o);
			default:
				return Cluster.staticSolrStrCluster(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqPageDesign(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqPageDesign(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "pageDesignKey":
			return PageDesign.staticSolrFqPageDesignKey(siteRequest_, o);
		case "childDesignKeys":
			return PageDesign.staticSolrFqChildDesignKeys(siteRequest_, o);
		case "parentDesignKeys":
			return PageDesign.staticSolrFqParentDesignKeys(siteRequest_, o);
		case "htmlPartKeys":
			return PageDesign.staticSolrFqHtmlPartKeys(siteRequest_, o);
		case "pageDesignCompleteName":
			return PageDesign.staticSolrFqPageDesignCompleteName(siteRequest_, o);
		case "designHidden":
			return PageDesign.staticSolrFqDesignHidden(siteRequest_, o);
		case "pageContentType":
			return PageDesign.staticSolrFqPageContentType(siteRequest_, o);
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
					o = definePageDesign(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object definePageDesign(String var, String val) {
		switch(var.toLowerCase()) {
			case "pagedesigncompletename":
				if(val != null)
					setPageDesignCompleteName(val);
				saves.add("pageDesignCompleteName");
				return val;
			case "designhidden":
				if(val != null)
					setDesignHidden(val);
				saves.add("designHidden");
				return val;
			case "pagecontenttype":
				if(val != null)
					setPageContentType(val);
				saves.add("pageContentType");
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
					o = definePageDesign(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object definePageDesign(String var, Object val) {
		switch(var.toLowerCase()) {
			case "pagedesigncompletename":
				if(val instanceof String)
					setPageDesignCompleteName((String)val);
				saves.add("pageDesignCompleteName");
				return val;
			case "designhidden":
				if(val instanceof Boolean)
					setDesignHidden((Boolean)val);
				saves.add("designHidden");
				return val;
			case "pagecontenttype":
				if(val instanceof String)
					setPageContentType((String)val);
				saves.add("pageContentType");
				return val;
			default:
				return super.defineCluster(var, val);
		}
	}

	/////////////
	// populate //
	/////////////

	@Override public void populateForClass(SolrDocument solrDocument) {
		populatePageDesign(solrDocument);
	}
	public void populatePageDesign(SolrDocument solrDocument) {
		PageDesign oPageDesign = (PageDesign)this;
		saves = (List<String>)solrDocument.get("saves_stored_strings");
		if(saves != null) {

			if(saves.contains("pageDesignKey")) {
				Long pageDesignKey = (Long)solrDocument.get("pageDesignKey_stored_long");
				if(pageDesignKey != null)
					oPageDesign.setPageDesignKey(pageDesignKey);
			}

			List<Long> childDesignKeys = (List<Long>)solrDocument.get("childDesignKeys_stored_longs");
			if(childDesignKeys != null)
				oPageDesign.childDesignKeys.addAll(childDesignKeys);

			List<Long> parentDesignKeys = (List<Long>)solrDocument.get("parentDesignKeys_stored_longs");
			if(parentDesignKeys != null)
				oPageDesign.parentDesignKeys.addAll(parentDesignKeys);

			List<Long> htmlPartKeys = (List<Long>)solrDocument.get("htmlPartKeys_stored_longs");
			if(htmlPartKeys != null)
				oPageDesign.htmlPartKeys.addAll(htmlPartKeys);

			if(saves.contains("pageDesignCompleteName")) {
				String pageDesignCompleteName = (String)solrDocument.get("pageDesignCompleteName_stored_string");
				if(pageDesignCompleteName != null)
					oPageDesign.setPageDesignCompleteName(pageDesignCompleteName);
			}

			if(saves.contains("designHidden")) {
				Boolean designHidden = (Boolean)solrDocument.get("designHidden_stored_boolean");
				if(designHidden != null)
					oPageDesign.setDesignHidden(designHidden);
			}

			if(saves.contains("pageContentType")) {
				String pageContentType = (String)solrDocument.get("pageContentType_stored_string");
				if(pageContentType != null)
					oPageDesign.setPageContentType(pageContentType);
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
			solrQuery.addFilterQuery("id:" + ClientUtils.escapeQueryChars("com.opendatapolicing.enus.design.PageDesign"));
			QueryResponse queryResponse = siteRequest.getSiteContext_().getSolrClient().query(solrQuery);
			if(queryResponse.getResults().size() > 0)
				siteRequest.setSolrDocument(queryResponse.getResults().get(0));
			PageDesign o = new PageDesign();
			o.siteRequestPageDesign(siteRequest);
			o.initDeepPageDesign(siteRequest);
			o.indexPageDesign();
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}


	@Override public void indexForClass() {
		indexPageDesign();
	}

	@Override public void indexForClass(SolrInputDocument document) {
		indexPageDesign(document);
	}

	public void indexPageDesign(SolrClient clientSolr) {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexPageDesign(document);
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexPageDesign() {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexPageDesign(document);
			SolrClient clientSolr = siteRequest_.getSiteContext_().getSolrClient();
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexPageDesign(SolrInputDocument document) {
		if(pageDesignKey != null) {
			document.addField("pageDesignKey_indexed_long", pageDesignKey);
			document.addField("pageDesignKey_stored_long", pageDesignKey);
		}
		if(childDesignKeys != null) {
			for(java.lang.Long o : childDesignKeys) {
				document.addField("childDesignKeys_indexed_longs", o);
			}
			for(java.lang.Long o : childDesignKeys) {
				document.addField("childDesignKeys_stored_longs", o);
			}
		}
		if(parentDesignKeys != null) {
			for(java.lang.Long o : parentDesignKeys) {
				document.addField("parentDesignKeys_indexed_longs", o);
			}
			for(java.lang.Long o : parentDesignKeys) {
				document.addField("parentDesignKeys_stored_longs", o);
			}
		}
		if(htmlPartKeys != null) {
			for(java.lang.Long o : htmlPartKeys) {
				document.addField("htmlPartKeys_indexed_longs", o);
			}
			for(java.lang.Long o : htmlPartKeys) {
				document.addField("htmlPartKeys_stored_longs", o);
			}
		}
		if(pageDesignCompleteName != null) {
			document.addField("pageDesignCompleteName_indexed_string", pageDesignCompleteName);
			document.addField("pageDesignCompleteName_stored_string", pageDesignCompleteName);
		}
		if(designHidden != null) {
			document.addField("designHidden_indexed_boolean", designHidden);
			document.addField("designHidden_stored_boolean", designHidden);
		}
		if(pageContentType != null) {
			document.addField("pageContentType_indexed_string", pageContentType);
			document.addField("pageContentType_stored_string", pageContentType);
		}
		super.indexCluster(document);

	}

	public void unindexPageDesign() {
		try {
		SiteRequestEnUS siteRequest = new SiteRequestEnUS();
			siteRequest.initDeepSiteRequestEnUS();
			SiteContextEnUS siteContext = new SiteContextEnUS();
			siteContext.initDeepSiteContextEnUS();
			siteRequest.setSiteContext_(siteContext);
			siteRequest.setSiteConfig_(siteContext.getSiteConfig());
			initDeepPageDesign(siteRequest);
			SolrClient solrClient = siteContext.getSolrClient();
			solrClient.deleteById(id.toString());
			solrClient.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public static String varIndexedPageDesign(String entityVar) {
		switch(entityVar) {
			case "pageDesignKey":
				return "pageDesignKey_indexed_long";
			case "childDesignKeys":
				return "childDesignKeys_indexed_longs";
			case "parentDesignKeys":
				return "parentDesignKeys_indexed_longs";
			case "htmlPartKeys":
				return "htmlPartKeys_indexed_longs";
			case "pageDesignCompleteName":
				return "pageDesignCompleteName_indexed_string";
			case "designHidden":
				return "designHidden_indexed_boolean";
			case "pageContentType":
				return "pageContentType_indexed_string";
			default:
				return Cluster.varIndexedCluster(entityVar);
		}
	}

	public static String varSearchPageDesign(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSearchCluster(entityVar);
		}
	}

	public static String varSuggestedPageDesign(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSuggestedCluster(entityVar);
		}
	}

	/////////////
	// store //
	/////////////

	@Override public void storeForClass(SolrDocument solrDocument) {
		storePageDesign(solrDocument);
	}
	public void storePageDesign(SolrDocument solrDocument) {
		PageDesign oPageDesign = (PageDesign)this;

		Long pageDesignKey = (Long)solrDocument.get("pageDesignKey_stored_long");
		if(pageDesignKey != null)
			oPageDesign.setPageDesignKey(pageDesignKey);

		List<Long> childDesignKeys = (List<Long>)solrDocument.get("childDesignKeys_stored_longs");
		if(childDesignKeys != null)
			oPageDesign.childDesignKeys.addAll(childDesignKeys);

		List<Long> parentDesignKeys = (List<Long>)solrDocument.get("parentDesignKeys_stored_longs");
		if(parentDesignKeys != null)
			oPageDesign.parentDesignKeys.addAll(parentDesignKeys);

		List<Long> htmlPartKeys = (List<Long>)solrDocument.get("htmlPartKeys_stored_longs");
		if(htmlPartKeys != null)
			oPageDesign.htmlPartKeys.addAll(htmlPartKeys);

		String pageDesignCompleteName = (String)solrDocument.get("pageDesignCompleteName_stored_string");
		if(pageDesignCompleteName != null)
			oPageDesign.setPageDesignCompleteName(pageDesignCompleteName);

		Boolean designHidden = (Boolean)solrDocument.get("designHidden_stored_boolean");
		if(designHidden != null)
			oPageDesign.setDesignHidden(designHidden);

		String pageContentType = (String)solrDocument.get("pageContentType_stored_string");
		if(pageContentType != null)
			oPageDesign.setPageContentType(pageContentType);

		super.storeCluster(solrDocument);
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestPageDesign() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof PageDesign) {
			PageDesign original = (PageDesign)o;
			if(!Objects.equals(pageDesignKey, original.getPageDesignKey()))
				apiRequest.addVars("pageDesignKey");
			if(!Objects.equals(childDesignKeys, original.getChildDesignKeys()))
				apiRequest.addVars("childDesignKeys");
			if(!Objects.equals(parentDesignKeys, original.getParentDesignKeys()))
				apiRequest.addVars("parentDesignKeys");
			if(!Objects.equals(htmlPartKeys, original.getHtmlPartKeys()))
				apiRequest.addVars("htmlPartKeys");
			if(!Objects.equals(pageDesignCompleteName, original.getPageDesignCompleteName()))
				apiRequest.addVars("pageDesignCompleteName");
			if(!Objects.equals(designHidden, original.getDesignHidden()))
				apiRequest.addVars("designHidden");
			if(!Objects.equals(pageContentType, original.getPageContentType()))
				apiRequest.addVars("pageContentType");
			super.apiRequestCluster();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), pageDesignKey, childDesignKeys, parentDesignKeys, htmlPartKeys, pageDesignCompleteName, designHidden, pageContentType);
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof PageDesign))
			return false;
		PageDesign that = (PageDesign)o;
		return super.equals(o)
				&& Objects.equals( pageDesignKey, that.pageDesignKey )
				&& Objects.equals( childDesignKeys, that.childDesignKeys )
				&& Objects.equals( parentDesignKeys, that.parentDesignKeys )
				&& Objects.equals( htmlPartKeys, that.htmlPartKeys )
				&& Objects.equals( pageDesignCompleteName, that.pageDesignCompleteName )
				&& Objects.equals( designHidden, that.designHidden )
				&& Objects.equals( pageContentType, that.pageContentType );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("PageDesign { ");
		sb.append( "pageDesignKey: " ).append(pageDesignKey);
		sb.append( ", childDesignKeys: " ).append(childDesignKeys);
		sb.append( ", parentDesignKeys: " ).append(parentDesignKeys);
		sb.append( ", htmlPartKeys: " ).append(htmlPartKeys);
		sb.append( ", pageDesignCompleteName: \"" ).append(pageDesignCompleteName).append( "\"" );
		sb.append( ", designHidden: " ).append(designHidden);
		sb.append( ", pageContentType: \"" ).append(pageContentType).append( "\"" );
		sb.append(" }");
		return sb.toString();
	}
}
