package com.fullana.proyectofinal.ui.gallery;

import static com.fullana.proyectofinal.utils.OpenIDUtils.CLIENT_TOKEN;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fullana.proyectofinal.databinding.FragmentAlbaranBinding;
import com.fullana.proyectofinal.utils.CommonUtilsSpreadsheet;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class AlbaranFragment extends Fragment {

    private FragmentAlbaranBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AlbaranViewModel galleryViewModel = new ViewModelProvider(this).get(AlbaranViewModel.class);


        binding = FragmentAlbaranBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        binding.button3.setOnClickListener((l) -> CompletableFuture.runAsync(() -> {
                    try {
                        CommonUtilsSpreadsheet.getSheet(CLIENT_TOKEN)
                                .spreadsheets()
                                .values()
                                .get("1Wpam2m0XBGudAVu0Z_AxUyGuXPVKQE4JWW79_eb9BHQ", "A1:Z")
                                .execute().getValues().forEach(
                                        (o) -> {
                                            if (o != null)
                                                o.forEach((j) -> {
                                                    if (j != null)
                                                        System.out.println("VALUE " + j);
                                                });
                                        }
                                );
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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