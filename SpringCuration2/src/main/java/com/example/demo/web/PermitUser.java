package com.example.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.Community;
import com.example.demo.model.User;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.repository.UserRepository;

@Controller
public class PermitUser {

	// status 0 : 承認待ち
	// status 1 : 承認
	// status 2 : 非承認

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommunityRepository communityRepository;

	@GetMapping("/permitUser/{communityId}")
	public String permitUser(@PathVariable(name="communityId", required=false) Long communityId, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		User user = userRepository.findByName(principal.getUsername());

		Community community = communityRepository.findOne(communityId);
		if(userRepository.countByCommunityId(communityId) == 0) {
			System.out.println(userRepository.countByCommunityId(communityId));
			model.addAttribute("msg", "承認待ちユーザーは存在しません");
			model.addAttribute("userList", null);
		}else {
			List<User> userList = userRepository.findUnpermitUser(communityId);
			model.addAttribute("userList", userList);
		}

		model.addAttribute("user", user);
		model.addAttribute("community", community);
		return "permitUser";
	}

	@PostMapping("permitUser/{communityId}/{userId}/{permitStatus}")
	public String postPermitUser(@PathVariable(name="communityId", required=true) Long communityId, @PathVariable(name="userId", required=true) Long userId,
			@PathVariable(name="permitStatus", required=true) Long permitStatus, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		User user = userRepository.findByName(principal.getUsername());

		if(permitStatus==1) {
			communityRepository.permitUser(communityId, userId);
		}
		if(permitStatus==2) {
			communityRepository.unpermitUser(communityId, userId);
		}

		return "redirect:/permitUser/" + String.valueOf(communityId);
	}
}
