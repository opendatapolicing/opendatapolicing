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
import com.opendatapolicing.enus.java.LocalDateSerializer;
import io.vertx.core.AbstractVerticle;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.opendatapolicing.enus.base.BaseModel;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.vertx.WorkerVerticle&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class WorkerVerticleGen<DEV> extends AbstractVerticle {
	protected static final Logger LOG = LoggerFactory.getLogger(WorkerVerticle.class);
	public static final String configureSharedWorkerExecutorFail1 = "Could not configure the shared worker executor. ";
	public static final String configureSharedWorkerExecutorFail = configureSharedWorkerExecutorFail1;
	public static final String configureSharedWorkerExecutorComplete1 = "The shared worker executor \"{}\" was configured successfully. ";
	public static final String configureSharedWorkerExecutorComplete = configureSharedWorkerExecutorComplete1;

	public static final String configureEmailComplete1 = "Configure sending email succeeded. ";
	public static final String configureEmailComplete = configureEmailComplete1;
	public static final String configureEmailFail1 = "Configure sending email failed. ";
	public static final String configureEmailFail = configureEmailFail1;

	public static final String importDataComplete1 = "Importing initial data completed. ";
	public static final String importDataComplete = importDataComplete1;
	public static final String importDataFail1 = "Importing initial data failed. ";
	public static final String importDataFail = importDataFail1;
	public static final String importDataSkip1 = "data import skipped. ";
	public static final String importDataSkip = importDataSkip1;

	public static final String syncFtpComplete1 = "Syncing FTP files completed. ";
	public static final String syncFtpComplete = syncFtpComplete1;
	public static final String syncFtpFail1 = "Syncing FTP files failed. ";
	public static final String syncFtpFail = syncFtpFail1;
	public static final String syncFtpSkip1 = "Skip syncing FTP files. ";
	public static final String syncFtpSkip = syncFtpSkip1;
	public static final String syncFtpStarted1 = "Started syncing FTP files. ";
	public static final String syncFtpStarted = syncFtpStarted1;

	public static final String syncFtpRecordComplete1 = "%s FTP sync completed. ";
	public static final String syncFtpRecordComplete = syncFtpRecordComplete1;
	public static final String syncFtpRecordFail1 = "%s FTP sync failed. ";
	public static final String syncFtpRecordFail = syncFtpRecordFail1;
	public static final String syncFtpRecordCounterResetFail1 = "%s FTP sync failed to reset counter. ";
	public static final String syncFtpRecordCounterResetFail = syncFtpRecordCounterResetFail1;
	public static final String syncFtpRecordSkip1 = "%s FTP sync skipped. ";
	public static final String syncFtpRecordSkip = syncFtpRecordSkip1;
	public static final String syncFtpRecordStarted1 = "%s FTP sync started. ";
	public static final String syncFtpRecordStarted = syncFtpRecordStarted1;

	public static final String syncFtpHandleBodyEventBusName1 = "opendatapolicing-enUS-%s";
	public static final String syncFtpHandleBodyEventBusName = syncFtpHandleBodyEventBusName1;
	public static final String syncFtpHandleBodyContext1 = "context";
	public static final String syncFtpHandleBodyContext = syncFtpHandleBodyContext1;
	public static final String syncFtpHandleBodyParams1 = "params";
	public static final String syncFtpHandleBodyParams = syncFtpHandleBodyParams1;
	public static final String syncFtpHandleBodyBody1 = "body";
	public static final String syncFtpHandleBodyBody = syncFtpHandleBodyBody1;
	public static final String syncFtpHandleBodyPath1 = "path";
	public static final String syncFtpHandleBodyPath = syncFtpHandleBodyPath1;
	public static final String syncFtpHandleBodyCookie1 = "cookie";
	public static final String syncFtpHandleBodyCookie = syncFtpHandleBodyCookie1;
	public static final String syncFtpHandleBodyQuery1 = "query";
	public static final String syncFtpHandleBodyQuery = syncFtpHandleBodyQuery1;
	public static final String syncFtpHandleBodyVar1 = "var";
	public static final String syncFtpHandleBodyVar = syncFtpHandleBodyVar1;
	public static final String syncFtpHandleBodyRefreshFalse1 = "refresh:false";
	public static final String syncFtpHandleBodyRefreshFalse = syncFtpHandleBodyRefreshFalse1;
	public static final String syncFtpHandleBodyCommitWithin1 = "commitWithin";
	public static final String syncFtpHandleBodyCommitWithin = syncFtpHandleBodyCommitWithin1;
	public static final String syncFtpHandleBodyAction1 = "action";
	public static final String syncFtpHandleBodyAction = syncFtpHandleBodyAction1;
	public static final String syncFtpHandleBodyPutImportFuture1 = "putimport%sFuture";
	public static final String syncFtpHandleBodyPutImportFuture = syncFtpHandleBodyPutImportFuture1;
	public static final String syncFtpHandleBodyWebSocket1 = "websocket%s";
	public static final String syncFtpHandleBodyWebSocket = syncFtpHandleBodyWebSocket1;

	public static final String refreshAllDataComplete1 = "Refresh all data completed. ";
	public static final String refreshAllDataComplete = refreshAllDataComplete1;
	public static final String refreshAllDataFail1 = "Refresh all data failed. ";
	public static final String refreshAllDataFail = refreshAllDataFail1;
	public static final String refreshAllDataSkip1 = "Refresh all data skipped. ";
	public static final String refreshAllDataSkip = refreshAllDataSkip1;

	public static final String refreshDataComplete1 = "%s refresh completed. ";
	public static final String refreshDataComplete = refreshDataComplete1;
	public static final String refreshDataFail1 = "%s refresh failed. ";
	public static final String refreshDataFail = refreshDataFail1;
	public static final String refreshDataSkip1 = "%s refresh skipped. ";
	public static final String refreshDataSkip = refreshDataSkip1;

	public static final String syncAgenciesComplete1 = "%s refresh completed. ";
	public static final String syncAgenciesComplete = syncAgenciesComplete1;
	public static final String syncAgenciesFail1 = "%s refresh failed. ";
	public static final String syncAgenciesFail = syncAgenciesFail1;
	public static final String syncAgenciesSkip1 = "%s refresh skipped. ";
	public static final String syncAgenciesSkip = syncAgenciesSkip1;

	public static final String syncAgenciesFacetsComplete1 = "%s refresh facet data completed. ";
	public static final String syncAgenciesFacetsComplete = syncAgenciesFacetsComplete1;
	public static final String syncAgenciesFacetsFail1 = "%s refresh facet data failed. ";
	public static final String syncAgenciesFacetsFail = syncAgenciesFacetsFail1;
	public static final String syncAgenciesFacetsSkip1 = "%s refresh facet data skipped. ";
	public static final String syncAgenciesFacetsSkip = syncAgenciesFacetsSkip1;


	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedWorkerVerticle = false;

	public WorkerVerticle initDeepWorkerVerticle(SiteRequestEnUS siteRequest_) {
		if(!alreadyInitializedWorkerVerticle) {
			alreadyInitializedWorkerVerticle = true;
			initDeepWorkerVerticle();
		}
		return (WorkerVerticle)this;
	}

	public void initDeepWorkerVerticle() {
		initWorkerVerticle();
	}

	public void initWorkerVerticle() {
	}

	public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepWorkerVerticle(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainWorkerVerticle(v);
			else if(o instanceof BaseModel) {
				BaseModel baseModel = (BaseModel)o;
				o = baseModel.obtainForClass(v);
			}
			else if(o instanceof Map) {
				Map<?, ?> map = (Map<?, ?>)o;
				o = map.get(v);
			}
		}
		return o;
	}
	public Object obtainWorkerVerticle(String var) {
		WorkerVerticle oWorkerVerticle = (WorkerVerticle)this;
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
				o = attributeWorkerVerticle(v, val);
			else if(o instanceof BaseModel) {
				BaseModel baseModel = (BaseModel)o;
				o = baseModel.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeWorkerVerticle(String var, Object val) {
		WorkerVerticle oWorkerVerticle = (WorkerVerticle)this;
		switch(var) {
			default:
				return null;
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetWorkerVerticle(entityVar,  siteRequest_, o);
	}
	public static Object staticSetWorkerVerticle(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return null;
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrWorkerVerticle(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrWorkerVerticle(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return null;
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrWorkerVerticle(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrWorkerVerticle(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return null;
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqWorkerVerticle(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqWorkerVerticle(String entityVar, SiteRequestEnUS siteRequest_, String o) {
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
					o = defineWorkerVerticle(v, val);
				else if(o instanceof BaseModel) {
					BaseModel oBaseModel = (BaseModel)o;
					o = oBaseModel.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineWorkerVerticle(String var, String val) {
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
					o = defineWorkerVerticle(v, val);
				else if(o instanceof BaseModel) {
					BaseModel oBaseModel = (BaseModel)o;
					o = oBaseModel.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineWorkerVerticle(String var, Object val) {
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
		if(!(o instanceof WorkerVerticle))
			return false;
		WorkerVerticle that = (WorkerVerticle)o;
		return true;
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("WorkerVerticle { ");
		sb.append(" }");
		return sb.toString();
	}

	public static final String[] WorkerVerticleVals = new String[] { configureSharedWorkerExecutorFail1, configureSharedWorkerExecutorComplete1, configureEmailComplete1, configureEmailFail1, importDataComplete1, importDataFail1, importDataSkip1, syncFtpComplete1, syncFtpFail1, syncFtpSkip1, syncFtpStarted1, syncFtpRecordComplete1, syncFtpRecordFail1, syncFtpRecordCounterResetFail1, syncFtpRecordSkip1, syncFtpRecordStarted1, syncFtpHandleBodyEventBusName1, syncFtpHandleBodyContext1, syncFtpHandleBodyParams1, syncFtpHandleBodyBody1, syncFtpHandleBodyPath1, syncFtpHandleBodyCookie1, syncFtpHandleBodyQuery1, syncFtpHandleBodyVar1, syncFtpHandleBodyRefreshFalse1, syncFtpHandleBodyCommitWithin1, syncFtpHandleBodyAction1, syncFtpHandleBodyPutImportFuture1, syncFtpHandleBodyWebSocket1, refreshAllDataComplete1, refreshAllDataFail1, refreshAllDataSkip1, refreshDataComplete1, refreshDataFail1, refreshDataSkip1, syncAgenciesComplete1, syncAgenciesFail1, syncAgenciesSkip1, syncAgenciesFacetsComplete1, syncAgenciesFacetsFail1, syncAgenciesFacetsSkip1 };

}
