package com.example.demo.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Community;
import com.example.demo.model.User;

@Transactional
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	// @Autowired
	// private SessionFactory sf;

	@Modifying
	@Query("update User u set" + " u.password = :uPassword" + " where u.id = :ID")
	public int updateUser(@Param("uPassword") String password, @Param("ID") Long ID);

	public User findById(Long id);

	public User findByName(String name);

	public boolean existsByIdAndFollowCommunities(Long userId, Community followCommunities);

//	@Query(value = "select count(*)>0 from user_community_follow where user_id = ?1 and community_id = ?2", nativeQuery = true)
//	public boolean isFollowCommunity(Long userId, Long communityId);

//	public boolean existsByIdAndCommunityId(Long userId, Long communityId);
}
