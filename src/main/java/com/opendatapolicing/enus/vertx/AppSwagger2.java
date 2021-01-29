package com.opendatapolicing.enus.vertx;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import com.opendatapolicing.enus.config.SiteConfig;
import com.opendatapolicing.enus.context.SiteContextEnUS;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.opendatapolicing.enus.wrap.Wrap;
import com.opendatapolicing.enus.writer.AllWriter;
import com.opendatapolicing.enus.writer.ApiWriter;

public class AppSwagger2 extends AppSwagger2Gen<Object> {

	public static void  main(String[] args) {
		AppSwagger2 api = new AppSwagger2();
		api.initDeepAppSwagger2();
		api.writeOpenApi();
	}

	protected void _siteRequest_(Wrap<SiteRequestEnUS> c) {
	}

	protected void _siteContext(SiteContextEnUS o) { 
	}

	protected void _siteConfig(Wrap<SiteConfig> c) {
		c.o(siteContext.getSiteConfig());
	}

	protected void _appPath(Wrap<String> c) {
		c.o(siteConfig.getAppPath());
	}

	protected void _appName(Wrap<String> c) {
		c.o("opendatapolicing");
	}

	protected void _languageName(Wrap<String> c) {
		c.o("enUS");
	}

	protected void _openApiVersion(Wrap<String> c) {
		c.o(siteConfig.getOpenApiVersion());
	}

	protected void _openApiVersionNumber(Wrap<Integer> c) {
		c.o((int)Double.parseDouble(StringUtils.substringBefore(openApiVersion, ".")));
	}

	protected void _tabsSchema(Wrap<Integer> c) {
		if(openApiVersionNumber == 2)
			c.o(1);
		else
			c.o(2);
	}

	protected void _apiVersion(Wrap<String> c) {
		c.o(siteConfig.getApiVersion());
	}

	protected void _openApiYamlPath(Wrap<String> c) {
		c.o(appPath + "/src/main/resources/" + ("2.0".equals(apiVersion) ? "swagger2" : "openapi3") + "-enUS.yaml");
	}

	protected void _openApiYamlFile(Wrap<File> c) {
		c.o(new File(openApiYamlPath));
	}

	protected void _w(Wrap<AllWriter> c) {
		c.o(AllWriter.create(siteRequest_, openApiYamlFile, "  "));
	}

	protected void _wPaths(Wrap<AllWriter> c) {
		c.o(AllWriter.create(siteRequest_, "  "));
	}

	protected void _wRequestBodies(Wrap<AllWriter> c) {
		c.o(AllWriter.create(siteRequest_, "  "));
	}

	protected void _wSchemas(Wrap<AllWriter> c) {
		c.o(AllWriter.create(siteRequest_, "  "));
	}

	List<String> classApiMethods;

	List<String> classUris;

	List<ApiWriter> apiWriters;

	String classApiTag;

	String classApiUri;

	String classSimpleName;

	String classAbsolutePath;

	Boolean classIsBase;

	Integer contextRows;

	Boolean classKeywordsFound;

	List<String> classKeywords;

	public void  writeOpenApi() {

		writeInfo();
		writeApi();

		w.s(wPaths.toString());
		w.s(wRequestBodies.toString());
		w.s(wSchemas.toString());

		w.flushClose();
	}

	public void  writeInfo() {

		if(openApiVersionNumber == 2)
			wPaths.l("swagger: \"", apiVersion, "\"");
		else
			wPaths.l("openapi: ", apiVersion);

		wPaths.l("info:");

		wPaths.t(1, "title: ").string(siteConfig.getApiTitle()).l();
//		wPaths.t(1, "description: ").yamlStr(2, siteConfig.getApiDescription());
		if(openApiVersionNumber == 2) {
			wPaths.t(1, "version: ").string(apiVersion).l();
			wPaths.t(0, "host: ").l(wPaths.js(siteConfig.getApiHostName()));
			wPaths.tl(0, "schemes:");
			wPaths.tl(1, "- \"https\"");
		}
		else if(openApiVersionNumber > 2) {
			wPaths.tl(1, "version: ", apiVersion);
			wPaths.tl(0, "servers:");
			wPaths.tl(1, "- url: ", siteConfig.getSiteBaseUrl());
		}
	}

	public void  writeApi() {
		try {
			wPaths.tl(0, "paths:");
			wPaths.l();
			wPaths.tl(1, "/callback:");
			wPaths.tl(2, "get:");
			wPaths.tl(3, "operationId: callback");
			wPaths.tl(3, "x-vertx-event-bus: ", appName, "-", languageName, "-callback");
			wPaths.tl(3, "description: >+");
			wPaths.tl(3, "responses:");
			wPaths.tl(4, "'200':");
			wPaths.tl(5, "description: >+");
			wPaths.tl(5, "content:");
			wPaths.tl(6, "application/json; charset=utf-8:");
			wPaths.tl(7, "schema:");
			wPaths.tl(8, "type: string");
			wPaths.l();
			wPaths.tl(1, "/logout:");
			wPaths.tl(2, "get:");
			wPaths.tl(3, "operationId: logout");
			wPaths.tl(3, "x-vertx-event-bus: ", appName, "-", languageName, "-logout");
			wPaths.tl(3, "description: >+");
			wPaths.tl(3, "responses:");
			wPaths.tl(4, "'200':");
			wPaths.tl(5, "description: >+");
			wPaths.tl(5, "content:");
			wPaths.tl(6, "application/json; charset=utf-8:");
			wPaths.tl(7, "schema:");
			wPaths.tl(8, "type: string");
			wPaths.l();
			wPaths.tl(1, "/photo:");
			wPaths.tl(2, "post:");
			wPaths.tl(3, "operationId: photo");
			wPaths.tl(3, "x-vertx-event-bus: ", appName, "-", languageName, "-photo");
			wPaths.tl(3, "description: >+");
			wPaths.tl(3, "requestBody:");
			wPaths.tl(4, "required: true");
			wPaths.tl(4, "content:");
			wPaths.tl(5, "multipart/form-data:");
			wPaths.tl(6, "schema:");
			wPaths.tl(7, "type: object");
			wPaths.tl(7, "properties:");
			wPaths.tl(8, "pk:");
			wPaths.tl(9, "type: string");
			wPaths.tl(8, "classeNomCanonique:");
			wPaths.tl(9, "type: string");
			wPaths.tl(8, "photo:");
			wPaths.tl(9, "type: string");
			wPaths.tl(9, "format: binary");
			wPaths.tl(3, "responses:");
			wPaths.tl(4, "'200':");
			wPaths.tl(5, "description: >+");
			wPaths.tl(5, "content:");
			wPaths.tl(6, "application/json; charset=utf-8:");
			wPaths.tl(7, "schema:");
			wPaths.tl(8, "type: string");
			wPaths.l();

//		  /callback:
//		    get:
//		      operationId: callback
//		      x-vertx-event-bus: opendatapolicing-enUS-School
//		      responses:
//		        '200':
//		          description: >+
//		          content:
//		            application/json; charset=utf-8:
//		              schema:
//		                type: string
//		  /logout:
//		    get:
//		      operationId: logout
//		      x-vertx-event-bus: opendatapolicing-enUS-School
//		      responses:
//		        '200':
//		          description: >+
//		          content:
//		            application/json; charset=utf-8:
//		              schema:
//		                type: string

			if(openApiVersionNumber == 2) {
				wSchemas.tl(0, "definitions:");
			}
			else {
				wRequestBodies.tl(0, "components:");
				if(siteConfig.getAuthUrl() != null) {
					wRequestBodies.tl(1, "securitySchemes:");
						wRequestBodies.tl(2, "openIdConnect:");
						wRequestBodies.tl(3, "type: openIdConnect");
						wRequestBodies.tl(3, "openIdConnectUrl: ", siteConfig.getAuthUrl(), "/realms/", siteConfig.getAuthRealm(), "/.well-known/openid-configuration");
				}
				wRequestBodies.tl(1, "requestBodies:");
				wRequestBodies.tl(2, "ErrorResponse:");
				wRequestBodies.tl(3, "content:");
				wRequestBodies.tl(4, "application/json:");
				wRequestBodies.tl(5, "schema:");
				wRequestBodies.tl(6, "$ref: '#/components/schemas/ErrorResponse'");

				wSchemas.tl(1, "schemas:");
			}

			SolrQuery searchClasses = new SolrQuery();
			searchClasses.setQuery("*:*");
			searchClasses.setRows(1000000);
			searchClasses.addFilterQuery("appliChemin_indexed_string:" + ClientUtils.escapeQueryChars(appPath));
			searchClasses.addFilterQuery("classeApi_indexed_boolean:true");
			searchClasses.addFilterQuery("partEstClasse_indexed_boolean:true");
			searchClasses.addSort("partNumero_indexed_int", ORDER.asc);
			QueryResponse searchClassesResponse = siteContext.getSolrClientComputate().query(searchClasses);
			SolrDocumentList searchClassesResultats = searchClassesResponse.getResults();
			Integer searchClassesLines = searchClasses.getRows();
			for(Long i = searchClassesResultats.getStart(); i < searchClassesResultats.getNumFound(); i+=searchClassesLines) {
				for(Integer j = 0; j < searchClassesResultats.size(); j++) {
					SolrDocument classSolrDocument = searchClassesResultats.get(j);

					classApiTag = StringUtils.defaultIfBlank((String)classSolrDocument.get("classeApiTag_enUS_stored_string"), classSimpleName + " API");
					classApiUri = (String)classSolrDocument.get("classeApiUri_enUS_stored_string");
					classIsBase = (Boolean)classSolrDocument.get("classeEstBase_stored_boolean");
					contextRows = (Integer)classSolrDocument.get("contexteRows_stored_int");

					classApiMethods = (List<String>)classSolrDocument.get("classeApiMethodes_enUS_stored_strings");
					classUris = new ArrayList<>();
					if(classApiMethods == null)
						classApiMethods = new ArrayList<>();
					apiWriters = new ArrayList<>();

					for(String classApiMethode : classApiMethods) {
						ApiWriter apiWriter = new ApiWriter();
						apiWriter.setClassSolrDocument(classSolrDocument);
						apiWriter.setClassApiMethod(classApiMethode);
						apiWriter.setContextRows(contextRows);
						apiWriter.setWPaths(wPaths);
						apiWriter.setWRequestBodies(wRequestBodies);
						apiWriter.setWSchemas(wSchemas);
						apiWriter.setOpenApiVersion(openApiVersion);
						apiWriter.setAppSwagger2(this);
						apiWriter.setClassUris(classUris);
						apiWriter.initDeepApiWriter(siteRequest_);
						apiWriters.add(apiWriter);
					}
					Collections.sort(apiWriters);

					classSimpleName = (String)classSolrDocument.get("classSimpleName_enUS_stored_string");
					classAbsolutePath = (String)classSolrDocument.get("classeCheminAbsolu_stored_string");

					classKeywordsFound = BooleanUtils.isTrue((Boolean)classSolrDocument.get("classeMotsClesTrouves_stored_boolean"));
					classKeywords = (List<String>)classSolrDocument.get("classeMotsCles_stored_strings");

					SolrQuery searchEntites = new SolrQuery();
					searchEntites.setQuery("*:*");
					searchEntites.setRows(1000000);
					searchEntites.addFilterQuery("appliChemin_indexed_string:" + ClientUtils.escapeQueryChars(siteConfig.getAppPath()));
					searchEntites.addFilterQuery("classeCheminAbsolu_indexed_string:" + ClientUtils.escapeQueryChars(classAbsolutePath));
					searchEntites.addFilterQuery("partEstEntite_indexed_boolean:true");
					searchEntites.addSort("partNumero_indexed_int", ORDER.asc);
					QueryResponse searchEntitesResponse = siteContext.getSolrClientComputate().query(searchEntites);
					SolrDocumentList searchEntitesResults = searchEntitesResponse.getResults();
					Integer searchEntitesLines = searchEntites.getRows();

					for(Long k = searchEntitesResults.getStart(); k < searchEntitesResults.getNumFound(); k+=searchEntitesLines) {
						for(Integer l = 0; l < searchEntitesResults.size(); l++) {
							for(ApiWriter apiWriter : apiWriters) {
								SolrDocument entiteDocumentSolr = searchEntitesResults.get(l);

								apiWriter.initEntity(entiteDocumentSolr);
								apiWriter.writeEntityHeaders();
								apiWriter.writeEntitySchema(null);
							}
						}
						searchEntites.setStart(i.intValue() + searchEntitesLines);
						searchEntitesResponse = siteContext.getSolrClientComputate().query(searchEntites);
						searchEntitesResults = searchEntitesResponse.getResults();
						searchEntitesLines = searchEntites.getRows();
					}
					for(ApiWriter apiWriter : apiWriters) {
						apiWriter.getWriters().flushClose();
						apiWriter.writeApi(false);
					}

					for(ApiWriter apiWriter : apiWriters) {
						apiWriter.getWResponseDescription().flushClose();
					}
				}
				searchClasses.setStart(i.intValue() + searchClassesLines);
				searchClassesResponse = siteContext.getSolrClientComputate().query(searchClasses);
				searchClassesResultats = searchClassesResponse.getResults();
				searchClassesLines = searchClasses.getRows();
			}
			wSchemas.tl(tabsSchema, "ErrorResponse:");
			wSchemas.tl(tabsSchema + 1, "required:");
			wSchemas.tl(tabsSchema + 2, "- type");
			wSchemas.tl(tabsSchema + 2, "- code");
			wSchemas.tl(tabsSchema + 1, "properties:");
			wSchemas.tl(tabsSchema + 2, "type:");
			wSchemas.tl(tabsSchema + 3, "type: string");
			wSchemas.tl(tabsSchema + 3, "enum:");
			wSchemas.tl(tabsSchema + 4, "- ERROR");
			wSchemas.tl(tabsSchema + 4, "- WARN");
			wSchemas.tl(tabsSchema + 4, "- INVALID");
			wSchemas.tl(tabsSchema + 4, "- FATAL");
			wSchemas.t(tabsSchema + 3, "description: ").yamlStr(tabsSchema + 4, "<br>invalid - Request did not confirm to the specification and was unprocessed & rejected. Please fix the value and try again</br>                         <br>warn - Request was partially processed.  E.g. some of the fields are missing in response to the system issues,  request was accepted successfully but will be processed asynchronously</br>                                                          <br>error - The request was accepted but could not be processed successfully</br>            <br>fatal - There was an internal system error while processing the request. These are technical errors and will be resolved by Citi, and the consumer should retry after some time. Business errors will not be categorized as fatal </br>");
			wSchemas.tl(tabsSchema + 2, "code:");
			wSchemas.tl(tabsSchema + 3, "type: string");
			wSchemas.tl(tabsSchema + 3, "description: Error code which qualifies the error. ");
			wSchemas.tl(tabsSchema + 2, "details:");
			wSchemas.tl(tabsSchema + 3, "type: string");
			wSchemas.tl(tabsSchema + 3, "description: Human readable explanation specific to the occurrence of the problem. ");
			wSchemas.tl(tabsSchema + 2, "location:");
			wSchemas.tl(tabsSchema + 3, "type: string");
			wSchemas.tl(tabsSchema + 3, "description: The name of the field that resulted in the error. ");
			wSchemas.tl(tabsSchema + 2, "moreInfo:");
			wSchemas.tl(tabsSchema + 3, "type: string");
			wSchemas.tl(tabsSchema + 3, "description: URI to human readable documentation of the error. ");
		} catch (Exception e) {
			ExceptionUtils.rethrow(e);
		}
	}
}
