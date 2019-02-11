package com.upgrad.quora.service.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upgrad.quora.service.dao.UserAuthRepository;
import com.upgrad.quora.service.dao.UserRepository;
import com.upgrad.quora.service.entity.User;
import com.upgrad.quora.service.entity.UserAuth;
import com.upgrad.quora.service.exception.AuthenticationFailedException;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import com.upgrad.quora.service.exception.UserNotFoundException;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserAuthRepository userAuthRepository;
	
	public User addUser(User user) throws SignUpRestrictedException
	{
		if(emailExists( user.getEmail()))
		{
			throw new SignUpRestrictedException("SGR-002", "This user has already been registered, try with any other emailId");
		}
		if(usernameExists(user.getUsername()))
		{
			throw new SignUpRestrictedException("SGR-001", "Try any other Username, this Username has already been taken");
		}
		user.setPassword(PasswordCryptographyProvider.encrypt(user.getPassword(), user.getSalt()));
		return userRepository.save(user);
	}
	
	public Boolean emailExists(String email)
	{
		List<User> users =userRepository.findAll();
		for(User user : users)
		{
			if(user.getEmail().equals(email))
			{
				return true;
			}
		}
		return false;
	}
	
	public Boolean usernameExists(String username)
	{
		List<User> users =userRepository.findAll();
		for(User user : users)
		{
			if(user.getUsername().equals(username))
			{
				return true;
			}
		}
		return false;
	}
	
	public User signin(String username,String password) throws AuthenticationFailedException
	{
		User user =getUserByName(username);
		if(user == null)
		{
			throw new AuthenticationFailedException("ATH-001","This username does not exist");
		}
		else
		{
			password = PasswordCryptographyProvider.encrypt(password, user.getSalt());
			if(user.getPassword().equals(password))
			{
				UserAuth auth = new UserAuth();
				auth.setUser(user);
				JwtTokenProvider provider =new JwtTokenProvider("");
				auth.setAccessToken(provider.generateToken(user.getUuid(), auth.getLoginAt(), auth.getLogoutAt()));
				userAuthRepository.save(auth);
				return user;
			}
			else
			{
				throw new AuthenticationFailedException("ATH-002","Password failed");
			}
		}
	}
	
	User getUserByName(String username)
	{
		List<User> users =userRepository.findAll();
		for(User user : users)
		{
			if(user.getUsername().equals(username))
			{
				return user;
			}
		}
		return null;
	}
	
	public User getUserByUUID(String uuid)
	{
		List<User> users =userRepository.findAll();
		for(User user : users)
		{
			if(user.getUuid().equals(uuid))
			{
				return user;
			}
		}
		return null;
	}
	
	public User deleteUser(String uuid) throws UserNotFoundException
	{
		User user = getUserByUUID(uuid);
		if(user==null)
		{
			throw new UserNotFoundException("USR-001", "User with entered uuid to be deleted does not exist");
		}
		else
		{
			userRepository.delete(user);
			return user;
		}
	}
	
	public User getUserById(String uuid) throws UserNotFoundException
	{
		User user = getUserByUUID(uuid);
		if(user==null)
		{
			throw new UserNotFoundException("USR-001", "User with entered uuid does not exist");
		}
		else
		{
			return user;
		}
	}

}
