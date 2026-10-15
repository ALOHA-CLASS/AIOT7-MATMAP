package com.aloha.matmap.service.impl.restaurants;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aloha.matmap.domain.restaurant.RestaurantSearchRequest;
import com.aloha.matmap.domain.restaurant.RestaurantSearchResponse;
import com.aloha.matmap.repository.RestaurantSearchRepository;
import com.aloha.matmap.service.inter.restaurants.RestaurantSearchService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RestaurantSearchServiceImpl implements RestaurantSearchService {

    private final RestaurantSearchRepository restaurantSearchRepository;

    @Override
    public List<RestaurantSearchResponse> search(RestaurantSearchRequest request) {
        return restaurantSearchRepository.search(request);
    }
}