package com.fullana.proyectofinal;

import static com.fullana.proyectofinal.utils.OpenIDUtils.RC_SIGN_IN;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fullana.proyectofinal.databinding.LoginActivityBinding;
import com.fullana.proyectofinal.utils.OpenIDUtils;

import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;

public class LoginActivity extends AppCompatActivity {

    LoginActivityBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signIn.setOnClickListener((l) -> {
//            CommonUtilsApi.googleAccount(this);
            OpenIDUtils.doAuthorization(this, OpenIDUtils.createAuthorizationRequest());
        });
        binding.button2.setOnClickListener((l) -> {
//            CommonUtilsApi.basicGoogleSignIn(this);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN && data != null) {
            AuthorizationService authService = new AuthorizationService(this);
            AuthorizationResponse resp = AuthorizationResponse.fromIntent(data);
            OpenIDUtils.getToken(this, resp);
        } else if (requestCode == 10) {
            System.out.println();
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        CommonUtilsManager.initialize(this);
    }
}
