package com.alok91340.ecommerceapi.service;

import com.alok91340.ecommerceapi.dto.SignUpDto;

public interface UserRegisterService {
    SignUpDto registerUser(SignUpDto signUpDto);
}
