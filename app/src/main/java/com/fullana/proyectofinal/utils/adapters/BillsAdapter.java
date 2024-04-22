package com.fullana.proyectofinal.utils.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fullana.proyectofinal.GlobalConfiguration;
import com.fullana.proyectofinal.model.bill.Bill;
import com.fullana.proyectofinal.R;
import com.fullana.proyectofinal.model.bill.FinalBill;
import com.fullana.proyectofinal.utils.CommonUtils;

import java.util.List;

public class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.ViewHolder> {

    List<FinalBill> bills;
    public BillsAdapter() {
            bills = CommonUtils.readFile(GlobalConfiguration.BILLS_SAVED,List.class);
        if (bills == null)
            bills =  List.of();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, date, weight;

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imagenFactura);
            title = view.findViewById(R.id.titulo);
            date = view.findViewById(R.id.fecha);
            weight = view.findViewById(R.id.peso);
        }
    }

    @NonNull
    @Override
    public BillsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_bill, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillsAdapter.ViewHolder holder, int position) {
        Bill bill = bills.get(position);
        holder.date.setText(CommonUtils.stringDateFromDate(bill.getDate()));
        holder.title.setText(bill.generateName());
        holder.weight.setText("500Kb");
//        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "mi_imagen.png");
//        holder.imageView.setImageURI(Uri.fromFile(file));
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }
}