package com.opendatapolicing.enus.page;  



import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.solr.common.SolrDocument;

import com.opendatapolicing.enus.agency.SiteAgencyGenPage;
import com.opendatapolicing.enus.config.SiteConfig;
import com.opendatapolicing.enus.design.PageDesignGenPage;
import com.opendatapolicing.enus.html.part.HtmlPart;
import com.opendatapolicing.enus.html.part.HtmlPartGenPage;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.opendatapolicing.enus.search.SearchList;
import com.opendatapolicing.enus.searchbasis.SearchBasisGenPage;
import com.opendatapolicing.enus.state.SiteStateGenPage;
import com.opendatapolicing.enus.trafficcontraband.ContrabandGenPage;
import com.opendatapolicing.enus.trafficperson.TrafficPersonGenPage;
import com.opendatapolicing.enus.trafficsearch.TrafficSearchGenPage;
import com.opendatapolicing.enus.trafficstop.TrafficStop;
import com.opendatapolicing.enus.trafficstop.TrafficStopEnUSApiServiceImpl;
import com.opendatapolicing.enus.trafficstop.TrafficStopGenPage;
import com.opendatapolicing.enus.user.SiteUser;
import com.opendatapolicing.enus.wrap.Wrap;
import com.opendatapolicing.enus.writer.AllWriter;
import com.opendatapolicing.enus.xml.UtilXml;

import io.netty.handler.codec.http.QueryStringDecoder;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.api.service.ServiceRequest;


/**
 * Keyword: classSimpleNamePageLayout
 */
public class PageLayout extends PageLayoutGen<Object> {

	public static final List<String> ROLES_MANAGER = Arrays.asList("SiteManager");

	public static final List<String> ROLES_ADMIN = Arrays.asList("SiteAdmin");

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");

	public static final List<String> ROLE_READS = Arrays.asList("User");

	public static List<String> HTML_CLOSED_ELEMENTS = Arrays.asList("area", "base", "br", "col", "command", "embed", "hr", "img", "input", "keygen", "link", "meta", "param", "source", "track", "wbr");

	public static List<String> HTML_ELEMENTS_NO_WRAP = Arrays.asList("script", "span", "a", "b", "i", "u", "title", "use", "h1", "h2", "h3", "h4", "h5", "h6", "pre", "p", "textarea");

	public static DateTimeFormatter FORMATDateTimeShort = DateTimeFormatter.ofPattern("MMM d yyyy h:mm a", Locale.US);

	public static DateTimeFormatter FORMATDateShort = DateTimeFormatter.ofPattern("EEE MMM d yyyy", Locale.US);

	public static DateTimeFormatter FORMATMonthYear = DateTimeFormatter.ofPattern("MMM yyyy", Locale.US);

	public static DateTimeFormatter FORMATDateDisplay = DateTimeFormatter.ofPattern("EEEE MMMM d yyyy", Locale.US);

	public static DateTimeFormatter FORMATDateTimeDisplay = DateTimeFormatter.ofPattern("EEEE MMMM d yyyy h:mm a:ss.SSS", Locale.US);

	public static DateTimeFormatter FORMATZonedDateTimeDisplay = DateTimeFormatter.ofPattern("EEEE MMMM d yyyy H:mm:ss.SSS zz VV", Locale.US);

	public static DateTimeFormatter FORMATTimeDisplay = DateTimeFormatter.ofPattern("h:mm a", Locale.US);

	protected void _siteRequest_(Wrap<SiteRequestEnUS> c) {
	}

	protected void _siteBaseUrl(Wrap<String> c) {
		c.o(siteRequest_.getSiteConfig_().getSiteBaseUrl());
	}

	protected void _staticBaseUrl(Wrap<String> c) {
		c.o(siteRequest_.getSiteConfig_().getStaticBaseUrl()); 
	}

	protected void _map(Map<String, Object> m) {
	}

	protected void _pageSolrDocument(Wrap<SolrDocument> c) {
		
	}

	protected void _w(Wrap<AllWriter> c) {
		c.o(siteRequest_.getW());
	}

	protected void _contextIconGroup(Wrap<String> c) {
	}

	protected void _contextIconName(Wrap<String> c) {
	}

	protected void _contextIconCssClasses(Wrap<String> c) {
		if(contextIconGroup != null && contextIconName != null)
			c.o("fa" + StringUtils.substring(contextIconGroup, 0, 1) + " fa-" + contextIconName + " ");
	}

	protected void _pageVisibleToBots(Wrap<Boolean> c) {
		c.o(BooleanUtils.toBooleanDefaultIfNull((Boolean)pageSolrDocument.get(c.var + "_stored_boolean"), false));
	}

	protected void _pageH1(Wrap<String> c) {
		c.o(StringUtils.defaultIfBlank((String)pageSolrDocument.get(c.var + "_stored_string"), null));
	}

	protected void _pageH2(Wrap<String> c) {
		c.o(StringUtils.defaultIfBlank((String)pageSolrDocument.get(c.var + "_stored_string"), null));
	}

	protected void _pageH3(Wrap<String> c) {
		c.o(StringUtils.defaultIfBlank((String)pageSolrDocument.get(c.var + "_stored_string"), null));
	}

	protected void __pageH1Short(Wrap<String> c) {
		c.o(StringUtils.defaultIfBlank((String)pageSolrDocument.get(c.var + "_stored_string"), pageH1));
	}

	protected void __pageH2Short(Wrap<String> c) {
		c.o(StringUtils.defaultIfBlank((String)pageSolrDocument.get(c.var + "_stored_string"), pageH2));
	}

	protected void __pageH3Short(Wrap<String> c) {
		c.o(StringUtils.defaultIfBlank((String)pageSolrDocument.get(c.var + "_stored_string"), pageH2));
	}

	protected void _pageTitle(Wrap<String> c) {
		c.o(StringUtils.defaultIfBlank((String)pageSolrDocument.get(c.var + "_enUS_stored_string"), StringUtils.join(pageH1, pageH2)));
	}

	protected void _pageUri(Wrap<String> c) {
		c.o(StringUtils.defaultIfBlank((String)pageSolrDocument.get(c.var + "_enUS_stored_string"), null));
	}

	protected void _pageUris(List<String> l) {
		l.add(pageUri.toString());
		l.add(pageUri + "/");
	}

	protected void _pageUrl(Wrap<String> c) {
	}

	protected void _pageImageUri(Wrap<String> c) {
		c.o(StringUtils.defaultIfBlank((String)pageSolrDocument.get(c.var + "_stored_string"), null));
	}

	protected void _pageImageUrl(Wrap<String> c) {
		c.o(StringUtils.defaultIfBlank((String)pageSolrDocument.get(c.var + "_stored_string"), "https://" + siteRequest_.getSiteConfig_().getSiteHostName() + pageImageUri));
	}

	protected void _pageVideoId(Wrap<String> c) {
		c.o(StringUtils.defaultIfBlank((String)pageSolrDocument.get(c.var + "_stored_string"), null));
	}

	protected void _pageVideoUrl(Wrap<String> c) {
		if(pageVideoId != null) {
			c.o(StringUtils.defaultIfBlank((String)pageSolrDocument.get(c.var + "_stored_string"), "https://youtu.be/" + pageVideoId));
		}
	}

	protected void _pageVideoUrlEmbed(Wrap<String> c) {
		if(pageVideoId != null) {
			c.o(StringUtils.defaultIfBlank((String)pageSolrDocument.get(c.var + "_stored_string"), "https://www.youtube.com/embed/" + pageVideoId));
		}
	}

	protected void _pageImageWidth(Wrap<Integer> c) {
		c.o((Integer)pageSolrDocument.get(c.var + "_stored_int"));
	}

	protected void _pageImageHeight(Wrap<Integer> c) {
		c.o((Integer)pageSolrDocument.get(c.var + "_stored_int"));
	}

	protected void _pageImageContentType(Wrap<String> c) {
		c.o(StringUtils.defaultIfBlank((String)pageSolrDocument.get(c.var + "_stored_string"), null));
	}

	protected void _pageContentType(Wrap<String> c) {
		c.o("text/html;charset=UTF-8");
	}

	protected void _pageCreated(Wrap<LocalDateTime> c) {   
		Date solrVal = (Date)pageSolrDocument.get(c.var + "_stored_date");
		if(solrVal != null)
			c.o(solrVal.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
	}

	protected void _pageModified(Wrap<LocalDateTime> c) {
		Date solrVal = (Date)pageSolrDocument.get(c.var + "_stored_date");
		if(solrVal != null)
			c.o(solrVal.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		else
			c.o(pageCreated);
	}

	protected void _pageKeywords(Wrap<String> c) {
	}

	protected void _pageDescription(Wrap<String> c) {
	}

	protected void _pageHomeUri(Wrap<String> c) {
		c.o("/");
	}

	protected void _pageSchoolUri(Wrap<String> c) {
		c.o(" /school");
	}

	protected void _pageUserUri(Wrap<String> c) {
		c.o("/user");
	}

	protected void _pageLogoutUri(Wrap<String> c) {
		try {
			SiteConfig siteConfig = siteRequest_.getSiteConfig_();
			String o = siteConfig.getAuthUrl() + "/realms/" + siteConfig.getAuthRealm() + "/protocol/openid-connect/logout?redirect_uri=" + URLEncoder.encode(siteConfig.getSiteBaseUrl() + "/logout", "UTF-8");
			c.o(o);
		} catch (UnsupportedEncodingException e) {
			ExceptionUtils.rethrow(e);
		}
	}

	@Override()
	public void  htmlMeta() {
		e("meta").a("charset", "UTF-8").fg();
		e("meta").a("name", "viewport").a("content", "width=device-width, initial-scale=1").fg();
		e("meta").a("name", "keywords").a("content", pageKeywords).fg();
		e("meta").a("property", "og:title").a("content", pageTitle).fg();
		e("meta").a("property", "og:description").a("content", pageDescription).fg();
		e("meta").a("property", "og:url").a("content", pageUrl).fg();
		e("meta").a("property", "og:site_name").a("content", siteRequest_.getSiteConfig_().getDomainName()).fg();
		e("meta").a("property", "og:image").a("content", pageImageUrl).fg();
		e("meta").a("property", "og:image:width").a("content", pageImageWidth).fg();
		e("meta").a("property", "og:image:height").a("content", pageImageHeight).fg();
		e("meta").a("property", "og:image:type").a("content", pageImageContentType).fg();
		e("meta").a("property", "og:image:alt").a("content", pageTitle).fg();
		e("meta").a("property", "og:type").a("content", "article").fg();
		e("meta").a("name", "twitter:card").a("content", "summary_large_image").fg();
		e("meta").a("name", "twitter:site").a("content", "@computateorg").fg();
		e("meta").a("name", "twitter:creator").a("content", "@computateorg").fg();
		e("meta").a("name", "twitter:title").a("content", pageTitle).fg();
		e("meta").a("name", "twitter:description").a("content", pageDescription).fg();
		e("meta").a("name", "twitter:image").a("content", pageImageUrl).fg();
		e("meta").a("name", "description").a("content", pageDescription).fg();
	}

	@Override()
	public void  htmlScriptsPageLayout() {
		e("script").a("src", staticBaseUrl, "/js/jquery-1.12.4.min.js").f().g("script");
		e("script").a("src", staticBaseUrl, "/js/site-enUS.js").f().g("script");
		e("script").a("src", staticBaseUrl, "/js/sockjs.js").f().g("script");
		e("script").a("src", staticBaseUrl, "/js/vertx-eventbus.js").f().g("script");
		e("script").a("src", staticBaseUrl, "/js/enUS/SiteUserPage.js").f().g("script");
		e("script").a("src", staticBaseUrl, "/js/enUS/SiteStatePage.js").f().g("script");
		e("script").a("src", staticBaseUrl, "/js/enUS/SiteAgencyPage.js").f().g("script");
		e("script").a("src", staticBaseUrl, "/js/enUS/TrafficStopPage.js").f().g("script");
		e("script").a("src", staticBaseUrl, "/js/enUS/SearchBasisPage.js").f().g("script");
		e("script").a("src", staticBaseUrl, "/js/enUS/ContrabandPage.js").f().g("script");
		e("script").a("src", staticBaseUrl, "/js/enUS/TrafficPersonPage.js").f().g("script");
		e("script").a("src", staticBaseUrl, "/js/enUS/TrafficSearchPage.js").f().g("script");
		e("script").a("src", staticBaseUrl, "/js/moment.js").f().g("script");
		e("script").a("src", staticBaseUrl, "/js/jqDatePicker.js").f().g("script");
		e("script").a("src", staticBaseUrl, "/js/jquery.serialize-object.js").f().g("script");
		e("script").a("src", staticBaseUrl, "/js/jSignature.js").f().g("script");
		e("script").a("src", "https://kit.fontawesome.com/59d19567d5.js").f().g("script");
	}

	@Override()
	public void  htmlScriptPageLayout() {
	}

	@Override()
	public void  htmlStylesPageLayout() {
		e("link").a("rel", "stylesheet").a("href", staticBaseUrl, "/css/w3.css").fg();
		e("link").a("rel", "stylesheet").a("href", staticBaseUrl, "/css/site-enUS.css").fg();
		e("link").a("rel", "stylesheet").a("href", staticBaseUrl, "/css/datePicker.css").fg();
		e("link").a("rel", "stylesheet").a("href", "https://fonts.googleapis.com/css?family=Khand").fg();
//		e("link").a("rel", "stylesheet").a("href", "https://pro.fontawesome.com/releases/v5.9.0/css/all.css").a("integrity", "sha384-vlOMx0hKjUCl4WzuhIhSNZSm2yQCaf0mOU1hEDK/iztH3gU4v5NMmJln9273A6Jz").a("crossorigin", "anonymous").fg();
	}

	@Override()
	public void  htmlStylePageLayout() {
	}

	@Override()
	public void  htmlBodyPageLayout() {
	}

	@Override()
	public void  htmlPageLayout() {
		e("html").a("xmlns:xlink", "http://www.w3.org/1999/xlink").a("xmlns", "http://www.w3.org/1999/xhtml").a("xmlns:fb", "http://ogp.me/ns/fb#").f();
			e("head").f();
				e("title").f();
					sx(pageTitle);
				g("title");
				htmlMeta();
				htmlScripts();
				e("script").f().l("/*<![CDATA[*/");
				htmlScript();
				s("/*]]>*/").g("script");
				htmlStyles();
				e("style").f().l("/*<![CDATA[*/");
				htmlStyle();
				s("/*]]>*/").g("style");
	
			g("head");
			e("body").a("class", "w3-light-grey ").f(); 
				e("a").a("name", "top").f().g("a");
				e("div").a("class", "top-box w3-top ").f().g("div");
				e("div").a("id", "modaleErreur").a("class", "w3-modal").a("onclick", "this.style.display = 'none';").f();
					e("div").a("class", "w3-modal-content w3-animate-zoom").f();
						e("header").a("class", "w3-container w3-center w3-red ").f();
							e("h3").f();
								sx("Une erreur s'est produite. ");
							g("h3");
						g("header");
					g("div");
					e("div").a("id", "modaleErreurMessage").a("class", "w3-container w3-center").f().g("div");
					e("header").a("class", "w3-container w3-center w3-padding-16 ").f();
						sx("L'erreur a été envoyée par e-mail à l'administrateur pour analyse. ");
					g("header");
				g("div");
				e("div").a("class", "site-section-all ").f();
					e("div").a("class", "site-section-above ").f();
						e("div").a("class", "w3-content w3-center w3-black ").f();
							e("div").a("class", "").f();
								menu("Menu1");
							g("div"); 
						g("div");
						e("div").a("id", "site-section-primary").a("class", "site-section-primary w3-text-black w3-padding-bottom-32 ").f();
							e("div").a("class", "w3-content ").f();
	
								htmlBody();

								e("footer").a("class", "w3-center w3-black w3-padding-48 w3-margin-top ").f();
									e("div").f();
										e("a").a("href", "https://github.com/opendatapolicing/opendatapolicing").a("class", "w3-xlarge ").f();
											sx("This site is open source");
										g("a");
									g("div");
									e("div").f();
										e("a").a("href", "https://github.com/opendatapolicing/opendatapolicing").a("class", "w3-large ").f();
											sx("View the source code here");
										g("a");
									g("div");
									e("div").a("class", "grow-30 w3-margin ").f();
										e("a").a("href", "https://www.openshift.com/products/online/").a("target", "_blank").f();
											e("span").a("class", "w3-large ").f();
												sx("Powered by ");
											g("span");
											e("img").a("alt", "").a("class", "w3-image ").a("style", "display: inline-block; width: 200px; margin: 0 10px;").a("src", staticBaseUrl, "/svg/openshift.svg").fg();
										g("a");
									g("div");
								g("footer");
							g("div");
						g("div");
					g("div");
				g("div");
				e("div").a("class", "w3-row site-section-contact ").f();
					e("div").a("class", "w3-content w3-center  w3-cell-row w3-margin-bottom-32 ").f();
						menu("Menu2");
						e("div").a("class", "w3-container ").f();
							e("div").a("class", "w3-container w3-text-black w3-margin-top ").f();
								e("h6").a("id", "h2-contactez-nous").a("class",  "w3-padding w3-xlarge w3-text-white ").f();
									sx("Let's get connected. ");
								g("h6");
								e("div").a("class", "w3-cell-row ").f();
									e("div").a("class", "w3-cell ").f();
										e("a").a("target", "_blank").a("rel", "noopener noreferrer").a("data-ajax", "false").a("href", "https://www.facebook.com/littleorchardpreschool/").f();
											e("img").a("alt", "").a("class", "grow-30 ").a("style", "display: inline-block; width: 50px; height: 50px; margin: 0 10px;").a("src", staticBaseUrl, "/svg/facebook.svg").fg();
										g("a");
									g("div");
								g("div");
								e("h6").a("class",  "w3-padding w3-large w3-text-white ").f();
									e("a").a("href", "#top").f();
										sx("Up to the top. ");
									g("a");
								g("h6");
							g("div");
						g("div");
					g("div");
				g("div");
			g("body");
		g("html");

	}

	public void  menu(String id) {
		e("div").a("class", "w3-bar w3-text-white w3-padding-bottom-8 w3-padding-top-8 ").a("style", "padding-left: 16px; padding-right: 16px; ").f();

			e("div").a("class", "site-bar-item w3-bar-item ").f();
				e("a").a("class", "").a("href", pageHomeUri).f();
					e("span").a("class", "site-menu-item").f();
						sx("home");
					g("span");
				g("a");
			g("div");

			{ e("div").a("class", "w3-dropdown-hover ").f();
				{ e("div").a("class", "w3-button w3-hover-pale-blue ").f();
						e("i").a("class", "far fa-globe-americas ").f().g("i");
						sx("states");
				} g("div");
				{ e("div").a("class", "w3-dropdown-content w3-card-4 w3-padding ").f();
					SiteStateGenPage.htmlSuggestedSiteStateGenPage(this, id, null);
				} g("div");
			} g("div");

			{ e("div").a("class", "w3-dropdown-hover ").f();
				{ e("div").a("class", "w3-button w3-hover-pale-yellow ").f();
						e("i").a("class", "far fa-road ").f().g("i");
						sx("agencies");
				} g("div");
				{ e("div").a("class", "w3-dropdown-content w3-card-4 w3-padding ").f();
					SiteAgencyGenPage.htmlSuggestedSiteAgencyGenPage(this, id, null);
				} g("div");
			} g("div");

			{ e("div").a("class", "w3-dropdown-hover ").f();
				{ e("div").a("class", "w3-button w3-hover-pale-green ").f();
						e("i").a("class", "far fa-newspaper ").f().g("i");
						sx("traffic stops");
				} g("div");
				{ e("div").a("class", "w3-dropdown-content w3-card-4 w3-padding ").f();
					TrafficStopGenPage.htmlSuggestedTrafficStopGenPage(this, id, null);
				} g("div");
			} g("div");

			{ e("div").a("class", "w3-dropdown-hover ").f();
				{ e("div").a("class", "w3-button w3-hover-pale-green ").f();
						e("i").a("class", "far fa-newspaper ").f().g("i");
						sx("search bases");
				} g("div");
				{ e("div").a("class", "w3-dropdown-content w3-card-4 w3-padding ").f();
					SearchBasisGenPage.htmlSuggestedSearchBasisGenPage(this, id, null);
				} g("div");
			} g("div");

			{ e("div").a("class", "w3-dropdown-hover ").f();
				{ e("div").a("class", "w3-button w3-hover-pale-green ").f();
						e("i").a("class", "far fa-newspaper ").f().g("i");
						sx("contrabands");
				} g("div");
				{ e("div").a("class", "w3-dropdown-content w3-card-4 w3-padding ").f();
					ContrabandGenPage.htmlSuggestedContrabandGenPage(this, id, null);
				} g("div");
			} g("div");

			{ e("div").a("class", "w3-dropdown-hover ").f();
				{ e("div").a("class", "w3-button w3-hover-pale-green ").f();
						e("i").a("class", "far fa-newspaper ").f().g("i");
						sx("people");
				} g("div");
				{ e("div").a("class", "w3-dropdown-content w3-card-4 w3-padding ").f();
					TrafficPersonGenPage.htmlSuggestedTrafficPersonGenPage(this, id, null);
				} g("div");
			} g("div");

			{ e("div").a("class", "w3-dropdown-hover ").f();
				{ e("div").a("class", "w3-button w3-hover-pale-green ").f();
						e("i").a("class", "far fa-newspaper ").f().g("i");
						sx("traffic searches");
				} g("div");
				{ e("div").a("class", "w3-dropdown-content w3-card-4 w3-padding ").f();
					TrafficSearchGenPage.htmlSuggestedTrafficSearchGenPage(this, id, null);
				} g("div");
			} g("div");

			if(
					CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES_ADMIN)
					|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES_ADMIN)
					) {
	
				{ e("div").a("class", "w3-dropdown-hover ").f();
					{ e("div").a("class", "w3-button w3-hover-khaki ").f();
							e("i").a("class", "far fa-drafting-compass ").f().g("i");
							sx("designs");
					} g("div");
					{ e("div").a("class", "w3-dropdown-content w3-card-4 w3-padding ").f();
						PageDesignGenPage.htmlSuggestedPageDesignGenPage(this, id, null);
					} g("div");
				} g("div");
	
				{ e("div").a("class", "w3-dropdown-hover ").f();
					{ e("div").a("class", "w3-button w3-hover-khaki ").f();
							e("i").a("class", "far fa-puzzle-piece ").f().g("i");
							sx("HTML");
					} g("div");
					{ e("div").a("class", "w3-dropdown-content w3-card-4 w3-padding ").f();
						HtmlPartGenPage.htmlSuggestedHtmlPartGenPage(this, id, null);
					} g("div");
				} g("div");
			}
			writeLoginLogout();
		g("div");
	}

	public void  writeLoginLogout() {
		Stack<String> xmlStack = siteRequest_.getXmlStack();
		boolean empty = xmlStack.isEmpty();
		if(empty) {
			xmlStack.push("html");
			xmlStack.push("body");
			xmlStack.push("div");
			xmlStack.push("div");
			xmlStack.push("div");
			
		}

		if(siteRequest_.getUserId() == null) {
			e("div").a("class", "site-bar-item w3-bar-item ").f();
				e("a").a("class", "w3-padding ").a("href", pageUserUri).f(); 
					e("span").a("class", "site-menu-item").f();
						e("i").a("class", "far fa-sign-in-alt ").f().g("i");
						sx("login");
					g("span");
				g("a");
			g("div");
		}
		if(siteRequest_.getUserId() != null) {

			{ e("div").a("class", "w3-dropdown-hover ").f();
				{ e("div").a("class", "w3-button w3-hover-green ").f();
						e("i").a("class", "far fa-user-cog ").f().g("i");
						sx(siteRequest_.getUserName());
				} g("div");
				{ e("div").a("class", "w3-dropdown-content w3-card-4 w3-padding ").f();
					SiteUser o = siteRequest_.getSiteUser();
					{ e("div").a("class", "w3-cell-row ").f();
						{ e("a").a("href", "/user/", siteRequest_.getUserKey()).a("class", "").f();
							e("i").a("class", "far fa-user ").f().g("i");
							sx("my user page");
						} g("a");
					} g("div");
					{ e("div").a("class", "w3-cell-row ").f();
						e("label").a("for", "Page_seeArchived").a("class", "").f().sx("see archived").g("label");
						e("input")
							.a("type", "checkbox")
							.a("value", "true")
							.a("class", "setSeeArchived")
							.a("name", "setSeeArchived")
							.a("id", "Page_seeArchived")
							.a("onchange", "patchSiteUserVal([{ name: 'fq', value: 'pk:' + ", siteRequest_.getUserKey(), " }], 'setSeeArchived', $(this).prop('checked'), function() { addGlow($('#Page_seeArchived')); }, function() { addError($('#Page_seeArchived')); }); ")
							;
							if(o.getSeeArchived() != null && o.getSeeArchived())
								a("checked", "checked");
						fg();
					} g("div");
					{ e("div").a("class", "w3-cell-row ").f();
						e("label").a("for", "Page_seeDeleted").a("class", "").f().sx("see deleted").g("label");
						e("input")
							.a("type", "checkbox")
							.a("value", "true")
							.a("class", "setSeeDeleted")
							.a("name", "setSeeDeleted")
							.a("id", "Page_seeDeleted")
							.a("onchange", "patchSiteUserVal([{ name: 'fq', value: 'pk:' + ", siteRequest_.getUserKey(), " }], 'setSeeDeleted', $(this).prop('checked'), function() { addGlow($('#Page_seeDeleted')); }, function() { addError($('#Page_seeDeleted')); }); ")
							;
							if(o.getSeeDeleted() != null && o.getSeeDeleted())
								a("checked", "checked");
						fg();
					} g("div");
				} g("div");
			} g("div");
			e("div").a("class", "site-bar-item w3-bar-item ").f();
				e("a").a("class", "w3-padding ").a("href", pageLogoutUri).f();
					e("span").a("class", "site-menu-item").f();
						e("i").a("class", "far fa-sign-out-alt ").f().g("i");
						sx("logout");
					g("span");
				g("a");
			g("div");
		}

		if(empty) {
			xmlStack.clear();
		}
	}

	public void  partagerPage() {
		{ e("div").a("class", "w3-content w3-center w3-padding-top-32 ").f();
			e("h3").f().sx("Share this story. ").g("h3");
			{ e("div").a("class", "w3-cell-row ").f();
			} g("div");
		} g("div");
	}

	public String formaterDateHeureCourt(Date date) {
		String resultat = "";
		if(date != null) {
			resultat = FORMATDateTimeShort.format(date.toInstant().atZone(ZoneId.systemDefault()));
		}
		return resultat;
	}

	public String formaterDateCourt(Date date) {
		String resultat = "";
		if(date != null) {
			resultat = FORMATDateShort.format(date.toInstant().atZone(ZoneId.systemDefault()));
		}
		return resultat;
	}

	public String formaterDateAffichage(Date date) {
		String resultat = "";
		if(date != null) {
			resultat = FORMATDateTimeDisplay.format(date.toInstant());
		}
		return resultat;
	}

	public String formaterDateAffichage(LocalDateTime date) {
		String resultat = "";
		if(date != null) {
			resultat = FORMATDateTimeDisplay.format(date);
		}
		return resultat;
	}

	public PageLayout e(String localName) {
		String localNameParent = siteRequest_.getXmlStack().isEmpty() ? null : siteRequest_.getXmlStack().peek();

		boolean eNoWrapParent = localNameParent == null || HTML_ELEMENTS_NO_WRAP.contains(localNameParent);
		String tabs = String.join("", Collections.nCopies(siteRequest_.getXmlStack().size(), "\t"));
		String tabsEscaped = String.join("", Collections.nCopies(siteRequest_.getXmlStack().size(), "\\t"));

		siteRequest_.getXmlStack().push(localName);
		if(StringUtils.equals(localName, "html"))
			w.s("<!DOCTYPE html>\n");
		if(!eNoWrapParent && !tabsEscaped.isEmpty()) {
			w.l();
			w.s(tabs);
		}
		w.s("<");
		w.s(localName);
		
		return this;
	}

	public PageLayout a1(String attributeName, Object...objects) {
		w.s(" ");
		w.s(attributeName);
		w.s("=\"");
		for(Object object : objects) {
			if(object != null) {
				String s = object.toString();
				w.s(UtilXml.escapeInQuotes(s));
			}
		}
		
		return this;
	}

	public PageLayout a(String attributeName, Object...objects) {
		w.s(" ");
		w.s(attributeName);
		w.s("=\"");
		for(Object object : objects) {
			if(object != null) {
				String s = object.toString();
				w.s(UtilXml.escapeInQuotes(s));
			}
		}
		w.s("\"");
		
		return this;
	}

	public PageLayout a2() {
		w.s("\"");
		
		return this;
	}

	public PageLayout f() {
		w.s(">");
		
		return this;
	}

	public PageLayout s(Object...objects) {
		for(Object object : objects) {
			if(object != null) {
				String s = object.toString();
				w.s(s);
			}
		}
		
		return this;
	}

	public PageLayout t(int numberTabs, Object...objects) {
		for(int i = 0; i < numberTabs; i++)
			s("\t");
		s(objects);
		return this;
	}

	public PageLayout tl(int numberTabs, Object...objects) {
		for(int i = 0; i < numberTabs; i++)
			s("\t");
		s(objects);
		s("\n");
		return this;
	}

	public PageLayout l(Object...objects) {
		s(objects);
		s("\n");
		return this;
	}

	public PageLayout lx(Object...objects) {
		s(objects);
		sx("\n");
		return this;
	}

	public PageLayout sx(Object...objects) {
		for(Object object : objects) {
			if(object != null) {
				String s = object.toString();
				w.s(UtilXml.escape(s));
			}
		}
		
		return this;
	}

	public PageLayout tx(int numberTabs, Object...objects) {
		for(int i = 0; i < numberTabs; i++)
			sx("\t");
		sx(objects);
		return this;
	}

	public PageLayout tlx(int numberTabs, Object...objects) {
		for(int i = 0; i < numberTabs; i++)
			sx("\t");
		sx(objects);
		sx("\n");
		return this;
	}

	public PageLayout fg() {
		w.s("/>");
		siteRequest_.getXmlStack().pop();
		
		return this;
	}

	public PageLayout g(String localName) {
		String localNameParent = siteRequest_.getXmlStack().peek();
		boolean eNoWrap = localNameParent == null || HTML_ELEMENTS_NO_WRAP.contains(localName);

		siteRequest_.getXmlStack().pop();
		String tabs = String.join("", Collections.nCopies(siteRequest_.getXmlStack().size(), "\t"));
		String tabsEscaped = String.join("", Collections.nCopies(siteRequest_.getXmlStack().size(), "\\t"));

		if(!eNoWrap && !tabsEscaped.isEmpty()) {
			w.l();
			w.s(tabs);
		}
		w.s("</");
		w.s(localName);
		w.s(">");
		
		return this;
	}

	public void  htmlFormOptionsUtilisateurSite(SiteUser o) {
		{ e("div").a("class", "w3-cell-row ").f();
			{ e("div").a("class", "w3-cell w3-cell-middle w3-center w3-mobile ").f();
				{ e("form").a("id", "voirArchiveForm").a("style", "display: inline-block; ").f();
					e("input")
						.a("type", "hidden")
						.a("name", "voirArchive")
						.a("id", "Page_voirArchive")
						.a("value", "false")
					.fg();

					e("input")
						.a("type", "checkbox")
						.a("value", "true")
						.a("class", "setSeeArchived")
						.a("name", "setSeeArchived")
						.a("id", "Page_voirArchive")
						.a("onchange", "patchUtilisateurSite($('#UtilisateurSiteForm'), $('#voirArchiveForm')); ")
						;
						if(o.getSeeArchived() != null && o.getSeeArchived())
							a("checked", "checked");
					fg();

					e("label").a("for", "Page_voirArchive").a("class", "").f().sx("voir archivé").g("label");
				} g("form");
			} g("div");
		} g("div");
		{ e("div").a("class", "w3-cell-row ").f();
			{ e("div").a("class", "w3-cell w3-cell-middle w3-center w3-mobile ").f();
				{ e("form").a("id", "voirSupprimeForm").a("style", "display: inline-block; ").f();
					e("input")
						.a("type", "hidden")
						.a("name", "voirSupprime")
						.a("id", "Page_voirSupprime")
						.a("value", "false")
					.fg();

					e("input");
						a("type", "checkbox");
						a("value", "true");
						a("class", "setSeeDeleted");
						a("name", "setSeeDeleted");
						a("id", "Page_voirSupprime");
						a("onchange", "patchUtilisateurSite($('#UtilisateurSiteForm'), $('#voirSupprimeForm')); ");
						if(o.getSeeDeleted() != null && o.getSeeDeleted())
							a("checked", "checked");
					fg();

					e("label").a("for", "Page_voirSupprime").a("class", "").f().sx("voir supprimé").g("label");
				} g("form");
			} g("div");
		} g("div");
	}

	public Integer htmlPageLayout2(String pageContentType, List<HtmlPart> htmlPartList, HtmlPart htmlPartParent, Integer start, Integer size) {

		Integer i;

		Double parentSort1 = null;
		Double parentSort2 = null;
		Double parentSort3 = null;
		Double parentSort4 = null;
		Double parentSort5 = null;
		Double parentSort6 = null;
		Double parentSort7 = null;
		Double parentSort8 = null;
		Double parentSort9 = null;
		Double parentSort10 = null;

		if(htmlPartParent != null) {
			parentSort1 = htmlPartParent.getSort1();
			parentSort2 = htmlPartParent.getSort2();
			parentSort3 = htmlPartParent.getSort3();
			parentSort4 = htmlPartParent.getSort4();
			parentSort5 = htmlPartParent.getSort5();
			parentSort6 = htmlPartParent.getSort6();
			parentSort7 = htmlPartParent.getSort7();
			parentSort8 = htmlPartParent.getSort8();
			parentSort9 = htmlPartParent.getSort9();
			parentSort10 = htmlPartParent.getSort10();
		}

		for(i = Math.abs(start); i < size; i++) {
			HtmlPart htmlPart = htmlPartList.get(i);

			Double sort1 = htmlPart.getSort1();
			Double sort2 = htmlPart.getSort2();
			Double sort3 = htmlPart.getSort3();
			Double sort4 = htmlPart.getSort4();
			Double sort5 = htmlPart.getSort5();
			Double sort6 = htmlPart.getSort6();
			Double sort7 = htmlPart.getSort7();
			Double sort8 = htmlPart.getSort8();
			Double sort9 = htmlPart.getSort9();
			Double sort10 = htmlPart.getSort10();

			if(htmlPartParent != null) {
				if(parentSort2 != null && (sort1 == null || parentSort1.compareTo(sort1) != 0))
					return i;
				if(parentSort3 != null && (sort2 == null || parentSort2.compareTo(sort2) != 0))
					return i;
				if(parentSort4 != null && (sort3 == null || parentSort3.compareTo(sort3) != 0))
					return i;
				if(parentSort5 != null && (sort4 == null || parentSort4.compareTo(sort4) != 0))
					return i;
				if(parentSort6 != null && (sort5 == null || parentSort5.compareTo(sort5) != 0))
					return i;
				if(parentSort7 != null && (sort6 == null || parentSort6.compareTo(sort6) != 0))
					return i;
				if(parentSort8 != null && (sort7 == null || parentSort7.compareTo(sort7) != 0))
					return i;
				if(parentSort9 != null && (sort8 == null || parentSort8.compareTo(sort8) != 0))
					return i;
				if(parentSort10 != null && (sort9 == null || parentSort9.compareTo(sort9) != 0))
					return i;
			}

			if(start >= 0) {
	
				String htmlVar = htmlPart.getHtmlVar();
				String htmlVarHtml = htmlPart.getHtmlVarHtml();
				String htmlVarSpan = htmlPart.getHtmlVarSpan();
				String htmlVarInput = htmlPart.getHtmlVarInput();
				String htmlVarForm = htmlPart.getHtmlVarForm();
				String htmlVarForEach = htmlPart.getHtmlVarForEach();
				String htmlVarBase64Decode = htmlPart.getHtmlVarBase64Decode();
				Boolean pdfExclude = htmlPart.getPdfExclude();
				Boolean htmlExclude = htmlPart.getHtmlExclude();

				if(htmlVarSpan != null)
					htmlVar = htmlVarSpan;
	
				if(
						"application/pdf".equals(pageContentType) && BooleanUtils.isNotTrue(pdfExclude)
						|| !"application/pdf".equals(pageContentType) && BooleanUtils.isNotTrue(htmlExclude)
						) {
					s(htmlPart.getHtmlBefore());
					if(htmlVar != null) {
	
						Object parent = StringUtils.contains(htmlVar, ".") ? obtainForClass(StringUtils.substringBeforeLast(htmlVar, ".")) : null;
						if(parent == null)
							parent = this;
						String var = StringUtils.substringAfterLast(htmlVar, ".");
						if(StringUtils.isBlank(var))
							var = htmlVar;
	
						if(htmlVarForEach != null) {
		
							Object parentForEach = StringUtils.contains(htmlVarForEach, ".") ? obtainForClass(StringUtils.substringBeforeLast(htmlVarForEach, ".")) : null;
							if(parentForEach == null)
								parentForEach = this;
							String varForEach = StringUtils.substringAfterLast(htmlVarForEach, ".");
							if(StringUtils.isBlank(varForEach))
								varForEach = htmlVarForEach;
		
							try {
								Collection<?> collection = (Collection<?>)MethodUtils.invokeMethod(parentForEach, "get" + StringUtils.capitalize(varForEach), new Object[] {});
								List<?> list = collection.stream().collect(Collectors.toList());
								Integer forStart = i + 1;
		
								for(Object o : list) {
									try {
										MethodUtils.invokeExactMethod(parent, "set" + StringUtils.capitalize(var), o);
										i = htmlPageLayout2(pageContentType, htmlPartList, htmlPart, forStart, size);
									} catch (RuntimeException e) {
										throw e;
									} catch (Exception e) {
										throw new RuntimeException(String.format("Could not call method %s of var %s and object: %s", "set" + StringUtils.capitalize(var), htmlVar, parent), e);
									}
								}
								if(list.size() == 0) {
									i = htmlPageLayout2(pageContentType, htmlPartList, htmlPart, -forStart, size);
								}
								i = i - 1;
							} catch (RuntimeException e) {
								throw e;
							} catch (Exception e) {
								throw new RuntimeException(String.format("Could not call method %s of object: %s", "get" + StringUtils.capitalize(varForEach), parentForEach), e);
							}
						}
						else {
							try {
								String s = (String)MethodUtils.invokeExactMethod(parent, "str" + StringUtils.capitalize(var));
								if(htmlVarSpan != null) {
									Long pk = (Long)MethodUtils.invokeExactMethod(parent, "getPk");
									e("span").a("class", "var", parent.getClass().getSimpleName(), pk, StringUtils.capitalize(var), " ").f().sx(s).g("span");
								}
								else {
									sx(s);
								}
							} catch (Exception e) {
								sx(obtainForClass(htmlVar));
							}
						}
					}
					if(htmlVarHtml != null) {
						Object parent = StringUtils.contains(htmlVarHtml, ".") ? obtainForClass(StringUtils.substringBeforeLast(htmlVarHtml, ".")) : null;
						if(parent == null)
							parent = this;
						String var = StringUtils.substringAfterLast(htmlVarHtml, ".");
						if(StringUtils.isBlank(var))
							var = htmlVarHtml;
	
						try {
							String s = (String)MethodUtils.invokeExactMethod(parent, "str" + StringUtils.capitalize(var));
							if(htmlVarSpan != null) {
								Long pk = (Long)MethodUtils.invokeExactMethod(parent, "getPk");
								e("span").a("class", "var", parent.getClass().getSimpleName(), pk, StringUtils.capitalize(var), " ").f().s(s).g("span");
							}
							else {
								s(s);
							}
						} catch (Exception e) {
							s(obtainForClass(htmlVarHtml));
						}
					}
					if(htmlVarForm != null) {
						Object parent = StringUtils.contains(htmlVarForm, ".") ? obtainForClass(StringUtils.substringBeforeLast(htmlVarForm, ".")) : null;
						if(parent == null)
							parent = this;
						String var = StringUtils.substringAfterLast(htmlVarForm, ".");
						if(StringUtils.isBlank(var))
							var = htmlVarForm;
	
	//					Object o = obtainForClass(StringUtils.substringBeforeLast(htmlVarForm, "."));
	//					String var = StringUtils.substringAfterLast(htmlVarForm, ".");
						try {
							MethodUtils.invokeExactMethod(parent, "htm" + StringUtils.capitalize(var), "Page");
						} catch (RuntimeException e) {
							throw e;
						} catch (Exception e) {
							throw new RuntimeException(String.format("Could not call method %s of var %s and object: %s", "htm" + StringUtils.capitalize(var), htmlVarInput, parent), e);
						}
					}
					if(htmlVarInput != null) {
						Object parent = StringUtils.contains(htmlVarInput, ".") ? obtainForClass(StringUtils.substringBeforeLast(htmlVarInput, ".")) : null;
						if(parent == null)
							parent = this;
						String var = StringUtils.substringAfterLast(htmlVarInput, ".");
						if(StringUtils.isBlank(var))
							var = htmlVarInput;
	
						try {
	//					Object o = obtainForClass(StringUtils.substringBeforeLast(htmlVarInput, "."));
	//					String var = StringUtils.substringAfterLast(htmlVarInput, ".");
							if("application/pdf".equals(pageContentType)) {
								Object o = obtainForClass(htmlVarInput);
								if(o instanceof Boolean) {
									e("img").a("class", "").a("style", "width: 1em; height: 1em; position: relative; top: 3px; ").a("src", siteRequest_.getSiteConfig_().getStaticBaseUrl(), ((Boolean)o) ? "/png/check-square-o.png" : "/png/square-o.png").fg();
								}
								else if (o instanceof String && o.toString().startsWith("data:image")) {
									e("img").a("class", "").a("style", "height: 100px; ").a("src", o.toString()).fg();
								}
								else {
									e("span").a("style", "border-bottom: 1px solid black; display: block; ").f();
									String s = (String)MethodUtils.invokeExactMethod(parent, "str" + StringUtils.capitalize(var));
									sx(s);
									g("span");
								}
							}
							else {
								try {
									MethodUtils.invokeExactMethod(parent, "input" + StringUtils.capitalize(var), "Page");
								} catch (RuntimeException e) {
									throw e;
								} catch (Exception e) {
									throw new RuntimeException(String.format("Could not call method %s of var %s and object: %s", "input" + StringUtils.capitalize(var), htmlVarInput, parent), e);
								}
							}
						} catch (RuntimeException e) {
							throw e;
						} catch (Exception e) {
							throw new RuntimeException(String.format("Could not call method %s of var %s and object: %s", "htm" + StringUtils.capitalize(var), htmlVarInput, parent), e);
						}
					}
					if(htmlVarBase64Decode != null) {
						Object parent = StringUtils.contains(htmlVarBase64Decode, ".") ? obtainForClass(StringUtils.substringBeforeLast(htmlVarBase64Decode, ".")) : null;
						if(parent == null)
							parent = this;
						String var = StringUtils.substringAfterLast(htmlVarBase64Decode, ".");
						if(StringUtils.isBlank(var))
							var = htmlVarBase64Decode;
	
						try {
							String s = (String)MethodUtils.invokeExactMethod(parent, "get" + StringUtils.capitalize(var));
							w.getBuffer().appendBytes(Base64.getDecoder().decode(s));
						} catch (Exception e) {
							s(obtainForClass(htmlVarBase64Decode));
						}
					}
					if(htmlPart.getLoginLogout()) {
						writeLoginLogout();
						l();
					}
					String searchUri = htmlPart.getSearchUri();
					String mapTo = htmlPart.getMapTo();
					if(searchUri != null && mapTo != null) {

						Matcher m = Pattern.compile("\\{\\{\\s*([^\\}\\s]+)\\s*\\}\\}").matcher(searchUri);
						boolean found = m.find();
						StringBuffer sb = new StringBuffer();

						while(found) {
							String var = m.group(1);
							Object obtain = obtainForClass(var);
							if(obtain != null)
								m.appendReplacement(sb, obtain.toString());
							found = m.find();
						}
						m.appendTail(sb);
						searchUri = sb.toString();

						SiteRequestEnUS siteRequest2 = new SiteRequestEnUS();
						siteRequest2.setVertx(siteRequest_.getVertx());
						siteRequest2.setSiteContext_(siteRequest_.getSiteContext_());
						siteRequest2.setSiteConfig_(siteRequest_.getSiteConfig_());
						siteRequest2.setUserId(siteRequest_.getUserId());
						siteRequest2.initDeepSiteRequestEnUS(siteRequest2);

						if(searchUri.startsWith("/api/traffic-stop?")) {
							TrafficStopEnUSApiServiceImpl service = new TrafficStopEnUSApiServiceImpl(siteRequest_.getSiteContext_());
							ServiceRequest serviceRequest = new ServiceRequest();
							siteRequest2.setServiceRequest(serviceRequest);
							JsonObject params = new JsonObject();
							serviceRequest.setParams(params);
							JsonObject query = new JsonObject();
							params.put("path", new JsonObject());
							Map<String, List<String>> decodedParams = new QueryStringDecoder(searchUri).parameters();
							for (Map.Entry<String, List<String>> entry : decodedParams.entrySet()) {
								String key = entry.getKey();
								if(query.containsKey(key) && entry.getValue() != null) {
									JsonArray values = query.getJsonArray(key);
									for(String value : entry.getValue()) {
										values.add(value);
									}
								}
								else {
									JsonArray values = new JsonArray();
									for(String value : entry.getValue()) {
										values.add(value);
									}
									query.put(key, values);
								}
							}
							query.put("query", query);
							params.put("query", query);
							SearchList<TrafficStop> searchList = service.aSearchTrafficStopList(siteRequest2, false, true, false, siteRequest_.getRequestUri(), "Search");
							searchList.toString();
							map.put(mapTo, searchList);
						}
					}
					s(htmlPart.getHtmlAfter());
				}
			}
		}

		return i;
	}

	public String urlEncode(String s) {
		try {
			return s == null ? "" : URLEncoder.encode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			ExceptionUtils.rethrow(e);
			return "";
		}
	}
}
