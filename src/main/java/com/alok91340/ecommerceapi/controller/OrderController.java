package com.alok91340.ecommerceapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alok91340.ecommerceapi.dto.OrderDto;
import com.alok91340.ecommerceapi.dto.OrderItemDto;
import com.alok91340.ecommerceapi.entities.User;
import com.alok91340.ecommerceapi.service.CommonService;
import com.alok91340.ecommerceapi.service.OrderItemService;
import com.alok91340.ecommerceapi.service.OrderService;
import com.alok91340.ecommerceapi.utils.isAuthenticatedAsAdminOrUser;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private CommonService commonService;

    @isAuthenticatedAsAdminOrUser
    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@AuthenticationPrincipal Authentication authentication) {
        User customer = commonService.getCurrentAuthenticatedUser(authentication);
        orderService.placeOrder(customer);
        return new ResponseEntity<>("Order placed successfully", HttpStatus.CREATED);
    }

    // find order by customer api
    @isAuthenticatedAsAdminOrUser
    @GetMapping("/findByCustomer")
    public List<OrderDto> listOrdersByCustomer(@AuthenticationPrincipal Authentication authentication) {

        User customer = commonService.getCurrentAuthenticatedUser(authentication);

        List<OrderDto> customerOrders = orderService.listOrdersByUser(customer);
        return customerOrders;
    }

    // find ordered items by Customer
    @isAuthenticatedAsAdminOrUser
    @GetMapping("/findOrderedItemsByCustomer")
    public List<OrderItemDto> findOrderedItemsByCustomer(@AuthenticationPrincipal Authentication authentication) {
        User customer = commonService.getCurrentAuthenticatedUser(authentication);
        List<OrderItemDto> customerOrderedItems = orderItemService.findOrderItemsByCustomer(customer);
        return customerOrderedItems;
    }
}
