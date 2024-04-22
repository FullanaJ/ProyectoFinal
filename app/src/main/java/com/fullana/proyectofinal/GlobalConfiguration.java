package com.fullana.proyectofinal;

import android.content.Context;

import com.fullana.proyectofinal.model.bill.Bill;
import com.fullana.proyectofinal.model.ID;
import com.fullana.proyectofinal.model.bill.IDBill;
import com.fullana.proyectofinal.utils.CommonUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

public class GlobalConfiguration {
    public static final String REDIRECT = "com.fullana.proyectofinal:/oauth2callback";
    public static final String APPLICATION_NAME = "Proyecto final";
    public static final List<ID> BILLS_ID = List.of(
            new IDBill(R.drawable.bill_1, "1n9Hs1lKaPgwXuowatDIY01GZeM6qMy_MuITSN2hnFVI", 14, IDBill.Instructions.BILL1)
    );
    public static final List<ID> DELIVERY_NOTES_ID = List.of(

    );
    public static String COMPANY_NAME;
    public static String COMPANY_CEO;
    public static String COMPANY_NIE;
    public static String COMPANY_ADDRESS;
    public static String COMPANY_CITY;
    public static String COMPANY_POSTAL_CODE;
    public static String COMPANY_PHONE;
    public static String COMPANY_EMAIL;
    public static File BILLS_SAVED;
    public static BigDecimal BILL_COUNT;
    public static Integer IVA;
    public static String COIN;
    private static final String BILLS_FILE_NAME = "Bills.json";
    private static final String COMPANY_FILE_NAME = "Company.json";

    public static void loadBills(Context context) {
        BILLS_SAVED = new File(context.getFilesDir(), BILLS_FILE_NAME);
    }

    private static void loadCompanyData(Context context) {
        List<String> list = (List<String>) CommonUtils.readFile(new File(context.getFilesDir(), COMPANY_FILE_NAME), List.class);
        if (list != null) {
            COMPANY_NAME = list.get(0);
            COMPANY_CEO = list.get(1);
            COMPANY_NIE = list.get(2);
            COMPANY_ADDRESS = list.get(3);
            COMPANY_CITY = list.get(4);
            COMPANY_POSTAL_CODE = list.get(5);
            COMPANY_PHONE = list.get(6);
            COMPANY_EMAIL = list.get(7);
            BILL_COUNT = new BigDecimal(list.get(8));
            IVA = Integer.parseInt(list.get(9));
            COIN = list.get(10);
        } else {
            COMPANY_NAME = "";
            COMPANY_CEO = "";
            COMPANY_NIE = "";
            COMPANY_ADDRESS = "";
            COMPANY_CITY = "";
            COMPANY_POSTAL_CODE = "";
            COMPANY_PHONE = "";
            COMPANY_EMAIL = "";
            BILL_COUNT = new BigDecimal(0);
            IVA = Bill.STANDARD;
            COIN = "€";
        }
    }

    public static void loadGlobalConfig(Context context) {
        loadBills(context);
        loadCompanyData(context);
    }

    // Método estático para escribir los valores en un archivo JSON
    public static void writeJsonCompanyData(Context context) {
        CommonUtils.writeFile(
                List.of(
                        COMPANY_NAME,
                        COMPANY_CEO,
                        COMPANY_NIE,
                        COMPANY_ADDRESS,
                        COMPANY_CITY,
                        COMPANY_POSTAL_CODE,
                        COMPANY_PHONE,
                        COMPANY_EMAIL,
                        BILL_COUNT.toString(),
                        IVA.toString()
                ),
                new File(context.getFilesDir(), COMPANY_FILE_NAME));
    }

}
