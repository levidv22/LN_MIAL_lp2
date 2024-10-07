package com.ln.mial.ecommerce.infraestructure.controller;

import com.ln.mial.ecommerce.app.service.*;
import com.ln.mial.ecommerce.infraestructure.entity.*;
import java.util.*;
import org.slf4j.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/products")
public class AdminProController {

    private final ProductosService productService;
    private final AlmacenService almacenService;
    private final Logger log = LoggerFactory.getLogger(AdminProController.class);

    public AdminProController(ProductosService productService, AlmacenService almacenService) {
        this.productService = productService;
        this.almacenService = almacenService;
    }

    @GetMapping
    public String showProducts(Model model) {
        List<ProductosEntity> products = productService.getProducts();

        for (ProductosEntity product : products) {
            List<AlmacenEntity> stockList = almacenService.getStockByProductEntity(product);
            if (!stockList.isEmpty()) {
                AlmacenEntity stock = stockList.get(0); // Asumiendo que hay solo un stock por producto
                product.setBalance(stock.getBalance()); // Asigna el balance directamente al producto
            } else {
                product.setBalance(0); // No hay stock, establecer a 0
            }
        }

        Collections.reverse(products);
        model.addAttribute("products", products);
        return "admin/productos";
    }
}
