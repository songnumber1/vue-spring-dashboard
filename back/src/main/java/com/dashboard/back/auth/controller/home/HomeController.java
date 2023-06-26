package com.dashboard.back.auth.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping({ "", "/", "home", "index", "main" })
	public String home(Model menuModel) {
		return "index";
	}
}
