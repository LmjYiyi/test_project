package com.loxie.tested_project.repository;

import com.loxie.tested_project.entity.TellerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Mapper
@Repository
public interface TellerRepository {

    /**
     * 根据认证类型和认证号查询用户信息
     * 
     * @param ssicType 认证类型
     * @param ssicId   认证号
     * @return 用户信息
     */
    Optional<TellerEntity> findBySsicTypeAndSsicId(@Param("ssicType") String ssicType, @Param("ssicId") String ssicId);

    /**
     * 根据认证号查询用户信息
     * 
     * @param ssicId 认证号
     * @return 用户信息
     */
    Optional<TellerEntity> findBySsicId(@Param("ssicId") String ssicId);

    /**
     * 根据用户名查询用户信息
     * 
     * @param username 用户名
     * @return 用户信息
     */
    Optional<TellerEntity> findByUsername(@Param("username") String username);

    /**
     * 根据邮箱查询用户信息
     * 
     * @param email 邮箱
     * @return 用户信息
     */
    Optional<TellerEntity> findByEmail(@Param("email") String email);

    /**
     * 根据手机号查询用户信息
     * 
     * @param phone 手机号
     * @return 用户信息
     */
    Optional<TellerEntity> findByPhone(@Param("phone") String phone);
}
