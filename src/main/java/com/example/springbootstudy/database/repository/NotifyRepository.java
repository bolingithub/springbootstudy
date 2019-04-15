package com.example.springbootstudy.database.repository;

import com.example.springbootstudy.database.entity.Notify;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotifyRepository extends CrudRepository<Notify, Long> {

    @Query(value = "select * from notify as t where t.user_id=?1 and t.type=?2 and t.status=?3 limit ?4 offset ?5", nativeQuery = true)
    List<Notify> findByUserIdAndTypeAndStatus(String user_id, long type, long status, long limit, long offset);
}
