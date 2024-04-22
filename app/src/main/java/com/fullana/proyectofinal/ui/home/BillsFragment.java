package com.fullana.proyectofinal.ui.home;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fullana.proyectofinal.databinding.FragmentBillBinding;
import com.fullana.proyectofinal.utils.adapters.BillsAdapter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BillsFragment extends Fragment {

    Path path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toPath();
    private FragmentBillBinding binding;
    private BillsViewModel viewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setAdapter(new BillsAdapter());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.fab.setOnClickListener((t) -> {
            viewModel.navigate(getActivity());
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        viewModel = new ViewModelProvider(this).get(BillsViewModel.class);
        binding = FragmentBillBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
