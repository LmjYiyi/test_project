package com.loxie.tested_project.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 统一认证用户信息查询响应数据模型
 */
public class TellerInfoData {

    @JsonProperty("field1")
    private String field1;

    @JsonProperty("field2")
    private Field2 field2;

    @JsonProperty("field3")
    private String field3;

    public TellerInfoData() {
    }

    public TellerInfoData(String field1, Field2 field2, String field3) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }

    // Getters and Setters
    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public Field2 getField2() {
        return field2;
    }

    public void setField2(Field2 field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    /**
     * 嵌套的内部类，用于表示 field2
     */
    public static class Field2 {
        @JsonProperty("sub_field1")
        private int subField1;
        @JsonProperty("sub_field2")
        private boolean subField2;

        public Field2() {
        }

        public Field2(int subField1, boolean subField2) {
            this.subField1 = subField1;
            this.subField2 = subField2;
        }

        // Getters and Setters
        public int getSubField1() {
            return subField1;
        }

        public void setSubField1(int subField1) {
            this.subField1 = subField1;
        }

        public boolean isSubField2() {
            return subField2;
        }

        public void setSubField2(boolean subField2) {
            this.subField2 = subField2;
        }
    }
}
