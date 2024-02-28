package com.fullana.proyectofinal.ui.creadorDeFacturas;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fullana.proyectofinal.databinding.FragmentCreadorDeFacturaBinding;

import java.io.File;
import java.io.FileOutputStream;


public class CreadorDeFactura extends Fragment {


    FragmentCreadorDeFacturaBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    void crearImagen() {
        // Obtén una referencia al fragmento
        CreadorDeFactura fragment = this;
        View fragmentView = fragment.getView();

        Bitmap bitmap = Bitmap.createBitmap(fragmentView.getWidth(), fragmentView.getHeight(), Bitmap.Config.ARGB_8888);

// Crea un lienzo para dibujar el bitmap
        Canvas canvas = new Canvas(bitmap);

// Renderiza la vista del fragmento en el bitmap
        fragmentView.draw(canvas);

// Define el nombre y la ruta de archivo para guardar el bitmap
        String filename = "mi_imagen.png"; // o "mi_imagen.jpg" si deseas guardar como JPG
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(storageDir, filename);

// Guarda el bitmap como una imagen en el almacenamiento externo
        try {
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream); // o CompressFormat.JPEG si deseas guardar como JPG
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreadorDeFacturaBinding.inflate(inflater, container, false);
        binding.fab.setOnClickListener((t)->{
            crearImagen();
        });
        return binding.getRoot();
    }
}