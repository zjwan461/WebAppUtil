package com.itsu.entity;

import java.time.LocalDateTime;

/**
 * @author 苏犇
 * @date 2019/7/14 11:28
 */
public class JsonResult {

    private static final String DEFAULT_SUCCESS_CODE = "200";
    private static final String DEFAULT_ERROR_CODE = "500";
    private static final String DEFAULT_SUCCESS_MSG = "success";
    private static final String DEFAULT_ERROR_MSG = "error";

    private String code;
    private String msg;
    private LocalDateTime time;
    private String requestUrl;
    private String error;
    private Object data;

    public JsonResult() {
    }

    public JsonResult(String code, String msg, LocalDateTime time, String requestUrl, String error, Object data) {
        this.code = code;
        this.msg = msg;
        this.time = time;
        this.requestUrl = requestUrl;
        this.error = error;
        this.data = data;
    }

    public static JsonResult ok() {
        return new JsonResult(DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MSG, LocalDateTime.now(), null, null, null);
    }

    public static JsonResult ok(Object data) {
        return new JsonResult(DEFAULT_SUCCESS_CODE, DEFAULT_SUCCESS_MSG, LocalDateTime.now(), null, null, data);
    }

    public static JsonResult ok(String msg) {
        return new JsonResult(DEFAULT_SUCCESS_CODE, msg, LocalDateTime.now(), null, null, null);
    }

    public static JsonResult ok(String code, String msg, LocalDateTime time, String requestUrl, Object data) {
        return new JsonResult(code, msg, time, requestUrl, null, data);
    }

    public static JsonResult error() {
        return new JsonResult(DEFAULT_ERROR_CODE, DEFAULT_ERROR_MSG, LocalDateTime.now(), null, null, null);
    }

    public static JsonResult error(String msg) {
        return new JsonResult(DEFAULT_ERROR_CODE, msg, LocalDateTime.now(), null, null, null);
    }

    public static JsonResult error(String code, String msg, LocalDateTime time, String requestUrl, String error) {
        return new JsonResult(code, msg, time, requestUrl, error, null);
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getError() {
        return error;
    }

    public Object getData() {
        return data;
    }
}
