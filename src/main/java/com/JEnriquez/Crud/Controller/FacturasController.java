package com.JEnriquez.Crud.Controller;

import com.JEnriquez.Crud.ML.Contrato;
import com.JEnriquez.Crud.ML.Factura;
import com.JEnriquez.Crud.ML.Result;
import com.JEnriquez.Crud.ML.Usuario;
import java.util.Comparator;
import java.util.stream.Collectors;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/facturas")
public class FacturasController {

    private RestTemplate restTemplate = new RestTemplate();
    private String urlBase = "http://localhost:8081/";

    @GetMapping
    public String GetAll(@RequestParam(defaultValue = "0") int currentPage, Model model) {
        try {
            Factura facturaBusqueda = new Factura();
            facturaBusqueda.Contrato = new Contrato();
            facturaBusqueda.Contrato.Usuario = new Usuario();
            Result result = new Result();

            ResponseEntity<Result<Factura>> response = restTemplate.exchange(urlBase + "factura?numeroPagina=" + currentPage,
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Factura>>() {
            });

            ResponseEntity<Result<Contrato>> responseContrato = restTemplate.exchange(urlBase + "contrato",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Contrato>>() {
            });

            ResponseEntity<Result<Usuario>> responseUsuarios = restTemplate.exchange(urlBase + "usuario",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Usuario>>() {
            });

            result.objects = response.getBody().objects.stream()
                    .sorted(Comparator.comparing(f -> f.getIdFactura()))
                    .collect(Collectors.toList());
            result.currentPage = response.getBody().currentPage;

            model.addAttribute("facturaBusqueda", facturaBusqueda);
            model.addAttribute("numeroPagina", result.currentPage);
            model.addAttribute("listaFacturas", result.objects);
            model.addAttribute("listaContratos", responseContrato.getBody().objects);
            model.addAttribute("listaUsuarios", responseUsuarios.getBody().objects);

        } catch (HttpStatusCodeException ex) {
            model.addAttribute("status", ex.getStatusCode());
            model.addAttribute("message", ex.getMessage());
            return "ErrorPage";
        }
        return "ViewFacturas";
    }

    @PostMapping("/busqueda")
    public String BusquedaDinamica(@ModelAttribute Factura factura, Model model) {
        try {
            ResponseEntity<Result> response = restTemplate.exchange(urlBase + "busquedaService",
                    HttpMethod.POST,
                    new HttpEntity<>(factura),
                    new ParameterizedTypeReference<Result>() {
            });
        } catch (HttpStatusCodeException ex) {
            model.addAttribute("status", ex.getStatusCode());
            model.addAttribute("message", ex.getMessage());
            return "ErrorPage";
        }
        return "redirect:/facturas";
    }

}
