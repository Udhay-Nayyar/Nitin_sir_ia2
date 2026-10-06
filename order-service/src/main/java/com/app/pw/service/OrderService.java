package com.app.pw.service;

import com.app.pw.client.FoodClient;
import com.app.pw.client.PaymentClient;
import com.app.pw.entity.Order;
import com.app.pw.repository.OrderRepository;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final FoodClient foodClient;
    private final PaymentClient paymentClient;
    private final HttpServletRequest request;

    public OrderService(
            OrderRepository orderRepository,
            FoodClient foodClient,
            PaymentClient paymentClient,
            HttpServletRequest request) {

        this.orderRepository = orderRepository;
        this.foodClient = foodClient;
        this.paymentClient = paymentClient;
        this.request = request;
    }

    public Order createOrder(Order order) {

        // Get JWT from incoming request
        String authorization =
                request.getHeader("Authorization");

        // 1. Get food details from Food Service
        FoodClient.FoodResponse food =
                foodClient.getFood(
                        order.getFoodId(),
                        authorization
                );

        // 2. Check food availability
        if (!Boolean.TRUE.equals(food.availability())) {
            throw new RuntimeException(
                    "Food is not available"
            );
        }

        // 3. Calculate total amount
        double total =
                food.price() * order.getQuantity();

        order.setTotalAmount(total);
        order.setStatus("PAYMENT_PENDING");

        // 4. Save order
        Order savedOrder =
                orderRepository.save(order);

        System.out.println(
                "ORDER ID = " + savedOrder.getId()
        );

        System.out.println(
                "TOTAL = " + total
        );

        // 5. Call Payment Service using Feign
        PaymentClient.PaymentResponse payment =
                paymentClient.processPayment(
                        new PaymentClient.PaymentRequest(
                                savedOrder.getId(),
                                total
                        ),
                        authorization
                );

        // 6. Update order status
        if ("SUCCESS".equals(payment.status())) {

            savedOrder.setStatus("CONFIRMED");

        } else {

            savedOrder.setStatus("PAYMENT_FAILED");
        }

        Order finalOrder =
                orderRepository.save(savedOrder);

        // 7. Dummy Kafka / Notification
        if ("CONFIRMED".equals(finalOrder.getStatus())) {

            System.out.println(
                    "NOTIFICATION → Order "
                            + finalOrder.getId()
                            + " created successfully for User "
                            + finalOrder.getUserId()
            );
        }

        return finalOrder;
    }

    public Order getOrderById(Long id) {

        return orderRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Order not found"
                        )
                );
    }

    public List<Order> getOrdersByUser(Long userId) {

        return orderRepository.findByUserId(userId);
    }

    public Order cancelOrder(Long id) {

        Order order = getOrderById(id);

        order.setStatus("CANCELLED");

        return orderRepository.save(order);
    }
}