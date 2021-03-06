package com.example.demo.form;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class SignupForm implements Serializable {

	@NotEmpty(message="名前を入力してください")
	@Size(min=1,max=20,message="20文字以内です")
	private String userName;

	@NotEmpty(message="パスワードを入力してください")
	@Size(min=1, max=20,message="２０文字以内です")
	private String password;


	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
