package com.fullana.proyectofinal.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Client {

    private String name;
    private String address;
    private String city;
    private String nif;

}
