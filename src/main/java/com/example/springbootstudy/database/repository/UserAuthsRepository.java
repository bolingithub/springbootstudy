package com.example.springbootstudy.database.repository;

import com.example.springbootstudy.database.entity.UserAuths;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserAuthsRepository extends CrudRepository<UserAuths, Long> {

    /**
     * 通过认证类型找到匹配的用户认证信息
     *
     * @param identityType 认证类型
     * @param identifier   认证识别
     * @param credential   认证凭证
     * @return
     */
    List<UserAuths> findByIdentityTypeAndIdentifierAndCredential(String identityType, String identifier, String credential);

    /**
     * 判断是否已存在匹配信息
     *
     * @param identityType
     * @param identifier
     * @return
     */
    int countByIdentityTypeAndIdentifier(String identityType, String identifier);

    /**
     * 判断这种认证类型的用户信息是否存在
     *
     * @param identityType 认证类型
     * @param identifier   认证识别
     * @return
     */
    boolean existsByIdentityTypeAndIdentifier(String identityType, String identifier);

    /**
     * 通过认证类型找到匹配的用户认证信息
     *
     * @param identityType
     * @param identifier
     * @return
     */
    List<UserAuths> findByIdentityTypeAndIdentifier(String identityType, String identifier);
}