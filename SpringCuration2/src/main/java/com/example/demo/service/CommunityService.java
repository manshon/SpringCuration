package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Community;
import com.example.demo.repository.CommunityRepository;

@Service
public class CommunityService {

	@Autowired
	private CommunityRepository repository;

    @Transactional
    public void registerCommunity(String name,Long adminId, String content) {
        Community community = new Community(name, adminId, content);
        repository.save(community);
    }

}
