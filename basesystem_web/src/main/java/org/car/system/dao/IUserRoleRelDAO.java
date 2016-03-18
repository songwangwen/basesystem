package org.car.system.dao;

import java.util.List;

import org.car.system.model.UserRoleRel;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRoleRelDAO {
	
	/**
	 * 批量插入用户角色信息
	 * @param list
	 * @return
	 */
	public int batchInsertUserRoleRel(List<UserRoleRel> list);
	/**
	 * 根据角色ID批量删除用户角色信息
	 * @param roleId
	 * @return
	 */
	public int deleteUserRoleRelByRoleId(long roleId);
	/**
	 * 根据用户ID批量删除用户角色信息
	 * @param userId
	 * @return
	 */
	public int deleteUserRoleRelByUserId(long userId);
	/**
	 * 根据角色ID查询用户角色信息
	 * @param roleId
	 * @return
	 */
	public List<UserRoleRel> queryUserRoleRelByRoleId(long roleId);
	/**
	 * 根据用户ID查询用户角色信息
	 * @param userId
	 * @return
	 */
	public List<UserRoleRel> queryUserRoleRelByUserId(long userId);
}
