package com.example.prm392_taixiufbt.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.prm392_taixiufbt.models.NewsItem;
import com.example.prm392_taixiufbt.models.QAItem;

import java.util.List;

public class QAItemDAO {
    @Dao
    public interface QAItemDao {
        @Query("SELECT * FROM QAItem")
        List<QAItem> getAll();

        @Insert
        void insert(QAItem qaItem);

        @Update
        void update(QAItem qaItem);

        @Delete
        void delete(QAItem qaItem);

        // Example of a specific query method
        @Query("SELECT * FROM QAItem WHERE id = :id")
        QAItem findById(int id);
    }
}
