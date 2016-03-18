package org.car.system.dao;

import java.util.List;

import org.car.common.model.TreeNode;
import org.car.system.model.MenuNode;
import org.springframework.stereotype.Repository;

@Repository
public interface IMenuDAO {
	/**
	 * 查询所有菜单项
	 * @param   node 菜单项类型<p/>
	 *         	menu:导航菜单<p/>
	 *         	button:操作按钮<p/>
	 *         	view：仅用于控制是否展示列表
	 * @return List<MenuNode>
	 */
	public List<MenuNode> getMenuNodeList(MenuNode node);
	/**
	 * 查询所有菜单项
	 * @param nodeType 菜单项类型<p/>
	 * @return List<TreeNode>
	 */
	public List<TreeNode> getTreeNodeList();
}
