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
@Table(name = "quote_url")
public class QuoteUrl {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

//	@Column(nullable = false)
//	private Long articleId;

	@Column(nullable = false)
	private String url;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date createdDate;

	// @JoinColumn(insertable=false,updatable=false, name="aritlceId")
	// private Article article;

	@PrePersist
	public void prePersist() {
		this.createdDate = new Date();
	}

	public QuoteUrl() {

	}

	public QuoteUrl(String url) {
		super();
		this.url = url;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
