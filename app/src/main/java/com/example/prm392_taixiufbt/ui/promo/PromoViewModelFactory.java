package com.example.prm392_taixiufbt.ui.promo;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.example.prm392_taixiufbt.DAO.NewsItemDAO.NewsItemDao;

public class PromoViewModelFactory implements ViewModelProvider.Factory {

    private final NewsItemDao newsItemDao;

    public PromoViewModelFactory(NewsItemDao newsItemDao) {
        this.newsItemDao = newsItemDao;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PromoViewModel.class)) {
            return (T) new PromoViewModel(newsItemDao);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}