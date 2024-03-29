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
package com.agiletec.aps.system.common.entity.model.attribute.util;

import org.jdom.Element;

import com.agiletec.aps.system.ApsSystemUtils;

/**
 * @author E.Santoboni
 */
public class BaseAttributeValidationRules implements IAttributeValidationRules {
	
	@Override
	public IAttributeValidationRules clone() {
		BaseAttributeValidationRules clone = null;
		try {
			Class validationConditionClass = Class.forName(this.getClass().getName());
			clone = (BaseAttributeValidationRules) validationConditionClass.newInstance();
			clone.setRequired(this.isRequired());
			if (null != this.getOgnlValidationRule()) {
				clone.setOgnlValidationRule(this.getOgnlValidationRule().clone());
			}
		} catch (Exception e) {
			ApsSystemUtils.logThrowable(e, this, "clone");
			throw new RuntimeException("Error detected while cloning the ValidationRules class '" 
					+ this.getClass().getName() + "' ");
		}
		return clone;
	}
	
	@Override
	public Element getJDOMConfigElement() {
		Element configElement = null;
		try {
			if (this.isEmpty()) return null;
			configElement = new Element(VALIDATIONS_ELEMENT_NAME);
			this.fillJDOMConfigElement(configElement);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "getJDOMConfigElement");
			throw new RuntimeException("Error detected while creating jdom element", t);
		}
		return configElement;
	}
	
	protected void fillJDOMConfigElement(Element configElement) {
		if (this.isRequired()) {
			Element element = new Element("required");
			element.setText("true");
			configElement.addContent(element);
		}
		if (null != this.getOgnlValidationRule()) {
			Element exprElement = this.getOgnlValidationRule().getConfigElement();
			if (null != exprElement) {
				configElement.addContent(exprElement);
			}
		}
	}
	
	@Override
	public void setConfig(Element attributeElement) {
		Element validationElement = attributeElement.getChild(VALIDATIONS_ELEMENT_NAME);
		if (null != validationElement) {
			this.extractValidationRules(validationElement);
		}
	}
	
	protected void extractValidationRules(Element validationElement) {
		String required = this.extractValue(validationElement, "required");
		this.setRequired(null != required && required.equalsIgnoreCase("true"));
		Element expressionElement = validationElement.getChild("expression");
		if (null != expressionElement) {
			OgnlValidationRule validationRule = new OgnlValidationRule(expressionElement);
			this.setOgnlValidationRule(validationRule);
		}
	}
	
	protected String extractValue(Element validationElements, String qName) {
		Element element = validationElements.getChild(qName);
		if (null != element) {
			return element.getText();
		}
		return null;
	}
	
	protected boolean isEmpty() {
		return (!this.isRequired() && null == this.getOgnlValidationRule());
	}
	
	@Override
	public boolean isRequired() {
		return this._required;
	}
	@Override
	public void setRequired(boolean required) {
		this._required = required;
	}
	
	@Override
	public OgnlValidationRule getOgnlValidationRule() {
		return _ognlValidationRule;
	}
	@Override
	public void setOgnlValidationRule(OgnlValidationRule ognlValidationRule) {
		this._ognlValidationRule = ognlValidationRule;
	}

	/**
	 * @uml.property  name="_required"
	 */
	private boolean _required;
	
	/**
	 * @uml.property  name="_ognlValidationRule"
	 * @uml.associationEnd  
	 */
	private OgnlValidationRule _ognlValidationRule;
	
}
