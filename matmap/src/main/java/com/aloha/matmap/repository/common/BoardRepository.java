package com.aloha.matmap.repository.common;

import org.springframework.stereotype.Repository;

import com.aloha.matmap.domain.common.Board;
import com.aloha.matmap.repository.BaseRepository;

@Repository
public interface BoardRepository extends BaseRepository<Board>, BoardRepositoryCustom {

}
