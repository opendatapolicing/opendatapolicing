package com.opendatapolicing.enus.state;

import java.util.List;
import com.opendatapolicing.enus.cluster.Cluster;
import com.opendatapolicing.enus.wrap.Wrap;

/**
 * Model: true
 * Stored: true
 * Indexed: true
 * Saved: true

 * ApiUri.enUS: /api/state
 * ApiTag.enUS: State

 * ApiMethod: PUTImport.enUS
 * ApiUriPUTImport.enUS: /api/state/import

 * ApiMethod: PUTMerge.enUS
 * ApiUriPUTMerge.enUS: /api/state/merge

 * ApiMethod: PUTCopy.enUS
 * ApiUriPUTCopy.enUS: /api/state/copy

 * ApiMethod: POST

 * ApiMethod: PATCH

 * ApiMethod: GET.enUS
 * ApiUriGET.enUS: /api/state/{id}

 * ApiMethod: Search

 * ApiMethod: AdminSearch.enUS
 * ApiUriAdminSearch.enUS: /api/admin/state

 * ApiMethod: SearchPage.enUS
 * ApiUriSearchPage.enUS: /state
 * PageSearchPage.enUS: SiteStatePage
 * PageSuperSearchPage.enUS: SiteStatePage

 * AName.enUS: a state
 * Color: pale-blue
 * IconGroup: regular
 * IconName: globe-americas
 * NameVar: state

 * Sort.asc: stateName

 * Rows: 100
 **/

public class SiteState extends SiteStateGen<Cluster> {
    protected void _stateKey(Wrap<Long> c) {
        c.o(pk);
    }

    protected void _stateName(Wrap<String> c) {
    }

    protected void _stateAbbreviation(Wrap<String> c) {
    }

    protected void _imageLeft(Wrap<Integer> c) {
    }

    protected void _imageTop(Wrap<Integer> c) {
    }

    protected void _agencyKeys(List<Long> o) {}

    protected void _stateCompleteName(Wrap<String> c) {
        c.o(stateName + " (" + stateAbbreviation + ")");
    }

    protected void _objectTitle(Wrap<String> c) {
        c.o(stateName + " (" + stateAbbreviation + ")");
    }
}
