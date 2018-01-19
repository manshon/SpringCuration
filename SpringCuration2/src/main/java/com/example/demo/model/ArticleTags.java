package com.example.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "article_tags")
public class ArticleTags {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

//	@Column(nullable = false)
//	private Long articleId;

	@Column(nullable = false)
	private String tag;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date createdDate;

	// @ManyToOne(fetch=FetchType.EAGER)
	// @JoinColumn(nullable=false, insertable=false, updatable=false, name="id")
	// private Article article;

	@PrePersist
	public void prePersist() {
		this.createdDate = new Date();
	}

	public ArticleTags() {

	}

	public ArticleTags(String tag) {
		super();
		this.tag = tag;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
