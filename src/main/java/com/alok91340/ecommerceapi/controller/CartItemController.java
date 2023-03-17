package com.alok91340.ecommerceapi.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alok91340.ecommerceapi.entities.User;
import com.alok91340.ecommerceapi.response.CartItemResponse;
import com.alok91340.ecommerceapi.service.CartitemService;
import com.alok91340.ecommerceapi.service.CommonService;
import com.alok91340.ecommerceapi.utils.isAuthenticatedAsAdminOrUser;

@RestController
@RequestMapping("api/v1/")
public class CartItemController {

    @Autowired
    private CartitemService cartitemService;

    @Autowired
    private CommonService commonService;

    // find by customer api
    @isAuthenticatedAsAdminOrUser
    @GetMapping("/findByCustomer")
    public CartItemResponse findByCustomerId(@AuthenticationPrincipal Authentication authentication) {

        User customer = commonService.getCurrentAuthenticatedUser(authentication);
        CartItemResponse responseCartItems = cartitemService.findByCustomer(customer);
        return responseCartItems;
    }

    // add item to the cart api
    @isAuthenticatedAsAdminOrUser
    @PostMapping("/addItem/{productId}/{quantity}")
    public ResponseEntity<CartItemResponse> addCartItem(@AuthenticationPrincipal Authentication authentication,
            @PathVariable Long productId,
            @PathVariable Integer quantity) {

        User customer = commonService.getCurrentAuthenticatedUser(authentication);

        CartItemResponse responseCartItem = cartitemService.addCartItem(customer, productId, quantity);
        return new ResponseEntity<>(responseCartItem, HttpStatus.CREATED);
    }

    // update item quantity api
    @isAuthenticatedAsAdminOrUser
    @PutMapping("/updateItemQuantity/{productId}/{quantity}")
    public ResponseEntity<CartItemResponse> updateItemQuantity(@AuthenticationPrincipal Authentication authentication,
            @PathVariable Long productId,
            @PathVariable Integer quantity) {
        User customer = commonService.getCurrentAuthenticatedUser(authentication);
        CartItemResponse responseCartItem = cartitemService.updateItemQuantity(customer, productId, quantity);
        return new ResponseEntity<>(responseCartItem, HttpStatus.OK);
    }

    // delete item product api
    @isAuthenticatedAsAdminOrUser
    @DeleteMapping("/deleteItemProduct/{productId}")
    public ResponseEntity<String> deleteItemProduct(@AuthenticationPrincipal Authentication authentication,
            @PathVariable Long productId) {

        User customer = commonService.getCurrentAuthenticatedUser(authentication);
        cartitemService.deleteItemProduct(customer, productId);
        return ResponseEntity.ok("Product with id = " + productId + " is deleted successfully from your shopping cart");
    }

}
