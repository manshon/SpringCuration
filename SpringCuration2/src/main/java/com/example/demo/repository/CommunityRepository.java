package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
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

	@Query(value="select * from community c join user_community_follow f on f.community_id = c.id where f.user_id = ?1 and f.conditions = ?2",nativeQuery=true)
	public List<Community> findByFollowUsersAndConditionsOrderByIdAsc(User user,int followStatus);

	@Modifying
	@Transactional
	@Query(value="update user_community_follow f join users u on f.user_id = u.id join community c on c.id = f.community_id set f.conditions = 1 where c.id = ?1 and u.id = ?2 ",nativeQuery=true)
	public void permitUser(Long communityId, Long userId);

	@Modifying
	@Transactional
	@Query(value="update user_community_follow f join users u on f.user_id = u.id join community c on c.id = f.community_id set f.conditions = 2 where community_id = ?1 and user_id = ?2 ",nativeQuery=true)
	public void unpermitUser(Long communityId, Long userId);

}
