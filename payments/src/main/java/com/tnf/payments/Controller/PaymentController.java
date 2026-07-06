package com.tnf.payments.Controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tnf.payments.Entities.Payments;
import com.tnf.payments.Services.PaymentService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Payments processPayment(@RequestParam String orderId, @RequestParam String customerEmail, @RequestParam BigDecimal amount) {
        return paymentService.processPayment(orderId, customerEmail, amount);
    }

    @GetMapping("/{id}")
    public Payments getPayment(@PathVariable String id) {
        return paymentService.getPaymentById(id);
    }
}
