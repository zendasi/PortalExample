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
package com.agiletec.plugins.jacms.apsadmin.content.attribute.action.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.services.group.Group;
import com.agiletec.apsadmin.system.ApsAdminSystemConstants;
import com.agiletec.plugins.jacms.aps.system.services.content.model.Content;
import com.agiletec.plugins.jacms.aps.system.services.resource.model.ResourceInterface;
import com.agiletec.plugins.jacms.apsadmin.content.ContentActionConstants;
import com.agiletec.plugins.jacms.apsadmin.resource.ResourceAction;

/**
 * Classe action a servizio della gestione attributi risorsa, 
 * estensione della action gestrice delle operazioni sulle risorse.
 * La classe ha il compito di permettere l'aggiunta diretta di una nuova risorsa 
 * sia nell'archivio (corrispondente al tipo) che nel contenuto che si stà editando.
 * @author E.Santoboni
 */
public class ExtendedResourceAction extends ResourceAction {
	
	@Override
	public String save() {
		try {
			if (ApsAdminSystemConstants.ADD == this.getStrutsAction()) {
				ResourceInterface resource = this.getResourceManager().addResource(this);
				this.buildEntryContentAnchorDest();
				ResourceAttributeActionHelper.joinResource(resource, this.getRequest());
			}
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "save");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	private void buildEntryContentAnchorDest() {
		HttpSession session = this.getRequest().getSession();
		String anchorDest = ResourceAttributeActionHelper.buildEntryContentAnchorDest(session);
		this.setEntryContentAnchorDest(anchorDest);
	}
	
	@Override
	public List<Group> getAllowedGroups() {
		List<Group> groups = new ArrayList<Group>();
		if (this.isCurrentUserMemberOf(Group.FREE_GROUP_NAME)) {
			groups.add(this.getGroupManager().getGroup(Group.FREE_GROUP_NAME));
		}
		String contentMainGroup = this.getContent().getMainGroup();
		if (contentMainGroup != null && !contentMainGroup.equals(Group.FREE_GROUP_NAME)) {
			groups.add(this.getGroupManager().getGroup(contentMainGroup));
		}
		return groups;
	}
	
	/**
	 * Restituisce il contenuto in sesione.
	 * @return Il contenuto in sesione.
	 */
	public Content getContent() {
		HttpSession session = this.getRequest().getSession();
		return (Content) session.getAttribute(ContentActionConstants.SESSION_PARAM_NAME_CURRENT_CONTENT);
	}
	
	public boolean isOnEditContent() {
		return true;
	}
	
	public String getEntryContentAnchorDest() {
		if (null == this._entryContentAnchorDest) {
			this.buildEntryContentAnchorDest();
		}
		return _entryContentAnchorDest;
	}
	protected void setEntryContentAnchorDest(String entryContentAnchorDest) {
		this._entryContentAnchorDest = entryContentAnchorDest;
	}
	
	/**
	 * @uml.property  name="_entryContentAnchorDest"
	 */
	private String _entryContentAnchorDest;
	
}
