package com.adminpanel.controller;

import com.adminpanel.model.User;
import com.adminpanel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/")
	public String home(Authentication authentication) {
		String username = authentication.getName();
		User user = userService.findByUsername(username);

		if (user.isBlocked()) {
			return "blocked";
		}

		return "index";
	}

	@GetMapping("/access-denied")
	public String accessDenied(){
		return "access-denied";
	}
}
