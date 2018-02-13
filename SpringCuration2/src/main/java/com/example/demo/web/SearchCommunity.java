package com.example.demo.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Community;
import com.example.demo.model.User;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CommunityService;

@Controller
public class SearchCommunity {

	@Autowired
	private CommunityService communityService;

	@Autowired
	private CommunityRepository communityRepository;

	@Autowired
	private UserRepository userRepository;


	@GetMapping("/searchCommunity")
	public String searchCommnity(HttpSession session, Model model, User user) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		user =  userRepository.findByName(principal.getUsername());

		List<Community> communityList =communityRepository.find20RandomCommunity();
		model.addAttribute("communityList", communityList);
		model.addAttribute("user", user);
		return "searchCommunity";
	}

	@PostMapping("/searchCommunity")
	public String postSearchCommunity(@RequestParam String search,HttpSession session, Model model, User user) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		user =  userRepository.findByName(principal.getUsername());

		List<Community> communityList = communityService.searchCommunity(search);
		model.addAttribute("search", search);
		model.addAttribute("user", user);
		model.addAttribute("communityList", communityList);
		return "searchCommunity";
	}
}
