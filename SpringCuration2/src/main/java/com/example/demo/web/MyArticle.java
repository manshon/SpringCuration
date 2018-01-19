package com.example.demo.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.Article;
import com.example.demo.model.User;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.service.ArticleService;

@Controller
public class MyArticle {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private ArticleService articleService;

	@GetMapping("/myArticle")
	public String myArticle(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		List<Article> articleList = articleRepository.findByContributorId(user.getId());

		model.addAttribute("articleList", articleList);
		model.addAttribute("user", user);
		return "myArticle";
	}
}
