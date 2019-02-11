package com.upgrad.quora.service.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upgrad.quora.service.entity.Question;

public interface QuestionRepository extends JpaRepository<Question,Integer> {

}
