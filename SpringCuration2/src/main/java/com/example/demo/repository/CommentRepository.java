package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
	public List<Comment> findById(Long id);

//	public void delete(Long id);
	public List<Comment> findByArticleIdOrderByCreatedDateDesc(Long articleId);
}
