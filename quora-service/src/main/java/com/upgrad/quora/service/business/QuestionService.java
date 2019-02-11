package com.upgrad.quora.service.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upgrad.quora.service.dao.QuestionRepository;
import com.upgrad.quora.service.entity.*;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;

@Service
public class QuestionService {
	
	@Autowired
	QuestionRepository questionRepository;
	
	@Autowired
	UserAuthService authService;
	
	public List<Question> getAll(String token) throws AuthorizationFailedException
	{
		authService.tokenIsValid(token);
		return questionRepository.findAll();
	}
	
	public Question createQuestion(Question question,String token) throws AuthorizationFailedException
	{
		authService.tokenIsValid(token);
		return questionRepository.save(question);
	}
	
	public Question getQuestionById(String uuid,String token) throws AuthorizationFailedException, InvalidQuestionException
	{
		authService.tokenIsValid(token);
		List<Question> questions = questionRepository.findAll();
		for(Question question : questions)
		{
			if(question.getUuid().equals(uuid))
			{
				return question;
			}
		}
		throw new InvalidQuestionException("QUES-001", "Entred question uuid do not exists");
	}
	
	public Question deleteQuestion(String uuid,String token) throws AuthorizationFailedException, InvalidQuestionException
	{
		Question question =getQuestionById(uuid, token);
		UserAuth auth = authService.getTokenAuth(token);
		if(question.getUser().equals(auth.getUser()) || auth.getUser().getRole().equalsIgnoreCase("admin"))
		{
			questionRepository.delete(question);
			return question;
		}
		else
		{
			throw new AuthorizationFailedException("ATHR-003", "Only the question owner or admin can delete a question");
		}

	}
	
	public Question editQuestion(String uuid,String token,Question newQuestion) throws AuthorizationFailedException, InvalidQuestionException
	{
		Question question =getQuestionById(uuid, token);
		question.setContent(newQuestion.getContent());
		UserAuth auth = authService.getTokenAuth(token);
		if(question.getUser().equals(auth.getUser()))
		{
			questionRepository.save(question);
			return question;
		}
		else
		{
			throw new AuthorizationFailedException("ATHR-003", "Only the question owner can edit the question");
		}

	}

}
