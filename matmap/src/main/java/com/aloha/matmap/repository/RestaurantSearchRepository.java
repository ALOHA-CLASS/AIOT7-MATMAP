package com.aloha.matmap.repository;

import static com.aloha.matmap.domain.restaurant.entity.QRestaurant.restaurant;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.aloha.matmap.domain.restaurant.RestaurantSearchRequest;
import com.aloha.matmap.domain.restaurant.RestaurantSearchResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RestaurantSearchRepository {

    private final JPAQueryFactory queryFactory;

    public List<RestaurantSearchResponse> search(RestaurantSearchRequest request) {
        return queryFactory
                .select(Projections.constructor(RestaurantSearchResponse.class,
                        restaurant.id,
                        restaurant.name,
                        restaurant.nickname,
                        restaurant.rating,
                        restaurant.address,
                        restaurant.latitude,
                        restaurant.longitude))
                .from(restaurant)
                .where(
                        keywordContains(request.getKeyword()),
                        ratingGoe(request.getMinRating()),
                        mapIdEq(request.getMapId()))
                .fetch();
    }

    // 값이 없으면 null → 그 조건은 무시됨
    private BooleanExpression keywordContains(String keyword) {
        if (!StringUtils.hasText(keyword)) return null;
        return restaurant.name.containsIgnoreCase(keyword)
                .or(restaurant.nickname.containsIgnoreCase(keyword));
    }

    private BooleanExpression ratingGoe(Double minRating) {
        if (minRating == null) return null;
        return restaurant.rating.goe(BigDecimal.valueOf(minRating));
    }

    private BooleanExpression mapIdEq(Long mapId) {
        if (mapId == null) return null;
        return restaurant.mapNo.eq(mapId);
    }
}