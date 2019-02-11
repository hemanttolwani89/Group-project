package com.upgrad.quora.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.upgrad.quora.service.business.UserAuthService;
import com.upgrad.quora.service.business.UserService;
import com.upgrad.quora.service.entity.User;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;

@RestController
public class CommunController {
	
	@Autowired
	UserAuthService authService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/userprofile/{userId}")
    public User getUser(@PathVariable(value="userId",required=true) final String userId,@RequestHeader("Authorization") String token) throws AuthorizationFailedException, UserNotFoundException {
		authService.tokenIsValid(token);
		User user = userService.getUserById(userId);
		return user;
    }
}
