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
package com.agiletec.apsadmin.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.apsadmin.tags.util.AutoIndexingTagHelper;
import com.agiletec.apsadmin.tags.util.IAutoIndexingTag;

/**
 * Print the current value of the counter.
 * The counter should be used inside the tabindex attribute in HTML tags that allow the use.
 * @author E.Santoboni
 */
public class CounterTag extends TagSupport implements IAutoIndexingTag {
	
	public CounterTag() {
		super();
		this.release();
	}
	
	@Override
	public int doStartTag() throws JspException {
		String currentCounter = this.getCurrentIndex();
		try {
			this.pageContext.getOut().print(currentCounter);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "doStartTag", "error creating (or modifying) counter");
			throw new JspException("error creating (or modifying) counter", t);
		}
		return super.doStartTag();
	}
	
	@Override
	public String getCurrentIndex() {
		return AutoIndexingTagHelper.getCurrentIndex(this, this.pageContext.getRequest());
	}
	
	@Override
	public void release() {
		super.release();
		this._step = 1;
	}
	
	@Override
	public int getStep() {
		return _step;
	}
	public void setStep(int step) {
		this._step = step;
	}
	
	/**
	 * @uml.property  name="_step"
	 */
	private int _step = 1;
	
}
