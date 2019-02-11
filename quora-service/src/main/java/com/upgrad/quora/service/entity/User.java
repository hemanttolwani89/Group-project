package com.upgrad.quora.service.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="users", schema = "public")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(length = 200)
	String uuid;
	
	@Column(length = 30)
	String firstname;
	@Column(length = 30)
	String lastname;
	@Column(length = 30)
	String username;
	@Column(length = 50)
	String email;
	String password;
	@Column(length = 200)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	String salt;
	@Column(length = 30)
	String country;
	@Column(length = 50)
	String aboutme;
	@Column(length = 30)
	String dob;
	@Column(length = 30)
	String role="nonadmin";
	@Column(length = 30)
	String contactnumber;
	
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE) 
	@OneToMany(mappedBy="user")
	List<UserAuth> userAuths; 
	
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE) 
	@OneToMany(mappedBy="user")
	List<Question> questions; 
	
	@JsonIgnore
	@OnDelete(action = OnDeleteAction.CASCADE) 
	@OneToMany(mappedBy="user")
	List<Answer> answers; 
	
	public List<UserAuth> getUserAuths() {
		return userAuths;
	}
	public void setUserAuths(List<UserAuth> userAuths) {
		this.userAuths = userAuths;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	public List<Answer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
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
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAboutme() {
		return aboutme;
	}
	public void setAboutme(String aboutme) {
		this.aboutme = aboutme;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getContactnumber() {
		return contactnumber;
	}
	public void setContactnumber(String contactnumber) {
		this.contactnumber = contactnumber;
	}
}
