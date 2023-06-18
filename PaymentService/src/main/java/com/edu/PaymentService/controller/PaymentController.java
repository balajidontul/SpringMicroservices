package com.edu.PaymentService.controller;

import com.edu.PaymentService.model.PaymentRequest;
import com.edu.PaymentService.model.PaymentResponse;
import com.edu.PaymentService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest){
        return new ResponseEntity<>( paymentService.doPayment(paymentRequest), HttpStatus.CREATED );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentDetails(@PathVariable long id){
        PaymentResponse paymentResponse = paymentService.getPaymentDetails(id);

        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);

    }

}
