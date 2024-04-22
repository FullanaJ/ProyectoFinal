package com.fullana.proyectofinal.ui.createBill;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.Button;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fullana.proyectofinal.GlobalConfiguration;
import com.fullana.proyectofinal.R;
import com.fullana.proyectofinal.model.Client;
import com.fullana.proyectofinal.model.Company;
import com.fullana.proyectofinal.model.Item;
import com.fullana.proyectofinal.model.bill.Bill;
import com.fullana.proyectofinal.model.bill.IDBill;
import com.fullana.proyectofinal.utils.CommonUtils;
import com.fullana.proyectofinal.utils.CommonUtilsDialog;
import com.fullana.proyectofinal.utils.adapters.ItemAdapter;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


public class CreateBillViewModel extends ViewModel {

    final AtomicBoolean cliCollapsed = new AtomicBoolean(false);
    final AtomicBoolean comCollapsed = new AtomicBoolean(false);
    final AtomicBoolean extCollapsed = new AtomicBoolean(false);
    final AtomicBoolean itemCollapsed = new AtomicBoolean(false);
    final MutableLiveData<Client> client = new MutableLiveData<>();
    final MutableLiveData<Company> company = new MutableLiveData<>();
    final MutableLiveData<Integer> iva = new MutableLiveData<>();
    final MutableLiveData<String> comments = new MutableLiveData<>();
    final MutableLiveData<String> billNumber = new MutableLiveData<>();
    final MutableLiveData<String> date = new MutableLiveData<>();
    final MutableLiveData<List<Item>> items = new MutableLiveData<>();
    private IDBill idBill;
    private ItemAdapter itemAdapter;

    public ItemAdapter instanceAttributesViewModel(IDBill idBill) {
        if (company.getValue() == null)
            company.setValue(Company.loadCompany());
        if (comments.getValue() == null)
            comments.setValue("");
        if (iva.getValue() == null)
            iva.setValue(GlobalConfiguration.IVA);
        if (billNumber.getValue() == null)
            billNumber.setValue(GlobalConfiguration.BILL_COUNT.toString());
        if (date.getValue() == null)
            date.setValue(CommonUtils.stringDateFromDate(new Date()));
        if (items.getValue() == null)
            items.setValue(new ArrayList<>());
        this.idBill = idBill;
        itemAdapter = new ItemAdapter(items);
        return itemAdapter;
    }


    /**
     * Abre un diálogo para editar los datos del cliente
     *
     * @param activity
     */
    public void editeClientData(Activity activity) {
        Client c = client.getValue();
        Callback callback = data -> {
            client.setValue(
                    new Client(
                            data.get("client"),
                            data.get("address"),
                            data.get("city"),
                            data.get("nif")
                    )
            );
            comments.setValue(data.get("comment"));
        };
        if (c != null) {
            HashMap<String, String> data = new HashMap<>(Map.of(
                    "client", c.getName(),
                    "address", c.getAddress(),
                    "city", c.getCity(),
                    "nif", c.getNif(),
                    "comment", comments.getValue()
            ));
            CommonUtilsDialog.editClientDataDialog(activity, data, callback);
        } else
            CommonUtilsDialog.newClientDataDialog(activity, callback);
    }

    public void editeCompanyData(Context context) {
        Company c = company.getValue();
        HashMap<String, String> data = new HashMap<>(Map.of(
                "companyName", c.getName(),
                "ceo", c.getNameCeo(),
                "address", c.getAddress(),
                "city", c.getCity(),
                "nif", c.getNie(),
                "postalCode", c.getPostalCode()
//                "phone", c.getPhone(),
//                "email", c.getEmail()

        ));
        CommonUtilsDialog.companyDataDialog(context, data, dataCallback ->
                company.setValue(
                        new Company(
                                dataCallback.get("companyName"),
                                dataCallback.get("ceo"),
                                dataCallback.get("nif"),
                                dataCallback.get("address"),
                                dataCallback.get("city"),
                                dataCallback.get("postalCode"),
                                dataCallback.get("phone"),
                                dataCallback.get("email")
                        )
                )
        );
    }


    public void createItem(Activity activity) {
        CommonUtilsDialog.newItemDialog(activity, dataCallback -> {
            items.getValue().add(0,
                    new Item(
                            dataCallback.get("code"),
                            dataCallback.get("article"),
                            Integer.parseInt(dataCallback.get("units")),
                            new BigDecimal(dataCallback.get("price"))
                    )
            );
            itemAdapter.notifyItemChanged(0);
        });
    }

    public void createBill(Activity activity) {
        if (!items.getValue().isEmpty()) {
            Bill bill;
            try {
                bill = new Bill(
                        idBill,
                        CommonUtils.datefromStringDate(date.getValue()),
                        billNumber.getValue(),
                        company.getValue(),
                        client.getValue(),
                        comments.getValue(),
                        items.getValue(),
                        iva.getValue()
                );
                if (bill.getCompany().hasRequiredData())
                    if (bill.getClient().hasRequiredData())
                        if (bill.hasRequiredAttributes())
                            bill.build(activity);
                        else
                            CommonUtilsDialog.defaultDialog(activity, activity.getResources().getString(R.string.error), bill.getRequiredAttributesError(activity));
                    else
                        CommonUtilsDialog.defaultDialog(activity, activity.getResources().getString(R.string.error), bill.getClient().getRequiredDataError(activity));
                else
                    CommonUtilsDialog.defaultDialog(activity, activity.getResources().getString(R.string.error), bill.getCompany().getRequiredDataError(activity));
            } catch (IOException e) {
                CommonUtilsDialog.defaultDialog(activity, activity.getResources().getString(R.string.error), activity.getResources().getString(R.string.error_connection));
            } catch (ParseException ignore) {
            }
        } else
            CommonUtilsDialog.defaultDialog(activity, activity.getResources().getString(R.string.error), activity.getResources().getString(R.string.error_no_items));
    }

    public void animateLayout(View view, AtomicBoolean collapsed, Button button, Resources resources) {
        Boolean b;
        if (b = collapsed.get()) {
            CommonUtils.expand(view);
            button.setText(resources.getText(R.string.collapse));
        } else {
            collapse(view);
            button.setText(resources.getText(R.string.show));
        }
        collapsed.set(!b);
    }

    public void collapse(View view) {
        CommonUtils.collapse(view, 85);
    }


    public interface Callback {
        void onDialogOk(Map<String, String> data);
    }
}
