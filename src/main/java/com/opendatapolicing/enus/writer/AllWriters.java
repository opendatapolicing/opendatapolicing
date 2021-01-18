package com.opendatapolicing.enus.writer;

import java.io.IOException;
import java.util.List;
import com.opendatapolicing.enus.request.SiteRequestEnUS;
import com.opendatapolicing.enus.wrap.Wrap;

/**
 * Keyword: classSimpleNameAllWriters
 **/

public class AllWriters extends AllWritersGen<Object> {
    protected void _siteRequest_(Wrap<SiteRequestEnUS> c) {
    }

    public static AllWriters create(SiteRequestEnUS siteRequest_, AllWriter...writers) {
        AllWriters o = new AllWriters();
        o.initDeepForClass(siteRequest_);
        o.addWriters(writers);
        return o;
    }

    protected void _writers(List<AllWriter> c) {
    }

    protected AllWriters t(int numberTabs, Object...objects) {
        for(AllWriter writer : writers) {
            writer.t(numberTabs, objects);
        }
        return this;
    }

    protected AllWriters tl(int numberTabs, Object...objects) {
        for(AllWriter writer : writers) {
            writer.tl(numberTabs, objects);
        }
        return this;
    }

    protected AllWriters l(Object...objects) {
        for(AllWriter writer : writers) {
            writer.l(objects);
        }
        return this;
    }

    protected AllWriters s(Object...objects) {
        for(AllWriter writer : writers) {
            writer.s(objects);
        }
        return this;
    }

    protected void flushClose() {
        for(AllWriter writer : writers) {
            writer.flushClose();
        }
    }

    public String toString() {
        return writers.get(0).toString();
    }
}
