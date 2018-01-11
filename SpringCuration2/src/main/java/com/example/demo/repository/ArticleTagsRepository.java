package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ArticleTags;

@Component
@Repository
public interface ArticleTagsRepository extends CrudRepository<ArticleTags, Long> {

}
