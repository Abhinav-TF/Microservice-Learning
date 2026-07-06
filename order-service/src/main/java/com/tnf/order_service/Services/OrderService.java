package com.tnf.order_service.Services;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.tnf.order_service.Entities.Order;
import com.tnf.order_service.Entities.PaymentDTO;
import com.tnf.order_service.Entities.ProductDTO;
import com.tnf.order_service.Repositories.OrderRepository;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);


    @Autowired
    private OrderRepository orderRepository;
    // @Autowired
    // private WebClient.Builder webClientBuilder;
    @Autowired
    WebClient webClient;

    public Order placeOrder(Order order) {

        logger.info("Starting order placement for product: {}", order.getProductId());

        try{
            if(order.getOrderDate() == null){
                order.setOrderDate(java.time.LocalDateTime.now());
            }

            //Validate product exists, wrong way
            //String productServiceURL = "http://localhost:8081/api/products/" + order.getProductId();

            ProductDTO product = fetchProduct(order.getProductId());

            //Correct way
            // String productServiceURL = "http://PRODUCT-SERVICE/api/products/" + order.getProductId();
            
            // ProductDTO product = webClient
            //         .get()
            //         .uri(productServiceURL)
            //         .retrieve()
            //         .bodyToMono(ProductDTO.class)
            //         .block();
            
            if (product == null) {
                logger.error("Product not found for productId: {}", order.getProductId());
                order.setStatus("FAILED");
                order.setTotalPrice(BigDecimal.ZERO);
                order.setProductName("Unknown Product");
                return orderRepository.save(order);
            }

            logger.info("Product found: {} - Price: INR {}", product.getName(), product.getPrice());

            BigDecimal total = product.getPrice().multiply(BigDecimal.valueOf(order.getQuantity()));
            order.setTotalPrice(total);
            // order.setStatus("COMPLETED");
            order.setProductName(product.getName());

            order.setStatus("PENDING");
            Order savedOrder = orderRepository.save(order);
            logger.info("Order saved with ID: {} ", savedOrder.getId());

            PaymentDTO payment = processPayment(savedOrder);

            if(payment != null && "COMPLETED".equals(payment.getStatus())) {
                savedOrder.setStatus("COMPLETED");
                savedOrder.setPaymentTransactionId(payment.getTransactionId());
                savedOrder.setPaymentStatus(payment.getStatus());
                logger.info("Payment completed: {}", payment.getTransactionId());
            } else {
                savedOrder.setStatus("FAILED");
                savedOrder.setPaymentStatus("FAILED");
                logger.error("Payment failed for order: {}", savedOrder.getId());

            }
            return orderRepository.save(savedOrder);
        } catch (WebClientResponseException.NotFound e) {
            logger.error("Product not found (404): {}", order.getProductId());
            order.setStatus("FAILED");
            order.setTotalPrice(BigDecimal.ZERO);
            order.setProductName("Product Not Found");
            return orderRepository.save(order);
        } catch (Exception e) {
            logger.error("Error during order placement: {}", e.getMessage());
            order.setStatus("FAILED");
            order.setTotalPrice(BigDecimal.ZERO);
            order.setProductName("Service Error");
            return orderRepository.save(order);
        }
    }

    private PaymentDTO processPayment(Order savedOrder) {
        try{
            String paymentServiceURL = "http://PAYMENT-SERVICE/api/payments?orderId=" + savedOrder.getId() + "&customerEmail=" + savedOrder.getCustomerEmail() + "&amount=" + savedOrder.getTotalPrice();
            logger.info("Calling payment service: {}", paymentServiceURL);

            return webClient
                    .post()
                    .uri(paymentServiceURL)
                    .retrieve()
                    .bodyToMono(PaymentDTO.class)
                    .block();
        } catch (WebClientResponseException e) {
            logger.error("Payment service error: {} - {}", e.getStatusCode(), e.getResponseBodyAsString());
            return null;
        } catch (Exception e) {
            logger.error("payment service call failed: {}", e.getMessage());
            return null;
        }
    }

    private ProductDTO fetchProduct(String productId) {
        try {
            String productServiceURL = "http://PRODUCT-SERVICE/api/products/" + productId;
            logger.info("Fetch product from: {}", productServiceURL);
            return webClient
                    .get()
                    .uri(productServiceURL)
                    .retrieve()
                    .bodyToMono(ProductDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            logger.error("Product not found: {}", productId);
            return null;
        } catch (Exception e) {
            logger.error("Error fetching product details: {}", e.getMessage());
            return null;
        }
    }

    

    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        if(orders.isEmpty()) {
            throw new RuntimeException("No orders found");
        }
        return orders;
    }

    public Order getOrderById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public List<Order> getOrdersByCustomerEmail(String email) {
        List<Order> orders = orderRepository.findByCustomerEmail(email);
        if(orders.isEmpty()) {
            throw new RuntimeException("No orders found for customer email: " + email);
        }
        return orders;
    }

}
