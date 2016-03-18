package org.car.system.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.car.common.controller.BaseController;
import org.car.common.model.JSONMessage;
import org.car.common.utils.HttpUtils;
import org.car.common.utils.IPUtils;
import org.car.common.utils.JsonUtil;
import org.car.common.utils.MD5;
import org.car.common.utils.StringUtil;
import org.car.system.model.MenuNode;
import org.car.system.model.User;
import org.car.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


/**
 * 用户登录类
 * ClassName: LoginController 
 * @Description: TODO
 * @author songwanwgen
 * @date 2015-8-31
 */
@Controller  
@RequestMapping("/login")
public class LoginController extends BaseController{
	@Autowired
	private IUserService userService;
	
	private User loginUser;//登录用户
	@RequestMapping("/main")
	public ModelAndView main(){
		loginUser = (User) request.getSession().getAttribute("loginUser");  
		ModelAndView mav = new ModelAndView();
		StringBuffer errorMsg = new StringBuffer();
		String viewName = "main";
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		if(StringUtils.isNotBlank(userName)&&StringUtils.isNotBlank(password)){
			loginUser = userService.findUserByUserName(userName);
			if(loginUser!=null){
				//匹配用户名密码
				password = MD5.getInstance().getMd5(password, "UTF-8");
				if(!password.equals(loginUser.getPassword())){
					//匹配失败,密码错误
					viewName = "login";
					errorMsg.append("密码错误");
				}else if(!loginUser.getEnabled()){
					//匹配失败,账号被停用
					viewName = "login";
					errorMsg.append("用户未启用，请联系管理员");
				}else if(userName.equals(loginUser.getUserName())&&password.equals(loginUser.getPassword())){
					request.getSession().setAttribute("loginUser",loginUser); 
					//匹配成功
					viewName = "main";
					//判断是否手机登录
			        Boolean b = HttpUtils.isMobileDevice(request);  
			        log.info("isMobileDevice，b={}",b);
					//查询数据库，读取当前用户权限
					Set<Integer> loginUserMenuSet = userService.queryRoleMenuAuthorityByUserId(loginUser.getId());
					request.getSession().setAttribute("loginUserMenuSet",loginUserMenuSet); 
					Map<String,MenuNode>  menuMap = userService.queryMenuNodeByUserId(loginUserMenuSet);
					request.getSession().setAttribute("menuMap",menuMap); 
					
					try {
						loginUser.setLastLoginTime(new Date());
						loginUser.setLastLoginIp(IPUtils.getIpAddr(request));
						//更新最后登录时间
						userService.modifyUser(loginUser);
						
					} catch (Exception e) {
						// TODO: handle exception
						log.error("更新用户信息失败,"+e.toString());
					}
		            log.info("用户登录,用户信息{}",JsonUtil.objectToJson(loginUser));
				}
			}else{
					//匹配失败,用户名不存在
					viewName = "login";
					errorMsg.append("用户名不存在");
			}
		}else if(loginUser==null){
			errorMsg.append("登录信息超时,请重新登录");
			viewName = "login";
		}
		mav.addObject("errorMsg",errorMsg);
		mav.addObject("username",userName);
		mav.setViewName(viewName);
		return mav;
	}
	
	
	/**
	 * 异步进行登录验证
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="relogin",method=RequestMethod.POST)     
	public JSONMessage relogin(){
		User reloginUser = null;
		JSONMessage result = new JSONMessage();
		String jsonForm = request.getParameter("jsonForm");
		try {
			if(jsonForm!=null){
				jsonForm = URLDecoder.decode(URLDecoder.decode(jsonForm, "UTF-8"),"UTF-8");
				reloginUser = (User)getProcessContent(jsonForm, User.class);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String userName = reloginUser==null?null:reloginUser.getUserName();
		String password = reloginUser==null?null:reloginUser.getPassword();
		if(!StringUtil.isNullOrEmpty(userName)&&!StringUtil.isNullOrEmpty(password)){
			loginUser = userService.findUserByUserName(userName);
			if(reloginUser!=null){
				//匹配用户名密码
				password = MD5.getInstance().getMd5(password, "UTF-8");
				if(!password.equals(loginUser.getPassword())){
					//匹配失败,密码错误
					result.setMsg("密码错误");
				}else if(!loginUser.getEnabled()){
					//匹配失败,密码错误
					result.setMsg("用户未启用，请联系管理员");
				}else if(userName.equals(loginUser.getUserName())&&password.equals(loginUser.getPassword())){
					result.setFlag(true);
					result.setMsg("登录成功");
				}
			}else{
					//匹配失败,用户名不存在
				result.setMsg("用户名不存在");
			}
		}else{
			if(loginUser==null){
				result.setMsg("用户名或密码为空");
			}
		}
		return result;
	}
}
