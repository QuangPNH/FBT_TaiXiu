package com.example.prm392_taixiufbt.DAO;

import android.content.Context;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.prm392_taixiufbt.models.GameRecord;
import com.example.prm392_taixiufbt.models.User;
import com.example.prm392_taixiufbt.models.UserWithGameRecord;

import java.util.List;

public class UserDAO {

    @Dao
    public interface UserDao {
        @Query("SELECT * FROM user")
        List<User> getAll();

        @Query("SELECT * FROM user WHERE id IN (:userIds)")
        List<User> loadAllByIds(int[] userIds);

        @Insert
        void insert(User users);

        @Delete
        void delete(User user);

        @Update
        void update(User user);

        // Example for a method annotated with @Transaction
        @Transaction
        @Query("SELECT * FROM User")
        List<UserWithGameRecord> getUsersWithGameRecord();

        @Query("SELECT * FROM GameRecord WHERE userId = :userId")
        List<GameRecord> findGameRecordsByUserId(int userId);

        // Additional method to demonstrate @Transaction usage
        @Query("DELETE FROM user WHERE id = :userId")
        void deleteUserById(int userId);

    }

}
