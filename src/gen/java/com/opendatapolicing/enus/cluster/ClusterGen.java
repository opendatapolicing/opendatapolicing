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
import com.opendatapolicing.enus.request.api.ApiRequest;
import java.time.ZoneId;
import com.opendatapolicing.enus.context.SiteContextEnUS;
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
import java.lang.Boolean;
import java.lang.String;
import org.slf4j.Logger;
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

	public Long sqlInheritPk() {
		return inheritPk;
	}

	public String jsonInheritPk() {
		return inheritPk == null ? "" : inheritPk.toString();
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

	public String sqlModifiedIsoOffsetDateTime() {
		return modifiedIsoOffsetDateTime;
	}

	public String jsonModifiedIsoOffsetDateTime() {
		return modifiedIsoOffsetDateTime == null ? "" : modifiedIsoOffsetDateTime;
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

	public Boolean sqlArchived() {
		return archived;
	}

	public String jsonArchived() {
		return archived == null ? "" : archived.toString();
	}

	public String htmTooltipArchived() {
		return null;
	}

	public String htmArchived() {
		return archived == null ? "" : StringEscapeUtils.escapeHtml4(strArchived());
	}

	public void inputArchived(String classApiMethodMethod) {
		Cluster s = (Cluster)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			if("Page".equals(classApiMethodMethod)) {
				s.e("input")
					.a("type", "checkbox")
					.a("id", classApiMethodMethod, "_archived")
					.a("value", "true");
			} else {
				s.e("select")
					.a("id", classApiMethodMethod, "_archived");
			}
			if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
				s.a("class", "setArchived classCluster inputCluster", pk, "Archived w3-input w3-border ");
				s.a("name", "setArchived");
			} else {
				s.a("class", "valueArchived classCluster inputCluster", pk, "Archived w3-input w3-border ");
				s.a("name", "archived");
			}
			if("Page".equals(classApiMethodMethod)) {
				s.a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setArchived', $(this).prop('checked'), function() { addGlow($('#", classApiMethodMethod, "_archived')); }, function() { addError($('#", classApiMethodMethod, "_archived')); }); ");
			}
			if("Page".equals(classApiMethodMethod)) {
				if(getArchived() != null && getArchived())
					s.a("checked", "checked");
				s.fg();
			} else {
				s.f();
				s.e("option").a("value", "").a("selected", "selected").f().g("option");
				s.e("option").a("value", "true").f().sx("true").g("option");
				s.e("option").a("value", "false").f().sx("false").g("option");
				s.g("select");
			}

		} else {
				s.e("span").a("class", "varCluster", pk, "Archived ").f().sx(htmArchived()).g("span");
		}
	}

	public void htmArchived(String classApiMethodMethod) {
		Cluster s = (Cluster)this;
		{ s.e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ s.e("div").a("class", "w3-padding ").f();
				{ s.e("div").a("id", "suggest", classApiMethodMethod, "ClusterArchived").f();
					{ s.e("div").a("class", "w3-card ").f();
						{ s.e("div").a("class", "w3-cell-row w3-gray ").f();
							s.e("label").a("for", classApiMethodMethod, "_archived").a("class", "").f().sx("archived").g("label");
						} s.g("div");
						{ s.e("div").a("class", "w3-cell-row w3-padding ").f();
							{ s.e("div").a("class", "w3-cell ").f();

								inputArchived(classApiMethodMethod);
							} s.g("div");
						} s.g("div");
					} s.g("div");
				} s.g("div");
			} s.g("div");
		} s.g("div");
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

	public Boolean sqlDeleted() {
		return deleted;
	}

	public String jsonDeleted() {
		return deleted == null ? "" : deleted.toString();
	}

	public String htmTooltipDeleted() {
		return null;
	}

	public String htmDeleted() {
		return deleted == null ? "" : StringEscapeUtils.escapeHtml4(strDeleted());
	}

	public void inputDeleted(String classApiMethodMethod) {
		Cluster s = (Cluster)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			if("Page".equals(classApiMethodMethod)) {
				s.e("input")
					.a("type", "checkbox")
					.a("id", classApiMethodMethod, "_deleted")
					.a("value", "true");
			} else {
				s.e("select")
					.a("id", classApiMethodMethod, "_deleted");
			}
			if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
				s.a("class", "setDeleted classCluster inputCluster", pk, "Deleted w3-input w3-border ");
				s.a("name", "setDeleted");
			} else {
				s.a("class", "valueDeleted classCluster inputCluster", pk, "Deleted w3-input w3-border ");
				s.a("name", "deleted");
			}
			if("Page".equals(classApiMethodMethod)) {
				s.a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setDeleted', $(this).prop('checked'), function() { addGlow($('#", classApiMethodMethod, "_deleted')); }, function() { addError($('#", classApiMethodMethod, "_deleted')); }); ");
			}
			if("Page".equals(classApiMethodMethod)) {
				if(getDeleted() != null && getDeleted())
					s.a("checked", "checked");
				s.fg();
			} else {
				s.f();
				s.e("option").a("value", "").a("selected", "selected").f().g("option");
				s.e("option").a("value", "true").f().sx("true").g("option");
				s.e("option").a("value", "false").f().sx("false").g("option");
				s.g("select");
			}

		} else {
				s.e("span").a("class", "varCluster", pk, "Deleted ").f().sx(htmDeleted()).g("span");
		}
	}

	public void htmDeleted(String classApiMethodMethod) {
		Cluster s = (Cluster)this;
		{ s.e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ s.e("div").a("class", "w3-padding ").f();
				{ s.e("div").a("id", "suggest", classApiMethodMethod, "ClusterDeleted").f();
					{ s.e("div").a("class", "w3-card ").f();
						{ s.e("div").a("class", "w3-cell-row w3-gray ").f();
							s.e("label").a("for", classApiMethodMethod, "_deleted").a("class", "").f().sx("deleted").g("label");
						} s.g("div");
						{ s.e("div").a("class", "w3-cell-row w3-padding ").f();
							{ s.e("div").a("class", "w3-cell ").f();

								inputDeleted(classApiMethodMethod);
							} s.g("div");
						} s.g("div");
					} s.g("div");
				} s.g("div");
			} s.g("div");
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

	public String sqlSessionId() {
		return sessionId;
	}

	public String jsonSessionId() {
		return sessionId == null ? "" : sessionId;
	}

	public String htmTooltipSessionId() {
		return null;
	}

	public String htmSessionId() {
		return sessionId == null ? "" : StringEscapeUtils.escapeHtml4(strSessionId());
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

	public Long sqlUserKey() {
		return userKey;
	}

	public String jsonUserKey() {
		return userKey == null ? "" : userKey.toString();
	}

	public String htmTooltipUserKey() {
		return null;
	}

	public String htmUserKey() {
		return userKey == null ? "" : StringEscapeUtils.escapeHtml4(strUserKey());
	}

	public void inputUserKey(String classApiMethodMethod) {
		Cluster s = (Cluster)this;
		s.e("span").a("class", "varCluster", pk, "UserKey ").f().sx(htmUserKey()).g("span");
	}

	public void htmUserKey(String classApiMethodMethod) {
		Cluster s = (Cluster)this;
		{ s.e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ s.e("div").a("class", "w3-padding ").f();
				{ s.e("div").a("id", "suggest", classApiMethodMethod, "ClusterUserKey").f();
					{ s.e("div").a("class", "w3-card ").f();
						{ s.e("div").a("class", "w3-cell-row w3-padding ").f();
							{ s.e("div").a("class", "w3-cell ").f();

								inputUserKey(classApiMethodMethod);
							} s.g("div");
						} s.g("div");
					} s.g("div");
				} s.g("div");
			} s.g("div");
		} s.g("div");
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

	public String sqlObjectNameVar() {
		return objectNameVar;
	}

	public String jsonObjectNameVar() {
		return objectNameVar == null ? "" : objectNameVar;
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

	public String sqlObjectSuggest() {
		return objectSuggest;
	}

	public String jsonObjectSuggest() {
		return objectSuggest == null ? "" : objectSuggest;
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

	public String sqlObjectText() {
		return objectText;
	}

	public String jsonObjectText() {
		return objectText == null ? "" : objectText;
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

	public String sqlPageUrlId() {
		return pageUrlId;
	}

	public String jsonPageUrlId() {
		return pageUrlId == null ? "" : pageUrlId;
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

	public String sqlPageUrlPk() {
		return pageUrlPk;
	}

	public String jsonPageUrlPk() {
		return pageUrlPk == null ? "" : pageUrlPk;
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

	public String sqlPageUrlApi() {
		return pageUrlApi;
	}

	public String jsonPageUrlApi() {
		return pageUrlApi == null ? "" : pageUrlApi;
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

	public String sqlPageH1() {
		return pageH1;
	}

	public String jsonPageH1() {
		return pageH1 == null ? "" : pageH1;
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
			case "archived":
				if(val != null)
					setArchived(val);
				saves.add("archived");
				return val;
			case "deleted":
				if(val != null)
					setDeleted(val);
				saves.add("deleted");
				return val;
			case "userkey":
				if(val != null)
					setUserKey(val);
				saves.add("userKey");
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
				if(val instanceof Long)
					setInheritPk((Long)val);
				saves.add("inheritPk");
				return val;
			case "created":
				if(val instanceof ZonedDateTime)
					setCreated((ZonedDateTime)val);
				else if(val instanceof OffsetDateTime)
					setCreated(((OffsetDateTime)val).atZoneSameInstant(ZoneId.of(siteRequest_.getSiteConfig_().getSiteZone())));
				saves.add("created");
				return val;
			case "archived":
				if(val instanceof Boolean)
					setArchived((Boolean)val);
				saves.add("archived");
				return val;
			case "deleted":
				if(val instanceof Boolean)
					setDeleted((Boolean)val);
				saves.add("deleted");
				return val;
			case "userkey":
				if(val instanceof Long)
					setUserKey((Long)val);
				saves.add("userKey");
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
		if(pk != null) {
			document.addField("pk_indexed_long", pk);
			document.addField("pk_stored_long", pk);
		}
		if(inheritPk != null) {
			document.addField("inheritPk_indexed_long", inheritPk);
			document.addField("inheritPk_stored_long", inheritPk);
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
		if(modifiedIsoOffsetDateTime != null) {
			document.addField("modifiedIsoOffsetDateTime_stored_string", modifiedIsoOffsetDateTime);
		}
		if(archived != null) {
			document.addField("archived_indexed_boolean", archived);
			document.addField("archived_stored_boolean", archived);
		}
		if(deleted != null) {
			document.addField("deleted_indexed_boolean", deleted);
			document.addField("deleted_stored_boolean", deleted);
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
		if(sessionId != null) {
			document.addField("sessionId_indexed_string", sessionId);
			document.addField("sessionId_stored_string", sessionId);
		}
		if(userKey != null) {
			document.addField("userKey_indexed_long", userKey);
			document.addField("userKey_stored_long", userKey);
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
		if(objectSuggest != null) {
			document.addField("objectSuggest_suggested", objectSuggest);
		}
		if(objectText != null) {
			document.addField("objectText_text_enUS", objectText.toString());
			document.addField("objectText_indexed_string", objectText);
		}
		if(pageUrlId != null) {
			document.addField("pageUrlId_indexed_string", pageUrlId);
			document.addField("pageUrlId_stored_string", pageUrlId);
		}
		if(pageUrlPk != null) {
			document.addField("pageUrlPk_indexed_string", pageUrlPk);
			document.addField("pageUrlPk_stored_string", pageUrlPk);
		}
	}

	public static String varIndexedCluster(String entityVar) {
		switch(entityVar) {
			case "pk":
				return "pk_indexed_long";
			case "inheritPk":
				return "inheritPk_indexed_long";
			case "id":
				return "id_indexed_string";
			case "created":
				return "created_indexed_date";
			case "modified":
				return "modified_indexed_date";
			case "archived":
				return "archived_indexed_boolean";
			case "deleted":
				return "deleted_indexed_boolean";
			case "classCanonicalName":
				return "classCanonicalName_indexed_string";
			case "classSimpleName":
				return "classSimpleName_indexed_string";
			case "classCanonicalNames":
				return "classCanonicalNames_indexed_strings";
			case "sessionId":
				return "sessionId_indexed_string";
			case "userKey":
				return "userKey_indexed_long";
			case "saves":
				return "saves_indexed_strings";
			case "objectTitle":
				return "objectTitle_indexed_string";
			case "objectId":
				return "objectId_indexed_string";
			case "objectSuggest":
				return "objectSuggest_suggested";
			case "objectText":
				return "objectText_text_enUS";
			case "pageUrlId":
				return "pageUrlId_indexed_string";
			case "pageUrlPk":
				return "pageUrlPk_indexed_string";
			default:
				return null;
		}
	}

	public static String varSearchCluster(String entityVar) {
		switch(entityVar) {
			case "objectText":
				return "objectText_text_enUS";
			case "objectSuggest":
				return "objectSuggest_suggested";
			default:
				return null;
		}
	}

	public static String varSuggestedCluster(String entityVar) {
		switch(entityVar) {
			case "objectSuggest":
				return "objectSuggest_suggested";
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

		Long inheritPk = (Long)solrDocument.get("inheritPk_stored_long");
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

		String modifiedIsoOffsetDateTime = (String)solrDocument.get("modifiedIsoOffsetDateTime_stored_string");
		if(modifiedIsoOffsetDateTime != null)
			oCluster.setModifiedIsoOffsetDateTime(modifiedIsoOffsetDateTime);

		Boolean archived = (Boolean)solrDocument.get("archived_stored_boolean");
		if(archived != null)
			oCluster.setArchived(archived);

		Boolean deleted = (Boolean)solrDocument.get("deleted_stored_boolean");
		if(deleted != null)
			oCluster.setDeleted(deleted);

		String classCanonicalName = (String)solrDocument.get("classCanonicalName_stored_string");
		if(classCanonicalName != null)
			oCluster.setClassCanonicalName(classCanonicalName);

		String classSimpleName = (String)solrDocument.get("classSimpleName_stored_string");
		if(classSimpleName != null)
			oCluster.setClassSimpleName(classSimpleName);

		List<String> classCanonicalNames = (List<String>)solrDocument.get("classCanonicalNames_stored_strings");
		if(classCanonicalNames != null)
			oCluster.classCanonicalNames.addAll(classCanonicalNames);

		String sessionId = (String)solrDocument.get("sessionId_stored_string");
		if(sessionId != null)
			oCluster.setSessionId(sessionId);

		Long userKey = (Long)solrDocument.get("userKey_stored_long");
		if(userKey != null)
			oCluster.setUserKey(userKey);

		List<String> saves = (List<String>)solrDocument.get("saves_stored_strings");
		if(saves != null)
			oCluster.saves.addAll(saves);

		String objectTitle = (String)solrDocument.get("objectTitle_stored_string");
		if(objectTitle != null)
			oCluster.setObjectTitle(objectTitle);

		String objectId = (String)solrDocument.get("objectId_stored_string");
		if(objectId != null)
			oCluster.setObjectId(objectId);

		String objectSuggest = (String)solrDocument.get("objectSuggest_suggested");
		oCluster.setObjectSuggest(objectSuggest);

		String objectText = (String)solrDocument.get("objectText_stored_string");
		if(objectText != null)
			oCluster.setObjectText(objectText);

		String pageUrlId = (String)solrDocument.get("pageUrlId_stored_string");
		if(pageUrlId != null)
			oCluster.setPageUrlId(pageUrlId);

		String pageUrlPk = (String)solrDocument.get("pageUrlPk_stored_string");
		if(pageUrlPk != null)
			oCluster.setPageUrlPk(pageUrlPk);
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
			if(!Objects.equals(modifiedIsoOffsetDateTime, original.getModifiedIsoOffsetDateTime()))
				apiRequest.addVars("modifiedIsoOffsetDateTime");
			if(!Objects.equals(archived, original.getArchived()))
				apiRequest.addVars("archived");
			if(!Objects.equals(deleted, original.getDeleted()))
				apiRequest.addVars("deleted");
			if(!Objects.equals(classCanonicalName, original.getClassCanonicalName()))
				apiRequest.addVars("classCanonicalName");
			if(!Objects.equals(classSimpleName, original.getClassSimpleName()))
				apiRequest.addVars("classSimpleName");
			if(!Objects.equals(classCanonicalNames, original.getClassCanonicalNames()))
				apiRequest.addVars("classCanonicalNames");
			if(!Objects.equals(sessionId, original.getSessionId()))
				apiRequest.addVars("sessionId");
			if(!Objects.equals(userKey, original.getUserKey()))
				apiRequest.addVars("userKey");
			if(!Objects.equals(saves, original.getSaves()))
				apiRequest.addVars("saves");
			if(!Objects.equals(objectTitle, original.getObjectTitle()))
				apiRequest.addVars("objectTitle");
			if(!Objects.equals(objectId, original.getObjectId()))
				apiRequest.addVars("objectId");
			if(!Objects.equals(objectSuggest, original.getObjectSuggest()))
				apiRequest.addVars("objectSuggest");
			if(!Objects.equals(objectText, original.getObjectText()))
				apiRequest.addVars("objectText");
			if(!Objects.equals(pageUrlId, original.getPageUrlId()))
				apiRequest.addVars("pageUrlId");
			if(!Objects.equals(pageUrlPk, original.getPageUrlPk()))
				apiRequest.addVars("pageUrlPk");
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(pk, inheritPk, id, created, modified, modifiedIsoOffsetDateTime, archived, deleted, classCanonicalName, classSimpleName, classCanonicalNames, sessionId, userKey, saves, objectTitle, objectId, objectSuggest, objectText, pageUrlId, pageUrlPk);
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
				&& Objects.equals( modifiedIsoOffsetDateTime, that.modifiedIsoOffsetDateTime )
				&& Objects.equals( archived, that.archived )
				&& Objects.equals( deleted, that.deleted )
				&& Objects.equals( classCanonicalName, that.classCanonicalName )
				&& Objects.equals( classSimpleName, that.classSimpleName )
				&& Objects.equals( classCanonicalNames, that.classCanonicalNames )
				&& Objects.equals( sessionId, that.sessionId )
				&& Objects.equals( userKey, that.userKey )
				&& Objects.equals( saves, that.saves )
				&& Objects.equals( objectTitle, that.objectTitle )
				&& Objects.equals( objectId, that.objectId )
				&& Objects.equals( objectSuggest, that.objectSuggest )
				&& Objects.equals( objectText, that.objectText )
				&& Objects.equals( pageUrlId, that.pageUrlId )
				&& Objects.equals( pageUrlPk, that.pageUrlPk );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Cluster { ");
		sb.append( "pk: " ).append(pk);
		sb.append( ", inheritPk: " ).append(inheritPk);
		sb.append( ", id: \"" ).append(id).append( "\"" );
		sb.append( ", created: " ).append(created);
		sb.append( ", modified: " ).append(modified);
		sb.append( ", modifiedIsoOffsetDateTime: \"" ).append(modifiedIsoOffsetDateTime).append( "\"" );
		sb.append( ", archived: " ).append(archived);
		sb.append( ", deleted: " ).append(deleted);
		sb.append( ", classCanonicalName: \"" ).append(classCanonicalName).append( "\"" );
		sb.append( ", classSimpleName: \"" ).append(classSimpleName).append( "\"" );
		sb.append( ", classCanonicalNames: " ).append(classCanonicalNames);
		sb.append( ", sessionId: \"" ).append(sessionId).append( "\"" );
		sb.append( ", userKey: " ).append(userKey);
		sb.append( ", saves: " ).append(saves);
		sb.append( ", objectTitle: \"" ).append(objectTitle).append( "\"" );
		sb.append( ", objectId: \"" ).append(objectId).append( "\"" );
		sb.append( ", objectSuggest: \"" ).append(objectSuggest).append( "\"" );
		sb.append( ", objectText: \"" ).append(objectText).append( "\"" );
		sb.append( ", pageUrlId: \"" ).append(pageUrlId).append( "\"" );
		sb.append( ", pageUrlPk: \"" ).append(pageUrlPk).append( "\"" );
		sb.append(" }");
		return sb.toString();
	}
}
