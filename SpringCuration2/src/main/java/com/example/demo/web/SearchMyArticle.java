package com.example.demo.web;

import java.util.ArrayList;
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
public class SearchMyArticle {

	@Autowired
	private ArticleRepository articleRepository;

	@GetMapping("/searchMyArticle")
	public String searchMyArticle(@RequestParam("keywords") String keywords, HttpSession session, Model model, User user) {
		user = (User) session.getAttribute("user");
		List<Article> articleList = new ArrayList<>();

		String[] keywordArray = keywords.split("[,、/／\t\\s]+");
		for(String keyword : keywordArray) {
			List<Article> tempList = articleRepository.findByKeywordAndUserId(user.getId(), keyword);
			for(Article tempArticle : tempList) {
				articleList.add(tempArticle);
			}
		}
		model.addAttribute("articleList", articleList);
		model.addAttribute("user", user);
		return "myArticle";
	}
}
