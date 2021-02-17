package com.opendatapolicing.enus.vertx;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import io.vertx.core.logging.LoggerFactory;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.vertx.core.logging.Logger;
import java.math.RoundingMode;
import com.opendatapolicing.enus.wrap.Wrap;
import java.math.MathContext;
import com.opendatapolicing.enus.writer.AllWriter;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.opendatapolicing.enus.request.api.ApiRequest;
import com.opendatapolicing.enus.vertx.AppSwagger2;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.opendatapolicing.enus.cluster.Cluster;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.vertx.AppOpenApi3&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class AppOpenApi3Gen<DEV> extends AppSwagger2 {
	protected static final Logger LOGGER = LoggerFactory.getLogger(AppOpenApi3.class);

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedAppOpenApi3 = false;

	public AppOpenApi3 initDeepAppOpenApi3(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedAppOpenApi3) {
			alreadyInitializedAppOpenApi3 = true;
			initDeepAppOpenApi3();
		}
		return (AppOpenApi3)this;
	}

	public void initDeepAppOpenApi3() {
		initAppOpenApi3();
		super.initDeepAppSwagger2(siteRequest_);
	}

	public void initAppOpenApi3() {
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepAppOpenApi3(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestAppOpenApi3(SiteRequestEnUS siteRequest_) {
			super.siteRequestAppSwagger2(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestAppOpenApi3(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainAppOpenApi3(v);
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
	public Object obtainAppOpenApi3(String var) {
		AppOpenApi3 oAppOpenApi3 = (AppOpenApi3)this;
		switch(var) {
			default:
				return super.obtainAppSwagger2(var);
		}
	}

	///////////////
	// attribute //
	///////////////

	@Override public boolean attributeForClass(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = attributeAppOpenApi3(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeAppOpenApi3(String var, Object val) {
		AppOpenApi3 oAppOpenApi3 = (AppOpenApi3)this;
		switch(var) {
			default:
				return super.attributeAppSwagger2(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetAppOpenApi3(entityVar,  siteRequest_, o);
	}
	public static Object staticSetAppOpenApi3(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return AppSwagger2.staticSetAppSwagger2(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrAppOpenApi3(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrAppOpenApi3(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return AppSwagger2.staticSolrAppSwagger2(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrAppOpenApi3(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrAppOpenApi3(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
			default:
				return AppSwagger2.staticSolrStrAppSwagger2(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqAppOpenApi3(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqAppOpenApi3(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
			default:
				return AppSwagger2.staticSolrFqAppSwagger2(entityVar,  siteRequest_, o);
		}
	}

	/////////////
	// define //
	/////////////

	@Override public boolean defineForClass(String var, String val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = defineAppOpenApi3(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineAppOpenApi3(String var, String val) {
		switch(var.toLowerCase()) {
			default:
				return super.defineAppSwagger2(var, val);
		}
	}

	@Override public boolean defineForClass(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = defineAppOpenApi3(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineAppOpenApi3(String var, Object val) {
		switch(var.toLowerCase()) {
			default:
				return super.defineAppSwagger2(var, val);
		}
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestAppOpenApi3() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof AppOpenApi3) {
			AppOpenApi3 original = (AppOpenApi3)o;
			super.apiRequestAppSwagger2();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode());
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof AppOpenApi3))
			return false;
		AppOpenApi3 that = (AppOpenApi3)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("AppOpenApi3 { ");
		sb.append(" }");
		return sb.toString();
	}
}
