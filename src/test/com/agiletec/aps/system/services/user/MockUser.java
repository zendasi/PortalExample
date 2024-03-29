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
package test.com.agiletec.aps.system.services.user;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.agiletec.aps.system.services.group.Group;
import com.agiletec.aps.system.services.role.Role;
import com.agiletec.aps.system.services.user.User;

public class MockUser extends User {
	
	public Set<Group> getGroups() {
		return _groups;
	}
	public void setGroups(Set<Group> groups) {
		Iterator<Group> iter = groups.iterator();
		while (iter.hasNext()) {
			Group group = (Group) iter.next();
			this.addAutority(group);
		}
		this._groups = groups;
	}
	public void addGroup(Group group) {
		this.addAutority(group);
		this._groups.add(group);
	}
	
	public Set<Role> getRoles() {
		return _roles;
	}
	public void setRoles(Set<Role> roles) {
		Iterator<Role> iter = roles.iterator();
		while (iter.hasNext()) {
			Role role = (Role) iter.next();
			this.addAutority(role);
		}
		this._roles = roles;
	}
	public void addRole(Role role) {
		this.addAutority(role);
		this._roles.add(role);
	}
	
	/**
	 * @uml.property  name="_groups"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="com.agiletec.aps.system.services.group.Group"
	 */
	private Set<Group> _groups = new HashSet<Group>();
    /**
	 * @uml.property  name="_roles"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="com.agiletec.aps.system.services.role.Role"
	 */
    private Set<Role> _roles = new HashSet<Role>();
	
}
