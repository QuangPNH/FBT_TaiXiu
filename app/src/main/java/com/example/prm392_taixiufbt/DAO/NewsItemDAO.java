package com.example.prm392_taixiufbt.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.prm392_taixiufbt.models.NewsItem;
import com.example.prm392_taixiufbt.models.User;

import java.util.List;

public class NewsItemDAO {
    @Dao
    public interface NewsItemDao {
        @Query("SELECT * FROM NewsItem")
        List<NewsItem> getAll();

        @Insert
        void insert(NewsItem newsItem);

        @Update
        void update(NewsItem newsItem);

        @Delete
        void delete(NewsItem newsItem);
        @Query("SELECT * FROM NewsItem WHERE type_id = :typeId")
        LiveData<List<NewsItem>> findByTypeId(int typeId);
        // Example of a specific query method
        @Query("SELECT * FROM NewsItem WHERE id = :id")
        NewsItem findById(int id);

    }
}
