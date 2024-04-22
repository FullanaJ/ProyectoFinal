package com.fullana.proyectofinal.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fullana.proyectofinal.R;
import com.fullana.proyectofinal.model.Item;
import com.fullana.proyectofinal.ui.createBill.CreateBillViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class CommonUtilsDialog {
    /**
     * Default dialog
     *
     * @param context
     * @param titleT
     * @param messageT
     */

    public static void defaultDialog(Context context, String titleT, String messageT) {

        Dialog dialog = new Dialog(context);
        com.fullana.proyectofinal.databinding.DialogAlertBinding binding = com.fullana.proyectofinal.databinding.DialogAlertBinding.inflate(LayoutInflater.from(context));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(binding.getRoot());

        binding.titleDialog.setText(titleT);
        binding.textDialog.setText(messageT);
        binding.okDialog.setOnClickListener((t) -> dialog.dismiss());
        dialog.show();
    }

    /**
     * Dialog to create a new item
     *
     * @param context /
     * @param callback
     */
    public static void newItemDialog(Context context, CreateBillViewModel.Callback callback) {
        editItemDialog(context, callback, null);
    }

    public static void editItemDialog(Context context, CreateBillViewModel.Callback callback, Item item) {
        Dialog dialog = new Dialog(context);
        com.fullana.proyectofinal.databinding.DialogItemBinding binding = com.fullana.proyectofinal.databinding.DialogItemBinding.inflate(LayoutInflater.from(context));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (item != null) {
            binding.codeDialog.setText(item.getCode());
            binding.articleDialog.setText(item.getArticle());
            binding.unitsDialog.setText(String.valueOf(item.getUnits()));
            binding.priceDialog.setText(item.getPrice().toString());
        }
        binding.codeDialog.setOnFocusChangeListener((v, hasFocus) ->
                checkTextIsNotVoid(hasFocus, binding.codeDialog, context.getResources()));
        binding.articleDialog.setOnFocusChangeListener((v, hasFocus) ->
                checkTextIsNotVoid(hasFocus, binding.articleDialog, context.getResources()));
        binding.unitsDialog.setOnFocusChangeListener((v, hasFocus) ->
                checkTextIsNotVoid(hasFocus, binding.unitsDialog, context.getResources()));
        binding.priceDialog.setOnFocusChangeListener((v, hasFocus) ->
                checkTextIsNotVoid(hasFocus, binding.priceDialog, context.getResources()));


        TextView ok = dialog.findViewById(R.id.okDialog);
        Map<String, String> finalData = new HashMap<>();
        ok.setOnClickListener((t) -> {
            if (binding.codeDialog.getText().toString().isEmpty() || binding.articleDialog.getText().toString().isEmpty() || binding.unitsDialog.getText().toString().isEmpty() || binding.priceDialog.getText().toString().isEmpty()) {
                CommonUtils.showToast(context, context.getResources().getString(R.string.fill_out_all_the_fields));
            } else {
                finalData.put("code", binding.codeDialog.getText().toString());
                finalData.put("article", binding.articleDialog.getText().toString());
                finalData.put("units", binding.unitsDialog.getText().toString());
                finalData.put("price", binding.priceDialog.getText().toString());
                callback.onDialogOk(finalData);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * Dialog to edit client data
     *
     * @param context
     * @param callback
     */
    public static void newClientDataDialog(Context context, CreateBillViewModel.Callback callback) {
        editClientDataDialog(context, null, callback);
    }

    public static void editClientDataDialog(Context context, Map<String, String> data, CreateBillViewModel.Callback callback) {
        Dialog dialog = new Dialog(context);
        com.fullana.proyectofinal.databinding.DialogDataClientBinding binding = com.fullana.proyectofinal.databinding.DialogDataClientBinding.inflate(LayoutInflater.from(context));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (data != null) {
            binding.clientNameDialog.setText(data.get("client"));
            binding.residenceDialog.setText(data.get("address"));
            binding.cityDialog.setText(data.get("city"));
            binding.nifDialog.setText(data.get("nif"));
            binding.commentDialog.setText(data.get("comment"));
        } else
            data = new HashMap<>();

        Map<String, String> finalData = data;
        binding.okDialog.setOnClickListener((t) -> {
            if (binding.clientNameDialog.getText().toString().isEmpty() || binding.residenceDialog.getText().toString().isEmpty() || binding.cityDialog.getText().toString().isEmpty() || binding.nifDialog.getText().toString().isEmpty()) {
                CommonUtils.showToast(context, context.getResources().getString(R.string.fill_out_all_the_fields));
            } else {
                finalData.put("client", binding.clientNameDialog.getText().toString());
                finalData.put("address", binding.residenceDialog.getText().toString());
                finalData.put("city", binding.cityDialog.getText().toString());
                finalData.put("nif", binding.nifDialog.getText().toString());
                finalData.put("comment", binding.commentDialog.getText().toString());
                callback.onDialogOk(finalData);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void companyDataDialog(Context context, Map<String, String> data, CreateBillViewModel.Callback callback) {

        Dialog dialog = new Dialog(context);
        com.fullana.proyectofinal.databinding.DialogCompanyBinding binding = com.fullana.proyectofinal.databinding.DialogCompanyBinding.inflate(LayoutInflater.from(context));
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        binding.companyNameDialog.setText(data.get("companyName"));
        binding.residenceDialog.setText(data.get("address"));
        binding.cityDialog.setText(data.get("city"));
        binding.nifDialog.setText(data.get("nif"));
        binding.ceoDialog.setText(data.get("ceo"));
        binding.postalCodeDialog.setText(data.get("postalCode"));


        binding.okDialog.setOnClickListener((t) -> {
            if (binding.companyNameDialog.getText().toString().isEmpty() || binding.residenceDialog.getText().toString().isEmpty() || binding.cityDialog.getText().toString().isEmpty() || binding.nifDialog.getText().toString().isEmpty()) {
                CommonUtils.showToast(context, context.getResources().getString(R.string.fill_out_all_the_fields));
            } else {
                data.put("companyName", binding.companyNameDialog.getText().toString());
                data.put("address", binding.residenceDialog.getText().toString());
                data.put("city", binding.cityDialog.getText().toString());
                data.put("nif", binding.nifDialog.getText().toString());
                data.put("ceo", binding.ceoDialog.getText().toString());
                data.put("postalCode", binding.postalCodeDialog.getText().toString());
                callback.onDialogOk(data);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private static void checkTextIsNotVoid(boolean hasFocus, TextInputEditText code, Resources resources) {
        if (!hasFocus && code.getText().toString().isEmpty()) {
            code.setError(resources.getText(R.string.empty_field_error));
        }
    }
}
