package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Community;

@Component
@Repository
public interface CommunityRepository extends CrudRepository<Community, Long> {
	public List<Community> findByAdminId(Long id);
}