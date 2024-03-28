package com.fullana.proyectofinal.ui.home;


import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fullana.proyectofinal.Model.Factura;
import com.fullana.proyectofinal.R;
import com.fullana.proyectofinal.databinding.FragmentFacturasBinding;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FacturasFragment extends Fragment {

    Path path =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toPath();
    private FragmentFacturasBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.recyclerView.setAdapter(new FacturaAdapter());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.fab.setOnClickListener((t)->{
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.creadorDeFactura);
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        FacturasViewModel homeViewModel = new ViewModelProvider(this).get(FacturasViewModel.class);

        binding = FragmentFacturasBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
class FacturaAdapter extends RecyclerView.Adapter<FacturaAdapter.ViewHolder> {

    ArrayList<Factura> facturas = new ArrayList<>(List.of(
            new Factura("Factura 1", "12/12/2021")));

    ;
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titulo,fecha,peso;
        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imagenFactura);
            titulo = view.findViewById(R.id.titulo);
            fecha = view.findViewById(R.id.fecha);
            peso = view.findViewById(R.id.peso);
        }
    }
    @NonNull
    @Override
    public FacturaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_facturas,parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacturaAdapter.ViewHolder holder, int position) {
        Factura factura = facturas.get(position);
        holder.fecha.setText("12/12/2021");
        holder.titulo.setText("Factura 1");
        holder.peso.setText("500Kb");
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "mi_imagen.png");
        System.out.println(file.getAbsolutePath())  ;
        System.out.println(file.exists());
        holder.imageView.setImageURI(Uri.fromFile(file));
    }

    @Override
    public int getItemCount() {
        return facturas.size();
    }
}