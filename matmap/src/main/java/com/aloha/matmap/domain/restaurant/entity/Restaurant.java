
package com.aloha.matmap.domain.restaurant.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "restaurants")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no; // DDL의 PK (no)

    @Column(nullable = false, length = 64, unique = true)
    private String id; // DDL의 UK (id)

    @Column(name = "map_no", nullable = false)
    private Long mapNo; // 소속 맛맵 번호

    @Column(nullable = false, length = 200)
    private String name; // 맛집 이름 (필수)

    @Column(length = 100)
    private String nickname; // 별명

    @Column(length = 500)
    private String address; // 주소

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(nullable = false, precision = 2, scale = 1)
    private Double rating; // 별점 (0.0 ~ 5.0)

    @Column(columnDefinition = "TEXT")
    private String memo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Visibility visibility = Visibility.MAP_ONLY;

    @Builder
    public Restaurant(String id, Long mapNo, String name, String nickname, String address, 
                      BigDecimal latitude, BigDecimal longitude, Double rating, String memo, Visibility visibility) {
        this.id = id;
        this.mapNo = mapNo;
        this.name = name;
        this.nickname = nickname;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
        this.memo = memo;
        if (visibility != null) {
            this.visibility = visibility;
        }
    }

    public void update(String name, String nickname, String address, BigDecimal latitude, BigDecimal longitude, Double rating, String memo, Visibility visibility) {
        this.name = name;
        this.nickname = nickname;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
        this.memo = memo;
        this.visibility = visibility;
    }
}