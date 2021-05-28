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
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.vertx.SemaphoreVerticle&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class SemaphoreVerticleGen<DEV> extends AbstractVerticle {
	protected static final Logger LOG = LoggerFactory.getLogger(SemaphoreVerticle.class);
	public static final String startComplete1 = "SemaphoreVerticle start completed. ";
	public static final String startComplete = startComplete1;
	public static final String startFail1 = "SemaphoreVerticle start failed. ";
	public static final String startFail = startFail1;
	public static final String startSemaphoreComplete1 = "%s semaphore completed. ";
	public static final String startSemaphoreComplete = startSemaphoreComplete1;
	public static final String startSemaphoreFail1 = "%s semaphore failed. ";
	public static final String startSemaphoreFail = startSemaphoreFail1;


	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedSemaphoreVerticle = false;

	public SemaphoreVerticle initDeepSemaphoreVerticle(SiteRequestEnUS siteRequest_) {
		if(!alreadyInitializedSemaphoreVerticle) {
			alreadyInitializedSemaphoreVerticle = true;
			initDeepSemaphoreVerticle();
		}
		return (SemaphoreVerticle)this;
	}

	public void initDeepSemaphoreVerticle() {
		initSemaphoreVerticle();
	}

	public void initSemaphoreVerticle() {
	}

	public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepSemaphoreVerticle(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainSemaphoreVerticle(v);
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
	public Object obtainSemaphoreVerticle(String var) {
		SemaphoreVerticle oSemaphoreVerticle = (SemaphoreVerticle)this;
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
				o = attributeSemaphoreVerticle(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeSemaphoreVerticle(String var, Object val) {
		SemaphoreVerticle oSemaphoreVerticle = (SemaphoreVerticle)this;
		switch(var) {
			default:
				return null;
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetSemaphoreVerticle(entityVar,  siteRequest_, o);
	}
	public static Object staticSetSemaphoreVerticle(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return null;
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrSemaphoreVerticle(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrSemaphoreVerticle(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return null;
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrSemaphoreVerticle(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrSemaphoreVerticle(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return null;
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqSemaphoreVerticle(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqSemaphoreVerticle(String entityVar, SiteRequestEnUS siteRequest_, String o) {
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
					o = defineSemaphoreVerticle(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSemaphoreVerticle(String var, String val) {
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
					o = defineSemaphoreVerticle(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSemaphoreVerticle(String var, Object val) {
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
		if(!(o instanceof SemaphoreVerticle))
			return false;
		SemaphoreVerticle that = (SemaphoreVerticle)o;
		return true;
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("SemaphoreVerticle { ");
		sb.append(" }");
		return sb.toString();
	}

	public static final String[] SemaphoreVerticleVals = new String[] { startComplete1, startFail1, startSemaphoreComplete1, startSemaphoreFail1 };

}
