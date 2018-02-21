package com.example.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "comment")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private Long articleId;

	@Column
	private Long userId;

	@Column
	private String content;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date updatedDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, insertable = false, updatable = false, name = "userId")
	private User contributor;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, insertable = false, updatable = false, name = "articleId")
	private Article article;

	@PrePersist
	public void prePersist() {
		this.createdDate = new Date();
		this.updatedDate = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public User getContributor() {
		return contributor;
	}

	public void setContributor(User contributor) {
		this.contributor = contributor;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

}
