package com.naveend3v.bookshop.jwt;

import com.naveend3v.bookshop.entity.UserInfo;
import com.naveend3v.bookshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<UserInfo> findByName(String username) {
        return userRepository.findByName(username);
    }

    @Override
    public UserInfo saveUser(UserInfo userInfo) {
        return userRepository.save(userInfo);
    }
}
