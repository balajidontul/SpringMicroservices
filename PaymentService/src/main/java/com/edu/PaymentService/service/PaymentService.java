package com.edu.PaymentService.service;

import com.edu.PaymentService.model.PaymentRequest;
import com.edu.PaymentService.model.PaymentResponse;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetails(long id);
}
