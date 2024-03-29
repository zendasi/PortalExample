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
package com.agiletec.aps.system.common.entity.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.common.FieldSearchFilter;
import com.agiletec.aps.system.common.entity.model.attribute.AttributeInterface;
import com.agiletec.aps.system.common.entity.model.attribute.BooleanAttribute;
import com.agiletec.aps.system.common.entity.model.attribute.DateAttribute;
import com.agiletec.aps.system.common.entity.model.attribute.ITextAttribute;
import com.agiletec.aps.system.common.entity.model.attribute.NumberAttribute;
import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.aps.util.DateConverter;

/**
 * This class implements a filter to search among entities.
 * @author E.Santoboni
 */
public class EntitySearchFilter extends FieldSearchFilter implements Serializable {
	
	/**
	 * Filter constructor.
	 * This constructor is used when checking the presence of a value contained
	 * either in the attribute field or in the entity metadata.
	 * @param key The key of the filtering element; it may be a content attribute of type {@link AttributeInterface}
	 * or the ID of metadata).
	 * @param isAttributeFilter Decide whether the filter is applied to an Entity Attribute (true) or
	 * to a metadata (false).
	 */
	public EntitySearchFilter(String key, boolean isAttributeFilter) {
		super(key);
		this.setAttributeFilter(isAttributeFilter);
	}
	
	/**
	 * Filter constructor.
	 * This constructor must be used to filter the attribute values or entity metadata
	 * looking for a specific value.
	 * @param key The key of the filtering element; it may be a content attribute of type {@link AttributeInterface}
	 * or the ID of metadata).
	 * @param isAttributeFilter Decide whether the filter is applied to an Entity Attribute (true) or
	 * to a metadata (false).
	 * @param value The value to look for. If null, the filter checks if the attribute (or metadata)
	 * has been valued.
	 * @param useLikeOption When true the database search will be performed using the "LIKE" clause.
	 * This option can be used to filter by the value of a string attribute (or metadata). It can be
	 * used only with string and with not null values.
	 */
	public EntitySearchFilter(String key, boolean isAttributeFilter, Object value, boolean useLikeOption) {
		this(key, isAttributeFilter);
		this.setValue(value);
		this.setLikeOption(useLikeOption);
	}
	
	/**
	 * Filter constructor.
	 * This constructor is used when filtering by a range of values; this can applied to both
	 * Entity Attributes and metadata).
	 * @param key The key of the filtering element; it may be a content attribute of type {@link AttributeInterface}
	 * or the ID of metadata).
	 * @param isAttributeFilter Decide whether the filter is applied to an Entity Attribute (true) or
	 * to a metadata (false).
	 * @param start The starting value of the interval. It can be an object of type
	 * "String", "Date", "BigDecimal", "Boolean" o null.
	 * @param end The ending value of the interval. It can be an object of type 
	 * "String", "Date", "BigDecimal", "Boolean" o null.
	 */
	public EntitySearchFilter(String key, boolean isAttributeFilter, Object start, Object end) {
		this(key, isAttributeFilter);
		if (start != null && end != null && !start.getClass().equals(end.getClass())) {
			throw new RuntimeException("Error: 'start' and 'end' types have to be equals");
		}
		this.setStart(start);
		this.setEnd(end);
	}
	
	/**
	 * Filter constructor.
	 * This constructor is used when filtering by a collection of allowed values; this can applied to both
	 * Entity Attributes and metadata).
	 * @param key The key of the filtering element; it may be a content attribute of type {@link AttributeInterface}
	 * or the ID of metadata).
	 * @param isAttributeFilter Decide whether the filter is applied to an Entity Attribute (true) or
	 * to a metadata (false).
	 * @param allowedValues The allowed values to look for. If null, the filter checks 
	 * if the attribute (or metadata) has been valued.
	 * @param useLikeOption When true the database search will be performed using the "LIKE" clause.
	 * This option can be used to filter by the value of a string attribute (or metadata). It can be
	 * used only with string and with allowed values not null.
	 */
	public EntitySearchFilter(String key, boolean isAttributeFilter, List<Object> allowedValues, boolean useLikeOption) {
		this(key, isAttributeFilter);
		this.setAllowedValues(allowedValues);
		this.setLikeOption(useLikeOption);
	}
	
	/**
	 * This method shows if the filter must be applied on a Entity Attribute or
	 * a metadata.
	 * @return true if the filter is to be applied to an attribute entity or a 
	 * to a metadata of the an entity.
	 */
	public boolean isAttributeFilter() {
		return _attributeFilter;
	}
	protected void setAttributeFilter(boolean attributeFilter) {
		this._attributeFilter = attributeFilter;
	}
	
	public String getLangCode() {
		return this._langCode;
	}
	
	public void setLangCode(String langCode) {
		if (null == langCode) return;
		if (!this.isAttributeFilter()) {
			throw new RuntimeException("Error: The language can be only specified on attribute filters");
		}
		if ((null != this.getValue() && !(this.getValue() instanceof String)) 
				|| (null != this.getStart() &&  !(this.getStart() instanceof String)) 
				|| (null != this.getEnd() &&  !(this.getEnd() instanceof String)) 
				|| (this.getAllowedValues() != null && !this.getAllowedValues().isEmpty() && !(this.getAllowedValues().get(0) instanceof String))) {
			throw new RuntimeException("Error: The language can be only specified on a null value of type string");
		}
		this._langCode = langCode;
	}
	
	@Override
	public String toString() {
		StringBuffer param = new StringBuffer();
		param.append(KEY_PARAM).append("=").append(this.getKey()).append(SEPARATOR);
		param.append(FILTER_TYPE_PARAM).append("=").append(Boolean.toString(this.isAttributeFilter()));
		this.appendParamValue(param, this.getValue(), VALUE_PARAM);
		this.appendParamValue(param, this.getOrder(), ORDER_PARAM);
		if (this.getValue() instanceof String) {
			this.appendParamValue(param, this.isLikeOption(), LIKE_OPTION_PARAM);
		}
		if (null != this.getAllowedValues() && !this.getAllowedValues().isEmpty()) {
			this.appendParamValue(param, this.getAllowedValues(), ALLOWED_VALUES_PARAM);
		}
		this.appendParamValue(param, this.getLangCode(), LANG_PARAM);
		this.appendParamValue(param, this.getStart(), START_PARAM);
		this.appendParamValue(param, this.getEnd(), END_PARAM);
		this.appendParamValue(param, this.getOrder(), ORDER_PARAM);
		return param.toString();
	}
	
	private void appendParamValue(StringBuffer param, Object value, String paramValue) {
		if (null != value) {
			param.append(SEPARATOR).append(paramValue).append("=");
			if (value instanceof List) {
				List<Object> values = (List<Object>) value;
				for (int i = 0; i < values.size() ; i++) {
					if (i>0) param.append(ALLOWED_VALUES_SEPARATOR);
					param.append(values.get(i));
				}
			} else {
				param.append(this.getToStringValue(value));
			}
		}
	}
	
	private String getToStringValue(Object value) {
		if (value instanceof String) {
			return value.toString();
		} else if (value instanceof Date) {
			return DateConverter.getFormattedDate((Date)value, DATE_PATTERN);
		} else if (value instanceof BigDecimal) {
			value = ((BigDecimal)value).toString();
		} else if (value instanceof Boolean) {
			value = ((Boolean)value).toString();
		}
		return value.toString();
	}
	
	@Deprecated
	public static EntitySearchFilter getInstance(IApsEntity prototype, String toStringFilter) {
		return getInstance(prototype, getProperties(toStringFilter));
	}
	
	@Deprecated
	public static Properties getProperties(String toStringFilter) {
		Properties props = new Properties();
		String[] params = toStringFilter.split(SEPARATOR);
		for (int i=0; i<params.length; i++) {
			String[] elements = params[i].split("=");
			if (elements.length != 2) continue;
			props.setProperty(elements[0], elements[1]);
		}
		return props;
	}
	
	public static EntitySearchFilter getInstance(IApsEntity prototype, Properties props) {
		EntitySearchFilter filter = null;
		try {
			String key = props.getProperty(KEY_PARAM);
			if (null == key) return null;
			boolean isAttributeFilter = Boolean.parseBoolean(props.getProperty(FILTER_TYPE_PARAM));
			filter = new EntitySearchFilter(key, isAttributeFilter);
			if (!isAttributeFilter) {
				String dataType = props.getProperty(DATA_TYPE_PARAM);
				if (null == dataType) dataType = DATA_TYPE_STRING;
				setValues(filter, props, dataType);
			} else {
				AttributeInterface attr = (AttributeInterface) prototype.getAttribute(key);
				if (null != attr && null != prototype) {
					String dataType = null;
					if (attr instanceof DateAttribute) {
						dataType = DATA_TYPE_DATE;
					} else if (attr instanceof ITextAttribute || attr instanceof BooleanAttribute) {
						dataType = DATA_TYPE_STRING;
					} else if (attr instanceof NumberAttribute) {
						dataType = DATA_TYPE_NUMBER;
					}
					setValues(filter, props, dataType);
				} else throw new ApsSystemException("ERROR: Entity type '" + prototype.getTypeCode() 
						+ "' and attribute '" + key + "' not recognized");
			}
			String order = props.getProperty(EntitySearchFilter.ORDER_PARAM);
			filter.setOrder(order);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, EntitySearchFilter.class, "Error on creation of filter instance");
			throw new RuntimeException("Error on creation of filter instance", t);
		}
		return filter;
	}
	
	private static void setValues(EntitySearchFilter filter, Properties props, String dataType) {
		if (null == dataType) return;
		String value = props.getProperty(VALUE_PARAM);
		String allowedValues = props.getProperty(ALLOWED_VALUES_PARAM);
		String start = props.getProperty(START_PARAM);
		String end = props.getProperty(END_PARAM);
		Object objectValue = getDataObject(value, dataType);
		List<Object> objectAllowedValues = buildAllowedValues(allowedValues, dataType);
		Object objectStart = getDataObject(start, dataType);
		Object objectEnd = getDataObject(end, dataType);
		String likeOptionString = props.getProperty(LIKE_OPTION_PARAM);
		boolean likeOption = (null != likeOptionString) ? Boolean.parseBoolean(likeOptionString) : false;
		if (objectValue != null) {
			filter.setValue(objectValue);
			filter.setLikeOption(likeOption);
		} else if (objectAllowedValues != null) {
			filter.setAllowedValues(objectAllowedValues);
			filter.setLikeOption(likeOption);
		} else {
			filter.setStart(objectStart);
			filter.setEnd(objectEnd);
		}
		String langCode = props.getProperty(LANG_PARAM);
		filter.setLangCode(langCode);
	}

	private static List<Object> buildAllowedValues(String allowedValues, String dataType) {
		if (null == allowedValues) return null;
		List<Object> values = null;
		String[] stringValues = allowedValues.split(ALLOWED_VALUES_SEPARATOR);
		if (null != stringValues && stringValues.length > 0) {
			values = new ArrayList<Object>();
			for (int i = 0; i < stringValues.length; i++) {
				String stringValue = stringValues[i];
				Object object = getDataObject(stringValue, dataType);
				if (null != object) {
					values.add(object);
				}
			}
		}
		if (null == values || values.size() == 0) return null;
		return values;
	}
	
	private static Object getDataObject(String stringValue, String dataType) {
		if (null == stringValue) return null;
		Object object = null;
		if (dataType.equals(DATA_TYPE_DATE)) {
			object = buildDate(stringValue);
		} else if (dataType.equals(DATA_TYPE_STRING)) {
			object = stringValue;
		} else if (dataType.equals(DATA_TYPE_NUMBER)) {
			object = buildNumber(stringValue);
		}
		return object;
	}
	
	private static Date buildDate(String dateString) {
		String today = "today, oggi, odierna";
		Date data = null;
		try {
			if (today.indexOf(dateString) != -1) {
				data = new java.util.Date();
			} else {
				SimpleDateFormat dataF = new SimpleDateFormat(EntitySearchFilter.DATE_PATTERN);
				data = dataF.parse(dateString);
			}
		} catch (ParseException ex) {
			ApsSystemUtils.getLogger().severe("Invalid string - '" + dateString + "'");
		}
		return data;
	}
	
	private static BigDecimal buildNumber(String numberString) {
		BigDecimal number = null;
		try {
			number = new BigDecimal(numberString);
		} catch (NumberFormatException e) {
			ApsSystemUtils.getLogger().severe("Invalid string - '" + numberString + "'");
		}
		return number;
	}
	
	/**
	 * @uml.property  name="_attributeFilter"
	 */
	private boolean _attributeFilter;
	
	/**
	 * @uml.property  name="_langCode"
	 */
	private String _langCode;
	
	public static final String SEPARATOR = ";";
	public static final String KEY_PARAM = "key";
	public static final String FILTER_TYPE_PARAM = "attributeFilter";
	public static final String VALUE_PARAM = "value";
	public static final String ALLOWED_VALUES_PARAM = "allowedValues";
	public static final String ALLOWED_VALUES_SEPARATOR = ",";
	public static final String LIKE_OPTION_PARAM = "likeOption";
	public static final String LANG_PARAM = "lang";
	public static final String START_PARAM = "start";
	public static final String END_PARAM = "end";
	public static final String ORDER_PARAM = "order";
	public static final String DATA_TYPE_PARAM = "dataType";
	
	public static final String DATA_TYPE_STRING = "string";
	public static final String DATA_TYPE_DATE = "date";
	public static final String DATA_TYPE_NUMBER = "number";
	
	public static final String DATE_PATTERN = "dd/MM/yyyy";
	
}