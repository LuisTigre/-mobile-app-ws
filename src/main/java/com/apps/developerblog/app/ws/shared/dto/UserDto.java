package com.apps.developerblog.app.ws.shared.dto;

import java.io.Serializable;

public class UserDto implements Serializable{

	private static final long serialVersionUID = 8923046816157939756L;
	
	private long id;
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String encryptedPassword;
	private String emailVerificatioToken;
	private Boolean emailVerificatioStatus = false;
//	private List<AdressDTO> addresses;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public String getEncryptedPassword() {
		return encryptedPassword;
	}
	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}
	public String getEmailVerificatioToken() {
		return emailVerificatioToken;
	}
	public void setEmailVerificatioToken(String emailVerificatioToken) {
		this.emailVerificatioToken = emailVerificatioToken;
	}
	public String getEmailVerificatioStatus() {
		return emailVerificatioStatus;
	}
	public void setEmailVerificatioStatus(String emailVerificatioStatus) {
		this.emailVerificatioStatus = emailVerificatioStatus;
	}
	
	
}
