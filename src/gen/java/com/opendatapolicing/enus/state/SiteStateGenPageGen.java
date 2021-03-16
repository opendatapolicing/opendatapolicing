package com.opendatapolicing.enus.state;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import com.opendatapolicing.enus.state.SiteState;
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

public abstract class SiteStateGenPageGen<DEV> extends PageLayout {
	protected static final Logger LOG = LoggerFactory.getLogger(SiteStateGenPage.class);

	///////////////////
	// listSiteState //
	///////////////////

	@JsonInclude(Include.NON_NULL)
	protected SearchList<SiteState> listSiteState;
	@JsonIgnore
	public Wrap<SearchList<SiteState>> listSiteStateWrap = new Wrap<SearchList<SiteState>>().p(this).c(SearchList.class).var("listSiteState").o(listSiteState);

	protected abstract void _listSiteState(Wrap<SearchList<SiteState>> c);

	public SearchList<SiteState> getListSiteState() {
		return listSiteState;
	}

	public void setListSiteState(SearchList<SiteState> listSiteState) {
		this.listSiteState = listSiteState;
		this.listSiteStateWrap.alreadyInitialized = true;
	}
	public static SearchList<SiteState> staticSetListSiteState(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteStateGenPage listSiteStateInit() {
		if(!listSiteStateWrap.alreadyInitialized) {
			_listSiteState(listSiteStateWrap);
			if(listSiteState == null)
				setListSiteState(listSiteStateWrap.o);
		}
		if(listSiteState != null)
			listSiteState.initDeepForClass(siteRequest_);
		listSiteStateWrap.alreadyInitialized(true);
		return (SiteStateGenPage)this;
	}

	////////////////
	// siteState_ //
	////////////////

	@JsonInclude(Include.NON_NULL)
	protected SiteState siteState_;
	@JsonIgnore
	public Wrap<SiteState> siteState_Wrap = new Wrap<SiteState>().p(this).c(SiteState.class).var("siteState_").o(siteState_);

	protected abstract void _siteState_(Wrap<SiteState> c);

	public SiteState getSiteState_() {
		return siteState_;
	}

	public void setSiteState_(SiteState siteState_) {
		this.siteState_ = siteState_;
		this.siteState_Wrap.alreadyInitialized = true;
	}
	public static SiteState staticSetSiteState_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteStateGenPage siteState_Init() {
		if(!siteState_Wrap.alreadyInitialized) {
			_siteState_(siteState_Wrap);
			if(siteState_ == null)
				setSiteState_(siteState_Wrap.o);
		}
		siteState_Wrap.alreadyInitialized(true);
		return (SiteStateGenPage)this;
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedSiteStateGenPage = false;

	public SiteStateGenPage initDeepSiteStateGenPage(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedSiteStateGenPage) {
			alreadyInitializedSiteStateGenPage = true;
			initDeepSiteStateGenPage();
		}
		return (SiteStateGenPage)this;
	}

	public void initDeepSiteStateGenPage() {
		initSiteStateGenPage();
		super.initDeepPageLayout(siteRequest_);
	}

	public void initSiteStateGenPage() {
		listSiteStateInit();
		siteState_Init();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepSiteStateGenPage(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestSiteStateGenPage(SiteRequestEnUS siteRequest_) {
			super.siteRequestPageLayout(siteRequest_);
		if(listSiteState != null)
			listSiteState.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestSiteStateGenPage(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainSiteStateGenPage(v);
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
	public Object obtainSiteStateGenPage(String var) {
		SiteStateGenPage oSiteStateGenPage = (SiteStateGenPage)this;
		switch(var) {
			case "listSiteState":
				return oSiteStateGenPage.listSiteState;
			case "siteState_":
				return oSiteStateGenPage.siteState_;
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
				o = attributeSiteStateGenPage(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeSiteStateGenPage(String var, Object val) {
		SiteStateGenPage oSiteStateGenPage = (SiteStateGenPage)this;
		switch(var) {
			default:
				return super.attributePageLayout(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetSiteStateGenPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSetSiteStateGenPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSetPageLayout(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrSiteStateGenPage(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrSiteStateGenPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSolrPageLayout(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrSiteStateGenPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrSiteStateGenPage(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return PageLayout.staticSolrStrPageLayout(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqSiteStateGenPage(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqSiteStateGenPage(String entityVar, SiteRequestEnUS siteRequest_, String o) {
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
					o = defineSiteStateGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSiteStateGenPage(String var, String val) {
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
					o = defineSiteStateGenPage(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSiteStateGenPage(String var, Object val) {
		switch(var.toLowerCase()) {
			default:
				return super.definePageLayout(var, val);
		}
	}

	/////////////////
	// htmlScripts //
	/////////////////

	@Override public void htmlScripts() {
		htmlScriptsSiteStateGenPage();
		super.htmlScripts();
	}

	public void htmlScriptsSiteStateGenPage() {
	}

	////////////////
	// htmlScript //
	////////////////

	@Override public void htmlScript() {
		htmlScriptSiteStateGenPage();
		super.htmlScript();
	}

	public void htmlScriptSiteStateGenPage() {
	}

	//////////////
	// htmlBody //
	//////////////

	@Override public void htmlBody() {
		htmlBodySiteStateGenPage();
		super.htmlBody();
	}

	public void htmlBodySiteStateGenPage() {
	}

	//////////
	// html //
	//////////

	@Override public void html() {
		htmlSiteStateGenPage();
		super.html();
	}

	public void htmlSiteStateGenPage() {
	}

	//////////////
	// htmlMeta //
	//////////////

	@Override public void htmlMeta() {
		htmlMetaSiteStateGenPage();
		super.htmlMeta();
	}

	public void htmlMetaSiteStateGenPage() {
	}

	////////////////
	// htmlStyles //
	////////////////

	@Override public void htmlStyles() {
		htmlStylesSiteStateGenPage();
		super.htmlStyles();
	}

	public void htmlStylesSiteStateGenPage() {
	}

	///////////////
	// htmlStyle //
	///////////////

	@Override public void htmlStyle() {
		htmlStyleSiteStateGenPage();
		super.htmlStyle();
	}

	public void htmlStyleSiteStateGenPage() {
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestSiteStateGenPage() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof SiteStateGenPage) {
			SiteStateGenPage original = (SiteStateGenPage)o;
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
		if(!(o instanceof SiteStateGenPage))
			return false;
		SiteStateGenPage that = (SiteStateGenPage)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("SiteStateGenPage { ");
		sb.append(" }");
		return sb.toString();
	}
}
