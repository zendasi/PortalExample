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
package test.com.agiletec.aps.system.services.controller;

import org.springframework.mock.web.MockHttpServletRequest;

import test.com.agiletec.aps.BaseTestCase;

import com.agiletec.aps.system.RequestContext;
import com.agiletec.aps.system.SystemConstants;
import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.aps.system.services.controller.ControllerManager;

/**
 * @author M.Diana - W.Ambu
 */
public class TestControllerManager extends BaseTestCase {
	
	public void testService_1() throws ApsSystemException {
		RequestContext reqCtx = this.getRequestContext();
		ControllerManager controller = (ControllerManager) this.getService(SystemConstants.CONTROLLER_MANAGER);
		MockHttpServletRequest request = (MockHttpServletRequest) reqCtx.getRequest();
		request.setServletPath("/it/homepage.wp");
		int status = controller.service(reqCtx);
		assertEquals(ControllerManager.OUTPUT, status);
		
		request.setParameter("username", "admin");
		request.setParameter("password", "admin");
		status = controller.service(reqCtx);
		assertEquals(ControllerManager.OUTPUT, status);
	}
	
	public void testService_2() throws ApsSystemException {
		RequestContext reqCtx = this.getRequestContext();
		ControllerManager controller = (ControllerManager) this.getService(SystemConstants.CONTROLLER_MANAGER);
		MockHttpServletRequest request = (MockHttpServletRequest) reqCtx.getRequest();
		request.setServletPath("/it/customers_page.wp");
		int status = controller.service(reqCtx);
		assertEquals(ControllerManager.REDIRECT, status);
		
		request.setParameter("username", "admin");
		request.setParameter("password", "admin");
		request.setServletPath("/it/customers_page.wp");
		status = controller.service(reqCtx);
		assertEquals(ControllerManager.OUTPUT, status);
	}
	
	public void testService_3() throws ApsSystemException {
		RequestContext reqCtx = this.getRequestContext();
		ControllerManager controller = (ControllerManager) this.getService(SystemConstants.CONTROLLER_MANAGER);
		MockHttpServletRequest request = (MockHttpServletRequest) reqCtx.getRequest();
		request.setServletPath("/it/administrators_page.page");
		int status = controller.service(reqCtx);
		assertEquals(ControllerManager.REDIRECT, status);
		String redirectUrl = (String) reqCtx.getExtraParam(RequestContext.EXTRAPAR_REDIRECT_URL);
		assertEquals("/japs/it/login.page?redirectflag=1", redirectUrl);
		
		request.setParameter(RequestContext.PAR_REDIRECT_FLAG, "1");
		status = controller.service(reqCtx);
		assertEquals(ControllerManager.REDIRECT, status);
		redirectUrl = (String) reqCtx.getExtraParam(RequestContext.EXTRAPAR_REDIRECT_URL);
		assertEquals("/japs/it/errorpage.page", redirectUrl);
	}
	
}