package com.JEnriquez.Crud.Controller;

import com.JEnriquez.Crud.ML.ResultToken;
import com.JEnriquez.Crud.ML.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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
@RequestMapping("/Auth")
public class LoginController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String URL_BASE = "http://localhost:8081/";

    @GetMapping("/loginPage")
    public String LoginPage(Model model) {
        Usuario usuario = new Usuario();

        model.addAttribute("usuario", usuario);
        return "Login";
    }

    @PostMapping("/Validation")
    public String Procesar(@ModelAttribute Usuario usuario, Model model, HttpSession session) {
        ResultToken resultToken = new ResultToken();

        try {
            ResponseEntity<ResultToken> response = restTemplate.exchange(URL_BASE + "auth/" + "Authlogin",
                    HttpMethod.POST,
                    new HttpEntity<>(usuario),
                    new ParameterizedTypeReference<ResultToken>() {
            });
            
            session.setAttribute("bearer", response.getBody().Bearer);
        } catch (HttpStatusCodeException ex) {
            model.addAttribute("status", ex.getStatusCode().value());
            model.addAttribute("statusText", ex.getStatusCode());
            model.addAttribute("message", ex.getMessage());
            return "ErrorPage";
        }
        
        return "redirect:/inicio";
    }
}
