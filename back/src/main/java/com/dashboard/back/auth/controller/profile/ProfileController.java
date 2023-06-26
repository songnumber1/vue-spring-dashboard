package com.dashboard.back.auth.controller.profile;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @GetMapping("/{menuid}/{menudept}")
    public String profile(Model menuModel, 
        Model model, 
        @PathVariable(value = "menuid") String menuid, 
        @PathVariable(value = "menudept") String menudept) {
            return "profile/profile";
    }
}
