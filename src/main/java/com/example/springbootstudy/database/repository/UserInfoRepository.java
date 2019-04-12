package com.example.springbootstudy.database.repository;

import com.example.springbootstudy.database.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {

    List<UserInfo> findByUserId(String userId);

    int countByUserId(String userId);
}