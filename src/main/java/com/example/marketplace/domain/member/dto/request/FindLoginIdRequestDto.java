package com.example.marketplace.domain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindLoginIdRequestDto {

    private String name;
    private String email;
}
