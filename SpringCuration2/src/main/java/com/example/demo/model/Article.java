package com.example.demo.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.ToString;

@Entity
@Table(name = "article")
@ToString(exclude = "tags")
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

	// テーブルの結合

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "aritlceId")
	private Set<ArticleTags> tags;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="articleId")
	private Set<Comment> comment;

	@ManyToOne
	@JoinColumn(nullable = false, insertable = false, updatable = false, name = "contributorId")
	private User adminUser;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "likes", joinColumns = @JoinColumn(name = "artilceId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"))
	private Set<User> likeUsers;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "articleId")
	private Set<QuoteUrl> quoteUrl;

	// @ManyToOne
	// @JoinColumn(nullable = false, insertable = false, updatable = false, name =
	// "communityId")
	// private Community community;

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

	public Long getConditions() {
		return conditions;
	}

	public void setConditions(Long conditions) {
		this.conditions = conditions;
	}

	public Set<ArticleTags> getTags() {
		return tags;
	}

	public void setTags(Set<ArticleTags> tags) {
		this.tags = tags;
	}

	public Set<Comment> getComment() {
		return comment;
	}

	public void setComment(Set<Comment> comment) {
		this.comment = comment;
	}

	public Set<QuoteUrl> getQuoteUrl() {
		return quoteUrl;
	}

	public void setQuoteUrl(Set<QuoteUrl> quoteUrl) {
		this.quoteUrl = quoteUrl;
	}

	public void addTags(ArticleTags tempTag) {
		if (tempTag != null) {
			if (tags == null) {
				tags = new HashSet<>();
			}
			tags.add(tempTag);
			tempTag.setArticle(this);
		}
	}

}
