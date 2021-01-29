package com.opendatapolicing.enus.trafficperson;  

import java.time.ZonedDateTime;
import java.util.List;

import com.opendatapolicing.enus.cluster.Cluster;
import com.opendatapolicing.enus.search.SearchList;
import com.opendatapolicing.enus.trafficstop.TrafficStop;
import com.opendatapolicing.enus.wrap.Wrap;

/**
 * Model: true
 * Api: true
 * Indexed: true
 * Saved: true
 * 
 * ApiTag.enUS: Person
 * ApiUri.enUS: /api/person
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
 * ApiUriAdminSearch.enUS: /api/admin/person
 * RoleUtilisateurAdminSearch.enUS: true
 * 
 * ApiMethod.enUS: SearchPage
 * PageSearchPage.enUS: TrafficPersonPage
 * PageSuperSearchPage.enUS: ClusterPage
 * ApiUriSearchPage.enUS: /person
 * 
 * AName.enUS: a person
 * NamePlural.enUS: people
 * Color: pale-green
 * IconGroup: regular
 * IconName: newspaper
 * NameVar.enUS: trafficStop
 * 
 * Role.enUS: SiteService
 * PublicRead: true
 * 
 * Map.hackathonMission: to create a new Java class TrafficStop to define the TrafficStop Java class that collects stop, search, and use-of-force police data publicly available to ensure transparency
 **/     
public class TrafficPerson extends TrafficPersonGen<Cluster> {

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Description.enUS: The primary key of the person in the database. 
	 */           
	protected void _personKey(Wrap<Long> c) {
		c.o(pk);
	}

	/**  
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Attribute: TrafficStop.personKeys
	 * HtmlRow: 4
	 * HtmlCell: 1
	 * DisplayName.enUS: traffic stop key
	 */          
	protected void _trafficStopKey(Wrap<Long> c) {
	}

	/**
	 * {@inheritDoc}
	 * Ignore: true
	 */ 
	protected void _trafficStopSearch(SearchList<TrafficStop> l) {
		l.setQuery("*:*");
		l.addFilterQuery("personKeys_indexed_longs:" + pk);
		l.setC(TrafficStop.class);
		l.setStore(true);
	}

	protected void _trafficStop_(Wrap<TrafficStop> c) {
		if(trafficStopSearch.size() > 0) {
			c.o(trafficStopSearch.get(0));
		}
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 3
	 * HtmlCell: 1
	 * DisplayName.enUS: agency title
	 */ 
	protected void _stopAgencyTitle(Wrap<String> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopAgencyTitle());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 3
	 * HtmlCell: 1
	 * DisplayName.enUS: stop date/time
	 */ 
	protected void _stopDateTime(Wrap<ZonedDateTime> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopDateTime());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * DisplayName.enUS: stop purpose number
	 */ 
	protected void _stopPurposeNum(Wrap<Integer> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopPurposeNum());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 3
	 * HtmlCell: 1
	 * DisplayName.enUS: stop purpose title
	 */ 
	protected void _stopPurposeTitle(Wrap<String> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopPurposeTitle());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * DisplayName.enUS: stop action number
	 */ 
	protected void _stopActionNum(Wrap<Integer> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopActionNum());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 3
	 * HtmlCell: 1
	 * DisplayName.enUS: agency title
	 */ 
	protected void _stopActionTitle(Wrap<String> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopActionTitle());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 3
	 * HtmlCell: 1
	 * DisplayName.enUS: driver arrest
	 */ 
	protected void _stopDriverArrest(Wrap<Boolean> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopDriverArrest());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 3
	 * HtmlCell: 1
	 * DisplayName.enUS: passenger arrest
	 */ 
	protected void _stopPassengerArrest(Wrap<Boolean> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopPassengerArrest());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 3
	 * HtmlCell: 1
	 * DisplayName.enUS: encounter force
	 */ 
	protected void _stopEncounterForce(Wrap<Boolean> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopEncounterForce());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 3
	 * HtmlCell: 1
	 * DisplayName.enUS: engage force
	 */ 
	protected void _stopEngageForce(Wrap<Boolean> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopEngageForce());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 3
	 * HtmlCell: 1
	 * DisplayName.enUS: officer injury
	 */ 
	protected void _stopOfficerInjury(Wrap<Boolean> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopOfficerInjury());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 3
	 * HtmlCell: 1
	 * DisplayName.enUS: driver injury
	 */ 
	protected void _stopDriverInjury(Wrap<Boolean> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopDriverInjury());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 3
	 * HtmlCell: 1
	 * DisplayName.enUS: passenger injury
	 */ 
	protected void _stopPassengerInjury(Wrap<Boolean> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopPassengerInjury());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 3
	 * HtmlCell: 1
	 * DisplayName.enUS: officer ID
	 */ 
	protected void _stopOfficerId(Wrap<String> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopOfficerId());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 3
	 * HtmlCell: 1
	 * DisplayName.enUS: location ID
	 */ 
	protected void _stopLocationId(Wrap<String> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopLocationId());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 3
	 * HtmlCell: 1
	 * DisplayName.enUS: city ID
	 */ 
	protected void _stopCityId(Wrap<String> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopCityId());
	}

	/**  
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Attribute: TrafficSearch.personKey
	 * HtmlRow: 4
	 * HtmlCell: 1
	 * DisplayName.enUS: traffic search keys
	 */          
	protected void _trafficSearchKeys(List<Long> c) {
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * HtmlRow: 3
	 * HtmlCell: 1
	 * DisplayName.enUS: person age
	 */ 
	protected void _personAge(Wrap<Integer> w) {
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * DisplayName.enUS: person type ID
	 */ 
	protected void _personTypeId(Wrap<String> w) {
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * HtmlRow: 3
	 * HtmlCell: 1
	 * DisplayName.enUS: person type title
	 */ 
	protected void _personTypeTitle(Wrap<String> w) {
		switch(personTypeId) {
		case "D":
			w.o("Driver"); break;
		case "P":
			w.o("Passenger"); break;
		}
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: person was driver
	 */ 
	protected void _personTypeDriver(Wrap<Boolean> w) {
		switch(personTypeId) {
		case "D":
			w.o(true); break;
		default:
			w.o(false); break;
		}
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: person was passenger
	 */ 
	protected void _personTypePassenger(Wrap<Boolean> w) {
		switch(personTypeId) {
		case "P":
			w.o(true); break;
		default:
			w.o(false); break;
		}
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * DisplayName.enUS: person gender ID
	 */ 
	protected void _personGenderId(Wrap<String> w) {
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * HtmlRow: 3
	 * HtmlCell: 1
	 * DisplayName.enUS: person gender title
	 */ 
	protected void _personGenderTitle(Wrap<String> w) {
		switch(personGenderId) {
		case "M":
			w.o("Male"); break;
		case "F":
			w.o("Female"); break;
		}
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: person was female
	 */ 
	protected void _personGenderFemale(Wrap<Boolean> w) {
		switch(personGenderId) {
		case "F":
			w.o(true); break;
		default:
			w.o(false); break;
		}
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * DisplayName.enUS: person was male
	 */ 
	protected void _personGenderMale(Wrap<Boolean> w) {
		switch(personGenderId) {
		case "M":
			w.o(true); break;
		default:
			w.o(false); break;
		}
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * DisplayName.enUS: person ethnicity ID
	 */ 
	protected void _personEthnicityId(Wrap<String> w) {
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * HtmlRow: 3
	 * HtmlCell: 1
	 * DisplayName.enUS: person ethnicity title
	 */ 
	protected void _personEthnicityTitle(Wrap<String> w) {
		switch(personEthnicityId) {
		case "H":
			w.o("Hispanic"); break;
		case "N":
			w.o("Non-Hispanic"); break;
		}
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * DisplayName.enUS: person race ID
	 */ 
	protected void _personRaceId(Wrap<String> w) {
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * HtmlRow: 3
	 * HtmlCell: 1
	 * DisplayName.enUS: person race title
	 */ 
	protected void _personRaceTitle(Wrap<String> w) {
		switch(personRaceId) {
		case "A":
			w.o("Asian"); break;
		case "B":
			w.o("Black"); break;
		case "I":
			w.o("Indigenous"); break;
		case "U":
			w.o("Other"); break;
		case "W":
			w.o("White"); break;
		}
	}
}
