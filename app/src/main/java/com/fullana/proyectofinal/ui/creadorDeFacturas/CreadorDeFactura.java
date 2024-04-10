package com.fullana.proyectofinal.ui.creadorDeFacturas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fullana.proyectofinal.databinding.FragmentCreadorDeFacturaBinding;
import com.fullana.proyectofinal.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;


public class CreadorDeFactura extends Fragment {


    FragmentCreadorDeFacturaBinding binding;
    CreadorDeFacturaViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CreadorDeFacturaViewModel.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreadorDeFacturaBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        setOnClicks();
        setMutableDataObserver();
//        viewModel.createRecycler(binding.recyclerView, getActivity());
        return binding.getRoot();
    }

    private void setOnClicks() {

        useAdapter();
        binding.save.setOnClickListener((t) -> {
            viewModel.createFactura(getActivity());
            CommonUtils.defaultDialog(getActivity(), "Imagen guardada", "La imagen se ha guardado en la galería");
        });
        binding.edite.setOnClickListener((t) -> {
            viewModel.editeFABPressed(binding.edite, List.of(binding.button));
        });
        binding.linearLayoutDatosCliente.setOnClickListener((l) -> {
            viewModel.editarDatosCliente(getActivity());
        });
    }

    private void useAdapter() {
        CreadorDeFacturaViewModel.ItemAdapter adapter = new CreadorDeFacturaViewModel.ItemAdapter();
        adapter.items.setValue(new ArrayList<>());
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        adapter.items.observe(getViewLifecycleOwner(), (t) -> {
            adapter.notifyItemChanged(0);
        });

        binding.button.setOnClickListener((t) -> viewModel.createItem(getActivity(), adapter.items));
    }

    //functions
    public void setMutableDataObserver() {
        LifecycleOwner owner = getViewLifecycleOwner();
        modifyView(owner, binding.nameClient, viewModel.client);
        modifyView(owner, binding.residenceClient, viewModel.residence);
        modifyView(owner, binding.cityClient, viewModel.city);
        modifyView(owner, binding.nifClient, viewModel.nif);

    }

    public void modifyView(LifecycleOwner owner, View view, MutableLiveData<String> data) {
        data.observe(owner, (t) -> {
            if (view instanceof TextView)
                ((TextView) view).setText(t);

        });
    }
}