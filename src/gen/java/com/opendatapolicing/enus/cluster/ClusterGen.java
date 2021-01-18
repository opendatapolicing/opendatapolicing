package com.opendatapolicing.enus.cluster;

import java.util.Arrays;
import java.util.Date;
import java.time.ZonedDateTime;
import org.apache.commons.lang3.StringUtils;
import java.lang.Long;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.util.Locale;
import io.vertx.core.json.JsonObject;
import java.time.ZoneOffset;
import io.vertx.core.logging.Logger;
import java.math.RoundingMode;
import com.opendatapolicing.enus.wrap.Wrap;
import java.math.MathContext;
import java.util.Set;
import com.opendatapolicing.enus.writer.AllWriter;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import com.opendatapolicing.enus.context.SiteContextEnUS;
import com.opendatapolicing.enus.request.api.ApiRequest;
import java.time.ZoneId;
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
import java.time.LocalDateTime;
import java.util.HashMap;
import java.text.NumberFormat;
import io.vertx.core.logging.LoggerFactory;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.Boolean;
import java.lang.String;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.apache.solr.client.solrj.SolrClient;
import io.vertx.core.json.JsonArray;
import org.apache.solr.common.SolrDocument;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.math.NumberUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.lang.Object;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class ClusterGen<DEV> extends Object {
	protected static final Logger LOGGER = LoggerFactory.getLogger(Cluster.class);

	public static final List<String> ROLES = Arrays.asList("SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("User");

	//////////////////
	// siteRequest_ //
	//////////////////

	/**	 The entity siteRequest_
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected SiteRequestEnUS siteRequest_;
	@JsonIgnore
	public Wrap<SiteRequestEnUS> siteRequest_Wrap = new Wrap<SiteRequestEnUS>().p(this).c(SiteRequestEnUS.class).var("siteRequest_").o(siteRequest_);

	/**	<br/> The entity siteRequest_
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:siteRequest_">Find the entity siteRequest_ in Solr</a>
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
	protected Cluster siteRequest_Init() {
		if(!siteRequest_Wrap.alreadyInitialized) {
			_siteRequest_(siteRequest_Wrap);
			if(siteRequest_ == null)
				setSiteRequest_(siteRequest_Wrap.o);
		}
		siteRequest_Wrap.alreadyInitialized(true);
		return (Cluster)this;
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pk">Find the entity pk in Solr</a>
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
		this.pk = Cluster.staticSetPk(siteRequest_, o);
		this.pkWrap.alreadyInitialized = true;
	}
	public static Long staticSetPk(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected Cluster pkInit() {
		if(!pkWrap.alreadyInitialized) {
			_pk(pkWrap);
			if(pk == null)
				setPk(pkWrap.o);
		}
		pkWrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static Long staticSolrPk(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrPk(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPk(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrPk(siteRequest_, Cluster.staticSolrPk(siteRequest_, Cluster.staticSetPk(siteRequest_, o)));
	}

	public Long solrPk() {
		return Cluster.staticSolrPk(siteRequest_, pk);
	}

	public String strPk() {
		return pk == null ? "" : pk.toString();
	}

	public String jsonPk() {
		return pk == null ? "" : pk.toString();
	}

	public String nomAffichagePk() {
		return null;
	}

	public String htmTooltipPk() {
		return null;
	}

	public String htmPk() {
		return pk == null ? "" : StringEscapeUtils.escapeHtml4(strPk());
	}

	///////////////
	// inheritPk //
	///////////////

	/**	 The entity inheritPk
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long inheritPk;
	@JsonIgnore
	public Wrap<Long> inheritPkWrap = new Wrap<Long>().p(this).c(Long.class).var("inheritPk").o(inheritPk);

	/**	<br/> The entity inheritPk
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:inheritPk">Find the entity inheritPk in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _inheritPk(Wrap<Long> c);

	public Long getInheritPk() {
		return inheritPk;
	}

	public void setInheritPk(Long inheritPk) {
		this.inheritPk = inheritPk;
		this.inheritPkWrap.alreadyInitialized = true;
	}
	public void setInheritPk(String o) {
		this.inheritPk = Cluster.staticSetInheritPk(siteRequest_, o);
		this.inheritPkWrap.alreadyInitialized = true;
	}
	public static Long staticSetInheritPk(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected Cluster inheritPkInit() {
		if(!inheritPkWrap.alreadyInitialized) {
			_inheritPk(inheritPkWrap);
			if(inheritPk == null)
				setInheritPk(inheritPkWrap.o);
		}
		inheritPkWrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static Long staticSolrInheritPk(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrInheritPk(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqInheritPk(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrInheritPk(siteRequest_, Cluster.staticSolrInheritPk(siteRequest_, Cluster.staticSetInheritPk(siteRequest_, o)));
	}

	public Long solrInheritPk() {
		return Cluster.staticSolrInheritPk(siteRequest_, inheritPk);
	}

	public String strInheritPk() {
		return inheritPk == null ? "" : inheritPk.toString();
	}

	public String jsonInheritPk() {
		return inheritPk == null ? "" : inheritPk.toString();
	}

	public String nomAffichageInheritPk() {
		return null;
	}

	public String htmTooltipInheritPk() {
		return null;
	}

	public String htmInheritPk() {
		return inheritPk == null ? "" : StringEscapeUtils.escapeHtml4(strInheritPk());
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:id">Find the entity id in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _id(Wrap<String> c);

	public String getId() {
		return id;
	}
	public void setId(String o) {
		this.id = Cluster.staticSetId(siteRequest_, o);
		this.idWrap.alreadyInitialized = true;
	}
	public static String staticSetId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected Cluster idInit() {
		if(!idWrap.alreadyInitialized) {
			_id(idWrap);
			if(id == null)
				setId(idWrap.o);
		}
		idWrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static String staticSolrId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqId(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrId(siteRequest_, Cluster.staticSolrId(siteRequest_, Cluster.staticSetId(siteRequest_, o)));
	}

	public String solrId() {
		return Cluster.staticSolrId(siteRequest_, id);
	}

	public String strId() {
		return id == null ? "" : id;
	}

	public String jsonId() {
		return id == null ? "" : id;
	}

	public String nomAffichageId() {
		return null;
	}

	public String htmTooltipId() {
		return null;
	}

	public String htmId() {
		return id == null ? "" : StringEscapeUtils.escapeHtml4(strId());
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:created">Find the entity created in Solr</a>
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
		this.created = Cluster.staticSetCreated(siteRequest_, o);
		this.createdWrap.alreadyInitialized = true;
	}
	public static ZonedDateTime staticSetCreated(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : ZonedDateTime.parse(o, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone()))).truncatedTo(ChronoUnit.MILLIS);
	}
	public void setCreated(Date o) {
		this.created = o == null ? null : ZonedDateTime.ofInstant(o.toInstant(), ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).truncatedTo(ChronoUnit.MILLIS);
		this.createdWrap.alreadyInitialized = true;
	}
	protected Cluster createdInit() {
		if(!createdWrap.alreadyInitialized) {
			_created(createdWrap);
			if(created == null)
				setCreated(createdWrap.o);
		}
		createdWrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static Date staticSolrCreated(SiteRequestEnUS siteRequest_, ZonedDateTime o) {
		return o == null ? null : Date.from(o.toInstant());
	}

	public static String staticSolrStrCreated(SiteRequestEnUS siteRequest_, Date o) {
		return "\"" + DateTimeFormatter.ISO_DATE_TIME.format(o.toInstant().atOffset(ZoneOffset.UTC)) + "\"";
	}

	public static String staticSolrFqCreated(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrCreated(siteRequest_, Cluster.staticSolrCreated(siteRequest_, Cluster.staticSetCreated(siteRequest_, o)));
	}

	public Date solrCreated() {
		return Cluster.staticSolrCreated(siteRequest_, created);
	}

	public String strCreated() {
		return created == null ? "" : created.format(DateTimeFormatter.ofPattern("EEE d MMM yyyy H:mm:ss a zz", Locale.forLanguageTag("en-US")));
	}

	public String jsonCreated() {
		return created == null ? "" : created.format(DateTimeFormatter.ISO_DATE_TIME);
	}

	public String nomAffichageCreated() {
		return null;
	}

	public String htmTooltipCreated() {
		return null;
	}

	public String htmCreated() {
		return created == null ? "" : StringEscapeUtils.escapeHtml4(strCreated());
	}

	//////////////
	// modified //
	//////////////

	/**	 The entity modified
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected ZonedDateTime modified;
	@JsonIgnore
	public Wrap<ZonedDateTime> modifiedWrap = new Wrap<ZonedDateTime>().p(this).c(ZonedDateTime.class).var("modified").o(modified);

	/**	<br/> The entity modified
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:modified">Find the entity modified in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _modified(Wrap<ZonedDateTime> c);

	public ZonedDateTime getModified() {
		return modified;
	}

	public void setModified(ZonedDateTime modified) {
		this.modified = modified;
		this.modifiedWrap.alreadyInitialized = true;
	}
	public void setModified(Instant o) {
		this.modified = o == null ? null : ZonedDateTime.from(o).truncatedTo(ChronoUnit.MILLIS);
		this.modifiedWrap.alreadyInitialized = true;
	}
	/** Example: 2011-12-03T10:15:30+01:00 **/
	public void setModified(String o) {
		this.modified = Cluster.staticSetModified(siteRequest_, o);
		this.modifiedWrap.alreadyInitialized = true;
	}
	public static ZonedDateTime staticSetModified(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : ZonedDateTime.parse(o, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone()))).truncatedTo(ChronoUnit.MILLIS);
	}
	public void setModified(Date o) {
		this.modified = o == null ? null : ZonedDateTime.ofInstant(o.toInstant(), ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())).truncatedTo(ChronoUnit.MILLIS);
		this.modifiedWrap.alreadyInitialized = true;
	}
	protected Cluster modifiedInit() {
		if(!modifiedWrap.alreadyInitialized) {
			_modified(modifiedWrap);
			if(modified == null)
				setModified(modifiedWrap.o);
		}
		modifiedWrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static Date staticSolrModified(SiteRequestEnUS siteRequest_, ZonedDateTime o) {
		return o == null ? null : Date.from(o.toInstant());
	}

	public static String staticSolrStrModified(SiteRequestEnUS siteRequest_, Date o) {
		return "\"" + DateTimeFormatter.ISO_DATE_TIME.format(o.toInstant().atOffset(ZoneOffset.UTC)) + "\"";
	}

	public static String staticSolrFqModified(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrModified(siteRequest_, Cluster.staticSolrModified(siteRequest_, Cluster.staticSetModified(siteRequest_, o)));
	}

	public Date solrModified() {
		return Cluster.staticSolrModified(siteRequest_, modified);
	}

	public String strModified() {
		return modified == null ? "" : modified.format(DateTimeFormatter.ofPattern("EEE d MMM yyyy H:mm:ss a zz", Locale.forLanguageTag("en-US")));
	}

	public String jsonModified() {
		return modified == null ? "" : modified.format(DateTimeFormatter.ISO_DATE_TIME);
	}

	public String nomAffichageModified() {
		return null;
	}

	public String htmTooltipModified() {
		return null;
	}

	public String htmModified() {
		return modified == null ? "" : StringEscapeUtils.escapeHtml4(strModified());
	}

	///////////////////////////////
	// modifiedIsoOffsetDateTime //
	///////////////////////////////

	/**	 The entity modifiedIsoOffsetDateTime
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String modifiedIsoOffsetDateTime;
	@JsonIgnore
	public Wrap<String> modifiedIsoOffsetDateTimeWrap = new Wrap<String>().p(this).c(String.class).var("modifiedIsoOffsetDateTime").o(modifiedIsoOffsetDateTime);

	/**	<br/> The entity modifiedIsoOffsetDateTime
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:modifiedIsoOffsetDateTime">Find the entity modifiedIsoOffsetDateTime in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _modifiedIsoOffsetDateTime(Wrap<String> c);

	public String getModifiedIsoOffsetDateTime() {
		return modifiedIsoOffsetDateTime;
	}
	public void setModifiedIsoOffsetDateTime(String o) {
		this.modifiedIsoOffsetDateTime = Cluster.staticSetModifiedIsoOffsetDateTime(siteRequest_, o);
		this.modifiedIsoOffsetDateTimeWrap.alreadyInitialized = true;
	}
	public static String staticSetModifiedIsoOffsetDateTime(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected Cluster modifiedIsoOffsetDateTimeInit() {
		if(!modifiedIsoOffsetDateTimeWrap.alreadyInitialized) {
			_modifiedIsoOffsetDateTime(modifiedIsoOffsetDateTimeWrap);
			if(modifiedIsoOffsetDateTime == null)
				setModifiedIsoOffsetDateTime(modifiedIsoOffsetDateTimeWrap.o);
		}
		modifiedIsoOffsetDateTimeWrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static String staticSolrModifiedIsoOffsetDateTime(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrModifiedIsoOffsetDateTime(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqModifiedIsoOffsetDateTime(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrModifiedIsoOffsetDateTime(siteRequest_, Cluster.staticSolrModifiedIsoOffsetDateTime(siteRequest_, Cluster.staticSetModifiedIsoOffsetDateTime(siteRequest_, o)));
	}

	public String solrModifiedIsoOffsetDateTime() {
		return Cluster.staticSolrModifiedIsoOffsetDateTime(siteRequest_, modifiedIsoOffsetDateTime);
	}

	public String strModifiedIsoOffsetDateTime() {
		return modifiedIsoOffsetDateTime == null ? "" : modifiedIsoOffsetDateTime;
	}

	public String jsonModifiedIsoOffsetDateTime() {
		return modifiedIsoOffsetDateTime == null ? "" : modifiedIsoOffsetDateTime;
	}

	public String nomAffichageModifiedIsoOffsetDateTime() {
		return null;
	}

	public String htmTooltipModifiedIsoOffsetDateTime() {
		return null;
	}

	public String htmModifiedIsoOffsetDateTime() {
		return modifiedIsoOffsetDateTime == null ? "" : StringEscapeUtils.escapeHtml4(strModifiedIsoOffsetDateTime());
	}

	//////////////
	// archived //
	//////////////

	/**	 The entity archived
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean archived;
	@JsonIgnore
	public Wrap<Boolean> archivedWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("archived").o(archived);

	/**	<br/> The entity archived
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:archived">Find the entity archived in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _archived(Wrap<Boolean> c);

	public Boolean getArchived() {
		return archived;
	}

	public void setArchived(Boolean archived) {
		this.archived = archived;
		this.archivedWrap.alreadyInitialized = true;
	}
	public void setArchived(String o) {
		this.archived = Cluster.staticSetArchived(siteRequest_, o);
		this.archivedWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetArchived(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected Cluster archivedInit() {
		if(!archivedWrap.alreadyInitialized) {
			_archived(archivedWrap);
			if(archived == null)
				setArchived(archivedWrap.o);
		}
		archivedWrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static Boolean staticSolrArchived(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrArchived(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqArchived(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrArchived(siteRequest_, Cluster.staticSolrArchived(siteRequest_, Cluster.staticSetArchived(siteRequest_, o)));
	}

	public Boolean solrArchived() {
		return Cluster.staticSolrArchived(siteRequest_, archived);
	}

	public String strArchived() {
		return archived == null ? "" : archived.toString();
	}

	public String jsonArchived() {
		return archived == null ? "" : archived.toString();
	}

	public String nomAffichageArchived() {
		return null;
	}

	public String htmTooltipArchived() {
		return null;
	}

	public String htmArchived() {
		return archived == null ? "" : StringEscapeUtils.escapeHtml4(strArchived());
	}

	/////////////
	// deleted //
	/////////////

	/**	 The entity deleted
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean deleted;
	@JsonIgnore
	public Wrap<Boolean> deletedWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("deleted").o(deleted);

	/**	<br/> The entity deleted
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:deleted">Find the entity deleted in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _deleted(Wrap<Boolean> c);

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
		this.deletedWrap.alreadyInitialized = true;
	}
	public void setDeleted(String o) {
		this.deleted = Cluster.staticSetDeleted(siteRequest_, o);
		this.deletedWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetDeleted(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected Cluster deletedInit() {
		if(!deletedWrap.alreadyInitialized) {
			_deleted(deletedWrap);
			if(deleted == null)
				setDeleted(deletedWrap.o);
		}
		deletedWrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static Boolean staticSolrDeleted(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrDeleted(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqDeleted(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrDeleted(siteRequest_, Cluster.staticSolrDeleted(siteRequest_, Cluster.staticSetDeleted(siteRequest_, o)));
	}

	public Boolean solrDeleted() {
		return Cluster.staticSolrDeleted(siteRequest_, deleted);
	}

	public String strDeleted() {
		return deleted == null ? "" : deleted.toString();
	}

	public String jsonDeleted() {
		return deleted == null ? "" : deleted.toString();
	}

	public String nomAffichageDeleted() {
		return null;
	}

	public String htmTooltipDeleted() {
		return null;
	}

	public String htmDeleted() {
		return deleted == null ? "" : StringEscapeUtils.escapeHtml4(strDeleted());
	}

	////////////////////////
	// classCanonicalName //
	////////////////////////

	/**	 The entity classCanonicalName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String classCanonicalName;
	@JsonIgnore
	public Wrap<String> classCanonicalNameWrap = new Wrap<String>().p(this).c(String.class).var("classCanonicalName").o(classCanonicalName);

	/**	<br/> The entity classCanonicalName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:classCanonicalName">Find the entity classCanonicalName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _classCanonicalName(Wrap<String> c);

	public String getClassCanonicalName() {
		return classCanonicalName;
	}
	public void setClassCanonicalName(String o) {
		this.classCanonicalName = Cluster.staticSetClassCanonicalName(siteRequest_, o);
		this.classCanonicalNameWrap.alreadyInitialized = true;
	}
	public static String staticSetClassCanonicalName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected Cluster classCanonicalNameInit() {
		if(!classCanonicalNameWrap.alreadyInitialized) {
			_classCanonicalName(classCanonicalNameWrap);
			if(classCanonicalName == null)
				setClassCanonicalName(classCanonicalNameWrap.o);
		}
		classCanonicalNameWrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static String staticSolrClassCanonicalName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrClassCanonicalName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqClassCanonicalName(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrClassCanonicalName(siteRequest_, Cluster.staticSolrClassCanonicalName(siteRequest_, Cluster.staticSetClassCanonicalName(siteRequest_, o)));
	}

	public String solrClassCanonicalName() {
		return Cluster.staticSolrClassCanonicalName(siteRequest_, classCanonicalName);
	}

	public String strClassCanonicalName() {
		return classCanonicalName == null ? "" : classCanonicalName;
	}

	public String jsonClassCanonicalName() {
		return classCanonicalName == null ? "" : classCanonicalName;
	}

	public String nomAffichageClassCanonicalName() {
		return null;
	}

	public String htmTooltipClassCanonicalName() {
		return null;
	}

	public String htmClassCanonicalName() {
		return classCanonicalName == null ? "" : StringEscapeUtils.escapeHtml4(strClassCanonicalName());
	}

	/////////////////////
	// classSimpleName //
	/////////////////////

	/**	 The entity classSimpleName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String classSimpleName;
	@JsonIgnore
	public Wrap<String> classSimpleNameWrap = new Wrap<String>().p(this).c(String.class).var("classSimpleName").o(classSimpleName);

	/**	<br/> The entity classSimpleName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:classSimpleName">Find the entity classSimpleName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _classSimpleName(Wrap<String> c);

	public String getClassSimpleName() {
		return classSimpleName;
	}
	public void setClassSimpleName(String o) {
		this.classSimpleName = Cluster.staticSetClassSimpleName(siteRequest_, o);
		this.classSimpleNameWrap.alreadyInitialized = true;
	}
	public static String staticSetClassSimpleName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected Cluster classSimpleNameInit() {
		if(!classSimpleNameWrap.alreadyInitialized) {
			_classSimpleName(classSimpleNameWrap);
			if(classSimpleName == null)
				setClassSimpleName(classSimpleNameWrap.o);
		}
		classSimpleNameWrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static String staticSolrClassSimpleName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrClassSimpleName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqClassSimpleName(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrClassSimpleName(siteRequest_, Cluster.staticSolrClassSimpleName(siteRequest_, Cluster.staticSetClassSimpleName(siteRequest_, o)));
	}

	public String solrClassSimpleName() {
		return Cluster.staticSolrClassSimpleName(siteRequest_, classSimpleName);
	}

	public String strClassSimpleName() {
		return classSimpleName == null ? "" : classSimpleName;
	}

	public String jsonClassSimpleName() {
		return classSimpleName == null ? "" : classSimpleName;
	}

	public String nomAffichageClassSimpleName() {
		return null;
	}

	public String htmTooltipClassSimpleName() {
		return null;
	}

	public String htmClassSimpleName() {
		return classSimpleName == null ? "" : StringEscapeUtils.escapeHtml4(strClassSimpleName());
	}

	/////////////////////////
	// classCanonicalNames //
	/////////////////////////

	/**	 The entity classCanonicalNames
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<String>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected List<String> classCanonicalNames = new ArrayList<String>();
	@JsonIgnore
	public Wrap<List<String>> classCanonicalNamesWrap = new Wrap<List<String>>().p(this).c(List.class).var("classCanonicalNames").o(classCanonicalNames);

	/**	<br/> The entity classCanonicalNames
	 *  It is constructed before being initialized with the constructor by default List<String>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:classCanonicalNames">Find the entity classCanonicalNames in Solr</a>
	 * <br/>
	 * @param classCanonicalNames is the entity already constructed. 
	 **/
	protected abstract void _classCanonicalNames(List<String> l);

	public List<String> getClassCanonicalNames() {
		return classCanonicalNames;
	}

	public void setClassCanonicalNames(List<String> classCanonicalNames) {
		this.classCanonicalNames = classCanonicalNames;
		this.classCanonicalNamesWrap.alreadyInitialized = true;
	}
	public static String staticSetClassCanonicalNames(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	public Cluster addClassCanonicalNames(String...objets) {
		for(String o : objets) {
			addClassCanonicalNames(o);
		}
		return (Cluster)this;
	}
	public Cluster addClassCanonicalNames(String o) {
		if(o != null && !classCanonicalNames.contains(o))
			this.classCanonicalNames.add(o);
		return (Cluster)this;
	}
	public void setClassCanonicalNames(JsonArray objets) {
		classCanonicalNames.clear();
		for(int i = 0; i < objets.size(); i++) {
			String o = objets.getString(i);
			addClassCanonicalNames(o);
		}
	}
	protected Cluster classCanonicalNamesInit() {
		if(!classCanonicalNamesWrap.alreadyInitialized) {
			_classCanonicalNames(classCanonicalNames);
		}
		classCanonicalNamesWrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static String staticSolrClassCanonicalNames(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrClassCanonicalNames(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqClassCanonicalNames(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrClassCanonicalNames(siteRequest_, Cluster.staticSolrClassCanonicalNames(siteRequest_, Cluster.staticSetClassCanonicalNames(siteRequest_, o)));
	}

	public List<String> solrClassCanonicalNames() {
		List<String> l = new ArrayList<String>();
		for(String o : classCanonicalNames) {
			l.add(Cluster.staticSolrClassCanonicalNames(siteRequest_, o));
		}
		return l;
	}

	public String strClassCanonicalNames() {
		return classCanonicalNames == null ? "" : classCanonicalNames.toString();
	}

	public String jsonClassCanonicalNames() {
		return classCanonicalNames == null ? "" : classCanonicalNames.toString();
	}

	public String nomAffichageClassCanonicalNames() {
		return null;
	}

	public String htmTooltipClassCanonicalNames() {
		return null;
	}

	public String htmClassCanonicalNames() {
		return classCanonicalNames == null ? "" : StringEscapeUtils.escapeHtml4(strClassCanonicalNames());
	}

	///////////////
	// sessionId //
	///////////////

	/**	 The entity sessionId
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String sessionId;
	@JsonIgnore
	public Wrap<String> sessionIdWrap = new Wrap<String>().p(this).c(String.class).var("sessionId").o(sessionId);

	/**	<br/> The entity sessionId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:sessionId">Find the entity sessionId in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _sessionId(Wrap<String> c);

	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String o) {
		this.sessionId = Cluster.staticSetSessionId(siteRequest_, o);
		this.sessionIdWrap.alreadyInitialized = true;
	}
	public static String staticSetSessionId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected Cluster sessionIdInit() {
		if(!sessionIdWrap.alreadyInitialized) {
			_sessionId(sessionIdWrap);
			if(sessionId == null)
				setSessionId(sessionIdWrap.o);
		}
		sessionIdWrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static String staticSolrSessionId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrSessionId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSessionId(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrSessionId(siteRequest_, Cluster.staticSolrSessionId(siteRequest_, Cluster.staticSetSessionId(siteRequest_, o)));
	}

	public String solrSessionId() {
		return Cluster.staticSolrSessionId(siteRequest_, sessionId);
	}

	public String strSessionId() {
		return sessionId == null ? "" : sessionId;
	}

	public String jsonSessionId() {
		return sessionId == null ? "" : sessionId;
	}

	public String nomAffichageSessionId() {
		return null;
	}

	public String htmTooltipSessionId() {
		return null;
	}

	public String htmSessionId() {
		return sessionId == null ? "" : StringEscapeUtils.escapeHtml4(strSessionId());
	}

	////////////
	// userId //
	////////////

	/**	 The entity userId
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String userId;
	@JsonIgnore
	public Wrap<String> userIdWrap = new Wrap<String>().p(this).c(String.class).var("userId").o(userId);

	/**	<br/> The entity userId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:userId">Find the entity userId in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _userId(Wrap<String> c);

	public String getUserId() {
		return userId;
	}
	public void setUserId(String o) {
		this.userId = Cluster.staticSetUserId(siteRequest_, o);
		this.userIdWrap.alreadyInitialized = true;
	}
	public static String staticSetUserId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected Cluster userIdInit() {
		if(!userIdWrap.alreadyInitialized) {
			_userId(userIdWrap);
			if(userId == null)
				setUserId(userIdWrap.o);
		}
		userIdWrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static String staticSolrUserId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrUserId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqUserId(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrUserId(siteRequest_, Cluster.staticSolrUserId(siteRequest_, Cluster.staticSetUserId(siteRequest_, o)));
	}

	public String solrUserId() {
		return Cluster.staticSolrUserId(siteRequest_, userId);
	}

	public String strUserId() {
		return userId == null ? "" : userId;
	}

	public String jsonUserId() {
		return userId == null ? "" : userId;
	}

	public String nomAffichageUserId() {
		return null;
	}

	public String htmTooltipUserId() {
		return null;
	}

	public String htmUserId() {
		return userId == null ? "" : StringEscapeUtils.escapeHtml4(strUserId());
	}

	/////////////
	// userKey //
	/////////////

	/**	 The entity userKey
	 *	 is defined as null before being initialized. 
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long userKey;
	@JsonIgnore
	public Wrap<Long> userKeyWrap = new Wrap<Long>().p(this).c(Long.class).var("userKey").o(userKey);

	/**	<br/> The entity userKey
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:userKey">Find the entity userKey in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _userKey(Wrap<Long> c);

	public Long getUserKey() {
		return userKey;
	}

	public void setUserKey(Long userKey) {
		this.userKey = userKey;
		this.userKeyWrap.alreadyInitialized = true;
	}
	public void setUserKey(String o) {
		this.userKey = Cluster.staticSetUserKey(siteRequest_, o);
		this.userKeyWrap.alreadyInitialized = true;
	}
	public static Long staticSetUserKey(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected Cluster userKeyInit() {
		if(!userKeyWrap.alreadyInitialized) {
			_userKey(userKeyWrap);
			if(userKey == null)
				setUserKey(userKeyWrap.o);
		}
		userKeyWrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static Long staticSolrUserKey(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrUserKey(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqUserKey(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrUserKey(siteRequest_, Cluster.staticSolrUserKey(siteRequest_, Cluster.staticSetUserKey(siteRequest_, o)));
	}

	public Long solrUserKey() {
		return Cluster.staticSolrUserKey(siteRequest_, userKey);
	}

	public String strUserKey() {
		return userKey == null ? "" : userKey.toString();
	}

	public String jsonUserKey() {
		return userKey == null ? "" : userKey.toString();
	}

	public String nomAffichageUserKey() {
		return null;
	}

	public String htmTooltipUserKey() {
		return null;
	}

	public String htmUserKey() {
		return userKey == null ? "" : StringEscapeUtils.escapeHtml4(strUserKey());
	}

	///////////
	// saves //
	///////////

	/**	 The entity saves
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<String>(). 
	 */
	@JsonInclude(Include.NON_NULL)
	protected List<String> saves = new ArrayList<String>();
	@JsonIgnore
	public Wrap<List<String>> savesWrap = new Wrap<List<String>>().p(this).c(List.class).var("saves").o(saves);

	/**	<br/> The entity saves
	 *  It is constructed before being initialized with the constructor by default List<String>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:saves">Find the entity saves in Solr</a>
	 * <br/>
	 * @param saves is the entity already constructed. 
	 **/
	protected abstract void _saves(List<String> l);

	public List<String> getSaves() {
		return saves;
	}

	public void setSaves(List<String> saves) {
		this.saves = saves;
		this.savesWrap.alreadyInitialized = true;
	}
	public static String staticSetSaves(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	public Cluster addSaves(String...objets) {
		for(String o : objets) {
			addSaves(o);
		}
		return (Cluster)this;
	}
	public Cluster addSaves(String o) {
		if(o != null && !saves.contains(o))
			this.saves.add(o);
		return (Cluster)this;
	}
	public void setSaves(JsonArray objets) {
		saves.clear();
		for(int i = 0; i < objets.size(); i++) {
			String o = objets.getString(i);
			addSaves(o);
		}
	}
	protected Cluster savesInit() {
		if(!savesWrap.alreadyInitialized) {
			_saves(saves);
		}
		savesWrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static String staticSolrSaves(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrSaves(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSaves(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrSaves(siteRequest_, Cluster.staticSolrSaves(siteRequest_, Cluster.staticSetSaves(siteRequest_, o)));
	}

	public List<String> solrSaves() {
		List<String> l = new ArrayList<String>();
		for(String o : saves) {
			l.add(Cluster.staticSolrSaves(siteRequest_, o));
		}
		return l;
	}

	public String strSaves() {
		return saves == null ? "" : saves.toString();
	}

	public String jsonSaves() {
		return saves == null ? "" : saves.toString();
	}

	public String nomAffichageSaves() {
		return null;
	}

	public String htmTooltipSaves() {
		return null;
	}

	public String htmSaves() {
		return saves == null ? "" : StringEscapeUtils.escapeHtml4(strSaves());
	}

	/////////////////
	// objectTitle //
	/////////////////

	/**	 The entity objectTitle
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String objectTitle;
	@JsonIgnore
	public Wrap<String> objectTitleWrap = new Wrap<String>().p(this).c(String.class).var("objectTitle").o(objectTitle);

	/**	<br/> The entity objectTitle
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:objectTitle">Find the entity objectTitle in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _objectTitle(Wrap<String> c);

	public String getObjectTitle() {
		return objectTitle;
	}
	public void setObjectTitle(String o) {
		this.objectTitle = Cluster.staticSetObjectTitle(siteRequest_, o);
		this.objectTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetObjectTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected Cluster objectTitleInit() {
		if(!objectTitleWrap.alreadyInitialized) {
			_objectTitle(objectTitleWrap);
			if(objectTitle == null)
				setObjectTitle(objectTitleWrap.o);
		}
		objectTitleWrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static String staticSolrObjectTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrObjectTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqObjectTitle(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrObjectTitle(siteRequest_, Cluster.staticSolrObjectTitle(siteRequest_, Cluster.staticSetObjectTitle(siteRequest_, o)));
	}

	public String solrObjectTitle() {
		return Cluster.staticSolrObjectTitle(siteRequest_, objectTitle);
	}

	public String strObjectTitle() {
		return objectTitle == null ? "" : objectTitle;
	}

	public String jsonObjectTitle() {
		return objectTitle == null ? "" : objectTitle;
	}

	public String nomAffichageObjectTitle() {
		return null;
	}

	public String htmTooltipObjectTitle() {
		return null;
	}

	public String htmObjectTitle() {
		return objectTitle == null ? "" : StringEscapeUtils.escapeHtml4(strObjectTitle());
	}

	//////////////
	// objectId //
	//////////////

	/**	 The entity objectId
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String objectId;
	@JsonIgnore
	public Wrap<String> objectIdWrap = new Wrap<String>().p(this).c(String.class).var("objectId").o(objectId);

	/**	<br/> The entity objectId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:objectId">Find the entity objectId in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _objectId(Wrap<String> c);

	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String o) {
		this.objectId = Cluster.staticSetObjectId(siteRequest_, o);
		this.objectIdWrap.alreadyInitialized = true;
	}
	public static String staticSetObjectId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected Cluster objectIdInit() {
		if(!objectIdWrap.alreadyInitialized) {
			_objectId(objectIdWrap);
			if(objectId == null)
				setObjectId(objectIdWrap.o);
		}
		objectIdWrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static String staticSolrObjectId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrObjectId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqObjectId(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrObjectId(siteRequest_, Cluster.staticSolrObjectId(siteRequest_, Cluster.staticSetObjectId(siteRequest_, o)));
	}

	public String solrObjectId() {
		return Cluster.staticSolrObjectId(siteRequest_, objectId);
	}

	public String strObjectId() {
		return objectId == null ? "" : objectId;
	}

	public String jsonObjectId() {
		return objectId == null ? "" : objectId;
	}

	public String nomAffichageObjectId() {
		return null;
	}

	public String htmTooltipObjectId() {
		return null;
	}

	public String htmObjectId() {
		return objectId == null ? "" : StringEscapeUtils.escapeHtml4(strObjectId());
	}

	///////////////////
	// objectNameVar //
	///////////////////

	/**	 The entity objectNameVar
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String objectNameVar;
	@JsonIgnore
	public Wrap<String> objectNameVarWrap = new Wrap<String>().p(this).c(String.class).var("objectNameVar").o(objectNameVar);

	/**	<br/> The entity objectNameVar
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:objectNameVar">Find the entity objectNameVar in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _objectNameVar(Wrap<String> c);

	public String getObjectNameVar() {
		return objectNameVar;
	}
	public void setObjectNameVar(String o) {
		this.objectNameVar = Cluster.staticSetObjectNameVar(siteRequest_, o);
		this.objectNameVarWrap.alreadyInitialized = true;
	}
	public static String staticSetObjectNameVar(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected Cluster objectNameVarInit() {
		if(!objectNameVarWrap.alreadyInitialized) {
			_objectNameVar(objectNameVarWrap);
			if(objectNameVar == null)
				setObjectNameVar(objectNameVarWrap.o);
		}
		objectNameVarWrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static String staticSolrObjectNameVar(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrObjectNameVar(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqObjectNameVar(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrObjectNameVar(siteRequest_, Cluster.staticSolrObjectNameVar(siteRequest_, Cluster.staticSetObjectNameVar(siteRequest_, o)));
	}

	public String solrObjectNameVar() {
		return Cluster.staticSolrObjectNameVar(siteRequest_, objectNameVar);
	}

	public String strObjectNameVar() {
		return objectNameVar == null ? "" : objectNameVar;
	}

	public String jsonObjectNameVar() {
		return objectNameVar == null ? "" : objectNameVar;
	}

	public String nomAffichageObjectNameVar() {
		return null;
	}

	public String htmTooltipObjectNameVar() {
		return null;
	}

	public String htmObjectNameVar() {
		return objectNameVar == null ? "" : StringEscapeUtils.escapeHtml4(strObjectNameVar());
	}

	///////////////////
	// objectSuggest //
	///////////////////

	/**	 The entity objectSuggest
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String objectSuggest;
	@JsonIgnore
	public Wrap<String> objectSuggestWrap = new Wrap<String>().p(this).c(String.class).var("objectSuggest").o(objectSuggest);

	/**	<br/> The entity objectSuggest
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:objectSuggest">Find the entity objectSuggest in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _objectSuggest(Wrap<String> c);

	public String getObjectSuggest() {
		return objectSuggest;
	}
	public void setObjectSuggest(String o) {
		this.objectSuggest = Cluster.staticSetObjectSuggest(siteRequest_, o);
		this.objectSuggestWrap.alreadyInitialized = true;
	}
	public static String staticSetObjectSuggest(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected Cluster objectSuggestInit() {
		if(!objectSuggestWrap.alreadyInitialized) {
			_objectSuggest(objectSuggestWrap);
			if(objectSuggest == null)
				setObjectSuggest(objectSuggestWrap.o);
		}
		objectSuggestWrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static String staticSolrObjectSuggest(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrObjectSuggest(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqObjectSuggest(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrObjectSuggest(siteRequest_, Cluster.staticSolrObjectSuggest(siteRequest_, Cluster.staticSetObjectSuggest(siteRequest_, o)));
	}

	public String solrObjectSuggest() {
		return Cluster.staticSolrObjectSuggest(siteRequest_, objectSuggest);
	}

	public String strObjectSuggest() {
		return objectSuggest == null ? "" : objectSuggest;
	}

	public String jsonObjectSuggest() {
		return objectSuggest == null ? "" : objectSuggest;
	}

	public String nomAffichageObjectSuggest() {
		return null;
	}

	public String htmTooltipObjectSuggest() {
		return null;
	}

	public String htmObjectSuggest() {
		return objectSuggest == null ? "" : StringEscapeUtils.escapeHtml4(strObjectSuggest());
	}

	////////////////
	// objectText //
	////////////////

	/**	 The entity objectText
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String objectText;
	@JsonIgnore
	public Wrap<String> objectTextWrap = new Wrap<String>().p(this).c(String.class).var("objectText").o(objectText);

	/**	<br/> The entity objectText
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:objectText">Find the entity objectText in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _objectText(Wrap<String> c);

	public String getObjectText() {
		return objectText;
	}
	public void setObjectText(String o) {
		this.objectText = Cluster.staticSetObjectText(siteRequest_, o);
		this.objectTextWrap.alreadyInitialized = true;
	}
	public static String staticSetObjectText(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected Cluster objectTextInit() {
		if(!objectTextWrap.alreadyInitialized) {
			_objectText(objectTextWrap);
			if(objectText == null)
				setObjectText(objectTextWrap.o);
		}
		objectTextWrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static String staticSolrObjectText(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrObjectText(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqObjectText(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrObjectText(siteRequest_, Cluster.staticSolrObjectText(siteRequest_, Cluster.staticSetObjectText(siteRequest_, o)));
	}

	public String solrObjectText() {
		return Cluster.staticSolrObjectText(siteRequest_, objectText);
	}

	public String strObjectText() {
		return objectText == null ? "" : objectText;
	}

	public String jsonObjectText() {
		return objectText == null ? "" : objectText;
	}

	public String nomAffichageObjectText() {
		return null;
	}

	public String htmTooltipObjectText() {
		return null;
	}

	public String htmObjectText() {
		return objectText == null ? "" : StringEscapeUtils.escapeHtml4(strObjectText());
	}

	///////////////
	// pageUrlId //
	///////////////

	/**	 The entity pageUrlId
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageUrlId;
	@JsonIgnore
	public Wrap<String> pageUrlIdWrap = new Wrap<String>().p(this).c(String.class).var("pageUrlId").o(pageUrlId);

	/**	<br/> The entity pageUrlId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageUrlId">Find the entity pageUrlId in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageUrlId(Wrap<String> c);

	public String getPageUrlId() {
		return pageUrlId;
	}
	public void setPageUrlId(String o) {
		this.pageUrlId = Cluster.staticSetPageUrlId(siteRequest_, o);
		this.pageUrlIdWrap.alreadyInitialized = true;
	}
	public static String staticSetPageUrlId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected Cluster pageUrlIdInit() {
		if(!pageUrlIdWrap.alreadyInitialized) {
			_pageUrlId(pageUrlIdWrap);
			if(pageUrlId == null)
				setPageUrlId(pageUrlIdWrap.o);
		}
		pageUrlIdWrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static String staticSolrPageUrlId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageUrlId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageUrlId(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrPageUrlId(siteRequest_, Cluster.staticSolrPageUrlId(siteRequest_, Cluster.staticSetPageUrlId(siteRequest_, o)));
	}

	public String solrPageUrlId() {
		return Cluster.staticSolrPageUrlId(siteRequest_, pageUrlId);
	}

	public String strPageUrlId() {
		return pageUrlId == null ? "" : pageUrlId;
	}

	public String jsonPageUrlId() {
		return pageUrlId == null ? "" : pageUrlId;
	}

	public String nomAffichagePageUrlId() {
		return null;
	}

	public String htmTooltipPageUrlId() {
		return null;
	}

	public String htmPageUrlId() {
		return pageUrlId == null ? "" : StringEscapeUtils.escapeHtml4(strPageUrlId());
	}

	///////////////
	// pageUrlPk //
	///////////////

	/**	 The entity pageUrlPk
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageUrlPk;
	@JsonIgnore
	public Wrap<String> pageUrlPkWrap = new Wrap<String>().p(this).c(String.class).var("pageUrlPk").o(pageUrlPk);

	/**	<br/> The entity pageUrlPk
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageUrlPk">Find the entity pageUrlPk in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageUrlPk(Wrap<String> c);

	public String getPageUrlPk() {
		return pageUrlPk;
	}
	public void setPageUrlPk(String o) {
		this.pageUrlPk = Cluster.staticSetPageUrlPk(siteRequest_, o);
		this.pageUrlPkWrap.alreadyInitialized = true;
	}
	public static String staticSetPageUrlPk(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected Cluster pageUrlPkInit() {
		if(!pageUrlPkWrap.alreadyInitialized) {
			_pageUrlPk(pageUrlPkWrap);
			if(pageUrlPk == null)
				setPageUrlPk(pageUrlPkWrap.o);
		}
		pageUrlPkWrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static String staticSolrPageUrlPk(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageUrlPk(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageUrlPk(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrPageUrlPk(siteRequest_, Cluster.staticSolrPageUrlPk(siteRequest_, Cluster.staticSetPageUrlPk(siteRequest_, o)));
	}

	public String solrPageUrlPk() {
		return Cluster.staticSolrPageUrlPk(siteRequest_, pageUrlPk);
	}

	public String strPageUrlPk() {
		return pageUrlPk == null ? "" : pageUrlPk;
	}

	public String jsonPageUrlPk() {
		return pageUrlPk == null ? "" : pageUrlPk;
	}

	public String nomAffichagePageUrlPk() {
		return null;
	}

	public String htmTooltipPageUrlPk() {
		return null;
	}

	public String htmPageUrlPk() {
		return pageUrlPk == null ? "" : StringEscapeUtils.escapeHtml4(strPageUrlPk());
	}

	////////////////
	// pageUrlApi //
	////////////////

	/**	 The entity pageUrlApi
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageUrlApi;
	@JsonIgnore
	public Wrap<String> pageUrlApiWrap = new Wrap<String>().p(this).c(String.class).var("pageUrlApi").o(pageUrlApi);

	/**	<br/> The entity pageUrlApi
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageUrlApi">Find the entity pageUrlApi in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageUrlApi(Wrap<String> c);

	public String getPageUrlApi() {
		return pageUrlApi;
	}
	public void setPageUrlApi(String o) {
		this.pageUrlApi = Cluster.staticSetPageUrlApi(siteRequest_, o);
		this.pageUrlApiWrap.alreadyInitialized = true;
	}
	public static String staticSetPageUrlApi(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected Cluster pageUrlApiInit() {
		if(!pageUrlApiWrap.alreadyInitialized) {
			_pageUrlApi(pageUrlApiWrap);
			if(pageUrlApi == null)
				setPageUrlApi(pageUrlApiWrap.o);
		}
		pageUrlApiWrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static String staticSolrPageUrlApi(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageUrlApi(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageUrlApi(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrPageUrlApi(siteRequest_, Cluster.staticSolrPageUrlApi(siteRequest_, Cluster.staticSetPageUrlApi(siteRequest_, o)));
	}

	public String solrPageUrlApi() {
		return Cluster.staticSolrPageUrlApi(siteRequest_, pageUrlApi);
	}

	public String strPageUrlApi() {
		return pageUrlApi == null ? "" : pageUrlApi;
	}

	public String jsonPageUrlApi() {
		return pageUrlApi == null ? "" : pageUrlApi;
	}

	public String nomAffichagePageUrlApi() {
		return null;
	}

	public String htmTooltipPageUrlApi() {
		return null;
	}

	public String htmPageUrlApi() {
		return pageUrlApi == null ? "" : StringEscapeUtils.escapeHtml4(strPageUrlApi());
	}

	////////////
	// pageH1 //
	////////////

	/**	 The entity pageH1
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String pageH1;
	@JsonIgnore
	public Wrap<String> pageH1Wrap = new Wrap<String>().p(this).c(String.class).var("pageH1").o(pageH1);

	/**	<br/> The entity pageH1
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:pageH1">Find the entity pageH1 in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _pageH1(Wrap<String> c);

	public String getPageH1() {
		return pageH1;
	}
	public void setPageH1(String o) {
		this.pageH1 = Cluster.staticSetPageH1(siteRequest_, o);
		this.pageH1Wrap.alreadyInitialized = true;
	}
	public static String staticSetPageH1(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected Cluster pageH1Init() {
		if(!pageH1Wrap.alreadyInitialized) {
			_pageH1(pageH1Wrap);
			if(pageH1 == null)
				setPageH1(pageH1Wrap.o);
		}
		pageH1Wrap.alreadyInitialized(true);
		return (Cluster)this;
	}

	public static String staticSolrPageH1(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrPageH1(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageH1(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrPageH1(siteRequest_, Cluster.staticSolrPageH1(siteRequest_, Cluster.staticSetPageH1(siteRequest_, o)));
	}

	public String solrPageH1() {
		return Cluster.staticSolrPageH1(siteRequest_, pageH1);
	}

	public String strPageH1() {
		return pageH1 == null ? "" : pageH1;
	}

	public String jsonPageH1() {
		return pageH1 == null ? "" : pageH1;
	}

	public String nomAffichagePageH1() {
		return null;
	}

	public String htmTooltipPageH1() {
		return null;
	}

	public String htmPageH1() {
		return pageH1 == null ? "" : StringEscapeUtils.escapeHtml4(strPageH1());
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedCluster = false;

	public Cluster initDeepCluster(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedCluster) {
			alreadyInitializedCluster = true;
			initDeepCluster();
		}
		return (Cluster)this;
	}

	public void initDeepCluster() {
		initCluster();
	}

	public void initCluster() {
		siteRequest_Init();
		pkInit();
		inheritPkInit();
		idInit();
		createdInit();
		modifiedInit();
		modifiedIsoOffsetDateTimeInit();
		archivedInit();
		deletedInit();
		classCanonicalNameInit();
		classSimpleNameInit();
		classCanonicalNamesInit();
		sessionIdInit();
		userIdInit();
		userKeyInit();
		savesInit();
		objectTitleInit();
		objectIdInit();
		objectNameVarInit();
		objectSuggestInit();
		objectTextInit();
		pageUrlIdInit();
		pageUrlPkInit();
		pageUrlApiInit();
		pageH1Init();
	}

	public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepCluster(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestCluster(SiteRequestEnUS siteRequest_) {
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestCluster(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainCluster(v);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.obtainForClass(v);
			}
		}
		return o;
	}
	public Object obtainCluster(String var) {
		Cluster oCluster = (Cluster)this;
		switch(var) {
			case "siteRequest_":
				return oCluster.siteRequest_;
			case "pk":
				return oCluster.pk;
			case "inheritPk":
				return oCluster.inheritPk;
			case "id":
				return oCluster.id;
			case "created":
				return oCluster.created;
			case "modified":
				return oCluster.modified;
			case "modifiedIsoOffsetDateTime":
				return oCluster.modifiedIsoOffsetDateTime;
			case "archived":
				return oCluster.archived;
			case "deleted":
				return oCluster.deleted;
			case "classCanonicalName":
				return oCluster.classCanonicalName;
			case "classSimpleName":
				return oCluster.classSimpleName;
			case "classCanonicalNames":
				return oCluster.classCanonicalNames;
			case "sessionId":
				return oCluster.sessionId;
			case "userId":
				return oCluster.userId;
			case "userKey":
				return oCluster.userKey;
			case "saves":
				return oCluster.saves;
			case "objectTitle":
				return oCluster.objectTitle;
			case "objectId":
				return oCluster.objectId;
			case "objectNameVar":
				return oCluster.objectNameVar;
			case "objectSuggest":
				return oCluster.objectSuggest;
			case "objectText":
				return oCluster.objectText;
			case "pageUrlId":
				return oCluster.pageUrlId;
			case "pageUrlPk":
				return oCluster.pageUrlPk;
			case "pageUrlApi":
				return oCluster.pageUrlApi;
			case "pageH1":
				return oCluster.pageH1;
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
				o = attributeCluster(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeCluster(String var, Object val) {
		Cluster oCluster = (Cluster)this;
		switch(var) {
			default:
				return null;
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetCluster(entityVar,  siteRequest_, o);
	}
	public static Object staticSetCluster(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "pk":
			return Cluster.staticSetPk(siteRequest_, o);
		case "inheritPk":
			return Cluster.staticSetInheritPk(siteRequest_, o);
		case "id":
			return Cluster.staticSetId(siteRequest_, o);
		case "created":
			return Cluster.staticSetCreated(siteRequest_, o);
		case "modified":
			return Cluster.staticSetModified(siteRequest_, o);
		case "modifiedIsoOffsetDateTime":
			return Cluster.staticSetModifiedIsoOffsetDateTime(siteRequest_, o);
		case "archived":
			return Cluster.staticSetArchived(siteRequest_, o);
		case "deleted":
			return Cluster.staticSetDeleted(siteRequest_, o);
		case "classCanonicalName":
			return Cluster.staticSetClassCanonicalName(siteRequest_, o);
		case "classSimpleName":
			return Cluster.staticSetClassSimpleName(siteRequest_, o);
		case "classCanonicalNames":
			return Cluster.staticSetClassCanonicalNames(siteRequest_, o);
		case "sessionId":
			return Cluster.staticSetSessionId(siteRequest_, o);
		case "userId":
			return Cluster.staticSetUserId(siteRequest_, o);
		case "userKey":
			return Cluster.staticSetUserKey(siteRequest_, o);
		case "saves":
			return Cluster.staticSetSaves(siteRequest_, o);
		case "objectTitle":
			return Cluster.staticSetObjectTitle(siteRequest_, o);
		case "objectId":
			return Cluster.staticSetObjectId(siteRequest_, o);
		case "objectNameVar":
			return Cluster.staticSetObjectNameVar(siteRequest_, o);
		case "objectSuggest":
			return Cluster.staticSetObjectSuggest(siteRequest_, o);
		case "objectText":
			return Cluster.staticSetObjectText(siteRequest_, o);
		case "pageUrlId":
			return Cluster.staticSetPageUrlId(siteRequest_, o);
		case "pageUrlPk":
			return Cluster.staticSetPageUrlPk(siteRequest_, o);
		case "pageUrlApi":
			return Cluster.staticSetPageUrlApi(siteRequest_, o);
		case "pageH1":
			return Cluster.staticSetPageH1(siteRequest_, o);
			default:
				return null;
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrCluster(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrCluster(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "pk":
			return Cluster.staticSolrPk(siteRequest_, (Long)o);
		case "inheritPk":
			return Cluster.staticSolrInheritPk(siteRequest_, (Long)o);
		case "id":
			return Cluster.staticSolrId(siteRequest_, (String)o);
		case "created":
			return Cluster.staticSolrCreated(siteRequest_, (ZonedDateTime)o);
		case "modified":
			return Cluster.staticSolrModified(siteRequest_, (ZonedDateTime)o);
		case "modifiedIsoOffsetDateTime":
			return Cluster.staticSolrModifiedIsoOffsetDateTime(siteRequest_, (String)o);
		case "archived":
			return Cluster.staticSolrArchived(siteRequest_, (Boolean)o);
		case "deleted":
			return Cluster.staticSolrDeleted(siteRequest_, (Boolean)o);
		case "classCanonicalName":
			return Cluster.staticSolrClassCanonicalName(siteRequest_, (String)o);
		case "classSimpleName":
			return Cluster.staticSolrClassSimpleName(siteRequest_, (String)o);
		case "classCanonicalNames":
			return Cluster.staticSolrClassCanonicalNames(siteRequest_, (String)o);
		case "sessionId":
			return Cluster.staticSolrSessionId(siteRequest_, (String)o);
		case "userId":
			return Cluster.staticSolrUserId(siteRequest_, (String)o);
		case "userKey":
			return Cluster.staticSolrUserKey(siteRequest_, (Long)o);
		case "saves":
			return Cluster.staticSolrSaves(siteRequest_, (String)o);
		case "objectTitle":
			return Cluster.staticSolrObjectTitle(siteRequest_, (String)o);
		case "objectId":
			return Cluster.staticSolrObjectId(siteRequest_, (String)o);
		case "objectNameVar":
			return Cluster.staticSolrObjectNameVar(siteRequest_, (String)o);
		case "objectSuggest":
			return Cluster.staticSolrObjectSuggest(siteRequest_, (String)o);
		case "objectText":
			return Cluster.staticSolrObjectText(siteRequest_, (String)o);
		case "pageUrlId":
			return Cluster.staticSolrPageUrlId(siteRequest_, (String)o);
		case "pageUrlPk":
			return Cluster.staticSolrPageUrlPk(siteRequest_, (String)o);
		case "pageUrlApi":
			return Cluster.staticSolrPageUrlApi(siteRequest_, (String)o);
		case "pageH1":
			return Cluster.staticSolrPageH1(siteRequest_, (String)o);
			default:
				return null;
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrCluster(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrCluster(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "pk":
			return Cluster.staticSolrStrPk(siteRequest_, (Long)o);
		case "inheritPk":
			return Cluster.staticSolrStrInheritPk(siteRequest_, (Long)o);
		case "id":
			return Cluster.staticSolrStrId(siteRequest_, (String)o);
		case "created":
			return Cluster.staticSolrStrCreated(siteRequest_, (Date)o);
		case "modified":
			return Cluster.staticSolrStrModified(siteRequest_, (Date)o);
		case "modifiedIsoOffsetDateTime":
			return Cluster.staticSolrStrModifiedIsoOffsetDateTime(siteRequest_, (String)o);
		case "archived":
			return Cluster.staticSolrStrArchived(siteRequest_, (Boolean)o);
		case "deleted":
			return Cluster.staticSolrStrDeleted(siteRequest_, (Boolean)o);
		case "classCanonicalName":
			return Cluster.staticSolrStrClassCanonicalName(siteRequest_, (String)o);
		case "classSimpleName":
			return Cluster.staticSolrStrClassSimpleName(siteRequest_, (String)o);
		case "classCanonicalNames":
			return Cluster.staticSolrStrClassCanonicalNames(siteRequest_, (String)o);
		case "sessionId":
			return Cluster.staticSolrStrSessionId(siteRequest_, (String)o);
		case "userId":
			return Cluster.staticSolrStrUserId(siteRequest_, (String)o);
		case "userKey":
			return Cluster.staticSolrStrUserKey(siteRequest_, (Long)o);
		case "saves":
			return Cluster.staticSolrStrSaves(siteRequest_, (String)o);
		case "objectTitle":
			return Cluster.staticSolrStrObjectTitle(siteRequest_, (String)o);
		case "objectId":
			return Cluster.staticSolrStrObjectId(siteRequest_, (String)o);
		case "objectNameVar":
			return Cluster.staticSolrStrObjectNameVar(siteRequest_, (String)o);
		case "objectSuggest":
			return Cluster.staticSolrStrObjectSuggest(siteRequest_, (String)o);
		case "objectText":
			return Cluster.staticSolrStrObjectText(siteRequest_, (String)o);
		case "pageUrlId":
			return Cluster.staticSolrStrPageUrlId(siteRequest_, (String)o);
		case "pageUrlPk":
			return Cluster.staticSolrStrPageUrlPk(siteRequest_, (String)o);
		case "pageUrlApi":
			return Cluster.staticSolrStrPageUrlApi(siteRequest_, (String)o);
		case "pageH1":
			return Cluster.staticSolrStrPageH1(siteRequest_, (String)o);
			default:
				return null;
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqCluster(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqCluster(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "pk":
			return Cluster.staticSolrFqPk(siteRequest_, o);
		case "inheritPk":
			return Cluster.staticSolrFqInheritPk(siteRequest_, o);
		case "id":
			return Cluster.staticSolrFqId(siteRequest_, o);
		case "created":
			return Cluster.staticSolrFqCreated(siteRequest_, o);
		case "modified":
			return Cluster.staticSolrFqModified(siteRequest_, o);
		case "modifiedIsoOffsetDateTime":
			return Cluster.staticSolrFqModifiedIsoOffsetDateTime(siteRequest_, o);
		case "archived":
			return Cluster.staticSolrFqArchived(siteRequest_, o);
		case "deleted":
			return Cluster.staticSolrFqDeleted(siteRequest_, o);
		case "classCanonicalName":
			return Cluster.staticSolrFqClassCanonicalName(siteRequest_, o);
		case "classSimpleName":
			return Cluster.staticSolrFqClassSimpleName(siteRequest_, o);
		case "classCanonicalNames":
			return Cluster.staticSolrFqClassCanonicalNames(siteRequest_, o);
		case "sessionId":
			return Cluster.staticSolrFqSessionId(siteRequest_, o);
		case "userId":
			return Cluster.staticSolrFqUserId(siteRequest_, o);
		case "userKey":
			return Cluster.staticSolrFqUserKey(siteRequest_, o);
		case "saves":
			return Cluster.staticSolrFqSaves(siteRequest_, o);
		case "objectTitle":
			return Cluster.staticSolrFqObjectTitle(siteRequest_, o);
		case "objectId":
			return Cluster.staticSolrFqObjectId(siteRequest_, o);
		case "objectNameVar":
			return Cluster.staticSolrFqObjectNameVar(siteRequest_, o);
		case "objectSuggest":
			return Cluster.staticSolrFqObjectSuggest(siteRequest_, o);
		case "objectText":
			return Cluster.staticSolrFqObjectText(siteRequest_, o);
		case "pageUrlId":
			return Cluster.staticSolrFqPageUrlId(siteRequest_, o);
		case "pageUrlPk":
			return Cluster.staticSolrFqPageUrlPk(siteRequest_, o);
		case "pageUrlApi":
			return Cluster.staticSolrFqPageUrlApi(siteRequest_, o);
		case "pageH1":
			return Cluster.staticSolrFqPageH1(siteRequest_, o);
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
					o = defineCluster(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineCluster(String var, String val) {
		switch(var) {
			default:
				return null;
		}
	}

	/////////////
	// populate //
	/////////////

	public void populateForClass(SolrDocument solrDocument) {
		populateCluster(solrDocument);
	}
	public void populateCluster(SolrDocument solrDocument) {
		Cluster oCluster = (Cluster)this;
		saves = (List<String>)solrDocument.get("saves_stored_strings");
		if(saves != null) {
		}
	}

	/////////////
	// index //
	/////////////

	public static void index() {
		try {
			SiteRequestEnUS siteRequest = new SiteRequestEnUS();
			siteRequest.initDeepSiteRequestEnUS();
			SiteContextEnUS siteContext = new SiteContextEnUS();
			siteContext.getSiteConfig().setConfigPath("/usr/local/src/opendatapolicing/config/opendatapolicing.config");
			siteContext.initDeepSiteContextEnUS();
			siteRequest.setSiteContext_(siteContext);
			siteRequest.setSiteConfig_(siteContext.getSiteConfig());
			SolrQuery solrQuery = new SolrQuery();
			solrQuery.setQuery("*:*");
			solrQuery.setRows(1);
			solrQuery.addFilterQuery("id:" + ClientUtils.escapeQueryChars("com.opendatapolicing.enus.cluster.Cluster"));
			QueryResponse queryResponse = siteRequest.getSiteContext_().getSolrClient().query(solrQuery);
			if(queryResponse.getResults().size() > 0)
				siteRequest.setSolrDocument(queryResponse.getResults().get(0));
			Cluster o = new Cluster();
			o.siteRequestCluster(siteRequest);
			o.initDeepCluster(siteRequest);
			o.indexCluster();
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}


	public void indexForClass() {
		indexCluster();
	}

	public void indexForClass(SolrInputDocument document) {
		indexCluster(document);
	}

	public void indexCluster(SolrClient clientSolr) {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexCluster(document);
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexCluster() {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexCluster(document);
			SolrClient clientSolr = siteRequest_.getSiteContext_().getSolrClient();
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexCluster(SolrInputDocument document) {
	}

	public static String varIndexedCluster(String entityVar) {
		switch(entityVar) {
			default:
				return null;
		}
	}

	public static String varSearchCluster(String entityVar) {
		switch(entityVar) {
			default:
				return null;
		}
	}

	public static String varSuggestedCluster(String entityVar) {
		switch(entityVar) {
			default:
				return null;
		}
	}

	/////////////
	// store //
	/////////////

	public void storeForClass(SolrDocument solrDocument) {
		storeCluster(solrDocument);
	}
	public void storeCluster(SolrDocument solrDocument) {
		Cluster oCluster = (Cluster)this;
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestCluster() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof Cluster) {
			Cluster original = (Cluster)o;
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
		if(!(o instanceof Cluster))
			return false;
		Cluster that = (Cluster)o;
		return true;
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Cluster { ");
		sb.append(" }");
		return sb.toString();
	}
}
