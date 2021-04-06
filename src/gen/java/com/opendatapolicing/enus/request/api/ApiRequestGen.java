package com.opendatapolicing.enus.request.api;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.Date;
import java.time.ZonedDateTime;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.lang.Integer;
import java.text.NumberFormat;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import java.lang.Long;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.util.Locale;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.ZoneOffset;
import java.lang.String;
import java.math.RoundingMode;
import com.opendatapolicing.enus.wrap.Wrap;
import org.slf4j.Logger;
import java.math.MathContext;
import com.opendatapolicing.enus.writer.AllWriter;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import com.opendatapolicing.enus.request.api.ApiRequest;
import java.time.ZoneId;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import java.util.List;
import java.time.temporal.ChronoUnit;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.lang.Object;
import com.opendatapolicing.enus.cluster.Cluster;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.request.api.ApiRequest&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class ApiRequestGen<DEV> extends Object {
	protected static final Logger LOG = LoggerFactory.getLogger(ApiRequest.class);

	//////////////////
	// siteRequest_ //
	//////////////////

	/**	 The entity siteRequest_
	 *	 is defined as null before being initialized. 
	 */
	@JsonIgnore
	@JsonInclude(Include.NON_NULL)
	protected SiteRequestEnUS siteRequest_;
	@JsonIgnore
	public Wrap<SiteRequestEnUS> siteRequest_Wrap = new Wrap<SiteRequestEnUS>().p(this).c(SiteRequestEnUS.class).var("siteRequest_").o(siteRequest_);

	/**	<br/> The entity siteRequest_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.request.api.ApiRequest&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:siteRequest_">Find the entity siteRequest_ in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _siteRequest_(Wrap<SiteRequestEnUS> c);

	public SiteRequestEnUS getSiteRequest_() {
		return siteRequest_;
	}

	public void setSiteRequest_(SiteRequestEnUS siteRequest_) {
		this.siteRequest_ = siteRequest_;
		this.siteRequest_Wrap.alreadyInitialized = true;
	}
	public static SiteRequestEnUS staticSetSiteRequest_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected ApiRequest siteRequest_Init() {
		if(!siteRequest_Wrap.alreadyInitialized) {
			_siteRequest_(siteRequest_Wrap);
			if(siteRequest_ == null)
				setSiteRequest_(siteRequest_Wrap.o);
		}
		siteRequest_Wrap.alreadyInitialized(true);
		return (ApiRequest)this;
	}

	/////////////
	// created //
	/////////////

	/**	 The entity created
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected ZonedDateTime created;
	@JsonIgnore
	public Wrap<ZonedDateTime> createdWrap = new Wrap<ZonedDateTime>().p(this).c(ZonedDateTime.class).var("created").o(created);

	/**	<br/> The entity created
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.request.api.ApiRequest&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:created">Find the entity created in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _created(Wrap<ZonedDateTime> c);

	public ZonedDateTime getCreated() {
		return created;
	}

	public void setCreated(ZonedDateTime created) {
		this.created = created;
		this.createdWrap.alreadyInitialized = true;
	}
	public void setCreated(Instant o) {
		this.created = o == null ? null : ZonedDateTime.from(o).truncatedTo(ChronoUnit.MILLIS);
		this.createdWrap.alreadyInitialized = true;
	}
	/** Example: 2011-12-03T10:15:30+01:00 **/
	public void setCreated(String o) {
		this.created = ApiRequest.staticSetCreated(siteRequest_, o);
		this.createdWrap.alreadyInitialized = true;
	}
	public static ZonedDateTime staticSetCreated(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : ZonedDateTime.parse(o, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone()))).truncatedTo(ChronoUnit.MILLIS);
	}
	public void setCreated(Date o) {
		this.created = o == null ? null : ZonedDateTime.ofInstant(o.toInstant(), ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).truncatedTo(ChronoUnit.MILLIS);
		this.createdWrap.alreadyInitialized = true;
	}
	protected ApiRequest createdInit() {
		if(!createdWrap.alreadyInitialized) {
			_created(createdWrap);
			if(created == null)
				setCreated(createdWrap.o);
		}
		createdWrap.alreadyInitialized(true);
		return (ApiRequest)this;
	}

	public static Date staticSolrCreated(SiteRequestEnUS siteRequest_, ZonedDateTime o) {
		return o == null ? null : Date.from(o.toInstant());
	}

	public static String staticSolrStrCreated(SiteRequestEnUS siteRequest_, Date o) {
		return "\"" + DateTimeFormatter.ISO_DATE_TIME.format(o.toInstant().atOffset(ZoneOffset.UTC)) + "\"";
	}

	public static String staticSolrFqCreated(SiteRequestEnUS siteRequest_, String o) {
		return ApiRequest.staticSolrStrCreated(siteRequest_, ApiRequest.staticSolrCreated(siteRequest_, ApiRequest.staticSetCreated(siteRequest_, o)));
	}

	public Date solrCreated() {
		return ApiRequest.staticSolrCreated(siteRequest_, created);
	}

	public String strCreated() {
		return created == null ? "" : created.format(DateTimeFormatter.ofPattern("EEE d MMM yyyy H:mm:ss a zz", Locale.forLanguageTag("en-US")));
	}

	public OffsetDateTime sqlCreated() {
		return created == null ? null : created.toOffsetDateTime();
	}

	public String jsonCreated() {
		return created == null ? "" : created.format(DateTimeFormatter.ISO_DATE_TIME);
	}

	//////////
	// rows //
	//////////

	/**	 The entity rows
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Integer rows;
	@JsonIgnore
	public Wrap<Integer> rowsWrap = new Wrap<Integer>().p(this).c(Integer.class).var("rows").o(rows);

	/**	<br/> The entity rows
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.request.api.ApiRequest&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:rows">Find the entity rows in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _rows(Wrap<Integer> c);

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
		this.rowsWrap.alreadyInitialized = true;
	}
	public void setRows(String o) {
		this.rows = ApiRequest.staticSetRows(siteRequest_, o);
		this.rowsWrap.alreadyInitialized = true;
	}
	public static Integer staticSetRows(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Integer.parseInt(o);
		return null;
	}
	protected ApiRequest rowsInit() {
		if(!rowsWrap.alreadyInitialized) {
			_rows(rowsWrap);
			if(rows == null)
				setRows(rowsWrap.o);
		}
		rowsWrap.alreadyInitialized(true);
		return (ApiRequest)this;
	}

	public static Integer staticSolrRows(SiteRequestEnUS siteRequest_, Integer o) {
		return o;
	}

	public static String staticSolrStrRows(SiteRequestEnUS siteRequest_, Integer o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqRows(SiteRequestEnUS siteRequest_, String o) {
		return ApiRequest.staticSolrStrRows(siteRequest_, ApiRequest.staticSolrRows(siteRequest_, ApiRequest.staticSetRows(siteRequest_, o)));
	}

	public Integer solrRows() {
		return ApiRequest.staticSolrRows(siteRequest_, rows);
	}

	public String strRows() {
		return rows == null ? "" : rows.toString();
	}

	public Integer sqlRows() {
		return rows;
	}

	public String jsonRows() {
		return rows == null ? "" : rows.toString();
	}

	//////////////
	// numFound //
	//////////////

	/**	 The entity numFound
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long numFound;
	@JsonIgnore
	public Wrap<Long> numFoundWrap = new Wrap<Long>().p(this).c(Long.class).var("numFound").o(numFound);

	/**	<br/> The entity numFound
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.request.api.ApiRequest&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:numFound">Find the entity numFound in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _numFound(Wrap<Long> c);

	public Long getNumFound() {
		return numFound;
	}

	public void setNumFound(Long numFound) {
		this.numFound = numFound;
		this.numFoundWrap.alreadyInitialized = true;
	}
	public void setNumFound(String o) {
		this.numFound = ApiRequest.staticSetNumFound(siteRequest_, o);
		this.numFoundWrap.alreadyInitialized = true;
	}
	public static Long staticSetNumFound(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected ApiRequest numFoundInit() {
		if(!numFoundWrap.alreadyInitialized) {
			_numFound(numFoundWrap);
			if(numFound == null)
				setNumFound(numFoundWrap.o);
		}
		numFoundWrap.alreadyInitialized(true);
		return (ApiRequest)this;
	}

	public static Long staticSolrNumFound(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrNumFound(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqNumFound(SiteRequestEnUS siteRequest_, String o) {
		return ApiRequest.staticSolrStrNumFound(siteRequest_, ApiRequest.staticSolrNumFound(siteRequest_, ApiRequest.staticSetNumFound(siteRequest_, o)));
	}

	public Long solrNumFound() {
		return ApiRequest.staticSolrNumFound(siteRequest_, numFound);
	}

	public String strNumFound() {
		return numFound == null ? "" : numFound.toString();
	}

	public Long sqlNumFound() {
		return numFound;
	}

	public String jsonNumFound() {
		return numFound == null ? "" : numFound.toString();
	}

	//////////////
	// numPATCH //
	//////////////

	/**	 The entity numPATCH
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long numPATCH;
	@JsonIgnore
	public Wrap<Long> numPATCHWrap = new Wrap<Long>().p(this).c(Long.class).var("numPATCH").o(numPATCH);

	/**	<br/> The entity numPATCH
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.request.api.ApiRequest&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:numPATCH">Find the entity numPATCH in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _numPATCH(Wrap<Long> c);

	public Long getNumPATCH() {
		return numPATCH;
	}

	public void setNumPATCH(Long numPATCH) {
		this.numPATCH = numPATCH;
		this.numPATCHWrap.alreadyInitialized = true;
	}
	public void setNumPATCH(String o) {
		this.numPATCH = ApiRequest.staticSetNumPATCH(siteRequest_, o);
		this.numPATCHWrap.alreadyInitialized = true;
	}
	public static Long staticSetNumPATCH(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected ApiRequest numPATCHInit() {
		if(!numPATCHWrap.alreadyInitialized) {
			_numPATCH(numPATCHWrap);
			if(numPATCH == null)
				setNumPATCH(numPATCHWrap.o);
		}
		numPATCHWrap.alreadyInitialized(true);
		return (ApiRequest)this;
	}

	public static Long staticSolrNumPATCH(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrNumPATCH(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqNumPATCH(SiteRequestEnUS siteRequest_, String o) {
		return ApiRequest.staticSolrStrNumPATCH(siteRequest_, ApiRequest.staticSolrNumPATCH(siteRequest_, ApiRequest.staticSetNumPATCH(siteRequest_, o)));
	}

	public Long solrNumPATCH() {
		return ApiRequest.staticSolrNumPATCH(siteRequest_, numPATCH);
	}

	public String strNumPATCH() {
		return numPATCH == null ? "" : numPATCH.toString();
	}

	public Long sqlNumPATCH() {
		return numPATCH;
	}

	public String jsonNumPATCH() {
		return numPATCH == null ? "" : numPATCH.toString();
	}

	//////////
	// uuid //
	//////////

	/**	 The entity uuid
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String uuid;
	@JsonIgnore
	public Wrap<String> uuidWrap = new Wrap<String>().p(this).c(String.class).var("uuid").o(uuid);

	/**	<br/> The entity uuid
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.request.api.ApiRequest&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:uuid">Find the entity uuid in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _uuid(Wrap<String> c);

	public String getUuid() {
		return uuid;
	}
	public void setUuid(String o) {
		this.uuid = ApiRequest.staticSetUuid(siteRequest_, o);
		this.uuidWrap.alreadyInitialized = true;
	}
	public static String staticSetUuid(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected ApiRequest uuidInit() {
		if(!uuidWrap.alreadyInitialized) {
			_uuid(uuidWrap);
			if(uuid == null)
				setUuid(uuidWrap.o);
		}
		uuidWrap.alreadyInitialized(true);
		return (ApiRequest)this;
	}

	public static String staticSolrUuid(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrUuid(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqUuid(SiteRequestEnUS siteRequest_, String o) {
		return ApiRequest.staticSolrStrUuid(siteRequest_, ApiRequest.staticSolrUuid(siteRequest_, ApiRequest.staticSetUuid(siteRequest_, o)));
	}

	public String solrUuid() {
		return ApiRequest.staticSolrUuid(siteRequest_, uuid);
	}

	public String strUuid() {
		return uuid == null ? "" : uuid;
	}

	public String sqlUuid() {
		return uuid;
	}

	public String jsonUuid() {
		return uuid == null ? "" : uuid;
	}

	////////
	// id //
	////////

	/**	 The entity id
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String id;
	@JsonIgnore
	public Wrap<String> idWrap = new Wrap<String>().p(this).c(String.class).var("id").o(id);

	/**	<br/> The entity id
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.request.api.ApiRequest&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:id">Find the entity id in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _id(Wrap<String> c);

	public String getId() {
		return id;
	}
	public void setId(String o) {
		this.id = ApiRequest.staticSetId(siteRequest_, o);
		this.idWrap.alreadyInitialized = true;
	}
	public static String staticSetId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected ApiRequest idInit() {
		if(!idWrap.alreadyInitialized) {
			_id(idWrap);
			if(id == null)
				setId(idWrap.o);
		}
		idWrap.alreadyInitialized(true);
		return (ApiRequest)this;
	}

	public static String staticSolrId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqId(SiteRequestEnUS siteRequest_, String o) {
		return ApiRequest.staticSolrStrId(siteRequest_, ApiRequest.staticSolrId(siteRequest_, ApiRequest.staticSetId(siteRequest_, o)));
	}

	public String solrId() {
		return ApiRequest.staticSolrId(siteRequest_, id);
	}

	public String strId() {
		return id == null ? "" : id;
	}

	public String sqlId() {
		return id;
	}

	public String jsonId() {
		return id == null ? "" : id;
	}

	////////
	// pk //
	////////

	/**	 The entity pk
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long pk;
	@JsonIgnore
	public Wrap<Long> pkWrap = new Wrap<Long>().p(this).c(Long.class).var("pk").o(pk);

	/**	<br/> The entity pk
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.request.api.ApiRequest&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pk">Find the entity pk in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pk(Wrap<Long> c);

	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
		this.pkWrap.alreadyInitialized = true;
	}
	public void setPk(String o) {
		this.pk = ApiRequest.staticSetPk(siteRequest_, o);
		this.pkWrap.alreadyInitialized = true;
	}
	public static Long staticSetPk(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected ApiRequest pkInit() {
		if(!pkWrap.alreadyInitialized) {
			_pk(pkWrap);
			if(pk == null)
				setPk(pkWrap.o);
		}
		pkWrap.alreadyInitialized(true);
		return (ApiRequest)this;
	}

	public static Long staticSolrPk(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrPk(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPk(SiteRequestEnUS siteRequest_, String o) {
		return ApiRequest.staticSolrStrPk(siteRequest_, ApiRequest.staticSolrPk(siteRequest_, ApiRequest.staticSetPk(siteRequest_, o)));
	}

	public Long solrPk() {
		return ApiRequest.staticSolrPk(siteRequest_, pk);
	}

	public String strPk() {
		return pk == null ? "" : pk.toString();
	}

	public Long sqlPk() {
		return pk;
	}

	public String jsonPk() {
		return pk == null ? "" : pk.toString();
	}

	//////////////
	// original //
	//////////////

	/**	 The entity original
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Object original;
	@JsonIgnore
	public Wrap<Object> originalWrap = new Wrap<Object>().p(this).c(Object.class).var("original").o(original);

	/**	<br/> The entity original
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.request.api.ApiRequest&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:original">Find the entity original in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _original(Wrap<Object> c);

	public Object getOriginal() {
		return original;
	}

	public void setOriginal(Object original) {
		this.original = original;
		this.originalWrap.alreadyInitialized = true;
	}
	public static Object staticSetOriginal(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected ApiRequest originalInit() {
		if(!originalWrap.alreadyInitialized) {
			_original(originalWrap);
			if(original == null)
				setOriginal(originalWrap.o);
		}
		originalWrap.alreadyInitialized(true);
		return (ApiRequest)this;
	}

	/////////
	// pks //
	/////////

	/**	 The entity pks
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> pks = new ArrayList<Long>();
	@JsonIgnore
	public Wrap<List<Long>> pksWrap = new Wrap<List<Long>>().p(this).c(List.class).var("pks").o(pks);

	/**	<br/> The entity pks
	 *  It is constructed before being initialized with the constructor by default List<Long>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.request.api.ApiRequest&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pks">Find the entity pks in Solr</a>
	 * <br/>
	 * @param pks is the entity already constructed. 
	 **/
	protected abstract void _pks(List<Long> c);

	public List<Long> getPks() {
		return pks;
	}

	public void setPks(List<Long> pks) {
		this.pks = pks;
		this.pksWrap.alreadyInitialized = true;
	}
	public void setPks(String o) {
		Long l = ApiRequest.staticSetPks(siteRequest_, o);
		if(l != null)
			addPks(l);
		this.pksWrap.alreadyInitialized = true;
	}
	public static Long staticSetPks(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	public ApiRequest addPks(Long...objets) {
		for(Long o : objets) {
			addPks(o);
		}
		return (ApiRequest)this;
	}
	public ApiRequest addPks(Long o) {
		if(o != null && !pks.contains(o))
			this.pks.add(o);
		return (ApiRequest)this;
	}
	public void setPks(JsonArray objets) {
		pks.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addPks(o);
		}
	}
	public ApiRequest addPks(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addPks(p);
		}
		return (ApiRequest)this;
	}
	protected ApiRequest pksInit() {
		if(!pksWrap.alreadyInitialized) {
			_pks(pks);
		}
		pksWrap.alreadyInitialized(true);
		return (ApiRequest)this;
	}

	public static Long staticSolrPks(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrPks(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPks(SiteRequestEnUS siteRequest_, String o) {
		return ApiRequest.staticSolrStrPks(siteRequest_, ApiRequest.staticSolrPks(siteRequest_, ApiRequest.staticSetPks(siteRequest_, o)));
	}

	public List<Long> solrPks() {
		List<Long> l = new ArrayList<Long>();
		for(Long o : pks) {
			l.add(ApiRequest.staticSolrPks(siteRequest_, o));
		}
		return l;
	}

	public String strPks() {
		return pks == null ? "" : pks.toString();
	}

	public List<Long> sqlPks() {
		return pks;
	}

	public String jsonPks() {
		return pks == null ? "" : pks.toString();
	}

	/////////////
	// classes //
	/////////////

	/**	 The entity classes
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<String>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected List<String> classes = new ArrayList<String>();
	@JsonIgnore
	public Wrap<List<String>> classesWrap = new Wrap<List<String>>().p(this).c(List.class).var("classes").o(classes);

	/**	<br/> The entity classes
	 *  It is constructed before being initialized with the constructor by default List<String>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.request.api.ApiRequest&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:classes">Find the entity classes in Solr</a>
	 * <br/>
	 * @param classes is the entity already constructed. 
	 **/
	protected abstract void _classes(List<String> c);

	public List<String> getClasses() {
		return classes;
	}

	public void setClasses(List<String> classes) {
		this.classes = classes;
		this.classesWrap.alreadyInitialized = true;
	}
	public static String staticSetClasses(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	public ApiRequest addClasses(String...objets) {
		for(String o : objets) {
			addClasses(o);
		}
		return (ApiRequest)this;
	}
	public ApiRequest addClasses(String o) {
		if(o != null && !classes.contains(o))
			this.classes.add(o);
		return (ApiRequest)this;
	}
	public void setClasses(JsonArray objets) {
		classes.clear();
		for(int i = 0; i < objets.size(); i++) {
			String o = objets.getString(i);
			addClasses(o);
		}
	}
	protected ApiRequest classesInit() {
		if(!classesWrap.alreadyInitialized) {
			_classes(classes);
		}
		classesWrap.alreadyInitialized(true);
		return (ApiRequest)this;
	}

	public static String staticSolrClasses(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrClasses(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqClasses(SiteRequestEnUS siteRequest_, String o) {
		return ApiRequest.staticSolrStrClasses(siteRequest_, ApiRequest.staticSolrClasses(siteRequest_, ApiRequest.staticSetClasses(siteRequest_, o)));
	}

	public List<String> solrClasses() {
		List<String> l = new ArrayList<String>();
		for(String o : classes) {
			l.add(ApiRequest.staticSolrClasses(siteRequest_, o));
		}
		return l;
	}

	public String strClasses() {
		return classes == null ? "" : classes.toString();
	}

	public List<String> sqlClasses() {
		return classes;
	}

	public String jsonClasses() {
		return classes == null ? "" : classes.toString();
	}

	//////////
	// vars //
	//////////

	/**	 The entity vars
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<String>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected List<String> vars = new ArrayList<String>();
	@JsonIgnore
	public Wrap<List<String>> varsWrap = new Wrap<List<String>>().p(this).c(List.class).var("vars").o(vars);

	/**	<br/> The entity vars
	 *  It is constructed before being initialized with the constructor by default List<String>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.request.api.ApiRequest&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:vars">Find the entity vars in Solr</a>
	 * <br/>
	 * @param vars is the entity already constructed. 
	 **/
	protected abstract void _vars(List<String> c);

	public List<String> getVars() {
		return vars;
	}

	public void setVars(List<String> vars) {
		this.vars = vars;
		this.varsWrap.alreadyInitialized = true;
	}
	public static String staticSetVars(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	public ApiRequest addVars(String...objets) {
		for(String o : objets) {
			addVars(o);
		}
		return (ApiRequest)this;
	}
	public ApiRequest addVars(String o) {
		if(o != null && !vars.contains(o))
			this.vars.add(o);
		return (ApiRequest)this;
	}
	public void setVars(JsonArray objets) {
		vars.clear();
		for(int i = 0; i < objets.size(); i++) {
			String o = objets.getString(i);
			addVars(o);
		}
	}
	protected ApiRequest varsInit() {
		if(!varsWrap.alreadyInitialized) {
			_vars(vars);
		}
		varsWrap.alreadyInitialized(true);
		return (ApiRequest)this;
	}

	public static String staticSolrVars(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrVars(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqVars(SiteRequestEnUS siteRequest_, String o) {
		return ApiRequest.staticSolrStrVars(siteRequest_, ApiRequest.staticSolrVars(siteRequest_, ApiRequest.staticSetVars(siteRequest_, o)));
	}

	public List<String> solrVars() {
		List<String> l = new ArrayList<String>();
		for(String o : vars) {
			l.add(ApiRequest.staticSolrVars(siteRequest_, o));
		}
		return l;
	}

	public String strVars() {
		return vars == null ? "" : vars.toString();
	}

	public List<String> sqlVars() {
		return vars;
	}

	public String jsonVars() {
		return vars == null ? "" : vars.toString();
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedApiRequest = false;

	public ApiRequest initDeepApiRequest(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedApiRequest) {
			alreadyInitializedApiRequest = true;
			initDeepApiRequest();
		}
		return (ApiRequest)this;
	}

	public void initDeepApiRequest() {
		initApiRequest();
	}

	public void initApiRequest() {
		siteRequest_Init();
		createdInit();
		rowsInit();
		numFoundInit();
		numPATCHInit();
		uuidInit();
		idInit();
		pkInit();
		originalInit();
		pksInit();
		classesInit();
		varsInit();
	}

	public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepApiRequest(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestApiRequest(SiteRequestEnUS siteRequest_) {
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestApiRequest(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainApiRequest(v);
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
	public Object obtainApiRequest(String var) {
		ApiRequest oApiRequest = (ApiRequest)this;
		switch(var) {
			case "siteRequest_":
				return oApiRequest.siteRequest_;
			case "created":
				return oApiRequest.created;
			case "rows":
				return oApiRequest.rows;
			case "numFound":
				return oApiRequest.numFound;
			case "numPATCH":
				return oApiRequest.numPATCH;
			case "uuid":
				return oApiRequest.uuid;
			case "id":
				return oApiRequest.id;
			case "pk":
				return oApiRequest.pk;
			case "original":
				return oApiRequest.original;
			case "pks":
				return oApiRequest.pks;
			case "classes":
				return oApiRequest.classes;
			case "vars":
				return oApiRequest.vars;
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
				o = attributeApiRequest(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeApiRequest(String var, Object val) {
		ApiRequest oApiRequest = (ApiRequest)this;
		switch(var) {
			default:
				return null;
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetApiRequest(entityVar,  siteRequest_, o);
	}
	public static Object staticSetApiRequest(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "created":
			return ApiRequest.staticSetCreated(siteRequest_, o);
		case "rows":
			return ApiRequest.staticSetRows(siteRequest_, o);
		case "numFound":
			return ApiRequest.staticSetNumFound(siteRequest_, o);
		case "numPATCH":
			return ApiRequest.staticSetNumPATCH(siteRequest_, o);
		case "uuid":
			return ApiRequest.staticSetUuid(siteRequest_, o);
		case "id":
			return ApiRequest.staticSetId(siteRequest_, o);
		case "pk":
			return ApiRequest.staticSetPk(siteRequest_, o);
		case "pks":
			return ApiRequest.staticSetPks(siteRequest_, o);
		case "classes":
			return ApiRequest.staticSetClasses(siteRequest_, o);
		case "vars":
			return ApiRequest.staticSetVars(siteRequest_, o);
			default:
				return null;
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrApiRequest(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrApiRequest(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "created":
			return ApiRequest.staticSolrCreated(siteRequest_, (ZonedDateTime)o);
		case "rows":
			return ApiRequest.staticSolrRows(siteRequest_, (Integer)o);
		case "numFound":
			return ApiRequest.staticSolrNumFound(siteRequest_, (Long)o);
		case "numPATCH":
			return ApiRequest.staticSolrNumPATCH(siteRequest_, (Long)o);
		case "uuid":
			return ApiRequest.staticSolrUuid(siteRequest_, (String)o);
		case "id":
			return ApiRequest.staticSolrId(siteRequest_, (String)o);
		case "pk":
			return ApiRequest.staticSolrPk(siteRequest_, (Long)o);
		case "pks":
			return ApiRequest.staticSolrPks(siteRequest_, (Long)o);
		case "classes":
			return ApiRequest.staticSolrClasses(siteRequest_, (String)o);
		case "vars":
			return ApiRequest.staticSolrVars(siteRequest_, (String)o);
			default:
				return null;
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrApiRequest(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrApiRequest(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "created":
			return ApiRequest.staticSolrStrCreated(siteRequest_, (Date)o);
		case "rows":
			return ApiRequest.staticSolrStrRows(siteRequest_, (Integer)o);
		case "numFound":
			return ApiRequest.staticSolrStrNumFound(siteRequest_, (Long)o);
		case "numPATCH":
			return ApiRequest.staticSolrStrNumPATCH(siteRequest_, (Long)o);
		case "uuid":
			return ApiRequest.staticSolrStrUuid(siteRequest_, (String)o);
		case "id":
			return ApiRequest.staticSolrStrId(siteRequest_, (String)o);
		case "pk":
			return ApiRequest.staticSolrStrPk(siteRequest_, (Long)o);
		case "pks":
			return ApiRequest.staticSolrStrPks(siteRequest_, (Long)o);
		case "classes":
			return ApiRequest.staticSolrStrClasses(siteRequest_, (String)o);
		case "vars":
			return ApiRequest.staticSolrStrVars(siteRequest_, (String)o);
			default:
				return null;
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqApiRequest(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqApiRequest(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "created":
			return ApiRequest.staticSolrFqCreated(siteRequest_, o);
		case "rows":
			return ApiRequest.staticSolrFqRows(siteRequest_, o);
		case "numFound":
			return ApiRequest.staticSolrFqNumFound(siteRequest_, o);
		case "numPATCH":
			return ApiRequest.staticSolrFqNumPATCH(siteRequest_, o);
		case "uuid":
			return ApiRequest.staticSolrFqUuid(siteRequest_, o);
		case "id":
			return ApiRequest.staticSolrFqId(siteRequest_, o);
		case "pk":
			return ApiRequest.staticSolrFqPk(siteRequest_, o);
		case "pks":
			return ApiRequest.staticSolrFqPks(siteRequest_, o);
		case "classes":
			return ApiRequest.staticSolrFqClasses(siteRequest_, o);
		case "vars":
			return ApiRequest.staticSolrFqVars(siteRequest_, o);
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
					o = defineApiRequest(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineApiRequest(String var, String val) {
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
					o = defineApiRequest(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineApiRequest(String var, Object val) {
		switch(var.toLowerCase()) {
			default:
				return null;
		}
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestApiRequest() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof ApiRequest) {
			ApiRequest original = (ApiRequest)o;
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
		if(!(o instanceof ApiRequest))
			return false;
		ApiRequest that = (ApiRequest)o;
		return true;
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("ApiRequest { ");
		sb.append(" }");
		return sb.toString();
	}
}
