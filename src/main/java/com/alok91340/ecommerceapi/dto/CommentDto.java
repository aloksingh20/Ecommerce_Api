package com.alok91340.ecommerceapi.dto;

import java.util.Set;

import com.alok91340.ecommerceapi.entities.Images;

import lombok.Data;

@Data
public class CommentDto {

    private String commentDesc;
    private String rate;
    private String image;
    private Images commentImg;
}
