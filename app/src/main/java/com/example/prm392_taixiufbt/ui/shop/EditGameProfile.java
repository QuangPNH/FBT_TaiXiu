package com.example.prm392_taixiufbt.ui.shop;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prm392_taixiufbt.DAO.FBTTaiXiuDatabase;
import com.example.prm392_taixiufbt.R;
import com.example.prm392_taixiufbt.models.GameProfile;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class EditGameProfile extends AppCompatActivity {

    private GameProfile gameProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_game_profile);

        gameProfile = (GameProfile) getIntent().getSerializableExtra("gameProfile");

        initUI();
    }

    private void initUI() {
        TextView titleTextView = findViewById(R.id.gameProfileCurrentId);
        EditText titleEditText = findViewById(R.id.titleEditText);
        SwitchMaterial roll1Switch = findViewById(R.id.roll1Switch);
        SwitchMaterial roll2Switch = findViewById(R.id.roll2Switch);
        SwitchMaterial roll3Switch = findViewById(R.id.roll3Switch);
        SwitchMaterial roll4Switch = findViewById(R.id.roll4Switch);
        SwitchMaterial roll5Switch = findViewById(R.id.roll5Switch);
        SwitchMaterial roll6Switch = findViewById(R.id.roll6Switch);
        SwitchMaterial roll7Switch = findViewById(R.id.roll7Switch);
        SwitchMaterial roll8Switch = findViewById(R.id.roll8Switch);
        SwitchMaterial roll9Switch = findViewById(R.id.roll9Switch);
        SwitchMaterial roll10Switch = findViewById(R.id.roll10Switch);

        // Initialize other switches similarly
        Button saveButton = findViewById(R.id.saveButton);

        // Set initial switch states based on the GameProfile object
        titleTextView.setText(String.valueOf(gameProfile.getId()));
        titleEditText.setText(gameProfile.getTitle());
        roll1Switch.setChecked(gameProfile.isRoll1Rate());
        roll2Switch.setChecked(gameProfile.isRoll2Rate());
        roll3Switch.setChecked(gameProfile.isRoll3Rate());
        roll4Switch.setChecked(gameProfile.isRoll4Rate());
        roll5Switch.setChecked(gameProfile.isRoll5Rate());
        roll6Switch.setChecked(gameProfile.isRoll6Rate());
        roll7Switch.setChecked(gameProfile.isRoll7Rate());
        roll8Switch.setChecked(gameProfile.isRoll8Rate());
        roll9Switch.setChecked(gameProfile.isRoll9Rate());
        roll10Switch.setChecked(gameProfile.isRoll10Rate());
        // Initialize other switches similarly
        saveButton.setOnClickListener(v -> {
            new Thread(() -> {
                // Collect modified values from UI components
                int id = Integer.parseInt(titleTextView.getText().toString());
                String title = titleEditText.getText().toString();
                boolean isRoll1Active = roll1Switch.isChecked();
                boolean isRoll2Active = roll2Switch.isChecked();
                boolean isRoll3Active = roll3Switch.isChecked();
                boolean isRoll4Active = roll4Switch.isChecked();
                boolean isRoll5Active = roll5Switch.isChecked();
                boolean isRoll6Active = roll6Switch.isChecked();
                boolean isRoll7Active = roll7Switch.isChecked();
                boolean isRoll8Active = roll8Switch.isChecked();
                boolean isRoll9Active = roll9Switch.isChecked();
                boolean isRoll10Active = roll10Switch.isChecked();

                // Update the GameProfile object
                gameProfile.setId(id);
                gameProfile.setTitle(title);
                gameProfile.setRoll1Rate(isRoll1Active);
                gameProfile.setRoll2Rate(isRoll2Active);
                gameProfile.setRoll3Rate(isRoll3Active);
                gameProfile.setRoll4Rate(isRoll4Active);
                gameProfile.setRoll5Rate(isRoll5Active);
                gameProfile.setRoll6Rate(isRoll6Active);
                gameProfile.setRoll7Rate(isRoll7Active);
                gameProfile.setRoll8Rate(isRoll8Active);
                gameProfile.setRoll9Rate(isRoll9Active);
                gameProfile.setRoll10Rate(isRoll10Active);

                // Get the database instance and update the GameProfile
                FBTTaiXiuDatabase db = FBTTaiXiuDatabase.getDatabase(getApplicationContext());
                db.gameProfileDao().update(gameProfile);

                // Optionally, handle UI updates or navigation on the main thread
                runOnUiThread(() -> {
                    // UI updates or Toast messages
                    Toast.makeText(EditGameProfile.this, "GameProfile saved", Toast.LENGTH_SHORT).show();
                    finish(); // Close the activity
                });
            }).start();
        });
    }
}