package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.FollowCommunity;

@Repository
public interface FollowCommunityRepository extends CrudRepository<FollowCommunity, Long> {

}
