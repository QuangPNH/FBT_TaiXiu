package com.example.prm392_taixiufbt.ui.promo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PromoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PromoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is promo fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}