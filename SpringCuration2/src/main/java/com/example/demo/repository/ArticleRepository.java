package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Article;
import com.example.demo.model.User;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Long> {

	public Page<Article> findByCommunityIdOrderByUpdatedDateDesc(Long communityId, Pageable pageable);

	public List<Article> findByContributorId(Long id);

	public List<Article> findByContributorIdOrderByUpdatedDateDesc(Long id);

	public List<Article> findByCommunityIdOrderByUpdatedDateDesc(Long id);

	public List<Article> findTop5ByCommunityIdOrderByUpdatedDateDesc(Long id);

	public List<Article> findByCommunityIdAndContributorIdOrderByCreatedDateDesc(Long communityId, Long userId);

	public Page<Article> findByCommunityIdAndContributorIdOrderByCreatedDateDesc(Long communityId, Long userId,Pageable pageable);

	@Query(value="select * from article join article_tags on article.id = article_tags.article_id where community_id = ?1 and ( tag like %?2% or article.title like %?3% )",nativeQuery=true)
	public List<Article> findByKeywordAndCommunityId(Long communityId, String keyword, String keyword2);

	@Query(value="select * from article left join article_tags on article.id = article_tags.article_id where community_id = ?1 and (title like %?2% or tag like %?3%) order by article.updated_date desc \n#pageable\n",nativeQuery=true)
	public Page<Article> findByCommunityIdAndTitleLikeOrTagsTagLikeOrderByUpdatedDateDesc(Long communityId, String tag, String title, Pageable pageable);

	@Query(value="select * from article join article_tags on article.id = article_tags.article_id where contributor_id = ?1 and tag like %?2% ",nativeQuery=true)
	public List<Article> findByKeywordAndUserId(Long userId, String keyword);

	public List<Article> findByCommunityIdAndAdminUser(Long communityId, User user);
}
