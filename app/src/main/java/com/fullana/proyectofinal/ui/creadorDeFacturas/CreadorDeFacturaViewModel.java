package com.fullana.proyectofinal.ui.creadorDeFacturas;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.fullana.proyectofinal.Model.Item;
import com.fullana.proyectofinal.R;
import com.fullana.proyectofinal.databinding.RecyclerItemFacturaBinding;
import com.fullana.proyectofinal.utils.CommonUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreadorDeFacturaViewModel extends ViewModel {

    boolean editable = false;
    MutableLiveData<String> client = new MutableLiveData<>();
    MutableLiveData<String> residence = new MutableLiveData<>();
    MutableLiveData<String> city = new MutableLiveData<>();
    MutableLiveData<String> nif = new MutableLiveData<>();
    MutableLiveData<String> comments = new MutableLiveData<>();

    public CreadorDeFacturaViewModel() {
        client.setValue("");
        residence.setValue("");
        city.setValue("");
        nif.setValue("");
        comments.setValue("");
    }

    public void editeFABPressed(FloatingActionButton fab, List<View> views) {
        if (editable) {
            editable = false;
            fab.setImageLevel(0);
            CommonUtils.goneViews(views);
        } else {
            editable = true;
            fab.setImageLevel(1);
            CommonUtils.visibleViews(views);
        }
    }

    /**
     * Abre un diálogo para editar los datos del cliente
     *
     * @param activity
     */
    public void editarDatosCliente(FragmentActivity activity) {

        if (!editable)
            return;

        HashMap<String, String> datos = new HashMap<>(Map.of(
                "cliente", client.getValue(),
                "domicilio", residence.getValue(),
                "ciudad", city.getValue(),
                "nif", nif.getValue(),
                "comentarios", comments.getValue()
        ));
        CommonUtils.datosClienteDialog(activity, datos, new Callback() {
            @Override
            public void onDialogOk(Map<String, String> datos) {
                client.setValue(datos.get("cliente"));
                residence.setValue(datos.get("domicilio"));
                city.setValue(datos.get("ciudad"));
                nif.setValue(datos.get("nif"));
                comments.setValue(datos.get("comentarios"));
            }
        });
    }

    public void createItem(FragmentActivity activity, MutableLiveData<ArrayList<Item>> items) {

        if (!editable)
            return;
        CommonUtils.nuevoItemDialog(activity, new Callback() {
            @Override
            public void onDialogOk(Map<String, String> datos) {
                ArrayList<Item> list = items.getValue();
                list.add(new Item(datos.get("code"), datos.get("article"), Integer.parseInt(datos.get("units")), Float.parseFloat(datos.get("price"))));
                items.setValue(list);
            }
        });
    }

    static class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

        MutableLiveData<ArrayList<Item>> items = new MutableLiveData<>();

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycler_item_factura, parent, false);

            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {

            inflateHolder(holder, items.getValue().get(position));
        }

        private void inflateHolder(@NonNull ItemViewHolder holder, Item item) {

            holder.binding.article.setText(item.getArticle());
            holder.binding.code.setText(item.getCode());
            holder.binding.price.setText(CommonUtils.formatFloat(item.getPrice()));
            holder.binding.quantity.setText(String.valueOf(item.getUnits()));
            holder.binding.iva.setText(CommonUtils.formatFloat((float) (item.getPrice() * item.getUnits() * 0.21)));
            holder.binding.total.setText(CommonUtils.formatFloat(item.getPrice() * item.getUnits()));
        }

        @Override
        public int getItemCount() {
            return items.getValue().size();
        }

        static class ItemViewHolder extends RecyclerView.ViewHolder {
            RecyclerItemFacturaBinding binding;

            public ItemViewHolder(View itemView) {
                super(itemView);
                binding = RecyclerItemFacturaBinding.bind(itemView);
            }
        }
    }

    public interface Callback {
        void onDialogOk(Map<String, String> datos);
    }
}
