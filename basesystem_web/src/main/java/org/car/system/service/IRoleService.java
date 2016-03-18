package org.car.system.service;

import java.util.List;

import org.car.common.model.JQTableVO;
import org.car.common.model.PageDTO;
import org.car.system.model.Role;
import org.car.system.model.RoleMenuAuthority;
import org.car.system.model.UserRoleRel;

public interface IRoleService {
	/**
	 * 分页查询SYSTEM_ROLE数据表
	 * @param page 分页信息
	 * @param role 过滤条件
	 * @return
	 */
	public JQTableVO queryAllRoleForPage(PageDTO page, Role role);
	
	/**
	 * 新增角色信息
	 * @param user
	 */
	public int save(Role role);
	
	/**
	 * 编辑角色信息
	 * @param user
	 */
	public int edit(Role role);
	
	/**
	 * 根据角色ID查询得到角色信息
	 * @param id
	 * @return
	 */
	public Role findRoleByRoleId(long id);
	
	/**
	 * 配置角色菜单权限
	 * @param roleId 待配置的角色ID
	 * @param authority 可访问的菜单项ID集合，逗号分隔
	 * @param userId 当前登录人员的id
	 * @return
	 */
	public int configureMenuAuthority(long roleId, String authority, long userId);
	
	
	/**
	 * 返回所有未被删除的角色信息
	 * @return
	 */
	public List<Role> queryAllRole();

}
