package com.example.demo.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.Article;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.CommentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ArticleService;

@Controller
public class DetailArticle {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private ArticleService articleService;

	@GetMapping("/detailArticle/{articleId}")
	public String detailArticle(@PathVariable("articleId") Long articleId,HttpSession session, Model model,
			User user) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		user = userRepository.findByName(principal.getUsername());


		 Article article = articleRepository.findOne(articleId);
		 boolean isLike = articleService.isLikeArticle(articleId, user.getId());
		 boolean createdDateIsNotUpdateDate = false;
		 if(article.getUpdatedDate() != null) {
			 createdDateIsNotUpdateDate = (article.getCreatedDate() == article.getUpdatedDate()) ? false : true;
		 }
		 List<Comment> commentList = commentRepository.findByArticleIdOrderByCreatedDateDesc(articleId);

		 model.addAttribute("createdDateIsNotUpdateDate", createdDateIsNotUpdateDate);
		 model.addAttribute("isLike", isLike);
		 model.addAttribute("user", user);
		 model.addAttribute("article", article);
		 model.addAttribute("commentList", commentList);
		 return "detailArticle";
	}
}
