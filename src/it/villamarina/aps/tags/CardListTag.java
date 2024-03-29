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
package it.villamarina.aps.tags;

import it.villamarina.aps.system.DemoSystemConstants;
import it.villamarina.aps.system.services.card.Card;
import it.villamarina.aps.system.services.card.ICardManager;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.util.ApsWebApplicationUtils;

/**
 * Tag erogatore della lista di Card.
 * @version 1.0
 * @author E.Santoboni
 */
public class CardListTag extends TagSupport {
	
	@Override
	public int doStartTag() throws JspException {
		ServletRequest request =  this.pageContext.getRequest();
		try {
			String holder = request.getParameter("holder");
			ICardManager cardManager = (ICardManager) ApsWebApplicationUtils.getBean(DemoSystemConstants.CARD_MANAGER, this.pageContext);
			List<Card> cards = cardManager.searchCards(holder);
			this.pageContext.setAttribute(this.getListName(), cards);
			if (null != holder) {
				this.pageContext.setAttribute("holder", holder);
			}
		} catch (Throwable e) {
			ApsSystemUtils.logThrowable(e, this, "doStartTag");
			throw new JspException("Errore in doStartTag", e);
		}
		return super.doStartTag();
	}
	
	public void release() {
		this._listName = null;
	}
	
	/**
	 * Restituisce il nome con il quale viene inserita nel pageContext la lista delle card.
	 * @return Il nome della variabile.
	 */
	public String getListName() {
		return _listName;
	}
	
	/**
	 * Setta il nome con il quale viene inserita nel pageContext la lista delle card.
	 * @param listName Il nome della variabile.
	 */
	public void setListName(String listName) {
		this._listName = listName;
	}
	
	/**
	 * @uml.property  name="_listName"
	 */
	private String _listName;
	
}
