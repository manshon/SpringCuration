package com.example.demo.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Controller
public class FollowCommunity {

	@Autowired
	CommunityRepository communityRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@PostMapping("/followCommunity")
	public String followCommunity(@RequestParam("communityId") Long communityId, HttpSession session, Model model,
			User user) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		user = userRepository.findByName(principal.getUsername());

		com.example.demo.model.Community community = communityRepository.findById(communityId);

		if (userRepository.existsByIdAndFollowCommunities(user.getId(), community)) {
			userService.unFollowCommunity(user.getId(), communityId);
		} else {
			// followしていないときの処理
			userService.followCommunity(user.getId(), communityId);
			// 承認機能がないコミュニティはそのままフォロー
			// 承認機能のがあるコミュニティは承認待ち
			if(community.getConditions() == 0) {
				communityRepository.permitUser(communityId, user.getId());
			}
		}

		model.addAttribute("user", user);
		return "redirect:/detailCommunity/" + String.valueOf(communityId);
	}
}
