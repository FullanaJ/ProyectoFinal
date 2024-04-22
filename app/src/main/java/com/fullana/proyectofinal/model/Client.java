package com.fullana.proyectofinal.model;

import android.app.Activity;

import com.fullana.proyectofinal.R;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@Data
@NoArgsConstructor
@ToString
public class Client {

    private String name;
    private String address;
    private String city;
    private String nif;

    public static Client voidClient(){
        return new Client("","","","");
    }

    public boolean hasRequiredData() {
        return name != null && !name.isEmpty() &&
                address != null && !address.isEmpty() &&
                city != null && !city.isEmpty() &&
                nif != null && !nif.isEmpty();
    }

    public String getRequiredDataError(Activity activity) {
    String x = activity.getString(R.string.error_required_attributes) + " " +
            activity.getString(R.string.on) + " " +
            activity.getString(R.string.client) + ": " +
            (name == null || name.isEmpty() ? activity.getString(R.string.name) + ", " : "") +
            (address == null || address.isEmpty() ? activity.getString(R.string.address) + ", " : "") +
            (city == null || city.isEmpty() ? activity.getString(R.string.city) + ", " : "") +
            (nif == null || nif.isEmpty() ? activity.getString(R.string.nif) + ", " : "");
    return x.substring(0, x.length() - 2)+".";
    }
}
