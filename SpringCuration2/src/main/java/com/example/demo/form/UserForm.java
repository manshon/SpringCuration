package com.example.demo.form;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class UserForm implements Serializable{
	@NotEmpty(message="名前を入力してください")
	@Size(min=1,max=20)
	private String name;

	@NotEmpty(message="パスワードを入力してください")
	@Size(min=1, max=20)
	private String password;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
