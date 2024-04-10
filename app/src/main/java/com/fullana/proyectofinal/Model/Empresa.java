package com.fullana.proyectofinal.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {

    private String name;
    private String nameComercial;
    private String nie;
    private String address;
    private String city;
    private String postalCode;
    private String phone;
    private String email;

}
