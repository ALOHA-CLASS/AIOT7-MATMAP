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
    private Long id;

    @Column(nullable = false, length = 50)
    private String nickname; // 별명 (식당 이름 등)

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal latitude; // 위도

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal longitude; // 경도

    @Column(nullable = false)
    private Double rating; // 별점 (0.0 ~ 5.0)

    @Column(columnDefinition = "TEXT")
    private String memo; // 메모

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Visibility visibility; // 공개 범위

    // 추후 User 엔티티와 연동 (임시로 Long userId로 두거나 User 객체 매핑)
    @Column(nullable = false)
    private Long userId; // 작성자 ID (권한 검증용)

    @Builder
    public Restaurant(String nickname, BigDecimal latitude, BigDecimal longitude, Double rating, String memo, Visibility visibility, Long userId) {
        this.nickname = nickname;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
        this.memo = memo;
        this.visibility = visibility;
        this.userId = userId;
    }

    // 수정 메서드 (feature/42)
    public void update(String nickname, BigDecimal latitude, BigDecimal longitude, Double rating, String memo, Visibility visibility) {
        this.nickname = nickname;
        this.latitude = latitude;
        this.longitude = longitude;
        this.rating = rating;
        this.memo = memo;
        this.visibility = visibility;
    }
}