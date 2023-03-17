package com.alok91340.ecommerceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alok91340.ecommerceapi.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
