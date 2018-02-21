package com.example.demo.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.ArticleForm;
import com.example.demo.model.Article;
import com.example.demo.model.ArticleTags;
import com.example.demo.model.QuoteUrl;
import com.example.demo.model.User;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ArticleService;

@Controller
public class EditArticle {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private ArticleService articleService;

	@GetMapping("/editArticle/{articleId}")
	public String editArticle(@PathVariable("articleId") Long articleId,@ModelAttribute ArticleForm form,
			HttpSession session,Model model, User user) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		user = userRepository.findByName(principal.getUsername());

		Article article = articleRepository.findOne(articleId);
		String articleTags = "";
		String articleUrls = "";
		for(ArticleTags tag : article.getTags()) {
			articleTags += tag.getTag();
		}
		for(QuoteUrl url : article.getQuoteUrl()) {
			articleUrls += url.getUrl() + "\n";
		}

		model.addAttribute("articleTags", articleTags);
		model.addAttribute("articleUrls", articleUrls);
		model.addAttribute("user", user);
		model.addAttribute("article", article);
		return "editArticle";
	}

	@PostMapping("/editArticle/{articleId}")
	public String postEditArticle(@PathVariable("articleId") Long articleId,@ModelAttribute ArticleForm form,
			HttpSession session,Model model, User user) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		user = userRepository.findByName(principal.getUsername());

		// update article
		articleService.updateArticle(articleId, form.getTitle(), form.getTags(), form.getQuoteUrls(), form.getConditions(), form.getContent());

		model.addAttribute("user", user);
		return "redirect:/detailArticle/" + String.valueOf(articleId);
	}







}
