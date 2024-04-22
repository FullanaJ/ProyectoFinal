package com.fullana.proyectofinal.ui.imageSelector;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.fullana.proyectofinal.databinding.FragmentImageSelectorBinding;


public class ImageSelector extends Fragment {

    FragmentImageSelectorBinding binding;
    ImageSelectorViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ImageSelectorViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        binding = FragmentImageSelectorBinding.inflate(inflater, container, false);
        int load = ImageSelectorViewModel.LOAD_BILLS;
        Bundle bundle = getArguments();
        if (bundle != null)
            load = bundle.getInt("load");

        binding.fabBefore.setOnClickListener((l) -> viewModel.beforePage());
        binding.fabNext.setOnClickListener((l) -> viewModel.nextPage());
        viewModel.initializeSwitcher(load, binding.imageSwitcher, getContext(), getViewLifecycleOwner());
        binding.select.setOnClickListener((l) -> viewModel.startActivity(getActivity()));


        return binding.getRoot();
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        binding.linearProgressIndicator.setProgressCompat(35,true);
    }

}