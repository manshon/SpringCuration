package com.example.demo.form;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class CommentForm implements Serializable{

	@NotEmpty
	@Size(max = 1000)
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
