package com.app.pw.controller;

import com.app.pw.entity.Payment;
import com.app.pw.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<Payment> processPayment(
            @RequestBody Payment payment) {

        return ResponseEntity.ok(
                paymentService.processPayment(payment)
        );
    }
}