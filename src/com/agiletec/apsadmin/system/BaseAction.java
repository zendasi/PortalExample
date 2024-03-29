/*
*
* Copyright 2005 AgileTec s.r.l. (http://www.agiletec.it) All rights reserved.
*
* This file is part of jAPS software.
* jAPS is a free software; 
* you can redistribute it and/or modify it
* under the terms of the GNU General Public License (GPL) as published by the Free Software Foundation; version 2.
* 
* See the file License for the specific language governing permissions   
* and limitations under the License
* 
* 
* 
* Copyright 2005 AgileTec s.r.l. (http://www.agiletec.it) All rights reserved.
*
*/
package com.agiletec.apsadmin.system;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.SystemConstants;
import com.agiletec.aps.system.services.authorization.IAuthorizationManager;
import com.agiletec.aps.system.services.group.Group;
import com.agiletec.aps.system.services.lang.ILangManager;
import com.agiletec.aps.system.services.lang.Lang;
import com.agiletec.aps.system.services.role.Permission;
import com.agiletec.aps.system.services.user.UserDetails;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Class beneath all actions.
 * @author E.Santoboni
 */
public class BaseAction extends ActionSupport implements ServletRequestAware, ParameterAware {
	
	/**
	 * Check if the current user belongs to the given group. It always returns true if the user
	 * belongs to the Administrators group.
	 * @param groupName The name of the group to check against the current user.
	 * @return true if the user belongs to the given group, false otherwise.
	 */
	protected boolean isCurrentUserMemberOf(String groupName) {
		UserDetails currentUser = this.getCurrentUser();
		IAuthorizationManager authManager = this.getAuthorizationManager();
		boolean isAuth = authManager.isAuthOnGroup(currentUser, groupName) || authManager.isAuthOnGroup(currentUser, Group.ADMINS_GROUP_NAME);
		return isAuth;
	}
	
	/**
	 * Check if the current user has the given permission granted. It always returns true if the 
	 * user has the the "superuser" permission set in some role.
	 * @param permissionName The name of the permission to check against the current user.
	 * @return true if the user has the permission granted, false otherwise.
	 */
	protected boolean hasCurrentUserPermission(String permissionName) {
		UserDetails currentUser = this.getCurrentUser();
		IAuthorizationManager authManager = this.getAuthorizationManager();
		boolean isAuth = authManager.isAuthOnPermission(currentUser, permissionName) || authManager.isAuthOnPermission(currentUser, Permission.SUPERUSER);
		return isAuth;
	}
	
	protected UserDetails getCurrentUser() {
		UserDetails currentUser = (UserDetails) this.getRequest().getSession().getAttribute(SystemConstants.SESSIONPARAM_CURRENT_USER);
		return currentUser;
	}
	
	public void setParameters(Map<String, String[]> params) {
		this._params = params;
	}
	
	protected Map<String, String[]> getParameters() {
		return this._params;
	}
	
	protected String getParameter(String paramName) {
		Object param = this.getParameters().get(paramName);
		if (param != null && param instanceof String[]) {
			return ((String[])param)[0];
		} else if (param instanceof String) {
			return (String)param;
		}
		return null;
	}
	
	/**
	 * Return the current system language used in the back-end interface. If this language does not
	 * belong to those known by the system the default language is returned. A log line will 
	 * report the problem.
	 * @return The current language.
	 */
	public Lang getCurrentLang() {
		Locale locale = this.getLocale();
		String langCode = locale.getLanguage();
		Lang currentLang = this.getLangManager().getLang(langCode);
		if (null != currentLang) {
			return currentLang;
		} else {
			ApsSystemUtils.getLogger().info("Lingua richiesta '" + langCode + "' non definita nel sistema");
			return this.getLangManager().getDefaultLang();
		}
	}
	
	/**
	 * Return a title by current lang.
	 * @param defaultValue The default value returned in case there is no valid title in properties.
	 * @param titles The titles.
	 * @return The title.
	 */
	public String getTitle(String defaultValue, Properties titles) {
		if (null == titles) return defaultValue;
		String title = null;
		Lang currentLang = this.getCurrentLang();
		title = titles.getProperty(currentLang.getCode());
		if (null == title) {
			Lang defaultLang = this.getLangManager().getDefaultLang();
			title = titles.getProperty(defaultLang.getCode());
		}
		if (null == title) {
			title = defaultValue;
		}
		return title;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this._request = request;
	}
	
	protected HttpServletRequest getRequest() {
		return _request;
	}
	
	protected ILangManager getLangManager() {
		return _langManager;
	}
	public void setLangManager(ILangManager langManager) {
		this._langManager = langManager;
	}
	
	@Deprecated
	protected IAuthorizationManager getAuthManager() {
		return _authorizationManager;
	}
	protected IAuthorizationManager getAuthorizationManager() {
		return _authorizationManager;
	}
	public void setAuthorizationManager(IAuthorizationManager authorizationManager) {
		this._authorizationManager = authorizationManager;
	}
	
	/**
	 * @uml.property  name="_langManager"
	 * @uml.associationEnd  
	 */
	private ILangManager _langManager;
	
	/**
	 * @uml.property  name="_authorizationManager"
	 * @uml.associationEnd  
	 */
	private IAuthorizationManager _authorizationManager;
	
	public static final String FAILURE = "failure";
	
	/**
	 * @uml.property  name="_request"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
	private HttpServletRequest _request;
	/**
	 * @uml.property  name="_params"
	 * @uml.associationEnd  multiplicity="(0 -1)" ordering="true" elementType="java.lang.String" qualifier="paramName:java.lang.String [Ljava.lang.String;"
	 */
	private Map<String, String[]> _params;
	
	public static final String USER_NOT_ALLOWED = "userNotAllowed";
	
}
