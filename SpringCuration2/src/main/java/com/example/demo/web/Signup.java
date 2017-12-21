package com.example.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.SignupForm;


@Controller
public class Signup{

	@GetMapping("/signup")
	public String input(Model model) {
		return "signup";
	}

	@PostMapping("/signup")
	public String result(@ModelAttribute SignupForm form, Model model) {
		return "redirect:/signin";
	}
}
