package com.opendatapolicing.enus.html.part;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.slf4j.LoggerFactory;
import com.opendatapolicing.enus.html.part.HtmlPart;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import com.opendatapolicing.enus.search.SearchList;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.RoundingMode;
import com.opendatapolicing.enus.wrap.Wrap;
import org.slf4j.Logger;
import java.math.MathContext;
import com.opendatapolicing.enus.writer.AllWriter;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.opendatapolicing.enus.request.api.ApiRequest;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import com.opendatapolicing.enus.page.PageLayout;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.opendatapolicing.enus.cluster.Cluster;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

public abstract class HtmlPartGenPageGen<DEV> extends PageLayout {
	protected static final Logger LOG = LoggerFactory.getLogger(HtmlPartGenPage.class);

	//////////////////
	// listHtmlPart //
	//////////////////

	@JsonInclude(Include.NON_NULL)
	protected SearchList<HtmlPart> listHtmlPart;
	@JsonIgnore
	public Wrap<SearchList<HtmlPart>> listHtmlPartWrap = new Wrap<SearchList<HtmlPart>>().p(this).c(SearchList.class).var("listHtmlPart").o(listHtmlPart);

	protected abstract void _listHtmlPart(Wrap<SearchList<HtmlPart>> c);

	public SearchList<HtmlPart> getListHtmlPart() {
		return listHtmlPart;
	}

	public void setListHtmlPart(SearchList<HtmlPart> listHtmlPart) {
		this.listHtmlPart = listHtmlPart;
		this.listHtmlPartWrap.alreadyInitialized = true;
	}
	public static SearchList<HtmlPart> staticSetListHtmlPart(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected HtmlPartGenPage listHtmlPartInit() {
		if(!listHtmlPartWrap.alreadyInitialized) {
			_listHtmlPart(listHtmlPartWrap);
			if(listHtmlPart == null)
				setListHtmlPart(listHtmlPartWrap.o);
		}
		if(listHtmlPart != null)
			listHtmlPart.initDeepForClass(siteRequest_);
		listHtmlPartWrap.alreadyInitialized(true);
		return (HtmlPartGenPage)this;
	}

	///////////////
	// htmlPart_ //
	///////////////

	@JsonInclude(Include.NON_NULL)
	protected HtmlPart htmlPart_;
	@JsonIgnore
	public Wrap<HtmlPart> htmlPart_Wrap = new Wrap<HtmlPart>().p(this).c(HtmlPart.class).var("htmlPart_").o(htmlPart_);

	protected abstract void _htmlPart_(Wrap<HtmlPart> c);

	public HtmlPart getHtmlPart_() {
		return htmlPart_;
	}

	public void setHtmlPart_(HtmlPart htmlPart_) {
		this.htmlPart_ = htmlPart_;
		this.htmlPart_Wrap.alreadyInitialized = true;
	}
	public static HtmlPart staticSetHtmlPart_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected HtmlPartGenPage htmlPart_Init() {
		if(!htmlPart_Wrap.alreadyInitialized) {
			_htmlPart_(htmlPart_Wrap);
			if(htmlPart_ == null)
				setHtmlPart_(htmlPart_Wrap.o);
		}
		htmlPart_Wrap.alreadyInitialized(true);
		return (HtmlPartGenPage)this;
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedHtmlPartGenPage = false;

	public HtmlPartGenPage initDeepHtmlPartGenPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedHtmlPartGenPage) {
			alreadyInitializedHtmlPartGenPage = true;
			initDeepHtmlPartGenPage();
		}
		return (HtmlPartGenPage)this;
	}

	public void initDeepHtmlPartGenPage() {
		initHtmlPartGenPage();
		super.initDeepPageLayout(siteRequest_);
	}

	public void initHtmlPartGenPage() {
		listHtmlPartInit();
		htmlPart_Init();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepHtmlPartGenPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestHtmlPartGenPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestPageLayout(siteRequest_);
		if(listHtmlPart != null)
			listHtmlPart.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestHtmlPartGenPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainHtmlPartGenPage(v);
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
	public Object obtainHtmlPartGenPage(String var) {
		HtmlPartGenPage oHtmlPartGenPage = (HtmlPartGenPage)this;
		switch(var) {
			case "listHtmlPart":
				return oHtmlPartGenPage.listHtmlPart;
			case "htmlPart_":
				return oHtmlPartGenPage.htmlPart_;
			default:
				return super.obtainPageLayout(var);
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
				o = attributeHtmlPartGenPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeHtmlPartGenPage(String var, Object val) {
		HtmlPartGenPage oHtmlPartGenPage = (HtmlPartGenPage)this;
		switch(var) {
			default:
				return super.attributePageLayout(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetHtmlPartGenPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSetHtmlPartGenPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSetPageLayout(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrHtmlPartGenPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrHtmlPartGenPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSolrPageLayout(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrHtmlPartGenPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrHtmlPartGenPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSolrStrPageLayout(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqHtmlPartGenPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqHtmlPartGenPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSolrFqPageLayout(entityVar,  siteRequest_, o);
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
					o = defineHtmlPartGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineHtmlPartGenPage(String var, String val) {
		switch(var.toLowerCase()) {
			default:
				return super.definePageLayout(var, val);
		}
	}

	@Override public boolean defineForClass(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = defineHtmlPartGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineHtmlPartGenPage(String var, Object val) {
		switch(var.toLowerCase()) {
			default:
				return super.definePageLayout(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsHtmlPartGenPage();
		super.htmlScripts();
	}

	public void htmlScriptsHtmlPartGenPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptHtmlPartGenPage();
		super.htmlScript();
	}

	public void htmlScriptHtmlPartGenPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodyHtmlPartGenPage();
		super.htmlBody();
	}

	public void htmlBodyHtmlPartGenPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlHtmlPartGenPage();
		super.html();
	}

	public void htmlHtmlPartGenPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaHtmlPartGenPage();
		super.htmlMeta();
	}

	public void htmlMetaHtmlPartGenPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesHtmlPartGenPage();
		super.htmlStyles();
	}

	public void htmlStylesHtmlPartGenPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleHtmlPartGenPage();
		super.htmlStyle();
	}

	public void htmlStyleHtmlPartGenPage() {
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestHtmlPartGenPage() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof HtmlPartGenPage) {
			HtmlPartGenPage original = (HtmlPartGenPage)o;
			super.apiRequestPageLayout();
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
		if(!(o instanceof HtmlPartGenPage))
			return false;
		HtmlPartGenPage that = (HtmlPartGenPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("HtmlPartGenPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
