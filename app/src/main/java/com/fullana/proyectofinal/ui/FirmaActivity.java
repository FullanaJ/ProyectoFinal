package com.fullana.proyectofinal.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fullana.proyectofinal.databinding.FirmaActivityBinding;

import java.io.IOException;
import java.io.OutputStream;

public class FirmaActivity extends AppCompatActivity {

    FirmaActivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FirmaActivityBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        binding.viewDraw.setVisibility(View.VISIBLE);
        binding.viewDraw.setDrawingCacheEnabled(true);
        binding.viewDraw.setEnabled(true);
        binding.viewDraw.invalidate();

        binding.borrar.setOnClickListener(v -> binding.viewDraw.borrarFirma());

        binding.dibujar.setOnClickListener(v -> {

            try (OutputStream fOut = openFileOutput("firma.PNG", Context.MODE_PRIVATE)){
                binding.viewDraw.getCanvasBitmap().compress(Bitmap.CompressFormat.PNG, 100, fOut);
                Toast.makeText(this,"Firam guardada",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this,"Algo a fallado",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
