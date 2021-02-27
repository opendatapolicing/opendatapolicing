package com.opendatapolicing.enus.user;

import java.util.Arrays;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.Date;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import java.text.NumberFormat;
import io.vertx.core.logging.LoggerFactory;
import java.util.ArrayList;
import org.apache.commons.collections.CollectionUtils;
import java.lang.Long;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.Boolean;
import io.vertx.core.json.JsonObject;
import java.lang.String;
import io.vertx.core.logging.Logger;
import java.math.RoundingMode;
import com.opendatapolicing.enus.wrap.Wrap;
import java.math.MathContext;
import org.apache.solr.client.solrj.response.QueryResponse;
import java.util.Set;
import com.opendatapolicing.enus.writer.AllWriter;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.opendatapolicing.enus.context.SiteContextEnUS;
import com.opendatapolicing.enus.request.api.ApiRequest;
import org.apache.solr.client.solrj.SolrClient;
import java.util.Objects;
import io.vertx.core.json.JsonArray;
import org.apache.solr.common.SolrDocument;
import java.util.List;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.commons.lang3.math.NumberUtils;
import java.util.Optional;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.opendatapolicing.enus.cluster.Cluster;
import org.apache.solr.client.solrj.util.ClientUtils;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.apache.solr.common.SolrInputDocument;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**	
 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstClasse_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.user.SiteUser&fq=classeEtendGen_indexed_boolean:true">Find the class  in Solr. </a>
 * <br/>
 **/
public abstract class SiteUserGen<DEV> extends Cluster {
	protected static final Logger LOGGER = LoggerFactory.getLogger(SiteUser.class);

	public static final List<String> ROLES = Arrays.asList("SiteAdmin", "SiteAdmin");
	public static final List<String> ROLE_READS = Arrays.asList("");

	public static final String SiteUser_AName = "a site user";
	public static final String SiteUser_This = "this ";
	public static final String SiteUser_ThisName = "this site user";
	public static final String SiteUser_A = "a ";
	public static final String SiteUser_TheName = "the site user";
	public static final String SiteUser_NameSingular = "site user";
	public static final String SiteUser_NamePlural = "site users";
	public static final String SiteUser_NameActual = "current site user";
	public static final String SiteUser_AllName = "all the site users";
	public static final String SiteUser_SearchAllNameBy = "search site users by ";
	public static final String SiteUser_Title = "site users";
	public static final String SiteUser_ThePluralName = "the site users";
	public static final String SiteUser_NoNameFound = "no site user found";
	public static final String SiteUser_NameVar = "siteUser";
	public static final String SiteUser_OfName = "of site user";
	public static final String SiteUser_ANameAdjective = "a site user";
	public static final String SiteUser_NameAdjectiveSingular = "site user";
	public static final String SiteUser_NameAdjectivePlural = "site users";
	public static final String SiteUser_IconGroup = "regular";
	public static final String SiteUser_IconName = "user-cog";

	//////////////
	// userKeys //
	//////////////

	/**	 The entity userKeys
	 *	Il est construit avant d'être initialisé avec le constructeur par défaut List<Long>(). 
	 */
	@JsonSerialize(contentUsing = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected List<Long> userKeys = new ArrayList<Long>();
	@JsonIgnore
	public Wrap<List<Long>> userKeysWrap = new Wrap<List<Long>>().p(this).c(List.class).var("userKeys").o(userKeys);

	/**	<br/> The entity userKeys
	 *  It is constructed before being initialized with the constructor by default List<Long>(). 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.user.SiteUser&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:userKeys">Find the entity userKeys in Solr</a>
	 * <br/>
	 * @param userKeys is the entity already constructed. 
	 **/
	protected abstract void _userKeys(List<Long> l);

	public List<Long> getUserKeys() {
		return userKeys;
	}

	public void setUserKeys(List<Long> userKeys) {
		this.userKeys = userKeys;
		this.userKeysWrap.alreadyInitialized = true;
	}
	public void setUserKeys(String o) {
		Long l = SiteUser.staticSetUserKeys(siteRequest_, o);
		if(l != null)
			addUserKeys(l);
		this.userKeysWrap.alreadyInitialized = true;
	}
	public static Long staticSetUserKeys(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	public SiteUser addUserKeys(Long...objets) {
		for(Long o : objets) {
			addUserKeys(o);
		}
		return (SiteUser)this;
	}
	public SiteUser addUserKeys(Long o) {
		if(o != null && !userKeys.contains(o))
			this.userKeys.add(o);
		return (SiteUser)this;
	}
	public void setUserKeys(JsonArray objets) {
		userKeys.clear();
		for(int i = 0; i < objets.size(); i++) {
			Long o = objets.getLong(i);
			addUserKeys(o);
		}
	}
	public SiteUser addUserKeys(String o) {
		if(NumberUtils.isParsable(o)) {
			Long p = Long.parseLong(o);
			addUserKeys(p);
		}
		return (SiteUser)this;
	}
	protected SiteUser userKeysInit() {
		if(!userKeysWrap.alreadyInitialized) {
			_userKeys(userKeys);
		}
		userKeysWrap.alreadyInitialized(true);
		return (SiteUser)this;
	}

	public static Long staticSolrUserKeys(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrUserKeys(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqUserKeys(SiteRequestEnUS siteRequest_, String o) {
		return SiteUser.staticSolrStrUserKeys(siteRequest_, SiteUser.staticSolrUserKeys(siteRequest_, SiteUser.staticSetUserKeys(siteRequest_, o)));
	}

	public List<Long> solrUserKeys() {
		List<Long> l = new ArrayList<Long>();
		for(Long o : userKeys) {
			l.add(SiteUser.staticSolrUserKeys(siteRequest_, o));
		}
		return l;
	}

	public String strUserKeys() {
		return userKeys == null ? "" : userKeys.toString();
	}

	public List<Long> sqlUserKeys() {
		return userKeys;
	}

	public String jsonUserKeys() {
		return userKeys == null ? "" : userKeys.toString();
	}

	public String nomAffichageUserKeys() {
		return null;
	}

	public String htmTooltipUserKeys() {
		return null;
	}

	public String htmUserKeys() {
		return userKeys == null ? "" : StringEscapeUtils.escapeHtml4(strUserKeys());
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.user.SiteUser&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:userId">Find the entity userId in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _userId(Wrap<String> c);

	public String getUserId() {
		return userId;
	}
	public void setUserId(String o) {
		this.userId = SiteUser.staticSetUserId(siteRequest_, o);
		this.userIdWrap.alreadyInitialized = true;
	}
	public static String staticSetUserId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteUser userIdInit() {
		if(!userIdWrap.alreadyInitialized) {
			_userId(userIdWrap);
			if(userId == null)
				setUserId(userIdWrap.o);
		}
		userIdWrap.alreadyInitialized(true);
		return (SiteUser)this;
	}

	public static String staticSolrUserId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrUserId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqUserId(SiteRequestEnUS siteRequest_, String o) {
		return SiteUser.staticSolrStrUserId(siteRequest_, SiteUser.staticSolrUserId(siteRequest_, SiteUser.staticSetUserId(siteRequest_, o)));
	}

	public String solrUserId() {
		return SiteUser.staticSolrUserId(siteRequest_, userId);
	}

	public String strUserId() {
		return userId == null ? "" : userId;
	}

	public String sqlUserId() {
		return userId;
	}

	public String jsonUserId() {
		return userId == null ? "" : userId;
	}

	public String nomAffichageUserId() {
		return "user ID";
	}

	public String htmTooltipUserId() {
		return null;
	}

	public String htmUserId() {
		return userId == null ? "" : StringEscapeUtils.escapeHtml4(strUserId());
	}

	public void inputUserId(String classApiMethodMethod) {
		SiteUser s = (SiteUser)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "user ID")
				.a("id", classApiMethodMethod, "_userId");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setUserId classSiteUser inputSiteUser", pk, "UserId w3-input w3-border ");
					a("name", "setUserId");
				} else {
					a("class", "valueUserId w3-input w3-border classSiteUser inputSiteUser", pk, "UserId w3-input w3-border ");
					a("name", "userId");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setUserId', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_userId')); }, function() { addError($('#", classApiMethodMethod, "_userId')); }); ");
				}
				a("value", strUserId())
			.fg();

		} else {
				e("span").a("class", "varSiteUser", pk, "UserId ").f().sx(htmUserId()).g("span");
		}
	}

	public void htmUserId(String classApiMethodMethod) {
		SiteUser s = (SiteUser)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SiteUserUserId").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3- ").f();
							e("label").a("for", classApiMethodMethod, "_userId").a("class", "").f().sx("user ID").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputUserId(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3- ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_userId')); $('#", classApiMethodMethod, "_userId').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SiteUserForm :input[name=pk]').val() }], 'setUserId', null, function() { addGlow($('#", classApiMethodMethod, "_userId')); }, function() { addError($('#", classApiMethodMethod, "_userId')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.user.SiteUser&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:userKey">Find the entity userKey in Solr</a>
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
		this.userKey = SiteUser.staticSetUserKey(siteRequest_, o);
		this.userKeyWrap.alreadyInitialized = true;
	}
	public static Long staticSetUserKey(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected SiteUser userKeyInit() {
		if(!userKeyWrap.alreadyInitialized) {
			_userKey(userKeyWrap);
			if(userKey == null)
				setUserKey(userKeyWrap.o);
		}
		userKeyWrap.alreadyInitialized(true);
		return (SiteUser)this;
	}

	public static Long staticSolrUserKey(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrUserKey(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqUserKey(SiteRequestEnUS siteRequest_, String o) {
		return SiteUser.staticSolrStrUserKey(siteRequest_, SiteUser.staticSolrUserKey(siteRequest_, SiteUser.staticSetUserKey(siteRequest_, o)));
	}

	public Long solrUserKey() {
		return SiteUser.staticSolrUserKey(siteRequest_, userKey);
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

	public String nomAffichageUserKey() {
		return "user key";
	}

	public String htmTooltipUserKey() {
		return null;
	}

	public String htmUserKey() {
		return userKey == null ? "" : StringEscapeUtils.escapeHtml4(strUserKey());
	}

	public void inputUserKey(String classApiMethodMethod) {
		SiteUser s = (SiteUser)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("placeholder", "user key")
				.a("id", classApiMethodMethod, "_userKey");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setUserKey classSiteUser inputSiteUser", pk, "UserKey w3-input w3-border ");
					a("name", "setUserKey");
				} else {
					a("class", "valueUserKey w3-input w3-border classSiteUser inputSiteUser", pk, "UserKey w3-input w3-border ");
					a("name", "userKey");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setUserKey', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_userKey')); }, function() { addError($('#", classApiMethodMethod, "_userKey')); }); ");
				}
				a("value", strUserKey())
			.fg();

		} else {
				e("span").a("class", "varSiteUser", pk, "UserKey ").f().sx(htmUserKey()).g("span");
		}
	}

	public void htmUserKey(String classApiMethodMethod) {
		SiteUser s = (SiteUser)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SiteUserUserKey").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3- ").f();
							e("label").a("for", classApiMethodMethod, "_userKey").a("class", "").f().sx("user key").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputUserKey(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3- ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_userKey')); $('#", classApiMethodMethod, "_userKey').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SiteUserForm :input[name=pk]').val() }], 'setUserKey', null, function() { addGlow($('#", classApiMethodMethod, "_userKey')); }, function() { addError($('#", classApiMethodMethod, "_userKey')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	//////////////
	// userName //
	//////////////

	/**	 The entity userName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String userName;
	@JsonIgnore
	public Wrap<String> userNameWrap = new Wrap<String>().p(this).c(String.class).var("userName").o(userName);

	/**	<br/> The entity userName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.user.SiteUser&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:userName">Find the entity userName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _userName(Wrap<String> c);

	public String getUserName() {
		return userName;
	}
	public void setUserName(String o) {
		this.userName = SiteUser.staticSetUserName(siteRequest_, o);
		this.userNameWrap.alreadyInitialized = true;
	}
	public static String staticSetUserName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteUser userNameInit() {
		if(!userNameWrap.alreadyInitialized) {
			_userName(userNameWrap);
			if(userName == null)
				setUserName(userNameWrap.o);
		}
		userNameWrap.alreadyInitialized(true);
		return (SiteUser)this;
	}

	public static String staticSolrUserName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrUserName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqUserName(SiteRequestEnUS siteRequest_, String o) {
		return SiteUser.staticSolrStrUserName(siteRequest_, SiteUser.staticSolrUserName(siteRequest_, SiteUser.staticSetUserName(siteRequest_, o)));
	}

	public String solrUserName() {
		return SiteUser.staticSolrUserName(siteRequest_, userName);
	}

	public String strUserName() {
		return userName == null ? "" : userName;
	}

	public String sqlUserName() {
		return userName;
	}

	public String jsonUserName() {
		return userName == null ? "" : userName;
	}

	public String nomAffichageUserName() {
		return null;
	}

	public String htmTooltipUserName() {
		return null;
	}

	public String htmUserName() {
		return userName == null ? "" : StringEscapeUtils.escapeHtml4(strUserName());
	}

	public void inputUserName(String classApiMethodMethod) {
		SiteUser s = (SiteUser)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("id", classApiMethodMethod, "_userName");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setUserName classSiteUser inputSiteUser", pk, "UserName w3-input w3-border ");
					a("name", "setUserName");
				} else {
					a("class", "valueUserName w3-input w3-border classSiteUser inputSiteUser", pk, "UserName w3-input w3-border ");
					a("name", "userName");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setUserName', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_userName')); }, function() { addError($('#", classApiMethodMethod, "_userName')); }); ");
				}
				a("value", strUserName())
			.fg();

		} else {
				e("span").a("class", "varSiteUser", pk, "UserName ").f().sx(htmUserName()).g("span");
		}
	}

	public void htmUserName(String classApiMethodMethod) {
		SiteUser s = (SiteUser)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SiteUserUserName").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputUserName(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3- ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_userName')); $('#", classApiMethodMethod, "_userName').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SiteUserForm :input[name=pk]').val() }], 'setUserName', null, function() { addGlow($('#", classApiMethodMethod, "_userName')); }, function() { addError($('#", classApiMethodMethod, "_userName')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	///////////////
	// userEmail //
	///////////////

	/**	 The entity userEmail
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String userEmail;
	@JsonIgnore
	public Wrap<String> userEmailWrap = new Wrap<String>().p(this).c(String.class).var("userEmail").o(userEmail);

	/**	<br/> The entity userEmail
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.user.SiteUser&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:userEmail">Find the entity userEmail in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _userEmail(Wrap<String> c);

	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String o) {
		this.userEmail = SiteUser.staticSetUserEmail(siteRequest_, o);
		this.userEmailWrap.alreadyInitialized = true;
	}
	public static String staticSetUserEmail(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteUser userEmailInit() {
		if(!userEmailWrap.alreadyInitialized) {
			_userEmail(userEmailWrap);
			if(userEmail == null)
				setUserEmail(userEmailWrap.o);
		}
		userEmailWrap.alreadyInitialized(true);
		return (SiteUser)this;
	}

	public static String staticSolrUserEmail(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrUserEmail(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqUserEmail(SiteRequestEnUS siteRequest_, String o) {
		return SiteUser.staticSolrStrUserEmail(siteRequest_, SiteUser.staticSolrUserEmail(siteRequest_, SiteUser.staticSetUserEmail(siteRequest_, o)));
	}

	public String solrUserEmail() {
		return SiteUser.staticSolrUserEmail(siteRequest_, userEmail);
	}

	public String strUserEmail() {
		return userEmail == null ? "" : userEmail;
	}

	public String sqlUserEmail() {
		return userEmail;
	}

	public String jsonUserEmail() {
		return userEmail == null ? "" : userEmail;
	}

	public String nomAffichageUserEmail() {
		return null;
	}

	public String htmTooltipUserEmail() {
		return null;
	}

	public String htmUserEmail() {
		return userEmail == null ? "" : StringEscapeUtils.escapeHtml4(strUserEmail());
	}

	public void inputUserEmail(String classApiMethodMethod) {
		SiteUser s = (SiteUser)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("id", classApiMethodMethod, "_userEmail");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setUserEmail classSiteUser inputSiteUser", pk, "UserEmail w3-input w3-border ");
					a("name", "setUserEmail");
				} else {
					a("class", "valueUserEmail w3-input w3-border classSiteUser inputSiteUser", pk, "UserEmail w3-input w3-border ");
					a("name", "userEmail");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setUserEmail', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_userEmail')); }, function() { addError($('#", classApiMethodMethod, "_userEmail')); }); ");
				}
				a("value", strUserEmail())
			.fg();

		} else {
				e("span").a("class", "varSiteUser", pk, "UserEmail ").f().sx(htmUserEmail()).g("span");
		}
	}

	public void htmUserEmail(String classApiMethodMethod) {
		SiteUser s = (SiteUser)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SiteUserUserEmail").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputUserEmail(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3- ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_userEmail')); $('#", classApiMethodMethod, "_userEmail').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SiteUserForm :input[name=pk]').val() }], 'setUserEmail', null, function() { addGlow($('#", classApiMethodMethod, "_userEmail')); }, function() { addError($('#", classApiMethodMethod, "_userEmail')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	///////////////////
	// userFirstName //
	///////////////////

	/**	 The entity userFirstName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String userFirstName;
	@JsonIgnore
	public Wrap<String> userFirstNameWrap = new Wrap<String>().p(this).c(String.class).var("userFirstName").o(userFirstName);

	/**	<br/> The entity userFirstName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.user.SiteUser&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:userFirstName">Find the entity userFirstName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _userFirstName(Wrap<String> c);

	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String o) {
		this.userFirstName = SiteUser.staticSetUserFirstName(siteRequest_, o);
		this.userFirstNameWrap.alreadyInitialized = true;
	}
	public static String staticSetUserFirstName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteUser userFirstNameInit() {
		if(!userFirstNameWrap.alreadyInitialized) {
			_userFirstName(userFirstNameWrap);
			if(userFirstName == null)
				setUserFirstName(userFirstNameWrap.o);
		}
		userFirstNameWrap.alreadyInitialized(true);
		return (SiteUser)this;
	}

	public static String staticSolrUserFirstName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrUserFirstName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqUserFirstName(SiteRequestEnUS siteRequest_, String o) {
		return SiteUser.staticSolrStrUserFirstName(siteRequest_, SiteUser.staticSolrUserFirstName(siteRequest_, SiteUser.staticSetUserFirstName(siteRequest_, o)));
	}

	public String solrUserFirstName() {
		return SiteUser.staticSolrUserFirstName(siteRequest_, userFirstName);
	}

	public String strUserFirstName() {
		return userFirstName == null ? "" : userFirstName;
	}

	public String sqlUserFirstName() {
		return userFirstName;
	}

	public String jsonUserFirstName() {
		return userFirstName == null ? "" : userFirstName;
	}

	public String nomAffichageUserFirstName() {
		return null;
	}

	public String htmTooltipUserFirstName() {
		return null;
	}

	public String htmUserFirstName() {
		return userFirstName == null ? "" : StringEscapeUtils.escapeHtml4(strUserFirstName());
	}

	public void inputUserFirstName(String classApiMethodMethod) {
		SiteUser s = (SiteUser)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("id", classApiMethodMethod, "_userFirstName");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setUserFirstName classSiteUser inputSiteUser", pk, "UserFirstName w3-input w3-border ");
					a("name", "setUserFirstName");
				} else {
					a("class", "valueUserFirstName w3-input w3-border classSiteUser inputSiteUser", pk, "UserFirstName w3-input w3-border ");
					a("name", "userFirstName");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setUserFirstName', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_userFirstName')); }, function() { addError($('#", classApiMethodMethod, "_userFirstName')); }); ");
				}
				a("value", strUserFirstName())
			.fg();

		} else {
				e("span").a("class", "varSiteUser", pk, "UserFirstName ").f().sx(htmUserFirstName()).g("span");
		}
	}

	public void htmUserFirstName(String classApiMethodMethod) {
		SiteUser s = (SiteUser)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SiteUserUserFirstName").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputUserFirstName(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3- ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_userFirstName')); $('#", classApiMethodMethod, "_userFirstName').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SiteUserForm :input[name=pk]').val() }], 'setUserFirstName', null, function() { addGlow($('#", classApiMethodMethod, "_userFirstName')); }, function() { addError($('#", classApiMethodMethod, "_userFirstName')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	//////////////////
	// userLastName //
	//////////////////

	/**	 The entity userLastName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String userLastName;
	@JsonIgnore
	public Wrap<String> userLastNameWrap = new Wrap<String>().p(this).c(String.class).var("userLastName").o(userLastName);

	/**	<br/> The entity userLastName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.user.SiteUser&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:userLastName">Find the entity userLastName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _userLastName(Wrap<String> c);

	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String o) {
		this.userLastName = SiteUser.staticSetUserLastName(siteRequest_, o);
		this.userLastNameWrap.alreadyInitialized = true;
	}
	public static String staticSetUserLastName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteUser userLastNameInit() {
		if(!userLastNameWrap.alreadyInitialized) {
			_userLastName(userLastNameWrap);
			if(userLastName == null)
				setUserLastName(userLastNameWrap.o);
		}
		userLastNameWrap.alreadyInitialized(true);
		return (SiteUser)this;
	}

	public static String staticSolrUserLastName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrUserLastName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqUserLastName(SiteRequestEnUS siteRequest_, String o) {
		return SiteUser.staticSolrStrUserLastName(siteRequest_, SiteUser.staticSolrUserLastName(siteRequest_, SiteUser.staticSetUserLastName(siteRequest_, o)));
	}

	public String solrUserLastName() {
		return SiteUser.staticSolrUserLastName(siteRequest_, userLastName);
	}

	public String strUserLastName() {
		return userLastName == null ? "" : userLastName;
	}

	public String sqlUserLastName() {
		return userLastName;
	}

	public String jsonUserLastName() {
		return userLastName == null ? "" : userLastName;
	}

	public String nomAffichageUserLastName() {
		return null;
	}

	public String htmTooltipUserLastName() {
		return null;
	}

	public String htmUserLastName() {
		return userLastName == null ? "" : StringEscapeUtils.escapeHtml4(strUserLastName());
	}

	public void inputUserLastName(String classApiMethodMethod) {
		SiteUser s = (SiteUser)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("id", classApiMethodMethod, "_userLastName");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setUserLastName classSiteUser inputSiteUser", pk, "UserLastName w3-input w3-border ");
					a("name", "setUserLastName");
				} else {
					a("class", "valueUserLastName w3-input w3-border classSiteUser inputSiteUser", pk, "UserLastName w3-input w3-border ");
					a("name", "userLastName");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setUserLastName', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_userLastName')); }, function() { addError($('#", classApiMethodMethod, "_userLastName')); }); ");
				}
				a("value", strUserLastName())
			.fg();

		} else {
				e("span").a("class", "varSiteUser", pk, "UserLastName ").f().sx(htmUserLastName()).g("span");
		}
	}

	public void htmUserLastName(String classApiMethodMethod) {
		SiteUser s = (SiteUser)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SiteUserUserLastName").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputUserLastName(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3- ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_userLastName')); $('#", classApiMethodMethod, "_userLastName').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SiteUserForm :input[name=pk]').val() }], 'setUserLastName', null, function() { addGlow($('#", classApiMethodMethod, "_userLastName')); }, function() { addError($('#", classApiMethodMethod, "_userLastName')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	//////////////////
	// userFullName //
	//////////////////

	/**	 The entity userFullName
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String userFullName;
	@JsonIgnore
	public Wrap<String> userFullNameWrap = new Wrap<String>().p(this).c(String.class).var("userFullName").o(userFullName);

	/**	<br/> The entity userFullName
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.user.SiteUser&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:userFullName">Find the entity userFullName in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _userFullName(Wrap<String> c);

	public String getUserFullName() {
		return userFullName;
	}
	public void setUserFullName(String o) {
		this.userFullName = SiteUser.staticSetUserFullName(siteRequest_, o);
		this.userFullNameWrap.alreadyInitialized = true;
	}
	public static String staticSetUserFullName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteUser userFullNameInit() {
		if(!userFullNameWrap.alreadyInitialized) {
			_userFullName(userFullNameWrap);
			if(userFullName == null)
				setUserFullName(userFullNameWrap.o);
		}
		userFullNameWrap.alreadyInitialized(true);
		return (SiteUser)this;
	}

	public static String staticSolrUserFullName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrUserFullName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqUserFullName(SiteRequestEnUS siteRequest_, String o) {
		return SiteUser.staticSolrStrUserFullName(siteRequest_, SiteUser.staticSolrUserFullName(siteRequest_, SiteUser.staticSetUserFullName(siteRequest_, o)));
	}

	public String solrUserFullName() {
		return SiteUser.staticSolrUserFullName(siteRequest_, userFullName);
	}

	public String strUserFullName() {
		return userFullName == null ? "" : userFullName;
	}

	public String sqlUserFullName() {
		return userFullName;
	}

	public String jsonUserFullName() {
		return userFullName == null ? "" : userFullName;
	}

	public String nomAffichageUserFullName() {
		return null;
	}

	public String htmTooltipUserFullName() {
		return null;
	}

	public String htmUserFullName() {
		return userFullName == null ? "" : StringEscapeUtils.escapeHtml4(strUserFullName());
	}

	public void inputUserFullName(String classApiMethodMethod) {
		SiteUser s = (SiteUser)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			e("input")
				.a("type", "text")
				.a("id", classApiMethodMethod, "_userFullName");
				if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
					a("class", "setUserFullName classSiteUser inputSiteUser", pk, "UserFullName w3-input w3-border ");
					a("name", "setUserFullName");
				} else {
					a("class", "valueUserFullName w3-input w3-border classSiteUser inputSiteUser", pk, "UserFullName w3-input w3-border ");
					a("name", "userFullName");
				}
				if("Page".equals(classApiMethodMethod)) {
					a("onclick", "removeGlow($(this)); ");
					a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setUserFullName', $(this).val(), function() { addGlow($('#", classApiMethodMethod, "_userFullName')); }, function() { addError($('#", classApiMethodMethod, "_userFullName')); }); ");
				}
				a("value", strUserFullName())
			.fg();

		} else {
				e("span").a("class", "varSiteUser", pk, "UserFullName ").f().sx(htmUserFullName()).g("span");
		}
	}

	public void htmUserFullName(String classApiMethodMethod) {
		SiteUser s = (SiteUser)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SiteUserUserFullName").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputUserFullName(classApiMethodMethod);
							} g("div");
							if(
									CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
									|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
									) {
								if("Page".equals(classApiMethodMethod)) {
									{ e("div").a("class", "w3-cell w3-left-align w3-cell-top ").f();
										{ e("button")
											.a("tabindex", "-1")
											.a("class", "w3-btn w3-round w3-border w3-border-black w3-ripple w3-padding w3-bar-item w3- ")
										.a("onclick", "removeGlow($('#", classApiMethodMethod, "_userFullName')); $('#", classApiMethodMethod, "_userFullName').val(null); patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:' + $('#SiteUserForm :input[name=pk]').val() }], 'setUserFullName', null, function() { addGlow($('#", classApiMethodMethod, "_userFullName')); }, function() { addError($('#", classApiMethodMethod, "_userFullName')); }); ")
											.f();
											e("i").a("class", "far fa-eraser ").f().g("i");
										} g("button");
									} g("div");
								}
							}
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	/////////////////
	// seeArchived //
	/////////////////

	/**	 The entity seeArchived
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean seeArchived;
	@JsonIgnore
	public Wrap<Boolean> seeArchivedWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("seeArchived").o(seeArchived);

	/**	<br/> The entity seeArchived
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.user.SiteUser&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:seeArchived">Find the entity seeArchived in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _seeArchived(Wrap<Boolean> c);

	public Boolean getSeeArchived() {
		return seeArchived;
	}

	public void setSeeArchived(Boolean seeArchived) {
		this.seeArchived = seeArchived;
		this.seeArchivedWrap.alreadyInitialized = true;
	}
	public void setSeeArchived(String o) {
		this.seeArchived = SiteUser.staticSetSeeArchived(siteRequest_, o);
		this.seeArchivedWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSeeArchived(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SiteUser seeArchivedInit() {
		if(!seeArchivedWrap.alreadyInitialized) {
			_seeArchived(seeArchivedWrap);
			if(seeArchived == null)
				setSeeArchived(seeArchivedWrap.o);
		}
		seeArchivedWrap.alreadyInitialized(true);
		return (SiteUser)this;
	}

	public static Boolean staticSolrSeeArchived(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrSeeArchived(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSeeArchived(SiteRequestEnUS siteRequest_, String o) {
		return SiteUser.staticSolrStrSeeArchived(siteRequest_, SiteUser.staticSolrSeeArchived(siteRequest_, SiteUser.staticSetSeeArchived(siteRequest_, o)));
	}

	public Boolean solrSeeArchived() {
		return SiteUser.staticSolrSeeArchived(siteRequest_, seeArchived);
	}

	public String strSeeArchived() {
		return seeArchived == null ? "" : seeArchived.toString();
	}

	public Boolean sqlSeeArchived() {
		return seeArchived;
	}

	public String jsonSeeArchived() {
		return seeArchived == null ? "" : seeArchived.toString();
	}

	public String nomAffichageSeeArchived() {
		return "see archived";
	}

	public String htmTooltipSeeArchived() {
		return null;
	}

	public String htmSeeArchived() {
		return seeArchived == null ? "" : StringEscapeUtils.escapeHtml4(strSeeArchived());
	}

	public void inputSeeArchived(String classApiMethodMethod) {
		SiteUser s = (SiteUser)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			if("Page".equals(classApiMethodMethod)) {
				e("input")
					.a("type", "checkbox")
					.a("id", classApiMethodMethod, "_seeArchived")
					.a("value", "true");
			} else {
				e("select")
					.a("id", classApiMethodMethod, "_seeArchived");
			}
			if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
				a("class", "setSeeArchived classSiteUser inputSiteUser", pk, "SeeArchived w3-input w3-border ");
				a("name", "setSeeArchived");
			} else {
				a("class", "valueSeeArchived classSiteUser inputSiteUser", pk, "SeeArchived w3-input w3-border ");
				a("name", "seeArchived");
			}
			if("Page".equals(classApiMethodMethod)) {
				a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSeeArchived', $(this).prop('checked'), function() { addGlow($('#", classApiMethodMethod, "_seeArchived')); }, function() { addError($('#", classApiMethodMethod, "_seeArchived')); }); ");
			}
			if("Page".equals(classApiMethodMethod)) {
				if(getSeeArchived() != null && getSeeArchived())
					a("checked", "checked");
				fg();
			} else {
				f();
				e("option").a("value", "").a("selected", "selected").f().g("option");
				e("option").a("value", "true").f().sx("true").g("option");
				e("option").a("value", "false").f().sx("false").g("option");
				g("select");
			}

		} else {
				e("span").a("class", "varSiteUser", pk, "SeeArchived ").f().sx(htmSeeArchived()).g("span");
		}
	}

	public void htmSeeArchived(String classApiMethodMethod) {
		SiteUser s = (SiteUser)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SiteUserSeeArchived").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3- ").f();
							e("label").a("for", classApiMethodMethod, "_seeArchived").a("class", "").f().sx("see archived").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSeeArchived(classApiMethodMethod);
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	////////////////
	// seeDeleted //
	////////////////

	/**	 The entity seeDeleted
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean seeDeleted;
	@JsonIgnore
	public Wrap<Boolean> seeDeletedWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("seeDeleted").o(seeDeleted);

	/**	<br/> The entity seeDeleted
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.user.SiteUser&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:seeDeleted">Find the entity seeDeleted in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _seeDeleted(Wrap<Boolean> c);

	public Boolean getSeeDeleted() {
		return seeDeleted;
	}

	public void setSeeDeleted(Boolean seeDeleted) {
		this.seeDeleted = seeDeleted;
		this.seeDeletedWrap.alreadyInitialized = true;
	}
	public void setSeeDeleted(String o) {
		this.seeDeleted = SiteUser.staticSetSeeDeleted(siteRequest_, o);
		this.seeDeletedWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetSeeDeleted(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SiteUser seeDeletedInit() {
		if(!seeDeletedWrap.alreadyInitialized) {
			_seeDeleted(seeDeletedWrap);
			if(seeDeleted == null)
				setSeeDeleted(seeDeletedWrap.o);
		}
		seeDeletedWrap.alreadyInitialized(true);
		return (SiteUser)this;
	}

	public static Boolean staticSolrSeeDeleted(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrSeeDeleted(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSeeDeleted(SiteRequestEnUS siteRequest_, String o) {
		return SiteUser.staticSolrStrSeeDeleted(siteRequest_, SiteUser.staticSolrSeeDeleted(siteRequest_, SiteUser.staticSetSeeDeleted(siteRequest_, o)));
	}

	public Boolean solrSeeDeleted() {
		return SiteUser.staticSolrSeeDeleted(siteRequest_, seeDeleted);
	}

	public String strSeeDeleted() {
		return seeDeleted == null ? "" : seeDeleted.toString();
	}

	public Boolean sqlSeeDeleted() {
		return seeDeleted;
	}

	public String jsonSeeDeleted() {
		return seeDeleted == null ? "" : seeDeleted.toString();
	}

	public String nomAffichageSeeDeleted() {
		return "see deleted";
	}

	public String htmTooltipSeeDeleted() {
		return null;
	}

	public String htmSeeDeleted() {
		return seeDeleted == null ? "" : StringEscapeUtils.escapeHtml4(strSeeDeleted());
	}

	public void inputSeeDeleted(String classApiMethodMethod) {
		SiteUser s = (SiteUser)this;
		if(
				CollectionUtils.containsAny(siteRequest_.getUserResourceRoles(), ROLES)
				|| CollectionUtils.containsAny(siteRequest_.getUserRealmRoles(), ROLES)
				) {
			if("Page".equals(classApiMethodMethod)) {
				e("input")
					.a("type", "checkbox")
					.a("id", classApiMethodMethod, "_seeDeleted")
					.a("value", "true");
			} else {
				e("select")
					.a("id", classApiMethodMethod, "_seeDeleted");
			}
			if("Page".equals(classApiMethodMethod) || "PATCH".equals(classApiMethodMethod)) {
				a("class", "setSeeDeleted classSiteUser inputSiteUser", pk, "SeeDeleted w3-input w3-border ");
				a("name", "setSeeDeleted");
			} else {
				a("class", "valueSeeDeleted classSiteUser inputSiteUser", pk, "SeeDeleted w3-input w3-border ");
				a("name", "seeDeleted");
			}
			if("Page".equals(classApiMethodMethod)) {
				a("onchange", "patch", getClass().getSimpleName(), "Val([{ name: 'fq', value: 'pk:", pk, "' }], 'setSeeDeleted', $(this).prop('checked'), function() { addGlow($('#", classApiMethodMethod, "_seeDeleted')); }, function() { addError($('#", classApiMethodMethod, "_seeDeleted')); }); ");
			}
			if("Page".equals(classApiMethodMethod)) {
				if(getSeeDeleted() != null && getSeeDeleted())
					a("checked", "checked");
				fg();
			} else {
				f();
				e("option").a("value", "").a("selected", "selected").f().g("option");
				e("option").a("value", "true").f().sx("true").g("option");
				e("option").a("value", "false").f().sx("false").g("option");
				g("select");
			}

		} else {
				e("span").a("class", "varSiteUser", pk, "SeeDeleted ").f().sx(htmSeeDeleted()).g("span");
		}
	}

	public void htmSeeDeleted(String classApiMethodMethod) {
		SiteUser s = (SiteUser)this;
		{ e("div").a("class", "w3-cell w3-cell-top w3-center w3-mobile ").f();
			{ e("div").a("class", "w3-padding ").f();
				{ e("div").a("id", "suggest", classApiMethodMethod, "SiteUserSeeDeleted").f();
					{ e("div").a("class", "w3-card ").f();
						{ e("div").a("class", "w3-cell-row w3- ").f();
							e("label").a("for", classApiMethodMethod, "_seeDeleted").a("class", "").f().sx("see deleted").g("label");
						} g("div");
						{ e("div").a("class", "w3-cell-row w3-padding ").f();
							{ e("div").a("class", "w3-cell ").f();

								inputSeeDeleted(classApiMethodMethod);
							} g("div");
						} g("div");
					} g("div");
				} g("div");
			} g("div");
		} g("div");
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedSiteUser = false;

	public SiteUser initDeepSiteUser(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedSiteUser) {
			alreadyInitializedSiteUser = true;
			initDeepSiteUser();
		}
		return (SiteUser)this;
	}

	public void initDeepSiteUser() {
		initSiteUser();
		super.initDeepCluster(siteRequest_);
	}

	public void initSiteUser() {
		userKeysInit();
		userIdInit();
		userKeyInit();
		userNameInit();
		userEmailInit();
		userFirstNameInit();
		userLastNameInit();
		userFullNameInit();
		seeArchivedInit();
		seeDeletedInit();
	}

	@Override public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepSiteUser(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestSiteUser(SiteRequestEnUS siteRequest_) {
			super.siteRequestCluster(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestSiteUser(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	@Override public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainSiteUser(v);
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
	public Object obtainSiteUser(String var) {
		SiteUser oSiteUser = (SiteUser)this;
		switch(var) {
			case "userKeys":
				return oSiteUser.userKeys;
			case "userId":
				return oSiteUser.userId;
			case "userKey":
				return oSiteUser.userKey;
			case "userName":
				return oSiteUser.userName;
			case "userEmail":
				return oSiteUser.userEmail;
			case "userFirstName":
				return oSiteUser.userFirstName;
			case "userLastName":
				return oSiteUser.userLastName;
			case "userFullName":
				return oSiteUser.userFullName;
			case "seeArchived":
				return oSiteUser.seeArchived;
			case "seeDeleted":
				return oSiteUser.seeDeleted;
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
				o = attributeSiteUser(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeSiteUser(String var, Object val) {
		SiteUser oSiteUser = (SiteUser)this;
		switch(var) {
			default:
				return super.attributeCluster(var, val);
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetSiteUser(entityVar,  siteRequest_, o);
	}
	public static Object staticSetSiteUser(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "userKeys":
			return SiteUser.staticSetUserKeys(siteRequest_, o);
		case "userId":
			return SiteUser.staticSetUserId(siteRequest_, o);
		case "userKey":
			return SiteUser.staticSetUserKey(siteRequest_, o);
		case "userName":
			return SiteUser.staticSetUserName(siteRequest_, o);
		case "userEmail":
			return SiteUser.staticSetUserEmail(siteRequest_, o);
		case "userFirstName":
			return SiteUser.staticSetUserFirstName(siteRequest_, o);
		case "userLastName":
			return SiteUser.staticSetUserLastName(siteRequest_, o);
		case "userFullName":
			return SiteUser.staticSetUserFullName(siteRequest_, o);
		case "seeArchived":
			return SiteUser.staticSetSeeArchived(siteRequest_, o);
		case "seeDeleted":
			return SiteUser.staticSetSeeDeleted(siteRequest_, o);
			default:
				return Cluster.staticSetCluster(entityVar,  siteRequest_, o);
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrSiteUser(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrSiteUser(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "userKeys":
			return SiteUser.staticSolrUserKeys(siteRequest_, (Long)o);
		case "userId":
			return SiteUser.staticSolrUserId(siteRequest_, (String)o);
		case "userKey":
			return SiteUser.staticSolrUserKey(siteRequest_, (Long)o);
		case "userName":
			return SiteUser.staticSolrUserName(siteRequest_, (String)o);
		case "userEmail":
			return SiteUser.staticSolrUserEmail(siteRequest_, (String)o);
		case "userFirstName":
			return SiteUser.staticSolrUserFirstName(siteRequest_, (String)o);
		case "userLastName":
			return SiteUser.staticSolrUserLastName(siteRequest_, (String)o);
		case "userFullName":
			return SiteUser.staticSolrUserFullName(siteRequest_, (String)o);
		case "seeArchived":
			return SiteUser.staticSolrSeeArchived(siteRequest_, (Boolean)o);
		case "seeDeleted":
			return SiteUser.staticSolrSeeDeleted(siteRequest_, (Boolean)o);
			default:
				return Cluster.staticSolrCluster(entityVar,  siteRequest_, o);
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrSiteUser(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrSiteUser(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "userKeys":
			return SiteUser.staticSolrStrUserKeys(siteRequest_, (Long)o);
		case "userId":
			return SiteUser.staticSolrStrUserId(siteRequest_, (String)o);
		case "userKey":
			return SiteUser.staticSolrStrUserKey(siteRequest_, (Long)o);
		case "userName":
			return SiteUser.staticSolrStrUserName(siteRequest_, (String)o);
		case "userEmail":
			return SiteUser.staticSolrStrUserEmail(siteRequest_, (String)o);
		case "userFirstName":
			return SiteUser.staticSolrStrUserFirstName(siteRequest_, (String)o);
		case "userLastName":
			return SiteUser.staticSolrStrUserLastName(siteRequest_, (String)o);
		case "userFullName":
			return SiteUser.staticSolrStrUserFullName(siteRequest_, (String)o);
		case "seeArchived":
			return SiteUser.staticSolrStrSeeArchived(siteRequest_, (Boolean)o);
		case "seeDeleted":
			return SiteUser.staticSolrStrSeeDeleted(siteRequest_, (Boolean)o);
			default:
				return Cluster.staticSolrStrCluster(entityVar,  siteRequest_, o);
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqSiteUser(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqSiteUser(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "userKeys":
			return SiteUser.staticSolrFqUserKeys(siteRequest_, o);
		case "userId":
			return SiteUser.staticSolrFqUserId(siteRequest_, o);
		case "userKey":
			return SiteUser.staticSolrFqUserKey(siteRequest_, o);
		case "userName":
			return SiteUser.staticSolrFqUserName(siteRequest_, o);
		case "userEmail":
			return SiteUser.staticSolrFqUserEmail(siteRequest_, o);
		case "userFirstName":
			return SiteUser.staticSolrFqUserFirstName(siteRequest_, o);
		case "userLastName":
			return SiteUser.staticSolrFqUserLastName(siteRequest_, o);
		case "userFullName":
			return SiteUser.staticSolrFqUserFullName(siteRequest_, o);
		case "seeArchived":
			return SiteUser.staticSolrFqSeeArchived(siteRequest_, o);
		case "seeDeleted":
			return SiteUser.staticSolrFqSeeDeleted(siteRequest_, o);
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
					o = defineSiteUser(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSiteUser(String var, String val) {
		switch(var.toLowerCase()) {
			case "userid":
				if(val != null)
					setUserId(val);
				saves.add("userId");
				return val;
			case "userkey":
				if(val != null)
					setUserKey(val);
				saves.add("userKey");
				return val;
			case "username":
				if(val != null)
					setUserName(val);
				saves.add("userName");
				return val;
			case "useremail":
				if(val != null)
					setUserEmail(val);
				saves.add("userEmail");
				return val;
			case "userfirstname":
				if(val != null)
					setUserFirstName(val);
				saves.add("userFirstName");
				return val;
			case "userlastname":
				if(val != null)
					setUserLastName(val);
				saves.add("userLastName");
				return val;
			case "userfullname":
				if(val != null)
					setUserFullName(val);
				saves.add("userFullName");
				return val;
			case "seearchived":
				if(val != null)
					setSeeArchived(val);
				saves.add("seeArchived");
				return val;
			case "seedeleted":
				if(val != null)
					setSeeDeleted(val);
				saves.add("seeDeleted");
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
					o = defineSiteUser(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSiteUser(String var, Object val) {
		switch(var.toLowerCase()) {
			case "userid":
				if(val instanceof String)
					setUserId((String)val);
				saves.add("userId");
				return val;
			case "userkey":
				if(val instanceof Long)
					setUserKey((Long)val);
				saves.add("userKey");
				return val;
			case "username":
				if(val instanceof String)
					setUserName((String)val);
				saves.add("userName");
				return val;
			case "useremail":
				if(val instanceof String)
					setUserEmail((String)val);
				saves.add("userEmail");
				return val;
			case "userfirstname":
				if(val instanceof String)
					setUserFirstName((String)val);
				saves.add("userFirstName");
				return val;
			case "userlastname":
				if(val instanceof String)
					setUserLastName((String)val);
				saves.add("userLastName");
				return val;
			case "userfullname":
				if(val instanceof String)
					setUserFullName((String)val);
				saves.add("userFullName");
				return val;
			case "seearchived":
				if(val instanceof Boolean)
					setSeeArchived((Boolean)val);
				saves.add("seeArchived");
				return val;
			case "seedeleted":
				if(val instanceof Boolean)
					setSeeDeleted((Boolean)val);
				saves.add("seeDeleted");
				return val;
			default:
				return super.defineCluster(var, val);
		}
	}

	/////////////
	// populate //
	/////////////

	@Override public void populateForClass(SolrDocument solrDocument) {
		populateSiteUser(solrDocument);
	}
	public void populateSiteUser(SolrDocument solrDocument) {
		SiteUser oSiteUser = (SiteUser)this;
		saves = (List<String>)solrDocument.get("saves_stored_strings");
		if(saves != null) {

			if(saves.contains("userKeys")) {
				List<Long> userKeys = (List<Long>)solrDocument.get("userKeys_stored_longs");
				if(userKeys != null)
					oSiteUser.userKeys.addAll(userKeys);
			}

			if(saves.contains("userId")) {
				String userId = (String)solrDocument.get("userId_stored_string");
				if(userId != null)
					oSiteUser.setUserId(userId);
			}

			if(saves.contains("userKey")) {
				Long userKey = (Long)solrDocument.get("userKey_stored_long");
				if(userKey != null)
					oSiteUser.setUserKey(userKey);
			}

			if(saves.contains("userName")) {
				String userName = (String)solrDocument.get("userName_stored_string");
				if(userName != null)
					oSiteUser.setUserName(userName);
			}

			if(saves.contains("userEmail")) {
				String userEmail = (String)solrDocument.get("userEmail_stored_string");
				if(userEmail != null)
					oSiteUser.setUserEmail(userEmail);
			}

			if(saves.contains("userFirstName")) {
				String userFirstName = (String)solrDocument.get("userFirstName_stored_string");
				if(userFirstName != null)
					oSiteUser.setUserFirstName(userFirstName);
			}

			if(saves.contains("userLastName")) {
				String userLastName = (String)solrDocument.get("userLastName_stored_string");
				if(userLastName != null)
					oSiteUser.setUserLastName(userLastName);
			}

			if(saves.contains("userFullName")) {
				String userFullName = (String)solrDocument.get("userFullName_stored_string");
				if(userFullName != null)
					oSiteUser.setUserFullName(userFullName);
			}

			if(saves.contains("seeArchived")) {
				Boolean seeArchived = (Boolean)solrDocument.get("seeArchived_stored_boolean");
				if(seeArchived != null)
					oSiteUser.setSeeArchived(seeArchived);
			}

			if(saves.contains("seeDeleted")) {
				Boolean seeDeleted = (Boolean)solrDocument.get("seeDeleted_stored_boolean");
				if(seeDeleted != null)
					oSiteUser.setSeeDeleted(seeDeleted);
			}
		}

		super.populateCluster(solrDocument);
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
			solrQuery.addFilterQuery("id:" + ClientUtils.escapeQueryChars("com.opendatapolicing.enus.user.SiteUser"));
			QueryResponse queryResponse = siteRequest.getSiteContext_().getSolrClient().query(solrQuery);
			if(queryResponse.getResults().size() > 0)
				siteRequest.setSolrDocument(queryResponse.getResults().get(0));
			SiteUser o = new SiteUser();
			o.siteRequestSiteUser(siteRequest);
			o.initDeepSiteUser(siteRequest);
			o.indexSiteUser();
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}


	@Override public void indexForClass() {
		indexSiteUser();
	}

	@Override public void indexForClass(SolrInputDocument document) {
		indexSiteUser(document);
	}

	public void indexSiteUser(SolrClient clientSolr) {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexSiteUser(document);
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexSiteUser() {
		try {
			SolrInputDocument document = new SolrInputDocument();
			indexSiteUser(document);
			SolrClient clientSolr = siteRequest_.getSiteContext_().getSolrClient();
			clientSolr.add(document);
			clientSolr.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public void indexSiteUser(SolrInputDocument document) {
		if(userKeys != null) {
			for(java.lang.Long o : userKeys) {
				document.addField("userKeys_indexed_longs", o);
			}
			for(java.lang.Long o : userKeys) {
				document.addField("userKeys_stored_longs", o);
			}
		}
		if(userId != null) {
			document.addField("userId_indexed_string", userId);
			document.addField("userId_stored_string", userId);
		}
		if(userKey != null) {
			document.addField("userKey_indexed_long", userKey);
			document.addField("userKey_stored_long", userKey);
		}
		if(userName != null) {
			document.addField("userName_indexed_string", userName);
			document.addField("userName_stored_string", userName);
		}
		if(userEmail != null) {
			document.addField("userEmail_indexed_string", userEmail);
			document.addField("userEmail_stored_string", userEmail);
		}
		if(userFirstName != null) {
			document.addField("userFirstName_indexed_string", userFirstName);
			document.addField("userFirstName_stored_string", userFirstName);
		}
		if(userLastName != null) {
			document.addField("userLastName_indexed_string", userLastName);
			document.addField("userLastName_stored_string", userLastName);
		}
		if(userFullName != null) {
			document.addField("userFullName_indexed_string", userFullName);
			document.addField("userFullName_stored_string", userFullName);
		}
		if(seeArchived != null) {
			document.addField("seeArchived_indexed_boolean", seeArchived);
			document.addField("seeArchived_stored_boolean", seeArchived);
		}
		if(seeDeleted != null) {
			document.addField("seeDeleted_indexed_boolean", seeDeleted);
			document.addField("seeDeleted_stored_boolean", seeDeleted);
		}
		super.indexCluster(document);

	}

	public void unindexSiteUser() {
		try {
		SiteRequestEnUS siteRequest = new SiteRequestEnUS();
			siteRequest.initDeepSiteRequestEnUS();
			SiteContextEnUS siteContext = new SiteContextEnUS();
			siteContext.initDeepSiteContextEnUS();
			siteRequest.setSiteContext_(siteContext);
			siteRequest.setSiteConfig_(siteContext.getSiteConfig());
			initDeepSiteUser(siteRequest);
			SolrClient solrClient = siteContext.getSolrClient();
			solrClient.deleteById(id.toString());
			solrClient.commit(false, false, true);
		} catch(Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}

	public static String varIndexedSiteUser(String entityVar) {
		switch(entityVar) {
			case "userKeys":
				return "userKeys_indexed_longs";
			case "userId":
				return "userId_indexed_string";
			case "userKey":
				return "userKey_indexed_long";
			case "userName":
				return "userName_indexed_string";
			case "userEmail":
				return "userEmail_indexed_string";
			case "userFirstName":
				return "userFirstName_indexed_string";
			case "userLastName":
				return "userLastName_indexed_string";
			case "userFullName":
				return "userFullName_indexed_string";
			case "seeArchived":
				return "seeArchived_indexed_boolean";
			case "seeDeleted":
				return "seeDeleted_indexed_boolean";
			default:
				return Cluster.varIndexedCluster(entityVar);
		}
	}

	public static String varSearchSiteUser(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSearchCluster(entityVar);
		}
	}

	public static String varSuggestedSiteUser(String entityVar) {
		switch(entityVar) {
			default:
				return Cluster.varSuggestedCluster(entityVar);
		}
	}

	/////////////
	// store //
	/////////////

	@Override public void storeForClass(SolrDocument solrDocument) {
		storeSiteUser(solrDocument);
	}
	public void storeSiteUser(SolrDocument solrDocument) {
		SiteUser oSiteUser = (SiteUser)this;

		List<Long> userKeys = (List<Long>)solrDocument.get("userKeys_stored_longs");
		if(userKeys != null)
			oSiteUser.userKeys.addAll(userKeys);

		String userId = (String)solrDocument.get("userId_stored_string");
		if(userId != null)
			oSiteUser.setUserId(userId);

		Long userKey = (Long)solrDocument.get("userKey_stored_long");
		if(userKey != null)
			oSiteUser.setUserKey(userKey);

		String userName = (String)solrDocument.get("userName_stored_string");
		if(userName != null)
			oSiteUser.setUserName(userName);

		String userEmail = (String)solrDocument.get("userEmail_stored_string");
		if(userEmail != null)
			oSiteUser.setUserEmail(userEmail);

		String userFirstName = (String)solrDocument.get("userFirstName_stored_string");
		if(userFirstName != null)
			oSiteUser.setUserFirstName(userFirstName);

		String userLastName = (String)solrDocument.get("userLastName_stored_string");
		if(userLastName != null)
			oSiteUser.setUserLastName(userLastName);

		String userFullName = (String)solrDocument.get("userFullName_stored_string");
		if(userFullName != null)
			oSiteUser.setUserFullName(userFullName);

		Boolean seeArchived = (Boolean)solrDocument.get("seeArchived_stored_boolean");
		if(seeArchived != null)
			oSiteUser.setSeeArchived(seeArchived);

		Boolean seeDeleted = (Boolean)solrDocument.get("seeDeleted_stored_boolean");
		if(seeDeleted != null)
			oSiteUser.setSeeDeleted(seeDeleted);

		super.storeCluster(solrDocument);
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestSiteUser() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof SiteUser) {
			SiteUser original = (SiteUser)o;
			if(!Objects.equals(userKeys, original.getUserKeys()))
				apiRequest.addVars("userKeys");
			if(!Objects.equals(userId, original.getUserId()))
				apiRequest.addVars("userId");
			if(!Objects.equals(userKey, original.getUserKey()))
				apiRequest.addVars("userKey");
			if(!Objects.equals(userName, original.getUserName()))
				apiRequest.addVars("userName");
			if(!Objects.equals(userEmail, original.getUserEmail()))
				apiRequest.addVars("userEmail");
			if(!Objects.equals(userFirstName, original.getUserFirstName()))
				apiRequest.addVars("userFirstName");
			if(!Objects.equals(userLastName, original.getUserLastName()))
				apiRequest.addVars("userLastName");
			if(!Objects.equals(userFullName, original.getUserFullName()))
				apiRequest.addVars("userFullName");
			if(!Objects.equals(seeArchived, original.getSeeArchived()))
				apiRequest.addVars("seeArchived");
			if(!Objects.equals(seeDeleted, original.getSeeDeleted()))
				apiRequest.addVars("seeDeleted");
			super.apiRequestCluster();
		}
	}

	//////////////
	// hashCode //
	//////////////

	@Override public int hashCode() {
		return Objects.hash(super.hashCode(), userKeys, userId, userKey, userName, userEmail, userFirstName, userLastName, userFullName, seeArchived, seeDeleted);
	}

	////////////
	// equals //
	////////////

	@Override public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof SiteUser))
			return false;
		SiteUser that = (SiteUser)o;
		return super.equals(o)
				&& Objects.equals( userKeys, that.userKeys )
				&& Objects.equals( userId, that.userId )
				&& Objects.equals( userKey, that.userKey )
				&& Objects.equals( userName, that.userName )
				&& Objects.equals( userEmail, that.userEmail )
				&& Objects.equals( userFirstName, that.userFirstName )
				&& Objects.equals( userLastName, that.userLastName )
				&& Objects.equals( userFullName, that.userFullName )
				&& Objects.equals( seeArchived, that.seeArchived )
				&& Objects.equals( seeDeleted, that.seeDeleted );
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("SiteUser { ");
		sb.append( "userKeys: " ).append(userKeys);
		sb.append( ", userId: \"" ).append(userId).append( "\"" );
		sb.append( ", userKey: " ).append(userKey);
		sb.append( ", userName: \"" ).append(userName).append( "\"" );
		sb.append( ", userEmail: \"" ).append(userEmail).append( "\"" );
		sb.append( ", userFirstName: \"" ).append(userFirstName).append( "\"" );
		sb.append( ", userLastName: \"" ).append(userLastName).append( "\"" );
		sb.append( ", userFullName: \"" ).append(userFullName).append( "\"" );
		sb.append( ", seeArchived: " ).append(seeArchived);
		sb.append( ", seeDeleted: " ).append(seeDeleted);
		sb.append(" }");
		return sb.toString();
	}
}
