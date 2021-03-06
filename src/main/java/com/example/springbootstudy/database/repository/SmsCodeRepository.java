package com.example.springbootstudy.database.repository;

import com.example.springbootstudy.database.entity.SmsCode;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SmsCodeRepository extends CrudRepository<SmsCode, Long> {

    List<SmsCode> findByPhoneAndTypeAndStatus(String phone, long type, long status);

}