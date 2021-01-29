package com.opendatapolicing.enus.trafficcontraband;

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
public interface TrafficContrabandEnUSGenApiService {
	static void registerService(SiteContextEnUS siteContext, Vertx vertx) {
		new ServiceBinder(vertx).setAddress("opendatapolicing-enUS-TrafficContraband").register(TrafficContrabandEnUSGenApiService.class, new TrafficContrabandEnUSApiServiceImpl(siteContext));
	}

	static TrafficContrabandEnUSGenApiService create(SiteContextEnUS siteContext, Vertx vertx) {
		return new TrafficContrabandEnUSApiServiceImpl(siteContext);
	}

	// Une méthode d'usine pour créer une instance et un proxy. 
	static TrafficContrabandEnUSGenApiService createProxy(Vertx vertx, String address) {
		return new TrafficContrabandEnUSGenApiServiceVertxEBProxy(vertx, address);
	}

	public void putimportTrafficContraband(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void putmergeTrafficContraband(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void putcopyTrafficContraband(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void postTrafficContraband(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void patchTrafficContraband(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void getTrafficContraband(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchTrafficContraband(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void adminsearchTrafficContraband(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchpageTrafficContrabandId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchpageTrafficContraband(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
}
