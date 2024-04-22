package com.fullana.proyectofinal.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Item {

    private String code;
    private String article;
    private Integer units;
    private BigDecimal price;
    public static Integer IVA;

    public BigDecimal getSubtotal() {
        return price.multiply(BigDecimal.valueOf(units));
    }

    public BigDecimal getCalculatedIVA() {
        return getSubtotal().multiply(BigDecimal.valueOf(100 / IVA));
    }

    public BigDecimal getTotal() {
        return getSubtotal().add(getCalculatedIVA());
    }
}
