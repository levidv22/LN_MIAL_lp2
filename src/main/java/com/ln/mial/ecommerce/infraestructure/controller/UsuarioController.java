package com.ln.mial.ecommerce.infraestructure.controller;

import com.ln.mial.ecommerce.app.service.*;
import com.ln.mial.ecommerce.infraestructure.entity.*;
import java.security.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/index/products")
public class UsuarioController {

    private final ProductosService productService;
    private final CategoriasService categoriasService;

    public UsuarioController(ProductosService productService, CategoriasService categoriasService) {
        this.productService = productService;
        this.categoriasService = categoriasService;
    }
    
    // Ruta para mostrar productos (tanto para clientes autenticados como no autenticados)
    @GetMapping
    public String showIndex(Model model, Principal principal) {
        // Verificar si el usuario está autenticado
        if (principal != null) {
            // El usuario ha iniciado sesión
            System.out.println("Usuario autenticado: " + principal.getName());
        } else {
            // El usuario no ha iniciado sesión
            System.out.println("Usuario no autenticado");
        }

        // Mostrar productos de la categoría con ID 1 por defecto
        return showProductsByCategoryInternal(1, model);
    }

// Ruta para mostrar productos por categoría (común)
    @GetMapping("/category/{id}")
    public String showProductsByCategory(@PathVariable Integer id, Model model) {
        // Mostrar productos según la categoría seleccionada
        return showProductsByCategoryInternal(id, model);
    }

// Método privado para reutilizar la lógica
    private String showProductsByCategoryInternal(Integer categoryId, Model model) {
        // Obtener productos por categoría
        Iterable<ProductosEntity> products = productService.getProductsByCategory(categoryId);
        // Obtener todas las categorías para los botones
        Iterable<CategoriasEntity> categories = categoriasService.getCategories();
        // Añadir productos y categorías al modelo
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "index"; // Redirigir a la vista index.html
    }
    
    @GetMapping("/privacidad")
    public String showPrivacy(){
        return "politica/privacidad";
    }

}
