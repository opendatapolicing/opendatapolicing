package com.opendatapolicing.enus.trafficstop;

import com.opendatapolicing.enus.context.SiteContextEnUS;
import com.opendatapolicing.enus.request.SiteRequestEnUS;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

/**
 * Translate: false
 **/
public class TrafficStopEnUSApiServiceImpl extends TrafficStopEnUSGenApiServiceImpl {

	public TrafficStopEnUSApiServiceImpl(SiteContextEnUS siteContext) {
		super(siteContext);
	}

	@Override
	public void sqlPATCHTrafficStop(TrafficStop o, Boolean inheritPk,
			Handler<AsyncResult<TrafficStop>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		JsonObject jsonObject = siteRequest.getJsonObject();

		// Because the TrafficStop data does not contain the personKeys, 
		// don't set the personKeys to null when reimporting TrafficStop data. 
		jsonObject.remove("setPersonKeys");

		super.sqlPATCHTrafficStop(o, inheritPk, eventHandler);
	}
}
