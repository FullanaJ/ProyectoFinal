package com.fullana.proyectofinal.utils.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.fullana.proyectofinal.R;
import com.fullana.proyectofinal.databinding.RecyclerItemBillBinding;
import com.fullana.proyectofinal.model.Item;
import com.fullana.proyectofinal.utils.CommonUtils;
import com.fullana.proyectofinal.utils.CommonUtilsDialog;

import java.math.BigDecimal;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    final MutableLiveData<List<Item>> items;

    public ItemAdapter(MutableLiveData<List<Item>> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_bill, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        inflateHolder(holder, items.getValue().get(position), position);
    }

    private void inflateHolder(@NonNull ItemViewHolder holder, Item item, int position) {

        holder.binding.article.setText(item.getArticle());
        holder.binding.code.setText(item.getCode());
        holder.binding.price.setText(CommonUtils.formatFloat(item.getPrice().floatValue()));
        holder.binding.quantity.setText(String.valueOf(item.getUnits()));
        holder.binding.total.setText(CommonUtils.formatFloat(item.getTotal().floatValue()));
        holder.binding.edit.setOnClickListener(v ->
                CommonUtilsDialog.editItemDialog((Activity) holder.itemView.getContext(),
                        (data) -> {
                            items.getValue().set(position, new Item(data.get("code"),
                                    data.get("article"),
                                    Integer.parseInt(data.get("units")),
                                    new BigDecimal(data.get("price"))));
                            notifyItemChanged(position);
                        }, item));
    }

    @Override
    public int getItemCount() {
        return items.getValue().size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        RecyclerItemBillBinding binding;

        public ItemViewHolder(View itemView) {
            super(itemView);
            binding = RecyclerItemBillBinding.bind(itemView);
        }
    }
}
