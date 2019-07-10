package com.yuchen.catalog.domain.common;

import com.yuchen.catalog.common.constants.EmReturnCode;

import java.io.Serializable;

/**
 * 通用返回实体
 */
public class ReturnJson implements Serializable {
    private String msg;
    private int retcode;
    private Object retObj;
    private boolean success;
    public final static ReturnJson Success = new ReturnJson("操作成功", EmReturnCode.SUCCESS.getValue(), null,true);
    public final static ReturnJson Faild = new ReturnJson("操作失败", EmReturnCode.FAIL.getValue(), null,false);

    public ReturnJson(String msg, int retcode, Object retObj,boolean success) {
        super();
        this.msg = msg;
        this.retcode = retcode;
        this.retObj = retObj;
        this.success = success;
    }

    public ReturnJson(String msg, Object retObj) {
        super();
        this.msg = msg;
        this.retcode = EmReturnCode.SUCCESS.getValue();
        this.retObj = retObj;
        this.success = true;
    }

    public ReturnJson(String msg,boolean success) {
        super();
        this.msg = msg;
        this.retcode = EmReturnCode.SUCCESS.getValue();
        this.retObj = null;
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public Object getRetObj() {
        return retObj;
    }

    public void setRetObj(Object retObj) {
        this.retObj = retObj;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
