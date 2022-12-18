package com.example.orderservice.controller;

import com.example.basedomains.dto.Order;
import com.example.basedomains.dto.OrderEvent;
import com.example.orderservice.kafka.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    @Autowired
    private OrderProducer orderProducer;

    @PostMapping("/orders")
    public ResponseEntity<String> placeOrder(@RequestBody Order order) {
        order.setOrderId(UUID.randomUUID().toString());

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setStatus("PENDING");
        orderEvent.setMessage("Order in is pending state");
        orderEvent.setOrder(order);

        orderProducer.sendMessage(orderEvent);
        return new ResponseEntity<>("Order placed successfully", HttpStatus.OK);
    }
}