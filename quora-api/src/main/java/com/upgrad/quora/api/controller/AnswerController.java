package com.upgrad.quora.api.controller;

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
import com.upgrad.quora.service.business.AnswerService;
import com.upgrad.quora.service.business.UserService;
import com.upgrad.quora.service.entity.Answer;
import com.upgrad.quora.service.exception.AnswerNotFoundException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;

@RestController
public class AnswerController {
	
	@Autowired
	AnswerService answerService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/question/{questionId}/answer/create")
    public Response add(@RequestParam(value = "answer", required=true) final String content,@PathVariable(value="questionId",required=true) final String questionId,@RequestHeader("Authorization") String token) throws AuthorizationFailedException, InvalidQuestionException {
			Answer answer =new Answer();
			answer.setAns(content);
			answer = answerService.add(questionId, token, answer);
			return new Response(answer.getUuid(), "ANSWER CREATED");
    }
	
	@PutMapping("/answer/edit/{answerId}")
    public Object edit(@RequestParam(value = "content", required=true) final String content,@PathVariable(value="answerId",required=true) final String answerId,@RequestHeader("Authorization") String token) throws AuthorizationFailedException, AnswerNotFoundException {
			Answer answer =new Answer();
			answer.setAns(content);
			answer = answerService.edit(answerId, token, answer);
			return new Response(answer.getUuid(), "ANSWER EDITED");
    }
	
	@DeleteMapping("/answer/delete/{answerId}")
    public Object delete(@PathVariable(value="answerId",required=true) final String answerId,@RequestHeader("Authorization") String token) throws AuthorizationFailedException, AnswerNotFoundException {
			Answer answer = answerService.delete(answerId, token);
			return new Response(answer.getUuid(), "ANSWER DELETED");
    }
	
	@GetMapping("/answer/all/{questionId}")
    public Object all(@PathVariable(value="questionId",required=true) final String questionId,@RequestHeader("Authorization") String token) throws AuthorizationFailedException, InvalidQuestionException {
			List<Answer> answers = answerService.getAnswersByQuestionId(questionId, token);
			return answers;
    }
}
