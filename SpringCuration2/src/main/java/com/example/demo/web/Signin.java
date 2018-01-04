package com.example.demo.web;

import javax.servlet.http.HttpServletRequest;
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

import com.example.demo.form.SignupForm;
import com.example.demo.model.User;
import com.example.demo.repository.userRepository;


@Controller
public class Signin{


	@Autowired userRepository repository;

	@GetMapping("/signin")
	public String input(@ModelAttribute SignupForm signupForm, Model model) {
		return "signin";
	}

	@PostMapping("/signin")
	public String result(@RequestParam String name, @RequestParam String password, @Validated BindingResult result, Model model, HttpServletRequest request) {

		if(result.hasErrors()) {
			return "signin";
		}
		User user = repository.findByName(name);
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		return "redirect:/community";
	}
}
