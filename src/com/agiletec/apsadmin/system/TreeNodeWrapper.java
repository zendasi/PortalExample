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
package com.agiletec.apsadmin.system;

import java.util.Iterator;
import java.util.Set;

import com.agiletec.aps.system.common.tree.ITreeNode;
import com.agiletec.aps.system.common.tree.TreeNode;

/**
 * @author E.Santoboni
 */
public class TreeNodeWrapper extends TreeNode {
	
	public TreeNodeWrapper(ITreeNode node) {
		this.setCode(node.getCode());
		Set<Object> codes = node.getTitles().keySet();
		Iterator<Object> iterKey = codes.iterator();
		while (iterKey.hasNext()) {
			String key = (String) iterKey.next();
			String title = node.getTitles().getProperty(key);
			this.getTitles().put(key, title);
		}
		this._empty = (null == node.getChildren() || node.getChildren().length == 0);
		this.setParent(node.getParent());
	}
	
	public boolean isOpen() {
		return _open;
	}
	public void setOpen(boolean open) {
		this._open = open;
	}
	
	public boolean isEmpty() {
		return _empty;
	}
	public void setEmpty(boolean empty) {
		this._empty = empty;
	}
	
	/**
	 * @uml.property  name="_empty"
	 */
	private boolean _empty;
	/**
	 * @uml.property  name="_open"
	 */
	private boolean _open;
	
}
