package com.example.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CommentService;

@Controller
public class DeleteComment {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CommentService commentService;



	@PostMapping("/deleteComment/{commentId}/{articleId}")
	public String deleteComment(@PathVariable Long commentId, @PathVariable Long articleId, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails principal = (UserDetails) auth.getPrincipal();
		User user = userRepository.findByName(principal.getUsername());

		commentService.deleteComment(user.getId(), commentId);

		model.addAttribute("user", user);
		return "redirect:/detailArticle/" + String.valueOf(articleId);
	}
}
