package com.alok91340.ecommerceapi.dto;

import com.alok91340.ecommerceapi.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class OrderDto {
    private String name;
    private String email;
    private String phone;
    private double totalPrice;
    private String status;

    @JsonIgnore
    private User customer;
}
