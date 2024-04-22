package com.fullana.proyectofinal.ui.deliveryNote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fullana.proyectofinal.databinding.FragmentDeliveryNoteBinding;

import java.util.concurrent.CompletableFuture;

public class DeliveryNote extends Fragment {

    private FragmentDeliveryNoteBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DeliveryNoteViewModel galleryViewModel = new ViewModelProvider(this).get(DeliveryNoteViewModel.class);


        binding = FragmentDeliveryNoteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.button3.setOnClickListener((l) -> CompletableFuture.runAsync(() -> {

        }).join());
        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}