package com.example.prm392_taixiufbt.ui.promo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.prm392_taixiufbt.models.NewsItem;
import com.example.prm392_taixiufbt.DAO.NewsItemDAO.NewsItemDao;
import java.util.List;

public class PromoViewModel extends ViewModel {

    private NewsItemDao newsItemDao; // Reference to NewsItemDao

    public PromoViewModel(NewsItemDao newsItemDao) {
        this.newsItemDao = newsItemDao; // Initialize NewsItemDao
    }

    // Method to fetch NewsItems by typeId using NewsItemDao
    public LiveData<List<NewsItem>> getNewsItemsByTypeId(int typeId) {
        return (LiveData<List<NewsItem>>) newsItemDao.findByTypeId(typeId);
    }
}