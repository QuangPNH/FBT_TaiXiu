package com.example.prm392_taixiufbt.DAO;

import com.example.prm392_taixiufbt.models.GameProfile;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public class GameProfileDAO {
    @Dao
    public interface GameProfileDao {
        @Insert
        void insert(GameProfile gameProfile);

        @Query("SELECT MAX(id) FROM GameProfile")
        int getMaxId();
        @Query("SELECT * FROM GameProfile")
        List<GameProfile> getAll();
        @Update
        void update(GameProfile gp);
        @Query("SELECT * FROM GameProfile")
        LiveData<List<GameProfile>> getAllLive();
        @Query("SELECT * FROM GameProfile WHERE isActive = :b")
        GameProfile getActiveGameProfile(boolean b);
    }

}
