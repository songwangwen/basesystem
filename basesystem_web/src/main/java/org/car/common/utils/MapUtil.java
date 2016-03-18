package org.car.common.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.car.common.model.PageDTO;

public class MapUtil {

	
	/**
	 * 通过Map的value来找到key,如果有多个,则以找到的以一个为准
	 * @param map  被查找的Map
	 * @param value Map的Value值
	 * @return
	 */
	public static Object getKeyByValue(Map<?,?> map,Object value){
			Object key = null;
			if(value!=null){
				Set<?> set=map.entrySet();
				Iterator<?> it=set.iterator();
				while(it.hasNext()) {
					@SuppressWarnings("rawtypes")
					Map.Entry entry=(Map.Entry)it.next();
					if(value.equals(entry.getValue())){
						key = entry.getKey();
					}
				}
			}
			return key;
	}
	/**
	 * 代维组织架构对应图标
	 */
	public static final Map<String,Object> constructionParams(PageDTO page,Class<?> clazz){
		Map<String,Object> result = new HashMap<String, Object>();
		return result;
	}
	
	
}
