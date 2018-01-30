package com.example.demo.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.Article;
import com.example.demo.model.User;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ArticleService;
import com.example.demo.service.CommunityService;
import com.example.demo.service.UserService;

@Controller
public class Community {

	@Autowired
	UserRepository repository;

	@Autowired
	CommunityRepository communityRepository;

	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	UserService userService;

	@Autowired
	CommunityService communityService;

	@Autowired
	ArticleService articleService;

	@GetMapping({ "/community", "/community/{communityId}" })
	public String home(@PathVariable(name = "communityId", required = false) Long communityId, HttpSession session,
			Model model) {
		User user = (User) session.getAttribute("user");

		List<com.example.demo.model.Community> adminCommunityList = null;
		List<com.example.demo.model.Community> followCommunityList = null;
		List<Article> articleList = new ArrayList<Article>();
		List<Long> likeArticleIdList = new ArrayList<Long>();
		// Long communityId ;

		if(communityId == null ) {
			if (userService.isFollowAnyCommunity(user.getId())) {
				followCommunityList = new ArrayList<>(repository.findById(user.getId()).getFollowCommunities());
				Long firstCommunityId = followCommunityList.get(0).getId();
				return "redirect:/community/" + String.valueOf(firstCommunityId);
	//			communityId = firstCommunityId;
			} else if (communityRepository.existsByAdminId(user.getId())) {
				adminCommunityList = communityService.findAdminCommunity(user.getId());
				Long firstCommunityId = adminCommunityList.get(0).getId();
				return "redirect:/community/" + String.valueOf(firstCommunityId);
	//			communityId = firstCommunityId;
			} else {
				return "redirect:/searchCommunity";
			}
		}

		// searchArticleからredirectされたときはsessionに保存されているarticleListを取ってくる
		if(session.getAttribute("articleList") != null) {
			articleList = (List<Article>) CurationHelper.cutSessionAttribute(session, "articleList");
		}else {
			articleList = articleRepository.findByCommunityId(communityId);
		}

		for(Article tempArticle : repository.findOne(user.getId()).getLikeArticles()) {
			likeArticleIdList.add(tempArticle.getId());
		}

		adminCommunityList = communityService.findAdminCommunity(user.getId());
		followCommunityList = new ArrayList<>(user.getFollowCommunities());

		model.addAttribute("likeArticleIdList", likeArticleIdList);
		model.addAttribute("articleList", articleList);
		model.addAttribute("selectedCommunityId", communityId);
		model.addAttribute("communityId", communityId);
		model.addAttribute("adminCommunityList", adminCommunityList);
		model.addAttribute("communityList", followCommunityList);
		model.addAttribute("user", user);
		return "community";

	}

}
