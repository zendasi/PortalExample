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
package com.agiletec.plugins.jacms.apsadmin.content.attribute.action.link;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpSession;

import com.agiletec.aps.system.services.group.Group;
import com.agiletec.aps.system.services.page.IPage;
import com.agiletec.apsadmin.portal.PageTreeAction;
import com.agiletec.plugins.jacms.aps.system.services.content.model.Content;
import com.agiletec.plugins.jacms.aps.system.services.content.model.SymbolicLink;
import com.agiletec.plugins.jacms.apsadmin.content.ContentActionConstants;
import com.agiletec.plugins.jacms.apsadmin.content.attribute.action.link.helper.ILinkAttributeActionHelper;

/**
 * Classe action delegata alla gestione dei link a pagina nelle 
 * operazioni sugli attributi tipo Link.
 * @author E.Santoboni
 */
public class PageLinkAction extends PageTreeAction {
	
	@Override
	public void validate() {
		super.validate();
		if (this.getFieldErrors().size()==0) {
			IPage linkedPage = this.getPageManager().getPage(this.getSelectedNode());
			if (null == linkedPage) {
				this.addFieldError("selectedNode", this.getText("error.content.link.pageNotExist"));
			}
		}
	}
	
	/**
	 * Gestisce la richiesta di associazione di un link a pagina.
	 * In questo metodo vine anche gestita la richiesta di link di contenuto su pagina. 
	 * @return Il codice del risultato.
	 */
	public String joinLink() {
		int destType = this.getLinkType();
		String[] destinations = {null, this.getContentId(), this.getSelectedNode()};
		this.buildEntryContentAnchorDest();
		this.getLinkAttributeHelper().joinLink(destinations, destType, this.getRequest());
		return SUCCESS;
	}
	
	private void buildEntryContentAnchorDest() {
		HttpSession session = this.getRequest().getSession();
		String anchorDest = this.getLinkAttributeHelper().buildEntryContentAnchorDest(session);
		this.setEntryContentAnchorDest(anchorDest);
	}
	
	@Override
	protected Collection<String> getNodeGroupCodes() {
		Set<String> groupCodes = new HashSet<String>();
		groupCodes.add(Group.FREE_GROUP_NAME);
		Content currentContent = this.getContent();
		if (null != currentContent.getMainGroup()) {
			groupCodes.add(currentContent.getMainGroup());
		}
		return groupCodes;
	}
	
	/**
	 * Restituisce il contenuto in sesione.
	 * @return Il contenuto in sesione.
	 */
	public Content getContent() {
		HttpSession session = this.getRequest().getSession();
		return (Content) session.getAttribute(ContentActionConstants.SESSION_PARAM_NAME_CURRENT_CONTENT);
	}
	
	public SymbolicLink getSymbolicLink() {
		return (SymbolicLink) this.getRequest().getSession().getAttribute(ILinkAttributeActionHelper.SYMBOLIC_LINK_SESSION_PARAM);
	}
	
	public String getContentId() {
		return _contentId;
	}
	public void setContentId(String contentId) {
		this._contentId = contentId;
	}
	
	public int getLinkType() {
		return _linkType;
	}
	public void setLinkType(int linkType) {
		this._linkType = linkType;
	}
	
	public String getEntryContentAnchorDest() {
		if (null == this._entryContentAnchorDest) {
			this.buildEntryContentAnchorDest();
		}
		return _entryContentAnchorDest;
	}
	protected void setEntryContentAnchorDest(String entryContentAnchorDest) {
		this._entryContentAnchorDest = entryContentAnchorDest;
	}
	
	/**
	 * Restituisce la classe helper della gestione degli attributi di tipo Link.
	 * @return La classe helper degli attributi di tipo Link.
	 */
	protected ILinkAttributeActionHelper getLinkAttributeHelper() {
		return _linkAttributeHelper;
	}
	/**
	 * Setta la classe helper della gestione degli attributi di tipo Link.
	 * @param linkAttributeHelper La classe helper degli attributi di tipo Link.
	 */
	public void setLinkAttributeHelper(ILinkAttributeActionHelper linkAttributeHelper) {
		this._linkAttributeHelper = linkAttributeHelper;
	}
	
	/**
	 * @uml.property  name="_contentId"
	 */
	private String _contentId;
	/**
	 * @uml.property  name="_linkType"
	 */
	private int _linkType;
	
	/**
	 * @uml.property  name="_entryContentAnchorDest"
	 */
	private String _entryContentAnchorDest;
	
	/**
	 * @uml.property  name="_linkAttributeHelper"
	 * @uml.associationEnd  
	 */
	private ILinkAttributeActionHelper _linkAttributeHelper;
	
}