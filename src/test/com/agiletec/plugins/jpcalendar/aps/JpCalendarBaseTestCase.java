/*
*
* Copyright 2009 AgileTec s.r.l. (http://www.agiletec.it) All rights reserved.
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
* Copyright 2009 AgileTec s.r.l. (http://www.agiletec.it) All rights reserved.
*
*/
package test.com.agiletec.plugins.jpcalendar.aps;

import test.com.agiletec.ConfigTestUtils;
import test.com.agiletec.aps.BaseTestCase;
import test.com.agiletec.plugins.jpcalendar.JpcalendarConfigTestUtils;

/**
 * BaseTestCase Class for jpcalendar aps tests.
 * You have to comment "getConfigUtils()" 
 * Method for testing plugin into custom jAPS 2.0 project.
 */
public class JpCalendarBaseTestCase extends BaseTestCase {
	
	@Override
	protected ConfigTestUtils getConfigUtils() {
		return new JpcalendarConfigTestUtils();
	}
	
}
