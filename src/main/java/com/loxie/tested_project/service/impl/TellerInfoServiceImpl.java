package com.loxie.tested_project.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.loxie.tested_project.entity.TellerEntity;
import com.loxie.tested_project.model.QryTellerInfoRequest;
import com.loxie.tested_project.model.TellerInfoData;
import com.loxie.tested_project.model.UniformResponse; // 导入 UniformResponse
import com.loxie.tested_project.repository.TellerRepository;
import com.loxie.tested_project.service.TellerInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 统一认证用户信息查询 Service 实现类
 */
@Service
public class TellerInfoServiceImpl implements TellerInfoService {

    private static final Logger logger = LoggerFactory.getLogger(TellerInfoServiceImpl.class);

    @Autowired
    private TellerRepository tellerRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public UniformResponse<TellerInfoData> queryTellerInfo(String appName, String appId, QryTellerInfoRequest request) {
        logger.info("开始查询用户信息，应用名: {}, 应用ID: {}, 认证类型: {}, 认证号: {}",
                appName, appId, request.getSsicType(), request.getSsicId());

        Optional<TellerEntity> tellerOpt = Optional.empty();
        switch (request.getSsicType()) {
            case "1": // 统一认证号
                logger.info("正在处理统一认证号查询...");
                tellerOpt = tellerRepository.findBySsicTypeAndSsicId("1", request.getSsicId());
                break;
            case "2": // 身份证
                logger.info("正在处理身份证信息查询...");
                tellerOpt = tellerRepository.findBySsicTypeAndSsicId("2", request.getSsicId());
                break;
            case "3": // 香港身份证
                logger.info("正在处理香港身份证查询...");
                tellerOpt = tellerRepository.findBySsicTypeAndSsicId("3", request.getSsicId());
                break;
            case "username": // 用户名
                logger.info("正在处理用户名查询...");
                tellerOpt = tellerRepository.findByUsername(request.getUsername());
                break;
            case "email": // 邮箱
                logger.info("正在处理邮箱查询...");
                tellerOpt = tellerRepository.findByEmail(request.getEmail());
                break;
            case "phone": // 手机号
                logger.info("正在处理手机号查询...");
                tellerOpt = tellerRepository.findByPhone(request.getPhone());
                break;
            default:
                // 理论上这里不会被执行到，因为ssicType的合法性已在Controller层校验
                logger.error("未知的认证类型: {}", request.getSsicType());
                // 即使Controller层已校验，这里也应有防御性处理
                return UniformResponse.fail("1001", "无效的认证类型");
        }

        if (tellerOpt.isPresent()) {
            TellerEntity teller = tellerOpt.get();

            // 检查用户激活状态
            if ("0".equals(teller.getStatus())) {
                logger.warn("用户未激活: {}", teller.getSsicId());
                return UniformResponse.fail("1004", "用户未激活");
            }

            TellerInfoData.Field2 field2 = null;
            try {
                // 尝试从 field2 字符串解析为 Field2 对象
                field2 = objectMapper.readValue(teller.getField2(), TellerInfoData.Field2.class);
            } catch (Exception e) {
                logger.error("解析 field2 失败: {}", teller.getField2(), e);
                // 如果解析失败，使用 TellerEntity 中的 subField1 和 subField2
                field2 = new TellerInfoData.Field2(
                        teller.getSubField1() != null ? teller.getSubField1() : 0,
                        teller.getSubField2() != null ? teller.getSubField2() : false);
            }

            return UniformResponse.success(new TellerInfoData(
                    teller.getField1(),
                    field2,
                    teller.getField3()));
        } else {
            logger.warn("数据库中未找到用户信息，认证类型: {}, 认证号: {}", request.getSsicType(), request.getSsicId());
            return UniformResponse.fail("1001", "校验身份信息失败"); // 未找到用户信息，返回1001
        }
    }

    @Override
    public boolean validateTellerIdentity(String ssicId) {
        logger.info("验证用户身份信息: {}", ssicId);
        Optional<TellerEntity> tellerOpt = tellerRepository.findBySsicId(ssicId);
        return tellerOpt.isPresent();
    }
}
