package org.car.common.model;

import java.util.Date;

public abstract class BaseObject {
	protected Date createTime;		//创建时间
	protected Date updateTime;		//更新时间
	protected long createUser;		//创建人员id
	protected long updateUser;		//更新人员id
	protected long version;		//版本，仅用于更新使用，无实际意义
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(long createUser) {
		this.createUser = createUser;
	}
	public long getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(long updateUser) {
		this.updateUser = updateUser;
	}
	public long getVersion() {
		return version;
	}
	public void setVersion(long version) {
		this.version = version;
	}
}
