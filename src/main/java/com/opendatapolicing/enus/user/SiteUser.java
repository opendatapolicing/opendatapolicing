package com.opendatapolicing.enus.user;

import java.util.List;
import com.opendatapolicing.enus.cluster.Cluster;
import com.opendatapolicing.enus.wrap.Wrap;

/**
 * Model: true
 * Stored: true
 * Indexed: true
 * Saved: true

 * ApiUri.enUS: /api/user
 * ApiTag.enUS: User

 * ApiMethod: Search

 * ApiMethod: PATCH

 * ApiMethod: POST

 * Keyword: classSimpleNameSiteUser
 * ApiMethod: SearchPage.enUS
 * ApiUriSearchPage.enUS: /user
 * PageSearchPage.enUS: SiteUserPage
 * PageSuperSearchPage.enUS: SiteUserPage

 * AName.enUS: a site user
 * Color:
 * IconGroup: regular
 * IconName: user-cog
 * NameVar: siteUser
 **/
public class SiteUser extends SiteUserGen<Cluster> {
    protected void _userKeys(List<Long> l) {
        l.add(pk);
    }

    protected void _userId(Wrap<String> c) {
    }

    protected void _userKey(Wrap<Long> c) {
    }

    protected void _userName(Wrap<String> c) {
    }

    protected void _userEmail(Wrap<String> c) {
    }

    protected void _userFirstName(Wrap<String> c) {
    }

    protected void _userLastName(Wrap<String> c) {
    }

    protected void _userFullName(Wrap<String> c) {
    }

    protected void _userSite(Wrap<String> c) {
    }

    protected void _customerProfileId(Wrap<String> c) {
    }

    protected void _userReceiveEmails(Wrap<Boolean> c) {
        c.o(false);
    }

    protected void _seeArchived(Wrap<Boolean> c) {
        c.o(false);
    }

    protected void _seeDeleted(Wrap<Boolean> c) {
        c.o(false);
    }

    protected void _objectTitle(Wrap<String> c) {
        c.o(userFullName + " " + userEmail + " " + userName);
    }
}
