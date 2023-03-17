package com.alok91340.ecommerceapi.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alok91340.ecommerceapi.Exception.EcommerceApiException;
import com.alok91340.ecommerceapi.dto.CartItemDto;
import com.alok91340.ecommerceapi.dto.OrderDto;
import com.alok91340.ecommerceapi.entities.Order;
import com.alok91340.ecommerceapi.entities.OrderItem;
import com.alok91340.ecommerceapi.entities.User;
import com.alok91340.ecommerceapi.repository.CartItemRepository;
import com.alok91340.ecommerceapi.repository.OrderRepository;
import com.alok91340.ecommerceapi.response.CartItemResponse;
import com.alok91340.ecommerceapi.service.CartitemService;
import com.alok91340.ecommerceapi.service.OrderItemService;
import com.alok91340.ecommerceapi.service.OrderService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private final OrderItemService orderItemService;
    @Autowired
    private final CartItemRepository cartItemRepository;
    @Autowired
    private final CartitemService cartitemService;
    @Autowired
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public void placeOrder(User customer) {
        CartItemResponse cartItemDto = cartitemService.findByCustomer(customer);
        // set order fields
        OrderDto orderDto = setFields(cartItemDto, customer);
        // save order to the db
        OrderDto savedOrder = saveOrder(orderDto);
        List<CartItemDto> cartItemDtoList = cartItemDto.getContent();
        for (CartItemDto cartItem : cartItemDtoList) {
            OrderItem orderProducts = new OrderItem();
            orderProducts.setUser(customer);
            ;
            orderProducts.setProduct(cartItem.getProduct());
            orderProducts.setOrder(mapToEntity(savedOrder));
            orderProducts.setProdPrice(cartItem.getProduct().getPrice());
            orderProducts.setProdQuantity(cartItem.getQuantity());
            orderProducts.setTotalPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
            orderItemService.addOrderProducts(orderProducts);
        }
        cartItemRepository.deleteByCustomerId(customer.getId());
    }

    @Override
    public OrderDto saveOrder(OrderDto orderDto) {
        // convert to entity
        Order order = mapToEntity(orderDto);
        // save order to db
        Order placedOrder = orderRepository.save(order);
        return mapToDto(placedOrder);
    }

    private OrderDto setFields(CartItemResponse cartItemDto, User customer) {
        OrderDto orderDto = new OrderDto();
        orderDto.setTotalPrice(cartItemDto.getTotalPrice());
        orderDto.setEmail(customer.getEmail());
        orderDto.setName(customer.getName());
        orderDto.setCustomer(customer);

        return orderDto;
    }

    @Override
    public List<OrderDto> listOrdersByUser(User customer) {
        List<Order> orders = orderRepository.findByUser(customer);
        if (orders.size() == 0) {
            throw new EcommerceApiException("User has no order", HttpStatus.BAD_REQUEST);
        }
        List<OrderDto> orderDtoList = orders.stream()
                .map(order -> mapToDto(order))
                .collect(Collectors.toList());
        return orderDtoList;
    }

    // map to Entity
    private Order mapToEntity(OrderDto orderDto) {
        Order order = modelMapper.map(orderDto, Order.class);
        return order;
    }

    // map to Dto
    private OrderDto mapToDto(Order order) {
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        return orderDto;
    }
}
