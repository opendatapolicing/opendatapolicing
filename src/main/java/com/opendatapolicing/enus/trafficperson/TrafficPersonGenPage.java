package com.opendatapolicing.enus.trafficperson;

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
import io.vertx.ext.web.api.OperationRequest;
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
public class TrafficPersonGenPage extends TrafficPersonGenPageGen<PageLayout> {

	public static final List<String> ROLES = Arrays.asList();
	public static final List<String> ROLE_READS = Arrays.asList();

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _listTrafficPerson(Wrap<SearchList<TrafficPerson>> c) {
	}

	protected void _trafficPerson_(Wrap<TrafficPerson> c) {
		if(listTrafficPerson != null && listTrafficPerson.size() == 1)
			c.o(listTrafficPerson.get(0));
	}

	@Override protected void _pageH1(Wrap<String> c) {
			c.o("persons");
	}

	@Override protected void _pageH2(Wrap<String> c) {
		c.o("");
	}

	@Override protected void _pageH3(Wrap<String> c) {
		c.o("");
	}

	@Override protected void _pageTitle(Wrap<String> c) {
		if(trafficPerson_ != null)
			c.o("persons");
		else if(listTrafficPerson == null || listTrafficPerson.size() == 0)
			c.o("no person found");
		else
			c.o("persons");
	}

	@Override protected void _pageUri(Wrap<String> c) {
		c.o("/api/person");
	}

	@Override protected void _pageImageUri(Wrap<String> c) {
			c.o("/png/api/person-999.png");
	}

	@Override protected void _contextIconGroup(Wrap<String> c) {
			c.o("regular");
	}

	@Override protected void _contextIconName(Wrap<String> c) {
			c.o("newspaper");
	}

	@Override public void initDeepTrafficPersonGenPage() {
		initTrafficPersonGenPage();
		super.initDeepPageLayout();
	}

	@Override public void htmlScriptsTrafficPersonGenPage() {
		e("script").a("src", staticBaseUrl, "/js/enUS/TrafficPersonPage.js").f().g("script");
	}

	@Override public void htmlScriptTrafficPersonGenPage() {
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
		tl(1, "var pk = ", Optional.ofNullable(siteRequest_.getRequest()).map(l -> l.toString()).orElse("null"), ";");
		tl(1, "if(pk != null) {");
		tl(1, "}");
		tl(1, "websocketTrafficPerson(websocketTrafficPersonInner);");
		l("});");
	}

	public void htmlFormPageTrafficPerson(TrafficPerson o) {
	}

	public void htmlFormPOSTTrafficPerson(TrafficPerson o) {
	}

	public void htmlFormPUTImportTrafficPerson(TrafficPerson o) {
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

	public void htmlFormPUTMergeTrafficPerson(TrafficPerson o) {
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

	public void htmlFormPUTCopyTrafficPerson(TrafficPerson o) {
	}

	public void htmlFormPATCHTrafficPerson(TrafficPerson o) {
	}

	public void htmlFormSearchTrafficPerson(TrafficPerson o) {
	}

	@Override public void htmlBodyTrafficPersonGenPage() {

		OperationRequest operationRequest = siteRequest_.getOperationRequest();
		JsonObject params = operationRequest.getParams();
		if(listTrafficPerson == null || listTrafficPerson.size() == 0) {

			{ e("h1").f();
				{ e("a").a("href", "/api/person").a("class", "w3-bar-item w3-btn w3-center w3-block w3-pale-green w3-hover-pale-green ").f();
					if(contextIconCssClasses != null)
						e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx("persons").g("span");
				} g("a");
			} g("h1");
			e("div").a("class", "w3-padding-16 w3-card-4 w3-light-grey ").f();
			{ e("h2").f();
				{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-pale-green ").f();
					if(contextIconCssClasses != null)
						e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx("no person found").g("span");
				} g("span");
			} g("h2");
		} else if(listTrafficPerson != null && listTrafficPerson.size() == 1 && params.getJsonObject("query").getString("q").equals("*:*")) {
			TrafficPerson o = listTrafficPerson.get(0);
			siteRequest_.setRequest(o.get());
			if(StringUtils.isNotEmpty(pageH1)) {
				{ e("h1").f();
					{ e("a").a("href", "/api/person").a("class", "w3-bar-item w3-btn w3-center w3-block w3-pale-green w3-hover-pale-green ").f();
						if(contextIconCssClasses != null)
							e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
						e("span").a("class", " ").f().sx(pageH1).g("span");
					} g("a");
				} g("h1");
			}
			e("div").a("class", "w3-padding-16 w3-card-4 w3-light-grey ").f();
			if(StringUtils.isNotEmpty(pageH2)) {
				{ e("h2").f();
					{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-pale-green ").f();
						e("span").a("class", " ").f().sx(pageH2).g("span");
					} g("span");
				} g("h2");
			}
			if(StringUtils.isNotEmpty(pageH3)) {
				{ e("h3").f();
					{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-pale-green ").f();
						e("span").a("class", " ").f().sx(pageH3).g("span");
					} g("span");
				} g("h3");
			}
		} else {

			{ e("h1").f();
				{ e("a").a("href", "/api/person").a("class", "w3-bar-item w3-btn w3-center w3-block w3-pale-green w3-hover-pale-green ").f();
					if(contextIconCssClasses != null)
						e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx(pageH1).g("span");
				} g("a");
			} g("h1");
			e("div").a("class", "").f();
				{ e("div").f();
					JsonObject queryParams = Optional.ofNullable(operationRequest).map(OperationRequest::getParams).map(or -> or.getJsonObject("query")).orElse(new JsonObject());
					Long num = listTrafficPerson.getQueryResponse().getResults().getNumFound();
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

					Integer rows1 = Optional.ofNullable(listTrafficPerson).map(l -> l.getRows()).orElse(10);
					Integer start1 = Optional.ofNullable(listTrafficPerson).map(l -> l.getStart()).orElse(1);
					Integer start2 = start1 - rows1;
					Integer start3 = start1 + rows1;
					Integer rows2 = rows1 / 2;
					Integer rows3 = rows1 * 2;
					start2 = start2 < 0 ? 0 : start2;
					StringBuilder fqs = new StringBuilder();
					for(String fq : Optional.ofNullable(listTrafficPerson).map(l -> l.getFilterQueries()).orElse(new String[0])) {
						if(!StringUtils.contains(fq, "(")) {
							String fq1 = StringUtils.substringBefore(fq, "_");
							String fq2 = StringUtils.substringAfter(fq, ":");
							if(!StringUtils.startsWithAny(fq, "classCanonicalNames_", "archived_", "deleted_", "sessionId", "userKeys"))
								fqs.append("&fq=").append(fq1).append(":").append(fq2);
						}
					}
					StringBuilder sorts = new StringBuilder();
					for(SortClause sort : Optional.ofNullable(listTrafficPerson).map(l -> l.getSorts()).orElse(Arrays.asList())) {
						sorts.append("&sort=").append(StringUtils.substringBefore(sort.getItem(), "_")).append(" ").append(sort.getOrder().name());
					}

					if(start1 == 0) {
						e("i").a("class", "fas fa-arrow-square-left w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/api/person?q=", query, fqs, sorts, "&start=", start2, "&rows=", rows1).f();
							e("i").a("class", "fas fa-arrow-square-left ").f().g("i");
						} g("a");
					}

					if(rows1 <= 1) {
						e("i").a("class", "fas fa-minus-square w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/api/person?q=", query, fqs, sorts, "&start=", start1, "&rows=", rows2).f();
							e("i").a("class", "fas fa-minus-square ").f().g("i");
						} g("a");
					}

					{ e("a").a("href", "/api/person?q=", query, fqs, sorts, "&start=", start1, "&rows=", rows3).f();
						e("i").a("class", "fas fa-plus-square ").f().g("i");
					} g("a");

					if(start3 >= num) {
						e("i").a("class", "fas fa-arrow-square-right w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/api/person?q=", query, fqs, sorts, "&start=", start3, "&rows=", rows1).f();
							e("i").a("class", "fas fa-arrow-square-right ").f().g("i");
						} g("a");
					}
						e("span").f().sx((start1 + 1), " - ", (start1 + rows1), " of ", num).g("span");
				} g("div");
				table1TrafficPersonGenPage();
		}

		if(listTrafficPerson != null && listTrafficPerson.size() == 1 && params.getJsonObject("query").getString("q").equals("*:*")) {
			TrafficPerson o = listTrafficPerson.first();

			{ e("div").a("class", "").f();

			} g("div");

		}
		htmlBodyFormsTrafficPersonGenPage();
		g("div");
	}

	public void table1TrafficPersonGenPage() {
		{ e("table").a("class", "w3-table w3-bordered w3-striped w3-border w3-hoverable ").f();
			table2TrafficPersonGenPage();
		} g("table");
	}

	public void table2TrafficPersonGenPage() {
		thead1TrafficPersonGenPage();
		tbody1TrafficPersonGenPage();
		tfoot1TrafficPersonGenPage();
	}

	public void thead1TrafficPersonGenPage() {
		{ e("thead").a("class", "w3-pale-green w3-hover-pale-green ").f();
			thead2TrafficPersonGenPage();
		} g("thead");
	}

	public void thead2TrafficPersonGenPage() {
			{ e("tr").f();
			} g("tr");
	}

	public void tbody1TrafficPersonGenPage() {
		{ e("tbody").f();
			tbody2TrafficPersonGenPage();
		} g("tbody");
	}

	public void tbody2TrafficPersonGenPage() {
		Map<String, Map<String, List<String>>> highlighting = listTrafficPerson.getQueryResponse().getHighlighting();
		for(int i = 0; i < listTrafficPerson.size(); i++) {
			TrafficPerson o = listTrafficPerson.getList().get(i);
			Map<String, List<String>> highlights = highlighting == null ? null : highlighting.get(o.getId());
			List<String> highlightList = highlights == null ? null : highlights.get(highlights.keySet().stream().findFirst().orElse(null));
			String uri = "/api/person/" + o.getnull();
			{ e("tr").f();
			} g("tr");
		}
	}

	public void tfoot1TrafficPersonGenPage() {
		{ e("tfoot").a("class", "w3-pale-green w3-hover-pale-green ").f();
			tfoot2TrafficPersonGenPage();
		} g("tfoot");
	}

	public void tfoot2TrafficPersonGenPage() {
		{ e("tr").f();
			SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listTrafficPerson.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(new SimpleOrderedMap());
		} g("tr");
	}

	public void htmlBodyFormsTrafficPersonGenPage() {
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("div").a("class", "w3-margin-top ").f();

			if(listTrafficPerson != null && listTrafficPerson.size() == 1) {
				{ e("button")
					.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-green ")
						.a("id", "refreshThisTrafficPersonGenPage")
						.a("onclick", "patchTrafficPersonVals( [ {name: 'fq', value: ':' + " + siteRequest_.getRequest() + " } ], {}, function() { addGlow($('#refreshThisTrafficPersonGenPage')); }, function() { addError($('#refreshThisTrafficPersonGenPage')); }); return false; ").f();
						e("i").a("class", "fas fa-sync-alt ").f().g("i");
					sx("refresh this person");
				} g("button");
			}
		}
		if(
				siteRequest_.getUserResourceRoles().contains("SiteAdmin")
				|| siteRequest_.getUserRealmRoles().contains("SiteAdmin")
				) {

			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-green ")
				.a("onclick", "$('#postTrafficPersonModal').show(); ")
				.f();
				e("i").a("class", "fas fa-file-plus ").f().g("i");
				sx("Create a person");
			} g("button");
			{ e("div").a("id", "postTrafficPersonModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-pale-green ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#postTrafficPersonModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Create a person").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").a("id", "postTrafficPersonFormValues").f();
							TrafficPerson o = new TrafficPerson();
							o.setSiteRequest_(siteRequest_);

							// Form POST
							{ e("div").a("id", "postTrafficPersonForm").f();
								htmlFormPOSTTrafficPerson(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-pale-green ")
								.a("onclick", "postTrafficPerson($('#postTrafficPersonForm')); ")
								.f().sx("Create a person")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-green ")
				.a("onclick", "$('#patchTrafficPersonModal').show(); ")
				.f();
				e("i").a("class", "fas fa-edit ").f().g("i");
				sx("Modify persons");
			} g("button");
			{ e("div").a("id", "patchTrafficPersonModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-pale-green ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#patchTrafficPersonModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Modify persons").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").a("id", "patchTrafficPersonFormValues").f();
							TrafficPerson o = new TrafficPerson();
							o.setSiteRequest_(siteRequest_);

							htmlFormPATCHTrafficPerson(o);
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-pale-green ")
								.a("onclick", "patchTrafficPerson(null, $('#patchTrafficPersonFormValues'), ", Optional.ofNullable(trafficPerson_).map(TrafficPerson::get).map(a -> a.toString()).orElse("null"), ", function() {}, function() {}); ")
								.f().sx("Modify persons")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");

			g("div");
		}
		htmlSuggestedTrafficPersonGenPage(this, null, listTrafficPerson);
	}

	/**
	**/
	public static void htmlSuggestedTrafficPersonGenPage(PageLayout p, String id, SearchList<TrafficPerson> listTrafficPerson) {
		SiteRequestEnUS siteRequest_ = p.getSiteRequest_();
		try {
			OperationRequest operationRequest = siteRequest_.getOperationRequest();
			JsonObject queryParams = Optional.ofNullable(operationRequest).map(OperationRequest::getParams).map(or -> or.getJsonObject("query")).orElse(new JsonObject());
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

			Integer rows1 = Optional.ofNullable(listTrafficPerson).map(l -> l.getRows()).orElse(10);
			Integer start1 = Optional.ofNullable(listTrafficPerson).map(l -> l.getStart()).orElse(1);
			Integer start2 = start1 - rows1;
			Integer start3 = start1 + rows1;
			Integer rows2 = rows1 / 2;
			Integer rows3 = rows1 * 2;
			start2 = start2 < 0 ? 0 : start2;
			StringBuilder fqs = new StringBuilder();
			for(String fq : Optional.ofNullable(listTrafficPerson).map(l -> l.getFilterQueries()).orElse(new String[0])) {
				if(!StringUtils.contains(fq, "(")) {
					String fq1 = StringUtils.substringBefore(fq, "_");
					String fq2 = StringUtils.substringAfter(fq, ":");
					if(!StringUtils.startsWithAny(fq, "classCanonicalNames_", "archived_", "deleted_", "sessionId", "userKeys"))
						fqs.append("&fq=").append(fq1).append(":").append(fq2);
				}
			}
			StringBuilder sorts = new StringBuilder();
			for(SortClause sort : Optional.ofNullable(listTrafficPerson).map(l -> l.getSorts()).orElse(Arrays.asList())) {
				sorts.append("&sort=").append(StringUtils.substringBefore(sort.getItem(), "_")).append(" ").append(sort.getOrder().name());
			}

			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), TrafficPersonGenPage.ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), TrafficPersonGenPage.ROLES)
					) {
					{ p.e("div").a("class", "").f();
						{ p.e("button").a("id", "refreshAllTrafficPersonGenPage", id).a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-green ").a("onclick", "patchTrafficPersonVals([], {}, function() { addGlow($('#refreshAllTrafficPersonGenPage", id, "')); }, function() { addError($('#refreshAllTrafficPersonGenPage", id, "')); }); ").f();
							p.e("i").a("class", "fas fa-sync-alt ").f().g("i");
							p.sx("refresh all the persons");
						} p.g("button");
					} p.g("div");
			}
			{ p.e("div").a("class", "w3-cell-row ").f();
				{ p.e("div").a("class", "w3-cell ").f();
					{ p.e("span").f();
						p.sx("search persons: ");
					} p.g("span");
				} p.g("div");
			} p.g("div");
			{ p.e("div").a("class", "w3-bar ").f();

				p.e("input")
					.a("type", "text")
					.a("class", "suggestTrafficPerson w3-input w3-border w3-bar-item ")
					.a("name", "suggestTrafficPerson")
					.a("id", "suggestTrafficPerson", id)
					.a("autocomplete", "off")
					.a("oninput", "suggestTrafficPerson( [ { 'name': 'q', 'value': ':' + $(this).val() }, { 'name': 'rows', 'value': '10' }, { 'name': 'fl', 'value': '' } ], $('#suggestListTrafficPerson", id, "'), ", p.getSiteRequest_().getRequest(), "); ")
					.a("onkeyup", "if (event.keyCode === 13) { event.preventDefault(); window.location.href = '/api/person?q=", query1, ":' + encodeURIComponent(this.value) + '", fqs, sorts, "&start=", start2, "&rows=", rows1, "'; }"); 
				if(listTrafficPerson != null)
					p.a("value", query2);
				p.fg();
				{ p.e("button")
					.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pale-green ")
					.a("onclick", "window.location.href = '/api/person?q=", query1, ":' + encodeURIComponent(this.previousElementSibling.value) + '", fqs, sorts, "&start=", start2, "&rows=", rows1, "'; ") 
					.f();
					p.e("i").a("class", "fas fa-search ").f().g("i");
				} p.g("button");

			} p.g("div");
			{ p.e("div").a("class", "w3-cell-row ").f();
				{ p.e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
					{ p.e("ul").a("class", "w3-ul w3-hoverable ").a("id", "suggestListTrafficPerson", id).f();
					} p.g("ul");
				} p.g("div");
			} p.g("div");
			{ p.e("div").a("class", "").f();
				{ p.e("a").a("href", "/api/person").a("class", "").f();
					p.e("i").a("class", "far fa-newspaper ").f().g("i");
					p.sx("see all the persons");
				} p.g("a");
			} p.g("div");
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

}
