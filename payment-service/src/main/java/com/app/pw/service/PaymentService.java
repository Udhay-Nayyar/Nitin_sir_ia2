package com.app.pw.service;

import com.app.pw.entity.Payment;
import com.app.pw.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment processPayment(Payment payment) {

        // Simulate payment processing
        if (payment.getAmount() != null
                && payment.getAmount() > 0) {

            payment.setStatus("SUCCESS");

        } else {

            payment.setStatus("FAILED");
        }

        return paymentRepository.save(payment);
    }
}