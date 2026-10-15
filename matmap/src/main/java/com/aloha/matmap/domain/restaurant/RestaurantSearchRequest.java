package com.aloha.matmap.domain.restaurant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantSearchRequest {

    // 맛집 이름 또는 별명 검색어
    private String keyword;

    // 최소 별점
    private Double minRating;

    // 조회할 맛맵 ID
    private Long mapId;
}