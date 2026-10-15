package com.aloha.matmap.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aloha.matmap.domain.restaurant.RestaurantSearchRequest;
import com.aloha.matmap.domain.restaurant.RestaurantSearchResponse;
import com.aloha.matmap.service.inter.restaurants.RestaurantSearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantSearchController {

    private final RestaurantSearchService restaurantSearchService;

    @GetMapping("/search")
    public ResponseEntity<List<RestaurantSearchResponse>> search(
            RestaurantSearchRequest request) {
        return ResponseEntity.ok(restaurantSearchService.search(request));
    }
}