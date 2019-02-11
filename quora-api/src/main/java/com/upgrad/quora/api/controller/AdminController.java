package com.upgrad.quora.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.upgrad.quora.api.Response;
import com.upgrad.quora.service.business.UserAuthService;
import com.upgrad.quora.service.business.UserService;
import com.upgrad.quora.service.entity.User;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;

@RestController
public class AdminController {
	
	@Autowired
	UserAuthService authService;
	
	@Autowired
	UserService userService;
	
	@DeleteMapping("/admin/user/{userId}")
    public Object getUser(@PathVariable(value="userId",required=true) final String userId,@RequestHeader("Authorization") String token) throws AuthorizationFailedException, UserNotFoundException {
		authService.isAdminToken(token);
		User user = userService.deleteUser(userId);
		return new Response(user.getUuid(),"USER SUCCESSFULLY DELETED");
    }
}
