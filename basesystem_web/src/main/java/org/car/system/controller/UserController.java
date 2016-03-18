package org.car.system.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.car.common.controller.BaseController;
import org.car.common.model.JQTableVO;
import org.car.common.model.JSONMessage;
import org.car.common.model.PageDTO;
import org.car.common.utils.JsonUtil;
import org.car.common.utils.MD5;
import org.car.system.model.User;
import org.car.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统注册用户管理相关角色控制类，
 * 主要对SYSTEM_ROLE数据表进行操作
 * @author songwangwen
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
	@Autowired
	private IUserService userService;
	@Value("${initial.password}")
	private String initialPassword;//初始密码
	
	private PageDTO page;
	private User user;
	
	
	@ResponseBody
	@RequestMapping(value="list",method=RequestMethod.POST)     
	public JQTableVO list(){
		page = getPage();
		String jsonForm = request.getParameter("jsonForm");
		try {
			if(jsonForm!=null){
				jsonForm = URLDecoder.decode(URLDecoder.decode(jsonForm, "UTF-8"),"UTF-8");
				user = (User)getProcessContent(jsonForm, User.class);
			}else{
				user = null;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("读取列表出错,user={}",JsonUtil.objectToJson(user).toString());
		}
		table = userService.queryAllUserForPage(page, user);
		return table;
	}
	
	/**
	 *新增用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="addNewUser",method=RequestMethod.POST)     
	public JSONMessage addNewUser(){
		JSONMessage msg = new JSONMessage();
		String jsonForm = request.getParameter("jsonForm");
		try {
			if(jsonForm!=null){
				jsonForm = URLDecoder.decode(URLDecoder.decode(jsonForm, "UTF-8"),"UTF-8");
				user = (User)getProcessContent(jsonForm, User.class);
				if(loginUser!=null)user.setCreateUser(loginUser.getId());
				userService.saveUser(user);
				msg.setFlag(true);
				msg.setMsg("新增用户成功");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	/**
	 *编辑用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="editUser",method=RequestMethod.POST)     
	public JSONMessage editUser(){
		JSONMessage msg = new JSONMessage();
		String jsonForm = request.getParameter("jsonForm");
		try {
			if(jsonForm!=null){
				jsonForm = URLDecoder.decode(URLDecoder.decode(jsonForm, "UTF-8"),"UTF-8");
				user = (User)getProcessContent(jsonForm, User.class);
				User existUser = userService.findUserByUserId(user.getId());
				if(existUser==null){
					msg.setFlag(false);
					msg.setMsg("编辑失败,该用户不存在或已被删除");
					return msg;
				}
				user.setVersion(existUser.getVersion());
				loginUser = (User) request.getSession().getAttribute("loginUser");
				user.setUpdateUser(loginUser.getId());
				if(userService.modifyUser(user)>0){
					msg.setFlag(true);
					msg.setMsg("编辑成功");
				}else{
					msg.setFlag(false);
					msg.setMsg("编辑失败");
				}
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	/**
	 *删除用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="removeUser",method=RequestMethod.POST)     
	public JSONMessage removeUser(@RequestParam(value="id") long id){
		JSONMessage msg = new JSONMessage();
		try {
			if(id!=0){
				user= userService.findUserByUserId(id);
				if(user==null){
					msg.setFlag(false);
					msg.setMsg("编辑失败,该用户不存在或已被删除");
					return msg;
				}
				user.setDeleted(true);
				loginUser = (User) request.getSession().getAttribute("loginUser");
				user.setUpdateUser(loginUser.getId());
				if(userService.modifyUser(user)>0){
					msg.setFlag(true);
					msg.setMsg("删除成功");
				}else{
					msg.setFlag(false);
					msg.setMsg("删除失败");
				}
			}else{
				msg.setFlag(true);
				msg.setMsg("删除失败,需要用户id参数");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	/**
	 *删除用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="configureRoleSubmit",method=RequestMethod.POST)     
	public JSONMessage configureRoleSubmit(@RequestParam(value="roleIds") String roleIds,
			@RequestParam(value="userId") long userId){
		JSONMessage msg = new JSONMessage();
		loginUser = (User) request.getSession().getAttribute("loginUser");
		try {
			//清除历史数据
			userService.deleteUserRoleRelByUserId(userId);
			userService.saveUserRoleRel(userId, roleIds, loginUser);
			msg.setFlag(true);
			msg.setMsg("编辑用户角色成功");
		} catch (Exception e) {
			// TODO: handle exception
			log.error("编辑用户角色失败");
			msg.setMsg("编辑用户角色失败");
		}
		return msg;
	}
	/**
	 *停用或启用用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="enabled",method=RequestMethod.POST)     
	public JSONMessage enabled(@RequestParam(value="id") long id){
		JSONMessage msg = new JSONMessage();
		loginUser = (User) request.getSession().getAttribute("loginUser");
		try {
			user = this.userService.findUserByUserId(id);
			if(user!=null){
				msg.setFlag(true);
				msg.setMsg((user.getEnabled()?"停用":"启用")+"用户["+user.getRealName()+"]成功");
				user.setEnabled(user.getEnabled()?false:true);
				userService.modifyUser(user);
			}else{
				msg.setFlag(false);
				msg.setMsg("停/启用户失败,用户不存在或已删除");
			}
			//清除历史数据
		} catch (Exception e) {
			// TODO: handle exception
			log.error("停/启用用户失败,{}",e);
			msg.setFlag(false);
			msg.setMsg("停/启用用户失败");
		}
		return msg;
	}
	/**
	 *重置登录密码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="resetPassword",method=RequestMethod.POST)     
	public JSONMessage resetPassword(@RequestParam(value="id") long id){
		JSONMessage msg = new JSONMessage();
		loginUser = (User) request.getSession().getAttribute("loginUser");
		try {
			user = this.userService.findUserByUserId(id);
			if(user!=null){
				msg.setFlag(true);
				msg.setMsg("用户["+user.getRealName()+"]密码被重置为初始密码,请告知用户及时修改密码！");
				user.setPassword(MD5.getInstance().getMd5(initialPassword, "UTF-8"));
				userService.modifyUser(user);
			}else{
				msg.setFlag(false);
				msg.setMsg("重置密码失败,用户不存在或已删除");
			}
			//清除历史数据
		} catch (Exception e) {
			// TODO: handle exception
			log.error("重置密码失败,{}",e);
			msg.setFlag(false);
			msg.setMsg("重置密码失败");
		}
		return msg;
	}

	/**
	 * 根据用户ID查询得到用户信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="queryUserById",method=RequestMethod.POST)     
	public User queryUserById(@RequestParam("id") long id){
		user = userService.findUserByUserId(id);
		return user;
	}
	/****************************getter  setter**************************************************/
}
