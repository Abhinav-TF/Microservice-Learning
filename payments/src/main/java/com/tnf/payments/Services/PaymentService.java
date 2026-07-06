package com.tnf.payments.Services;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tnf.payments.Entities.Payments;
import com.tnf.payments.Exceptions.PaymentNotFoundException;
import com.tnf.payments.Repositories.PaymentRepository;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    public Payments processPayment(String orderId, String customerEmail, BigDecimal amount) {
        Payments payment = new Payments();
        payment.setOrderId(orderId);
        payment.setCustomerEmail(customerEmail);
        payment.setAmount(amount);

        if (Math.random() > 0.0) {
            payment.setStatus("COMPLETED");
            payment.setTransactionId("TXN-" + UUID.randomUUID().toString());
        } else {
            payment.setStatus("FAILED");
        }
        return paymentRepository.save(payment);
    }

    public Payments getPaymentById(String id) {
        return paymentRepository.findById(id).orElseThrow(() -> new PaymentNotFoundException(id));
    }
}
