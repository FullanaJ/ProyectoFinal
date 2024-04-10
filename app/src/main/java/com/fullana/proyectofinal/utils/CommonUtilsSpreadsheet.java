package com.fullana.proyectofinal.utils;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.sheets.v4.Sheets;

public class CommonUtilsSpreadsheet {

    public static String CLIENT_TOKEN;


    public static Sheets getSheet(String token) {
        GoogleCredential credential = new GoogleCredential().setAccessToken(token);

        return new Sheets.Builder(OpenIDUtils.HTTP_TRANSPORT, OpenIDUtils.JSON_FACTORY,credential)
                .setApplicationName(OpenIDUtils.APPLICATION_NAME)
                .build();
    }

}
