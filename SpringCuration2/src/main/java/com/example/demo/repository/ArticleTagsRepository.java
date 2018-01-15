package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ArticleTags;

@Repository
public interface ArticleTagsRepository extends CrudRepository<ArticleTags, Long> {

}
