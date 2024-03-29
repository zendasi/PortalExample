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
package com.agiletec.apsadmin.tags.util;

import java.io.Writer;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.components.Component;

import com.agiletec.apsadmin.tags.ParamMapTag;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * Component class for tag {@link ParamMapTag} used to parameterize other tags with a map of parameters.
 * @author E.Santoboni
 */
public class ParamMap extends Component {
	
    public ParamMap(ValueStack stack) {
        super(stack);
    }
    
    @Override
	public boolean end(Writer writer, String body) {
    	Log log = LogFactory.getLog(ParamMap.class);
    	Component component = this.findAncestor(Component.class);
        if (null == this.getMap()) {
            log.warn("Attribute map is mandatory.");
            return super.end(writer, null);
        }
        Object object = findValue(this.getMap());
        if (null == object) {
            log.warn("Map not found in ValueStack");
            return super.end(writer, null);
        }
        if(!(object instanceof Map)) {
            log.warn("Error in JSP. Attribute map must evaluate to java.util.Map. Found type: " + object.getClass().getName());
            return super.end(writer, null);
        }
        component.addAllParameters((Map) object);
		return super.end(writer, null);
	}

	protected String getMap() {
		return _map;
	}
	public void setMap(String map) {
		this._map = map;
	}
	
	/**
	 * @uml.property  name="_map"
	 */
	private String _map;
	
}