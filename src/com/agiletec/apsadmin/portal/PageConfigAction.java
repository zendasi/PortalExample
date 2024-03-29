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
package com.agiletec.apsadmin.portal;

import java.util.List;
import java.util.logging.Logger;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.services.page.IPage;
import com.agiletec.aps.system.services.page.Showlet;
import com.agiletec.aps.system.services.showlettype.ShowletType;

/**
 * Main action class for the pages configuration.
 * @author E.Santoboni
 */
public class PageConfigAction extends AbstractPortalAction implements IPageConfigAction {
	
	@Override
	public String configure() {
		String pageCode = (this.getSelectedNode() != null ? this.getSelectedNode() : this.getPageCode());
		this.setPageCode(pageCode);
		String check = this.checkSelectedNode(pageCode);
		if (null != check) return check;
		return SUCCESS;
	}
	
	@Override
	public String editFrame() {
		try {
			String result = this.checkBaseParams();
			if (null != result) return result;
			Showlet showlet = this.getCurrentPage().getShowlets()[this.getFrame()];// può essere null
			this.setShowlet(showlet);
			if (showlet != null) {
				ShowletType showletType = showlet.getType();
				ApsSystemUtils.getLogger().finest("pageCode=" + this.getPageCode() 
						+ ", frame=" + this.getFrame() + ", showletCode=" + showletType.getCode());
				this.setShowletAction(showletType.getAction());
				if (null == showletType.getConfig() && null != this.getShowletAction()) {
					return "configureSpecialShowlet";
				}
			} else {
				ApsSystemUtils.getLogger().finest("pageCode=" + this.getPageCode() 
						+ ", frame=" + this.getFrame() + ", Showlet vuota da configurare.");
			}
		} catch (Exception e) {
			ApsSystemUtils.logThrowable(e, this, "editFrame");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	public String joinShowlet() {
		Logger log = ApsSystemUtils.getLogger();
		try {
			String result = this.checkBaseParams();
			if (null != result) return result;
			if (null != this.getShowletTypeCode() && this.getShowletTypeCode().length() == 0) {
				this.addActionError(this.getText("error.page.showletTypeCodeUnknown"));
				return INPUT;
			}
			log.finest("code=" + this.getShowletTypeCode() + ", pageCode=" 
					+ this.getPageCode() + ", frame=" + this.getFrame());
			ShowletType showletType = this.getShowletType(this.getShowletTypeCode());
			if (null == showletType) {
				this.addActionError(this.getText("error.page.showletTypeCodeUnknown"));
				return INPUT;
			}
			if (null == showletType.getConfig() && null != showletType.getAction()) {
				this.setShowletAction(showletType.getAction());
				//continua con la configurazione di showlet
				return "configureSpecialShowlet";
			}
			Showlet showlet = new Showlet();
			showlet.setType(showletType);
			this.getPageManager().joinShowlet(this.getPageCode(), showlet, this.getFrame());
		} catch (Exception e) {
			ApsSystemUtils.logThrowable(e, this, "joinShowlet");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	@Deprecated
	public String removeShowlet() {
		return this.trashShowlet();
	}
	
	@Override
	public String trashShowlet() {
		try {
			String result = this.checkBaseParams();
			if (null != result) return result;
		} catch (Exception e) {
			ApsSystemUtils.logThrowable(e, this, "trashShowlet");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	public String deleteShowlet() {
		try {
			String result = this.checkBaseParams();
			if (null != result) return result;
			this.getPageManager().removeShowlet(this.getPageCode(), this.getFrame());
		} catch (Exception e) {
			ApsSystemUtils.logThrowable(e, this, "deleteShowlet");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	//TODO METODO COMUNE ALLA CONFIG SPECIAL SHOWLET
	protected String checkBaseParams() {
		Logger log = ApsSystemUtils.getLogger();
		IPage page = this.getPage(this.getPageCode());
		if (!this.isUserAllowed(page)) {
			log.info("Utente corrente non abilitato all'editazione della pagina richiesta");
			this.addActionError(this.getText("error.page.userNotAllowed"));
			return "pageTree";
		}
		if (null == page) {
			log.info("Codice della pagina nullo");
			this.addActionError(this.getText("error.page.invalidPageCode"));
			return "pageTree";
		}
		if (this.getFrame() == -1 || this.getFrame() >= page.getShowlets().length) {
			log.info("Identificativo frame richiesto '" + this.getFrame() + "' non corretto");
			this.addActionError(this.getText("error.page.invalidPageFrame"));
			return "pageTree";
		}
		return null;
	}
	
	public List<ShowletType> getShowletTypes() {
		return this.getShowletTypeManager().getShowletTypes();
	}
	
	public ShowletType getShowletType(String typeCode) {
		return this.getShowletTypeManager().getShowletType(typeCode);
	}
	
	public IPage getCurrentPage() {
		return this.getPage(this.getPageCode());
	}
	
	public String getPageCode() {
		return _pageCode;
	}
	public void setPageCode(String pageCode) {
		this._pageCode = pageCode;
	}
	
	public int getFrame() {
		return _frame;
	}
	public void setFrame(int frame) {
		this._frame = frame;
	}
	
	public String getShowletAction() {
		return _showletAction;
	}
	public void setShowletAction(String showletAction) {
		this._showletAction = showletAction;
	}
	
	public String getShowletTypeCode() {
		return _showletTypeCode;
	}
	public void setShowletTypeCode(String showletTypeCode) {
		this._showletTypeCode = showletTypeCode;
	}
	
	public Showlet getShowlet() {
		return _showlet;
	}
	public void setShowlet(Showlet showlet) {
		this._showlet = showlet;
	}
	
	/**
	 * @uml.property  name="_pageCode"
	 */
	private String _pageCode;
	/**
	 * @uml.property  name="_frame"
	 */
	private int _frame = -1;
	/**
	 * @uml.property  name="_showletAction"
	 */
	private String _showletAction;
	/**
	 * @uml.property  name="_showletTypeCode"
	 */
	private String _showletTypeCode;
	
	/**
	 * @uml.property  name="_showlet"
	 * @uml.associationEnd  
	 */
	private Showlet _showlet;
	
}