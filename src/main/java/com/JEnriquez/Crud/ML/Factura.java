package com.JEnriquez.Crud.ML;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter @Setter
public class Factura {
    
    private int IdFactura;
    
    public Contrato Contrato;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
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
