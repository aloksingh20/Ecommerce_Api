package com.alok91340.ecommerceapi.service.Impl;

import java.util.Collections;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alok91340.ecommerceapi.dto.SignUpDto;
import com.alok91340.ecommerceapi.entities.Role;
import com.alok91340.ecommerceapi.entities.User;
import com.alok91340.ecommerceapi.repository.RoleRepository;
import com.alok91340.ecommerceapi.repository.UserRepository;
import com.alok91340.ecommerceapi.service.UserRegisterService;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public SignUpDto registerUser(SignUpDto signUpDto) {
        User user = mapToEntity(signUpDto);

        User registeredUser = this.userRepository.save(user);

        return mapToDto(registeredUser);
    }

    private SignUpDto mapToDto(User user) {
        SignUpDto signUpDto = new SignUpDto();
        signUpDto.setUserName(user.getUsername());
        signUpDto.setEmail(user.getEmail());
        signUpDto.setPassword(user.getPassword());
        signUpDto.setName(user.getName());
        return signUpDto;
    }

    private User mapToEntity(SignUpDto signUpDto) {
        User user = new User();
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUserName());
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        // Add role to the user

        Role role = this.roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singleton(role));
        return user;
    }

}
