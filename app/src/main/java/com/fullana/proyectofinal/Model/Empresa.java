package com.fullana.proyectofinal.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {

    String name;
    String nameComercial;
    String nie;
    String address;
    String city;
    String postalCode;
    String phone;
    String email;

}
