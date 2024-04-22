package com.fullana.proyectofinal.utils;

import static com.fullana.proyectofinal.GlobalConfiguration.APPLICATION_NAME;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.CopySheetToAnotherSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.SpreadsheetProperties;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CommonUtilsSpreadsheet {

    public static Sheets getSheet(String token) {
        return new Sheets.Builder(OpenIDUtils.HTTP_TRANSPORT, OpenIDUtils.JSON_FACTORY, new GoogleCredential().setAccessToken(token))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static Sheets.Spreadsheets.Values getSpreadValues(String token) {
        return getSheet(token)
                .spreadsheets()
                        .values();
    }

    public static Sheets.Spreadsheets.Values.Get getSpreadsheetGet(Sheets sheets, String sheetID, String range) throws IOException {
        return sheets.spreadsheets().values().get(sheetID, range);
    }

    public static Sheets.Spreadsheets.Values.Get getSpreadsheetValues(Sheets sheets, String sheetID) throws IOException {
        return getSpreadsheetGet(sheets, sheetID, "A1:Z");
    }

    public static void editSpreadsheet(String token, String idSpreadsheet, String range, String value) throws IOException {
        UpdateValuesResponse result = getSheet(token)
                .spreadsheets()
                .values()
                .update(idSpreadsheet, range, new ValueRange().setValues(List.of(Collections.singletonList(value))))
                .execute();
        System.out.println("Update Rows = " + result.getUpdatedRows());
    }

    public static void editSpreadsheet(Sheets sheet, String idSpreadsheet, String range, String value) {
        try {
            UpdateValuesResponse result = sheet.spreadsheets()
                    .values()
                    .update(idSpreadsheet, range, new ValueRange().setValues(List.of(Collections.singletonList(value))))
                    .execute();
            System.out.println("Update Rows = " + result.getUpdatedRows());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void editSpreadsheet(Sheets.Spreadsheets.Values values, String idSpreadsheet, String range, String value) throws IOException {
        UpdateValuesResponse result = values
                .update(idSpreadsheet, range, new ValueRange().setValues(List.of(List.of(value))))
                .setValueInputOption("RAW")
                .execute();
        System.out.println("Update Rows = " + result.getUpdatedRows());
    }


    /**
     * Crea una Spreadsheet al usuario y devulve su id
     *
     * @param token
     * @param title
     * @return
     */
    public static String createNewSpreadsheet(String token, String title) throws IOException {
        Spreadsheet spreadSheet = new Spreadsheet().setProperties(
                new SpreadsheetProperties()
                        .setTitle(title));
        Spreadsheet result = getSheet(token)
                .spreadsheets()
                .create(spreadSheet).execute();
        return result.getSpreadsheetId();
    }

    public static Integer copyPasteSpreadsheet(String token, String spreadsheetIdCopied, String spreadsheetIDPasted) throws IOException {
        return copyPasteSpreadsheet(token, spreadsheetIdCopied, 0, spreadsheetIDPasted);
    }

    public static Integer copyPasteSpreadsheet(String token, String spreadsheetIdCopied, Integer pageToCopy, String spreadsheetIDPasted) throws IOException {
        return getSheet(token)
                .spreadsheets()
                .sheets()
                .copyTo(
                        spreadsheetIdCopied,
                        pageToCopy,
                        new CopySheetToAnotherSpreadsheetRequest()
                                .setDestinationSpreadsheetId(
                                        spreadsheetIDPasted
                                )
                )
                .execute().getSheetId();

    }
}


