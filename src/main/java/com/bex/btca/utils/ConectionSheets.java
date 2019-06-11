package com.bex.btca.utils;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

import static com.bex.btca.utils.FilesSheetsDrive.EQC_BTCA_PLACEMENT;
import static com.bex.btca.utils.FilesSheetsDrive.EQDL_BTCA_PLACEMENT;

public class ConectionSheets {
    private static final String APPLICATION_NAME = "btca";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = ConectionSheets.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SheetsScopes.all())
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    /**
     * Prints the names and majors of students in a sample spreadsheet:
     * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
     */
    public void actualizar (String spreadsheetId, String fecha, String status,String value,String version) throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        StringBuilder cellWrite=new StringBuilder();

        if(version.equals(EQC_BTCA_PLACEMENT)||version.equals(EQDL_BTCA_PLACEMENT)){
             cellWrite.append("Placements");
        }else{
             cellWrite.append("Pre_trade");
        }
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String range = "Pre_trade!A:A";
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();

        List<List<Object>> values = response.getValues();
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            int numb=0;
            for (List row : values) {
                for (Object objs: row) {
                    if(objs.equals(fecha)){
                        numb= values.indexOf(row)+1;
                        switch (status) {
                            case "Accepted":
                                System.out.println(cellWrite);
                                cellWrite.append("!B").append(numb);
                                break;
                            case "Send":
                                cellWrite.append("!D").append(numb);
                                break;
                            case "Rejected":
                                cellWrite.append("!F").append(numb);
                                break;
                        }
                    }
                }
            }
        }
        String[] a = new String[]{value};
        List<List<Object>> valuesWr = Arrays.asList( Arrays.asList(a));

        ValueRange body = new ValueRange().setValues(valuesWr);

        service.spreadsheets().values().update(spreadsheetId, cellWrite.toString(), body).setValueInputOption("USER_ENTERED").execute();
        /*

        CellData setUserEnteredValue = new CellData()
                .setUserEnteredValue(new ExtendedValue()
                        .setStringValue(value));

        CellFormat myFormat = new CellFormat();
        myFormat.setBackgroundColor(new Color().setRed(1)); // red background
        myFormat.setTextFormat(new TextFormat().setFontSize(16)); // 16pt font
        setUserEnteredValue.setUserEnteredFormat(myFormat);

       ArrayList<Request> requesteds = new ArrayList<Request>();
        Request requests = new Request();

        requests.setRepeatCell(new RepeatCellRequest()
                        .setCell(new CellData()
                                .setUserEnteredValue( new ExtendedValue().setStringValue(value))
                                .setUserEnteredFormat(new CellFormat()
                                        .setBackgroundColor(new Color()
                                                .setRed(Float.valueOf("1"))
                                                .setGreen(Float.valueOf("0"))
                                                .setBlue(Float.valueOf("0"))
                                        )
                                        .setTextFormat(new TextFormat()
                                                .setFontSize(15)
                                                .setBold(Boolean.TRUE)
                                        )
                                )
                        )
                        .setRange(new GridRange()
                                .setSheetId(1)
                                .setStartRowIndex(1)
                                .setEndRowIndex(1)
                                .setStartColumnIndex(1)
                                .setEndColumnIndex(1)
                        )
                        .setFields("*")
        );

        requesteds.add(requests);
        BatchUpdateSpreadsheetRequest batchRequests = new BatchUpdateSpreadsheetRequest();
        batchRequests.setRequests(requesteds);
        service.spreadsheets().batchUpdate(spreadsheetId, batchRequests).execute();
 */

    }
}