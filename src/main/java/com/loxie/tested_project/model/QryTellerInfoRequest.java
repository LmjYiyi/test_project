package com.loxie.tested_project.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 统一认证用户信息查询请求模型
 */
public class QryTellerInfoRequest {

    @JsonProperty("ssicType")
    @NotBlank(message = "认证类型不能为空")
    private String ssicType;

    @JsonProperty("ssicId")
    @NotBlank(message = "认证号不能为空")
    private String ssicId;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("biz_content")
    @NotNull(message = "业务内容不能为空")
    @Valid
    private BizContent bizContent;

    // Getters and Setters
    public String getSsicType() {
        return ssicType;
    }

    public void setSsicType(String ssicType) {
        this.ssicType = ssicType;
    }

    public String getSsicId() {
        return ssicId;
    }

    public void setSsicId(String ssicId) {
        this.ssicId = ssicId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BizContent getBizContent() {
        return bizContent;
    }

    public void setBizContent(BizContent bizContent) {
        this.bizContent = bizContent;
    }

    @Override
    public String toString() {
        return "QryTellerInfoRequest{" +
                "ssicType='" + ssicType + '\'' +
                ", ssicId='" + ssicId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", bizContent=" + bizContent +
                '}';
    }
}
