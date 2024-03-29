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
package test.com.agiletec.aps.services.mock;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.agiletec.aps.system.common.AbstractDAO;

/**
 * @author E.Santoboni
 */
public class MockShowletTypeDAO extends AbstractDAO {
	
	public void deleteShowletType(String showletTypeCode) {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = this.getConnection();
			conn.setAutoCommit(false);
			stat = conn.prepareStatement(DELETE_SHOWLET_TYPE);
			stat.setString(1, showletTypeCode);
			stat.executeUpdate();
			conn.commit();
		} catch (Throwable t) {
			this.executeRollback(conn);
			processDaoException(t, "Error deleting showlet type", "deleteShowletType");
		} finally {
			closeDaoResources(null, stat, conn);
		}
	}
	
	/**
	 * @uml.property  name="dELETE_SHOWLET_TYPE"
	 */
	private final String DELETE_SHOWLET_TYPE =
		"DELETE FROM showletcatalog WHERE code = ?";
	
}
