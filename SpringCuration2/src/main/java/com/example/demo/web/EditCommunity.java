package com.example.demo.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.CommunityForm;
import com.example.demo.model.CommunityTags;
import com.example.demo.model.User;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.service.CommunityService;

@Controller
public class EditCommunity {

	@Autowired CommunityRepository repository;

	@Autowired CommunityService service;

	@GetMapping("editCommunity/{communityId}")
	public String editCommunity(@PathVariable("communityId") Long communityId, HttpSession session, Model model, User user) {
		user = (User) session.getAttribute("user");
		com.example.demo.model.Community community = repository.findById(communityId);
		String stringTags = "";
		for(CommunityTags tag : community.getTags()) {
			stringTags += tag.getTag();
			System.out.println(tag);
		}
		model.addAttribute("user", user);
		model.addAttribute("community", community);
		model.addAttribute("stringTags", stringTags);
		return "editCommunity";
	}


	@PostMapping("/editCommunity/{communityId}")
	public String postEditCommunity(@PathVariable("communityId") Long communityId,@ModelAttribute CommunityForm form, HttpSession session, Model model, User user) {
		user = (User) session.getAttribute("user");
//		com.example.demo.model.Community community = repository.findById(communityId);
		service.editCommunity(communityId, form.getContent(), form.getTags());

		model.addAttribute("user", user);
		return "myCommunity";
	}
}
