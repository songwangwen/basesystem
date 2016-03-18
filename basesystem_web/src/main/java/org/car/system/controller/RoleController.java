package org.car.system.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.car.common.controller.BaseController;
import org.car.common.model.JQTableVO;
import org.car.common.model.JSONMessage;
import org.car.common.model.PageDTO;
import org.car.system.model.Role;
import org.car.system.model.RoleMenuAuthority;
import org.car.system.model.User;
import org.car.system.model.UserRoleRel;
import org.car.system.service.IRoleService;
import org.car.system.service.IUserService;
import org.car.system.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统注册用户管理控制类，主要对SYSTEM_USER数据表进行操作
 * @author songwangwen
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
	protected static final Log log = LogFactory.getLog(UserServiceImpl.class); 
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IUserService userService;
	
	private PageDTO page;
	private Role role;
	@ResponseBody
	@RequestMapping(value="list",method=RequestMethod.POST)     
	public JQTableVO list(){
		page = getPage();
		String jsonForm = request.getParameter("jsonForm");
		try {
			if(jsonForm!=null){
				jsonForm = URLDecoder.decode(URLDecoder.decode(jsonForm, "UTF-8"),"UTF-8");
				role = (Role)getProcessContent(jsonForm, Role.class);
			}else{
				role = null;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		table = roleService.queryAllRoleForPage(page, role);
		return table;
	}
	
	/**
	 *新增角色
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="addNewRole",method=RequestMethod.POST)     
	public JSONMessage addNewRole(){
		JSONMessage msg = new JSONMessage();
		String jsonForm = request.getParameter("jsonForm");
		try {
			if(jsonForm!=null){
				jsonForm = URLDecoder.decode(URLDecoder.decode(jsonForm, "UTF-8"),"UTF-8");
				if(loginUser!=null)role.setCreateUser(loginUser.getId());
				role = (Role)getProcessContent(jsonForm, Role.class);
				roleService.save(role);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg.setMsg("新增失败");
			msg.setFlag(false);
		}
		msg.setFlag(true);
		msg.setMsg("新增成功");
		return msg;
	}
	/**
	 *编辑角色
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="editRole",method=RequestMethod.POST)     
	public JSONMessage editRole(){
		JSONMessage msg = new JSONMessage();
		String jsonForm = request.getParameter("jsonForm");
		try {
			if(jsonForm!=null){
				jsonForm = URLDecoder.decode(URLDecoder.decode(jsonForm, "UTF-8"),"UTF-8");
				role = (Role)getProcessContent(jsonForm, Role.class);
				Role existRole = roleService.findRoleByRoleId(role.getId());
				if(existRole==null){
					msg.setFlag(false);
					msg.setMsg("编辑失败,该角色不存在或已被删除");
					return msg;
				}
				role.setVersion(existRole.getVersion());
				loginUser = (User) request.getSession().getAttribute("loginUser");
				role.setUpdateUser(loginUser.getId());
				roleService.edit(role);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("编辑角色失败");
			msg.setMsg("编辑角色失败");
			msg.setFlag(false);
		}
		msg.setFlag(true);
		msg.setMsg("编辑成功");
		return msg;
	}
	/**
	 *删除角色
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="removeRole",method=RequestMethod.POST)     
	public JSONMessage removeRole(@RequestParam(value="id") long id){
		JSONMessage msg = new JSONMessage();
		try {
			if(id!=0){
				role = roleService.findRoleByRoleId(role.getId());
				if(role==null){
					msg.setFlag(false);
					msg.setMsg("删除失败,该角色不存在或已被删除");
					return msg;
				}
				loginUser = (User) request.getSession().getAttribute("loginUser");
				role.setUpdateUser(loginUser.getId());
				if(roleService.edit(role)>0){
					msg.setFlag(true);
					msg.setMsg("删除角色成功");
				}else{
					msg.setFlag(false);
					msg.setMsg("删除角色失败");
				}
			}else{
				msg.setFlag(false);
				msg.setMsg("删除角色,需要用户id参数");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	
	/**
	 * 根据角色ID查询得到角色信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="queryRoleById",method=RequestMethod.POST)     
	public Role queryRoleById(@RequestParam("id") long id){
		role = roleService.findRoleByRoleId(id);
		return role;
	}
	/**
	 *配置角色权限
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="configureAuthority",method=RequestMethod.POST)     
	public JSONMessage configureAuthority(@RequestParam("id") long id,
			@RequestParam("authority") String authority){
		loginUser = (User) request.getSession().getAttribute("loginUser");
		JSONMessage msg = new JSONMessage();
		try {
			if(id!=0){
				role = roleService.findRoleByRoleId(id);
				if(role==null){
					msg.setFlag(false);
					msg.setMsg("配置失败,该角色不存在或已被删除");
					return msg;
				}
				if(roleService.configureMenuAuthority(id,authority,loginUser.getId())>0){
					msg.setFlag(true);
					msg.setMsg("配置角色权限成功");
				}else{
					msg.setFlag(false);
					msg.setMsg("配置角色权限失败");
				}
			}else{
				msg.setFlag(false);
				msg.setMsg("配置角色权限失败,缺少参数");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}
	
	/**
	 *返回所有角色信息，用于配置人员的角色，已配置的则打勾
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="queryRoleList",method=RequestMethod.POST)     
	public JSONMessage queryRoleList(@RequestParam("userId") long userId){
		StringBuffer HTML = new  StringBuffer();
		List<Role> roleList = roleService.queryAllRole();
		List<UserRoleRel> relList = userService.queryUserRoleRelByUserId(userId);
		for(Role r :roleList){
			/**
			 * 
			 * <li title='超级管理员(2001)' class='selected'>
		        <a id='fb815f11-6457-4bf4-b600-f3dbea378ff3'>
		        <img src='/css/icon/16/user-worker.png'>
		       		 超级管理员
		        </a><i></i></li>
			 * ***/
			HTML.append("<li title='超级管理员(2001)' ");
			if(comparison(relList,r.getId())){
				HTML.append(" class='selected' ");
			}
			HTML.append("><a id='").append(r.getId()).append("'>");
			HTML.append("<img src='/css/icon/16/user-worker.png'>").append(r.getRoleName()).append("</a><i></i></li>");
		}
		JSONMessage msg = new JSONMessage();
		msg.setFlag(true);
		msg.setMsg(HTML.toString());
		return msg;
	}
	
	/**
	 * 判断当前用户是否已经拥有该权限
	 * @param auth
	 * @param menuId
	 * @return
	 */
	public boolean comparison(List<UserRoleRel> relList,long roleId){
		boolean flag = false;
		if(relList!=null&&relList.size()>0)
			for(UserRoleRel r:relList){
				if(r.getRoleId()==roleId){
					flag = true;
					return flag;
				}
			}
		return flag;
	}
	/****************************getter  setter**************************************************/
}
