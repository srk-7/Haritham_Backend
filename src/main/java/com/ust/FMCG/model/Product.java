package com.ust.FMCG.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    private String id;

    private String name;

    private String description;

    private String category; // fruits, vegetables, leafy vegs, etc.

    private double pricePerUnit;

    private int quantityAvailable;

    private String imageUrl;

    private String sellerId; // reference to User

    private boolean visible = true; // default visibility is true

    // Constructors, getters, setters
}
