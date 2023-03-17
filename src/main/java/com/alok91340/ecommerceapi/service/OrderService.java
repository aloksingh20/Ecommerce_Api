package com.alok91340.ecommerceapi.service;

import java.util.List;

import com.alok91340.ecommerceapi.dto.OrderDto;
import com.alok91340.ecommerceapi.entities.User;

public interface OrderService {

    void placeOrder(User customer);

    OrderDto saveOrder(OrderDto orderDto);

    List<OrderDto> listOrdersByUser(User customer);
}
