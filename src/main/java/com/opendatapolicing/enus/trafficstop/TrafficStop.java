package com.opendatapolicing.enus.trafficstop;                                                

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.common.SolrInputDocument;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.opendatapolicing.enus.agency.SiteAgency;
import com.opendatapolicing.enus.cluster.Cluster;
import com.opendatapolicing.enus.search.SearchList;
import com.opendatapolicing.enus.state.SiteState;
import com.opendatapolicing.enus.trafficperson.TrafficPerson;
import com.opendatapolicing.enus.trafficsearch.TrafficSearch;
import com.opendatapolicing.enus.wrap.Wrap;

import io.vertx.core.Promise;

/**
 * Model: true
 * Api: true
 * Indexed: true
 * Saved: true
 * Map.Integer.sqlSort: 7
 * 
 * ApiTag.enUS: Traffic Stop
 * ApiUri.enUS: /api/traffic-stop
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
 * ApiUriAdminSearch.enUS: /api/admin/traffic-stop
 * RoleUtilisateurAdminSearch.enUS: true
 * 
 * AName.enUS: a traffic stop
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
public class TrafficStop extends TrafficStopGen<Cluster> {

	////////////////
	// SiteState //
	////////////////


	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 */
	protected void _stateAbbreviation(Wrap<String> w) {
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 */
	protected void _stateName(Wrap<String> w) {
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 4
	 * HtmlCell: 1
	 * DisplayName.enUS: agency title
	 */
	protected void _agencyTitle(Wrap<String> w) {
	}

	////////////
	// Fields //
	////////////

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 4
	 * HtmlCell: 2
	 * DisplayName.enUS: stop date/time
	 */ 
	protected void _stopDateTime(Wrap<ZonedDateTime> w) {
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * DisplayName.enUS: stop year
	 */ 
	protected void _stopYear(Wrap<Integer> w) {
		if(stopDateTime != null)
			w.o(stopDateTime.getYear());
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * DisplayName.enUS: stop purpose number
	 */ 
	protected void _stopPurposeNum(Wrap<Integer> w) {
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * HtmlRow: 4
	 * HtmlCell: 3
	 * DisplayName.enUS: stop purpose title
	 */ 
	protected void _stopPurposeTitle(Wrap<String> w) {
		if(stopPurposeNum != null) {
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
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * DisplayName.enUS: stop action number
	 */ 
	protected void _stopActionNum(Wrap<Integer> w) {
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * HtmlRow: 4
	 * HtmlCell: 4
	 * DisplayName.enUS: agency title
	 */ 
	protected void _stopActionTitle(Wrap<String> w) {
		if(stopActionNum != null) {
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
	}

	/**  
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 5
	 * HtmlCell: 1
	 * DisplayName.enUS: driver arrest
	 */ 
	protected void _stopDriverArrest(Wrap<Boolean> w) {
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 5
	 * HtmlCell: 2
	 * DisplayName.enUS: passenger arrest
	 */ 
	protected void _stopPassengerArrest(Wrap<Boolean> w) {
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 5
	 * HtmlCell: 3
	 * DisplayName.enUS: encounter force
	 */ 
	protected void _stopEncounterForce(Wrap<Boolean> w) {
	}

	/**   
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 5
	 * HtmlCell: 4
	 * DisplayName.enUS: engage force
	 */ 
	protected void _stopEngageForce(Wrap<Boolean> w) {
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 6
	 * HtmlCell: 1
	 * DisplayName.enUS: officer injury
	 */ 
	protected void _stopOfficerInjury(Wrap<Boolean> w) {
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 6
	 * HtmlCell: 2
	 * DisplayName.enUS: driver injury
	 */ 
	protected void _stopDriverInjury(Wrap<Boolean> w) {
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 6
	 * HtmlCell: 3
	 * DisplayName.enUS: passenger injury
	 */ 
	protected void _stopPassengerInjury(Wrap<Boolean> w) {
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 6
	 * HtmlCell: 4
	 * DisplayName.enUS: officer ID
	 * SetTrim: true
	 */ 
	protected void _stopOfficerId(Wrap<String> w) {
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 7
	 * HtmlCell: 1
	 * DisplayName.enUS: location ID
	 * SetTrim: true
	 */ 
	protected void _stopLocationId(Wrap<String> w) {
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 7
	 * HtmlCell: 2
	 * DisplayName.enUS: city ID
	 * SetTrim: true
	 */ 
	protected void _stopCityId(Wrap<String> w) {
	}

	/** 
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Attribute: TrafficPerson.trafficStopKey
	 * HtmlRow: 7
	 * HtmlCell: 3
	 * DisplayName.enUS: people
	 */ 
	protected void _personKeys(List<Long> c) {
	}

	/**
	 * {@inheritDoc}
	 * Ignore: true
	 */ 
	protected void _personSearch(Promise<SearchList<TrafficPerson>> promise) {
		SearchList<TrafficPerson> l = new SearchList<>();
		l.setQuery("*:*");
		l.addFilterQuery("stopId_indexed_string:" + inheritPk);
		l.setC(TrafficPerson.class);
		l.setRows(0);
		l.addFacetField("personRaceTitle_indexed_string");
		l.setStore(true);
		promise.complete(l);
	}

	/** 
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 */
	protected void _personRaceTitles(List<String> l) {
		FacetField field = personSearch.getQueryResponse().getFacetField("personRaceTitle_indexed_string");
		if(field != null) {
			for(Count count : field.getValues()) {
				if(count.getCount() > 0)
					l.add(count.getName());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * Ignore: true
	 */ 
	protected void _trafficSearchSearch(Promise<SearchList<TrafficSearch>> promise) { 
		SearchList<TrafficSearch> l = new SearchList<>();
		l.setQuery("*:*");
		l.addFilterQuery("stopId_indexed_string:" + inheritPk);
		l.setC(TrafficSearch.class);
		l.setRows(0);
		l.addFacetField("personRaceTitle_indexed_string");
		l.setStore(true);
		promise.complete(l);
	}

	/** 
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 */
	protected void _trafficSearchRaceTitles(List<String> l) {
		FacetField field = trafficSearchSearch.getQueryResponse().getFacetField("personRaceTitle_indexed_string");
		if(field != null) {
			for(Count count : field.getValues()) {
				if(count.getCount() > 0)
					l.add(count.getName());
			}
		}
	}
//
//	/**   
//	 * {@inheritDoc}
//	 * Indexed: true
//	 * Stored: true
//	 * VarH2: true
//	 * VarTitle: true
//	 */ 
//	protected void _trafficStopCompleteName(Wrap<String> c) {                        
//		StringBuilder b = new StringBuilder();
//		b.append(strStopDateTime());
//		if(stopPurposeTitle != null)
//			b.append(" ").append(stopPurposeTitle.toLowerCase());
//		if(stopActionTitle != null)
//			b.append(" with ").append(stopActionTitle.toLowerCase());
//		if(stopDriverArrest)
//			b.append(", driver arrested");
//		if(stopPassengerArrest)
//			b.append(", passenger arrested");
//		if(stopEncounterForce)
//			b.append(", force encountered");
//		if(stopEngageForce)
//			b.append(", force engaged");
//		if(stopOfficerInjury)
//			b.append(", officer injured");
//		if(stopDriverInjury)
//			b.append(", driver injured");
//		if(stopPassengerInjury)
//			b.append(", passenger injured");
//		c.o(b.toString());
//	}

	public class Serializer extends JsonSerializer<TrafficStop> {
	
		@Override()
		public void  serialize(TrafficStop o, JsonGenerator generator, SerializerProvider provider) throws IOException, IOException {
			SolrInputDocument document = new SolrInputDocument();
			o.indexTrafficStop(document);
			generator.writeString(document.jsonStr());
		}
	}
}

