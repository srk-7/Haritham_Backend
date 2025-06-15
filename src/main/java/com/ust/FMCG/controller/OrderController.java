package com.ust.FMCG.controller;

import com.ust.FMCG.dto.OrderRequest;
import com.ust.FMCG.model.Order;
import com.ust.FMCG.repository.OrderRepository;
import com.ust.FMCG.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "https://haritham-frontend.vercel.app", allowCredentials = "true")
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.placeOrder(orderRequest));
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<?> getOrdersForSeller(@PathVariable String sellerId) {
        return ResponseEntity.ok(orderService.getOrdersForSeller(sellerId));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable String orderId,
            @RequestParam String status) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
        }

        Order order = optionalOrder.get();

        if (!isValidStatus(status)) {
            return ResponseEntity.badRequest().body("Invalid status. Must be one of: ORDERED, PACKED, PLACED_ON_HARITHAM_TABLE, COLLECTED");
        }

        order.setStatus(status);
        orderRepository.save(order);

        return ResponseEntity.ok("Order status updated to: " + status);
    }

    private boolean isValidStatus(String status) {
        return status != null && (
            status.equals("ORDERED") ||
            status.equals("PACKED") ||
            status.equals("PLACED_ON_HARITHAM_TABLE") ||
            status.equals("COLLECTED")
        );
    }

    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<Order>> getOrdersByBuyer(@PathVariable String buyerId) {
        List<Order> orders = orderRepository.findByBuyerId(buyerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getOrdersByProduct(@PathVariable String productId) {
        return ResponseEntity.ok(orderService.getOrdersByProductId(productId));
    }

}
