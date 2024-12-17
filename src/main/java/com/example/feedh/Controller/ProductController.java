package com.example.feedh.Controller;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.example.feedh.ApiResponse.ApiResponse;
import com.example.feedh.Model.Product;
import com.example.feedh.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    // CRUD - Start
    @GetMapping("/get")
    public ResponseEntity getAllProducts() {
        return ResponseEntity.status(200).body(productService.getAllProducts());
    }

    @PostMapping("/add/{supplierId}")
    public ResponseEntity addProduct(@PathVariable Integer supplierId, @RequestBody @Valid Product product) {
        productService.addProduct(supplierId, product);
        return ResponseEntity.status(200).body(new ApiResponse("Product has been added to supplier with ID: " + supplierId + " successfully"));
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity updateProduct(@PathVariable Integer productId, @RequestBody @Valid Product product) {
        productService.updateProduct(productId, product);
        return ResponseEntity.status(200).body(new ApiResponse("Product with ID: " + productId + " has been updated successfully"));
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(200).body(new ApiResponse("Product with ID: " + productId + " has been deleted successfully"));
    }
    // CRUD - End

    // Getters - Start
    @GetMapping("/get/by-id/{productId}")
    public ResponseEntity getProductById(@PathVariable Integer productId) {
        return ResponseEntity.status(200).body(productService.getProductById(productId));
    }

    @GetMapping("/get/by-category/{category}")
    public ResponseEntity getProductByCategory(@PathVariable String category) {
        return ResponseEntity.status(200).body(productService.getProductByCategory(category));
    }

    @GetMapping("/get/by-price/more-than-equal/{price}")
    public ResponseEntity getProductByPriceMoreThanOrEqual(@PathVariable Double price) {
        return ResponseEntity.status(200).body(productService.getProductByPriceMoreThanOrEqual(price));
    }

    @GetMapping("/get/by-price/less-than-equal/{price}")
    public ResponseEntity getProductByPriceLessThanOrEqual(@PathVariable Double price) {
        return ResponseEntity.status(200).body(productService.getProductByPriceLessThanOrEqual(price));
    }
    // Getters - End

    // Services - Start
    @PutMapping("/buy/{customerId}/{productId}")
    public ResponseEntity buyProductById(@PathVariable Integer customerId, @PathVariable Integer productId) {
        productService.buyProductById(customerId, productId);
        return ResponseEntity.status(200).body(new ApiResponse("You have bought this product successfully"));
    }
}
