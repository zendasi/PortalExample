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
package com.agiletec.aps.system.common;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Utility Class for searching operation on db. This class presents utility method for searching on db table throw  Field search filter.
 * @author  E.Santoboni
 */
public abstract class AbstractSearcherDAO extends AbstractDAO {
	
	protected List<String> searchId(FieldSearchFilter[] filters) {
		Connection conn = null;
		List<String> idList = new ArrayList<String>();
		PreparedStatement stat = null;
		ResultSet result = null;
		try {
			conn = this.getConnection();
			stat = this.buildStatement(filters, false, conn);
			result = stat.executeQuery();
			this.flowResult(idList, filters, result);
		} catch (Throwable t) {
			processDaoException(t, "Error while loading the list of IDs", "searchId");
		} finally {
			closeDaoResources(result, stat, conn);
		}
		return idList;
	}
	
	protected FieldSearchFilter[] addFilter(FieldSearchFilter[] filters, FieldSearchFilter filterToAdd){
		int len = 0;
		if (filters != null) {
			len = filters.length;
		}
		FieldSearchFilter[] newFilters = new FieldSearchFilter[len + 1];
		for(int i=0; i < len; i++){
			newFilters[i] = filters[i];
		}
		newFilters[len] = filterToAdd;
		return newFilters;
	}
	
	protected void flowResult(List<String> contentsId, FieldSearchFilter[] filters, ResultSet result) throws SQLException {
		while (result.next()) {
			String id = result.getString(this.getMasterTableIdFieldName());
			if (contentsId.contains(id)) continue;
			if (!this.isForceTextCaseSearch() || null == filters || filters.length == 0) {
				contentsId.add(id);
			} else {
				boolean verify = this.verifyLikeFieldFilters(result, filters);
				if (verify) contentsId.add(id);
			}
		}
	}
	
	protected boolean verifyLikeFieldFilters(ResultSet result,
			FieldSearchFilter[] likeFieldFilters) throws SQLException {
		boolean verify = true;
		for (int i=0; i<likeFieldFilters.length; i++) {
			FieldSearchFilter filter = likeFieldFilters[i];
			if (filter.getKey() == null || !filter.isLikeOption() || !this.isForceTextCaseSearch()) {
				continue;
			}
			String fieldName = this.getTableFieldName(filter.getKey());
			String value = result.getString(fieldName);
			if (null != filter.getValue()) {
				verify = this.checkText((String)filter.getValue(), value);
				if (!verify) break;
			} else if (filter.getAllowedValues() != null && filter.getAllowedValues().size() > 0) {
				List<Object> allowedValues = filter.getAllowedValues();
				verify = this.verifyLikeAllowedValuesFilter(value, allowedValues);
				if (!verify) break;
			}
		}
		return verify;
	}
	
	protected boolean verifyLikeAllowedValuesFilter(String extractedValue, List<Object> allowedValues) {
		boolean verify = false;
		for (int j = 0; j < allowedValues.size(); j++) {
			String allowedValue = (String) allowedValues.get(j);
			verify = this.checkText(allowedValue, extractedValue);
			if (verify) break;
		}
		return verify;
	}
	
	/**
	 * This utility method checks if the given Text matches or is contained inside
	 * another one.
	 * @param insertedText The text to look for
	 * @param text The text to search in
	 * @return True if an occurrence of 'insertedText' is found in 'text'.
	 */
	protected boolean checkText(String insertedText, String text) {
		if (this.isForceCaseInsensitiveLikeSearch() 
				&& (null == insertedText || insertedText.trim().length() == 0 
						|| (null != text && text.toLowerCase().indexOf(insertedText.trim().toLowerCase()) != -1))) {
			return true;
		}
		if (this.isForceCaseSensitiveLikeSearch() 
				&& (null == insertedText || insertedText.trim().length() == 0 
						|| (null != text && text.indexOf(insertedText.trim()) != -1))) {
			return true;
		}
		return false;
	}
	
	private PreparedStatement buildStatement(FieldSearchFilter[] filters, boolean selectAll, Connection conn) {
		String query = this.createQueryString(filters, selectAll);
		PreparedStatement stat = null;
		try {
			stat = conn.prepareStatement(query);
			int index = 0;
			index = this.addMetadataFieldFilterStatementBlock(filters, index, stat);
		} catch (Throwable t) {
			processDaoException(t, "Error while creating the statement", "buildStatement");
		}
		return stat;
	}
	
	/**
	 * Add to the statement the filters on the entity metadata.
	 * @param filters the filters to add to the statement.
	 * @param index The current index of the statement.
	 * @param stat The statement.
	 * @return The current statement index, eventually incremented by filters.
	 * @throws Throwable In case of error.
	 */
	protected int addMetadataFieldFilterStatementBlock(FieldSearchFilter[] filters, int index, PreparedStatement stat) throws Throwable {
		if (filters == null) return index;
		for (int i=0; i<filters.length; i++) {
			FieldSearchFilter filter = filters[i];
			if (filter.getKey() != null) {
				index = this.addObjectSearchStatementBlock(filter, index, stat);
			}
		}
		return index;
	}
	
	/**
	 * Add to the statement a filter on a attribute.
	 * @param filter The filter on the attribute to apply in the statement.
	 * @param index The last index used to associate the elements to the statement.
	 * @param stat The statement where the filters are applied.
	 * @return The last used index.
	 * @throws SQLException In case of error.
	 */
	protected int addObjectSearchStatementBlock(FieldSearchFilter filter, int index, PreparedStatement stat) throws SQLException {
		if (filter.isLikeOption() && this.isForceTextCaseSearch()) {
			return index;
		}
		if (filter.isNullOption()) {
			return index;
		}
		if (filter.getAllowedValues() != null && filter.getAllowedValues().size() > 0) {
			List<Object> allowedValues = filter.getAllowedValues();
			for (int i = 0; i < allowedValues.size(); i++) {
				Object allowedValue = allowedValues.get(i);
				this.addObjectSearchStatementBlock(stat, ++index, allowedValue, filter.isLikeOption());
			}
		} else if (filter.getValue() != null) {
			this.addObjectSearchStatementBlock(stat, ++index, filter.getValue(), filter.isLikeOption());
		} else {
			if (null != filter.getStart()) {
				this.addObjectSearchStatementBlock(stat, ++index, filter.getStart(), false);
			}
			if (null != filter.getEnd()) {
				this.addObjectSearchStatementBlock(stat, ++index, filter.getEnd(), false);
			}
		}
		return index;
	}
	
	protected void addObjectSearchStatementBlock(PreparedStatement stat, int index,
			Object object, boolean isLikeOption) throws SQLException {
		if (object instanceof String) {
			if (isLikeOption) {
				stat.setString(index, "%"+((String) object)+"%");
			} else {
				stat.setString(index, (String) object);
			}
		} else if (object instanceof Date) {
			stat.setDate(index, new java.sql.Date(((Date) object).getTime()));
		} else if (object instanceof BigDecimal) {
			stat.setBigDecimal(index, (BigDecimal) object);
		} else if (object instanceof Boolean) {
			stat.setString(index, ((Boolean) object).toString());
		} else {
			stat.setObject(index, object);
		}
	}
	
	private String createQueryString(FieldSearchFilter[] filters, boolean selectAll) {
		StringBuffer query = this.createBaseQueryBlock(filters, selectAll);
		boolean hasAppendWhereClause = this.appendMetadataFieldFilterQueryBlocks(filters, query, false);
		boolean ordered = appendOrderQueryBlocks(filters, query, false);
		return query.toString();
	}
	
	/**
	 * Create the 'base block' of the query with the eventual references to the support table.
	 * @param filters The filters defined.
	 * @param selectAll When true, this will insert all the fields in the master table in the select 
	 * of the master query.
	 * When true we select all the available fields; when false only the field addressed by the filter
	 * is selected.
	 * @return The base block of the query.
	 */
	protected StringBuffer createBaseQueryBlock(FieldSearchFilter[] filters, boolean selectAll) {
		StringBuffer query = this.createMasterSelectQueryBlock(filters, selectAll);
		return query;
	}
	
	private StringBuffer createMasterSelectQueryBlock(FieldSearchFilter[] filters, boolean selectAll) {
		String masterTableName = this.getMasterTableName();
		StringBuffer query = new StringBuffer("SELECT ").append(masterTableName).append(".");
		if (selectAll) {
			query.append("* ");
		} else {
			query.append(this.getMasterTableIdFieldName());
			if (this.isForceTextCaseSearch() && filters != null) {
				for (int i=0; i<filters.length; i++) {
					FieldSearchFilter filter = filters[i];
					if (filter.isLikeOption()) {
						query.append(", ").append(masterTableName).append(".").append(this.getTableFieldName(filters[i].getKey()));
					}
				}
			}
		}
		query.append(" FROM ").append(masterTableName).append(" ");
		return query;
	}
	
	protected boolean appendMetadataFieldFilterQueryBlocks(FieldSearchFilter[] filters, StringBuffer query, boolean hasAppendWhereClause) {
		if (filters == null) return hasAppendWhereClause;
		for (int i=0; i<filters.length; i++) {
			FieldSearchFilter filter = filters[i];
			if (filter.getKey() != null) {
				hasAppendWhereClause = this.addMetadataFieldFilterQueryBlock(filter, query, hasAppendWhereClause);
			}
		}
		return hasAppendWhereClause;
	}
	
	protected boolean addMetadataFieldFilterQueryBlock(FieldSearchFilter filter, StringBuffer query, boolean hasAppendWhereClause) {
		if (filter.isLikeOption() && this.isForceTextCaseSearch()) {
			return hasAppendWhereClause;
		}
		hasAppendWhereClause = this.verifyWhereClauseAppend(query, hasAppendWhereClause);
		String tableFieldName = this.getTableFieldName(filter.getKey());
		if (filter.getAllowedValues() != null && filter.getAllowedValues().size() > 0) {
			List<Object> allowedValues = filter.getAllowedValues();
			for (int j = 0; j < allowedValues.size(); j++) {
				if (j == 0) {
					query.append(" ( ");
				} else {
					query.append(" OR ");
				}
				query.append(this.getMasterTableName()).append(".").append(tableFieldName).append(" ");
				if (filter.isLikeOption()) {
					query.append(this.getLikeClause());
				} else {
					query.append("= ? ");
				}
				if (j == (allowedValues.size()-1)) query.append(" ) ");
			}
		} else {
			query.append(this.getMasterTableName()).append(".").append(tableFieldName).append(" ");
			if (filter.getValue() != null) {
				if (filter.isLikeOption()) {
					query.append(this.getLikeClause());
				} else {
					query.append("= ? ");
				}
			} else {
				if (null != filter.getStart()) {
					query.append(">= ? ");
					if (null != filter.getEnd()) {
						query.append("AND ").append(this.getMasterTableName()).append(".").append(tableFieldName).append(" ");
						query.append("<= ? ");
					}
				} else if (null != filter.getEnd()) {
					query.append("<= ? ");
				} else {
					if (filter.isNullOption()) {
						query.append(" IS NULL ");
					} else {
						query.append(" IS NOT NULL ");
					}
				}
			}
		}
		return hasAppendWhereClause;
	}
	
	protected boolean appendOrderQueryBlocks(FieldSearchFilter[] filters, StringBuffer query, boolean ordered) {
		if (filters == null) return ordered;
		for (int i=0; i<filters.length; i++) {
			FieldSearchFilter filter = filters[i];
			if (null != filter.getKey() && null != filter.getOrder() && !filter.isNullOption()) {
				if (!ordered) {
					query.append("ORDER BY ");
					ordered = true;
				} else {
					query.append(", ");
				}
				String fieldName = this.getTableFieldName(filter.getKey());
				query.append(this.getMasterTableName()).append(".").append(fieldName).append(" ").append(filter.getOrder());
			}
		}
		return ordered;
	}
	
	protected boolean verifyWhereClauseAppend(StringBuffer query, boolean hasAppendWhereClause) {
		if (hasAppendWhereClause) {
			query.append("AND ");
		} else {
			query.append("WHERE ");
			hasAppendWhereClause = true;
		}
		return hasAppendWhereClause;
	}
	
	protected abstract String getTableFieldName(String metadataFieldKey);
	
	/**
	 * Return the name of the entities master table.
	 * @return  The name of the master table.
	 * @uml.property  name="masterTableName"
	 */
	protected abstract String getMasterTableName();
	
	/**
	 * Return the name of the ID field in the master table.
	 * @return  The name of the ID field.
	 * @uml.property  name="masterTableIdFieldName"
	 */
	protected abstract String getMasterTableIdFieldName();
	
	protected String getLikeClause() {
		if (null == this._likeClause || this._likeClause.trim().length() == 0) {
			return DEFAULT_LIKE_CLAUSE;
		}
		return _likeClause;
	}
	public void setLikeClause(String likeClause) {
		this._likeClause = likeClause;
	}
	
	protected boolean isForceTextCaseSearch() {
		return (this.isForceCaseInsensitiveLikeSearch() || this.isForceCaseSensitiveLikeSearch());
	}
	
	protected boolean isForceCaseSensitiveLikeSearch() {
		return _forceCaseSensitiveLikeSearch;
	}
	public void setForceCaseSensitiveLikeSearch(boolean forceCaseSensitiveLikeSearch) {
		this._forceCaseSensitiveLikeSearch = forceCaseSensitiveLikeSearch;
	}
	
	protected boolean isForceCaseInsensitiveLikeSearch() {
		return _forceCaseInsensitiveLikeSearch;
	}
	public void setForceCaseInsensitiveLikeSearch(boolean forceCaseInsensitiveLikeSearch) {
		this._forceCaseInsensitiveLikeSearch = forceCaseInsensitiveLikeSearch;
	}
	
	/**
	 * @uml.property  name="_likeClause"
	 */
	private String _likeClause;
	private static final String DEFAULT_LIKE_CLAUSE = "LIKE ? ";
	/**
	 * @uml.property  name="_forceCaseSensitiveLikeSearch"
	 */
	private boolean _forceCaseSensitiveLikeSearch = false;
	/**
	 * @uml.property  name="_forceCaseInsensitiveLikeSearch"
	 */
	private boolean _forceCaseInsensitiveLikeSearch = false;
	
}