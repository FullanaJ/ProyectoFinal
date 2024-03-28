package com.fullana.proyectofinal.Model;

import java.math.BigDecimal;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Factura {

    private String date;
    private String number;
    private Empresa empresa;
    private Client client;
    private String comentario;

    private ArrayList<Item> items;

    private BigDecimal subtotal;
    private BigDecimal descuento;
    private BigDecimal iva;
    private BigDecimal recargoEquivalencia;
    private BigDecimal total;






}
