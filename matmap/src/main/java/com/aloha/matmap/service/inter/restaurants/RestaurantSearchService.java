package com.aloha.matmap.service.inter.restaurants;

import java.util.List;

import com.aloha.matmap.domain.restaurant.RestaurantSearchRequest;
import com.aloha.matmap.domain.restaurant.RestaurantSearchResponse;

public interface RestaurantSearchService {

    List<RestaurantSearchResponse> search(RestaurantSearchRequest request);
}