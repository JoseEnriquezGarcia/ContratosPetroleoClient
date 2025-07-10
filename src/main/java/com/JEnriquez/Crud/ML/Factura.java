package com.JEnriquez.Crud.ML;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Factura {
    
    private int IdFactura;
    
    public Contrato Contrato;
    
    private Date Fecha;
    
    private double NominadaRecepcion;
    
    private double AsignadaRecepcion;
    
    private double NominadaEntrega;
    
    private double AsignadaEntrega;
    
    private double ExcesoFirme;
    
    private double UsoInterrumpible;
    
    private double GasExceso;
    
    private double CargoUso;

    private double CargoGasExceso;
    
    private double TotalFactura;
}
