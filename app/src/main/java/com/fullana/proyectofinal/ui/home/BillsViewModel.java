package com.fullana.proyectofinal.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.fullana.proyectofinal.R;
import com.fullana.proyectofinal.ui.imageSelector.ImageSelectorViewModel;
import com.fullana.proyectofinal.utils.CommonUtils;

public class BillsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BillsViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void navigate(Activity activity) {

        Bundle b = new Bundle();
        b.putInt("load", ImageSelectorViewModel.LOAD_BILLS);
        CommonUtils.navigate(activity, b, R.id.nav_image_selector);
    }
}