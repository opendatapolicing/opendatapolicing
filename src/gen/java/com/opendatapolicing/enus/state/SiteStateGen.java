package com.opendatapolicing.enus.state;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Arrays;
import java.util.Date;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import java.lang.Integer;
import java.lang.Long;
import java.util.Map;
import io.vertx.core.json.JsonObject;
import com.opendatapolicing.enus.java.ZonedDateTimeSerializer;
import java.math.RoundingMode;
import com.opendatapolicing.enus.wrap.Wrap;
import com.opendatapolicing.enus.java.ZonedDateTimeDeserializer;
import java.math.MathContext;
import java.util.Set;
import com.opendatapolicing.enus.writer.AllWriter;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.vertx.core.Future;
import com.opendatapolicing.enus.request.api.ApiRequest;
import java.util.Objects;
import java.util.List;
import org.apache.solr.client.solrj.SolrQuery;
import java.util.Optional;
import com.opendatapolicing.enus.cluster.Cluster;
import org.apache.solr.client.solrj.util.ClientUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.solr.common.SolrInputDocument;
import org.apache.commons.lang3.exception.ExceptionUtils;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.HashMap;
import java.text.NumberFormat;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opendatapolicing.enus.config.ConfigKeys;
import java.lang.String;
import org.slf4j.Logger;
import io.vertx.core.Promise;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.apache.solr.client.solrj.SolrClient;
import io.vertx.core.json.JsonArray;
import org.apache.solr.common.SolrDocument;
import org.apache.commons.lang3.math.NumberUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.opendatapolicing.enus.agency.SiteAgency;
import com.opendatapolicing.enus.request.SiteRequestEnUS;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.state.SiteState&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class SiteStateGen<DEV> extends Cluster {
	protected static final Logger LOG = LoggerFactory.getLogger(SiteState.class);

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("");

	//////////////
	// stateKey //
	//////////////

	/**	 The entity stateKey
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long stateKey;
	@JsonIgnore
	public Wrap<Long> stateKeyWrap = new Wrap<Long>().var("stateKey").o(stateKey);

	/**	<br/> The entity stateKey
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.state.SiteState&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stateKey">Find the entity stateKey in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stateKey(Wrap<Long> c);

	public Long getStateKey() {
		return stateKey;
	}

	public void setStateKey(Long stateKey) {
		this.stateKey = stateKey;
		this.stateKeyWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setStateKey(String o) {
		this.stateKey = SiteState.staticSetStateKey(siteRequest_, o);
		this.stateKeyWrap.alreadyInitialized = true;
	}
	public static Long staticSetStateKey(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected SiteState stateKeyInit() {
		if(!stateKeyWrap.alreadyInitialized) {
			_stateKey(stateKeyWrap);
			if(stateKey == null)
				setStateKey(stateKeyWrap.o);
			stateKeyWrap.o(null);
		}
		stateKeyWrap.alreadyInitialized(true);
		return (SiteState)this;
	}

	public static Long staticSolrStateKey(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrStateKey(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStateKey(SiteRequestEnUS siteRequest_, String o) {
		return SiteState.staticSolrStrStateKey(siteRequest_, SiteState.staticSolrStateKey(siteRequest_, SiteState.staticSetStateKey(siteRequest_, o)));
	}

	public Long solrStateKey() {
		return SiteState.staticSolrStateKey(siteRequest_, stateKey);
	}

	public String strStateKey() {
		return stateKey == null ? "" : stateKey.toString();
	}

	public Long sqlStateKey() {
		return stateKey;
	}

	public String jsonStateKey() {
		return stateKey == null ? "" : stateKey.toString();
	}

	///////////////
	// stateName //
	///////////////

	/**	 The entity stateName
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String stateName;
	@JsonIgnore
	public Wrap<String> stateNameWrap = new Wrap<String>().var("stateName").o(stateName);

	/**	<br/> The entity stateName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.state.SiteState&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stateName">Find the entity stateName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stateName(Wrap<String> c);

	public String getStateName() {
		return stateName;
	}
	public void setStateName(String o) {
		this.stateName = SiteState.staticSetStateName(siteRequest_, o);
		this.stateNameWrap.alreadyInitialized = true;
	}
	public static String staticSetStateName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteState stateNameInit() {
		if(!stateNameWrap.alreadyInitialized) {
			_stateName(stateNameWrap);
			if(stateName == null)
				setStateName(stateNameWrap.o);
			stateNameWrap.o(null);
		}
		stateNameWrap.alreadyInitialized(true);
		return (SiteState)this;
	}

	public static String staticSolrStateName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStateName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStateName(SiteRequestEnUS siteRequest_, String o) {
		return SiteState.staticSolrStrStateName(siteRequest_, SiteState.staticSolrStateName(siteRequest_, SiteState.staticSetStateName(siteRequest_, o)));
	}

	public String solrStateName() {
		return SiteState.staticSolrStateName(siteRequest_, stateName);
	}

	public String strStateName() {
		return stateName == null ? "" : stateName;
	}

	public String sqlStateName() {
		return stateName;
	}

	public String jsonStateName() {
		return stateName == null ? "" : stateName;
	}

	///////////////////////
	// stateAbbreviation //
	///////////////////////

	/**	 The entity stateAbbreviation
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String stateAbbreviation;
	@JsonIgnore
	public Wrap<String> stateAbbreviationWrap = new Wrap<String>().var("stateAbbreviation").o(stateAbbreviation);

	/**	<br/> The entity stateAbbreviation
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.state.SiteState&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stateAbbreviation">Find the entity stateAbbreviation in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stateAbbreviation(Wrap<String> c);

	public String getStateAbbreviation() {
		return stateAbbreviation;
	}
	public void setStateAbbreviation(String o) {
		this.stateAbbreviation = SiteState.staticSetStateAbbreviation(siteRequest_, o);
		this.stateAbbreviationWrap.alreadyInitialized = true;
	}
	public static String staticSetStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteState stateAbbreviationInit() {
		if(!stateAbbreviationWrap.alreadyInitialized) {
			_stateAbbreviation(stateAbbreviationWrap);
			if(stateAbbreviation == null)
				setStateAbbreviation(stateAbbreviationWrap.o);
			stateAbbreviationWrap.o(null);
		}
		stateAbbreviationWrap.alreadyInitialized(true);
		return (SiteState)this;
	}

	public static String staticSolrStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStateAbbreviation(SiteRequestEnUS siteRequest_, String o) {
		return SiteState.staticSolrStrStateAbbreviation(siteRequest_, SiteState.staticSolrStateAbbreviation(siteRequest_, SiteState.staticSetStateAbbreviation(siteRequest_, o)));
	}

	public String solrStateAbbreviation() {
		return SiteState.staticSolrStateAbbreviation(siteRequest_, stateAbbreviation);
	}

	public String strStateAbbreviation() {
		return stateAbbreviation == null ? "" : stateAbbreviation;
	}

	public String sqlStateAbbreviation() {
		return stateAbbreviation;
	}

	public String jsonStateAbbreviation() {
		return stateAbbreviation == null ? "" : stateAbbreviation;
	}

	///////////////
	// imageLeft //
	///////////////

	/**	 The entity imageLeft
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer imageLeft;
	@JsonIgnore
	public Wrap<Integer> imageLeftWrap = new Wrap<Integer>().var("imageLeft").o(imageLeft);

	/**	<br/> The entity imageLeft
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.state.SiteState&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:imageLeft">Find the entity imageLeft in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _imageLeft(Wrap<Integer> c);

	public Integer getImageLeft() {
		return imageLeft;
	}

	public void setImageLeft(Integer imageLeft) {
		this.imageLeft = imageLeft;
		this.imageLeftWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setImageLeft(String o) {
		this.imageLeft = SiteState.staticSetImageLeft(siteRequest_, o);
		this.imageLeftWrap.alreadyInitialized = true;
	}
	public static Integer staticSetImageLeft(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected SiteState imageLeftInit() {
		if(!imageLeftWrap.alreadyInitialized) {
			_imageLeft(imageLeftWrap);
			if(imageLeft == null)
				setImageLeft(imageLeftWrap.o);
			imageLeftWrap.o(null);
		}
		imageLeftWrap.alreadyInitialized(true);
		return (SiteState)this;
	}

	public static Integer staticSolrImageLeft(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrImageLeft(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqImageLeft(SiteRequestEnUS siteRequest_, String o) {
		return SiteState.staticSolrStrImageLeft(siteRequest_, SiteState.staticSolrImageLeft(siteRequest_, SiteState.staticSetImageLeft(siteRequest_, o)));
	}

	public Integer solrImageLeft() {
		return SiteState.staticSolrImageLeft(siteRequest_, imageLeft);
	}

	public String strImageLeft() {
		return imageLeft == null ? "" : imageLeft.toString();
	}

	public Integer sqlImageLeft() {
		return imageLeft;
	}

	public String jsonImageLeft() {
		return imageLeft == null ? "" : imageLeft.toString();
	}

	//////////////
	// imageTop //
	//////////////

	/**	 The entity imageTop
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer imageTop;
	@JsonIgnore
	public Wrap<Integer> imageTopWrap = new Wrap<Integer>().var("imageTop").o(imageTop);

	/**	<br/> The entity imageTop
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.state.SiteState&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:imageTop">Find the entity imageTop in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _imageTop(Wrap<Integer> c);

	public Integer getImageTop() {
		return imageTop;
	}

	public void setImageTop(Integer imageTop) {
		this.imageTop = imageTop;
		this.imageTopWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setImageTop(String o) {
		this.imageTop = SiteState.staticSetImageTop(siteRequest_, o);
		this.imageTopWrap.alreadyInitialized = true;
	}
	public static Integer staticSetImageTop(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected SiteState imageTopInit() {
		if(!imageTopWrap.alreadyInitialized) {
			_imageTop(imageTopWrap);
			if(imageTop == null)
				setImageTop(imageTopWrap.o);
			imageTopWrap.o(null);
		}
		imageTopWrap.alreadyInitialized(true);
		return (SiteState)this;
	}

	public static Integer staticSolrImageTop(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrImageTop(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqImageTop(SiteRequestEnUS siteRequest_, String o) {
		return SiteState.staticSolrStrImageTop(siteRequest_, SiteState.staticSolrImageTop(siteRequest_, SiteState.staticSetImageTop(siteRequest_, o)));
	}

	public Integer solrImageTop() {
		return SiteState.staticSolrImageTop(siteRequest_, imageTop);
	}

	public String strImageTop() {
		return imageTop == null ? "" : imageTop.toString();
	}

	public Integer sqlImageTop() {
		return imageTop;
	}

	public String jsonImageTop() {
		return imageTop == null ? "" : imageTop.toString();
	}

	////////////////
	// agencyKeys //
	////////////////

	/**	 The entity agencyKeys
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonProperty
	@JsonFormat(shape = JsonFormat.Shape.ARRAY)
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> agencyKeys = new ArrayList<Long>();
	@JsonIgnore
	public Wrap<List<Long>> agencyKeysWrap = new Wrap<List<Long>>().var("agencyKeys").o(agencyKeys);

	/**	<br/> The entity agencyKeys
	 *  It is constructed before being initialized with the constructor by default List<Long>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.state.SiteState&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:agencyKeys">Find the entity agencyKeys in Solr</a>
	 * <br/>
	 * @param agencyKeys is the entity already constructed. 
	 **/
	protected abstract void _agencyKeys(List<Long> o);

	public List<Long> getAgencyKeys() {
		return agencyKeys;
	}

	public void setAgencyKeys(List<Long> agencyKeys) {
		this.agencyKeys = agencyKeys;
		this.agencyKeysWrap.alreadyInitialized = true;
	}
	@JsonIgnore
	public void setAgencyKeys(String o) {
		Long l = SiteState.staticSetAgencyKeys(siteRequest_, o);
		if(l != null)
			addAgencyKeys(l);
		this.agencyKeysWrap.alreadyInitialized = true;
	}
	public static Long staticSetAgencyKeys(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	public SiteState addAgencyKeys(Long...objets) {
		for(Long o : objets) {
			addAgencyKeys(o);
		}
		return (SiteState)this;
	}
	public SiteState addAgencyKeys(Long o) {
		if(o != null && !agencyKeys.contains(o))
			this.agencyKeys.add(o);
		return (SiteState)this;
	}
	@JsonIgnore
	public void setAgencyKeys(JsonArray objets) {
		agencyKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addAgencyKeys(o);
		}
	}
	public SiteState addAgencyKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addAgencyKeys(p);
		}
		return (SiteState)this;
	}
	protected SiteState agencyKeysInit() {
		if(!agencyKeysWrap.alreadyInitialized) {
			_agencyKeys(agencyKeys);
		}
		agencyKeysWrap.alreadyInitialized(true);
		return (SiteState)this;
	}

	public static Long staticSolrAgencyKeys(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrAgencyKeys(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqAgencyKeys(SiteRequestEnUS siteRequest_, String o) {
		return SiteState.staticSolrStrAgencyKeys(siteRequest_, SiteState.staticSolrAgencyKeys(siteRequest_, SiteState.staticSetAgencyKeys(siteRequest_, o)));
	}

	public List<Long> solrAgencyKeys() {
		List<Long> l = new ArrayList<Long>();
		for(Long o : agencyKeys) {
			l.add(SiteState.staticSolrAgencyKeys(siteRequest_, o));
		}
		return l;
	}

	public String strAgencyKeys() {
		return agencyKeys == null ? "" : agencyKeys.toString();
	}

	public List<Long> sqlAgencyKeys() {
		return agencyKeys;
	}

	public String jsonAgencyKeys() {
		return agencyKeys == null ? "" : agencyKeys.toString();
	}

	///////////////////////
	// stateCompleteName //
	///////////////////////

	/**	 The entity stateCompleteName
	 *	 is defined as null before being initialized. 
	 */
	@JsonProperty
	@JsonInclude(Include.NON_NULL)
	protected String stateCompleteName;
	@JsonIgnore
	public Wrap<String> stateCompleteNameWrap = new Wrap<String>().var("stateCompleteName").o(stateCompleteName);

	/**	<br/> The entity stateCompleteName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.state.SiteState&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:stateCompleteName">Find the entity stateCompleteName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _stateCompleteName(Wrap<String> c);

	public String getStateCompleteName() {
		return stateCompleteName;
	}
	public void setStateCompleteName(String o) {
		this.stateCompleteName = SiteState.staticSetStateCompleteName(siteRequest_, o);
		this.stateCompleteNameWrap.alreadyInitialized = true;
	}
	public static String staticSetStateCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteState stateCompleteNameInit() {
		if(!stateCompleteNameWrap.alreadyInitialized) {
			_stateCompleteName(stateCompleteNameWrap);
			if(stateCompleteName == null)
				setStateCompleteName(stateCompleteNameWrap.o);
			stateCompleteNameWrap.o(null);
		}
		stateCompleteNameWrap.alreadyInitialized(true);
		return (SiteState)this;
	}

	public static String staticSolrStateCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrStateCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqStateCompleteName(SiteRequestEnUS siteRequest_, String o) {
		return SiteState.staticSolrStrStateCompleteName(siteRequest_, SiteState.staticSolrStateCompleteName(siteRequest_, SiteState.staticSetStateCompleteName(siteRequest_, o)));
	}

	public String solrStateCompleteName() {
		return SiteState.staticSolrStateCompleteName(siteRequest_, stateCompleteName);
	}

	public String strStateCompleteName() {
		return stateCompleteName == null ? "" : stateCompleteName;
	}

	public String sqlStateCompleteName() {
		return stateCompleteName;
	}

	public String jsonStateCompleteName() {
		return stateCompleteName == null ? "" : stateCompleteName;
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedSiteState = false;

	public Future<Void> promiseDeepSiteState(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedSiteState) {
			alreadyInitializedSiteState = true;
			return promiseDeepSiteState();
		} else {
			return Future.succeededFuture();
		}
	}

	public Future<Void> promiseDeepSiteState() {
		Promise<Void> promise = Promise.promise();
		Promise<Void> promise2 = Promise.promise();
		promiseSiteState(promise2);
		promise2.future().onSuccess(a -> {
			super.promiseDeepCluster(siteRequest_).onSuccess(b -> {
				promise.complete();
			}).onFailure(ex -> {
				promise.fail(ex);
			});
		}).onFailure(ex -> {
			promise.fail(ex);
		});
		return promise.future();
	}

	public Future<Void> promiseSiteState(Promise<Void> promise) {
		Future.future(a -> a.complete()).compose(a -> {
			Promise<Void> promise2 = Promise.promise();
			try {
				stateKeyInit();
				stateNameInit();
				stateAbbreviationInit();
				imageLeftInit();
				imageTopInit();
				agencyKeysInit();
				stateCompleteNameInit();
				promise2.complete();
			} catch(Exception ex) {
				promise2.fail(ex);
			}
			return promise2.future();
		}).onSuccess(a -> {
			promise.complete();
		}).onFailure(ex -> {
			promise.fail(ex);
		});
		return promise.future();
	}

	@Override public Future<Void> promiseDeepForClass(SiteRequestEnUS siteRequest_) {
		return promiseDeepSiteState(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestSiteState(SiteRequestEnUS siteRequest_) {
			super.siteRequestCluster(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestSiteState(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainSiteState(v);
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
	public Object obtainSiteState(String var) {
		SiteState oSiteState = (SiteState)this;
		switch(var) {
			case "stateKey":
				return oSiteState.stateKey;
			case "stateName":
				return oSiteState.stateName;
			case "stateAbbreviation":
				return oSiteState.stateAbbreviation;
			case "imageLeft":
				return oSiteState.imageLeft;
			case "imageTop":
				return oSiteState.imageTop;
			case "agencyKeys":
				return oSiteState.agencyKeys;
			case "stateCompleteName":
				return oSiteState.stateCompleteName;
			default:
				return super.obtainCluster(var);
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
				o = attributeSiteState(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeSiteState(String var, Object val) {
		SiteState oSiteState = (SiteState)this;
		switch(var) {
			case "agencyKeys":
				oSiteState.addAgencyKeys((Long)val);
				if(!saves.contains("agencyKeys"))
					saves.add("agencyKeys");
				return val;
			default:
				return super.attributeCluster(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetSiteState(entityVar,  siteRequest_, o);
	}
	public static Object staticSetSiteState(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "stateKey":
			return SiteState.staticSetStateKey(siteRequest_, o);
		case "stateName":
			return SiteState.staticSetStateName(siteRequest_, o);
		case "stateAbbreviation":
			return SiteState.staticSetStateAbbreviation(siteRequest_, o);
		case "imageLeft":
			return SiteState.staticSetImageLeft(siteRequest_, o);
		case "imageTop":
			return SiteState.staticSetImageTop(siteRequest_, o);
		case "agencyKeys":
			return SiteState.staticSetAgencyKeys(siteRequest_, o);
		case "stateCompleteName":
			return SiteState.staticSetStateCompleteName(siteRequest_, o);
			default:
				return Cluster.staticSetCluster(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrSiteState(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrSiteState(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "stateKey":
			return SiteState.staticSolrStateKey(siteRequest_, (Long)o);
		case "stateName":
			return SiteState.staticSolrStateName(siteRequest_, (String)o);
		case "stateAbbreviation":
			return SiteState.staticSolrStateAbbreviation(siteRequest_, (String)o);
		case "imageLeft":
			return SiteState.staticSolrImageLeft(siteRequest_, (Integer)o);
		case "imageTop":
			return SiteState.staticSolrImageTop(siteRequest_, (Integer)o);
		case "agencyKeys":
			return SiteState.staticSolrAgencyKeys(siteRequest_, (Long)o);
		case "stateCompleteName":
			return SiteState.staticSolrStateCompleteName(siteRequest_, (String)o);
			default:
				return Cluster.staticSolrCluster(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrSiteState(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrSiteState(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "stateKey":
			return SiteState.staticSolrStrStateKey(siteRequest_, (Long)o);
		case "stateName":
			return SiteState.staticSolrStrStateName(siteRequest_, (String)o);
		case "stateAbbreviation":
			return SiteState.staticSolrStrStateAbbreviation(siteRequest_, (String)o);
		case "imageLeft":
			return SiteState.staticSolrStrImageLeft(siteRequest_, (Integer)o);
		case "imageTop":
			return SiteState.staticSolrStrImageTop(siteRequest_, (Integer)o);
		case "agencyKeys":
			return SiteState.staticSolrStrAgencyKeys(siteRequest_, (Long)o);
		case "stateCompleteName":
			return SiteState.staticSolrStrStateCompleteName(siteRequest_, (String)o);
			default:
				return Cluster.staticSolrStrCluster(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqSiteState(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqSiteState(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "stateKey":
			return SiteState.staticSolrFqStateKey(siteRequest_, o);
		case "stateName":
			return SiteState.staticSolrFqStateName(siteRequest_, o);
		case "stateAbbreviation":
			return SiteState.staticSolrFqStateAbbreviation(siteRequest_, o);
		case "imageLeft":
			return SiteState.staticSolrFqImageLeft(siteRequest_, o);
		case "imageTop":
			return SiteState.staticSolrFqImageTop(siteRequest_, o);
		case "agencyKeys":
			return SiteState.staticSolrFqAgencyKeys(siteRequest_, o);
		case "stateCompleteName":
			return SiteState.staticSolrFqStateCompleteName(siteRequest_, o);
			default:
				return Cluster.staticSolrFqCluster(entityVar,  siteRequest_, o);
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
					o = defineSiteState(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSiteState(String var, String val) {
		switch(var.toLowerCase()) {
			case "statename":
				if(val != null)
					setStateName(val);
				saves.add("stateName");
				return val;
			case "stateabbreviation":
				if(val != null)
					setStateAbbreviation(val);
				saves.add("stateAbbreviation");
				return val;
			case "imageleft":
				if(val != null)
					setImageLeft(val);
				saves.add("imageLeft");
				return val;
			case "imagetop":
				if(val != null)
					setImageTop(val);
				saves.add("imageTop");
				return val;
			default:
				return super.defineCluster(var, val);
		}
	}

	@Override public boolean defineForClass(String var, Object val) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		if(val != null) {
			for(String v : vars) {
				if(o == null)
					o = defineSiteState(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSiteState(String var, Object val) {
		switch(var.toLowerCase()) {
			case "statename":
				if(val instanceof String)
					setStateName((String)val);
				saves.add("stateName");
				return val;
			case "stateabbreviation":
				if(val instanceof String)
					setStateAbbreviation((String)val);
				saves.add("stateAbbreviation");
				return val;
			case "imageleft":
				if(val instanceof Integer)
					setImageLeft((Integer)val);
				saves.add("imageLeft");
				return val;
			case "imagetop":
				if(val instanceof Integer)
					setImageTop((Integer)val);
				saves.add("imageTop");
				return val;
			default:
				return super.defineCluster(var, val);
		}
	}

	/////////////
	// populate //
	/////////////

	@Override public void populateForClass(SolrDocument solrDocument) {
		populateSiteState(solrDocument);
	}
	public void populateSiteState(SolrDocument solrDocument) {
		SiteState oSiteState = (SiteState)this;
		saves = (List<String>)solrDocument.get("saves_stored_strings");
		if(saves != null) {

			if(saves.contains("stateKey")) {
				Long stateKey = (Long)solrDocument.get("stateKey_stored_long");
				if(stateKey != null)
					oSiteState.setStateKey(stateKey);
			}

			if(saves.contains("stateName")) {
				String stateName = (String)solrDocument.get("stateName_stored_string");
				if(stateName != null)
					oSiteState.setStateName(stateName);
			}

			if(saves.contains("stateAbbreviation")) {
				String stateAbbreviation = (String)solrDocument.get("stateAbbreviation_stored_string");
				if(stateAbbreviation != null)
					oSiteState.setStateAbbreviation(stateAbbreviation);
			}

			if(saves.contains("imageLeft")) {
				Integer imageLeft = (Integer)solrDocument.get("imageLeft_stored_int");
				if(imageLeft != null)
					oSiteState.setImageLeft(imageLeft);
			}

			if(saves.contains("imageTop")) {
				Integer imageTop = (Integer)solrDocument.get("imageTop_stored_int");
				if(imageTop != null)
					oSiteState.setImageTop(imageTop);
			}

			List<Long> agencyKeys = (List<Long>)solrDocument.get("agencyKeys_stored_longs");
			if(agencyKeys != null)
				oSiteState.agencyKeys.addAll(agencyKeys);

			if(saves.contains("stateCompleteName")) {
				String stateCompleteName = (String)solrDocument.get("stateCompleteName_stored_string");
				if(stateCompleteName != null)
					oSiteState.setStateCompleteName(stateCompleteName);
			}
		}

		super.populateCluster(solrDocument);
	}

	public void indexSiteState(SolrInputDocument document) {
		if(stateKey != null) {
			document.addField("stateKey_indexed_long", stateKey);
			document.addField("stateKey_stored_long", stateKey);
		}
		if(stateName != null) {
			document.addField("stateName_indexed_string", stateName);
			document.addField("stateName_stored_string", stateName);
		}
		if(stateAbbreviation != null) {
			document.addField("stateAbbreviation_indexed_string", stateAbbreviation);
			document.addField("stateAbbreviation_stored_string", stateAbbreviation);
		}
		if(imageLeft != null) {
			document.addField("imageLeft_indexed_int", imageLeft);
			document.addField("imageLeft_stored_int", imageLeft);
		}
		if(imageTop != null) {
			document.addField("imageTop_indexed_int", imageTop);
			document.addField("imageTop_stored_int", imageTop);
		}
		if(agencyKeys != null) {
			for(java.lang.Long o : agencyKeys) {
				document.addField("agencyKeys_indexed_longs", o);
			}
			for(java.lang.Long o : agencyKeys) {
				document.addField("agencyKeys_stored_longs", o);
			}
		}
		if(stateCompleteName != null) {
			document.addField("stateCompleteName_indexed_string", stateCompleteName);
			document.addField("stateCompleteName_stored_string", stateCompleteName);
		}
		super.indexCluster(document);

	}

	public static String varIndexedSiteState(String entityVar) {
		switch(entityVar) {
			case "stateKey":
				return "stateKey_indexed_long";
			case "stateName":
				return "stateName_indexed_string";
			case "stateAbbreviation":
				return "stateAbbreviation_indexed_string";
			case "imageLeft":
				return "imageLeft_indexed_int";
			case "imageTop":
				return "imageTop_indexed_int";
			case "agencyKeys":
				return "agencyKeys_indexed_longs";
			case "stateCompleteName":
				return "stateCompleteName_indexed_string";
			default:
				return Cluster.varIndexedCluster(entityVar);
		}
	}

	public static String varSearchSiteState(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSearchCluster(entityVar);
		}
	}

	public static String varSuggestedSiteState(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSuggestedCluster(entityVar);
		}
	}

	/////////////
	// store //
	/////////////

	@Override public void storeForClass(SolrDocument solrDocument) {
		storeSiteState(solrDocument);
	}
	public void storeSiteState(SolrDocument solrDocument) {
		SiteState oSiteState = (SiteState)this;

		oSiteState.setStateKey(Optional.ofNullable(solrDocument.get("stateKey_stored_long")).map(v -> v.toString()).orElse(null));
		oSiteState.setStateName(Optional.ofNullable(solrDocument.get("stateName_stored_string")).map(v -> v.toString()).orElse(null));
		oSiteState.setStateAbbreviation(Optional.ofNullable(solrDocument.get("stateAbbreviation_stored_string")).map(v -> v.toString()).orElse(null));
		oSiteState.setImageLeft(Optional.ofNullable(solrDocument.get("imageLeft_stored_int")).map(v -> v.toString()).orElse(null));
		oSiteState.setImageTop(Optional.ofNullable(solrDocument.get("imageTop_stored_int")).map(v -> v.toString()).orElse(null));
		Optional.ofNullable((List<?>)solrDocument.get("agencyKeys_stored_longs")).orElse(Arrays.asList()).stream().filter(v -> v != null).forEach(v -> {
			oSiteState.addAgencyKeys(v.toString());
		});
		oSiteState.setStateCompleteName(Optional.ofNullable(solrDocument.get("stateCompleteName_stored_string")).map(v -> v.toString()).orElse(null));

		super.storeCluster(solrDocument);
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestSiteState() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof SiteState) {
			SiteState original = (SiteState)o;
			if(!Objects.equals(stateKey, original.getStateKey()))
				apiRequest.addVars("stateKey");
			if(!Objects.equals(stateName, original.getStateName()))
				apiRequest.addVars("stateName");
			if(!Objects.equals(stateAbbreviation, original.getStateAbbreviation()))
				apiRequest.addVars("stateAbbreviation");
			if(!Objects.equals(imageLeft, original.getImageLeft()))
				apiRequest.addVars("imageLeft");
			if(!Objects.equals(imageTop, original.getImageTop()))
				apiRequest.addVars("imageTop");
			if(!Objects.equals(agencyKeys, original.getAgencyKeys()))
				apiRequest.addVars("agencyKeys");
			if(!Objects.equals(stateCompleteName, original.getStateCompleteName()))
				apiRequest.addVars("stateCompleteName");
			super.apiRequestCluster();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), stateKey, stateName, stateAbbreviation, imageLeft, imageTop, agencyKeys, stateCompleteName);
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof SiteState))
			return false;
		SiteState that = (SiteState)o;
		return super.equals(o)
				&& Objects.equals( stateKey, that.stateKey )
				&& Objects.equals( stateName, that.stateName )
				&& Objects.equals( stateAbbreviation, that.stateAbbreviation )
				&& Objects.equals( imageLeft, that.imageLeft )
				&& Objects.equals( imageTop, that.imageTop )
				&& Objects.equals( agencyKeys, that.agencyKeys )
				&& Objects.equals( stateCompleteName, that.stateCompleteName );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("SiteState { ");
		sb.append( "stateKey: " ).append(stateKey);
		sb.append( ", stateName: \"" ).append(stateName).append( "\"" );
		sb.append( ", stateAbbreviation: \"" ).append(stateAbbreviation).append( "\"" );
		sb.append( ", imageLeft: " ).append(imageLeft);
		sb.append( ", imageTop: " ).append(imageTop);
		sb.append( ", agencyKeys: " ).append(agencyKeys);
		sb.append( ", stateCompleteName: \"" ).append(stateCompleteName).append( "\"" );
		sb.append(" }");
		return sb.toString();
	}

	public static final String VAR_stateKey = "stateKey";
	public static final String VAR_stateName = "stateName";
	public static final String VAR_stateAbbreviation = "stateAbbreviation";
	public static final String VAR_imageLeft = "imageLeft";
	public static final String VAR_imageTop = "imageTop";
	public static final String VAR_agencyKeys = "agencyKeys";
	public static final String VAR_stateCompleteName = "stateCompleteName";
}
