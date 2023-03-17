package com.alok91340.ecommerceapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alok91340.ecommerceapi.entities.Order;
import com.alok91340.ecommerceapi.entities.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User customer);
}
