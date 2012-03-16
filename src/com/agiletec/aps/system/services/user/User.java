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

import java.util.Calendar;
import java.util.Date;

/**
 * Rappresentazione di un'utente.
 * @author M.Diana - E.Santoboni
 */
public class User extends AbstractUser {

	/**
	 * Indica se l'utente è definito localmente 
	 * all'interno del db "serv" di jAPS.
	 * @return true se è un'utente locale di jAPS, 
	 * false se è un'utente definito in altra base dati.
	 */
	public boolean isJapsUser() {
		return true;
	}

	/**
	 * Crea una copia dell'oggetto user e lo restituisce.
	 * @return Oggetto di tipo User clonato.
	 */
	public Object clone() {
		User cl = new User();
		cl.setUsername(this.getUsername());
		cl.setPassword("");

		cl.setAuthorities(this.getAuthorities());
		return cl;
	}

	public Date getCreationDate() {
		return _creationDate;
	}
	protected void setCreationDate(Date creationDate) {
		this._creationDate = creationDate;
	}

	public Date getLastAccess() {
		return _lastAccess;
	}
	public void setLastAccess(Date lastAccess) {
		this._lastAccess = lastAccess;
	}

	public Date getLastPasswordChange() {
		return _lastPasswordChange;
	}
	public void setLastPasswordChange(Date lastPasswordChange) {
		this._lastPasswordChange = lastPasswordChange;
	}

	@Override
	public boolean isDisabled() {
		return _disabled;
	}
	public void setDisabled(boolean disabled) {
		this._disabled = disabled;
	}

	protected boolean isCheckCredentials() {
		return _checkCredentials;
	}
	protected void setCheckCredentials(boolean checkCredentials) {
		this._checkCredentials = checkCredentials;
	}

	public int getMaxMonthsSinceLastAccess() {
		return _maxMonthsSinceLastAccess;
	}
	public void setMaxMonthsSinceLastAccess(int maxMonthsSinceLastAccess) {
		this._maxMonthsSinceLastAccess = maxMonthsSinceLastAccess;
	}

	public int getMaxMonthsSinceLastPasswordChange() {
		return _maxMonthsSinceLastPasswordChange;
	}
	public void setMaxMonthsSinceLastPasswordChange(int maxMonthsSinceLastPasswordChange) {
		this._maxMonthsSinceLastPasswordChange = maxMonthsSinceLastPasswordChange;
	}

	@Override
	public boolean isAccountNotExpired() {
		if (!this.isCheckCredentials()) return true;
		int maxDelay = this.getMaxMonthsSinceLastAccess();
		if (maxDelay > 0) {
			Date dateForCheck = (this.getLastAccess() != null ? this.getLastAccess() : this.getCreationDate());
			if (null != dateForCheck) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(dateForCheck);
				cal.add(Calendar.MONTH, maxDelay);
				Date expirationDate = cal.getTime();
				return expirationDate.after(new Date());
			}
		}
		return super.isAccountNotExpired();
	}

	@Override
	public boolean isCredentialsNotExpired() {
		if (!this.isCheckCredentials()) return true;
		int maxDelay = this.getMaxMonthsSinceLastPasswordChange();
		if (maxDelay > 0) {
			Date dateForCheck = (this.getLastPasswordChange() != null ? this.getLastPasswordChange() : this.getCreationDate());
			if (null != dateForCheck) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(dateForCheck);
				cal.add(Calendar.MONTH, maxDelay);
				Date expirationDate = cal.getTime();
				return expirationDate.after(new Date());
			}
		}
		return super.isCredentialsNotExpired();
	}

	/**
	 * @uml.property  name="_creationDate"
	 */
	private Date _creationDate;//CAMBIARE IN REGISTRATION DATE
	/**
	 * @uml.property  name="_lastAccess"
	 */
	private Date _lastAccess;
	/**
	 * @uml.property  name="_lastPasswordChange"
	 */
	private Date _lastPasswordChange;

	/**
	 * @uml.property  name="_disabled"
	 */
	private boolean _disabled;

	/**
	 * @uml.property  name="_checkCredentials"
	 */
	private boolean _checkCredentials;

	/**
	 * @uml.property  name="_maxMonthsSinceLastAccess"
	 */
	private int _maxMonthsSinceLastAccess = -1;
	/**
	 * @uml.property  name="_maxMonthsSinceLastPasswordChange"
	 */
	private int _maxMonthsSinceLastPasswordChange = -1;

}
