package com.upgrad.quora.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.upgrad.quora.api.Response;
import com.upgrad.quora.service.business.UserAuthService;
import com.upgrad.quora.service.business.UserService;
import com.upgrad.quora.service.entity.User;
import com.upgrad.quora.service.entity.UserAuth;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import com.upgrad.quora.service.exception.SignOutRestrictedException;
import com.upgrad.quora.service.exception.SignUpRestrictedException;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/user/signup")
    public Object signup(@RequestParam(value = "userName", required=true) final String username,
    		@RequestParam(value = "firstName", required=true) final String firstname,
    		@RequestParam(value = "lastName", required=true) final String lastname,
    		@RequestParam(value = "emailAddress", required=true) final String email,
    		@RequestParam(value = "country", required=true) final String country,
    		@RequestParam(value = "password", required=true) final String password,
    		@RequestParam(value = "aboutMe", required=true) final String aboutme,
    		@RequestParam(value = "dob", required=true) final String dob,
    		@RequestParam(value = "contactNumber", required=true) final String contactNumber) throws SignUpRestrictedException{
		User user =new User();
		user.setAboutme(aboutme);
		user.setContactnumber(contactNumber);
		user.setCountry(country);
		user.setDob(dob);
		user.setUsername(username);
		user.setEmail(email);
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setPassword(password);
		User newuser = userService.addUser(user);
		return new Response(newuser.getUuid(),"USER SUCCESSFULLY REGISTERED");
    }
	
	@PostMapping("/user/signin")
	Object signin(@RequestHeader("Authorization") String content) throws AuthenticationFailedException
	{
		content = content.replace("Basic ", "");
		String username = content.split(":")[0];
		String password = content.split(":")[1];
		User user = userService.signin(username, password);
		return new Response(user.getUuid(),"SIGNED IN SUCCESSFULLY");
	}
	
	@Autowired
	UserAuthService userAuthService;
	
	@PostMapping("/user/signout")
	Object signout(@RequestHeader("Authorization") String token) throws SignOutRestrictedException
	{
		UserAuth auth = userAuthService.signout(token);
		return new Response(auth.getUser().getUuid(), "SIGNED OUT SUCCESSFULLY");
	}
}
