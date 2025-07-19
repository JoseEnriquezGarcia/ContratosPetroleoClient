package com.JEnriquez.Crud.Controller;

import com.JEnriquez.Crud.ML.NodoComercialEntrega;
import com.JEnriquez.Crud.ML.NodoComercialRecepcion;
import com.JEnriquez.Crud.ML.Result;
import com.JEnriquez.Crud.ML.Zona;
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
@RequestMapping("/zonas")
public class ZonaController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String URL_BASE = "http://localhost:8081/";

    @GetMapping
    public String GetZona(Model model) {

        try {
            ResponseEntity<Result<NodoComercialRecepcion>> responseNodoRecepcion = restTemplate.exchange(URL_BASE + "nodoRecepcion",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<NodoComercialRecepcion>>() {
            });

            ResponseEntity<Result<NodoComercialEntrega>> responseNodoEntrega = restTemplate.exchange(URL_BASE + "nodoEntrega",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<NodoComercialEntrega>>() {
            });

            model.addAttribute("listaZonaExtraccion", responseNodoRecepcion.getBody().objects);
            model.addAttribute("listaZonaInyeccion", responseNodoEntrega.getBody().objects);
        } catch (HttpStatusCodeException ex) {
            model.addAttribute("status", ex.getStatusCode());
            model.addAttribute("message", ex.getMessage());
            return "ErrorPage";
        }
        return "ViewZonas";
    }
}
