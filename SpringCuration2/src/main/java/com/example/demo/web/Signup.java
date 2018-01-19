package com.example.demo.web;

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
import com.example.demo.repository.UserRepository;

@Controller
public class Signup {

	@Autowired
	private UserRepository repository;

	@GetMapping("/signup")
	public String signup(@ModelAttribute UserForm userForm, Model model) {
		model.addAttribute("UserForm", userForm);
		return "signup";
	}

	@PostMapping("/signup")
	public String postSignup(@Validated @ModelAttribute UserForm userForm,BindingResult result,@RequestParam String confirmPassword,Model model) {
		if(result.hasErrors()) {
			return signup(userForm,model);
		}
		String password = userForm.getPassword();
		if(!password.equals(confirmPassword)) {
			model.addAttribute("errorMsg", "パスワードが異なります");
			return signup(userForm,model);
		}

		User user = new User();
		user.setName(userForm.getName());
		user.setPassword(password);
		repository.save(user);
		return "signin";
	}
}
