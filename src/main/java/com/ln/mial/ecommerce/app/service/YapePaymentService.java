//package com.ln.mial.ecommerce.app.service;
//
//import java.math.BigDecimal;
//import java.util.HashMap;
//import java.util.Map;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class YapePaymentService {
//    @Value("${yape.api.url}")
//    private String yapeApiUrl;
//
//    @Value("${yape.api.key}")
//    private String yapeApiKey;
//
//    public String processPayment(BigDecimal amount, String currency, String description) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Headers de autenticación para la API de Yape
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + yapeApiKey);
//        headers.set("Content-Type", "application/json");
//
//        // Cuerpo de la solicitud de pago
//        Map<String, Object> paymentRequest = new HashMap<>();
//        paymentRequest.put("amount", amount);
//        paymentRequest.put("currency", currency);
//        paymentRequest.put("description", description);
//
//        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(paymentRequest, headers);
//
//        try {
//            ResponseEntity<String> response = restTemplate.exchange(
//                    yapeApiUrl + "/payments", HttpMethod.POST, requestEntity, String.class);
//
//            if (response.getStatusCode().is2xxSuccessful()) {
//                // Procesa la respuesta, por ejemplo, obteniendo el ID de la transacción
//                return "Pago realizado con éxito: " + response.getBody();
//            } else {
//                return "Error en el pago: " + response.getBody();
//            }
//        } catch (Exception e) {
//            return "Hubo un error al procesar el pago: " + e.getMessage();
//        }
//    }
//}
