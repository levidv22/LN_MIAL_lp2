package com.ln.mial.ecommerce.app.service;

import com.ln.mial.ecommerce.infraestructure.entity.UsuariosEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RegisterService {

    private final UsuariosService usuariosService;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(UsuariosService usuariosService, PasswordEncoder passwordEncoder) {
        this.usuariosService = usuariosService;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(UsuariosEntity user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usuariosService.createUser(user);
    }

}
