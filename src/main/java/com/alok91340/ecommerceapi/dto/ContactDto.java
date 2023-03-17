package com.alok91340.ecommerceapi.dto;

import lombok.Data;

@Data
public class ContactDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String subject;
    private String message;
    private String ipAddress;
    private String status;
}
