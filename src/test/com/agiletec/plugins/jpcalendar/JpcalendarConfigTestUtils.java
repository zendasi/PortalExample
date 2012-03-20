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
package test.com.agiletec.plugins.jpcalendar;

import test.com.agiletec.ConfigTestUtils;

public class JpcalendarConfigTestUtils extends ConfigTestUtils {
	
	protected String[] getSpringConfigFilePaths() {
    	String[] filePaths = new String[4];
		filePaths[0] = "admin/test/testSystemConfig.xml";
		filePaths[1] = "WebContent/WEB-INF/conf/managers/**/**.xml";
		filePaths[2] = "WebContent/WEB-INF/apsadmin/conf/**/**.xml";
		filePaths[3] = "WebContent/WEB-INF/plugins/jpcalendar/conf/**/**.xml";
		return filePaths;
    }
	
}
