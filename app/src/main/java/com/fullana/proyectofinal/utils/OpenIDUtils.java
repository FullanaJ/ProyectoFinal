package com.fullana.proyectofinal.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.fullana.proyectofinal.MainActivity;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.SheetsScopes;

import net.openid.appauth.AuthState;
import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ResponseTypeValues;
import net.openid.appauth.TokenResponse;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class OpenIDUtils {

    public static Uri URI_GOOGLE_OAUTH2 = Uri.parse("https://accounts.google.com/o/oauth2/auth");
    public static Uri URI_GOOGLE_TOKEN = Uri.parse("https://oauth2.googleapis.com/token");
    public static String CLIENT_TOKEN;
    public static final String REDIRECT = "com.fullana.proyectofinal:/oauth2callback";
    public static final String APPLICATION_NAME = "Proyecto final";
    public static final String CLIENT_ID = "816271458390-vpsg23deata489nmp8dl5sgckldqr9e5.apps.googleusercontent.com";
    public static final String CLIENT_SECRET = "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";
    public static final List<String> SCOPES = List.of(
            "https://www.googleapis.com/auth/drive",
            "https://www.googleapis.com/auth/drive.readonly",
            "https://www.googleapis.com/auth/drive.file",
            "https://www.googleapis.com/auth/spreadsheets",
            "https://www.googleapis.com/auth/spreadsheets.readonly"
    );
    public static final int RC_SIGN_IN = 123;
    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    public static final JsonFactory JSON_FACTORY = new GsonFactory();
    private static final AuthorizationServiceConfiguration authorizationServiceConfiguration =
            new AuthorizationServiceConfiguration(URI_GOOGLE_OAUTH2, URI_GOOGLE_TOKEN);

    public static AuthorizationRequest createAuthorizationRequest() {

        AuthorizationRequest.Builder authRequestBuilder =
                new AuthorizationRequest.Builder(
                        authorizationServiceConfiguration, // the authorization service configuration
                        CLIENT_ID, // the client ID, typically pre-registered and static
                        ResponseTypeValues.CODE, // the response_type value: we want a code
                        Uri.parse(REDIRECT));

        return authRequestBuilder
                .setScopes(SCOPES)
                .build();
    }

    public static void getToken(Activity activity, AuthorizationResponse resp) {
        AuthorizationService service = new AuthorizationService(activity);
        service.performTokenRequest(
                resp.createTokenExchangeRequest(),
                new AuthorizationService.TokenResponseCallback() {
                    @Override
                    public void onTokenRequestCompleted(
                            TokenResponse resp, AuthorizationException ex) {
                        if (resp != null) {
                            CLIENT_TOKEN = resp.accessToken;
                            activity.startActivity(new Intent(activity, MainActivity.class));
                        } else
                            CommonUtils.defaultDialog(activity, "Error","Algo no a ocurrido bien");
                    }
                });
    }

    public static void doAuthorization(Activity activity, AuthorizationRequest authRequest) {
        AuthorizationService authService = new AuthorizationService(activity);
        Intent authIntent = authService.getAuthorizationRequestIntent(authRequest);
        activity.startActivityForResult(authIntent, RC_SIGN_IN);
    }
}
