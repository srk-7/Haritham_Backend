package com.ust.FMCG.controller;

import com.ust.FMCG.dto.OrderRequest;
import com.ust.FMCG.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.placeOrder(orderRequest));
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<?> getOrdersForSeller(@PathVariable String sellerId) {
        return ResponseEntity.ok(orderService.getOrdersForSeller(sellerId));
    }


}
