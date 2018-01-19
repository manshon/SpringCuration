package com.example.demo.form;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class ArticleForm implements Serializable {

	@NotEmpty(message = "タイトルを入力してください")
	@Size(max = 50)
	private String title;

	private String tags;

	private String quoteUrls;

	private Long conditions;

	@NotEmpty(message = "本文を入力してください")
	@Size(max = 1000)
	private String content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getQuoteUrls() {
		return quoteUrls;
	}

	public void setQuoteUrls(String quoteUrls) {
		this.quoteUrls = quoteUrls;
	}

	public Long getConditions() {
		return conditions;
	}

	public void setConditions(Long conditions) {
		this.conditions = conditions;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
