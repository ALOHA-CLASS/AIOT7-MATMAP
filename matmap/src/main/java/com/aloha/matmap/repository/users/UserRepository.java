package com.aloha.matmap.repository.users;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.aloha.matmap.domain.users.Users;
import com.aloha.matmap.repository.BaseRepository;

@Repository
public interface UserRepository extends BaseRepository<Users>, UserRepositoryCustom {

  // 회원조회 (권한 목록 포함)
  Optional<Users> findByUsername(String username);

}
