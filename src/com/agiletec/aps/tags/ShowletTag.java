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

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.RequestContext;
import com.agiletec.aps.system.SystemConstants;
import com.agiletec.aps.system.services.page.IPage;

/**
 * Includes the jsp associated to the showlet as configured in the page frame
 * 
 */
public class ShowletTag extends TagSupport {

	public int doEndTag() throws JspException {
		ServletRequest req =  this.pageContext.getRequest();
		RequestContext reqCtx = (RequestContext) req.getAttribute(RequestContext.REQCTX);
		IPage page = (IPage) reqCtx.getExtraParam(SystemConstants.EXTRAPAR_CURRENT_PAGE);
		try {
			String showletOutput[] = (String[]) reqCtx.getExtraParam("ShowletOutput");
			if(_frame <0 || _frame >= showletOutput.length){
				String msg = "Frame attribute =" + _frame + " exceeds the limit in the page " + page.getCode();
				ApsSystemUtils.getLogger().severe(msg);
				throw new JspException(msg);
			}
			String showlet = showletOutput[_frame];
			if (null == showlet) 
				showlet = "";
			this.pageContext.getOut().print(showlet);
		} catch (Throwable t) {
			String msg = "Error detected in the inclusion of the output showlet";
			ApsSystemUtils.logThrowable(t, this, "doEndTag", msg);
			throw new JspException(msg, t);
		}
		
		return EVAL_PAGE;
	}
	
	/**
	 * Return the frame attribute
	 * @return The positional frame number
	 */
	public int getFrame() {
		return _frame;
	}

	/**
	 * Set the frame attribute
	 * @param frame The positional frame number
	 */
	public void setFrame(int frame) {
		this._frame = frame;
	}

	/**
	 * @uml.property  name="_frame"
	 */
	private int _frame;
}
