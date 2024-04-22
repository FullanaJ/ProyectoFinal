package com.fullana.proyectofinal.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ID implements Serializable {
    private final Integer imageId;
    private final String googleId;
    private final int maxItemsForBill;
}
