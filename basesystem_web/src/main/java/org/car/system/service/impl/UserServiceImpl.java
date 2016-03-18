package org.car.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.car.common.model.JQTableVO;
import org.car.common.model.PageDTO;
import org.car.common.utils.ClassUtil;
import org.car.common.utils.MD5;
import org.car.system.dao.IAuthorityDAO;
import org.car.system.dao.IMenuDAO;
import org.car.system.dao.IRoleDAO;
import org.car.system.dao.IUserDAO;
import org.car.system.dao.IUserRoleRelDAO;
import org.car.system.model.MenuNode;
import org.car.system.model.Role;
import org.car.system.model.RoleMenuAuthority;
import org.car.system.model.User;
import org.car.system.model.UserRoleRel;
import org.car.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统用户管理业务类
 * @author songwangwen
 */
@Service
public class UserServiceImpl implements IUserService {
	private static final Log log = LogFactory.getLog(UserServiceImpl.class); 

	@Autowired
	private IUserDAO userDao;
	@Autowired
	private IRoleDAO roleDao;
	@Autowired
	private IUserRoleRelDAO userRoleRelDAO;
	@Autowired
	private IAuthorityDAO authorityDAO;
	@Autowired
	private IMenuDAO menuDao;
	
	@Override
	public User findUserByUserName(String userName) {
		List<User> list = userDao.getUserByUserName(userName);
		if(list!=null&&list.size()>0)
			return list.get(0);
		return null;
	}

	@Override
	public JQTableVO queryAllUserForPage(PageDTO page, User user) {
		JQTableVO vo = new JQTableVO(page);
		// TODO Auto-generated method stub
		Map<String,Object> params = ClassUtil.getObjDeclaredFields(page,user);
		int total = userDao.getAllUserCount(params);
		List<User> userList = userDao.getAllUserForPage(params);
		if(userList!=null&&userList.size()>0)
		for(User u:userList){
			StringBuffer roleNames = null;
			List<UserRoleRel> relList = userRoleRelDAO.queryUserRoleRelByUserId(u.getId());
			if(relList!=null&&relList.size()>0){
				for(UserRoleRel rel:relList){
					if(roleNames==null){
						roleNames = new StringBuffer();
					}else{
						roleNames.append(",");	
					}
					roleNames.append(rel.getRoleName());
				}
			}
			System.out.println("roleNames===="+roleNames);
			u.setRoleNames(roleNames==null?"":roleNames.toString());
		}
		vo.setDataList(userList);
		vo.setDataCount(total);
		return vo;
	}

	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		user.setPassword(MD5.getInstance().getMd5(user.getPassword(),"UTF-8"));
		userDao.insertUser(user);
	}
	@Override
	public int modifyUser(User user) {
		// TODO Auto-generated method stub
		user.setUpdateTime(new Date());
		return userDao.updateUserById(user);
	}

	@Override
	public User findUserByUserId(long id) {
		// TODO Auto-generated method stub
		return userDao.getUserByUserId(id);
	}

	@Override
	public List<UserRoleRel> queryUserRoleRelByUserId(long userId) {
		// TODO Auto-generated method stub
		return userRoleRelDAO.queryUserRoleRelByUserId(userId);
	}

	@Override
	public int deleteUserRoleRelByUserId(long userId) {
		// TODO Auto-generated method stub
		return userRoleRelDAO.deleteUserRoleRelByUserId(userId);
	}

	@Override
	public int saveUserRoleRel(long userId, String roleIds,User loginUser){
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(roleIds)){
			return 0;
		}
		//删除
		List<UserRoleRel> list = new ArrayList<UserRoleRel>();
		for(String rid:roleIds.split(",")){
			UserRoleRel r = new UserRoleRel();
			Role role = roleDao.getRoleById(Long.parseLong(rid));
			if(role!=null){
				r.setUserId(userId);
				r.setRoleId(role.getId());
				r.setCreateTime(new Date());
				r.setCreateUser(loginUser.getId());
				r.setUserName(loginUser.getRealName());
				r.setRoleName(role.getRoleName());
				list.add(r);
			}
		}
		return userRoleRelDAO.batchInsertUserRoleRel(list);
	}

	@Override
	public Set<Integer> queryRoleMenuAuthorityByUserId(long userId){
		Set<Integer> nodeIdSet = new HashSet<Integer>();
		List<UserRoleRel> relList = userRoleRelDAO.queryUserRoleRelByUserId(userId);
		for(UserRoleRel role:relList){
			List<RoleMenuAuthority> rmaList = authorityDAO.queryRoleAuthorityByRoleId(role.getRoleId());
			for(RoleMenuAuthority a:rmaList){
				nodeIdSet.add(a.getMenuId());
			}
		}
		return nodeIdSet;
	}

	@Override
	public Map<String,MenuNode> queryMenuNodeByUserId(Set<Integer> set){
		 	Map<String,MenuNode> result = new HashMap<String,MenuNode>();
		 	MenuNode node = new MenuNode();
			node.setNodeIdSet(set);
			List<MenuNode> menuList  = menuDao.getMenuNodeList(node);
			for(MenuNode menu:menuList){
				result.put(menu.getNodeModel()+"_"+menu.getIconCls(), menu);
			}
			return result;
	}
}
