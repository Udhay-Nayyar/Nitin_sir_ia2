package com.app.pw.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "payment-service",
        url = "http://localhost:8084"
)
public interface PaymentClient {

    @PostMapping("/payments")
    PaymentResponse processPayment(
            @RequestBody PaymentRequest request,
            @RequestHeader("Authorization") String authorization
    );

    record PaymentRequest(
            Long orderId,
            Double amount
    ) {}

    record PaymentResponse(
            Long id,
            Long orderId,
            Double amount,
            String status
    ) {}
}