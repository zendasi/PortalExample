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

import com.agiletec.aps.system.common.tree.ITreeNode;

/**
 * Interface of the Action class which handles tree. 
 * @author   E.Santoboni
 */
public interface ITreeAction {
	
	/**
	 * Build the tree.
	 * This action method allows to set the collection of the "selected node" to build the showable tree.
	 * @return The result code.
	 */
	public String buildTree();
	
	/**
	 * Open a tree node.
	 * This action method allows to set the collection of the "selected node" to build the showable tree.
	 * @return The result code.
	 */
	//public String openTreeNode();
	
	/**
	 * Close a tree node.
	 * This action method allows to set the collection of the "selected node" to build the showable tree.
	 * @return The result code.
	 */
	//public String closeTreeNode();
	
	/**
	 * Open a tree node. This action method allows to set the collection of the "selected node" to build the showable tree.
	 * @return  The result code.
	 * @uml.property  name="allowedTreeRootNode"
	 * @uml.associationEnd  
	 */
	public ITreeNode getAllowedTreeRootNode();
	
	/**
	 * Return the root of the showable tree. The tree il build by the current "selected nodes".
	 * @return   The root of the showable tree.
	 * @uml.property  name="showableTree"
	 * @uml.associationEnd  
	 */
	public ITreeNode getShowableTree();
	
	public static final String ACTION_MARKER_OPEN = "open";
	public static final String ACTION_MARKER_CLOSE = "close";
	
}