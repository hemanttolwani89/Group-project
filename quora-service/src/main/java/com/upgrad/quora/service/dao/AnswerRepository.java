package com.upgrad.quora.service.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upgrad.quora.service.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer,Integer> {

}
