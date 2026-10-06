package com.app.pw.controller;

import com.app.pw.entity.Order;
import com.app.pw.service.OrderService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(
            @RequestBody Order order) {

        return ResponseEntity.ok(
                orderService.createOrder(order)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                orderService.getOrderById(id)
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                orderService.getOrdersByUser(userId)
        );
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Order> cancelOrder(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                orderService.cancelOrder(id)
        );
    }
}