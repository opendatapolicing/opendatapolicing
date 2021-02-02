package com.opendatapolicing.enus.trafficstop;

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
public class TrafficStopGenPage extends TrafficStopGenPageGen<PageLayout> {

	public static final List<String> ROLES = Arrays.asList("SiteService");
	public static final List<String> ROLE_READS = Arrays.asList("");

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _listTrafficStop(Wrap<SearchList<TrafficStop>> c) {
	}

	protected void _trafficStop_(Wrap<TrafficStop> c) {
		if(listTrafficStop != null && listTrafficStop.size() == 1)
			c.o(listTrafficStop.get(0));
	}

	@Override protected void _pageH1(Wrap<String> c) {
			c.o("traffic stops");
	}

	@Override protected void _pageH2(Wrap<String> c) {
		if(trafficStop_ != null && trafficStop_.getTrafficStopCompleteName() != null)
			c.o(trafficStop_.getTrafficStopCompleteName());
	}

	@Override protected void _pageH3(Wrap<String> c) {
		c.o("");
	}

	@Override protected void _pageTitle(Wrap<String> c) {
		if(trafficStop_ != null && trafficStop_.getTrafficStopCompleteName() != null)
			c.o(trafficStop_.getTrafficStopCompleteName());
		else if(trafficStop_ != null)
			c.o("traffic stops");
		else if(listTrafficStop == null || listTrafficStop.size() == 0)
			c.o("no traffic stop found");
		else
			c.o("traffic stops");
	}

	@Override protected void _pageUri(Wrap<String> c) {
		c.o("/traffic-stop");
	}

	@Override protected void _pageImageUri(Wrap<String> c) {
			c.o("/png/traffic-stop-999.png");
	}

	@Override protected void _contextIconGroup(Wrap<String> c) {
			c.o("regular");
	}

	@Override protected void _contextIconName(Wrap<String> c) {
			c.o("newspaper");
	}

	@Override public void initDeepTrafficStopGenPage() {
		initTrafficStopGenPage();
		super.initDeepPageLayout();
	}

	@Override public void htmlScriptsTrafficStopGenPage() {
		e("script").a("src", staticBaseUrl, "/js/enUS/TrafficStopPage.js").f().g("script");
	}

	@Override public void htmlScriptTrafficStopGenPage() {
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
		tl(1, "}");
		tl(1, "websocketTrafficStop(websocketTrafficStopInner);");
		l("});");
	}

	public void htmlFormPageTrafficStop(TrafficStop o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPk("Page");
			o.htmCreated("Page");
			o.htmModified("Page");
			o.htmObjectId("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchived("Page");
			o.htmDeleted("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStopAgencyTitle("Page");
			o.htmStopDateTime("Page");
			o.htmStopPurposeTitle("Page");
			o.htmStopActionTitle("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStopDriverArrest("Page");
			o.htmStopPassengerArrest("Page");
			o.htmStopEncounterForce("Page");
			o.htmStopEngageForce("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStopOfficerInjury("Page");
			o.htmStopDriverInjury("Page");
			o.htmStopPassengerInjury("Page");
			o.htmStopOfficerId("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStopLocationId("Page");
			o.htmStopCityId("Page");
			o.htmPersonKeys("Page");
		} g("div");
	}

	public void htmlFormPOSTTrafficStop(TrafficStop o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPk("POST");
			o.htmCreated("POST");
			o.htmModified("POST");
			o.htmObjectId("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchived("POST");
			o.htmDeleted("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStopAgencyTitle("POST");
			o.htmStopDateTime("POST");
			o.htmStopPurposeTitle("POST");
			o.htmStopActionTitle("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStopDriverArrest("POST");
			o.htmStopPassengerArrest("POST");
			o.htmStopEncounterForce("POST");
			o.htmStopEngageForce("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStopOfficerInjury("POST");
			o.htmStopDriverInjury("POST");
			o.htmStopPassengerInjury("POST");
			o.htmStopOfficerId("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStopLocationId("POST");
			o.htmStopCityId("POST");
			o.htmPersonKeys("POST");
		} g("div");
	}

	public void htmlFormPUTImportTrafficStop(TrafficStop o) {
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

	public void htmlFormPUTMergeTrafficStop(TrafficStop o) {
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

	public void htmlFormPUTCopyTrafficStop(TrafficStop o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCreated("PUTCopy");
			o.htmModified("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchived("PUTCopy");
			o.htmDeleted("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStopAgencyTitle("PUTCopy");
			o.htmStopDateTime("PUTCopy");
			o.htmStopPurposeTitle("PUTCopy");
			o.htmStopActionTitle("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStopDriverArrest("PUTCopy");
			o.htmStopPassengerArrest("PUTCopy");
			o.htmStopEncounterForce("PUTCopy");
			o.htmStopEngageForce("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStopOfficerInjury("PUTCopy");
			o.htmStopDriverInjury("PUTCopy");
			o.htmStopPassengerInjury("PUTCopy");
			o.htmStopOfficerId("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStopLocationId("PUTCopy");
			o.htmStopCityId("PUTCopy");
		} g("div");
	}

	public void htmlFormPATCHTrafficStop(TrafficStop o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCreated("PATCH");
			o.htmModified("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchived("PATCH");
			o.htmDeleted("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStopAgencyTitle("PATCH");
			o.htmStopDateTime("PATCH");
			o.htmStopPurposeTitle("PATCH");
			o.htmStopActionTitle("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStopDriverArrest("PATCH");
			o.htmStopPassengerArrest("PATCH");
			o.htmStopEncounterForce("PATCH");
			o.htmStopEngageForce("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStopOfficerInjury("PATCH");
			o.htmStopDriverInjury("PATCH");
			o.htmStopPassengerInjury("PATCH");
			o.htmStopOfficerId("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStopLocationId("PATCH");
			o.htmStopCityId("PATCH");
		} g("div");
	}

	public void htmlFormSearchTrafficStop(TrafficStop o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPk("Search");
			o.htmCreated("Search");
			o.htmModified("Search");
			o.htmObjectId("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchived("Search");
			o.htmDeleted("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStopAgencyTitle("Search");
			o.htmStopDateTime("Search");
			o.htmStopPurposeTitle("Search");
			o.htmStopActionTitle("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStopDriverArrest("Search");
			o.htmStopPassengerArrest("Search");
			o.htmStopEncounterForce("Search");
			o.htmStopEngageForce("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStopOfficerInjury("Search");
			o.htmStopDriverInjury("Search");
			o.htmStopPassengerInjury("Search");
			o.htmStopOfficerId("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmStopLocationId("Search");
			o.htmStopCityId("Search");
			o.htmPersonKeys("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInheritPk("Search");
			o.htmUserId("Search");
			o.htmUserKey("Search");
			o.htmObjectTitle("Search");
			o.htmStopPurposeNum("Search");
			o.htmStopActionNum("Search");
		} g("div");
	}

	@Override public void htmlBodyTrafficStopGenPage() {

		OperationRequest operationRequest = siteRequest_.getOperationRequest();
		JsonObject params = operationRequest.getParams();
		if(listTrafficStop == null || listTrafficStop.size() == 0) {

			{ e("h1").f();
				{ e("a").a("href", "/traffic-stop").a("class", "w3-bar-item w3-btn w3-center w3-block w3-pale-green w3-hover-pale-green ").f();
					if(contextIconCssClasses != null)
						e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx("traffic stops").g("span");
				} g("a");
			} g("h1");
			e("div").a("class", "w3-padding-16 w3-card-4 w3-light-grey ").f();
			{ e("h2").f();
				{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-pale-green ").f();
					if(contextIconCssClasses != null)
						e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx("no traffic stop found").g("span");
				} g("span");
			} g("h2");
		} else if(listTrafficStop != null && listTrafficStop.size() == 1 && params.getJsonObject("query").getString("q").equals("*:*")) {
			TrafficStop o = listTrafficStop.get(0);
			siteRequest_.setRequestPk(o.getPk());
			if(StringUtils.isNotEmpty(pageH1)) {
				{ e("h1").f();
					{ e("a").a("href", "/traffic-stop").a("class", "w3-bar-item w3-btn w3-center w3-block w3-pale-green w3-hover-pale-green ").f();
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
				{ e("a").a("href", "/traffic-stop").a("class", "w3-bar-item w3-btn w3-center w3-block w3-pale-green w3-hover-pale-green ").f();
					if(contextIconCssClasses != null)
						e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx(pageH1).g("span");
				} g("a");
			} g("h1");
			{ e("div").a("class", "").f();
				{ e("div").f();
					JsonObject queryParams = Optional.ofNullable(operationRequest).map(OperationRequest::getParams).map(or -> or.getJsonObject("query")).orElse(new JsonObject());
					Long num = listTrafficStop.getQueryResponse().getResults().getNumFound();
					String q = "*:*";
					String query1 = "objectText";
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

					Integer rows1 = Optional.ofNullable(listTrafficStop).map(l -> l.getRows()).orElse(10);
					Integer start1 = Optional.ofNullable(listTrafficStop).map(l -> l.getStart()).orElse(1);
					Integer start2 = start1 - rows1;
					Integer start3 = start1 + rows1;
					Integer rows2 = rows1 / 2;
					Integer rows3 = rows1 * 2;
					start2 = start2 < 0 ? 0 : start2;
					StringBuilder fqs = new StringBuilder();
					for(String fq : Optional.ofNullable(listTrafficStop).map(l -> l.getFilterQueries()).orElse(new String[0])) {
						if(!StringUtils.contains(fq, "(")) {
							String fq1 = StringUtils.substringBefore(fq, "_");
							String fq2 = StringUtils.substringAfter(fq, ":");
							if(!StringUtils.startsWithAny(fq, "classCanonicalNames_", "archived_", "deleted_", "sessionId", "userKeys"))
								fqs.append("&fq=").append(fq1).append(":").append(fq2);
						}
					}
					StringBuilder sorts = new StringBuilder();
					for(SortClause sort : Optional.ofNullable(listTrafficStop).map(l -> l.getSorts()).orElse(Arrays.asList())) {
						sorts.append("&sort=").append(StringUtils.substringBefore(sort.getItem(), "_")).append(" ").append(sort.getOrder().name());
					}

					if(start1 == 0) {
						e("i").a("class", "fas fa-arrow-square-left w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/traffic-stop?q=", query, fqs, sorts, "&start=", start2, "&rows=", rows1).f();
							e("i").a("class", "fas fa-arrow-square-left ").f().g("i");
						} g("a");
					}

					if(rows1 <= 1) {
						e("i").a("class", "fas fa-minus-square w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/traffic-stop?q=", query, fqs, sorts, "&start=", start1, "&rows=", rows2).f();
							e("i").a("class", "fas fa-minus-square ").f().g("i");
						} g("a");
					}

					{ e("a").a("href", "/traffic-stop?q=", query, fqs, sorts, "&start=", start1, "&rows=", rows3).f();
						e("i").a("class", "fas fa-plus-square ").f().g("i");
					} g("a");

					if(start3 >= num) {
						e("i").a("class", "fas fa-arrow-square-right w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/traffic-stop?q=", query, fqs, sorts, "&start=", start3, "&rows=", rows1).f();
							e("i").a("class", "fas fa-arrow-square-right ").f().g("i");
						} g("a");
					}
						e("span").f().sx((start1 + 1), " - ", (start1 + rows1), " of ", num).g("span");
				} g("div");
				table1TrafficStopGenPage();
			} g("div");
		}

		if(listTrafficStop != null && listTrafficStop.size() == 1 && params.getJsonObject("query").getString("q").equals("*:*")) {
			TrafficStop o = listTrafficStop.first();

			{ e("div").a("class", "").f();

				if(o.getPk() != null) {
					{ e("form").a("action", "").a("id", "TrafficStopForm").a("style", "display: inline-block; width: 100%; ").a("onsubmit", "event.preventDefault(); return false; ").f();
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
					htmlFormPageTrafficStop(o);
				}

			} g("div");

		}
		htmlBodyFormsTrafficStopGenPage();
	}

	public void table1TrafficStopGenPage() {
		{ e("table").a("class", "w3-table w3-bordered w3-striped w3-border w3-hoverable ").f();
			table2TrafficStopGenPage();
		} g("table");
	}

	public void table2TrafficStopGenPage() {
		thead1TrafficStopGenPage();
		tbody1TrafficStopGenPage();
		tfoot1TrafficStopGenPage();
	}

	public void thead1TrafficStopGenPage() {
		{ e("thead").a("class", "w3-pale-green w3-hover-pale-green ").f();
			thead2TrafficStopGenPage();
		} g("thead");
	}

	public void thead2TrafficStopGenPage() {
			{ e("tr").f();
			if(getColumnCreated()) {
				e("th").f().sx("created").g("th");
			}
			if(getColumnObjectTitle()) {
				e("th").f().sx("").g("th");
			}
			} g("tr");
	}

	public void tbody1TrafficStopGenPage() {
		{ e("tbody").f();
			tbody2TrafficStopGenPage();
		} g("tbody");
	}

	public void tbody2TrafficStopGenPage() {
		Map<String, Map<String, List<String>>> highlighting = listTrafficStop.getQueryResponse().getHighlighting();
		for(int i = 0; i < listTrafficStop.size(); i++) {
			TrafficStop o = listTrafficStop.getList().get(i);
			Map<String, List<String>> highlights = highlighting == null ? null : highlighting.get(o.getId());
			List<String> highlightList = highlights == null ? null : highlights.get(highlights.keySet().stream().findFirst().orElse(null));
			String uri = "/traffic-stop/" + o.getPk();
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
							e("i").a("class", "far fa-newspaper ").f().g("i");
							{ e("span").f();
								sx(o.strObjectTitle());
							} g("span");
						} g("a");
					} g("td");
				}
			} g("tr");
		}
	}

	public void tfoot1TrafficStopGenPage() {
		{ e("tfoot").a("class", "w3-pale-green w3-hover-pale-green ").f();
			tfoot2TrafficStopGenPage();
		} g("tfoot");
	}

	public void tfoot2TrafficStopGenPage() {
		{ e("tr").f();
			SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listTrafficStop.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(new SimpleOrderedMap());
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

	public void htmlBodyFormsTrafficStopGenPage() {
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("div").a("class", "w3-margin-top ").f();

			if(listTrafficStop != null && listTrafficStop.size() == 1) {
				{ e("button")
					.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-green ")
						.a("id", "refreshThisTrafficStopGenPage")
						.a("onclick", "patchTrafficStopVals( [ {name: 'fq', value: 'pk:' + " + siteRequest_.getRequestPk() + " } ], {}, function() { addGlow($('#refreshThisTrafficStopGenPage')); }, function() { addError($('#refreshThisTrafficStopGenPage')); }); return false; ").f();
						e("i").a("class", "fas fa-sync-alt ").f().g("i");
					sx("refresh this traffic stop");
				} g("button");
			}
		}
		if(
				siteRequest_.getUserResourceRoles().contains("SiteAdmin")
				|| siteRequest_.getUserRealmRoles().contains("SiteAdmin")
				) {

			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-green ")
				.a("onclick", "$('#putimportTrafficStopModal').show(); ")
				.f();
				e("i").a("class", "fas fa-file-import ").f().g("i");
				sx("Import traffic stops");
			} g("button");
			{ e("div").a("id", "putimportTrafficStopModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-pale-green ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putimportTrafficStopModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Import traffic stops").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").a("id", "putimportTrafficStopFormValues").f();
							TrafficStop o = new TrafficStop();
							o.setSiteRequest_(siteRequest_);

							// Form PUT
							{ e("div").a("id", "putimportTrafficStopForm").f();
								htmlFormPUTImportTrafficStop(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-pale-green ")
								.a("onclick", "putimportTrafficStop($('#putimportTrafficStopForm')); ")
								.f().sx("Import traffic stops")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-green ")
				.a("onclick", "$('#putmergeTrafficStopModal').show(); ")
				.f();
				e("i").a("class", "fas fa-code-merge ").f().g("i");
				sx("Merge traffic stops");
			} g("button");
			{ e("div").a("id", "putmergeTrafficStopModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-pale-green ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putmergeTrafficStopModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Merge traffic stops").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").a("id", "putmergeTrafficStopFormValues").f();
							TrafficStop o = new TrafficStop();
							o.setSiteRequest_(siteRequest_);

							// Form PUT
							{ e("div").a("id", "putmergeTrafficStopForm").f();
								htmlFormPUTMergeTrafficStop(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-pale-green ")
								.a("onclick", "putmergeTrafficStop($('#putmergeTrafficStopForm')); ")
								.f().sx("Merge traffic stops")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-green ")
				.a("onclick", "$('#putcopyTrafficStopModal').show(); ")
				.f();
				e("i").a("class", "fas fa-copy ").f().g("i");
				sx("Duplicate traffic stops");
			} g("button");
			{ e("div").a("id", "putcopyTrafficStopModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-pale-green ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putcopyTrafficStopModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Duplicate traffic stops").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").a("id", "putcopyTrafficStopFormValues").f();
							TrafficStop o = new TrafficStop();
							o.setSiteRequest_(siteRequest_);

							// Form PUT
							{ e("div").a("id", "putcopyTrafficStopForm").f();
								htmlFormPUTCopyTrafficStop(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-pale-green ")
								.a("onclick", "putcopyTrafficStop($('#putcopyTrafficStopForm'), ", trafficStop_ == null ? "null" : trafficStop_.getPk(), "); ")
								.f().sx("Duplicate traffic stops")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-green ")
				.a("onclick", "$('#postTrafficStopModal').show(); ")
				.f();
				e("i").a("class", "fas fa-file-plus ").f().g("i");
				sx("Create a traffic stop");
			} g("button");
			{ e("div").a("id", "postTrafficStopModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-pale-green ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#postTrafficStopModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Create a traffic stop").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").a("id", "postTrafficStopFormValues").f();
							TrafficStop o = new TrafficStop();
							o.setSiteRequest_(siteRequest_);

							// Form POST
							{ e("div").a("id", "postTrafficStopForm").f();
								htmlFormPOSTTrafficStop(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-pale-green ")
								.a("onclick", "postTrafficStop($('#postTrafficStopForm')); ")
								.f().sx("Create a traffic stop")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-green ")
				.a("onclick", "$('#patchTrafficStopModal').show(); ")
				.f();
				e("i").a("class", "fas fa-edit ").f().g("i");
				sx("Modify traffic stops");
			} g("button");
			{ e("div").a("id", "patchTrafficStopModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-pale-green ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#patchTrafficStopModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Modify traffic stops").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").a("id", "patchTrafficStopFormValues").f();
							TrafficStop o = new TrafficStop();
							o.setSiteRequest_(siteRequest_);

							htmlFormPATCHTrafficStop(o);
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-pale-green ")
								.a("onclick", "patchTrafficStop(null, $('#patchTrafficStopFormValues'), ", Optional.ofNullable(trafficStop_).map(TrafficStop::getPk).map(a -> a.toString()).orElse("null"), ", function() {}, function() {}); ")
								.f().sx("Modify traffic stops")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");

			g("div");
		}
		htmlSuggestedTrafficStopGenPage(this, null, listTrafficStop);
	}

	/**
	**/
	public static void htmlSuggestedTrafficStopGenPage(PageLayout p, String id, SearchList<TrafficStop> listTrafficStop) {
		SiteRequestEnUS siteRequest_ = p.getSiteRequest_();
		try {
			OperationRequest operationRequest = siteRequest_.getOperationRequest();
			JsonObject queryParams = Optional.ofNullable(operationRequest).map(OperationRequest::getParams).map(or -> or.getJsonObject("query")).orElse(new JsonObject());
			String q = "*:*";
			String query1 = "objectText";
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

			Integer rows1 = Optional.ofNullable(listTrafficStop).map(l -> l.getRows()).orElse(10);
			Integer start1 = Optional.ofNullable(listTrafficStop).map(l -> l.getStart()).orElse(1);
			Integer start2 = start1 - rows1;
			Integer start3 = start1 + rows1;
			Integer rows2 = rows1 / 2;
			Integer rows3 = rows1 * 2;
			start2 = start2 < 0 ? 0 : start2;
			StringBuilder fqs = new StringBuilder();
			for(String fq : Optional.ofNullable(listTrafficStop).map(l -> l.getFilterQueries()).orElse(new String[0])) {
				if(!StringUtils.contains(fq, "(")) {
					String fq1 = StringUtils.substringBefore(fq, "_");
					String fq2 = StringUtils.substringAfter(fq, ":");
					if(!StringUtils.startsWithAny(fq, "classCanonicalNames_", "archived_", "deleted_", "sessionId", "userKeys"))
						fqs.append("&fq=").append(fq1).append(":").append(fq2);
				}
			}
			StringBuilder sorts = new StringBuilder();
			for(SortClause sort : Optional.ofNullable(listTrafficStop).map(l -> l.getSorts()).orElse(Arrays.asList())) {
				sorts.append("&sort=").append(StringUtils.substringBefore(sort.getItem(), "_")).append(" ").append(sort.getOrder().name());
			}

			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), TrafficStopGenPage.ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), TrafficStopGenPage.ROLES)
					) {
					{ p.e("div").a("class", "").f();
						{ p.e("button").a("id", "refreshAllTrafficStopGenPage", id).a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-green ").a("onclick", "patchTrafficStopVals([], {}, function() { addGlow($('#refreshAllTrafficStopGenPage", id, "')); }, function() { addError($('#refreshAllTrafficStopGenPage", id, "')); }); ").f();
							p.e("i").a("class", "fas fa-sync-alt ").f().g("i");
							p.sx("refresh all the traffic stops");
						} p.g("button");
					} p.g("div");
			}
			{ p.e("div").a("class", "w3-cell-row ").f();
				{ p.e("div").a("class", "w3-cell ").f();
					{ p.e("span").f();
						p.sx("search traffic stops: ");
					} p.g("span");
				} p.g("div");
			} p.g("div");
			{ p.e("div").a("class", "w3-bar ").f();

				p.e("input")
					.a("type", "text")
					.a("class", "suggestTrafficStop w3-input w3-border w3-bar-item ")
					.a("name", "suggestTrafficStop")
					.a("id", "suggestTrafficStop", id)
					.a("autocomplete", "off")
					.a("oninput", "suggestTrafficStopObjectSuggest( [ { 'name': 'q', 'value': 'objectSuggest:' + $(this).val() }, { 'name': 'rows', 'value': '10' }, { 'name': 'fl', 'value': 'pk,pageUrlPk,trafficStopCompleteName' } ], $('#suggestListTrafficStop", id, "'), ", p.getSiteRequest_().getRequestPk(), "); ")
					.a("onkeyup", "if (event.keyCode === 13) { event.preventDefault(); window.location.href = '/traffic-stop?q=", query1, ":' + encodeURIComponent(this.value) + '", fqs, sorts, "&start=", start2, "&rows=", rows1, "'; }"); 
				if(listTrafficStop != null)
					p.a("value", query2);
				p.fg();
				{ p.e("button")
					.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pale-green ")
					.a("onclick", "window.location.href = '/traffic-stop?q=", query1, ":' + encodeURIComponent(this.previousElementSibling.value) + '", fqs, sorts, "&start=", start2, "&rows=", rows1, "'; ") 
					.f();
					p.e("i").a("class", "fas fa-search ").f().g("i");
				} p.g("button");

			} p.g("div");
			{ p.e("div").a("class", "w3-cell-row ").f();
				{ p.e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
					{ p.e("ul").a("class", "w3-ul w3-hoverable ").a("id", "suggestListTrafficStop", id).f();
					} p.g("ul");
				} p.g("div");
			} p.g("div");
			{ p.e("div").a("class", "").f();
				{ p.e("a").a("href", "/traffic-stop").a("class", "").f();
					p.e("i").a("class", "far fa-newspaper ").f().g("i");
					p.sx("see all the traffic stops");
				} p.g("a");
			} p.g("div");
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

}
