package org.car.system.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.car.common.model.BaseObject;

/**
 * 系统使用用户信息
 * 对应SYSTEM_USER数据表
 * @author songwangwen
 */
public class User extends BaseObject{
	private Long id;//主键ID
	private String userName;//用户名
	private String password;//密码
	private String realName;//真实姓名
	private String lastLoginIp;//最后登录IP
	private Date lastLoginTime;//最后登录时间
	private Boolean enabled;//停启用；1.启用
	private Boolean deleted;//删除标志；1.删除
	
	//扩展参数，角色名称集合
	private String roleNames;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public String getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
}
