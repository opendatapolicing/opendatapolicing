package com.opendatapolicing.enus.user;        

import java.util.List;

import com.opendatapolicing.enus.cluster.Cluster;
import com.opendatapolicing.enus.wrap.Wrap;


/**   
 * CanonicalName.enUS: org.computate.scolaire.enUS.user.SiteUser
 * 
 * Model: true
 * Api: true
 * Indexed: true
 * Map.Integer.sqlSort: 3
 * 
 * ApiTag.frFR: Utilisateur
 * ApiUri.frFR: /api/utilisateur
 * 
 * ApiTag.enUS: User
 * ApiUri.enUS: /api/user
 * 
 * ApiMethod.frFR: Recherche
 * ApiMethod.enUS: Search
 * ApiMethod: PATCH
 * ApiMethod: POST
 * 
 * ApiMethod.enUS: SearchPage
 * PageSearchPage.enUS: SiteUserPage
 * SuperPageSearchPage.enUS: ClusterPage
 * ApiUriSearchPage.enUS: /user
 * 
 * Keyword: classSimpleNameSiteUser
 * Role.frFR: SiteAdmin
 * Role.enUS: SiteAdmin
 * Filter: utilisateurId
 * Saves: true
 * 
 * AName.frFR: un utilisateur du site
 * AName.enUS: a site user
 * PluralName.frFR: utilisateurs du site
 * Coulor: gray
 * IconGroup: regular
 * IconName: user-cog
 * 
 * RoleUser: true
 * Role.frFR: SiteAdmin
 * Role.enUS: SiteAdmin
 */           
public class SiteUser extends SiteUserGen<Cluster> {   

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 */
	protected void _userKeys(List<Long> l) {
		l.add(pk);
	}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * DisplayName: user ID
	 */
	protected void _userId(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 */
	protected void _userName(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 */
	protected void _userEmail(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 */
	protected void _userFirstName(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 */
	protected void _userLastName(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 */
	protected void _userFullName(Wrap<String> c) {
	}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 3
	 * HtmlCell: 2
	 * DisplayName.enUS: see archived
	 */
	protected void _seeArchived(Wrap<Boolean> c) {
		c.o(false);
	}

	/**
	 * {@inheritDoc}
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * HtmlRow: 3
	 * HtmlCell: 3
	 * DisplayName.enUS: see deleted
	 */
	protected void _seeDeleted(Wrap<Boolean> c) {
		c.o(false);
	}

	@Override protected void  _objectTitle(Wrap<String> c) {
		c.o(userFullName + " " + userName);
	}
}
