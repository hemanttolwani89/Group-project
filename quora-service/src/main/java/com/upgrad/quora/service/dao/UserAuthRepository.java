package com.upgrad.quora.service.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upgrad.quora.service.entity.UserAuth;

public interface UserAuthRepository extends JpaRepository<UserAuth,Long> {

}
