package com.loxie.tested_project.service;

import com.loxie.tested_project.model.QryTellerInfoRequest;
import com.loxie.tested_project.model.TellerInfoData;
import com.loxie.tested_project.model.UniformResponse; // 导入 UniformResponse

/**
 * 统一认证用户信息查询 Service 接口
 */
public interface TellerInfoService {

    /**
     * 根据认证类型和认证号查询用户信息
     * 
     * @param appName 请求应用名
     * @param appId   请求应用号
     * @param request 请求参数
     * @return 包含用户信息数据或错误信息的统一响应
     */
    UniformResponse<TellerInfoData> queryTellerInfo(String appName, String appId, QryTellerInfoRequest request);

    /**
     * 验证用户身份信息是否存在
     * 
     * @param ssicId 认证号
     * @return 如果身份信息存在则返回true，否则返回false
     */
    boolean validateTellerIdentity(String ssicId);
}
