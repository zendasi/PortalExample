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
package com.agiletec.aps.system.common.entity.parse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.exception.ApsSystemException;

/**
 * Abstract Dom class parser of Attribute support Objects.
 * @author  E.Santoboni
 */
public abstract class AbstractAttributeSupportObjectDOM {
	
	protected void validate(String xmlText, String definitionPath) throws ApsSystemException {
		SchemaFactory factory = 
            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		InputStream schemaIs = null;
		InputStream xmlIs = null;
		try {
			schemaIs = this.getClass().getResourceAsStream(this.getSchemaFileName());
			Source schemaSource = new StreamSource(schemaIs);
			Schema schema = factory.newSchema(schemaSource);
	        Validator validator = schema.newValidator();
	        xmlIs = new ByteArrayInputStream(xmlText.getBytes("UTF-8"));
	        Source source = new StreamSource(xmlIs);
	        validator.validate(source);
	        ApsSystemUtils.getLogger().info("Valid definition : " + definitionPath);
        } catch (Throwable t) {
        	String message = "Error validating definition : " + definitionPath;
        	ApsSystemUtils.logThrowable(t, this, "this", message);
        	throw new ApsSystemException(message, t);
        } finally {
        	try {
				if (null != schemaIs) schemaIs.close();
				if (null != xmlIs) xmlIs.close();
			} catch (IOException e) {
				ApsSystemUtils.logThrowable(e, this, "this");
			}
        }
	}
	
	protected Document decodeDOM(String xmlText) throws ApsSystemException {
		Document doc = null;
		SAXBuilder builder = new SAXBuilder();
		builder.setValidation(false);
		StringReader reader = new StringReader(xmlText);
		try {
			doc = builder.build(reader);
		} catch (Exception ex) {
			throw new ApsSystemException("Error while parsing: " + ex.getMessage(), ex);
		}
		return doc;
	}
	
	/**
	 * @uml.property  name="schemaFileName"
	 */
	protected abstract String getSchemaFileName();
	
}