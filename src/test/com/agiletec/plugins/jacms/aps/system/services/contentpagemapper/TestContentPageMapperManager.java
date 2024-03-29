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
package test.com.agiletec.plugins.jacms.aps.system.services.contentpagemapper;

import test.com.agiletec.aps.BaseTestCase;

import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.plugins.jacms.aps.system.JacmsSystemConstants;
import com.agiletec.plugins.jacms.aps.system.services.contentpagemapper.IContentPageMapperManager;

/**
 * @version 1.0
 * @author M.Casari
 */
public class TestContentPageMapperManager extends BaseTestCase {
	
    protected void setUp() throws Exception {
        super.setUp();
        this.init();
    }
    
    public void testGetContentPageMapper() throws ApsSystemException {
		String codePage = _contentPageMapperManager.getPageCode("ART1");
		assertEquals(codePage, "homepage");
	}
	
    public void testReloadContentPageMapper() throws ApsSystemException{   
        _contentPageMapperManager.reloadContentPageMapper();
        String codePage = _contentPageMapperManager.getPageCode("ART1");
        assertEquals(codePage, "homepage");
    }
    
    private void init() throws Exception {
    	try {
    		_contentPageMapperManager = (IContentPageMapperManager) this.getService(JacmsSystemConstants.CONTENT_PAGE_MAPPER_MANAGER);
    	} catch (Throwable t) {
            throw new Exception();
        }
    }
    
    /**
	 * @uml.property  name="_contentPageMapperManager"
	 * @uml.associationEnd  
	 */
    private IContentPageMapperManager _contentPageMapperManager = null;
    
}
