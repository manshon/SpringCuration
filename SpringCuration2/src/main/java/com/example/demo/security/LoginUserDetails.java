package com.example.demo.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class LoginUserDetails extends User {

	private final com.example.demo.model.User user;

	public LoginUserDetails(com.example.demo.model.User user) {
		super(user.getName(),user.getPassword(),AuthorityUtils.createAuthorityList("ROLE_USER"));
		this.user = user;
	}

	public com.example.demo.model.User getUser(){
		return user;
	}
}
