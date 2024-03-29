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
package com.agiletec.plugins.jacms.apsadmin.content.attribute.action.resource;

/**
 * Interfaccia base per le classi action delegate alla gestione delle operazioni base sugli attributi risorsa. Le azioni gestite rappresentano ciascuna un entry point dall'interfaccia di redazione contenuto. 
 * @author  E.Santoboni
 */
public interface IResourceAttributeAction {
	
	/**
	 * Esegue l'operazione di richiesta scelta risorsa.
	 * @return Il codice del risultato dell'azione.
	 */
	public String chooseResource();
	
	/**
	 * Esegue l'operazione di richiesta rimozione risorsa.
	 * @return Il codice del risultato dell'azione.
	 */
	public String removeResource();
	
	/**
	 * Restituisce il nome dell'attributo tipo Risorsa.
	 * @return  Il nome dell'attributo.
	 * @uml.property  name="attributeName"
	 */
	public String getAttributeName();
	
	/**
	 * Restituisce il nome dell'attributo parent nel caso che l'attributo  in oggetto sia inserito all'interno di un'attributo tipo "Composito".
	 * @return  Il nome dell'attributo parent.
	 * @uml.property  name="parentAttributeName"
	 */
	public String getParentAttributeName();
	
	/**
	 * Restituisce l'indice dell'attributo nel caso che l'attributo 
	 * in oggetto sia inserito all'interno di una lista di attributo tipo "List" o "Monolist".
	 * @return L'indice dell'attributo nel caso di lista di attributi.
	 */
	public int getElementIndex();
	
	/**
	 * Restituisce il codice della lingua tramite il quale associare la risorsa all'attributo.
	 * @return  Il codice della lingua dell'attributo corrente.
	 * @uml.property  name="resourceLangCode"
	 */
	public String getResourceLangCode();
	
	/**
	 * Restituisce il codice del tipo di risorsa.
	 * @return  Il codice del tipo di risorsa.
	 * @uml.property  name="resourceTypeCode"
	 */
	public String getResourceTypeCode();
	
}