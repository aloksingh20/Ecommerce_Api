package com.alok91340.ecommerceapi.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alok91340.ecommerceapi.entities.Role;
import com.alok91340.ecommerceapi.entities.User;
import com.alok91340.ecommerceapi.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

        @Autowired
        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = this.userRepository.findByEmail(username)
                                .orElseThrow(() -> new UsernameNotFoundException("user not found" + username));
                return new org.springframework.security.core.userdetails.User(
                                user.getEmail(),
                                user.getPassword(),
                                mapRolesToAuthorities(user.getRoles()));
        }

        private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
                return roles.stream()
                                .map(role -> new SimpleGrantedAuthority(role.getName()))
                                .collect(Collectors.toList());
        }
        // private Collection<? extends GrantedAuthority>
        // mapRolesToAuthorities(Set<Role> roles) {
        // return roles.stream()
        // .map(role -> new SimpleGrantedAuthority(role.getName()))
        // .collect(Collectors.toList());
        // }
}

// userRepository.findByUsernameOrEmail(username,username).orElseThrow(()->new
// UsernameNotFoundException("User not found with username or email:
// "+username));return user;