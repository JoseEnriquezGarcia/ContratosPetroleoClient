package com.JEnriquez.Crud.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inicio")
public class ContratosPetroleoController {
    
    @GetMapping
    public String Index(){
        
        
        
        return "Index";
    }
}
