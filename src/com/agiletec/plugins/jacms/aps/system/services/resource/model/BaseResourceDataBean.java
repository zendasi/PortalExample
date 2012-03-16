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
package com.agiletec.plugins.jacms.aps.system.services.resource.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.agiletec.aps.system.services.category.Category;

/**
 * Base resource data bean
 * @author E.Santoboni
 */
public class BaseResourceDataBean implements ResourceDataBean {
	
	@Override
	public String getResourceId() {
		return _resourceId;
	}
	public void setResourceId(String resourceId) {
		this._resourceId = resourceId;
	}
	
	public BaseResourceDataBean(File file) {
		if (null == file) {
			throw new RuntimeException("Null File");
		}
		this.setFile(file);
	}
	
	@Override
	public String getResourceType() {
		return _resourceType;
	}
	public void setResourceType(String resourceType) {
		this._resourceType = resourceType;
	}
	
	@Override
	public String getDescr() {
		return _description;
	}
	public void setDescr(String descr) {
		this._description = descr;
	}
	
	@Override
	public String getMainGroup() {
		return _mainGroup;
	}
	public void setMainGroup(String mainGroup) {
		this._mainGroup = mainGroup;
	}
	
	@Override
	public File getFile() {
		return _file;
	}
	public void setFile(File file) {
		this._file = file;
	}
	
	@Override
	public List<Category> getCategories() {
		return _categories;
	}
	public void setCategories(List<Category> categories) {
		this._categories = categories;
	}
	
	@Override
	public String getMimeType() {
		return _mimeType;
	}
	public void setMimeType(String mimeType) {
		this._mimeType = mimeType;
	}
	
	@Override
	public int getFileSize() {
		return (int) this.getFile().length()/1000;
	}
	
	@Override
	public String getFileName() {
		if (null != this._fileName) {
			return _fileName;
		}
		String fullName = this.getFile().getName();
		return fullName.substring(fullName.lastIndexOf('/')+1).trim();
	}
	public void setFileName(String fileName) {
		this._fileName = fileName;
	}
	
	@Override
	public InputStream getInputStream() throws Throwable {
		return new FileInputStream(this._file);
	}
	
	/**
	 * @uml.property  name="_resourceId"
	 */
	private String _resourceId;
	/**
	 * @uml.property  name="_resourceType"
	 */
	private String _resourceType;
	/**
	 * @uml.property  name="_description"
	 */
	private String _description;
	/**
	 * @uml.property  name="_mainGroup"
	 */
	private String _mainGroup;
	/**
	 * @uml.property  name="_file"
	 */
	private File _file;
	/**
	 * @uml.property  name="_categories"
	 */
	private List<Category> _categories = new ArrayList<Category>();
	/**
	 * @uml.property  name="_mimeType"
	 */
	private String _mimeType;
	/**
	 * @uml.property  name="_fileName"
	 */
	private String _fileName;
	
}