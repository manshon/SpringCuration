package com.example.demo.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.demo.model.User;

@Component
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

//	@Autowired
//	private SessionFactory sf;

	public User findByName(String name);
}

