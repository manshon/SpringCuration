package com.example.demo.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.UserForm;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Controller
public class EditUser {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@GetMapping("/editUser")
	public String editUser(HttpSession session, Model model,User user, UserForm userForm) {
		user = (User) session.getAttribute("user");
		model.addAttribute("userForm", userForm);
		model.addAttribute("user", user);
		return "editUser";
	}

	@PostMapping("/editUser")
	public String postEditUser(HttpSession session, Model model,User user, UserForm userForm) {
		user = (User) session.getAttribute("user");
		userService.updateUser(userForm.getPassword(),user.getId());
		model.addAttribute("user", user);
		return "redirect:/user";
	}
}
