package com.example.feedh.Controller;

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
}
