package com.aloha.matmap.repository.mapgroups;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MapGroupRepository
        extends JpaRepository<MapGroup, Long> {

    // 문자열 식별자(id)로 맛맵 조회
    @Query("SELECT m FROM MapGroup m WHERE m.id = :id")
    Optional<MapGroup> findByPublicId(@Param("id") String id);
}

