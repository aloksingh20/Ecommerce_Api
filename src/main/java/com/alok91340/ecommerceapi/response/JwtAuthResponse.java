package com.alok91340.ecommerceapi.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
