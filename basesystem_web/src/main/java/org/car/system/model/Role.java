package org.car.system.model;

import java.util.Date;

import org.car.common.model.BaseObject;

/**
 * 
 * 角色实体类，对应表 system_role
 * @author songwangwen
 *
 */
public class Role extends BaseObject{
    private long id;
    private String roleName;// '角色名称',
    private String remark ;// '备注信息',
    private Boolean deleted;//'删除标志位'
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
}
