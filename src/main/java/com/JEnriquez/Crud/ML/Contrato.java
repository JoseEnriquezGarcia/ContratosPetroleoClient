package com.JEnriquez.Crud.ML;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Contrato {

    private int IdContrato;

    public Usuario Usuario;

    public NodoComercialRecepcion NodoComercialRecepcion;

    public NodoComercialEntrega NodoComercialEntrega;

    private String ClaveContrato;
}
