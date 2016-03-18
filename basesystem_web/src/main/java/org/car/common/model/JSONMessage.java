package org.car.common.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JSONMessage {
	protected static final Log log = LogFactory.getLog(JSONMessage.class); 
	private boolean flag = false;//是否成功
	private Object msg;//参数
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public Object getMsg() {
		return msg;
	}
	public void setMsg(Object msg) {
		this.msg = msg;
	}
}
