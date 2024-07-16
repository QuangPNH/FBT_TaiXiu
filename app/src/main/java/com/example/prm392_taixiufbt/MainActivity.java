package com.example.prm392_taixiufbt;

import android.os.Bundle;
import android.util.Log;

import com.example.prm392_taixiufbt.DAO.FBTTaiXiuDatabase;
import com.example.prm392_taixiufbt.DAO.NewsItemDAO;
import com.example.prm392_taixiufbt.DAO.UserDAO;
import com.example.prm392_taixiufbt.databinding.ActivityLoginBinding;
import com.example.prm392_taixiufbt.models.NewsItem;
import com.example.prm392_taixiufbt.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.prm392_taixiufbt.databinding.ActivityMainBinding;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ActivityLoginBinding bindingLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_sport, R.id.navigation_shop,R.id.navigation_promo, R.id.navigation_account)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        // Create an instance of the database
        FBTTaiXiuDatabase db = FBTTaiXiuDatabase.getDatabase(this);

        // Use an ExecutorService to perform database operations asynchronously
        ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();
        databaseExecutor.execute(() -> {
            try {
                // Insert sample data into the database
                NewsItemDAO.NewsItemDao newsItemDao = db.newsItemDao();
                // Initialize NewsItem objects with sample data
                NewsItem newsItem1 = new NewsItem(1, "Promo News 1", "Description for NewsItem 1", "http://example.com/image1.jpg", "Content 1", "Author 1", "01-01-2023", 0);
                NewsItem newsItem2 = new NewsItem(2, "Promo News 2", "Description for NewsItem 2", "http://example.com/image2.jpg", "Content 2", "Author 2", "02-01-2023", 0);
                NewsItem newsItem3 = new NewsItem(3, "Promo News 3", "Description for NewsItem 3", "http://example.com/image3.jpg", "Content 3", "Author 3", "03-01-2023", 0);
                NewsItem newsItem4 = new NewsItem(4, "Sport News 1", "Description for NewsItem 4", "http://example.com/image4.jpg", "Content 4", "Author 4", "04-01-2023", 1);
                NewsItem newsItem5 = new NewsItem(5, "Sport News 2", "Description for NewsItem 5", "http://example.com/image5.jpg", "Content 5", "Author 5", "05-01-2023", 1);
                NewsItem newsItem6 = new NewsItem(6, "Sport News 3", "Description for NewsItem 6", "http://example.com/image6.jpg", "Content 6", "Author 6", "06-01-2023", 1);

                // Insert the data
                newsItemDao.insert(newsItem1);
                newsItemDao.insert(newsItem2);
                newsItemDao.insert(newsItem3);
                newsItemDao.insert(newsItem4);
                newsItemDao.insert(newsItem5);
                newsItemDao.insert(newsItem6);
                // Add more insertions here
                UserDAO.UserDao userDao = db.userDao();


                List<NewsItem> newsItems = newsItemDao.getAll();
                // Initialize User objects with sample data
                User Admin = new User(1, "admin", "admin123", "admin@example.com", "Admin", "0123456789", 1);
                User user1 = new User(1, "user1", "password1", "user1@example.com", "User One", "0123456789", 0);
                User user2 = new User(2, "user2", "password2", "user2@example.com", "User Two", "0123456789", 0);
                User user3 = new User(3, "user3", "password3", "user3@example.com", "User Three", "0123456789", 0);

                // Insert the data
                userDao.insert(user1);
                userDao.insert(user2);
                userDao.insert(user3);

                // Log success
                Log.d("DatabaseInsert", "Sample data inserted successfully");
            } catch (Exception e) {
                // Log or handle the exception
                Log.e("DatabaseInsert", "Error inserting sample data", e);
            }
        });
    }

}