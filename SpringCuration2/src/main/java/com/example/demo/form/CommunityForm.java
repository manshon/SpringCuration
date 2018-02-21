package com.example.demo.form;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class CommunityForm implements Serializable {

	@NotEmpty(message = "コミュニティ名を入力してください")
	@Size(max = 30)
	private String name;

	private String tags;

	@NotEmpty(message = "コミュニティの説明を入力してください")
	private String content;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
