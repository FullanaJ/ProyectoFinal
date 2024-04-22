package com.fullana.proyectofinal.model.bill;

import android.app.Activity;

import com.fullana.proyectofinal.GlobalConfiguration;
import com.fullana.proyectofinal.R;
import com.fullana.proyectofinal.model.Client;
import com.fullana.proyectofinal.model.Company;
import com.fullana.proyectofinal.model.Item;
import com.fullana.proyectofinal.utils.CommonUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@ToString
@Getter
@Setter
@EqualsAndHashCode
public class Bill extends IDBill implements Serializable {

    private Date date;
    private String number;
    private Company company;
    private Client client;
    private String comment;
    private List<Item> items;
    private Integer IVA;

    public static final int
            STANDARD = 21,
            REDUCED = 10,
            SUPER_REDUCED = 4;

    public Bill(int imageId, String googleId, int maxItemsForBill, com.fullana.proyectofinal.model.bill.IDBill.Instructions instructions, Date date, String number, Company company, Client client, String comment, List<Item> items, Integer IVA) {
        super(imageId, googleId, maxItemsForBill, instructions);
        this.date = date;
        this.number = number;
        this.company = company;
        this.client = client;
        this.comment = comment;
        this.items = items;
        this.IVA = IVA;
    }

    public Bill(IDBill idBill, Date date, String number, Company company, Client client, String comment, List<Item> items, Integer IVA) {
        super(idBill.getImageId(), idBill.getGoogleId(), idBill.getMaxItemsForBill(), idBill.getInstructions());
        this.date = date;
        this.number = number;
        this.company = company;
        this.client = client;
        this.comment = comment;
        this.items = items;
        this.IVA = IVA;

    }

    public void build(Activity activity) throws IOException {
        CommonUtils.addBillToFile(
                instructions.createBill(this, activity),
                GlobalConfiguration.BILLS_SAVED
        );

    }

    public String generateName() {
        return client.getName() + "_" + CommonUtils.stringDateFromDate(date) + "_" + number;
    }

    public int getManyBills(Bill bill) {
        int times = bill.items.size() / bill.getMaxItemsForBill();
        if (bill.items.size() % bill.getMaxItemsForBill() != 0) times++;
        return times;
    }

    public Boolean hasRequiredAttributes() {
        return date != null && number != null && company != null && client != null && IVA != null;
    }

    public String getRequiredAttributesError(Activity activity) {
        String x = activity.getResources().getString(R.string.error_required_attributes) + ": " +
                (date == null ? activity.getResources().getString(R.string.date) + ", " : "") +
                (number == null ? activity.getResources().getString(R.string.number) + ", " : "") +
                (IVA == null ? activity.getResources().getString(R.string.iva) + ", " : "");
        return x.substring(0, x.length() - 2) + ".";
    }
    public static Bill generateVoidBill(){
        return new Bill(0, "", 0, null, new Date(), "", new Company(), new Client(), "", List.of(), 0);
    }
}
