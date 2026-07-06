package com.tnf.order_service.Entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentDTO {
    private String id;
    private String orderId;
    private String customerEmail;
    private BigDecimal amount;
    private String status;
    private String transactionId;
    private LocalDateTime paymentDate;
    public PaymentDTO() {
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
        return "PaymentDTO [id=" + id + ", orderId=" + orderId + ", customerEmail=" + customerEmail + ", amount="
                + amount + ", status=" + status + ", transactionId=" + transactionId + ", paymentDate=" + paymentDate
                + "]";
    }
    
    
}
