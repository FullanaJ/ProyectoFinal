package com.fullana.proyectofinal;

import static org.junit.Assert.assertTrue;

import com.fullana.proyectofinal.utils.CommonUtilsDrive;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;


/**
 * Example local unit test, which will execute on the development machine (host).
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {

        assertTrue(new File("src/main/res/raw/factura_ejemplo1.ods").exists());
    }

    @Test
    public void calculateBills() {
        System.out.println(13 / 14);
        System.out.println(13 % 14);
    }

    @Test
    public void checkJsonBillsFunctionality() throws IOException {
        File json = new File("src/main/java/com/fullana/proyectofinal/prueba.pdf");
        json.createNewFile();
        try (OutputStream out = new FileOutputStream(json)) {
            CommonUtilsDrive.getDrive("token").files().export("1n9Hs1lKaPgwXuowatDIY01GZeM6qMy_MuITSN2hnFVI", "application/pdf")
                    .executeMediaAndDownloadTo(out);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertTrue(json.exists());
//        List<Bill> list = CommonUtils.readFile(json);
//        assertEquals(2, list.size());
////        CommonUtils.addBillToFile(new Bill(),json);
//        list = CommonUtils.readFile(json);
//        assertEquals(3, list.size());
    }


}