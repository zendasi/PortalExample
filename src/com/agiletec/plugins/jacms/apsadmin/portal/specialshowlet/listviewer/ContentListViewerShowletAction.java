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
package com.agiletec.plugins.jacms.apsadmin.portal.specialshowlet.listviewer;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface;
import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.aps.system.services.category.Category;
import com.agiletec.aps.system.services.category.ICategoryManager;
import com.agiletec.aps.system.services.lang.Lang;
import com.agiletec.aps.system.services.page.IPage;
import com.agiletec.aps.system.services.page.Showlet;
import com.agiletec.aps.util.ApsProperties;
import com.agiletec.aps.util.SelectItem;
import com.agiletec.apsadmin.portal.specialshowlet.SimpleShowletConfigAction;
import com.agiletec.plugins.jacms.aps.system.services.content.IContentManager;
import com.agiletec.plugins.jacms.aps.system.services.content.model.Content;
import com.agiletec.plugins.jacms.aps.system.services.content.model.SmallContentType;
import com.agiletec.plugins.jacms.aps.system.services.content.showlet.ContentListHelper;
import com.agiletec.plugins.jacms.aps.system.services.content.showlet.IContentListHelper;
import com.agiletec.plugins.jacms.aps.system.services.content.showlet.UserFilterOptionBean;
import com.agiletec.plugins.jacms.aps.system.services.content.showlet.util.FilterUtils;
import com.agiletec.plugins.jacms.aps.system.services.contentmodel.ContentModel;
import com.agiletec.plugins.jacms.aps.system.services.contentmodel.IContentModelManager;

/**
 * Action per la gestione della configurazione della showlet erogatore avanzato lista contenuti.
 * @author E.Santoboni
 */
public class ContentListViewerShowletAction extends SimpleShowletConfigAction implements IContentListViewerShowletAction {
	
	@Override
	public void validate() {
		super.validate();
		try {
			if (null == this.getFieldErrors().get("contentType")) {
				if (null == this.getContentManager().createContentType(this.getContentType())) {
					this.addFieldError("contentType", this.getText("error.showlet.listViewer.invalidContentType", new String[]{this.getContentType()}));
				}
			}
			if (this.getActionErrors().size()>0 || this.getFieldErrors().size()>0) {
				this.setShowlet(super.createNewShowlet());
				return;
			}
			this.createValuedShowlet();
			this.validateTitle();
			this.validateLink();
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "validate", "Error validating list viewer");
		}
	}
	
	protected void validateTitle() {
		String titleParamPrefix = IContentListHelper.SHOWLET_PARAM_TITLE + "_";
		if (this.isMultilanguageParamValued(titleParamPrefix)) {
			Lang defaultLang = this.getLangManager().getDefaultLang();
			String defaultTitleParam = titleParamPrefix + defaultLang.getCode();
			String defaultTitle = this.getShowlet().getConfig().getProperty(defaultTitleParam);
			if (defaultTitle == null || defaultTitle.length() == 0) {
				String[] args = {defaultLang.getDescr()};
				this.addFieldError(defaultTitleParam, this.getText("error.showlet.listViewer.defaultLangTitle.required", args));
			}
		}
	}
	
	protected void validateLink() {
		String pageLink = this.getShowlet().getConfig().getProperty(IContentListHelper.SHOWLET_PARAM_PAGE_LINK);
		boolean existsPageLink = pageLink != null && this.getPage(pageLink) != null;
		String linkDescrParamPrefix = IContentListHelper.SHOWLET_PARAM_PAGE_LINK_DESCR + "_";
		if (existsPageLink || this.isMultilanguageParamValued(linkDescrParamPrefix)) {
			if (!existsPageLink) {
				this.addFieldError(IContentListHelper.SHOWLET_PARAM_PAGE_LINK, this.getText("error.showlet.listViewer.pageLink.required"));
			}
			Lang defaultLang = this.getLangManager().getDefaultLang();
			String defaultLinkDescrParam = linkDescrParamPrefix + defaultLang.getCode();
			String defaultLinkDescr = this.getShowlet().getConfig().getProperty(defaultLinkDescrParam);
			if (defaultLinkDescr == null || defaultLinkDescr.length() == 0) {
				String[] args = {defaultLang.getDescr()};
				this.addFieldError(defaultLinkDescrParam, this.getText("error.showlet.listViewer.defaultLangLink.required", args));
			}
		}
	}
	
	private boolean isMultilanguageParamValued(String prefix) {
		ApsProperties config = this.getShowlet().getConfig();
		if (null == config) return false;
		for (int i = 0; i < this.getLangs().size(); i++) {
			Lang lang = this.getLangs().get(i);
			String paramValue = config.getProperty(prefix+lang.getCode());
			if (null != paramValue && paramValue.trim().length() > 0) return true;
		}
		return false;
	}
	
	@Override
	public String init() {
		try {
			super.init();
			ApsProperties config = this.getShowlet().getConfig();
			if (null == config) return SUCCESS;
			this.extractFiltersProperties(config);
			this.extractUserFiltersProperties(config);
			this.extractCategories(config);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "init");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	public String configContentType() {
		try {
			Showlet showlet = super.createNewShowlet();
			showlet.getConfig().setProperty(IContentListHelper.SHOWLET_PARAM_CONTENT_TYPE, this.getContentType());
			this.setShowlet(showlet);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "init");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	public String changeContentType() {
		try {
			Showlet showlet = super.createNewShowlet();
			this.setShowlet(showlet);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "changeContentType");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	public String addCategory() {
		return this.addRemoveCategory(true);
	}
	
	@Override
	public String removeCategory() {
		return this.addRemoveCategory(false);
	}
	
	protected String addRemoveCategory(boolean add) {
		try {
			this.createValuedShowlet();
			List<String> categoryCodes = this.getCategoryCodes();
			String category = this.getCategoryCode();
			if (add && category != null && category.length() > 0 
					&& !categoryCodes.contains(category) 
					&& this.getCategoryManager().getCategory(category) != null) {
				categoryCodes.add(category);
			} else {
				categoryCodes.remove(category);
			}
			String categories = ContentListHelper.concatStrings(categoryCodes, IContentListHelper.CATEGORIES_SEPARATOR);
			this.getShowlet().getConfig().setProperty(IContentListHelper.SHOWLET_PARAM_CATEGORIES, categories);
		} catch (Throwable t) {
			String marker = (add) ? "adding" : "removing";
			ApsSystemUtils.logThrowable(t, this, "addRemoveCategory", "Error " + marker + " category : '" + this.getCategoryCode() + "'");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	public List<SelectItem> getAllowedUserFilterTypes() throws ApsSystemException {
		List<SelectItem> types = new ArrayList<SelectItem>();
		try {
			types.add(new SelectItem(UserFilterOptionBean.KEY_FULLTEXT, this.getText("label.fulltext")));
			types.add(new SelectItem(UserFilterOptionBean.KEY_CATEGORY, this.getText("label.category")));
			String contentType = this.getShowlet().getConfig().getProperty(IContentListHelper.SHOWLET_PARAM_CONTENT_TYPE);
			Content prototype = this.getContentManager().createContentType(contentType);
			List<AttributeInterface> contentAttributes = prototype.getAttributeList();
			for (int i=0; i<contentAttributes.size(); i++) {
				AttributeInterface attribute = contentAttributes.get(i);
				if (attribute.isSearcheable()) {
					types.add(new SelectItem(UserFilterOptionBean.TYPE_ATTRIBUTE + "_" + attribute.getName(), this.getText("label.attribute", new String[]{attribute.getName()})));
				}
			}
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "getAllowedUserFilterTypes");
			throw new ApsSystemException("Error extracting allowed user filter types", t);
		}
		return types;
	}
	
	public List<SelectItem> getAllowedFilterTypes() throws ApsSystemException {
		List<SelectItem> types = new ArrayList<SelectItem>();
		try {
			types.add(new SelectItem(IContentListFilterAction.METADATA_KEY_PREFIX + IContentManager.CONTENT_CREATION_DATE_FILTER_KEY, this.getText("label.creationDate")));
			types.add(new SelectItem(IContentListFilterAction.METADATA_KEY_PREFIX + IContentManager.CONTENT_MODIFY_DATE_FILTER_KEY, this.getText("label.lastModifyDate")));
			String contentType = this.getShowlet().getConfig().getProperty(IContentListHelper.SHOWLET_PARAM_CONTENT_TYPE);
			Content prototype = this.getContentManager().createContentType(contentType);
			List<AttributeInterface> contentAttributes = prototype.getAttributeList();
			for (int i=0; i<contentAttributes.size(); i++) {
				AttributeInterface attribute = contentAttributes.get(i);
				if (attribute.isSearcheable()) {
					types.add(new SelectItem(attribute.getName(), this.getText("label.attribute", new String[]{attribute.getName()})));
				}
			}
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "getAllowedFilterTypes");
			throw new ApsSystemException("Error extracting allowed filter types", t);
		}
		return types;
	}
	
	@Override
	public String addUserFilter() {
		try {
			this.createValuedShowlet();
			Properties newUserFilter = this.createUserFilterProperties();
			if (null == newUserFilter) {
				this.addFieldError("userFilterKey", this.getText("error.showlet.listViewer.invalidUserFilter"));
				return INPUT;
			}
			List<Properties> userFiltersProperties = this.getUserFiltersProperties();
			if (null != newUserFilter) {
				userFiltersProperties.add(newUserFilter);
			}
			String newShowletParam = FilterUtils.getShowletParam(userFiltersProperties);
			this.getShowlet().getConfig().setProperty(IContentListHelper.SHOWLET_PARAM_USER_FILTERS, newShowletParam);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "addUserFilter");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	protected Properties createUserFilterProperties() throws ApsSystemException {
		String filterKey = this.getUserFilterKey();
		if (null == filterKey) return null;
		Properties properties = new Properties();
		try {
			if (filterKey.equals(UserFilterOptionBean.KEY_FULLTEXT) || filterKey.equals(UserFilterOptionBean.KEY_CATEGORY)) {
				properties.put(UserFilterOptionBean.PARAM_KEY, filterKey);
				properties.put(UserFilterOptionBean.PARAM_IS_ATTRIBUTE_FILTER, String.valueOf(false));
			} else if (filterKey.startsWith(UserFilterOptionBean.TYPE_ATTRIBUTE + "_")) {
				properties.put(UserFilterOptionBean.PARAM_KEY, filterKey.substring((UserFilterOptionBean.TYPE_ATTRIBUTE + "_").length()));
				properties.put(UserFilterOptionBean.PARAM_IS_ATTRIBUTE_FILTER, String.valueOf(true));
			}
			if (properties.isEmpty()) return null;
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "addUserFilter");
			throw new ApsSystemException("Error creating user filter", t);
		}
		return properties;
	}
	
	@Override
	public String moveUserFilter() {
		return this.removeMoveUserFilter(true);
	}
	
	@Override
	public String removeUserFilter() {
		return this.removeMoveUserFilter(false);
	}
	
	protected String removeMoveUserFilter(boolean move) {
		try {
			this.createValuedShowlet();
			List<Properties> userFiltersProperties = this.getUserFiltersProperties();
			int filterIndex = this.getFilterIndex();
			if (move) {
				Properties element = userFiltersProperties.get(filterIndex);
				if (getMovement().equalsIgnoreCase(MOVEMENT_UP_CODE)) {
					if (filterIndex > 0) {
						userFiltersProperties.remove(filterIndex);
						userFiltersProperties.add(filterIndex -1, element);
					}
				} else if (getMovement().equalsIgnoreCase(MOVEMENT_DOWN_CODE)) {
					if (filterIndex < userFiltersProperties.size() -1) {
						userFiltersProperties.remove(filterIndex);
						userFiltersProperties.add(filterIndex + 1, element);
					}
				}
			} else {
				userFiltersProperties.remove(filterIndex);
			}
			String newShowletParam = FilterUtils.getShowletParam(userFiltersProperties);
			this.getShowlet().getConfig().setProperty(IContentListHelper.SHOWLET_PARAM_USER_FILTERS, newShowletParam);
		} catch (Throwable t) {
			String marker = (move) ? "moving" : "removing";
			ApsSystemUtils.logThrowable(t, this, "removeMoveUserFilter", "Error " + marker + " userFilter");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	public String addFilter() {
		try {
			this.createValuedShowlet();
			List<Properties> properties = this.getFiltersProperties();
			properties.add(this.getNewFilter());
			String newShowletParam = FilterUtils.getShowletParam(properties);
			this.getShowlet().getConfig().setProperty(IContentListHelper.SHOWLET_PARAM_FILTERS, newShowletParam);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "addFilter");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	public String moveFilter() {
		return this.moveRemoveFilter(true);
	}
	
	@Override
	public String removeFilter() {
		return this.moveRemoveFilter(false);
	}
	
	protected String moveRemoveFilter(boolean move) {
		try {
			this.createValuedShowlet();
			List<Properties> userFiltersProperties = this.getFiltersProperties();
			int filterIndex = this.getFilterIndex();
			if (move) {
				Properties element = userFiltersProperties.get(filterIndex);
				if (getMovement().equalsIgnoreCase(MOVEMENT_UP_CODE)){
					if (filterIndex > 0) {
						userFiltersProperties.remove(filterIndex);
						userFiltersProperties.add(filterIndex -1, element);
					}
				} else if (getMovement().equalsIgnoreCase(MOVEMENT_DOWN_CODE)) {
					if (filterIndex < userFiltersProperties.size() -1) {
						userFiltersProperties.remove(filterIndex);
						userFiltersProperties.add(filterIndex + 1, element);
					}
				}
			} else {
				userFiltersProperties.remove(filterIndex);
			}
			String newShowletParam = FilterUtils.getShowletParam(userFiltersProperties);
			this.getShowlet().getConfig().setProperty(IContentListHelper.SHOWLET_PARAM_FILTERS, newShowletParam);
		} catch (Throwable t) {
			String marker = (move) ? "moving" : "removing";
			ApsSystemUtils.logThrowable(t, this, "removeMoveFilter", "Error " + marker + " filter");
			return FAILURE;
		}
		return SUCCESS;
	}
	
	@Override
	protected void createValuedShowlet() throws Exception {
		try {
			super.createValuedShowlet();
			ApsProperties config = this.getShowlet().getConfig();
			this.extractFiltersProperties(config);
			this.extractUserFiltersProperties(config);
			this.extractCategories(config);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "createValuedShowlet", "Error creating valued showlet");
			throw new ApsSystemException("Error creating user filter", t);
		}
	}
	
	private void extractFiltersProperties(ApsProperties config) {
		if (null == config) return;
		String filters = config.getProperty(IContentListHelper.SHOWLET_PARAM_FILTERS);
		List<Properties> properties = FilterUtils.getFiltersProperties(filters);
		this.setFiltersProperties(properties);
	}
	
	private void extractUserFiltersProperties(ApsProperties config) {
		if (null == config) return;
		String filters = config.getProperty(IContentListHelper.SHOWLET_PARAM_USER_FILTERS);
		List<Properties> properties = FilterUtils.getFiltersProperties(filters);
		this.setUserFiltersProperties(properties);
	}
	
	protected void extractCategories(ApsProperties config) {
		if (null == config) return;
		String categories = config.getProperty(IContentListHelper.SHOWLET_PARAM_CATEGORIES);
		if (null != categories) {
			List<String> categoryCodes = ContentListHelper.splitValues(categories, IContentListHelper.CATEGORIES_SEPARATOR);
			this.setCategoryCodes(categoryCodes);
		}
		String category = config.getProperty(IContentListHelper.SHOWLET_PARAM_CATEGORY);
		if (null != category) {
			if (null == this.getCategoryCodes()) {
				this.setCategoryCodes(new ArrayList<String>());
			}
			this.getCategoryCodes().add(category);
		}
	}
	
	/**
	 * Restituisce la lista di contenuti (in forma small) definiti nel sistema.
	 * Il metodo è a servizio delle jsp che richiedono questo dato per fornire 
	 * una corretta visualizzazione della pagina.
	 * @return La lista di tipi di contenuto (in forma small) definiti nel sistema.
	 */
	public List<SmallContentType> getContentTypes() {
		return this.getContentManager().getSmallContentTypes();
	}
	
	/**
	 * Restituisce la lista di categorie definite nel sistema.
	 * @return La lista di categorie definite nel sistema.
	 */
	public List<Category> getCategories() {
		return this.getCategoryManager().getCategoriesList();
	}
	
	public Category getCategory(String categoryCode) {
		return this.getCategoryManager().getCategory(categoryCode);
	}
	
	/**
	 * Restituisce la lista di Modelli di Contenuto compatibili con il tipo di contenuto specificato.
	 * @param contentType Il tipo di contenuto cui restituire i modelli compatibili.
	 * @return La lista di Modelli di Contenuto compatibili con il tipo di contenuto specificato.
	 */
	public List<ContentModel> getModelsForContentType(String contentType) {
		return this.getContentModelManager().getModelsForContentType(contentType);
	}
	
	public List<IPage> getPages() {
		if (this._pages == null) {
			this._pages = new ArrayList<IPage>();
			IPage root = this.getPageManager().getRoot();
			this.addPages(root, this._pages);
		}
		return this._pages;
	}
	
	protected void addPages(IPage page, List<IPage> pages) {
		pages.add(page);
		IPage[] children = page.getChildren();
		for (int i=0; i<children.length; i++) {
			this.addPages(children[i], pages);
		}
	}
	
	public String getContentType() {
		return _contentType;
	}
	public void setContentType(String contentType) {
		this._contentType = contentType;
	}
	
	public String getModelId() {
		return _modelId;
	}
	public void setModelId(String modelId) {
		this._modelId = modelId;
	}
	
	public String getCategoryCode() {
		return _categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this._categoryCode = categoryCode;
	}
	
	public List<String> getCategoryCodes() {
		return _categoryCodes;
	}
	public void setCategoryCodes(List<String> categoryCodes) {
		this._categoryCodes = categoryCodes;
	}
	
	@Deprecated
	public String getCategory() {
		return this.getCategoryCode();
	}
	@Deprecated
	public void setCategory(String category) {
		this.setCategoryCode(category);
	}
	
	public String getUserFilterKey() {
		return _userFilterKey;
	}
	public void setUserFilterKey(String userFilterKey) {
		this._userFilterKey = userFilterKey;
	}
	
	public void setUserFiltersProperties(List<Properties> userFiltersProperties) {
		this._userFiltersProperties = userFiltersProperties;
	}
	public List<Properties> getUserFiltersProperties() {
		return _userFiltersProperties;
	}
	
	public void setUserFilters(String userFilters) {
		this._userFilters = userFilters;
	}
	public String getUserFilters() {
		return _userFilters;
	}
	
	public String getMaxElemForItem() {
		return _maxElemForItem;
	}
	public void setMaxElemForItem(String maxElemForItem) {
		this._maxElemForItem = maxElemForItem;
	}
	
	public String getFilters() {
		return _filters;
	}
	public void setFilters(String filters) {
		this._filters = filters;
	}
	
	public int getFilterIndex() {
		return _filterIndex;
	}
	public void setFilterIndex(int filterIndex) {
		this._filterIndex = filterIndex;
	}
	
	public String getMovement() {
		return _movement;
	}
	public void setMovement(String movement) {
		this._movement = movement;
	}
	
	public List<Properties> getFiltersProperties() {
		return _filtersProperties;
	}
	public void setFiltersProperties(List<Properties> filtersProperties) {
		this._filtersProperties = filtersProperties;
	}
	
	public Properties getNewFilter() {
		return _newFilter;
	}
	public void setNewFilter(Properties newFilter) {
		this._newFilter = newFilter;
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
	
	protected ICategoryManager getCategoryManager() {
		return _categoryManager;
	}
	public void setCategoryManager(ICategoryManager categoryManager) {
		this._categoryManager = categoryManager;
	}
	
	/**
	 * @uml.property  name="_contentType"
	 */
	private String _contentType;
	/**
	 * @uml.property  name="_modelId"
	 */
	private String _modelId;
	
	/**
	 * @uml.property  name="_categoryCode"
	 */
	private String _categoryCode;
	/**
	 * @uml.property  name="_categoryCodes"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
	private List<String> _categoryCodes = new ArrayList<String>();
	
	/**
	 * @uml.property  name="_userFilterKey"
	 */
	private String _userFilterKey;
	/**
	 * @uml.property  name="_userFiltersProperties"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.util.Properties"
	 */
	private List<Properties> _userFiltersProperties;
	/**
	 * @uml.property  name="_userFilters"
	 */
	private String _userFilters;
	
	/**
	 * @uml.property  name="_maxElemForItem"
	 */
	private String _maxElemForItem;
	/**
	 * @uml.property  name="_filters"
	 */
	private String _filters;
	
	/**
	 * @uml.property  name="_filterIndex"
	 */
	private int _filterIndex;
	/**
	 * @uml.property  name="_movement"
	 */
	private String _movement;
	
	/**
	 * @uml.property  name="_filtersProperties"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.util.Properties"
	 */
	private List<Properties> _filtersProperties;
	
	/**
	 * @uml.property  name="_newFilter"
	 */
	private Properties _newFilter;
	
	/**
	 * @uml.property  name="_pages"
	 */
	private List<IPage> _pages;
	
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
	 * @uml.property  name="_categoryManager"
	 * @uml.associationEnd  
	 */
	private ICategoryManager _categoryManager;
	
}