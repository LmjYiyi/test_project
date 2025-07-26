package com.loxie.tested_project.controller;

import com.loxie.tested_project.model.QryTellerInfoRequest;
import com.loxie.tested_project.model.TellerInfoData;
import com.loxie.tested_project.model.UniformResponse;
import com.loxie.tested_project.service.TellerInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 统一认证用户信息查询 Controller
 * 对应接口: UniformTeller.qryTellerInfo
 */
@RestController
@RequestMapping("/api/aam/uniformteller")
@Validated // 启用对方法参数的校验
public class UniformTellerController {

    private static final Logger logger = LoggerFactory.getLogger(UniformTellerController.class);

    @Autowired
    private TellerInfoService tellerInfoService;

    /**
     * 统一认证用户信息查询接口
     *
     * @param appName     请求应用名 (从请求头 X-Request-App 获取)
     * @param appId       请求应用号 (从请求头 X-Request-Id 获取)
     * @param requestBody 请求体，包含用户标识和业务内容
     * @return 返回一个包含处理结果的 ResponseEntity
     */
    @PostMapping("/qrytellerInfo/V1")
    public ResponseEntity<UniformResponse<TellerInfoData>> qryTellerInfo(
            @RequestHeader("X-Request-App") @NotBlank(message = "请求头 X-Request-App 不能为空") String appName,
            @RequestHeader("X-Request-Id") @NotBlank(message = "请求头 X-Request-Id 不能为空") String appId,
            @Valid @RequestBody QryTellerInfoRequest requestBody) {

        logger.info("接收到来自应用 {} (ID: {}) 的请求", appName, appId);
        logger.debug("请求参数: {}", requestBody);

        // 调用 Service 层验证用户身份
        // 根据接口文档，ssicType是必输字段，但validateTellerIdentity只接受ssicId
        // 这里需要根据实际业务逻辑调整，如果ssicType是验证身份的依据，则需要传入ssicType
        // 暂时保持原样，只用ssicId进行验证
        // 校验 ssicType 的合法取值范围
        String ssicType = requestBody.getSsicType();
        if (!("1".equals(ssicType) || "2".equals(ssicType) || "3".equals(ssicType))) {
            logger.warn("无效的认证类型: {}", ssicType);
            return ResponseEntity.ok(UniformResponse.fail("1001", "无效的认证类型"));
        }

        // 调用 Service 层查询用户信息，Service层会处理身份验证、激活状态和查询结果
        UniformResponse<TellerInfoData> response = tellerInfoService.queryTellerInfo(appName, appId, requestBody);

        if ("0".equals(response.getReturnCode())) {
            logger.info("用户信息查询成功: {}", response.getData().getField1());
        } else {
            logger.warn("用户信息查询失败，返回码: {}, 返回信息: {}", response.getReturnCode(), response.getReturnMsg());
        }
        return ResponseEntity.ok(response);
    }

    /**
     * 处理参数校验异常
     * 当 @Valid 或 @Validated 注解的参数校验失败时，会抛出 MethodArgumentNotValidException
     *
     * @param ex MethodArgumentNotValidException 异常对象
     * @return 统一的错误响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK) // 保持HTTP状态码为200，但返回业务错误码
    public ResponseEntity<UniformResponse<TellerInfoData>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<String> errors = result.getAllErrors().stream()
                .map(error -> {
                    if (error instanceof FieldError) {
                        return ((FieldError) error).getDefaultMessage();
                    }
                    return error.getDefaultMessage();
                })
                .collect(Collectors.toList());

        String errorMessage = String.join("; ", errors);
        logger.error("参数校验失败: {}", errorMessage);
        return ResponseEntity.ok(UniformResponse.fail("1003", errorMessage));
    }
}
