package com.fullana.proyectofinal.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {

    private String code;
    private String article;
    private int units;
    private float price;

}
