package com.loxie.tested_project.controller;

import com.loxie.tested_project.model.CreditCardTransferRequest;
import com.loxie.tested_project.model.ResponseData;
import com.loxie.tested_project.service.CreditCardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import org.springframework.web.bind.MethodArgumentNotValidException; // 导入这个类
import org.springframework.web.bind.annotation.ExceptionHandler; // 导入这个类
import org.springframework.web.bind.annotation.ResponseStatus; // 导入这个类
import org.springframework.http.HttpStatus; // 导入这个类

@Slf4j
@Validated
@RestController
@RequestMapping("/api/creditcard")
public class CreditCardController {

    @Autowired
    private CreditCardService creditCardService;

    @PostMapping("/transferPay/V1")
    public ResponseData transferPay(
            @RequestHeader("X-Request-App") String xRequestApp,
            @RequestHeader("X-Request-Id") String xRequestId,
            @Valid @RequestBody CreditCardTransferRequest request) {

        log.info("Received credit card transfer request, X-Request-Id: {}", xRequestId);

        // 请求头校验 - 只需非空
        if (xRequestApp == null || xRequestApp.isEmpty()) {
            log.error("Missing X-Request-App header");
            return ResponseData.fail("ERR_HEADER_APP", "X-Request-App header is missing or empty", xRequestId, null,
                    null, null);
        }
        if (xRequestId == null || xRequestId.isEmpty()) {
            log.error("Missing X-Request-Id header");
            return ResponseData.fail("ERR_HEADER_ID", "X-Request-Id header is missing or empty", null, null, null,
                    null);
        }

        try {
            ResponseData response = creditCardService.transferPay(request);
            log.info("Credit card transfer completed successfully, X-Request-Id: {}", xRequestId);
            return response;
        } catch (Exception e) {
            log.error("Credit card transfer failed, X-Request-Id: {}, error: {}", xRequestId, e.getMessage(), e);
            return ResponseData.fail("ERR_SYSTEM", "System error occurred", xRequestId, null, null, null);
        }
    }

    // 新增一个异常处理器来捕获验证错误
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseData handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = "";
            if (error instanceof org.springframework.validation.FieldError) {
                fieldName = ((org.springframework.validation.FieldError) error).getField();
            }
            String errorMessage = error.getDefaultMessage();
            errors.append(fieldName).append(": ").append(errorMessage).append("; ");
        });
        log.error("Validation failed: {}", errors.toString());
        return ResponseData.fail("ERR_VALIDATION", "请求参数校验失败: " + errors.toString(), null, null, null, null);
    }
}
