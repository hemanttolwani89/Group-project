package com.upgrad.quora.service.entity;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_auth", schema = "public")
public class UserAuth {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(length = 200)
	String uuid;
	
	@ManyToOne
	User user;
	
	@Column(length = 500)
	String AccessToken;
	ZonedDateTime expiresAt;
	ZonedDateTime loginAt;
	ZonedDateTime logoutAt;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getAccessToken() {
		return AccessToken;
	}
	public void setAccessToken(String accessToken) {
		AccessToken = accessToken;
	}
	public ZonedDateTime getExpiresAt() {
		return expiresAt;
	}
	public void setExpiresAt(ZonedDateTime expiresAt) {
		this.expiresAt = expiresAt;
	}
	public ZonedDateTime getLoginAt() {
		return loginAt;
	}
	public void setLoginAt(ZonedDateTime loginAt) {
		this.loginAt = loginAt;
	}
	public ZonedDateTime getLogoutAt() {
		return logoutAt;
	}
	public void setLogoutAt(ZonedDateTime logoutAt) {
		this.logoutAt = logoutAt;
	}

}
