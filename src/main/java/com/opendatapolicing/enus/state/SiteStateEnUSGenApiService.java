package com.opendatapolicing.enus.state;

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
public interface SiteStateEnUSGenApiService {
	static void registerService(SiteContextEnUS siteContext, Vertx vertx) {
		new ServiceBinder(vertx).setAddress("opendatapolicing-enUS-SiteState").register(SiteStateEnUSGenApiService.class, new SiteStateEnUSApiServiceImpl(siteContext));
	}

	static SiteStateEnUSGenApiService create(SiteContextEnUS siteContext, Vertx vertx) {
		return new SiteStateEnUSApiServiceImpl(siteContext);
	}

	// Une méthode d'usine pour créer une instance et un proxy. 
	static SiteStateEnUSGenApiService createProxy(Vertx vertx, String address) {
		return new SiteStateEnUSGenApiServiceVertxEBProxy(vertx, address);
	}

	public void putimport.enusSiteState(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void putmerge.enusSiteState(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void putcopy.enusSiteState(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void postSiteState(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void patchSiteState(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void get.enusSiteState(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchSiteState(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void adminsearch.enusSiteState(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchpage.enusSiteStateId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchpage.enusSiteState(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
}
