package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

//	@GetMapping("/")
//	public String input(HttpSession session, UserForm userForm, Model model) {
//		if (session.getAttribute("user") != null) {
//			return "redirect:/community";
//		}
//		return "signin";
//	}

	@GetMapping("/")
	public String signin() {
		return "signin";
	}


//	@PostMapping("/")
//	public String index(@Validated @ModelAttribute UserForm userForm, BindingResult result, HttpSession session, Model model) {
//		User user = new User();
//
//		if (result.hasErrors()) {
//			model.addAttribute("validationError", "不正な値が入力されました。");
//			return input(session, userForm, model);
//		}
//
//		if(repository.findByName(userForm.getName()) != null) {
//			user = repository.findByName(userForm.getName());
//		}else {
//			model.addAttribute("errorMsg", "ユーザー名が間違っています");
//			return input(session, userForm, model);
//		}
//
//		String password = CurationHelper.encryption(userForm.getPassword());
//		// password check
//		if (!password.equals(user.getPassword())) {
//			model.addAttribute("errorMsg", "パスワードが異なります");
//			return input(session, userForm, model);
//		}
//
//		session.setAttribute("user", user);
//		return "redirect:/community";
//	}
}
