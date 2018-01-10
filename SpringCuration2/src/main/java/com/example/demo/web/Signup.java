package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.form.SignupForm;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;


@Controller
public class Signup{


    @Autowired
    private UserRepository repository;

	@GetMapping("/signup")
	public String input(@ModelAttribute SignupForm signupForm, Model model) {
		return "signup";
	}

	@PostMapping("/signup")
		public String addNewUser (
				@RequestParam String name,
				@RequestParam String password) {
			// @ResponseBody means the returned String is the response, not a view name
			// @RequestParam means it is a parameter from the GET or POST request

			User user = new User();
			user.setName(name);
			user.setPassword(password);
			repository.save(user);
			return "signin";
	}
}
