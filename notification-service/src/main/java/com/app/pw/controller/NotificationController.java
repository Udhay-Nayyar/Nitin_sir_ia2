package com.app.pw.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @PostMapping
    public String sendNotification(
            @RequestParam Long orderId,
            @RequestParam Long userId) {

        System.out.println(
                "================================="
        );

        System.out.println(
                "NOTIFICATION SERVICE"
        );

        System.out.println(
                "Order " + orderId +
                " created successfully for User " +
                userId
        );

        System.out.println(
                "================================="
        );

        return "Notification sent successfully";
    }
}