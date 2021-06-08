package com.opendatapolicing.enus.vertx;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opendatapolicing.enus.java.ZonedDateTimeSerializer;
import com.opendatapolicing.enus.config.ConfigKeys;
import java.math.RoundingMode;
import com.opendatapolicing.enus.wrap.Wrap;
import org.slf4j.Logger;
import com.opendatapolicing.enus.java.ZonedDateTimeDeserializer;
import java.math.MathContext;
import io.vertx.core.Promise;
import com.opendatapolicing.enus.writer.AllWriter;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.vertx.core.Future;
import com.opendatapolicing.enus.request.api.ApiRequest;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import io.vertx.core.AbstractVerticle;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.opendatapolicing.enus.cluster.Cluster;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.opendatapolicing.enus.request.SiteRequestEnUS;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.vertx.MainVerticle&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class MainVerticleGen<DEV> extends AbstractVerticle {

/*
CREATE TABLE PageDesign(
	pk bigserial primary key
	, inheritPk text
	, created timestamp with time zone
	, pageDesignCompleteName text
	, designHidden boolean
	, pageContentType text
	);
CREATE TABLE HtmlPart(
	pk bigserial primary key
	, inheritPk text
	, created timestamp with time zone
	, htmlLink text
	, htmlElement text
	, htmlId text
	, htmlClasses text
	, htmlStyle text
	, htmlBefore text
	, htmlAfter text
	, htmlText text
	, htmlVar text
	, htmlVarSpan text
	, htmlVarForm text
	, htmlVarInput text
	, htmlVarForEach text
	, htmlVarHtml text
	, htmlVarBase64Decode text
	, htmlExclude boolean
	, pdfExclude boolean
	, loginLogout boolean
	, searchUri text
	, mapTo text
	, sort1 double precision
	, sort2 double precision
	, sort3 double precision
	, sort4 double precision
	, sort5 double precision
	, sort6 double precision
	, sort7 double precision
	, sort8 double precision
	, sort9 double precision
	, sort10 double precision
	);
CREATE TABLE SiteUser(
	userKey bigint
	, userId text
	, userName text
	, userEmail text
	, userFirstName text
	, userLastName text
	, userFullName text
	);
CREATE TABLE SiteState(
	pk bigserial primary key
	, inheritPk text
	, created timestamp with time zone
	, stateName text
	, stateAbbreviation text
	, imageLeft integer
	, imageTop integer
	);
CREATE TABLE SiteAgency(
	pk bigserial primary key
	, inheritPk text
	, created timestamp with time zone
	, agencyName text
	, stateKey bigint references SiteState(pk)
	, imageLeft integer
	, imageTop integer
	, imageCoords text
	);
CREATE TABLE TrafficStop(
	stateAbbreviation text
	, stateName text
	, agencyTitle text
	, stopDateTime timestamp with time zone
	, stopYear integer
	, stopPurposeNum integer
	, stopActionNum integer
	, stopDriverArrest boolean
	, stopPassengerArrest boolean
	, stopEncounterForce boolean
	, stopEngageForce boolean
	, stopOfficerInjury boolean
	, stopDriverInjury boolean
	, stopPassengerInjury boolean
	, stopOfficerId text
	, stopLocationId text
	, stopCityId text
	);
CREATE TABLE TrafficPerson(
	pk bigserial primary key
	, inheritPk text
	, created timestamp with time zone
	, stopId text
	, personAge integer
	, personTypeId text
	, personGenderId text
	, personEthnicityId text
	, personRaceId text
	);
CREATE TABLE TrafficSearch(
	pk bigserial primary key
	, inheritPk text
	, created timestamp with time zone
	, personId text
	, searchTypeNum integer
	, searchVehicle boolean
	, searchDriver boolean
	, searchPassenger boolean
	, searchProperty boolean
	, searchVehicleSiezed boolean
	, searchPersonalPropertySiezed boolean
	, searchOtherPropertySiezed boolean
	);
CREATE TABLE TrafficContraband(
	pk bigserial primary key
	, inheritPk text
	, created timestamp with time zone
	, searchId text
	, contrabandOunces decimal
	, contrabandPounds decimal
	, contrabandPints decimal
	, contrabandGallons decimal
	, contrabandDosages decimal
	, contrabandGrams decimal
	, contrabandKilos decimal
	, contrabandMoney decimal
	, contrabandWeapons decimal
	, contrabandDollarAmount decimal
	);
CREATE TABLE SearchBasis(
	pk bigserial primary key
	, inheritPk text
	, created timestamp with time zone
	, searchId text
	, searchBasisId text
	);
CREATE TABLE PageDesignChildDesignKeys_PageDesignParentDesignKeys(
	pk bigserial primary key
	, pk1 bigint references PageDesign(pk)
	, pk2 bigint references PageDesign(pk)
	);
CREATE TABLE PageDesignHtmlPartKeys_HtmlPartPageDesignKeys(
	pk bigserial primary key
	, pk1 bigint references PageDesign(pk)
	, pk2 bigint references HtmlPart(pk)
	);

DROP TABLE PageDesign CASCADE;
DROP TABLE HtmlPart CASCADE;
DROP TABLE SiteUser CASCADE;
DROP TABLE SiteState CASCADE;
DROP TABLE SiteAgency CASCADE;
DROP TABLE TrafficStop CASCADE;
DROP TABLE TrafficPerson CASCADE;
DROP TABLE TrafficSearch CASCADE;
DROP TABLE TrafficContraband CASCADE;
DROP TABLE SearchBasis CASCADE;
DROP TABLE PageDesignChildDesignKeys_PageDesignParentDesignKeys CASCADE;
DROP TABLE PageDesignHtmlPartKeys_HtmlPartPageDesignKeys CASCADE;
*/

	protected static final Logger LOG = LoggerFactory.getLogger(MainVerticle.class);
	public static final String configureConfigComplete1 = "The config was configured successfully. ";
	public static final String configureConfigComplete = configureConfigComplete1;
	public static final String configureConfigFail1 = "Could not configure the config(). ";
	public static final String configureConfigFail = configureConfigFail1;

	public static final String configureDataConnectionError1 = "Could not open the database client connection. ";
	public static final String configureDataConnectionError = configureDataConnectionError1;
	public static final String configureDataConnectionSuccess1 = "The database client connection was successful. ";
	public static final String configureDataConnectionSuccess = configureDataConnectionSuccess1;
	public static final String configureDataInitError1 = "Could not initialize the database tables. ";
	public static final String configureDataInitError = configureDataInitError1;
	public static final String configureDataInitSuccess1 = "The database tables were created successfully. ";
	public static final String configureDataInitSuccess = configureDataInitSuccess1;

	public static final String configureOpenApiError1 = "Could not configure the auth server and API. ";
	public static final String configureOpenApiError = configureOpenApiError1;
	public static final String configureOpenApiSuccess1 = "The auth server and API was configured successfully. ";
	public static final String configureOpenApiSuccess = configureOpenApiSuccess1;

	public static final String configureSharedWorkerExecutorFail1 = "Could not configure the shared worker executor. ";
	public static final String configureSharedWorkerExecutorFail = configureSharedWorkerExecutorFail1;
	public static final String configureSharedWorkerExecutorComplete1 = "The shared worker executor \"{}\" was configured successfully. ";
	public static final String configureSharedWorkerExecutorComplete = configureSharedWorkerExecutorComplete1;

	public static final String configureHealthChecksComplete1 = "The health checks were configured successfully. ";
	public static final String configureHealthChecksComplete = configureHealthChecksComplete1;
	public static final String configureHealthChecksFail1 = "Could not configure the health checks. ";
	public static final String configureHealthChecksFail = configureHealthChecksFail1;
	public static final String configureHealthChecksErrorDatabase1 = "The database is not configured properly. ";
	public static final String configureHealthChecksErrorDatabase = configureHealthChecksErrorDatabase1;
	public static final String configureHealthChecksEmptySolr1 = "The Solr search engine is empty. ";
	public static final String configureHealthChecksEmptySolr = configureHealthChecksEmptySolr1;
	public static final String configureHealthChecksErrorSolr1 = "The Solr search engine is not configured properly. ";
	public static final String configureHealthChecksErrorSolr = configureHealthChecksErrorSolr1;
	public static final String configureHealthChecksErrorVertx1 = "The Vert.x application is not configured properly. ";
	public static final String configureHealthChecksErrorVertx = configureHealthChecksErrorVertx1;

	public static final String configureWebsocketsComplete1 = "Configure websockets succeeded. ";
	public static final String configureWebsocketsComplete = configureWebsocketsComplete1;
	public static final String configureWebsocketsFail1 = "Configure websockets failed. ";
	public static final String configureWebsocketsFail = configureWebsocketsFail1;

	public static final String configureEmailComplete1 = "Configure sending email succeeded. ";
	public static final String configureEmailComplete = configureEmailComplete1;
	public static final String configureEmailFail1 = "Configure sending email failed. ";
	public static final String configureEmailFail = configureEmailFail1;

	public static final String configureApiFail1 = "The API was not configured properly. ";
	public static final String configureApiFail = configureApiFail1;
	public static final String configureApiComplete1 = "The API was configured properly. ";
	public static final String configureApiComplete = configureApiComplete1;

	public static final String configureUiFail1 = "The UI was not configured properly. ";
	public static final String configureUiFail = configureUiFail1;
	public static final String configureUiComplete1 = "The UI was configured properly. ";
	public static final String configureUiComplete = configureUiComplete1;

	public static final String startServerErrorServer1 = "The server is not configured properly. ";
	public static final String startServerErrorServer = startServerErrorServer1;
	public static final String startServerSuccessServer1 = "The HTTP server is running: %s";
	public static final String startServerSuccessServer = startServerSuccessServer1;
	public static final String startServerBeforeServer1 = "HTTP server starting: %s";
	public static final String startServerBeforeServer = startServerBeforeServer1;
	public static final String startServerSsl1 = "Configuring SSL: %s";
	public static final String startServerSsl = startServerSsl1;

	public static final String stopFail1 = "Could not close the database client connection. ";
	public static final String stopFail = stopFail1;
	public static final String stopComplete1 = "The database client connection was closed. ";
	public static final String stopComplete = stopComplete1;


	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedMainVerticle = false;

	public MainVerticle initDeepMainVerticle(SiteRequestEnUS siteRequest_) {
		if(!alreadyInitializedMainVerticle) {
			alreadyInitializedMainVerticle = true;
			initDeepMainVerticle();
		}
		return (MainVerticle)this;
	}

	public void initDeepMainVerticle() {
		initMainVerticle();
	}

	public void initMainVerticle() {
	}

	public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepMainVerticle(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainMainVerticle(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
			else if(o instanceof Map) {
				Map<?, ?> map = (Map<?, ?>)o;
				o = map.get(v);
			}
		}
		return o;
	}
	public Object obtainMainVerticle(String var) {
		MainVerticle oMainVerticle = (MainVerticle)this;
		switch(var) {
			default:
				return null;
		}
	}

	///////////////
	// attribute //
	///////////////

	public boolean attributeForClass(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = attributeMainVerticle(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeMainVerticle(String var, Object val) {
		MainVerticle oMainVerticle = (MainVerticle)this;
		switch(var) {
			default:
				return null;
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetMainVerticle(entityVar,  siteRequest_, o);
	}
	public static Object staticSetMainVerticle(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return null;
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrMainVerticle(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrMainVerticle(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return null;
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrMainVerticle(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrMainVerticle(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return null;
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqMainVerticle(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqMainVerticle(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return null;
		}
	}

	/////////////
	// define //
	/////////////

	public boolean defineForClass(String var, String val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = defineMainVerticle(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineMainVerticle(String var, String val) {
		switch(var.toLowerCase()) {
			default:
				return null;
		}
	}

	public boolean defineForClass(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = defineMainVerticle(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineMainVerticle(String var, Object val) {
		switch(var.toLowerCase()) {
			default:
				return null;
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash();
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof MainVerticle))
			return false;
		MainVerticle that = (MainVerticle)o;
		return true;
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("MainVerticle { ");
		sb.append(" }");
		return sb.toString();
	}

	public static final String[] MainVerticleVals = new String[] { configureConfigComplete1, configureConfigFail1, configureDataConnectionError1, configureDataConnectionSuccess1, configureDataInitError1, configureDataInitSuccess1, configureOpenApiError1, configureOpenApiSuccess1, configureSharedWorkerExecutorFail1, configureSharedWorkerExecutorComplete1, configureHealthChecksComplete1, configureHealthChecksFail1, configureHealthChecksErrorDatabase1, configureHealthChecksEmptySolr1, configureHealthChecksErrorSolr1, configureHealthChecksErrorVertx1, configureWebsocketsComplete1, configureWebsocketsFail1, configureEmailComplete1, configureEmailFail1, configureApiFail1, configureApiComplete1, configureUiFail1, configureUiComplete1, startServerErrorServer1, startServerSuccessServer1, startServerBeforeServer1, startServerSsl1, stopFail1, stopComplete1 };

}
