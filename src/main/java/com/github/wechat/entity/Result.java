package com.github.wechat.entity;

import lombok.Getter;

/**
 * 返回结果实体类
 *
 */
@Getter
public class Result {
    private int code;
    private String message;
    private Object data;

    private Result setResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
        return this;
    }

    public Result success() {
        return setResult(200, "success", null);
    }

    public Result success(Object data) {
        return setResult(200, "success", data);
    }

    public Result error(String message, Object data) {
        return setResult(500, message, data);
    }

    public Result error(int code, String message, Object data) {
        return setResult(code, message, data);
    }

    public Result error(int code, String message) {
        return setResult(code, message, null);
    }
    
    public Result error(String message) {
        return setResult(500, message, null);
    }
}