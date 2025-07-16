package com.JEnriquez.Crud.Controller;

import com.JEnriquez.Crud.ML.Contrato;
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
@RequestMapping("/contratos")
public class ContratosController {

    private RestTemplate restTemplate = new RestTemplate();
    private String urlBase = "http://localhost:8081/";

    @GetMapping
    public String GetContratosFactura(Model model) {
        try {
            ResponseEntity<Result<Contrato>> response = restTemplate.exchange(urlBase + "contrato",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Contrato>>() {
            });
            
            model.addAttribute("listaContratos", response.getBody().objects);
        } catch (HttpStatusCodeException ex) {
            model.addAttribute("status", ex.getStatusCode());
            model.addAttribute("message", ex.getMessage());
            return "ErrorPage";
        }
        return "ViewContratos";
    }
}
