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
package test.com.agiletec.plugins.jacms.aps.system.services.resource.mock;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.agiletec.aps.system.services.category.Category;
import com.agiletec.plugins.jacms.aps.system.services.resource.model.ResourceDataBean;

/**
 * @author E.Santoboni
 */
public class MockResourceDataBean implements ResourceDataBean {
	
	@Override
	public String getResourceId() {
		return null;
	}
	
	/**
	 * Restituisce il tipo di risorsa.
	 * @return Il tipo di risorsa.
	 */
	public String getResourceType() {
		return _resourceType;
	}
	
	/**
	 * Setta il tipo di risorsa.
	 * @param type Il tipo di risorsa.
	 */
	public void setResourceType(String type) {
		this._resourceType = type;
	}
	
	/**
	 * Setta la descrizione della risorsa.
	 * @param descr La descrizione della risorsa.
	 */
	public void setDescr(String descr) {
		this._descr = descr;
	}
	
	/**
	 * Restituisce la descrizione della risorsa.
	 * @return La descrizione della risorsa.
	 */
	public String getDescr() {
		return _descr;
	}
	
	/**
	 * Setta il file relativo alla risorsa.
	 * @param file Il file relativo alla risorsa.
	 */
	public void setFile(File file) {
		this._file = file;
	}
	
	public String getMainGroup() {
		return _mainGroup;
	}
	
	public void setMainGroup(String mainGroup) {
		this._mainGroup = mainGroup;
	}
	
	public List<Category> getCategories() {
		return _categories;
	}
	
	public void setCategories(List<Category> categories) {
		this._categories = categories;
	}
	
	public int getFileSize() {
		return (int) this._file.length()/1000;
	}
	
	public InputStream getInputStream() throws Throwable {
		return new FileInputStream(this._file);
	}
	
	public String getFileName() {
		String filename = _file.getName().substring(_file.getName().lastIndexOf('/')+1).trim();
		return filename;
	}
	
	public String getMimeType() {
		return _mimeType;
	}
	public void setMimeType(String mimetype) {
		this._mimeType = mimetype;
	}

	/**
	 * @uml.property  name="_resourceType"
	 */
	private String _resourceType;
	/**
	 * @uml.property  name="_descr"
	 */
	private String _descr;
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
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="com.agiletec.aps.system.services.category.Category"
	 */
	private List<Category> _categories = new ArrayList<Category>();
	/**
	 * @uml.property  name="_mimeType"
	 */
	private String _mimeType;
	@Override
	public File getFile() {
		return _file;
	}
	
}
