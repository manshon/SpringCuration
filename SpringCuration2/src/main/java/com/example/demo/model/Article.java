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
@Table(name="article")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

    @Column(nullable = false)
	private String title;

    @Column(nullable = false)
	private Long contributorId;

    @Column(nullable = false)
	private String content;

    @Column(nullable = false)
	private Long conditions;

    @Column(nullable = false)
	private Long communityId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
	private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;



    @PrePersist
    public void prePersist() {
        this.createdDate = new Date();
    }



	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public Long getContributorId() {
		return contributorId;
	}


	public void setContributorId(Long contributorId) {
		this.contributorId = contributorId;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Long isCondition() {
		return conditions;
	}


	public void setCondition(Long conditions) {
		this.conditions = conditions;
	}


	public Long getCommunityId() {
		return communityId;
	}


	public void setCommunityId(Long communityId) {
		this.communityId = communityId;
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



}
