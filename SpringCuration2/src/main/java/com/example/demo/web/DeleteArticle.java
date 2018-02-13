package com.example.demo.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.User;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ArticleService;

@Controller
public class DeleteArticle {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private ArticleService articleService;


	@GetMapping("/deleteArticle/{articleId}")
	public String deleteArticle(@PathVariable("articleId") Long articleId,HttpSession session,Model model, User user) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		user = userRepository.findByName(principal.getUsername());

		model.addAttribute("articleId", articleId);
		model.addAttribute("user", user);
		return "deleteArticle";
	}

	@PostMapping("/deleteArticle/{articleId}")
	public String postDeleteArticle(@PathVariable("articleId") Long articleId,HttpSession session,Model model, User user) {
		user = (User) session.getAttribute("user");
		articleService.deleteArticle(articleId);

		model.addAttribute("user", user);
		return "redirect:/myArticle";
	}
}
