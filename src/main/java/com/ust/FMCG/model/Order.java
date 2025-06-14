package com.ust.FMCG.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    private String id;

    private String buyerId;

    private String sellerId;

    private String productId;

    private String productName;

    private int quantityOrdered;

    private double totalPrice;

    private LocalDateTime orderDate;

    private String buyerName;

    private String buyerMobile;

    private String buyerEmpId;

    private String status = "ORDERED";
    // Constructors, getters, setters
}
