package com.ust.FMCG.service;

import com.ust.FMCG.model.Product;
import com.ust.FMCG.model.User;
import com.ust.FMCG.repository.ProductRepository;
import com.ust.FMCG.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public Product addProduct(Product product) {
        // Check that seller exists first
        User seller = userRepository.findById(product.getSellerId())
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        // Save product after seller is verified
        Product saved = productRepository.save(product);

        // Update seller with product reference
        seller.getSellingProductIds().add(saved.getId());
        userRepository.save(seller);

        return saved;
    }

    public Product updateProduct(String id, Product updatedProduct) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        existing.setName(updatedProduct.getName());
        existing.setCategory(updatedProduct.getCategory());
        existing.setPricePerUnit(updatedProduct.getPricePerUnit());
        existing.setQuantityAvailable(updatedProduct.getQuantityAvailable());
        existing.setDescription(updatedProduct.getDescription());
        existing.setImageUrl(updatedProduct.getImageUrl());
        return productRepository.save(existing);
    }

    public String deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(id);
        return "Product deleted successfully";
    }

    public List<Product> getSellerProducts(String sellerId) {
        return productRepository.findBySellerId(sellerId);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll().stream()
                .filter(Product::isVisible)
                .collect(Collectors.toList());
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryIgnoreCase(category).stream()
                .filter(Product::isVisible)
                .collect(Collectors.toList());
    }

    public Product updateProductVisibility(String id, boolean visible) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setVisible(visible);
        return productRepository.save(product);
    }
}
