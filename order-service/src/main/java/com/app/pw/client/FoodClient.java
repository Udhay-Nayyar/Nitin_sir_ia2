package com.app.pw.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "food-service",
        url = "http://localhost:8082"
)
public interface FoodClient {

    @GetMapping("/foods/{id}")
    FoodResponse getFood(
            @PathVariable("id") Long id,
            @RequestHeader("Authorization") String authorization
    );

    record FoodResponse(
            Long id,
            String foodName,
            Double price,
            String category,
            Boolean availability,
            Long restaurantId
    ) {}
}