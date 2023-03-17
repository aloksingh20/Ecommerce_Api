package com.alok91340.ecommerceapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alok91340.ecommerceapi.entities.OrderItem;
import com.alok91340.ecommerceapi.entities.User;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByUser(User user);
}
