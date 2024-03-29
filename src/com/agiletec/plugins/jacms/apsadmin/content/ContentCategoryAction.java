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
package com.agiletec.plugins.jacms.apsadmin.content;

import java.util.Set;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.services.category.Category;
import com.agiletec.aps.system.services.category.ICategoryManager;
import com.agiletec.apsadmin.system.AbstractTreeAction;
import com.agiletec.plugins.jacms.aps.system.services.content.model.Content;
import com.agiletec.plugins.jacms.apsadmin.content.helper.IContentActionHelper;
import com.opensymphony.xwork2.Action;

/**
 * Action class that manages the category tree operation on content finding GUI interface and the relationships between content and categories.
 * @author E.Santoboni
 */
public class ContentCategoryAction extends AbstractTreeAction implements IContentCategoryAction {
	
	@Override
	public String buildTree() {
		try {
			String result = super.buildTree();
			if (!result.equals(Action.SUCCESS)) return result;
			Set<String> targets = this.getTreeNodesToOpen();
			String marker = this.getTreeNodeActionMarkerCode();
			if (null == marker && null != this.getCategoryCode() && !targets.contains(this.getCategoryCode())) {
				targets.add(this.getCategoryCode());
			}
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "buildTree");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	public String joinCategory() {
		this.updateContentOnSession();
		try {
			String categoryCode = this.getCategoryCode();
			Category category = this.getCategoryManager().getCategory(categoryCode);
			if (null != category && !category.getCode().equals(category.getParentCode()) 
					&& !this.getContent().getCategories().contains(category)) { 
				this.getContent().addCategory(category);
			}
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "joinCategory");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	public String removeCategory() {
		this.updateContentOnSession();
		try {
			String categoryCode = this.getCategoryCode();
			Category category = this.getCategoryManager().getCategory(categoryCode);
			if (null != category) {
				this.getContent().removeCategory(category);
			}
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "removeCategory");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	public Content getContent() {
		return (Content) this.getRequest().getSession().getAttribute(ContentActionConstants.SESSION_PARAM_NAME_CURRENT_CONTENT);
	}
	
	protected Content updateContentOnSession() {
		Content content = this.getContent();
		this.getContentActionHelper().updateEntity(content, this.getRequest());
		return content;
	}
	
	public String getCategoryCode() {
		return _categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this._categoryCode = categoryCode;
	}
	
	protected ICategoryManager getCategoryManager() {
		return _categoryManager;
	}
	public void setCategoryManager(ICategoryManager categoryManager) {
		this._categoryManager = categoryManager;
	}
	
	protected IContentActionHelper getContentActionHelper() {
		return _contentActionHelper;
	}
	public void setContentActionHelper(IContentActionHelper contentActionHelper) {
		this._contentActionHelper = contentActionHelper;
	}
	
	/**
	 * @uml.property  name="_categoryCode"
	 */
	private String _categoryCode;
	/**
	 * @uml.property  name="_categoryManager"
	 * @uml.associationEnd  
	 */
	private ICategoryManager _categoryManager;
	
	/**
	 * @uml.property  name="_contentActionHelper"
	 * @uml.associationEnd  
	 */
	private IContentActionHelper _contentActionHelper;
	
}