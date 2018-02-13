package com.example.demo.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CommunityService;

@Controller
public class EditCommunity {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommunityRepository communityRepository;

	@Autowired
	private CommunityService communityService;

	@GetMapping("editCommunity/{communityId}")
	public String editCommunity(@PathVariable("communityId") Long communityId, HttpSession session, Model model, User user) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		user = userRepository.findByName(principal.getUsername());

		com.example.demo.model.Community community = communityRepository.findById(communityId);
		String stringTags = "";
		boolean first = true;
		for(CommunityTags tag : community.getTags()) {
			if(first) {
				stringTags += tag.getTag();
				first = false;
			}else {
				stringTags = String.join(",",stringTags, tag.getTag());
			}
		}
		model.addAttribute("user", user);
		model.addAttribute("community", community);
		model.addAttribute("stringTags", stringTags);
		return "editCommunity";
	}


	@PostMapping("/editCommunity/{communityId}")
	public String postEditCommunity(@PathVariable("communityId") Long communityId,@ModelAttribute CommunityForm form, HttpSession session, Model model, User user) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		user = userRepository.findByName(principal.getUsername());

//		com.example.demo.model.Community community = repository.findById(communityId);
		communityService.editCommunity(communityId, form.getContent(), form.getTags());

		model.addAttribute("user", user);
		return "redirect:/myCommunity";
	}
}
