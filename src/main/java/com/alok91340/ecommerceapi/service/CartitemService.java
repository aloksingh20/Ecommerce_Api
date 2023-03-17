package com.alok91340.ecommerceapi.service;

import com.alok91340.ecommerceapi.entities.User;
import com.alok91340.ecommerceapi.response.CartItemResponse;

public interface CartitemService {

    CartItemResponse findByCustomer(User user);

    CartItemResponse addCartItem(User user, Long prodId, Integer quantity);

    CartItemResponse updateItemQuantity(User user, Long prodId, Integer quantity);

    void deleteItemProduct(User user, Long prodId);

}
