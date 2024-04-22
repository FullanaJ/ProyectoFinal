package com.fullana.proyectofinal.utils;

import static com.fullana.proyectofinal.GlobalConfiguration.APPLICATION_NAME;

import android.app.Activity;
import android.net.Uri;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.CompletableFuture;

public class CommonUtilsDrive {

    private static String ID;

    /**
     * Get the Drive service
     * @param token
     * @return
     */
    public static Drive getDrive(String token) {
        return new Drive.Builder(OpenIDUtils.HTTP_TRANSPORT, OpenIDUtils.JSON_FACTORY, new GoogleCredential().setAccessToken(token))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Create a PDF file
     * @param activity
     * @param name
     */
    public static void getDriveFileToPdf(String id, Activity activity, String name) {
        CommonUtils.createPDFFile(activity, name);
        ID = id;
    }

    public static void loadPdfDataOnPDF(Uri uri, Activity activity) {
        CompletableFuture.runAsync(() -> {
            try (OutputStream out = activity.getContentResolver().openOutputStream(uri)) {
                CommonUtilsDrive.getDrive(OpenIDUtils.CLIENT_TOKEN).files().export(ID, "application/pdf")
                        .executeMediaAndDownloadTo(out);
                out.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).join();
    }

    public static String copyFile(Drive drive, String id, String name) {
        File fileMetadata = new File();
        fileMetadata.setName(name);// Define el nombre del archivo
        fileMetadata.setCopyRequiresWriterPermission(true);// Define que la copia requiere permisos de escritura
        try {
            // Realiza la copia del archivo
            return drive.files().copy(id, fileMetadata).execute().getId();
        } catch (IOException e) {
            System.out.println(e);
        }

        return id;
    }
}
