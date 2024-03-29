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
import com.agiletec.aps.util.ApsWebApplicationUtils;
import com.agiletec.plugins.jacms.aps.system.JacmsSystemConstants;
import com.agiletec.plugins.jacms.aps.system.services.content.showlet.IContentListBean;
import com.agiletec.plugins.jacms.aps.system.services.content.showlet.IContentListFilterBean;
import com.agiletec.plugins.jacms.aps.system.services.content.showlet.IContentListHelper;
import com.agiletec.plugins.jacms.aps.system.services.content.showlet.UserFilterOptionBean;

/**
 * ContentListTag" sub-tag, it creates a custom user filter to restrict the result of the content search by front-end user.
 * @author E.Santoboni
 */
public class ContentListUserFilterOptionTag extends TagSupport implements IContentListFilterBean {
	
	public ContentListUserFilterOptionTag() {
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
				for (int i=0; i < IContentListHelper.allowedMetadataUserFilterOptionKeys.length; i++) {
					if (i!=0) message.concat(",");
					message.concat(IContentListHelper.allowedMetadataUserFilterOptionKeys[i]);
				}
				throw new RuntimeException("The key '" + this.getKey() + "' is unknown; " +
						"Please use a valid one - " + message);
			}
			IContentListHelper helper = (IContentListHelper) ApsWebApplicationUtils.getBean(JacmsSystemConstants.CONTENT_LIST_HELPER, this.pageContext);
			IContentListBean parent = (IContentListBean) findAncestorWithClass(this, IContentListBean.class);
			String contentType = parent.getContentType();
			UserFilterOptionBean filter = helper.getUserFilterOption(contentType, this, reqCtx);
			if (null != filter) {
				parent.addUserFilterOption(filter);
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
			for (int i = 0; i < IContentListHelper.allowedMetadataUserFilterOptionKeys.length; i++) {
				if (IContentListHelper.allowedMetadataFilterKeys[i].equals(this.getKey())) return true;
			}
		}
		return false;
	}
	
	@Override
	public void release() {
		this._key = null;
		this._attributeFilter = false;
	}
	
	/**
	 * Return the key of the filter.
	 * This string can be:<br/>
	 * - the name of a content attribute compatible with the type declared in the "contentListTag" (it requires the "attributeFilter" attribute to be "true")<br/>
	 * - the ID of one of the content metadata (the "attributeFilter" must be false)<br/>
	 * The allowed filter key that can be applied to content metadata are:<br/>
	 * - "fulltext" allows filter by full-text search<br />
	 * - "category" allows filter by a system category.
	 * @return The key of the filter.
	 */
	@Override
	public String getKey() {
		return _key;
	}
	
	/**
	 * Set the key of the filter.
	 * This string can be:<br/>
	 * - the name of a content attribute compatible with the type declared in the "contentListTag" (it requires the "attributeFilter" attribute to be "true")<br/>
	 * - the ID of one of the content metadata (the "attributeFilter" must be false)<br/>
	 * The allowed filter key that can be applied to content metadata are:<br/>
	 * - "fulltext" allows filter by full-text search<br />
	 * - "category" allows filter by a system category.
	 * @param key The key to set
	 */
	public void setKey(String key) {
		this._key = key;
	}
	
	/**
	 * Checks whether the filter must be applied to an attribute or to a content metadata
	 * @return true if the filter must be applied to an attribute.
	 */
	@Override
	public boolean isAttributeFilter() {
		return _attributeFilter;
	}
	
	/**
	 * Decides whether the filter must be applied to an attribute or to a
	 * content metadata, admitted values are (true|false).<br/>
	 * The "key" attribute will be checked for validity if the filter is going to be applied to a metadata
	 * @param attributeFilter true if the filter must be applied to an attribute.
	 */
	public void setAttributeFilter(boolean attributeFilter) {
		this._attributeFilter = attributeFilter;
	}
	
	@Override
	public String getEnd() {return null;}
	@Override
	public boolean getLikeOption() {return false;}
	@Override
	public String getOrder() {return null;}
	@Override
	public String getStart() {return null;}
	@Override
	public String getValue() {return null;}
	
	/**
	 * @uml.property  name="_key"
	 */
	private String _key;
	/**
	 * @uml.property  name="_attributeFilter"
	 */
	private boolean _attributeFilter;
	
}
