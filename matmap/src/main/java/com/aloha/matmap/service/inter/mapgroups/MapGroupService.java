 package com.aloha.matmap.service.inter.mapgroups;

import com.aloha.matmap.dto.MapGroupCreateRequest;
import com.aloha.matmap.dto.MapGroupResponse;

import java.util.List;

public interface MapGroupService {

    // 맛맵 생성
    MapGroupResponse create(Long loginUserNo, MapGroupCreateRequest request);

    List<MapGroupResponse> getMyMaps(Long loginUserNo);

    MapGroupResponse getMapDetail(String id, Long loginUserNo);
}


