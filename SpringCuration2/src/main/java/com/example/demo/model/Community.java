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

@Entity
@Table(name = "community")
public class Community {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Long adminId;

	@Column(nullable = false)
	private String content;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date createdDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	@JoinColumn(name = "communityId")
	private Set<CommunityTags> tags;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(insertable = false, updatable = false, name = "adminId")
	private User admin;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinTable(name = "user_community_follow", joinColumns = @JoinColumn(name = "community_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private Set<User> followUsers;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Article> articles;

	public Community() {

	}

	public Community(String name, Long adminId, String content) {
		super();
		this.name = name;
		this.adminId = adminId;
		this.content = content;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
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

	public Set<CommunityTags> getTags() {
		return tags;
	}

	public void setTags(Set<CommunityTags> tags) {
		this.tags = tags;
	}

	public Set<User> getFollowUsers() {
		return followUsers;
	}

	public void setFollowUsers(Set<User> followUsers) {
		this.followUsers = followUsers;
	}

	public Set<Article> getArticles() {
		return articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	public void addTags(CommunityTags tempTag) {
		if (tempTag != null) {
			if (tags == null) {
				tags = new HashSet<>();
			}
			tags.add(tempTag);
			// tempTag.setCommunity(this);
		}
	}

	public void addArticle(Article tempArticle) {
		if (tempArticle != null) {
			if (articles == null) {
				articles = new HashSet<>();
			}
			articles.add(tempArticle);
		}
	}

	public void removeArticle(Article tempArticle) {
		boolean isRemove = this.getArticles().remove(tempArticle);

	}

	public void addUser(User tempUser) {
		if (tempUser != null) {
			if (followUsers == null) {
				followUsers = new HashSet<>();
			}
			followUsers.add(tempUser);
		}
	}

	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}

}