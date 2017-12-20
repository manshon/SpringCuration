package entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
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

    // JPA requirement
    protected User() {}

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @PrePersist
    public void prePersist() {
        this.created_date = new Date();
    }

    @Override
    public String toString() {
        return String.format("Message[id=%d, name='%s', text='%s']", id, name);
    }

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

}