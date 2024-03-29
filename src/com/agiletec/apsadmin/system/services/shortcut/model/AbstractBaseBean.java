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
package com.agiletec.apsadmin.system.services.shortcut.model;

import java.io.Serializable;

/**
 * @author E.Santoboni
 */
public abstract class AbstractBaseBean implements Serializable {
	
	public AbstractBaseBean(String id) {
		this.setId(id);
	}
	
	public String getId() {
		return _id;
	}
	protected void setId(String id) {
		this._id = id;
	}
	
	public String getDescriptionKey() {
		return _descriptionKey;
	}
	public void setDescriptionKey(String descriptionKey) {
		this._descriptionKey = descriptionKey;
	}
	
	public String getDescription() {
		return _description;
	}
	public void setDescription(String description) {
		this._description = description;
	}
	
	public String getLongDescription() {
		return _longDescription;
	}
	public void setLongDescription(String longDescription) {
		this._longDescription = longDescription;
	}
	
	public String getLongDescriptionKey() {
		return _longDescriptionKey;
	}
	public void setLongDescriptionKey(String longDescriptionKey) {
		this._longDescriptionKey = longDescriptionKey;
	}
	
	/**
	 * @uml.property  name="_id"
	 */
	private String _id;
	/**
	 * @uml.property  name="_descriptionKey"
	 */
	private String _descriptionKey;
	/**
	 * @uml.property  name="_description"
	 */
	private String _description;
	
	/**
	 * @uml.property  name="_longDescription"
	 */
	private String _longDescription;
	/**
	 * @uml.property  name="_longDescriptionKey"
	 */
	private String _longDescriptionKey;
	
}