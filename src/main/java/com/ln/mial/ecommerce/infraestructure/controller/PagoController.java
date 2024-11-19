package com.ln.mial.ecommerce.infraestructure.controller;

import jakarta.servlet.http.*;
import com.ln.mial.ecommerce.app.service.*;
import com.ln.mial.ecommerce.infraestructure.entity.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.*;
import org.springframework.web.servlet.*;
import java.io.*;
import java.math.*;
import java.time.*;
import java.util.*;
import com.ln.mial.ecommerce.app.service.DetallePedidosService;
import com.ln.mial.ecommerce.app.service.UploadFile;
import com.ln.mial.ecommerce.infraestructure.entity.StatusPedido;

@Controller
@RequestMapping("/user/checkout")
public class PagoController {

    private final PagosService pagosService;
    private final DetallePedidosService detallePedidosService;
    private final UploadFile uploadFile;
    private final PedidosService pedidosService;
    private final AlmacenService almacenService;

    public PagoController(PagosService pagosService, DetallePedidosService detallePedidosService, UploadFile uploadFile, PedidosService pedidosService, AlmacenService almacenService) {
        this.pagosService = pagosService;
        this.detallePedidosService = detallePedidosService;
        this.uploadFile = uploadFile;
        this.pedidosService = pedidosService;
        this.almacenService = almacenService;
    }

    // Mostrar la vista de pago
    @GetMapping
    public String showPaymentPage(HttpSession session, Model model) {
        PedidosEntity order = (PedidosEntity) session.getAttribute("currentOrder");

        if (order == null) {
            return "redirect:/user/carrito"; // Redirigir al carrito si no hay pedido
        }

        // Obtener los productos del pedido
        List<DetallePedidosEntity> orderDetails = detallePedidosService.getOrderDetailsByOrder(order);
        BigDecimal totalAmount = orderDetails.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Añadir datos del usuario y productos al modelo
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("cart", orderDetails);
        model.addAttribute("totalAmount", totalAmount);

        return "pago";  // Redirigir a la vista de pago
    }

    // Procesar el pago
    @PostMapping
    public ModelAndView confirmarPago(@RequestParam("file") MultipartFile multipartfile,
                                      @RequestParam("shippingAddress") String shippingAddress,
                                      HttpSession session) throws IOException {
        PedidosEntity order = (PedidosEntity) session.getAttribute("currentOrder");

        if (order == null) {
            return new ModelAndView("redirect:/user/carrito"); // Si no hay pedido en proceso, redirigir
        }

        // Guardar la dirección de envío en el pedido
        order.setShippingAddress(shippingAddress);

        // Guardar el comprobante en el sistema de archivos
        String imagePago = uploadFile.upload(multipartfile); // Guardar la imagen y obtener el nombre del archivo

        // Crear un nuevo registro de pago
        PagosEntity pago = new PagosEntity();
        pago.setAmount(order.getTotalAmount());
        pago.setPaymentDate(LocalDateTime.now());
        pago.setOrder(order);
        pago.setImagePago(imagePago);
        pagosService.savePayment(pago);

        // Cambiar el estado del pedido a PAGADO
        order.setStatusPedido(StatusPedido.PAGADO);
        pedidosService.saveOrder(order); // Guardar los cambios en el pedido

        // Actualizar el stock en el almacén
        for (DetallePedidosEntity orderDetail : detallePedidosService.getOrderDetailsByOrder(order)) {
            ProductosEntity product = orderDetail.getProduct();
            AlmacenEntity stock = almacenService.getStockByProductEntity(product).get(0);

            // Actualizar las salidas y balance
            stock.setSalidas(stock.getSalidas() + orderDetail.getQuantity());
            stock.setBalance(stock.getEntradas() - stock.getSalidas());
            almacenService.saveStock(stock);
        }

        // Remover el pedido de la sesión
        session.removeAttribute("currentOrder");

        return new ModelAndView("redirect:/user/historial"); // Redirigir al historial de pedidos
    }

}
