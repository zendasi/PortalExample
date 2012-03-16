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
import java.util.List;

import org.jdom.Element;

import com.agiletec.aps.system.common.entity.model.AttributeSearchInfo;
import com.agiletec.aps.system.common.entity.model.IApsEntity;
import com.agiletec.aps.system.common.entity.model.attribute.util.IAttributeValidationRules;
import com.agiletec.aps.system.common.entity.parse.attribute.AttributeHandlerInterface;
import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.aps.system.services.lang.Lang;

/**
 * Interface for Entity Attributes. The attributes contain the informations constituting the Entity. Is it very important to implement the clone() method correctly since it is used when cloning Entity Attributes and when populating list of attributes. 
 * @author  W.Ambu
 */
public interface AttributeInterface extends Serializable {

	/**
	 * Return the name of the attribute
	 * @return  The name of the attribute
	 * @uml.property  name="name"
	 */
	public String getName();

	/**
	 * Set up the name of the attribute
	 * @param name  The name to set
	 * @uml.property  name="name"
	 */
	public void setName(String name);

	/**
	 * Return the attribute type
	 * @return  The attribute type
	 * @uml.property  name="type"
	 */
	public String getType();

	/**
	 * Set up the type of the attribute. The name is the ID of the attribute, picked  from the attribute configuration-
	 * @param typeName  The type of the attribute.
	 * @uml.property  name="type"
	 */
	public void setType(String typeName);

	/**
	 * Test whether the attribute supports multiple languages or not. 
	 * @return True if the attribute supports multiple languages, false otherwise.
	 */	
	public boolean isMultilingual();
	
	/**
	 * Test whether the current attribute is a Text Attribute or not.
	 * @return True if the attribute is a text, false otherwise.
	 */
	public boolean isTextAttribute();
	
	/**
	 * Test if the current attribute is a "simple" one. Simple attributes are those
	 * composed by only one attribute; "Composed" one are built aggregating,
	 * in various manner, simple attributes.
	 * @return True if the current attribute is a simple on, false if the attribute is a 
	 * composed.
	 */
	public boolean isSimple();

	/**
	 * Return a clone of the prototype attribute.
	 * @return  The clone of the prototype of the current attribute.
	 * @uml.property  name="attributePrototype"
	 */
	public Object getAttributePrototype();

	/**
	 * Set up the default language to utilize when, during the rendering of the attribute, the 
	 * informations requested are not available in the desired language.
	 * @param langCode The code of the default language
	 */
	public void setDefaultLangCode(String langCode);

	/**
	 * Set up the language to use when rendering the attribute.
	 * @param langCode The code to use when rendering the attribute.
	 */
	public void setRenderingLang(String langCode);

	/**
	 * Test whether the attribute is searchable (using a query on the DB) or not. The
	 * information held by a searchable attribute is replicated in an appropriate table
	 * used for SQL queries.
	 * @return True if the attribute is a searchable one. 
	 */	
	public boolean isSearcheable();

	/**
	 * Set up the searchable status of an attribute. When set to 'true' then the
	 * information held by the current attribute is replicated in an appropriate table
	 * used for SQL queries.
	 * @param sercheable True if the attribute is of searchable type, false otherwise.
	 */	
	public void setSearcheable(boolean sercheable);

	/**
	 * Test whether the attribute has the searchable option supported or not. 
	 * @return True if the attribute has the searchable option supported. 
	 */	
	public boolean isSearchableOptionSupported();
	
	/**
	 * Return the list of the informations characterizing the attribute in order
	 * to perform database queries.
	 * @param systemLangs The list of the system languages.
	 * @return The list of the characterizing informations.
	 */
	public List<AttributeSearchInfo> getSearchInfos(List<Lang> systemLangs);
	
	/**
	 * Test whether this attribute is declared mandatory or not.
	 * @return True if the attribute is mandatory, false otherwise.
	 */
	public boolean isRequired();

	/**
	 * Set up the required (mandatory) condition for the current attribute.
	 * @param required True if the attribute is mandatory
	 */
	public void setRequired(boolean required);

	/**
	 * Return the JDOM portion representing the attribute to further include in a document which will result in a complete entity. 
	 * @return   The JDOM element and eventually its children, representing the attribute.
	 * @uml.property  name="jDOMElement"
	 * @uml.associationEnd  
	 */
	public Element getJDOMElement();
	
	/**
	 * Get the XML element that determines the configuration of the type.
	 * @return   The XML element configuring the type
	 * @uml.property  name="jDOMConfigElement"
	 * @uml.associationEnd  
	 */
	public Element getJDOMConfigElement();
	
	/**
	 * Return the indexing type of the attribute in the search engine.
	 * @return  The indexing type, chosen among the constants defined in this interface
	 * @uml.property  name="indexingType"
	 */
	public String getIndexingType();

	/**
	 * Set up the indexing type of the attribute. in the search engine.
	 * @param indexingType  One of the constants defined in this interface.
	 * @uml.property  name="indexingType"
	 */
	public void setIndexingType(String indexingType);

	/**
	 * Build the configuration of the attribute using the JDOM description for the
	 * attribute.
	 * @param attributeElement The JDOM element as extracted from the XML which configures
	 * the Entity Type.
	 * @throws ApsSystemException in case of error
	 */
	public void setAttributeConfig(Element attributeElement) throws ApsSystemException;
	
	/**
	 * Set up the the reference to the entity that includes the attribute.
	 * @param parentEntity  The reference to the entity including the attribute.
	 * @uml.property  name="parentEntity"
	 */
	public void setParentEntity(IApsEntity parentEntity);
	
	/**
	 * Return the reference to the entity that includes this attribute.
	 * @return   The reference to the entity which includes the attribute.
	 * @uml.property  name="parentEntity"
	 * @uml.associationEnd  
	 */
	public IApsEntity getParentEntity();
	
	/**
	 * Return the handler class which parses the XML element related to the attribute, as obtained from the XML describing the entity.
	 * @return   The handler class which parses the attribute.
	 * @uml.property  name="handler"
	 * @uml.associationEnd  
	 */
	public AttributeHandlerInterface getHandler();
	
	/**
	 * Set up the handler class which parses the XML element related to the attribute, as obtained from the XML describing the entity
	 * @param The  handler class which parses the attribute.
	 * @uml.property  name="handler"
	 */
	public void setHandler(AttributeHandlerInterface handler);
	
	/**
	 * Check whether the attribute is active or not.
	 * @return True if the attribute is active, false otherwise.
	 */
	public boolean isActive();
	
	/**
	 * Disable the attribute depending on the given deactivation code.
	 * @param disablingCode The deactivation code.
	 */
	public void disable(String disablingCode);
	
	/**
	 * Set up the deactivation code to disable the attribute.
	 * @param disablingCodes The deactivation code.
	 */
	public void setDisablingCodes(String[] disablingCodes);
	
	public String[] getDisablingCodes();
	
	/**
	 * @uml.property  name="validationRules"
	 * @uml.associationEnd  
	 */
	public IAttributeValidationRules getValidationRules();
	
	/**
	 * @param validationRules
	 * @uml.property  name="validationRules"
	 */
	public void setValidationRules(IAttributeValidationRules validationRules);
	
	/**
	 * Return the names of the associated roles.
	 * @return The name of the roles.
	 */
	public String[] getRoles();
	
	/**
	 * Set the roles to the attibute.
	 * @param roles The role to set.
	 */
	public void setRoles(String[] roles);
	
	/**
	 * @uml.property  name="value"
	 */
	public Object getValue();
	
}