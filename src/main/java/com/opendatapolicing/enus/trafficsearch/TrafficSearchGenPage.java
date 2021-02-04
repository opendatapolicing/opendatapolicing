package com.opendatapolicing.enus.trafficsearch;

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
public class TrafficSearchGenPage extends TrafficSearchGenPageGen<PageLayout> {

	public static final List<String> ROLES = Arrays.asList("SiteService");
	public static final List<String> ROLE_READS = Arrays.asList("");

	/**
	 * {@inheritDoc}
	 * 
	 **/
	protected void _listTrafficSearch(Wrap<SearchList<TrafficSearch>> c) {
	}

	protected void _trafficSearch_(Wrap<TrafficSearch> c) {
		if(listTrafficSearch != null && listTrafficSearch.size() == 1)
			c.o(listTrafficSearch.get(0));
	}

	@Override protected void _pageH1(Wrap<String> c) {
			c.o("traffic searchs");
	}

	@Override protected void _pageH2(Wrap<String> c) {
		if(trafficSearch_ != null && trafficSearch_.getTrafficSearchCompleteName() != null)
			c.o(trafficSearch_.getTrafficSearchCompleteName());
	}

	@Override protected void _pageH3(Wrap<String> c) {
		c.o("");
	}

	@Override protected void _pageTitle(Wrap<String> c) {
		if(trafficSearch_ != null && trafficSearch_.getTrafficSearchCompleteName() != null)
			c.o(trafficSearch_.getTrafficSearchCompleteName());
		else if(trafficSearch_ != null)
			c.o("traffic searchs");
		else if(listTrafficSearch == null || listTrafficSearch.size() == 0)
			c.o("no traffic search found");
		else
			c.o("traffic searchs");
	}

	@Override protected void _pageUri(Wrap<String> c) {
		c.o("/traffic-search");
	}

	@Override protected void _pageImageUri(Wrap<String> c) {
			c.o("/png/traffic-search-999.png");
	}

	@Override protected void _contextIconGroup(Wrap<String> c) {
			c.o("regular");
	}

	@Override protected void _contextIconName(Wrap<String> c) {
			c.o("newspaper");
	}

	@Override public void initDeepTrafficSearchGenPage() {
		initTrafficSearchGenPage();
		super.initDeepPageLayout();
	}

	@Override public void htmlScriptsTrafficSearchGenPage() {
		e("script").a("src", staticBaseUrl, "/js/enUS/TrafficSearchPage.js").f().g("script");
		e("script").a("src", staticBaseUrl, "/js/enUS/TrafficPersonPage.js").f().g("script");
		e("script").a("src", staticBaseUrl, "/js/enUS/ContrabandPage.js").f().g("script");
		e("script").a("src", staticBaseUrl, "/js/enUS/SearchBasisPage.js").f().g("script");
	}

	@Override public void htmlScriptTrafficSearchGenPage() {
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
			tl(2, "suggestTrafficSearchPersonKey([{'name':'fq','value':'trafficSearchKeys:' + pk}], $('#listTrafficSearchPersonKey_Page'), pk, true); ");
		} else {
			tl(2, "suggestTrafficSearchPersonKey([{'name':'fq','value':'trafficSearchKeys:' + pk}], $('#listTrafficSearchPersonKey_Page'), pk, false); ");
		}
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			tl(2, "suggestTrafficSearchContrabandKeys([{'name':'fq','value':'searchKey:' + pk}], $('#listTrafficSearchContrabandKeys_Page'), pk, true); ");
		} else {
			tl(2, "suggestTrafficSearchContrabandKeys([{'name':'fq','value':'searchKey:' + pk}], $('#listTrafficSearchContrabandKeys_Page'), pk, false); ");
		}
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			tl(2, "suggestTrafficSearchSearchBasisKeys([{'name':'fq','value':'searchKey:' + pk}], $('#listTrafficSearchSearchBasisKeys_Page'), pk, true); ");
		} else {
			tl(2, "suggestTrafficSearchSearchBasisKeys([{'name':'fq','value':'searchKey:' + pk}], $('#listTrafficSearchSearchBasisKeys_Page'), pk, false); ");
		}
		tl(1, "}");
		tl(1, "websocketTrafficSearch(websocketTrafficSearchInner);");
		l("});");
	}

	public void htmlFormPageTrafficSearch(TrafficSearch o) {
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
			o.htmPersonKey("Page");
			o.htmContrabandKeys("Page");
			o.htmSearchBasisKeys("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSearchTypeTitle("Page");
			o.htmSearchVehicle("Page");
			o.htmSearchDriver("Page");
			o.htmSearchPassenger("Page");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSearchProperty("Page");
			o.htmSearchVehicleSiezed("Page");
			o.htmSearchPersonalPropertySiezed("Page");
			o.htmSearchOtherPropertySiezed("Page");
		} g("div");
	}

	public void htmlFormPOSTTrafficSearch(TrafficSearch o) {
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
			o.htmPersonKey("POST");
			o.htmContrabandKeys("POST");
			o.htmSearchBasisKeys("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSearchTypeTitle("POST");
			o.htmSearchVehicle("POST");
			o.htmSearchDriver("POST");
			o.htmSearchPassenger("POST");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSearchProperty("POST");
			o.htmSearchVehicleSiezed("POST");
			o.htmSearchPersonalPropertySiezed("POST");
			o.htmSearchOtherPropertySiezed("POST");
		} g("div");
	}

	public void htmlFormPUTImportTrafficSearch(TrafficSearch o) {
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

	public void htmlFormPUTMergeTrafficSearch(TrafficSearch o) {
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

	public void htmlFormPUTCopyTrafficSearch(TrafficSearch o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCreated("PUTCopy");
			o.htmModified("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchived("PUTCopy");
			o.htmDeleted("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPersonKey("PUTCopy");
			o.htmContrabandKeys("PUTCopy");
			o.htmSearchBasisKeys("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSearchVehicle("PUTCopy");
			o.htmSearchDriver("PUTCopy");
			o.htmSearchPassenger("PUTCopy");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSearchProperty("PUTCopy");
			o.htmSearchVehicleSiezed("PUTCopy");
			o.htmSearchPersonalPropertySiezed("PUTCopy");
			o.htmSearchOtherPropertySiezed("PUTCopy");
		} g("div");
	}

	public void htmlFormPATCHTrafficSearch(TrafficSearch o) {
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmCreated("PATCH");
			o.htmModified("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmArchived("PATCH");
			o.htmDeleted("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmPersonKey("PATCH");
			o.htmContrabandKeys("PATCH");
			o.htmSearchBasisKeys("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSearchVehicle("PATCH");
			o.htmSearchDriver("PATCH");
			o.htmSearchPassenger("PATCH");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSearchProperty("PATCH");
			o.htmSearchVehicleSiezed("PATCH");
			o.htmSearchPersonalPropertySiezed("PATCH");
			o.htmSearchOtherPropertySiezed("PATCH");
		} g("div");
	}

	public void htmlFormSearchTrafficSearch(TrafficSearch o) {
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
			o.htmPersonKey("Search");
			o.htmContrabandKeys("Search");
			o.htmSearchBasisKeys("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSearchTypeTitle("Search");
			o.htmSearchVehicle("Search");
			o.htmSearchDriver("Search");
			o.htmSearchPassenger("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmSearchProperty("Search");
			o.htmSearchVehicleSiezed("Search");
			o.htmSearchPersonalPropertySiezed("Search");
			o.htmSearchOtherPropertySiezed("Search");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			o.htmInheritPk("Search");
			o.htmUserId("Search");
			o.htmUserKey("Search");
			o.htmObjectTitle("Search");
			o.htmStopAgencyTitle("Search");
			o.htmStopDateTime("Search");
			o.htmStopPurposeNum("Search");
			o.htmStopPurposeTitle("Search");
			o.htmStopActionNum("Search");
			o.htmStopActionTitle("Search");
			o.htmStopDriverArrest("Search");
			o.htmStopPassengerArrest("Search");
			o.htmStopEncounterForce("Search");
			o.htmStopEngageForce("Search");
			o.htmStopOfficerInjury("Search");
			o.htmStopDriverInjury("Search");
			o.htmStopPassengerInjury("Search");
			o.htmStopOfficerId("Search");
			o.htmStopLocationId("Search");
			o.htmStopCityId("Search");
			o.htmPersonTypeId("Search");
			o.htmPersonGenderId("Search");
			o.htmPersonEthnicityId("Search");
			o.htmPersonRaceId("Search");
			o.htmSearchTypeNum("Search");
		} g("div");
	}

	@Override public void htmlBodyTrafficSearchGenPage() {

		OperationRequest operationRequest = siteRequest_.getOperationRequest();
		JsonObject params = operationRequest.getParams();
		if(listTrafficSearch == null || listTrafficSearch.size() == 0) {

			{ e("h1").f();
				{ e("a").a("href", "/traffic-search").a("class", "w3-bar-item w3-btn w3-center w3-block w3-pale-green w3-hover-pale-green ").f();
					if(contextIconCssClasses != null)
						e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx("traffic searchs").g("span");
				} g("a");
			} g("h1");
			e("div").a("class", "w3-padding-16 w3-card-4 w3-light-grey ").f();
			{ e("h2").f();
				{ e("span").a("class", "w3-bar-item w3-padding w3-center w3-block w3-pale-green ").f();
					if(contextIconCssClasses != null)
						e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx("no traffic search found").g("span");
				} g("span");
			} g("h2");
		} else if(listTrafficSearch != null && listTrafficSearch.size() == 1 && params.getJsonObject("query").getString("q").equals("*:*")) {
			TrafficSearch o = listTrafficSearch.get(0);
			siteRequest_.setRequestPk(o.getPk());
			if(StringUtils.isNotEmpty(pageH1)) {
				{ e("h1").f();
					{ e("a").a("href", "/traffic-search").a("class", "w3-bar-item w3-btn w3-center w3-block w3-pale-green w3-hover-pale-green ").f();
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
				{ e("a").a("href", "/traffic-search").a("class", "w3-bar-item w3-btn w3-center w3-block w3-pale-green w3-hover-pale-green ").f();
					if(contextIconCssClasses != null)
						e("i").a("class", contextIconCssClasses + " site-menu-icon ").f().g("i");
					e("span").a("class", " ").f().sx(pageH1).g("span");
				} g("a");
			} g("h1");
			{ e("div").a("class", "").f();
				{ e("div").f();
					JsonObject queryParams = Optional.ofNullable(operationRequest).map(OperationRequest::getParams).map(or -> or.getJsonObject("query")).orElse(new JsonObject());
					Long num = listTrafficSearch.getQueryResponse().getResults().getNumFound();
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

					Integer rows1 = Optional.ofNullable(listTrafficSearch).map(l -> l.getRows()).orElse(10);
					Integer start1 = Optional.ofNullable(listTrafficSearch).map(l -> l.getStart()).orElse(1);
					Integer start2 = start1 - rows1;
					Integer start3 = start1 + rows1;
					Integer rows2 = rows1 / 2;
					Integer rows3 = rows1 * 2;
					start2 = start2 < 0 ? 0 : start2;
					StringBuilder fqs = new StringBuilder();
					for(String fq : Optional.ofNullable(listTrafficSearch).map(l -> l.getFilterQueries()).orElse(new String[0])) {
						if(!StringUtils.contains(fq, "(")) {
							String fq1 = StringUtils.substringBefore(fq, "_");
							String fq2 = StringUtils.substringAfter(fq, ":");
							if(!StringUtils.startsWithAny(fq, "classCanonicalNames_", "archived_", "deleted_", "sessionId", "userKeys"))
								fqs.append("&fq=").append(fq1).append(":").append(fq2);
						}
					}
					StringBuilder sorts = new StringBuilder();
					for(SortClause sort : Optional.ofNullable(listTrafficSearch).map(l -> l.getSorts()).orElse(Arrays.asList())) {
						sorts.append("&sort=").append(StringUtils.substringBefore(sort.getItem(), "_")).append(" ").append(sort.getOrder().name());
					}

					if(start1 == 0) {
						e("i").a("class", "fas fa-arrow-square-left w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/traffic-search?q=", query, fqs, sorts, "&start=", start2, "&rows=", rows1).f();
							e("i").a("class", "fas fa-arrow-square-left ").f().g("i");
						} g("a");
					}

					if(rows1 <= 1) {
						e("i").a("class", "fas fa-minus-square w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/traffic-search?q=", query, fqs, sorts, "&start=", start1, "&rows=", rows2).f();
							e("i").a("class", "fas fa-minus-square ").f().g("i");
						} g("a");
					}

					{ e("a").a("href", "/traffic-search?q=", query, fqs, sorts, "&start=", start1, "&rows=", rows3).f();
						e("i").a("class", "fas fa-plus-square ").f().g("i");
					} g("a");

					if(start3 >= num) {
						e("i").a("class", "fas fa-arrow-square-right w3-opacity ").f().g("i");
					} else {
						{ e("a").a("href", "/traffic-search?q=", query, fqs, sorts, "&start=", start3, "&rows=", rows1).f();
							e("i").a("class", "fas fa-arrow-square-right ").f().g("i");
						} g("a");
					}
						e("span").f().sx((start1 + 1), " - ", (start1 + rows1), " of ", num).g("span");
				} g("div");
				table1TrafficSearchGenPage();
			} g("div");
		}

		if(listTrafficSearch != null && listTrafficSearch.size() == 1 && params.getJsonObject("query").getString("q").equals("*:*")) {
			TrafficSearch o = listTrafficSearch.first();

			{ e("div").a("class", "").f();

				if(o.getPk() != null) {
					{ e("form").a("action", "").a("id", "TrafficSearchForm").a("style", "display: inline-block; width: 100%; ").a("onsubmit", "event.preventDefault(); return false; ").f();
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
					htmlFormPageTrafficSearch(o);
				}

			} g("div");

		}
		htmlBodyFormsTrafficSearchGenPage();
	}

	public void table1TrafficSearchGenPage() {
		{ e("table").a("class", "w3-table w3-bordered w3-striped w3-border w3-hoverable ").f();
			table2TrafficSearchGenPage();
		} g("table");
	}

	public void table2TrafficSearchGenPage() {
		thead1TrafficSearchGenPage();
		tbody1TrafficSearchGenPage();
		tfoot1TrafficSearchGenPage();
	}

	public void thead1TrafficSearchGenPage() {
		{ e("thead").a("class", "w3-pale-green w3-hover-pale-green ").f();
			thead2TrafficSearchGenPage();
		} g("thead");
	}

	public void thead2TrafficSearchGenPage() {
			{ e("tr").f();
			if(getColumnCreated()) {
				e("th").f().sx("created").g("th");
			}
			if(getColumnObjectTitle()) {
				e("th").f().sx("").g("th");
			}
			} g("tr");
	}

	public void tbody1TrafficSearchGenPage() {
		{ e("tbody").f();
			tbody2TrafficSearchGenPage();
		} g("tbody");
	}

	public void tbody2TrafficSearchGenPage() {
		Map<String, Map<String, List<String>>> highlighting = listTrafficSearch.getQueryResponse().getHighlighting();
		for(int i = 0; i < listTrafficSearch.size(); i++) {
			TrafficSearch o = listTrafficSearch.getList().get(i);
			Map<String, List<String>> highlights = highlighting == null ? null : highlighting.get(o.getId());
			List<String> highlightList = highlights == null ? null : highlights.get(highlights.keySet().stream().findFirst().orElse(null));
			String uri = "/traffic-search/" + o.getPk();
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

	public void tfoot1TrafficSearchGenPage() {
		{ e("tfoot").a("class", "w3-pale-green w3-hover-pale-green ").f();
			tfoot2TrafficSearchGenPage();
		} g("tfoot");
	}

	public void tfoot2TrafficSearchGenPage() {
		{ e("tr").f();
			SimpleOrderedMap facets = (SimpleOrderedMap)Optional.ofNullable(listTrafficSearch.getQueryResponse()).map(QueryResponse::getResponse).map(r -> r.get("facets")).orElse(new SimpleOrderedMap());
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

	public void htmlBodyFormsTrafficSearchGenPage() {
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {

			if(listTrafficSearch != null && listTrafficSearch.size() == 1) {
				{ e("button")
					.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-green ")
						.a("id", "refreshThisTrafficSearchGenPage")
						.a("onclick", "patchTrafficSearchVals( [ {name: 'fq', value: 'pk:' + " + siteRequest_.getRequestPk() + " } ], {}, function() { addGlow($('#refreshThisTrafficSearchGenPage')); }, function() { addError($('#refreshThisTrafficSearchGenPage')); }); return false; ").f();
						e("i").a("class", "fas fa-sync-alt ").f().g("i");
					sx("refresh this traffic search");
				} g("button");
			}
		}
		if(
				siteRequest_.getUserResourceRoles().contains("SiteAdmin")
				|| siteRequest_.getUserRealmRoles().contains("SiteAdmin")
				) {

			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-green ")
				.a("onclick", "$('#putimportTrafficSearchModal').show(); ")
				.f();
				e("i").a("class", "fas fa-file-import ").f().g("i");
				sx("Import traffic searchs");
			} g("button");
			{ e("div").a("id", "putimportTrafficSearchModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-pale-green ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putimportTrafficSearchModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Import traffic searchs").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").a("id", "putimportTrafficSearchFormValues").f();
							TrafficSearch o = new TrafficSearch();
							o.setSiteRequest_(siteRequest_);

							// Form PUT
							{ e("div").a("id", "putimportTrafficSearchForm").f();
								htmlFormPUTImportTrafficSearch(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-pale-green ")
								.a("onclick", "putimportTrafficSearch($('#putimportTrafficSearchForm')); ")
								.f().sx("Import traffic searchs")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-green ")
				.a("onclick", "$('#putmergeTrafficSearchModal').show(); ")
				.f();
				e("i").a("class", "fas fa-code-merge ").f().g("i");
				sx("Merge traffic searchs");
			} g("button");
			{ e("div").a("id", "putmergeTrafficSearchModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-pale-green ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putmergeTrafficSearchModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Merge traffic searchs").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").a("id", "putmergeTrafficSearchFormValues").f();
							TrafficSearch o = new TrafficSearch();
							o.setSiteRequest_(siteRequest_);

							// Form PUT
							{ e("div").a("id", "putmergeTrafficSearchForm").f();
								htmlFormPUTMergeTrafficSearch(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-pale-green ")
								.a("onclick", "putmergeTrafficSearch($('#putmergeTrafficSearchForm')); ")
								.f().sx("Merge traffic searchs")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-green ")
				.a("onclick", "$('#putcopyTrafficSearchModal').show(); ")
				.f();
				e("i").a("class", "fas fa-copy ").f().g("i");
				sx("Duplicate traffic searchs");
			} g("button");
			{ e("div").a("id", "putcopyTrafficSearchModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-pale-green ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#putcopyTrafficSearchModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Duplicate traffic searchs").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").a("id", "putcopyTrafficSearchFormValues").f();
							TrafficSearch o = new TrafficSearch();
							o.setSiteRequest_(siteRequest_);

							// Form PUT
							{ e("div").a("id", "putcopyTrafficSearchForm").f();
								htmlFormPUTCopyTrafficSearch(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-pale-green ")
								.a("onclick", "putcopyTrafficSearch($('#putcopyTrafficSearchForm'), ", trafficSearch_ == null ? "null" : trafficSearch_.getPk(), "); ")
								.f().sx("Duplicate traffic searchs")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-green ")
				.a("onclick", "$('#postTrafficSearchModal').show(); ")
				.f();
				e("i").a("class", "fas fa-file-plus ").f().g("i");
				sx("Create a traffic search");
			} g("button");
			{ e("div").a("id", "postTrafficSearchModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-pale-green ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#postTrafficSearchModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Create a traffic search").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").a("id", "postTrafficSearchFormValues").f();
							TrafficSearch o = new TrafficSearch();
							o.setSiteRequest_(siteRequest_);

							// Form POST
							{ e("div").a("id", "postTrafficSearchForm").f();
								htmlFormPOSTTrafficSearch(o);
							} g("div");
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-pale-green ")
								.a("onclick", "postTrafficSearch($('#postTrafficSearchForm')); ")
								.f().sx("Create a traffic search")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");


			{ e("button")
				.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-green ")
				.a("onclick", "$('#patchTrafficSearchModal').show(); ")
				.f();
				e("i").a("class", "fas fa-edit ").f().g("i");
				sx("Modify traffic searchs");
			} g("button");
			{ e("div").a("id", "patchTrafficSearchModal").a("class", "w3-modal w3-padding-32 ").f();
				{ e("div").a("class", "w3-modal-content ").f();
					{ e("div").a("class", "w3-card-4 ").f();
						{ e("header").a("class", "w3-container w3-pale-green ").f();
							e("span").a("class", "w3-button w3-display-topright ").a("onclick", "$('#patchTrafficSearchModal').hide(); ").f().sx("×").g("span");
							e("h2").a("class", "w3-padding ").f().sx("Modify traffic searchs").g("h2");
						} g("header");
						{ e("div").a("class", "w3-container ").a("id", "patchTrafficSearchFormValues").f();
							TrafficSearch o = new TrafficSearch();
							o.setSiteRequest_(siteRequest_);

							htmlFormPATCHTrafficSearch(o);
							e("button")
								.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-margin w3-pale-green ")
								.a("onclick", "patchTrafficSearch(null, $('#patchTrafficSearchFormValues'), ", Optional.ofNullable(trafficSearch_).map(TrafficSearch::getPk).map(a -> a.toString()).orElse("null"), ", function() {}, function() {}); ")
								.f().sx("Modify traffic searchs")
							.g("button");

						} g("div");
					} g("div");
				} g("div");
			} g("div");

		}
		htmlSuggestedTrafficSearchGenPage(this, null, listTrafficSearch);
	}

	/**
	**/
	public static void htmlSuggestedTrafficSearchGenPage(PageLayout p, String id, SearchList<TrafficSearch> listTrafficSearch) {
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

			Integer rows1 = Optional.ofNullable(listTrafficSearch).map(l -> l.getRows()).orElse(10);
			Integer start1 = Optional.ofNullable(listTrafficSearch).map(l -> l.getStart()).orElse(1);
			Integer start2 = start1 - rows1;
			Integer start3 = start1 + rows1;
			Integer rows2 = rows1 / 2;
			Integer rows3 = rows1 * 2;
			start2 = start2 < 0 ? 0 : start2;
			StringBuilder fqs = new StringBuilder();
			for(String fq : Optional.ofNullable(listTrafficSearch).map(l -> l.getFilterQueries()).orElse(new String[0])) {
				if(!StringUtils.contains(fq, "(")) {
					String fq1 = StringUtils.substringBefore(fq, "_");
					String fq2 = StringUtils.substringAfter(fq, ":");
					if(!StringUtils.startsWithAny(fq, "classCanonicalNames_", "archived_", "deleted_", "sessionId", "userKeys"))
						fqs.append("&fq=").append(fq1).append(":").append(fq2);
				}
			}
			StringBuilder sorts = new StringBuilder();
			for(SortClause sort : Optional.ofNullable(listTrafficSearch).map(l -> l.getSorts()).orElse(Arrays.asList())) {
				sorts.append("&sort=").append(StringUtils.substringBefore(sort.getItem(), "_")).append(" ").append(sort.getOrder().name());
			}

			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), TrafficSearchGenPage.ROLES)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), TrafficSearchGenPage.ROLES)
					) {
					{ p.e("div").a("class", "").f();
						{ p.e("button").a("id", "refreshAllTrafficSearchGenPage", id).a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-pale-green ").a("onclick", "patchTrafficSearchVals([], {}, function() { addGlow($('#refreshAllTrafficSearchGenPage", id, "')); }, function() { addError($('#refreshAllTrafficSearchGenPage", id, "')); }); ").f();
							p.e("i").a("class", "fas fa-sync-alt ").f().g("i");
							p.sx("refresh all the traffic searchs");
						} p.g("button");
					} p.g("div");
			}
			{ p.e("div").a("class", "w3-cell-row ").f();
				{ p.e("div").a("class", "w3-cell ").f();
					{ p.e("span").f();
						p.sx("search traffic searchs: ");
					} p.g("span");
				} p.g("div");
			} p.g("div");
			{ p.e("div").a("class", "w3-bar ").f();

				p.e("input")
					.a("type", "text")
					.a("class", "suggestTrafficSearch w3-input w3-border w3-bar-item ")
					.a("name", "suggestTrafficSearch")
					.a("id", "suggestTrafficSearch", id)
					.a("autocomplete", "off")
					.a("oninput", "suggestTrafficSearchObjectSuggest( [ { 'name': 'q', 'value': 'objectSuggest:' + $(this).val() }, { 'name': 'rows', 'value': '10' }, { 'name': 'fl', 'value': 'pk,pageUrlPk,trafficSearchCompleteName' } ], $('#suggestListTrafficSearch", id, "'), ", p.getSiteRequest_().getRequestPk(), "); ")
					.a("onkeyup", "if (event.keyCode === 13) { event.preventDefault(); window.location.href = '/traffic-search?q=", query1, ":' + encodeURIComponent(this.value) + '", fqs, sorts, "&start=", start2, "&rows=", rows1, "'; }"); 
				if(listTrafficSearch != null)
					p.a("value", query2);
				p.fg();
				{ p.e("button")
					.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-pale-green ")
					.a("onclick", "window.location.href = '/traffic-search?q=", query1, ":' + encodeURIComponent(this.previousElementSibling.value) + '", fqs, sorts, "&start=", start2, "&rows=", rows1, "'; ") 
					.f();
					p.e("i").a("class", "fas fa-search ").f().g("i");
				} p.g("button");

			} p.g("div");
			{ p.e("div").a("class", "w3-cell-row ").f();
				{ p.e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
					{ p.e("ul").a("class", "w3-ul w3-hoverable ").a("id", "suggestListTrafficSearch", id).f();
					} p.g("ul");
				} p.g("div");
			} p.g("div");
			{ p.e("div").a("class", "").f();
				{ p.e("a").a("href", "/traffic-search").a("class", "").f();
					p.e("i").a("class", "far fa-newspaper ").f().g("i");
					p.sx("see all the traffic searchs");
				} p.g("a");
			} p.g("div");
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

}
