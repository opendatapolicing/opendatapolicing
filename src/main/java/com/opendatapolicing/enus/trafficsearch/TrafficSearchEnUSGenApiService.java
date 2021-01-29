package com.opendatapolicing.enus.trafficsearch;

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
public interface TrafficSearchEnUSGenApiService {
	static void registerService(SiteContextEnUS siteContext, Vertx vertx) {
		new ServiceBinder(vertx).setAddress("opendatapolicing-enUS-TrafficSearch").register(TrafficSearchEnUSGenApiService.class, new TrafficSearchEnUSApiServiceImpl(siteContext));
	}

	static TrafficSearchEnUSGenApiService create(SiteContextEnUS siteContext, Vertx vertx) {
		return new TrafficSearchEnUSApiServiceImpl(siteContext);
	}

	// Une méthode d'usine pour créer une instance et un proxy. 
	static TrafficSearchEnUSGenApiService createProxy(Vertx vertx, String address) {
		return new TrafficSearchEnUSGenApiServiceVertxEBProxy(vertx, address);
	}

	public void putimportTrafficSearch(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void putmergeTrafficSearch(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void putcopyTrafficSearch(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void postTrafficSearch(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void patchTrafficSearch(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void getTrafficSearch(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchTrafficSearch(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void adminsearchTrafficSearch(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchpageTrafficSearchId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchpageTrafficSearch(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
}
