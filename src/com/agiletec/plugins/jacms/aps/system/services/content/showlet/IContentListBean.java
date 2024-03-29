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
package com.agiletec.plugins.jacms.aps.system.services.content.showlet;

import java.util.List;

import com.agiletec.aps.system.common.entity.model.EntitySearchFilter;

/**
 * Il bean detentore dei parametri di ricerca di liste di contenuti.
 * @author  E.Santoboni
 */
public interface IContentListBean {
	
	/**
	 * Restituisce il nome identificativo della lista.
	 * @return  Returns Il nome identificativo della lista.
	 * @uml.property  name="listName"
	 */
	public String getListName();
	
	/**
	 * Restituisce il codice dei tipi di contenuto da cercare.
	 * @return  Il codice dei tipi di contenuto da cercare.
	 * @uml.property  name="contentType"
	 */
	public String getContentType();
	
	/**
	 * Setta il codice dei tipi di contenuto da cercare.
	 * @param contentType  Il codice dei tipi di contenuto da cercare.
	 * @uml.property  name="contentType"
	 */
	public void setContentType(String contentType);
	
	/**
	 * Restituisce la categoria dei contenuto da cercare.
	 * @return  La categoria dei contenuto da cercare.
	 * @uml.property  name="category"
	 */
	public String getCategory();
	
	/**
	 * Setta la categoria dei contenuto da cercare.
	 * @param category  La categoria dei contenuto da cercare.
	 * @uml.property  name="category"
	 */
	public void setCategory(String category);
	
	/**
	 * Aggiunge un filtro in coda alla lista di filtri definita nel bean.
	 * @param filter Il filtro da aggiungere.
	 */
	public void addFilter(EntitySearchFilter filter);
	
	/**
	 * Restituisce la lista di filtri definita nel bean.
	 * @return La lista di filtri definita nel bean.
	 */
	public EntitySearchFilter[] getFilters();
	
	/**
	 * Aggiunge una opzione filtro utente in coda alla lista di filtri definita nel bean.
	 * @param filter L'opzione filtro utente da aggiungere.
	 */
	public void addUserFilterOption(UserFilterOptionBean filter);
	
	/**
	 * Restituisce la lista di opzioni filtro utente definita.
	 * @return La lista di opzioni filtro utente definita nel bean.
	 */
	public List<UserFilterOptionBean> getUserFilterOptions();
	
	/**
	 * Indica se nel recupero della lista deve essere utilizzata la cache di sistema.
	 * @return True se deve essere utilizzata la chache di sistema, false in caso contrario.
	 */
	public boolean isCacheable();
	
}