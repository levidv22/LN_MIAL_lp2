package com.ln.mial.ecommerce.infraestructure.controller;

import com.ln.mial.ecommerce.app.service.*;
import com.ln.mial.ecommerce.infraestructure.entity.*;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.slf4j.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/create")
public class AdminFormController {

    private final ProductosService productService;
    private final CategoriasService categoriasService;
    private final AlmacenService almacenService;
    private final Logger log = LoggerFactory.getLogger(AdminFormController.class);

    public AdminFormController(ProductosService productService, CategoriasService categoriasService, AlmacenService almacenService) {
        this.productService = productService;
        this.categoriasService = categoriasService;
        this.almacenService = almacenService;
    }

    @GetMapping
    public String showCategory(Model model) {
        Iterable<CategoriasEntity> categories = categoriasService.getCategories(); // Obtener las categorías
        model.addAttribute("categories", categories); // Añadir las categorías al modelo
        return "admin/formulario";
    }

    @PostMapping
    public String addProduct(ProductosEntity product, @RequestParam("file") MultipartFile multipartfile, @RequestParam("categoryId") Integer categoryId, HttpSession session) throws IOException {
        CategoriasEntity category = categoriasService.getCategoryById(categoryId);
        product.setCategory(category);  // Set selected category
        log.info("Nombre de producto: {}", product);
        productService.saveProduct(product, multipartfile, session);
        return "redirect:/admin/products";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Integer id, Model model) {
        Iterable<CategoriasEntity> categories = categoriasService.getCategories();
        model.addAttribute("categories", categories);
        ProductosEntity product = productService.getProductById(id);
        log.info("Product obtenido: {}", product);
        model.addAttribute("product", product);
        return "admin/editar";
    }


    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Integer id) {
        productService.deleteProductById(id);
        return "redirect:/admin/products";
    }

}
