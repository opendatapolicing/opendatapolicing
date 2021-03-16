package com.opendatapolicing.enus.request;

import java.util.Arrays;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.LoggerFactory;
import io.vertx.core.MultiMap;
import org.apache.commons.lang3.StringUtils;
import java.lang.Long;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import java.util.Map;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.Transaction;
import java.math.RoundingMode;
import com.opendatapolicing.enus.wrap.Wrap;
import java.math.MathContext;
import com.opendatapolicing.enus.writer.AllWriter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.opendatapolicing.enus.request.api.ApiRequest;
import com.opendatapolicing.enus.context.SiteContextEnUS;
import io.vertx.ext.web.api.service.ServiceRequest;
import java.util.Objects;
import java.util.List;
import io.vertx.ext.auth.User;
import org.apache.solr.client.solrj.SolrQuery;
import java.util.Optional;
import com.opendatapolicing.enus.cluster.Cluster;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.util.HashMap;
import java.text.NumberFormat;
import java.util.Stack;
import java.util.ArrayList;
import io.vertx.sqlclient.SqlConnection;
import org.apache.commons.collections.CollectionUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.opendatapolicing.enus.user.SiteUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.lang.Boolean;
import java.lang.String;
import org.slf4j.Logger;
import org.apache.solr.client.solrj.response.QueryResponse;
import io.vertx.core.Vertx;
import org.apache.commons.text.StringEscapeUtils;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.vertx.core.json.JsonArray;
import org.apache.solr.common.SolrDocument;
import org.apache.commons.lang3.math.NumberUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.lang.Object;
import com.opendatapolicing.enus.config.SiteConfig;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

public abstract class SiteRequestEnUSGen<DEV> extends Object {
	protected static final Logger LOG = LoggerFactory.getLogger(SiteRequestEnUS.class);

	//////////////////
	// siteContext_ //
	//////////////////

	@JsonInclude(Include.NON_NULL)
	protected SiteContextEnUS siteContext_;
	@JsonIgnore
	public Wrap<SiteContextEnUS> siteContext_Wrap = new Wrap<SiteContextEnUS>().p(this).c(SiteContextEnUS.class).var("siteContext_").o(siteContext_);

	protected abstract void _siteContext_(Wrap<SiteContextEnUS> c);

	public SiteContextEnUS getSiteContext_() {
		return siteContext_;
	}

	public void setSiteContext_(SiteContextEnUS siteContext_) {
		this.siteContext_ = siteContext_;
		this.siteContext_Wrap.alreadyInitialized = true;
	}
	public static SiteContextEnUS staticSetSiteContext_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteRequestEnUS siteContext_Init() {
		if(!siteContext_Wrap.alreadyInitialized) {
			_siteContext_(siteContext_Wrap);
			if(siteContext_ == null)
				setSiteContext_(siteContext_Wrap.o);
		}
		siteContext_Wrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	/////////////////
	// siteConfig_ //
	/////////////////

	@JsonInclude(Include.NON_NULL)
	protected SiteConfig siteConfig_;
	@JsonIgnore
	public Wrap<SiteConfig> siteConfig_Wrap = new Wrap<SiteConfig>().p(this).c(SiteConfig.class).var("siteConfig_").o(siteConfig_);

	protected abstract void _siteConfig_(Wrap<SiteConfig> c);

	public SiteConfig getSiteConfig_() {
		return siteConfig_;
	}

	public void setSiteConfig_(SiteConfig siteConfig_) {
		this.siteConfig_ = siteConfig_;
		this.siteConfig_Wrap.alreadyInitialized = true;
	}
	public static SiteConfig staticSetSiteConfig_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteRequestEnUS siteConfig_Init() {
		if(!siteConfig_Wrap.alreadyInitialized) {
			_siteConfig_(siteConfig_Wrap);
			if(siteConfig_ == null)
				setSiteConfig_(siteConfig_Wrap.o);
		}
		siteConfig_Wrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	//////////////////
	// siteRequest_ //
	//////////////////

	@JsonInclude(Include.NON_NULL)
	protected SiteRequestEnUS siteRequest_;
	@JsonIgnore
	public Wrap<SiteRequestEnUS> siteRequest_Wrap = new Wrap<SiteRequestEnUS>().p(this).c(SiteRequestEnUS.class).var("siteRequest_").o(siteRequest_);

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
	protected SiteRequestEnUS siteRequest_Init() {
		if(!siteRequest_Wrap.alreadyInitialized) {
			_siteRequest_(siteRequest_Wrap);
			if(siteRequest_ == null)
				setSiteRequest_(siteRequest_Wrap.o);
		}
		siteRequest_Wrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	/////////////////
	// apiRequest_ //
	/////////////////

	@JsonInclude(Include.NON_NULL)
	protected ApiRequest apiRequest_;
	@JsonIgnore
	public Wrap<ApiRequest> apiRequest_Wrap = new Wrap<ApiRequest>().p(this).c(ApiRequest.class).var("apiRequest_").o(apiRequest_);

	protected abstract void _apiRequest_(Wrap<ApiRequest> c);

	public ApiRequest getApiRequest_() {
		return apiRequest_;
	}

	public void setApiRequest_(ApiRequest apiRequest_) {
		this.apiRequest_ = apiRequest_;
		this.apiRequest_Wrap.alreadyInitialized = true;
	}
	public static ApiRequest staticSetApiRequest_(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteRequestEnUS apiRequest_Init() {
		if(!apiRequest_Wrap.alreadyInitialized) {
			_apiRequest_(apiRequest_Wrap);
			if(apiRequest_ == null)
				setApiRequest_(apiRequest_Wrap.o);
		}
		apiRequest_Wrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	///////////
	// vertx //
	///////////

	@JsonInclude(Include.NON_NULL)
	protected Vertx vertx;
	@JsonIgnore
	public Wrap<Vertx> vertxWrap = new Wrap<Vertx>().p(this).c(Vertx.class).var("vertx").o(vertx);

	protected abstract void _vertx(Wrap<Vertx> c);

	public Vertx getVertx() {
		return vertx;
	}

	public void setVertx(Vertx vertx) {
		this.vertx = vertx;
		this.vertxWrap.alreadyInitialized = true;
	}
	public static Vertx staticSetVertx(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteRequestEnUS vertxInit() {
		if(!vertxWrap.alreadyInitialized) {
			_vertx(vertxWrap);
			if(vertx == null)
				setVertx(vertxWrap.o);
		}
		vertxWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	////////////////
	// jsonObject //
	////////////////

	@JsonInclude(Include.NON_NULL)
	protected JsonObject jsonObject;
	@JsonIgnore
	public Wrap<JsonObject> jsonObjectWrap = new Wrap<JsonObject>().p(this).c(JsonObject.class).var("jsonObject").o(jsonObject);

	protected abstract void _jsonObject(Wrap<JsonObject> c);

	public JsonObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JsonObject jsonObject) {
		this.jsonObject = jsonObject;
		this.jsonObjectWrap.alreadyInitialized = true;
	}
	public static JsonObject staticSetJsonObject(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteRequestEnUS jsonObjectInit() {
		if(!jsonObjectWrap.alreadyInitialized) {
			_jsonObject(jsonObjectWrap);
			if(jsonObject == null)
				setJsonObject(jsonObjectWrap.o);
		}
		jsonObjectWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	///////////////
	// solrQuery //
	///////////////

	@JsonInclude(Include.NON_NULL)
	protected SolrQuery solrQuery;
	@JsonIgnore
	public Wrap<SolrQuery> solrQueryWrap = new Wrap<SolrQuery>().p(this).c(SolrQuery.class).var("solrQuery").o(solrQuery);

	protected abstract void _solrQuery(Wrap<SolrQuery> c);

	public SolrQuery getSolrQuery() {
		return solrQuery;
	}

	public void setSolrQuery(SolrQuery solrQuery) {
		this.solrQuery = solrQuery;
		this.solrQueryWrap.alreadyInitialized = true;
	}
	public static SolrQuery staticSetSolrQuery(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteRequestEnUS solrQueryInit() {
		if(!solrQueryWrap.alreadyInitialized) {
			_solrQuery(solrQueryWrap);
			if(solrQuery == null)
				setSolrQuery(solrQueryWrap.o);
		}
		solrQueryWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	////////////////////
	// serviceRequest //
	////////////////////

	@JsonInclude(Include.NON_NULL)
	protected ServiceRequest serviceRequest;
	@JsonIgnore
	public Wrap<ServiceRequest> serviceRequestWrap = new Wrap<ServiceRequest>().p(this).c(ServiceRequest.class).var("serviceRequest").o(serviceRequest);

	protected abstract void _serviceRequest(Wrap<ServiceRequest> c);

	public ServiceRequest getServiceRequest() {
		return serviceRequest;
	}

	public void setServiceRequest(ServiceRequest serviceRequest) {
		this.serviceRequest = serviceRequest;
		this.serviceRequestWrap.alreadyInitialized = true;
	}
	public static ServiceRequest staticSetServiceRequest(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteRequestEnUS serviceRequestInit() {
		if(!serviceRequestWrap.alreadyInitialized) {
			_serviceRequest(serviceRequestWrap);
			if(serviceRequest == null)
				setServiceRequest(serviceRequestWrap.o);
		}
		serviceRequestWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	///////////////////
	// queryResponse //
	///////////////////

	@JsonInclude(Include.NON_NULL)
	protected QueryResponse queryResponse;
	@JsonIgnore
	public Wrap<QueryResponse> queryResponseWrap = new Wrap<QueryResponse>().p(this).c(QueryResponse.class).var("queryResponse").o(queryResponse);

	protected abstract void _queryResponse(Wrap<QueryResponse> c);

	public QueryResponse getQueryResponse() {
		return queryResponse;
	}

	public void setQueryResponse(QueryResponse queryResponse) {
		this.queryResponse = queryResponse;
		this.queryResponseWrap.alreadyInitialized = true;
	}
	public static QueryResponse staticSetQueryResponse(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteRequestEnUS queryResponseInit() {
		if(!queryResponseWrap.alreadyInitialized) {
			_queryResponse(queryResponseWrap);
			if(queryResponse == null)
				setQueryResponse(queryResponseWrap.o);
		}
		queryResponseWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	///////////////////
	// searchResults //
	///////////////////

	@JsonInclude(Include.NON_NULL)
	protected SolrDocumentList searchResults;
	@JsonIgnore
	public Wrap<SolrDocumentList> searchResultsWrap = new Wrap<SolrDocumentList>().p(this).c(SolrDocumentList.class).var("searchResults").o(searchResults);

	protected abstract void _searchResults(Wrap<SolrDocumentList> c);

	public SolrDocumentList getSearchResults() {
		return searchResults;
	}

	public void setSearchResults(SolrDocumentList searchResults) {
		this.searchResults = searchResults;
		this.searchResultsWrap.alreadyInitialized = true;
	}
	public static SolrDocumentList staticSetSearchResults(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteRequestEnUS searchResultsInit() {
		if(!searchResultsWrap.alreadyInitialized) {
			_searchResults(searchResultsWrap);
			if(searchResults == null)
				setSearchResults(searchResultsWrap.o);
		}
		searchResultsWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	///////
	// w //
	///////

	@JsonInclude(Include.NON_NULL)
	protected AllWriter w;
	@JsonIgnore
	public Wrap<AllWriter> wWrap = new Wrap<AllWriter>().p(this).c(AllWriter.class).var("w").o(w);

	protected abstract void _w(Wrap<AllWriter> c);

	public AllWriter getW() {
		return w;
	}

	public void setW(AllWriter w) {
		this.w = w;
		this.wWrap.alreadyInitialized = true;
	}
	public static AllWriter staticSetW(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteRequestEnUS wInit() {
		if(!wWrap.alreadyInitialized) {
			_w(wWrap);
			if(w == null)
				setW(wWrap.o);
		}
		if(w != null)
			w.initDeepForClass(siteRequest_);
		wWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	//////////
	// user //
	//////////

	@JsonInclude(Include.NON_NULL)
	protected User user;
	@JsonIgnore
	public Wrap<User> userWrap = new Wrap<User>().p(this).c(User.class).var("user").o(user);

	protected abstract void _user(Wrap<User> c);

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		this.userWrap.alreadyInitialized = true;
	}
	public static User staticSetUser(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteRequestEnUS userInit() {
		if(!userWrap.alreadyInitialized) {
			_user(userWrap);
			if(user == null)
				setUser(userWrap.o);
		}
		userWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	///////////////////
	// jsonPrincipal //
	///////////////////

	@JsonInclude(Include.NON_NULL)
	protected JsonObject jsonPrincipal;
	@JsonIgnore
	public Wrap<JsonObject> jsonPrincipalWrap = new Wrap<JsonObject>().p(this).c(JsonObject.class).var("jsonPrincipal").o(jsonPrincipal);

	protected abstract void _jsonPrincipal(Wrap<JsonObject> c);

	public JsonObject getJsonPrincipal() {
		return jsonPrincipal;
	}

	public void setJsonPrincipal(JsonObject jsonPrincipal) {
		this.jsonPrincipal = jsonPrincipal;
		this.jsonPrincipalWrap.alreadyInitialized = true;
	}
	public static JsonObject staticSetJsonPrincipal(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteRequestEnUS jsonPrincipalInit() {
		if(!jsonPrincipalWrap.alreadyInitialized) {
			_jsonPrincipal(jsonPrincipalWrap);
			if(jsonPrincipal == null)
				setJsonPrincipal(jsonPrincipalWrap.o);
		}
		jsonPrincipalWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	////////////
	// userId //
	////////////

	@JsonInclude(Include.NON_NULL)
	protected String userId;
	@JsonIgnore
	public Wrap<String> userIdWrap = new Wrap<String>().p(this).c(String.class).var("userId").o(userId);

	protected abstract void _userId(Wrap<String> c);

	public String getUserId() {
		return userId;
	}
	public void setUserId(String o) {
		this.userId = SiteRequestEnUS.staticSetUserId(siteRequest_, o);
		this.userIdWrap.alreadyInitialized = true;
	}
	public static String staticSetUserId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteRequestEnUS userIdInit() {
		if(!userIdWrap.alreadyInitialized) {
			_userId(userIdWrap);
			if(userId == null)
				setUserId(userIdWrap.o);
		}
		userIdWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	public static String staticSolrUserId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrUserId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqUserId(SiteRequestEnUS siteRequest_, String o) {
		return SiteRequestEnUS.staticSolrStrUserId(siteRequest_, SiteRequestEnUS.staticSolrUserId(siteRequest_, SiteRequestEnUS.staticSetUserId(siteRequest_, o)));
	}

	public String solrUserId() {
		return SiteRequestEnUS.staticSolrUserId(siteRequest_, userId);
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

	/////////////
	// userKey //
	/////////////

	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long userKey;
	@JsonIgnore
	public Wrap<Long> userKeyWrap = new Wrap<Long>().p(this).c(Long.class).var("userKey").o(userKey);

	protected abstract void _userKey(Wrap<Long> c);

	public Long getUserKey() {
		return userKey;
	}

	public void setUserKey(Long userKey) {
		this.userKey = userKey;
		this.userKeyWrap.alreadyInitialized = true;
	}
	public void setUserKey(String o) {
		this.userKey = SiteRequestEnUS.staticSetUserKey(siteRequest_, o);
		this.userKeyWrap.alreadyInitialized = true;
	}
	public static Long staticSetUserKey(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected SiteRequestEnUS userKeyInit() {
		if(!userKeyWrap.alreadyInitialized) {
			_userKey(userKeyWrap);
			if(userKey == null)
				setUserKey(userKeyWrap.o);
		}
		userKeyWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	public static Long staticSolrUserKey(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrUserKey(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqUserKey(SiteRequestEnUS siteRequest_, String o) {
		return SiteRequestEnUS.staticSolrStrUserKey(siteRequest_, SiteRequestEnUS.staticSolrUserKey(siteRequest_, SiteRequestEnUS.staticSetUserKey(siteRequest_, o)));
	}

	public Long solrUserKey() {
		return SiteRequestEnUS.staticSolrUserKey(siteRequest_, userKey);
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

	///////////////
	// sessionId //
	///////////////

	@JsonInclude(Include.NON_NULL)
	protected String sessionId;
	@JsonIgnore
	public Wrap<String> sessionIdWrap = new Wrap<String>().p(this).c(String.class).var("sessionId").o(sessionId);

	protected abstract void _sessionId(Wrap<String> c);

	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String o) {
		this.sessionId = SiteRequestEnUS.staticSetSessionId(siteRequest_, o);
		this.sessionIdWrap.alreadyInitialized = true;
	}
	public static String staticSetSessionId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteRequestEnUS sessionIdInit() {
		if(!sessionIdWrap.alreadyInitialized) {
			_sessionId(sessionIdWrap);
			if(sessionId == null)
				setSessionId(sessionIdWrap.o);
		}
		sessionIdWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	public static String staticSolrSessionId(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrSessionId(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSessionId(SiteRequestEnUS siteRequest_, String o) {
		return SiteRequestEnUS.staticSolrStrSessionId(siteRequest_, SiteRequestEnUS.staticSolrSessionId(siteRequest_, SiteRequestEnUS.staticSetSessionId(siteRequest_, o)));
	}

	public String solrSessionId() {
		return SiteRequestEnUS.staticSolrSessionId(siteRequest_, sessionId);
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

	/////////////////////
	// sessionIdBefore //
	/////////////////////

	@JsonInclude(Include.NON_NULL)
	protected String sessionIdBefore;
	@JsonIgnore
	public Wrap<String> sessionIdBeforeWrap = new Wrap<String>().p(this).c(String.class).var("sessionIdBefore").o(sessionIdBefore);

	protected abstract void _sessionIdBefore(Wrap<String> c);

	public String getSessionIdBefore() {
		return sessionIdBefore;
	}
	public void setSessionIdBefore(String o) {
		this.sessionIdBefore = SiteRequestEnUS.staticSetSessionIdBefore(siteRequest_, o);
		this.sessionIdBeforeWrap.alreadyInitialized = true;
	}
	public static String staticSetSessionIdBefore(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteRequestEnUS sessionIdBeforeInit() {
		if(!sessionIdBeforeWrap.alreadyInitialized) {
			_sessionIdBefore(sessionIdBeforeWrap);
			if(sessionIdBefore == null)
				setSessionIdBefore(sessionIdBeforeWrap.o);
		}
		sessionIdBeforeWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	public static String staticSolrSessionIdBefore(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrSessionIdBefore(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqSessionIdBefore(SiteRequestEnUS siteRequest_, String o) {
		return SiteRequestEnUS.staticSolrStrSessionIdBefore(siteRequest_, SiteRequestEnUS.staticSolrSessionIdBefore(siteRequest_, SiteRequestEnUS.staticSetSessionIdBefore(siteRequest_, o)));
	}

	public String solrSessionIdBefore() {
		return SiteRequestEnUS.staticSolrSessionIdBefore(siteRequest_, sessionIdBefore);
	}

	public String strSessionIdBefore() {
		return sessionIdBefore == null ? "" : sessionIdBefore;
	}

	public String sqlSessionIdBefore() {
		return sessionIdBefore;
	}

	public String jsonSessionIdBefore() {
		return sessionIdBefore == null ? "" : sessionIdBefore;
	}

	//////////////
	// userName //
	//////////////

	@JsonInclude(Include.NON_NULL)
	protected String userName;
	@JsonIgnore
	public Wrap<String> userNameWrap = new Wrap<String>().p(this).c(String.class).var("userName").o(userName);

	protected abstract void _userName(Wrap<String> c);

	public String getUserName() {
		return userName;
	}
	public void setUserName(String o) {
		this.userName = SiteRequestEnUS.staticSetUserName(siteRequest_, o);
		this.userNameWrap.alreadyInitialized = true;
	}
	public static String staticSetUserName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteRequestEnUS userNameInit() {
		if(!userNameWrap.alreadyInitialized) {
			_userName(userNameWrap);
			if(userName == null)
				setUserName(userNameWrap.o);
		}
		userNameWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	public static String staticSolrUserName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrUserName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqUserName(SiteRequestEnUS siteRequest_, String o) {
		return SiteRequestEnUS.staticSolrStrUserName(siteRequest_, SiteRequestEnUS.staticSolrUserName(siteRequest_, SiteRequestEnUS.staticSetUserName(siteRequest_, o)));
	}

	public String solrUserName() {
		return SiteRequestEnUS.staticSolrUserName(siteRequest_, userName);
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

	//////////////////
	// userLastName //
	//////////////////

	@JsonInclude(Include.NON_NULL)
	protected String userLastName;
	@JsonIgnore
	public Wrap<String> userLastNameWrap = new Wrap<String>().p(this).c(String.class).var("userLastName").o(userLastName);

	protected abstract void _userLastName(Wrap<String> c);

	public String getUserLastName() {
		return userLastName;
	}
	public void setUserLastName(String o) {
		this.userLastName = SiteRequestEnUS.staticSetUserLastName(siteRequest_, o);
		this.userLastNameWrap.alreadyInitialized = true;
	}
	public static String staticSetUserLastName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteRequestEnUS userLastNameInit() {
		if(!userLastNameWrap.alreadyInitialized) {
			_userLastName(userLastNameWrap);
			if(userLastName == null)
				setUserLastName(userLastNameWrap.o);
		}
		userLastNameWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	public static String staticSolrUserLastName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrUserLastName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqUserLastName(SiteRequestEnUS siteRequest_, String o) {
		return SiteRequestEnUS.staticSolrStrUserLastName(siteRequest_, SiteRequestEnUS.staticSolrUserLastName(siteRequest_, SiteRequestEnUS.staticSetUserLastName(siteRequest_, o)));
	}

	public String solrUserLastName() {
		return SiteRequestEnUS.staticSolrUserLastName(siteRequest_, userLastName);
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

	///////////////////
	// userFirstName //
	///////////////////

	@JsonInclude(Include.NON_NULL)
	protected String userFirstName;
	@JsonIgnore
	public Wrap<String> userFirstNameWrap = new Wrap<String>().p(this).c(String.class).var("userFirstName").o(userFirstName);

	protected abstract void _userFirstName(Wrap<String> c);

	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String o) {
		this.userFirstName = SiteRequestEnUS.staticSetUserFirstName(siteRequest_, o);
		this.userFirstNameWrap.alreadyInitialized = true;
	}
	public static String staticSetUserFirstName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteRequestEnUS userFirstNameInit() {
		if(!userFirstNameWrap.alreadyInitialized) {
			_userFirstName(userFirstNameWrap);
			if(userFirstName == null)
				setUserFirstName(userFirstNameWrap.o);
		}
		userFirstNameWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	public static String staticSolrUserFirstName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrUserFirstName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqUserFirstName(SiteRequestEnUS siteRequest_, String o) {
		return SiteRequestEnUS.staticSolrStrUserFirstName(siteRequest_, SiteRequestEnUS.staticSolrUserFirstName(siteRequest_, SiteRequestEnUS.staticSetUserFirstName(siteRequest_, o)));
	}

	public String solrUserFirstName() {
		return SiteRequestEnUS.staticSolrUserFirstName(siteRequest_, userFirstName);
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

	//////////////////
	// userFullName //
	//////////////////

	@JsonInclude(Include.NON_NULL)
	protected String userFullName;
	@JsonIgnore
	public Wrap<String> userFullNameWrap = new Wrap<String>().p(this).c(String.class).var("userFullName").o(userFullName);

	protected abstract void _userFullName(Wrap<String> c);

	public String getUserFullName() {
		return userFullName;
	}
	public void setUserFullName(String o) {
		this.userFullName = SiteRequestEnUS.staticSetUserFullName(siteRequest_, o);
		this.userFullNameWrap.alreadyInitialized = true;
	}
	public static String staticSetUserFullName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteRequestEnUS userFullNameInit() {
		if(!userFullNameWrap.alreadyInitialized) {
			_userFullName(userFullNameWrap);
			if(userFullName == null)
				setUserFullName(userFullNameWrap.o);
		}
		userFullNameWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	public static String staticSolrUserFullName(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrUserFullName(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqUserFullName(SiteRequestEnUS siteRequest_, String o) {
		return SiteRequestEnUS.staticSolrStrUserFullName(siteRequest_, SiteRequestEnUS.staticSolrUserFullName(siteRequest_, SiteRequestEnUS.staticSetUserFullName(siteRequest_, o)));
	}

	public String solrUserFullName() {
		return SiteRequestEnUS.staticSolrUserFullName(siteRequest_, userFullName);
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

	///////////////
	// userEmail //
	///////////////

	@JsonInclude(Include.NON_NULL)
	protected String userEmail;
	@JsonIgnore
	public Wrap<String> userEmailWrap = new Wrap<String>().p(this).c(String.class).var("userEmail").o(userEmail);

	protected abstract void _userEmail(Wrap<String> c);

	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String o) {
		this.userEmail = SiteRequestEnUS.staticSetUserEmail(siteRequest_, o);
		this.userEmailWrap.alreadyInitialized = true;
	}
	public static String staticSetUserEmail(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteRequestEnUS userEmailInit() {
		if(!userEmailWrap.alreadyInitialized) {
			_userEmail(userEmailWrap);
			if(userEmail == null)
				setUserEmail(userEmailWrap.o);
		}
		userEmailWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	public static String staticSolrUserEmail(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrUserEmail(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqUserEmail(SiteRequestEnUS siteRequest_, String o) {
		return SiteRequestEnUS.staticSolrStrUserEmail(siteRequest_, SiteRequestEnUS.staticSolrUserEmail(siteRequest_, SiteRequestEnUS.staticSetUserEmail(siteRequest_, o)));
	}

	public String solrUserEmail() {
		return SiteRequestEnUS.staticSolrUserEmail(siteRequest_, userEmail);
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

	////////////////////
	// userRealmRoles //
	////////////////////

	@JsonInclude(Include.NON_NULL)
	protected List<String> userRealmRoles = new ArrayList<String>();
	@JsonIgnore
	public Wrap<List<String>> userRealmRolesWrap = new Wrap<List<String>>().p(this).c(List.class).var("userRealmRoles").o(userRealmRoles);

	protected abstract void _userRealmRoles(List<String> o);

	public List<String> getUserRealmRoles() {
		return userRealmRoles;
	}

	public void setUserRealmRoles(List<String> userRealmRoles) {
		this.userRealmRoles = userRealmRoles;
		this.userRealmRolesWrap.alreadyInitialized = true;
	}
	public static String staticSetUserRealmRoles(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	public SiteRequestEnUS addUserRealmRoles(String...objets) {
		for(String o : objets) {
			addUserRealmRoles(o);
		}
		return (SiteRequestEnUS)this;
	}
	public SiteRequestEnUS addUserRealmRoles(String o) {
		if(o != null && !userRealmRoles.contains(o))
			this.userRealmRoles.add(o);
		return (SiteRequestEnUS)this;
	}
	public void setUserRealmRoles(JsonArray objets) {
		userRealmRoles.clear();
		for(int i = 0; i < objets.size(); i++) {
			String o = objets.getString(i);
			addUserRealmRoles(o);
		}
	}
	protected SiteRequestEnUS userRealmRolesInit() {
		if(!userRealmRolesWrap.alreadyInitialized) {
			_userRealmRoles(userRealmRoles);
		}
		userRealmRolesWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	public static String staticSolrUserRealmRoles(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrUserRealmRoles(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqUserRealmRoles(SiteRequestEnUS siteRequest_, String o) {
		return SiteRequestEnUS.staticSolrStrUserRealmRoles(siteRequest_, SiteRequestEnUS.staticSolrUserRealmRoles(siteRequest_, SiteRequestEnUS.staticSetUserRealmRoles(siteRequest_, o)));
	}

	public List<String> solrUserRealmRoles() {
		List<String> l = new ArrayList<String>();
		for(String o : userRealmRoles) {
			l.add(SiteRequestEnUS.staticSolrUserRealmRoles(siteRequest_, o));
		}
		return l;
	}

	public String strUserRealmRoles() {
		return userRealmRoles == null ? "" : userRealmRoles.toString();
	}

	public List<String> sqlUserRealmRoles() {
		return userRealmRoles;
	}

	public String jsonUserRealmRoles() {
		return userRealmRoles == null ? "" : userRealmRoles.toString();
	}

	//////////////////
	// userResource //
	//////////////////

	@JsonInclude(Include.NON_NULL)
	protected JsonObject userResource;
	@JsonIgnore
	public Wrap<JsonObject> userResourceWrap = new Wrap<JsonObject>().p(this).c(JsonObject.class).var("userResource").o(userResource);

	protected abstract void _userResource(Wrap<JsonObject> c);

	public JsonObject getUserResource() {
		return userResource;
	}

	public void setUserResource(JsonObject userResource) {
		this.userResource = userResource;
		this.userResourceWrap.alreadyInitialized = true;
	}
	public static JsonObject staticSetUserResource(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteRequestEnUS userResourceInit() {
		if(!userResourceWrap.alreadyInitialized) {
			_userResource(userResourceWrap);
			if(userResource == null)
				setUserResource(userResourceWrap.o);
		}
		userResourceWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	///////////////////////
	// userResourceRoles //
	///////////////////////

	@JsonInclude(Include.NON_NULL)
	protected List<String> userResourceRoles = new ArrayList<String>();
	@JsonIgnore
	public Wrap<List<String>> userResourceRolesWrap = new Wrap<List<String>>().p(this).c(List.class).var("userResourceRoles").o(userResourceRoles);

	protected abstract void _userResourceRoles(List<String> o);

	public List<String> getUserResourceRoles() {
		return userResourceRoles;
	}

	public void setUserResourceRoles(List<String> userResourceRoles) {
		this.userResourceRoles = userResourceRoles;
		this.userResourceRolesWrap.alreadyInitialized = true;
	}
	public static String staticSetUserResourceRoles(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	public SiteRequestEnUS addUserResourceRoles(String...objets) {
		for(String o : objets) {
			addUserResourceRoles(o);
		}
		return (SiteRequestEnUS)this;
	}
	public SiteRequestEnUS addUserResourceRoles(String o) {
		if(o != null && !userResourceRoles.contains(o))
			this.userResourceRoles.add(o);
		return (SiteRequestEnUS)this;
	}
	public void setUserResourceRoles(JsonArray objets) {
		userResourceRoles.clear();
		for(int i = 0; i < objets.size(); i++) {
			String o = objets.getString(i);
			addUserResourceRoles(o);
		}
	}
	protected SiteRequestEnUS userResourceRolesInit() {
		if(!userResourceRolesWrap.alreadyInitialized) {
			_userResourceRoles(userResourceRoles);
		}
		userResourceRolesWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	public static String staticSolrUserResourceRoles(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrUserResourceRoles(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqUserResourceRoles(SiteRequestEnUS siteRequest_, String o) {
		return SiteRequestEnUS.staticSolrStrUserResourceRoles(siteRequest_, SiteRequestEnUS.staticSolrUserResourceRoles(siteRequest_, SiteRequestEnUS.staticSetUserResourceRoles(siteRequest_, o)));
	}

	public List<String> solrUserResourceRoles() {
		List<String> l = new ArrayList<String>();
		for(String o : userResourceRoles) {
			l.add(SiteRequestEnUS.staticSolrUserResourceRoles(siteRequest_, o));
		}
		return l;
	}

	public String strUserResourceRoles() {
		return userResourceRoles == null ? "" : userResourceRoles.toString();
	}

	public List<String> sqlUserResourceRoles() {
		return userResourceRoles;
	}

	public String jsonUserResourceRoles() {
		return userResourceRoles == null ? "" : userResourceRoles.toString();
	}

	//////////////
	// siteUser //
	//////////////

	@JsonInclude(Include.NON_NULL)
	protected SiteUser siteUser;
	@JsonIgnore
	public Wrap<SiteUser> siteUserWrap = new Wrap<SiteUser>().p(this).c(SiteUser.class).var("siteUser").o(siteUser);

	protected abstract void _siteUser(Wrap<SiteUser> c);

	public SiteUser getSiteUser() {
		return siteUser;
	}

	public void setSiteUser(SiteUser siteUser) {
		this.siteUser = siteUser;
		this.siteUserWrap.alreadyInitialized = true;
	}
	public static SiteUser staticSetSiteUser(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteRequestEnUS siteUserInit() {
		if(!siteUserWrap.alreadyInitialized) {
			_siteUser(siteUserWrap);
			if(siteUser == null)
				setSiteUser(siteUserWrap.o);
		}
		if(siteUser != null)
			siteUser.initDeepForClass(siteRequest_);
		siteUserWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	//////////////
	// xmlStack //
	//////////////

	@JsonInclude(Include.NON_NULL)
	protected Stack<String> xmlStack = new Stack<String>();
	@JsonIgnore
	public Wrap<Stack<String>> xmlStackWrap = new Wrap<Stack<String>>().p(this).c(Stack.class).var("xmlStack").o(xmlStack);

	protected abstract void _xmlStack(Stack<String> o);

	public Stack<String> getXmlStack() {
		return xmlStack;
	}

	public void setXmlStack(Stack<String> xmlStack) {
		this.xmlStack = xmlStack;
		this.xmlStackWrap.alreadyInitialized = true;
	}
	public static Stack<String> staticSetXmlStack(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteRequestEnUS xmlStackInit() {
		if(!xmlStackWrap.alreadyInitialized) {
			_xmlStack(xmlStack);
		}
		xmlStackWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	//////////////////
	// solrDocument //
	//////////////////

	@JsonInclude(Include.NON_NULL)
	protected SolrDocument solrDocument;
	@JsonIgnore
	public Wrap<SolrDocument> solrDocumentWrap = new Wrap<SolrDocument>().p(this).c(SolrDocument.class).var("solrDocument").o(solrDocument);

	protected abstract void _solrDocument(Wrap<SolrDocument> c);

	public SolrDocument getSolrDocument() {
		return solrDocument;
	}

	public void setSolrDocument(SolrDocument solrDocument) {
		this.solrDocument = solrDocument;
		this.solrDocumentWrap.alreadyInitialized = true;
	}
	public static SolrDocument staticSetSolrDocument(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteRequestEnUS solrDocumentInit() {
		if(!solrDocumentWrap.alreadyInitialized) {
			_solrDocument(solrDocumentWrap);
			if(solrDocument == null)
				setSolrDocument(solrDocumentWrap.o);
		}
		solrDocumentWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	///////////////
	// pageAdmin //
	///////////////

	@JsonInclude(Include.NON_NULL)
	protected Boolean pageAdmin;
	@JsonIgnore
	public Wrap<Boolean> pageAdminWrap = new Wrap<Boolean>().p(this).c(Boolean.class).var("pageAdmin").o(pageAdmin);

	protected abstract void _pageAdmin(Wrap<Boolean> c);

	public Boolean getPageAdmin() {
		return pageAdmin;
	}

	public void setPageAdmin(Boolean pageAdmin) {
		this.pageAdmin = pageAdmin;
		this.pageAdminWrap.alreadyInitialized = true;
	}
	public void setPageAdmin(String o) {
		this.pageAdmin = SiteRequestEnUS.staticSetPageAdmin(siteRequest_, o);
		this.pageAdminWrap.alreadyInitialized = true;
	}
	public static Boolean staticSetPageAdmin(SiteRequestEnUS siteRequest_, String o) {
		return Boolean.parseBoolean(o);
	}
	protected SiteRequestEnUS pageAdminInit() {
		if(!pageAdminWrap.alreadyInitialized) {
			_pageAdmin(pageAdminWrap);
			if(pageAdmin == null)
				setPageAdmin(pageAdminWrap.o);
		}
		pageAdminWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	public static Boolean staticSolrPageAdmin(SiteRequestEnUS siteRequest_, Boolean o) {
		return o;
	}

	public static String staticSolrStrPageAdmin(SiteRequestEnUS siteRequest_, Boolean o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqPageAdmin(SiteRequestEnUS siteRequest_, String o) {
		return SiteRequestEnUS.staticSolrStrPageAdmin(siteRequest_, SiteRequestEnUS.staticSolrPageAdmin(siteRequest_, SiteRequestEnUS.staticSetPageAdmin(siteRequest_, o)));
	}

	public Boolean solrPageAdmin() {
		return SiteRequestEnUS.staticSolrPageAdmin(siteRequest_, pageAdmin);
	}

	public String strPageAdmin() {
		return pageAdmin == null ? "" : pageAdmin.toString();
	}

	public Boolean sqlPageAdmin() {
		return pageAdmin;
	}

	public String jsonPageAdmin() {
		return pageAdmin == null ? "" : pageAdmin.toString();
	}

	///////////////
	// requestPk //
	///////////////

	@JsonSerialize(using = ToStringSerializer.class)
	@JsonInclude(Include.NON_NULL)
	protected Long requestPk;
	@JsonIgnore
	public Wrap<Long> requestPkWrap = new Wrap<Long>().p(this).c(Long.class).var("requestPk").o(requestPk);

	protected abstract void _requestPk(Wrap<Long> c);

	public Long getRequestPk() {
		return requestPk;
	}

	public void setRequestPk(Long requestPk) {
		this.requestPk = requestPk;
		this.requestPkWrap.alreadyInitialized = true;
	}
	public void setRequestPk(String o) {
		this.requestPk = SiteRequestEnUS.staticSetRequestPk(siteRequest_, o);
		this.requestPkWrap.alreadyInitialized = true;
	}
	public static Long staticSetRequestPk(SiteRequestEnUS siteRequest_, String o) {
		if(NumberUtils.isParsable(o))
			return Long.parseLong(o);
		return null;
	}
	protected SiteRequestEnUS requestPkInit() {
		if(!requestPkWrap.alreadyInitialized) {
			_requestPk(requestPkWrap);
			if(requestPk == null)
				setRequestPk(requestPkWrap.o);
		}
		requestPkWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	public static Long staticSolrRequestPk(SiteRequestEnUS siteRequest_, Long o) {
		return o;
	}

	public static String staticSolrStrRequestPk(SiteRequestEnUS siteRequest_, Long o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqRequestPk(SiteRequestEnUS siteRequest_, String o) {
		return SiteRequestEnUS.staticSolrStrRequestPk(siteRequest_, SiteRequestEnUS.staticSolrRequestPk(siteRequest_, SiteRequestEnUS.staticSetRequestPk(siteRequest_, o)));
	}

	public Long solrRequestPk() {
		return SiteRequestEnUS.staticSolrRequestPk(siteRequest_, requestPk);
	}

	public String strRequestPk() {
		return requestPk == null ? "" : requestPk.toString();
	}

	public Long sqlRequestPk() {
		return requestPk;
	}

	public String jsonRequestPk() {
		return requestPk == null ? "" : requestPk.toString();
	}

	////////////////
	// requestUri //
	////////////////

	@JsonInclude(Include.NON_NULL)
	protected String requestUri;
	@JsonIgnore
	public Wrap<String> requestUriWrap = new Wrap<String>().p(this).c(String.class).var("requestUri").o(requestUri);

	protected abstract void _requestUri(Wrap<String> c);

	public String getRequestUri() {
		return requestUri;
	}
	public void setRequestUri(String o) {
		this.requestUri = SiteRequestEnUS.staticSetRequestUri(siteRequest_, o);
		this.requestUriWrap.alreadyInitialized = true;
	}
	public static String staticSetRequestUri(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteRequestEnUS requestUriInit() {
		if(!requestUriWrap.alreadyInitialized) {
			_requestUri(requestUriWrap);
			if(requestUri == null)
				setRequestUri(requestUriWrap.o);
		}
		requestUriWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	public static String staticSolrRequestUri(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrRequestUri(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqRequestUri(SiteRequestEnUS siteRequest_, String o) {
		return SiteRequestEnUS.staticSolrStrRequestUri(siteRequest_, SiteRequestEnUS.staticSolrRequestUri(siteRequest_, SiteRequestEnUS.staticSetRequestUri(siteRequest_, o)));
	}

	public String solrRequestUri() {
		return SiteRequestEnUS.staticSolrRequestUri(siteRequest_, requestUri);
	}

	public String strRequestUri() {
		return requestUri == null ? "" : requestUri;
	}

	public String sqlRequestUri() {
		return requestUri;
	}

	public String jsonRequestUri() {
		return requestUri == null ? "" : requestUri;
	}

	///////////////////
	// requestMethod //
	///////////////////

	@JsonInclude(Include.NON_NULL)
	protected String requestMethod;
	@JsonIgnore
	public Wrap<String> requestMethodWrap = new Wrap<String>().p(this).c(String.class).var("requestMethod").o(requestMethod);

	protected abstract void _requestMethod(Wrap<String> c);

	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String o) {
		this.requestMethod = SiteRequestEnUS.staticSetRequestMethod(siteRequest_, o);
		this.requestMethodWrap.alreadyInitialized = true;
	}
	public static String staticSetRequestMethod(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}
	protected SiteRequestEnUS requestMethodInit() {
		if(!requestMethodWrap.alreadyInitialized) {
			_requestMethod(requestMethodWrap);
			if(requestMethod == null)
				setRequestMethod(requestMethodWrap.o);
		}
		requestMethodWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	public static String staticSolrRequestMethod(SiteRequestEnUS siteRequest_, String o) {
		return o;
	}

	public static String staticSolrStrRequestMethod(SiteRequestEnUS siteRequest_, String o) {
		return o == null ? null : o.toString();
	}

	public static String staticSolrFqRequestMethod(SiteRequestEnUS siteRequest_, String o) {
		return SiteRequestEnUS.staticSolrStrRequestMethod(siteRequest_, SiteRequestEnUS.staticSolrRequestMethod(siteRequest_, SiteRequestEnUS.staticSetRequestMethod(siteRequest_, o)));
	}

	public String solrRequestMethod() {
		return SiteRequestEnUS.staticSolrRequestMethod(siteRequest_, requestMethod);
	}

	public String strRequestMethod() {
		return requestMethod == null ? "" : requestMethod;
	}

	public String sqlRequestMethod() {
		return requestMethod;
	}

	public String jsonRequestMethod() {
		return requestMethod == null ? "" : requestMethod;
	}

	////////
	// tx //
	////////

	@JsonInclude(Include.NON_NULL)
	protected Transaction tx;
	@JsonIgnore
	public Wrap<Transaction> txWrap = new Wrap<Transaction>().p(this).c(Transaction.class).var("tx").o(tx);

	protected abstract void _tx(Wrap<Transaction> c);

	public Transaction getTx() {
		return tx;
	}

	public void setTx(Transaction tx) {
		this.tx = tx;
		this.txWrap.alreadyInitialized = true;
	}
	public static Transaction staticSetTx(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteRequestEnUS txInit() {
		if(!txWrap.alreadyInitialized) {
			_tx(txWrap);
			if(tx == null)
				setTx(txWrap.o);
		}
		txWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	///////////////////
	// sqlConnection //
	///////////////////

	@JsonInclude(Include.NON_NULL)
	protected SqlConnection sqlConnection;
	@JsonIgnore
	public Wrap<SqlConnection> sqlConnectionWrap = new Wrap<SqlConnection>().p(this).c(SqlConnection.class).var("sqlConnection").o(sqlConnection);

	protected abstract void _sqlConnection(Wrap<SqlConnection> c);

	public SqlConnection getSqlConnection() {
		return sqlConnection;
	}

	public void setSqlConnection(SqlConnection sqlConnection) {
		this.sqlConnection = sqlConnection;
		this.sqlConnectionWrap.alreadyInitialized = true;
	}
	public static SqlConnection staticSetSqlConnection(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteRequestEnUS sqlConnectionInit() {
		if(!sqlConnectionWrap.alreadyInitialized) {
			_sqlConnection(sqlConnectionWrap);
			if(sqlConnection == null)
				setSqlConnection(sqlConnectionWrap.o);
		}
		sqlConnectionWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	////////////////////
	// requestHeaders //
	////////////////////

	@JsonInclude(Include.NON_NULL)
	protected MultiMap requestHeaders;
	@JsonIgnore
	public Wrap<MultiMap> requestHeadersWrap = new Wrap<MultiMap>().p(this).c(MultiMap.class).var("requestHeaders").o(requestHeaders);

	protected abstract void _requestHeaders(Wrap<MultiMap> c);

	public MultiMap getRequestHeaders() {
		return requestHeaders;
	}

	public void setRequestHeaders(MultiMap requestHeaders) {
		this.requestHeaders = requestHeaders;
		this.requestHeadersWrap.alreadyInitialized = true;
	}
	public static MultiMap staticSetRequestHeaders(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteRequestEnUS requestHeadersInit() {
		if(!requestHeadersWrap.alreadyInitialized) {
			_requestHeaders(requestHeadersWrap);
			if(requestHeaders == null)
				setRequestHeaders(requestHeadersWrap.o);
		}
		requestHeadersWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	/////////////////
	// requestVars //
	/////////////////

	@JsonInclude(Include.NON_NULL)
	protected Map<String, String> requestVars = new HashMap<String, String>();
	@JsonIgnore
	public Wrap<Map<String, String>> requestVarsWrap = new Wrap<Map<String, String>>().p(this).c(Map.class).var("requestVars").o(requestVars);

	protected abstract void _requestVars(Map<String, String> m);

	public Map<String, String> getRequestVars() {
		return requestVars;
	}

	public void setRequestVars(Map<String, String> requestVars) {
		this.requestVars = requestVars;
		this.requestVarsWrap.alreadyInitialized = true;
	}
	public static Map<String, String> staticSetRequestVars(SiteRequestEnUS siteRequest_, String o) {
		return null;
	}
	protected SiteRequestEnUS requestVarsInit() {
		if(!requestVarsWrap.alreadyInitialized) {
			_requestVars(requestVars);
		}
		requestVarsWrap.alreadyInitialized(true);
		return (SiteRequestEnUS)this;
	}

	//////////////
	// initDeep //
	//////////////

	protected boolean alreadyInitializedSiteRequestEnUS = false;

	public SiteRequestEnUS initDeepSiteRequestEnUS(SiteRequestEnUS siteRequest_) {
		setSiteRequest_(siteRequest_);
		if(!alreadyInitializedSiteRequestEnUS) {
			alreadyInitializedSiteRequestEnUS = true;
			initDeepSiteRequestEnUS();
		}
		return (SiteRequestEnUS)this;
	}

	public void initDeepSiteRequestEnUS() {
		initSiteRequestEnUS();
	}

	public void initSiteRequestEnUS() {
		siteContext_Init();
		siteConfig_Init();
		siteRequest_Init();
		apiRequest_Init();
		vertxInit();
		jsonObjectInit();
		solrQueryInit();
		serviceRequestInit();
		queryResponseInit();
		searchResultsInit();
		wInit();
		userInit();
		jsonPrincipalInit();
		userIdInit();
		userKeyInit();
		sessionIdInit();
		sessionIdBeforeInit();
		userNameInit();
		userLastNameInit();
		userFirstNameInit();
		userFullNameInit();
		userEmailInit();
		userRealmRolesInit();
		userResourceInit();
		userResourceRolesInit();
		siteUserInit();
		xmlStackInit();
		solrDocumentInit();
		pageAdminInit();
		requestPkInit();
		requestUriInit();
		requestMethodInit();
		txInit();
		sqlConnectionInit();
		requestHeadersInit();
		requestVarsInit();
	}

	public void initDeepForClass(SiteRequestEnUS siteRequest_) {
		initDeepSiteRequestEnUS(siteRequest_);
	}

	/////////////////
	// siteRequest //
	/////////////////

	public void siteRequestSiteRequestEnUS(SiteRequestEnUS siteRequest_) {
		if(w != null)
			w.setSiteRequest_(siteRequest_);
		if(siteUser != null)
			siteUser.setSiteRequest_(siteRequest_);
	}

	public void siteRequestForClass(SiteRequestEnUS siteRequest_) {
		siteRequestSiteRequestEnUS(siteRequest_);
	}

	/////////////
	// obtain //
	/////////////

	public Object obtainForClass(String var) {
		String[] vars = StringUtils.split(var, ".");
		Object o = null;
		for(String v : vars) {
			if(o == null)
				o = obtainSiteRequestEnUS(v);
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
	public Object obtainSiteRequestEnUS(String var) {
		SiteRequestEnUS oSiteRequestEnUS = (SiteRequestEnUS)this;
		switch(var) {
			case "siteContext_":
				return oSiteRequestEnUS.siteContext_;
			case "siteConfig_":
				return oSiteRequestEnUS.siteConfig_;
			case "siteRequest_":
				return oSiteRequestEnUS.siteRequest_;
			case "apiRequest_":
				return oSiteRequestEnUS.apiRequest_;
			case "vertx":
				return oSiteRequestEnUS.vertx;
			case "jsonObject":
				return oSiteRequestEnUS.jsonObject;
			case "solrQuery":
				return oSiteRequestEnUS.solrQuery;
			case "serviceRequest":
				return oSiteRequestEnUS.serviceRequest;
			case "queryResponse":
				return oSiteRequestEnUS.queryResponse;
			case "searchResults":
				return oSiteRequestEnUS.searchResults;
			case "w":
				return oSiteRequestEnUS.w;
			case "user":
				return oSiteRequestEnUS.user;
			case "jsonPrincipal":
				return oSiteRequestEnUS.jsonPrincipal;
			case "userId":
				return oSiteRequestEnUS.userId;
			case "userKey":
				return oSiteRequestEnUS.userKey;
			case "sessionId":
				return oSiteRequestEnUS.sessionId;
			case "sessionIdBefore":
				return oSiteRequestEnUS.sessionIdBefore;
			case "userName":
				return oSiteRequestEnUS.userName;
			case "userLastName":
				return oSiteRequestEnUS.userLastName;
			case "userFirstName":
				return oSiteRequestEnUS.userFirstName;
			case "userFullName":
				return oSiteRequestEnUS.userFullName;
			case "userEmail":
				return oSiteRequestEnUS.userEmail;
			case "userRealmRoles":
				return oSiteRequestEnUS.userRealmRoles;
			case "userResource":
				return oSiteRequestEnUS.userResource;
			case "userResourceRoles":
				return oSiteRequestEnUS.userResourceRoles;
			case "siteUser":
				return oSiteRequestEnUS.siteUser;
			case "xmlStack":
				return oSiteRequestEnUS.xmlStack;
			case "solrDocument":
				return oSiteRequestEnUS.solrDocument;
			case "pageAdmin":
				return oSiteRequestEnUS.pageAdmin;
			case "requestPk":
				return oSiteRequestEnUS.requestPk;
			case "requestUri":
				return oSiteRequestEnUS.requestUri;
			case "requestMethod":
				return oSiteRequestEnUS.requestMethod;
			case "tx":
				return oSiteRequestEnUS.tx;
			case "sqlConnection":
				return oSiteRequestEnUS.sqlConnection;
			case "requestHeaders":
				return oSiteRequestEnUS.requestHeaders;
			case "requestVars":
				return oSiteRequestEnUS.requestVars;
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
				o = attributeSiteRequestEnUS(v, val);
			else if(o instanceof Cluster) {
				Cluster cluster = (Cluster)o;
				o = cluster.attributeForClass(v, val);
			}
		}
		return o != null;
	}
	public Object attributeSiteRequestEnUS(String var, Object val) {
		SiteRequestEnUS oSiteRequestEnUS = (SiteRequestEnUS)this;
		switch(var) {
			default:
				return null;
		}
	}

	///////////////
	// staticSet //
	///////////////

	public static Object staticSetForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSetSiteRequestEnUS(entityVar,  siteRequest_, o);
	}
	public static Object staticSetSiteRequestEnUS(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "userId":
			return SiteRequestEnUS.staticSetUserId(siteRequest_, o);
		case "userKey":
			return SiteRequestEnUS.staticSetUserKey(siteRequest_, o);
		case "sessionId":
			return SiteRequestEnUS.staticSetSessionId(siteRequest_, o);
		case "sessionIdBefore":
			return SiteRequestEnUS.staticSetSessionIdBefore(siteRequest_, o);
		case "userName":
			return SiteRequestEnUS.staticSetUserName(siteRequest_, o);
		case "userLastName":
			return SiteRequestEnUS.staticSetUserLastName(siteRequest_, o);
		case "userFirstName":
			return SiteRequestEnUS.staticSetUserFirstName(siteRequest_, o);
		case "userFullName":
			return SiteRequestEnUS.staticSetUserFullName(siteRequest_, o);
		case "userEmail":
			return SiteRequestEnUS.staticSetUserEmail(siteRequest_, o);
		case "userRealmRoles":
			return SiteRequestEnUS.staticSetUserRealmRoles(siteRequest_, o);
		case "userResourceRoles":
			return SiteRequestEnUS.staticSetUserResourceRoles(siteRequest_, o);
		case "pageAdmin":
			return SiteRequestEnUS.staticSetPageAdmin(siteRequest_, o);
		case "requestPk":
			return SiteRequestEnUS.staticSetRequestPk(siteRequest_, o);
		case "requestUri":
			return SiteRequestEnUS.staticSetRequestUri(siteRequest_, o);
		case "requestMethod":
			return SiteRequestEnUS.staticSetRequestMethod(siteRequest_, o);
			default:
				return null;
		}
	}

	////////////////
	// staticSolr //
	////////////////

	public static Object staticSolrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrSiteRequestEnUS(entityVar,  siteRequest_, o);
	}
	public static Object staticSolrSiteRequestEnUS(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "userId":
			return SiteRequestEnUS.staticSolrUserId(siteRequest_, (String)o);
		case "userKey":
			return SiteRequestEnUS.staticSolrUserKey(siteRequest_, (Long)o);
		case "sessionId":
			return SiteRequestEnUS.staticSolrSessionId(siteRequest_, (String)o);
		case "sessionIdBefore":
			return SiteRequestEnUS.staticSolrSessionIdBefore(siteRequest_, (String)o);
		case "userName":
			return SiteRequestEnUS.staticSolrUserName(siteRequest_, (String)o);
		case "userLastName":
			return SiteRequestEnUS.staticSolrUserLastName(siteRequest_, (String)o);
		case "userFirstName":
			return SiteRequestEnUS.staticSolrUserFirstName(siteRequest_, (String)o);
		case "userFullName":
			return SiteRequestEnUS.staticSolrUserFullName(siteRequest_, (String)o);
		case "userEmail":
			return SiteRequestEnUS.staticSolrUserEmail(siteRequest_, (String)o);
		case "userRealmRoles":
			return SiteRequestEnUS.staticSolrUserRealmRoles(siteRequest_, (String)o);
		case "userResourceRoles":
			return SiteRequestEnUS.staticSolrUserResourceRoles(siteRequest_, (String)o);
		case "pageAdmin":
			return SiteRequestEnUS.staticSolrPageAdmin(siteRequest_, (Boolean)o);
		case "requestPk":
			return SiteRequestEnUS.staticSolrRequestPk(siteRequest_, (Long)o);
		case "requestUri":
			return SiteRequestEnUS.staticSolrRequestUri(siteRequest_, (String)o);
		case "requestMethod":
			return SiteRequestEnUS.staticSolrRequestMethod(siteRequest_, (String)o);
			default:
				return null;
		}
	}

	///////////////////
	// staticSolrStr //
	///////////////////

	public static String staticSolrStrForClass(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		return staticSolrStrSiteRequestEnUS(entityVar,  siteRequest_, o);
	}
	public static String staticSolrStrSiteRequestEnUS(String entityVar, SiteRequestEnUS siteRequest_, Object o) {
		switch(entityVar) {
		case "userId":
			return SiteRequestEnUS.staticSolrStrUserId(siteRequest_, (String)o);
		case "userKey":
			return SiteRequestEnUS.staticSolrStrUserKey(siteRequest_, (Long)o);
		case "sessionId":
			return SiteRequestEnUS.staticSolrStrSessionId(siteRequest_, (String)o);
		case "sessionIdBefore":
			return SiteRequestEnUS.staticSolrStrSessionIdBefore(siteRequest_, (String)o);
		case "userName":
			return SiteRequestEnUS.staticSolrStrUserName(siteRequest_, (String)o);
		case "userLastName":
			return SiteRequestEnUS.staticSolrStrUserLastName(siteRequest_, (String)o);
		case "userFirstName":
			return SiteRequestEnUS.staticSolrStrUserFirstName(siteRequest_, (String)o);
		case "userFullName":
			return SiteRequestEnUS.staticSolrStrUserFullName(siteRequest_, (String)o);
		case "userEmail":
			return SiteRequestEnUS.staticSolrStrUserEmail(siteRequest_, (String)o);
		case "userRealmRoles":
			return SiteRequestEnUS.staticSolrStrUserRealmRoles(siteRequest_, (String)o);
		case "userResourceRoles":
			return SiteRequestEnUS.staticSolrStrUserResourceRoles(siteRequest_, (String)o);
		case "pageAdmin":
			return SiteRequestEnUS.staticSolrStrPageAdmin(siteRequest_, (Boolean)o);
		case "requestPk":
			return SiteRequestEnUS.staticSolrStrRequestPk(siteRequest_, (Long)o);
		case "requestUri":
			return SiteRequestEnUS.staticSolrStrRequestUri(siteRequest_, (String)o);
		case "requestMethod":
			return SiteRequestEnUS.staticSolrStrRequestMethod(siteRequest_, (String)o);
			default:
				return null;
		}
	}

	//////////////////
	// staticSolrFq //
	//////////////////

	public static String staticSolrFqForClass(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		return staticSolrFqSiteRequestEnUS(entityVar,  siteRequest_, o);
	}
	public static String staticSolrFqSiteRequestEnUS(String entityVar, SiteRequestEnUS siteRequest_, String o) {
		switch(entityVar) {
		case "userId":
			return SiteRequestEnUS.staticSolrFqUserId(siteRequest_, o);
		case "userKey":
			return SiteRequestEnUS.staticSolrFqUserKey(siteRequest_, o);
		case "sessionId":
			return SiteRequestEnUS.staticSolrFqSessionId(siteRequest_, o);
		case "sessionIdBefore":
			return SiteRequestEnUS.staticSolrFqSessionIdBefore(siteRequest_, o);
		case "userName":
			return SiteRequestEnUS.staticSolrFqUserName(siteRequest_, o);
		case "userLastName":
			return SiteRequestEnUS.staticSolrFqUserLastName(siteRequest_, o);
		case "userFirstName":
			return SiteRequestEnUS.staticSolrFqUserFirstName(siteRequest_, o);
		case "userFullName":
			return SiteRequestEnUS.staticSolrFqUserFullName(siteRequest_, o);
		case "userEmail":
			return SiteRequestEnUS.staticSolrFqUserEmail(siteRequest_, o);
		case "userRealmRoles":
			return SiteRequestEnUS.staticSolrFqUserRealmRoles(siteRequest_, o);
		case "userResourceRoles":
			return SiteRequestEnUS.staticSolrFqUserResourceRoles(siteRequest_, o);
		case "pageAdmin":
			return SiteRequestEnUS.staticSolrFqPageAdmin(siteRequest_, o);
		case "requestPk":
			return SiteRequestEnUS.staticSolrFqRequestPk(siteRequest_, o);
		case "requestUri":
			return SiteRequestEnUS.staticSolrFqRequestUri(siteRequest_, o);
		case "requestMethod":
			return SiteRequestEnUS.staticSolrFqRequestMethod(siteRequest_, o);
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
					o = defineSiteRequestEnUS(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSiteRequestEnUS(String var, String val) {
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
					o = defineSiteRequestEnUS(v, val);
				else if(o instanceof Cluster) {
					Cluster oCluster = (Cluster)o;
					o = oCluster.defineForClass(v, val);
				}
			}
		}
		return o != null;
	}
	public Object defineSiteRequestEnUS(String var, Object val) {
		switch(var.toLowerCase()) {
			default:
				return null;
		}
	}

	//////////////////
	// apiRequest //
	//////////////////

	public void apiRequestSiteRequestEnUS() {
		ApiRequest apiRequest = Optional.ofNullable(siteRequest_).map(SiteRequestEnUS::getApiRequest_).orElse(null);
		Object o = Optional.ofNullable(apiRequest).map(ApiRequest::getOriginal).orElse(null);
		if(o != null && o instanceof SiteRequestEnUS) {
			SiteRequestEnUS original = (SiteRequestEnUS)o;
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
		if(!(o instanceof SiteRequestEnUS))
			return false;
		SiteRequestEnUS that = (SiteRequestEnUS)o;
		return true;
	}

	//////////////
	// toString //
	//////////////

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("SiteRequestEnUS { ");
		sb.append(" }");
		return sb.toString();
	}
}
