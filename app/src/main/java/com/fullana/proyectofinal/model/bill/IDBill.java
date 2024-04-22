package com.fullana.proyectofinal.model.bill;

import android.app.Activity;

import com.fullana.proyectofinal.R;
import com.fullana.proyectofinal.model.Client;
import com.fullana.proyectofinal.model.Company;
import com.fullana.proyectofinal.model.ID;
import com.fullana.proyectofinal.model.Item;
import com.fullana.proyectofinal.utils.CommonUtils;
import com.fullana.proyectofinal.utils.CommonUtilsDialog;
import com.fullana.proyectofinal.utils.CommonUtilsDrive;
import com.fullana.proyectofinal.utils.CommonUtilsSpreadsheet;
import com.fullana.proyectofinal.utils.OpenIDUtils;
import com.google.api.services.sheets.v4.Sheets;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IDBill extends ID implements Serializable {

    Instructions instructions;

    public IDBill(int imageId, String googleId, int maxItemsForBill, Instructions instructions) {
        super(imageId, googleId, maxItemsForBill);
        this.instructions = instructions;
    }

    public enum Instructions implements Action {
        BILL1 {
            @Override
            public FinalBill createBill(Bill bill, Activity context) {

                return CompletableFuture.supplyAsync(() -> {
                    int times = bill.getManyBills(bill);
                    Sheets.Spreadsheets.Values values = CommonUtilsSpreadsheet.getSpreadValues(OpenIDUtils.CLIENT_TOKEN);
                    FinalBill finalBill;
                    try {
                        String idSheet = CommonUtilsDrive.copyFile(CommonUtilsDrive.getDrive(OpenIDUtils.CLIENT_TOKEN), bill.getGoogleId(), bill.generateName());
                        CommonUtilsSpreadsheet.editSpreadsheet(values, idSheet, "E9", bill.getComment());
                        CommonUtilsSpreadsheet.editSpreadsheet(values, idSheet, "B6", CommonUtils.stringDateFromDate(bill.getDate()));
                        CommonUtilsSpreadsheet.editSpreadsheet(values, idSheet, "B5", bill.getNumber());
                        encapsulation(values, bill.getClient(), idSheet);
                        encapsulation(values, bill.getCompany(), idSheet);
                        finalBill = encapsulation(values, bill.getItems(), idSheet, times);
                        return finalBill;
                    } catch (IOException ignored) {
                        CommonUtilsDialog.defaultDialog(context, context.getString(R.string.error), context.getString(R.string.error_connection));
                        return null;
                    }
                }).whenComplete((finalBill, throwable) -> {
                    if (finalBill != null) {
                        CommonUtilsDrive.getDriveFileToPdf(finalBill.getBillGoogleId(), context, bill.generateName().replaceAll("/", "_"));
                    }
                }).join();
            }

            @Override
            public void encapsulation(Sheets.Spreadsheets.Values values, Company company, String id) throws IOException {
                CommonUtilsSpreadsheet.editSpreadsheet(values, id, "D4", company.getAddress());
                CommonUtilsSpreadsheet.editSpreadsheet(values, id, "G5", company.getPostalCode() + " " + company.getCity());
                CommonUtilsSpreadsheet.editSpreadsheet(values, id, "H6", "NIF " + company.getNie());
                CommonUtilsSpreadsheet.editSpreadsheet(values, id, "G3", company.getNameCeo());
            }

            @Override
            public void encapsulation(Sheets.Spreadsheets.Values values, Client client, String id) throws IOException {
                CommonUtilsSpreadsheet.editSpreadsheet(values, id, "B9", client.getAddress());
                CommonUtilsSpreadsheet.editSpreadsheet(values, id, "B10", client.getCity());
                CommonUtilsSpreadsheet.editSpreadsheet(values, id, "B11", client.getNif());
                CommonUtilsSpreadsheet.editSpreadsheet(values, id, "B8", client.getName());
            }

            @Override
            public FinalBill encapsulation(Sheets.Spreadsheets.Values values, List<Item> items, String id, int times) throws IOException {
                int firstRow = 14;
                BigDecimal discount = new BigDecimal(0);
                BigDecimal totalIVA = new BigDecimal(0);
                BigDecimal subtotal = new BigDecimal(0);
                BigDecimal total = new BigDecimal(0);

                for (Item item : items) {
                    CommonUtilsSpreadsheet.editSpreadsheet(values, id, "B" + firstRow, item.getCode());
                    CommonUtilsSpreadsheet.editSpreadsheet(values, id, "C" + firstRow, item.getArticle());
                    CommonUtilsSpreadsheet.editSpreadsheet(values, id, "D" + firstRow, String.valueOf(item.getUnits()));
                    CommonUtilsSpreadsheet.editSpreadsheet(values, id, "E" + firstRow, String.valueOf(item.getPrice()));
                    CommonUtilsSpreadsheet.editSpreadsheet(values, id, "F" + firstRow, String.valueOf(item.getSubtotal()));
                    CommonUtilsSpreadsheet.editSpreadsheet(values, id, "G" + firstRow, String.valueOf(item.getCalculatedIVA()));
                    CommonUtilsSpreadsheet.editSpreadsheet(values, id, "H" + firstRow, String.valueOf(item.getTotal()));
                    totalIVA.add(item.getCalculatedIVA());
                    subtotal.add(item.getSubtotal());
                    total.add(item.getTotal());
                    firstRow++;
                }
                CommonUtilsSpreadsheet.editSpreadsheet(values, id, "H30", String.valueOf(discount));
                CommonUtilsSpreadsheet.editSpreadsheet(values, id, "F33", String.valueOf(totalIVA));
                CommonUtilsSpreadsheet.editSpreadsheet(values, id, "H29", String.valueOf(subtotal));
                CommonUtilsSpreadsheet.editSpreadsheet(values, id, "H36", String.valueOf(total));
                CommonUtilsSpreadsheet.editSpreadsheet(values, id, "H32", String.valueOf(total));
                return new FinalBill(Bill.generateVoidBill(), subtotal, total, totalIVA, id);
            }
        };

        public abstract FinalBill createBill(Bill bill, Activity context);
    }

    private interface Action {
        FinalBill createBill(Bill bill, Activity context);

        void encapsulation(Sheets.Spreadsheets.Values values, Company company, String id) throws IOException;

        void encapsulation(Sheets.Spreadsheets.Values values, Client client, String id) throws IOException;

        FinalBill encapsulation(Sheets.Spreadsheets.Values values, List<Item> items, String id, int times) throws IOException;
    }
}
