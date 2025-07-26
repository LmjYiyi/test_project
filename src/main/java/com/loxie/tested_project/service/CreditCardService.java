package com.loxie.tested_project.service;

import com.loxie.tested_project.model.CreditCardTransferRequest;
import com.loxie.tested_project.model.ResponseData;

public interface CreditCardService {
    ResponseData transferPay(CreditCardTransferRequest request);
}
