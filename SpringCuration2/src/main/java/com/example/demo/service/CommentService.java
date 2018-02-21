package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Article;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	public void deleteComment(Long userId, Long commentId) {
		Comment comment = commentRepository.findOne(commentId);
		User user = comment.getContributor();
		Article article = comment.getArticle();
		user.getComment().remove(comment);
		article.getComment().remove(comment);
		commentRepository.delete(comment);

	}

}
