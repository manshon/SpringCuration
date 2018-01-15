package com.example.demo.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.model.User;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CommunityService;
import com.example.demo.service.UserService;

@Controller
public class Community {

	@Autowired
	UserRepository repository;

	@Autowired
	CommunityRepository communityRepository;

	@Autowired
	UserService userService;

	@Autowired
	CommunityService communityService;

	@GetMapping({ "/community", "/community/{communityId}" })
	public String home(@PathVariable(name = "communityId", required = false) Long communityId, HttpSession session,
			Model model) {
		User user = (User) session.getAttribute("user");

		List<com.example.demo.model.Community> adminCommunityList = null;
		List<com.example.demo.model.Community> followCommunityList = null;
		// Long communityId ;

		if (userService.isFollowAnyCommunity(user.getId())) {
			followCommunityList = new ArrayList<>(user.getFollowCommunities());
			Long firstCommunityId = followCommunityList.get(0).getId();
			communityId = firstCommunityId;
		} else if (communityRepository.existsByAdminId(user.getId())) {
			adminCommunityList = communityService.findAdminCommunity(user.getId());
			Long firstCommunityId = adminCommunityList.get(0).getId();
			communityId = firstCommunityId;
		} else {
			return "redirect:/searchCommunity";
		}

		adminCommunityList = communityService.findAdminCommunity(user.getId());
		followCommunityList = new ArrayList<>(user.getFollowCommunities());

		// sessionにcommunityIdがあるとき実行
		// Long communityId2 = (session.getAttribute("communityId") != null) ? (Long)
		// CurationHelper.cutSessionAttribute(session, "communityId") : 0;
		// if(communityId2 == 0) {
		// if(communityId != null && !communityId.equals("")) {
		// communityId2 = (long) Integer.parseInt(communityId);
		// }else {
		// communityId2 = firstCommunityId;
		// }
		// }

		model.addAttribute("selectedCommunityId", communityId);
		model.addAttribute("communityId", communityId);
		model.addAttribute("adminCommunityList", adminCommunityList);
		model.addAttribute("communityList", followCommunityList);
		model.addAttribute("user", user);
		return "community";

	}

}
