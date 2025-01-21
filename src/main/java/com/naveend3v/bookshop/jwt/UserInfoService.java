package com.naveend3v.bookshop.jwt;

import com.naveend3v.bookshop.entity.UserInfo;

import java.util.Optional;

public interface UserInfoService {
    Optional<UserInfo> findByName(String username);
    UserInfo saveUser(UserInfo userInfo);
}
