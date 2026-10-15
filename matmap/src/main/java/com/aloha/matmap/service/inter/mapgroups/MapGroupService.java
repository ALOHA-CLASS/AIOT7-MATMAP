 package com.aloha.matmap.service.inter;

import com.aloha.matmap.dto.MapGroupCreateRequest;
import com.aloha.matmap.dto.MapGroupResponse;

public interface MapGroupService {

    // 맛맵 생성
    MapGroupResponse create(Long loginUserNo, MapGroupCreateRequest request);
}


