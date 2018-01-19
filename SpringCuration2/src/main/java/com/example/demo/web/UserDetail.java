package com.example.demo.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Controller
public class UserDetail {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/user")
	public String userDetail(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		int numOfPosts = userRepository.findOne(user.getId()).getAdminArticles().size();

		model.addAttribute("numOfPosts", numOfPosts);
		model.addAttribute("user", user);
		return "user";
	}
}
