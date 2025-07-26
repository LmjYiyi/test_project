package com.loxie.tested_project.entity;

import java.io.Serializable;

/**
 * 统一认证用户信息实体类
 */
public class TellerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ssicType;
    private String ssicId;
    private String username;
    private String email;
    private String phone;
    private String field1;
    private String field2; // 假设 field2 是一个JSON字符串，需要进一步解析
    private String field3;
    private Integer subField1;
    private Boolean subField2;
    private String status; // 新增 status 字段

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

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public Integer getSubField1() {
        return subField1;
    }

    public void setSubField1(Integer subField1) {
        this.subField1 = subField1;
    }

    public Boolean getSubField2() {
        return subField2;
    }

    public void setSubField2(Boolean subField2) {
        this.subField2 = subField2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TellerEntity{" +
                "ssicType='" + ssicType + '\'' +
                ", ssicId='" + ssicId + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", field1='" + field1 + '\'' +
                ", field2='" + field2 + '\'' +
                ", field3='" + field3 + '\'' +
                ", subField1=" + subField1 +
                ", subField2=" + subField2 +
                ", status='" + status + '\'' + // 添加 status 到 toString
                '}';
    }
}
