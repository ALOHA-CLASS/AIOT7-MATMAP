package com.aloha.matmap.domain.restaurant;

import lombok.Getter;
import lombok.Setter;

@Getter         // 변수에 저장된 값을 읽어올때 사용
@Setter         // 변수에 새로운 값을 설정할때 사용
public class RestaurantSearchRequest {
    
    // 맛집 이름 검색어
    private String keyword;


}
