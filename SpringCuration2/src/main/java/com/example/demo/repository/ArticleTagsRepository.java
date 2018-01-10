package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.ArticleTags;

public interface ArticleTagsRepository extends CrudRepository<ArticleTags, Long> {

}
