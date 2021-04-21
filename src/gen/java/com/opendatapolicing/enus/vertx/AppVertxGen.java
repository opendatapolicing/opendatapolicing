package com.opendatapolicing.enus.vertx;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opendatapolicing.enus.config.ConfigKeys;
import java.math.RoundingMode;
import com.opendatapolicing.enus.wrap.Wrap;
import org.slf4j.Logger;
import java.math.MathContext;
import com.opendatapolicing.enus.writer.AllWriter;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
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
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.vertx.AppVertx&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class AppVertxGen<DEV> extends AbstractVerticle {

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
	pk bigserial primary key
	, inheritPk text
	, created timestamp with time zone
	, userKey bigint
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
	pk bigserial primary key
	, inheritPk text
	, created timestamp with time zone
	, stateAbbreviation text
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
	, trafficStopKey bigint references TrafficStop(pk)
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
	, personKey bigint references TrafficPerson(pk)
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
	, searchKey bigint references TrafficSearch(pk)
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
	, searchKey bigint references TrafficSearch(pk)
	, searchBasisId text
	, searchBasisTitle text
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

	protected static final Logger LOG = LoggerFactory.getLogger(AppVertx.class);
	public static final String configureDataConnectionError1 = "Could not open the database client connection. ";
	public static final String configureDataConnectionError = configureDataConnectionError1;
	public static final String configureDataConnectionSuccess1 = "The database client connection was successful. ";
	public static final String configureDataConnectionSuccess = configureDataConnectionSuccess1;
	public static final String configureDataInitError1 = "Could not initialize the database tables. ";
	public static final String configureDataInitError = configureDataInitError1;
	public static final String configureDataInitSuccess1 = "The database tables were created successfully. ";
	public static final String configureDataInitSuccess = configureDataInitSuccess1;

	public static final String configureClusterDataError1 = "Could not configure the shared cluster data. ";
	public static final String configureClusterDataError = configureClusterDataError1;
	public static final String configureClusterDataSuccess1 = "The shared cluster data was configured successfully. ";
	public static final String configureClusterDataSuccess = configureClusterDataSuccess1;

	public static final String configureOpenApiError1 = "Could not configure the auth server and API. ";
	public static final String configureOpenApiError = configureOpenApiError1;
	public static final String configureOpenApiSuccess1 = "The auth server and API was configured successfully. ";
	public static final String configureOpenApiSuccess = configureOpenApiSuccess1;

	public static final String configureSharedWorkerExecutorError1 = "Could not configure the shared worker executor. ";
	public static final String configureSharedWorkerExecutorError = configureSharedWorkerExecutorError1;
	public static final String configureSharedWorkerExecutorSuccess1 = "The shared worker executor was configured successfully. ";
	public static final String configureSharedWorkerExecutorSuccess = configureSharedWorkerExecutorSuccess1;

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

	public static final String configureApiFail1 = "The API was configured properly. ";
	public static final String configureApiFail = configureApiFail1;
	public static final String configureApiComplete1 = "The API was not configured properly. ";
	public static final String configureApiComplete = configureApiComplete1;

	public static final String configureUiFail1 = "The UI was configured properly. ";
	public static final String configureUiFail = configureUiFail1;
	public static final String configureUiComplete1 = "The UI was not configured properly. ";
	public static final String configureUiComplete = configureUiComplete1;

	public static final String startServerErrorServer1 = "The server is not configured properly. ";
	public static final String startServerErrorServer = startServerErrorServer1;
	public static final String startServerSuccessServer1 = "The HTTP server is running: %s";
	public static final String startServerSuccessServer = startServerSuccessServer1;
	public static final String startServerBeforeServer1 = "HTTP server starting: %s";
	public static final String startServerBeforeServer = startServerBeforeServer1;
	public static final String startServerSsl1 = "Configuring SSL: %s";
	public static final String startServerSsl = startServerSsl1;

	public static final String closeDataError1 = "Could not close the database client connection. ";
	public static final String closeDataError = closeDataError1;
	public static final String closeDataSuccess1 = "The database client connextion was closed. ";
	public static final String closeDataSuccess = closeDataSuccess1;


	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedAppVertx = false;

	public AppVertx initDeepAppVertx(SiteRequestEnUS siteRequest_) {
		if(!alreadyInitializedAppVertx) {
			alreadyInitializedAppVertx = true;
			initDeepAppVertx();
		}
		return (AppVertx)this;
	}

	public void initDeepAppVertx() {
		initAppVertx();
	}

	public void initAppVertx() {
	}

	public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepAppVertx(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainAppVertx(v);
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
	public Object obtainAppVertx(String var) {
		AppVertx oAppVertx = (AppVertx)this;
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
				o = attributeAppVertx(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeAppVertx(String var, Object val) {
		AppVertx oAppVertx = (AppVertx)this;
		switch(var) {
			default:
				return null;
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetAppVertx(entityVar,  siteRequest_, o);
	}
	public static Object staticSetAppVertx(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return null;
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrAppVertx(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrAppVertx(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return null;
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrAppVertx(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrAppVertx(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return null;
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqAppVertx(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqAppVertx(String entityVar, SiteRequestEnUS siteRequest_, String o) {
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
					o = defineAppVertx(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineAppVertx(String var, String val) {
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
					o = defineAppVertx(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineAppVertx(String var, Object val) {
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
		if(!(o instanceof AppVertx))
			return false;
		AppVertx that = (AppVertx)o;
		return true;
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("AppVertx { ");
		sb.append(" }");
		return sb.toString();
	}

	public static final String[] AppVertxVals = new String[] { configureDataConnectionError1, configureDataConnectionSuccess1, configureDataInitError1, configureDataInitSuccess1, configureClusterDataError1, configureClusterDataSuccess1, configureOpenApiError1, configureOpenApiSuccess1, configureSharedWorkerExecutorError1, configureSharedWorkerExecutorSuccess1, configureHealthChecksErrorDatabase1, configureHealthChecksEmptySolr1, configureHealthChecksErrorSolr1, configureHealthChecksErrorVertx1, configureWebsocketsComplete1, configureWebsocketsFail1, configureEmailComplete1, configureEmailFail1, configureApiFail1, configureApiComplete1, configureUiFail1, configureUiComplete1, startServerErrorServer1, startServerSuccessServer1, startServerBeforeServer1, startServerSsl1, closeDataError1, closeDataSuccess1 };
}
