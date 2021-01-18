package com.opendatapolicing.enus.vertx;

import com.opendatapolicing.enus.config.SiteConfig;
import com.opendatapolicing.enus.context.SiteContextEnUS;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.opendatapolicing.enus.wrap.Wrap;

/**
 **/

public class AppPopulate extends AppPopulateGen<Object> {
    protected void _siteRequest_(Wrap<SiteRequestEnUS> c) {
    }

    protected void _siteContext(SiteContextEnUS o) {
    }

    protected void _siteConfig(Wrap<SiteConfig> c) {
        c.o(siteContext.getSiteConfig());
    }

    public static void main(String[] args) {
        AppPopulate api = new AppPopulate();
        api.initDeepAppPopulate();
        api.populateData();
    }

    protected void populateData() {

    }
}
