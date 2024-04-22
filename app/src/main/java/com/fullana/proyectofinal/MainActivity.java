package com.fullana.proyectofinal;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.fullana.proyectofinal.databinding.ActivityMainBinding;
import com.fullana.proyectofinal.utils.CommonUtilsDrive;
import com.fullana.proyectofinal.utils.OpenIDUtils;
import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    public static final int REQUEST_PERMISSION_CODE = 1;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (OpenIDUtils.CLIENT_TOKEN != null) {
            GlobalConfiguration.loadGlobalConfig(getBaseContext());
            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());
            setSupportActionBar(binding.appBarMain.toolbar);
            DrawerLayout drawer = binding.drawerLayout;
            NavigationView navigationView = binding.navView;
            // Passing each menu ID as a set of Ids because each
            // menu should be considered as top level destinations.
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_bills, R.id.nav_delivery_note, R.id.nav_config)
                    .setOpenableLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);
        } else
            startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
//            Bundle bundle = data.getExtras();
            Uri uri = data.getData();
            if (uri != null) {
                CommonUtilsDrive.loadPdfDataOnPDF(uri,this);
                Toast.makeText(getApplicationContext(), "PDF downloaded", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

}
