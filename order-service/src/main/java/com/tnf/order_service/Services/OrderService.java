package com.tnf.order_service.Services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.tnf.order_service.Entities.Order;
import com.tnf.order_service.Entities.ProductDTO;
import com.tnf.order_service.Repositories.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    // @Autowired
    // private WebClient.Builder webClientBuilder;
    @Autowired
    WebClient webClient;

    public Order placeOrder(Order order) {
        try{
            if(order.getOrderDate() == null){
                order.setOrderDate(java.time.LocalDateTime.now());
            }

            //Validate product exists, wrong way
            //String productServiceURL = "http://localhost:8081/api/products/" + order.getProductId();

            //Correct way
            String productServiceURL = "http://PRODUCT-SERVICE/api/products/" + order.getProductId();
            
            ProductDTO product = webClient
                    .get()
                    .uri(productServiceURL)
                    .retrieve()
                    .bodyToMono(ProductDTO.class)
                    .block();
            
            if (product == null) {
                order.setStatus("FAILED");
                order.setTotalPrice(BigDecimal.ZERO);
                return orderRepository.save(order);
            }
            BigDecimal total = product.getPrice().multiply(BigDecimal.valueOf(order.getQuantity()));
            order.setTotalPrice(total);
            order.setStatus("COMPLETED");
            order.setProductName(product.getName());
            return orderRepository.save(order);
        } catch (Exception e) {
            order.setStatus("FAILED");
            order.setTotalPrice(BigDecimal.ZERO);
            return orderRepository.save(order);
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
