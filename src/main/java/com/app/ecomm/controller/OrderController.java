package com.app.ecomm.controller;

import com.app.ecomm.dto.OrderResponse;
import com.app.ecomm.repository.OrderRepository;
import com.app.ecomm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {


    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Optional<OrderResponse>> createOrder(@RequestHeader("X-User-ID") String userId){
        Optional<OrderResponse> order=orderService.createOrder(userId);
        return order.isEmpty()?ResponseEntity.notFound().build():ResponseEntity.ok(order);
    }
}
