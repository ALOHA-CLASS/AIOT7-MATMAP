package com.aloha.matmap.repository.common;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.aloha.matmap.domain.common.Media;
import com.aloha.matmap.repository.BaseRepository;

@Repository
public interface MediaRepository extends BaseRepository<Media> {

  Optional<Media> findFirstByParentIdOrderByNoAsc(String parentId);

  List<Media> findByParentId(String parentId);

  List<Media> findByContent(String content);

}
