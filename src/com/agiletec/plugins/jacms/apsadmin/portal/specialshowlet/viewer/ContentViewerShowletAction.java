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
package com.agiletec.plugins.jacms.apsadmin.portal.specialshowlet.viewer;

import java.util.ArrayList;
import java.util.List;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.services.page.IPage;
import com.agiletec.apsadmin.portal.specialshowlet.SimpleShowletConfigAction;
import com.agiletec.plugins.jacms.aps.system.services.content.IContentManager;
import com.agiletec.plugins.jacms.aps.system.services.content.model.Content;
import com.agiletec.plugins.jacms.aps.system.services.content.model.ContentRecordVO;
import com.agiletec.plugins.jacms.aps.system.services.contentmodel.ContentModel;
import com.agiletec.plugins.jacms.aps.system.services.contentmodel.IContentModelManager;
import com.agiletec.plugins.jacms.apsadmin.util.CmsPageActionUtil;

/**
 * Action per la gestione della configurazione della showlet erogatore contenuto singolo.
 * @author E.Santoboni
 */
public class ContentViewerShowletAction extends SimpleShowletConfigAction implements IContentViewerShowletAction {
	
	@Override
	public void validate() {
		super.validate();
		if (this.getFieldErrors().size()==0) {
			try {
				Content publishingContent = this.getContentManager().loadContent(this.getContentId(), true);
				if (null == publishingContent) {
					this.addFieldError("contentId", this.getText("error.showlet.viewer.nullContent"));
				} else {
					IPage currentPage = this.getCurrentPage();
					if (!CmsPageActionUtil.isContentPublishableOnPage(publishingContent, currentPage)) {
						List<String> pageGroups = new ArrayList<String>();
						pageGroups.add(currentPage.getGroup());
						if (null != currentPage.getExtraGroups()) {
							pageGroups.addAll(currentPage.getExtraGroups());
						}
						this.addFieldError("contentId", this.getText("error.showlet.viewer.invalidContent", new String[]{pageGroups.toString()}));
					}
				}
			} catch (Throwable t) {
				ApsSystemUtils.logThrowable(t, this, "validate", "Errore in validazione contenuto con id " + this.getContentId());
				throw new RuntimeException("Errore in validazione contenuto con id " + this.getContentId(), t);
			}
		}
		if (this.getFieldErrors().size()>0) {
			try {
				this.createValuedShowlet();
			} catch (Throwable t) {
				ApsSystemUtils.logThrowable(t, this, "validate", "Errore in creazione showlet valorizzata");
				throw new RuntimeException("Errore in creazione showlet valorizzata", t);
			}
		}
	}
	
	@Override
	public String joinContent() {
		try {
			this.createValuedShowlet();
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "joinContent");
			throw new RuntimeException("Errore in associazione contenuto", t);
		}
		return SUCCESS;
	}
	
	/**
	 * Restituisce il contenuto vo in base all'identificativo.
	 * @param contentId L'identificativo del contenuto.
	 * @return Il contenuto vo cercato.
	 */
	public ContentRecordVO getContentVo(String contentId) {
		ContentRecordVO contentVo = null;
		try {
			contentVo = this.getContentManager().loadContentVO(contentId);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "getContentVo");
			throw new RuntimeException("Errore in caricamento contenuto vo", t);
		}
		return contentVo;
	}
	
	/**
	 * Restituisce la lista di Modelli di Contenuto compatibili con il contenuto specificato.
	 * @param contentId Il contenuto cui restituire i modelli compatibili.
	 * @return La lista di Modelli di Contenuto compatibili con il contenuto specificato.
	 */
	public List<ContentModel> getModelsForContent(String contentId) {
		if (null == contentId) return new ArrayList<ContentModel>();
		String typeCode = contentId.substring(0, 3);
		return this.getContentModelManager().getModelsForContentType(typeCode);
	}
	
	protected IContentModelManager getContentModelManager() {
		return _contentModelManager;
	}
	public void setContentModelManager(IContentModelManager contentModelManager) {
		this._contentModelManager = contentModelManager;
	}
	
	protected IContentManager getContentManager() {
		return _contentManager;
	}
	public void setContentManager(IContentManager contentManager) {
		this._contentManager = contentManager;
	}

	public String getContentId() {
		return _contentId;
	}
	public void setContentId(String contentId) {
		this._contentId = contentId;
	}
	
	public String getModelId() {
		return _modelId;
	}
	public void setModelId(String modelId) {
		this._modelId = modelId;
	}
	
	/**
	 * @uml.property  name="_contentModelManager"
	 * @uml.associationEnd  
	 */
	private IContentModelManager _contentModelManager;
	/**
	 * @uml.property  name="_contentManager"
	 * @uml.associationEnd  
	 */
	private IContentManager _contentManager;
	
	/**
	 * @uml.property  name="_contentId"
	 */
	private String _contentId;
	/**
	 * @uml.property  name="_modelId"
	 */
	private String _modelId;
	
}