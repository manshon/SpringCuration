package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Article;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
	public List<Article> findByContributorId(Long id);

	public List<Article> findByCommunityId(Long id);
}
