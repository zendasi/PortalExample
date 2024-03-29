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
package com.agiletec.plugins.jacms.apsadmin.content.attribute.action.hypertext;

import com.agiletec.apsadmin.system.BaseAction;

/**
 * Classe action delegata alla gestione generale dei jAPSLinks (link interni al testo degli attributi Hypertext).
 * @author E.Santoboni
 */
public class HypertextAttributeAction extends BaseAction {
	
	public String getInternalActionName() {
		return _internalActionName;
	}
	public void setInternalActionName(String internalActionName) {
		this._internalActionName = internalActionName;
	}
	
	public String getActiveTab() {
		return _activeTab;
	}
	public void setActiveTab(String activeTab) {
		this._activeTab = activeTab;
	}
	
	/**
	 * @uml.property  name="_internalActionName"
	 */
	private String _internalActionName;
	/**
	 * @uml.property  name="_activeTab"
	 */
	private String _activeTab;
	
}