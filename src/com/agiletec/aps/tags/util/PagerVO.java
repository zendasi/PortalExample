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
package com.agiletec.aps.tags.util;

/**
 * Oggetto di utilità per il tag paginatore.
 * Rappresenta l'oggetto che permette di estrarre i parametri necessari per la corretta 
 * visualizzazione dell'iter corrente (l'insieme di oggetti "sottoinsieme della lista principale" 
 * che deve essere visualizzato nella pagina corrente).
 * @author E.Santoboni
 */
public class PagerVO implements IPagerVO {
	
	/**
	 * Array di utilità; restituisce l'array ordinato degli indici numerici degli item.
	 * @return L'array ordinato degli indici numerici degli item.
	 */
	public int[] getItems() {
		int[] items = new int[this.getMaxItem()];
		for (int i = 0; i<this.getMaxItem(); i++) {
			items[i] = i+1;
		}
		return items;
	}
	
	/**
	 * Costruisce e restituisce il nome del parametro tramite il quale 
	 * individuare dalla request l'identificativo del item richiesto.
	 * Il metodo viene richiamato all'interno della jsp che genera il paginatore.
	 * @return Il nome del parametro tramite il quale 
	 * individuare dalla request l'identificativo del item richiesto.
	 */
	public String getParamItemName() {
		String paramName = "";
		if (null != this.getPagerId()) {
			paramName = this.getPagerId() + "_";
		}
		paramName = paramName + "item";
		return paramName;
	}
	
	/**
	 * Restituisce il numero massimo di elementi della lista per ogni item.
	 * @return Il numero massimo di elementi della lista per ogni item.
	 */
	public int getMax() {
		return _max;
	}

	/**
	 * Setta il numero massimo di elementi della lista per ogni item.
	 * @param max Il numero massimo di elementi della lista per ogni item.
	 */
	protected void setMax(int max) {
		this._max = max;
	}

	/**
	 * Restituisce l'identificativo numerico del gruppo item precedente.
	 * @return L'identificativo numerico del gruppo item precedente.
	 */
	public int getPrevItem() {
		return _prevItem;
	}

	/**
	 * Setta l'identificativo numerico del gruppo item precedente.
	 * @param prev L'identificativo numerico del gruppo item precedente.
	 */
	protected void setPrevItem(int prev) {
		this._prevItem = prev;
	}

	/**
	 * Restituisce il size della lista principale.
	 * @return Il size della lista principale.
	 */
	public int getSize() {
		return _size;
	}

	/**
	 * Setta il size della lista principale.
	 * @param size Il size della lista principale.
	 */
	protected void setSize(int size) {
		this._size = size;
	}

	/**
	 * Restituisce l'identificativo numerico del gruppo item successivo.
	 * @return L'identificativo numerico del gruppo item successivo.
	 */
	public int getNextItem() {
		return _nextItem;
	}

	/**
	 * Setta l'identificativo numerico del gruppo item successivo.
	 * @param next L'identificativo numerico del gruppo item successivo.
	 */
	protected void setNextItem(int next) {
		this._nextItem = next;
	}

	/**
	 * Restituisce l'identificativo numerico del gruppo item corrente.
	 * @return L'identificativo numerico del gruppo item corrente.
	 */
	public int getCurrItem() {
		return _currItem;
	}

	/**
	 * Setta l'identificativo numerico del gruppo iter corrente.
	 * @param currItem L'identificativo numerico del gruppo item corrente.
	 */
	protected void setCurrItem(int currItem) {
		this._currItem = currItem;
	}
	
	/**
	 * Restituisce l'indice di partenza sulla lista principale dell'item corrente.
	 * @return L'indice di partenza sulla lista principale dell'item corrente.
	 */
	public int getBegin() {
		return _begin;
	}

	/**
	 * Setta l'indice di partenza sulla lista principale dell'item corrente.
	 * @param begin L'indice di partenza sulla lista principale dell'item corrente.
	 */
	protected void setBegin(int begin) {
		this._begin = begin;
	}

	/**
	 * Restituisce l'indice di arrivo sulla lista principale dell'item corrente.
	 * @return L'indice di arrivo sulla lista principale dell'item corrente.
	 */
	public int getEnd() {
		return _end;
	}

	/**
	 * Setta l'indice di arrivo sulla lista principale dell'item corrente.
	 * @param end L'indice di arrivo sulla lista principale dell'item corrente.
	 */
	protected void setEnd(int end) {
		this._end = end;
	}
	
	/**
	 * Restituisce l'identificativo numerico dell'ultimo gruppo iter.
	 * @return L'identificativo numerico dell'ultimo gruppo item.
	 */
	public int getMaxItem() {
		return _maxItem;
	}

	/**
	 * Setta l'identificativo numerico dell'ultimo gruppo iter.
	 * @param maxItem L'identificativo numerico dell'ultimo gruppo item.
	 */
	protected void setMaxItem(int maxItem) {
		this._maxItem = maxItem;
	}
	
	/**
	 * Setta l'identificativo del paginatore.
	 * @return L'identificativo del paginatore.
	 */
	public String getPagerId() {
		return _pagerId;
	}

	/**
	 * Restituisce l'identificativo del paginatore.
	 * @param pagerId l'identificativo del paginatore.
	 */
	protected void setPagerId(String pagerId) {
		this._pagerId = pagerId;
	}
	
	public int getBeginItemAnchor() {
		return _beginItemAnchor;
	}
	protected void setBeginItemAnchor(int beginItemAnchor) {
		this._beginItemAnchor = beginItemAnchor;
	}
	public int getEndItemAnchor() {
		return _endItemAnchor;
	}
	protected void setEndItemAnchor(int endItemAnchor) {
		this._endItemAnchor = endItemAnchor;
	}
	
	public boolean isAdvanced() {
		return _advanced;
	}
	protected void setAdvanced(boolean advanced) {
		this._advanced = advanced;
	}
	
	public int getOffset() {
		return _offset;
	}
	protected void setOffset(int offset) {
		this._offset = offset;
	}
	
	/**
	 * @uml.property  name="_prevItem"
	 */
	private int _prevItem;
	/**
	 * @uml.property  name="_currItem"
	 */
	private int _currItem;
	/**
	 * @uml.property  name="_nextItem"
	 */
	private int _nextItem;
	/**
	 * @uml.property  name="_maxItem"
	 */
	private int _maxItem;
	/**
	 * @uml.property  name="_begin"
	 */
	private int _begin;
	/**
	 * @uml.property  name="_end"
	 */
	private int _end;
	/**
	 * @uml.property  name="_size"
	 */
	private int _size;
	/**
	 * @uml.property  name="_max"
	 */
	private int _max;
	/**
	 * @uml.property  name="_pagerId"
	 */
	private String _pagerId;
	
	/**
	 * @uml.property  name="_offset"
	 */
	private int _offset;
	/**
	 * @uml.property  name="_beginItemAnchor"
	 */
	private int _beginItemAnchor;
	/**
	 * @uml.property  name="_endItemAnchor"
	 */
	private int _endItemAnchor;
	/**
	 * @uml.property  name="_advanced"
	 */
	private boolean _advanced;

}