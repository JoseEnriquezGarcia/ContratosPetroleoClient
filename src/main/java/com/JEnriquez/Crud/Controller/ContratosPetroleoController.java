package com.JEnriquez.Crud.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inicio")
public class ContratosPetroleoController {

    @GetMapping
    public String Index(HttpSession session, Model model) {
        if (session.getAttribute("bearer") == null) {
            return "redirect:/Auth/login";
        } else {
            model.addAttribute("username", session.getAttribute("username").toString());
            model.addAttribute("rol", session.getAttribute("rol").toString());
            return "Index";
        }
    }
}
