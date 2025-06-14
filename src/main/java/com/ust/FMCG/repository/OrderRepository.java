package com.ust.FMCG.repository;

import com.ust.FMCG.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order,String> {

    List<Order> findByBuyerId(String userId);

    List<Order> findBySellerId(String sellerId);

    List<Order> findByProductId(String productId);

}
