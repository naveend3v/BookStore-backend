package com.naveend3v.bookshop.service;

import com.naveend3v.bookshop.model.UserInfo;
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

    @Override
    public Optional<UserInfo> findById(Integer userId) {
        return userRepository.findById(userId);
    }
}
