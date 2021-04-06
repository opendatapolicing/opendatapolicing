package com.opendatapolicing.enus.html.part;

import com.opendatapolicing.enus.page.PageLayout;
import com.opendatapolicing.enus.config.SiteConfig;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.opendatapolicing.enus.context.SiteContextEnUS;
import com.opendatapolicing.enus.user.SiteUser;
import java.io.IOException;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import com.opendatapolicing.enus.search.SearchList;
import com.opendatapolicing.enus.wrap.Wrap;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.api.service.ServiceRequest;
import io.vertx.core.json.JsonArray;
import java.net.URLDecoder;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.Map;
import java.util.List;
import java.util.Optional;
import org.apache.solr.common.util.SimpleOrderedMap;
import java.util.stream.Collectors;
import java.util.Arrays;
import org.apache.solr.client.solrj.response.QueryResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.math.MathContext;
import org.apache.commons.collections.CollectionUtils;
import java.util.Objects;
import org.apache.solr.client.solrj.SolrQuery.SortClause;


/**
 * Translate: false
 **/
public class HtmlPartGenPage extends HtmlPartGenPageGen<PageLayout> {

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("");

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _listHtmlPart(Wrap<SearchList<HtmlPart>> c) {
	}

	protected void _htmlPart_(Wrap<HtmlPart> c) {
		if(listHtmlPart != null && listHtmlPart.size() == 1)
			c.o(listHtmlPart.get(0));
	}

	@Override protected void _pageH1(Wrap<String> c) {
			c.o("HTML parts");
	}

	@Override protected void _pageH2(Wrap<String> c) {
		c.o("");
	}

	@Override protected void _pageH3(Wrap<String> c) {
		c.o("");
	}

	@Override protected void _pageTitle(Wrap<String> c) {
		if(htmlPart_ != null && htmlPart_.getHtmlPartTitle() != null)
			c.o(htmlPart_.getHtmlPartTitle());
		else if(htmlPart_ != null)
			c.o("HTML parts");
		else if(listHtmlPart == null || listHtmlPart.size() == 0)
			c.o("no HTML part found");
		else
			c.o("HTML parts");
	}

	@Override protected void _pageUri(Wrap<String> c) {
		c.o("/html-part");
	}

	@Override protected void _pageImageUri(Wrap<String> c) {
			c.o("/png/html-part-999.png");
	}

	@Override protected void _contextIconGroup(Wrap<String> c) {
			c.o("regular");
	}

	@Override protected void _contextIconName(Wrap<String> c) {
			c.o("puzzle-piece");
	}

	@Override public void initDeepHtmlPartGenPage() {
		initHtmlPartGenPage();
		super.initDeepPageLayout();
	}

	@Override public void htmlScriptsHtmlPartGenPage() {
		e("script").a("src", staticBaseUrl, "/js/enUS/HtmlPartPage.js").f().g("script");
		e("script").a("src", staticBaseUrl, "/js/enUS/PageDesignPage.js").f().g("script");
	}

	@Override public void htmlScriptHtmlPartGenPage() {
		l("$(document).ready(function() {");
		tl(1, "document.onkeydown = function(evt) {");
		tl(2, "evt = evt || window.event;");
		tl(2, "var isEscape = false;");
		tl(2, "if ('key' in evt) {");
		tl(3, "isEscape = (evt.key === 'Escape' || evt.key === 'Esc');");
		tl(2, "} else {");
		tl(3, "isEscape = (evt.keyCode === 27);");
		tl(2, "}");
		tl(2, "if (isEscape) {");
		tl(3, "$('.w3-modal:visible').hide();");
		tl(2, "}");
		tl(1, "};");
		tl(1, "window.eventBus = new EventBus('/eventbus');");
		tl(1, "var pk = ", Optional.ofNullable(siteRequest_.getRequestPk()).map(l -> l.toString()).orElse("null"), ";");
		tl(1, "if(pk != null) {");
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			tl(2, "suggestHtmlPartPageDesignKeys([{'name':'fq','value':'htmlPartKeys:' + pk}], $('#listHtmlPartPageDesignKeys_Page'), pk, true); ");
		} else {
			tl(2, "suggestHtmlPartPageDesignKeys([{'name':'fq','value':'htmlPartKeys:' + pk}], $('#listHtmlPartPageDesignKeys_Page'), pk, false); ");
		}
		tl(1, "}");
		tl(1, "websocketHtmlPart(websocketHtmlPartInner);");
		l("});");
	}

	public void htmlFormPageHtmlPart(HtmlPart o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPk("Page");
			o.htmCreated("Page");
			o.htmModified("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPageDesignKeys("Page");
			o.htmHtmlLink("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlElement("Page");
			o.htmHtmlId("Page");
			o.htmHtmlClasses("Page");
			o.htmHtmlStyle("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlBefore("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlAfter("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlText("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlVar("Page");
			o.htmHtmlVarSpan("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlVarForm("Page");
			o.htmHtmlVarInput("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlVarForEach("Page");
			o.htmHtmlVarHtml("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlVarBase64Decode("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlExclude("Page");
			o.htmPdfExclude("Page");
			o.htmLoginLogout("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSearchUri("Page");
			o.htmMapTo("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSort1("Page");
			o.htmSort2("Page");
			o.htmSort3("Page");
			o.htmSort4("Page");
			o.htmSort5("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSort6("Page");
			o.htmSort7("Page");
			o.htmSort8("Page");
			o.htmSort9("Page");
			o.htmSort10("Page");
		} g("div");
	}

	public void htmlFormPOSTHtmlPart(HtmlPart o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPk("POST");
			o.htmCreated("POST");
			o.htmModified("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPageDesignKeys("POST");
			o.htmHtmlLink("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlElement("POST");
			o.htmHtmlId("POST");
			o.htmHtmlClasses("POST");
			o.htmHtmlStyle("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlBefore("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlAfter("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlText("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlVar("POST");
			o.htmHtmlVarSpan("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlVarForm("POST");
			o.htmHtmlVarInput("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlVarForEach("POST");
			o.htmHtmlVarHtml("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlVarBase64Decode("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlExclude("POST");
			o.htmPdfExclude("POST");
			o.htmLoginLogout("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSearchUri("POST");
			o.htmMapTo("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSort1("POST");
			o.htmSort2("POST");
			o.htmSort3("POST");
			o.htmSort4("POST");
			o.htmSort5("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSort6("POST");
			o.htmSort7("POST");
			o.htmSort8("POST");
			o.htmSort9("POST");
			o.htmSort10("POST");
		} g("div");
	}

	public void htmlFormPUTImportHtmlPart(HtmlPart o) {
		{ e("div").a("class", "w3-cell-row ").f();
			e("textarea")
				.a("class", "PUTImport_list w3-input w3-border ")
				.a("style", "height: 400px; ")
				.a("placeholder", "{ \"list\": [ { \"pk\": ... , \"saves\": [ ... ] }, ... ] }")
				;
				f();
			g("textarea");
		} g("div");
	}

	public void htmlFormPUTMergeHtmlPart(HtmlPart o) {
		{ e("div").a("class", "w3-cell-row ").f();
			e("textarea")
				.a("class", "PUTMerge_list w3-input w3-border ")
				.a("style", "height: 400px; ")
				.a("placeholder", "{ \"list\": [ { \"pk\": ... , \"saves\": [ ... ] }, ... ] }")
				;
				f();
			g("textarea");
		} g("div");
	}

	public void htmlFormPUTCopyHtmlPart(HtmlPart o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCreated("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPageDesignKeys("PUTCopy");
			o.htmHtmlLink("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlElement("PUTCopy");
			o.htmHtmlId("PUTCopy");
			o.htmHtmlClasses("PUTCopy");
			o.htmHtmlStyle("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlBefore("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlAfter("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlText("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlVar("PUTCopy");
			o.htmHtmlVarSpan("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlVarForm("PUTCopy");
			o.htmHtmlVarInput("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlVarForEach("PUTCopy");
			o.htmHtmlVarHtml("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlVarBase64Decode("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlExclude("PUTCopy");
			o.htmPdfExclude("PUTCopy");
			o.htmLoginLogout("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSearchUri("PUTCopy");
			o.htmMapTo("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSort1("PUTCopy");
			o.htmSort2("PUTCopy");
			o.htmSort3("PUTCopy");
			o.htmSort4("PUTCopy");
			o.htmSort5("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSort6("PUTCopy");
			o.htmSort7("PUTCopy");
			o.htmSort8("PUTCopy");
			o.htmSort9("PUTCopy");
			o.htmSort10("PUTCopy");
		} g("div");
	}

	public void htmlFormPATCHHtmlPart(HtmlPart o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCreated("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPageDesignKeys("PATCH");
			o.htmHtmlLink("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlElement("PATCH");
			o.htmHtmlId("PATCH");
			o.htmHtmlClasses("PATCH");
			o.htmHtmlStyle("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlBefore("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlAfter("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlText("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlVar("PATCH");
			o.htmHtmlVarSpan("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlVarForm("PATCH");
			o.htmHtmlVarInput("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlVarForEach("PATCH");
			o.htmHtmlVarHtml("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlVarBase64Decode("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlExclude("PATCH");
			o.htmPdfExclude("PATCH");
			o.htmLoginLogout("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSearchUri("PATCH");
			o.htmMapTo("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSort1("PATCH");
			o.htmSort2("PATCH");
			o.htmSort3("PATCH");
			o.htmSort4("PATCH");
			o.htmSort5("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSort6("PATCH");
			o.htmSort7("PATCH");
			o.htmSort8("PATCH");
			o.htmSort9("PATCH");
			o.htmSort10("PATCH");
		} g("div");
	}

	public void htmlFormSearchHtmlPart(HtmlPart o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPk("Search");
			o.htmCreated("Search");
			o.htmModified("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPageDesignKeys("Search");
			o.htmHtmlLink("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlElement("Search");
			o.htmHtmlId("Search");
			o.htmHtmlClasses("Search");
			o.htmHtmlStyle("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlVar("Search");
			o.htmHtmlVarSpan("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlVarForm("Search");
			o.htmHtmlVarInput("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlVarForEach("Search");
			o.htmHtmlVarHtml("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlVarBase64Decode("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmHtmlExclude("Search");
			o.htmPdfExclude("Search");
			o.htmLoginLogout("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSearchUri("Search");
			o.htmMapTo("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSort1("Search");
			o.htmSort2("Search");
			o.htmSort3("Search");
			o.htmSort4("Search");
			o.htmSort5("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSort6("Search");
			o.htmSort7("Search");
			o.htmSort8("Search");
			o.htmSort9("Search");
			o.htmSort10("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInheritPk("Search");
		} g("div");
	}

	@Override public void htmlBodyHtmlPartGenPage() {

		ServiceRequest serviceRequest = siteRequest_.getServiceRequest();
		JsonObject params = serviceRequest.getParams();
		if(listHtmlPart == null || listHtmlPart.size() == 0) {

			{ e("h1").f();
				{ e("a").a("href", "/html-part").a("class", "w3-bar-item w3-btn w3-center w3-block w3-khaki w3-hover-khaki ").f();
					if(contextIconCssClasses != null)
						e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx("HTML parts").g("span");
				} g("a");
			} g("h1");
			e("div").a("class", "w3-padding-16 w3-card-4 w3-light-grey ").f();
			{ e("h2").f();
				{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-khaki ").f();
					if(contextIconCssClasses != null)
						e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx("no HTML part found").g("span");
				} g("span");
			} g("h2");
		} else if(listHtmlPart != null && listHtmlPart.size() == 1 && params.getJsonObject("query").getString("q").equals("*:*")) {
			HtmlPart o = listHtmlPart.get(0);
			siteRequest_.setRequestPk(o.getPk());
			if(StringUtils.isNotEmpty(pageH1)) {
				{ e("h1").f();
					{ e("a").a("href", "/html-part").a("class", "w3-bar-item w3-btn w3-center w3-block w3-khaki w3-hover-khaki ").f();
						if(contextIconCssClasses != null)
							e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
						e("span").a("class", " ").f().sx(pageH1).g("span");
					} g("a");
				} g("h1");
			}
			e("div").a("class", "w3-padding-16 w3-card-4 w3-light-grey ").f();
			if(StringUtils.isNotEmpty(pageH2)) {
				{ e("h2").f();
					{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-khaki ").f();
						e("span").a("class", " ").f().sx(pageH2).g("span");
					} g("span");
				} g("h2");
			}
			if(StringUtils.isNotEmpty(pageH3)) {
				{ e("h3").f();
					{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-khaki ").f();
						e("span").a("class", " ").f().sx(pageH3).g("span");
					} g("span");
				} g("h3");
			}
		} else {

			{ e("h1").f();
				{ e("a").a("href", "/html-part").a("class", "w3-bar-item w3-btn w3-center w3-block w3-khaki w3-hover-khaki ").f();
					if(contextIconCssClasses != null)
						e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx(pageH1).g("span");
				} g("a");
			} g("h1");
			{ e("div").a("class", "").f();
				{ e("div").f();
					JsonObject queryParams = Optional.ofNullable(serviceRequest).map(ServiceRequest::getParams).map(or -> or.getJsonObject("query")).orElse(new JsonObject());
					Long num = listHtmlPart.getQueryResponse().getResults().getNumFound();
					String q = "*:*";
					String query1 = "";
					String query2 = "";
					String query = "*:*";
					for(String paramName : queryParams.fieldNames()) {
						String entityVar = null;
						String valueIndexed = null;
						Object paramObjectValues = queryParams.getValue(paramName);
						JsonArray paramObjects = paramObjectValues instanceof JsonArray ? (JsonArray)paramObjectValues : new JsonArray().add(paramObjectValues);

						try {
							for(Object paramObject : paramObjects) {
								switch(paramName) {
									case "q":
										q = (String)paramObject;
										entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
										valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
										query1 = entityVar.equals("*") ? query1 : entityVar;
										query2 = valueIndexed;
										query = query1 + ":" + query2;
								}
							}
						} catch(Exception e) {
							ExceptionUtils.rethrow(e);
						}
					}

					Integer rows1 = Optional.ofNullable(listHtmlPart).map(l -> l.getRows()).orElse(10);
					Integer start1 = Optional.ofNullable(listHtmlPart).map(l -> l.getStart()).orElse(1);
					Integer start2 = start1 - rows1;
					Integer start3 = start1 + rows1;
					Integer rows2 = rows1 / 2;
					Integer rows3 = rows1 * 2;
					start2 = start2 < 0 ? 0 : start2;
					StringBuilder fqs = new StringBuilder();
					for(String fq : Optional.ofNullable(listHtmlPart).map(l -> l.getFilterQueries()).orElse(new String[0])) {
						if(!StringUtils.contains(fq, "(")) {
							String fq1 = StringUtils.substringBefore(fq, "_");
							String fq2 = StringUtils.substringAfter(fq, ":");
							if(!StringUtils.startsWithAny(fq, "classCanonicalNames_", "archived_", "deleted_", "sessionId", "userKeys"))
								fqs.append("&fq=").append(fq1).append(":").append(fq2);
						}
					}
					StringBuilder sorts = new StringBuilder();
					for(SortClause sort : Optional.ofNullable(listHtmlPart).map(l -> l.getSorts()).orElse(Arrays.asList())) {
						sorts.append("&sort=").append(StringUtils.substringBefore(sort.getItem(), "_")).append(" ").append(sort.getOrder().name());
					}

					if(start1 == 0) {
						e("i").a("class", "fas fa-arrow-square-left w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/html-part?q=", query, fqs, sorts, "&start=", start2, "&rows=", rows1).f();
							e("i").a("class", "fas fa-arrow-square-left ").f().g("i");
						} g("a");
					}

					if(rows1 <= 1) {
						e("i").a("class", "fas fa-minus-square w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/html-part?q=", query, fqs, sorts, "&start=", start1, "&rows=", rows2).f();
							e("i").a("class", "fas fa-minus-square ").f().g("i");
						} g("a");
					}

					{ e("a").a("href", "/html-part?q=", query, fqs, sorts, "&start=", start1, "&rows=", rows3).f();
						e("i").a("class", "fas fa-plus-square ").f().g("i");
					} g("a");

					if(start3 >= num) {
						e("i").a("class", "fas fa-arrow-square-right w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/html-part?q=", query, fqs, sorts, "&start=", start3, "&rows=", rows1).f();
							e("i").a("class", "fas fa-arrow-square-right ").f().g("i");
						} g("a");
					}
						e("span").f().sx((start1 + 1), " - ", (start1 + rows1), " of ", num).g("span");
				} g("div");
				table1HtmlPartGenPage();
			} g("div");
		}

		if(listHtmlPart != null && listHtmlPart.size() == 1 && params.getJsonObject("query").getString("q").equals("*:*")) {
			HtmlPart o = listHtmlPart.first();

			{ e("div").a("class", "").f();

				if(o.getPk() != null) {
					{ e("form").a("action", "").a("id", "HtmlPartForm").a("style", "display: inline-block; width: 100%; ").a("onsubmit", "event.preventDefault(); return false; ").f();
						e("input")
						.a("name", "pk")
						.a("class", "valuePk")
						.a("type", "hidden")
						.a("value", o.getPk())
						.fg();
						e("input")
						.a("name", "focusId")
						.a("type", "hidden")
						.fg();
					} g("form");
					htmlFormPageHtmlPart(o);
				}

			} g("div");

		}
		htmlBodyFormsHtmlPartGenPage();
	}

	public void table1HtmlPartGenPage() {
		{ e("table").a("class", "w3-table w3-bordered w3-striped w3-border w3-hoverable ").f();
			table2HtmlPartGenPage();
		} g("table");
	}

	public void table2HtmlPartGenPage() {
		thead1HtmlPartGenPage();
		tbody1HtmlPartGenPage();
		tfoot1HtmlPartGenPage();
	}

	public void thead1HtmlPartGenPage() {
		{ e("thead").a("class", "w3-khaki w3-hover-khaki ").f();
			thead2HtmlPartGenPage();
		} g("thead");
	}

	public void thead2HtmlPartGenPage() {
			{ e("tr").f();
			if(getColumnCreated()) {
				e("th").f().sx("created").g("th");
			}
			} g("tr");
	}

	public void tbody1HtmlPartGenPage() {
		{ e("tbody").f();
			tbody2HtmlPartGenPage();
		} g("tbody");
	}

	public void tbody2HtmlPartGenPage() {
		Map<String, Map<String, List<String>>> highlighting = listHtmlPart.getQueryResponse().getHighlighting();
		for(int i = 0; i < listHtmlPart.size(); i++) {
			HtmlPart o = listHtmlPart.getList().get(i);
			Map<String, List<String>> highlights = highlighting == null ? null : highlighting.get(o.getId());
			List<String> highlightList = highlights == null ? null : highlights.get(highlights.keySet().stream().findFirst().orElse(null));
			String uri = "/html-part/" + o.getPk();
			{ e("tr").f();
				if(getColumnCreated()) {
					{ e("td").f();
						{ e("a").a("href", uri).f();
							{ e("span").f();
								sx(o.strCreated());
							} g("span");
						} g("a");
					} g("td");
				}
			} g("tr");
		}
	}

	public void tfoot1HtmlPartGenPage() {
		{ e("tfoot").a("class", "w3-khaki w3-hover-khaki ").f();
			tfoot2HtmlPartGenPage();
		} g("tfoot");
	}

	public void tfoot2HtmlPartGenPage() {
		{ e("tr").f();
			SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listHtmlPart.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(new SimpleOrderedMap());
			if(getColumnCreated()) {
				e("td").f();
				g("td");
			}
		} g("tr");
	}

	public Boolean getColumnCreated() {
		return true;
	}

	public void htmlBodyFormsHtmlPartGenPage() {
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {

			if(listHtmlPart != null && listHtmlPart.size() == 1) {
				{ e("button")
					.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-khaki ")
						.a("id", "refreshThisHtmlPartGenPage")
						.a("onclick", "patchHtmlPartVals( [ {name: 'fq', value: 'pk:' + " + siteRequest_.getRequestPk() + " } ], {}, function() { addGlow($('#refreshThisHtmlPartGenPage')); }, function() { addError($('#refreshThisHtmlPartGenPage')); }); return false; ").f();
						e("i").a("class", "fas fa-sync-alt ").f().g("i");
					sx("refresh this HTML part");
				} g("button");
			}
		}
		if(
				siteRequest_.getUserResourceRoles().contains("SiteAdmin")
				|| siteRequest_.getUserRealmRoles().contains("SiteAdmin")
				) {

			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-khaki ")
				.a("onclick", "$('#postHtmlPartModal').show(); ")
				.f();
				e("i").a("class", "fas fa-file-plus ").f().g("i");
				sx("Create an HTML part");
			} g("button");
			{ e("div").a("id", "postHtmlPartModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-khaki ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#postHtmlPartModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Create an HTML part").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").a("id", "postHtmlPartFormValues").f();
							HtmlPart o = new HtmlPart();
							o.setSiteRequest_(siteRequest_);

							// Form POST
							{ e("div").a("id", "postHtmlPartForm").f();
								htmlFormPOSTHtmlPart(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-khaki ")
								.a("onclick", "postHtmlPart($('#postHtmlPartForm')); ")
								.f().sx("Create an HTML part")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-khaki ")
				.a("onclick", "$('#putimportHtmlPartModal').show(); ")
				.f();
				e("i").a("class", "fas fa-file-import ").f().g("i");
				sx("Import HTML parts");
			} g("button");
			{ e("div").a("id", "putimportHtmlPartModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-khaki ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putimportHtmlPartModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Import HTML parts").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").a("id", "putimportHtmlPartFormValues").f();
							HtmlPart o = new HtmlPart();
							o.setSiteRequest_(siteRequest_);

							// Form PUT
							{ e("div").a("id", "putimportHtmlPartForm").f();
								htmlFormPUTImportHtmlPart(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-khaki ")
								.a("onclick", "putimportHtmlPart($('#putimportHtmlPartForm')); ")
								.f().sx("Import HTML parts")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-khaki ")
				.a("onclick", "$('#putmergeHtmlPartModal').show(); ")
				.f();
				e("i").a("class", "fas fa-code-merge ").f().g("i");
				sx("Merge HTML parts");
			} g("button");
			{ e("div").a("id", "putmergeHtmlPartModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-khaki ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putmergeHtmlPartModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Merge HTML parts").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").a("id", "putmergeHtmlPartFormValues").f();
							HtmlPart o = new HtmlPart();
							o.setSiteRequest_(siteRequest_);

							// Form PUT
							{ e("div").a("id", "putmergeHtmlPartForm").f();
								htmlFormPUTMergeHtmlPart(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-khaki ")
								.a("onclick", "putmergeHtmlPart($('#putmergeHtmlPartForm')); ")
								.f().sx("Merge HTML parts")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-khaki ")
				.a("onclick", "$('#putcopyHtmlPartModal').show(); ")
				.f();
				e("i").a("class", "fas fa-copy ").f().g("i");
				sx("Duplicate HTML parts");
			} g("button");
			{ e("div").a("id", "putcopyHtmlPartModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-khaki ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putcopyHtmlPartModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Duplicate HTML parts").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").a("id", "putcopyHtmlPartFormValues").f();
							HtmlPart o = new HtmlPart();
							o.setSiteRequest_(siteRequest_);

							// Form PUT
							{ e("div").a("id", "putcopyHtmlPartForm").f();
								htmlFormPUTCopyHtmlPart(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-khaki ")
								.a("onclick", "putcopyHtmlPart($('#putcopyHtmlPartForm'), ", htmlPart_ == null ? "null" : htmlPart_.getPk(), "); ")
								.f().sx("Duplicate HTML parts")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-khaki ")
				.a("onclick", "$('#patchHtmlPartModal').show(); ")
				.f();
				e("i").a("class", "fas fa-edit ").f().g("i");
				sx("Modify HTML parts");
			} g("button");
			{ e("div").a("id", "patchHtmlPartModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-khaki ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#patchHtmlPartModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Modify HTML parts").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").a("id", "patchHtmlPartFormValues").f();
							HtmlPart o = new HtmlPart();
							o.setSiteRequest_(siteRequest_);

							htmlFormPATCHHtmlPart(o);
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-khaki ")
								.a("onclick", "patchHtmlPart(null, $('#patchHtmlPartFormValues'), ", Optional.ofNullable(htmlPart_).map(HtmlPart::getPk).map(a -> a.toString()).orElse("null"), ", function() {}, function() {}); ")
								.f().sx("Modify HTML parts")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");

		}
		htmlSuggestedHtmlPartGenPage(this, null, listHtmlPart);
	}

	/**
	**/
	public static void htmlSuggestedHtmlPartGenPage(PageLayout p, String id, SearchList<HtmlPart> listHtmlPart) {
		SiteRequestEnUS siteRequest_ = p.getSiteRequest_();
		try {
			ServiceRequest serviceRequest = siteRequest_.getServiceRequest();
			JsonObject queryParams = Optional.ofNullable(serviceRequest).map(ServiceRequest::getParams).map(or -> or.getJsonObject("query")).orElse(new JsonObject());
			String q = "*:*";
			String query1 = "";
			String query2 = "";
			for(String paramName : queryParams.fieldNames()) {
				String entityVar = null;
				String valueIndexed = null;
				Object paramObjectValues = queryParams.getValue(paramName);
				JsonArray paramObjects = paramObjectValues instanceof JsonArray ? (JsonArray)paramObjectValues : new JsonArray().add(paramObjectValues);

				try {
					for(Object paramObject : paramObjects) {
						switch(paramName) {
							case "q":
								q = (String)paramObject;
								entityVar = StringUtils.trim(StringUtils.substringBefore((String)paramObject, ":"));
								valueIndexed = URLDecoder.decode(StringUtils.trim(StringUtils.substringAfter((String)paramObject, ":")), "UTF-8");
								query1 = entityVar.equals("*") ? query1 : entityVar;
								query2 = valueIndexed.equals("*") ? "" : valueIndexed;
						}
					}
				} catch(Exception e) {
					ExceptionUtils.rethrow(e);
				}
			}

			Integer rows1 = Optional.ofNullable(listHtmlPart).map(l -> l.getRows()).orElse(10);
			Integer start1 = Optional.ofNullable(listHtmlPart).map(l -> l.getStart()).orElse(1);
			Integer start2 = start1 - rows1;
			Integer start3 = start1 + rows1;
			Integer rows2 = rows1 / 2;
			Integer rows3 = rows1 * 2;
			start2 = start2 < 0 ? 0 : start2;
			StringBuilder fqs = new StringBuilder();
			for(String fq : Optional.ofNullable(listHtmlPart).map(l -> l.getFilterQueries()).orElse(new String[0])) {
				if(!StringUtils.contains(fq, "(")) {
					String fq1 = StringUtils.substringBefore(fq, "_");
					String fq2 = StringUtils.substringAfter(fq, ":");
					if(!StringUtils.startsWithAny(fq, "classCanonicalNames_", "archived_", "deleted_", "sessionId", "userKeys"))
						fqs.append("&fq=").append(fq1).append(":").append(fq2);
				}
			}
			StringBuilder sorts = new StringBuilder();
			for(SortClause sort : Optional.ofNullable(listHtmlPart).map(l -> l.getSorts()).orElse(Arrays.asList())) {
				sorts.append("&sort=").append(StringUtils.substringBefore(sort.getItem(), "_")).append(" ").append(sort.getOrder().name());
			}

			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), HtmlPartGenPage.ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), HtmlPartGenPage.ROLES)
					) {
					{ p.e("div").a("class", "").f();
						{ p.e("button").a("id", "refreshAllHtmlPartGenPage", id).a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-khaki ").a("onclick", "patchHtmlPartVals([], {}, function() { addGlow($('#refreshAllHtmlPartGenPage", id, "')); }, function() { addError($('#refreshAllHtmlPartGenPage", id, "')); }); ").f();
							p.e("i").a("class", "fas fa-sync-alt ").f().g("i");
							p.sx("refresh all the HTML parts");
						} p.g("button");
					} p.g("div");
			}
			{ p.e("div").a("class", "w3-cell-row ").f();
				{ p.e("div").a("class", "w3-cell ").f();
					{ p.e("span").f();
						p.sx("search HTML parts: ");
					} p.g("span");
				} p.g("div");
			} p.g("div");
			{ p.e("div").a("class", "w3-bar ").f();

				p.e("input")
					.a("type", "text")
					.a("class", "suggestHtmlPart w3-input w3-border w3-bar-item ")
					.a("name", "suggestHtmlPart")
					.a("id", "suggestHtmlPart", id)
					.a("autocomplete", "off")
					.a("oninput", "suggestHtmlPart( [ { 'name': 'q', 'value': ':' + $(this).val() }, { 'name': 'rows', 'value': '10' }, { 'name': 'fl', 'value': 'pk,htmlPartTitle' } ], $('#suggestListHtmlPart", id, "'), ", p.getSiteRequest_().getRequestPk(), "); ")
					.a("onkeyup", "if (event.keyCode === 13) { event.preventDefault(); window.location.href = '/html-part?q=", query1, ":' + encodeURIComponent(this.value) + '", fqs, sorts, "&start=", start2, "&rows=", rows1, "'; }"); 
				if(listHtmlPart != null)
					p.a("value", query2);
				p.fg();
				{ p.e("button")
					.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-khaki ")
					.a("onclick", "window.location.href = '/html-part?q=", query1, ":' + encodeURIComponent(this.previousElementSibling.value) + '", fqs, sorts, "&start=", start2, "&rows=", rows1, "'; ") 
					.f();
					p.e("i").a("class", "fas fa-search ").f().g("i");
				} p.g("button");

			} p.g("div");
			{ p.e("div").a("class", "w3-cell-row ").f();
				{ p.e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
					{ p.e("ul").a("class", "w3-ul w3-hoverable ").a("id", "suggestListHtmlPart", id).f();
					} p.g("ul");
				} p.g("div");
			} p.g("div");
			{ p.e("div").a("class", "").f();
				{ p.e("a").a("href", "/html-part").a("class", "").f();
					p.e("i").a("class", "far fa-puzzle-piece ").f().g("i");
					p.sx("see all the HTML parts");
				} p.g("a");
			} p.g("div");
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

}
