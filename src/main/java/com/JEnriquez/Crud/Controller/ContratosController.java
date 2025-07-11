package com.JEnriquez.Crud.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/contratos")
public class ContratosController {

    private RestTemplate restTemplate = new RestTemplate();
    private String urlBase = "";

    @GetMapping
    public String GetContratosFactura() {

        return "";
    }
}
