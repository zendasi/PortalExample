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
package test.com.agiletec.apsadmin.portal;

import test.com.agiletec.apsadmin.ApsAdminBaseTestCase;

import com.agiletec.aps.system.SystemConstants;
import com.agiletec.aps.system.services.page.IPage;
import com.agiletec.aps.system.services.page.IPageManager;
import com.agiletec.aps.system.services.page.Showlet;
import com.opensymphony.xwork2.Action;

/**
 * @author E.Santoboni
 */
public class TestPageConfigAction extends ApsAdminBaseTestCase {
	
	@Override
	protected void setUp() throws Exception {
        super.setUp();
        this.init();
    }
	
	public void testConfigPage() throws Throwable {
		String result = this.executeConfigPage("pagina_1", "editorCoach");
		assertEquals("userNotAllowed", result);
		result = this.executeConfigPage("pagina_1", "pageManagerCoach");
		assertEquals("pageTree", result);
		result = this.executeConfigPage("pagina_1", "admin");
		assertEquals(Action.SUCCESS, result);
		result = this.executeConfigPage("coach_page", "pageManagerCoach");
		assertEquals(Action.SUCCESS, result);
	}
	
	private String executeConfigPage(String selectedPageCode, String username) throws Throwable {
		this.setUserOnSession(username);
		this.initAction("/do/Page", "configure");
		this.addParameter("selectedNode", selectedPageCode);
		return this.executeAction();
	}
	
	public void testConfigEditFrame() throws Throwable {
		String result = this.executeEditFrame("coach_page", -1, "pageManagerCoach");//invalid frame
		assertEquals("pageTree", result);
		assertEquals(1, this.getAction().getActionErrors().size());
		
		result = this.executeEditFrame("coach_page", 10, "pageManagerCoach");//frame out of range
		assertEquals("pageTree", result);
		assertEquals(1, this.getAction().getActionErrors().size());
		
		result = this.executeEditFrame("coach_page", 2, "pageManagerCoach");
		assertEquals("configureSpecialShowlet", result);
		assertEquals(0, this.getAction().getActionErrors().size());
		
		result = this.executeEditFrame("contentview", 2, "pageManagerCoach");//user not allowed
		assertEquals("pageTree", result);
		assertEquals(1, this.getAction().getActionErrors().size());
		
		result = this.executeEditFrame("contentview", 2, "admin");
		assertEquals("configureSpecialShowlet", result);
		assertEquals(0, this.getAction().getActionErrors().size());
		
		result = this.executeEditFrame("contentview", 1, "admin");
		assertEquals(Action.SUCCESS, result);
		assertEquals(0, this.getAction().getActionErrors().size());
	}
	
	private String executeEditFrame(String pageCode, int frame, String username) throws Throwable {
		this.setUserOnSession(username);
		this.initAction("/do/Page", "editFrame");
		this.addParameter("pageCode", pageCode);
		this.addParameter("frame", String.valueOf(frame));
		return this.executeAction();
	}
	
	public void testJoinShowlet() throws Throwable {
		String pageCode = "pagina_1";
		int frame = 1;
		IPage pagina_1 = this._pageManager.getPage(pageCode);
		try {
			assertNull(pagina_1.getShowlets()[frame]);
			String result = this.executeJoinShowlet(pageCode, frame, "content_viewer", "admin");
			assertEquals("configureSpecialShowlet", result);
			result = this.executeJoinShowlet(pageCode, frame, "content_viewer", "pageManagerCoach");
			assertEquals("pageTree", result);
			assertEquals(1, this.getAction().getActionErrors().size());
		} catch (Throwable t) {
			throw t;
		} finally {
			pagina_1.getShowlets()[frame] = null;
			this._pageManager.updatePage(pagina_1);
		}
	}
	
	public void testJoinRemoveShowlet() throws Throwable {
		this.testJoinRemoveShowlet("login_form");
		this.testJoinRemoveShowlet("logic_type");
	}
	
	private void testJoinRemoveShowlet(String showletTypeCode) throws Throwable {
		String pageCode = "pagina_1";
		int frame = 1;
		IPage pagina_1 = this._pageManager.getPage(pageCode);
		try {
			assertNull(pagina_1.getShowlets()[frame]);
			String result = this.executeJoinShowlet(pageCode, frame, showletTypeCode, "pageManagerCoach");
			assertEquals("pageTree", result);
			result = this.executeJoinShowlet(pageCode, frame, showletTypeCode, "admin");
			assertEquals(Action.SUCCESS, result);
			pagina_1 = this._pageManager.getPage(pageCode);
			assertNotNull(pagina_1.getShowlets()[frame]);
			assertEquals(showletTypeCode, pagina_1.getShowlets()[frame].getType().getCode());
			
			result = this.executeTrashShowlet(pageCode, frame, "admin");
			assertEquals(Action.SUCCESS, result);
			pagina_1 = this._pageManager.getPage(pageCode);
			assertNotNull(pagina_1.getShowlets()[frame]);
			
			result = this.executeDeleteShowlet(pageCode, frame, "admin");
			assertEquals(Action.SUCCESS, result);
			pagina_1 = this._pageManager.getPage(pageCode);
			assertNull(pagina_1.getShowlets()[frame]);
		} catch (Throwable t) {
			throw t;
		} finally {
			pagina_1.getShowlets()[frame] = null;
			this._pageManager.updatePage(pagina_1);
		}
	}
	
	public void testTrashShowlet() throws Throwable {
		String pageCode = "contentview";
		int frame = 1;
		IPage contentview = this._pageManager.getPage(pageCode);
		Showlet showlet = contentview.getShowlets()[frame];
		try {
			assertNotNull(showlet);
			String result = this.executeTrashShowlet(pageCode, frame, "pageManagerCoach");
			assertEquals("pageTree", result);
			assertEquals(1, this.getAction().getActionErrors().size());
			result = this.executeTrashShowlet(pageCode, frame, "admin");
			assertEquals(Action.SUCCESS, result);
			IPage modifiedContentview = this._pageManager.getPage(pageCode);
			Showlet[] modifiedShowlets = modifiedContentview.getShowlets();
			assertNotNull(modifiedShowlets[frame]);
		} catch (Throwable t) {
			contentview = this._pageManager.getPage(pageCode);
			contentview.getShowlets()[frame] = showlet;
			this._pageManager.updatePage(contentview);
			throw t;
		}
	}
	
	public void testDeleteShowlet() throws Throwable {
		String pageCode = "contentview";
		int frame = 1;
		IPage contentview = this._pageManager.getPage(pageCode);
		Showlet showlet = contentview.getShowlets()[frame];
		try {
			assertNotNull(showlet);
			String result = this.executeDeleteShowlet(pageCode, frame, "pageManagerCoach");
			assertEquals("pageTree", result);
			assertEquals(1, this.getAction().getActionErrors().size());
			result = this.executeDeleteShowlet(pageCode, frame, "admin");
			assertEquals(Action.SUCCESS, result);
			IPage modifiedContentview = this._pageManager.getPage(pageCode);
			Showlet[] modifiedShowlets = modifiedContentview.getShowlets();
			assertNull(modifiedShowlets[frame]);
		} catch (Throwable t) {
			throw t;
		} finally {
			contentview = this._pageManager.getPage(pageCode);
			contentview.getShowlets()[frame] = showlet;
			this._pageManager.updatePage(contentview);
		}
	}
	
	private String executeJoinShowlet(String pageCode, int frame, String showletTypeCode, String username) throws Throwable {
		this.setUserOnSession(username);
		this.initAction("/do/Page", "joinShowlet");
		this.addParameter("pageCode", pageCode);
		this.addParameter("showletTypeCode", showletTypeCode);
		this.addParameter("frame", String.valueOf(frame));
		return this.executeAction();
	}
	
	private String executeTrashShowlet(String pageCode, int frame, String username) throws Throwable {
		this.setUserOnSession(username);
		this.initAction("/do/Page", "trashShowletFromPage");
		this.addParameter("pageCode", pageCode);
		this.addParameter("frame", String.valueOf(frame));
		return this.executeAction();
	}
	
	private String executeDeleteShowlet(String pageCode, int frame, String username) throws Throwable {
		this.setUserOnSession(username);
		this.initAction("/do/Page", "deleteShowletFromPage");
		this.addParameter("pageCode", pageCode);
		this.addParameter("frame", String.valueOf(frame));
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
