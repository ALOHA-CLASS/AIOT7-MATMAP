package com.aloha.matmap.repository.common;

import com.aloha.matmap.domain.common.Board;
import com.aloha.matmap.domain.common.PageInfo;
import com.aloha.matmap.domain.common.QueryParams;

public interface BoardRepositoryCustom {

  // 검색 조건 페이징 조회
  PageInfo<Board> search(QueryParams queryParams);

}
