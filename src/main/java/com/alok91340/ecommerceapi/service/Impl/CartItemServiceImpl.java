package com.alok91340.ecommerceapi.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alok91340.ecommerceapi.Exception.EcommerceApiException;
import com.alok91340.ecommerceapi.Exception.ResourceNotFoundException;
import com.alok91340.ecommerceapi.dto.CartItemDto;
import com.alok91340.ecommerceapi.entities.Cartitem;
import com.alok91340.ecommerceapi.entities.Product;
import com.alok91340.ecommerceapi.entities.User;
import com.alok91340.ecommerceapi.repository.CartItemRepository;
import com.alok91340.ecommerceapi.repository.ProductRepository;
import com.alok91340.ecommerceapi.repository.UserRepository;
import com.alok91340.ecommerceapi.response.CartItemResponse;
import com.alok91340.ecommerceapi.service.CartitemService;
import com.alok91340.ecommerceapi.service.CommonService;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

@Service
@Slf4j
public class CartItemServiceImpl implements CartitemService {

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommonService commonService;

    @Override
    public CartItemResponse findByCustomer(User customer) {

        List<Cartitem> cartItems = cartItemRepository.findByCustomer(customer);

        if (cartItems.size() == 0) {
            throw new EcommerceApiException("User has no product in cart item", HttpStatus.BAD_REQUEST);
        }

        List<CartItemDto> cartItemDtoList = cartItems.stream()
                .map(cartItem -> mapToDto(cartItem))
                .collect(Collectors.toList());
        DoubleStream totalPrice = cartItemDtoList.stream()
                .mapToDouble(cartItemDto -> cartItemDto.getProduct().getPrice() * cartItemDto.getQuantity());
        CartItemResponse cartItemResponse = new CartItemResponse();
        cartItemResponse.setContent(cartItemDtoList);
        cartItemResponse.setTotalPrice(totalPrice.sum());
        return cartItemResponse;
    }

    @Override
    public CartItemResponse addCartItem(User customer, Long productId, Integer quantity) {
        Integer addedQuantity = quantity;
        Product product = findProductById(productId);

        Cartitem cartItem = cartItemRepository.findByCustomerAndProduct(customer, product);
        if (cartItem != null) {
            addedQuantity = cartItem.getQuantity() + quantity;
            cartItem.setQuantity(addedQuantity);
        } else {
            cartItem = new Cartitem();
            cartItem.setCustomer(customer);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
        }

        Cartitem addedCartItem = cartItemRepository.save(cartItem);
        CartItemDto cartItemDto = mapToDto(addedCartItem);

        CartItemResponse cartItemResponse = commonService.getResponse(cartItemDto);
        return cartItemResponse;
    }

    @Override
    public CartItemResponse updateItemQuantity(User customer, Long productId, Integer quantity) {
        Product product = findProductById(productId);
        Cartitem cartItem = cartItemRepository.findByCustomerAndProduct(customer, product);
        if (cartItem == null) {
            throw new EcommerceApiException("Product is not in the cart item", HttpStatus.BAD_REQUEST);
        }
        cartItem.setQuantity(quantity);
        Cartitem updatedItemQuantity = cartItemRepository.save(cartItem);
        CartItemDto cartItemDto = mapToDto(updatedItemQuantity);

        CartItemResponse cartItemResponse = commonService.getResponse(cartItemDto);
        return cartItemResponse;
    }

    @Override
    @Transactional
    public void deleteItemProduct(User customer, Long productId) {
        Product product = findProductById(productId);
        Cartitem cartItem = cartItemRepository.findByCustomerAndProduct(customer, product);
        if (cartItem == null) {
            throw new EcommerceApiException("Product is not in the cart item", HttpStatus.BAD_REQUEST);
        }
        cartItemRepository.deleteByCustomerAndProduct(customer.getId(), productId);
    }

    // map to dto
    private CartItemDto mapToDto(Cartitem cartItem) {
        CartItemDto cartItemDto = modelMapper.map(cartItem, CartItemDto.class);
        return cartItemDto;
    }

    // map to entity
    private Cartitem mapToEntity(CartItemDto cartItemDto) {
        Cartitem cartItem = modelMapper.map(cartItemDto, Cartitem.class);
        return cartItem;
    }

    private User findCustomerById(Long customerId) {
        User user = userRepository.findById(customerId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User", customerId));
        return user;
    }

    private Product findProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product", productId));
        return product;
    }

}
