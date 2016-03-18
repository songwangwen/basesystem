package org.car.system.model;

import org.car.common.model.BaseObject;

/**
 * @author songwangwen
 * 用户与角色关系对应表system_user_role_rel
 */
public class UserRoleRel extends BaseObject{
	private long  userId;		//用户id
	private long  roleId;		//角色id
	private String userName;	//用户名称
	private String roleName;	//角色名称
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
