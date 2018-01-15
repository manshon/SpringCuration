package com.example.demo.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.form.SignupForm;
import com.example.demo.model.User;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CommunityService;
import com.example.demo.service.UserService;

@Controller
public class Signin {

	@Autowired
	UserRepository repository;

	@Autowired
	CommunityRepository communityRepository;

	@Autowired
	UserService userService;

	@Autowired
	CommunityService communityService;

	@GetMapping("/")
	public String input(@ModelAttribute SignupForm signupForm, Model model) {
		return "signin";
	}

	// @PostMapping("/signin")
	// public String result(@RequestParam String name, @RequestParam String
	// password, Model model, HttpServletRequest request) {
	//
	// User user = repository.findByName(name);
	// HttpSession session = request.getSession();
	// session.setAttribute("user", user);
	// return "redirect:/community";
	// }

	@PostMapping("/")
	public String index(@RequestParam String name, @RequestParam String password, HttpSession session) {
		// Authentication authentication = (Authentication) principal;
		// User user = (User) authentication.getPrincipal();
		User user = repository.findByName(name);

		session.setAttribute("user", user);
		return "redirect:/community";
	}
}
