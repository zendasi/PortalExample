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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.common.AbstractService;
import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.aps.system.services.lang.events.LangsChangedEvent;
import com.agiletec.aps.system.services.lang.events.LangsChangedObserver;
import com.agiletec.aps.util.ApsProperties;

/**
 * Servizio di gestione dei tipi di showlet (ShowletType) definiti
 * nel sistema. (Questo servizio non riguarda la configurazione delle
 * istanze di showlet nelle pagine)
 * @author 
 */
public class ShowletTypeManager extends AbstractService 
		implements IShowletTypeManager, LangsChangedObserver {
	
	@Override
	public void init() throws Exception {
		this.loadShowletTypes();
		ApsSystemUtils.getLogger().config(this.getClass().getName() + ": initialized " + this._showletTypes.size() + " showlet types");
	}
	
	/**
	 * Caricamento da db del catalogo dei tipi di showlet.
	 * @throws ApsSystemException In caso di errori di lettura da db.
	 */
	private void loadShowletTypes() throws ApsSystemException {
		try {
			this._showletTypes = this.getShowletTypeDAO().loadShowletTypes();
			Iterator<ShowletType> iter = this._showletTypes.values().iterator();
			while (iter.hasNext()) {
				ShowletType type = iter.next();
				String mainTypeCode = type.getParentTypeCode();
				if (null != mainTypeCode) {
					type.setParentType(this._showletTypes.get(mainTypeCode));
				}
			}
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "loadShowletTypes");
			throw new ApsSystemException("Error loading showlets types", t);
		}
	}
	
	@Override
	public void updateFromLangsChanged(LangsChangedEvent event) {
		try {
			this.init();
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "updateFromLangsChanged", "Error on init method");
		}
	}
	
	@Override
	public ShowletType getShowletType(String code) {
		return this._showletTypes.get(code);
	}
	
	@Override
	public List<ShowletType> getShowletTypes() {
		List<ShowletType> types = new ArrayList<ShowletType>();
		Iterator<ShowletType> masterTypesIter = this._showletTypes.values().iterator();
		while (masterTypesIter.hasNext()) {
			ShowletType showletType = masterTypesIter.next();
			types.add(showletType.clone());
		}
		return types;
	}
	
	@Override
	public void addShowletType(ShowletType showletType) throws ApsSystemException {
		try {
			ShowletType type = this._showletTypes.get(showletType.getCode());
			if (null != type) {
				ApsSystemUtils.getLogger().severe("Type already exists : type code" + showletType.getCode());
				return;
			}
			String parentTypeCode = showletType.getParentTypeCode();
			if (null != parentTypeCode && null == this._showletTypes.get(parentTypeCode)) {
				throw new ApsSystemException("ERROR : Parent type '" + parentTypeCode + "' doesn't exists");
			}
			if (null == parentTypeCode && null != showletType.getConfig()) {
				throw new ApsSystemException("ERROR : Parent type null and default config not null");
			}
			if (null != showletType.getTypeParameters() && null != showletType.getConfig()) {
				throw new ApsSystemException("ERROR : Params not null and config not null");
			}
			this.getShowletTypeDAO().addShowletType(showletType);
			this._showletTypes.put(showletType.getCode(), showletType);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "addShowletType");
			throw new ApsSystemException("Error adding a Showlet Type", t);
		}
	}
	
	@Override
	public void deleteShowletType(String showletTypeCode) throws ApsSystemException {
		try {
			ShowletType type = this._showletTypes.get(showletTypeCode);
			if (null == type) {
				ApsSystemUtils.getLogger().severe("Type not exists : type code" + showletTypeCode);
				return;
			}
			if (type.isLocked()) {
				ApsSystemUtils.getLogger().severe("A loked showlet can't be deleted - type " + showletTypeCode);
				return;
			}
			this.getShowletTypeDAO().deleteShowletType(showletTypeCode);
			this._showletTypes.remove(showletTypeCode);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "deleteShowletType");
			throw new ApsSystemException("Error deleting showlet type", t);
		}
	}
	
	@Override
	public void updateShowletType(String showletTypeCode, ApsProperties titles, ApsProperties defaultConfig) throws ApsSystemException {
		try {
			ShowletType type = this._showletTypes.get(showletTypeCode);
			if (null == type) {
				ApsSystemUtils.getLogger().severe("Type not exists : type code" + showletTypeCode);
				return;
			}
			if (type.isLocked() || !type.isLogic() || !type.isUserType()) {
				this.updateShowletTypeTitles(showletTypeCode, titles);
				return;
			}
			this.getShowletTypeDAO().updateShowletType(showletTypeCode, titles, defaultConfig);
			type.setTitles(titles);
			type.setConfig(defaultConfig);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "updateShowletTypeTitles");
			throw new ApsSystemException("Error updating Showlet type titles : type code" + showletTypeCode, t);
		}
	}
	
	@Override
	public void updateShowletTypeTitles(String showletTypeCode, ApsProperties titles) throws ApsSystemException {
		try {
			ShowletType type = this._showletTypes.get(showletTypeCode);
			if (null == type) {
				ApsSystemUtils.getLogger().severe("Type not exists : type code" + showletTypeCode);
				return;
			}
			this.getShowletTypeDAO().updateShowletTypeTitles(showletTypeCode, titles);
			type.setTitles(titles);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "updateShowletTypeTitles");
			throw new ApsSystemException("Error updating Showlet type titles : type code" + showletTypeCode, t);
		}
	}
	
	protected IShowletTypeDAO getShowletTypeDAO() {
		return _showletTypeDao;
	}
	public void setShowletTypeDAO(IShowletTypeDAO showletTypeDAO) {
		this._showletTypeDao = showletTypeDAO;
	}
	
	/**
	 * @uml.property  name="_showletTypes"
	 * @uml.associationEnd  qualifier="showletTypeCode:java.lang.String com.agiletec.aps.system.services.showlettype.ShowletType"
	 */
	private Map<String, ShowletType> _showletTypes;
	
	/**
	 * @uml.property  name="_showletTypeDao"
	 * @uml.associationEnd  
	 */
	private IShowletTypeDAO _showletTypeDao;
	
}