package com.ust.FMCG.repository;

import com.ust.FMCG.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product,String> {
    List<Product> findBySellerId(String sellerId);


    List<Product> findByCategoryIgnoreCase(String category);
}
