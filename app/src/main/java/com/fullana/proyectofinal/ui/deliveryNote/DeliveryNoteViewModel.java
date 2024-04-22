package com.fullana.proyectofinal.ui.deliveryNote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DeliveryNoteViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DeliveryNoteViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}