package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Article;
import com.example.demo.model.Community;
import com.example.demo.model.User;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long>, JpaSpecificationExecutor<Community> {
	public List<Community> findByAdminId(Long id);

	public Community findByName(String communityName);

	@Query(value = "SELECT * FROM community ORDER BY RAND() LIMIT 20", nativeQuery = true)
	public List<Community> find20RandomCommunity();

	@Query(value = "SELECT * FROM community c " + "left join community_tags t ON c.id = t.community_id "
			+ "where c.name like %?1% OR t.tag like %?1% ORDER BY c.id", nativeQuery = true)
	public List<Community> findByKeyword(String keyword);

	public boolean existsByAdminId(Long adminId);

//	public boolean existsByUserId(Long userId);

//	public List<Community> findByUserId(Long userId);

	public Community findById(Long id);

	public Community findByArticles(Article article);

	public void deleteById(Long id);

	public List<Community> findByFollowUsersOrderByIdAsc(User user);


}
