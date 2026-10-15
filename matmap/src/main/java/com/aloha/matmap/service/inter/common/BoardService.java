package com.aloha.matmap.service.inter.common;

import com.aloha.matmap.domain.common.Board;
import com.aloha.matmap.domain.common.QueryParams;
import com.aloha.matmap.service.inter.BaseService;
import com.aloha.matmap.domain.common.PageInfo;

public interface BoardService extends BaseService<Board> {

  PageInfo<Board> page(QueryParams queryParams);

  boolean delete(Long no);                                      // no(PK) 삭제
  boolean deleteById(String id);                                // id(PK) 삭제
  
}
