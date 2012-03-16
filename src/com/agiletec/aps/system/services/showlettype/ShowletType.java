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
package com.agiletec.aps.system.services.showlettype;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.agiletec.aps.util.ApsProperties;

/**
 * Rappresenta un tipo di oggetto visuale che può essere inserito in una
 * pagina, in uno dei frames specificati dal modello di pagina.
 * A questa rappresentazione corrisponde una jsp che implementa
 * effettivamente l'oggetto visuale.
 * @author
 */
public class ShowletType implements Serializable {
	
	@Override
	public ShowletType clone() {
		ShowletType clone = new ShowletType();
		clone.setAction(this.getAction());
		clone.setCode(this.getCode());
		if (null != this.getConfig()) {
			clone.setConfig(this.getConfig().clone());
		}
		clone.setLocked(this.isLocked());
		clone.setParentType(this.getParentType());
		clone.setParentTypeCode(this.getParentTypeCode());
		clone.setPluginCode(this.getPluginCode());
		if (null != this.getTitles()) {
			clone.setTitles(this.getTitles().clone());
		}
		if (null != this.getTypeParameters()) {
			List<ShowletTypeParameter> params = new ArrayList<ShowletTypeParameter>();
			for (int i = 0; i < this.getTypeParameters().size(); i++) {
				params.add(this.getTypeParameters().get(i).clone());
			}
			clone.setTypeParameters(params);
		}
		return clone;
	}
	
	/**
	 * Restituisce il codice del tipo di showlet.
	 * @return Il codice del tipo di showlet
	 */
	public String getCode() {
		return _code;
	}

	/**
	 * Imposta il codice del tipo di showlet.
	 * @param code Il codice del tipo di showlet
	 */
	public void setCode(String code) {
		this._code = code;
	}

	public ApsProperties getTitles() {
		return _titles;
	}
	public void setTitles(ApsProperties titles) {
		this._titles = titles;
	}
	
	/**
	 * restituisce la lista dei parametri previsti per il tipo di showlet.
	 * @return La lista di parametri in oggetti del tipo ShowletTypeParameter.
	 */
	public List<ShowletTypeParameter> getTypeParameters() {
		return _parameters;
	}

	/**
	 * Imposta la lista dei parametri previsti per il tipo di showlet.
	 * La lista deve essere composta da oggetti del tipo ShowletTypeParameter.
	 * @param typeParameters The parameters to set.
	 */
	public void setTypeParameters(List<ShowletTypeParameter> typeParameters) {
		this._parameters = typeParameters;
	}
	
	/**
	 * Restituisce il nome della action specifica che gestisce questo tipo di showlet.
	 * @return Il nome della action specifica, null se non vi è nessun action specifica.
	 */
	public String getAction() {
		return _action;
	}

	/**
	 * Setta il nome della action specifica che gestisce questo tipo di showlet.
	 * @param action Il nome della action specifica.
	 */
	public void setAction(String action) {
		this._action = action;
	}
	
	/**
	 * Return the code of the plugin owner of showlet type.
	 * The field is null if the showlet type belong to jAPS Core.
	 * @return The plugin code.
	 */
	public String getPluginCode() {
		return _pluginCode;
	}
	
	/**
	 * Set the code of the plugin owner of showlet type.
	 * @param pluginCode The plugin code. 
	 */
	public void setPluginCode(String pluginCode) {
		this._pluginCode = pluginCode;
	}
	
	protected String getParentTypeCode() {
		return _parentTypeCode;
	}
	protected void setParentTypeCode(String parentTypeCode) {
		this._parentTypeCode = parentTypeCode;
	}
	
	public ShowletType getParentType() {
		return _parentType;
	}
	public void setParentType(ShowletType parentType) {
		this._parentType = parentType;
		if (null != parentType) {
			this.setParentTypeCode(parentType.getCode());
		}
	}
	
	public ApsProperties getConfig() {
		return _config;
	}
	public void setConfig(ApsProperties config) {
		this._config = config;
	}
	
	public boolean isLogic() {
		return (null != this.getParentType());
	}
	
	public boolean isUserType() {
		return (this.isLogic() && !this.isLocked());
	}
	
	public boolean isLocked() {
		return _locked;
	}
	public void setLocked(boolean locked) {
		this._locked = locked;
	}
	
	/**
	 * Il codice del tipo di showlet.
	 * @uml.property  name="_code"
	 */
	private String _code;
	
	/**
	 * @uml.property  name="_titles"
	 * @uml.associationEnd  
	 */
	private ApsProperties _titles;
	
	/**
	 * La lista dei parametri previsti per il tipo di showlet.
	 * @uml.property  name="_parameters"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="com.agiletec.aps.system.services.showlettype.ShowletTypeParameter"
	 */
	private List<ShowletTypeParameter> _parameters;
	
	/**
	 * Il nome della action specifica che gestisce questo tipo di showlet. null se non vi è nessun action specifica.
	 * @uml.property  name="_action"
	 */
	private String _action;
	
	/**
	 * The code of the plugin owner of showlet type.
	 * @uml.property  name="_pluginCode"
	 */
	private String _pluginCode;
	
	/**
	 * @uml.property  name="_parentTypeCode"
	 */
	private String _parentTypeCode;
	
	/**
	 * @uml.property  name="_parentType"
	 * @uml.associationEnd  
	 */
	private ShowletType _parentType;
	
	/**
	 * @uml.property  name="_config"
	 * @uml.associationEnd  
	 */
	private ApsProperties _config;
	
	/**
	 * @uml.property  name="_locked"
	 */
	private boolean _locked;
	
}
