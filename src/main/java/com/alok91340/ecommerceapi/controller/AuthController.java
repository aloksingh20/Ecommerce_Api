package com.alok91340.ecommerceapi.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alok91340.ecommerceapi.dto.LoginDto;
import com.alok91340.ecommerceapi.dto.SignUpDto;
import com.alok91340.ecommerceapi.repository.UserRepository;
import com.alok91340.ecommerceapi.response.JwtAuthResponse;
import com.alok91340.ecommerceapi.security.JwtTokenProvider;
import com.alok91340.ecommerceapi.service.UserRegisterService;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserRegisterService userRegisterService;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    // login api
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@RequestBody LoginDto loginDto) throws Exception {

        authenticate(loginDto.getUserNameOrEmail(),

                loginDto.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(loginDto.getUserNameOrEmail());

        final String token = tokenProvider.generateToken(userDetails);

        return ResponseEntity.ok(new JwtAuthResponse(token));
    }

    // register api
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {

        // check for username exists in DB
        if (userRepository.existsByUsername(signUpDto.getUserName())) {
            return new ResponseEntity<>("Username already exists",
                    HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByEmail(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }
        userRegisterService.registerUser(signUpDto);
        return new ResponseEntity<>("User is successfully registered", HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws

    Exception {

        Objects.requireNonNull(username);

        Objects.requireNonNull(password);

        try {

            authenticationManager.authenticate(new

            UsernamePasswordAuthenticationToken(username, password));
            System.out.println("hello");
        } catch (DisabledException e) {

            throw new Exception("USER_DISABLED", e);

        } catch (BadCredentialsException e) {

            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
