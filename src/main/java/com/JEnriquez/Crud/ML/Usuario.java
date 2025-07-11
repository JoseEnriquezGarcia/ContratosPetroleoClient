package com.JEnriquez.Crud.ML;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Usuario {
    private int IdUsuario;
    
    private String Nombre;
    
    private List<Contrato> contratos;
}
