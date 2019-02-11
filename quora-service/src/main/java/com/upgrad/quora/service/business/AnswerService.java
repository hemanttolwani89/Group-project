package com.upgrad.quora.service.business;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upgrad.quora.service.dao.AnswerRepository;
import com.upgrad.quora.service.entity.Answer;
import com.upgrad.quora.service.entity.Question;
import com.upgrad.quora.service.entity.UserAuth;
import com.upgrad.quora.service.exception.AnswerNotFoundException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;

@Service
public class AnswerService {
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	AnswerRepository answerRepository;
	
	public Answer add(String uuid,String token,Answer answer) throws AuthorizationFailedException, InvalidQuestionException
	{
		Question question = questionService.getQuestionById(uuid, token);
		answer.setQuestion(question);
		answerRepository.save(answer);
		return answer;
	}
	
	public Answer edit(String uuid,String token,Answer answer) throws AuthorizationFailedException, AnswerNotFoundException
	{
		Answer oldAnswer = getAnswerById(uuid, token);
		oldAnswer.setAns(answer.getAns());
		answerRepository.save(oldAnswer);
		return oldAnswer;
	}
	
	public Answer delete(String uuid,String token) throws AuthorizationFailedException, AnswerNotFoundException
	{
		Answer answer = getAnswerById(uuid, token);
		answerRepository.delete(answer);
		return answer;
	}
	
	@Autowired
	UserAuthService authService;
	
	public Answer getAnswerById(String uuid,String token) throws AuthorizationFailedException, AnswerNotFoundException
	{
		authService.tokenIsValid(token);
		List<Answer> answers = answerRepository.findAll();
		for(Answer answer : answers)
		{
			if(answer.getUuid().equals(uuid))
			{
				UserAuth auth = authService.getTokenAuth(token);
				if(auth != null && auth.getUser().equals(answer.getUser()))
					return answer;
				else
					throw new AuthorizationFailedException("ATHR-003", "Only the answer owner can edit the answer");
			}
		}
		throw new AnswerNotFoundException("ANS-001", "Entred answer uuid do not exists");
	}
	
	public Answer getAnswerByIdAuthAdmin(String uuid,String token) throws AuthorizationFailedException, AnswerNotFoundException
	{
		authService.tokenIsValid(token);
		List<Answer> answers = answerRepository.findAll();
		for(Answer answer : answers)
		{
			if(answer.getUuid().equals(uuid))
			{
				UserAuth auth = authService.getTokenAuth(token);
				if(auth != null && (auth.getUser().equals(answer.getUser()) || auth.getUser().getRole().equalsIgnoreCase("admin")))
					return answer;
				else
					throw new AuthorizationFailedException("ATHR-003", "Only the answer owner or admin can delete the answer");
			}
		}
		throw new AnswerNotFoundException("ANS-001", "Entred answer uuid do not exists");
	}
	
	public List<Answer> getAnswersByQuestionId(String uuid,String token) throws AuthorizationFailedException, InvalidQuestionException
	{
		Question question = questionService.getQuestionById(uuid, token);
		List<Answer> all = answerRepository.findAll();
		List<Answer> out = new ArrayList<>();
		for(Answer answer : all)
		{
			if(answer.getQuestion().equals(question))
			{
				out.add(answer);
			}
		}
		return out;
	}

}
