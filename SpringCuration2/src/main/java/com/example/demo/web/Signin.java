package com.example.demo.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.form.UserForm;
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

	// @ModelAttribute
	// public UserForm form() {
	// UserForm form = new UserForm();
	// return form;
	// }

	@GetMapping("/")
	public String input(HttpSession session, UserForm userForm, Model model) {
		if (session.getAttribute("user") != null) {
			return "redirect:/community";
		}
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
	public String index(@RequestParam String name, @RequestParam String password,
			@Validated @ModelAttribute UserForm userForm, BindingResult result, HttpSession session, Model model) {
		User user = new User();

		if (result.hasErrors()) {
			model.addAttribute("validationError", "不正な値が入力されました。");
			return input(session, userForm, model);
		}

		if(repository.findByName(name) != null) {
			user = repository.findByName(name);
		}else {
			model.addAttribute("errorMsg", "ユーザー名が間違っています");
			return input(session, userForm, model);
		}

		// password check
		if (!userForm.getPassword().equals(user.getPassword())) {
			model.addAttribute("errorMsg", "パスワードが異なります");
			return input(session, userForm, model);
		}

		session.setAttribute("user", user);
		return "redirect:/community";
	}
}
