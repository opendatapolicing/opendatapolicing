package com.opendatapolicing.enus.trafficstop;

import java.time.ZonedDateTime;
import java.util.List;
import com.opendatapolicing.enus.agency.SiteAgency;
import com.opendatapolicing.enus.cluster.Cluster;
import com.opendatapolicing.enus.search.SearchList;
import com.opendatapolicing.enus.wrap.Wrap;

/**
 * Model: true
 * Stored: true
 * Indexed: true
 * Saved: true

 * ApiUri.enUS: /api/traffic-stop
 * ApiTag.enUS: Traffic Stop

 * ApiMethod: PUTImport.enUS
 * ApiUriPUTImport.enUS: /api/traffic-stop/import

 * ApiMethod: PUTMerge.enUS
 * ApiUriPUTMerge.enUS: /api/traffic-stop/merge

 * ApiMethod: PUTCopy.enUS
 * ApiUriPUTCopy.enUS: /api/traffic-stop/copy

 * ApiMethod: POST

 * ApiMethod: PATCH

 * ApiMethod: GET.enUS
 * ApiUriGET.enUS: /api/traffic-stop/{id}

 * ApiMethod: Search

 * ApiMethod: AdminSearch.enUS
 * ApiUriAdminSearch.enUS: /api/admin/traffic-stop

 * ApiMethod: SearchPage.enUS
 * ApiUriSearchPage.enUS: /traffic-stop
 * PageSearchPage.enUS: TrafficStopPage
 * PageSuperSearchPage.enUS: TrafficStopPage

 * AName.enUS: a traffic stop
 * Color: pale-green
 * IconGroup: regular
 * IconName: newspaper
 * NameVar: trafficStop
 **/

public class TrafficStop extends TrafficStopGen<Cluster> {
	
	protected void _trafficStopKey(Wrap<Long> c) {
		c.o(pk);
	}

	protected void _agencyKey(Wrap<Long> c) {
	}

	protected void _agencySearch(SearchList<SiteAgency> l) {
		if(agencyKey != null) {
			l.setQuery("*:*");
			l.addFilterQuery("pk_indexed_long:" + agencyKey);
			l.setC(SiteAgency.class);
			l.setStore(true);
		}
	}

	protected void _agency_(Wrap<SiteAgency> c) {
		if(agencySearch.size() > 0) {
			c.o(agencySearch.get(0));
		}
	}

	protected void _stopAgencyTitle(Wrap<String> w) {
	}

	protected void _stopDateTime(Wrap<ZonedDateTime> w) {
	}

	protected void _stopPurposeNum(Wrap<Integer> w) {
	}

	protected void _stopPurposeTitle(Wrap<String> w) {
		switch(stopPurposeNum) {
		case 1:
			w.o("Speed Limit Violation"); break;
		case 2:
			w.o("Stop Light/Sign Violation"); break;
		case 3:
			w.o("Driving While Impaired"); break;
		case 4:
			w.o("Safe Movement Violation"); break;
		case 5:
			w.o("Vehicle Equipment Violation"); break;
		case 6:
			w.o("Vehicle Regulatory Violation"); break;
		case 7:
			w.o("Seat Belt Violation"); break;
		case 8:
			w.o("Investigation"); break;
		case 9:
			w.o("Other Motor Vehicle Violation"); break;
		case 10:
			w.o("Checkpoint"); break;
		}
	}

	protected void _stopActionNum(Wrap<Integer> w) {
	}

	protected void _stopActionTitle(Wrap<String> w) {
		switch(stopActionNum) {
		case 1:
			w.o("Verbal Warning"); break;
		case 2:
			w.o("Written Warning"); break;
		case 3:
			w.o("Citation Issued"); break;
		case 4:
			w.o("On-View Arrest"); break;
		case 5:
			w.o("No Action Taken"); break;
		}
	}

	protected void _stopDriverArrest(Wrap<Boolean> w) {
	}

	protected void _stopPassengerArrest(Wrap<Boolean> w) {
	}

	protected void _stopEncounterForce(Wrap<Boolean> w) {
	}

	protected void _stopEngageForce(Wrap<Boolean> w) {
	}

	protected void _stopOfficerInjury(Wrap<Boolean> w) {
	}

	protected void _stopDriverInjury(Wrap<Boolean> w) {
	}

	protected void _stopPassengerInjury(Wrap<Boolean> w) {
	}

	protected void _stopOfficerId(Wrap<String> w) {
	}

	protected void _stopLocationId(Wrap<String> w) {
	}

	protected void _stopCityId(Wrap<String> w) {
	}

	protected void _personKeys(List<Long> c) {
	}

	protected void _trafficStopCompleteName(Wrap<String> c) {
		c.o(stopActionTitle + " during " + stopPurposeTitle + " traffic stop");
	}
}