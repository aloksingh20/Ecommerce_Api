package com.alok91340.ecommerceapi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import com.alok91340.ecommerceapi.dto.CartItemDto;
import com.alok91340.ecommerceapi.entities.User;
import com.alok91340.ecommerceapi.response.CartItemResponse;
import com.alok91340.ecommerceapi.response.CommonResponse;

public interface CommonService<T> {
    CommonResponse getResponseContent(Page<T> page, List<T> dtoList);

    // cart iem response handler
    CartItemResponse getResponse(CartItemDto cartItemDto);

    // get current authenticated user
    User getCurrentAuthenticatedUser(Authentication authentication);

    // entity mapper
    T mapToEntity(T type);

    // dto mapper
    T mapToDto(T type);
}
