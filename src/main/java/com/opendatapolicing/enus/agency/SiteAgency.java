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
	
	protected void _agencyKey(Wrap<Long> c) {
		c.o(pk);
	}

	protected void _agencyName(Wrap<String> c) {
	}

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

	protected void _stateKey(Wrap<Long> c) {
	}

	protected void _imageLeft(Wrap<Integer> c) {
		if(state_ != null)
			c.o(state_.getImageLeft());
	}

	protected void _imageTop(Wrap<Integer> c) {
		if(state_ != null)
			c.o(state_.getImageTop());
	}

	protected void _imageCoords(Wrap<String> c) {
	}

	protected void _stateId(Wrap<String> c) {
		if(state_ != null)
			c.o(state_.getObjectId());
	}

	protected void _stateName(Wrap<String> c) {
		if(state_ != null)
			c.o(state_.getStateName());
	}

	protected void _stateAbbreviation(Wrap<String> c) {
		if(state_ != null)
			c.o(state_.getStateAbbreviation());
	}

	protected void _agencyOnlyName(Wrap<String> c) {
		if(StringUtils.equals(agencyName, stateName))
			c.o("A A A " + stateName);
		else
			c.o(agencyName + " in " + stateName + " (" + stateAbbreviation + ")");
	}

	protected void _agencyCompleteName(Wrap<String> c) {
		if(StringUtils.equals(agencyName, stateName))
			c.o(stateName + " (" + stateAbbreviation + ")");
		else
			c.o(agencyName + " in " + stateName + " (" + stateAbbreviation + ")");
	}

	_objectTitle(Wrap<String> c) {
		if(StringUtils.equals(agencyName, stateName))
			c.o(stateName + " (" + stateAbbreviation + ")");
		else
			c.o(agencyName + " in " + stateName + " (" + stateAbbreviation + ")");
	}
}