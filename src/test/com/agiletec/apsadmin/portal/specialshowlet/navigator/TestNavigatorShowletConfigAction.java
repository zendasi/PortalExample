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
package test.com.agiletec.apsadmin.portal.specialshowlet.navigator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import test.com.agiletec.apsadmin.ApsAdminBaseTestCase;

import com.agiletec.aps.system.SystemConstants;
import com.agiletec.aps.system.services.page.IPage;
import com.agiletec.aps.system.services.page.IPageManager;
import com.agiletec.aps.system.services.page.Showlet;
import com.agiletec.aps.system.services.page.showlet.NavigatorExpression;
import com.agiletec.aps.util.ApsProperties;
import com.agiletec.apsadmin.portal.specialshowlet.navigator.INavigatorShowletConfigAction;
import com.agiletec.apsadmin.portal.specialshowlet.navigator.NavigatorShowletConfigAction;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author E.Santoboni
 */
public class TestNavigatorShowletConfigAction extends ApsAdminBaseTestCase {
	
	@Override
	protected void setUp() throws Exception {
        super.setUp();
        this.init();
    }
	
	public void testInitConfigNavigator_1() throws Throwable {
		String result = this.executeConfigNavigator("admin", "homepage", "1", "leftmenu");
		assertEquals(Action.SUCCESS, result);
		INavigatorShowletConfigAction action = (INavigatorShowletConfigAction) this.getAction();
		Showlet showlet = action.getShowlet();
		assertNotNull(showlet);
		assertEquals(0, showlet.getConfig().size());
	}
	
	public void testInitConfigNavigator_2() throws Throwable {
		String result = this.executeConfigNavigator("admin", "pagina_1", "2", null);
		assertEquals(Action.SUCCESS, result);
		INavigatorShowletConfigAction action = (INavigatorShowletConfigAction) this.getAction();
		Showlet showlet = action.getShowlet();
		assertNotNull(showlet);
		ApsProperties props = showlet.getConfig();
		assertEquals(1, props.size());
		assertEquals("abs(1).subtree(2)", props.getProperty("navSpec"));
		List<NavigatorExpression> expressions = action.getExpressions();
		assertEquals(1, expressions.size());
		NavigatorExpression expression = expressions.get(0);
		assertEquals(NavigatorExpression.SPEC_ABS_ID, expression.getSpecId());
		assertEquals(1, expression.getSpecAbsLevel());
		assertEquals(NavigatorExpression.OPERATOR_SUBTREE_ID, expression.getOperatorId());
		assertEquals(2, expression.getOperatorSubtreeLevel());
	}
	
	public void testExecuteMoveExpression_1() throws Throwable {
		Map<String, String> params = new HashMap<String, String>();
		params.put("pageCode", "pagina_2");
		params.put("frame", "0");
		params.put("showletTypeCode", "leftmenu");
		params.put("navSpec", "parent.subtree(2)+abs(1).subtree(2)+current");
		params.put("movement", INavigatorShowletConfigAction.MOVEMENT_DOWN_CODE);
		params.put("expressionIndex", "1");
		String result = this.executeMoveExpression("admin", params);
		assertEquals(Action.SUCCESS, result);
		INavigatorShowletConfigAction action = (INavigatorShowletConfigAction) this.getAction();
		Showlet showlet = action.getShowlet();
		assertNotNull(showlet);
		ApsProperties props = showlet.getConfig();
		assertEquals(1, props.size());
		assertEquals("parent.subtree(2) + current + abs(1).subtree(2)", props.getProperty("navSpec"));
		List<NavigatorExpression> expressions = action.getExpressions();
		assertEquals(3, expressions.size());
		NavigatorExpression expression1 = expressions.get(1);
		assertEquals(NavigatorExpression.SPEC_CURRENT_PAGE_ID, expression1.getSpecId());
		assertTrue(expression1.getOperatorId()<0);
	}
	
	public void testExecuteMoveExpression_2() throws Throwable {
		Map<String, String> params = new HashMap<String, String>();
		params.put("pageCode", "pagina_2");
		params.put("frame", "0");
		params.put("showletTypeCode", "leftmenu");
		params.put("navSpec", "parent.subtree(2)+abs(1).subtree(2)+current");
		params.put("movement", INavigatorShowletConfigAction.MOVEMENT_UP_CODE);
		params.put("expressionIndex", "2");
		String result = this.executeMoveExpression("admin", params);
		assertEquals(Action.SUCCESS, result);
		INavigatorShowletConfigAction action = (INavigatorShowletConfigAction) this.getAction();
		Showlet showlet = action.getShowlet();
		assertNotNull(showlet);
		ApsProperties props = showlet.getConfig();
		assertEquals(1, props.size());
		assertEquals("parent.subtree(2) + current + abs(1).subtree(2)", props.getProperty("navSpec"));
		List<NavigatorExpression> expressions = action.getExpressions();
		assertEquals(3, expressions.size());
		NavigatorExpression expression1 = expressions.get(1);
		assertEquals(NavigatorExpression.SPEC_CURRENT_PAGE_ID, expression1.getSpecId());
		assertTrue(expression1.getOperatorId()<0);
	}
	
	public void testExecuteMoveExpression_3() throws Throwable {
		Map<String, String> params = new HashMap<String, String>();
		params.put("pageCode", "pagina_2");
		params.put("frame", "0");
		params.put("showletTypeCode", "leftmenu");
		params.put("navSpec", "parent.subtree(2)+abs(1).subtree(2)+current");
		params.put("movement", INavigatorShowletConfigAction.MOVEMENT_UP_CODE);
		params.put("expressionIndex", "3");//INDICE SUPERIORE AL SIZE
		String result = this.executeMoveExpression("admin", params);
		assertEquals(Action.SUCCESS, result);
		INavigatorShowletConfigAction action = (INavigatorShowletConfigAction) this.getAction();
		Showlet showlet = action.getShowlet();
		assertNotNull(showlet);
		ApsProperties props = showlet.getConfig();
		assertEquals(1, props.size());
		assertEquals("parent.subtree(2) + abs(1).subtree(2) + current", props.getProperty("navSpec"));
		List<NavigatorExpression> expressions = action.getExpressions();
		assertEquals(3, expressions.size());
		NavigatorExpression expression2 = expressions.get(2);
		assertEquals(NavigatorExpression.SPEC_CURRENT_PAGE_ID, expression2.getSpecId());
		assertTrue(expression2.getOperatorId()<0);
	}
	
	public void testExecuteRemoveExpression_1() throws Throwable {
		Map<String, String> params = new HashMap<String, String>();
		params.put("pageCode", "pagina_2");
		params.put("frame", "0");
		params.put("showletTypeCode", "leftmenu");
		params.put("navSpec", "parent.subtree(2)+abs(1).subtree(2)+current");
		params.put("expressionIndex", "1");
		String result = this.executeRemoveExpression("admin", params);
		assertEquals(Action.SUCCESS, result);
		INavigatorShowletConfigAction action = (INavigatorShowletConfigAction) this.getAction();
		Showlet showlet = action.getShowlet();
		assertNotNull(showlet);
		ApsProperties props = showlet.getConfig();
		assertEquals(1, props.size());
		assertEquals("parent.subtree(2) + current", props.getProperty("navSpec"));
		List<NavigatorExpression> expressions = action.getExpressions();
		assertEquals(2, expressions.size());
		NavigatorExpression expression1 = expressions.get(1);
		assertEquals(NavigatorExpression.SPEC_CURRENT_PAGE_ID, expression1.getSpecId());
		assertTrue(expression1.getOperatorId()<0);
	}
	
	public void testExecuteRemoveExpression_2() throws Throwable {
		Map<String, String> params = new HashMap<String, String>();
		params.put("pageCode", "pagina_2");
		params.put("frame", "0");
		params.put("showletTypeCode", "leftmenu");
		params.put("navSpec", "parent.subtree(2)+abs(1).subtree(2)+current");
		params.put("expressionIndex", "3");//INDICE SUPERIORE AL SIZE
		String result = this.executeRemoveExpression("admin", params);
		assertEquals(Action.SUCCESS, result);
		INavigatorShowletConfigAction action = (INavigatorShowletConfigAction) this.getAction();
		Showlet showlet = action.getShowlet();
		assertNotNull(showlet);
		ApsProperties props = showlet.getConfig();
		assertEquals(1, props.size());
		assertEquals("parent.subtree(2) + abs(1).subtree(2) + current", props.getProperty("navSpec"));
		List<NavigatorExpression> expressions = action.getExpressions();
		assertEquals(3, expressions.size());
		NavigatorExpression expression2 = expressions.get(2);
		assertEquals(NavigatorExpression.SPEC_CURRENT_PAGE_ID, expression2.getSpecId());
		assertTrue(expression2.getOperatorId()<0);
	}
	
	public void testFailureAddExpression_1() throws Throwable {
		Map<String, String> params = new HashMap<String, String>();
		params.put("pageCode", "pagina_2");
		params.put("frame", "0");
		params.put("showletTypeCode", "leftmenu");
		params.put("navSpec", "parent.subtree(2)+abs(1).subtree(2)+current");
		String result = this.executeAddExpression("admin", params);
		assertEquals(Action.INPUT, result);
		ActionSupport action = this.getAction();
		Map<String, List<String>> fieldErrors = action.getFieldErrors();
		assertEquals(1, fieldErrors.size());
		assertEquals(1, fieldErrors.get("specId").size());
		
		NavigatorShowletConfigAction navAction = (NavigatorShowletConfigAction) action;
		Showlet showlet = navAction.getShowlet();
		assertNotNull(showlet);
		ApsProperties props = showlet.getConfig();
		assertEquals(0, props.size());
		assertEquals("parent.subtree(2)+abs(1).subtree(2)+current", navAction.getNavSpec());
		assertEquals(3, navAction.getExpressions().size());
	}
	
	public void testFailureAddExpression_2() throws Throwable {
		Map<String, String> params = new HashMap<String, String>();
		params.put("pageCode", "pagina_2");
		params.put("frame", "0");
		params.put("showletTypeCode", "leftmenu");
		params.put("navSpec", "parent.subtree(2)+abs(1).subtree(2)+current");
		params.put("specId", "3");
		params.put("specSuperLevel", "-2");
		String result = this.executeAddExpression("admin", params);
		assertEquals(Action.INPUT, result);
		ActionSupport action = this.getAction();
		assertEquals(1, action.getActionErrors().size());
		
		NavigatorShowletConfigAction navAction = (NavigatorShowletConfigAction) action;
		Showlet showlet = navAction.getShowlet();
		assertNotNull(showlet);
		ApsProperties props = showlet.getConfig();
		assertEquals(0, props.size());
		assertEquals("parent.subtree(2)+abs(1).subtree(2)+current", navAction.getNavSpec());
		assertEquals(3, navAction.getExpressions().size());
	}
	
	public void testFailureAddExpression_3() throws Throwable {
		Map<String, String> params = new HashMap<String, String>();
		params.put("pageCode", "pagina_2");
		params.put("frame", "0");
		params.put("showletTypeCode", "leftmenu");
		params.put("navSpec", "parent.subtree(2)+current");
		params.put("specId", "4");
		params.put("specAbsLevel", "-1");
		String result = this.executeAddExpression("admin", params);
		assertEquals(Action.INPUT, result);
		ActionSupport action = this.getAction();
		assertEquals(1, action.getActionErrors().size());
		
		NavigatorShowletConfigAction navAction = (NavigatorShowletConfigAction) action;
		Showlet showlet = navAction.getShowlet();
		assertNotNull(showlet);
		ApsProperties props = showlet.getConfig();
		assertEquals(0, props.size());
		assertEquals("parent.subtree(2)+current", navAction.getNavSpec());
		assertEquals(2, navAction.getExpressions().size());
	}
	
	public void testFailureAddExpression_4() throws Throwable {
		Map<String, String> params = new HashMap<String, String>();
		params.put("pageCode", "pagina_2");
		params.put("frame", "0");
		params.put("showletTypeCode", "leftmenu");
		params.put("navSpec", "");
		params.put("specId", "5");
		params.put("specCode", "  ");
		params.put("operatorId", "3");
		params.put("operatorSubtreeLevel", "-1");
		String result = this.executeAddExpression("admin", params);
		assertEquals(Action.INPUT, result);
		ActionSupport action = this.getAction();
		assertEquals(2, action.getActionErrors().size());
		
		NavigatorShowletConfigAction navAction = (NavigatorShowletConfigAction) action;
		Showlet showlet = navAction.getShowlet();
		assertNotNull(showlet);
		ApsProperties props = showlet.getConfig();
		assertEquals(0, props.size());
		assertEquals("", navAction.getNavSpec());
		assertEquals(0, navAction.getExpressions().size());
	}
	
	public void testExecuteAddExpression_1() throws Throwable {
		Map<String, String> params = new HashMap<String, String>();
		params.put("pageCode", "pagina_2");
		params.put("frame", "0");
		params.put("showletTypeCode", "leftmenu");
		params.put("navSpec", "abs(1).subtree(2)");
		params.put("specId", String.valueOf(NavigatorExpression.SPEC_PARENT_PAGE_ID));
		params.put("operatorId", String.valueOf(NavigatorExpression.OPERATOR_CHILDREN_ID));
		String result = this.executeAddExpression("admin", params);
		assertEquals(Action.SUCCESS, result);
		
		NavigatorShowletConfigAction action = (NavigatorShowletConfigAction) this.getAction();
		assertEquals("abs(1).subtree(2) + parent.children", action.getNavSpec());
	}
	
	public void testExecuteAddExpression_2() throws Throwable {
		Map<String, String> params = new HashMap<String, String>();
		params.put("pageCode", "pagina_2");
		params.put("frame", "0");
		params.put("showletTypeCode", "leftmenu");
		params.put("navSpec", "");
		params.put("specId", String.valueOf(NavigatorExpression.SPEC_ABS_ID));
		params.put("specAbsLevel", "1");
		params.put("operatorId", String.valueOf(NavigatorExpression.OPERATOR_SUBTREE_ID));
		params.put("operatorSubtreeLevel", "2");
		String result = this.executeAddExpression("admin", params);
		assertEquals(Action.SUCCESS, result);
		
		NavigatorShowletConfigAction action = (NavigatorShowletConfigAction) this.getAction();
		assertEquals("abs(1).subtree(2)", action.getNavSpec());
	}
	
	public void testSave() throws Throwable {
		String pageCode = "pagina_2";
		int frame = 0;
		IPage page = this._pageManager.getPage(pageCode);
		Showlet showlet = page.getShowlets()[frame];
		assertNull(showlet);
		try {
			this.setUserOnSession("admin");
			this.initAction("/do/Page/SpecialShowlet/Navigator", "saveNavigatorConfig");
			this.addParameter("pageCode", pageCode);
			this.addParameter("frame", String.valueOf(frame));
			this.addParameter("showletTypeCode", "leftmenu");
			this.addParameter("navSpec", "parent.subtree(2)");
			String result = this.executeAction();
			assertEquals("configure", result);
			page = this._pageManager.getPage(pageCode);
			showlet = page.getShowlets()[frame];
			assertNotNull(showlet);
			assertEquals("leftmenu", showlet.getType().getCode());
			assertEquals(1, showlet.getConfig().size());
			assertEquals("parent.subtree(2)", showlet.getConfig().getProperty("navSpec"));
		} catch (Throwable t) {
			throw t;
		} finally {
			page = this._pageManager.getPage(pageCode);
			page.getShowlets()[frame] = null;
			this._pageManager.updatePage(page);
		}
	}
	
	private String executeConfigNavigator(String userName, 
			String pageCode, String frame, String showletTypeCode) throws Throwable {
		this.setUserOnSession(userName);
		this.initAction("/do/Page/SpecialShowlet", "navigatorConfig");
		this.addParameter("pageCode", pageCode);
		this.addParameter("frame", frame);
		if (null != showletTypeCode && showletTypeCode.trim().length()>0) {
			this.addParameter("showletTypeCode", showletTypeCode);
		}
		return this.executeAction();
	}
	
	private String executeMoveExpression(String userName, Map<String, String> params) throws Throwable {
		this.setUserOnSession(userName);
		this.initAction("/do/Page/SpecialShowlet/Navigator", "moveExpression");
		this.addParameters(params);
		return this.executeAction();
	}
	
	private String executeRemoveExpression(String userName, Map<String, String> params) throws Throwable {
		this.setUserOnSession(userName);
		this.initAction("/do/Page/SpecialShowlet/Navigator", "removeExpression");
		this.addParameters(params);
		return this.executeAction();
	}
	
	private String executeAddExpression(String userName, Map<String, String> params) throws Throwable {
		this.setUserOnSession(userName);
		this.initAction("/do/Page/SpecialShowlet/Navigator", "addExpression");
		this.addParameters(params);
		return this.executeAction();
	}
	
	private void init() throws Exception {
    	try {
    		this._pageManager = (IPageManager) this.getService(SystemConstants.PAGE_MANAGER);
    	} catch (Throwable t) {
            throw new Exception(t);
        }
    }
    
    /**
	 * @uml.property  name="_pageManager"
	 * @uml.associationEnd  
	 */
    private IPageManager _pageManager = null;
	
}
