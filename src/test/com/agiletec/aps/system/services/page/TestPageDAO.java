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
package test.com.agiletec.aps.system.services.page;

import java.util.List;

import javax.sql.DataSource;

import test.com.agiletec.aps.BaseTestCase;

import com.agiletec.aps.system.SystemConstants;
import com.agiletec.aps.system.services.page.IPage;
import com.agiletec.aps.system.services.page.Page;
import com.agiletec.aps.system.services.page.PageDAO;
import com.agiletec.aps.system.services.page.Showlet;
import com.agiletec.aps.system.services.pagemodel.IPageModelManager;
import com.agiletec.aps.system.services.pagemodel.PageModel;
import com.agiletec.aps.system.services.showlettype.IShowletTypeManager;
import com.agiletec.aps.system.services.showlettype.ShowletType;
import com.agiletec.aps.util.ApsProperties;

/**
 * @author M.Diana
 */
public class TestPageDAO extends BaseTestCase {
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.init();
	}
	
    public void testLoadPageList() throws Throwable {
    	try {
			List<IPage> pages = _pageDao.loadPages();
            String value = null;
            boolean contains = false;
            for (int i=0; i<pages.size(); i++) {
    			IPage page = pages.get(i);
    			value = page.getCode();
    			if (value.equals("homepage")) {
    				contains = true;
    			}
    		}
    		assertTrue(contains);
        } catch (Throwable t) {
            throw t;
        }
	}
    
	public void testAddUpdateDeletePage() throws Throwable {
		Page newPageForTest = this.createPageForTest();
		try {
        	_pageDao.deletePage(newPageForTest);
        	_pageDao.addPage(newPageForTest);
        	List<IPage> pages = _pageDao.loadPages();
		    String value = null;
		    boolean contains = false;
		    for (int i=0; i<pages.size(); i++) {
    			IPage page = pages.get(i);
				value = page.getCode();
				if (value.equals("temp")) {
					assertEquals(page.getCode(), "temp");
					assertEquals(page.getGroup(), "free");
					assertEquals(page.getTitle("it"), "pagina temporanea");
					assertEquals(page.getModel().getCode(), "service");
					assertTrue(page.isShowable());
					Showlet[] showlets = page.getShowlets();
					contains = showlets[0].getConfig().contains("temp");
					assertEquals(contains, true);
					assertEquals(showlets[0].getPublishedContent(), "ART1");
					assertEquals(showlets[0].getType().getCode(), "content_viewer");
				}
			}
			this.updatePage(newPageForTest, _pageDao);
		} catch (Throwable t) {
			throw t;
		} finally {
			this.deletePage(newPageForTest, _pageDao);
		}
	}
	
	private void updatePage(Page pageToUpdate, PageDAO pageDAO) throws Throwable {
		pageToUpdate.setTitle("it", "pagina temporanea1");
		pageToUpdate.setShowable(false);
		Showlet showlet = new Showlet();
		ApsProperties config = new ApsProperties();
		config.setProperty("temp1", "temp1");		
		showlet.setConfig(config);
		showlet.setPublishedContent("ART11");
		ShowletType showletType = new ShowletType();
		showletType.setCode("content_viewer");
		showlet.setType(showletType);
		Showlet[] modifiesShowlets = {showlet};
		pageToUpdate.setShowlets(modifiesShowlets);
		try {
			pageDAO.updatePage(pageToUpdate);
			List<IPage> pages = pageDAO.loadPages();
	        String value = null;
	        for (int i=0; i<pages.size(); i++) {
    			IPage page = pages.get(i);
				value = page.getCode();
				if (value.equals("temp")) {
					assertEquals(page.getCode(), "temp");
					assertEquals(page.getGroup(), "free");
					assertEquals(page.getTitle("it"), "pagina temporanea1");
					assertEquals(page.getModel().getCode(), "service");
					assertFalse(page.isShowable());
					Showlet[] showlets = page.getShowlets();
					assertTrue(showlets[0].getConfig().contains("temp1"));
					assertEquals(showlets[0].getPublishedContent(), "ART11");
					assertEquals(showlets[0].getType().getCode(), "content_viewer");
				}
			}
        } catch (Throwable t) {
            throw t;
        }
	}
	
	private void deletePage(Page page, PageDAO pageDAO) throws Throwable {
		try {
        	pageDAO.deletePage(page);
        } catch (Throwable e) {
        	throw e;
        }
        List<IPage> pages = null;
        try {
        	pages = pageDAO.loadPages();
        } catch (Throwable t) {
        	throw t;
        }
        IPage currentPage = null;
        String value = null;
        boolean contains = false;
        for (int i=0; i<pages.size(); i++) {
        	currentPage = pages.get(i);
			value = currentPage.getCode();
			if (value.equals("temp")) {
				contains = true;
			}
		}
		assertEquals(contains, false);        
	}
	
	private Page createPageForTest() {
		Page page = new Page();
		page.setCode("temp");
		Page parentPage = new Page();
		parentPage.setCode("service");
		page.setParent(parentPage);
		PageModel pageModel = new PageModel();
		pageModel.setCode("service");
		page.setModel(pageModel);
		page.setGroup("free");
		page.setShowable(true);
		page.setTitle("it", "temptitle");
		ApsProperties titles = new ApsProperties();
		titles.setProperty("it", "pagina temporanea");
		page.setTitles(titles);
		Showlet showlet = new Showlet();
		ApsProperties config = new ApsProperties();
		config.setProperty("temp", "temp");		
		showlet.setConfig(config);
		showlet.setPublishedContent("ART1");
		ShowletType showletType = new ShowletType();
		showletType.setCode("content_viewer");
		showlet.setType(showletType);
		Showlet[] showlets = {showlet};
		page.setShowlets(showlets);
		return page;
	}
	
	private void init() throws Exception {
    	try {
    		DataSource dataSource = (DataSource) this.getApplicationContext().getBean("portDataSource");
    		_pageDao = new PageDAO();
    		_pageDao.setDataSource(dataSource);
    		IPageModelManager pageModelManager = (IPageModelManager) this.getService(SystemConstants.PAGE_MODEL_MANAGER);
    		IShowletTypeManager showletTypeManager = (IShowletTypeManager) this.getService(SystemConstants.SHOWLET_TYPE_MANAGER);
    		this._pageDao.setPageModelManager(pageModelManager);
    		this._pageDao.setShowletTypeManager(showletTypeManager);
		} catch (Throwable e) {
			throw new Exception(e);
		}
	}
    
    /**
	 * @uml.property  name="_pageDao"
	 * @uml.associationEnd  
	 */
    private PageDAO _pageDao;
	
}
