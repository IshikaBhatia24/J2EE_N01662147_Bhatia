package com.example.coffeeshop.services;

import com.example.coffeeshop.models.Product;
import com.example.coffeeshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Add a new product
    public Product addProduct(Product product) {
        return productRepository.save(product);  // Save to DB
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();  // Fetch from DB
    }

    // Find coffee by ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);  // Fetch by ID
    }

    // Update coffee details by ID
    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setName(updatedProduct.getName());
            existingProduct.setPrice(updatedProduct.getPrice());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setStock(updatedProduct.getStock());
            return productRepository.save(existingProduct);  // Save updates
        });
    }

    // Remove product by ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);  // Delete by ID
    }
}
