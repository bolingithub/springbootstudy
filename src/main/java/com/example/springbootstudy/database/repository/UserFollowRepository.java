package com.example.springbootstudy.database.repository;

import com.example.springbootstudy.database.entity.UserFollow;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserFollowRepository extends CrudRepository<UserFollow, Long> {

    @Query(value = "select * from user_follow as t where t.follow_id=?1 and t.status=?2 limit ?3 offset ?4", nativeQuery = true)
    List<UserFollow> findByFollowIdAndStatus(String followId, long status, long limit, long offset);

    int countByUserIdAndFollowId(String userId, String followId);

    int countByFollowIdAndStatus(String followId, long status);
}