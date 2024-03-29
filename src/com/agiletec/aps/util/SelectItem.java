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
package com.agiletec.aps.util;

/**
 * A single item for a select form element.
 * @author E.Santoboni
 */
public class SelectItem {
	
	public SelectItem(String key, String value) {
		this._key = key;
		this._value = value;
	}
	
	public SelectItem(String key, String value, String optgroup) {
		this(key, value);
		this._optgroup = optgroup;
	}
	
	public String getKey() {
		return _key;
	}
	public String getValue() {
		return _value;
	}
	public String getOptgroup() {
		return _optgroup;
	}
	
	/**
	 * @uml.property  name="_key"
	 */
	private String _key;
	/**
	 * @uml.property  name="_value"
	 */
	private String _value;
	/**
	 * @uml.property  name="_optgroup"
	 */
	private String _optgroup;
	
}