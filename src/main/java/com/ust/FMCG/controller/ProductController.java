package com.ust.FMCG.controller;

import com.ust.FMCG.model.Product;
import com.ust.FMCG.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://haritham-frontend.vercel.app", allowCredentials = "true")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.addProduct(product));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        return ResponseEntity.ok(productService.deleteProduct(id));
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<?> getSellerProducts(@PathVariable String sellerId) {
        return ResponseEntity.ok(productService.getSellerProducts(sellerId));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    @PutMapping("/{id}/visibility")
    public ResponseEntity<?> updateProductVisibility(
            @PathVariable String id,
            @RequestParam boolean visible) {
        return ResponseEntity.ok(productService.updateProductVisibility(id, visible));
    }

}
