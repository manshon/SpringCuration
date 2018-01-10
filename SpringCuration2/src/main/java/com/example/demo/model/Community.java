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
@Table(name="community")
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

    public Community() {

    }

    public Community( String name, Long adminId, String content) {
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
}