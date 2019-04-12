package com.example.springbootstudy.database.repository;

import com.example.springbootstudy.database.entity.UserFollow;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserFollowRepository extends CrudRepository<UserFollow, Long> {

    List<UserFollow> findByUserIdAndStatus(String userId, long status);

    int countByUserIdAndFollowId(String userId, String followId);
}