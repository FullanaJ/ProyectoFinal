package com.fullana.proyectofinal.model;

import android.app.Activity;

import com.fullana.proyectofinal.GlobalConfiguration;
import com.fullana.proyectofinal.R;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Company {

    private String name;
    private String nameCeo;
    private String nie;
    private String address;
    private String city;
    private String postalCode;
    private String phone;
    private String email;

    public static Company loadCompany() {
        return new Company(
                GlobalConfiguration.COMPANY_NAME,
                GlobalConfiguration.COMPANY_CEO,
                GlobalConfiguration.COMPANY_NIE,
                GlobalConfiguration.COMPANY_ADDRESS,
                GlobalConfiguration.COMPANY_CITY,
                GlobalConfiguration.COMPANY_POSTAL_CODE,
                GlobalConfiguration.COMPANY_PHONE,
                GlobalConfiguration.COMPANY_EMAIL
        );
    }

    public Boolean hasRequiredData() {
        return name != null && !name.isEmpty() &&
                nameCeo != null && !nameCeo.isEmpty() &&
                nie != null && !nie.isEmpty() &&
                address != null && !address.isEmpty() &&
                city != null && !city.isEmpty() &&
                postalCode != null && !postalCode.isEmpty();
//                && phone != null && !phone.isEmpty() &&
//                email != null && !email.isEmpty();
    }

    public String getRequiredDataError(Activity activity) {
        String x = activity.getString(R.string.error_required_attributes) + " " +
                activity.getString(R.string.on) + " " +
                activity.getString(R.string.company) + ": " +
                (name == null || name.isEmpty() ? activity.getString(R.string.name) + ", " : "") +
                (nameCeo == null || nameCeo.isEmpty() ? activity.getString(R.string.ceo) + ", " : "") +
                (nie == null || nie.isEmpty() ? activity.getString(R.string.nif) + ", " : "") +
                (address == null || address.isEmpty() ? activity.getString(R.string.address) + ", " : "") +
                (city == null || city.isEmpty() ? activity.getString(R.string.city) + ", " : "") +
                (postalCode == null || postalCode.isEmpty() ? activity.getString(R.string.postal_code) + ", " : "");
//                (phone == null || phone.isEmpty() ? activity.getString(R.string.phone) + ", " : "") +
//                (email == null || email.isEmpty() ? activity.getString(R.string.email) + ", " : "");
        return x.substring(0, x.length() - 2) + ".";
    }
}
