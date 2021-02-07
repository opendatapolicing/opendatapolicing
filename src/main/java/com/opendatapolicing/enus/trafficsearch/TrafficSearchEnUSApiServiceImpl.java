package com.opendatapolicing.enus.trafficsearch;

import com.opendatapolicing.enus.context.SiteContextEnUS;
import com.opendatapolicing.enus.request.SiteRequestEnUS;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

/**
 * Translate: false
 **/
public class TrafficSearchEnUSApiServiceImpl extends TrafficSearchEnUSGenApiServiceImpl {

	public TrafficSearchEnUSApiServiceImpl(SiteContextEnUS siteContext) {
		super(siteContext);
	}

	@Override
	public void sqlPATCHTrafficSearch(TrafficSearch o, Boolean inheritPk,
			Handler<AsyncResult<TrafficSearch>> eventHandler) {
		SiteRequestEnUS siteRequest = o.getSiteRequest_();
		JsonObject jsonObject = siteRequest.getJsonObject();

		// Because the TrafficSearch data does not contain the contrabandKeys, 
		// don't set the contrabandKeys to null when reimporting TrafficSearch data. 
		jsonObject.remove("setContrabandKeys");

		// Because the TrafficSearch data does not contain the searchBasisKeys, 
		// don't set the searchBasisKeys to null when reimporting TrafficSearch data. 
		jsonObject.remove("setSearchBasisKeys");

		super.sqlPATCHTrafficSearch(o, inheritPk, eventHandler);
	}
}
