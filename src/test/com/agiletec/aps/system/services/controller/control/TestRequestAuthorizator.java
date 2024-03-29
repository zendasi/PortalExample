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
package test.com.agiletec.aps.system.services.controller.control;

import test.com.agiletec.aps.BaseTestCase;

import com.agiletec.aps.system.RequestContext;
import com.agiletec.aps.system.SystemConstants;
import com.agiletec.aps.system.services.controller.ControllerManager;
import com.agiletec.aps.system.services.controller.control.ControlServiceInterface;
import com.agiletec.aps.system.services.page.IPage;
import com.agiletec.aps.system.services.page.IPageManager;

/**
 * @author M.Diana
 */
public class TestRequestAuthorizator extends BaseTestCase {
	
	@Override
	protected void setUp() throws Exception {
        super.setUp();
        this.init();
    }
	
	public void testService_1() throws Throwable {
		RequestContext reqCtx = this.getRequestContext();
		this.setUserOnSession(SystemConstants.GUEST_USER_NAME);
		IPage root = this._pageManager.getRoot();
		reqCtx.addExtraParam(SystemConstants.EXTRAPAR_CURRENT_PAGE, root);
		int status = _authorizator.service(reqCtx, ControllerManager.CONTINUE);
		assertEquals(status, ControllerManager.CONTINUE);
		String redirectUrl = (String) reqCtx.getExtraParam(RequestContext.EXTRAPAR_REDIRECT_URL);
		assertNull(redirectUrl);
	}
	
	public void testService_2() throws Throwable {
		RequestContext reqCtx = this.getRequestContext();
		this.setUserOnSession("admin");
		IPage root = this._pageManager.getRoot();
		reqCtx.addExtraParam(SystemConstants.EXTRAPAR_CURRENT_PAGE, root);
		int status = this._authorizator.service(reqCtx, ControllerManager.CONTINUE);
		assertEquals(status, ControllerManager.CONTINUE);
		String redirectUrl = (String) reqCtx.getExtraParam(RequestContext.EXTRAPAR_REDIRECT_URL);
		assertNull(redirectUrl);
	}
	
	public void testServiceFailure_1() throws Throwable {
		RequestContext reqCtx = this.getRequestContext();
		this.setUserOnSession(SystemConstants.GUEST_USER_NAME);
		IPage requiredPage = this._pageManager.getPage("customers_page");
		reqCtx.addExtraParam(SystemConstants.EXTRAPAR_CURRENT_PAGE, requiredPage);
		int status = _authorizator.service(reqCtx, ControllerManager.CONTINUE);
		assertEquals(status, ControllerManager.REDIRECT);
		String redirectUrl = (String) reqCtx.getExtraParam(RequestContext.EXTRAPAR_REDIRECT_URL);
		assertEquals("/japs/it/login.page?redirectflag=1", redirectUrl);
	}
	
	public void testServiceFailure_2() throws Throwable {
		RequestContext reqCtx = this.getRequestContext();
		reqCtx.getRequest().getSession().removeAttribute(SystemConstants.SESSIONPARAM_CURRENT_USER);
		IPage root = this._pageManager.getRoot();
		reqCtx.addExtraParam(SystemConstants.EXTRAPAR_CURRENT_PAGE, root);
		int status = _authorizator.service(reqCtx, ControllerManager.CONTINUE);
		assertEquals(status, ControllerManager.SYS_ERROR);
	}
	
	private void init() throws Exception {
        try {
        	this._authorizator = (ControlServiceInterface) this.getApplicationContext().getBean("RequestAuthorizatorControlService");
        	this._pageManager = (IPageManager) this.getService(SystemConstants.PAGE_MANAGER);
        } catch (Throwable e) {
            throw new Exception(e);
        }
    }
	
	/**
	 * @uml.property  name="_authorizator"
	 * @uml.associationEnd  
	 */
	private ControlServiceInterface _authorizator;
	
	/**
	 * @uml.property  name="_pageManager"
	 * @uml.associationEnd  
	 */
	private IPageManager _pageManager;
    
}