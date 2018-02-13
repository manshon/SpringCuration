package com.example.demo.web;

import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.Article;
import com.example.demo.model.Community;
import com.example.demo.model.User;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CommunityService;
import com.example.demo.service.UserService;

@Controller
public class DetailCommunity {

	@Autowired
	CommunityRepository repository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CommunityService service;

	@Autowired
	UserService userService;


	@GetMapping("/detailCommunity/{communityId}")
	public String detailCommunity(@PathVariable("communityId") Long communityId, HttpSession session, Model model, User user) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		user = userRepository.findByName(principal.getUsername());
//		if(session.getAttribute("communityId") != null) {
//			sessionCommunityId = CurationHelper.cutSessionAttribute(session, communityId);
//			Community community = repository.findById(communityId);
//		}
		Community community = repository.findById(communityId);
		Set<Article> articles = community.getArticles();
		boolean isFollow = userService.isFollowAnyCommunity(user.getId());

		model.addAttribute("community", community);
		model.addAttribute("isFollow", isFollow);
		model.addAttribute("articles", articles);
		model.addAttribute("user", user);
		return "detailCommunity";
	}
}
