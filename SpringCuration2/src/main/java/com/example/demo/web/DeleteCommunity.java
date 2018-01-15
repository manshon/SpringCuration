package com.example.demo.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.User;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.service.CommunityService;

@Controller
public class DeleteCommunity {

	@Autowired
	CommunityRepository repository;

	@Autowired
	CommunityService service;

	@GetMapping("/deleteCommunity/{communityId}")
	public String deleteCommunity(@PathVariable("communityId") String communityId,HttpSession session, Model model, User user) {
		user = (User) session.getAttribute("user");

		model.addAttribute("user", user);
		model.addAttribute("communityId", Long.parseLong(communityId));
		return "deleteCommunity";
	}

	@PostMapping("/deleteCommunity/{communityId}")
	public String postDeleteCommunity(@PathVariable("communityId") Long communityId,HttpSession session, Model model, User user) {
		user = (User) session.getAttribute("user");
		System.out.println(communityId);
//		com.example.demo.model.Community com = repository.findById(Long.parseLong(communityId));
//		service.deleteCommunity(Long.parseLong(communityId));
		repository.delete(communityId);

		model.addAttribute("user", user);
		return "redirect:/myCommunity";
	}
}
