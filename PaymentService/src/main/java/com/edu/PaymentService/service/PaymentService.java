package com.edu.PaymentService.service;

import com.edu.PaymentService.model.PaymentRequest;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);
}
