package org.car.system.service;

import java.util.List;
import java.util.Set;

import org.car.common.model.TreeNode;
import org.car.system.model.MenuNode;
import org.car.system.model.User;

public interface IMenuService {
	
	/**
	 * 查询左侧菜单数据表，以树形结构返回数据
	 * @param set 当前登录的操作员可访问的菜单id集合
	 * @return List<MenuNode>
	 */
	public List<MenuNode> findLeftMenuTree(Set<Integer> set);
	
	
	/**
	 * 查询权限配置菜单树
	 * @return List<MenuNode>
	 */
	public List<MenuNode> findDataAuthorityTree();
	
	
	/**
	 * 返回权限配置树
	 * @param roleId 当前配置的角色，已有权限打勾
	 * @return
	 */
	public List<TreeNode> findMenuAuthorityTree(long roleId);
	
}
