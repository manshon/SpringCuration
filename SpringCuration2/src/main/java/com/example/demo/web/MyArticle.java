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

import com.example.demo.model.Article;
import com.example.demo.model.User;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ArticleService;

@Controller
public class MyArticle {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private ArticleService articleService;

	@GetMapping("/myArticle")
	public String myArticle(HttpSession session, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		User user = userRepository.findByName(principal.getUsername());

		List<Article> articleList = articleRepository.findByContributorIdOrderByUpdatedDateDesc(user.getId());

		model.addAttribute("articleList", articleList);
		model.addAttribute("user", user);
		return "myArticle";
	}
}
