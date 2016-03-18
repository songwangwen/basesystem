package org.song.pojo;

import java.io.Serializable;

/**
 * Created by songwangwen on 2016/3/7.
 */
public class RespondDTO implements Serializable{
    /**
     * 返回值编码
     */
    private int code;
    /**
     * 返回值备注信息
     */
    private String msg;
    /**
     * 具体返回值
     */
    private Object data;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
