package com.fullana.proyectofinal.model.bill;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class FinalBill extends Bill {
    private final BigDecimal subtotal;
    private final BigDecimal total;
    private final BigDecimal totalIVA;
    private String billGoogleId;

    public FinalBill(Bill bill, BigDecimal subtotal, BigDecimal total, BigDecimal totalIVA, String billGoogleId) {
        super(bill.getImageId(), bill.getGoogleId(), bill.getMaxItemsForBill(), bill.getInstructions(), bill.getDate(), bill.getNumber(), bill.getCompany(), bill.getClient(), bill.getComment(), bill.getItems(), bill.getIVA());
        this.billGoogleId = billGoogleId;
        this.subtotal = subtotal;
        this.total = total;
        this.totalIVA = totalIVA;
    }
    public FinalBill setBill(Bill bill) {
        return new FinalBill(bill, this.subtotal, this.total, this.totalIVA, this.billGoogleId);
    }
}
