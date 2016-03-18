package org.car.system.dao;

import java.util.List;
import java.util.Map;

import org.car.system.model.Role;
import org.springframework.stereotype.Repository;

/**
 * 用户管理持久层接口，由spring完成实现
 * @author songwangwen
 *
 */
@Repository
public interface IRoleDAO {

	/**
	 * 查询所有角色资料
	 * @param params 分页及查询过滤条件
	 * @return List<Role>
	 * @see Role
	 */
	public List<Role> getAllRoleForPage(Map<String, Object> params);
	/**
	 * 查询所有角色资料
	 * @param params 分页及查询过滤条件
	 * @return List<Role>
	 * @see Role
	 */
	public List<Role> getAllRole();
	
	/**
	 * 查询所有角色的总数，用于分页使用
	 * @param params 分页及查询过滤条件
	 * @return int 总记录数
	 */
	public int getAllRoleSize(Map<String, Object> params);
	/**
	 * 新增角色
	 * @param role 角色实体
	 * @see Role
	 */
	public int insertRole(Role role);
	/**
	 * 编辑角色
	 * @param role 角色实体
	 * @see Role
	 */
	public int updateRole(Role role);

	/**
	 * 根据用户id查询得到用户的角色列表
	 * @param id 用户id
	 * @return List<Role>
	 * @see Role
	 */
	public List<Role> getRoleListByUserId(long id);
	
	/**
	 * 根据角色ID查询实体类
	 * @param id
	 * @return Role
	 * @see Role
	 */
	public Role getRoleById(long id);
}
