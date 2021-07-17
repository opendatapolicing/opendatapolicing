package com.opendatapolicing.enus.config;  

/**
 * Keyword: classSimpleNameConfigKeys
 */
public class ConfigKeys {

	/**
	 * The path to the config file of the site. 
	 **/
	public static final String CONFIG_PATH = "configPath";

	/**
	 * The INI Configuration Object for the config file. 
	 **/
	public static final String CONFIG = "config";

	/**
	 * The name of the principal group of settings of the config for this website. 
	 **/
	public static final String SITE_IDENTIFIER = "siteIdentifier";

	/**
	 * The path to the project of the site cloned from git. 
	 **/
	public static final String APP_PATH = "appPath";

	/**
	 * The path to the basic authentication properties file with users and passwords. 
	 **/
	public static final String DOC_ROOT = "docRoot";

	/**
	 * The path to the docroot for the project. 
	 **/
	public static final String COMPANY_NAME = "companyName";

	/**
	 * The domain name of the site. 
	 **/
	public static final String DOMAIN_NAME = "domainName";

	/**
	 * The host name of the site. 
	 **/
	public static final String SITE_HOST_NAME = "siteHostName";

	/**
	 * The port of the site. 
	 **/
	public static final String SITE_PORT = "sitePort";

	/**
	 * The number of instances of the Vertx verticle to deploy. 
	 **/
	public static final String SITE_INSTANCES = "siteInstances";

	/**
	 * The Auth realm of the site. 
	 **/
	public static final String API_COUNTER_PAUSE = "apiCounterPause";

	/**
	 * 
	 **/
	public static final String API_COUNTER_RESUME = "apiCounterResume";

	/**
	 * 
	 **/
	public static final String API_COUNTER_FETCH = "apiCounterFetch";

	/**
	 * 
	 **/
	public static final String AUTH_REALM = "authRealm";

	/**
	 * The Auth client ID of the site. 
	 **/
	public static final String AUTH_RESOURCE = "authResource";

	/**
	 * The Auth client secret of the site. 
	 **/
	public static final String AUTH_SECRET = "authSecret";

	/**
	 * Whether SSL is required in Auth for the site. 
	 **/
	public static final String AUTH_SSL_REQUIRED = "authSslRequired";

	/**
	 * Enable SSL Passthrough
	 **/
	public static final String SSL_PASSTHROUGH = "sslPassthrough";

	/**
	 * The path to the Java keystore for the site. 
	 **/
	public static final String SSL_JKS_PATH = "sslJksPath";

	/**
	 * The password for the Java keystore for the site. 
	 **/
	public static final String SSL_JKS_PASSWORD = "sslJksPassword";

	/**
	 * The port to the Auth server. 
	 **/
	public static final String AUTH_PORT = "authPort";

	/**
	 * Whether the Auth server uses SSL. 
	 **/
	public static final String AUTH_SSL = "authSsl";

	/**
	 * The token URI to the Auth server. 
	 **/
	public static final String AUTH_TOKEN_URI = "authTokenUri";

	/**
	 * 
	 **/
	public static final String AUTH_HOST_NAME = "authHostName";

	/**
	 * The URL of the Auth server. 
	 **/
	public static final String AUTH_URL = "authUrl";

	/**
	 * The base URL for the URLs of the site. 
	 **/
	public static final String SITE_BASE_URL = "siteBaseUrl";

	/**
	 * The display name of the site. 
	 **/
	public static final String SITE_DISPLAY_NAME = "siteDisplayName";

	/**
	 * The class name of the JDBC driver class for the database. 
	 **/
	public static final String JDBC_DRIVER_CLASS = "jdbcDriverClass";

	/**
	 * The username for the database. 
	 **/
	public static final String JDBC_USERNAME = "jdbcUsername";

	/**
	 * The password for the database. 
	 **/
	public static final String JDBC_PASSWORD = "jdbcPassword";

	/**
	 * The max pool size for the database. 
	 **/
	public static final String JDBC_MAX_POOL_SIZE = "jdbcMaxPoolSize";

	/**
	 * Set the maximum connection request allowed in the wait queue, any requests beyond the max size will result in an failure. If the value is set to a negative number then the queue will be unbounded. 
	 **/
	public static final String JDBC_MAX_WAIT_QUEUE_SIZE = "jdbcMaxWaitQueueSize";

	/**
	 * The max pool size for the database. 
	 **/
	public static final String JDBC_MIN_POOL_SIZE = "jdbcMinPoolSize";

	/**
	 * The max statements for the database. 
	 **/
	public static final String JDBC_MAX_STATEMENTS = "jdbcMaxStatements";

	/**
	 * The max statements per connection for the database. 
	 **/
	public static final String JDBC_MAX_STATEMENTS_PER_CONNECTION = "jdbcMaxStatementsPerConnection";

	/**
	 * The max idle time for the database. 
	 **/
	public static final String JDBC_MAX_IDLE_TIME = "jdbcMaxIdleTime";

	/**
	 * The max idle time for the connection to the database. 
	 **/
	public static final String JDBC_CONNECT_TIMEOUT = "jdbcConnectTimeout";

	/**
	 * The JDBC URL to the database. 
	 **/
	public static final String JDBC_HOST = "jdbcHost";

	/**
	 * The JDBC URL to the database. 
	 **/
	public static final String JDBC_PORT = "jdbcPort";

	/**
	 * The JDBC URL to the database. 
	 **/
	public static final String JDBC_DATABASE = "jdbcDatabase";

	/**
	 * The hostname to the SOLR search engine. 
	 **/
	public static final String SOLR_HOST_NAME = "solrHostName";

	/**
	 * The port to the SOLR search engine. 
	 **/
	public static final String SOLR_PORT = "solrPort";

	/**
	 * The Solr collection. 
	 **/
	public static final String SOLR_COLLECTION = "solrCollection";

	/**
	 * The URL to the SOLR search engine for the computate project. 
	 **/
	public static final String SOLR_URL_COMPUTATE = "solrUrlComputate";

	/**
	 * The Email account for the site. 
	 **/
	public static final String ACCOUNT_EMAIL = "accountEmail";

	/**
	 * The OpenID Connect role for an administrator. 
	 **/
	public static final String ROLE_ADMIN = "roleAdmin";

	/**
	 * The email address for the administrator of the site for the error reports. 
	 **/
	public static final String EMAIL_ADMIN = "emailAdmin";

	/**
	 * The version of OpenAPI used with Vert.x which should probably be 3.0. 
	 **/
	public static final String OPEN_API_VERSION = "openApiVersion";

	/**
	 * The description of your site API. 
	 **/
	public static final String API_DESCRIPTION = "apiDescription";

	/**
	 * The title of your site API. 
	 **/
	public static final String API_TITLE = "apiTitle";

	/**
	 * The terms of service of your site API. 
	 **/
	public static final String API_TERMS_SERVICE = "apiTermsService";

	/**
	 * The version of your site API. 
	 **/
	public static final String API_VERSION = "apiVersion";

	/**
	 * The contact email of your site API. 
	 **/
	public static final String API_CONTACT_EMAIL = "apiContactEmail";

	/**
	 * The open source license name of your site API. 
	 **/
	public static final String API_LICENSE_NAME = "apiLicenseName";

	/**
	 * The open source license URL of your site API. 
	 **/
	public static final String API_LICENSE_URL = "apiLicenseUrl";

	/**
	 * The host name of your site API. 
	 **/
	public static final String API_HOST_NAME = "apiHostName";

	/**
	 * The base path of your site API. 
	 **/
	public static final String API_BASE_PATH = "apiBasePath";

	/**
	 * The base URL of your static files. 
	 **/
	public static final String STATIC_BASE_URL = "staticBaseUrl";

	/**
	 * The path to the static files for the site. 
	 **/
	public static final String STATIC_PATH = "staticPath";

	/**
	 * 
	 **/
	public static final String IMPORT_PATH = "importPath";

	/**
	 * The SMTP host name for sending email. 
	 **/
	public static final String EMAIL_HOST = "emailHost";

	/**
	 * The SMTP port for sending email. 
	 **/
	public static final String EMAIL_PORT = "emailPort";

	/**
	 * The SMTP username for sending email. 
	 **/
	public static final String EMAIL_USERNAME = "emailUsername";

	/**
	 * The SMTP password for sending email. 
	 **/
	public static final String EMAIL_PASSWORD = "emailPassword";

	/**
	 * The from address for sending email. 
	 **/
	public static final String EMAIL_FROM = "emailFrom";

	/**
	 * Whether authentication is required for sending email. 
	 **/
	public static final String EMAIL_AUTH = "emailAuth";

	/**
	 * Whether SSL is required for sending email. 
	 **/
	public static final String EMAIL_SSL = "emailSsl";

	/**
	 * The default timezone of the site. 
	 **/
	public static final String SITE_ZONE = "siteZone";

	/**
	 * 
	 **/
	public static final String TIMER_DB_SOLR_SYNC_IN_SECONDS = "timerDbSolrSyncInSeconds";

	/**
	 * 
	 **/
	public static final String ENABLE_DB_SOLR_SYNC = "enableDbSolrSync";

	/**
	 * 
	 **/
	public static final String ENABLE_REFRESH_DATA = "enableRefreshData";

	/**
	 * 
	 **/
	public static final String ENABLE_IMPORT_DATA = "ENABLE_IMPORT_DATA";

	/**
	 * 
	 **/
	public static final String WORKER_POOL_SIZE = "workerPoolSize";

	/**
	 * 
	 **/
	public static final String VERTX_WARNING_EXCEPTION_SECONDS = "vertxWarningExceptionSeconds";

	/**
	 * 
	 **/
	public static final String ZOOKEEPER_HOST_NAME = "zookeeperHostName";

	/**
	 * 
	 **/
	public static final String ZOOKEEPER_PORT = "zookeeperPort";

	/**
	 * 
	 **/
	public static final String ZOOKEEPER_HOSTS = "zookeeperHosts";

	/**
	 * 
	 **/
	public static final String ACS_API_YEAR = "ACS_API_YEAR";

	/**
	 * 
	 **/
	public static final String ACS_API_KEY = "ACS_API_KEY";
}
