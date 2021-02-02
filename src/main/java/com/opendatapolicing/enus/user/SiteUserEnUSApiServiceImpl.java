package com.opendatapolicing.enus.user;

import org.apache.commons.lang3.StringUtils;

import com.opendatapolicing.enus.context.SiteContextEnUS;
import com.opendatapolicing.enus.request.SiteRequestEnUS;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.api.OperationResponse;

/**
 * Translate: false
 **/
public class SiteUserEnUSApiServiceImpl extends SiteUserEnUSGenApiServiceImpl {

	public SiteUserEnUSApiServiceImpl(SiteContextEnUS siteContext) {
		super(siteContext);
	}

	@Override
	public void sqlPOSTSiteUser(SiteUser o, Boolean inheritPk, Handler<AsyncResult<OperationResponse>> eventHandler) {
		SiteRequestEnUS siteRequest_ = o.getSiteRequest_();
		JsonObject jsonObject = siteRequest_.getJsonObject();
		if(StringUtils.equals(o.getUserId(), siteRequest_.getUserId())) {
			jsonObject.put("userFirstName", siteRequest_.getUserFirstName());
			jsonObject.put("userLastName", siteRequest_.getUserLastName());
			jsonObject.put("userFullName", siteRequest_.getUserFullName());
			jsonObject.put("userEmail", siteRequest_.getUserEmail());
			jsonObject.put("userId", siteRequest_.getUserId());
			jsonObject.put("userName", siteRequest_.getUserName());
		}
		super.sqlPOSTSiteUser(o, inheritPk, eventHandler);
	}

	@Override
	public void sqlPATCHSiteUser(SiteUser o, Boolean inheritPk, Handler<AsyncResult<SiteUser>> eventHandler) {
		SiteRequestEnUS siteRequest_ = o.getSiteRequest_();
		JsonObject jsonObject = siteRequest_.getJsonObject();
		if(StringUtils.equals(o.getUserId(), siteRequest_.getUserId())) {
			jsonObject.put("setUserFirstName", siteRequest_.getUserFirstName());
			jsonObject.put("setUserLastName", siteRequest_.getUserLastName());
			jsonObject.put("setUserFullName", siteRequest_.getUserFullName());
			jsonObject.put("setUserEmail", siteRequest_.getUserEmail());
			jsonObject.put("setUserId", siteRequest_.getUserId());
			jsonObject.put("setUserName", siteRequest_.getUserName());
		}
		super.sqlPATCHSiteUser(o, inheritPk, eventHandler);
	}
}
