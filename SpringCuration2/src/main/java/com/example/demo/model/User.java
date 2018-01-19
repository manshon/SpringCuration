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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String password;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable = false)
	private Date created_date;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated_date;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	private Set<Community> adminCommunities;

	// failed to lazily initialize a collection of role: com.example.demo.model.User.followCommunities, could not initialize proxy - no Session

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH }, fetch = FetchType.EAGER, mappedBy = "followUsers")
	private Set<Community> followCommunities;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "adminUser")
	private Set<Article> adminArticles;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH }, fetch = FetchType.EAGER, mappedBy = "likeUsers")
	private Set<Article> likeArticles;

	// mappedBy reference an unknown target entity property:
	// com.example.demo.model.Article.adminUsers in
	// com.example.demo.model.User.adminArticles

	// JPA requirement
	public User() {

	}

	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}

	@PrePersist
	public void prePersist() {
		this.created_date = new Date();
	}

	// @Override
	// public String toString() {
	// return String.format("Message[id=%d, name='%s', text='%s']", id, name);
	// }

	public Long getId() {
		return id;
	}

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

	public Date getCreatedAt() {
		return created_date;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Date getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(Date updated_date) {
		this.updated_date = updated_date;
	}

	public Set<Community> getAdminCommunities() {
		return adminCommunities;
	}

	public void setAdminCommunities(Set<Community> adminCommunities) {
		this.adminCommunities = adminCommunities;
	}

	public Set<Community> getFollowCommunities() {
		return followCommunities;
	}

	public void setFollowCommunities(Set<Community> followCommunities) {
		this.followCommunities = followCommunities;
	}

	public Set<Article> getAdminArticles() {
		return adminArticles;
	}

	public void setAdminArticles(Set<Article> adminArticles) {
		this.adminArticles = adminArticles;
	}

	public Set<Article> getLikeArticles() {
		return likeArticles;
	}

	public void setLikeArticles(Set<Article> likeArticles) {
		this.likeArticles = likeArticles;
	}

	public void addFollowCommunity(Community tempCommunity) {
		if (tempCommunity != null) {
			if (followCommunities == null) {
				followCommunities = new HashSet<Community>();
			}
			followCommunities.add(tempCommunity);
		}
	}

	public void addAdminArticle(Article tempArticle) {
		if(tempArticle != null) {
			if(adminArticles == null) {
				adminArticles = new HashSet<Article>();
			}
			adminArticles.add(tempArticle);
		}
	}

	public void addLikeArticles(Article tempArticle) {
		if(tempArticle != null) {
			if(likeArticles == null) {
				likeArticles = new HashSet<>();
			}
			likeArticles.add(tempArticle);
		}
	}

}