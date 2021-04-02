package com.opendatapolicing.enus.html.part;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.lang.Double;
import java.util.Date;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
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
import java.math.RoundingMode;
import com.opendatapolicing.enus.wrap.Wrap;
import org.slf4j.Logger;
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
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class HtmlPartGen<DEV> extends Cluster {
	protected static final Logger LOG = LoggerFactory.getLogger(HtmlPart.class);

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("");

	public static final String HtmlPart_AName = "an HTML part";
	public static final String HtmlPart_This = "this ";
	public static final String HtmlPart_ThisName = "this HTML part";
	public static final String HtmlPart_A = "a ";
	public static final String HtmlPart_TheName = "theHTML part";
	public static final String HtmlPart_NameSingular = "HTML part";
	public static final String HtmlPart_NamePlural = "HTML parts";
	public static final String HtmlPart_NameActual = "current HTML part";
	public static final String HtmlPart_AllName = "all the HTML parts";
	public static final String HtmlPart_SearchAllNameBy = "search HTML parts by ";
	public static final String HtmlPart_Title = "HTML parts";
	public static final String HtmlPart_ThePluralName = "the HTML parts";
	public static final String HtmlPart_NoNameFound = "no HTML part found";
	public static final String HtmlPart_NameVar = "html-part";
	public static final String HtmlPart_OfName = "of HTML part";
	public static final String HtmlPart_ANameAdjective = "an HTML part";
	public static final String HtmlPart_NameAdjectiveSingular = "HTML part";
	public static final String HtmlPart_NameAdjectivePlural = "HTML parts";
	public static final String HtmlPart_Color = "khaki";
	public static final String HtmlPart_IconGroup = "regular";
	public static final String HtmlPart_IconName = "puzzle-piece";
	public static final Integer HtmlPart_Rows = 300;

	/////////////////
	// htmlPartKey //
	/////////////////

	/**	 The entity htmlPartKey
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long htmlPartKey;
	@JsonIgnore
	public Wrap<Long> htmlPartKeyWrap = new Wrap<Long>().p(this).c(Long.class).var("htmlPartKey").o(htmlPartKey);

	/**	<br/> The entity htmlPartKey
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlPartKey">Find the entity htmlPartKey in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _htmlPartKey(Wrap<Long> c);

	public Long getHtmlPartKey() {
		return htmlPartKey;
	}

	public void setHtmlPartKey(Long htmlPartKey) {
		this.htmlPartKey = htmlPartKey;
		this.htmlPartKeyWrap.alreadyInitialized = true;
	}
	public void setHtmlPartKey(String o) {
		this.htmlPartKey = HtmlPart.staticSetHtmlPartKey(siteRequest_, o);
		this.htmlPartKeyWrap.alreadyInitialized = true;
	}
	public static Long staticSetHtmlPartKey(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected HtmlPart htmlPartKeyInit() {
		if(!htmlPartKeyWrap.alreadyInitialized) {
			_htmlPartKey(htmlPartKeyWrap);
			if(htmlPartKey == null)
				setHtmlPartKey(htmlPartKeyWrap.o);
		}
		htmlPartKeyWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static Long staticSolrHtmlPartKey(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrHtmlPartKey(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqHtmlPartKey(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrHtmlPartKey(siteRequest_, HtmlPart.staticSolrHtmlPartKey(siteRequest_, HtmlPart.staticSetHtmlPartKey(siteRequest_, o)));
	}

	public Long solrHtmlPartKey() {
		return HtmlPart.staticSolrHtmlPartKey(siteRequest_, htmlPartKey);
	}

	public String strHtmlPartKey() {
		return htmlPartKey == null ? "" : htmlPartKey.toString();
	}

	public Long sqlHtmlPartKey() {
		return htmlPartKey;
	}

	public String jsonHtmlPartKey() {
		return htmlPartKey == null ? "" : htmlPartKey.toString();
	}

	public String htmTooltipHtmlPartKey() {
		return null;
	}

	public String htmHtmlPartKey() {
		return htmlPartKey == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlPartKey());
	}

	////////////////////
	// pageDesignKeys //
	////////////////////

	/**	 The entity pageDesignKeys
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> pageDesignKeys = new ArrayList<Long>();
	@JsonIgnore
	public Wrap<List<Long>> pageDesignKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("pageDesignKeys").o(pageDesignKeys);

	/**	<br/> The entity pageDesignKeys
	 *  It is constructed before being initialized with the constructor by default List<Long>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageDesignKeys">Find the entity pageDesignKeys in Solr</a>
	 * <br/>
	 * @param pageDesignKeys is the entity already constructed. 
	 **/
	protected abstract void _pageDesignKeys(List<Long> l);

	public List<Long> getPageDesignKeys() {
		return pageDesignKeys;
	}

	public void setPageDesignKeys(List<Long> pageDesignKeys) {
		this.pageDesignKeys = pageDesignKeys;
		this.pageDesignKeysWrap.alreadyInitialized = true;
	}
	public void setPageDesignKeys(String o) {
		Long l = HtmlPart.staticSetPageDesignKeys(siteRequest_, o);
		if(l != null)
			addPageDesignKeys(l);
		this.pageDesignKeysWrap.alreadyInitialized = true;
	}
	public static Long staticSetPageDesignKeys(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	public HtmlPart addPageDesignKeys(Long...objets) {
		for(Long o : objets) {
			addPageDesignKeys(o);
		}
		return (HtmlPart)this;
	}
	public HtmlPart addPageDesignKeys(Long o) {
		if(o != null && !pageDesignKeys.contains(o))
			this.pageDesignKeys.add(o);
		return (HtmlPart)this;
	}
	public void setPageDesignKeys(JsonArray objets) {
		pageDesignKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addPageDesignKeys(o);
		}
	}
	public HtmlPart addPageDesignKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addPageDesignKeys(p);
		}
		return (HtmlPart)this;
	}
	protected HtmlPart pageDesignKeysInit() {
		if(!pageDesignKeysWrap.alreadyInitialized) {
			_pageDesignKeys(pageDesignKeys);
		}
		pageDesignKeysWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static Long staticSolrPageDesignKeys(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrPageDesignKeys(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageDesignKeys(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrPageDesignKeys(siteRequest_, HtmlPart.staticSolrPageDesignKeys(siteRequest_, HtmlPart.staticSetPageDesignKeys(siteRequest_, o)));
	}

	public List<Long> solrPageDesignKeys() {
		List<Long> l = new ArrayList<Long>();
		for(Long o : pageDesignKeys) {
			l.add(HtmlPart.staticSolrPageDesignKeys(siteRequest_, o));
		}
		return l;
	}

	public String strPageDesignKeys() {
		return pageDesignKeys == null ? "" : pageDesignKeys.toString();
	}

	public List<Long> sqlPageDesignKeys() {
		return pageDesignKeys;
	}

	public String jsonPageDesignKeys() {
		return pageDesignKeys == null ? "" : pageDesignKeys.toString();
	}

	public String htmTooltipPageDesignKeys() {
		return null;
	}

	public String htmPageDesignKeys() {
		return pageDesignKeys == null ? "" : StringEscapeUtils.escapeHtml4(strPageDesignKeys());
	}

	public void inputPageDesignKeys(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("i").a("class", "far fa-search w3-xxlarge w3-cell w3-cell-middle ").f().g("i");
			if("PUTCopy".equals(classApiMethodMethod)) {
				{ e("div").f();
					e("input")
						.a("type", "checkbox")
						.a("id", classApiMethodMethod, "_pageDesignKeys_clear")
						.a("class", "pageDesignKeys_clear ")
						.fg();
					e("label").a("for", "classApiMethodMethod, \"_pageDesignKeys_clear").f().sx("clear").g("label");
				} g("div");
			}
			e("input")
				.a("type", "text")
				.a("placeholder", "page designs")
				.a("class", "valueObjectSuggest suggestPageDesignKeys w3-input w3-border w3-cell w3-cell-middle ")
				.a("name", "setPageDesignKeys")
				.a("id", classApiMethodMethod, "_pageDesignKeys")
				.a("autocomplete", "off");
				a("oninput", "suggestHtmlPartPageDesignKeys($(this).val() ? [ { 'name': 'q', 'value': 'objectSuggest:' + $(this).val() }, { 'name': 'rows', 'value': '10' }, { 'name': 'fl', 'value': 'pk,pageUrlPk,pageDesignCompleteName' } ] : [", pk == null ? "" : "{'name':'fq','value':'htmlPartKeys:" + pk + "'}", "], $('#listHtmlPartPageDesignKeys_", classApiMethodMethod, "'), ", pk, "); ");

				fg();

		} else {
		}
	}

	public void htmPageDesignKeys(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartPageDesignKeys").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("a").a("href", "/page-design?fq=htmlPartKeys:", pk).a("class", "w3-cell w3-btn w3-center h4 w3-block h4 w3-khaki w3-hover-khaki ").f();
								e("i").a("class", "far fa-drafting-compass ").f().g("i");
								sx("page designs");
							} g("a");
						} g("div");
						{ e("div").a("class", "w3-cell-row ").f();
							{ e("h5").a("class", "w3-cell ").f();
								sx("relate  to this HTML part");
							} g("h5");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();
								{ e("div").a("class", "w3-cell-row ").f();

								inputPageDesignKeys(classApiMethodMethod);
								} g("div");
							} g("div");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
								{ e("ul").a("class", "w3-ul w3-hoverable ").a("id", "listHtmlPartPageDesignKeys_", classApiMethodMethod).f();
								} g("ul");
								if(
										CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), PageDesign.ROLES)
										|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), PageDesign.ROLES)
										) {
									if("Page".equals(classApiMethodMethod)) {
										{ e("div").a("class", "w3-cell-row ").f();
											e("button")
												.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-khaki ")
												.a("id", classApiMethodMethod, "_pageDesignKeys_add")
												.a("onclick", "$(this).addClass('w3-disabled'); this.disabled = true; this.innerHTML = 'Sending…'; postPageDesignVals({ htmlPartKeys: [ \"", pk, "\" ] }, function() {}, function() { addError($('#", classApiMethodMethod, "pageDesignKeys')); });")
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

	//////////////
	// htmlLink //
	//////////////

	/**	 The entity htmlLink
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlLink;
	@JsonIgnore
	public Wrap<String> htmlLinkWrap = new Wrap<String>().p(this).c(String.class).var("htmlLink").o(htmlLink);

	/**	<br/> The entity htmlLink
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlLink">Find the entity htmlLink in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _htmlLink(Wrap<String> c);

	public String getHtmlLink() {
		return htmlLink;
	}
	public void setHtmlLink(String o) {
		this.htmlLink = HtmlPart.staticSetHtmlLink(siteRequest_, o);
		this.htmlLinkWrap.alreadyInitialized = true;
	}
	public static String staticSetHtmlLink(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected HtmlPart htmlLinkInit() {
		if(!htmlLinkWrap.alreadyInitialized) {
			_htmlLink(htmlLinkWrap);
			if(htmlLink == null)
				setHtmlLink(htmlLinkWrap.o);
		}
		htmlLinkWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static String staticSolrHtmlLink(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrHtmlLink(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqHtmlLink(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrHtmlLink(siteRequest_, HtmlPart.staticSolrHtmlLink(siteRequest_, HtmlPart.staticSetHtmlLink(siteRequest_, o)));
	}

	public String solrHtmlLink() {
		return HtmlPart.staticSolrHtmlLink(siteRequest_, htmlLink);
	}

	public String strHtmlLink() {
		return htmlLink == null ? "" : htmlLink;
	}

	public String sqlHtmlLink() {
		return htmlLink;
	}

	public String jsonHtmlLink() {
		return htmlLink == null ? "" : htmlLink;
	}

	public String htmTooltipHtmlLink() {
		return null;
	}

	public String htmHtmlLink() {
		return htmlLink == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlLink());
	}

	public void inputHtmlLink(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "link")
				.a("id", classApiMethodMethod, "_htmlLink");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setHtmlLink classHtmlPart inputHtmlPart", pk, "HtmlLink w3-input w3-border ");
					a("name", "setHtmlLink");
				} else {
					a("class", "valueHtmlLink w3-input w3-border classHtmlPart inputHtmlPart", pk, "HtmlLink w3-input w3-border ");
					a("name", "htmlLink");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setHtmlLink', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_htmlLink')); }, function() { addError($('#", classApiMethodMethod, "_htmlLink')); }); ");
				}
				a("value", strHtmlLink())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "HtmlLink ").f().sx(htmHtmlLink()).g("span");
			}
		}
	}

	public void htmHtmlLink(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartHtmlLink").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_htmlLink").a("class", "").f().sx("link").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputHtmlLink(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_htmlLink')); $('#", classApiMethodMethod, "_htmlLink').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setHtmlLink', null, function() { addGlow($('#", classApiMethodMethod, "_htmlLink')); }, function() { addError($('#", classApiMethodMethod, "_htmlLink')); }); ")
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

	/////////////////
	// htmlElement //
	/////////////////

	/**	 The entity htmlElement
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlElement;
	@JsonIgnore
	public Wrap<String> htmlElementWrap = new Wrap<String>().p(this).c(String.class).var("htmlElement").o(htmlElement);

	/**	<br/> The entity htmlElement
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlElement">Find the entity htmlElement in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _htmlElement(Wrap<String> c);

	public String getHtmlElement() {
		return htmlElement;
	}
	public void setHtmlElement(String o) {
		this.htmlElement = HtmlPart.staticSetHtmlElement(siteRequest_, o);
		this.htmlElementWrap.alreadyInitialized = true;
	}
	public static String staticSetHtmlElement(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected HtmlPart htmlElementInit() {
		if(!htmlElementWrap.alreadyInitialized) {
			_htmlElement(htmlElementWrap);
			if(htmlElement == null)
				setHtmlElement(htmlElementWrap.o);
		}
		htmlElementWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static String staticSolrHtmlElement(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrHtmlElement(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqHtmlElement(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrHtmlElement(siteRequest_, HtmlPart.staticSolrHtmlElement(siteRequest_, HtmlPart.staticSetHtmlElement(siteRequest_, o)));
	}

	public String solrHtmlElement() {
		return HtmlPart.staticSolrHtmlElement(siteRequest_, htmlElement);
	}

	public String strHtmlElement() {
		return htmlElement == null ? "" : htmlElement;
	}

	public String sqlHtmlElement() {
		return htmlElement;
	}

	public String jsonHtmlElement() {
		return htmlElement == null ? "" : htmlElement;
	}

	public String htmTooltipHtmlElement() {
		return null;
	}

	public String htmHtmlElement() {
		return htmlElement == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlElement());
	}

	public void inputHtmlElement(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "HTML element")
				.a("id", classApiMethodMethod, "_htmlElement");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setHtmlElement classHtmlPart inputHtmlPart", pk, "HtmlElement w3-input w3-border ");
					a("name", "setHtmlElement");
				} else {
					a("class", "valueHtmlElement w3-input w3-border classHtmlPart inputHtmlPart", pk, "HtmlElement w3-input w3-border ");
					a("name", "htmlElement");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setHtmlElement', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_htmlElement')); }, function() { addError($('#", classApiMethodMethod, "_htmlElement')); }); ");
				}
				a("value", strHtmlElement())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "HtmlElement ").f().sx(htmHtmlElement()).g("span");
			}
		}
	}

	public void htmHtmlElement(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartHtmlElement").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_htmlElement").a("class", "").f().sx("HTML element").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputHtmlElement(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_htmlElement')); $('#", classApiMethodMethod, "_htmlElement').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setHtmlElement', null, function() { addGlow($('#", classApiMethodMethod, "_htmlElement')); }, function() { addError($('#", classApiMethodMethod, "_htmlElement')); }); ")
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

	////////////
	// htmlId //
	////////////

	/**	 The entity htmlId
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlId;
	@JsonIgnore
	public Wrap<String> htmlIdWrap = new Wrap<String>().p(this).c(String.class).var("htmlId").o(htmlId);

	/**	<br/> The entity htmlId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlId">Find the entity htmlId in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _htmlId(Wrap<String> c);

	public String getHtmlId() {
		return htmlId;
	}
	public void setHtmlId(String o) {
		this.htmlId = HtmlPart.staticSetHtmlId(siteRequest_, o);
		this.htmlIdWrap.alreadyInitialized = true;
	}
	public static String staticSetHtmlId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected HtmlPart htmlIdInit() {
		if(!htmlIdWrap.alreadyInitialized) {
			_htmlId(htmlIdWrap);
			if(htmlId == null)
				setHtmlId(htmlIdWrap.o);
		}
		htmlIdWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static String staticSolrHtmlId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrHtmlId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqHtmlId(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrHtmlId(siteRequest_, HtmlPart.staticSolrHtmlId(siteRequest_, HtmlPart.staticSetHtmlId(siteRequest_, o)));
	}

	public String solrHtmlId() {
		return HtmlPart.staticSolrHtmlId(siteRequest_, htmlId);
	}

	public String strHtmlId() {
		return htmlId == null ? "" : htmlId;
	}

	public String sqlHtmlId() {
		return htmlId;
	}

	public String jsonHtmlId() {
		return htmlId == null ? "" : htmlId;
	}

	public String htmTooltipHtmlId() {
		return null;
	}

	public String htmHtmlId() {
		return htmlId == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlId());
	}

	public void inputHtmlId(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "HTML ID")
				.a("id", classApiMethodMethod, "_htmlId");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setHtmlId classHtmlPart inputHtmlPart", pk, "HtmlId w3-input w3-border ");
					a("name", "setHtmlId");
				} else {
					a("class", "valueHtmlId w3-input w3-border classHtmlPart inputHtmlPart", pk, "HtmlId w3-input w3-border ");
					a("name", "htmlId");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setHtmlId', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_htmlId')); }, function() { addError($('#", classApiMethodMethod, "_htmlId')); }); ");
				}
				a("value", strHtmlId())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "HtmlId ").f().sx(htmHtmlId()).g("span");
			}
		}
	}

	public void htmHtmlId(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartHtmlId").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_htmlId").a("class", "").f().sx("HTML ID").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputHtmlId(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_htmlId')); $('#", classApiMethodMethod, "_htmlId').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setHtmlId', null, function() { addGlow($('#", classApiMethodMethod, "_htmlId')); }, function() { addError($('#", classApiMethodMethod, "_htmlId')); }); ")
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

	/////////////////
	// htmlClasses //
	/////////////////

	/**	 The entity htmlClasses
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlClasses;
	@JsonIgnore
	public Wrap<String> htmlClassesWrap = new Wrap<String>().p(this).c(String.class).var("htmlClasses").o(htmlClasses);

	/**	<br/> The entity htmlClasses
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlClasses">Find the entity htmlClasses in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _htmlClasses(Wrap<String> c);

	public String getHtmlClasses() {
		return htmlClasses;
	}
	public void setHtmlClasses(String o) {
		this.htmlClasses = HtmlPart.staticSetHtmlClasses(siteRequest_, o);
		this.htmlClassesWrap.alreadyInitialized = true;
	}
	public static String staticSetHtmlClasses(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected HtmlPart htmlClassesInit() {
		if(!htmlClassesWrap.alreadyInitialized) {
			_htmlClasses(htmlClassesWrap);
			if(htmlClasses == null)
				setHtmlClasses(htmlClassesWrap.o);
		}
		htmlClassesWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static String staticSolrHtmlClasses(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrHtmlClasses(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqHtmlClasses(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrHtmlClasses(siteRequest_, HtmlPart.staticSolrHtmlClasses(siteRequest_, HtmlPart.staticSetHtmlClasses(siteRequest_, o)));
	}

	public String solrHtmlClasses() {
		return HtmlPart.staticSolrHtmlClasses(siteRequest_, htmlClasses);
	}

	public String strHtmlClasses() {
		return htmlClasses == null ? "" : htmlClasses;
	}

	public String sqlHtmlClasses() {
		return htmlClasses;
	}

	public String jsonHtmlClasses() {
		return htmlClasses == null ? "" : htmlClasses;
	}

	public String htmTooltipHtmlClasses() {
		return null;
	}

	public String htmHtmlClasses() {
		return htmlClasses == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlClasses());
	}

	public void inputHtmlClasses(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "HTML classes")
				.a("id", classApiMethodMethod, "_htmlClasses");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setHtmlClasses classHtmlPart inputHtmlPart", pk, "HtmlClasses w3-input w3-border ");
					a("name", "setHtmlClasses");
				} else {
					a("class", "valueHtmlClasses w3-input w3-border classHtmlPart inputHtmlPart", pk, "HtmlClasses w3-input w3-border ");
					a("name", "htmlClasses");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setHtmlClasses', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_htmlClasses')); }, function() { addError($('#", classApiMethodMethod, "_htmlClasses')); }); ");
				}
				a("value", strHtmlClasses())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "HtmlClasses ").f().sx(htmHtmlClasses()).g("span");
			}
		}
	}

	public void htmHtmlClasses(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartHtmlClasses").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_htmlClasses").a("class", "").f().sx("HTML classes").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputHtmlClasses(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_htmlClasses')); $('#", classApiMethodMethod, "_htmlClasses').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setHtmlClasses', null, function() { addGlow($('#", classApiMethodMethod, "_htmlClasses')); }, function() { addError($('#", classApiMethodMethod, "_htmlClasses')); }); ")
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
	// htmlStyle //
	///////////////

	/**	 The entity htmlStyle
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlStyle;
	@JsonIgnore
	public Wrap<String> htmlStyleWrap = new Wrap<String>().p(this).c(String.class).var("htmlStyle").o(htmlStyle);

	/**	<br/> The entity htmlStyle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlStyle">Find the entity htmlStyle in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _htmlStyle(Wrap<String> c);

	public String getHtmlStyle() {
		return htmlStyle;
	}
	public void setHtmlStyle(String o) {
		this.htmlStyle = HtmlPart.staticSetHtmlStyle(siteRequest_, o);
		this.htmlStyleWrap.alreadyInitialized = true;
	}
	public static String staticSetHtmlStyle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected HtmlPart htmlStyleInit() {
		if(!htmlStyleWrap.alreadyInitialized) {
			_htmlStyle(htmlStyleWrap);
			if(htmlStyle == null)
				setHtmlStyle(htmlStyleWrap.o);
		}
		htmlStyleWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static String staticSolrHtmlStyle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrHtmlStyle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqHtmlStyle(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrHtmlStyle(siteRequest_, HtmlPart.staticSolrHtmlStyle(siteRequest_, HtmlPart.staticSetHtmlStyle(siteRequest_, o)));
	}

	public String solrHtmlStyle() {
		return HtmlPart.staticSolrHtmlStyle(siteRequest_, htmlStyle);
	}

	public String strHtmlStyle() {
		return htmlStyle == null ? "" : htmlStyle;
	}

	public String sqlHtmlStyle() {
		return htmlStyle;
	}

	public String jsonHtmlStyle() {
		return htmlStyle == null ? "" : htmlStyle;
	}

	public String htmTooltipHtmlStyle() {
		return null;
	}

	public String htmHtmlStyle() {
		return htmlStyle == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlStyle());
	}

	public void inputHtmlStyle(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "HTML style")
				.a("id", classApiMethodMethod, "_htmlStyle");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setHtmlStyle classHtmlPart inputHtmlPart", pk, "HtmlStyle w3-input w3-border ");
					a("name", "setHtmlStyle");
				} else {
					a("class", "valueHtmlStyle w3-input w3-border classHtmlPart inputHtmlPart", pk, "HtmlStyle w3-input w3-border ");
					a("name", "htmlStyle");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setHtmlStyle', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_htmlStyle')); }, function() { addError($('#", classApiMethodMethod, "_htmlStyle')); }); ");
				}
				a("value", strHtmlStyle())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "HtmlStyle ").f().sx(htmHtmlStyle()).g("span");
			}
		}
	}

	public void htmHtmlStyle(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartHtmlStyle").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_htmlStyle").a("class", "").f().sx("HTML style").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputHtmlStyle(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_htmlStyle')); $('#", classApiMethodMethod, "_htmlStyle').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setHtmlStyle', null, function() { addGlow($('#", classApiMethodMethod, "_htmlStyle')); }, function() { addError($('#", classApiMethodMethod, "_htmlStyle')); }); ")
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
	// htmlBefore //
	////////////////

	/**	 The entity htmlBefore
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlBefore;
	@JsonIgnore
	public Wrap<String> htmlBeforeWrap = new Wrap<String>().p(this).c(String.class).var("htmlBefore").o(htmlBefore);

	/**	<br/> The entity htmlBefore
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlBefore">Find the entity htmlBefore in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _htmlBefore(Wrap<String> c);

	public String getHtmlBefore() {
		return htmlBefore;
	}
	public void setHtmlBefore(String o) {
		this.htmlBefore = HtmlPart.staticSetHtmlBefore(siteRequest_, o);
		this.htmlBeforeWrap.alreadyInitialized = true;
	}
	public static String staticSetHtmlBefore(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected HtmlPart htmlBeforeInit() {
		if(!htmlBeforeWrap.alreadyInitialized) {
			_htmlBefore(htmlBeforeWrap);
			if(htmlBefore == null)
				setHtmlBefore(htmlBeforeWrap.o);
		}
		htmlBeforeWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static String staticSolrHtmlBefore(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrHtmlBefore(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqHtmlBefore(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrHtmlBefore(siteRequest_, HtmlPart.staticSolrHtmlBefore(siteRequest_, HtmlPart.staticSetHtmlBefore(siteRequest_, o)));
	}

	public String solrHtmlBefore() {
		return HtmlPart.staticSolrHtmlBefore(siteRequest_, htmlBefore);
	}

	public String strHtmlBefore() {
		return htmlBefore == null ? "" : htmlBefore;
	}

	public String sqlHtmlBefore() {
		return htmlBefore;
	}

	public String jsonHtmlBefore() {
		return htmlBefore == null ? "" : htmlBefore;
	}

	public String htmTooltipHtmlBefore() {
		return null;
	}

	public String htmHtmlBefore() {
		return htmlBefore == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlBefore());
	}

	public void inputHtmlBefore(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("textarea")
				.a("placeholder", "HTML before")
				.a("id", classApiMethodMethod, "_htmlBefore");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setHtmlBefore classHtmlPart inputHtmlPart", pk, "HtmlBefore w3-input w3-border ");
					a("name", "setHtmlBefore");
				} else {
					a("class", "valueHtmlBefore w3-input w3-border classHtmlPart inputHtmlPart", pk, "HtmlBefore w3-input w3-border ");
					a("name", "htmlBefore");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setHtmlBefore', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_htmlBefore')); }, function() { addError($('#", classApiMethodMethod, "_htmlBefore')); }); ");
				}
			f().sx(strHtmlBefore()).g("textarea");

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "HtmlBefore ").f().sx(htmHtmlBefore()).g("span");
			}
		}
	}

	public void htmHtmlBefore(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartHtmlBefore").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_htmlBefore").a("class", "").f().sx("HTML before").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputHtmlBefore(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_htmlBefore')); $('#", classApiMethodMethod, "_htmlBefore').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setHtmlBefore', null, function() { addGlow($('#", classApiMethodMethod, "_htmlBefore')); }, function() { addError($('#", classApiMethodMethod, "_htmlBefore')); }); ")
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
	// htmlAfter //
	///////////////

	/**	 The entity htmlAfter
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlAfter;
	@JsonIgnore
	public Wrap<String> htmlAfterWrap = new Wrap<String>().p(this).c(String.class).var("htmlAfter").o(htmlAfter);

	/**	<br/> The entity htmlAfter
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlAfter">Find the entity htmlAfter in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _htmlAfter(Wrap<String> c);

	public String getHtmlAfter() {
		return htmlAfter;
	}
	public void setHtmlAfter(String o) {
		this.htmlAfter = HtmlPart.staticSetHtmlAfter(siteRequest_, o);
		this.htmlAfterWrap.alreadyInitialized = true;
	}
	public static String staticSetHtmlAfter(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected HtmlPart htmlAfterInit() {
		if(!htmlAfterWrap.alreadyInitialized) {
			_htmlAfter(htmlAfterWrap);
			if(htmlAfter == null)
				setHtmlAfter(htmlAfterWrap.o);
		}
		htmlAfterWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static String staticSolrHtmlAfter(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrHtmlAfter(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqHtmlAfter(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrHtmlAfter(siteRequest_, HtmlPart.staticSolrHtmlAfter(siteRequest_, HtmlPart.staticSetHtmlAfter(siteRequest_, o)));
	}

	public String solrHtmlAfter() {
		return HtmlPart.staticSolrHtmlAfter(siteRequest_, htmlAfter);
	}

	public String strHtmlAfter() {
		return htmlAfter == null ? "" : htmlAfter;
	}

	public String sqlHtmlAfter() {
		return htmlAfter;
	}

	public String jsonHtmlAfter() {
		return htmlAfter == null ? "" : htmlAfter;
	}

	public String htmTooltipHtmlAfter() {
		return null;
	}

	public String htmHtmlAfter() {
		return htmlAfter == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlAfter());
	}

	public void inputHtmlAfter(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("textarea")
				.a("placeholder", "HTML after")
				.a("id", classApiMethodMethod, "_htmlAfter");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setHtmlAfter classHtmlPart inputHtmlPart", pk, "HtmlAfter w3-input w3-border ");
					a("name", "setHtmlAfter");
				} else {
					a("class", "valueHtmlAfter w3-input w3-border classHtmlPart inputHtmlPart", pk, "HtmlAfter w3-input w3-border ");
					a("name", "htmlAfter");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setHtmlAfter', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_htmlAfter')); }, function() { addError($('#", classApiMethodMethod, "_htmlAfter')); }); ");
				}
			f().sx(strHtmlAfter()).g("textarea");

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "HtmlAfter ").f().sx(htmHtmlAfter()).g("span");
			}
		}
	}

	public void htmHtmlAfter(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartHtmlAfter").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_htmlAfter").a("class", "").f().sx("HTML after").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputHtmlAfter(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_htmlAfter')); $('#", classApiMethodMethod, "_htmlAfter').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setHtmlAfter', null, function() { addGlow($('#", classApiMethodMethod, "_htmlAfter')); }, function() { addError($('#", classApiMethodMethod, "_htmlAfter')); }); ")
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
	// htmlText //
	//////////////

	/**	 The entity htmlText
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlText;
	@JsonIgnore
	public Wrap<String> htmlTextWrap = new Wrap<String>().p(this).c(String.class).var("htmlText").o(htmlText);

	/**	<br/> The entity htmlText
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlText">Find the entity htmlText in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _htmlText(Wrap<String> c);

	public String getHtmlText() {
		return htmlText;
	}
	public void setHtmlText(String o) {
		this.htmlText = HtmlPart.staticSetHtmlText(siteRequest_, o);
		this.htmlTextWrap.alreadyInitialized = true;
	}
	public static String staticSetHtmlText(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected HtmlPart htmlTextInit() {
		if(!htmlTextWrap.alreadyInitialized) {
			_htmlText(htmlTextWrap);
			if(htmlText == null)
				setHtmlText(htmlTextWrap.o);
		}
		htmlTextWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static String staticSolrHtmlText(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrHtmlText(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqHtmlText(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrHtmlText(siteRequest_, HtmlPart.staticSolrHtmlText(siteRequest_, HtmlPart.staticSetHtmlText(siteRequest_, o)));
	}

	public String solrHtmlText() {
		return HtmlPart.staticSolrHtmlText(siteRequest_, htmlText);
	}

	public String strHtmlText() {
		return htmlText == null ? "" : htmlText;
	}

	public String sqlHtmlText() {
		return htmlText;
	}

	public String jsonHtmlText() {
		return htmlText == null ? "" : htmlText;
	}

	public String htmTooltipHtmlText() {
		return null;
	}

	public String htmHtmlText() {
		return htmlText == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlText());
	}

	public void inputHtmlText(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("textarea")
				.a("placeholder", "text")
				.a("id", classApiMethodMethod, "_htmlText");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setHtmlText classHtmlPart inputHtmlPart", pk, "HtmlText w3-input w3-border ");
					a("name", "setHtmlText");
				} else {
					a("class", "valueHtmlText w3-input w3-border classHtmlPart inputHtmlPart", pk, "HtmlText w3-input w3-border ");
					a("name", "htmlText");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setHtmlText', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_htmlText')); }, function() { addError($('#", classApiMethodMethod, "_htmlText')); }); ");
				}
			f().sx(strHtmlText()).g("textarea");

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "HtmlText ").f().sx(htmHtmlText()).g("span");
			}
		}
	}

	public void htmHtmlText(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartHtmlText").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_htmlText").a("class", "").f().sx("text").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputHtmlText(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_htmlText')); $('#", classApiMethodMethod, "_htmlText').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setHtmlText', null, function() { addGlow($('#", classApiMethodMethod, "_htmlText')); }, function() { addError($('#", classApiMethodMethod, "_htmlText')); }); ")
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

	/////////////
	// htmlVar //
	/////////////

	/**	 The entity htmlVar
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlVar;
	@JsonIgnore
	public Wrap<String> htmlVarWrap = new Wrap<String>().p(this).c(String.class).var("htmlVar").o(htmlVar);

	/**	<br/> The entity htmlVar
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlVar">Find the entity htmlVar in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _htmlVar(Wrap<String> c);

	public String getHtmlVar() {
		return htmlVar;
	}
	public void setHtmlVar(String o) {
		this.htmlVar = HtmlPart.staticSetHtmlVar(siteRequest_, o);
		this.htmlVarWrap.alreadyInitialized = true;
	}
	public static String staticSetHtmlVar(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected HtmlPart htmlVarInit() {
		if(!htmlVarWrap.alreadyInitialized) {
			_htmlVar(htmlVarWrap);
			if(htmlVar == null)
				setHtmlVar(htmlVarWrap.o);
		}
		htmlVarWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static String staticSolrHtmlVar(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrHtmlVar(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqHtmlVar(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrHtmlVar(siteRequest_, HtmlPart.staticSolrHtmlVar(siteRequest_, HtmlPart.staticSetHtmlVar(siteRequest_, o)));
	}

	public String solrHtmlVar() {
		return HtmlPart.staticSolrHtmlVar(siteRequest_, htmlVar);
	}

	public String strHtmlVar() {
		return htmlVar == null ? "" : htmlVar;
	}

	public String sqlHtmlVar() {
		return htmlVar;
	}

	public String jsonHtmlVar() {
		return htmlVar == null ? "" : htmlVar;
	}

	public String htmTooltipHtmlVar() {
		return null;
	}

	public String htmHtmlVar() {
		return htmlVar == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlVar());
	}

	public void inputHtmlVar(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "var")
				.a("id", classApiMethodMethod, "_htmlVar");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setHtmlVar classHtmlPart inputHtmlPart", pk, "HtmlVar w3-input w3-border ");
					a("name", "setHtmlVar");
				} else {
					a("class", "valueHtmlVar w3-input w3-border classHtmlPart inputHtmlPart", pk, "HtmlVar w3-input w3-border ");
					a("name", "htmlVar");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setHtmlVar', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_htmlVar')); }, function() { addError($('#", classApiMethodMethod, "_htmlVar')); }); ");
				}
				a("value", strHtmlVar())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "HtmlVar ").f().sx(htmHtmlVar()).g("span");
			}
		}
	}

	public void htmHtmlVar(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartHtmlVar").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_htmlVar").a("class", "").f().sx("var").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputHtmlVar(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_htmlVar')); $('#", classApiMethodMethod, "_htmlVar').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setHtmlVar', null, function() { addGlow($('#", classApiMethodMethod, "_htmlVar')); }, function() { addError($('#", classApiMethodMethod, "_htmlVar')); }); ")
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

	/////////////////
	// htmlVarSpan //
	/////////////////

	/**	 The entity htmlVarSpan
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlVarSpan;
	@JsonIgnore
	public Wrap<String> htmlVarSpanWrap = new Wrap<String>().p(this).c(String.class).var("htmlVarSpan").o(htmlVarSpan);

	/**	<br/> The entity htmlVarSpan
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlVarSpan">Find the entity htmlVarSpan in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _htmlVarSpan(Wrap<String> c);

	public String getHtmlVarSpan() {
		return htmlVarSpan;
	}
	public void setHtmlVarSpan(String o) {
		this.htmlVarSpan = HtmlPart.staticSetHtmlVarSpan(siteRequest_, o);
		this.htmlVarSpanWrap.alreadyInitialized = true;
	}
	public static String staticSetHtmlVarSpan(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected HtmlPart htmlVarSpanInit() {
		if(!htmlVarSpanWrap.alreadyInitialized) {
			_htmlVarSpan(htmlVarSpanWrap);
			if(htmlVarSpan == null)
				setHtmlVarSpan(htmlVarSpanWrap.o);
		}
		htmlVarSpanWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static String staticSolrHtmlVarSpan(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrHtmlVarSpan(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqHtmlVarSpan(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrHtmlVarSpan(siteRequest_, HtmlPart.staticSolrHtmlVarSpan(siteRequest_, HtmlPart.staticSetHtmlVarSpan(siteRequest_, o)));
	}

	public String solrHtmlVarSpan() {
		return HtmlPart.staticSolrHtmlVarSpan(siteRequest_, htmlVarSpan);
	}

	public String strHtmlVarSpan() {
		return htmlVarSpan == null ? "" : htmlVarSpan;
	}

	public String sqlHtmlVarSpan() {
		return htmlVarSpan;
	}

	public String jsonHtmlVarSpan() {
		return htmlVarSpan == null ? "" : htmlVarSpan;
	}

	public String htmTooltipHtmlVarSpan() {
		return null;
	}

	public String htmHtmlVarSpan() {
		return htmlVarSpan == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlVarSpan());
	}

	public void inputHtmlVarSpan(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "var span")
				.a("id", classApiMethodMethod, "_htmlVarSpan");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setHtmlVarSpan classHtmlPart inputHtmlPart", pk, "HtmlVarSpan w3-input w3-border ");
					a("name", "setHtmlVarSpan");
				} else {
					a("class", "valueHtmlVarSpan w3-input w3-border classHtmlPart inputHtmlPart", pk, "HtmlVarSpan w3-input w3-border ");
					a("name", "htmlVarSpan");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setHtmlVarSpan', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_htmlVarSpan')); }, function() { addError($('#", classApiMethodMethod, "_htmlVarSpan')); }); ");
				}
				a("value", strHtmlVarSpan())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "HtmlVarSpan ").f().sx(htmHtmlVarSpan()).g("span");
			}
		}
	}

	public void htmHtmlVarSpan(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartHtmlVarSpan").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_htmlVarSpan").a("class", "").f().sx("var span").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputHtmlVarSpan(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_htmlVarSpan')); $('#", classApiMethodMethod, "_htmlVarSpan').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setHtmlVarSpan', null, function() { addGlow($('#", classApiMethodMethod, "_htmlVarSpan')); }, function() { addError($('#", classApiMethodMethod, "_htmlVarSpan')); }); ")
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

	/////////////////
	// htmlVarForm //
	/////////////////

	/**	 The entity htmlVarForm
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlVarForm;
	@JsonIgnore
	public Wrap<String> htmlVarFormWrap = new Wrap<String>().p(this).c(String.class).var("htmlVarForm").o(htmlVarForm);

	/**	<br/> The entity htmlVarForm
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlVarForm">Find the entity htmlVarForm in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _htmlVarForm(Wrap<String> c);

	public String getHtmlVarForm() {
		return htmlVarForm;
	}
	public void setHtmlVarForm(String o) {
		this.htmlVarForm = HtmlPart.staticSetHtmlVarForm(siteRequest_, o);
		this.htmlVarFormWrap.alreadyInitialized = true;
	}
	public static String staticSetHtmlVarForm(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected HtmlPart htmlVarFormInit() {
		if(!htmlVarFormWrap.alreadyInitialized) {
			_htmlVarForm(htmlVarFormWrap);
			if(htmlVarForm == null)
				setHtmlVarForm(htmlVarFormWrap.o);
		}
		htmlVarFormWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static String staticSolrHtmlVarForm(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrHtmlVarForm(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqHtmlVarForm(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrHtmlVarForm(siteRequest_, HtmlPart.staticSolrHtmlVarForm(siteRequest_, HtmlPart.staticSetHtmlVarForm(siteRequest_, o)));
	}

	public String solrHtmlVarForm() {
		return HtmlPart.staticSolrHtmlVarForm(siteRequest_, htmlVarForm);
	}

	public String strHtmlVarForm() {
		return htmlVarForm == null ? "" : htmlVarForm;
	}

	public String sqlHtmlVarForm() {
		return htmlVarForm;
	}

	public String jsonHtmlVarForm() {
		return htmlVarForm == null ? "" : htmlVarForm;
	}

	public String htmTooltipHtmlVarForm() {
		return null;
	}

	public String htmHtmlVarForm() {
		return htmlVarForm == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlVarForm());
	}

	public void inputHtmlVarForm(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "var form")
				.a("id", classApiMethodMethod, "_htmlVarForm");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setHtmlVarForm classHtmlPart inputHtmlPart", pk, "HtmlVarForm w3-input w3-border ");
					a("name", "setHtmlVarForm");
				} else {
					a("class", "valueHtmlVarForm w3-input w3-border classHtmlPart inputHtmlPart", pk, "HtmlVarForm w3-input w3-border ");
					a("name", "htmlVarForm");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setHtmlVarForm', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_htmlVarForm')); }, function() { addError($('#", classApiMethodMethod, "_htmlVarForm')); }); ");
				}
				a("value", strHtmlVarForm())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "HtmlVarForm ").f().sx(htmHtmlVarForm()).g("span");
			}
		}
	}

	public void htmHtmlVarForm(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartHtmlVarForm").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_htmlVarForm").a("class", "").f().sx("var form").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputHtmlVarForm(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_htmlVarForm')); $('#", classApiMethodMethod, "_htmlVarForm').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setHtmlVarForm', null, function() { addGlow($('#", classApiMethodMethod, "_htmlVarForm')); }, function() { addError($('#", classApiMethodMethod, "_htmlVarForm')); }); ")
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
	// htmlVarInput //
	//////////////////

	/**	 The entity htmlVarInput
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlVarInput;
	@JsonIgnore
	public Wrap<String> htmlVarInputWrap = new Wrap<String>().p(this).c(String.class).var("htmlVarInput").o(htmlVarInput);

	/**	<br/> The entity htmlVarInput
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlVarInput">Find the entity htmlVarInput in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _htmlVarInput(Wrap<String> c);

	public String getHtmlVarInput() {
		return htmlVarInput;
	}
	public void setHtmlVarInput(String o) {
		this.htmlVarInput = HtmlPart.staticSetHtmlVarInput(siteRequest_, o);
		this.htmlVarInputWrap.alreadyInitialized = true;
	}
	public static String staticSetHtmlVarInput(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected HtmlPart htmlVarInputInit() {
		if(!htmlVarInputWrap.alreadyInitialized) {
			_htmlVarInput(htmlVarInputWrap);
			if(htmlVarInput == null)
				setHtmlVarInput(htmlVarInputWrap.o);
		}
		htmlVarInputWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static String staticSolrHtmlVarInput(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrHtmlVarInput(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqHtmlVarInput(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrHtmlVarInput(siteRequest_, HtmlPart.staticSolrHtmlVarInput(siteRequest_, HtmlPart.staticSetHtmlVarInput(siteRequest_, o)));
	}

	public String solrHtmlVarInput() {
		return HtmlPart.staticSolrHtmlVarInput(siteRequest_, htmlVarInput);
	}

	public String strHtmlVarInput() {
		return htmlVarInput == null ? "" : htmlVarInput;
	}

	public String sqlHtmlVarInput() {
		return htmlVarInput;
	}

	public String jsonHtmlVarInput() {
		return htmlVarInput == null ? "" : htmlVarInput;
	}

	public String htmTooltipHtmlVarInput() {
		return null;
	}

	public String htmHtmlVarInput() {
		return htmlVarInput == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlVarInput());
	}

	public void inputHtmlVarInput(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "var input")
				.a("id", classApiMethodMethod, "_htmlVarInput");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setHtmlVarInput classHtmlPart inputHtmlPart", pk, "HtmlVarInput w3-input w3-border ");
					a("name", "setHtmlVarInput");
				} else {
					a("class", "valueHtmlVarInput w3-input w3-border classHtmlPart inputHtmlPart", pk, "HtmlVarInput w3-input w3-border ");
					a("name", "htmlVarInput");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setHtmlVarInput', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_htmlVarInput')); }, function() { addError($('#", classApiMethodMethod, "_htmlVarInput')); }); ");
				}
				a("value", strHtmlVarInput())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "HtmlVarInput ").f().sx(htmHtmlVarInput()).g("span");
			}
		}
	}

	public void htmHtmlVarInput(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartHtmlVarInput").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_htmlVarInput").a("class", "").f().sx("var input").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputHtmlVarInput(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_htmlVarInput')); $('#", classApiMethodMethod, "_htmlVarInput').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setHtmlVarInput', null, function() { addGlow($('#", classApiMethodMethod, "_htmlVarInput')); }, function() { addError($('#", classApiMethodMethod, "_htmlVarInput')); }); ")
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
	// htmlVarForEach //
	////////////////////

	/**	 The entity htmlVarForEach
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlVarForEach;
	@JsonIgnore
	public Wrap<String> htmlVarForEachWrap = new Wrap<String>().p(this).c(String.class).var("htmlVarForEach").o(htmlVarForEach);

	/**	<br/> The entity htmlVarForEach
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlVarForEach">Find the entity htmlVarForEach in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _htmlVarForEach(Wrap<String> c);

	public String getHtmlVarForEach() {
		return htmlVarForEach;
	}
	public void setHtmlVarForEach(String o) {
		this.htmlVarForEach = HtmlPart.staticSetHtmlVarForEach(siteRequest_, o);
		this.htmlVarForEachWrap.alreadyInitialized = true;
	}
	public static String staticSetHtmlVarForEach(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected HtmlPart htmlVarForEachInit() {
		if(!htmlVarForEachWrap.alreadyInitialized) {
			_htmlVarForEach(htmlVarForEachWrap);
			if(htmlVarForEach == null)
				setHtmlVarForEach(htmlVarForEachWrap.o);
		}
		htmlVarForEachWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static String staticSolrHtmlVarForEach(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrHtmlVarForEach(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqHtmlVarForEach(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrHtmlVarForEach(siteRequest_, HtmlPart.staticSolrHtmlVarForEach(siteRequest_, HtmlPart.staticSetHtmlVarForEach(siteRequest_, o)));
	}

	public String solrHtmlVarForEach() {
		return HtmlPart.staticSolrHtmlVarForEach(siteRequest_, htmlVarForEach);
	}

	public String strHtmlVarForEach() {
		return htmlVarForEach == null ? "" : htmlVarForEach;
	}

	public String sqlHtmlVarForEach() {
		return htmlVarForEach;
	}

	public String jsonHtmlVarForEach() {
		return htmlVarForEach == null ? "" : htmlVarForEach;
	}

	public String htmTooltipHtmlVarForEach() {
		return null;
	}

	public String htmHtmlVarForEach() {
		return htmlVarForEach == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlVarForEach());
	}

	public void inputHtmlVarForEach(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "var for each")
				.a("id", classApiMethodMethod, "_htmlVarForEach");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setHtmlVarForEach classHtmlPart inputHtmlPart", pk, "HtmlVarForEach w3-input w3-border ");
					a("name", "setHtmlVarForEach");
				} else {
					a("class", "valueHtmlVarForEach w3-input w3-border classHtmlPart inputHtmlPart", pk, "HtmlVarForEach w3-input w3-border ");
					a("name", "htmlVarForEach");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setHtmlVarForEach', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_htmlVarForEach')); }, function() { addError($('#", classApiMethodMethod, "_htmlVarForEach')); }); ");
				}
				a("value", strHtmlVarForEach())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "HtmlVarForEach ").f().sx(htmHtmlVarForEach()).g("span");
			}
		}
	}

	public void htmHtmlVarForEach(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartHtmlVarForEach").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_htmlVarForEach").a("class", "").f().sx("var for each").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputHtmlVarForEach(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_htmlVarForEach')); $('#", classApiMethodMethod, "_htmlVarForEach').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setHtmlVarForEach', null, function() { addGlow($('#", classApiMethodMethod, "_htmlVarForEach')); }, function() { addError($('#", classApiMethodMethod, "_htmlVarForEach')); }); ")
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

	/////////////////
	// htmlVarHtml //
	/////////////////

	/**	 The entity htmlVarHtml
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlVarHtml;
	@JsonIgnore
	public Wrap<String> htmlVarHtmlWrap = new Wrap<String>().p(this).c(String.class).var("htmlVarHtml").o(htmlVarHtml);

	/**	<br/> The entity htmlVarHtml
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlVarHtml">Find the entity htmlVarHtml in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _htmlVarHtml(Wrap<String> c);

	public String getHtmlVarHtml() {
		return htmlVarHtml;
	}
	public void setHtmlVarHtml(String o) {
		this.htmlVarHtml = HtmlPart.staticSetHtmlVarHtml(siteRequest_, o);
		this.htmlVarHtmlWrap.alreadyInitialized = true;
	}
	public static String staticSetHtmlVarHtml(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected HtmlPart htmlVarHtmlInit() {
		if(!htmlVarHtmlWrap.alreadyInitialized) {
			_htmlVarHtml(htmlVarHtmlWrap);
			if(htmlVarHtml == null)
				setHtmlVarHtml(htmlVarHtmlWrap.o);
		}
		htmlVarHtmlWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static String staticSolrHtmlVarHtml(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrHtmlVarHtml(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqHtmlVarHtml(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrHtmlVarHtml(siteRequest_, HtmlPart.staticSolrHtmlVarHtml(siteRequest_, HtmlPart.staticSetHtmlVarHtml(siteRequest_, o)));
	}

	public String solrHtmlVarHtml() {
		return HtmlPart.staticSolrHtmlVarHtml(siteRequest_, htmlVarHtml);
	}

	public String strHtmlVarHtml() {
		return htmlVarHtml == null ? "" : htmlVarHtml;
	}

	public String sqlHtmlVarHtml() {
		return htmlVarHtml;
	}

	public String jsonHtmlVarHtml() {
		return htmlVarHtml == null ? "" : htmlVarHtml;
	}

	public String htmTooltipHtmlVarHtml() {
		return null;
	}

	public String htmHtmlVarHtml() {
		return htmlVarHtml == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlVarHtml());
	}

	public void inputHtmlVarHtml(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "var html")
				.a("id", classApiMethodMethod, "_htmlVarHtml");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setHtmlVarHtml classHtmlPart inputHtmlPart", pk, "HtmlVarHtml w3-input w3-border ");
					a("name", "setHtmlVarHtml");
				} else {
					a("class", "valueHtmlVarHtml w3-input w3-border classHtmlPart inputHtmlPart", pk, "HtmlVarHtml w3-input w3-border ");
					a("name", "htmlVarHtml");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setHtmlVarHtml', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_htmlVarHtml')); }, function() { addError($('#", classApiMethodMethod, "_htmlVarHtml')); }); ");
				}
				a("value", strHtmlVarHtml())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "HtmlVarHtml ").f().sx(htmHtmlVarHtml()).g("span");
			}
		}
	}

	public void htmHtmlVarHtml(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartHtmlVarHtml").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_htmlVarHtml").a("class", "").f().sx("var html").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputHtmlVarHtml(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_htmlVarHtml')); $('#", classApiMethodMethod, "_htmlVarHtml').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setHtmlVarHtml', null, function() { addGlow($('#", classApiMethodMethod, "_htmlVarHtml')); }, function() { addError($('#", classApiMethodMethod, "_htmlVarHtml')); }); ")
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

	/////////////////////////
	// htmlVarBase64Decode //
	/////////////////////////

	/**	 The entity htmlVarBase64Decode
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String htmlVarBase64Decode;
	@JsonIgnore
	public Wrap<String> htmlVarBase64DecodeWrap = new Wrap<String>().p(this).c(String.class).var("htmlVarBase64Decode").o(htmlVarBase64Decode);

	/**	<br/> The entity htmlVarBase64Decode
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlVarBase64Decode">Find the entity htmlVarBase64Decode in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _htmlVarBase64Decode(Wrap<String> c);

	public String getHtmlVarBase64Decode() {
		return htmlVarBase64Decode;
	}
	public void setHtmlVarBase64Decode(String o) {
		this.htmlVarBase64Decode = HtmlPart.staticSetHtmlVarBase64Decode(siteRequest_, o);
		this.htmlVarBase64DecodeWrap.alreadyInitialized = true;
	}
	public static String staticSetHtmlVarBase64Decode(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected HtmlPart htmlVarBase64DecodeInit() {
		if(!htmlVarBase64DecodeWrap.alreadyInitialized) {
			_htmlVarBase64Decode(htmlVarBase64DecodeWrap);
			if(htmlVarBase64Decode == null)
				setHtmlVarBase64Decode(htmlVarBase64DecodeWrap.o);
		}
		htmlVarBase64DecodeWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static String staticSolrHtmlVarBase64Decode(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrHtmlVarBase64Decode(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqHtmlVarBase64Decode(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrHtmlVarBase64Decode(siteRequest_, HtmlPart.staticSolrHtmlVarBase64Decode(siteRequest_, HtmlPart.staticSetHtmlVarBase64Decode(siteRequest_, o)));
	}

	public String solrHtmlVarBase64Decode() {
		return HtmlPart.staticSolrHtmlVarBase64Decode(siteRequest_, htmlVarBase64Decode);
	}

	public String strHtmlVarBase64Decode() {
		return htmlVarBase64Decode == null ? "" : htmlVarBase64Decode;
	}

	public String sqlHtmlVarBase64Decode() {
		return htmlVarBase64Decode;
	}

	public String jsonHtmlVarBase64Decode() {
		return htmlVarBase64Decode == null ? "" : htmlVarBase64Decode;
	}

	public String htmTooltipHtmlVarBase64Decode() {
		return null;
	}

	public String htmHtmlVarBase64Decode() {
		return htmlVarBase64Decode == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlVarBase64Decode());
	}

	public void inputHtmlVarBase64Decode(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "var base64 decode")
				.a("id", classApiMethodMethod, "_htmlVarBase64Decode");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setHtmlVarBase64Decode classHtmlPart inputHtmlPart", pk, "HtmlVarBase64Decode w3-input w3-border ");
					a("name", "setHtmlVarBase64Decode");
				} else {
					a("class", "valueHtmlVarBase64Decode w3-input w3-border classHtmlPart inputHtmlPart", pk, "HtmlVarBase64Decode w3-input w3-border ");
					a("name", "htmlVarBase64Decode");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setHtmlVarBase64Decode', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_htmlVarBase64Decode')); }, function() { addError($('#", classApiMethodMethod, "_htmlVarBase64Decode')); }); ");
				}
				a("value", strHtmlVarBase64Decode())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "HtmlVarBase64Decode ").f().sx(htmHtmlVarBase64Decode()).g("span");
			}
		}
	}

	public void htmHtmlVarBase64Decode(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartHtmlVarBase64Decode").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_htmlVarBase64Decode").a("class", "").f().sx("var base64 decode").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputHtmlVarBase64Decode(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_htmlVarBase64Decode')); $('#", classApiMethodMethod, "_htmlVarBase64Decode').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setHtmlVarBase64Decode', null, function() { addGlow($('#", classApiMethodMethod, "_htmlVarBase64Decode')); }, function() { addError($('#", classApiMethodMethod, "_htmlVarBase64Decode')); }); ")
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

	/////////////////
	// htmlExclude //
	/////////////////

	/**	 The entity htmlExclude
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean htmlExclude;
	@JsonIgnore
	public Wrap<Boolean> htmlExcludeWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("htmlExclude").o(htmlExclude);

	/**	<br/> The entity htmlExclude
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:htmlExclude">Find the entity htmlExclude in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _htmlExclude(Wrap<Boolean> c);

	public Boolean getHtmlExclude() {
		return htmlExclude;
	}

	public void setHtmlExclude(Boolean htmlExclude) {
		this.htmlExclude = htmlExclude;
		this.htmlExcludeWrap.alreadyInitialized = true;
	}
	public void setHtmlExclude(String o) {
		this.htmlExclude = HtmlPart.staticSetHtmlExclude(siteRequest_, o);
		this.htmlExcludeWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetHtmlExclude(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected HtmlPart htmlExcludeInit() {
		if(!htmlExcludeWrap.alreadyInitialized) {
			_htmlExclude(htmlExcludeWrap);
			if(htmlExclude == null)
				setHtmlExclude(htmlExcludeWrap.o);
		}
		htmlExcludeWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static Boolean staticSolrHtmlExclude(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrHtmlExclude(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqHtmlExclude(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrHtmlExclude(siteRequest_, HtmlPart.staticSolrHtmlExclude(siteRequest_, HtmlPart.staticSetHtmlExclude(siteRequest_, o)));
	}

	public Boolean solrHtmlExclude() {
		return HtmlPart.staticSolrHtmlExclude(siteRequest_, htmlExclude);
	}

	public String strHtmlExclude() {
		return htmlExclude == null ? "" : htmlExclude.toString();
	}

	public Boolean sqlHtmlExclude() {
		return htmlExclude;
	}

	public String jsonHtmlExclude() {
		return htmlExclude == null ? "" : htmlExclude.toString();
	}

	public String htmTooltipHtmlExclude() {
		return null;
	}

	public String htmHtmlExclude() {
		return htmlExclude == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlExclude());
	}

	public void inputHtmlExclude(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			if("Page".equals(classApiMethodMethod)) {
				e("input")
					.a("type", "checkbox")
					.a("id", classApiMethodMethod, "_htmlExclude")
					.a("value", "true");
			} else {
				e("select")
					.a("id", classApiMethodMethod, "_htmlExclude");
			}
			if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
				a("class", "setHtmlExclude classHtmlPart inputHtmlPart", pk, "HtmlExclude w3-input w3-border ");
				a("name", "setHtmlExclude");
			} else {
				a("class", "valueHtmlExclude classHtmlPart inputHtmlPart", pk, "HtmlExclude w3-input w3-border ");
				a("name", "htmlExclude");
			}
			if("Page".equals(classApiMethodMethod)) {
				a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setHtmlExclude', $(this).prop('checked'), function() { addGlow($('#", classApiMethodMethod, "_htmlExclude')); }, function() { addError($('#", classApiMethodMethod, "_htmlExclude')); }); ");
			}
			if("Page".equals(classApiMethodMethod)) {
				if(getHtmlExclude() != null && getHtmlExclude())
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
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "HtmlExclude ").f().sx(htmHtmlExclude()).g("span");
			}
		}
	}

	public void htmHtmlExclude(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartHtmlExclude").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_htmlExclude").a("class", "").f().sx("HTML exclude").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputHtmlExclude(classApiMethodMethod);
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	////////////////
	// pdfExclude //
	////////////////

	/**	 The entity pdfExclude
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean pdfExclude;
	@JsonIgnore
	public Wrap<Boolean> pdfExcludeWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("pdfExclude").o(pdfExclude);

	/**	<br/> The entity pdfExclude
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pdfExclude">Find the entity pdfExclude in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pdfExclude(Wrap<Boolean> c);

	public Boolean getPdfExclude() {
		return pdfExclude;
	}

	public void setPdfExclude(Boolean pdfExclude) {
		this.pdfExclude = pdfExclude;
		this.pdfExcludeWrap.alreadyInitialized = true;
	}
	public void setPdfExclude(String o) {
		this.pdfExclude = HtmlPart.staticSetPdfExclude(siteRequest_, o);
		this.pdfExcludeWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetPdfExclude(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected HtmlPart pdfExcludeInit() {
		if(!pdfExcludeWrap.alreadyInitialized) {
			_pdfExclude(pdfExcludeWrap);
			if(pdfExclude == null)
				setPdfExclude(pdfExcludeWrap.o);
		}
		pdfExcludeWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static Boolean staticSolrPdfExclude(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrPdfExclude(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPdfExclude(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrPdfExclude(siteRequest_, HtmlPart.staticSolrPdfExclude(siteRequest_, HtmlPart.staticSetPdfExclude(siteRequest_, o)));
	}

	public Boolean solrPdfExclude() {
		return HtmlPart.staticSolrPdfExclude(siteRequest_, pdfExclude);
	}

	public String strPdfExclude() {
		return pdfExclude == null ? "" : pdfExclude.toString();
	}

	public Boolean sqlPdfExclude() {
		return pdfExclude;
	}

	public String jsonPdfExclude() {
		return pdfExclude == null ? "" : pdfExclude.toString();
	}

	public String htmTooltipPdfExclude() {
		return null;
	}

	public String htmPdfExclude() {
		return pdfExclude == null ? "" : StringEscapeUtils.escapeHtml4(strPdfExclude());
	}

	public void inputPdfExclude(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			if("Page".equals(classApiMethodMethod)) {
				e("input")
					.a("type", "checkbox")
					.a("id", classApiMethodMethod, "_pdfExclude")
					.a("value", "true");
			} else {
				e("select")
					.a("id", classApiMethodMethod, "_pdfExclude");
			}
			if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
				a("class", "setPdfExclude classHtmlPart inputHtmlPart", pk, "PdfExclude w3-input w3-border ");
				a("name", "setPdfExclude");
			} else {
				a("class", "valuePdfExclude classHtmlPart inputHtmlPart", pk, "PdfExclude w3-input w3-border ");
				a("name", "pdfExclude");
			}
			if("Page".equals(classApiMethodMethod)) {
				a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setPdfExclude', $(this).prop('checked'), function() { addGlow($('#", classApiMethodMethod, "_pdfExclude')); }, function() { addError($('#", classApiMethodMethod, "_pdfExclude')); }); ");
			}
			if("Page".equals(classApiMethodMethod)) {
				if(getPdfExclude() != null && getPdfExclude())
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
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "PdfExclude ").f().sx(htmPdfExclude()).g("span");
			}
		}
	}

	public void htmPdfExclude(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartPdfExclude").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_pdfExclude").a("class", "").f().sx("PDF exclude").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputPdfExclude(classApiMethodMethod);
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	/////////////////
	// loginLogout //
	/////////////////

	/**	 The entity loginLogout
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean loginLogout;
	@JsonIgnore
	public Wrap<Boolean> loginLogoutWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("loginLogout").o(loginLogout);

	/**	<br/> The entity loginLogout
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:loginLogout">Find the entity loginLogout in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _loginLogout(Wrap<Boolean> c);

	public Boolean getLoginLogout() {
		return loginLogout;
	}

	public void setLoginLogout(Boolean loginLogout) {
		this.loginLogout = loginLogout;
		this.loginLogoutWrap.alreadyInitialized = true;
	}
	public void setLoginLogout(String o) {
		this.loginLogout = HtmlPart.staticSetLoginLogout(siteRequest_, o);
		this.loginLogoutWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetLoginLogout(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected HtmlPart loginLogoutInit() {
		if(!loginLogoutWrap.alreadyInitialized) {
			_loginLogout(loginLogoutWrap);
			if(loginLogout == null)
				setLoginLogout(loginLogoutWrap.o);
		}
		loginLogoutWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static Boolean staticSolrLoginLogout(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrLoginLogout(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqLoginLogout(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrLoginLogout(siteRequest_, HtmlPart.staticSolrLoginLogout(siteRequest_, HtmlPart.staticSetLoginLogout(siteRequest_, o)));
	}

	public Boolean solrLoginLogout() {
		return HtmlPart.staticSolrLoginLogout(siteRequest_, loginLogout);
	}

	public String strLoginLogout() {
		return loginLogout == null ? "" : loginLogout.toString();
	}

	public Boolean sqlLoginLogout() {
		return loginLogout;
	}

	public String jsonLoginLogout() {
		return loginLogout == null ? "" : loginLogout.toString();
	}

	public String htmTooltipLoginLogout() {
		return null;
	}

	public String htmLoginLogout() {
		return loginLogout == null ? "" : StringEscapeUtils.escapeHtml4(strLoginLogout());
	}

	public void inputLoginLogout(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			if("Page".equals(classApiMethodMethod)) {
				e("input")
					.a("type", "checkbox")
					.a("id", classApiMethodMethod, "_loginLogout")
					.a("value", "true");
			} else {
				e("select")
					.a("id", classApiMethodMethod, "_loginLogout");
			}
			if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
				a("class", "setLoginLogout classHtmlPart inputHtmlPart", pk, "LoginLogout w3-input w3-border ");
				a("name", "setLoginLogout");
			} else {
				a("class", "valueLoginLogout classHtmlPart inputHtmlPart", pk, "LoginLogout w3-input w3-border ");
				a("name", "loginLogout");
			}
			if("Page".equals(classApiMethodMethod)) {
				a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setLoginLogout', $(this).prop('checked'), function() { addGlow($('#", classApiMethodMethod, "_loginLogout')); }, function() { addError($('#", classApiMethodMethod, "_loginLogout')); }); ");
			}
			if("Page".equals(classApiMethodMethod)) {
				if(getLoginLogout() != null && getLoginLogout())
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
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "LoginLogout ").f().sx(htmLoginLogout()).g("span");
			}
		}
	}

	public void htmLoginLogout(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartLoginLogout").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_loginLogout").a("class", "").f().sx("login/logout").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputLoginLogout(classApiMethodMethod);
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	///////////////
	// searchUri //
	///////////////

	/**	 The entity searchUri
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String searchUri;
	@JsonIgnore
	public Wrap<String> searchUriWrap = new Wrap<String>().p(this).c(String.class).var("searchUri").o(searchUri);

	/**	<br/> The entity searchUri
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:searchUri">Find the entity searchUri in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _searchUri(Wrap<String> c);

	public String getSearchUri() {
		return searchUri;
	}
	public void setSearchUri(String o) {
		this.searchUri = HtmlPart.staticSetSearchUri(siteRequest_, o);
		this.searchUriWrap.alreadyInitialized = true;
	}
	public static String staticSetSearchUri(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected HtmlPart searchUriInit() {
		if(!searchUriWrap.alreadyInitialized) {
			_searchUri(searchUriWrap);
			if(searchUri == null)
				setSearchUri(searchUriWrap.o);
		}
		searchUriWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static String staticSolrSearchUri(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrSearchUri(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSearchUri(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrSearchUri(siteRequest_, HtmlPart.staticSolrSearchUri(siteRequest_, HtmlPart.staticSetSearchUri(siteRequest_, o)));
	}

	public String solrSearchUri() {
		return HtmlPart.staticSolrSearchUri(siteRequest_, searchUri);
	}

	public String strSearchUri() {
		return searchUri == null ? "" : searchUri;
	}

	public String sqlSearchUri() {
		return searchUri;
	}

	public String jsonSearchUri() {
		return searchUri == null ? "" : searchUri;
	}

	public String htmTooltipSearchUri() {
		return null;
	}

	public String htmSearchUri() {
		return searchUri == null ? "" : StringEscapeUtils.escapeHtml4(strSearchUri());
	}

	public void inputSearchUri(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "search uri")
				.a("id", classApiMethodMethod, "_searchUri");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSearchUri classHtmlPart inputHtmlPart", pk, "SearchUri w3-input w3-border ");
					a("name", "setSearchUri");
				} else {
					a("class", "valueSearchUri w3-input w3-border classHtmlPart inputHtmlPart", pk, "SearchUri w3-input w3-border ");
					a("name", "searchUri");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSearchUri', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_searchUri')); }, function() { addError($('#", classApiMethodMethod, "_searchUri')); }); ");
				}
				a("value", strSearchUri())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "SearchUri ").f().sx(htmSearchUri()).g("span");
			}
		}
	}

	public void htmSearchUri(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartSearchUri").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_searchUri").a("class", "").f().sx("search uri").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSearchUri(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_searchUri')); $('#", classApiMethodMethod, "_searchUri').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setSearchUri', null, function() { addGlow($('#", classApiMethodMethod, "_searchUri')); }, function() { addError($('#", classApiMethodMethod, "_searchUri')); }); ")
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

	///////////
	// mapTo //
	///////////

	/**	 The entity mapTo
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String mapTo;
	@JsonIgnore
	public Wrap<String> mapToWrap = new Wrap<String>().p(this).c(String.class).var("mapTo").o(mapTo);

	/**	<br/> The entity mapTo
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:mapTo">Find the entity mapTo in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _mapTo(Wrap<String> c);

	public String getMapTo() {
		return mapTo;
	}
	public void setMapTo(String o) {
		this.mapTo = HtmlPart.staticSetMapTo(siteRequest_, o);
		this.mapToWrap.alreadyInitialized = true;
	}
	public static String staticSetMapTo(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected HtmlPart mapToInit() {
		if(!mapToWrap.alreadyInitialized) {
			_mapTo(mapToWrap);
			if(mapTo == null)
				setMapTo(mapToWrap.o);
		}
		mapToWrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static String staticSolrMapTo(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrMapTo(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqMapTo(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrMapTo(siteRequest_, HtmlPart.staticSolrMapTo(siteRequest_, HtmlPart.staticSetMapTo(siteRequest_, o)));
	}

	public String solrMapTo() {
		return HtmlPart.staticSolrMapTo(siteRequest_, mapTo);
	}

	public String strMapTo() {
		return mapTo == null ? "" : mapTo;
	}

	public String sqlMapTo() {
		return mapTo;
	}

	public String jsonMapTo() {
		return mapTo == null ? "" : mapTo;
	}

	public String htmTooltipMapTo() {
		return null;
	}

	public String htmMapTo() {
		return mapTo == null ? "" : StringEscapeUtils.escapeHtml4(strMapTo());
	}

	public void inputMapTo(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "map to")
				.a("id", classApiMethodMethod, "_mapTo");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setMapTo classHtmlPart inputHtmlPart", pk, "MapTo w3-input w3-border ");
					a("name", "setMapTo");
				} else {
					a("class", "valueMapTo w3-input w3-border classHtmlPart inputHtmlPart", pk, "MapTo w3-input w3-border ");
					a("name", "mapTo");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setMapTo', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_mapTo')); }, function() { addError($('#", classApiMethodMethod, "_mapTo')); }); ");
				}
				a("value", strMapTo())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "MapTo ").f().sx(htmMapTo()).g("span");
			}
		}
	}

	public void htmMapTo(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartMapTo").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_mapTo").a("class", "").f().sx("map to").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputMapTo(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_mapTo')); $('#", classApiMethodMethod, "_mapTo').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setMapTo', null, function() { addGlow($('#", classApiMethodMethod, "_mapTo')); }, function() { addError($('#", classApiMethodMethod, "_mapTo')); }); ")
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

	///////////
	// sort1 //
	///////////

	/**	 The entity sort1
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Double sort1;
	@JsonIgnore
	public Wrap<Double> sort1Wrap = new Wrap<Double>().p(this).c(Double.class).var("sort1").o(sort1);

	/**	<br/> The entity sort1
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sort1">Find the entity sort1 in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _sort1(Wrap<Double> c);

	public Double getSort1() {
		return sort1;
	}

	public void setSort1(Double sort1) {
		this.sort1 = sort1;
		this.sort1Wrap.alreadyInitialized = true;
	}
	public void setSort1(String o) {
		this.sort1 = HtmlPart.staticSetSort1(siteRequest_, o);
		this.sort1Wrap.alreadyInitialized = true;
	}
	public static Double staticSetSort1(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Double.parseDouble(o);
		return null;
	}
	protected HtmlPart sort1Init() {
		if(!sort1Wrap.alreadyInitialized) {
			_sort1(sort1Wrap);
			if(sort1 == null)
				setSort1(sort1Wrap.o);
		}
		sort1Wrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static Double staticSolrSort1(SiteRequestEnUS siteRequest_, Double o) {
		return o;
	}

	public static String staticSolrStrSort1(SiteRequestEnUS siteRequest_, Double o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSort1(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrSort1(siteRequest_, HtmlPart.staticSolrSort1(siteRequest_, HtmlPart.staticSetSort1(siteRequest_, o)));
	}

	public Double solrSort1() {
		return HtmlPart.staticSolrSort1(siteRequest_, sort1);
	}

	public String strSort1() {
		return sort1 == null ? "" : sort1.toString();
	}

	public Double sqlSort1() {
		return sort1;
	}

	public String jsonSort1() {
		return sort1 == null ? "" : sort1.toString();
	}

	public String htmTooltipSort1() {
		return null;
	}

	public String htmSort1() {
		return sort1 == null ? "" : StringEscapeUtils.escapeHtml4(strSort1());
	}

	public void inputSort1(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "sort1")
				.a("id", classApiMethodMethod, "_sort1");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSort1 classHtmlPart inputHtmlPart", pk, "Sort1 w3-input w3-border ");
					a("name", "setSort1");
				} else {
					a("class", "valueSort1 w3-input w3-border classHtmlPart inputHtmlPart", pk, "Sort1 w3-input w3-border ");
					a("name", "sort1");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSort1', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_sort1')); }, function() { addError($('#", classApiMethodMethod, "_sort1')); }); ");
				}
				a("value", strSort1())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "Sort1 ").f().sx(htmSort1()).g("span");
			}
		}
	}

	public void htmSort1(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartSort1").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_sort1").a("class", "").f().sx("sort1").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSort1(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_sort1')); $('#", classApiMethodMethod, "_sort1').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setSort1', null, function() { addGlow($('#", classApiMethodMethod, "_sort1')); }, function() { addError($('#", classApiMethodMethod, "_sort1')); }); ")
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

	///////////
	// sort2 //
	///////////

	/**	 The entity sort2
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Double sort2;
	@JsonIgnore
	public Wrap<Double> sort2Wrap = new Wrap<Double>().p(this).c(Double.class).var("sort2").o(sort2);

	/**	<br/> The entity sort2
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sort2">Find the entity sort2 in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _sort2(Wrap<Double> c);

	public Double getSort2() {
		return sort2;
	}

	public void setSort2(Double sort2) {
		this.sort2 = sort2;
		this.sort2Wrap.alreadyInitialized = true;
	}
	public void setSort2(String o) {
		this.sort2 = HtmlPart.staticSetSort2(siteRequest_, o);
		this.sort2Wrap.alreadyInitialized = true;
	}
	public static Double staticSetSort2(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Double.parseDouble(o);
		return null;
	}
	protected HtmlPart sort2Init() {
		if(!sort2Wrap.alreadyInitialized) {
			_sort2(sort2Wrap);
			if(sort2 == null)
				setSort2(sort2Wrap.o);
		}
		sort2Wrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static Double staticSolrSort2(SiteRequestEnUS siteRequest_, Double o) {
		return o;
	}

	public static String staticSolrStrSort2(SiteRequestEnUS siteRequest_, Double o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSort2(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrSort2(siteRequest_, HtmlPart.staticSolrSort2(siteRequest_, HtmlPart.staticSetSort2(siteRequest_, o)));
	}

	public Double solrSort2() {
		return HtmlPart.staticSolrSort2(siteRequest_, sort2);
	}

	public String strSort2() {
		return sort2 == null ? "" : sort2.toString();
	}

	public Double sqlSort2() {
		return sort2;
	}

	public String jsonSort2() {
		return sort2 == null ? "" : sort2.toString();
	}

	public String htmTooltipSort2() {
		return null;
	}

	public String htmSort2() {
		return sort2 == null ? "" : StringEscapeUtils.escapeHtml4(strSort2());
	}

	public void inputSort2(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "sort2")
				.a("id", classApiMethodMethod, "_sort2");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSort2 classHtmlPart inputHtmlPart", pk, "Sort2 w3-input w3-border ");
					a("name", "setSort2");
				} else {
					a("class", "valueSort2 w3-input w3-border classHtmlPart inputHtmlPart", pk, "Sort2 w3-input w3-border ");
					a("name", "sort2");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSort2', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_sort2')); }, function() { addError($('#", classApiMethodMethod, "_sort2')); }); ");
				}
				a("value", strSort2())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "Sort2 ").f().sx(htmSort2()).g("span");
			}
		}
	}

	public void htmSort2(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartSort2").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_sort2").a("class", "").f().sx("sort2").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSort2(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_sort2')); $('#", classApiMethodMethod, "_sort2').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setSort2', null, function() { addGlow($('#", classApiMethodMethod, "_sort2')); }, function() { addError($('#", classApiMethodMethod, "_sort2')); }); ")
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

	///////////
	// sort3 //
	///////////

	/**	 The entity sort3
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Double sort3;
	@JsonIgnore
	public Wrap<Double> sort3Wrap = new Wrap<Double>().p(this).c(Double.class).var("sort3").o(sort3);

	/**	<br/> The entity sort3
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sort3">Find the entity sort3 in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _sort3(Wrap<Double> c);

	public Double getSort3() {
		return sort3;
	}

	public void setSort3(Double sort3) {
		this.sort3 = sort3;
		this.sort3Wrap.alreadyInitialized = true;
	}
	public void setSort3(String o) {
		this.sort3 = HtmlPart.staticSetSort3(siteRequest_, o);
		this.sort3Wrap.alreadyInitialized = true;
	}
	public static Double staticSetSort3(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Double.parseDouble(o);
		return null;
	}
	protected HtmlPart sort3Init() {
		if(!sort3Wrap.alreadyInitialized) {
			_sort3(sort3Wrap);
			if(sort3 == null)
				setSort3(sort3Wrap.o);
		}
		sort3Wrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static Double staticSolrSort3(SiteRequestEnUS siteRequest_, Double o) {
		return o;
	}

	public static String staticSolrStrSort3(SiteRequestEnUS siteRequest_, Double o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSort3(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrSort3(siteRequest_, HtmlPart.staticSolrSort3(siteRequest_, HtmlPart.staticSetSort3(siteRequest_, o)));
	}

	public Double solrSort3() {
		return HtmlPart.staticSolrSort3(siteRequest_, sort3);
	}

	public String strSort3() {
		return sort3 == null ? "" : sort3.toString();
	}

	public Double sqlSort3() {
		return sort3;
	}

	public String jsonSort3() {
		return sort3 == null ? "" : sort3.toString();
	}

	public String htmTooltipSort3() {
		return null;
	}

	public String htmSort3() {
		return sort3 == null ? "" : StringEscapeUtils.escapeHtml4(strSort3());
	}

	public void inputSort3(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "sort3")
				.a("id", classApiMethodMethod, "_sort3");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSort3 classHtmlPart inputHtmlPart", pk, "Sort3 w3-input w3-border ");
					a("name", "setSort3");
				} else {
					a("class", "valueSort3 w3-input w3-border classHtmlPart inputHtmlPart", pk, "Sort3 w3-input w3-border ");
					a("name", "sort3");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSort3', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_sort3')); }, function() { addError($('#", classApiMethodMethod, "_sort3')); }); ");
				}
				a("value", strSort3())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "Sort3 ").f().sx(htmSort3()).g("span");
			}
		}
	}

	public void htmSort3(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartSort3").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_sort3").a("class", "").f().sx("sort3").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSort3(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_sort3')); $('#", classApiMethodMethod, "_sort3').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setSort3', null, function() { addGlow($('#", classApiMethodMethod, "_sort3')); }, function() { addError($('#", classApiMethodMethod, "_sort3')); }); ")
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

	///////////
	// sort4 //
	///////////

	/**	 The entity sort4
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Double sort4;
	@JsonIgnore
	public Wrap<Double> sort4Wrap = new Wrap<Double>().p(this).c(Double.class).var("sort4").o(sort4);

	/**	<br/> The entity sort4
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sort4">Find the entity sort4 in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _sort4(Wrap<Double> c);

	public Double getSort4() {
		return sort4;
	}

	public void setSort4(Double sort4) {
		this.sort4 = sort4;
		this.sort4Wrap.alreadyInitialized = true;
	}
	public void setSort4(String o) {
		this.sort4 = HtmlPart.staticSetSort4(siteRequest_, o);
		this.sort4Wrap.alreadyInitialized = true;
	}
	public static Double staticSetSort4(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Double.parseDouble(o);
		return null;
	}
	protected HtmlPart sort4Init() {
		if(!sort4Wrap.alreadyInitialized) {
			_sort4(sort4Wrap);
			if(sort4 == null)
				setSort4(sort4Wrap.o);
		}
		sort4Wrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static Double staticSolrSort4(SiteRequestEnUS siteRequest_, Double o) {
		return o;
	}

	public static String staticSolrStrSort4(SiteRequestEnUS siteRequest_, Double o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSort4(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrSort4(siteRequest_, HtmlPart.staticSolrSort4(siteRequest_, HtmlPart.staticSetSort4(siteRequest_, o)));
	}

	public Double solrSort4() {
		return HtmlPart.staticSolrSort4(siteRequest_, sort4);
	}

	public String strSort4() {
		return sort4 == null ? "" : sort4.toString();
	}

	public Double sqlSort4() {
		return sort4;
	}

	public String jsonSort4() {
		return sort4 == null ? "" : sort4.toString();
	}

	public String htmTooltipSort4() {
		return null;
	}

	public String htmSort4() {
		return sort4 == null ? "" : StringEscapeUtils.escapeHtml4(strSort4());
	}

	public void inputSort4(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "sort4")
				.a("id", classApiMethodMethod, "_sort4");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSort4 classHtmlPart inputHtmlPart", pk, "Sort4 w3-input w3-border ");
					a("name", "setSort4");
				} else {
					a("class", "valueSort4 w3-input w3-border classHtmlPart inputHtmlPart", pk, "Sort4 w3-input w3-border ");
					a("name", "sort4");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSort4', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_sort4')); }, function() { addError($('#", classApiMethodMethod, "_sort4')); }); ");
				}
				a("value", strSort4())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "Sort4 ").f().sx(htmSort4()).g("span");
			}
		}
	}

	public void htmSort4(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartSort4").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_sort4").a("class", "").f().sx("sort4").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSort4(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_sort4')); $('#", classApiMethodMethod, "_sort4').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setSort4', null, function() { addGlow($('#", classApiMethodMethod, "_sort4')); }, function() { addError($('#", classApiMethodMethod, "_sort4')); }); ")
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

	///////////
	// sort5 //
	///////////

	/**	 The entity sort5
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Double sort5;
	@JsonIgnore
	public Wrap<Double> sort5Wrap = new Wrap<Double>().p(this).c(Double.class).var("sort5").o(sort5);

	/**	<br/> The entity sort5
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sort5">Find the entity sort5 in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _sort5(Wrap<Double> c);

	public Double getSort5() {
		return sort5;
	}

	public void setSort5(Double sort5) {
		this.sort5 = sort5;
		this.sort5Wrap.alreadyInitialized = true;
	}
	public void setSort5(String o) {
		this.sort5 = HtmlPart.staticSetSort5(siteRequest_, o);
		this.sort5Wrap.alreadyInitialized = true;
	}
	public static Double staticSetSort5(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Double.parseDouble(o);
		return null;
	}
	protected HtmlPart sort5Init() {
		if(!sort5Wrap.alreadyInitialized) {
			_sort5(sort5Wrap);
			if(sort5 == null)
				setSort5(sort5Wrap.o);
		}
		sort5Wrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static Double staticSolrSort5(SiteRequestEnUS siteRequest_, Double o) {
		return o;
	}

	public static String staticSolrStrSort5(SiteRequestEnUS siteRequest_, Double o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSort5(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrSort5(siteRequest_, HtmlPart.staticSolrSort5(siteRequest_, HtmlPart.staticSetSort5(siteRequest_, o)));
	}

	public Double solrSort5() {
		return HtmlPart.staticSolrSort5(siteRequest_, sort5);
	}

	public String strSort5() {
		return sort5 == null ? "" : sort5.toString();
	}

	public Double sqlSort5() {
		return sort5;
	}

	public String jsonSort5() {
		return sort5 == null ? "" : sort5.toString();
	}

	public String htmTooltipSort5() {
		return null;
	}

	public String htmSort5() {
		return sort5 == null ? "" : StringEscapeUtils.escapeHtml4(strSort5());
	}

	public void inputSort5(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "sort5")
				.a("id", classApiMethodMethod, "_sort5");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSort5 classHtmlPart inputHtmlPart", pk, "Sort5 w3-input w3-border ");
					a("name", "setSort5");
				} else {
					a("class", "valueSort5 w3-input w3-border classHtmlPart inputHtmlPart", pk, "Sort5 w3-input w3-border ");
					a("name", "sort5");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSort5', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_sort5')); }, function() { addError($('#", classApiMethodMethod, "_sort5')); }); ");
				}
				a("value", strSort5())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "Sort5 ").f().sx(htmSort5()).g("span");
			}
		}
	}

	public void htmSort5(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartSort5").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_sort5").a("class", "").f().sx("sort5").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSort5(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_sort5')); $('#", classApiMethodMethod, "_sort5').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setSort5', null, function() { addGlow($('#", classApiMethodMethod, "_sort5')); }, function() { addError($('#", classApiMethodMethod, "_sort5')); }); ")
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

	///////////
	// sort6 //
	///////////

	/**	 The entity sort6
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Double sort6;
	@JsonIgnore
	public Wrap<Double> sort6Wrap = new Wrap<Double>().p(this).c(Double.class).var("sort6").o(sort6);

	/**	<br/> The entity sort6
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sort6">Find the entity sort6 in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _sort6(Wrap<Double> c);

	public Double getSort6() {
		return sort6;
	}

	public void setSort6(Double sort6) {
		this.sort6 = sort6;
		this.sort6Wrap.alreadyInitialized = true;
	}
	public void setSort6(String o) {
		this.sort6 = HtmlPart.staticSetSort6(siteRequest_, o);
		this.sort6Wrap.alreadyInitialized = true;
	}
	public static Double staticSetSort6(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Double.parseDouble(o);
		return null;
	}
	protected HtmlPart sort6Init() {
		if(!sort6Wrap.alreadyInitialized) {
			_sort6(sort6Wrap);
			if(sort6 == null)
				setSort6(sort6Wrap.o);
		}
		sort6Wrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static Double staticSolrSort6(SiteRequestEnUS siteRequest_, Double o) {
		return o;
	}

	public static String staticSolrStrSort6(SiteRequestEnUS siteRequest_, Double o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSort6(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrSort6(siteRequest_, HtmlPart.staticSolrSort6(siteRequest_, HtmlPart.staticSetSort6(siteRequest_, o)));
	}

	public Double solrSort6() {
		return HtmlPart.staticSolrSort6(siteRequest_, sort6);
	}

	public String strSort6() {
		return sort6 == null ? "" : sort6.toString();
	}

	public Double sqlSort6() {
		return sort6;
	}

	public String jsonSort6() {
		return sort6 == null ? "" : sort6.toString();
	}

	public String htmTooltipSort6() {
		return null;
	}

	public String htmSort6() {
		return sort6 == null ? "" : StringEscapeUtils.escapeHtml4(strSort6());
	}

	public void inputSort6(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "sort6")
				.a("id", classApiMethodMethod, "_sort6");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSort6 classHtmlPart inputHtmlPart", pk, "Sort6 w3-input w3-border ");
					a("name", "setSort6");
				} else {
					a("class", "valueSort6 w3-input w3-border classHtmlPart inputHtmlPart", pk, "Sort6 w3-input w3-border ");
					a("name", "sort6");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSort6', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_sort6')); }, function() { addError($('#", classApiMethodMethod, "_sort6')); }); ");
				}
				a("value", strSort6())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "Sort6 ").f().sx(htmSort6()).g("span");
			}
		}
	}

	public void htmSort6(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartSort6").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_sort6").a("class", "").f().sx("sort6").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSort6(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_sort6')); $('#", classApiMethodMethod, "_sort6').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setSort6', null, function() { addGlow($('#", classApiMethodMethod, "_sort6')); }, function() { addError($('#", classApiMethodMethod, "_sort6')); }); ")
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

	///////////
	// sort7 //
	///////////

	/**	 The entity sort7
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Double sort7;
	@JsonIgnore
	public Wrap<Double> sort7Wrap = new Wrap<Double>().p(this).c(Double.class).var("sort7").o(sort7);

	/**	<br/> The entity sort7
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sort7">Find the entity sort7 in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _sort7(Wrap<Double> c);

	public Double getSort7() {
		return sort7;
	}

	public void setSort7(Double sort7) {
		this.sort7 = sort7;
		this.sort7Wrap.alreadyInitialized = true;
	}
	public void setSort7(String o) {
		this.sort7 = HtmlPart.staticSetSort7(siteRequest_, o);
		this.sort7Wrap.alreadyInitialized = true;
	}
	public static Double staticSetSort7(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Double.parseDouble(o);
		return null;
	}
	protected HtmlPart sort7Init() {
		if(!sort7Wrap.alreadyInitialized) {
			_sort7(sort7Wrap);
			if(sort7 == null)
				setSort7(sort7Wrap.o);
		}
		sort7Wrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static Double staticSolrSort7(SiteRequestEnUS siteRequest_, Double o) {
		return o;
	}

	public static String staticSolrStrSort7(SiteRequestEnUS siteRequest_, Double o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSort7(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrSort7(siteRequest_, HtmlPart.staticSolrSort7(siteRequest_, HtmlPart.staticSetSort7(siteRequest_, o)));
	}

	public Double solrSort7() {
		return HtmlPart.staticSolrSort7(siteRequest_, sort7);
	}

	public String strSort7() {
		return sort7 == null ? "" : sort7.toString();
	}

	public Double sqlSort7() {
		return sort7;
	}

	public String jsonSort7() {
		return sort7 == null ? "" : sort7.toString();
	}

	public String htmTooltipSort7() {
		return null;
	}

	public String htmSort7() {
		return sort7 == null ? "" : StringEscapeUtils.escapeHtml4(strSort7());
	}

	public void inputSort7(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "sort7")
				.a("id", classApiMethodMethod, "_sort7");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSort7 classHtmlPart inputHtmlPart", pk, "Sort7 w3-input w3-border ");
					a("name", "setSort7");
				} else {
					a("class", "valueSort7 w3-input w3-border classHtmlPart inputHtmlPart", pk, "Sort7 w3-input w3-border ");
					a("name", "sort7");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSort7', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_sort7')); }, function() { addError($('#", classApiMethodMethod, "_sort7')); }); ");
				}
				a("value", strSort7())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "Sort7 ").f().sx(htmSort7()).g("span");
			}
		}
	}

	public void htmSort7(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartSort7").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_sort7").a("class", "").f().sx("sort7").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSort7(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_sort7')); $('#", classApiMethodMethod, "_sort7').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setSort7', null, function() { addGlow($('#", classApiMethodMethod, "_sort7')); }, function() { addError($('#", classApiMethodMethod, "_sort7')); }); ")
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

	///////////
	// sort8 //
	///////////

	/**	 The entity sort8
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Double sort8;
	@JsonIgnore
	public Wrap<Double> sort8Wrap = new Wrap<Double>().p(this).c(Double.class).var("sort8").o(sort8);

	/**	<br/> The entity sort8
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sort8">Find the entity sort8 in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _sort8(Wrap<Double> c);

	public Double getSort8() {
		return sort8;
	}

	public void setSort8(Double sort8) {
		this.sort8 = sort8;
		this.sort8Wrap.alreadyInitialized = true;
	}
	public void setSort8(String o) {
		this.sort8 = HtmlPart.staticSetSort8(siteRequest_, o);
		this.sort8Wrap.alreadyInitialized = true;
	}
	public static Double staticSetSort8(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Double.parseDouble(o);
		return null;
	}
	protected HtmlPart sort8Init() {
		if(!sort8Wrap.alreadyInitialized) {
			_sort8(sort8Wrap);
			if(sort8 == null)
				setSort8(sort8Wrap.o);
		}
		sort8Wrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static Double staticSolrSort8(SiteRequestEnUS siteRequest_, Double o) {
		return o;
	}

	public static String staticSolrStrSort8(SiteRequestEnUS siteRequest_, Double o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSort8(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrSort8(siteRequest_, HtmlPart.staticSolrSort8(siteRequest_, HtmlPart.staticSetSort8(siteRequest_, o)));
	}

	public Double solrSort8() {
		return HtmlPart.staticSolrSort8(siteRequest_, sort8);
	}

	public String strSort8() {
		return sort8 == null ? "" : sort8.toString();
	}

	public Double sqlSort8() {
		return sort8;
	}

	public String jsonSort8() {
		return sort8 == null ? "" : sort8.toString();
	}

	public String htmTooltipSort8() {
		return null;
	}

	public String htmSort8() {
		return sort8 == null ? "" : StringEscapeUtils.escapeHtml4(strSort8());
	}

	public void inputSort8(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "sort8")
				.a("id", classApiMethodMethod, "_sort8");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSort8 classHtmlPart inputHtmlPart", pk, "Sort8 w3-input w3-border ");
					a("name", "setSort8");
				} else {
					a("class", "valueSort8 w3-input w3-border classHtmlPart inputHtmlPart", pk, "Sort8 w3-input w3-border ");
					a("name", "sort8");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSort8', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_sort8')); }, function() { addError($('#", classApiMethodMethod, "_sort8')); }); ");
				}
				a("value", strSort8())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "Sort8 ").f().sx(htmSort8()).g("span");
			}
		}
	}

	public void htmSort8(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartSort8").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_sort8").a("class", "").f().sx("sort8").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSort8(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_sort8')); $('#", classApiMethodMethod, "_sort8').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setSort8', null, function() { addGlow($('#", classApiMethodMethod, "_sort8')); }, function() { addError($('#", classApiMethodMethod, "_sort8')); }); ")
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

	///////////
	// sort9 //
	///////////

	/**	 The entity sort9
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Double sort9;
	@JsonIgnore
	public Wrap<Double> sort9Wrap = new Wrap<Double>().p(this).c(Double.class).var("sort9").o(sort9);

	/**	<br/> The entity sort9
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sort9">Find the entity sort9 in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _sort9(Wrap<Double> c);

	public Double getSort9() {
		return sort9;
	}

	public void setSort9(Double sort9) {
		this.sort9 = sort9;
		this.sort9Wrap.alreadyInitialized = true;
	}
	public void setSort9(String o) {
		this.sort9 = HtmlPart.staticSetSort9(siteRequest_, o);
		this.sort9Wrap.alreadyInitialized = true;
	}
	public static Double staticSetSort9(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Double.parseDouble(o);
		return null;
	}
	protected HtmlPart sort9Init() {
		if(!sort9Wrap.alreadyInitialized) {
			_sort9(sort9Wrap);
			if(sort9 == null)
				setSort9(sort9Wrap.o);
		}
		sort9Wrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static Double staticSolrSort9(SiteRequestEnUS siteRequest_, Double o) {
		return o;
	}

	public static String staticSolrStrSort9(SiteRequestEnUS siteRequest_, Double o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSort9(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrSort9(siteRequest_, HtmlPart.staticSolrSort9(siteRequest_, HtmlPart.staticSetSort9(siteRequest_, o)));
	}

	public Double solrSort9() {
		return HtmlPart.staticSolrSort9(siteRequest_, sort9);
	}

	public String strSort9() {
		return sort9 == null ? "" : sort9.toString();
	}

	public Double sqlSort9() {
		return sort9;
	}

	public String jsonSort9() {
		return sort9 == null ? "" : sort9.toString();
	}

	public String htmTooltipSort9() {
		return null;
	}

	public String htmSort9() {
		return sort9 == null ? "" : StringEscapeUtils.escapeHtml4(strSort9());
	}

	public void inputSort9(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "sort9")
				.a("id", classApiMethodMethod, "_sort9");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSort9 classHtmlPart inputHtmlPart", pk, "Sort9 w3-input w3-border ");
					a("name", "setSort9");
				} else {
					a("class", "valueSort9 w3-input w3-border classHtmlPart inputHtmlPart", pk, "Sort9 w3-input w3-border ");
					a("name", "sort9");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSort9', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_sort9')); }, function() { addError($('#", classApiMethodMethod, "_sort9')); }); ");
				}
				a("value", strSort9())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "Sort9 ").f().sx(htmSort9()).g("span");
			}
		}
	}

	public void htmSort9(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartSort9").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_sort9").a("class", "").f().sx("sort9").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSort9(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_sort9')); $('#", classApiMethodMethod, "_sort9').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setSort9', null, function() { addGlow($('#", classApiMethodMethod, "_sort9')); }, function() { addError($('#", classApiMethodMethod, "_sort9')); }); ")
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

	////////////
	// sort10 //
	////////////

	/**	 The entity sort10
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Double sort10;
	@JsonIgnore
	public Wrap<Double> sort10Wrap = new Wrap<Double>().p(this).c(Double.class).var("sort10").o(sort10);

	/**	<br/> The entity sort10
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sort10">Find the entity sort10 in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _sort10(Wrap<Double> c);

	public Double getSort10() {
		return sort10;
	}

	public void setSort10(Double sort10) {
		this.sort10 = sort10;
		this.sort10Wrap.alreadyInitialized = true;
	}
	public void setSort10(String o) {
		this.sort10 = HtmlPart.staticSetSort10(siteRequest_, o);
		this.sort10Wrap.alreadyInitialized = true;
	}
	public static Double staticSetSort10(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Double.parseDouble(o);
		return null;
	}
	protected HtmlPart sort10Init() {
		if(!sort10Wrap.alreadyInitialized) {
			_sort10(sort10Wrap);
			if(sort10 == null)
				setSort10(sort10Wrap.o);
		}
		sort10Wrap.alreadyInitialized(true);
		return (HtmlPart)this;
	}

	public static Double staticSolrSort10(SiteRequestEnUS siteRequest_, Double o) {
		return o;
	}

	public static String staticSolrStrSort10(SiteRequestEnUS siteRequest_, Double o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSort10(SiteRequestEnUS siteRequest_, String o) {
		return HtmlPart.staticSolrStrSort10(siteRequest_, HtmlPart.staticSolrSort10(siteRequest_, HtmlPart.staticSetSort10(siteRequest_, o)));
	}

	public Double solrSort10() {
		return HtmlPart.staticSolrSort10(siteRequest_, sort10);
	}

	public String strSort10() {
		return sort10 == null ? "" : sort10.toString();
	}

	public Double sqlSort10() {
		return sort10;
	}

	public String jsonSort10() {
		return sort10 == null ? "" : sort10.toString();
	}

	public String htmTooltipSort10() {
		return null;
	}

	public String htmSort10() {
		return sort10 == null ? "" : StringEscapeUtils.escapeHtml4(strSort10());
	}

	public void inputSort10(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "sort10")
				.a("id", classApiMethodMethod, "_sort10");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setSort10 classHtmlPart inputHtmlPart", pk, "Sort10 w3-input w3-border ");
					a("name", "setSort10");
				} else {
					a("class", "valueSort10 w3-input w3-border classHtmlPart inputHtmlPart", pk, "Sort10 w3-input w3-border ");
					a("name", "sort10");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSort10', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_sort10')); }, function() { addError($('#", classApiMethodMethod, "_sort10')); }); ");
				}
				a("value", strSort10())
			.fg();

		} else {
			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
				e("span").a("class", "varHtmlPart", pk, "Sort10 ").f().sx(htmSort10()).g("span");
			}
		}
	}

	public void htmSort10(String classApiMethodMethod) {
		HtmlPart s = (HtmlPart)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "HtmlPartSort10").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-khaki ").f();
							e("label").a("for", classApiMethodMethod, "_sort10").a("class", "").f().sx("sort10").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSort10(classApiMethodMethod);
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
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_sort10')); $('#", classApiMethodMethod, "_sort10').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#HtmlPartForm :input[name=pk]').val() }], 'setSort10', null, function() { addGlow($('#", classApiMethodMethod, "_sort10')); }, function() { addError($('#", classApiMethodMethod, "_sort10')); }); ")
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

	protected boolean alreadyInitializedHtmlPart = false;

	public HtmlPart initDeepHtmlPart(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedHtmlPart) {
			alreadyInitializedHtmlPart = true;
			initDeepHtmlPart();
		}
		return (HtmlPart)this;
	}

	public void initDeepHtmlPart() {
		initHtmlPart();
		super.initDeepCluster(siteRequest_);
	}

	public void initHtmlPart() {
		htmlPartKeyInit();
		pageDesignKeysInit();
		htmlLinkInit();
		htmlElementInit();
		htmlIdInit();
		htmlClassesInit();
		htmlStyleInit();
		htmlBeforeInit();
		htmlAfterInit();
		htmlTextInit();
		htmlVarInit();
		htmlVarSpanInit();
		htmlVarFormInit();
		htmlVarInputInit();
		htmlVarForEachInit();
		htmlVarHtmlInit();
		htmlVarBase64DecodeInit();
		htmlExcludeInit();
		pdfExcludeInit();
		loginLogoutInit();
		searchUriInit();
		mapToInit();
		sort1Init();
		sort2Init();
		sort3Init();
		sort4Init();
		sort5Init();
		sort6Init();
		sort7Init();
		sort8Init();
		sort9Init();
		sort10Init();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepHtmlPart(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestHtmlPart(SiteRequestEnUS siteRequest_) {
			super.siteRequestCluster(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestHtmlPart(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainHtmlPart(v);
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
	public Object obtainHtmlPart(String var) {
		HtmlPart oHtmlPart = (HtmlPart)this;
		switch(var) {
			case "htmlPartKey":
				return oHtmlPart.htmlPartKey;
			case "pageDesignKeys":
				return oHtmlPart.pageDesignKeys;
			case "htmlLink":
				return oHtmlPart.htmlLink;
			case "htmlElement":
				return oHtmlPart.htmlElement;
			case "htmlId":
				return oHtmlPart.htmlId;
			case "htmlClasses":
				return oHtmlPart.htmlClasses;
			case "htmlStyle":
				return oHtmlPart.htmlStyle;
			case "htmlBefore":
				return oHtmlPart.htmlBefore;
			case "htmlAfter":
				return oHtmlPart.htmlAfter;
			case "htmlText":
				return oHtmlPart.htmlText;
			case "htmlVar":
				return oHtmlPart.htmlVar;
			case "htmlVarSpan":
				return oHtmlPart.htmlVarSpan;
			case "htmlVarForm":
				return oHtmlPart.htmlVarForm;
			case "htmlVarInput":
				return oHtmlPart.htmlVarInput;
			case "htmlVarForEach":
				return oHtmlPart.htmlVarForEach;
			case "htmlVarHtml":
				return oHtmlPart.htmlVarHtml;
			case "htmlVarBase64Decode":
				return oHtmlPart.htmlVarBase64Decode;
			case "htmlExclude":
				return oHtmlPart.htmlExclude;
			case "pdfExclude":
				return oHtmlPart.pdfExclude;
			case "loginLogout":
				return oHtmlPart.loginLogout;
			case "searchUri":
				return oHtmlPart.searchUri;
			case "mapTo":
				return oHtmlPart.mapTo;
			case "sort1":
				return oHtmlPart.sort1;
			case "sort2":
				return oHtmlPart.sort2;
			case "sort3":
				return oHtmlPart.sort3;
			case "sort4":
				return oHtmlPart.sort4;
			case "sort5":
				return oHtmlPart.sort5;
			case "sort6":
				return oHtmlPart.sort6;
			case "sort7":
				return oHtmlPart.sort7;
			case "sort8":
				return oHtmlPart.sort8;
			case "sort9":
				return oHtmlPart.sort9;
			case "sort10":
				return oHtmlPart.sort10;
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
				o = attributeHtmlPart(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeHtmlPart(String var, Object val) {
		HtmlPart oHtmlPart = (HtmlPart)this;
		switch(var) {
			case "pageDesignKeys":
				oHtmlPart.addPageDesignKeys((Long)val);
				if(!saves.contains("pageDesignKeys"))
					saves.add("pageDesignKeys");
				return val;
			default:
				return super.attributeCluster(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetHtmlPart(entityVar,  siteRequest_, o);
	}
	public static Object staticSetHtmlPart(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "htmlPartKey":
			return HtmlPart.staticSetHtmlPartKey(siteRequest_, o);
		case "pageDesignKeys":
			return HtmlPart.staticSetPageDesignKeys(siteRequest_, o);
		case "htmlLink":
			return HtmlPart.staticSetHtmlLink(siteRequest_, o);
		case "htmlElement":
			return HtmlPart.staticSetHtmlElement(siteRequest_, o);
		case "htmlId":
			return HtmlPart.staticSetHtmlId(siteRequest_, o);
		case "htmlClasses":
			return HtmlPart.staticSetHtmlClasses(siteRequest_, o);
		case "htmlStyle":
			return HtmlPart.staticSetHtmlStyle(siteRequest_, o);
		case "htmlBefore":
			return HtmlPart.staticSetHtmlBefore(siteRequest_, o);
		case "htmlAfter":
			return HtmlPart.staticSetHtmlAfter(siteRequest_, o);
		case "htmlText":
			return HtmlPart.staticSetHtmlText(siteRequest_, o);
		case "htmlVar":
			return HtmlPart.staticSetHtmlVar(siteRequest_, o);
		case "htmlVarSpan":
			return HtmlPart.staticSetHtmlVarSpan(siteRequest_, o);
		case "htmlVarForm":
			return HtmlPart.staticSetHtmlVarForm(siteRequest_, o);
		case "htmlVarInput":
			return HtmlPart.staticSetHtmlVarInput(siteRequest_, o);
		case "htmlVarForEach":
			return HtmlPart.staticSetHtmlVarForEach(siteRequest_, o);
		case "htmlVarHtml":
			return HtmlPart.staticSetHtmlVarHtml(siteRequest_, o);
		case "htmlVarBase64Decode":
			return HtmlPart.staticSetHtmlVarBase64Decode(siteRequest_, o);
		case "htmlExclude":
			return HtmlPart.staticSetHtmlExclude(siteRequest_, o);
		case "pdfExclude":
			return HtmlPart.staticSetPdfExclude(siteRequest_, o);
		case "loginLogout":
			return HtmlPart.staticSetLoginLogout(siteRequest_, o);
		case "searchUri":
			return HtmlPart.staticSetSearchUri(siteRequest_, o);
		case "mapTo":
			return HtmlPart.staticSetMapTo(siteRequest_, o);
		case "sort1":
			return HtmlPart.staticSetSort1(siteRequest_, o);
		case "sort2":
			return HtmlPart.staticSetSort2(siteRequest_, o);
		case "sort3":
			return HtmlPart.staticSetSort3(siteRequest_, o);
		case "sort4":
			return HtmlPart.staticSetSort4(siteRequest_, o);
		case "sort5":
			return HtmlPart.staticSetSort5(siteRequest_, o);
		case "sort6":
			return HtmlPart.staticSetSort6(siteRequest_, o);
		case "sort7":
			return HtmlPart.staticSetSort7(siteRequest_, o);
		case "sort8":
			return HtmlPart.staticSetSort8(siteRequest_, o);
		case "sort9":
			return HtmlPart.staticSetSort9(siteRequest_, o);
		case "sort10":
			return HtmlPart.staticSetSort10(siteRequest_, o);
			default:
				return Cluster.staticSetCluster(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrHtmlPart(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrHtmlPart(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "htmlPartKey":
			return HtmlPart.staticSolrHtmlPartKey(siteRequest_, (Long)o);
		case "pageDesignKeys":
			return HtmlPart.staticSolrPageDesignKeys(siteRequest_, (Long)o);
		case "htmlLink":
			return HtmlPart.staticSolrHtmlLink(siteRequest_, (String)o);
		case "htmlElement":
			return HtmlPart.staticSolrHtmlElement(siteRequest_, (String)o);
		case "htmlId":
			return HtmlPart.staticSolrHtmlId(siteRequest_, (String)o);
		case "htmlClasses":
			return HtmlPart.staticSolrHtmlClasses(siteRequest_, (String)o);
		case "htmlStyle":
			return HtmlPart.staticSolrHtmlStyle(siteRequest_, (String)o);
		case "htmlBefore":
			return HtmlPart.staticSolrHtmlBefore(siteRequest_, (String)o);
		case "htmlAfter":
			return HtmlPart.staticSolrHtmlAfter(siteRequest_, (String)o);
		case "htmlText":
			return HtmlPart.staticSolrHtmlText(siteRequest_, (String)o);
		case "htmlVar":
			return HtmlPart.staticSolrHtmlVar(siteRequest_, (String)o);
		case "htmlVarSpan":
			return HtmlPart.staticSolrHtmlVarSpan(siteRequest_, (String)o);
		case "htmlVarForm":
			return HtmlPart.staticSolrHtmlVarForm(siteRequest_, (String)o);
		case "htmlVarInput":
			return HtmlPart.staticSolrHtmlVarInput(siteRequest_, (String)o);
		case "htmlVarForEach":
			return HtmlPart.staticSolrHtmlVarForEach(siteRequest_, (String)o);
		case "htmlVarHtml":
			return HtmlPart.staticSolrHtmlVarHtml(siteRequest_, (String)o);
		case "htmlVarBase64Decode":
			return HtmlPart.staticSolrHtmlVarBase64Decode(siteRequest_, (String)o);
		case "htmlExclude":
			return HtmlPart.staticSolrHtmlExclude(siteRequest_, (Boolean)o);
		case "pdfExclude":
			return HtmlPart.staticSolrPdfExclude(siteRequest_, (Boolean)o);
		case "loginLogout":
			return HtmlPart.staticSolrLoginLogout(siteRequest_, (Boolean)o);
		case "searchUri":
			return HtmlPart.staticSolrSearchUri(siteRequest_, (String)o);
		case "mapTo":
			return HtmlPart.staticSolrMapTo(siteRequest_, (String)o);
		case "sort1":
			return HtmlPart.staticSolrSort1(siteRequest_, (Double)o);
		case "sort2":
			return HtmlPart.staticSolrSort2(siteRequest_, (Double)o);
		case "sort3":
			return HtmlPart.staticSolrSort3(siteRequest_, (Double)o);
		case "sort4":
			return HtmlPart.staticSolrSort4(siteRequest_, (Double)o);
		case "sort5":
			return HtmlPart.staticSolrSort5(siteRequest_, (Double)o);
		case "sort6":
			return HtmlPart.staticSolrSort6(siteRequest_, (Double)o);
		case "sort7":
			return HtmlPart.staticSolrSort7(siteRequest_, (Double)o);
		case "sort8":
			return HtmlPart.staticSolrSort8(siteRequest_, (Double)o);
		case "sort9":
			return HtmlPart.staticSolrSort9(siteRequest_, (Double)o);
		case "sort10":
			return HtmlPart.staticSolrSort10(siteRequest_, (Double)o);
			default:
				return Cluster.staticSolrCluster(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrHtmlPart(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrHtmlPart(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "htmlPartKey":
			return HtmlPart.staticSolrStrHtmlPartKey(siteRequest_, (Long)o);
		case "pageDesignKeys":
			return HtmlPart.staticSolrStrPageDesignKeys(siteRequest_, (Long)o);
		case "htmlLink":
			return HtmlPart.staticSolrStrHtmlLink(siteRequest_, (String)o);
		case "htmlElement":
			return HtmlPart.staticSolrStrHtmlElement(siteRequest_, (String)o);
		case "htmlId":
			return HtmlPart.staticSolrStrHtmlId(siteRequest_, (String)o);
		case "htmlClasses":
			return HtmlPart.staticSolrStrHtmlClasses(siteRequest_, (String)o);
		case "htmlStyle":
			return HtmlPart.staticSolrStrHtmlStyle(siteRequest_, (String)o);
		case "htmlBefore":
			return HtmlPart.staticSolrStrHtmlBefore(siteRequest_, (String)o);
		case "htmlAfter":
			return HtmlPart.staticSolrStrHtmlAfter(siteRequest_, (String)o);
		case "htmlText":
			return HtmlPart.staticSolrStrHtmlText(siteRequest_, (String)o);
		case "htmlVar":
			return HtmlPart.staticSolrStrHtmlVar(siteRequest_, (String)o);
		case "htmlVarSpan":
			return HtmlPart.staticSolrStrHtmlVarSpan(siteRequest_, (String)o);
		case "htmlVarForm":
			return HtmlPart.staticSolrStrHtmlVarForm(siteRequest_, (String)o);
		case "htmlVarInput":
			return HtmlPart.staticSolrStrHtmlVarInput(siteRequest_, (String)o);
		case "htmlVarForEach":
			return HtmlPart.staticSolrStrHtmlVarForEach(siteRequest_, (String)o);
		case "htmlVarHtml":
			return HtmlPart.staticSolrStrHtmlVarHtml(siteRequest_, (String)o);
		case "htmlVarBase64Decode":
			return HtmlPart.staticSolrStrHtmlVarBase64Decode(siteRequest_, (String)o);
		case "htmlExclude":
			return HtmlPart.staticSolrStrHtmlExclude(siteRequest_, (Boolean)o);
		case "pdfExclude":
			return HtmlPart.staticSolrStrPdfExclude(siteRequest_, (Boolean)o);
		case "loginLogout":
			return HtmlPart.staticSolrStrLoginLogout(siteRequest_, (Boolean)o);
		case "searchUri":
			return HtmlPart.staticSolrStrSearchUri(siteRequest_, (String)o);
		case "mapTo":
			return HtmlPart.staticSolrStrMapTo(siteRequest_, (String)o);
		case "sort1":
			return HtmlPart.staticSolrStrSort1(siteRequest_, (Double)o);
		case "sort2":
			return HtmlPart.staticSolrStrSort2(siteRequest_, (Double)o);
		case "sort3":
			return HtmlPart.staticSolrStrSort3(siteRequest_, (Double)o);
		case "sort4":
			return HtmlPart.staticSolrStrSort4(siteRequest_, (Double)o);
		case "sort5":
			return HtmlPart.staticSolrStrSort5(siteRequest_, (Double)o);
		case "sort6":
			return HtmlPart.staticSolrStrSort6(siteRequest_, (Double)o);
		case "sort7":
			return HtmlPart.staticSolrStrSort7(siteRequest_, (Double)o);
		case "sort8":
			return HtmlPart.staticSolrStrSort8(siteRequest_, (Double)o);
		case "sort9":
			return HtmlPart.staticSolrStrSort9(siteRequest_, (Double)o);
		case "sort10":
			return HtmlPart.staticSolrStrSort10(siteRequest_, (Double)o);
			default:
				return Cluster.staticSolrStrCluster(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqHtmlPart(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqHtmlPart(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "htmlPartKey":
			return HtmlPart.staticSolrFqHtmlPartKey(siteRequest_, o);
		case "pageDesignKeys":
			return HtmlPart.staticSolrFqPageDesignKeys(siteRequest_, o);
		case "htmlLink":
			return HtmlPart.staticSolrFqHtmlLink(siteRequest_, o);
		case "htmlElement":
			return HtmlPart.staticSolrFqHtmlElement(siteRequest_, o);
		case "htmlId":
			return HtmlPart.staticSolrFqHtmlId(siteRequest_, o);
		case "htmlClasses":
			return HtmlPart.staticSolrFqHtmlClasses(siteRequest_, o);
		case "htmlStyle":
			return HtmlPart.staticSolrFqHtmlStyle(siteRequest_, o);
		case "htmlBefore":
			return HtmlPart.staticSolrFqHtmlBefore(siteRequest_, o);
		case "htmlAfter":
			return HtmlPart.staticSolrFqHtmlAfter(siteRequest_, o);
		case "htmlText":
			return HtmlPart.staticSolrFqHtmlText(siteRequest_, o);
		case "htmlVar":
			return HtmlPart.staticSolrFqHtmlVar(siteRequest_, o);
		case "htmlVarSpan":
			return HtmlPart.staticSolrFqHtmlVarSpan(siteRequest_, o);
		case "htmlVarForm":
			return HtmlPart.staticSolrFqHtmlVarForm(siteRequest_, o);
		case "htmlVarInput":
			return HtmlPart.staticSolrFqHtmlVarInput(siteRequest_, o);
		case "htmlVarForEach":
			return HtmlPart.staticSolrFqHtmlVarForEach(siteRequest_, o);
		case "htmlVarHtml":
			return HtmlPart.staticSolrFqHtmlVarHtml(siteRequest_, o);
		case "htmlVarBase64Decode":
			return HtmlPart.staticSolrFqHtmlVarBase64Decode(siteRequest_, o);
		case "htmlExclude":
			return HtmlPart.staticSolrFqHtmlExclude(siteRequest_, o);
		case "pdfExclude":
			return HtmlPart.staticSolrFqPdfExclude(siteRequest_, o);
		case "loginLogout":
			return HtmlPart.staticSolrFqLoginLogout(siteRequest_, o);
		case "searchUri":
			return HtmlPart.staticSolrFqSearchUri(siteRequest_, o);
		case "mapTo":
			return HtmlPart.staticSolrFqMapTo(siteRequest_, o);
		case "sort1":
			return HtmlPart.staticSolrFqSort1(siteRequest_, o);
		case "sort2":
			return HtmlPart.staticSolrFqSort2(siteRequest_, o);
		case "sort3":
			return HtmlPart.staticSolrFqSort3(siteRequest_, o);
		case "sort4":
			return HtmlPart.staticSolrFqSort4(siteRequest_, o);
		case "sort5":
			return HtmlPart.staticSolrFqSort5(siteRequest_, o);
		case "sort6":
			return HtmlPart.staticSolrFqSort6(siteRequest_, o);
		case "sort7":
			return HtmlPart.staticSolrFqSort7(siteRequest_, o);
		case "sort8":
			return HtmlPart.staticSolrFqSort8(siteRequest_, o);
		case "sort9":
			return HtmlPart.staticSolrFqSort9(siteRequest_, o);
		case "sort10":
			return HtmlPart.staticSolrFqSort10(siteRequest_, o);
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
					o = defineHtmlPart(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineHtmlPart(String var, String val) {
		switch(var.toLowerCase()) {
			case "htmllink":
				if(val != null)
					setHtmlLink(val);
				saves.add("htmlLink");
				return val;
			case "htmlelement":
				if(val != null)
					setHtmlElement(val);
				saves.add("htmlElement");
				return val;
			case "htmlid":
				if(val != null)
					setHtmlId(val);
				saves.add("htmlId");
				return val;
			case "htmlclasses":
				if(val != null)
					setHtmlClasses(val);
				saves.add("htmlClasses");
				return val;
			case "htmlstyle":
				if(val != null)
					setHtmlStyle(val);
				saves.add("htmlStyle");
				return val;
			case "htmlbefore":
				if(val != null)
					setHtmlBefore(val);
				saves.add("htmlBefore");
				return val;
			case "htmlafter":
				if(val != null)
					setHtmlAfter(val);
				saves.add("htmlAfter");
				return val;
			case "htmltext":
				if(val != null)
					setHtmlText(val);
				saves.add("htmlText");
				return val;
			case "htmlvar":
				if(val != null)
					setHtmlVar(val);
				saves.add("htmlVar");
				return val;
			case "htmlvarspan":
				if(val != null)
					setHtmlVarSpan(val);
				saves.add("htmlVarSpan");
				return val;
			case "htmlvarform":
				if(val != null)
					setHtmlVarForm(val);
				saves.add("htmlVarForm");
				return val;
			case "htmlvarinput":
				if(val != null)
					setHtmlVarInput(val);
				saves.add("htmlVarInput");
				return val;
			case "htmlvarforeach":
				if(val != null)
					setHtmlVarForEach(val);
				saves.add("htmlVarForEach");
				return val;
			case "htmlvarhtml":
				if(val != null)
					setHtmlVarHtml(val);
				saves.add("htmlVarHtml");
				return val;
			case "htmlvarbase64decode":
				if(val != null)
					setHtmlVarBase64Decode(val);
				saves.add("htmlVarBase64Decode");
				return val;
			case "htmlexclude":
				if(val != null)
					setHtmlExclude(val);
				saves.add("htmlExclude");
				return val;
			case "pdfexclude":
				if(val != null)
					setPdfExclude(val);
				saves.add("pdfExclude");
				return val;
			case "loginlogout":
				if(val != null)
					setLoginLogout(val);
				saves.add("loginLogout");
				return val;
			case "searchuri":
				if(val != null)
					setSearchUri(val);
				saves.add("searchUri");
				return val;
			case "mapto":
				if(val != null)
					setMapTo(val);
				saves.add("mapTo");
				return val;
			case "sort1":
				if(val != null)
					setSort1(val);
				saves.add("sort1");
				return val;
			case "sort2":
				if(val != null)
					setSort2(val);
				saves.add("sort2");
				return val;
			case "sort3":
				if(val != null)
					setSort3(val);
				saves.add("sort3");
				return val;
			case "sort4":
				if(val != null)
					setSort4(val);
				saves.add("sort4");
				return val;
			case "sort5":
				if(val != null)
					setSort5(val);
				saves.add("sort5");
				return val;
			case "sort6":
				if(val != null)
					setSort6(val);
				saves.add("sort6");
				return val;
			case "sort7":
				if(val != null)
					setSort7(val);
				saves.add("sort7");
				return val;
			case "sort8":
				if(val != null)
					setSort8(val);
				saves.add("sort8");
				return val;
			case "sort9":
				if(val != null)
					setSort9(val);
				saves.add("sort9");
				return val;
			case "sort10":
				if(val != null)
					setSort10(val);
				saves.add("sort10");
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
					o = defineHtmlPart(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineHtmlPart(String var, Object val) {
		switch(var.toLowerCase()) {
			case "htmllink":
				if(val instanceof String)
					setHtmlLink((String)val);
				saves.add("htmlLink");
				return val;
			case "htmlelement":
				if(val instanceof String)
					setHtmlElement((String)val);
				saves.add("htmlElement");
				return val;
			case "htmlid":
				if(val instanceof String)
					setHtmlId((String)val);
				saves.add("htmlId");
				return val;
			case "htmlclasses":
				if(val instanceof String)
					setHtmlClasses((String)val);
				saves.add("htmlClasses");
				return val;
			case "htmlstyle":
				if(val instanceof String)
					setHtmlStyle((String)val);
				saves.add("htmlStyle");
				return val;
			case "htmlbefore":
				if(val instanceof String)
					setHtmlBefore((String)val);
				saves.add("htmlBefore");
				return val;
			case "htmlafter":
				if(val instanceof String)
					setHtmlAfter((String)val);
				saves.add("htmlAfter");
				return val;
			case "htmltext":
				if(val instanceof String)
					setHtmlText((String)val);
				saves.add("htmlText");
				return val;
			case "htmlvar":
				if(val instanceof String)
					setHtmlVar((String)val);
				saves.add("htmlVar");
				return val;
			case "htmlvarspan":
				if(val instanceof String)
					setHtmlVarSpan((String)val);
				saves.add("htmlVarSpan");
				return val;
			case "htmlvarform":
				if(val instanceof String)
					setHtmlVarForm((String)val);
				saves.add("htmlVarForm");
				return val;
			case "htmlvarinput":
				if(val instanceof String)
					setHtmlVarInput((String)val);
				saves.add("htmlVarInput");
				return val;
			case "htmlvarforeach":
				if(val instanceof String)
					setHtmlVarForEach((String)val);
				saves.add("htmlVarForEach");
				return val;
			case "htmlvarhtml":
				if(val instanceof String)
					setHtmlVarHtml((String)val);
				saves.add("htmlVarHtml");
				return val;
			case "htmlvarbase64decode":
				if(val instanceof String)
					setHtmlVarBase64Decode((String)val);
				saves.add("htmlVarBase64Decode");
				return val;
			case "htmlexclude":
				if(val instanceof Boolean)
					setHtmlExclude((Boolean)val);
				saves.add("htmlExclude");
				return val;
			case "pdfexclude":
				if(val instanceof Boolean)
					setPdfExclude((Boolean)val);
				saves.add("pdfExclude");
				return val;
			case "loginlogout":
				if(val instanceof Boolean)
					setLoginLogout((Boolean)val);
				saves.add("loginLogout");
				return val;
			case "searchuri":
				if(val instanceof String)
					setSearchUri((String)val);
				saves.add("searchUri");
				return val;
			case "mapto":
				if(val instanceof String)
					setMapTo((String)val);
				saves.add("mapTo");
				return val;
			case "sort1":
				if(val instanceof Double)
					setSort1((Double)val);
				saves.add("sort1");
				return val;
			case "sort2":
				if(val instanceof Double)
					setSort2((Double)val);
				saves.add("sort2");
				return val;
			case "sort3":
				if(val instanceof Double)
					setSort3((Double)val);
				saves.add("sort3");
				return val;
			case "sort4":
				if(val instanceof Double)
					setSort4((Double)val);
				saves.add("sort4");
				return val;
			case "sort5":
				if(val instanceof Double)
					setSort5((Double)val);
				saves.add("sort5");
				return val;
			case "sort6":
				if(val instanceof Double)
					setSort6((Double)val);
				saves.add("sort6");
				return val;
			case "sort7":
				if(val instanceof Double)
					setSort7((Double)val);
				saves.add("sort7");
				return val;
			case "sort8":
				if(val instanceof Double)
					setSort8((Double)val);
				saves.add("sort8");
				return val;
			case "sort9":
				if(val instanceof Double)
					setSort9((Double)val);
				saves.add("sort9");
				return val;
			case "sort10":
				if(val instanceof Double)
					setSort10((Double)val);
				saves.add("sort10");
				return val;
			default:
				return super.defineCluster(var, val);
		}
	}

	/////////////
	// populate //
	/////////////

	@Override public void populateForClass(SolrDocument solrDocument) {
		populateHtmlPart(solrDocument);
	}
	public void populateHtmlPart(SolrDocument solrDocument) {
		HtmlPart oHtmlPart = (HtmlPart)this;
		saves = (List<String>)solrDocument.get("saves_stored_strings");
		if(saves != null) {

			if(saves.contains("htmlPartKey")) {
				Long htmlPartKey = (Long)solrDocument.get("htmlPartKey_stored_long");
				if(htmlPartKey != null)
					oHtmlPart.setHtmlPartKey(htmlPartKey);
			}

			List<Long> pageDesignKeys = (List<Long>)solrDocument.get("pageDesignKeys_stored_longs");
			if(pageDesignKeys != null)
				oHtmlPart.pageDesignKeys.addAll(pageDesignKeys);

			if(saves.contains("htmlLink")) {
				String htmlLink = (String)solrDocument.get("htmlLink_stored_string");
				if(htmlLink != null)
					oHtmlPart.setHtmlLink(htmlLink);
			}

			if(saves.contains("htmlElement")) {
				String htmlElement = (String)solrDocument.get("htmlElement_stored_string");
				if(htmlElement != null)
					oHtmlPart.setHtmlElement(htmlElement);
			}

			if(saves.contains("htmlId")) {
				String htmlId = (String)solrDocument.get("htmlId_stored_string");
				if(htmlId != null)
					oHtmlPart.setHtmlId(htmlId);
			}

			if(saves.contains("htmlClasses")) {
				String htmlClasses = (String)solrDocument.get("htmlClasses_stored_string");
				if(htmlClasses != null)
					oHtmlPart.setHtmlClasses(htmlClasses);
			}

			if(saves.contains("htmlStyle")) {
				String htmlStyle = (String)solrDocument.get("htmlStyle_stored_string");
				if(htmlStyle != null)
					oHtmlPart.setHtmlStyle(htmlStyle);
			}

			if(saves.contains("htmlBefore")) {
				String htmlBefore = (String)solrDocument.get("htmlBefore_stored_string");
				if(htmlBefore != null)
					oHtmlPart.setHtmlBefore(htmlBefore);
			}

			if(saves.contains("htmlAfter")) {
				String htmlAfter = (String)solrDocument.get("htmlAfter_stored_string");
				if(htmlAfter != null)
					oHtmlPart.setHtmlAfter(htmlAfter);
			}

			if(saves.contains("htmlText")) {
				String htmlText = (String)solrDocument.get("htmlText_stored_string");
				if(htmlText != null)
					oHtmlPart.setHtmlText(htmlText);
			}

			if(saves.contains("htmlVar")) {
				String htmlVar = (String)solrDocument.get("htmlVar_stored_string");
				if(htmlVar != null)
					oHtmlPart.setHtmlVar(htmlVar);
			}

			if(saves.contains("htmlVarSpan")) {
				String htmlVarSpan = (String)solrDocument.get("htmlVarSpan_stored_string");
				if(htmlVarSpan != null)
					oHtmlPart.setHtmlVarSpan(htmlVarSpan);
			}

			if(saves.contains("htmlVarForm")) {
				String htmlVarForm = (String)solrDocument.get("htmlVarForm_stored_string");
				if(htmlVarForm != null)
					oHtmlPart.setHtmlVarForm(htmlVarForm);
			}

			if(saves.contains("htmlVarInput")) {
				String htmlVarInput = (String)solrDocument.get("htmlVarInput_stored_string");
				if(htmlVarInput != null)
					oHtmlPart.setHtmlVarInput(htmlVarInput);
			}

			if(saves.contains("htmlVarForEach")) {
				String htmlVarForEach = (String)solrDocument.get("htmlVarForEach_stored_string");
				if(htmlVarForEach != null)
					oHtmlPart.setHtmlVarForEach(htmlVarForEach);
			}

			if(saves.contains("htmlVarHtml")) {
				String htmlVarHtml = (String)solrDocument.get("htmlVarHtml_stored_string");
				if(htmlVarHtml != null)
					oHtmlPart.setHtmlVarHtml(htmlVarHtml);
			}

			if(saves.contains("htmlVarBase64Decode")) {
				String htmlVarBase64Decode = (String)solrDocument.get("htmlVarBase64Decode_stored_string");
				if(htmlVarBase64Decode != null)
					oHtmlPart.setHtmlVarBase64Decode(htmlVarBase64Decode);
			}

			if(saves.contains("htmlExclude")) {
				Boolean htmlExclude = (Boolean)solrDocument.get("htmlExclude_stored_boolean");
				if(htmlExclude != null)
					oHtmlPart.setHtmlExclude(htmlExclude);
			}

			if(saves.contains("pdfExclude")) {
				Boolean pdfExclude = (Boolean)solrDocument.get("pdfExclude_stored_boolean");
				if(pdfExclude != null)
					oHtmlPart.setPdfExclude(pdfExclude);
			}

			if(saves.contains("loginLogout")) {
				Boolean loginLogout = (Boolean)solrDocument.get("loginLogout_stored_boolean");
				if(loginLogout != null)
					oHtmlPart.setLoginLogout(loginLogout);
			}

			if(saves.contains("searchUri")) {
				String searchUri = (String)solrDocument.get("searchUri_stored_string");
				if(searchUri != null)
					oHtmlPart.setSearchUri(searchUri);
			}

			if(saves.contains("mapTo")) {
				String mapTo = (String)solrDocument.get("mapTo_stored_string");
				if(mapTo != null)
					oHtmlPart.setMapTo(mapTo);
			}

			if(saves.contains("sort1")) {
				Double sort1 = (Double)solrDocument.get("sort1_stored_double");
				if(sort1 != null)
					oHtmlPart.setSort1(sort1);
			}

			if(saves.contains("sort2")) {
				Double sort2 = (Double)solrDocument.get("sort2_stored_double");
				if(sort2 != null)
					oHtmlPart.setSort2(sort2);
			}

			if(saves.contains("sort3")) {
				Double sort3 = (Double)solrDocument.get("sort3_stored_double");
				if(sort3 != null)
					oHtmlPart.setSort3(sort3);
			}

			if(saves.contains("sort4")) {
				Double sort4 = (Double)solrDocument.get("sort4_stored_double");
				if(sort4 != null)
					oHtmlPart.setSort4(sort4);
			}

			if(saves.contains("sort5")) {
				Double sort5 = (Double)solrDocument.get("sort5_stored_double");
				if(sort5 != null)
					oHtmlPart.setSort5(sort5);
			}

			if(saves.contains("sort6")) {
				Double sort6 = (Double)solrDocument.get("sort6_stored_double");
				if(sort6 != null)
					oHtmlPart.setSort6(sort6);
			}

			if(saves.contains("sort7")) {
				Double sort7 = (Double)solrDocument.get("sort7_stored_double");
				if(sort7 != null)
					oHtmlPart.setSort7(sort7);
			}

			if(saves.contains("sort8")) {
				Double sort8 = (Double)solrDocument.get("sort8_stored_double");
				if(sort8 != null)
					oHtmlPart.setSort8(sort8);
			}

			if(saves.contains("sort9")) {
				Double sort9 = (Double)solrDocument.get("sort9_stored_double");
				if(sort9 != null)
					oHtmlPart.setSort9(sort9);
			}

			if(saves.contains("sort10")) {
				Double sort10 = (Double)solrDocument.get("sort10_stored_double");
				if(sort10 != null)
					oHtmlPart.setSort10(sort10);
			}
		}

		super.populateCluster(solrDocument);
	}


	@Override public void indexForClass() {
		indexHtmlPart();
	}

	@Override public void indexForClass(SolrInputDocument document) {
		indexHtmlPart(document);
	}

	public void indexHtmlPart(SolrClient clientSolr) {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexHtmlPart(document);
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexHtmlPart() {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexHtmlPart(document);
			SolrClient clientSolr = siteRequest_.getSiteContext_().getSolrClient();
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexHtmlPart(SolrInputDocument document) {
		if(htmlPartKey != null) {
			document.addField("htmlPartKey_indexed_long", htmlPartKey);
			document.addField("htmlPartKey_stored_long", htmlPartKey);
		}
		if(pageDesignKeys != null) {
			for(java.lang.Long o : pageDesignKeys) {
				document.addField("pageDesignKeys_indexed_longs", o);
			}
			for(java.lang.Long o : pageDesignKeys) {
				document.addField("pageDesignKeys_stored_longs", o);
			}
		}
		if(htmlLink != null) {
			document.addField("htmlLink_indexed_string", htmlLink);
			document.addField("htmlLink_stored_string", htmlLink);
		}
		if(htmlElement != null) {
			document.addField("htmlElement_indexed_string", htmlElement);
			document.addField("htmlElement_stored_string", htmlElement);
		}
		if(htmlId != null) {
			document.addField("htmlId_indexed_string", htmlId);
			document.addField("htmlId_stored_string", htmlId);
		}
		if(htmlClasses != null) {
			document.addField("htmlClasses_indexed_string", htmlClasses);
			document.addField("htmlClasses_stored_string", htmlClasses);
		}
		if(htmlStyle != null) {
			document.addField("htmlStyle_indexed_string", htmlStyle);
			document.addField("htmlStyle_stored_string", htmlStyle);
		}
		if(htmlBefore != null) {
			document.addField("htmlBefore_stored_string", htmlBefore);
		}
		if(htmlAfter != null) {
			document.addField("htmlAfter_stored_string", htmlAfter);
		}
		if(htmlText != null) {
			document.addField("htmlText_stored_string", htmlText);
		}
		if(htmlVar != null) {
			document.addField("htmlVar_indexed_string", htmlVar);
			document.addField("htmlVar_stored_string", htmlVar);
		}
		if(htmlVarSpan != null) {
			document.addField("htmlVarSpan_indexed_string", htmlVarSpan);
			document.addField("htmlVarSpan_stored_string", htmlVarSpan);
		}
		if(htmlVarForm != null) {
			document.addField("htmlVarForm_indexed_string", htmlVarForm);
			document.addField("htmlVarForm_stored_string", htmlVarForm);
		}
		if(htmlVarInput != null) {
			document.addField("htmlVarInput_indexed_string", htmlVarInput);
			document.addField("htmlVarInput_stored_string", htmlVarInput);
		}
		if(htmlVarForEach != null) {
			document.addField("htmlVarForEach_indexed_string", htmlVarForEach);
			document.addField("htmlVarForEach_stored_string", htmlVarForEach);
		}
		if(htmlVarHtml != null) {
			document.addField("htmlVarHtml_indexed_string", htmlVarHtml);
			document.addField("htmlVarHtml_stored_string", htmlVarHtml);
		}
		if(htmlVarBase64Decode != null) {
			document.addField("htmlVarBase64Decode_indexed_string", htmlVarBase64Decode);
			document.addField("htmlVarBase64Decode_stored_string", htmlVarBase64Decode);
		}
		if(htmlExclude != null) {
			document.addField("htmlExclude_indexed_boolean", htmlExclude);
			document.addField("htmlExclude_stored_boolean", htmlExclude);
		}
		if(pdfExclude != null) {
			document.addField("pdfExclude_indexed_boolean", pdfExclude);
			document.addField("pdfExclude_stored_boolean", pdfExclude);
		}
		if(loginLogout != null) {
			document.addField("loginLogout_indexed_boolean", loginLogout);
			document.addField("loginLogout_stored_boolean", loginLogout);
		}
		if(searchUri != null) {
			document.addField("searchUri_indexed_string", searchUri);
			document.addField("searchUri_stored_string", searchUri);
		}
		if(mapTo != null) {
			document.addField("mapTo_indexed_string", mapTo);
			document.addField("mapTo_stored_string", mapTo);
		}
		if(sort1 != null) {
			document.addField("sort1_indexed_double", sort1);
			document.addField("sort1_stored_double", sort1);
		}
		if(sort2 != null) {
			document.addField("sort2_indexed_double", sort2);
			document.addField("sort2_stored_double", sort2);
		}
		if(sort3 != null) {
			document.addField("sort3_indexed_double", sort3);
			document.addField("sort3_stored_double", sort3);
		}
		if(sort4 != null) {
			document.addField("sort4_indexed_double", sort4);
			document.addField("sort4_stored_double", sort4);
		}
		if(sort5 != null) {
			document.addField("sort5_indexed_double", sort5);
			document.addField("sort5_stored_double", sort5);
		}
		if(sort6 != null) {
			document.addField("sort6_indexed_double", sort6);
			document.addField("sort6_stored_double", sort6);
		}
		if(sort7 != null) {
			document.addField("sort7_indexed_double", sort7);
			document.addField("sort7_stored_double", sort7);
		}
		if(sort8 != null) {
			document.addField("sort8_indexed_double", sort8);
			document.addField("sort8_stored_double", sort8);
		}
		if(sort9 != null) {
			document.addField("sort9_indexed_double", sort9);
			document.addField("sort9_stored_double", sort9);
		}
		if(sort10 != null) {
			document.addField("sort10_indexed_double", sort10);
			document.addField("sort10_stored_double", sort10);
		}
		super.indexCluster(document);

	}

	public static String varIndexedHtmlPart(String entityVar) {
		switch(entityVar) {
			case "htmlPartKey":
				return "htmlPartKey_indexed_long";
			case "pageDesignKeys":
				return "pageDesignKeys_indexed_longs";
			case "htmlLink":
				return "htmlLink_indexed_string";
			case "htmlElement":
				return "htmlElement_indexed_string";
			case "htmlId":
				return "htmlId_indexed_string";
			case "htmlClasses":
				return "htmlClasses_indexed_string";
			case "htmlStyle":
				return "htmlStyle_indexed_string";
			case "htmlVar":
				return "htmlVar_indexed_string";
			case "htmlVarSpan":
				return "htmlVarSpan_indexed_string";
			case "htmlVarForm":
				return "htmlVarForm_indexed_string";
			case "htmlVarInput":
				return "htmlVarInput_indexed_string";
			case "htmlVarForEach":
				return "htmlVarForEach_indexed_string";
			case "htmlVarHtml":
				return "htmlVarHtml_indexed_string";
			case "htmlVarBase64Decode":
				return "htmlVarBase64Decode_indexed_string";
			case "htmlExclude":
				return "htmlExclude_indexed_boolean";
			case "pdfExclude":
				return "pdfExclude_indexed_boolean";
			case "loginLogout":
				return "loginLogout_indexed_boolean";
			case "searchUri":
				return "searchUri_indexed_string";
			case "mapTo":
				return "mapTo_indexed_string";
			case "sort1":
				return "sort1_indexed_double";
			case "sort2":
				return "sort2_indexed_double";
			case "sort3":
				return "sort3_indexed_double";
			case "sort4":
				return "sort4_indexed_double";
			case "sort5":
				return "sort5_indexed_double";
			case "sort6":
				return "sort6_indexed_double";
			case "sort7":
				return "sort7_indexed_double";
			case "sort8":
				return "sort8_indexed_double";
			case "sort9":
				return "sort9_indexed_double";
			case "sort10":
				return "sort10_indexed_double";
			default:
				return Cluster.varIndexedCluster(entityVar);
		}
	}

	public static String varSearchHtmlPart(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSearchCluster(entityVar);
		}
	}

	public static String varSuggestedHtmlPart(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSuggestedCluster(entityVar);
		}
	}

	/////////////
	// store //
	/////////////

	@Override public void storeForClass(SolrDocument solrDocument) {
		storeHtmlPart(solrDocument);
	}
	public void storeHtmlPart(SolrDocument solrDocument) {
		HtmlPart oHtmlPart = (HtmlPart)this;

		Long htmlPartKey = (Long)solrDocument.get("htmlPartKey_stored_long");
		if(htmlPartKey != null)
			oHtmlPart.setHtmlPartKey(htmlPartKey);

		List<Long> pageDesignKeys = (List<Long>)solrDocument.get("pageDesignKeys_stored_longs");
		if(pageDesignKeys != null)
			oHtmlPart.pageDesignKeys.addAll(pageDesignKeys);

		String htmlLink = (String)solrDocument.get("htmlLink_stored_string");
		if(htmlLink != null)
			oHtmlPart.setHtmlLink(htmlLink);

		String htmlElement = (String)solrDocument.get("htmlElement_stored_string");
		if(htmlElement != null)
			oHtmlPart.setHtmlElement(htmlElement);

		String htmlId = (String)solrDocument.get("htmlId_stored_string");
		if(htmlId != null)
			oHtmlPart.setHtmlId(htmlId);

		String htmlClasses = (String)solrDocument.get("htmlClasses_stored_string");
		if(htmlClasses != null)
			oHtmlPart.setHtmlClasses(htmlClasses);

		String htmlStyle = (String)solrDocument.get("htmlStyle_stored_string");
		if(htmlStyle != null)
			oHtmlPart.setHtmlStyle(htmlStyle);

		String htmlBefore = (String)solrDocument.get("htmlBefore_stored_string");
		if(htmlBefore != null)
			oHtmlPart.setHtmlBefore(htmlBefore);

		String htmlAfter = (String)solrDocument.get("htmlAfter_stored_string");
		if(htmlAfter != null)
			oHtmlPart.setHtmlAfter(htmlAfter);

		String htmlText = (String)solrDocument.get("htmlText_stored_string");
		if(htmlText != null)
			oHtmlPart.setHtmlText(htmlText);

		String htmlVar = (String)solrDocument.get("htmlVar_stored_string");
		if(htmlVar != null)
			oHtmlPart.setHtmlVar(htmlVar);

		String htmlVarSpan = (String)solrDocument.get("htmlVarSpan_stored_string");
		if(htmlVarSpan != null)
			oHtmlPart.setHtmlVarSpan(htmlVarSpan);

		String htmlVarForm = (String)solrDocument.get("htmlVarForm_stored_string");
		if(htmlVarForm != null)
			oHtmlPart.setHtmlVarForm(htmlVarForm);

		String htmlVarInput = (String)solrDocument.get("htmlVarInput_stored_string");
		if(htmlVarInput != null)
			oHtmlPart.setHtmlVarInput(htmlVarInput);

		String htmlVarForEach = (String)solrDocument.get("htmlVarForEach_stored_string");
		if(htmlVarForEach != null)
			oHtmlPart.setHtmlVarForEach(htmlVarForEach);

		String htmlVarHtml = (String)solrDocument.get("htmlVarHtml_stored_string");
		if(htmlVarHtml != null)
			oHtmlPart.setHtmlVarHtml(htmlVarHtml);

		String htmlVarBase64Decode = (String)solrDocument.get("htmlVarBase64Decode_stored_string");
		if(htmlVarBase64Decode != null)
			oHtmlPart.setHtmlVarBase64Decode(htmlVarBase64Decode);

		Boolean htmlExclude = (Boolean)solrDocument.get("htmlExclude_stored_boolean");
		if(htmlExclude != null)
			oHtmlPart.setHtmlExclude(htmlExclude);

		Boolean pdfExclude = (Boolean)solrDocument.get("pdfExclude_stored_boolean");
		if(pdfExclude != null)
			oHtmlPart.setPdfExclude(pdfExclude);

		Boolean loginLogout = (Boolean)solrDocument.get("loginLogout_stored_boolean");
		if(loginLogout != null)
			oHtmlPart.setLoginLogout(loginLogout);

		String searchUri = (String)solrDocument.get("searchUri_stored_string");
		if(searchUri != null)
			oHtmlPart.setSearchUri(searchUri);

		String mapTo = (String)solrDocument.get("mapTo_stored_string");
		if(mapTo != null)
			oHtmlPart.setMapTo(mapTo);

		Double sort1 = (Double)solrDocument.get("sort1_stored_double");
		if(sort1 != null)
			oHtmlPart.setSort1(sort1);

		Double sort2 = (Double)solrDocument.get("sort2_stored_double");
		if(sort2 != null)
			oHtmlPart.setSort2(sort2);

		Double sort3 = (Double)solrDocument.get("sort3_stored_double");
		if(sort3 != null)
			oHtmlPart.setSort3(sort3);

		Double sort4 = (Double)solrDocument.get("sort4_stored_double");
		if(sort4 != null)
			oHtmlPart.setSort4(sort4);

		Double sort5 = (Double)solrDocument.get("sort5_stored_double");
		if(sort5 != null)
			oHtmlPart.setSort5(sort5);

		Double sort6 = (Double)solrDocument.get("sort6_stored_double");
		if(sort6 != null)
			oHtmlPart.setSort6(sort6);

		Double sort7 = (Double)solrDocument.get("sort7_stored_double");
		if(sort7 != null)
			oHtmlPart.setSort7(sort7);

		Double sort8 = (Double)solrDocument.get("sort8_stored_double");
		if(sort8 != null)
			oHtmlPart.setSort8(sort8);

		Double sort9 = (Double)solrDocument.get("sort9_stored_double");
		if(sort9 != null)
			oHtmlPart.setSort9(sort9);

		Double sort10 = (Double)solrDocument.get("sort10_stored_double");
		if(sort10 != null)
			oHtmlPart.setSort10(sort10);

		super.storeCluster(solrDocument);
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestHtmlPart() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof HtmlPart) {
			HtmlPart original = (HtmlPart)o;
			if(!Objects.equals(htmlPartKey, original.getHtmlPartKey()))
				apiRequest.addVars("htmlPartKey");
			if(!Objects.equals(pageDesignKeys, original.getPageDesignKeys()))
				apiRequest.addVars("pageDesignKeys");
			if(!Objects.equals(htmlLink, original.getHtmlLink()))
				apiRequest.addVars("htmlLink");
			if(!Objects.equals(htmlElement, original.getHtmlElement()))
				apiRequest.addVars("htmlElement");
			if(!Objects.equals(htmlId, original.getHtmlId()))
				apiRequest.addVars("htmlId");
			if(!Objects.equals(htmlClasses, original.getHtmlClasses()))
				apiRequest.addVars("htmlClasses");
			if(!Objects.equals(htmlStyle, original.getHtmlStyle()))
				apiRequest.addVars("htmlStyle");
			if(!Objects.equals(htmlBefore, original.getHtmlBefore()))
				apiRequest.addVars("htmlBefore");
			if(!Objects.equals(htmlAfter, original.getHtmlAfter()))
				apiRequest.addVars("htmlAfter");
			if(!Objects.equals(htmlText, original.getHtmlText()))
				apiRequest.addVars("htmlText");
			if(!Objects.equals(htmlVar, original.getHtmlVar()))
				apiRequest.addVars("htmlVar");
			if(!Objects.equals(htmlVarSpan, original.getHtmlVarSpan()))
				apiRequest.addVars("htmlVarSpan");
			if(!Objects.equals(htmlVarForm, original.getHtmlVarForm()))
				apiRequest.addVars("htmlVarForm");
			if(!Objects.equals(htmlVarInput, original.getHtmlVarInput()))
				apiRequest.addVars("htmlVarInput");
			if(!Objects.equals(htmlVarForEach, original.getHtmlVarForEach()))
				apiRequest.addVars("htmlVarForEach");
			if(!Objects.equals(htmlVarHtml, original.getHtmlVarHtml()))
				apiRequest.addVars("htmlVarHtml");
			if(!Objects.equals(htmlVarBase64Decode, original.getHtmlVarBase64Decode()))
				apiRequest.addVars("htmlVarBase64Decode");
			if(!Objects.equals(htmlExclude, original.getHtmlExclude()))
				apiRequest.addVars("htmlExclude");
			if(!Objects.equals(pdfExclude, original.getPdfExclude()))
				apiRequest.addVars("pdfExclude");
			if(!Objects.equals(loginLogout, original.getLoginLogout()))
				apiRequest.addVars("loginLogout");
			if(!Objects.equals(searchUri, original.getSearchUri()))
				apiRequest.addVars("searchUri");
			if(!Objects.equals(mapTo, original.getMapTo()))
				apiRequest.addVars("mapTo");
			if(!Objects.equals(sort1, original.getSort1()))
				apiRequest.addVars("sort1");
			if(!Objects.equals(sort2, original.getSort2()))
				apiRequest.addVars("sort2");
			if(!Objects.equals(sort3, original.getSort3()))
				apiRequest.addVars("sort3");
			if(!Objects.equals(sort4, original.getSort4()))
				apiRequest.addVars("sort4");
			if(!Objects.equals(sort5, original.getSort5()))
				apiRequest.addVars("sort5");
			if(!Objects.equals(sort6, original.getSort6()))
				apiRequest.addVars("sort6");
			if(!Objects.equals(sort7, original.getSort7()))
				apiRequest.addVars("sort7");
			if(!Objects.equals(sort8, original.getSort8()))
				apiRequest.addVars("sort8");
			if(!Objects.equals(sort9, original.getSort9()))
				apiRequest.addVars("sort9");
			if(!Objects.equals(sort10, original.getSort10()))
				apiRequest.addVars("sort10");
			super.apiRequestCluster();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), htmlPartKey, pageDesignKeys, htmlLink, htmlElement, htmlId, htmlClasses, htmlStyle, htmlBefore, htmlAfter, htmlText, htmlVar, htmlVarSpan, htmlVarForm, htmlVarInput, htmlVarForEach, htmlVarHtml, htmlVarBase64Decode, htmlExclude, pdfExclude, loginLogout, searchUri, mapTo, sort1, sort2, sort3, sort4, sort5, sort6, sort7, sort8, sort9, sort10);
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof HtmlPart))
			return false;
		HtmlPart that = (HtmlPart)o;
		return super.equals(o)
				&& Objects.equals( htmlPartKey, that.htmlPartKey )
				&& Objects.equals( pageDesignKeys, that.pageDesignKeys )
				&& Objects.equals( htmlLink, that.htmlLink )
				&& Objects.equals( htmlElement, that.htmlElement )
				&& Objects.equals( htmlId, that.htmlId )
				&& Objects.equals( htmlClasses, that.htmlClasses )
				&& Objects.equals( htmlStyle, that.htmlStyle )
				&& Objects.equals( htmlBefore, that.htmlBefore )
				&& Objects.equals( htmlAfter, that.htmlAfter )
				&& Objects.equals( htmlText, that.htmlText )
				&& Objects.equals( htmlVar, that.htmlVar )
				&& Objects.equals( htmlVarSpan, that.htmlVarSpan )
				&& Objects.equals( htmlVarForm, that.htmlVarForm )
				&& Objects.equals( htmlVarInput, that.htmlVarInput )
				&& Objects.equals( htmlVarForEach, that.htmlVarForEach )
				&& Objects.equals( htmlVarHtml, that.htmlVarHtml )
				&& Objects.equals( htmlVarBase64Decode, that.htmlVarBase64Decode )
				&& Objects.equals( htmlExclude, that.htmlExclude )
				&& Objects.equals( pdfExclude, that.pdfExclude )
				&& Objects.equals( loginLogout, that.loginLogout )
				&& Objects.equals( searchUri, that.searchUri )
				&& Objects.equals( mapTo, that.mapTo )
				&& Objects.equals( sort1, that.sort1 )
				&& Objects.equals( sort2, that.sort2 )
				&& Objects.equals( sort3, that.sort3 )
				&& Objects.equals( sort4, that.sort4 )
				&& Objects.equals( sort5, that.sort5 )
				&& Objects.equals( sort6, that.sort6 )
				&& Objects.equals( sort7, that.sort7 )
				&& Objects.equals( sort8, that.sort8 )
				&& Objects.equals( sort9, that.sort9 )
				&& Objects.equals( sort10, that.sort10 );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("HtmlPart { ");
		sb.append( "htmlPartKey: " ).append(htmlPartKey);
		sb.append( ", pageDesignKeys: " ).append(pageDesignKeys);
		sb.append( ", htmlLink: \"" ).append(htmlLink).append( "\"" );
		sb.append( ", htmlElement: \"" ).append(htmlElement).append( "\"" );
		sb.append( ", htmlId: \"" ).append(htmlId).append( "\"" );
		sb.append( ", htmlClasses: \"" ).append(htmlClasses).append( "\"" );
		sb.append( ", htmlStyle: \"" ).append(htmlStyle).append( "\"" );
		sb.append( ", htmlBefore: \"" ).append(htmlBefore).append( "\"" );
		sb.append( ", htmlAfter: \"" ).append(htmlAfter).append( "\"" );
		sb.append( ", htmlText: \"" ).append(htmlText).append( "\"" );
		sb.append( ", htmlVar: \"" ).append(htmlVar).append( "\"" );
		sb.append( ", htmlVarSpan: \"" ).append(htmlVarSpan).append( "\"" );
		sb.append( ", htmlVarForm: \"" ).append(htmlVarForm).append( "\"" );
		sb.append( ", htmlVarInput: \"" ).append(htmlVarInput).append( "\"" );
		sb.append( ", htmlVarForEach: \"" ).append(htmlVarForEach).append( "\"" );
		sb.append( ", htmlVarHtml: \"" ).append(htmlVarHtml).append( "\"" );
		sb.append( ", htmlVarBase64Decode: \"" ).append(htmlVarBase64Decode).append( "\"" );
		sb.append( ", htmlExclude: " ).append(htmlExclude);
		sb.append( ", pdfExclude: " ).append(pdfExclude);
		sb.append( ", loginLogout: " ).append(loginLogout);
		sb.append( ", searchUri: \"" ).append(searchUri).append( "\"" );
		sb.append( ", mapTo: \"" ).append(mapTo).append( "\"" );
		sb.append( ", sort1: " ).append(sort1);
		sb.append( ", sort2: " ).append(sort2);
		sb.append( ", sort3: " ).append(sort3);
		sb.append( ", sort4: " ).append(sort4);
		sb.append( ", sort5: " ).append(sort5);
		sb.append( ", sort6: " ).append(sort6);
		sb.append( ", sort7: " ).append(sort7);
		sb.append( ", sort8: " ).append(sort8);
		sb.append( ", sort9: " ).append(sort9);
		sb.append( ", sort10: " ).append(sort10);
		sb.append(" }");
		return sb.toString();
	}
}
