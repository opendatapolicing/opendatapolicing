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
 * Indexed: true
 * Page: true
 * 
 * AName.enUS: a cluster
 * Color: gray
 * IconGroup: regular
 * IconName: fort-awesome
 * Keyword: classSimpleNameCluster
 * 
 * RoleUser: true
 * Role.enUS: SiteService
 * RoleRead.enUS: User
 **/ 

public class Cluster extends ClusterGen<Object> {

	/**
	 * {@inheritDoc}
	 * Ignore: true
	 */
	protected void _siteRequest_(Wrap<SiteRequestEnUS> c) {}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * PrimaryKey: true
	 * Modify: false
	 * HtmlRow: 1
	 * HtmlCell: 1
	 * DisplayName.enUS: primary key
	 */
	protected void _pk(Wrap<Long> c) {}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * InheritPrimaryKey: true
	 * Define: true
	 */
	protected void _inheritPk(Wrap<Long> c) {}

	/**
	 * {@inheritDoc}
	 * UniqueKey: true
	 */
	protected void _id(Wrap<String> c) {
		if(pk != null)
			c.o(getClass().getSimpleName() + "_" + pk.toString());
	}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * Modify: false
	 * VarCreated: true
	 * HtmlRow: 1
	 * HtmlCell: 2
	 * HtmlColumn: 2
	 * DisplayName.enUS: created
	 */
	protected void _created(Wrap<ZonedDateTime> c) {}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Modify: false
	 * VarModified: true
	 * HtmlRow: 1
	 * HtmlCell: 3
	 * DisplayName.enUS: modified
	 */ 
	protected void _modified(Wrap<ZonedDateTime> c) {
		c.o(ZonedDateTime.now(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())));
	}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 */ 
	protected void _classCanonicalName(Wrap<String> c) {
		String o = getClass().getCanonicalName();
		c.o(o);
	}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 */ 
	protected void _classSimpleName(Wrap<String> c) {
		String o = getClass().getSimpleName();
		c.o(o);
	}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 */ 
	protected void _classCanonicalNames(List<String> l) { 
		Class<?> cl = getClass();
		if(!cl.equals(Cluster.class))
			l.add(cl.getCanonicalName());
		l.add(Cluster.class.getCanonicalName());
	}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Saves: true
	 */ 
	protected void _saves(List<String> l) {
	}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * VarTitle: true
	 * HtmlColumn: 2
	 */ 
	protected void _objectTitle(Wrap<String> c) {
		if(pk != null)
			c.o(pk.toString());
	}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * VarId: true
	 */ 
	protected void _objectId(Wrap<String> c) {
		if(pk != null){
			c.o(pk.toString());
		}
	}

	public String toId(String s) {
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

	public Cluster e(String localName) {
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

	public Cluster a1(String attributeName, Object...objects) {
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

	public Cluster a(String attributeName, Object...objects) {
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

	public Cluster a2() {
		AllWriter w = siteRequest_.getW();
		w.s("\"");
		
		return this;
	}

	public Cluster f() {
		AllWriter w = siteRequest_.getW();
		w.s(">");
		
		return this;
	}

	public Cluster s(Object...objects) {
		AllWriter w = siteRequest_.getW();
		for(Object object : objects) {
			if(object != null) {
				String s = object.toString();
				w.s(s);
			}
		}
		
		return this;
	}

	public Cluster t(int numberTabs, Object...objects) {
		for(int i = 0; i < numberTabs; i++)
			s("  ");
		s(objects);
		return this;
	}

	public Cluster tl(int numberTabs, Object...objects) {
		for(int i = 0; i < numberTabs; i++)
			s("  ");
		s(objects);
		s("\n");
		return this;
	}

	public Cluster l(Object...objects) {
		s(objects);
		s("\n");
		return this;
	}

	public Cluster lx(Object...objects) {
		s(objects);
		sx("\n");
		return this;
	}

	public Cluster sx(Object...objects) {
		AllWriter w = siteRequest_.getW();
		for(Object object : objects) {
			if(object != null) {
				String s = object.toString();
				w.s(UtilXml.escape(s));
			}
		}
		
		return this;
	}

	public Cluster tx(int numberTabs, Object...objects) {
		for(int i = 0; i < numberTabs; i++)
			sx("  ");
		sx(objects);
		return this;
	}

	public Cluster tlx(int numberTabs, Object...objects) {
		for(int i = 0; i < numberTabs; i++)
			sx("  ");
		sx(objects);
		sx("\n");
		return this;
	}

	public Cluster fg() {
		AllWriter w = siteRequest_.getW();
		w.s("/>");
		siteRequest_.getXmlStack().pop();
		
		return this;
	}

	public Cluster g(String localName) {
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
}