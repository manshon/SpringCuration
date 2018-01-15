package com.example.test.user;

import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.model.Community;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUser {

	@Autowired
	UserRepository repository;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testFollowCommuniy(){
		User user = new User();
		user.setName("Jhon");
		user.setPassword("password");

		HashSet<Community> followedCommunity = new HashSet<Community>();
		Community com = new Community();
		com.setName("health");
		com.setContent("About health");

		followedCommunity.add(com);

		user.setFollowCommunities(followedCommunity);

		repository.save(user);
	}

}
