package org.car.system.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.car.common.model.JQTableVO;
import org.car.common.model.PageDTO;
import org.car.system.model.MenuNode;
import org.car.system.model.User;
import org.car.system.model.UserRoleRel;

public interface IUserService {
	/**
	 * 分页查询SYSTEM_USER数据表
	 * @param page 分页信息
	 * @param user 过滤条件
	 * @return
	 */
	public JQTableVO queryAllUserForPage(PageDTO page, User user);
	
	/**
	 * 根据用户名查询得到登录用户，未查到则返回null
	 * @param userName 用户名
	 * @return User
	 * @see User
	 */
	public User findUserByUserName(String userName);
	
	/**
	 * 新增用户
	 * @param user
	 */
	public void saveUser(User user);
	/**
	 * 编辑用户
	 * @param user
	 */
	public int modifyUser(User user);
	
	/**
	 * 根据用户ID查询得到用户，未查到则返回null
	 * @param id
	 * @return User
	 * @see User
	 */
	public User findUserByUserId(long id);
	
	
	/**
	 * 查找某用户的角色信息
	 * @param userId
	 * @return
	 */
	public List<UserRoleRel> queryUserRoleRelByUserId(long userId);

	
	/**
	 * 删除当前用户的所有角色信息
	 * @param userId
	 * @return
	 */
	public int deleteUserRoleRelByUserId(long userId);
	/**
	 * 批量插入用户角色
	 * @param userId 用户ID
	 * @param roleIds 角色ID集合，逗号分隔
	 * @param User 当前登录的操作员
	 * @return
	 */
	public int saveUserRoleRel(long userId, String roleIds, User loginUser);
	
	/**
	 * 通过userId找到该用户可访问的菜单id集合
	 * @param userId
	 * @return
	 */
	public Set<Integer> queryRoleMenuAuthorityByUserId(long userId);
	/**
	 * 根据用户id查询得到用户可访问的菜单项
	 * @param set 用户可访问的菜单ID集合
	 * @return Map<String,MenuNode>
	 */
	public Map<String,MenuNode> queryMenuNodeByUserId(Set<Integer> set);
}
