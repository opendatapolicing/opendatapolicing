package com.opendatapolicing.enus.trafficperson;

import java.time.ZonedDateTime;
import java.util.List;
import com.opendatapolicing.enus.cluster.Cluster;
import com.opendatapolicing.enus.search.SearchList;
import com.opendatapolicing.enus.trafficstop.TrafficStop;
import com.opendatapolicing.enus.wrap.Wrap;

/**
 * Model: true
 * Stored: true
 * Indexed: true
 * Saved: true

 * ApiUri.enUS: /api/person
 * ApiTag.enUS: Person

 * ApiMethod: PUTImport.enUS
 * ApiUriPUTImport.enUS: /api/person/import

 * ApiMethod: PUTMerge.enUS
 * ApiUriPUTMerge.enUS: /api/person/merge

 * ApiMethod: PUTCopy.enUS
 * ApiUriPUTCopy.enUS: /api/person/copy

 * ApiMethod: POST

 * ApiMethod: PATCH

 * ApiMethod: GET.enUS
 * ApiUriGET.enUS: /api/person/{id}

 * ApiMethod: Search

 * ApiMethod: AdminSearch.enUS
 * ApiUriAdminSearch.enUS: /api/admin/person

 * ApiMethod: SearchPage.enUS
 * ApiUriSearchPage.enUS: /person
 * PageSearchPage.enUS: TrafficPersonPage
 * PageSuperSearchPage.enUS: TrafficPersonPage

 * AName.enUS: a person
 * Color: pale-green
 * IconGroup: regular
 * IconName: newspaper
 * NameVar: trafficStop
 **/

public class TrafficPerson extends TrafficPersonGen<Cluster> {
	
	protected void _personKey(Wrap<Long> c) {
		c.o(pk);
	}

	protected void _trafficStopKey(Wrap<Long> c) {
	}

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

	protected void _stopAgencyTitle(Wrap<String> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopAgencyTitle());
	}

	protected void _stopDateTime(Wrap<ZonedDateTime> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopDateTime());
	}

	protected void _stopPurposeNum(Wrap<Integer> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopPurposeNum());
	}

	protected void _stopPurposeTitle(Wrap<String> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopPurposeTitle());
	}

	protected void _stopActionNum(Wrap<Integer> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopActionNum());
	}

	protected void _stopActionTitle(Wrap<String> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopActionTitle());
	}

	protected void _stopDriverArrest(Wrap<Boolean> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopDriverArrest());
	}

	protected void _stopPassengerArrest(Wrap<Boolean> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopPassengerArrest());
	}

	protected void _stopEncounterForce(Wrap<Boolean> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopEncounterForce());
	}

	protected void _stopEngageForce(Wrap<Boolean> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopEngageForce());
	}

	protected void _stopOfficerInjury(Wrap<Boolean> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopOfficerInjury());
	}

	protected void _stopDriverInjury(Wrap<Boolean> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopDriverInjury());
	}

	protected void _stopPassengerInjury(Wrap<Boolean> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopPassengerInjury());
	}

	protected void _stopOfficerId(Wrap<String> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopOfficerId());
	}

	protected void _stopLocationId(Wrap<String> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopLocationId());
	}

	protected void _stopCityId(Wrap<String> w) {
		if(trafficStop_ != null)
			w.o(trafficStop_.getStopCityId());
	}

protected void _trafficSearchKeys(List<Long> c) {
	}

	protected void _personAge(Wrap<Integer> w) {
	}

	protected void _personTypeId(Wrap<String> w) {
	}

	protected void _personTypeTitle(Wrap<String> w) {
		switch(personTypeId) {
		case "D":
			w.o("Driver"); break;
		case "P":
			w.o("Passenger"); break;
		}
	}

	protected void _personTypeDriver(Wrap<Boolean> w) {
		switch(personTypeId) {
		case "D":
			w.o(true); break;
		default:
			w.o(false); break;
		}
	}

	protected void _personTypePassenger(Wrap<Boolean> w) {
		switch(personTypeId) {
		case "P":
			w.o(true); break;
		default:
			w.o(false); break;
		}
	}

	protected void _personGenderId(Wrap<String> w) {
	}

	protected void _personGenderTitle(Wrap<String> w) {
		switch(personGenderId) {
		case "M":
			w.o("Male"); break;
		case "F":
			w.o("Female"); break;
		}
	}

	protected void _personGenderFemale(Wrap<Boolean> w) {
		switch(personGenderId) {
		case "F":
			w.o(true); break;
		default:
			w.o(false); break;
		}
	}

	protected void _personGenderMale(Wrap<Boolean> w) {
		switch(personGenderId) {
		case "M":
			w.o(true); break;
		default:
			w.o(false); break;
		}
	}

	protected void _personEthnicityId(Wrap<String> w) {
	}

	protected void _personEthnicityTitle(Wrap<String> w) {
		switch(personEthnicityId) {
		case "H":
			w.o("Hispanic"); break;
		case "N":
			w.o("Non-Hispanic"); break;
		}
	}

	protected void _personRaceId(Wrap<String> w) {
	}

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