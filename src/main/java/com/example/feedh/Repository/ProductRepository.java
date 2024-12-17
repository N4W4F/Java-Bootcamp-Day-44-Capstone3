package com.example.feedh.Repository;

import com.example.feedh.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findProductById(Integer id);

    List<Product> findProductByCategory(String category);

    @Query("select p from Product p where p.price >= ?1")
    List<Product> getProductByPriceMoreThanOrEqual(Double price);

    @Query("select p from Product p where p.price <= ?1")
    List<Product> getProductByPriceLessThanOrEqual(Double price);
}
