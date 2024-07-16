package com.example.prm392_taixiufbt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_taixiufbt.DAO.FBTTaiXiuDatabase;
import com.example.prm392_taixiufbt.DAO.NewsItemDAO;
import com.example.prm392_taixiufbt.DAO.UserDAO;
import com.example.prm392_taixiufbt.models.NewsItem;
import com.example.prm392_taixiufbt.models.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

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
                User Admin = new User(10, "admin", "admin123", "admin@example.com", "Admin", "0123456789", 1,1000000000);
                User user1 = new User(1, "user1", "password1", "user1@example.com", "User One", "0123456789", 0,500000);
                User user2 = new User(2, "user2", "password2", "user2@example.com", "User Two", "0123456789", 0,600000);
                User user3 = new User(3, "user3", "password3", "user3@example.com", "User Three", "0123456789", 0,700000);

                // Insert the data
                userDao.insert(user1);
                userDao.insert(user2);
                userDao.insert(user3);
                userDao.insert(Admin);

                // Log success
                Log.d("DatabaseInsert", "Sample data inserted successfully");
            } catch (Exception e) {
                // Log or handle the exception
                Log.e("DatabaseInsert", "Error inserting sample data", e);
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }
    private void checkLoginInBackground(final String username, final String password) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            // Background work here
            FBTTaiXiuDatabase db = FBTTaiXiuDatabase.getDatabase(getApplicationContext());
            User user = db.userDao().findUserByUsernameAndPassword(username, password);

            handler.post(() -> {
                // UI Thread work here
                if (user != null) {
                    // Login success
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    saveLoginInfo(username); // Save login info and proceed to MainActivity
                } else {
                    // Login failed
                    Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
    private void saveLoginInfo(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Username", username);
        editor.apply();

        // Proceed to MainActivity
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Close LoginActivity
    }
    private void checkLogin() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        checkLoginInBackground(username, password);
    }
}