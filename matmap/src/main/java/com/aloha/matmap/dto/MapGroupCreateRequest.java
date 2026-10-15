package com.aloha.matmap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MapGroupCreateRequest(

    @NotBlank(message = "맛맵 이름은 필수입니다.")
    @Size(max = 100, message = "맛맵 이름은 100자 이하여야 합니다.")
    String name,

    @Size(max = 5000, message = "맛맵 설명은 5000자 이하여야 합니다.")
    String description,

    MapVisibility visibility

) { }
