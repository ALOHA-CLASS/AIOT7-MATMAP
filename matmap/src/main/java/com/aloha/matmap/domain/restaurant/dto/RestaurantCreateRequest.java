package com.aloha.matmap.domain.restaurant.dto;

import com.aloha.matmap.domain.restaurant.entity.Visibility;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;


@Getter
@NoArgsConstructor
public class RestaurantCreateRequest {

    @NotNull(message = "맛맵 번호는 필수 입력 값입니다.")
    private Long mapNo;

    @NotBlank(message = "맛집 이름은 필수 입력 값입니다.")
    @Size(max = 200, message = "맛집 이름은 200자 이내로 입력해주세요.")
    private String name;

    @Size(max = 100, message = "별명은 100자 이내로 입력해주세요.")
    private String nickname;

    @Size(max = 500, message = "주소는 500자 이내로 입력해주세요.")
    private String address;

    @NotNull(message = "위도는 필수 입력 값입니다.")
    private BigDecimal latitude;

    @NotNull(message = "경도는 필수 입력 값입니다.")
    private BigDecimal longitude;

    @NotNull(message = "별점은 필수 입력 값입니다.")
    @Min(value = 0, message = "별점은 최소 0점 이상이어야 합니다.")
    @Max(value = 5, message = "별점은 최대 5점까지 가능합니다.")
    private Double rating;

    private String memo;

    @NotNull(message = "공개 범위는 필수 입력 값입니다.")
    private Visibility visibility;
}