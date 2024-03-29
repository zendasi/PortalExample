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
package com.agiletec.apsadmin.category;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.common.tree.ITreeNode;
import com.agiletec.aps.system.services.category.Category;
import com.agiletec.aps.system.services.category.ICategoryManager;
import com.agiletec.aps.system.services.lang.Lang;
import com.agiletec.aps.util.ApsProperties;
import com.agiletec.apsadmin.category.helper.ICategoryActionHelper;
import com.agiletec.apsadmin.system.AbstractTreeAction;
import com.agiletec.apsadmin.system.ApsAdminSystemConstants;
import com.agiletec.apsadmin.system.BaseActionHelper;

/**
 * Action class which handles categories. 
 * @author E.Santoboni - G.Cocco
 */
public class CategoryAction extends AbstractTreeAction implements ICategoryAction, ICategoryTreeAction {
	
	@Override
	public void validate() {
		super.validate();
		this.checkCode();
		this.checkTitles();
	}
	
	private void checkTitles() {
		Iterator<Lang> langsIter = this.getLangManager().getLangs().iterator();
		while (langsIter.hasNext()) {
			Lang lang = (Lang) langsIter.next();
			String titleKey = "lang"+lang.getCode();
			String title = this.getRequest().getParameter(titleKey);
			if (null != title) {
				this.getTitles().put(lang.getCode(), title.trim());
			}
			if (null == title || title.trim().length() == 0) {
				String[] args = {lang.getDescr()};
				this.addFieldError(titleKey, this.getText("error.category.insertTitle", args));
			}
		}
	}
	
	private void checkCode() {
		String code = this.getCategoryCode();
		if ((this.getStrutsAction() == ApsAdminSystemConstants.ADD || 
				this.getStrutsAction() == ApsAdminSystemConstants.PASTE) 
				&& null != code && code.trim().length() > 0) {
			String currectCode = BaseActionHelper.purgeString(code.trim());
			if (currectCode.length() > 0 && null != this.getCategoryManager().getCategory(currectCode)) {
				String[] args = {currectCode};
				this.addFieldError("categoryCode", this.getText("error.category.duplicateCode", args));
			}
			this.setCategoryCode(currectCode);
		}
	}
	
	@Override
	public String add() {
		String selectedNode = this.getSelectedNode();
		try {
			Category category = this.getCategory(selectedNode);
			if (null == category) {
				this.addActionError(this.getText("error.category.selectCategory"));
				return "categoryTree";
			}
			this.setStrutsAction(ApsAdminSystemConstants.ADD);
			this.setParentCategoryCode(selectedNode);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "add");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	public String edit() {
		this.setStrutsAction(ApsAdminSystemConstants.EDIT);
		return this.extractCategoryFormValues();
	}
	
	@Override
	public String showDetail() {
		String result = this.extractCategoryFormValues();
		if (!result.equals(SUCCESS)) return result;
		this.extractReferencingObjects(this.getSelectedNode());
		return result;
	}
	
	protected String extractCategoryFormValues() {
		String selectedNode = this.getSelectedNode();
		try {
			Category category = this.getCategory(selectedNode);
			if (null == category) {
				this.addActionError(this.getText("error.category.selectCategory"));
				return "categoryTree";
			}
			this.setParentCategoryCode(category.getParentCode());
			this.setCategoryCode(category.getCode());
			this.setTitles(category.getTitles());
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "extractCategoryFormValues");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	public String trash() {
		try {
			String check = this.chechDelete();
			if (null != check) return check;
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "trash");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	public String delete() {
		String selectedNode = this.getSelectedNode();
		try {
			String check = this.chechDelete();
			if (null != check) return check;
			Category currentCategory = this.getCategory(selectedNode);
			this.getCategoryManager().deleteCategory(selectedNode);
			this.setSelectedNode(currentCategory.getParent().getCode());
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "delete");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	/**
	 * Perform all the needed checks before deleting a category.
	 * When errors are detected a new actionMessaged, containing the appropriate error code and messaged, is created.
	 * @return null if the deletion operation is successful, otherwise the error code
	 */
	protected String chechDelete() {
		Category currentCategory = this.getCategory(this.getSelectedNode());
		if (null == currentCategory) {
			ApsSystemUtils.getLogger().info("E' necessario selezionare un nodo");
			this.addActionError(this.getText("error.category.selectCategory"));
			return "categoryTree";
		}
		if (currentCategory.getCode().equals(currentCategory.getParentCode())) {
			ApsSystemUtils.getLogger().info("Non è possibile eliminare la categoria Home");
			this.addActionError(this.getText("error.category.homeDelete.notAllowed"));
			return "categoryTree";
		}
		if (currentCategory.getChildren().length != 0) {
			ApsSystemUtils.getLogger().info("Non è possibile eliminare una categoria con figlie");
			this.addActionError(this.getText("error.category.deleteWithChildren.notAllowed"));
			return "categoryTree";
        }
		this.extractReferencingObjects(this.getSelectedNode());
		if (null != this.getReferences() && this.getReferences().size() > 0) {
	        return "references";
		}
		return null;
	}
	
	protected void extractReferencingObjects(String categoryCode) {
		try {
			Category category = this.getCategoryManager().getCategory(categoryCode);
			if (null != category) {
				Map references = this.getHelper().getReferencingObjects(category, this.getRequest());
				if (references.size() > 0) {
					this.setReferences(references);
				}
			}
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "extractReferencingObjects", "Error extracting referenced objects by category '" + categoryCode + "'");
		}
	}
	
	@Override
	public String save() {
		Logger log = ApsSystemUtils.getLogger();
		try {
			if (this.getStrutsAction() == ApsAdminSystemConstants.EDIT) {
				Category category = this.getCategory(this.getCategoryCode());
				category.setTitles(this.getTitles());
				this.getCategoryManager().updateCategory(category);
				log.finest("Updated category " + category.getCode());
			} else {
				Category category = this.getHelper().buildNewCategory(this.getCategoryCode(), this.getParentCategoryCode(), this.getTitles());
				this.getCategoryManager().addCategory(category);
				log.finest("Added new category " + this.getCategoryCode());
			}
		} catch (Exception e) {
			ApsSystemUtils.logThrowable(e, this, "save");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	public Category getCategory(String categoryCode) {
		return this.getCategoryManager().getCategory(categoryCode);
	}
	
	@Override
	@Deprecated
	public Category getRoot() {
		return this.getCategoryManager().getRoot();
	}
	
	@Override
	public ITreeNode getTreeRootNode() {
		ITreeNode node = null;
		try {
			node = this.getHelper().getAllowedTreeRoot(new ArrayList<String>());
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "getTreeRootNode");
		}
		return node;
	}
	
	public List<Lang> getLangs() {
		return this.getLangManager().getLangs();
	}
	
	public int getStrutsAction() {
		return _strutsAction;
	}
	public void setStrutsAction(int strutsAction) {
		this._strutsAction = strutsAction;
	}
	
	public String getCategoryCode() {
		return _categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this._categoryCode = categoryCode;
	}
	
	public String getParentCategoryCode() {
		return _parentCategoryCode;
	}
	public void setParentCategoryCode(String parentCategoryCode) {
		this._parentCategoryCode = parentCategoryCode;
	}
	
	public ApsProperties getTitles() {
		return _titles;
	}
	public void setTitles(ApsProperties titles) {
		this._titles = titles;
	}
	
	protected ICategoryManager getCategoryManager() {
		return _categoryManager;
	}
	
	public void setCategoryManager(ICategoryManager categoryManager) {
		this._categoryManager = categoryManager;
	}
	
	protected ICategoryActionHelper getHelper() {
		return (ICategoryActionHelper) super.getTreeHelper();
	}
	
	public Map getReferences() {
		return _references;
	}
	protected void setReferences(Map references) {
		this._references = references;
	}
	
	public String getSelectedNode() {
		return _selectedNode;
	}
	public void setSelectedNode(String selectedNode) {
		super.getTreeNodesToOpen().add(selectedNode);
		this._selectedNode = selectedNode;
	}
	
	/**
	 * @uml.property  name="_strutsAction"
	 */
	private int _strutsAction;
	/**
	 * @uml.property  name="_categoryCode"
	 */
	private String _categoryCode;
	/**
	 * @uml.property  name="_parentCategoryCode"
	 */
	private String _parentCategoryCode;
	/**
	 * @uml.property  name="_selectedNode"
	 */
	private String _selectedNode;
	/**
	 * @uml.property  name="_titles"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private ApsProperties _titles = new ApsProperties();
	
	/**
	 * @uml.property  name="_categoryManager"
	 * @uml.associationEnd  
	 */
	private ICategoryManager _categoryManager;
	
	/**
	 * @uml.property  name="_references"
	 * @uml.associationEnd  multiplicity="(0 -1)" ordering="true" elementType="java.lang.String" qualifier="constant:java.lang.String java.util.List"
	 */
	private Map _references;
	
}