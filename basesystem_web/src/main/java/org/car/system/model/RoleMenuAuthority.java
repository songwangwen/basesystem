package org.car.system.model;

import org.car.common.model.BaseObject;

/**
 * @author songwangwen
 * 菜单访问权限控制实体类,指定某一角色可以访问的菜单项目
 * system_menu_authority
 */
public class RoleMenuAuthority extends BaseObject{
	private long id;
	private long roleId;
	private int menuId;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
}
