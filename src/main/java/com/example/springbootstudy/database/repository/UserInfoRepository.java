package com.example.springbootstudy.database.repository;

import com.example.springbootstudy.database.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;

public interface UserInfoRepository extends CrudRepository<UserInfo, Long> {

}