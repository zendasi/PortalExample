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
package com.agiletec.plugins.jacms.aps.system.services.resource;

import java.util.List;

import com.agiletec.aps.system.ApsSystemUtils;

/**
 * Thread Class delegate to execute resource refresh operations.
 * The recognized operation is the reloading of the master file name (to improve the porting from jAPS 2.0.x to version 2.2.x) 
 * and the refreshing of resource instances.
 * @author E.Santoboni
 */
public class ResourceReloaderThread extends Thread {
	
	public ResourceReloaderThread(ResourceManager resourceManager, int operationCode, List<String> resources) {
		this._resourceManager = resourceManager;
		this.setOperationCode(operationCode);
		this.setResources(resources);
	}
	
	@Override
	public void run() {
		if (null == this.getResources()) return;
		if (this.getOperationCode() == RELOAD_MASTER_FILE_NAME) {
			this._resourceManager.setStatus(IResourceManager.STATUS_RELOADING_RESOURCE_MAIN_FILENAME_IN_PROGRESS);
		} else if (this.getOperationCode() == REFRESH_INSTANCE) {
			this._resourceManager.setStatus(IResourceManager.STATUS_RELOADING_RESOURCE_INSTANCES_IN_PROGRESS);
		}
		try {
			for (int i = 0; i < this.getResources().size(); i++) {
				String resourceId = this.getResources().get(i);
				if (this.getOperationCode() == RELOAD_MASTER_FILE_NAME) {
					this._resourceManager.refreshMasterFileNames(resourceId);
				} else if (this.getOperationCode() == REFRESH_INSTANCE) {
					this._resourceManager.refreshResourceInstances(resourceId);
				}
			}
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "run");
		} finally {
			this._resourceManager.setStatus(IResourceManager.STATUS_READY);
		}
	}
	
	protected List<String> getResources() {
		return resources;
	}
	protected void setResources(List<String> resources) {
		this.resources = resources;
	}
	
	protected int getOperationCode() {
		return _operationCode;
	}
	protected void setOperationCode(int operationCode) {
		this._operationCode = operationCode;
	}
	
	/**
	 * @uml.property  name="_resourceManager"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private ResourceManager _resourceManager;
	/**
	 * @uml.property  name="resources"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
	private List<String> resources;
	
	/**
	 * @uml.property  name="_operationCode"
	 */
	private int _operationCode;
	
	public static final int REFRESH_INSTANCE = 1;
	public static final int RELOAD_MASTER_FILE_NAME = 2;
	
}