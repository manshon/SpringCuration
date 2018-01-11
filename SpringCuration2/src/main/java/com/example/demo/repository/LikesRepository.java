package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Likes;

@Repository
public interface LikesRepository extends CrudRepository<Likes, Long> {

}
