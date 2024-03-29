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

/**
 * @author E.Santoboni
 */
public abstract class AbstractAttributeValidationRules extends BaseAttributeValidationRules {
	
	@Override
	public IAttributeValidationRules clone() {
		AbstractAttributeValidationRules clone = (AbstractAttributeValidationRules) super.clone();
		clone.setRangeEnd(this.getRangeEnd());
		clone.setRangeEndAttribute(this.getRangeEndAttribute());
		clone.setRangeStart(this.getRangeStart());
		clone.setRangeStartAttribute(this.getRangeStartAttribute());
		clone.setValue(this.getValue());
		clone.setValueAttribute(this.getValueAttribute());
		return clone;
	}
	
	
	protected void insertJDOMConfigElement(String conditionRuleCode, 
			String attributeName, String toStringValue, Element configElement) {
		if ((toStringValue != null  && toStringValue.trim().length() > 0) || (attributeName != null && attributeName.trim().length() > 0)) {
			Element element = new Element(conditionRuleCode);
			if (toStringValue != null  && toStringValue.trim().length() > 0) {
				element.setText(toStringValue);
			} else {
				element.setAttribute("attribute", attributeName);
			}
			configElement.addContent(element);
		}
	}
	
	@Override
	protected boolean isEmpty() {
		return (super.isEmpty() 
				&& (null == this.getRangeStart()) 
				&& (null == this.getRangeEnd()) 
				&& (null == this.getValue()) 
				&& (null == this.getRangeStartAttribute() || this.getRangeStartAttribute().trim().length() == 0) 
				&& (null == this.getRangeEndAttribute() || this.getRangeEndAttribute().trim().length() == 0) 
				&& (null == this.getValueAttribute() || this.getValueAttribute().trim().length() == 0) );
	}
	
	public Object getValue() {
		return _value;
	}
	public void setValue(Object value) {
		this._value = value;
	}
	
	public Object getRangeStart() {
		return _rangeStart;
	}
	public void setRangeStart(Object rangeStart) {
		this._rangeStart = rangeStart;
	}
	
	public Object getRangeEnd() {
		return _rangeEnd;
	}
	public void setRangeEnd(Object rangeEnd) {
		this._rangeEnd = rangeEnd;
	}
	
	public String getValueAttribute() {
		return _valueAttribute;
	}
	public void setValueAttribute(String equalAttribute) {
		this._valueAttribute = equalAttribute;
	}
	
	public String getRangeStartAttribute() {
		return _rangeStartAttribute;
	}
	public void setRangeStartAttribute(String rangeStartAttribute) {
		this._rangeStartAttribute = rangeStartAttribute;
	}
	
	public String getRangeEndAttribute() {
		return _rangeEndAttribute;
	}
	public void setRangeEndAttribute(String rangeEndAttribute) {
		this._rangeEndAttribute = rangeEndAttribute;
	}
	
	/**
	 * @uml.property  name="_value"
	 */
	private Object _value;
	/**
	 * @uml.property  name="_rangeStart"
	 */
	private Object _rangeStart;
	/**
	 * @uml.property  name="_rangeEnd"
	 */
	private Object _rangeEnd;
	/**
	 * @uml.property  name="_valueAttribute"
	 */
	private String _valueAttribute;
	/**
	 * @uml.property  name="_rangeStartAttribute"
	 */
	private String _rangeStartAttribute;
	/**
	 * @uml.property  name="_rangeEndAttribute"
	 */
	private String _rangeEndAttribute;
	
	protected static final String RULE_TYPE = "type";
	
}
