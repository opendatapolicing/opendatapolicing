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
 * ApiTag.enUS: User
 * ApiUri.enUS: /api/user
 * 
 * ApiMethod.enUS: Search
 * ApiMethod: PATCH
 * ApiMethod: POST
 * 
 * Keyword: classSimpleNameSiteUser
 * Role.enUS: SiteAdmin
 * Filter: utilisateurId
 * Saves: true
 * 
 * AName.enUS: a site user
 * Color: gray
 * IconGroup: regular
 * IconName: user-cog
 * 
 * RoleUser: true
 * Role.enUS: SiteAdmin
 */
public class SiteUser extends SiteUserGen<Cluster> {   

	/**   
	 * {@inheritDoc}
	 * Var.enUS: userKey
	 * Indexed: true
	 * Stored: true
	 * Define: true
	 * Modify: false
	 */                 
	protected void _userKey(Wrap<Long> c) {
	}

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
}
