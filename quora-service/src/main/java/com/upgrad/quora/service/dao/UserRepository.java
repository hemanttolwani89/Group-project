package com.upgrad.quora.service.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upgrad.quora.service.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {

}
