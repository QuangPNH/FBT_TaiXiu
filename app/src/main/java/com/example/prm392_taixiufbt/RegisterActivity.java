package com.example.prm392_taixiufbt;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.prm392_taixiufbt.models.User;
import com.example.prm392_taixiufbt.models.UserViewModel;

import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullNameEditText, usernameEditText, passwordEditText, emailEditText, phoneEditText;
    private Button registerButton;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullNameEditText = findViewById(R.id.fullNameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        registerButton = findViewById(R.id.registerButton);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        registerButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        // Collect data from EditText fields
        String fullName = fullNameEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();

        // Check if any field is empty and prompt the user if so
        if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new User object with the collected data
        User newUser = new User(fullName, username, password, email, phone, 0, 100000);

        // Insert the new user using the UserViewModel
        userViewModel.insert(newUser);

        // Notify the user of successful registration
        Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}