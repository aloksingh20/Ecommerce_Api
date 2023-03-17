package com.alok91340.ecommerceapi.service;

import java.util.List;

import com.alok91340.ecommerceapi.dto.OrderItemDto;
import com.alok91340.ecommerceapi.entities.OrderItem;
import com.alok91340.ecommerceapi.entities.User;

public interface OrderItemService {
    void addOrderProducts(OrderItem orderProducts);

    List<OrderItemDto> findOrderItemsByCustomer(User customer);
}
