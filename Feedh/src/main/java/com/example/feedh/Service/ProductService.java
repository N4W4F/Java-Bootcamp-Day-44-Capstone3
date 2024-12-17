package com.example.feedh.Service;

import com.example.feedh.ApiResponse.ApiException;
import com.example.feedh.DTOout.ProductDTOout;
import com.example.feedh.Model.Product;
import com.example.feedh.Model.Supplier;
import com.example.feedh.Repostiory.ProductRepository;
import com.example.feedh.Repostiory.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    public List<ProductDTOout> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTOout> productDTOS = new ArrayList<>();

        for (Product p : products) {
            productDTOS.add(new ProductDTOout(p.getName(), p.getCategory(), p.getDescription(), p.getPrice()));
        }
        return productDTOS;
    }

    public void addProduct(Integer supplierId, Product product) {
        Supplier supplier = supplierRepository.findSupplierById(supplierId);
        if (supplier == null) {
            throw new ApiException("Supplier with ID: " + supplierId + " was not found");
        }
        product.setSupplier(supplier);
        productRepository.save(product);
    }

    public void updateProduct(Integer productId, Product product) {
        Product oldProduct = productRepository.findProductById(productId);
        if (oldProduct == null) {
            throw new ApiException("Product with ID: " + productId + " was not found");
        }
        oldProduct.setName(product.getName());
        oldProduct.setCategory(product.getCategory());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setPrice(product.getPrice());
        productRepository.save(oldProduct);
    }

    public void deleteProduct(Integer productId) {
        Product product = productRepository.findProductById(productId);
        if (product == null) {
            throw new ApiException("Product with ID: " + productId + " was not found");
        }
        productRepository.delete(product);
    }
}
