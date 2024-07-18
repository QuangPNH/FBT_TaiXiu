package com.example.prm392_taixiufbt.ui.shop;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_taixiufbt.DAO.FBTTaiXiuDatabase;
import com.example.prm392_taixiufbt.R;
import com.example.prm392_taixiufbt.models.GameProfile;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;

public class AddGameProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game_profile);

        Button submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(v -> {
            // Retrieve input values
            String title = ((TextInputEditText) findViewById(R.id.titleEditText)).getText().toString();
            boolean roll1Rate = ((SwitchMaterial) findViewById(R.id.roll1Switch)).isChecked();
            boolean roll2Rate = ((SwitchMaterial) findViewById(R.id.roll2Switch)).isChecked();
            boolean roll3Rate = ((SwitchMaterial) findViewById(R.id.roll3Switch)).isChecked();
            boolean roll4Rate = ((SwitchMaterial) findViewById(R.id.roll4Switch)).isChecked();
            boolean roll5Rate = ((SwitchMaterial) findViewById(R.id.roll5Switch)).isChecked();
            boolean roll6Rate = ((SwitchMaterial) findViewById(R.id.roll6Switch)).isChecked();
            boolean roll7Rate = ((SwitchMaterial) findViewById(R.id.roll7Switch)).isChecked();
            boolean roll8Rate = ((SwitchMaterial) findViewById(R.id.roll8Switch)).isChecked();
            boolean roll9Rate = ((SwitchMaterial) findViewById(R.id.roll9Switch)).isChecked();
            boolean roll10Rate = ((SwitchMaterial) findViewById(R.id.roll10Switch)).isChecked();
            // Repeat for each roll rate switch

            // Generate ID for the new GameProfile
            int id = generateId();

            // Create a new GameProfile instance
            GameProfile gameProfile = new GameProfile(id, title, roll1Rate, roll2Rate, roll3Rate, roll4Rate, roll5Rate, roll6Rate, roll7Rate, roll8Rate, roll9Rate, roll10Rate, false);

            // Insert the new GameProfile into the database
            insertGameProfile(gameProfile);

            // Show a confirmation message
            Toast.makeText(AddGameProfile.this, "Game Profile added successfully!", Toast.LENGTH_SHORT).show();

        });
    }

    private int generateId() {
        FBTTaiXiuDatabase db = FBTTaiXiuDatabase.getDatabase(this);
        int maxId = db.gameProfileDao().getMaxId();
        return maxId + 1;
    }

    private void insertGameProfile(GameProfile gameProfile) {
        FBTTaiXiuDatabase db = FBTTaiXiuDatabase.getDatabase(this);
        // Use a background thread for database operations
        new Thread(() -> db.gameProfileDao().insert(gameProfile)).start();
    }
}