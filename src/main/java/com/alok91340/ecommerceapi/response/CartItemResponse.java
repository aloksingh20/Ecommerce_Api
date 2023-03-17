package com.alok91340.ecommerceapi.response;

import java.util.List;

import com.alok91340.ecommerceapi.dto.CartItemDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CartItemResponse {
    private List<CartItemDto> content;
    private double totalPrice;
}
