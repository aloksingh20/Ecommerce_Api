package com.alok91340.ecommerceapi.dto;

import lombok.Data;

@Data
public class ImageDto {
    private String name;
    private String type;
    private byte[] imageData;

}
