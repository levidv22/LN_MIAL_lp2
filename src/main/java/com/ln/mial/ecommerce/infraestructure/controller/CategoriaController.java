package com.ln.mial.ecommerce.infraestructure.controller;

import com.ln.mial.ecommerce.app.service.*;
import com.ln.mial.ecommerce.infraestructure.entity.*;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
public class CategoriaController {

    private final CategoriasService categoriasService;

    public CategoriaController(CategoriasService categoriasService) {
        this.categoriasService = categoriasService;
    }

    @GetMapping
    public String showCategories(Model model) {
        model.addAttribute("categories", categoriasService.getCategories());
        return "admin/products";
    }

    @PostMapping
    public String addCategory(@RequestParam String name, @RequestParam String status) {
        CategoriasEntity category = new CategoriasEntity();
        category.setName(name);
        category.setStatusCategoria(StatusCategoria.valueOf(status));
        categoriasService.saveCategory(category);
        return "redirect:/admin/create";
    }

}
