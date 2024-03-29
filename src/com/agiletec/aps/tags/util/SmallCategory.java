/*
*
* Copyright 2005 AgileTec s.r.l. (http://www.agiletec.it) All rights reserved.
*
* This file is part of JAPS software.
* JAPS and its  source-code is  licensed under the  terms of the
* GNU General Public License  as published by  the Free Software
* Foundation (http://www.fsf.org/licensing/licenses/gpl.txt).
* 
* You may copy, adapt, and redistribute this file for commercial
* or non-commercial use.
* When copying,  adapting,  or redistributing  this document you
* are required to provide proper attribution  to AgileTec, using
* the following attribution line:
* Copyright 2005 AgileTec s.r.l. (http://www.agiletec.it) All rights reserved.
*
*/
package com.agiletec.aps.tags.util;


/**
 * @version 1.0
 * @author E.Santoboni
 */
public class SmallCategory implements Comparable<SmallCategory> {
	
	public String getCode() {
		return _code;
	}
	public void setCode(String code) {
		this._code = code;
	}
	
	public String getTitle() {
		return _title;
	}
	public void setTitle(String title) {
		this._title = title;
	}
	
	public int compareTo(SmallCategory smallCat) {
		return _title.compareTo(smallCat.getTitle());
	}
	
	/**
	 * @uml.property  name="_code"
	 */
	private String _code;
	/**
	 * @uml.property  name="_title"
	 */
	private String _title;
	
}