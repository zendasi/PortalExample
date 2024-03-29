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
package com.agiletec.plugins.jacms.apsadmin.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.services.category.Category;
import com.agiletec.aps.system.services.group.Group;
import com.agiletec.aps.system.services.group.IGroupManager;
import com.agiletec.apsadmin.system.ApsAdminSystemConstants;
import com.agiletec.plugins.jacms.aps.system.services.resource.model.AbstractMonoInstanceResource;
import com.agiletec.plugins.jacms.aps.system.services.resource.model.AbstractMultiInstanceResource;
import com.agiletec.plugins.jacms.aps.system.services.resource.model.ResourceDataBean;
import com.agiletec.plugins.jacms.aps.system.services.resource.model.ResourceInterface;

/**
 * Class used to handle resource objects.
 * @author E.Santoboni
 */
public class ResourceAction extends AbstractResourceAction implements IResourceAction, ResourceDataBean {
	
	@Override
	public void validate() {
		super.validate();
		if (!this.hasFieldErrors() && this.getStrutsAction() == ApsAdminSystemConstants.ADD) {
			ResourceInterface resourcePrototype = this.getResourceManager().createResourceType(this.getResourceType());
			this.checkRightFileType(resourcePrototype);
			if (this.hasFieldErrors()) return;
			this.checkDuplicateFile(resourcePrototype);
		}
	}
	
	private void checkRightFileType(ResourceInterface resourcePrototype) {
		boolean isRight = false;
		if (this._filename.length() > 0) {
			String fileName = _filename;
			String docType = fileName.substring(fileName.lastIndexOf('.')+1).trim();
			String[] types = resourcePrototype.getAllowedFileTypes();
			isRight = this.isValidType(docType, types);
		} else {
			isRight = true;
		}
		if (!isRight) {
			this.addFieldError("upload", this.getText("error.resource.file.wrongFormat"));
		}
	}
	
	private void checkDuplicateFile(ResourceInterface resourcePrototype) {
		resourcePrototype.setMainGroup(this.getMainGroup());
		String baseDiskFolder = resourcePrototype.getDiskFolder();
    	String formFileName = this.getFileName();
    	String fileName = null;
    	if (resourcePrototype.isMultiInstance()) {
    		fileName = ((AbstractMultiInstanceResource) resourcePrototype).getInstanceFileName(formFileName, 0, null);
    	} else {
    		fileName = ((AbstractMonoInstanceResource) resourcePrototype).getInstanceFileName(formFileName);
    	}
    	if ((new File(baseDiskFolder + fileName)).exists()) {
    		String[] args = {formFileName};
    		this.addFieldError("upload", this.getText("error.resource.file.alreadyPresent", args));
		}
	}
	
	private boolean isValidType(String docType, String[] rightTypes) {
		boolean isValid = false;
		if (rightTypes.length > 0) {
			for (int i=0; i<rightTypes.length; i++) {
				if (docType.toLowerCase().equals(rightTypes[i])) {
					isValid = true;
					break;
				}
			}
		} else {
			isValid = true;
		}
		return isValid;
	}
	
	@Override
	public String newResource() {
		this.setStrutsAction(ApsAdminSystemConstants.ADD);
		try {
			if (this.getAuthorizationManager().isAuthOnGroup(this.getCurrentUser(), Group.FREE_GROUP_NAME)) {
				this.setMainGroup(Group.FREE_GROUP_NAME);
			}
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "newResource");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	public String edit() {
		try {
			ResourceInterface resource = this.loadResource(this.getResourceId());
			this.setResourceTypeCode(resource.getType());
			this.setDescr(resource.getDescr());
			List<Category> resCategories = resource.getCategories();
			for (int i=0; i<resCategories.size(); i++) {
				Category resCat = resCategories.get(i);
				this.getCategoryCodes().add(resCat.getCode());
			}
			this.setMainGroup(resource.getMainGroup());
			this.setStrutsAction(ApsAdminSystemConstants.EDIT);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "edit");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	public String save() {
		try {
			if (ApsAdminSystemConstants.ADD == this.getStrutsAction()) {
				this.getResourceManager().addResource(this);
			} else if (ApsAdminSystemConstants.EDIT == this.getStrutsAction()) {
				this.getResourceManager().updateResource(this);
			}
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "save");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	public String trash() {
		try {
			String result = this.checkDeleteResource();
			if (null != result) return result;
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "trash");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	public String delete() {
		try {
			String result = this.checkDeleteResource();
			if (null != result) return result;
			ResourceInterface resource = this.loadResource(this.getResourceId());
			this.getResourceManager().deleteResource(resource);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "delete");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	protected String checkDeleteResource() throws Throwable {
		String resourceId = this.getResourceId();
		ResourceInterface resource = this.loadResource(resourceId);
		if (resource == null) {
			this.addActionError(this.getText("error.resource.delete.invalid"));
			return INPUT;
		}
		Map<String, List> references = this.getResourceActionHelper().getReferencingObjects(resource, this.getRequest());
		this.setReferences(references);
		if (references.size() > 0) {
			return "references";
		}
		return null;
	}
	
	@Override
	public String joinCategory() {
		return this.joinRemoveCategory(true, this.getCategoryCode());
	}
	
	@Override
	public String removeCategory() {
		return this.joinRemoveCategory(false, this.getCategoryCode());
	}
	
	/**
	 * This method perfoms either the linking of a resource to a category or the removal of such association.
	 * NOTE: in the current implementation operations carried on invalid or unknown categories do not return error code on
	 * purpose, since the join or unlink process does not take place. 
	 * @param isJoin if 'true' associates a resource to a category, otherwise remove it
	 * @param categoryCode the string code of the category to work with.
	 * @return FAILURE if error detected, SUCCESS otherwise.
	 */
	private String joinRemoveCategory(boolean isJoin, String categoryCode) {
		try {
			Category category = this.getCategory(categoryCode);
			if (category == null) return SUCCESS;
			List<String> categories = this.getCategoryCodes();
			if (isJoin) {
				if (!categories.contains(categoryCode)) {
					categories.add(categoryCode);
				}
			} else {
				categories.remove(categoryCode);
			}
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "joinRemoveCategory");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	public Category getCategory(String categoryCode) {
		return this.getCategoryManager().getCategory(categoryCode);
	}
	
	public String getResourceId() {
		return _resourceId;
	}
	public void setResourceId(String resourceId) {
		this._resourceId = resourceId;
	}
	
	public int getStrutsAction() {
		return _strutsAction;
	}
	public void setStrutsAction(int strutsAction) {
		this._strutsAction = strutsAction;
	}
	
	public Map<String, List> getReferences() {
		try {
			if (null == this._references) {
				this._references = this.getResourceActionHelper().getReferencingObjects(this.getResourceId(), this.getRequest());
			}
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "getReferences", "Error extracting references");
		}
		return _references;
	}
	
	public void setReferences(Map<String, List> references) {
		this._references = references;
	}
	
    public void setUpload(File file) {
       this._file = file;
    }
    public File getUpload() {
		return this._file;
	}

    public void setUploadContentType(String contentType) {
       this._contentType = contentType;
    }

    public void setUploadFileName(String filename) {
       this._filename = filename;
    }
	
    @Override
	public String getDescr() {
		return _descr;
	}
	public void setDescr(String descr) {
		this._descr = descr;
	}
	
	@Override
	public List<Category> getCategories() {
		List<Category> categories = new ArrayList<Category>(this.getCategoryCodes().size());
		Iterator<String> iter = this.getCategoryCodes().iterator();
		while (iter.hasNext()) {
			String categoryCode = iter.next();
			Category category = this.getCategoryManager().getCategory(categoryCode);
			if (null != category) categories.add(category);
		}
		return categories;
	}
	
	@Override
	public String getFileName() {
		if (null != this._filename && this.isNormalizeFileName()) {
			return this._filename.trim().replace(' ', '_');
		}
		return this._filename;
	}
	
	public boolean isNormalizeFileName() {
		return _normalizeFileName;
	}
	public void setNormalizeFileName(boolean normalizeFileName) {
		this._normalizeFileName = normalizeFileName;
	}
	
	@Override
	public int getFileSize() {
		return (int) this._file.length()/1000;
	}

	@Override
	public File getFile() {
		return _file;
	}
	
	@Override
	public InputStream getInputStream() throws Throwable {
		if (null == this.getFile()) return null;
		return new FileInputStream(this.getFile());
	}
	
	@Override
	public String getMainGroup() {
		return this._mainGroup;
	}
	public void setMainGroup(String mainGroup) {
		this._mainGroup = mainGroup;
	}
	
	public List<String> getCategoryCodes() {
		return _categoryCodes;
	}
	public void setCategoryCodes(List<String> categoryCodes) {
		this._categoryCodes = categoryCodes;
	}
	
	@Override
	public String getMimeType() {
		return this._contentType;
	}
	@Override
	public String getResourceType() {
		return this.getResourceTypeCode();
	}
	
	public String getCategoryCode() {
		return _categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this._categoryCode = categoryCode;
	}
	
	protected IGroupManager getGroupManager() {
		return _groupManager;
	}
	public void setGroupManager(IGroupManager groupManager) {
		this._groupManager = groupManager;
	}
	
	/**
	 * @uml.property  name="_resourceId"
	 */
	private String _resourceId;
	/**
	 * @uml.property  name="_descr"
	 */
	private String _descr;
	/**
	 * @uml.property  name="_mainGroup"
	 */
	private String _mainGroup;
	/**
	 * @uml.property  name="_categoryCodes"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
	private List<String> _categoryCodes = new ArrayList<String>();
	
	/**
	 * @uml.property  name="_file"
	 */
	private File _file;
    /**
	 * @uml.property  name="_contentType"
	 */
    private String _contentType;
    /**
	 * @uml.property  name="_filename"
	 */
    private String _filename;
    
    /**
	 * @uml.property  name="_normalizeFileName"
	 */
    private boolean _normalizeFileName = false;
	
	/**
	 * @uml.property  name="_groupManager"
	 * @uml.associationEnd  
	 */
	private IGroupManager _groupManager;
    
    /**
	 * @uml.property  name="_strutsAction"
	 */
    private int _strutsAction;
	
	/**
	 * @uml.property  name="_references"
	 */
	private Map<String, List> _references;
	
	/**
	 * @uml.property  name="_categoryCode"
	 */
	private String _categoryCode;
	
}