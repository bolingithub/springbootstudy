package com.example.springbootstudy.database;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<Users, Integer> {

    List<Users> findByNickname(String nickname);
}
