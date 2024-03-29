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
package com.agiletec.plugins.jacms.aps.tags;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.RequestContext;
import com.agiletec.aps.system.common.entity.model.EntitySearchFilter;
import com.agiletec.aps.util.ApsWebApplicationUtils;
import com.agiletec.plugins.jacms.aps.system.JacmsSystemConstants;
import com.agiletec.plugins.jacms.aps.system.services.content.showlet.IContentListBean;
import com.agiletec.plugins.jacms.aps.system.services.content.showlet.IContentListFilterBean;
import com.agiletec.plugins.jacms.aps.system.services.content.showlet.IContentListHelper;

/**
 * ContentListTag" sub-tag, it creates a filter to restrict the result of the content search.
 * Please note that the filters will be applied in the same order they are
 * declared and the result of the search will reflect this fact.
 * @author E.Santoboni
 */
public class ContentListFilterTag extends TagSupport implements IContentListFilterBean {
	
	public ContentListFilterTag() {
		super();
		this.release();
	}
	
	@Override
	public int doEndTag() throws JspException {
		ServletRequest request =  this.pageContext.getRequest();
		RequestContext reqCtx = (RequestContext) request.getAttribute(RequestContext.REQCTX);
		try {
			if (!this.isRightKey()) {
				String message = "";
				for (int i=0; i < IContentListHelper.allowedMetadataFilterKeys.length; i++) {
					if (i!=0) message.concat(",");
					message.concat(IContentListHelper.allowedMetadataFilterKeys[i]);
				}
				throw new RuntimeException("The key '" + this.getKey() + "' is unknown; " +
						"Please use a valid one - " + message);
			}
			IContentListHelper helper = (IContentListHelper) ApsWebApplicationUtils.getBean(JacmsSystemConstants.CONTENT_LIST_HELPER, this.pageContext);
			IContentListBean parent = (IContentListBean) findAncestorWithClass(this, IContentListBean.class);
			String contentType = parent.getContentType();
			EntitySearchFilter filter = helper.getFilter(contentType, this, reqCtx);
			if (null != filter) {
				parent.addFilter(filter);
			}
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "doEndTag");
			throw new JspException("Tag error detected ", t);
		}
		return super.doEndTag();
	}
	
	private boolean isRightKey() {
		if (this.isAttributeFilter()) {
			return true;
		} else {
			for (int i = 0; i < IContentListHelper.allowedMetadataFilterKeys.length; i++) {
				if (IContentListHelper.allowedMetadataFilterKeys[i].equals(this.getKey())) return true;
			}
		}
		return false;
	}
	
	@Override
	public void release() {
		this._key = null;
		this._attributeFilter = false;
		this._value = null;
		this._order = null;
		this._start = null;
		this._end = null;
		this._likeOption = false;
	}
	
	/**
	 * Get the string used to filter and sort the contents.
	 */
	@Override
	public String getKey() {
		return _key;
	}
	
	/**
	 * Set the string used to filter and sort the contents.
	 *	This string can be:<br/>
	 *	 - the name of a content attribute compatible with the type declared in the "contentListTag" (it requires the "attributeFilter" attribute to be "true")<br/>
	 *	 - the ID of one of the content metadata (the "attributeFilter" must be false)<br/>
	 *	The allowed filter key that can be applied to content metadata are:<br/>
	 *	 - "created" allows sorting by date of creation of content
	 *	 - "modified" allows sorting by date of modification of content.
	 * @param key
	 */
	public void setKey(String key) {
		this._key = key;
	}
	
	/**
	 * Checks whether the filter must be applied to an attribute or to a
	 * content metadata
	 */
	@Override
	public boolean isAttributeFilter() {
		return _attributeFilter;
	}
	/**
	 * Decides whether the filter must be applied to an attribute or to a
	 * content metadata, admitted values are (true|false).<br/>
	 * The "key" attribute will be checked for validity if the filter is going
	 * to be applied to a metadata
	 * @param attributeFilter 
	 */
	public void setAttributeFilter(boolean attributeFilter) {
		this._attributeFilter = attributeFilter;
	}
	/**
	 * Get the filtering value
	 */
	@Override
	public String getValue() {
		return _value;
	}
	/**
	 * Set the value of the filter to apply
	 * @param value the value if the filter
	 */
	public void setValue(String value) {
		this._value = value;
	}
	
	/**
	 * Return the lowest, to restrict results from, limit of the filter.
	 */
	@Override
	public String getStart() {
		return _start;
	}
	/**
	 * Set the filter to restrict the result to the contents by attribute type or by the field specified 
	 * with the key (<b>respect</b> the following matches):<br/>
	 *	 <b>Text</b> field --> <b>start Text</b><br/>
	 *	 <b>Text</b> attribute type --> <b>start Text</b><br/>
	 *	 <b>Numeric</b> attribute type --> <b>start Numeric</b><br/>
	 *	 <b>date</b> attribute type --> <b>start data</b><br/>
	 *	If the data filter is used:<br>
	 *	 - <b>today</b>, <b>oggi</b> or <b>odierna</b> will select all the contents with a date greater
	 *	or equal to the system date<br/>
	 *	 - using date with the pattern "dd/MM/yyyy" will select all the contents
	 *	with a date greater or equal to the one inserted.
	 * @param start
	 */
	public void setStart(String start) {
		this._start = start;
	}
	
	/**
	 * Return the upper limit of the filter
	 */
	@Override
	public String getEnd() {
		return _end;
	}
	/**
	 * Similar the the "start" attribute but with the opposite behaviour it
	 * sets the upper limit of the filter
	 * @param end
	 */
	public void setEnd(String end) {
		this._end = end;
	}
	
	/**
	 * Get the order to apply to the result. It can be null.
	 */
	@Override
	public String getOrder() {
		return _order;
	}
	/**
	 * Specifies the sorting behaviour of the IDs found: "ASC"ending or "DESC"ending.
	 * By default no ordering is performed.
	 * @param order The (ASC|DESC) string
	 */
	public void setOrder(String order) {
		this._order = order;
	}
	@Override
	public boolean getLikeOption() {
		return this._likeOption;
	}
	
	/**
	 * Toggles the 'like' functionality. 
	 * The option is available for metadata and on Text Content attributes.
	 * @param likeOption Admitted values: (true|false). Default: false.
	 */
	public void setLikeOption(boolean likeOption) {
		this._likeOption = likeOption;
	}
	
	/**
	 * @uml.property  name="_key"
	 */
	private String _key;
	/**
	 * @uml.property  name="_attributeFilter"
	 */
	private boolean _attributeFilter;
	/**
	 * @uml.property  name="_value"
	 */
	private String _value;
	/**
	 * @uml.property  name="_start"
	 */
	private String _start;
	/**
	 * @uml.property  name="_end"
	 */
	private String _end;
	/**
	 * @uml.property  name="_order"
	 */
	private String _order;
	/**
	 * @uml.property  name="_likeOption"
	 */
	private boolean _likeOption;
	
}
