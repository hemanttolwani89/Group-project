package com.upgrad.quora.service.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upgrad.quora.service.dao.UserAuthRepository;
import com.upgrad.quora.service.entity.UserAuth;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.SignOutRestrictedException;

@Service
public class UserAuthService {
	
	@Autowired
	UserAuthRepository authRepository;
	
	public UserAuth getTokenAuth(String token)
	{
		List<UserAuth> auths = authRepository.findAll();
		for(UserAuth auth : auths)
		{
			if(auth.getAccessToken().equals(token))
			{
				return auth;
			}
		}
		return null;
	}
	
	public UserAuth signout(String token) throws SignOutRestrictedException
	{
		UserAuth auth =getTokenAuth(token);
		if(auth!=null)
		{
			return auth;
		}
		else
		{
			throw new SignOutRestrictedException("SGR-001","User is not Signed in");
		}
	}
	
	public Boolean tokenIsValid(String token) throws AuthorizationFailedException
	{
		UserAuth auth =getTokenAuth(token);
		if(auth!=null)
		{
			if(auth.getLogoutAt()!=null)
			{
				throw new AuthorizationFailedException("ATHR-002","User is signed out.Sign in first to get user details");
			}
			else
			{
				return true;
			}
		}
		else
		{
			throw new AuthorizationFailedException("ATHR-001","User has not signed in");
		}
	}
	
	public Boolean isAdminToken(String token) throws AuthorizationFailedException
	{
		UserAuth auth =getTokenAuth(token);
		if(auth!=null)
		{
			if(auth.getLogoutAt()!=null)
			{
				throw new AuthorizationFailedException("ATHR-002","User is signed out");
			}
			else
			{
				if(auth.getUser().getRole().equalsIgnoreCase("admin"))
				{
					return true;
				}
				else
				{
					throw new AuthorizationFailedException("ATHR-003","Unauthorized Access, Entered user is not an admin");
				}
			}
		}
		else
		{
			throw new AuthorizationFailedException("ATHR-001","User has not signed in");
		}
	}

}
