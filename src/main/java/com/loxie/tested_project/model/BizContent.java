package com.loxie.tested_project.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

/**
 * 业务内容模型
 */
public class BizContent {

    @JsonProperty("serviceName")
    @NotBlank(message = "站点名称不能为空")
    private String serviceName;

    @JsonProperty("randomKey")
    @NotBlank(message = "随机密钥不能为空")
    private String randomKey;

    @JsonProperty("timestamp")
    private String timestamp;

    // Getters and Setters
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getRandomKey() {
        return randomKey;
    }

    public void setRandomKey(String randomKey) {
        this.randomKey = randomKey;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "BizContent{" +
                "serviceName='" + serviceName + '\'' +
                ", randomKey='" + randomKey + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
