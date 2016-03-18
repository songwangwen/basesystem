package org.car.common.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.car.common.model.PageDTO;

/**
 * 反射处理类
 * @author songwangwen
 */
public class ClassUtil {
	/**
	 * 将一个实体类中的所有属性以及属性值对应地存储到一个Map中
	 * @param o 待处理实体类
	 * @return
	 */
	public static Map<String,Object> getObjDeclaredFields(Object o){
		Map<String,Object> map = new HashMap<String, Object>();
		Field f[] = o.getClass().getDeclaredFields() ;	// 取得本类中的属性
		for(int i=0;i<f.length;i++){
	        f[i].setAccessible(true); // 抑制Java对修饰符的检查
			String fieldName = f[i].getName();
			try {
				Object fieldValue = (Object)f[i].get(o);
				map.put(fieldName, fieldValue);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return map;
	}
	public static Map<String,Object> getObjDeclaredFields(PageDTO page,Object o){
		Map<String,Object> params = new HashMap<String, Object>();
		if(o!=null){
			Field f[] = o.getClass().getDeclaredFields() ;	// 取得本类中的属性
			for(int i=0;i<f.length;i++){
				f[i].setAccessible(true); // 抑制Java对修饰符的检查
				String fieldName = f[i].getName();
				try {
					Object fieldValue = (Object)f[i].get(o);
					params.put(fieldName, fieldValue);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(page!=null){
			params.put("pageIndex", (page.getjPageIndex()-1)*page.getjPageSize());
			params.put("pageSize", page.getjPageSize());
			params.put("sortCol", page.getjSortCol());
			params.put("sortType", page.getjSortType());
		}
		return params;
	}
}
