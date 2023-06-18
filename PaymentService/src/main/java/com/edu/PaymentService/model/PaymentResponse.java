package com.edu.PaymentService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private long paymentId;
    private long orderId;
    private long amount;
    private String paymentStatus;
    private PaymentMode paymentMode;
    private Instant paymentDate;
}
