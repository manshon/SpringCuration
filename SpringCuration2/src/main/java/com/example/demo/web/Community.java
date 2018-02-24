package com.example.demo.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
			Model model, Pageable pageable) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		User user = repository.findByName(principal.getUsername());

		List<com.example.demo.model.Community> adminCommunityList = null;
		List<com.example.demo.model.Community> followCommunityList = null;
		List<Article> articleList = new ArrayList<Article>();
		List<Long> likeArticleIdList = new ArrayList<Long>();
		// Long communityId ;

		if(communityId == null ) {
			if (userService.isFollowAnyCommunity(user.getId())) {
				followCommunityList = new ArrayList<>(repository.findById(user.getId()).getFollowCommunities());
				Long firstCommunityId = 0L;
				// followStatusのチェック
				for(com.example.demo.model.Community tempCommunity: followCommunityList) {
					if(tempCommunity.getConditions() == 1) {
						firstCommunityId = tempCommunity.getId();
						break;
					}
				}
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

		Page<Article> articlePage = null;
		// searchArticleからredirectされたときはsessionに保存されているarticleListを取ってくる
		if(session.getAttribute("articlePage") != null) {
//			articleList = (List<Article>) CurationHelper.cutSessionAttribute(session, "articleList");
			articlePage = (Page<Article>) CurationHelper.cutSessionAttribute(session, "articlePage");
		}else {
			articleList = articleRepository.findByCommunityIdAndFollowConditionsOrderByUpdatedDateDesc(communityId, 1);
			articlePage = articleRepository.findByCommunityIdOrderByUpdatedDateDesc(communityId, user.getId(), 1, pageable);
		}

		for(Article tempArticle : repository.findOne(user.getId()).getLikeArticles()) {
			likeArticleIdList.add(tempArticle.getId());
		}

		adminCommunityList = communityService.findAdminCommunity(user.getId());

//		followCommunityList = new ArrayList<>(user.getFollowCommunities());
		followCommunityList = communityRepository.findByFollowUsersAndConditionsOrderByIdAsc(user, 1);

		model.addAttribute("likeArticleIdList", likeArticleIdList);
//		model.addAttribute("articleList", articleList);
		model.addAttribute("selectedCommunityId", communityId);
		model.addAttribute("communityId", communityId);
		model.addAttribute("adminCommunityList", adminCommunityList);
		model.addAttribute("followCommunityList", followCommunityList);
		model.addAttribute("user", user);
		model.addAttribute("page", articlePage);
		model.addAttribute("article", articlePage.getContent());
		model.addAttribute("url", "/community/" + String.valueOf(communityId));

		return "community";

	}

}
