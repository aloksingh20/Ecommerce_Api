package com.alok91340.ecommerceapi.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alok91340.ecommerceapi.Exception.EcommerceApiException;
import com.alok91340.ecommerceapi.dto.OrderItemDto;
import com.alok91340.ecommerceapi.entities.OrderItem;
import com.alok91340.ecommerceapi.entities.User;
import com.alok91340.ecommerceapi.repository.OrderItemRepository;
import com.alok91340.ecommerceapi.service.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void addOrderProducts(OrderItem orderProducts) {
        orderItemRepository.save(orderProducts);
    }

    @Override
    public List<OrderItemDto> findOrderItemsByCustomer(User customer) {

        List<OrderItem> orderItems = this.orderItemRepository.findByUser(customer);
        if (orderItems.size() == 0) {
            throw new EcommerceApiException("User has no orderd products", HttpStatus.BAD_REQUEST);
        }

        List<OrderItemDto> orderProductsDtoList = orderItems.stream()
                .map(orderItem -> mapToDto(orderItem))
                .collect(Collectors.toList());

        return orderProductsDtoList;
    }

    private OrderItemDto mapToDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = this.modelMapper.map(orderItem, OrderItemDto.class);
        return orderItemDto;
    }

}
