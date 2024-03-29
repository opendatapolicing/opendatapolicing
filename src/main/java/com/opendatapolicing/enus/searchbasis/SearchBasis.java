package com.opendatapolicing.enus.searchbasis;   

import java.time.ZonedDateTime;

import com.opendatapolicing.enus.base.BaseModel;
import com.opendatapolicing.enus.search.SearchList;
import com.opendatapolicing.enus.trafficsearch.TrafficSearch;
import com.opendatapolicing.enus.wrap.Wrap;

import io.vertx.core.Promise;

/**
 * Model: true
 * Api: true
 * Indexed: true
 * Saved: true
 * Map.Integer.sqlSort: 11
 * 
 * ApiTag.enUS: Search Basis
 * ApiUri.enUS: /api/search-basis
 * 
 * ApiMethod.enUS: PUTImport

 * ApiMethod: POST
 * ApiMethod: PATCH
 * ApiMethod: GET
 * ApiMethod.enUS: Search
 * 
 * ApiMethod.enUS: AdminSearch
 * ApiUriAdminSearch.enUS: /api/admin/search-basis
 * RoleUtilisateurAdminSearch.enUS: true
 * 
 * AName.enUS: a search basis
 * Color: pale-green
 * IconGroup: regular
 * IconName: newspaper
 * NameVar.enUS: searchBasis
 * NamePlural.enUS: search bases
 * 
 * Role.enUS: SiteService
 * PublicRead: true
 * 
 * Map.hackathonMission: to create a new Java class SearchBasis to define the SearchBasis Java class that collects search, search, and use-of-force police data publicly available to ensure transparency
 **/      
public class SearchBasis extends SearchBasisGen<BaseModel> {

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Description.enUS: The primary key of the search basis in the database. 
	 */           
	protected void _searchBasisKey(Wrap<Long> c) {
		c.o(pk);
	}

	/**  
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * DisplayName.enUS: search ID
	 */          
	protected void _searchId(Wrap<String> w) {
	}

	/**
	 * {@inheritDoc}
	 * Ignore: true
	 */ 
	protected void _trafficSearchSearch(Promise<SearchList<TrafficSearch>> promise) {
		SearchList<TrafficSearch> l = new SearchList<>();
		if(searchId != null) {
			l.setQuery("*:*");
			l.addFilterQuery("inheritPk_indexedstored_string:" + searchId);
			l.setC(TrafficSearch.class);
			l.setStore(true);
		}
		promise.complete(l);
	}

	protected void _trafficSearch_(Wrap<TrafficSearch> w) {
		if(trafficSearchSearch.size() > 0) {
			w.o(trafficSearchSearch.get(0));
		}
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: state abbreviation
	 */ 
	protected void _stateAbbreviation(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStateAbbreviation());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: agency title
	 */ 
	protected void _agencyTitle(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getAgencyTitle());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: stop date/time
	 */ 
	protected void _stopDateTime(Wrap<ZonedDateTime> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopDateTime());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: stop purpose number
	 */ 
	protected void _stopPurposeNum(Wrap<Integer> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopPurposeNum());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: stop purpose title
	 */ 
	protected void _stopPurposeTitle(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopPurposeTitle());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: stop action number
	 */ 
	protected void _stopActionNum(Wrap<Integer> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopActionNum());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: agency title
	 */ 
	protected void _stopActionTitle(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopActionTitle());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: driver arrest
	 */ 
	protected void _stopDriverArrest(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopDriverArrest());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: passenger arrest
	 */ 
	protected void _stopPassengerArrest(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopPassengerArrest());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: encounter force
	 */ 
	protected void _stopEncounterForce(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopEncounterForce());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: engage force
	 */ 
	protected void _stopEngageForce(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopEngageForce());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: officer injury
	 */ 
	protected void _stopOfficerInjury(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopOfficerInjury());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: driver injury
	 */ 
	protected void _stopDriverInjury(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopDriverInjury());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: passenger injury
	 */ 
	protected void _stopPassengerInjury(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopPassengerInjury());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: officer ID
	 */ 
	protected void _stopOfficerId(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopOfficerId());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: location ID
	 */ 
	protected void _stopLocationId(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopLocationId());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: city ID
	 */ 
	protected void _stopCityId(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopCityId());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: person age
	 */ 
	protected void _personAge(Wrap<Integer> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonAge());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: person type ID
	 */ 
	protected void _personTypeId(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonTypeId());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: person type title
	 */ 
	protected void _personTypeTitle(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonTypeTitle());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: person was driver
	 */ 
	protected void _personTypeDriver(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonTypeDriver());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: person was passenger
	 */ 
	protected void _personTypePassenger(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonTypePassenger());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: person gender ID
	 */ 
	protected void _personGenderId(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonGenderId());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: person gender title
	 */ 
	protected void _personGenderTitle(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonGenderTitle());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: person was female
	 */ 
	protected void _personGenderFemale(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonGenderFemale());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: person was male
	 */ 
	protected void _personGenderMale(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonGenderMale());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: person ethnicity ID
	 */ 
	protected void _personEthnicityId(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonEthnicityId());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: person ethnicity title
	 */ 
	protected void _personEthnicityTitle(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonEthnicityTitle());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: person race ID
	 */ 
	protected void _personRaceId(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonRaceId());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: person race title
	 */ 
	protected void _personRaceTitle(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getPersonRaceTitle());
	}

	/**  
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: traffic stop ID
	 */          
	protected void _stopId(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getStopId());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: search type number
	 */ 
	protected void _searchTypeNum(Wrap<Integer> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getSearchTypeNum());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: search type title
	 */ 
	protected void _searchTypeTitle(Wrap<String> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getSearchTypeTitle());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: vehicle search
	 */ 
	protected void _searchVehicle(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getSearchVehicle());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: driver search
	 */ 
	protected void _searchDriver(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getSearchDriver());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: passenger search
	 */ 
	protected void _searchPassenger(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getSearchPassenger());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: property search
	 */ 
	protected void _searchProperty(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getSearchProperty());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: vehicle siezed
	 */ 
	protected void _searchVehicleSiezed(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getSearchVehicleSiezed());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: personal property siezed
	 */ 
	protected void _searchPersonalPropertySiezed(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getSearchPersonalPropertySiezed());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: other property siezed
	 */ 
	protected void _searchOtherPropertySiezed(Wrap<Boolean> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getSearchOtherPropertySiezed());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * DisplayName.enUS: search basis ID
	 */ 
	protected void _searchBasisId(Wrap<String> w) {
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: search basis title
	 */ 
	protected void _searchBasisTitle(Wrap<String> w) {
		if(searchBasisId != null) {
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
	}
//
//	/**   
//	 * {@inheritDoc}
//	 * Indexed: true
//	 * Stored: true
//	 */ 
//	protected void _searchBasisShortName(Wrap<String> w) {
//		StringBuilder b = new StringBuilder();
//		if(searchBasisTitle != null)
//			b.append(strSearchBasisTitle());
//		w.o(b.toString());
//	}
//
//	/**   
//	 * {@inheritDoc}
//	 * Indexed: true
//	 * Stored: true
//	 * VarH2: true
//	 * VarTitle: true
//	 */ 
//	protected void _searchBasisCompleteName(Wrap<String> c) {
//		if(searchKey == null)
//			c.o(String.format("%s basis", searchBasisShortName));
//		else
//			c.o(String.format("%s basis of search %s", searchBasisShortName, searchKey));
//	}
//
//	@Override
//	protected void _objectTitle(Wrap<String> c) {
//		c.o(searchBasisCompleteName);
//	}
}
