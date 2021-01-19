package com.opendatapolicing.enus.searchbasis;

import java.time.ZonedDateTime;

import com.opendatapolicing.enus.cluster.Cluster;
import com.opendatapolicing.enus.search.SearchList;
import com.opendatapolicing.enus.trafficsearch.TrafficSearch;
import com.opendatapolicing.enus.wrap.Wrap;

/**
 * Model: true
 * Stored: true
 * Indexed: true
 * Saved: true

 * ApiUri.enUS: /api/search-basis
 * ApiTag.enUS: Search Basis

 * ApiMethod.enUS: PUTImport
 * ApiUriPUTImport.enUS: /api/search-basis/import

 * ApiMethod.enUS: PUTMerge
 * ApiUriPUTMerge.enUS: /api/search-basis/merge

 * ApiMethod.enUS: PUTCopy
 * ApiUriPUTCopy.enUS: /api/search-basis/copy

 * ApiMethod: POST

 * ApiMethod: PATCH

 * ApiMethod.enUS: GET
 * ApiUriGET.enUS: /api/search-basis/{id}

 * ApiMethod: Search

 * ApiMethod.enUS: AdminSearch
 * ApiUriAdminSearch.enUS: /api/admin/search-basis

 * ApiMethod.enUS: SearchPage
 * ApiUriSearchPage.enUS: /search-basis
 * PageSearchPage.enUS: SearchBasisPage
 * PageSuperSearchPage.enUS: SearchBasisPage

 * AName.enUS: a search basis
 * Color: pale-green
 * IconGroup: regular
 * IconName: newspaper
 * NameVar: searchBasis
 **/
public class SearchBasis extends SearchBasisGen<Cluster> {
	
	protected void _contrabandKey(Wrap<Long> c) {
		c.o(pk);
	}

	protected void _searchKey(Wrap<Long> w) {
	}

	protected void _trafficSearchSearch(SearchList<TrafficSearch> l) {
		l.setQuery("*:*");
		l.addFilterQuery("contrabandKeys_indexed_longs:" + pk);
		l.setC(TrafficSearch.class);
		l.setStore(true);
	}

	protected void _trafficSearch_(Wrap<TrafficSearch> w) {
		if(trafficSearchSearch.size() > 0) {
			w.o(trafficSearchSearch.get(0));
		}
	}

	protected void _stopAgencyTitle(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopAgencyTitle());
	}

	protected void _stopDateTime(Wrap<ZonedDateTime> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopDateTime());
	}

	protected void _stopPurposeNum(Wrap<Integer> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopPurposeNum());
	}

	protected void _stopPurposeTitle(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopPurposeTitle());
	}

	protected void _stopActionNum(Wrap<Integer> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopActionNum());
	}

	protected void _stopActionTitle(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopActionTitle());
	}

	protected void _stopDriverArrest(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopDriverArrest());
	}

	protected void _stopPassengerArrest(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopPassengerArrest());
	}

	protected void _stopEncounterForce(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopEncounterForce());
	}

	protected void _stopEngageForce(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopEngageForce());
	}

	protected void _stopOfficerInjury(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopOfficerInjury());
	}

	protected void _stopDriverInjury(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopDriverInjury());
	}

	protected void _stopPassengerInjury(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopPassengerInjury());
	}

	protected void _stopOfficerId(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopOfficerId());
	}

	protected void _stopLocationId(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopLocationId());
	}

	protected void _stopCityId(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopCityId());
	}

	protected void _personAge(Wrap<Integer> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonAge());
	}

	protected void _personTypeId(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonTypeId());
	}

	protected void _personTypeTitle(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonTypeTitle());
	}

	protected void _personTypeDriver(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonTypeDriver());
	}

	protected void _personTypePassenger(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonTypePassenger());
	}

	protected void _personGenderId(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonGenderId());
	}

	protected void _personGenderTitle(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonGenderTitle());
	}

	protected void _personGenderFemale(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonGenderFemale());
	}

	protected void _personGenderMale(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonGenderMale());
	}

	protected void _personEthnicityId(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonEthnicityId());
	}

	protected void _personEthnicityTitle(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonEthnicityTitle());
	}

	protected void _personRaceId(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonRaceId());
	}

	protected void _personRaceTitle(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonRaceTitle());
	}

	protected void _trafficStopKey(Wrap<Long> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getTrafficStopKey());
	}

	protected void _searchTypeNum(Wrap<Integer> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getSearchTypeNum());
	}

	protected void _searchTypeTitle(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getSearchTypeTitle());
	}

	protected void _searchVehicle(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getSearchVehicle());
	}

	protected void _searchDriver(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getSearchDriver());
	}

	protected void _searchPassenger(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getSearchPassenger());
	}

	protected void _searchProperty(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getSearchProperty());
	}

	protected void _searchVehicleSiezed(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getSearchVehicleSiezed());
	}

	protected void _searchPersonalPropertySiezed(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getSearchPersonalPropertySiezed());
	}

	protected void _searchOtherPropertySiezed(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getSearchOtherPropertySiezed());
	}

	protected void _searchBasisId(Wrap<String> w) {
	}

	protected void _searchBasisTitle(Wrap<String> w) {
		switch(searchBasisId) {
		case "ER":
			w.o("Erratic/Suspicious Behavior"); break;
		case "OB":
			w.o("Observation of Suspected Contraband"); break;
		case "OI":
			w.o("Other Official Information"); break;
		case "SM":
			w.o("Suspicious Movement"); break;
		case "TIP":
			w.o("Informant Tip"); break;
		case "WTNS":
			w.o("Witness Observation"); break;
		}
	}

	@Override
	public String strSearchBasisTitle() {
		return "basis of " + strSearchBasisTitle();
	}

	protected void _searchBasisShortName(Wrap<String> w) {
		StringBuilder b = new StringBuilder();
		if(searchBasisTitle != null)
			b.append(strSearchBasisTitle());
		w.o(b.toString());
	}

	protected void _searchBasisCompleteName(Wrap<String> c) {
		c.o(searchBasisShortName);
	}
}
