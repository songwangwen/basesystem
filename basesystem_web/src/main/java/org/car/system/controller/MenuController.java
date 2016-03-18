package org.car.system.controller;


import java.util.List;
import java.util.Set;

import org.car.common.controller.BaseController;
import org.car.common.model.TreeNode;
import org.car.system.model.MenuNode;
import org.car.system.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 系统注册用户管理控制类，主要对system_menu数据表进行操作
 * @author songwangwen
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController{
	@Autowired
	private IMenuService menuService;
	
	/**
	 * 左侧导航菜单树
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="list",method=RequestMethod.POST)     
	public List<MenuNode> queryMenuTree(){
		//当前操作员可见的左侧菜单
		loginUserMenuSet = (Set<Integer>)request.getSession().getAttribute("loginUserMenuSet");
		List<MenuNode> menuList=menuService.findLeftMenuTree(loginUserMenuSet);
		return menuList;
	}
	/**
	 * 操作权限树包括左侧菜单以及按钮
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="authorityList",method=RequestMethod.POST)     
	public List<TreeNode> authorityMenuTree(@RequestParam(value="roleId") long roleId){
		List<TreeNode> menuList=menuService.findMenuAuthorityTree(roleId);
		return menuList;
	}
}
