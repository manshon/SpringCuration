package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.CommunityForm;
import com.example.demo.model.User;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CommunityService;

@Controller
public class CreateCommunity {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommunityRepository communityRepository;

	@Autowired
	private CommunityService communityService;

	@GetMapping("/createCommunity")
	public String createCommunity(@ModelAttribute CommunityForm form, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		User user = userRepository.findByName(principal.getUsername());

		model.addAttribute("user", user);
		return "createCommunity";
	}

	@PostMapping("/createCommunity")
	public String postCreateCommunity(@ModelAttribute CommunityForm form,BindingResult result, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		User user = userRepository.findByName(principal.getUsername());

		if(result.hasErrors()) {
			model.addAttribute("msg", "記入が不十分です");
			return createCommunity(form, model);
		}

		if(communityRepository.findByName(form.getName()) != null) {
			model.addAttribute("msg", "このコミュニティ名は使用されています");
			return createCommunity(form, model);
		}

		communityService.registerCommunity(form.getName(), user.getId(), form.getContent(), form.getTags());
		model.addAttribute("user", user);
		return "redirect:/myCommunity";
	}
}
