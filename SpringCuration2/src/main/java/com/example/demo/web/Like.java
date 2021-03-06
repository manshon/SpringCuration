package com.example.demo.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Article;
import com.example.demo.model.User;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ArticleService;

@Controller
public class Like {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private ArticleService articleService;

	@GetMapping("/like")
	public String likeArticle(@RequestParam("articleId") Long articleId,@RequestParam("path") String path,@RequestParam(value="communityId",required=false) Long communityId
			,HttpSession session, Model model, User user) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		user = userRepository.findByName(principal.getUsername());

		Article article = articleRepository.findOne(articleId);
		String returnPath = "";

		articleService.likeAndUnlikeArticle(articleId, user.getId());

		// path == /detailCommunity
		if(path.equals("detailArticle")) {
			returnPath =  "redirect:/detailArticle/" + String.valueOf(articleId);
		}
		// path == /community
		if(path.equals("community")) {
			returnPath =  "redirect:/community/" + String.valueOf(communityId) ;
		}

		return returnPath;
	}
}
