package com.JEnriquez.Crud.Controller;

import com.JEnriquez.Crud.ML.Contrato;
import com.JEnriquez.Crud.ML.NodoComercialEntrega;
import com.JEnriquez.Crud.ML.NodoComercialRecepcion;
import com.JEnriquez.Crud.ML.Result;
import com.JEnriquez.Crud.ML.Usuario;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/contratos")
public class ContratosController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String URL_BASE = "http://localhost:8081/";

    @GetMapping
    public String GetContratosFactura(Model model) {
        try {
            Contrato contratoBusqueda = new Contrato();
            contratoBusqueda.Usuario = new Usuario();
            contratoBusqueda.NodoComercialRecepcion = new NodoComercialRecepcion();
            contratoBusqueda.NodoComercialEntrega = new NodoComercialEntrega();

            ResponseEntity<Result<Contrato>> response = restTemplate.exchange(URL_BASE + "contrato",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Contrato>>() {
            });

            ResponseEntity<Result<Usuario>> responseUsuario = restTemplate.exchange(URL_BASE + "usuario",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Usuario>>() {
            });

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

            model.addAttribute("contratoBusqueda", contratoBusqueda);
            model.addAttribute("mostrarMensaje", false);
            model.addAttribute("listaContratos", response.getBody().objects);
            model.addAttribute("listaUsuarios", responseUsuario.getBody().objects);
            model.addAttribute("listaNodoRecepcion", responseNodoRecepcion.getBody().objects);
            model.addAttribute("listaNodoEntrega", responseNodoEntrega.getBody().objects);
        } catch (HttpStatusCodeException ex) {
            model.addAttribute("status", ex.getStatusCode());
            model.addAttribute("message", ex.getMessage());
            return "ErrorPage";
        }
        return "ViewContratos";
    }

    @PostMapping("/busqueda")
    public String Busqueda(@ModelAttribute Contrato contrato, Model model) {
        try {
            Result result = new Result();
            Contrato contratoBusqueda = new Contrato();
            contratoBusqueda.Usuario = new Usuario();
            contratoBusqueda.NodoComercialRecepcion = new NodoComercialRecepcion();
            contratoBusqueda.NodoComercialEntrega = new NodoComercialEntrega();

            ResponseEntity<Result<Contrato>> response = restTemplate.exchange(URL_BASE + "contrato/busquedaContratoService",
                    HttpMethod.POST,
                    new HttpEntity<>(contrato),
                    new ParameterizedTypeReference<Result<Contrato>>() {
            });

            ResponseEntity<Result<Contrato>> responseContratos = restTemplate.exchange(URL_BASE + "contrato",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Contrato>>() {
            });

            ResponseEntity<Result<Usuario>> responseUsuario = restTemplate.exchange(URL_BASE + "usuario",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Usuario>>() {
            });

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
            if (!response.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(204))) {
                result.objects = response.getBody().objects;
                model.addAttribute("contratoBusqueda", contratoBusqueda);
                model.addAttribute("mostrarMensaje", false);
                model.addAttribute("listaContratos", result.objects);
                model.addAttribute("listaContratosBusqueda", responseContratos.getBody().objects);
                model.addAttribute("listaUsuarios", responseUsuario.getBody().objects);
                model.addAttribute("listaNodoRecepcion", responseNodoRecepcion.getBody().objects);
                model.addAttribute("listaNodoEntrega", responseNodoEntrega.getBody().objects);
            } else {
                model.addAttribute("contratoBusqueda", contratoBusqueda);
                model.addAttribute("mostrarMensaje", true);
                model.addAttribute("listaContratos", result);
                model.addAttribute("listaContratosBusqueda", responseContratos.getBody().objects);
                model.addAttribute("listaUsuarios", responseUsuario.getBody().objects);
                model.addAttribute("listaNodoRecepcion", responseNodoRecepcion.getBody().objects);
                model.addAttribute("listaNodoEntrega", responseNodoEntrega.getBody().objects);
            }
        } catch (HttpStatusCodeException ex) {
            model.addAttribute("status", ex.getStatusCode());
            model.addAttribute("message", ex.getMessage());
            return "ErrorPage";
        }
        return "ViewContratos";
    }
}
