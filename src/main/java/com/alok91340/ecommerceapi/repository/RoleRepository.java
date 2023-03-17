package com.alok91340.ecommerceapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alok91340.ecommerceapi.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
