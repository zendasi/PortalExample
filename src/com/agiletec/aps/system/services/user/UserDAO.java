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
package com.agiletec.aps.system.services.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.agiletec.aps.system.common.AbstractDAO;
import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.aps.util.IApsEncrypter;

/**
 * Data Access Object per gli oggetti Utente.
 * @author M.Diana - E.Santoboni
 */
public class UserDAO extends AbstractDAO implements IUserDAO {

	/**
	 * Carica e restituisce la lista completa di utenti presenti nel db.
	 * @return La lista completa di utenti (oggetti User)
	 */
	public List<UserDetails> loadUsers() {
		Connection conn = null;
		List<UserDetails> users = null;
		Statement stat = null;
		ResultSet res = null;
		try {
			conn = this.getConnection();
			stat = conn.createStatement();
			res = stat.executeQuery(LOAD_USERS);
			users = this.loadUsers(res);
		} catch (Throwable t) {
			processDaoException(t, "Error loading the users list", "loadUsersList");
		} finally {
			closeDaoResources(res, stat, conn);
		}
		return users;
	}

	public List<UserDetails> searchUsers(String text) {
		if (null == text) text = "";
		Connection conn = null;
		List<UserDetails> users = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		try {
			conn = this.getConnection();
			stat = conn.prepareStatement(SEARCH_USERS_BY_TEXT);
			stat.setString(1, "%"+text+"%");
			res = stat.executeQuery();
			users = this.loadUsers(res);
		} catch (Throwable t) {
			processDaoException(t, "Error while searching users", "searchUsers");
		} finally {
			closeDaoResources(res, stat, conn);
		}
		return users;
	}

	protected List<UserDetails> loadUsers(ResultSet result) throws SQLException {
		List<UserDetails> users = new ArrayList<UserDetails>();
		User user = null;
		while (result.next()) {
			String userName = result.getString(1);
			user = new User();
			user.setUsername(userName);
			user.setPassword(result.getString(2));
			user.setCreationDate(result.getDate(3));
			user.setLastAccess(result.getDate(4));
			user.setLastPasswordChange(result.getDate(5));
			int activeId = result.getInt(6);
			user.setDisabled(activeId!=1);
			users.add(user);
		}
		return users;
	}

	/**
	 * Carica un'utente corrispondente alla userName e password immessa. null
	 * se non vi è nessun utente corrispondente.
	 * @param username Nome utente dell'utente cercato
	 * @param password password dell'utente cercato
	 * @return L'oggetto utente corrispondente ai parametri richiesti, oppure
	 *         null se non vi è nessun utente corrispondente.
	 */
	public UserDetails loadUser(String username, String password) {
		UserDetails user = null;
		try {
			String encrypdedPassword = this.getEncryptedPassword(password);
			user = this.executeLoadingUser(username, encrypdedPassword);
			if (null == user) {
				user = this.executeLoadingUser(username, password);
			}
			if (null != user && user instanceof AbstractUser) {
				((AbstractUser) user).setPassword(password);
			}
		} catch (Throwable t) {
			processDaoException(t, "Error while loading the user " + username, "loadUser");
		}
		return user;
	}

	private UserDetails executeLoadingUser(String username, String password) {
		Connection conn = null;
		UserDetails user = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		try {
			conn = this.getConnection();
			stat = conn.prepareStatement(LOAD_USER);
			stat.setString(1, username);
			stat.setString(2, password);
			res = stat.executeQuery();
			user = this.createUserFromRecord(res);
		} catch (Throwable t) {
			processDaoException(t, "Error while loading the user " + username, "executeLoadingUser");
		} finally {
			closeDaoResources(res, stat, conn);
		}
		return user;
	}



	/**
	 * Carica un'utente corrispondente alla userName immessa. null
	 * se non vi è nessun utente corrispondente.
	 * @param username Nome utente dell'utente cercato.
	 * @return L'oggetto utente corrispondente ai parametri richiesti, oppure
	 *         null se non vi è nessun utente corrispondente.
	 */
	public UserDetails loadUser(String username) {
		Connection conn = null;
		User user = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		try {
			conn = this.getConnection();
			stat = conn.prepareStatement(LOAD_USER_FROM_USERNAME);
			stat.setString(1, username);
			res = stat.executeQuery();
			user = this.createUserFromRecord(res);
		} catch (Throwable t) {
			processDaoException(t, "Error while loading the user " + username, "loadUser");
		} finally {
			closeDaoResources(res, stat, conn);
		}
		return user;
	}

	/**
	 * Cancella l'utente.
	 * @param user L'oggetto di tipo User relativo all'utente da cancellare.
	 */
	public void deleteUser(UserDetails user) {
		this.deleteUser(user.getUsername());
	}

	/**
	 * Cancella l'utente corrispondente alla userName immessa.
	 * @param username Il nome identificatore dell'utente.
	 */
	public void deleteUser(String username) {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = this.getConnection();
			conn.setAutoCommit(false);
			this.removeUserGroups(username, conn);
			this.removeUserRoles(username, conn);
			stat = conn.prepareStatement(DELETE_USER);
			stat.setString(1, username);
			stat.executeUpdate();
			conn.commit();
		} catch (Throwable t) {
			this.executeRollback(conn);
			processDaoException(t, "Error deleting a user", "deleteUser");
		} finally {
			closeDaoResources(null, stat, conn);
		}
	}

	/**
	 * Aggiunge un nuovo utente.
	 * @param user Oggetto di tipo User relativo all'utente da aggiungere.
	 */
	public void addUser(UserDetails user) {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = this.getConnection();
			conn.setAutoCommit(false);
			stat = conn.prepareStatement(ADD_USER);
			stat.setString(1, user.getUsername());
			String encrypdedPassword = this.getEncryptedPassword(user.getPassword());
			stat.setString(2, encrypdedPassword);
			stat.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
			if (!user.isDisabled()) {
				stat.setInt(4, 1);
			} else stat.setInt(4, 0);
			stat.executeUpdate();
			conn.commit();
		} catch (Throwable t) {
			this.executeRollback(conn);
			processDaoException(t, "Error adding a new user", "addUser");
		} finally {
			closeDaoResources(null, stat, conn);
		}
	}

	/**
	 * Aggiorna un utente già presente con nuovi valori 
	 * (tranne la username che è fissa).
	 * @param user Oggetto di tipo User relativo all'utente da aggiornare.
	 */
	public void updateUser(UserDetails user) {
		User japsUser = ((user instanceof User) ? (User) user : null);
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = this.getConnection();
			conn.setAutoCommit(false);
			stat = conn.prepareStatement(UPDATE_USER);
			stat.setString(1, user.getPassword());
			if (null != japsUser && null != japsUser.getLastAccess()) {
				stat.setDate(2, new java.sql.Date(japsUser.getLastAccess().getTime()));
			} else {
				stat.setNull(2, Types.DATE);
			}
			if (null != japsUser && null != japsUser.getLastPasswordChange()) {
				stat.setDate(3, new java.sql.Date(japsUser.getLastPasswordChange().getTime()));
			} else {
				stat.setNull(3, Types.DATE);
			}
			if (null != japsUser) {
				if (!japsUser.isDisabled()) {
					stat.setInt(4, 1);
				} else stat.setInt(4, 0);
			} else {
				stat.setNull(4, Types.NUMERIC);
			}

			stat.setString(5, user.getUsername());
			stat.executeUpdate();
			conn.commit();
		} catch (Throwable t) {
			this.executeRollback(conn);
			processDaoException(t, "Error while updating the user " + user.getUsername(), "updateUser");
		} finally {
			closeDaoResources(null, stat, conn);
		}
	}

	@Override
	public void changePassword(String username, String password) {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = this.getConnection();
			conn.setAutoCommit(false);
			stat = conn.prepareStatement(CHANGE_PASSWORD);
			String encrypdedPassword = this.getEncryptedPassword(password);
			stat.setString(1, encrypdedPassword);
			stat.setDate(2, new java.sql.Date(new Date().getTime()));
			stat.setString(3, username);
			stat.executeUpdate();
			conn.commit();
		} catch (Throwable t) {
			this.executeRollback(conn);
			processDaoException(t, "Error updating the password for the user " + username, "changePassword");
		} finally {
			closeDaoResources(null, stat, conn);
		}
	}

	@Override
	public void updateLastAccess(String username) {
		Connection conn = null;
		PreparedStatement stat = null;
		try {
			conn = this.getConnection();
			conn.setAutoCommit(false);
			stat = conn.prepareStatement(UPDATE_LAST_ACCESS);
			stat.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
			stat.setString(2, username);
			stat.executeUpdate();
			conn.commit();
		} catch (Throwable t) {
			this.executeRollback(conn);
			processDaoException(t, "Error updating the password for the user " + username, "updateLastAccess");
		} finally {
			closeDaoResources(null, stat, conn);
		}
	}

	private void removeUserRoles(String username, Connection conn) {
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(DELETE_USER_ROLE);
			stat.setString(1, username);
			stat.executeUpdate();
		} catch (Throwable t) {
			processDaoException(t, "Error deleting the association between the user and his roles" , "removeUserRoles");
		} finally {
			closeDaoResources(null, stat);
		}
	}

	/**
	 * Crea un utente leggendo i valori dal record corrente del ResultSet passato.
	 * Attenzione: la query di origine del ResultSet deve avere nella select list i
	 * campi esattamente in questo numero e ordine:
	 * 1=username, 2=passwd
	 * @param res Il ResultSet da cui leggere i valori
	 * @return L'oggetto user popolato.
	 * @throws SQLException
	 */
	protected User createUserFromRecord(ResultSet res) throws SQLException {
		User user = null;
		if (res.next()) {
			user = new User();
			user.setUsername(res.getString(1));
			user.setPassword(res.getString(2));
			user.setCreationDate(res.getDate(3));
			user.setLastAccess(res.getDate(4));
			user.setLastPasswordChange(res.getDate(5));
			int activeId = res.getInt(6);
			user.setDisabled(activeId!=1);
		}
		return user;
	}

	private void removeUserGroups(String username, Connection conn) throws ApsSystemException {
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(DELETE_USER_GROUP);
			stat.setString(1, username);
			stat.executeUpdate();
		} catch (Throwable t) {
			processDaoException(t, "Error deleting the association between the user and his roles", 
			"removeUserGroups");
		} finally {
			closeDaoResources(null, stat);
		}
	}

	@Override
	public List<String> loadUsernamesForGroup(String groupName) {
		Connection conn = null;
		List<String> usernames = new ArrayList<String>();
		PreparedStatement stat = null;
		ResultSet res = null;
		try {
			conn = this.getConnection();
			stat = conn.prepareStatement(LOAD_USERNAMES_FROM_GROUP);
			stat.setString(1, groupName);
			res = stat.executeQuery();
			while (res.next()) {
				usernames.add(res.getString(1));
			}
		} catch (Throwable t) {
			processDaoException(t, "Error getting the usernames sharing the same group", 
					"loadUsernamesForGroup");
		} finally {
			closeDaoResources(res, stat, conn);
		}
		return usernames;
	}

	/**
	 * Carica gli utenti membri di un gruppo.
	 * @param groupName Il nome del grupo tramite il quale cercare gli utenti.
	 * @return La lista degli utenti (oggetti User) membri del gruppo specificato.
	 */
	public List<UserDetails> loadUsersForGroup(String groupName) {
		Connection conn = null;
		List<UserDetails> users = null;
		PreparedStatement stat = null;
		ResultSet res = null;
		try {
			conn = this.getConnection();
			stat = conn.prepareStatement(LOAD_USERS_FROM_GROUP);
			stat.setString(1, groupName);
			res = stat.executeQuery();
			users = this.loadUsers(res);
		} catch (Throwable t) {
			processDaoException(t, "Error loading the list of users members of the group", 
					"loadUsersForGroup");
		} finally {
			closeDaoResources(res, stat, conn);
		}
		return users;
	}

	protected String getEncryptedPassword(String password) throws ApsSystemException {
		String encrypted = password;
		if (null != this.getEncrypter()) {
			encrypted = this.getEncrypter().encrypt(password);
		}
		return encrypted;
	}

	protected IApsEncrypter getEncrypter() {
		return _encrypter;
	}
	public void setEncrypter(IApsEncrypter encrypter) {
		this._encrypter = encrypter;
	}

	/**
	 * @uml.property  name="_encrypter"
	 * @uml.associationEnd  
	 */
	private IApsEncrypter _encrypter;

	/**
	 * @uml.property  name="pREFIX_LOAD_USERS"
	 */
	private final String PREFIX_LOAD_USERS = 
		"SELECT authusers.username, authusers.passwd, authusers.registrationdate, " +
		"authusers.lastaccess, authusers.lastpasswordchange, authusers.active FROM authusers ";

	/**
	 * @uml.property  name="lOAD_USERS"
	 */
	private final String LOAD_USERS =
		PREFIX_LOAD_USERS + "ORDER BY authusers.username";

	/**
	 * @uml.property  name="sEARCH_USERS_BY_TEXT"
	 */
	private final String SEARCH_USERS_BY_TEXT =
		PREFIX_LOAD_USERS + " WHERE authusers.username LIKE ? ORDER BY authusers.username";

	/**
	 * @uml.property  name="lOAD_USER"
	 */
	private final String LOAD_USER =
		PREFIX_LOAD_USERS + "WHERE authusers.username = ? AND authusers.passwd = ? ";

	/**
	 * @uml.property  name="lOAD_USER_FROM_USERNAME"
	 */
	private final String LOAD_USER_FROM_USERNAME =
		PREFIX_LOAD_USERS + "WHERE authusers.username = ? ";

	/**
	 * @uml.property  name="lOAD_USERS_FROM_GROUP"
	 */
	private final String LOAD_USERS_FROM_GROUP =
		PREFIX_LOAD_USERS + " LEFT JOIN authusergroups ON authusers.username = authusergroups.username " +
		"WHERE authusergroups.groupname = ? ORDER BY authusers.username";

	/**
	 * @uml.property  name="lOAD_USERNAMES_FROM_GROUP"
	 */
	private final String LOAD_USERNAMES_FROM_GROUP =
		"SELECT authusergroups.username FROM authusergroups WHERE authusergroups.groupname = ? ORDER BY authusergroups.username";

	/**
	 * @uml.property  name="dELETE_USER"
	 */
	private final String DELETE_USER =
		"DELETE FROM authusers WHERE username = ? ";

	/**
	 * @uml.property  name="aDD_USER"
	 */
	private final String ADD_USER =
		"INSERT INTO authusers (username, passwd, registrationdate, active) VALUES ( ? , ? , ? , ? )";

	/**
	 * @uml.property  name="cHANGE_PASSWORD"
	 */
	private final String CHANGE_PASSWORD =
		"UPDATE authusers SET passwd = ? , lastpasswordchange = ? WHERE username = ? ";

	/**
	 * @uml.property  name="uPDATE_USER"
	 */
	private final String UPDATE_USER =
		"UPDATE authusers SET passwd = ? , lastaccess = ? , lastpasswordchange = ? , active = ? WHERE username = ? ";

	/**
	 * @uml.property  name="uPDATE_LAST_ACCESS"
	 */
	private final String UPDATE_LAST_ACCESS =
		"UPDATE authusers SET lastaccess = ? WHERE username = ? ";

	/**
	 * @uml.property  name="dELETE_USER_GROUP"
	 */
	private final String DELETE_USER_GROUP =
		"DELETE FROM authusergroups WHERE username = ? ";

	/**
	 * @uml.property  name="dELETE_USER_ROLE"
	 */
	private final String DELETE_USER_ROLE =
		"DELETE FROM authuserroles WHERE username = ? ";

}
