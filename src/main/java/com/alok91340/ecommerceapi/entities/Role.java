package com.alok91340.ecommerceapi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Role {

    @Id
    private Long id;
    @Column(length = 60)
    private String name;
}
