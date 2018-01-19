package com.example.demo.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.ArticleForm;
import com.example.demo.model.User;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.service.ArticleService;

@Controller
public class Summary {

	@Autowired
	CommunityRepository communityRepository;

	@Autowired
	ArticleService articleService;

	@GetMapping("/summary/{communityId}")
	public String summaryArticle(@PathVariable("communityId") Long communityId, @ModelAttribute ArticleForm form,
			HttpSession session, Model model, User user) {
		user = (User) session.getAttribute("user");
		com.example.demo.model.Community community = communityRepository.findById(communityId);

		model.addAttribute("community", community);
		model.addAttribute("user", user);
		return "summary";
	}

	@PostMapping("/summary/{communityId}")
	public String postSummaryArticle(@PathVariable("communityId") Long communityId,
			@Validated @ModelAttribute ArticleForm form, HttpSession session, BindingResult result, Model model,
			User user) {
		user = (User) session.getAttribute("user");
		if (result.hasErrors()) {
			model.addAttribute("ValidationError", "入力が正しくありません");
			return summaryArticle(communityId, form, session, model, user);
		}
		com.example.demo.model.Community community = communityRepository.findById(communityId);

		articleService.registerArticle(form.getTitle(), form.getTags(), form.getQuoteUrls(), form.getConditions(), form.getContent(), user.getId(), communityId);

		model.addAttribute("community", community);
		model.addAttribute("user", user);
		return "redirect:/myArticle";
	}
}
