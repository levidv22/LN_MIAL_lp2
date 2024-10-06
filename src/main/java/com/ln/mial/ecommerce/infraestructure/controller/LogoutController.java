package com.ln.mial.ecommerce.infraestructure.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
public class LogoutController {
    
    @GetMapping
    public String logout(HttpSession session) {
        session.invalidate(); // Invalida la sesión actual
        return "redirect:/index/products";  // Redirige a la página de inicio
    }
}
