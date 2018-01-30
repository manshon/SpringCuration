package com.example.demo.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Article;
import com.example.demo.model.User;
import com.example.demo.repository.ArticleRepository;

@Controller
public class SearchMyArticleRelatedCommunity {

	@Autowired
	private ArticleRepository articleRepository;

	@GetMapping("/searchMyArticleRelatedCommunity")
	public String searchMyArticleRelatedCommunity(@RequestParam("communityId") Long communityId,HttpSession session, Model model, User user) {
		user = (User) session.getAttribute("user");
		List<Article> articleList = articleRepository.findByCommunityIdAndAdminUser(communityId, user);

		session.setAttribute("articleList", articleList);
		model.addAttribute("user", user);
		return "redirect:/community/" + String.valueOf(communityId);
	}
}