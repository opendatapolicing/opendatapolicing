package com.opendatapolicing.enus.design;

import java.util.List;
import com.opendatapolicing.enus.cluster.Cluster;
import com.opendatapolicing.enus.wrap.Wrap;

/**
 * Model: true
 * Api: true
 * Indexed: true
 * Saved: true
 * 
 * ApiTag.enUS: Page Design
 * ApiUri.enUS: /api/page-design
 * 
 * ApiMethod.enUS: PUTImport
 * ApiMethod.enUS: PUTMerge
 * ApiMethod.enUS: PUTCopy
 * ApiMethod: POST
 * ApiMethod: PATCH
 * ApiMethod: GET
 * ApiMethod.enUS: Search
 * 
 * ApiMethod.enUS: AdminSearch
 * ApiUriAdminSearch.enUS: /api/admin/page-design
 * RoleUtilisateurAdminSearch.enUS: true
 * 
 * ApiMethod.enUS: SearchPage
 * PageSearchPage.enUS: PageDesignPage
 * PageSuperSearchPage.enUS: ClusterPage
 * ApiUriSearchPage.enUS: /page-design
 * 
 * ApiMethod.enUS: DesignDisplaySearchPage
 * PageDesignDisplaySearchPage.enUS: DesignDisplayPage
 * PageSuperDesignDisplaySearchPage.enUS: ClusterPage
 * ApiUriDesignDisplaySearchPage.enUS: /page
 * 
 * ApiMethod.enUS: DesignPdfSearchPage
 * PageDesignPdfSearchPage.enUS: DesignPdfPage
 * PageSuperDesignPdfSearchPage.enUS: ClusterPage
 * ApiUriDesignPdfSearchPage.enUS: /pdf
 * ApiTypeMedia200DesignPdfSearchPage: application/pdf
 * 
 * ApiMethod.enUS: DesignEmailSearchPage
 * PageDesignEmailSearchPage.enUS: DesignEmailPage
 * PageSuperDesignEmailSearchPage.enUS: ClusterPage
 * ApiUriDesignEmailSearchPage.enUS: /email
 * 
 * ApiMethod.enUS: HomePageSearchPage
 * PageHomePageSearchPage.enUS: DesignDisplayPage
 * PageSuperHomePageSearchPage.enUS: ClusterPage
 * ApiUriHomePageSearchPage.enUS: /
 * 
 * AName.enUS: a page design
 * Color: khaki
 * IconGroup: regular
 * IconName: drafting-compass
 * NameVar.frFR: design-page
 * NameVar.enUS: page-design
 * 
 * Role.frFR: SiteAdmin
 * Role.enUS: SiteAdmin
 * PublicRead: true
 * 
 * Sort.asc: pageDesignCompleteName
 * 
 * Rows: 100
 **/ 
public class PageDesign extends PageDesignGen<Cluster> {
	
	protected void _pageDesignKey(Wrap<Long> c) {
		c.o(pk);
	}

	protected void _childDesignKeys(List<Long> c) {
	}

	protected void _parentDesignKeys(List<Long> c) {
	}

	protected void _htmlPartKeys(List<Long> o) {}

	protected void _pageDesignCompleteName(Wrap<String> c) {
		c.o("enrollment design");
	}

	protected void _designHidden(Wrap<Boolean> c) {
		c.o(false);
	}

	protected void _pageContentType(Wrap<String> c) {
		c.o("text/html;charset=UTF-8");
	}

	_objectTitle(Wrap<String> c) {
		c.o(pageDesignCompleteName);
	}
}