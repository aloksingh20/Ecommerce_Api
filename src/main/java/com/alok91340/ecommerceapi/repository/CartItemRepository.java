package com.alok91340.ecommerceapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.alok91340.ecommerceapi.entities.Cartitem;
import com.alok91340.ecommerceapi.entities.Product;
import com.alok91340.ecommerceapi.entities.User;

public interface CartItemRepository extends JpaRepository<Cartitem, Long> {
    List<Cartitem> findByCustomer(User user);

    Cartitem findByCustomerAndProduct(User user, Product product);

    @Query(value = "UPDATE Cartitem c SET c.quantity = ?1 WHERE c.product.id = ?2 AND c.customer.id = ?3")
    void updateItemQuantity(Integer quantity, Long productId, Long customerId);

    @Query(value = "DELETE FROM Cartitem c WHERE c.customer.id = ?1 AND c.product.id = ?2")
    @Modifying
    void deleteByCustomerAndProduct(Long customerId, Long productId);

    @Query(value = "DELETE FROM Cartitem c WHERE c.customer.id = ?1")
    @Modifying
    void deleteByCustomerId(Long customerId);
}
