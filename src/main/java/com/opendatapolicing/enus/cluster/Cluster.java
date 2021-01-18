package com.opendatapolicing.enus.cluster;

import java.text.Normalizer;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import com.opendatapolicing.enus.page.PageLayout;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.opendatapolicing.enus.wrap.Wrap;
import com.opendatapolicing.enus.writer.AllWriter;
import com.opendatapolicing.enus.xml.UtilXml;

/**
 * Model: true
 * Api: true
 * Indexed: true
 * 
 * ApiTag.enUS: Cluster
 * ApiUri.enUS: /api/cluster
 * 
 * ApiMethod: POST
 * ApiMethod: PUT
 * ApiMethod: PATCH
 * ApiMethod: GET
 * ApiMethod.enUS: Search
 * 
 * ApiMethode.enUS: SearchPage
 * PageSearchPage.enUS: ClusterPage
 * ApiUriSearchPage.enUS: /cluster
 * 
 * AName.enUS: a cluster
 * Color: gray
 * IconGroup: regular
 * IconName: fort-awesome
 * Keyword: classSimpleNameCluster
 * 
 * RoleUser: true
 * Role.frFR: SiteAdmin
 * Role.enUS: SiteAdmin
 * RoleRead.enUS: User
 **/  

public class Cluster extends ClusterGen<Object> {
	
	protected void _siteRequest_(Wrap<SiteRequestEnUS> c) {}
	
	protected void _pk(Wrap<Long> c) {}
	
	protected void _inheritPk(Wrap<Long> c) {}
	
	protected void _id(Wrap<String> c) {
		if(pk != null)
			c.o(pk.toString());
	}
	
	protected void _created(Wrap<ZonedDateTime> c) {}
	
	protected void _modified(Wrap<ZonedDateTime> c) {
		c.o(ZonedDateTime.now(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())));
	}
	
	protected void _modifiedIsoOffsetDateTime(Wrap<String> c) {
		if(modified != null)
			c.o(modified.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
	}
	
	protected void _archived(Wrap<Boolean> c) {
		c.o(false);
	}
	
	protected void _deleted(Wrap<Boolean> c) {
		c.o(false);
	}
	
	protected void _classCanonicalName(Wrap<String> c) {
		String o = getClass().getCanonicalName();
		c.o(o);
	}
	
	protected void _classSimpleName(Wrap<String> c) {
		String o = getClass().getSimpleName();
		c.o(o);
	}
	
	protected void _classCanonicalNames(List<String> l) { 
		Class<?> cl = getClass();
		if(!cl.equals(Cluster.class))
			l.add(cl.getCanonicalName());
		l.add(Cluster.class.getCanonicalName());
	}
	
	protected void _sessionId(Wrap<String> c) {
	}
	
	protected void _userId(Wrap<String> c) {
	}
	
	protected void _userKey(Wrap<Long> c) {
	}
	
	protected void _saves(List<String> l) {
	}
	
	protected void _objectTitle(Wrap<String> c) {
	}
	
	protected void _objectId(Wrap<String> c) {
		if(objectTitle != null) {
			c.o(toId(objectTitle));
		}
		else if(pk != null){
			c.o(pk.toString());
		}
	}
	
	toId(String s) {
		if(s != null) {
			s = Normalizer.normalize(s, Normalizer.Form.NFD);
			s = StringUtils.lowerCase(s);
			s = StringUtils.trim(s);
			s = StringUtils.replacePattern(s, "\\s{1,}", "-");
			s = StringUtils.replacePattern(s, "[^\\w-]", "");
			s = StringUtils.replacePattern(s, "-{2,}", "-");
		}

		return s;
	}
	
	protected void _objectNameVar(Wrap<String> c) {
		if(objectId != null) {
			Class<?> cl = getClass();

			try {
				String o = toId((String)FieldUtils.getField(cl, cl.getSimpleName() + "_NameVar").get(this));
				c.o(o);
			} catch (Exception e) {
				ExceptionUtils.rethrow(e);
			}
		}
	}
	
	protected void _objectSuggest(Wrap<String> c) { 
		StringBuilder b = new StringBuilder();
		if(pk != null)
			b.append(" ").append(pk);
		if(objectNameVar != null)
			b.append(" ").append(objectNameVar);
		if(objectId != null)
			b.append(" ").append(objectId);
		if(objectTitle != null)
			b.append(" ").append(objectTitle);
		c.o(b.toString());
	}
	
	protected void _objectText(Wrap<String> c) { 
		StringBuilder b = new StringBuilder();
		if(pk != null)
			b.append(" ").append(pk);
		if(objectNameVar != null)
			b.append(" ").append(objectNameVar);
		if(objectId != null)
			b.append(" ").append(objectId);
		if(objectTitle != null)
			b.append(" ").append(objectTitle);
		c.o(b.toString());
	}
	
	protected void _pageUrlId(Wrap<String> c) {
		if(objectId != null) {
			String o = siteRequest_.getSiteConfig_().getSiteBaseUrl() + "/" + objectNameVar + "/" + objectId;
			c.o(o);
		}
	}
	
	protected void _pageUrlPk(Wrap<String> c) {
		if(pk != null) {
			String o = siteRequest_.getSiteConfig_().getSiteBaseUrl() + "/" + objectNameVar + "/" + pk;
			c.o(o);
		}
	}
	
	protected void _pageUrlApi(Wrap<String> c) {
		if(pk != null) {
			String o = siteRequest_.getSiteConfig_().getSiteBaseUrl() + "/api/" + objectNameVar + "/" + pk;
			c.o(o);
		}
	}
	
	protected void _pageH1(Wrap<String> c) {
		try {
			Class<?> cl = getClass();
			c.o((String)FieldUtils.getField(cl, cl.getSimpleName() + "_NameSingular").get(this) + ": " + objectTitle);
		} catch (Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}
	
	e(String localName) {
		AllWriter w = siteRequest_.getW();
		String localNameParent = siteRequest_.getXmlStack().isEmpty() ? null : siteRequest_.getXmlStack().peek();

		boolean eNoWrapParent = localNameParent == null || PageLayout.HTML_ELEMENTS_NO_WRAP.contains(localNameParent);
		String tabs = String.join("", Collections.nCopies(siteRequest_.getXmlStack().size(), "  "));

		siteRequest_.getXmlStack().push(localName);
		if(StringUtils.equals(localName, "html"))
			w.s("<!DOCTYPE html>\n");
		if(!eNoWrapParent && !tabs.isEmpty()) {
			w.l();
			w.s(tabs);
		}
		w.s("<");
		w.s(localName);
		
		return this;
	}
	
	a1(String attributeName, Object...objects) {
		AllWriter w = siteRequest_.getW();
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
	
	a(String attributeName, Object...objects) {
		AllWriter w = siteRequest_.getW();
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
	
	a2() {
		AllWriter w = siteRequest_.getW();
		w.s("\"");
		
		return this;
	}
	
	f() {
		AllWriter w = siteRequest_.getW();
		w.s(">");
		
		return this;
	}
	
	s(Object...objects) {
		AllWriter w = siteRequest_.getW();
		for(Object object : objects) {
			if(object != null) {
				String s = object.toString();
				w.s(s);
			}
		}
		
		return this;
	}
	
	t(int numberTabs, Object...objects) {
		for(int i = 0; i < numberTabs; i++)
			s("  ");
		s(objects);
		return this;
	}
	
	tl(int numberTabs, Object...objects) {
		for(int i = 0; i < numberTabs; i++)
			s("  ");
		s(objects);
		s("\n");
		return this;
	}
	
	l(Object...objects) {
		s(objects);
		s("\n");
		return this;
	}

	lx(Object...objects) {
		s(objects);
		sx("\n");
		return this;
	}

	sx(Object...objects) {
		AllWriter w = siteRequest_.getW();
		for(Object object : objects) {
			if(object != null) {
				String s = object.toString();
				w.s(UtilXml.escape(s));
			}
		}
		
		return this;
	}

	tx(int numberTabs, Object...objects) {
		for(int i = 0; i < numberTabs; i++)
			sx("  ");
		sx(objects);
		return this;
	}

	tlx(int numberTabs, Object...objects) {
		for(int i = 0; i < numberTabs; i++)
			sx("  ");
		sx(objects);
		sx("\n");
		return this;
	}

	fg() {
		AllWriter w = siteRequest_.getW();
		w.s("/>");
		siteRequest_.getXmlStack().pop();
		
		return this;
	}

	g(String localName) {
		AllWriter w = siteRequest_.getW();
		String localNameParent = siteRequest_.getXmlStack().peek();
		boolean eNoWrap = localNameParent == null || PageLayout.HTML_ELEMENTS_NO_WRAP.contains(localName);
	
		siteRequest_.getXmlStack().pop();
		String tabs = String.join("", Collections.nCopies(siteRequest_.getXmlStack().size(), "  "));
	
		if(!eNoWrap || localNameParent == null)
			w.l();
		if(!eNoWrap && !tabs.isEmpty())
			w.s(tabs);
		w.s("</");
		w.s(localName);
		w.s(">");
		
		return this;
	}

	htmPk(String classApiMethodMethod) {
		Cluster s = (Cluster)this;
		{ s.e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			if("Page".equals(classApiMethodMethod)) {
				{ s.e("div").a("class", "w3-padding ").f();
					{ s.e("div").a("class", "w3-card ").f();
						{ s.e("div").a("class", "w3-cell-row w3- ").f();
							s.e("label").a("class", "").f().sx("primary key").g("label");
						} s.g("div");
						{ s.e("div").a("class", "w3-cell-row  ").f();
							{ s.e("div").a("class", "w3-cell ").f();
								{ s.e("div").a("class", "w3-rest ").f();
									s.e("a").a("href", StringUtils.substringBeforeLast(pageUrlApi, "/"), "?fq=pk:", pk).a("class", "varCluster", pk, "Pk ").f().sx(strPk()).g("a");
								} s.g("div");
							} s.g("div");
						} s.g("div");
					} s.g("div");
				} s.g("div");
			}
		} s.g("div");
	}
}