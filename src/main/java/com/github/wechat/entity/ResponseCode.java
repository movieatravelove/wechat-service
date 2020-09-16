package com.github.wechat.entity;

/**
 * @Author: zhang
 * @Date: 2019/12/12/012 11:12
 * @Description:
 */
public enum ResponseCode {

    SUCCESS(200, "SUCCESS"), ERROR(400, "ERROR"),
    NEED_LOGIN(100, "NEED_LOGIN"),
    ERROR_LOGIN(401, "你输入的账号和密码有误!!"),
    ERROR_CODE_LOGIN(402, "你输入的验证有误!!"),
    SESSION_FAIIL(101, "SESSION IS CLOSED"),
    ILLEGAL_ARGUMENT(202, "ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}