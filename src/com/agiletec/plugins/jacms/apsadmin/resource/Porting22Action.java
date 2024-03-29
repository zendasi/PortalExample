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
package com.agiletec.plugins.jacms.apsadmin.resource;

import java.util.ArrayList;
import java.util.List;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.services.group.Group;
import com.agiletec.aps.system.services.group.IGroupManager;
import com.agiletec.apsadmin.system.BaseAction;
import com.agiletec.plugins.jacms.aps.system.services.resource.IResourceManager;

/**
 * Classe action used to improve the porting from jAPS 2.0.x to version 2.2.x
 * @author E.Santoboni
 */
public class Porting22Action extends BaseAction {
	
	public String showResources() {
		try {
			List<String> groupCodes = new ArrayList<String>();
			List<Group> systemGroups = this.getGroupManager().getGroups();
			for (int i = 0; i < systemGroups.size(); i++) {
				groupCodes.add(systemGroups.get(i).getName());
			}
			List<String> resourcesId = this.getResourceManager().searchResourcesId(null, null, null, groupCodes);
			this.setResourcesId(resourcesId);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "showResources");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	public String executeRefresh() {
		try {
			this.getResourceManager().refreshMasterFileNames();
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "startReload");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	public List<String> getResourcesId() {
		return _resourcesId;
	}
	public void setResourcesId(List<String> resourcesId) {
		this._resourcesId = resourcesId;
	}
	
	protected IResourceManager getResourceManager() {
		return _resourceManager;
	}
	public void setResourceManager(IResourceManager resourceManager) {
		this._resourceManager = resourceManager;
	}
	
	protected IGroupManager getGroupManager() {
		return _groupManager;
	}
	public void setGroupManager(IGroupManager groupManager) {
		this._groupManager = groupManager;
	}
	
	/**
	 * @uml.property  name="_resourcesId"
	 */
	private List<String> _resourcesId;
	/**
	 * @uml.property  name="_resourceManager"
	 * @uml.associationEnd  
	 */
	private IResourceManager _resourceManager;
	/**
	 * @uml.property  name="_groupManager"
	 * @uml.associationEnd  
	 */
	private IGroupManager _groupManager;
	
}