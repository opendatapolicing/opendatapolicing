package com.opendatapolicing.enus.trafficsearch;

import java.time.ZonedDateTime;
import java.util.List;
import com.opendatapolicing.enus.cluster.Cluster;
import com.opendatapolicing.enus.search.SearchList;
import com.opendatapolicing.enus.trafficperson.TrafficPerson;
import com.opendatapolicing.enus.wrap.Wrap;

/**
 * Model: true
 * Stored: true
 * Indexed: true
 * Saved: true

 * ApiUri.enUS: /api/traffic-search
 * ApiTag.enUS: Traffic Search

 * ApiMethod: PUTImport.enUS
 * ApiUriPUTImport.enUS: /api/traffic-search/import

 * ApiMethod: PUTMerge.enUS
 * ApiUriPUTMerge.enUS: /api/traffic-search/merge

 * ApiMethod: PUTCopy.enUS
 * ApiUriPUTCopy.enUS: /api/traffic-search/copy

 * ApiMethod: POST

 * ApiMethod: PATCH

 * ApiMethod: GET.enUS
 * ApiUriGET.enUS: /api/traffic-search/{id}

 * ApiMethod: Search

 * ApiMethod: AdminSearch.enUS
 * ApiUriAdminSearch.enUS: /api/admin/traffic-search

 * ApiMethod: SearchPage.enUS
 * ApiUriSearchPage.enUS: /traffic-search
 * PageSearchPage.enUS: TrafficSearchPage
 * PageSuperSearchPage.enUS: TrafficSearchPage

 * AName.enUS: a traffic search
 * Color: pale-green
 * IconGroup: regular
 * IconName: newspaper
 * NameVar: trafficSearch

 * Rows: 100
 **/

public class TrafficSearch extends TrafficSearchGen<Cluster> {
	
	protected void _trafficSearchKey(Wrap<Long> c) {
		c.o(pk);
	}

	protected void _personKey(Wrap<Long> w) {
	}

	protected void _trafficPersonSearch(SearchList<TrafficPerson> l) {
		l.setQuery("*:*");
		l.addFilterQuery("trafficSearchKeys_indexed_longs:" + pk);
		l.setC(TrafficPerson.class);
		l.setStore(true);
	}

	protected void _trafficPerson_(Wrap<TrafficPerson> w) {
		if(trafficPersonSearch.size() > 0) {
			w.o(trafficPersonSearch.get(0));
		}
	}

	protected void _contrabandKeys(List<Long> w) {
	}

	protected void _searchBasisKeys(List<Long> w) {
	}

	protected void _stopAgencyTitle(Wrap<String> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getStopAgencyTitle());
	}

	protected void _stopDateTime(Wrap<ZonedDateTime> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getStopDateTime());
	}

	protected void _stopPurposeNum(Wrap<Integer> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getStopPurposeNum());
	}

	protected void _stopPurposeTitle(Wrap<String> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getStopPurposeTitle());
	}

	protected void _stopActionNum(Wrap<Integer> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getStopActionNum());
	}

	protected void _stopActionTitle(Wrap<String> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getStopActionTitle());
	}

	protected void _stopDriverArrest(Wrap<Boolean> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getStopDriverArrest());
	}

	protected void _stopPassengerArrest(Wrap<Boolean> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getStopPassengerArrest());
	}

	protected void _stopEncounterForce(Wrap<Boolean> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getStopEncounterForce());
	}

	protected void _stopEngageForce(Wrap<Boolean> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getStopEngageForce());
	}

	protected void _stopOfficerInjury(Wrap<Boolean> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getStopOfficerInjury());
	}

	protected void _stopDriverInjury(Wrap<Boolean> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getStopDriverInjury());
	}

	protected void _stopPassengerInjury(Wrap<Boolean> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getStopPassengerInjury());
	}

	protected void _stopOfficerId(Wrap<String> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getStopOfficerId());
	}

	protected void _stopLocationId(Wrap<String> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getStopLocationId());
	}

	protected void _stopCityId(Wrap<String> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getStopCityId());
	}

	protected void _personAge(Wrap<Integer> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getPersonAge());
	}

	protected void _personTypeId(Wrap<String> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getPersonTypeId());
	}

	protected void _personTypeTitle(Wrap<String> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getPersonTypeTitle());
	}

	protected void _personTypeDriver(Wrap<Boolean> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getPersonTypeDriver());
	}

	protected void _personTypePassenger(Wrap<Boolean> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getPersonTypePassenger());
	}

	protected void _personGenderId(Wrap<String> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getPersonGenderId());
	}

	protected void _personGenderTitle(Wrap<String> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getPersonGenderTitle());
	}

	protected void _personGenderFemale(Wrap<Boolean> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getPersonGenderFemale());
	}

	protected void _personGenderMale(Wrap<Boolean> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getPersonGenderMale());
	}

	protected void _personEthnicityId(Wrap<String> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getPersonEthnicityId());
	}

	protected void _personEthnicityTitle(Wrap<String> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getPersonEthnicityTitle());
	}

	protected void _personRaceId(Wrap<String> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getPersonRaceId());
	}

	protected void _personRaceTitle(Wrap<String> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getPersonRaceTitle());
	}

	protected void _trafficStopKey(Wrap<Long> w) {
		if(trafficPerson_ != null)
			w.o(trafficPerson_.getTrafficStopKey());
	}

	protected void _searchTypeNum(Wrap<Integer> w) {
	}

	protected void _searchTypeTitle(Wrap<String> w) {
		switch(searchTypeNum) {
		case 1:
			w.o("Consent"); break;
		case 2:
			w.o("Search Warrant"); break;
		case 3:
			w.o("Probable Cause"); break;
		case 4:
			w.o("Search Incident to Arrest"); break;
		case 5:
			w.o("Protective Frisk"); break;
		}
	}

	protected void _searchVehicle(Wrap<Boolean> w) {
	}

	protected void _searchDriver(Wrap<Boolean> w) {
	}

	protected void _searchPassenger(Wrap<Boolean> w) {
	}

	protected void _searchProperty(Wrap<Boolean> w) {
	}

	protected void _searchVehicleSiezed(Wrap<Boolean> w) {
	}

	protected void _searchPersonalPropertySiezed(Wrap<Boolean> w) {
	}

	protected void _searchOtherPropertySiezed(Wrap<Boolean> w) {
	}

	protected void _trafficSearchCompleteName(Wrap<String> c) {
		c.o(stopActionTitle + " during " + stopPurposeTitle + " traffic search");
	}
}