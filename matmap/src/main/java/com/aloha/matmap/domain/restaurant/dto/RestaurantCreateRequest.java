package com.aloha.matmap.domain.restaurant.dto;

import com.aloha.matmap.domain.restaurant.entity.Visibility;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;


@Getter
@NoArgsConstructor
public class RestaurantCreateRequest {

    @NotBlank(message = "별명은 필수 입력 값입니다.")
    @Size(max = 50, message = "별명은 50자 이내로 입력해주세요.")
    private String nickname;

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