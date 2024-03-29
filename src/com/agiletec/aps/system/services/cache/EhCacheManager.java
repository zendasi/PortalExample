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
package com.agiletec.aps.system.services.cache;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.web.context.ServletContextAware;

import com.agiletec.aps.system.ApsSystemUtils;
import com.agiletec.aps.system.SystemConstants;
import com.agiletec.aps.system.common.AbstractService;
import com.agiletec.aps.system.exception.ApsSystemException;
import com.agiletec.aps.system.services.page.IPage;
import com.agiletec.aps.system.services.page.events.PageChangedEvent;
import com.agiletec.aps.system.services.page.events.PageChangedObserver;
import com.agiletec.aps.util.FileTextReader;

/**
 * Manager of the System Cache
 * @author E.Santoboni
 */
public class EhCacheManager extends AbstractService implements ICacheManager, PageChangedObserver, ServletContextAware {
	
	@Override
	public void init() throws Exception {
		InputStream is1 = this._servletContext.getResourceAsStream(this.getCacheConfigurationFilePath());
		InputStream is2 = null;
		try {
			String text = FileTextReader.getText(is1);
			text = text.replaceFirst(CACHE_DISK_ROOT_FOLDER_MARKER, this.getCacheDiskRootFolder());
			byte[] bytes = text.getBytes("UTF-8");
			is2 = new ByteArrayInputStream(bytes);
			this._cacheManager = CacheManager.create(is2);
		} catch (Throwable t) {
			ApsSystemUtils.logThrowable(t, this, "init");
			throw new ApsSystemException("Error detected while inizializing cache manager", t);
		} finally {
			is1.close();
			if (null != is2) {
				is2.close();
			}
		}
		this._cacheManager.clearAll();
		this._cache = this._cacheManager.getCache(CACHE_NAME);
		ApsSystemUtils.getLogger().config(
				this.getClass().getName() + ": cache service initialized");
	}
	
	@Override
	public void updateFromPageChanged(PageChangedEvent event) {
		IPage page = event.getPage();
		String pageCacheGroupName = SystemConstants.PAGES_CACHE_GROUP_PREFIX + page.getCode();
		this.flushGroup(pageCacheGroupName);
	}
	
	@Override
	protected void release() {
		super.release();
		this._cacheManager.removalAll();
		this._cacheManager.shutdown();
		this._groups.clear();
	}
	
	@Override
	public void destroy() {
		this._cacheManager.removalAll();
		this._cacheManager.shutdown();
		this._groups.clear();
	}
	
	@Override
	public void flushAll() {
		this._cache.flush();
	}
	
	@Override
	public void flushEntry(String key) {
		this._cache.remove(key);
	}
	
	@Override
	public void flushGroup(String group) {
		String[] groups = {group};
		this.accessOnGroupMapping(-1, groups, null);
	}
	
	@Override
	public Object getFromCache(String key, int myRefreshPeriod) {
		Element element = this._cache.get(key);
		if (null == element) return null;
		long creationTime = element.getCreationTime();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(creationTime);
		calendar.add(Calendar.SECOND, myRefreshPeriod);
		if (calendar.before(Calendar.getInstance())) {
			this._cache.remove(key);
			return null;
		}
		return element.getObjectValue();
	}
	
	public long getCreationTime(String key) {
		Element element = this._cache.get(key);
		if (null == element) return 0;
		return element.getCreationTime();
	}
	
	@Override
	public Object getFromCache(String key) {
		Element element = this._cache.get(key);
		if (null == element) return null;
		return element.getObjectValue();
	}
	@Override
	public void putInCache(String key, Object obj, String[] groups) {
		Element element = new Element(key, obj);
		this._cache.put(element);
		this.accessOnGroupMapping(1, groups, key);
	}
	
	private synchronized void accessOnGroupMapping(int operationId, String[] groups, String key) {
		if (operationId>0) {
			//aggiunta
			for (int i = 0; i < groups.length; i++) {
				String group = groups[i];
				List<String> objectKeys = this._groups.get(group);
				if (null == objectKeys) {
					objectKeys = new ArrayList<String>();
					this._groups.put(group, objectKeys);
				}
				if (!objectKeys.contains(key)) objectKeys.add(key);
			}
		} else {
			//rimozione
			for (int i = 0; i < groups.length; i++) {
				String group = groups[i];
				List<String> objectKeys = this._groups.get(group);
				if (null != objectKeys) {
					for (int j = 0; j < objectKeys.size(); j++) {
						String extractedKey = objectKeys.get(j);
						this.flushEntry(extractedKey);
					}
					this._groups.remove(group);
				}
			}
		}
	}
	
	@Override
	public void putInCache(String key, Object obj) {
		Element element = new Element(key, obj);
		this._cache.put(element);
	}
	
	protected String getCacheConfigurationFilePath() {
		return _cacheConfigurationFilePath;
	}
	public void setCacheConfigurationFilePath(String cacheConfigurationFilePath) {
		this._cacheConfigurationFilePath = cacheConfigurationFilePath;
	}
	
	protected String getCacheDiskRootFolder() {
		return _cacheDiskRootFolder;
	}
	public void setCacheDiskRootFolder(String cacheDiskRootFolder) {
		this._cacheDiskRootFolder = cacheDiskRootFolder;
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this._servletContext = servletContext;
	}
	
	/**
	 * @uml.property  name="_cacheConfigurationFilePath"
	 */
	private String _cacheConfigurationFilePath;
	/**
	 * @uml.property  name="_cacheDiskRootFolder"
	 */
	private String _cacheDiskRootFolder;
	/**
	 * @uml.property  name="_servletContext"
	 * @uml.associationEnd  
	 */
	private ServletContext _servletContext;
	
	/**
	 * @uml.property  name="_cacheManager"
	 * @uml.associationEnd  
	 */
	private CacheManager _cacheManager;
	/**
	 * @uml.property  name="_cache"
	 * @uml.associationEnd  
	 */
	private Cache _cache;
	
	/**
	 * @uml.property  name="_groups"
	 * @uml.associationEnd  multiplicity="(0 -1)" ordering="true" elementType="java.lang.String" qualifier="group:java.lang.String java.util.List"
	 */
	private Map<String, List<String>> _groups = new HashMap<String, List<String>>();
	/**
	 * @uml.property  name="cACHE_DISK_ROOT_FOLDER_MARKER"
	 */
	private final String CACHE_DISK_ROOT_FOLDER_MARKER = "@cacheDiskRootFolder@";
	/**
	 * @uml.property  name="cACHE_NAME"
	 */
	private final String CACHE_NAME = "jAPS2.0_Cache";
	
}