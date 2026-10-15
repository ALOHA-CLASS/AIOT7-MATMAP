package com.aloha.matmap.domain.restaurant.dto;

import com.aloha.matmap.domain.restaurant.entity.Restaurant;
import com.aloha.matmap.domain.restaurant.entity.Visibility;
import lombok.Getter;
import java.math.BigDecimal;

@Getter
public class RestaurantResponse {
    private final Long no;
    private final String id;
    private final Long mapNo;
    private final String name;
    private final String nickname;
    private final String address;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final Double rating;
    private final String memo;
    private final Visibility visibility;

    public RestaurantResponse(Restaurant restaurant) {
        this.no = restaurant.getNo();
        this.id = restaurant.getId();
        this.mapNo = restaurant.getMapNo();
        this.name = restaurant.getName();
        this.nickname = restaurant.getNickname();
        this.address = restaurant.getAddress();
        this.latitude = restaurant.getLatitude();
        this.longitude = restaurant.getLongitude();
        this.rating = restaurant.getRating();
        this.memo = restaurant.getMemo();
        this.visibility = restaurant.getVisibility();
    }
}