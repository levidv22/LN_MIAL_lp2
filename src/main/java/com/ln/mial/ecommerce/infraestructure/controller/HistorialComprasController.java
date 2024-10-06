package com.ln.mial.ecommerce.infraestructure.controller;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import com.ln.mial.ecommerce.app.service.*;
import com.ln.mial.ecommerce.infraestructure.entity.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import com.ln.mial.ecommerce.infraestructure.dto.PedidoAgrupadoDTO;
import java.time.format.DateTimeFormatter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/historial")
public class HistorialComprasController {

    private final PagosService pagosService;
    private final PedidosService pedidosService;
    private final DetallePedidosService detallePedidosService;
    private final EnviosService enviosService;

    public HistorialComprasController(PagosService pagosService, PedidosService pedidosService, DetallePedidosService detallePedidosService, EnviosService enviosService) {
        this.pagosService = pagosService;
        this.pedidosService = pedidosService;
        this.detallePedidosService = detallePedidosService;
        this.enviosService = enviosService;
    }

    @GetMapping("/user")
    public String showPurchasedProducts(HttpSession session, Model model) {
        // Obtener el usuario de la sesión
        UsuariosEntity user = (UsuariosEntity) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login"; // Redirigir si no está logueado
        }

        // Obtener los pedidos pagados (PAGADO) del usuario
        List<PedidosEntity> paidOrders = pedidosService.getOrdersByUserAndStatus(user, StatusPedido.PAGADO);

        // Crear una lista para los detalles agrupados por pedido
        List<PedidoAgrupadoDTO> pedidosAgrupados = new ArrayList<>();

        for (PedidosEntity order : paidOrders) {
            List<DetallePedidosEntity> orderDetails = detallePedidosService.getOrderDetailsByOrder(order);
            PagosEntity payment = pagosService.getPaymentsByOrder(order).stream().findFirst().orElse(null);

            PedidoAgrupadoDTO pedidoAgrupado = new PedidoAgrupadoDTO();
            pedidoAgrupado.setUsername(order.getUser().getUsername());  // Aunque aquí solo es un usuario
            pedidoAgrupado.setDetallesPedido(orderDetails);
            pedidoAgrupado.setTotalAmount(order.getTotalAmount());
            pedidoAgrupado.setImagenPago(payment != null ? payment.getImagePago() : null);

            pedidosAgrupados.add(pedidoAgrupado);
        }

        // Invertir la lista para mostrar los más recientes primero
        Collections.reverse(pedidosAgrupados);

        // Añadir los detalles agrupados al modelo
        model.addAttribute("pedidosAgrupados", pedidosAgrupados);

        return "historial-compras"; // Vista donde mostrar el historial de compras agrupadas
    }

    // Mostrar vista para agregar el envío
    @GetMapping("/envio/{pedidoId}")
    public String showShippingForm(@PathVariable Integer pedidoId, Model model) {
        PedidosEntity pedido = pedidosService.getOrderById(pedidoId);
        if (pedido == null) {
            return "redirect:historial-compras"; // Redirigir si el pedido no existe
        }

        EnviosEntity envio = enviosService.getShippingByOrder(pedido).stream().findFirst().orElse(null);

        // Formatear fechas si existe un envío
        if (envio != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            model.addAttribute("shippingDateFormatted", envio.getShippingDate().format(formatter));
            model.addAttribute("estimatedDeliveryDateFormatted", envio.getEstimatedDeliveryDate().format(formatter));
        }

        model.addAttribute("pedido", pedido);
        model.addAttribute("envio", envio);
        return "ver-envio"; // Vista para agregar envío
    }

}
