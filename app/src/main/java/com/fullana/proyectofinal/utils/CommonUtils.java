package com.fullana.proyectofinal.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.fullana.proyectofinal.R;
import com.fullana.proyectofinal.ui.creadorDeFacturas.CreadorDeFactura;
import com.fullana.proyectofinal.ui.creadorDeFacturas.CreadorDeFacturaViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonUtils {

    /**
     * Show a toast
     *
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Dialog to create a new item
     *
     * @param activity
     * @param callback
     */
    public static void nuevoItemDialog(FragmentActivity activity, CreadorDeFacturaViewModel.Callback callback) {

        HashMap<String, String> datos = new HashMap<>();
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_item);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        TextInputEditText code = dialog.findViewById(R.id.codeDialog);
        TextInputEditText article = dialog.findViewById(R.id.articleDialog);
        TextInputEditText units = dialog.findViewById(R.id.unitsDialog);
        TextInputEditText price = dialog.findViewById(R.id.priceDialog);

        TextInputLayout layoutcode = dialog.findViewById(R.id.codeDialogLayout);
        TextInputLayout layoutarticle = dialog.findViewById(R.id.articleDialogLayout);
        TextInputLayout layoutunits = dialog.findViewById(R.id.unitsDialogLayout);
        TextInputLayout layoutprice = dialog.findViewById(R.id.priceDialogLayout);

        code.setText(datos.get("code"));
        article.setText(datos.get("article"));
        units.setText(datos.get("units"));
        price.setText(datos.get("price"));

        code.setOnFocusChangeListener((v, hasFocus) ->
                checkTextIsNotVoid(hasFocus, code, activity.getResources(), layoutcode));
        article.setOnFocusChangeListener((v, hasFocus) ->
                checkTextIsNotVoid(hasFocus, article, activity.getResources(), layoutarticle));
        units.setOnFocusChangeListener((v, hasFocus) ->
                checkTextIsNotVoid(hasFocus, units, activity.getResources(), layoutunits));
        price.setOnFocusChangeListener((v, hasFocus) ->
                checkTextIsNotVoid(hasFocus, price, activity.getResources(), layoutprice));


        TextView ok = dialog.findViewById(R.id.okDialog);
        ok.setOnClickListener((t) -> {
            if (code.getText().toString().isEmpty() || article.getText().toString().isEmpty() || units.getText().toString().isEmpty() || price.getText().toString().isEmpty()) {
                CommonUtils.showToast(activity, activity.getResources().getString(R.string.rellena_todos_los_campos));
                return;
            } else {
                datos.put("code", code.getText().toString());
                datos.put("article", article.getText().toString());
                datos.put("units", units.getText().toString());
                datos.put("price", price.getText().toString());
                callback.onDialogOk(datos);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private static void checkTextIsNotVoid(boolean hasFocus, TextInputEditText code, Resources resources, TextInputLayout layout) {
        if (!hasFocus && code.getText().toString().isEmpty()) {
            code.setError(resources.getText(R.string.empty_field_error));

        } else {
        }

    }

    /**
     * Dialog to edit client data
     *
     * @param activity
     * @param datos
     * @param callback
     */
    public static void datosClienteDialog(FragmentActivity activity, Map<String, String> datos, CreadorDeFacturaViewModel.Callback callback) {

        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_datos_cliente);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        TextInputEditText client = dialog.findViewById(R.id.clientDialog);
        TextInputEditText residence = dialog.findViewById(R.id.residenceDialog);
        TextInputEditText city = dialog.findViewById(R.id.cityDialog);
        TextInputEditText nif = dialog.findViewById(R.id.nifDialog);
        TextInputEditText comment = dialog.findViewById(R.id.commentDialog);

        client.setText(datos.get("cliente"));
        residence.setText(datos.get("domicilio"));
        city.setText(datos.get("ciudad"));
        nif.setText(datos.get("nif"));
        comment.setText(datos.get("comentarios"));

        TextView ok = dialog.findViewById(R.id.okDialog);
        ok.setOnClickListener((t) -> {
            if (client.getText().toString().isEmpty() || residence.getText().toString().isEmpty() || city.getText().toString().isEmpty() || nif.getText().toString().isEmpty() || comment.getText().toString().isEmpty()) {
                CommonUtils.showToast(activity, activity.getResources().getString(R.string.rellena_todos_los_campos));
                return;
            } else {
                datos.put("cliente", client.getText().toString());
                datos.put("domicilio", residence.getText().toString());
                datos.put("ciudad", city.getText().toString());
                datos.put("nif", nif.getText().toString());
                datos.put("comentarios", comment.getText().toString());
                callback.onDialogOk(datos);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * Default dialog
     *
     * @param activity
     * @param titleT
     * @param messageT
     */
    public static void defaultDialog(Activity activity, String titleT, String messageT) {

        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_alert);
        dialog.getWindow().setDimAmount(0.8f);

        TextView title = dialog.findViewById(R.id.titleDialog);
        title.setText(titleT);
        TextView message = dialog.findViewById(R.id.textDialog);
        message.setText(messageT);
        TextView ok = dialog.findViewById(R.id.okDialog);

        ok.setOnClickListener((t) -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    /**
     * Disable views
     *
     * @param views
     */
    public static void goneViews(List<View> views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * Enable views
     *
     * @param views
     */
    public static void visibleViews(List<View> views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Get external storage file
     *
     * @param activity
     * @return
     */
    public static File getExternalStorageFile(AppCompatActivity activity) {
        return activity.getFilesDir();
    }

    /**
     * Get external storage path
     *
     * @param activity
     * @return
     */
    public static Path getExternalStoragePath(Activity activity) {
        return activity.getFilesDir().toPath();
    }

    public static String formatFloat(float number) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return decimalFormat.format(number);
    }
}

