package com.ln.mial.ecommerce.infraestructure.controller;

import com.ln.mial.ecommerce.app.service.UsuariosService;
import com.ln.mial.ecommerce.infraestructure.entity.UsuariosEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/usuarios")
public class AdminUserController {
    
    private final UsuariosService usuariosService;

    public AdminUserController(UsuariosService usuariosService) {
        this.usuariosService = usuariosService;
    }

    @GetMapping
    public String showUsers(Model model) {
        Iterable<UsuariosEntity> users = usuariosService.getUsers();

        model.addAttribute("users", users);
        return "admin/usuarios";
    }
}
