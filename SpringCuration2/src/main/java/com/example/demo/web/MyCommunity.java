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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.model.Community;
import com.example.demo.model.User;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CommunityService;

@SessionAttributes("user")
@Controller
public class MyCommunity {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommunityService communityService;

	@Autowired
	private CommunityRepository communityRepository;

	@GetMapping("/myCommunity")
	public String showMyCommunity(HttpSession session, Model model,User user) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		user = userRepository.findByName(principal.getUsername());

		List<Community> communityList = communityRepository.findByAdminId(user.getId());
		model.addAttribute("communityList", communityList);
		model.addAttribute("user", user);
		return "myCommunity";
	}
}
