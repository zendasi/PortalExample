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
package com.agiletec.aps.system.common.entity.model.attribute;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The attribute role class.
 * @author E.Santoboni
 */
public class AttributeRole implements Serializable {
	
	public AttributeRole(String name, String description, List<String> allowedTypes) {
		this._name = name;
		this._description = description;
		this._allowedAttributeTypes = allowedTypes;
	}
	
	@Override
	public AttributeRole clone() {
		List<String> allowedTypes = new ArrayList<String>();
		allowedTypes.addAll(this.getAllowedAttributeTypes());
		return new AttributeRole(this.getName(), this.getDescription(), allowedTypes);
	}
	
	public String getName() {
		return _name;
	}
	public String getDescription() {
		return _description;
	}
	public List<String> getAllowedAttributeTypes() {
		return _allowedAttributeTypes;
	}
	
	/**
	 * @uml.property  name="_name"
	 */
	private String _name;
	/**
	 * @uml.property  name="_description"
	 */
	private String _description;
	/**
	 * @uml.property  name="_allowedAttributeTypes"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
	private List<String> _allowedAttributeTypes;
	
}