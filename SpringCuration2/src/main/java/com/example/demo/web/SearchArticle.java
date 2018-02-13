package com.example.demo.web;

import java.util.ArrayList;
import java.util.List;

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

@Controller
public class SearchArticle {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@GetMapping("/searchArticle")
	public String searhcArticle(@RequestParam("communityId") Long communityId, @RequestParam("keywords") String keywords,
			HttpSession session, Model model, User user) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		user = userRepository.findByName(principal.getUsername());

		List<Article> articleList = new ArrayList<>();

		if(keywords != null && !keywords.equals("")) {
			String[] keywordArray = keywords.split("[,、/／\t\\s]+");
			for(String keyword : keywordArray) {
				List<Article> tempList = articleRepository.findByKeywordAndCommunityId(communityId, keyword);
				for(Article tempArticle : tempList) {
					articleList.add(tempArticle);
				}
			}
		}else {
			articleList = articleRepository.findByCommunityId(communityId);
		}

		session.setAttribute("articleList", articleList);
//		model.addAttribute("articleList", articleList);
		model.addAttribute("user", user);
		return "redirect:/community/" + String.valueOf(communityId);
	}
}




