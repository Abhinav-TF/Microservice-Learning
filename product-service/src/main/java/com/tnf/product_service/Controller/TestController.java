package com.tnf.product_service.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RefreshScope       /* You can call it in postman by hitting the endpoint with a POST request to /actuator/refresh */
public class TestController {
    @Value("${message}")
    private String message;

    @Value("${my.name}")
    private String team;

    @GetMapping("/vijay")
    public String config() {
        return message + " - " + team;
    }
}
