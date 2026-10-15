package com.aloha.matmap.service.impl.users;

import org.springframework.stereotype.Service;

import com.aloha.matmap.domain.users.UserAuth;
import com.aloha.matmap.service.inter.users.UserAuthService;
import com.aloha.matmap.repository.users.UserAuthRepository;
import com.aloha.matmap.service.impl.BaseServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserAuthServiceImpl extends BaseServiceImpl<UserAuth, UserAuthRepository> implements UserAuthService {

}
