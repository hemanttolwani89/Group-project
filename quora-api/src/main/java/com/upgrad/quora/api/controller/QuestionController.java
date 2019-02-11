package com.upgrad.quora.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.upgrad.quora.api.Response;
import com.upgrad.quora.service.business.QuestionService;
import com.upgrad.quora.service.business.UserService;
import com.upgrad.quora.service.entity.Question;
import com.upgrad.quora.service.entity.User;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import com.upgrad.quora.service.exception.UserNotFoundException;

@RestController
public class QuestionController {
	
	@Autowired
	QuestionService questionService;
	
	@GetMapping("/question/all")
    public List<Question> getAll(@RequestHeader("Authorization") String token) throws AuthorizationFailedException {
		List<Question> questions;
		questions = questionService.getAll(token);
		return questions;
    }
	
	@Autowired
	UserService userService;
	
	@GetMapping("/question/all/{userId}")
    public List<Question> getQuestionByUserId(@PathVariable(value="userId",required=true) final String userId,@RequestHeader("Authorization") String token) throws UserNotFoundException, AuthorizationFailedException {
			List<Question> questions = new ArrayList<>();
			List<Question> all = questionService.getAll(token);
			User user = userService.getUserByUUID(userId);
			if(user==null)
			{
				throw new UserNotFoundException("USR-001", "USer with entred uuid whose question details are to be seen does not exist");
			}
			for(Question question : all)
			{
				if(question.getUuid().equals(userId))
				{
					questions.add(question);
				}
			}
			return questions;
    }
	
	@PostMapping("/question/create")
    public Response getUser(@RequestParam(value = "content", required=true) final String content,@RequestHeader("Authorization") String token) throws AuthorizationFailedException {
        Question question = new Question();
        question.setContent(content);
		Question saved = questionService.createQuestion(question, token);
		return new Response(saved.getUuid(), "QUESTION CREATED");
    }
	
	@DeleteMapping("/question/delete/{questionId}")
    public Response delete(@PathVariable(value="questionId",required=true) final String questionId,@RequestHeader("Authorization") String token) throws AuthorizationFailedException, InvalidQuestionException {
		Question question = questionService.deleteQuestion(questionId, token);
		return new Response(question.getUuid(), "QUESTION DELETED");
    }
	
	@PutMapping("/question/edit/{questionId}")
    public Response editQuestionContent(@RequestParam(value = "content", required=true) final String content,@PathVariable(value="questionId",required=true) final String questionId,@RequestHeader("Authorization") String token) throws AuthorizationFailedException, InvalidQuestionException {
		Question newQuestion = new Question();
		newQuestion.setContent(content);
		Question question = questionService.editQuestion(questionId, token,newQuestion);
		return new Response(question.getUuid(), "QUESTION DELETED");
    }
}
