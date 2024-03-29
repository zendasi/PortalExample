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
package com.agiletec.apsadmin.system.entity;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

import com.agiletec.aps.system.common.entity.IEntityManager;

/**
 * @author E.Santoboni
 */
public class DefaultApsEntityFinderAction extends AbstractApsEntityFinderAction implements BeanFactoryAware {
	
	@Override
	public String delete() {
		// Operation Not Supported
		return FAILURE;
	}
	
	@Override
	public String trash() {
		// Operation Not Supported
		return FAILURE;
	}
	
	@Override
	protected void deleteEntity(String entityId) throws Throwable {
		// Operation Not Supported
	}
	
	protected IEntityManager getEntityManager() {
		String entityManagerName = this.getEntityManagerName();
		return (IEntityManager) this.getBeanFactory().getBean(entityManagerName);
	}
	
	public String getEntityManagerName() {
		String sessionValue = (String) this.getRequest().getSession().getAttribute(ApsEntityActionConstants.ENTITY_TYPE_MANAGER_SESSION_PARAM);
		if (null != sessionValue) return sessionValue;
		return _entityManagerName;
	}
	public void setEntityManagerName(String entityManagerName) {
		this.getRequest().getSession().setAttribute(ApsEntityActionConstants.ENTITY_TYPE_MANAGER_SESSION_PARAM, entityManagerName);
		this._entityManagerName = entityManagerName;
	}
	
	protected BeanFactory getBeanFactory() {
		return _beanFactory;
	}
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this._beanFactory = beanFactory;
	}
	
	/**
	 * @uml.property  name="_entityManagerName"
	 */
	private String _entityManagerName;
	
	/**
	 * @uml.property  name="_beanFactory"
	 * @uml.associationEnd  
	 */
	private BeanFactory _beanFactory;
	
}