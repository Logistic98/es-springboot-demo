package com.es.demo.utils;

import lombok.Getter;
import lombok.ToString;

/**
 * 数据统一下发实体(统一接口响应格式的组件）
 */

@Getter
@ToString
public class ResponseBean<T> {
    private int code;
    private String msg;
    private T data;

    // 成功操作
    public static <E> ResponseBean<E> success(E data) {
        return new ResponseBean<E>(ResultCode.SUCCESS, data);
    }

    // 失败操作
    public static <E> ResponseBean<E> failure(E data) {
        return new ResponseBean<E>(ResultCode.FAILURE, data);
    }

    // 设置为 private
    private ResponseBean(ResultCode result, T data) {
        this.code = result.code;
        this.msg = result.msg;
        this.data = data;
    }

    // 设置 private
    private static enum ResultCode {
        SUCCESS(200, "请求成功"),
        FAILURE(500, "请求失败");

        ResultCode(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        private int code;
        private String msg;
    }
}