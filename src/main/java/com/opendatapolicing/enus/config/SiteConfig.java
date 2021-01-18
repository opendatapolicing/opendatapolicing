package com.opendatapolicing.enus.config;

import java.io.File;
import java.io.Serializable;
import java.time.ZoneId;
import org.apache.commons.configuration2.INIConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import com.opendatapolicing.enus.wrap.Wrap;

/**
 * Keyword: classSimpleNameSiteConfig
 */   
public class SiteConfig extends SiteConfigGen<Object> {
	
	protected void _configPath(Wrap<String> c) {   
		String o = System.getenv("configPath");
		c.o(o);
	}
	
	protected void _config(Wrap<INIConfiguration> c) {
		if(configPath != null) {
			Configurations configurations = new Configurations();
			File configFile = new File(configPath);
			if(configFile.exists()) {
				try {
					INIConfiguration o = configurations.ini(configFile);
					c.o(o);
				} catch (ConfigurationException e) {
					ExceptionUtils.rethrow(e);
				}
			}
		}
	}
	
	protected void _siteIdentifier(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv("appName");
		else
			o = config.getString("appName");

		c.o(o);
	}
	
	protected void _prefixEscaped(Wrap<String> c) {
		String o = StringUtils.replace(siteIdentifier, ".", "..") + ".";
		c.o(o);
	}

	protected void _appPath(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var, StringUtils.substringBefore(configPath, "/config/"));

		c.o(o);
	}

	protected void _docRoot(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _companyName(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _domainName(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _siteHostName(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _sitePort(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = NumberUtils.toInt(System.getenv(c.var));
		else
			o = config.getInt(prefixEscaped + c.var, 8080);
		c.o(o);
	}

	protected void _siteInstances(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = NumberUtils.toInt(System.getenv(c.var));
		else
			o = config.getInt(prefixEscaped + c.var, 1);
		c.o(o);
	}

	protected void _authRealm(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = StringUtils.defaultIfBlank(config.getString(prefixEscaped + c.var), siteIdentifier);
		c.o(o);
	}

	protected void _authResource(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = StringUtils.defaultIfBlank(config.getString(prefixEscaped + c.var), siteIdentifier);
		c.o(o);
	}

	protected void _authSecret(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = StringUtils.defaultIfBlank(config.getString(prefixEscaped + c.var), siteIdentifier);
		c.o(o);
	}

	protected void _authSslRequired(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = StringUtils.defaultIfBlank(config.getString(prefixEscaped + c.var), "all");
		c.o(o);
	}

	protected void _sslPassthrough(Wrap<Boolean> w) {
		Boolean o;
		if(config == null)
			o = BooleanUtils.toBoolean(System.getenv(w.var));
		else
			o = BooleanUtils.toBoolean(StringUtils.defaultIfBlank(config.getString(prefixEscaped + w.var), "false"));
		w.o(o);
	}

	protected void _sslJksPath(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = StringUtils.defaultIfBlank(config.getString(prefixEscaped + c.var), siteIdentifier);
		c.o(o);
	}

	protected void _sslJksPassword(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = StringUtils.defaultIfBlank(config.getString(prefixEscaped + c.var), siteIdentifier);
		c.o(o);
	}

	protected void _authUrl(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = StringUtils.defaultIfBlank(config.getString(prefixEscaped + c.var), siteIdentifier);
		c.o(o);
	}

	protected void _encryptionSalt(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _encryptionPassword(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _siteBaseUrl(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = StringUtils.defaultIfBlank(config.getString(prefixEscaped + c.var), "https://" + siteHostName);
		c.o(o);
	}

	protected void _siteDisplayName(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = StringUtils.defaultIfBlank(config.getString(prefixEscaped + c.var), siteIdentifier);
		c.o(o);
	}

	protected void _jdbcDriverClass(Wrap<String> c) {
		String o;
		if(config == null)
			o = StringUtils.defaultIfEmpty(System.getenv(c.var), "org.postgresql.Driver");
		else
			o = config.getString(prefixEscaped + c.var, "org.postgresql.Driver");
		c.o(o);
	}

	protected void _jdbcUsername(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _jdbcPassword(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _jdbcMaxPoolSize(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = NumberUtils.toInt(System.getenv(c.var), 15);
		else
			o = config.getInt(prefixEscaped + c.var, 15);
		c.o(o);
	}

	protected void _jdbcMaxWaitQueueSize(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = NumberUtils.toInt(System.getenv(c.var), -1);
		else
			o = config.getInt(prefixEscaped + c.var, -1);
		c.o(o);
	}

	protected void _jdbcMinPoolSize(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = NumberUtils.toInt(System.getenv(c.var), 0);
		else
			o = config.getInt(prefixEscaped + c.var, 0);
		c.o(o);
	}

	protected void _jdbcMaxStatements(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = NumberUtils.toInt(System.getenv(c.var), 0);
		else
			o = config.getInt(prefixEscaped + c.var, 0);
		c.o(o);
	}

	protected void _jdbcMaxStatementsPerConnection(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = NumberUtils.toInt(System.getenv(c.var), 0);
		else
			o = config.getInt(prefixEscaped + c.var, 0);
		c.o(o);
	}

	protected void _jdbcMaxIdleTime(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = NumberUtils.toInt(System.getenv(c.var), 10);
		else
			o = config.getInt(prefixEscaped + c.var, 10);
		c.o(o);
	}

	protected void _jdbcConnectTimeout(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = NumberUtils.toInt(System.getenv(c.var), 5000);
		else
			o = config.getInt(prefixEscaped + c.var, 5000);
		c.o(o);
	}

	protected void _jdbcHost(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _jdbcPort(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = Integer.parseInt(ObjectUtils.defaultIfNull(System.getenv(c.var), "5432"));
		else
			o = config.getInt(prefixEscaped + c.var, 5432);
		c.o(o);
	}

	protected void _jdbcDatabase(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _solrUrl(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _solrUrlComputate(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _accountFacebook(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _accountTwitter(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _accountInstagram(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _accountYoutube(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _accountPinterest(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _accountEmail(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _roleAdmin(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _emailAdmin(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _numberExecutors(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = Integer.parseInt(ObjectUtils.defaultIfNull(System.getenv(c.var), "1"));
		else
			o = config.getInt(prefixEscaped + c.var, 1);
		c.o(o);
	}

	protected void _openApiVersion(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var, "3.0.0");
		c.o(o);
	}

	protected void _apiDescription(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var, "2.0");
		c.o(o);
	}

	protected void _apiTitle(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var, siteDisplayName);
		c.o(o);
	}

	protected void _apiTermsService(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var, "http://swagger.io/terms/");
		c.o(o);
	}

	protected void _apiVersion(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var, "1");
		c.o(o);
	}

	protected void _apiContactEmail(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _apiLicenseName(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var, "Apache 2.0");
		c.o(o);
	}

	protected void _apiLicenseUrl(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var, "http://www.apache.org/licenses/LICENSE-2.0.html");
		c.o(o);
	}

	protected void _apiHostName(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var, siteHostName);
		c.o(o);
	}

	protected void _apiBasePath(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var, "/api/v" + apiVersion);
		c.o(o);
	}

	protected void _staticBaseUrl(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = StringUtils.defaultIfBlank(config.getString(prefixEscaped + c.var), "/static");
		c.o(o);
	}

	protected void _staticPath(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _importPath(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _emailHost(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _emailPort(Wrap<Integer> c) {
		Integer o;
		if(config == null)
			o = NumberUtils.toInt(System.getenv(c.var));
		else
			o = config.getInt(prefixEscaped + c.var, 465);
		c.o(o);
	}

	protected void _emailUsername(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _emailPassword(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _emailFrom(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = config.getString(prefixEscaped + c.var);
		c.o(o);
	}

	protected void _emailAuth(Wrap<Boolean> c) {
		Boolean o;
		if(config == null)
			o = BooleanUtils.toBoolean(System.getenv(c.var));
		else
			o = BooleanUtils.toBoolean(StringUtils.defaultIfBlank(config.getString(prefixEscaped + c.var), "false"));
		c.o(o);
	}

	protected void _emailSsl(Wrap<Boolean> c) {
		Boolean o;
		if(config == null)
			o = BooleanUtils.toBoolean(System.getenv(c.var));
		else
			o = BooleanUtils.toBoolean(StringUtils.defaultIfBlank(config.getString(prefixEscaped + c.var), "false"));
		c.o(o);
	}

	protected void _siteZone(Wrap<String> c) {
		String o;
		if(config == null)
			o = System.getenv(c.var);
		else
			o = StringUtils.defaultIfBlank(config.getString(prefixEscaped + c.var), ZoneId.systemDefault().getId());
		c.o(o);
	}
}