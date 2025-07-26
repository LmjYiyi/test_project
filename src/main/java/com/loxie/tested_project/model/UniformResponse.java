package com.loxie.tested_project.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UniformResponse<T> {
    @JsonProperty("return_code")
    private String returnCode;

    @JsonProperty("return_msg")
    private String returnMsg;

    private T data;

    // 构造函数
    public UniformResponse(String returnCode, String returnMsg, T data) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
        this.data = data;
    }

    // 静态工厂方法，用于快速创建成功和失败的响应
    public static <T> UniformResponse<T> success(T data) {
        return new UniformResponse<>("0", "请求处理成功", data);
    }

    public static <T> UniformResponse<T> fail(String code, String message) {
        return new UniformResponse<>(code, message, null);
    }

    // Getters and Setters
    public String getReturnCode() { return returnCode; }
    public void setReturnCode(String returnCode) { this.returnCode = returnCode; }
    public String getReturnMsg() { return returnMsg; }
    public void setReturnMsg(String returnMsg) { this.returnMsg = returnMsg; }
    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
}
