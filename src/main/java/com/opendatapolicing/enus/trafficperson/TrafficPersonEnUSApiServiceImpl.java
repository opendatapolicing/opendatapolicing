package com.opendatapolicing.enus.trafficperson;

import com.opendatapolicing.enus.context.SiteContextEnUS;
import com.opendatapolicing.enus.request.SiteRequestEnUS;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

/**
 * Translate: false
 **/
public class TrafficPersonEnUSApiServiceImpl extends TrafficPersonEnUSGenApiServiceImpl {

	public TrafficPersonEnUSApiServiceImpl(SiteContextEnUS siteContext) {
		super(siteContext);
	}

	@Override
	public void sqlPATCHTrafficPerson(TrafficPerson o, Boolean inheritPk,
			Handler<AsyncResult<TrafficPerson>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		JsonObject jsonObject = siteRequest.getJsonObject();

		// Because the TrafficPerson data does not contain the searchKeys, 
		// don't set the searchKeys to null when reimporting TrafficPerson data. 
		jsonObject.remove("setTrafficSearchKeys");

		super.sqlPATCHTrafficPerson(o, inheritPk, eventHandler);
	}
}
