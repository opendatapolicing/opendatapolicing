package com.opendatapolicing.enus.trafficcontraband;    

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.opendatapolicing.enus.cluster.Cluster;
import com.opendatapolicing.enus.search.SearchList;
import com.opendatapolicing.enus.trafficsearch.TrafficSearch;
import com.opendatapolicing.enus.wrap.Wrap;

import io.vertx.core.Promise;

/**
 * Model: true
 * Api: true
 * Indexed: true
 * Saved: true
 * Map.Integer.sqlSort: 10
 * 
 * ApiTag.enUS: Contraband
 * ApiUri.enUS: /api/contraband
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
 * ApiUriAdminSearch.enUS: /api/admin/contraband
 * RoleUtilisateurAdminSearch.enUS: true
 * 
 * AName.enUS: a contraband
 * Color: pale-green
 * IconGroup: regular
 * IconName: newspaper
 * NameVar.enUS: contraband
 * 
 * Role.enUS: SiteService
 * PublicRead: true
 * 
 * Map.hackathonMission: to create a new Java class TrafficContraband to define the TrafficContraband Java class that collects search, search, and use-of-force police data publicly available to ensure transparency
 **/   
public class TrafficContraband extends TrafficContrabandGen<Cluster> {

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Description.enUS: The primary key of the contraband in the database. 
	 */           
	protected void _contrabandKey(Wrap<Long> c) {
		c.o(pk);
	}

	/**  
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Attribute: TrafficSearch.contrabandKeys
	 * HtmlRow: 4
	 * HtmlCell: 1
	 * DisplayName.enUS: search key
	 */          
	protected void _searchKey(Wrap<Long> w) {
	}

	/**
	 * {@inheritDoc}
	 * Ignore: true
	 */ 
	protected void _trafficSearchSearch(Promise<SearchList<TrafficSearch>> promise) {
		if(searchKey != null) {
			SearchList<TrafficSearch> l = new SearchList<>();
			l.setQuery("*:*");
			l.addFilterQuery("pk_indexed_long:" + searchKey);
			l.setC(TrafficSearch.class);
			l.setStore(true);
			promise.complete(l);
		}
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
	 * DisplayName.enUS: traffic stop key
	 */          
	protected void _trafficStopKey(Wrap<Long> w) {
		if(trafficSearch_ != null)
			w.o(trafficSearch_.getTrafficStopKey());
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
	 * HtmlRow: 5
	 * HtmlCell: 1
	 * DisplayName.enUS: ounces
	 */ 
	protected void _contrabandOunces(Wrap<BigDecimal> w) {
	}
	@Override public String strContrabandOunces() {
		return super.strContrabandOunces() + " oz";
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 5
	 * HtmlCell: 2
	 * DisplayName.enUS: pounds
	 */ 
	protected void _contrabandPounds(Wrap<BigDecimal> w) {
	}
	@Override public String strContrabandPounds() {
		return super.strContrabandPounds() + " lbs";
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 5
	 * HtmlCell: 3
	 * DisplayName.enUS: pints
	 */ 
	protected void _contrabandPints(Wrap<BigDecimal> w) {
	}
	@Override public String strContrabandPints() {
		return super.strContrabandPints() + " pints";
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 5
	 * HtmlCell: 4
	 * DisplayName.enUS: gallons
	 */ 
	protected void _contrabandGallons(Wrap<BigDecimal> w) {
	}
	@Override public String strContrabandGallons() {
		return super.strContrabandGallons() + " gal";
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 6
	 * HtmlCell: 1
	 * DisplayName.enUS: dosages
	 */ 
	protected void _contrabandDosages(Wrap<BigDecimal> w) {
	}
	@Override public String strContrabandDosages() {
		return super.strContrabandDosages() + " doses";
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 6
	 * HtmlCell: 2
	 * DisplayName.enUS: grams
	 */ 
	protected void _contrabandGrams(Wrap<BigDecimal> w) {
	}
	@Override public String strContrabandGrams() {
		return super.strContrabandGrams() + " g";
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 6
	 * HtmlCell: 3
	 * DisplayName.enUS: kilos
	 */ 
	protected void _contrabandKilos(Wrap<BigDecimal> w) {
	}
	@Override public String strContrabandKilos() {
		return super.strContrabandKilos() + " kilos";
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 6
	 * HtmlCell: 4
	 * DisplayName.enUS: money
	 */ 
	protected void _contrabandMoney(Wrap<BigDecimal> w) {
	}
	@Override public String strContrabandMoney() {
		return "$" + super.strContrabandMoney();
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 7
	 * HtmlCell: 1
	 * DisplayName.enUS: weapons
	 */ 
	protected void _contrabandWeapons(Wrap<BigDecimal> w) {
	}
	@Override public String strContrabandWeapons() {
		return super.strContrabandWeapons() + " weapons";
	}

	/**    
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 7
	 * HtmlCell: 2
	 * DisplayName.enUS: dollar amount
	 */ 
	protected void _contrabandDollarAmount(Wrap<BigDecimal> w) {
	}
	@Override public String strContrabandDollarAmount() {
		return "$" + super.strContrabandDollarAmount() + " worth";
	}
//
//	/**  
//	 * {@inheritDoc}
//	 * Indexed: true
//	 * Stored: true
//	 */ 
//	protected void _trafficSearchShortName(Wrap<String> w) {
//		StringBuilder b = new StringBuilder();
//		if(contrabandDollarAmount != null && contrabandDollarAmount.compareTo(BigDecimal.ZERO) != 0)
//			b.append(strContrabandDollarAmount()).append(" of ");
//		if(contrabandOunces != null && contrabandOunces.compareTo(BigDecimal.ZERO) != 0)
//			b.append(strContrabandOunces()).append(" ");
//		if(contrabandPounds != null && contrabandPounds.compareTo(BigDecimal.ZERO) != 0)
//			b.append(strContrabandPounds()).append(" ");
//		if(contrabandPints != null && contrabandPints.compareTo(BigDecimal.ZERO) != 0)
//			b.append(strContrabandPints()).append(" ");
//		if(contrabandGallons != null && contrabandGallons.compareTo(BigDecimal.ZERO) != 0)
//			b.append(strContrabandGallons()).append(" ");
//		if(contrabandDosages != null && contrabandDosages.compareTo(BigDecimal.ZERO) != 0)
//			b.append(strContrabandDosages()).append(" ");
//		if(contrabandGrams != null && contrabandGrams.compareTo(BigDecimal.ZERO) != 0)
//			b.append(strContrabandGrams()).append(" ");
//		if(contrabandKilos != null && contrabandKilos.compareTo(BigDecimal.ZERO) != 0)
//			b.append(strContrabandKilos()).append(" ");
//		if(contrabandMoney != null && contrabandMoney.compareTo(BigDecimal.ZERO) != 0)
//			b.append(strContrabandMoney()).append(" ");
//		if(contrabandWeapons != null && contrabandWeapons.compareTo(BigDecimal.ZERO) != 0)
//			b.append(strContrabandMoney()).append(" ");
//		b.append("contraband");
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
//	protected void _trafficSearchCompleteName(Wrap<String> c) {
//		c.o(trafficSearchShortName);
//	}
//
//	@Override
//	protected void _objectTitle(Wrap<String> c) {
//		c.o(trafficSearchCompleteName);
//	}
}
