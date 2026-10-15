package com.aloha.matmap.domain.restaurant;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantSearchResponse {

    private Long id;
    private String name;
    private String nickname;
    private BigDecimal rating;
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
}