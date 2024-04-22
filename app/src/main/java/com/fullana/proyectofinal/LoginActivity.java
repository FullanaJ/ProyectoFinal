package com.fullana.proyectofinal;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static com.fullana.proyectofinal.MainActivity.REQUEST_PERMISSION_CODE;
import static com.fullana.proyectofinal.utils.OpenIDUtils.RC_SIGN_IN;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fullana.proyectofinal.databinding.LoginActivityBinding;
import com.fullana.proyectofinal.utils.CommonUtils;
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
        getNecessaryPermissions();
        binding.signIn.setOnClickListener((l) -> {
//            CommonUtilsApi.googleAccount(this);
            OpenIDUtils.doAuthorization(this, OpenIDUtils.createAuthorizationRequest());
        });
        binding.button2.setOnClickListener((l) -> {
//            CommonUtils.createPDFFile(this, null);
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN && data != null) {
            AuthorizationService authService = new AuthorizationService(this);
            AuthorizationResponse resp = AuthorizationResponse.fromIntent(data);
            assert resp != null;
            OpenIDUtils.getToken(this, resp);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_LONG).show();
                    System.out.println(Environment.getExternalStorageDirectory().canRead());
                    System.out.println(Environment.getExternalStorageDirectory().canWrite());
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void getNecessaryPermissions() {

        onRequestPermissionsResult(REQUEST_PERMISSION_CODE,
                new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE},
                new int[]{PERMISSION_GRANTED, PERMISSION_GRANTED});
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
//        CommonUtilsManager.initialize(this);
    }
}
