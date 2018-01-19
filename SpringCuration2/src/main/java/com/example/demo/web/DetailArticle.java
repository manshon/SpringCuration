package com.example.demo.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.Article;
import com.example.demo.model.User;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.service.ArticleService;

@Controller
public class DetailArticle {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private ArticleService articleService;

	@GetMapping("/detailArticle/{articleId}")
	public String detailArticle(@PathVariable("articleId") Long articleId,HttpSession session, Model model,
			User user) {
		 user = (User) session.getAttribute("user");
		 Article article = articleRepository.findOne(articleId);
		 boolean isLike = articleService.isLikeArticle(articleId, user.getId());

		 model.addAttribute("isLike", isLike);
		 model.addAttribute("user", user);
		 model.addAttribute("article", article);
		 return "detailArticle";
	}
}
