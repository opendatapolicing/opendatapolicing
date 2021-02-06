package com.opendatapolicing.enus.agency;  

import java.time.format.DateTimeFormatter;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import com.opendatapolicing.enus.cluster.Cluster;
import com.opendatapolicing.enus.search.SearchList;
import com.opendatapolicing.enus.state.SiteState;
import com.opendatapolicing.enus.wrap.Wrap;

/**
 * Model: true
 * Api: true
 * Indexed: true
 * Saved: true
 * 
 * ApiTag.enUS: State
 * ApiUri.enUS: /api/agency
 * 
 * ApiMethod.enUS: PUTImport
 * ApiMethod.enUS: PUTMerge
 * ApiMethod.enUS: PUTCopy
 * ApiMethod: POST
 * ApiMethod: PATCH
 * ApiMethod: GET
 * ApiMethod.enUS: Search
 * 
 * ApiMethod.enUS: AdminSearch
 * ApiUriAdminSearch.enUS: /api/admin/agency
 * RoleUtilisateurAdminSearch.enUS: true
 * 
 * ApiMethod.enUS: SearchPage
 * PageSearchPage.enUS: SiteAgencyPage
 * PageSuperSearchPage.enUS: ClusterPage
 * ApiUriSearchPage.enUS: /agency
 * 
 * AName.enUS: a agency
 * Color: pale-yellow
 * IconGroup: regular
 * IconName: road
 * NameVar.enUS: agency
 * NamePlural.enUS: agencies
 * 
 * Role.frFR: SiteAdmin
 * Role.enUS: SiteAdmin
 * PublicRead: true
 * 
 * Sort.asc: agencyName
 * 
 * Rows: 300
 **/

public class SiteAgency extends SiteAgencyGen<Cluster> {
	
	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Description.enUS: The primary key of the agency in the database. 
	 */            
	protected void _agencyKey(Wrap<Long> c) {
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
	protected void _agencyName(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Ignore: true
	 */
	protected void _stateSearch(SearchList<SiteState> l) {
		l.setQuery("*:*");
		l.addFilterQuery("agencyKeys_indexed_longs:" + pk);
		l.setC(SiteState.class);
		l.setStore(true);
	}

	protected void _state_(Wrap<SiteState> c) {
		if(stateSearch.size() > 0) {
			c.o(stateSearch.get(0));
		}
	}

	/**  
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Attribute: SiteState.agencyKeys
	 * HtmlRow: 5
	 * HtmlCell: 1
	 * DisplayName.enUS: state
	 */         
	protected void _stateKey(Wrap<Long> c) {
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
		if(state_ != null)
			c.o(state_.getImageLeft());
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
		if(state_ != null)
			c.o(state_.getImageTop());
	}

	/**   
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 4
	 * HtmlCell: 3
	 * Multiline: true
	 * DisplayName.enUS: image map coordinates
	 */ 
	protected void _imageCoords(Wrap<String> c) {
	}

	/**   
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 */ 
	protected void _stateId(Wrap<String> c) {
		if(state_ != null)
			c.o(state_.getObjectId());
	}

	/**   
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 */ 
	protected void _stateName(Wrap<String> c) {
		if(state_ != null)
			c.o(state_.getStateName());
	}

	/**   
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 */ 
	protected void _stateAbbreviation(Wrap<String> c) {
		if(state_ != null)
			c.o(state_.getStateAbbreviation());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 */ 
	protected void _agencyOnlyName(Wrap<String> c) {
		if(StringUtils.equals(agencyName, stateName))
			c.o("A A A " + stateName);
		else
			c.o(agencyName + " in " + stateName + " (" + stateAbbreviation + ")");
	}

	/**   
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * VarH2: true
	 * VarTitle: true
	 */ 
	protected void _agencyCompleteName(Wrap<String> c) {
		if(StringUtils.equals(agencyName, stateName))
			c.o(stateName + " (" + stateAbbreviation + ")");
		else
			c.o(agencyName + " in " + stateName + " (" + stateAbbreviation + ")");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override() protected void  _objectTitle(Wrap<String> c) {
		if(StringUtils.equals(agencyName, stateName))
			c.o(stateName + " (" + stateAbbreviation + ")");
		else
			c.o(agencyName + " in " + stateName + " (" + stateAbbreviation + ")");
	}
}