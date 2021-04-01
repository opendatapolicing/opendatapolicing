package com.opendatapolicing.enus.config;  

import java.io.Serializable;
import java.time.ZoneId;

import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.opendatapolicing.enus.wrap.Wrap;

import io.vertx.core.json.JsonObject;




/**
 * Keyword: classSimpleNameSiteConfig
 */   
public class SiteConfig extends SiteConfigGen<Object> implements Serializable {

	/**	
	 *	The path to the config file of the site. 
	 **/
	protected void _configPath(Wrap<String> c) {   
		String o = System.getenv("configPath");
		c.o(o);
	}

	/**	
	 *	The INI Configuration Object for the config file. 
	 **/
	protected void _config(Wrap<JsonObject> c) {
	}

	/**	
	 *	The name of the principal group of settings of the config for this website. 
	 **/
	protected void _siteIdentifier(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv("appName");
		else
			o = config.getString("appName");

		c.o(o);
	}

	/**	
	 *	The path to the project of the site cloned from git. 
	 **/
	protected void _appPath(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var, StringUtils.substringBefore(configPath, "/config/"));

		c.o(o);
	}

	/**	
	 *	The path to the docroot for the project. 
	 **/
	protected void _docRoot(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	/**	
	 *	The name of the company. 
	 **/
	protected void _companyName(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	/**	
	 *	The domain name of the site. 
	 **/
	protected void _domainName(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	/**	
	 *	The host name of the site. 
	 **/
	protected void _siteHostName(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	/**	
	 *	The port of the site. 
	 **/
	protected void _sitePort(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = NumberUtils.toInt(System.getenv(c.var));
		else
			o = NumberUtils.toInt(config.getString(c.var), 8080);
		c.o(o);
	}

	/**
	 * The number of instances of the Vertx verticle to deploy. 
	 **/
	protected void _siteInstances(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = NumberUtils.toInt(System.getenv(c.var));
		else
			o = NumberUtils.toInt(config.getString(c.var), 1);
		c.o(o);
	}

	/**	
	 *	The Keycloak realm of the site. 
	 **/
	protected void _authRealm(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = StringUtils.defaultIfBlank(config.getString(c.var), siteIdentifier);
		c.o(o);
	}

	/**	
	 *	The Keycloak client ID of the site. 
	 **/
	protected void _authResource(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = StringUtils.defaultIfBlank(config.getString(c.var), siteIdentifier);
		c.o(o);
	}

	/**	
	 *	The Keycloak client secret of the site. 
	 **/
	protected void _authSecret(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = StringUtils.defaultIfBlank(config.getString(c.var), siteIdentifier);
		c.o(o);
	}

	/**	
	 *	Whether SSL is required in Keycloak for the site. 
	 **/
	protected void _authSslRequired(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = StringUtils.defaultIfBlank(config.getString(c.var), "all");
		c.o(o);
	}

	/**	
	 * Description.enUS: Enables SSL Passthrough configuration. 
	 **/
	protected void _sslPassthrough(Wrap<Boolean> w) {
		Boolean o;
		if(config == null)
			o = BooleanUtils.toBoolean(System.getenv(w.var));
		else
			o = BooleanUtils.toBoolean(StringUtils.defaultIfBlank(config.getString(w.var), "false"));
		w.o(o);
	}

	/**	
	 *	The path to the Java keystore for the site. 
	 **/
	protected void _sslJksPath(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = StringUtils.defaultIfBlank(config.getString(c.var), siteIdentifier);
		c.o(o);
	}

	/**	
	 *	The password for the Java keystore for the site. 
	 **/
	protected void _sslJksPassword(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = StringUtils.defaultIfBlank(config.getString(c.var), siteIdentifier);
		c.o(o);
	}

	/**	
	 *	The URL to the Keycloak server. 
	 **/
	protected void _authUrl(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = StringUtils.defaultIfBlank(config.getString(c.var), siteIdentifier);
		c.o(o);
	}

	/**	
	 *	The encryption salt to use for all database encryption. 
	 **/
	protected void _encryptionSalt(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	/**	
	 *	The encryption password to use for all encryption of the database. 
	 **/
	protected void _encryptionPassword(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	/**	
	 *	The base URL for the URLs of the site. 
	 **/
	protected void _siteBaseUrl(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = StringUtils.defaultIfBlank(config.getString(c.var), "https://" + siteHostName);
		c.o(o);
	}

	/**	
	 *	The display name of the site. 
	 **/
	protected void _siteDisplayName(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = StringUtils.defaultIfBlank(config.getString(c.var), siteIdentifier);
		c.o(o);
	}

	/**	
	 *	The class name of the JDBC driver class for the database. 
	 **/
	protected void _jdbcDriverClass(Wrap<String> c) {
		String o;
		if(config == null)
			o = StringUtils.defaultIfEmpty(System.getenv(c.var), "org.postgresql.Driver");
		else
			o = config.getString(c.var, "org.postgresql.Driver");
		c.o(o);
	}

	/**	
	 *	The username for the database. 
	 **/
	protected void _jdbcUsername(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	/**	
	 *	The password for the database. 
	 **/
	protected void _jdbcPassword(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	/**	
	 *	The max pool size for the database. 
	 **/
	protected void _jdbcMaxPoolSize(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = NumberUtils.toInt(System.getenv(c.var), 10);
		else
			o = NumberUtils.toInt(config.getString(c.var), 10);
		c.o(o);
	}

	/**
	 * Set the maximum connection request allowed in the wait queue, 
	 * any requests beyond the max size will result in an failure. 
	 * If the value is set to a negative number then the queue will be unbounded. 
	 **/
	protected void _jdbcMaxWaitQueueSize(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = NumberUtils.toInt(System.getenv(c.var), -1);
		else
			o = NumberUtils.toInt(config.getString(c.var), -1);
		c.o(o);
	}

	/**	
	 *	The max pool size for the database. 
	 **/
	protected void _jdbcMinPoolSize(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = NumberUtils.toInt(System.getenv(c.var), 0);
		else
			o = NumberUtils.toInt(config.getString(c.var), 0);
		c.o(o);
	}

	/**	
	 *	The max statements for the database. 
	 **/
	protected void _jdbcMaxStatements(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = NumberUtils.toInt(System.getenv(c.var), 0);
		else
			o = NumberUtils.toInt(config.getString(c.var), 0);
		c.o(o);
	}

	/**	
	 *	The max statements per connection for the database. 
	 **/
	protected void _jdbcMaxStatementsPerConnection(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = NumberUtils.toInt(System.getenv(c.var), 0);
		else
			o = NumberUtils.toInt(config.getString(c.var), 0);
		c.o(o);
	}

	/**	
	 *	The max idle time for the database. 
	 **/
	protected void _jdbcMaxIdleTime(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = NumberUtils.toInt(System.getenv(c.var), 10);
		else
			o = NumberUtils.toInt(config.getString(c.var), 10);
		c.o(o);
	}

	/**
	 * The max idle time for the connection to the database. 
	 **/
	protected void _jdbcConnectTimeout(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = NumberUtils.toInt(System.getenv(c.var), 5000);
		else
			o = NumberUtils.toInt(config.getString(c.var), 5000);
		c.o(o);
	}

	/**	
	 *	The JDBC URL to the database. 
	 **/
	protected void _jdbcHost(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	/**
	 * The JDBC URL to the database. 
	 **/
	protected void _jdbcPort(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = Integer.parseInt(ObjectUtils.defaultIfNull(System.getenv(c.var), "5432"));
		else
			o = NumberUtils.toInt(config.getString(c.var), 5432);
		c.o(o);
	}

	/**
	 * The JDBC URL to the database. 
	 **/
	protected void _jdbcDatabase(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	/**	
	 *	The URL to the SOLR search engine. 
	 **/
	protected void _solrUrl(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	/**	
	 *	The URL to the SOLR search engine for the computate project. 
	 **/
	protected void _solrUrlComputate(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	/**	
	 *	The Facebook account for the site. 
	 **/
	protected void _accountFacebook(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	/**	
	 *	The Twitter account for the site. 
	 **/
	protected void _accountTwitter(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	/**	
	 *	The Instagram account for the site. 
	 **/
	protected void _accountInstagram(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	/**	
	 *	The Youtube account for the site. 
	 **/
	protected void _accountYoutube(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	/**	
	 *	The Pinterest account for the site. 
	 **/
	protected void _accountPinterest(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	/**	
	 *	The Email account for the site. 
	 **/
	protected void _accountEmail(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	/**	
	 *	The OpenID Connect role for an administrator. 
	 **/
	protected void _roleAdmin(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	/**	
	 *	The email address for the administrator of the site for the error reports. 
	 **/
	protected void _emailAdmin(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	/**	
	 *	The number of executors for executing background tasks in the site. 
	 **/
	protected void _numberExecutors(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = Integer.parseInt(ObjectUtils.defaultIfNull(System.getenv(c.var), "1"));
		else
			o = NumberUtils.toInt(config.getString(c.var), 1);
		c.o(o);
	}

	/**	
	 *	The version of OpenAPI used with Vert.x which should probably be 3.0. 
	 **/
	protected void _openApiVersion(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var, "3.0.0");
		c.o(o);
	}

	/**	
	 *	The description of your site API. 
	 **/
	protected void _apiDescription(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var, "2.0");
		c.o(o);
	}

	/**	
	 *	The title of your site API. 
	 **/
	protected void _apiTitle(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var, siteDisplayName);
		c.o(o);
	}

	/**	
	 *	The terms of service of your site API. 
	 **/
	protected void _apiTermsService(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var, "http://swagger.io/terms/");
		c.o(o);
	}

	/**	
	 *	The version of your site API. 
	 **/
	protected void _apiVersion(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var, "1");
		c.o(o);
	}

	/**	
	 *	The contact email of your site API. 
	 **/
	protected void _apiContactEmail(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	/**	
	 *	The open source license name of your site API. 
	 **/
	protected void _apiLicenseName(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var, "Apache 2.0");
		c.o(o);
	}

	/**	
	 *	The open source license URL of your site API. 
	 **/
	protected void _apiLicenseUrl(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var, "http://www.apache.org/licenses/LICENSE-2.0.html");
		c.o(o);
	}

	/**	
	 *	The host name of your site API. 
	 **/
	protected void _apiHostName(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var, siteHostName);
		c.o(o);
	}

	/**	
	 *	The base path of your site API. 
	 **/
	protected void _apiBasePath(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var, "/api/v" + apiVersion);
		c.o(o);
	}

	/**	
	 *	The base URL of your static files. 
	 **/
	protected void _staticBaseUrl(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = StringUtils.defaultIfBlank(config.getString(c.var), "/static");
		c.o(o);
	}

	/**	
	 *	The optional path of your static files. 
	 **/
	protected void _staticPath(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	/**	
	 *	The optional path to import files to the site. 
	 **/
	protected void _importPath(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	protected void _emailHost(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	protected void _emailPort(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = NumberUtils.toInt(System.getenv(c.var));
		else
			o = NumberUtils.toInt(config.getString(c.var), 465);
		c.o(o);
	}

	protected void _emailUsername(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	protected void _emailPassword(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	protected void _emailFrom(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(c.var);
		c.o(o);
	}

	protected void _emailAuth(Wrap<Boolean> c) {
		Boolean o;
		if(config == null)
			o = BooleanUtils.toBoolean(System.getenv(c.var));
		else
			o = BooleanUtils.toBoolean(StringUtils.defaultIfBlank(config.getString(c.var), "false"));
		c.o(o);
	}

	protected void _emailSsl(Wrap<Boolean> c) {
		Boolean o;
		if(config == null)
			o = BooleanUtils.toBoolean(System.getenv(c.var));
		else
			o = BooleanUtils.toBoolean(StringUtils.defaultIfBlank(config.getString(c.var), "false"));
		c.o(o);
	}

	/**	
	 *	The default timezone of the site. 
	 **/
	protected void _siteZone(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = StringUtils.defaultIfBlank(config.getString(c.var), ZoneId.systemDefault().getId());
		c.o(o);
	}
}
