package com.example.springbootstudy.database.repository;

import com.example.springbootstudy.database.entity.UserLoginHistory;
import org.springframework.data.repository.CrudRepository;

public interface UserLoginHistoryRepository extends CrudRepository<UserLoginHistory, Long> {

}