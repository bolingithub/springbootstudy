package com.example.springbootstudy.database.repository;

import com.example.springbootstudy.database.entity.UserFollow;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserFollowRepository extends CrudRepository<UserFollow, Long> {

    List<UserFollow> findByFollowIdAndStatus(String followId, long status, Pageable pageable);

    int countByUserIdAndFollowId(String userId, String followId);
}