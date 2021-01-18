package com.opendatapolicing.enus.html.part;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.lang.Double;
import java.util.Date;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import io.vertx.core.logging.LoggerFactory;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import java.lang.Long;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
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
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.html.part.HtmlPart&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class HtmlPartGen<DEV> extends Cluster {
	protected static final Logger LOGGER = LoggerFactory.getLogger(HtmlPart.class);

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

	public String jsonHtmlPartKey() {
		return htmlPartKey == null ? "" : htmlPartKey.toString();
	}

	public String nomAffichageHtmlPartKey() {
		return null;
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

	public String jsonPageDesignKeys() {
		return pageDesignKeys == null ? "" : pageDesignKeys.toString();
	}

	public String nomAffichagePageDesignKeys() {
		return null;
	}

	public String htmTooltipPageDesignKeys() {
		return null;
	}

	public String htmPageDesignKeys() {
		return pageDesignKeys == null ? "" : StringEscapeUtils.escapeHtml4(strPageDesignKeys());
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

	public String jsonHtmlLink() {
		return htmlLink == null ? "" : htmlLink;
	}

	public String nomAffichageHtmlLink() {
		return null;
	}

	public String htmTooltipHtmlLink() {
		return null;
	}

	public String htmHtmlLink() {
		return htmlLink == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlLink());
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

	public String jsonHtmlElement() {
		return htmlElement == null ? "" : htmlElement;
	}

	public String nomAffichageHtmlElement() {
		return null;
	}

	public String htmTooltipHtmlElement() {
		return null;
	}

	public String htmHtmlElement() {
		return htmlElement == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlElement());
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

	public String jsonHtmlId() {
		return htmlId == null ? "" : htmlId;
	}

	public String nomAffichageHtmlId() {
		return null;
	}

	public String htmTooltipHtmlId() {
		return null;
	}

	public String htmHtmlId() {
		return htmlId == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlId());
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

	public String jsonHtmlClasses() {
		return htmlClasses == null ? "" : htmlClasses;
	}

	public String nomAffichageHtmlClasses() {
		return null;
	}

	public String htmTooltipHtmlClasses() {
		return null;
	}

	public String htmHtmlClasses() {
		return htmlClasses == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlClasses());
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

	public String jsonHtmlStyle() {
		return htmlStyle == null ? "" : htmlStyle;
	}

	public String nomAffichageHtmlStyle() {
		return null;
	}

	public String htmTooltipHtmlStyle() {
		return null;
	}

	public String htmHtmlStyle() {
		return htmlStyle == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlStyle());
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

	public String jsonHtmlBefore() {
		return htmlBefore == null ? "" : htmlBefore;
	}

	public String nomAffichageHtmlBefore() {
		return null;
	}

	public String htmTooltipHtmlBefore() {
		return null;
	}

	public String htmHtmlBefore() {
		return htmlBefore == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlBefore());
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

	public String jsonHtmlAfter() {
		return htmlAfter == null ? "" : htmlAfter;
	}

	public String nomAffichageHtmlAfter() {
		return null;
	}

	public String htmTooltipHtmlAfter() {
		return null;
	}

	public String htmHtmlAfter() {
		return htmlAfter == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlAfter());
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

	public String jsonHtmlText() {
		return htmlText == null ? "" : htmlText;
	}

	public String nomAffichageHtmlText() {
		return null;
	}

	public String htmTooltipHtmlText() {
		return null;
	}

	public String htmHtmlText() {
		return htmlText == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlText());
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

	public String jsonHtmlVar() {
		return htmlVar == null ? "" : htmlVar;
	}

	public String nomAffichageHtmlVar() {
		return null;
	}

	public String htmTooltipHtmlVar() {
		return null;
	}

	public String htmHtmlVar() {
		return htmlVar == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlVar());
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

	public String jsonHtmlVarSpan() {
		return htmlVarSpan == null ? "" : htmlVarSpan;
	}

	public String nomAffichageHtmlVarSpan() {
		return null;
	}

	public String htmTooltipHtmlVarSpan() {
		return null;
	}

	public String htmHtmlVarSpan() {
		return htmlVarSpan == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlVarSpan());
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

	public String jsonHtmlVarForm() {
		return htmlVarForm == null ? "" : htmlVarForm;
	}

	public String nomAffichageHtmlVarForm() {
		return null;
	}

	public String htmTooltipHtmlVarForm() {
		return null;
	}

	public String htmHtmlVarForm() {
		return htmlVarForm == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlVarForm());
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

	public String jsonHtmlVarInput() {
		return htmlVarInput == null ? "" : htmlVarInput;
	}

	public String nomAffichageHtmlVarInput() {
		return null;
	}

	public String htmTooltipHtmlVarInput() {
		return null;
	}

	public String htmHtmlVarInput() {
		return htmlVarInput == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlVarInput());
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

	public String jsonHtmlVarForEach() {
		return htmlVarForEach == null ? "" : htmlVarForEach;
	}

	public String nomAffichageHtmlVarForEach() {
		return null;
	}

	public String htmTooltipHtmlVarForEach() {
		return null;
	}

	public String htmHtmlVarForEach() {
		return htmlVarForEach == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlVarForEach());
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

	public String jsonHtmlVarHtml() {
		return htmlVarHtml == null ? "" : htmlVarHtml;
	}

	public String nomAffichageHtmlVarHtml() {
		return null;
	}

	public String htmTooltipHtmlVarHtml() {
		return null;
	}

	public String htmHtmlVarHtml() {
		return htmlVarHtml == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlVarHtml());
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

	public String jsonHtmlVarBase64Decode() {
		return htmlVarBase64Decode == null ? "" : htmlVarBase64Decode;
	}

	public String nomAffichageHtmlVarBase64Decode() {
		return null;
	}

	public String htmTooltipHtmlVarBase64Decode() {
		return null;
	}

	public String htmHtmlVarBase64Decode() {
		return htmlVarBase64Decode == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlVarBase64Decode());
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

	public String jsonHtmlExclude() {
		return htmlExclude == null ? "" : htmlExclude.toString();
	}

	public String nomAffichageHtmlExclude() {
		return null;
	}

	public String htmTooltipHtmlExclude() {
		return null;
	}

	public String htmHtmlExclude() {
		return htmlExclude == null ? "" : StringEscapeUtils.escapeHtml4(strHtmlExclude());
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

	public String jsonPdfExclude() {
		return pdfExclude == null ? "" : pdfExclude.toString();
	}

	public String nomAffichagePdfExclude() {
		return null;
	}

	public String htmTooltipPdfExclude() {
		return null;
	}

	public String htmPdfExclude() {
		return pdfExclude == null ? "" : StringEscapeUtils.escapeHtml4(strPdfExclude());
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

	public String jsonLoginLogout() {
		return loginLogout == null ? "" : loginLogout.toString();
	}

	public String nomAffichageLoginLogout() {
		return null;
	}

	public String htmTooltipLoginLogout() {
		return null;
	}

	public String htmLoginLogout() {
		return loginLogout == null ? "" : StringEscapeUtils.escapeHtml4(strLoginLogout());
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

	public String jsonSort1() {
		return sort1 == null ? "" : sort1.toString();
	}

	public String nomAffichageSort1() {
		return null;
	}

	public String htmTooltipSort1() {
		return null;
	}

	public String htmSort1() {
		return sort1 == null ? "" : StringEscapeUtils.escapeHtml4(strSort1());
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

	public String jsonSort2() {
		return sort2 == null ? "" : sort2.toString();
	}

	public String nomAffichageSort2() {
		return null;
	}

	public String htmTooltipSort2() {
		return null;
	}

	public String htmSort2() {
		return sort2 == null ? "" : StringEscapeUtils.escapeHtml4(strSort2());
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

	public String jsonSort3() {
		return sort3 == null ? "" : sort3.toString();
	}

	public String nomAffichageSort3() {
		return null;
	}

	public String htmTooltipSort3() {
		return null;
	}

	public String htmSort3() {
		return sort3 == null ? "" : StringEscapeUtils.escapeHtml4(strSort3());
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

	public String jsonSort4() {
		return sort4 == null ? "" : sort4.toString();
	}

	public String nomAffichageSort4() {
		return null;
	}

	public String htmTooltipSort4() {
		return null;
	}

	public String htmSort4() {
		return sort4 == null ? "" : StringEscapeUtils.escapeHtml4(strSort4());
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

	public String jsonSort5() {
		return sort5 == null ? "" : sort5.toString();
	}

	public String nomAffichageSort5() {
		return null;
	}

	public String htmTooltipSort5() {
		return null;
	}

	public String htmSort5() {
		return sort5 == null ? "" : StringEscapeUtils.escapeHtml4(strSort5());
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

	public String jsonSort6() {
		return sort6 == null ? "" : sort6.toString();
	}

	public String nomAffichageSort6() {
		return null;
	}

	public String htmTooltipSort6() {
		return null;
	}

	public String htmSort6() {
		return sort6 == null ? "" : StringEscapeUtils.escapeHtml4(strSort6());
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

	public String jsonSort7() {
		return sort7 == null ? "" : sort7.toString();
	}

	public String nomAffichageSort7() {
		return null;
	}

	public String htmTooltipSort7() {
		return null;
	}

	public String htmSort7() {
		return sort7 == null ? "" : StringEscapeUtils.escapeHtml4(strSort7());
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

	public String jsonSort8() {
		return sort8 == null ? "" : sort8.toString();
	}

	public String nomAffichageSort8() {
		return null;
	}

	public String htmTooltipSort8() {
		return null;
	}

	public String htmSort8() {
		return sort8 == null ? "" : StringEscapeUtils.escapeHtml4(strSort8());
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

	public String jsonSort9() {
		return sort9 == null ? "" : sort9.toString();
	}

	public String nomAffichageSort9() {
		return null;
	}

	public String htmTooltipSort9() {
		return null;
	}

	public String htmSort9() {
		return sort9 == null ? "" : StringEscapeUtils.escapeHtml4(strSort9());
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

	public String jsonSort10() {
		return sort10 == null ? "" : sort10.toString();
	}

	public String nomAffichageSort10() {
		return null;
	}

	public String htmTooltipSort10() {
		return null;
	}

	public String htmSort10() {
		return sort10 == null ? "" : StringEscapeUtils.escapeHtml4(strSort10());
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
		switch(var) {
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
			solrQuery.addFilterQuery("id:" + ClientUtils.escapeQueryChars("com.opendatapolicing.enus.html.part.HtmlPart"));
			QueryResponse queryResponse = siteRequest.getSiteContext_().getSolrClient().query(solrQuery);
			if(queryResponse.getResults().size() > 0)
				siteRequest.setSolrDocument(queryResponse.getResults().get(0));
			HtmlPart o = new HtmlPart();
			o.siteRequestHtmlPart(siteRequest);
			o.initDeepHtmlPart(siteRequest);
			o.indexHtmlPart();
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
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
		super.indexCluster(document);

	}

	public static String varIndexedHtmlPart(String entityVar) {
		switch(entityVar) {
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
		if(!(o instanceof HtmlPart))
			return false;
		HtmlPart that = (HtmlPart)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("HtmlPart { ");
		sb.append(" }");
		return sb.toString();
	}
}
