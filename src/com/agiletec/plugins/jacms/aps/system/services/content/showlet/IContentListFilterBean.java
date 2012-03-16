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
package com.agiletec.plugins.jacms.aps.system.services.content.showlet;

/**
 * @author  E.Santoboni
 */
public interface IContentListFilterBean {
	
	/**
	 * @uml.property  name="key"
	 */
	public String getKey();
	
	public boolean isAttributeFilter();
	
	public boolean getLikeOption();
	
	/**
	 * @uml.property  name="value"
	 */
	public String getValue();
	
	/**
	 * @uml.property  name="start"
	 */
	public String getStart();
	
	/**
	 * @uml.property  name="end"
	 */
	public String getEnd();
	
	/**
	 * @uml.property  name="order"
	 */
	public String getOrder();
	
}