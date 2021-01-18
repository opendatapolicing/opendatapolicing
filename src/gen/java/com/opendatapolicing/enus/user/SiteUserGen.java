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
	public static final String SiteUser_Color = "IconGroup: regular";
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

	//////////////
	// userSite //
	//////////////

	/**	 The entity userSite
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String userSite;
	@JsonIgnore
	public Wrap<String> userSiteWrap = new Wrap<String>().p(this).c(String.class).var("userSite").o(userSite);

	/**	<br/> The entity userSite
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.user.SiteUser&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:userSite">Find the entity userSite in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _userSite(Wrap<String> c);

	public String getUserSite() {
		return userSite;
	}
	public void setUserSite(String o) {
		this.userSite = SiteUser.staticSetUserSite(siteRequest_, o);
		this.userSiteWrap.alreadyInitialized = true;
	}
	public static String staticSetUserSite(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteUser userSiteInit() {
		if(!userSiteWrap.alreadyInitialized) {
			_userSite(userSiteWrap);
			if(userSite == null)
				setUserSite(userSiteWrap.o);
		}
		userSiteWrap.alreadyInitialized(true);
		return (SiteUser)this;
	}

	public static String staticSolrUserSite(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrUserSite(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqUserSite(SiteRequestEnUS siteRequest_, String o) {
		return SiteUser.staticSolrStrUserSite(siteRequest_, SiteUser.staticSolrUserSite(siteRequest_, SiteUser.staticSetUserSite(siteRequest_, o)));
	}

	public String solrUserSite() {
		return SiteUser.staticSolrUserSite(siteRequest_, userSite);
	}

	public String strUserSite() {
		return userSite == null ? "" : userSite;
	}

	public String jsonUserSite() {
		return userSite == null ? "" : userSite;
	}

	public String nomAffichageUserSite() {
		return null;
	}

	public String htmTooltipUserSite() {
		return null;
	}

	public String htmUserSite() {
		return userSite == null ? "" : StringEscapeUtils.escapeHtml4(strUserSite());
	}

	///////////////////////
	// customerProfileId //
	///////////////////////

	/**	 The entity customerProfileId
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected String customerProfileId;
	@JsonIgnore
	public Wrap<String> customerProfileIdWrap = new Wrap<String>().p(this).c(String.class).var("customerProfileId").o(customerProfileId);

	/**	<br/> The entity customerProfileId
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.user.SiteUser&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:customerProfileId">Find the entity customerProfileId in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _customerProfileId(Wrap<String> c);

	public String getCustomerProfileId() {
		return customerProfileId;
	}
	public void setCustomerProfileId(String o) {
		this.customerProfileId = SiteUser.staticSetCustomerProfileId(siteRequest_, o);
		this.customerProfileIdWrap.alreadyInitialized = true;
	}
	public static String staticSetCustomerProfileId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteUser customerProfileIdInit() {
		if(!customerProfileIdWrap.alreadyInitialized) {
			_customerProfileId(customerProfileIdWrap);
			if(customerProfileId == null)
				setCustomerProfileId(customerProfileIdWrap.o);
		}
		customerProfileIdWrap.alreadyInitialized(true);
		return (SiteUser)this;
	}

	public static String staticSolrCustomerProfileId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrCustomerProfileId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqCustomerProfileId(SiteRequestEnUS siteRequest_, String o) {
		return SiteUser.staticSolrStrCustomerProfileId(siteRequest_, SiteUser.staticSolrCustomerProfileId(siteRequest_, SiteUser.staticSetCustomerProfileId(siteRequest_, o)));
	}

	public String solrCustomerProfileId() {
		return SiteUser.staticSolrCustomerProfileId(siteRequest_, customerProfileId);
	}

	public String strCustomerProfileId() {
		return customerProfileId == null ? "" : customerProfileId;
	}

	public String jsonCustomerProfileId() {
		return customerProfileId == null ? "" : customerProfileId;
	}

	public String nomAffichageCustomerProfileId() {
		return null;
	}

	public String htmTooltipCustomerProfileId() {
		return null;
	}

	public String htmCustomerProfileId() {
		return customerProfileId == null ? "" : StringEscapeUtils.escapeHtml4(strCustomerProfileId());
	}

	///////////////////////
	// userReceiveEmails //
	///////////////////////

	/**	 The entity userReceiveEmails
	 *	 is defined as null before being initialized. 
	 */
	@JsonInclude(Include.NON_NULL)
	protected Boolean userReceiveEmails;
	@JsonIgnore
	public Wrap<Boolean> userReceiveEmailsWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("userReceiveEmails").o(userReceiveEmails);

	/**	<br/> The entity userReceiveEmails
	 *  is defined as null before being initialized. 
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.user.SiteUser&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:userReceiveEmails">Find the entity userReceiveEmails in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _userReceiveEmails(Wrap<Boolean> c);

	public Boolean getUserReceiveEmails() {
		return userReceiveEmails;
	}

	public void setUserReceiveEmails(Boolean userReceiveEmails) {
		this.userReceiveEmails = userReceiveEmails;
		this.userReceiveEmailsWrap.alreadyInitialized = true;
	}
	public void setUserReceiveEmails(String o) {
		this.userReceiveEmails = SiteUser.staticSetUserReceiveEmails(siteRequest_, o);
		this.userReceiveEmailsWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetUserReceiveEmails(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SiteUser userReceiveEmailsInit() {
		if(!userReceiveEmailsWrap.alreadyInitialized) {
			_userReceiveEmails(userReceiveEmailsWrap);
			if(userReceiveEmails == null)
				setUserReceiveEmails(userReceiveEmailsWrap.o);
		}
		userReceiveEmailsWrap.alreadyInitialized(true);
		return (SiteUser)this;
	}

	public static Boolean staticSolrUserReceiveEmails(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrUserReceiveEmails(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqUserReceiveEmails(SiteRequestEnUS siteRequest_, String o) {
		return SiteUser.staticSolrStrUserReceiveEmails(siteRequest_, SiteUser.staticSolrUserReceiveEmails(siteRequest_, SiteUser.staticSetUserReceiveEmails(siteRequest_, o)));
	}

	public Boolean solrUserReceiveEmails() {
		return SiteUser.staticSolrUserReceiveEmails(siteRequest_, userReceiveEmails);
	}

	public String strUserReceiveEmails() {
		return userReceiveEmails == null ? "" : userReceiveEmails.toString();
	}

	public String jsonUserReceiveEmails() {
		return userReceiveEmails == null ? "" : userReceiveEmails.toString();
	}

	public String nomAffichageUserReceiveEmails() {
		return null;
	}

	public String htmTooltipUserReceiveEmails() {
		return null;
	}

	public String htmUserReceiveEmails() {
		return userReceiveEmails == null ? "" : StringEscapeUtils.escapeHtml4(strUserReceiveEmails());
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

	public String jsonSeeArchived() {
		return seeArchived == null ? "" : seeArchived.toString();
	}

	public String nomAffichageSeeArchived() {
		return null;
	}

	public String htmTooltipSeeArchived() {
		return null;
	}

	public String htmSeeArchived() {
		return seeArchived == null ? "" : StringEscapeUtils.escapeHtml4(strSeeArchived());
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

	public String jsonSeeDeleted() {
		return seeDeleted == null ? "" : seeDeleted.toString();
	}

	public String nomAffichageSeeDeleted() {
		return null;
	}

	public String htmTooltipSeeDeleted() {
		return null;
	}

	public String htmSeeDeleted() {
		return seeDeleted == null ? "" : StringEscapeUtils.escapeHtml4(strSeeDeleted());
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
	 * <br/><a href="http://localhost:8983/solr/computate/select?q=*:*&fq=partEstEntite_indexed_boolean:true&fq=classeNomCanonique_enUS_indexed_string:com.opendatapolicing.enus.user.SiteUser&fq=classeEtendGen_indexed_boolean:true&fq=entiteVar_enUS_indexed_string:objectTitle">Find the entity objectTitle in Solr</a>
	 * <br/>
	 * @param c is for wrapping a value to assign to this entity during initialization. 
	 **/
	protected abstract void _objectTitle(Wrap<String> c);

	public String getObjectTitle() {
		return objectTitle;
	}
	public void setObjectTitle(String o) {
		this.objectTitle = SiteUser.staticSetObjectTitle(siteRequest_, o);
		this.objectTitleWrap.alreadyInitialized = true;
	}
	public static String staticSetObjectTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteUser objectTitleInit() {
		if(!objectTitleWrap.alreadyInitialized) {
			_objectTitle(objectTitleWrap);
			if(objectTitle == null)
				setObjectTitle(objectTitleWrap.o);
		}
		objectTitleWrap.alreadyInitialized(true);
		return (SiteUser)this;
	}

	public static String staticSolrObjectTitle(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrObjectTitle(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqObjectTitle(SiteRequestEnUS siteRequest_, String o) {
		return SiteUser.staticSolrStrObjectTitle(siteRequest_, SiteUser.staticSolrObjectTitle(siteRequest_, SiteUser.staticSetObjectTitle(siteRequest_, o)));
	}

	public String solrObjectTitle() {
		return SiteUser.staticSolrObjectTitle(siteRequest_, objectTitle);
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
		userSiteInit();
		customerProfileIdInit();
		userReceiveEmailsInit();
		seeArchivedInit();
		seeDeletedInit();
		objectTitleInit();
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
			case "userSite":
				return oSiteUser.userSite;
			case "customerProfileId":
				return oSiteUser.customerProfileId;
			case "userReceiveEmails":
				return oSiteUser.userReceiveEmails;
			case "seeArchived":
				return oSiteUser.seeArchived;
			case "seeDeleted":
				return oSiteUser.seeDeleted;
			case "objectTitle":
				return oSiteUser.objectTitle;
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
		case "userSite":
			return SiteUser.staticSetUserSite(siteRequest_, o);
		case "customerProfileId":
			return SiteUser.staticSetCustomerProfileId(siteRequest_, o);
		case "userReceiveEmails":
			return SiteUser.staticSetUserReceiveEmails(siteRequest_, o);
		case "seeArchived":
			return SiteUser.staticSetSeeArchived(siteRequest_, o);
		case "seeDeleted":
			return SiteUser.staticSetSeeDeleted(siteRequest_, o);
		case "objectTitle":
			return SiteUser.staticSetObjectTitle(siteRequest_, o);
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
		case "userSite":
			return SiteUser.staticSolrUserSite(siteRequest_, (String)o);
		case "customerProfileId":
			return SiteUser.staticSolrCustomerProfileId(siteRequest_, (String)o);
		case "userReceiveEmails":
			return SiteUser.staticSolrUserReceiveEmails(siteRequest_, (Boolean)o);
		case "seeArchived":
			return SiteUser.staticSolrSeeArchived(siteRequest_, (Boolean)o);
		case "seeDeleted":
			return SiteUser.staticSolrSeeDeleted(siteRequest_, (Boolean)o);
		case "objectTitle":
			return SiteUser.staticSolrObjectTitle(siteRequest_, (String)o);
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
		case "userSite":
			return SiteUser.staticSolrStrUserSite(siteRequest_, (String)o);
		case "customerProfileId":
			return SiteUser.staticSolrStrCustomerProfileId(siteRequest_, (String)o);
		case "userReceiveEmails":
			return SiteUser.staticSolrStrUserReceiveEmails(siteRequest_, (Boolean)o);
		case "seeArchived":
			return SiteUser.staticSolrStrSeeArchived(siteRequest_, (Boolean)o);
		case "seeDeleted":
			return SiteUser.staticSolrStrSeeDeleted(siteRequest_, (Boolean)o);
		case "objectTitle":
			return SiteUser.staticSolrStrObjectTitle(siteRequest_, (String)o);
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
		case "userSite":
			return SiteUser.staticSolrFqUserSite(siteRequest_, o);
		case "customerProfileId":
			return SiteUser.staticSolrFqCustomerProfileId(siteRequest_, o);
		case "userReceiveEmails":
			return SiteUser.staticSolrFqUserReceiveEmails(siteRequest_, o);
		case "seeArchived":
			return SiteUser.staticSolrFqSeeArchived(siteRequest_, o);
		case "seeDeleted":
			return SiteUser.staticSolrFqSeeDeleted(siteRequest_, o);
		case "objectTitle":
			return SiteUser.staticSolrFqObjectTitle(siteRequest_, o);
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
		switch(var) {
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
		super.indexCluster(document);

	}

	public static String varIndexedSiteUser(String entityVar) {
		switch(entityVar) {
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
			super.apiRequestCluster();
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
		if(!(o instanceof SiteUser))
			return false;
		SiteUser that = (SiteUser)o;
		return super.equals(o);
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString() + "\n");
		sb.append("SiteUser { ");
		sb.append(" }");
		return sb.toString();
	}
}
