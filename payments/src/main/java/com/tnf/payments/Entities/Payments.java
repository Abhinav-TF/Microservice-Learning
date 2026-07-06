package com.tnf.payments.Entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "payments")
public class Payments {
    @Id
    private String id;
    private String orderId;
    private String customerEmail;
    private BigDecimal amount;
    private String status;
    private String transactionId;
    private LocalDateTime paymentDate;

    public Payments() {
        this.paymentDate = LocalDateTime.now();
    }

    public Payments(String id, String orderId, String customerEmail, BigDecimal amount, String status, String transactionId, LocalDateTime paymentDate) {
        super();
        this.id = id;
        this.orderId = orderId;
        this.customerEmail = customerEmail;
        this.amount = amount;
        this.status = status;
        this.transactionId = transactionId;
        this.paymentDate = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    @Override
    public String toString() {
        return "Payments [id=" + id + ", orderId=" + orderId + ", customerEmail=" + customerEmail + ", amount=" + amount
                + ", status=" + status + ", transactionId=" + transactionId + ", paymentDate=" + paymentDate + "]";
    }

}
