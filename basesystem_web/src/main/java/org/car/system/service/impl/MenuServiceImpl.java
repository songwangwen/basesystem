package org.car.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.car.common.constants.MenuNodeType;
import org.car.common.model.TreeNode;
import org.car.common.utils.TreeUtil;
import org.car.system.dao.IAuthorityDAO;
import org.car.system.dao.IMenuDAO;
import org.car.system.dao.IRoleDAO;
import org.car.system.dao.IUserDAO;
import org.car.system.dao.IUserRoleRelDAO;
import org.car.system.model.MenuNode;
import org.car.system.model.RoleMenuAuthority;
import org.car.system.model.User;
import org.car.system.model.UserRoleRel;
import org.car.system.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuServiceImpl implements IMenuService {
	 private static final Log log = LogFactory.getLog(MenuServiceImpl.class); 

	@Autowired
	private IMenuDAO menuDao;
	@Autowired
	private IAuthorityDAO  authorityDAO;
	
	@Override
	public List<MenuNode> findLeftMenuTree(Set<Integer> set) {
		// TODO Auto-generated method stub
		MenuNode node = new MenuNode();
		node.setNodeType("menu");
		node.setNodeIdSet(set);
		List<MenuNode> menuList  = menuDao.getMenuNodeList(node);
		//将原始的List通过地柜方法拼装为一个树形的List（子节点包含在父节点的child属性里面）
		if(menuList!=null&&menuList.size()>0)
			menuList = TreeUtil.getRoot(menuList);
		else
			log.info("菜单项查询结果为空");
		return menuList;
	}

	@Override
	public List<MenuNode> findDataAuthorityTree() {
		List<MenuNode> menuList  = menuDao.getMenuNodeList(null);
		//将原始的List通过地柜方法拼装为一个树形的List（子节点包含在父节点的child属性里面）
		if(menuList!=null&&menuList.size()>0)
			menuList = TreeUtil.getRoot(menuList);
		else
			log.info("权限菜单配置树查询结果为空");
		return menuList;
	}
	
	@Override
	public List<TreeNode> findMenuAuthorityTree(long roleId) {
		List<RoleMenuAuthority> roleMenuAuthority = null;
		if(roleId!=0){
			roleMenuAuthority = authorityDAO.queryRoleAuthorityByRoleId(roleId);
		}
		List<TreeNode> treeList  = new ArrayList<TreeNode>();
		List<MenuNode> menuList  = menuDao.getMenuNodeList(null);
		for(MenuNode menu:menuList){
			TreeNode tn = new TreeNode();
			tn.setId(String.valueOf(menu.getNodeId()));
			tn.setText(menu.getNodeName());
			tn.setIconCls(menu.getIconCls());
			tn.setParentId(String.valueOf(menu.getParentId()));
			Map<String,Object> attributes = new HashMap<String,Object>();
			attributes.put("nodeType",menu.getNodeType());
			tn.setAttributes(attributes);
			//菜单项，而且必须是按钮才打勾
			if((MenuNodeType.BUTTON.equals(menu.getNodeType())||MenuNodeType.VIEW.equals(menu.getNodeType()))
					&&comparison(roleMenuAuthority, menu.getNodeId())){
				tn.setChecked("true");
			}
			treeList.add(tn);
		}
		if(treeList!=null&&treeList.size()>0)
			treeList = TreeUtil.getTreeNodeRoot(treeList);
		else
			log.info("权限菜单配置树查询结果为空");
		return treeList;
	}

	/**
	 * 判断当前角色是否已经拥有该权限
	 * @param auth
	 * @param menuId
	 * @return
	 */
	public boolean comparison(List<RoleMenuAuthority> authList,long menuId){
		boolean flag = false;
		if(authList!=null&&authList.size()>0)
			for(RoleMenuAuthority r:authList){
				if(r.getMenuId()==menuId){
					return true;
				}
			}
		return flag;
	}
}
