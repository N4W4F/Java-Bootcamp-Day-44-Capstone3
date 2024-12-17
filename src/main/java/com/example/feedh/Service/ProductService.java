package com.example.feedh.Service;

import com.example.feedh.ApiResponse.ApiException;
import com.example.feedh.DTOout.ProductDTOout;
import com.example.feedh.Model.Customer;
import com.example.feedh.Model.Product;
import com.example.feedh.Model.Supplier;
import com.example.feedh.Repository.CustomerRepository;
import com.example.feedh.Repository.ProductRepository;
import com.example.feedh.Repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final CustomerRepository customerRepository;

    // CRUD - Start
    public List<ProductDTOout> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTOout> productDTOS = new ArrayList<>();

        for (Product p : products) {
            productDTOS.add(new ProductDTOout(p.getName(), p.getCategory(), p.getDescription(), p.getPrice(), p.getQuantity()));
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
    // CRUD - End

    // Getters
    public ProductDTOout getProductById(Integer productId) {
        Product product = productRepository.findProductById(productId);
        if (product == null) {
            throw new ApiException("Product with ID: " + productId + " was not found");
        }
        return new ProductDTOout(product.getName(), product.getCategory(), product.getDescription(), product.getPrice(), product.getQuantity());
    }

    public List<ProductDTOout> getProductByCategory(String category) {
        List<Product> products = productRepository.findProductByCategory(category);
        if (products.isEmpty()) {
            throw new ApiException("There are no products in this category");
        }
        List<ProductDTOout> productDTOS = new ArrayList<>();
        for (Product p : products) {
            productDTOS.add(new ProductDTOout(p.getName(), p.getCategory(), p.getDescription(), p.getPrice(), p.getQuantity()));
        }
        return productDTOS;
    }

    public List<ProductDTOout> getProductByPriceMoreThanOrEqual(Double price) {
        List<Product> products = productRepository.getProductByPriceMoreThanOrEqual(price);
        if (products.isEmpty()) {
            throw new ApiException("There are no products with price more than " + price);
        }
        List<ProductDTOout> productDTOS = new ArrayList<>();
        for (Product p : products) {
            productDTOS.add(new ProductDTOout(p.getName(), p.getCategory(), p.getDescription(), p.getPrice(), p.getQuantity()));
        }
        return productDTOS;
    }

    public List<ProductDTOout> getProductByPriceLessThanOrEqual(Double price) {
        List<Product> products = productRepository.getProductByPriceLessThanOrEqual(price);
        if (products.isEmpty()) {
            throw new ApiException("There are no products with price less than " + price);
        }
        List<ProductDTOout> productDTOS = new ArrayList<>();
        for (Product p : products) {
            productDTOS.add(new ProductDTOout(p.getName(), p.getCategory(), p.getDescription(), p.getPrice(), p.getQuantity()));
        }
        return productDTOS;
    }
    // Getters - End

    // Services - Start
    public void buyProductById(Integer customerId, Integer productId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException("Customer with ID: " + customerId + " was not found");
        }

        Product product = productRepository.findProductById(productId);
        if (product == null) {
            throw new ApiException("Product with ID: " + productId + " was not found");
        }

        if (product.getQuantity() == 0) {
            throw new ApiException("Product is out of stock");
        }
        product.setQuantity(product.getQuantity() - 1);
    }
}
