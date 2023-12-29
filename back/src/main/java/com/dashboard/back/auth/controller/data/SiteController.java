package com.dashboard.back.auth.controller.data;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/site")
public class SiteController {
    @GetMapping("/{menuid}/{menudept}")
    public String site(Model menuModel, 
        Model model, 
        @PathVariable(value = "menuid") String menuid, 
        @PathVariable(value = "menudept") String menudept) {
            return "site/site";
    }
}
