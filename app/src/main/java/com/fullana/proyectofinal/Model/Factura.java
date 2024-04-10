package com.fullana.proyectofinal.Model;

import android.app.Activity;


import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private BigDecimal recargoEquivalencia;
    private BigDecimal total;
    @Setter(AccessLevel.NONE)
//    private Intruciones intruciones;
    private Integer IVA;

    public void arma(Path path, Activity activity) {
//        intruciones.arma(this, path, activity);
    }

    public static int
            ESTANDAR = 21,
            REDUCIDO = 10,
            SUPER_REDUUCIDO = 4;

    private String generateUniqueName() {
        return client.getName() + "_" + date + "_" + number;
    }

//    public enum Intruciones implements Action {
//        FACTURA1 {
//            @Override
//            public void arma(Factura factura, Path path, Activity activity) {
//                try {
//                    InputStream inputStream = activity.getResources().openRawResource(R.raw.factura_ejemplo1);
//
//                    byte[] buffer = new byte[inputStream.available()];
//                    inputStream.read(buffer);
//
//                    File targetFile = new File(path.toString()+"/prueba1.odt");
//                    targetFile.createNewFile();
//                    Files.write(targetFile.toPath(), buffer);
//                    Sheet sheet = CommonUtilsCalc.createSheetFromString(targetFile.toString());
//                    encapsulacion(sheet, factura.getEmpresa());
//                    encapsulacion(sheet, factura.getClient());
//                    CommonUtilsCalc.saveSheet(sheet, path.resolve(factura.generateUniqueName()).toAbsolutePath().toString());
//
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//            @Override
//            public void encapsulacion(Sheet sheet, Empresa empresa) {
//                try {
//                    CommonUtilsCalc.editCell(sheet, 3, 5, empresa.getName());
//                    CommonUtilsCalc.editCell(sheet, 4, 8, empresa.getAddress());
//                    CommonUtilsCalc.editCell(sheet, 5, 8, empresa.getPostalCode() + " " + empresa.getCity());
//                    CommonUtilsCalc.editCell(sheet, 6, 8, "NIF" + empresa.getNie());
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//
//            @Override
//            public void encapsulacion(Sheet sheet, Client client) {
//                try {
//                    CommonUtilsCalc.editCell(sheet, 8, 3, client.getName());
//                    CommonUtilsCalc.editCell(sheet, 9, 3, client.getAddress());
//                    CommonUtilsCalc.editCell(sheet, 10, 3, client.getCity());
//                    CommonUtilsCalc.editCell(sheet, 11, 3, client.getNif());
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        },
//        FACTURA2 {
//            @Override
//            public void arma(Factura factura, Path path, Activity activity) {
//
//            }
//
//            @Override
//            public void encapsulacion(Sheet sheet, Empresa empresa) {
//
//            }
//
//            @Override
//            public void encapsulacion(Sheet sheet, Client client) {
//
//            }
//        },
//
//        FACTURA3 {
//            @Override
//            public void arma(Factura factura, Path path, Activity activity) {
//
//            }
//
//            @Override
//            public void encapsulacion(Sheet sheet, Empresa empresa) {
//
//            }
//
//            @Override
//            public void encapsulacion(Sheet sheet, Client client) {
//
//            }
//        }
//    }
//
//    private interface Action {
//        void arma(Factura factura, Path path, Activity activity);
//
//        void encapsulacion(Sheet sheet, Empresa empresa);
//
//        void encapsulacion(Sheet sheet, Client client);
//    }
}
