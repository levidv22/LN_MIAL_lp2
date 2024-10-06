package com.ln.mial.ecommerce.infraestructure.controller;

import com.ln.mial.ecommerce.app.service.UsuariosService;
import com.ln.mial.ecommerce.infraestructure.entity.UsuariosEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/password-reset")
public class CambiarContraController {

    private final UsuariosService userService;

    public CambiarContraController(UsuariosService userService) {
        this.userService = userService;
    }

    // Mostrar formulario para ingresar el correo electrónico
    @GetMapping
    public String showResetForm() {
        return "cuenta/password-reset"; // Vista para verificar el correo electrónico
    }

    // Verificar correo electrónico
    @PostMapping("/verify-email")
    public String verifyEmail(@RequestParam String email, Model model) {
        UsuariosEntity user = userService.findByEmail(email);
        if (user != null) {
            model.addAttribute("userId", user.getId());
            model.addAttribute("email", email);
            return "cuenta/password-change"; // Vista para cambiar la contraseña
        } else {
            model.addAttribute("error", "El correo electrónico no se encuentra registrado.");
            return "cuenta/password-reset"; // Volver a la vista de verificación de correo
        }
    }

    // Cambiar la contraseña
    @PostMapping("/change-password")
    public ModelAndView changePassword(@RequestParam Integer userId, @RequestParam String newPassword) {
        UsuariosEntity user = userService.findById(userId);
        if (user != null) {
            user.setPassword(newPassword); // Actualizar la contraseña
            userService.createUser(user); // Guardar el usuario actualizado en la base de datos
            return new ModelAndView("redirect:/login?success=password_reset");
        } else {
            return new ModelAndView("redirect:/password-reset?error=user_not_found");
        }
    }
}
