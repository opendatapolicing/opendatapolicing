package com.opendatapolicing.enus.state;                           

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.opendatapolicing.enus.base.BaseModel;
import com.opendatapolicing.enus.wrap.Wrap;

/**
 * Model: true
 * Api: true
 * Indexed: true
 * Saved: true
 * Map.Integer.sqlSort: 5
 * 
 * ApiTag.enUS: State
 * ApiUri.enUS: /api/state
 * 
 * ApiMethod.enUS: PUTImport

 * ApiMethod: POST
 * ApiMethod: PATCH
 * ApiMethod: GET
 * ApiMethod.enUS: Search
 * 
 * ApiMethod.enUS: AdminSearch
 * ApiUriAdminSearch.enUS: /api/admin/state
 * RoleUtilisateurAdminSearch.enUS: true
 * 
 * AName.enUS: a state
 * Color: pale-blue
 * IconGroup: regular
 * IconName: globe-americas
 * NameVar.enUS: state
 * 
 * Role.frFR: SiteAdmin
 * Role.enUS: SiteAdmin
 * PublicRead: true
 * 
 * Sort.asc: stateName
 * 
 * Rows: 100
**/    
public class SiteState extends SiteStateGen<BaseModel> {

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Description.enUS: The primary key of the state in the database. 
	 */            
	protected void _stateKey(Wrap<Long> c) {
		c.o(pk);
	}

	/**   
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 3
	 * HtmlCell: 1
	 * DisplayName.enUS: name
	 */  
	protected void _stateName(Wrap<String> c) {
	}

	/**   
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 3
	 * HtmlCell: 2
	 * DisplayName.enUS: abbreviation
	 */ 
	protected void _stateAbbreviation(Wrap<String> c) {
	}

	/**   
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 3
	 * HtmlCell: 3
	 * DisplayName.enUS: ACS ID
	 */ 
	protected void _stateAcsId(Wrap<String> c) {
	}

	/**   
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 4
	 * HtmlCell: 1
	 * DisplayName.enUS: image left pixels
	 */ 
	protected void _imageLeft(Wrap<Integer> c) {
	}

	/**   
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 4
	 * HtmlCell: 2
	 * DisplayName.enUS: image top pixels
	 */ 
	protected void _imageTop(Wrap<Integer> c) {
	}

	/**  
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Attribute: SiteAgency.stateKey
	 * HtmlRow: 5
	 * HtmlCell: 1
	 * DisplayName.enUS: agencies
	 */         
	protected void _agencyKeys(List<Long> o) {}

	/**   
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * VarH2: true
	 * VarTitle: true
	 */ 
	protected void _stateCompleteName(Wrap<String> c) {
		c.o(stateName + " (" + stateAbbreviation + ")");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override() protected void _objectTitle(Wrap<String> c) {
		c.o(stateName + " (" + stateAbbreviation + ")");
	}

	@Override protected void _objectId(Wrap<String> c) {
		if(StringUtils.isNotBlank(stateAbbreviation))
			c.o(StringUtils.lowerCase(stateAbbreviation));
		else
			c.o(pk.toString());
	}
}

