package com.ln.mial.ecommerce.infraestructure.controller;

import com.ln.mial.ecommerce.app.service.LoginService;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {
    
//    private final UsuariosService userService;
//    private final PedidosService pedidosService;
//
//    public LoginController(UsuariosService userService, PedidosService pedidosService) {
//        this.userService = userService;
//        this.pedidosService = pedidosService;
//    }
//
//    
//    @GetMapping
//    public String showLogin() {
//        return "cuenta/login"; // Redirige a login.html
//    }
//
//    @PostMapping
//    public ModelAndView loginUser(@RequestParam String email, @RequestParam String password, HttpSession session) {
//        UsuariosEntity user = userService.findByEmail(email);
//
//        if (user != null) { // Verificar si el usuario fue encontrado
//            if (user.getPassword().equals(password)) {
//                session.setAttribute("username", user.getUsername());
//                session.setAttribute("email", user.getEmail());
//                session.setAttribute("iduser", user.getId());  // Guardar el `iduser`
//                session.setAttribute("user", user); // Guardar objeto usuario en la sesión
//
//                // Recuperar el pedido con estado EN_PROCESO para este usuario
//                List<PedidosEntity> ordersInProcess = pedidosService.getOrdersByUserAndStatus(user, StatusPedido.EN_PROCESO);
//                if (!ordersInProcess.isEmpty()) {
//                    // Si hay un pedido en proceso, lo colocamos en la sesión
//                    session.setAttribute("currentOrder", ordersInProcess.get(0));
//                }
//
//                if (user.getTypeUser() == TypeUser.USER) {
//                    return new ModelAndView("redirect:/index/products");
//                } else {
//                    return new ModelAndView("redirect:/admin/create");
//                }
//            } else {
//                return new ModelAndView("redirect:/login?error=invalid_password"); // Error de contraseña
//            }
//        } else {
//            return new ModelAndView("redirect:/login?error=user_not_found"); // Error de usuario no encontrado
//        }
//    }
    
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping
    public String showLogin() {
        return "cuenta/login"; // Redirige a login.html
    }
}
