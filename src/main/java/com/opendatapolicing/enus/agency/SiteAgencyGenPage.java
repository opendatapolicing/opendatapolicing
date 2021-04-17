package com.opendatapolicing.enus.agency;

import com.opendatapolicing.enus.page.PageLayout;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
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
public class SiteAgencyGenPage extends SiteAgencyGenPageGen<PageLayout> {

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("");

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _listSiteAgency(Wrap<SearchList<SiteAgency>> c) {
	}

	protected void _siteAgency_(Wrap<SiteAgency> c) {
		if(listSiteAgency != null && listSiteAgency.size() == 1)
			c.o(listSiteAgency.get(0));
	}

	@Override protected void _pageH1(Wrap<String> c) {
			c.o("agencies");
	}

	@Override protected void _pageH2(Wrap<String> c) {
		if(siteAgency_ != null && siteAgency_.getAgencyCompleteName() != null)
			c.o(siteAgency_.getAgencyCompleteName());
	}

	@Override protected void _pageH3(Wrap<String> c) {
		c.o("");
	}

	@Override protected void _pageTitle(Wrap<String> c) {
		if(siteAgency_ != null && siteAgency_.getAgencyCompleteName() != null)
			c.o(siteAgency_.getAgencyCompleteName());
		else if(siteAgency_ != null)
			c.o("agencies");
		else if(listSiteAgency == null || listSiteAgency.size() == 0)
			c.o("no agency found");
		else
			c.o("agencies");
	}

	@Override protected void _pageUri(Wrap<String> c) {
		c.o("/agency");
	}

	@Override protected void _pageImageUri(Wrap<String> c) {
			c.o("/png/agency-999.png");
	}

	@Override protected void _contextIconGroup(Wrap<String> c) {
			c.o("regular");
	}

	@Override protected void _contextIconName(Wrap<String> c) {
			c.o("road");
	}

	@Override public void initDeepSiteAgencyGenPage() {
		initSiteAgencyGenPage();
		super.initDeepPageLayout();
	}

	@Override public void htmlScriptsSiteAgencyGenPage() {
		e("script").a("src", staticBaseUrl, "/js/enUS/SiteAgencyPage.js").f().g("script");
		e("script").a("src", staticBaseUrl, "/js/enUS/SiteStatePage.js").f().g("script");
	}

	@Override public void htmlScriptSiteAgencyGenPage() {
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
			tl(2, "suggestSiteAgencyStateKey([{'name':'fq','value':'agencyKeys:' + pk}], $('#listSiteAgencyStateKey_Page'), pk, true); ");
		} else {
			tl(2, "suggestSiteAgencyStateKey([{'name':'fq','value':'agencyKeys:' + pk}], $('#listSiteAgencyStateKey_Page'), pk, false); ");
		}
		tl(1, "}");
		tl(1, "websocketSiteAgency(websocketSiteAgencyInner);");
		l("});");
	}

	public void htmlFormPageSiteAgency(SiteAgency o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPk("Page");
			o.htmCreated("Page");
			o.htmModified("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmAgencyName("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmImageLeft("Page");
			o.htmImageTop("Page");
			o.htmImageCoords("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStateKey("Page");
		} g("div");
	}

	public void htmlFormPOSTSiteAgency(SiteAgency o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPk("POST");
			o.htmCreated("POST");
			o.htmModified("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmAgencyName("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmImageLeft("POST");
			o.htmImageTop("POST");
			o.htmImageCoords("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStateKey("POST");
		} g("div");
	}

	public void htmlFormPUTImportSiteAgency(SiteAgency o) {
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

	public void htmlFormPUTMergeSiteAgency(SiteAgency o) {
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

	public void htmlFormPUTCopySiteAgency(SiteAgency o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCreated("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmAgencyName("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmImageLeft("PUTCopy");
			o.htmImageTop("PUTCopy");
			o.htmImageCoords("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStateKey("PUTCopy");
		} g("div");
	}

	public void htmlFormPATCHSiteAgency(SiteAgency o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCreated("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmAgencyName("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmImageLeft("PATCH");
			o.htmImageTop("PATCH");
			o.htmImageCoords("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStateKey("PATCH");
		} g("div");
	}

	public void htmlFormSearchSiteAgency(SiteAgency o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPk("Search");
			o.htmCreated("Search");
			o.htmModified("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmAgencyName("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmImageLeft("Search");
			o.htmImageTop("Search");
			o.htmImageCoords("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStateKey("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInheritPk("Search");
			o.htmObjectTitle("Search");
		} g("div");
	}

	@Override public void htmlBodySiteAgencyGenPage() {

		ServiceRequest serviceRequest = siteRequest_.getServiceRequest();
		JsonObject params = serviceRequest.getParams();
		if(listSiteAgency == null || listSiteAgency.size() == 0) {

			{ e("h1").f();
				{ e("a").a("href", "/agency").a("class", "w3-bar-item w3-btn w3-center w3-block w3-pale-yellow w3-hover-pale-yellow ").f();
					if(contextIconCssClasses != null)
						e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx("agencies").g("span");
				} g("a");
			} g("h1");
			e("div").a("class", "w3-padding-16 w3-card-4 w3-light-grey ").f();
			{ e("h2").f();
				{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-pale-yellow ").f();
					if(contextIconCssClasses != null)
						e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx("no agency found").g("span");
				} g("span");
			} g("h2");
		} else if(listSiteAgency != null && listSiteAgency.size() == 1 && params.getJsonObject("query").getString("q").equals("*:*")) {
			SiteAgency o = listSiteAgency.get(0);
			siteRequest_.setRequestPk(o.getPk());
			if(StringUtils.isNotEmpty(pageH1)) {
				{ e("h1").f();
					{ e("a").a("href", "/agency").a("class", "w3-bar-item w3-btn w3-center w3-block w3-pale-yellow w3-hover-pale-yellow ").f();
						if(contextIconCssClasses != null)
							e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
						e("span").a("class", " ").f().sx(pageH1).g("span");
					} g("a");
				} g("h1");
			}
			e("div").a("class", "w3-padding-16 w3-card-4 w3-light-grey ").f();
			if(StringUtils.isNotEmpty(pageH2)) {
				{ e("h2").f();
					{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-pale-yellow ").f();
						e("span").a("class", " ").f().sx(pageH2).g("span");
					} g("span");
				} g("h2");
			}
			if(StringUtils.isNotEmpty(pageH3)) {
				{ e("h3").f();
					{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-pale-yellow ").f();
						e("span").a("class", " ").f().sx(pageH3).g("span");
					} g("span");
				} g("h3");
			}
		} else {

			{ e("h1").f();
				{ e("a").a("href", "/agency").a("class", "w3-bar-item w3-btn w3-center w3-block w3-pale-yellow w3-hover-pale-yellow ").f();
					if(contextIconCssClasses != null)
						e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx(pageH1).g("span");
				} g("a");
			} g("h1");
			{ e("div").a("class", "").f();
				{ e("div").f();
					JsonObject queryParams = Optional.ofNullable(serviceRequest).map(ServiceRequest::getParams).map(or -> or.getJsonObject("query")).orElse(new JsonObject());
					Long num = listSiteAgency.getQueryResponse().getResults().getNumFound();
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

					Integer rows1 = Optional.ofNullable(listSiteAgency).map(l -> l.getRows()).orElse(10);
					Integer start1 = Optional.ofNullable(listSiteAgency).map(l -> l.getStart()).orElse(1);
					Integer start2 = start1 - rows1;
					Integer start3 = start1 + rows1;
					Integer rows2 = rows1 / 2;
					Integer rows3 = rows1 * 2;
					start2 = start2 < 0 ? 0 : start2;
					StringBuilder fqs = new StringBuilder();
					for(String fq : Optional.ofNullable(listSiteAgency).map(l -> l.getFilterQueries()).orElse(new String[0])) {
						if(!StringUtils.contains(fq, "(")) {
							String fq1 = StringUtils.substringBefore(fq, "_");
							String fq2 = StringUtils.substringAfter(fq, ":");
							if(!StringUtils.startsWithAny(fq, "classCanonicalNames_", "archived_", "deleted_", "sessionId", "userKeys"))
								fqs.append("&fq=").append(fq1).append(":").append(fq2);
						}
					}
					StringBuilder sorts = new StringBuilder();
					for(SortClause sort : Optional.ofNullable(listSiteAgency).map(l -> l.getSorts()).orElse(Arrays.asList())) {
						sorts.append("&sort=").append(StringUtils.substringBefore(sort.getItem(), "_")).append(" ").append(sort.getOrder().name());
					}

					if(start1 == 0) {
						e("i").a("class", "fas fa-arrow-square-left w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/agency?q=", query, fqs, sorts, "&start=", start2, "&rows=", rows1).f();
							e("i").a("class", "fas fa-arrow-square-left ").f().g("i");
						} g("a");
					}

					if(rows1 <= 1) {
						e("i").a("class", "fas fa-minus-square w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/agency?q=", query, fqs, sorts, "&start=", start1, "&rows=", rows2).f();
							e("i").a("class", "fas fa-minus-square ").f().g("i");
						} g("a");
					}

					{ e("a").a("href", "/agency?q=", query, fqs, sorts, "&start=", start1, "&rows=", rows3).f();
						e("i").a("class", "fas fa-plus-square ").f().g("i");
					} g("a");

					if(start3 >= num) {
						e("i").a("class", "fas fa-arrow-square-right w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/agency?q=", query, fqs, sorts, "&start=", start3, "&rows=", rows1).f();
							e("i").a("class", "fas fa-arrow-square-right ").f().g("i");
						} g("a");
					}
						e("span").f().sx((start1 + 1), " - ", (start1 + rows1), " of ", num).g("span");
				} g("div");
				table1SiteAgencyGenPage();
			} g("div");
		}

		if(listSiteAgency != null && listSiteAgency.size() == 1 && params.getJsonObject("query").getString("q").equals("*:*")) {
			SiteAgency o = listSiteAgency.first();

			{ e("div").a("class", "").f();

				if(o.getPk() != null) {
					{ e("form").a("action", "").a("id", "SiteAgencyForm").a("style", "display: inline-block; width: 100%; ").a("onsubmit", "event.preventDefault(); return false; ").f();
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
					htmlFormPageSiteAgency(o);
				}

			} g("div");

		}
		htmlBodyFormsSiteAgencyGenPage();
	}

	public void table1SiteAgencyGenPage() {
		{ e("table").a("class", "w3-table w3-bordered w3-striped w3-border w3-hoverable ").f();
			table2SiteAgencyGenPage();
		} g("table");
	}

	public void table2SiteAgencyGenPage() {
		thead1SiteAgencyGenPage();
		tbody1SiteAgencyGenPage();
		tfoot1SiteAgencyGenPage();
	}

	public void thead1SiteAgencyGenPage() {
		{ e("thead").a("class", "w3-pale-yellow w3-hover-pale-yellow ").f();
			thead2SiteAgencyGenPage();
		} g("thead");
	}

	public void thead2SiteAgencyGenPage() {
			{ e("tr").f();
			if(getColumnCreated()) {
				e("th").f().sx("created").g("th");
			}
			if(getColumnObjectTitle()) {
				e("th").f().sx("").g("th");
			}
			} g("tr");
	}

	public void tbody1SiteAgencyGenPage() {
		{ e("tbody").f();
			tbody2SiteAgencyGenPage();
		} g("tbody");
	}

	public void tbody2SiteAgencyGenPage() {
		Map<String, Map<String, List<String>>> highlighting = listSiteAgency.getQueryResponse().getHighlighting();
		for(int i = 0; i < listSiteAgency.size(); i++) {
			SiteAgency o = listSiteAgency.getList().get(i);
			Map<String, List<String>> highlights = highlighting == null ? null : highlighting.get(o.getId());
			List<String> highlightList = highlights == null ? null : highlights.get(highlights.keySet().stream().findFirst().orElse(null));
			String uri = "/agency/" + o.getPk();
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
				if(getColumnObjectTitle()) {
					{ e("td").f();
						{ e("a").a("href", uri).f();
							e("i").a("class", "far fa-road ").f().g("i");
							{ e("span").f();
								sx(o.strObjectTitle());
							} g("span");
						} g("a");
					} g("td");
				}
			} g("tr");
		}
	}

	public void tfoot1SiteAgencyGenPage() {
		{ e("tfoot").a("class", "w3-pale-yellow w3-hover-pale-yellow ").f();
			tfoot2SiteAgencyGenPage();
		} g("tfoot");
	}

	public void tfoot2SiteAgencyGenPage() {
		{ e("tr").f();
			SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listSiteAgency.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(new SimpleOrderedMap());
			if(getColumnCreated()) {
				e("td").f();
				g("td");
			}
			if(getColumnObjectTitle()) {
				e("td").f();
				g("td");
			}
		} g("tr");
	}

	public Boolean getColumnCreated() {
		return true;
	}

	public Boolean getColumnObjectTitle() {
		return true;
	}

	public void htmlBodyFormsSiteAgencyGenPage() {
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {

			if(listSiteAgency != null && listSiteAgency.size() == 1) {
				{ e("button")
					.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-yellow ")
						.a("id", "refreshThisSiteAgencyGenPage")
						.a("onclick", "patchSiteAgencyVals( [ {name: 'fq', value: 'pk:' + " + siteRequest_.getRequestPk() + " } ], {}, function() { addGlow($('#refreshThisSiteAgencyGenPage')); }, function() { addError($('#refreshThisSiteAgencyGenPage')); }); return false; ").f();
						e("i").a("class", "fas fa-sync-alt ").f().g("i");
					sx("refresh this agency");
				} g("button");
			}
		}
		if(
				siteRequest_.getUserResourceRoles().contains("SiteAdmin")
				|| siteRequest_.getUserRealmRoles().contains("SiteAdmin")
				) {

			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-yellow ")
				.a("onclick", "$('#putimportSiteAgencyModal').show(); ")
				.f();
				e("i").a("class", "fas fa-file-import ").f().g("i");
				sx("Import agencies");
			} g("button");
			{ e("div").a("id", "putimportSiteAgencyModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-pale-yellow ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putimportSiteAgencyModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Import agencies").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").a("id", "putimportSiteAgencyFormValues").f();
							SiteAgency o = new SiteAgency();
							o.setSiteRequest_(siteRequest_);

							// Form PUT
							{ e("div").a("id", "putimportSiteAgencyForm").f();
								htmlFormPUTImportSiteAgency(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-pale-yellow ")
								.a("onclick", "putimportSiteAgency($('#putimportSiteAgencyForm')); ")
								.f().sx("Import agencies")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-yellow ")
				.a("onclick", "$('#putmergeSiteAgencyModal').show(); ")
				.f();
				e("i").a("class", "fas fa-code-merge ").f().g("i");
				sx("Merge agencies");
			} g("button");
			{ e("div").a("id", "putmergeSiteAgencyModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-pale-yellow ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putmergeSiteAgencyModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Merge agencies").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").a("id", "putmergeSiteAgencyFormValues").f();
							SiteAgency o = new SiteAgency();
							o.setSiteRequest_(siteRequest_);

							// Form PUT
							{ e("div").a("id", "putmergeSiteAgencyForm").f();
								htmlFormPUTMergeSiteAgency(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-pale-yellow ")
								.a("onclick", "putmergeSiteAgency($('#putmergeSiteAgencyForm')); ")
								.f().sx("Merge agencies")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-yellow ")
				.a("onclick", "$('#putcopySiteAgencyModal').show(); ")
				.f();
				e("i").a("class", "fas fa-copy ").f().g("i");
				sx("Duplicate agencies");
			} g("button");
			{ e("div").a("id", "putcopySiteAgencyModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-pale-yellow ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putcopySiteAgencyModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Duplicate agencies").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").a("id", "putcopySiteAgencyFormValues").f();
							SiteAgency o = new SiteAgency();
							o.setSiteRequest_(siteRequest_);

							// Form PUT
							{ e("div").a("id", "putcopySiteAgencyForm").f();
								htmlFormPUTCopySiteAgency(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-pale-yellow ")
								.a("onclick", "putcopySiteAgency($('#putcopySiteAgencyForm'), ", siteAgency_ == null ? "null" : siteAgency_.getPk(), "); ")
								.f().sx("Duplicate agencies")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-yellow ")
				.a("onclick", "$('#postSiteAgencyModal').show(); ")
				.f();
				e("i").a("class", "fas fa-file-plus ").f().g("i");
				sx("Create a agency");
			} g("button");
			{ e("div").a("id", "postSiteAgencyModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-pale-yellow ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#postSiteAgencyModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Create a agency").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").a("id", "postSiteAgencyFormValues").f();
							SiteAgency o = new SiteAgency();
							o.setSiteRequest_(siteRequest_);

							// Form POST
							{ e("div").a("id", "postSiteAgencyForm").f();
								htmlFormPOSTSiteAgency(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-pale-yellow ")
								.a("onclick", "postSiteAgency($('#postSiteAgencyForm')); ")
								.f().sx("Create a agency")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-yellow ")
				.a("onclick", "$('#patchSiteAgencyModal').show(); ")
				.f();
				e("i").a("class", "fas fa-edit ").f().g("i");
				sx("Modify agencies");
			} g("button");
			{ e("div").a("id", "patchSiteAgencyModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-pale-yellow ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#patchSiteAgencyModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Modify agencies").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").a("id", "patchSiteAgencyFormValues").f();
							SiteAgency o = new SiteAgency();
							o.setSiteRequest_(siteRequest_);

							htmlFormPATCHSiteAgency(o);
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-pale-yellow ")
								.a("onclick", "patchSiteAgency(null, $('#patchSiteAgencyFormValues'), ", Optional.ofNullable(siteAgency_).map(SiteAgency::getPk).map(a -> a.toString()).orElse("null"), ", function() {}, function() {}); ")
								.f().sx("Modify agencies")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");

		}
		htmlSuggestedSiteAgencyGenPage(this, null, listSiteAgency);
	}

	/**
	**/
	public static void htmlSuggestedSiteAgencyGenPage(PageLayout p, String id, SearchList<SiteAgency> listSiteAgency) {
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

			Integer rows1 = Optional.ofNullable(listSiteAgency).map(l -> l.getRows()).orElse(10);
			Integer start1 = Optional.ofNullable(listSiteAgency).map(l -> l.getStart()).orElse(1);
			Integer start2 = start1 - rows1;
			Integer start3 = start1 + rows1;
			Integer rows2 = rows1 / 2;
			Integer rows3 = rows1 * 2;
			start2 = start2 < 0 ? 0 : start2;
			StringBuilder fqs = new StringBuilder();
			for(String fq : Optional.ofNullable(listSiteAgency).map(l -> l.getFilterQueries()).orElse(new String[0])) {
				if(!StringUtils.contains(fq, "(")) {
					String fq1 = StringUtils.substringBefore(fq, "_");
					String fq2 = StringUtils.substringAfter(fq, ":");
					if(!StringUtils.startsWithAny(fq, "classCanonicalNames_", "archived_", "deleted_", "sessionId", "userKeys"))
						fqs.append("&fq=").append(fq1).append(":").append(fq2);
				}
			}
			StringBuilder sorts = new StringBuilder();
			for(SortClause sort : Optional.ofNullable(listSiteAgency).map(l -> l.getSorts()).orElse(Arrays.asList())) {
				sorts.append("&sort=").append(StringUtils.substringBefore(sort.getItem(), "_")).append(" ").append(sort.getOrder().name());
			}

			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), SiteAgencyGenPage.ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), SiteAgencyGenPage.ROLES)
					) {
					{ p.e("div").a("class", "").f();
						{ p.e("button").a("id", "refreshAllSiteAgencyGenPage", id).a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-yellow ").a("onclick", "patchSiteAgencyVals([], {}, function() { addGlow($('#refreshAllSiteAgencyGenPage", id, "')); }, function() { addError($('#refreshAllSiteAgencyGenPage", id, "')); }); ").f();
							p.e("i").a("class", "fas fa-sync-alt ").f().g("i");
							p.sx("refresh all the agencies");
						} p.g("button");
					} p.g("div");
			}
			{ p.e("div").a("class", "w3-cell-row ").f();
				{ p.e("div").a("class", "w3-cell ").f();
					{ p.e("span").f();
						p.sx("search agencies: ");
					} p.g("span");
				} p.g("div");
			} p.g("div");
			{ p.e("div").a("class", "w3-bar ").f();

				p.e("input")
					.a("type", "text")
					.a("class", "suggestSiteAgency w3-input w3-border w3-bar-item ")
					.a("name", "suggestSiteAgency")
					.a("id", "suggestSiteAgency", id)
					.a("autocomplete", "off")
					.a("oninput", "suggestSiteAgency( [ { 'name': 'q', 'value': ':' + $(this).val() }, { 'name': 'rows', 'value': '10' }, { 'name': 'fl', 'value': 'pk,agencyCompleteName' } ], $('#suggestListSiteAgency", id, "'), ", p.getSiteRequest_().getRequestPk(), "); ")
					.a("onkeyup", "if (event.keyCode === 13) { event.preventDefault(); window.location.href = '/agency?q=", query1, ":' + encodeURIComponent(this.value) + '", fqs, sorts, "&start=", start2, "&rows=", rows1, "'; }"); 
				if(listSiteAgency != null)
					p.a("value", query2);
				p.fg();
				{ p.e("button")
					.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pale-yellow ")
					.a("onclick", "window.location.href = '/agency?q=", query1, ":' + encodeURIComponent(this.previousElementSibling.value) + '", fqs, sorts, "&start=", start2, "&rows=", rows1, "'; ") 
					.f();
					p.e("i").a("class", "fas fa-search ").f().g("i");
				} p.g("button");

			} p.g("div");
			{ p.e("div").a("class", "w3-cell-row ").f();
				{ p.e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
					{ p.e("ul").a("class", "w3-ul w3-hoverable ").a("id", "suggestListSiteAgency", id).f();
					} p.g("ul");
				} p.g("div");
			} p.g("div");
			{ p.e("div").a("class", "").f();
				{ p.e("a").a("href", "/agency").a("class", "").f();
					p.e("i").a("class", "far fa-road ").f().g("i");
					p.sx("see all the agencies");
				} p.g("a");
			} p.g("div");
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

}
