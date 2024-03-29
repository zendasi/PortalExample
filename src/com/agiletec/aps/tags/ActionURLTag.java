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
package com.agiletec.aps.tags;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.RequestContext;
import com.agiletec.aps.system.SystemConstants;
import com.agiletec.aps.system.services.page.IPage;
import com.agiletec.aps.system.services.url.IURLManager;
import com.agiletec.aps.system.services.url.PageURL;
import com.agiletec.aps.tags.util.IParameterParentTag;
import com.agiletec.aps.util.ApsWebApplicationUtils;

/**
 * Build the URL to call a functionality of a servlet defined within the system.
 * This tag can use subtag ParameterTag to add url parameters.
 * The parameter 'path' must contain a path relative to the context of web-application
 * (es.: /do/myAction or /WEB-INF/jsp/myJSP.jsp)
 * @author M.Casari - E.Santoboni
 */
public class ActionURLTag extends TagSupport implements IParameterParentTag {
	
	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}
	
	@Override
	public int doEndTag() throws JspException {	
		ServletRequest request =  this.pageContext.getRequest();
		RequestContext reqCtx = (RequestContext) request.getAttribute(RequestContext.REQCTX);
		IURLManager urlManager = (IURLManager) ApsWebApplicationUtils.getBean(SystemConstants.URL_MANAGER, this.pageContext);
		try {
			PageURL pageUrl = urlManager.createURL(reqCtx);
			IPage currPage = (IPage) reqCtx.getExtraParam(SystemConstants.EXTRAPAR_CURRENT_PAGE);
			Integer currentFrame = (Integer) reqCtx.getExtraParam(SystemConstants.EXTRAPAR_CURRENT_FRAME);
			pageUrl.setPage(currPage);
			pageUrl.addParam(InternalServletTag.REQUEST_PARAM_ACTIONPATH, this.getPath());
			pageUrl.addParam(InternalServletTag.REQUEST_PARAM_FRAMEDEST, currentFrame.toString());
			if (null != this.getParameters()) {
				Iterator<String> iter = this.getParameters().keySet().iterator();
				while (iter.hasNext()) {
					String name = (String) iter.next();
					pageUrl.addParam(name, this.getParameters().get(name));
				}
			}
			this.pageContext.getOut().print(pageUrl.getURL());
		} catch (IOException e) {
			ApsSystemUtils.logThrowable(e, this, "doEndTag");
			throw new JspException("Error closing tag ", e);
		}
		this.release();
		return EVAL_PAGE;
	}
	
	@Override
	public void release() {
		this._path = null;
		this._parameters = null;
	}
	
	/**
	 * Return the path related to the action or the page to invoke.
	 * @return The requested path
	 */
	public String getPath() {
		return _path;
	}

	/**
	 * Set the path related to the action or the page to invoke.
	 * @param path The path to invoke
	 */
	public void setPath(String path) {
		this._path = path;
	}
	
	public Map<String, String> getParameters() {
		return this._parameters;
	}
	
	@Override
	public void addParameter(String name, String value) {
		if (null == this._parameters) {
			this._parameters = new HashMap<String, String>();
		}
		this._parameters.put(name, value);
	}
	
	/**
	 * @uml.property  name="_path"
	 */
	private String _path;
	
	/**
	 * @uml.property  name="_parameters"
	 * @uml.associationEnd  qualifier="name:java.lang.String java.lang.String"
	 */
	private Map<String, String> _parameters;
	
}
