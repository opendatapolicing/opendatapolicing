package com.opendatapolicing.enus.agency;                 

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.util.ClientUtils;

import com.opendatapolicing.enus.base.BaseModel;
import com.opendatapolicing.enus.search.SearchList;
import com.opendatapolicing.enus.state.SiteState;
import com.opendatapolicing.enus.wrap.Wrap;

import io.vertx.core.Promise;

/**
 * Model: true
 * Api: true
 * Indexed: true
 * Saved: true
 * Map.Integer.sqlSort: 6
 * 
 * ApiTag.enUS: State
 * ApiUri.enUS: /api/agency
 * 
 * ApiMethod.enUS: PUTImport
 * ApiMethod: POST
 * ApiMethod: PATCH
 * ApiMethod: GET
 * ApiMethod.enUS: Search
 * 
 * ApiMethod.enUS: AdminSearch
 * ApiUriAdminSearch.enUS: /api/admin/agency
 * RoleUtilisateurAdminSearch.enUS: true
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
 * Sort.asc: agencyTitle
 * 
 * Rows: 300
 **/
public class SiteAgency extends SiteAgencyGen<BaseModel> {

	/**   
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 */ 
	protected void _stateAbbreviation(Wrap<String> c) {
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
	protected void _agencyTitle(Wrap<String> c) {
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
	protected void _agencyAcsId(Wrap<String> c) {
	}

	/**   
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 */ 
	protected void _agencyTotal(Wrap<Long> c) {
	}

	/**   
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 */ 
	protected void _agencyTotalWhite(Wrap<Long> c) {
	}

	/**   
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 */ 
	protected void _agencyTotalBlack(Wrap<Long> c) {
	}

	/**   
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 */ 
	protected void _agencyTotalIndigenous(Wrap<Long> c) {
	}

	/**   
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 */ 
	protected void _agencyTotalAsian(Wrap<Long> c) {
	}

	/**   
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 */ 
	protected void _agencyTotalPacificIslander(Wrap<Long> c) {
	}

	/**   
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 */ 
	protected void _agencyTotalLatinx(Wrap<Long> c) {
	}

	/**   
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 */ 
	protected void _agencyTotalMultiracial(Wrap<Long> c) {
	}

	/**   
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 */ 
	protected void _agencyTotalOther(Wrap<Long> c) {
	}

	/**
	 * {@inheritDoc}
	 * Ignore: true
	 */
	protected void _stateSearch(Promise<SearchList<SiteState>> promise) {
		SearchList<SiteState> l = new SearchList<>();
		if(stateAbbreviation != null) {
			l.setQuery("*:*");
			l.addFilterQuery("stateAbbreviation_indexedstored_string:" + ClientUtils.escapeQueryChars(stateAbbreviation));
			l.setC(SiteState.class);
			l.setStore(true);
		}
		promise.complete(l);
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
	 * Suggested: true
	 */    
	protected void _objectSuggest(Wrap<String> c) { 
		c.o(agencyTitle);
	}

	@Override
	protected void _objectTitle(Wrap<String> c) {
		c.o(String.format("%s (%s)", agencyTitle, stateAbbreviation));
	}
}