package com.JEnriquez.Crud.Controller;

import com.JEnriquez.Crud.ML.Factura;
import com.JEnriquez.Crud.ML.Result;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/facturas")
public class FacturasController {

    private RestTemplate restTemplate = new RestTemplate();
    private String urlBase = "http://localhost:8081/";

    @GetMapping
    public String GetAll(Model model) {
        try {

            ResponseEntity<Result<Factura>> response = restTemplate.exchange(urlBase + "factura",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Factura>>() {
            });
            Result result = response.getBody();

            model.addAttribute("listaFacturas", result.objects);
        } catch (HttpStatusCodeException ex) {
            model.addAttribute("status", ex.getStatusCode());
            model.addAttribute("message", ex.getMessage());
        }
        return "ViewFacturas";
    }
}
