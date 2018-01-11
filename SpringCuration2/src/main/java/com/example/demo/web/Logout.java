package com.example.demo.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.User;

@Controller
//@SessionAttributes("user")
public class Logout {

//	@Autowired
//	HttpSession session;

	@GetMapping("/logout")
	public String logout(HttpSession session, User user) {
		user = (User) session.getAttribute("user");
		System.out.println(user.getName());
		session.invalidate();
		System.out.println("logout");
		return "redirect:/";
	}
}
