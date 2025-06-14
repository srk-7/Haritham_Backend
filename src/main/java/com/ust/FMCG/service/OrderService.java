package com.ust.FMCG.service;

import com.ust.FMCG.dto.OrderRequest;
import com.ust.FMCG.model.Order;
import com.ust.FMCG.model.Product;
import com.ust.FMCG.model.User;
import com.ust.FMCG.repository.OrderRepository;
import com.ust.FMCG.repository.ProductRepository;
import com.ust.FMCG.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public Order placeOrder(OrderRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getQuantityAvailable() < request.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }

        User buyer = userRepository.findById(request.getBuyerId())
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        product.setQuantityAvailable(product.getQuantityAvailable() - request.getQuantity());
        productRepository.save(product);

        Order order = new Order();
        order.setProductId(product.getId());
        order.setProductName(product.getName());
        order.setQuantityOrdered(request.getQuantity());
        order.setTotalPrice(product.getPricePerUnit() * request.getQuantity());
        order.setBuyerId(buyer.getId());
        order.setBuyerName(buyer.getName());
        order.setBuyerMobile(buyer.getMobile());
        order.setBuyerEmpId(buyer.getEmpId());
        order.setSellerId(product.getSellerId());
        order.setOrderDate(LocalDateTime.now());

        Order saved = orderRepository.save(order);

        buyer.getOrdersPlaced().add(saved);
        userRepository.save(buyer);

        return saved;
    }


    public List<Order> getOrdersForSeller(String sellerId) {
        return orderRepository.findBySellerId(sellerId);
    }

    public List<Order> getOrdersByProductId(String productId) {
        return orderRepository.findByProductId(productId);
    }
}
