package com.fullana.proyectofinal.utils;

import static com.fullana.proyectofinal.GlobalConfiguration.REDIRECT;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.fullana.proyectofinal.MainActivity;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;

import net.openid.appauth.AuthorizationException;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ResponseTypeValues;
import net.openid.appauth.TokenResponse;

import java.util.List;


public class OpenIDUtils {

    public static Uri URI_GOOGLE_OAUTH2 = Uri.parse("https://accounts.google.com/o/oauth2/auth");
    public static Uri URI_GOOGLE_TOKEN = Uri.parse("https://oauth2.googleapis.com/token");
    public static final String CLIENT_ID = "";
    public static String CLIENT_TOKEN;
    public static final List<String> SCOPES = List.of(
            "https://www.googleapis.com/auth/drive",
            "https://www.googleapis.com/auth/drive.file",
            "https://www.googleapis.com/auth/drive.readonly",
            "https://www.googleapis.com/auth/drive.photos.readonly",
            "https://www.googleapis.com/auth/drive.appdata",
            "https://www.googleapis.com/auth/drive.apps.readonly",
            "https://www.googleapis.com/auth/spreadsheets",
            "https://www.googleapis.com/auth/spreadsheets.readonly",
            "https://www.googleapis.com/auth/docs"
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
                (resp1, ex) -> {
                    if (resp1 != null) {
                        CLIENT_TOKEN = resp1.accessToken;
                        activity.startActivity(new Intent(activity, MainActivity.class));
                    } else
                        CommonUtilsDialog.defaultDialog(activity, "Error", "Algo no a ocurrido bien");
                });
    }

    public static void doAuthorization(Activity activity, AuthorizationRequest authRequest) {
        AuthorizationService authService = new AuthorizationService(activity);
        Intent authIntent = authService.getAuthorizationRequestIntent(authRequest);
        activity.startActivityForResult(authIntent, RC_SIGN_IN);
    }
}
