package com.example.prm392_taixiufbt.ui.shop;

import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

import com.example.prm392_taixiufbt.DAO.GameProfileDAO;
import com.example.prm392_taixiufbt.DAO.FBTTaiXiuDatabase;
import com.example.prm392_taixiufbt.models.GameProfile;

public class GameProfileRepository {
    private GameProfileDAO.GameProfileDao gameProfileDao;
    private LiveData<List<GameProfile>> allGameProfiles;

    public GameProfileRepository(Application application) {
        FBTTaiXiuDatabase db = FBTTaiXiuDatabase.getDatabase(application);
        gameProfileDao = db.gameProfileDao();
        allGameProfiles = gameProfileDao.getAllLive();
    }

    public LiveData<List<GameProfile>> getAllGameProfiles() {
        return allGameProfiles;
    }
}