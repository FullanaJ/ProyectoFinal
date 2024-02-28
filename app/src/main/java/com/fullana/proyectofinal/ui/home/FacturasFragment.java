package com.fullana.proyectofinal.ui.home;


import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.fullana.proyectofinal.R;
import com.fullana.proyectofinal.databinding.FragmentFacturasBinding;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FacturasFragment extends Fragment {

    Path path =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toPath();
    private FragmentFacturasBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        FacturasViewModel homeViewModel =
                new ViewModelProvider(this).get(FacturasViewModel.class);

        binding = FragmentFacturasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        homeViewModel.getText();
        binding.fab.setOnClickListener((t)->{
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.creadorDeFactura);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}