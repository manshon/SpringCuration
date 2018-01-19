package com.example.demo.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
		user = (User) session.getAttribute("user");
		com.example.demo.model.Community community = communityRepository.findById(communityId);

		if (userRepository.existsByIdAndFollowCommunities(user.getId(), community)) {
			userService.unFollowCommunity(user.getId(), communityId);
		} else {
			userService.followCommunity(user.getId(), communityId);
		}

		model.addAttribute("user", user);
		return "redirect:/detailCommunity/" + String.valueOf(communityId);
	}
}
