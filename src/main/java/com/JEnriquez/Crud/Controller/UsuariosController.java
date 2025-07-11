package com.JEnriquez.Crud.Controller;

import com.JEnriquez.Crud.ML.Result;
import com.JEnriquez.Crud.ML.Usuario;
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
@RequestMapping("/usuario")
public class UsuariosController {

    private RestTemplate restTemplate = new RestTemplate();
    private String urlBase = "http://localhost:8081/";

    @GetMapping
    public String GetAllUsuariosContratos(Model model) {
        try {
            ResponseEntity<Result<Usuario>> response = restTemplate.exchange(urlBase + "usuario",
                    HttpMethod.GET,
                    HttpEntity.EMPTY,
                    new ParameterizedTypeReference<Result<Usuario>>() {
            });

            Result result = new Result();
            result = response.getBody();

            model.addAttribute("listaUsuarios", result.objects);
        } catch (HttpStatusCodeException ex) {
            model.addAttribute("status", ex.getStatusCode());
            model.addAttribute("message", ex.getMessage());
        }
        return "ViewUsuarios";
    }
}
