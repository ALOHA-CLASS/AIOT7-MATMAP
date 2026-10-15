package com.aloha.matmap.repository.mapgroups;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.aloha.matmap.domain.mapgroups.MapGroups;

public interface MapGroupRepository
        extends JpaRepository<MapGroups, Long> {

    // 문자열 식별자(id)로 맛맵 조회
    @Query("SELECT m FROM MapGroup m WHERE m.id = :id")
    Optional<MapGroups> findByPublicId(@Param("id") String id);

    // 로그인한 사용자가 소유한 맛맵 목록
    List<MapGroups> findAllByOwner_NoOrderByCreatedAtDesc(Long ownerNo);

    // 맛맵의 문자열 ID로 상세 조회
    Optional<MapGroups> findById(String id);
}

