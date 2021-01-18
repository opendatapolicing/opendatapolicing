package com.opendatapolicing.enus.design;

import com.opendatapolicing.enus.context.SiteContextEnUS;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.ext.web.api.generator.WebApiServiceGen;
import io.vertx.serviceproxy.ServiceBinder;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.web.api.OperationRequest;
import io.vertx.ext.web.api.OperationResponse;

/**
 * Translate: false
 * Gen: false
 **/
@WebApiServiceGen
@ProxyGen
public interface PageDesignEnUSGenApiService {
	static void registerService(SiteContextEnUS siteContext, Vertx vertx) {
		new ServiceBinder(vertx).setAddress("opendatapolicing-enUS-PageDesign").register(PageDesignEnUSGenApiService.class, new PageDesignEnUSApiServiceImpl(siteContext));
	}

	static PageDesignEnUSGenApiService create(SiteContextEnUS siteContext, Vertx vertx) {
		return new PageDesignEnUSApiServiceImpl(siteContext);
	}

	// Une méthode d'usine pour créer une instance et un proxy. 
	static PageDesignEnUSGenApiService createProxy(Vertx vertx, String address) {
		return new PageDesignEnUSGenApiServiceVertxEBProxy(vertx, address);
	}

	public void putimportPageDesign(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void putmergePageDesign(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void putcopyPageDesign(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void postPageDesign(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void patchPageDesign(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void getPageDesign(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchPageDesign(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void adminsearchPageDesign(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchpagePageDesignId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchpagePageDesign(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void designdisplaysearchpagePageDesignId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void designdisplaysearchpagePageDesign(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void designpdfsearchpagePageDesignId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void designpdfsearchpagePageDesign(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void designemailsearchpagePageDesignId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void designemailsearchpagePageDesign(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void homepagesearchpagePageDesignId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void homepagesearchpagePageDesign(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
}
