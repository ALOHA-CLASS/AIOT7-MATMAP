
package com.aloha.matmap.dto;

import java.time.LocalDateTime;

import com.aloha.matmap.dto.map.MapGroup;
import com.aloha.matmap.dto.map.MapVisibility;

public record MapGroupResponse(
    Long no,
    String id,
    Long ownerNo,
    String name,
    String description,
    MapVisibility visibility,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {

    public static MapGroupResponse from(MapGroup mapGroup) {
        return new MapGroupResponse(
            mapGroup.getNo(),
            mapGroup.getId(),
            mapGroup.getOwner().getNo(),
            mapGroup.getName(),
            mapGroup.getDescription(),
            mapGroup.getVisibility(),
            mapGroup.getCreatedAt(),
            mapGroup.getUpdatedAt()
        );
    }
}