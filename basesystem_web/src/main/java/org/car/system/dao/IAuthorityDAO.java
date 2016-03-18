package org.car.system.dao;

import java.util.List;

import org.car.system.model.RoleMenuAuthority;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthorityDAO {
	/**
	 * 批量插入角色权限
	 * @param list 
	 * @return
	 */
	public int batchInsertRoleAuthority(List<RoleMenuAuthority> list);
	
	/**
	 * 编辑角色权限前，先清空之前的数据
	 * @param roleId
	 * @return
	 */
	public int deleteRoleAuthorityByRoleId(long roleId);
	
	
	/**
	 * 查找一个角色的权限
	 * @param roleId
	 * @return List<RoleMenuAuthority>
	 */
	public List<RoleMenuAuthority> queryRoleAuthorityByRoleId(long roleId);
}
