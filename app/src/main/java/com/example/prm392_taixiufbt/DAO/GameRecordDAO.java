package com.example.prm392_taixiufbt.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.prm392_taixiufbt.models.GameRecord;

import java.util.List;

public class GameRecordDAO {
    @Dao
    public interface GameRecordDao {
        @Insert
        void insert(GameRecord gameRecord);

        @Query("SELECT * FROM GameRecord")
        List<GameRecord> getAll();

        @Query("SELECT * FROM GameRecord WHERE id = :id")
        GameRecord findById(int id);

        @Update
        void update(GameRecord gameRecord);

        @Delete
        void delete(GameRecord gameRecord);
        // Corrected method with parameter used in the query
        @Query("DELETE FROM GameRecord WHERE userId = :userId")
        void deleteByUserId(int userId);
    }
}
