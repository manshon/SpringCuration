package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.Community;

public interface MyCommunityRepository {

	public List<Community> find20RandomCommunity();
}
