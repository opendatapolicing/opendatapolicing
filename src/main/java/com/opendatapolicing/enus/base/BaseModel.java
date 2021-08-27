package com.opendatapolicing.enus.base;        

import java.text.Normalizer;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.opendatapolicing.enus.config.ConfigKeys;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.opendatapolicing.enus.wrap.Wrap;

/**
 * Indexed: true
 * 
 * AName.enUS: a base model
 * Color: gray
 * IconGroup: regular
 * IconName: fort-awesome
 * Keyword: classSimpleNameCluster
 * 
 * RoleUser: true
 * Role.enUS: SiteService
 * RoleRead.enUS: User
 **/  

public class BaseModel extends BaseModelGen<Object> {

	/**
	 * {@inheritDoc}
	 * Ignore: true
	 */
	protected void _siteRequest_(Wrap<SiteRequestEnUS> w) {}

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
	protected void _pk(Wrap<Long> w) {}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * InheritPrimaryKey: true
	 * Define: true
	 */
	protected void _inheritPk(Wrap<String> w) {}

	/**
	 * {@inheritDoc}
	 * UniqueKey: true
	 */
	protected void _id(Wrap<String> w) {
		if(pk != null)
			w.o(getClass().getSimpleName() + "_" + pk.toString());
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
	protected void _created(Wrap<ZonedDateTime> w) {}

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
	protected void _modified(Wrap<ZonedDateTime> w) {
		w.o(ZonedDateTime.now(ZoneId.of(siteRequest_.getConfig().getString(ConfigKeys.SITE_ZONE))));
	}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 */ 
	protected void _classCanonicalName(Wrap<String> w) {
		w.o(getClass().getCanonicalName());
	}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 */ 
	protected void _classSimpleName(Wrap<String> w) {
		w.o(getClass().getSimpleName());
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
	protected void _objectTitle(Wrap<String> w) {
		if(pk != null)
			w.o(pk.toString());
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
}