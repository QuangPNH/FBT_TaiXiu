package com.example.prm392_taixiufbt.ui.shop;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import android.app.Application;

public class ShopViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;

    public ShopViewModelFactory(Application application) {
        mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ShopViewModel.class)) {
            return (T) new ShopViewModel(mApplication);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}