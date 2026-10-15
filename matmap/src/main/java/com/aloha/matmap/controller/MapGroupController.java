package com.aloha.matmap.controller;

import com.aloha.matmap.dto.MapGroupCreateRequest;
import com.aloha.matmap.dto.MapGroupResponse;
import com.aloha.matmap.service.inter.mapgroups.MapGroupService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/maps")
public class MapGroupController {

    private final MapGroupService mapGroupService;

    public MapGroupController(MapGroupService mapGroupService) {
        this.mapGroupService = mapGroupService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MapGroupResponse create(
            @AuthenticationPrincipal(expression = "no")
            Long loginUserNo,
            @Valid @RequestBody MapGroupCreateRequest request
    ) {
        return mapGroupService.create(loginUserNo, request);
    }
}

