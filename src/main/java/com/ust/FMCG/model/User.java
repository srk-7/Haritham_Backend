package com.ust.FMCG.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;

    private String empId;

    private String name;

    private String mobile;

    private String password; // stored securely (hashed)

    private String salt;

    private List<Order> ordersPlaced = new ArrayList<>(); // buy history

    private List<String> sellingProductIds = new ArrayList<>();

    // Constructors, getters, setters
}
