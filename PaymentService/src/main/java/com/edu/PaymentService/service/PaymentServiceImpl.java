package com.edu.PaymentService.service;

import com.edu.PaymentService.entity.TransactionDetails;
import com.edu.PaymentService.model.PaymentMode;
import com.edu.PaymentService.model.PaymentRequest;
import com.edu.PaymentService.model.PaymentResponse;
import com.edu.PaymentService.repository.TransactionDetailRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private TransactionDetailRepository transactionDetailRepository;

    @Override
    public Long doPayment(PaymentRequest paymentRequest) {
        log.info("INSIDE TRANSACTION REPOSITORY - Recording payment Details");

        TransactionDetails transactionDetails = TransactionDetails.builder()
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentDate(Instant.now())
                .orderId(paymentRequest.getOrderId())
                .paymentStatus("SUCCESS")
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount()).build();

        transactionDetailRepository.save(transactionDetails);

        log.info("TRANSACTION COMPLETED WITH ID: {}", transactionDetails.getId());

        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentDetails(long id) {
        log.info("Getting payment details for get order details");
        TransactionDetails transactionDetails = transactionDetailRepository.findByOrderId(id);

        return PaymentResponse.builder()
        .paymentId(transactionDetails.getId())
        .paymentDate(transactionDetails.getPaymentDate())
        .paymentStatus(transactionDetails.getPaymentStatus())
        .amount(transactionDetails.getAmount())
        .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
        .build();
    }
}
