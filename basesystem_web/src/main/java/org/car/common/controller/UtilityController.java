package org.car.common.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.car.common.model.FieldExist;
import org.car.common.service.IUtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 
 * 通用工具类，主要作用如下
 * 1、检测重复
 * ClassName: UtilityController 
 * @Description: TODO
 * @author songwanwgen
 * @date 2015-09-23
 */
@Controller  
@RequestMapping("/utility")
public class UtilityController extends BaseController{
	
	private FieldExist field;//验证数据表中某字段值是否已存在
	
	@Autowired
	private IUtilityService utilityService;
	
	/**
	 * 验证数据表中的某个字段是否存在某个数据值
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="fieldExist",method=RequestMethod.POST)     
	public int fieldExist(){
		int flag = 0;
		String jsonForm = request.getParameter("jsonForm");
		try {
			if(jsonForm!=null){
				jsonForm = URLDecoder.decode(URLDecoder.decode(jsonForm, "UTF-8"),"UTF-8");
				field = (FieldExist)getProcessContent(jsonForm, FieldExist.class);
				flag = utilityService.fieldExist(field);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
}
