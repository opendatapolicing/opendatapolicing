package com.opendatapolicing.enus.request; 

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import com.opendatapolicing.enus.config.SiteConfig;
import com.opendatapolicing.enus.context.SiteContextEnUS;
import com.opendatapolicing.enus.request.api.ApiRequest;
import com.opendatapolicing.enus.user.SiteUser;
import com.opendatapolicing.enus.wrap.Wrap;
import com.opendatapolicing.enus.writer.AllWriter;

import io.vertx.core.Vertx;
import io.vertx.core.http.CaseInsensitiveHeaders;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.oauth2.KeycloakHelper;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.sqlclient.SqlConnection;
import io.vertx.sqlclient.Transaction;

/**
 * Keyword: classSimpleNameSiteRequest
 */      
public class SiteRequestEnUS extends SiteRequestEnUSGen<Object> implements Serializable {

	/**	
	 *	The site context with global site information. 
	 **/
	protected void _siteContext_(Wrap<SiteContextEnUS> c) {
	}

	private static final Pattern PATTERN_SESSION = Pattern.compile("vertx-web.session=(\\w+)");

	/**	
	 *	The site configuration. 
	 **/
	protected void _siteConfig_(Wrap<SiteConfig> c) {
		SiteConfig o = siteContext_.getSiteConfig();
		c.o(o);
	}

	protected void _siteRequest_(Wrap<SiteRequestEnUS> c) { 
		c.o(this);
	}

	protected void _apiRequest_(Wrap<ApiRequest> c) { 
	}

	protected void _vertx(Wrap<Vertx> c) {
		if(siteContext_ != null)
			c.o(siteContext_.getVertx());
	}

	protected void _jsonObject(Wrap<JsonObject> c) {
	}

	protected void _solrQuery(Wrap<SolrQuery> c) {
	}

	protected void _operationRequest(Wrap<OperationRequest> c) {
	}

	protected void _queryResponse(Wrap<QueryResponse> c) {
		if(solrQuery != null) {
			try {
				QueryResponse o = siteContext_.getSolrClient().query(solrQuery);
				c.o(o);
			} catch (SolrServerException | IOException e) {
				ExceptionUtils.rethrow(e);
			}
		}
	}

	protected void _searchResults(Wrap<SolrDocumentList> c) {
		if(queryResponse != null) {
			SolrDocumentList o = queryResponse.getResults();
			c.o(o);
		}
	}

	protected void _w(Wrap<AllWriter> c) {
	}

	protected void _userVertx(Wrap<JsonObject> c) {
		if(operationRequest != null) {
			JsonObject o = operationRequest.getUser();
			c.o(o);
		}

	}

	protected void _jsonPrincipal(Wrap<JsonObject> c) {
		if(userVertx != null) {
			JsonObject o = KeycloakHelper.parseToken(userVertx.getString("access_token"));
			c.o(o);
		}
	}

	protected void _userId(Wrap<String> c) {
		if(jsonPrincipal != null) {
			String o = jsonPrincipal.getString("sub");
			c.o(o);
		}
	}

	protected void _userKey(Wrap<Long> c) {
	}

	protected void _sessionId(Wrap<String> c) {
		if(operationRequest != null) {
			String cookie = operationRequest.getHeaders().get("Cookie");
			if(StringUtils.isNotBlank(cookie)) {
				Matcher m = PATTERN_SESSION.matcher(cookie);
				if(m.matches()) {
					c.o(m.group(1));
				}
			}
		}
	}

	protected void _sessionIdBefore(Wrap<String> c) {
		if(operationRequest != null) {
			c.o(operationRequest.getParams().getJsonObject("cookie").getString("sessionIdBefore"));
		}
	}

	protected void _userName(Wrap<String> c) {
		if(jsonPrincipal != null) {
			String o = jsonPrincipal.getString("preferred_username");
			c.o(o);
		}
	}

	protected void _userLastName(Wrap<String> c) {
		if(jsonPrincipal != null) {
			String o = jsonPrincipal.getString("family_name");
			c.o(o);
		}
	}

	protected void _userFirstName(Wrap<String> c) { 
		if(jsonPrincipal != null) {
			String o = jsonPrincipal.getString("given_name");
			c.o(o);
		}
	}

	protected void _userFullName(Wrap<String> c) {
		if(jsonPrincipal != null) {
			String o = jsonPrincipal.getString("name");
			c.o(o);
		}
	}

	protected void _userRealmRoles(List<String> o) {
		JsonArray roles = Optional.ofNullable(jsonPrincipal).map(o1 -> o1.getJsonObject("realm_access")).map(o2 -> o2.getJsonArray("roles")).orElse(new JsonArray());
		roles.stream().forEach(r -> {
			addUserRealmRoles((String)r);
		});
	}

	protected void _userResource(Wrap<JsonObject> c) {
		JsonObject o = Optional.ofNullable(jsonPrincipal).map(p -> p.getJsonObject("resource_access")).map(o1 -> o1.getJsonObject(
				Optional.ofNullable(siteRequest_).map(r -> r.getSiteConfig_()).map(c1 -> c1.getAuthResource()).orElse("")
				)).orElse(new JsonObject());
		c.o(o);
	}

	protected void _userResourceRoles(List<String> o) {
		JsonArray roles = Optional.ofNullable(userResource).map(o2 -> o2.getJsonArray("roles")).orElse(new JsonArray());
		roles.stream().forEach(r -> {
			addUserResourceRoles((String)r);
		});
	}

	protected void _siteUser(Wrap<SiteUser> c) { 
		if(userId != null) {
			SiteUser o = new SiteUser();
			o.setUserName(userName);
			o.setUserFirstName(userFirstName);
			o.setUserLastName(userLastName);
			o.setUserId(userId);
			c.o(o);
		}
	}

	protected void _xmlStack(Stack<String> o) {}

	protected void _solrDocument(Wrap<SolrDocument> c) {  
	}

	protected void _pageAdmin(Wrap<Boolean> c) { 
		c.o(false);
	}

	protected void _requestPk(Wrap<Long> c) {
		if(operationRequest != null)
			c.o(operationRequest.getParams().getLong("pk"));
	}

	protected void _requestUri(Wrap<String> c) {
	}

	protected void _requestMethod(Wrap<String> c) {
	}

	protected void _tx(Wrap<Transaction> c) {
	}

	protected void _sqlConnection(Wrap<SqlConnection> c) {
	}

	protected void _requestHeaders(Wrap<CaseInsensitiveHeaders> c) {
	}

	protected void _requestVars(Map<String, String> m) {
	}

	public SiteRequestEnUS copy() {
		SiteRequestEnUS o = new SiteRequestEnUS();
		o.setSiteContext_(siteContext_);
		o.setJsonObject(jsonObject);
		o.setSolrQuery(solrQuery);
		o.setOperationRequest(operationRequest);
		o.setUserKey(userKey);
		o.setSolrDocument(solrDocument);
		o.setPageAdmin(pageAdmin);
		o.setTx(tx);
		o.setRequestHeaders(requestHeaders);
		o.setRequestVars(requestVars);
		return o;
	}
}
