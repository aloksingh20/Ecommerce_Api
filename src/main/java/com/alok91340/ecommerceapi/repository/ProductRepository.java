package com.alok91340.ecommerceapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alok91340.ecommerceapi.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p FROM Product p WHERE " +
            "p.title LIKE CONCAT('%', :query, '%') " +
            "or p.description LIKE CONCAT('%', :query, '%')" +
            "or p.keywords LIKE CONCAT('%', :query, '%')" +
            "or p.detail LIKE CONCAT('%', :query, '%')", nativeQuery = true)
    List<Product> searchProduct(String query);
}
