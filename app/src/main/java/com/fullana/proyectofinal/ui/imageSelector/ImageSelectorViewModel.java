package com.fullana.proyectofinal.ui.imageSelector;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.fullana.proyectofinal.GlobalConfiguration;
import com.fullana.proyectofinal.R;
import com.fullana.proyectofinal.model.ID;
import com.fullana.proyectofinal.utils.CommonUtils;

import java.util.List;

public class ImageSelectorViewModel extends ViewModel {


    private List<ID> images_id;
    private final MutableLiveData<Integer> actualImage = new MutableLiveData<>();
    public static final int LOAD_BILLS = 1;
    public static final int LOAD_DELIVERY_NOTES = 2;
    private int loaded;

    public void initializeSwitcher(int load, ImageSwitcher imageSwitcher, Context context, LifecycleOwner lifecycleOwner) {
        loaded = load;
        switch (load) {
            case LOAD_BILLS:
                loadBills();
                break;
            case LOAD_DELIVERY_NOTES:
                loadDeliveryNotes();
                break;
        }
        imageSwitcher.setFactory(() -> new ImageView(context));
        actualImage.observe(lifecycleOwner, (integer -> imageSwitcher.setImageResource(images_id.get(integer).getImageId())));
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right));
        if (!images_id.isEmpty())
            actualImage.setValue(0);
    }

    public void loadBills() {
        images_id = GlobalConfiguration.BILLS_ID;
    }

    public void loadDeliveryNotes() {
        images_id = GlobalConfiguration.DELIVERY_NOTES_ID;
    }

    public void nextPage() {
        if (actualImage.getValue() < images_id.size() - 1)
            actualImage.setValue(actualImage.getValue() + 1);
    }

    public void beforePage() {
        if (actualImage.getValue() > 0)
            actualImage.setValue(actualImage.getValue() - 1);
    }

    public void startActivity(Activity activity) {
        int id;
        switch (loaded) {
            case LOAD_BILLS:
                id = R.id.nav_createBill;
                break;
//            case LOAD_DELIVERY_NOTES:
//                id = R.id.nav_delivery_note;
//                break;
            default:
                id = R.id.nav_createBill;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("id", images_id.get(actualImage.getValue()));
        CommonUtils.navigate(activity,bundle,id);
    }
}
