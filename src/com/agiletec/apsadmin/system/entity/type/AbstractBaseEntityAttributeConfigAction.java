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
package com.agiletec.apsadmin.system.entity.type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.common.entity.IEntityManager;
import com.agiletec.aps.system.common.entity.model.IApsEntity;
import com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface;
import com.agiletec.aps.system.common.entity.model.attribute.AttributeRole;
import com.agiletec.aps.system.common.entity.model.attribute.DateAttribute;
import com.agiletec.aps.system.common.entity.model.attribute.EnumeratorAttribute;
import com.agiletec.aps.system.common.entity.model.attribute.NumberAttribute;
import com.agiletec.aps.system.common.entity.model.attribute.util.EnumeratorAttributeItemsExtractor;
import com.agiletec.aps.system.common.entity.model.attribute.util.IAttributeValidationRules;
import com.agiletec.aps.system.common.entity.model.attribute.util.DateAttributeValidationRules;
import com.agiletec.aps.system.common.entity.model.attribute.util.NumberAttributeValidationRules;
import com.agiletec.aps.system.common.entity.model.attribute.util.OgnlValidationRule;
import com.agiletec.aps.system.common.entity.model.attribute.util.TextAttributeValidationRules;
import com.agiletec.aps.system.common.searchengine.IndexableAttributeInterface;
import com.agiletec.apsadmin.system.BaseAction;

/**
 * Base action for Configure Entity Attributes.
 * @author E.Santoboni
 */
public class AbstractBaseEntityAttributeConfigAction extends BaseAction implements BeanFactoryAware {
	
	/**
	 * Fill form fields.
	 * @param attribute 
	 */
	protected void valueFormFields(AttributeInterface attribute) {
		this.setAttributeName(attribute.getName());
		this.setAttributeTypeCode(attribute.getType());
		if (null != attribute.getRoles()) {
			this.setAttributeRoles(Arrays.asList(attribute.getRoles()));
		}
		if (null != attribute.getDisablingCodes()) {
			this.setDisablingCodes(Arrays.asList(attribute.getDisablingCodes()));
		}
		IAttributeValidationRules valRule = attribute.getValidationRules();
		this.setRequired(new Boolean(valRule.isRequired()));
		this.setOgnlValidationRule(valRule.getOgnlValidationRule());
		this.setSearcheable(new Boolean(attribute.isSearcheable()));
		String indexingType = attribute.getIndexingType();
		if (null != indexingType) {
			this.setIndexable(indexingType.equalsIgnoreCase(IndexableAttributeInterface.INDEXING_TYPE_TEXT));
		}
		if (attribute.isTextAttribute()) {
			TextAttributeValidationRules textValRule = (TextAttributeValidationRules) valRule;
			if (textValRule.getMaxLength() > -1) {
				this.setMaxLength(textValRule.getMaxLength());
			}
			if (textValRule.getMinLength() > -1) {
				this.setMinLength(textValRule.getMinLength());
			}
			this.setRegexp(textValRule.getRegexp());
			this.setRangeEndString((String) textValRule.getRangeEnd());
			this.setRangeStartString((String) textValRule.getRangeStart());
			this.setEqualString((String) textValRule.getValue());
			this.setRangeEndStringAttribute(textValRule.getRangeEndAttribute());
			this.setRangeStartStringAttribute(textValRule.getRangeStartAttribute());
			this.setEqualStringAttribute(textValRule.getValueAttribute());
			if (attribute instanceof EnumeratorAttribute) {
				EnumeratorAttribute enumeratorAttribute = (EnumeratorAttribute) attribute;
				this.setEnumeratorStaticItems(enumeratorAttribute.getStaticItems());
				this.setEnumeratorStaticItemsSeparator(enumeratorAttribute.getCustomSeparator());
				this.setEnumeratorExtractorBean(enumeratorAttribute.getExtractorBeanName());
			}
		}
		if (attribute instanceof DateAttribute) {
			DateAttributeValidationRules dateValRule = (DateAttributeValidationRules) valRule;
			this.setRangeEndDate((Date) dateValRule.getRangeEnd());
			this.setRangeStartDate((Date) dateValRule.getRangeStart());
			this.setEqualDate((Date) dateValRule.getValue());
			this.setRangeEndDateAttribute(dateValRule.getRangeEndAttribute());
			this.setRangeStartDateAttribute(dateValRule.getRangeStartAttribute());
			this.setEqualDateAttribute(dateValRule.getValueAttribute());
		}
		if (attribute instanceof NumberAttribute) {
			NumberAttributeValidationRules nulValRule = (NumberAttributeValidationRules) valRule;
			this.setRangeEndNumber((Integer) nulValRule.getRangeEnd());
			this.setRangeStartNumber((Integer) nulValRule.getRangeStart());
			this.setEqualNumber((Integer) nulValRule.getValue());
			this.setRangeEndNumberAttribute(nulValRule.getRangeEndAttribute());
			this.setRangeStartNumberAttribute(nulValRule.getRangeStartAttribute());
			this.setEqualNumberAttribute(nulValRule.getValueAttribute());
		}
	}
	
	/**
	 * Fill attribute fields.
	 * @param attribute The attribute to edit with the form values.
	 * @return A customized return code in the attribute needs a extra configuration, else null.
	 */
	protected String fillAttributeFields(AttributeInterface attribute) {
		attribute.setRoles(this.createStringArray(this.getAttributeRoles()));
		attribute.setDisablingCodes(this.createStringArray(this.getDisablingCodes()));
		attribute.setSearcheable(null != this.getSearcheable() && this.getSearcheable());
		String indexingType = IndexableAttributeInterface.INDEXING_TYPE_NONE;
		if (null != this.getIndexable()) {
			indexingType = IndexableAttributeInterface.INDEXING_TYPE_TEXT;
		}
		attribute.setIndexingType(indexingType);
		IAttributeValidationRules valCond = attribute.getValidationRules();
		valCond.setRequired(null != this.getRequired() && this.getRequired());
		valCond.setOgnlValidationRule(this.getOgnlValidationRule());
		if (attribute.isTextAttribute()) {
			TextAttributeValidationRules valRule = (TextAttributeValidationRules) valCond;
			int maxLength = ((null != this.getMaxLength()) ? this.getMaxLength().intValue() : -1);
			valRule.setMaxLength(maxLength);
			int minLength = ((null != this.getMinLength()) ? this.getMinLength().intValue() : -1);
			valRule.setMinLength(minLength);
			valRule.setRegexp(this.getRegexp());
			valRule.setRangeEnd(this.getRangeEndString());
			valRule.setRangeStart(this.getRangeStartString());
			valRule.setValue(this.getEqualString());
			valRule.setRangeEndAttribute(this.getRangeEndStringAttribute());
			valRule.setRangeStartAttribute(this.getRangeStartStringAttribute());
			valRule.setValueAttribute(this.getEqualStringAttribute());
			if (attribute instanceof EnumeratorAttribute) {
				EnumeratorAttribute enumeratorAttribute = (EnumeratorAttribute) attribute;
				enumeratorAttribute.setStaticItems(this.getEnumeratorStaticItems());
				if (null != this.getEnumeratorStaticItemsSeparator() && this.getEnumeratorStaticItemsSeparator().length()>0) {
					enumeratorAttribute.setCustomSeparator(this.getEnumeratorStaticItemsSeparator());
				}
				if (null != this.getEnumeratorExtractorBean() && this.getEnumeratorExtractorBean().trim().length() > 0) {
					enumeratorAttribute.setExtractorBeanName(this.getEnumeratorExtractorBean());
				} else {
					enumeratorAttribute.setExtractorBeanName(null);
				}
			}
		}
		if (attribute instanceof DateAttribute) {
			DateAttributeValidationRules dateValRule = (DateAttributeValidationRules) valCond;
			dateValRule.setRangeEnd(this.getRangeEndDate());
			dateValRule.setRangeStart(this.getRangeStartDate());
			dateValRule.setValue(this.getEqualDate());
			dateValRule.setRangeEndAttribute(this.getRangeEndDateAttribute());
			dateValRule.setRangeStartAttribute(this.getRangeStartDateAttribute());
			dateValRule.setValueAttribute(this.getEqualDateAttribute());
		}
		if (attribute instanceof NumberAttribute) {
			NumberAttributeValidationRules nulValRule = (NumberAttributeValidationRules) valCond;
			nulValRule.setRangeEnd(this.getRangeEndNumber());
			nulValRule.setRangeStart(this.getRangeStartNumber());
			nulValRule.setValue(this.getEqualNumber());
			nulValRule.setRangeEndAttribute(this.getRangeEndNumberAttribute());
			nulValRule.setRangeStartAttribute(this.getRangeStartNumberAttribute());
			nulValRule.setValueAttribute(this.getEqualNumberAttribute());
		}
		return null;
	}
	
	private String[] createStringArray(List<String> list) {
		if (null == list || list.isEmpty()) return null;
		String[] array = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}
	
	/**
	 * Return the current entity type on edit.
	 * @return The current entity type.
	 */
	public IApsEntity getEntityType() {
		return (IApsEntity) this.getRequest().getSession().getAttribute(IEntityTypeConfigAction.ENTITY_TYPE_ON_EDIT_SESSION_PARAM);
	}
	
	/**
	 * Return the entity manager name that manages the current entity on edit.
	 * @return The entity manager name.
	 */
	public String getEntityManagerName() {
		return (String) this.getRequest().getSession().getAttribute(IEntityTypeConfigAction.ENTITY_TYPE_MANAGER_SESSION_PARAM);
	}
	
	/**
	 * Return the entity manager that manages the current entity on edit.
	 * @return The entity manager.
	 */
	protected IEntityManager getEntityManager() {
		String entityManagerName = this.getEntityManagerName();
		return (IEntityManager) this.getBeanFactory().getBean(entityManagerName);
	}
	
	/**
	 * Return the namespace prefix specific for the current entity manager.
	 * The prefix will extract by the object {@link EntityTypeNamespaceInfoBean} associated to the current entity manager.
	 * @return The namespace prefix specific for the current entity manager.
	 */
	public String getEntityTypeManagementNamespacePrefix() {
		try {
			EntityTypeNamespaceInfoBean infoBean = (EntityTypeNamespaceInfoBean) this.getBeanFactory().getBean(this.getEntityManagerName()+"NamespaceInfoBean");
			return infoBean.getNamespacePrefix();
		} catch (Throwable t) {
			//nothing to do
		}
		return "";
	}
	
	/**
	 * Indicates whether the current entity manager is a Search Engine user or not.
	 * @return True if the current entity manager is a Search Engine user, false otherwise.
	 */
	public boolean isEntityManagerSearchEngineUser() {
		return this.getEntityManager().isSearchEngineUser();
	}
	
	public boolean isIndexableOptionSupported(String attributeTypeCode) {
		try {
			AttributeInterface attribute = this.getAttributePrototype(attributeTypeCode);
			return (null == attribute.getIndexingType());
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "isIndexableOptionSupported");
		}
		return false;
	}
	
	public boolean isSearchableOptionSupported(String attributeTypeCode) {
		try {
			AttributeInterface attribute = this.getAttributePrototype(attributeTypeCode);
			return attribute.isSearchableOptionSupported();
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "isSearchableOptionSupported");
		}
		return false;
	}
	
	public AttributeInterface getAttributePrototype(String typeCode) {
		IEntityManager entityManager = this.getEntityManager();
		Map<String, AttributeInterface> attributeTypes = entityManager.getEntityAttributePrototypes();
		return attributeTypes.get(typeCode);
	}
	
	/**
	 * Return the list of the other entity attributes of the same type of the current on edit.
	 * @return The list of the other entity attributes of the same type.
	 */
	public List<AttributeInterface> getSameAttributes() {
		AttributeInterface attributePrototype = this.getAttributePrototype(this.getAttributeTypeCode());
		List<AttributeInterface> attributes = new ArrayList<AttributeInterface>();
		List<AttributeInterface> currentEntityAttributes = this.getEntityType().getAttributeList();
		for (int i = 0; i < currentEntityAttributes.size(); i++) {
			AttributeInterface attribute = currentEntityAttributes.get(i);
			if (attribute.getClass().isInstance(attributePrototype) 
					&& (null == this.getAttributeName() || !attribute.getName().equals(this.getAttributeName()))) {
				attributes.add(attribute);
			}
		}
		return attributes;
	}
	
	/**
	 * Return the list of the attribute role not in use on the entity type on edit.
	 * @return The list of the free attribute roles.
	 */
	public List<AttributeRole> getFreeAttributeRoleNames() {
		List<AttributeRole> freeRoles = new ArrayList<AttributeRole>();
		List<AttributeRole> roles = this.getAttributeRoles(this.getAttributeTypeCode());
		if (null == roles) return freeRoles;
		for (int i = 0; i < roles.size(); i++) {
			AttributeRole role = roles.get(i);
			AttributeInterface utilizer = this.getEntityType().getAttributeByRole(role.getName());
			if (null == utilizer || utilizer.getName().equals(this.getAttributeName())) {
				freeRoles.add(role);
			}
		}
		return freeRoles;
	}
	
	protected List<AttributeRole> getAttributeRoles(String attributeTypeCode) {
		List<AttributeRole> rolesByType = new ArrayList<AttributeRole>();
		List<AttributeRole> roles = this.getEntityManager().getAttributeRoles();
		if (null == roles) return rolesByType;
		for (int i = 0; i < roles.size(); i++) {
			AttributeRole role = roles.get(i);
			if (role.getAllowedAttributeTypes().contains(attributeTypeCode)) {
				rolesByType.add(role);
			}
		}
		return rolesByType;
	}
	
	/**
	 * Return an attribute role.
	 * @param roleName The name of the tole to return.
	 * @return The required role.
	 */
	public AttributeRole getAttributeRole(String roleName) {
		return this.getEntityManager().getAttributeRole(roleName);
	}
	
	public Map<String, String> getAttributeDisablingCodes() {
		return this.getEntityManager().getAttributeDisablingCodes();
	}
	
	public List<String> getEnumeratorExtractorBeans() {
		List<String> extractors = null;
		try {
			ListableBeanFactory factory = (ListableBeanFactory) this.getBeanFactory();
			String[] defNames = factory.getBeanNamesForType(EnumeratorAttributeItemsExtractor.class);
			extractors = Arrays.asList(defNames);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "getEnumeratorExtractorBeans");
			throw new RuntimeException("Error while extracting enumerator extractor beans", t);
		}
		return extractors;
	}
	
	public int getStrutsAction() {
		return _strutsAction;
	}
	public void setStrutsAction(int strutsAction) {
		this._strutsAction = strutsAction;
	}
	
	public String getAttributeName() {
		return _attributeName;
	}
	public void setAttributeName(String attributeName) {
		this._attributeName = attributeName;
	}
	
	public String getAttributeTypeCode() {
		return _attributeTypeCode;
	}
	public void setAttributeTypeCode(String attributeTypeCode) {
		this._attributeTypeCode = attributeTypeCode;
	}
	
	public Boolean getSearcheable() {
		return _searchable;
	}
	public void setSearcheable(Boolean searcheable) {
		this._searchable = searcheable;
	}
	
	public Boolean getIndexable() {
		return _indexable;
	}
	public void setIndexable(Boolean indexable) {
		this._indexable = indexable;
	}
	
	public List<String> getAttributeRoles() {
		return _attributeRoles;
	}
	public void setAttributeRoles(List<String> attributeRoles) {
		this._attributeRoles = attributeRoles;
	}
	
	public List<String> getDisablingCodes() {
		return _disablingCodes;
	}
	public void setDisablingCodes(List<String> disablingCodes) {
		this._disablingCodes = disablingCodes;
	}
	
	public Boolean getRequired() {
		return _required;
	}
	public void setRequired(Boolean required) {
		this._required = required;
	}
	
	public OgnlValidationRule getOgnlValidationRule() {
		return _ognlValidationRule;
	}
	public void setOgnlValidationRule(OgnlValidationRule ognlValidationRule) {
		this._ognlValidationRule = ognlValidationRule;
	}
	
	public Integer getMinLength() {
		return _minLength;
	}
	public void setMinLength(Integer minLength) {
		this._minLength = minLength;
	}
	
	public Integer getMaxLength() {
		return _maxLength;
	}
	public void setMaxLength(Integer maxLength) {
		this._maxLength = maxLength;
	}
	
	public String getRegexp() {
		return _regexp;
	}
	public void setRegexp(String regexp) {
		this._regexp = regexp;
	}
	
	public String getRangeStartString() {
		return _rangeStartString;
	}
	public void setRangeStartString(String rangeStartString) {
		this._rangeStartString = rangeStartString;
	}
	
	public String getRangeEndString() {
		return _rangeEndString;
	}
	public void setRangeEndString(String rangeEndString) {
		this._rangeEndString = rangeEndString;
	}
	
	public String getRangeStartStringAttribute() {
		return _rangeStartStringAttribute;
	}
	public void setRangeStartStringAttribute(String rangeStartStringAttribute) {
		this._rangeStartStringAttribute = rangeStartStringAttribute;
	}
	
	public String getRangeEndStringAttribute() {
		return _rangeEndStringAttribute;
	}
	public void setRangeEndStringAttribute(String rangeEndStringAttribute) {
		this._rangeEndStringAttribute = rangeEndStringAttribute;
	}
	
	public String getEqualString() {
		return _equalString;
	}
	public void setEqualString(String equalString) {
		this._equalString = equalString;
	}
	
	public String getEqualStringAttribute() {
		return _equalStringAttribute;
	}
	public void setEqualStringAttribute(String equalStringAttribute) {
		this._equalStringAttribute = equalStringAttribute;
	}
	
	public Date getRangeStartDate() {
		return _rangeStartDate;
	}
	public void setRangeStartDate(Date rangeStartDate) {
		this._rangeStartDate = rangeStartDate;
	}
	
	public Date getRangeEndDate() {
		return _rangeEndDate;
	}
	public void setRangeEndDate(Date rangeEndDate) {
		this._rangeEndDate = rangeEndDate;
	}
	
	public String getRangeStartDateAttribute() {
		return _rangeStartDateAttribute;
	}
	public void setRangeStartDateAttribute(String rangeStartDateAttribute) {
		this._rangeStartDateAttribute = rangeStartDateAttribute;
	}
	
	public String getRangeEndDateAttribute() {
		return _rangeEndDateAttribute;
	}
	public void setRangeEndDateAttribute(String rangeEndDateAttribute) {
		this._rangeEndDateAttribute = rangeEndDateAttribute;
	}
	
	public Date getEqualDate() {
		return _equalDate;
	}
	public void setEqualDate(Date equalDate) {
		this._equalDate = equalDate;
	}
	
	public String getEqualDateAttribute() {
		return _equalDateAttribute;
	}
	public void setEqualDateAttribute(String equalDateAttribute) {
		this._equalDateAttribute = equalDateAttribute;
	}
	
	public Integer getRangeStartNumber() {
		return _rangeStartNumber;
	}
	public void setRangeStartNumber(Integer rangeStartNumber) {
		this._rangeStartNumber = rangeStartNumber;
	}
	
	public String getRangeStartNumberAttribute() {
		return _rangeStartNumberAttribute;
	}
	public void setRangeStartNumberAttribute(String rangeStartNumberAttribute) {
		this._rangeStartNumberAttribute = rangeStartNumberAttribute;
	}
	
	public Integer getRangeEndNumber() {
		return _rangeEndNumber;
	}
	public void setRangeEndNumber(Integer rangeEndNumber) {
		this._rangeEndNumber = rangeEndNumber;
	}
	
	public String getRangeEndNumberAttribute() {
		return _rangeEndNumberAttribute;
	}
	public void setRangeEndNumberAttribute(String rangeEndNumberAttribute) {
		this._rangeEndNumberAttribute = rangeEndNumberAttribute;
	}
	
	public Integer getEqualNumber() {
		return _equalNumber;
	}
	public void setEqualNumber(Integer equalNumber) {
		this._equalNumber = equalNumber;
	}
	
	public String getEqualNumberAttribute() {
		return _equalNumberAttribute;
	}
	public void setEqualNumberAttribute(String equalNumberAttribute) {
		this._equalNumberAttribute = equalNumberAttribute;
	}
	
	public String getEnumeratorStaticItems() {
		return _enumeratorStaticItems;
	}
	public void setEnumeratorStaticItems(String enumeratorStaticItems) {
		this._enumeratorStaticItems = enumeratorStaticItems;
	}
	
	public String getEnumeratorStaticItemsSeparator() {
		return _enumeratorStaticItemsSeparator;
	}
	public void setEnumeratorStaticItemsSeparator(String enumeratorStaticItemsSeparator) {
		this._enumeratorStaticItemsSeparator = enumeratorStaticItemsSeparator;
	}
	
	public String getEnumeratorExtractorBean() {
		return _enumeratorExtractorBean;
	}
	public void setEnumeratorExtractorBean(String enumeratorExtractorBean) {
		this._enumeratorExtractorBean = enumeratorExtractorBean;
	}
	
	protected BeanFactory getBeanFactory() {
		return _beanFactory;
	}
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this._beanFactory = beanFactory;
	}
	
	/**
	 * @uml.property  name="_strutsAction"
	 */
	private int _strutsAction;
	
	/**
	 * @uml.property  name="_attributeName"
	 */
	private String _attributeName;
	/**
	 * @uml.property  name="_attributeTypeCode"
	 */
	private String _attributeTypeCode;
	/**
	 * @uml.property  name="_searchable"
	 */
	private Boolean _searchable;
	/**
	 * @uml.property  name="_indexable"
	 */
	private Boolean _indexable;
	/**
	 * @uml.property  name="_attributeRoles"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
	private List<String> _attributeRoles;
	/**
	 * @uml.property  name="_disablingCodes"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
	private List<String> _disablingCodes;
	
	/**
	 * @uml.property  name="_required"
	 */
	private Boolean _required;
	
	/**
	 * @uml.property  name="_ognlValidationRule"
	 * @uml.associationEnd  
	 */
	private OgnlValidationRule _ognlValidationRule;
	
	/**
	 * @uml.property  name="_minLength"
	 */
	private Integer _minLength;
	/**
	 * @uml.property  name="_maxLength"
	 */
	private Integer _maxLength;
	/**
	 * @uml.property  name="_regexp"
	 */
	private String _regexp;
	
	/**
	 * @uml.property  name="_rangeStartString"
	 */
	private String _rangeStartString;
	/**
	 * @uml.property  name="_rangeEndString"
	 */
	private String _rangeEndString;
	/**
	 * @uml.property  name="_rangeStartStringAttribute"
	 */
	private String _rangeStartStringAttribute;
	/**
	 * @uml.property  name="_rangeEndStringAttribute"
	 */
	private String _rangeEndStringAttribute;
	/**
	 * @uml.property  name="_equalString"
	 */
	private String _equalString;
	/**
	 * @uml.property  name="_equalStringAttribute"
	 */
	private String _equalStringAttribute;
	
	/**
	 * @uml.property  name="_rangeStartDate"
	 */
	private Date _rangeStartDate;
	/**
	 * @uml.property  name="_rangeEndDate"
	 */
	private Date _rangeEndDate;
	/**
	 * @uml.property  name="_rangeStartDateAttribute"
	 */
	private String _rangeStartDateAttribute;
	/**
	 * @uml.property  name="_rangeEndDateAttribute"
	 */
	private String _rangeEndDateAttribute;
	/**
	 * @uml.property  name="_equalDate"
	 */
	private Date _equalDate;
	/**
	 * @uml.property  name="_equalDateAttribute"
	 */
	private String _equalDateAttribute;
	
	/**
	 * @uml.property  name="_rangeStartNumber"
	 */
	private Integer _rangeStartNumber;
	/**
	 * @uml.property  name="_rangeStartNumberAttribute"
	 */
	private String _rangeStartNumberAttribute;
	/**
	 * @uml.property  name="_rangeEndNumber"
	 */
	private Integer _rangeEndNumber;
	/**
	 * @uml.property  name="_rangeEndNumberAttribute"
	 */
	private String _rangeEndNumberAttribute;
	/**
	 * @uml.property  name="_equalNumber"
	 */
	private Integer _equalNumber;
	/**
	 * @uml.property  name="_equalNumberAttribute"
	 */
	private String _equalNumberAttribute;
	
	/**
	 * @uml.property  name="_enumeratorStaticItems"
	 */
	private String _enumeratorStaticItems;
	/**
	 * @uml.property  name="_enumeratorStaticItemsSeparator"
	 */
	private String _enumeratorStaticItemsSeparator;
	/**
	 * @uml.property  name="_enumeratorExtractorBean"
	 */
	private String _enumeratorExtractorBean;
	
	/**
	 * @uml.property  name="_beanFactory"
	 * @uml.associationEnd  
	 */
	private BeanFactory _beanFactory;
	
}