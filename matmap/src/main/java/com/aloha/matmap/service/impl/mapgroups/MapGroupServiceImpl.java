
package com.aloha.matmap.service.impl;

import com.aloha.matmap.domain.mapgroups.MapGroup;
import com.aloha.matmap.repository.mapgroups.MapGroupRepository;
import com.aloha.matmap.dto.MapGroupCreateRequest;
import com.aloha.matmap.dto.MapGroupResponse;
import com.aloha.matmap.domain.users.Users;
import com.aloha.matmap.repository.users.UserRepository;

import jakarta.persistence.EntityManager;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class MapGroupServiceImpl implements MapGroupService {

    private final MapGroupRepository mapGroupRepository;
    private final UserRepository userRepository;
    private final EntityManager entityManager;

    public MapGroupServiceImpl(
            MapGroupRepository mapGroupRepository,
            UserRepository userRepository,
            EntityManager entityManager
    ) {
        this.mapGroupRepository = mapGroupRepository;
        this.userRepository = userRepository;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public MapGroupResponse create(
            Long loginUserNo,
            MapGroupCreateRequest request
    ) {
        // 1. 로그인한 회원 조회
        Users owner = userRepository.findById(loginUserNo)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "로그인 회원 정보를 찾을 수 없습니다."
                ));

        // 2. 맛맵의 문자열 고유 ID 생성
        String publicId = UUID.randomUUID().toString();

        // 3. 맛맵 엔티티 생성
        MapGroup mapGroup = new MapGroup(
                publicId,
                owner,
                request.name(),
                request.description(),
                request.visibility()
        );

        // 4. DB 저장
        MapGroup saved = mapGroupRepository.saveAndFlush(mapGroup);

        // 5. DB에서 자동 생성한 시간 컬럼을 다시 읽음
        entityManager.refresh(saved);

        // 6. 응답 DTO 반환
        return MapGroupResponse.from(saved);
    }
}

