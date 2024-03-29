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
package com.agiletec.aps.system.common.entity.model.attribute;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.agiletec.aps.system.common.entity.model.IApsEntity;
import com.agiletec.aps.system.common.entity.model.attribute.util.BaseAttributeValidationRules;
import com.agiletec.aps.system.common.entity.model.attribute.util.IAttributeValidationRules;
import com.agiletec.aps.system.common.entity.parse.attribute.AttributeHandlerInterface;
import com.agiletec.aps.system.common.searchengine.IndexableAttributeInterface;
import com.agiletec.aps.system.exception.ApsSystemException;

/**
 * This abstract class must be used when implementing Entity Attributes.
 * @author W.Ambu - E.Santoboni
 */
public abstract class AbstractAttribute implements AttributeInterface, Serializable {
	
	/**
	 * @see com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface#isMultilingual()
	 */
	@Override
	public boolean isMultilingual() {
		return false;
	}
	
	/**
	 * @see com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface#isTextAttribute()
	 */
	@Override
	public boolean isTextAttribute() {
		return false;
	}
	
	/**
	 * @see com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface#getName()
	 */
	@Override
	public String getName() {
		return _name;
	}
	
	/**
	 * @see com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this._name = name;
	}
	
	/**
	 * The returned type corresponds to the attribute code as found in the declaration
	 * of the attribute type.
	 * @see com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface#getType()
	 */
	@Override
	public String getType() {
		return _type;
	}
	
	/**
	 * @see com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface#setType(java.lang.String)
	 */
	@Override
	public void setType(String typeName) {
		this._type = typeName;
	}
	
	/**
	 * @see com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface#setDefaultLangCode(java.lang.String)
	 */
	@Override
	public void setDefaultLangCode(String langCode){
		this._defaultLangCode = langCode;
	}
	
	/**
	 * Return the code of the default language.
	 * @return The code of the default language.
	 */
	public String getDefaultLangCode(){
		return _defaultLangCode;
	}
	
	/**
	 * Set up the language to use in the rendering process.
	 * @param langCode The code of the rendering language.
	 */
	@Override
	public void setRenderingLang(String langCode){
		_renderingLangCode = langCode;
	}
	
	/**
	 * Return the code of the language used in the rendering process.
	 * @return The code of the language used for rendering.
	 */
	public String getRenderingLang(){
		return _renderingLangCode;
	}
	
	/**
	 * @return True if the attribute is searchable, false otherwise.
	 */
	@Override
	public boolean isSearcheable() {
		return _searcheable;
	}
	
	/**
	 * Toggle the searchable condition of the attribute.
	 * @param searchable True if the attribute is searchable, false otherwise.
	 */
	@Override
	public void setSearcheable(boolean searchable){
		this._searcheable = searchable;
	}
	
	/**
	 * @see com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface#getAttributePrototype()
	 */
	@Override
	public Object getAttributePrototype() {
		AttributeInterface clone = null;
		try {
			Class attributeClass = Class.forName(this.getClass().getName());
			clone = (AttributeInterface) attributeClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Error detected while cloning the attribute '" 
					+ this.getName() + "' type '" + this.getType() + "'", e);
		}
		clone.setName(this.getName());
		clone.setType(this.getType());
		clone.setSearcheable(this.isSearcheable());
		clone.setDefaultLangCode(this.getDefaultLangCode());
		clone.setIndexingType(this.getIndexingType());
		clone.setParentEntity(this.getParentEntity());
		AttributeHandlerInterface handler = (AttributeHandlerInterface) this.getHandler().getAttributeHandlerPrototype();
		clone.setHandler(handler);
		if (this.getDisablingCodes() != null) {
			String[] disablingCodes = new String[this.getDisablingCodes().length];
			for (int i=0; i<this.getDisablingCodes().length; i++) {
				disablingCodes[i] = this.getDisablingCodes()[i];
			}
			clone.setDisablingCodes(disablingCodes);
		}
		if (this.getRoles() != null) {
			String[] roles = new String[this.getRoles().length];
			for (int i=0; i<this.getRoles().length; i++) {
				roles[i] = this.getRoles()[i];
			}
			clone.setRoles(roles);
		}
		clone.setValidationRules(this.getValidationRules().clone());
		return clone;
	}
	
	@Override
	public void setAttributeConfig(Element attributeElement) throws ApsSystemException {
		String name = this.extractXmlAttribute(attributeElement, "name", true);
		this.setName(name);
		String searcheable = this.extractXmlAttribute(attributeElement, "searcheable", false);
		this.setSearcheable(null != searcheable && searcheable.equalsIgnoreCase("true"));
		
		IAttributeValidationRules validationCondition = this.getValidationRules();
		validationCondition.setConfig(attributeElement);
		
		//to guaranted compatibility with previsous version of jAPS 2.0.12 *** Start Block
		String required = this.extractXmlAttribute(attributeElement, "required", false);
		if (null != required && required.equalsIgnoreCase("true")) {
			this.setRequired(true);
		}
		//to guaranted compatibility with previsous version of jAPS 2.0.12 *** End Block
		
		String indexingType = this.extractXmlAttribute(attributeElement, "indexingtype", false);
		if (null != indexingType) {
			this.setIndexingType(indexingType);
		} else {
			this.setIndexingType(IndexableAttributeInterface.INDEXING_TYPE_NONE);
		}
		
		Element disablingCodesElements = attributeElement.getChild("disablingCodes");
		if (null != disablingCodesElements) {
			String[] disablingCodes = this.extractValues(disablingCodesElements, "code");
			this.setDisablingCodes(disablingCodes);
		} else {
			//to guaranted compatibility with previsous version of jAPS 2.0.12 *** Start Block
			String disablingCodesStr = this.extractXmlAttribute(attributeElement, "disablingCodes", false);
			if (disablingCodesStr != null) {
				String[] disablingCodes = disablingCodesStr.split(",");
				this.setDisablingCodes(disablingCodes);
			}
			//to guaranted compatibility with previsous version of jAPS 2.0.12 *** End Block
		}
		Element rolesElements = attributeElement.getChild("roles");
		if (null != rolesElements) {
			String[] roles = this.extractValues(rolesElements, "role");
			this.setRoles(roles);
		}
	}
	
	private String[] extractValues(Element elements, String subElementName) {
		if (null == elements) return null;
		List<String> values = new ArrayList<String>();
		List<Element> subElements = elements.getChildren(subElementName);
		if (null == subElements || subElements.size() == 0) return null;
		for (int i = 0; i < subElements.size(); i++) {
			String text = subElements.get(i).getText();
			if (null != text && text.trim().length() > 0) {
				values.add(text.trim());
			}
		}
		String[] array = new String[values.size()];
		for (int i = 0; i < values.size(); i++) {
			array[i] = values.get(i);
		}
		return array;
	}

	@Override
	public Element getJDOMConfigElement() {
		Element configElement = new Element(this.getTypeConfigElementName());
		configElement.setAttribute("name", this.getName());
		configElement.setAttribute("attributetype", this.getType());
		if (this.isSearcheable()) {
			configElement.setAttribute("searcheable", "true");
		}
		Element validationElement = this.getValidationRules().getJDOMConfigElement();
		if (null != validationElement) {
			configElement.addContent(validationElement);
		}
		if (null != this.getIndexingType() && !this.getIndexingType().equals(IndexableAttributeInterface.INDEXING_TYPE_NONE)) {
			configElement.setAttribute("indexingtype", this.getIndexingType());
		}
		this.addArrayElement(configElement, this.getDisablingCodes(), "disablingCodes", "code");
		this.addArrayElement(configElement, this.getRoles(), "roles", "role");
		return configElement;
	}
	
	private void addArrayElement(Element configElement, String[] values, String elementName, String subElementName) {
		if (null != values) {
			Element arrayElem = new Element(elementName);
			for (int i = 0; i < values.length; i++) {
				Element stringElem = new Element(subElementName);
				stringElem.setText(values[i]);
				arrayElem.addContent(stringElem);
			}
			configElement.addContent(arrayElem);
		}
	}
	
	protected String getTypeConfigElementName() {
		return "attribute";
	}

	/**
	 * Get the attribute matching the given criteria from a XML string.
	 * @param currElement The element where to extract the value of the attribute from 
	 * @param attributeName Name of the requested attribute.
	 * @param required Select a mandatory condition of the attribute to search for.
	 * @return The value of the requested attribute.
	 * @throws ApsSystemException when a attribute declared mandatory is not present in the given
	 * XML element.
	 */
	protected String extractXmlAttribute(Element currElement, String attributeName,
			boolean required) throws ApsSystemException {
		String value = currElement.getAttributeValue(attributeName);
		if (required && value == null) {
			throw new ApsSystemException("Attribute '" + attributeName +"' not found in the tag <" + currElement.getName() + ">");
		}
		return value;
	}
	
	@Deprecated (/** DO NOTHING : to guaranted compatibility with previsous version of jAPS 2.0.12 */)
	protected void addListElementTypeConfig(Element configElement) {}
	
	/**
	 * @see com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface#getIndexingType()
	 */
	@Override
	public String getIndexingType(){
		return _indexingType;
	}
	
	/**
	 * @see com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface#setIndexingType(java.lang.String)
	 */
	@Override
	public void setIndexingType(String indexingType){
		this._indexingType = indexingType;
	}
	
	@Override
	public boolean isSimple() {
		return true;
	}
	
	@Override
	public boolean isRequired() {
		return this.getValidationRules().isRequired();
	}
	
	@Override
	public void setRequired(boolean required) {
		this.getValidationRules().setRequired(required);
	}
	
	@Override
	public IApsEntity getParentEntity() {
		return _parentEntity;
	}
	@Override
	public void setParentEntity(IApsEntity parentEntity) {
		this._parentEntity = parentEntity;
	}
	
	@Override
	public AttributeHandlerInterface getHandler() {
		return _handler;
	}
	@Override
	public void setHandler(AttributeHandlerInterface handler) {
		this._handler = handler;
	}
	
	@Override
	public void disable(String disablingCode) {
		if (_disablingCodes != null && disablingCode != null) {
			for (int i=0; i<_disablingCodes.length; i++) {
				if (disablingCode.equals(_disablingCodes[i])) {
					this._active = false;
					return;
				}
			}
		}
	}
	@Override
	public boolean isActive() {
		return _active;
	}
	@Override
	public void setDisablingCodes(String[] disablingCodes) {
		this._disablingCodes = disablingCodes;
	}
	@Override
	public String[] getDisablingCodes() {
		return this._disablingCodes;
	}
	
	@Override
	public String[] getRoles() {
		return _roles;
	}
	@Override
	public void setRoles(String[] roles) {
		this._roles = roles;
	}
	
	protected IAttributeValidationRules getValidationRuleNewIntance() {
		return new BaseAttributeValidationRules();
	}
	@Override
	public IAttributeValidationRules getValidationRules() {
		if (null == this._validationRules) {
			this.setValidationRules(this.getValidationRuleNewIntance());
		}
		return _validationRules;
	}
	
	@Override
	public void setValidationRules(IAttributeValidationRules validationRules) {
		this._validationRules = validationRules;
	}
	
	/**
	 * @uml.property  name="_name"
	 */
	private String _name;
	/**
	 * @uml.property  name="_type"
	 */
	private String _type;
	/**
	 * @uml.property  name="_defaultLangCode"
	 */
	private String _defaultLangCode;
	/**
	 * @uml.property  name="_renderingLangCode"
	 */
	private String _renderingLangCode;
	/**
	 * @uml.property  name="_searcheable"
	 */
	private boolean _searcheable;
	/**
	 * @uml.property  name="_indexingType"
	 */
	private String _indexingType;
	/**
	 * @uml.property  name="_parentEntity"
	 * @uml.associationEnd  
	 */
	private IApsEntity _parentEntity;
	
	/**
	 * @uml.property  name="_handler"
	 * @uml.associationEnd  
	 */
	private AttributeHandlerInterface _handler;
	
	/**
	 * @uml.property  name="_disablingCodes" multiplicity="(0 -1)" dimension="1"
	 */
	private String[] _disablingCodes;
	/**
	 * @uml.property  name="_roles" multiplicity="(0 -1)" dimension="1"
	 */
	private String[] _roles;
	/**
	 * @uml.property  name="_active"
	 */
	private boolean _active = true;
	
	/**
	 * @uml.property  name="_validationRules"
	 * @uml.associationEnd  
	 */
	private IAttributeValidationRules _validationRules;
	
}