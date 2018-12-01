package com.imooc.miaosha.domain;

import java.io.Serializable;

public class CodeMsg implements Serializable {

    private int code;
    private String msg;

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }



    //通用的错误码  5001xx
    public static CodeMsg SUCCESS = new CodeMsg(0, "成功");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常: %s");

    //登陆模块 5002xx
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "登陆密码不能为空");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500213, "手机号格式错误");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "手机号不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误");

    //订单模块 5004xx
    public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(500400, "订单不存在");

    //秒杀模块 5005xx
    public static CodeMsg MIAO_SHA_OVER = new CodeMsg(500500, "商品已经秒杀完毕");
    public static CodeMsg REPEATE_MIAOSHA = new CodeMsg(500501, "商品重复秒杀");

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg,args);
        return new CodeMsg(code, message);
    }

    @Override
    public String toString() {
        return "CodeMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
