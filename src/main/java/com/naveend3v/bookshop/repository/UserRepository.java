package com.naveend3v.bookshop.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.naveend3v.bookshop.entity.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, Integer>{
	Optional<UserInfo> findByName(String username);
}
