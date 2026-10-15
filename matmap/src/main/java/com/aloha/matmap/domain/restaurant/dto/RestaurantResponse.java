package com.aloha.matmap.domain.restaurant.dto;

import com.aloha.matmap.domain.restaurant.entity.Restaurant;
import com.aloha.matmap.domain.restaurant.entity.Visibility;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class RestaurantResponse {
    private final Long id;
    private final String nickname;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final Double rating;
    private final String memo;
    private final Visibility visibility;
    private final Long userId;

    public RestaurantResponse(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.nickname = restaurant.getNickname();
        this.latitude = restaurant.getLatitude();
        this.longitude = restaurant.getLongitude();
        this.rating = restaurant.getRating();
        this.memo = restaurant.getMemo();
        this.visibility = restaurant.getVisibility();
        this.userId = restaurant.getUserId();
    }
}