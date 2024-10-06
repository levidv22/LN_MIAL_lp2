package com.ln.mial.ecommerce.infraestructure.controller;

import com.ln.mial.ecommerce.app.service.*;
import com.ln.mial.ecommerce.infraestructure.entity.*;
import java.time.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;

@Controller
@RequestMapping("/register")
public class RegisterController {
    
    private final UsuariosService userService;

    public RegisterController(UsuariosService userService) {
        this.userService = userService;
    }
    
    @GetMapping
    public String showRegister() {
        return "cuenta/register"; // Redirige a register.html
    }

    // Maneja el registro de usuario
    @PostMapping
    public ModelAndView registerUser(@RequestParam String username, @RequestParam String email, @RequestParam String cellphone, @RequestParam String password) {
        UsuariosEntity user = new UsuariosEntity();
        user.setUsername(username);
        user.setEmail(email);
        user.setCellphone(cellphone);
        user.setPassword(password);
        user.setDateCreated(LocalDateTime.now());
        user.setTypeUser(TypeUser.USER);  // Set default user type as USER
        userService.createUser(user);
        return new ModelAndView("redirect:/login");
    }
}
