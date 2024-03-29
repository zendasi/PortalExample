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
package com.agiletec.aps.system.common.entity.parse.attribute;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface;
import com.agiletec.aps.system.common.entity.model.attribute.CompositeAttribute;

/**
 * Handler class that interprets the XML defining a 'Composite Attribute'.
 * @author E.Santoboni
 */
public class CompositeAttributeHandler extends AbstractAttributeHandler {
	
	@Override
	public void startAttribute(Attributes attributes, String qName) throws SAXException {
		if (qName.equals("composite")) {
			startComposite(attributes, qName);
		} else {
			this.startAttributeElement(attributes, qName);
		}
	}
	
	private void startAttributeElement(Attributes attributes, String qName) throws SAXException {
		if (null == this._elementAttribute) {
			if (qName.equals("attribute")) {
				String attributename = extractAttribute(attributes, "name", qName, true);
				this._elementAttribute = ((CompositeAttribute) this.getCurrentAttr()).getAttribute(attributename);
			}
		} else {
			if (null == this._elementAttributeHandler) {
				this._elementAttributeHandler = this._elementAttribute.getHandler();
				this._elementAttributeHandler.setCurrentAttr(this._elementAttribute);
			}
			this._elementAttributeHandler.startAttribute(attributes, qName);
		}
	}
	
	private void startComposite(Attributes attributes, String qName) throws SAXException {
		//nothing to do
	}
	
	@Override
	public void endAttribute(String qName, StringBuffer textBuffer) {
		if (qName.equals("composite")) {
			endComposite();
		} else {
			if (null == _elementAttributeHandler || _elementAttributeHandler.isEndAttribute(qName)) {
				_elementAttribute = null;
				_elementAttributeHandler = null;
			} else {
				_elementAttributeHandler.endAttribute(qName, textBuffer);
			}
		}
	}
	
	private void endComposite() {
		_elementAttribute = null;
		_elementAttributeHandler = null;
	}
	
	@Override
	public boolean isEndAttribute(String qName) {
		return qName.equals("composite");
	}
	
	/**
	 * @uml.property  name="_elementAttribute"
	 * @uml.associationEnd  
	 */
	private AttributeInterface _elementAttribute;
	/**
	 * @uml.property  name="_elementAttributeHandler"
	 * @uml.associationEnd  
	 */
	private AttributeHandlerInterface _elementAttributeHandler;

}