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
@Table(name="community_tags")
public class CommunityTags {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable=false)
	private Long articleId;

	@Column(nullable=false)
	private String tag;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
	private Date createdDate;


	@PrePersist
    public void prePersist() {
        this.createdDate = new Date();
    }
}
