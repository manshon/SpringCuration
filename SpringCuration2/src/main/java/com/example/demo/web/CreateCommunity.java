package com.example.demo.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.User;
import com.example.demo.service.CommunityService;

@Controller
public class CreateCommunity {

	@Autowired
	CommunityService communityService;

	@GetMapping("/createCommunity")
	public String createCommunity(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		return "createCommunity";
	}

	@PostMapping("/createCommunity")
	public String postCreateCommunity(@RequestParam String name, @RequestParam String tags,
			@RequestParam String content, HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		communityService.registerCommunity(name, user.getId(), content, tags);
		model.addAttribute("user", user);
		return "redirect:/myCommunity";
	}
}
