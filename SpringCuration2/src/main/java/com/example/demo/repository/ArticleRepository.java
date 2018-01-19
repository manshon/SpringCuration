package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Article;
import com.example.demo.model.User;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {
	public List<Article> findByContributorId(Long id);

	public List<Article> findByCommunityId(Long id);

	@Query(value="select * from article join article_tags on article.id = article_tags.article_id where community_id = ?1 and tag like %?2% ",nativeQuery=true)
	public List<Article> findByKeywordAndCommunityId(Long communityId, String keyword);

	@Query(value="select * from article join article_tags on article.id = article_tags.article_id where contributor_id = ?1 and tag like %?2% ",nativeQuery=true)
	public List<Article> findByKeywordAndUserId(Long userId, String keyword);

	public List<Article> findByCommunityIdAndAdminUser(Long communityId, User user);
}
