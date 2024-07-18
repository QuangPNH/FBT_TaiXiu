package com.example.prm392_taixiufbt.DAO;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.annotation.NonNull;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.prm392_taixiufbt.models.GameProfile;
import com.example.prm392_taixiufbt.models.GameRecord;
import com.example.prm392_taixiufbt.models.NewsItem;
import com.example.prm392_taixiufbt.models.QAItem;
import com.example.prm392_taixiufbt.models.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, NewsItem.class, QAItem.class, GameRecord.class, GameProfile.class}, version = 11)
@TypeConverters({Converters.class})
public abstract class FBTTaiXiuDatabase extends RoomDatabase {
    public abstract UserDAO.UserDao userDao();
    public abstract NewsItemDAO.NewsItemDao newsItemDao();
    public abstract QAItemDAO.QAItemDao qaItemDao();
    public abstract GameRecordDAO.GameRecordDao gameRecordDao();
    public abstract GameProfileDAO.GameProfileDao gameProfileDao();

    // Define the ExecutorService
    public static final ExecutorService databaseWriteExecutor =
            Executors.newCachedThreadPool();
    // Singleton instance
    private static volatile FBTTaiXiuDatabase INSTANCE;


    public static FBTTaiXiuDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FBTTaiXiuDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    FBTTaiXiuDatabase.class, "FBT_TaiXiu").fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}