package com.opendatapolicing.enus.cluster;

import java.util.Arrays;
import java.util.Date;
import java.time.ZonedDateTime;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import java.lang.Long;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.util.Locale;
import java.util.Map;
import java.time.ZoneOffset;
import java.math.RoundingMode;
import com.opendatapolicing.enus.wrap.Wrap;
import java.math.MathContext;
import com.opendatapolicing.enus.writer.AllWriter;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import io.vertx.core.Future;
import com.opendatapolicing.enus.request.api.ApiRequest;
import java.time.ZoneId;
import java.util.Objects;
import java.util.List;
import java.time.OffsetDateTime;
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
	protected static final Logger LOG = LoggerFactory.getLogger(Cluster.class);

	public static final List<String> ROLES = Arrays.asList("SiteService");
	public static final List<String> ROLE_READS = Arrays.asList("User");

	public static final String Cluster_AName = "a cluster";
	public static final String Cluster_This = "this ";
	public static final String Cluster_ThisName = "this cluster";
	public static final String Cluster_A = "a ";
	public static final String Cluster_TheName = "the cluster";
	public static final String Cluster_NameSingular = "cluster";
	public static final String Cluster_NamePlural = "clusters";
	public static final String Cluster_NameActual = "current cluster";
	public static final String Cluster_AllName = "all the clusters";
	public static final String Cluster_SearchAllNameBy = "search clusters by ";
	public static final String Cluster_Title = "clusters";
	public static final String Cluster_ThePluralName = "the clusters";
	public static final String Cluster_NoNameFound = "no cluster found";
	public static final String Cluster_NameVar = "cluster";
	public static final String Cluster_OfName = "of cluster";
	public static final String Cluster_ANameAdjective = "a cluster";
	public static final String Cluster_NameAdjectiveSingular = "cluster";
	public static final String Cluster_NameAdjectivePlural = "clusters";
	public static final String Cluster_Color = "gray";
	public static final String Cluster_IconGroup = "regular";
	public static final String Cluster_IconName = "fort-awesome";

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

	public Long sqlPk() {
		return pk;
	}

	public String jsonPk() {
		return pk == null ? "" : pk.toString();
	}

	public String htmTooltipPk() {
		return null;
	}

	public String htmPk() {
		return pk == null ? "" : StringEscapeUtils.escapeHtml4(strPk());
	}

	public void inputPk(String classApiMethodMethod) {
		Cluster s = (Cluster)this;
		s.e("span").a("class", "varCluster", pk, "Pk ").f().sx(htmPk()).g("span");
	}

	public void htmPk(String classApiMethodMethod) {
		Cluster s = (Cluster)this;
		{ s.e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			if("Page".equals(classApiMethodMethod)) {
				{ s.e("div").a("class", "w3-padding ").f();
					{ s.e("div").a("class", "w3-card ").f();
						{ s.e("div").a("class", "w3-cell-row w3-gray ").f();
							s.e("label").a("class", "").f().sx("primary key").g("label");
						} s.g("div");
						{ s.e("div").a("class", "w3-cell-row  ").f();
							{ s.e("div").a("class", "w3-cell ").f();
								{ s.e("div").a("class", "w3-rest ").f();
									s.e("span").a("class", "varCluster", pk, "Pk ").f().sx(strPk()).g("span");
								} s.g("div");
							} s.g("div");
						} s.g("div");
					} s.g("div");
				} s.g("div");
			}
		} s.g("div");
	}

	///////////////
	// inheritPk //
	///////////////

	/**	 The entity inheritPk
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String inheritPk;
	@JsonIgnore
	public Wrap<String> inheritPkWrap = new Wrap<String>().p(this).c(String.class).var("inheritPk").o(inheritPk);

	/**	<br/> The entity inheritPk
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.cluster.Cluster&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:inheritPk">Find the entity inheritPk in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _inheritPk(Wrap<String> c);

	public String getInheritPk() {
		return inheritPk;
	}
	public void setInheritPk(String o) {
		this.inheritPk = Cluster.staticSetInheritPk(siteRequest_, o);
		this.inheritPkWrap.alreadyInitialized = true;
	}
	public static String staticSetInheritPk(SiteRequestEnUS siteRequest_, String o) {
		return o;
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

	public static String staticSolrInheritPk(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrInheritPk(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqInheritPk(SiteRequestEnUS siteRequest_, String o) {
		return Cluster.staticSolrStrInheritPk(siteRequest_, Cluster.staticSolrInheritPk(siteRequest_, Cluster.staticSetInheritPk(siteRequest_, o)));
	}

	public String solrInheritPk() {
		return Cluster.staticSolrInheritPk(siteRequest_, inheritPk);
	}

	public String strInheritPk() {
		return inheritPk == null ? "" : inheritPk;
	}

	public String sqlInheritPk() {
		return inheritPk;
	}

	public String jsonInheritPk() {
		return inheritPk == null ? "" : inheritPk;
	}

	public String htmTooltipInheritPk() {
		return null;
	}

	public String htmInheritPk() {
		return inheritPk == null ? "" : StringEscapeUtils.escapeHtml4(strInheritPk());
	}

	public void inputInheritPk(String classApiMethodMethod) {
		Cluster s = (Cluster)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			s.e("input")
				.a("type", "text")
				.a("id", classApiMethodMethod, "_inheritPk");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					s.a("class", "setInheritPk classCluster inputCluster", pk, "InheritPk w3-input w3-border ");
					s.a("name", "setInheritPk");
				} else {
					s.a("class", "valueInheritPk w3-input w3-border classCluster inputCluster", pk, "InheritPk w3-input w3-border ");
					s.a("name", "inheritPk");
				}
				if("Page".equals(classApiMethodMethod)) {
					s.a("onclick", "removeGlow($(this)); ");
					s.a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setInheritPk', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_inheritPk')); }, function() { addError($('#", classApiMethodMethod, "_inheritPk')); }); ");
				}
				s.a("value", strInheritPk())
			.fg();

		} else {
				s.e("span").a("class", "varCluster", pk, "InheritPk ").f().sx(htmInheritPk()).g("span");
		}
	}

	public void htmInheritPk(String classApiMethodMethod) {
		Cluster s = (Cluster)this;
		{ s.e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ s.e("div").a("class", "w3-padding ").f();
				{ s.e("div").a("id", "suggest", classApiMethodMethod, "ClusterInheritPk").f();
					{ s.e("div").a("class", "w3-card ").f();
						{ s.e("div").a("class", "w3-cell-row w3-padding ").f();
							{ s.e("div").a("class", "w3-cell ").f();

								inputInheritPk(classApiMethodMethod);
							} s.g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ s.e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ s.e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3-gray ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_inheritPk')); $('#", classApiMethodMethod, "_inheritPk').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#ClusterForm :input[name=pk]').val() }], 'setInheritPk', null, function() { addGlow($('#", classApiMethodMethod, "_inheritPk')); }, function() { addError($('#", classApiMethodMethod, "_inheritPk')); }); ")
											.f();
											s.e("i").a("class", "far fa-eraser ").f().g("i");
										} s.g("button");
									} s.g("div");
								}
							}
						} s.g("div");
					} s.g("div");
				} s.g("div");
			} s.g("div");
		} s.g("div");
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

	public String sqlId() {
		return id;
	}

	public String jsonId() {
		return id == null ? "" : id;
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
		return o == null ? null : ZonedDateTime.parse(o, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest_.getConfig().getString(ConfigKeys.SITE_ZONE)))).truncatedTo(ChronoUnit.MILLIS);
	}
	public void setCreated(Date o) {
		this.created = o == null ? null : ZonedDateTime.ofInstant(o.toInstant(), ZoneId.of(siteRequest_.getConfig().getString(ConfigKeys.SITE_ZONE))).truncatedTo(ChronoUnit.MILLIS);
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

	public OffsetDateTime sqlCreated() {
		return created == null ? null : created.toOffsetDateTime();
	}

	public String jsonCreated() {
		return created == null ? "" : created.format(DateTimeFormatter.ISO_DATE_TIME);
	}

	public String htmTooltipCreated() {
		return null;
	}

	public String htmCreated() {
		return created == null ? "" : StringEscapeUtils.escapeHtml4(strCreated());
	}

	public void inputCreated(String classApiMethodMethod) {
		Cluster s = (Cluster)this;
		s.e("span").a("class", "varCluster", pk, "Created ").f().sx(htmCreated()).g("span");
	}

	public void htmCreated(String classApiMethodMethod) {
		Cluster s = (Cluster)this;
		{ s.e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ s.e("div").a("class", "w3-padding ").f();
				{ s.e("div").a("id", "suggest", classApiMethodMethod, "ClusterCreated").f();
					{ s.e("div").a("class", "w3-card ").f();
						{ s.e("div").a("class", "w3-cell-row w3-gray ").f();
							s.e("label").a("for", classApiMethodMethod, "_created").a("class", "").f().sx("created").g("label");
						} s.g("div");
						{ s.e("div").a("class", "w3-cell-row w3-padding ").f();
							{ s.e("div").a("class", "w3-cell ").f();
								inputCreated(classApiMethodMethod);
							} s.g("div");
						} s.g("div");
					} s.g("div");
				} s.g("div");
			} s.g("div");
		} s.g("div");
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
		return o == null ? null : ZonedDateTime.parse(o, DateTimeFormatter.ISO_DATE_TIME.withZone(ZoneId.of(siteRequest_.getConfig().getString(ConfigKeys.SITE_ZONE)))).truncatedTo(ChronoUnit.MILLIS);
	}
	public void setModified(Date o) {
		this.modified = o == null ? null : ZonedDateTime.ofInstant(o.toInstant(), ZoneId.of(siteRequest_.getConfig().getString(ConfigKeys.SITE_ZONE))).truncatedTo(ChronoUnit.MILLIS);
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

	public OffsetDateTime sqlModified() {
		return modified == null ? null : modified.toOffsetDateTime();
	}

	public String jsonModified() {
		return modified == null ? "" : modified.format(DateTimeFormatter.ISO_DATE_TIME);
	}

	public String htmTooltipModified() {
		return null;
	}

	public String htmModified() {
		return modified == null ? "" : StringEscapeUtils.escapeHtml4(strModified());
	}

	public void inputModified(String classApiMethodMethod) {
		Cluster s = (Cluster)this;
		s.e("span").a("class", "varCluster", pk, "Modified ").f().sx(htmModified()).g("span");
	}

	public void htmModified(String classApiMethodMethod) {
		Cluster s = (Cluster)this;
		{ s.e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			if("Page".equals(classApiMethodMethod)) {
				{ s.e("div").a("class", "w3-padding ").f();
					{ s.e("div").a("class", "w3-card ").f();
						{ s.e("div").a("class", "w3-cell-row w3-gray ").f();
							s.e("label").a("class", "").f().sx("modified").g("label");
						} s.g("div");
						{ s.e("div").a("class", "w3-cell-row  ").f();
							{ s.e("div").a("class", "w3-cell ").f();
								{ s.e("div").a("class", "w3-rest ").f();
									s.e("span").a("class", "varCluster", pk, "Modified ").f().sx(strModified()).g("span");
								} s.g("div");
							} s.g("div");
						} s.g("div");
					} s.g("div");
				} s.g("div");
			}
		} s.g("div");
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

	public String sqlClassCanonicalName() {
		return classCanonicalName;
	}

	public String jsonClassCanonicalName() {
		return classCanonicalName == null ? "" : classCanonicalName;
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

	public String sqlClassSimpleName() {
		return classSimpleName;
	}

	public String jsonClassSimpleName() {
		return classSimpleName == null ? "" : classSimpleName;
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
		return o;
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

	public List<String> sqlClassCanonicalNames() {
		return classCanonicalNames;
	}

	public String jsonClassCanonicalNames() {
		return classCanonicalNames == null ? "" : classCanonicalNames.toString();
	}

	public String htmTooltipClassCanonicalNames() {
		return null;
	}

	public String htmClassCanonicalNames() {
		return classCanonicalNames == null ? "" : StringEscapeUtils.escapeHtml4(strClassCanonicalNames());
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
		return o;
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

	public List<String> sqlSaves() {
		return saves;
	}

	public String jsonSaves() {
		return saves == null ? "" : saves.toString();
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

	public String sqlObjectTitle() {
		return objectTitle;
	}

	public String jsonObjectTitle() {
		return objectTitle == null ? "" : objectTitle;
	}

	public String htmTooltipObjectTitle() {
		return null;
	}

	public String htmObjectTitle() {
		return objectTitle == null ? "" : StringEscapeUtils.escapeHtml4(strObjectTitle());
	}

	public void inputObjectTitle(String classApiMethodMethod) {
		Cluster s = (Cluster)this;
	}

	public void htmObjectTitle(String classApiMethodMethod) {
		Cluster s = (Cluster)this;
		{ s.e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			if("Page".equals(classApiMethodMethod)) {
				{ s.e("div").a("class", "w3-padding ").f();
					{ s.e("div").a("class", "w3-card ").f();
						{ s.e("div").a("class", "w3-cell-row  ").f();
							{ s.e("div").a("class", "w3-cell ").f();
								{ s.e("div").a("class", "w3-rest ").f();
									s.e("span").a("class", "varCluster", pk, "ObjectTitle ").f().sx(strObjectTitle()).g("span");
								} s.g("div");
							} s.g("div");
						} s.g("div");
					} s.g("div");
				} s.g("div");
			}
		} s.g("div");
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

	public String sqlObjectId() {
		return objectId;
	}

	public String jsonObjectId() {
		return objectId == null ? "" : objectId;
	}

	public String htmTooltipObjectId() {
		return null;
	}

	public String htmObjectId() {
		return objectId == null ? "" : StringEscapeUtils.escapeHtml4(strObjectId());
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedCluster = false;

	public Future<Void> promiseDeepCluster(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedCluster) {
			alreadyInitializedCluster = true;
			return promiseDeepCluster();
		} else {
			return Future.succeededFuture();
		}
	}

	public Future<Void> promiseDeepCluster() {
		Promise<Void> promise = Promise.promise();
		Promise<Void> promise2 = Promise.promise();
		promiseCluster(promise2);
		promise2.future().onSuccess(a -> {
			promise.complete();
		}).onFailure(ex -> {
			promise.fail(ex);
		});
		return promise.future();
	}

	public Future<Void> promiseCluster(Promise<Void> promise) {
		Future.future(a -> {}).compose(a -> {
			Promise<Void> promise2 = Promise.promise();
			siteRequest_Init();
			pkInit();
			inheritPkInit();
			idInit();
			createdInit();
			modifiedInit();
			classCanonicalNameInit();
			classSimpleNameInit();
			classCanonicalNamesInit();
			savesInit();
			objectTitleInit();
			objectIdInit();
			promise2.complete();
			return promise2.future();
		}).onSuccess(a -> {
			promise.complete();
		}).onFailure(ex -> {
			promise.fail(ex);
		});
		return promise.future();
	}

	public Future<Void> promiseDeepForClass(SiteRequestEnUS siteRequest_) {
		return promiseDeepCluster(siteRequest_);
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
			else if(o instanceof Map) {
				Map<?, ?> map = (Map<?, ?>)o;
				o = map.get(v);
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
			case "classCanonicalName":
				return oCluster.classCanonicalName;
			case "classSimpleName":
				return oCluster.classSimpleName;
			case "classCanonicalNames":
				return oCluster.classCanonicalNames;
			case "saves":
				return oCluster.saves;
			case "objectTitle":
				return oCluster.objectTitle;
			case "objectId":
				return oCluster.objectId;
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
		case "classCanonicalName":
			return Cluster.staticSetClassCanonicalName(siteRequest_, o);
		case "classSimpleName":
			return Cluster.staticSetClassSimpleName(siteRequest_, o);
		case "classCanonicalNames":
			return Cluster.staticSetClassCanonicalNames(siteRequest_, o);
		case "saves":
			return Cluster.staticSetSaves(siteRequest_, o);
		case "objectTitle":
			return Cluster.staticSetObjectTitle(siteRequest_, o);
		case "objectId":
			return Cluster.staticSetObjectId(siteRequest_, o);
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
			return Cluster.staticSolrInheritPk(siteRequest_, (String)o);
		case "id":
			return Cluster.staticSolrId(siteRequest_, (String)o);
		case "created":
			return Cluster.staticSolrCreated(siteRequest_, (ZonedDateTime)o);
		case "modified":
			return Cluster.staticSolrModified(siteRequest_, (ZonedDateTime)o);
		case "classCanonicalName":
			return Cluster.staticSolrClassCanonicalName(siteRequest_, (String)o);
		case "classSimpleName":
			return Cluster.staticSolrClassSimpleName(siteRequest_, (String)o);
		case "classCanonicalNames":
			return Cluster.staticSolrClassCanonicalNames(siteRequest_, (String)o);
		case "saves":
			return Cluster.staticSolrSaves(siteRequest_, (String)o);
		case "objectTitle":
			return Cluster.staticSolrObjectTitle(siteRequest_, (String)o);
		case "objectId":
			return Cluster.staticSolrObjectId(siteRequest_, (String)o);
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
			return Cluster.staticSolrStrInheritPk(siteRequest_, (String)o);
		case "id":
			return Cluster.staticSolrStrId(siteRequest_, (String)o);
		case "created":
			return Cluster.staticSolrStrCreated(siteRequest_, (Date)o);
		case "modified":
			return Cluster.staticSolrStrModified(siteRequest_, (Date)o);
		case "classCanonicalName":
			return Cluster.staticSolrStrClassCanonicalName(siteRequest_, (String)o);
		case "classSimpleName":
			return Cluster.staticSolrStrClassSimpleName(siteRequest_, (String)o);
		case "classCanonicalNames":
			return Cluster.staticSolrStrClassCanonicalNames(siteRequest_, (String)o);
		case "saves":
			return Cluster.staticSolrStrSaves(siteRequest_, (String)o);
		case "objectTitle":
			return Cluster.staticSolrStrObjectTitle(siteRequest_, (String)o);
		case "objectId":
			return Cluster.staticSolrStrObjectId(siteRequest_, (String)o);
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
		case "classCanonicalName":
			return Cluster.staticSolrFqClassCanonicalName(siteRequest_, o);
		case "classSimpleName":
			return Cluster.staticSolrFqClassSimpleName(siteRequest_, o);
		case "classCanonicalNames":
			return Cluster.staticSolrFqClassCanonicalNames(siteRequest_, o);
		case "saves":
			return Cluster.staticSolrFqSaves(siteRequest_, o);
		case "objectTitle":
			return Cluster.staticSolrFqObjectTitle(siteRequest_, o);
		case "objectId":
			return Cluster.staticSolrFqObjectId(siteRequest_, o);
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
		switch(var.toLowerCase()) {
			case "inheritpk":
				if(val != null)
					setInheritPk(val);
				saves.add("inheritPk");
				return val;
			case "created":
				if(val != null)
					setCreated(val);
				saves.add("created");
				return val;
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
					o = defineCluster(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineCluster(String var, Object val) {
		switch(var.toLowerCase()) {
			case "inheritpk":
				if(val instanceof String)
					setInheritPk((String)val);
				saves.add("inheritPk");
				return val;
			case "created":
				if(val instanceof ZonedDateTime)
					setCreated((ZonedDateTime)val);
				else if(val instanceof OffsetDateTime)
					setCreated(((OffsetDateTime)val).atZoneSameInstant(ZoneId.of(siteRequest_.getConfig().getString(ConfigKeys.SITE_ZONE))));
				saves.add("created");
				return val;
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

	public void indexCluster(SolrInputDocument document) {
		if(pk != null) {
			document.addField("pk_indexed_long", pk);
			document.addField("pk_stored_long", pk);
		}
		if(inheritPk != null) {
			document.addField("inheritPk_indexed_string", inheritPk);
			document.addField("inheritPk_stored_string", inheritPk);
		}
		if(id != null) {
			document.addField("id", id);
			document.addField("id_indexed_string", id);
		}
		if(created != null) {
			document.addField("created_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(created.toInstant(), ZoneId.of("UTC"))));
			document.addField("created_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(created.toInstant(), ZoneId.of("UTC"))));
		}
		if(modified != null) {
			document.addField("modified_indexed_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(modified.toInstant(), ZoneId.of("UTC"))));
			document.addField("modified_stored_date", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(ZonedDateTime.ofInstant(modified.toInstant(), ZoneId.of("UTC"))));
		}
		if(classCanonicalName != null) {
			document.addField("classCanonicalName_indexed_string", classCanonicalName);
			document.addField("classCanonicalName_stored_string", classCanonicalName);
		}
		if(classSimpleName != null) {
			document.addField("classSimpleName_indexed_string", classSimpleName);
			document.addField("classSimpleName_stored_string", classSimpleName);
		}
		if(classCanonicalNames != null) {
			for(java.lang.String o : classCanonicalNames) {
				document.addField("classCanonicalNames_indexed_strings", o);
			}
			for(java.lang.String o : classCanonicalNames) {
				document.addField("classCanonicalNames_stored_strings", o);
			}
		}
		if(saves != null) {
			for(java.lang.String o : saves) {
				document.addField("saves_indexed_strings", o);
			}
			for(java.lang.String o : saves) {
				document.addField("saves_stored_strings", o);
			}
		}
		if(objectTitle != null) {
			document.addField("objectTitle_indexed_string", objectTitle);
			document.addField("objectTitle_stored_string", objectTitle);
		}
		if(objectId != null) {
			document.addField("objectId_indexed_string", objectId);
			document.addField("objectId_stored_string", objectId);
		}
	}

	public static String varIndexedCluster(String entityVar) {
		switch(entityVar) {
			case "pk":
				return "pk_indexed_long";
			case "inheritPk":
				return "inheritPk_indexed_string";
			case "id":
				return "id_indexed_string";
			case "created":
				return "created_indexed_date";
			case "modified":
				return "modified_indexed_date";
			case "classCanonicalName":
				return "classCanonicalName_indexed_string";
			case "classSimpleName":
				return "classSimpleName_indexed_string";
			case "classCanonicalNames":
				return "classCanonicalNames_indexed_strings";
			case "saves":
				return "saves_indexed_strings";
			case "objectTitle":
				return "objectTitle_indexed_string";
			case "objectId":
				return "objectId_indexed_string";
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

		Long pk = (Long)solrDocument.get("pk_stored_long");
		if(pk != null)
			oCluster.setPk(pk);

		String inheritPk = (String)solrDocument.get("inheritPk_stored_string");
		if(inheritPk != null)
			oCluster.setInheritPk(inheritPk);

		String id = (String)solrDocument.get("id");
		oCluster.setId(id);

		Date created = (Date)solrDocument.get("created_stored_date");
		if(created != null)
			oCluster.setCreated(created);

		Date modified = (Date)solrDocument.get("modified_stored_date");
		if(modified != null)
			oCluster.setModified(modified);

		String classCanonicalName = (String)solrDocument.get("classCanonicalName_stored_string");
		if(classCanonicalName != null)
			oCluster.setClassCanonicalName(classCanonicalName);

		String classSimpleName = (String)solrDocument.get("classSimpleName_stored_string");
		if(classSimpleName != null)
			oCluster.setClassSimpleName(classSimpleName);

		List<String> classCanonicalNames = (List<String>)solrDocument.get("classCanonicalNames_stored_strings");
		if(classCanonicalNames != null)
			oCluster.classCanonicalNames.addAll(classCanonicalNames);

		List<String> saves = (List<String>)solrDocument.get("saves_stored_strings");
		if(saves != null)
			oCluster.saves.addAll(saves);

		String objectTitle = (String)solrDocument.get("objectTitle_stored_string");
		if(objectTitle != null)
			oCluster.setObjectTitle(objectTitle);

		String objectId = (String)solrDocument.get("objectId_stored_string");
		if(objectId != null)
			oCluster.setObjectId(objectId);
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestCluster() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof Cluster) {
			Cluster original = (Cluster)o;
			if(!Objects.equals(pk, original.getPk()))
				apiRequest.addVars("pk");
			if(!Objects.equals(inheritPk, original.getInheritPk()))
				apiRequest.addVars("inheritPk");
			if(!Objects.equals(id, original.getId()))
				apiRequest.addVars("id");
			if(!Objects.equals(created, original.getCreated()))
				apiRequest.addVars("created");
			if(!Objects.equals(modified, original.getModified()))
				apiRequest.addVars("modified");
			if(!Objects.equals(classCanonicalName, original.getClassCanonicalName()))
				apiRequest.addVars("classCanonicalName");
			if(!Objects.equals(classSimpleName, original.getClassSimpleName()))
				apiRequest.addVars("classSimpleName");
			if(!Objects.equals(classCanonicalNames, original.getClassCanonicalNames()))
				apiRequest.addVars("classCanonicalNames");
			if(!Objects.equals(saves, original.getSaves()))
				apiRequest.addVars("saves");
			if(!Objects.equals(objectTitle, original.getObjectTitle()))
				apiRequest.addVars("objectTitle");
			if(!Objects.equals(objectId, original.getObjectId()))
				apiRequest.addVars("objectId");
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(pk, inheritPk, id, created, modified, classCanonicalName, classSimpleName, classCanonicalNames, saves, objectTitle, objectId);
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
		return Objects.equals( pk, that.pk )
				&& Objects.equals( inheritPk, that.inheritPk )
				&& Objects.equals( id, that.id )
				&& Objects.equals( created, that.created )
				&& Objects.equals( modified, that.modified )
				&& Objects.equals( classCanonicalName, that.classCanonicalName )
				&& Objects.equals( classSimpleName, that.classSimpleName )
				&& Objects.equals( classCanonicalNames, that.classCanonicalNames )
				&& Objects.equals( saves, that.saves )
				&& Objects.equals( objectTitle, that.objectTitle )
				&& Objects.equals( objectId, that.objectId );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Cluster { ");
		sb.append( "pk: " ).append(pk);
		sb.append( ", inheritPk: \"" ).append(inheritPk).append( "\"" );
		sb.append( ", id: \"" ).append(id).append( "\"" );
		sb.append( ", created: " ).append(created);
		sb.append( ", modified: " ).append(modified);
		sb.append( ", classCanonicalName: \"" ).append(classCanonicalName).append( "\"" );
		sb.append( ", classSimpleName: \"" ).append(classSimpleName).append( "\"" );
		sb.append( ", classCanonicalNames: " ).append(classCanonicalNames);
		sb.append( ", saves: " ).append(saves);
		sb.append( ", objectTitle: \"" ).append(objectTitle).append( "\"" );
		sb.append( ", objectId: \"" ).append(objectId).append( "\"" );
		sb.append(" }");
		return sb.toString();
	}
}
