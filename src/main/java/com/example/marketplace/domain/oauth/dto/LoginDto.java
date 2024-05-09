package com.example.marketplace.domain.oauth.dto;

import lombok.Data;

@Data
public class LoginDto {
    private Boolean isUser;
    private String token;
}
