package com.example.prm392_taixiufbt.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.prm392_taixiufbt.models.User;
import com.example.prm392_taixiufbt.DAO.UserDAO.UserDao;
import com.example.prm392_taixiufbt.DAO.FBTTaiXiuDatabase;

public class UserViewModel extends AndroidViewModel {
    private UserDao userDao;
    private MutableLiveData<Integer> userCount;

    public UserViewModel(Application application) {
        super(application);
        FBTTaiXiuDatabase db = FBTTaiXiuDatabase.getDatabase(application);
        userDao = db.userDao();
        userCount = new MutableLiveData<>();
    }

    public LiveData<Integer> getUserCount() {
        return userDao.getCount();
    }

    public void insert(User user) {
        FBTTaiXiuDatabase.databaseWriteExecutor.execute(() -> {
            userDao.insert(user);
        });
    }
}