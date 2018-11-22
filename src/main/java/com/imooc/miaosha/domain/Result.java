package com.imooc.miaosha.domain;

import java.io.Serializable;

public class Result implements Serializable {
    private static final long serialVersionUID = 1L;
    private Boolean success;
    private Object msg;
    private Integer code = 0;

    public Result(Boolean success, Object msg) {
        if(msg.getClass() == CodeMsg.class) {
            CodeMsg codeMsg = (CodeMsg) msg;
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
        } else {
            this.msg = msg;
        }
        this.success = success;
    }

    public Result(Object msg) {
        if(msg.getClass() == CodeMsg.class) {
            CodeMsg codeMsg = (CodeMsg) msg;
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
        } else {
            this.msg = msg;
        }
        this.success = true;
    }
    public Result() {}

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public static Result buildError(Object errorMsg){
        return new Result(false, errorMsg);
    }

    public static Result buildSuccess(Object msg){
        return new Result(true, msg);
    }


}
