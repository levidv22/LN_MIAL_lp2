package com.ln.mial.ecommerce.infraestructure.controller;

import com.ln.mial.ecommerce.app.service.LogoutService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/close")
public class LogoutController {
    
    private final LogoutService logoutService;
    
    public LogoutController(LogoutService logoutService) {
        this.logoutService = logoutService;
    }
    
     @GetMapping
    public String logout(HttpSession httpSession){
        logoutService.logout(httpSession);
        return "redirect:/home";
    }
}
