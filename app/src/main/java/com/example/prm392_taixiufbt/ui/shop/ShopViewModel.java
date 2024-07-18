package com.example.prm392_taixiufbt.ui.shop;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import android.app.Application;
import java.util.List;
import com.example.prm392_taixiufbt.models.GameProfile;

public class ShopViewModel extends ViewModel {

    private GameProfileRepository repository;
    private LiveData<List<GameProfile>> allGameProfiles;



    public ShopViewModel(Application application) {
        repository = new GameProfileRepository(application);
        allGameProfiles = repository.getAllGameProfiles();
    }

    public LiveData<List<GameProfile>> getGameProfiles() {
        return allGameProfiles;
    }
}