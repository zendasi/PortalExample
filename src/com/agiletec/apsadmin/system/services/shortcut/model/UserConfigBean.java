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
 * The shortcut user config class.
 * @author E.Santoboni
 */
public class UserConfigBean implements Serializable {
	
	public UserConfigBean(String username, String[] config) {
		this.setUsername(username);
		this.setConfig(config);
	}
	
	public String getUsername() {
		return _username;
	}
	protected void setUsername(String username) {
		this._username = username;
	}
	
	public String[] getConfig() {
		return _config;
	}
	protected void setConfig(String[] config) {
		this._config = config;
	}
	
	/**
	 * @uml.property  name="_username"
	 */
	private String _username;
	/**
	 * @uml.property  name="_config" multiplicity="(0 -1)" dimension="1"
	 */
	private String[] _config;
	
}