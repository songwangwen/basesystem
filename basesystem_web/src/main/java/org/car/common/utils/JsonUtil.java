package org.car.common.utils;

import net.sf.json.JSONObject;
import com.google.gson.Gson;

public class JsonUtil {

	/**
	 * 从一个JSON 对象字符格式中得到一个java对象 说明：Bean的无参构造函数一定要写, 否则会报:
	 * net.sf.json.JSONException: java.lang.NoSuchMethodException
	 * 
	 * @param jsonString
	 * @param pojoCalss
	 * @return
	 */
	public static Object getObjectFromJsonString(String jsonString, String key,Class<?> pojoCalss) {
		Object pojo;
		String key_json = JSONObject.fromObject(jsonString).getString(key);
		JSONObject jsonObject = JSONObject.fromObject(key_json);
		pojo = JSONObject.toBean(jsonObject, pojoCalss);
		return pojo;
	}
	
	/**
	 * 将对象转化为json字符串
	 * @param obj
	 * @return json={"key":"value"}
	 */
	public static String objectToJson(Object obj) {
		return new Gson().toJson(obj);
	}
	/**
	 * json字符串转化为对象
	 * @param json json字符串 json={"key":"value"}
	 * @param pojoCalss 需要转化的对象class
	 * @return
	 */
	public static Object jsonToMap(String json,Class<?> pojoCalss) {
		return new Gson().fromJson(json,pojoCalss);
	}
}
