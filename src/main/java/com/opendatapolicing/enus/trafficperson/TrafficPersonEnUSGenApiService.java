package com.opendatapolicing.enus.trafficperson;

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
public interface TrafficPersonEnUSGenApiService {
	static void registerService(SiteContextEnUS siteContext, Vertx vertx) {
		new ServiceBinder(vertx).setAddress("opendatapolicing-enUS-TrafficPerson").register(TrafficPersonEnUSGenApiService.class, new TrafficPersonEnUSApiServiceImpl(siteContext));
	}

	static TrafficPersonEnUSGenApiService create(SiteContextEnUS siteContext, Vertx vertx) {
		return new TrafficPersonEnUSApiServiceImpl(siteContext);
	}

	// Une méthode d'usine pour créer une instance et un proxy. 
	static TrafficPersonEnUSGenApiService createProxy(Vertx vertx, String address) {
		return new TrafficPersonEnUSGenApiServiceVertxEBProxy(vertx, address);
	}

	public void putimport.enusTrafficPerson(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void putmerge.enusTrafficPerson(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void putcopy.enusTrafficPerson(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void postTrafficPerson(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void patchTrafficPerson(JsonObject body, OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void get.enusTrafficPerson(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchTrafficPerson(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void adminsearch.enusTrafficPerson(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchpage.enusTrafficPersonId(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
	public void searchpage.enusTrafficPerson(OperationRequest operationRequest, Handler<AsyncResult<OperationResponse>> eventHandler);
}
